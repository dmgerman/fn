begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.imports
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
package|;
end_package

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
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
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
name|AuthorList
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
name|BibtexFields
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
name|OutputPrinter
import|;
end_import

begin_comment
comment|/**  * Imports a Biblioscape Tag File. The format is described on  * http://www.biblioscape.com/manual_bsp/Biblioscape_Tag_File.htm Several  * Biblioscape field types are ignored. Others are only included in the BibTeX  * field "comment".  */
end_comment

begin_class
DECL|class|ScifinderImporter
specifier|public
class|class
name|ScifinderImporter
extends|extends
name|ImportFormat
block|{
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
literal|"Scifinder"
return|;
block|}
comment|/*      *  (non-Javadoc)      * @see net.sf.jabref.imports.ImportFormat#getCLIId()      */
annotation|@
name|Override
DECL|method|getCLIId ()
specifier|public
name|String
name|getCLIId
parameter_list|()
block|{
return|return
literal|"scifinder"
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
name|int
name|i
init|=
literal|0
decl_stmt|;
while|while
condition|(
operator|(
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
operator|)
operator|&&
operator|(
name|i
operator|<
literal|50
operator|)
condition|)
block|{
if|if
condition|(
name|str
operator|.
name|trim
argument_list|()
operator|.
name|equals
argument_list|(
literal|"START_RECORD"
argument_list|)
condition|)
block|{
return|return
literal|true
return|;
block|}
name|i
operator|++
expr_stmt|;
block|}
return|return
literal|false
return|;
block|}
comment|/**      * Parse the entries in the source, and return a List of BibtexEntry      * objects.      */
annotation|@
name|Override
DECL|method|importEntries (InputStream stream, OutputPrinter status)
specifier|public
name|List
argument_list|<
name|BibtexEntry
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
name|ArrayList
argument_list|<
name|BibtexEntry
argument_list|>
name|bibitems
init|=
operator|new
name|ArrayList
argument_list|<
name|BibtexEntry
argument_list|>
argument_list|()
decl_stmt|;
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
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
name|String
name|number
init|=
literal|""
decl_stmt|;
name|String
name|country
init|=
literal|""
decl_stmt|;
name|String
name|kindcode
init|=
literal|""
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
name|sb
operator|.
name|append
argument_list|(
name|str
argument_list|)
expr_stmt|;
block|}
name|String
index|[]
name|entries
init|=
name|sb
operator|.
name|toString
argument_list|()
operator|.
name|split
argument_list|(
literal|"START_RECORD"
argument_list|)
decl_stmt|;
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|hm
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<
name|entries
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|String
index|[]
name|fields
init|=
name|entries
index|[
name|i
index|]
operator|.
name|split
argument_list|(
literal|"FIELD "
argument_list|)
decl_stmt|;
name|String
name|journal
init|=
literal|null
decl_stmt|;
name|String
name|Type
init|=
literal|""
decl_stmt|;
name|hm
operator|.
name|clear
argument_list|()
expr_stmt|;
comment|// reset
for|for
control|(
name|String
name|field
range|:
name|fields
control|)
block|{
if|if
condition|(
name|field
operator|.
name|contains
argument_list|(
literal|":"
argument_list|)
condition|)
block|{
name|String
name|tmp
index|[]
init|=
operator|new
name|String
index|[
literal|2
index|]
decl_stmt|;
name|tmp
index|[
literal|0
index|]
operator|=
name|field
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|field
operator|.
name|indexOf
argument_list|(
literal|":"
argument_list|)
argument_list|)
expr_stmt|;
name|tmp
index|[
literal|1
index|]
operator|=
name|field
operator|.
name|substring
argument_list|(
name|field
operator|.
name|indexOf
argument_list|(
literal|":"
argument_list|)
operator|+
literal|1
argument_list|)
operator|.
name|trim
argument_list|()
expr_stmt|;
if|if
condition|(
name|tmp
operator|.
name|length
operator|>
literal|1
condition|)
block|{
comment|//==2
if|if
condition|(
name|tmp
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
literal|"Author"
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"author"
argument_list|,
name|AuthorList
operator|.
name|fixAuthor_lastNameFirst
argument_list|(
name|tmp
index|[
literal|1
index|]
operator|.
name|replaceAll
argument_list|(
literal|";"
argument_list|,
literal|" and "
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|tmp
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
literal|"Title"
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"title"
argument_list|,
name|tmp
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|tmp
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
literal|"Journal Title"
argument_list|)
condition|)
block|{
name|journal
operator|=
name|tmp
index|[
literal|1
index|]
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|tmp
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
literal|"Volume"
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"volume"
argument_list|,
name|tmp
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|tmp
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
literal|"Page"
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"pages"
argument_list|,
name|tmp
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|tmp
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
literal|"Publication Year"
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"year"
argument_list|,
name|tmp
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|tmp
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
literal|"Abstract"
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"abstract"
argument_list|,
name|tmp
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|tmp
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
literal|"Supplementary Terms"
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"keywords"
argument_list|,
name|tmp
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|tmp
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
literal|"Inventor Name"
argument_list|)
operator|&&
operator|(
name|tmp
index|[
literal|1
index|]
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
operator|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"author"
argument_list|,
name|AuthorList
operator|.
name|fixAuthor_lastNameFirst
argument_list|(
name|tmp
index|[
literal|1
index|]
operator|.
name|replaceAll
argument_list|(
literal|";"
argument_list|,
literal|" and "
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|tmp
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
literal|"Patent Assignee"
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"institution"
argument_list|,
name|tmp
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|tmp
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
literal|"Patent Kind Code"
argument_list|)
condition|)
block|{
name|kindcode
operator|=
literal|" "
operator|+
name|tmp
index|[
literal|1
index|]
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|tmp
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
literal|"Patent Country"
argument_list|)
condition|)
block|{
name|country
operator|=
name|tmp
index|[
literal|1
index|]
operator|+
literal|" "
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|tmp
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
literal|"Patent Number"
argument_list|)
condition|)
block|{
name|number
operator|=
name|tmp
index|[
literal|1
index|]
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|tmp
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
literal|"Priority Application Date"
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"number"
argument_list|,
name|country
operator|+
name|number
operator|+
name|kindcode
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|tmp
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
literal|"Document Type"
argument_list|)
condition|)
block|{
if|if
condition|(
name|tmp
index|[
literal|1
index|]
operator|.
name|startsWith
argument_list|(
literal|"Journal"
argument_list|)
operator|||
name|tmp
index|[
literal|1
index|]
operator|.
name|startsWith
argument_list|(
literal|"Review"
argument_list|)
condition|)
block|{
name|Type
operator|=
literal|"article"
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|tmp
index|[
literal|1
index|]
operator|.
name|equals
argument_list|(
literal|"Dissertation"
argument_list|)
condition|)
block|{
name|Type
operator|=
literal|"phdthesis"
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|tmp
index|[
literal|1
index|]
operator|.
name|equals
argument_list|(
literal|"Patent"
argument_list|)
condition|)
block|{
name|Type
operator|=
literal|"patent"
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|tmp
index|[
literal|1
index|]
operator|.
name|startsWith
argument_list|(
literal|"Conference"
argument_list|)
condition|)
block|{
name|Type
operator|=
literal|"conference"
expr_stmt|;
block|}
else|else
block|{
name|Type
operator|=
name|tmp
index|[
literal|1
index|]
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
name|BibtexEntry
name|b
init|=
operator|new
name|BibtexEntry
argument_list|(
name|BibtexFields
operator|.
name|DEFAULT_BIBTEXENTRY_ID
argument_list|,
name|Globals
operator|.
name|getEntryType
argument_list|(
name|Type
argument_list|)
argument_list|)
decl_stmt|;
comment|// id assumes an existing database so don't
comment|// create one here
name|b
operator|.
name|setField
argument_list|(
name|hm
argument_list|)
expr_stmt|;
if|if
condition|(
name|journal
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|Type
operator|.
name|equals
argument_list|(
literal|"conference"
argument_list|)
condition|)
block|{
name|b
operator|.
name|setField
argument_list|(
literal|"booktitle"
argument_list|,
name|journal
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|b
operator|.
name|setField
argument_list|(
literal|"journal"
argument_list|,
name|journal
argument_list|)
expr_stmt|;
block|}
block|}
name|bibitems
operator|.
name|add
argument_list|(
name|b
argument_list|)
expr_stmt|;
block|}
return|return
name|bibitems
return|;
block|}
block|}
end_class

end_unit

