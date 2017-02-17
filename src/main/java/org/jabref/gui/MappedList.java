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
name|ArrayList
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
name|java
operator|.
name|util
operator|.
name|function
operator|.
name|Function
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|collections
operator|.
name|ListChangeListener
operator|.
name|Change
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
name|TransformationList
import|;
end_import

begin_comment
comment|/**  * Mapped view of a {@link ObservableList}.  *  * It is a planned to include a similar version of this class in the Java core, see  * https://bugs.openjdk.java.net/browse/JDK-8091967  * Until it is available in Java, we use a own implementation.  * Source code taken from  * https://gist.github.com/TomasMikula/8883719#file-mappedlist-java-L1  *  * @param<E> source type  * @param<F> target type  */
end_comment

begin_class
DECL|class|MappedList
specifier|public
class|class
name|MappedList
parameter_list|<
name|E
parameter_list|,
name|F
parameter_list|>
extends|extends
name|TransformationList
argument_list|<
name|F
argument_list|,
name|E
argument_list|>
block|{
DECL|field|mapper
specifier|private
specifier|final
name|Function
argument_list|<
name|E
argument_list|,
name|F
argument_list|>
name|mapper
decl_stmt|;
DECL|method|MappedList (ObservableList<? extends E> source, Function<E, F> mapper)
specifier|public
name|MappedList
parameter_list|(
name|ObservableList
argument_list|<
name|?
extends|extends
name|E
argument_list|>
name|source
parameter_list|,
name|Function
argument_list|<
name|E
argument_list|,
name|F
argument_list|>
name|mapper
parameter_list|)
block|{
name|super
argument_list|(
name|source
argument_list|)
expr_stmt|;
name|this
operator|.
name|mapper
operator|=
name|mapper
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getSourceIndex (int index)
specifier|public
name|int
name|getSourceIndex
parameter_list|(
name|int
name|index
parameter_list|)
block|{
return|return
name|index
return|;
block|}
annotation|@
name|Override
DECL|method|get (int index)
specifier|public
name|F
name|get
parameter_list|(
name|int
name|index
parameter_list|)
block|{
return|return
name|mapper
operator|.
name|apply
argument_list|(
name|getSource
argument_list|()
operator|.
name|get
argument_list|(
name|index
argument_list|)
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|size ()
specifier|public
name|int
name|size
parameter_list|()
block|{
return|return
name|getSource
argument_list|()
operator|.
name|size
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|sourceChanged (Change<? extends E> c)
specifier|protected
name|void
name|sourceChanged
parameter_list|(
name|Change
argument_list|<
name|?
extends|extends
name|E
argument_list|>
name|c
parameter_list|)
block|{
name|fireChange
argument_list|(
operator|new
name|Change
argument_list|<
name|F
argument_list|>
argument_list|(
name|this
argument_list|)
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|wasAdded
parameter_list|()
block|{
return|return
name|c
operator|.
name|wasAdded
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|wasRemoved
parameter_list|()
block|{
return|return
name|c
operator|.
name|wasRemoved
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|wasReplaced
parameter_list|()
block|{
return|return
name|c
operator|.
name|wasReplaced
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|wasUpdated
parameter_list|()
block|{
return|return
name|c
operator|.
name|wasUpdated
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|wasPermutated
parameter_list|()
block|{
return|return
name|c
operator|.
name|wasPermutated
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|getPermutation
parameter_list|(
name|int
name|i
parameter_list|)
block|{
return|return
name|c
operator|.
name|getPermutation
argument_list|(
name|i
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|protected
name|int
index|[]
name|getPermutation
parameter_list|()
block|{
comment|// This method is only called by the superclass methods
comment|// wasPermutated() and getPermutation(int), which are
comment|// both overriden by this class. There is no other way
comment|// this method can be called.
throw|throw
operator|new
name|AssertionError
argument_list|(
literal|"Unreachable code"
argument_list|)
throw|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|F
argument_list|>
name|getRemoved
parameter_list|()
block|{
name|ArrayList
argument_list|<
name|F
argument_list|>
name|res
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|c
operator|.
name|getRemovedSize
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|E
name|e
range|:
name|c
operator|.
name|getRemoved
argument_list|()
control|)
block|{
name|res
operator|.
name|add
argument_list|(
name|mapper
operator|.
name|apply
argument_list|(
name|e
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|res
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|getFrom
parameter_list|()
block|{
return|return
name|c
operator|.
name|getFrom
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|getTo
parameter_list|()
block|{
return|return
name|c
operator|.
name|getTo
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|next
parameter_list|()
block|{
return|return
name|c
operator|.
name|next
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|reset
parameter_list|()
block|{
name|c
operator|.
name|reset
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
