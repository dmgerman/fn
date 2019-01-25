begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.openoffice
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|openoffice
package|;
end_package

begin_import
import|import
name|javax
operator|.
name|inject
operator|.
name|Inject
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
name|TableColumn
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
name|TableColumn
operator|.
name|CellEditEvent
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
name|TableView
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
name|cell
operator|.
name|TextFieldTableCell
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
name|ValueTableCellFactory
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
name|strings
operator|.
name|StringUtil
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
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|beans
operator|.
name|UnknownPropertyException
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|container
operator|.
name|NoSuchElementException
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|lang
operator|.
name|WrappedTargetException
import|;
end_import

begin_class
DECL|class|ManageCitationsDialogView
specifier|public
class|class
name|ManageCitationsDialogView
extends|extends
name|BaseDialog
argument_list|<
name|Void
argument_list|>
block|{
DECL|field|HTML_BOLD_END_TAG
specifier|private
specifier|static
specifier|final
name|String
name|HTML_BOLD_END_TAG
init|=
literal|"</b>"
decl_stmt|;
DECL|field|HTML_BOLD_START_TAG
specifier|private
specifier|static
specifier|final
name|String
name|HTML_BOLD_START_TAG
init|=
literal|"<b>"
decl_stmt|;
DECL|field|ooBase
specifier|private
specifier|final
name|OOBibBase
name|ooBase
decl_stmt|;
DECL|field|citationsTableView
annotation|@
name|FXML
specifier|private
name|TableView
argument_list|<
name|CitationEntryViewModel
argument_list|>
name|citationsTableView
decl_stmt|;
DECL|field|citation
annotation|@
name|FXML
specifier|private
name|TableColumn
argument_list|<
name|CitationEntryViewModel
argument_list|,
name|String
argument_list|>
name|citation
decl_stmt|;
DECL|field|extraInfo
annotation|@
name|FXML
specifier|private
name|TableColumn
argument_list|<
name|CitationEntryViewModel
argument_list|,
name|String
argument_list|>
name|extraInfo
decl_stmt|;
DECL|field|dialogService
annotation|@
name|Inject
specifier|private
name|DialogService
name|dialogService
decl_stmt|;
DECL|field|viewModel
specifier|private
name|ManageCitationsDialogViewModel
name|viewModel
decl_stmt|;
DECL|method|ManageCitationsDialogView (OOBibBase ooBase)
specifier|public
name|ManageCitationsDialogView
parameter_list|(
name|OOBibBase
name|ooBase
parameter_list|)
block|{
name|this
operator|.
name|ooBase
operator|=
name|ooBase
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
name|setResultConverter
argument_list|(
name|btn
lambda|->
block|{
if|if
condition|(
name|btn
operator|==
name|ButtonType
operator|.
name|OK
condition|)
block|{
name|viewModel
operator|.
name|storeSettings
argument_list|()
expr_stmt|;
block|}
return|return
literal|null
return|;
block|}
argument_list|)
expr_stmt|;
name|setTitle
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Manage citations"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|initialize ()
specifier|private
name|void
name|initialize
parameter_list|()
throws|throws
name|NoSuchElementException
throws|,
name|WrappedTargetException
throws|,
name|UnknownPropertyException
block|{
name|viewModel
operator|=
operator|new
name|ManageCitationsDialogViewModel
argument_list|(
name|ooBase
argument_list|,
name|dialogService
argument_list|)
expr_stmt|;
name|citation
operator|.
name|setCellValueFactory
argument_list|(
name|cellData
lambda|->
name|cellData
operator|.
name|getValue
argument_list|()
operator|.
name|citationProperty
argument_list|()
argument_list|)
expr_stmt|;
operator|new
name|ValueTableCellFactory
argument_list|<
name|CitationEntryViewModel
argument_list|,
name|String
argument_list|>
argument_list|()
operator|.
name|withGraphic
argument_list|(
name|this
operator|::
name|getText
argument_list|)
operator|.
name|install
argument_list|(
name|citation
argument_list|)
expr_stmt|;
name|extraInfo
operator|.
name|setCellValueFactory
argument_list|(
name|cellData
lambda|->
name|cellData
operator|.
name|getValue
argument_list|()
operator|.
name|extraInformationProperty
argument_list|()
argument_list|)
expr_stmt|;
name|extraInfo
operator|.
name|setEditable
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|citationsTableView
operator|.
name|setEditable
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|citationsTableView
operator|.
name|itemsProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|viewModel
operator|.
name|citationsProperty
argument_list|()
argument_list|)
expr_stmt|;
name|extraInfo
operator|.
name|setOnEditCommit
argument_list|(
parameter_list|(
name|CellEditEvent
argument_list|<
name|CitationEntryViewModel
argument_list|,
name|String
argument_list|>
name|cell
parameter_list|)
lambda|->
block|{
name|cell
operator|.
name|getRowValue
argument_list|()
operator|.
name|setExtraInfo
argument_list|(
name|cell
operator|.
name|getNewValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|extraInfo
operator|.
name|setCellFactory
argument_list|(
name|TextFieldTableCell
operator|.
name|forTableColumn
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|getText (String citationContext)
specifier|private
name|Node
name|getText
parameter_list|(
name|String
name|citationContext
parameter_list|)
block|{
name|String
name|inBetween
init|=
name|StringUtil
operator|.
name|substringBetween
argument_list|(
name|citationContext
argument_list|,
name|HTML_BOLD_START_TAG
argument_list|,
name|HTML_BOLD_END_TAG
argument_list|)
decl_stmt|;
name|String
name|start
init|=
name|citationContext
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|citationContext
operator|.
name|indexOf
argument_list|(
name|HTML_BOLD_START_TAG
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|end
init|=
name|citationContext
operator|.
name|substring
argument_list|(
name|citationContext
operator|.
name|lastIndexOf
argument_list|(
name|HTML_BOLD_END_TAG
argument_list|)
operator|+
name|HTML_BOLD_END_TAG
operator|.
name|length
argument_list|()
argument_list|,
name|citationContext
operator|.
name|length
argument_list|()
argument_list|)
decl_stmt|;
name|Text
name|startText
init|=
operator|new
name|Text
argument_list|(
name|start
argument_list|)
decl_stmt|;
name|Text
name|inBetweenText
init|=
operator|new
name|Text
argument_list|(
name|inBetween
argument_list|)
decl_stmt|;
name|inBetweenText
operator|.
name|setStyle
argument_list|(
literal|"-fx-font-weight: bold"
argument_list|)
expr_stmt|;
name|Text
name|endText
init|=
operator|new
name|Text
argument_list|(
name|end
argument_list|)
decl_stmt|;
name|FlowPane
name|flow
init|=
operator|new
name|FlowPane
argument_list|(
name|startText
argument_list|,
name|inBetweenText
argument_list|,
name|endText
argument_list|)
decl_stmt|;
return|return
name|flow
return|;
block|}
block|}
end_class

end_unit

