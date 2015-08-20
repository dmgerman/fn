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
name|BorderLayout
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|GridBagConstraints
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|GridBagLayout
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Insets
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
comment|/*      * Dialog that prompts the user to choose a type for an entry.      * Returns null if cancelled.      */
DECL|field|type
specifier|private
name|BibtexEntryType
name|type
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
DECL|field|COLNUM
specifier|private
specifier|static
specifier|final
name|int
name|COLNUM
init|=
literal|3
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
name|BibtexEntryType
name|type
decl_stmt|;
DECL|method|TypeButton (String label, BibtexEntryType type_)
specifier|public
name|TypeButton
parameter_list|(
name|String
name|label
parameter_list|,
name|BibtexEntryType
name|type_
parameter_list|)
block|{
name|super
argument_list|(
name|label
argument_list|)
expr_stmt|;
name|type
operator|=
name|type_
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
DECL|method|EntryTypeDialog (JabRefFrame baseFrame_)
specifier|public
name|EntryTypeDialog
parameter_list|(
name|JabRefFrame
name|baseFrame_
parameter_list|)
block|{
name|super
argument_list|(
name|baseFrame_
argument_list|,
literal|true
argument_list|)
expr_stmt|;
comment|// Set modal on.
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
name|JPanel
name|pan
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|pan
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|JPanel
name|buttons
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|JButton
comment|// ok = new JButton("Ok"),
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
comment|//ok.addActionListener(this);
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
name|baseFrame_
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
comment|//buttons.add(ok);
name|ButtonBarBuilder
name|bb
init|=
operator|new
name|ButtonBarBuilder
argument_list|(
name|buttons
argument_list|)
decl_stmt|;
comment|//buttons.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
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
name|getContentPane
argument_list|()
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
name|GridBagLayout
name|gbl
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
name|pan
operator|.
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|GridBagConstraints
name|con
init|=
operator|new
name|GridBagConstraints
argument_list|()
decl_stmt|;
name|con
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|WEST
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|HORIZONTAL
expr_stmt|;
name|con
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
name|int
name|col
init|=
literal|0
decl_stmt|;
for|for
control|(
name|BibtexEntryType
name|tp
range|:
name|BibtexEntryType
operator|.
name|getAllValues
argument_list|()
control|)
block|{
if|if
condition|(
name|tp
operator|.
name|isVisibleAtNewEntryDialog
argument_list|()
condition|)
block|{
name|TypeButton
name|b
init|=
operator|new
name|TypeButton
argument_list|(
name|StringUtil
operator|.
name|capitalizeFirst
argument_list|(
name|tp
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|,
name|tp
argument_list|)
decl_stmt|;
name|b
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
name|COLNUM
condition|)
block|{
name|col
operator|=
literal|0
expr_stmt|;
name|con
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
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
block|}
name|gbl
operator|.
name|setConstraints
argument_list|(
name|b
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|pan
operator|.
name|add
argument_list|(
name|b
argument_list|)
expr_stmt|;
block|}
block|}
name|pan
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"Entry types"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|//pan.setBackground(Color.white);
comment|//buttons.setBackground(Color.white);
name|pack
argument_list|()
expr_stmt|;
name|setResizable
argument_list|(
literal|false
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
name|BibtexEntryType
name|getChoice
parameter_list|()
block|{
comment|//return type;
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
comment|//  new ImageIcon(GUIGlobals.imagepath+GUIGlobals.closeIconFile));
comment|//putValue(SHORT_DESCRIPTION, "Cancel");
comment|//putValue(MNEMONIC_KEY, GUIGlobals.closeKeyCode);
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

