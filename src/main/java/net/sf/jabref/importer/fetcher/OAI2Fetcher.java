begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2016 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.importer.fetcher
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
operator|.
name|fetcher
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|UnsupportedEncodingException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|HttpURLConnection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URLEncoder
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|charset
operator|.
name|StandardCharsets
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Locale
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JOptionPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JPanel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|ParserConfigurationException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|SAXParser
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|SAXParserFactory
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|help
operator|.
name|HelpFile
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
operator|.
name|ImportInspector
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
operator|.
name|OAI2Handler
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
operator|.
name|OutputPrinter
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|l10n
operator|.
name|Localization
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|BibEntry
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|IdGenerator
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|MonthUtil
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_import
import|import
name|org
operator|.
name|xml
operator|.
name|sax
operator|.
name|SAXException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|xml
operator|.
name|sax
operator|.
name|helpers
operator|.
name|DefaultHandler
import|;
end_import

begin_comment
comment|/**  *  * This class can be used to access any archive offering an OAI2 interface. By  * default it will access ArXiv.org  *  * @author Ulrich St&auml;rk  * @author Christian Kopf  */
end_comment

begin_class
DECL|class|OAI2Fetcher
specifier|public
class|class
name|OAI2Fetcher
implements|implements
name|EntryFetcher
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|OAI2Fetcher
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|OAI2_ARXIV_PREFIXIDENTIFIER
specifier|private
specifier|static
specifier|final
name|String
name|OAI2_ARXIV_PREFIXIDENTIFIER
init|=
literal|"oai%3AarXiv.org%3A"
decl_stmt|;
DECL|field|OAI2_ARXIV_HOST
specifier|private
specifier|static
specifier|final
name|String
name|OAI2_ARXIV_HOST
init|=
literal|"export.arxiv.org"
decl_stmt|;
DECL|field|OAI2_ARXIV_SCRIPT
specifier|private
specifier|static
specifier|final
name|String
name|OAI2_ARXIV_SCRIPT
init|=
literal|"oai2"
decl_stmt|;
DECL|field|OAI2_ARXIV_METADATAPREFIX
specifier|private
specifier|static
specifier|final
name|String
name|OAI2_ARXIV_METADATAPREFIX
init|=
literal|"arXiv"
decl_stmt|;
DECL|field|OAI2_ARXIV_ARCHIVENAME
specifier|private
specifier|static
specifier|final
name|String
name|OAI2_ARXIV_ARCHIVENAME
init|=
literal|"ArXiv.org"
decl_stmt|;
DECL|field|OAI2_IDENTIFIER_FIELD
specifier|private
specifier|static
specifier|final
name|String
name|OAI2_IDENTIFIER_FIELD
init|=
literal|"oai2identifier"
decl_stmt|;
DECL|field|saxParser
specifier|private
name|SAXParser
name|saxParser
decl_stmt|;
DECL|field|oai2Host
specifier|private
specifier|final
name|String
name|oai2Host
decl_stmt|;
DECL|field|oai2Script
specifier|private
specifier|final
name|String
name|oai2Script
decl_stmt|;
DECL|field|oai2MetaDataPrefix
specifier|private
specifier|final
name|String
name|oai2MetaDataPrefix
decl_stmt|;
DECL|field|oai2PrefixIdentifier
specifier|private
specifier|final
name|String
name|oai2PrefixIdentifier
decl_stmt|;
DECL|field|oai2ArchiveName
specifier|private
specifier|final
name|String
name|oai2ArchiveName
decl_stmt|;
DECL|field|shouldContinue
specifier|private
name|boolean
name|shouldContinue
init|=
literal|true
decl_stmt|;
DECL|field|status
specifier|private
name|OutputPrinter
name|status
decl_stmt|;
DECL|field|waitTime
specifier|private
name|long
name|waitTime
init|=
operator|-
literal|1
decl_stmt|;
DECL|field|lastCall
specifier|private
name|Date
name|lastCall
decl_stmt|;
comment|/**      *      *      * @param oai2Host      *            the host to query without leading http:// and without trailing /      * @param oai2Script      *            the relative location of the oai2 interface without leading      *            and trailing /      * @param oai2Metadataprefix      *            the urlencoded metadataprefix      * @param oai2Prefixidentifier      *            the urlencoded prefix identifier      * @param waitTimeMs      *            Time to wait in milliseconds between query-requests.      */
DECL|method|OAI2Fetcher (String oai2Host, String oai2Script, String oai2Metadataprefix, String oai2Prefixidentifier, String oai2ArchiveName, long waitTimeMs)
specifier|public
name|OAI2Fetcher
parameter_list|(
name|String
name|oai2Host
parameter_list|,
name|String
name|oai2Script
parameter_list|,
name|String
name|oai2Metadataprefix
parameter_list|,
name|String
name|oai2Prefixidentifier
parameter_list|,
name|String
name|oai2ArchiveName
parameter_list|,
name|long
name|waitTimeMs
parameter_list|)
block|{
name|this
operator|.
name|oai2Host
operator|=
name|oai2Host
expr_stmt|;
name|this
operator|.
name|oai2Script
operator|=
name|oai2Script
expr_stmt|;
name|this
operator|.
name|oai2MetaDataPrefix
operator|=
name|oai2Metadataprefix
expr_stmt|;
name|this
operator|.
name|oai2PrefixIdentifier
operator|=
name|oai2Prefixidentifier
expr_stmt|;
name|this
operator|.
name|oai2ArchiveName
operator|=
name|oai2ArchiveName
expr_stmt|;
name|this
operator|.
name|waitTime
operator|=
name|waitTimeMs
expr_stmt|;
try|try
block|{
name|SAXParserFactory
name|parserFactory
init|=
name|SAXParserFactory
operator|.
name|newInstance
argument_list|()
decl_stmt|;
name|saxParser
operator|=
name|parserFactory
operator|.
name|newSAXParser
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ParserConfigurationException
decl||
name|SAXException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Error creating SAXParser for OAI2Fetcher"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Default Constructor. The archive queried will be ArXiv.org      *      */
DECL|method|OAI2Fetcher ()
specifier|public
name|OAI2Fetcher
parameter_list|()
block|{
name|this
argument_list|(
name|OAI2Fetcher
operator|.
name|OAI2_ARXIV_HOST
argument_list|,
name|OAI2Fetcher
operator|.
name|OAI2_ARXIV_SCRIPT
argument_list|,
name|OAI2Fetcher
operator|.
name|OAI2_ARXIV_METADATAPREFIX
argument_list|,
name|OAI2Fetcher
operator|.
name|OAI2_ARXIV_PREFIXIDENTIFIER
argument_list|,
name|OAI2Fetcher
operator|.
name|OAI2_ARXIV_ARCHIVENAME
argument_list|,
literal|20000L
argument_list|)
expr_stmt|;
block|}
comment|/**      * Construct the query URL      *      * @param key      *            The key of the OAI2 entry that the url should point to.      *      * @return a String denoting the query URL      */
DECL|method|constructUrl (String key)
specifier|public
name|String
name|constructUrl
parameter_list|(
name|String
name|key
parameter_list|)
block|{
name|String
name|identifier
decl_stmt|;
try|try
block|{
name|identifier
operator|=
name|URLEncoder
operator|.
name|encode
argument_list|(
name|key
argument_list|,
name|StandardCharsets
operator|.
name|UTF_8
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsupportedEncodingException
name|e
parameter_list|)
block|{
return|return
literal|""
return|;
block|}
return|return
literal|"http://"
operator|+
name|oai2Host
operator|+
literal|"/"
operator|+
name|oai2Script
operator|+
literal|"?"
operator|+
literal|"verb=GetRecord"
operator|+
literal|"&identifier="
operator|+
name|oai2PrefixIdentifier
operator|+
name|identifier
operator|+
literal|"&metadataPrefix="
operator|+
name|oai2MetaDataPrefix
return|;
block|}
comment|/**      * some archives - like ArXiv.org - might expect of you to wait some time      */
DECL|method|shouldWait ()
specifier|private
name|boolean
name|shouldWait
parameter_list|()
block|{
return|return
name|waitTime
operator|>
literal|0
return|;
block|}
comment|/**      * Strip subcategories from ArXiv key.      *      * @param key The key to fix.      * @return Fixed key.      */
DECL|method|fixKey (String key)
specifier|public
specifier|static
name|String
name|fixKey
parameter_list|(
name|String
name|key
parameter_list|)
block|{
name|String
name|resultingKey
init|=
name|key
decl_stmt|;
if|if
condition|(
name|resultingKey
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|ENGLISH
argument_list|)
operator|.
name|startsWith
argument_list|(
literal|"arxiv:"
argument_list|)
condition|)
block|{
name|resultingKey
operator|=
name|resultingKey
operator|.
name|substring
argument_list|(
literal|6
argument_list|)
expr_stmt|;
block|}
name|int
name|dot
init|=
name|resultingKey
operator|.
name|indexOf
argument_list|(
literal|'.'
argument_list|)
decl_stmt|;
name|int
name|slash
init|=
name|resultingKey
operator|.
name|indexOf
argument_list|(
literal|'/'
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|dot
operator|>
operator|-
literal|1
operator|)
operator|&&
operator|(
name|dot
operator|<
name|slash
operator|)
condition|)
block|{
name|resultingKey
operator|=
name|resultingKey
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|dot
argument_list|)
operator|+
name|resultingKey
operator|.
name|substring
argument_list|(
name|slash
argument_list|,
name|resultingKey
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|resultingKey
return|;
block|}
DECL|method|correctLineBreaks (String s)
specifier|public
specifier|static
name|String
name|correctLineBreaks
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|String
name|result
init|=
name|s
operator|.
name|replaceAll
argument_list|(
literal|"\\n(?!\\s*\\n)"
argument_list|,
literal|" "
argument_list|)
decl_stmt|;
name|result
operator|=
name|result
operator|.
name|replaceAll
argument_list|(
literal|"\\s*\\n\\s*"
argument_list|,
literal|"\n"
argument_list|)
expr_stmt|;
return|return
name|result
operator|.
name|replaceAll
argument_list|(
literal|" {2,}"
argument_list|,
literal|" "
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"(^\\s*|\\s+$)"
argument_list|,
literal|""
argument_list|)
return|;
block|}
comment|/**      * Import an entry from an OAI2 archive. The BibEntry provided has to      * have the field OAI2_IDENTIFIER_FIELD set to the search string.      *      * @param key      *            The OAI2 key to fetch from ArXiv.      * @return The imported BibEntry or null if none.      */
DECL|method|importOai2Entry (String key)
specifier|public
name|BibEntry
name|importOai2Entry
parameter_list|(
name|String
name|key
parameter_list|)
block|{
comment|/**          * Fix for problem reported in mailing-list:          *   https://sourceforge.net/forum/message.php?msg_id=4087158          */
name|String
name|fixedKey
init|=
name|OAI2Fetcher
operator|.
name|fixKey
argument_list|(
name|key
argument_list|)
decl_stmt|;
name|String
name|url
init|=
name|constructUrl
argument_list|(
name|fixedKey
argument_list|)
decl_stmt|;
try|try
block|{
name|URL
name|oai2Url
init|=
operator|new
name|URL
argument_list|(
name|url
argument_list|)
decl_stmt|;
name|HttpURLConnection
name|oai2Connection
init|=
operator|(
name|HttpURLConnection
operator|)
name|oai2Url
operator|.
name|openConnection
argument_list|()
decl_stmt|;
name|oai2Connection
operator|.
name|setRequestProperty
argument_list|(
literal|"User-Agent"
argument_list|,
literal|"JabRef"
argument_list|)
expr_stmt|;
comment|/* create an empty BibEntry and set the oai2identifier field */
name|BibEntry
name|be
init|=
operator|new
name|BibEntry
argument_list|(
name|IdGenerator
operator|.
name|next
argument_list|()
argument_list|,
literal|"article"
argument_list|)
decl_stmt|;
name|be
operator|.
name|setField
argument_list|(
name|OAI2Fetcher
operator|.
name|OAI2_IDENTIFIER_FIELD
argument_list|,
name|fixedKey
argument_list|)
expr_stmt|;
name|DefaultHandler
name|handlerBase
init|=
operator|new
name|OAI2Handler
argument_list|(
name|be
argument_list|)
decl_stmt|;
try|try
init|(
name|InputStream
name|inputStream
init|=
name|oai2Connection
operator|.
name|getInputStream
argument_list|()
init|)
block|{
comment|/* parse the result */
name|saxParser
operator|.
name|parse
argument_list|(
name|inputStream
argument_list|,
name|handlerBase
argument_list|)
expr_stmt|;
comment|/* Correct line breaks and spacing */
for|for
control|(
name|String
name|name
range|:
name|be
operator|.
name|getFieldNames
argument_list|()
control|)
block|{
name|be
operator|.
name|getFieldOptional
argument_list|(
name|name
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|content
lambda|->
name|be
operator|.
name|setField
argument_list|(
name|name
argument_list|,
name|OAI2Fetcher
operator|.
name|correctLineBreaks
argument_list|(
name|content
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|fixedKey
operator|.
name|matches
argument_list|(
literal|"\\d\\d\\d\\d\\..*"
argument_list|)
condition|)
block|{
name|be
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"20"
operator|+
name|fixedKey
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|int
name|monthNumber
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|fixedKey
operator|.
name|substring
argument_list|(
literal|2
argument_list|,
literal|4
argument_list|)
argument_list|)
decl_stmt|;
name|MonthUtil
operator|.
name|Month
name|month
init|=
name|MonthUtil
operator|.
name|getMonthByNumber
argument_list|(
name|monthNumber
argument_list|)
decl_stmt|;
if|if
condition|(
name|month
operator|.
name|isValid
argument_list|()
condition|)
block|{
name|be
operator|.
name|setField
argument_list|(
literal|"month"
argument_list|,
name|month
operator|.
name|bibtexFormat
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|be
return|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|status
operator|.
name|showMessage
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"An Exception occurred while accessing '%0'"
argument_list|,
name|url
argument_list|)
operator|+
literal|"\n\n"
operator|+
name|e
argument_list|,
name|getTitle
argument_list|()
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SAXException
name|e
parameter_list|)
block|{
name|status
operator|.
name|showMessage
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"An SAXException occurred while parsing '%0':"
argument_list|,
name|url
argument_list|)
operator|+
literal|"\n\n"
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|getTitle
argument_list|()
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|RuntimeException
name|e
parameter_list|)
block|{
name|status
operator|.
name|showMessage
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error while fetching from %0"
argument_list|,
literal|"OAI2 source ("
operator|+
name|url
operator|+
literal|"):"
argument_list|)
operator|+
literal|"\n\n"
operator|+
name|e
operator|.
name|getMessage
argument_list|()
operator|+
literal|"\n\n"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Note: A full text search is currently not supported for %0"
argument_list|,
name|getTitle
argument_list|()
argument_list|)
argument_list|,
name|getTitle
argument_list|()
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|getHelpPage ()
specifier|public
name|HelpFile
name|getHelpPage
parameter_list|()
block|{
return|return
name|HelpFile
operator|.
name|FETCHER_OAI2_ARXIV
return|;
block|}
annotation|@
name|Override
DECL|method|getOptionsPanel ()
specifier|public
name|JPanel
name|getOptionsPanel
parameter_list|()
block|{
comment|// we have no additional options
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|getTitle ()
specifier|public
name|String
name|getTitle
parameter_list|()
block|{
return|return
literal|"ArXiv.org"
return|;
block|}
annotation|@
name|Override
DECL|method|processQuery (String query, ImportInspector dialog, OutputPrinter statusOP)
specifier|public
name|boolean
name|processQuery
parameter_list|(
name|String
name|query
parameter_list|,
name|ImportInspector
name|dialog
parameter_list|,
name|OutputPrinter
name|statusOP
parameter_list|)
block|{
name|status
operator|=
name|statusOP
expr_stmt|;
try|try
block|{
name|shouldContinue
operator|=
literal|true
expr_stmt|;
comment|/* multiple keys can be delimited by ; or space */
name|String
index|[]
name|keys
init|=
name|query
operator|.
name|replace
argument_list|(
literal|" "
argument_list|,
literal|";"
argument_list|)
operator|.
name|split
argument_list|(
literal|";"
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|keys
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|String
name|key
init|=
name|keys
index|[
name|i
index|]
decl_stmt|;
comment|/*                  * some archives - like arxive.org - might expect of you to wait                  * some time                  */
if|if
condition|(
name|shouldWait
argument_list|()
operator|&&
operator|(
name|lastCall
operator|!=
literal|null
operator|)
condition|)
block|{
name|long
name|elapsed
init|=
operator|new
name|Date
argument_list|()
operator|.
name|getTime
argument_list|()
operator|-
name|lastCall
operator|.
name|getTime
argument_list|()
decl_stmt|;
while|while
condition|(
name|elapsed
operator|<
name|waitTime
condition|)
block|{
name|status
operator|.
name|setStatus
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Waiting for ArXiv..."
argument_list|)
operator|+
operator|(
operator|(
name|waitTime
operator|-
name|elapsed
operator|)
operator|/
literal|1000
operator|)
operator|+
literal|" s"
argument_list|)
expr_stmt|;
name|Thread
operator|.
name|sleep
argument_list|(
literal|1000
argument_list|)
expr_stmt|;
name|elapsed
operator|=
operator|new
name|Date
argument_list|()
operator|.
name|getTime
argument_list|()
operator|-
name|lastCall
operator|.
name|getTime
argument_list|()
expr_stmt|;
block|}
block|}
name|status
operator|.
name|setStatus
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Processing %0"
argument_list|,
name|key
argument_list|)
argument_list|)
expr_stmt|;
comment|/* the cancel button has been hit */
if|if
condition|(
operator|!
name|shouldContinue
condition|)
block|{
break|break;
block|}
comment|/* query the archive and load the results into the BibEntry */
name|BibEntry
name|be
init|=
name|importOai2Entry
argument_list|(
name|key
argument_list|)
decl_stmt|;
if|if
condition|(
name|shouldWait
argument_list|()
condition|)
block|{
name|lastCall
operator|=
operator|new
name|Date
argument_list|()
expr_stmt|;
block|}
comment|/* add the entry to the inspection dialog */
if|if
condition|(
name|be
operator|!=
literal|null
condition|)
block|{
name|dialog
operator|.
name|addEntry
argument_list|(
name|be
argument_list|)
expr_stmt|;
block|}
comment|/* update the dialogs progress bar */
name|dialog
operator|.
name|setProgress
argument_list|(
name|i
operator|+
literal|1
argument_list|,
name|keys
operator|.
name|length
argument_list|)
expr_stmt|;
block|}
return|return
literal|true
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|status
operator|.
name|setStatus
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error while fetching from %0"
argument_list|,
literal|"OAI2"
argument_list|)
argument_list|)
expr_stmt|;
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Error while fetching from OAI2"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
return|return
literal|false
return|;
block|}
annotation|@
name|Override
DECL|method|stopFetching ()
specifier|public
name|void
name|stopFetching
parameter_list|()
block|{
name|shouldContinue
operator|=
literal|false
expr_stmt|;
block|}
block|}
end_class

end_unit

