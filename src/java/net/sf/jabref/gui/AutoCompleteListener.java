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
name|FocusEvent
import|;
end_import

begin_comment
comment|/**  * Created by Morten O. Alver, 16 Feb. 2007  */
end_comment

begin_class
DECL|class|AutoCompleteListener
specifier|public
class|class
name|AutoCompleteListener
extends|extends
name|KeyAdapter
implements|implements
name|FocusListener
block|{
DECL|field|completer
name|AbstractAutoCompleter
name|completer
decl_stmt|;
DECL|field|toSetIn
specifier|protected
name|String
name|toSetIn
init|=
literal|null
decl_stmt|,
DECL|field|lastBeginning
name|lastBeginning
init|=
literal|null
decl_stmt|;
DECL|field|lastCaretPosition
specifier|protected
name|int
name|lastCaretPosition
init|=
operator|-
literal|1
decl_stmt|;
DECL|field|lastCompletions
specifier|protected
name|Object
index|[]
name|lastCompletions
init|=
literal|null
decl_stmt|;
DECL|field|lastShownCompletion
specifier|protected
name|int
name|lastShownCompletion
init|=
literal|0
decl_stmt|;
comment|// This field is set if the focus listener should call another focus listener
comment|// after finishing. This is needed because the autocomplete listener must
comment|// run before the focus listener responsible for storing the current edit.
DECL|field|nextFocusListener
specifier|protected
name|FocusListener
name|nextFocusListener
init|=
literal|null
decl_stmt|;
comment|// These variables keep track of the situation from time to time.
DECL|method|AutoCompleteListener (AbstractAutoCompleter completer)
specifier|public
name|AutoCompleteListener
parameter_list|(
name|AbstractAutoCompleter
name|completer
parameter_list|)
block|{
name|this
operator|.
name|completer
operator|=
name|completer
expr_stmt|;
block|}
comment|/**      * This method is used if the focus listener should call another focus listener      * after finishing. This is needed because the autocomplete listener must      * run before the focus listener responsible for storing the current edit.      *      * @param listener The listener to call.      */
DECL|method|setNextFocusListener (FocusListener listener)
specifier|public
name|void
name|setNextFocusListener
parameter_list|(
name|FocusListener
name|listener
parameter_list|)
block|{
name|this
operator|.
name|nextFocusListener
operator|=
name|listener
expr_stmt|;
block|}
DECL|method|keyPressed (KeyEvent e)
specifier|public
name|void
name|keyPressed
parameter_list|(
name|KeyEvent
name|e
parameter_list|)
block|{
if|if
condition|(
operator|(
name|toSetIn
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|e
operator|.
name|getKeyCode
argument_list|()
operator|==
name|KeyEvent
operator|.
name|VK_ENTER
operator|)
condition|)
block|{
name|JTextComponent
name|comp
init|=
operator|(
name|JTextComponent
operator|)
name|e
operator|.
name|getSource
argument_list|()
decl_stmt|;
name|int
name|end
init|=
name|comp
operator|.
name|getSelectionEnd
argument_list|()
decl_stmt|;
name|comp
operator|.
name|select
argument_list|(
name|end
argument_list|,
name|end
argument_list|)
expr_stmt|;
name|e
operator|.
name|consume
argument_list|()
expr_stmt|;
return|return;
block|}
comment|// Cycle through alternative completions when user presses PGUP/PGDN:
elseif|else
if|if
condition|(
operator|(
name|e
operator|.
name|getKeyCode
argument_list|()
operator|==
name|KeyEvent
operator|.
name|VK_PAGE_DOWN
operator|)
operator|&&
operator|(
name|lastCompletions
operator|!=
literal|null
operator|)
condition|)
block|{
name|cycle
argument_list|(
operator|(
name|JTextComponent
operator|)
name|e
operator|.
name|getSource
argument_list|()
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|e
operator|.
name|consume
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|(
name|e
operator|.
name|getKeyCode
argument_list|()
operator|==
name|KeyEvent
operator|.
name|VK_PAGE_UP
operator|)
operator|&&
operator|(
name|lastCompletions
operator|!=
literal|null
operator|)
condition|)
block|{
name|cycle
argument_list|(
operator|(
name|JTextComponent
operator|)
name|e
operator|.
name|getSource
argument_list|()
argument_list|,
operator|-
literal|1
argument_list|)
expr_stmt|;
name|e
operator|.
name|consume
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|cycle (JTextComponent comp, int increment)
specifier|private
name|void
name|cycle
parameter_list|(
name|JTextComponent
name|comp
parameter_list|,
name|int
name|increment
parameter_list|)
block|{
name|lastShownCompletion
operator|+=
name|increment
expr_stmt|;
if|if
condition|(
name|lastShownCompletion
operator|>=
name|lastCompletions
operator|.
name|length
condition|)
name|lastShownCompletion
operator|=
literal|0
expr_stmt|;
elseif|else
if|if
condition|(
name|lastShownCompletion
operator|<
literal|0
condition|)
name|lastShownCompletion
operator|=
name|lastCompletions
operator|.
name|length
operator|-
literal|1
expr_stmt|;
name|String
name|sno
init|=
call|(
name|String
call|)
argument_list|(
name|lastCompletions
index|[
name|lastShownCompletion
index|]
argument_list|)
decl_stmt|;
name|toSetIn
operator|=
name|sno
operator|.
name|substring
argument_list|(
name|lastBeginning
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
name|StringBuffer
name|alltext
init|=
operator|new
name|StringBuffer
argument_list|(
name|comp
operator|.
name|getText
argument_list|()
argument_list|)
decl_stmt|;
name|int
name|deletedChars
init|=
name|comp
operator|.
name|getSelectionEnd
argument_list|()
operator|-
name|comp
operator|.
name|getSelectionStart
argument_list|()
decl_stmt|;
name|alltext
operator|.
name|delete
argument_list|(
name|comp
operator|.
name|getSelectionStart
argument_list|()
argument_list|,
name|comp
operator|.
name|getSelectionEnd
argument_list|()
argument_list|)
expr_stmt|;
name|int
name|cp
init|=
name|comp
operator|.
name|getCaretPosition
argument_list|()
operator|-
name|deletedChars
decl_stmt|;
name|alltext
operator|.
name|insert
argument_list|(
name|cp
argument_list|,
name|toSetIn
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
comment|//Util.pr(alltext.toString());
name|comp
operator|.
name|setText
argument_list|(
name|alltext
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|comp
operator|.
name|setCaretPosition
argument_list|(
name|cp
operator|+
name|toSetIn
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
name|comp
operator|.
name|select
argument_list|(
name|cp
argument_list|,
name|cp
operator|+
name|sno
operator|.
name|length
argument_list|()
operator|-
name|lastBeginning
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
name|lastCaretPosition
operator|=
name|comp
operator|.
name|getCaretPosition
argument_list|()
expr_stmt|;
comment|//System.out.println("ToSetIn: '"+toSetIn+"'");
block|}
DECL|method|keyTyped (KeyEvent e)
specifier|public
name|void
name|keyTyped
parameter_list|(
name|KeyEvent
name|e
parameter_list|)
block|{
name|char
name|ch
init|=
name|e
operator|.
name|getKeyChar
argument_list|()
decl_stmt|;
if|if
condition|(
name|Character
operator|.
name|isLetter
argument_list|(
name|ch
argument_list|)
condition|)
block|{
name|JTextComponent
name|comp
init|=
operator|(
name|JTextComponent
operator|)
name|e
operator|.
name|getSource
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|toSetIn
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|toSetIn
operator|.
name|length
argument_list|()
operator|>
literal|1
operator|)
operator|&&
operator|(
name|ch
operator|==
name|toSetIn
operator|.
name|charAt
argument_list|(
literal|1
argument_list|)
operator|)
condition|)
block|{
comment|// User continues on the word that was suggested.
name|toSetIn
operator|=
name|toSetIn
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
name|toSetIn
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|int
name|cp
init|=
name|comp
operator|.
name|getCaretPosition
argument_list|()
decl_stmt|;
comment|//comp.setCaretPosition(cp+1-toSetIn.);
comment|//System.out.println(cp-toSetIn.length()+" - "+cp);
name|comp
operator|.
name|select
argument_list|(
name|cp
operator|+
literal|1
operator|-
name|toSetIn
operator|.
name|length
argument_list|()
argument_list|,
name|cp
argument_list|)
expr_stmt|;
name|lastBeginning
operator|=
name|lastBeginning
operator|+
name|ch
expr_stmt|;
name|e
operator|.
name|consume
argument_list|()
expr_stmt|;
name|lastCaretPosition
operator|=
name|comp
operator|.
name|getCaretPosition
argument_list|()
expr_stmt|;
comment|//System.out.println("Added char: '"+toSetIn+"'");
comment|//System.out.println("LastBeginning: '"+lastBeginning+"'");
name|lastCompletions
operator|=
name|findCompletions
argument_list|(
name|lastBeginning
argument_list|,
name|comp
argument_list|)
expr_stmt|;
name|lastShownCompletion
operator|=
literal|0
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|lastCompletions
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|Object
name|lastCompletion
init|=
name|lastCompletions
index|[
name|i
index|]
decl_stmt|;
comment|//System.out.println("Completion["+i+"] = "+lastCompletion);
if|if
condition|(
operator|(
operator|(
name|String
operator|)
name|lastCompletion
operator|)
operator|.
name|endsWith
argument_list|(
name|toSetIn
argument_list|)
condition|)
block|{
name|lastShownCompletion
operator|=
name|i
expr_stmt|;
break|break;
block|}
block|}
comment|//System.out.println("Index now: "+lastShownCompletion);
if|if
condition|(
name|toSetIn
operator|.
name|length
argument_list|()
operator|<
literal|2
condition|)
name|toSetIn
operator|=
literal|null
expr_stmt|;
return|return;
block|}
block|}
if|if
condition|(
operator|(
name|toSetIn
operator|!=
literal|null
operator|)
operator|&&
operator|(
operator|(
name|toSetIn
operator|.
name|length
argument_list|()
operator|<=
literal|1
operator|)
operator|||
operator|(
name|ch
operator|!=
name|toSetIn
operator|.
name|charAt
argument_list|(
literal|1
argument_list|)
operator|)
operator|)
condition|)
block|{
comment|// User discontinues the word that was suggested.
name|lastBeginning
operator|=
name|lastBeginning
operator|+
name|ch
expr_stmt|;
name|Object
index|[]
name|completed
init|=
name|findCompletions
argument_list|(
name|lastBeginning
argument_list|,
name|comp
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|completed
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|completed
operator|.
name|length
operator|>
literal|0
operator|)
condition|)
block|{
name|lastShownCompletion
operator|=
literal|0
expr_stmt|;
name|lastCompletions
operator|=
name|completed
expr_stmt|;
name|String
name|sno
init|=
call|(
name|String
call|)
argument_list|(
name|completed
index|[
literal|0
index|]
argument_list|)
decl_stmt|;
name|int
name|lastLen
init|=
name|toSetIn
operator|.
name|length
argument_list|()
operator|-
literal|1
decl_stmt|;
name|toSetIn
operator|=
name|sno
operator|.
name|substring
argument_list|(
name|lastBeginning
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
name|String
name|text
init|=
name|comp
operator|.
name|getText
argument_list|()
decl_stmt|;
comment|//Util.pr(""+lastLen);
name|comp
operator|.
name|setText
argument_list|(
name|text
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|lastCaretPosition
operator|-
name|lastLen
argument_list|)
operator|+
name|toSetIn
operator|+
name|text
operator|.
name|substring
argument_list|(
name|lastCaretPosition
argument_list|)
argument_list|)
expr_stmt|;
name|comp
operator|.
name|select
argument_list|(
name|lastCaretPosition
operator|+
literal|1
operator|-
name|lastLen
argument_list|,
name|lastCaretPosition
operator|+
name|toSetIn
operator|.
name|length
argument_list|()
operator|-
name|lastLen
argument_list|)
expr_stmt|;
name|lastCaretPosition
operator|=
name|comp
operator|.
name|getCaretPosition
argument_list|()
expr_stmt|;
name|e
operator|.
name|consume
argument_list|()
expr_stmt|;
return|return;
block|}
else|else
block|{
name|toSetIn
operator|=
literal|null
expr_stmt|;
return|return;
block|}
block|}
name|StringBuffer
name|currentword
init|=
name|getCurrentWord
argument_list|(
name|comp
argument_list|)
decl_stmt|;
if|if
condition|(
name|currentword
operator|==
literal|null
condition|)
return|return;
name|currentword
operator|.
name|append
argument_list|(
name|ch
argument_list|)
expr_stmt|;
name|Object
index|[]
name|completed
init|=
name|findCompletions
argument_list|(
name|currentword
operator|.
name|toString
argument_list|()
argument_list|,
name|comp
argument_list|)
decl_stmt|;
name|int
name|no
init|=
literal|0
decl_stmt|;
comment|// We use the first word in the array of completions.
if|if
condition|(
operator|(
name|completed
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|completed
operator|.
name|length
operator|>
literal|0
operator|)
condition|)
block|{
name|lastShownCompletion
operator|=
literal|0
expr_stmt|;
name|lastCompletions
operator|=
name|completed
expr_stmt|;
name|String
name|sno
init|=
call|(
name|String
call|)
argument_list|(
name|completed
index|[
name|no
index|]
argument_list|)
decl_stmt|;
name|toSetIn
operator|=
name|sno
operator|.
name|substring
argument_list|(
name|currentword
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
comment|//Util.pr("AutoCompListener: Found "+completed[0]);
name|StringBuffer
name|alltext
init|=
operator|new
name|StringBuffer
argument_list|(
name|comp
operator|.
name|getText
argument_list|()
argument_list|)
decl_stmt|;
name|int
name|cp
init|=
name|comp
operator|.
name|getCaretPosition
argument_list|()
decl_stmt|;
name|alltext
operator|.
name|insert
argument_list|(
name|cp
argument_list|,
name|toSetIn
argument_list|)
expr_stmt|;
comment|//Util.pr(alltext.toString());
name|comp
operator|.
name|setText
argument_list|(
name|alltext
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|comp
operator|.
name|setCaretPosition
argument_list|(
name|cp
argument_list|)
expr_stmt|;
name|comp
operator|.
name|select
argument_list|(
name|cp
operator|+
literal|1
argument_list|,
name|cp
operator|+
literal|1
operator|+
name|sno
operator|.
name|length
argument_list|()
operator|-
name|currentword
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
name|e
operator|.
name|consume
argument_list|()
expr_stmt|;
name|lastCaretPosition
operator|=
name|comp
operator|.
name|getCaretPosition
argument_list|()
expr_stmt|;
name|lastBeginning
operator|=
name|currentword
operator|.
name|toString
argument_list|()
expr_stmt|;
return|return;
block|}
block|}
comment|//Util.pr("#hm");
name|toSetIn
operator|=
literal|null
expr_stmt|;
name|lastCompletions
operator|=
literal|null
expr_stmt|;
block|}
DECL|method|findCompletions (String beginning, JTextComponent comp)
specifier|protected
name|Object
index|[]
name|findCompletions
parameter_list|(
name|String
name|beginning
parameter_list|,
name|JTextComponent
name|comp
parameter_list|)
block|{
return|return
name|completer
operator|.
name|complete
argument_list|(
name|beginning
argument_list|)
return|;
block|}
DECL|method|getCurrentWord (JTextComponent comp)
specifier|protected
name|StringBuffer
name|getCurrentWord
parameter_list|(
name|JTextComponent
name|comp
parameter_list|)
block|{
name|StringBuffer
name|res
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|String
name|upToCaret
decl_stmt|;
try|try
block|{
name|upToCaret
operator|=
name|comp
operator|.
name|getText
argument_list|(
literal|0
argument_list|,
name|comp
operator|.
name|getCaretPosition
argument_list|()
argument_list|)
expr_stmt|;
comment|// We now have the text from the start of the field up to the caret position.
comment|// In most fields, we are only interested in the currently edited word, so we
comment|// seek from the caret backward to the closest space:
if|if
condition|(
operator|!
name|completer
operator|.
name|isSingleUnitField
argument_list|()
condition|)
block|{
if|if
condition|(
operator|(
name|comp
operator|.
name|getCaretPosition
argument_list|()
operator|<
name|comp
operator|.
name|getText
argument_list|()
operator|.
name|length
argument_list|()
operator|)
operator|&&
operator|!
name|Character
operator|.
name|isWhitespace
argument_list|(
name|comp
operator|.
name|getText
argument_list|()
operator|.
name|charAt
argument_list|(
name|comp
operator|.
name|getCaretPosition
argument_list|()
argument_list|)
argument_list|)
condition|)
return|return
literal|null
return|;
name|boolean
name|found
init|=
literal|false
decl_stmt|;
name|int
name|piv
init|=
name|upToCaret
operator|.
name|length
argument_list|()
operator|-
literal|1
decl_stmt|;
while|while
condition|(
operator|!
name|found
operator|&&
operator|(
name|piv
operator|>=
literal|0
operator|)
condition|)
block|{
if|if
condition|(
name|Character
operator|.
name|isWhitespace
argument_list|(
name|upToCaret
operator|.
name|charAt
argument_list|(
name|piv
argument_list|)
argument_list|)
condition|)
name|found
operator|=
literal|true
expr_stmt|;
else|else
name|piv
operator|--
expr_stmt|;
block|}
comment|//if (piv< 0)
comment|//piv = 0;
name|res
operator|.
name|append
argument_list|(
name|upToCaret
operator|.
name|substring
argument_list|(
name|piv
operator|+
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// For fields such as "journal" it is more reasonable to try to complete on the entire
comment|// text field content, so we skip the searching and keep the entire part up to the caret:
else|else
name|res
operator|.
name|append
argument_list|(
name|upToCaret
argument_list|)
expr_stmt|;
comment|//Util.pr("AutoCompListener: "+res.toString());
block|}
catch|catch
parameter_list|(
name|BadLocationException
name|ex
parameter_list|)
block|{         }
return|return
name|res
return|;
block|}
DECL|field|ANY_NAME
DECL|field|FIRST_NAME
DECL|field|LAST_NAME
specifier|final
specifier|static
name|int
name|ANY_NAME
init|=
literal|0
decl_stmt|,
name|FIRST_NAME
init|=
literal|1
decl_stmt|,
name|LAST_NAME
init|=
literal|2
decl_stmt|;
DECL|method|findNamePositionStatus (JTextComponent comp)
specifier|protected
name|int
name|findNamePositionStatus
parameter_list|(
name|JTextComponent
name|comp
parameter_list|)
block|{
name|String
name|upToCaret
decl_stmt|;
try|try
block|{
name|upToCaret
operator|=
name|comp
operator|.
name|getText
argument_list|(
literal|0
argument_list|,
name|comp
operator|.
name|getCaretPosition
argument_list|()
argument_list|)
expr_stmt|;
comment|// Clip off evertyhing up to and including the last " and " before:
name|upToCaret
operator|=
name|upToCaret
operator|.
name|substring
argument_list|(
name|upToCaret
operator|.
name|lastIndexOf
argument_list|(
literal|" and "
argument_list|)
operator|+
literal|1
argument_list|)
expr_stmt|;
name|int
name|commaIndex
init|=
name|upToCaret
operator|.
name|indexOf
argument_list|(
literal|','
argument_list|)
decl_stmt|;
if|if
condition|(
name|commaIndex
operator|<
literal|0
condition|)
return|return
name|ANY_NAME
return|;
else|else
return|return
name|FIRST_NAME
return|;
block|}
catch|catch
parameter_list|(
name|BadLocationException
name|ex
parameter_list|)
block|{
return|return
name|ANY_NAME
return|;
block|}
block|}
DECL|method|focusGained (FocusEvent event)
specifier|public
name|void
name|focusGained
parameter_list|(
name|FocusEvent
name|event
parameter_list|)
block|{
if|if
condition|(
name|nextFocusListener
operator|!=
literal|null
condition|)
name|nextFocusListener
operator|.
name|focusGained
argument_list|(
name|event
argument_list|)
expr_stmt|;
block|}
DECL|method|focusLost (FocusEvent event)
specifier|public
name|void
name|focusLost
parameter_list|(
name|FocusEvent
name|event
parameter_list|)
block|{
if|if
condition|(
name|lastCompletions
operator|!=
literal|null
condition|)
block|{
name|JTextComponent
name|comp
init|=
operator|(
name|JTextComponent
operator|)
name|event
operator|.
name|getSource
argument_list|()
decl_stmt|;
name|int
name|selStart
init|=
name|comp
operator|.
name|getSelectionStart
argument_list|()
decl_stmt|;
name|String
name|text
init|=
name|comp
operator|.
name|getText
argument_list|()
decl_stmt|;
name|comp
operator|.
name|setText
argument_list|(
name|text
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|selStart
argument_list|)
operator|+
name|text
operator|.
name|substring
argument_list|(
name|comp
operator|.
name|getSelectionEnd
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|comp
operator|.
name|setCaretPosition
argument_list|(
name|selStart
argument_list|)
expr_stmt|;
name|lastCompletions
operator|=
literal|null
expr_stmt|;
name|lastShownCompletion
operator|=
literal|0
expr_stmt|;
name|lastCaretPosition
operator|=
operator|-
literal|1
expr_stmt|;
name|toSetIn
operator|=
literal|null
expr_stmt|;
block|}
if|if
condition|(
name|nextFocusListener
operator|!=
literal|null
condition|)
name|nextFocusListener
operator|.
name|focusLost
argument_list|(
name|event
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

