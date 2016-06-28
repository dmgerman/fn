begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright (C) 2003-2016 JabRef contributors.  * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *  * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *  * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.autocompleter
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|autocompleter
package|;
end_package

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
name|Abbreviation
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
name|JournalAbbreviationLoader
import|;
end_import

begin_class
DECL|class|JournalAutoCompleter
specifier|public
class|class
name|JournalAutoCompleter
extends|extends
name|EntireFieldAutoCompleter
block|{
DECL|field|abbreviationLoader
specifier|private
specifier|final
name|JournalAbbreviationLoader
name|abbreviationLoader
decl_stmt|;
DECL|method|JournalAutoCompleter (String fieldName, AutoCompletePreferences preferences, JournalAbbreviationLoader abbreviationLoader)
name|JournalAutoCompleter
parameter_list|(
name|String
name|fieldName
parameter_list|,
name|AutoCompletePreferences
name|preferences
parameter_list|,
name|JournalAbbreviationLoader
name|abbreviationLoader
parameter_list|)
block|{
name|super
argument_list|(
name|fieldName
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|this
operator|.
name|abbreviationLoader
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|abbreviationLoader
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|complete (String toComplete)
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|complete
parameter_list|(
name|String
name|toComplete
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|completions
init|=
name|super
operator|.
name|complete
argument_list|(
name|toComplete
argument_list|)
decl_stmt|;
comment|// Also return journal names in the journal abbreviation list
for|for
control|(
name|Abbreviation
name|abbreviation
range|:
name|abbreviationLoader
operator|.
name|getRepository
argument_list|()
operator|.
name|getAbbreviations
argument_list|()
control|)
block|{
if|if
condition|(
name|abbreviation
operator|.
name|getName
argument_list|()
operator|.
name|startsWith
argument_list|(
name|toComplete
argument_list|)
condition|)
block|{
name|completions
operator|.
name|add
argument_list|(
name|abbreviation
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|completions
return|;
block|}
block|}
end_class

end_unit

