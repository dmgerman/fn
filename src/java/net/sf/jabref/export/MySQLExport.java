begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.export
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
package|;
end_package

begin_import
import|import
name|junit
operator|.
name|awtui
operator|.
name|Logo
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
name|Globals
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
name|BibtexDatabase
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
name|BibtexEntry
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
name|BibtexEntryType
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
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileOutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|PrintStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
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
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ListIterator
import|;
end_import

begin_comment
comment|/**  * Skeleton implementation of MySQLExporter.  */
end_comment

begin_class
DECL|class|MySQLExport
specifier|public
class|class
name|MySQLExport
extends|extends
name|ExportFormat
block|{
DECL|method|MySQLExport ()
specifier|public
name|MySQLExport
parameter_list|()
block|{
comment|// You need to fill in the correct extension, and edit the name if necessary.
comment|// The second argument is the command-line name of the export format:
name|super
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"MySQL database"
argument_list|)
argument_list|,
literal|"mysql"
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
literal|".sql"
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * First method calledd when user starts the export. 	 * 	 * @param database The bibtex database from which to export. 	 * @param file The filename to which the export should be writtten.      * @param encoding The encoding to use.      * @param keySet The set of IDs of the entries to export. 	 * @throws java.lang.Exception  	 */
DECL|method|performExport (final BibtexDatabase database, final MetaData metaData, final String file, final String encoding, Set keySet)
specifier|public
name|void
name|performExport
parameter_list|(
specifier|final
name|BibtexDatabase
name|database
parameter_list|,
specifier|final
name|MetaData
name|metaData
parameter_list|,
specifier|final
name|String
name|file
parameter_list|,
specifier|final
name|String
name|encoding
parameter_list|,
name|Set
name|keySet
parameter_list|)
throws|throws
name|Exception
block|{
comment|// get TreeMap of all Bibtex Entry Types
name|TreeMap
argument_list|<
name|String
argument_list|,
name|BibtexEntryType
argument_list|>
name|ALL_TYPES
init|=
name|BibtexEntryType
operator|.
name|ALL_TYPES
decl_stmt|;
name|ArrayList
argument_list|<
name|String
argument_list|>
name|fields
init|=
operator|new
name|ArrayList
argument_list|()
decl_stmt|;
name|Set
name|mappings
init|=
name|ALL_TYPES
operator|.
name|entrySet
argument_list|()
decl_stmt|;
comment|// loop through entry types
for|for
control|(
name|Iterator
name|iter
init|=
name|mappings
operator|.
name|iterator
argument_list|()
init|;
name|iter
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
comment|// get the entry type
name|Map
operator|.
name|Entry
name|me
init|=
operator|(
name|Map
operator|.
name|Entry
operator|)
name|iter
operator|.
name|next
argument_list|()
decl_stmt|;
name|BibtexEntryType
name|val
init|=
operator|(
name|BibtexEntryType
operator|)
name|me
operator|.
name|getValue
argument_list|()
decl_stmt|;
comment|// get required, optional, general and utility fields for this type
name|fields
operator|=
name|processFields
argument_list|(
name|fields
argument_list|,
name|val
operator|.
name|getRequiredFields
argument_list|()
argument_list|)
expr_stmt|;
name|fields
operator|=
name|processFields
argument_list|(
name|fields
argument_list|,
name|val
operator|.
name|getOptionalFields
argument_list|()
argument_list|)
expr_stmt|;
name|fields
operator|=
name|processFields
argument_list|(
name|fields
argument_list|,
name|val
operator|.
name|getGeneralFields
argument_list|()
argument_list|)
expr_stmt|;
name|fields
operator|=
name|processFields
argument_list|(
name|fields
argument_list|,
name|val
operator|.
name|getUtilityFields
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// open output file
name|File
name|outfile
init|=
operator|new
name|File
argument_list|(
name|file
argument_list|)
decl_stmt|;
if|if
condition|(
name|outfile
operator|.
name|exists
argument_list|()
condition|)
name|outfile
operator|.
name|delete
argument_list|()
expr_stmt|;
name|PrintStream
name|fout
init|=
operator|new
name|PrintStream
argument_list|(
name|outfile
argument_list|)
decl_stmt|;
comment|// generate SQL that specifies columns corresponding to fields
name|String
name|sql1
init|=
name|sql_fieldColumns
argument_list|(
name|fields
argument_list|,
literal|"\tVARCHAR(3)\t\tDEFAULT NULL"
argument_list|)
decl_stmt|;
name|String
name|sql2
init|=
name|sql_fieldColumns
argument_list|(
name|fields
argument_list|,
literal|"\tTEXT\t\tDEFAULT NULL"
argument_list|)
decl_stmt|;
comment|// generate MySQL tables using auto-generated SQL
name|String
name|sql
init|=
name|sql_createTables
argument_list|(
name|sql1
argument_list|,
name|sql2
argument_list|)
decl_stmt|;
comment|// write SQL table creation to file
name|fout
operator|.
name|println
argument_list|(
name|sql
argument_list|)
expr_stmt|;
comment|// create comma separated list of field names for use in INSERT statements
name|String
name|fieldstr
init|=
literal|""
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|fields
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|i
operator|>
literal|0
condition|)
name|fieldstr
operator|=
name|fieldstr
operator|+
literal|", "
expr_stmt|;
name|fieldstr
operator|=
name|fieldstr
operator|+
name|fields
operator|.
name|get
argument_list|(
name|i
argument_list|)
expr_stmt|;
block|}
comment|// get entries selected for export
name|List
argument_list|<
name|BibtexEntry
argument_list|>
name|entries
init|=
name|FileActions
operator|.
name|getSortedEntries
argument_list|(
name|database
argument_list|,
name|keySet
argument_list|,
literal|false
argument_list|)
decl_stmt|;
comment|// populate entry_type table
name|sql_popTabET
argument_list|(
name|mappings
argument_list|,
name|fields
argument_list|,
name|fieldstr
argument_list|,
name|fout
argument_list|)
expr_stmt|;
comment|// populate entries table
name|sql_popTabFD
argument_list|(
name|entries
argument_list|,
name|fields
argument_list|,
name|fieldstr
argument_list|,
name|fout
argument_list|)
expr_stmt|;
name|fout
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Inserts the elements of a String array into an ArrayList making sure not  	 * to duplicate entries in the ArrayList 	 *  	 * @param fields The ArrayList containing unique entries 	 * @param efields The String array to be inserted into the ArrayList 	 * @return The updated ArrayList with new unique entries 	 */
DECL|method|processFields ( ArrayList<String> fields, String[] efields)
specifier|private
name|ArrayList
argument_list|<
name|String
argument_list|>
name|processFields
parameter_list|(
name|ArrayList
argument_list|<
name|String
argument_list|>
name|fields
parameter_list|,
name|String
index|[]
name|efields
parameter_list|)
block|{
if|if
condition|(
name|efields
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|efields
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
operator|!
name|fields
operator|.
name|contains
argument_list|(
name|efields
index|[
name|i
index|]
argument_list|)
condition|)
name|fields
operator|.
name|add
argument_list|(
name|efields
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|fields
return|;
block|}
comment|/** 	 * Generates DML specifying table columns and thier datatypes.  The output 	 * of this routine should be used within a CREATE TABLE statement. 	 *  	 * @param fields  Contains unique field names 	 * @param datatype Specifies the SQL data type that the fields should take on. 	 * @return The DML code to be included in a CREATE TABLE statement. 	 */
DECL|method|sql_fieldColumns ( ArrayList<String> fields, String datatype)
specifier|private
name|String
name|sql_fieldColumns
parameter_list|(
name|ArrayList
argument_list|<
name|String
argument_list|>
name|fields
parameter_list|,
name|String
name|datatype
parameter_list|)
block|{
name|String
name|str
init|=
literal|""
decl_stmt|;
name|ListIterator
argument_list|<
name|String
argument_list|>
name|li
init|=
name|fields
operator|.
name|listIterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|li
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|str
operator|=
name|str
operator|+
name|li
operator|.
name|next
argument_list|()
operator|+
literal|"\t"
operator|+
name|datatype
expr_stmt|;
if|if
condition|(
name|li
operator|.
name|hasNext
argument_list|()
condition|)
name|str
operator|=
name|str
operator|+
literal|",\n"
expr_stmt|;
block|}
return|return
name|str
return|;
block|}
comment|/** 	 * Returns the DML code necessary to create all tables required for holding 	 * jabref database. 	 * 	 * @param sql1 Column specifications for fields in entry_type table. 	 * @param sql2 Column specifications for fields in entries table. 	 * @return DML to creat all tables. 	 */
DECL|method|sql_createTables ( String sql1, String sql2)
specifier|private
name|String
name|sql_createTables
parameter_list|(
name|String
name|sql1
parameter_list|,
name|String
name|sql2
parameter_list|)
block|{
name|String
name|sql
init|=
literal|"DROP TABLE IF EXISTS entry_type;\n"
operator|+
literal|"CREATE TABLE entry_types\n"
operator|+
literal|"(\n"
operator|+
literal|"entry_types_id    INT UNSIGNED  NOT NULL AUTO_INCREMENT,\n"
operator|+
literal|"label			 TEXT,\n"
operator|+
name|sql1
operator|+
literal|",\n"
operator|+
literal|"PRIMARY KEY (entry_types_id)\n"
operator|+
literal|");\n"
operator|+
literal|"\n"
operator|+
literal|"DROP TABLE IF EXISTS entries;\n"
operator|+
literal|"create table entries\n"
operator|+
literal|"(\n"
operator|+
literal|"entries_id      INTEGER         NOT NULL AUTO_INCREMENT,\n"
operator|+
literal|"entry_types_id  INTEGER         DEFAULT NULL,\n"
operator|+
literal|"cite_key 	   VARCHAR(30)     DEFAULT NULL,\n"
operator|+
name|sql2
operator|+
literal|",\n"
operator|+
literal|"PRIMARY KEY (entries_id),\n"
operator|+
literal|"FOREIGN KEY (entry_types_id) REFERENCES entry_type(entry_types_id)\n"
operator|+
literal|");\n"
operator|+
literal|"\n"
operator|+
literal|"DROP TABLE IF EXISTS groups;\n"
operator|+
literal|"CREATE TABLE groups\n"
operator|+
literal|"(\n"
operator|+
literal|"groups_id       INTEGER         NOT NULL AUTO_INCREMENT,\n"
operator|+
literal|"label           VARCHAR(30)     DEFAULT NULL,\n"
operator|+
literal|"parent_id       INTEGER         DEFAULT NULL,\n"
operator|+
literal|"PRIMARY KEY (groups_id)\n"
operator|+
literal|");\n"
operator|+
literal|"\n"
operator|+
literal|"DROP TABLE IF EXISTS entry_group;\n"
operator|+
literal|"CREATE TABLE entry_group\n"
operator|+
literal|"(\n"
operator|+
literal|"entries_id       INTEGER        NOT NULL AUTO_INCREMENT,\n"
operator|+
literal|"groups_id        INTEGER        DEFAULT NULL,\n"
operator|+
literal|"FOREIGN KEY (entries_id) REFERENCES entry_fields(entries_id),\n"
operator|+
literal|"FOREIGN KEY (groups_id)  REFERENCES groups(groups_id)\n"
operator|+
literal|");\n"
decl_stmt|;
return|return
name|sql
return|;
block|}
comment|/** 	 * Generates the DML required to populate the entry_types table with jabref data.  	 * 	 * @param mappings A Set of bibtex entries that are to be exported. 	 * @param fields The fields to be specified. 	 * @param fieldstr A comma delimited string of all field names. 	 * @param out The printstream to which the DML should be written. 	 */
DECL|method|sql_popTabET ( Set mappings, ArrayList fields, String fieldstr, PrintStream out)
specifier|private
name|void
name|sql_popTabET
parameter_list|(
name|Set
name|mappings
parameter_list|,
name|ArrayList
name|fields
parameter_list|,
name|String
name|fieldstr
parameter_list|,
name|PrintStream
name|out
parameter_list|)
block|{
name|String
name|sql
init|=
literal|""
decl_stmt|;
name|String
name|insert
init|=
literal|"INSERT INTO entry_types (label, "
operator|+
name|fieldstr
operator|+
literal|") VALUES ("
decl_stmt|;
name|ArrayList
name|fieldID
init|=
operator|new
name|ArrayList
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|fields
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
name|fieldID
operator|.
name|add
argument_list|(
literal|null
argument_list|)
expr_stmt|;
comment|// loop through entry types
name|Integer
name|cnt
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Iterator
name|iter
init|=
name|mappings
operator|.
name|iterator
argument_list|()
init|;
name|iter
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
comment|// get the entry type
name|Map
operator|.
name|Entry
name|me
init|=
operator|(
name|Map
operator|.
name|Entry
operator|)
name|iter
operator|.
name|next
argument_list|()
decl_stmt|;
name|BibtexEntryType
name|val
init|=
operator|(
name|BibtexEntryType
operator|)
name|me
operator|.
name|getValue
argument_list|()
decl_stmt|;
comment|// set ID for each field corresponding to its relationship to the entry type
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|fieldID
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|fieldID
operator|.
name|set
argument_list|(
name|i
argument_list|,
literal|""
argument_list|)
expr_stmt|;
block|}
name|fieldID
operator|=
name|setFieldID
argument_list|(
name|fields
argument_list|,
name|fieldID
argument_list|,
name|val
operator|.
name|getRequiredFields
argument_list|()
argument_list|,
literal|"req"
argument_list|)
expr_stmt|;
name|fieldID
operator|=
name|setFieldID
argument_list|(
name|fields
argument_list|,
name|fieldID
argument_list|,
name|val
operator|.
name|getOptionalFields
argument_list|()
argument_list|,
literal|"opt"
argument_list|)
expr_stmt|;
name|fieldID
operator|=
name|setFieldID
argument_list|(
name|fields
argument_list|,
name|fieldID
argument_list|,
name|val
operator|.
name|getGeneralFields
argument_list|()
argument_list|,
literal|"gen"
argument_list|)
expr_stmt|;
name|fieldID
operator|=
name|setFieldID
argument_list|(
name|fields
argument_list|,
name|fieldID
argument_list|,
name|val
operator|.
name|getUtilityFields
argument_list|()
argument_list|,
literal|"uti"
argument_list|)
expr_stmt|;
comment|// build SQL insert statement
name|sql
operator|=
name|insert
operator|+
literal|"\""
operator|+
name|val
operator|.
name|getName
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|+
literal|"\""
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|fieldID
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|sql
operator|=
name|sql
operator|+
literal|", "
expr_stmt|;
if|if
condition|(
name|fieldID
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|!=
literal|""
condition|)
block|{
name|sql
operator|=
name|sql
operator|+
literal|"\""
operator|+
name|fieldID
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|+
literal|"\""
expr_stmt|;
block|}
else|else
block|{
name|sql
operator|=
name|sql
operator|+
literal|"NULL"
expr_stmt|;
block|}
block|}
name|sql
operator|=
name|sql
operator|+
literal|");"
expr_stmt|;
comment|// write SQL insert to file
if|if
condition|(
name|out
operator|instanceof
name|PrintStream
condition|)
block|{
name|out
operator|.
name|println
argument_list|(
name|sql
argument_list|)
expr_stmt|;
block|}
block|}
return|return;
block|}
comment|/** 	 * A utility function for facilitating the assignment of a code to each field 	 * name that represents the relationship of that field to a specific entry type.  	 * 	 * @param fields  A list of all fields. 	 * @param fieldID A list for holding the codes. 	 * @param fieldstr A String array containing the fields to be coded. 	 * @param ID The code that should be assigned to the specified fields. 	 * @return The updated code list. 	 */
DECL|method|setFieldID ( ArrayList fields, ArrayList fieldID, String[] fieldstr, String ID )
specifier|private
name|ArrayList
name|setFieldID
parameter_list|(
name|ArrayList
name|fields
parameter_list|,
name|ArrayList
name|fieldID
parameter_list|,
name|String
index|[]
name|fieldstr
parameter_list|,
name|String
name|ID
parameter_list|)
block|{
if|if
condition|(
name|fieldstr
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|fieldstr
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|fieldID
operator|.
name|set
argument_list|(
name|fields
operator|.
name|indexOf
argument_list|(
name|fieldstr
index|[
name|i
index|]
argument_list|)
argument_list|,
name|ID
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|fieldID
return|;
block|}
comment|/** 	 * Generates the DML required to populate the entries table with jabref data.  	 * 	 * @param entries A sorted list of all entries to be exported. 	 * @param fields The fields to be specified. 	 * @param fieldstr A comma delimited string of all field names. 	 * @param out The printstream to which the DML should be written. 	 */
DECL|method|sql_popTabFD ( List<BibtexEntry> entries, ArrayList fields, String fieldstr, PrintStream out)
specifier|private
name|void
name|sql_popTabFD
parameter_list|(
name|List
argument_list|<
name|BibtexEntry
argument_list|>
name|entries
parameter_list|,
name|ArrayList
name|fields
parameter_list|,
name|String
name|fieldstr
parameter_list|,
name|PrintStream
name|out
parameter_list|)
block|{
name|String
name|sql
init|=
literal|""
decl_stmt|;
name|String
name|val
init|=
literal|""
decl_stmt|;
name|String
name|insert
init|=
literal|"INSERT INTO entries (entry_types_id, cite_key, "
operator|+
name|fieldstr
operator|+
literal|") VALUES ((SELECT entry_types_id FROM entry_types WHERE label=\""
decl_stmt|;
comment|// loop throught the entries that are to be exported
for|for
control|(
name|BibtexEntry
name|entry
range|:
name|entries
control|)
block|{
comment|// build SQL insert statement
name|sql
operator|=
name|insert
operator|+
name|entry
operator|.
name|getType
argument_list|()
operator|.
name|getName
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|+
literal|"\")"
expr_stmt|;
name|sql
operator|=
name|sql
operator|+
literal|", \""
operator|+
name|entry
operator|.
name|getCiteKey
argument_list|()
operator|+
literal|"\""
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|fields
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|sql
operator|=
name|sql
operator|+
literal|", "
expr_stmt|;
name|val
operator|=
name|entry
operator|.
name|getField
argument_list|(
operator|(
name|String
operator|)
name|fields
operator|.
name|get
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|val
operator|!=
literal|null
condition|)
block|{
name|sql
operator|=
name|sql
operator|+
literal|"\""
operator|+
name|val
operator|.
name|replaceAll
argument_list|(
literal|"\""
argument_list|,
literal|"\\\\\""
argument_list|)
operator|+
literal|"\""
expr_stmt|;
block|}
else|else
block|{
name|sql
operator|=
name|sql
operator|+
literal|"NULL"
expr_stmt|;
block|}
block|}
name|sql
operator|=
name|sql
operator|+
literal|");"
expr_stmt|;
comment|// write SQL insert to file
if|if
condition|(
name|out
operator|instanceof
name|PrintStream
condition|)
block|{
name|out
operator|.
name|println
argument_list|(
name|sql
argument_list|)
expr_stmt|;
block|}
block|}
return|return;
block|}
block|}
end_class

end_unit

