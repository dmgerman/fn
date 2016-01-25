begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|StringTokenizer
import|;
end_import

begin_comment
comment|/**  * Delivers possible completions for a given string.  * Stores all words in the given field which are separated by SEPARATING_CHARS.  *  * @author kahlert, cordes  */
end_comment

begin_class
DECL|class|DefaultAutoCompleter
class|class
name|DefaultAutoCompleter
extends|extends
name|AbstractAutoCompleter
block|{
DECL|field|fieldName
specifier|private
specifier|final
name|String
name|fieldName
decl_stmt|;
DECL|field|SEPARATING_CHARS
specifier|private
specifier|static
specifier|final
name|String
name|SEPARATING_CHARS
init|=
literal|";,\n "
decl_stmt|;
comment|/**      * @see AutoCompleterFactory      */
DECL|method|DefaultAutoCompleter (String fieldName, AutoCompletePreferences preferences)
name|DefaultAutoCompleter
parameter_list|(
name|String
name|fieldName
parameter_list|,
name|AutoCompletePreferences
name|preferences
parameter_list|)
block|{
name|super
argument_list|(
name|preferences
argument_list|)
expr_stmt|;
name|this
operator|.
name|fieldName
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|fieldName
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|isSingleUnitField ()
specifier|public
name|boolean
name|isSingleUnitField
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
comment|/**      * {@inheritDoc}      * Stores all words in the given field which are separated by SEPARATING_CHARS.      */
annotation|@
name|Override
DECL|method|addBibtexEntry (BibEntry entry)
specifier|public
name|void
name|addBibtexEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
if|if
condition|(
name|entry
operator|==
literal|null
condition|)
block|{
return|return;
block|}
name|entry
operator|.
name|getFieldOptional
argument_list|(
name|fieldName
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|fieldValue
lambda|->
block|{
name|StringTokenizer
name|tok
init|=
operator|new
name|StringTokenizer
argument_list|(
name|fieldValue
argument_list|,
name|SEPARATING_CHARS
argument_list|)
decl_stmt|;
while|while
condition|(
name|tok
operator|.
name|hasMoreTokens
argument_list|()
condition|)
block|{
name|addItemToIndex
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

