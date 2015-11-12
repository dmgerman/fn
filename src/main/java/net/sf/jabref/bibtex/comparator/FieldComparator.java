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
name|gui
operator|.
name|BibtexFields
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
name|util
operator|.
name|Util
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
name|date
operator|.
name|YearUtil
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

begin_comment
comment|/**  *   * A comparator for BibtexEntry fields  *   * Initial Version:  *   * @author alver  * @version Date: Oct 13, 2005 Time: 10:10:04 PM To  *   * TODO: Testcases  *   */
end_comment

begin_class
DECL|class|FieldComparator
specifier|public
class|class
name|FieldComparator
implements|implements
name|Comparator
argument_list|<
name|BibtexEntry
argument_list|>
block|{
DECL|field|collator
specifier|private
specifier|static
name|Collator
name|collator
decl_stmt|;
static|static
block|{
try|try
block|{
name|FieldComparator
operator|.
name|collator
operator|=
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
name|replaceAll
argument_list|(
literal|"<'\u005f'"
argument_list|,
literal|"<' '<'\u005f'"
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ParseException
name|e
parameter_list|)
block|{
name|FieldComparator
operator|.
name|collator
operator|=
name|Collator
operator|.
name|getInstance
argument_list|()
expr_stmt|;
block|}
block|}
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
DECL|field|isNameField
specifier|private
specifier|final
name|boolean
name|isNameField
decl_stmt|;
DECL|field|isTypeHeader
specifier|private
specifier|final
name|boolean
name|isTypeHeader
decl_stmt|;
DECL|field|isYearField
specifier|private
specifier|final
name|boolean
name|isYearField
decl_stmt|;
DECL|field|isMonthField
specifier|private
specifier|final
name|boolean
name|isMonthField
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
name|field
expr_stmt|;
name|this
operator|.
name|field
operator|=
name|field
operator|.
name|split
argument_list|(
name|MainTableFormat
operator|.
name|COL_DEFINITION_FIELD_SEPARATOR
argument_list|)
expr_stmt|;
name|multiplier
operator|=
name|reversed
condition|?
operator|-
literal|1
else|:
literal|1
expr_stmt|;
name|isTypeHeader
operator|=
name|this
operator|.
name|field
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
name|BibtexEntry
operator|.
name|TYPE_HEADER
argument_list|)
expr_stmt|;
name|isNameField
operator|=
name|this
operator|.
name|field
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
literal|"author"
argument_list|)
operator|||
name|this
operator|.
name|field
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
literal|"editor"
argument_list|)
expr_stmt|;
name|isYearField
operator|=
name|this
operator|.
name|field
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
literal|"year"
argument_list|)
expr_stmt|;
name|isMonthField
operator|=
name|this
operator|.
name|field
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
literal|"month"
argument_list|)
expr_stmt|;
name|isNumeric
operator|=
name|BibtexFields
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
block|}
annotation|@
name|Override
DECL|method|compare (BibtexEntry e1, BibtexEntry e2)
specifier|public
name|int
name|compare
parameter_list|(
name|BibtexEntry
name|e1
parameter_list|,
name|BibtexEntry
name|e2
parameter_list|)
block|{
name|Object
name|f1
decl_stmt|;
name|Object
name|f2
decl_stmt|;
if|if
condition|(
name|isTypeHeader
condition|)
block|{
comment|// Sort by type.
name|f1
operator|=
name|e1
operator|.
name|getType
argument_list|()
operator|.
name|getName
argument_list|()
expr_stmt|;
name|f2
operator|=
name|e2
operator|.
name|getType
argument_list|()
operator|.
name|getName
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
comment|/*          * [ 1598777 ] Month sorting          *           * http://sourceforge.net/tracker/index.php?func=detail&aid=1598777&group_id=92314&atid=600306          */
name|int
name|localMultiplier
init|=
name|multiplier
decl_stmt|;
if|if
condition|(
name|isMonthField
condition|)
block|{
name|localMultiplier
operator|=
operator|-
name|localMultiplier
expr_stmt|;
block|}
comment|// Catch all cases involving null:
if|if
condition|(
name|f1
operator|==
literal|null
condition|)
block|{
return|return
name|f2
operator|==
literal|null
condition|?
literal|0
else|:
name|localMultiplier
return|;
block|}
if|if
condition|(
name|f2
operator|==
literal|null
condition|)
block|{
return|return
operator|-
name|localMultiplier
return|;
block|}
comment|// Now we now that both f1 and f2 are != null
if|if
condition|(
name|isNameField
condition|)
block|{
name|f1
operator|=
name|AuthorList
operator|.
name|fixAuthorForAlphabetization
argument_list|(
operator|(
name|String
operator|)
name|f1
argument_list|)
expr_stmt|;
name|f2
operator|=
name|AuthorList
operator|.
name|fixAuthorForAlphabetization
argument_list|(
operator|(
name|String
operator|)
name|f2
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|isYearField
condition|)
block|{
comment|/*              * [ 1285977 ] Impossible to properly sort a numeric field              *               * http://sourceforge.net/tracker/index.php?func=detail&aid=1285977&group_id=92314&atid=600307              */
name|f1
operator|=
name|YearUtil
operator|.
name|toFourDigitYear
argument_list|(
operator|(
name|String
operator|)
name|f1
argument_list|)
expr_stmt|;
name|f2
operator|=
name|YearUtil
operator|.
name|toFourDigitYear
argument_list|(
operator|(
name|String
operator|)
name|f2
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|isMonthField
condition|)
block|{
comment|/*              * [ 1535044 ] Month sorting              *               * http://sourceforge.net/tracker/index.php?func=detail&aid=1535044&group_id=92314&atid=600306              */
name|f1
operator|=
name|MonthUtil
operator|.
name|getMonth
argument_list|(
operator|(
name|String
operator|)
name|f1
argument_list|)
operator|.
name|number
expr_stmt|;
name|f2
operator|=
name|MonthUtil
operator|.
name|getMonth
argument_list|(
operator|(
name|String
operator|)
name|f2
argument_list|)
operator|.
name|number
expr_stmt|;
block|}
if|if
condition|(
name|isNumeric
condition|)
block|{
name|Integer
name|i1
init|=
literal|null
decl_stmt|;
name|Integer
name|i2
init|=
literal|null
decl_stmt|;
try|try
block|{
name|i1
operator|=
name|Util
operator|.
name|intValueOf
argument_list|(
operator|(
name|String
operator|)
name|f1
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|ex
parameter_list|)
block|{
comment|// Parsing failed.
block|}
try|try
block|{
name|i2
operator|=
name|Util
operator|.
name|intValueOf
argument_list|(
operator|(
name|String
operator|)
name|f2
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|ex
parameter_list|)
block|{
comment|// Parsing failed.
block|}
if|if
condition|(
name|i2
operator|!=
literal|null
operator|&&
name|i1
operator|!=
literal|null
condition|)
block|{
comment|// Ok, parsing was successful. Update f1 and f2:
name|f1
operator|=
name|i1
expr_stmt|;
name|f2
operator|=
name|i2
expr_stmt|;
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
name|f1
operator|=
name|i1
expr_stmt|;
name|f2
operator|=
name|i1
operator|+
literal|1
expr_stmt|;
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
name|f2
operator|=
name|i2
expr_stmt|;
name|f1
operator|=
name|i2
operator|+
literal|1
expr_stmt|;
block|}
comment|// Else none of them were parseable, and we can fall back on comparing strings.
block|}
name|int
name|result
decl_stmt|;
if|if
condition|(
name|f1
operator|instanceof
name|Integer
operator|&&
name|f2
operator|instanceof
name|Integer
condition|)
block|{
name|result
operator|=
operator|(
operator|(
name|Integer
operator|)
name|f1
operator|)
operator|.
name|compareTo
argument_list|(
operator|(
name|Integer
operator|)
name|f2
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|f2
operator|instanceof
name|Integer
condition|)
block|{
name|Integer
name|f1AsInteger
init|=
operator|new
name|Integer
argument_list|(
name|f1
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
name|result
operator|=
operator|-
name|f1AsInteger
operator|.
name|compareTo
argument_list|(
operator|(
name|Integer
operator|)
name|f2
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|f1
operator|instanceof
name|Integer
condition|)
block|{
name|Integer
name|f2AsInteger
init|=
operator|new
name|Integer
argument_list|(
name|f2
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
name|result
operator|=
operator|-
operator|(
operator|(
name|Integer
operator|)
name|f1
operator|)
operator|.
name|compareTo
argument_list|(
name|f2AsInteger
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|String
name|ours
init|=
operator|(
operator|(
name|String
operator|)
name|f1
operator|)
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
name|String
name|theirs
init|=
operator|(
operator|(
name|String
operator|)
name|f2
operator|)
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
name|result
operator|=
name|FieldComparator
operator|.
name|collator
operator|.
name|compare
argument_list|(
name|ours
argument_list|,
name|theirs
argument_list|)
expr_stmt|;
comment|//ours.compareTo(theirs);
block|}
return|return
name|result
operator|*
name|localMultiplier
return|;
block|}
DECL|method|getField (BibtexEntry entry)
specifier|private
name|Object
name|getField
parameter_list|(
name|BibtexEntry
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
name|Object
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
comment|/**      * Returns the field this Comparator compares by.      *       * @return The field name.      */
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

