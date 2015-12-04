begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.actions
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|actions
package|;
end_package

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
name|Arrays
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
name|FileListEntry
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
name|logic
operator|.
name|FieldChange
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
name|logic
operator|.
name|cleanup
operator|.
name|Cleaner
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
name|logic
operator|.
name|util
operator|.
name|io
operator|.
name|FileUtil
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
name|database
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
name|util
operator|.
name|Util
import|;
end_import

begin_class
DECL|class|RenamePdfCleanup
specifier|public
class|class
name|RenamePdfCleanup
implements|implements
name|Cleaner
block|{
DECL|field|paths
specifier|private
specifier|final
name|String
index|[]
name|paths
decl_stmt|;
DECL|field|database
specifier|private
specifier|final
name|BibtexDatabase
name|database
decl_stmt|;
DECL|field|onlyRelativePaths
specifier|private
specifier|final
name|Boolean
name|onlyRelativePaths
decl_stmt|;
DECL|field|unsuccessfulRenames
specifier|private
name|int
name|unsuccessfulRenames
decl_stmt|;
DECL|method|RenamePdfCleanup (String[] paths, Boolean onlyRelativePaths, BibtexDatabase database)
specifier|public
name|RenamePdfCleanup
parameter_list|(
name|String
index|[]
name|paths
parameter_list|,
name|Boolean
name|onlyRelativePaths
parameter_list|,
name|BibtexDatabase
name|database
parameter_list|)
block|{
name|this
operator|.
name|paths
operator|=
name|paths
expr_stmt|;
name|this
operator|.
name|database
operator|=
name|database
expr_stmt|;
name|this
operator|.
name|onlyRelativePaths
operator|=
name|onlyRelativePaths
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|cleanup (BibtexEntry entry)
specifier|public
name|List
argument_list|<
name|FieldChange
argument_list|>
name|cleanup
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
comment|//Extract the path
name|String
name|oldValue
init|=
name|entry
operator|.
name|getField
argument_list|(
name|Globals
operator|.
name|FILE_FIELD
argument_list|)
decl_stmt|;
if|if
condition|(
name|oldValue
operator|==
literal|null
condition|)
block|{
return|return
operator|new
name|ArrayList
argument_list|<>
argument_list|()
return|;
block|}
name|FileListTableModel
name|flModel
init|=
operator|new
name|FileListTableModel
argument_list|()
decl_stmt|;
name|flModel
operator|.
name|setContent
argument_list|(
name|oldValue
argument_list|)
expr_stmt|;
if|if
condition|(
name|flModel
operator|.
name|getRowCount
argument_list|()
operator|==
literal|0
condition|)
block|{
return|return
operator|new
name|ArrayList
argument_list|<>
argument_list|()
return|;
block|}
name|boolean
name|changed
init|=
literal|false
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
name|flModel
operator|.
name|getRowCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|String
name|realOldFilename
init|=
name|flModel
operator|.
name|getEntry
argument_list|(
name|i
argument_list|)
operator|.
name|getLink
argument_list|()
decl_stmt|;
if|if
condition|(
name|onlyRelativePaths
operator|&&
operator|(
operator|new
name|File
argument_list|(
name|realOldFilename
argument_list|)
operator|.
name|isAbsolute
argument_list|()
operator|)
condition|)
block|{
continue|continue;
block|}
name|String
name|newFilename
init|=
name|Util
operator|.
name|getLinkedFileName
argument_list|(
name|database
argument_list|,
name|entry
argument_list|)
decl_stmt|;
comment|//String oldFilename = bes.getField(GUIGlobals.FILE_FIELD); // would have to be stored for undoing purposes
comment|//Add extension to newFilename
name|newFilename
operator|=
name|newFilename
operator|+
literal|"."
operator|+
name|flModel
operator|.
name|getEntry
argument_list|(
name|i
argument_list|)
operator|.
name|getType
argument_list|()
operator|.
name|getExtension
argument_list|()
expr_stmt|;
comment|//get new Filename with path
comment|//Create new Path based on old Path and new filename
name|File
name|expandedOldFile
init|=
name|FileUtil
operator|.
name|expandFilename
argument_list|(
name|realOldFilename
argument_list|,
name|paths
argument_list|)
decl_stmt|;
if|if
condition|(
name|expandedOldFile
operator|.
name|getParent
argument_list|()
operator|==
literal|null
condition|)
block|{
comment|// something went wrong. Just skip this entry
continue|continue;
block|}
name|String
name|newPath
init|=
name|expandedOldFile
operator|.
name|getParent
argument_list|()
operator|.
name|concat
argument_list|(
name|System
operator|.
name|getProperty
argument_list|(
literal|"file.separator"
argument_list|)
argument_list|)
operator|.
name|concat
argument_list|(
name|newFilename
argument_list|)
decl_stmt|;
if|if
condition|(
operator|new
name|File
argument_list|(
name|newPath
argument_list|)
operator|.
name|exists
argument_list|()
condition|)
block|{
comment|// we do not overwrite files
comment|// TODO: we could check here if the newPath file is linked with the current entry. And if not, we could add a link
continue|continue;
block|}
comment|//do rename
name|boolean
name|renameSuccessful
init|=
name|FileUtil
operator|.
name|renameFile
argument_list|(
name|expandedOldFile
operator|.
name|toString
argument_list|()
argument_list|,
name|newPath
argument_list|)
decl_stmt|;
if|if
condition|(
name|renameSuccessful
condition|)
block|{
name|changed
operator|=
literal|true
expr_stmt|;
comment|//Change the path for this entry
name|String
name|description
init|=
name|flModel
operator|.
name|getEntry
argument_list|(
name|i
argument_list|)
operator|.
name|getDescription
argument_list|()
decl_stmt|;
name|ExternalFileType
name|type
init|=
name|flModel
operator|.
name|getEntry
argument_list|(
name|i
argument_list|)
operator|.
name|getType
argument_list|()
decl_stmt|;
name|flModel
operator|.
name|removeEntry
argument_list|(
name|i
argument_list|)
expr_stmt|;
comment|// we cannot use "newPath" to generate a FileListEntry as newPath is absolute, but we want to keep relative paths whenever possible
name|File
name|parent
init|=
operator|(
operator|new
name|File
argument_list|(
name|realOldFilename
argument_list|)
operator|)
operator|.
name|getParentFile
argument_list|()
decl_stmt|;
name|String
name|newFileEntryFileName
decl_stmt|;
if|if
condition|(
name|parent
operator|==
literal|null
condition|)
block|{
name|newFileEntryFileName
operator|=
name|newFilename
expr_stmt|;
block|}
else|else
block|{
name|newFileEntryFileName
operator|=
name|parent
operator|.
name|toString
argument_list|()
operator|.
name|concat
argument_list|(
name|System
operator|.
name|getProperty
argument_list|(
literal|"file.separator"
argument_list|)
argument_list|)
operator|.
name|concat
argument_list|(
name|newFilename
argument_list|)
expr_stmt|;
block|}
name|flModel
operator|.
name|addEntry
argument_list|(
name|i
argument_list|,
operator|new
name|FileListEntry
argument_list|(
name|description
argument_list|,
name|newFileEntryFileName
argument_list|,
name|type
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|unsuccessfulRenames
operator|++
expr_stmt|;
block|}
block|}
if|if
condition|(
name|changed
condition|)
block|{
name|String
name|newValue
init|=
name|flModel
operator|.
name|getStringRepresentation
argument_list|()
decl_stmt|;
assert|assert
operator|(
operator|!
name|oldValue
operator|.
name|equals
argument_list|(
name|newValue
argument_list|)
operator|)
assert|;
name|entry
operator|.
name|setField
argument_list|(
name|Globals
operator|.
name|FILE_FIELD
argument_list|,
name|newValue
argument_list|)
expr_stmt|;
comment|//we put an undo of the field content here
comment|//the file is not being renamed back, which leads to inconsistencies
comment|//if we put a null undo object here, the change by "doMakePathsRelative" would overwrite the field value nevertheless.
name|FieldChange
name|change
init|=
operator|new
name|FieldChange
argument_list|(
name|entry
argument_list|,
name|Globals
operator|.
name|FILE_FIELD
argument_list|,
name|oldValue
argument_list|,
name|newValue
argument_list|)
decl_stmt|;
return|return
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|FieldChange
index|[]
block|{
name|change
block|}
argument_list|)
return|;
block|}
return|return
operator|new
name|ArrayList
argument_list|<>
argument_list|()
return|;
block|}
DECL|method|getUnsuccessfulRenames ()
specifier|public
name|int
name|getUnsuccessfulRenames
parameter_list|()
block|{
return|return
name|unsuccessfulRenames
return|;
block|}
block|}
end_class

end_unit

