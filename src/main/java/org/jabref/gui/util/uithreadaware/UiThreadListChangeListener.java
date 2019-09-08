begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.util.uithreadaware
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|util
operator|.
name|uithreadaware
package|;
end_package

begin_import
import|import
name|javafx
operator|.
name|collections
operator|.
name|ListChangeListener
import|;
end_import

begin_class
DECL|class|UiThreadListChangeListener
class|class
name|UiThreadListChangeListener
parameter_list|<
name|E
parameter_list|>
implements|implements
name|ListChangeListener
argument_list|<
name|E
argument_list|>
block|{
DECL|field|delegate
specifier|private
specifier|final
name|ListChangeListener
argument_list|<
name|E
argument_list|>
name|delegate
decl_stmt|;
DECL|method|UiThreadListChangeListener (ListChangeListener<E> delegate)
specifier|public
name|UiThreadListChangeListener
parameter_list|(
name|ListChangeListener
argument_list|<
name|E
argument_list|>
name|delegate
parameter_list|)
block|{
name|this
operator|.
name|delegate
operator|=
name|delegate
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|onChanged (Change<? extends E> c)
specifier|public
name|void
name|onChanged
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
name|UiThreadHelper
operator|.
name|ensureUiThreadExecution
argument_list|(
parameter_list|()
lambda|->
name|delegate
operator|.
name|onChanged
argument_list|(
name|c
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|equals (Object o)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
return|return
name|delegate
operator|.
name|equals
argument_list|(
name|o
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|hashCode ()
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|delegate
operator|.
name|hashCode
argument_list|()
return|;
block|}
block|}
end_class

end_unit

