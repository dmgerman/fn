begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2015 JabRef contributors.  This program is free software; you can redistribute it and/or modify  it under the terms of the GNU General Public License as published by  the Free Software Foundation; either version 2 of the License, or  (at your option) any later version.   This program is distributed in the hope that it will be useful,  but WITHOUT ANY WARRANTY; without even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  GNU General Public License for more details.   You should have received a copy of the GNU General Public License along  with this program; if not, write to the Free Software Foundation, Inc.,  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.gui.mergeentries
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|mergeentries
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
name|exporter
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
name|bibtex
operator|.
name|BibtexEntryWriter
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
name|model
operator|.
name|entry
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
name|model
operator|.
name|entry
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
name|JabRefPreferences
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
name|util
operator|.
name|strings
operator|.
name|StringUtil
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
name|PreviewPanel
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
comment|/**  * @author Oscar Gustafsson  *  *         Class for dealing with merging entries  */
end_comment

begin_class
DECL|class|MergeEntries
specifier|public
class|class
name|MergeEntries
block|{
comment|// Headings
comment|// @formatter:off
DECL|field|columnHeadings
specifier|private
specifier|final
name|String
index|[]
name|columnHeadings
init|=
block|{
name|Localization
operator|.
name|lang
argument_list|(
literal|"Field"
argument_list|)
block|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Left entry"
argument_list|)
block|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Left"
argument_list|)
block|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"None"
argument_list|)
block|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Right"
argument_list|)
block|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Right entry"
argument_list|)
block|}
decl_stmt|;
comment|// @formatter:on
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
specifier|final
name|CellConstraints
name|cc
init|=
operator|new
name|CellConstraints
argument_list|()
decl_stmt|;
DECL|field|mergedEntry
specifier|private
specifier|final
name|BibtexEntry
name|mergedEntry
init|=
operator|new
name|BibtexEntry
argument_list|()
decl_stmt|;
DECL|field|one
specifier|private
specifier|final
name|BibtexEntry
name|one
decl_stmt|;
DECL|field|two
specifier|private
specifier|final
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
name|TreeSet
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
DECL|field|mergePanel
specifier|private
specifier|final
name|JPanel
name|mergePanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|field|mainPanel
specifier|private
specifier|final
name|JPanel
name|mainPanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
comment|/**      * Constructor taking two entries      *      * @param bOne First entry      * @param bTwo Second entry      */
DECL|method|MergeEntries (BibtexEntry bOne, BibtexEntry bTwo)
specifier|public
name|MergeEntries
parameter_list|(
name|BibtexEntry
name|bOne
parameter_list|,
name|BibtexEntry
name|bTwo
parameter_list|)
block|{
name|one
operator|=
name|bOne
expr_stmt|;
name|two
operator|=
name|bTwo
expr_stmt|;
name|initialize
argument_list|()
expr_stmt|;
block|}
comment|/**      * Constructor with optional column captions for the two entries      *      * @param bOne First entry      * @param bTwo Second entry      * @param headingOne Heading for first entry      * @param headingTwo Heading for second entry      */
DECL|method|MergeEntries (BibtexEntry bOne, BibtexEntry bTwo, String headingOne, String headingTwo)
specifier|public
name|MergeEntries
parameter_list|(
name|BibtexEntry
name|bOne
parameter_list|,
name|BibtexEntry
name|bTwo
parameter_list|,
name|String
name|headingOne
parameter_list|,
name|String
name|headingTwo
parameter_list|)
block|{
name|columnHeadings
index|[
literal|1
index|]
operator|=
name|headingOne
expr_stmt|;
name|columnHeadings
index|[
literal|5
index|]
operator|=
name|headingTwo
expr_stmt|;
name|one
operator|=
name|bOne
expr_stmt|;
name|two
operator|=
name|bTwo
expr_stmt|;
name|initialize
argument_list|()
expr_stmt|;
block|}
comment|/**      * Main function for building the merge entry JPanel      */
DECL|method|initialize ()
specifier|private
name|void
name|initialize
parameter_list|()
block|{
name|joint
operator|=
operator|new
name|TreeSet
argument_list|<>
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
name|TreeSet
argument_list|<
name|String
argument_list|>
name|toberemoved
init|=
operator|new
name|TreeSet
argument_list|<>
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
comment|// Create main layout
name|String
name|colSpecMain
init|=
literal|"left:pref, 5px, center:3cm:grow, 5px, center:pref, 3px, center:pref, 3px, center:pref, 5px, center:3cm:grow"
decl_stmt|;
name|String
name|colSpecMerge
init|=
literal|"left:pref, 5px, fill:3cm:grow, 5px, center:pref, 3px, center:pref, 3px, center:pref, 5px, fill:3cm:grow"
decl_stmt|;
name|String
name|rowSpec
init|=
literal|"pref, pref, 10px, fill:5cm:grow, 10px, pref, 10px, fill:3cm:grow"
decl_stmt|;
name|StringBuilder
name|rowBuilder
init|=
operator|new
name|StringBuilder
argument_list|(
literal|""
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
literal|"pref"
argument_list|)
expr_stmt|;
name|FormLayout
name|mainLayout
init|=
operator|new
name|FormLayout
argument_list|(
name|colSpecMain
argument_list|,
name|rowSpec
argument_list|)
decl_stmt|;
name|FormLayout
name|mergeLayout
init|=
operator|new
name|FormLayout
argument_list|(
name|colSpecMerge
argument_list|,
name|rowBuilder
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
name|mainPanel
operator|.
name|setLayout
argument_list|(
name|mainLayout
argument_list|)
expr_stmt|;
name|mergePanel
operator|.
name|setLayout
argument_list|(
name|mergeLayout
argument_list|)
expr_stmt|;
name|JLabel
name|label
init|=
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Use"
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
name|mainPanel
operator|.
name|add
argument_list|(
name|label
argument_list|,
name|cc
operator|.
name|xyw
argument_list|(
literal|4
argument_list|,
literal|1
argument_list|,
literal|7
argument_list|,
literal|"center, bottom"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Set headings
name|JLabel
name|headingLabels
index|[]
init|=
operator|new
name|JLabel
index|[
literal|6
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
literal|6
condition|;
name|i
operator|++
control|)
block|{
name|headingLabels
index|[
name|i
index|]
operator|=
operator|new
name|JLabel
argument_list|(
name|columnHeadings
index|[
name|i
index|]
argument_list|)
expr_stmt|;
name|font
operator|=
name|headingLabels
index|[
name|i
index|]
operator|.
name|getFont
argument_list|()
expr_stmt|;
name|headingLabels
index|[
name|i
index|]
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
name|mainPanel
operator|.
name|add
argument_list|(
name|headingLabels
index|[
name|i
index|]
argument_list|,
name|cc
operator|.
name|xy
argument_list|(
literal|1
operator|+
operator|(
name|i
operator|*
literal|2
operator|)
argument_list|,
literal|2
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|mainPanel
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
literal|3
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
name|label
operator|=
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Entry type"
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
name|mergePanel
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
literal|1
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
name|mergePanel
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
literal|1
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
name|mergePanel
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
operator|(
name|k
operator|*
literal|2
operator|)
argument_list|,
literal|1
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
annotation|@
name|Override
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
name|mergePanel
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
literal|1
argument_list|)
argument_list|)
expr_stmt|;
comment|// For all fields in joint add a row and possibly radio buttons
name|int
name|row
init|=
literal|2
decl_stmt|;
name|int
name|maxLabelWidth
init|=
operator|-
literal|1
decl_stmt|;
name|int
name|tmpLabelWidth
init|=
literal|0
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
literal|2
index|]
operator|=
name|field
expr_stmt|;
name|label
operator|=
operator|new
name|JLabel
argument_list|(
name|StringUtil
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
name|mergePanel
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
literal|1
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
literal|1
index|]
operator|=
literal|true
expr_stmt|;
block|}
block|}
name|tmpLabelWidth
operator|=
name|label
operator|.
name|getPreferredSize
argument_list|()
operator|.
name|width
expr_stmt|;
if|if
condition|(
name|tmpLabelWidth
operator|>
name|maxLabelWidth
condition|)
block|{
name|maxLabelWidth
operator|=
name|tmpLabelWidth
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
operator|||
name|field
operator|.
name|equals
argument_list|(
literal|"review"
argument_list|)
condition|)
block|{
comment|// Treat the abstract and review fields special
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
name|mergeLayout
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
name|mergePanel
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
name|mergePanel
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
literal|1
index|]
condition|)
block|{
name|rbg
index|[
name|row
operator|-
literal|1
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
literal|1
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
literal|1
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
literal|1
index|]
argument_list|)
expr_stmt|;
name|mergePanel
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
literal|1
index|]
argument_list|,
name|cc
operator|.
name|xy
argument_list|(
literal|5
operator|+
operator|(
name|k
operator|*
literal|2
operator|)
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
literal|1
index|]
operator|.
name|addChangeListener
argument_list|(
operator|new
name|ChangeListener
argument_list|()
block|{
annotation|@
name|Override
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
literal|1
index|]
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|string2
operator|==
literal|null
condition|)
block|{
name|rb
index|[
literal|2
index|]
index|[
name|row
operator|-
literal|1
index|]
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|rb
index|[
literal|0
index|]
index|[
name|row
operator|-
literal|1
index|]
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
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
literal|1
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
operator|||
name|field
operator|.
name|equals
argument_list|(
literal|"review"
argument_list|)
condition|)
block|{
comment|// Again, treat abstract and review special
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
name|mergePanel
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
name|mergePanel
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
name|JScrollPane
name|scrollPane
init|=
operator|new
name|JScrollPane
argument_list|(
name|mergePanel
argument_list|,
name|JScrollPane
operator|.
name|VERTICAL_SCROLLBAR_AS_NEEDED
argument_list|,
name|JScrollPane
operator|.
name|HORIZONTAL_SCROLLBAR_NEVER
argument_list|)
decl_stmt|;
name|scrollPane
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|()
argument_list|)
expr_stmt|;
name|mainPanel
operator|.
name|add
argument_list|(
name|scrollPane
argument_list|,
name|cc
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|4
argument_list|,
literal|11
argument_list|)
argument_list|)
expr_stmt|;
name|mainPanel
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
literal|5
argument_list|,
literal|11
argument_list|)
argument_list|)
expr_stmt|;
comment|// Synchronize column widths
name|String
name|rbAlign
index|[]
init|=
block|{
literal|"right"
block|,
literal|"center"
block|,
literal|"left"
block|}
decl_stmt|;
name|mainLayout
operator|.
name|setColumnSpec
argument_list|(
literal|1
argument_list|,
name|ColumnSpec
operator|.
name|decode
argument_list|(
name|Integer
operator|.
name|toString
argument_list|(
name|maxLabelWidth
argument_list|)
operator|+
literal|"px"
argument_list|)
argument_list|)
expr_stmt|;
name|Integer
name|maxRBWidth
init|=
operator|-
literal|1
decl_stmt|;
name|Integer
name|tmpRBWidth
decl_stmt|;
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
name|tmpRBWidth
operator|=
name|headingLabels
index|[
name|k
operator|+
literal|2
index|]
operator|.
name|getPreferredSize
argument_list|()
operator|.
name|width
expr_stmt|;
if|if
condition|(
name|tmpRBWidth
operator|>
name|maxRBWidth
condition|)
block|{
name|maxRBWidth
operator|=
name|tmpRBWidth
expr_stmt|;
block|}
block|}
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
name|mergeLayout
operator|.
name|setColumnSpec
argument_list|(
literal|5
operator|+
operator|(
name|k
operator|*
literal|2
operator|)
argument_list|,
name|ColumnSpec
operator|.
name|decode
argument_list|(
name|rbAlign
index|[
name|k
index|]
operator|+
literal|":"
operator|+
name|maxRBWidth
operator|.
name|toString
argument_list|()
operator|+
literal|"px"
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Setup a PreviewPanel and a Bibtex source box for the merged entry
name|label
operator|=
operator|new
name|JLabel
argument_list|(
name|Localization
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
name|mainPanel
operator|.
name|add
argument_list|(
name|label
argument_list|,
name|cc
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|6
argument_list|,
literal|6
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
name|JabRefPreferences
operator|.
name|PREVIEW_0
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
comment|// JScrollPane jsppp = new JScrollPane(pp, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
name|mainPanel
operator|.
name|add
argument_list|(
name|pp
argument_list|,
name|cc
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|8
argument_list|,
literal|6
argument_list|)
argument_list|)
expr_stmt|;
name|label
operator|=
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Merged BibTeX source code"
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
name|mainPanel
operator|.
name|add
argument_list|(
name|label
argument_list|,
name|cc
operator|.
name|xyw
argument_list|(
literal|8
argument_list|,
literal|6
argument_list|,
literal|4
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
name|mainPanel
operator|.
name|add
argument_list|(
name|jspta
argument_list|,
name|cc
operator|.
name|xyw
argument_list|(
literal|8
argument_list|,
literal|8
argument_list|,
literal|4
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
operator|new
name|BibtexEntryWriter
argument_list|(
operator|new
name|LatexFieldFormatter
argument_list|()
argument_list|,
literal|false
argument_list|)
operator|.
name|write
argument_list|(
name|mergedEntry
argument_list|,
name|sw
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
name|Localization
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
comment|// Add some margin around the layout
name|mainLayout
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
name|mainLayout
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
name|mainLayout
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
name|mainLayout
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
if|if
condition|(
name|mainPanel
operator|.
name|getHeight
argument_list|()
operator|>
name|DIM
operator|.
name|height
condition|)
block|{
name|mainPanel
operator|.
name|setSize
argument_list|(
operator|new
name|Dimension
argument_list|(
name|mergePanel
operator|.
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
name|mainPanel
operator|.
name|getWidth
argument_list|()
operator|>
name|DIM
operator|.
name|width
condition|)
block|{
name|mainPanel
operator|.
name|setSize
argument_list|(
operator|new
name|Dimension
argument_list|(
name|DIM
operator|.
name|width
argument_list|,
name|mergePanel
operator|.
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
name|mainPanel
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|javax
operator|.
name|swing
operator|.
name|SwingUtilities
operator|.
name|invokeLater
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
name|scrollPane
operator|.
name|getVerticalScrollBar
argument_list|()
operator|.
name|setValue
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
comment|/**      * @return Merged BibtexEntry      */
DECL|method|getMergeEntry ()
specifier|public
name|BibtexEntry
name|getMergeEntry
parameter_list|()
block|{
return|return
name|mergedEntry
return|;
block|}
comment|/**      * @return The merge entry JPanel      */
DECL|method|getMergeEntryPanel ()
specifier|public
name|JPanel
name|getMergeEntryPanel
parameter_list|()
block|{
return|return
name|mainPanel
return|;
block|}
comment|/**      * Update the merged BibtexEntry with source and preview panel everytime something is changed      */
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
operator|new
name|BibtexEntryWriter
argument_list|(
operator|new
name|LatexFieldFormatter
argument_list|()
argument_list|,
literal|false
argument_list|)
operator|.
name|write
argument_list|(
name|mergedEntry
argument_list|,
name|sw
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
name|Localization
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

