begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|com.incors.plaf.kunststoff
package|package
name|com
operator|.
name|incors
operator|.
name|plaf
operator|.
name|kunststoff
package|;
end_package

begin_comment
comment|/*  * This code was developed by INCORS GmbH (www.incors.com)  * with contributions by Jamie LaScolea.  *  * It is published under the terms of the GNU Lesser General Public License.  */
end_comment

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
name|javax
operator|.
name|swing
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
name|plaf
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
name|plaf
operator|.
name|basic
operator|.
name|*
import|;
end_import

begin_class
DECL|class|KunststoffTabbedPaneUI
specifier|public
class|class
name|KunststoffTabbedPaneUI
extends|extends
name|BasicTabbedPaneUI
block|{
DECL|field|SHADOW_WIDTH
specifier|private
specifier|static
specifier|final
name|int
name|SHADOW_WIDTH
init|=
literal|5
decl_stmt|;
DECL|method|createUI (JComponent c)
specifier|public
specifier|static
name|ComponentUI
name|createUI
parameter_list|(
name|JComponent
name|c
parameter_list|)
block|{
return|return
operator|new
name|KunststoffTabbedPaneUI
argument_list|()
return|;
block|}
DECL|method|installDefaults ()
specifier|protected
name|void
name|installDefaults
parameter_list|()
block|{
name|super
operator|.
name|installDefaults
argument_list|()
expr_stmt|;
block|}
comment|/*    * Thanks to a contribution by Jamie LaScolea this method now works with    * multiple rows of tabs.    */
DECL|method|paintTab (Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect)
specifier|protected
name|void
name|paintTab
parameter_list|(
name|Graphics
name|g
parameter_list|,
name|int
name|tabPlacement
parameter_list|,
name|Rectangle
index|[]
name|rects
parameter_list|,
name|int
name|tabIndex
parameter_list|,
name|Rectangle
name|iconRect
parameter_list|,
name|Rectangle
name|textRect
parameter_list|)
block|{
name|super
operator|.
name|paintTab
argument_list|(
name|g
argument_list|,
name|tabPlacement
argument_list|,
name|rects
argument_list|,
name|tabIndex
argument_list|,
name|iconRect
argument_list|,
name|textRect
argument_list|)
expr_stmt|;
name|Graphics2D
name|g2D
init|=
operator|(
name|Graphics2D
operator|)
name|g
decl_stmt|;
name|Rectangle
name|tabRect
init|=
name|rects
index|[
name|tabIndex
index|]
decl_stmt|;
name|Color
name|colorShadow
init|=
name|KunststoffLookAndFeel
operator|.
name|getComponentGradientColorShadow
argument_list|()
decl_stmt|;
name|Color
name|colorShadowFaded
init|=
name|KunststoffUtilities
operator|.
name|getTranslucentColor
argument_list|(
name|colorShadow
argument_list|,
literal|0
argument_list|)
decl_stmt|;
name|Color
name|colorReflection
init|=
name|KunststoffLookAndFeel
operator|.
name|getComponentGradientColorReflection
argument_list|()
decl_stmt|;
name|Color
name|colorReflectionFaded
init|=
name|KunststoffUtilities
operator|.
name|getTranslucentColor
argument_list|(
name|colorReflection
argument_list|,
literal|0
argument_list|)
decl_stmt|;
comment|// paint shadow that the selected tab throws on the next tab
name|int
name|selectedIndex
init|=
name|tabPane
operator|.
name|getSelectedIndex
argument_list|()
decl_stmt|;
comment|// the following statement was added by Jamie LaScolea as a bug fix. Thanks Jamie!
if|if
condition|(
name|this
operator|.
name|lastTabInRun
argument_list|(
name|tabPane
operator|.
name|getTabCount
argument_list|()
argument_list|,
name|this
operator|.
name|selectedRun
argument_list|)
operator|!=
name|selectedIndex
condition|)
block|{
if|if
condition|(
name|tabPlacement
operator|==
name|JTabbedPane
operator|.
name|TOP
operator|||
name|tabPlacement
operator|==
name|JTabbedPane
operator|.
name|BOTTOM
condition|)
block|{
if|if
condition|(
name|tabIndex
operator|==
name|selectedIndex
operator|+
literal|1
condition|)
block|{
name|Rectangle
name|gradientRect
init|=
operator|new
name|Rectangle
argument_list|(
operator|(
name|int
operator|)
name|tabRect
operator|.
name|getX
argument_list|()
argument_list|,
operator|(
name|int
operator|)
name|tabRect
operator|.
name|getY
argument_list|()
argument_list|,
name|SHADOW_WIDTH
argument_list|,
operator|(
name|int
operator|)
name|tabRect
operator|.
name|getHeight
argument_list|()
argument_list|)
decl_stmt|;
name|KunststoffUtilities
operator|.
name|drawGradient
argument_list|(
name|g
argument_list|,
name|colorShadow
argument_list|,
name|colorShadowFaded
argument_list|,
name|gradientRect
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
if|if
condition|(
name|tabIndex
operator|==
name|selectedIndex
operator|+
literal|1
condition|)
block|{
name|Rectangle
name|gradientRect
init|=
operator|new
name|Rectangle
argument_list|(
operator|(
name|int
operator|)
name|tabRect
operator|.
name|getX
argument_list|()
argument_list|,
operator|(
name|int
operator|)
name|tabRect
operator|.
name|getY
argument_list|()
argument_list|,
operator|(
name|int
operator|)
name|tabRect
operator|.
name|getWidth
argument_list|()
argument_list|,
name|SHADOW_WIDTH
argument_list|)
decl_stmt|;
name|KunststoffUtilities
operator|.
name|drawGradient
argument_list|(
name|g
argument_list|,
name|colorShadow
argument_list|,
name|colorShadowFaded
argument_list|,
name|gradientRect
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|tabPlacement
operator|==
name|JTabbedPane
operator|.
name|TOP
condition|)
block|{
name|Rectangle
name|gradientRect
init|=
operator|new
name|Rectangle
argument_list|(
operator|(
name|int
operator|)
name|tabRect
operator|.
name|getX
argument_list|()
argument_list|,
operator|(
name|int
operator|)
name|tabRect
operator|.
name|getY
argument_list|()
argument_list|,
operator|(
name|int
operator|)
name|tabRect
operator|.
name|getWidth
argument_list|()
argument_list|,
operator|(
name|int
operator|)
name|SHADOW_WIDTH
argument_list|)
decl_stmt|;
name|KunststoffUtilities
operator|.
name|drawGradient
argument_list|(
name|g
argument_list|,
name|colorReflection
argument_list|,
name|colorReflectionFaded
argument_list|,
name|gradientRect
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|tabPlacement
operator|==
name|JTabbedPane
operator|.
name|BOTTOM
condition|)
block|{
if|if
condition|(
name|tabIndex
operator|!=
name|selectedIndex
condition|)
block|{
name|Rectangle
name|gradientRect
init|=
operator|new
name|Rectangle
argument_list|(
operator|(
name|int
operator|)
name|tabRect
operator|.
name|getX
argument_list|()
argument_list|,
operator|(
name|int
operator|)
name|tabRect
operator|.
name|getY
argument_list|()
argument_list|,
operator|(
name|int
operator|)
name|tabRect
operator|.
name|getWidth
argument_list|()
argument_list|,
name|SHADOW_WIDTH
argument_list|)
decl_stmt|;
name|KunststoffUtilities
operator|.
name|drawGradient
argument_list|(
name|g
argument_list|,
name|colorShadow
argument_list|,
name|colorShadowFaded
argument_list|,
name|gradientRect
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|tabPlacement
operator|==
name|JTabbedPane
operator|.
name|RIGHT
condition|)
block|{
if|if
condition|(
name|tabIndex
operator|!=
name|selectedIndex
condition|)
block|{
name|Rectangle
name|gradientRect
init|=
operator|new
name|Rectangle
argument_list|(
operator|(
name|int
operator|)
name|tabRect
operator|.
name|getX
argument_list|()
argument_list|,
operator|(
name|int
operator|)
name|tabRect
operator|.
name|getY
argument_list|()
argument_list|,
operator|(
name|int
operator|)
name|SHADOW_WIDTH
argument_list|,
operator|(
name|int
operator|)
name|tabRect
operator|.
name|getHeight
argument_list|()
argument_list|)
decl_stmt|;
name|KunststoffUtilities
operator|.
name|drawGradient
argument_list|(
name|g
argument_list|,
name|colorShadow
argument_list|,
name|colorShadowFaded
argument_list|,
name|gradientRect
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|paintTabBackground (Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected )
specifier|protected
name|void
name|paintTabBackground
parameter_list|(
name|Graphics
name|g
parameter_list|,
name|int
name|tabPlacement
parameter_list|,
name|int
name|tabIndex
parameter_list|,
name|int
name|x
parameter_list|,
name|int
name|y
parameter_list|,
name|int
name|w
parameter_list|,
name|int
name|h
parameter_list|,
name|boolean
name|isSelected
parameter_list|)
block|{
if|if
condition|(
name|isSelected
condition|)
block|{
name|g
operator|.
name|setColor
argument_list|(
name|UIManager
operator|.
name|getColor
argument_list|(
literal|"TabbedPane.selected"
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|g
operator|.
name|setColor
argument_list|(
name|tabPane
operator|.
name|getBackgroundAt
argument_list|(
name|tabIndex
argument_list|)
argument_list|)
expr_stmt|;
block|}
switch|switch
condition|(
name|tabPlacement
condition|)
block|{
case|case
name|LEFT
case|:
name|g
operator|.
name|fillRect
argument_list|(
name|x
operator|+
literal|1
argument_list|,
name|y
operator|+
literal|1
argument_list|,
name|w
operator|-
literal|2
argument_list|,
name|h
operator|-
literal|3
argument_list|)
expr_stmt|;
break|break;
case|case
name|RIGHT
case|:
name|g
operator|.
name|fillRect
argument_list|(
name|x
argument_list|,
name|y
operator|+
literal|1
argument_list|,
name|w
operator|-
literal|2
argument_list|,
name|h
operator|-
literal|3
argument_list|)
expr_stmt|;
break|break;
case|case
name|BOTTOM
case|:
name|g
operator|.
name|fillRect
argument_list|(
name|x
operator|+
literal|1
argument_list|,
name|y
argument_list|,
name|w
operator|-
literal|3
argument_list|,
name|h
operator|-
literal|1
argument_list|)
expr_stmt|;
break|break;
case|case
name|TOP
case|:
default|default:
name|g
operator|.
name|fillRect
argument_list|(
name|x
operator|+
literal|1
argument_list|,
name|y
operator|+
literal|1
argument_list|,
name|w
operator|-
literal|3
argument_list|,
name|h
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

