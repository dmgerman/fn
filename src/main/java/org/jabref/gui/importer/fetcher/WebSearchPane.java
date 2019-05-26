begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.importer.fetcher
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|importer
operator|.
name|fetcher
package|;
end_package

begin_import
import|import
name|javafx
operator|.
name|geometry
operator|.
name|Pos
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
name|layout
operator|.
name|HBox
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
name|Priority
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
name|StackPane
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
name|VBox
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
name|JabRefFrame
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
name|SidePaneComponent
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
name|SidePaneManager
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
name|SidePaneType
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
name|Action
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
name|StandardActions
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
name|help
operator|.
name|HelpAction
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
name|gui
operator|.
name|search
operator|.
name|SearchTextField
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
name|SearchBasedFetcher
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
name|preferences
operator|.
name|JabRefPreferences
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

begin_class
DECL|class|WebSearchPane
specifier|public
class|class
name|WebSearchPane
extends|extends
name|SidePaneComponent
block|{
DECL|field|preferences
specifier|private
specifier|final
name|JabRefPreferences
name|preferences
decl_stmt|;
DECL|field|viewModel
specifier|private
specifier|final
name|WebSearchPaneViewModel
name|viewModel
decl_stmt|;
DECL|method|WebSearchPane (SidePaneManager sidePaneManager, JabRefPreferences preferences, JabRefFrame frame)
specifier|public
name|WebSearchPane
parameter_list|(
name|SidePaneManager
name|sidePaneManager
parameter_list|,
name|JabRefPreferences
name|preferences
parameter_list|,
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|super
argument_list|(
name|sidePaneManager
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|WWW
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Web search"
argument_list|)
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
name|viewModel
operator|=
operator|new
name|WebSearchPaneViewModel
argument_list|(
name|preferences
operator|.
name|getImportFormatPreferences
argument_list|()
argument_list|,
name|frame
argument_list|,
name|preferences
argument_list|,
name|frame
operator|.
name|getDialogService
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getToggleAction ()
specifier|public
name|Action
name|getToggleAction
parameter_list|()
block|{
return|return
name|StandardActions
operator|.
name|TOGGLE_WEB_SEARCH
return|;
block|}
annotation|@
name|Override
DECL|method|createContentPane ()
specifier|protected
name|Node
name|createContentPane
parameter_list|()
block|{
comment|// Setup combo box for fetchers
name|ComboBox
argument_list|<
name|SearchBasedFetcher
argument_list|>
name|fetchers
init|=
operator|new
name|ComboBox
argument_list|<>
argument_list|()
decl_stmt|;
operator|new
name|ViewModelListCellFactory
argument_list|<
name|SearchBasedFetcher
argument_list|>
argument_list|()
operator|.
name|withText
argument_list|(
name|SearchBasedFetcher
operator|::
name|getName
argument_list|)
operator|.
name|install
argument_list|(
name|fetchers
argument_list|)
expr_stmt|;
name|fetchers
operator|.
name|itemsProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|viewModel
operator|.
name|fetchersProperty
argument_list|()
argument_list|)
expr_stmt|;
name|fetchers
operator|.
name|valueProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|viewModel
operator|.
name|selectedFetcherProperty
argument_list|()
argument_list|)
expr_stmt|;
name|fetchers
operator|.
name|setMaxWidth
argument_list|(
name|Double
operator|.
name|POSITIVE_INFINITY
argument_list|)
expr_stmt|;
comment|// Create help button for currently selected fetcher
name|StackPane
name|helpButtonContainer
init|=
operator|new
name|StackPane
argument_list|()
decl_stmt|;
name|ActionFactory
name|factory
init|=
operator|new
name|ActionFactory
argument_list|(
name|preferences
operator|.
name|getKeyBindingRepository
argument_list|()
argument_list|)
decl_stmt|;
name|EasyBind
operator|.
name|subscribe
argument_list|(
name|viewModel
operator|.
name|selectedFetcherProperty
argument_list|()
argument_list|,
name|fetcher
lambda|->
block|{
if|if
condition|(
operator|(
name|fetcher
operator|!=
literal|null
operator|)
operator|&&
name|fetcher
operator|.
name|getHelpPage
argument_list|()
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|Button
name|helpButton
init|=
name|factory
operator|.
name|createIconButton
argument_list|(
name|StandardActions
operator|.
name|HELP
argument_list|,
operator|new
name|HelpAction
argument_list|(
name|fetcher
operator|.
name|getHelpPage
argument_list|()
operator|.
name|get
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|helpButtonContainer
operator|.
name|getChildren
argument_list|()
operator|.
name|setAll
argument_list|(
name|helpButton
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|helpButtonContainer
operator|.
name|getChildren
argument_list|()
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|HBox
name|fetcherContainer
init|=
operator|new
name|HBox
argument_list|(
name|fetchers
argument_list|,
name|helpButtonContainer
argument_list|)
decl_stmt|;
name|HBox
operator|.
name|setHgrow
argument_list|(
name|fetchers
argument_list|,
name|Priority
operator|.
name|ALWAYS
argument_list|)
expr_stmt|;
comment|// Create text field for query input
name|TextField
name|query
init|=
name|SearchTextField
operator|.
name|create
argument_list|()
decl_stmt|;
name|query
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|viewModel
operator|.
name|search
argument_list|()
argument_list|)
expr_stmt|;
name|viewModel
operator|.
name|queryProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|query
operator|.
name|textProperty
argument_list|()
argument_list|)
expr_stmt|;
comment|// Create button that triggers search
name|Button
name|search
init|=
operator|new
name|Button
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Search"
argument_list|)
argument_list|)
decl_stmt|;
name|search
operator|.
name|setDefaultButton
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|search
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|viewModel
operator|.
name|search
argument_list|()
argument_list|)
expr_stmt|;
comment|// Put everything together
name|VBox
name|container
init|=
operator|new
name|VBox
argument_list|()
decl_stmt|;
name|container
operator|.
name|setAlignment
argument_list|(
name|Pos
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|container
operator|.
name|getChildren
argument_list|()
operator|.
name|addAll
argument_list|(
name|fetcherContainer
argument_list|,
name|query
argument_list|,
name|search
argument_list|)
expr_stmt|;
return|return
name|container
return|;
block|}
annotation|@
name|Override
DECL|method|getType ()
specifier|public
name|SidePaneType
name|getType
parameter_list|()
block|{
return|return
name|SidePaneType
operator|.
name|WEB_SEARCH
return|;
block|}
annotation|@
name|Override
DECL|method|beforeClosing ()
specifier|public
name|void
name|beforeClosing
parameter_list|()
block|{
name|preferences
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|WEB_SEARCH_VISIBLE
argument_list|,
name|Boolean
operator|.
name|FALSE
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|afterOpening ()
specifier|public
name|void
name|afterOpening
parameter_list|()
block|{
name|preferences
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|WEB_SEARCH_VISIBLE
argument_list|,
name|Boolean
operator|.
name|TRUE
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getResizePolicy ()
specifier|public
name|Priority
name|getResizePolicy
parameter_list|()
block|{
return|return
name|Priority
operator|.
name|NEVER
return|;
block|}
block|}
end_class

end_unit

