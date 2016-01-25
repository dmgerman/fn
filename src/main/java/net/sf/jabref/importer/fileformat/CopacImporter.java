begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
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
name|BufferedReader
import|;
end_import

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
name|util
operator|.
name|LinkedList
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
name|regex
operator|.
name|Pattern
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
name|ImportFormatReader
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
name|BibtexEntryTypes
import|;
end_import

begin_comment
comment|/**  * Importer for COPAC format.  *  * Documentation can be found online at:  *  * http://copac.ac.uk/faq/#format  */
end_comment

begin_class
DECL|class|CopacImporter
specifier|public
class|class
name|CopacImporter
extends|extends
name|ImportFormat
block|{
DECL|field|COPAC_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|COPAC_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"^\\s*TI- "
argument_list|)
decl_stmt|;
comment|/**      * Return the name of this import format.      */
annotation|@
name|Override
DECL|method|getFormatName ()
specifier|public
name|String
name|getFormatName
parameter_list|()
block|{
return|return
literal|"Copac"
return|;
block|}
comment|/*      * (non-Javadoc)      *      * @see net.sf.jabref.imports.ImportFormat#getCLIId()      */
annotation|@
name|Override
DECL|method|getCLIId ()
specifier|public
name|String
name|getCLIId
parameter_list|()
block|{
return|return
literal|"cpc"
return|;
block|}
comment|/**      * Check whether the source is in the correct format for this importer.      */
annotation|@
name|Override
DECL|method|isRecognizedFormat (InputStream stream)
specifier|public
name|boolean
name|isRecognizedFormat
parameter_list|(
name|InputStream
name|stream
parameter_list|)
throws|throws
name|IOException
block|{
name|BufferedReader
name|in
init|=
operator|new
name|BufferedReader
argument_list|(
name|ImportFormatReader
operator|.
name|getReaderDefaultEncoding
argument_list|(
name|stream
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|str
decl_stmt|;
while|while
condition|(
operator|(
name|str
operator|=
name|in
operator|.
name|readLine
argument_list|()
operator|)
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|CopacImporter
operator|.
name|COPAC_PATTERN
operator|.
name|matcher
argument_list|(
name|str
argument_list|)
operator|.
name|find
argument_list|()
condition|)
block|{
return|return
literal|true
return|;
block|}
block|}
return|return
literal|false
return|;
block|}
comment|/**      * Parse the entries in the source, and return a List of BibEntry      * objects.      */
annotation|@
name|Override
DECL|method|importEntries (InputStream stream, OutputPrinter status)
specifier|public
name|List
argument_list|<
name|BibEntry
argument_list|>
name|importEntries
parameter_list|(
name|InputStream
name|stream
parameter_list|,
name|OutputPrinter
name|status
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
name|stream
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
literal|"No stream given."
argument_list|)
throw|;
block|}
name|BufferedReader
name|in
init|=
operator|new
name|BufferedReader
argument_list|(
name|ImportFormatReader
operator|.
name|getReaderDefaultEncoding
argument_list|(
name|stream
argument_list|)
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|entries
init|=
operator|new
name|LinkedList
argument_list|<>
argument_list|()
decl_stmt|;
comment|// Preprocess entries
name|String
name|str
decl_stmt|;
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
while|while
condition|(
operator|(
name|str
operator|=
name|in
operator|.
name|readLine
argument_list|()
operator|)
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|str
operator|.
name|length
argument_list|()
operator|<
literal|4
condition|)
block|{
continue|continue;
block|}
name|String
name|code
init|=
name|str
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|4
argument_list|)
decl_stmt|;
if|if
condition|(
literal|"    "
operator|.
name|equals
argument_list|(
name|code
argument_list|)
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
operator|.
name|append
argument_list|(
name|str
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// begining of a new item
if|if
condition|(
literal|"TI- "
operator|.
name|equals
argument_list|(
name|str
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|4
argument_list|)
argument_list|)
condition|)
block|{
if|if
condition|(
name|sb
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|entries
operator|.
name|add
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|sb
operator|=
operator|new
name|StringBuffer
argument_list|()
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
literal|'\n'
argument_list|)
operator|.
name|append
argument_list|(
name|str
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|sb
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|entries
operator|.
name|add
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|List
argument_list|<
name|BibEntry
argument_list|>
name|results
init|=
operator|new
name|LinkedList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|entry
range|:
name|entries
control|)
block|{
comment|// Copac does not contain enough information on the type of the
comment|// document. A book is assumed.
name|BibEntry
name|b
init|=
operator|new
name|BibEntry
argument_list|(
name|DEFAULT_BIBTEXENTRY_ID
argument_list|,
name|BibtexEntryTypes
operator|.
name|BOOK
argument_list|)
decl_stmt|;
name|String
index|[]
name|lines
init|=
name|entry
operator|.
name|split
argument_list|(
literal|"\n"
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|line1
range|:
name|lines
control|)
block|{
name|String
name|line
init|=
name|line1
operator|.
name|trim
argument_list|()
decl_stmt|;
if|if
condition|(
name|line
operator|.
name|length
argument_list|()
operator|<
literal|4
condition|)
block|{
continue|continue;
block|}
name|String
name|code
init|=
name|line
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|4
argument_list|)
decl_stmt|;
if|if
condition|(
literal|"TI- "
operator|.
name|equals
argument_list|(
name|code
argument_list|)
condition|)
block|{
name|setOrAppend
argument_list|(
name|b
argument_list|,
literal|"title"
argument_list|,
name|line
operator|.
name|substring
argument_list|(
literal|4
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|,
literal|", "
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"AU- "
operator|.
name|equals
argument_list|(
name|code
argument_list|)
condition|)
block|{
name|setOrAppend
argument_list|(
name|b
argument_list|,
literal|"author"
argument_list|,
name|line
operator|.
name|substring
argument_list|(
literal|4
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|,
literal|" and "
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"PY- "
operator|.
name|equals
argument_list|(
name|code
argument_list|)
condition|)
block|{
name|setOrAppend
argument_list|(
name|b
argument_list|,
literal|"year"
argument_list|,
name|line
operator|.
name|substring
argument_list|(
literal|4
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|,
literal|", "
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"PU- "
operator|.
name|equals
argument_list|(
name|code
argument_list|)
condition|)
block|{
name|setOrAppend
argument_list|(
name|b
argument_list|,
literal|"publisher"
argument_list|,
name|line
operator|.
name|substring
argument_list|(
literal|4
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|,
literal|", "
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"SE- "
operator|.
name|equals
argument_list|(
name|code
argument_list|)
condition|)
block|{
name|setOrAppend
argument_list|(
name|b
argument_list|,
literal|"series"
argument_list|,
name|line
operator|.
name|substring
argument_list|(
literal|4
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|,
literal|", "
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"IS- "
operator|.
name|equals
argument_list|(
name|code
argument_list|)
condition|)
block|{
name|setOrAppend
argument_list|(
name|b
argument_list|,
literal|"isbn"
argument_list|,
name|line
operator|.
name|substring
argument_list|(
literal|4
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|,
literal|", "
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"KW- "
operator|.
name|equals
argument_list|(
name|code
argument_list|)
condition|)
block|{
name|setOrAppend
argument_list|(
name|b
argument_list|,
literal|"keywords"
argument_list|,
name|line
operator|.
name|substring
argument_list|(
literal|4
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|,
literal|", "
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"NT- "
operator|.
name|equals
argument_list|(
name|code
argument_list|)
condition|)
block|{
name|setOrAppend
argument_list|(
name|b
argument_list|,
literal|"note"
argument_list|,
name|line
operator|.
name|substring
argument_list|(
literal|4
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|,
literal|", "
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"PD- "
operator|.
name|equals
argument_list|(
name|code
argument_list|)
condition|)
block|{
name|setOrAppend
argument_list|(
name|b
argument_list|,
literal|"physicaldimensions"
argument_list|,
name|line
operator|.
name|substring
argument_list|(
literal|4
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|,
literal|", "
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"DT- "
operator|.
name|equals
argument_list|(
name|code
argument_list|)
condition|)
block|{
name|setOrAppend
argument_list|(
name|b
argument_list|,
literal|"documenttype"
argument_list|,
name|line
operator|.
name|substring
argument_list|(
literal|4
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|,
literal|", "
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|setOrAppend
argument_list|(
name|b
argument_list|,
name|code
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|2
argument_list|)
argument_list|,
name|line
operator|.
name|substring
argument_list|(
literal|4
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|,
literal|", "
argument_list|)
expr_stmt|;
block|}
block|}
name|results
operator|.
name|add
argument_list|(
name|b
argument_list|)
expr_stmt|;
block|}
return|return
name|results
return|;
block|}
DECL|method|setOrAppend (BibEntry b, String field, String value, String separator)
specifier|private
specifier|static
name|void
name|setOrAppend
parameter_list|(
name|BibEntry
name|b
parameter_list|,
name|String
name|field
parameter_list|,
name|String
name|value
parameter_list|,
name|String
name|separator
parameter_list|)
block|{
if|if
condition|(
name|b
operator|.
name|hasField
argument_list|(
name|field
argument_list|)
condition|)
block|{
name|b
operator|.
name|setField
argument_list|(
name|field
argument_list|,
name|b
operator|.
name|getField
argument_list|(
name|field
argument_list|)
operator|+
name|separator
operator|+
name|value
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|b
operator|.
name|setField
argument_list|(
name|field
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

