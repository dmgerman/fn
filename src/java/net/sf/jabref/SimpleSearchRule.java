begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003 Nathan Dunn, Morten O. Alver   All programs in this directory and  subdirectories are published under the GNU General Public License as  described below.   This program is free software; you can redistribute it and/or modify  it under the terms of the GNU General Public License as published by  the Free Software Foundation; either version 2 of the License, or (at  your option) any later version.   This program is distributed in the hope that it will be useful, but  WITHOUT ANY WARRANTY; without even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU  General Public License for more details.   You should have received a copy of the GNU General Public License  along with this program; if not, write to the Free Software  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA   Further information about the GNU GPL is available at:  http://www.gnu.org/copyleft/gpl.ja.html   */
end_comment

begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
package|;
end_package

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
name|export
operator|.
name|layout
operator|.
name|format
operator|.
name|RemoveLatexCommands
import|;
end_import

begin_class
DECL|class|SimpleSearchRule
specifier|public
class|class
name|SimpleSearchRule
implements|implements
name|SearchRule
block|{
DECL|field|m_caseSensitiveSearch
specifier|final
name|boolean
name|m_caseSensitiveSearch
decl_stmt|;
comment|//static RemoveBrackets removeBrackets = new RemoveBrackets();
DECL|field|removeBrackets
specifier|static
name|RemoveLatexCommands
name|removeBrackets
init|=
operator|new
name|RemoveLatexCommands
argument_list|()
decl_stmt|;
DECL|method|SimpleSearchRule (boolean caseSensitive)
specifier|public
name|SimpleSearchRule
parameter_list|(
name|boolean
name|caseSensitive
parameter_list|)
block|{
name|m_caseSensitiveSearch
operator|=
name|caseSensitive
expr_stmt|;
block|}
DECL|method|validateSearchStrings (Map<String, String> searchStrings)
specifier|public
name|boolean
name|validateSearchStrings
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|searchStrings
parameter_list|)
block|{
return|return
literal|true
return|;
block|}
DECL|method|applyRule (Map<String, String> searchStrings, BibtexEntry bibtexEntry)
specifier|public
name|int
name|applyRule
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|searchStrings
parameter_list|,
name|BibtexEntry
name|bibtexEntry
parameter_list|)
block|{
name|String
name|searchString
init|=
name|searchStrings
operator|.
name|values
argument_list|()
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|m_caseSensitiveSearch
condition|)
name|searchString
operator|=
name|searchString
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
name|int
name|score
init|=
literal|0
decl_stmt|;
name|int
name|counter
init|=
literal|0
decl_stmt|;
name|Object
name|fieldContentAsObject
decl_stmt|;
name|String
name|fieldContent
decl_stmt|;
for|for
control|(
name|String
name|field
range|:
name|bibtexEntry
operator|.
name|getAllFields
argument_list|()
control|)
block|{
name|fieldContentAsObject
operator|=
name|bibtexEntry
operator|.
name|getField
argument_list|(
name|field
argument_list|)
expr_stmt|;
if|if
condition|(
name|fieldContentAsObject
operator|!=
literal|null
condition|)
try|try
block|{
name|fieldContent
operator|=
name|removeBrackets
operator|.
name|format
argument_list|(
name|fieldContentAsObject
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|m_caseSensitiveSearch
condition|)
name|fieldContent
operator|=
name|fieldContent
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
name|counter
operator|=
name|fieldContent
operator|.
name|indexOf
argument_list|(
name|searchString
argument_list|,
name|counter
argument_list|)
expr_stmt|;
while|while
condition|(
name|counter
operator|>=
literal|0
condition|)
block|{
operator|++
name|score
expr_stmt|;
name|counter
operator|=
name|fieldContent
operator|.
name|indexOf
argument_list|(
name|searchString
argument_list|,
name|counter
operator|+
literal|1
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"sorting error: "
operator|+
name|t
argument_list|)
expr_stmt|;
block|}
name|counter
operator|=
literal|0
expr_stmt|;
block|}
return|return
name|score
return|;
block|}
block|}
end_class

end_unit

