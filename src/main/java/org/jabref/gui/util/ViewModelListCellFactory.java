begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.util
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|util
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|function
operator|.
name|BiConsumer
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
name|ListCell
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
name|Tooltip
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|input
operator|.
name|DragEvent
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|input
operator|.
name|MouseEvent
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|paint
operator|.
name|Paint
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
name|javafx
operator|.
name|util
operator|.
name|Callback
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
name|de
operator|.
name|jensd
operator|.
name|fx
operator|.
name|glyphs
operator|.
name|GlyphIcons
import|;
end_import

begin_import
import|import
name|de
operator|.
name|jensd
operator|.
name|fx
operator|.
name|glyphs
operator|.
name|materialdesignicons
operator|.
name|utils
operator|.
name|MaterialDesignIconFactory
import|;
end_import

begin_comment
comment|/**  * Constructs a {@link ListCell} based on the view model of the row and a bunch of specified converter methods.  *  * @param<T> cell value  */
end_comment

begin_class
DECL|class|ViewModelListCellFactory
specifier|public
class|class
name|ViewModelListCellFactory
parameter_list|<
name|T
parameter_list|>
implements|implements
name|Callback
argument_list|<
name|ListView
argument_list|<
name|T
argument_list|>
argument_list|,
name|ListCell
argument_list|<
name|T
argument_list|>
argument_list|>
block|{
DECL|field|toText
specifier|private
name|Callback
argument_list|<
name|T
argument_list|,
name|String
argument_list|>
name|toText
decl_stmt|;
DECL|field|toGraphic
specifier|private
name|Callback
argument_list|<
name|T
argument_list|,
name|Node
argument_list|>
name|toGraphic
decl_stmt|;
DECL|field|toTooltip
specifier|private
name|Callback
argument_list|<
name|T
argument_list|,
name|String
argument_list|>
name|toTooltip
decl_stmt|;
DECL|field|toOnMouseClickedEvent
specifier|private
name|BiConsumer
argument_list|<
name|T
argument_list|,
name|?
super|super
name|MouseEvent
argument_list|>
name|toOnMouseClickedEvent
decl_stmt|;
DECL|field|toStyleClass
specifier|private
name|Callback
argument_list|<
name|T
argument_list|,
name|String
argument_list|>
name|toStyleClass
decl_stmt|;
DECL|field|toContextMenu
specifier|private
name|Callback
argument_list|<
name|T
argument_list|,
name|ContextMenu
argument_list|>
name|toContextMenu
decl_stmt|;
DECL|field|toOnDragDetected
specifier|private
name|BiConsumer
argument_list|<
name|T
argument_list|,
name|?
super|super
name|MouseEvent
argument_list|>
name|toOnDragDetected
decl_stmt|;
DECL|field|toOnDragDropped
specifier|private
name|BiConsumer
argument_list|<
name|T
argument_list|,
name|?
super|super
name|DragEvent
argument_list|>
name|toOnDragDropped
decl_stmt|;
DECL|field|toOnDragEntered
specifier|private
name|BiConsumer
argument_list|<
name|T
argument_list|,
name|?
super|super
name|DragEvent
argument_list|>
name|toOnDragEntered
decl_stmt|;
DECL|field|toOnDragExited
specifier|private
name|BiConsumer
argument_list|<
name|T
argument_list|,
name|?
super|super
name|DragEvent
argument_list|>
name|toOnDragExited
decl_stmt|;
DECL|field|toOnDragOver
specifier|private
name|BiConsumer
argument_list|<
name|T
argument_list|,
name|?
super|super
name|DragEvent
argument_list|>
name|toOnDragOver
decl_stmt|;
DECL|method|withText (Callback<T, String> toText)
specifier|public
name|ViewModelListCellFactory
argument_list|<
name|T
argument_list|>
name|withText
parameter_list|(
name|Callback
argument_list|<
name|T
argument_list|,
name|String
argument_list|>
name|toText
parameter_list|)
block|{
name|this
operator|.
name|toText
operator|=
name|toText
expr_stmt|;
return|return
name|this
return|;
block|}
DECL|method|withGraphic (Callback<T, Node> toGraphic)
specifier|public
name|ViewModelListCellFactory
argument_list|<
name|T
argument_list|>
name|withGraphic
parameter_list|(
name|Callback
argument_list|<
name|T
argument_list|,
name|Node
argument_list|>
name|toGraphic
parameter_list|)
block|{
name|this
operator|.
name|toGraphic
operator|=
name|toGraphic
expr_stmt|;
return|return
name|this
return|;
block|}
DECL|method|withIcon (Callback<T, GlyphIcons> toIcon)
specifier|public
name|ViewModelListCellFactory
argument_list|<
name|T
argument_list|>
name|withIcon
parameter_list|(
name|Callback
argument_list|<
name|T
argument_list|,
name|GlyphIcons
argument_list|>
name|toIcon
parameter_list|)
block|{
name|this
operator|.
name|toGraphic
operator|=
name|viewModel
lambda|->
name|MaterialDesignIconFactory
operator|.
name|get
argument_list|()
operator|.
name|createIcon
argument_list|(
name|toIcon
operator|.
name|call
argument_list|(
name|viewModel
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|this
return|;
block|}
DECL|method|withIcon (Callback<T, GlyphIcons> toIcon, Callback<T, Paint> toColor)
specifier|public
name|ViewModelListCellFactory
argument_list|<
name|T
argument_list|>
name|withIcon
parameter_list|(
name|Callback
argument_list|<
name|T
argument_list|,
name|GlyphIcons
argument_list|>
name|toIcon
parameter_list|,
name|Callback
argument_list|<
name|T
argument_list|,
name|Paint
argument_list|>
name|toColor
parameter_list|)
block|{
name|this
operator|.
name|toGraphic
operator|=
name|viewModel
lambda|->
block|{
name|Text
name|graphic
init|=
name|MaterialDesignIconFactory
operator|.
name|get
argument_list|()
operator|.
name|createIcon
argument_list|(
name|toIcon
operator|.
name|call
argument_list|(
name|viewModel
argument_list|)
argument_list|)
decl_stmt|;
name|graphic
operator|.
name|setFill
argument_list|(
name|toColor
operator|.
name|call
argument_list|(
name|viewModel
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|graphic
return|;
block|}
expr_stmt|;
return|return
name|this
return|;
block|}
DECL|method|withTooltip (Callback<T, String> toTooltip)
specifier|public
name|ViewModelListCellFactory
argument_list|<
name|T
argument_list|>
name|withTooltip
parameter_list|(
name|Callback
argument_list|<
name|T
argument_list|,
name|String
argument_list|>
name|toTooltip
parameter_list|)
block|{
name|this
operator|.
name|toTooltip
operator|=
name|toTooltip
expr_stmt|;
return|return
name|this
return|;
block|}
DECL|method|withContextMenu (Callback<T, ContextMenu> toContextMenu)
specifier|public
name|ViewModelListCellFactory
argument_list|<
name|T
argument_list|>
name|withContextMenu
parameter_list|(
name|Callback
argument_list|<
name|T
argument_list|,
name|ContextMenu
argument_list|>
name|toContextMenu
parameter_list|)
block|{
name|this
operator|.
name|toContextMenu
operator|=
name|toContextMenu
expr_stmt|;
return|return
name|this
return|;
block|}
DECL|method|withStyleClass (Callback<T, String> toStyleClass)
specifier|public
name|ViewModelListCellFactory
argument_list|<
name|T
argument_list|>
name|withStyleClass
parameter_list|(
name|Callback
argument_list|<
name|T
argument_list|,
name|String
argument_list|>
name|toStyleClass
parameter_list|)
block|{
name|this
operator|.
name|toStyleClass
operator|=
name|toStyleClass
expr_stmt|;
return|return
name|this
return|;
block|}
DECL|method|withOnMouseClickedEvent ( BiConsumer<T, ? super MouseEvent> toOnMouseClickedEvent)
specifier|public
name|ViewModelListCellFactory
argument_list|<
name|T
argument_list|>
name|withOnMouseClickedEvent
parameter_list|(
name|BiConsumer
argument_list|<
name|T
argument_list|,
name|?
super|super
name|MouseEvent
argument_list|>
name|toOnMouseClickedEvent
parameter_list|)
block|{
name|this
operator|.
name|toOnMouseClickedEvent
operator|=
name|toOnMouseClickedEvent
expr_stmt|;
return|return
name|this
return|;
block|}
DECL|method|setOnDragDetected (BiConsumer<T, ? super MouseEvent> toOnDragDetected)
specifier|public
name|ViewModelListCellFactory
argument_list|<
name|T
argument_list|>
name|setOnDragDetected
parameter_list|(
name|BiConsumer
argument_list|<
name|T
argument_list|,
name|?
super|super
name|MouseEvent
argument_list|>
name|toOnDragDetected
parameter_list|)
block|{
name|this
operator|.
name|toOnDragDetected
operator|=
name|toOnDragDetected
expr_stmt|;
return|return
name|this
return|;
block|}
DECL|method|setOnDragDropped (BiConsumer<T, ? super DragEvent> toOnDragDropped)
specifier|public
name|ViewModelListCellFactory
argument_list|<
name|T
argument_list|>
name|setOnDragDropped
parameter_list|(
name|BiConsumer
argument_list|<
name|T
argument_list|,
name|?
super|super
name|DragEvent
argument_list|>
name|toOnDragDropped
parameter_list|)
block|{
name|this
operator|.
name|toOnDragDropped
operator|=
name|toOnDragDropped
expr_stmt|;
return|return
name|this
return|;
block|}
DECL|method|setOnDragEntered (BiConsumer<T, ? super DragEvent> toOnDragEntered)
specifier|public
name|ViewModelListCellFactory
argument_list|<
name|T
argument_list|>
name|setOnDragEntered
parameter_list|(
name|BiConsumer
argument_list|<
name|T
argument_list|,
name|?
super|super
name|DragEvent
argument_list|>
name|toOnDragEntered
parameter_list|)
block|{
name|this
operator|.
name|toOnDragEntered
operator|=
name|toOnDragEntered
expr_stmt|;
return|return
name|this
return|;
block|}
DECL|method|setOnDragExited (BiConsumer<T, ? super DragEvent> toOnDragExited)
specifier|public
name|ViewModelListCellFactory
argument_list|<
name|T
argument_list|>
name|setOnDragExited
parameter_list|(
name|BiConsumer
argument_list|<
name|T
argument_list|,
name|?
super|super
name|DragEvent
argument_list|>
name|toOnDragExited
parameter_list|)
block|{
name|this
operator|.
name|toOnDragExited
operator|=
name|toOnDragExited
expr_stmt|;
return|return
name|this
return|;
block|}
DECL|method|setOnDragOver (BiConsumer<T, ? super DragEvent> toOnDragOver)
specifier|public
name|ViewModelListCellFactory
argument_list|<
name|T
argument_list|>
name|setOnDragOver
parameter_list|(
name|BiConsumer
argument_list|<
name|T
argument_list|,
name|?
super|super
name|DragEvent
argument_list|>
name|toOnDragOver
parameter_list|)
block|{
name|this
operator|.
name|toOnDragOver
operator|=
name|toOnDragOver
expr_stmt|;
return|return
name|this
return|;
block|}
DECL|method|install (ComboBox<T> comboBox)
specifier|public
name|void
name|install
parameter_list|(
name|ComboBox
argument_list|<
name|T
argument_list|>
name|comboBox
parameter_list|)
block|{
name|comboBox
operator|.
name|setButtonCell
argument_list|(
name|this
operator|.
name|call
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|comboBox
operator|.
name|setCellFactory
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
DECL|method|install (ListView<T> listView)
specifier|public
name|void
name|install
parameter_list|(
name|ListView
argument_list|<
name|T
argument_list|>
name|listView
parameter_list|)
block|{
name|listView
operator|.
name|setCellFactory
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|call (ListView<T> param)
specifier|public
name|ListCell
argument_list|<
name|T
argument_list|>
name|call
parameter_list|(
name|ListView
argument_list|<
name|T
argument_list|>
name|param
parameter_list|)
block|{
return|return
operator|new
name|ListCell
argument_list|<
name|T
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|protected
name|void
name|updateItem
parameter_list|(
name|T
name|item
parameter_list|,
name|boolean
name|empty
parameter_list|)
block|{
name|super
operator|.
name|updateItem
argument_list|(
name|item
argument_list|,
name|empty
argument_list|)
expr_stmt|;
name|T
name|viewModel
init|=
name|getItem
argument_list|()
decl_stmt|;
if|if
condition|(
name|empty
operator|||
operator|(
name|viewModel
operator|==
literal|null
operator|)
condition|)
block|{
name|setText
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|setGraphic
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|setOnMouseClicked
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|setTooltip
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|toText
operator|!=
literal|null
condition|)
block|{
name|setText
argument_list|(
name|toText
operator|.
name|call
argument_list|(
name|viewModel
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|toGraphic
operator|!=
literal|null
condition|)
block|{
name|setGraphic
argument_list|(
name|toGraphic
operator|.
name|call
argument_list|(
name|viewModel
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|toOnMouseClickedEvent
operator|!=
literal|null
condition|)
block|{
name|setOnMouseClicked
argument_list|(
name|event
lambda|->
name|toOnMouseClickedEvent
operator|.
name|accept
argument_list|(
name|viewModel
argument_list|,
name|event
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|toStyleClass
operator|!=
literal|null
condition|)
block|{
name|getStyleClass
argument_list|()
operator|.
name|setAll
argument_list|(
name|toStyleClass
operator|.
name|call
argument_list|(
name|viewModel
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|toTooltip
operator|!=
literal|null
condition|)
block|{
name|String
name|tooltipText
init|=
name|toTooltip
operator|.
name|call
argument_list|(
name|viewModel
argument_list|)
decl_stmt|;
if|if
condition|(
name|StringUtil
operator|.
name|isNotBlank
argument_list|(
name|tooltipText
argument_list|)
condition|)
block|{
name|setTooltip
argument_list|(
operator|new
name|Tooltip
argument_list|(
name|tooltipText
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|toContextMenu
operator|!=
literal|null
condition|)
block|{
name|setContextMenu
argument_list|(
name|toContextMenu
operator|.
name|call
argument_list|(
name|viewModel
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|toOnDragDetected
operator|!=
literal|null
condition|)
block|{
name|setOnDragDetected
argument_list|(
name|event
lambda|->
name|toOnDragDetected
operator|.
name|accept
argument_list|(
name|viewModel
argument_list|,
name|event
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|toOnDragDropped
operator|!=
literal|null
condition|)
block|{
name|setOnDragDropped
argument_list|(
name|event
lambda|->
name|toOnDragDropped
operator|.
name|accept
argument_list|(
name|viewModel
argument_list|,
name|event
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|toOnDragEntered
operator|!=
literal|null
condition|)
block|{
name|setOnDragEntered
argument_list|(
name|event
lambda|->
name|toOnDragEntered
operator|.
name|accept
argument_list|(
name|viewModel
argument_list|,
name|event
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|toOnDragExited
operator|!=
literal|null
condition|)
block|{
name|setOnDragExited
argument_list|(
name|event
lambda|->
name|toOnDragExited
operator|.
name|accept
argument_list|(
name|viewModel
argument_list|,
name|event
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|toOnDragOver
operator|!=
literal|null
condition|)
block|{
name|setOnDragOver
argument_list|(
name|event
lambda|->
name|toOnDragOver
operator|.
name|accept
argument_list|(
name|viewModel
argument_list|,
name|event
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|getListView
argument_list|()
operator|.
name|refresh
argument_list|()
expr_stmt|;
block|}
block|}
return|;
block|}
block|}
end_class

end_unit

