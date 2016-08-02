begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.gui
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
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
name|gui
operator|.
name|undo
operator|.
name|NamedCompound
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
name|undo
operator|.
name|UndoableFieldChange
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
name|FieldName
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
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_class
DECL|class|EntryMarker
specifier|public
class|class
name|EntryMarker
block|{
DECL|field|MARK_COLOR_LEVELS
specifier|public
specifier|static
specifier|final
name|int
name|MARK_COLOR_LEVELS
init|=
literal|6
decl_stmt|;
DECL|field|MAX_MARKING_LEVEL
specifier|public
specifier|static
specifier|final
name|int
name|MAX_MARKING_LEVEL
init|=
name|MARK_COLOR_LEVELS
operator|-
literal|1
decl_stmt|;
DECL|field|IMPORT_MARK_LEVEL
specifier|public
specifier|static
specifier|final
name|int
name|IMPORT_MARK_LEVEL
init|=
name|MARK_COLOR_LEVELS
decl_stmt|;
DECL|field|MARK_NUMBER_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|MARK_NUMBER_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
operator|.
name|MARKING_WITH_NUMBER_PATTERN
argument_list|)
decl_stmt|;
comment|/**      * @param increment whether the given increment should be added to the current one. Currently never used in JabRef. Could be used to increase marking color ("Mark in specific color").      */
DECL|method|markEntry (BibEntry be, int markIncrement, boolean increment, NamedCompound ce)
specifier|public
specifier|static
name|void
name|markEntry
parameter_list|(
name|BibEntry
name|be
parameter_list|,
name|int
name|markIncrement
parameter_list|,
name|boolean
name|increment
parameter_list|,
name|NamedCompound
name|ce
parameter_list|)
block|{
name|int
name|prevMarkLevel
decl_stmt|;
name|String
name|newValue
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|be
operator|.
name|hasField
argument_list|(
name|FieldName
operator|.
name|MARKED
argument_list|)
condition|)
block|{
name|String
name|markerString
init|=
name|be
operator|.
name|getFieldOptional
argument_list|(
name|FieldName
operator|.
name|MARKED
argument_list|)
operator|.
name|get
argument_list|()
decl_stmt|;
name|int
name|index
init|=
name|markerString
operator|.
name|indexOf
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|WRAPPED_USERNAME
argument_list|)
decl_stmt|;
if|if
condition|(
name|index
operator|>=
literal|0
condition|)
block|{
comment|// Already marked 1 for this user.
name|prevMarkLevel
operator|=
literal|1
expr_stmt|;
name|newValue
operator|=
name|markerString
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|index
argument_list|)
operator|+
name|markerString
operator|.
name|substring
argument_list|(
name|index
operator|+
name|Globals
operator|.
name|prefs
operator|.
name|WRAPPED_USERNAME
operator|.
name|length
argument_list|()
argument_list|)
operator|+
name|Globals
operator|.
name|prefs
operator|.
name|WRAPPED_USERNAME
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|WRAPPED_USERNAME
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
operator|+
literal|":"
operator|+
operator|(
name|increment
condition|?
name|Math
operator|.
name|min
argument_list|(
name|MAX_MARKING_LEVEL
argument_list|,
name|prevMarkLevel
operator|+
name|markIncrement
argument_list|)
else|:
name|markIncrement
operator|)
operator|+
literal|"]"
expr_stmt|;
block|}
else|else
block|{
name|Matcher
name|m
init|=
name|MARK_NUMBER_PATTERN
operator|.
name|matcher
argument_list|(
name|markerString
argument_list|)
decl_stmt|;
if|if
condition|(
name|m
operator|.
name|find
argument_list|()
condition|)
block|{
try|try
block|{
name|prevMarkLevel
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|newValue
operator|=
name|markerString
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|m
operator|.
name|start
argument_list|(
literal|1
argument_list|)
argument_list|)
operator|+
operator|(
name|increment
condition|?
name|Math
operator|.
name|min
argument_list|(
name|MAX_MARKING_LEVEL
argument_list|,
name|prevMarkLevel
operator|+
name|markIncrement
argument_list|)
else|:
name|markIncrement
operator|)
operator|+
name|markerString
operator|.
name|substring
argument_list|(
name|m
operator|.
name|end
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|ex
parameter_list|)
block|{
comment|// Do nothing.
block|}
block|}
block|}
block|}
if|if
condition|(
name|newValue
operator|==
literal|null
condition|)
block|{
name|newValue
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|WRAPPED_USERNAME
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|WRAPPED_USERNAME
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
operator|+
literal|":"
operator|+
name|markIncrement
operator|+
literal|"]"
expr_stmt|;
block|}
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableFieldChange
argument_list|(
name|be
argument_list|,
name|FieldName
operator|.
name|MARKED
argument_list|,
name|be
operator|.
name|getFieldOptional
argument_list|(
name|FieldName
operator|.
name|MARKED
argument_list|)
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
argument_list|,
name|newValue
argument_list|)
argument_list|)
expr_stmt|;
name|be
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|MARKED
argument_list|,
name|newValue
argument_list|)
expr_stmt|;
block|}
comment|/**      * SIDE EFFECT: Unselects given entry      */
DECL|method|unmarkEntry (BibEntry be, boolean onlyMaxLevel, BibDatabase database, NamedCompound ce)
specifier|public
specifier|static
name|void
name|unmarkEntry
parameter_list|(
name|BibEntry
name|be
parameter_list|,
name|boolean
name|onlyMaxLevel
parameter_list|,
name|BibDatabase
name|database
parameter_list|,
name|NamedCompound
name|ce
parameter_list|)
block|{
if|if
condition|(
name|be
operator|.
name|hasField
argument_list|(
name|FieldName
operator|.
name|MARKED
argument_list|)
condition|)
block|{
name|String
name|markerString
init|=
name|be
operator|.
name|getFieldOptional
argument_list|(
name|FieldName
operator|.
name|MARKED
argument_list|)
operator|.
name|get
argument_list|()
decl_stmt|;
if|if
condition|(
literal|"0"
operator|.
name|equals
argument_list|(
name|markerString
argument_list|)
condition|)
block|{
if|if
condition|(
operator|!
name|onlyMaxLevel
condition|)
block|{
name|unmarkOldStyle
argument_list|(
name|be
argument_list|,
name|database
argument_list|,
name|ce
argument_list|)
expr_stmt|;
block|}
return|return;
block|}
name|String
name|newValue
init|=
literal|null
decl_stmt|;
name|int
name|index
init|=
name|markerString
operator|.
name|indexOf
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|WRAPPED_USERNAME
argument_list|)
decl_stmt|;
if|if
condition|(
name|index
operator|>=
literal|0
condition|)
block|{
comment|// Marked 1 for this user.
if|if
condition|(
name|onlyMaxLevel
condition|)
block|{
return|return;
block|}
else|else
block|{
name|newValue
operator|=
name|markerString
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|index
argument_list|)
operator|+
name|markerString
operator|.
name|substring
argument_list|(
name|index
operator|+
name|Globals
operator|.
name|prefs
operator|.
name|WRAPPED_USERNAME
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|Matcher
name|m
init|=
name|MARK_NUMBER_PATTERN
operator|.
name|matcher
argument_list|(
name|markerString
argument_list|)
decl_stmt|;
if|if
condition|(
name|m
operator|.
name|find
argument_list|()
condition|)
block|{
try|try
block|{
name|int
name|prevMarkLevel
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|onlyMaxLevel
operator|||
operator|(
name|prevMarkLevel
operator|==
name|MARK_COLOR_LEVELS
operator|)
condition|)
block|{
if|if
condition|(
name|prevMarkLevel
operator|>
literal|1
condition|)
block|{
name|newValue
operator|=
name|markerString
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|m
operator|.
name|start
argument_list|(
literal|1
argument_list|)
argument_list|)
operator|+
name|markerString
operator|.
name|substring
argument_list|(
name|m
operator|.
name|end
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|String
name|toRemove
init|=
name|Globals
operator|.
name|prefs
operator|.
name|WRAPPED_USERNAME
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|WRAPPED_USERNAME
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
operator|+
literal|":1]"
decl_stmt|;
name|index
operator|=
name|markerString
operator|.
name|indexOf
argument_list|(
name|toRemove
argument_list|)
expr_stmt|;
if|if
condition|(
name|index
operator|>=
literal|0
condition|)
block|{
name|newValue
operator|=
name|markerString
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|index
argument_list|)
operator|+
name|markerString
operator|.
name|substring
argument_list|(
name|index
operator|+
name|toRemove
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
return|return;
block|}
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|ex
parameter_list|)
block|{
comment|// Do nothing.
block|}
block|}
block|}
comment|/*int piv = 0, hit;             StringBuffer sb = new StringBuffer();             while ((hit = s.indexOf(G047749118118             1110lobals.prefs.WRAPPED_USERNAME, piv))>= 0) {             	if (hit> 0)             		sb.append(s.substring(piv, hit));             	piv = hit + Globals.prefs.WRAPPED_USERNAME.length();             }             if (piv< s.length() - 1) {             	sb.append(s.substring(piv));             }             String newVal = sb.length()> 0 ? sb.toString() : null;*/
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableFieldChange
argument_list|(
name|be
argument_list|,
name|FieldName
operator|.
name|MARKED
argument_list|,
name|be
operator|.
name|getFieldOptional
argument_list|(
name|FieldName
operator|.
name|MARKED
argument_list|)
operator|.
name|get
argument_list|()
argument_list|,
name|newValue
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|newValue
operator|==
literal|null
condition|)
block|{
name|be
operator|.
name|clearField
argument_list|(
name|FieldName
operator|.
name|MARKED
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|be
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|MARKED
argument_list|,
name|newValue
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * An entry is marked with a "0", not in the new style with user names. We      * want to unmark it as transparently as possible. Since this shouldn't      * happen too often, we do it by scanning the "owner" fields of the entire      * database, collecting all user names. We then mark the entry for all users      * except the current one. Thus only the user who unmarks will see that it      * is unmarked, and we get rid of the old-style marking.      *      * @param be      * @param ce      */
DECL|method|unmarkOldStyle (BibEntry be, BibDatabase database, NamedCompound ce)
specifier|private
specifier|static
name|void
name|unmarkOldStyle
parameter_list|(
name|BibEntry
name|be
parameter_list|,
name|BibDatabase
name|database
parameter_list|,
name|NamedCompound
name|ce
parameter_list|)
block|{
name|Set
argument_list|<
name|Object
argument_list|>
name|owners
init|=
operator|new
name|TreeSet
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|BibEntry
name|entry
range|:
name|database
operator|.
name|getEntries
argument_list|()
control|)
block|{
name|entry
operator|.
name|getFieldOptional
argument_list|(
name|FieldName
operator|.
name|OWNER
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|owners
operator|::
name|add
argument_list|)
expr_stmt|;
block|}
name|owners
operator|.
name|remove
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|DEFAULT_OWNER
argument_list|)
argument_list|)
expr_stmt|;
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
for|for
control|(
name|Object
name|owner
range|:
name|owners
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|'['
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|owner
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|']'
argument_list|)
expr_stmt|;
block|}
name|String
name|newVal
init|=
name|sb
operator|.
name|toString
argument_list|()
decl_stmt|;
if|if
condition|(
name|newVal
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableFieldChange
argument_list|(
name|be
argument_list|,
name|FieldName
operator|.
name|MARKED
argument_list|,
name|be
operator|.
name|getFieldOptional
argument_list|(
name|FieldName
operator|.
name|MARKED
argument_list|)
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|be
operator|.
name|clearField
argument_list|(
name|FieldName
operator|.
name|MARKED
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableFieldChange
argument_list|(
name|be
argument_list|,
name|FieldName
operator|.
name|MARKED
argument_list|,
name|be
operator|.
name|getFieldOptional
argument_list|(
name|FieldName
operator|.
name|MARKED
argument_list|)
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
argument_list|,
name|newVal
argument_list|)
argument_list|)
expr_stmt|;
name|be
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|MARKED
argument_list|,
name|newVal
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|isMarked (BibEntry be)
specifier|public
specifier|static
name|int
name|isMarked
parameter_list|(
name|BibEntry
name|be
parameter_list|)
block|{
if|if
condition|(
operator|!
name|be
operator|.
name|hasField
argument_list|(
name|FieldName
operator|.
name|MARKED
argument_list|)
condition|)
block|{
return|return
literal|0
return|;
block|}
name|String
name|s
init|=
name|be
operator|.
name|getFieldOptional
argument_list|(
name|FieldName
operator|.
name|MARKED
argument_list|)
operator|.
name|get
argument_list|()
decl_stmt|;
if|if
condition|(
literal|"0"
operator|.
name|equals
argument_list|(
name|s
argument_list|)
condition|)
block|{
return|return
literal|1
return|;
block|}
name|int
name|index
init|=
name|s
operator|.
name|indexOf
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|WRAPPED_USERNAME
argument_list|)
decl_stmt|;
if|if
condition|(
name|index
operator|>=
literal|0
condition|)
block|{
return|return
literal|1
return|;
block|}
name|Matcher
name|m
init|=
name|MARK_NUMBER_PATTERN
operator|.
name|matcher
argument_list|(
name|s
argument_list|)
decl_stmt|;
if|if
condition|(
name|m
operator|.
name|find
argument_list|()
condition|)
block|{
try|try
block|{
return|return
name|Integer
operator|.
name|parseInt
argument_list|(
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|ex
parameter_list|)
block|{
return|return
literal|1
return|;
block|}
block|}
else|else
block|{
return|return
literal|0
return|;
block|}
block|}
DECL|method|shouldMarkEntries ()
specifier|public
specifier|static
name|boolean
name|shouldMarkEntries
parameter_list|()
block|{
return|return
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|MARK_IMPORTED_ENTRIES
argument_list|)
return|;
block|}
block|}
end_class

end_unit

