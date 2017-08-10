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
name|List
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
name|Predicate
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
name|binding
operator|.
name|Bindings
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
name|BooleanProperty
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
name|ObjectProperty
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
name|SimpleObjectProperty
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|value
operator|.
name|ObservableValue
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
name|collections
operator|.
name|transformation
operator|.
name|FilteredList
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
name|TreeItem
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

begin_comment
comment|/**  * Taken from https://gist.github.com/lestard/011e9ed4433f9eb791a8  */
end_comment

begin_class
DECL|class|RecursiveTreeItem
specifier|public
class|class
name|RecursiveTreeItem
parameter_list|<
name|T
parameter_list|>
extends|extends
name|TreeItem
argument_list|<
name|T
argument_list|>
block|{
DECL|field|expandedProperty
specifier|private
specifier|final
name|Callback
argument_list|<
name|T
argument_list|,
name|BooleanProperty
argument_list|>
name|expandedProperty
decl_stmt|;
DECL|field|childrenFactory
specifier|private
name|Callback
argument_list|<
name|T
argument_list|,
name|ObservableList
argument_list|<
name|T
argument_list|>
argument_list|>
name|childrenFactory
decl_stmt|;
DECL|field|filter
specifier|private
name|ObjectProperty
argument_list|<
name|Predicate
argument_list|<
name|T
argument_list|>
argument_list|>
name|filter
init|=
operator|new
name|SimpleObjectProperty
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|children
specifier|private
name|FilteredList
argument_list|<
name|T
argument_list|>
name|children
decl_stmt|;
DECL|method|RecursiveTreeItem (final T value, Callback<T, ObservableList<T>> func)
specifier|public
name|RecursiveTreeItem
parameter_list|(
specifier|final
name|T
name|value
parameter_list|,
name|Callback
argument_list|<
name|T
argument_list|,
name|ObservableList
argument_list|<
name|T
argument_list|>
argument_list|>
name|func
parameter_list|)
block|{
name|this
argument_list|(
name|value
argument_list|,
name|func
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
DECL|method|RecursiveTreeItem (final T value, Callback<T, ObservableList<T>> func, Callback<T, BooleanProperty> expandedProperty, ObservableValue<Predicate<T>> filter)
specifier|public
name|RecursiveTreeItem
parameter_list|(
specifier|final
name|T
name|value
parameter_list|,
name|Callback
argument_list|<
name|T
argument_list|,
name|ObservableList
argument_list|<
name|T
argument_list|>
argument_list|>
name|func
parameter_list|,
name|Callback
argument_list|<
name|T
argument_list|,
name|BooleanProperty
argument_list|>
name|expandedProperty
parameter_list|,
name|ObservableValue
argument_list|<
name|Predicate
argument_list|<
name|T
argument_list|>
argument_list|>
name|filter
parameter_list|)
block|{
name|this
argument_list|(
name|value
argument_list|,
literal|null
argument_list|,
name|func
argument_list|,
name|expandedProperty
argument_list|,
name|filter
argument_list|)
expr_stmt|;
block|}
DECL|method|RecursiveTreeItem (final T value, Callback<T, ObservableList<T>> func, ObservableValue<Predicate<T>> filter)
specifier|public
name|RecursiveTreeItem
parameter_list|(
specifier|final
name|T
name|value
parameter_list|,
name|Callback
argument_list|<
name|T
argument_list|,
name|ObservableList
argument_list|<
name|T
argument_list|>
argument_list|>
name|func
parameter_list|,
name|ObservableValue
argument_list|<
name|Predicate
argument_list|<
name|T
argument_list|>
argument_list|>
name|filter
parameter_list|)
block|{
name|this
argument_list|(
name|value
argument_list|,
literal|null
argument_list|,
name|func
argument_list|,
literal|null
argument_list|,
name|filter
argument_list|)
expr_stmt|;
block|}
DECL|method|RecursiveTreeItem (final T value, Node graphic, Callback<T, ObservableList<T>> func, Callback<T, BooleanProperty> expandedProperty, ObservableValue<Predicate<T>> filter)
specifier|private
name|RecursiveTreeItem
parameter_list|(
specifier|final
name|T
name|value
parameter_list|,
name|Node
name|graphic
parameter_list|,
name|Callback
argument_list|<
name|T
argument_list|,
name|ObservableList
argument_list|<
name|T
argument_list|>
argument_list|>
name|func
parameter_list|,
name|Callback
argument_list|<
name|T
argument_list|,
name|BooleanProperty
argument_list|>
name|expandedProperty
parameter_list|,
name|ObservableValue
argument_list|<
name|Predicate
argument_list|<
name|T
argument_list|>
argument_list|>
name|filter
parameter_list|)
block|{
name|super
argument_list|(
name|value
argument_list|,
name|graphic
argument_list|)
expr_stmt|;
name|this
operator|.
name|childrenFactory
operator|=
name|func
expr_stmt|;
name|this
operator|.
name|expandedProperty
operator|=
name|expandedProperty
expr_stmt|;
if|if
condition|(
name|filter
operator|!=
literal|null
condition|)
block|{
name|this
operator|.
name|filter
operator|.
name|bind
argument_list|(
name|filter
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
block|{
name|addChildrenListener
argument_list|(
name|value
argument_list|)
expr_stmt|;
name|bindExpandedProperty
argument_list|(
name|value
argument_list|,
name|expandedProperty
argument_list|)
expr_stmt|;
block|}
name|valueProperty
argument_list|()
operator|.
name|addListener
argument_list|(
parameter_list|(
name|obs
parameter_list|,
name|oldValue
parameter_list|,
name|newValue
parameter_list|)
lambda|->
block|{
if|if
condition|(
name|newValue
operator|!=
literal|null
condition|)
block|{
name|addChildrenListener
argument_list|(
name|newValue
argument_list|)
expr_stmt|;
name|bindExpandedProperty
argument_list|(
name|newValue
argument_list|,
name|expandedProperty
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|bindExpandedProperty (T value, Callback<T, BooleanProperty> expandedProperty)
specifier|private
name|void
name|bindExpandedProperty
parameter_list|(
name|T
name|value
parameter_list|,
name|Callback
argument_list|<
name|T
argument_list|,
name|BooleanProperty
argument_list|>
name|expandedProperty
parameter_list|)
block|{
if|if
condition|(
name|expandedProperty
operator|!=
literal|null
condition|)
block|{
name|expandedProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|expandedProperty
operator|.
name|call
argument_list|(
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|addChildrenListener (T value)
specifier|private
name|void
name|addChildrenListener
parameter_list|(
name|T
name|value
parameter_list|)
block|{
name|children
operator|=
operator|new
name|FilteredList
argument_list|<>
argument_list|(
name|childrenFactory
operator|.
name|call
argument_list|(
name|value
argument_list|)
argument_list|)
expr_stmt|;
name|children
operator|.
name|predicateProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|Bindings
operator|.
name|createObjectBinding
argument_list|(
parameter_list|()
lambda|->
name|this
operator|::
name|showNode
argument_list|,
name|filter
argument_list|)
argument_list|)
expr_stmt|;
name|addAsChildren
argument_list|(
name|children
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|children
operator|.
name|addListener
argument_list|(
operator|(
name|ListChangeListener
argument_list|<
name|T
argument_list|>
operator|)
name|change
lambda|->
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
name|change
operator|.
name|getRemoved
argument_list|()
operator|.
name|forEach
argument_list|(
name|t
lambda|->
block|{
specifier|final
name|List
argument_list|<
name|TreeItem
argument_list|<
name|T
argument_list|>
argument_list|>
name|itemsToRemove
init|=
name|getChildren
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|treeItem
lambda|->
name|treeItem
operator|.
name|getValue
argument_list|()
operator|.
name|equals
argument_list|(
name|t
argument_list|)
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
decl_stmt|;
name|getChildren
argument_list|()
operator|.
name|removeAll
argument_list|(
name|itemsToRemove
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|change
operator|.
name|wasAdded
argument_list|()
condition|)
block|{
name|addAsChildren
argument_list|(
name|change
operator|.
name|getAddedSubList
argument_list|()
argument_list|,
name|change
operator|.
name|getFrom
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|addAsChildren (List<? extends T> children, int startIndex)
specifier|private
name|void
name|addAsChildren
parameter_list|(
name|List
argument_list|<
name|?
extends|extends
name|T
argument_list|>
name|children
parameter_list|,
name|int
name|startIndex
parameter_list|)
block|{
name|List
argument_list|<
name|RecursiveTreeItem
argument_list|<
name|T
argument_list|>
argument_list|>
name|treeItems
init|=
name|children
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|child
lambda|->
operator|new
name|RecursiveTreeItem
argument_list|<>
argument_list|(
name|child
argument_list|,
name|getGraphic
argument_list|()
argument_list|,
name|childrenFactory
argument_list|,
name|expandedProperty
argument_list|,
name|filter
argument_list|)
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
decl_stmt|;
name|getChildren
argument_list|()
operator|.
name|addAll
argument_list|(
name|startIndex
argument_list|,
name|treeItems
argument_list|)
expr_stmt|;
block|}
DECL|method|showNode (T t)
specifier|private
name|boolean
name|showNode
parameter_list|(
name|T
name|t
parameter_list|)
block|{
if|if
condition|(
name|filter
operator|.
name|get
argument_list|()
operator|==
literal|null
condition|)
block|{
return|return
literal|true
return|;
block|}
if|if
condition|(
name|filter
operator|.
name|get
argument_list|()
operator|.
name|test
argument_list|(
name|t
argument_list|)
condition|)
block|{
comment|// Node is directly matched -> so show it
return|return
literal|true
return|;
block|}
comment|// Are there children (or children of children...) that are matched? If yes we also need to show this node
return|return
name|childrenFactory
operator|.
name|call
argument_list|(
name|t
argument_list|)
operator|.
name|stream
argument_list|()
operator|.
name|anyMatch
argument_list|(
name|this
operator|::
name|showNode
argument_list|)
return|;
block|}
block|}
end_class

end_unit

