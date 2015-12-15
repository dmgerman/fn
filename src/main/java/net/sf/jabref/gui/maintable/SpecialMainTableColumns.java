begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.maintable
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|maintable
package|;
end_package

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
name|external
operator|.
name|ExternalFileType
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
name|gui
operator|.
name|FileListTableModel
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
name|gui
operator|.
name|GUIGlobals
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
name|gui
operator|.
name|IconTheme
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
name|model
operator|.
name|entry
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
name|model
operator|.
name|entry
operator|.
name|EntryUtil
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
name|specialfields
operator|.
name|*
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|*
import|;
end_import

begin_class
DECL|class|SpecialMainTableColumns
specifier|public
class|class
name|SpecialMainTableColumns
block|{
DECL|field|NUMBER_COL
specifier|public
specifier|static
specifier|final
name|MainTableColumn
name|NUMBER_COL
init|=
operator|new
name|MainTableColumn
argument_list|(
name|GUIGlobals
operator|.
name|NUMBER_COL
argument_list|)
block|{
annotation|@
name|Override
specifier|public
name|Object
name|getColumnValue
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
return|return
literal|"#"
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getDisplayName
parameter_list|()
block|{
return|return
literal|"#"
return|;
block|}
block|}
decl_stmt|;
DECL|field|RANKING_COLUMN
specifier|public
specifier|static
specifier|final
name|MainTableColumn
name|RANKING_COLUMN
init|=
operator|new
name|MainTableColumn
argument_list|(
name|SpecialFieldsUtils
operator|.
name|FIELDNAME_RANKING
argument_list|,
operator|new
name|String
index|[]
block|{
name|SpecialFieldsUtils
operator|.
name|FIELDNAME_RANKING
block|}
argument_list|,
operator|new
name|JLabel
argument_list|(
name|EntryUtil
operator|.
name|capitalizeFirst
argument_list|(
name|SpecialFieldsUtils
operator|.
name|FIELDNAME_RANKING
argument_list|)
argument_list|)
argument_list|)
block|{
annotation|@
name|Override
specifier|public
name|Object
name|getColumnValue
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
name|SpecialFieldValue
name|rank
init|=
name|Rank
operator|.
name|getInstance
argument_list|()
operator|.
name|parse
argument_list|(
name|entry
operator|.
name|getField
argument_list|(
name|SpecialFieldsUtils
operator|.
name|FIELDNAME_RANKING
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|rank
operator|!=
literal|null
condition|)
block|{
return|return
name|rank
operator|.
name|createLabel
argument_list|()
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
block|}
decl_stmt|;
DECL|field|PRIORITY_COLUMN
specifier|public
specifier|static
specifier|final
name|MainTableColumn
name|PRIORITY_COLUMN
init|=
operator|new
name|MainTableColumn
argument_list|(
name|SpecialFieldsUtils
operator|.
name|FIELDNAME_PRIORITY
argument_list|,
operator|new
name|String
index|[]
block|{
name|SpecialFieldsUtils
operator|.
name|FIELDNAME_PRIORITY
block|}
argument_list|,
operator|new
name|JLabel
argument_list|(
name|Priority
operator|.
name|getInstance
argument_list|()
operator|.
name|getRepresentingIcon
argument_list|()
argument_list|)
argument_list|)
block|{
annotation|@
name|Override
specifier|public
name|Object
name|getColumnValue
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
name|SpecialFieldValue
name|prio
init|=
name|Priority
operator|.
name|getInstance
argument_list|()
operator|.
name|parse
argument_list|(
name|entry
operator|.
name|getField
argument_list|(
name|SpecialFieldsUtils
operator|.
name|FIELDNAME_PRIORITY
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|prio
operator|!=
literal|null
condition|)
block|{
return|return
name|prio
operator|.
name|createLabel
argument_list|()
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
block|}
decl_stmt|;
DECL|field|READ_STATUS_COLUMN
specifier|public
specifier|static
specifier|final
name|MainTableColumn
name|READ_STATUS_COLUMN
init|=
operator|new
name|MainTableColumn
argument_list|(
name|SpecialFieldsUtils
operator|.
name|FIELDNAME_READ
argument_list|,
operator|new
name|String
index|[]
block|{
name|SpecialFieldsUtils
operator|.
name|FIELDNAME_READ
block|}
argument_list|,
operator|new
name|JLabel
argument_list|(
name|ReadStatus
operator|.
name|getInstance
argument_list|()
operator|.
name|getRepresentingIcon
argument_list|()
argument_list|)
argument_list|)
block|{
annotation|@
name|Override
specifier|public
name|Object
name|getColumnValue
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
name|SpecialFieldValue
name|status
init|=
name|ReadStatus
operator|.
name|getInstance
argument_list|()
operator|.
name|parse
argument_list|(
name|entry
operator|.
name|getField
argument_list|(
name|SpecialFieldsUtils
operator|.
name|FIELDNAME_READ
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|status
operator|!=
literal|null
condition|)
block|{
return|return
name|status
operator|.
name|createLabel
argument_list|()
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
block|}
decl_stmt|;
DECL|field|RELEVANCE_COLUMN
specifier|public
specifier|static
specifier|final
name|MainTableColumn
name|RELEVANCE_COLUMN
init|=
name|createIconColumn
argument_list|(
name|SpecialFieldsUtils
operator|.
name|FIELDNAME_RELEVANCE
argument_list|,
operator|new
name|String
index|[]
block|{
name|SpecialFieldsUtils
operator|.
name|FIELDNAME_RELEVANCE
block|}
argument_list|,
operator|new
name|JLabel
argument_list|(
name|Relevance
operator|.
name|getInstance
argument_list|()
operator|.
name|getRepresentingIcon
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|PRINTED_COLUMN
specifier|public
specifier|static
specifier|final
name|MainTableColumn
name|PRINTED_COLUMN
init|=
name|createIconColumn
argument_list|(
name|SpecialFieldsUtils
operator|.
name|FIELDNAME_PRINTED
argument_list|,
operator|new
name|String
index|[]
block|{
name|SpecialFieldsUtils
operator|.
name|FIELDNAME_PRINTED
block|}
argument_list|,
operator|new
name|JLabel
argument_list|(
name|Printed
operator|.
name|getInstance
argument_list|()
operator|.
name|getRepresentingIcon
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|QUALITY_COLUMN
specifier|public
specifier|static
specifier|final
name|MainTableColumn
name|QUALITY_COLUMN
init|=
name|createIconColumn
argument_list|(
name|SpecialFieldsUtils
operator|.
name|FIELDNAME_QUALITY
argument_list|,
operator|new
name|String
index|[]
block|{
name|SpecialFieldsUtils
operator|.
name|FIELDNAME_QUALITY
block|}
argument_list|,
operator|new
name|JLabel
argument_list|(
name|Quality
operator|.
name|getInstance
argument_list|()
operator|.
name|getRepresentingIcon
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|FILE_COLUMN
specifier|public
specifier|static
specifier|final
name|MainTableColumn
name|FILE_COLUMN
init|=
operator|new
name|MainTableColumn
argument_list|(
name|Globals
operator|.
name|FILE_FIELD
argument_list|,
operator|new
name|String
index|[]
block|{
name|Globals
operator|.
name|FILE_FIELD
block|}
argument_list|,
operator|new
name|JLabel
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|FILE
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
argument_list|)
block|{
annotation|@
name|Override
specifier|public
name|Object
name|getColumnValue
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
comment|// We use a FileListTableModel to parse the field content:
name|FileListTableModel
name|fileList
init|=
operator|new
name|FileListTableModel
argument_list|()
decl_stmt|;
name|fileList
operator|.
name|setContent
argument_list|(
name|entry
operator|.
name|getField
argument_list|(
name|Globals
operator|.
name|FILE_FIELD
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|fileList
operator|.
name|getRowCount
argument_list|()
operator|>
literal|1
condition|)
block|{
return|return
operator|new
name|JLabel
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|FILE_MULTIPLE
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|fileList
operator|.
name|getRowCount
argument_list|()
operator|==
literal|1
condition|)
block|{
name|ExternalFileType
name|type
init|=
name|fileList
operator|.
name|getEntry
argument_list|(
literal|0
argument_list|)
operator|.
name|getType
argument_list|()
decl_stmt|;
if|if
condition|(
name|type
operator|!=
literal|null
condition|)
block|{
return|return
name|type
operator|.
name|getIconLabel
argument_list|()
return|;
block|}
block|}
return|return
literal|null
return|;
block|}
block|}
decl_stmt|;
comment|/**      * Creates a MainTableColumn which shows an icon instead textual content      *      * @param columnName the name of the column      * @param fields     the entry fields which should be shown      * @return the crated MainTableColumn      */
DECL|method|createIconColumn (String columnName, String[] fields, JLabel iconLabel)
specifier|public
specifier|static
name|MainTableColumn
name|createIconColumn
parameter_list|(
name|String
name|columnName
parameter_list|,
name|String
index|[]
name|fields
parameter_list|,
name|JLabel
name|iconLabel
parameter_list|)
block|{
return|return
operator|new
name|MainTableColumn
argument_list|(
name|columnName
argument_list|,
name|fields
argument_list|,
name|iconLabel
argument_list|)
block|{
annotation|@
name|Override
specifier|public
name|Object
name|getColumnValue
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
name|JLabel
name|iconLabel
init|=
literal|null
decl_stmt|;
name|boolean
name|iconFound
init|=
literal|false
decl_stmt|;
comment|// check for each field whether content is available
for|for
control|(
name|String
name|field
range|:
name|fields
control|)
block|{
if|if
condition|(
name|entry
operator|.
name|getField
argument_list|(
name|field
argument_list|)
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|iconFound
condition|)
block|{
return|return
operator|new
name|JLabel
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|FILE_MULTIPLE
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
return|;
block|}
else|else
block|{
name|iconLabel
operator|=
name|GUIGlobals
operator|.
name|getTableIcon
argument_list|(
name|field
argument_list|)
expr_stmt|;
name|iconFound
operator|=
literal|true
expr_stmt|;
block|}
block|}
block|}
return|return
name|iconLabel
return|;
block|}
block|}
return|;
block|}
comment|/**      * create a MainTableColumn for specific file types.      *      * Shows the icon for the given type (or the FILE_MULTIPLE icon)      *      * @param externalFileTypeName the name of the externalFileType      *      * @return the created MainTableColumn      */
DECL|method|createFileIconColumn (String externalFileTypeName)
specifier|public
specifier|static
name|MainTableColumn
name|createFileIconColumn
parameter_list|(
name|String
name|externalFileTypeName
parameter_list|)
block|{
return|return
operator|new
name|MainTableColumn
argument_list|(
name|externalFileTypeName
argument_list|,
operator|new
name|String
index|[]
block|{
name|Globals
operator|.
name|FILE_FIELD
block|}
argument_list|,
operator|new
name|JLabel
argument_list|()
argument_list|)
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|isFileFilter
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getDisplayName
parameter_list|()
block|{
return|return
name|externalFileTypeName
return|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|getColumnValue
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
name|boolean
name|iconFound
init|=
literal|false
decl_stmt|;
name|JLabel
name|iconLabel
init|=
literal|null
decl_stmt|;
name|FileListTableModel
name|fileList
init|=
operator|new
name|FileListTableModel
argument_list|()
decl_stmt|;
name|fileList
operator|.
name|setContent
argument_list|(
name|entry
operator|.
name|getField
argument_list|(
name|Globals
operator|.
name|FILE_FIELD
argument_list|)
argument_list|)
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
name|fileList
operator|.
name|getRowCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|fileList
operator|.
name|getEntry
argument_list|(
name|i
argument_list|)
operator|.
name|getType
argument_list|()
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|externalFileTypeName
operator|.
name|equalsIgnoreCase
argument_list|(
name|fileList
operator|.
name|getEntry
argument_list|(
name|i
argument_list|)
operator|.
name|getType
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
name|iconFound
condition|)
block|{
comment|// already found another file of the desired type - show FILE_MULTIPLE Icon
return|return
operator|new
name|JLabel
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|FILE_MULTIPLE
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
return|;
block|}
else|else
block|{
name|iconLabel
operator|=
name|fileList
operator|.
name|getEntry
argument_list|(
name|i
argument_list|)
operator|.
name|getType
argument_list|()
operator|.
name|getIconLabel
argument_list|()
expr_stmt|;
name|iconFound
operator|=
literal|true
expr_stmt|;
block|}
block|}
block|}
block|}
return|return
name|iconLabel
return|;
block|}
block|}
return|;
block|}
block|}
end_class

end_unit

