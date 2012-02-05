begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.autocompleter
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|BibtexEntry
import|;
end_import

begin_comment
comment|/**  * Crossref autocompleter stores info from the key field.  *   * @author kahlert, cordes  *   */
end_comment

begin_class
DECL|class|CrossrefAutoCompleter
specifier|public
class|class
name|CrossrefAutoCompleter
extends|extends
name|AbstractAutoCompleter
block|{
DECL|field|_fieldName
specifier|public
name|String
name|_fieldName
decl_stmt|;
comment|/** 	 * @see AutoCompleterFactory 	 */
DECL|method|CrossrefAutoCompleter (String fieldName)
specifier|protected
name|CrossrefAutoCompleter
parameter_list|(
name|String
name|fieldName
parameter_list|)
block|{
name|_fieldName
operator|=
name|fieldName
expr_stmt|;
block|}
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
DECL|method|complete (String s)
specifier|public
name|String
index|[]
name|complete
parameter_list|(
name|String
name|s
parameter_list|)
block|{
return|return
name|super
operator|.
name|complete
argument_list|(
name|s
argument_list|)
return|;
block|}
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
operator|!=
literal|null
condition|)
block|{
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
name|addWordToIndex
argument_list|(
name|key
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

