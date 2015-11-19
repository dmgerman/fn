begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|awt
operator|.
name|event
operator|.
name|WindowAdapter
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
name|WindowEvent
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
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
name|gui
operator|.
name|keyboard
operator|.
name|KeyBinds
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
name|model
operator|.
name|entry
operator|.
name|*
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jdesktop
operator|.
name|swingx
operator|.
name|VerticalLayout
import|;
end_import

begin_comment
comment|/**  * Dialog that prompts the user to choose a type for an entry.  * Returns null if cancelled.  */
end_comment

begin_class
DECL|class|EntryTypeDialog
specifier|public
class|class
name|EntryTypeDialog
extends|extends
name|JDialog
implements|implements
name|ActionListener
block|{
DECL|field|type
specifier|private
name|EntryType
name|type
decl_stmt|;
DECL|field|COLUMN
specifier|private
specifier|static
specifier|final
name|int
name|COLUMN
init|=
literal|3
decl_stmt|;
DECL|field|biblatexMode
specifier|private
name|boolean
name|biblatexMode
decl_stmt|;
DECL|field|cancelAction
specifier|private
specifier|final
name|CancelAction
name|cancelAction
init|=
operator|new
name|CancelAction
argument_list|()
decl_stmt|;
DECL|class|TypeButton
specifier|static
class|class
name|TypeButton
extends|extends
name|JButton
implements|implements
name|Comparable
argument_list|<
name|TypeButton
argument_list|>
block|{
DECL|field|type
specifier|final
name|EntryType
name|type
decl_stmt|;
DECL|method|TypeButton (String label, EntryType _type)
specifier|public
name|TypeButton
parameter_list|(
name|String
name|label
parameter_list|,
name|EntryType
name|_type
parameter_list|)
block|{
name|super
argument_list|(
name|label
argument_list|)
expr_stmt|;
name|type
operator|=
name|_type
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|compareTo (TypeButton o)
specifier|public
name|int
name|compareTo
parameter_list|(
name|TypeButton
name|o
parameter_list|)
block|{
return|return
name|type
operator|.
name|getName
argument_list|()
operator|.
name|compareTo
argument_list|(
name|o
operator|.
name|type
operator|.
name|getName
argument_list|()
argument_list|)
return|;
block|}
block|}
DECL|method|EntryTypeDialog (JabRefFrame frame)
specifier|public
name|EntryTypeDialog
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|)
block|{
comment|// modal dialog
name|super
argument_list|(
name|frame
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|biblatexMode
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|BIBLATEX_MODE
argument_list|)
expr_stmt|;
name|setTitle
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Select entry type"
argument_list|)
argument_list|)
expr_stmt|;
name|addWindowListener
argument_list|(
operator|new
name|WindowAdapter
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|windowClosing
parameter_list|(
name|WindowEvent
name|e
parameter_list|)
block|{
name|cancelAction
operator|.
name|actionPerformed
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
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
name|createCancelButtonBarPanel
argument_list|()
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
name|createEntryGroupsPanel
argument_list|()
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|pack
argument_list|()
expr_stmt|;
name|setResizable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
DECL|method|createEntryGroupsPanel ()
specifier|private
name|JPanel
name|createEntryGroupsPanel
parameter_list|()
block|{
name|JPanel
name|panel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|panel
operator|.
name|setLayout
argument_list|(
operator|new
name|VerticalLayout
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|biblatexMode
condition|)
block|{
name|panel
operator|.
name|add
argument_list|(
name|createEntryGroupPanel
argument_list|(
literal|"BibLateX"
argument_list|,
name|EntryTypes
operator|.
name|getAllValues
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|panel
operator|.
name|add
argument_list|(
name|createEntryGroupPanel
argument_list|(
literal|"BibTeX"
argument_list|,
name|BibtexEntryTypes
operator|.
name|ALL
argument_list|)
argument_list|)
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|createEntryGroupPanel
argument_list|(
literal|"IEEETran"
argument_list|,
name|IEEETranEntryTypes
operator|.
name|ALL
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|panel
return|;
block|}
DECL|method|createCancelButtonBarPanel ()
specifier|private
name|JPanel
name|createCancelButtonBarPanel
parameter_list|()
block|{
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
name|cancel
operator|.
name|addActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
comment|// Make ESC close dialog, equivalent to clicking Cancel.
name|cancel
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
name|KeyBinds
operator|.
name|CLOSE_DIALOG
argument_list|)
argument_list|,
literal|"close"
argument_list|)
expr_stmt|;
name|cancel
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
name|JPanel
name|buttons
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|ButtonBarBuilder
name|bb
init|=
operator|new
name|ButtonBarBuilder
argument_list|(
name|buttons
argument_list|)
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
name|cancel
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
return|return
name|buttons
return|;
block|}
DECL|method|createEntryGroupPanel (String groupTitle, Collection<EntryType> entries)
specifier|private
name|JPanel
name|createEntryGroupPanel
parameter_list|(
name|String
name|groupTitle
parameter_list|,
name|Collection
argument_list|<
name|EntryType
argument_list|>
name|entries
parameter_list|)
block|{
name|JPanel
name|panel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|GridBagLayout
name|bagLayout
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
name|panel
operator|.
name|setLayout
argument_list|(
name|bagLayout
argument_list|)
expr_stmt|;
name|GridBagConstraints
name|constraints
init|=
operator|new
name|GridBagConstraints
argument_list|()
decl_stmt|;
name|constraints
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|WEST
expr_stmt|;
name|constraints
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|HORIZONTAL
expr_stmt|;
name|constraints
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|4
argument_list|,
literal|4
argument_list|,
literal|4
argument_list|,
literal|4
argument_list|)
expr_stmt|;
comment|// column count
name|int
name|col
init|=
literal|0
decl_stmt|;
for|for
control|(
name|EntryType
name|entryType
range|:
name|entries
control|)
block|{
name|TypeButton
name|entryButton
init|=
operator|new
name|TypeButton
argument_list|(
name|entryType
operator|.
name|getName
argument_list|()
argument_list|,
name|entryType
argument_list|)
decl_stmt|;
name|entryButton
operator|.
name|addActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
comment|// Check if we should finish the row.
name|col
operator|++
expr_stmt|;
if|if
condition|(
name|col
operator|==
name|EntryTypeDialog
operator|.
name|COLUMN
condition|)
block|{
name|col
operator|=
literal|0
expr_stmt|;
name|constraints
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
block|}
else|else
block|{
name|constraints
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
block|}
name|bagLayout
operator|.
name|setConstraints
argument_list|(
name|entryButton
argument_list|,
name|constraints
argument_list|)
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|entryButton
argument_list|)
expr_stmt|;
block|}
name|panel
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createTitledBorder
argument_list|(
name|BorderFactory
operator|.
name|createEtchedBorder
argument_list|()
argument_list|,
name|groupTitle
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|panel
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
operator|instanceof
name|TypeButton
condition|)
block|{
name|type
operator|=
operator|(
operator|(
name|TypeButton
operator|)
name|e
operator|.
name|getSource
argument_list|()
operator|)
operator|.
name|type
expr_stmt|;
block|}
name|dispose
argument_list|()
expr_stmt|;
block|}
DECL|method|getChoice ()
specifier|public
name|EntryType
name|getChoice
parameter_list|()
block|{
return|return
name|type
return|;
block|}
DECL|class|CancelAction
class|class
name|CancelAction
extends|extends
name|AbstractAction
block|{
DECL|method|CancelAction ()
specifier|public
name|CancelAction
parameter_list|()
block|{
name|super
argument_list|(
literal|"Cancel"
argument_list|)
expr_stmt|;
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
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

