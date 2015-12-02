begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2012, 2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.importer.fileformat
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
operator|.
name|fileformat
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
name|OutputStreamWriter
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
name|MalformedURLException
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
name|URLConnection
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
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Scanner
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
name|XMLStreamConstants
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
name|entry
operator|.
name|BibtexEntryTypes
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
name|Globals
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
name|JabRef
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
name|logic
operator|.
name|labelPattern
operator|.
name|LabelPatternUtil
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
name|EntryType
import|;
end_import

begin_comment
comment|/**  * This importer parses text format citations using the online API of FreeCite -  * Open Source Citation Parser http://freecite.library.brown.edu/  */
end_comment

begin_class
DECL|class|FreeCiteImporter
specifier|public
class|class
name|FreeCiteImporter
extends|extends
name|ImportFormat
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
name|FreeCiteImporter
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Override
DECL|method|isRecognizedFormat (InputStream in)
specifier|public
name|boolean
name|isRecognizedFormat
parameter_list|(
name|InputStream
name|in
parameter_list|)
throws|throws
name|IOException
block|{
comment|// TODO: We don't know how to recognize text files, therefore we return
comment|// "false"
return|return
literal|false
return|;
block|}
annotation|@
name|Override
DECL|method|importEntries (InputStream in, OutputPrinter status)
specifier|public
name|List
argument_list|<
name|BibtexEntry
argument_list|>
name|importEntries
parameter_list|(
name|InputStream
name|in
parameter_list|,
name|OutputPrinter
name|status
parameter_list|)
throws|throws
name|IOException
block|{
try|try
init|(
name|Scanner
name|scan
init|=
operator|new
name|Scanner
argument_list|(
name|in
argument_list|)
init|)
block|{
name|String
name|text
init|=
name|scan
operator|.
name|useDelimiter
argument_list|(
literal|"\\A"
argument_list|)
operator|.
name|next
argument_list|()
decl_stmt|;
return|return
name|importEntries
argument_list|(
name|text
argument_list|,
name|status
argument_list|)
return|;
block|}
block|}
DECL|method|importEntries (String text, OutputPrinter status)
specifier|public
name|List
argument_list|<
name|BibtexEntry
argument_list|>
name|importEntries
parameter_list|(
name|String
name|text
parameter_list|,
name|OutputPrinter
name|status
parameter_list|)
block|{
comment|// URLencode the string for transmission
name|String
name|urlencodedCitation
init|=
literal|null
decl_stmt|;
try|try
block|{
name|urlencodedCitation
operator|=
name|URLEncoder
operator|.
name|encode
argument_list|(
name|text
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
comment|// e.printStackTrace();
block|}
name|String
name|data
init|=
literal|"citation="
operator|+
name|urlencodedCitation
decl_stmt|;
comment|// Send the request
name|URL
name|url
decl_stmt|;
name|URLConnection
name|conn
decl_stmt|;
try|try
block|{
name|url
operator|=
operator|new
name|URL
argument_list|(
literal|"http://freecite.library.brown.edu/citations/create"
argument_list|)
expr_stmt|;
name|conn
operator|=
name|url
operator|.
name|openConnection
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|MalformedURLException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
return|return
literal|null
return|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
return|return
literal|null
return|;
block|}
try|try
block|{
name|conn
operator|.
name|setRequestProperty
argument_list|(
literal|"accept"
argument_list|,
literal|"text/xml"
argument_list|)
expr_stmt|;
name|conn
operator|.
name|setDoOutput
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|OutputStreamWriter
name|writer
init|=
operator|new
name|OutputStreamWriter
argument_list|(
name|conn
operator|.
name|getOutputStream
argument_list|()
argument_list|)
decl_stmt|;
comment|// write parameters
name|writer
operator|.
name|write
argument_list|(
name|data
argument_list|)
expr_stmt|;
name|writer
operator|.
name|flush
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalStateException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Already connected."
argument_list|,
name|e
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
literal|"Unable to connect to FreeCite online service."
argument_list|)
argument_list|)
expr_stmt|;
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Unable to connect to FreeCite online service."
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
comment|// output is in conn.getInputStream();
comment|// new InputStreamReader(conn.getInputStream())
name|List
argument_list|<
name|BibtexEntry
argument_list|>
name|res
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|XMLInputFactory
name|factory
init|=
name|XMLInputFactory
operator|.
name|newInstance
argument_list|()
decl_stmt|;
try|try
block|{
name|XMLStreamReader
name|parser
init|=
name|factory
operator|.
name|createXMLStreamReader
argument_list|(
name|conn
operator|.
name|getInputStream
argument_list|()
argument_list|)
decl_stmt|;
while|while
condition|(
name|parser
operator|.
name|hasNext
argument_list|()
condition|)
block|{
if|if
condition|(
operator|(
name|parser
operator|.
name|getEventType
argument_list|()
operator|==
name|XMLStreamConstants
operator|.
name|START_ELEMENT
operator|)
operator|&&
literal|"citation"
operator|.
name|equals
argument_list|(
name|parser
operator|.
name|getLocalName
argument_list|()
argument_list|)
condition|)
block|{
name|parser
operator|.
name|nextTag
argument_list|()
expr_stmt|;
name|StringBuilder
name|noteSB
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|BibtexEntry
name|e
init|=
operator|new
name|BibtexEntry
argument_list|()
decl_stmt|;
comment|// fallback type
name|EntryType
name|type
init|=
name|BibtexEntryTypes
operator|.
name|INPROCEEDINGS
decl_stmt|;
while|while
condition|(
operator|!
operator|(
operator|(
name|parser
operator|.
name|getEventType
argument_list|()
operator|==
name|XMLStreamConstants
operator|.
name|END_ELEMENT
operator|)
operator|&&
literal|"citation"
operator|.
name|equals
argument_list|(
name|parser
operator|.
name|getLocalName
argument_list|()
argument_list|)
operator|)
condition|)
block|{
if|if
condition|(
name|parser
operator|.
name|getEventType
argument_list|()
operator|==
name|XMLStreamConstants
operator|.
name|START_ELEMENT
condition|)
block|{
name|String
name|ln
init|=
name|parser
operator|.
name|getLocalName
argument_list|()
decl_stmt|;
if|if
condition|(
literal|"authors"
operator|.
name|equals
argument_list|(
name|ln
argument_list|)
condition|)
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|parser
operator|.
name|nextTag
argument_list|()
expr_stmt|;
while|while
condition|(
name|parser
operator|.
name|getEventType
argument_list|()
operator|==
name|XMLStreamConstants
operator|.
name|START_ELEMENT
condition|)
block|{
comment|// author is directly nested below authors
assert|assert
literal|"author"
operator|.
name|equals
argument_list|(
name|parser
operator|.
name|getLocalName
argument_list|()
argument_list|)
assert|;
name|String
name|author
init|=
name|parser
operator|.
name|getElementText
argument_list|()
decl_stmt|;
if|if
condition|(
name|sb
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
comment|// first author
name|sb
operator|.
name|append
argument_list|(
name|author
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
literal|" and "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|author
argument_list|)
expr_stmt|;
block|}
assert|assert
name|parser
operator|.
name|getEventType
argument_list|()
operator|==
name|XMLStreamConstants
operator|.
name|END_ELEMENT
assert|;
assert|assert
literal|"author"
operator|.
name|equals
argument_list|(
name|parser
operator|.
name|getLocalName
argument_list|()
argument_list|)
assert|;
name|parser
operator|.
name|nextTag
argument_list|()
expr_stmt|;
comment|// current tag is either begin:author or
comment|// end:authors
block|}
name|e
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"journal"
operator|.
name|equals
argument_list|(
name|ln
argument_list|)
condition|)
block|{
comment|// we guess that the entry is a journal
comment|// the alternative way is to parse
comment|// ctx:context-objects / ctx:context-object / ctx:referent / ctx:metadata-by-val / ctx:metadata / journal / rft:genre
comment|// the drawback is that ctx:context-objects is NOT nested in citation, but a separate element
comment|// we would have to change the whole parser to parse that format.
name|type
operator|=
name|BibtexEntryTypes
operator|.
name|ARTICLE
expr_stmt|;
name|e
operator|.
name|setField
argument_list|(
name|ln
argument_list|,
name|parser
operator|.
name|getElementText
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"tech"
operator|.
name|equals
argument_list|(
name|ln
argument_list|)
condition|)
block|{
name|type
operator|=
name|BibtexEntryTypes
operator|.
name|TECHREPORT
expr_stmt|;
comment|// the content of the "tech" field seems to contain the number of the technical report
name|e
operator|.
name|setField
argument_list|(
literal|"number"
argument_list|,
name|parser
operator|.
name|getElementText
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"doi"
operator|.
name|equals
argument_list|(
name|ln
argument_list|)
operator|||
literal|"institution"
operator|.
name|equals
argument_list|(
name|ln
argument_list|)
operator|||
literal|"location"
operator|.
name|equals
argument_list|(
name|ln
argument_list|)
operator|||
literal|"number"
operator|.
name|equals
argument_list|(
name|ln
argument_list|)
operator|||
literal|"note"
operator|.
name|equals
argument_list|(
name|ln
argument_list|)
operator|||
literal|"title"
operator|.
name|equals
argument_list|(
name|ln
argument_list|)
operator|||
literal|"pages"
operator|.
name|equals
argument_list|(
name|ln
argument_list|)
operator|||
literal|"publisher"
operator|.
name|equals
argument_list|(
name|ln
argument_list|)
operator|||
literal|"volume"
operator|.
name|equals
argument_list|(
name|ln
argument_list|)
operator|||
literal|"year"
operator|.
name|equals
argument_list|(
name|ln
argument_list|)
condition|)
block|{
name|e
operator|.
name|setField
argument_list|(
name|ln
argument_list|,
name|parser
operator|.
name|getElementText
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"booktitle"
operator|.
name|equals
argument_list|(
name|ln
argument_list|)
condition|)
block|{
name|String
name|booktitle
init|=
name|parser
operator|.
name|getElementText
argument_list|()
decl_stmt|;
if|if
condition|(
name|booktitle
operator|.
name|startsWith
argument_list|(
literal|"In "
argument_list|)
condition|)
block|{
comment|// special treatment for parsing of
comment|// "In proceedings of..." references
name|booktitle
operator|=
name|booktitle
operator|.
name|substring
argument_list|(
literal|3
argument_list|)
expr_stmt|;
block|}
name|e
operator|.
name|setField
argument_list|(
literal|"booktitle"
argument_list|,
name|booktitle
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"raw_string"
operator|.
name|equals
argument_list|(
name|ln
argument_list|)
condition|)
block|{
comment|// raw input string is ignored
block|}
else|else
block|{
comment|// all other tags are stored as note
name|noteSB
operator|.
name|append
argument_list|(
name|ln
argument_list|)
expr_stmt|;
name|noteSB
operator|.
name|append
argument_list|(
literal|":"
argument_list|)
expr_stmt|;
name|noteSB
operator|.
name|append
argument_list|(
name|parser
operator|.
name|getElementText
argument_list|()
argument_list|)
expr_stmt|;
name|noteSB
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
block|}
block|}
name|parser
operator|.
name|next
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|noteSB
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|String
name|note
init|=
name|e
operator|.
name|getField
argument_list|(
literal|"note"
argument_list|)
decl_stmt|;
if|if
condition|(
name|note
operator|!=
literal|null
condition|)
block|{
comment|// "note" could have been set during the parsing as FreeCite also returns "note"
name|note
operator|=
name|note
operator|.
name|concat
argument_list|(
name|Globals
operator|.
name|NEWLINE
argument_list|)
operator|.
name|concat
argument_list|(
name|noteSB
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|note
operator|=
name|noteSB
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
name|e
operator|.
name|setField
argument_list|(
literal|"note"
argument_list|,
name|note
argument_list|)
expr_stmt|;
block|}
comment|// type has been derived from "genre"
comment|// has to be done before label generation as label generation is dependent on entry type
name|e
operator|.
name|setType
argument_list|(
name|type
argument_list|)
expr_stmt|;
comment|// autogenerate label (BibTeX key)
name|LabelPatternUtil
operator|.
name|makeLabel
argument_list|(
name|JabRef
operator|.
name|jrf
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|metaData
argument_list|()
argument_list|,
name|JabRef
operator|.
name|jrf
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|database
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|res
operator|.
name|add
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
name|parser
operator|.
name|next
argument_list|()
expr_stmt|;
block|}
name|parser
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
return|return
literal|null
return|;
block|}
return|return
name|res
return|;
block|}
annotation|@
name|Override
DECL|method|getFormatName ()
specifier|public
name|String
name|getFormatName
parameter_list|()
block|{
return|return
literal|"text citations"
return|;
block|}
block|}
end_class

end_unit

