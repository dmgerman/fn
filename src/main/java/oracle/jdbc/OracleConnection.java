begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|oracle.jdbc
package|package
name|oracle
operator|.
name|jdbc
package|;
end_package

begin_import
import|import
name|java
operator|.
name|sql
operator|.
name|Array
import|;
end_import

begin_import
import|import
name|java
operator|.
name|sql
operator|.
name|Blob
import|;
end_import

begin_import
import|import
name|java
operator|.
name|sql
operator|.
name|CallableStatement
import|;
end_import

begin_import
import|import
name|java
operator|.
name|sql
operator|.
name|Clob
import|;
end_import

begin_import
import|import
name|java
operator|.
name|sql
operator|.
name|Connection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|sql
operator|.
name|DatabaseMetaData
import|;
end_import

begin_import
import|import
name|java
operator|.
name|sql
operator|.
name|NClob
import|;
end_import

begin_import
import|import
name|java
operator|.
name|sql
operator|.
name|PreparedStatement
import|;
end_import

begin_import
import|import
name|java
operator|.
name|sql
operator|.
name|SQLClientInfoException
import|;
end_import

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
name|sql
operator|.
name|SQLWarning
import|;
end_import

begin_import
import|import
name|java
operator|.
name|sql
operator|.
name|SQLXML
import|;
end_import

begin_import
import|import
name|java
operator|.
name|sql
operator|.
name|Savepoint
import|;
end_import

begin_import
import|import
name|java
operator|.
name|sql
operator|.
name|Statement
import|;
end_import

begin_import
import|import
name|java
operator|.
name|sql
operator|.
name|Struct
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Properties
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|Executor
import|;
end_import

begin_import
import|import
name|oracle
operator|.
name|jdbc
operator|.
name|dcn
operator|.
name|DatabaseChangeRegistration
import|;
end_import

begin_comment
comment|/**  * A mocking class used as a placeholder for the real Oracle JDBC drivers to prevent build errors.  */
end_comment

