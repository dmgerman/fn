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
name|model
operator|.
name|database
operator|.
name|shared
operator|.
name|DatabaseConnection
import|;
end_import

begin_comment
comment|/**  * Processes all incoming or outgoing bib data to MySQL Database and manages its structure.  */
end_comment

begin_class
DECL|class|MySQLProcessor
specifier|public
class|class
name|MySQLProcessor
extends|extends
name|DBMSProcessor
block|{
DECL|method|MySQLProcessor (DatabaseConnection connection)
specifier|public
name|MySQLProcessor
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
literal|"CREATE TABLE IF NOT EXISTS `ENTRY` ("
operator|+
literal|"`SHARED_ID` INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT, "
operator|+
literal|"`TYPE` VARCHAR(255) NOT NULL, "
operator|+
literal|"`VERSION` INT(11) DEFAULT 1)"
argument_list|)
expr_stmt|;
name|connection
operator|.
name|createStatement
argument_list|()
operator|.
name|executeUpdate
argument_list|(
literal|"CREATE TABLE IF NOT EXISTS `FIELD` ("
operator|+
literal|"`ENTRY_SHARED_ID` INT(11) NOT NULL, "
operator|+
literal|"`NAME` VARCHAR(255) NOT NULL, "
operator|+
literal|"`VALUE` TEXT DEFAULT NULL, "
operator|+
literal|"FOREIGN KEY (`ENTRY_SHARED_ID`) REFERENCES `ENTRY`(`SHARED_ID`) ON DELETE CASCADE)"
argument_list|)
expr_stmt|;
name|connection
operator|.
name|createStatement
argument_list|()
operator|.
name|executeUpdate
argument_list|(
literal|"CREATE TABLE IF NOT EXISTS `METADATA` ("
operator|+
literal|"`KEY` varchar(255) NOT NULL,"
operator|+
literal|"`VALUE` text NOT NULL)"
argument_list|)
expr_stmt|;
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
literal|"`"
operator|+
name|expression
operator|+
literal|"`"
return|;
block|}
block|}
end_class

end_unit

