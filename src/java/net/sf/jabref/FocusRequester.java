begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
package|;
end_package

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|SwingUtilities
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Component
import|;
end_import

begin_class
DECL|class|FocusRequester
specifier|public
class|class
name|FocusRequester
implements|implements
name|Runnable
block|{
DECL|field|comp
specifier|private
name|Component
name|comp
decl_stmt|;
DECL|method|FocusRequester (Component comp)
specifier|public
name|FocusRequester
parameter_list|(
name|Component
name|comp
parameter_list|)
block|{
if|if
condition|(
name|comp
operator|==
literal|null
condition|)
name|Thread
operator|.
name|dumpStack
argument_list|()
expr_stmt|;
comment|//System.out.println("FocusRequester: "+comp.toString());
name|this
operator|.
name|comp
operator|=
name|comp
expr_stmt|;
try|try
block|{
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|run ()
specifier|public
name|void
name|run
parameter_list|()
block|{
name|comp
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

