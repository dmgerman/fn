begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.entryeditor
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|entryeditor
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Comparator
import|;
end_import

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
name|java
operator|.
name|util
operator|.
name|SortedSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
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
name|Tooltip
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
name|autocompleter
operator|.
name|SuggestionProviders
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
name|IconTheme
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
name|InternalField
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
name|OrFields
import|;
end_import

begin_class
DECL|class|RequiredFieldsTab
specifier|public
class|class
name|RequiredFieldsTab
extends|extends
name|FieldsEditorTab
block|{
DECL|method|RequiredFieldsTab (BibDatabaseContext databaseContext, SuggestionProviders suggestionProviders, UndoManager undoManager, DialogService dialogService)
specifier|public
name|RequiredFieldsTab
parameter_list|(
name|BibDatabaseContext
name|databaseContext
parameter_list|,
name|SuggestionProviders
name|suggestionProviders
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
literal|false
argument_list|,
name|databaseContext
argument_list|,
name|suggestionProviders
argument_list|,
name|undoManager
argument_list|,
name|dialogService
argument_list|)
expr_stmt|;
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Required fields"
argument_list|)
argument_list|)
expr_stmt|;
name|setTooltip
argument_list|(
operator|new
name|Tooltip
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Show required fields"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|setGraphic
argument_list|(
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|REQUIRED
operator|.
name|getGraphicNode
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|determineFieldsToShow (BibEntry entry)
specifier|protected
name|SortedSet
argument_list|<
name|Field
argument_list|>
name|determineFieldsToShow
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
name|Optional
argument_list|<
name|BibEntryType
argument_list|>
name|entryType
init|=
name|Globals
operator|.
name|entryTypesManager
operator|.
name|enrich
argument_list|(
name|entry
operator|.
name|getType
argument_list|()
argument_list|,
name|databaseContext
operator|.
name|getMode
argument_list|()
argument_list|)
decl_stmt|;
name|SortedSet
argument_list|<
name|Field
argument_list|>
name|fields
init|=
operator|new
name|TreeSet
argument_list|<>
argument_list|(
name|Comparator
operator|.
name|comparing
argument_list|(
name|Field
operator|::
name|getName
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|entryType
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|fields
operator|.
name|addAll
argument_list|(
name|entryType
operator|.
name|get
argument_list|()
operator|.
name|getRequiredFields
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|OrFields
operator|::
name|getPrimary
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toSet
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|// Add the edit field for Bibtex-key.
name|fields
operator|.
name|add
argument_list|(
name|InternalField
operator|.
name|KEY_FIELD
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// Entry type unknown -> treat all fields as required
name|fields
operator|.
name|addAll
argument_list|(
name|entry
operator|.
name|getFields
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|fields
return|;
block|}
block|}
end_class

end_unit

