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
name|BibtexEntry
import|;
end_import

begin_comment
comment|/**  * Delivers possible completions for a given string based on the key fields of the added items.  *  * @author kahlert, cordes  */
end_comment

begin_class
DECL|class|BibtexKeyAutoCompleter
class|class
name|BibtexKeyAutoCompleter
extends|extends
name|AbstractAutoCompleter
block|{
DECL|method|BibtexKeyAutoCompleter (AutoCompletePreferences preferences)
specifier|public
name|BibtexKeyAutoCompleter
parameter_list|(
name|AutoCompletePreferences
name|preferences
parameter_list|)
block|{
name|super
argument_list|(
name|preferences
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
comment|/**      * {@inheritDoc}      * The bibtex key of the entry will be added to the index.      */
annotation|@
name|Override
DECL|method|addBibtexEntry (BibtexEntry entry)
specifier|public
name|void
name|addBibtexEntry
parameter_list|(
name|BibtexEntry
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
name|String
name|key
init|=
name|entry
operator|.
name|getCiteKey
argument_list|()
decl_stmt|;
if|if
condition|(
name|key
operator|!=
literal|null
condition|)
block|{
name|addItemToIndex
argument_list|(
name|key
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|getLengthOfShortestWordToAdd ()
specifier|protected
name|int
name|getLengthOfShortestWordToAdd
parameter_list|()
block|{
return|return
literal|1
return|;
block|}
block|}
end_class

end_unit

