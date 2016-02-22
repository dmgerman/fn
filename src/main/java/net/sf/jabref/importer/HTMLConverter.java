begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2016 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.importer
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|exporter
operator|.
name|layout
operator|.
name|LayoutFormatter
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
name|util
operator|.
name|strings
operator|.
name|HTMLUnicodeConversionMaps
import|;
end_import

begin_class
DECL|class|HTMLConverter
specifier|public
class|class
name|HTMLConverter
implements|implements
name|LayoutFormatter
implements|,
name|Formatter
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
name|HTMLConverter
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
DECL|method|HTMLConverter ()
specifier|public
name|HTMLConverter
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
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
condition|)
block|{
return|return
name|text
return|;
block|}
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
comment|// Deal with the form<sup>k</sup>and<sub>k</sub>
comment|// If the result is in text or equation form can be controlled
comment|// From the "Advanced settings" tab
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_CONVERT_TO_EQUATION
argument_list|)
condition|)
block|{
name|text
operator|=
name|text
operator|.
name|replaceAll
argument_list|(
literal|"<[ ]?sup>([^<]+)</sup>"
argument_list|,
literal|"\\$\\^\\{$1\\}\\$"
argument_list|)
expr_stmt|;
name|text
operator|=
name|text
operator|.
name|replaceAll
argument_list|(
literal|"<[ ]?sub>([^<]+)</sub>"
argument_list|,
literal|"\\$_\\{$1\\}\\$"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|text
operator|=
name|text
operator|.
name|replaceAll
argument_list|(
literal|"<[ ]?sup>([^<]+)</sup>"
argument_list|,
literal|"\\\\textsuperscript\\{$1\\}"
argument_list|)
expr_stmt|;
name|text
operator|=
name|text
operator|.
name|replaceAll
argument_list|(
literal|"<[ ]?sub>([^<]+)</sub>"
argument_list|,
literal|"\\\\textsubscript\\{$1\\}"
argument_list|)
expr_stmt|;
block|}
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
name|text
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
name|text
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
name|text
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
name|text
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
name|text
operator|=
name|text
operator|.
name|replaceAll
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
name|text
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
name|text
operator|=
name|text
operator|.
name|replaceAll
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
name|text
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
name|text
operator|=
name|text
operator|.
name|replaceAll
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
literal|"\\{\\\\"
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
literal|"\\{\\\\i\\}\\}"
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
name|text
operator|=
name|text
operator|.
name|replaceAll
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
literal|"\\{\\\\"
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
literal|"\\{\\\\j\\}\\}"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|text
operator|=
name|text
operator|.
name|replaceAll
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
literal|"\\{\\\\"
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
literal|"\\{"
operator|+
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
operator|+
literal|"\\}\\}"
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
name|text
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
name|text
operator|=
name|text
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
name|text
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
name|text
operator|.
name|trim
argument_list|()
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
literal|"HTMLConverter"
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
literal|"HtmlConverter"
return|;
block|}
block|}
end_class

end_unit

