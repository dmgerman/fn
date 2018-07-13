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
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|DialogService
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
name|GenerateBibtexKeySingleAction
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
name|autocompleter
operator|.
name|AutoCompleteSuggestionProvider
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
name|entryeditor
operator|.
name|EntryEditorPreferences
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
name|integrity
operator|.
name|FieldCheckers
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
DECL|class|BibtexKeyEditorViewModel
specifier|public
class|class
name|BibtexKeyEditorViewModel
extends|extends
name|AbstractEditorViewModel
block|{
DECL|field|preferences
specifier|private
specifier|final
name|EntryEditorPreferences
name|preferences
decl_stmt|;
DECL|field|databaseContext
specifier|private
specifier|final
name|BibDatabaseContext
name|databaseContext
decl_stmt|;
DECL|field|undoManager
specifier|private
specifier|final
name|UndoManager
name|undoManager
decl_stmt|;
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
decl_stmt|;
DECL|method|BibtexKeyEditorViewModel (String fieldName, AutoCompleteSuggestionProvider<?> suggestionProvider, FieldCheckers fieldCheckers, EntryEditorPreferences preferences, BibDatabaseContext databaseContext, UndoManager undoManager, DialogService dialogService)
specifier|public
name|BibtexKeyEditorViewModel
parameter_list|(
name|String
name|fieldName
parameter_list|,
name|AutoCompleteSuggestionProvider
argument_list|<
name|?
argument_list|>
name|suggestionProvider
parameter_list|,
name|FieldCheckers
name|fieldCheckers
parameter_list|,
name|EntryEditorPreferences
name|preferences
parameter_list|,
name|BibDatabaseContext
name|databaseContext
parameter_list|,
name|UndoManager
name|undoManager
parameter_list|,
name|DialogService
name|dialogService
parameter_list|)
block|{
name|super
argument_list|(
name|fieldName
argument_list|,
name|suggestionProvider
argument_list|,
name|fieldCheckers
argument_list|)
expr_stmt|;
name|this
operator|.
name|preferences
operator|=
name|preferences
expr_stmt|;
name|this
operator|.
name|databaseContext
operator|=
name|databaseContext
expr_stmt|;
name|this
operator|.
name|undoManager
operator|=
name|undoManager
expr_stmt|;
name|this
operator|.
name|dialogService
operator|=
name|dialogService
expr_stmt|;
block|}
DECL|method|getGenerateCiteKeyCommand ()
specifier|public
name|Command
name|getGenerateCiteKeyCommand
parameter_list|()
block|{
return|return
operator|new
name|GenerateBibtexKeySingleAction
argument_list|(
name|entry
argument_list|,
name|databaseContext
argument_list|,
name|dialogService
argument_list|,
name|preferences
argument_list|,
name|undoManager
argument_list|)
return|;
block|}
block|}
end_class

end_unit

