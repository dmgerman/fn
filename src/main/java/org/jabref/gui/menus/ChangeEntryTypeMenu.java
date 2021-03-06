begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.menus
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|menus
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

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
name|collections
operator|.
name|ObservableList
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
name|ContextMenu
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
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|SeparatorMenuItem
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|Globals
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
name|undo
operator|.
name|CountingUndoManager
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
name|undo
operator|.
name|NamedCompound
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
name|undo
operator|.
name|UndoableChangeType
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|l10n
operator|.
name|Localization
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
name|database
operator|.
name|BibDatabaseContext
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
name|database
operator|.
name|BibDatabaseMode
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
name|BibEntry
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
name|BibEntryType
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
name|types
operator|.
name|BibtexEntryTypeDefinitions
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
name|types
operator|.
name|EntryType
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
name|types
operator|.
name|IEEETranEntryTypeDefinitions
import|;
end_import

begin_class
DECL|class|ChangeEntryTypeMenu
specifier|public
class|class
name|ChangeEntryTypeMenu
block|{
DECL|method|ChangeEntryTypeMenu ()
specifier|public
name|ChangeEntryTypeMenu
parameter_list|()
block|{      }
DECL|method|createMenuItem (EntryType type, BibEntry entry, UndoManager undoManager)
specifier|public
specifier|static
name|MenuItem
name|createMenuItem
parameter_list|(
name|EntryType
name|type
parameter_list|,
name|BibEntry
name|entry
parameter_list|,
name|UndoManager
name|undoManager
parameter_list|)
block|{
name|MenuItem
name|menuItem
init|=
operator|new
name|MenuItem
argument_list|(
name|type
operator|.
name|getDisplayName
argument_list|()
argument_list|)
decl_stmt|;
name|menuItem
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
block|{
name|NamedCompound
name|compound
init|=
operator|new
name|NamedCompound
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Change entry type"
argument_list|)
argument_list|)
decl_stmt|;
name|entry
operator|.
name|setType
argument_list|(
name|type
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|change
lambda|->
name|compound
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableChangeType
argument_list|(
name|change
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|undoManager
operator|.
name|addEdit
argument_list|(
name|compound
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
return|return
name|menuItem
return|;
block|}
DECL|method|getChangeEntryTypePopupMenu (BibEntry entry, BibDatabaseContext bibDatabaseContext, CountingUndoManager undoManager)
specifier|public
name|ContextMenu
name|getChangeEntryTypePopupMenu
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|BibDatabaseContext
name|bibDatabaseContext
parameter_list|,
name|CountingUndoManager
name|undoManager
parameter_list|)
block|{
name|ContextMenu
name|menu
init|=
operator|new
name|ContextMenu
argument_list|()
decl_stmt|;
name|populateComplete
argument_list|(
name|menu
operator|.
name|getItems
argument_list|()
argument_list|,
name|entry
argument_list|,
name|bibDatabaseContext
argument_list|,
name|undoManager
argument_list|)
expr_stmt|;
return|return
name|menu
return|;
block|}
DECL|method|getChangeEntryTypeMenu (BibEntry entry, BibDatabaseContext bibDatabaseContext, CountingUndoManager undoManager)
specifier|public
name|Menu
name|getChangeEntryTypeMenu
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|BibDatabaseContext
name|bibDatabaseContext
parameter_list|,
name|CountingUndoManager
name|undoManager
parameter_list|)
block|{
name|Menu
name|menu
init|=
operator|new
name|Menu
argument_list|()
decl_stmt|;
name|menu
operator|.
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Change entry type"
argument_list|)
argument_list|)
expr_stmt|;
name|populateComplete
argument_list|(
name|menu
operator|.
name|getItems
argument_list|()
argument_list|,
name|entry
argument_list|,
name|bibDatabaseContext
argument_list|,
name|undoManager
argument_list|)
expr_stmt|;
return|return
name|menu
return|;
block|}
DECL|method|populateComplete (ObservableList<MenuItem> items, BibEntry entry, BibDatabaseContext bibDatabaseContext, CountingUndoManager undoManager)
specifier|private
name|void
name|populateComplete
parameter_list|(
name|ObservableList
argument_list|<
name|MenuItem
argument_list|>
name|items
parameter_list|,
name|BibEntry
name|entry
parameter_list|,
name|BibDatabaseContext
name|bibDatabaseContext
parameter_list|,
name|CountingUndoManager
name|undoManager
parameter_list|)
block|{
if|if
condition|(
name|bibDatabaseContext
operator|.
name|isBiblatexMode
argument_list|()
condition|)
block|{
comment|// Default BibLaTeX
name|populate
argument_list|(
name|items
argument_list|,
name|Globals
operator|.
name|entryTypesManager
operator|.
name|getAllTypes
argument_list|(
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
argument_list|,
name|entry
argument_list|,
name|undoManager
argument_list|)
expr_stmt|;
comment|// Custom types
name|populateSubMenu
argument_list|(
name|items
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Custom"
argument_list|)
argument_list|,
name|Globals
operator|.
name|entryTypesManager
operator|.
name|getAllCustomTypes
argument_list|(
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
argument_list|,
name|entry
argument_list|,
name|undoManager
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// Default BibTeX
name|populateSubMenu
argument_list|(
name|items
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBTEX
operator|.
name|getFormattedName
argument_list|()
argument_list|,
name|BibtexEntryTypeDefinitions
operator|.
name|ALL
argument_list|,
name|entry
argument_list|,
name|undoManager
argument_list|)
expr_stmt|;
name|items
operator|.
name|remove
argument_list|(
literal|0
argument_list|)
expr_stmt|;
comment|// Remove separator
comment|// IEEETran
name|populateSubMenu
argument_list|(
name|items
argument_list|,
literal|"IEEETran"
argument_list|,
name|IEEETranEntryTypeDefinitions
operator|.
name|ALL
argument_list|,
name|entry
argument_list|,
name|undoManager
argument_list|)
expr_stmt|;
comment|// Custom types
name|populateSubMenu
argument_list|(
name|items
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Custom"
argument_list|)
argument_list|,
name|Globals
operator|.
name|entryTypesManager
operator|.
name|getAllCustomTypes
argument_list|(
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
argument_list|,
name|entry
argument_list|,
name|undoManager
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|populateSubMenu (ObservableList<MenuItem> items, String text, List<BibEntryType> entryTypes, BibEntry entry, CountingUndoManager undoManager)
specifier|private
name|void
name|populateSubMenu
parameter_list|(
name|ObservableList
argument_list|<
name|MenuItem
argument_list|>
name|items
parameter_list|,
name|String
name|text
parameter_list|,
name|List
argument_list|<
name|BibEntryType
argument_list|>
name|entryTypes
parameter_list|,
name|BibEntry
name|entry
parameter_list|,
name|CountingUndoManager
name|undoManager
parameter_list|)
block|{
if|if
condition|(
operator|!
name|entryTypes
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|items
operator|.
name|add
argument_list|(
operator|new
name|SeparatorMenuItem
argument_list|()
argument_list|)
expr_stmt|;
name|Menu
name|custom
init|=
operator|new
name|Menu
argument_list|(
name|text
argument_list|)
decl_stmt|;
name|populate
argument_list|(
name|custom
argument_list|,
name|entryTypes
argument_list|,
name|entry
argument_list|,
name|undoManager
argument_list|)
expr_stmt|;
name|items
operator|.
name|add
argument_list|(
name|custom
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|populate (ObservableList<MenuItem> items, Collection<BibEntryType> types, BibEntry entry, UndoManager undoManager)
specifier|private
name|void
name|populate
parameter_list|(
name|ObservableList
argument_list|<
name|MenuItem
argument_list|>
name|items
parameter_list|,
name|Collection
argument_list|<
name|BibEntryType
argument_list|>
name|types
parameter_list|,
name|BibEntry
name|entry
parameter_list|,
name|UndoManager
name|undoManager
parameter_list|)
block|{
for|for
control|(
name|BibEntryType
name|type
range|:
name|types
control|)
block|{
name|items
operator|.
name|add
argument_list|(
name|createMenuItem
argument_list|(
name|type
operator|.
name|getType
argument_list|()
argument_list|,
name|entry
argument_list|,
name|undoManager
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|populate (Menu menu, Collection<BibEntryType> types, BibEntry entry, UndoManager undoManager)
specifier|private
name|void
name|populate
parameter_list|(
name|Menu
name|menu
parameter_list|,
name|Collection
argument_list|<
name|BibEntryType
argument_list|>
name|types
parameter_list|,
name|BibEntry
name|entry
parameter_list|,
name|UndoManager
name|undoManager
parameter_list|)
block|{
name|populate
argument_list|(
name|menu
operator|.
name|getItems
argument_list|()
argument_list|,
name|types
argument_list|,
name|entry
argument_list|,
name|undoManager
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

