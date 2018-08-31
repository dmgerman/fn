begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.shared
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
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
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|shared
operator|.
name|exception
operator|.
name|InvalidDBMSConnectionPropertiesException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
operator|.
name|shared
operator|.
name|DBMSType
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|testutils
operator|.
name|category
operator|.
name|DatabaseTest
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
import|import
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|params
operator|.
name|ParameterizedTest
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
name|params
operator|.
name|provider
operator|.
name|EnumSource
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
name|assertNotNull
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
name|assertThrows
import|;
end_import

begin_class
annotation|@
name|DatabaseTest
DECL|class|DBMSConnectionTest
specifier|public
class|class
name|DBMSConnectionTest
block|{
annotation|@
name|ParameterizedTest
annotation|@
name|EnumSource
argument_list|(
name|DBMSType
operator|.
name|class
argument_list|)
DECL|method|testGetConnection (DBMSType dbmsType)
specifier|public
name|void
name|testGetConnection
parameter_list|(
name|DBMSType
name|dbmsType
parameter_list|)
throws|throws
name|SQLException
throws|,
name|InvalidDBMSConnectionPropertiesException
block|{
name|DBMSConnectionProperties
name|properties
init|=
name|TestConnector
operator|.
name|getTestConnectionProperties
argument_list|(
name|dbmsType
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
operator|new
name|DBMSConnection
argument_list|(
name|properties
argument_list|)
operator|.
name|getConnection
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetConnectionFail (DBMSType dbmsType)
specifier|public
name|void
name|testGetConnectionFail
parameter_list|(
name|DBMSType
name|dbmsType
parameter_list|)
throws|throws
name|SQLException
throws|,
name|InvalidDBMSConnectionPropertiesException
block|{
name|assertThrows
argument_list|(
name|SQLException
operator|.
name|class
argument_list|,
parameter_list|()
lambda|->
operator|new
name|DBMSConnection
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
argument_list|,
literal|false
argument_list|)
argument_list|)
operator|.
name|getConnection
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

