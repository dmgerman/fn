begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.formatter.bibtexfields
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|formatter
operator|.
name|bibtexfields
package|;
end_package

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
name|layout
operator|.
name|LayoutFormatter
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
name|HTMLUnicodeConversionMaps
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
name|cleanup
operator|.
name|Formatter
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_class
DECL|class|HtmlToLatexFormatter
specifier|public
class|class
name|HtmlToLatexFormatter
extends|extends
name|Formatter
implements|implements
name|LayoutFormatter
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Logger
name|LOGGER
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|HtmlToLatexFormatter
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|MAX_TAG_LENGTH
specifier|private
specifier|static
specifier|final
name|int
name|MAX_TAG_LENGTH
init|=
literal|100
decl_stmt|;
DECL|field|ESCAPED_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|ESCAPED_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"&#([x]*)([0]*)(\\p{XDigit}+);"
argument_list|)
decl_stmt|;
DECL|field|ESCAPED_PATTERN2
specifier|private
specifier|static
specifier|final
name|Pattern
name|ESCAPED_PATTERN2
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"(.)&#([x]*)([0]*)(\\p{XDigit}+);"
argument_list|)
decl_stmt|;
DECL|field|ESCAPED_PATTERN3
specifier|private
specifier|static
specifier|final
name|Pattern
name|ESCAPED_PATTERN3
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"&#([x]*)([0]*)(\\p{XDigit}+);"
argument_list|)
decl_stmt|;
DECL|field|ESCAPED_PATTERN4
specifier|private
specifier|static
specifier|final
name|Pattern
name|ESCAPED_PATTERN4
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"&(\\w+);"
argument_list|)
decl_stmt|;
annotation|@
name|Override
DECL|method|format (String text)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|text
parameter_list|)
block|{
name|String
name|result
init|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|text
argument_list|)
decl_stmt|;
if|if
condition|(
name|result
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
name|result
return|;
block|}
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
comment|// Deal with the form<sup>k</sup>and<sub>k</sub>
name|result
operator|=
name|result
operator|.
name|replaceAll
argument_list|(
literal|"<[ ]?sup>([^<]+)</sup>"
argument_list|,
literal|"\\\\textsuperscript\\{$1\\}"
argument_list|)
expr_stmt|;
name|result
operator|=
name|result
operator|.
name|replaceAll
argument_list|(
literal|"<[ ]?sub>([^<]+)</sub>"
argument_list|,
literal|"\\\\textsubscript\\{$1\\}"
argument_list|)
expr_stmt|;
comment|// TODO: maybe rewrite this based on regular expressions instead
comment|// Note that (at least) the IEEE Xplore fetcher must be fixed as it relies on the current way to
comment|// remove tags for its image alt-tag to equation converter
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|result
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|int
name|c
init|=
name|result
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|c
operator|==
literal|'<'
condition|)
block|{
name|i
operator|=
name|readTag
argument_list|(
name|result
argument_list|,
name|i
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
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
name|result
operator|=
name|sb
operator|.
name|toString
argument_list|()
expr_stmt|;
comment|// Handle text based HTML entities
name|Set
argument_list|<
name|String
argument_list|>
name|patterns
init|=
name|HTMLUnicodeConversionMaps
operator|.
name|HTML_LATEX_CONVERSION_MAP
operator|.
name|keySet
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|pattern
range|:
name|patterns
control|)
block|{
name|result
operator|=
name|result
operator|.
name|replace
argument_list|(
name|pattern
argument_list|,
name|HTMLUnicodeConversionMaps
operator|.
name|HTML_LATEX_CONVERSION_MAP
operator|.
name|get
argument_list|(
name|pattern
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Handle numerical HTML entities
name|Matcher
name|m
init|=
name|ESCAPED_PATTERN
operator|.
name|matcher
argument_list|(
name|result
argument_list|)
decl_stmt|;
while|while
condition|(
name|m
operator|.
name|find
argument_list|()
condition|)
block|{
name|int
name|num
init|=
name|Integer
operator|.
name|decode
argument_list|(
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
operator|.
name|replace
argument_list|(
literal|"x"
argument_list|,
literal|"#"
argument_list|)
operator|+
name|m
operator|.
name|group
argument_list|(
literal|3
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|HTMLUnicodeConversionMaps
operator|.
name|NUMERICAL_LATEX_CONVERSION_MAP
operator|.
name|containsKey
argument_list|(
name|num
argument_list|)
condition|)
block|{
name|result
operator|=
name|result
operator|.
name|replace
argument_list|(
literal|"&#"
operator|+
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
operator|+
name|m
operator|.
name|group
argument_list|(
literal|2
argument_list|)
operator|+
name|m
operator|.
name|group
argument_list|(
literal|3
argument_list|)
operator|+
literal|";"
argument_list|,
name|HTMLUnicodeConversionMaps
operator|.
name|NUMERICAL_LATEX_CONVERSION_MAP
operator|.
name|get
argument_list|(
name|num
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Combining accents
name|m
operator|=
name|ESCAPED_PATTERN2
operator|.
name|matcher
argument_list|(
name|result
argument_list|)
expr_stmt|;
while|while
condition|(
name|m
operator|.
name|find
argument_list|()
condition|)
block|{
name|int
name|num
init|=
name|Integer
operator|.
name|decode
argument_list|(
name|m
operator|.
name|group
argument_list|(
literal|2
argument_list|)
operator|.
name|replace
argument_list|(
literal|"x"
argument_list|,
literal|"#"
argument_list|)
operator|+
name|m
operator|.
name|group
argument_list|(
literal|4
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|HTMLUnicodeConversionMaps
operator|.
name|ESCAPED_ACCENTS
operator|.
name|containsKey
argument_list|(
name|num
argument_list|)
condition|)
block|{
if|if
condition|(
literal|"i"
operator|.
name|equals
argument_list|(
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
condition|)
block|{
name|result
operator|=
name|result
operator|.
name|replace
argument_list|(
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
operator|+
literal|"&#"
operator|+
name|m
operator|.
name|group
argument_list|(
literal|2
argument_list|)
operator|+
name|m
operator|.
name|group
argument_list|(
literal|3
argument_list|)
operator|+
name|m
operator|.
name|group
argument_list|(
literal|4
argument_list|)
operator|+
literal|";"
argument_list|,
literal|"{\\"
operator|+
name|HTMLUnicodeConversionMaps
operator|.
name|ESCAPED_ACCENTS
operator|.
name|get
argument_list|(
name|num
argument_list|)
operator|+
literal|"{\\i}}"
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"j"
operator|.
name|equals
argument_list|(
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
condition|)
block|{
name|result
operator|=
name|result
operator|.
name|replace
argument_list|(
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
operator|+
literal|"&#"
operator|+
name|m
operator|.
name|group
argument_list|(
literal|2
argument_list|)
operator|+
name|m
operator|.
name|group
argument_list|(
literal|3
argument_list|)
operator|+
name|m
operator|.
name|group
argument_list|(
literal|4
argument_list|)
operator|+
literal|";"
argument_list|,
literal|"{\\"
operator|+
name|HTMLUnicodeConversionMaps
operator|.
name|ESCAPED_ACCENTS
operator|.
name|get
argument_list|(
name|num
argument_list|)
operator|+
literal|"{\\j}}"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|result
operator|=
name|result
operator|.
name|replace
argument_list|(
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
operator|+
literal|"&#"
operator|+
name|m
operator|.
name|group
argument_list|(
literal|2
argument_list|)
operator|+
name|m
operator|.
name|group
argument_list|(
literal|3
argument_list|)
operator|+
name|m
operator|.
name|group
argument_list|(
literal|4
argument_list|)
operator|+
literal|";"
argument_list|,
literal|"{\\"
operator|+
name|HTMLUnicodeConversionMaps
operator|.
name|ESCAPED_ACCENTS
operator|.
name|get
argument_list|(
name|num
argument_list|)
operator|+
literal|"{"
operator|+
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
operator|+
literal|"}}"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|// Find non-converted numerical characters
name|m
operator|=
name|ESCAPED_PATTERN3
operator|.
name|matcher
argument_list|(
name|result
argument_list|)
expr_stmt|;
while|while
condition|(
name|m
operator|.
name|find
argument_list|()
condition|)
block|{
name|int
name|num
init|=
name|Integer
operator|.
name|decode
argument_list|(
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
operator|.
name|replace
argument_list|(
literal|"x"
argument_list|,
literal|"#"
argument_list|)
operator|+
name|m
operator|.
name|group
argument_list|(
literal|3
argument_list|)
argument_list|)
decl_stmt|;
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"HTML escaped char not converted: "
operator|+
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
operator|+
name|m
operator|.
name|group
argument_list|(
literal|2
argument_list|)
operator|+
name|m
operator|.
name|group
argument_list|(
literal|3
argument_list|)
operator|+
literal|" = "
operator|+
name|Integer
operator|.
name|toString
argument_list|(
name|num
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Remove $$ in case of two adjacent conversions
name|result
operator|=
name|result
operator|.
name|replace
argument_list|(
literal|"$$"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
comment|// Find non-covered special characters with alphabetic codes
name|m
operator|=
name|ESCAPED_PATTERN4
operator|.
name|matcher
argument_list|(
name|result
argument_list|)
expr_stmt|;
while|while
condition|(
name|m
operator|.
name|find
argument_list|()
condition|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"HTML escaped char not converted: "
operator|+
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|result
operator|.
name|trim
argument_list|()
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"Converts HTML code to LaTeX code."
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getExampleInput ()
specifier|public
name|String
name|getExampleInput
parameter_list|()
block|{
return|return
literal|"<strong>JabRef</strong>"
return|;
block|}
DECL|method|readTag (String text, int position)
specifier|private
name|int
name|readTag
parameter_list|(
name|String
name|text
parameter_list|,
name|int
name|position
parameter_list|)
block|{
comment|// Have just read the< character that starts the tag.
name|int
name|index
init|=
name|text
operator|.
name|indexOf
argument_list|(
literal|'>'
argument_list|,
name|position
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|index
operator|>
name|position
operator|)
operator|&&
operator|(
operator|(
name|index
operator|-
name|position
operator|)
operator|<
name|MAX_TAG_LENGTH
operator|)
condition|)
block|{
return|return
name|index
return|;
comment|// Just skip the tag.
block|}
else|else
block|{
return|return
name|position
return|;
comment|// Don't do anything.
block|}
block|}
annotation|@
name|Override
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"HTML to LaTeX"
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getKey ()
specifier|public
name|String
name|getKey
parameter_list|()
block|{
return|return
literal|"html_to_latex"
return|;
block|}
block|}
end_class

end_unit

