begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.bibtex.comparator
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|bibtex
operator|.
name|comparator
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
name|bibtex
operator|.
name|FieldProperties
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
name|bibtex
operator|.
name|InternalBibtexFields
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
name|gui
operator|.
name|maintable
operator|.
name|MainTableFormat
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
name|config
operator|.
name|SaveOrderConfig
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
name|util
operator|.
name|strings
operator|.
name|StringUtil
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
name|model
operator|.
name|entry
operator|.
name|AuthorList
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
name|model
operator|.
name|entry
operator|.
name|MonthUtil
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
name|model
operator|.
name|entry
operator|.
name|YearUtil
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
name|model
operator|.
name|entry
operator|.
name|BibEntry
import|;
end_import

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|Collator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|ParseException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|RuleBasedCollator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Comparator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Locale
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Objects
import|;
end_import

begin_comment
comment|/**  *  * A comparator for BibEntry fields  *  * Initial Version:  *  * @author alver  * @version Date: Oct 13, 2005 Time: 10:10:04 PM To  *  * TODO: Testcases  *  */
end_comment

begin_class
DECL|class|FieldComparator
specifier|public
class|class
name|FieldComparator
implements|implements
name|Comparator
argument_list|<
name|BibEntry
argument_list|>
block|{
DECL|field|COLLATOR
specifier|private
specifier|static
specifier|final
name|Collator
name|COLLATOR
init|=
name|getCollator
argument_list|()
decl_stmt|;
DECL|method|getCollator ()
specifier|private
specifier|static
name|Collator
name|getCollator
parameter_list|()
block|{
try|try
block|{
return|return
operator|new
name|RuleBasedCollator
argument_list|(
operator|(
operator|(
name|RuleBasedCollator
operator|)
name|Collator
operator|.
name|getInstance
argument_list|()
operator|)
operator|.
name|getRules
argument_list|()
operator|.
name|replace
argument_list|(
literal|"<'\u005f'"
argument_list|,
literal|"<' '<'\u005f'"
argument_list|)
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|ParseException
name|e
parameter_list|)
block|{
return|return
name|Collator
operator|.
name|getInstance
argument_list|()
return|;
block|}
block|}
DECL|enum|FieldType
enum|enum
name|FieldType
block|{
DECL|enumConstant|NAME
DECL|enumConstant|TYPE
DECL|enumConstant|YEAR
DECL|enumConstant|MONTH
DECL|enumConstant|OTHER
name|NAME
block|,
name|TYPE
block|,
name|YEAR
block|,
name|MONTH
block|,
name|OTHER
block|;     }
DECL|field|field
specifier|private
specifier|final
name|String
index|[]
name|field
decl_stmt|;
DECL|field|fieldName
specifier|private
specifier|final
name|String
name|fieldName
decl_stmt|;
DECL|field|fieldType
specifier|private
specifier|final
name|FieldType
name|fieldType
decl_stmt|;
DECL|field|isNumeric
specifier|private
specifier|final
name|boolean
name|isNumeric
decl_stmt|;
DECL|field|multiplier
specifier|private
specifier|final
name|int
name|multiplier
decl_stmt|;
DECL|method|FieldComparator (String field)
specifier|public
name|FieldComparator
parameter_list|(
name|String
name|field
parameter_list|)
block|{
name|this
argument_list|(
name|field
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
DECL|method|FieldComparator (String field, boolean reversed)
specifier|public
name|FieldComparator
parameter_list|(
name|String
name|field
parameter_list|,
name|boolean
name|reversed
parameter_list|)
block|{
name|this
operator|.
name|fieldName
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|field
argument_list|)
expr_stmt|;
name|this
operator|.
name|field
operator|=
name|fieldName
operator|.
name|split
argument_list|(
name|MainTableFormat
operator|.
name|COL_DEFINITION_FIELD_SEPARATOR
argument_list|)
expr_stmt|;
name|fieldType
operator|=
name|determineFieldType
argument_list|()
expr_stmt|;
name|isNumeric
operator|=
name|InternalBibtexFields
operator|.
name|isNumeric
argument_list|(
name|this
operator|.
name|field
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
if|if
condition|(
name|fieldType
operator|==
name|FieldType
operator|.
name|MONTH
condition|)
block|{
comment|/*              * [ 1598777 ] Month sorting              *              * http://sourceforge.net/tracker/index.php?func=detail&aid=1598777&group_id=92314&atid=600306              */
name|multiplier
operator|=
name|reversed
condition|?
literal|1
else|:
operator|-
literal|1
expr_stmt|;
block|}
else|else
block|{
name|multiplier
operator|=
name|reversed
condition|?
operator|-
literal|1
else|:
literal|1
expr_stmt|;
block|}
block|}
DECL|method|determineFieldType ()
specifier|private
name|FieldType
name|determineFieldType
parameter_list|()
block|{
if|if
condition|(
name|BibEntry
operator|.
name|TYPE_HEADER
operator|.
name|equals
argument_list|(
name|this
operator|.
name|field
index|[
literal|0
index|]
argument_list|)
condition|)
block|{
return|return
name|FieldType
operator|.
name|TYPE
return|;
block|}
elseif|else
if|if
condition|(
name|InternalBibtexFields
operator|.
name|getFieldExtras
argument_list|(
name|this
operator|.
name|field
index|[
literal|0
index|]
argument_list|)
operator|.
name|contains
argument_list|(
name|FieldProperties
operator|.
name|PERSON_NAMES
argument_list|)
condition|)
block|{
return|return
name|FieldType
operator|.
name|NAME
return|;
block|}
elseif|else
if|if
condition|(
literal|"year"
operator|.
name|equals
argument_list|(
name|this
operator|.
name|field
index|[
literal|0
index|]
argument_list|)
condition|)
block|{
return|return
name|FieldType
operator|.
name|YEAR
return|;
block|}
elseif|else
if|if
condition|(
literal|"month"
operator|.
name|equals
argument_list|(
name|this
operator|.
name|field
index|[
literal|0
index|]
argument_list|)
condition|)
block|{
return|return
name|FieldType
operator|.
name|MONTH
return|;
block|}
else|else
block|{
return|return
name|FieldType
operator|.
name|OTHER
return|;
block|}
block|}
DECL|method|FieldComparator (SaveOrderConfig.SortCriterion sortCriterion)
specifier|public
name|FieldComparator
parameter_list|(
name|SaveOrderConfig
operator|.
name|SortCriterion
name|sortCriterion
parameter_list|)
block|{
name|this
argument_list|(
name|sortCriterion
operator|.
name|field
argument_list|,
name|sortCriterion
operator|.
name|descending
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|compare (BibEntry e1, BibEntry e2)
specifier|public
name|int
name|compare
parameter_list|(
name|BibEntry
name|e1
parameter_list|,
name|BibEntry
name|e2
parameter_list|)
block|{
name|String
name|f1
decl_stmt|;
name|String
name|f2
decl_stmt|;
if|if
condition|(
name|fieldType
operator|==
name|FieldType
operator|.
name|TYPE
condition|)
block|{
comment|// Sort by type.
name|f1
operator|=
name|e1
operator|.
name|getType
argument_list|()
expr_stmt|;
name|f2
operator|=
name|e2
operator|.
name|getType
argument_list|()
expr_stmt|;
block|}
else|else
block|{
comment|// If the field is author or editor, we rearrange names so they are
comment|// sorted according to last name.
name|f1
operator|=
name|getField
argument_list|(
name|e1
argument_list|)
expr_stmt|;
name|f2
operator|=
name|getField
argument_list|(
name|e2
argument_list|)
expr_stmt|;
block|}
comment|// Catch all cases involving null:
if|if
condition|(
operator|(
name|f1
operator|==
literal|null
operator|)
operator|&&
operator|(
name|f2
operator|==
literal|null
operator|)
condition|)
block|{
return|return
literal|0
return|;
block|}
elseif|else
if|if
condition|(
name|f1
operator|==
literal|null
condition|)
block|{
return|return
name|multiplier
return|;
block|}
elseif|else
if|if
condition|(
name|f2
operator|==
literal|null
condition|)
block|{
return|return
operator|-
name|multiplier
return|;
block|}
comment|// Now we now that both f1 and f2 are != null
if|if
condition|(
name|fieldType
operator|==
name|FieldType
operator|.
name|NAME
condition|)
block|{
name|f1
operator|=
name|AuthorList
operator|.
name|fixAuthorForAlphabetization
argument_list|(
name|f1
argument_list|)
expr_stmt|;
name|f2
operator|=
name|AuthorList
operator|.
name|fixAuthorForAlphabetization
argument_list|(
name|f2
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|fieldType
operator|==
name|FieldType
operator|.
name|YEAR
condition|)
block|{
return|return
name|Integer
operator|.
name|compare
argument_list|(
name|YearUtil
operator|.
name|toFourDigitYearWithInts
argument_list|(
name|f1
argument_list|)
argument_list|,
name|YearUtil
operator|.
name|toFourDigitYearWithInts
argument_list|(
name|f2
argument_list|)
argument_list|)
operator|*
name|multiplier
return|;
block|}
elseif|else
if|if
condition|(
name|fieldType
operator|==
name|FieldType
operator|.
name|MONTH
condition|)
block|{
return|return
name|Integer
operator|.
name|compare
argument_list|(
name|MonthUtil
operator|.
name|getMonth
argument_list|(
name|f1
argument_list|)
operator|.
name|number
argument_list|,
name|MonthUtil
operator|.
name|getMonth
argument_list|(
name|f2
argument_list|)
operator|.
name|number
argument_list|)
operator|*
name|multiplier
return|;
block|}
if|if
condition|(
name|isNumeric
condition|)
block|{
name|Integer
name|i1
init|=
name|StringUtil
operator|.
name|intValueOfWithNull
argument_list|(
name|f1
argument_list|)
decl_stmt|;
name|Integer
name|i2
init|=
name|StringUtil
operator|.
name|intValueOfWithNull
argument_list|(
name|f2
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|i2
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|i1
operator|!=
literal|null
operator|)
condition|)
block|{
comment|// Ok, parsing was successful. Update f1 and f2:
return|return
name|i1
operator|.
name|compareTo
argument_list|(
name|i2
argument_list|)
operator|*
name|multiplier
return|;
block|}
elseif|else
if|if
condition|(
name|i1
operator|!=
literal|null
condition|)
block|{
comment|// The first one was parseable, but not the second one.
comment|// This means we consider one< two
return|return
operator|-
literal|1
operator|*
name|multiplier
return|;
block|}
elseif|else
if|if
condition|(
name|i2
operator|!=
literal|null
condition|)
block|{
comment|// The second one was parseable, but not the first one.
comment|// This means we consider one> two
return|return
literal|1
operator|*
name|multiplier
return|;
block|}
comment|// Else none of them were parseable, and we can fall back on comparing strings.
block|}
name|String
name|ours
init|=
name|f1
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|ENGLISH
argument_list|)
decl_stmt|;
name|String
name|theirs
init|=
name|f2
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|ENGLISH
argument_list|)
decl_stmt|;
return|return
name|COLLATOR
operator|.
name|compare
argument_list|(
name|ours
argument_list|,
name|theirs
argument_list|)
operator|*
name|multiplier
return|;
block|}
DECL|method|getField (BibEntry entry)
specifier|private
name|String
name|getField
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
for|for
control|(
name|String
name|aField
range|:
name|field
control|)
block|{
name|String
name|o
init|=
name|entry
operator|.
name|getFieldOrAlias
argument_list|(
name|aField
argument_list|)
decl_stmt|;
if|if
condition|(
name|o
operator|!=
literal|null
condition|)
block|{
return|return
name|o
return|;
block|}
block|}
return|return
literal|null
return|;
block|}
comment|/**      * Returns the field this Comparator compares by.      *      * @return The field name.      */
DECL|method|getFieldName ()
specifier|public
name|String
name|getFieldName
parameter_list|()
block|{
return|return
name|fieldName
return|;
block|}
block|}
end_class

end_unit

