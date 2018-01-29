begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
package|;
end_package

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|Icon
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|Node
import|;
end_import

begin_interface
DECL|interface|JabRefIcon
specifier|public
interface|interface
name|JabRefIcon
block|{
DECL|method|getIcon ()
name|Icon
name|getIcon
parameter_list|()
function_decl|;
DECL|method|getSmallIcon ()
name|Icon
name|getSmallIcon
parameter_list|()
function_decl|;
DECL|method|getGraphicNode ()
name|Node
name|getGraphicNode
parameter_list|()
function_decl|;
DECL|method|disabled ()
name|JabRefIcon
name|disabled
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

