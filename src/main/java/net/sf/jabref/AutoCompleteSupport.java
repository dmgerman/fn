begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
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
name|FocusListener
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
name|autocompleter
operator|.
name|AbstractAutoCompleter
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
name|autocompleter
operator|.
name|AutoCompleter
import|;
end_import

begin_comment
comment|/**  * Based on code by   * 	Santhosh Kumar (http://www.jroller.com/santhosh/date/20050620)  * 	James Lemieux (Glazed Lists AutoCompleteSupport)  */
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
name|AutoCompleteRenderer
argument_list|<
name|E
argument_list|>
name|renderer
decl_stmt|;
DECL|field|autoCompleter
name|AutoCompleter
argument_list|<
name|E
argument_list|>
name|autoCompleter
decl_stmt|;
DECL|field|textComp
name|JTextComponent
name|textComp
decl_stmt|;
DECL|field|popup
name|JPopupMenu
name|popup
init|=
operator|new
name|JPopupMenu
argument_list|()
decl_stmt|;
comment|/** 	 *<tt>true</tt> if the text in the combobox editor is selected when the 	 * editor gains focus;<tt>false</tt> otherwise. 	 */
DECL|field|selectsTextOnFocusGain
specifier|private
name|boolean
name|selectsTextOnFocusGain
init|=
literal|true
decl_stmt|;
comment|/** 	 * Handles selecting the text in the comboBoxEditorComponent when it gains 	 * focus. 	 */
DECL|field|selectTextOnFocusGainHandler
specifier|private
specifier|final
name|FocusListener
name|selectTextOnFocusGainHandler
init|=
operator|new
name|ComboBoxEditorFocusHandler
argument_list|()
decl_stmt|;
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
argument_list|<
name|E
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
block|}
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
argument_list|<
name|E
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|install ()
specifier|public
name|void
name|install
parameter_list|()
block|{
specifier|final
name|Action
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
name|Action
name|downAction
init|=
operator|new
name|MoveAction
argument_list|(
literal|1
argument_list|)
decl_stmt|;
specifier|final
name|Action
name|hidePopupAction
init|=
operator|new
name|AbstractAction
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|popup
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
decl_stmt|;
specifier|final
name|Action
name|acceptAction
init|=
operator|new
name|AbstractAction
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
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
return|return;
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
name|piv
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
name|piv
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
name|piv
argument_list|)
argument_list|)
operator|&&
name|textComp
operator|.
name|getText
argument_list|()
operator|.
name|charAt
argument_list|(
name|piv
argument_list|)
operator|!=
literal|','
condition|)
block|{
name|piv
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
name|piv
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
block|}
decl_stmt|;
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
name|textComp
operator|.
name|getDocument
argument_list|()
operator|.
name|addDocumentListener
argument_list|(
name|documentListener
argument_list|)
expr_stmt|;
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
comment|// add a FocusListener to the ComboBoxEditor which selects all text when
comment|// focus is gained
name|this
operator|.
name|textComp
operator|.
name|addFocusListener
argument_list|(
name|selectTextOnFocusGainHandler
argument_list|)
expr_stmt|;
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
name|popup
operator|.
name|addPopupMenuListener
argument_list|(
operator|new
name|PopupMenuListener
argument_list|()
block|{
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
specifier|public
name|void
name|popupMenuCanceled
parameter_list|(
name|PopupMenuEvent
name|e
parameter_list|)
block|{ 			}
block|}
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Returns<tt>true</tt> if the combo box editor text is selected when it 	 * gains focus;<tt>false</tt> otherwise. 	 */
DECL|method|getSelectsTextOnFocusGain ()
specifier|public
name|boolean
name|getSelectsTextOnFocusGain
parameter_list|()
block|{
return|return
name|selectsTextOnFocusGain
return|;
block|}
comment|/** 	 * If<code>selectsTextOnFocusGain</code> is<tt>true</tt>, all text in the 	 * editor is selected when the combo box editor gains focus. If it is 	 *<tt>false</tt> the selection state of the editor is not effected by focus 	 * changes. 	 * 	 * @throws IllegalStateException 	 *             if this method is called from any Thread other than the Swing 	 *             Event Dispatch Thread 	 */
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
DECL|field|documentListener
name|DocumentListener
name|documentListener
init|=
operator|new
name|DocumentListener
argument_list|()
block|{
specifier|public
name|void
name|insertUpdate
parameter_list|(
name|DocumentEvent
name|e
parameter_list|)
block|{
name|postProcessDocumentChange
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|removeUpdate
parameter_list|(
name|DocumentEvent
name|e
parameter_list|)
block|{
name|postProcessDocumentChange
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|changedUpdate
parameter_list|(
name|DocumentEvent
name|e
parameter_list|)
block|{ 		}
block|}
decl_stmt|;
DECL|method|postProcessDocumentChange ()
specifier|private
name|void
name|postProcessDocumentChange
parameter_list|()
block|{
name|String
name|text
init|=
name|textComp
operator|.
name|getText
argument_list|()
decl_stmt|;
name|popup
operator|.
name|setVisible
argument_list|(
literal|false
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
if|if
condition|(
name|autoCompleter
operator|==
literal|null
condition|)
return|return;
name|E
index|[]
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
name|candidates
operator|.
name|length
operator|>
literal|0
condition|)
block|{
name|renderer
operator|.
name|selectItem
argument_list|(
literal|0
argument_list|)
expr_stmt|;
comment|// popup.repaint();
comment|// Hide and show then, to recalculate height
comment|// popup.hide();
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
name|popup
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|textComp
operator|.
name|hasFocus
argument_list|()
condition|)
name|textComp
operator|.
name|requestFocusInWindow
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * The action invoked by hitting the up or down arrow key. 	 */
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
comment|/** 	 * When the user selects a value from the popup with the mouse, we want to 	 * honour their selection *without* attempting to autocomplete it to a new 	 * term. Otherwise, it is possible that selections which are prefixes for 	 * values that appear higher in the ComboBoxModel cannot be selected by the 	 * mouse since they can always be successfully autocompleted to another 	 * term. 	 */
comment|/* 	 * private class PopupMouseHandler extends MouseAdapter { 	 *  	 * @Override public void mousePressed(MouseEvent e) { doNotAutoComplete = 	 * true; } 	 *  	 * @Override public void mouseReleased(MouseEvent e) { doNotAutoComplete = 	 * false; } } 	 */
comment|/** 	 * To emulate Firefox behaviour, all text in the ComboBoxEditor is selected 	 * from beginning to end when the ComboBoxEditor gains focus if the value 	 * returned from {@link AutoCompleteSupport#getSelectsTextOnFocusGain()} 	 * allows this behaviour. In addition, the JPopupMenu is hidden when the 	 * ComboBoxEditor loses focus if the value returned from 	 * {@link AutoCompleteSupport#getHidesPopupOnFocusLost()} allows this 	 * behaviour. 	 */
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
name|getSelectsTextOnFocusGain
argument_list|()
operator|&&
operator|!
name|e
operator|.
name|isTemporary
argument_list|()
condition|)
name|textComp
operator|.
name|selectAll
argument_list|()
expr_stmt|;
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
block|{ 		}
block|}
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
block|}
end_class

end_unit

