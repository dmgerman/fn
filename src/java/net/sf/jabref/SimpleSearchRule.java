begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 Nathan Dunn, Morten O. Alver  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
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
name|Enumeration
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
DECL|field|m_searchAll
specifier|final
name|boolean
name|m_searchAll
decl_stmt|;
DECL|field|m_searchReq
specifier|final
name|boolean
name|m_searchReq
decl_stmt|;
DECL|field|m_searchOpt
specifier|final
name|boolean
name|m_searchOpt
decl_stmt|;
DECL|field|m_searchGen
specifier|final
name|boolean
name|m_searchGen
decl_stmt|;
DECL|method|SimpleSearchRule (boolean caseSensitive, boolean searchAll, boolean searchReq, boolean searchOpt, boolean searchGen)
specifier|public
name|SimpleSearchRule
parameter_list|(
name|boolean
name|caseSensitive
parameter_list|,
name|boolean
name|searchAll
parameter_list|,
name|boolean
name|searchReq
parameter_list|,
name|boolean
name|searchOpt
parameter_list|,
name|boolean
name|searchGen
parameter_list|)
block|{
name|m_caseSensitiveSearch
operator|=
name|caseSensitive
expr_stmt|;
comment|// 2005.03.29, trying to remove field category searches, to simplify
comment|// search usability.
name|m_searchAll
operator|=
literal|true
expr_stmt|;
comment|//m_searchAll = searchAll;
comment|// ---------------------------------------------------
name|m_searchReq
operator|=
name|searchReq
expr_stmt|;
name|m_searchOpt
operator|=
name|searchOpt
expr_stmt|;
name|m_searchGen
operator|=
name|searchGen
expr_stmt|;
block|}
DECL|method|applyRule (Map searchStrings,BibtexEntry bibtexEntry)
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
name|int
name|counter
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
name|String
name|upperString
init|=
literal|null
decl_stmt|;
try|try
block|{
name|upperString
operator|=
name|searchString
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|)
operator|.
name|toUpperCase
argument_list|()
operator|+
name|searchString
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
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
name|t
argument_list|)
expr_stmt|;
name|upperString
operator|=
name|searchString
expr_stmt|;
block|}
if|if
condition|(
name|m_caseSensitiveSearch
condition|)
block|{
name|score
operator|+=
name|doSearch
argument_list|(
name|searchString
argument_list|,
name|bibtexEntry
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|score
operator|+=
name|doSearch
argument_list|(
name|searchString
argument_list|,
name|bibtexEntry
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|searchString
operator|.
name|equals
argument_list|(
name|searchString
operator|.
name|toLowerCase
argument_list|()
argument_list|)
condition|)
block|{
name|score
operator|+=
name|doSearch
argument_list|(
name|searchString
operator|.
name|toLowerCase
argument_list|()
argument_list|,
name|bibtexEntry
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|searchString
operator|.
name|equals
argument_list|(
name|searchString
operator|.
name|toUpperCase
argument_list|()
argument_list|)
condition|)
block|{
name|score
operator|+=
name|doSearch
argument_list|(
name|searchString
operator|.
name|toUpperCase
argument_list|()
argument_list|,
name|bibtexEntry
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|searchString
operator|.
name|equals
argument_list|(
name|upperString
argument_list|)
condition|)
block|{
name|score
operator|+=
name|doSearch
argument_list|(
name|upperString
argument_list|,
name|bibtexEntry
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|score
return|;
block|}
DECL|method|doSearch (String searchString, BibtexEntry bibtexEntry)
specifier|public
name|int
name|doSearch
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
name|int
name|counter
init|=
literal|0
decl_stmt|;
comment|//score += searchAllFields(searchString,bibtexEntry) ;
if|if
condition|(
name|m_searchAll
condition|)
block|{
name|score
operator|+=
name|searchAllFields
argument_list|(
name|searchString
argument_list|,
name|bibtexEntry
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|m_searchReq
condition|)
name|score
operator|+=
name|searchRequiredFields
argument_list|(
name|searchString
argument_list|,
name|bibtexEntry
argument_list|)
expr_stmt|;
if|if
condition|(
name|m_searchOpt
condition|)
name|score
operator|+=
name|searchOptionalFields
argument_list|(
name|searchString
argument_list|,
name|bibtexEntry
argument_list|)
expr_stmt|;
if|if
condition|(
name|m_searchGen
condition|)
name|score
operator|+=
name|searchGeneralFields
argument_list|(
name|searchString
argument_list|,
name|bibtexEntry
argument_list|)
expr_stmt|;
block|}
return|return
name|score
return|;
block|}
DECL|method|searchRequiredFields (String searchString,BibtexEntry bibtexEntry)
specifier|public
name|int
name|searchRequiredFields
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
name|int
name|counter
init|=
literal|0
decl_stmt|;
name|String
index|[]
name|requiredField
init|=
name|bibtexEntry
operator|.
name|getRequiredFields
argument_list|()
decl_stmt|;
if|if
condition|(
name|requiredField
operator|!=
literal|null
condition|)
comment|// Some entries lack required fields.
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|requiredField
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|bibtexEntry
operator|.
name|getField
argument_list|(
name|requiredField
index|[
name|i
index|]
argument_list|)
operator|!=
literal|null
condition|)
try|try
block|{
name|counter
operator|=
name|String
operator|.
name|valueOf
argument_list|(
name|bibtexEntry
operator|.
name|getField
argument_list|(
name|requiredField
index|[
name|i
index|]
argument_list|)
argument_list|)
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
name|String
operator|.
name|valueOf
argument_list|(
name|bibtexEntry
operator|.
name|getField
argument_list|(
name|requiredField
index|[
name|i
index|]
argument_list|)
argument_list|)
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
DECL|method|searchOptionalFields (String searchString,BibtexEntry bibtexEntry)
specifier|public
name|int
name|searchOptionalFields
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
name|int
name|counter
init|=
literal|0
decl_stmt|;
name|String
index|[]
name|optionalField
init|=
name|bibtexEntry
operator|.
name|getOptionalFields
argument_list|()
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
name|optionalField
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|bibtexEntry
operator|.
name|getField
argument_list|(
name|optionalField
index|[
name|i
index|]
argument_list|)
operator|!=
literal|null
condition|)
try|try
block|{
name|counter
operator|=
name|String
operator|.
name|valueOf
argument_list|(
name|bibtexEntry
operator|.
name|getField
argument_list|(
name|optionalField
index|[
name|i
index|]
argument_list|)
argument_list|)
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
name|String
operator|.
name|valueOf
argument_list|(
name|bibtexEntry
operator|.
name|getField
argument_list|(
name|optionalField
index|[
name|i
index|]
argument_list|)
argument_list|)
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
DECL|method|searchGeneralFields (String searchString,BibtexEntry bibtexEntry)
specifier|public
name|int
name|searchGeneralFields
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
name|int
name|counter
init|=
literal|0
decl_stmt|;
name|String
index|[]
name|generalField
init|=
name|bibtexEntry
operator|.
name|getGeneralFields
argument_list|()
decl_stmt|;
comment|//        System.out.println("the number of general fields: " +generalField.length) ;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|generalField
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|bibtexEntry
operator|.
name|getField
argument_list|(
name|generalField
index|[
name|i
index|]
argument_list|)
operator|!=
literal|null
condition|)
try|try
block|{
name|counter
operator|=
name|String
operator|.
name|valueOf
argument_list|(
name|bibtexEntry
operator|.
name|getField
argument_list|(
name|generalField
index|[
name|i
index|]
argument_list|)
argument_list|)
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
name|String
operator|.
name|valueOf
argument_list|(
name|bibtexEntry
operator|.
name|getField
argument_list|(
name|generalField
index|[
name|i
index|]
argument_list|)
argument_list|)
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
DECL|method|searchAllFields (String searchString,BibtexEntry bibtexEntry)
specifier|public
name|int
name|searchAllFields
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
name|int
name|counter
init|=
literal|0
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
comment|//String[] fields = GUIGlobals.ALL_FIELDS;
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
if|if
condition|(
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
operator|!=
literal|null
condition|)
try|try
block|{
name|counter
operator|=
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

