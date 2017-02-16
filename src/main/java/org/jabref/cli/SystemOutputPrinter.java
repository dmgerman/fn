begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.cli
package|package
name|org
operator|.
name|jabref
operator|.
name|cli
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
name|importer
operator|.
name|OutputPrinter
import|;
end_import

begin_class
DECL|class|SystemOutputPrinter
specifier|public
class|class
name|SystemOutputPrinter
implements|implements
name|OutputPrinter
block|{
annotation|@
name|Override
DECL|method|setStatus (String s)
specifier|public
name|void
name|setStatus
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|showMessage (String message, String title, int msgType)
specifier|public
name|void
name|showMessage
parameter_list|(
name|String
name|message
parameter_list|,
name|String
name|title
parameter_list|,
name|int
name|msgType
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|title
operator|+
literal|": "
operator|+
name|message
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|showMessage (String message)
specifier|public
name|void
name|showMessage
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

