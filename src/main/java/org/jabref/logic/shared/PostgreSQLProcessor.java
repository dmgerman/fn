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
name|PreparedStatement
import|;
end_import

begin_import
import|import
name|java
operator|.
name|sql
operator|.
name|ResultSet
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
name|Statement
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
name|Level
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
name|Logger
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
name|listener
operator|.
name|PostgresSQLNotificationListener
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
name|DatabaseConnection
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
name|entry
operator|.
name|BibEntry
import|;
end_import

begin_import
import|import
name|com
operator|.
name|impossibl
operator|.
name|postgres
operator|.
name|api
operator|.
name|jdbc
operator|.
name|PGConnection
import|;
end_import

begin_import
import|import
name|com
operator|.
name|impossibl
operator|.
name|postgres
operator|.
name|jdbc
operator|.
name|PGDataSource
import|;
end_import

begin_import
import|import
name|com
operator|.
name|impossibl
operator|.
name|postgres
operator|.
name|jdbc
operator|.
name|ThreadedHousekeeper
import|;
end_import

begin_comment
comment|/**  * Processes all incoming or outgoing bib data to PostgreSQL database and manages its structure.  */
end_comment

begin_class
DECL|class|PostgreSQLProcessor
specifier|public
class|class
name|PostgreSQLProcessor
extends|extends
name|DBMSProcessor
block|{
DECL|field|pgConnection
specifier|private
name|PGConnection
name|pgConnection
decl_stmt|;
DECL|field|listener
specifier|private
name|PostgresSQLNotificationListener
name|listener
decl_stmt|;
DECL|method|PostgreSQLProcessor (DatabaseConnection connection)
specifier|public
name|PostgreSQLProcessor
parameter_list|(
name|DatabaseConnection
name|connection
parameter_list|)
block|{
name|super
argument_list|(
name|connection
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates and sets up the needed tables and columns according to the database type.      *      * @throws SQLException      */
annotation|@
name|Override
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
throws|throws
name|SQLException
block|{
name|connection
operator|.
name|createStatement
argument_list|()
operator|.
name|executeUpdate
argument_list|(
literal|"CREATE TABLE IF NOT EXISTS \"ENTRY\" ("
operator|+
literal|"\"SHARED_ID\" SERIAL PRIMARY KEY, "
operator|+
literal|"\"TYPE\" VARCHAR, "
operator|+
literal|"\"VERSION\" INTEGER DEFAULT 1)"
argument_list|)
expr_stmt|;
name|connection
operator|.
name|createStatement
argument_list|()
operator|.
name|executeUpdate
argument_list|(
literal|"CREATE TABLE IF NOT EXISTS \"FIELD\" ("
operator|+
literal|"\"ENTRY_SHARED_ID\" INTEGER REFERENCES \"ENTRY\"(\"SHARED_ID\") ON DELETE CASCADE, "
operator|+
literal|"\"NAME\" VARCHAR, "
operator|+
literal|"\"VALUE\" TEXT)"
argument_list|)
expr_stmt|;
name|connection
operator|.
name|createStatement
argument_list|()
operator|.
name|executeUpdate
argument_list|(
literal|"CREATE TABLE IF NOT EXISTS \"METADATA\" ("
operator|+
literal|"\"KEY\" VARCHAR,"
operator|+
literal|"\"VALUE\" TEXT)"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|insertIntoEntryTable (BibEntry bibEntry)
specifier|protected
name|void
name|insertIntoEntryTable
parameter_list|(
name|BibEntry
name|bibEntry
parameter_list|)
block|{
comment|// Inserting into ENTRY table
name|StringBuilder
name|insertIntoEntryQuery
init|=
operator|new
name|StringBuilder
argument_list|()
operator|.
name|append
argument_list|(
literal|"INSERT INTO "
argument_list|)
operator|.
name|append
argument_list|(
name|escape
argument_list|(
literal|"ENTRY"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|"("
argument_list|)
operator|.
name|append
argument_list|(
name|escape
argument_list|(
literal|"TYPE"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|") VALUES(?)"
argument_list|)
decl_stmt|;
comment|// This is the only method to get generated keys which is accepted by MySQL, PostgreSQL and Oracle.
try|try
init|(
name|PreparedStatement
name|preparedEntryStatement
init|=
name|connection
operator|.
name|prepareStatement
argument_list|(
name|insertIntoEntryQuery
operator|.
name|toString
argument_list|()
argument_list|,
name|Statement
operator|.
name|RETURN_GENERATED_KEYS
argument_list|)
init|)
block|{
name|preparedEntryStatement
operator|.
name|setString
argument_list|(
literal|1
argument_list|,
name|bibEntry
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|preparedEntryStatement
operator|.
name|executeUpdate
argument_list|()
expr_stmt|;
try|try
init|(
name|ResultSet
name|generatedKeys
init|=
name|preparedEntryStatement
operator|.
name|getGeneratedKeys
argument_list|()
init|)
block|{
if|if
condition|(
name|generatedKeys
operator|.
name|next
argument_list|()
condition|)
block|{
name|bibEntry
operator|.
name|getSharedBibEntryData
argument_list|()
operator|.
name|setSharedID
argument_list|(
name|generatedKeys
operator|.
name|getInt
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
comment|// set generated ID locally
block|}
block|}
block|}
catch|catch
parameter_list|(
name|SQLException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"SQL Error: "
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|escape (String expression)
name|String
name|escape
parameter_list|(
name|String
name|expression
parameter_list|)
block|{
return|return
literal|"\""
operator|+
name|expression
operator|+
literal|"\""
return|;
block|}
annotation|@
name|Override
DECL|method|startNotificationListener (DBMSSynchronizer dbmsSynchronizer)
specifier|public
name|void
name|startNotificationListener
parameter_list|(
name|DBMSSynchronizer
name|dbmsSynchronizer
parameter_list|)
block|{
comment|// Disable cleanup output of ThreadedHousekeeper
comment|// TODO: this one is strange
comment|/*         /home/florian/jabref/src/main/java/org/jabref/logic/shared/PostgreSQLProcessor.java:98: error: cannot access Referenceable         dataSource.setHost(connectionProperties.getHost());                   ^         class file for javax.naming.Referenceable not found         */
comment|//        LOGGER.error("Notification listener disabled for now.");
comment|//        Logger.getLogger(ThreadedHousekeeper.class.getName()).setLevel(Level.SEVERE);
comment|//
comment|//        this.listener = new PostgresSQLNotificationListener(dbmsSynchronizer);
comment|//
comment|//        PGDataSource dataSource = new PGDataSource();
comment|//        dataSource.setHost(connectionProperties.getHost());
comment|//        dataSource.setPort(connectionProperties.getPort());
comment|//        dataSource.setDatabase(connectionProperties.getDatabase());
comment|//        dataSource.setUser(connectionProperties.getUser());
comment|//        dataSource.setPassword(connectionProperties.getPassword());
comment|//
comment|//        try {
comment|//            pgConnection = (PGConnection) dataSource.getConnection();
comment|//            pgConnection.createStatement().execute("LISTEN jabrefLiveUpdate");
comment|//            // Do not use `new PostgresSQLNotificationListener(...)` as the object has to exist continuously!
comment|//            // Otherwise the listener is going to be deleted by GC.
comment|//            pgConnection.addNotificationListener(listener);
comment|//        } catch (SQLException e) {
comment|//            LOGGER.error("SQL Error: ", e);
comment|//        }
block|}
annotation|@
name|Override
DECL|method|stopNotificationListener ()
specifier|public
name|void
name|stopNotificationListener
parameter_list|()
block|{
try|try
block|{
name|pgConnection
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SQLException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"SQL Error: "
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|notifyClients ()
specifier|public
name|void
name|notifyClients
parameter_list|()
block|{
try|try
block|{
name|pgConnection
operator|.
name|createStatement
argument_list|()
operator|.
name|execute
argument_list|(
literal|"NOTIFY jabrefLiveUpdate, '"
operator|+
name|PROCESSOR_ID
operator|+
literal|"';"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SQLException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"SQL Error: "
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

