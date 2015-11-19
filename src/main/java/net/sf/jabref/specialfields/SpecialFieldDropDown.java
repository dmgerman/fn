begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2012 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.specialfields
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|specialfields
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Dimension
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
name|JMenuItem
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JPopupMenu
import|;
end_import

begin_import
import|import
name|com
operator|.
name|jgoodies
operator|.
name|looks
operator|.
name|HeaderStyle
import|;
end_import

begin_import
import|import
name|com
operator|.
name|jgoodies
operator|.
name|looks
operator|.
name|Options
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
name|gui
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
name|logic
operator|.
name|util
operator|.
name|OS
import|;
end_import

begin_class
DECL|class|SpecialFieldDropDown
specifier|public
class|class
name|SpecialFieldDropDown
block|{
DECL|method|generateSpecialFieldButtonWithDropDown (SpecialField field, JabRefFrame frame)
specifier|public
specifier|static
name|JButton
name|generateSpecialFieldButtonWithDropDown
parameter_list|(
name|SpecialField
name|field
parameter_list|,
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|Dimension
name|buttonDim
init|=
operator|new
name|Dimension
argument_list|(
literal|23
argument_list|,
literal|23
argument_list|)
decl_stmt|;
name|JButton
name|button
init|=
operator|new
name|JButton
argument_list|(
name|field
operator|.
name|getRepresentingIcon
argument_list|()
argument_list|)
decl_stmt|;
name|button
operator|.
name|setToolTipText
argument_list|(
name|field
operator|.
name|getToolTip
argument_list|()
argument_list|)
expr_stmt|;
name|button
operator|.
name|setPreferredSize
argument_list|(
name|buttonDim
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|OS
operator|.
name|OS_X
condition|)
block|{
name|button
operator|.
name|setMargin
argument_list|(
operator|new
name|Insets
argument_list|(
literal|1
argument_list|,
literal|0
argument_list|,
literal|2
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|button
operator|.
name|setBorder
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|button
operator|.
name|setBorderPainted
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|button
operator|.
name|setRolloverEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|button
operator|.
name|setOpaque
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|button
operator|.
name|setBounds
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
name|buttonDim
operator|.
name|width
argument_list|,
name|buttonDim
operator|.
name|height
argument_list|)
expr_stmt|;
name|button
operator|.
name|setSize
argument_list|(
name|buttonDim
argument_list|)
expr_stmt|;
name|button
operator|.
name|setMinimumSize
argument_list|(
name|buttonDim
argument_list|)
expr_stmt|;
name|button
operator|.
name|setMaximumSize
argument_list|(
name|buttonDim
argument_list|)
expr_stmt|;
name|button
operator|.
name|putClientProperty
argument_list|(
name|Options
operator|.
name|HEADER_STYLE_KEY
argument_list|,
name|HeaderStyle
operator|.
name|BOTH
argument_list|)
expr_stmt|;
name|button
operator|.
name|addActionListener
argument_list|(
operator|new
name|MenuButtonActionListener
argument_list|(
name|field
argument_list|,
name|frame
argument_list|,
name|button
argument_list|,
name|buttonDim
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|button
return|;
block|}
DECL|class|MenuButtonActionListener
specifier|private
specifier|static
class|class
name|MenuButtonActionListener
implements|implements
name|ActionListener
block|{
DECL|field|popup
specifier|private
name|JPopupMenu
name|popup
decl_stmt|;
DECL|field|dim
specifier|private
specifier|final
name|Dimension
name|dim
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|field
specifier|private
specifier|final
name|SpecialField
name|field
decl_stmt|;
DECL|field|button
specifier|private
specifier|final
name|JButton
name|button
decl_stmt|;
DECL|method|MenuButtonActionListener (SpecialField field, JabRefFrame frame, JButton button, Dimension dim)
specifier|public
name|MenuButtonActionListener
parameter_list|(
name|SpecialField
name|field
parameter_list|,
name|JabRefFrame
name|frame
parameter_list|,
name|JButton
name|button
parameter_list|,
name|Dimension
name|dim
parameter_list|)
block|{
name|this
operator|.
name|field
operator|=
name|field
expr_stmt|;
name|this
operator|.
name|dim
operator|=
name|dim
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|this
operator|.
name|button
operator|=
name|button
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
name|popup
operator|==
literal|null
condition|)
block|{
name|popup
operator|=
operator|new
name|JPopupMenu
argument_list|()
expr_stmt|;
for|for
control|(
name|SpecialFieldValue
name|val
range|:
name|field
operator|.
name|getValues
argument_list|()
control|)
block|{
name|JMenuItem
name|item
init|=
operator|new
name|JMenuItem
argument_list|(
name|val
operator|.
name|getIcon
argument_list|()
argument_list|)
decl_stmt|;
name|item
operator|.
name|setText
argument_list|(
name|val
operator|.
name|getMenuString
argument_list|()
argument_list|)
expr_stmt|;
name|item
operator|.
name|setToolTipText
argument_list|(
name|val
operator|.
name|getToolTipText
argument_list|()
argument_list|)
expr_stmt|;
name|item
operator|.
name|addActionListener
argument_list|(
operator|new
name|PopupitemActionListener
argument_list|(
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
argument_list|,
name|val
operator|.
name|getActionName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|item
operator|.
name|setMargin
argument_list|(
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|popup
operator|.
name|add
argument_list|(
name|item
argument_list|)
expr_stmt|;
block|}
block|}
name|popup
operator|.
name|show
argument_list|(
name|button
argument_list|,
literal|0
argument_list|,
name|dim
operator|.
name|height
argument_list|)
expr_stmt|;
block|}
DECL|class|PopupitemActionListener
specifier|private
class|class
name|PopupitemActionListener
implements|implements
name|ActionListener
block|{
DECL|field|panel
specifier|private
specifier|final
name|BasePanel
name|panel
decl_stmt|;
DECL|field|actionName
specifier|private
specifier|final
name|String
name|actionName
decl_stmt|;
DECL|method|PopupitemActionListener (BasePanel panel, String actionName)
specifier|public
name|PopupitemActionListener
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|String
name|actionName
parameter_list|)
block|{
name|this
operator|.
name|panel
operator|=
name|panel
expr_stmt|;
name|this
operator|.
name|actionName
operator|=
name|actionName
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
name|panel
operator|.
name|runCommand
argument_list|(
name|actionName
argument_list|)
expr_stmt|;
name|popup
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

