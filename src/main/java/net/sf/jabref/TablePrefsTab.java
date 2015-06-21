begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2012 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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

begin_class
DECL|class|TablePrefsTab
class|class
name|TablePrefsTab
extends|extends
name|JPanel
implements|implements
name|PrefsTab
block|{
DECL|field|_prefs
name|JabRefPreferences
name|_prefs
decl_stmt|;
DECL|field|autoResizeMode
DECL|field|priDesc
DECL|field|secDesc
DECL|field|terDesc
DECL|field|floatMarked
specifier|private
name|JCheckBox
name|autoResizeMode
decl_stmt|,
name|priDesc
decl_stmt|,
name|secDesc
decl_stmt|,
name|terDesc
decl_stmt|,
name|floatMarked
decl_stmt|;
DECL|field|namesAsIs
DECL|field|namesFf
DECL|field|namesFl
DECL|field|namesNatbib
DECL|field|abbrNames
DECL|field|noAbbrNames
specifier|private
name|JRadioButton
name|namesAsIs
decl_stmt|,
name|namesFf
decl_stmt|,
name|namesFl
decl_stmt|,
name|namesNatbib
decl_stmt|,
name|abbrNames
decl_stmt|,
name|noAbbrNames
decl_stmt|,
DECL|field|lastNamesOnly
name|lastNamesOnly
decl_stmt|;
DECL|field|priField
DECL|field|secField
DECL|field|terField
DECL|field|numericFields
specifier|private
name|JTextField
name|priField
decl_stmt|,
name|secField
decl_stmt|,
name|terField
decl_stmt|,
name|numericFields
decl_stmt|;
DECL|field|priSort
DECL|field|secSort
DECL|field|terSort
specifier|private
name|JComboBox
name|priSort
decl_stmt|,
name|secSort
decl_stmt|,
name|terSort
decl_stmt|;
comment|/**      * Customization of external program paths.      *       * @param prefs      *            a<code>JabRefPreferences</code> value      */
DECL|method|TablePrefsTab (JabRefPreferences prefs, JabRefFrame frame)
specifier|public
name|TablePrefsTab
parameter_list|(
name|JabRefPreferences
name|prefs
parameter_list|,
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|_prefs
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
name|v
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
name|v
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
name|v
argument_list|)
expr_stmt|;
name|String
index|[]
name|allPlusKey
init|=
name|v
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|v
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
name|bg
init|=
operator|new
name|ButtonGroup
argument_list|()
decl_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|namesAsIs
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|namesNatbib
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|namesFf
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|namesFl
argument_list|)
expr_stmt|;
name|ButtonGroup
name|bg2
init|=
operator|new
name|ButtonGroup
argument_list|()
decl_stmt|;
name|bg2
operator|.
name|add
argument_list|(
name|lastNamesOnly
argument_list|)
expr_stmt|;
name|bg2
operator|.
name|add
argument_list|(
name|abbrNames
argument_list|)
expr_stmt|;
name|bg2
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
comment|// builder.append(pan); builder.append(noAbbrNames); builder.nextLine();
comment|// builder.append(pan); builder.append(abbrNames); builder.nextLine();
comment|// builder.append(pan); builder.append(lastNamesOnly);
comment|// builder.nextLine();
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
literal|":"
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
operator|(
name|_prefs
operator|.
name|getInt
argument_list|(
literal|"autoResizeMode"
argument_list|)
operator|==
name|JTable
operator|.
name|AUTO_RESIZE_ALL_COLUMNS
operator|)
argument_list|)
expr_stmt|;
name|priField
operator|.
name|setText
argument_list|(
name|_prefs
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
name|_prefs
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
name|_prefs
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
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"namesAsIs"
argument_list|)
condition|)
name|namesAsIs
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"namesFf"
argument_list|)
condition|)
name|namesFf
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"namesNatbib"
argument_list|)
condition|)
name|namesNatbib
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
else|else
name|namesFl
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"abbrAuthorNames"
argument_list|)
condition|)
name|abbrNames
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"namesLastOnly"
argument_list|)
condition|)
name|lastNamesOnly
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
else|else
name|noAbbrNames
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|priDesc
operator|.
name|setSelected
argument_list|(
name|_prefs
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
name|_prefs
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
name|_prefs
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
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"floatMarkedEntries"
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
name|_prefs
operator|.
name|get
argument_list|(
literal|"numericFields"
argument_list|)
decl_stmt|;
if|if
condition|(
name|numF
operator|==
literal|null
condition|)
name|numericFields
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
else|else
name|numericFields
operator|.
name|setText
argument_list|(
name|numF
argument_list|)
expr_stmt|;
block|}
comment|/**      * Store changes to table preferences. This method is called when the user      * clicks Ok.      *       */
DECL|method|storeSettings ()
specifier|public
name|void
name|storeSettings
parameter_list|()
block|{
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"namesAsIs"
argument_list|,
name|namesAsIs
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"namesFf"
argument_list|,
name|namesFf
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"namesNatbib"
argument_list|,
name|namesNatbib
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"namesLastOnly"
argument_list|,
name|lastNamesOnly
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"abbrAuthorNames"
argument_list|,
name|abbrNames
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putInt
argument_list|(
literal|"autoResizeMode"
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
name|_prefs
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
name|_prefs
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
name|_prefs
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
comment|// _prefs.put(JabRefPreferences.SECONDARY_SORT_FIELD,
comment|// GUIGlobals.ALL_FIELDS[secSort.getSelectedIndex()]);
comment|// _prefs.put(JabRefPreferences.TERTIARY_SORT_FIELD,
comment|// GUIGlobals.ALL_FIELDS[terSort.getSelectedIndex()]);
name|_prefs
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
name|_prefs
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
name|_prefs
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
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"floatMarkedEntries"
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
name|_prefs
operator|.
name|get
argument_list|(
literal|"numericFields"
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
name|length
argument_list|()
operator|==
literal|0
condition|)
name|newVal
operator|=
literal|null
expr_stmt|;
if|if
condition|(
operator|(
operator|(
name|newVal
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|oldVal
operator|==
literal|null
operator|)
operator|)
operator|||
operator|(
operator|(
name|newVal
operator|==
literal|null
operator|)
operator|&&
operator|(
name|oldVal
operator|!=
literal|null
operator|)
operator|)
operator|||
operator|(
operator|(
name|newVal
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|newVal
operator|.
name|equals
argument_list|(
name|oldVal
argument_list|)
operator|)
condition|)
block|{
name|_prefs
operator|.
name|put
argument_list|(
literal|"numericFields"
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

