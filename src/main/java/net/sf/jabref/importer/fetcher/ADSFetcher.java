begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (c) 2009, Ryo IGARASHI<rigarash@gmail.com>  * All rights reserved.  * Redistribution and use in source and binary forms, with or without  * modification, are permitted provided that the following conditions are met:  *  *     * Redistributions of source code must retain the above copyright  *       notice, this list of conditions and the following disclaimer.  *     * Redistributions in binary form must reproduce the above copyright  *        notice, this list of conditions and the following disclaimer in the  *       documentation and/or other materials provided with the distribution.  *  * THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS "AS IS" AND ANY  * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED  * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE  * DISCLAIMED. IN NO EVENT SHALL THE REGENTS AND CONTRIBUTORS BE LIABLE FOR ANY  * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES  * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;  * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND  * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT  * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS  * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.  */
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
name|InputStreamReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|BufferedInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|BufferedReader
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
name|javax
operator|.
name|xml
operator|.
name|stream
operator|.
name|XMLInputFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|stream
operator|.
name|XMLStreamReader
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|stream
operator|.
name|XMLStreamException
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
operator|.
name|*
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
name|fileformat
operator|.
name|BibtexParser
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
name|BibtexEntry
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
name|database
operator|.
name|BibtexDatabase
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

begin_comment
comment|/**  *  * This class handles accessing and obtaining BibTeX entry  * from ADS(The NASA Astrophysics Data System).  * Fetching using DOI(Document Object Identifier) is only supported.  *  * @author Ryo IGARASHI  * @version $Id$  */
end_comment

