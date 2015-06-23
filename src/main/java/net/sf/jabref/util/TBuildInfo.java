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
DECL|package|net.sf.jabref.util
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|BufferedReader
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
name|io
operator|.
name|InputStreamReader
import|;
end_import

begin_class
DECL|class|TBuildInfo
specifier|public
class|class
name|TBuildInfo
block|{
DECL|field|BUILD_DATE
specifier|private
name|String
name|BUILD_DATE
init|=
literal|""
decl_stmt|;
DECL|field|BUILD_VERSION
specifier|private
name|String
name|BUILD_VERSION
init|=
literal|"devel - 1st edition family"
decl_stmt|;
DECL|field|BUILD_NUMBER
specifier|private
name|String
name|BUILD_NUMBER
init|=
literal|"1"
decl_stmt|;
comment|//  private TBuilderInfo runtime = new TBuildInfo() ;
DECL|method|TBuildInfo (String path)
specifier|public
name|TBuildInfo
parameter_list|(
name|String
name|path
parameter_list|)
block|{
name|readBuildVersionData
argument_list|(
name|path
argument_list|)
expr_stmt|;
block|}
comment|// --------------------------------------------------------------------------
comment|// some informations from extern build file
DECL|method|readBuildVersionData (String path)
specifier|private
name|void
name|readBuildVersionData
parameter_list|(
name|String
name|path
parameter_list|)
block|{
name|String
name|buf
init|=
literal|null
decl_stmt|;
name|int
name|sep
init|=
literal|0
decl_stmt|;
name|String
name|Key
decl_stmt|,
name|Value
decl_stmt|;
name|BufferedReader
name|input
init|=
literal|null
decl_stmt|;
try|try
block|{
name|input
operator|=
operator|new
name|BufferedReader
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
name|getClass
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
name|path
argument_list|)
argument_list|)
argument_list|,
literal|100
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e1
parameter_list|)
block|{
comment|//      System.out.println( e1 ) ;
comment|//      e1.printStackTrace();
comment|//      Logger.global.info( e1.getMessage() ) ;
return|return;
block|}
try|try
block|{
while|while
condition|(
operator|(
name|buf
operator|=
name|input
operator|.
name|readLine
argument_list|()
operator|)
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
operator|!
name|buf
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// no empty lines
if|if
condition|(
name|buf
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|!=
literal|'#'
condition|)
block|{
comment|// data line, comments - first char = #
name|sep
operator|=
name|buf
operator|.
name|indexOf
argument_list|(
literal|'='
argument_list|)
expr_stmt|;
if|if
condition|(
name|sep
operator|>
literal|0
condition|)
block|{
comment|// = found
name|Key
operator|=
name|buf
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|sep
argument_list|)
expr_stmt|;
name|Value
operator|=
name|buf
operator|.
name|substring
argument_list|(
name|sep
operator|+
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
name|Key
operator|.
name|equals
argument_list|(
literal|"builddate"
argument_list|)
condition|)
block|{
name|BUILD_DATE
operator|=
name|Value
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|Key
operator|.
name|equals
argument_list|(
literal|"build"
argument_list|)
condition|)
block|{
name|BUILD_NUMBER
operator|=
name|Value
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|Key
operator|.
name|equals
argument_list|(
literal|"version"
argument_list|)
condition|)
block|{
name|BUILD_VERSION
operator|=
name|Value
expr_stmt|;
block|}
block|}
block|}
comment|// data line
block|}
block|}
comment|// while
block|}
catch|catch
parameter_list|(
name|IOException
name|iex
parameter_list|)
block|{
comment|//      System.err.println(iex.getMessage());
comment|//      Logger.global.info( iex.getMessage() ) ;
block|}
try|try
block|{
name|input
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
comment|//      System.out.println(e.getMessage());
comment|//      Logger.global.info( e.getMessage() ) ;
block|}
block|}
comment|// --------------------------------------------------------------------------
DECL|method|getBUILD_DATE ()
specifier|public
name|String
name|getBUILD_DATE
parameter_list|()
block|{
return|return
name|BUILD_DATE
return|;
block|}
DECL|method|getBUILD_VERSION ()
specifier|public
name|String
name|getBUILD_VERSION
parameter_list|()
block|{
return|return
name|BUILD_VERSION
return|;
block|}
DECL|method|getBUILD_NUMBER ()
specifier|public
name|String
name|getBUILD_NUMBER
parameter_list|()
block|{
return|return
name|BUILD_NUMBER
return|;
block|}
comment|// --------------------------------------------------------------------------
block|}
end_class

end_unit

