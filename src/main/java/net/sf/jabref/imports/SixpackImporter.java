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
name|*
import|;
end_import

begin_comment
comment|/**  * Imports a Biblioscape Tag File. The format is described on  * http://www.biblioscape.com/manual_bsp/Biblioscape_Tag_File.htm Several  * Biblioscape field types are ignored. Others are only included in the BibTeX  * field "comment".  */
end_comment

begin_class
DECL|class|SixpackImporter
specifier|public
class|class
name|SixpackImporter
extends|extends
name|ImportFormat
block|{
DECL|field|SEPARATOR
specifier|private
specifier|final
name|String
name|SEPARATOR
init|=
operator|new
name|String
argument_list|(
operator|new
name|char
index|[]
block|{
literal|0
block|,
literal|48
block|}
argument_list|)
decl_stmt|;
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
name|SixpackImporter
operator|.
name|class
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
literal|"Sixpack"
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
literal|"sixpack"
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
name|str
operator|=
name|in
operator|.
name|readLine
argument_list|()
operator|)
operator|!=
literal|null
operator|&&
name|i
operator|<
literal|50
condition|)
block|{
if|if
condition|(
name|str
operator|.
name|contains
argument_list|(
name|SEPARATOR
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
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|fI
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
name|fI
operator|.
name|put
argument_list|(
literal|"id"
argument_list|,
literal|"bibtexkey"
argument_list|)
expr_stmt|;
name|fI
operator|.
name|put
argument_list|(
literal|"au"
argument_list|,
literal|"author"
argument_list|)
expr_stmt|;
name|fI
operator|.
name|put
argument_list|(
literal|"ti"
argument_list|,
literal|"title"
argument_list|)
expr_stmt|;
name|fI
operator|.
name|put
argument_list|(
literal|"jo"
argument_list|,
literal|"journal"
argument_list|)
expr_stmt|;
name|fI
operator|.
name|put
argument_list|(
literal|"vo"
argument_list|,
literal|"volume"
argument_list|)
expr_stmt|;
name|fI
operator|.
name|put
argument_list|(
literal|"nu"
argument_list|,
literal|"number"
argument_list|)
expr_stmt|;
name|fI
operator|.
name|put
argument_list|(
literal|"pa"
argument_list|,
literal|"pages"
argument_list|)
expr_stmt|;
name|fI
operator|.
name|put
argument_list|(
literal|"mo"
argument_list|,
literal|"month"
argument_list|)
expr_stmt|;
name|fI
operator|.
name|put
argument_list|(
literal|"yr"
argument_list|,
literal|"year"
argument_list|)
expr_stmt|;
name|fI
operator|.
name|put
argument_list|(
literal|"kw"
argument_list|,
literal|"keywords"
argument_list|)
expr_stmt|;
name|fI
operator|.
name|put
argument_list|(
literal|"ab"
argument_list|,
literal|"abstract"
argument_list|)
expr_stmt|;
name|fI
operator|.
name|put
argument_list|(
literal|"no"
argument_list|,
literal|"note"
argument_list|)
expr_stmt|;
name|fI
operator|.
name|put
argument_list|(
literal|"ed"
argument_list|,
literal|"editor"
argument_list|)
expr_stmt|;
name|fI
operator|.
name|put
argument_list|(
literal|"pu"
argument_list|,
literal|"publisher"
argument_list|)
expr_stmt|;
name|fI
operator|.
name|put
argument_list|(
literal|"se"
argument_list|,
literal|"series"
argument_list|)
expr_stmt|;
name|fI
operator|.
name|put
argument_list|(
literal|"ad"
argument_list|,
literal|"address"
argument_list|)
expr_stmt|;
name|fI
operator|.
name|put
argument_list|(
literal|"en"
argument_list|,
literal|"edition"
argument_list|)
expr_stmt|;
name|fI
operator|.
name|put
argument_list|(
literal|"ch"
argument_list|,
literal|"chapter"
argument_list|)
expr_stmt|;
name|fI
operator|.
name|put
argument_list|(
literal|"hp"
argument_list|,
literal|"howpublished"
argument_list|)
expr_stmt|;
name|fI
operator|.
name|put
argument_list|(
literal|"tb"
argument_list|,
literal|"booktitle"
argument_list|)
expr_stmt|;
name|fI
operator|.
name|put
argument_list|(
literal|"or"
argument_list|,
literal|"organization"
argument_list|)
expr_stmt|;
name|fI
operator|.
name|put
argument_list|(
literal|"sc"
argument_list|,
literal|"school"
argument_list|)
expr_stmt|;
name|fI
operator|.
name|put
argument_list|(
literal|"in"
argument_list|,
literal|"institution"
argument_list|)
expr_stmt|;
name|fI
operator|.
name|put
argument_list|(
literal|"ty"
argument_list|,
literal|"type"
argument_list|)
expr_stmt|;
name|fI
operator|.
name|put
argument_list|(
literal|"url"
argument_list|,
literal|"url"
argument_list|)
expr_stmt|;
name|fI
operator|.
name|put
argument_list|(
literal|"cr"
argument_list|,
literal|"crossref"
argument_list|)
expr_stmt|;
name|fI
operator|.
name|put
argument_list|(
literal|"fi"
argument_list|,
literal|"file"
argument_list|)
expr_stmt|;
name|ArrayList
argument_list|<
name|BibtexEntry
argument_list|>
name|bibitems
init|=
operator|new
name|ArrayList
argument_list|<>
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
name|in
operator|.
name|readLine
argument_list|()
expr_stmt|;
name|String
name|ln
init|=
name|in
operator|.
name|readLine
argument_list|()
decl_stmt|;
if|if
condition|(
name|ln
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
name|String
index|[]
name|fieldDef
init|=
name|ln
operator|.
name|split
argument_list|(
literal|","
argument_list|)
decl_stmt|;
name|String
name|s
decl_stmt|;
name|BibtexEntry
name|entry
decl_stmt|;
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
try|try
block|{
name|s
operator|=
name|s
operator|.
name|replaceAll
argument_list|(
literal|"<par>"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
comment|// What is<par> ????
name|String
index|[]
name|fields
init|=
name|s
operator|.
name|split
argument_list|(
name|SEPARATOR
argument_list|)
decl_stmt|;
comment|// Check type and create entry:
if|if
condition|(
name|fields
operator|.
name|length
operator|<
literal|2
condition|)
block|{
continue|continue;
comment|// Avoid ArrayIndexOutOfBoundsException
block|}
name|BibtexEntryType
name|typ
init|=
name|BibtexEntryType
operator|.
name|getType
argument_list|(
name|fields
index|[
literal|1
index|]
operator|.
name|toLowerCase
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|typ
operator|==
literal|null
condition|)
block|{
name|String
name|type
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|fields
index|[
literal|1
index|]
operator|.
name|equals
argument_list|(
literal|"Masterthesis"
argument_list|)
condition|)
block|{
name|type
operator|=
literal|"mastersthesis"
expr_stmt|;
block|}
if|if
condition|(
name|fields
index|[
literal|1
index|]
operator|.
name|equals
argument_list|(
literal|"PhD-Thesis"
argument_list|)
condition|)
block|{
name|type
operator|=
literal|"phdthesis"
expr_stmt|;
block|}
if|if
condition|(
name|fields
index|[
literal|1
index|]
operator|.
name|equals
argument_list|(
literal|"miscellaneous"
argument_list|)
condition|)
block|{
name|type
operator|=
literal|"misc"
expr_stmt|;
block|}
if|if
condition|(
name|fields
index|[
literal|1
index|]
operator|.
name|equals
argument_list|(
literal|"Conference"
argument_list|)
condition|)
block|{
name|type
operator|=
literal|"proceedings"
expr_stmt|;
block|}
name|typ
operator|=
name|BibtexEntryType
operator|.
name|getType
argument_list|(
name|type
operator|.
name|toLowerCase
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|entry
operator|=
operator|new
name|BibtexEntry
argument_list|(
name|IdGenerator
operator|.
name|next
argument_list|()
argument_list|,
name|typ
argument_list|)
expr_stmt|;
name|String
name|fld
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
name|Math
operator|.
name|min
argument_list|(
name|fieldDef
operator|.
name|length
argument_list|,
name|fields
operator|.
name|length
argument_list|)
condition|;
name|i
operator|++
control|)
block|{
name|fld
operator|=
name|fI
operator|.
name|get
argument_list|(
name|fieldDef
index|[
name|i
index|]
argument_list|)
expr_stmt|;
if|if
condition|(
name|fld
operator|!=
literal|null
condition|)
block|{
switch|switch
condition|(
name|fld
condition|)
block|{
case|case
literal|"author"
case|:
case|case
literal|"editor"
case|:
name|ImportFormatReader
operator|.
name|setIfNecessary
argument_list|(
name|entry
argument_list|,
name|fld
argument_list|,
name|fields
index|[
name|i
index|]
operator|.
name|replaceAll
argument_list|(
literal|" and "
argument_list|,
literal|", "
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|", "
argument_list|,
literal|" and "
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
literal|"pages"
case|:
name|ImportFormatReader
operator|.
name|setIfNecessary
argument_list|(
name|entry
argument_list|,
name|fld
argument_list|,
name|fields
index|[
name|i
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
break|break;
case|case
literal|"file"
case|:
name|String
name|fieldName
init|=
literal|"pdf"
decl_stmt|;
comment|// We set pdf as default.
if|if
condition|(
name|fields
index|[
name|i
index|]
operator|.
name|endsWith
argument_list|(
literal|"ps"
argument_list|)
operator|||
name|fields
index|[
name|i
index|]
operator|.
name|endsWith
argument_list|(
literal|"ps.gz"
argument_list|)
condition|)
block|{
name|fieldName
operator|=
literal|"ps"
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|fields
index|[
name|i
index|]
operator|.
name|endsWith
argument_list|(
literal|"html"
argument_list|)
condition|)
block|{
name|fieldName
operator|=
literal|"url"
expr_stmt|;
block|}
name|ImportFormatReader
operator|.
name|setIfNecessary
argument_list|(
name|entry
argument_list|,
name|fieldName
argument_list|,
name|fields
index|[
name|i
index|]
argument_list|)
expr_stmt|;
break|break;
default|default:
name|ImportFormatReader
operator|.
name|setIfNecessary
argument_list|(
name|entry
argument_list|,
name|fld
argument_list|,
name|fields
index|[
name|i
index|]
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
block|}
name|bibitems
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NullPointerException
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Problem parsing Sixpack entry, ignoring entry."
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|bibitems
return|;
block|}
block|}
end_class

end_unit

