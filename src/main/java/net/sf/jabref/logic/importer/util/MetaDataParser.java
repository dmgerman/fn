begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.importer.util
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|util
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Reader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|StringReader
import|;
end_import

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
name|Optional
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
name|Cleanups
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
name|importer
operator|.
name|ParseException
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
name|database
operator|.
name|BibDatabaseMode
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
name|SaveOrderConfig
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_class
DECL|class|MetaDataParser
specifier|public
class|class
name|MetaDataParser
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|MetaDataParser
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * Parses the given data map and returns a new resulting {@link MetaData} instance.      */
DECL|method|parse (Map<String, String> data, Character keywordSeparator)
specifier|public
specifier|static
name|MetaData
name|parse
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|data
parameter_list|,
name|Character
name|keywordSeparator
parameter_list|)
throws|throws
name|ParseException
block|{
return|return
name|parse
argument_list|(
operator|new
name|MetaData
argument_list|()
argument_list|,
name|data
argument_list|,
name|keywordSeparator
argument_list|)
return|;
block|}
comment|/**      * Parses the data map and changes the given {@link MetaData} instance respectively.      */
DECL|method|parse (MetaData metaData, Map<String, String> data, Character keywordSeparator)
specifier|public
specifier|static
name|MetaData
name|parse
parameter_list|(
name|MetaData
name|metaData
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|data
parameter_list|,
name|Character
name|keywordSeparator
parameter_list|)
throws|throws
name|ParseException
block|{
name|List
argument_list|<
name|String
argument_list|>
name|defaultCiteKeyPattern
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|nonDefaultCiteKeyPatterns
init|=
operator|new
name|HashMap
argument_list|<>
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
name|String
argument_list|>
name|entry
range|:
name|data
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|value
init|=
name|getAsList
argument_list|(
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"selector_"
argument_list|)
condition|)
block|{
comment|// Ignore old content selector metadata
continue|continue;
block|}
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|startsWith
argument_list|(
name|MetaData
operator|.
name|PREFIX_KEYPATTERN
argument_list|)
condition|)
block|{
name|String
name|entryType
init|=
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|substring
argument_list|(
name|MetaData
operator|.
name|PREFIX_KEYPATTERN
operator|.
name|length
argument_list|()
argument_list|)
decl_stmt|;
name|nonDefaultCiteKeyPatterns
operator|.
name|put
argument_list|(
name|entryType
argument_list|,
name|Collections
operator|.
name|singletonList
argument_list|(
name|getSingleItem
argument_list|(
name|value
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
continue|continue;
block|}
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|startsWith
argument_list|(
name|MetaData
operator|.
name|FILE_DIRECTORY
operator|+
literal|'-'
argument_list|)
condition|)
block|{
comment|// The user name comes directly after "FILE_DIRECTORY-"
name|String
name|user
init|=
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|substring
argument_list|(
name|MetaData
operator|.
name|FILE_DIRECTORY
operator|.
name|length
argument_list|()
operator|+
literal|1
argument_list|)
decl_stmt|;
name|metaData
operator|.
name|setUserFileDirectory
argument_list|(
name|user
argument_list|,
name|getSingleItem
argument_list|(
name|value
argument_list|)
argument_list|)
expr_stmt|;
continue|continue;
block|}
switch|switch
condition|(
name|entry
operator|.
name|getKey
argument_list|()
condition|)
block|{
case|case
name|MetaData
operator|.
name|GROUPSTREE
case|:
name|metaData
operator|.
name|setGroups
argument_list|(
name|GroupsParser
operator|.
name|importGroups
argument_list|(
name|value
argument_list|,
name|keywordSeparator
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|MetaData
operator|.
name|SAVE_ACTIONS
case|:
name|metaData
operator|.
name|setSaveActions
argument_list|(
name|Cleanups
operator|.
name|parse
argument_list|(
name|value
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|MetaData
operator|.
name|DATABASE_TYPE
case|:
name|metaData
operator|.
name|setMode
argument_list|(
name|BibDatabaseMode
operator|.
name|parse
argument_list|(
name|getSingleItem
argument_list|(
name|value
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|MetaData
operator|.
name|KEYPATTERNDEFAULT
case|:
name|defaultCiteKeyPattern
operator|=
name|Collections
operator|.
name|singletonList
argument_list|(
name|getSingleItem
argument_list|(
name|value
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|MetaData
operator|.
name|PROTECTED_FLAG_META
case|:
if|if
condition|(
name|Boolean
operator|.
name|parseBoolean
argument_list|(
name|getSingleItem
argument_list|(
name|value
argument_list|)
argument_list|)
condition|)
block|{
name|metaData
operator|.
name|markAsProtected
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|metaData
operator|.
name|markAsNotProtected
argument_list|()
expr_stmt|;
block|}
break|break;
case|case
name|MetaData
operator|.
name|FILE_DIRECTORY
case|:
name|metaData
operator|.
name|setDefaultFileDirectory
argument_list|(
name|getSingleItem
argument_list|(
name|value
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|MetaData
operator|.
name|SAVE_ORDER_CONFIG
case|:
name|metaData
operator|.
name|setSaveOrderConfig
argument_list|(
name|SaveOrderConfig
operator|.
name|parse
argument_list|(
name|value
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
literal|"groupsversion"
case|:
case|case
literal|"groups"
case|:
comment|// These keys were used in previous JabRef versions, we will not support them anymore -> ignored
break|break;
block|}
block|}
if|if
condition|(
operator|!
name|defaultCiteKeyPattern
operator|.
name|isEmpty
argument_list|()
operator|||
operator|!
name|nonDefaultCiteKeyPatterns
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|metaData
operator|.
name|setCiteKeyPattern
argument_list|(
name|defaultCiteKeyPattern
argument_list|,
name|nonDefaultCiteKeyPatterns
argument_list|)
expr_stmt|;
block|}
return|return
name|metaData
return|;
block|}
comment|/**      * Returns the first item in the list.      * If the specified list does not contain exactly one item, then a {@link ParseException} will be thrown.      * @param value      * @return      */
DECL|method|getSingleItem (List<String> value)
specifier|private
specifier|static
name|String
name|getSingleItem
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|value
parameter_list|)
throws|throws
name|ParseException
block|{
if|if
condition|(
name|value
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
return|return
name|value
operator|.
name|get
argument_list|(
literal|0
argument_list|)
return|;
block|}
else|else
block|{
throw|throw
operator|new
name|ParseException
argument_list|(
literal|"Expected a single item but received "
operator|+
name|value
operator|.
name|toString
argument_list|()
argument_list|)
throw|;
block|}
block|}
DECL|method|getAsList (String value)
specifier|private
specifier|static
name|List
argument_list|<
name|String
argument_list|>
name|getAsList
parameter_list|(
name|String
name|value
parameter_list|)
throws|throws
name|ParseException
block|{
name|StringReader
name|valueReader
init|=
operator|new
name|StringReader
argument_list|(
name|value
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|orderedValue
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
comment|// We must allow for ; and \ in escape sequences.
try|try
block|{
name|Optional
argument_list|<
name|String
argument_list|>
name|unit
decl_stmt|;
while|while
condition|(
operator|(
name|unit
operator|=
name|getNextUnit
argument_list|(
name|valueReader
argument_list|)
operator|)
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|orderedValue
operator|.
name|add
argument_list|(
name|unit
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Weird error while parsing meta data."
argument_list|,
name|ex
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|ParseException
argument_list|(
literal|"Weird error while parsing meta data."
argument_list|,
name|ex
argument_list|)
throw|;
block|}
return|return
name|orderedValue
return|;
block|}
comment|/**      * Reads the next unit. Units are delimited by ';' (MetaData.SEPARATOR_CHARACTER).      */
DECL|method|getNextUnit (Reader reader)
specifier|private
specifier|static
name|Optional
argument_list|<
name|String
argument_list|>
name|getNextUnit
parameter_list|(
name|Reader
name|reader
parameter_list|)
throws|throws
name|IOException
block|{
name|int
name|c
decl_stmt|;
name|boolean
name|escape
init|=
literal|false
decl_stmt|;
name|StringBuilder
name|res
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
while|while
condition|(
operator|(
name|c
operator|=
name|reader
operator|.
name|read
argument_list|()
operator|)
operator|!=
operator|-
literal|1
condition|)
block|{
if|if
condition|(
name|escape
condition|)
block|{
name|res
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
expr_stmt|;
name|escape
operator|=
literal|false
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|c
operator|==
name|MetaData
operator|.
name|ESCAPE_CHARACTER
condition|)
block|{
name|escape
operator|=
literal|true
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|c
operator|==
name|MetaData
operator|.
name|SEPARATOR_CHARACTER
condition|)
block|{
break|break;
block|}
else|else
block|{
name|res
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|res
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|res
operator|.
name|toString
argument_list|()
argument_list|)
return|;
block|}
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
block|}
end_class

end_unit

