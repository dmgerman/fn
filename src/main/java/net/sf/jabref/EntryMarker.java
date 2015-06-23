begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|undo
operator|.
name|UndoableFieldChange
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
comment|/**      * @param increment whether the given increment should be added to the current one. Currently never used in JabRef      */
DECL|method|markEntry (BibtexEntry be, int markIncrement, boolean increment, NamedCompound ce)
specifier|public
specifier|static
name|void
name|markEntry
parameter_list|(
name|BibtexEntry
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
name|Object
name|o
init|=
name|be
operator|.
name|getField
argument_list|(
name|BibtexFields
operator|.
name|MARKED
argument_list|)
decl_stmt|;
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
name|o
operator|!=
literal|null
condition|)
block|{
name|String
name|s
init|=
name|o
operator|.
name|toString
argument_list|()
decl_stmt|;
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
comment|// Already marked 1 for this user.
name|prevMarkLevel
operator|=
literal|1
expr_stmt|;
name|newValue
operator|=
name|s
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|index
argument_list|)
operator|+
name|s
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
name|s
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
name|s
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
name|BibtexFields
operator|.
name|MARKED
argument_list|,
name|be
operator|.
name|getField
argument_list|(
name|BibtexFields
operator|.
name|MARKED
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
name|BibtexFields
operator|.
name|MARKED
argument_list|,
name|newValue
argument_list|)
expr_stmt|;
block|}
comment|/**      * SIDE EFFECT: Unselectes given entry      */
DECL|method|unmarkEntry (BibtexEntry be, boolean onlyMaxLevel, BibtexDatabase database, NamedCompound ce)
specifier|public
specifier|static
name|void
name|unmarkEntry
parameter_list|(
name|BibtexEntry
name|be
parameter_list|,
name|boolean
name|onlyMaxLevel
parameter_list|,
name|BibtexDatabase
name|database
parameter_list|,
name|NamedCompound
name|ce
parameter_list|)
block|{
name|Object
name|o
init|=
name|be
operator|.
name|getField
argument_list|(
name|BibtexFields
operator|.
name|MARKED
argument_list|)
decl_stmt|;
if|if
condition|(
name|o
operator|!=
literal|null
condition|)
block|{
name|String
name|s
init|=
name|o
operator|.
name|toString
argument_list|()
decl_stmt|;
if|if
condition|(
name|s
operator|.
name|equals
argument_list|(
literal|"0"
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
comment|// Marked 1 for this user.
if|if
condition|(
operator|!
name|onlyMaxLevel
condition|)
block|{
name|newValue
operator|=
name|s
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|index
argument_list|)
operator|+
name|s
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
else|else
block|{
return|return;
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
name|s
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
name|s
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
name|s
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
name|s
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|index
argument_list|)
operator|+
name|s
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
name|BibtexFields
operator|.
name|MARKED
argument_list|,
name|be
operator|.
name|getField
argument_list|(
name|BibtexFields
operator|.
name|MARKED
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
name|BibtexFields
operator|.
name|MARKED
argument_list|,
name|newValue
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * An entry is marked with a "0", not in the new style with user names. We      * want to unmark it as transparently as possible. Since this shouldn't      * happen too often, we do it by scanning the "owner" fields of the entire      * database, collecting all user names. We then mark the entry for all users      * except the current one. Thus only the user who unmarks will see that it      * is unmarked, and we get rid of the old-style marking.      *      * @param be      * @param ce      */
DECL|method|unmarkOldStyle (BibtexEntry be, BibtexDatabase database, NamedCompound ce)
specifier|private
specifier|static
name|void
name|unmarkOldStyle
parameter_list|(
name|BibtexEntry
name|be
parameter_list|,
name|BibtexDatabase
name|database
parameter_list|,
name|NamedCompound
name|ce
parameter_list|)
block|{
name|TreeSet
argument_list|<
name|Object
argument_list|>
name|owners
init|=
operator|new
name|TreeSet
argument_list|<
name|Object
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|BibtexEntry
name|entry
range|:
name|database
operator|.
name|getEntries
argument_list|()
control|)
block|{
name|Object
name|o
init|=
name|entry
operator|.
name|getField
argument_list|(
name|BibtexFields
operator|.
name|OWNER
argument_list|)
decl_stmt|;
if|if
condition|(
name|o
operator|!=
literal|null
condition|)
block|{
name|owners
operator|.
name|add
argument_list|(
name|o
argument_list|)
expr_stmt|;
comment|// System.out.println("Owner: "+entry.getField(Globals.OWNER));
block|}
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
literal|"defaultOwner"
argument_list|)
argument_list|)
expr_stmt|;
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
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
operator|.
name|toString
argument_list|()
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
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
name|newVal
operator|=
literal|null
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
name|BibtexFields
operator|.
name|MARKED
argument_list|,
name|be
operator|.
name|getField
argument_list|(
name|BibtexFields
operator|.
name|MARKED
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
name|BibtexFields
operator|.
name|MARKED
argument_list|,
name|newVal
argument_list|)
expr_stmt|;
block|}
DECL|method|isMarked (BibtexEntry be)
specifier|public
specifier|static
name|int
name|isMarked
parameter_list|(
name|BibtexEntry
name|be
parameter_list|)
block|{
name|Object
name|fieldVal
init|=
name|be
operator|.
name|getField
argument_list|(
name|BibtexFields
operator|.
name|MARKED
argument_list|)
decl_stmt|;
if|if
condition|(
name|fieldVal
operator|==
literal|null
condition|)
block|{
return|return
literal|0
return|;
block|}
name|String
name|s
init|=
operator|(
name|String
operator|)
name|fieldVal
decl_stmt|;
if|if
condition|(
name|s
operator|.
name|equals
argument_list|(
literal|"0"
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
block|}
end_class

end_unit

