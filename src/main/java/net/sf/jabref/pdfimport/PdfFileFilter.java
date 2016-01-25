begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General public static License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General public static License for more details.      You should have received a copy of the GNU General public static License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.pdfimport
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|pdfimport
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
name|io
operator|.
name|FileFilter
import|;
end_import

begin_class
DECL|class|PdfFileFilter
specifier|public
class|class
name|PdfFileFilter
implements|implements
name|FileFilter
block|{
DECL|field|INSTANCE
specifier|public
specifier|static
name|PdfFileFilter
name|INSTANCE
init|=
operator|new
name|PdfFileFilter
argument_list|()
decl_stmt|;
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
name|String
name|path
init|=
name|file
operator|.
name|getPath
argument_list|()
decl_stmt|;
return|return
name|isMatchingFileFilter
argument_list|(
name|path
argument_list|)
return|;
block|}
DECL|method|accept (String path)
specifier|public
name|boolean
name|accept
parameter_list|(
name|String
name|path
parameter_list|)
block|{
if|if
condition|(
operator|(
name|path
operator|==
literal|null
operator|)
operator|||
name|path
operator|.
name|isEmpty
argument_list|()
operator|||
operator|!
name|path
operator|.
name|contains
argument_list|(
literal|"."
argument_list|)
condition|)
block|{
return|return
literal|false
return|;
block|}
return|return
name|isMatchingFileFilter
argument_list|(
name|path
argument_list|)
return|;
block|}
DECL|method|isMatchingFileFilter (String path)
specifier|private
specifier|static
name|boolean
name|isMatchingFileFilter
parameter_list|(
name|String
name|path
parameter_list|)
block|{
name|String
name|extension
init|=
name|path
operator|.
name|substring
argument_list|(
name|path
operator|.
name|lastIndexOf
argument_list|(
literal|"."
argument_list|)
operator|+
literal|1
argument_list|)
decl_stmt|;
return|return
literal|"pdf"
operator|.
name|equalsIgnoreCase
argument_list|(
name|extension
argument_list|)
return|;
block|}
block|}
end_class

end_unit

