begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.entryeditor.fileannotationtab
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|entryeditor
operator|.
name|fileannotationtab
package|;
end_package

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Path
import|;
end_import

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
name|collections
operator|.
name|ListChangeListener
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|event
operator|.
name|ActionEvent
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
name|geometry
operator|.
name|HPos
import|;
end_import

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
name|control
operator|.
name|TextArea
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
name|ColumnConstraints
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
name|GridPane
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
name|RowConstraints
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
name|Font
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
name|TextAlignment
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

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|util
operator|.
name|FileUpdateMonitor
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
annotation|@
name|FXML
DECL|field|files
specifier|public
name|ComboBox
argument_list|<
name|Path
argument_list|>
name|files
decl_stmt|;
annotation|@
name|FXML
DECL|field|annotationList
specifier|public
name|ListView
argument_list|<
name|FileAnnotationViewModel
argument_list|>
name|annotationList
decl_stmt|;
annotation|@
name|FXML
DECL|field|author
specifier|public
name|Label
name|author
decl_stmt|;
annotation|@
name|FXML
DECL|field|page
specifier|public
name|Label
name|page
decl_stmt|;
annotation|@
name|FXML
DECL|field|date
specifier|public
name|Label
name|date
decl_stmt|;
annotation|@
name|FXML
DECL|field|content
specifier|public
name|TextArea
name|content
decl_stmt|;
annotation|@
name|FXML
DECL|field|marking
specifier|public
name|TextArea
name|marking
decl_stmt|;
annotation|@
name|FXML
DECL|field|grid
specifier|public
name|GridPane
name|grid
decl_stmt|;
annotation|@
name|Inject
DECL|field|fileAnnotationCache
specifier|private
name|FileAnnotationCache
name|fileAnnotationCache
decl_stmt|;
annotation|@
name|Inject
DECL|field|entry
specifier|private
name|BibEntry
name|entry
decl_stmt|;
annotation|@
name|Inject
DECL|field|fileMonitor
specifier|private
name|FileUpdateMonitor
name|fileMonitor
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
argument_list|,
name|fileMonitor
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
name|getMarking
argument_list|)
operator|.
name|withGraphic
argument_list|(
name|annotation
lambda|->
block|{
return|return
name|createFileAnnotationNode
argument_list|(
name|annotation
argument_list|)
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
name|annotationList
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|selectFirst
argument_list|()
expr_stmt|;
name|annotationList
operator|.
name|itemsProperty
argument_list|()
operator|.
name|get
argument_list|()
operator|.
name|addListener
argument_list|(
operator|(
name|ListChangeListener
argument_list|<
name|?
super|super
name|FileAnnotationViewModel
argument_list|>
operator|)
name|c
lambda|->
name|annotationList
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|selectFirst
argument_list|()
argument_list|)
expr_stmt|;
comment|// Set-up details pane
name|content
operator|.
name|textProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|EasyBind
operator|.
name|select
argument_list|(
name|viewModel
operator|.
name|currentAnnotationProperty
argument_list|()
argument_list|)
operator|.
name|selectObject
argument_list|(
name|FileAnnotationViewModel
operator|::
name|contentProperty
argument_list|)
argument_list|)
expr_stmt|;
name|marking
operator|.
name|textProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|EasyBind
operator|.
name|select
argument_list|(
name|viewModel
operator|.
name|currentAnnotationProperty
argument_list|()
argument_list|)
operator|.
name|selectObject
argument_list|(
name|FileAnnotationViewModel
operator|::
name|markingProperty
argument_list|)
argument_list|)
expr_stmt|;
name|grid
operator|.
name|disableProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|viewModel
operator|.
name|isAnnotationsEmpty
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|createFileAnnotationNode (FileAnnotationViewModel annotation)
specifier|private
name|Node
name|createFileAnnotationNode
parameter_list|(
name|FileAnnotationViewModel
name|annotation
parameter_list|)
block|{
name|GridPane
name|node
init|=
operator|new
name|GridPane
argument_list|()
decl_stmt|;
name|ColumnConstraints
name|firstColumn
init|=
operator|new
name|ColumnConstraints
argument_list|()
decl_stmt|;
name|ColumnConstraints
name|secondColumn
init|=
operator|new
name|ColumnConstraints
argument_list|()
decl_stmt|;
name|firstColumn
operator|.
name|setPercentWidth
argument_list|(
literal|70
argument_list|)
expr_stmt|;
name|secondColumn
operator|.
name|setPercentWidth
argument_list|(
literal|30
argument_list|)
expr_stmt|;
name|firstColumn
operator|.
name|setHalignment
argument_list|(
name|HPos
operator|.
name|LEFT
argument_list|)
expr_stmt|;
name|secondColumn
operator|.
name|setHalignment
argument_list|(
name|HPos
operator|.
name|RIGHT
argument_list|)
expr_stmt|;
name|node
operator|.
name|getColumnConstraints
argument_list|()
operator|.
name|addAll
argument_list|(
name|firstColumn
argument_list|,
name|secondColumn
argument_list|)
expr_stmt|;
name|RowConstraints
name|firstRow
init|=
operator|new
name|RowConstraints
argument_list|()
decl_stmt|;
name|RowConstraints
name|secondRow
init|=
operator|new
name|RowConstraints
argument_list|()
decl_stmt|;
name|firstRow
operator|.
name|setMinHeight
argument_list|(
literal|10
argument_list|)
expr_stmt|;
name|firstRow
operator|.
name|setPrefHeight
argument_list|(
literal|15
argument_list|)
expr_stmt|;
name|secondRow
operator|.
name|setMinHeight
argument_list|(
literal|10
argument_list|)
expr_stmt|;
name|secondRow
operator|.
name|setPrefHeight
argument_list|(
literal|35
argument_list|)
expr_stmt|;
name|node
operator|.
name|getRowConstraints
argument_list|()
operator|.
name|addAll
argument_list|(
name|firstRow
argument_list|,
name|secondRow
argument_list|)
expr_stmt|;
name|Label
name|marking
init|=
operator|new
name|Label
argument_list|(
name|annotation
operator|.
name|getMarking
argument_list|()
argument_list|)
decl_stmt|;
name|Label
name|author
init|=
operator|new
name|Label
argument_list|(
name|annotation
operator|.
name|getAuthor
argument_list|()
argument_list|)
decl_stmt|;
name|Label
name|date
init|=
operator|new
name|Label
argument_list|(
name|annotation
operator|.
name|getDate
argument_list|()
argument_list|)
decl_stmt|;
name|Label
name|page
init|=
operator|new
name|Label
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Page"
argument_list|)
operator|+
literal|": "
operator|+
name|annotation
operator|.
name|getPage
argument_list|()
argument_list|)
decl_stmt|;
name|marking
operator|.
name|setFont
argument_list|(
operator|new
name|Font
argument_list|(
literal|"System Bold"
argument_list|,
literal|15
argument_list|)
argument_list|)
expr_stmt|;
name|marking
operator|.
name|setPrefWidth
argument_list|(
literal|250
argument_list|)
expr_stmt|;
name|author
operator|.
name|setFont
argument_list|(
operator|new
name|Font
argument_list|(
literal|"System"
argument_list|,
literal|14
argument_list|)
argument_list|)
expr_stmt|;
name|marking
operator|.
name|setPrefHeight
argument_list|(
literal|10
argument_list|)
expr_stmt|;
name|author
operator|.
name|setPrefHeight
argument_list|(
literal|30
argument_list|)
expr_stmt|;
name|date
operator|.
name|setPrefHeight
argument_list|(
literal|10
argument_list|)
expr_stmt|;
name|page
operator|.
name|setPrefHeight
argument_list|(
literal|30
argument_list|)
expr_stmt|;
comment|// add alignment for text in the list
name|marking
operator|.
name|setTextAlignment
argument_list|(
name|TextAlignment
operator|.
name|LEFT
argument_list|)
expr_stmt|;
name|marking
operator|.
name|setAlignment
argument_list|(
name|Pos
operator|.
name|TOP_LEFT
argument_list|)
expr_stmt|;
name|author
operator|.
name|setTextAlignment
argument_list|(
name|TextAlignment
operator|.
name|LEFT
argument_list|)
expr_stmt|;
name|author
operator|.
name|setAlignment
argument_list|(
name|Pos
operator|.
name|TOP_LEFT
argument_list|)
expr_stmt|;
name|date
operator|.
name|setTextAlignment
argument_list|(
name|TextAlignment
operator|.
name|RIGHT
argument_list|)
expr_stmt|;
name|date
operator|.
name|setAlignment
argument_list|(
name|Pos
operator|.
name|TOP_RIGHT
argument_list|)
expr_stmt|;
name|page
operator|.
name|setTextAlignment
argument_list|(
name|TextAlignment
operator|.
name|RIGHT
argument_list|)
expr_stmt|;
name|page
operator|.
name|setAlignment
argument_list|(
name|Pos
operator|.
name|TOP_RIGHT
argument_list|)
expr_stmt|;
name|node
operator|.
name|add
argument_list|(
name|marking
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|node
operator|.
name|add
argument_list|(
name|author
argument_list|,
literal|0
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|node
operator|.
name|add
argument_list|(
name|date
argument_list|,
literal|1
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|node
operator|.
name|add
argument_list|(
name|page
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|)
expr_stmt|;
return|return
name|node
return|;
block|}
DECL|method|copy (ActionEvent event)
specifier|public
name|void
name|copy
parameter_list|(
name|ActionEvent
name|event
parameter_list|)
block|{
name|viewModel
operator|.
name|copyCurrentAnnotation
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

