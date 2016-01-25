begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.exporter.layout.format
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|exporter
operator|.
name|layout
operator|.
name|format
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
name|exporter
operator|.
name|layout
operator|.
name|ParamLayoutFormatter
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
name|model
operator|.
name|entry
operator|.
name|FileField
operator|.
name|ParsedFileField
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
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

begin_comment
comment|/**  * Export formatter that handles the file link list of JabRef 2.3 and later, by  * selecting the first file link, if any, specified by the field.  */
end_comment

begin_class
DECL|class|FileLink
specifier|public
class|class
name|FileLink
implements|implements
name|ParamLayoutFormatter
block|{
DECL|field|fileType
specifier|private
name|String
name|fileType
decl_stmt|;
annotation|@
name|Override
DECL|method|format (String field)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|field
parameter_list|)
block|{
if|if
condition|(
name|field
operator|==
literal|null
condition|)
block|{
return|return
literal|""
return|;
block|}
name|List
argument_list|<
name|ParsedFileField
argument_list|>
name|fileList
init|=
name|FileField
operator|.
name|parse
argument_list|(
name|field
argument_list|)
decl_stmt|;
name|String
name|link
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|fileType
operator|==
literal|null
condition|)
block|{
comment|// No file type specified. Simply take the first link.
if|if
condition|(
operator|!
operator|(
name|fileList
operator|.
name|isEmpty
argument_list|()
operator|)
condition|)
block|{
name|link
operator|=
name|fileList
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|link
expr_stmt|;
block|}
block|}
else|else
block|{
comment|// A file type is specified:
for|for
control|(
name|ParsedFileField
name|flEntry
range|:
name|fileList
control|)
block|{
if|if
condition|(
name|flEntry
operator|.
name|fileType
operator|.
name|equalsIgnoreCase
argument_list|(
name|fileType
argument_list|)
condition|)
block|{
name|link
operator|=
name|flEntry
operator|.
name|link
expr_stmt|;
break|break;
block|}
block|}
block|}
if|if
condition|(
name|link
operator|==
literal|null
condition|)
block|{
return|return
literal|""
return|;
block|}
name|String
index|[]
name|dirs
decl_stmt|;
comment|// We need to resolve the file directory from the database's metadata,
comment|// but that is not available from a formatter. Therefore, as an
comment|// ugly hack, the export routine has set a global variable before
comment|// starting the export, which contains the database's file directory:
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|fileDirForDatabase
operator|!=
literal|null
condition|)
block|{
name|dirs
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|fileDirForDatabase
expr_stmt|;
block|}
else|else
block|{
name|dirs
operator|=
operator|new
name|String
index|[]
block|{
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|Globals
operator|.
name|FILE_FIELD
operator|+
name|Globals
operator|.
name|DIR_SUFFIX
argument_list|)
block|}
expr_stmt|;
block|}
name|File
name|f
init|=
name|FileUtil
operator|.
name|expandFilename
argument_list|(
name|link
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
name|dirs
argument_list|)
argument_list|)
decl_stmt|;
comment|/*          * Stumbled over this while investigating          *          * https://sourceforge.net/tracker/index.php?func=detail&aid=1469903&group_id=92314&atid=600306          */
if|if
condition|(
name|f
operator|!=
literal|null
condition|)
block|{
try|try
block|{
return|return
name|f
operator|.
name|getCanonicalPath
argument_list|()
return|;
comment|//f.toURI().toString();
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
return|return
name|f
operator|.
name|getPath
argument_list|()
return|;
block|}
block|}
else|else
block|{
return|return
name|link
return|;
block|}
block|}
comment|/**      * This method is called if the layout file specifies an argument for this      * formatter. We use it as an indicator of which file type we should look for.      * @param arg The file type.      */
annotation|@
name|Override
DECL|method|setArgument (String arg)
specifier|public
name|void
name|setArgument
parameter_list|(
name|String
name|arg
parameter_list|)
block|{
name|fileType
operator|=
name|arg
expr_stmt|;
block|}
block|}
end_class

end_unit

