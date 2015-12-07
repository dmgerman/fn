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
name|java
operator|.
name|util
operator|.
name|Objects
import|;
end_import

begin_comment
comment|/**  * Returns an autocompleter to a given fieldname.  *  * @author kahlert, cordes  */
end_comment

begin_class
DECL|class|AutoCompleterFactory
specifier|public
class|class
name|AutoCompleterFactory
block|{
DECL|field|preferences
specifier|private
specifier|final
name|AutoCompletePreferences
name|preferences
decl_stmt|;
DECL|method|AutoCompleterFactory (AutoCompletePreferences preferences)
specifier|public
name|AutoCompleterFactory
parameter_list|(
name|AutoCompletePreferences
name|preferences
parameter_list|)
block|{
name|this
operator|.
name|preferences
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|preferences
argument_list|)
expr_stmt|;
block|}
DECL|method|getFor (String fieldName)
specifier|public
name|AutoCompleter
argument_list|<
name|String
argument_list|>
name|getFor
parameter_list|(
name|String
name|fieldName
parameter_list|)
block|{
if|if
condition|(
literal|"author"
operator|.
name|equals
argument_list|(
name|fieldName
argument_list|)
operator|||
literal|"editor"
operator|.
name|equals
argument_list|(
name|fieldName
argument_list|)
condition|)
block|{
return|return
operator|new
name|NameFieldAutoCompleter
argument_list|(
name|fieldName
argument_list|,
name|preferences
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
literal|"crossref"
operator|.
name|equals
argument_list|(
name|fieldName
argument_list|)
condition|)
block|{
return|return
operator|new
name|BibtexKeyAutoCompleter
argument_list|(
name|preferences
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
literal|"journal"
operator|.
name|equals
argument_list|(
name|fieldName
argument_list|)
operator|||
literal|"publisher"
operator|.
name|equals
argument_list|(
name|fieldName
argument_list|)
condition|)
block|{
return|return
operator|new
name|EntireFieldAutoCompleter
argument_list|(
name|fieldName
argument_list|,
name|preferences
argument_list|)
return|;
block|}
else|else
block|{
return|return
operator|new
name|DefaultAutoCompleter
argument_list|(
name|fieldName
argument_list|,
name|preferences
argument_list|)
return|;
block|}
block|}
DECL|method|getFor (String fieldName, String secondFieldName)
specifier|public
name|AutoCompleter
argument_list|<
name|String
argument_list|>
name|getFor
parameter_list|(
name|String
name|fieldName
parameter_list|,
name|String
name|secondFieldName
parameter_list|)
block|{
return|return
operator|new
name|NameFieldAutoCompleter
argument_list|(
operator|new
name|String
index|[]
block|{
name|fieldName
block|,
name|secondFieldName
block|}
argument_list|,
literal|true
argument_list|,
name|preferences
argument_list|)
return|;
block|}
block|}
end_class

end_unit

