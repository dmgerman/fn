begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2004 R. Nagel   All programs in this directory and  subdirectories are published under the GNU General Public License as  described below.   This program is free software; you can redistribute it and/or modify  it under the terms of the GNU General Public License as published by  the Free Software Foundation; either version 2 of the License, or (at  your option) any later version.   This program is distributed in the hope that it will be useful, but  WITHOUT ANY WARRANTY; without even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU  General Public License for more details.   You should have received a copy of the GNU General Public License  along with this program; if not, write to the Free Software  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA   Further information about the GNU GPL is available at:  http://www.gnu.org/copyleft/gpl.ja.html   */
end_comment

begin_comment
comment|// created by : r.nagel 06.10.2004
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|// function : save the textposition for tags in a recent TextInputDialog context
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|// modified :
end_comment

begin_package
DECL|package|net.sf.jabref.wizard.text
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|wizard
operator|.
name|text
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|LinkedList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ListIterator
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|text
operator|.
name|StyledDocument
import|;
end_import

begin_class
DECL|class|TagToMarkedTextStore
specifier|public
class|class
name|TagToMarkedTextStore
block|{
DECL|class|TMarkedStoreItem
specifier|private
class|class
name|TMarkedStoreItem
block|{
DECL|field|start
name|int
name|start
decl_stmt|;
DECL|field|end
name|int
name|end
decl_stmt|;
block|}
DECL|field|tagMap
specifier|private
name|HashMap
argument_list|<
name|String
argument_list|,
name|LinkedList
argument_list|<
name|TMarkedStoreItem
argument_list|>
argument_list|>
name|tagMap
decl_stmt|;
DECL|method|TagToMarkedTextStore ()
specifier|public
name|TagToMarkedTextStore
parameter_list|()
block|{
name|tagMap
operator|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|LinkedList
argument_list|<
name|TMarkedStoreItem
argument_list|>
argument_list|>
argument_list|(
literal|10
argument_list|)
expr_stmt|;
block|}
comment|/** appends a selection propertie for tag */
DECL|method|appendPosition (String tag, int start, int end)
specifier|public
name|void
name|appendPosition
parameter_list|(
name|String
name|tag
parameter_list|,
name|int
name|start
parameter_list|,
name|int
name|end
parameter_list|)
block|{
name|LinkedList
argument_list|<
name|TMarkedStoreItem
argument_list|>
name|ll
init|=
name|tagMap
operator|.
name|get
argument_list|(
name|tag
argument_list|)
decl_stmt|;
if|if
condition|(
name|ll
operator|==
literal|null
condition|)
block|{
name|ll
operator|=
operator|new
name|LinkedList
argument_list|<
name|TMarkedStoreItem
argument_list|>
argument_list|()
expr_stmt|;
name|tagMap
operator|.
name|put
argument_list|(
name|tag
argument_list|,
name|ll
argument_list|)
expr_stmt|;
block|}
name|TMarkedStoreItem
name|item
init|=
operator|new
name|TMarkedStoreItem
argument_list|()
decl_stmt|;
name|ll
operator|.
name|add
argument_list|(
name|item
argument_list|)
expr_stmt|;
name|item
operator|.
name|end
operator|=
name|end
expr_stmt|;
name|item
operator|.
name|start
operator|=
name|start
expr_stmt|;
block|}
comment|/** insert selection propertie for tag, old entries were deleted */
DECL|method|insertPosition (String tag, int start, int end)
specifier|public
name|void
name|insertPosition
parameter_list|(
name|String
name|tag
parameter_list|,
name|int
name|start
parameter_list|,
name|int
name|end
parameter_list|)
block|{
name|LinkedList
argument_list|<
name|TMarkedStoreItem
argument_list|>
name|ll
init|=
name|tagMap
operator|.
name|get
argument_list|(
name|tag
argument_list|)
decl_stmt|;
if|if
condition|(
name|ll
operator|==
literal|null
condition|)
block|{
name|ll
operator|=
operator|new
name|LinkedList
argument_list|<
name|TMarkedStoreItem
argument_list|>
argument_list|()
expr_stmt|;
name|tagMap
operator|.
name|put
argument_list|(
name|tag
argument_list|,
name|ll
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|ll
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
name|TMarkedStoreItem
name|item
init|=
operator|new
name|TMarkedStoreItem
argument_list|()
decl_stmt|;
name|ll
operator|.
name|add
argument_list|(
name|item
argument_list|)
expr_stmt|;
name|item
operator|.
name|end
operator|=
name|end
expr_stmt|;
name|item
operator|.
name|start
operator|=
name|start
expr_stmt|;
block|}
comment|/** set the Style for the tag if an entry is available */
DECL|method|setStyleForTag (String tag, String style, StyledDocument doc)
specifier|public
name|void
name|setStyleForTag
parameter_list|(
name|String
name|tag
parameter_list|,
name|String
name|style
parameter_list|,
name|StyledDocument
name|doc
parameter_list|)
block|{
name|LinkedList
argument_list|<
name|TMarkedStoreItem
argument_list|>
name|ll
init|=
name|tagMap
operator|.
name|get
argument_list|(
name|tag
argument_list|)
decl_stmt|;
if|if
condition|(
name|ll
operator|!=
literal|null
condition|)
block|{
comment|// iterate over all saved selections
for|for
control|(
name|TMarkedStoreItem
name|du2
range|:
name|ll
control|)
block|{
if|if
condition|(
name|du2
operator|!=
literal|null
condition|)
block|{
name|TMarkedStoreItem
name|item
init|=
operator|(
name|TMarkedStoreItem
operator|)
name|du2
decl_stmt|;
name|doc
operator|.
name|setCharacterAttributes
argument_list|(
name|item
operator|.
name|start
argument_list|,
name|item
operator|.
name|end
operator|-
name|item
operator|.
name|start
argument_list|,
name|doc
operator|.
name|getStyle
argument_list|(
name|style
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
end_class

end_unit

