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
name|List
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

begin_comment
comment|/**  * Role of an importer for JabRef.  *   *<p>Importers are sorted according to following criteria  *<ol><li>  *   custom importers come first, then importers shipped with JabRef  *</li><li>  *   then importers are sorted by name.  *</li></ol>  *</p>  */
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
comment|/**      * Using this when I have no database open or when I read      * non bibtex file formats (used by the ImportFormatReader.java)      */
DECL|field|DEFAULT_BIBTEXENTRY_ID
specifier|public
specifier|static
specifier|final
name|String
name|DEFAULT_BIBTEXENTRY_ID
init|=
literal|"__ID"
decl_stmt|;
DECL|field|isCustomImporter
specifier|private
name|boolean
name|isCustomImporter
decl_stmt|;
comment|/**      * Constructor for custom importers.      */
DECL|method|ImportFormat ()
specifier|public
name|ImportFormat
parameter_list|()
block|{
name|this
operator|.
name|isCustomImporter
operator|=
literal|false
expr_stmt|;
block|}
comment|/**      * Check whether the source is in the correct format for this importer.      */
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
comment|/**      * Parse the entries in the source, and return a List of BibEntry      * objects.      *      * This method can be called in two different contexts - either when importing in      * a specified format, or when importing in unknown format. In the latter case,      * JabRef cycles through all available import formats. No error messages or feedback      * is displayed from individual import formats in this case.      *      * If importing in a specified format, and null or an empty list is returned, JabRef reports      * that no entries were found. If an IOException is thrown, JabRef displays the exception's      * message in unmodified form.      *      * TODO the return type should be changed to "ParseResult" as the parser could use a different encoding than the default encoding      */
DECL|method|importEntries (InputStream in, OutputPrinter status)
specifier|public
specifier|abstract
name|List
argument_list|<
name|BibEntry
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
function_decl|;
comment|/**      * Name of this import format.      *       *<p>The name must be unique.</p>      *       * @return format name, must be unique and not<code>null</code>      */
DECL|method|getFormatName ()
specifier|public
specifier|abstract
name|String
name|getFormatName
parameter_list|()
function_decl|;
comment|/**      * Extensions that this importer can read.      *       * @return comma separated list of extensions or<code>null</code> for the default      */
DECL|method|getExtensions ()
specifier|public
name|String
name|getExtensions
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
comment|/**      * Short, one token ID to identify the format from the command line.      *       * @return command line ID      */
DECL|method|getCLIId ()
specifier|public
name|String
name|getCLIId
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
comment|/**      * Description  of the ImportFormat.      *       *<p>Implementors of ImportFormats should override this. Ideally, it should specify      *<ul><li>      *   what kind of entries from what sources and based on what specification it is able to import      *</li><li>      *   by what criteria it {@link #isRecognizedFormat(InputStream) recognizes} an import format      *</li></ul>      *       * @return description of the import format      */
DECL|method|getDescription ()
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
literal|"No description available for "
operator|+
name|getFormatName
argument_list|()
operator|+
literal|"."
return|;
block|}
comment|/**      * Sets if this is a custom importer.      *       *<p>For custom importers added dynamically to JabRef, this will be      * set automatically by JabRef.</p>      *       * @param isCustomImporter if this is a custom importer      */
DECL|method|setIsCustomImporter (boolean isCustomImporter)
specifier|public
specifier|final
name|void
name|setIsCustomImporter
parameter_list|(
name|boolean
name|isCustomImporter
parameter_list|)
block|{
name|this
operator|.
name|isCustomImporter
operator|=
name|isCustomImporter
expr_stmt|;
block|}
comment|/**      * Wether this importer is a custom importer.      *       *<p>Custom importers will have precedence over built-in importers.</p>      *       * @return  wether this is a custom importer      */
DECL|method|getIsCustomImporter ()
specifier|public
specifier|final
name|boolean
name|getIsCustomImporter
parameter_list|()
block|{
return|return
name|this
operator|.
name|isCustomImporter
return|;
block|}
comment|/*      *  (non-Javadoc)      * @see java.lang.Object#hashCode()      */
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
comment|/*      *  (non-Javadoc)      * @see java.lang.Object#equals(java.lang.Object)      */
annotation|@
name|Override
DECL|method|equals (Object o)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
return|return
name|o
operator|!=
literal|null
operator|&&
name|o
operator|instanceof
name|ImportFormat
operator|&&
operator|(
operator|(
name|ImportFormat
operator|)
name|o
operator|)
operator|.
name|getIsCustomImporter
argument_list|()
operator|==
name|getIsCustomImporter
argument_list|()
operator|&&
operator|(
operator|(
name|ImportFormat
operator|)
name|o
operator|)
operator|.
name|getFormatName
argument_list|()
operator|.
name|equals
argument_list|(
name|getFormatName
argument_list|()
argument_list|)
return|;
block|}
comment|/*      *  (non-Javadoc)      * @see java.lang.Object#toString()      */
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
comment|/*      *  (non-Javadoc)      * @see java.lang.Comparable#compareTo(java.lang.Object)      */
annotation|@
name|Override
DECL|method|compareTo (ImportFormat importer)
specifier|public
name|int
name|compareTo
parameter_list|(
name|ImportFormat
name|importer
parameter_list|)
block|{
name|int
name|result
decl_stmt|;
if|if
condition|(
name|getIsCustomImporter
argument_list|()
operator|==
name|importer
operator|.
name|getIsCustomImporter
argument_list|()
condition|)
block|{
name|result
operator|=
name|getFormatName
argument_list|()
operator|.
name|compareTo
argument_list|(
name|importer
operator|.
name|getFormatName
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|result
operator|=
name|getIsCustomImporter
argument_list|()
condition|?
literal|1
else|:
operator|-
literal|1
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
block|}
end_class

end_unit

