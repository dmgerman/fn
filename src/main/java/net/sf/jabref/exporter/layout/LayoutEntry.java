begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.exporter.layout
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|exporter
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
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Vector
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
name|*
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
name|exporter
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
name|gui
operator|.
name|preftabs
operator|.
name|NameFormatterTab
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
name|Encodings
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
name|model
operator|.
name|database
operator|.
name|BibtexDatabase
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
name|BibtexEntry
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
name|util
operator|.
name|Util
import|;
end_import

begin_class
DECL|class|LayoutEntry
class|class
name|LayoutEntry
block|{
DECL|field|option
specifier|private
name|LayoutFormatter
index|[]
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
name|LayoutEntry
index|[]
name|layoutEntries
decl_stmt|;
DECL|field|type
specifier|private
specifier|final
name|int
name|type
decl_stmt|;
DECL|field|classPrefix
specifier|private
specifier|final
name|String
name|classPrefix
decl_stmt|;
DECL|field|invalidFormatter
specifier|private
name|ArrayList
argument_list|<
name|String
argument_list|>
name|invalidFormatter
decl_stmt|;
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
DECL|method|LayoutEntry (StringInt si, String classPrefix_)
specifier|public
name|LayoutEntry
parameter_list|(
name|StringInt
name|si
parameter_list|,
name|String
name|classPrefix_
parameter_list|)
throws|throws
name|Exception
block|{
name|type
operator|=
name|si
operator|.
name|i
expr_stmt|;
name|classPrefix
operator|=
name|classPrefix_
expr_stmt|;
if|if
condition|(
name|si
operator|.
name|i
operator|==
name|LayoutHelper
operator|.
name|IS_LAYOUT_TEXT
condition|)
block|{
name|text
operator|=
name|si
operator|.
name|s
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|si
operator|.
name|i
operator|==
name|LayoutHelper
operator|.
name|IS_SIMPLE_FIELD
condition|)
block|{
name|text
operator|=
name|si
operator|.
name|s
operator|.
name|trim
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|(
name|si
operator|.
name|i
operator|==
name|LayoutHelper
operator|.
name|IS_FIELD_START
operator|)
operator|||
operator|(
name|si
operator|.
name|i
operator|==
name|LayoutHelper
operator|.
name|IS_FIELD_END
operator|)
condition|)
block|{         }
elseif|else
if|if
condition|(
name|si
operator|.
name|i
operator|==
name|LayoutHelper
operator|.
name|IS_OPTION_FIELD
condition|)
block|{
name|Vector
argument_list|<
name|String
argument_list|>
name|v
init|=
operator|new
name|Vector
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|WSITools
operator|.
name|tokenize
argument_list|(
name|v
argument_list|,
name|si
operator|.
name|s
argument_list|,
literal|"\n"
argument_list|)
expr_stmt|;
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
name|classPrefix
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
if|if
condition|(
name|invalidFormatter
operator|==
literal|null
condition|)
block|{
name|invalidFormatter
operator|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
block|}
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
block|}
DECL|method|LayoutEntry (Vector<StringInt> parsedEntries, String classPrefix_, int layoutType)
specifier|public
name|LayoutEntry
parameter_list|(
name|Vector
argument_list|<
name|StringInt
argument_list|>
name|parsedEntries
parameter_list|,
name|String
name|classPrefix_
parameter_list|,
name|int
name|layoutType
parameter_list|)
throws|throws
name|Exception
block|{
name|classPrefix
operator|=
name|classPrefix_
expr_stmt|;
name|String
name|blockStart
decl_stmt|;
name|String
name|blockEnd
decl_stmt|;
name|StringInt
name|si
decl_stmt|;
name|Vector
argument_list|<
name|StringInt
argument_list|>
name|blockEntries
init|=
literal|null
decl_stmt|;
name|Vector
argument_list|<
name|LayoutEntry
argument_list|>
name|tmpEntries
init|=
operator|new
name|Vector
argument_list|<
name|LayoutEntry
argument_list|>
argument_list|()
decl_stmt|;
name|LayoutEntry
name|le
decl_stmt|;
name|si
operator|=
name|parsedEntries
operator|.
name|get
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|blockStart
operator|=
name|si
operator|.
name|s
expr_stmt|;
name|si
operator|=
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
expr_stmt|;
name|blockEnd
operator|=
name|si
operator|.
name|s
expr_stmt|;
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
name|si
operator|.
name|s
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<
operator|(
name|parsedEntries
operator|.
name|size
argument_list|()
operator|-
literal|1
operator|)
condition|;
name|i
operator|++
control|)
block|{
name|si
operator|=
name|parsedEntries
operator|.
name|get
argument_list|(
name|i
argument_list|)
expr_stmt|;
comment|// System.out.println("PARSED-ENTRY: "+si.s+"="+si.i);
if|if
condition|(
operator|(
name|si
operator|.
name|i
operator|==
name|LayoutHelper
operator|.
name|IS_LAYOUT_TEXT
operator|)
operator|||
operator|(
name|si
operator|.
name|i
operator|==
name|LayoutHelper
operator|.
name|IS_SIMPLE_FIELD
operator|)
condition|)
block|{             }
elseif|else
if|if
condition|(
operator|(
name|si
operator|.
name|i
operator|==
name|LayoutHelper
operator|.
name|IS_FIELD_START
operator|)
operator|||
operator|(
name|si
operator|.
name|i
operator|==
name|LayoutHelper
operator|.
name|IS_GROUP_START
operator|)
condition|)
block|{
name|blockEntries
operator|=
operator|new
name|Vector
argument_list|<
name|StringInt
argument_list|>
argument_list|()
expr_stmt|;
name|blockStart
operator|=
name|si
operator|.
name|s
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|(
name|si
operator|.
name|i
operator|==
name|LayoutHelper
operator|.
name|IS_FIELD_END
operator|)
operator|||
operator|(
name|si
operator|.
name|i
operator|==
name|LayoutHelper
operator|.
name|IS_GROUP_END
operator|)
condition|)
block|{
if|if
condition|(
name|blockStart
operator|.
name|equals
argument_list|(
name|si
operator|.
name|s
argument_list|)
condition|)
block|{
name|blockEntries
operator|.
name|add
argument_list|(
name|si
argument_list|)
expr_stmt|;
if|if
condition|(
name|si
operator|.
name|i
operator|==
name|LayoutHelper
operator|.
name|IS_GROUP_END
condition|)
block|{
name|le
operator|=
operator|new
name|LayoutEntry
argument_list|(
name|blockEntries
argument_list|,
name|classPrefix
argument_list|,
name|LayoutHelper
operator|.
name|IS_GROUP_START
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|le
operator|=
operator|new
name|LayoutEntry
argument_list|(
name|blockEntries
argument_list|,
name|classPrefix
argument_list|,
name|LayoutHelper
operator|.
name|IS_FIELD_START
argument_list|)
expr_stmt|;
block|}
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
literal|"Nested field entries are not implemented !!!"
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|si
operator|.
name|i
operator|==
name|LayoutHelper
operator|.
name|IS_OPTION_FIELD
condition|)
block|{             }
comment|// else if (si.i == LayoutHelper.IS_OPTION_FIELD_PARAM)
comment|// {
comment|// }
if|if
condition|(
name|blockEntries
operator|==
literal|null
condition|)
block|{
comment|// System.out.println("BLOCK ADD: "+si.s+"="+si.i);
name|tmpEntries
operator|.
name|add
argument_list|(
operator|new
name|LayoutEntry
argument_list|(
name|si
argument_list|,
name|classPrefix
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
name|si
argument_list|)
expr_stmt|;
block|}
block|}
name|layoutEntries
operator|=
operator|new
name|LayoutEntry
index|[
name|tmpEntries
operator|.
name|size
argument_list|()
index|]
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|tmpEntries
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|layoutEntries
index|[
name|i
index|]
operator|=
name|tmpEntries
operator|.
name|get
argument_list|(
name|i
argument_list|)
expr_stmt|;
comment|// Note if one of the entries has an invalid formatter:
if|if
condition|(
name|layoutEntries
index|[
name|i
index|]
operator|.
name|isInvalidFormatter
argument_list|()
condition|)
block|{
if|if
condition|(
name|invalidFormatter
operator|==
literal|null
condition|)
block|{
name|invalidFormatter
operator|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
name|invalidFormatter
operator|.
name|addAll
argument_list|(
name|layoutEntries
index|[
name|i
index|]
operator|.
name|getInvalidFormatters
argument_list|()
argument_list|)
expr_stmt|;
block|}
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
DECL|method|doLayout (BibtexEntry bibtex, BibtexDatabase database)
specifier|private
name|String
name|doLayout
parameter_list|(
name|BibtexEntry
name|bibtex
parameter_list|,
name|BibtexDatabase
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
literal|null
argument_list|)
return|;
block|}
DECL|method|doLayout (BibtexEntry bibtex, BibtexDatabase database, ArrayList<String> wordsToHighlight)
specifier|public
name|String
name|doLayout
parameter_list|(
name|BibtexEntry
name|bibtex
parameter_list|,
name|BibtexDatabase
name|database
parameter_list|,
name|ArrayList
argument_list|<
name|String
argument_list|>
name|wordsToHighlight
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
name|BibtexDatabase
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
block|{
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
name|BibtexDatabase
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
name|BibtexDatabase
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
name|BibtexDatabase
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
name|length
condition|;
name|i
operator|++
control|)
block|{
name|fieldText
operator|=
name|layoutEntries
index|[
name|i
index|]
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
name|length
condition|)
block|{
if|if
condition|(
name|layoutEntries
index|[
name|i
operator|+
literal|1
index|]
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
comment|//System.out.println("ENTRY-BLOCK: " +
comment|//layoutEntries[i].doLayout(bibtex));
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
name|highlightWords
argument_list|(
name|fieldText
argument_list|,
name|wordsToHighlight
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
name|text
operator|.
name|equals
argument_list|(
literal|"bibtextype"
argument_list|)
condition|)
block|{
name|fieldEntry
operator|=
name|bibtex
operator|.
name|getType
argument_list|()
operator|.
name|getName
argument_list|()
expr_stmt|;
block|}
else|else
block|{
comment|// changed section begin - arudert
comment|// resolve field (recognized by leading backslash) or text
name|String
name|field
init|=
name|text
operator|.
name|startsWith
argument_list|(
literal|"\\"
argument_list|)
condition|?
name|BibtexDatabase
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
name|BibtexDatabase
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
name|field
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
name|field
expr_stmt|;
block|}
block|}
comment|//System.out.println("OPTION: "+option);
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
name|BibtexDatabase
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
comment|/**      * Do layout for general formatters (no bibtex-entry fields).      *      * @param database      *            Bibtex Database      * @return      */
DECL|method|doLayout (BibtexDatabase database, String encoding)
specifier|public
name|String
name|doLayout
parameter_list|(
name|BibtexDatabase
name|database
parameter_list|,
name|String
name|encoding
parameter_list|)
block|{
if|if
condition|(
name|type
operator|==
name|LayoutHelper
operator|.
name|IS_LAYOUT_TEXT
condition|)
block|{
return|return
name|text
return|;
block|}
elseif|else
if|if
condition|(
name|type
operator|==
name|LayoutHelper
operator|.
name|IS_SIMPLE_FIELD
condition|)
block|{
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|(
literal|"bibtex entry fields not allowed in begin or end layout"
argument_list|)
throw|;
block|}
elseif|else
if|if
condition|(
operator|(
name|type
operator|==
name|LayoutHelper
operator|.
name|IS_FIELD_START
operator|)
operator|||
operator|(
name|type
operator|==
name|LayoutHelper
operator|.
name|IS_GROUP_START
operator|)
condition|)
block|{
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|(
literal|"field and group starts not allowed in begin or end layout"
argument_list|)
throw|;
block|}
elseif|else
if|if
condition|(
operator|(
name|type
operator|==
name|LayoutHelper
operator|.
name|IS_FIELD_END
operator|)
operator|||
operator|(
name|type
operator|==
name|LayoutHelper
operator|.
name|IS_GROUP_END
operator|)
condition|)
block|{
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|(
literal|"field and group ends not allowed in begin or end layout"
argument_list|)
throw|;
block|}
elseif|else
if|if
condition|(
name|type
operator|==
name|LayoutHelper
operator|.
name|IS_OPTION_FIELD
condition|)
block|{
name|String
name|field
init|=
name|BibtexDatabase
operator|.
name|getText
argument_list|(
name|text
argument_list|,
name|database
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
block|}
elseif|else
if|if
condition|(
name|type
operator|==
name|LayoutHelper
operator|.
name|IS_ENCODING_NAME
condition|)
block|{
comment|// Try to translate from Java encoding name to common name:
name|String
name|commonName
init|=
name|Encodings
operator|.
name|ENCODING_NAMES_LOOKUP
operator|.
name|get
argument_list|(
name|encoding
argument_list|)
decl_stmt|;
return|return
name|commonName
operator|!=
literal|null
condition|?
name|commonName
else|:
name|encoding
return|;
block|}
elseif|else
if|if
condition|(
name|type
operator|==
name|LayoutHelper
operator|.
name|IS_FILENAME
condition|)
block|{
name|File
name|f
init|=
name|Globals
operator|.
name|prefs
operator|.
name|databaseFile
decl_stmt|;
return|return
name|f
operator|!=
literal|null
condition|?
name|f
operator|.
name|getName
argument_list|()
else|:
literal|""
return|;
block|}
elseif|else
if|if
condition|(
name|type
operator|==
name|LayoutHelper
operator|.
name|IS_FILEPATH
condition|)
block|{
name|File
name|f
init|=
name|Globals
operator|.
name|prefs
operator|.
name|databaseFile
decl_stmt|;
return|return
name|f
operator|!=
literal|null
condition|?
name|f
operator|.
name|getPath
argument_list|()
else|:
literal|""
return|;
block|}
return|return
literal|""
return|;
block|}
comment|// added section - end (arudert)
DECL|method|getLayoutFormatterByClassName (String className, String classPrefix)
specifier|private
specifier|static
name|LayoutFormatter
name|getLayoutFormatterByClassName
parameter_list|(
name|String
name|className
parameter_list|,
name|String
name|classPrefix
parameter_list|)
throws|throws
name|Exception
block|{
if|if
condition|(
operator|!
name|className
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
try|try
block|{
try|try
block|{
return|return
operator|(
name|LayoutFormatter
operator|)
name|Class
operator|.
name|forName
argument_list|(
name|classPrefix
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
name|Throwable
name|ex2
parameter_list|)
block|{
return|return
operator|(
name|LayoutFormatter
operator|)
name|Class
operator|.
name|forName
argument_list|(
name|className
argument_list|)
operator|.
name|newInstance
argument_list|()
return|;
block|}
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"Formatter not found"
argument_list|)
operator|+
literal|": "
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
return|return
literal|null
return|;
block|}
comment|/**      * Return an array of LayoutFormatters found in the given formatterName      * string (in order of appearance).      *      */
DECL|method|getOptionalLayout (String formatterName, String classPrefix)
specifier|private
specifier|static
name|LayoutFormatter
index|[]
name|getOptionalLayout
parameter_list|(
name|String
name|formatterName
parameter_list|,
name|String
name|classPrefix
parameter_list|)
block|{
name|ArrayList
argument_list|<
name|String
index|[]
argument_list|>
name|formatterStrings
init|=
name|Util
operator|.
name|parseMethodsCalls
argument_list|(
name|formatterName
argument_list|)
decl_stmt|;
name|ArrayList
argument_list|<
name|LayoutFormatter
argument_list|>
name|results
init|=
operator|new
name|ArrayList
argument_list|<
name|LayoutFormatter
argument_list|>
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
name|NameFormatterTab
operator|.
name|getNameFormatters
argument_list|()
decl_stmt|;
for|for
control|(
name|String
index|[]
name|strings
range|:
name|formatterStrings
control|)
block|{
name|String
name|className
init|=
name|strings
index|[
literal|0
index|]
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
name|classPrefix
argument_list|)
decl_stmt|;
comment|// If this formatter accepts an argument, check if we have one, and
comment|// set it if so:
if|if
condition|(
name|f
operator|instanceof
name|ParamLayoutFormatter
condition|)
block|{
if|if
condition|(
name|strings
operator|.
name|length
operator|>=
literal|2
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
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
block|}
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
name|ignored
parameter_list|)
block|{             }
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
operator|.
name|toArray
argument_list|(
operator|new
name|LayoutFormatter
index|[
name|results
operator|.
name|size
argument_list|()
index|]
argument_list|)
return|;
block|}
DECL|method|isInvalidFormatter ()
specifier|public
name|boolean
name|isInvalidFormatter
parameter_list|()
block|{
return|return
name|invalidFormatter
operator|!=
literal|null
return|;
block|}
DECL|method|getInvalidFormatters ()
specifier|public
name|ArrayList
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
comment|/**      * Will return the text that was called by the method with HTML tags      * to highlight each word the user has searched for and will skip      * the highlight process if the first Char isn't a letter or a digit.      *      * This check is a quick hack to avoid highlighting of HTML tags      * It does not always work, but it does its job mostly      *      * @param text This is a String in which we search for different words      * @param toHighlight List of all words which must be highlighted      *      * @return String that was called by the method, with HTML Tags if a word was found      */
DECL|method|highlightWords (String text, ArrayList<String> toHighlight)
specifier|private
name|String
name|highlightWords
parameter_list|(
name|String
name|text
parameter_list|,
name|ArrayList
argument_list|<
name|String
argument_list|>
name|toHighlight
parameter_list|)
block|{
if|if
condition|(
name|toHighlight
operator|==
literal|null
condition|)
block|{
return|return
name|text
return|;
block|}
name|Matcher
name|matcher
init|=
name|Util
operator|.
name|getPatternForWords
argument_list|(
name|toHighlight
argument_list|)
operator|.
name|matcher
argument_list|(
name|text
argument_list|)
decl_stmt|;
if|if
condition|(
name|Character
operator|.
name|isLetterOrDigit
argument_list|(
name|text
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
argument_list|)
condition|)
block|{
name|String
name|hlColor
init|=
name|HIGHLIGHT_COLOR
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
name|String
name|found
decl_stmt|;
while|while
condition|(
name|matcher
operator|.
name|find
argument_list|()
condition|)
block|{
name|matcher
operator|.
name|end
argument_list|()
expr_stmt|;
name|found
operator|=
name|matcher
operator|.
name|group
argument_list|()
expr_stmt|;
comment|// color the search keyword	-
comment|// put first String Part and then html + word + html to a StringBuffer
name|matcher
operator|.
name|appendReplacement
argument_list|(
name|sb
argument_list|,
literal|"<span style=\"background-color:"
operator|+
name|hlColor
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
block|}
return|return
name|text
return|;
block|}
block|}
end_class

end_unit

