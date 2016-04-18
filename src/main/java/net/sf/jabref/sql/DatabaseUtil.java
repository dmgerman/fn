begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.sql
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|sql
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
name|javax
operator|.
name|swing
operator|.
name|JOptionPane
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|BibDatabaseContext
import|;
end_import

begin_class
DECL|class|DatabaseUtil
specifier|public
class|class
name|DatabaseUtil
block|{
DECL|method|removeDB (DBImportExportDialog dialogo, String dbName, Connection conn, BibDatabaseContext databaseContext)
specifier|public
specifier|static
name|void
name|removeDB
parameter_list|(
name|DBImportExportDialog
name|dialogo
parameter_list|,
name|String
name|dbName
parameter_list|,
name|Connection
name|conn
parameter_list|,
name|BibDatabaseContext
name|databaseContext
parameter_list|)
throws|throws
name|SQLException
block|{
if|if
condition|(
name|dialogo
operator|.
name|removeAction
condition|)
block|{
if|if
condition|(
operator|(
name|dialogo
operator|.
name|selectedInt
operator|<=
literal|0
operator|)
operator|&&
name|dialogo
operator|.
name|isExporter
argument_list|()
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|dialogo
operator|.
name|getDiag
argument_list|()
argument_list|,
literal|"Please select a DB to be removed"
argument_list|,
literal|"SQL Export"
argument_list|,
name|JOptionPane
operator|.
name|INFORMATION_MESSAGE
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|removeDB
argument_list|(
name|dbName
argument_list|,
name|conn
argument_list|,
name|databaseContext
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|removeDB (String dbName, Connection conn, BibDatabaseContext databaseContext)
specifier|public
specifier|static
name|void
name|removeDB
parameter_list|(
name|String
name|dbName
parameter_list|,
name|Connection
name|conn
parameter_list|,
name|BibDatabaseContext
name|databaseContext
parameter_list|)
throws|throws
name|SQLException
block|{
name|removeAGivenDB
argument_list|(
name|conn
argument_list|,
name|getDatabaseIDByName
argument_list|(
name|databaseContext
argument_list|,
name|conn
argument_list|,
name|dbName
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Returns a Jabref Database ID from the database in case the DB is already exported. In case the bib was already      * exported before, the method returns the id, otherwise it calls the method that inserts a new row and returns the      * ID for this new database      *      * @param databaseContext the database      * @param out The output (PrintStream or Connection) object to which the DML should be written.      * @return The ID of database row of the jabref database being exported      * @throws SQLException      */
DECL|method|getDatabaseIDByName (BibDatabaseContext databaseContext, Object out, String dbName)
specifier|public
specifier|static
name|int
name|getDatabaseIDByName
parameter_list|(
name|BibDatabaseContext
name|databaseContext
parameter_list|,
name|Object
name|out
parameter_list|,
name|String
name|dbName
parameter_list|)
throws|throws
name|SQLException
block|{
if|if
condition|(
name|out
operator|instanceof
name|Connection
condition|)
block|{
name|String
name|query
init|=
literal|"SELECT database_id FROM jabref_database WHERE database_name='"
operator|+
name|dbName
operator|+
literal|"';"
decl_stmt|;
try|try
init|(
name|Statement
name|statement
init|=
call|(
name|Statement
call|)
argument_list|(
operator|(
name|Connection
operator|)
name|out
argument_list|)
operator|.
name|createStatement
argument_list|()
init|;                  ResultSet rs = statement.executeQuery(query)
block|)
block|{
if|if
condition|(
name|rs
operator|.
name|next
argument_list|()
condition|)
block|{
return|return
name|rs
operator|.
name|getInt
argument_list|(
literal|"database_id"
argument_list|)
return|;
block|}
else|else
block|{
name|insertJabRefDatabase
argument_list|(
name|databaseContext
argument_list|,
name|out
argument_list|,
name|dbName
argument_list|)
expr_stmt|;
return|return
name|getDatabaseIDByName
argument_list|(
name|databaseContext
argument_list|,
name|out
argument_list|,
name|dbName
argument_list|)
return|;
block|}
block|}
block|}
else|else
block|{
comment|// in case of text export there will be only 1 bib exported
name|insertJabRefDatabase
argument_list|(
name|databaseContext
argument_list|,
name|out
argument_list|,
name|dbName
argument_list|)
expr_stmt|;
return|return
literal|1
return|;
block|}
block|}
end_class

begin_function
DECL|method|removeAGivenDB (Object out, final int database_id)
specifier|private
specifier|static
name|void
name|removeAGivenDB
parameter_list|(
name|Object
name|out
parameter_list|,
specifier|final
name|int
name|database_id
parameter_list|)
throws|throws
name|SQLException
block|{
name|removeAllRecordsForAGivenDB
argument_list|(
name|out
argument_list|,
name|database_id
argument_list|)
expr_stmt|;
name|SQLUtil
operator|.
name|processQuery
argument_list|(
name|out
argument_list|,
literal|"DELETE FROM jabref_database WHERE database_id='"
operator|+
name|database_id
operator|+
literal|"';"
argument_list|)
expr_stmt|;
block|}
end_function

begin_comment
comment|/**      * Removes all records for the database being exported in case it was exported before.      *      * @param out The output (PrintStream or Connection) object to which the DML should be written.      * @param database_id Id of the database being exported.      * @throws SQLException      */
end_comment

begin_function
DECL|method|removeAllRecordsForAGivenDB (Object out, final int database_id)
specifier|public
specifier|static
name|void
name|removeAllRecordsForAGivenDB
parameter_list|(
name|Object
name|out
parameter_list|,
specifier|final
name|int
name|database_id
parameter_list|)
throws|throws
name|SQLException
block|{
name|SQLUtil
operator|.
name|processQuery
argument_list|(
name|out
argument_list|,
literal|"DELETE FROM entries WHERE database_id='"
operator|+
name|database_id
operator|+
literal|"';"
argument_list|)
expr_stmt|;
name|SQLUtil
operator|.
name|processQuery
argument_list|(
name|out
argument_list|,
literal|"DELETE FROM groups WHERE database_id='"
operator|+
name|database_id
operator|+
literal|"';"
argument_list|)
expr_stmt|;
name|SQLUtil
operator|.
name|processQuery
argument_list|(
name|out
argument_list|,
literal|"DELETE FROM strings WHERE database_id='"
operator|+
name|database_id
operator|+
literal|"';"
argument_list|)
expr_stmt|;
block|}
end_function

begin_comment
comment|/**      * This method creates a new row into jabref_database table enabling to export more than one .bib      *      * @param databaseContext the database      * @param out The output (PrintStream or Connection) object to which the DML should be written.      *      * @throws SQLException      */
end_comment

begin_function
DECL|method|insertJabRefDatabase (final BibDatabaseContext databaseContext, Object out, String dbName)
specifier|private
specifier|static
name|void
name|insertJabRefDatabase
parameter_list|(
specifier|final
name|BibDatabaseContext
name|databaseContext
parameter_list|,
name|Object
name|out
parameter_list|,
name|String
name|dbName
parameter_list|)
throws|throws
name|SQLException
block|{
name|String
name|path
decl_stmt|;
if|if
condition|(
name|databaseContext
operator|.
name|getDatabaseFile
argument_list|()
operator|==
literal|null
condition|)
block|{
name|path
operator|=
name|dbName
expr_stmt|;
block|}
else|else
block|{
name|path
operator|=
name|databaseContext
operator|.
name|getDatabaseFile
argument_list|()
operator|.
name|getAbsolutePath
argument_list|()
expr_stmt|;
block|}
name|SQLUtil
operator|.
name|processQuery
argument_list|(
name|out
argument_list|,
literal|"INSERT INTO jabref_database(database_name, md5_path) VALUES ('"
operator|+
name|dbName
operator|+
literal|"', md5('"
operator|+
name|path
operator|+
literal|"'));"
argument_list|)
expr_stmt|;
block|}
end_function

unit|}
end_unit
