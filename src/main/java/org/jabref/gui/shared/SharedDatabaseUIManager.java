begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.shared
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|shared
package|;
end_package

begin_import
import|import
name|java
operator|.
name|sql
operator|.
name|SQLException
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
name|java
operator|.
name|util
operator|.
name|Optional
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JOptionPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|SwingUtilities
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
name|JabRefGUI
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
name|JabRefFrame
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
name|entryeditor
operator|.
name|EntryEditor
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
name|UndoableRemoveEntry
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
name|logic
operator|.
name|shared
operator|.
name|DBMSConnection
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
name|shared
operator|.
name|DBMSConnectionProperties
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
name|shared
operator|.
name|DBMSSynchronizer
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
name|shared
operator|.
name|event
operator|.
name|ConnectionLostEvent
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
name|shared
operator|.
name|event
operator|.
name|SharedEntryNotPresentEvent
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
name|shared
operator|.
name|event
operator|.
name|UpdateRefusedEvent
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
name|shared
operator|.
name|exception
operator|.
name|InvalidDBMSConnectionPropertiesException
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
name|shared
operator|.
name|exception
operator|.
name|NotASharedDatabaseException
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
name|shared
operator|.
name|prefs
operator|.
name|SharedDatabasePreferences
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
name|Defaults
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
name|BibDatabaseContext
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
name|database
operator|.
name|shared
operator|.
name|DatabaseNotSupportedException
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
name|shared
operator|.
name|DatabaseSynchronizer
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|common
operator|.
name|eventbus
operator|.
name|Subscribe
import|;
end_import

