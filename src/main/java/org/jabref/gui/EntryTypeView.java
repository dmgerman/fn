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
name|javafx
operator|.
name|event
operator|.
name|Event
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|fxml
operator|.
name|FXML
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
name|Button
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
name|ButtonType
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
name|ComboBox
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
name|TextField
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
name|TitledPane
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|layout
operator|.
name|FlowPane
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
name|util
operator|.
name|BaseDialog
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
name|util
operator|.
name|ControlHelper
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
name|util
operator|.
name|ViewModelListCellFactory
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
name|importer
operator|.
name|IdBasedFetcher
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
name|EntryTypes
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
name|BiblatexEntryTypes
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
name|BibtexEntryTypes
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
name|IEEETranEntryTypes
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_import
import|import
name|com
operator|.
name|airhacks
operator|.
name|afterburner
operator|.
name|views
operator|.
name|ViewLoader
import|;
end_import

begin_import
import|import
name|org
operator|.
name|fxmisc
operator|.
name|easybind
operator|.
name|EasyBind
import|;
end_import

begin_comment
comment|/**  * Dialog that prompts the user to choose a type for an entry.  * Returns null if canceled.  */
end_comment

begin_class
DECL|class|EntryTypeView
specifier|public
class|class
name|EntryTypeView
extends|extends
name|BaseDialog
argument_list|<
name|EntryType
argument_list|>
block|{
DECL|field|generateButton
annotation|@
name|FXML
specifier|private
name|ButtonType
name|generateButton
decl_stmt|;
DECL|field|idTextField
annotation|@
name|FXML
specifier|private
name|TextField
name|idTextField
decl_stmt|;
DECL|field|idBasedFetchers
annotation|@
name|FXML
specifier|private
name|ComboBox
argument_list|<
name|IdBasedFetcher
argument_list|>
name|idBasedFetchers
decl_stmt|;
DECL|field|biblatexPane
annotation|@
name|FXML
specifier|private
name|FlowPane
name|biblatexPane
decl_stmt|;
DECL|field|bibTexPane
annotation|@
name|FXML
specifier|private
name|FlowPane
name|bibTexPane
decl_stmt|;
DECL|field|ieeetranPane
annotation|@
name|FXML
specifier|private
name|FlowPane
name|ieeetranPane
decl_stmt|;
DECL|field|customPane
annotation|@
name|FXML
specifier|private
name|FlowPane
name|customPane
decl_stmt|;
DECL|field|biblatexTitlePane
annotation|@
name|FXML
specifier|private
name|TitledPane
name|biblatexTitlePane
decl_stmt|;
DECL|field|bibTexTitlePane
annotation|@
name|FXML
specifier|private
name|TitledPane
name|bibTexTitlePane
decl_stmt|;
DECL|field|ieeeTranTitlePane
annotation|@
name|FXML
specifier|private
name|TitledPane
name|ieeeTranTitlePane
decl_stmt|;
DECL|field|customTitlePane
annotation|@
name|FXML
specifier|private
name|TitledPane
name|customTitlePane
decl_stmt|;
DECL|field|basePanel
specifier|private
specifier|final
name|BasePanel
name|basePanel
decl_stmt|;
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
decl_stmt|;
DECL|field|prefs
specifier|private
specifier|final
name|JabRefPreferences
name|prefs
decl_stmt|;
DECL|field|type
specifier|private
name|EntryType
name|type
decl_stmt|;
DECL|field|viewModel
specifier|private
name|EntryTypeViewModel
name|viewModel
decl_stmt|;
DECL|method|EntryTypeView (BasePanel basePanel, DialogService dialogService, JabRefPreferences preferences)
specifier|public
name|EntryTypeView
parameter_list|(
name|BasePanel
name|basePanel
parameter_list|,
name|DialogService
name|dialogService
parameter_list|,
name|JabRefPreferences
name|preferences
parameter_list|)
block|{
name|this
operator|.
name|basePanel
operator|=
name|basePanel
expr_stmt|;
name|this
operator|.
name|dialogService
operator|=
name|dialogService
expr_stmt|;
name|this
operator|.
name|prefs
operator|=
name|preferences
expr_stmt|;
name|this
operator|.
name|setTitle
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Select entry type"
argument_list|)
argument_list|)
expr_stmt|;
name|ViewLoader
operator|.
name|view
argument_list|(
name|this
argument_list|)
operator|.
name|load
argument_list|()
operator|.
name|setAsDialogPane
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|ControlHelper
operator|.
name|setAction
argument_list|(
name|generateButton
argument_list|,
name|this
operator|.
name|getDialogPane
argument_list|()
argument_list|,
name|event
lambda|->
name|viewModel
operator|.
name|runFetcherWorker
argument_list|()
argument_list|)
expr_stmt|;
name|setResultConverter
argument_list|(
name|button
lambda|->
block|{
comment|//The buttonType will always be cancel, even if we pressed one of the entry type buttons
return|return
name|type
return|;
block|}
argument_list|)
expr_stmt|;
name|Button
name|btnGenerate
init|=
operator|(
name|Button
operator|)
name|this
operator|.
name|getDialogPane
argument_list|()
operator|.
name|lookupButton
argument_list|(
name|generateButton
argument_list|)
decl_stmt|;
name|btnGenerate
operator|.
name|textProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|EasyBind
operator|.
name|map
argument_list|(
name|viewModel
operator|.
name|searchingProperty
argument_list|()
argument_list|,
name|searching
lambda|->
operator|(
name|searching
operator|)
condition|?
name|Localization
operator|.
name|lang
argument_list|(
literal|"Searching..."
argument_list|)
else|:
name|Localization
operator|.
name|lang
argument_list|(
literal|"Generate"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|btnGenerate
operator|.
name|disableProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|viewModel
operator|.
name|searchingProperty
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|addEntriesToPane (FlowPane pane, Collection<? extends EntryType> entries)
specifier|private
name|void
name|addEntriesToPane
parameter_list|(
name|FlowPane
name|pane
parameter_list|,
name|Collection
argument_list|<
name|?
extends|extends
name|EntryType
argument_list|>
name|entries
parameter_list|)
block|{
for|for
control|(
name|EntryType
name|entryType
range|:
name|entries
control|)
block|{
name|Button
name|entryButton
init|=
operator|new
name|Button
argument_list|(
name|entryType
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|entryButton
operator|.
name|setUserData
argument_list|(
name|entryType
argument_list|)
expr_stmt|;
name|entryButton
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|setEntryTypeForReturnAndClose
argument_list|(
name|entryType
argument_list|)
argument_list|)
expr_stmt|;
name|pane
operator|.
name|getChildren
argument_list|()
operator|.
name|add
argument_list|(
name|entryButton
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|FXML
DECL|method|initialize ()
specifier|public
name|void
name|initialize
parameter_list|()
block|{
name|viewModel
operator|=
operator|new
name|EntryTypeViewModel
argument_list|(
name|prefs
argument_list|,
name|basePanel
argument_list|,
name|dialogService
argument_list|)
expr_stmt|;
name|idBasedFetchers
operator|.
name|itemsProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|viewModel
operator|.
name|fetcherItemsProperty
argument_list|()
argument_list|)
expr_stmt|;
name|idTextField
operator|.
name|textProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|viewModel
operator|.
name|idTextProperty
argument_list|()
argument_list|)
expr_stmt|;
name|idBasedFetchers
operator|.
name|valueProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|viewModel
operator|.
name|selectedItemProperty
argument_list|()
argument_list|)
expr_stmt|;
name|EasyBind
operator|.
name|subscribe
argument_list|(
name|viewModel
operator|.
name|getFocusAndSelectAllProperty
argument_list|()
argument_list|,
name|evt
lambda|->
block|{
if|if
condition|(
name|evt
condition|)
block|{
name|idTextField
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
name|idTextField
operator|.
name|selectAll
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
operator|new
name|ViewModelListCellFactory
argument_list|<
name|IdBasedFetcher
argument_list|>
argument_list|()
operator|.
name|withText
argument_list|(
name|item
lambda|->
name|item
operator|.
name|getName
argument_list|()
argument_list|)
operator|.
name|install
argument_list|(
name|idBasedFetchers
argument_list|)
expr_stmt|;
comment|//we set the managed property so that they will only be rendered when they are visble so that the Nodes only take the space when visible
comment|//avoids removing and adding from the scence graph
name|bibTexTitlePane
operator|.
name|managedProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|bibTexTitlePane
operator|.
name|visibleProperty
argument_list|()
argument_list|)
expr_stmt|;
name|ieeeTranTitlePane
operator|.
name|managedProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|ieeeTranTitlePane
operator|.
name|visibleProperty
argument_list|()
argument_list|)
expr_stmt|;
name|biblatexTitlePane
operator|.
name|managedProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|biblatexTitlePane
operator|.
name|visibleProperty
argument_list|()
argument_list|)
expr_stmt|;
name|customTitlePane
operator|.
name|managedProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|customTitlePane
operator|.
name|visibleProperty
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|basePanel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|isBiblatexMode
argument_list|()
condition|)
block|{
name|addEntriesToPane
argument_list|(
name|biblatexPane
argument_list|,
name|BiblatexEntryTypes
operator|.
name|ALL
argument_list|)
expr_stmt|;
name|bibTexTitlePane
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|ieeeTranTitlePane
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|EntryType
argument_list|>
name|customTypes
init|=
name|EntryTypes
operator|.
name|getAllCustomTypes
argument_list|(
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
decl_stmt|;
if|if
condition|(
name|customTypes
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|customTitlePane
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|addEntriesToPane
argument_list|(
name|customPane
argument_list|,
name|customTypes
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|biblatexTitlePane
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|addEntriesToPane
argument_list|(
name|bibTexPane
argument_list|,
name|BibtexEntryTypes
operator|.
name|ALL
argument_list|)
expr_stmt|;
name|addEntriesToPane
argument_list|(
name|ieeetranPane
argument_list|,
name|IEEETranEntryTypes
operator|.
name|ALL
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|EntryType
argument_list|>
name|customTypes
init|=
name|EntryTypes
operator|.
name|getAllCustomTypes
argument_list|(
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
decl_stmt|;
if|if
condition|(
name|customTypes
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|customTitlePane
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|addEntriesToPane
argument_list|(
name|customPane
argument_list|,
name|customTypes
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|getChoice ()
specifier|public
name|EntryType
name|getChoice
parameter_list|()
block|{
return|return
name|type
return|;
block|}
annotation|@
name|FXML
DECL|method|runFetcherWorker (Event event)
specifier|private
name|void
name|runFetcherWorker
parameter_list|(
name|Event
name|event
parameter_list|)
block|{
name|viewModel
operator|.
name|runFetcherWorker
argument_list|()
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|focusTextField (Event event)
specifier|private
name|void
name|focusTextField
parameter_list|(
name|Event
name|event
parameter_list|)
block|{
name|idTextField
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
name|idTextField
operator|.
name|selectAll
argument_list|()
expr_stmt|;
block|}
DECL|method|setEntryTypeForReturnAndClose (EntryType entryType)
specifier|private
name|void
name|setEntryTypeForReturnAndClose
parameter_list|(
name|EntryType
name|entryType
parameter_list|)
block|{
name|type
operator|=
name|entryType
expr_stmt|;
name|viewModel
operator|.
name|stopFetching
argument_list|()
expr_stmt|;
name|this
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit
