begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.formatter
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|formatter
package|;
end_package

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|cleanup
operator|.
name|Formatter
import|;
end_import

begin_comment
comment|/**  * All formatters should implement hashcode and equals in the way the interface {@link Formatter} provides. This superclass ensures this.  */
end_comment

begin_class
DECL|class|AbstractFormatter
specifier|public
specifier|abstract
class|class
name|AbstractFormatter
implements|implements
name|Formatter
block|{
annotation|@
name|Override
DECL|method|hashCode ()
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|defaultHashCode
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|equals (Object obj)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
return|return
name|defaultEquals
argument_list|(
name|obj
argument_list|)
return|;
block|}
block|}
end_class

end_unit

