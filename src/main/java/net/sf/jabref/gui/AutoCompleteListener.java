begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2012 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|logging
operator|.
name|Level
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|logging
operator|.
name|Logger
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
DECL|field|logger
specifier|private
specifier|static
name|Logger
name|logger
init|=
name|Logger
operator|.
name|getLogger
argument_list|(
name|AutoCompleteListener
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
DECL|field|completer
name|AbstractAutoCompleter
argument_list|<
name|String
argument_list|>
name|completer
decl_stmt|;
comment|// These variables keep track of the situation from time to time.
DECL|field|toSetIn
specifier|protected
name|String
name|toSetIn
init|=
literal|null
decl_stmt|;
comment|// null indicates that there are no completions available
DECL|field|lastBeginning
specifier|protected
name|String
name|lastBeginning
init|=
literal|null
decl_stmt|;
comment|// the letters, the user has typed until know
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
name|String
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
DECL|field|consumeEnterKey
specifier|protected
name|boolean
name|consumeEnterKey
init|=
literal|true
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
DECL|method|AutoCompleteListener (AbstractAutoCompleter completer)
specifier|public
name|AutoCompleteListener
parameter_list|(
name|AbstractAutoCompleter
name|completer
parameter_list|)
block|{
comment|//    	if (logger.getHandlers().length == 0) {
comment|//	    	logger.setLevel(Level.FINEST);
comment|//	    	ConsoleHandler ch = new ConsoleHandler();
comment|//	    	ch.setLevel(Level.FINEST);
comment|//	    	logger.addHandler(ch);
comment|//    	}
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
comment|/**      * This setting determines whether the autocomplete listener should consume the Enter key      * stroke when it leads to accepting a completion. If set to false, the JTextComponent will receive      * the Enter key press after the completion is done. The default value if true.      * @param t true to indicate that the Enter key should be consumed, false that it should be forwarded      */
DECL|method|setConsumeEnterKey (boolean t)
specifier|public
name|void
name|setConsumeEnterKey
parameter_list|(
name|boolean
name|t
parameter_list|)
block|{
name|this
operator|.
name|consumeEnterKey
operator|=
name|t
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
comment|// replace typed characters by characters from completion
name|lastBeginning
operator|=
name|lastCompletions
index|[
name|lastShownCompletion
index|]
expr_stmt|;
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
name|toSetIn
operator|=
literal|null
expr_stmt|;
if|if
condition|(
name|consumeEnterKey
condition|)
name|e
operator|.
name|consume
argument_list|()
expr_stmt|;
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
name|toSetIn
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
name|toSetIn
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
comment|//        else if ((e.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {
comment|//        	StringBuffer currentword = getCurrentWord((JTextComponent) e.getSource());
comment|//        	// delete last char to obey semantics of back space
comment|//        	currentword.deleteCharAt(currentword.length()-1);
comment|//        	doCompletion(currentword, e);
comment|//        }
elseif|else
if|if
condition|(
name|e
operator|.
name|getKeyChar
argument_list|()
operator|==
name|KeyEvent
operator|.
name|CHAR_UNDEFINED
condition|)
block|{
if|if
condition|(
name|e
operator|.
name|getKeyCode
argument_list|()
operator|!=
name|KeyEvent
operator|.
name|VK_SHIFT
condition|)
block|{
comment|// shift is OK, everyhting else leads to a reset
name|resetAutoCompletion
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|logger
operator|.
name|finest
argument_list|(
literal|"special case: shift pressed. No action."
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|logger
operator|.
name|finest
argument_list|(
literal|"special case: defined character, but not caught above"
argument_list|)
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
assert|assert
operator|(
name|lastCompletions
operator|!=
literal|null
operator|)
assert|;
assert|assert
operator|(
name|lastCompletions
operator|.
name|length
operator|>
literal|0
operator|)
assert|;
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
name|lastCompletions
index|[
name|lastShownCompletion
index|]
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
name|StringBuilder
name|alltext
init|=
operator|new
name|StringBuilder
argument_list|(
name|comp
operator|.
name|getText
argument_list|()
argument_list|)
decl_stmt|;
name|int
name|oldSelectionStart
init|=
name|comp
operator|.
name|getSelectionStart
argument_list|()
decl_stmt|;
name|int
name|oldSelectionEnd
init|=
name|comp
operator|.
name|getSelectionEnd
argument_list|()
decl_stmt|;
comment|// replace prefix with new prefix
name|int
name|startPos
init|=
name|comp
operator|.
name|getSelectionStart
argument_list|()
operator|-
name|lastBeginning
operator|.
name|length
argument_list|()
decl_stmt|;
name|alltext
operator|.
name|delete
argument_list|(
name|startPos
argument_list|,
name|oldSelectionStart
argument_list|)
expr_stmt|;
name|alltext
operator|.
name|insert
argument_list|(
name|startPos
argument_list|,
name|sno
operator|.
name|subSequence
argument_list|(
literal|0
argument_list|,
name|lastBeginning
operator|.
name|length
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|// replace suffix with new suffix
name|alltext
operator|.
name|delete
argument_list|(
name|oldSelectionStart
argument_list|,
name|oldSelectionEnd
argument_list|)
expr_stmt|;
comment|//int cp = oldSelectionEnd - deletedChars;
name|alltext
operator|.
name|insert
argument_list|(
name|oldSelectionStart
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
comment|//comp.setCaretPosition(cp+toSetIn.length()-1);
name|comp
operator|.
name|select
argument_list|(
name|oldSelectionStart
argument_list|,
name|oldSelectionStart
operator|+
name|toSetIn
operator|.
name|length
argument_list|()
operator|-
literal|1
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
comment|/**      * If user cancels autocompletion by      *   a) entering another letter than the completed word (and there is no other auto completion)      *   b) space      * the casing of the letters has to be kept      *       * Global variable "lastBeginning" keeps track of typed letters.      * We rely on this variable to reconstruct the text       *       * @param wordSeperatorTyped indicates whether the user has typed a white space character or a      */
DECL|method|setUnmodifiedTypedLetters (JTextComponent comp, boolean lastBeginningContainsTypedCharacter, boolean wordSeperatorTyped)
specifier|private
name|void
name|setUnmodifiedTypedLetters
parameter_list|(
name|JTextComponent
name|comp
parameter_list|,
name|boolean
name|lastBeginningContainsTypedCharacter
parameter_list|,
name|boolean
name|wordSeperatorTyped
parameter_list|)
block|{
if|if
condition|(
name|lastBeginning
operator|==
literal|null
condition|)
block|{
name|logger
operator|.
name|finest
argument_list|(
literal|"no last beginning"
argument_list|)
expr_stmt|;
comment|// There was no previous input (if the user typed a word, where no autocompletion is available)
comment|// Thus, there is nothing to replace
return|return;
block|}
name|logger
operator|.
name|finest
argument_list|(
literal|"lastBeginning:>"
operator|+
name|lastBeginning
operator|+
literal|"<"
argument_list|)
expr_stmt|;
if|if
condition|(
name|comp
operator|.
name|getSelectedText
argument_list|()
operator|==
literal|null
condition|)
block|{
comment|// if there is no selection
comment|// the user has typed the complete word, but possibly with a different casing
comment|// we need a replacement
if|if
condition|(
name|wordSeperatorTyped
condition|)
block|{
name|logger
operator|.
name|finest
argument_list|(
literal|"replacing complete word"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// if user did not press a white space character (space, ...),
comment|// then we do not do anything
return|return;
block|}
block|}
else|else
block|{
name|logger
operator|.
name|finest
argument_list|(
literal|"selected text "
operator|+
name|comp
operator|.
name|getSelectedText
argument_list|()
operator|+
literal|" will be removed"
argument_list|)
expr_stmt|;
comment|// remove completion suggestion
name|comp
operator|.
name|replaceSelection
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
name|lastCaretPosition
operator|=
name|comp
operator|.
name|getCaretPosition
argument_list|()
expr_stmt|;
name|int
name|endIndex
init|=
name|lastCaretPosition
operator|-
name|lastBeginning
operator|.
name|length
argument_list|()
decl_stmt|;
if|if
condition|(
name|lastBeginningContainsTypedCharacter
condition|)
block|{
comment|// the current letter is NOT contained in comp.getText(), but in lastBeginning
comment|// thus lastBeginning.length() is one too large
name|endIndex
operator|++
expr_stmt|;
block|}
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
name|endIndex
argument_list|)
operator|.
name|concat
argument_list|(
name|lastBeginning
argument_list|)
operator|.
name|concat
argument_list|(
name|text
operator|.
name|substring
argument_list|(
name|lastCaretPosition
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|lastBeginningContainsTypedCharacter
condition|)
block|{
comment|// the current letter is NOT contained in comp.getText()
comment|// Thus, cursor position also did not get updated
name|lastCaretPosition
operator|++
expr_stmt|;
block|}
name|comp
operator|.
name|setCaretPosition
argument_list|(
name|lastCaretPosition
argument_list|)
expr_stmt|;
name|lastBeginning
operator|=
literal|null
expr_stmt|;
block|}
comment|/**      * Start a new completion attempt      * (instead of treating a continuation of an existing word or an interrupt of the current word)      */
DECL|method|startCompletion (StringBuffer currentword, KeyEvent e)
specifier|private
name|void
name|startCompletion
parameter_list|(
name|StringBuffer
name|currentword
parameter_list|,
name|KeyEvent
name|e
parameter_list|)
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
name|String
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
name|String
name|prefix
init|=
name|completer
operator|.
name|getPrefix
argument_list|()
decl_stmt|;
name|String
name|cWord
init|=
operator|(
name|prefix
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|prefix
operator|.
name|length
argument_list|()
operator|>
literal|0
operator|)
condition|?
name|currentword
operator|.
name|toString
argument_list|()
operator|.
name|substring
argument_list|(
name|prefix
operator|.
name|length
argument_list|()
argument_list|)
else|:
name|currentword
operator|.
name|toString
argument_list|()
decl_stmt|;
if|if
condition|(
name|logger
operator|.
name|isLoggable
argument_list|(
name|Level
operator|.
name|FINEST
argument_list|)
condition|)
block|{
name|logger
operator|.
name|finest
argument_list|(
literal|"startCompletion"
argument_list|)
expr_stmt|;
name|logger
operator|.
name|finest
argument_list|(
literal|"currentword:>"
operator|+
name|currentword
operator|+
literal|"<"
argument_list|)
expr_stmt|;
name|logger
operator|.
name|finest
argument_list|(
literal|"prefix:>"
operator|+
name|prefix
operator|+
literal|"<"
argument_list|)
expr_stmt|;
name|logger
operator|.
name|finest
argument_list|(
literal|"cword:>"
operator|+
name|cWord
operator|+
literal|"<"
argument_list|)
expr_stmt|;
block|}
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
name|completed
index|[
name|no
index|]
decl_stmt|;
comment|// these two lines obey the user's input
comment|//toSetIn = Character.toString(ch);
comment|//toSetIn = toSetIn.concat(sno.substring(cWord.length()));
comment|// BUT we obey the completion
name|toSetIn
operator|=
name|sno
operator|.
name|substring
argument_list|(
name|cWord
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
name|logger
operator|.
name|isLoggable
argument_list|(
name|Level
operator|.
name|FINEST
argument_list|)
condition|)
block|{
name|logger
operator|.
name|finest
argument_list|(
literal|"toSetIn:>"
operator|+
name|toSetIn
operator|+
literal|"<"
argument_list|)
expr_stmt|;
block|}
name|StringBuilder
name|alltext
init|=
operator|new
name|StringBuilder
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
name|cWord
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
name|char
name|ch
init|=
name|e
operator|.
name|getKeyChar
argument_list|()
decl_stmt|;
name|logger
operator|.
name|finest
argument_list|(
literal|"Appending>"
operator|+
name|ch
operator|+
literal|"<"
argument_list|)
expr_stmt|;
if|if
condition|(
name|cWord
operator|.
name|length
argument_list|()
operator|<=
literal|1
condition|)
block|{
name|lastBeginning
operator|=
name|Character
operator|.
name|toString
argument_list|(
name|ch
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|lastBeginning
operator|=
name|cWord
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|cWord
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
operator|.
name|concat
argument_list|(
name|Character
operator|.
name|toString
argument_list|(
name|ch
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
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
name|logger
operator|.
name|finest
argument_list|(
literal|"key typed event caught"
argument_list|)
expr_stmt|;
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
name|ch
operator|==
literal|'\n'
condition|)
comment|// this case is handled at keyPressed(e)
return|return;
if|if
condition|(
operator|(
name|e
operator|.
name|getModifiers
argument_list|()
operator||
name|KeyEvent
operator|.
name|SHIFT_MASK
operator|)
operator|==
name|KeyEvent
operator|.
name|SHIFT_MASK
condition|)
block|{
comment|// plain key or SHIFT + key is pressed, no handling of CTRL+key,  META+key, ...
if|if
condition|(
name|Character
operator|.
name|isLetter
argument_list|(
name|ch
argument_list|)
operator|||
name|Character
operator|.
name|isDigit
argument_list|(
name|ch
argument_list|)
operator|||
operator|(
name|Character
operator|.
name|isWhitespace
argument_list|(
name|ch
argument_list|)
operator|&&
name|completer
operator|.
name|isSingleUnitField
argument_list|()
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
if|if
condition|(
name|logger
operator|.
name|isLoggable
argument_list|(
name|Level
operator|.
name|FINEST
argument_list|)
condition|)
block|{
if|if
condition|(
name|toSetIn
operator|==
literal|null
condition|)
name|logger
operator|.
name|finest
argument_list|(
literal|"toSetIn: NULL"
argument_list|)
expr_stmt|;
else|else
name|logger
operator|.
name|finest
argument_list|(
literal|"toSetIn:>"
operator|+
name|toSetIn
operator|+
literal|"<"
argument_list|)
expr_stmt|;
block|}
comment|// The case-insensitive system is a bit tricky here
comment|// If keyword is "TODO" and user types "tO", then this is treated as "continue" as the "O" matches the "O"
comment|// If keyword is "TODO" and user types "To", then this is treated as "discont" as the "o" does NOT match the "O".
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
name|logger
operator|.
name|finest
argument_list|(
literal|"cont"
argument_list|)
expr_stmt|;
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
name|String
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
name|lastCompletion
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
block|{
comment|// User typed the last character of the autocompleted word
comment|// We have to replace the automcompletion word by the typed word.
comment|// This helps if the user presses "space" after the completion
comment|// "space" indicates that the user does NOT want the autocompletion,
comment|// but the typed word
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
name|lastCaretPosition
operator|-
name|lastBeginning
operator|.
name|length
argument_list|()
argument_list|)
operator|+
name|lastBeginning
operator|+
name|text
operator|.
name|substring
argument_list|(
name|lastCaretPosition
argument_list|)
argument_list|)
expr_stmt|;
comment|// there is no selected text, therefore we are not updating the selection
name|toSetIn
operator|=
literal|null
expr_stmt|;
block|}
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
if|if
condition|(
name|logger
operator|.
name|isLoggable
argument_list|(
name|Level
operator|.
name|FINEST
argument_list|)
condition|)
block|{
name|logger
operator|.
name|finest
argument_list|(
literal|"discont"
argument_list|)
expr_stmt|;
name|logger
operator|.
name|finest
argument_list|(
literal|"toSetIn:>"
operator|+
name|toSetIn
operator|+
literal|"<"
argument_list|)
expr_stmt|;
name|logger
operator|.
name|finest
argument_list|(
literal|"lastBeginning:>"
operator|+
name|lastBeginning
operator|+
literal|"<"
argument_list|)
expr_stmt|;
block|}
name|String
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
name|completed
index|[
literal|0
index|]
decl_stmt|;
comment|// toSetIn = string used for autocompletion last time
comment|// this string has to be removed
comment|// lastCaretPosition is the position of the caret after toSetIn.
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
comment|//we do not use toSetIn as we want to obey the casing of "sno"
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
operator|-
name|lastBeginning
operator|.
name|length
argument_list|()
operator|+
literal|1
argument_list|)
operator|+
name|sno
operator|+
name|text
operator|.
name|substring
argument_list|(
name|lastCaretPosition
argument_list|)
argument_list|)
expr_stmt|;
name|int
name|startSelect
init|=
name|lastCaretPosition
operator|+
literal|1
operator|-
name|lastLen
decl_stmt|;
name|int
name|endSelect
init|=
name|lastCaretPosition
operator|+
name|toSetIn
operator|.
name|length
argument_list|()
operator|-
name|lastLen
decl_stmt|;
name|comp
operator|.
name|select
argument_list|(
name|startSelect
argument_list|,
name|endSelect
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
name|setUnmodifiedTypedLetters
argument_list|(
name|comp
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|e
operator|.
name|consume
argument_list|()
expr_stmt|;
name|toSetIn
operator|=
literal|null
expr_stmt|;
return|return;
block|}
block|}
name|logger
operator|.
name|finest
argument_list|(
literal|"case else"
argument_list|)
expr_stmt|;
name|comp
operator|.
name|replaceSelection
argument_list|(
literal|""
argument_list|)
expr_stmt|;
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
name|currentword
operator|=
operator|new
name|StringBuffer
argument_list|()
expr_stmt|;
comment|// only "real characters" end up here
assert|assert
operator|(
operator|!
name|Character
operator|.
name|isISOControl
argument_list|(
name|ch
argument_list|)
operator|)
assert|;
name|currentword
operator|.
name|append
argument_list|(
name|ch
argument_list|)
expr_stmt|;
name|startCompletion
argument_list|(
name|currentword
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return;
block|}
else|else
block|{
if|if
condition|(
name|Character
operator|.
name|isWhitespace
argument_list|(
name|ch
argument_list|)
condition|)
block|{
assert|assert
operator|(
operator|!
name|completer
operator|.
name|isSingleUnitField
argument_list|()
operator|)
assert|;
name|logger
operator|.
name|finest
argument_list|(
literal|"whitespace&& !singleUnitField"
argument_list|)
expr_stmt|;
comment|// start a new search if end-of-field is reached
comment|// replace displayed letters with typed letters
name|setUnmodifiedTypedLetters
argument_list|(
operator|(
name|JTextComponent
operator|)
name|e
operator|.
name|getSource
argument_list|()
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|resetAutoCompletion
argument_list|()
expr_stmt|;
return|return;
block|}
name|logger
operator|.
name|finest
argument_list|(
literal|"No letter/digit/whitespace or CHAR_UNDEFINED"
argument_list|)
expr_stmt|;
comment|// replace displayed letters with typed letters
name|setUnmodifiedTypedLetters
argument_list|(
operator|(
name|JTextComponent
operator|)
name|e
operator|.
name|getSource
argument_list|()
argument_list|,
literal|false
argument_list|,
operator|!
name|Character
operator|.
name|isISOControl
argument_list|(
name|ch
argument_list|)
argument_list|)
expr_stmt|;
name|resetAutoCompletion
argument_list|()
expr_stmt|;
return|return;
block|}
block|}
name|resetAutoCompletion
argument_list|()
expr_stmt|;
block|}
comment|/**      * Resets the auto completion data in a way that no leftovers are there      */
DECL|method|resetAutoCompletion ()
specifier|private
name|void
name|resetAutoCompletion
parameter_list|()
block|{
name|logger
operator|.
name|finest
argument_list|(
literal|"Resetting autocompletion"
argument_list|)
expr_stmt|;
name|toSetIn
operator|=
literal|null
expr_stmt|;
name|lastBeginning
operator|=
literal|null
expr_stmt|;
block|}
DECL|method|findCompletions (String beginning, JTextComponent comp)
specifier|protected
name|String
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
block|{
comment|// caret is in the middle of the text AND current character is a whitespace
comment|// that means: a new word is started and there is no current word
return|return
literal|null
return|;
block|}
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
name|upToCaret
operator|.
name|charAt
argument_list|(
name|piv
argument_list|)
argument_list|)
condition|)
block|{
name|piv
operator|--
expr_stmt|;
block|}
comment|// priv points to whitespace char or priv is -1
comment|// copy everything from the next char up to the end of "upToCaret"
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
else|else
block|{
comment|// For fields such as "journal" it is more reasonable to try to complete on the entire
comment|// text field content, so we skip the searching and keep the entire part up to the caret:
name|res
operator|.
name|append
argument_list|(
name|upToCaret
argument_list|)
expr_stmt|;
block|}
comment|//Util.pr("AutoCompListener: "+res.toString());
block|}
catch|catch
parameter_list|(
name|BadLocationException
name|ignore
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
name|toSetIn
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
name|clearCurrentSuggestion
argument_list|(
name|comp
argument_list|)
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
DECL|method|clearCurrentSuggestion (JTextComponent comp)
specifier|public
name|void
name|clearCurrentSuggestion
parameter_list|(
name|JTextComponent
name|comp
parameter_list|)
block|{
if|if
condition|(
name|toSetIn
operator|!=
literal|null
condition|)
block|{
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
block|}
block|}
end_class

end_unit

