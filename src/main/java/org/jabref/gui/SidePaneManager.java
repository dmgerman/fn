begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui
package|package
name|org
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
name|util
operator|.
name|Comparator
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
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Stream
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|Globals
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|groups
operator|.
name|GroupSidePane
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|importer
operator|.
name|fetcher
operator|.
name|WebSearchPane
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|openoffice
operator|.
name|OpenOfficeSidePanel
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|openoffice
operator|.
name|OpenOfficePreferences
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_comment
comment|/**  * Manages which {@link SidePaneComponent}s are shown.  */
end_comment

begin_class
DECL|class|SidePaneManager
specifier|public
class|class
name|SidePaneManager
block|{
DECL|field|sidePane
specifier|private
specifier|final
name|SidePane
name|sidePane
decl_stmt|;
DECL|field|components
specifier|private
specifier|final
name|Map
argument_list|<
name|SidePaneType
argument_list|,
name|SidePaneComponent
argument_list|>
name|components
init|=
operator|new
name|LinkedHashMap
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|visibleComponents
specifier|private
specifier|final
name|List
argument_list|<
name|SidePaneComponent
argument_list|>
name|visibleComponents
init|=
operator|new
name|LinkedList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|preferences
specifier|private
specifier|final
name|JabRefPreferences
name|preferences
decl_stmt|;
DECL|method|SidePaneManager (JabRefPreferences preferences, JabRefFrame frame)
specifier|public
name|SidePaneManager
parameter_list|(
name|JabRefPreferences
name|preferences
parameter_list|,
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|this
operator|.
name|preferences
operator|=
name|preferences
expr_stmt|;
name|this
operator|.
name|sidePane
operator|=
operator|new
name|SidePane
argument_list|()
expr_stmt|;
name|OpenOfficePreferences
name|openOfficePreferences
init|=
name|preferences
operator|.
name|getOpenOfficePreferences
argument_list|()
decl_stmt|;
name|Stream
operator|.
name|of
argument_list|(
operator|new
name|GroupSidePane
argument_list|(
name|this
argument_list|,
name|preferences
argument_list|,
name|frame
operator|.
name|getDialogService
argument_list|()
argument_list|)
argument_list|,
operator|new
name|WebSearchPane
argument_list|(
name|this
argument_list|,
name|preferences
argument_list|,
name|frame
argument_list|)
argument_list|,
operator|new
name|OpenOfficeSidePanel
argument_list|(
name|this
argument_list|,
name|preferences
argument_list|,
name|frame
argument_list|)
argument_list|)
operator|.
name|forEach
argument_list|(
name|pane
lambda|->
name|components
operator|.
name|put
argument_list|(
name|pane
operator|.
name|getType
argument_list|()
argument_list|,
name|pane
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|GROUP_SIDEPANE_VISIBLE
argument_list|)
condition|)
block|{
name|show
argument_list|(
name|SidePaneType
operator|.
name|GROUPS
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|openOfficePreferences
operator|.
name|getShowPanel
argument_list|()
condition|)
block|{
name|show
argument_list|(
name|SidePaneType
operator|.
name|OPEN_OFFICE
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|WEB_SEARCH_VISIBLE
argument_list|)
condition|)
block|{
name|show
argument_list|(
name|SidePaneType
operator|.
name|WEB_SEARCH
argument_list|)
expr_stmt|;
block|}
name|updateView
argument_list|()
expr_stmt|;
block|}
DECL|method|getPane ()
specifier|public
name|SidePane
name|getPane
parameter_list|()
block|{
return|return
name|sidePane
return|;
block|}
DECL|method|isComponentVisible (SidePaneType type)
specifier|public
name|boolean
name|isComponentVisible
parameter_list|(
name|SidePaneType
name|type
parameter_list|)
block|{
return|return
name|visibleComponents
operator|.
name|contains
argument_list|(
name|getComponent
argument_list|(
name|type
argument_list|)
argument_list|)
return|;
block|}
DECL|method|getComponent (SidePaneType type)
specifier|public
name|SidePaneComponent
name|getComponent
parameter_list|(
name|SidePaneType
name|type
parameter_list|)
block|{
name|SidePaneComponent
name|component
init|=
name|components
operator|.
name|get
argument_list|(
name|type
argument_list|)
decl_stmt|;
if|if
condition|(
name|component
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Side component "
operator|+
name|type
operator|+
literal|" not registered."
argument_list|)
throw|;
block|}
else|else
block|{
return|return
name|component
return|;
block|}
block|}
comment|/**      * If the given component is visible it will be hidden and the other way around.      */
DECL|method|toggle (SidePaneType type)
specifier|public
name|void
name|toggle
parameter_list|(
name|SidePaneType
name|type
parameter_list|)
block|{
if|if
condition|(
name|isComponentVisible
argument_list|(
name|type
argument_list|)
condition|)
block|{
name|hide
argument_list|(
name|type
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|show
argument_list|(
name|type
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Makes sure that the given component is visible.      */
DECL|method|show (SidePaneType type)
specifier|public
name|void
name|show
parameter_list|(
name|SidePaneType
name|type
parameter_list|)
block|{
name|SidePaneComponent
name|component
init|=
name|getComponent
argument_list|(
name|type
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|visibleComponents
operator|.
name|contains
argument_list|(
name|component
argument_list|)
condition|)
block|{
comment|// Add the new component
name|visibleComponents
operator|.
name|add
argument_list|(
name|component
argument_list|)
expr_stmt|;
comment|// Sort the visible components by their preferred position
name|visibleComponents
operator|.
name|sort
argument_list|(
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
name|afterOpening
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      * Makes sure that the given component is not visible.      */
DECL|method|hide (SidePaneType type)
specifier|public
name|void
name|hide
parameter_list|(
name|SidePaneType
name|type
parameter_list|)
block|{
name|SidePaneComponent
name|component
init|=
name|getComponent
argument_list|(
name|type
argument_list|)
decl_stmt|;
if|if
condition|(
name|visibleComponents
operator|.
name|contains
argument_list|(
name|component
argument_list|)
condition|)
block|{
name|component
operator|.
name|beforeClosing
argument_list|()
expr_stmt|;
name|visibleComponents
operator|.
name|remove
argument_list|(
name|component
argument_list|)
expr_stmt|;
name|updateView
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      * Stores the current configuration of visible components in the preferences,      * so that we show components at the preferred position next time.      */
DECL|method|updatePreferredPositions ()
specifier|private
name|void
name|updatePreferredPositions
parameter_list|()
block|{
name|Map
argument_list|<
name|SidePaneType
argument_list|,
name|Integer
argument_list|>
name|preferredPositions
init|=
name|preferences
operator|.
name|getSidePanePreferredPositions
argument_list|()
decl_stmt|;
comment|// Use the currently shown positions of all visible components
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
name|visibleComponents
control|)
block|{
name|preferredPositions
operator|.
name|put
argument_list|(
name|comp
operator|.
name|getType
argument_list|()
argument_list|,
name|index
argument_list|)
expr_stmt|;
name|index
operator|++
expr_stmt|;
block|}
name|preferences
operator|.
name|storeSidePanePreferredPositions
argument_list|(
name|preferredPositions
argument_list|)
expr_stmt|;
block|}
comment|/**      * Moves the given component up.      */
DECL|method|moveUp (SidePaneComponent component)
specifier|public
name|void
name|moveUp
parameter_list|(
name|SidePaneComponent
name|component
parameter_list|)
block|{
if|if
condition|(
name|visibleComponents
operator|.
name|contains
argument_list|(
name|component
argument_list|)
condition|)
block|{
name|int
name|currentPosition
init|=
name|visibleComponents
operator|.
name|indexOf
argument_list|(
name|component
argument_list|)
decl_stmt|;
if|if
condition|(
name|currentPosition
operator|>
literal|0
condition|)
block|{
name|int
name|newPosition
init|=
name|currentPosition
operator|-
literal|1
decl_stmt|;
name|visibleComponents
operator|.
name|remove
argument_list|(
name|currentPosition
argument_list|)
expr_stmt|;
name|visibleComponents
operator|.
name|add
argument_list|(
name|newPosition
argument_list|,
name|component
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
comment|/**      * Moves the given component down.      */
DECL|method|moveDown (SidePaneComponent comp)
specifier|public
name|void
name|moveDown
parameter_list|(
name|SidePaneComponent
name|comp
parameter_list|)
block|{
if|if
condition|(
name|visibleComponents
operator|.
name|contains
argument_list|(
name|comp
argument_list|)
condition|)
block|{
name|int
name|currentPosition
init|=
name|visibleComponents
operator|.
name|indexOf
argument_list|(
name|comp
argument_list|)
decl_stmt|;
if|if
condition|(
name|currentPosition
operator|<
operator|(
name|visibleComponents
operator|.
name|size
argument_list|()
operator|-
literal|1
operator|)
condition|)
block|{
name|int
name|newPosition
init|=
name|currentPosition
operator|+
literal|1
decl_stmt|;
name|visibleComponents
operator|.
name|remove
argument_list|(
name|currentPosition
argument_list|)
expr_stmt|;
name|visibleComponents
operator|.
name|add
argument_list|(
name|newPosition
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
comment|/**      * Updates the view to reflect changes to visible components.      */
DECL|method|updateView ()
specifier|private
name|void
name|updateView
parameter_list|()
block|{
name|sidePane
operator|.
name|setComponents
argument_list|(
name|visibleComponents
argument_list|)
expr_stmt|;
if|if
condition|(
name|visibleComponents
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|sidePane
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sidePane
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Helper class for sorting visible components based on their preferred position.      */
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
name|SidePaneType
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
name|Globals
operator|.
name|prefs
operator|.
name|getSidePanePreferredPositions
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
name|int
name|pos1
init|=
name|preferredPositions
operator|.
name|getOrDefault
argument_list|(
name|comp1
operator|.
name|getType
argument_list|()
argument_list|,
literal|0
argument_list|)
decl_stmt|;
name|int
name|pos2
init|=
name|preferredPositions
operator|.
name|getOrDefault
argument_list|(
name|comp2
operator|.
name|getType
argument_list|()
argument_list|,
literal|0
argument_list|)
decl_stmt|;
return|return
name|Integer
operator|.
name|compare
argument_list|(
name|pos1
argument_list|,
name|pos2
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