begin_class
DECL|class|OracleConnection
specifier|public
class|class
name|OracleConnection
implements|implements
name|Connection
block|{
DECL|field|DCN_NOTIFY_ROWIDS
specifier|public
specifier|static
name|String
name|DCN_NOTIFY_ROWIDS
decl_stmt|;
DECL|field|DCN_QUERY_CHANGE_NOTIFICATION
specifier|public
specifier|static
name|String
name|DCN_QUERY_CHANGE_NOTIFICATION
decl_stmt|;
annotation|@
name|Override
DECL|method|unwrap (Class<T> iface)
specifier|public
parameter_list|<
name|T
parameter_list|>
name|T
name|unwrap
parameter_list|(
name|Class
argument_list|<
name|T
argument_list|>
name|iface
parameter_list|)
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|isWrapperFor (Class<?> iface)
specifier|public
name|boolean
name|isWrapperFor
parameter_list|(
name|Class
argument_list|<
name|?
argument_list|>
name|iface
parameter_list|)
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
return|return
literal|false
return|;
block|}
annotation|@
name|Override
DECL|method|createStatement ()
specifier|public
name|Statement
name|createStatement
parameter_list|()
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|prepareStatement (String sql)
specifier|public
name|PreparedStatement
name|prepareStatement
parameter_list|(
name|String
name|sql
parameter_list|)
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|prepareCall (String sql)
specifier|public
name|CallableStatement
name|prepareCall
parameter_list|(
name|String
name|sql
parameter_list|)
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|nativeSQL (String sql)
specifier|public
name|String
name|nativeSQL
parameter_list|(
name|String
name|sql
parameter_list|)
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|setAutoCommit (boolean autoCommit)
specifier|public
name|void
name|setAutoCommit
parameter_list|(
name|boolean
name|autoCommit
parameter_list|)
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
block|}
annotation|@
name|Override
DECL|method|getAutoCommit ()
specifier|public
name|boolean
name|getAutoCommit
parameter_list|()
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
return|return
literal|false
return|;
block|}
annotation|@
name|Override
DECL|method|commit ()
specifier|public
name|void
name|commit
parameter_list|()
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
block|}
annotation|@
name|Override
DECL|method|rollback ()
specifier|public
name|void
name|rollback
parameter_list|()
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
block|}
annotation|@
name|Override
DECL|method|close ()
specifier|public
name|void
name|close
parameter_list|()
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
block|}
annotation|@
name|Override
DECL|method|isClosed ()
specifier|public
name|boolean
name|isClosed
parameter_list|()
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
return|return
literal|false
return|;
block|}
annotation|@
name|Override
DECL|method|getMetaData ()
specifier|public
name|DatabaseMetaData
name|getMetaData
parameter_list|()
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|setReadOnly (boolean readOnly)
specifier|public
name|void
name|setReadOnly
parameter_list|(
name|boolean
name|readOnly
parameter_list|)
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
block|}
annotation|@
name|Override
DECL|method|isReadOnly ()
specifier|public
name|boolean
name|isReadOnly
parameter_list|()
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
return|return
literal|false
return|;
block|}
annotation|@
name|Override
DECL|method|setCatalog (String catalog)
specifier|public
name|void
name|setCatalog
parameter_list|(
name|String
name|catalog
parameter_list|)
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
block|}
annotation|@
name|Override
DECL|method|getCatalog ()
specifier|public
name|String
name|getCatalog
parameter_list|()
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|setTransactionIsolation (int level)
specifier|public
name|void
name|setTransactionIsolation
parameter_list|(
name|int
name|level
parameter_list|)
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
block|}
annotation|@
name|Override
DECL|method|getTransactionIsolation ()
specifier|public
name|int
name|getTransactionIsolation
parameter_list|()
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
return|return
literal|0
return|;
block|}
annotation|@
name|Override
DECL|method|getWarnings ()
specifier|public
name|SQLWarning
name|getWarnings
parameter_list|()
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|clearWarnings ()
specifier|public
name|void
name|clearWarnings
parameter_list|()
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
block|}
annotation|@
name|Override
DECL|method|createStatement (int resultSetType, int resultSetConcurrency)
specifier|public
name|Statement
name|createStatement
parameter_list|(
name|int
name|resultSetType
parameter_list|,
name|int
name|resultSetConcurrency
parameter_list|)
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|prepareStatement (String sql, int resultSetType, int resultSetConcurrency)
specifier|public
name|PreparedStatement
name|prepareStatement
parameter_list|(
name|String
name|sql
parameter_list|,
name|int
name|resultSetType
parameter_list|,
name|int
name|resultSetConcurrency
parameter_list|)
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|prepareCall (String sql, int resultSetType, int resultSetConcurrency)
specifier|public
name|CallableStatement
name|prepareCall
parameter_list|(
name|String
name|sql
parameter_list|,
name|int
name|resultSetType
parameter_list|,
name|int
name|resultSetConcurrency
parameter_list|)
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|getTypeMap ()
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|Class
argument_list|<
name|?
argument_list|>
argument_list|>
name|getTypeMap
parameter_list|()
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|setTypeMap (Map<String, Class<?>> map)
specifier|public
name|void
name|setTypeMap
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|Class
argument_list|<
name|?
argument_list|>
argument_list|>
name|map
parameter_list|)
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
block|}
annotation|@
name|Override
DECL|method|setHoldability (int holdability)
specifier|public
name|void
name|setHoldability
parameter_list|(
name|int
name|holdability
parameter_list|)
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
block|}
annotation|@
name|Override
DECL|method|getHoldability ()
specifier|public
name|int
name|getHoldability
parameter_list|()
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
return|return
literal|0
return|;
block|}
annotation|@
name|Override
DECL|method|setSavepoint ()
specifier|public
name|Savepoint
name|setSavepoint
parameter_list|()
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|setSavepoint (String name)
specifier|public
name|Savepoint
name|setSavepoint
parameter_list|(
name|String
name|name
parameter_list|)
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|rollback (Savepoint savepoint)
specifier|public
name|void
name|rollback
parameter_list|(
name|Savepoint
name|savepoint
parameter_list|)
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
block|}
annotation|@
name|Override
DECL|method|releaseSavepoint (Savepoint savepoint)
specifier|public
name|void
name|releaseSavepoint
parameter_list|(
name|Savepoint
name|savepoint
parameter_list|)
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
block|}
annotation|@
name|Override
DECL|method|createStatement (int resultSetType, int resultSetConcurrency, int resultSetHoldability)
specifier|public
name|Statement
name|createStatement
parameter_list|(
name|int
name|resultSetType
parameter_list|,
name|int
name|resultSetConcurrency
parameter_list|,
name|int
name|resultSetHoldability
parameter_list|)
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|prepareStatement (String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability)
specifier|public
name|PreparedStatement
name|prepareStatement
parameter_list|(
name|String
name|sql
parameter_list|,
name|int
name|resultSetType
parameter_list|,
name|int
name|resultSetConcurrency
parameter_list|,
name|int
name|resultSetHoldability
parameter_list|)
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|prepareCall (String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability)
specifier|public
name|CallableStatement
name|prepareCall
parameter_list|(
name|String
name|sql
parameter_list|,
name|int
name|resultSetType
parameter_list|,
name|int
name|resultSetConcurrency
parameter_list|,
name|int
name|resultSetHoldability
parameter_list|)
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|prepareStatement (String sql, int autoGeneratedKeys)
specifier|public
name|PreparedStatement
name|prepareStatement
parameter_list|(
name|String
name|sql
parameter_list|,
name|int
name|autoGeneratedKeys
parameter_list|)
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|prepareStatement (String sql, int[] columnIndexes)
specifier|public
name|PreparedStatement
name|prepareStatement
parameter_list|(
name|String
name|sql
parameter_list|,
name|int
index|[]
name|columnIndexes
parameter_list|)
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|prepareStatement (String sql, String[] columnNames)
specifier|public
name|PreparedStatement
name|prepareStatement
parameter_list|(
name|String
name|sql
parameter_list|,
name|String
index|[]
name|columnNames
parameter_list|)
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|createClob ()
specifier|public
name|Clob
name|createClob
parameter_list|()
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|createBlob ()
specifier|public
name|Blob
name|createBlob
parameter_list|()
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|createNClob ()
specifier|public
name|NClob
name|createNClob
parameter_list|()
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|createSQLXML ()
specifier|public
name|SQLXML
name|createSQLXML
parameter_list|()
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|isValid (int timeout)
specifier|public
name|boolean
name|isValid
parameter_list|(
name|int
name|timeout
parameter_list|)
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
return|return
literal|false
return|;
block|}
annotation|@
name|Override
DECL|method|setClientInfo (String name, String value)
specifier|public
name|void
name|setClientInfo
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|value
parameter_list|)
throws|throws
name|SQLClientInfoException
block|{
comment|//  Auto-generated method stub
block|}
annotation|@
name|Override
DECL|method|setClientInfo (Properties properties)
specifier|public
name|void
name|setClientInfo
parameter_list|(
name|Properties
name|properties
parameter_list|)
throws|throws
name|SQLClientInfoException
block|{
comment|//  Auto-generated method stub
block|}
annotation|@
name|Override
DECL|method|getClientInfo (String name)
specifier|public
name|String
name|getClientInfo
parameter_list|(
name|String
name|name
parameter_list|)
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|getClientInfo ()
specifier|public
name|Properties
name|getClientInfo
parameter_list|()
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|createArrayOf (String typeName, Object[] elements)
specifier|public
name|Array
name|createArrayOf
parameter_list|(
name|String
name|typeName
parameter_list|,
name|Object
index|[]
name|elements
parameter_list|)
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|createStruct (String typeName, Object[] attributes)
specifier|public
name|Struct
name|createStruct
parameter_list|(
name|String
name|typeName
parameter_list|,
name|Object
index|[]
name|attributes
parameter_list|)
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|setSchema (String schema)
specifier|public
name|void
name|setSchema
parameter_list|(
name|String
name|schema
parameter_list|)
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
block|}
annotation|@
name|Override
DECL|method|getSchema ()
specifier|public
name|String
name|getSchema
parameter_list|()
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|abort (Executor executor)
specifier|public
name|void
name|abort
parameter_list|(
name|Executor
name|executor
parameter_list|)
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
block|}
annotation|@
name|Override
DECL|method|setNetworkTimeout (Executor executor, int milliseconds)
specifier|public
name|void
name|setNetworkTimeout
parameter_list|(
name|Executor
name|executor
parameter_list|,
name|int
name|milliseconds
parameter_list|)
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
block|}
annotation|@
name|Override
DECL|method|getNetworkTimeout ()
specifier|public
name|int
name|getNetworkTimeout
parameter_list|()
throws|throws
name|SQLException
block|{
comment|//  Auto-generated method stub
return|return
literal|0
return|;
block|}
DECL|method|registerDatabaseChangeNotification (@uppressWarningsR) Properties properties)
specifier|public
name|DatabaseChangeRegistration
name|registerDatabaseChangeNotification
parameter_list|(
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unused"
argument_list|)
name|Properties
name|properties
parameter_list|)
block|{
return|return
operator|new
name|DatabaseChangeRegistration
argument_list|()
return|;
block|}
DECL|method|unregisterDatabaseChangeNotification (@uppressWarningsR) DatabaseChangeRegistration databaseChangeRegistration)
specifier|public
name|void
name|unregisterDatabaseChangeNotification
parameter_list|(
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unused"
argument_list|)
name|DatabaseChangeRegistration
name|databaseChangeRegistration
parameter_list|)
block|{
comment|// do nothing
block|}
block|}
end_class

end_unit

