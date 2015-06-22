begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2012 JabRef contributors.  This program is free software; you can redistribute it and/or modify  it under the terms of the GNU General Public License as published by  the Free Software Foundation; either version 2 of the License, or  (at your option) any later version.   This program is distributed in the hope that it will be useful,  but WITHOUT ANY WARRANTY; without even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  GNU General Public License for more details.   You should have received a copy of the GNU General Public License along  with this program; if not, write to the Free Software Foundation, Inc.,  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
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
name|ActionListener
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|StringWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
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
name|ChangeEvent
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
name|ChangeListener
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
name|export
operator|.
name|LatexFieldFormatter
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
name|BasePanel
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
name|BibtexEntry
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
name|BibtexEntryType
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|MetaData
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
name|PreviewPanel
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
name|Util
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
name|undo
operator|.
name|NamedCompound
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
name|undo
operator|.
name|UndoableInsertEntry
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
name|undo
operator|.
name|UndoableRemoveEntry
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
name|layout
operator|.
name|CellConstraints
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
name|jgoodies
operator|.
name|forms
operator|.
name|layout
operator|.
name|RowSpec
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
name|ColumnSpec
import|;
end_import

begin_comment
comment|// created by : ?
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|// modified : r.nagel 2.09.2004
end_comment

begin_comment
comment|//            - insert close button
end_comment

