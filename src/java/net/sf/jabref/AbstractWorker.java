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
name|spin
operator|.
name|Spin
import|;
end_import

begin_comment
comment|/**  * Convenience class for creating an object used for performing a time-  * consuming action off the Swing thread, and optionally performing GUI  * work afterwards. This class is supported by runCommand() in BasePanel,  * which, if the action called is an AbstractWorker, will run its run()  * method through the Worker interface, and then its update() method through  * the CallBack interface. This procedure ensures that run() cannot freeze  * the GUI, and that update() can safely update GUI components.  */
end_comment

begin_class
DECL|class|AbstractWorker
specifier|public
specifier|abstract
class|class
name|AbstractWorker
implements|implements
name|Worker
implements|,
name|CallBack
block|{
DECL|field|worker
specifier|private
name|Worker
name|worker
decl_stmt|;
DECL|field|callBack
specifier|private
name|CallBack
name|callBack
decl_stmt|;
DECL|method|AbstractWorker ()
specifier|public
name|AbstractWorker
parameter_list|()
block|{
name|worker
operator|=
operator|(
name|Worker
operator|)
name|Spin
operator|.
name|off
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|callBack
operator|=
operator|(
name|CallBack
operator|)
name|Spin
operator|.
name|over
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
DECL|method|init ()
specifier|public
name|void
name|init
parameter_list|()
throws|throws
name|Throwable
block|{      }
comment|/**      * This method returns a wrapped Worker instance of this AbstractWorker.      * whose methods will automatically be run off the EDT (Swing) thread.      */
DECL|method|getWorker ()
specifier|public
name|Worker
name|getWorker
parameter_list|()
block|{
return|return
name|worker
return|;
block|}
comment|/**      * This method returns a wrapped CallBack instance of this AbstractWorker      * whose methods will automatically be run on the EDT (Swing) thread.      */
DECL|method|getCallBack ()
specifier|public
name|CallBack
name|getCallBack
parameter_list|()
block|{
return|return
name|callBack
return|;
block|}
comment|/**      * Empty implementation of the update() method. Override this method      * if a callback is needed.      */
DECL|method|update ()
specifier|public
name|void
name|update
parameter_list|()
block|{     }
block|}
end_class

end_unit

