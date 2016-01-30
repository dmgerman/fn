begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|util
operator|.
name|*
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
name|ListDataListener
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
name|ListSelectionEvent
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
name|ListSelectionListener
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
name|bibtex
operator|.
name|EntryTypes
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
name|gui
operator|.
name|util
operator|.
name|FocusRequester
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
name|model
operator|.
name|entry
operator|.
name|*
import|;
end_import

begin_class
DECL|class|EntryCustomizationDialog
specifier|public
class|class
name|EntryCustomizationDialog
extends|extends
name|JDialog
implements|implements
name|ListSelectionListener
implements|,
name|ActionListener
block|{
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|gbl
specifier|protected
name|GridBagLayout
name|gbl
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
DECL|field|con
specifier|protected
name|GridBagConstraints
name|con
init|=
operator|new
name|GridBagConstraints
argument_list|()
decl_stmt|;
DECL|field|reqComp
specifier|private
name|FieldSetComponent
name|reqComp
decl_stmt|;
DECL|field|optComp
specifier|private
name|FieldSetComponent
name|optComp
decl_stmt|;
DECL|field|optComp2
specifier|private
name|FieldSetComponent
name|optComp2
decl_stmt|;
DECL|field|typeComp
specifier|private
name|EntryTypeList
name|typeComp
decl_stmt|;
DECL|field|ok
specifier|private
name|JButton
name|ok
decl_stmt|;
DECL|field|cancel
specifier|private
name|JButton
name|cancel
decl_stmt|;
DECL|field|apply
specifier|private
name|JButton
name|apply
decl_stmt|;
DECL|field|helpButton
specifier|protected
name|JButton
name|helpButton
decl_stmt|;
DECL|field|delete
specifier|protected
name|JButton
name|delete
decl_stmt|;
DECL|field|importTypes
specifier|protected
name|JButton
name|importTypes
decl_stmt|;
DECL|field|exportTypes
specifier|protected
name|JButton
name|exportTypes
decl_stmt|;
DECL|field|preset
specifier|private
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|preset
init|=
name|InternalBibtexFields
operator|.
name|getAllFieldNames
argument_list|()
decl_stmt|;
DECL|field|lastSelected
specifier|private
name|String
name|lastSelected
decl_stmt|;
DECL|field|reqLists
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|reqLists
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|optLists
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|optLists
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|opt2Lists
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|opt2Lists
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|defaulted
specifier|private
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|defaulted
init|=
operator|new
name|HashSet
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|changed
specifier|private
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|changed
init|=
operator|new
name|HashSet
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|biblatexMode
specifier|private
name|boolean
name|biblatexMode
decl_stmt|;
DECL|field|bibDatabaseContext
specifier|private
name|BibDatabaseContext
name|bibDatabaseContext
decl_stmt|;
comment|/**      * Creates a new instance of EntryCustomizationDialog      */
DECL|method|EntryCustomizationDialog (JabRefFrame frame)
specifier|public
name|EntryCustomizationDialog
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|super
argument_list|(
name|frame
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Customize entry types"
argument_list|)
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|initGui
argument_list|()
expr_stmt|;
block|}
DECL|method|initGui ()
specifier|private
name|void
name|initGui
parameter_list|()
block|{
name|Container
name|pane
init|=
name|getContentPane
argument_list|()
decl_stmt|;
name|pane
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|bibDatabaseContext
operator|=
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|getBibDatabaseContext
argument_list|()
expr_stmt|;
name|biblatexMode
operator|=
name|bibDatabaseContext
operator|.
name|isBiblatexMode
argument_list|()
expr_stmt|;
name|JPanel
name|main
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|JPanel
name|buttons
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|JPanel
name|right
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|main
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|right
operator|.
name|setLayout
argument_list|(
operator|new
name|GridLayout
argument_list|(
name|biblatexMode
condition|?
literal|2
else|:
literal|1
argument_list|,
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|entryTypes
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|s
range|:
name|EntryTypes
operator|.
name|getAllTypes
argument_list|(
name|bibDatabaseContext
operator|.
name|getMode
argument_list|()
argument_list|)
control|)
block|{
name|entryTypes
operator|.
name|add
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
name|typeComp
operator|=
operator|new
name|EntryTypeList
argument_list|(
name|entryTypes
argument_list|,
name|bibDatabaseContext
operator|.
name|getMode
argument_list|()
argument_list|)
expr_stmt|;
name|typeComp
operator|.
name|addListSelectionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|typeComp
operator|.
name|addAdditionActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|typeComp
operator|.
name|addDefaultActionListener
argument_list|(
operator|new
name|DefaultListener
argument_list|()
argument_list|)
expr_stmt|;
name|typeComp
operator|.
name|setListSelectionMode
argument_list|(
name|ListSelectionModel
operator|.
name|SINGLE_SELECTION
argument_list|)
expr_stmt|;
comment|//typeComp.setEnabled(false);
name|reqComp
operator|=
operator|new
name|FieldSetComponent
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Required fields"
argument_list|)
argument_list|,
operator|new
name|ArrayList
argument_list|<>
argument_list|()
argument_list|,
name|preset
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|reqComp
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|reqComp
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
name|ListDataListener
name|dataListener
init|=
operator|new
name|DataListener
argument_list|()
decl_stmt|;
name|reqComp
operator|.
name|addListDataListener
argument_list|(
name|dataListener
argument_list|)
expr_stmt|;
name|optComp
operator|=
operator|new
name|FieldSetComponent
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Optional fields"
argument_list|)
argument_list|,
operator|new
name|ArrayList
argument_list|<>
argument_list|()
argument_list|,
name|preset
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|optComp
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|optComp
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
name|optComp
operator|.
name|addListDataListener
argument_list|(
name|dataListener
argument_list|)
expr_stmt|;
name|right
operator|.
name|add
argument_list|(
name|reqComp
argument_list|)
expr_stmt|;
name|right
operator|.
name|add
argument_list|(
name|optComp
argument_list|)
expr_stmt|;
if|if
condition|(
name|biblatexMode
condition|)
block|{
name|optComp2
operator|=
operator|new
name|FieldSetComponent
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Optional fields"
argument_list|)
operator|+
literal|" 2"
argument_list|,
operator|new
name|ArrayList
argument_list|<>
argument_list|()
argument_list|,
name|preset
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|optComp2
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|optComp2
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
name|optComp2
operator|.
name|addListDataListener
argument_list|(
name|dataListener
argument_list|)
expr_stmt|;
name|right
operator|.
name|add
argument_list|(
operator|new
name|JPanel
argument_list|()
argument_list|)
expr_stmt|;
name|right
operator|.
name|add
argument_list|(
name|optComp2
argument_list|)
expr_stmt|;
block|}
comment|//right.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), Globals.lang("Fields")));
name|right
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEtchedBorder
argument_list|()
argument_list|)
expr_stmt|;
name|ok
operator|=
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
expr_stmt|;
name|cancel
operator|=
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
expr_stmt|;
name|apply
operator|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Apply"
argument_list|)
argument_list|)
expr_stmt|;
name|ok
operator|.
name|addActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|apply
operator|.
name|addActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|cancel
operator|.
name|addActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|ButtonBarBuilder
name|bb
init|=
operator|new
name|ButtonBarBuilder
argument_list|(
name|buttons
argument_list|)
decl_stmt|;
name|buttons
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
name|apply
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
name|AbstractAction
name|closeAction
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
name|e
parameter_list|)
block|{
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
decl_stmt|;
name|ActionMap
name|am
init|=
name|main
operator|.
name|getActionMap
argument_list|()
decl_stmt|;
name|InputMap
name|im
init|=
name|main
operator|.
name|getInputMap
argument_list|(
name|JComponent
operator|.
name|WHEN_IN_FOCUSED_WINDOW
argument_list|)
decl_stmt|;
name|im
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
name|am
operator|.
name|put
argument_list|(
literal|"close"
argument_list|,
name|closeAction
argument_list|)
expr_stmt|;
comment|//con.fill = GridBagConstraints.BOTH;
comment|//con.weightx = 0.3;
comment|//con.weighty = 1;
comment|//gbl.setConstraints(typeComp, con);
name|main
operator|.
name|add
argument_list|(
name|typeComp
argument_list|,
name|BorderLayout
operator|.
name|WEST
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|right
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|main
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
name|pane
operator|.
name|add
argument_list|(
name|main
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|pane
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
name|pack
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|valueChanged (ListSelectionEvent e)
specifier|public
name|void
name|valueChanged
parameter_list|(
name|ListSelectionEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|.
name|getValueIsAdjusting
argument_list|()
condition|)
block|{
return|return;
block|}
if|if
condition|(
name|lastSelected
operator|!=
literal|null
condition|)
block|{
comment|// The entry type lastSelected is now unselected, so we store the current settings
comment|// for that type in our two maps.
name|reqLists
operator|.
name|put
argument_list|(
name|lastSelected
argument_list|,
name|reqComp
operator|.
name|getFields
argument_list|()
argument_list|)
expr_stmt|;
name|optLists
operator|.
name|put
argument_list|(
name|lastSelected
argument_list|,
name|optComp
operator|.
name|getFields
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|biblatexMode
condition|)
block|{
name|opt2Lists
operator|.
name|put
argument_list|(
name|lastSelected
argument_list|,
name|optComp2
operator|.
name|getFields
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|String
name|s
init|=
name|typeComp
operator|.
name|getFirstSelected
argument_list|()
decl_stmt|;
if|if
condition|(
name|s
operator|==
literal|null
condition|)
block|{
return|return;
block|}
name|List
argument_list|<
name|String
argument_list|>
name|rl
init|=
name|reqLists
operator|.
name|get
argument_list|(
name|s
argument_list|)
decl_stmt|;
if|if
condition|(
name|rl
operator|==
literal|null
condition|)
block|{
name|EntryType
name|type
init|=
name|EntryTypes
operator|.
name|getType
argument_list|(
name|s
argument_list|,
name|bibDatabaseContext
operator|.
name|getMode
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|type
operator|==
literal|null
condition|)
block|{
comment|// New entry
name|reqComp
operator|.
name|setFields
argument_list|(
operator|new
name|ArrayList
argument_list|<>
argument_list|()
argument_list|)
expr_stmt|;
name|reqComp
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|optComp
operator|.
name|setFields
argument_list|(
operator|new
name|ArrayList
argument_list|<>
argument_list|()
argument_list|)
expr_stmt|;
name|optComp
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|biblatexMode
condition|)
block|{
name|optComp2
operator|.
name|setFields
argument_list|(
operator|new
name|ArrayList
argument_list|<>
argument_list|()
argument_list|)
expr_stmt|;
name|optComp2
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
operator|new
name|FocusRequester
argument_list|(
name|reqComp
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|List
argument_list|<
name|String
argument_list|>
name|req
init|=
name|type
operator|.
name|getRequiredFields
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|opt
decl_stmt|;
if|if
condition|(
name|biblatexMode
condition|)
block|{
name|opt
operator|=
name|type
operator|.
name|getPrimaryOptionalFields
argument_list|()
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|opt2
init|=
name|type
operator|.
name|getSecondaryOptionalFields
argument_list|()
decl_stmt|;
name|optComp2
operator|.
name|setFields
argument_list|(
name|opt2
argument_list|)
expr_stmt|;
name|optComp2
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|opt
operator|=
name|type
operator|.
name|getOptionalFields
argument_list|()
expr_stmt|;
block|}
name|reqComp
operator|.
name|setFields
argument_list|(
name|req
argument_list|)
expr_stmt|;
name|reqComp
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|optComp
operator|.
name|setFields
argument_list|(
name|opt
argument_list|)
expr_stmt|;
name|optComp
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|reqComp
operator|.
name|setFields
argument_list|(
name|rl
argument_list|)
expr_stmt|;
name|optComp
operator|.
name|setFields
argument_list|(
name|optLists
operator|.
name|get
argument_list|(
name|s
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|biblatexMode
condition|)
block|{
name|optComp2
operator|.
name|setFields
argument_list|(
name|opt2Lists
operator|.
name|get
argument_list|(
name|s
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|lastSelected
operator|=
name|s
expr_stmt|;
name|typeComp
operator|.
name|enable
argument_list|(
name|s
argument_list|,
name|changed
operator|.
name|contains
argument_list|(
name|lastSelected
argument_list|)
operator|&&
operator|!
name|defaulted
operator|.
name|contains
argument_list|(
name|lastSelected
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|applyChanges ()
specifier|private
name|void
name|applyChanges
parameter_list|()
block|{
name|valueChanged
argument_list|(
operator|new
name|ListSelectionEvent
argument_list|(
operator|new
name|JList
argument_list|<>
argument_list|()
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
comment|// Iterate over our map of required fields, and list those types if necessary:
name|List
argument_list|<
name|String
argument_list|>
name|types
init|=
name|typeComp
operator|.
name|getFields
argument_list|()
decl_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|stringListEntry
range|:
name|reqLists
operator|.
name|entrySet
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|types
operator|.
name|contains
argument_list|(
name|stringListEntry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
continue|continue;
block|}
name|List
argument_list|<
name|String
argument_list|>
name|reqStr
init|=
name|stringListEntry
operator|.
name|getValue
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|optStr
init|=
name|optLists
operator|.
name|get
argument_list|(
name|stringListEntry
operator|.
name|getKey
argument_list|()
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|opt2Str
init|=
name|opt2Lists
operator|.
name|get
argument_list|(
name|stringListEntry
operator|.
name|getKey
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|opt2Str
operator|==
literal|null
condition|)
block|{
name|opt2Str
operator|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
comment|// If this type is already existing, check if any changes have
comment|// been made
name|boolean
name|changesMade
init|=
literal|true
decl_stmt|;
if|if
condition|(
name|defaulted
operator|.
name|contains
argument_list|(
name|stringListEntry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
comment|// This type should be reverted to its default setup.
name|String
name|nm
init|=
name|EntryUtil
operator|.
name|capitalizeFirst
argument_list|(
name|stringListEntry
operator|.
name|getKey
argument_list|()
argument_list|)
decl_stmt|;
name|EntryTypes
operator|.
name|removeType
argument_list|(
name|nm
argument_list|,
name|bibDatabaseContext
operator|.
name|getMode
argument_list|()
argument_list|)
expr_stmt|;
name|updateTypesForEntries
argument_list|(
name|nm
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|EntryType
name|oldType
init|=
name|EntryTypes
operator|.
name|getType
argument_list|(
name|stringListEntry
operator|.
name|getKey
argument_list|()
argument_list|,
name|bibDatabaseContext
operator|.
name|getMode
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|oldType
operator|!=
literal|null
condition|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|oldReq
init|=
name|oldType
operator|.
name|getRequiredFieldsFlat
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|oldOpt
init|=
name|oldType
operator|.
name|getOptionalFields
argument_list|()
decl_stmt|;
if|if
condition|(
name|biblatexMode
condition|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|oldPriOpt
init|=
name|oldType
operator|.
name|getPrimaryOptionalFields
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|oldSecOpt
init|=
name|oldType
operator|.
name|getSecondaryOptionalFields
argument_list|()
decl_stmt|;
if|if
condition|(
name|equalLists
argument_list|(
name|oldReq
argument_list|,
name|reqStr
argument_list|)
operator|&&
name|equalLists
argument_list|(
name|oldPriOpt
argument_list|,
name|optStr
argument_list|)
operator|&&
name|equalLists
argument_list|(
name|oldSecOpt
argument_list|,
name|opt2Str
argument_list|)
condition|)
block|{
name|changesMade
operator|=
literal|false
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|equalLists
argument_list|(
name|oldReq
argument_list|,
name|reqStr
argument_list|)
operator|&&
name|equalLists
argument_list|(
name|oldOpt
argument_list|,
name|optStr
argument_list|)
condition|)
block|{
name|changesMade
operator|=
literal|false
expr_stmt|;
block|}
block|}
if|if
condition|(
name|changesMade
condition|)
block|{
name|CustomEntryType
name|typ
init|=
name|biblatexMode
condition|?
operator|new
name|CustomEntryType
argument_list|(
name|EntryUtil
operator|.
name|capitalizeFirst
argument_list|(
name|stringListEntry
operator|.
name|getKey
argument_list|()
argument_list|)
argument_list|,
name|reqStr
argument_list|,
name|optStr
argument_list|,
name|opt2Str
argument_list|)
else|:
operator|new
name|CustomEntryType
argument_list|(
name|EntryUtil
operator|.
name|capitalizeFirst
argument_list|(
name|stringListEntry
operator|.
name|getKey
argument_list|()
argument_list|)
argument_list|,
name|reqStr
argument_list|,
name|optStr
argument_list|)
decl_stmt|;
name|EntryTypes
operator|.
name|addOrModifyCustomEntryType
argument_list|(
name|typ
argument_list|)
expr_stmt|;
name|updateTypesForEntries
argument_list|(
name|typ
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|Set
argument_list|<
name|Object
argument_list|>
name|toRemove
init|=
operator|new
name|HashSet
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|o
range|:
name|EntryTypes
operator|.
name|getAllTypes
argument_list|(
name|bibDatabaseContext
operator|.
name|getMode
argument_list|()
argument_list|)
control|)
block|{
if|if
condition|(
operator|!
name|types
operator|.
name|contains
argument_list|(
name|o
argument_list|)
condition|)
block|{
name|toRemove
operator|.
name|add
argument_list|(
name|o
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Remove those that should be removed:
if|if
condition|(
operator|!
name|toRemove
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
for|for
control|(
name|Object
name|aToRemove
range|:
name|toRemove
control|)
block|{
name|typeDeletion
argument_list|(
operator|(
name|String
operator|)
name|aToRemove
argument_list|)
expr_stmt|;
block|}
block|}
name|updateTables
argument_list|()
expr_stmt|;
block|}
DECL|method|typeDeletion (String name)
specifier|private
name|void
name|typeDeletion
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|EntryType
name|type
init|=
name|EntryTypes
operator|.
name|getType
argument_list|(
name|name
argument_list|,
name|bibDatabaseContext
operator|.
name|getMode
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|type
operator|instanceof
name|CustomEntryType
condition|)
block|{
if|if
condition|(
name|EntryTypes
operator|.
name|getStandardType
argument_list|(
name|name
argument_list|,
name|bibDatabaseContext
operator|.
name|getMode
argument_list|()
argument_list|)
operator|==
literal|null
condition|)
block|{
name|int
name|reply
init|=
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
name|frame
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"All entries of this "
operator|+
literal|"type will be declared "
operator|+
literal|"typeless. Continue?"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Delete custom format"
argument_list|)
operator|+
literal|" '"
operator|+
name|EntryUtil
operator|.
name|capitalizeFirst
argument_list|(
name|name
argument_list|)
operator|+
literal|'\''
argument_list|,
name|JOptionPane
operator|.
name|YES_NO_OPTION
argument_list|,
name|JOptionPane
operator|.
name|WARNING_MESSAGE
argument_list|)
decl_stmt|;
if|if
condition|(
name|reply
operator|!=
name|JOptionPane
operator|.
name|YES_OPTION
condition|)
block|{
return|return;
block|}
block|}
name|EntryTypes
operator|.
name|removeType
argument_list|(
name|name
argument_list|,
name|bibDatabaseContext
operator|.
name|getMode
argument_list|()
argument_list|)
expr_stmt|;
name|updateTypesForEntries
argument_list|(
name|EntryUtil
operator|.
name|capitalizeFirst
argument_list|(
name|name
argument_list|)
argument_list|)
expr_stmt|;
name|changed
operator|.
name|remove
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|reqLists
operator|.
name|remove
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|optLists
operator|.
name|remove
argument_list|(
name|name
argument_list|)
expr_stmt|;
if|if
condition|(
name|biblatexMode
condition|)
block|{
name|opt2Lists
operator|.
name|remove
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|equalLists (List<String> one, List<String> two)
specifier|private
specifier|static
name|boolean
name|equalLists
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|one
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|two
parameter_list|)
block|{
if|if
condition|(
operator|(
name|one
operator|==
literal|null
operator|)
operator|&&
operator|(
name|two
operator|==
literal|null
operator|)
condition|)
block|{
return|return
literal|true
return|;
comment|// Both null.
block|}
if|if
condition|(
operator|(
name|one
operator|==
literal|null
operator|)
operator|||
operator|(
name|two
operator|==
literal|null
operator|)
condition|)
block|{
return|return
literal|false
return|;
comment|// One of them null, the other not.
block|}
if|if
condition|(
name|one
operator|.
name|size
argument_list|()
operator|!=
name|two
operator|.
name|size
argument_list|()
condition|)
block|{
return|return
literal|false
return|;
comment|// Different length.
block|}
comment|// If we get here, we know that both are non-null, and that they have the same length.
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|one
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
name|one
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|equals
argument_list|(
name|two
operator|.
name|get
argument_list|(
name|i
argument_list|)
argument_list|)
condition|)
block|{
return|return
literal|false
return|;
block|}
block|}
comment|// If we get here, all entries have matched.
return|return
literal|true
return|;
block|}
annotation|@
name|Override
DECL|method|actionPerformed (ActionEvent e)
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
name|e
operator|.
name|getSource
argument_list|()
operator|==
name|ok
condition|)
block|{
name|applyChanges
argument_list|()
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|e
operator|.
name|getSource
argument_list|()
operator|==
name|cancel
condition|)
block|{
name|dispose
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|e
operator|.
name|getSource
argument_list|()
operator|==
name|apply
condition|)
block|{
name|applyChanges
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|e
operator|.
name|getSource
argument_list|()
operator|==
name|typeComp
condition|)
block|{
comment|//System.out.println("add: "+e.getActionCommand());
name|typeComp
operator|.
name|selectField
argument_list|(
name|e
operator|.
name|getActionCommand
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Cycle through all databases, and make sure everything is updated with      * the new type customization. This includes making sure all entries have      * a valid type, that no obsolete entry editors are around, and that      * the right-click menus' change type menu is up-to-date.      */
DECL|method|updateTypesForEntries (String typeName)
specifier|private
name|void
name|updateTypesForEntries
parameter_list|(
name|String
name|typeName
parameter_list|)
block|{
for|for
control|(
name|BasePanel
name|bp
range|:
name|frame
operator|.
name|getBasePanelList
argument_list|()
control|)
block|{
comment|// Invalidate associated cached entry editor
name|bp
operator|.
name|entryEditors
operator|.
name|remove
argument_list|(
name|typeName
argument_list|)
expr_stmt|;
for|for
control|(
name|BibEntry
name|entry
range|:
name|bp
operator|.
name|database
argument_list|()
operator|.
name|getEntries
argument_list|()
control|)
block|{
name|EntryType
name|newType
init|=
name|EntryTypes
operator|.
name|getType
argument_list|(
name|entry
operator|.
name|getType
argument_list|()
argument_list|,
name|bibDatabaseContext
operator|.
name|getMode
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|newType
operator|!=
literal|null
condition|)
block|{
name|entry
operator|.
name|setType
argument_list|(
name|newType
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
DECL|method|updateTables ()
specifier|private
name|void
name|updateTables
parameter_list|()
block|{
for|for
control|(
name|BasePanel
name|basePanel
range|:
name|frame
operator|.
name|getBasePanelList
argument_list|()
control|)
block|{
operator|(
operator|(
name|javax
operator|.
name|swing
operator|.
name|table
operator|.
name|AbstractTableModel
operator|)
name|basePanel
operator|.
name|mainTable
operator|.
name|getModel
argument_list|()
operator|)
operator|.
name|fireTableDataChanged
argument_list|()
expr_stmt|;
block|}
block|}
comment|// DEFAULT button pressed. Remember that this entry should be reset to default,
comment|// unless changes are made later.
DECL|class|DefaultListener
specifier|private
class|class
name|DefaultListener
implements|implements
name|ActionListener
block|{
annotation|@
name|Override
DECL|method|actionPerformed (ActionEvent e)
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
name|lastSelected
operator|==
literal|null
condition|)
block|{
return|return;
block|}
name|defaulted
operator|.
name|add
argument_list|(
name|lastSelected
argument_list|)
expr_stmt|;
name|EntryType
name|type
init|=
name|EntryTypes
operator|.
name|getStandardType
argument_list|(
name|lastSelected
argument_list|,
name|bibDatabaseContext
operator|.
name|getMode
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|type
operator|!=
literal|null
condition|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|of
init|=
name|type
operator|.
name|getOptionalFields
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|req
init|=
name|type
operator|.
name|getRequiredFields
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|opt1
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|opt2
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
operator|(
name|of
operator|.
name|isEmpty
argument_list|()
operator|)
condition|)
block|{
if|if
condition|(
name|biblatexMode
condition|)
block|{
name|opt1
operator|=
name|type
operator|.
name|getPrimaryOptionalFields
argument_list|()
expr_stmt|;
name|opt2
operator|=
name|type
operator|.
name|getSecondaryOptionalFields
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|opt1
operator|=
name|of
expr_stmt|;
block|}
block|}
name|reqComp
operator|.
name|setFields
argument_list|(
name|req
argument_list|)
expr_stmt|;
name|reqComp
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|optComp
operator|.
name|setFields
argument_list|(
name|opt1
argument_list|)
expr_stmt|;
if|if
condition|(
name|biblatexMode
condition|)
block|{
name|optComp2
operator|.
name|setFields
argument_list|(
name|opt2
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
DECL|class|DataListener
class|class
name|DataListener
implements|implements
name|ListDataListener
block|{
annotation|@
name|Override
DECL|method|intervalAdded (javax.swing.event.ListDataEvent e)
specifier|public
name|void
name|intervalAdded
parameter_list|(
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|ListDataEvent
name|e
parameter_list|)
block|{
name|record
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|intervalRemoved (javax.swing.event.ListDataEvent e)
specifier|public
name|void
name|intervalRemoved
parameter_list|(
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|ListDataEvent
name|e
parameter_list|)
block|{
name|record
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|contentsChanged (javax.swing.event.ListDataEvent e)
specifier|public
name|void
name|contentsChanged
parameter_list|(
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|ListDataEvent
name|e
parameter_list|)
block|{
name|record
argument_list|()
expr_stmt|;
block|}
DECL|method|record ()
specifier|private
name|void
name|record
parameter_list|()
block|{
if|if
condition|(
name|lastSelected
operator|==
literal|null
condition|)
block|{
return|return;
block|}
name|defaulted
operator|.
name|remove
argument_list|(
name|lastSelected
argument_list|)
expr_stmt|;
name|changed
operator|.
name|add
argument_list|(
name|lastSelected
argument_list|)
expr_stmt|;
name|typeComp
operator|.
name|enable
argument_list|(
name|lastSelected
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

