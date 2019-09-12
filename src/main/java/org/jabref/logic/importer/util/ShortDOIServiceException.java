begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.importer.util
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|util
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
DECL|class|ShortDOIServiceException
specifier|public
class|class
name|ShortDOIServiceException
extends|extends
name|JabRefException
block|{
DECL|method|ShortDOIServiceException (String message)
specifier|public
name|ShortDOIServiceException
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
DECL|method|ShortDOIServiceException (String message, Throwable cause)
specifier|public
name|ShortDOIServiceException
parameter_list|(
name|String
name|message
parameter_list|,
name|Throwable
name|cause
parameter_list|)
block|{
name|super
argument_list|(
name|message
argument_list|,
name|cause
argument_list|)
expr_stmt|;
block|}
DECL|method|ShortDOIServiceException (String message, String localizedMessage)
specifier|public
name|ShortDOIServiceException
parameter_list|(
name|String
name|message
parameter_list|,
name|String
name|localizedMessage
parameter_list|)
block|{
name|super
argument_list|(
name|message
argument_list|,
name|localizedMessage
argument_list|)
expr_stmt|;
block|}
DECL|method|ShortDOIServiceException (String message, String localizedMessage, Throwable cause)
specifier|public
name|ShortDOIServiceException
parameter_list|(
name|String
name|message
parameter_list|,
name|String
name|localizedMessage
parameter_list|,
name|Throwable
name|cause
parameter_list|)
block|{
name|super
argument_list|(
name|message
argument_list|,
name|localizedMessage
argument_list|,
name|cause
argument_list|)
expr_stmt|;
block|}
DECL|method|ShortDOIServiceException (Throwable cause)
specifier|public
name|ShortDOIServiceException
parameter_list|(
name|Throwable
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
