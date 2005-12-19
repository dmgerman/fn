begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
block|{
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
comment|/**      * Parse the entries in the source, and return a List of BibtexEntry      * objects.      */
DECL|method|importEntries (InputStream in)
specifier|public
specifier|abstract
name|List
name|importEntries
parameter_list|(
name|InputStream
name|in
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
DECL|method|compareTo (Object o)
specifier|public
name|int
name|compareTo
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
name|int
name|result
init|=
literal|0
decl_stmt|;
name|ImportFormat
name|importer
init|=
operator|(
name|ImportFormat
operator|)
name|o
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

