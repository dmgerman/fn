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
name|junit
operator|.
name|framework
operator|.
name|Assert
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

begin_class
DECL|class|RemoteUtilTest
specifier|public
class|class
name|RemoteUtilTest
block|{
annotation|@
name|Test
DECL|method|rejectPortNumberBelowZero ()
specifier|public
name|void
name|rejectPortNumberBelowZero
parameter_list|()
block|{
name|Assert
operator|.
name|assertFalse
argument_list|(
literal|"Port number must be non negative."
argument_list|,
name|RemoteUtil
operator|.
name|isUserPort
argument_list|(
operator|-
literal|55
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|rejectReservedSystemPorts ()
specifier|public
name|void
name|rejectReservedSystemPorts
parameter_list|()
block|{
name|Assert
operator|.
name|assertFalse
argument_list|(
literal|"Port number must be outside reserved system range (0-1023)."
argument_list|,
name|RemoteUtil
operator|.
name|isUserPort
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
literal|"Port number must be outside reserved system range (0-1023)."
argument_list|,
name|RemoteUtil
operator|.
name|isUserPort
argument_list|(
literal|1023
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|rejectPortsAbove16Bits ()
specifier|public
name|void
name|rejectPortsAbove16Bits
parameter_list|()
block|{
comment|// 2 ^ 16 - 1 => 65535
name|Assert
operator|.
name|assertFalse
argument_list|(
literal|"Port number should be below 65535."
argument_list|,
name|RemoteUtil
operator|.
name|isUserPort
argument_list|(
literal|65536
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|acceptPortsAboveSystemPorts ()
specifier|public
name|void
name|acceptPortsAboveSystemPorts
parameter_list|()
block|{
comment|// ports 1024 -> 65535
name|Assert
operator|.
name|assertTrue
argument_list|(
literal|"Port number in between 1024 and 65535 should be valid."
argument_list|,
name|RemoteUtil
operator|.
name|isUserPort
argument_list|(
literal|1024
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
literal|"Port number in between 1024 and 65535 should be valid."
argument_list|,
name|RemoteUtil
operator|.
name|isUserPort
argument_list|(
literal|65535
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

