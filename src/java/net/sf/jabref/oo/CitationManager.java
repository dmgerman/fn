begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.oo
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|oo
package|;
end_package

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|BasicEventList
import|;
end_import

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|EventList
import|;
end_import

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|gui
operator|.
name|TableFormat
import|;
end_import

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|swing
operator|.
name|EventTableModel
import|;
end_import

begin_import
import|import
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|builder
operator|.
name|ButtonBarBuilder
import|;
end_import

begin_import
import|import
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|builder
operator|.
name|DefaultFormBuilder
import|;
end_import

begin_import
import|import
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|layout
operator|.
name|FormLayout
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|container
operator|.
name|XNameAccess
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
name|Globals
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
name|JabRefFrame
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
name|MouseAdapter
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
name|util
operator|.
name|List
import|;
end_import

begin_comment
comment|/**  * Dialog for modifying existing citations.  */
end_comment

begin_class
DECL|class|CitationManager
specifier|public
class|class
name|CitationManager
block|{
DECL|field|ooBase
name|OOBibBase
name|ooBase
decl_stmt|;
DECL|field|diag
name|JDialog
name|diag
decl_stmt|;
DECL|field|list
name|EventList
argument_list|<
name|CitEntry
argument_list|>
name|list
decl_stmt|;
DECL|field|table
name|JTable
name|table
decl_stmt|;
DECL|field|tableModel
name|EventTableModel
argument_list|<
name|CitEntry
argument_list|>
name|tableModel
decl_stmt|;
DECL|field|ok
name|JButton
name|ok
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Ok"
argument_list|)
argument_list|)
decl_stmt|,
DECL|field|cancel
name|cancel
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
argument_list|)
decl_stmt|;
DECL|method|CitationManager (final JabRefFrame frame, OOBibBase ooBase)
specifier|public
name|CitationManager
parameter_list|(
specifier|final
name|JabRefFrame
name|frame
parameter_list|,
name|OOBibBase
name|ooBase
parameter_list|)
throws|throws
name|Exception
block|{
name|diag
operator|=
operator|new
name|JDialog
argument_list|(
name|frame
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Manage citations"
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|this
operator|.
name|ooBase
operator|=
name|ooBase
expr_stmt|;
name|list
operator|=
operator|new
name|BasicEventList
argument_list|<
name|CitEntry
argument_list|>
argument_list|()
expr_stmt|;
name|XNameAccess
name|nameAccess
init|=
name|ooBase
operator|.
name|getReferenceMarks
argument_list|()
decl_stmt|;
name|String
index|[]
name|names
init|=
name|ooBase
operator|.
name|getJabRefReferenceMarks
argument_list|(
name|nameAccess
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|name
range|:
name|names
control|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|keys
init|=
name|ooBase
operator|.
name|parseRefMarkName
argument_list|(
name|name
argument_list|)
decl_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|CitEntry
argument_list|(
name|name
argument_list|,
name|keys
argument_list|,
literal|"<html>..."
operator|+
name|ooBase
operator|.
name|getCitationContext
argument_list|(
name|nameAccess
argument_list|,
name|name
argument_list|,
literal|30
argument_list|,
literal|30
argument_list|,
literal|true
argument_list|)
operator|+
literal|"...</html>"
argument_list|,
name|ooBase
operator|.
name|getCustomProperty
argument_list|(
name|name
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|tableModel
operator|=
operator|new
name|EventTableModel
argument_list|<
name|CitEntry
argument_list|>
argument_list|(
name|list
argument_list|,
operator|new
name|CitEntryFormat
argument_list|()
argument_list|)
expr_stmt|;
name|table
operator|=
operator|new
name|JTable
argument_list|(
name|tableModel
argument_list|)
expr_stmt|;
name|diag
operator|.
name|add
argument_list|(
operator|new
name|JScrollPane
argument_list|(
name|table
argument_list|)
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|ButtonBarBuilder
name|bb
init|=
operator|new
name|ButtonBarBuilder
argument_list|()
decl_stmt|;
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|ok
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|cancel
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|bb
operator|.
name|getPanel
argument_list|()
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|)
argument_list|)
expr_stmt|;
name|diag
operator|.
name|add
argument_list|(
name|bb
operator|.
name|getPanel
argument_list|()
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
name|diag
operator|.
name|pack
argument_list|()
expr_stmt|;
name|diag
operator|.
name|setSize
argument_list|(
literal|700
argument_list|,
literal|400
argument_list|)
expr_stmt|;
name|Action
name|okAction
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
name|actionEvent
parameter_list|)
block|{
try|try
block|{
name|storeSettings
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
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
literal|"Problem modifying citation"
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|diag
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
decl_stmt|;
name|ok
operator|.
name|addActionListener
argument_list|(
name|okAction
argument_list|)
expr_stmt|;
name|Action
name|cancelAction
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
name|actionEvent
parameter_list|)
block|{
name|diag
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
decl_stmt|;
name|cancel
operator|.
name|addActionListener
argument_list|(
name|cancelAction
argument_list|)
expr_stmt|;
name|bb
operator|.
name|getPanel
argument_list|()
operator|.
name|getInputMap
argument_list|(
name|JComponent
operator|.
name|WHEN_IN_FOCUSED_WINDOW
argument_list|)
operator|.
name|put
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getKey
argument_list|(
literal|"Close dialog"
argument_list|)
argument_list|,
literal|"close"
argument_list|)
expr_stmt|;
name|bb
operator|.
name|getPanel
argument_list|()
operator|.
name|getActionMap
argument_list|()
operator|.
name|put
argument_list|(
literal|"close"
argument_list|,
name|cancelAction
argument_list|)
expr_stmt|;
name|table
operator|.
name|getColumnModel
argument_list|()
operator|.
name|getColumn
argument_list|(
literal|0
argument_list|)
operator|.
name|setPreferredWidth
argument_list|(
literal|600
argument_list|)
expr_stmt|;
name|table
operator|.
name|getColumnModel
argument_list|()
operator|.
name|getColumn
argument_list|(
literal|1
argument_list|)
operator|.
name|setPreferredWidth
argument_list|(
literal|90
argument_list|)
expr_stmt|;
name|table
operator|.
name|setPreferredScrollableViewportSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|700
argument_list|,
literal|500
argument_list|)
argument_list|)
expr_stmt|;
name|table
operator|.
name|addMouseListener
argument_list|(
operator|new
name|TableClickListener
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|storeSettings ()
specifier|private
name|void
name|storeSettings
parameter_list|()
throws|throws
name|Exception
block|{
for|for
control|(
name|CitEntry
name|entry
range|:
name|list
control|)
block|{
if|if
condition|(
name|entry
operator|.
name|pageInfoChanged
argument_list|()
condition|)
block|{
name|ooBase
operator|.
name|setCustomProperty
argument_list|(
name|entry
operator|.
name|refMarkName
argument_list|,
name|entry
operator|.
name|pageInfo
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|showDialog ()
specifier|public
name|void
name|showDialog
parameter_list|()
block|{
name|diag
operator|.
name|setLocationRelativeTo
argument_list|(
name|diag
operator|.
name|getParent
argument_list|()
argument_list|)
expr_stmt|;
name|diag
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
DECL|class|CitEntry
class|class
name|CitEntry
implements|implements
name|Comparable
argument_list|<
name|CitEntry
argument_list|>
block|{
DECL|field|refMarkName
DECL|field|pageInfo
DECL|field|keyString
DECL|field|context
DECL|field|origPageInfo
name|String
name|refMarkName
decl_stmt|,
name|pageInfo
decl_stmt|,
name|keyString
decl_stmt|,
name|context
decl_stmt|,
name|origPageInfo
decl_stmt|;
DECL|field|keys
name|List
argument_list|<
name|String
argument_list|>
name|keys
decl_stmt|;
DECL|method|CitEntry (String refMarkName, List<String> keys, String context, String pageInfo)
specifier|public
name|CitEntry
parameter_list|(
name|String
name|refMarkName
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|keys
parameter_list|,
name|String
name|context
parameter_list|,
name|String
name|pageInfo
parameter_list|)
block|{
name|this
operator|.
name|refMarkName
operator|=
name|refMarkName
expr_stmt|;
name|this
operator|.
name|keys
operator|=
name|keys
expr_stmt|;
name|this
operator|.
name|context
operator|=
name|context
expr_stmt|;
name|this
operator|.
name|pageInfo
operator|=
name|pageInfo
expr_stmt|;
name|this
operator|.
name|origPageInfo
operator|=
name|pageInfo
expr_stmt|;
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
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
name|keys
operator|.
name|size
argument_list|()
condition|;
name|j
operator|++
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|keys
operator|.
name|get
argument_list|(
name|j
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|j
operator|<
name|keys
operator|.
name|size
argument_list|()
operator|-
literal|1
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
block|}
name|keyString
operator|=
name|sb
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
DECL|method|pageInfoChanged ()
specifier|public
name|boolean
name|pageInfoChanged
parameter_list|()
block|{
if|if
condition|(
operator|(
operator|(
name|pageInfo
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|origPageInfo
operator|==
literal|null
operator|)
operator|)
operator|||
operator|(
operator|(
name|pageInfo
operator|==
literal|null
operator|)
operator|&&
operator|(
name|origPageInfo
operator|!=
literal|null
operator|)
operator|)
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|pageInfo
operator|!=
literal|null
condition|)
return|return
name|pageInfo
operator|.
name|compareTo
argument_list|(
name|origPageInfo
argument_list|)
operator|!=
literal|0
return|;
else|else
return|return
literal|false
return|;
block|}
DECL|method|compareTo (CitEntry other)
specifier|public
name|int
name|compareTo
parameter_list|(
name|CitEntry
name|other
parameter_list|)
block|{
return|return
name|this
operator|.
name|refMarkName
operator|.
name|compareTo
argument_list|(
name|other
operator|.
name|refMarkName
argument_list|)
return|;
block|}
block|}
DECL|class|CitEntryFormat
class|class
name|CitEntryFormat
implements|implements
name|TableFormat
argument_list|<
name|CitEntry
argument_list|>
block|{
DECL|method|getColumnCount ()
specifier|public
name|int
name|getColumnCount
parameter_list|()
block|{
return|return
literal|2
return|;
block|}
DECL|method|getColumnName (int i)
specifier|public
name|String
name|getColumnName
parameter_list|(
name|int
name|i
parameter_list|)
block|{
switch|switch
condition|(
name|i
condition|)
block|{
case|case
literal|0
case|:
return|return
name|Globals
operator|.
name|lang
argument_list|(
literal|"Citation"
argument_list|)
return|;
comment|//case 1: return Globals.lang("Context");
default|default:
return|return
name|Globals
operator|.
name|lang
argument_list|(
literal|"Extra information"
argument_list|)
return|;
block|}
block|}
DECL|method|getColumnValue (CitEntry citEntry, int i)
specifier|public
name|Object
name|getColumnValue
parameter_list|(
name|CitEntry
name|citEntry
parameter_list|,
name|int
name|i
parameter_list|)
block|{
switch|switch
condition|(
name|i
condition|)
block|{
comment|//case 0: return citEntry.keyString;
case|case
literal|0
case|:
return|return
name|citEntry
operator|.
name|context
return|;
default|default:
return|return
name|citEntry
operator|.
name|pageInfo
operator|!=
literal|null
condition|?
name|citEntry
operator|.
name|pageInfo
else|:
literal|""
return|;
block|}
block|}
block|}
DECL|class|TableClickListener
class|class
name|TableClickListener
extends|extends
name|MouseAdapter
block|{
DECL|method|mouseClicked (MouseEvent e)
specifier|public
name|void
name|mouseClicked
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{
if|if
condition|(
operator|(
name|e
operator|.
name|getButton
argument_list|()
operator|==
name|MouseEvent
operator|.
name|BUTTON1
operator|)
operator|&&
operator|(
name|e
operator|.
name|getClickCount
argument_list|()
operator|==
literal|2
operator|)
condition|)
block|{
name|int
name|row
init|=
name|table
operator|.
name|rowAtPoint
argument_list|(
name|e
operator|.
name|getPoint
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|row
operator|>=
literal|0
condition|)
block|{
name|SingleCitDialog
name|scd
init|=
operator|new
name|SingleCitDialog
argument_list|(
name|list
operator|.
name|get
argument_list|(
name|row
argument_list|)
argument_list|)
decl_stmt|;
name|scd
operator|.
name|showDialog
argument_list|()
expr_stmt|;
block|}
block|}
comment|//else if (e.isPopupTrigger())
comment|//    processPopupTrigger(e);
block|}
block|}
DECL|class|SingleCitDialog
class|class
name|SingleCitDialog
block|{
DECL|field|diag
name|JDialog
name|diag
decl_stmt|;
DECL|field|pageInfo
name|JTextField
name|pageInfo
init|=
operator|new
name|JTextField
argument_list|(
literal|20
argument_list|)
decl_stmt|;
DECL|field|title
name|JLabel
name|title
decl_stmt|;
DECL|field|ok
name|JButton
name|ok
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Ok"
argument_list|)
argument_list|)
decl_stmt|,
DECL|field|cancel
name|cancel
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|_entry
name|CitEntry
name|_entry
decl_stmt|;
DECL|method|SingleCitDialog (CitEntry entry)
specifier|public
name|SingleCitDialog
parameter_list|(
name|CitEntry
name|entry
parameter_list|)
block|{
name|this
operator|.
name|_entry
operator|=
name|entry
expr_stmt|;
name|title
operator|=
operator|new
name|JLabel
argument_list|(
name|entry
operator|.
name|context
argument_list|)
expr_stmt|;
name|pageInfo
operator|.
name|setText
argument_list|(
name|entry
operator|.
name|pageInfo
argument_list|)
expr_stmt|;
name|diag
operator|=
operator|new
name|JDialog
argument_list|(
name|CitationManager
operator|.
name|this
operator|.
name|diag
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Citation"
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|DefaultFormBuilder
name|b
init|=
operator|new
name|DefaultFormBuilder
argument_list|(
operator|new
name|FormLayout
argument_list|(
literal|"left:pref, 4dlu, left:150dlu"
argument_list|,
literal|""
argument_list|)
argument_list|)
decl_stmt|;
name|b
operator|.
name|append
argument_list|(
name|title
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|b
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Extra information (e.g. page number)"
argument_list|)
argument_list|)
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
name|pageInfo
argument_list|)
expr_stmt|;
name|b
operator|.
name|getPanel
argument_list|()
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|)
argument_list|)
expr_stmt|;
name|diag
operator|.
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|b
operator|.
name|getPanel
argument_list|()
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|ButtonBarBuilder
name|bb
init|=
operator|new
name|ButtonBarBuilder
argument_list|()
decl_stmt|;
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|ok
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|cancel
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|bb
operator|.
name|getPanel
argument_list|()
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|)
argument_list|)
expr_stmt|;
name|diag
operator|.
name|add
argument_list|(
name|bb
operator|.
name|getPanel
argument_list|()
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
name|Action
name|okAction
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
name|actionEvent
parameter_list|)
block|{
if|if
condition|(
name|pageInfo
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|_entry
operator|.
name|pageInfo
operator|=
name|pageInfo
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
expr_stmt|;
block|}
else|else
name|_entry
operator|.
name|pageInfo
operator|=
literal|null
expr_stmt|;
name|tableModel
operator|.
name|fireTableDataChanged
argument_list|()
expr_stmt|;
name|diag
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
decl_stmt|;
name|ok
operator|.
name|addActionListener
argument_list|(
name|okAction
argument_list|)
expr_stmt|;
name|Action
name|cancelAction
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
name|actionEvent
parameter_list|)
block|{
name|diag
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
decl_stmt|;
name|cancel
operator|.
name|addActionListener
argument_list|(
name|cancelAction
argument_list|)
expr_stmt|;
name|b
operator|.
name|getPanel
argument_list|()
operator|.
name|getInputMap
argument_list|(
name|JComponent
operator|.
name|WHEN_IN_FOCUSED_WINDOW
argument_list|)
operator|.
name|put
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getKey
argument_list|(
literal|"Close dialog"
argument_list|)
argument_list|,
literal|"close"
argument_list|)
expr_stmt|;
name|b
operator|.
name|getPanel
argument_list|()
operator|.
name|getActionMap
argument_list|()
operator|.
name|put
argument_list|(
literal|"close"
argument_list|,
name|cancelAction
argument_list|)
expr_stmt|;
block|}
DECL|method|showDialog ()
specifier|public
name|void
name|showDialog
parameter_list|()
block|{
name|diag
operator|.
name|pack
argument_list|()
expr_stmt|;
name|diag
operator|.
name|setLocationRelativeTo
argument_list|(
name|diag
operator|.
name|getParent
argument_list|()
argument_list|)
expr_stmt|;
name|diag
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

