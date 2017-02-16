begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.gui
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|gui
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

begin_interface
DECL|interface|Dialog
specifier|public
interface|interface
name|Dialog
parameter_list|<
name|R
parameter_list|>
block|{
DECL|method|showAndWait ()
name|Optional
argument_list|<
name|R
argument_list|>
name|showAndWait
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

