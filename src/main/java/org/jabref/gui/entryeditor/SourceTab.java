begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.entryeditor
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|entryeditor
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|StringReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|StringWriter
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
name|java
operator|.
name|util
operator|.
name|Objects
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|undo
operator|.
name|UndoManager
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|ObjectProperty
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|SimpleObjectProperty
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|collections
operator|.
name|ListChangeListener
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
name|Tooltip
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
name|IconTheme
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
name|undo
operator|.
name|NamedCompound
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
name|undo
operator|.
name|UndoableChangeType
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
name|undo
operator|.
name|UndoableFieldChange
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
name|BindingsHelper
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
name|bibtex
operator|.
name|BibEntryWriter
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
name|bibtex
operator|.
name|InvalidFieldValueException
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
name|bibtex
operator|.
name|LatexFieldFormatter
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
name|importer
operator|.
name|ParserResult
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
name|importer
operator|.
name|fileformat
operator|.
name|BibtexParser
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
name|BibDatabase
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
name|BibEntry
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
name|de
operator|.
name|saxsys
operator|.
name|mvvmfx
operator|.
name|utils
operator|.
name|validation
operator|.
name|ObservableRuleBasedValidator
import|;
end_import

begin_import
import|import
name|de
operator|.
name|saxsys
operator|.
name|mvvmfx
operator|.
name|utils
operator|.
name|validation
operator|.
name|ValidationMessage
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_import
import|import
name|org
operator|.
name|controlsfx
operator|.
name|control
operator|.
name|NotificationPane
import|;
end_import

begin_import
import|import
name|org
operator|.
name|fxmisc
operator|.
name|flowless
operator|.
name|VirtualizedScrollPane
import|;
end_import

begin_import
import|import
name|org
operator|.
name|fxmisc
operator|.
name|richtext
operator|.
name|CodeArea
import|;
end_import

