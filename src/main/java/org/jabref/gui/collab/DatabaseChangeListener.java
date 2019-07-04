begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.collab
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|collab
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_interface
DECL|interface|DatabaseChangeListener
specifier|public
interface|interface
name|DatabaseChangeListener
block|{
DECL|method|databaseChanged (List<DatabaseChangeViewModel> changes)
name|void
name|databaseChanged
parameter_list|(
name|List
argument_list|<
name|DatabaseChangeViewModel
argument_list|>
name|changes
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

