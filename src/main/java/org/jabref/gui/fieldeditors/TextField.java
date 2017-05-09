begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.fieldeditors
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|fieldeditors
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Color
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
name|lang
operator|.
name|reflect
operator|.
name|InvocationTargetException
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
name|JComponent
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
name|javax
operator|.
name|swing
operator|.
name|text
operator|.
name|Document
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
name|CannotRedoException
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
name|CannotUndoException
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
name|actions
operator|.
name|Actions
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
name|autocompleter
operator|.
name|AutoCompleteListener
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
name|component
operator|.
name|JTextFieldWithPlaceholder
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
comment|/**  * An implementation of the FieldEditor backed by a JTextField. Used for single-line input, only BibTex key at the  * moment?!  */
end_comment

begin_class
DECL|class|TextField
specifier|public
class|class
name|TextField
extends|extends
name|JTextFieldWithPlaceholder
implements|implements
name|FieldEditor
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
name|TextField
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|fieldName
specifier|private
specifier|final
name|String
name|fieldName
decl_stmt|;
DECL|field|undo
specifier|private
name|UndoManager
name|undo
decl_stmt|;
DECL|field|autoCompleteListener
specifier|private
name|AutoCompleteListener
name|autoCompleteListener
decl_stmt|;
DECL|method|TextField (String fieldName, String content, boolean changeColorOnFocus)
specifier|public
name|TextField
parameter_list|(
name|String
name|fieldName
parameter_list|,
name|String
name|content
parameter_list|,
name|boolean
name|changeColorOnFocus
parameter_list|)
block|{
name|this
argument_list|(
name|fieldName
argument_list|,
name|content
argument_list|,
name|changeColorOnFocus
argument_list|,
literal|""
argument_list|)
expr_stmt|;
block|}
DECL|method|TextField (String fieldName, String content, boolean changeColorOnFocus, String title)
specifier|public
name|TextField
parameter_list|(
name|String
name|fieldName
parameter_list|,
name|String
name|content
parameter_list|,
name|boolean
name|changeColorOnFocus
parameter_list|,
name|String
name|title
parameter_list|)
block|{
name|super
argument_list|(
name|content
argument_list|,
name|title
argument_list|)
expr_stmt|;
name|setupPasteListener
argument_list|()
expr_stmt|;
name|setupUndoRedo
argument_list|()
expr_stmt|;
name|updateFont
argument_list|()
expr_stmt|;
comment|// Add the global focus listener, so a menu item can see if this field
comment|// was focused when
comment|// an action was called.
name|addFocusListener
argument_list|(
name|Globals
operator|.
name|getFocusListener
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|changeColorOnFocus
condition|)
block|{
name|addFocusListener
argument_list|(
operator|new
name|FieldEditorFocusListener
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|this
operator|.
name|fieldName
operator|=
name|fieldName
expr_stmt|;
name|setBackground
argument_list|(
name|GUIGlobals
operator|.
name|validFieldBackgroundColor
argument_list|)
expr_stmt|;
name|setForeground
argument_list|(
name|GUIGlobals
operator|.
name|editorTextColor
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|setText (String t)
specifier|public
name|void
name|setText
parameter_list|(
name|String
name|t
parameter_list|)
block|{
name|super
operator|.
name|setText
argument_list|(
name|t
argument_list|)
expr_stmt|;
if|if
condition|(
name|undo
operator|!=
literal|null
condition|)
block|{
name|undo
operator|.
name|discardAllEdits
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|append (String text)
specifier|public
name|void
name|append
parameter_list|(
name|String
name|text
parameter_list|)
block|{
name|setText
argument_list|(
name|getText
argument_list|()
operator|+
name|text
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getFieldName ()
specifier|public
name|String
name|getFieldName
parameter_list|()
block|{
return|return
name|fieldName
return|;
block|}
annotation|@
name|Override
DECL|method|getPane ()
specifier|public
name|JComponent
name|getPane
parameter_list|()
block|{
return|return
name|this
return|;
block|}
annotation|@
name|Override
DECL|method|getTextComponent ()
specifier|public
name|JComponent
name|getTextComponent
parameter_list|()
block|{
return|return
name|this
return|;
block|}
annotation|@
name|Override
DECL|method|setActiveBackgroundColor ()
specifier|public
name|void
name|setActiveBackgroundColor
parameter_list|()
block|{
name|setBackgroundColor
argument_list|(
name|GUIGlobals
operator|.
name|activeBackgroundColor
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|setValidBackgroundColor ()
specifier|public
name|void
name|setValidBackgroundColor
parameter_list|()
block|{
name|setBackgroundColor
argument_list|(
name|GUIGlobals
operator|.
name|validFieldBackgroundColor
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|setInvalidBackgroundColor ()
specifier|public
name|void
name|setInvalidBackgroundColor
parameter_list|()
block|{
name|setBackgroundColor
argument_list|(
name|GUIGlobals
operator|.
name|invalidFieldBackgroundColor
argument_list|)
expr_stmt|;
block|}
DECL|method|setBackgroundColor (Color color)
specifier|private
name|void
name|setBackgroundColor
parameter_list|(
name|Color
name|color
parameter_list|)
block|{
if|if
condition|(
name|SwingUtilities
operator|.
name|isEventDispatchThread
argument_list|()
condition|)
block|{
name|setBackground
argument_list|(
name|color
argument_list|)
expr_stmt|;
block|}
else|else
block|{
try|try
block|{
name|SwingUtilities
operator|.
name|invokeAndWait
argument_list|(
parameter_list|()
lambda|->
name|setBackground
argument_list|(
name|color
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InvocationTargetException
decl||
name|InterruptedException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Problem setting background color"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|updateFont ()
specifier|private
name|void
name|updateFont
parameter_list|()
block|{
name|setFont
argument_list|(
name|GUIGlobals
operator|.
name|currentFont
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
comment|// Only replaces selected text if found
DECL|method|paste (String textToInsert)
specifier|public
name|void
name|paste
parameter_list|(
name|String
name|textToInsert
parameter_list|)
block|{
name|replaceSelection
argument_list|(
name|textToInsert
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|undo ()
specifier|public
name|void
name|undo
parameter_list|()
block|{
comment|// Nothing
block|}
annotation|@
name|Override
DECL|method|redo ()
specifier|public
name|void
name|redo
parameter_list|()
block|{
comment|// Nothing
block|}
annotation|@
name|Override
DECL|method|setAutoCompleteListener (AutoCompleteListener listener)
specifier|public
name|void
name|setAutoCompleteListener
parameter_list|(
name|AutoCompleteListener
name|listener
parameter_list|)
block|{
name|autoCompleteListener
operator|=
name|listener
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|clearAutoCompleteSuggestion ()
specifier|public
name|void
name|clearAutoCompleteSuggestion
parameter_list|()
block|{
if|if
condition|(
name|autoCompleteListener
operator|!=
literal|null
condition|)
block|{
name|autoCompleteListener
operator|.
name|clearCurrentSuggestion
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|setupPasteListener ()
specifier|private
name|void
name|setupPasteListener
parameter_list|()
block|{
comment|// Bind paste command to KeyBinds.PASTE
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
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|keyboard
operator|.
name|KeyBinding
operator|.
name|PASTE
argument_list|)
argument_list|,
name|Actions
operator|.
name|PASTE
argument_list|)
expr_stmt|;
block|}
DECL|method|setupUndoRedo ()
specifier|private
name|void
name|setupUndoRedo
parameter_list|()
block|{
name|undo
operator|=
operator|new
name|UndoManager
argument_list|()
expr_stmt|;
name|Document
name|doc
init|=
name|getDocument
argument_list|()
decl_stmt|;
comment|// Listen for undo and redo events
name|doc
operator|.
name|addUndoableEditListener
argument_list|(
name|evt
lambda|->
name|undo
operator|.
name|addEdit
argument_list|(
name|evt
operator|.
name|getEdit
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|// Create an undo action and add it to the text component
name|getActionMap
argument_list|()
operator|.
name|put
argument_list|(
literal|"Undo"
argument_list|,
operator|new
name|AbstractAction
argument_list|(
literal|"Undo"
argument_list|)
block|{
annotation|@
name|Override
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|evt
parameter_list|)
block|{
try|try
block|{
if|if
condition|(
name|undo
operator|.
name|canUndo
argument_list|()
condition|)
block|{
name|undo
operator|.
name|undo
argument_list|()
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|CannotUndoException
name|ignored
parameter_list|)
block|{
comment|// Ignored
block|}
block|}
block|}
argument_list|)
expr_stmt|;
comment|// Bind the undo action to ctl-Z
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
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|keyboard
operator|.
name|KeyBinding
operator|.
name|UNDO
argument_list|)
argument_list|,
literal|"Undo"
argument_list|)
expr_stmt|;
comment|// Create a redo action and add it to the text component
name|getActionMap
argument_list|()
operator|.
name|put
argument_list|(
literal|"Redo"
argument_list|,
operator|new
name|AbstractAction
argument_list|(
name|Actions
operator|.
name|REDO
argument_list|)
block|{
annotation|@
name|Override
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|evt
parameter_list|)
block|{
try|try
block|{
if|if
condition|(
name|undo
operator|.
name|canRedo
argument_list|()
condition|)
block|{
name|undo
operator|.
name|redo
argument_list|()
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|CannotRedoException
name|ignored
parameter_list|)
block|{
comment|// Ignored
block|}
block|}
block|}
argument_list|)
expr_stmt|;
comment|// Bind the redo action to ctl-Y
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
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|keyboard
operator|.
name|KeyBinding
operator|.
name|REDO
argument_list|)
argument_list|,
literal|"Redo"
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

