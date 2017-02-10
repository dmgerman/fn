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
name|java
operator|.
name|util
operator|.
name|Collections
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
name|Optional
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|StringJoiner
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
name|layout
operator|.
name|LayoutFormatter
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
name|layout
operator|.
name|format
operator|.
name|LatexToUnicodeFormatter
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
name|model
operator|.
name|entry
operator|.
name|FieldProperty
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
name|InternalBibtexFields
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
DECL|field|toUnicode
specifier|private
specifier|final
name|LayoutFormatter
name|toUnicode
init|=
operator|new
name|LatexToUnicodeFormatter
argument_list|()
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
DECL|method|MainTableColumn (String columnName, List<String> bibtexFields, BibDatabase database)
specifier|public
name|MainTableColumn
parameter_list|(
name|String
name|columnName
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
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
name|bibtexFields
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
DECL|method|MainTableColumn (String columnName, List<String> bibtexFields, JLabel iconLabel)
specifier|public
name|MainTableColumn
parameter_list|(
name|String
name|columnName
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
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
name|bibtexFields
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
name|FieldName
operator|.
name|FIELD_SEPARATOR
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
name|boolean
name|isNameColumn
init|=
literal|false
decl_stmt|;
name|Optional
argument_list|<
name|String
argument_list|>
name|content
init|=
name|Optional
operator|.
name|empty
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|field
range|:
name|bibtexFields
control|)
block|{
name|content
operator|=
name|entry
operator|.
name|getResolvedFieldOrAlias
argument_list|(
name|field
argument_list|,
name|database
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|content
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|isNameColumn
operator|=
name|InternalBibtexFields
operator|.
name|getFieldProperties
argument_list|(
name|field
argument_list|)
operator|.
name|contains
argument_list|(
name|FieldProperty
operator|.
name|PERSON_NAMES
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
name|String
name|result
init|=
name|content
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|isNameColumn
condition|)
block|{
name|result
operator|=
name|toUnicode
operator|.
name|format
argument_list|(
name|MainTableNameFormatter
operator|.
name|formatName
argument_list|(
name|result
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|result
operator|!=
literal|null
condition|)
block|{
name|result
operator|=
name|toUnicode
operator|.
name|format
argument_list|(
name|result
argument_list|)
operator|.
name|trim
argument_list|()
expr_stmt|;
block|}
return|return
name|result
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
comment|/**      * Check if the value returned by getColumnValue() is the same as a simple check of the entry's field(s) would give      * The reasons for being different are (combinations may also happen):      * - The entry has a crossref where the field content is obtained from      * - The field has a string in it (which getColumnValue() resolves)      * - There are some alias fields. For example, if the entry has a date field but no year field,      *   {@link BibEntry#getResolvedFieldOrAlias(String, BibDatabase)} will return the year value from the date field      *   when queried for year      *      * @param entry the BibEntry      * @return true if the value returned by getColumnValue() is resolved as outlined above      */
DECL|method|isResolved (BibEntry entry)
specifier|public
name|boolean
name|isResolved
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
literal|false
return|;
block|}
name|Optional
argument_list|<
name|String
argument_list|>
name|resolvedFieldContent
init|=
name|Optional
operator|.
name|empty
argument_list|()
decl_stmt|;
name|Optional
argument_list|<
name|String
argument_list|>
name|plainFieldContent
init|=
name|Optional
operator|.
name|empty
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|field
range|:
name|bibtexFields
control|)
block|{
comment|// entry type or bibtex key will never be resolved
if|if
condition|(
name|BibEntry
operator|.
name|TYPE_HEADER
operator|.
name|equals
argument_list|(
name|field
argument_list|)
operator|||
name|BibEntry
operator|.
name|OBSOLETE_TYPE_HEADER
operator|.
name|equals
argument_list|(
name|field
argument_list|)
operator|||
name|BibEntry
operator|.
name|KEY_FIELD
operator|.
name|equals
argument_list|(
name|field
argument_list|)
condition|)
block|{
return|return
literal|false
return|;
block|}
else|else
block|{
name|plainFieldContent
operator|=
name|entry
operator|.
name|getField
argument_list|(
name|field
argument_list|)
expr_stmt|;
name|resolvedFieldContent
operator|=
name|entry
operator|.
name|getResolvedFieldOrAlias
argument_list|(
name|field
argument_list|,
name|database
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|resolvedFieldContent
operator|.
name|isPresent
argument_list|()
condition|)
block|{
break|break;
block|}
block|}
return|return
operator|(
operator|!
name|resolvedFieldContent
operator|.
name|equals
argument_list|(
name|plainFieldContent
argument_list|)
operator|)
return|;
block|}
block|}
end_class

end_unit

