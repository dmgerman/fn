begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.preferences
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|preferences
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
name|javafx
operator|.
name|scene
operator|.
name|Node
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|CheckBox
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|Label
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|RadioButton
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|ToggleGroup
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|layout
operator|.
name|GridPane
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|layout
operator|.
name|Pane
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
name|l10n
operator|.
name|Localization
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
DECL|class|TablePrefsTab
class|class
name|TablePrefsTab
extends|extends
name|Pane
implements|implements
name|PreferencesTab
block|{
DECL|field|prefs
specifier|private
specifier|final
name|JabRefPreferences
name|prefs
decl_stmt|;
DECL|field|autoResizeMode
specifier|private
specifier|final
name|CheckBox
name|autoResizeMode
decl_stmt|;
DECL|field|namesAsIs
specifier|private
specifier|final
name|RadioButton
name|namesAsIs
decl_stmt|;
DECL|field|namesFf
specifier|private
specifier|final
name|RadioButton
name|namesFf
decl_stmt|;
DECL|field|namesFl
specifier|private
specifier|final
name|RadioButton
name|namesFl
decl_stmt|;
DECL|field|namesNatbib
specifier|private
specifier|final
name|RadioButton
name|namesNatbib
decl_stmt|;
DECL|field|abbrNames
specifier|private
specifier|final
name|RadioButton
name|abbrNames
decl_stmt|;
DECL|field|noAbbrNames
specifier|private
specifier|final
name|RadioButton
name|noAbbrNames
decl_stmt|;
DECL|field|lastNamesOnly
specifier|private
specifier|final
name|RadioButton
name|lastNamesOnly
decl_stmt|;
DECL|field|builder
specifier|private
specifier|final
name|GridPane
name|builder
init|=
operator|new
name|GridPane
argument_list|()
decl_stmt|;
comment|/**      * Customization of external program paths.      *      * @param prefs      *            a<code>JabRefPreferences</code> value      */
DECL|method|TablePrefsTab (JabRefPreferences prefs)
specifier|public
name|TablePrefsTab
parameter_list|(
name|JabRefPreferences
name|prefs
parameter_list|)
block|{
name|this
operator|.
name|prefs
operator|=
name|prefs
expr_stmt|;
comment|/**          * Added Bibtexkey to combobox.          *          * [ 1540646 ] default sort order: bibtexkey          *          * http://sourceforge.net/tracker/index.php?func=detail&aid=1540646&group_id=92314&atid=600306          */
name|autoResizeMode
operator|=
operator|new
name|CheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Fit table horizontally on screen"
argument_list|)
argument_list|)
expr_stmt|;
name|namesAsIs
operator|=
operator|new
name|RadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Show names unchanged"
argument_list|)
argument_list|)
expr_stmt|;
name|namesFf
operator|=
operator|new
name|RadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Show 'Firstname Lastname'"
argument_list|)
argument_list|)
expr_stmt|;
name|namesFl
operator|=
operator|new
name|RadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Show 'Lastname, Firstname'"
argument_list|)
argument_list|)
expr_stmt|;
name|namesNatbib
operator|=
operator|new
name|RadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Natbib style"
argument_list|)
argument_list|)
expr_stmt|;
name|noAbbrNames
operator|=
operator|new
name|RadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Do not abbreviate names"
argument_list|)
argument_list|)
expr_stmt|;
name|abbrNames
operator|=
operator|new
name|RadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Abbreviate names"
argument_list|)
argument_list|)
expr_stmt|;
name|lastNamesOnly
operator|=
operator|new
name|RadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Show last names only"
argument_list|)
argument_list|)
expr_stmt|;
name|Label
name|formatOfAuthor
init|=
operator|new
name|Label
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Format of author and editor names"
argument_list|)
argument_list|)
decl_stmt|;
name|formatOfAuthor
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"sectionHeader"
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|formatOfAuthor
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|)
expr_stmt|;
specifier|final
name|ToggleGroup
name|formatNamesToggleGroup
init|=
operator|new
name|ToggleGroup
argument_list|()
decl_stmt|;
specifier|final
name|ToggleGroup
name|nameAbbrevToggleGroup
init|=
operator|new
name|ToggleGroup
argument_list|()
decl_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|namesAsIs
argument_list|,
literal|1
argument_list|,
literal|2
argument_list|)
expr_stmt|;
name|namesAsIs
operator|.
name|setToggleGroup
argument_list|(
name|formatNamesToggleGroup
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|noAbbrNames
argument_list|,
literal|2
argument_list|,
literal|2
argument_list|)
expr_stmt|;
name|noAbbrNames
operator|.
name|setToggleGroup
argument_list|(
name|nameAbbrevToggleGroup
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|namesFf
argument_list|,
literal|1
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|namesFf
operator|.
name|setToggleGroup
argument_list|(
name|formatNamesToggleGroup
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|abbrNames
argument_list|,
literal|2
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|abbrNames
operator|.
name|setToggleGroup
argument_list|(
name|nameAbbrevToggleGroup
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|namesFl
argument_list|,
literal|1
argument_list|,
literal|4
argument_list|)
expr_stmt|;
name|namesFl
operator|.
name|setToggleGroup
argument_list|(
name|formatNamesToggleGroup
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|lastNamesOnly
argument_list|,
literal|2
argument_list|,
literal|4
argument_list|)
expr_stmt|;
name|lastNamesOnly
operator|.
name|setToggleGroup
argument_list|(
name|nameAbbrevToggleGroup
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|namesNatbib
argument_list|,
literal|1
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|namesNatbib
operator|.
name|setToggleGroup
argument_list|(
name|formatNamesToggleGroup
argument_list|)
expr_stmt|;
name|Label
name|label1
init|=
operator|new
name|Label
argument_list|(
literal|""
argument_list|)
decl_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|label1
argument_list|,
literal|1
argument_list|,
literal|6
argument_list|)
expr_stmt|;
name|Label
name|general
init|=
operator|new
name|Label
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"General"
argument_list|)
argument_list|)
decl_stmt|;
name|general
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"sectionHeader"
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|general
argument_list|,
literal|1
argument_list|,
literal|7
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|autoResizeMode
argument_list|,
literal|1
argument_list|,
literal|8
argument_list|)
expr_stmt|;
name|abbrNames
operator|.
name|disableProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|namesNatbib
operator|.
name|selectedProperty
argument_list|()
argument_list|)
expr_stmt|;
name|lastNamesOnly
operator|.
name|disableProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|namesNatbib
operator|.
name|selectedProperty
argument_list|()
argument_list|)
expr_stmt|;
name|noAbbrNames
operator|.
name|disableProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|namesNatbib
operator|.
name|selectedProperty
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getBuilder ()
specifier|public
name|Node
name|getBuilder
parameter_list|()
block|{
return|return
name|builder
return|;
block|}
annotation|@
name|Override
DECL|method|setValues ()
specifier|public
name|void
name|setValues
parameter_list|()
block|{
name|autoResizeMode
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|AUTO_RESIZE_MODE
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|NAMES_AS_IS
argument_list|)
condition|)
block|{
name|namesAsIs
operator|.
name|setSelected
argument_list|(
literal|true
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
name|JabRefPreferences
operator|.
name|NAMES_FIRST_LAST
argument_list|)
condition|)
block|{
name|namesFf
operator|.
name|setSelected
argument_list|(
literal|true
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
name|JabRefPreferences
operator|.
name|NAMES_NATBIB
argument_list|)
condition|)
block|{
name|namesNatbib
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|namesFl
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|ABBR_AUTHOR_NAMES
argument_list|)
condition|)
block|{
name|abbrNames
operator|.
name|setSelected
argument_list|(
literal|true
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
name|JabRefPreferences
operator|.
name|NAMES_LAST_ONLY
argument_list|)
condition|)
block|{
name|lastNamesOnly
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|noAbbrNames
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Store changes to table preferences. This method is called when the user      * clicks Ok.      *      */
annotation|@
name|Override
DECL|method|storeSettings ()
specifier|public
name|void
name|storeSettings
parameter_list|()
block|{
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|NAMES_AS_IS
argument_list|,
name|namesAsIs
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|NAMES_FIRST_LAST
argument_list|,
name|namesFf
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|NAMES_NATBIB
argument_list|,
name|namesNatbib
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|NAMES_LAST_ONLY
argument_list|,
name|lastNamesOnly
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|ABBR_AUTHOR_NAMES
argument_list|,
name|abbrNames
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|AUTO_RESIZE_MODE
argument_list|,
name|autoResizeMode
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|validateSettings ()
specifier|public
name|boolean
name|validateSettings
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
annotation|@
name|Override
DECL|method|getTabName ()
specifier|public
name|String
name|getTabName
parameter_list|()
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Entry table"
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getRestartWarnings ()
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getRestartWarnings
parameter_list|()
block|{
return|return
operator|new
name|ArrayList
argument_list|<>
argument_list|()
return|;
block|}
block|}
end_class

end_unit

