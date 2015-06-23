begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_class
DECL|class|IncrementalSearcher
class|class
name|IncrementalSearcher
block|{
DECL|field|prefs
specifier|private
specifier|final
name|JabRefPreferences
name|prefs
decl_stmt|;
DECL|field|hitInField
specifier|private
name|String
name|hitInField
decl_stmt|;
DECL|method|IncrementalSearcher (JabRefPreferences prefs)
specifier|public
name|IncrementalSearcher
parameter_list|(
name|JabRefPreferences
name|prefs
parameter_list|)
block|{
name|this
operator|.
name|prefs
operator|=
name|prefs
expr_stmt|;
block|}
DECL|method|getField ()
specifier|public
name|String
name|getField
parameter_list|()
block|{
return|return
name|hitInField
return|;
block|}
DECL|method|search (String pattern, BibtexEntry bibtexEntry)
specifier|public
name|boolean
name|search
parameter_list|(
name|String
name|pattern
parameter_list|,
name|BibtexEntry
name|bibtexEntry
parameter_list|)
block|{
name|hitInField
operator|=
literal|null
expr_stmt|;
return|return
name|searchFields
argument_list|(
name|bibtexEntry
operator|.
name|getAllFields
argument_list|()
argument_list|,
name|bibtexEntry
argument_list|,
name|pattern
argument_list|)
return|;
block|}
DECL|method|searchFields (Set<String> fields, BibtexEntry bibtexEntry, String searchString)
specifier|private
name|boolean
name|searchFields
parameter_list|(
name|Set
argument_list|<
name|String
argument_list|>
name|fields
parameter_list|,
name|BibtexEntry
name|bibtexEntry
parameter_list|,
name|String
name|searchString
parameter_list|)
block|{
name|boolean
name|found
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|fields
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|String
name|field
range|:
name|fields
control|)
block|{
try|try
block|{
comment|/*Globals.logger("Searching field '"+fields[i].toString()                     	       +"' for '"                     	       +pattern.toString()+"'.");*/
if|if
condition|(
name|bibtexEntry
operator|.
name|getField
argument_list|(
name|field
argument_list|)
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"caseSensitiveSearch"
argument_list|)
condition|)
block|{
if|if
condition|(
name|bibtexEntry
operator|.
name|getField
argument_list|(
name|field
argument_list|)
operator|.
name|contains
argument_list|(
name|searchString
argument_list|)
condition|)
block|{
name|found
operator|=
literal|true
expr_stmt|;
block|}
block|}
else|else
block|{
if|if
condition|(
name|bibtexEntry
operator|.
name|getField
argument_list|(
name|field
argument_list|)
operator|.
name|toLowerCase
argument_list|()
operator|.
name|contains
argument_list|(
name|searchString
operator|.
name|toLowerCase
argument_list|()
argument_list|)
condition|)
block|{
name|found
operator|=
literal|true
expr_stmt|;
block|}
block|}
if|if
condition|(
name|found
condition|)
block|{
name|hitInField
operator|=
name|field
expr_stmt|;
return|return
literal|true
return|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Searching error: "
operator|+
name|t
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
literal|false
return|;
block|}
block|}
end_class

end_unit

