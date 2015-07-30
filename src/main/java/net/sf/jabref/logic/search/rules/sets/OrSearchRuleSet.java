begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.search.rules.sets
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|search
operator|.
name|rules
operator|.
name|sets
package|;
end_package

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
name|logic
operator|.
name|search
operator|.
name|SearchRule
import|;
end_import

begin_comment
comment|/**  * Subclass of SearchRuleSet that ANDs or ORs between its rules, returning 0 or  * 1.  */
end_comment

begin_class
DECL|class|OrSearchRuleSet
specifier|public
class|class
name|OrSearchRuleSet
extends|extends
name|SearchRuleSet
block|{
annotation|@
name|Override
DECL|method|applyRule (String searchString, BibtexEntry bibtexEntry)
specifier|public
name|boolean
name|applyRule
parameter_list|(
name|String
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
comment|// We let each rule add a maximum of 1 to the score.
for|for
control|(
name|SearchRule
name|rule
range|:
name|ruleSet
control|)
block|{
if|if
condition|(
name|rule
operator|.
name|applyRule
argument_list|(
name|searchString
argument_list|,
name|bibtexEntry
argument_list|)
condition|)
block|{
name|score
operator|++
expr_stmt|;
block|}
block|}
comment|// OR rule demands score> 0.
return|return
name|score
operator|>
literal|0
return|;
block|}
block|}
end_class

end_unit

