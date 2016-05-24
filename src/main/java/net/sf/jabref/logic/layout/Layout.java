begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|List
argument_list|<
name|LayoutEntry
argument_list|>
name|layoutEntries
decl_stmt|;
DECL|field|missingFormatters
specifier|private
specifier|final
name|List
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
DECL|method|Layout (List<StringInt> parsedEntries, JournalAbbreviationRepository repository)
specifier|public
name|Layout
parameter_list|(
name|List
argument_list|<
name|StringInt
argument_list|>
name|parsedEntries
parameter_list|,
name|JournalAbbreviationRepository
name|repository
parameter_list|)
block|{
name|List
argument_list|<
name|LayoutEntry
argument_list|>
name|tmpEntries
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|parsedEntries
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
name|List
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
comment|// Do nothing
break|break;
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
name|IS_FIELD_END
condition|?
name|LayoutHelper
operator|.
name|IS_FIELD_START
else|:
name|LayoutHelper
operator|.
name|IS_GROUP_START
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
name|debug
argument_list|(
name|blockStart
operator|+
literal|'\n'
operator|+
name|parsedEntry
operator|.
name|s
argument_list|)
expr_stmt|;
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Nested field/group entries are not implemented!"
argument_list|)
expr_stmt|;
name|Thread
operator|.
name|dumpStack
argument_list|()
expr_stmt|;
block|}
block|}
break|break;
default|default:
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
name|missingFormatters
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
DECL|method|doLayout (BibEntry bibtex, BibDatabase database)
specifier|public
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
comment|/**      * Returns the processed bibtex entry. If the database argument is      * null, no string references will be resolved. Otherwise all valid      * string references will be replaced by the strings' contents. Even      * recursive string references are resolved.      */
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
name|highlightPattern
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
name|databaseContext
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
comment|// added section - end (arudert)
DECL|method|getMissingFormatters ()
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getMissingFormatters
parameter_list|()
block|{
return|return
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|missingFormatters
argument_list|)
return|;
block|}
block|}
end_class

end_unit

