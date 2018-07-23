begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.importer
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|importer
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|BorderLayout
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Dimension
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionEvent
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
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Path
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
name|java
operator|.
name|util
operator|.
name|zip
operator|.
name|ZipFile
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|AbstractAction
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|Action
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|ActionMap
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|BorderFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|InputMap
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JButton
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JComponent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JFrame
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JPanel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JScrollPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JTable
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|ListSelectionModel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|ScrollPaneConstants
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|table
operator|.
name|AbstractTableModel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|table
operator|.
name|TableColumnModel
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
name|JabRefDialog
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
name|keyboard
operator|.
name|KeyBinding
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
name|gui
operator|.
name|util
operator|.
name|FileDialogConfiguration
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
name|importer
operator|.
name|fileformat
operator|.
name|CustomImporter
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
name|util
operator|.
name|StandardFileType
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
name|jgoodies
operator|.
name|forms
operator|.
name|builder
operator|.
name|ButtonBarBuilder
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

begin_comment
comment|/**  * Dialog to manage custom importers.  */
end_comment

begin_class
DECL|class|ImportCustomizationDialog
specifier|public
class|class
name|ImportCustomizationDialog
extends|extends
name|JabRefDialog
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
name|ImportCustomizationDialog
operator|.
name|class
argument_list|)
decl_stmt|;
comment|// Column widths for import customization dialog table:
DECL|field|COL_0_WIDTH
specifier|private
specifier|static
specifier|final
name|int
name|COL_0_WIDTH
init|=
literal|200
decl_stmt|;
DECL|field|COL_1_WIDTH
specifier|private
specifier|static
specifier|final
name|int
name|COL_1_WIDTH
init|=
literal|80
decl_stmt|;
DECL|field|COL_2_WIDTH
specifier|private
specifier|static
specifier|final
name|int
name|COL_2_WIDTH
init|=
literal|200
decl_stmt|;
DECL|field|COL_3_WIDTH
specifier|private
specifier|static
specifier|final
name|int
name|COL_3_WIDTH
init|=
literal|200
decl_stmt|;
DECL|field|customImporterTable
specifier|private
specifier|final
name|JTable
name|customImporterTable
decl_stmt|;
DECL|method|ImportCustomizationDialog (final JabRefFrame frame)
specifier|public
name|ImportCustomizationDialog
parameter_list|(
specifier|final
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|super
argument_list|(
operator|(
name|JFrame
operator|)
literal|null
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Manage custom imports"
argument_list|)
argument_list|,
literal|false
argument_list|,
name|ImportCustomizationDialog
operator|.
name|class
argument_list|)
expr_stmt|;
name|DialogService
name|dialogService
init|=
name|frame
operator|.
name|getDialogService
argument_list|()
decl_stmt|;
name|ImportTableModel
name|tableModel
init|=
operator|new
name|ImportTableModel
argument_list|()
decl_stmt|;
name|customImporterTable
operator|=
operator|new
name|JTable
argument_list|(
name|tableModel
argument_list|)
expr_stmt|;
name|TableColumnModel
name|cm
init|=
name|customImporterTable
operator|.
name|getColumnModel
argument_list|()
decl_stmt|;
name|cm
operator|.
name|getColumn
argument_list|(
literal|0
argument_list|)
operator|.
name|setPreferredWidth
argument_list|(
name|COL_0_WIDTH
argument_list|)
expr_stmt|;
name|cm
operator|.
name|getColumn
argument_list|(
literal|1
argument_list|)
operator|.
name|setPreferredWidth
argument_list|(
name|COL_1_WIDTH
argument_list|)
expr_stmt|;
name|cm
operator|.
name|getColumn
argument_list|(
literal|2
argument_list|)
operator|.
name|setPreferredWidth
argument_list|(
name|COL_2_WIDTH
argument_list|)
expr_stmt|;
name|cm
operator|.
name|getColumn
argument_list|(
literal|3
argument_list|)
operator|.
name|setPreferredWidth
argument_list|(
name|COL_3_WIDTH
argument_list|)
expr_stmt|;
name|JScrollPane
name|sp
init|=
operator|new
name|JScrollPane
argument_list|(
name|customImporterTable
argument_list|,
name|ScrollPaneConstants
operator|.
name|VERTICAL_SCROLLBAR_AS_NEEDED
argument_list|,
name|ScrollPaneConstants
operator|.
name|HORIZONTAL_SCROLLBAR_NEVER
argument_list|)
decl_stmt|;
name|customImporterTable
operator|.
name|setSelectionMode
argument_list|(
name|ListSelectionModel
operator|.
name|SINGLE_SELECTION
argument_list|)
expr_stmt|;
name|customImporterTable
operator|.
name|setPreferredScrollableViewportSize
argument_list|(
name|getSize
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|customImporterTable
operator|.
name|getRowCount
argument_list|()
operator|>
literal|0
condition|)
block|{
name|customImporterTable
operator|.
name|setRowSelectionInterval
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|)
expr_stmt|;
block|}
name|JButton
name|addFromFolderButton
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Add from folder"
argument_list|)
argument_list|)
decl_stmt|;
name|addFromFolderButton
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
name|FileDialogConfiguration
name|fileDialogConfiguration
init|=
operator|new
name|FileDialogConfiguration
operator|.
name|Builder
argument_list|()
operator|.
name|addExtensionFilter
argument_list|(
name|StandardFileType
operator|.
name|CLASS
argument_list|,
name|StandardFileType
operator|.
name|JAR
argument_list|)
operator|.
name|withDefaultExtension
argument_list|(
name|StandardFileType
operator|.
name|JAR
argument_list|)
operator|.
name|withInitialDirectory
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|WORKING_DIRECTORY
argument_list|)
argument_list|)
operator|.
name|build
argument_list|()
decl_stmt|;
name|Optional
argument_list|<
name|Path
argument_list|>
name|selectedFile
init|=
name|DefaultTaskExecutor
operator|.
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
name|dialogService
operator|.
name|showFileOpenDialog
argument_list|(
name|fileDialogConfiguration
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|selectedFile
operator|.
name|isPresent
argument_list|()
operator|&&
operator|(
name|selectedFile
operator|.
name|get
argument_list|()
operator|.
name|getParent
argument_list|()
operator|!=
literal|null
operator|)
condition|)
block|{
name|String
name|chosenFileStr
init|=
name|selectedFile
operator|.
name|get
argument_list|()
operator|.
name|toString
argument_list|()
decl_stmt|;
try|try
block|{
name|String
name|basePath
init|=
name|selectedFile
operator|.
name|get
argument_list|()
operator|.
name|getParent
argument_list|()
operator|.
name|toString
argument_list|()
decl_stmt|;
name|String
name|className
init|=
name|pathToClass
argument_list|(
name|basePath
argument_list|,
operator|new
name|File
argument_list|(
name|chosenFileStr
argument_list|)
argument_list|)
decl_stmt|;
name|CustomImporter
name|importer
init|=
operator|new
name|CustomImporter
argument_list|(
name|basePath
argument_list|,
name|className
argument_list|)
decl_stmt|;
name|addOrReplaceImporter
argument_list|(
name|importer
argument_list|)
expr_stmt|;
name|customImporterTable
operator|.
name|revalidate
argument_list|()
expr_stmt|;
name|customImporterTable
operator|.
name|repaint
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|exc
parameter_list|)
block|{
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Could not instantiate %0"
argument_list|,
name|chosenFileStr
argument_list|)
argument_list|,
name|exc
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NoClassDefFoundError
name|exc
parameter_list|)
block|{
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Could not instantiate %0. Have you chosen the correct package path?"
argument_list|,
name|chosenFileStr
argument_list|)
argument_list|,
name|exc
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|addFromFolderButton
operator|.
name|setToolTipText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Add a (compiled) custom Importer class from a class path."
argument_list|)
operator|+
literal|"\n"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"The path need not be on the classpath of JabRef."
argument_list|)
argument_list|)
expr_stmt|;
name|JButton
name|addFromJarButton
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Add from JAR"
argument_list|)
argument_list|)
decl_stmt|;
name|addFromJarButton
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
name|FileDialogConfiguration
name|fileDialogConfiguration
init|=
operator|new
name|FileDialogConfiguration
operator|.
name|Builder
argument_list|()
operator|.
name|addExtensionFilter
argument_list|(
name|StandardFileType
operator|.
name|JAR
argument_list|,
name|StandardFileType
operator|.
name|ZIP
argument_list|)
operator|.
name|withDefaultExtension
argument_list|(
name|StandardFileType
operator|.
name|JAR
argument_list|)
operator|.
name|withInitialDirectory
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|WORKING_DIRECTORY
argument_list|)
argument_list|)
operator|.
name|build
argument_list|()
decl_stmt|;
name|Optional
argument_list|<
name|Path
argument_list|>
name|jarZipFile
init|=
name|DefaultTaskExecutor
operator|.
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
name|dialogService
operator|.
name|showFileOpenDialog
argument_list|(
name|fileDialogConfiguration
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|jarZipFile
operator|.
name|isPresent
argument_list|()
condition|)
block|{
try|try
init|(
name|ZipFile
name|zipFile
init|=
operator|new
name|ZipFile
argument_list|(
name|jarZipFile
operator|.
name|get
argument_list|()
operator|.
name|toFile
argument_list|()
argument_list|,
name|ZipFile
operator|.
name|OPEN_READ
argument_list|)
init|)
block|{
name|ZipFileChooser
name|zipFileChooser
init|=
operator|new
name|ZipFileChooser
argument_list|(
name|this
argument_list|,
name|zipFile
argument_list|)
decl_stmt|;
name|zipFileChooser
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|customImporterTable
operator|.
name|revalidate
argument_list|()
expr_stmt|;
name|customImporterTable
operator|.
name|repaint
argument_list|(
literal|10
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|exc
parameter_list|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Could not open ZIP-archive."
argument_list|,
name|exc
argument_list|)
expr_stmt|;
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Could not open %0"
argument_list|,
name|jarZipFile
operator|.
name|get
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
operator|+
literal|"\n"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Have you chosen the correct package path?"
argument_list|)
argument_list|,
name|exc
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NoClassDefFoundError
name|exc
parameter_list|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Could not instantiate ZIP-archive reader."
argument_list|,
name|exc
argument_list|)
expr_stmt|;
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Could not instantiate %0"
argument_list|,
name|jarZipFile
operator|.
name|get
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
operator|+
literal|"\n"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Have you chosen the correct package path?"
argument_list|)
argument_list|,
name|exc
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|addFromJarButton
operator|.
name|setToolTipText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Add a (compiled) custom Importer class from a ZIP-archive."
argument_list|)
operator|+
literal|"\n"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"The ZIP-archive need not be on the classpath of JabRef."
argument_list|)
argument_list|)
expr_stmt|;
name|JButton
name|showDescButton
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Show description"
argument_list|)
argument_list|)
decl_stmt|;
name|showDescButton
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
name|int
name|row
init|=
name|customImporterTable
operator|.
name|getSelectedRow
argument_list|()
decl_stmt|;
if|if
condition|(
name|row
operator|==
operator|-
literal|1
condition|)
block|{
name|dialogService
operator|.
name|showWarningDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Manage custom imports"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Please select an importer."
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|CustomImporter
name|importer
init|=
operator|(
operator|(
name|ImportTableModel
operator|)
name|customImporterTable
operator|.
name|getModel
argument_list|()
operator|)
operator|.
name|getImporter
argument_list|(
name|row
argument_list|)
decl_stmt|;
name|dialogService
operator|.
name|showWarningDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Manage custom imports"
argument_list|)
argument_list|,
name|importer
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|JButton
name|removeButton
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remove"
argument_list|)
argument_list|)
decl_stmt|;
name|removeButton
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
name|int
name|row
init|=
name|customImporterTable
operator|.
name|getSelectedRow
argument_list|()
decl_stmt|;
if|if
condition|(
name|row
operator|==
operator|-
literal|1
condition|)
block|{
name|dialogService
operator|.
name|showWarningDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Manage custom imports"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Please select an importer."
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|customImporterTable
operator|.
name|removeRowSelectionInterval
argument_list|(
name|row
argument_list|,
name|row
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|customImports
operator|.
name|remove
argument_list|(
operator|(
operator|(
name|ImportTableModel
operator|)
name|customImporterTable
operator|.
name|getModel
argument_list|()
operator|)
operator|.
name|getImporter
argument_list|(
name|row
argument_list|)
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|IMPORT_FORMAT_READER
operator|.
name|resetImportFormats
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getImportFormatPreferences
argument_list|()
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getXMPPreferences
argument_list|()
argument_list|,
name|Globals
operator|.
name|getFileUpdateMonitor
argument_list|()
argument_list|)
expr_stmt|;
name|customImporterTable
operator|.
name|revalidate
argument_list|()
expr_stmt|;
name|customImporterTable
operator|.
name|repaint
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|Action
name|closeAction
init|=
operator|new
name|AbstractAction
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
decl_stmt|;
name|JButton
name|closeButton
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Close"
argument_list|)
argument_list|)
decl_stmt|;
name|closeButton
operator|.
name|addActionListener
argument_list|(
name|closeAction
argument_list|)
expr_stmt|;
name|JButton
name|helpButton
init|=
operator|new
name|HelpAction
argument_list|(
name|HelpFile
operator|.
name|CUSTOM_IMPORTS
argument_list|)
operator|.
name|getHelpButton
argument_list|()
decl_stmt|;
comment|// Key bindings:
name|JPanel
name|mainPanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|ActionMap
name|am
init|=
name|mainPanel
operator|.
name|getActionMap
argument_list|()
decl_stmt|;
name|InputMap
name|im
init|=
name|mainPanel
operator|.
name|getInputMap
argument_list|(
name|JComponent
operator|.
name|WHEN_IN_FOCUSED_WINDOW
argument_list|)
decl_stmt|;
name|im
operator|.
name|put
argument_list|(
name|Globals
operator|.
name|getKeyPrefs
argument_list|()
operator|.
name|getKey
argument_list|(
name|KeyBinding
operator|.
name|CLOSE
argument_list|)
argument_list|,
literal|"close"
argument_list|)
expr_stmt|;
name|am
operator|.
name|put
argument_list|(
literal|"close"
argument_list|,
name|closeAction
argument_list|)
expr_stmt|;
name|mainPanel
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|mainPanel
operator|.
name|add
argument_list|(
name|sp
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|JPanel
name|buttons
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|ButtonBarBuilder
name|bb
init|=
operator|new
name|ButtonBarBuilder
argument_list|(
name|buttons
argument_list|)
decl_stmt|;
name|buttons
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|2
argument_list|,
literal|2
argument_list|,
literal|2
argument_list|,
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|addFromFolderButton
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|addFromJarButton
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|showDescButton
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|removeButton
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|closeButton
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addUnrelatedGap
argument_list|()
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|helpButton
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|mainPanel
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|buttons
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
name|this
operator|.
name|setSize
argument_list|(
name|getSize
argument_list|()
argument_list|)
expr_stmt|;
name|pack
argument_list|()
expr_stmt|;
name|customImporterTable
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
block|}
comment|/*     *  (non-Javadoc)     * @see java.awt.Component#getSize()     */
annotation|@
name|Override
DECL|method|getSize ()
specifier|public
name|Dimension
name|getSize
parameter_list|()
block|{
name|int
name|width
init|=
name|COL_0_WIDTH
operator|+
name|COL_1_WIDTH
operator|+
name|COL_2_WIDTH
operator|+
name|COL_3_WIDTH
decl_stmt|;
return|return
operator|new
name|Dimension
argument_list|(
name|width
argument_list|,
name|width
operator|/
literal|2
argument_list|)
return|;
block|}
comment|/**      * Converts a path relative to a base-path into a class name.      *      * @param basePath  base path      * @param path  path that includes base-path as a prefix      * @return  class name      */
DECL|method|pathToClass (String basePath, File path)
specifier|private
specifier|static
name|String
name|pathToClass
parameter_list|(
name|String
name|basePath
parameter_list|,
name|File
name|path
parameter_list|)
block|{
name|String
name|className
init|=
literal|null
decl_stmt|;
name|File
name|actualPath
init|=
name|path
decl_stmt|;
comment|// remove leading basepath from path
while|while
condition|(
operator|!
name|actualPath
operator|.
name|equals
argument_list|(
operator|new
name|File
argument_list|(
name|basePath
argument_list|)
argument_list|)
condition|)
block|{
name|className
operator|=
name|actualPath
operator|.
name|getName
argument_list|()
operator|+
operator|(
name|className
operator|==
literal|null
condition|?
literal|""
else|:
literal|"."
operator|+
name|className
operator|)
expr_stmt|;
name|actualPath
operator|=
name|actualPath
operator|.
name|getParentFile
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|className
operator|!=
literal|null
condition|)
block|{
name|int
name|lastDot
init|=
name|className
operator|.
name|lastIndexOf
argument_list|(
literal|'.'
argument_list|)
decl_stmt|;
if|if
condition|(
name|lastDot
operator|<
literal|0
condition|)
block|{
return|return
name|className
return|;
block|}
name|className
operator|=
name|className
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|lastDot
argument_list|)
expr_stmt|;
block|}
return|return
name|className
return|;
block|}
comment|/**      * Adds an importer to the model that underlies the custom importers.      *      * @param importer  importer      */
DECL|method|addOrReplaceImporter (CustomImporter importer)
specifier|public
name|void
name|addOrReplaceImporter
parameter_list|(
name|CustomImporter
name|importer
parameter_list|)
block|{
name|Globals
operator|.
name|prefs
operator|.
name|customImports
operator|.
name|replaceImporter
argument_list|(
name|importer
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|IMPORT_FORMAT_READER
operator|.
name|resetImportFormats
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getImportFormatPreferences
argument_list|()
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getXMPPreferences
argument_list|()
argument_list|,
name|Globals
operator|.
name|getFileUpdateMonitor
argument_list|()
argument_list|)
expr_stmt|;
operator|(
operator|(
name|ImportTableModel
operator|)
name|customImporterTable
operator|.
name|getModel
argument_list|()
operator|)
operator|.
name|fireTableDataChanged
argument_list|()
expr_stmt|;
block|}
comment|/**      * Table model for the custom importer table.      */
DECL|class|ImportTableModel
specifier|private
class|class
name|ImportTableModel
extends|extends
name|AbstractTableModel
block|{
DECL|field|columnNames
specifier|private
specifier|final
name|String
index|[]
name|columnNames
init|=
operator|new
name|String
index|[]
block|{
name|Localization
operator|.
name|lang
argument_list|(
literal|"Import name"
argument_list|)
block|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Command line id"
argument_list|)
block|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Importer class"
argument_list|)
block|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Contained in"
argument_list|)
block|}
decl_stmt|;
annotation|@
name|Override
DECL|method|getValueAt (int rowIndex, int columnIndex)
specifier|public
name|Object
name|getValueAt
parameter_list|(
name|int
name|rowIndex
parameter_list|,
name|int
name|columnIndex
parameter_list|)
block|{
name|Object
name|value
init|=
literal|null
decl_stmt|;
name|CustomImporter
name|importer
init|=
name|getImporter
argument_list|(
name|rowIndex
argument_list|)
decl_stmt|;
if|if
condition|(
name|columnIndex
operator|==
literal|0
condition|)
block|{
name|value
operator|=
name|importer
operator|.
name|getName
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|columnIndex
operator|==
literal|1
condition|)
block|{
name|value
operator|=
name|importer
operator|.
name|getName
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|columnIndex
operator|==
literal|2
condition|)
block|{
name|value
operator|=
name|importer
operator|.
name|getClassName
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|columnIndex
operator|==
literal|3
condition|)
block|{
name|value
operator|=
name|importer
operator|.
name|getBasePath
argument_list|()
expr_stmt|;
block|}
return|return
name|value
return|;
block|}
annotation|@
name|Override
DECL|method|getColumnCount ()
specifier|public
name|int
name|getColumnCount
parameter_list|()
block|{
return|return
name|columnNames
operator|.
name|length
return|;
block|}
annotation|@
name|Override
DECL|method|getRowCount ()
specifier|public
name|int
name|getRowCount
parameter_list|()
block|{
return|return
name|Globals
operator|.
name|prefs
operator|.
name|customImports
operator|.
name|size
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|getColumnName (int col)
specifier|public
name|String
name|getColumnName
parameter_list|(
name|int
name|col
parameter_list|)
block|{
return|return
name|columnNames
index|[
name|col
index|]
return|;
block|}
DECL|method|getImporter (int rowIndex)
specifier|public
name|CustomImporter
name|getImporter
parameter_list|(
name|int
name|rowIndex
parameter_list|)
block|{
name|CustomImporter
index|[]
name|importers
init|=
name|Globals
operator|.
name|prefs
operator|.
name|customImports
operator|.
name|toArray
argument_list|(
operator|new
name|CustomImporter
index|[
name|Globals
operator|.
name|prefs
operator|.
name|customImports
operator|.
name|size
argument_list|()
index|]
argument_list|)
decl_stmt|;
return|return
name|importers
index|[
name|rowIndex
index|]
return|;
block|}
block|}
block|}
end_class

end_unit

