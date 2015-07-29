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
name|MetaData
import|;
end_import

begin_class
DECL|class|DBImporterExporter
specifier|public
class|class
name|DBImporterExporter
block|{
DECL|method|removeDB (DBImportExportDialog dialogo, String dbName, Connection conn, MetaData metadata)
specifier|public
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
name|MetaData
name|metadata
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
name|removeAGivenDB
argument_list|(
name|conn
argument_list|,
name|getDatabaseIDByName
argument_list|(
name|metadata
argument_list|,
name|conn
argument_list|,
name|dbName
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * Returns a Jabref Database ID from the database in case the DB is already      * exported. In case the bib was already exported before, the method returns      * the id, otherwise it calls the method that inserts a new row and returns      * the ID for this new database      *      * @param metaData      *            The MetaData object containing the database information      * @param out      *            The output (PrintStream or Connection) object to which the DML      *            should be written.      * @return The ID of database row of the jabref database being exported      * @throws SQLException      */
DECL|method|getDatabaseIDByName (MetaData metaData, Object out, String dbName)
specifier|protected
name|int
name|getDatabaseIDByName
parameter_list|(
name|MetaData
name|metaData
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
name|Object
name|response
init|=
name|SQLUtil
operator|.
name|processQueryWithResults
argument_list|(
name|out
argument_list|,
literal|"SELECT database_id FROM jabref_database WHERE database_name='"
operator|+
name|dbName
operator|+
literal|"';"
argument_list|)
decl_stmt|;
name|ResultSet
name|rs
init|=
operator|(
operator|(
name|Statement
operator|)
name|response
operator|)
operator|.
name|getResultSet
argument_list|()
decl_stmt|;
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
name|metaData
argument_list|,
name|out
argument_list|,
name|dbName
argument_list|)
expr_stmt|;
return|return
name|getDatabaseIDByName
argument_list|(
name|metaData
argument_list|,
name|out
argument_list|,
name|dbName
argument_list|)
return|;
block|}
block|}
comment|// in case of text export there will be only 1 bib exported
else|else
block|{
name|insertJabRefDatabase
argument_list|(
name|metaData
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
DECL|method|removeAGivenDB (Object out, int database_id)
specifier|private
name|void
name|removeAGivenDB
parameter_list|(
name|Object
name|out
parameter_list|,
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
comment|/**      * Removes all records for the database being exported in case it was      * exported before.      *      * @param out      *            The output (PrintStream or Connection) object to which the DML      *            should be written.      * @param database_id      *            Id of the database being exported.      * @throws SQLException      */
DECL|method|removeAllRecordsForAGivenDB (Object out, int database_id)
specifier|protected
name|void
name|removeAllRecordsForAGivenDB
parameter_list|(
name|Object
name|out
parameter_list|,
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
comment|/**      * This method creates a new row into jabref_database table enabling to      * export more than one .bib      *      * @param metaData      *            The MetaData object containing the groups information      * @param out      *            The output (PrintStream or Connection) object to which the DML      *            should be written.      *      * @throws SQLException      */
DECL|method|insertJabRefDatabase (final MetaData metaData, Object out, String dbName)
specifier|private
name|void
name|insertJabRefDatabase
parameter_list|(
specifier|final
name|MetaData
name|metaData
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
name|metaData
operator|.
name|getFile
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
name|metaData
operator|.
name|getFile
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
block|}
end_class

end_unit

