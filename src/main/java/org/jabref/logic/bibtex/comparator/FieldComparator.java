begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.bibtex.comparator
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|bibtex
operator|.
name|comparator
package|;
end_package

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
name|Optional
import|;
end_import

begin_import
import|import
name|org
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
name|org
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
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|Month
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|field
operator|.
name|Field
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|field
operator|.
name|FieldProperty
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|field
operator|.
name|InternalField
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|field
operator|.
name|OrFields
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|field
operator|.
name|StandardField
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|metadata
operator|.
name|SaveOrderConfig
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|strings
operator|.
name|StringUtil
import|;
end_import

begin_comment
comment|/**  * A comparator for BibEntry fields  */
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
block|}
DECL|field|fields
specifier|private
specifier|final
name|OrFields
name|fields
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
DECL|method|FieldComparator (Field field)
specifier|public
name|FieldComparator
parameter_list|(
name|Field
name|field
parameter_list|)
block|{
name|this
argument_list|(
operator|new
name|OrFields
argument_list|(
name|field
argument_list|)
argument_list|,
literal|false
argument_list|)
expr_stmt|;
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
operator|new
name|OrFields
argument_list|(
name|sortCriterion
operator|.
name|field
argument_list|)
argument_list|,
name|sortCriterion
operator|.
name|descending
argument_list|)
expr_stmt|;
block|}
DECL|method|FieldComparator (OrFields fields, boolean descending)
specifier|public
name|FieldComparator
parameter_list|(
name|OrFields
name|fields
parameter_list|,
name|boolean
name|descending
parameter_list|)
block|{
name|this
operator|.
name|fields
operator|=
name|fields
expr_stmt|;
name|fieldType
operator|=
name|determineFieldType
argument_list|()
expr_stmt|;
name|isNumeric
operator|=
name|this
operator|.
name|fields
operator|.
name|getPrimary
argument_list|()
operator|.
name|isNumeric
argument_list|()
expr_stmt|;
name|multiplier
operator|=
name|descending
condition|?
operator|-
literal|1
else|:
literal|1
expr_stmt|;
block|}
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
DECL|method|determineFieldType ()
specifier|private
name|FieldType
name|determineFieldType
parameter_list|()
block|{
if|if
condition|(
name|InternalField
operator|.
name|TYPE_HEADER
operator|.
name|equals
argument_list|(
name|this
operator|.
name|fields
operator|.
name|getPrimary
argument_list|()
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
name|this
operator|.
name|fields
operator|.
name|getPrimary
argument_list|()
operator|.
name|getProperties
argument_list|()
operator|.
name|contains
argument_list|(
name|FieldProperty
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
name|StandardField
operator|.
name|YEAR
operator|.
name|equals
argument_list|(
name|this
operator|.
name|fields
operator|.
name|getPrimary
argument_list|()
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
name|StandardField
operator|.
name|MONTH
operator|.
name|equals
argument_list|(
name|this
operator|.
name|fields
operator|.
name|getPrimary
argument_list|()
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
DECL|method|getFieldValue (BibEntry entry)
specifier|private
name|String
name|getFieldValue
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
for|for
control|(
name|Field
name|aField
range|:
name|fields
control|)
block|{
name|Optional
argument_list|<
name|String
argument_list|>
name|o
init|=
name|entry
operator|.
name|getFieldOrAliasLatexFree
argument_list|(
name|aField
argument_list|)
decl_stmt|;
if|if
condition|(
name|o
operator|.
name|isPresent
argument_list|()
condition|)
block|{
return|return
name|o
operator|.
name|get
argument_list|()
return|;
block|}
block|}
return|return
literal|null
return|;
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
operator|.
name|getDisplayName
argument_list|()
expr_stmt|;
name|f2
operator|=
name|e2
operator|.
name|getType
argument_list|()
operator|.
name|getDisplayName
argument_list|()
expr_stmt|;
block|}
else|else
block|{
comment|// If the field is author or editor, we rearrange names so they are
comment|// sorted according to last name.
name|f1
operator|=
name|getFieldValue
argument_list|(
name|e1
argument_list|)
expr_stmt|;
name|f2
operator|=
name|getFieldValue
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
comment|// Now we know that both f1 and f2 are != null
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
name|Integer
name|f1year
init|=
name|StringUtil
operator|.
name|intValueOfOptional
argument_list|(
name|f1
argument_list|)
operator|.
name|orElse
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|Integer
name|f2year
init|=
name|StringUtil
operator|.
name|intValueOfOptional
argument_list|(
name|f2
argument_list|)
operator|.
name|orElse
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|int
name|comparisonResult
init|=
name|Integer
operator|.
name|compare
argument_list|(
name|f1year
argument_list|,
name|f2year
argument_list|)
decl_stmt|;
return|return
name|comparisonResult
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
name|int
name|month1
init|=
name|Month
operator|.
name|parse
argument_list|(
name|f1
argument_list|)
operator|.
name|map
argument_list|(
name|Month
operator|::
name|getNumber
argument_list|)
operator|.
name|orElse
argument_list|(
operator|-
literal|1
argument_list|)
decl_stmt|;
name|int
name|month2
init|=
name|Month
operator|.
name|parse
argument_list|(
name|f2
argument_list|)
operator|.
name|map
argument_list|(
name|Month
operator|::
name|getNumber
argument_list|)
operator|.
name|orElse
argument_list|(
operator|-
literal|1
argument_list|)
decl_stmt|;
return|return
name|Integer
operator|.
name|compare
argument_list|(
name|month1
argument_list|,
name|month2
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
name|Optional
argument_list|<
name|Integer
argument_list|>
name|i1
init|=
name|StringUtil
operator|.
name|intValueOfOptional
argument_list|(
name|f1
argument_list|)
decl_stmt|;
name|Optional
argument_list|<
name|Integer
argument_list|>
name|i2
init|=
name|StringUtil
operator|.
name|intValueOfOptional
argument_list|(
name|f2
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|i2
operator|.
name|isPresent
argument_list|()
operator|)
operator|&&
operator|(
name|i1
operator|.
name|isPresent
argument_list|()
operator|)
condition|)
block|{
comment|// Ok, parsing was successful. Update f1 and f2:
return|return
name|i1
operator|.
name|get
argument_list|()
operator|.
name|compareTo
argument_list|(
name|i2
operator|.
name|get
argument_list|()
argument_list|)
operator|*
name|multiplier
return|;
block|}
elseif|else
if|if
condition|(
name|i1
operator|.
name|isPresent
argument_list|()
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
operator|.
name|isPresent
argument_list|()
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
block|}
end_class

end_unit

