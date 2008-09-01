begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Hashtable
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
name|BasePanel
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
name|BibtexFields
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
name|GUIGlobals
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
name|SearchRuleSet
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
name|Util
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
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|matchers
operator|.
name|Matcher
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
specifier|public
specifier|static
specifier|final
name|String
index|[]
DECL|field|PDF
name|PDF
init|=
block|{
literal|"pdf"
block|,
literal|"ps"
block|}
decl_stmt|,
DECL|field|URL_
name|URL_
init|=
block|{
literal|"url"
block|,
literal|"doi"
block|}
decl_stmt|,
DECL|field|CITESEER
name|CITESEER
init|=
block|{
literal|"citeseerurl"
block|}
decl_stmt|,
DECL|field|FILE
name|FILE
init|=
block|{
name|GUIGlobals
operator|.
name|FILE_FIELD
block|}
decl_stmt|;
DECL|field|panel
name|BasePanel
name|panel
decl_stmt|;
DECL|field|columns
specifier|private
name|String
index|[]
index|[]
name|columns
decl_stmt|;
comment|// Contains the current column names.
DECL|field|padleft
specifier|public
name|int
name|padleft
init|=
operator|-
literal|1
decl_stmt|;
comment|// padleft indicates how many columns (starting from left) are
comment|// special columns (number column or icon column).
DECL|field|iconCols
specifier|private
name|HashMap
argument_list|<
name|Integer
argument_list|,
name|String
index|[]
argument_list|>
name|iconCols
init|=
operator|new
name|HashMap
argument_list|<
name|Integer
argument_list|,
name|String
index|[]
argument_list|>
argument_list|()
decl_stmt|;
DECL|field|nameCols
name|int
index|[]
index|[]
name|nameCols
init|=
literal|null
decl_stmt|;
DECL|field|namesAsIs
DECL|field|abbr_names
DECL|field|namesNatbib
DECL|field|namesFf
DECL|field|namesLf
DECL|field|namesLastOnly
DECL|field|showShort
name|boolean
name|namesAsIs
decl_stmt|,
name|abbr_names
decl_stmt|,
name|namesNatbib
decl_stmt|,
name|namesFf
decl_stmt|,
name|namesLf
decl_stmt|,
name|namesLastOnly
decl_stmt|,
name|showShort
decl_stmt|;
DECL|method|MainTableFormat (BasePanel panel)
specifier|public
name|MainTableFormat
parameter_list|(
name|BasePanel
name|panel
parameter_list|)
block|{
name|this
operator|.
name|panel
operator|=
name|panel
expr_stmt|;
block|}
DECL|method|getColumnCount ()
specifier|public
name|int
name|getColumnCount
parameter_list|()
block|{
return|return
name|padleft
operator|+
name|columns
operator|.
name|length
return|;
block|}
DECL|method|getColumnName (int col)
specifier|public
name|String
name|getColumnName
parameter_list|(
name|int
name|col
parameter_list|)
block|{
if|if
condition|(
name|col
operator|==
literal|0
condition|)
block|{
return|return
name|GUIGlobals
operator|.
name|NUMBER_COL
return|;
block|}
elseif|else
if|if
condition|(
name|getIconTypeForColumn
argument_list|(
name|col
argument_list|)
operator|!=
literal|null
condition|)
block|{
return|return
literal|""
return|;
block|}
else|else
comment|// try to find an alternative fieldname (for display)
block|{
name|String
index|[]
name|fld
init|=
name|columns
index|[
name|col
operator|-
name|padleft
index|]
decl_stmt|;
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
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
name|fld
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|i
operator|>
literal|0
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|'/'
argument_list|)
expr_stmt|;
name|String
name|disName
init|=
name|BibtexFields
operator|.
name|getFieldDisplayName
argument_list|(
name|fld
index|[
name|i
index|]
argument_list|)
decl_stmt|;
if|if
condition|(
name|disName
operator|!=
literal|null
condition|)
name|sb
operator|.
name|append
argument_list|(
name|disName
argument_list|)
expr_stmt|;
else|else
name|sb
operator|.
name|append
argument_list|(
name|Util
operator|.
name|nCase
argument_list|(
name|fld
index|[
name|i
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
comment|/*String disName = BibtexFields.getFieldDisplayName(columns[col - padleft]) ;           if ( disName != null)           {             return disName ;           } */
block|}
comment|//return Util.nCase(columns[col - padleft]);
block|}
comment|/**      * This method returns a string array indicating the types of icons to be displayed in the given column.      * It returns null if the column is not an icon column, and thereby also serves to identify icon      * columns.      */
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
name|Object
name|o
init|=
name|iconCols
operator|.
name|get
argument_list|(
operator|new
name|Integer
argument_list|(
name|col
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|o
operator|!=
literal|null
condition|)
return|return
operator|(
name|String
index|[]
operator|)
name|o
return|;
else|else
return|return
literal|null
return|;
block|}
comment|/**      * Finds the column index for the given column name.      * @param colName The column name      * @return The column index if any, or -1 if no column has that name.      */
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
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|columns
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
comment|// TODO: is the following line correct with [0] ?
if|if
condition|(
name|columns
index|[
name|i
index|]
index|[
literal|0
index|]
operator|.
name|equalsIgnoreCase
argument_list|(
name|colName
argument_list|)
condition|)
return|return
name|i
operator|+
name|padleft
return|;
block|}
return|return
operator|-
literal|1
return|;
block|}
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
name|Object
name|o
init|=
literal|null
decl_stmt|;
name|String
index|[]
name|iconType
init|=
name|getIconTypeForColumn
argument_list|(
name|col
argument_list|)
decl_stmt|;
comment|// If non-null, indicates an icon column's type.
if|if
condition|(
name|col
operator|==
literal|0
condition|)
block|{
name|o
operator|=
literal|"#"
expr_stmt|;
comment|// + (row + 1);
block|}
elseif|else
if|if
condition|(
name|iconType
operator|!=
literal|null
condition|)
block|{
name|int
name|hasField
init|=
operator|-
literal|1
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
name|iconType
operator|.
name|length
operator|-
literal|1
init|;
name|i
operator|>=
literal|0
condition|;
name|i
operator|--
control|)
if|if
condition|(
name|hasField
argument_list|(
name|be
argument_list|,
name|iconType
index|[
name|i
index|]
argument_list|)
condition|)
name|hasField
operator|=
name|i
expr_stmt|;
if|if
condition|(
name|hasField
operator|<
literal|0
condition|)
return|return
literal|null
return|;
comment|// Ok, so we are going to display an icon. Find out which one, and return it:
if|if
condition|(
name|iconType
index|[
name|hasField
index|]
operator|.
name|equals
argument_list|(
name|GUIGlobals
operator|.
name|FILE_FIELD
argument_list|)
condition|)
block|{
name|o
operator|=
name|FileListTableModel
operator|.
name|getFirstLabel
argument_list|(
name|be
operator|.
name|getField
argument_list|(
name|GUIGlobals
operator|.
name|FILE_FIELD
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
name|o
operator|=
name|GUIGlobals
operator|.
name|getTableIcon
argument_list|(
name|iconType
index|[
name|hasField
index|]
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|String
index|[]
name|fld
init|=
name|columns
index|[
name|col
operator|-
name|padleft
index|]
decl_stmt|;
comment|// Go through the fields until we find one with content:
name|int
name|j
init|=
literal|0
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
name|fld
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|fld
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
name|GUIGlobals
operator|.
name|TYPE_HEADER
argument_list|)
condition|)
name|o
operator|=
name|be
operator|.
name|getType
argument_list|()
operator|.
name|getName
argument_list|()
expr_stmt|;
else|else
name|o
operator|=
name|be
operator|.
name|getField
argument_list|(
name|fld
index|[
name|i
index|]
argument_list|)
expr_stmt|;
if|if
condition|(
name|o
operator|!=
literal|null
condition|)
block|{
name|j
operator|=
name|i
expr_stmt|;
break|break;
block|}
block|}
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|nameCols
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
operator|(
name|col
operator|-
name|padleft
operator|==
name|nameCols
index|[
name|i
index|]
index|[
literal|0
index|]
operator|)
operator|&&
operator|(
name|nameCols
index|[
name|i
index|]
index|[
literal|1
index|]
operator|==
name|j
operator|)
condition|)
block|{
return|return
name|formatName
argument_list|(
name|o
argument_list|)
return|;
block|}
block|}
block|}
return|return
name|o
return|;
block|}
comment|/**      * Format a name field for the table, according to user preferences.      * @param o The contents of the name field.      * @return The formatted name field.      */
DECL|method|formatName (Object o)
specifier|public
name|Object
name|formatName
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|o
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
if|if
condition|(
name|namesAsIs
condition|)
return|return
name|o
return|;
if|if
condition|(
name|namesNatbib
condition|)
name|o
operator|=
name|AuthorList
operator|.
name|fixAuthor_Natbib
argument_list|(
operator|(
name|String
operator|)
name|o
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|namesLastOnly
condition|)
name|o
operator|=
name|AuthorList
operator|.
name|fixAuthor_lastNameOnlyCommas
argument_list|(
operator|(
name|String
operator|)
name|o
argument_list|,
literal|false
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|namesFf
condition|)
name|o
operator|=
name|AuthorList
operator|.
name|fixAuthor_firstNameFirstCommas
argument_list|(
operator|(
name|String
operator|)
name|o
argument_list|,
name|abbr_names
argument_list|,
literal|false
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|namesLf
condition|)
name|o
operator|=
name|AuthorList
operator|.
name|fixAuthor_lastNameFirstCommas
argument_list|(
operator|(
name|String
operator|)
name|o
argument_list|,
name|abbr_names
argument_list|,
literal|false
argument_list|)
expr_stmt|;
return|return
name|o
return|;
block|}
DECL|method|hasField (BibtexEntry be, String field)
specifier|public
name|boolean
name|hasField
parameter_list|(
name|BibtexEntry
name|be
parameter_list|,
name|String
name|field
parameter_list|)
block|{
comment|// Returns true iff the entry has a nonzero value in its
comment|// 'search' field.
return|return
operator|(
operator|(
name|be
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|be
operator|.
name|getField
argument_list|(
name|field
argument_list|)
operator|!=
literal|null
operator|)
operator|)
return|;
block|}
DECL|method|updateTableFormat ()
specifier|public
name|void
name|updateTableFormat
parameter_list|()
block|{
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
literal|"columnNames"
argument_list|)
decl_stmt|;
name|columns
operator|=
operator|new
name|String
index|[
name|colSettings
operator|.
name|length
index|]
index|[]
expr_stmt|;
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
name|COL_DEFINITION_FIELD_SEPARATOR
argument_list|)
decl_stmt|;
name|columns
index|[
name|i
index|]
operator|=
operator|new
name|String
index|[
name|fields
operator|.
name|length
index|]
expr_stmt|;
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|fields
operator|.
name|length
condition|;
name|j
operator|++
control|)
block|{
name|columns
index|[
name|i
index|]
index|[
name|j
index|]
operator|=
name|fields
index|[
name|j
index|]
expr_stmt|;
block|}
block|}
comment|// Read name format options:
name|showShort
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"showShort"
argument_list|)
expr_stmt|;
comment|//MK:
name|namesNatbib
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"namesNatbib"
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
literal|"namesLastOnly"
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
literal|"namesAsIs"
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
literal|"abbrAuthorNames"
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
literal|"namesFf"
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
comment|// Set the icon columns, indicating the number of special columns to the left.
comment|// We add those that are enabled in preferences.
name|iconCols
operator|.
name|clear
argument_list|()
expr_stmt|;
name|int
name|coln
init|=
literal|1
decl_stmt|;
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"fileColumn"
argument_list|)
condition|)
name|iconCols
operator|.
name|put
argument_list|(
name|coln
operator|++
argument_list|,
name|FILE
argument_list|)
expr_stmt|;
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"pdfColumn"
argument_list|)
condition|)
name|iconCols
operator|.
name|put
argument_list|(
name|coln
operator|++
argument_list|,
name|PDF
argument_list|)
expr_stmt|;
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"urlColumn"
argument_list|)
condition|)
name|iconCols
operator|.
name|put
argument_list|(
name|coln
operator|++
argument_list|,
name|URL_
argument_list|)
expr_stmt|;
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"citeseerColumn"
argument_list|)
condition|)
name|iconCols
operator|.
name|put
argument_list|(
name|coln
operator|++
argument_list|,
name|CITESEER
argument_list|)
expr_stmt|;
comment|// Add 1 to the number of icon columns to get padleft.
name|padleft
operator|=
literal|1
operator|+
name|iconCols
operator|.
name|size
argument_list|()
expr_stmt|;
comment|// Set up the int[][] nameCols, to mark which columns should be
comment|// treated as lists of names. This is to provide a correct presentation
comment|// of names as efficiently as possible.
comment|// Each subarray contains the column number (before padding) and the
comment|// subfield number in case a column has fallback fields.
name|Vector
argument_list|<
name|int
index|[]
argument_list|>
name|tmp
init|=
operator|new
name|Vector
argument_list|<
name|int
index|[]
argument_list|>
argument_list|(
literal|2
argument_list|,
literal|1
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
name|columns
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|columns
index|[
name|i
index|]
operator|.
name|length
condition|;
name|j
operator|++
control|)
block|{
if|if
condition|(
name|columns
index|[
name|i
index|]
index|[
name|j
index|]
operator|.
name|equals
argument_list|(
literal|"author"
argument_list|)
operator|||
name|columns
index|[
name|i
index|]
index|[
name|j
index|]
operator|.
name|equals
argument_list|(
literal|"editor"
argument_list|)
condition|)
block|{
name|tmp
operator|.
name|add
argument_list|(
operator|new
name|int
index|[]
block|{
name|i
block|,
name|j
block|}
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|nameCols
operator|=
operator|new
name|int
index|[
name|tmp
operator|.
name|size
argument_list|()
index|]
index|[]
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|nameCols
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|nameCols
index|[
name|i
index|]
operator|=
name|tmp
operator|.
name|elementAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|isIconColumn (int col)
specifier|public
name|boolean
name|isIconColumn
parameter_list|(
name|int
name|col
parameter_list|)
block|{
return|return
operator|(
name|getIconTypeForColumn
argument_list|(
name|col
argument_list|)
operator|!=
literal|null
operator|)
return|;
block|}
DECL|class|NoSearchMatcher
specifier|static
class|class
name|NoSearchMatcher
implements|implements
name|Matcher
argument_list|<
name|BibtexEntry
argument_list|>
block|{
DECL|method|matches (BibtexEntry object)
specifier|public
name|boolean
name|matches
parameter_list|(
name|BibtexEntry
name|object
parameter_list|)
block|{
return|return
literal|true
return|;
block|}
block|}
DECL|class|SearchMatcher
specifier|static
class|class
name|SearchMatcher
implements|implements
name|Matcher
argument_list|<
name|BibtexEntry
argument_list|>
block|{
DECL|field|ruleSet
specifier|private
name|SearchRuleSet
name|ruleSet
decl_stmt|;
DECL|field|searchOptions
specifier|private
name|Hashtable
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|searchOptions
decl_stmt|;
DECL|method|SearchMatcher (SearchRuleSet ruleSet, Hashtable<String, String> searchOptions)
specifier|public
name|SearchMatcher
parameter_list|(
name|SearchRuleSet
name|ruleSet
parameter_list|,
name|Hashtable
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|searchOptions
parameter_list|)
block|{
name|this
operator|.
name|ruleSet
operator|=
name|ruleSet
expr_stmt|;
name|this
operator|.
name|searchOptions
operator|=
name|searchOptions
expr_stmt|;
block|}
DECL|method|matches (BibtexEntry entry)
specifier|public
name|boolean
name|matches
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
name|int
name|result
init|=
name|ruleSet
operator|.
name|applyRule
argument_list|(
name|searchOptions
argument_list|,
name|entry
argument_list|)
decl_stmt|;
return|return
name|result
operator|>
literal|0
return|;
block|}
block|}
block|}
end_class

end_unit

