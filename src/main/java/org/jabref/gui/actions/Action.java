begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.actions
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|actions
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
import|;
end_import

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
name|gui
operator|.
name|keyboard
operator|.
name|KeyBinding
import|;
end_import

begin_interface
DECL|interface|Action
specifier|public
interface|interface
name|Action
block|{
DECL|method|getIcon ()
name|Optional
argument_list|<
name|JabRefIcon
argument_list|>
name|getIcon
parameter_list|()
function_decl|;
DECL|method|getKeyBinding ()
name|Optional
argument_list|<
name|KeyBinding
argument_list|>
name|getKeyBinding
parameter_list|()
function_decl|;
DECL|method|getText ()
name|String
name|getText
parameter_list|()
function_decl|;
DECL|method|getDescription ()
name|String
name|getDescription
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

