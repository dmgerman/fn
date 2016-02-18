begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.exporter
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|exporter
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
name|MetaData
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
name|BibtexFieldFormatters
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
name|CaseChangers
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
name|util
operator|.
name|*
import|;
end_import

begin_class
DECL|class|SaveActions
specifier|public
class|class
name|SaveActions
block|{
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
name|List
argument_list|<
name|Formatter
argument_list|>
name|availableFormatters
decl_stmt|;
DECL|field|META_KEY
specifier|public
specifier|static
specifier|final
name|String
name|META_KEY
init|=
literal|"saveActions"
decl_stmt|;
DECL|field|enabled
specifier|private
name|boolean
name|enabled
decl_stmt|;
DECL|method|SaveActions (MetaData metaData)
specifier|public
name|SaveActions
parameter_list|(
name|MetaData
name|metaData
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|metaData
argument_list|)
expr_stmt|;
name|actions
operator|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
expr_stmt|;
name|setAvailableFormatters
argument_list|()
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|formatters
init|=
name|metaData
operator|.
name|getData
argument_list|(
name|META_KEY
argument_list|)
decl_stmt|;
if|if
condition|(
name|formatters
operator|==
literal|null
condition|)
block|{
comment|// no save actions defined in the meta data
return|return;
block|}
else|else
block|{
name|parseEnabledStatus
argument_list|(
name|formatters
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|parseSaveActions
argument_list|(
name|formatters
operator|.
name|get
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
return|return
literal|true
return|;
if|if
condition|(
name|o
operator|==
literal|null
operator|||
name|getClass
argument_list|()
operator|!=
name|o
operator|.
name|getClass
argument_list|()
condition|)
return|return
literal|false
return|;
name|SaveActions
name|that
init|=
operator|(
name|SaveActions
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
return|return
literal|false
return|;
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
name|int
name|result
init|=
name|actions
operator|.
name|hashCode
argument_list|()
decl_stmt|;
name|result
operator|=
literal|31
operator|*
name|result
operator|+
operator|(
name|enabled
condition|?
literal|1
else|:
literal|0
operator|)
expr_stmt|;
return|return
name|result
return|;
block|}
DECL|method|parseSaveActions (String formatterString)
specifier|private
name|void
name|parseSaveActions
parameter_list|(
name|String
name|formatterString
parameter_list|)
block|{
comment|//read concrete actions
name|int
name|startIndex
init|=
literal|0
decl_stmt|;
name|int
name|index
init|=
literal|0
decl_stmt|;
name|String
name|remainingString
init|=
name|formatterString
decl_stmt|;
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
name|index
operator|=
name|remainingString
operator|.
name|indexOf
argument_list|(
literal|"["
argument_list|)
expr_stmt|;
name|String
name|fieldKey
init|=
name|remainingString
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|index
argument_list|)
decl_stmt|;
name|int
name|endIndex
init|=
name|remainingString
operator|.
name|indexOf
argument_list|(
literal|"]"
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
literal|","
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
name|tokenIndex
operator|==
operator|-
literal|1
operator|||
name|tokenIndex
operator|>
name|endIndex
condition|)
block|{
name|tokenIndex
operator|=
name|remainingString
operator|.
name|indexOf
argument_list|(
literal|"]"
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
name|index
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
literal|","
argument_list|)
expr_stmt|;
name|index
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
return|return;
block|}
block|}
DECL|method|parseEnabledStatus (String enablementString)
specifier|private
name|void
name|parseEnabledStatus
parameter_list|(
name|String
name|enablementString
parameter_list|)
block|{
comment|//read if save actions should be enabled
name|enabled
operator|=
literal|"enabled"
operator|.
name|equals
argument_list|(
name|enablementString
argument_list|)
expr_stmt|;
block|}
DECL|method|setAvailableFormatters ()
specifier|private
name|void
name|setAvailableFormatters
parameter_list|()
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
name|BibtexFieldFormatters
operator|.
name|ALL
argument_list|)
expr_stmt|;
name|availableFormatters
operator|.
name|addAll
argument_list|(
name|CaseChangers
operator|.
name|ALL
argument_list|)
expr_stmt|;
block|}
DECL|method|applySaveActions (BibEntry entry)
specifier|public
name|BibEntry
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
name|applyAllActions
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
return|return
name|entry
return|;
block|}
DECL|method|applyAllActions (BibEntry entry)
specifier|private
name|void
name|applyAllActions
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
for|for
control|(
name|FieldFormatterCleanup
name|action
range|:
name|actions
control|)
block|{
name|action
operator|.
name|cleanup
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|getFormatterFromString (String formatterName)
specifier|private
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
specifier|public
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
block|}
end_class

end_unit

