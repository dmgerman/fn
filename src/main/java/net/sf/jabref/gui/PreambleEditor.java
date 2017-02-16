begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
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
name|Component
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Container
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
name|FocusAdapter
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
name|FocusEvent
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
name|WindowAdapter
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
name|WindowEvent
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
name|JDialog
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
name|LayoutFocusTraversalPolicy
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|text
operator|.
name|JTextComponent
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|actions
operator|.
name|Actions
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
name|fieldeditors
operator|.
name|FieldEditor
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
name|fieldeditors
operator|.
name|TextArea
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
name|gui
operator|.
name|undo
operator|.
name|UndoablePreambleChange
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
name|util
operator|.
name|WindowLocation
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_class
DECL|class|PreambleEditor
class|class
name|PreambleEditor
extends|extends
name|JDialog
block|{
comment|// A reference to the entry this object works on.
DECL|field|database
specifier|private
specifier|final
name|BibDatabase
name|database
decl_stmt|;
DECL|field|panel
specifier|private
specifier|final
name|BasePanel
name|panel
decl_stmt|;
DECL|field|editor
specifier|private
specifier|final
name|FieldEditor
name|editor
decl_stmt|;
DECL|field|undoAction
specifier|private
specifier|final
name|UndoAction
name|undoAction
init|=
operator|new
name|UndoAction
argument_list|()
decl_stmt|;
DECL|field|storeFieldAction
specifier|private
specifier|final
name|StoreFieldAction
name|storeFieldAction
init|=
operator|new
name|StoreFieldAction
argument_list|()
decl_stmt|;
DECL|field|redoAction
specifier|private
specifier|final
name|RedoAction
name|redoAction
init|=
operator|new
name|RedoAction
argument_list|()
decl_stmt|;
comment|// The action concerned with closing the window.
DECL|field|closeAction
specifier|private
specifier|final
name|CloseAction
name|closeAction
init|=
operator|new
name|CloseAction
argument_list|()
decl_stmt|;
DECL|method|PreambleEditor (JabRefFrame baseFrame, BasePanel panel, BibDatabase database)
specifier|public
name|PreambleEditor
parameter_list|(
name|JabRefFrame
name|baseFrame
parameter_list|,
name|BasePanel
name|panel
parameter_list|,
name|BibDatabase
name|database
parameter_list|)
block|{
name|super
argument_list|(
name|baseFrame
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
name|database
operator|=
name|database
expr_stmt|;
name|addWindowListener
argument_list|(
operator|new
name|WindowAdapter
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|windowClosing
parameter_list|(
name|WindowEvent
name|e
parameter_list|)
block|{
name|closeAction
operator|.
name|actionPerformed
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|windowOpened
parameter_list|(
name|WindowEvent
name|e
parameter_list|)
block|{
name|editor
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|setFocusTraversalPolicy
argument_list|(
operator|new
name|LayoutFocusTraversalPolicy
argument_list|()
block|{
annotation|@
name|Override
specifier|protected
name|boolean
name|accept
parameter_list|(
name|Component
name|c
parameter_list|)
block|{
return|return
name|super
operator|.
name|accept
argument_list|(
name|c
argument_list|)
operator|&&
operator|(
name|c
operator|instanceof
name|FieldEditor
operator|)
return|;
block|}
block|}
argument_list|)
expr_stmt|;
name|JPanel
name|pan
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|GridBagLayout
name|gbl
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
name|pan
operator|.
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|GridBagConstraints
name|con
init|=
operator|new
name|GridBagConstraints
argument_list|()
decl_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|BOTH
expr_stmt|;
name|con
operator|.
name|weighty
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
literal|10
argument_list|,
literal|5
argument_list|,
literal|10
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|editor
operator|=
operator|new
name|TextArea
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Preamble"
argument_list|)
argument_list|,
name|database
operator|.
name|getPreamble
argument_list|()
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|setupJTextComponent
argument_list|(
operator|(
name|TextArea
operator|)
name|editor
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|editor
operator|.
name|getLabel
argument_list|()
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|pan
operator|.
name|add
argument_list|(
name|editor
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|editor
operator|.
name|getPane
argument_list|()
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|pan
operator|.
name|add
argument_list|(
name|editor
operator|.
name|getPane
argument_list|()
argument_list|)
expr_stmt|;
name|Container
name|conPane
init|=
name|getContentPane
argument_list|()
decl_stmt|;
name|conPane
operator|.
name|add
argument_list|(
name|pan
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|setTitle
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Edit preamble"
argument_list|)
argument_list|)
expr_stmt|;
name|WindowLocation
name|pw
init|=
operator|new
name|WindowLocation
argument_list|(
name|this
argument_list|,
name|JabRefPreferences
operator|.
name|PREAMBLE_POS_X
argument_list|,
name|JabRefPreferences
operator|.
name|PREAMBLE_POS_Y
argument_list|,
name|JabRefPreferences
operator|.
name|PREAMBLE_SIZE_X
argument_list|,
name|JabRefPreferences
operator|.
name|PREAMBLE_SIZE_Y
argument_list|)
decl_stmt|;
name|pw
operator|.
name|displayWindowAtStoredLocation
argument_list|()
expr_stmt|;
block|}
DECL|method|setupJTextComponent (JTextComponent ta)
specifier|private
name|void
name|setupJTextComponent
parameter_list|(
name|JTextComponent
name|ta
parameter_list|)
block|{
comment|// Set up key bindings and focus listener for the FieldEditor.
name|ta
operator|.
name|getInputMap
argument_list|()
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
name|ta
operator|.
name|getActionMap
argument_list|()
operator|.
name|put
argument_list|(
literal|"close"
argument_list|,
name|closeAction
argument_list|)
expr_stmt|;
name|ta
operator|.
name|getInputMap
argument_list|()
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
name|PREAMBLE_EDITOR_STORE_CHANGES
argument_list|)
argument_list|,
literal|"store"
argument_list|)
expr_stmt|;
name|ta
operator|.
name|getActionMap
argument_list|()
operator|.
name|put
argument_list|(
literal|"store"
argument_list|,
name|storeFieldAction
argument_list|)
expr_stmt|;
name|ta
operator|.
name|getInputMap
argument_list|()
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
name|UNDO
argument_list|)
argument_list|,
literal|"undo"
argument_list|)
expr_stmt|;
name|ta
operator|.
name|getActionMap
argument_list|()
operator|.
name|put
argument_list|(
name|Actions
operator|.
name|UNDO
argument_list|,
name|undoAction
argument_list|)
expr_stmt|;
name|ta
operator|.
name|getInputMap
argument_list|()
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
name|REDO
argument_list|)
argument_list|,
literal|"redo"
argument_list|)
expr_stmt|;
name|ta
operator|.
name|getActionMap
argument_list|()
operator|.
name|put
argument_list|(
name|Actions
operator|.
name|REDO
argument_list|,
name|redoAction
argument_list|)
expr_stmt|;
name|ta
operator|.
name|addFocusListener
argument_list|(
operator|new
name|FieldListener
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|updatePreamble ()
specifier|public
name|void
name|updatePreamble
parameter_list|()
block|{
name|editor
operator|.
name|setText
argument_list|(
name|database
operator|.
name|getPreamble
argument_list|()
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|class|FieldListener
specifier|private
class|class
name|FieldListener
extends|extends
name|FocusAdapter
block|{
comment|/*          * Focus listener that fires the storeFieldAction when a TextArea          * loses focus.          */
annotation|@
name|Override
DECL|method|focusLost (FocusEvent e)
specifier|public
name|void
name|focusLost
parameter_list|(
name|FocusEvent
name|e
parameter_list|)
block|{
if|if
condition|(
operator|!
name|e
operator|.
name|isTemporary
argument_list|()
condition|)
block|{
name|storeFieldAction
operator|.
name|actionPerformed
argument_list|(
operator|new
name|ActionEvent
argument_list|(
name|e
operator|.
name|getSource
argument_list|()
argument_list|,
literal|0
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|class|StoreFieldAction
class|class
name|StoreFieldAction
extends|extends
name|AbstractAction
block|{
DECL|method|StoreFieldAction ()
specifier|public
name|StoreFieldAction
parameter_list|()
block|{
name|super
argument_list|(
literal|"Store field value"
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|SHORT_DESCRIPTION
argument_list|,
literal|"Store field value"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|String
name|toSet
init|=
name|editor
operator|.
name|getText
argument_list|()
decl_stmt|;
comment|// We check if the field has changed, since we don't want to mark the
comment|// base as changed unless we have a real change.
if|if
condition|(
operator|!
name|database
operator|.
name|getPreamble
argument_list|()
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
operator|.
name|equals
argument_list|(
name|toSet
argument_list|)
condition|)
block|{
name|panel
operator|.
name|getUndoManager
argument_list|()
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoablePreambleChange
argument_list|(
name|database
argument_list|,
name|panel
argument_list|,
name|database
operator|.
name|getPreamble
argument_list|()
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
argument_list|,
name|toSet
argument_list|)
argument_list|)
expr_stmt|;
name|database
operator|.
name|setPreamble
argument_list|(
name|toSet
argument_list|)
expr_stmt|;
if|if
condition|(
operator|(
name|toSet
operator|==
literal|null
operator|)
operator|||
name|toSet
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|editor
operator|.
name|setLabelColor
argument_list|(
name|GUIGlobals
operator|.
name|NULL_FIELD_COLOR
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|editor
operator|.
name|setLabelColor
argument_list|(
name|GUIGlobals
operator|.
name|ENTRY_EDITOR_LABEL_COLOR
argument_list|)
expr_stmt|;
block|}
name|editor
operator|.
name|setValidBackgroundColor
argument_list|()
expr_stmt|;
if|if
condition|(
name|editor
operator|.
name|getTextComponent
argument_list|()
operator|.
name|hasFocus
argument_list|()
condition|)
block|{
name|editor
operator|.
name|setActiveBackgroundColor
argument_list|()
expr_stmt|;
block|}
name|panel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
block|}
block|}
block|}
DECL|class|UndoAction
class|class
name|UndoAction
extends|extends
name|AbstractAction
block|{
DECL|method|UndoAction ()
specifier|public
name|UndoAction
parameter_list|()
block|{
name|super
argument_list|(
literal|"Undo"
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|UNDO
operator|.
name|getIcon
argument_list|()
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|SHORT_DESCRIPTION
argument_list|,
literal|"Undo"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|panel
operator|.
name|runCommand
argument_list|(
name|Actions
operator|.
name|UNDO
argument_list|)
expr_stmt|;
block|}
block|}
DECL|class|RedoAction
class|class
name|RedoAction
extends|extends
name|AbstractAction
block|{
DECL|method|RedoAction ()
specifier|public
name|RedoAction
parameter_list|()
block|{
name|super
argument_list|(
literal|"Redo"
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|REDO
operator|.
name|getIcon
argument_list|()
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|SHORT_DESCRIPTION
argument_list|,
literal|"Redo"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|panel
operator|.
name|runCommand
argument_list|(
name|Actions
operator|.
name|REDO
argument_list|)
expr_stmt|;
block|}
block|}
DECL|class|CloseAction
class|class
name|CloseAction
extends|extends
name|AbstractAction
block|{
DECL|method|CloseAction ()
specifier|public
name|CloseAction
parameter_list|()
block|{
name|super
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Close window"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|storeFieldAction
operator|.
name|actionPerformed
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|panel
operator|.
name|preambleEditorClosing
argument_list|()
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|getFieldEditor ()
specifier|public
name|FieldEditor
name|getFieldEditor
parameter_list|()
block|{
return|return
name|editor
return|;
block|}
DECL|method|storeCurrentEdit ()
specifier|public
name|void
name|storeCurrentEdit
parameter_list|()
block|{
name|storeFieldAction
operator|.
name|actionPerformed
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