begin_class
DECL|class|ADSFetcher
specifier|public
class|class
name|ADSFetcher
implements|implements
name|EntryFetcher
block|{
annotation|@
name|Override
DECL|method|getOptionsPanel ()
specifier|public
name|JPanel
name|getOptionsPanel
parameter_list|()
block|{
comment|// No option panel
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|getHelpPage ()
specifier|public
name|String
name|getHelpPage
parameter_list|()
block|{
comment|// TODO: No help page
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|getKeyName ()
specifier|public
name|String
name|getKeyName
parameter_list|()
block|{
return|return
literal|"ADS from ADS-DOI"
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
name|Localization
operator|.
name|menuTitle
argument_list|(
name|getKeyName
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|processQuery (String query, ImportInspector dialog, OutputPrinter status)
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
name|status
parameter_list|)
block|{
try|try
block|{
comment|/* Remove "doi:" scheme identifier */
name|query
operator|=
name|query
operator|.
name|replaceAll
argument_list|(
literal|"^(doi:|DOI:)"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
comment|/* Allow fetching only 1 key */
name|String
name|key
init|=
name|query
decl_stmt|;
comment|/* Query ADS and load the results into the BibtexDatabase */
name|status
operator|.
name|setStatus
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Processing "
argument_list|)
operator|+
name|key
argument_list|)
expr_stmt|;
name|BibtexDatabase
name|bd
init|=
name|importADSEntries
argument_list|(
name|key
argument_list|,
name|status
argument_list|)
decl_stmt|;
comment|/* Add the entry to the inspection dialog */
name|status
operator|.
name|setStatus
argument_list|(
literal|"Adding fetched entries"
argument_list|)
expr_stmt|;
if|if
condition|(
name|bd
operator|.
name|getEntryCount
argument_list|()
operator|>
literal|0
condition|)
block|{
for|for
control|(
name|BibtexEntry
name|entry
range|:
name|bd
operator|.
name|getEntries
argument_list|()
control|)
block|{
name|importADSAbstract
argument_list|(
name|key
argument_list|,
name|entry
argument_list|,
name|status
argument_list|)
expr_stmt|;
name|dialog
operator|.
name|addEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
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
literal|"Error while fetching from ADS"
argument_list|)
operator|+
literal|": "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
return|return
literal|true
return|;
block|}
annotation|@
name|Override
DECL|method|stopFetching ()
specifier|public
name|void
name|stopFetching
parameter_list|()
block|{     }
DECL|method|importADSEntries (String key, OutputPrinter status)
specifier|private
name|BibtexDatabase
name|importADSEntries
parameter_list|(
name|String
name|key
parameter_list|,
name|OutputPrinter
name|status
parameter_list|)
block|{
name|String
name|url
init|=
name|constructUrl
argument_list|(
name|key
argument_list|)
decl_stmt|;
try|try
block|{
name|URL
name|ADSUrl
init|=
operator|new
name|URL
argument_list|(
name|url
operator|+
literal|"&data_type=BIBTEX"
argument_list|)
decl_stmt|;
name|HttpURLConnection
name|ADSConnection
init|=
operator|(
name|HttpURLConnection
operator|)
name|ADSUrl
operator|.
name|openConnection
argument_list|()
decl_stmt|;
name|ADSConnection
operator|.
name|setRequestProperty
argument_list|(
literal|"User-Agent"
argument_list|,
literal|"Jabref"
argument_list|)
expr_stmt|;
name|InputStream
name|is
init|=
name|ADSConnection
operator|.
name|getInputStream
argument_list|()
decl_stmt|;
name|BufferedReader
name|reader
init|=
operator|new
name|BufferedReader
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
name|is
argument_list|)
argument_list|)
decl_stmt|;
name|ParserResult
name|pr
init|=
name|BibtexParser
operator|.
name|parse
argument_list|(
name|reader
argument_list|)
decl_stmt|;
return|return
name|pr
operator|.
name|getDatabase
argument_list|()
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
literal|"An Exception ocurred while accessing '%0'"
argument_list|,
name|url
argument_list|)
operator|+
literal|"\n\n"
operator|+
name|e
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
name|getKeyName
argument_list|()
argument_list|)
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
literal|"An Error occurred while fetching from ADS (%0):"
argument_list|,
operator|new
name|String
index|[]
block|{
name|url
block|}
argument_list|)
operator|+
literal|"\n\n"
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
name|getKeyName
argument_list|()
argument_list|)
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
DECL|method|constructUrl (String key)
specifier|private
specifier|static
name|String
name|constructUrl
parameter_list|(
name|String
name|key
parameter_list|)
block|{
return|return
literal|"http://adsabs.harvard.edu/doi/"
operator|+
name|key
return|;
block|}
DECL|method|importADSAbstract (String key, BibtexEntry entry, OutputPrinter status)
specifier|private
name|void
name|importADSAbstract
parameter_list|(
name|String
name|key
parameter_list|,
name|BibtexEntry
name|entry
parameter_list|,
name|OutputPrinter
name|status
parameter_list|)
block|{
comment|/* TODO: construct ADSUrl from BibtexEntry */
name|String
name|url
init|=
name|constructUrl
argument_list|(
name|key
argument_list|)
decl_stmt|;
try|try
block|{
name|URL
name|ADSUrl
init|=
operator|new
name|URL
argument_list|(
name|url
operator|+
literal|"&data_type=XML"
argument_list|)
decl_stmt|;
name|HttpURLConnection
name|ADSConnection
init|=
operator|(
name|HttpURLConnection
operator|)
name|ADSUrl
operator|.
name|openConnection
argument_list|()
decl_stmt|;
name|ADSConnection
operator|.
name|setRequestProperty
argument_list|(
literal|"User-Agent"
argument_list|,
literal|"Jabref"
argument_list|)
expr_stmt|;
name|BufferedInputStream
name|bis
init|=
operator|new
name|BufferedInputStream
argument_list|(
name|ADSConnection
operator|.
name|getInputStream
argument_list|()
argument_list|)
decl_stmt|;
name|XMLInputFactory
name|factory
init|=
name|XMLInputFactory
operator|.
name|newInstance
argument_list|()
decl_stmt|;
name|XMLStreamReader
name|reader
init|=
name|factory
operator|.
name|createXMLStreamReader
argument_list|(
name|bis
argument_list|)
decl_stmt|;
name|boolean
name|isAbstract
init|=
literal|false
decl_stmt|;
name|String
name|abstractText
init|=
literal|""
decl_stmt|;
while|while
condition|(
name|reader
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|reader
operator|.
name|next
argument_list|()
expr_stmt|;
if|if
condition|(
name|reader
operator|.
name|isStartElement
argument_list|()
operator|&&
name|reader
operator|.
name|getLocalName
argument_list|()
operator|.
name|equals
argument_list|(
literal|"abstract"
argument_list|)
condition|)
block|{
name|isAbstract
operator|=
literal|true
expr_stmt|;
block|}
if|if
condition|(
name|isAbstract
operator|&&
name|reader
operator|.
name|isCharacters
argument_list|()
condition|)
block|{
name|abstractText
operator|=
name|abstractText
operator|+
name|reader
operator|.
name|getText
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|isAbstract
operator|&&
name|reader
operator|.
name|isEndElement
argument_list|()
condition|)
block|{
name|isAbstract
operator|=
literal|false
expr_stmt|;
block|}
block|}
name|abstractText
operator|=
name|abstractText
operator|.
name|replace
argument_list|(
literal|"\n"
argument_list|,
literal|" "
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"abstract"
argument_list|,
name|abstractText
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|XMLStreamException
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
literal|"An Error occurred while parsing abstract"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
name|getKeyName
argument_list|()
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
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
literal|"An Exception ocurred while accessing '%0'"
argument_list|,
name|url
argument_list|)
operator|+
literal|"\n\n"
operator|+
name|e
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
name|getKeyName
argument_list|()
argument_list|)
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
literal|"An Error occurred while fetching from ADS (%0):"
argument_list|,
operator|new
name|String
index|[]
block|{
name|url
block|}
argument_list|)
operator|+
literal|"\n\n"
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
name|getKeyName
argument_list|()
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

