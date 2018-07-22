begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.fieldeditors
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|fieldeditors
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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|function
operator|.
name|Supplier
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|MenuItem
import|;
end_import

begin_interface
DECL|interface|ContextMenuAddable
specifier|public
interface|interface
name|ContextMenuAddable
block|{
comment|/**      * Adds the given list of menu items to the context menu. The usage of {@link Supplier} prevents that the menus need      * to be instantiated at this point. They are populated when the user needs them which prevents many unnecessary      * allocations when the main table is just scrolled with the entry editor open.      */
DECL|method|addToContextMenu (final Supplier<List<MenuItem>> items)
name|void
name|addToContextMenu
parameter_list|(
specifier|final
name|Supplier
argument_list|<
name|List
argument_list|<
name|MenuItem
argument_list|>
argument_list|>
name|items
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

