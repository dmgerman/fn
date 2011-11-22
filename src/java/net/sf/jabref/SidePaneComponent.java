begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|java
operator|.
name|net
operator|.
name|URL
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
name|JToolBar
import|;
end_import

begin_import
import|import
name|com
operator|.
name|jgoodies
operator|.
name|uif_lite
operator|.
name|panel
operator|.
name|SimpleInternalFrame
import|;
end_import

begin_class
DECL|class|SidePaneComponent
specifier|public
specifier|abstract
class|class
name|SidePaneComponent
extends|extends
name|SimpleInternalFrame
block|{
DECL|field|close
specifier|protected
name|JButton
name|close
init|=
operator|new
name|JButton
argument_list|(
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"close"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|visible
specifier|protected
name|boolean
name|visible
init|=
literal|false
decl_stmt|;
DECL|field|manager
specifier|protected
name|SidePaneManager
name|manager
decl_stmt|;
DECL|field|panel
specifier|protected
name|BasePanel
name|panel
init|=
literal|null
decl_stmt|;
DECL|method|SidePaneComponent (SidePaneManager manager, URL icon, String title)
specifier|public
name|SidePaneComponent
parameter_list|(
name|SidePaneManager
name|manager
parameter_list|,
name|URL
name|icon
parameter_list|,
name|String
name|title
parameter_list|)
block|{
name|super
argument_list|(
operator|new
name|ImageIcon
argument_list|(
name|icon
argument_list|)
argument_list|,
name|title
argument_list|)
expr_stmt|;
name|this
operator|.
name|manager
operator|=
name|manager
expr_stmt|;
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|JToolBar
name|tlb
init|=
operator|new
name|JToolBar
argument_list|()
decl_stmt|;
name|close
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
comment|// tlb.setOpaque(false);
name|close
operator|.
name|setBorder
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|tlb
operator|.
name|add
argument_list|(
name|close
argument_list|)
expr_stmt|;
name|close
operator|.
name|addActionListener
argument_list|(
operator|new
name|CloseButtonListener
argument_list|()
argument_list|)
expr_stmt|;
name|setToolBar
argument_list|(
name|tlb
argument_list|)
expr_stmt|;
comment|// setBorder(BorderFactory.createEtchedBorder());
comment|// setBorder(BorderFactory.createMatteBorder(1,1,1,1,java.awt.Color.green));
comment|// setPreferredSize(new java.awt.Dimension
comment|// (GUIGlobals.SPLIT_PANE_DIVIDER_LOCATION, 200));
comment|// Util.pr(""+GUIGlobals.SPLIT_PANE_DIVIDER_LOCATION);
block|}
DECL|method|hideAway ()
specifier|public
name|void
name|hideAway
parameter_list|()
block|{
name|manager
operator|.
name|hideComponent
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Used by SidePaneManager only, to keep track of visibility. 	 *  	 */
DECL|method|setVisibility (boolean vis)
name|void
name|setVisibility
parameter_list|(
name|boolean
name|vis
parameter_list|)
block|{
name|visible
operator|=
name|vis
expr_stmt|;
block|}
comment|/** 	 * Used by SidePaneManager only, to keep track of visibility. 	 *  	 */
DECL|method|hasVisibility ()
name|boolean
name|hasVisibility
parameter_list|()
block|{
return|return
name|visible
return|;
block|}
DECL|method|setActiveBasePanel (BasePanel panel)
specifier|public
name|void
name|setActiveBasePanel
parameter_list|(
name|BasePanel
name|panel
parameter_list|)
block|{
name|this
operator|.
name|panel
operator|=
name|panel
expr_stmt|;
block|}
DECL|method|getActiveBasePanel ()
specifier|public
name|BasePanel
name|getActiveBasePanel
parameter_list|()
block|{
return|return
name|panel
return|;
block|}
comment|/** 	 * Override this method if the component needs to make any changes before it 	 * can close. 	 */
DECL|method|componentClosing ()
specifier|public
name|void
name|componentClosing
parameter_list|()
block|{  	}
comment|/** 	 * Override this method if the component needs to do any actions when 	 * opening. 	 */
DECL|method|componentOpening ()
specifier|public
name|void
name|componentOpening
parameter_list|()
block|{  	}
DECL|method|getMinimumSize ()
specifier|public
name|Dimension
name|getMinimumSize
parameter_list|()
block|{
return|return
name|getPreferredSize
argument_list|()
return|;
block|}
DECL|class|CloseButtonListener
class|class
name|CloseButtonListener
implements|implements
name|ActionListener
block|{
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|hideAway
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

