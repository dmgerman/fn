begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2002-2003 Nizar N. Batada nbatada@stanford.edu All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
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
name|*
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
name|event
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

begin_class
DECL|class|EntryCustomizationDialog
class|class
name|EntryCustomizationDialog
extends|extends
name|JDialog
implements|implements
name|ItemListener
block|{
DECL|field|type
name|BibtexEntryType
name|type
decl_stmt|;
DECL|field|reqSP
DECL|field|optSP
name|JScrollPane
name|reqSP
decl_stmt|,
name|optSP
decl_stmt|;
DECL|field|ok
DECL|field|cancel
DECL|field|helpButton
DECL|field|delete
name|JButton
name|ok
decl_stmt|,
name|cancel
decl_stmt|,
name|helpButton
decl_stmt|,
name|delete
decl_stmt|;
DECL|field|panel
name|JPanel
name|panel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|,
DECL|field|fieldPanel
name|fieldPanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|,
DECL|field|typePanel
name|typePanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|field|width
name|int
name|width
init|=
literal|10
decl_stmt|;
DECL|field|messageLabel
name|JLabel
name|messageLabel
init|=
operator|new
name|JLabel
argument_list|(
literal|""
argument_list|,
name|SwingConstants
operator|.
name|CENTER
argument_list|)
decl_stmt|;
DECL|field|name
name|JTextField
name|name
init|=
operator|new
name|JTextField
argument_list|(
literal|""
argument_list|,
name|width
argument_list|)
decl_stmt|;
DECL|field|req_ta
name|JTextArea
name|req_ta
init|=
operator|new
name|JTextArea
argument_list|(
literal|""
argument_list|,
literal|1
argument_list|,
name|width
argument_list|)
decl_stmt|,
comment|//10 row, 20 columns
DECL|field|opt_ta
name|opt_ta
init|=
operator|new
name|JTextArea
argument_list|(
literal|""
argument_list|,
literal|1
argument_list|,
name|width
argument_list|)
decl_stmt|;
comment|//10 row, 20 columns
comment|// need to get FIeld name from somewhere
DECL|field|types_cb
name|JComboBox
name|types_cb
init|=
operator|new
name|JComboBox
argument_list|()
decl_stmt|;
DECL|field|help
name|HelpAction
name|help
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
DECL|field|buttonPanel
name|JPanel
name|buttonPanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|method|EntryCustomizationDialog (JabRefFrame parent)
specifier|public
name|EntryCustomizationDialog
parameter_list|(
name|JabRefFrame
name|parent
parameter_list|)
block|{
comment|//Type=Article, Book etc
comment|// templateName will be used to put on the dialog frame
comment|// create 10 default entries
comment|// return an array
name|super
argument_list|(
name|parent
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Customize entry types"
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|help
operator|=
operator|new
name|HelpAction
argument_list|(
name|parent
operator|.
name|helpDiag
argument_list|,
name|GUIGlobals
operator|.
name|customEntriesHelp
argument_list|,
literal|"Help"
argument_list|,
name|GUIGlobals
operator|.
name|helpSmallIconFile
argument_list|)
expr_stmt|;
name|setTypeSelection
argument_list|()
expr_stmt|;
name|setSize
argument_list|(
literal|400
argument_list|,
literal|400
argument_list|)
expr_stmt|;
name|initialize
argument_list|()
expr_stmt|;
name|makeButtons
argument_list|()
expr_stmt|;
name|reqSP
operator|=
operator|new
name|JScrollPane
argument_list|(
name|req_ta
argument_list|,
name|JScrollPane
operator|.
name|VERTICAL_SCROLLBAR_AS_NEEDED
argument_list|,
name|JScrollPane
operator|.
name|HORIZONTAL_SCROLLBAR_NEVER
argument_list|)
expr_stmt|;
name|optSP
operator|=
operator|new
name|JScrollPane
argument_list|(
name|opt_ta
argument_list|,
name|JScrollPane
operator|.
name|VERTICAL_SCROLLBAR_AS_NEEDED
argument_list|,
name|JScrollPane
operator|.
name|HORIZONTAL_SCROLLBAR_NEVER
argument_list|)
expr_stmt|;
comment|//helpButton = new JButton(help);
comment|//helpButton.setText(null);
name|JToolBar
name|tlb
init|=
operator|new
name|JToolBar
argument_list|()
decl_stmt|;
name|tlb
operator|.
name|setFloatable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|tlb
operator|.
name|add
argument_list|(
name|help
argument_list|)
expr_stmt|;
name|panel
operator|.
name|setBackground
argument_list|(
name|GUIGlobals
operator|.
name|lightGray
argument_list|)
expr_stmt|;
name|buttonPanel
operator|.
name|setBackground
argument_list|(
name|GUIGlobals
operator|.
name|lightGray
argument_list|)
expr_stmt|;
name|panel
operator|.
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|typePanel
operator|.
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|fieldPanel
operator|.
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
comment|//panel.setBorder(BorderFactory.createEtchedBorder());
name|fieldPanel
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEtchedBorder
argument_list|()
argument_list|)
expr_stmt|;
name|typePanel
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEtchedBorder
argument_list|()
argument_list|)
expr_stmt|;
name|JLabel
name|lab
init|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Type: "
argument_list|)
argument_list|)
decl_stmt|,
name|lab2
init|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Name: "
argument_list|)
argument_list|)
decl_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|lab
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|lab2
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|types_cb
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|HORIZONTAL
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|name
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|NONE
expr_stmt|;
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
name|weightx
operator|=
literal|0
expr_stmt|;
comment|//gbl.setConstraints(helpButton, con);
name|gbl
operator|.
name|setConstraints
argument_list|(
name|tlb
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|typePanel
operator|.
name|add
argument_list|(
name|lab
argument_list|)
expr_stmt|;
name|typePanel
operator|.
name|add
argument_list|(
name|types_cb
argument_list|)
expr_stmt|;
name|typePanel
operator|.
name|add
argument_list|(
name|lab2
argument_list|)
expr_stmt|;
name|typePanel
operator|.
name|add
argument_list|(
name|name
argument_list|)
expr_stmt|;
comment|//typePanel.add(helpButton);
name|typePanel
operator|.
name|add
argument_list|(
name|tlb
argument_list|)
expr_stmt|;
name|lab
operator|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Required fields"
argument_list|)
argument_list|)
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|BOTH
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|lab
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|con
operator|.
name|weighty
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|reqSP
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|fieldPanel
operator|.
name|add
argument_list|(
name|lab
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|lab
operator|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Optional fields"
argument_list|)
argument_list|)
expr_stmt|;
name|con
operator|.
name|weighty
operator|=
literal|0
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|lab
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|fieldPanel
operator|.
name|add
argument_list|(
name|lab
argument_list|)
expr_stmt|;
name|con
operator|.
name|weighty
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|optSP
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|fieldPanel
operator|.
name|add
argument_list|(
name|reqSP
argument_list|)
expr_stmt|;
name|fieldPanel
operator|.
name|add
argument_list|(
name|optSP
argument_list|)
expr_stmt|;
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
name|weighty
operator|=
literal|0
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|typePanel
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|con
operator|.
name|weighty
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|fieldPanel
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|con
operator|.
name|weighty
operator|=
literal|0
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|messageLabel
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|typePanel
argument_list|)
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|fieldPanel
argument_list|)
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|messageLabel
argument_list|)
expr_stmt|;
name|name
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
block|}
DECL|method|EntryCustomizationDialog (JabRefFrame parent, BibtexEntryType type_)
specifier|public
name|EntryCustomizationDialog
parameter_list|(
name|JabRefFrame
name|parent
parameter_list|,
name|BibtexEntryType
name|type_
parameter_list|)
block|{
name|this
argument_list|(
name|parent
argument_list|)
expr_stmt|;
name|type
operator|=
name|type_
expr_stmt|;
block|}
DECL|method|initialize ()
name|void
name|initialize
parameter_list|()
block|{
name|setModal
argument_list|(
literal|true
argument_list|)
expr_stmt|;
comment|//setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
name|getContentPane
argument_list|()
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|buttonPanel
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|panel
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|messageLabel
operator|.
name|setForeground
argument_list|(
name|Color
operator|.
name|black
argument_list|)
expr_stmt|;
name|messageLabel
operator|.
name|setText
argument_list|(
literal|"Delimit fields with semicolon, ex.: author;title;journal"
argument_list|)
expr_stmt|;
name|types_cb
operator|.
name|addItemListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
DECL|method|save ()
name|void
name|save
parameter_list|()
block|{
name|String
name|reqStr
init|=
name|req_ta
operator|.
name|getText
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|"\\s+"
argument_list|,
literal|""
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"\\n+"
argument_list|,
literal|""
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|,
name|optStr
init|=
name|opt_ta
operator|.
name|getText
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|"\\s+"
argument_list|,
literal|""
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"\\n+"
argument_list|,
literal|""
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
name|String
name|typeName
init|=
name|name
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|typeName
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|CustomEntryType
name|typ
init|=
operator|new
name|CustomEntryType
argument_list|(
name|typeName
operator|.
name|toLowerCase
argument_list|()
argument_list|,
name|reqStr
argument_list|,
name|optStr
argument_list|)
decl_stmt|;
name|BibtexEntryType
operator|.
name|ALL_TYPES
operator|.
name|put
argument_list|(
name|typ
operator|.
name|getName
argument_list|()
argument_list|,
name|typ
argument_list|)
expr_stmt|;
name|setTypeSelection
argument_list|()
expr_stmt|;
name|messageLabel
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Stored definition for type"
operator|+
literal|" '"
operator|+
name|Util
operator|.
name|nCase
argument_list|(
name|typ
operator|.
name|getName
argument_list|()
argument_list|)
operator|+
literal|"'."
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|messageLabel
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"You must fill in a name for the entry type."
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|setTypeSelection ()
specifier|private
name|void
name|setTypeSelection
parameter_list|()
block|{
name|types_cb
operator|.
name|removeAllItems
argument_list|()
expr_stmt|;
name|types_cb
operator|.
name|addItem
argument_list|(
literal|"<new>"
argument_list|)
expr_stmt|;
name|Iterator
name|i
init|=
name|BibtexEntryType
operator|.
name|ALL_TYPES
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|BibtexEntryType
name|type
decl_stmt|;
name|String
name|toSet
decl_stmt|;
while|while
condition|(
name|i
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|type
operator|=
name|BibtexEntryType
operator|.
name|getType
argument_list|(
operator|(
name|String
operator|)
name|i
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
name|toSet
operator|=
name|Util
operator|.
name|nCase
argument_list|(
name|type
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|type
operator|instanceof
name|CustomEntryType
condition|)
name|toSet
operator|=
name|toSet
operator|+
literal|" *"
expr_stmt|;
name|types_cb
operator|.
name|addItem
argument_list|(
name|toSet
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|makeButtons ()
name|void
name|makeButtons
parameter_list|()
block|{
name|ok
operator|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Store"
argument_list|)
argument_list|)
expr_stmt|;
name|cancel
operator|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Close"
argument_list|)
argument_list|)
expr_stmt|;
name|delete
operator|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Delete custom"
argument_list|)
argument_list|)
expr_stmt|;
name|buttonPanel
operator|.
name|add
argument_list|(
name|ok
argument_list|)
expr_stmt|;
name|buttonPanel
operator|.
name|add
argument_list|(
name|delete
argument_list|)
expr_stmt|;
name|buttonPanel
operator|.
name|add
argument_list|(
name|cancel
argument_list|)
expr_stmt|;
name|ok
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
name|save
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|cancel
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
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|delete
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
name|BibtexEntryType
name|type
init|=
name|BibtexEntryType
operator|.
name|getType
argument_list|(
name|name
operator|.
name|getText
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|type
operator|==
literal|null
condition|)
name|messageLabel
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"There is no entry type"
argument_list|)
operator|+
literal|" '"
operator|+
name|Util
operator|.
name|nCase
argument_list|(
name|name
operator|.
name|getText
argument_list|()
argument_list|)
operator|+
literal|"' "
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"defined."
argument_list|)
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
operator|!
operator|(
name|type
operator|instanceof
name|CustomEntryType
operator|)
condition|)
name|messageLabel
operator|.
name|setText
argument_list|(
literal|"'"
operator|+
name|type
operator|.
name|getName
argument_list|()
operator|+
literal|"' "
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"is a standard type."
argument_list|)
argument_list|)
expr_stmt|;
else|else
block|{
name|BibtexEntryType
operator|.
name|removeType
argument_list|(
name|name
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|setTypeSelection
argument_list|()
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
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
name|types_cb
operator|.
name|getSelectedIndex
argument_list|()
operator|>
literal|0
condition|)
block|{
comment|// User has selected one of the existing types.
name|String
name|name
init|=
operator|(
name|String
operator|)
name|types_cb
operator|.
name|getSelectedItem
argument_list|()
decl_stmt|;
name|updateToType
argument_list|(
operator|(
name|name
operator|.
name|split
argument_list|(
literal|" "
argument_list|)
operator|)
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|name
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|req_ta
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|opt_ta
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|name
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|updateToType (String o)
specifier|public
name|void
name|updateToType
parameter_list|(
name|String
name|o
parameter_list|)
block|{
name|BibtexEntryType
name|type
init|=
name|BibtexEntryType
operator|.
name|getType
argument_list|(
name|o
argument_list|)
decl_stmt|;
name|name
operator|.
name|setText
argument_list|(
name|type
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|req_ta
operator|.
name|setText
argument_list|(
name|Util
operator|.
name|stringArrayToDelimited
argument_list|(
name|type
operator|.
name|getRequiredFields
argument_list|()
argument_list|,
literal|";\n"
argument_list|)
argument_list|)
expr_stmt|;
name|opt_ta
operator|.
name|setText
argument_list|(
name|Util
operator|.
name|stringArrayToDelimited
argument_list|(
name|type
operator|.
name|getOptionalFields
argument_list|()
argument_list|,
literal|";\n"
argument_list|)
argument_list|)
expr_stmt|;
name|req_ta
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

