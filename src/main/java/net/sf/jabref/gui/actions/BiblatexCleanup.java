begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.actions
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|actions
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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
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
name|FieldChange
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
name|cleanup
operator|.
name|Cleaner
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
name|EntryConverter
import|;
end_import

begin_comment
comment|/**  * Converts the entry to BibLatex format.  */
end_comment

begin_class
DECL|class|BiblatexCleanup
specifier|public
class|class
name|BiblatexCleanup
implements|implements
name|Cleaner
block|{
annotation|@
name|Override
DECL|method|cleanup (BibEntry entry)
specifier|public
name|List
argument_list|<
name|FieldChange
argument_list|>
name|cleanup
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
name|ArrayList
argument_list|<
name|FieldChange
argument_list|>
name|changes
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|alias
range|:
name|EntryConverter
operator|.
name|FIELD_ALIASES_TEX_TO_LTX
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|String
name|oldFieldName
init|=
name|alias
operator|.
name|getKey
argument_list|()
decl_stmt|;
name|String
name|newFieldName
init|=
name|alias
operator|.
name|getValue
argument_list|()
decl_stmt|;
name|String
name|oldValue
init|=
name|entry
operator|.
name|getField
argument_list|(
name|oldFieldName
argument_list|)
decl_stmt|;
name|String
name|newValue
init|=
name|entry
operator|.
name|getField
argument_list|(
name|newFieldName
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|oldValue
operator|!=
literal|null
operator|)
operator|&&
operator|(
operator|!
name|oldValue
operator|.
name|isEmpty
argument_list|()
operator|)
operator|&&
operator|(
name|newValue
operator|==
literal|null
operator|)
condition|)
block|{
comment|// There is content in the old field and no value in the new, so just copy
name|entry
operator|.
name|setField
argument_list|(
name|newFieldName
argument_list|,
name|oldValue
argument_list|)
expr_stmt|;
name|changes
operator|.
name|add
argument_list|(
operator|new
name|FieldChange
argument_list|(
name|entry
argument_list|,
name|newFieldName
argument_list|,
literal|null
argument_list|,
name|oldValue
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|oldFieldName
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|changes
operator|.
name|add
argument_list|(
operator|new
name|FieldChange
argument_list|(
name|entry
argument_list|,
name|oldFieldName
argument_list|,
name|oldValue
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Dates: create date out of year and month, save it and delete old fields
if|if
condition|(
operator|(
name|entry
operator|.
name|getField
argument_list|(
literal|"date"
argument_list|)
operator|==
literal|null
operator|)
operator|||
operator|(
name|entry
operator|.
name|getField
argument_list|(
literal|"date"
argument_list|)
operator|.
name|isEmpty
argument_list|()
operator|)
condition|)
block|{
name|String
name|newDate
init|=
name|entry
operator|.
name|getFieldOrAlias
argument_list|(
literal|"date"
argument_list|)
decl_stmt|;
name|String
name|oldYear
init|=
name|entry
operator|.
name|getField
argument_list|(
literal|"year"
argument_list|)
decl_stmt|;
name|String
name|oldMonth
init|=
name|entry
operator|.
name|getField
argument_list|(
literal|"month"
argument_list|)
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"date"
argument_list|,
name|newDate
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"month"
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|changes
operator|.
name|add
argument_list|(
operator|new
name|FieldChange
argument_list|(
name|entry
argument_list|,
literal|"date"
argument_list|,
literal|null
argument_list|,
name|newDate
argument_list|)
argument_list|)
expr_stmt|;
name|changes
operator|.
name|add
argument_list|(
operator|new
name|FieldChange
argument_list|(
name|entry
argument_list|,
literal|"year"
argument_list|,
name|oldYear
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|changes
operator|.
name|add
argument_list|(
operator|new
name|FieldChange
argument_list|(
name|entry
argument_list|,
literal|"month"
argument_list|,
name|oldMonth
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|changes
return|;
block|}
block|}
end_class

end_unit

