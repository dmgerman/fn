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
name|util
operator|.
name|Vector
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

begin_comment
comment|/**  * Main class for formatting DOCUMENT ME!  */
end_comment

begin_class
DECL|class|Layout
specifier|public
class|class
name|Layout
block|{
DECL|field|layoutEntries
specifier|private
specifier|final
name|LayoutEntry
index|[]
name|layoutEntries
decl_stmt|;
DECL|field|missingFormatters
specifier|private
specifier|final
name|ArrayList
argument_list|<
name|String
argument_list|>
name|missingFormatters
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
name|Layout
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|method|Layout (Vector<StringInt> parsedEntries, String classPrefix)
specifier|public
name|Layout
parameter_list|(
name|Vector
argument_list|<
name|StringInt
argument_list|>
name|parsedEntries
parameter_list|,
name|String
name|classPrefix
parameter_list|)
throws|throws
name|Exception
block|{
name|StringInt
name|si
decl_stmt|;
name|Vector
argument_list|<
name|LayoutEntry
argument_list|>
name|tmpEntries
init|=
operator|new
name|Vector
argument_list|<>
argument_list|(
name|parsedEntries
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
name|Vector
argument_list|<
name|StringInt
argument_list|>
name|blockEntries
init|=
literal|null
decl_stmt|;
name|LayoutEntry
name|le
decl_stmt|;
name|String
name|blockStart
init|=
literal|null
decl_stmt|;
for|for
control|(
name|StringInt
name|parsedEntry
range|:
name|parsedEntries
control|)
block|{
name|si
operator|=
name|parsedEntry
expr_stmt|;
comment|// TODO: Rewrite using switch
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
block|{
comment|// Do nothing
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
name|IS_FIELD_START
condition|)
block|{
name|blockEntries
operator|=
operator|new
name|Vector
argument_list|<>
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
name|si
operator|.
name|i
operator|==
name|LayoutHelper
operator|.
name|IS_FIELD_END
condition|)
block|{
if|if
condition|(
operator|(
name|blockStart
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|blockEntries
operator|!=
literal|null
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
name|debug
argument_list|(
name|blockStart
operator|+
literal|'\n'
operator|+
name|si
operator|.
name|s
argument_list|)
expr_stmt|;
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Nested field entries are not implemented !!!"
argument_list|)
expr_stmt|;
name|Thread
operator|.
name|dumpStack
argument_list|()
expr_stmt|;
block|}
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
name|IS_GROUP_START
condition|)
block|{
name|blockEntries
operator|=
operator|new
name|Vector
argument_list|<>
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
name|si
operator|.
name|i
operator|==
name|LayoutHelper
operator|.
name|IS_GROUP_END
condition|)
block|{
if|if
condition|(
operator|(
name|blockStart
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|blockEntries
operator|!=
literal|null
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
name|Thread
operator|.
name|dumpStack
argument_list|()
expr_stmt|;
block|}
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
block|{
comment|// Do nothing
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
name|missingFormatters
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
comment|//System.out.println(layoutEntries[i].text);
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
for|for
control|(
name|LayoutEntry
name|layoutEntry
range|:
name|layoutEntries
control|)
block|{
name|layoutEntry
operator|.
name|setPostFormatter
argument_list|(
name|formatter
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|doLayout (BibtexEntry bibtex, BibtexDatabase database)
specifier|public
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
comment|/**      * Returns the processed bibtex entry. If the database argument is      * null, no string references will be resolved. Otherwise all valid      * string references will be replaced by the strings' contents. Even      * recursive string references are resolved.      */
DECL|method|doLayout (BibtexEntry bibtex, BibtexDatabase database, List<String> wordsToHighlight)
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
name|List
argument_list|<
name|String
argument_list|>
name|wordsToHighlight
parameter_list|)
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|(
literal|100
argument_list|)
decl_stmt|;
for|for
control|(
name|LayoutEntry
name|layoutEntry
range|:
name|layoutEntries
control|)
block|{
name|String
name|fieldText
init|=
name|layoutEntry
operator|.
name|doLayout
argument_list|(
name|bibtex
argument_list|,
name|database
argument_list|,
name|wordsToHighlight
argument_list|)
decl_stmt|;
comment|// 2005.05.05 M. Alver
comment|// The following change means we treat null fields as "". This is to fix the
comment|// problem of whitespace disappearing after missing fields. Hoping there are
comment|// no side effects.
if|if
condition|(
name|fieldText
operator|==
literal|null
condition|)
block|{
name|fieldText
operator|=
literal|""
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
name|fieldText
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Returns the processed text. If the database argument is      * null, no string references will be resolved. Otherwise all valid      * string references will be replaced by the strings' contents. Even      * recursive string references are resolved.      */
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
comment|//System.out.println("LAYOUT: " + bibtex.getId());
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
name|LayoutEntry
name|layoutEntry
range|:
name|layoutEntries
control|)
block|{
name|fieldText
operator|=
name|layoutEntry
operator|.
name|doLayout
argument_list|(
name|database
argument_list|,
name|encoding
argument_list|)
expr_stmt|;
if|if
condition|(
name|fieldText
operator|==
literal|null
condition|)
block|{
name|fieldText
operator|=
literal|""
expr_stmt|;
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
comment|// added section - end (arudert)
DECL|method|getMissingFormatters ()
specifier|public
name|ArrayList
argument_list|<
name|String
argument_list|>
name|getMissingFormatters
parameter_list|()
block|{
return|return
name|missingFormatters
return|;
block|}
block|}
end_class

end_unit

