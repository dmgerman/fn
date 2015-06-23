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
name|BibtexEntryType
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

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|Util
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

begin_comment
comment|/**  * Imports a Biblioscape Tag File. The format is described on  * http://www.biblioscape.com/manual_bsp/Biblioscape_Tag_File.htm Several  * Biblioscape field types are ignored. Others are only included in the BibTeX  * field "comment".  */
end_comment

begin_class
DECL|class|JstorImporter
specifier|public
class|class
name|JstorImporter
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
literal|"JStor (tab delimited)"
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
literal|"jstor"
return|;
block|}
comment|/**      * Check whether the source is in the correct format for this importer.      */
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
return|return
literal|true
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
name|String
name|s
init|=
literal|""
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
while|while
condition|(
operator|(
name|s
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|s
operator|.
name|startsWith
argument_list|(
literal|"Item Type"
argument_list|)
condition|)
block|{
name|s
operator|=
name|in
operator|.
name|readLine
argument_list|()
expr_stmt|;
block|}
while|while
condition|(
operator|(
name|s
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
name|s
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
continue|continue;
block|}
if|if
condition|(
name|s
operator|.
name|startsWith
argument_list|(
literal|"-----------------------------"
argument_list|)
condition|)
block|{
break|break;
block|}
name|String
index|[]
name|fields
init|=
name|s
operator|.
name|split
argument_list|(
literal|"\t"
argument_list|)
decl_stmt|;
name|BibtexEntry
name|be
init|=
operator|new
name|BibtexEntry
argument_list|(
name|Util
operator|.
name|createNeutralId
argument_list|()
argument_list|)
decl_stmt|;
try|try
block|{
if|if
condition|(
name|fields
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
literal|"FLA"
argument_list|)
condition|)
block|{
name|be
operator|.
name|setType
argument_list|(
name|BibtexEntryType
operator|.
name|getType
argument_list|(
literal|"article"
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|ImportFormatReader
operator|.
name|setIfNecessary
argument_list|(
name|be
argument_list|,
literal|"title"
argument_list|,
name|fields
index|[
literal|2
index|]
argument_list|)
expr_stmt|;
name|ImportFormatReader
operator|.
name|setIfNecessary
argument_list|(
name|be
argument_list|,
literal|"author"
argument_list|,
name|AuthorList
operator|.
name|fixAuthor_lastNameFirst
argument_list|(
name|fields
index|[
literal|4
index|]
operator|.
name|replaceAll
argument_list|(
literal|"; "
argument_list|,
literal|" and "
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|ImportFormatReader
operator|.
name|setIfNecessary
argument_list|(
name|be
argument_list|,
literal|"journal"
argument_list|,
name|fields
index|[
literal|7
index|]
argument_list|)
expr_stmt|;
name|ImportFormatReader
operator|.
name|setIfNecessary
argument_list|(
name|be
argument_list|,
literal|"volume"
argument_list|,
name|fields
index|[
literal|9
index|]
argument_list|)
expr_stmt|;
name|ImportFormatReader
operator|.
name|setIfNecessary
argument_list|(
name|be
argument_list|,
literal|"number"
argument_list|,
name|fields
index|[
literal|10
index|]
argument_list|)
expr_stmt|;
name|String
index|[]
name|datefield
init|=
name|fields
index|[
literal|12
index|]
operator|.
name|split
argument_list|(
literal|" "
argument_list|)
decl_stmt|;
name|ImportFormatReader
operator|.
name|setIfNecessary
argument_list|(
name|be
argument_list|,
literal|"year"
argument_list|,
name|datefield
index|[
name|datefield
operator|.
name|length
operator|-
literal|1
index|]
argument_list|)
expr_stmt|;
if|if
condition|(
name|datefield
operator|.
name|length
operator|>
literal|1
condition|)
block|{
if|if
condition|(
name|datefield
index|[
literal|0
index|]
operator|.
name|endsWith
argument_list|(
literal|","
argument_list|)
condition|)
block|{
name|datefield
index|[
literal|0
index|]
operator|=
name|datefield
index|[
literal|0
index|]
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|datefield
index|[
literal|0
index|]
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
name|ImportFormatReader
operator|.
name|setIfNecessary
argument_list|(
name|be
argument_list|,
literal|"month"
argument_list|,
name|datefield
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
block|}
comment|//for (int i=0; i<fields.length; i++)
comment|//  Util.pr(i+": "+fields[i]);
name|ImportFormatReader
operator|.
name|setIfNecessary
argument_list|(
name|be
argument_list|,
literal|"pages"
argument_list|,
name|fields
index|[
literal|13
index|]
operator|.
name|replaceAll
argument_list|(
literal|"-"
argument_list|,
literal|"--"
argument_list|)
argument_list|)
expr_stmt|;
name|ImportFormatReader
operator|.
name|setIfNecessary
argument_list|(
name|be
argument_list|,
literal|"url"
argument_list|,
name|fields
index|[
literal|14
index|]
argument_list|)
expr_stmt|;
name|ImportFormatReader
operator|.
name|setIfNecessary
argument_list|(
name|be
argument_list|,
literal|"issn"
argument_list|,
name|fields
index|[
literal|15
index|]
argument_list|)
expr_stmt|;
name|ImportFormatReader
operator|.
name|setIfNecessary
argument_list|(
name|be
argument_list|,
literal|"abstract"
argument_list|,
name|fields
index|[
literal|16
index|]
argument_list|)
expr_stmt|;
name|ImportFormatReader
operator|.
name|setIfNecessary
argument_list|(
name|be
argument_list|,
literal|"keywords"
argument_list|,
name|fields
index|[
literal|17
index|]
argument_list|)
expr_stmt|;
name|ImportFormatReader
operator|.
name|setIfNecessary
argument_list|(
name|be
argument_list|,
literal|"copyright"
argument_list|,
name|fields
index|[
literal|21
index|]
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ArrayIndexOutOfBoundsException
name|ignored
parameter_list|)
block|{             }
name|bibitems
operator|.
name|add
argument_list|(
name|be
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

