begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  All programs in this directory and subdirectories are published under the   GNU General Public License as described below.   This program is free software; you can redistribute it and/or modify it   under the terms of the GNU General Public License as published by the Free   Software Foundation; either version 2 of the License, or (at your option)   any later version.   This program is distributed in the hope that it will be useful, but WITHOUT   ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or   FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for   more details.   You should have received a copy of the GNU General Public License along   with this program; if not, write to the Free Software Foundation, Inc., 59   Temple Place, Suite 330, Boston, MA 02111-1307 USA   Further information about the GNU GPL is available at:  http://www.gnu.org/copyleft/gpl.ja.html  */
end_comment

begin_package
DECL|package|net.sf.jabref.groups
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|Vector
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
name|BibtexDatabase
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
name|Util
import|;
end_import

begin_comment
comment|/**  * Handles versioning of groups, e.g. automatic conversion from previous to  * current versions, or import of flat groups (JabRef<= 1.6) to tree.  *   * @author jzieren (10.04.2005)  */
end_comment

begin_class
DECL|class|VersionHandling
specifier|public
class|class
name|VersionHandling
block|{
DECL|field|CURRENT_VERSION
specifier|public
specifier|static
specifier|final
name|int
name|CURRENT_VERSION
init|=
literal|3
decl_stmt|;
comment|/**      * Imports old (flat) groups data and converts it to a 2-level tree with an      * AllEntriesGroup at the root.      *       * @return the root of the generated tree.      */
DECL|method|importFlatGroups (Vector<String> groups)
specifier|public
specifier|static
name|GroupTreeNode
name|importFlatGroups
parameter_list|(
name|Vector
argument_list|<
name|String
argument_list|>
name|groups
parameter_list|)
throws|throws
name|IllegalArgumentException
block|{
name|GroupTreeNode
name|root
init|=
operator|new
name|GroupTreeNode
argument_list|(
operator|new
name|AllEntriesGroup
argument_list|()
argument_list|)
decl_stmt|;
specifier|final
name|int
name|number
init|=
name|groups
operator|.
name|size
argument_list|()
operator|/
literal|3
decl_stmt|;
name|String
name|name
decl_stmt|,
name|field
decl_stmt|,
name|regexp
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|number
condition|;
operator|++
name|i
control|)
block|{
name|field
operator|=
name|groups
operator|.
name|get
argument_list|(
literal|3
operator|*
name|i
operator|+
literal|0
argument_list|)
expr_stmt|;
name|name
operator|=
name|groups
operator|.
name|get
argument_list|(
literal|3
operator|*
name|i
operator|+
literal|1
argument_list|)
expr_stmt|;
name|regexp
operator|=
name|groups
operator|.
name|get
argument_list|(
literal|3
operator|*
name|i
operator|+
literal|2
argument_list|)
expr_stmt|;
name|root
operator|.
name|add
argument_list|(
operator|new
name|GroupTreeNode
argument_list|(
operator|new
name|KeywordGroup
argument_list|(
name|name
argument_list|,
name|field
argument_list|,
name|regexp
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|,
name|AbstractGroup
operator|.
name|INDEPENDENT
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|root
return|;
block|}
DECL|method|importGroups (Vector<String> orderedData, BibtexDatabase db, int version)
specifier|public
specifier|static
name|GroupTreeNode
name|importGroups
parameter_list|(
name|Vector
argument_list|<
name|String
argument_list|>
name|orderedData
parameter_list|,
name|BibtexDatabase
name|db
parameter_list|,
name|int
name|version
parameter_list|)
throws|throws
name|Exception
block|{
switch|switch
condition|(
name|version
condition|)
block|{
case|case
literal|0
case|:
case|case
literal|1
case|:
return|return
name|Version0_1
operator|.
name|fromString
argument_list|(
name|orderedData
operator|.
name|firstElement
argument_list|()
argument_list|,
name|db
argument_list|,
name|version
argument_list|)
return|;
case|case
literal|2
case|:
case|case
literal|3
case|:
return|return
name|Version2_3
operator|.
name|fromString
argument_list|(
name|orderedData
argument_list|,
name|db
argument_list|,
name|version
argument_list|)
return|;
default|default:
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Failed to read groups data (unsupported version: %0)"
argument_list|,
literal|""
operator|+
name|version
argument_list|)
argument_list|)
throw|;
block|}
block|}
comment|/** Imports groups version 0 and 1. */
DECL|class|Version0_1
specifier|private
specifier|static
class|class
name|Version0_1
block|{
comment|/**          * Parses the textual representation obtained from          * GroupTreeNode.toString() and recreates that node and all of its          * children from it.          *           * @throws Exception          *             When a group could not be recreated          */
DECL|method|fromString (String s, BibtexDatabase db, int version)
specifier|private
specifier|static
name|GroupTreeNode
name|fromString
parameter_list|(
name|String
name|s
parameter_list|,
name|BibtexDatabase
name|db
parameter_list|,
name|int
name|version
parameter_list|)
throws|throws
name|Exception
block|{
name|GroupTreeNode
name|root
init|=
literal|null
decl_stmt|;
name|GroupTreeNode
name|newNode
decl_stmt|;
name|int
name|i
decl_stmt|;
name|String
name|g
decl_stmt|;
while|while
condition|(
name|s
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
if|if
condition|(
name|s
operator|.
name|startsWith
argument_list|(
literal|"("
argument_list|)
condition|)
block|{
name|String
name|subtree
init|=
name|getSubtree
argument_list|(
name|s
argument_list|)
decl_stmt|;
name|newNode
operator|=
name|fromString
argument_list|(
name|subtree
argument_list|,
name|db
argument_list|,
name|version
argument_list|)
expr_stmt|;
comment|// continue after this subtree by removing it
comment|// and the leading/trailing braces, and
comment|// the comma (that makes 3) that always trails it
comment|// unless it's at the end of s anyway.
name|i
operator|=
literal|3
operator|+
name|subtree
operator|.
name|length
argument_list|()
expr_stmt|;
name|s
operator|=
name|i
operator|>=
name|s
operator|.
name|length
argument_list|()
condition|?
literal|""
else|:
name|s
operator|.
name|substring
argument_list|(
name|i
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|i
operator|=
name|indexOfUnquoted
argument_list|(
name|s
argument_list|,
literal|','
argument_list|)
expr_stmt|;
name|g
operator|=
name|i
operator|<
literal|0
condition|?
name|s
else|:
name|s
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|i
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|>=
literal|0
condition|)
name|s
operator|=
name|s
operator|.
name|substring
argument_list|(
name|i
operator|+
literal|1
argument_list|)
expr_stmt|;
else|else
name|s
operator|=
literal|""
expr_stmt|;
name|newNode
operator|=
operator|new
name|GroupTreeNode
argument_list|(
name|AbstractGroup
operator|.
name|fromString
argument_list|(
name|Util
operator|.
name|unquote
argument_list|(
name|g
argument_list|,
literal|'\\'
argument_list|)
argument_list|,
name|db
argument_list|,
name|version
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|root
operator|==
literal|null
condition|)
comment|// first node will be root
name|root
operator|=
name|newNode
expr_stmt|;
else|else
name|root
operator|.
name|add
argument_list|(
name|newNode
argument_list|)
expr_stmt|;
block|}
return|return
name|root
return|;
block|}
comment|/**          * Returns the substring delimited by a pair of matching braces, with          * the first brace at index 0. Quoted characters are skipped.          *           * @return the matching substring, or "" if not found.          */
DECL|method|getSubtree (String s)
specifier|private
specifier|static
name|String
name|getSubtree
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|int
name|i
init|=
literal|1
decl_stmt|;
name|int
name|level
init|=
literal|1
decl_stmt|;
while|while
condition|(
name|i
operator|<
name|s
operator|.
name|length
argument_list|()
condition|)
block|{
switch|switch
condition|(
name|s
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
condition|)
block|{
case|case
literal|'\\'
case|:
operator|++
name|i
expr_stmt|;
break|break;
case|case
literal|'('
case|:
operator|++
name|level
expr_stmt|;
break|break;
case|case
literal|')'
case|:
operator|--
name|level
expr_stmt|;
if|if
condition|(
name|level
operator|==
literal|0
condition|)
return|return
name|s
operator|.
name|substring
argument_list|(
literal|1
argument_list|,
name|i
argument_list|)
return|;
break|break;
block|}
operator|++
name|i
expr_stmt|;
block|}
return|return
literal|""
return|;
block|}
comment|/**          * Returns the index of the first occurence of c, skipping quoted          * special characters (escape character: '\\').          *           * @param s          *            The String to search in.          * @param c          *            The character to search          * @return The index of the first unescaped occurence of c in s, or -1          *         if not found.          */
DECL|method|indexOfUnquoted (String s, char c)
specifier|private
specifier|static
name|int
name|indexOfUnquoted
parameter_list|(
name|String
name|s
parameter_list|,
name|char
name|c
parameter_list|)
block|{
name|int
name|i
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|i
operator|<
name|s
operator|.
name|length
argument_list|()
condition|)
block|{
if|if
condition|(
name|s
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
operator|==
literal|'\\'
condition|)
block|{
operator|++
name|i
expr_stmt|;
comment|// skip quoted special
block|}
else|else
block|{
if|if
condition|(
name|s
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
operator|==
name|c
condition|)
return|return
name|i
return|;
block|}
operator|++
name|i
expr_stmt|;
block|}
return|return
operator|-
literal|1
return|;
block|}
block|}
DECL|class|Version2_3
specifier|private
specifier|static
class|class
name|Version2_3
block|{
DECL|method|fromString (Vector<String> data, BibtexDatabase db, int version)
specifier|private
specifier|static
name|GroupTreeNode
name|fromString
parameter_list|(
name|Vector
argument_list|<
name|String
argument_list|>
name|data
parameter_list|,
name|BibtexDatabase
name|db
parameter_list|,
name|int
name|version
parameter_list|)
throws|throws
name|Exception
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
name|GroupTreeNode
name|newNode
decl_stmt|;
name|AbstractGroup
name|group
decl_stmt|;
name|int
name|spaceIndex
decl_stmt|;
name|int
name|level
decl_stmt|;
name|String
name|s
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|data
operator|.
name|size
argument_list|()
condition|;
operator|++
name|i
control|)
block|{
name|s
operator|=
name|data
operator|.
name|elementAt
argument_list|(
name|i
argument_list|)
operator|.
name|toString
argument_list|()
expr_stmt|;
comment|// This allows to read databases that have been modified by, e.g., BibDesk
name|s
operator|=
name|s
operator|.
name|trim
argument_list|()
expr_stmt|;
if|if
condition|(
name|s
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
continue|continue;
name|spaceIndex
operator|=
name|s
operator|.
name|indexOf
argument_list|(
literal|' '
argument_list|)
expr_stmt|;
if|if
condition|(
name|spaceIndex
operator|<=
literal|0
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"bad format"
argument_list|)
throw|;
comment|// JZTODO lyrics
name|level
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|s
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|spaceIndex
argument_list|)
argument_list|)
expr_stmt|;
name|group
operator|=
name|AbstractGroup
operator|.
name|fromString
argument_list|(
name|s
operator|.
name|substring
argument_list|(
name|spaceIndex
operator|+
literal|1
argument_list|)
argument_list|,
name|db
argument_list|,
name|version
argument_list|)
expr_stmt|;
name|newNode
operator|=
operator|new
name|GroupTreeNode
argument_list|(
name|group
argument_list|)
expr_stmt|;
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
name|cursor
operator|=
operator|(
name|GroupTreeNode
operator|)
name|cursor
operator|.
name|getParent
argument_list|()
expr_stmt|;
name|cursor
operator|.
name|add
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
block|}
end_class

end_unit

