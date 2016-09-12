begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.autocompleter
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|autocompleter
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
name|KeyEvent
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
name|AbstractAction
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
name|JComponent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JPopupMenu
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
name|event
operator|.
name|DocumentEvent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|DocumentListener
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|PopupMenuEvent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|PopupMenuListener
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|autocompleter
operator|.
name|AutoCompleter
import|;
end_import

begin_comment
comment|/**  * Endows a textbox with the ability to autocomplete the input. Based on code by Santhosh Kumar  * (http://www.jroller.com/santhosh/date/20050620) James Lemieux (Glazed Lists AutoCompleteSupport)  *  * @param<E> type of items displayed in the autocomplete popup  */
end_comment

begin_class
DECL|class|AutoCompleteSupport
specifier|public
class|class
name|AutoCompleteSupport
parameter_list|<
name|E
parameter_list|>
block|{
DECL|field|renderer
specifier|private
specifier|final
name|AutoCompleteRenderer
argument_list|<
name|E
argument_list|>
name|renderer
decl_stmt|;
DECL|field|autoCompleter
specifier|private
name|AutoCompleter
argument_list|<
name|E
argument_list|>
name|autoCompleter
decl_stmt|;
DECL|field|textComp
specifier|private
specifier|final
name|JTextComponent
name|textComp
decl_stmt|;
DECL|field|popup
specifier|private
specifier|final
name|JPopupMenu
name|popup
init|=
operator|new
name|JPopupMenu
argument_list|()
decl_stmt|;
DECL|field|selectsTextOnFocusGain
specifier|private
name|boolean
name|selectsTextOnFocusGain
init|=
literal|true
decl_stmt|;
comment|/**      * Constructs a new AutoCompleteSupport for the textbox using the autocompleter and a renderer.      *      * @param textComp the textbox component for which autocompletion should be enabled      * @param autoCompleter the autocompleter providing the data      * @param renderer the renderer displaying the popup      */
DECL|method|AutoCompleteSupport (JTextComponent textComp, AutoCompleter<E> autoCompleter, AutoCompleteRenderer<E> renderer)
specifier|public
name|AutoCompleteSupport
parameter_list|(
name|JTextComponent
name|textComp
parameter_list|,
name|AutoCompleter
argument_list|<
name|E
argument_list|>
name|autoCompleter
parameter_list|,
name|AutoCompleteRenderer
argument_list|<
name|E
argument_list|>
name|renderer
parameter_list|)
block|{
name|this
operator|.
name|renderer
operator|=
name|renderer
expr_stmt|;
name|this
operator|.
name|textComp
operator|=
name|textComp
expr_stmt|;
name|this
operator|.
name|autoCompleter
operator|=
name|autoCompleter
expr_stmt|;
block|}
comment|/**      * Constructs a new AutoCompleteSupport for the textbox. The possible autocomplete items are displayed as a simple      * list. The autocompletion items are provided by an AutoCompleter which has to be specified later using      * {@link setAutoCompleter}.      *      * @param textComp the textbox component for which autocompletion should be enabled      */
DECL|method|AutoCompleteSupport (JTextComponent textComp)
specifier|public
name|AutoCompleteSupport
parameter_list|(
name|JTextComponent
name|textComp
parameter_list|)
block|{
name|this
argument_list|(
name|textComp
argument_list|,
literal|null
argument_list|,
operator|new
name|ListAutoCompleteRenderer
argument_list|<>
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Constructs a new AutoCompleteSupport for the textbox using the autocompleter and a renderer. The possible      * autocomplete items are displayed as a simple list.      *      * @param textComp the textbox component for which autocompletion should be enabled      * @param autoCompleter the autocompleter providing the data      */
DECL|method|AutoCompleteSupport (JTextComponent textComp, AutoCompleter<E> autoCompleter)
specifier|public
name|AutoCompleteSupport
parameter_list|(
name|JTextComponent
name|textComp
parameter_list|,
name|AutoCompleter
argument_list|<
name|E
argument_list|>
name|autoCompleter
parameter_list|)
block|{
name|this
argument_list|(
name|textComp
argument_list|,
name|autoCompleter
argument_list|,
operator|new
name|ListAutoCompleteRenderer
argument_list|<>
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Inits the autocompletion popup. After this method is called, further input in the specified textbox will be      * autocompleted.      */
DECL|method|install ()
specifier|public
name|void
name|install
parameter_list|()
block|{
comment|// ActionListeners for navigating the suggested autocomplete items with the arrow keys
specifier|final
name|ActionListener
name|upAction
init|=
operator|new
name|MoveAction
argument_list|(
operator|-
literal|1
argument_list|)
decl_stmt|;
specifier|final
name|ActionListener
name|downAction
init|=
operator|new
name|MoveAction
argument_list|(
literal|1
argument_list|)
decl_stmt|;
comment|// ActionListener hiding the autocomplete popup
specifier|final
name|ActionListener
name|hidePopupAction
init|=
name|e
lambda|->
name|popup
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
decl_stmt|;
comment|// ActionListener accepting the currently selected item as the autocompletion
specifier|final
name|ActionListener
name|acceptAction
init|=
name|e
lambda|->
block|{
name|E
name|itemToInsert
init|=
name|renderer
operator|.
name|getSelectedItem
argument_list|()
decl_stmt|;
if|if
condition|(
name|itemToInsert
operator|==
literal|null
condition|)
block|{
return|return;
block|}
name|String
name|toInsert
init|=
name|autoCompleter
operator|.
name|getAutoCompleteText
argument_list|(
name|itemToInsert
argument_list|)
decl_stmt|;
comment|// TODO: The following should be refactored. For example, the autocompleter shouldn't know whether we want to complete one word or multiple.
comment|// In most fields, we are only interested in the currently edited word, so we
comment|// seek from the caret backward to the closest space:
if|if
condition|(
operator|!
name|autoCompleter
operator|.
name|isSingleUnitField
argument_list|()
condition|)
block|{
comment|// Get position of last word separator (whitespace or comma)
name|int
name|priv
init|=
name|textComp
operator|.
name|getText
argument_list|()
operator|.
name|length
argument_list|()
operator|-
literal|1
decl_stmt|;
while|while
condition|(
operator|(
name|priv
operator|>=
literal|0
operator|)
operator|&&
operator|!
name|Character
operator|.
name|isWhitespace
argument_list|(
name|textComp
operator|.
name|getText
argument_list|()
operator|.
name|charAt
argument_list|(
name|priv
argument_list|)
argument_list|)
operator|&&
operator|(
name|textComp
operator|.
name|getText
argument_list|()
operator|.
name|charAt
argument_list|(
name|priv
argument_list|)
operator|!=
literal|','
operator|)
condition|)
block|{
name|priv
operator|--
expr_stmt|;
block|}
comment|// priv points to whitespace char or priv is -1
comment|// copy everything from the next char up to the end of "upToCaret"
name|textComp
operator|.
name|setText
argument_list|(
name|textComp
operator|.
name|getText
argument_list|()
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|priv
operator|+
literal|1
argument_list|)
operator|+
name|toInsert
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// For fields such as "journal" it is more reasonable to try to complete on the entire
comment|// text field content, so we skip the searching and keep the entire part up to the caret:
name|textComp
operator|.
name|setText
argument_list|(
name|toInsert
argument_list|)
expr_stmt|;
block|}
name|textComp
operator|.
name|setCaretPosition
argument_list|(
name|textComp
operator|.
name|getText
argument_list|()
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
name|popup
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
decl_stmt|;
comment|// Create popup
name|popup
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createMatteBorder
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|,
name|Color
operator|.
name|LIGHT_GRAY
argument_list|)
argument_list|)
expr_stmt|;
name|popup
operator|.
name|setPopupSize
argument_list|(
name|textComp
operator|.
name|getWidth
argument_list|()
argument_list|,
literal|200
argument_list|)
expr_stmt|;
name|popup
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|popup
operator|.
name|setFocusable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|popup
operator|.
name|setRequestFocusEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|popup
operator|.
name|add
argument_list|(
name|renderer
operator|.
name|init
argument_list|(
name|acceptAction
argument_list|)
argument_list|)
expr_stmt|;
comment|// Listen for changes to the text -> update autocomplete suggestions
name|textComp
operator|.
name|getDocument
argument_list|()
operator|.
name|addDocumentListener
argument_list|(
operator|new
name|DocumentListener
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|insertUpdate
parameter_list|(
name|DocumentEvent
name|e
parameter_list|)
block|{
name|postProcessTextChange
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|removeUpdate
parameter_list|(
name|DocumentEvent
name|e
parameter_list|)
block|{
name|postProcessTextChange
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|changedUpdate
parameter_list|(
name|DocumentEvent
name|e
parameter_list|)
block|{
comment|// Do nothing
block|}
block|}
argument_list|)
expr_stmt|;
comment|// Listen for up/down arrow keys -> move currently selected item up or down
comment|// We have to reimplement this function here since we cannot be sure that a simple list will be used to display the items
comment|// So better let the renderer decide what to do.
comment|// (Moreover, the list does not have the focus so probably would not recognize the keystrokes in the first place.)
name|textComp
operator|.
name|registerKeyboardAction
argument_list|(
name|downAction
argument_list|,
name|KeyStroke
operator|.
name|getKeyStroke
argument_list|(
name|KeyEvent
operator|.
name|VK_DOWN
argument_list|,
literal|0
argument_list|)
argument_list|,
name|JComponent
operator|.
name|WHEN_FOCUSED
argument_list|)
expr_stmt|;
name|textComp
operator|.
name|registerKeyboardAction
argument_list|(
name|upAction
argument_list|,
name|KeyStroke
operator|.
name|getKeyStroke
argument_list|(
name|KeyEvent
operator|.
name|VK_UP
argument_list|,
literal|0
argument_list|)
argument_list|,
name|JComponent
operator|.
name|WHEN_FOCUSED
argument_list|)
expr_stmt|;
comment|// Listen for ESC key -> hide popup
name|textComp
operator|.
name|registerKeyboardAction
argument_list|(
name|hidePopupAction
argument_list|,
name|KeyStroke
operator|.
name|getKeyStroke
argument_list|(
name|KeyEvent
operator|.
name|VK_ESCAPE
argument_list|,
literal|0
argument_list|)
argument_list|,
name|JComponent
operator|.
name|WHEN_IN_FOCUSED_WINDOW
argument_list|)
expr_stmt|;
comment|// Listen to focus events -> select all the text on gaining the focus
name|this
operator|.
name|textComp
operator|.
name|addFocusListener
argument_list|(
operator|new
name|ComboBoxEditorFocusHandler
argument_list|()
argument_list|)
expr_stmt|;
comment|// Listen for ENTER key if popup is visible -> accept current autocomplete suggestion
name|popup
operator|.
name|addPopupMenuListener
argument_list|(
operator|new
name|PopupMenuListener
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|popupMenuWillBecomeVisible
parameter_list|(
name|PopupMenuEvent
name|e
parameter_list|)
block|{
name|textComp
operator|.
name|registerKeyboardAction
argument_list|(
name|acceptAction
argument_list|,
name|KeyStroke
operator|.
name|getKeyStroke
argument_list|(
name|KeyEvent
operator|.
name|VK_ENTER
argument_list|,
literal|0
argument_list|)
argument_list|,
name|JComponent
operator|.
name|WHEN_FOCUSED
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|popupMenuWillBecomeInvisible
parameter_list|(
name|PopupMenuEvent
name|e
parameter_list|)
block|{
name|textComp
operator|.
name|unregisterKeyboardAction
argument_list|(
name|KeyStroke
operator|.
name|getKeyStroke
argument_list|(
name|KeyEvent
operator|.
name|VK_ENTER
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|popupMenuCanceled
parameter_list|(
name|PopupMenuEvent
name|e
parameter_list|)
block|{
comment|// Do nothing
block|}
block|}
argument_list|)
expr_stmt|;
block|}
comment|/**      * Returns whether the text in the textbox is selected when the textbox gains focus. Defaults to true.      *      * @return      */
DECL|method|isSelectsTextOnFocusGain ()
specifier|public
name|boolean
name|isSelectsTextOnFocusGain
parameter_list|()
block|{
return|return
name|selectsTextOnFocusGain
return|;
block|}
comment|/**      * Sets whether the text in the textbox is selected when the textbox gains focus. Default is true.      *      * @param selectsTextOnFocusGain new value      */
DECL|method|setSelectsTextOnFocusGain (boolean selectsTextOnFocusGain)
specifier|public
name|void
name|setSelectsTextOnFocusGain
parameter_list|(
name|boolean
name|selectsTextOnFocusGain
parameter_list|)
block|{
name|this
operator|.
name|selectsTextOnFocusGain
operator|=
name|selectsTextOnFocusGain
expr_stmt|;
block|}
comment|/**      * The text changed so update autocomplete suggestions accordingly.      */
DECL|method|postProcessTextChange ()
specifier|private
name|void
name|postProcessTextChange
parameter_list|()
block|{
if|if
condition|(
name|autoCompleter
operator|==
literal|null
condition|)
block|{
name|popup
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
return|return;
block|}
name|String
name|text
init|=
name|textComp
operator|.
name|getText
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|E
argument_list|>
name|candidates
init|=
name|autoCompleter
operator|.
name|complete
argument_list|(
name|text
argument_list|)
decl_stmt|;
name|renderer
operator|.
name|update
argument_list|(
name|candidates
argument_list|)
expr_stmt|;
if|if
condition|(
name|textComp
operator|.
name|isEnabled
argument_list|()
operator|&&
operator|(
operator|!
name|candidates
operator|.
name|isEmpty
argument_list|()
operator|)
condition|)
block|{
name|renderer
operator|.
name|selectItem
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|popup
operator|.
name|setPopupSize
argument_list|(
name|textComp
operator|.
name|getWidth
argument_list|()
argument_list|,
literal|200
argument_list|)
expr_stmt|;
name|popup
operator|.
name|show
argument_list|(
name|textComp
argument_list|,
literal|0
argument_list|,
name|textComp
operator|.
name|getHeight
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|popup
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|textComp
operator|.
name|hasFocus
argument_list|()
condition|)
block|{
name|textComp
operator|.
name|requestFocusInWindow
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      * The action invoked by hitting the up or down arrow key. If the popup is currently shown, that the action is      * relayed to it. Otherwise the arrow keys trigger the popup.      */
DECL|class|MoveAction
specifier|private
class|class
name|MoveAction
extends|extends
name|AbstractAction
block|{
DECL|field|offset
specifier|private
specifier|final
name|int
name|offset
decl_stmt|;
DECL|method|MoveAction (int offset)
specifier|public
name|MoveAction
parameter_list|(
name|int
name|offset
parameter_list|)
block|{
name|this
operator|.
name|offset
operator|=
name|offset
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
if|if
condition|(
name|popup
operator|.
name|isVisible
argument_list|()
condition|)
block|{
name|renderer
operator|.
name|selectItemRelative
argument_list|(
name|offset
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|popup
operator|.
name|show
argument_list|(
name|textComp
argument_list|,
literal|0
argument_list|,
name|textComp
operator|.
name|getHeight
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * Selects all text when the textbox gains focus. The behavior is controlled by the value returned from      * {@link AutoCompleteSupport#isSelectsTextOnFocusGain()}.      */
DECL|class|ComboBoxEditorFocusHandler
specifier|private
class|class
name|ComboBoxEditorFocusHandler
extends|extends
name|FocusAdapter
block|{
annotation|@
name|Override
DECL|method|focusGained (FocusEvent e)
specifier|public
name|void
name|focusGained
parameter_list|(
name|FocusEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|isSelectsTextOnFocusGain
argument_list|()
operator|&&
operator|!
name|e
operator|.
name|isTemporary
argument_list|()
condition|)
block|{
name|textComp
operator|.
name|selectAll
argument_list|()
expr_stmt|;
block|}
block|}
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
comment|// Do nothing
block|}
block|}
comment|/**      * Sets the autocompleter used to present autocomplete suggestions.      *      * @param autoCompleter the autocompleter providing the data      */
DECL|method|setAutoCompleter (AutoCompleter<E> autoCompleter)
specifier|public
name|void
name|setAutoCompleter
parameter_list|(
name|AutoCompleter
argument_list|<
name|E
argument_list|>
name|autoCompleter
parameter_list|)
block|{
name|this
operator|.
name|autoCompleter
operator|=
name|autoCompleter
expr_stmt|;
block|}
DECL|method|setVisible (boolean visible)
specifier|public
name|void
name|setVisible
parameter_list|(
name|boolean
name|visible
parameter_list|)
block|{
name|popup
operator|.
name|setVisible
argument_list|(
name|visible
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

