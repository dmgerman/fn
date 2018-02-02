begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref
package|package
name|org
operator|.
name|jabref
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Frame
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|annotation
operator|.
name|Native
import|;
end_import

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
name|ArrayList
import|;
end_import

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
name|Enumeration
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
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
name|UIDefaults
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|UIManager
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|UnsupportedLookAndFeelException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|plaf
operator|.
name|FontUIResource
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|application
operator|.
name|Platform
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|Scene
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
name|TextArea
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
name|VBox
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|stage
operator|.
name|Stage
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
name|GUIGlobals
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
name|autosaveandbackup
operator|.
name|BackupUIManager
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
name|importer
operator|.
name|ParserResultWarningDialog
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
name|importer
operator|.
name|actions
operator|.
name|OpenDatabaseAction
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
name|shared
operator|.
name|SharedDatabaseUIManager
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
name|worker
operator|.
name|VersionWorker
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
name|autosaveandbackup
operator|.
name|BackupManager
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
name|OpenDatabase
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
name|util
operator|.
name|OS
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
name|util
operator|.
name|Version
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
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|awt
operator|.
name|Pointer
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_class
DECL|class|JabRefGUI
specifier|public
class|class
name|JabRefGUI
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Logger
name|LOGGER
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|JabRefGUI
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|mainFrame
specifier|private
specifier|static
name|JabRefFrame
name|mainFrame
decl_stmt|;
DECL|field|bibDatabases
specifier|private
specifier|final
name|List
argument_list|<
name|ParserResult
argument_list|>
name|bibDatabases
decl_stmt|;
DECL|field|isBlank
specifier|private
specifier|final
name|boolean
name|isBlank
decl_stmt|;
DECL|field|failed
specifier|private
specifier|final
name|List
argument_list|<
name|ParserResult
argument_list|>
name|failed
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|toOpenTab
specifier|private
specifier|final
name|List
argument_list|<
name|ParserResult
argument_list|>
name|toOpenTab
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|focusedFile
specifier|private
specifier|final
name|String
name|focusedFile
decl_stmt|;
DECL|method|JabRefGUI (Stage mainStage, List<ParserResult> argsDatabases, boolean isBlank)
specifier|public
name|JabRefGUI
parameter_list|(
name|Stage
name|mainStage
parameter_list|,
name|List
argument_list|<
name|ParserResult
argument_list|>
name|argsDatabases
parameter_list|,
name|boolean
name|isBlank
parameter_list|)
block|{
name|this
operator|.
name|bibDatabases
operator|=
name|argsDatabases
expr_stmt|;
name|this
operator|.
name|isBlank
operator|=
name|isBlank
expr_stmt|;
comment|// passed file (we take the first one) should be focused
name|focusedFile
operator|=
name|argsDatabases
operator|.
name|stream
argument_list|()
operator|.
name|findFirst
argument_list|()
operator|.
name|flatMap
argument_list|(
name|ParserResult
operator|::
name|getFile
argument_list|)
operator|.
name|map
argument_list|(
name|File
operator|::
name|getAbsolutePath
argument_list|)
operator|.
name|orElse
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|LAST_FOCUSED
argument_list|)
argument_list|)
expr_stmt|;
name|openWindow
argument_list|(
name|mainStage
argument_list|)
expr_stmt|;
name|JabRefGUI
operator|.
name|checkForNewVersion
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
DECL|method|checkForNewVersion (boolean manualExecution)
specifier|public
specifier|static
name|void
name|checkForNewVersion
parameter_list|(
name|boolean
name|manualExecution
parameter_list|)
block|{
name|Version
name|toBeIgnored
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getVersionPreferences
argument_list|()
operator|.
name|getIgnoredVersion
argument_list|()
decl_stmt|;
name|Version
name|currentVersion
init|=
name|Globals
operator|.
name|BUILD_INFO
operator|.
name|getVersion
argument_list|()
decl_stmt|;
operator|new
name|VersionWorker
argument_list|(
name|JabRefGUI
operator|.
name|getMainFrame
argument_list|()
argument_list|,
name|manualExecution
argument_list|,
name|currentVersion
argument_list|,
name|toBeIgnored
argument_list|)
operator|.
name|execute
argument_list|()
expr_stmt|;
block|}
DECL|method|openWindow (Stage mainStage)
specifier|private
name|void
name|openWindow
parameter_list|(
name|Stage
name|mainStage
parameter_list|)
block|{
comment|// This property is set to make the Mac OSX Java VM move the menu bar to the top of the screen
if|if
condition|(
name|OS
operator|.
name|OS_X
condition|)
block|{
name|System
operator|.
name|setProperty
argument_list|(
literal|"apple.laf.useScreenMenuBar"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
block|}
comment|// Set antialiasing on everywhere. This only works in JRE>= 1.5.
comment|// Or... it doesn't work, period.
comment|// TODO test and maybe remove this! I found this commented out with no additional info ( payload@lavabit.com )
comment|// Enabled since JabRef 2.11 beta 4
name|System
operator|.
name|setProperty
argument_list|(
literal|"swing.aatext"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
comment|// Default is "on".
comment|// "lcd" instead of "on" because of http://wiki.netbeans.org/FaqFontRendering and http://docs.oracle.com/javase/6/docs/technotes/guides/2d/flags.html#aaFonts
name|System
operator|.
name|setProperty
argument_list|(
literal|"awt.useSystemAAFontSettings"
argument_list|,
literal|"lcd"
argument_list|)
expr_stmt|;
comment|// look and feel. This MUST be the first thing to do before loading any Swing-specific code!
name|setLookAndFeel
argument_list|()
expr_stmt|;
comment|// If the option is enabled, open the last edited libraries, if any.
if|if
condition|(
operator|!
name|isBlank
operator|&&
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|OPEN_LAST_EDITED
argument_list|)
condition|)
block|{
name|openLastEditedDatabases
argument_list|()
expr_stmt|;
block|}
name|GUIGlobals
operator|.
name|init
argument_list|()
expr_stmt|;
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Initializing frame"
argument_list|)
expr_stmt|;
name|JabRefGUI
operator|.
name|mainFrame
operator|=
operator|new
name|JabRefFrame
argument_list|()
expr_stmt|;
comment|// Add all bibDatabases databases to the frame:
name|boolean
name|first
init|=
literal|false
decl_stmt|;
if|if
condition|(
operator|!
name|bibDatabases
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
for|for
control|(
name|Iterator
argument_list|<
name|ParserResult
argument_list|>
name|parserResultIterator
init|=
name|bibDatabases
operator|.
name|iterator
argument_list|()
init|;
name|parserResultIterator
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|ParserResult
name|pr
init|=
name|parserResultIterator
operator|.
name|next
argument_list|()
decl_stmt|;
comment|// Define focused tab
if|if
condition|(
name|pr
operator|.
name|getFile
argument_list|()
operator|.
name|get
argument_list|()
operator|.
name|getAbsolutePath
argument_list|()
operator|.
name|equals
argument_list|(
name|focusedFile
argument_list|)
condition|)
block|{
name|first
operator|=
literal|true
expr_stmt|;
block|}
if|if
condition|(
name|pr
operator|.
name|isInvalid
argument_list|()
condition|)
block|{
name|failed
operator|.
name|add
argument_list|(
name|pr
argument_list|)
expr_stmt|;
name|parserResultIterator
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|pr
operator|.
name|getDatabase
argument_list|()
operator|.
name|isShared
argument_list|()
condition|)
block|{
try|try
block|{
operator|new
name|SharedDatabaseUIManager
argument_list|(
name|mainFrame
argument_list|)
operator|.
name|openSharedDatabaseFromParserResult
argument_list|(
name|pr
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SQLException
decl||
name|DatabaseNotSupportedException
decl||
name|InvalidDBMSConnectionPropertiesException
decl||
name|NotASharedDatabaseException
name|e
parameter_list|)
block|{
name|pr
operator|.
name|getDatabaseContext
argument_list|()
operator|.
name|clearDatabaseFile
argument_list|()
expr_stmt|;
comment|// do not open the original file
name|pr
operator|.
name|getDatabase
argument_list|()
operator|.
name|clearSharedDatabaseID
argument_list|()
expr_stmt|;
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Connection error"
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
literal|null
argument_list|,
name|e
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
literal|"A local copy will be opened."
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Connection error"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|WARNING_MESSAGE
argument_list|)
expr_stmt|;
block|}
name|toOpenTab
operator|.
name|add
argument_list|(
name|pr
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|pr
operator|.
name|toOpenTab
argument_list|()
condition|)
block|{
comment|// things to be appended to an opened tab should be done after opening all tabs
comment|// add them to the list
name|toOpenTab
operator|.
name|add
argument_list|(
name|pr
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|JabRefGUI
operator|.
name|getMainFrame
argument_list|()
operator|.
name|addParserResult
argument_list|(
name|pr
argument_list|,
name|first
argument_list|)
expr_stmt|;
name|first
operator|=
literal|false
expr_stmt|;
block|}
block|}
block|}
comment|// finally add things to the currently opened tab
for|for
control|(
name|ParserResult
name|pr
range|:
name|toOpenTab
control|)
block|{
name|JabRefGUI
operator|.
name|getMainFrame
argument_list|()
operator|.
name|addParserResult
argument_list|(
name|pr
argument_list|,
name|first
argument_list|)
expr_stmt|;
name|first
operator|=
literal|false
expr_stmt|;
block|}
comment|// If we are set to remember the window location, we also remember the maximised
comment|// state. This needs to be set after the window has been made visible, so we
comment|// do it here:
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
name|WINDOW_MAXIMISED
argument_list|)
condition|)
block|{
comment|//JabRefGUI.getMainFrame().setExtendedState(Frame.MAXIMIZED_BOTH);
block|}
name|mainStage
operator|.
name|setTitle
argument_list|(
name|JabRefFrame
operator|.
name|FRAME_TITLE
argument_list|)
expr_stmt|;
name|TextArea
name|ta
init|=
operator|new
name|TextArea
argument_list|(
literal|"output\n"
argument_list|)
decl_stmt|;
name|VBox
name|root
init|=
operator|new
name|VBox
argument_list|(
literal|5
argument_list|,
name|ta
argument_list|)
decl_stmt|;
name|Scene
name|scene
init|=
operator|new
name|Scene
argument_list|(
name|root
argument_list|,
literal|800
argument_list|,
literal|200
argument_list|)
decl_stmt|;
name|mainStage
operator|.
name|setTitle
argument_list|(
literal|"Find this window"
argument_list|)
expr_stmt|;
name|mainStage
operator|.
name|setScene
argument_list|(
name|scene
argument_list|)
expr_stmt|;
name|mainStage
operator|.
name|show
argument_list|()
expr_stmt|;
comment|/*         //gets this window (stage)         long lhwnd = com.sun.glass.ui.Window.getWindows().get(0).getNativeWindow();         Pointer lpVoid = new Pointer(lhwnd);         //gets the foreground (focused) window         final User32 user32 = User32.INSTANCE;         char[] windowText = new char[512];         WinDef.HWND hwnd = user32.GetForegroundWindow();         //see what the title is         user32.GetWindowText(hwnd, windowText, 512);         //user32.GetWindowText(new HWND(lpVoid), windowText, 512);//to use the hwnd from stage         String text = (Native.toString(windowText));         //see if it's the same pointer         ta.appendText("HWND java:" + lpVoid + " HWND user32:" + hwnd + " text:" + text + "\n");         //change the window style if it's the right title         if (text.equals(mainStage.getTitle())) {             //the style to change             int WS_DLGFRAME = 0x00400000;//s/b long I think             //not the same constant here??             ta.appendText("windows api:" + WS_DLGFRAME + " JNA: " + WinUser.SM_CXDLGFRAME);             int oldStyle = user32.GetWindowLong(hwnd, GWL_STYLE);             int newStyle = oldStyle& ~0x00400000; //bitwise not WS_DLGFRAME means remove the style             newStyle = newStyle& ~0x00040000;//WS_THICKFRAME             user32.SetWindowLong(hwnd, GWL_STYLE, newStyle);         }         */
name|mainStage
operator|.
name|setOnCloseRequest
argument_list|(
name|event
lambda|->
block|{
name|mainFrame
operator|.
name|quit
argument_list|()
expr_stmt|;
name|Platform
operator|.
name|exit
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
for|for
control|(
name|ParserResult
name|pr
range|:
name|failed
control|)
block|{
name|String
name|message
init|=
literal|"<html>"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error opening file '%0'."
argument_list|,
name|pr
operator|.
name|getFile
argument_list|()
operator|.
name|get
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
operator|+
literal|"<p>"
operator|+
name|pr
operator|.
name|getErrorMessage
argument_list|()
operator|+
literal|"</html>"
decl_stmt|;
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
literal|null
argument_list|,
name|message
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error opening file"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
comment|// Display warnings, if any
name|int
name|tabNumber
init|=
literal|0
decl_stmt|;
for|for
control|(
name|ParserResult
name|pr
range|:
name|bibDatabases
control|)
block|{
name|ParserResultWarningDialog
operator|.
name|showParserResultWarningDialog
argument_list|(
name|pr
argument_list|,
name|JabRefGUI
operator|.
name|getMainFrame
argument_list|()
argument_list|,
name|tabNumber
operator|++
argument_list|)
expr_stmt|;
block|}
comment|// After adding the databases, go through each and see if
comment|// any post open actions need to be done. For instance, checking
comment|// if we found new entry types that can be imported, or checking
comment|// if the database contents should be modified due to new features
comment|// in this version of JabRef.
comment|// Note that we have to check whether i does not go over getBasePanelCount().
comment|// This is because importToOpen might have been used, which adds to
comment|// loadedDatabases, but not to getBasePanelCount()
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
operator|(
name|i
operator|<
name|bibDatabases
operator|.
name|size
argument_list|()
operator|)
operator|&&
operator|(
name|i
operator|<
name|JabRefGUI
operator|.
name|getMainFrame
argument_list|()
operator|.
name|getBasePanelCount
argument_list|()
operator|)
condition|;
name|i
operator|++
control|)
block|{
name|ParserResult
name|pr
init|=
name|bibDatabases
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|BasePanel
name|panel
init|=
name|JabRefGUI
operator|.
name|getMainFrame
argument_list|()
operator|.
name|getBasePanelAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|OpenDatabaseAction
operator|.
name|performPostOpenActions
argument_list|(
name|panel
argument_list|,
name|pr
argument_list|)
expr_stmt|;
block|}
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Finished adding panels"
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|bibDatabases
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|JabRefGUI
operator|.
name|getMainFrame
argument_list|()
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|getMainTable
argument_list|()
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|openLastEditedDatabases ()
specifier|private
name|void
name|openLastEditedDatabases
parameter_list|()
block|{
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|LAST_EDITED
argument_list|)
operator|==
literal|null
condition|)
block|{
return|return;
block|}
name|List
argument_list|<
name|String
argument_list|>
name|lastFiles
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getStringList
argument_list|(
name|JabRefPreferences
operator|.
name|LAST_EDITED
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|fileName
range|:
name|lastFiles
control|)
block|{
name|File
name|dbFile
init|=
operator|new
name|File
argument_list|(
name|fileName
argument_list|)
decl_stmt|;
comment|// Already parsed via command line parameter, e.g., "jabref.jar somefile.bib"
if|if
condition|(
name|isLoaded
argument_list|(
name|dbFile
argument_list|)
operator|||
operator|!
name|dbFile
operator|.
name|exists
argument_list|()
condition|)
block|{
continue|continue;
block|}
if|if
condition|(
name|BackupManager
operator|.
name|checkForBackupFile
argument_list|(
name|dbFile
operator|.
name|toPath
argument_list|()
argument_list|)
condition|)
block|{
name|BackupUIManager
operator|.
name|showRestoreBackupDialog
argument_list|(
literal|null
argument_list|,
name|dbFile
operator|.
name|toPath
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|ParserResult
name|parsedDatabase
init|=
name|OpenDatabase
operator|.
name|loadDatabase
argument_list|(
name|fileName
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getImportFormatPreferences
argument_list|()
argument_list|,
name|Globals
operator|.
name|getFileUpdateMonitor
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|parsedDatabase
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error opening file"
argument_list|)
operator|+
literal|" '"
operator|+
name|dbFile
operator|.
name|getPath
argument_list|()
operator|+
literal|"'"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|bibDatabases
operator|.
name|add
argument_list|(
name|parsedDatabase
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|isLoaded (File fileToOpen)
specifier|private
name|boolean
name|isLoaded
parameter_list|(
name|File
name|fileToOpen
parameter_list|)
block|{
for|for
control|(
name|ParserResult
name|pr
range|:
name|bibDatabases
control|)
block|{
if|if
condition|(
name|pr
operator|.
name|getFile
argument_list|()
operator|.
name|isPresent
argument_list|()
operator|&&
name|pr
operator|.
name|getFile
argument_list|()
operator|.
name|get
argument_list|()
operator|.
name|equals
argument_list|(
name|fileToOpen
argument_list|)
condition|)
block|{
return|return
literal|true
return|;
block|}
block|}
return|return
literal|false
return|;
block|}
DECL|method|setLookAndFeel ()
specifier|private
name|void
name|setLookAndFeel
parameter_list|()
block|{
try|try
block|{
name|String
name|lookFeel
decl_stmt|;
name|String
name|systemLookFeel
init|=
name|UIManager
operator|.
name|getSystemLookAndFeelClassName
argument_list|()
decl_stmt|;
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
name|USE_DEFAULT_LOOK_AND_FEEL
argument_list|)
condition|)
block|{
comment|// FIXME: Problems with OpenJDK and GTK L&F
comment|// See https://github.com/JabRef/jabref/issues/393, https://github.com/JabRef/jabref/issues/638
if|if
condition|(
name|System
operator|.
name|getProperty
argument_list|(
literal|"java.runtime.name"
argument_list|)
operator|.
name|contains
argument_list|(
literal|"OpenJDK"
argument_list|)
condition|)
block|{
comment|// Metal L&F
name|lookFeel
operator|=
name|UIManager
operator|.
name|getCrossPlatformLookAndFeelClassName
argument_list|()
expr_stmt|;
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"There seem to be problems with OpenJDK and the default GTK Look&Feel. Using Metal L&F instead. Change to another L&F with caution."
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|lookFeel
operator|=
name|systemLookFeel
expr_stmt|;
block|}
block|}
else|else
block|{
name|lookFeel
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|WIN_LOOK_AND_FEEL
argument_list|)
expr_stmt|;
block|}
comment|// FIXME: Open JDK problem
if|if
condition|(
name|UIManager
operator|.
name|getCrossPlatformLookAndFeelClassName
argument_list|()
operator|.
name|equals
argument_list|(
name|lookFeel
argument_list|)
operator|&&
operator|!
name|System
operator|.
name|getProperty
argument_list|(
literal|"java.runtime.name"
argument_list|)
operator|.
name|contains
argument_list|(
literal|"OpenJDK"
argument_list|)
condition|)
block|{
comment|// try to avoid ending up with the ugly Metal L&F
name|UIManager
operator|.
name|setLookAndFeel
argument_list|(
literal|"javax.swing.plaf.nimbus.NimbusLookAndFeel"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
try|try
block|{
name|UIManager
operator|.
name|setLookAndFeel
argument_list|(
name|lookFeel
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ClassNotFoundException
decl||
name|InstantiationException
decl||
name|IllegalAccessException
decl||
name|UnsupportedLookAndFeelException
name|e
parameter_list|)
block|{
comment|// specified look and feel does not exist on the classpath, so use system l&f
name|UIManager
operator|.
name|setLookAndFeel
argument_list|(
name|systemLookFeel
argument_list|)
expr_stmt|;
comment|// also set system l&f as default
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|WIN_LOOK_AND_FEEL
argument_list|,
name|systemLookFeel
argument_list|)
expr_stmt|;
comment|// notify the user
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
literal|null
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Unable to find the requested look and feel and thus the default one is used."
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Warning"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|WARNING_MESSAGE
argument_list|)
expr_stmt|;
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Unable to find requested look and feel"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
comment|// On Linux, Java FX fonts look blurry per default. This can be improved by using a non-default rendering
comment|// setting. See https://github.com/woky/javafx-hates-linux
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
name|FX_FONT_RENDERING_TWEAK
argument_list|)
condition|)
block|{
name|System
operator|.
name|setProperty
argument_list|(
literal|"prism.text"
argument_list|,
literal|"t2k"
argument_list|)
expr_stmt|;
name|System
operator|.
name|setProperty
argument_list|(
literal|"prism.lcdtext"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Look and feel could not be set"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
comment|// In JabRef v2.8, we did it only on NON-Mac. Now, we try on all platforms
name|boolean
name|overrideDefaultFonts
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|OVERRIDE_DEFAULT_FONTS
argument_list|)
decl_stmt|;
if|if
condition|(
name|overrideDefaultFonts
condition|)
block|{
name|int
name|fontSize
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getInt
argument_list|(
name|JabRefPreferences
operator|.
name|MENU_FONT_SIZE
argument_list|)
decl_stmt|;
name|UIDefaults
name|defaults
init|=
name|UIManager
operator|.
name|getDefaults
argument_list|()
decl_stmt|;
name|Enumeration
argument_list|<
name|Object
argument_list|>
name|keys
init|=
name|defaults
operator|.
name|keys
argument_list|()
decl_stmt|;
for|for
control|(
name|Object
name|key
range|:
name|Collections
operator|.
name|list
argument_list|(
name|keys
argument_list|)
control|)
block|{
if|if
condition|(
operator|(
name|key
operator|instanceof
name|String
operator|)
operator|&&
operator|(
operator|(
name|String
operator|)
name|key
operator|)
operator|.
name|endsWith
argument_list|(
literal|".font"
argument_list|)
condition|)
block|{
name|FontUIResource
name|font
init|=
operator|(
name|FontUIResource
operator|)
name|UIManager
operator|.
name|get
argument_list|(
name|key
argument_list|)
decl_stmt|;
name|font
operator|=
operator|new
name|FontUIResource
argument_list|(
name|font
operator|.
name|getName
argument_list|()
argument_list|,
name|font
operator|.
name|getStyle
argument_list|()
argument_list|,
name|fontSize
argument_list|)
expr_stmt|;
name|defaults
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|font
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
DECL|method|getMainFrame ()
specifier|public
specifier|static
name|JabRefFrame
name|getMainFrame
parameter_list|()
block|{
return|return
name|mainFrame
return|;
block|}
comment|// Only used for testing, other than that do NOT set the mainFrame...
DECL|method|setMainFrame (JabRefFrame mainFrame)
specifier|public
specifier|static
name|void
name|setMainFrame
parameter_list|(
name|JabRefFrame
name|mainFrame
parameter_list|)
block|{
name|JabRefGUI
operator|.
name|mainFrame
operator|=
name|mainFrame
expr_stmt|;
block|}
block|}
end_class

end_unit

