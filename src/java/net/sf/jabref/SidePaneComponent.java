begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003  Nizar N. Batada, Morten O. Alver  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
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
name|javax
operator|.
name|swing
operator|.
name|*
import|;
end_import

begin_class
DECL|class|SidePaneComponent
specifier|public
class|class
name|SidePaneComponent
extends|extends
name|JPanel
block|{
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
DECL|method|SidePaneComponent (SidePaneManager manager)
specifier|public
name|SidePaneComponent
parameter_list|(
name|SidePaneManager
name|manager
parameter_list|)
block|{
name|this
operator|.
name|manager
operator|=
name|manager
expr_stmt|;
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEtchedBorder
argument_list|()
argument_list|)
expr_stmt|;
comment|//setPreferredSize(new java.awt.Dimension
comment|//		  (GUIGlobals.SPLIT_PANE_DIVIDER_LOCATION, 200));
comment|//Util.pr(""+GUIGlobals.SPLIT_PANE_DIVIDER_LOCATION);
block|}
DECL|method|hideAway ()
specifier|public
name|void
name|hideAway
parameter_list|()
block|{
name|manager
operator|.
name|hideAway
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
comment|/**      * Used by SidePaneManager only, to keep track of visibility.      *      */
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
comment|/**      * Used by SidePaneManager only, to keep track of visibility.      *      */
DECL|method|hasVisibility ()
name|boolean
name|hasVisibility
parameter_list|()
block|{
return|return
name|visible
return|;
block|}
comment|/**      * Override this method if the component needs to make any changes      * before it can close.      */
DECL|method|componentClosing ()
specifier|public
name|void
name|componentClosing
parameter_list|()
block|{      }
comment|/**      * Override this method if the component needs to do any actions      * when opening.      */
DECL|method|componentOpening ()
specifier|public
name|void
name|componentOpening
parameter_list|()
block|{      }
block|}
end_class

end_unit

