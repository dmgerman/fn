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

begin_class
DECL|class|RelativePathsCleanup
specifier|public
class|class
name|RelativePathsCleanup
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
DECL|method|RelativePathsCleanup (List<String> paths)
specifier|public
name|RelativePathsCleanup
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|paths
parameter_list|)
block|{
name|this
operator|.
name|paths
operator|=
name|paths
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
name|oldFileName
init|=
name|flEntry
operator|.
name|link
decl_stmt|;
name|String
name|newFileName
init|=
name|FileUtil
operator|.
name|shortenFileName
argument_list|(
operator|new
name|File
argument_list|(
name|oldFileName
argument_list|)
argument_list|,
name|paths
argument_list|)
operator|.
name|toString
argument_list|()
decl_stmt|;
name|FileField
operator|.
name|ParsedFileField
name|newFlEntry
init|=
name|flEntry
decl_stmt|;
if|if
condition|(
operator|!
name|oldFileName
operator|.
name|equals
argument_list|(
name|newFileName
argument_list|)
condition|)
block|{
name|newFlEntry
operator|=
operator|new
name|FileField
operator|.
name|ParsedFileField
argument_list|(
name|flEntry
operator|.
name|description
argument_list|,
name|newFileName
argument_list|,
name|flEntry
operator|.
name|fileType
argument_list|)
expr_stmt|;
name|changed
operator|=
literal|true
expr_stmt|;
block|}
name|newFileList
operator|.
name|add
argument_list|(
name|newFlEntry
argument_list|)
expr_stmt|;
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
block|}
end_class

end_unit

