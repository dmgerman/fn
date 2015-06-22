begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|*
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
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|panel
name|BasePanel
name|panel
decl_stmt|;
DECL|field|sidep
specifier|final
name|SidePane
name|sidep
decl_stmt|;
DECL|field|components
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|SidePaneComponent
argument_list|>
name|components
init|=
operator|new
name|LinkedHashMap
argument_list|<
name|String
argument_list|,
name|SidePaneComponent
argument_list|>
argument_list|()
decl_stmt|;
DECL|field|componentNames
specifier|final
name|Map
argument_list|<
name|SidePaneComponent
argument_list|,
name|String
argument_list|>
name|componentNames
init|=
operator|new
name|HashMap
argument_list|<
name|SidePaneComponent
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
DECL|field|visible
specifier|final
name|List
argument_list|<
name|SidePaneComponent
argument_list|>
name|visible
init|=
operator|new
name|LinkedList
argument_list|<
name|SidePaneComponent
argument_list|>
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
comment|/*          * Change by Morten Alver 2005.12.04: By postponing the updating of the          * side pane components, we get rid of the annoying latency when          * switching tabs:          */
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
name|componentNames
operator|.
name|put
argument_list|(
name|comp
argument_list|,
name|name
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
comment|// Sort the visible components by their preferred position
name|Collections
operator|.
name|sort
argument_list|(
name|visible
argument_list|,
operator|new
name|PreferredIndexSort
argument_list|()
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
DECL|method|getComponent (String name)
specifier|public
name|SidePaneComponent
name|getComponent
parameter_list|(
name|String
name|name
parameter_list|)
block|{
return|return
name|components
operator|.
name|get
argument_list|(
name|name
argument_list|)
return|;
block|}
DECL|method|getComponentName (SidePaneComponent comp)
specifier|public
name|String
name|getComponentName
parameter_list|(
name|SidePaneComponent
name|comp
parameter_list|)
block|{
return|return
name|componentNames
operator|.
name|get
argument_list|(
name|comp
argument_list|)
return|;
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
DECL|method|getPreferredPositions ()
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|Integer
argument_list|>
name|getPreferredPositions
parameter_list|()
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|Integer
argument_list|>
name|preferredPositions
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Integer
argument_list|>
argument_list|()
decl_stmt|;
name|String
index|[]
name|componentNames
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getStringArray
argument_list|(
literal|"sidePaneComponentNames"
argument_list|)
decl_stmt|;
name|String
index|[]
name|componentPositions
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getStringArray
argument_list|(
literal|"sidePaneComponentPreferredPositions"
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|componentNames
operator|.
name|length
condition|;
operator|++
name|i
control|)
block|{
try|try
block|{
name|preferredPositions
operator|.
name|put
argument_list|(
name|componentNames
index|[
name|i
index|]
argument_list|,
name|Integer
operator|.
name|parseInt
argument_list|(
name|componentPositions
index|[
name|i
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
comment|// Invalid integer format, ignore
block|}
block|}
return|return
name|preferredPositions
return|;
block|}
DECL|method|updatePreferredPositions ()
specifier|private
name|void
name|updatePreferredPositions
parameter_list|()
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|Integer
argument_list|>
name|preferredPositions
init|=
name|getPreferredPositions
argument_list|()
decl_stmt|;
comment|// Update the preferred positions of all visible components
name|int
name|index
init|=
literal|0
decl_stmt|;
for|for
control|(
name|SidePaneComponent
name|comp
range|:
name|visible
control|)
block|{
name|String
name|componentName
init|=
name|getComponentName
argument_list|(
name|comp
argument_list|)
decl_stmt|;
name|preferredPositions
operator|.
name|put
argument_list|(
name|componentName
argument_list|,
name|index
operator|++
argument_list|)
expr_stmt|;
block|}
comment|// Split the map into a pair of parallel String arrays suitable for storage
name|String
index|[]
name|componentNames
init|=
name|preferredPositions
operator|.
name|keySet
argument_list|()
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
literal|0
index|]
argument_list|)
decl_stmt|;
name|String
index|[]
name|componentPositions
init|=
operator|new
name|String
index|[
name|preferredPositions
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|componentNames
operator|.
name|length
condition|;
operator|++
name|i
control|)
block|{
name|componentPositions
index|[
name|i
index|]
operator|=
name|preferredPositions
operator|.
name|get
argument_list|(
name|componentNames
index|[
name|i
index|]
argument_list|)
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
name|Globals
operator|.
name|prefs
operator|.
name|putStringArray
argument_list|(
literal|"sidePaneComponentNames"
argument_list|,
name|componentNames
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putStringArray
argument_list|(
literal|"sidePaneComponentPreferredPositions"
argument_list|,
name|componentPositions
argument_list|)
expr_stmt|;
block|}
comment|// Helper class for sorting visible componenys based on their preferred position
DECL|class|PreferredIndexSort
specifier|private
class|class
name|PreferredIndexSort
implements|implements
name|Comparator
argument_list|<
name|SidePaneComponent
argument_list|>
block|{
DECL|field|preferredPositions
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|Integer
argument_list|>
name|preferredPositions
decl_stmt|;
DECL|method|PreferredIndexSort ()
specifier|public
name|PreferredIndexSort
parameter_list|()
block|{
name|preferredPositions
operator|=
name|getPreferredPositions
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|compare (SidePaneComponent comp1, SidePaneComponent comp2)
specifier|public
name|int
name|compare
parameter_list|(
name|SidePaneComponent
name|comp1
parameter_list|,
name|SidePaneComponent
name|comp2
parameter_list|)
block|{
name|String
name|comp1Name
init|=
name|getComponentName
argument_list|(
name|comp1
argument_list|)
decl_stmt|;
name|String
name|comp2Name
init|=
name|getComponentName
argument_list|(
name|comp2
argument_list|)
decl_stmt|;
comment|// Manually provide default values, since getOrDefault() doesn't exist prior to Java 8
name|int
name|pos1
init|=
operator|(
name|preferredPositions
operator|.
name|containsKey
argument_list|(
name|comp1Name
argument_list|)
condition|?
name|preferredPositions
operator|.
name|get
argument_list|(
name|comp1Name
argument_list|)
else|:
literal|0
operator|)
decl_stmt|;
name|int
name|pos2
init|=
operator|(
name|preferredPositions
operator|.
name|containsKey
argument_list|(
name|comp2Name
argument_list|)
condition|?
name|preferredPositions
operator|.
name|get
argument_list|(
name|comp2Name
argument_list|)
else|:
literal|0
operator|)
decl_stmt|;
return|return
name|Integer
operator|.
name|valueOf
argument_list|(
name|pos1
argument_list|)
operator|.
name|compareTo
argument_list|(
name|pos2
argument_list|)
return|;
block|}
block|}
DECL|method|moveUp (SidePaneComponent comp)
specifier|public
specifier|synchronized
name|void
name|moveUp
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
name|int
name|currIndex
init|=
name|visible
operator|.
name|indexOf
argument_list|(
name|comp
argument_list|)
decl_stmt|;
if|if
condition|(
name|currIndex
operator|>
literal|0
condition|)
block|{
name|int
name|newIndex
init|=
name|currIndex
operator|-
literal|1
decl_stmt|;
name|visible
operator|.
name|remove
argument_list|(
name|currIndex
argument_list|)
expr_stmt|;
name|visible
operator|.
name|add
argument_list|(
name|newIndex
argument_list|,
name|comp
argument_list|)
expr_stmt|;
name|updatePreferredPositions
argument_list|()
expr_stmt|;
name|updateView
argument_list|()
expr_stmt|;
block|}
block|}
block|}
DECL|method|moveDown (SidePaneComponent comp)
specifier|public
specifier|synchronized
name|void
name|moveDown
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
name|int
name|currIndex
init|=
name|visible
operator|.
name|indexOf
argument_list|(
name|comp
argument_list|)
decl_stmt|;
if|if
condition|(
name|currIndex
operator|<
operator|(
name|visible
operator|.
name|size
argument_list|()
operator|-
literal|1
operator|)
condition|)
block|{
name|int
name|newIndex
init|=
name|currIndex
operator|+
literal|1
decl_stmt|;
name|visible
operator|.
name|remove
argument_list|(
name|currIndex
argument_list|)
expr_stmt|;
name|visible
operator|.
name|add
argument_list|(
name|newIndex
argument_list|,
name|comp
argument_list|)
expr_stmt|;
name|updatePreferredPositions
argument_list|()
expr_stmt|;
name|updateView
argument_list|()
expr_stmt|;
block|}
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
name|componentNames
operator|.
name|remove
argument_list|(
name|components
operator|.
name|get
argument_list|(
name|name
argument_list|)
argument_list|)
expr_stmt|;
name|components
operator|.
name|remove
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
comment|/**      * Update all side pane components to show information from the given      * BasePanel.      *       * @param panel      */
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
name|String
name|key
range|:
name|components
operator|.
name|keySet
argument_list|()
control|)
block|{
name|components
operator|.
name|get
argument_list|(
name|key
argument_list|)
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
name|int
name|width
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getInt
argument_list|(
literal|"sidePaneWidth"
argument_list|)
decl_stmt|;
if|if
condition|(
name|width
operator|>
literal|0
condition|)
name|frame
operator|.
name|contentPane
operator|.
name|setDividerLocation
argument_list|(
name|width
argument_list|)
expr_stmt|;
else|else
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
block|{
if|if
condition|(
name|sidep
operator|.
name|isVisible
argument_list|()
condition|)
name|Globals
operator|.
name|prefs
operator|.
name|putInt
argument_list|(
literal|"sidePaneWidth"
argument_list|,
name|frame
operator|.
name|contentPane
operator|.
name|getDividerLocation
argument_list|()
argument_list|)
expr_stmt|;
name|sidep
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
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