begin_class
DECL|class|MergeEntriesDialog
specifier|public
class|class
name|MergeEntriesDialog
extends|extends
name|JDialog
block|{
comment|// private String [] preferedOrder = {"author", "title", "journal", "booktitle", "volume", "number", "pages", "year", "month"};
DECL|field|columnHeadings
specifier|private
name|String
index|[]
name|columnHeadings
init|=
block|{
name|Globals
operator|.
name|lang
argument_list|(
literal|"Field"
argument_list|)
block|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"First entry"
argument_list|)
block|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Use 1st"
argument_list|)
block|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"None"
argument_list|)
block|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Use 2nd"
argument_list|)
block|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Second entry"
argument_list|)
block|}
decl_stmt|;
DECL|field|DIM
specifier|private
specifier|final
name|Dimension
name|DIM
init|=
operator|new
name|Dimension
argument_list|(
literal|800
argument_list|,
literal|800
argument_list|)
decl_stmt|;
DECL|field|preview
specifier|private
name|PreviewPanel
name|preview
decl_stmt|;
DECL|field|panel
specifier|private
name|BasePanel
name|panel
decl_stmt|;
DECL|field|frame
specifier|private
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|rb
specifier|private
name|JRadioButton
index|[]
index|[]
name|rb
decl_stmt|;
DECL|field|identical
specifier|private
name|Boolean
index|[]
name|identical
decl_stmt|;
DECL|field|cc
specifier|private
name|CellConstraints
name|cc
init|=
operator|new
name|CellConstraints
argument_list|()
decl_stmt|;
DECL|field|mergedEntry
specifier|private
name|BibtexEntry
name|mergedEntry
init|=
operator|new
name|BibtexEntry
argument_list|()
decl_stmt|;
DECL|field|one
specifier|private
name|BibtexEntry
name|one
decl_stmt|;
DECL|field|two
specifier|private
name|BibtexEntry
name|two
decl_stmt|;
DECL|field|jta
specifier|private
name|JTextArea
name|jta
decl_stmt|;
DECL|field|pp
specifier|private
name|PreviewPanel
name|pp
decl_stmt|;
DECL|field|doneBuilding
specifier|private
name|Boolean
name|doneBuilding
init|=
literal|false
decl_stmt|;
DECL|field|joint
specifier|private
name|Set
argument_list|<
name|String
argument_list|>
name|joint
decl_stmt|;
DECL|field|jointStrings
specifier|private
name|String
index|[]
name|jointStrings
decl_stmt|;
DECL|field|ce
specifier|private
name|NamedCompound
name|ce
decl_stmt|;
DECL|method|MergeEntriesDialog (BasePanel panel)
specifier|public
name|MergeEntriesDialog
parameter_list|(
name|BasePanel
name|panel
parameter_list|)
block|{
name|super
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Merge entries"
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|this
operator|.
name|panel
operator|=
name|panel
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|panel
operator|.
name|frame
argument_list|()
expr_stmt|;
comment|// Start setting up the dialog
name|init
argument_list|(
name|panel
operator|.
name|getSelectedEntries
argument_list|()
argument_list|)
expr_stmt|;
name|Util
operator|.
name|placeDialog
argument_list|(
name|this
argument_list|,
name|this
operator|.
name|frame
argument_list|)
expr_stmt|;
block|}
DECL|method|init (BibtexEntry[] selected)
specifier|private
name|void
name|init
parameter_list|(
name|BibtexEntry
index|[]
name|selected
parameter_list|)
block|{
comment|// Check if there are two entries selected
if|if
condition|(
name|selected
operator|.
name|length
operator|!=
literal|2
condition|)
block|{
comment|// None selected. Inform the user to select entries first.
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
literal|"You have to choose exactly two entries to merge."
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Merge entries"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|INFORMATION_MESSAGE
argument_list|)
expr_stmt|;
name|this
operator|.
name|dispose
argument_list|()
expr_stmt|;
return|return;
block|}
comment|// Store the two entries
name|one
operator|=
name|selected
index|[
literal|0
index|]
expr_stmt|;
name|two
operator|=
name|selected
index|[
literal|1
index|]
expr_stmt|;
comment|// Create undo-compound
name|ce
operator|=
operator|new
name|NamedCompound
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Merge entries"
argument_list|)
argument_list|)
expr_stmt|;
name|joint
operator|=
operator|new
name|TreeSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|one
operator|.
name|getAllFields
argument_list|()
argument_list|)
expr_stmt|;
name|joint
operator|.
name|addAll
argument_list|(
name|two
operator|.
name|getAllFields
argument_list|()
argument_list|)
expr_stmt|;
comment|// Remove field starting with __
name|Set
argument_list|<
name|String
argument_list|>
name|toberemoved
init|=
operator|new
name|TreeSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|field
range|:
name|joint
control|)
block|{
if|if
condition|(
name|field
operator|.
name|startsWith
argument_list|(
literal|"__"
argument_list|)
condition|)
block|{
name|toberemoved
operator|.
name|add
argument_list|(
name|field
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|String
name|field
range|:
name|toberemoved
control|)
block|{
name|joint
operator|.
name|remove
argument_list|(
name|field
argument_list|)
expr_stmt|;
block|}
comment|// Create storage arrays
name|rb
operator|=
operator|new
name|JRadioButton
index|[
literal|3
index|]
index|[
name|joint
operator|.
name|size
argument_list|()
operator|+
literal|1
index|]
expr_stmt|;
name|ButtonGroup
index|[]
name|rbg
init|=
operator|new
name|ButtonGroup
index|[
name|joint
operator|.
name|size
argument_list|()
operator|+
literal|1
index|]
decl_stmt|;
name|identical
operator|=
operator|new
name|Boolean
index|[
name|joint
operator|.
name|size
argument_list|()
operator|+
literal|1
index|]
expr_stmt|;
name|jointStrings
operator|=
operator|new
name|String
index|[
name|joint
operator|.
name|size
argument_list|()
index|]
expr_stmt|;
comment|// Create layout
name|String
name|colSpec
init|=
literal|"left:pref, 5px, fill:4cm:grow, 5px, right:pref, 3px, center:pref, 3px, left:pref, 5px, fill:4cm:grow"
decl_stmt|;
name|StringBuilder
name|rowBuilder
init|=
operator|new
name|StringBuilder
argument_list|(
literal|"pref, 10px, pref, "
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
name|joint
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|rowBuilder
operator|.
name|append
argument_list|(
literal|"pref, "
argument_list|)
expr_stmt|;
block|}
name|rowBuilder
operator|.
name|append
argument_list|(
literal|"10px, top:4cm:grow, 10px, pref"
argument_list|)
expr_stmt|;
name|FormLayout
name|layout
init|=
operator|new
name|FormLayout
argument_list|(
name|colSpec
argument_list|,
name|rowBuilder
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
comment|// layout.setColumnGroups(new int[][] {{3, 11}});
name|this
operator|.
name|setLayout
argument_list|(
name|layout
argument_list|)
expr_stmt|;
comment|// Set headings
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
literal|6
condition|;
name|i
operator|++
control|)
block|{
name|JLabel
name|label
init|=
operator|new
name|JLabel
argument_list|(
name|columnHeadings
index|[
name|i
index|]
argument_list|)
decl_stmt|;
name|Font
name|font
init|=
name|label
operator|.
name|getFont
argument_list|()
decl_stmt|;
name|label
operator|.
name|setFont
argument_list|(
name|font
operator|.
name|deriveFont
argument_list|(
name|font
operator|.
name|getStyle
argument_list|()
operator||
name|Font
operator|.
name|BOLD
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|add
argument_list|(
name|label
argument_list|,
name|cc
operator|.
name|xy
argument_list|(
literal|1
operator|+
name|i
operator|*
literal|2
argument_list|,
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|this
operator|.
name|add
argument_list|(
operator|new
name|JSeparator
argument_list|()
argument_list|,
name|cc
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|2
argument_list|,
literal|11
argument_list|)
argument_list|)
expr_stmt|;
comment|// Start with entry type
name|BibtexEntryType
name|type1
init|=
name|one
operator|.
name|getType
argument_list|()
decl_stmt|;
name|BibtexEntryType
name|type2
init|=
name|two
operator|.
name|getType
argument_list|()
decl_stmt|;
name|mergedEntry
operator|.
name|setType
argument_list|(
name|type1
argument_list|)
expr_stmt|;
name|JLabel
name|label
init|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Entry type"
argument_list|)
argument_list|)
decl_stmt|;
name|Font
name|font
init|=
name|label
operator|.
name|getFont
argument_list|()
decl_stmt|;
name|label
operator|.
name|setFont
argument_list|(
name|font
operator|.
name|deriveFont
argument_list|(
name|font
operator|.
name|getStyle
argument_list|()
operator||
name|Font
operator|.
name|BOLD
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|add
argument_list|(
name|label
argument_list|,
name|cc
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|3
argument_list|)
argument_list|)
expr_stmt|;
name|JTextArea
name|type1ta
init|=
operator|new
name|JTextArea
argument_list|(
name|type1
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|type1ta
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|this
operator|.
name|add
argument_list|(
name|type1ta
argument_list|,
name|cc
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|3
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|type1
operator|.
name|compareTo
argument_list|(
name|type2
argument_list|)
operator|!=
literal|0
condition|)
block|{
name|identical
index|[
literal|0
index|]
operator|=
literal|false
expr_stmt|;
name|rbg
index|[
literal|0
index|]
operator|=
operator|new
name|ButtonGroup
argument_list|()
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
literal|3
condition|;
name|k
operator|+=
literal|2
control|)
block|{
name|rb
index|[
name|k
index|]
index|[
literal|0
index|]
operator|=
operator|new
name|JRadioButton
argument_list|()
expr_stmt|;
name|rbg
index|[
literal|0
index|]
operator|.
name|add
argument_list|(
name|rb
index|[
name|k
index|]
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|this
operator|.
name|add
argument_list|(
name|rb
index|[
name|k
index|]
index|[
literal|0
index|]
argument_list|,
name|cc
operator|.
name|xy
argument_list|(
literal|5
operator|+
name|k
operator|*
literal|2
argument_list|,
literal|3
argument_list|)
argument_list|)
expr_stmt|;
name|rb
index|[
name|k
index|]
index|[
literal|0
index|]
operator|.
name|addChangeListener
argument_list|(
operator|new
name|ChangeListener
argument_list|()
block|{
specifier|public
name|void
name|stateChanged
parameter_list|(
name|ChangeEvent
name|e
parameter_list|)
block|{
name|updateAll
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
name|rb
index|[
literal|0
index|]
index|[
literal|0
index|]
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|identical
index|[
literal|0
index|]
operator|=
literal|true
expr_stmt|;
block|}
name|JTextArea
name|type2ta
init|=
operator|new
name|JTextArea
argument_list|(
name|type2
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|type2ta
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|this
operator|.
name|add
argument_list|(
name|type2ta
argument_list|,
name|cc
operator|.
name|xy
argument_list|(
literal|11
argument_list|,
literal|3
argument_list|)
argument_list|)
expr_stmt|;
comment|// For all fields in joint add a row and possibly radio buttons
name|int
name|row
init|=
literal|4
decl_stmt|;
for|for
control|(
name|String
name|field
range|:
name|joint
control|)
block|{
name|jointStrings
index|[
name|row
operator|-
literal|4
index|]
operator|=
name|field
expr_stmt|;
name|label
operator|=
operator|new
name|JLabel
argument_list|(
name|Util
operator|.
name|toUpperFirstLetter
argument_list|(
name|field
argument_list|)
argument_list|)
expr_stmt|;
name|font
operator|=
name|label
operator|.
name|getFont
argument_list|()
expr_stmt|;
name|label
operator|.
name|setFont
argument_list|(
name|font
operator|.
name|deriveFont
argument_list|(
name|font
operator|.
name|getStyle
argument_list|()
operator||
name|Font
operator|.
name|BOLD
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|add
argument_list|(
name|label
argument_list|,
name|cc
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
name|row
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|string1
init|=
name|one
operator|.
name|getField
argument_list|(
name|field
argument_list|)
decl_stmt|;
name|String
name|string2
init|=
name|two
operator|.
name|getField
argument_list|(
name|field
argument_list|)
decl_stmt|;
name|identical
index|[
name|row
operator|-
literal|3
index|]
operator|=
literal|false
expr_stmt|;
if|if
condition|(
operator|(
name|string1
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|string2
operator|!=
literal|null
operator|)
condition|)
block|{
if|if
condition|(
name|string1
operator|.
name|equals
argument_list|(
name|string2
argument_list|)
condition|)
block|{
name|identical
index|[
name|row
operator|-
literal|3
index|]
operator|=
literal|true
expr_stmt|;
block|}
block|}
if|if
condition|(
name|field
operator|.
name|equals
argument_list|(
literal|"abstract"
argument_list|)
condition|)
block|{
comment|// Treat the abstract field special, maybe more fields? Review? Note?
name|JTextArea
name|tf
init|=
operator|new
name|JTextArea
argument_list|()
decl_stmt|;
name|tf
operator|.
name|setLineWrap
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|tf
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|JScrollPane
name|jsptf
init|=
operator|new
name|JScrollPane
argument_list|(
name|tf
argument_list|)
decl_stmt|;
name|layout
operator|.
name|setRowSpec
argument_list|(
name|row
argument_list|,
name|RowSpec
operator|.
name|decode
argument_list|(
literal|"center:2cm:grow"
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|add
argument_list|(
name|jsptf
argument_list|,
name|cc
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
name|row
argument_list|,
literal|"f, f"
argument_list|)
argument_list|)
expr_stmt|;
name|tf
operator|.
name|setText
argument_list|(
name|string1
argument_list|)
expr_stmt|;
name|tf
operator|.
name|setCaretPosition
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|JTextArea
name|tf
init|=
operator|new
name|JTextArea
argument_list|(
name|string1
argument_list|)
decl_stmt|;
name|this
operator|.
name|add
argument_list|(
name|tf
argument_list|,
name|cc
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
name|row
argument_list|)
argument_list|)
expr_stmt|;
name|tf
operator|.
name|setCaretPosition
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|tf
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
comment|// Add radio buttons if the two entries do not have identical fields
if|if
condition|(
operator|!
name|identical
index|[
name|row
operator|-
literal|3
index|]
condition|)
block|{
name|rbg
index|[
name|row
operator|-
literal|3
index|]
operator|=
operator|new
name|ButtonGroup
argument_list|()
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
literal|3
condition|;
name|k
operator|++
control|)
block|{
name|rb
index|[
name|k
index|]
index|[
name|row
operator|-
literal|3
index|]
operator|=
operator|new
name|JRadioButton
argument_list|()
expr_stmt|;
name|rbg
index|[
name|row
operator|-
literal|3
index|]
operator|.
name|add
argument_list|(
name|rb
index|[
name|k
index|]
index|[
name|row
operator|-
literal|3
index|]
argument_list|)
expr_stmt|;
name|this
operator|.
name|add
argument_list|(
name|rb
index|[
name|k
index|]
index|[
name|row
operator|-
literal|3
index|]
argument_list|,
name|cc
operator|.
name|xy
argument_list|(
literal|5
operator|+
name|k
operator|*
literal|2
argument_list|,
name|row
argument_list|)
argument_list|)
expr_stmt|;
name|rb
index|[
name|k
index|]
index|[
name|row
operator|-
literal|3
index|]
operator|.
name|addChangeListener
argument_list|(
operator|new
name|ChangeListener
argument_list|()
block|{
specifier|public
name|void
name|stateChanged
parameter_list|(
name|ChangeEvent
name|e
parameter_list|)
block|{
name|updateAll
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|string1
operator|!=
literal|null
condition|)
block|{
name|mergedEntry
operator|.
name|setField
argument_list|(
name|field
argument_list|,
name|string1
argument_list|)
expr_stmt|;
name|rb
index|[
literal|0
index|]
index|[
name|row
operator|-
literal|3
index|]
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|mergedEntry
operator|.
name|setField
argument_list|(
name|field
argument_list|,
name|string2
argument_list|)
expr_stmt|;
name|rb
index|[
literal|2
index|]
index|[
name|row
operator|-
literal|3
index|]
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|mergedEntry
operator|.
name|setField
argument_list|(
name|field
argument_list|,
name|string1
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|field
operator|.
name|equals
argument_list|(
literal|"abstract"
argument_list|)
condition|)
block|{
comment|// Again, treat abstract special
name|JTextArea
name|tf
init|=
operator|new
name|JTextArea
argument_list|()
decl_stmt|;
name|tf
operator|.
name|setLineWrap
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|tf
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|JScrollPane
name|jsptf
init|=
operator|new
name|JScrollPane
argument_list|(
name|tf
argument_list|)
decl_stmt|;
name|this
operator|.
name|add
argument_list|(
name|jsptf
argument_list|,
name|cc
operator|.
name|xy
argument_list|(
literal|11
argument_list|,
name|row
argument_list|,
literal|"f, f"
argument_list|)
argument_list|)
expr_stmt|;
name|tf
operator|.
name|setText
argument_list|(
name|string2
argument_list|)
expr_stmt|;
name|tf
operator|.
name|setCaretPosition
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|JTextArea
name|tf
init|=
operator|new
name|JTextArea
argument_list|(
name|string2
argument_list|)
decl_stmt|;
name|this
operator|.
name|add
argument_list|(
name|tf
argument_list|,
name|cc
operator|.
name|xy
argument_list|(
literal|11
argument_list|,
name|row
argument_list|)
argument_list|)
expr_stmt|;
name|tf
operator|.
name|setCaretPosition
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|tf
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
name|row
operator|++
expr_stmt|;
block|}
name|this
operator|.
name|add
argument_list|(
operator|new
name|JSeparator
argument_list|()
argument_list|,
name|cc
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
name|row
argument_list|,
literal|11
argument_list|)
argument_list|)
expr_stmt|;
name|row
operator|++
expr_stmt|;
comment|// Setup a PreviewPanel and a Bibtex source box for the merged entry
name|label
operator|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Merged entry"
argument_list|)
argument_list|)
expr_stmt|;
name|font
operator|=
name|label
operator|.
name|getFont
argument_list|()
expr_stmt|;
name|label
operator|.
name|setFont
argument_list|(
name|font
operator|.
name|deriveFont
argument_list|(
name|font
operator|.
name|getStyle
argument_list|()
operator||
name|Font
operator|.
name|BOLD
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|add
argument_list|(
name|label
argument_list|,
name|cc
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
name|row
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|layoutString
init|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"preview0"
argument_list|)
decl_stmt|;
name|pp
operator|=
operator|new
name|PreviewPanel
argument_list|(
literal|null
argument_list|,
name|mergedEntry
argument_list|,
literal|null
argument_list|,
operator|new
name|MetaData
argument_list|()
argument_list|,
name|layoutString
argument_list|)
expr_stmt|;
comment|// JScrollPane jsppp = new JScrollPane(pp);
name|this
operator|.
name|add
argument_list|(
name|pp
argument_list|,
name|cc
operator|.
name|xyw
argument_list|(
literal|3
argument_list|,
name|row
argument_list|,
literal|3
argument_list|)
argument_list|)
expr_stmt|;
name|jta
operator|=
operator|new
name|JTextArea
argument_list|()
expr_stmt|;
name|jta
operator|.
name|setLineWrap
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|JScrollPane
name|jspta
init|=
operator|new
name|JScrollPane
argument_list|(
name|jta
argument_list|)
decl_stmt|;
name|this
operator|.
name|add
argument_list|(
name|jspta
argument_list|,
name|cc
operator|.
name|xyw
argument_list|(
literal|9
argument_list|,
name|row
argument_list|,
literal|3
argument_list|)
argument_list|)
expr_stmt|;
name|jta
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|StringWriter
name|sw
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
try|try
block|{
name|mergedEntry
operator|.
name|write
argument_list|(
name|sw
argument_list|,
operator|new
name|LatexFieldFormatter
argument_list|()
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Error in entry"
operator|+
literal|": "
operator|+
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|jta
operator|.
name|setText
argument_list|(
name|sw
operator|.
name|getBuffer
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|jta
operator|.
name|setCaretPosition
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|row
operator|++
expr_stmt|;
name|this
operator|.
name|add
argument_list|(
operator|new
name|JSeparator
argument_list|()
argument_list|,
name|cc
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
name|row
argument_list|,
literal|11
argument_list|)
argument_list|)
expr_stmt|;
name|row
operator|++
expr_stmt|;
comment|// Create buttons
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
name|cancel
operator|.
name|setActionCommand
argument_list|(
literal|"cancel"
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
name|buttonPressed
argument_list|(
name|e
operator|.
name|getActionCommand
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|JButton
name|newentry
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Add new entry and keep both old entries"
argument_list|)
argument_list|)
decl_stmt|;
name|newentry
operator|.
name|setActionCommand
argument_list|(
literal|"newentry"
argument_list|)
expr_stmt|;
name|newentry
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
name|buttonPressed
argument_list|(
name|e
operator|.
name|getActionCommand
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|JButton
name|replaceentries
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Replace old entries with new entry"
argument_list|)
argument_list|)
decl_stmt|;
name|replaceentries
operator|.
name|setActionCommand
argument_list|(
literal|"replace"
argument_list|)
expr_stmt|;
name|replaceentries
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
name|buttonPressed
argument_list|(
name|e
operator|.
name|getActionCommand
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
operator|new
name|JButton
index|[]
block|{
name|replaceentries
block|,
name|newentry
block|,
name|cancel
block|}
argument_list|)
expr_stmt|;
name|this
operator|.
name|add
argument_list|(
name|bb
operator|.
name|getPanel
argument_list|()
argument_list|,
name|cc
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
name|row
argument_list|,
literal|11
argument_list|)
argument_list|)
expr_stmt|;
comment|// Add some margin around the layout
name|layout
operator|.
name|appendRow
argument_list|(
name|RowSpec
operator|.
name|decode
argument_list|(
literal|"10px"
argument_list|)
argument_list|)
expr_stmt|;
name|layout
operator|.
name|appendColumn
argument_list|(
name|ColumnSpec
operator|.
name|decode
argument_list|(
literal|"10px"
argument_list|)
argument_list|)
expr_stmt|;
name|layout
operator|.
name|insertRow
argument_list|(
literal|1
argument_list|,
name|RowSpec
operator|.
name|decode
argument_list|(
literal|"10px"
argument_list|)
argument_list|)
expr_stmt|;
name|layout
operator|.
name|insertColumn
argument_list|(
literal|1
argument_list|,
name|ColumnSpec
operator|.
name|decode
argument_list|(
literal|"10px"
argument_list|)
argument_list|)
expr_stmt|;
name|pack
argument_list|()
expr_stmt|;
if|if
condition|(
name|getHeight
argument_list|()
operator|>
name|DIM
operator|.
name|height
condition|)
block|{
name|setSize
argument_list|(
operator|new
name|Dimension
argument_list|(
name|getWidth
argument_list|()
argument_list|,
name|DIM
operator|.
name|height
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|getWidth
argument_list|()
operator|>
name|DIM
operator|.
name|width
condition|)
block|{
name|setSize
argument_list|(
operator|new
name|Dimension
argument_list|(
name|DIM
operator|.
name|width
argument_list|,
name|getHeight
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Everything done, allow any action to actually update the merged entry
name|doneBuilding
operator|=
literal|true
expr_stmt|;
comment|// Show what we've got
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
DECL|method|buttonPressed (String button)
specifier|private
name|void
name|buttonPressed
parameter_list|(
name|String
name|button
parameter_list|)
block|{
if|if
condition|(
name|button
operator|.
name|equals
argument_list|(
literal|"cancel"
argument_list|)
condition|)
block|{
comment|// Cancelled, throw it away
name|panel
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Cancelled merging entries"
argument_list|)
argument_list|)
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|button
operator|.
name|equals
argument_list|(
literal|"newentry"
argument_list|)
condition|)
block|{
comment|// Create a new entry and add it to the undo stack
comment|// Keep the other two entries
name|panel
operator|.
name|insertEntry
argument_list|(
name|mergedEntry
argument_list|)
expr_stmt|;
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableInsertEntry
argument_list|(
name|panel
operator|.
name|database
argument_list|()
argument_list|,
name|mergedEntry
argument_list|,
name|panel
argument_list|)
argument_list|)
expr_stmt|;
name|ce
operator|.
name|end
argument_list|()
expr_stmt|;
name|panel
operator|.
name|undoManager
operator|.
name|addEdit
argument_list|(
name|ce
argument_list|)
expr_stmt|;
name|panel
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Merged entries into a new and kept the old"
argument_list|)
argument_list|)
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|button
operator|.
name|equals
argument_list|(
literal|"replace"
argument_list|)
condition|)
block|{
comment|// Create a new entry and add it to the undo stack
comment|// Remove the other two entries and add them to the undo stack (which is not working...)
name|panel
operator|.
name|insertEntry
argument_list|(
name|mergedEntry
argument_list|)
expr_stmt|;
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableInsertEntry
argument_list|(
name|panel
operator|.
name|database
argument_list|()
argument_list|,
name|mergedEntry
argument_list|,
name|panel
argument_list|)
argument_list|)
expr_stmt|;
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableRemoveEntry
argument_list|(
name|panel
operator|.
name|database
argument_list|()
argument_list|,
name|one
argument_list|,
name|panel
argument_list|)
argument_list|)
expr_stmt|;
name|panel
operator|.
name|database
argument_list|()
operator|.
name|removeEntry
argument_list|(
name|one
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableRemoveEntry
argument_list|(
name|panel
operator|.
name|database
argument_list|()
argument_list|,
name|two
argument_list|,
name|panel
argument_list|)
argument_list|)
expr_stmt|;
name|panel
operator|.
name|database
argument_list|()
operator|.
name|removeEntry
argument_list|(
name|two
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|ce
operator|.
name|end
argument_list|()
expr_stmt|;
name|panel
operator|.
name|undoManager
operator|.
name|addEdit
argument_list|(
name|ce
argument_list|)
expr_stmt|;
name|panel
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Merged entries into a new and removed the old"
argument_list|)
argument_list|)
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|updateAll ()
specifier|private
name|void
name|updateAll
parameter_list|()
block|{
if|if
condition|(
operator|!
name|doneBuilding
condition|)
block|{
comment|// If we've not done adding everything, do not do anything...
return|return;
block|}
comment|// Check if the type is changed
if|if
condition|(
operator|!
name|identical
index|[
literal|0
index|]
condition|)
block|{
if|if
condition|(
name|rb
index|[
literal|0
index|]
index|[
literal|0
index|]
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|mergedEntry
operator|.
name|setType
argument_list|(
name|one
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|mergedEntry
operator|.
name|setType
argument_list|(
name|two
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Check all fields
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|joint
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
operator|!
name|identical
index|[
name|i
operator|+
literal|1
index|]
condition|)
block|{
if|if
condition|(
name|rb
index|[
literal|0
index|]
index|[
name|i
operator|+
literal|1
index|]
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|mergedEntry
operator|.
name|setField
argument_list|(
name|jointStrings
index|[
name|i
index|]
argument_list|,
name|one
operator|.
name|getField
argument_list|(
name|jointStrings
index|[
name|i
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|rb
index|[
literal|2
index|]
index|[
name|i
operator|+
literal|1
index|]
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|mergedEntry
operator|.
name|setField
argument_list|(
name|jointStrings
index|[
name|i
index|]
argument_list|,
name|two
operator|.
name|getField
argument_list|(
name|jointStrings
index|[
name|i
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|mergedEntry
operator|.
name|setField
argument_list|(
name|jointStrings
index|[
name|i
index|]
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|// Update the PreviewPanel
name|pp
operator|.
name|setEntry
argument_list|(
name|mergedEntry
argument_list|)
expr_stmt|;
comment|// Update the Bibtex source view
name|StringWriter
name|sw
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
try|try
block|{
name|mergedEntry
operator|.
name|write
argument_list|(
name|sw
argument_list|,
operator|new
name|LatexFieldFormatter
argument_list|()
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Error in entry"
operator|+
literal|": "
operator|+
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|jta
operator|.
name|setText
argument_list|(
name|sw
operator|.
name|getBuffer
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|jta
operator|.
name|setCaretPosition
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

