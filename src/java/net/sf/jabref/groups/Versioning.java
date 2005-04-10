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

begin_comment
comment|/**  * Handles versioning of groups, e.g. automatic conversion from previous to  * current versions, or import of flat groups (JabRef<= 1.6) to tree.  *   * @author jzieren (10.04.2005)  */
end_comment

begin_class
DECL|class|Versioning
specifier|public
class|class
name|Versioning
block|{
DECL|field|CURRENT_VERSION
specifier|public
specifier|static
specifier|final
name|int
name|CURRENT_VERSION
init|=
literal|1
decl_stmt|;
comment|/**      * Imports old (flat) groups data and converts it to a 2-level tree with an      * AllEntriesGroup at the root.      *       * @return the root of the generated tree.      */
DECL|method|importFlatGroups (Vector groups)
specifier|public
specifier|static
name|GroupTreeNode
name|importFlatGroups
parameter_list|(
name|Vector
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
operator|(
name|String
operator|)
name|groups
operator|.
name|elementAt
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
operator|(
name|String
operator|)
name|groups
operator|.
name|elementAt
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
operator|(
name|String
operator|)
name|groups
operator|.
name|elementAt
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
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|root
return|;
block|}
block|}
end_class

end_unit

