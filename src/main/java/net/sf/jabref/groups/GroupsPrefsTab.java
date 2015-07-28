begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|FocusEvent
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
name|FocusListener
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
name|PrefsTab
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

begin_comment
comment|// JZTODO lyrics
end_comment

begin_class
DECL|class|GroupsPrefsTab
specifier|public
class|class
name|GroupsPrefsTab
extends|extends
name|JPanel
implements|implements
name|PrefsTab
block|{
DECL|field|showIcons
specifier|private
specifier|final
name|JCheckBox
name|showIcons
init|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Show icons for groups"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|showDynamic
specifier|private
specifier|final
name|JCheckBox
name|showDynamic
init|=
operator|new
name|JCheckBox
argument_list|(
literal|"<html>"
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"Show dynamic groups in<i>italics</i>"
argument_list|)
operator|+
literal|"</html>"
argument_list|)
decl_stmt|;
DECL|field|expandTree
specifier|private
specifier|final
name|JCheckBox
name|expandTree
init|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Initially show groups tree expanded"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|autoShow
specifier|private
specifier|final
name|JCheckBox
name|autoShow
init|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Automatically show groups interface when switching to a database that contains groups"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|autoHide
specifier|private
specifier|final
name|JCheckBox
name|autoHide
init|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Automatically hide groups interface when switching to a database that contains no groups"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|autoAssignGroup
specifier|private
specifier|final
name|JCheckBox
name|autoAssignGroup
init|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Automatically assign new entry to selected groups"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|groupingField
specifier|private
specifier|final
name|JTextField
name|groupingField
init|=
operator|new
name|JTextField
argument_list|(
literal|20
argument_list|)
decl_stmt|;
DECL|field|keywordSeparator
specifier|private
specifier|final
name|JTextField
name|keywordSeparator
init|=
operator|new
name|JTextField
argument_list|(
literal|2
argument_list|)
decl_stmt|;
DECL|field|prefs
specifier|private
specifier|final
name|JabRefPreferences
name|prefs
decl_stmt|;
DECL|method|GroupsPrefsTab (JabRefPreferences prefs)
specifier|public
name|GroupsPrefsTab
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
name|keywordSeparator
operator|.
name|addFocusListener
argument_list|(
operator|new
name|FocusListener
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|focusGained
parameter_list|(
name|FocusEvent
name|e
parameter_list|)
block|{
name|keywordSeparator
operator|.
name|selectAll
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|focusLost
parameter_list|(
name|FocusEvent
name|e
parameter_list|)
block|{
comment|// deselection is automatic
block|}
block|}
argument_list|)
expr_stmt|;
name|FormLayout
name|layout
init|=
operator|new
name|FormLayout
argument_list|(
literal|"9dlu, pref"
argument_list|,
comment|//500px",
literal|"p, 3dlu, p, 3dlu, p, 3dlu, p, 3dlu, p, 3dlu, p, 3dlu, p, 3dlu, "
operator|+
literal|"p, 3dlu, p"
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
name|builder
operator|.
name|appendSeparator
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"View"
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|nextColumn
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|showIcons
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|nextColumn
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|showDynamic
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|nextColumn
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|expandTree
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|nextColumn
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|autoShow
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|nextColumn
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|autoHide
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|nextColumn
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|autoAssignGroup
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
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
literal|"Dynamic groups"
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|nextColumn
argument_list|()
expr_stmt|;
comment|// build subcomponent
name|FormLayout
name|layout2
init|=
operator|new
name|FormLayout
argument_list|(
literal|"left:pref, 2dlu, left:pref"
argument_list|,
literal|"p, 3dlu, p"
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
name|builder2
operator|.
name|append
argument_list|(
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Default grouping field"
argument_list|)
operator|+
literal|":"
argument_list|)
argument_list|)
expr_stmt|;
name|builder2
operator|.
name|append
argument_list|(
name|groupingField
argument_list|)
expr_stmt|;
name|builder2
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder2
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder2
operator|.
name|append
argument_list|(
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"When adding/removing keywords, separate them by"
argument_list|)
operator|+
literal|":"
argument_list|)
argument_list|)
expr_stmt|;
name|builder2
operator|.
name|append
argument_list|(
name|keywordSeparator
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
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|JPanel
name|panel
init|=
name|builder
operator|.
name|getPanel
argument_list|()
decl_stmt|;
name|panel
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
name|panel
argument_list|,
name|BorderLayout
operator|.
name|CENTER
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
name|showIcons
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|GROUP_SHOW_ICONS
argument_list|)
argument_list|)
expr_stmt|;
name|showDynamic
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|GROUP_SHOW_DYNAMIC
argument_list|)
argument_list|)
expr_stmt|;
name|expandTree
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|GROUP_EXPAND_TREE
argument_list|)
argument_list|)
expr_stmt|;
name|groupingField
operator|.
name|setText
argument_list|(
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|GROUPS_DEFAULT_FIELD
argument_list|)
argument_list|)
expr_stmt|;
name|autoShow
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|GROUP_AUTO_SHOW
argument_list|)
argument_list|)
expr_stmt|;
name|autoHide
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|GROUP_AUTO_HIDE
argument_list|)
argument_list|)
expr_stmt|;
name|keywordSeparator
operator|.
name|setText
argument_list|(
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|GROUP_KEYWORD_SEPARATOR
argument_list|)
argument_list|)
expr_stmt|;
name|autoAssignGroup
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|AUTO_ASSIGN_GROUP
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
name|GROUP_SHOW_ICONS
argument_list|,
name|showIcons
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
name|GROUP_SHOW_DYNAMIC
argument_list|,
name|showDynamic
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
name|GROUP_EXPAND_TREE
argument_list|,
name|expandTree
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
name|GROUPS_DEFAULT_FIELD
argument_list|,
name|groupingField
operator|.
name|getText
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
name|GROUP_AUTO_SHOW
argument_list|,
name|autoShow
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
name|GROUP_AUTO_HIDE
argument_list|,
name|autoHide
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
name|AUTO_ASSIGN_GROUP
argument_list|,
name|autoAssignGroup
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
name|GROUP_KEYWORD_SEPARATOR
argument_list|,
name|keywordSeparator
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
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
literal|"Groups"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

