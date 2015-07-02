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
name|AlphaComposite
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
name|Graphics
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Graphics2D
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Image
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Point
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Rectangle
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Toolkit
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
name|MouseAdapter
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
name|MouseEvent
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
name|MouseMotionAdapter
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JPanel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JTabbedPane
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|GUIGlobals
import|;
end_import

begin_comment
comment|/**  * Extends the JTabbedPane class to support Drag&Drop of Tabs.  *   * @author kleinms, strassfn  */
end_comment

begin_class
DECL|class|DragDropPane
class|class
name|DragDropPane
extends|extends
name|JTabbedPane
block|{
DECL|field|draggingState
specifier|private
name|boolean
name|draggingState
init|=
literal|false
decl_stmt|;
comment|// State var if we are at dragging or not
DECL|field|indexDraggedTab
specifier|private
name|int
name|indexDraggedTab
decl_stmt|;
comment|// The index of the tab we drag at the moment
DECL|field|markerPane
specifier|private
specifier|final
name|MarkerPane
name|markerPane
decl_stmt|;
comment|// The glass panel for painting the position marker
DECL|method|DragDropPane ()
name|DragDropPane
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
name|indexDraggedTab
operator|=
operator|-
literal|1
expr_stmt|;
name|markerPane
operator|=
operator|new
name|MarkerPane
argument_list|()
expr_stmt|;
name|markerPane
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
comment|// -------------------------------------------
comment|// Adding listeners for Drag&Drop Actions
comment|// -------------------------------------------
name|addMouseMotionListener
argument_list|(
operator|new
name|MouseMotionAdapter
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|mouseDragged
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{
comment|// Mouse is dragging
comment|// Calculates the tab index based on the mouse position
name|int
name|indexActTab
init|=
name|getUI
argument_list|()
operator|.
name|tabForCoordinate
argument_list|(
name|DragDropPane
operator|.
name|this
argument_list|,
name|e
operator|.
name|getX
argument_list|()
argument_list|,
name|e
operator|.
name|getY
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|draggingState
condition|)
block|{
comment|// We are not at tab dragging
if|if
condition|(
name|indexActTab
operator|>=
literal|0
condition|)
block|{
comment|// Mouse is above a tab, otherwise tabNumber would be -1
comment|// -->Now we are at tab tragging
name|draggingState
operator|=
literal|true
expr_stmt|;
comment|// Mark now we are at dragging
name|indexDraggedTab
operator|=
name|indexActTab
expr_stmt|;
comment|// Set draggedTabIndex to the tabNumber where we are now
name|repaint
argument_list|()
expr_stmt|;
block|}
block|}
else|else
block|{
comment|//We are at tab tragging
if|if
condition|(
operator|(
name|indexDraggedTab
operator|>=
literal|0
operator|)
operator|&&
operator|(
name|indexActTab
operator|>=
literal|0
operator|)
condition|)
block|{
comment|//Is it a valid scenario?
name|boolean
name|toTheLeft
init|=
name|e
operator|.
name|getX
argument_list|()
operator|<=
name|getUI
argument_list|()
operator|.
name|getTabBounds
argument_list|(
name|DragDropPane
operator|.
name|this
argument_list|,
name|indexActTab
argument_list|)
operator|.
name|getCenterX
argument_list|()
decl_stmt|;
comment|//Go to the left or to the right of the actual Tab
name|DragDropPane
operator|.
name|this
operator|.
name|getRootPane
argument_list|()
operator|.
name|setGlassPane
argument_list|(
name|markerPane
argument_list|)
expr_stmt|;
comment|//Set the MarkerPane as glass Pane
name|Rectangle
name|actTabRect
init|=
name|SwingUtilities
operator|.
name|convertRectangle
argument_list|(
name|DragDropPane
operator|.
name|this
argument_list|,
name|getBoundsAt
argument_list|(
name|indexActTab
argument_list|)
argument_list|,
name|DragDropPane
operator|.
name|this
operator|.
name|markerPane
argument_list|)
decl_stmt|;
comment|//Rectangle with the same dimensions as the tab at the mouse position
if|if
condition|(
name|toTheLeft
condition|)
block|{
name|markerPane
operator|.
name|setPicLocation
argument_list|(
operator|new
name|Point
argument_list|(
name|actTabRect
operator|.
name|x
argument_list|,
name|actTabRect
operator|.
name|y
operator|+
name|actTabRect
operator|.
name|height
argument_list|)
argument_list|)
expr_stmt|;
comment|//Set pic to the left of the tab at the mouse position
block|}
else|else
block|{
name|markerPane
operator|.
name|setPicLocation
argument_list|(
operator|new
name|Point
argument_list|(
name|actTabRect
operator|.
name|x
operator|+
name|actTabRect
operator|.
name|width
argument_list|,
name|actTabRect
operator|.
name|y
operator|+
name|actTabRect
operator|.
name|height
argument_list|)
argument_list|)
expr_stmt|;
comment|//Set pic to the right of the tab at the mouse position
block|}
name|markerPane
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|markerPane
operator|.
name|repaint
argument_list|()
expr_stmt|;
name|repaint
argument_list|()
expr_stmt|;
block|}
else|else
block|{
comment|//We have no valid tab tragging scenario
name|markerPane
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|markerPane
operator|.
name|repaint
argument_list|()
expr_stmt|;
block|}
block|}
name|super
operator|.
name|mouseDragged
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|addMouseListener
argument_list|(
operator|new
name|MouseAdapter
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|mouseReleased
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{
name|DragDropPane
operator|.
name|this
operator|.
name|markerPane
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
comment|//Set MarkerPane invisible
name|int
name|indexActTab
init|=
name|getUI
argument_list|()
operator|.
name|tabForCoordinate
argument_list|(
name|DragDropPane
operator|.
name|this
argument_list|,
name|e
operator|.
name|getX
argument_list|()
argument_list|,
name|e
operator|.
name|getY
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|indexDraggedTab
operator|>=
literal|0
operator|)
operator|&&
operator|(
name|indexActTab
operator|>=
literal|0
operator|)
operator|&&
operator|(
name|indexDraggedTab
operator|!=
name|indexActTab
operator|)
condition|)
block|{
comment|//Is it a valid scenario?
if|if
condition|(
name|draggingState
condition|)
block|{
comment|//We are at tab tragging
name|boolean
name|toTheLeft
init|=
name|e
operator|.
name|getX
argument_list|()
operator|<=
name|getUI
argument_list|()
operator|.
name|getTabBounds
argument_list|(
name|DragDropPane
operator|.
name|this
argument_list|,
name|indexActTab
argument_list|)
operator|.
name|getCenterX
argument_list|()
decl_stmt|;
comment|//Go to the left or to the right of the actual Tab
name|DragDropPane
operator|.
name|this
operator|.
name|markerPane
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|Component
name|actTab
init|=
name|getComponentAt
argument_list|(
name|indexDraggedTab
argument_list|)
decl_stmt|;
comment|//Save dragged tab
name|String
name|actTabTitle
init|=
name|getTitleAt
argument_list|(
name|indexDraggedTab
argument_list|)
decl_stmt|;
comment|//Save Title of the dragged tab
name|removeTabAt
argument_list|(
name|indexDraggedTab
argument_list|)
expr_stmt|;
comment|//Remove dragged tab
name|int
name|newTabPos
decl_stmt|;
if|if
condition|(
name|indexActTab
operator|<
name|indexDraggedTab
condition|)
block|{
comment|//We are dragging the tab to the left of its the position
if|if
condition|(
name|toTheLeft
operator|&&
operator|(
name|indexActTab
operator|<
operator|(
name|DragDropPane
operator|.
name|this
operator|.
name|getTabCount
argument_list|()
operator|)
operator|)
condition|)
block|{
name|newTabPos
operator|=
name|indexActTab
expr_stmt|;
block|}
else|else
block|{
name|newTabPos
operator|=
name|indexActTab
operator|+
literal|1
expr_stmt|;
block|}
block|}
else|else
block|{
comment|//We are dragging the tab to the right of the old position
if|if
condition|(
name|toTheLeft
operator|&&
operator|(
name|indexActTab
operator|>
literal|0
operator|)
condition|)
block|{
name|newTabPos
operator|=
name|indexActTab
operator|-
literal|1
expr_stmt|;
block|}
else|else
block|{
name|newTabPos
operator|=
name|indexActTab
expr_stmt|;
block|}
block|}
name|insertTab
argument_list|(
name|actTabTitle
argument_list|,
literal|null
argument_list|,
name|actTab
argument_list|,
literal|null
argument_list|,
name|newTabPos
argument_list|)
expr_stmt|;
comment|//Insert dragged tab at new position
name|DragDropPane
operator|.
name|this
operator|.
name|setSelectedIndex
argument_list|(
name|newTabPos
argument_list|)
expr_stmt|;
comment|//Set selection back to the tab (at the new tab position
block|}
block|}
name|draggingState
operator|=
literal|false
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
comment|/**      * A glass panel which sets the marker for Dragging of Tabs.      *       */
DECL|class|MarkerPane
class|class
name|MarkerPane
extends|extends
name|JPanel
block|{
DECL|field|locationP
specifier|private
name|Point
name|locationP
decl_stmt|;
DECL|field|markerImg
specifier|private
specifier|final
name|Image
name|markerImg
decl_stmt|;
DECL|method|MarkerPane ()
specifier|public
name|MarkerPane
parameter_list|()
block|{
name|setOpaque
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|markerImg
operator|=
name|Toolkit
operator|.
name|getDefaultToolkit
argument_list|()
operator|.
name|getImage
argument_list|(
name|GUIGlobals
operator|.
name|getIconUrl
argument_list|(
literal|"dragNdropArrow"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Sets the marker image
block|}
annotation|@
name|Override
DECL|method|paintComponent (Graphics g)
specifier|public
name|void
name|paintComponent
parameter_list|(
name|Graphics
name|g
parameter_list|)
block|{
operator|(
operator|(
name|Graphics2D
operator|)
name|g
operator|)
operator|.
name|setComposite
argument_list|(
name|AlphaComposite
operator|.
name|getInstance
argument_list|(
name|AlphaComposite
operator|.
name|SRC_OVER
argument_list|,
literal|0.9f
argument_list|)
argument_list|)
expr_stmt|;
comment|// Set transparency
name|g
operator|.
name|drawImage
argument_list|(
name|markerImg
argument_list|,
name|locationP
operator|.
name|x
operator|-
operator|(
name|markerImg
operator|.
name|getWidth
argument_list|(
literal|null
argument_list|)
operator|/
literal|2
operator|)
argument_list|,
name|locationP
operator|.
name|y
argument_list|,
literal|null
argument_list|)
expr_stmt|;
comment|// draw the image at the middle of the given location
block|}
comment|/**          * Sets the new location, where the marker should be placed.          *           * @param pt the point for the marker          */
DECL|method|setPicLocation (Point pt)
specifier|public
name|void
name|setPicLocation
parameter_list|(
name|Point
name|pt
parameter_list|)
block|{
name|this
operator|.
name|locationP
operator|=
name|pt
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

