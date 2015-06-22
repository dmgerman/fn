begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.imports
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
package|;
end_package

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|filechooser
operator|.
name|FileFilter
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

begin_comment
comment|/**  * File filter that lets the user choose export format while choosing file to  * export to. Contains a reference to the ExportFormat in question.  */
end_comment

begin_class
DECL|class|ImportFileFilter
specifier|public
class|class
name|ImportFileFilter
extends|extends
name|FileFilter
implements|implements
name|Comparable
argument_list|<
name|ImportFileFilter
argument_list|>
block|{
DECL|field|format
specifier|private
specifier|final
name|ImportFormat
name|format
decl_stmt|;
DECL|field|name
specifier|private
specifier|final
name|String
name|name
decl_stmt|;
DECL|method|ImportFileFilter (ImportFormat format)
specifier|public
name|ImportFileFilter
parameter_list|(
name|ImportFormat
name|format
parameter_list|)
block|{
name|this
operator|.
name|format
operator|=
name|format
expr_stmt|;
name|this
operator|.
name|name
operator|=
name|format
operator|.
name|getFormatName
argument_list|()
expr_stmt|;
block|}
DECL|method|getImportFormat ()
specifier|public
name|ImportFormat
name|getImportFormat
parameter_list|()
block|{
return|return
name|format
return|;
block|}
DECL|method|accept (File file)
specifier|public
name|boolean
name|accept
parameter_list|(
name|File
name|file
parameter_list|)
block|{
return|return
literal|true
return|;
comment|/*if (file.isDirectory())             return true;         else             return file.getPath().toLowerCase().endsWith(extension);*/
block|}
DECL|method|getDescription ()
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|name
return|;
block|}
DECL|method|compareTo (ImportFileFilter o)
specifier|public
name|int
name|compareTo
parameter_list|(
name|ImportFileFilter
name|o
parameter_list|)
block|{
return|return
name|name
operator|.
name|compareTo
argument_list|(
name|o
operator|.
name|name
argument_list|)
return|;
block|}
block|}
end_class

end_unit

