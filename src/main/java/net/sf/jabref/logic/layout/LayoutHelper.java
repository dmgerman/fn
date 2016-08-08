begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2016 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.layout
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|layout
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
name|PushbackReader
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
name|Objects
import|;
end_import

begin_comment
comment|/**  * Helper class to get a Layout object.  *  *<code>  * LayoutHelper helper = new LayoutHelper(...a reader...);  * Layout layout = helper.getLayoutFromText();  *</code>  *  */
end_comment

begin_class
DECL|class|LayoutHelper
specifier|public
class|class
name|LayoutHelper
block|{
DECL|field|IS_LAYOUT_TEXT
specifier|public
specifier|static
specifier|final
name|int
name|IS_LAYOUT_TEXT
init|=
literal|1
decl_stmt|;
DECL|field|IS_SIMPLE_FIELD
specifier|public
specifier|static
specifier|final
name|int
name|IS_SIMPLE_FIELD
init|=
literal|2
decl_stmt|;
DECL|field|IS_FIELD_START
specifier|public
specifier|static
specifier|final
name|int
name|IS_FIELD_START
init|=
literal|3
decl_stmt|;
DECL|field|IS_FIELD_END
specifier|public
specifier|static
specifier|final
name|int
name|IS_FIELD_END
init|=
literal|4
decl_stmt|;
DECL|field|IS_OPTION_FIELD
specifier|public
specifier|static
specifier|final
name|int
name|IS_OPTION_FIELD
init|=
literal|5
decl_stmt|;
DECL|field|IS_GROUP_START
specifier|public
specifier|static
specifier|final
name|int
name|IS_GROUP_START
init|=
literal|6
decl_stmt|;
DECL|field|IS_GROUP_END
specifier|public
specifier|static
specifier|final
name|int
name|IS_GROUP_END
init|=
literal|7
decl_stmt|;
DECL|field|IS_ENCODING_NAME
specifier|public
specifier|static
specifier|final
name|int
name|IS_ENCODING_NAME
init|=
literal|8
decl_stmt|;
DECL|field|IS_FILENAME
specifier|public
specifier|static
specifier|final
name|int
name|IS_FILENAME
init|=
literal|9
decl_stmt|;
DECL|field|IS_FILEPATH
specifier|public
specifier|static
specifier|final
name|int
name|IS_FILEPATH
init|=
literal|10
decl_stmt|;
DECL|field|currentGroup
specifier|private
specifier|static
name|String
name|currentGroup
decl_stmt|;
DECL|field|in
specifier|private
specifier|final
name|PushbackReader
name|in
decl_stmt|;
DECL|field|parsedEntries
specifier|private
specifier|final
name|List
argument_list|<
name|StringInt
argument_list|>
name|parsedEntries
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|prefs
specifier|private
specifier|final
name|LayoutFormatterPreferences
name|prefs
decl_stmt|;
DECL|field|endOfFile
specifier|private
name|boolean
name|endOfFile
decl_stmt|;
DECL|method|LayoutHelper (Reader in, LayoutFormatterPreferences prefs)
specifier|public
name|LayoutHelper
parameter_list|(
name|Reader
name|in
parameter_list|,
name|LayoutFormatterPreferences
name|prefs
parameter_list|)
block|{
name|this
operator|.
name|in
operator|=
operator|new
name|PushbackReader
argument_list|(
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|in
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|prefs
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|prefs
argument_list|)
expr_stmt|;
block|}
DECL|method|getLayoutFromText ()
specifier|public
name|Layout
name|getLayoutFromText
parameter_list|()
throws|throws
name|IOException
block|{
name|parse
argument_list|()
expr_stmt|;
for|for
control|(
name|StringInt
name|parsedEntry
range|:
name|parsedEntries
control|)
block|{
if|if
condition|(
operator|(
name|parsedEntry
operator|.
name|i
operator|==
name|LayoutHelper
operator|.
name|IS_SIMPLE_FIELD
operator|)
operator|||
operator|(
name|parsedEntry
operator|.
name|i
operator|==
name|LayoutHelper
operator|.
name|IS_FIELD_START
operator|)
operator|||
operator|(
name|parsedEntry
operator|.
name|i
operator|==
name|LayoutHelper
operator|.
name|IS_FIELD_END
operator|)
operator|||
operator|(
name|parsedEntry
operator|.
name|i
operator|==
name|LayoutHelper
operator|.
name|IS_GROUP_START
operator|)
operator|||
operator|(
name|parsedEntry
operator|.
name|i
operator|==
name|LayoutHelper
operator|.
name|IS_GROUP_END
operator|)
condition|)
block|{
name|parsedEntry
operator|.
name|s
operator|=
name|parsedEntry
operator|.
name|s
operator|.
name|trim
argument_list|()
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
block|}
block|}
return|return
operator|new
name|Layout
argument_list|(
name|parsedEntries
argument_list|,
name|prefs
argument_list|)
return|;
block|}
DECL|method|getCurrentGroup ()
specifier|public
specifier|static
name|String
name|getCurrentGroup
parameter_list|()
block|{
return|return
name|LayoutHelper
operator|.
name|currentGroup
return|;
block|}
DECL|method|setCurrentGroup (String newGroup)
specifier|public
specifier|static
name|void
name|setCurrentGroup
parameter_list|(
name|String
name|newGroup
parameter_list|)
block|{
name|LayoutHelper
operator|.
name|currentGroup
operator|=
name|newGroup
expr_stmt|;
block|}
DECL|method|doBracketedField (final int field)
specifier|private
name|void
name|doBracketedField
parameter_list|(
specifier|final
name|int
name|field
parameter_list|)
throws|throws
name|IOException
block|{
name|StringBuilder
name|buffer
init|=
literal|null
decl_stmt|;
name|int
name|c
decl_stmt|;
name|boolean
name|start
init|=
literal|false
decl_stmt|;
while|while
condition|(
operator|!
name|endOfFile
condition|)
block|{
name|c
operator|=
name|read
argument_list|()
expr_stmt|;
if|if
condition|(
name|c
operator|==
operator|-
literal|1
condition|)
block|{
name|endOfFile
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|buffer
operator|!=
literal|null
condition|)
block|{
name|parsedEntries
operator|.
name|add
argument_list|(
operator|new
name|StringInt
argument_list|(
name|buffer
operator|.
name|toString
argument_list|()
argument_list|,
name|field
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return;
block|}
if|if
condition|(
operator|(
name|c
operator|==
literal|'{'
operator|)
operator|||
operator|(
name|c
operator|==
literal|'}'
operator|)
condition|)
block|{
if|if
condition|(
name|c
operator|==
literal|'}'
condition|)
block|{
if|if
condition|(
name|buffer
operator|!=
literal|null
condition|)
block|{
name|parsedEntries
operator|.
name|add
argument_list|(
operator|new
name|StringInt
argument_list|(
name|buffer
operator|.
name|toString
argument_list|()
argument_list|,
name|field
argument_list|)
argument_list|)
expr_stmt|;
return|return;
block|}
block|}
else|else
block|{
name|start
operator|=
literal|true
expr_stmt|;
block|}
block|}
else|else
block|{
if|if
condition|(
name|buffer
operator|==
literal|null
condition|)
block|{
name|buffer
operator|=
operator|new
name|StringBuilder
argument_list|(
literal|100
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|start
operator|&&
operator|(
name|c
operator|!=
literal|'}'
operator|)
condition|)
block|{
name|buffer
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
block|}
block|}
comment|/**      *      */
DECL|method|doBracketedOptionField ()
specifier|private
name|void
name|doBracketedOptionField
parameter_list|()
throws|throws
name|IOException
block|{
name|StringBuilder
name|buffer
init|=
literal|null
decl_stmt|;
name|int
name|c
decl_stmt|;
name|boolean
name|start
init|=
literal|false
decl_stmt|;
name|boolean
name|inQuotes
init|=
literal|false
decl_stmt|;
name|boolean
name|doneWithOptions
init|=
literal|false
decl_stmt|;
name|String
name|option
init|=
literal|null
decl_stmt|;
name|String
name|tmp
decl_stmt|;
while|while
condition|(
operator|!
name|endOfFile
condition|)
block|{
name|c
operator|=
name|read
argument_list|()
expr_stmt|;
if|if
condition|(
name|c
operator|==
operator|-
literal|1
condition|)
block|{
name|endOfFile
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|buffer
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|option
operator|==
literal|null
condition|)
block|{
name|tmp
operator|=
name|buffer
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|tmp
operator|=
name|buffer
operator|.
name|toString
argument_list|()
operator|+
literal|'\n'
operator|+
name|option
expr_stmt|;
block|}
name|parsedEntries
operator|.
name|add
argument_list|(
operator|new
name|StringInt
argument_list|(
name|tmp
argument_list|,
name|LayoutHelper
operator|.
name|IS_OPTION_FIELD
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return;
block|}
if|if
condition|(
operator|!
name|inQuotes
operator|&&
operator|(
operator|(
name|c
operator|==
literal|']'
operator|)
operator|||
operator|(
name|c
operator|==
literal|'['
operator|)
operator|||
operator|(
name|doneWithOptions
operator|&&
operator|(
operator|(
name|c
operator|==
literal|'{'
operator|)
operator|||
operator|(
name|c
operator|==
literal|'}'
operator|)
operator|)
operator|)
operator|)
condition|)
block|{
if|if
condition|(
operator|(
name|c
operator|==
literal|']'
operator|)
operator|||
operator|(
name|doneWithOptions
operator|&&
operator|(
name|c
operator|==
literal|'}'
operator|)
operator|)
condition|)
block|{
comment|// changed section start - arudert
comment|// buffer may be null for parameters
if|if
condition|(
operator|(
name|c
operator|==
literal|']'
operator|)
operator|&&
operator|(
name|buffer
operator|!=
literal|null
operator|)
condition|)
block|{
comment|// changed section end - arudert
name|option
operator|=
name|buffer
operator|.
name|toString
argument_list|()
expr_stmt|;
name|buffer
operator|=
literal|null
expr_stmt|;
name|start
operator|=
literal|false
expr_stmt|;
name|doneWithOptions
operator|=
literal|true
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|c
operator|==
literal|'}'
condition|)
block|{
comment|// changed section begin - arudert
comment|// bracketed option must be followed by an (optionally empty) parameter
comment|// if empty, the parameter is set to " " (whitespace to avoid that the tokenizer that
comment|// splits the string later on ignores the empty parameter)
name|String
name|parameter
init|=
name|buffer
operator|==
literal|null
condition|?
literal|" "
else|:
name|buffer
operator|.
name|toString
argument_list|()
decl_stmt|;
if|if
condition|(
name|option
operator|==
literal|null
condition|)
block|{
name|tmp
operator|=
name|parameter
expr_stmt|;
block|}
else|else
block|{
name|tmp
operator|=
name|parameter
operator|+
literal|'\n'
operator|+
name|option
expr_stmt|;
block|}
name|parsedEntries
operator|.
name|add
argument_list|(
operator|new
name|StringInt
argument_list|(
name|tmp
argument_list|,
name|LayoutHelper
operator|.
name|IS_OPTION_FIELD
argument_list|)
argument_list|)
expr_stmt|;
return|return;
block|}
comment|// changed section end - arudert
comment|// changed section start - arudert
comment|// }
comment|// changed section end - arudert
block|}
else|else
block|{
name|start
operator|=
literal|true
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|c
operator|==
literal|'"'
condition|)
block|{
name|inQuotes
operator|=
operator|!
name|inQuotes
expr_stmt|;
if|if
condition|(
name|buffer
operator|==
literal|null
condition|)
block|{
name|buffer
operator|=
operator|new
name|StringBuilder
argument_list|(
literal|100
argument_list|)
expr_stmt|;
block|}
name|buffer
operator|.
name|append
argument_list|(
literal|'"'
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|buffer
operator|==
literal|null
condition|)
block|{
name|buffer
operator|=
operator|new
name|StringBuilder
argument_list|(
literal|100
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|start
condition|)
block|{
comment|// changed section begin - arudert
comment|// keep the backslash so we know wether this is a fieldname or an ordinary parameter
comment|//if (c != '\\')
comment|//{
name|buffer
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
expr_stmt|;
comment|//}
comment|// changed section end - arudert
block|}
block|}
block|}
block|}
DECL|method|parse ()
specifier|private
name|void
name|parse
parameter_list|()
throws|throws
name|IOException
throws|,
name|StringIndexOutOfBoundsException
block|{
name|skipWhitespace
argument_list|()
expr_stmt|;
name|int
name|c
decl_stmt|;
name|StringBuilder
name|buffer
init|=
literal|null
decl_stmt|;
name|boolean
name|escaped
init|=
literal|false
decl_stmt|;
while|while
condition|(
operator|!
name|endOfFile
condition|)
block|{
name|c
operator|=
name|read
argument_list|()
expr_stmt|;
if|if
condition|(
name|c
operator|==
operator|-
literal|1
condition|)
block|{
name|endOfFile
operator|=
literal|true
expr_stmt|;
comment|/*                  * CO 2006-11-11: Added check for null, otherwise a Layout that                  * finishes with a curly brace throws a NPE                  */
if|if
condition|(
name|buffer
operator|!=
literal|null
condition|)
block|{
name|parsedEntries
operator|.
name|add
argument_list|(
operator|new
name|StringInt
argument_list|(
name|buffer
operator|.
name|toString
argument_list|()
argument_list|,
name|LayoutHelper
operator|.
name|IS_LAYOUT_TEXT
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return;
block|}
if|if
condition|(
operator|(
name|c
operator|==
literal|'\\'
operator|)
operator|&&
operator|(
name|peek
argument_list|()
operator|!=
literal|'\\'
operator|)
operator|&&
operator|!
name|escaped
condition|)
block|{
if|if
condition|(
name|buffer
operator|!=
literal|null
condition|)
block|{
name|parsedEntries
operator|.
name|add
argument_list|(
operator|new
name|StringInt
argument_list|(
name|buffer
operator|.
name|toString
argument_list|()
argument_list|,
name|LayoutHelper
operator|.
name|IS_LAYOUT_TEXT
argument_list|)
argument_list|)
expr_stmt|;
name|buffer
operator|=
literal|null
expr_stmt|;
block|}
name|parseField
argument_list|()
expr_stmt|;
comment|// To make sure the next character, if it is a backslash,
comment|// doesn't get ignored, since "previous" now holds a backslash:
name|escaped
operator|=
literal|false
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|buffer
operator|==
literal|null
condition|)
block|{
name|buffer
operator|=
operator|new
name|StringBuilder
argument_list|(
literal|100
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|(
name|c
operator|!=
literal|'\\'
operator|)
operator|||
name|escaped
condition|)
comment|// (previous == '\\')))
block|{
name|buffer
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
name|escaped
operator|=
operator|(
name|c
operator|==
literal|'\\'
operator|)
operator|&&
operator|!
name|escaped
expr_stmt|;
block|}
block|}
block|}
DECL|method|parseField ()
specifier|private
name|void
name|parseField
parameter_list|()
throws|throws
name|IOException
block|{
name|int
name|c
decl_stmt|;
name|StringBuilder
name|buffer
init|=
literal|null
decl_stmt|;
name|String
name|name
decl_stmt|;
while|while
condition|(
operator|!
name|endOfFile
condition|)
block|{
name|c
operator|=
name|read
argument_list|()
expr_stmt|;
if|if
condition|(
name|c
operator|==
operator|-
literal|1
condition|)
block|{
name|endOfFile
operator|=
literal|true
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|Character
operator|.
name|isLetter
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
operator|&&
operator|(
name|c
operator|!=
literal|'_'
operator|)
operator|&&
operator|(
name|c
operator|!=
literal|'-'
operator|)
condition|)
block|{
name|unread
argument_list|(
name|c
argument_list|)
expr_stmt|;
name|name
operator|=
name|buffer
operator|==
literal|null
condition|?
literal|""
else|:
name|buffer
operator|.
name|toString
argument_list|()
expr_stmt|;
if|if
condition|(
name|name
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|StringBuilder
name|lastFive
init|=
operator|new
name|StringBuilder
argument_list|(
literal|10
argument_list|)
decl_stmt|;
for|for
control|(
name|StringInt
name|entry
range|:
name|parsedEntries
operator|.
name|subList
argument_list|(
name|Math
operator|.
name|max
argument_list|(
literal|0
argument_list|,
name|parsedEntries
operator|.
name|size
argument_list|()
operator|-
literal|6
argument_list|)
argument_list|,
name|parsedEntries
operator|.
name|size
argument_list|()
operator|-
literal|1
argument_list|)
control|)
block|{
name|lastFive
operator|.
name|append
argument_list|(
name|entry
operator|.
name|s
argument_list|)
expr_stmt|;
block|}
throw|throw
operator|new
name|StringIndexOutOfBoundsException
argument_list|(
literal|"Backslash parsing error near \'"
operator|+
name|lastFive
operator|.
name|toString
argument_list|()
operator|.
name|replace
argument_list|(
literal|"\n"
argument_list|,
literal|" "
argument_list|)
operator|+
literal|'\''
argument_list|)
throw|;
block|}
if|if
condition|(
literal|"begin"
operator|.
name|equalsIgnoreCase
argument_list|(
name|name
argument_list|)
condition|)
block|{
comment|// get field name
name|doBracketedField
argument_list|(
name|LayoutHelper
operator|.
name|IS_FIELD_START
argument_list|)
expr_stmt|;
return|return;
block|}
elseif|else
if|if
condition|(
literal|"begingroup"
operator|.
name|equalsIgnoreCase
argument_list|(
name|name
argument_list|)
condition|)
block|{
comment|// get field name
name|doBracketedField
argument_list|(
name|LayoutHelper
operator|.
name|IS_GROUP_START
argument_list|)
expr_stmt|;
return|return;
block|}
elseif|else
if|if
condition|(
literal|"format"
operator|.
name|equalsIgnoreCase
argument_list|(
name|name
argument_list|)
condition|)
block|{
if|if
condition|(
name|c
operator|==
literal|'['
condition|)
block|{
comment|// get format parameter
comment|// get field name
name|doBracketedOptionField
argument_list|()
expr_stmt|;
return|return;
block|}
else|else
block|{
comment|// get field name
name|doBracketedField
argument_list|(
name|LayoutHelper
operator|.
name|IS_OPTION_FIELD
argument_list|)
expr_stmt|;
return|return;
block|}
block|}
elseif|else
if|if
condition|(
literal|"filename"
operator|.
name|equalsIgnoreCase
argument_list|(
name|name
argument_list|)
condition|)
block|{
comment|// Print the name of the database BIB file.
comment|// This is only supported in begin/end layouts, not in
comment|// entry layouts.
name|parsedEntries
operator|.
name|add
argument_list|(
operator|new
name|StringInt
argument_list|(
name|name
argument_list|,
name|LayoutHelper
operator|.
name|IS_FILENAME
argument_list|)
argument_list|)
expr_stmt|;
return|return;
block|}
elseif|else
if|if
condition|(
literal|"filepath"
operator|.
name|equalsIgnoreCase
argument_list|(
name|name
argument_list|)
condition|)
block|{
comment|// Print the full path of the database BIB file.
comment|// This is only supported in begin/end layouts, not in
comment|// entry layouts.
name|parsedEntries
operator|.
name|add
argument_list|(
operator|new
name|StringInt
argument_list|(
name|name
argument_list|,
name|LayoutHelper
operator|.
name|IS_FILEPATH
argument_list|)
argument_list|)
expr_stmt|;
return|return;
block|}
elseif|else
if|if
condition|(
literal|"end"
operator|.
name|equalsIgnoreCase
argument_list|(
name|name
argument_list|)
condition|)
block|{
comment|// get field name
name|doBracketedField
argument_list|(
name|LayoutHelper
operator|.
name|IS_FIELD_END
argument_list|)
expr_stmt|;
return|return;
block|}
elseif|else
if|if
condition|(
literal|"endgroup"
operator|.
name|equalsIgnoreCase
argument_list|(
name|name
argument_list|)
condition|)
block|{
comment|// get field name
name|doBracketedField
argument_list|(
name|LayoutHelper
operator|.
name|IS_GROUP_END
argument_list|)
expr_stmt|;
return|return;
block|}
elseif|else
if|if
condition|(
literal|"encoding"
operator|.
name|equalsIgnoreCase
argument_list|(
name|name
argument_list|)
condition|)
block|{
comment|// Print the name of the current encoding used for export.
comment|// This is only supported in begin/end layouts, not in
comment|// entry layouts.
name|parsedEntries
operator|.
name|add
argument_list|(
operator|new
name|StringInt
argument_list|(
name|name
argument_list|,
name|LayoutHelper
operator|.
name|IS_ENCODING_NAME
argument_list|)
argument_list|)
expr_stmt|;
return|return;
block|}
comment|// for all other cases
name|parsedEntries
operator|.
name|add
argument_list|(
operator|new
name|StringInt
argument_list|(
name|name
argument_list|,
name|LayoutHelper
operator|.
name|IS_SIMPLE_FIELD
argument_list|)
argument_list|)
expr_stmt|;
return|return;
block|}
else|else
block|{
if|if
condition|(
name|buffer
operator|==
literal|null
condition|)
block|{
name|buffer
operator|=
operator|new
name|StringBuilder
argument_list|(
literal|100
argument_list|)
expr_stmt|;
block|}
name|buffer
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
block|}
DECL|method|peek ()
specifier|private
name|int
name|peek
parameter_list|()
throws|throws
name|IOException
block|{
name|int
name|c
init|=
name|read
argument_list|()
decl_stmt|;
name|unread
argument_list|(
name|c
argument_list|)
expr_stmt|;
return|return
name|c
return|;
block|}
DECL|method|read ()
specifier|private
name|int
name|read
parameter_list|()
throws|throws
name|IOException
block|{
return|return
name|in
operator|.
name|read
argument_list|()
return|;
block|}
DECL|method|skipWhitespace ()
specifier|private
name|void
name|skipWhitespace
parameter_list|()
throws|throws
name|IOException
block|{
name|int
name|c
decl_stmt|;
while|while
condition|(
literal|true
condition|)
block|{
name|c
operator|=
name|read
argument_list|()
expr_stmt|;
if|if
condition|(
operator|(
name|c
operator|==
operator|-
literal|1
operator|)
operator|||
operator|(
name|c
operator|==
literal|65535
operator|)
condition|)
block|{
name|endOfFile
operator|=
literal|true
expr_stmt|;
return|return;
block|}
if|if
condition|(
operator|!
name|Character
operator|.
name|isWhitespace
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
condition|)
block|{
name|unread
argument_list|(
name|c
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
block|}
DECL|method|unread (int c)
specifier|private
name|void
name|unread
parameter_list|(
name|int
name|c
parameter_list|)
throws|throws
name|IOException
block|{
name|in
operator|.
name|unread
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

