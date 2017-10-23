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
name|List
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
name|gui
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
name|EntryType
import|;
end_import

begin_class
DECL|class|DeprecatedFieldsTab
specifier|public
class|class
name|DeprecatedFieldsTab
extends|extends
name|FieldsEditorTab
block|{
DECL|method|DeprecatedFieldsTab (BibDatabaseContext databaseContext, SuggestionProviders suggestionProviders)
specifier|public
name|DeprecatedFieldsTab
parameter_list|(
name|BibDatabaseContext
name|databaseContext
parameter_list|,
name|SuggestionProviders
name|suggestionProviders
parameter_list|)
block|{
name|super
argument_list|(
literal|false
argument_list|,
name|databaseContext
argument_list|,
name|suggestionProviders
argument_list|)
expr_stmt|;
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Deprecated fields"
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
literal|"Show deprecated BibTeX fields"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|setGraphic
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|OPTIONAL
operator|.
name|getGraphicNode
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|determineFieldsToShow (BibEntry entry, EntryType entryType)
specifier|protected
name|List
argument_list|<
name|String
argument_list|>
name|determineFieldsToShow
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|EntryType
name|entryType
parameter_list|)
block|{
return|return
name|entryType
operator|.
name|getDeprecatedFields
argument_list|()
return|;
block|}
block|}
end_class

end_unit

