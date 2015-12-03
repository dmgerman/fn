begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2012 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.gui.maintable
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|maintable
package|;
end_package

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
name|HashMap
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
name|Vector
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
name|model
operator|.
name|entry
operator|.
name|AuthorList
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
name|BibtexEntry
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
name|JabRefPreferences
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
name|EntryUtil
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
name|specialfields
operator|.
name|Priority
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
name|specialfields
operator|.
name|Rank
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
name|specialfields
operator|.
name|ReadStatus
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
name|specialfields
operator|.
name|SpecialFieldValue
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
name|specialfields
operator|.
name|SpecialFieldsUtils
import|;
end_import

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|gui
operator|.
name|TableFormat
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JLabel
import|;
end_import

begin_comment
comment|/**  * Class defining the contents and column headers of the main table.  */
end_comment

begin_class
DECL|class|MainTableFormat
specifier|public
class|class
name|MainTableFormat
implements|implements
name|TableFormat
argument_list|<
name|BibtexEntry
argument_list|>
block|{
comment|// Character separating field names that are to be used in sequence as
comment|// fallbacks for a single column (e.g. "author/editor" to use editor where
comment|// author is not set):
DECL|field|COL_DEFINITION_FIELD_SEPARATOR
specifier|public
specifier|static
specifier|final
name|String
name|COL_DEFINITION_FIELD_SEPARATOR
init|=
literal|"/"
decl_stmt|;
comment|// Values to gather iconImages for those columns
comment|// These values are also used to put a heading into the table; see getColumnName(int)
DECL|field|PDF
specifier|private
specifier|static
specifier|final
name|String
index|[]
name|PDF
init|=
block|{
literal|"pdf"
block|,
literal|"ps"
block|}
decl_stmt|;
DECL|field|URL_FIRST
specifier|private
specifier|static
specifier|final
name|String
index|[]
name|URL_FIRST
init|=
block|{
literal|"url"
block|,
literal|"doi"
block|}
decl_stmt|;
DECL|field|DOI_FIRST
specifier|private
specifier|static
specifier|final
name|String
index|[]
name|DOI_FIRST
init|=
block|{
literal|"doi"
block|,
literal|"url"
block|}
decl_stmt|;
DECL|field|ARXIV
specifier|private
specifier|static
specifier|final
name|String
index|[]
name|ARXIV
init|=
block|{
literal|"eprint"
block|}
decl_stmt|;
DECL|field|FILE
specifier|public
specifier|static
specifier|final
name|String
index|[]
name|FILE
init|=
block|{
name|Globals
operator|.
name|FILE_FIELD
block|}
decl_stmt|;
DECL|field|tableColumns
specifier|private
name|List
argument_list|<
name|MainTableColumn
argument_list|>
name|tableColumns
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|namesAsIs
specifier|private
name|boolean
name|namesAsIs
decl_stmt|;
DECL|field|abbr_names
specifier|private
name|boolean
name|abbr_names
decl_stmt|;
DECL|field|namesNatbib
specifier|private
name|boolean
name|namesNatbib
decl_stmt|;
DECL|field|namesFf
specifier|private
name|boolean
name|namesFf
decl_stmt|;
DECL|field|namesLf
specifier|private
name|boolean
name|namesLf
decl_stmt|;
DECL|field|namesLastOnly
specifier|private
name|boolean
name|namesLastOnly
decl_stmt|;
annotation|@
name|Override
DECL|method|getColumnCount ()
specifier|public
name|int
name|getColumnCount
parameter_list|()
block|{
return|return
name|tableColumns
operator|.
name|size
argument_list|()
return|;
block|}
comment|/**      * @return the string that should be put in the column header      */
annotation|@
name|Override
DECL|method|getColumnName (int col)
specifier|public
name|String
name|getColumnName
parameter_list|(
name|int
name|col
parameter_list|)
block|{
return|return
name|tableColumns
operator|.
name|get
argument_list|(
name|col
argument_list|)
operator|.
name|getDisplayName
argument_list|()
return|;
block|}
DECL|method|getTableColumns ()
specifier|public
name|List
argument_list|<
name|MainTableColumn
argument_list|>
name|getTableColumns
parameter_list|()
block|{
return|return
name|tableColumns
return|;
block|}
comment|/**      * This method returns a string array indicating the types of icons to be displayed in the given column.      * It returns null if the column is not an icon column, and thereby also serves to identify icon      * columns.      */
comment|//TODO to be removed?
DECL|method|getIconTypeForColumn (int col)
specifier|public
name|String
index|[]
name|getIconTypeForColumn
parameter_list|(
name|int
name|col
parameter_list|)
block|{
return|return
literal|null
return|;
comment|//        Object o = iconCols.get(Integer.valueOf(col));
comment|//        if (o != null) {
comment|//            return (String[]) o;
comment|//        } else {
comment|//            return null;
comment|//        }
block|}
comment|/**      * Finds the column index for the given column name.      *      * @param colName The column name      * @return The column index if any, or -1 if no column has that name.      */
DECL|method|getColumnIndex (String colName)
specifier|public
name|int
name|getColumnIndex
parameter_list|(
name|String
name|colName
parameter_list|)
block|{
for|for
control|(
name|MainTableColumn
name|tableColumn
range|:
name|tableColumns
control|)
block|{
if|if
condition|(
name|tableColumn
operator|.
name|getColumnName
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
name|colName
argument_list|)
condition|)
block|{
return|return
name|tableColumns
operator|.
name|lastIndexOf
argument_list|(
name|tableColumn
argument_list|)
return|;
block|}
block|}
return|return
operator|-
literal|1
return|;
block|}
annotation|@
name|Override
DECL|method|getColumnValue (BibtexEntry be, int col)
specifier|public
name|Object
name|getColumnValue
parameter_list|(
name|BibtexEntry
name|be
parameter_list|,
name|int
name|col
parameter_list|)
block|{
return|return
name|tableColumns
operator|.
name|get
argument_list|(
name|col
argument_list|)
operator|.
name|getColumnValue
argument_list|(
name|be
argument_list|)
return|;
block|}
comment|/**      * Format a name field for the table, according to user preferences.      *      * @param nameToFormat The contents of the name field.      * @return The formatted name field.      */
comment|// TODO move to some Util class?
DECL|method|formatName (String nameToFormat)
specifier|public
name|String
name|formatName
parameter_list|(
name|String
name|nameToFormat
parameter_list|)
block|{
if|if
condition|(
name|nameToFormat
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
elseif|else
if|if
condition|(
name|namesAsIs
condition|)
block|{
return|return
name|nameToFormat
return|;
block|}
elseif|else
if|if
condition|(
name|namesNatbib
condition|)
block|{
name|nameToFormat
operator|=
name|AuthorList
operator|.
name|fixAuthor_Natbib
argument_list|(
name|nameToFormat
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|namesLastOnly
condition|)
block|{
name|nameToFormat
operator|=
name|AuthorList
operator|.
name|fixAuthor_lastNameOnlyCommas
argument_list|(
name|nameToFormat
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|namesFf
condition|)
block|{
name|nameToFormat
operator|=
name|AuthorList
operator|.
name|fixAuthor_firstNameFirstCommas
argument_list|(
name|nameToFormat
argument_list|,
name|abbr_names
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|namesLf
condition|)
block|{
name|nameToFormat
operator|=
name|AuthorList
operator|.
name|fixAuthor_lastNameFirstCommas
argument_list|(
name|nameToFormat
argument_list|,
name|abbr_names
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
return|return
name|nameToFormat
return|;
block|}
DECL|method|updateTableFormat ()
specifier|public
name|void
name|updateTableFormat
parameter_list|()
block|{
comment|// clear existing column configuration
name|tableColumns
operator|.
name|clear
argument_list|()
expr_stmt|;
comment|// Add numbering column to tableColumns
name|tableColumns
operator|.
name|add
argument_list|(
name|SpecialMainTableColumns
operator|.
name|NUMBER_COL
argument_list|)
expr_stmt|;
comment|// Add 'normal' bibtex fields as configured in the preferences
comment|// Read table columns from prefs:
name|String
index|[]
name|colSettings
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getStringArray
argument_list|(
name|JabRefPreferences
operator|.
name|COLUMN_NAMES
argument_list|)
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
name|colSettings
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
comment|// stored column name will be used as columnName
name|String
name|columnName
init|=
name|colSettings
index|[
name|i
index|]
decl_stmt|;
comment|// There might be more than one field to display, e.g., "author/editor" or "date/year" - so split
comment|// at MainTableFormat.COL_DEFINITION_FIELD_SEPARATOR
name|String
index|[]
name|fields
init|=
name|colSettings
index|[
name|i
index|]
operator|.
name|split
argument_list|(
name|MainTableFormat
operator|.
name|COL_DEFINITION_FIELD_SEPARATOR
argument_list|)
decl_stmt|;
name|tableColumns
operator|.
name|add
argument_list|(
operator|new
name|MainTableColumn
argument_list|(
name|columnName
argument_list|,
name|fields
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Add the "special" icon columns (e.g., ranking, file, ...) that are enabled in preferences.
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|SpecialFieldsUtils
operator|.
name|PREF_SPECIALFIELDSENABLED
argument_list|)
condition|)
block|{
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|SpecialFieldsUtils
operator|.
name|PREF_SHOWCOLUMN_RANKING
argument_list|)
condition|)
block|{
name|tableColumns
operator|.
name|add
argument_list|(
name|SpecialMainTableColumns
operator|.
name|RANKING_COLUMN
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|SpecialFieldsUtils
operator|.
name|PREF_SHOWCOLUMN_RELEVANCE
argument_list|)
condition|)
block|{
name|tableColumns
operator|.
name|add
argument_list|(
name|SpecialMainTableColumns
operator|.
name|RELEVANCE_COLUMN
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|SpecialFieldsUtils
operator|.
name|PREF_SHOWCOLUMN_QUALITY
argument_list|)
condition|)
block|{
name|tableColumns
operator|.
name|add
argument_list|(
name|SpecialMainTableColumns
operator|.
name|QUALITY_COLUMN
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|SpecialFieldsUtils
operator|.
name|PREF_SHOWCOLUMN_PRIORITY
argument_list|)
condition|)
block|{
name|tableColumns
operator|.
name|add
argument_list|(
name|SpecialMainTableColumns
operator|.
name|PRIORITY_COLUMN
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|SpecialFieldsUtils
operator|.
name|PREF_SHOWCOLUMN_PRINTED
argument_list|)
condition|)
block|{
name|tableColumns
operator|.
name|add
argument_list|(
name|SpecialMainTableColumns
operator|.
name|PRINTED_COLUMN
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|SpecialFieldsUtils
operator|.
name|PREF_SHOWCOLUMN_READ
argument_list|)
condition|)
block|{
name|tableColumns
operator|.
name|add
argument_list|(
name|SpecialMainTableColumns
operator|.
name|READ_STATUS_COLUMN
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|FILE_COLUMN
argument_list|)
condition|)
block|{
name|tableColumns
operator|.
name|add
argument_list|(
name|SpecialMainTableColumns
operator|.
name|FILE_COLUMN
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|PDF_COLUMN
argument_list|)
condition|)
block|{
name|tableColumns
operator|.
name|add
argument_list|(
name|SpecialMainTableColumns
operator|.
name|createIconColumn
argument_list|(
name|JabRefPreferences
operator|.
name|PDF_COLUMN
argument_list|,
name|MainTableFormat
operator|.
name|PDF
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|URL_COLUMN
argument_list|)
condition|)
block|{
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|PREFER_URL_DOI
argument_list|)
condition|)
block|{
name|tableColumns
operator|.
name|add
argument_list|(
name|SpecialMainTableColumns
operator|.
name|createIconColumn
argument_list|(
name|JabRefPreferences
operator|.
name|URL_COLUMN
argument_list|,
name|MainTableFormat
operator|.
name|DOI_FIRST
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|tableColumns
operator|.
name|add
argument_list|(
name|SpecialMainTableColumns
operator|.
name|createIconColumn
argument_list|(
name|JabRefPreferences
operator|.
name|URL_COLUMN
argument_list|,
name|MainTableFormat
operator|.
name|URL_FIRST
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|ARXIV_COLUMN
argument_list|)
condition|)
block|{
name|tableColumns
operator|.
name|add
argument_list|(
name|SpecialMainTableColumns
operator|.
name|createIconColumn
argument_list|(
name|JabRefPreferences
operator|.
name|ARXIV_COLUMN
argument_list|,
name|MainTableFormat
operator|.
name|ARXIV
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|EXTRA_FILE_COLUMNS
argument_list|)
condition|)
block|{
name|String
index|[]
name|desiredColumns
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getStringArray
argument_list|(
name|JabRefPreferences
operator|.
name|LIST_OF_FILE_COLUMNS
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|desiredColumn
range|:
name|desiredColumns
control|)
block|{
name|tableColumns
operator|.
name|add
argument_list|(
name|SpecialMainTableColumns
operator|.
name|createFileIconColumn
argument_list|(
name|desiredColumn
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Read name format options:
name|namesNatbib
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|NAMES_NATBIB
argument_list|)
expr_stmt|;
comment|//MK:
name|namesLastOnly
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|NAMES_LAST_ONLY
argument_list|)
expr_stmt|;
name|namesAsIs
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|NAMES_AS_IS
argument_list|)
expr_stmt|;
name|abbr_names
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|ABBR_AUTHOR_NAMES
argument_list|)
expr_stmt|;
comment|//MK:
name|namesFf
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|NAMES_FIRST_LAST
argument_list|)
expr_stmt|;
name|namesLf
operator|=
operator|!
operator|(
name|namesAsIs
operator|||
name|namesFf
operator|||
name|namesNatbib
operator|||
name|namesLastOnly
operator|)
expr_stmt|;
comment|// None of the above.
block|}
block|}
end_class

end_unit

