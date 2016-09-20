begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.exporter
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|exporter
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
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
name|java
operator|.
name|util
operator|.
name|Objects
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
name|java
operator|.
name|util
operator|.
name|StringJoiner
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
name|cleanup
operator|.
name|FieldFormatterCleanup
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
name|formatter
operator|.
name|Formatter
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
name|formatter
operator|.
name|Formatters
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
name|formatter
operator|.
name|IdentityFormatter
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
name|formatter
operator|.
name|bibtexfields
operator|.
name|NormalizeMonthFormatter
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
name|formatter
operator|.
name|bibtexfields
operator|.
name|NormalizePagesFormatter
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
name|formatter
operator|.
name|bibtexfields
operator|.
name|OrdinalsToSuperscriptFormatter
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
name|OS
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
name|FieldChange
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
name|FieldName
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
name|metadata
operator|.
name|MetaData
import|;
end_import

begin_class
DECL|class|FieldFormatterCleanups
specifier|public
class|class
name|FieldFormatterCleanups
block|{
DECL|field|ENABLED
specifier|public
specifier|static
specifier|final
name|String
name|ENABLED
init|=
literal|"enabled"
decl_stmt|;
DECL|field|DISABLED
specifier|public
specifier|static
specifier|final
name|String
name|DISABLED
init|=
literal|"disabled"
decl_stmt|;
DECL|field|actions
specifier|private
specifier|final
name|List
argument_list|<
name|FieldFormatterCleanup
argument_list|>
name|actions
decl_stmt|;
DECL|field|availableFormatters
specifier|private
specifier|static
name|List
argument_list|<
name|Formatter
argument_list|>
name|availableFormatters
decl_stmt|;
DECL|field|enabled
specifier|private
specifier|final
name|boolean
name|enabled
decl_stmt|;
DECL|field|DEFAULT_SAVE_ACTIONS
specifier|public
specifier|static
specifier|final
name|FieldFormatterCleanups
name|DEFAULT_SAVE_ACTIONS
decl_stmt|;
static|static
block|{
name|availableFormatters
operator|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
expr_stmt|;
name|availableFormatters
operator|.
name|addAll
argument_list|(
name|Formatters
operator|.
name|ALL
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|FieldFormatterCleanup
argument_list|>
name|defaultFormatters
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|defaultFormatters
operator|.
name|add
argument_list|(
operator|new
name|FieldFormatterCleanup
argument_list|(
name|FieldName
operator|.
name|PAGES
argument_list|,
operator|new
name|NormalizePagesFormatter
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|defaultFormatters
operator|.
name|add
argument_list|(
operator|new
name|FieldFormatterCleanup
argument_list|(
name|FieldName
operator|.
name|MONTH
argument_list|,
operator|new
name|NormalizeMonthFormatter
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|defaultFormatters
operator|.
name|add
argument_list|(
operator|new
name|FieldFormatterCleanup
argument_list|(
name|FieldName
operator|.
name|BOOKTITLE
argument_list|,
operator|new
name|OrdinalsToSuperscriptFormatter
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|DEFAULT_SAVE_ACTIONS
operator|=
operator|new
name|FieldFormatterCleanups
argument_list|(
literal|false
argument_list|,
name|defaultFormatters
argument_list|)
expr_stmt|;
block|}
DECL|method|FieldFormatterCleanups (boolean enabled, String formatterString)
specifier|public
name|FieldFormatterCleanups
parameter_list|(
name|boolean
name|enabled
parameter_list|,
name|String
name|formatterString
parameter_list|)
block|{
name|this
argument_list|(
name|enabled
argument_list|,
name|parse
argument_list|(
name|formatterString
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|FieldFormatterCleanups (boolean enabled, List<FieldFormatterCleanup> actions)
specifier|public
name|FieldFormatterCleanups
parameter_list|(
name|boolean
name|enabled
parameter_list|,
name|List
argument_list|<
name|FieldFormatterCleanup
argument_list|>
name|actions
parameter_list|)
block|{
name|this
operator|.
name|enabled
operator|=
name|enabled
expr_stmt|;
name|this
operator|.
name|actions
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|actions
argument_list|)
expr_stmt|;
block|}
DECL|method|isEnabled ()
specifier|public
name|boolean
name|isEnabled
parameter_list|()
block|{
return|return
name|enabled
return|;
block|}
DECL|method|getConfiguredActions ()
specifier|public
name|List
argument_list|<
name|FieldFormatterCleanup
argument_list|>
name|getConfiguredActions
parameter_list|()
block|{
return|return
name|Collections
operator|.
name|unmodifiableList
argument_list|(
name|actions
argument_list|)
return|;
block|}
DECL|method|getAvailableFormatters ()
specifier|public
name|List
argument_list|<
name|Formatter
argument_list|>
name|getAvailableFormatters
parameter_list|()
block|{
return|return
name|Collections
operator|.
name|unmodifiableList
argument_list|(
name|availableFormatters
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|equals (Object o)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|this
operator|==
name|o
condition|)
block|{
return|return
literal|true
return|;
block|}
if|if
condition|(
operator|(
name|o
operator|==
literal|null
operator|)
operator|||
operator|(
name|getClass
argument_list|()
operator|!=
name|o
operator|.
name|getClass
argument_list|()
operator|)
condition|)
block|{
return|return
literal|false
return|;
block|}
name|FieldFormatterCleanups
name|that
init|=
operator|(
name|FieldFormatterCleanups
operator|)
name|o
decl_stmt|;
if|if
condition|(
name|enabled
operator|!=
name|that
operator|.
name|enabled
condition|)
block|{
return|return
literal|false
return|;
block|}
return|return
name|actions
operator|.
name|equals
argument_list|(
name|that
operator|.
name|actions
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|hashCode ()
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|Objects
operator|.
name|hash
argument_list|(
name|actions
argument_list|,
name|enabled
argument_list|)
return|;
block|}
DECL|method|parse (String formatterString)
specifier|public
specifier|static
name|List
argument_list|<
name|FieldFormatterCleanup
argument_list|>
name|parse
parameter_list|(
name|String
name|formatterString
parameter_list|)
block|{
if|if
condition|(
operator|(
name|formatterString
operator|==
literal|null
operator|)
operator|||
name|formatterString
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// no save actions defined in the meta data
return|return
operator|new
name|ArrayList
argument_list|<>
argument_list|()
return|;
block|}
name|List
argument_list|<
name|FieldFormatterCleanup
argument_list|>
name|actions
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
comment|//read concrete actions
name|int
name|startIndex
init|=
literal|0
decl_stmt|;
comment|// first remove all newlines for easier parsing
name|String
name|remainingString
init|=
name|formatterString
decl_stmt|;
name|remainingString
operator|=
name|StringUtil
operator|.
name|unifyLineBreaksToConfiguredLineBreaks
argument_list|(
name|remainingString
argument_list|)
operator|.
name|replaceAll
argument_list|(
name|OS
operator|.
name|NEWLINE
argument_list|,
literal|""
argument_list|)
expr_stmt|;
try|try
block|{
while|while
condition|(
name|startIndex
operator|<
name|formatterString
operator|.
name|length
argument_list|()
condition|)
block|{
comment|// read the field name
name|int
name|currentIndex
init|=
name|remainingString
operator|.
name|indexOf
argument_list|(
literal|'['
argument_list|)
decl_stmt|;
name|String
name|fieldKey
init|=
name|remainingString
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|currentIndex
argument_list|)
decl_stmt|;
name|int
name|endIndex
init|=
name|remainingString
operator|.
name|indexOf
argument_list|(
literal|']'
argument_list|)
decl_stmt|;
name|startIndex
operator|+=
name|endIndex
operator|+
literal|1
expr_stmt|;
comment|//read each formatter
name|int
name|tokenIndex
init|=
name|remainingString
operator|.
name|indexOf
argument_list|(
literal|','
argument_list|)
decl_stmt|;
do|do
block|{
name|boolean
name|doBreak
init|=
literal|false
decl_stmt|;
if|if
condition|(
operator|(
name|tokenIndex
operator|==
operator|-
literal|1
operator|)
operator|||
operator|(
name|tokenIndex
operator|>
name|endIndex
operator|)
condition|)
block|{
name|tokenIndex
operator|=
name|remainingString
operator|.
name|indexOf
argument_list|(
literal|']'
argument_list|)
expr_stmt|;
name|doBreak
operator|=
literal|true
expr_stmt|;
block|}
name|String
name|formatterKey
init|=
name|remainingString
operator|.
name|substring
argument_list|(
name|currentIndex
operator|+
literal|1
argument_list|,
name|tokenIndex
argument_list|)
decl_stmt|;
name|actions
operator|.
name|add
argument_list|(
operator|new
name|FieldFormatterCleanup
argument_list|(
name|fieldKey
argument_list|,
name|getFormatterFromString
argument_list|(
name|formatterKey
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|remainingString
operator|=
name|remainingString
operator|.
name|substring
argument_list|(
name|tokenIndex
operator|+
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
name|remainingString
operator|.
name|startsWith
argument_list|(
literal|"]"
argument_list|)
operator|||
name|doBreak
condition|)
block|{
break|break;
block|}
name|tokenIndex
operator|=
name|remainingString
operator|.
name|indexOf
argument_list|(
literal|','
argument_list|)
expr_stmt|;
name|currentIndex
operator|=
operator|-
literal|1
expr_stmt|;
block|}
do|while
condition|(
literal|true
condition|)
do|;
block|}
block|}
catch|catch
parameter_list|(
name|StringIndexOutOfBoundsException
name|ignore
parameter_list|)
block|{
comment|// if this exception occurs, the remaining part of the save actions string is invalid.
comment|// Thus we stop parsing and take what we have parsed until now
return|return
name|actions
return|;
block|}
return|return
name|actions
return|;
block|}
DECL|method|applySaveActions (BibEntry entry)
specifier|public
name|List
argument_list|<
name|FieldChange
argument_list|>
name|applySaveActions
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
if|if
condition|(
name|enabled
condition|)
block|{
return|return
name|applyAllActions
argument_list|(
name|entry
argument_list|)
return|;
block|}
else|else
block|{
return|return
operator|new
name|ArrayList
argument_list|<>
argument_list|()
return|;
block|}
block|}
DECL|method|applyAllActions (BibEntry entry)
specifier|private
name|List
argument_list|<
name|FieldChange
argument_list|>
name|applyAllActions
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
name|List
argument_list|<
name|FieldChange
argument_list|>
name|result
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|FieldFormatterCleanup
name|action
range|:
name|actions
control|)
block|{
name|result
operator|.
name|addAll
argument_list|(
name|action
operator|.
name|cleanup
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
DECL|method|getFormatterFromString (String formatterName)
specifier|private
specifier|static
name|Formatter
name|getFormatterFromString
parameter_list|(
name|String
name|formatterName
parameter_list|)
block|{
for|for
control|(
name|Formatter
name|formatter
range|:
name|availableFormatters
control|)
block|{
if|if
condition|(
name|formatterName
operator|.
name|equals
argument_list|(
name|formatter
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
return|return
name|formatter
return|;
block|}
block|}
return|return
operator|new
name|IdentityFormatter
argument_list|()
return|;
block|}
DECL|method|getMetaDataString (List<FieldFormatterCleanup> actionList)
specifier|private
specifier|static
name|String
name|getMetaDataString
parameter_list|(
name|List
argument_list|<
name|FieldFormatterCleanup
argument_list|>
name|actionList
parameter_list|)
block|{
comment|//first, group all formatters by the field for which they apply
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|groupedByField
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|FieldFormatterCleanup
name|cleanup
range|:
name|actionList
control|)
block|{
name|String
name|key
init|=
name|cleanup
operator|.
name|getField
argument_list|()
decl_stmt|;
comment|// add new list into the hashmap if needed
if|if
condition|(
operator|!
name|groupedByField
operator|.
name|containsKey
argument_list|(
name|key
argument_list|)
condition|)
block|{
name|groupedByField
operator|.
name|put
argument_list|(
name|key
argument_list|,
operator|new
name|ArrayList
argument_list|<>
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|//add the formatter to the map if it is not already there
name|List
argument_list|<
name|String
argument_list|>
name|formattersForKey
init|=
name|groupedByField
operator|.
name|get
argument_list|(
name|key
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|formattersForKey
operator|.
name|contains
argument_list|(
name|cleanup
operator|.
name|getFormatter
argument_list|()
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|formattersForKey
operator|.
name|add
argument_list|(
name|cleanup
operator|.
name|getFormatter
argument_list|()
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|// convert the contents of the hashmap into the correct serialization
name|StringBuilder
name|result
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|entry
range|:
name|groupedByField
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|result
operator|.
name|append
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
name|StringJoiner
name|joiner
init|=
operator|new
name|StringJoiner
argument_list|(
literal|","
argument_list|,
literal|"["
argument_list|,
literal|"]"
operator|+
name|OS
operator|.
name|NEWLINE
argument_list|)
decl_stmt|;
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|forEach
argument_list|(
name|joiner
operator|::
name|add
argument_list|)
expr_stmt|;
name|result
operator|.
name|append
argument_list|(
name|joiner
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|result
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|getAsStringList ()
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getAsStringList
parameter_list|()
block|{
name|List
argument_list|<
name|String
argument_list|>
name|stringRepresentation
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
if|if
condition|(
name|enabled
condition|)
block|{
name|stringRepresentation
operator|.
name|add
argument_list|(
name|ENABLED
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|stringRepresentation
operator|.
name|add
argument_list|(
name|DISABLED
argument_list|)
expr_stmt|;
block|}
name|String
name|formatterString
init|=
name|FieldFormatterCleanups
operator|.
name|getMetaDataString
argument_list|(
name|actions
argument_list|)
decl_stmt|;
name|stringRepresentation
operator|.
name|add
argument_list|(
name|formatterString
argument_list|)
expr_stmt|;
return|return
name|stringRepresentation
return|;
block|}
DECL|method|parse (List<String> formatterMetaList)
specifier|public
specifier|static
name|FieldFormatterCleanups
name|parse
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|formatterMetaList
parameter_list|)
block|{
if|if
condition|(
operator|(
name|formatterMetaList
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|formatterMetaList
operator|.
name|size
argument_list|()
operator|>=
literal|2
operator|)
condition|)
block|{
name|boolean
name|enablementStatus
init|=
name|ENABLED
operator|.
name|equals
argument_list|(
name|formatterMetaList
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|formatterString
init|=
name|formatterMetaList
operator|.
name|get
argument_list|(
literal|1
argument_list|)
decl_stmt|;
return|return
operator|new
name|FieldFormatterCleanups
argument_list|(
name|enablementStatus
argument_list|,
name|formatterString
argument_list|)
return|;
block|}
else|else
block|{
comment|// return default actions
return|return
name|FieldFormatterCleanups
operator|.
name|DEFAULT_SAVE_ACTIONS
return|;
block|}
block|}
DECL|method|fromMetaData (MetaData metadata)
specifier|public
specifier|static
name|Optional
argument_list|<
name|FieldFormatterCleanups
argument_list|>
name|fromMetaData
parameter_list|(
name|MetaData
name|metadata
parameter_list|)
block|{
return|return
name|metadata
operator|.
name|getSaveActions
argument_list|()
operator|.
name|map
argument_list|(
name|FieldFormatterCleanups
operator|::
name|parse
argument_list|)
return|;
block|}
block|}
end_class

end_unit

