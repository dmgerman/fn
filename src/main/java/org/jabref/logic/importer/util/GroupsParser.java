begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.importer.util
package|package
name|org
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
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|groups
operator|.
name|DefaultGroupsFactory
import|;
end_import

begin_import
import|import
name|org
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
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|l10n
operator|.
name|Localization
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|MetadataSerializationConfiguration
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|strings
operator|.
name|QuotedStringTokenizer
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
name|groups
operator|.
name|AbstractGroup
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
name|groups
operator|.
name|AutomaticKeywordGroup
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
name|groups
operator|.
name|AutomaticPersonsGroup
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
name|groups
operator|.
name|ExplicitGroup
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
name|groups
operator|.
name|GroupHierarchyType
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
name|groups
operator|.
name|GroupTreeNode
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
name|groups
operator|.
name|KeywordGroup
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
name|groups
operator|.
name|RegexKeywordGroup
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
name|groups
operator|.
name|SearchGroup
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
name|groups
operator|.
name|WordKeywordGroup
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
comment|/**  * Converts string representation of groups to a parsed {@link GroupTreeNode}.  */
end_comment

begin_class
DECL|class|GroupsParser
specifier|public
class|class
name|GroupsParser
block|{
DECL|method|GroupsParser ()
specifier|private
name|GroupsParser
parameter_list|()
block|{     }
DECL|method|importGroups (List<String> orderedData, Character keywordSeparator)
specifier|public
specifier|static
name|GroupTreeNode
name|importGroups
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|orderedData
parameter_list|,
name|Character
name|keywordSeparator
parameter_list|)
throws|throws
name|ParseException
block|{
try|try
block|{
name|GroupTreeNode
name|cursor
init|=
literal|null
decl_stmt|;
name|GroupTreeNode
name|root
init|=
literal|null
decl_stmt|;
for|for
control|(
name|String
name|string
range|:
name|orderedData
control|)
block|{
comment|// This allows to read databases that have been modified by, e.g., BibDesk
name|string
operator|=
name|string
operator|.
name|trim
argument_list|()
expr_stmt|;
if|if
condition|(
name|string
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
continue|continue;
block|}
name|int
name|spaceIndex
init|=
name|string
operator|.
name|indexOf
argument_list|(
literal|' '
argument_list|)
decl_stmt|;
if|if
condition|(
name|spaceIndex
operator|<=
literal|0
condition|)
block|{
throw|throw
operator|new
name|ParseException
argument_list|(
literal|"Expected \""
operator|+
name|string
operator|+
literal|"\" to contain whitespace"
argument_list|)
throw|;
block|}
name|int
name|level
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|string
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|spaceIndex
argument_list|)
argument_list|)
decl_stmt|;
name|AbstractGroup
name|group
init|=
name|GroupsParser
operator|.
name|fromString
argument_list|(
name|string
operator|.
name|substring
argument_list|(
name|spaceIndex
operator|+
literal|1
argument_list|)
argument_list|,
name|keywordSeparator
argument_list|)
decl_stmt|;
name|GroupTreeNode
name|newNode
init|=
name|GroupTreeNode
operator|.
name|fromGroup
argument_list|(
name|group
argument_list|)
decl_stmt|;
if|if
condition|(
name|cursor
operator|==
literal|null
condition|)
block|{
comment|// create new root
name|cursor
operator|=
name|newNode
expr_stmt|;
name|root
operator|=
name|cursor
expr_stmt|;
block|}
else|else
block|{
comment|// insert at desired location
while|while
condition|(
operator|(
name|level
operator|<=
name|cursor
operator|.
name|getLevel
argument_list|()
operator|)
operator|&&
operator|(
name|cursor
operator|.
name|getParent
argument_list|()
operator|.
name|isPresent
argument_list|()
operator|)
condition|)
block|{
name|cursor
operator|=
name|cursor
operator|.
name|getParent
argument_list|()
operator|.
name|get
argument_list|()
expr_stmt|;
block|}
name|cursor
operator|.
name|addChild
argument_list|(
name|newNode
argument_list|)
expr_stmt|;
name|cursor
operator|=
name|newNode
expr_stmt|;
block|}
block|}
return|return
name|root
return|;
block|}
catch|catch
parameter_list|(
name|ParseException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ParseException
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Group tree could not be parsed. If you save the BibTeX library, all groups will be lost."
argument_list|)
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
comment|/**      * Re-create a group instance from a textual representation.      *      * @param s The result from the group's toString() method.      * @return New instance of the encoded group.      * @throws ParseException If an error occurred and a group could not be created,      *                        e.g. due to a malformed regular expression.      */
DECL|method|fromString (String s, Character keywordSeparator)
specifier|public
specifier|static
name|AbstractGroup
name|fromString
parameter_list|(
name|String
name|s
parameter_list|,
name|Character
name|keywordSeparator
parameter_list|)
throws|throws
name|ParseException
block|{
if|if
condition|(
name|s
operator|.
name|startsWith
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|KEYWORD_GROUP_ID
argument_list|)
condition|)
block|{
return|return
name|GroupsParser
operator|.
name|keywordGroupFromString
argument_list|(
name|s
argument_list|,
name|keywordSeparator
argument_list|)
return|;
block|}
if|if
condition|(
name|s
operator|.
name|startsWith
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|ALL_ENTRIES_GROUP_ID
argument_list|)
condition|)
block|{
return|return
name|GroupsParser
operator|.
name|allEntriesGroupFromString
argument_list|(
name|s
argument_list|)
return|;
block|}
if|if
condition|(
name|s
operator|.
name|startsWith
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|SEARCH_GROUP_ID
argument_list|)
condition|)
block|{
return|return
name|GroupsParser
operator|.
name|searchGroupFromString
argument_list|(
name|s
argument_list|)
return|;
block|}
if|if
condition|(
name|s
operator|.
name|startsWith
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|EXPLICIT_GROUP_ID
argument_list|)
condition|)
block|{
return|return
name|GroupsParser
operator|.
name|explicitGroupFromString
argument_list|(
name|s
argument_list|,
name|keywordSeparator
argument_list|)
return|;
block|}
if|if
condition|(
name|s
operator|.
name|startsWith
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|LEGACY_EXPLICIT_GROUP_ID
argument_list|)
condition|)
block|{
return|return
name|GroupsParser
operator|.
name|legacyExplicitGroupFromString
argument_list|(
name|s
argument_list|,
name|keywordSeparator
argument_list|)
return|;
block|}
if|if
condition|(
name|s
operator|.
name|startsWith
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|AUTOMATIC_PERSONS_GROUP_ID
argument_list|)
condition|)
block|{
return|return
name|GroupsParser
operator|.
name|automaticPersonsGroupFromString
argument_list|(
name|s
argument_list|)
return|;
block|}
if|if
condition|(
name|s
operator|.
name|startsWith
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|AUTOMATIC_KEYWORD_GROUP_ID
argument_list|)
condition|)
block|{
return|return
name|GroupsParser
operator|.
name|automaticKeywordGroupFromString
argument_list|(
name|s
argument_list|)
return|;
block|}
throw|throw
operator|new
name|ParseException
argument_list|(
literal|"Unknown group: "
operator|+
name|s
argument_list|)
throw|;
block|}
DECL|method|automaticPersonsGroupFromString (String string)
specifier|private
specifier|static
name|AbstractGroup
name|automaticPersonsGroupFromString
parameter_list|(
name|String
name|string
parameter_list|)
block|{
if|if
condition|(
operator|!
name|string
operator|.
name|startsWith
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|AUTOMATIC_PERSONS_GROUP_ID
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"KeywordGroup cannot be created from \""
operator|+
name|string
operator|+
literal|"\"."
argument_list|)
throw|;
block|}
name|QuotedStringTokenizer
name|tok
init|=
operator|new
name|QuotedStringTokenizer
argument_list|(
name|string
operator|.
name|substring
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|AUTOMATIC_PERSONS_GROUP_ID
operator|.
name|length
argument_list|()
argument_list|)
argument_list|,
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
argument_list|,
name|MetadataSerializationConfiguration
operator|.
name|GROUP_QUOTE_CHAR
argument_list|)
decl_stmt|;
name|String
name|name
init|=
name|StringUtil
operator|.
name|unquote
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
argument_list|,
name|MetadataSerializationConfiguration
operator|.
name|GROUP_QUOTE_CHAR
argument_list|)
decl_stmt|;
name|GroupHierarchyType
name|context
init|=
name|GroupHierarchyType
operator|.
name|getByNumberOrDefault
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|field
init|=
name|StringUtil
operator|.
name|unquote
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
argument_list|,
name|MetadataSerializationConfiguration
operator|.
name|GROUP_QUOTE_CHAR
argument_list|)
decl_stmt|;
name|AutomaticPersonsGroup
name|newGroup
init|=
operator|new
name|AutomaticPersonsGroup
argument_list|(
name|name
argument_list|,
name|context
argument_list|,
name|field
argument_list|)
decl_stmt|;
name|addGroupDetails
argument_list|(
name|tok
argument_list|,
name|newGroup
argument_list|)
expr_stmt|;
return|return
name|newGroup
return|;
block|}
DECL|method|automaticKeywordGroupFromString (String string)
specifier|private
specifier|static
name|AbstractGroup
name|automaticKeywordGroupFromString
parameter_list|(
name|String
name|string
parameter_list|)
block|{
if|if
condition|(
operator|!
name|string
operator|.
name|startsWith
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|AUTOMATIC_KEYWORD_GROUP_ID
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"KeywordGroup cannot be created from \""
operator|+
name|string
operator|+
literal|"\"."
argument_list|)
throw|;
block|}
name|QuotedStringTokenizer
name|tok
init|=
operator|new
name|QuotedStringTokenizer
argument_list|(
name|string
operator|.
name|substring
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|AUTOMATIC_KEYWORD_GROUP_ID
operator|.
name|length
argument_list|()
argument_list|)
argument_list|,
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
argument_list|,
name|MetadataSerializationConfiguration
operator|.
name|GROUP_QUOTE_CHAR
argument_list|)
decl_stmt|;
name|String
name|name
init|=
name|StringUtil
operator|.
name|unquote
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
argument_list|,
name|MetadataSerializationConfiguration
operator|.
name|GROUP_QUOTE_CHAR
argument_list|)
decl_stmt|;
name|GroupHierarchyType
name|context
init|=
name|GroupHierarchyType
operator|.
name|getByNumberOrDefault
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|field
init|=
name|StringUtil
operator|.
name|unquote
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
argument_list|,
name|MetadataSerializationConfiguration
operator|.
name|GROUP_QUOTE_CHAR
argument_list|)
decl_stmt|;
name|Character
name|delimiter
init|=
name|tok
operator|.
name|nextToken
argument_list|()
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|Character
name|hierarchicalDelimiter
init|=
name|tok
operator|.
name|nextToken
argument_list|()
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|AutomaticKeywordGroup
name|newGroup
init|=
operator|new
name|AutomaticKeywordGroup
argument_list|(
name|name
argument_list|,
name|context
argument_list|,
name|field
argument_list|,
name|delimiter
argument_list|,
name|hierarchicalDelimiter
argument_list|)
decl_stmt|;
name|addGroupDetails
argument_list|(
name|tok
argument_list|,
name|newGroup
argument_list|)
expr_stmt|;
return|return
name|newGroup
return|;
block|}
comment|/**      * Parses s and recreates the KeywordGroup from it.      *      * @param s The String representation obtained from      *          KeywordGroup.toString()      */
DECL|method|keywordGroupFromString (String s, Character keywordSeparator)
specifier|private
specifier|static
name|KeywordGroup
name|keywordGroupFromString
parameter_list|(
name|String
name|s
parameter_list|,
name|Character
name|keywordSeparator
parameter_list|)
throws|throws
name|ParseException
block|{
if|if
condition|(
operator|!
name|s
operator|.
name|startsWith
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|KEYWORD_GROUP_ID
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"KeywordGroup cannot be created from \""
operator|+
name|s
operator|+
literal|"\"."
argument_list|)
throw|;
block|}
name|QuotedStringTokenizer
name|tok
init|=
operator|new
name|QuotedStringTokenizer
argument_list|(
name|s
operator|.
name|substring
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|KEYWORD_GROUP_ID
operator|.
name|length
argument_list|()
argument_list|)
argument_list|,
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
argument_list|,
name|MetadataSerializationConfiguration
operator|.
name|GROUP_QUOTE_CHAR
argument_list|)
decl_stmt|;
name|String
name|name
init|=
name|StringUtil
operator|.
name|unquote
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
argument_list|,
name|MetadataSerializationConfiguration
operator|.
name|GROUP_QUOTE_CHAR
argument_list|)
decl_stmt|;
name|GroupHierarchyType
name|context
init|=
name|GroupHierarchyType
operator|.
name|getByNumberOrDefault
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|field
init|=
name|StringUtil
operator|.
name|unquote
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
argument_list|,
name|MetadataSerializationConfiguration
operator|.
name|GROUP_QUOTE_CHAR
argument_list|)
decl_stmt|;
name|String
name|expression
init|=
name|StringUtil
operator|.
name|unquote
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
argument_list|,
name|MetadataSerializationConfiguration
operator|.
name|GROUP_QUOTE_CHAR
argument_list|)
decl_stmt|;
name|boolean
name|caseSensitive
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
argument_list|)
operator|==
literal|1
decl_stmt|;
name|boolean
name|regExp
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
argument_list|)
operator|==
literal|1
decl_stmt|;
name|KeywordGroup
name|newGroup
decl_stmt|;
if|if
condition|(
name|regExp
condition|)
block|{
name|newGroup
operator|=
operator|new
name|RegexKeywordGroup
argument_list|(
name|name
argument_list|,
name|context
argument_list|,
name|field
argument_list|,
name|expression
argument_list|,
name|caseSensitive
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|newGroup
operator|=
operator|new
name|WordKeywordGroup
argument_list|(
name|name
argument_list|,
name|context
argument_list|,
name|field
argument_list|,
name|expression
argument_list|,
name|caseSensitive
argument_list|,
name|keywordSeparator
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
name|addGroupDetails
argument_list|(
name|tok
argument_list|,
name|newGroup
argument_list|)
expr_stmt|;
return|return
name|newGroup
return|;
block|}
DECL|method|explicitGroupFromString (String input, Character keywordSeparator)
specifier|private
specifier|static
name|ExplicitGroup
name|explicitGroupFromString
parameter_list|(
name|String
name|input
parameter_list|,
name|Character
name|keywordSeparator
parameter_list|)
throws|throws
name|ParseException
block|{
if|if
condition|(
operator|!
name|input
operator|.
name|startsWith
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|EXPLICIT_GROUP_ID
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"ExplicitGroup cannot be created from \""
operator|+
name|input
operator|+
literal|"\"."
argument_list|)
throw|;
block|}
name|QuotedStringTokenizer
name|tok
init|=
operator|new
name|QuotedStringTokenizer
argument_list|(
name|input
operator|.
name|substring
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|EXPLICIT_GROUP_ID
operator|.
name|length
argument_list|()
argument_list|)
argument_list|,
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
argument_list|,
name|MetadataSerializationConfiguration
operator|.
name|GROUP_QUOTE_CHAR
argument_list|)
decl_stmt|;
name|String
name|name
init|=
name|tok
operator|.
name|nextToken
argument_list|()
decl_stmt|;
try|try
block|{
name|int
name|context
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
argument_list|)
decl_stmt|;
name|ExplicitGroup
name|newGroup
init|=
operator|new
name|ExplicitGroup
argument_list|(
name|name
argument_list|,
name|GroupHierarchyType
operator|.
name|getByNumberOrDefault
argument_list|(
name|context
argument_list|)
argument_list|,
name|keywordSeparator
argument_list|)
decl_stmt|;
name|addGroupDetails
argument_list|(
name|tok
argument_list|,
name|newGroup
argument_list|)
expr_stmt|;
return|return
name|newGroup
return|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|exception
parameter_list|)
block|{
throw|throw
operator|new
name|ParseException
argument_list|(
literal|"Could not parse context in "
operator|+
name|input
argument_list|)
throw|;
block|}
block|}
DECL|method|legacyExplicitGroupFromString (String input, Character keywordSeparator)
specifier|private
specifier|static
name|ExplicitGroup
name|legacyExplicitGroupFromString
parameter_list|(
name|String
name|input
parameter_list|,
name|Character
name|keywordSeparator
parameter_list|)
throws|throws
name|ParseException
block|{
if|if
condition|(
operator|!
name|input
operator|.
name|startsWith
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|LEGACY_EXPLICIT_GROUP_ID
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"ExplicitGroup cannot be created from \""
operator|+
name|input
operator|+
literal|"\"."
argument_list|)
throw|;
block|}
name|QuotedStringTokenizer
name|tok
init|=
operator|new
name|QuotedStringTokenizer
argument_list|(
name|input
operator|.
name|substring
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|LEGACY_EXPLICIT_GROUP_ID
operator|.
name|length
argument_list|()
argument_list|)
argument_list|,
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
argument_list|,
name|MetadataSerializationConfiguration
operator|.
name|GROUP_QUOTE_CHAR
argument_list|)
decl_stmt|;
name|String
name|name
init|=
name|tok
operator|.
name|nextToken
argument_list|()
decl_stmt|;
try|try
block|{
name|int
name|context
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
argument_list|)
decl_stmt|;
name|ExplicitGroup
name|newGroup
init|=
operator|new
name|ExplicitGroup
argument_list|(
name|name
argument_list|,
name|GroupHierarchyType
operator|.
name|getByNumberOrDefault
argument_list|(
name|context
argument_list|)
argument_list|,
name|keywordSeparator
argument_list|)
decl_stmt|;
name|GroupsParser
operator|.
name|addLegacyEntryKeys
argument_list|(
name|tok
argument_list|,
name|newGroup
argument_list|)
expr_stmt|;
return|return
name|newGroup
return|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|exception
parameter_list|)
block|{
throw|throw
operator|new
name|ParseException
argument_list|(
literal|"Could not parse context in "
operator|+
name|input
argument_list|)
throw|;
block|}
block|}
comment|/**      * Called only when created fromString.      * JabRef used to store the entries of an explicit group in the serialization, e.g.      *  ExplicitGroup:GroupName\;0\;Key1\;Key2\;;      * This method exists for backwards compatibility.      */
DECL|method|addLegacyEntryKeys (QuotedStringTokenizer tok, ExplicitGroup group)
specifier|private
specifier|static
name|void
name|addLegacyEntryKeys
parameter_list|(
name|QuotedStringTokenizer
name|tok
parameter_list|,
name|ExplicitGroup
name|group
parameter_list|)
block|{
while|while
condition|(
name|tok
operator|.
name|hasMoreTokens
argument_list|()
condition|)
block|{
name|String
name|key
init|=
name|StringUtil
operator|.
name|unquote
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
argument_list|,
name|MetadataSerializationConfiguration
operator|.
name|GROUP_QUOTE_CHAR
argument_list|)
decl_stmt|;
name|group
operator|.
name|addLegacyEntryKey
argument_list|(
name|key
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|allEntriesGroupFromString (String s)
specifier|private
specifier|static
name|AbstractGroup
name|allEntriesGroupFromString
parameter_list|(
name|String
name|s
parameter_list|)
block|{
if|if
condition|(
operator|!
name|s
operator|.
name|startsWith
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|ALL_ENTRIES_GROUP_ID
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"AllEntriesGroup cannot be created from \""
operator|+
name|s
operator|+
literal|"\"."
argument_list|)
throw|;
block|}
return|return
name|DefaultGroupsFactory
operator|.
name|getAllEntriesGroup
argument_list|()
return|;
block|}
comment|/**      * Parses s and recreates the SearchGroup from it.      *      * @param s The String representation obtained from      *          SearchGroup.toString(), or null if incompatible      */
DECL|method|searchGroupFromString (String s)
specifier|private
specifier|static
name|AbstractGroup
name|searchGroupFromString
parameter_list|(
name|String
name|s
parameter_list|)
block|{
if|if
condition|(
operator|!
name|s
operator|.
name|startsWith
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|SEARCH_GROUP_ID
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"SearchGroup cannot be created from \""
operator|+
name|s
operator|+
literal|"\"."
argument_list|)
throw|;
block|}
name|QuotedStringTokenizer
name|tok
init|=
operator|new
name|QuotedStringTokenizer
argument_list|(
name|s
operator|.
name|substring
argument_list|(
name|MetadataSerializationConfiguration
operator|.
name|SEARCH_GROUP_ID
operator|.
name|length
argument_list|()
argument_list|)
argument_list|,
name|MetadataSerializationConfiguration
operator|.
name|GROUP_UNIT_SEPARATOR
argument_list|,
name|MetadataSerializationConfiguration
operator|.
name|GROUP_QUOTE_CHAR
argument_list|)
decl_stmt|;
name|String
name|name
init|=
name|tok
operator|.
name|nextToken
argument_list|()
decl_stmt|;
name|int
name|context
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
argument_list|)
decl_stmt|;
name|String
name|expression
init|=
name|tok
operator|.
name|nextToken
argument_list|()
decl_stmt|;
name|boolean
name|caseSensitive
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
argument_list|)
operator|==
literal|1
decl_stmt|;
name|boolean
name|regExp
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
argument_list|)
operator|==
literal|1
decl_stmt|;
comment|// version 0 contained 4 additional booleans to specify search
comment|// fields; these are ignored now, all fields are always searched
return|return
operator|new
name|SearchGroup
argument_list|(
name|StringUtil
operator|.
name|unquote
argument_list|(
name|name
argument_list|,
name|MetadataSerializationConfiguration
operator|.
name|GROUP_QUOTE_CHAR
argument_list|)
argument_list|,
name|GroupHierarchyType
operator|.
name|getByNumberOrDefault
argument_list|(
name|context
argument_list|)
argument_list|,
name|StringUtil
operator|.
name|unquote
argument_list|(
name|expression
argument_list|,
name|MetadataSerializationConfiguration
operator|.
name|GROUP_QUOTE_CHAR
argument_list|)
argument_list|,
name|caseSensitive
argument_list|,
name|regExp
argument_list|)
return|;
block|}
DECL|method|addGroupDetails (QuotedStringTokenizer tokenizer, AbstractGroup group)
specifier|private
specifier|static
name|void
name|addGroupDetails
parameter_list|(
name|QuotedStringTokenizer
name|tokenizer
parameter_list|,
name|AbstractGroup
name|group
parameter_list|)
block|{
if|if
condition|(
name|tokenizer
operator|.
name|hasMoreTokens
argument_list|()
condition|)
block|{
name|group
operator|.
name|setExpanded
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|tokenizer
operator|.
name|nextToken
argument_list|()
argument_list|)
operator|==
literal|1
argument_list|)
expr_stmt|;
name|group
operator|.
name|setColor
argument_list|(
name|tokenizer
operator|.
name|nextToken
argument_list|()
argument_list|)
expr_stmt|;
name|group
operator|.
name|setIconName
argument_list|(
name|tokenizer
operator|.
name|nextToken
argument_list|()
argument_list|)
expr_stmt|;
name|group
operator|.
name|setDescription
argument_list|(
name|tokenizer
operator|.
name|nextToken
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

