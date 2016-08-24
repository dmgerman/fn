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
comment|/**  * Processes all incoming or outgoing bib data to Oracle database and manages its structure.  */
end_comment

begin_class
DECL|class|OracleProcessor
specifier|public
class|class
name|OracleProcessor
extends|extends
name|DBMSProcessor
block|{
comment|/**      * @param connection Working SQL connection      * @param dbmsType Instance of {@link DBMSType}      */
DECL|method|OracleProcessor (Connection connection)
specifier|public
name|OracleProcessor
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
literal|"CREATE TABLE \"ENTRY\" ("
operator|+
literal|"\"SHARED_ID\" NUMBER NOT NULL, "
operator|+
literal|"\"TYPE\" VARCHAR2(255) NULL, "
operator|+
literal|"\"VERSION\" NUMBER DEFAULT 1, "
operator|+
literal|"CONSTRAINT \"ENTRY_PK\" PRIMARY KEY (\"SHARED_ID\"))"
argument_list|)
expr_stmt|;
name|connection
operator|.
name|createStatement
argument_list|()
operator|.
name|executeUpdate
argument_list|(
literal|"CREATE SEQUENCE \"ENTRY_SEQ\""
argument_list|)
expr_stmt|;
name|connection
operator|.
name|createStatement
argument_list|()
operator|.
name|executeUpdate
argument_list|(
literal|"CREATE TRIGGER \"ENTRY_T\" BEFORE INSERT ON \"ENTRY\" "
operator|+
literal|"FOR EACH ROW BEGIN SELECT \"ENTRY_SEQ\".NEXTVAL INTO :NEW.shared_id FROM DUAL; END;"
argument_list|)
expr_stmt|;
name|connection
operator|.
name|createStatement
argument_list|()
operator|.
name|executeUpdate
argument_list|(
literal|"CREATE TABLE \"FIELD\" ("
operator|+
literal|"\"ENTRY_SHARED_ID\" NUMBER NOT NULL, "
operator|+
literal|"\"NAME\" VARCHAR2(255) NOT NULL, "
operator|+
literal|"\"VALUE\" CLOB NULL, "
operator|+
literal|"CONSTRAINT \"ENTRY_SHARED_ID_FK\" FOREIGN KEY (\"ENTRY_SHARED_ID\") "
operator|+
literal|"REFERENCES \"ENTRY\"(\"SHARED_ID\") ON DELETE CASCADE)"
argument_list|)
expr_stmt|;
name|connection
operator|.
name|createStatement
argument_list|()
operator|.
name|executeUpdate
argument_list|(
literal|"CREATE TABLE \"METADATA\" ("
operator|+
literal|"\"KEY\"  VARCHAR2(255) NULL,"
operator|+
literal|"\"VALUE\"  CLOB NOT NULL)"
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

