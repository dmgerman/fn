begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|BufferedInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileInputStream
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
name|nio
operator|.
name|file
operator|.
name|Path
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
name|Objects
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
name|ParserResult
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

begin_comment
comment|/**  * Role of an importer for JabRef.  */
end_comment

begin_class
DECL|class|ImportFormat
specifier|public
specifier|abstract
class|class
name|ImportFormat
implements|implements
name|Comparable
argument_list|<
name|ImportFormat
argument_list|>
block|{
comment|/**      * Using this when I have no database open or when I read      * non bibtex file formats (used by the ImportFormatReader.java)      *      * TODO: Is this field really needed or would calling IdGenerator.next() suffice?      */
DECL|field|DEFAULT_BIBTEXENTRY_ID
specifier|public
specifier|static
specifier|final
name|String
name|DEFAULT_BIBTEXENTRY_ID
init|=
literal|"__ID"
decl_stmt|;
comment|/**      * Check whether the source is in the correct format for this importer.      *      * The effect of this method is primarily to avoid unnecessary processing of      * files when searching for a suitable import format. If this method returns      * false, the import routine will move on to the next import format.      *      * Thus the correct behaviour is to return false if it is certain that the file is      * not of the suitable type, and true otherwise. Returning true is the safe choice if not certain.      */
DECL|method|isRecognizedFormat (InputStream in)
specifier|public
specifier|abstract
name|boolean
name|isRecognizedFormat
parameter_list|(
name|InputStream
name|in
parameter_list|)
throws|throws
name|IOException
function_decl|;
DECL|method|isRecognizedFormat (Path filePath)
specifier|public
name|boolean
name|isRecognizedFormat
parameter_list|(
name|Path
name|filePath
parameter_list|)
throws|throws
name|IOException
block|{
try|try
init|(
name|InputStream
name|stream
init|=
operator|new
name|FileInputStream
argument_list|(
name|filePath
operator|.
name|toFile
argument_list|()
argument_list|)
init|;
name|BufferedInputStream
name|bufferedStream
operator|=
operator|new
name|BufferedInputStream
argument_list|(
name|stream
argument_list|)
init|)
block|{
name|bufferedStream
operator|.
name|mark
argument_list|(
name|Integer
operator|.
name|MAX_VALUE
argument_list|)
expr_stmt|;
return|return
name|isRecognizedFormat
argument_list|(
name|bufferedStream
argument_list|)
return|;
block|}
block|}
comment|/**      * Parse the database in the source.      *      * This method can be called in two different contexts - either when importing in      * a specified format, or when importing in unknown format. In the latter case,      * JabRef cycles through all available import formats. No error messages or feedback      * is displayed from individual import formats in this case.      *      * If importing in a specified format and an empty database is returned, JabRef reports      * that no entries were found.      *      * This method should never return null.      *      * @param in the input stream to read from      */
DECL|method|importDatabase (InputStream in)
specifier|public
specifier|abstract
name|ParserResult
name|importDatabase
parameter_list|(
name|InputStream
name|in
parameter_list|)
throws|throws
name|IOException
function_decl|;
DECL|method|importDatabase (Path filePath)
specifier|public
name|ParserResult
name|importDatabase
parameter_list|(
name|Path
name|filePath
parameter_list|)
throws|throws
name|IOException
block|{
try|try
init|(
name|InputStream
name|stream
init|=
operator|new
name|FileInputStream
argument_list|(
name|filePath
operator|.
name|toFile
argument_list|()
argument_list|)
init|;
name|BufferedInputStream
name|bufferedStream
operator|=
operator|new
name|BufferedInputStream
argument_list|(
name|stream
argument_list|)
init|)
block|{
return|return
name|importDatabase
argument_list|(
name|bufferedStream
argument_list|)
return|;
block|}
block|}
comment|/**      * Returns the name of this import format.      *      *<p>The name must be unique.</p>      *      * @return format name, must be unique and not<code>null</code>      */
DECL|method|getFormatName ()
specifier|public
specifier|abstract
name|String
name|getFormatName
parameter_list|()
function_decl|;
comment|/**      * Returns the file extensions that this importer can read.      * The extension should contain the leading dot, so for example ".bib"      *      * @return list of supported file extensions (not null but may be empty)      */
DECL|method|getExtensions ()
specifier|public
specifier|abstract
name|List
argument_list|<
name|String
argument_list|>
name|getExtensions
parameter_list|()
function_decl|;
comment|/**      * Returns a one-word ID which identifies this import format.      * Used for example, to identify the format when used from the command line.      *      * @return ID, must be unique and not<code>null</code>      */
DECL|method|getId ()
specifier|public
name|String
name|getId
parameter_list|()
block|{
name|String
name|id
init|=
name|getFormatName
argument_list|()
decl_stmt|;
name|StringBuilder
name|result
init|=
operator|new
name|StringBuilder
argument_list|(
name|id
operator|.
name|length
argument_list|()
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
name|id
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|char
name|c
init|=
name|id
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|Character
operator|.
name|isLetterOrDigit
argument_list|(
name|c
argument_list|)
condition|)
block|{
name|result
operator|.
name|append
argument_list|(
name|Character
operator|.
name|toLowerCase
argument_list|(
name|c
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|result
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Returns the description of the import format.      *      * The description should specify      *<ul><li>      *   what kind of entries from what sources and based on what specification it is able to import      *</li><li>      *   by what criteria it {@link #isRecognizedFormat(InputStream) recognizes} an import format      *</li></ul>      *      * @return description of the import format      */
DECL|method|getDescription ()
specifier|public
specifier|abstract
name|String
name|getDescription
parameter_list|()
function_decl|;
annotation|@
name|Override
DECL|method|hashCode ()
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|getFormatName
argument_list|()
operator|.
name|hashCode
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|equals (Object obj)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
if|if
condition|(
name|obj
operator|==
literal|null
condition|)
block|{
return|return
literal|false
return|;
block|}
if|if
condition|(
operator|!
operator|(
name|obj
operator|instanceof
name|ImportFormat
operator|)
condition|)
block|{
return|return
literal|false
return|;
block|}
name|ImportFormat
name|other
init|=
operator|(
name|ImportFormat
operator|)
name|obj
decl_stmt|;
return|return
name|Objects
operator|.
name|equals
argument_list|(
name|this
operator|.
name|getFormatName
argument_list|()
argument_list|,
name|other
operator|.
name|getFormatName
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getFormatName
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|compareTo (ImportFormat o)
specifier|public
name|int
name|compareTo
parameter_list|(
name|ImportFormat
name|o
parameter_list|)
block|{
return|return
name|getFormatName
argument_list|()
operator|.
name|compareTo
argument_list|(
name|o
operator|.
name|getFormatName
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