begin_class
DECL|class|SourceTab
specifier|public
class|class
name|SourceTab
extends|extends
name|EntryEditorTab
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|SourceTab
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|mode
specifier|private
specifier|final
name|BibDatabaseMode
name|mode
decl_stmt|;
DECL|field|undoManager
specifier|private
name|UndoManager
name|undoManager
decl_stmt|;
DECL|field|sourceIsValid
specifier|private
specifier|final
name|ObjectProperty
argument_list|<
name|ValidationMessage
argument_list|>
name|sourceIsValid
init|=
operator|new
name|SimpleObjectProperty
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|sourceValidator
specifier|private
specifier|final
name|ObservableRuleBasedValidator
name|sourceValidator
init|=
operator|new
name|ObservableRuleBasedValidator
argument_list|(
name|sourceIsValid
argument_list|)
decl_stmt|;
DECL|method|SourceTab (BasePanel panel)
specifier|public
name|SourceTab
parameter_list|(
name|BasePanel
name|panel
parameter_list|)
block|{
name|this
operator|.
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
name|this
operator|.
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 source"
argument_list|,
name|mode
operator|.
name|getFormattedName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|setTooltip
argument_list|(
operator|new
name|Tooltip
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Show/edit %0 source"
argument_list|,
name|mode
operator|.
name|getFormattedName
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|setGraphic
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|SOURCE
operator|.
name|getGraphicNode
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|undoManager
operator|=
name|panel
operator|.
name|getUndoManager
argument_list|()
expr_stmt|;
block|}
DECL|method|getSourceString (BibEntry entry, BibDatabaseMode type)
specifier|private
specifier|static
name|String
name|getSourceString
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|BibDatabaseMode
name|type
parameter_list|)
throws|throws
name|IOException
block|{
name|StringWriter
name|stringWriter
init|=
operator|new
name|StringWriter
argument_list|(
literal|200
argument_list|)
decl_stmt|;
name|LatexFieldFormatter
name|formatter
init|=
name|LatexFieldFormatter
operator|.
name|buildIgnoreHashes
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getLatexFieldFormatterPreferences
argument_list|()
argument_list|)
decl_stmt|;
operator|new
name|BibEntryWriter
argument_list|(
name|formatter
argument_list|,
literal|false
argument_list|)
operator|.
name|writeWithoutPrependedNewlines
argument_list|(
name|entry
argument_list|,
name|stringWriter
argument_list|,
name|type
argument_list|)
expr_stmt|;
return|return
name|stringWriter
operator|.
name|getBuffer
argument_list|()
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|createSourceEditor ()
specifier|private
name|CodeArea
name|createSourceEditor
parameter_list|()
block|{
name|CodeArea
name|codeArea
init|=
operator|new
name|CodeArea
argument_list|()
decl_stmt|;
name|codeArea
operator|.
name|setWrapText
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|codeArea
operator|.
name|lookup
argument_list|(
literal|".styled-text-area"
argument_list|)
operator|.
name|setStyle
argument_list|(
literal|"-fx-font-size: "
operator|+
name|Globals
operator|.
name|prefs
operator|.
name|getFontSizeFX
argument_list|()
operator|+
literal|"pt;"
argument_list|)
expr_stmt|;
return|return
name|codeArea
return|;
block|}
annotation|@
name|Override
DECL|method|shouldShow (BibEntry entry)
specifier|public
name|boolean
name|shouldShow
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
return|return
literal|true
return|;
block|}
annotation|@
name|Override
DECL|method|bindToEntry (BibEntry entry)
specifier|protected
name|void
name|bindToEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
name|CodeArea
name|codeArea
init|=
name|createSourceEditor
argument_list|()
decl_stmt|;
name|VirtualizedScrollPane
argument_list|<
name|CodeArea
argument_list|>
name|node
init|=
operator|new
name|VirtualizedScrollPane
argument_list|<>
argument_list|(
name|codeArea
argument_list|)
decl_stmt|;
name|NotificationPane
name|notificationPane
init|=
operator|new
name|NotificationPane
argument_list|(
name|node
argument_list|)
decl_stmt|;
name|notificationPane
operator|.
name|setShowFromTop
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|sourceValidator
operator|.
name|getValidationStatus
argument_list|()
operator|.
name|getMessages
argument_list|()
operator|.
name|addListener
argument_list|(
operator|(
name|ListChangeListener
argument_list|<
name|ValidationMessage
argument_list|>
operator|)
name|c
lambda|->
block|{
if|if
condition|(
name|sourceValidator
operator|.
name|getValidationStatus
argument_list|()
operator|.
name|isValid
argument_list|()
condition|)
block|{
name|notificationPane
operator|.
name|hide
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|sourceValidator
operator|.
name|getValidationStatus
argument_list|()
operator|.
name|getHighestMessage
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|validationMessage
lambda|->
name|notificationPane
operator|.
name|show
argument_list|(
name|validationMessage
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|this
operator|.
name|setContent
argument_list|(
name|notificationPane
argument_list|)
expr_stmt|;
comment|// Store source for every change in the source code
comment|// and update source code for every change of entry field values
name|BindingsHelper
operator|.
name|bindContentBidirectional
argument_list|(
name|entry
operator|.
name|getFieldsObservable
argument_list|()
argument_list|,
name|codeArea
operator|.
name|textProperty
argument_list|()
argument_list|,
name|this
operator|::
name|storeSource
argument_list|,
name|fields
lambda|->
block|{
name|DefaultTaskExecutor
operator|.
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
block|{
name|codeArea
operator|.
name|clear
argument_list|()
expr_stmt|;
try|try
block|{
name|codeArea
operator|.
name|appendText
argument_list|(
name|getSourceString
argument_list|(
name|entry
argument_list|,
name|mode
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|codeArea
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|codeArea
operator|.
name|appendText
argument_list|(
name|ex
operator|.
name|getMessage
argument_list|()
operator|+
literal|"\n\n"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Correct the entry, and reopen editor to display/edit source."
argument_list|)
argument_list|)
expr_stmt|;
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Incorrect entry"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|storeSource (String text)
specifier|private
name|void
name|storeSource
parameter_list|(
name|String
name|text
parameter_list|)
block|{
if|if
condition|(
name|text
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return;
block|}
name|BibtexParser
name|bibtexParser
init|=
operator|new
name|BibtexParser
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getImportFormatPreferences
argument_list|()
argument_list|)
decl_stmt|;
try|try
block|{
name|ParserResult
name|parserResult
init|=
name|bibtexParser
operator|.
name|parse
argument_list|(
operator|new
name|StringReader
argument_list|(
name|text
argument_list|)
argument_list|)
decl_stmt|;
name|BibDatabase
name|database
init|=
name|parserResult
operator|.
name|getDatabase
argument_list|()
decl_stmt|;
if|if
condition|(
name|database
operator|.
name|getEntryCount
argument_list|()
operator|>
literal|1
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"More than one entry found."
argument_list|)
throw|;
block|}
if|if
condition|(
operator|!
name|database
operator|.
name|hasEntries
argument_list|()
condition|)
block|{
if|if
condition|(
name|parserResult
operator|.
name|hasWarnings
argument_list|()
condition|)
block|{
comment|// put the warning into as exception text -> it will be displayed to the user
throw|throw
operator|new
name|IllegalStateException
argument_list|(
name|parserResult
operator|.
name|warnings
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
throw|;
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"No entries found."
argument_list|)
throw|;
block|}
block|}
name|NamedCompound
name|compound
init|=
operator|new
name|NamedCompound
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"source edit"
argument_list|)
argument_list|)
decl_stmt|;
name|BibEntry
name|newEntry
init|=
name|database
operator|.
name|getEntries
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|String
name|newKey
init|=
name|newEntry
operator|.
name|getCiteKeyOptional
argument_list|()
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|newKey
operator|!=
literal|null
condition|)
block|{
name|currentEntry
operator|.
name|setCiteKey
argument_list|(
name|newKey
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|currentEntry
operator|.
name|clearCiteKey
argument_list|()
expr_stmt|;
block|}
comment|// First, remove fields that the user has removed.
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
name|field
range|:
name|currentEntry
operator|.
name|getFieldMap
argument_list|()
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|String
name|fieldName
init|=
name|field
operator|.
name|getKey
argument_list|()
decl_stmt|;
name|String
name|fieldValue
init|=
name|field
operator|.
name|getValue
argument_list|()
decl_stmt|;
if|if
condition|(
name|InternalBibtexFields
operator|.
name|isDisplayableField
argument_list|(
name|fieldName
argument_list|)
operator|&&
operator|!
name|newEntry
operator|.
name|hasField
argument_list|(
name|fieldName
argument_list|)
condition|)
block|{
name|compound
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableFieldChange
argument_list|(
name|currentEntry
argument_list|,
name|fieldName
argument_list|,
name|fieldValue
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|currentEntry
operator|.
name|clearField
argument_list|(
name|fieldName
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Then set all fields that have been set by the user.
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
name|field
range|:
name|newEntry
operator|.
name|getFieldMap
argument_list|()
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|String
name|fieldName
init|=
name|field
operator|.
name|getKey
argument_list|()
decl_stmt|;
name|String
name|oldValue
init|=
name|currentEntry
operator|.
name|getField
argument_list|(
name|fieldName
argument_list|)
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
decl_stmt|;
name|String
name|newValue
init|=
name|field
operator|.
name|getValue
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|Objects
operator|.
name|equals
argument_list|(
name|oldValue
argument_list|,
name|newValue
argument_list|)
condition|)
block|{
comment|// Test if the field is legally set.
operator|new
name|LatexFieldFormatter
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getLatexFieldFormatterPreferences
argument_list|()
argument_list|)
operator|.
name|format
argument_list|(
name|newValue
argument_list|,
name|fieldName
argument_list|)
expr_stmt|;
name|compound
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableFieldChange
argument_list|(
name|currentEntry
argument_list|,
name|fieldName
argument_list|,
name|oldValue
argument_list|,
name|newValue
argument_list|)
argument_list|)
expr_stmt|;
name|currentEntry
operator|.
name|setField
argument_list|(
name|fieldName
argument_list|,
name|newValue
argument_list|)
expr_stmt|;
block|}
block|}
comment|// See if the user has changed the entry type:
if|if
condition|(
operator|!
name|Objects
operator|.
name|equals
argument_list|(
name|newEntry
operator|.
name|getType
argument_list|()
argument_list|,
name|currentEntry
operator|.
name|getType
argument_list|()
argument_list|)
condition|)
block|{
name|compound
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableChangeType
argument_list|(
name|currentEntry
argument_list|,
name|currentEntry
operator|.
name|getType
argument_list|()
argument_list|,
name|newEntry
operator|.
name|getType
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|currentEntry
operator|.
name|setType
argument_list|(
name|newEntry
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|compound
operator|.
name|end
argument_list|()
expr_stmt|;
name|undoManager
operator|.
name|addEdit
argument_list|(
name|compound
argument_list|)
expr_stmt|;
name|sourceIsValid
operator|.
name|setValue
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InvalidFieldValueException
decl||
name|IllegalStateException
decl||
name|IOException
name|ex
parameter_list|)
block|{
name|sourceIsValid
operator|.
name|setValue
argument_list|(
name|ValidationMessage
operator|.
name|error
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Problem with parsing entry"
argument_list|)
operator|+
literal|": "
operator|+
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Incorrect source"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

