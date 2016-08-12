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
name|Connection
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
comment|/**      * @param connection Working SQL connection      * @param dbmsType Instance of {@link DBMSType}      */
DECL|method|PostgreSQLProcessor (Connection connection)
specifier|public
name|PostgreSQLProcessor
parameter_list|(
name|Connection
name|connection
parameter_list|)
block|{
name|super
argument_list|(
name|connection
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates and sets up the needed tables and columns according to the database type.      * @throws SQLException      */
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
DECL|method|escape (String expression)
specifier|public
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
block|}
end_class

end_unit

