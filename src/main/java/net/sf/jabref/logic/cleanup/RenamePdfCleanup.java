begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.     This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.     You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.cleanup
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|cleanup
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
name|Collections
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
name|util
operator|.
name|Optional
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
name|BibDatabase
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
name|BibEntry
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
name|FileField
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
name|CleanupJob
block|{
DECL|field|paths
specifier|private
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|paths
decl_stmt|;
DECL|field|database
specifier|private
specifier|final
name|BibDatabase
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
DECL|method|RenamePdfCleanup (List<String> paths, Boolean onlyRelativePaths, BibDatabase database)
specifier|public
name|RenamePdfCleanup
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|paths
parameter_list|,
name|Boolean
name|onlyRelativePaths
parameter_list|,
name|BibDatabase
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
DECL|method|cleanup (BibEntry entry)
specifier|public
name|List
argument_list|<
name|FieldChange
argument_list|>
name|cleanup
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
comment|//Extract the path
name|Optional
argument_list|<
name|String
argument_list|>
name|oldValue
init|=
name|entry
operator|.
name|getFieldOptional
argument_list|(
name|Globals
operator|.
name|FILE_FIELD
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|oldValue
operator|.
name|isPresent
argument_list|()
condition|)
block|{
return|return
operator|new
name|ArrayList
argument_list|<>
argument_list|()
return|;
block|}
name|List
argument_list|<
name|FileField
operator|.
name|ParsedFileField
argument_list|>
name|fileList
init|=
name|FileField
operator|.
name|parse
argument_list|(
name|oldValue
operator|.
name|get
argument_list|()
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|FileField
operator|.
name|ParsedFileField
argument_list|>
name|newFileList
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|boolean
name|changed
init|=
literal|false
decl_stmt|;
for|for
control|(
name|FileField
operator|.
name|ParsedFileField
name|flEntry
range|:
name|fileList
control|)
block|{
name|String
name|realOldFilename
init|=
name|flEntry
operator|.
name|link
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
name|StringBuilder
name|newFilename
init|=
operator|new
name|StringBuilder
argument_list|(
name|Util
operator|.
name|getLinkedFileName
argument_list|(
name|database
argument_list|,
name|entry
argument_list|)
argument_list|)
decl_stmt|;
comment|//String oldFilename = bes.getField(GUIGlobals.FILE_FIELD); // would have to be stored for undoing purposes
comment|//Add extension to newFilename
name|newFilename
operator|.
name|append
argument_list|(
literal|"."
argument_list|)
expr_stmt|;
name|newFilename
operator|.
name|append
argument_list|(
name|FileUtil
operator|.
name|getFileExtension
argument_list|(
name|realOldFilename
argument_list|)
operator|.
name|orElse
argument_list|(
literal|"pdf"
argument_list|)
argument_list|)
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
operator|(
name|expandedOldFile
operator|==
literal|null
operator|)
operator|||
operator|(
name|expandedOldFile
operator|.
name|getParent
argument_list|()
operator|==
literal|null
operator|)
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
operator|.
name|toString
argument_list|()
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
name|flEntry
operator|.
name|description
decl_stmt|;
name|String
name|type
init|=
name|flEntry
operator|.
name|fileType
decl_stmt|;
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
operator|||
name|paths
operator|.
name|contains
argument_list|(
name|parent
operator|.
name|getAbsolutePath
argument_list|()
argument_list|)
condition|)
block|{
name|newFileEntryFileName
operator|=
name|newFilename
operator|.
name|toString
argument_list|()
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
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|newFileList
operator|.
name|add
argument_list|(
operator|new
name|FileField
operator|.
name|ParsedFileField
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
name|FileField
operator|.
name|getStringRepresentation
argument_list|(
name|newFileList
argument_list|)
decl_stmt|;
assert|assert
operator|(
operator|!
name|oldValue
operator|.
name|get
argument_list|()
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
operator|.
name|get
argument_list|()
argument_list|,
name|newValue
argument_list|)
decl_stmt|;
return|return
name|Collections
operator|.
name|singletonList
argument_list|(
name|change
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

