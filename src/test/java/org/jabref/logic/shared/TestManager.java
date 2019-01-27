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
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
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

begin_comment
comment|/**  * This class provides helping methods for database tests. Furthermore it determines database systems which are ready to  * be used for tests.  */
end_comment

begin_class
DECL|class|TestManager
specifier|public
class|class
name|TestManager
block|{
DECL|method|getDBMSTypeTestParameter ()
specifier|public
specifier|static
name|Collection
argument_list|<
name|DBMSType
argument_list|>
name|getDBMSTypeTestParameter
parameter_list|()
block|{
name|Set
argument_list|<
name|DBMSType
argument_list|>
name|dbmsTypes
init|=
operator|new
name|HashSet
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|DBMSType
name|dbmsType
range|:
name|DBMSType
operator|.
name|values
argument_list|()
control|)
block|{
try|try
block|{
name|TestConnector
operator|.
name|getTestDBMSConnection
argument_list|(
name|dbmsType
argument_list|)
expr_stmt|;
name|dbmsTypes
operator|.
name|add
argument_list|(
name|dbmsType
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SQLException
decl||
name|InvalidDBMSConnectionPropertiesException
name|e
parameter_list|)
block|{
comment|// skip parameter
block|}
block|}
return|return
name|dbmsTypes
return|;
block|}
DECL|method|clearTables (DBMSConnection dbmsConnection)
specifier|public
specifier|static
name|void
name|clearTables
parameter_list|(
name|DBMSConnection
name|dbmsConnection
parameter_list|)
throws|throws
name|SQLException
block|{
name|DBMSType
name|dbmsType
init|=
name|dbmsConnection
operator|.
name|getProperties
argument_list|()
operator|.
name|getType
argument_list|()
decl_stmt|;
if|if
condition|(
name|dbmsType
operator|==
name|DBMSType
operator|.
name|MYSQL
condition|)
block|{
name|dbmsConnection
operator|.
name|getConnection
argument_list|()
operator|.
name|createStatement
argument_list|()
operator|.
name|executeUpdate
argument_list|(
literal|"DROP TABLE IF EXISTS `FIELD`"
argument_list|)
expr_stmt|;
name|dbmsConnection
operator|.
name|getConnection
argument_list|()
operator|.
name|createStatement
argument_list|()
operator|.
name|executeUpdate
argument_list|(
literal|"DROP TABLE IF EXISTS `ENTRY`"
argument_list|)
expr_stmt|;
name|dbmsConnection
operator|.
name|getConnection
argument_list|()
operator|.
name|createStatement
argument_list|()
operator|.
name|executeUpdate
argument_list|(
literal|"DROP TABLE IF EXISTS `METADATA`"
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|dbmsType
operator|==
name|DBMSType
operator|.
name|POSTGRESQL
condition|)
block|{
name|dbmsConnection
operator|.
name|getConnection
argument_list|()
operator|.
name|createStatement
argument_list|()
operator|.
name|executeUpdate
argument_list|(
literal|"DROP TABLE IF EXISTS \"FIELD\""
argument_list|)
expr_stmt|;
name|dbmsConnection
operator|.
name|getConnection
argument_list|()
operator|.
name|createStatement
argument_list|()
operator|.
name|executeUpdate
argument_list|(
literal|"DROP TABLE IF EXISTS \"ENTRY\""
argument_list|)
expr_stmt|;
name|dbmsConnection
operator|.
name|getConnection
argument_list|()
operator|.
name|createStatement
argument_list|()
operator|.
name|executeUpdate
argument_list|(
literal|"DROP TABLE IF EXISTS \"METADATA\""
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|dbmsType
operator|==
name|DBMSType
operator|.
name|ORACLE
condition|)
block|{
name|dbmsConnection
operator|.
name|getConnection
argument_list|()
operator|.
name|createStatement
argument_list|()
operator|.
name|executeUpdate
argument_list|(
literal|"BEGIN\n"
operator|+
literal|"EXECUTE IMMEDIATE 'DROP TABLE \"FIELD\"';\n"
operator|+
literal|"EXECUTE IMMEDIATE 'DROP TABLE \"ENTRY\"';\n"
operator|+
literal|"EXECUTE IMMEDIATE 'DROP TABLE \"METADATA\"';\n"
operator|+
literal|"EXECUTE IMMEDIATE 'DROP SEQUENCE \"ENTRY_SEQ\"';\n"
operator|+
literal|"EXCEPTION\n"
operator|+
literal|"WHEN OTHERS THEN\n"
operator|+
literal|"IF SQLCODE != -942 THEN\n"
operator|+
literal|"RAISE;\n"
operator|+
literal|"END IF;\n"
operator|+
literal|"END;"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

