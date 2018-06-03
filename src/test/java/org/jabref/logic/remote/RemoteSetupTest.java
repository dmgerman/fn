begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.remote
package|package
name|org
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

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|Socket
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|charset
operator|.
name|StandardCharsets
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
name|remote
operator|.
name|client
operator|.
name|RemoteClient
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
name|remote
operator|.
name|server
operator|.
name|MessageHandler
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
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|OS
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|support
operator|.
name|DisabledOnCIServer
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|BeforeEach
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Test
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Assertions
operator|.
name|assertFalse
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Assertions
operator|.
name|assertTrue
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Assumptions
operator|.
name|assumeFalse
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|mockito
operator|.
name|ArgumentMatchers
operator|.
name|any
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|mockito
operator|.
name|Mockito
operator|.
name|mock
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|mockito
operator|.
name|Mockito
operator|.
name|never
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|mockito
operator|.
name|Mockito
operator|.
name|verify
import|;
end_import

begin_comment
comment|/**  * Tests where the remote client and server setup is wrong.  */
end_comment

begin_class
annotation|@
name|DisabledOnCIServer
argument_list|(
literal|"Tests fails sporadically on CI server"
argument_list|)
DECL|class|RemoteSetupTest
class|class
name|RemoteSetupTest
block|{
DECL|field|messageHandler
specifier|private
name|MessageHandler
name|messageHandler
decl_stmt|;
annotation|@
name|BeforeEach
DECL|method|setUp ()
name|void
name|setUp
parameter_list|()
block|{
name|messageHandler
operator|=
name|mock
argument_list|(
name|MessageHandler
operator|.
name|class
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGoodCase ()
name|void
name|testGoodCase
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
index|[]
name|message
init|=
operator|new
name|String
index|[]
block|{
literal|"MYMESSAGE"
block|}
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
name|messageHandler
argument_list|,
name|port
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|server
operator|.
name|isOpen
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
operator|new
name|RemoteClient
argument_list|(
name|port
argument_list|)
operator|.
name|sendCommandLineArguments
argument_list|(
name|message
argument_list|)
argument_list|)
expr_stmt|;
name|verify
argument_list|(
name|messageHandler
argument_list|)
operator|.
name|handleCommandLineArguments
argument_list|(
name|message
argument_list|)
expr_stmt|;
name|server
operator|.
name|stop
argument_list|()
expr_stmt|;
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
name|void
name|testGoodCaseWithAllLifecycleMethods
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
index|[]
name|message
init|=
operator|new
name|String
index|[]
block|{
literal|"MYMESSAGE"
block|}
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
name|assertFalse
argument_list|(
name|server
operator|.
name|isOpen
argument_list|()
argument_list|)
expr_stmt|;
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
name|assertFalse
argument_list|(
name|server
operator|.
name|isOpen
argument_list|()
argument_list|)
expr_stmt|;
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
name|messageHandler
argument_list|,
name|port
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|server
operator|.
name|isOpen
argument_list|()
argument_list|)
expr_stmt|;
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
name|assertTrue
argument_list|(
name|server
operator|.
name|isOpen
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|server
operator|.
name|isNotStartedBefore
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
operator|new
name|RemoteClient
argument_list|(
name|port
argument_list|)
operator|.
name|sendCommandLineArguments
argument_list|(
name|message
argument_list|)
argument_list|)
expr_stmt|;
name|verify
argument_list|(
name|messageHandler
argument_list|)
operator|.
name|handleCommandLineArguments
argument_list|(
name|message
argument_list|)
expr_stmt|;
name|server
operator|.
name|stop
argument_list|()
expr_stmt|;
name|assertFalse
argument_list|(
name|server
operator|.
name|isOpen
argument_list|()
argument_list|)
expr_stmt|;
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
name|void
name|testPortAlreadyInUse
parameter_list|()
throws|throws
name|IOException
block|{
name|assumeFalse
argument_list|(
name|OS
operator|.
name|OS_X
argument_list|)
expr_stmt|;
specifier|final
name|int
name|port
init|=
literal|34567
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
name|messageHandler
argument_list|,
name|port
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|server
operator|.
name|isOpen
argument_list|()
argument_list|)
expr_stmt|;
name|verify
argument_list|(
name|messageHandler
argument_list|,
name|never
argument_list|()
argument_list|)
operator|.
name|handleCommandLineArguments
argument_list|(
name|any
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Test
DECL|method|testClientTimeout ()
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
name|assertFalse
argument_list|(
operator|new
name|RemoteClient
argument_list|(
name|port
argument_list|)
operator|.
name|sendCommandLineArguments
argument_list|(
operator|new
name|String
index|[]
block|{
name|message
block|}
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|pingReturnsFalseForWrongServerListening ()
name|void
name|pingReturnsFalseForWrongServerListening
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
comment|// Setup dummy server always answering "whatever"
operator|new
name|Thread
argument_list|(
parameter_list|()
lambda|->
block|{
try|try
init|(
name|Socket
name|message
init|=
name|socket
operator|.
name|accept
argument_list|()
init|;
name|OutputStream
name|os
operator|=
name|message
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
argument_list|(
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
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
argument_list|)
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
name|assertFalse
argument_list|(
operator|new
name|RemoteClient
argument_list|(
name|port
argument_list|)
operator|.
name|ping
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|pingReturnsFalseForNoServerListening ()
name|void
name|pingReturnsFalseForNoServerListening
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
name|assertFalse
argument_list|(
operator|new
name|RemoteClient
argument_list|(
name|port
argument_list|)
operator|.
name|ping
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|pingReturnsTrueWhenServerIsRunning ()
name|void
name|pingReturnsTrueWhenServerIsRunning
parameter_list|()
block|{
specifier|final
name|int
name|port
init|=
literal|34567
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
name|server
operator|.
name|openAndStart
argument_list|(
name|messageHandler
argument_list|,
name|port
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
operator|new
name|RemoteClient
argument_list|(
name|port
argument_list|)
operator|.
name|ping
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

