begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.dbproperties
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|dbproperties
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
name|event
operator|.
name|ActionEvent
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
name|ActionListener
import|;
end_import

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
name|AbstractAction
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
name|ButtonGroup
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|DefaultComboBoxModel
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
name|JCheckBox
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JComboBox
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
name|JRadioButton
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JTextField
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
name|FXDialogService
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
name|SaveOrderConfigDisplay
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
name|cleanup
operator|.
name|FieldFormatterCleanupsPanel
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
name|DirectoryDialogConfiguration
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
name|cleanup
operator|.
name|Cleanups
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
name|shared
operator|.
name|DatabaseLocation
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
name|metadata
operator|.
name|MetaData
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
name|metadata
operator|.
name|SaveOrderConfig
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
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|builder
operator|.
name|FormBuilder
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
name|layout
operator|.
name|FormLayout
import|;
end_import

begin_class
DECL|class|DatabasePropertiesDialog
specifier|public
class|class
name|DatabasePropertiesDialog
extends|extends
name|JabRefDialog
block|{
DECL|field|metaData
specifier|private
specifier|final
name|MetaData
name|metaData
decl_stmt|;
DECL|field|panel
specifier|private
specifier|final
name|BasePanel
name|panel
decl_stmt|;
DECL|field|encoding
specifier|private
specifier|final
name|JComboBox
argument_list|<
name|Charset
argument_list|>
name|encoding
decl_stmt|;
DECL|field|ok
specifier|private
specifier|final
name|JButton
name|ok
decl_stmt|;
DECL|field|cancel
specifier|private
specifier|final
name|JButton
name|cancel
decl_stmt|;
DECL|field|fileDir
specifier|private
specifier|final
name|JTextField
name|fileDir
init|=
operator|new
name|JTextField
argument_list|(
literal|40
argument_list|)
decl_stmt|;
DECL|field|fileDirIndv
specifier|private
specifier|final
name|JTextField
name|fileDirIndv
init|=
operator|new
name|JTextField
argument_list|(
literal|40
argument_list|)
decl_stmt|;
DECL|field|oldFileVal
specifier|private
name|String
name|oldFileVal
init|=
literal|""
decl_stmt|;
DECL|field|oldFileIndvVal
specifier|private
name|String
name|oldFileIndvVal
init|=
literal|""
decl_stmt|;
DECL|field|oldSaveOrderConfig
specifier|private
name|SaveOrderConfig
name|oldSaveOrderConfig
decl_stmt|;
comment|/* The code for "Save sort order" is copied from ExportSortingPrefsTab and slightly updated to fit storing at metadata */
DECL|field|saveInOriginalOrder
specifier|private
name|JRadioButton
name|saveInOriginalOrder
decl_stmt|;
DECL|field|saveInSpecifiedOrder
specifier|private
name|JRadioButton
name|saveInSpecifiedOrder
decl_stmt|;
DECL|field|protect
specifier|private
specifier|final
name|JCheckBox
name|protect
init|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Refuse to save the library before external changes have been reviewed."
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|oldProtectVal
specifier|private
name|boolean
name|oldProtectVal
decl_stmt|;
DECL|field|saveOrderPanel
specifier|private
name|SaveOrderConfigDisplay
name|saveOrderPanel
decl_stmt|;
DECL|field|fieldFormatterCleanupsPanel
specifier|private
name|FieldFormatterCleanupsPanel
name|fieldFormatterCleanupsPanel
decl_stmt|;
DECL|method|DatabasePropertiesDialog (BasePanel panel)
specifier|public
name|DatabasePropertiesDialog
parameter_list|(
name|BasePanel
name|panel
parameter_list|)
block|{
name|super
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Library properties"
argument_list|)
argument_list|,
literal|true
argument_list|,
name|DatabasePropertiesDialog
operator|.
name|class
argument_list|)
expr_stmt|;
name|encoding
operator|=
operator|new
name|JComboBox
argument_list|<>
argument_list|()
expr_stmt|;
name|encoding
operator|.
name|setModel
argument_list|(
operator|new
name|DefaultComboBoxModel
argument_list|<>
argument_list|(
name|Encodings
operator|.
name|ENCODINGS
argument_list|)
argument_list|)
expr_stmt|;
name|ok
operator|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"OK"
argument_list|)
argument_list|)
expr_stmt|;
name|cancel
operator|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|panel
operator|=
name|panel
expr_stmt|;
name|this
operator|.
name|metaData
operator|=
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getMetaData
argument_list|()
expr_stmt|;
name|init
argument_list|()
expr_stmt|;
block|}
DECL|method|updateEnableStatus ()
specifier|public
name|void
name|updateEnableStatus
parameter_list|()
block|{
name|DatabaseLocation
name|location
init|=
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getLocation
argument_list|()
decl_stmt|;
name|boolean
name|isShared
init|=
operator|(
name|location
operator|==
name|DatabaseLocation
operator|.
name|SHARED
operator|)
decl_stmt|;
name|encoding
operator|.
name|setEnabled
argument_list|(
operator|!
name|isShared
argument_list|)
expr_stmt|;
comment|// the encoding of shared database is always UTF-8
name|saveInOriginalOrder
operator|.
name|setEnabled
argument_list|(
operator|!
name|isShared
argument_list|)
expr_stmt|;
name|saveInSpecifiedOrder
operator|.
name|setEnabled
argument_list|(
operator|!
name|isShared
argument_list|)
expr_stmt|;
name|saveOrderPanel
operator|.
name|setEnabled
argument_list|(
operator|!
name|isShared
argument_list|)
expr_stmt|;
name|protect
operator|.
name|setEnabled
argument_list|(
operator|!
name|isShared
argument_list|)
expr_stmt|;
block|}
DECL|method|init ()
specifier|private
name|void
name|init
parameter_list|()
block|{
name|DirectoryDialogConfiguration
name|directoryDialogConfiguration
init|=
operator|new
name|DirectoryDialogConfiguration
operator|.
name|Builder
argument_list|()
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
name|DialogService
name|ds
init|=
operator|new
name|FXDialogService
argument_list|()
decl_stmt|;
name|JButton
name|browseFile
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Browse"
argument_list|)
argument_list|)
decl_stmt|;
name|JButton
name|browseFileIndv
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Browse"
argument_list|)
argument_list|)
decl_stmt|;
name|browseFile
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|DefaultTaskExecutor
operator|.
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
name|ds
operator|.
name|showDirectorySelectionDialog
argument_list|(
name|directoryDialogConfiguration
argument_list|)
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|f
lambda|->
name|fileDir
operator|.
name|setText
argument_list|(
name|f
operator|.
name|toAbsolutePath
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|browseFileIndv
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|DefaultTaskExecutor
operator|.
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
name|ds
operator|.
name|showDirectorySelectionDialog
argument_list|(
name|directoryDialogConfiguration
argument_list|)
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|f
lambda|->
name|fileDirIndv
operator|.
name|setText
argument_list|(
name|f
operator|.
name|toAbsolutePath
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|setupSortOrderConfiguration
argument_list|()
expr_stmt|;
name|FormLayout
name|form
init|=
operator|new
name|FormLayout
argument_list|(
literal|"left:pref, 4dlu, pref:grow, 4dlu, pref:grow, 4dlu, pref"
argument_list|,
literal|"pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, fill:pref:grow, 180dlu, fill:pref:grow,"
argument_list|)
decl_stmt|;
name|FormBuilder
name|builder
init|=
name|FormBuilder
operator|.
name|create
argument_list|()
operator|.
name|layout
argument_list|(
name|form
argument_list|)
decl_stmt|;
name|builder
operator|.
name|getPanel
argument_list|()
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Library encoding"
argument_list|)
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|encoding
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|builder
operator|.
name|addSeparator
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Override default file directories"
argument_list|)
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|3
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"General file directory"
argument_list|)
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|fileDir
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|browseFile
argument_list|)
operator|.
name|xy
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"User-specific file directory"
argument_list|)
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|7
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|fileDirIndv
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|7
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|browseFileIndv
argument_list|)
operator|.
name|xy
argument_list|(
literal|5
argument_list|,
literal|7
argument_list|)
expr_stmt|;
name|builder
operator|.
name|addSeparator
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save sort order"
argument_list|)
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|13
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|saveInOriginalOrder
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|15
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|saveInSpecifiedOrder
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|17
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|saveOrderPanel
operator|=
operator|new
name|SaveOrderConfigDisplay
argument_list|()
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|saveOrderPanel
operator|.
name|getPanel
argument_list|()
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|21
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|builder
operator|.
name|addSeparator
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Library protection"
argument_list|)
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|23
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|protect
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|25
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|fieldFormatterCleanupsPanel
operator|=
operator|new
name|FieldFormatterCleanupsPanel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Enable save actions"
argument_list|)
argument_list|,
name|Cleanups
operator|.
name|DEFAULT_SAVE_ACTIONS
argument_list|)
expr_stmt|;
name|builder
operator|.
name|addSeparator
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save actions"
argument_list|)
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|27
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|fieldFormatterCleanupsPanel
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|29
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|ButtonBarBuilder
name|bb
init|=
operator|new
name|ButtonBarBuilder
argument_list|()
decl_stmt|;
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|ok
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|cancel
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addRelatedGap
argument_list|()
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
operator|new
name|HelpAction
argument_list|(
name|HelpFile
operator|.
name|DATABASE_PROPERTIES
argument_list|)
operator|.
name|getHelpButton
argument_list|()
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|bb
operator|.
name|getPanel
argument_list|()
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|)
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|builder
operator|.
name|getPanel
argument_list|()
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
name|bb
operator|.
name|getPanel
argument_list|()
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
name|pack
argument_list|()
expr_stmt|;
name|AbstractAction
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
name|ActionMap
name|am
init|=
name|builder
operator|.
name|getPanel
argument_list|()
operator|.
name|getActionMap
argument_list|()
decl_stmt|;
name|InputMap
name|im
init|=
name|builder
operator|.
name|getPanel
argument_list|()
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
name|ok
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
if|if
condition|(
name|propertiesChanged
argument_list|()
condition|)
block|{
name|storeSettings
argument_list|()
expr_stmt|;
block|}
name|dispose
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|cancel
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|dispose
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|propertiesChanged ()
specifier|private
name|boolean
name|propertiesChanged
parameter_list|()
block|{
name|Charset
name|oldEncoding
init|=
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getMetaData
argument_list|()
operator|.
name|getEncoding
argument_list|()
operator|.
name|orElse
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getDefaultEncoding
argument_list|()
argument_list|)
decl_stmt|;
name|Charset
name|newEncoding
init|=
operator|(
name|Charset
operator|)
name|encoding
operator|.
name|getSelectedItem
argument_list|()
decl_stmt|;
name|boolean
name|saveActionsChanged
init|=
name|fieldFormatterCleanupsPanel
operator|.
name|hasChanged
argument_list|()
decl_stmt|;
name|boolean
name|saveOrderConfigChanged
init|=
operator|!
name|getNewSaveOrderConfig
argument_list|()
operator|.
name|equals
argument_list|(
name|oldSaveOrderConfig
argument_list|)
decl_stmt|;
name|boolean
name|changed
init|=
name|saveOrderConfigChanged
operator|||
operator|!
name|newEncoding
operator|.
name|equals
argument_list|(
name|oldEncoding
argument_list|)
operator|||
operator|!
name|oldFileVal
operator|.
name|equals
argument_list|(
name|fileDir
operator|.
name|getText
argument_list|()
argument_list|)
operator|||
operator|!
name|oldFileIndvVal
operator|.
name|equals
argument_list|(
name|fileDirIndv
operator|.
name|getText
argument_list|()
argument_list|)
operator|||
operator|(
name|oldProtectVal
operator|!=
name|protect
operator|.
name|isSelected
argument_list|()
operator|)
operator|||
name|saveActionsChanged
decl_stmt|;
return|return
name|changed
return|;
block|}
DECL|method|getNewSaveOrderConfig ()
specifier|private
name|SaveOrderConfig
name|getNewSaveOrderConfig
parameter_list|()
block|{
name|SaveOrderConfig
name|saveOrderConfig
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|saveInOriginalOrder
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|saveOrderConfig
operator|=
name|SaveOrderConfig
operator|.
name|getDefaultSaveOrder
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|saveOrderConfig
operator|=
name|saveOrderPanel
operator|.
name|getSaveOrderConfig
argument_list|()
expr_stmt|;
name|saveOrderConfig
operator|.
name|setSaveInSpecifiedOrder
argument_list|()
expr_stmt|;
block|}
return|return
name|saveOrderConfig
return|;
block|}
DECL|method|setupSortOrderConfiguration ()
specifier|private
name|void
name|setupSortOrderConfiguration
parameter_list|()
block|{
name|saveInOriginalOrder
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save entries in their original order"
argument_list|)
argument_list|)
expr_stmt|;
name|saveInSpecifiedOrder
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save entries ordered as specified"
argument_list|)
argument_list|)
expr_stmt|;
name|ButtonGroup
name|bg
init|=
operator|new
name|ButtonGroup
argument_list|()
decl_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|saveInOriginalOrder
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|saveInSpecifiedOrder
argument_list|)
expr_stmt|;
name|ActionListener
name|listener
init|=
name|e
lambda|->
block|{
name|boolean
name|selected
init|=
name|e
operator|.
name|getSource
argument_list|()
operator|==
name|saveInSpecifiedOrder
decl_stmt|;
name|saveOrderPanel
operator|.
name|setEnabled
argument_list|(
name|selected
argument_list|)
expr_stmt|;
block|}
decl_stmt|;
name|saveInOriginalOrder
operator|.
name|addActionListener
argument_list|(
name|listener
argument_list|)
expr_stmt|;
name|saveInSpecifiedOrder
operator|.
name|addActionListener
argument_list|(
name|listener
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|setVisible (boolean visible)
specifier|public
name|void
name|setVisible
parameter_list|(
name|boolean
name|visible
parameter_list|)
block|{
if|if
condition|(
name|visible
condition|)
block|{
name|setValues
argument_list|()
expr_stmt|;
block|}
name|super
operator|.
name|setVisible
argument_list|(
name|visible
argument_list|)
expr_stmt|;
block|}
DECL|method|setValues ()
specifier|private
name|void
name|setValues
parameter_list|()
block|{
name|Optional
argument_list|<
name|Charset
argument_list|>
name|charset
init|=
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getMetaData
argument_list|()
operator|.
name|getEncoding
argument_list|()
decl_stmt|;
name|encoding
operator|.
name|setSelectedItem
argument_list|(
name|charset
operator|.
name|orElse
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getDefaultEncoding
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|Optional
argument_list|<
name|SaveOrderConfig
argument_list|>
name|storedSaveOrderConfig
init|=
name|metaData
operator|.
name|getSaveOrderConfig
argument_list|()
decl_stmt|;
name|boolean
name|selected
decl_stmt|;
if|if
condition|(
operator|!
name|storedSaveOrderConfig
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|saveInOriginalOrder
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|oldSaveOrderConfig
operator|=
name|SaveOrderConfig
operator|.
name|getDefaultSaveOrder
argument_list|()
expr_stmt|;
name|selected
operator|=
literal|false
expr_stmt|;
block|}
else|else
block|{
name|SaveOrderConfig
name|saveOrderConfig
init|=
name|storedSaveOrderConfig
operator|.
name|get
argument_list|()
decl_stmt|;
name|oldSaveOrderConfig
operator|=
name|saveOrderConfig
expr_stmt|;
if|if
condition|(
name|saveOrderConfig
operator|.
name|saveInOriginalOrder
condition|)
block|{
name|saveInOriginalOrder
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|selected
operator|=
literal|false
expr_stmt|;
block|}
else|else
block|{
name|saveInSpecifiedOrder
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|selected
operator|=
literal|true
expr_stmt|;
block|}
name|saveOrderPanel
operator|.
name|setSaveOrderConfig
argument_list|(
name|saveOrderConfig
argument_list|)
expr_stmt|;
block|}
name|saveOrderPanel
operator|.
name|setEnabled
argument_list|(
name|selected
argument_list|)
expr_stmt|;
name|Optional
argument_list|<
name|String
argument_list|>
name|fileD
init|=
name|metaData
operator|.
name|getDefaultFileDirectory
argument_list|()
decl_stmt|;
if|if
condition|(
name|fileD
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|fileDir
operator|.
name|setText
argument_list|(
name|fileD
operator|.
name|get
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|fileDir
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
name|String
name|fileDI
init|=
name|metaData
operator|.
name|getUserFileDirectory
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getUser
argument_list|()
argument_list|)
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
decl_stmt|;
comment|// File dir setting
name|fileDirIndv
operator|.
name|setText
argument_list|(
name|fileDI
argument_list|)
expr_stmt|;
name|oldFileIndvVal
operator|=
name|fileDirIndv
operator|.
name|getText
argument_list|()
expr_stmt|;
name|protect
operator|.
name|setSelected
argument_list|(
name|metaData
operator|.
name|isProtected
argument_list|()
argument_list|)
expr_stmt|;
comment|// Store original values to see if they get changed:
name|oldFileVal
operator|=
name|fileDir
operator|.
name|getText
argument_list|()
expr_stmt|;
name|oldProtectVal
operator|=
name|protect
operator|.
name|isSelected
argument_list|()
expr_stmt|;
comment|//set save actions
name|fieldFormatterCleanupsPanel
operator|.
name|setValues
argument_list|(
name|metaData
argument_list|)
expr_stmt|;
block|}
DECL|method|storeSettings ()
specifier|private
name|void
name|storeSettings
parameter_list|()
block|{
name|Charset
name|oldEncoding
init|=
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getMetaData
argument_list|()
operator|.
name|getEncoding
argument_list|()
operator|.
name|orElse
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getDefaultEncoding
argument_list|()
argument_list|)
decl_stmt|;
name|Charset
name|newEncoding
init|=
operator|(
name|Charset
operator|)
name|encoding
operator|.
name|getSelectedItem
argument_list|()
decl_stmt|;
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getMetaData
argument_list|()
operator|.
name|setEncoding
argument_list|(
name|newEncoding
argument_list|)
expr_stmt|;
name|String
name|text
init|=
name|fileDir
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
decl_stmt|;
if|if
condition|(
name|text
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|metaData
operator|.
name|clearDefaultFileDirectory
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|metaData
operator|.
name|setDefaultFileDirectory
argument_list|(
name|text
argument_list|)
expr_stmt|;
block|}
comment|// Repeat for individual file dir - reuse 'text' and 'dir' vars
name|text
operator|=
name|fileDirIndv
operator|.
name|getText
argument_list|()
expr_stmt|;
if|if
condition|(
name|text
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|metaData
operator|.
name|clearUserFileDirectory
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getUser
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|metaData
operator|.
name|setUserFileDirectory
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getUser
argument_list|()
argument_list|,
name|text
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|protect
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|metaData
operator|.
name|markAsProtected
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|metaData
operator|.
name|markAsNotProtected
argument_list|()
expr_stmt|;
block|}
name|SaveOrderConfig
name|newSaveOrderConfig
init|=
name|getNewSaveOrderConfig
argument_list|()
decl_stmt|;
name|boolean
name|saveOrderConfigChanged
init|=
operator|!
name|getNewSaveOrderConfig
argument_list|()
operator|.
name|equals
argument_list|(
name|oldSaveOrderConfig
argument_list|)
decl_stmt|;
comment|// See if any of the values have been modified:
if|if
condition|(
name|saveOrderConfigChanged
condition|)
block|{
if|if
condition|(
name|newSaveOrderConfig
operator|.
name|equals
argument_list|(
name|SaveOrderConfig
operator|.
name|getDefaultSaveOrder
argument_list|()
argument_list|)
condition|)
block|{
name|metaData
operator|.
name|clearSaveOrderConfig
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|metaData
operator|.
name|setSaveOrderConfig
argument_list|(
name|newSaveOrderConfig
argument_list|)
expr_stmt|;
block|}
block|}
name|boolean
name|saveActionsChanged
init|=
name|fieldFormatterCleanupsPanel
operator|.
name|hasChanged
argument_list|()
decl_stmt|;
if|if
condition|(
name|saveActionsChanged
condition|)
block|{
if|if
condition|(
name|fieldFormatterCleanupsPanel
operator|.
name|isDefaultSaveActions
argument_list|()
condition|)
block|{
name|metaData
operator|.
name|clearSaveActions
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|fieldFormatterCleanupsPanel
operator|.
name|storeSettings
argument_list|(
name|metaData
argument_list|)
expr_stmt|;
block|}
block|}
name|boolean
name|changed
init|=
name|saveOrderConfigChanged
operator|||
operator|!
name|newEncoding
operator|.
name|equals
argument_list|(
name|oldEncoding
argument_list|)
operator|||
operator|!
name|oldFileVal
operator|.
name|equals
argument_list|(
name|fileDir
operator|.
name|getText
argument_list|()
argument_list|)
operator|||
operator|!
name|oldFileIndvVal
operator|.
name|equals
argument_list|(
name|fileDirIndv
operator|.
name|getText
argument_list|()
argument_list|)
operator|||
operator|(
name|oldProtectVal
operator|!=
name|protect
operator|.
name|isSelected
argument_list|()
operator|)
operator|||
name|saveActionsChanged
decl_stmt|;
comment|// ... if so, mark base changed. Prevent the Undo button from removing
comment|// change marking:
if|if
condition|(
name|changed
condition|)
block|{
name|panel
operator|.
name|markNonUndoableBaseChanged
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

