begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003 Morten O. Alver, Nizar N. Batada   All programs in this directory and  subdirectories are published under the GNU General Public License as  described below.   This program is free software; you can redistribute it and/or modify  it under the terms of the GNU General Public License as published by  the Free Software Foundation; either version 2 of the License, or (at  your option) any later version.   This program is distributed in the hope that it will be useful, but  WITHOUT ANY WARRANTY; without even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU  General Public License for more details.   You should have received a copy of the GNU General Public License  along with this program; if not, write to the Free Software  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA   Further information about the GNU GPL is available at:  http://www.gnu.org/copyleft/gpl.ja.html   */
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
name|Enumeration
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
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
name|BibtexEntry
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
name|SearchRule
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
name|SearchRuleSet
import|;
end_import

begin_comment
comment|/**  * Subclass of SearchRuleSet that ANDs or ORs between its rules, eturning 0 or  * 1.  */
end_comment

begin_class
DECL|class|AndOrSearchRuleSet
class|class
name|AndOrSearchRuleSet
extends|extends
name|SearchRuleSet
block|{
DECL|field|and
DECL|field|invert
specifier|private
name|boolean
name|and
decl_stmt|,
name|invert
decl_stmt|;
DECL|method|AndOrSearchRuleSet (boolean and, boolean invert)
specifier|public
name|AndOrSearchRuleSet
parameter_list|(
name|boolean
name|and
parameter_list|,
name|boolean
name|invert
parameter_list|)
block|{
name|this
operator|.
name|and
operator|=
name|and
expr_stmt|;
name|this
operator|.
name|invert
operator|=
name|invert
expr_stmt|;
block|}
DECL|method|applyRule (Map searchString, BibtexEntry bibtexEntry)
specifier|public
name|int
name|applyRule
parameter_list|(
name|Map
name|searchString
parameter_list|,
name|BibtexEntry
name|bibtexEntry
parameter_list|)
block|{
name|int
name|score
init|=
literal|0
decl_stmt|;
name|Enumeration
name|e
init|=
name|ruleSet
operator|.
name|elements
argument_list|()
decl_stmt|;
comment|// We let each rule add a maximum of 1 to the score.
while|while
condition|(
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|)
block|{
name|score
operator|+=
operator|(
operator|(
operator|(
name|SearchRule
operator|)
name|e
operator|.
name|nextElement
argument_list|()
operator|)
operator|.
name|applyRule
argument_list|(
name|searchString
argument_list|,
name|bibtexEntry
argument_list|)
operator|>
literal|0
condition|?
literal|1
else|:
literal|0
operator|)
expr_stmt|;
block|}
comment|// Then an AND rule demands that score == number of rules, and
comment|// an OR rule demands score> 0.
name|boolean
name|res
decl_stmt|;
if|if
condition|(
name|and
condition|)
name|res
operator|=
operator|(
name|score
operator|==
name|ruleSet
operator|.
name|size
argument_list|()
operator|)
expr_stmt|;
else|else
name|res
operator|=
operator|(
name|score
operator|>
literal|0
operator|)
expr_stmt|;
if|if
condition|(
name|invert
condition|)
return|return
operator|(
name|res
condition|?
literal|0
else|:
literal|1
operator|)
return|;
return|return
operator|(
name|res
condition|?
literal|1
else|:
literal|0
operator|)
return|;
block|}
block|}
end_class

end_unit

