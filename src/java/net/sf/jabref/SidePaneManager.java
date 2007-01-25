begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003  Nizar N. Batada, Morten O. Alver   All programs in this directory and  subdirectories are published under the GNU General Public License as  described below.   This program is free software; you can redistribute it and/or modify  it under the terms of the GNU General Public License as published by  the Free Software Foundation; either version 2 of the License, or (at  your option) any later version.   This program is distributed in the hope that it will be useful, but  WITHOUT ANY WARRANTY; without even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU  General Public License for more details.   You should have received a copy of the GNU General Public License  along with this program; if not, write to the Free Software  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA   Further information about the GNU GPL is available at:  http://www.gnu.org/copyleft/gpl.ja.html   */
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
name|LinkedHashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|LinkedList
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
name|SwingUtilities
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

begin_comment
comment|/**  * Manages visibility of SideShowComponents in a given newly constructed  * sidePane.  *   * @version $Revision$ ($Date$)  *   */
end_comment

begin_class
DECL|class|SidePaneManager
specifier|public
class|class
name|SidePaneManager
block|{
DECL|field|frame
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|panel
name|BasePanel
name|panel
decl_stmt|;
DECL|field|sidep
name|SidePane
name|sidep
decl_stmt|;
DECL|field|components
name|Map
name|components
init|=
operator|new
name|LinkedHashMap
argument_list|()
decl_stmt|;
DECL|field|visible
name|List
name|visible
init|=
operator|new
name|LinkedList
argument_list|()
decl_stmt|;
DECL|method|SidePaneManager (JabRefFrame frame)
specifier|public
name|SidePaneManager
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
comment|/* 		 * Change by Morten Alver 2005.12.04: By postponing the updating of the 		 * side pane components, we get rid of the annoying latency when 		 * switching tabs: 		 */
name|frame
operator|.
name|tabbedPane
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
name|event
parameter_list|)
block|{
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
operator|new
name|Runnable
argument_list|()
block|{
specifier|public
name|void
name|run
parameter_list|()
block|{
name|setActiveBasePanel
argument_list|(
operator|(
name|BasePanel
operator|)
name|SidePaneManager
operator|.
name|this
operator|.
name|frame
operator|.
name|tabbedPane
operator|.
name|getSelectedComponent
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|sidep
operator|=
operator|new
name|SidePane
argument_list|()
expr_stmt|;
name|sidep
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
DECL|method|getPanel ()
specifier|public
name|SidePane
name|getPanel
parameter_list|()
block|{
return|return
name|sidep
return|;
block|}
DECL|method|hasComponent (String name)
specifier|public
specifier|synchronized
name|boolean
name|hasComponent
parameter_list|(
name|String
name|name
parameter_list|)
block|{
return|return
operator|(
name|components
operator|.
name|get
argument_list|(
name|name
argument_list|)
operator|!=
literal|null
operator|)
return|;
block|}
DECL|method|isComponentVisible (String name)
specifier|public
name|boolean
name|isComponentVisible
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|Object
name|o
init|=
name|components
operator|.
name|get
argument_list|(
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
name|o
operator|!=
literal|null
condition|)
block|{
return|return
name|visible
operator|.
name|contains
argument_list|(
name|o
argument_list|)
return|;
block|}
else|else
block|{
return|return
literal|false
return|;
block|}
block|}
DECL|method|toggle (String name)
specifier|public
specifier|synchronized
name|void
name|toggle
parameter_list|(
name|String
name|name
parameter_list|)
block|{
if|if
condition|(
name|isComponentVisible
argument_list|(
name|name
argument_list|)
condition|)
block|{
name|hide
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|show
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|show (String name)
specifier|public
name|void
name|show
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|Object
name|o
init|=
name|components
operator|.
name|get
argument_list|(
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
name|o
operator|!=
literal|null
condition|)
block|{
name|show
argument_list|(
operator|(
name|SidePaneComponent
operator|)
name|o
argument_list|)
expr_stmt|;
block|}
else|else
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Side pane component '"
operator|+
name|name
operator|+
literal|"' unknown."
argument_list|)
expr_stmt|;
block|}
DECL|method|hide (String name)
specifier|public
name|void
name|hide
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|Object
name|o
init|=
name|components
operator|.
name|get
argument_list|(
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
name|o
operator|!=
literal|null
condition|)
block|{
name|hideComponent
argument_list|(
operator|(
name|SidePaneComponent
operator|)
name|o
argument_list|)
expr_stmt|;
block|}
else|else
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Side pane component '"
operator|+
name|name
operator|+
literal|"' unknown."
argument_list|)
expr_stmt|;
block|}
DECL|method|register (String name, SidePaneComponent comp)
specifier|public
specifier|synchronized
name|void
name|register
parameter_list|(
name|String
name|name
parameter_list|,
name|SidePaneComponent
name|comp
parameter_list|)
block|{
name|components
operator|.
name|put
argument_list|(
name|name
argument_list|,
name|comp
argument_list|)
expr_stmt|;
block|}
DECL|method|registerAndShow (String name, SidePaneComponent comp)
specifier|public
specifier|synchronized
name|void
name|registerAndShow
parameter_list|(
name|String
name|name
parameter_list|,
name|SidePaneComponent
name|comp
parameter_list|)
block|{
name|register
argument_list|(
name|name
argument_list|,
name|comp
argument_list|)
expr_stmt|;
name|show
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
DECL|method|show (SidePaneComponent component)
specifier|private
specifier|synchronized
name|void
name|show
parameter_list|(
name|SidePaneComponent
name|component
parameter_list|)
block|{
if|if
condition|(
operator|!
name|visible
operator|.
name|contains
argument_list|(
name|component
argument_list|)
condition|)
block|{
comment|// Put the new component at the top of the group
name|visible
operator|.
name|add
argument_list|(
literal|0
argument_list|,
name|component
argument_list|)
expr_stmt|;
name|updateView
argument_list|()
expr_stmt|;
name|component
operator|.
name|componentOpening
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|hideComponent (SidePaneComponent comp)
specifier|public
specifier|synchronized
name|void
name|hideComponent
parameter_list|(
name|SidePaneComponent
name|comp
parameter_list|)
block|{
if|if
condition|(
name|visible
operator|.
name|contains
argument_list|(
name|comp
argument_list|)
condition|)
block|{
name|comp
operator|.
name|componentClosing
argument_list|()
expr_stmt|;
name|visible
operator|.
name|remove
argument_list|(
name|comp
argument_list|)
expr_stmt|;
name|updateView
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|hideComponent (String name)
specifier|public
specifier|synchronized
name|void
name|hideComponent
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|SidePaneComponent
name|comp
init|=
operator|(
name|SidePaneComponent
operator|)
name|components
operator|.
name|get
argument_list|(
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
name|comp
operator|==
literal|null
condition|)
return|return;
if|if
condition|(
name|visible
operator|.
name|contains
argument_list|(
name|comp
argument_list|)
condition|)
block|{
name|comp
operator|.
name|componentClosing
argument_list|()
expr_stmt|;
name|visible
operator|.
name|remove
argument_list|(
name|comp
argument_list|)
expr_stmt|;
name|updateView
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|unregisterComponent (String name)
specifier|public
specifier|synchronized
name|void
name|unregisterComponent
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|components
operator|.
name|remove
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Update all side pane components to show information from the given 	 * BasePanel. 	 *  	 * @param panel 	 */
DECL|method|setActiveBasePanel (BasePanel panel)
specifier|public
name|void
name|setActiveBasePanel
parameter_list|(
name|BasePanel
name|panel
parameter_list|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|components
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Object
name|key
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
operator|(
operator|(
name|SidePaneComponent
operator|)
name|components
operator|.
name|get
argument_list|(
name|key
argument_list|)
operator|)
operator|.
name|setActiveBasePanel
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|updateView ()
specifier|public
name|void
name|updateView
parameter_list|()
block|{
name|sidep
operator|.
name|setComponents
argument_list|(
name|visible
argument_list|)
expr_stmt|;
if|if
condition|(
name|visible
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
name|boolean
name|wasVisible
init|=
name|sidep
operator|.
name|isVisible
argument_list|()
decl_stmt|;
name|sidep
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|wasVisible
condition|)
block|{
name|frame
operator|.
name|contentPane
operator|.
name|setDividerLocation
argument_list|(
name|getPanel
argument_list|()
operator|.
name|getPreferredSize
argument_list|()
operator|.
name|width
argument_list|)
expr_stmt|;
block|}
block|}
else|else
name|sidep
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
DECL|method|revalidate ()
specifier|public
name|void
name|revalidate
parameter_list|()
block|{
name|sidep
operator|.
name|revalidate
argument_list|()
expr_stmt|;
name|sidep
operator|.
name|repaint
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

