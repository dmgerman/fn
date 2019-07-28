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
name|javafx
operator|.
name|collections
operator|.
name|FXCollections
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
name|ProgressIndicator
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
name|ScrollPane
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
name|javafx
operator|.
name|scene
operator|.
name|text
operator|.
name|Text
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
name|util
operator|.
name|TaskExecutor
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
name|strings
operator|.
name|LatexToUnicodeAdapter
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
name|texparser
operator|.
name|Citation
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
name|PreferencesService
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
DECL|class|LatexCitationsTab
specifier|public
class|class
name|LatexCitationsTab
extends|extends
name|EntryEditorTab
block|{
DECL|field|viewModel
specifier|private
specifier|final
name|LatexCitationsTabViewModel
name|viewModel
decl_stmt|;
DECL|field|searchPane
specifier|private
specifier|final
name|StackPane
name|searchPane
decl_stmt|;
DECL|field|progressIndicator
specifier|private
specifier|final
name|ProgressIndicator
name|progressIndicator
decl_stmt|;
DECL|field|graphicCitationList
specifier|private
specifier|final
name|ObservableList
argument_list|<
name|VBox
argument_list|>
name|graphicCitationList
decl_stmt|;
DECL|method|LatexCitationsTab (BibDatabaseContext databaseContext, PreferencesService preferencesService, TaskExecutor taskExecutor)
specifier|public
name|LatexCitationsTab
parameter_list|(
name|BibDatabaseContext
name|databaseContext
parameter_list|,
name|PreferencesService
name|preferencesService
parameter_list|,
name|TaskExecutor
name|taskExecutor
parameter_list|)
block|{
name|this
operator|.
name|viewModel
operator|=
operator|new
name|LatexCitationsTabViewModel
argument_list|(
name|databaseContext
argument_list|,
name|preferencesService
argument_list|,
name|taskExecutor
argument_list|)
expr_stmt|;
name|this
operator|.
name|searchPane
operator|=
operator|new
name|StackPane
argument_list|()
expr_stmt|;
name|this
operator|.
name|progressIndicator
operator|=
operator|new
name|ProgressIndicator
argument_list|()
expr_stmt|;
name|this
operator|.
name|graphicCitationList
operator|=
name|FXCollections
operator|.
name|observableArrayList
argument_list|()
expr_stmt|;
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"LaTeX Citations"
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
literal|"Search citations for this entry in LaTeX files"
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
name|LATEX_CITATIONS
operator|.
name|getGraphicNode
argument_list|()
argument_list|)
expr_stmt|;
name|setSearchPane
argument_list|()
expr_stmt|;
block|}
DECL|method|setSearchPane ()
specifier|private
name|void
name|setSearchPane
parameter_list|()
block|{
name|progressIndicator
operator|.
name|setMaxSize
argument_list|(
literal|100.0
argument_list|,
literal|100.0
argument_list|)
expr_stmt|;
name|searchPane
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"latex-citations-tab"
argument_list|)
expr_stmt|;
name|setContent
argument_list|(
name|searchPane
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|bindToEntry (BibEntry entry)
specifier|protected
name|void
name|bindToEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
name|EasyBind
operator|.
name|subscribe
argument_list|(
name|viewModel
operator|.
name|statusProperty
argument_list|()
argument_list|,
name|status
lambda|->
block|{
switch|switch
condition|(
name|status
condition|)
block|{
case|case
name|IN_PROGRESS
case|:
name|searchPane
operator|.
name|getChildren
argument_list|()
operator|.
name|setAll
argument_list|(
name|progressIndicator
argument_list|)
expr_stmt|;
break|break;
case|case
name|CITATIONS_FOUND
case|:
name|graphicCitationList
operator|.
name|setAll
argument_list|(
name|EasyBind
operator|.
name|map
argument_list|(
name|viewModel
operator|.
name|getCitationList
argument_list|()
argument_list|,
name|this
operator|::
name|citationToGraphic
argument_list|)
argument_list|)
expr_stmt|;
name|searchPane
operator|.
name|getChildren
argument_list|()
operator|.
name|setAll
argument_list|(
name|getCitationsPane
argument_list|()
argument_list|)
expr_stmt|;
break|break;
case|case
name|NO_RESULTS
case|:
name|searchPane
operator|.
name|getChildren
argument_list|()
operator|.
name|setAll
argument_list|(
name|getNotFoundPane
argument_list|()
argument_list|)
expr_stmt|;
break|break;
case|case
name|ERROR
case|:
name|searchPane
operator|.
name|getChildren
argument_list|()
operator|.
name|setAll
argument_list|(
name|getErrorPane
argument_list|()
argument_list|)
expr_stmt|;
break|break;
case|case
name|INACTIVE
case|:
default|default:
name|searchPane
operator|.
name|getChildren
argument_list|()
operator|.
name|clear
argument_list|()
expr_stmt|;
break|break;
block|}
block|}
argument_list|)
expr_stmt|;
name|viewModel
operator|.
name|init
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
DECL|method|citationToGraphic (Citation citation)
specifier|private
name|VBox
name|citationToGraphic
parameter_list|(
name|Citation
name|citation
parameter_list|)
block|{
name|HBox
name|contextBox
init|=
operator|new
name|HBox
argument_list|(
operator|new
name|Text
argument_list|(
name|LatexToUnicodeAdapter
operator|.
name|format
argument_list|(
name|citation
operator|.
name|getContext
argument_list|()
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|contextBox
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"latex-citations-context-box"
argument_list|)
expr_stmt|;
name|Text
name|positionText
init|=
operator|new
name|Text
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"%n%s (%s:%s-%s)"
argument_list|,
name|citation
operator|.
name|getPath
argument_list|()
operator|.
name|toAbsolutePath
argument_list|()
argument_list|,
name|citation
operator|.
name|getLine
argument_list|()
argument_list|,
name|citation
operator|.
name|getColStart
argument_list|()
argument_list|,
name|citation
operator|.
name|getColEnd
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|positionText
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"latex-citations-position-text"
argument_list|)
expr_stmt|;
return|return
operator|new
name|VBox
argument_list|(
name|contextBox
argument_list|,
name|positionText
argument_list|)
return|;
block|}
DECL|method|getCitationsPane ()
specifier|private
name|ScrollPane
name|getCitationsPane
parameter_list|()
block|{
name|Text
name|titleText
init|=
operator|new
name|Text
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Citations found"
argument_list|)
argument_list|)
decl_stmt|;
name|titleText
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"latex-citations-title-text"
argument_list|)
expr_stmt|;
name|VBox
name|citationsBox
init|=
operator|new
name|VBox
argument_list|(
literal|20.0
argument_list|,
name|titleText
argument_list|)
decl_stmt|;
name|citationsBox
operator|.
name|getChildren
argument_list|()
operator|.
name|addAll
argument_list|(
name|graphicCitationList
argument_list|)
expr_stmt|;
name|ScrollPane
name|citationsPane
init|=
operator|new
name|ScrollPane
argument_list|()
decl_stmt|;
name|citationsPane
operator|.
name|setContent
argument_list|(
name|citationsBox
argument_list|)
expr_stmt|;
return|return
name|citationsPane
return|;
block|}
DECL|method|getNotFoundPane ()
specifier|private
name|ScrollPane
name|getNotFoundPane
parameter_list|()
block|{
name|Text
name|notFoundTitleText
init|=
operator|new
name|Text
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"No citations found"
argument_list|)
argument_list|)
decl_stmt|;
name|notFoundTitleText
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"latex-citations-title-text"
argument_list|)
expr_stmt|;
name|Text
name|notFoundText
init|=
operator|new
name|Text
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"No LaTeX files containing this entry were found."
argument_list|)
argument_list|)
decl_stmt|;
name|notFoundText
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"latex-citations-not-found-text"
argument_list|)
expr_stmt|;
name|Text
name|notFoundAdviceText
init|=
operator|new
name|Text
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"You can set the LaTeX file directory in the 'Library properties' dialog."
argument_list|)
argument_list|)
decl_stmt|;
name|notFoundAdviceText
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"latex-citations-not-found-advice-text"
argument_list|)
expr_stmt|;
name|VBox
name|notFoundBox
init|=
operator|new
name|VBox
argument_list|(
literal|20.0
argument_list|,
name|notFoundTitleText
argument_list|,
name|notFoundText
argument_list|,
name|notFoundAdviceText
argument_list|)
decl_stmt|;
name|ScrollPane
name|notFoundPane
init|=
operator|new
name|ScrollPane
argument_list|()
decl_stmt|;
name|notFoundPane
operator|.
name|setContent
argument_list|(
name|notFoundBox
argument_list|)
expr_stmt|;
return|return
name|notFoundPane
return|;
block|}
DECL|method|getErrorPane ()
specifier|private
name|ScrollPane
name|getErrorPane
parameter_list|()
block|{
name|Text
name|errorTitleText
init|=
operator|new
name|Text
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error"
argument_list|)
argument_list|)
decl_stmt|;
name|errorTitleText
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"latex-citations-error-title-text"
argument_list|)
expr_stmt|;
name|Text
name|errorMessageText
init|=
operator|new
name|Text
argument_list|(
name|viewModel
operator|.
name|searchErrorProperty
argument_list|()
operator|.
name|get
argument_list|()
argument_list|)
decl_stmt|;
name|errorMessageText
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"latex-citations-error-text"
argument_list|)
expr_stmt|;
name|VBox
name|errorBox
init|=
operator|new
name|VBox
argument_list|(
literal|20.0
argument_list|,
name|errorTitleText
argument_list|,
name|errorMessageText
argument_list|)
decl_stmt|;
name|ScrollPane
name|errorPane
init|=
operator|new
name|ScrollPane
argument_list|()
decl_stmt|;
name|errorPane
operator|.
name|setContent
argument_list|(
name|errorBox
argument_list|)
expr_stmt|;
return|return
name|errorPane
return|;
block|}
annotation|@
name|Override
DECL|method|shouldShow (BibEntry entry)
specifier|public
name|boolean
name|shouldShow
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
return|return
name|viewModel
operator|.
name|shouldShow
argument_list|()
return|;
block|}
block|}
end_class

end_unit

