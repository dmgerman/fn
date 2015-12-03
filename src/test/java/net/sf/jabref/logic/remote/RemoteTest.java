begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.remote
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|remote
package|;
end_package

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Assert
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|remote
operator|.
name|client
operator|.
name|RemoteListenerClient
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|remote
operator|.
name|server
operator|.
name|RemoteListenerServerLifecycle
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|OutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|ServerSocket
import|;
end_import

begin_class
DECL|class|RemoteTest
specifier|public
class|class
name|RemoteTest
block|{
annotation|@
name|Test
DECL|method|testGoodCase ()
specifier|public
name|void
name|testGoodCase
parameter_list|()
throws|throws
name|Exception
block|{
specifier|final
name|int
name|port
init|=
literal|34567
decl_stmt|;
specifier|final
name|String
name|message
init|=
literal|"MYMESSAGE"
decl_stmt|;
try|try
init|(
name|RemoteListenerServerLifecycle
name|server
init|=
operator|new
name|RemoteListenerServerLifecycle
argument_list|()
init|)
block|{
name|Assert
operator|.
name|assertFalse
argument_list|(
name|server
operator|.
name|isOpen
argument_list|()
argument_list|)
expr_stmt|;
name|server
operator|.
name|openAndStart
argument_list|(
name|msg
lambda|->
name|Assert
operator|.
name|assertEquals
argument_list|(
name|message
argument_list|,
name|msg
argument_list|)
argument_list|,
name|port
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|server
operator|.
name|isOpen
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|RemoteListenerClient
operator|.
name|sendToActiveJabRefInstance
argument_list|(
operator|new
name|String
index|[]
block|{
name|message
block|}
argument_list|,
name|port
argument_list|)
argument_list|)
expr_stmt|;
name|server
operator|.
name|stop
argument_list|()
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|server
operator|.
name|isOpen
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|testGoodCaseWithAllLifecycleMethods ()
specifier|public
name|void
name|testGoodCaseWithAllLifecycleMethods
parameter_list|()
throws|throws
name|Exception
block|{
specifier|final
name|int
name|port
init|=
literal|34567
decl_stmt|;
specifier|final
name|String
name|message
init|=
literal|"MYMESSAGE"
decl_stmt|;
try|try
init|(
name|RemoteListenerServerLifecycle
name|server
init|=
operator|new
name|RemoteListenerServerLifecycle
argument_list|()
init|)
block|{
name|Assert
operator|.
name|assertFalse
argument_list|(
name|server
operator|.
name|isOpen
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|server
operator|.
name|isNotStartedBefore
argument_list|()
argument_list|)
expr_stmt|;
name|server
operator|.
name|stop
argument_list|()
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|server
operator|.
name|isOpen
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|server
operator|.
name|isNotStartedBefore
argument_list|()
argument_list|)
expr_stmt|;
name|server
operator|.
name|open
argument_list|(
name|msg
lambda|->
name|Assert
operator|.
name|assertEquals
argument_list|(
name|message
argument_list|,
name|msg
argument_list|)
argument_list|,
name|port
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|server
operator|.
name|isOpen
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|server
operator|.
name|isNotStartedBefore
argument_list|()
argument_list|)
expr_stmt|;
name|server
operator|.
name|start
argument_list|()
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|server
operator|.
name|isOpen
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|server
operator|.
name|isNotStartedBefore
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|RemoteListenerClient
operator|.
name|sendToActiveJabRefInstance
argument_list|(
operator|new
name|String
index|[]
block|{
name|message
block|}
argument_list|,
name|port
argument_list|)
argument_list|)
expr_stmt|;
name|server
operator|.
name|stop
argument_list|()
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|server
operator|.
name|isOpen
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|server
operator|.
name|isNotStartedBefore
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|testPortAlreadyInUse ()
specifier|public
name|void
name|testPortAlreadyInUse
parameter_list|()
throws|throws
name|IOException
block|{
specifier|final
name|int
name|port
init|=
literal|34567
decl_stmt|;
specifier|final
name|String
name|message
init|=
literal|"MYMESSAGE"
decl_stmt|;
try|try
init|(
name|ServerSocket
name|socket
init|=
operator|new
name|ServerSocket
argument_list|(
name|port
argument_list|)
init|)
block|{
name|Assert
operator|.
name|assertTrue
argument_list|(
name|socket
operator|.
name|isBound
argument_list|()
argument_list|)
expr_stmt|;
try|try
init|(
name|RemoteListenerServerLifecycle
name|server
init|=
operator|new
name|RemoteListenerServerLifecycle
argument_list|()
init|)
block|{
name|Assert
operator|.
name|assertFalse
argument_list|(
name|server
operator|.
name|isOpen
argument_list|()
argument_list|)
expr_stmt|;
name|server
operator|.
name|openAndStart
argument_list|(
name|msg
lambda|->
name|Assert
operator|.
name|fail
argument_list|(
literal|"should not happen"
argument_list|)
argument_list|,
name|port
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|server
operator|.
name|isOpen
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
comment|// TODO Auto-generated catch block
name|Assert
operator|.
name|fail
argument_list|(
literal|"Exception: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Test
DECL|method|testClientTimeout ()
specifier|public
name|void
name|testClientTimeout
parameter_list|()
block|{
specifier|final
name|int
name|port
init|=
literal|34567
decl_stmt|;
specifier|final
name|String
name|message
init|=
literal|"MYMESSAGE"
decl_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|RemoteListenerClient
operator|.
name|sendToActiveJabRefInstance
argument_list|(
operator|new
name|String
index|[]
block|{
name|message
block|}
argument_list|,
name|port
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testClientConnectingToWrongServer ()
specifier|public
name|void
name|testClientConnectingToWrongServer
parameter_list|()
throws|throws
name|IOException
throws|,
name|InterruptedException
block|{
specifier|final
name|int
name|port
init|=
literal|34567
decl_stmt|;
specifier|final
name|String
name|message
init|=
literal|"MYMESSAGE"
decl_stmt|;
try|try
init|(
name|ServerSocket
name|socket
init|=
operator|new
name|ServerSocket
argument_list|(
name|port
argument_list|)
init|)
block|{
operator|new
name|Thread
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
try|try
init|(
name|OutputStream
name|os
init|=
name|socket
operator|.
name|accept
argument_list|()
operator|.
name|getOutputStream
argument_list|()
init|)
block|{
name|os
operator|.
name|write
argument_list|(
literal|"whatever"
operator|.
name|getBytes
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
comment|// Ignored
block|}
block|}
block|}
operator|.
name|start
argument_list|()
expr_stmt|;
name|Thread
operator|.
name|sleep
argument_list|(
literal|100
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|RemoteListenerClient
operator|.
name|sendToActiveJabRefInstance
argument_list|(
operator|new
name|String
index|[]
block|{
name|message
block|}
argument_list|,
name|port
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

