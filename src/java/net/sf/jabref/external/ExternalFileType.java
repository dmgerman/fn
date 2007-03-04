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
name|java
operator|.
name|net
operator|.
name|URL
import|;
end_import

begin_comment
comment|/**  * Created by IntelliJ IDEA.  * User: alver  * Date: Mar 4, 2006  * Time: 4:27:05 PM  * To change this template use File | Settings | File Templates.  */
end_comment

begin_class
DECL|class|ExternalFileType
specifier|public
class|class
name|ExternalFileType
implements|implements
name|Comparable
block|{
DECL|field|name
DECL|field|extension
DECL|field|openWith
specifier|protected
name|String
name|name
decl_stmt|,
name|extension
decl_stmt|,
name|openWith
decl_stmt|;
DECL|field|icon
specifier|protected
name|ImageIcon
name|icon
decl_stmt|;
DECL|method|ExternalFileType (String name, String extension, String openWith, ImageIcon icon)
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
name|openWith
parameter_list|,
name|ImageIcon
name|icon
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
name|this
operator|.
name|extension
operator|=
name|extension
expr_stmt|;
name|this
operator|.
name|openWith
operator|=
name|openWith
expr_stmt|;
name|this
operator|.
name|icon
operator|=
name|icon
expr_stmt|;
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
comment|/**      * Get the bibtex field name used to link to this file type.      * Currently we assume that field name equals filename extension.      * @return The field name.      */
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
DECL|method|compareTo (Object o)
specifier|public
name|int
name|compareTo
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
return|return
name|getName
argument_list|()
operator|.
name|compareTo
argument_list|(
operator|(
operator|(
name|ExternalFileType
operator|)
name|o
operator|)
operator|.
name|getName
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