begin_class
DECL|class|SharedDatabaseUIManager
specifier|public
class|class
name|SharedDatabaseUIManager
block|{
DECL|field|jabRefFrame
specifier|private
specifier|final
name|JabRefFrame
name|jabRefFrame
decl_stmt|;
DECL|field|dbmsSynchronizer
specifier|private
name|DatabaseSynchronizer
name|dbmsSynchronizer
decl_stmt|;
DECL|method|SharedDatabaseUIManager (JabRefFrame jabRefFrame)
specifier|public
name|SharedDatabaseUIManager
parameter_list|(
name|JabRefFrame
name|jabRefFrame
parameter_list|)
block|{
name|this
operator|.
name|jabRefFrame
operator|=
name|jabRefFrame
expr_stmt|;
block|}
annotation|@
name|Subscribe
DECL|method|listen (ConnectionLostEvent connectionLostEvent)
specifier|public
name|void
name|listen
parameter_list|(
name|ConnectionLostEvent
name|connectionLostEvent
parameter_list|)
block|{
name|jabRefFrame
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Connection lost."
argument_list|)
argument_list|)
expr_stmt|;
name|String
index|[]
name|options
init|=
block|{
name|Localization
operator|.
name|lang
argument_list|(
literal|"Reconnect"
argument_list|)
block|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Work offline"
argument_list|)
block|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Close library"
argument_list|)
block|}
decl_stmt|;
name|int
name|answer
init|=
name|JOptionPane
operator|.
name|showOptionDialog
argument_list|(
name|jabRefFrame
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"The connection to the server has been terminated."
argument_list|)
operator|+
literal|"\n\n"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Connection lost"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|YES_NO_CANCEL_OPTION
argument_list|,
name|JOptionPane
operator|.
name|WARNING_MESSAGE
argument_list|,
literal|null
argument_list|,
name|options
argument_list|,
name|options
index|[
literal|0
index|]
argument_list|)
decl_stmt|;
if|if
condition|(
name|answer
operator|==
literal|0
condition|)
block|{
name|jabRefFrame
operator|.
name|closeCurrentTab
argument_list|()
expr_stmt|;
name|ConnectToSharedDatabaseDialog
name|connectToSharedDatabaseDialog
init|=
operator|new
name|ConnectToSharedDatabaseDialog
argument_list|(
name|jabRefFrame
argument_list|)
decl_stmt|;
name|connectToSharedDatabaseDialog
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|answer
operator|==
literal|1
condition|)
block|{
name|connectionLostEvent
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|convertToLocalDatabase
argument_list|()
expr_stmt|;
name|jabRefFrame
operator|.
name|refreshTitleAndTabs
argument_list|()
expr_stmt|;
name|jabRefFrame
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Working offline."
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|jabRefFrame
operator|.
name|closeCurrentTab
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Subscribe
DECL|method|listen (UpdateRefusedEvent updateRefusedEvent)
specifier|public
name|void
name|listen
parameter_list|(
name|UpdateRefusedEvent
name|updateRefusedEvent
parameter_list|)
block|{
name|jabRefFrame
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Update refused."
argument_list|)
argument_list|)
expr_stmt|;
operator|new
name|MergeSharedEntryDialog
argument_list|(
name|jabRefFrame
argument_list|,
name|dbmsSynchronizer
argument_list|,
name|updateRefusedEvent
operator|.
name|getLocalBibEntry
argument_list|()
argument_list|,
name|updateRefusedEvent
operator|.
name|getSharedBibEntry
argument_list|()
argument_list|,
name|updateRefusedEvent
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getMode
argument_list|()
argument_list|)
operator|.
name|showMergeDialog
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Subscribe
DECL|method|listen (SharedEntryNotPresentEvent event)
specifier|public
name|void
name|listen
parameter_list|(
name|SharedEntryNotPresentEvent
name|event
parameter_list|)
block|{
name|BasePanel
name|panel
init|=
name|jabRefFrame
operator|.
name|getCurrentBasePanel
argument_list|()
decl_stmt|;
name|EntryEditor
name|entryEditor
init|=
name|panel
operator|.
name|getEntryEditor
argument_list|()
decl_stmt|;
name|panel
operator|.
name|getUndoManager
argument_list|()
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableRemoveEntry
argument_list|(
name|panel
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|event
operator|.
name|getBibEntry
argument_list|()
argument_list|,
name|panel
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|Objects
operator|.
name|nonNull
argument_list|(
name|entryEditor
argument_list|)
operator|&&
operator|(
name|entryEditor
operator|.
name|getEntry
argument_list|()
operator|==
name|event
operator|.
name|getBibEntry
argument_list|()
operator|)
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|jabRefFrame
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"The BibEntry you currently work on has been deleted on the shared side."
argument_list|)
operator|+
literal|"\n"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"You can restore the entry using the \"Undo\" operation."
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Shared entry is no longer present"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|INFORMATION_MESSAGE
argument_list|)
expr_stmt|;
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
parameter_list|()
lambda|->
name|panel
operator|.
name|closeBottomPane
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Opens a new shared database tab with the given {@link DBMSConnectionProperties}.      *      * @param dbmsConnectionProperties Connection data      * @param raiseTab If<code>true</code> the new tab gets selected.      * @return BasePanel which also used by {@link SaveDatabaseAction}      */
DECL|method|openNewSharedDatabaseTab (DBMSConnectionProperties dbmsConnectionProperties)
specifier|public
name|BasePanel
name|openNewSharedDatabaseTab
parameter_list|(
name|DBMSConnectionProperties
name|dbmsConnectionProperties
parameter_list|)
throws|throws
name|SQLException
throws|,
name|DatabaseNotSupportedException
throws|,
name|InvalidDBMSConnectionPropertiesException
block|{
name|JabRefFrame
name|frame
init|=
name|JabRefGUI
operator|.
name|getMainFrame
argument_list|()
decl_stmt|;
name|BibDatabaseMode
name|selectedMode
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getDefaultBibDatabaseMode
argument_list|()
decl_stmt|;
name|BibDatabaseContext
name|bibDatabaseContext
init|=
operator|new
name|BibDatabaseContext
argument_list|(
operator|new
name|Defaults
argument_list|(
name|selectedMode
argument_list|)
argument_list|)
decl_stmt|;
name|DBMSSynchronizer
name|synchronizer
init|=
operator|new
name|DBMSSynchronizer
argument_list|(
name|bibDatabaseContext
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getKeywordDelimiter
argument_list|()
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getKeyPattern
argument_list|()
argument_list|)
decl_stmt|;
name|bibDatabaseContext
operator|.
name|convertToSharedDatabase
argument_list|(
name|synchronizer
argument_list|)
expr_stmt|;
name|dbmsSynchronizer
operator|=
name|bibDatabaseContext
operator|.
name|getDBMSSynchronizer
argument_list|()
expr_stmt|;
name|dbmsSynchronizer
operator|.
name|openSharedDatabase
argument_list|(
operator|new
name|DBMSConnection
argument_list|(
name|dbmsConnectionProperties
argument_list|)
argument_list|)
expr_stmt|;
name|dbmsSynchronizer
operator|.
name|registerListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|frame
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Connection to %0 server established."
argument_list|,
name|dbmsConnectionProperties
operator|.
name|getType
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|frame
operator|.
name|addTab
argument_list|(
name|bibDatabaseContext
argument_list|,
literal|true
argument_list|)
return|;
block|}
DECL|method|openSharedDatabaseFromParserResult (ParserResult parserResult)
specifier|public
name|void
name|openSharedDatabaseFromParserResult
parameter_list|(
name|ParserResult
name|parserResult
parameter_list|)
throws|throws
name|SQLException
throws|,
name|DatabaseNotSupportedException
throws|,
name|InvalidDBMSConnectionPropertiesException
throws|,
name|NotASharedDatabaseException
block|{
name|Optional
argument_list|<
name|String
argument_list|>
name|sharedDatabaseIDOptional
init|=
name|parserResult
operator|.
name|getDatabase
argument_list|()
operator|.
name|getSharedDatabaseID
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|sharedDatabaseIDOptional
operator|.
name|isPresent
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|NotASharedDatabaseException
argument_list|()
throw|;
block|}
name|String
name|sharedDatabaseID
init|=
name|sharedDatabaseIDOptional
operator|.
name|get
argument_list|()
decl_stmt|;
name|DBMSConnectionProperties
name|dbmsConnectionProperties
init|=
operator|new
name|DBMSConnectionProperties
argument_list|(
operator|new
name|SharedDatabasePreferences
argument_list|(
name|sharedDatabaseID
argument_list|)
argument_list|)
decl_stmt|;
name|JabRefFrame
name|frame
init|=
name|JabRefGUI
operator|.
name|getMainFrame
argument_list|()
decl_stmt|;
name|BibDatabaseMode
name|selectedMode
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getDefaultBibDatabaseMode
argument_list|()
decl_stmt|;
name|BibDatabaseContext
name|bibDatabaseContext
init|=
operator|new
name|BibDatabaseContext
argument_list|(
operator|new
name|Defaults
argument_list|(
name|selectedMode
argument_list|)
argument_list|)
decl_stmt|;
name|DBMSSynchronizer
name|synchronizer
init|=
operator|new
name|DBMSSynchronizer
argument_list|(
name|bibDatabaseContext
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getKeywordDelimiter
argument_list|()
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getKeyPattern
argument_list|()
argument_list|)
decl_stmt|;
name|bibDatabaseContext
operator|.
name|convertToSharedDatabase
argument_list|(
name|synchronizer
argument_list|)
expr_stmt|;
name|bibDatabaseContext
operator|.
name|getDatabase
argument_list|()
operator|.
name|setSharedDatabaseID
argument_list|(
name|sharedDatabaseID
argument_list|)
expr_stmt|;
name|bibDatabaseContext
operator|.
name|setDatabaseFile
argument_list|(
name|parserResult
operator|.
name|getDatabaseContext
argument_list|()
operator|.
name|getDatabaseFile
argument_list|()
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|dbmsSynchronizer
operator|=
name|bibDatabaseContext
operator|.
name|getDBMSSynchronizer
argument_list|()
expr_stmt|;
name|dbmsSynchronizer
operator|.
name|openSharedDatabase
argument_list|(
operator|new
name|DBMSConnection
argument_list|(
name|dbmsConnectionProperties
argument_list|)
argument_list|)
expr_stmt|;
name|dbmsSynchronizer
operator|.
name|registerListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|parserResult
operator|.
name|setDatabaseContext
argument_list|(
name|bibDatabaseContext
argument_list|)
expr_stmt|;
name|frame
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Connection to %0 server established."
argument_list|,
name|dbmsConnectionProperties
operator|.
name|getType
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

