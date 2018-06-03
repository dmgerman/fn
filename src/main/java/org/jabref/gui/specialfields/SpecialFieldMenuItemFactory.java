begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.specialfields
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|specialfields
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|function
operator|.
name|Function
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|undo
operator|.
name|UndoManager
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
name|Menu
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

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|BasePanel
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
name|actions
operator|.
name|ActionFactory
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
name|actions
operator|.
name|OldCommandWrapper
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
name|actions
operator|.
name|OldCommandWrapperForActiveDatabase
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
name|specialfields
operator|.
name|SpecialField
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
name|specialfields
operator|.
name|SpecialFieldValue
import|;
end_import

begin_import
import|import
name|de
operator|.
name|saxsys
operator|.
name|mvvmfx
operator|.
name|utils
operator|.
name|commands
operator|.
name|Command
import|;
end_import

begin_class
DECL|class|SpecialFieldMenuItemFactory
specifier|public
class|class
name|SpecialFieldMenuItemFactory
block|{
DECL|method|getSpecialFieldSingleItem (SpecialField field, ActionFactory factory, BasePanel panel)
specifier|public
specifier|static
name|MenuItem
name|getSpecialFieldSingleItem
parameter_list|(
name|SpecialField
name|field
parameter_list|,
name|ActionFactory
name|factory
parameter_list|,
name|BasePanel
name|panel
parameter_list|)
block|{
name|SpecialFieldValueViewModel
name|specialField
init|=
operator|new
name|SpecialFieldValueViewModel
argument_list|(
name|field
operator|.
name|getValues
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
decl_stmt|;
return|return
name|factory
operator|.
name|createMenuItem
argument_list|(
name|specialField
operator|.
name|getAction
argument_list|()
argument_list|,
operator|new
name|OldCommandWrapper
argument_list|(
name|specialField
operator|.
name|getCommand
argument_list|()
argument_list|,
name|panel
argument_list|)
argument_list|)
return|;
block|}
DECL|method|getSpecialFieldSingleItemForActiveDatabase (SpecialField field, ActionFactory factory)
specifier|public
specifier|static
name|MenuItem
name|getSpecialFieldSingleItemForActiveDatabase
parameter_list|(
name|SpecialField
name|field
parameter_list|,
name|ActionFactory
name|factory
parameter_list|)
block|{
name|SpecialFieldValueViewModel
name|specialField
init|=
operator|new
name|SpecialFieldValueViewModel
argument_list|(
name|field
operator|.
name|getValues
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
decl_stmt|;
return|return
name|factory
operator|.
name|createMenuItem
argument_list|(
name|specialField
operator|.
name|getAction
argument_list|()
argument_list|,
operator|new
name|OldCommandWrapperForActiveDatabase
argument_list|(
name|specialField
operator|.
name|getCommand
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
DECL|method|createSpecialFieldMenu (SpecialField field, ActionFactory factory, BasePanel panel)
specifier|public
specifier|static
name|Menu
name|createSpecialFieldMenu
parameter_list|(
name|SpecialField
name|field
parameter_list|,
name|ActionFactory
name|factory
parameter_list|,
name|BasePanel
name|panel
parameter_list|)
block|{
return|return
name|createSpecialFieldMenu
argument_list|(
name|field
argument_list|,
name|factory
argument_list|,
name|panel
operator|.
name|getUndoManager
argument_list|()
argument_list|,
name|specialField
lambda|->
operator|new
name|OldCommandWrapper
argument_list|(
name|specialField
operator|.
name|getCommand
argument_list|()
argument_list|,
name|panel
argument_list|)
argument_list|)
return|;
block|}
DECL|method|createSpecialFieldMenuForActiveDatabase (SpecialField field, ActionFactory factory, UndoManager undoManager)
specifier|public
specifier|static
name|Menu
name|createSpecialFieldMenuForActiveDatabase
parameter_list|(
name|SpecialField
name|field
parameter_list|,
name|ActionFactory
name|factory
parameter_list|,
name|UndoManager
name|undoManager
parameter_list|)
block|{
return|return
name|createSpecialFieldMenu
argument_list|(
name|field
argument_list|,
name|factory
argument_list|,
name|undoManager
argument_list|,
name|specialField
lambda|->
operator|new
name|OldCommandWrapperForActiveDatabase
argument_list|(
name|specialField
operator|.
name|getCommand
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
DECL|method|createSpecialFieldMenu (SpecialField field, ActionFactory factory, UndoManager undoManager, Function<SpecialFieldValueViewModel, Command> commandFactory)
specifier|public
specifier|static
name|Menu
name|createSpecialFieldMenu
parameter_list|(
name|SpecialField
name|field
parameter_list|,
name|ActionFactory
name|factory
parameter_list|,
name|UndoManager
name|undoManager
parameter_list|,
name|Function
argument_list|<
name|SpecialFieldValueViewModel
argument_list|,
name|Command
argument_list|>
name|commandFactory
parameter_list|)
block|{
name|SpecialFieldViewModel
name|viewModel
init|=
operator|new
name|SpecialFieldViewModel
argument_list|(
name|field
argument_list|,
name|undoManager
argument_list|)
decl_stmt|;
name|Menu
name|menu
init|=
name|factory
operator|.
name|createMenu
argument_list|(
name|viewModel
operator|.
name|getAction
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|SpecialFieldValue
name|val
range|:
name|field
operator|.
name|getValues
argument_list|()
control|)
block|{
name|SpecialFieldValueViewModel
name|specialField
init|=
operator|new
name|SpecialFieldValueViewModel
argument_list|(
name|val
argument_list|)
decl_stmt|;
name|menu
operator|.
name|getItems
argument_list|()
operator|.
name|add
argument_list|(
name|factory
operator|.
name|createMenuItem
argument_list|(
name|specialField
operator|.
name|getAction
argument_list|()
argument_list|,
name|commandFactory
operator|.
name|apply
argument_list|(
name|specialField
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|menu
return|;
block|}
block|}
end_class

end_unit

