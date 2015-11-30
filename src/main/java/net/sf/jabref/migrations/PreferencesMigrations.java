begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.migrations
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|migrations
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
name|Map
import|;
end_import

begin_class
DECL|class|PreferencesMigrations
specifier|public
class|class
name|PreferencesMigrations
block|{
comment|/**      * This method is called at startup, and makes necessary adaptations to      * preferences for users from an earlier version of Jabref.      */
DECL|method|replaceAbstractField ()
specifier|public
specifier|static
name|void
name|replaceAbstractField
parameter_list|()
block|{
comment|// Make sure "abstract" is not in General fields, because
comment|// Jabref 1.55 moves the abstract to its own tab.
name|String
name|genFields
init|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|GENERAL_FIELDS
argument_list|)
decl_stmt|;
if|if
condition|(
name|genFields
operator|.
name|contains
argument_list|(
literal|"abstract"
argument_list|)
condition|)
block|{
name|String
name|newGen
decl_stmt|;
if|if
condition|(
literal|"abstract"
operator|.
name|equals
argument_list|(
name|genFields
argument_list|)
condition|)
block|{
name|newGen
operator|=
literal|""
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|genFields
operator|.
name|contains
argument_list|(
literal|";abstract;"
argument_list|)
condition|)
block|{
name|newGen
operator|=
name|genFields
operator|.
name|replaceAll
argument_list|(
literal|";abstract;"
argument_list|,
literal|";"
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|genFields
operator|.
name|indexOf
argument_list|(
literal|"abstract;"
argument_list|)
operator|==
literal|0
condition|)
block|{
name|newGen
operator|=
name|genFields
operator|.
name|replaceAll
argument_list|(
literal|"abstract;"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|genFields
operator|.
name|indexOf
argument_list|(
literal|";abstract"
argument_list|)
operator|==
operator|(
name|genFields
operator|.
name|length
argument_list|()
operator|-
literal|9
operator|)
condition|)
block|{
name|newGen
operator|=
name|genFields
operator|.
name|replaceAll
argument_list|(
literal|";abstract"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|newGen
operator|=
name|genFields
expr_stmt|;
block|}
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|GENERAL_FIELDS
argument_list|,
name|newGen
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Added from Jabref 2.11 beta 4 onwards to fix wrong encoding names      */
DECL|method|upgradeFaultyEncodingStrings ()
specifier|public
specifier|static
name|void
name|upgradeFaultyEncodingStrings
parameter_list|()
block|{
name|JabRefPreferences
name|prefs
init|=
name|Globals
operator|.
name|prefs
decl_stmt|;
name|String
name|defaultEncoding
init|=
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|DEFAULT_ENCODING
argument_list|)
decl_stmt|;
if|if
condition|(
name|defaultEncoding
operator|==
literal|null
condition|)
block|{
return|return;
block|}
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|encodingMap
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
name|encodingMap
operator|.
name|put
argument_list|(
literal|"UTF8"
argument_list|,
literal|"UTF-8"
argument_list|)
expr_stmt|;
name|encodingMap
operator|.
name|put
argument_list|(
literal|"Cp1250"
argument_list|,
literal|"CP1250"
argument_list|)
expr_stmt|;
name|encodingMap
operator|.
name|put
argument_list|(
literal|"Cp1251"
argument_list|,
literal|"CP1251"
argument_list|)
expr_stmt|;
name|encodingMap
operator|.
name|put
argument_list|(
literal|"Cp1252"
argument_list|,
literal|"CP1252"
argument_list|)
expr_stmt|;
name|encodingMap
operator|.
name|put
argument_list|(
literal|"Cp1253"
argument_list|,
literal|"CP1253"
argument_list|)
expr_stmt|;
name|encodingMap
operator|.
name|put
argument_list|(
literal|"Cp1254"
argument_list|,
literal|"CP1254"
argument_list|)
expr_stmt|;
name|encodingMap
operator|.
name|put
argument_list|(
literal|"Cp1257"
argument_list|,
literal|"CP1257"
argument_list|)
expr_stmt|;
name|encodingMap
operator|.
name|put
argument_list|(
literal|"ISO8859_1"
argument_list|,
literal|"ISO8859-1"
argument_list|)
expr_stmt|;
name|encodingMap
operator|.
name|put
argument_list|(
literal|"ISO8859_2"
argument_list|,
literal|"ISO8859-2"
argument_list|)
expr_stmt|;
name|encodingMap
operator|.
name|put
argument_list|(
literal|"ISO8859_3"
argument_list|,
literal|"ISO8859-3"
argument_list|)
expr_stmt|;
name|encodingMap
operator|.
name|put
argument_list|(
literal|"ISO8859_4"
argument_list|,
literal|"ISO8859-4"
argument_list|)
expr_stmt|;
name|encodingMap
operator|.
name|put
argument_list|(
literal|"ISO8859_5"
argument_list|,
literal|"ISO8859-5"
argument_list|)
expr_stmt|;
name|encodingMap
operator|.
name|put
argument_list|(
literal|"ISO8859_6"
argument_list|,
literal|"ISO8859-6"
argument_list|)
expr_stmt|;
name|encodingMap
operator|.
name|put
argument_list|(
literal|"ISO8859_7"
argument_list|,
literal|"ISO8859-7"
argument_list|)
expr_stmt|;
name|encodingMap
operator|.
name|put
argument_list|(
literal|"ISO8859_8"
argument_list|,
literal|"ISO8859-8"
argument_list|)
expr_stmt|;
name|encodingMap
operator|.
name|put
argument_list|(
literal|"ISO8859_9"
argument_list|,
literal|"ISO8859-9"
argument_list|)
expr_stmt|;
name|encodingMap
operator|.
name|put
argument_list|(
literal|"ISO8859_13"
argument_list|,
literal|"ISO8859-13"
argument_list|)
expr_stmt|;
name|encodingMap
operator|.
name|put
argument_list|(
literal|"ISO8859_15"
argument_list|,
literal|"ISO8859-15"
argument_list|)
expr_stmt|;
name|encodingMap
operator|.
name|put
argument_list|(
literal|"KOI8_R"
argument_list|,
literal|"KOI8-R"
argument_list|)
expr_stmt|;
name|encodingMap
operator|.
name|put
argument_list|(
literal|"Big5_HKSCS"
argument_list|,
literal|"Big5-HKSCS"
argument_list|)
expr_stmt|;
name|encodingMap
operator|.
name|put
argument_list|(
literal|"EUC_JP"
argument_list|,
literal|"EUC-JP"
argument_list|)
expr_stmt|;
if|if
condition|(
name|encodingMap
operator|.
name|get
argument_list|(
name|defaultEncoding
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|DEFAULT_ENCODING
argument_list|,
name|encodingMap
operator|.
name|get
argument_list|(
name|defaultEncoding
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Upgrade the sort order preferences for the current version      * The old preference is kept in case an old version of JabRef is used with      * these preferences, but it is only used when the new preference does not      * exist      */
DECL|method|upgradeSortOrder ()
specifier|public
specifier|static
name|void
name|upgradeSortOrder
parameter_list|()
block|{
name|JabRefPreferences
name|prefs
init|=
name|Globals
operator|.
name|prefs
decl_stmt|;
if|if
condition|(
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|SAVE_IN_SPECIFIED_ORDER
argument_list|,
literal|null
argument_list|)
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"saveInStandardOrder"
argument_list|,
literal|false
argument_list|)
condition|)
block|{
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|SAVE_IN_SPECIFIED_ORDER
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|SAVE_PRIMARY_SORT_FIELD
argument_list|,
literal|"author"
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|SAVE_SECONDARY_SORT_FIELD
argument_list|,
literal|"editor"
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|SAVE_TERTIARY_SORT_FIELD
argument_list|,
literal|"year"
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|SAVE_PRIMARY_SORT_DESCENDING
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|SAVE_SECONDARY_SORT_DESCENDING
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|SAVE_TERTIARY_SORT_DESCENDING
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"saveInTitleOrder"
argument_list|,
literal|false
argument_list|)
condition|)
block|{
comment|// saveInTitleOrder => title, author, editor
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|SAVE_IN_SPECIFIED_ORDER
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|SAVE_PRIMARY_SORT_FIELD
argument_list|,
literal|"title"
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|SAVE_SECONDARY_SORT_FIELD
argument_list|,
literal|"author"
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|SAVE_TERTIARY_SORT_FIELD
argument_list|,
literal|"editor"
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|SAVE_PRIMARY_SORT_DESCENDING
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|SAVE_SECONDARY_SORT_DESCENDING
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|SAVE_TERTIARY_SORT_DESCENDING
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_IN_SPECIFIED_ORDER
argument_list|,
literal|null
argument_list|)
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"exportInStandardOrder"
argument_list|,
literal|false
argument_list|)
condition|)
block|{
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_IN_SPECIFIED_ORDER
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_PRIMARY_SORT_FIELD
argument_list|,
literal|"author"
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_SECONDARY_SORT_FIELD
argument_list|,
literal|"editor"
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_TERTIARY_SORT_FIELD
argument_list|,
literal|"year"
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_PRIMARY_SORT_DESCENDING
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_SECONDARY_SORT_DESCENDING
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_TERTIARY_SORT_DESCENDING
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"exportInTitleOrder"
argument_list|,
literal|false
argument_list|)
condition|)
block|{
comment|// exportInTitleOrder => title, author, editor
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_IN_SPECIFIED_ORDER
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_PRIMARY_SORT_FIELD
argument_list|,
literal|"title"
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_SECONDARY_SORT_FIELD
argument_list|,
literal|"author"
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_TERTIARY_SORT_FIELD
argument_list|,
literal|"editor"
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_PRIMARY_SORT_DESCENDING
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_SECONDARY_SORT_DESCENDING
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_TERTIARY_SORT_DESCENDING
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

