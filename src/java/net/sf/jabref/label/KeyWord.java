begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 Morten O. Alver, Nizar N. Batada  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
end_comment

begin_package
DECL|package|net.sf.jabref.label
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|label
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Hashtable
import|;
end_import

begin_class
DECL|class|KeyWord
specifier|public
class|class
name|KeyWord
block|{
DECL|method|KeyWord ()
specifier|private
name|KeyWord
parameter_list|()
block|{
comment|// puts all keywords in
name|setKeyWords
argument_list|()
expr_stmt|;
block|}
DECL|method|setKeyWords ()
specifier|private
specifier|static
name|void
name|setKeyWords
parameter_list|()
block|{
name|keyWordTable
operator|.
name|put
argument_list|(
literal|"society"
argument_list|,
literal|"society"
argument_list|)
expr_stmt|;
name|keyWordTable
operator|.
name|put
argument_list|(
literal|"transaction"
argument_list|,
literal|"transaction"
argument_list|)
expr_stmt|;
name|keyWordTable
operator|.
name|put
argument_list|(
literal|"transactions"
argument_list|,
literal|"transactions"
argument_list|)
expr_stmt|;
name|keyWordTable
operator|.
name|put
argument_list|(
literal|"journal"
argument_list|,
literal|"journal"
argument_list|)
expr_stmt|;
name|keyWordTable
operator|.
name|put
argument_list|(
literal|"review"
argument_list|,
literal|"review"
argument_list|)
expr_stmt|;
name|keyWordTable
operator|.
name|put
argument_list|(
literal|"revue"
argument_list|,
literal|"revue"
argument_list|)
expr_stmt|;
name|keyWordTable
operator|.
name|put
argument_list|(
literal|"communication"
argument_list|,
literal|"communication"
argument_list|)
expr_stmt|;
name|keyWordTable
operator|.
name|put
argument_list|(
literal|"communications"
argument_list|,
literal|"communications"
argument_list|)
expr_stmt|;
name|keyWordTable
operator|.
name|put
argument_list|(
literal|"letters"
argument_list|,
literal|"letters"
argument_list|)
expr_stmt|;
name|keyWordTable
operator|.
name|put
argument_list|(
literal|"advances"
argument_list|,
literal|"advances"
argument_list|)
expr_stmt|;
name|keyWordTable
operator|.
name|put
argument_list|(
literal|"proceedings"
argument_list|,
literal|"proceedings"
argument_list|)
expr_stmt|;
name|keyWordTable
operator|.
name|put
argument_list|(
literal|"proceeding"
argument_list|,
literal|"proceeding"
argument_list|)
expr_stmt|;
name|keyWordTable
operator|.
name|put
argument_list|(
literal|"international"
argument_list|,
literal|"international"
argument_list|)
expr_stmt|;
name|keyWordTable
operator|.
name|put
argument_list|(
literal|"joint"
argument_list|,
literal|"joint"
argument_list|)
expr_stmt|;
name|keyWordTable
operator|.
name|put
argument_list|(
literal|"conference"
argument_list|,
literal|"conference"
argument_list|)
expr_stmt|;
block|}
comment|// accessors, if users, or anyone would want to change these defaults
comment|// later
DECL|method|addKeyWord (String newKeyWord)
specifier|public
specifier|static
name|void
name|addKeyWord
parameter_list|(
name|String
name|newKeyWord
parameter_list|)
block|{
name|keyWordTable
operator|.
name|put
argument_list|(
name|newKeyWord
argument_list|,
name|newKeyWord
argument_list|)
expr_stmt|;
block|}
DECL|method|removeKeyWord (String newKeyWord)
specifier|public
specifier|static
name|String
name|removeKeyWord
parameter_list|(
name|String
name|newKeyWord
parameter_list|)
block|{
return|return
operator|(
name|String
operator|)
name|keyWordTable
operator|.
name|remove
argument_list|(
name|newKeyWord
argument_list|)
return|;
block|}
DECL|method|isKeyWord (String matchWord)
specifier|public
specifier|static
name|boolean
name|isKeyWord
parameter_list|(
name|String
name|matchWord
parameter_list|)
block|{
if|if
condition|(
name|keyWordTable
operator|.
name|size
argument_list|()
operator|==
literal|0
condition|)
block|{
name|setKeyWords
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|keyWordTable
operator|.
name|containsKey
argument_list|(
name|matchWord
operator|.
name|toLowerCase
argument_list|()
argument_list|)
condition|)
block|{
return|return
literal|true
return|;
block|}
return|return
literal|false
return|;
block|}
DECL|method|isKeyWordMatchCase (String matchWord)
specifier|public
specifier|static
name|boolean
name|isKeyWordMatchCase
parameter_list|(
name|String
name|matchWord
parameter_list|)
block|{
if|if
condition|(
name|keyWordTable
operator|.
name|size
argument_list|()
operator|==
literal|0
condition|)
block|{
name|setKeyWords
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|keyWordTable
operator|.
name|containsKey
argument_list|(
name|matchWord
argument_list|)
condition|)
block|{
return|return
literal|true
return|;
block|}
return|return
literal|false
return|;
block|}
DECL|field|keyWordTable
specifier|private
specifier|static
name|Hashtable
name|keyWordTable
init|=
operator|new
name|Hashtable
argument_list|()
decl_stmt|;
block|}
end_class

end_unit

