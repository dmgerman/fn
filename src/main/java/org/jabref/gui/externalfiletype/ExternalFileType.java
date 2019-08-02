begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.externalfiletype
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|externalfiletype
package|;
end_package

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|icon
operator|.
name|JabRefIcon
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|field
operator|.
name|Field
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|field
operator|.
name|FieldFactory
import|;
end_import

begin_interface
DECL|interface|ExternalFileType
specifier|public
interface|interface
name|ExternalFileType
block|{
DECL|method|getName ()
name|String
name|getName
parameter_list|()
function_decl|;
DECL|method|getExtension ()
name|String
name|getExtension
parameter_list|()
function_decl|;
DECL|method|getMimeType ()
name|String
name|getMimeType
parameter_list|()
function_decl|;
comment|/**      * Get the bibtex field name used for this file type.      * Currently we assume that field name equals filename extension.      *      * @return The field name.      */
DECL|method|getField ()
specifier|default
name|Field
name|getField
parameter_list|()
block|{
return|return
name|FieldFactory
operator|.
name|parseField
argument_list|(
name|getExtension
argument_list|()
argument_list|)
return|;
block|}
DECL|method|getOpenWithApplication ()
name|String
name|getOpenWithApplication
parameter_list|()
function_decl|;
DECL|method|getIcon ()
name|JabRefIcon
name|getIcon
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

