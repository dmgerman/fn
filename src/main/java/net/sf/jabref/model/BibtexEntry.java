begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 David Weitzman, Morten O. Alver  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  Note: Modified for use in JabRef.  */
end_comment

begin_package
DECL|package|net.sf.jabref.model
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
package|;
end_package

begin_import
import|import
name|java
operator|.
name|beans
operator|.
name|PropertyChangeEvent
import|;
end_import

begin_import
import|import
name|java
operator|.
name|beans
operator|.
name|PropertyVetoException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|beans
operator|.
name|VetoableChangeListener
import|;
end_import

begin_import
import|import
name|java
operator|.
name|beans
operator|.
name|VetoableChangeSupport
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Writer
import|;
end_import

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|DateFormat
import|;
end_import

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|FieldPosition
import|;
end_import

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|ParseException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|ParsePosition
import|;
end_import

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|SimpleDateFormat
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|*
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
name|*
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
name|export
operator|.
name|FieldFormatter
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
name|BibtexEntryWriter
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
name|id
operator|.
name|IdGenerator
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
name|util
operator|.
name|MonthUtil
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

begin_class
DECL|class|BibtexEntry
specifier|public
class|class
name|BibtexEntry
block|{
DECL|field|TYPE_HEADER
specifier|public
specifier|static
specifier|final
name|String
name|TYPE_HEADER
init|=
literal|"entrytype"
decl_stmt|;
DECL|field|ID_FIELD
specifier|private
specifier|static
specifier|final
name|String
name|ID_FIELD
init|=
literal|"id"
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
name|BibtexEntry
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|FIELD_ALIASES_OLD_TO_NEW
specifier|public
specifier|static
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|FIELD_ALIASES_OLD_TO_NEW
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
comment|// Bibtex to BibLatex
DECL|field|FIELD_ALIASES_NEW_TO_OLD
specifier|public
specifier|static
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|FIELD_ALIASES_NEW_TO_OLD
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
comment|// BibLatex to Bibtex
static|static
block|{
name|BibtexEntry
operator|.
name|FIELD_ALIASES_OLD_TO_NEW
operator|.
name|put
argument_list|(
literal|"address"
argument_list|,
literal|"location"
argument_list|)
expr_stmt|;
name|BibtexEntry
operator|.
name|FIELD_ALIASES_NEW_TO_OLD
operator|.
name|put
argument_list|(
literal|"location"
argument_list|,
literal|"address"
argument_list|)
expr_stmt|;
name|BibtexEntry
operator|.
name|FIELD_ALIASES_OLD_TO_NEW
operator|.
name|put
argument_list|(
literal|"annote"
argument_list|,
literal|"annotation"
argument_list|)
expr_stmt|;
name|BibtexEntry
operator|.
name|FIELD_ALIASES_NEW_TO_OLD
operator|.
name|put
argument_list|(
literal|"annotation"
argument_list|,
literal|"annote"
argument_list|)
expr_stmt|;
name|BibtexEntry
operator|.
name|FIELD_ALIASES_OLD_TO_NEW
operator|.
name|put
argument_list|(
literal|"archiveprefix"
argument_list|,
literal|"eprinttype"
argument_list|)
expr_stmt|;
name|BibtexEntry
operator|.
name|FIELD_ALIASES_NEW_TO_OLD
operator|.
name|put
argument_list|(
literal|"eprinttype"
argument_list|,
literal|"archiveprefix"
argument_list|)
expr_stmt|;
name|BibtexEntry
operator|.
name|FIELD_ALIASES_OLD_TO_NEW
operator|.
name|put
argument_list|(
literal|"journal"
argument_list|,
literal|"journaltitle"
argument_list|)
expr_stmt|;
name|BibtexEntry
operator|.
name|FIELD_ALIASES_NEW_TO_OLD
operator|.
name|put
argument_list|(
literal|"journaltitle"
argument_list|,
literal|"journal"
argument_list|)
expr_stmt|;
name|BibtexEntry
operator|.
name|FIELD_ALIASES_OLD_TO_NEW
operator|.
name|put
argument_list|(
literal|"key"
argument_list|,
literal|"sortkey"
argument_list|)
expr_stmt|;
name|BibtexEntry
operator|.
name|FIELD_ALIASES_NEW_TO_OLD
operator|.
name|put
argument_list|(
literal|"sortkey"
argument_list|,
literal|"key"
argument_list|)
expr_stmt|;
name|BibtexEntry
operator|.
name|FIELD_ALIASES_OLD_TO_NEW
operator|.
name|put
argument_list|(
literal|"pdf"
argument_list|,
literal|"file"
argument_list|)
expr_stmt|;
name|BibtexEntry
operator|.
name|FIELD_ALIASES_NEW_TO_OLD
operator|.
name|put
argument_list|(
literal|"file"
argument_list|,
literal|"pdf"
argument_list|)
expr_stmt|;
name|BibtexEntry
operator|.
name|FIELD_ALIASES_OLD_TO_NEW
operator|.
name|put
argument_list|(
literal|"primaryclass"
argument_list|,
literal|"eprintclass"
argument_list|)
expr_stmt|;
name|BibtexEntry
operator|.
name|FIELD_ALIASES_NEW_TO_OLD
operator|.
name|put
argument_list|(
literal|"eprintclass"
argument_list|,
literal|"primaryclass"
argument_list|)
expr_stmt|;
name|BibtexEntry
operator|.
name|FIELD_ALIASES_OLD_TO_NEW
operator|.
name|put
argument_list|(
literal|"school"
argument_list|,
literal|"institution"
argument_list|)
expr_stmt|;
name|BibtexEntry
operator|.
name|FIELD_ALIASES_NEW_TO_OLD
operator|.
name|put
argument_list|(
literal|"institution"
argument_list|,
literal|"school"
argument_list|)
expr_stmt|;
block|}
DECL|field|id
specifier|private
name|String
name|id
decl_stmt|;
DECL|field|type
specifier|private
name|BibtexEntryType
name|type
decl_stmt|;
DECL|field|fields
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|fields
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|changeSupport
specifier|private
specifier|final
name|VetoableChangeSupport
name|changeSupport
init|=
operator|new
name|VetoableChangeSupport
argument_list|(
name|this
argument_list|)
decl_stmt|;
comment|// Search and grouping status is stored in boolean fields for quick reference:
DECL|field|searchHit
specifier|private
name|boolean
name|searchHit
decl_stmt|;
DECL|field|groupHit
specifier|private
name|boolean
name|groupHit
decl_stmt|;
DECL|method|BibtexEntry ()
specifier|public
name|BibtexEntry
parameter_list|()
block|{
name|this
argument_list|(
name|IdGenerator
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|BibtexEntry (String id)
specifier|public
name|BibtexEntry
parameter_list|(
name|String
name|id
parameter_list|)
block|{
name|this
argument_list|(
name|id
argument_list|,
name|BibtexEntryTypes
operator|.
name|OTHER
argument_list|)
expr_stmt|;
block|}
DECL|method|BibtexEntry (String id, BibtexEntryType type)
specifier|public
name|BibtexEntry
parameter_list|(
name|String
name|id
parameter_list|,
name|BibtexEntryType
name|type
parameter_list|)
block|{
if|if
condition|(
name|id
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|NullPointerException
argument_list|(
literal|"Every BibtexEntry must have an ID"
argument_list|)
throw|;
block|}
name|this
operator|.
name|id
operator|=
name|id
expr_stmt|;
name|setType
argument_list|(
name|type
argument_list|)
expr_stmt|;
block|}
comment|/**      * Returns an array describing the optional fields for this entry.      */
DECL|method|getOptionalFields ()
specifier|public
name|String
index|[]
name|getOptionalFields
parameter_list|()
block|{
return|return
name|type
operator|.
name|getOptionalFields
argument_list|()
operator|.
name|clone
argument_list|()
return|;
block|}
comment|/**      * Returns an array describing the required fields for this entry.      */
DECL|method|getRequiredFields ()
specifier|public
name|String
index|[]
name|getRequiredFields
parameter_list|()
block|{
return|return
name|type
operator|.
name|getRequiredFields
argument_list|()
operator|.
name|clone
argument_list|()
return|;
block|}
DECL|method|getUserDefinedFields ()
specifier|public
name|String
index|[]
name|getUserDefinedFields
parameter_list|()
block|{
return|return
name|Globals
operator|.
name|prefs
operator|.
name|getStringArray
argument_list|(
name|JabRefPreferences
operator|.
name|WRITEFIELD_USERDEFINEDORDER
argument_list|)
return|;
block|}
comment|/**      * Returns an set containing the names of all fields that are      * set for this particular entry.      */
DECL|method|getAllFields ()
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getAllFields
parameter_list|()
block|{
return|return
operator|new
name|TreeSet
argument_list|<>
argument_list|(
name|fields
operator|.
name|keySet
argument_list|()
argument_list|)
return|;
block|}
comment|/**      * Returns true if this entry contains the fields it needs to be      * complete.      */
DECL|method|hasAllRequiredFields (BibtexDatabase database)
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexDatabase
name|database
parameter_list|)
block|{
return|return
name|type
operator|.
name|hasAllRequiredFields
argument_list|(
name|this
argument_list|,
name|database
argument_list|)
return|;
block|}
comment|/**      * Returns this entry's type.      */
DECL|method|getType ()
specifier|public
name|BibtexEntryType
name|getType
parameter_list|()
block|{
return|return
name|type
return|;
block|}
comment|/**      * Sets this entry's type.      */
DECL|method|setType (BibtexEntryType type)
specifier|public
name|void
name|setType
parameter_list|(
name|BibtexEntryType
name|type
parameter_list|)
block|{
if|if
condition|(
name|type
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Every BibtexEntry must have a type.  Instead of null, use type OTHER"
argument_list|)
throw|;
block|}
name|BibtexEntryType
name|oldType
init|=
name|this
operator|.
name|type
decl_stmt|;
try|try
block|{
comment|// We set the type before throwing the changeEvent, to enable
comment|// the change listener to access the new value if the change
comment|// sets off a change in database sorting etc.
name|this
operator|.
name|type
operator|=
name|type
expr_stmt|;
name|firePropertyChangedEvent
argument_list|(
name|TYPE_HEADER
argument_list|,
name|oldType
operator|!=
literal|null
condition|?
name|oldType
operator|.
name|getName
argument_list|()
else|:
literal|null
argument_list|,
name|type
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|PropertyVetoException
name|pve
parameter_list|)
block|{
name|pve
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      * Prompts the entry to call BibtexEntryType.getType(String) with      * its current type name as argument, and sets its type according      * to what is returned. This method is called when a user changes      * the type customization, to make sure all entries are set with      * current types.      *      * @return true if the entry could find a type, false if not (in      * this case the type will have been set to      * BibtexEntryTypes.TYPELESS).      */
DECL|method|updateType ()
specifier|public
name|boolean
name|updateType
parameter_list|()
block|{
name|BibtexEntryType
name|newType
init|=
name|BibtexEntryType
operator|.
name|getType
argument_list|(
name|type
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|newType
operator|!=
literal|null
condition|)
block|{
name|type
operator|=
name|newType
expr_stmt|;
return|return
literal|true
return|;
block|}
name|type
operator|=
name|BibtexEntryTypes
operator|.
name|TYPELESS
expr_stmt|;
return|return
literal|false
return|;
block|}
comment|/**      * Sets this entry's ID, provided the database containing it      * doesn't veto the change.      */
DECL|method|setId (String id)
specifier|public
name|void
name|setId
parameter_list|(
name|String
name|id
parameter_list|)
throws|throws
name|KeyCollisionException
block|{
if|if
condition|(
name|id
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Every BibtexEntry must have an ID"
argument_list|)
throw|;
block|}
try|try
block|{
name|firePropertyChangedEvent
argument_list|(
name|BibtexEntry
operator|.
name|ID_FIELD
argument_list|,
name|this
operator|.
name|id
argument_list|,
name|id
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|PropertyVetoException
name|pv
parameter_list|)
block|{
throw|throw
operator|new
name|KeyCollisionException
argument_list|(
literal|"Couldn't change ID: "
operator|+
name|pv
argument_list|)
throw|;
block|}
name|this
operator|.
name|id
operator|=
name|id
expr_stmt|;
block|}
comment|/**      * Returns this entry's ID.      */
DECL|method|getId ()
specifier|public
name|String
name|getId
parameter_list|()
block|{
return|return
name|id
return|;
block|}
comment|/**      * Returns the contents of the given field, or null if it is not set.      */
DECL|method|getField (String name)
specifier|public
name|String
name|getField
parameter_list|(
name|String
name|name
parameter_list|)
block|{
return|return
name|fields
operator|.
name|get
argument_list|(
name|name
argument_list|)
return|;
block|}
comment|/**      * Returns the contents of the given field, its alias or null if both are      * not set.      *<p>      * The following aliases are considered (old bibtex<-> new biblatex) based      * on the BibLatex documentation, chapter 2.2.5:      * address<-> location      * annote<-> annotation      * archiveprefix<-> eprinttype      * journal<-> journaltitle      * key<-> sortkey      * pdf<-> file      * primaryclass<-> eprintclass      * school<-> institution      * These work bidirectional.      *<p>      * Special attention is paid to dates: (see the BibLatex documentation,      * chapter 2.3.8)      * The fields 'year' and 'month' are used if the 'date'      * field is empty. Conversely, getFieldOrAlias("year") also tries to      * extract the year from the 'date' field (analogously for 'month').      */
DECL|method|getFieldOrAlias (String name)
specifier|public
name|String
name|getFieldOrAlias
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|String
name|fieldValue
init|=
name|getField
argument_list|(
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
name|fieldValue
operator|!=
literal|null
operator|&&
operator|!
name|fieldValue
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
name|fieldValue
return|;
block|}
comment|// No value of this field found, so look at the alias
comment|// Create bidirectional dictionary between field names and their aliases
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|aliases
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
name|aliases
operator|.
name|putAll
argument_list|(
name|BibtexEntry
operator|.
name|FIELD_ALIASES_OLD_TO_NEW
argument_list|)
expr_stmt|;
name|aliases
operator|.
name|putAll
argument_list|(
name|BibtexEntry
operator|.
name|FIELD_ALIASES_NEW_TO_OLD
argument_list|)
expr_stmt|;
name|String
name|aliasForField
init|=
name|aliases
operator|.
name|get
argument_list|(
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
name|aliasForField
operator|!=
literal|null
condition|)
block|{
return|return
name|getField
argument_list|(
name|aliasForField
argument_list|)
return|;
block|}
comment|// So we did not found the field itself or its alias...
comment|// Finally, handle dates
if|if
condition|(
name|name
operator|.
name|equals
argument_list|(
literal|"date"
argument_list|)
condition|)
block|{
name|String
name|year
init|=
name|getField
argument_list|(
literal|"year"
argument_list|)
decl_stmt|;
name|MonthUtil
operator|.
name|Month
name|month
init|=
name|MonthUtil
operator|.
name|getMonth
argument_list|(
name|getField
argument_list|(
literal|"month"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|year
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|month
operator|.
name|isValid
argument_list|()
condition|)
block|{
return|return
name|year
operator|+
literal|'-'
operator|+
name|month
operator|.
name|twoDigitNumber
return|;
block|}
else|else
block|{
return|return
name|year
return|;
block|}
block|}
block|}
if|if
condition|(
name|name
operator|.
name|equals
argument_list|(
literal|"year"
argument_list|)
operator|||
name|name
operator|.
name|equals
argument_list|(
literal|"month"
argument_list|)
condition|)
block|{
name|String
name|date
init|=
name|getField
argument_list|(
literal|"date"
argument_list|)
decl_stmt|;
if|if
condition|(
name|date
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
comment|// Create date format matching dates with year and month
name|DateFormat
name|df
init|=
operator|new
name|DateFormat
argument_list|()
block|{
specifier|static
specifier|final
name|String
name|FORMAT1
init|=
literal|"yyyy-MM-dd"
decl_stmt|;
specifier|static
specifier|final
name|String
name|FORMAT2
init|=
literal|"yyyy-MM"
decl_stmt|;
specifier|final
name|SimpleDateFormat
name|sdf1
init|=
operator|new
name|SimpleDateFormat
argument_list|(
name|FORMAT1
argument_list|)
decl_stmt|;
specifier|final
name|SimpleDateFormat
name|sdf2
init|=
operator|new
name|SimpleDateFormat
argument_list|(
name|FORMAT2
argument_list|)
decl_stmt|;
annotation|@
name|Override
specifier|public
name|StringBuffer
name|format
parameter_list|(
name|Date
name|date
parameter_list|,
name|StringBuffer
name|toAppendTo
parameter_list|,
name|FieldPosition
name|fieldPosition
parameter_list|)
block|{
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|()
throw|;
block|}
annotation|@
name|Override
specifier|public
name|Date
name|parse
parameter_list|(
name|String
name|source
parameter_list|,
name|ParsePosition
name|pos
parameter_list|)
block|{
if|if
condition|(
name|source
operator|.
name|length
argument_list|()
operator|-
name|pos
operator|.
name|getIndex
argument_list|()
operator|==
name|FORMAT1
operator|.
name|length
argument_list|()
condition|)
block|{
return|return
name|sdf1
operator|.
name|parse
argument_list|(
name|source
argument_list|,
name|pos
argument_list|)
return|;
block|}
return|return
name|sdf2
operator|.
name|parse
argument_list|(
name|source
argument_list|,
name|pos
argument_list|)
return|;
block|}
block|}
decl_stmt|;
try|try
block|{
name|Date
name|parsedDate
init|=
name|df
operator|.
name|parse
argument_list|(
name|date
argument_list|)
decl_stmt|;
name|Calendar
name|calendar
init|=
name|Calendar
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|calendar
operator|.
name|setTime
argument_list|(
name|parsedDate
argument_list|)
expr_stmt|;
if|if
condition|(
name|name
operator|.
name|equals
argument_list|(
literal|"year"
argument_list|)
condition|)
block|{
return|return
name|Integer
operator|.
name|toString
argument_list|(
name|calendar
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|YEAR
argument_list|)
argument_list|)
return|;
block|}
if|if
condition|(
name|name
operator|.
name|equals
argument_list|(
literal|"month"
argument_list|)
condition|)
block|{
return|return
name|Integer
operator|.
name|toString
argument_list|(
name|calendar
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|MONTH
argument_list|)
operator|+
literal|1
argument_list|)
return|;
comment|// Shift by 1 since in this calendar Jan = 0
block|}
block|}
catch|catch
parameter_list|(
name|ParseException
name|e
parameter_list|)
block|{
comment|// So not a date with year and month, try just to parse years
name|df
operator|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"yyyy"
argument_list|)
expr_stmt|;
try|try
block|{
name|Date
name|parsedDate
init|=
name|df
operator|.
name|parse
argument_list|(
name|date
argument_list|)
decl_stmt|;
name|Calendar
name|calendar
init|=
name|Calendar
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|calendar
operator|.
name|setTime
argument_list|(
name|parsedDate
argument_list|)
expr_stmt|;
if|if
condition|(
name|name
operator|.
name|equals
argument_list|(
literal|"year"
argument_list|)
condition|)
block|{
return|return
name|Integer
operator|.
name|toString
argument_list|(
name|calendar
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|YEAR
argument_list|)
argument_list|)
return|;
block|}
block|}
catch|catch
parameter_list|(
name|ParseException
name|e2
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Could not parse entry "
operator|+
name|name
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
comment|// Date field not in valid format
block|}
block|}
block|}
return|return
literal|null
return|;
block|}
DECL|method|getCiteKey ()
specifier|public
name|String
name|getCiteKey
parameter_list|()
block|{
return|return
name|fields
operator|.
name|containsKey
argument_list|(
name|BibtexFields
operator|.
name|KEY_FIELD
argument_list|)
condition|?
name|fields
operator|.
name|get
argument_list|(
name|BibtexFields
operator|.
name|KEY_FIELD
argument_list|)
else|:
literal|null
return|;
block|}
comment|/**      * Sets a number of fields simultaneously. The given HashMap contains field      * names as keys, each mapped to the value to set.      * WARNING: this method does not notify change listeners, so it should *NOT*      * be used for entries that are being displayed in the GUI. Furthermore, it      * does not check values for content, so e.g. empty strings will be set as such.      */
DECL|method|setField (Map<String, String> fields)
specifier|public
name|void
name|setField
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|fields
parameter_list|)
block|{
name|this
operator|.
name|fields
operator|.
name|putAll
argument_list|(
name|fields
argument_list|)
expr_stmt|;
block|}
comment|/**      * Set a field, and notify listeners about the change.      *      * @param name  The field to set.      * @param value The value to set.      */
DECL|method|setField (String name, String value)
specifier|public
name|void
name|setField
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|value
parameter_list|)
block|{
if|if
condition|(
name|BibtexEntry
operator|.
name|ID_FIELD
operator|.
name|equals
argument_list|(
name|name
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The field name '"
operator|+
name|name
operator|+
literal|"' is reserved"
argument_list|)
throw|;
block|}
name|String
name|oldValue
init|=
name|fields
operator|.
name|get
argument_list|(
name|name
argument_list|)
decl_stmt|;
try|try
block|{
comment|// We set the field before throwing the changeEvent, to enable
comment|// the change listener to access the new value if the change
comment|// sets off a change in database sorting etc.
name|fields
operator|.
name|put
argument_list|(
name|name
argument_list|,
name|value
argument_list|)
expr_stmt|;
name|firePropertyChangedEvent
argument_list|(
name|name
argument_list|,
name|oldValue
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|PropertyVetoException
name|pve
parameter_list|)
block|{
comment|// Since we have already made the change, we must undo it since
comment|// the change was rejected:
name|fields
operator|.
name|put
argument_list|(
name|name
argument_list|,
name|oldValue
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Change rejected: "
operator|+
name|pve
argument_list|)
throw|;
block|}
block|}
comment|/**      * Remove the mapping for the field name, and notify listeners about      * the change.      *      * @param name The field to clear.      */
DECL|method|clearField (String name)
specifier|public
name|void
name|clearField
parameter_list|(
name|String
name|name
parameter_list|)
block|{
if|if
condition|(
name|BibtexEntry
operator|.
name|ID_FIELD
operator|.
name|equals
argument_list|(
name|name
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The field name '"
operator|+
name|name
operator|+
literal|"' is reserved"
argument_list|)
throw|;
block|}
name|Object
name|oldValue
init|=
name|fields
operator|.
name|get
argument_list|(
name|name
argument_list|)
decl_stmt|;
name|fields
operator|.
name|remove
argument_list|(
name|name
argument_list|)
expr_stmt|;
try|try
block|{
name|firePropertyChangedEvent
argument_list|(
name|name
argument_list|,
name|oldValue
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|PropertyVetoException
name|pve
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Change rejected: "
operator|+
name|pve
argument_list|)
throw|;
block|}
block|}
comment|/**      * Determines whether this entry has all the given fields present. If a non-null      * database argument is given, this method will try to look up missing fields in      * entries linked by the "crossref" field, if any.      *      * @param fields   An array of field names to be checked.      * @param database The database in which to look up crossref'd entries, if any. This      *                 argument can be null, meaning that no attempt will be made to follow crossrefs.      * @return true if all fields are set or could be resolved, false otherwise.      */
DECL|method|allFieldsPresent (String[] fields, BibtexDatabase database)
name|boolean
name|allFieldsPresent
parameter_list|(
name|String
index|[]
name|fields
parameter_list|,
name|BibtexDatabase
name|database
parameter_list|)
block|{
for|for
control|(
name|String
name|field
range|:
name|fields
control|)
block|{
if|if
condition|(
name|BibtexDatabase
operator|.
name|getResolvedField
argument_list|(
name|field
argument_list|,
name|this
argument_list|,
name|database
argument_list|)
operator|==
literal|null
condition|)
block|{
return|return
literal|false
return|;
block|}
block|}
return|return
literal|true
return|;
block|}
DECL|method|atLeastOnePresent (String[] fields, BibtexDatabase database)
name|boolean
name|atLeastOnePresent
parameter_list|(
name|String
index|[]
name|fields
parameter_list|,
name|BibtexDatabase
name|database
parameter_list|)
block|{
for|for
control|(
name|String
name|field
range|:
name|fields
control|)
block|{
name|String
name|value
init|=
name|BibtexDatabase
operator|.
name|getResolvedField
argument_list|(
name|field
argument_list|,
name|this
argument_list|,
name|database
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
operator|&&
operator|!
name|value
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
literal|true
return|;
block|}
block|}
return|return
literal|false
return|;
block|}
DECL|method|firePropertyChangedEvent (String fieldName, Object oldValue, Object newValue)
specifier|private
name|void
name|firePropertyChangedEvent
parameter_list|(
name|String
name|fieldName
parameter_list|,
name|Object
name|oldValue
parameter_list|,
name|Object
name|newValue
parameter_list|)
throws|throws
name|PropertyVetoException
block|{
name|changeSupport
operator|.
name|fireVetoableChange
argument_list|(
operator|new
name|PropertyChangeEvent
argument_list|(
name|this
argument_list|,
name|fieldName
argument_list|,
name|oldValue
argument_list|,
name|newValue
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Adds a VetoableChangeListener, which is notified of field      * changes. This is useful for an object that needs to update      * itself each time a field changes.      */
DECL|method|addPropertyChangeListener (VetoableChangeListener listener)
specifier|public
name|void
name|addPropertyChangeListener
parameter_list|(
name|VetoableChangeListener
name|listener
parameter_list|)
block|{
name|changeSupport
operator|.
name|addVetoableChangeListener
argument_list|(
name|listener
argument_list|)
expr_stmt|;
block|}
comment|/**      * Removes a property listener.      */
DECL|method|removePropertyChangeListener (VetoableChangeListener listener)
specifier|public
name|void
name|removePropertyChangeListener
parameter_list|(
name|VetoableChangeListener
name|listener
parameter_list|)
block|{
name|changeSupport
operator|.
name|removeVetoableChangeListener
argument_list|(
name|listener
argument_list|)
expr_stmt|;
block|}
comment|/**      * Write this entry to the given Writer, with the given FieldFormatter.      *      * @param write True if this is a write, false if it is a display. The write will      *              not include non-writeable fields if it is a write, otherwise non-displayable fields      *              will be ignored. Refer to GUIGlobals for isWriteableField(String) and      *              isDisplayableField(String).      */
DECL|method|write (Writer out, FieldFormatter ff, boolean write)
specifier|public
name|void
name|write
parameter_list|(
name|Writer
name|out
parameter_list|,
name|FieldFormatter
name|ff
parameter_list|,
name|boolean
name|write
parameter_list|)
throws|throws
name|IOException
block|{
operator|new
name|BibtexEntryWriter
argument_list|(
name|ff
argument_list|,
name|write
argument_list|)
operator|.
name|write
argument_list|(
name|this
argument_list|,
name|out
argument_list|)
expr_stmt|;
block|}
comment|/**      * Returns a clone of this entry. Useful for copying.      */
annotation|@
name|Override
DECL|method|clone ()
specifier|public
name|Object
name|clone
parameter_list|()
block|{
name|BibtexEntry
name|clone
init|=
operator|new
name|BibtexEntry
argument_list|(
name|id
argument_list|,
name|type
argument_list|)
decl_stmt|;
name|clone
operator|.
name|fields
operator|=
operator|new
name|HashMap
argument_list|<>
argument_list|(
name|fields
argument_list|)
expr_stmt|;
return|return
name|clone
return|;
block|}
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getType
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|':'
operator|+
name|getField
argument_list|(
name|BibtexFields
operator|.
name|KEY_FIELD
argument_list|)
return|;
block|}
DECL|method|isSearchHit ()
specifier|public
name|boolean
name|isSearchHit
parameter_list|()
block|{
return|return
name|searchHit
return|;
block|}
DECL|method|setSearchHit (boolean searchHit)
specifier|public
name|void
name|setSearchHit
parameter_list|(
name|boolean
name|searchHit
parameter_list|)
block|{
name|this
operator|.
name|searchHit
operator|=
name|searchHit
expr_stmt|;
block|}
DECL|method|isGroupHit ()
specifier|public
name|boolean
name|isGroupHit
parameter_list|()
block|{
return|return
name|groupHit
return|;
block|}
DECL|method|setGroupHit (boolean groupHit)
specifier|public
name|void
name|setGroupHit
parameter_list|(
name|boolean
name|groupHit
parameter_list|)
block|{
name|this
operator|.
name|groupHit
operator|=
name|groupHit
expr_stmt|;
block|}
comment|/**      * @param maxCharacters The maximum number of characters (additional      *                      characters are replaced with "..."). Set to 0 to disable truncation.      * @return A short textual description of the entry in the format:      * Author1, Author2: Title (Year)      */
DECL|method|getAuthorTitleYear (int maxCharacters)
specifier|public
name|String
name|getAuthorTitleYear
parameter_list|(
name|int
name|maxCharacters
parameter_list|)
block|{
name|String
index|[]
name|s
init|=
operator|new
name|String
index|[]
block|{
name|getField
argument_list|(
literal|"author"
argument_list|)
block|,
name|getField
argument_list|(
literal|"title"
argument_list|)
block|,
name|getField
argument_list|(
literal|"year"
argument_list|)
block|}
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|s
operator|.
name|length
condition|;
operator|++
name|i
control|)
block|{
if|if
condition|(
name|s
index|[
name|i
index|]
operator|==
literal|null
condition|)
block|{
name|s
index|[
name|i
index|]
operator|=
literal|"N/A"
expr_stmt|;
block|}
block|}
name|String
name|text
init|=
name|s
index|[
literal|0
index|]
operator|+
literal|": \""
operator|+
name|s
index|[
literal|1
index|]
operator|+
literal|"\" ("
operator|+
name|s
index|[
literal|2
index|]
operator|+
literal|')'
decl_stmt|;
if|if
condition|(
name|maxCharacters
operator|<=
literal|0
operator|||
name|text
operator|.
name|length
argument_list|()
operator|<=
name|maxCharacters
condition|)
block|{
return|return
name|text
return|;
block|}
return|return
name|text
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|maxCharacters
operator|+
literal|1
argument_list|)
operator|+
literal|"..."
return|;
block|}
block|}
end_class

end_unit

