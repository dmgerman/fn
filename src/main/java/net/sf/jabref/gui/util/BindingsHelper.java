begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.util
package|package
name|net
operator|.
name|sf
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
name|Predicate
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
name|binding
operator|.
name|BooleanBinding
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
name|BooleanPropertyBase
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
name|ObservableList
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|css
operator|.
name|PseudoClass
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

begin_comment
comment|/**  * Helper methods for javafx binding.  * Some methods are taken from https://bugs.openjdk.java.net/browse/JDK-8134679  */
end_comment

begin_class
DECL|class|BindingsHelper
specifier|public
class|class
name|BindingsHelper
block|{
DECL|method|any (ObservableList<T> source, Predicate<T> predicate)
specifier|public
specifier|static
parameter_list|<
name|T
parameter_list|>
name|BooleanBinding
name|any
parameter_list|(
name|ObservableList
argument_list|<
name|T
argument_list|>
name|source
parameter_list|,
name|Predicate
argument_list|<
name|T
argument_list|>
name|predicate
parameter_list|)
block|{
return|return
name|Bindings
operator|.
name|createBooleanBinding
argument_list|(
parameter_list|()
lambda|->
name|source
operator|.
name|stream
argument_list|()
operator|.
name|anyMatch
argument_list|(
name|predicate
argument_list|)
argument_list|,
name|source
argument_list|)
return|;
block|}
DECL|method|all (ObservableList<T> source, Predicate<T> predicate)
specifier|public
specifier|static
parameter_list|<
name|T
parameter_list|>
name|BooleanBinding
name|all
parameter_list|(
name|ObservableList
argument_list|<
name|T
argument_list|>
name|source
parameter_list|,
name|Predicate
argument_list|<
name|T
argument_list|>
name|predicate
parameter_list|)
block|{
comment|// Stream.allMatch() (in contrast to Stream.anyMatch() returns 'true' for empty streams, so this has to be checked explicitly.
return|return
name|Bindings
operator|.
name|createBooleanBinding
argument_list|(
parameter_list|()
lambda|->
operator|!
name|source
operator|.
name|isEmpty
argument_list|()
operator|&&
name|source
operator|.
name|stream
argument_list|()
operator|.
name|allMatch
argument_list|(
name|predicate
argument_list|)
argument_list|,
name|source
argument_list|)
return|;
block|}
DECL|method|includePseudoClassWhen (Node node, PseudoClass pseudoClass, ObservableValue<? extends Boolean> condition)
specifier|public
specifier|static
name|void
name|includePseudoClassWhen
parameter_list|(
name|Node
name|node
parameter_list|,
name|PseudoClass
name|pseudoClass
parameter_list|,
name|ObservableValue
argument_list|<
name|?
extends|extends
name|Boolean
argument_list|>
name|condition
parameter_list|)
block|{
name|BooleanProperty
name|pseudoClassState
init|=
operator|new
name|BooleanPropertyBase
argument_list|(
literal|false
argument_list|)
block|{
annotation|@
name|Override
specifier|protected
name|void
name|invalidated
parameter_list|()
block|{
name|node
operator|.
name|pseudoClassStateChanged
argument_list|(
name|pseudoClass
argument_list|,
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|getBean
parameter_list|()
block|{
return|return
name|node
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|pseudoClass
operator|.
name|getPseudoClassName
argument_list|()
return|;
block|}
block|}
decl_stmt|;
name|pseudoClassState
operator|.
name|bind
argument_list|(
name|condition
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
