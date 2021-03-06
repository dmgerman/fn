begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.util.component
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|util
operator|.
name|component
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
name|function
operator|.
name|BiConsumer
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
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|ListProperty
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|SimpleListProperty
import|;
end_import

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
name|ListChangeListener
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
name|layout
operator|.
name|HBox
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|util
operator|.
name|StringConverter
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

begin_comment
comment|/**  * Field editor that provides various pre-defined options as a drop-down combobox.  */
end_comment

begin_class
DECL|class|TagBar
specifier|public
class|class
name|TagBar
parameter_list|<
name|T
parameter_list|>
extends|extends
name|HBox
block|{
DECL|field|tags
specifier|private
specifier|final
name|ListProperty
argument_list|<
name|T
argument_list|>
name|tags
decl_stmt|;
DECL|field|stringConverter
specifier|private
name|StringConverter
argument_list|<
name|T
argument_list|>
name|stringConverter
decl_stmt|;
DECL|field|inputTextField
annotation|@
name|FXML
specifier|private
name|TextField
name|inputTextField
decl_stmt|;
DECL|field|tagList
annotation|@
name|FXML
specifier|private
name|HBox
name|tagList
decl_stmt|;
DECL|field|onTagClicked
specifier|private
name|BiConsumer
argument_list|<
name|T
argument_list|,
name|MouseEvent
argument_list|>
name|onTagClicked
decl_stmt|;
DECL|method|TagBar ()
specifier|public
name|TagBar
parameter_list|()
block|{
name|tags
operator|=
operator|new
name|SimpleListProperty
argument_list|<>
argument_list|(
name|FXCollections
operator|.
name|observableArrayList
argument_list|()
argument_list|)
expr_stmt|;
name|tags
operator|.
name|addListener
argument_list|(
name|this
operator|::
name|onTagsChanged
argument_list|)
expr_stmt|;
comment|// Load FXML
name|ViewLoader
operator|.
name|view
argument_list|(
name|this
argument_list|)
operator|.
name|root
argument_list|(
name|this
argument_list|)
operator|.
name|load
argument_list|()
expr_stmt|;
name|getStylesheets
argument_list|()
operator|.
name|add
argument_list|(
literal|0
argument_list|,
name|TagBar
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"TagBar.css"
argument_list|)
operator|.
name|toExternalForm
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|getInputTextField ()
specifier|public
name|TextField
name|getInputTextField
parameter_list|()
block|{
return|return
name|inputTextField
return|;
block|}
DECL|method|getTags ()
specifier|public
name|ObservableList
argument_list|<
name|T
argument_list|>
name|getTags
parameter_list|()
block|{
return|return
name|tags
operator|.
name|get
argument_list|()
return|;
block|}
DECL|method|setTags (Collection<T> newTags)
specifier|public
name|void
name|setTags
parameter_list|(
name|Collection
argument_list|<
name|T
argument_list|>
name|newTags
parameter_list|)
block|{
name|this
operator|.
name|tags
operator|.
name|setAll
argument_list|(
name|tags
argument_list|)
expr_stmt|;
block|}
DECL|method|tagsProperty ()
specifier|public
name|ListProperty
argument_list|<
name|T
argument_list|>
name|tagsProperty
parameter_list|()
block|{
return|return
name|tags
return|;
block|}
DECL|method|onTagsChanged (ListChangeListener.Change<? extends T> change)
specifier|private
name|void
name|onTagsChanged
parameter_list|(
name|ListChangeListener
operator|.
name|Change
argument_list|<
name|?
extends|extends
name|T
argument_list|>
name|change
parameter_list|)
block|{
while|while
condition|(
name|change
operator|.
name|next
argument_list|()
condition|)
block|{
if|if
condition|(
name|change
operator|.
name|wasRemoved
argument_list|()
condition|)
block|{
name|tagList
operator|.
name|getChildren
argument_list|()
operator|.
name|subList
argument_list|(
name|change
operator|.
name|getFrom
argument_list|()
argument_list|,
name|change
operator|.
name|getFrom
argument_list|()
operator|+
name|change
operator|.
name|getRemovedSize
argument_list|()
argument_list|)
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|change
operator|.
name|wasAdded
argument_list|()
condition|)
block|{
name|tagList
operator|.
name|getChildren
argument_list|()
operator|.
name|addAll
argument_list|(
name|change
operator|.
name|getFrom
argument_list|()
argument_list|,
name|change
operator|.
name|getAddedSubList
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|this
operator|::
name|createTag
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|createTag (T item)
specifier|private
name|Tag
argument_list|<
name|T
argument_list|>
name|createTag
parameter_list|(
name|T
name|item
parameter_list|)
block|{
name|Tag
argument_list|<
name|T
argument_list|>
name|tag
init|=
operator|new
name|Tag
argument_list|<>
argument_list|(
name|stringConverter
operator|::
name|toString
argument_list|)
decl_stmt|;
name|tag
operator|.
name|setOnTagRemoved
argument_list|(
name|tags
operator|::
name|remove
argument_list|)
expr_stmt|;
name|tag
operator|.
name|setValue
argument_list|(
name|item
argument_list|)
expr_stmt|;
if|if
condition|(
name|onTagClicked
operator|!=
literal|null
condition|)
block|{
name|tag
operator|.
name|setOnMouseClicked
argument_list|(
name|event
lambda|->
name|onTagClicked
operator|.
name|accept
argument_list|(
name|item
argument_list|,
name|event
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|tag
return|;
block|}
annotation|@
name|FXML
DECL|method|addTextAsNewTag (ActionEvent event)
specifier|private
name|void
name|addTextAsNewTag
parameter_list|(
name|ActionEvent
name|event
parameter_list|)
block|{
name|String
name|inputText
init|=
name|inputTextField
operator|.
name|getText
argument_list|()
decl_stmt|;
if|if
condition|(
name|StringUtil
operator|.
name|isNotBlank
argument_list|(
name|inputText
argument_list|)
condition|)
block|{
name|T
name|newTag
init|=
name|stringConverter
operator|.
name|fromString
argument_list|(
name|inputText
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|newTag
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|tags
operator|.
name|contains
argument_list|(
name|newTag
argument_list|)
condition|)
block|{
name|tags
operator|.
name|add
argument_list|(
name|newTag
argument_list|)
expr_stmt|;
name|inputTextField
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
block|}
block|}
DECL|method|setStringConverter (StringConverter<T> stringConverter)
specifier|public
name|void
name|setStringConverter
parameter_list|(
name|StringConverter
argument_list|<
name|T
argument_list|>
name|stringConverter
parameter_list|)
block|{
name|this
operator|.
name|stringConverter
operator|=
name|stringConverter
expr_stmt|;
block|}
DECL|method|setOnTagClicked (BiConsumer<T, MouseEvent> onTagClicked)
specifier|public
name|void
name|setOnTagClicked
parameter_list|(
name|BiConsumer
argument_list|<
name|T
argument_list|,
name|MouseEvent
argument_list|>
name|onTagClicked
parameter_list|)
block|{
name|this
operator|.
name|onTagClicked
operator|=
name|onTagClicked
expr_stmt|;
block|}
block|}
end_class

end_unit

