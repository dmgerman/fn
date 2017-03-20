begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.maintable
package|package
name|org
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
name|org
operator|.
name|jabref
operator|.
name|Globals
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
name|AuthorList
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_class
DECL|class|MainTableNameFormatter
specifier|public
class|class
name|MainTableNameFormatter
block|{
DECL|method|MainTableNameFormatter ()
specifier|private
name|MainTableNameFormatter
parameter_list|()
block|{}
comment|/**      * Format a name field for the table, according to user preferences.      *      * @param nameToFormat The contents of the name field.      * @return The formatted name field.      */
DECL|method|formatName (final String nameToFormat)
specifier|public
specifier|static
name|String
name|formatName
parameter_list|(
specifier|final
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
comment|// Read name format options:
specifier|final
name|boolean
name|namesNatbib
init|=
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
decl_stmt|;
comment|//MK:
specifier|final
name|boolean
name|namesLastOnly
init|=
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
decl_stmt|;
specifier|final
name|boolean
name|namesAsIs
init|=
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
decl_stmt|;
specifier|final
name|boolean
name|namesFf
init|=
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
decl_stmt|;
specifier|final
name|boolean
name|abbrAuthorNames
init|=
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
decl_stmt|;
comment|//MK:
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
return|return
name|AuthorList
operator|.
name|fixAuthorNatbib
argument_list|(
name|nameToFormat
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|namesLastOnly
condition|)
block|{
return|return
name|AuthorList
operator|.
name|fixAuthorLastNameOnlyCommas
argument_list|(
name|nameToFormat
argument_list|,
literal|false
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|namesFf
condition|)
block|{
return|return
name|AuthorList
operator|.
name|fixAuthorFirstNameFirstCommas
argument_list|(
name|nameToFormat
argument_list|,
name|abbrAuthorNames
argument_list|,
literal|false
argument_list|)
return|;
block|}
comment|// None of namesAsIs, namesNatbib, namesAsIs, namesFf
return|return
name|AuthorList
operator|.
name|fixAuthorLastNameFirstCommas
argument_list|(
name|nameToFormat
argument_list|,
name|abbrAuthorNames
argument_list|,
literal|false
argument_list|)
return|;
block|}
block|}
end_class

end_unit

