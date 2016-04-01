begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.gui.openoffice
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|openoffice
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
name|DefaultEventTableModel
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
name|FormBuilder
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
name|beans
operator|.
name|IllegalTypeException
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
name|beans
operator|.
name|NotRemoveableException
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
name|beans
operator|.
name|PropertyExistException
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
name|beans
operator|.
name|UnknownPropertyException
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
name|NoSuchElementException
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
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|lang
operator|.
name|IllegalArgumentException
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
name|lang
operator|.
name|WrappedTargetException
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
name|gui
operator|.
name|JabRefFrame
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
name|gui
operator|.
name|keyboard
operator|.
name|KeyBinding
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
name|logic
operator|.
name|l10n
operator|.
name|Localization
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
name|logic
operator|.
name|openoffice
operator|.
name|CitationEntry
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
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
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
name|Optional
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
class|class
name|CitationManager
block|{
DECL|field|ooBase
specifier|private
specifier|final
name|OOBibBase
name|ooBase
decl_stmt|;
DECL|field|diag
specifier|private
specifier|final
name|JDialog
name|diag
decl_stmt|;
DECL|field|list
specifier|private
specifier|final
name|EventList
argument_list|<
name|CitationEntry
argument_list|>
name|list
decl_stmt|;
DECL|field|table
specifier|private
specifier|final
name|JTable
name|table
decl_stmt|;
DECL|field|tableModel
specifier|private
specifier|final
name|DefaultEventTableModel
argument_list|<
name|CitationEntry
argument_list|>
name|tableModel
decl_stmt|;
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|CitationManager
operator|.
name|class
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
name|NoSuchElementException
throws|,
name|WrappedTargetException
throws|,
name|UnknownPropertyException
block|{
name|diag
operator|=
operator|new
name|JDialog
argument_list|(
name|frame
argument_list|,
name|Localization
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
argument_list|<>
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
name|List
argument_list|<
name|String
argument_list|>
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
name|list
operator|.
name|add
argument_list|(
operator|new
name|CitationEntry
argument_list|(
name|name
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
name|DefaultEventTableModel
argument_list|<>
argument_list|(
name|list
argument_list|,
operator|new
name|CitationEntryFormat
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
name|JButton
name|ok
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"OK"
argument_list|)
argument_list|)
decl_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|ok
argument_list|)
expr_stmt|;
name|JButton
name|cancel
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
argument_list|)
decl_stmt|;
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
name|ok
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
try|try
block|{
name|storeSettings
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnknownPropertyException
decl||
name|NotRemoveableException
decl||
name|PropertyExistException
decl||
name|IllegalTypeException
decl||
name|IllegalArgumentException
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Problem modifying citation"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|Localization
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
argument_list|)
expr_stmt|;
name|Action
name|cancelAction
init|=
operator|new
name|AbstractAction
argument_list|()
block|{
annotation|@
name|Override
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
name|getKeyPrefs
argument_list|()
operator|.
name|getKey
argument_list|(
name|KeyBinding
operator|.
name|CLOSE_DIALOG
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
literal|580
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
literal|110
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
name|UnknownPropertyException
throws|,
name|NotRemoveableException
throws|,
name|PropertyExistException
throws|,
name|IllegalTypeException
throws|,
name|IllegalArgumentException
block|{
for|for
control|(
name|CitationEntry
name|entry
range|:
name|list
control|)
block|{
name|Optional
argument_list|<
name|String
argument_list|>
name|pageInfo
init|=
name|entry
operator|.
name|getPageInfo
argument_list|()
decl_stmt|;
if|if
condition|(
name|entry
operator|.
name|pageInfoChanged
argument_list|()
operator|&&
name|pageInfo
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|ooBase
operator|.
name|setCustomProperty
argument_list|(
name|entry
operator|.
name|getRefMarkName
argument_list|()
argument_list|,
name|pageInfo
operator|.
name|get
argument_list|()
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
DECL|class|CitationEntryFormat
specifier|private
specifier|static
class|class
name|CitationEntryFormat
implements|implements
name|TableFormat
argument_list|<
name|CitationEntry
argument_list|>
block|{
annotation|@
name|Override
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
annotation|@
name|Override
DECL|method|getColumnName (int i)
specifier|public
name|String
name|getColumnName
parameter_list|(
name|int
name|i
parameter_list|)
block|{
if|if
condition|(
name|i
operator|==
literal|0
condition|)
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Citation"
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Extra information"
argument_list|)
return|;
block|}
block|}
annotation|@
name|Override
DECL|method|getColumnValue (CitationEntry citEntry, int i)
specifier|public
name|Object
name|getColumnValue
parameter_list|(
name|CitationEntry
name|citEntry
parameter_list|,
name|int
name|i
parameter_list|)
block|{
if|if
condition|(
name|i
operator|==
literal|0
condition|)
block|{
return|return
name|citEntry
operator|.
name|getContext
argument_list|()
return|;
block|}
else|else
block|{
return|return
name|citEntry
operator|.
name|getPageInfo
argument_list|()
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
return|;
block|}
block|}
block|}
DECL|class|TableClickListener
specifier|private
class|class
name|TableClickListener
extends|extends
name|MouseAdapter
block|{
annotation|@
name|Override
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
name|SingleCitationDialog
name|scd
init|=
operator|new
name|SingleCitationDialog
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
block|}
block|}
DECL|class|SingleCitationDialog
class|class
name|SingleCitationDialog
block|{
DECL|field|singleCiteDialog
specifier|private
specifier|final
name|JDialog
name|singleCiteDialog
decl_stmt|;
DECL|field|pageInfo
specifier|private
specifier|final
name|JTextField
name|pageInfo
init|=
operator|new
name|JTextField
argument_list|(
literal|20
argument_list|)
decl_stmt|;
DECL|field|okButton
specifier|private
specifier|final
name|JButton
name|okButton
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"OK"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|cancelButton
specifier|private
specifier|final
name|JButton
name|cancelButton
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|entry
specifier|private
specifier|final
name|CitationEntry
name|entry
decl_stmt|;
DECL|method|SingleCitationDialog (CitationEntry citEntry)
specifier|public
name|SingleCitationDialog
parameter_list|(
name|CitationEntry
name|citEntry
parameter_list|)
block|{
name|this
operator|.
name|entry
operator|=
name|citEntry
expr_stmt|;
name|pageInfo
operator|.
name|setText
argument_list|(
name|entry
operator|.
name|getPageInfo
argument_list|()
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|singleCiteDialog
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"Citation"
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|FormBuilder
name|builder
init|=
name|FormBuilder
operator|.
name|create
argument_list|()
operator|.
name|layout
argument_list|(
operator|new
name|FormLayout
argument_list|(
literal|"left:pref, 4dlu, fill:150dlu:grow"
argument_list|,
literal|"pref, 4dlu, pref"
argument_list|)
argument_list|)
decl_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|entry
operator|.
name|getContext
argument_list|()
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Extra information (e.g. page number)"
argument_list|)
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|pageInfo
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|padding
argument_list|(
literal|"10dlu, 10dlu, 10dlu, 10dlu"
argument_list|)
expr_stmt|;
name|singleCiteDialog
operator|.
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|builder
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
name|okButton
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|cancelButton
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|bb
operator|.
name|padding
argument_list|(
literal|"5dlu, 5dlu, 5dlu, 5dlu"
argument_list|)
expr_stmt|;
name|singleCiteDialog
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
name|okButton
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
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
name|isEmpty
argument_list|()
condition|)
block|{
name|entry
operator|.
name|setPageInfo
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|entry
operator|.
name|setPageInfo
argument_list|(
name|pageInfo
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|tableModel
operator|.
name|fireTableDataChanged
argument_list|()
expr_stmt|;
name|singleCiteDialog
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|Action
name|cancelAction
init|=
operator|new
name|AbstractAction
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|actionEvent
parameter_list|)
block|{
name|singleCiteDialog
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
decl_stmt|;
name|cancelButton
operator|.
name|addActionListener
argument_list|(
name|cancelAction
argument_list|)
expr_stmt|;
name|builder
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
name|getKeyPrefs
argument_list|()
operator|.
name|getKey
argument_list|(
name|KeyBinding
operator|.
name|CLOSE_DIALOG
argument_list|)
argument_list|,
literal|"close"
argument_list|)
expr_stmt|;
name|builder
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
name|singleCiteDialog
operator|.
name|pack
argument_list|()
expr_stmt|;
name|singleCiteDialog
operator|.
name|setLocationRelativeTo
argument_list|(
name|singleCiteDialog
operator|.
name|getParent
argument_list|()
argument_list|)
expr_stmt|;
name|singleCiteDialog
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
