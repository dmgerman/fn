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
name|event
operator|.
name|ActionEvent
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
name|regex
operator|.
name|Matcher
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
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
name|KeyStroke
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
name|BadLocationException
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
name|DefaultHighlighter
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
name|text
operator|.
name|Highlighter
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
name|Keymap
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
name|util
operator|.
name|component
operator|.
name|JTextAreaWithPlaceholder
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
name|search
operator|.
name|SearchQueryHighlightListener
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

begin_class
DECL|class|JTextAreaWithHighlighting
specifier|public
class|class
name|JTextAreaWithHighlighting
extends|extends
name|JTextAreaWithPlaceholder
implements|implements
name|SearchQueryHighlightListener
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
name|JTextAreaWithHighlighting
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|highlightPattern
specifier|private
name|Optional
argument_list|<
name|Pattern
argument_list|>
name|highlightPattern
init|=
name|Optional
operator|.
name|empty
argument_list|()
decl_stmt|;
DECL|field|undo
specifier|private
name|UndoManager
name|undo
decl_stmt|;
DECL|method|JTextAreaWithHighlighting ()
specifier|public
name|JTextAreaWithHighlighting
parameter_list|()
block|{
name|this
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
DECL|method|JTextAreaWithHighlighting (String text)
specifier|public
name|JTextAreaWithHighlighting
parameter_list|(
name|String
name|text
parameter_list|)
block|{
name|this
argument_list|(
name|text
argument_list|,
literal|""
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a text area with the ability to highlight parts of the content.      * It also defines a placeholder which will be displayed the content is empty.      *      * @param text      * @param placeholder      */
DECL|method|JTextAreaWithHighlighting (String text, String placeholder)
specifier|public
name|JTextAreaWithHighlighting
parameter_list|(
name|String
name|text
parameter_list|,
name|String
name|placeholder
parameter_list|)
block|{
name|super
argument_list|(
name|text
argument_list|,
name|placeholder
argument_list|)
expr_stmt|;
name|setupUndoRedo
argument_list|()
expr_stmt|;
name|setupPasteListener
argument_list|()
expr_stmt|;
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
operator|.
name|name
argument_list|()
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
comment|// Bind the redo action to ctrl-Y
name|boolean
name|bind
init|=
literal|true
decl_stmt|;
name|KeyStroke
name|redoKey
init|=
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
name|EDITOR_EMACS_KEYBINDINGS
argument_list|)
condition|)
block|{
comment|// If emacs is enabled, check if we have a conflict at keys
comment|// If yes, do not bind
comment|// Typically, we have: CTRL+y is "yank" in emacs and REDO in 'normal' settings
comment|// The Emacs key bindings are stored in the keymap, not in the input map.
name|Keymap
name|keymap
init|=
name|getKeymap
argument_list|()
decl_stmt|;
name|KeyStroke
index|[]
name|keys
init|=
name|keymap
operator|.
name|getBoundKeyStrokes
argument_list|()
decl_stmt|;
name|int
name|i
init|=
literal|0
decl_stmt|;
while|while
condition|(
operator|(
name|i
operator|<
name|keys
operator|.
name|length
operator|)
operator|&&
operator|!
name|keys
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
name|redoKey
argument_list|)
condition|)
block|{
name|i
operator|++
expr_stmt|;
block|}
if|if
condition|(
name|i
operator|<
name|keys
operator|.
name|length
condition|)
block|{
comment|// conflict found -> do not bind
name|bind
operator|=
literal|false
expr_stmt|;
block|}
block|}
if|if
condition|(
name|bind
condition|)
block|{
name|getInputMap
argument_list|()
operator|.
name|put
argument_list|(
name|redoKey
argument_list|,
literal|"Redo"
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Highlight words in the Textarea      *      * @param words to highlight      */
DECL|method|highLight ()
specifier|private
name|void
name|highLight
parameter_list|()
block|{
comment|// highlight all characters that appear in charsToHighlight
name|Highlighter
name|highlighter
init|=
name|getHighlighter
argument_list|()
decl_stmt|;
name|highlighter
operator|.
name|removeAllHighlights
argument_list|()
expr_stmt|;
if|if
condition|(
operator|(
name|highlightPattern
operator|==
literal|null
operator|)
operator|||
operator|!
name|highlightPattern
operator|.
name|isPresent
argument_list|()
condition|)
block|{
return|return;
block|}
name|String
name|content
init|=
name|getText
argument_list|()
decl_stmt|;
if|if
condition|(
name|content
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return;
block|}
name|highlightPattern
operator|.
name|ifPresent
argument_list|(
name|pattern
lambda|->
block|{
name|Matcher
name|matcher
init|=
name|pattern
operator|.
name|matcher
argument_list|(
name|content
argument_list|)
decl_stmt|;
while|while
condition|(
name|matcher
operator|.
name|find
argument_list|()
condition|)
block|{
try|try
block|{
name|highlighter
operator|.
name|addHighlight
argument_list|(
name|matcher
operator|.
name|start
argument_list|()
argument_list|,
name|matcher
operator|.
name|end
argument_list|()
argument_list|,
name|DefaultHighlighter
operator|.
name|DefaultPainter
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|BadLocationException
name|ble
parameter_list|)
block|{
comment|// should not occur if matcher works right
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Highlighting not possible, bad location"
argument_list|,
name|ble
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|setText (String text)
specifier|public
name|void
name|setText
parameter_list|(
name|String
name|text
parameter_list|)
block|{
name|super
operator|.
name|setText
argument_list|(
name|text
argument_list|)
expr_stmt|;
name|highLight
argument_list|()
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
DECL|method|highlightPattern (Optional<Pattern> highlightPattern)
specifier|public
name|void
name|highlightPattern
parameter_list|(
name|Optional
argument_list|<
name|Pattern
argument_list|>
name|highlightPattern
parameter_list|)
block|{
name|this
operator|.
name|highlightPattern
operator|=
name|highlightPattern
expr_stmt|;
name|highLight
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

