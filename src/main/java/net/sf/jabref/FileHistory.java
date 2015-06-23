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
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|LinkedList
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JMenu
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JMenuItem
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JOptionPane
import|;
end_import

begin_class
DECL|class|FileHistory
specifier|public
class|class
name|FileHistory
extends|extends
name|JMenu
implements|implements
name|ActionListener
block|{
DECL|field|prefs
specifier|private
specifier|final
name|JabRefPreferences
name|prefs
decl_stmt|;
DECL|field|history
specifier|private
specifier|final
name|LinkedList
argument_list|<
name|String
argument_list|>
name|history
init|=
operator|new
name|LinkedList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|method|FileHistory (JabRefPreferences prefs, JabRefFrame frame)
specifier|public
name|FileHistory
parameter_list|(
name|JabRefPreferences
name|prefs
parameter_list|,
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|String
name|name
init|=
name|Globals
operator|.
name|menuTitle
argument_list|(
literal|"Recent files"
argument_list|)
decl_stmt|;
name|int
name|i
init|=
name|name
operator|.
name|indexOf
argument_list|(
literal|'&'
argument_list|)
decl_stmt|;
if|if
condition|(
name|i
operator|>=
literal|0
condition|)
block|{
name|setText
argument_list|(
name|name
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|i
argument_list|)
operator|+
name|name
operator|.
name|substring
argument_list|(
name|i
operator|+
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|char
name|mnemonic
init|=
name|Character
operator|.
name|toUpperCase
argument_list|(
name|name
operator|.
name|charAt
argument_list|(
name|i
operator|+
literal|1
argument_list|)
argument_list|)
decl_stmt|;
name|setMnemonic
argument_list|(
operator|(
name|int
operator|)
name|mnemonic
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|setText
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
name|this
operator|.
name|prefs
operator|=
name|prefs
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|String
index|[]
name|old
init|=
name|prefs
operator|.
name|getStringArray
argument_list|(
literal|"recentFiles"
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|old
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|old
operator|.
name|length
operator|>
literal|0
operator|)
condition|)
block|{
for|for
control|(
name|i
operator|=
literal|0
init|;
name|i
operator|<
name|old
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|history
operator|.
name|addFirst
argument_list|(
name|old
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
name|setItems
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Adds the file name to the top of the menu. If it already is in      * the menu, it is merely moved to the top.      *      * @param filename a<code>String</code> value      */
DECL|method|newFile (String filename)
specifier|public
name|void
name|newFile
parameter_list|(
name|String
name|filename
parameter_list|)
block|{
name|int
name|i
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|i
operator|<
name|history
operator|.
name|size
argument_list|()
condition|)
block|{
if|if
condition|(
name|history
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|equals
argument_list|(
name|filename
argument_list|)
condition|)
block|{
name|history
operator|.
name|remove
argument_list|(
name|i
operator|--
argument_list|)
expr_stmt|;
block|}
name|i
operator|++
expr_stmt|;
block|}
name|history
operator|.
name|addFirst
argument_list|(
name|filename
argument_list|)
expr_stmt|;
while|while
condition|(
name|history
operator|.
name|size
argument_list|()
operator|>
name|prefs
operator|.
name|getInt
argument_list|(
literal|"historySize"
argument_list|)
condition|)
block|{
name|history
operator|.
name|removeLast
argument_list|()
expr_stmt|;
block|}
name|setItems
argument_list|()
expr_stmt|;
if|if
condition|(
operator|!
name|isEnabled
argument_list|()
condition|)
block|{
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|setItems ()
specifier|private
name|void
name|setItems
parameter_list|()
block|{
name|removeAll
argument_list|()
expr_stmt|;
name|Iterator
argument_list|<
name|String
argument_list|>
name|i
init|=
name|history
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|int
name|count
init|=
literal|1
decl_stmt|;
while|while
condition|(
name|i
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|addItem
argument_list|(
name|i
operator|.
name|next
argument_list|()
argument_list|,
name|count
operator|++
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|addItem (String filename, int num)
specifier|private
name|void
name|addItem
parameter_list|(
name|String
name|filename
parameter_list|,
name|int
name|num
parameter_list|)
block|{
name|String
name|number
init|=
name|num
operator|+
literal|""
decl_stmt|;
name|JMenuItem
name|item
init|=
operator|new
name|JMenuItem
argument_list|(
name|number
operator|+
literal|". "
operator|+
name|filename
argument_list|)
decl_stmt|;
name|char
name|mnemonic
init|=
name|Character
operator|.
name|toUpperCase
argument_list|(
name|number
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
argument_list|)
decl_stmt|;
name|item
operator|.
name|setMnemonic
argument_list|(
operator|(
name|int
operator|)
name|mnemonic
argument_list|)
expr_stmt|;
name|item
operator|.
name|addActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|item
argument_list|)
expr_stmt|;
comment|//history.addFirst(item);
block|}
DECL|method|removeItem (String filename)
specifier|private
name|void
name|removeItem
parameter_list|(
name|String
name|filename
parameter_list|)
block|{
name|int
name|i
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|i
operator|<
name|history
operator|.
name|size
argument_list|()
condition|)
block|{
if|if
condition|(
name|history
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|equals
argument_list|(
name|filename
argument_list|)
condition|)
block|{
name|history
operator|.
name|remove
argument_list|(
name|i
argument_list|)
expr_stmt|;
name|setItems
argument_list|()
expr_stmt|;
return|return;
block|}
name|i
operator|++
expr_stmt|;
block|}
block|}
DECL|method|storeHistory ()
specifier|public
name|void
name|storeHistory
parameter_list|()
block|{
if|if
condition|(
name|history
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
name|String
index|[]
name|names
init|=
operator|new
name|String
index|[
name|history
operator|.
name|size
argument_list|()
index|]
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
name|names
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|names
index|[
name|i
index|]
operator|=
name|history
operator|.
name|get
argument_list|(
name|i
argument_list|)
expr_stmt|;
block|}
name|prefs
operator|.
name|putStringArray
argument_list|(
literal|"recentFiles"
argument_list|,
name|names
argument_list|)
expr_stmt|;
block|}
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
name|name
init|=
operator|(
operator|(
name|JMenuItem
operator|)
name|e
operator|.
name|getSource
argument_list|()
operator|)
operator|.
name|getText
argument_list|()
decl_stmt|;
name|int
name|pos
init|=
name|name
operator|.
name|indexOf
argument_list|(
literal|" "
argument_list|)
decl_stmt|;
name|name
operator|=
name|name
operator|.
name|substring
argument_list|(
name|pos
operator|+
literal|1
argument_list|)
expr_stmt|;
comment|//Util.pr("'"+name+"'");
specifier|final
name|File
name|fileToOpen
init|=
operator|new
name|File
argument_list|(
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|fileToOpen
operator|.
name|exists
argument_list|()
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"File not found"
argument_list|)
operator|+
literal|": "
operator|+
name|fileToOpen
operator|.
name|getName
argument_list|()
argument_list|,
literal|"Error"
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
name|removeItem
argument_list|(
name|name
argument_list|)
expr_stmt|;
return|return;
block|}
name|JabRefExecutorService
operator|.
name|INSTANCE
operator|.
name|execute
argument_list|(
operator|new
name|Runnable
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
name|frame
operator|.
name|open
operator|.
name|openIt
argument_list|(
name|fileToOpen
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

