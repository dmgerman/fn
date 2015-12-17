begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.search
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|search
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
name|Globals
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
name|JabRefPreferences
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
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Matcher
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
DECL|class|MatchesHighlighter
specifier|public
class|class
name|MatchesHighlighter
block|{
comment|// used at highlighting in preview area.
comment|// Color chosen similar to JTextComponent.getSelectionColor(), which is
comment|// used at highlighting words at the editor
DECL|field|HIGHLIGHT_COLOR
specifier|public
specifier|static
specifier|final
name|String
name|HIGHLIGHT_COLOR
init|=
literal|"#3399FF"
decl_stmt|;
comment|/**      * Will return the text that was called by the method with HTML tags to highlight each word the user has searched      * for and will skip the highlight process if the first Char isn't a letter or a digit.      *<p>      * This check is a quick hack to avoid highlighting of HTML tags It does not always work, but it does its job mostly      *      * @param text             This is a String in which we search for different words      * @param wordsToHighlight List of all words which must be highlighted      * @return String that was called by the method, with HTML Tags if a word was found      */
DECL|method|highlightWordsWithHTML (String text, List<String> wordsToHighlight)
specifier|public
specifier|static
name|String
name|highlightWordsWithHTML
parameter_list|(
name|String
name|text
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|wordsToHighlight
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|wordsToHighlight
argument_list|)
expr_stmt|;
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|text
argument_list|)
expr_stmt|;
if|if
condition|(
name|text
operator|.
name|isEmpty
argument_list|()
operator|||
name|wordsToHighlight
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
name|text
return|;
block|}
name|Optional
argument_list|<
name|Pattern
argument_list|>
name|patternForWords
init|=
name|getPatternForWords
argument_list|(
name|wordsToHighlight
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|SEARCH_REG_EXP
argument_list|)
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|SEARCH_CASE_SENSITIVE
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|patternForWords
operator|.
name|isPresent
argument_list|()
condition|)
block|{
return|return
name|text
return|;
block|}
name|Matcher
name|matcher
init|=
name|patternForWords
operator|.
name|get
argument_list|()
operator|.
name|matcher
argument_list|(
name|text
argument_list|)
decl_stmt|;
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|boolean
name|foundSomething
init|=
literal|false
decl_stmt|;
while|while
condition|(
name|matcher
operator|.
name|find
argument_list|()
condition|)
block|{
name|String
name|found
init|=
name|matcher
operator|.
name|group
argument_list|()
decl_stmt|;
comment|// color the search keyword
comment|// put first String Part and then html + word + html to a StringBuffer
name|matcher
operator|.
name|appendReplacement
argument_list|(
name|sb
argument_list|,
literal|"<span style=\"background-color:"
operator|+
name|HIGHLIGHT_COLOR
operator|+
literal|";\">"
operator|+
name|found
operator|+
literal|"</span>"
argument_list|)
expr_stmt|;
name|foundSomething
operator|=
literal|true
expr_stmt|;
block|}
if|if
condition|(
name|foundSomething
condition|)
block|{
name|matcher
operator|.
name|appendTail
argument_list|(
name|sb
argument_list|)
expr_stmt|;
name|text
operator|=
name|sb
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
return|return
name|text
return|;
block|}
comment|// Returns a regular expression pattern in the form (w1)|(w2)| ... wi are escaped if no regular expression search is enabled
DECL|method|getPatternForWords (List<String> words, boolean useRegex, boolean isCaseSensitive)
specifier|public
specifier|static
name|Optional
argument_list|<
name|Pattern
argument_list|>
name|getPatternForWords
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|words
parameter_list|,
name|boolean
name|useRegex
parameter_list|,
name|boolean
name|isCaseSensitive
parameter_list|)
block|{
if|if
condition|(
operator|(
name|words
operator|==
literal|null
operator|)
operator|||
name|words
operator|.
name|isEmpty
argument_list|()
operator|||
name|words
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|isEmpty
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
comment|// compile the words to a regular expression in the form (w1)|(w2)|(w3)
name|StringJoiner
name|joiner
init|=
operator|new
name|StringJoiner
argument_list|(
literal|")|("
argument_list|,
literal|"("
argument_list|,
literal|")"
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|word
range|:
name|words
control|)
block|{
name|joiner
operator|.
name|add
argument_list|(
name|useRegex
condition|?
name|word
else|:
name|Pattern
operator|.
name|quote
argument_list|(
name|word
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|String
name|searchPattern
init|=
name|joiner
operator|.
name|toString
argument_list|()
decl_stmt|;
if|if
condition|(
name|isCaseSensitive
condition|)
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|Pattern
operator|.
name|compile
argument_list|(
name|searchPattern
argument_list|)
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|Pattern
operator|.
name|compile
argument_list|(
name|searchPattern
argument_list|,
name|Pattern
operator|.
name|CASE_INSENSITIVE
argument_list|)
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

