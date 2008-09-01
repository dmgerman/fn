begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.external
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|external
package|;
end_package

begin_import
import|import
name|javax
operator|.
name|swing
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
name|GUIGlobals
import|;
end_import

begin_comment
comment|/**  * This class defines a type of external files that can be linked to from JabRef.  * The class contains enough information to provide an icon, a standard extension  * and a link to which application handles files of this type.  */
end_comment

begin_class
DECL|class|ExternalFileType
specifier|public
class|class
name|ExternalFileType
implements|implements
name|Comparable
argument_list|<
name|ExternalFileType
argument_list|>
block|{
DECL|field|name
DECL|field|extension
DECL|field|openWith
DECL|field|iconName
DECL|field|mimeType
specifier|protected
name|String
name|name
decl_stmt|,
name|extension
decl_stmt|,
name|openWith
decl_stmt|,
name|iconName
decl_stmt|,
name|mimeType
decl_stmt|;
DECL|field|icon
specifier|protected
name|ImageIcon
name|icon
decl_stmt|;
DECL|field|label
specifier|protected
name|JLabel
name|label
init|=
operator|new
name|JLabel
argument_list|()
decl_stmt|;
DECL|method|ExternalFileType (String name, String extension, String mimeType, String openWith, String iconName)
specifier|public
name|ExternalFileType
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|extension
parameter_list|,
name|String
name|mimeType
parameter_list|,
name|String
name|openWith
parameter_list|,
name|String
name|iconName
parameter_list|)
block|{
name|label
operator|.
name|setText
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
name|label
operator|.
name|setToolTipText
argument_list|(
name|this
operator|.
name|name
argument_list|)
expr_stmt|;
name|this
operator|.
name|extension
operator|=
name|extension
expr_stmt|;
name|this
operator|.
name|mimeType
operator|=
name|mimeType
expr_stmt|;
name|this
operator|.
name|openWith
operator|=
name|openWith
expr_stmt|;
name|setIconName
argument_list|(
name|iconName
argument_list|)
expr_stmt|;
block|}
comment|/**      * Construct an ExternalFileType from a String array. This constructor is used when      * reading file type definitions from Preferences, where the available data types are      * limited. We assume that the array contains the same values as the main constructor,      * in the same order.      *      * TODO: The icon argument needs special treatment. At the moment, we assume that the fourth      * element of the array contains the icon keyword to be looked up in the current icon theme.      * To support icons found elsewhere on the file system we simply need to prefix the icon name      * with a marker.       *      * @param val Constructor arguments.      */
DECL|method|ExternalFileType (String[] val)
specifier|public
name|ExternalFileType
parameter_list|(
name|String
index|[]
name|val
parameter_list|)
block|{
if|if
condition|(
operator|(
name|val
operator|==
literal|null
operator|)
operator|||
operator|(
name|val
operator|.
name|length
operator|<
literal|4
operator|)
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Cannot contruct ExternalFileType without four elements in String[] argument."
argument_list|)
throw|;
name|this
operator|.
name|name
operator|=
name|val
index|[
literal|0
index|]
expr_stmt|;
name|label
operator|.
name|setToolTipText
argument_list|(
name|this
operator|.
name|name
argument_list|)
expr_stmt|;
name|this
operator|.
name|extension
operator|=
name|val
index|[
literal|1
index|]
expr_stmt|;
name|label
operator|.
name|setText
argument_list|(
literal|null
argument_list|)
expr_stmt|;
comment|// Up to version 2.4b the mime type is not included:
if|if
condition|(
name|val
operator|.
name|length
operator|==
literal|4
condition|)
block|{
name|this
operator|.
name|openWith
operator|=
name|val
index|[
literal|2
index|]
expr_stmt|;
name|setIconName
argument_list|(
name|val
index|[
literal|3
index|]
argument_list|)
expr_stmt|;
block|}
comment|// When mime type is included, the array length should be 5:
elseif|else
if|if
condition|(
name|val
operator|.
name|length
operator|==
literal|5
condition|)
block|{
name|this
operator|.
name|mimeType
operator|=
name|val
index|[
literal|2
index|]
expr_stmt|;
name|this
operator|.
name|openWith
operator|=
name|val
index|[
literal|3
index|]
expr_stmt|;
name|setIconName
argument_list|(
name|val
index|[
literal|4
index|]
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Return a String array representing this file type. This is used for storage into      * Preferences, and the same array can be used to construct the file type later,      * using the String[] constructor.      *      * @return A String[] containing all information about this file type.      */
DECL|method|getStringArrayRepresentation ()
specifier|public
name|String
index|[]
name|getStringArrayRepresentation
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{
name|name
block|,
name|extension
block|,
name|mimeType
block|,
name|openWith
block|,
name|iconName
block|}
return|;
block|}
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|name
return|;
block|}
DECL|method|setName (String name)
specifier|public
name|void
name|setName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
name|label
operator|.
name|setToolTipText
argument_list|(
name|this
operator|.
name|name
argument_list|)
expr_stmt|;
block|}
DECL|method|getExtension ()
specifier|public
name|String
name|getExtension
parameter_list|()
block|{
return|return
name|extension
return|;
block|}
DECL|method|setExtension (String extension)
specifier|public
name|void
name|setExtension
parameter_list|(
name|String
name|extension
parameter_list|)
block|{
name|this
operator|.
name|extension
operator|=
name|extension
expr_stmt|;
block|}
DECL|method|getMimeType ()
specifier|public
name|String
name|getMimeType
parameter_list|()
block|{
return|return
name|mimeType
return|;
block|}
DECL|method|setMimeType (String mimeType)
specifier|public
name|void
name|setMimeType
parameter_list|(
name|String
name|mimeType
parameter_list|)
block|{
name|this
operator|.
name|mimeType
operator|=
name|mimeType
expr_stmt|;
block|}
comment|/**      * Get the bibtex field name used to extension to this file type.      * Currently we assume that field name equals filename extension.      * @return The field name.      */
DECL|method|getFieldName ()
specifier|public
name|String
name|getFieldName
parameter_list|()
block|{
return|return
name|extension
return|;
block|}
DECL|method|getOpenWith ()
specifier|public
name|String
name|getOpenWith
parameter_list|()
block|{
return|return
name|openWith
return|;
block|}
DECL|method|setOpenWith (String openWith)
specifier|public
name|void
name|setOpenWith
parameter_list|(
name|String
name|openWith
parameter_list|)
block|{
name|this
operator|.
name|openWith
operator|=
name|openWith
expr_stmt|;
block|}
comment|/**      * Set the string associated with this file type's icon. The string is used      * to get the actual icon by the method GUIGlobals.getIcon(String)      * @param name The icon name to use.      */
DECL|method|setIconName (String name)
specifier|public
name|void
name|setIconName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|this
operator|.
name|iconName
operator|=
name|name
expr_stmt|;
try|try
block|{
name|this
operator|.
name|icon
operator|=
name|GUIGlobals
operator|.
name|getImage
argument_list|(
name|iconName
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NullPointerException
name|ex
parameter_list|)
block|{
comment|// Loading the icon failed. This could be because the icons have not been
comment|// initialized, which will be the case if we are operating from the command
comment|// line and the graphical interface hasn't been initialized. In that case
comment|// we will do without the icon:
name|this
operator|.
name|icon
operator|=
literal|null
expr_stmt|;
block|}
name|label
operator|.
name|setIcon
argument_list|(
name|this
operator|.
name|icon
argument_list|)
expr_stmt|;
block|}
comment|/**      * Obtain a JLabel instance set with this file type's icon. The same JLabel      * is returned from each call of this method.      * @return the label.      */
DECL|method|getIconLabel ()
specifier|public
name|JLabel
name|getIconLabel
parameter_list|()
block|{
return|return
name|label
return|;
block|}
comment|/**      * Get the string associated with this file type's icon. The string is used      * to get the actual icon by the method GUIGlobals.getIcon(String)      * @return The icon name.      */
DECL|method|getIconName ()
specifier|public
name|String
name|getIconName
parameter_list|()
block|{
return|return
name|iconName
return|;
block|}
DECL|method|getIcon ()
specifier|public
name|ImageIcon
name|getIcon
parameter_list|()
block|{
return|return
name|icon
return|;
block|}
DECL|method|setIcon (ImageIcon icon)
specifier|public
name|void
name|setIcon
parameter_list|(
name|ImageIcon
name|icon
parameter_list|)
block|{
name|this
operator|.
name|icon
operator|=
name|icon
expr_stmt|;
block|}
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getName
argument_list|()
return|;
block|}
DECL|method|compareTo (ExternalFileType o)
specifier|public
name|int
name|compareTo
parameter_list|(
name|ExternalFileType
name|o
parameter_list|)
block|{
return|return
name|getName
argument_list|()
operator|.
name|compareTo
argument_list|(
name|o
operator|.
name|getName
argument_list|()
argument_list|)
return|;
block|}
DECL|method|copy ()
specifier|public
name|ExternalFileType
name|copy
parameter_list|()
block|{
return|return
operator|new
name|ExternalFileType
argument_list|(
name|name
argument_list|,
name|extension
argument_list|,
name|mimeType
argument_list|,
name|openWith
argument_list|,
name|iconName
argument_list|)
return|;
block|}
DECL|method|hashCode ()
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|name
operator|.
name|hashCode
argument_list|()
return|;
block|}
comment|/**      * We define two file type objects as equal if their name, extension, openWith and      * iconName are equal.      *      * @param object The file type to compare with.      * @return true if the file types are equal.      */
DECL|method|equals (Object object)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|object
parameter_list|)
block|{
name|ExternalFileType
name|other
init|=
operator|(
name|ExternalFileType
operator|)
name|object
decl_stmt|;
if|if
condition|(
name|other
operator|==
literal|null
condition|)
return|return
literal|false
return|;
return|return
operator|(
name|name
operator|==
literal|null
condition|?
name|other
operator|.
name|name
operator|==
literal|null
else|:
name|name
operator|.
name|equals
argument_list|(
name|other
operator|.
name|name
argument_list|)
operator|)
operator|&&
operator|(
name|extension
operator|==
literal|null
condition|?
name|other
operator|.
name|extension
operator|==
literal|null
else|:
name|extension
operator|.
name|equals
argument_list|(
name|other
operator|.
name|extension
argument_list|)
operator|)
operator|&&
operator|(
name|mimeType
operator|==
literal|null
condition|?
name|other
operator|.
name|mimeType
operator|==
literal|null
else|:
name|mimeType
operator|.
name|equals
argument_list|(
name|other
operator|.
name|mimeType
argument_list|)
operator|)
operator|&&
operator|(
name|openWith
operator|==
literal|null
condition|?
name|other
operator|.
name|openWith
operator|==
literal|null
else|:
name|openWith
operator|.
name|equals
argument_list|(
name|other
operator|.
name|openWith
argument_list|)
operator|)
operator|&&
operator|(
name|iconName
operator|==
literal|null
condition|?
name|other
operator|.
name|iconName
operator|==
literal|null
else|:
name|iconName
operator|.
name|equals
argument_list|(
name|other
operator|.
name|iconName
argument_list|)
operator|)
return|;
block|}
block|}
end_class

end_unit

