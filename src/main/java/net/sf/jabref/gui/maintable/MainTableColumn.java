begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|bibtex
operator|.
name|EntryTypes
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
name|model
operator|.
name|entry
operator|.
name|TypedBibEntry
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|*
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

begin_class
DECL|class|MainTableColumn
specifier|public
class|class
name|MainTableColumn
block|{
DECL|field|columnName
specifier|private
specifier|final
name|String
name|columnName
decl_stmt|;
DECL|field|bibtexFields
specifier|private
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|bibtexFields
decl_stmt|;
DECL|field|isIconColumn
specifier|private
specifier|final
name|boolean
name|isIconColumn
decl_stmt|;
DECL|field|iconLabel
specifier|private
specifier|final
name|Optional
argument_list|<
name|JLabel
argument_list|>
name|iconLabel
decl_stmt|;
DECL|field|database
specifier|private
specifier|final
name|Optional
argument_list|<
name|BibDatabase
argument_list|>
name|database
decl_stmt|;
DECL|method|MainTableColumn (String columnName)
specifier|public
name|MainTableColumn
parameter_list|(
name|String
name|columnName
parameter_list|)
block|{
name|this
operator|.
name|columnName
operator|=
name|columnName
expr_stmt|;
name|this
operator|.
name|bibtexFields
operator|=
name|Collections
operator|.
name|emptyList
argument_list|()
expr_stmt|;
name|this
operator|.
name|isIconColumn
operator|=
literal|false
expr_stmt|;
name|this
operator|.
name|iconLabel
operator|=
name|Optional
operator|.
name|empty
argument_list|()
expr_stmt|;
name|this
operator|.
name|database
operator|=
name|Optional
operator|.
name|empty
argument_list|()
expr_stmt|;
block|}
DECL|method|MainTableColumn (String columnName, String[] bibtexFields, BibDatabase database)
specifier|public
name|MainTableColumn
parameter_list|(
name|String
name|columnName
parameter_list|,
name|String
index|[]
name|bibtexFields
parameter_list|,
name|BibDatabase
name|database
parameter_list|)
block|{
name|this
operator|.
name|columnName
operator|=
name|columnName
expr_stmt|;
name|this
operator|.
name|bibtexFields
operator|=
name|Collections
operator|.
name|unmodifiableList
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|bibtexFields
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|isIconColumn
operator|=
literal|false
expr_stmt|;
name|this
operator|.
name|iconLabel
operator|=
name|Optional
operator|.
name|empty
argument_list|()
expr_stmt|;
name|this
operator|.
name|database
operator|=
name|Optional
operator|.
name|of
argument_list|(
name|database
argument_list|)
expr_stmt|;
block|}
DECL|method|MainTableColumn (String columnName, String[] bibtexFields, JLabel iconLabel)
specifier|public
name|MainTableColumn
parameter_list|(
name|String
name|columnName
parameter_list|,
name|String
index|[]
name|bibtexFields
parameter_list|,
name|JLabel
name|iconLabel
parameter_list|)
block|{
name|this
operator|.
name|columnName
operator|=
name|columnName
expr_stmt|;
name|this
operator|.
name|bibtexFields
operator|=
name|Collections
operator|.
name|unmodifiableList
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|bibtexFields
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|isIconColumn
operator|=
literal|true
expr_stmt|;
name|this
operator|.
name|iconLabel
operator|=
name|Optional
operator|.
name|of
argument_list|(
name|iconLabel
argument_list|)
expr_stmt|;
name|this
operator|.
name|database
operator|=
name|Optional
operator|.
name|empty
argument_list|()
expr_stmt|;
block|}
comment|/**      * Get the table column name to be displayed in the UI      *      * @return name to be displayed. null if field is empty.      */
DECL|method|getDisplayName ()
specifier|public
name|String
name|getDisplayName
parameter_list|()
block|{
if|if
condition|(
name|bibtexFields
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
literal|null
return|;
block|}
name|StringJoiner
name|joiner
init|=
operator|new
name|StringJoiner
argument_list|(
name|MainTableFormat
operator|.
name|COL_DEFINITION_FIELD_SEPARATOR
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|field
range|:
name|bibtexFields
control|)
block|{
name|joiner
operator|.
name|add
argument_list|(
name|field
argument_list|)
expr_stmt|;
block|}
return|return
name|joiner
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Checks whether the column should display names      * Relevant as name value format can be formatted.      *      * @return true if the bibtex fields contains author or editor      */
DECL|method|isNameColumn ()
specifier|public
name|boolean
name|isNameColumn
parameter_list|()
block|{
return|return
name|bibtexFields
operator|.
name|contains
argument_list|(
literal|"author"
argument_list|)
operator|||
name|bibtexFields
operator|.
name|contains
argument_list|(
literal|"editor"
argument_list|)
return|;
block|}
DECL|method|getColumnName ()
specifier|public
name|String
name|getColumnName
parameter_list|()
block|{
return|return
name|columnName
return|;
block|}
DECL|method|getBibtexFields ()
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getBibtexFields
parameter_list|()
block|{
return|return
name|bibtexFields
return|;
block|}
DECL|method|isIconColumn ()
specifier|public
name|boolean
name|isIconColumn
parameter_list|()
block|{
return|return
name|isIconColumn
return|;
block|}
DECL|method|isFileFilter ()
specifier|public
name|boolean
name|isFileFilter
parameter_list|()
block|{
return|return
literal|false
return|;
comment|// Overridden in SpecialMainTableColumns for file filter columns
block|}
DECL|method|getColumnValue (BibEntry entry)
specifier|public
name|Object
name|getColumnValue
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
if|if
condition|(
name|bibtexFields
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
literal|null
return|;
block|}
name|String
name|content
init|=
literal|null
decl_stmt|;
for|for
control|(
name|String
name|field
range|:
name|bibtexFields
control|)
block|{
if|if
condition|(
name|field
operator|.
name|equals
argument_list|(
name|BibEntry
operator|.
name|TYPE_HEADER
argument_list|)
condition|)
block|{
name|content
operator|=
name|EntryUtil
operator|.
name|capitalizeFirst
argument_list|(
name|entry
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|content
operator|=
name|entry
operator|.
name|getFieldOrAlias
argument_list|(
name|field
argument_list|)
expr_stmt|;
if|if
condition|(
name|database
operator|.
name|isPresent
argument_list|()
operator|&&
literal|"Author"
operator|.
name|equalsIgnoreCase
argument_list|(
name|columnName
argument_list|)
operator|&&
operator|(
name|content
operator|!=
literal|null
operator|)
condition|)
block|{
name|content
operator|=
name|database
operator|.
name|get
argument_list|()
operator|.
name|resolveForStrings
argument_list|(
name|content
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|content
operator|!=
literal|null
condition|)
block|{
break|break;
block|}
block|}
if|if
condition|(
name|isNameColumn
argument_list|()
condition|)
block|{
return|return
name|MainTableNameFormatter
operator|.
name|formatName
argument_list|(
name|content
argument_list|)
return|;
block|}
return|return
name|content
return|;
block|}
DECL|method|getHeaderLabel ()
specifier|public
name|JLabel
name|getHeaderLabel
parameter_list|()
block|{
if|if
condition|(
name|isIconColumn
condition|)
block|{
return|return
name|iconLabel
operator|.
name|get
argument_list|()
return|;
block|}
else|else
block|{
return|return
operator|new
name|JLabel
argument_list|(
name|getDisplayName
argument_list|()
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

