begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2004 R. Nagel   All programs in this directory and  subdirectories are published under the GNU General Public License as  described below.   This program is free software; you can redistribute it and/or modify  it under the terms of the GNU General Public License as published by  the Free Software Foundation; either version 2 of the License, or (at  your option) any later version.   This program is distributed in the hope that it will be useful, but  WITHOUT ANY WARRANTY; without even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU  General Public License for more details.   You should have received a copy of the GNU General Public License  along with this program; if not, write to the Free Software  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA   Further information about the GNU GPL is available at:  http://www.gnu.org/copyleft/gpl.ja.html   */
end_comment

begin_comment
comment|// created by : r.nagel 19.10.2004
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|// function : a popupmenu for bibtex fieldtext editors
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|// modified :
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
name|awt
operator|.
name|event
operator|.
name|MouseEvent
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
name|MouseListener
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
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
name|ImageIcon
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
name|util
operator|.
name|CaseChangeMenu
import|;
end_import

begin_class
DECL|class|FieldTextMenu
specifier|public
class|class
name|FieldTextMenu
implements|implements
name|MouseListener
block|{
DECL|field|myFieldName
specifier|private
name|FieldEditor
name|myFieldName
decl_stmt|;
DECL|field|inputMenu
specifier|private
name|JPopupMenu
name|inputMenu
init|=
operator|new
name|JPopupMenu
argument_list|()
decl_stmt|;
DECL|field|copyAct
specifier|private
name|CopyAction
name|copyAct
init|=
operator|new
name|CopyAction
argument_list|()
decl_stmt|;
DECL|field|pasteAct
specifier|private
name|PasteAction
name|pasteAct
init|=
operator|new
name|PasteAction
argument_list|()
decl_stmt|;
DECL|method|FieldTextMenu (FieldEditor fieldComponent)
specifier|public
name|FieldTextMenu
parameter_list|(
name|FieldEditor
name|fieldComponent
parameter_list|)
block|{
name|myFieldName
operator|=
name|fieldComponent
expr_stmt|;
comment|// copy/paste Menu
name|inputMenu
operator|.
name|add
argument_list|(
name|pasteAct
argument_list|)
expr_stmt|;
name|inputMenu
operator|.
name|add
argument_list|(
name|copyAct
argument_list|)
expr_stmt|;
name|inputMenu
operator|.
name|addSeparator
argument_list|()
expr_stmt|;
name|inputMenu
operator|.
name|add
argument_list|(
operator|new
name|ReplaceAction
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|myFieldName
operator|.
name|getTextComponent
argument_list|()
operator|instanceof
name|JTextComponent
condition|)
name|inputMenu
operator|.
name|add
argument_list|(
operator|new
name|CaseChangeMenu
argument_list|(
operator|(
name|JTextComponent
operator|)
name|myFieldName
operator|.
name|getTextComponent
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|mouseClicked (MouseEvent e)
specifier|public
name|void
name|mouseClicked
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{   }
DECL|method|mouseEntered (MouseEvent e)
specifier|public
name|void
name|mouseEntered
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{   }
DECL|method|mouseExited (MouseEvent e)
specifier|public
name|void
name|mouseExited
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{   }
DECL|method|mousePressed (MouseEvent e)
specifier|public
name|void
name|mousePressed
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{
name|maybeShowPopup
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
DECL|method|mouseReleased (MouseEvent e)
specifier|public
name|void
name|mouseReleased
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{
name|maybeShowPopup
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
DECL|method|maybeShowPopup ( MouseEvent e )
specifier|private
name|void
name|maybeShowPopup
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|.
name|isPopupTrigger
argument_list|()
condition|)
block|{
if|if
condition|(
name|myFieldName
operator|!=
literal|null
condition|)
block|{
name|myFieldName
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
comment|// enable/disable copy to clipboard if selected text available
name|String
name|txt
init|=
name|myFieldName
operator|.
name|getSelectedText
argument_list|()
decl_stmt|;
name|boolean
name|cStat
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|txt
operator|!=
literal|null
condition|)
if|if
condition|(
name|txt
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|cStat
operator|=
literal|true
expr_stmt|;
name|copyAct
operator|.
name|setEnabled
argument_list|(
name|cStat
argument_list|)
expr_stmt|;
name|inputMenu
operator|.
name|show
argument_list|(
name|e
operator|.
name|getComponent
argument_list|()
argument_list|,
name|e
operator|.
name|getX
argument_list|()
argument_list|,
name|e
operator|.
name|getY
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|// ---------------------------------------------------------------------------
DECL|class|BasicAction
specifier|abstract
class|class
name|BasicAction
extends|extends
name|AbstractAction
block|{
DECL|method|BasicAction (String text, String description, URL icon)
specifier|public
name|BasicAction
parameter_list|(
name|String
name|text
parameter_list|,
name|String
name|description
parameter_list|,
name|URL
name|icon
parameter_list|)
block|{
name|super
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
name|text
argument_list|)
argument_list|,
operator|new
name|ImageIcon
argument_list|(
name|icon
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|SHORT_DESCRIPTION
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
name|description
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|BasicAction (String text, String description, URL icon, KeyStroke key)
specifier|public
name|BasicAction
parameter_list|(
name|String
name|text
parameter_list|,
name|String
name|description
parameter_list|,
name|URL
name|icon
parameter_list|,
name|KeyStroke
name|key
parameter_list|)
block|{
name|super
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
name|text
argument_list|)
argument_list|,
operator|new
name|ImageIcon
argument_list|(
name|icon
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|ACCELERATOR_KEY
argument_list|,
name|key
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|SHORT_DESCRIPTION
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
name|description
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|BasicAction (String text)
specifier|public
name|BasicAction
parameter_list|(
name|String
name|text
parameter_list|)
block|{
name|super
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
name|text
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|BasicAction (String text, KeyStroke key)
specifier|public
name|BasicAction
parameter_list|(
name|String
name|text
parameter_list|,
name|KeyStroke
name|key
parameter_list|)
block|{
name|super
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
name|text
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|ACCELERATOR_KEY
argument_list|,
name|key
argument_list|)
expr_stmt|;
block|}
DECL|method|actionPerformed (ActionEvent e)
specifier|public
specifier|abstract
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
function_decl|;
block|}
comment|//---------------------------------------------------------------
comment|/*class MenuHeaderAction extends BasicAction   {     public MenuHeaderAction(String comment)     {       super("Edit -" +comment);       this.setEnabled(false);     }      public void actionPerformed(ActionEvent e) { }   }     */
comment|// ---------------------------------------------------------------------------
DECL|class|PasteAction
class|class
name|PasteAction
extends|extends
name|BasicAction
block|{
DECL|method|PasteAction ()
specifier|public
name|PasteAction
parameter_list|()
block|{
name|super
argument_list|(
literal|"Paste from clipboard"
argument_list|,
literal|"Paste from clipboard"
argument_list|,
name|GUIGlobals
operator|.
name|getIconUrl
argument_list|(
literal|"paste"
argument_list|)
argument_list|)
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
try|try
block|{
name|String
name|data
init|=
name|ClipBoardManager
operator|.
name|clipBoard
operator|.
name|getClipboardContents
argument_list|()
decl_stmt|;
if|if
condition|(
name|data
operator|!=
literal|null
condition|)
if|if
condition|(
name|data
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
if|if
condition|(
name|myFieldName
operator|!=
literal|null
condition|)
name|myFieldName
operator|.
name|paste
argument_list|(
name|data
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{}
block|}
block|}
comment|// ---------------------------------------------------------------------------
DECL|class|CopyAction
class|class
name|CopyAction
extends|extends
name|BasicAction
block|{
DECL|method|CopyAction ()
specifier|public
name|CopyAction
parameter_list|()
block|{
name|super
argument_list|(
literal|"Copy to clipboard"
argument_list|,
literal|"Copy to clipboard"
argument_list|,
name|GUIGlobals
operator|.
name|getIconUrl
argument_list|(
literal|"copy"
argument_list|)
argument_list|)
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
try|try
block|{
comment|//        String data = ( String ) systemClip.getContents( null ).getTransferData(
comment|//            DataFlavor.stringFlavor ) ;
if|if
condition|(
name|myFieldName
operator|!=
literal|null
condition|)
block|{
name|String
name|data
init|=
name|myFieldName
operator|.
name|getSelectedText
argument_list|()
decl_stmt|;
if|if
condition|(
name|data
operator|!=
literal|null
condition|)
if|if
condition|(
name|data
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|ClipBoardManager
operator|.
name|clipBoard
operator|.
name|setClipboardContents
argument_list|(
name|data
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{}
block|}
block|}
DECL|class|ReplaceAction
class|class
name|ReplaceAction
extends|extends
name|BasicAction
block|{
DECL|method|ReplaceAction ()
specifier|public
name|ReplaceAction
parameter_list|()
block|{
name|super
argument_list|(
literal|"Replace comma by and where appropriate"
argument_list|)
expr_stmt|;
block|}
DECL|method|actionPerformed (ActionEvent evt)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|evt
parameter_list|)
block|{
if|if
condition|(
name|myFieldName
operator|.
name|getText
argument_list|()
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
return|return;
block|}
comment|//myFieldName.selectAll();
name|String
name|input
init|=
name|myFieldName
operator|.
name|getText
argument_list|()
decl_stmt|;
comment|//myFieldName.setText(input.replaceAll(","," and"));
name|myFieldName
operator|.
name|setText
argument_list|(
name|generalFixAuthor
argument_list|(
name|input
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|generalFixAuthor (String in)
specifier|public
specifier|static
name|String
name|generalFixAuthor
parameter_list|(
name|String
name|in
parameter_list|)
block|{
name|String
name|author
decl_stmt|;
name|String
index|[]
name|authors
init|=
name|in
operator|.
name|split
argument_list|(
literal|"( |,)and "
argument_list|,
operator|-
literal|1
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|authors
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|authors
index|[
name|i
index|]
operator|=
name|authors
index|[
name|i
index|]
operator|.
name|trim
argument_list|()
expr_stmt|;
block|}
comment|/* determine whether the last author name includes a comma          * 0 is intentional (consider -1 as alternative) */
name|author
operator|=
name|authors
index|[
name|authors
operator|.
name|length
operator|-
literal|1
index|]
expr_stmt|;
name|boolean
name|lnfn
init|=
operator|(
name|author
operator|.
name|indexOf
argument_list|(
literal|","
argument_list|)
operator|>
literal|0
operator|)
decl_stmt|;
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
comment|/*not tested!*/
if|if
condition|(
name|lnfn
condition|)
block|{
name|String
index|[]
name|parts
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|authors
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|parts
operator|=
name|authors
index|[
name|i
index|]
operator|.
name|split
argument_list|(
literal|","
argument_list|,
operator|-
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
name|parts
operator|.
name|length
operator|==
literal|2
condition|)
block|{
name|parts
index|[
literal|0
index|]
operator|=
name|parts
index|[
literal|0
index|]
operator|.
name|trim
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|" "
argument_list|,
literal|"~"
argument_list|)
expr_stmt|;
name|parts
index|[
literal|1
index|]
operator|=
name|parts
index|[
literal|1
index|]
operator|.
name|trim
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|" "
argument_list|,
literal|"~"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|parts
index|[
literal|1
index|]
operator|+
literal|" "
operator|+
name|parts
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|authors
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|i
operator|<
name|authors
operator|.
name|length
operator|-
literal|1
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|" and "
argument_list|)
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|authors
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|String
index|[]
name|iAuthors
init|=
name|authors
index|[
name|i
index|]
operator|.
name|split
argument_list|(
literal|","
argument_list|)
decl_stmt|;
name|String
index|[]
name|ijparts
decl_stmt|;
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|iAuthors
operator|.
name|length
condition|;
name|j
operator|++
control|)
block|{
name|iAuthors
index|[
name|j
index|]
operator|=
name|iAuthors
index|[
name|j
index|]
operator|.
name|trim
argument_list|()
expr_stmt|;
name|ijparts
operator|=
name|iAuthors
index|[
name|j
index|]
operator|.
name|split
argument_list|(
literal|" "
argument_list|,
operator|-
literal|1
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|k
init|=
literal|0
init|;
name|k
operator|<
name|ijparts
operator|.
name|length
condition|;
name|k
operator|++
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|ijparts
index|[
name|k
index|]
argument_list|)
expr_stmt|;
if|if
condition|(
name|k
operator|<
name|ijparts
operator|.
name|length
operator|-
literal|2
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|'~'
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|k
operator|==
name|ijparts
operator|.
name|length
operator|-
literal|2
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|j
operator|<
name|iAuthors
operator|.
name|length
operator|-
literal|1
operator|||
name|i
operator|<
name|authors
operator|.
name|length
operator|-
literal|1
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|" and "
argument_list|)
expr_stmt|;
block|}
block|}
comment|/* end of j-loop (authors split by ,) */
block|}
comment|/* end of i-loop (authors split by and)*/
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

