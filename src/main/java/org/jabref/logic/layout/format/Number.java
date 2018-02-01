begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.layout.format
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|layout
operator|.
name|format
package|;
end_package

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|exporter
operator|.
name|ExporterFactory
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|layout
operator|.
name|ParamLayoutFormatter
import|;
end_import

begin_comment
comment|/**  * Formatter that outputs a sequence number for the current entry. The sequence number is  * tied to the entry's position in the order, not to the number of calls to this formatter.  */
end_comment

begin_class
DECL|class|Number
specifier|public
class|class
name|Number
implements|implements
name|ParamLayoutFormatter
block|{
annotation|@
name|Override
DECL|method|setArgument (String arg)
specifier|public
name|void
name|setArgument
parameter_list|(
name|String
name|arg
parameter_list|)
block|{
comment|// No effect currently.
block|}
annotation|@
name|Override
DECL|method|format (String fieldText)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|fieldText
parameter_list|)
block|{
return|return
name|String
operator|.
name|valueOf
argument_list|(
name|ExporterFactory
operator|.
name|entryNumber
argument_list|)
return|;
block|}
block|}
end_class

end_unit

