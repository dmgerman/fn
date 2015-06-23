begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

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
DECL|class|ExportFileFilter
class|class
name|ExportFileFilter
extends|extends
name|FileFilter
implements|implements
name|Comparable
argument_list|<
name|ExportFileFilter
argument_list|>
block|{
DECL|field|format
specifier|private
specifier|final
name|IExportFormat
name|format
decl_stmt|;
DECL|field|extension
specifier|private
specifier|final
name|String
name|extension
decl_stmt|;
DECL|field|name
specifier|private
specifier|final
name|String
name|name
decl_stmt|;
DECL|method|ExportFileFilter (IExportFormat format, String extension)
specifier|public
name|ExportFileFilter
parameter_list|(
name|IExportFormat
name|format
parameter_list|,
name|String
name|extension
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
name|extension
operator|=
name|extension
expr_stmt|;
name|this
operator|.
name|name
operator|=
name|format
operator|.
name|getDisplayName
argument_list|()
operator|+
literal|" (*"
operator|+
name|extension
operator|+
literal|")"
expr_stmt|;
block|}
DECL|method|getExportFormat ()
specifier|public
name|IExportFormat
name|getExportFormat
parameter_list|()
block|{
return|return
name|format
return|;
block|}
DECL|method|getExtension ()
specifier|public
name|String
name|getExtension
parameter_list|()
block|{
return|return
name|extension
return|;
block|}
annotation|@
name|Override
DECL|method|accept (File file)
specifier|public
name|boolean
name|accept
parameter_list|(
name|File
name|file
parameter_list|)
block|{
if|if
condition|(
name|file
operator|.
name|isDirectory
argument_list|()
condition|)
block|{
return|return
literal|true
return|;
block|}
else|else
block|{
return|return
name|file
operator|.
name|getPath
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|.
name|endsWith
argument_list|(
name|extension
argument_list|)
return|;
block|}
block|}
annotation|@
name|Override
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
annotation|@
name|Override
DECL|method|compareTo (ExportFileFilter o)
specifier|public
name|int
name|compareTo
parameter_list|(
name|ExportFileFilter
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

