begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 Morten O. Alver, Nizar N. Batada  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
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
name|util
operator|.
name|Vector
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
name|java
operator|.
name|awt
operator|.
name|*
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
name|*
import|;
end_import

begin_class
DECL|class|FieldContentSelector
class|class
name|FieldContentSelector
extends|extends
name|JComponent
implements|implements
name|ItemListener
block|{
DECL|field|DELIMITER
DECL|field|DELIMITER_2
specifier|final
name|String
name|DELIMITER
init|=
literal|" "
decl_stmt|,
name|DELIMITER_2
init|=
literal|""
decl_stmt|;
DECL|field|editor
name|FieldEditor
name|editor
decl_stmt|;
DECL|field|list
name|JComboBox
name|list
init|=
operator|new
name|JComboBox
argument_list|()
block|{
specifier|public
name|Dimension
name|getPreferredSize
parameter_list|()
block|{
name|Dimension
name|parents
init|=
name|super
operator|.
name|getPreferredSize
argument_list|()
decl_stmt|;
if|if
condition|(
name|parents
operator|.
name|width
operator|>
name|GUIGlobals
operator|.
name|MAX_CONTENT_SELECTOR_WIDTH
condition|)
name|parents
operator|.
name|width
operator|=
name|GUIGlobals
operator|.
name|MAX_CONTENT_SELECTOR_WIDTH
expr_stmt|;
return|return
name|parents
return|;
block|}
block|}
decl_stmt|;
DECL|field|manage
name|JButton
name|manage
decl_stmt|;
DECL|field|gbl
name|GridBagLayout
name|gbl
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
DECL|field|con
name|GridBagConstraints
name|con
init|=
operator|new
name|GridBagConstraints
argument_list|()
decl_stmt|;
DECL|field|parent
name|EntryEditor
name|parent
decl_stmt|;
DECL|field|metaData
name|MetaData
name|metaData
decl_stmt|;
DECL|method|FieldContentSelector (EntryEditor parent, FieldEditor editor_, MetaData data)
specifier|public
name|FieldContentSelector
parameter_list|(
name|EntryEditor
name|parent
parameter_list|,
name|FieldEditor
name|editor_
parameter_list|,
name|MetaData
name|data
parameter_list|)
block|{
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|this
operator|.
name|editor
operator|=
name|editor_
expr_stmt|;
name|this
operator|.
name|parent
operator|=
name|parent
expr_stmt|;
name|metaData
operator|=
name|data
expr_stmt|;
name|list
operator|.
name|setEditable
argument_list|(
literal|true
argument_list|)
expr_stmt|;
specifier|final
name|MetaData
name|metaData
init|=
name|data
decl_stmt|;
specifier|final
name|JabRefFrame
name|frame
init|=
name|parent
operator|.
name|frame
decl_stmt|;
specifier|final
name|BasePanel
name|panel
init|=
name|parent
operator|.
name|panel
decl_stmt|;
name|updateList
argument_list|()
expr_stmt|;
comment|//else
comment|//    list = new JComboBox(items.toArray());
name|con
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|HORIZONTAL
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
comment|//list.setPreferredSize(new Dimension(140, list.getPreferredSize().height));
name|gbl
operator|.
name|setConstraints
argument_list|(
name|list
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|list
operator|.
name|addItemListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|list
argument_list|)
expr_stmt|;
name|manage
operator|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Manage"
argument_list|)
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|manage
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|manage
argument_list|)
expr_stmt|;
name|manage
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
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
name|ContentSelectorDialog2
name|csd
init|=
operator|new
name|ContentSelectorDialog2
argument_list|(
name|frame
argument_list|,
name|panel
argument_list|,
literal|false
argument_list|,
name|metaData
argument_list|,
name|editor
operator|.
name|getFieldName
argument_list|()
argument_list|)
decl_stmt|;
name|Util
operator|.
name|placeDialog
argument_list|(
name|csd
argument_list|,
name|frame
argument_list|)
expr_stmt|;
name|csd
operator|.
name|show
argument_list|()
expr_stmt|;
comment|//updateList();
block|}
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|updateList ()
specifier|public
name|void
name|updateList
parameter_list|()
block|{
name|list
operator|.
name|removeAllItems
argument_list|()
expr_stmt|;
name|list
operator|.
name|addItem
argument_list|(
literal|""
argument_list|)
expr_stmt|;
comment|//(Globals.lang(""));
name|Vector
name|items
init|=
name|metaData
operator|.
name|getData
argument_list|(
name|Globals
operator|.
name|SELECTOR_META_PREFIX
operator|+
name|editor
operator|.
name|getFieldName
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|items
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|items
operator|.
name|size
argument_list|()
operator|>
literal|0
operator|)
condition|)
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
name|items
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
name|list
operator|.
name|addItem
argument_list|(
name|items
operator|.
name|elementAt
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
comment|//list = new JComboBox();
block|}
block|}
DECL|method|itemStateChanged (ItemEvent e)
specifier|public
name|void
name|itemStateChanged
parameter_list|(
name|ItemEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|.
name|getStateChange
argument_list|()
operator|==
name|ItemEvent
operator|.
name|DESELECTED
condition|)
return|return;
comment|// We get an uninteresting DESELECTED event as well.
if|if
condition|(
name|list
operator|.
name|getSelectedIndex
argument_list|()
operator|==
literal|0
condition|)
return|return;
comment|// The first element is only for show.
name|String
name|chosen
init|=
operator|(
name|String
operator|)
name|list
operator|.
name|getSelectedItem
argument_list|()
decl_stmt|;
if|if
condition|(
name|list
operator|.
name|getSelectedIndex
argument_list|()
operator|==
operator|-
literal|1
condition|)
block|{
comment|// User edited in a new word. Add this.
name|addWord
argument_list|(
name|chosen
argument_list|)
expr_stmt|;
comment|/*   Vector items = metaData.getData(Globals.SELECTOR_META_PREFIX+ 					    editor.getFieldName()); 	    boolean exists = false; 	    int pos = -1; 	    for (int i=0; i<items.size(); i++) { 		String s = (String)items.elementAt(i); 		if (s.equals(chosen)) { 		    exists = true; 		    break; 		} 		if (s.toLowerCase().compareTo(chosen.toLowerCase())< 0) 		    pos = i+1; 	    } 	    if (!exists) { 		items.add(Math.max(0, pos), chosen); 		parent.panel.markNonUndoableBaseChanged(); 		updateList(); 	    }*/
block|}
if|if
condition|(
operator|!
name|editor
operator|.
name|getText
argument_list|()
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
name|editor
operator|.
name|append
argument_list|(
name|DELIMITER
argument_list|)
expr_stmt|;
name|editor
operator|.
name|append
argument_list|(
name|chosen
operator|+
name|DELIMITER_2
argument_list|)
expr_stmt|;
name|list
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|parent
operator|.
name|storeFieldAction
operator|.
name|actionPerformed
argument_list|(
operator|new
name|ActionEvent
argument_list|(
name|editor
argument_list|,
literal|0
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
comment|// Transfer focus to the editor.
name|editor
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
comment|//new FocusRequester(editor.getTextComponent());
block|}
comment|/**    * Adds a word to the selector (to the JList and to the MetaData), unless it    * is already there    *    * @param newWord String Word to add    */
DECL|method|addWord (String newWord)
specifier|public
name|void
name|addWord
parameter_list|(
name|String
name|newWord
parameter_list|)
block|{
name|Vector
name|items
init|=
name|metaData
operator|.
name|getData
argument_list|(
name|Globals
operator|.
name|SELECTOR_META_PREFIX
operator|+
name|editor
operator|.
name|getFieldName
argument_list|()
argument_list|)
decl_stmt|;
name|boolean
name|exists
init|=
literal|false
decl_stmt|;
name|int
name|pos
init|=
operator|-
literal|1
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
name|items
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|String
name|s
init|=
operator|(
name|String
operator|)
name|items
operator|.
name|elementAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|s
operator|.
name|equals
argument_list|(
name|newWord
argument_list|)
condition|)
block|{
name|exists
operator|=
literal|true
expr_stmt|;
break|break;
block|}
if|if
condition|(
name|s
operator|.
name|toLowerCase
argument_list|()
operator|.
name|compareTo
argument_list|(
name|newWord
operator|.
name|toLowerCase
argument_list|()
argument_list|)
operator|<
literal|0
condition|)
name|pos
operator|=
name|i
operator|+
literal|1
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|exists
condition|)
block|{
name|items
operator|.
name|add
argument_list|(
name|Math
operator|.
name|max
argument_list|(
literal|0
argument_list|,
name|pos
argument_list|)
argument_list|,
name|newWord
argument_list|)
expr_stmt|;
name|parent
operator|.
name|panel
operator|.
name|markNonUndoableBaseChanged
argument_list|()
expr_stmt|;
name|parent
operator|.
name|panel
operator|.
name|updateAllContentSelectors
argument_list|()
expr_stmt|;
comment|//updateList();
block|}
block|}
block|}
end_class

end_unit

