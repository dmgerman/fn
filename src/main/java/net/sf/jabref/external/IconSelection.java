begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.external
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|external
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
name|Component
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
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|LinkedHashSet
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
name|java
operator|.
name|util
operator|.
name|Map
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
name|DefaultListModel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|ImageIcon
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JButton
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JDialog
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
name|JList
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JScrollPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|ListCellRenderer
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|ListSelectionModel
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
name|GUIGlobals
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

begin_comment
comment|/**  * Dialog box for choosing an icon for an external file type.  */
end_comment

begin_class
DECL|class|IconSelection
class|class
name|IconSelection
extends|extends
name|JDialog
block|{
DECL|field|icons
specifier|private
name|JList
name|icons
decl_stmt|;
DECL|field|iconKeys
specifier|private
name|List
argument_list|<
name|String
argument_list|>
name|iconKeys
decl_stmt|;
DECL|field|ok
specifier|private
specifier|final
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
literal|"Ok"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|cancel
specifier|private
specifier|final
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
DECL|field|okPressed
specifier|private
name|boolean
name|okPressed
decl_stmt|;
DECL|field|selected
specifier|private
name|int
name|selected
init|=
operator|-
literal|1
decl_stmt|;
DECL|field|parent
specifier|private
specifier|final
name|JDialog
name|parent
decl_stmt|;
DECL|method|IconSelection (JDialog parent, String initialSelection)
specifier|public
name|IconSelection
parameter_list|(
name|JDialog
name|parent
parameter_list|,
name|String
name|initialSelection
parameter_list|)
block|{
name|super
argument_list|(
name|parent
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Select icon"
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|this
operator|.
name|parent
operator|=
name|parent
expr_stmt|;
name|init
argument_list|(
name|initialSelection
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|setVisible (boolean visible)
specifier|public
name|void
name|setVisible
parameter_list|(
name|boolean
name|visible
parameter_list|)
block|{
if|if
condition|(
name|visible
condition|)
block|{
name|okPressed
operator|=
literal|false
expr_stmt|;
name|selected
operator|=
operator|-
literal|1
expr_stmt|;
block|}
name|super
operator|.
name|setVisible
argument_list|(
name|visible
argument_list|)
expr_stmt|;
block|}
comment|/**      * After dialog has closed, this method reports whether a selection was made      * or it was cancelled.      * @return true if a selection was made.      */
DECL|method|isOkPressed ()
specifier|public
name|boolean
name|isOkPressed
parameter_list|()
block|{
return|return
name|okPressed
return|;
block|}
DECL|method|getSelectedIconKey ()
specifier|public
name|String
name|getSelectedIconKey
parameter_list|()
block|{
if|if
condition|(
name|selected
operator|>=
literal|0
condition|)
block|{
return|return
name|iconKeys
operator|.
name|get
argument_list|(
name|selected
argument_list|)
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
DECL|method|init (String initialSelection)
specifier|private
name|void
name|init
parameter_list|(
name|String
name|initialSelection
parameter_list|)
block|{
name|int
name|initSelIndex
init|=
operator|-
literal|1
decl_stmt|;
name|iconKeys
operator|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|icns
init|=
name|GUIGlobals
operator|.
name|getAllIcons
argument_list|()
decl_stmt|;
name|HashSet
argument_list|<
name|ImageIcon
argument_list|>
name|iconSet
init|=
operator|new
name|LinkedHashSet
argument_list|<
name|ImageIcon
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|key
range|:
name|icns
operator|.
name|keySet
argument_list|()
control|)
block|{
name|ImageIcon
name|icon
init|=
name|GUIGlobals
operator|.
name|getImage
argument_list|(
name|key
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|iconSet
operator|.
name|contains
argument_list|(
name|icon
argument_list|)
condition|)
block|{
name|iconKeys
operator|.
name|add
argument_list|(
name|key
argument_list|)
expr_stmt|;
if|if
condition|(
name|key
operator|.
name|equals
argument_list|(
name|initialSelection
argument_list|)
condition|)
block|{
name|initSelIndex
operator|=
name|iconKeys
operator|.
name|size
argument_list|()
operator|-
literal|1
expr_stmt|;
block|}
block|}
name|iconSet
operator|.
name|add
argument_list|(
name|icon
argument_list|)
expr_stmt|;
block|}
name|DefaultListModel
name|listModel
init|=
operator|new
name|DefaultListModel
argument_list|()
decl_stmt|;
name|icons
operator|=
operator|new
name|JList
argument_list|(
name|listModel
argument_list|)
expr_stmt|;
for|for
control|(
name|ImageIcon
name|anIconSet
range|:
name|iconSet
control|)
block|{
name|listModel
operator|.
name|addElement
argument_list|(
operator|new
name|JLabel
argument_list|(
name|anIconSet
argument_list|)
argument_list|)
expr_stmt|;
block|}
class|class
name|MyRenderer
implements|implements
name|ListCellRenderer
block|{
specifier|final
name|JLabel
name|comp
init|=
operator|new
name|JLabel
argument_list|()
decl_stmt|;
specifier|public
name|MyRenderer
parameter_list|()
block|{
name|comp
operator|.
name|setOpaque
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|comp
operator|.
name|setIconTextGap
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|comp
operator|.
name|setHorizontalAlignment
argument_list|(
name|JLabel
operator|.
name|CENTER
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Component
name|getListCellRendererComponent
parameter_list|(
name|JList
name|list
parameter_list|,
name|Object
name|value
parameter_list|,
name|int
name|i
parameter_list|,
name|boolean
name|isSelected
parameter_list|,
name|boolean
name|hasFocus
parameter_list|)
block|{
name|comp
operator|.
name|setText
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|comp
operator|.
name|setIcon
argument_list|(
operator|(
operator|(
name|JLabel
operator|)
name|value
operator|)
operator|.
name|getIcon
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|isSelected
condition|)
block|{
name|comp
operator|.
name|setBackground
argument_list|(
name|list
operator|.
name|getSelectionBackground
argument_list|()
argument_list|)
expr_stmt|;
name|comp
operator|.
name|setForeground
argument_list|(
name|list
operator|.
name|getSelectionForeground
argument_list|()
argument_list|)
expr_stmt|;
name|comp
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEtchedBorder
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|comp
operator|.
name|setBackground
argument_list|(
name|list
operator|.
name|getBackground
argument_list|()
argument_list|)
expr_stmt|;
name|comp
operator|.
name|setForeground
argument_list|(
name|list
operator|.
name|getForeground
argument_list|()
argument_list|)
expr_stmt|;
name|comp
operator|.
name|setBorder
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
return|return
name|comp
return|;
block|}
block|}
if|if
condition|(
name|initSelIndex
operator|>=
literal|0
condition|)
block|{
name|icons
operator|.
name|setSelectedIndex
argument_list|(
name|initSelIndex
argument_list|)
expr_stmt|;
block|}
name|icons
operator|.
name|setCellRenderer
argument_list|(
operator|new
name|MyRenderer
argument_list|()
argument_list|)
expr_stmt|;
name|icons
operator|.
name|setSelectionMode
argument_list|(
name|ListSelectionModel
operator|.
name|SINGLE_SELECTION
argument_list|)
expr_stmt|;
name|icons
operator|.
name|setLayoutOrientation
argument_list|(
name|JList
operator|.
name|HORIZONTAL_WRAP
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
name|ok
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
name|ok
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
name|actionEvent
parameter_list|)
block|{
name|okPressed
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|icons
operator|.
name|getSelectedValue
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|selected
operator|=
name|icons
operator|.
name|getSelectedIndex
argument_list|()
expr_stmt|;
block|}
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
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
name|okPressed
operator|=
literal|false
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
operator|new
name|JScrollPane
argument_list|(
name|icons
argument_list|)
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
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
name|pack
argument_list|()
expr_stmt|;
name|setLocationRelativeTo
argument_list|(
name|parent
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

