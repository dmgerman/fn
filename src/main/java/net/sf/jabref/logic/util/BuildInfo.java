begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2005 R. Nagel   All programs in this directory and  subdirectories are published under the GNU General Public License as  described below.   This program is free software; you can redistribute it and/or modify  it under the terms of the GNU General Public License as published by  the Free Software Foundation; either version 2 of the License, or (at  your option) any later version.   This program is distributed in the hope that it will be useful, but  WITHOUT ANY WARRANTY; without even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU  General Public License for more details.   You should have received a copy of the GNU General Public License  along with this program; if not, write to the Free Software  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA   Further information about the GNU GPL is available at:  http://www.gnu.org/copyleft/gpl.ja.html   */
end_comment

begin_comment
comment|// created by : r.nagel 01.06.2005
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|// function : read build informations from build.properies file
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|// modified:
end_comment

begin_comment
comment|//
end_comment

begin_package
DECL|package|net.sf.jabref.logic.util
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
package|;
end_package

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
name|Properties
import|;
end_import

begin_class
DECL|class|BuildInfo
specifier|public
class|class
name|BuildInfo
block|{
DECL|field|version
specifier|private
specifier|final
name|String
name|version
decl_stmt|;
DECL|method|BuildInfo ()
specifier|public
name|BuildInfo
parameter_list|()
block|{
name|this
argument_list|(
literal|"/resource/build.properties"
argument_list|)
expr_stmt|;
block|}
DECL|method|BuildInfo (String path)
specifier|public
name|BuildInfo
parameter_list|(
name|String
name|path
parameter_list|)
block|{
name|Properties
name|properties
init|=
operator|new
name|Properties
argument_list|()
decl_stmt|;
try|try
block|{
name|properties
operator|.
name|load
argument_list|(
name|getClass
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
name|path
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
decl||
name|NullPointerException
name|ignored
parameter_list|)
block|{         }
name|version
operator|=
name|properties
operator|.
name|getProperty
argument_list|(
literal|"version"
argument_list|,
literal|"dev"
argument_list|)
expr_stmt|;
block|}
DECL|method|getVersion ()
specifier|public
name|String
name|getVersion
parameter_list|()
block|{
return|return
name|version
return|;
block|}
block|}
end_class

end_unit

