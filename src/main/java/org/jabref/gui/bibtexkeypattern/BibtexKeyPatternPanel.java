begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.bibtexkeypattern
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|bibtexkeypattern
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|GridBagConstraints
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|GridBagLayout
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
name|Locale
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
name|Button
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
name|TextField
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
name|Globals
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
name|BasePanel
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
name|help
operator|.
name|HelpAction
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
name|help
operator|.
name|HelpFile
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
name|model
operator|.
name|EntryTypes
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
name|bibtexkeypattern
operator|.
name|AbstractBibtexKeyPattern
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
name|bibtexkeypattern
operator|.
name|DatabaseBibtexKeyPattern
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
name|bibtexkeypattern
operator|.
name|GlobalBibtexKeyPattern
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
name|EntryType
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
DECL|class|BibtexKeyPatternPanel
specifier|public
class|class
name|BibtexKeyPatternPanel
extends|extends
name|Pane
block|{
comment|// used by both BibtexKeyPatternPanel and TabLabelPAttern
DECL|field|gbl
specifier|protected
specifier|final
name|GridBagLayout
name|gbl
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
DECL|field|con
specifier|protected
specifier|final
name|GridBagConstraints
name|con
init|=
operator|new
name|GridBagConstraints
argument_list|()
decl_stmt|;
comment|// default pattern
DECL|field|defaultPat
specifier|protected
specifier|final
name|TextField
name|defaultPat
init|=
operator|new
name|TextField
argument_list|()
decl_stmt|;
DECL|field|help
specifier|private
specifier|final
name|HelpAction
name|help
decl_stmt|;
DECL|field|COLUMNS
specifier|private
specifier|final
name|int
name|COLUMNS
init|=
literal|2
decl_stmt|;
comment|// one field for each type
DECL|field|textFields
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|TextField
argument_list|>
name|textFields
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|panel
specifier|private
specifier|final
name|BasePanel
name|panel
decl_stmt|;
DECL|field|gridPane
specifier|private
specifier|final
name|GridPane
name|gridPane
init|=
operator|new
name|GridPane
argument_list|()
decl_stmt|;
DECL|method|BibtexKeyPatternPanel (BasePanel panel)
specifier|public
name|BibtexKeyPatternPanel
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
name|help
operator|=
operator|new
name|HelpAction
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Help on key patterns"
argument_list|)
argument_list|,
name|HelpFile
operator|.
name|BIBTEX_KEY_PATTERN
argument_list|)
expr_stmt|;
name|gridPane
operator|.
name|setHgap
argument_list|(
literal|10
argument_list|)
expr_stmt|;
name|gridPane
operator|.
name|setVgap
argument_list|(
literal|5
argument_list|)
expr_stmt|;
name|buildGUI
argument_list|()
expr_stmt|;
block|}
DECL|method|buildGUI ()
specifier|private
name|void
name|buildGUI
parameter_list|()
block|{
name|BibDatabaseMode
name|mode
decl_stmt|;
comment|// check mode of currently used DB
if|if
condition|(
name|panel
operator|!=
literal|null
condition|)
block|{
name|mode
operator|=
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getMode
argument_list|()
expr_stmt|;
block|}
else|else
block|{
comment|// use preferences value if no DB is open
name|mode
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|getDefaultBibDatabaseMode
argument_list|()
expr_stmt|;
block|}
name|int
name|rowIndex
init|=
literal|1
decl_stmt|;
name|int
name|columnIndex
init|=
literal|0
decl_stmt|;
comment|// The header - can be removed
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|COLUMNS
condition|;
name|i
operator|++
control|)
block|{
name|Label
name|label
init|=
operator|new
name|Label
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Entry type"
argument_list|)
argument_list|)
decl_stmt|;
name|Label
name|keyPattern
init|=
operator|new
name|Label
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Key pattern"
argument_list|)
argument_list|)
decl_stmt|;
name|gridPane
operator|.
name|add
argument_list|(
name|label
argument_list|,
operator|++
name|columnIndex
argument_list|,
name|rowIndex
argument_list|)
expr_stmt|;
name|gridPane
operator|.
name|add
argument_list|(
name|keyPattern
argument_list|,
operator|++
name|columnIndex
argument_list|,
name|rowIndex
argument_list|)
expr_stmt|;
operator|++
name|columnIndex
expr_stmt|;
comment|//3
block|}
name|rowIndex
operator|++
expr_stmt|;
name|Label
name|defaultPattern
init|=
operator|new
name|Label
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Default pattern"
argument_list|)
argument_list|)
decl_stmt|;
name|Button
name|button
init|=
operator|new
name|Button
argument_list|(
literal|"Default"
argument_list|)
decl_stmt|;
name|button
operator|.
name|setOnAction
argument_list|(
name|e
lambda|->
name|defaultPat
operator|.
name|setText
argument_list|(
operator|(
name|String
operator|)
name|Globals
operator|.
name|prefs
operator|.
name|defaults
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|DEFAULT_BIBTEX_KEY_PATTERN
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|gridPane
operator|.
name|add
argument_list|(
name|defaultPattern
argument_list|,
literal|1
argument_list|,
name|rowIndex
argument_list|)
expr_stmt|;
name|gridPane
operator|.
name|add
argument_list|(
name|defaultPat
argument_list|,
literal|2
argument_list|,
name|rowIndex
argument_list|)
expr_stmt|;
name|gridPane
operator|.
name|add
argument_list|(
name|button
argument_list|,
literal|3
argument_list|,
name|rowIndex
argument_list|)
expr_stmt|;
name|columnIndex
operator|=
literal|1
expr_stmt|;
for|for
control|(
name|EntryType
name|type
range|:
name|EntryTypes
operator|.
name|getAllValues
argument_list|(
name|mode
argument_list|)
control|)
block|{
name|Label
name|label1
init|=
operator|new
name|Label
argument_list|(
name|type
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|TextField
name|textField
init|=
operator|new
name|TextField
argument_list|()
decl_stmt|;
name|Button
name|button1
init|=
operator|new
name|Button
argument_list|(
literal|"Default"
argument_list|)
decl_stmt|;
name|button1
operator|.
name|setOnAction
argument_list|(
name|e1
lambda|->
name|textField
operator|.
name|setText
argument_list|(
operator|(
name|String
operator|)
name|Globals
operator|.
name|prefs
operator|.
name|defaults
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|DEFAULT_BIBTEX_KEY_PATTERN
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|gridPane
operator|.
name|add
argument_list|(
name|label1
argument_list|,
literal|1
operator|+
operator|(
name|columnIndex
operator|*
literal|3
operator|)
argument_list|,
name|rowIndex
argument_list|)
expr_stmt|;
name|gridPane
operator|.
name|add
argument_list|(
name|textField
argument_list|,
literal|2
operator|+
operator|(
name|columnIndex
operator|*
literal|3
operator|)
argument_list|,
name|rowIndex
argument_list|)
expr_stmt|;
name|gridPane
operator|.
name|add
argument_list|(
name|button1
argument_list|,
literal|3
operator|+
operator|(
name|columnIndex
operator|*
literal|3
operator|)
argument_list|,
name|rowIndex
argument_list|)
expr_stmt|;
name|textFields
operator|.
name|put
argument_list|(
name|type
operator|.
name|getName
argument_list|()
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|ROOT
argument_list|)
argument_list|,
name|textField
argument_list|)
expr_stmt|;
if|if
condition|(
name|columnIndex
operator|==
name|COLUMNS
operator|-
literal|1
condition|)
block|{
name|columnIndex
operator|=
literal|0
expr_stmt|;
name|rowIndex
operator|++
expr_stmt|;
block|}
else|else
block|{
name|columnIndex
operator|++
expr_stmt|;
block|}
block|}
name|rowIndex
operator|++
expr_stmt|;
name|Button
name|help1
init|=
operator|new
name|Button
argument_list|(
literal|"?"
argument_list|)
decl_stmt|;
name|help1
operator|.
name|setOnAction
argument_list|(
name|e
lambda|->
operator|new
name|HelpAction
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Help on key patterns"
argument_list|)
argument_list|,
name|HelpFile
operator|.
name|BIBTEX_KEY_PATTERN
argument_list|)
operator|.
name|getHelpButton
argument_list|()
operator|.
name|doClick
argument_list|()
argument_list|)
expr_stmt|;
name|gridPane
operator|.
name|add
argument_list|(
name|help1
argument_list|,
literal|1
argument_list|,
name|rowIndex
argument_list|)
expr_stmt|;
name|Button
name|btnDefaultAll1
init|=
operator|new
name|Button
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Reset all"
argument_list|)
argument_list|)
decl_stmt|;
name|btnDefaultAll1
operator|.
name|setOnAction
argument_list|(
name|e
lambda|->
block|{
comment|// reset all fields
for|for
control|(
name|TextField
name|field
range|:
name|textFields
operator|.
name|values
argument_list|()
control|)
block|{
name|field
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
name|defaultPat
operator|.
name|setText
argument_list|(
operator|(
name|String
operator|)
name|Globals
operator|.
name|prefs
operator|.
name|defaults
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|DEFAULT_BIBTEX_KEY_PATTERN
argument_list|)
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|gridPane
operator|.
name|add
argument_list|(
name|btnDefaultAll1
argument_list|,
literal|2
argument_list|,
name|rowIndex
argument_list|)
expr_stmt|;
block|}
comment|/**      * fill the given LabelPattern by values generated from the text fields      */
DECL|method|fillPatternUsingPanelData (AbstractBibtexKeyPattern keypatterns)
specifier|private
name|void
name|fillPatternUsingPanelData
parameter_list|(
name|AbstractBibtexKeyPattern
name|keypatterns
parameter_list|)
block|{
comment|// each entry type
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|TextField
argument_list|>
name|entry
range|:
name|textFields
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|String
name|text
init|=
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|getText
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|text
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|keypatterns
operator|.
name|addBibtexKeyPattern
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|,
name|text
argument_list|)
expr_stmt|;
block|}
block|}
comment|// default value
name|String
name|text
init|=
name|defaultPat
operator|.
name|getText
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|text
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// we do not trim the value at the assignment to enable users to have spaces at the beginning and at the end of the pattern
name|keypatterns
operator|.
name|setDefaultValue
argument_list|(
name|text
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|getKeyPatternAsGlobalBibtexKeyPattern ()
specifier|protected
name|GlobalBibtexKeyPattern
name|getKeyPatternAsGlobalBibtexKeyPattern
parameter_list|()
block|{
name|GlobalBibtexKeyPattern
name|res
init|=
name|GlobalBibtexKeyPattern
operator|.
name|fromPattern
argument_list|(
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|DEFAULT_BIBTEX_KEY_PATTERN
argument_list|)
argument_list|)
decl_stmt|;
name|fillPatternUsingPanelData
argument_list|(
name|res
argument_list|)
expr_stmt|;
return|return
name|res
return|;
block|}
DECL|method|getKeyPatternAsDatabaseBibtexKeyPattern ()
specifier|public
name|DatabaseBibtexKeyPattern
name|getKeyPatternAsDatabaseBibtexKeyPattern
parameter_list|()
block|{
name|DatabaseBibtexKeyPattern
name|res
init|=
operator|new
name|DatabaseBibtexKeyPattern
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getKeyPattern
argument_list|()
argument_list|)
decl_stmt|;
name|fillPatternUsingPanelData
argument_list|(
name|res
argument_list|)
expr_stmt|;
return|return
name|res
return|;
block|}
comment|/**      * Fills the current values to the text fields      *      * @param keyPattern the BibtexKeyPattern to use as initial value      */
DECL|method|setValues (AbstractBibtexKeyPattern keyPattern)
specifier|public
name|void
name|setValues
parameter_list|(
name|AbstractBibtexKeyPattern
name|keyPattern
parameter_list|)
block|{
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|TextField
argument_list|>
name|entry
range|:
name|textFields
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|setValue
argument_list|(
name|entry
operator|.
name|getValue
argument_list|()
argument_list|,
name|entry
operator|.
name|getKey
argument_list|()
argument_list|,
name|keyPattern
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|keyPattern
operator|.
name|getDefaultValue
argument_list|()
operator|==
literal|null
operator|||
name|keyPattern
operator|.
name|getDefaultValue
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|defaultPat
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|defaultPat
operator|.
name|setText
argument_list|(
name|keyPattern
operator|.
name|getDefaultValue
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|setValue (TextField tf, String fieldName, AbstractBibtexKeyPattern keyPattern)
specifier|private
specifier|static
name|void
name|setValue
parameter_list|(
name|TextField
name|tf
parameter_list|,
name|String
name|fieldName
parameter_list|,
name|AbstractBibtexKeyPattern
name|keyPattern
parameter_list|)
block|{
if|if
condition|(
name|keyPattern
operator|.
name|isDefaultValue
argument_list|(
name|fieldName
argument_list|)
condition|)
block|{
name|tf
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|tf
operator|.
name|setText
argument_list|(
name|keyPattern
operator|.
name|getValue
argument_list|(
name|fieldName
argument_list|)
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|getPanel ()
specifier|public
name|Node
name|getPanel
parameter_list|()
block|{
return|return
name|gridPane
return|;
block|}
block|}
end_class

end_unit

