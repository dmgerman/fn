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
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
import|;
end_import

begin_class
DECL|class|RegExpRule
specifier|public
class|class
name|RegExpRule
implements|implements
name|SearchRule
block|{
DECL|field|m_caseSensitiveSearch
specifier|final
name|boolean
name|m_caseSensitiveSearch
decl_stmt|;
DECL|method|RegExpRule (boolean caseSensitive)
specifier|public
name|RegExpRule
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
DECL|method|applyRule (Map searchStrings, BibtexEntry bibtexEntry)
specifier|public
name|int
name|applyRule
parameter_list|(
name|Map
name|searchStrings
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
name|Iterator
name|e
init|=
name|searchStrings
operator|.
name|values
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|String
name|searchString
init|=
operator|(
name|String
operator|)
name|e
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|searchString
operator|.
name|matches
argument_list|(
literal|"\\.\\*"
argument_list|)
condition|)
block|{
name|searchString
operator|=
literal|".*"
operator|+
name|searchString
operator|+
literal|".*"
expr_stmt|;
block|}
name|int
name|flags
init|=
literal|0
decl_stmt|;
if|if
condition|(
operator|!
name|m_caseSensitiveSearch
condition|)
name|flags
operator|=
name|Pattern
operator|.
name|CASE_INSENSITIVE
expr_stmt|;
comment|// testing
name|Pattern
name|pattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
name|searchString
argument_list|,
name|flags
argument_list|)
decl_stmt|;
name|Object
index|[]
name|fields
init|=
name|bibtexEntry
operator|.
name|getAllFields
argument_list|()
decl_stmt|;
name|score
operator|+=
name|searchFields
argument_list|(
name|fields
argument_list|,
name|bibtexEntry
argument_list|,
name|pattern
argument_list|)
expr_stmt|;
return|return
name|score
return|;
block|}
DECL|method|searchFields (Object[] fields, BibtexEntry bibtexEntry, Pattern pattern)
specifier|protected
name|int
name|searchFields
parameter_list|(
name|Object
index|[]
name|fields
parameter_list|,
name|BibtexEntry
name|bibtexEntry
parameter_list|,
name|Pattern
name|pattern
parameter_list|)
block|{
name|int
name|score
init|=
literal|0
decl_stmt|;
if|if
condition|(
name|fields
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|fields
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
try|try
block|{
if|if
condition|(
name|pattern
operator|.
name|matcher
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|bibtexEntry
operator|.
name|getField
argument_list|(
name|fields
index|[
name|i
index|]
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
argument_list|)
operator|.
name|matches
argument_list|()
condition|)
block|{
name|score
operator|++
expr_stmt|;
comment|// Util.pr(String.valueOf(bibtexEntry.getField(fields[i].toString())));
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
literal|"Searching error: "
operator|+
name|t
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|score
return|;
block|}
block|}
end_class

end_unit

