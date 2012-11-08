begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|ArrayList
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
name|*
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
name|UndoableEditEvent
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
name|UndoableEditListener
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

begin_class
DECL|class|JTextAreaWithHighlighting
specifier|public
class|class
name|JTextAreaWithHighlighting
extends|extends
name|JTextArea
implements|implements
name|SearchTextListener
block|{
DECL|field|wordsToHighlight
specifier|private
name|ArrayList
argument_list|<
name|String
argument_list|>
name|wordsToHighlight
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
name|super
argument_list|()
expr_stmt|;
name|setupUndoRedo
argument_list|()
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
name|super
argument_list|(
name|text
argument_list|)
expr_stmt|;
name|setupUndoRedo
argument_list|()
expr_stmt|;
block|}
DECL|method|JTextAreaWithHighlighting (Document doc)
specifier|public
name|JTextAreaWithHighlighting
parameter_list|(
name|Document
name|doc
parameter_list|)
block|{
name|super
argument_list|(
name|doc
argument_list|)
expr_stmt|;
name|setupUndoRedo
argument_list|()
expr_stmt|;
block|}
DECL|method|JTextAreaWithHighlighting (int rows, int columns)
specifier|public
name|JTextAreaWithHighlighting
parameter_list|(
name|int
name|rows
parameter_list|,
name|int
name|columns
parameter_list|)
block|{
name|super
argument_list|(
name|rows
argument_list|,
name|columns
argument_list|)
expr_stmt|;
name|setupUndoRedo
argument_list|()
expr_stmt|;
block|}
DECL|method|JTextAreaWithHighlighting (String text, int rows, int columns)
specifier|public
name|JTextAreaWithHighlighting
parameter_list|(
name|String
name|text
parameter_list|,
name|int
name|rows
parameter_list|,
name|int
name|columns
parameter_list|)
block|{
name|super
argument_list|(
name|text
argument_list|,
name|rows
argument_list|,
name|columns
argument_list|)
expr_stmt|;
name|setupUndoRedo
argument_list|()
expr_stmt|;
block|}
DECL|method|JTextAreaWithHighlighting (Document doc, String text, int rows, int columns)
specifier|public
name|JTextAreaWithHighlighting
parameter_list|(
name|Document
name|doc
parameter_list|,
name|String
name|text
parameter_list|,
name|int
name|rows
parameter_list|,
name|int
name|columns
parameter_list|)
block|{
name|super
argument_list|(
name|doc
argument_list|,
name|text
argument_list|,
name|rows
argument_list|,
name|columns
argument_list|)
expr_stmt|;
name|setupUndoRedo
argument_list|()
expr_stmt|;
block|}
DECL|method|setupUndoRedo ()
specifier|protected
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
operator|new
name|UndoableEditListener
argument_list|()
block|{
specifier|public
name|void
name|undoableEditHappened
parameter_list|(
name|UndoableEditEvent
name|evt
parameter_list|)
block|{
name|undo
operator|.
name|addEdit
argument_list|(
name|evt
operator|.
name|getEdit
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
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
name|e
parameter_list|)
block|{                         }
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
name|prefs
operator|.
name|getKey
argument_list|(
literal|"Undo"
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
literal|"Redo"
argument_list|)
block|{
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
name|e
parameter_list|)
block|{                         }
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
name|prefs
operator|.
name|getKey
argument_list|(
literal|"Redo"
argument_list|)
argument_list|,
literal|"Redo"
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Highlight words in the Textarea 	 *  	 * @param words to highlight 	 */
DECL|method|highLight (ArrayList<String> words)
specifier|private
name|void
name|highLight
parameter_list|(
name|ArrayList
argument_list|<
name|String
argument_list|>
name|words
parameter_list|)
block|{
comment|// highlight all characters that appear in charsToHighlight
name|Highlighter
name|h
init|=
name|getHighlighter
argument_list|()
decl_stmt|;
comment|// myTa.set
name|h
operator|.
name|removeAllHighlights
argument_list|()
expr_stmt|;
if|if
condition|(
name|words
operator|==
literal|null
operator|||
name|words
operator|.
name|isEmpty
argument_list|()
operator|||
name|words
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|length
argument_list|()
operator|==
literal|0
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
name|length
argument_list|()
operator|==
literal|0
condition|)
return|return;
name|Matcher
name|matcher
init|=
name|Globals
operator|.
name|getPatternForWords
argument_list|(
name|words
argument_list|)
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
name|h
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
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|ble
argument_list|)
expr_stmt|;
block|}
block|}
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
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"highLightWords"
argument_list|)
condition|)
block|{
name|highLight
argument_list|(
name|wordsToHighlight
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|undo
operator|!=
literal|null
condition|)
name|undo
operator|.
name|discardAllEdits
argument_list|()
expr_stmt|;
block|}
DECL|method|searchText (ArrayList<String> words)
specifier|public
name|void
name|searchText
parameter_list|(
name|ArrayList
argument_list|<
name|String
argument_list|>
name|words
parameter_list|)
block|{
comment|// words have to be stored in class variable as
comment|// setText() makes use of them
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"highLightWords"
argument_list|)
condition|)
block|{
name|this
operator|.
name|wordsToHighlight
operator|=
name|words
expr_stmt|;
name|highLight
argument_list|(
name|words
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|this
operator|.
name|wordsToHighlight
operator|!=
literal|null
condition|)
block|{
comment|// setting of "highLightWords" seems to have changed.
comment|// clear all highlights and remember the clearing (by wordsToHighlight = null)
name|this
operator|.
name|wordsToHighlight
operator|=
literal|null
expr_stmt|;
name|highLight
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

