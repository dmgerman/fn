begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright (C) 2003-2016 JabRef contributors.  * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *  * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *  * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.groups
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|groups
package|;
end_package

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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
operator|.
name|fileformat
operator|.
name|ParseException
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
name|l10n
operator|.
name|Localization
import|;
end_import

begin_comment
comment|/**  * Converts string representation of groups to a parsed {@link GroupTreeNode}.  */
end_comment

begin_class
DECL|class|GroupsParser
specifier|public
class|class
name|GroupsParser
block|{
DECL|method|importGroups (List<String> orderedData)
specifier|public
specifier|static
name|GroupTreeNode
name|importGroups
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|orderedData
parameter_list|)
throws|throws
name|ParseException
block|{
name|GroupTreeNode
name|cursor
init|=
literal|null
decl_stmt|;
name|GroupTreeNode
name|root
init|=
literal|null
decl_stmt|;
for|for
control|(
name|String
name|string
range|:
name|orderedData
control|)
block|{
comment|// This allows to read databases that have been modified by, e.g., BibDesk
name|string
operator|=
name|string
operator|.
name|trim
argument_list|()
expr_stmt|;
if|if
condition|(
name|string
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
continue|continue;
block|}
name|int
name|spaceIndex
init|=
name|string
operator|.
name|indexOf
argument_list|(
literal|' '
argument_list|)
decl_stmt|;
if|if
condition|(
name|spaceIndex
operator|<=
literal|0
condition|)
block|{
throw|throw
operator|new
name|ParseException
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Expected \"%0\" to contain whitespace"
argument_list|,
name|string
argument_list|)
argument_list|)
throw|;
block|}
name|int
name|level
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|string
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|spaceIndex
argument_list|)
argument_list|)
decl_stmt|;
name|AbstractGroup
name|group
init|=
name|AbstractGroup
operator|.
name|fromString
argument_list|(
name|string
operator|.
name|substring
argument_list|(
name|spaceIndex
operator|+
literal|1
argument_list|)
argument_list|)
decl_stmt|;
name|GroupTreeNode
name|newNode
init|=
operator|new
name|GroupTreeNode
argument_list|(
name|group
argument_list|)
decl_stmt|;
if|if
condition|(
name|cursor
operator|==
literal|null
condition|)
block|{
comment|// create new root
name|cursor
operator|=
name|newNode
expr_stmt|;
name|root
operator|=
name|cursor
expr_stmt|;
block|}
else|else
block|{
comment|// insert at desired location
while|while
condition|(
name|level
operator|<=
name|cursor
operator|.
name|getLevel
argument_list|()
condition|)
block|{
name|cursor
operator|=
name|cursor
operator|.
name|getParent
argument_list|()
operator|.
name|get
argument_list|()
expr_stmt|;
block|}
name|cursor
operator|.
name|addChild
argument_list|(
name|newNode
argument_list|)
expr_stmt|;
name|cursor
operator|=
name|newNode
expr_stmt|;
block|}
block|}
return|return
name|root
return|;
block|}
block|}
end_class

end_unit

