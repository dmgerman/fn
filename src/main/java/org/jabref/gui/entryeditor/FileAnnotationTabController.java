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
name|beans
operator|.
name|binding
operator|.
name|Bindings
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
name|Control
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
name|Label
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
name|ListView
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
name|SelectionMode
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
name|AbstractController
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
name|logic
operator|.
name|pdf
operator|.
name|FileAnnotationCache
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

begin_class
DECL|class|FileAnnotationTabController
specifier|public
class|class
name|FileAnnotationTabController
extends|extends
name|AbstractController
argument_list|<
name|FileAnnotationTabViewModel
argument_list|>
block|{
DECL|field|files
annotation|@
name|FXML
name|ComboBox
argument_list|<
name|String
argument_list|>
name|files
decl_stmt|;
DECL|field|annotationList
annotation|@
name|FXML
name|ListView
argument_list|<
name|FileAnnotationViewModel
argument_list|>
name|annotationList
decl_stmt|;
DECL|field|fileAnnotationCache
annotation|@
name|Inject
specifier|private
name|FileAnnotationCache
name|fileAnnotationCache
decl_stmt|;
DECL|field|entry
annotation|@
name|Inject
specifier|private
name|BibEntry
name|entry
decl_stmt|;
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
name|FileAnnotationTabViewModel
argument_list|(
name|fileAnnotationCache
argument_list|,
name|entry
argument_list|)
expr_stmt|;
comment|// Set-up files list
name|files
operator|.
name|getItems
argument_list|()
operator|.
name|setAll
argument_list|(
name|viewModel
operator|.
name|filesProperty
argument_list|()
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
name|files
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|selectedItemProperty
argument_list|()
operator|.
name|addListener
argument_list|(
parameter_list|(
name|observable
parameter_list|,
name|oldValue
parameter_list|,
name|newValue
parameter_list|)
lambda|->
name|viewModel
operator|.
name|notifyNewSelectedFile
argument_list|(
name|newValue
argument_list|)
argument_list|)
expr_stmt|;
name|files
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|selectFirst
argument_list|()
expr_stmt|;
comment|// Set-up annotation list
name|annotationList
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|setSelectionMode
argument_list|(
name|SelectionMode
operator|.
name|SINGLE
argument_list|)
expr_stmt|;
name|annotationList
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|selectedItemProperty
argument_list|()
operator|.
name|addListener
argument_list|(
parameter_list|(
name|observable
parameter_list|,
name|oldValue
parameter_list|,
name|newValue
parameter_list|)
lambda|->
name|viewModel
operator|.
name|notifyNewSelectedAnnotation
argument_list|(
name|newValue
argument_list|)
argument_list|)
expr_stmt|;
name|ViewModelListCellFactory
argument_list|<
name|FileAnnotationViewModel
argument_list|>
name|cellFactory
init|=
operator|new
name|ViewModelListCellFactory
argument_list|<
name|FileAnnotationViewModel
argument_list|>
argument_list|()
operator|.
name|withTooltip
argument_list|(
name|FileAnnotationViewModel
operator|::
name|getDescription
argument_list|)
operator|.
name|withGraphic
argument_list|(
name|annotation
lambda|->
block|{
name|VBox
name|node
init|=
operator|new
name|VBox
argument_list|()
decl_stmt|;
name|Text
name|text
init|=
operator|new
name|Text
argument_list|()
decl_stmt|;
name|text
operator|.
name|setText
argument_list|(
name|annotation
operator|.
name|getContent
argument_list|()
argument_list|)
expr_stmt|;
name|text
operator|.
name|getStyleClass
argument_list|()
operator|.
name|setAll
argument_list|(
literal|"text"
argument_list|)
expr_stmt|;
name|HBox
name|details
init|=
operator|new
name|HBox
argument_list|()
decl_stmt|;
name|details
operator|.
name|getStyleClass
argument_list|()
operator|.
name|setAll
argument_list|(
literal|"details"
argument_list|)
expr_stmt|;
name|Text
name|page
init|=
operator|new
name|Text
argument_list|()
decl_stmt|;
name|page
operator|.
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Page: "
argument_list|)
operator|+
name|Integer
operator|.
name|toString
argument_list|(
name|annotation
operator|.
name|getPage
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|Text
name|author
init|=
operator|new
name|Text
argument_list|()
decl_stmt|;
name|author
operator|.
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Author: "
argument_list|)
operator|+
name|annotation
operator|.
name|getAuthor
argument_list|()
argument_list|)
expr_stmt|;
name|details
operator|.
name|getChildren
argument_list|()
operator|.
name|addAll
argument_list|(
name|page
argument_list|,
name|author
argument_list|)
expr_stmt|;
name|node
operator|.
name|getChildren
argument_list|()
operator|.
name|addAll
argument_list|(
name|text
argument_list|,
name|details
argument_list|)
expr_stmt|;
name|node
operator|.
name|setMaxWidth
argument_list|(
name|Control
operator|.
name|USE_PREF_SIZE
argument_list|)
expr_stmt|;
return|return
name|node
return|;
block|}
argument_list|)
decl_stmt|;
name|annotationList
operator|.
name|setCellFactory
argument_list|(
name|cellFactory
argument_list|)
expr_stmt|;
name|annotationList
operator|.
name|setPlaceholder
argument_list|(
operator|new
name|Label
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"File has no attached annotations"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|Bindings
operator|.
name|bindContent
argument_list|(
name|annotationList
operator|.
name|itemsProperty
argument_list|()
operator|.
name|get
argument_list|()
argument_list|,
name|viewModel
operator|.
name|annotationsProperty
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

