begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003 Morten O. Alver, Nizar N. Batada   All programs in this directory and  subdirectories are published under the GNU General Public License as  described below.   This program is free software; you can redistribute it and/or modify  it under the terms of the GNU General Public License as published by  the Free Software Foundation; either version 2 of the License, or (at  your option) any later version.   This program is distributed in the hope that it will be useful, but  WITHOUT ANY WARRANTY; without even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU  General Public License for more details.   You should have received a copy of the GNU General Public License  along with this program; if not, write to the Free Software  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA   Further information about the GNU GPL is available at:  http://www.gnu.org/copyleft/gpl.ja.html   */
end_comment

begin_package
DECL|package|net.sf.jabref.groups
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|groups
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
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|StringReader
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
name|regex
operator|.
name|Pattern
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
name|*
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
name|*
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
name|components
operator|.
name|*
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
name|search
operator|.
name|*
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
name|*
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
name|factories
operator|.
name|*
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
name|*
import|;
end_import

begin_comment
comment|/**  * Dialog for creating or modifying groups. Operates directly on the Vector  * containing group information.  */
end_comment

begin_class
DECL|class|GroupDialog
class|class
name|GroupDialog
extends|extends
name|JDialog
block|{
DECL|field|INDEX_EXPLICITGROUP
specifier|private
specifier|static
specifier|final
name|int
name|INDEX_EXPLICITGROUP
init|=
literal|0
decl_stmt|;
DECL|field|INDEX_KEYWORDGROUP
specifier|private
specifier|static
specifier|final
name|int
name|INDEX_KEYWORDGROUP
init|=
literal|1
decl_stmt|;
DECL|field|INDEX_SEARCHGROUP
specifier|private
specifier|static
specifier|final
name|int
name|INDEX_SEARCHGROUP
init|=
literal|2
decl_stmt|;
DECL|field|TEXTFIELD_LENGTH
specifier|private
specifier|static
specifier|final
name|int
name|TEXTFIELD_LENGTH
init|=
literal|30
decl_stmt|;
comment|// for all types
DECL|field|m_name
specifier|private
name|JTextField
name|m_name
init|=
operator|new
name|JTextField
argument_list|(
name|TEXTFIELD_LENGTH
argument_list|)
decl_stmt|;
DECL|field|m_nameLabel
specifier|private
name|JLabel
name|m_nameLabel
init|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Group name"
argument_list|)
operator|+
literal|":"
argument_list|)
decl_stmt|;
comment|// for KeywordGroup
DECL|field|m_kgSearchExpression
specifier|private
name|JTextField
name|m_kgSearchExpression
init|=
operator|new
name|JTextField
argument_list|(
name|TEXTFIELD_LENGTH
argument_list|)
decl_stmt|;
DECL|field|m_searchField
specifier|private
name|JTextField
name|m_searchField
init|=
operator|new
name|JTextField
argument_list|(
name|TEXTFIELD_LENGTH
argument_list|)
decl_stmt|;
DECL|field|m_keywordLabel
specifier|private
name|JLabel
name|m_keywordLabel
init|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Search term"
argument_list|)
operator|+
literal|":"
argument_list|)
decl_stmt|;
DECL|field|m_searchFieldLabel
specifier|private
name|JLabel
name|m_searchFieldLabel
init|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Field to search"
argument_list|)
operator|+
literal|":"
argument_list|)
decl_stmt|;
DECL|field|m_kgCaseSensitive
specifier|private
name|JCheckBox
name|m_kgCaseSensitive
init|=
operator|new
name|JCheckBox
argument_list|(
literal|"Case sensitive"
argument_list|)
decl_stmt|;
DECL|field|m_kgIsRegExp
specifier|private
name|JCheckBox
name|m_kgIsRegExp
init|=
operator|new
name|JCheckBox
argument_list|(
literal|"Regular Expression"
argument_list|)
decl_stmt|;
DECL|field|m_keywordGroupPanel
specifier|private
name|JPanel
name|m_keywordGroupPanel
decl_stmt|;
comment|// for SearchGroup
comment|// JZTODO translation
DECL|field|m_sgSearchExpression
specifier|private
name|JTextField
name|m_sgSearchExpression
init|=
operator|new
name|JTextField
argument_list|(
name|TEXTFIELD_LENGTH
argument_list|)
decl_stmt|;
DECL|field|m_sgCaseSensitive
specifier|private
name|JCheckBox
name|m_sgCaseSensitive
init|=
operator|new
name|JCheckBox
argument_list|(
literal|"Case sensitive"
argument_list|)
decl_stmt|;
DECL|field|m_sgIsRegExp
specifier|private
name|JCheckBox
name|m_sgIsRegExp
init|=
operator|new
name|JCheckBox
argument_list|(
literal|"Regular Expression"
argument_list|)
decl_stmt|;
DECL|field|m_searchExpressionLabel
specifier|private
name|JLabel
name|m_searchExpressionLabel
init|=
operator|new
name|JLabel
argument_list|(
literal|"Search expression:"
argument_list|)
decl_stmt|;
DECL|field|m_searchGroupPanel
specifier|private
name|JPanel
name|m_searchGroupPanel
decl_stmt|;
DECL|field|m_searchType
specifier|private
name|JLabel
name|m_searchType
init|=
operator|new
name|JLabel
argument_list|(
literal|"Plaintext Search"
argument_list|)
decl_stmt|;
DECL|field|m_searchAllFields
specifier|private
name|JCheckBox
name|m_searchAllFields
init|=
operator|new
name|JCheckBox
argument_list|(
literal|"Search All Fields"
argument_list|)
decl_stmt|;
DECL|field|m_searchRequiredFields
specifier|private
name|JCheckBox
name|m_searchRequiredFields
init|=
operator|new
name|JCheckBox
argument_list|(
literal|"Search Required Fields"
argument_list|)
decl_stmt|;
DECL|field|m_searchOptionalFields
specifier|private
name|JCheckBox
name|m_searchOptionalFields
init|=
operator|new
name|JCheckBox
argument_list|(
literal|"Search Optional Fields"
argument_list|)
decl_stmt|;
DECL|field|m_searchGeneralFields
specifier|private
name|JCheckBox
name|m_searchGeneralFields
init|=
operator|new
name|JCheckBox
argument_list|(
literal|"Search General Fields"
argument_list|)
decl_stmt|;
DECL|field|m_parser
specifier|private
name|SearchExpressionParser
name|m_parser
decl_stmt|;
comment|// JZTODO: translations...
comment|// for all types
DECL|field|m_types
specifier|private
name|DefaultComboBoxModel
name|m_types
init|=
operator|new
name|DefaultComboBoxModel
argument_list|()
decl_stmt|;
DECL|field|m_typeLabel
specifier|private
name|JLabel
name|m_typeLabel
init|=
operator|new
name|JLabel
argument_list|(
literal|"Assign entries based on:"
argument_list|)
decl_stmt|;
DECL|field|m_typeSelector
specifier|private
name|JComboBox
name|m_typeSelector
init|=
operator|new
name|JComboBox
argument_list|()
decl_stmt|;
DECL|field|m_ok
specifier|private
name|JButton
name|m_ok
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
decl_stmt|;
DECL|field|m_cancel
specifier|private
name|JButton
name|m_cancel
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
DECL|field|m_mainPanel
DECL|field|lowerPanel
specifier|private
name|JPanel
name|m_mainPanel
decl_stmt|,
name|lowerPanel
decl_stmt|;
DECL|field|m_okPressed
specifier|private
name|boolean
name|m_okPressed
init|=
literal|false
decl_stmt|;
DECL|field|m_parent
specifier|private
specifier|final
name|JabRefFrame
name|m_parent
decl_stmt|;
DECL|field|m_basePanel
specifier|private
specifier|final
name|BasePanel
name|m_basePanel
decl_stmt|;
DECL|field|m_resultingGroup
specifier|private
name|AbstractGroup
name|m_resultingGroup
decl_stmt|;
DECL|field|m_editedGroup
specifier|private
specifier|final
name|AbstractGroup
name|m_editedGroup
decl_stmt|;
DECL|field|lowerLayout
specifier|private
name|CardLayout
name|lowerLayout
init|=
operator|new
name|CardLayout
argument_list|()
decl_stmt|;
comment|/**      * Shows a group add/edit dialog.      *       * @param jabrefFrame      *            The parent frame.      * @param defaultField      *            The default grouping field.      * @param editedGroup      *            The group being edited, or null if a new group is to be      *            created.      */
DECL|method|GroupDialog (JabRefFrame jabrefFrame, BasePanel basePanel, AbstractGroup editedGroup)
specifier|public
name|GroupDialog
parameter_list|(
name|JabRefFrame
name|jabrefFrame
parameter_list|,
name|BasePanel
name|basePanel
parameter_list|,
name|AbstractGroup
name|editedGroup
parameter_list|)
block|{
name|super
argument_list|(
name|jabrefFrame
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Edit group"
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|m_basePanel
operator|=
name|basePanel
expr_stmt|;
name|m_parent
operator|=
name|jabrefFrame
expr_stmt|;
name|m_editedGroup
operator|=
name|editedGroup
expr_stmt|;
comment|// set default values (overwritten if editedGroup != null)
name|m_searchField
operator|.
name|setText
argument_list|(
name|jabrefFrame
operator|.
name|prefs
argument_list|()
operator|.
name|get
argument_list|(
literal|"groupsDefaultField"
argument_list|)
argument_list|)
expr_stmt|;
comment|// configure elements
name|m_types
operator|.
name|addElement
argument_list|(
literal|"Explicit"
argument_list|)
expr_stmt|;
name|m_types
operator|.
name|addElement
argument_list|(
literal|"Keywords"
argument_list|)
expr_stmt|;
name|m_types
operator|.
name|addElement
argument_list|(
literal|"Search Expression"
argument_list|)
expr_stmt|;
name|m_typeSelector
operator|.
name|setModel
argument_list|(
name|m_types
argument_list|)
expr_stmt|;
comment|// create layout
name|m_mainPanel
operator|=
operator|new
name|JPanelYBoxPreferredWidth
argument_list|()
expr_stmt|;
comment|/*JPanel namePanel = new JPanelXBoxPreferredHeight();         namePanel.add(m_nameLabel);         namePanel.add(Box.createHorizontalGlue());         namePanel.add(new JPanelXBoxPreferredSize(m_name));         JPanel typePanel = new JPanelXBoxPreferredHeight();         typePanel.add(m_typeLabel);         typePanel.add(Box.createHorizontalGlue());         typePanel.add(new JPanelXBoxPreferredSize(m_typeSelector));         */
comment|// ...for keyword group
name|FormLayout
name|layout
init|=
operator|new
name|FormLayout
argument_list|(
literal|"left:pref, 4dlu, fill:130dlu"
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
comment|//builder.appendSeparator(Globals.lang("Keyword"));
name|builder
operator|.
name|append
argument_list|(
name|m_searchFieldLabel
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|m_searchField
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
name|m_keywordLabel
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|m_kgSearchExpression
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
name|m_kgCaseSensitive
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
name|m_kgIsRegExp
argument_list|)
expr_stmt|;
comment|/*m_keywordGroupPanel = new JPanelYBox();         JPanel kgField = new JPanelXBoxPreferredHeight();         kgField.add(m_searchFieldLabel);         kgField.add(Box.createHorizontalGlue());         kgField.add(new JPanelXBoxPreferredSize(m_searchField));         JPanel kgExpression = new JPanelXBoxPreferredHeight();         kgExpression.add(m_keywordLabel);         kgExpression.add(Box.createHorizontalGlue());         kgExpression.add(new JPanelXBoxPreferredSize(m_kgSearchExpression));         m_keywordGroupPanel.add(kgField);         m_keywordGroupPanel.add(kgExpression);         m_keywordGroupPanel.add(Box.createVerticalGlue());         */
name|m_keywordGroupPanel
operator|=
name|builder
operator|.
name|getPanel
argument_list|()
expr_stmt|;
comment|// ...for search group
name|layout
operator|=
operator|new
name|FormLayout
argument_list|(
literal|"left:pref, 4dlu, fill:130dlu"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|builder
operator|=
operator|new
name|DefaultFormBuilder
argument_list|(
name|layout
argument_list|)
expr_stmt|;
comment|//builder.appendSeparator(Globals.lang("Search Expression"));
name|builder
operator|.
name|append
argument_list|(
name|m_searchExpressionLabel
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|m_sgSearchExpression
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
name|m_sgCaseSensitive
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
name|m_sgIsRegExp
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
name|m_searchAllFields
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
name|m_searchRequiredFields
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
name|m_searchOptionalFields
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
name|m_searchGeneralFields
argument_list|)
expr_stmt|;
name|m_searchGroupPanel
operator|=
name|builder
operator|.
name|getPanel
argument_list|()
expr_stmt|;
comment|/*JPanel sgExpression = new JPanelXBoxPreferredHeight();         sgExpression.add(m_searchExpressionLabel);         sgExpression.add(Box.createHorizontalGlue());         sgExpression.add(new JPanelXBoxPreferredSize(m_sgSearchExpression));         JPanel sgSearchType = new JPanelXBoxPreferredHeight(m_searchType);         sgSearchType.add(Box.createHorizontalGlue());         JPanel sgCaseSensitive = new JPanelXBoxPreferredHeight(m_caseSensitive);         JPanel sgRegExp = new JPanelXBoxPreferredHeight(m_isRegExp);         JPanel sgAll = new JPanelXBoxPreferredHeight(m_searchAllFields);         JPanel sgReq = new JPanelXBoxPreferredHeight(m_searchRequiredFields);         JPanel sgOpt = new JPanelXBoxPreferredHeight(m_searchOptionalFields);         JPanel sgGen = new JPanelXBoxPreferredHeight(m_searchGeneralFields);         sgCaseSensitive.add(Box.createHorizontalGlue());         sgRegExp.add(Box.createHorizontalGlue());         sgAll.add(Box.createHorizontalGlue());         sgReq.add(Box.createHorizontalGlue());         sgOpt.add(Box.createHorizontalGlue());         sgGen.add(Box.createHorizontalGlue());         m_searchGroupPanel.add(sgExpression);         m_searchGroupPanel.add(sgSearchType);         m_searchGroupPanel.add(sgCaseSensitive);         m_searchGroupPanel.add(sgRegExp);         m_searchGroupPanel.add(sgAll);         m_searchGroupPanel.add(sgReq);         m_searchGroupPanel.add(sgOpt);         m_searchGroupPanel.add(sgGen);         m_searchGroupPanel.add(Box.createVerticalGlue());         */
name|m_keywordGroupPanel
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|2
argument_list|,
literal|2
argument_list|,
literal|2
argument_list|,
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|m_searchGroupPanel
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|2
argument_list|,
literal|2
argument_list|,
literal|2
argument_list|,
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|lowerPanel
operator|=
operator|new
name|JPanel
argument_list|()
expr_stmt|;
name|lowerPanel
operator|.
name|setLayout
argument_list|(
name|lowerLayout
argument_list|)
expr_stmt|;
name|lowerPanel
operator|.
name|add
argument_list|(
operator|new
name|JPanel
argument_list|()
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|INDEX_EXPLICITGROUP
argument_list|)
argument_list|)
expr_stmt|;
name|lowerPanel
operator|.
name|add
argument_list|(
name|m_keywordGroupPanel
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|INDEX_KEYWORDGROUP
argument_list|)
argument_list|)
expr_stmt|;
name|lowerPanel
operator|.
name|add
argument_list|(
name|m_searchGroupPanel
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|INDEX_SEARCHGROUP
argument_list|)
argument_list|)
expr_stmt|;
name|layout
operator|=
operator|new
name|FormLayout
argument_list|(
literal|"left:pref, 4dlu, fill:80dlu"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|builder
operator|=
operator|new
name|DefaultFormBuilder
argument_list|(
name|layout
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|m_nameLabel
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|m_name
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
name|m_typeLabel
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|m_typeSelector
argument_list|)
expr_stmt|;
comment|//builder.nextLine();
comment|//builder.append(lowerPanel);
name|m_mainPanel
operator|=
name|builder
operator|.
name|getPanel
argument_list|()
expr_stmt|;
name|m_mainPanel
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|4
argument_list|,
literal|4
argument_list|,
literal|4
argument_list|,
literal|4
argument_list|)
argument_list|)
expr_stmt|;
name|lowerPanel
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEtchedBorder
argument_list|()
argument_list|)
expr_stmt|;
comment|//createEmptyBorder(2, 2, 2, 2));
comment|//m_mainPanel.add(namePanel);
comment|//m_mainPanel.add(typePanel);
name|JPanel
name|buttons
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
comment|//XBoxPreferredHeight();
name|buttons
operator|.
name|add
argument_list|(
name|m_ok
argument_list|)
expr_stmt|;
comment|//buttons.add(Box.createHorizontalStrut(5));
name|buttons
operator|.
name|add
argument_list|(
name|m_cancel
argument_list|)
expr_stmt|;
name|Container
name|cp
init|=
name|getContentPane
argument_list|()
decl_stmt|;
name|cp
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|cp
operator|.
name|add
argument_list|(
name|m_mainPanel
argument_list|,
name|BorderLayout
operator|.
name|NORTH
argument_list|)
expr_stmt|;
name|cp
operator|.
name|add
argument_list|(
name|lowerPanel
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|cp
operator|.
name|add
argument_list|(
name|buttons
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
comment|//cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));
comment|//cp.add(m_mainPanel);
comment|//cp.add(Box.createVerticalGlue());
comment|//cp.add(buttons);
comment|// add listeners
name|m_typeSelector
operator|.
name|addItemListener
argument_list|(
operator|new
name|ItemListener
argument_list|()
block|{
specifier|public
name|void
name|itemStateChanged
parameter_list|(
name|ItemEvent
name|e
parameter_list|)
block|{
name|setLayoutForGroup
argument_list|(
name|m_typeSelector
operator|.
name|getSelectedIndex
argument_list|()
argument_list|)
expr_stmt|;
name|updateComponents
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|m_cancel
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
name|m_ok
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
name|m_okPressed
operator|=
literal|true
expr_stmt|;
switch|switch
condition|(
name|m_typeSelector
operator|.
name|getSelectedIndex
argument_list|()
condition|)
block|{
case|case
name|INDEX_EXPLICITGROUP
case|:
if|if
condition|(
name|m_editedGroup
operator|instanceof
name|ExplicitGroup
condition|)
block|{
comment|// keep assignments from possible previous ExplicitGroup
name|m_resultingGroup
operator|=
name|m_editedGroup
operator|.
name|deepCopy
argument_list|()
expr_stmt|;
name|m_resultingGroup
operator|.
name|setName
argument_list|(
name|m_name
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|m_resultingGroup
operator|=
operator|new
name|ExplicitGroup
argument_list|(
name|m_name
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|,
name|m_basePanel
operator|.
name|database
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|m_editedGroup
operator|==
literal|null
condition|)
break|break;
comment|// do not perform the below conversion
name|addPreviousEntries
argument_list|()
expr_stmt|;
block|}
break|break;
case|case
name|INDEX_KEYWORDGROUP
case|:
comment|// regex is correct, otherwise OK would have been disabled
comment|// therefore I don't catch anything here
name|m_resultingGroup
operator|=
operator|new
name|KeywordGroup
argument_list|(
name|m_name
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|,
name|m_searchField
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|,
name|m_kgSearchExpression
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|,
name|m_kgCaseSensitive
operator|.
name|isSelected
argument_list|()
argument_list|,
name|m_kgIsRegExp
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
break|break;
case|case
name|INDEX_SEARCHGROUP
case|:
try|try
block|{
comment|// regex is correct, otherwise OK would have been
comment|// disabled
comment|// therefore I don't catch anything here
name|m_resultingGroup
operator|=
operator|new
name|SearchGroup
argument_list|(
name|m_name
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|,
name|m_sgSearchExpression
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|,
name|m_sgCaseSensitive
operator|.
name|isSelected
argument_list|()
argument_list|,
name|m_sgIsRegExp
operator|.
name|isSelected
argument_list|()
argument_list|,
name|m_searchAllFields
operator|.
name|isSelected
argument_list|()
argument_list|,
name|m_searchRequiredFields
operator|.
name|isSelected
argument_list|()
argument_list|,
name|m_searchOptionalFields
operator|.
name|isSelected
argument_list|()
argument_list|,
name|m_searchGeneralFields
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e1
parameter_list|)
block|{
comment|// should never happen
block|}
break|break;
block|}
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|CaretListener
name|caretListener
init|=
operator|new
name|CaretListener
argument_list|()
block|{
specifier|public
name|void
name|caretUpdate
parameter_list|(
name|CaretEvent
name|e
parameter_list|)
block|{
name|updateComponents
argument_list|()
expr_stmt|;
block|}
block|}
decl_stmt|;
name|ItemListener
name|itemListener
init|=
operator|new
name|ItemListener
argument_list|()
block|{
specifier|public
name|void
name|itemStateChanged
parameter_list|(
name|ItemEvent
name|e
parameter_list|)
block|{
name|updateComponents
argument_list|()
expr_stmt|;
block|}
block|}
decl_stmt|;
name|m_name
operator|.
name|addCaretListener
argument_list|(
name|caretListener
argument_list|)
expr_stmt|;
name|m_searchField
operator|.
name|addCaretListener
argument_list|(
name|caretListener
argument_list|)
expr_stmt|;
name|m_kgSearchExpression
operator|.
name|addCaretListener
argument_list|(
name|caretListener
argument_list|)
expr_stmt|;
name|m_kgCaseSensitive
operator|.
name|addItemListener
argument_list|(
name|itemListener
argument_list|)
expr_stmt|;
name|m_kgIsRegExp
operator|.
name|addItemListener
argument_list|(
name|itemListener
argument_list|)
expr_stmt|;
name|m_sgSearchExpression
operator|.
name|addCaretListener
argument_list|(
name|caretListener
argument_list|)
expr_stmt|;
name|m_sgIsRegExp
operator|.
name|addItemListener
argument_list|(
name|itemListener
argument_list|)
expr_stmt|;
name|m_sgCaseSensitive
operator|.
name|addItemListener
argument_list|(
name|itemListener
argument_list|)
expr_stmt|;
name|m_searchAllFields
operator|.
name|addItemListener
argument_list|(
name|itemListener
argument_list|)
expr_stmt|;
name|m_searchRequiredFields
operator|.
name|addItemListener
argument_list|(
name|itemListener
argument_list|)
expr_stmt|;
name|m_searchOptionalFields
operator|.
name|addItemListener
argument_list|(
name|itemListener
argument_list|)
expr_stmt|;
name|m_searchGeneralFields
operator|.
name|addItemListener
argument_list|(
name|itemListener
argument_list|)
expr_stmt|;
comment|// configure for current type
if|if
condition|(
name|editedGroup
operator|instanceof
name|KeywordGroup
condition|)
block|{
name|KeywordGroup
name|group
init|=
operator|(
name|KeywordGroup
operator|)
name|editedGroup
decl_stmt|;
name|m_name
operator|.
name|setText
argument_list|(
name|group
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|m_searchField
operator|.
name|setText
argument_list|(
name|group
operator|.
name|getSearchField
argument_list|()
argument_list|)
expr_stmt|;
name|m_kgSearchExpression
operator|.
name|setText
argument_list|(
name|group
operator|.
name|getSearchExpression
argument_list|()
argument_list|)
expr_stmt|;
name|m_kgCaseSensitive
operator|.
name|setSelected
argument_list|(
name|group
operator|.
name|isCaseSensitive
argument_list|()
argument_list|)
expr_stmt|;
name|m_kgIsRegExp
operator|.
name|setSelected
argument_list|(
name|group
operator|.
name|isRegExp
argument_list|()
argument_list|)
expr_stmt|;
name|m_typeSelector
operator|.
name|setSelectedIndex
argument_list|(
name|INDEX_KEYWORDGROUP
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|editedGroup
operator|instanceof
name|SearchGroup
condition|)
block|{
name|SearchGroup
name|group
init|=
operator|(
name|SearchGroup
operator|)
name|editedGroup
decl_stmt|;
name|m_name
operator|.
name|setText
argument_list|(
name|group
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|m_sgSearchExpression
operator|.
name|setText
argument_list|(
name|group
operator|.
name|getSearchExpression
argument_list|()
argument_list|)
expr_stmt|;
name|m_sgCaseSensitive
operator|.
name|setSelected
argument_list|(
name|group
operator|.
name|isCaseSensitive
argument_list|()
argument_list|)
expr_stmt|;
name|m_sgIsRegExp
operator|.
name|setSelected
argument_list|(
name|group
operator|.
name|isRegExp
argument_list|()
argument_list|)
expr_stmt|;
name|m_searchAllFields
operator|.
name|setSelected
argument_list|(
name|group
operator|.
name|searchAllFields
argument_list|()
argument_list|)
expr_stmt|;
name|m_searchRequiredFields
operator|.
name|setSelected
argument_list|(
name|group
operator|.
name|searchRequiredFields
argument_list|()
argument_list|)
expr_stmt|;
name|m_searchOptionalFields
operator|.
name|setSelected
argument_list|(
name|group
operator|.
name|searchOptionalFields
argument_list|()
argument_list|)
expr_stmt|;
name|m_searchGeneralFields
operator|.
name|setSelected
argument_list|(
name|group
operator|.
name|searchGeneralFields
argument_list|()
argument_list|)
expr_stmt|;
name|m_typeSelector
operator|.
name|setSelectedIndex
argument_list|(
name|INDEX_SEARCHGROUP
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|editedGroup
operator|instanceof
name|ExplicitGroup
condition|)
block|{
name|m_name
operator|.
name|setText
argument_list|(
name|editedGroup
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|m_typeSelector
operator|.
name|setSelectedIndex
argument_list|(
name|INDEX_EXPLICITGROUP
argument_list|)
expr_stmt|;
block|}
name|pack
argument_list|()
expr_stmt|;
comment|//setSize(350, 300);
name|setResizable
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|updateComponents
argument_list|()
expr_stmt|;
name|setLayoutForGroup
argument_list|(
name|m_typeSelector
operator|.
name|getSelectedIndex
argument_list|()
argument_list|)
expr_stmt|;
name|Util
operator|.
name|placeDialog
argument_list|(
name|this
argument_list|,
name|m_parent
argument_list|)
expr_stmt|;
block|}
DECL|method|okPressed ()
specifier|public
name|boolean
name|okPressed
parameter_list|()
block|{
return|return
name|m_okPressed
return|;
block|}
DECL|method|getResultingGroup ()
specifier|public
name|AbstractGroup
name|getResultingGroup
parameter_list|()
block|{
return|return
name|m_resultingGroup
return|;
block|}
DECL|method|setLayoutForGroup (int index)
specifier|private
name|void
name|setLayoutForGroup
parameter_list|(
name|int
name|index
parameter_list|)
block|{
name|lowerLayout
operator|.
name|show
argument_list|(
name|lowerPanel
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|index
argument_list|)
argument_list|)
expr_stmt|;
comment|/*switch (index) {         case INDEX_KEYWORDGROUP:             m_mainPanel.remove(m_searchGroupPanel);             m_mainPanel.add(m_keywordGroupPanel);             validate();             repaint();             break;         case INDEX_SEARCHGROUP:             m_mainPanel.remove(m_keywordGroupPanel);             m_mainPanel.add(m_searchGroupPanel);             validate();             repaint();             break;         case INDEX_EXPLICITGROUP:             m_mainPanel.remove(m_searchGroupPanel);             m_mainPanel.remove(m_keywordGroupPanel);             validate();             repaint();             break;         } */
block|}
DECL|method|updateComponents ()
specifier|private
name|void
name|updateComponents
parameter_list|()
block|{
comment|// all groups need a name
name|boolean
name|okEnabled
init|=
name|m_name
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
decl_stmt|;
name|String
name|s
decl_stmt|;
switch|switch
condition|(
name|m_typeSelector
operator|.
name|getSelectedIndex
argument_list|()
condition|)
block|{
case|case
name|INDEX_KEYWORDGROUP
case|:
name|s
operator|=
name|m_searchField
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
expr_stmt|;
name|okEnabled
operator|=
name|okEnabled
operator|&&
name|s
operator|.
name|length
argument_list|()
operator|>
literal|0
operator|&&
name|s
operator|.
name|indexOf
argument_list|(
literal|' '
argument_list|)
operator|<
literal|0
expr_stmt|;
name|s
operator|=
name|m_kgSearchExpression
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
expr_stmt|;
name|okEnabled
operator|=
name|okEnabled
operator|&&
name|s
operator|.
name|length
argument_list|()
operator|>
literal|0
expr_stmt|;
if|if
condition|(
name|m_kgIsRegExp
operator|.
name|isSelected
argument_list|()
condition|)
block|{
try|try
block|{
name|Pattern
operator|.
name|compile
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|okEnabled
operator|=
literal|false
expr_stmt|;
block|}
block|}
break|break;
case|case
name|INDEX_SEARCHGROUP
case|:
name|s
operator|=
name|m_sgSearchExpression
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
expr_stmt|;
name|okEnabled
operator|=
name|okEnabled
operator|&
name|s
operator|.
name|length
argument_list|()
operator|>
literal|0
expr_stmt|;
name|boolean
name|advancedSearch
init|=
name|SearchExpressionParser
operator|.
name|isValidSyntax
argument_list|(
name|s
argument_list|,
name|m_sgCaseSensitive
operator|.
name|isSelected
argument_list|()
argument_list|,
name|m_sgIsRegExp
operator|.
name|isSelected
argument_list|()
argument_list|)
decl_stmt|;
name|m_searchType
operator|.
name|setText
argument_list|(
name|advancedSearch
condition|?
literal|"Advanced Search"
else|:
literal|"Plaintext Search"
argument_list|)
expr_stmt|;
name|m_searchAllFields
operator|.
name|setEnabled
argument_list|(
operator|!
name|advancedSearch
argument_list|)
expr_stmt|;
name|m_searchRequiredFields
operator|.
name|setEnabled
argument_list|(
operator|!
name|advancedSearch
operator|&&
operator|!
name|m_searchAllFields
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|m_searchOptionalFields
operator|.
name|setEnabled
argument_list|(
operator|!
name|advancedSearch
operator|&&
operator|!
name|m_searchAllFields
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|m_searchGeneralFields
operator|.
name|setEnabled
argument_list|(
operator|!
name|advancedSearch
operator|&&
operator|!
name|m_searchAllFields
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|validate
argument_list|()
expr_stmt|;
break|break;
case|case
name|INDEX_EXPLICITGROUP
case|:
break|break;
block|}
name|m_ok
operator|.
name|setEnabled
argument_list|(
name|okEnabled
argument_list|)
expr_stmt|;
block|}
comment|/**      * This is used when a group is converted and the new group supports      * explicit adding of entries: All entries that match the previous group are      * added to the new group.      */
DECL|method|addPreviousEntries ()
specifier|private
name|void
name|addPreviousEntries
parameter_list|()
block|{
comment|// JZTODO in general, this should create undo information
comment|// because it might affect the entries. currently, it is only
comment|// used for ExplicitGroups; the undo information for this case is
comment|// contained completely in the UndoableModifyGroup object.
comment|// JZTODO lyrics...
name|int
name|i
init|=
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
name|m_basePanel
operator|.
name|frame
argument_list|()
argument_list|,
literal|"Assign all entries that matched the previous group to this group?"
argument_list|,
literal|"Conversion to an Explicit Group"
argument_list|,
name|JOptionPane
operator|.
name|YES_NO_OPTION
argument_list|,
name|JOptionPane
operator|.
name|QUESTION_MESSAGE
argument_list|)
decl_stmt|;
if|if
condition|(
name|i
operator|==
name|JOptionPane
operator|.
name|NO_OPTION
condition|)
return|return;
name|BibtexEntry
name|entry
decl_stmt|;
for|for
control|(
name|Iterator
name|it
init|=
name|m_basePanel
operator|.
name|database
argument_list|()
operator|.
name|getEntries
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|entry
operator|=
operator|(
name|BibtexEntry
operator|)
name|it
operator|.
name|next
argument_list|()
expr_stmt|;
if|if
condition|(
name|m_editedGroup
operator|.
name|contains
argument_list|(
name|entry
argument_list|)
condition|)
operator|(
operator|(
name|ExplicitGroup
operator|)
name|m_resultingGroup
operator|)
operator|.
name|addEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

