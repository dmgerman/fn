begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.bibtex
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|bibtex
package|;
end_package

begin_comment
comment|/**  * @deprecated implement as {@link org.jabref.logic.integrity.IntegrityCheck} instead.  */
end_comment

begin_class
annotation|@
name|Deprecated
DECL|class|InvalidFieldValueException
specifier|public
class|class
name|InvalidFieldValueException
extends|extends
name|Exception
block|{
DECL|method|InvalidFieldValueException (String message)
specifier|public
name|InvalidFieldValueException
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

