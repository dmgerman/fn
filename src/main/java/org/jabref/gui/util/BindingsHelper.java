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
name|Consumer
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
name|property
operator|.
name|Property
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
name|ChangeListener
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
DECL|method|BindingsHelper ()
specifier|private
name|BindingsHelper
parameter_list|()
block|{     }
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
comment|/**      * Binds propertA bidirectional to propertyB while using updateB to update propertyB when propertyA changed.      */
DECL|method|bindBidirectional (Property<A> propertyA, ObservableValue<A> propertyB, Consumer<A> updateB)
specifier|public
specifier|static
parameter_list|<
name|A
parameter_list|>
name|void
name|bindBidirectional
parameter_list|(
name|Property
argument_list|<
name|A
argument_list|>
name|propertyA
parameter_list|,
name|ObservableValue
argument_list|<
name|A
argument_list|>
name|propertyB
parameter_list|,
name|Consumer
argument_list|<
name|A
argument_list|>
name|updateB
parameter_list|)
block|{
specifier|final
name|BidirectionalBinding
argument_list|<
name|A
argument_list|>
name|binding
init|=
operator|new
name|BidirectionalBinding
argument_list|<>
argument_list|(
name|propertyA
argument_list|,
name|propertyB
argument_list|,
name|updateB
argument_list|)
decl_stmt|;
comment|// use updateB as initial source
name|propertyA
operator|.
name|setValue
argument_list|(
name|propertyB
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|propertyA
operator|.
name|addListener
argument_list|(
name|binding
argument_list|)
expr_stmt|;
name|propertyB
operator|.
name|addListener
argument_list|(
name|binding
argument_list|)
expr_stmt|;
block|}
DECL|class|BidirectionalBinding
specifier|private
specifier|static
class|class
name|BidirectionalBinding
parameter_list|<
name|A
parameter_list|>
implements|implements
name|ChangeListener
argument_list|<
name|A
argument_list|>
block|{
DECL|field|propertyA
specifier|private
specifier|final
name|Property
argument_list|<
name|A
argument_list|>
name|propertyA
decl_stmt|;
DECL|field|updateB
specifier|private
specifier|final
name|Consumer
argument_list|<
name|A
argument_list|>
name|updateB
decl_stmt|;
DECL|field|updating
specifier|private
name|boolean
name|updating
init|=
literal|false
decl_stmt|;
DECL|method|BidirectionalBinding (Property<A> propertyA, ObservableValue<A> propertyB, Consumer<A> updateB)
specifier|public
name|BidirectionalBinding
parameter_list|(
name|Property
argument_list|<
name|A
argument_list|>
name|propertyA
parameter_list|,
name|ObservableValue
argument_list|<
name|A
argument_list|>
name|propertyB
parameter_list|,
name|Consumer
argument_list|<
name|A
argument_list|>
name|updateB
parameter_list|)
block|{
name|this
operator|.
name|propertyA
operator|=
name|propertyA
expr_stmt|;
name|this
operator|.
name|updateB
operator|=
name|updateB
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|changed (ObservableValue<? extends A> observable, A oldValue, A newValue)
specifier|public
name|void
name|changed
parameter_list|(
name|ObservableValue
argument_list|<
name|?
extends|extends
name|A
argument_list|>
name|observable
parameter_list|,
name|A
name|oldValue
parameter_list|,
name|A
name|newValue
parameter_list|)
block|{
if|if
condition|(
operator|!
name|updating
condition|)
block|{
try|try
block|{
name|updating
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|observable
operator|==
name|propertyA
condition|)
block|{
name|updateB
operator|.
name|accept
argument_list|(
name|newValue
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|propertyA
operator|.
name|setValue
argument_list|(
name|newValue
argument_list|)
expr_stmt|;
block|}
block|}
finally|finally
block|{
name|updating
operator|=
literal|false
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
end_class

end_unit

