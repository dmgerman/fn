begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.exporter
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|exporter
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
name|awt
operator|.
name|Insets
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
name|file
operator|.
name|Path
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
name|Paths
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
name|JDialog
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JLabel
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
name|FileDialog
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
name|FileExtensions
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

begin_comment
comment|/**  * Dialog for creating or modifying custom exports.  */
end_comment

begin_class
DECL|class|CustomExportDialog
class|class
name|CustomExportDialog
extends|extends
name|JDialog
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
name|CustomExportDialog
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|name
specifier|private
specifier|final
name|JTextField
name|name
init|=
operator|new
name|JTextField
argument_list|(
literal|60
argument_list|)
decl_stmt|;
DECL|field|layoutFile
specifier|private
specifier|final
name|JTextField
name|layoutFile
init|=
operator|new
name|JTextField
argument_list|(
literal|60
argument_list|)
decl_stmt|;
DECL|field|extension
specifier|private
specifier|final
name|JTextField
name|extension
init|=
operator|new
name|JTextField
argument_list|(
literal|60
argument_list|)
decl_stmt|;
DECL|field|frame
specifier|private
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|okPressed
specifier|private
name|boolean
name|okPressed
decl_stmt|;
DECL|method|CustomExportDialog (final JabRefFrame parent, final String exporterName, final String layoutFileName, final String extensionName)
specifier|public
name|CustomExportDialog
parameter_list|(
specifier|final
name|JabRefFrame
name|parent
parameter_list|,
specifier|final
name|String
name|exporterName
parameter_list|,
specifier|final
name|String
name|layoutFileName
parameter_list|,
specifier|final
name|String
name|extensionName
parameter_list|)
block|{
name|this
argument_list|(
name|parent
argument_list|)
expr_stmt|;
name|frame
operator|=
name|parent
expr_stmt|;
name|name
operator|.
name|setText
argument_list|(
name|exporterName
argument_list|)
expr_stmt|;
name|layoutFile
operator|.
name|setText
argument_list|(
name|layoutFileName
argument_list|)
expr_stmt|;
name|extension
operator|.
name|setText
argument_list|(
name|extensionName
argument_list|)
expr_stmt|;
block|}
DECL|method|CustomExportDialog (final JabRefFrame parent)
specifier|public
name|CustomExportDialog
parameter_list|(
specifier|final
name|JabRefFrame
name|parent
parameter_list|)
block|{
name|super
argument_list|(
name|parent
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Edit custom export"
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|frame
operator|=
name|parent
expr_stmt|;
name|ActionListener
name|okListener
init|=
name|e
lambda|->
block|{
name|Path
name|layoutFileDir
init|=
name|Paths
operator|.
name|get
argument_list|(
name|layoutFile
operator|.
name|getText
argument_list|()
argument_list|)
operator|.
name|getParent
argument_list|()
decl_stmt|;
if|if
condition|(
name|layoutFileDir
operator|!=
literal|null
condition|)
block|{
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_WORKING_DIRECTORY
argument_list|,
name|layoutFileDir
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// Check that there are no empty strings.
if|if
condition|(
name|layoutFile
operator|.
name|getText
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|||
name|name
operator|.
name|getText
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|||
name|extension
operator|.
name|getText
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|||
operator|!
name|layoutFile
operator|.
name|getText
argument_list|()
operator|.
name|endsWith
argument_list|(
literal|".layout"
argument_list|)
condition|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"One field is empty!"
argument_list|)
expr_stmt|;
comment|//TODO: Better error message
return|return;
block|}
comment|// Handling of : and ; must also be done.
name|okPressed
operator|=
literal|true
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
decl_stmt|;
name|layoutFile
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_WORKING_DIRECTORY
argument_list|)
argument_list|)
expr_stmt|;
name|JButton
name|ok
init|=
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
decl_stmt|;
name|ok
operator|.
name|addActionListener
argument_list|(
name|okListener
argument_list|)
expr_stmt|;
name|name
operator|.
name|addActionListener
argument_list|(
name|okListener
argument_list|)
expr_stmt|;
name|layoutFile
operator|.
name|addActionListener
argument_list|(
name|okListener
argument_list|)
expr_stmt|;
name|extension
operator|.
name|addActionListener
argument_list|(
name|okListener
argument_list|)
expr_stmt|;
name|JButton
name|cancel
init|=
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
decl_stmt|;
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
name|JButton
name|browse
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
name|FileDialog
name|dialog
init|=
operator|new
name|FileDialog
argument_list|(
name|frame
argument_list|)
operator|.
name|withExtension
argument_list|(
name|FileExtensions
operator|.
name|LAYOUT
argument_list|)
decl_stmt|;
name|dialog
operator|.
name|setDefaultExtension
argument_list|(
name|FileExtensions
operator|.
name|LAYOUT
argument_list|)
expr_stmt|;
name|browse
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|dialog
operator|.
name|showDialogAndGetSelectedFile
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|f
lambda|->
name|layoutFile
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
name|AbstractAction
name|cancelAction
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
comment|// Key bindings:
name|JPanel
name|main
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|ActionMap
name|am
init|=
name|main
operator|.
name|getActionMap
argument_list|()
decl_stmt|;
name|InputMap
name|im
init|=
name|main
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
name|CLOSE_DIALOG
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
name|cancelAction
argument_list|)
expr_stmt|;
comment|// Layout starts here.
name|GridBagLayout
name|gbl
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
name|main
operator|.
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|main
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createTitledBorder
argument_list|(
name|BorderFactory
operator|.
name|createEtchedBorder
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Export properties"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|// Main panel:
name|GridBagConstraints
name|con
init|=
operator|new
name|GridBagConstraints
argument_list|()
decl_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|3
argument_list|,
literal|5
argument_list|,
literal|3
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|con
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|EAST
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|NONE
expr_stmt|;
name|con
operator|.
name|gridx
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
literal|0
expr_stmt|;
name|JLabel
name|nl
init|=
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Export name"
argument_list|)
operator|+
literal|':'
argument_list|)
decl_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|nl
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|nl
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
literal|1
expr_stmt|;
name|JLabel
name|nr
init|=
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Main layout file"
argument_list|)
operator|+
literal|':'
argument_list|)
decl_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|nr
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|nr
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
literal|2
expr_stmt|;
name|JLabel
name|nf
init|=
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Extension"
argument_list|)
operator|+
literal|':'
argument_list|)
decl_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|nf
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|nf
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|2
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|WEST
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|HORIZONTAL
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|gridx
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|name
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|layoutFile
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|layoutFile
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridx
operator|=
literal|2
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|0
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|browse
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|browse
argument_list|)
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|2
expr_stmt|;
name|con
operator|.
name|gridx
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
literal|2
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|extension
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|extension
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
name|addGlue
argument_list|()
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|main
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
name|setSize
argument_list|(
literal|700
argument_list|,
literal|200
argument_list|)
expr_stmt|;
name|setLocationRelativeTo
argument_list|(
name|parent
argument_list|)
expr_stmt|;
block|}
DECL|method|okPressed ()
specifier|public
name|boolean
name|okPressed
parameter_list|()
block|{
return|return
name|okPressed
return|;
block|}
DECL|method|layoutFile ()
specifier|public
name|String
name|layoutFile
parameter_list|()
block|{
return|return
name|layoutFile
operator|.
name|getText
argument_list|()
return|;
block|}
DECL|method|name ()
specifier|public
name|String
name|name
parameter_list|()
block|{
return|return
name|name
operator|.
name|getText
argument_list|()
return|;
block|}
DECL|method|extension ()
specifier|public
name|String
name|extension
parameter_list|()
block|{
name|String
name|ext
init|=
name|extension
operator|.
name|getText
argument_list|()
decl_stmt|;
if|if
condition|(
name|ext
operator|.
name|startsWith
argument_list|(
literal|"."
argument_list|)
condition|)
block|{
return|return
name|ext
return|;
block|}
elseif|else
if|if
condition|(
name|ext
operator|.
name|startsWith
argument_list|(
literal|"*."
argument_list|)
condition|)
block|{
return|return
name|ext
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
return|;
block|}
else|else
block|{
return|return
literal|'.'
operator|+
name|ext
return|;
block|}
block|}
block|}
end_class

end_unit
