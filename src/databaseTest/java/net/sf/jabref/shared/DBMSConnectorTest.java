begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.shared
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|shared
package|;
end_package

begin_import
import|import
name|java
operator|.
name|sql
operator|.
name|SQLException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
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
name|org
operator|.
name|junit
operator|.
name|runner
operator|.
name|RunWith
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|runners
operator|.
name|Parameterized
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|runners
operator|.
name|Parameterized
operator|.
name|Parameter
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|runners
operator|.
name|Parameterized
operator|.
name|Parameters
import|;
end_import

begin_class
annotation|@
name|RunWith
argument_list|(
name|Parameterized
operator|.
name|class
argument_list|)
DECL|class|DBMSConnectorTest
specifier|public
class|class
name|DBMSConnectorTest
block|{
annotation|@
name|Parameter
DECL|field|dbmsType
specifier|public
name|DBMSType
name|dbmsType
decl_stmt|;
annotation|@
name|Parameters
argument_list|(
name|name
operator|=
literal|"Test with {0} database system"
argument_list|)
DECL|method|getTestingDatabaseSystems ()
specifier|public
specifier|static
name|Collection
argument_list|<
name|DBMSType
argument_list|>
name|getTestingDatabaseSystems
parameter_list|()
block|{
return|return
name|DBMSConnector
operator|.
name|getAvailableDBMSTypes
argument_list|()
return|;
block|}
annotation|@
name|Test
DECL|method|testGetNewConnection ()
specifier|public
name|void
name|testGetNewConnection
parameter_list|()
throws|throws
name|ClassNotFoundException
throws|,
name|SQLException
block|{
name|DBMSConnectionProperties
name|properties
init|=
name|TestConnector
operator|.
name|getConnectionProperties
argument_list|(
name|dbmsType
argument_list|)
decl_stmt|;
name|DBMSConnector
operator|.
name|getNewConnection
argument_list|(
name|properties
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|SQLException
operator|.
name|class
argument_list|)
DECL|method|testGetNewConnectionFail ()
specifier|public
name|void
name|testGetNewConnectionFail
parameter_list|()
throws|throws
name|SQLException
throws|,
name|ClassNotFoundException
block|{
name|DBMSConnector
operator|.
name|getNewConnection
argument_list|(
operator|new
name|DBMSConnectionProperties
argument_list|(
name|dbmsType
argument_list|,
literal|"XXXX"
argument_list|,
literal|0
argument_list|,
literal|"XXXX"
argument_list|,
literal|"XXXX"
argument_list|,
literal|"XXXX"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

