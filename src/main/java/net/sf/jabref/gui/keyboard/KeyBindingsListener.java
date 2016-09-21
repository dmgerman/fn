begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.keyboard
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|keyboard
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
name|KeyAdapter
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
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Locale
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
import|;
end_import

begin_comment
comment|/**  * respond to grabKey and display the key binding  */
end_comment

begin_class
DECL|class|KeyBindingsListener
specifier|public
class|class
name|KeyBindingsListener
extends|extends
name|KeyAdapter
block|{
DECL|field|table
specifier|private
specifier|final
name|KeyBindingTable
name|table
decl_stmt|;
DECL|method|KeyBindingsListener (KeyBindingTable table)
specifier|public
name|KeyBindingsListener
parameter_list|(
name|KeyBindingTable
name|table
parameter_list|)
block|{
name|this
operator|.
name|table
operator|=
name|table
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|keyPressed (KeyEvent evt)
specifier|public
name|void
name|keyPressed
parameter_list|(
name|KeyEvent
name|evt
parameter_list|)
block|{
name|boolean
name|isFunctionKey
init|=
literal|false
decl_stmt|;
name|boolean
name|isEscapeKey
init|=
literal|false
decl_stmt|;
name|boolean
name|isDeleteKey
init|=
literal|false
decl_stmt|;
comment|// first check if anything is selected if not then return
specifier|final
name|int
name|selRow
init|=
name|table
operator|.
name|getSelectedRow
argument_list|()
decl_stmt|;
name|boolean
name|isAnyRowSelected
init|=
name|selRow
operator|>=
literal|0
decl_stmt|;
if|if
condition|(
operator|!
name|isAnyRowSelected
condition|)
block|{
return|return;
block|}
specifier|final
name|String
name|modifier
init|=
name|getModifierText
argument_list|(
name|evt
argument_list|)
decl_stmt|;
comment|// VALIDATE code and modifier
comment|// all key bindings must have a modifier: ctrl alt etc
if|if
condition|(
literal|""
operator|.
name|equals
argument_list|(
name|modifier
argument_list|)
condition|)
block|{
name|int
name|kc
init|=
name|evt
operator|.
name|getKeyCode
argument_list|()
decl_stmt|;
name|isFunctionKey
operator|=
operator|(
name|kc
operator|>=
name|KeyEvent
operator|.
name|VK_F1
operator|)
operator|&&
operator|(
name|kc
operator|<=
name|KeyEvent
operator|.
name|VK_F12
operator|)
expr_stmt|;
name|isEscapeKey
operator|=
name|kc
operator|==
name|KeyEvent
operator|.
name|VK_ESCAPE
expr_stmt|;
name|isDeleteKey
operator|=
name|kc
operator|==
name|KeyEvent
operator|.
name|VK_DELETE
expr_stmt|;
if|if
condition|(
operator|!
operator|(
name|isFunctionKey
operator|||
name|isEscapeKey
operator|||
name|isDeleteKey
operator|)
condition|)
block|{
comment|// need a modifier except for function, escape and delete keys
return|return;
block|}
block|}
name|int
name|code
init|=
name|evt
operator|.
name|getKeyCode
argument_list|()
decl_stmt|;
name|String
name|newKey
decl_stmt|;
comment|//skip the event triggered only by a modifier, tab, backspace or enter because these normally have preset
comment|// functionality if they alone are pressed
if|if
condition|(
name|code
operator|==
name|KeyEvent
operator|.
name|VK_ALT
operator|||
name|code
operator|==
name|KeyEvent
operator|.
name|VK_TAB
operator|||
name|code
operator|==
name|KeyEvent
operator|.
name|VK_BACK_SPACE
operator|||
name|code
operator|==
name|KeyEvent
operator|.
name|VK_ENTER
operator|||
name|code
operator|==
name|KeyEvent
operator|.
name|VK_SPACE
operator|||
name|code
operator|==
name|KeyEvent
operator|.
name|VK_CONTROL
operator|||
name|code
operator|==
name|KeyEvent
operator|.
name|VK_SHIFT
operator|||
name|code
operator|==
name|KeyEvent
operator|.
name|VK_META
condition|)
block|{
return|return;
block|}
if|if
condition|(
literal|""
operator|.
name|equals
argument_list|(
name|modifier
argument_list|)
condition|)
block|{
if|if
condition|(
name|isFunctionKey
condition|)
block|{
name|newKey
operator|=
name|KeyEvent
operator|.
name|getKeyText
argument_list|(
name|code
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|isEscapeKey
condition|)
block|{
name|newKey
operator|=
literal|"ESCAPE"
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|isDeleteKey
condition|)
block|{
name|newKey
operator|=
literal|"DELETE"
expr_stmt|;
block|}
else|else
block|{
return|return;
block|}
block|}
else|else
block|{
name|newKey
operator|=
name|modifier
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|ENGLISH
argument_list|)
operator|+
literal|" "
operator|+
name|KeyEvent
operator|.
name|getKeyText
argument_list|(
name|code
argument_list|)
expr_stmt|;
block|}
comment|//SHOW new key binding
comment|//find which key is selected and set its value
name|table
operator|.
name|setValueAt
argument_list|(
name|newKey
argument_list|,
name|selRow
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|table
operator|.
name|revalidate
argument_list|()
expr_stmt|;
name|table
operator|.
name|repaint
argument_list|()
expr_stmt|;
block|}
comment|/**      * Collects th English translations of all modifiers and returns them separated by a space      *      * @param evt the KeyEvent that was triggered to set the KeyBindings      * @return a String containing the modifier keys text      */
DECL|method|getModifierText (KeyEvent evt)
specifier|private
name|String
name|getModifierText
parameter_list|(
name|KeyEvent
name|evt
parameter_list|)
block|{
name|ArrayList
argument_list|<
name|String
argument_list|>
name|modifiersList
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
if|if
condition|(
name|evt
operator|.
name|isControlDown
argument_list|()
condition|)
block|{
name|modifiersList
operator|.
name|add
argument_list|(
literal|"ctrl"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|evt
operator|.
name|isAltDown
argument_list|()
condition|)
block|{
name|modifiersList
operator|.
name|add
argument_list|(
literal|"alt"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|evt
operator|.
name|isShiftDown
argument_list|()
condition|)
block|{
name|modifiersList
operator|.
name|add
argument_list|(
literal|"shift"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|evt
operator|.
name|isAltGraphDown
argument_list|()
condition|)
block|{
name|modifiersList
operator|.
name|add
argument_list|(
literal|"alt gr"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|evt
operator|.
name|isMetaDown
argument_list|()
condition|)
block|{
name|modifiersList
operator|.
name|add
argument_list|(
literal|"meta"
argument_list|)
expr_stmt|;
block|}
comment|//Now build the String with all the modifier texts
name|String
name|modifiersAsString
init|=
name|modifiersList
operator|.
name|stream
argument_list|()
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|joining
argument_list|(
literal|" "
argument_list|)
argument_list|)
decl_stmt|;
return|return
name|modifiersAsString
return|;
block|}
block|}
end_class

end_unit

