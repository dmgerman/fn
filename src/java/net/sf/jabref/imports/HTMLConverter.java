begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.imports
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
package|;
end_package

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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
operator|.
name|layout
operator|.
name|LayoutFormatter
import|;
end_import

begin_comment
comment|/**  * Created by IntelliJ IDEA.  * User: alver  * Date: Mar 26, 2006  * Time: 8:05:08 PM  * To change this template use File | Settings | File Templates.  */
end_comment

begin_class
DECL|class|HTMLConverter
specifier|public
class|class
name|HTMLConverter
implements|implements
name|LayoutFormatter
block|{
DECL|field|escapedSymbols
specifier|private
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|escapedSymbols
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
DECL|method|HTMLConverter ()
specifier|public
name|HTMLConverter
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
name|escapedSymbols
operator|.
name|put
argument_list|(
literal|"&ldquo;"
argument_list|,
literal|"``"
argument_list|)
expr_stmt|;
name|escapedSymbols
operator|.
name|put
argument_list|(
literal|"&rdquo;"
argument_list|,
literal|"''"
argument_list|)
expr_stmt|;
name|escapedSymbols
operator|.
name|put
argument_list|(
literal|"&lsquo;"
argument_list|,
literal|"``"
argument_list|)
expr_stmt|;
name|escapedSymbols
operator|.
name|put
argument_list|(
literal|"&rsquo;"
argument_list|,
literal|"''"
argument_list|)
expr_stmt|;
name|escapedSymbols
operator|.
name|put
argument_list|(
literal|"&nbsp;"
argument_list|,
literal|" "
argument_list|)
expr_stmt|;
name|escapedSymbols
operator|.
name|put
argument_list|(
literal|"&quot;"
argument_list|,
literal|"\""
argument_list|)
expr_stmt|;
name|escapedSymbols
operator|.
name|put
argument_list|(
literal|"&amp;"
argument_list|,
literal|"&"
argument_list|)
expr_stmt|;
name|escapedSymbols
operator|.
name|put
argument_list|(
literal|"&lt;"
argument_list|,
literal|"<"
argument_list|)
expr_stmt|;
name|escapedSymbols
operator|.
name|put
argument_list|(
literal|"&gt;"
argument_list|,
literal|">"
argument_list|)
expr_stmt|;
name|escapedSymbols
operator|.
name|put
argument_list|(
literal|"&mu;"
argument_list|,
literal|"\\$\\\\mu\\$"
argument_list|)
expr_stmt|;
name|escapedSymbols
operator|.
name|put
argument_list|(
literal|"&times;"
argument_list|,
literal|"\\$\\\\times\\$"
argument_list|)
expr_stmt|;
name|escapedSymbols
operator|.
name|put
argument_list|(
literal|"&Sigma;"
argument_list|,
literal|"\\{\\$\\\\Sigma\\$\\}"
argument_list|)
expr_stmt|;
name|escapedSymbols
operator|.
name|put
argument_list|(
literal|"&Delta;"
argument_list|,
literal|"\\{\\$\\\\Delta\\$\\}"
argument_list|)
expr_stmt|;
name|escapedSymbols
operator|.
name|put
argument_list|(
literal|"&radic;"
argument_list|,
literal|"\\$\\\\sqrt{}\\$"
argument_list|)
expr_stmt|;
block|}
DECL|method|format (String text)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|text
parameter_list|)
block|{
if|if
condition|(
name|text
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
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
name|sb
argument_list|,
name|i
argument_list|)
expr_stmt|;
block|}
else|else
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
name|text
operator|=
name|sb
operator|.
name|toString
argument_list|()
expr_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|patterns
init|=
name|escapedSymbols
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
name|escapedSymbols
operator|.
name|get
argument_list|(
name|pattern
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|Pattern
name|escapedPattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"&#([x]*\\p{XDigit}+);"
argument_list|)
decl_stmt|;
name|Matcher
name|m
init|=
name|escapedPattern
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
argument_list|)
decl_stmt|;
switch|switch
condition|(
name|num
condition|)
block|{
case|case
literal|37
case|:
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
literal|";"
argument_list|,
literal|"%"
argument_list|)
expr_stmt|;
break|break;
case|case
literal|38
case|:
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
literal|";"
argument_list|,
literal|"&"
argument_list|)
expr_stmt|;
break|break;
case|case
literal|176
case|:
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
literal|";"
argument_list|,
literal|"\\$\\\\deg\\$"
argument_list|)
expr_stmt|;
break|break;
case|case
literal|181
case|:
case|case
literal|956
case|:
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
literal|";"
argument_list|,
literal|"\\$\\\\mu\\$"
argument_list|)
expr_stmt|;
break|break;
case|case
literal|215
case|:
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
literal|";"
argument_list|,
literal|"\\$\\\\times\\$"
argument_list|)
expr_stmt|;
break|break;
case|case
literal|769
case|:
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
literal|";"
argument_list|,
literal|"'"
argument_list|)
expr_stmt|;
comment|// Can be solved better as it is a combining accent
break|break;
case|case
literal|916
case|:
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
literal|";"
argument_list|,
literal|"\\{\\$\\\\Delta\\$\\}"
argument_list|)
expr_stmt|;
break|break;
case|case
literal|931
case|:
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
literal|";"
argument_list|,
literal|"\\{\\$\\\\Sigma\\$\\}"
argument_list|)
expr_stmt|;
break|break;
case|case
literal|948
case|:
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
literal|";"
argument_list|,
literal|"\\$\\\\delta\\$"
argument_list|)
expr_stmt|;
break|break;
case|case
literal|963
case|:
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
literal|";"
argument_list|,
literal|"\\{\\$\\\\sigma\\$\\}"
argument_list|)
expr_stmt|;
break|break;
case|case
literal|8208
case|:
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
literal|";"
argument_list|,
literal|"-"
argument_list|)
expr_stmt|;
break|break;
case|case
literal|8211
case|:
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
literal|";"
argument_list|,
literal|"--"
argument_list|)
expr_stmt|;
break|break;
case|case
literal|8212
case|:
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
literal|";"
argument_list|,
literal|"---"
argument_list|)
expr_stmt|;
break|break;
case|case
literal|8217
case|:
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
literal|";"
argument_list|,
literal|"'"
argument_list|)
expr_stmt|;
break|break;
case|case
literal|8730
case|:
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
literal|";"
argument_list|,
literal|"\\$\\\\sqrt{}\\$"
argument_list|)
expr_stmt|;
break|break;
default|default:
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"HTML escaped char not converted "
operator|+
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
operator|+
literal|": "
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
block|}
comment|// Find non-covered special characters with alphabetic codes
name|escapedPattern
operator|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"&(\\w+);"
argument_list|)
expr_stmt|;
name|m
operator|=
name|escapedPattern
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
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"HTML escaped char not converted "
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
DECL|field|MAX_TAG_LENGTH
specifier|private
specifier|final
name|int
name|MAX_TAG_LENGTH
init|=
literal|30
decl_stmt|;
comment|/*private final int MAX_CHAR_LENGTH = 10;      private int readHtmlChar(String text, StringBuffer sb, int position) {         // Have just read the< character that starts the tag.         int index = text.indexOf(';', position);         if ((index> position)&& (index-position< MAX_CHAR_LENGTH)) {         	//String code = text.substring(position, index);             //System.out.println("Removed code: "+text.substring(position, index));             return index; // Just skip the tag.         } else return position; // Don't do anything.     }*/
DECL|method|readTag (String text, StringBuffer sb, int position)
specifier|private
name|int
name|readTag
parameter_list|(
name|String
name|text
parameter_list|,
name|StringBuffer
name|sb
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
name|index
operator|-
name|position
operator|<
name|MAX_TAG_LENGTH
operator|)
condition|)
block|{
comment|//System.out.println("Removed tag: "+text.substring(position, index));
return|return
name|index
return|;
comment|// Just skip the tag.
block|}
else|else
return|return
name|position
return|;
comment|// Don't do anything.
block|}
block|}
end_class

end_unit

