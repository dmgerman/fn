begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.fieldeditors
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|fieldeditors
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
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
name|javafx
operator|.
name|util
operator|.
name|StringConverter
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|autocompleter
operator|.
name|AutoCompleteSuggestionProvider
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|integrity
operator|.
name|FieldCheckers
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
operator|.
name|BibDatabaseMode
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|Month
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|field
operator|.
name|Field
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|strings
operator|.
name|StringUtil
import|;
end_import

begin_class
DECL|class|MonthEditorViewModel
specifier|public
class|class
name|MonthEditorViewModel
extends|extends
name|OptionEditorViewModel
argument_list|<
name|Month
argument_list|>
block|{
DECL|field|databaseMode
specifier|private
name|BibDatabaseMode
name|databaseMode
decl_stmt|;
DECL|method|MonthEditorViewModel (Field field, AutoCompleteSuggestionProvider<?> suggestionProvider, BibDatabaseMode databaseMode, FieldCheckers fieldCheckers)
specifier|public
name|MonthEditorViewModel
parameter_list|(
name|Field
name|field
parameter_list|,
name|AutoCompleteSuggestionProvider
argument_list|<
name|?
argument_list|>
name|suggestionProvider
parameter_list|,
name|BibDatabaseMode
name|databaseMode
parameter_list|,
name|FieldCheckers
name|fieldCheckers
parameter_list|)
block|{
name|super
argument_list|(
name|field
argument_list|,
name|suggestionProvider
argument_list|,
name|fieldCheckers
argument_list|)
expr_stmt|;
name|this
operator|.
name|databaseMode
operator|=
name|databaseMode
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getStringConverter ()
specifier|public
name|StringConverter
argument_list|<
name|Month
argument_list|>
name|getStringConverter
parameter_list|()
block|{
return|return
operator|new
name|StringConverter
argument_list|<
name|Month
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|(
name|Month
name|object
parameter_list|)
block|{
if|if
condition|(
name|object
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
else|else
block|{
if|if
condition|(
name|databaseMode
operator|==
name|BibDatabaseMode
operator|.
name|BIBLATEX
condition|)
block|{
return|return
name|String
operator|.
name|valueOf
argument_list|(
name|object
operator|.
name|getNumber
argument_list|()
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|object
operator|.
name|getJabRefFormat
argument_list|()
return|;
block|}
block|}
block|}
annotation|@
name|Override
specifier|public
name|Month
name|fromString
parameter_list|(
name|String
name|string
parameter_list|)
block|{
if|if
condition|(
name|StringUtil
operator|.
name|isNotBlank
argument_list|(
name|string
argument_list|)
condition|)
block|{
return|return
name|Month
operator|.
name|parse
argument_list|(
name|string
argument_list|)
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
block|}
return|;
block|}
annotation|@
name|Override
DECL|method|getItems ()
specifier|public
name|List
argument_list|<
name|Month
argument_list|>
name|getItems
parameter_list|()
block|{
return|return
name|Arrays
operator|.
name|asList
argument_list|(
name|Month
operator|.
name|values
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|convertToDisplayText (Month object)
specifier|public
name|String
name|convertToDisplayText
parameter_list|(
name|Month
name|object
parameter_list|)
block|{
return|return
name|object
operator|.
name|getFullName
argument_list|()
return|;
block|}
block|}
end_class

end_unit

