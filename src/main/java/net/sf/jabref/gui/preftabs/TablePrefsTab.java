begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2012 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.gui.preftabs
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|preftabs
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|BorderLayout
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
name|util
operator|.
name|Arrays
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

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
name|BorderFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|ButtonGroup
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JCheckBox
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JComboBox
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JLabel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JPanel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JRadioButton
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JTable
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JTextField
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|BibtexFields
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

begin_class
DECL|class|TablePrefsTab
specifier|public
class|class
name|TablePrefsTab
extends|extends
name|JPanel
implements|implements
name|PrefsTab
block|{
DECL|field|prefs
specifier|private
specifier|final
name|JabRefPreferences
name|prefs
decl_stmt|;
DECL|field|autoResizeMode
specifier|private
specifier|final
name|JCheckBox
name|autoResizeMode
decl_stmt|;
DECL|field|priDesc
specifier|private
specifier|final
name|JCheckBox
name|priDesc
decl_stmt|;
DECL|field|secDesc
specifier|private
specifier|final
name|JCheckBox
name|secDesc
decl_stmt|;
DECL|field|terDesc
specifier|private
specifier|final
name|JCheckBox
name|terDesc
decl_stmt|;
DECL|field|floatMarked
specifier|private
specifier|final
name|JCheckBox
name|floatMarked
decl_stmt|;
DECL|field|namesAsIs
specifier|private
specifier|final
name|JRadioButton
name|namesAsIs
decl_stmt|;
DECL|field|namesFf
specifier|private
specifier|final
name|JRadioButton
name|namesFf
decl_stmt|;
DECL|field|namesFl
specifier|private
specifier|final
name|JRadioButton
name|namesFl
decl_stmt|;
DECL|field|namesNatbib
specifier|private
specifier|final
name|JRadioButton
name|namesNatbib
decl_stmt|;
DECL|field|abbrNames
specifier|private
specifier|final
name|JRadioButton
name|abbrNames
decl_stmt|;
DECL|field|noAbbrNames
specifier|private
specifier|final
name|JRadioButton
name|noAbbrNames
decl_stmt|;
DECL|field|lastNamesOnly
specifier|private
specifier|final
name|JRadioButton
name|lastNamesOnly
decl_stmt|;
DECL|field|priField
specifier|private
specifier|final
name|JTextField
name|priField
decl_stmt|;
DECL|field|secField
specifier|private
specifier|final
name|JTextField
name|secField
decl_stmt|;
DECL|field|terField
specifier|private
specifier|final
name|JTextField
name|terField
decl_stmt|;
DECL|field|numericFields
specifier|private
specifier|final
name|JTextField
name|numericFields
decl_stmt|;
DECL|field|priSort
specifier|private
specifier|final
name|JComboBox
name|priSort
decl_stmt|;
DECL|field|secSort
specifier|private
specifier|final
name|JComboBox
name|secSort
decl_stmt|;
DECL|field|terSort
specifier|private
specifier|final
name|JComboBox
name|terSort
decl_stmt|;
comment|/**      * Customization of external program paths.      *       * @param prefs      *            a<code>JabRefPreferences</code> value      */
DECL|method|TablePrefsTab (JabRefPreferences prefs)
specifier|public
name|TablePrefsTab
parameter_list|(
name|JabRefPreferences
name|prefs
parameter_list|)
block|{
name|this
operator|.
name|prefs
operator|=
name|prefs
expr_stmt|;
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
comment|/**          * Added Bibtexkey to combobox.          *           * [ 1540646 ] default sort order: bibtexkey          *           * http://sourceforge.net/tracker/index.php?func=detail&aid=1540646&group_id=92314&atid=600306          */
name|Vector
argument_list|<
name|String
argument_list|>
name|fieldNames
init|=
operator|new
name|Vector
argument_list|<
name|String
argument_list|>
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|BibtexFields
operator|.
name|getAllFieldNames
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|fieldNames
operator|.
name|add
argument_list|(
name|BibtexFields
operator|.
name|KEY_FIELD
argument_list|)
expr_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|fieldNames
argument_list|)
expr_stmt|;
name|String
index|[]
name|allPlusKey
init|=
name|fieldNames
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|fieldNames
operator|.
name|size
argument_list|()
index|]
argument_list|)
decl_stmt|;
name|priSort
operator|=
operator|new
name|JComboBox
argument_list|(
name|allPlusKey
argument_list|)
expr_stmt|;
name|secSort
operator|=
operator|new
name|JComboBox
argument_list|(
name|allPlusKey
argument_list|)
expr_stmt|;
name|terSort
operator|=
operator|new
name|JComboBox
argument_list|(
name|allPlusKey
argument_list|)
expr_stmt|;
name|autoResizeMode
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Fit table horizontally on screen"
argument_list|)
argument_list|)
expr_stmt|;
name|namesAsIs
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Show names unchanged"
argument_list|)
argument_list|)
expr_stmt|;
name|namesFf
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Show 'Firstname Lastname'"
argument_list|)
argument_list|)
expr_stmt|;
name|namesFl
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Show 'Lastname, Firstname'"
argument_list|)
argument_list|)
expr_stmt|;
name|namesNatbib
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Natbib style"
argument_list|)
argument_list|)
expr_stmt|;
name|noAbbrNames
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Do not abbreviate names"
argument_list|)
argument_list|)
expr_stmt|;
name|abbrNames
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Abbreviate names"
argument_list|)
argument_list|)
expr_stmt|;
name|lastNamesOnly
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Show last names only"
argument_list|)
argument_list|)
expr_stmt|;
name|floatMarked
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Float marked entries"
argument_list|)
argument_list|)
expr_stmt|;
name|priField
operator|=
operator|new
name|JTextField
argument_list|(
literal|10
argument_list|)
expr_stmt|;
name|secField
operator|=
operator|new
name|JTextField
argument_list|(
literal|10
argument_list|)
expr_stmt|;
name|terField
operator|=
operator|new
name|JTextField
argument_list|(
literal|10
argument_list|)
expr_stmt|;
name|numericFields
operator|=
operator|new
name|JTextField
argument_list|(
literal|30
argument_list|)
expr_stmt|;
name|priSort
operator|.
name|insertItemAt
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"<select>"
argument_list|)
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|secSort
operator|.
name|insertItemAt
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"<select>"
argument_list|)
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|terSort
operator|.
name|insertItemAt
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"<select>"
argument_list|)
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|priSort
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|priSort
operator|.
name|getSelectedIndex
argument_list|()
operator|>
literal|0
condition|)
block|{
name|priField
operator|.
name|setText
argument_list|(
name|priSort
operator|.
name|getSelectedItem
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|priSort
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|secSort
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|secSort
operator|.
name|getSelectedIndex
argument_list|()
operator|>
literal|0
condition|)
block|{
name|secField
operator|.
name|setText
argument_list|(
name|secSort
operator|.
name|getSelectedItem
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|secSort
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|terSort
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|terSort
operator|.
name|getSelectedIndex
argument_list|()
operator|>
literal|0
condition|)
block|{
name|terField
operator|.
name|setText
argument_list|(
name|terSort
operator|.
name|getSelectedItem
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|terSort
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|ButtonGroup
name|nameStyle
init|=
operator|new
name|ButtonGroup
argument_list|()
decl_stmt|;
name|nameStyle
operator|.
name|add
argument_list|(
name|namesAsIs
argument_list|)
expr_stmt|;
name|nameStyle
operator|.
name|add
argument_list|(
name|namesNatbib
argument_list|)
expr_stmt|;
name|nameStyle
operator|.
name|add
argument_list|(
name|namesFf
argument_list|)
expr_stmt|;
name|nameStyle
operator|.
name|add
argument_list|(
name|namesFl
argument_list|)
expr_stmt|;
name|ButtonGroup
name|nameAbbrev
init|=
operator|new
name|ButtonGroup
argument_list|()
decl_stmt|;
name|nameAbbrev
operator|.
name|add
argument_list|(
name|lastNamesOnly
argument_list|)
expr_stmt|;
name|nameAbbrev
operator|.
name|add
argument_list|(
name|abbrNames
argument_list|)
expr_stmt|;
name|nameAbbrev
operator|.
name|add
argument_list|(
name|noAbbrNames
argument_list|)
expr_stmt|;
name|priDesc
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Descending"
argument_list|)
argument_list|)
expr_stmt|;
name|secDesc
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Descending"
argument_list|)
argument_list|)
expr_stmt|;
name|terDesc
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Descending"
argument_list|)
argument_list|)
expr_stmt|;
name|FormLayout
name|layout
init|=
operator|new
name|FormLayout
argument_list|(
literal|"1dlu, 8dlu, left:pref, 4dlu, fill:pref, 4dlu, fill:60dlu, 4dlu, fill:pref"
argument_list|,
literal|""
argument_list|)
decl_stmt|;
name|DefaultFormBuilder
name|builder
init|=
operator|new
name|DefaultFormBuilder
argument_list|(
name|layout
argument_list|)
decl_stmt|;
name|JLabel
name|lab
decl_stmt|;
name|JPanel
name|pan
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|builder
operator|.
name|appendSeparator
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Format of author and editor names"
argument_list|)
argument_list|)
expr_stmt|;
name|DefaultFormBuilder
name|nameBuilder
init|=
operator|new
name|DefaultFormBuilder
argument_list|(
operator|new
name|FormLayout
argument_list|(
literal|"left:pref, 8dlu, left:pref"
argument_list|,
literal|""
argument_list|)
argument_list|)
decl_stmt|;
name|nameBuilder
operator|.
name|append
argument_list|(
name|namesAsIs
argument_list|)
expr_stmt|;
name|nameBuilder
operator|.
name|append
argument_list|(
name|noAbbrNames
argument_list|)
expr_stmt|;
name|nameBuilder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|nameBuilder
operator|.
name|append
argument_list|(
name|namesFf
argument_list|)
expr_stmt|;
name|nameBuilder
operator|.
name|append
argument_list|(
name|abbrNames
argument_list|)
expr_stmt|;
name|nameBuilder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|nameBuilder
operator|.
name|append
argument_list|(
name|namesFl
argument_list|)
expr_stmt|;
name|nameBuilder
operator|.
name|append
argument_list|(
name|lastNamesOnly
argument_list|)
expr_stmt|;
name|nameBuilder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|nameBuilder
operator|.
name|append
argument_list|(
name|namesNatbib
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|pan
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|nameBuilder
operator|.
name|getPanel
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|appendSeparator
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Default sort criteria"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Create a new panel with its own FormLayout for these items:
name|FormLayout
name|layout2
init|=
operator|new
name|FormLayout
argument_list|(
literal|"left:pref, 8dlu, fill:pref, 4dlu, fill:60dlu, 4dlu, left:pref"
argument_list|,
literal|""
argument_list|)
decl_stmt|;
name|DefaultFormBuilder
name|builder2
init|=
operator|new
name|DefaultFormBuilder
argument_list|(
name|layout2
argument_list|)
decl_stmt|;
name|lab
operator|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Primary sort criterion"
argument_list|)
argument_list|)
expr_stmt|;
name|builder2
operator|.
name|append
argument_list|(
name|lab
argument_list|)
expr_stmt|;
name|builder2
operator|.
name|append
argument_list|(
name|priSort
argument_list|)
expr_stmt|;
name|builder2
operator|.
name|append
argument_list|(
name|priField
argument_list|)
expr_stmt|;
name|builder2
operator|.
name|append
argument_list|(
name|priDesc
argument_list|)
expr_stmt|;
name|builder2
operator|.
name|nextLine
argument_list|()
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
literal|"Secondary sort criterion"
argument_list|)
argument_list|)
expr_stmt|;
name|builder2
operator|.
name|append
argument_list|(
name|lab
argument_list|)
expr_stmt|;
name|builder2
operator|.
name|append
argument_list|(
name|secSort
argument_list|)
expr_stmt|;
name|builder2
operator|.
name|append
argument_list|(
name|secField
argument_list|)
expr_stmt|;
name|builder2
operator|.
name|append
argument_list|(
name|secDesc
argument_list|)
expr_stmt|;
name|builder2
operator|.
name|nextLine
argument_list|()
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
literal|"Tertiary sort criterion"
argument_list|)
argument_list|)
expr_stmt|;
name|builder2
operator|.
name|append
argument_list|(
name|lab
argument_list|)
expr_stmt|;
name|builder2
operator|.
name|append
argument_list|(
name|terSort
argument_list|)
expr_stmt|;
name|builder2
operator|.
name|append
argument_list|(
name|terField
argument_list|)
expr_stmt|;
name|builder2
operator|.
name|append
argument_list|(
name|terDesc
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|pan
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|builder2
operator|.
name|getPanel
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|pan
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|floatMarked
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|pan
argument_list|)
expr_stmt|;
name|builder2
operator|=
operator|new
name|DefaultFormBuilder
argument_list|(
operator|new
name|FormLayout
argument_list|(
literal|"left:pref, 8dlu, fill:pref"
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|builder2
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Sort the following fields as numeric fields"
argument_list|)
operator|+
literal|':'
argument_list|)
expr_stmt|;
name|builder2
operator|.
name|append
argument_list|(
name|numericFields
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|builder2
operator|.
name|getPanel
argument_list|()
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|appendSeparator
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"General"
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|pan
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|autoResizeMode
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|pan
operator|=
name|builder
operator|.
name|getPanel
argument_list|()
expr_stmt|;
name|pan
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
name|add
argument_list|(
name|pan
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|namesNatbib
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
name|changeEvent
parameter_list|)
block|{
name|abbrNames
operator|.
name|setEnabled
argument_list|(
operator|!
name|namesNatbib
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|lastNamesOnly
operator|.
name|setEnabled
argument_list|(
operator|!
name|namesNatbib
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|noAbbrNames
operator|.
name|setEnabled
argument_list|(
operator|!
name|namesNatbib
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|setValues ()
specifier|public
name|void
name|setValues
parameter_list|()
block|{
name|autoResizeMode
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getInt
argument_list|(
name|JabRefPreferences
operator|.
name|AUTO_RESIZE_MODE
argument_list|)
operator|==
name|JTable
operator|.
name|AUTO_RESIZE_ALL_COLUMNS
argument_list|)
expr_stmt|;
name|priField
operator|.
name|setText
argument_list|(
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|PRIMARY_SORT_FIELD
argument_list|)
argument_list|)
expr_stmt|;
name|secField
operator|.
name|setText
argument_list|(
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|SECONDARY_SORT_FIELD
argument_list|)
argument_list|)
expr_stmt|;
name|terField
operator|.
name|setText
argument_list|(
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|TERTIARY_SORT_FIELD
argument_list|)
argument_list|)
expr_stmt|;
name|priSort
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|secSort
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|terSort
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
if|if
condition|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|NAMES_AS_IS
argument_list|)
condition|)
block|{
name|namesAsIs
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|NAMES_FIRST_LAST
argument_list|)
condition|)
block|{
name|namesFf
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|NAMES_NATBIB
argument_list|)
condition|)
block|{
name|namesNatbib
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|namesFl
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|ABBR_AUTHOR_NAMES
argument_list|)
condition|)
block|{
name|abbrNames
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|NAMES_LAST_ONLY
argument_list|)
condition|)
block|{
name|lastNamesOnly
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|noAbbrNames
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
name|priDesc
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|PRIMARY_SORT_DESCENDING
argument_list|)
argument_list|)
expr_stmt|;
name|secDesc
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|SECONDARY_SORT_DESCENDING
argument_list|)
argument_list|)
expr_stmt|;
name|terDesc
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|TERTIARY_SORT_DESCENDING
argument_list|)
argument_list|)
expr_stmt|;
name|floatMarked
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|FLOAT_MARKED_ENTRIES
argument_list|)
argument_list|)
expr_stmt|;
name|abbrNames
operator|.
name|setEnabled
argument_list|(
operator|!
name|namesNatbib
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|lastNamesOnly
operator|.
name|setEnabled
argument_list|(
operator|!
name|namesNatbib
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|noAbbrNames
operator|.
name|setEnabled
argument_list|(
operator|!
name|namesNatbib
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|numF
init|=
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|NUMERIC_FIELDS
argument_list|)
decl_stmt|;
if|if
condition|(
name|numF
operator|==
literal|null
condition|)
block|{
name|numericFields
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|numericFields
operator|.
name|setText
argument_list|(
name|numF
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Store changes to table preferences. This method is called when the user      * clicks Ok.      *       */
annotation|@
name|Override
DECL|method|storeSettings ()
specifier|public
name|void
name|storeSettings
parameter_list|()
block|{
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|NAMES_AS_IS
argument_list|,
name|namesAsIs
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|NAMES_FIRST_LAST
argument_list|,
name|namesFf
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|NAMES_NATBIB
argument_list|,
name|namesNatbib
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|NAMES_LAST_ONLY
argument_list|,
name|lastNamesOnly
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|ABBR_AUTHOR_NAMES
argument_list|,
name|abbrNames
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putInt
argument_list|(
name|JabRefPreferences
operator|.
name|AUTO_RESIZE_MODE
argument_list|,
name|autoResizeMode
operator|.
name|isSelected
argument_list|()
condition|?
name|JTable
operator|.
name|AUTO_RESIZE_ALL_COLUMNS
else|:
name|JTable
operator|.
name|AUTO_RESIZE_OFF
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|PRIMARY_SORT_DESCENDING
argument_list|,
name|priDesc
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|SECONDARY_SORT_DESCENDING
argument_list|,
name|secDesc
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|TERTIARY_SORT_DESCENDING
argument_list|,
name|terDesc
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|PRIMARY_SORT_FIELD
argument_list|,
name|priField
operator|.
name|getText
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|SECONDARY_SORT_FIELD
argument_list|,
name|secField
operator|.
name|getText
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|TERTIARY_SORT_FIELD
argument_list|,
name|terField
operator|.
name|getText
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|FLOAT_MARKED_ENTRIES
argument_list|,
name|floatMarked
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
comment|// updatefont
name|String
name|oldVal
init|=
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|NUMERIC_FIELDS
argument_list|)
decl_stmt|;
name|String
name|newVal
init|=
name|numericFields
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
decl_stmt|;
if|if
condition|(
name|newVal
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|newVal
operator|=
literal|null
expr_stmt|;
block|}
if|if
condition|(
name|newVal
operator|!=
literal|null
operator|&&
name|oldVal
operator|==
literal|null
operator|||
name|newVal
operator|==
literal|null
operator|&&
name|oldVal
operator|!=
literal|null
operator|||
name|newVal
operator|!=
literal|null
operator|&&
operator|!
name|newVal
operator|.
name|equals
argument_list|(
name|oldVal
argument_list|)
condition|)
block|{
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|NUMERIC_FIELDS
argument_list|,
name|newVal
argument_list|)
expr_stmt|;
name|BibtexFields
operator|.
name|setNumericFieldsFromPrefs
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|readyToClose ()
specifier|public
name|boolean
name|readyToClose
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
annotation|@
name|Override
DECL|method|getTabName ()
specifier|public
name|String
name|getTabName
parameter_list|()
block|{
return|return
name|Globals
operator|.
name|lang
argument_list|(
literal|"Entry table"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

