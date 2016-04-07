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
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|charset
operator|.
name|Charset
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
name|BibDatabaseContext
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
name|journals
operator|.
name|JournalAbbreviationRepository
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
name|layout
operator|.
name|format
operator|.
name|JournalAbbreviator
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
name|layout
operator|.
name|format
operator|.
name|NameFormatter
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
name|layout
operator|.
name|format
operator|.
name|NotFoundFormatter
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
name|search
operator|.
name|MatchesHighlighter
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
name|model
operator|.
name|database
operator|.
name|BibDatabase
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

begin_class
DECL|class|LayoutEntry
class|class
name|LayoutEntry
block|{
DECL|field|option
specifier|private
name|List
argument_list|<
name|LayoutFormatter
argument_list|>
name|option
decl_stmt|;
comment|// Formatter to be run after other formatters:
DECL|field|postFormatter
specifier|private
name|LayoutFormatter
name|postFormatter
decl_stmt|;
DECL|field|text
specifier|private
name|String
name|text
decl_stmt|;
DECL|field|layoutEntries
specifier|private
name|List
argument_list|<
name|LayoutEntry
argument_list|>
name|layoutEntries
decl_stmt|;
DECL|field|type
specifier|private
specifier|final
name|int
name|type
decl_stmt|;
DECL|field|invalidFormatter
specifier|private
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|invalidFormatter
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
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
name|LayoutEntry
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|repository
specifier|private
specifier|final
name|JournalAbbreviationRepository
name|repository
decl_stmt|;
DECL|method|LayoutEntry (StringInt si, JournalAbbreviationRepository repository)
specifier|public
name|LayoutEntry
parameter_list|(
name|StringInt
name|si
parameter_list|,
name|JournalAbbreviationRepository
name|repository
parameter_list|)
block|{
name|this
operator|.
name|repository
operator|=
name|repository
expr_stmt|;
name|type
operator|=
name|si
operator|.
name|i
expr_stmt|;
switch|switch
condition|(
name|type
condition|)
block|{
case|case
name|LayoutHelper
operator|.
name|IS_LAYOUT_TEXT
case|:
name|text
operator|=
name|si
operator|.
name|s
expr_stmt|;
break|break;
case|case
name|LayoutHelper
operator|.
name|IS_SIMPLE_FIELD
case|:
name|text
operator|=
name|si
operator|.
name|s
operator|.
name|trim
argument_list|()
expr_stmt|;
break|break;
case|case
name|LayoutHelper
operator|.
name|IS_OPTION_FIELD
case|:
name|doOptionField
argument_list|(
name|si
operator|.
name|s
argument_list|)
expr_stmt|;
break|break;
case|case
name|LayoutHelper
operator|.
name|IS_FIELD_START
case|:
case|case
name|LayoutHelper
operator|.
name|IS_FIELD_END
case|:
default|default:
break|break;
block|}
block|}
DECL|method|LayoutEntry (List<StringInt> parsedEntries, int layoutType, JournalAbbreviationRepository repository)
specifier|public
name|LayoutEntry
parameter_list|(
name|List
argument_list|<
name|StringInt
argument_list|>
name|parsedEntries
parameter_list|,
name|int
name|layoutType
parameter_list|,
name|JournalAbbreviationRepository
name|repository
parameter_list|)
block|{
name|this
operator|.
name|repository
operator|=
name|repository
expr_stmt|;
name|List
argument_list|<
name|StringInt
argument_list|>
name|blockEntries
init|=
literal|null
decl_stmt|;
name|List
argument_list|<
name|LayoutEntry
argument_list|>
name|tmpEntries
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|LayoutEntry
name|le
decl_stmt|;
name|String
name|blockStart
init|=
name|parsedEntries
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|s
decl_stmt|;
name|String
name|blockEnd
init|=
name|parsedEntries
operator|.
name|get
argument_list|(
name|parsedEntries
operator|.
name|size
argument_list|()
operator|-
literal|1
argument_list|)
operator|.
name|s
decl_stmt|;
if|if
condition|(
operator|!
name|blockStart
operator|.
name|equals
argument_list|(
name|blockEnd
argument_list|)
condition|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Field start and end entry must be equal."
argument_list|)
expr_stmt|;
block|}
name|type
operator|=
name|layoutType
expr_stmt|;
name|text
operator|=
name|blockEnd
expr_stmt|;
for|for
control|(
name|StringInt
name|parsedEntry
range|:
name|parsedEntries
operator|.
name|subList
argument_list|(
literal|1
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
switch|switch
condition|(
name|parsedEntry
operator|.
name|i
condition|)
block|{
case|case
name|LayoutHelper
operator|.
name|IS_FIELD_START
case|:
case|case
name|LayoutHelper
operator|.
name|IS_GROUP_START
case|:
name|blockEntries
operator|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
expr_stmt|;
name|blockStart
operator|=
name|parsedEntry
operator|.
name|s
expr_stmt|;
break|break;
case|case
name|LayoutHelper
operator|.
name|IS_FIELD_END
case|:
case|case
name|LayoutHelper
operator|.
name|IS_GROUP_END
case|:
if|if
condition|(
name|blockStart
operator|.
name|equals
argument_list|(
name|parsedEntry
operator|.
name|s
argument_list|)
condition|)
block|{
name|blockEntries
operator|.
name|add
argument_list|(
name|parsedEntry
argument_list|)
expr_stmt|;
name|le
operator|=
operator|new
name|LayoutEntry
argument_list|(
name|blockEntries
argument_list|,
name|parsedEntry
operator|.
name|i
operator|==
name|LayoutHelper
operator|.
name|IS_GROUP_END
condition|?
name|LayoutHelper
operator|.
name|IS_GROUP_START
else|:
name|LayoutHelper
operator|.
name|IS_FIELD_START
argument_list|,
name|repository
argument_list|)
expr_stmt|;
name|tmpEntries
operator|.
name|add
argument_list|(
name|le
argument_list|)
expr_stmt|;
name|blockEntries
operator|=
literal|null
expr_stmt|;
block|}
else|else
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Nested field entries are not implemented!"
argument_list|)
expr_stmt|;
block|}
break|break;
case|case
name|LayoutHelper
operator|.
name|IS_LAYOUT_TEXT
case|:
case|case
name|LayoutHelper
operator|.
name|IS_SIMPLE_FIELD
case|:
case|case
name|LayoutHelper
operator|.
name|IS_OPTION_FIELD
case|:
default|default:
comment|// Do nothing
break|break;
block|}
if|if
condition|(
name|blockEntries
operator|==
literal|null
condition|)
block|{
name|tmpEntries
operator|.
name|add
argument_list|(
operator|new
name|LayoutEntry
argument_list|(
name|parsedEntry
argument_list|,
name|repository
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|blockEntries
operator|.
name|add
argument_list|(
name|parsedEntry
argument_list|)
expr_stmt|;
block|}
block|}
name|layoutEntries
operator|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|tmpEntries
argument_list|)
expr_stmt|;
for|for
control|(
name|LayoutEntry
name|layoutEntry
range|:
name|layoutEntries
control|)
block|{
name|invalidFormatter
operator|.
name|addAll
argument_list|(
name|layoutEntry
operator|.
name|getInvalidFormatters
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|setPostFormatter (LayoutFormatter formatter)
specifier|public
name|void
name|setPostFormatter
parameter_list|(
name|LayoutFormatter
name|formatter
parameter_list|)
block|{
name|this
operator|.
name|postFormatter
operator|=
name|formatter
expr_stmt|;
block|}
DECL|method|doLayout (BibEntry bibtex, BibDatabase database)
specifier|private
name|String
name|doLayout
parameter_list|(
name|BibEntry
name|bibtex
parameter_list|,
name|BibDatabase
name|database
parameter_list|)
block|{
return|return
name|doLayout
argument_list|(
name|bibtex
argument_list|,
name|database
argument_list|,
name|Optional
operator|.
name|empty
argument_list|()
argument_list|)
return|;
block|}
DECL|method|doLayout (BibEntry bibtex, BibDatabase database, Optional<Pattern> highlightPattern)
specifier|public
name|String
name|doLayout
parameter_list|(
name|BibEntry
name|bibtex
parameter_list|,
name|BibDatabase
name|database
parameter_list|,
name|Optional
argument_list|<
name|Pattern
argument_list|>
name|highlightPattern
parameter_list|)
block|{
switch|switch
condition|(
name|type
condition|)
block|{
case|case
name|LayoutHelper
operator|.
name|IS_LAYOUT_TEXT
case|:
return|return
name|text
return|;
case|case
name|LayoutHelper
operator|.
name|IS_SIMPLE_FIELD
case|:
name|String
name|value
init|=
name|BibDatabase
operator|.
name|getResolvedField
argument_list|(
name|text
argument_list|,
name|bibtex
argument_list|,
name|database
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|==
literal|null
condition|)
block|{
name|value
operator|=
literal|""
expr_stmt|;
block|}
comment|// If a post formatter has been set, call it:
if|if
condition|(
name|postFormatter
operator|!=
literal|null
condition|)
block|{
name|value
operator|=
name|postFormatter
operator|.
name|format
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
return|return
name|value
return|;
case|case
name|LayoutHelper
operator|.
name|IS_FIELD_START
case|:
case|case
name|LayoutHelper
operator|.
name|IS_GROUP_START
case|:
name|String
name|field
decl_stmt|;
if|if
condition|(
name|type
operator|==
name|LayoutHelper
operator|.
name|IS_GROUP_START
condition|)
block|{
name|field
operator|=
name|BibDatabase
operator|.
name|getResolvedField
argument_list|(
name|text
argument_list|,
name|bibtex
argument_list|,
name|database
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|text
operator|.
name|matches
argument_list|(
literal|".*(;|(\\&+)).*"
argument_list|)
condition|)
block|{
comment|// split the strings along&,&& or ; for AND formatter
name|String
index|[]
name|parts
init|=
name|text
operator|.
name|split
argument_list|(
literal|"\\s*(;|(\\&+))\\s*"
argument_list|)
decl_stmt|;
name|field
operator|=
literal|null
expr_stmt|;
for|for
control|(
name|String
name|part
range|:
name|parts
control|)
block|{
name|field
operator|=
name|BibDatabase
operator|.
name|getResolvedField
argument_list|(
name|part
argument_list|,
name|bibtex
argument_list|,
name|database
argument_list|)
expr_stmt|;
if|if
condition|(
name|field
operator|==
literal|null
condition|)
block|{
break|break;
block|}
block|}
block|}
else|else
block|{
comment|// split the strings along |, ||  for OR formatter
name|String
index|[]
name|parts
init|=
name|text
operator|.
name|split
argument_list|(
literal|"\\s*(\\|+)\\s*"
argument_list|)
decl_stmt|;
name|field
operator|=
literal|null
expr_stmt|;
for|for
control|(
name|String
name|part
range|:
name|parts
control|)
block|{
name|field
operator|=
name|BibDatabase
operator|.
name|getResolvedField
argument_list|(
name|part
argument_list|,
name|bibtex
argument_list|,
name|database
argument_list|)
expr_stmt|;
if|if
condition|(
name|field
operator|!=
literal|null
condition|)
block|{
break|break;
block|}
block|}
block|}
if|if
condition|(
operator|(
name|field
operator|==
literal|null
operator|)
operator|||
operator|(
operator|(
name|type
operator|==
name|LayoutHelper
operator|.
name|IS_GROUP_START
operator|)
operator|&&
name|field
operator|.
name|equalsIgnoreCase
argument_list|(
name|LayoutHelper
operator|.
name|getCurrentGroup
argument_list|()
argument_list|)
operator|)
condition|)
block|{
return|return
literal|null
return|;
block|}
else|else
block|{
if|if
condition|(
name|type
operator|==
name|LayoutHelper
operator|.
name|IS_GROUP_START
condition|)
block|{
name|LayoutHelper
operator|.
name|setCurrentGroup
argument_list|(
name|field
argument_list|)
expr_stmt|;
block|}
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|(
literal|100
argument_list|)
decl_stmt|;
name|String
name|fieldText
decl_stmt|;
name|boolean
name|previousSkipped
init|=
literal|false
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
name|layoutEntries
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|fieldText
operator|=
name|layoutEntries
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|doLayout
argument_list|(
name|bibtex
argument_list|,
name|database
argument_list|)
expr_stmt|;
if|if
condition|(
name|fieldText
operator|==
literal|null
condition|)
block|{
if|if
condition|(
operator|(
name|i
operator|+
literal|1
operator|)
operator|<
name|layoutEntries
operator|.
name|size
argument_list|()
condition|)
block|{
if|if
condition|(
name|layoutEntries
operator|.
name|get
argument_list|(
name|i
operator|+
literal|1
argument_list|)
operator|.
name|doLayout
argument_list|(
name|bibtex
argument_list|,
name|database
argument_list|)
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|i
operator|++
expr_stmt|;
name|previousSkipped
operator|=
literal|true
expr_stmt|;
continue|continue;
block|}
block|}
block|}
else|else
block|{
comment|// if previous was skipped --> remove leading line
comment|// breaks
if|if
condition|(
name|previousSkipped
condition|)
block|{
name|int
name|eol
init|=
literal|0
decl_stmt|;
while|while
condition|(
operator|(
name|eol
operator|<
name|fieldText
operator|.
name|length
argument_list|()
operator|)
operator|&&
operator|(
operator|(
name|fieldText
operator|.
name|charAt
argument_list|(
name|eol
argument_list|)
operator|==
literal|'\n'
operator|)
operator|||
operator|(
name|fieldText
operator|.
name|charAt
argument_list|(
name|eol
argument_list|)
operator|==
literal|'\r'
operator|)
operator|)
condition|)
block|{
name|eol
operator|++
expr_stmt|;
block|}
if|if
condition|(
name|eol
operator|<
name|fieldText
operator|.
name|length
argument_list|()
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|fieldText
operator|.
name|substring
argument_list|(
name|eol
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
comment|/*                              * if fieldText is not null and the bibtexentry is marked                              * as a searchhit, try to highlight the searched words                              *                             */
if|if
condition|(
name|bibtex
operator|.
name|isSearchHit
argument_list|()
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|MatchesHighlighter
operator|.
name|highlightWordsWithHTML
argument_list|(
name|fieldText
argument_list|,
name|highlightPattern
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|fieldText
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|previousSkipped
operator|=
literal|false
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
case|case
name|LayoutHelper
operator|.
name|IS_FIELD_END
case|:
case|case
name|LayoutHelper
operator|.
name|IS_GROUP_END
case|:
return|return
literal|""
return|;
case|case
name|LayoutHelper
operator|.
name|IS_OPTION_FIELD
case|:
name|String
name|fieldEntry
decl_stmt|;
if|if
condition|(
literal|"bibtextype"
operator|.
name|equals
argument_list|(
name|text
argument_list|)
condition|)
block|{
name|fieldEntry
operator|=
name|bibtex
operator|.
name|getType
argument_list|()
expr_stmt|;
block|}
else|else
block|{
comment|// changed section begin - arudert
comment|// resolve field (recognized by leading backslash) or text
name|String
name|fieldText
init|=
name|text
operator|.
name|startsWith
argument_list|(
literal|"\\"
argument_list|)
condition|?
name|BibDatabase
operator|.
name|getResolvedField
argument_list|(
name|text
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
argument_list|,
name|bibtex
argument_list|,
name|database
argument_list|)
else|:
name|BibDatabase
operator|.
name|getText
argument_list|(
name|text
argument_list|,
name|database
argument_list|)
decl_stmt|;
comment|// changed section end - arudert
if|if
condition|(
name|fieldText
operator|==
literal|null
condition|)
block|{
name|fieldEntry
operator|=
literal|""
expr_stmt|;
block|}
else|else
block|{
name|fieldEntry
operator|=
name|fieldText
expr_stmt|;
block|}
block|}
if|if
condition|(
name|option
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|LayoutFormatter
name|anOption
range|:
name|option
control|)
block|{
name|fieldEntry
operator|=
name|anOption
operator|.
name|format
argument_list|(
name|fieldEntry
argument_list|)
expr_stmt|;
block|}
block|}
comment|// If a post formatter has been set, call it:
if|if
condition|(
name|postFormatter
operator|!=
literal|null
condition|)
block|{
name|fieldEntry
operator|=
name|postFormatter
operator|.
name|format
argument_list|(
name|fieldEntry
argument_list|)
expr_stmt|;
block|}
return|return
name|fieldEntry
return|;
case|case
name|LayoutHelper
operator|.
name|IS_ENCODING_NAME
case|:
comment|// Printing the encoding name is not supported in entry layouts, only
comment|// in begin/end layouts. This prevents breakage if some users depend
comment|// on a field called "encoding". We simply return this field instead:
return|return
name|BibDatabase
operator|.
name|getResolvedField
argument_list|(
literal|"encoding"
argument_list|,
name|bibtex
argument_list|,
name|database
argument_list|)
return|;
default|default:
return|return
literal|""
return|;
block|}
block|}
comment|// added section - begin (arudert)
comment|/**      * Do layout for general formatters (no bibtex-entry fields).      *      * @param databaseContext      *            Bibtex Database      * @return      */
DECL|method|doLayout (BibDatabaseContext databaseContext, Charset encoding)
specifier|public
name|String
name|doLayout
parameter_list|(
name|BibDatabaseContext
name|databaseContext
parameter_list|,
name|Charset
name|encoding
parameter_list|)
block|{
switch|switch
condition|(
name|type
condition|)
block|{
case|case
name|LayoutHelper
operator|.
name|IS_LAYOUT_TEXT
case|:
return|return
name|text
return|;
case|case
name|LayoutHelper
operator|.
name|IS_SIMPLE_FIELD
case|:
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|(
literal|"bibtex entry fields not allowed in begin or end layout"
argument_list|)
throw|;
case|case
name|LayoutHelper
operator|.
name|IS_FIELD_START
case|:
case|case
name|LayoutHelper
operator|.
name|IS_GROUP_START
case|:
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|(
literal|"field and group starts not allowed in begin or end layout"
argument_list|)
throw|;
case|case
name|LayoutHelper
operator|.
name|IS_FIELD_END
case|:
case|case
name|LayoutHelper
operator|.
name|IS_GROUP_END
case|:
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|(
literal|"field and group ends not allowed in begin or end layout"
argument_list|)
throw|;
case|case
name|LayoutHelper
operator|.
name|IS_OPTION_FIELD
case|:
name|String
name|field
init|=
name|BibDatabase
operator|.
name|getText
argument_list|(
name|text
argument_list|,
name|databaseContext
operator|.
name|getDatabase
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|option
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|LayoutFormatter
name|anOption
range|:
name|option
control|)
block|{
name|field
operator|=
name|anOption
operator|.
name|format
argument_list|(
name|field
argument_list|)
expr_stmt|;
block|}
block|}
comment|// If a post formatter has been set, call it:
if|if
condition|(
name|postFormatter
operator|!=
literal|null
condition|)
block|{
name|field
operator|=
name|postFormatter
operator|.
name|format
argument_list|(
name|field
argument_list|)
expr_stmt|;
block|}
return|return
name|field
return|;
case|case
name|LayoutHelper
operator|.
name|IS_ENCODING_NAME
case|:
return|return
name|encoding
operator|.
name|displayName
argument_list|()
return|;
case|case
name|LayoutHelper
operator|.
name|IS_FILENAME
case|:
name|File
name|f
init|=
name|databaseContext
operator|.
name|getDatabaseFile
argument_list|()
decl_stmt|;
return|return
name|f
operator|==
literal|null
condition|?
literal|""
else|:
name|f
operator|.
name|getName
argument_list|()
return|;
case|case
name|LayoutHelper
operator|.
name|IS_FILEPATH
case|:
name|File
name|f2
init|=
name|databaseContext
operator|.
name|getDatabaseFile
argument_list|()
decl_stmt|;
return|return
name|f2
operator|==
literal|null
condition|?
literal|""
else|:
name|f2
operator|.
name|getPath
argument_list|()
return|;
default|default:
break|break;
block|}
return|return
literal|""
return|;
block|}
DECL|method|doOptionField (String s)
specifier|private
name|void
name|doOptionField
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|v
init|=
name|StringUtil
operator|.
name|tokenizeToList
argument_list|(
name|s
argument_list|,
literal|"\n"
argument_list|)
decl_stmt|;
if|if
condition|(
name|v
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
name|text
operator|=
name|v
operator|.
name|get
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|text
operator|=
name|v
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|trim
argument_list|()
expr_stmt|;
name|option
operator|=
name|LayoutEntry
operator|.
name|getOptionalLayout
argument_list|(
name|v
operator|.
name|get
argument_list|(
literal|1
argument_list|)
argument_list|,
name|repository
argument_list|)
expr_stmt|;
comment|// See if there was an undefined formatter:
for|for
control|(
name|LayoutFormatter
name|anOption
range|:
name|option
control|)
block|{
if|if
condition|(
name|anOption
operator|instanceof
name|NotFoundFormatter
condition|)
block|{
name|String
name|notFound
init|=
operator|(
operator|(
name|NotFoundFormatter
operator|)
name|anOption
operator|)
operator|.
name|getNotFound
argument_list|()
decl_stmt|;
name|invalidFormatter
operator|.
name|add
argument_list|(
name|notFound
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
comment|// added section - end (arudert)
DECL|method|getLayoutFormatterByClassName (String className, JournalAbbreviationRepository repostiory)
specifier|private
specifier|static
name|LayoutFormatter
name|getLayoutFormatterByClassName
parameter_list|(
name|String
name|className
parameter_list|,
name|JournalAbbreviationRepository
name|repostiory
parameter_list|)
throws|throws
name|Exception
block|{
if|if
condition|(
name|className
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
literal|null
return|;
block|}
if|if
condition|(
literal|"JournalAbbreviator"
operator|.
name|equals
argument_list|(
name|className
argument_list|)
condition|)
block|{
return|return
operator|new
name|JournalAbbreviator
argument_list|(
name|repostiory
argument_list|)
return|;
block|}
try|try
block|{
name|String
name|prefix
init|=
literal|"net.sf.jabref.logic.layout.format."
decl_stmt|;
return|return
operator|(
name|LayoutFormatter
operator|)
name|Class
operator|.
name|forName
argument_list|(
name|prefix
operator|+
name|className
argument_list|)
operator|.
name|newInstance
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|ClassNotFoundException
name|ex
parameter_list|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Formatter not found: "
operator|+
name|className
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|InstantiationException
name|ex
parameter_list|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
name|className
operator|+
literal|" cannot be instantiated."
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|IllegalAccessException
name|ex
parameter_list|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
name|className
operator|+
literal|" cannot be accessed."
argument_list|)
throw|;
block|}
block|}
comment|/**      * Return an array of LayoutFormatters found in the given formatterName      * string (in order of appearance).      * @param repository      *      */
DECL|method|getOptionalLayout (String formatterName, JournalAbbreviationRepository repository)
specifier|private
specifier|static
name|List
argument_list|<
name|LayoutFormatter
argument_list|>
name|getOptionalLayout
parameter_list|(
name|String
name|formatterName
parameter_list|,
name|JournalAbbreviationRepository
name|repository
parameter_list|)
block|{
name|List
argument_list|<
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|formatterStrings
init|=
name|parseMethodsCalls
argument_list|(
name|formatterName
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|LayoutFormatter
argument_list|>
name|results
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|formatterStrings
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|userNameFormatter
init|=
name|NameFormatter
operator|.
name|getNameFormatters
argument_list|()
decl_stmt|;
for|for
control|(
name|List
argument_list|<
name|String
argument_list|>
name|strings
range|:
name|formatterStrings
control|)
block|{
name|String
name|className
init|=
name|strings
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
comment|// Check if this is a name formatter defined by this export filter:
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|customExportNameFormatters
operator|!=
literal|null
condition|)
block|{
name|String
name|contents
init|=
name|Globals
operator|.
name|prefs
operator|.
name|customExportNameFormatters
operator|.
name|get
argument_list|(
name|className
argument_list|)
decl_stmt|;
if|if
condition|(
name|contents
operator|!=
literal|null
condition|)
block|{
name|NameFormatter
name|nf
init|=
operator|new
name|NameFormatter
argument_list|()
decl_stmt|;
name|nf
operator|.
name|setParameter
argument_list|(
name|contents
argument_list|)
expr_stmt|;
name|results
operator|.
name|add
argument_list|(
name|nf
argument_list|)
expr_stmt|;
continue|continue;
block|}
block|}
comment|// Try to load from formatters in formatter folder
try|try
block|{
name|LayoutFormatter
name|f
init|=
name|LayoutEntry
operator|.
name|getLayoutFormatterByClassName
argument_list|(
name|className
argument_list|,
name|repository
argument_list|)
decl_stmt|;
comment|// If this formatter accepts an argument, check if we have one, and
comment|// set it if so:
if|if
condition|(
operator|(
name|f
operator|instanceof
name|ParamLayoutFormatter
operator|)
operator|&&
operator|(
name|strings
operator|.
name|size
argument_list|()
operator|>=
literal|2
operator|)
condition|)
block|{
operator|(
operator|(
name|ParamLayoutFormatter
operator|)
name|f
operator|)
operator|.
name|setArgument
argument_list|(
name|strings
operator|.
name|get
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|results
operator|.
name|add
argument_list|(
name|f
argument_list|)
expr_stmt|;
continue|continue;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Problem with formatter"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
comment|// Then check whether this is a user defined formatter
name|String
name|formatterParameter
init|=
name|userNameFormatter
operator|.
name|get
argument_list|(
name|className
argument_list|)
decl_stmt|;
if|if
condition|(
name|formatterParameter
operator|!=
literal|null
condition|)
block|{
name|NameFormatter
name|nf
init|=
operator|new
name|NameFormatter
argument_list|()
decl_stmt|;
name|nf
operator|.
name|setParameter
argument_list|(
name|formatterParameter
argument_list|)
expr_stmt|;
name|results
operator|.
name|add
argument_list|(
name|nf
argument_list|)
expr_stmt|;
continue|continue;
block|}
comment|// If not found throw exception...
comment|//return new LayoutFormatter[] {new NotFoundFormatter(className)};
name|results
operator|.
name|add
argument_list|(
operator|new
name|NotFoundFormatter
argument_list|(
name|className
argument_list|)
argument_list|)
expr_stmt|;
comment|//throw new Exception(Globals.lang("Formatter not found") + ": "+ className);
block|}
return|return
name|results
return|;
block|}
DECL|method|getInvalidFormatters ()
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getInvalidFormatters
parameter_list|()
block|{
return|return
name|invalidFormatter
return|;
block|}
DECL|method|parseMethodsCalls (String calls)
specifier|public
specifier|static
name|List
argument_list|<
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|parseMethodsCalls
parameter_list|(
name|String
name|calls
parameter_list|)
block|{
name|List
argument_list|<
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|result
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|char
index|[]
name|c
init|=
name|calls
operator|.
name|toCharArray
argument_list|()
decl_stmt|;
name|int
name|i
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|i
operator|<
name|c
operator|.
name|length
condition|)
block|{
name|int
name|start
init|=
name|i
decl_stmt|;
if|if
condition|(
name|Character
operator|.
name|isJavaIdentifierStart
argument_list|(
name|c
index|[
name|i
index|]
argument_list|)
condition|)
block|{
name|i
operator|++
expr_stmt|;
while|while
condition|(
operator|(
name|i
operator|<
name|c
operator|.
name|length
operator|)
operator|&&
operator|(
name|Character
operator|.
name|isJavaIdentifierPart
argument_list|(
name|c
index|[
name|i
index|]
argument_list|)
operator|||
operator|(
name|c
index|[
name|i
index|]
operator|==
literal|'.'
operator|)
operator|)
condition|)
block|{
name|i
operator|++
expr_stmt|;
block|}
if|if
condition|(
operator|(
name|i
operator|<
name|c
operator|.
name|length
operator|)
operator|&&
operator|(
name|c
index|[
name|i
index|]
operator|==
literal|'('
operator|)
condition|)
block|{
name|String
name|method
init|=
name|calls
operator|.
name|substring
argument_list|(
name|start
argument_list|,
name|i
argument_list|)
decl_stmt|;
comment|// Skip the brace
name|i
operator|++
expr_stmt|;
if|if
condition|(
name|i
operator|<
name|c
operator|.
name|length
condition|)
block|{
if|if
condition|(
name|c
index|[
name|i
index|]
operator|==
literal|'"'
condition|)
block|{
comment|// Parameter is in format "xxx"
comment|// Skip "
name|i
operator|++
expr_stmt|;
name|int
name|startParam
init|=
name|i
decl_stmt|;
name|i
operator|++
expr_stmt|;
name|boolean
name|escaped
init|=
literal|false
decl_stmt|;
while|while
condition|(
operator|(
operator|(
name|i
operator|+
literal|1
operator|)
operator|<
name|c
operator|.
name|length
operator|)
operator|&&
operator|!
operator|(
operator|!
name|escaped
operator|&&
operator|(
name|c
index|[
name|i
index|]
operator|==
literal|'"'
operator|)
operator|&&
operator|(
name|c
index|[
name|i
operator|+
literal|1
index|]
operator|==
literal|')'
operator|)
operator|)
condition|)
block|{
if|if
condition|(
name|c
index|[
name|i
index|]
operator|==
literal|'\\'
condition|)
block|{
name|escaped
operator|=
operator|!
name|escaped
expr_stmt|;
block|}
else|else
block|{
name|escaped
operator|=
literal|false
expr_stmt|;
block|}
name|i
operator|++
expr_stmt|;
block|}
name|String
name|param
init|=
name|calls
operator|.
name|substring
argument_list|(
name|startParam
argument_list|,
name|i
argument_list|)
decl_stmt|;
name|result
operator|.
name|add
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|method
argument_list|,
name|param
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// Parameter is in format xxx
name|int
name|startParam
init|=
name|i
decl_stmt|;
while|while
condition|(
operator|(
name|i
operator|<
name|c
operator|.
name|length
operator|)
operator|&&
operator|(
name|c
index|[
name|i
index|]
operator|!=
literal|')'
operator|)
condition|)
block|{
name|i
operator|++
expr_stmt|;
block|}
name|String
name|param
init|=
name|calls
operator|.
name|substring
argument_list|(
name|startParam
argument_list|,
name|i
argument_list|)
decl_stmt|;
name|result
operator|.
name|add
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|method
argument_list|,
name|param
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
comment|// Incorrectly terminated open brace
name|result
operator|.
name|add
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|method
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|String
name|method
init|=
name|calls
operator|.
name|substring
argument_list|(
name|start
argument_list|,
name|i
argument_list|)
decl_stmt|;
name|result
operator|.
name|add
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|method
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|i
operator|++
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
block|}
end_class

end_unit

