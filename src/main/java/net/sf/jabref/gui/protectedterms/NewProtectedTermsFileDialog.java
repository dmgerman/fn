begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.protectedterms
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|protectedterms
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
name|BorderFactory
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
name|JTextField
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
name|gui
operator|.
name|FileDialog
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
name|gui
operator|.
name|JabRefFrame
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
name|gui
operator|.
name|keyboard
operator|.
name|KeyBinding
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
name|logic
operator|.
name|l10n
operator|.
name|Localization
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
name|logic
operator|.
name|protectedterms
operator|.
name|ProtectedTermsLoader
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
name|logic
operator|.
name|util
operator|.
name|FileExtensions
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
DECL|class|NewProtectedTermsFileDialog
specifier|public
class|class
name|NewProtectedTermsFileDialog
extends|extends
name|JDialog
block|{
DECL|field|newFile
specifier|private
specifier|final
name|JTextField
name|newFile
init|=
operator|new
name|JTextField
argument_list|()
decl_stmt|;
DECL|field|newDescription
specifier|private
specifier|final
name|JTextField
name|newDescription
init|=
operator|new
name|JTextField
argument_list|()
decl_stmt|;
DECL|field|enabled
specifier|private
specifier|final
name|JCheckBox
name|enabled
init|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Enabled"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|addOKPressed
specifier|private
name|boolean
name|addOKPressed
decl_stmt|;
DECL|field|loader
specifier|private
specifier|final
name|ProtectedTermsLoader
name|loader
decl_stmt|;
DECL|method|NewProtectedTermsFileDialog (JDialog parent, ProtectedTermsLoader loader)
specifier|public
name|NewProtectedTermsFileDialog
parameter_list|(
name|JDialog
name|parent
parameter_list|,
name|ProtectedTermsLoader
name|loader
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
literal|"New protected terms file"
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|this
operator|.
name|loader
operator|=
name|loader
expr_stmt|;
name|setupDialog
argument_list|()
expr_stmt|;
name|setLocationRelativeTo
argument_list|(
name|parent
argument_list|)
expr_stmt|;
block|}
DECL|method|NewProtectedTermsFileDialog (JabRefFrame mainFrame, ProtectedTermsLoader loader)
specifier|public
name|NewProtectedTermsFileDialog
parameter_list|(
name|JabRefFrame
name|mainFrame
parameter_list|,
name|ProtectedTermsLoader
name|loader
parameter_list|)
block|{
name|super
argument_list|(
name|mainFrame
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"New protected terms file"
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|this
operator|.
name|loader
operator|=
name|loader
expr_stmt|;
name|setupDialog
argument_list|()
expr_stmt|;
name|setLocationRelativeTo
argument_list|(
name|mainFrame
argument_list|)
expr_stmt|;
block|}
DECL|method|setupDialog ()
specifier|private
name|void
name|setupDialog
parameter_list|()
block|{
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
literal|null
argument_list|)
operator|.
name|withExtension
argument_list|(
name|FileExtensions
operator|.
name|TERMS
argument_list|)
decl_stmt|;
name|dialog
operator|.
name|setDefaultExtension
argument_list|(
name|FileExtensions
operator|.
name|TERMS
argument_list|)
expr_stmt|;
name|browse
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
name|Optional
argument_list|<
name|Path
argument_list|>
name|file
init|=
name|dialog
operator|.
name|showDialogAndGetSelectedFile
argument_list|()
decl_stmt|;
name|file
operator|.
name|ifPresent
argument_list|(
name|f
lambda|->
name|newFile
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
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
comment|// Build content panel
name|FormBuilder
name|builder
init|=
name|FormBuilder
operator|.
name|create
argument_list|()
decl_stmt|;
name|builder
operator|.
name|layout
argument_list|(
operator|new
name|FormLayout
argument_list|(
literal|"left:pref, 4dlu, fill:100dlu:grow, 4dlu, pref"
argument_list|,
literal|"p, 4dlu, p, 4dlu, p"
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
literal|"Description"
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
name|newDescription
argument_list|)
operator|.
name|xyw
argument_list|(
literal|3
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"File"
argument_list|)
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|newFile
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|browse
argument_list|)
operator|.
name|xy
argument_list|(
literal|5
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|enabled
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|enabled
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|builder
operator|.
name|padding
argument_list|(
literal|"10dlu, 10dlu, 10dlu, 10dlu"
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|builder
operator|.
name|build
argument_list|()
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
comment|// Buttons
name|ButtonBarBuilder
name|bb
init|=
operator|new
name|ButtonBarBuilder
argument_list|()
decl_stmt|;
name|JButton
name|addOKButton
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
name|JButton
name|addCancelButton
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
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|addOKButton
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|addCancelButton
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
name|addOKButton
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
name|addOKPressed
operator|=
literal|true
expr_stmt|;
name|loader
operator|.
name|addNewProtectedTermsList
argument_list|(
name|newDescription
operator|.
name|getText
argument_list|()
argument_list|,
name|newFile
operator|.
name|getText
argument_list|()
argument_list|,
name|enabled
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|Action
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
name|addOKPressed
operator|=
literal|false
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
decl_stmt|;
name|addCancelButton
operator|.
name|addActionListener
argument_list|(
name|cancelAction
argument_list|)
expr_stmt|;
comment|// Key bindings:
name|bb
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
name|bb
operator|.
name|getPanel
argument_list|()
operator|.
name|getActionMap
argument_list|()
operator|.
name|put
argument_list|(
literal|"close"
argument_list|,
name|cancelAction
argument_list|)
expr_stmt|;
name|pack
argument_list|()
expr_stmt|;
block|}
DECL|method|isOKPressed ()
specifier|public
name|boolean
name|isOKPressed
parameter_list|()
block|{
return|return
name|addOKPressed
return|;
block|}
block|}
end_class

end_unit

