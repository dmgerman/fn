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
name|nio
operator|.
name|charset
operator|.
name|Charset
import|;
end_import

begin_import
import|import
name|java
operator|.
name|time
operator|.
name|format
operator|.
name|DateTimeFormatter
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|collections
operator|.
name|FXCollections
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|collections
operator|.
name|ObservableList
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
name|ComboBox
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
name|javafx
operator|.
name|scene
operator|.
name|shape
operator|.
name|Line
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
name|DialogService
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
name|gui
operator|.
name|util
operator|.
name|DefaultTaskExecutor
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
name|Encodings
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
name|InternalBibtexFields
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

begin_import
import|import static
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|l10n
operator|.
name|Languages
operator|.
name|LANGUAGES
import|;
end_import

begin_class
DECL|class|GeneralTab
class|class
name|GeneralTab
extends|extends
name|Pane
implements|implements
name|PrefsTab
block|{
DECL|field|useOwner
specifier|private
specifier|final
name|CheckBox
name|useOwner
decl_stmt|;
DECL|field|overwriteOwner
specifier|private
specifier|final
name|CheckBox
name|overwriteOwner
decl_stmt|;
DECL|field|enforceLegalKeys
specifier|private
specifier|final
name|CheckBox
name|enforceLegalKeys
decl_stmt|;
DECL|field|shouldCollectTelemetry
specifier|private
specifier|final
name|CheckBox
name|shouldCollectTelemetry
decl_stmt|;
DECL|field|confirmDelete
specifier|private
specifier|final
name|CheckBox
name|confirmDelete
decl_stmt|;
DECL|field|memoryStick
specifier|private
specifier|final
name|CheckBox
name|memoryStick
decl_stmt|;
DECL|field|inspectionWarnDupli
specifier|private
specifier|final
name|CheckBox
name|inspectionWarnDupli
decl_stmt|;
DECL|field|useTimeStamp
specifier|private
specifier|final
name|CheckBox
name|useTimeStamp
decl_stmt|;
DECL|field|updateTimeStamp
specifier|private
specifier|final
name|CheckBox
name|updateTimeStamp
decl_stmt|;
DECL|field|overwriteTimeStamp
specifier|private
specifier|final
name|CheckBox
name|overwriteTimeStamp
decl_stmt|;
DECL|field|defOwnerField
specifier|private
specifier|final
name|TextField
name|defOwnerField
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
DECL|field|timeStampFormat
specifier|private
specifier|final
name|TextField
name|timeStampFormat
decl_stmt|;
DECL|field|timeStampField
specifier|private
specifier|final
name|TextField
name|timeStampField
decl_stmt|;
DECL|field|prefs
specifier|private
specifier|final
name|JabRefPreferences
name|prefs
decl_stmt|;
DECL|field|options
specifier|private
specifier|final
name|ObservableList
argument_list|<
name|String
argument_list|>
name|options
init|=
name|FXCollections
operator|.
name|observableArrayList
argument_list|(
name|LANGUAGES
operator|.
name|keySet
argument_list|()
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|LANGUAGES
operator|.
name|keySet
argument_list|()
operator|.
name|size
argument_list|()
index|]
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|language
specifier|private
specifier|final
name|ComboBox
argument_list|<
name|String
argument_list|>
name|language
init|=
operator|new
name|ComboBox
argument_list|<>
argument_list|(
name|options
argument_list|)
decl_stmt|;
DECL|field|encodings
specifier|private
specifier|final
name|ComboBox
argument_list|<
name|Charset
argument_list|>
name|encodings
decl_stmt|;
DECL|field|biblatexMode
specifier|private
specifier|final
name|ComboBox
argument_list|<
name|BibDatabaseMode
argument_list|>
name|biblatexMode
decl_stmt|;
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
decl_stmt|;
DECL|method|GeneralTab (DialogService dialogService, JabRefPreferences prefs)
specifier|public
name|GeneralTab
parameter_list|(
name|DialogService
name|dialogService
parameter_list|,
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
name|this
operator|.
name|dialogService
operator|=
name|dialogService
expr_stmt|;
name|biblatexMode
operator|=
operator|new
name|ComboBox
argument_list|<>
argument_list|(
name|FXCollections
operator|.
name|observableArrayList
argument_list|(
name|BibDatabaseMode
operator|.
name|values
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|memoryStick
operator|=
operator|new
name|CheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Load and Save preferences from/to jabref.xml on start-up (memory stick mode)"
argument_list|)
argument_list|)
expr_stmt|;
name|useOwner
operator|=
operator|new
name|CheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Mark new entries with owner name"
argument_list|)
operator|+
literal|':'
argument_list|)
expr_stmt|;
name|updateTimeStamp
operator|=
operator|new
name|CheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Update timestamp on modification"
argument_list|)
argument_list|)
expr_stmt|;
name|useTimeStamp
operator|=
operator|new
name|CheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Mark new entries with addition date"
argument_list|)
operator|+
literal|". "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Date format"
argument_list|)
operator|+
literal|':'
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|useTimeStamp
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|updateTimeStamp
operator|.
name|setDisable
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
name|useTimeStamp
operator|.
name|setOnAction
argument_list|(
name|e
lambda|->
name|updateTimeStamp
operator|.
name|setDisable
argument_list|(
operator|!
name|useTimeStamp
operator|.
name|isSelected
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|overwriteOwner
operator|=
operator|new
name|CheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Overwrite"
argument_list|)
argument_list|)
expr_stmt|;
name|overwriteTimeStamp
operator|=
operator|new
name|CheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"If a pasted or imported entry already has the field set, overwrite."
argument_list|)
argument_list|)
expr_stmt|;
name|enforceLegalKeys
operator|=
operator|new
name|CheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Enforce legal characters in BibTeX keys"
argument_list|)
argument_list|)
expr_stmt|;
name|confirmDelete
operator|=
operator|new
name|CheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Show confirmation dialog when deleting entries"
argument_list|)
argument_list|)
expr_stmt|;
name|defOwnerField
operator|=
operator|new
name|TextField
argument_list|()
expr_stmt|;
name|defOwnerField
operator|.
name|setPrefSize
argument_list|(
literal|80
argument_list|,
literal|20
argument_list|)
expr_stmt|;
name|timeStampFormat
operator|=
operator|new
name|TextField
argument_list|()
expr_stmt|;
name|timeStampFormat
operator|.
name|setPrefSize
argument_list|(
literal|80
argument_list|,
literal|20
argument_list|)
expr_stmt|;
name|timeStampField
operator|=
operator|new
name|TextField
argument_list|()
expr_stmt|;
name|timeStampField
operator|.
name|setPrefSize
argument_list|(
literal|80
argument_list|,
literal|20
argument_list|)
expr_stmt|;
name|inspectionWarnDupli
operator|=
operator|new
name|CheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Warn about unresolved duplicates when closing inspection window"
argument_list|)
argument_list|)
expr_stmt|;
name|shouldCollectTelemetry
operator|=
operator|new
name|CheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Collect and share telemetry data to help improve JabRef."
argument_list|)
argument_list|)
expr_stmt|;
name|encodings
operator|=
operator|new
name|ComboBox
argument_list|<>
argument_list|(
name|FXCollections
operator|.
name|observableArrayList
argument_list|(
name|Encodings
operator|.
name|ENCODINGS
argument_list|)
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
literal|1
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
operator|new
name|Line
argument_list|()
argument_list|,
literal|1
argument_list|,
literal|2
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|inspectionWarnDupli
argument_list|,
literal|1
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
operator|new
name|Line
argument_list|()
argument_list|,
literal|1
argument_list|,
literal|4
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|confirmDelete
argument_list|,
literal|1
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
operator|new
name|Line
argument_list|()
argument_list|,
literal|1
argument_list|,
literal|6
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|enforceLegalKeys
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
operator|new
name|Line
argument_list|()
argument_list|,
literal|1
argument_list|,
literal|8
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|memoryStick
argument_list|,
literal|1
argument_list|,
literal|9
argument_list|)
expr_stmt|;
comment|// Create a new panel with its own FormLayout for the last items:
name|builder
operator|.
name|add
argument_list|(
name|useOwner
argument_list|,
literal|1
argument_list|,
literal|10
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|defOwnerField
argument_list|,
literal|2
argument_list|,
literal|10
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|overwriteOwner
argument_list|,
literal|3
argument_list|,
literal|10
argument_list|)
expr_stmt|;
name|Button
name|help
init|=
operator|new
name|Button
argument_list|(
literal|"?"
argument_list|)
decl_stmt|;
name|help
operator|.
name|setPrefSize
argument_list|(
literal|10
argument_list|,
literal|10
argument_list|)
expr_stmt|;
name|help
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
operator|new
name|HelpAction
argument_list|(
name|HelpFile
operator|.
name|OWNER
argument_list|)
operator|.
name|getHelpButton
argument_list|()
operator|.
name|doClick
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|help
argument_list|,
literal|4
argument_list|,
literal|10
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|useTimeStamp
argument_list|,
literal|1
argument_list|,
literal|13
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|timeStampFormat
argument_list|,
literal|2
argument_list|,
literal|13
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|overwriteTimeStamp
argument_list|,
literal|3
argument_list|,
literal|13
argument_list|)
expr_stmt|;
name|Label
name|fieldName
init|=
operator|new
name|Label
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Field name"
argument_list|)
operator|+
literal|':'
argument_list|)
decl_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|fieldName
argument_list|,
literal|4
argument_list|,
literal|13
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|timeStampField
argument_list|,
literal|5
argument_list|,
literal|13
argument_list|)
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
name|event
lambda|->
operator|new
name|HelpAction
argument_list|(
name|HelpFile
operator|.
name|TIMESTAMP
argument_list|)
operator|.
name|getHelpButton
argument_list|()
operator|.
name|doClick
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|help1
argument_list|,
literal|6
argument_list|,
literal|13
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|updateTimeStamp
argument_list|,
literal|1
argument_list|,
literal|14
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
operator|new
name|Line
argument_list|()
argument_list|,
literal|1
argument_list|,
literal|15
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|shouldCollectTelemetry
argument_list|,
literal|1
argument_list|,
literal|15
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
operator|new
name|Line
argument_list|()
argument_list|,
literal|1
argument_list|,
literal|16
argument_list|)
expr_stmt|;
name|Label
name|languageLabel
init|=
operator|new
name|Label
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Language"
argument_list|)
operator|+
literal|':'
argument_list|)
decl_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|languageLabel
argument_list|,
literal|1
argument_list|,
literal|17
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|language
argument_list|,
literal|2
argument_list|,
literal|17
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
operator|new
name|Line
argument_list|()
argument_list|,
literal|2
argument_list|,
literal|18
argument_list|)
expr_stmt|;
name|Label
name|defaultEncoding
init|=
operator|new
name|Label
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Default encoding"
argument_list|)
operator|+
literal|':'
argument_list|)
decl_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|defaultEncoding
argument_list|,
literal|1
argument_list|,
literal|19
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|encodings
argument_list|,
literal|2
argument_list|,
literal|19
argument_list|)
expr_stmt|;
name|Label
name|defaultBibliographyMode
init|=
operator|new
name|Label
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Default bibliography mode"
argument_list|)
argument_list|)
decl_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|defaultBibliographyMode
argument_list|,
literal|1
argument_list|,
literal|20
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|biblatexMode
argument_list|,
literal|2
argument_list|,
literal|20
argument_list|)
expr_stmt|;
block|}
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
name|useOwner
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_OWNER
argument_list|)
argument_list|)
expr_stmt|;
name|overwriteOwner
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|OVERWRITE_OWNER
argument_list|)
argument_list|)
expr_stmt|;
name|useTimeStamp
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_TIME_STAMP
argument_list|)
argument_list|)
expr_stmt|;
name|overwriteTimeStamp
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|OVERWRITE_TIME_STAMP
argument_list|)
argument_list|)
expr_stmt|;
name|updateTimeStamp
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|UPDATE_TIMESTAMP
argument_list|)
argument_list|)
expr_stmt|;
name|updateTimeStamp
operator|.
name|setSelected
argument_list|(
name|useTimeStamp
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|enforceLegalKeys
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|ENFORCE_LEGAL_BIBTEX_KEY
argument_list|)
argument_list|)
expr_stmt|;
name|shouldCollectTelemetry
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|shouldCollectTelemetry
argument_list|()
argument_list|)
expr_stmt|;
name|memoryStick
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|MEMORY_STICK_MODE
argument_list|)
argument_list|)
expr_stmt|;
name|confirmDelete
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|CONFIRM_DELETE
argument_list|)
argument_list|)
expr_stmt|;
name|defOwnerField
operator|.
name|setText
argument_list|(
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|DEFAULT_OWNER
argument_list|)
argument_list|)
expr_stmt|;
name|timeStampFormat
operator|.
name|setText
argument_list|(
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|TIME_STAMP_FORMAT
argument_list|)
argument_list|)
expr_stmt|;
name|timeStampField
operator|.
name|setText
argument_list|(
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|TIME_STAMP_FIELD
argument_list|)
argument_list|)
expr_stmt|;
name|inspectionWarnDupli
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|WARN_ABOUT_DUPLICATES_IN_INSPECTION
argument_list|)
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
name|JabRefPreferences
operator|.
name|BIBLATEX_DEFAULT_MODE
argument_list|)
condition|)
block|{
name|biblatexMode
operator|.
name|setValue
argument_list|(
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|biblatexMode
operator|.
name|setValue
argument_list|(
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
expr_stmt|;
block|}
name|Charset
name|enc
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getDefaultEncoding
argument_list|()
decl_stmt|;
name|encodings
operator|.
name|setValue
argument_list|(
name|enc
argument_list|)
expr_stmt|;
name|String
name|oldLan
init|=
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|LANGUAGE
argument_list|)
decl_stmt|;
comment|// Language choice
name|int
name|ilk
init|=
literal|0
decl_stmt|;
for|for
control|(
name|String
name|lan
range|:
name|LANGUAGES
operator|.
name|values
argument_list|()
control|)
block|{
if|if
condition|(
name|lan
operator|.
name|equals
argument_list|(
name|oldLan
argument_list|)
condition|)
block|{
name|language
operator|.
name|setVisibleRowCount
argument_list|(
name|ilk
argument_list|)
expr_stmt|;
block|}
name|ilk
operator|++
expr_stmt|;
block|}
block|}
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
name|USE_OWNER
argument_list|,
name|useOwner
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
name|OVERWRITE_OWNER
argument_list|,
name|overwriteOwner
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
name|USE_TIME_STAMP
argument_list|,
name|useTimeStamp
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
name|OVERWRITE_TIME_STAMP
argument_list|,
name|overwriteTimeStamp
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
name|UPDATE_TIMESTAMP
argument_list|,
name|updateTimeStamp
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
name|ENFORCE_LEGAL_BIBTEX_KEY
argument_list|,
name|enforceLegalKeys
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|setShouldCollectTelemetry
argument_list|(
name|shouldCollectTelemetry
operator|.
name|isSelected
argument_list|()
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
name|MEMORY_STICK_MODE
argument_list|)
operator|&&
operator|!
name|memoryStick
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|DefaultTaskExecutor
operator|.
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
name|dialogService
operator|.
name|showInformationDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Memory stick mode"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"To disable the memory stick mode"
operator|+
literal|" rename or remove the jabref.xml file in the same folder as JabRef."
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|MEMORY_STICK_MODE
argument_list|,
name|memoryStick
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
name|CONFIRM_DELETE
argument_list|,
name|confirmDelete
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
name|WARN_ABOUT_DUPLICATES_IN_INSPECTION
argument_list|,
name|inspectionWarnDupli
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|owner
init|=
name|defOwnerField
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
decl_stmt|;
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|DEFAULT_OWNER
argument_list|,
name|owner
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|TIME_STAMP_FORMAT
argument_list|,
name|timeStampFormat
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|TIME_STAMP_FIELD
argument_list|,
name|timeStampField
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
comment|// Update name of the time stamp field based on preferences
name|InternalBibtexFields
operator|.
name|updateTimeStampField
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|TIME_STAMP_FIELD
argument_list|)
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|setDefaultEncoding
argument_list|(
name|encodings
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|BIBLATEX_DEFAULT_MODE
argument_list|,
name|biblatexMode
operator|.
name|getValue
argument_list|()
operator|.
name|equals
argument_list|(
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|LANGUAGES
operator|.
name|get
argument_list|(
name|language
operator|.
name|getValue
argument_list|()
argument_list|)
operator|.
name|equals
argument_list|(
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|LANGUAGE
argument_list|)
argument_list|)
condition|)
block|{
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|LANGUAGE
argument_list|,
name|LANGUAGES
operator|.
name|get
argument_list|(
name|language
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|Localization
operator|.
name|setLanguage
argument_list|(
name|LANGUAGES
operator|.
name|get
argument_list|(
name|language
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|// Update any defaults that might be language dependent:
name|Globals
operator|.
name|prefs
operator|.
name|setLanguageDependentDefaultValues
argument_list|()
expr_stmt|;
comment|// Warn about restart needed:
name|DefaultTaskExecutor
operator|.
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
name|dialogService
operator|.
name|showWarningDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Changed language settings"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"You have changed the language setting."
argument_list|)
operator|.
name|concat
argument_list|(
literal|" "
argument_list|)
operator|.
name|concat
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"You must restart JabRef for this to come into effect."
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|validateSettings ()
specifier|public
name|boolean
name|validateSettings
parameter_list|()
block|{
try|try
block|{
comment|// Test if date format is legal:
name|DateTimeFormatter
operator|.
name|ofPattern
argument_list|(
name|timeStampFormat
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|ex2
parameter_list|)
block|{
name|DefaultTaskExecutor
operator|.
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Invalid date format"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"The chosen date format for new entries is not valid"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
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
literal|"General"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

