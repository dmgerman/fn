begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.groups
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|groups
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
name|List
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
name|Set
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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|PatternSyntaxException
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
name|util
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
name|logic
operator|.
name|l10n
operator|.
name|Localization
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
name|QuotedStringTokenizer
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
name|EntryUtil
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
name|preferences
operator|.
name|JabRefPreferences
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

begin_comment
comment|/**  * @author jzieren  */
end_comment

begin_class
DECL|class|KeywordGroup
specifier|public
class|class
name|KeywordGroup
extends|extends
name|AbstractGroup
block|{
DECL|field|ID
specifier|public
specifier|static
specifier|final
name|String
name|ID
init|=
literal|"KeywordGroup:"
decl_stmt|;
DECL|field|searchField
specifier|private
specifier|final
name|String
name|searchField
decl_stmt|;
DECL|field|searchExpression
specifier|private
specifier|final
name|String
name|searchExpression
decl_stmt|;
DECL|field|caseSensitive
specifier|private
specifier|final
name|boolean
name|caseSensitive
decl_stmt|;
DECL|field|regExp
specifier|private
specifier|final
name|boolean
name|regExp
decl_stmt|;
DECL|field|pattern
specifier|private
name|Pattern
name|pattern
decl_stmt|;
DECL|field|searchWords
specifier|private
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|searchWords
decl_stmt|;
DECL|field|jabRefPreferences
specifier|protected
specifier|final
name|JabRefPreferences
name|jabRefPreferences
decl_stmt|;
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
name|KeywordGroup
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * Creates a KeywordGroup with the specified properties.      */
DECL|method|KeywordGroup (String name, String searchField, String searchExpression, boolean caseSensitive, boolean regExp, GroupHierarchyType context, JabRefPreferences jabRefPreferences)
specifier|public
name|KeywordGroup
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|searchField
parameter_list|,
name|String
name|searchExpression
parameter_list|,
name|boolean
name|caseSensitive
parameter_list|,
name|boolean
name|regExp
parameter_list|,
name|GroupHierarchyType
name|context
parameter_list|,
name|JabRefPreferences
name|jabRefPreferences
parameter_list|)
throws|throws
name|ParseException
block|{
name|super
argument_list|(
name|name
argument_list|,
name|context
argument_list|)
expr_stmt|;
name|this
operator|.
name|searchField
operator|=
name|searchField
expr_stmt|;
name|this
operator|.
name|searchExpression
operator|=
name|searchExpression
expr_stmt|;
name|this
operator|.
name|caseSensitive
operator|=
name|caseSensitive
expr_stmt|;
name|this
operator|.
name|regExp
operator|=
name|regExp
expr_stmt|;
if|if
condition|(
name|this
operator|.
name|regExp
condition|)
block|{
name|compilePattern
argument_list|()
expr_stmt|;
block|}
name|this
operator|.
name|jabRefPreferences
operator|=
name|jabRefPreferences
expr_stmt|;
name|this
operator|.
name|searchWords
operator|=
name|EntryUtil
operator|.
name|getStringAsWords
argument_list|(
name|searchExpression
argument_list|)
expr_stmt|;
block|}
DECL|method|compilePattern ()
specifier|private
name|void
name|compilePattern
parameter_list|()
throws|throws
name|ParseException
block|{
try|try
block|{
name|pattern
operator|=
name|caseSensitive
condition|?
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\b"
operator|+
name|searchExpression
operator|+
literal|"\\b"
argument_list|)
else|:
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\b"
operator|+
name|searchExpression
operator|+
literal|"\\b"
argument_list|,
name|Pattern
operator|.
name|CASE_INSENSITIVE
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|PatternSyntaxException
name|exception
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
literal|"Syntax error in regular-expression pattern"
argument_list|,
name|searchExpression
argument_list|)
argument_list|)
throw|;
block|}
block|}
comment|/**      * Parses s and recreates the KeywordGroup from it.      *      * @param s The String representation obtained from      *          KeywordGroup.toString()      */
DECL|method|fromString (String s, JabRefPreferences jabRefPreferences)
specifier|public
specifier|static
name|AbstractGroup
name|fromString
parameter_list|(
name|String
name|s
parameter_list|,
name|JabRefPreferences
name|jabRefPreferences
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
name|KeywordGroup
operator|.
name|ID
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
name|KeywordGroup
operator|.
name|ID
operator|.
name|length
argument_list|()
argument_list|)
argument_list|,
name|AbstractGroup
operator|.
name|SEPARATOR
argument_list|,
name|AbstractGroup
operator|.
name|QUOTE_CHAR
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
name|field
init|=
name|tok
operator|.
name|nextToken
argument_list|()
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
return|return
operator|new
name|KeywordGroup
argument_list|(
name|StringUtil
operator|.
name|unquote
argument_list|(
name|name
argument_list|,
name|AbstractGroup
operator|.
name|QUOTE_CHAR
argument_list|)
argument_list|,
name|StringUtil
operator|.
name|unquote
argument_list|(
name|field
argument_list|,
name|AbstractGroup
operator|.
name|QUOTE_CHAR
argument_list|)
argument_list|,
name|StringUtil
operator|.
name|unquote
argument_list|(
name|expression
argument_list|,
name|AbstractGroup
operator|.
name|QUOTE_CHAR
argument_list|)
argument_list|,
name|caseSensitive
argument_list|,
name|regExp
argument_list|,
name|GroupHierarchyType
operator|.
name|getByNumber
argument_list|(
name|context
argument_list|)
argument_list|,
name|jabRefPreferences
argument_list|)
return|;
block|}
comment|/**      * Returns a String representation of this object that can be used to      * reconstruct it.      */
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|KeywordGroup
operator|.
name|ID
operator|+
name|StringUtil
operator|.
name|quote
argument_list|(
name|getName
argument_list|()
argument_list|,
name|AbstractGroup
operator|.
name|SEPARATOR
argument_list|,
name|AbstractGroup
operator|.
name|QUOTE_CHAR
argument_list|)
operator|+
name|AbstractGroup
operator|.
name|SEPARATOR
operator|+
name|getContext
argument_list|()
operator|.
name|ordinal
argument_list|()
operator|+
name|AbstractGroup
operator|.
name|SEPARATOR
operator|+
name|StringUtil
operator|.
name|quote
argument_list|(
name|searchField
argument_list|,
name|AbstractGroup
operator|.
name|SEPARATOR
argument_list|,
name|AbstractGroup
operator|.
name|QUOTE_CHAR
argument_list|)
operator|+
name|AbstractGroup
operator|.
name|SEPARATOR
operator|+
name|StringUtil
operator|.
name|quote
argument_list|(
name|searchExpression
argument_list|,
name|AbstractGroup
operator|.
name|SEPARATOR
argument_list|,
name|AbstractGroup
operator|.
name|QUOTE_CHAR
argument_list|)
operator|+
name|AbstractGroup
operator|.
name|SEPARATOR
operator|+
name|StringUtil
operator|.
name|booleanToBinaryString
argument_list|(
name|caseSensitive
argument_list|)
operator|+
name|AbstractGroup
operator|.
name|SEPARATOR
operator|+
name|StringUtil
operator|.
name|booleanToBinaryString
argument_list|(
name|regExp
argument_list|)
operator|+
name|AbstractGroup
operator|.
name|SEPARATOR
return|;
block|}
annotation|@
name|Override
DECL|method|supportsAdd ()
specifier|public
name|boolean
name|supportsAdd
parameter_list|()
block|{
return|return
operator|!
name|regExp
return|;
block|}
annotation|@
name|Override
DECL|method|supportsRemove ()
specifier|public
name|boolean
name|supportsRemove
parameter_list|()
block|{
return|return
operator|!
name|regExp
return|;
block|}
annotation|@
name|Override
DECL|method|add (List<BibEntry> entriesToAdd)
specifier|public
name|Optional
argument_list|<
name|EntriesGroupChange
argument_list|>
name|add
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entriesToAdd
parameter_list|)
block|{
if|if
condition|(
operator|!
name|supportsAdd
argument_list|()
condition|)
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
if|if
condition|(
operator|(
name|entriesToAdd
operator|!=
literal|null
operator|)
operator|&&
operator|!
operator|(
name|entriesToAdd
operator|.
name|isEmpty
argument_list|()
operator|)
condition|)
block|{
name|List
argument_list|<
name|FieldChange
argument_list|>
name|changes
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|boolean
name|modified
init|=
literal|false
decl_stmt|;
for|for
control|(
name|BibEntry
name|entry
range|:
name|entriesToAdd
control|)
block|{
if|if
condition|(
operator|!
name|contains
argument_list|(
name|entry
argument_list|)
condition|)
block|{
name|String
name|oldContent
init|=
name|entry
operator|.
name|getFieldOptional
argument_list|(
name|searchField
argument_list|)
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
decl_stmt|;
name|String
name|pre
init|=
name|jabRefPreferences
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|KEYWORD_SEPARATOR
argument_list|)
decl_stmt|;
name|String
name|newContent
init|=
operator|(
name|oldContent
operator|==
literal|null
condition|?
literal|""
else|:
name|oldContent
operator|+
name|pre
operator|)
operator|+
name|searchExpression
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|searchField
argument_list|,
name|newContent
argument_list|)
expr_stmt|;
comment|// Store change information.
name|changes
operator|.
name|add
argument_list|(
operator|new
name|FieldChange
argument_list|(
name|entry
argument_list|,
name|searchField
argument_list|,
name|oldContent
argument_list|,
name|newContent
argument_list|)
argument_list|)
expr_stmt|;
name|modified
operator|=
literal|true
expr_stmt|;
block|}
block|}
return|return
name|modified
condition|?
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|EntriesGroupChange
argument_list|(
name|changes
argument_list|)
argument_list|)
else|:
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|remove (List<BibEntry> entriesToRemove)
specifier|public
name|Optional
argument_list|<
name|EntriesGroupChange
argument_list|>
name|remove
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entriesToRemove
parameter_list|)
block|{
if|if
condition|(
operator|!
name|supportsRemove
argument_list|()
condition|)
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
if|if
condition|(
operator|(
name|entriesToRemove
operator|!=
literal|null
operator|)
operator|&&
operator|(
operator|!
name|entriesToRemove
operator|.
name|isEmpty
argument_list|()
operator|)
condition|)
block|{
name|List
argument_list|<
name|FieldChange
argument_list|>
name|changes
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|boolean
name|modified
init|=
literal|false
decl_stmt|;
for|for
control|(
name|BibEntry
name|entry
range|:
name|entriesToRemove
control|)
block|{
if|if
condition|(
name|contains
argument_list|(
name|entry
argument_list|)
condition|)
block|{
name|String
name|oldContent
init|=
name|entry
operator|.
name|getFieldOptional
argument_list|(
name|searchField
argument_list|)
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
decl_stmt|;
name|removeMatches
argument_list|(
name|entry
argument_list|)
expr_stmt|;
comment|// Store change information.
name|changes
operator|.
name|add
argument_list|(
operator|new
name|FieldChange
argument_list|(
name|entry
argument_list|,
name|searchField
argument_list|,
name|oldContent
argument_list|,
name|entry
operator|.
name|getFieldOptional
argument_list|(
name|searchField
argument_list|)
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|modified
operator|=
literal|true
expr_stmt|;
block|}
block|}
return|return
name|modified
condition|?
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|EntriesGroupChange
argument_list|(
name|changes
argument_list|)
argument_list|)
else|:
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
return|return
name|Optional
operator|.
name|empty
argument_list|()
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
operator|!
operator|(
name|o
operator|instanceof
name|KeywordGroup
operator|)
condition|)
block|{
return|return
literal|false
return|;
block|}
name|KeywordGroup
name|other
init|=
operator|(
name|KeywordGroup
operator|)
name|o
decl_stmt|;
return|return
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|other
operator|.
name|getName
argument_list|()
argument_list|)
operator|&&
name|searchField
operator|.
name|equals
argument_list|(
name|other
operator|.
name|searchField
argument_list|)
operator|&&
name|searchExpression
operator|.
name|equals
argument_list|(
name|other
operator|.
name|searchExpression
argument_list|)
operator|&&
operator|(
name|caseSensitive
operator|==
name|other
operator|.
name|caseSensitive
operator|)
operator|&&
operator|(
name|regExp
operator|==
name|other
operator|.
name|regExp
operator|)
operator|&&
operator|(
name|getHierarchicalContext
argument_list|()
operator|==
name|other
operator|.
name|getHierarchicalContext
argument_list|()
operator|)
return|;
block|}
annotation|@
name|Override
DECL|method|contains (BibEntry entry)
specifier|public
name|boolean
name|contains
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
if|if
condition|(
name|regExp
condition|)
block|{
name|Optional
argument_list|<
name|String
argument_list|>
name|content
init|=
name|entry
operator|.
name|getFieldOptional
argument_list|(
name|searchField
argument_list|)
decl_stmt|;
return|return
name|content
operator|.
name|map
argument_list|(
name|value
lambda|->
name|pattern
operator|.
name|matcher
argument_list|(
name|value
argument_list|)
operator|.
name|find
argument_list|()
argument_list|)
operator|.
name|orElse
argument_list|(
literal|false
argument_list|)
return|;
block|}
name|Set
argument_list|<
name|String
argument_list|>
name|words
init|=
name|entry
operator|.
name|getFieldAsWords
argument_list|(
name|searchField
argument_list|)
decl_stmt|;
if|if
condition|(
name|words
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
literal|false
return|;
block|}
if|if
condition|(
name|caseSensitive
condition|)
block|{
return|return
name|words
operator|.
name|containsAll
argument_list|(
name|searchWords
argument_list|)
return|;
block|}
return|return
name|containsCaseInsensitive
argument_list|(
name|searchWords
argument_list|,
name|words
argument_list|)
return|;
block|}
DECL|method|containsCaseInsensitive (List<String> searchText, Set<String> words)
specifier|private
name|boolean
name|containsCaseInsensitive
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|searchText
parameter_list|,
name|Set
argument_list|<
name|String
argument_list|>
name|words
parameter_list|)
block|{
for|for
control|(
name|String
name|searchWord
range|:
name|searchText
control|)
block|{
if|if
condition|(
operator|!
name|containsCaseInsensitive
argument_list|(
name|searchWord
argument_list|,
name|words
argument_list|)
condition|)
block|{
return|return
literal|false
return|;
block|}
block|}
return|return
literal|true
return|;
block|}
DECL|method|containsCaseInsensitive (String text, Set<String> words)
specifier|private
name|boolean
name|containsCaseInsensitive
parameter_list|(
name|String
name|text
parameter_list|,
name|Set
argument_list|<
name|String
argument_list|>
name|words
parameter_list|)
block|{
for|for
control|(
name|String
name|word
range|:
name|words
control|)
block|{
if|if
condition|(
name|word
operator|.
name|equalsIgnoreCase
argument_list|(
name|text
argument_list|)
condition|)
block|{
return|return
literal|true
return|;
block|}
block|}
return|return
literal|false
return|;
block|}
comment|/**      * Look for the given non-regexp string in another string, but check whether a      * match concerns a complete word, not part of a word.      *      * @param word The word to look for.      * @param text The string to look in.      * @return true if the word was found, false otherwise.      */
DECL|method|containsWord (String word, String text)
specifier|public
specifier|static
name|boolean
name|containsWord
parameter_list|(
name|String
name|word
parameter_list|,
name|String
name|text
parameter_list|)
block|{
name|int
name|piv
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|piv
operator|<
name|text
operator|.
name|length
argument_list|()
condition|)
block|{
name|int
name|index
init|=
name|text
operator|.
name|indexOf
argument_list|(
name|word
argument_list|,
name|piv
argument_list|)
decl_stmt|;
if|if
condition|(
name|index
operator|<
literal|0
condition|)
block|{
return|return
literal|false
return|;
block|}
comment|// Found a match. See if it is a complete word:
if|if
condition|(
operator|(
operator|(
name|index
operator|==
literal|0
operator|)
operator|||
operator|!
name|Character
operator|.
name|isLetterOrDigit
argument_list|(
name|text
operator|.
name|charAt
argument_list|(
name|index
operator|-
literal|1
argument_list|)
argument_list|)
operator|)
operator|&&
operator|(
operator|(
operator|(
name|index
operator|+
name|word
operator|.
name|length
argument_list|()
operator|)
operator|==
name|text
operator|.
name|length
argument_list|()
operator|)
operator|||
operator|!
name|Character
operator|.
name|isLetterOrDigit
argument_list|(
name|text
operator|.
name|charAt
argument_list|(
name|index
operator|+
name|word
operator|.
name|length
argument_list|()
argument_list|)
argument_list|)
operator|)
condition|)
block|{
return|return
literal|true
return|;
block|}
else|else
block|{
name|piv
operator|=
name|index
operator|+
literal|1
expr_stmt|;
block|}
block|}
return|return
literal|false
return|;
block|}
comment|/**      * Removes matches of searchString in the entry's field. This is only      * possible if the search expression is not a regExp.      */
DECL|method|removeMatches (BibEntry entry)
specifier|private
name|void
name|removeMatches
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
name|entry
operator|.
name|getFieldOptional
argument_list|(
name|searchField
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|content
lambda|->
block|{
name|StringBuffer
name|sbOrig
init|=
operator|new
name|StringBuffer
argument_list|(
name|content
argument_list|)
decl_stmt|;
name|StringBuffer
name|sbLower
init|=
operator|new
name|StringBuffer
argument_list|(
name|content
operator|.
name|toLowerCase
argument_list|()
argument_list|)
decl_stmt|;
name|StringBuffer
name|haystack
init|=
name|caseSensitive
condition|?
name|sbOrig
else|:
name|sbLower
decl_stmt|;
name|String
name|needle
init|=
name|caseSensitive
condition|?
name|searchExpression
else|:
name|searchExpression
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
name|int
name|i
decl_stmt|;
name|int
name|j
decl_stmt|;
name|int
name|k
decl_stmt|;
specifier|final
name|String
name|separator
init|=
name|jabRefPreferences
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|KEYWORD_SEPARATOR
argument_list|)
decl_stmt|;
while|while
condition|(
operator|(
name|i
operator|=
name|haystack
operator|.
name|indexOf
argument_list|(
name|needle
argument_list|)
operator|)
operator|>=
literal|0
condition|)
block|{
name|sbOrig
operator|.
name|replace
argument_list|(
name|i
argument_list|,
name|i
operator|+
name|needle
operator|.
name|length
argument_list|()
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|sbLower
operator|.
name|replace
argument_list|(
name|i
argument_list|,
name|i
operator|+
name|needle
operator|.
name|length
argument_list|()
argument_list|,
literal|""
argument_list|)
expr_stmt|;
comment|// reduce spaces at i to 1
name|j
operator|=
name|i
expr_stmt|;
name|k
operator|=
name|i
expr_stmt|;
while|while
condition|(
operator|(
operator|(
name|j
operator|-
literal|1
operator|)
operator|>=
literal|0
operator|)
operator|&&
operator|(
name|separator
operator|.
name|indexOf
argument_list|(
name|haystack
operator|.
name|charAt
argument_list|(
name|j
operator|-
literal|1
argument_list|)
argument_list|)
operator|>=
literal|0
operator|)
condition|)
block|{
operator|--
name|j
expr_stmt|;
block|}
while|while
condition|(
operator|(
name|k
operator|<
name|haystack
operator|.
name|length
argument_list|()
operator|)
operator|&&
operator|(
name|separator
operator|.
name|indexOf
argument_list|(
name|haystack
operator|.
name|charAt
argument_list|(
name|k
argument_list|)
argument_list|)
operator|>=
literal|0
operator|)
condition|)
block|{
operator|++
name|k
expr_stmt|;
block|}
name|sbOrig
operator|.
name|replace
argument_list|(
name|j
argument_list|,
name|k
argument_list|,
operator|(
name|j
operator|>=
literal|0
operator|)
operator|&&
operator|(
name|k
operator|<
name|sbOrig
operator|.
name|length
argument_list|()
operator|)
condition|?
name|separator
else|:
literal|""
argument_list|)
expr_stmt|;
name|sbLower
operator|.
name|replace
argument_list|(
name|j
argument_list|,
name|k
argument_list|,
operator|(
name|j
operator|>=
literal|0
operator|)
operator|&&
operator|(
name|k
operator|<
name|sbOrig
operator|.
name|length
argument_list|()
operator|)
condition|?
name|separator
else|:
literal|""
argument_list|)
expr_stmt|;
block|}
name|String
name|result
init|=
name|sbOrig
operator|.
name|toString
argument_list|()
operator|.
name|trim
argument_list|()
decl_stmt|;
if|if
condition|(
name|result
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|entry
operator|.
name|clearField
argument_list|(
name|searchField
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|entry
operator|.
name|setField
argument_list|(
name|searchField
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|deepCopy ()
specifier|public
name|AbstractGroup
name|deepCopy
parameter_list|()
block|{
try|try
block|{
return|return
operator|new
name|KeywordGroup
argument_list|(
name|getName
argument_list|()
argument_list|,
name|searchField
argument_list|,
name|searchExpression
argument_list|,
name|caseSensitive
argument_list|,
name|regExp
argument_list|,
name|getContext
argument_list|()
argument_list|,
name|jabRefPreferences
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|ParseException
name|exception
parameter_list|)
block|{
comment|// this should never happen, because the constructor obviously succeeded in creating _this_ instance!
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Internal error in KeywordGroup.deepCopy(). "
operator|+
literal|"Please report this on https://github.com/JabRef/jabref/issues"
argument_list|,
name|exception
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
DECL|method|isCaseSensitive ()
specifier|public
name|boolean
name|isCaseSensitive
parameter_list|()
block|{
return|return
name|caseSensitive
return|;
block|}
DECL|method|isRegExp ()
specifier|public
name|boolean
name|isRegExp
parameter_list|()
block|{
return|return
name|regExp
return|;
block|}
DECL|method|getSearchExpression ()
specifier|public
name|String
name|getSearchExpression
parameter_list|()
block|{
return|return
name|searchExpression
return|;
block|}
DECL|method|getSearchField ()
specifier|public
name|String
name|getSearchField
parameter_list|()
block|{
return|return
name|searchField
return|;
block|}
annotation|@
name|Override
DECL|method|isDynamic ()
specifier|public
name|boolean
name|isDynamic
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
annotation|@
name|Override
DECL|method|getDescription ()
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|KeywordGroup
operator|.
name|getDescriptionForPreview
argument_list|(
name|searchField
argument_list|,
name|searchExpression
argument_list|,
name|caseSensitive
argument_list|,
name|regExp
argument_list|)
return|;
block|}
DECL|method|getDescriptionForPreview (String field, String expr, boolean caseSensitive, boolean regExp)
specifier|public
specifier|static
name|String
name|getDescriptionForPreview
parameter_list|(
name|String
name|field
parameter_list|,
name|String
name|expr
parameter_list|,
name|boolean
name|caseSensitive
parameter_list|,
name|boolean
name|regExp
parameter_list|)
block|{
name|String
name|header
init|=
name|regExp
condition|?
name|Localization
operator|.
name|lang
argument_list|(
literal|"This group contains entries whose<b>%0</b> field contains the regular expression<b>%1</b>"
argument_list|,
name|field
argument_list|,
name|StringUtil
operator|.
name|quoteForHTML
argument_list|(
name|expr
argument_list|)
argument_list|)
else|:
name|Localization
operator|.
name|lang
argument_list|(
literal|"This group contains entries whose<b>%0</b> field contains the keyword<b>%1</b>"
argument_list|,
name|field
argument_list|,
name|StringUtil
operator|.
name|quoteForHTML
argument_list|(
name|expr
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|caseSensitiveText
init|=
name|caseSensitive
condition|?
name|Localization
operator|.
name|lang
argument_list|(
literal|"case sensitive"
argument_list|)
else|:
name|Localization
operator|.
name|lang
argument_list|(
literal|"case insensitive"
argument_list|)
decl_stmt|;
name|String
name|footer
init|=
name|regExp
condition|?
name|Localization
operator|.
name|lang
argument_list|(
literal|"Entries cannot be manually assigned to or removed from this group."
argument_list|)
else|:
name|Localization
operator|.
name|lang
argument_list|(
literal|"Additionally, entries whose<b>%0</b> field does not contain "
operator|+
literal|"<b>%1</b> can be assigned manually to this group by selecting them "
operator|+
literal|"then using either drag and drop or the context menu. "
operator|+
literal|"This process adds the term<b>%1</b> to "
operator|+
literal|"each entry's<b>%0</b> field. "
operator|+
literal|"Entries can be removed manually from this group by selecting them "
operator|+
literal|"then using the context menu. "
operator|+
literal|"This process removes the term<b>%1</b> from "
operator|+
literal|"each entry's<b>%0</b> field."
argument_list|,
name|field
argument_list|,
name|StringUtil
operator|.
name|quoteForHTML
argument_list|(
name|expr
argument_list|)
argument_list|)
decl_stmt|;
return|return
name|String
operator|.
name|format
argument_list|(
literal|"%s (%s). %s"
argument_list|,
name|header
argument_list|,
name|caseSensitiveText
argument_list|,
name|footer
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getShortDescription (boolean showDynamic)
specifier|public
name|String
name|getShortDescription
parameter_list|(
name|boolean
name|showDynamic
parameter_list|)
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"<b>"
argument_list|)
expr_stmt|;
if|if
condition|(
name|showDynamic
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"<i>"
argument_list|)
operator|.
name|append
argument_list|(
name|StringUtil
operator|.
name|quoteForHTML
argument_list|(
name|getName
argument_list|()
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|"</i>"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|StringUtil
operator|.
name|quoteForHTML
argument_list|(
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
literal|"</b> - "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"dynamic group"
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"<b>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|searchField
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"</b>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"contains"
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"<b>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|StringUtil
operator|.
name|quoteForHTML
argument_list|(
name|searchExpression
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"</b>)"
argument_list|)
expr_stmt|;
switch|switch
condition|(
name|getHierarchicalContext
argument_list|()
condition|)
block|{
case|case
name|INCLUDING
case|:
name|sb
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"includes subgroups"
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|REFINING
case|:
name|sb
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"refines supergroup"
argument_list|)
argument_list|)
expr_stmt|;
break|break;
default|default:
break|break;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|getTypeId ()
specifier|public
name|String
name|getTypeId
parameter_list|()
block|{
return|return
name|KeywordGroup
operator|.
name|ID
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
comment|// TODO Auto-generated method stub
return|return
name|super
operator|.
name|hashCode
argument_list|()
return|;
block|}
block|}
end_class

end_unit

