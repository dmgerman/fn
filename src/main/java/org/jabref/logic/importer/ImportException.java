begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.importer
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
package|;
end_package

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|JabRefException
import|;
end_import

begin_class
DECL|class|ImportException
specifier|public
class|class
name|ImportException
extends|extends
name|JabRefException
block|{
DECL|method|ImportException (String errorMessage, Exception cause)
specifier|public
name|ImportException
parameter_list|(
name|String
name|errorMessage
parameter_list|,
name|Exception
name|cause
parameter_list|)
block|{
name|super
argument_list|(
name|errorMessage
argument_list|,
name|cause
argument_list|)
expr_stmt|;
block|}
DECL|method|ImportException (String errorMessage)
specifier|public
name|ImportException
parameter_list|(
name|String
name|errorMessage
parameter_list|)
block|{
name|super
argument_list|(
name|errorMessage
argument_list|)
expr_stmt|;
block|}
DECL|method|ImportException (Exception cause)
specifier|public
name|ImportException
parameter_list|(
name|Exception
name|cause
parameter_list|)
block|{
name|super
argument_list|(
name|cause
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

