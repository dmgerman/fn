begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.util.logging
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|logging
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|logging
operator|.
name|Handler
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|logging
operator|.
name|LogRecord
import|;
end_import

begin_comment
comment|/**  * With Java 7, one could directly set a format for the SimpleFormatter  * (http://stackoverflow.com/a/10722260/873282) and use that in a StreamHandler.  * As JabRef is compatible with Java6, we have to write our own Handler  */
end_comment

begin_class
DECL|class|StdoutConsoleHandler
specifier|public
class|class
name|StdoutConsoleHandler
extends|extends
name|Handler
block|{
annotation|@
name|Override
DECL|method|close ()
specifier|public
name|void
name|close
parameter_list|()
throws|throws
name|SecurityException
block|{     }
annotation|@
name|Override
DECL|method|flush ()
specifier|public
name|void
name|flush
parameter_list|()
block|{
name|System
operator|.
name|out
operator|.
name|flush
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|publish (LogRecord record)
specifier|public
name|void
name|publish
parameter_list|(
name|LogRecord
name|record
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|record
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|flush
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

