begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.preferences
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|preferences
package|;
end_package

begin_interface
DECL|interface|PreferenceTabViewModel
specifier|public
interface|interface
name|PreferenceTabViewModel
block|{
DECL|method|setValues ()
name|void
name|setValues
parameter_list|()
function_decl|;
DECL|method|storeSettings ()
name|void
name|storeSettings
parameter_list|()
function_decl|;
DECL|method|validateSettings ()
name|boolean
name|validateSettings
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

