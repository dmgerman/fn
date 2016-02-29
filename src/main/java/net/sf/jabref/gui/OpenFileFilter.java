begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.gui
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
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
name|FilenameFilter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_class
DECL|class|OpenFileFilter
specifier|public
class|class
name|OpenFileFilter
extends|extends
name|javax
operator|.
name|swing
operator|.
name|filechooser
operator|.
name|FileFilter
implements|implements
name|FilenameFilter
block|{
DECL|field|extSet
specifier|private
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|extSet
init|=
operator|new
name|HashSet
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|desc
specifier|private
specifier|final
name|String
name|desc
decl_stmt|;
DECL|method|OpenFileFilter (String[] extensions)
specifier|public
name|OpenFileFilter
parameter_list|(
name|String
index|[]
name|extensions
parameter_list|)
block|{
name|StringBuilder
name|buf
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|int
name|numExt
init|=
name|extensions
operator|.
name|length
decl_stmt|;
if|if
condition|(
name|numExt
operator|>
literal|0
condition|)
block|{
name|buf
operator|.
name|append
argument_list|(
literal|'*'
argument_list|)
expr_stmt|;
name|buf
operator|.
name|append
argument_list|(
name|extensions
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|extSet
operator|.
name|add
argument_list|(
name|extensions
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|int
name|curExt
init|=
literal|1
init|;
name|curExt
operator|<
name|numExt
condition|;
name|curExt
operator|++
control|)
block|{
name|buf
operator|.
name|append
argument_list|(
literal|", *"
argument_list|)
expr_stmt|;
name|buf
operator|.
name|append
argument_list|(
name|extensions
index|[
name|curExt
index|]
argument_list|)
expr_stmt|;
name|extSet
operator|.
name|add
argument_list|(
name|extensions
index|[
name|curExt
index|]
argument_list|)
expr_stmt|;
block|}
name|desc
operator|=
name|buf
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
DECL|method|OpenFileFilter ()
specifier|public
name|OpenFileFilter
parameter_list|()
block|{
name|this
argument_list|(
operator|new
name|String
index|[]
block|{
literal|".bib"
block|,
literal|".dat"
block|,
comment|// silverplatter ending
literal|".txt"
block|,
comment|// windows puts ".txt" extentions and for scifinder
literal|".ris"
block|,
literal|".ref"
block|,
comment|// refer/endnote format
literal|".fcgi"
block|,
comment|// default for pubmed
literal|".bibx"
block|,
comment|// default for BibTeXML
literal|".xml"
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|OpenFileFilter (String s)
specifier|public
name|OpenFileFilter
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|this
argument_list|(
name|s
operator|.
name|split
argument_list|(
literal|"[, ]+"
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
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
return|return
name|accept
argument_list|(
name|file
operator|.
name|getName
argument_list|()
argument_list|)
return|;
block|}
DECL|method|accept (String filename)
specifier|public
name|boolean
name|accept
parameter_list|(
name|String
name|filename
parameter_list|)
block|{
name|String
name|lowerCaseFileName
init|=
name|filename
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
name|int
name|dotPos
init|=
name|lowerCaseFileName
operator|.
name|lastIndexOf
argument_list|(
literal|'.'
argument_list|)
decl_stmt|;
if|if
condition|(
name|dotPos
operator|==
operator|-
literal|1
condition|)
block|{
return|return
literal|false
return|;
block|}
name|int
name|dotDotPos
init|=
name|lowerCaseFileName
operator|.
name|lastIndexOf
argument_list|(
literal|'.'
argument_list|,
name|dotPos
operator|-
literal|1
argument_list|)
decl_stmt|;
comment|// for dot.dot extensions
return|return
name|extSet
operator|.
name|contains
argument_list|(
name|lowerCaseFileName
operator|.
name|substring
argument_list|(
name|dotPos
argument_list|)
argument_list|)
operator|||
operator|(
operator|(
name|dotDotPos
operator|>=
literal|0
operator|)
operator|&&
name|extSet
operator|.
name|contains
argument_list|(
name|lowerCaseFileName
operator|.
name|substring
argument_list|(
name|dotDotPos
argument_list|)
argument_list|)
operator|)
return|;
block|}
DECL|method|getSuffix (String filenm)
specifier|public
name|String
name|getSuffix
parameter_list|(
name|String
name|filenm
parameter_list|)
block|{
name|int
name|dotPos
decl_stmt|;
name|dotPos
operator|=
name|filenm
operator|.
name|lastIndexOf
argument_list|(
literal|'.'
argument_list|)
expr_stmt|;
if|if
condition|(
name|dotPos
operator|==
operator|-
literal|1
condition|)
block|{
return|return
literal|null
return|;
block|}
name|String
name|suffix
decl_stmt|;
name|suffix
operator|=
name|filenm
operator|.
name|substring
argument_list|(
name|dotPos
argument_list|)
expr_stmt|;
if|if
condition|(
name|extSet
operator|.
name|contains
argument_list|(
name|suffix
argument_list|)
condition|)
block|{
return|return
name|suffix
return|;
block|}
name|dotPos
operator|=
name|filenm
operator|.
name|lastIndexOf
argument_list|(
literal|'.'
argument_list|,
name|dotPos
operator|-
literal|1
argument_list|)
expr_stmt|;
comment|// for dot.dot extensions
if|if
condition|(
name|dotPos
operator|==
operator|-
literal|1
condition|)
block|{
return|return
literal|null
return|;
block|}
name|suffix
operator|=
name|filenm
operator|.
name|substring
argument_list|(
name|dotPos
argument_list|)
expr_stmt|;
if|if
condition|(
name|extSet
operator|.
name|contains
argument_list|(
name|suffix
argument_list|)
condition|)
block|{
return|return
name|suffix
return|;
block|}
return|return
literal|null
return|;
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
name|desc
return|;
block|}
annotation|@
name|Override
DECL|method|accept (File dir, String name)
specifier|public
name|boolean
name|accept
parameter_list|(
name|File
name|dir
parameter_list|,
name|String
name|name
parameter_list|)
block|{
return|return
name|accept
argument_list|(
operator|new
name|File
argument_list|(
name|dir
operator|.
name|getPath
argument_list|()
operator|+
name|name
argument_list|)
argument_list|)
return|;
block|}
block|}
end_class

end_unit

