begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.journals
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|journals
package|;
end_package

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|JabRefException
import|;
end_import

begin_comment
comment|/**  * This exception will be thrown if any of the mandatory fields for an {@link Abbreviation}  * object are empty. The mandatory fields are {@code name} and {@code abbreviation}.  *  */
end_comment

begin_class
DECL|class|EmptyFieldException
specifier|public
class|class
name|EmptyFieldException
extends|extends
name|JabRefException
block|{
DECL|method|EmptyFieldException (String message)
specifier|public
name|EmptyFieldException
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|super
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

