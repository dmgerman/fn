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
name|BibtexEntryType
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
comment|/**  *  This class is the abstract class which contains all of the rules  *  for making the different types of Rules.  */
end_comment

begin_class
DECL|class|LabelMaker
specifier|public
class|class
name|LabelMaker
block|{
DECL|method|applyRule (BibtexEntry newEntry, BibtexDatabase base)
specifier|public
name|BibtexEntry
name|applyRule
parameter_list|(
name|BibtexEntry
name|newEntry
parameter_list|,
name|BibtexDatabase
name|base
parameter_list|)
block|{
name|String
name|newKey
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|ruleTable
operator|.
name|containsKey
argument_list|(
name|newEntry
operator|.
name|getType
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
condition|)
block|{
name|newKey
operator|=
name|ruleTable
operator|.
name|get
argument_list|(
name|newEntry
operator|.
name|getType
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
operator|.
name|applyRule
argument_list|(
name|newEntry
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|newKey
operator|=
name|applyDefaultRule
argument_list|(
name|newEntry
argument_list|)
expr_stmt|;
block|}
comment|// Remove all illegal characters from the key.
name|newKey
operator|=
name|Util
operator|.
name|checkLegalKey
argument_list|(
name|newKey
argument_list|)
expr_stmt|;
comment|// Try new keys until we get a unique one:
if|if
condition|(
name|base
operator|.
name|setCiteKeyForEntry
argument_list|(
name|newEntry
operator|.
name|getId
argument_list|()
argument_list|,
name|newKey
argument_list|)
condition|)
block|{
name|char
name|c
init|=
literal|'b'
decl_stmt|;
name|String
name|modKey
init|=
name|newKey
operator|+
literal|"a"
decl_stmt|;
while|while
condition|(
name|base
operator|.
name|setCiteKeyForEntry
argument_list|(
name|newEntry
operator|.
name|getId
argument_list|()
argument_list|,
name|modKey
argument_list|)
condition|)
name|modKey
operator|=
name|newKey
operator|+
operator|(
operator|(
name|c
operator|++
operator|)
operator|)
expr_stmt|;
block|}
comment|//newEntry.setField(Globals.KEY_FIELD, newKey);
comment|// ...
return|return
name|newEntry
return|;
block|}
DECL|method|setDefaultRule (LabelRule newRule)
specifier|public
name|void
name|setDefaultRule
parameter_list|(
name|LabelRule
name|newRule
parameter_list|)
block|{
name|defaultRule
operator|=
name|newRule
expr_stmt|;
block|}
DECL|method|applyDefaultRule (BibtexEntry newEntry)
specifier|public
name|String
name|applyDefaultRule
parameter_list|(
name|BibtexEntry
name|newEntry
parameter_list|)
block|{
return|return
name|defaultRule
operator|.
name|applyRule
argument_list|(
name|newEntry
argument_list|)
return|;
block|}
comment|// there should be a default rule for any type
DECL|method|addRule (LabelRule rule,BibtexEntryType type)
specifier|public
name|void
name|addRule
parameter_list|(
name|LabelRule
name|rule
parameter_list|,
name|BibtexEntryType
name|type
parameter_list|)
block|{
name|ruleTable
operator|.
name|put
argument_list|(
name|type
operator|.
name|getName
argument_list|()
argument_list|,
name|rule
argument_list|)
expr_stmt|;
block|}
DECL|field|defaultRule
specifier|protected
name|LabelRule
name|defaultRule
init|=
operator|new
name|ArticleLabelRule
argument_list|()
decl_stmt|;
DECL|field|ruleTable
specifier|protected
name|Hashtable
argument_list|<
name|String
argument_list|,
name|LabelRule
argument_list|>
name|ruleTable
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|LabelRule
argument_list|>
argument_list|()
decl_stmt|;
block|}
end_class

end_unit

