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
comment|/*  * This code was developed by INCORS GmbH (www.incors.com).  * It is published under the terms of the GNU Lesser General Public License.  */
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
name|metal
operator|.
name|*
import|;
end_import

begin_class
DECL|class|KunststoffButtonUI
specifier|public
class|class
name|KunststoffButtonUI
extends|extends
name|MetalButtonUI
block|{
DECL|field|buttonUI
specifier|private
specifier|final
specifier|static
name|KunststoffButtonUI
name|buttonUI
init|=
operator|new
name|KunststoffButtonUI
argument_list|()
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
name|buttonUI
return|;
block|}
DECL|method|paint (Graphics g, JComponent c)
specifier|public
name|void
name|paint
parameter_list|(
name|Graphics
name|g
parameter_list|,
name|JComponent
name|c
parameter_list|)
block|{
name|super
operator|.
name|paint
argument_list|(
name|g
argument_list|,
name|c
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|c
operator|.
name|isOpaque
argument_list|()
condition|)
block|{
return|return;
comment|// we only paint the gradients if the button is opaque.
comment|// Thanks to Christoph Wilhelms for proposing this.
block|}
name|Component
name|parent
init|=
name|c
operator|.
name|getParent
argument_list|()
decl_stmt|;
if|if
condition|(
name|parent
operator|instanceof
name|JToolBar
condition|)
block|{
name|int
name|orientation
init|=
operator|(
operator|(
name|JToolBar
operator|)
name|parent
operator|)
operator|.
name|getOrientation
argument_list|()
decl_stmt|;
name|Point
name|loc
init|=
name|c
operator|.
name|getLocation
argument_list|()
decl_stmt|;
name|Rectangle
name|rectReflection
decl_stmt|;
name|Rectangle
name|rectShadow
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
name|colorShadow
init|=
name|KunststoffLookAndFeel
operator|.
name|getComponentGradientColorShadow
argument_list|()
decl_stmt|;
if|if
condition|(
name|orientation
operator|==
name|SwingConstants
operator|.
name|HORIZONTAL
condition|)
block|{
comment|// if tool bar orientation is horizontal
comment|// paint upper gradient
if|if
condition|(
name|colorReflection
operator|!=
literal|null
condition|)
block|{
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
name|rectReflection
operator|=
operator|new
name|Rectangle
argument_list|(
literal|0
argument_list|,
operator|-
name|loc
operator|.
name|y
argument_list|,
name|parent
operator|.
name|getWidth
argument_list|()
argument_list|,
name|parent
operator|.
name|getHeight
argument_list|()
operator|/
literal|2
argument_list|)
expr_stmt|;
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
name|rectReflection
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
comment|// paint lower gradient
if|if
condition|(
name|colorShadow
operator|!=
literal|null
condition|)
block|{
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
name|rectShadow
operator|=
operator|new
name|Rectangle
argument_list|(
literal|0
argument_list|,
name|parent
operator|.
name|getHeight
argument_list|()
operator|/
literal|2
operator|-
name|loc
operator|.
name|y
argument_list|,
name|parent
operator|.
name|getWidth
argument_list|()
argument_list|,
name|parent
operator|.
name|getHeight
argument_list|()
operator|/
literal|2
argument_list|)
expr_stmt|;
name|KunststoffUtilities
operator|.
name|drawGradient
argument_list|(
name|g
argument_list|,
name|colorShadowFaded
argument_list|,
name|colorShadow
argument_list|,
name|rectShadow
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
comment|// if tool bar orientation is vertical
comment|// paint left gradient
if|if
condition|(
name|colorReflection
operator|!=
literal|null
condition|)
block|{
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
name|rectReflection
operator|=
operator|new
name|Rectangle
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
name|parent
operator|.
name|getWidth
argument_list|()
operator|/
literal|2
argument_list|,
name|parent
operator|.
name|getHeight
argument_list|()
argument_list|)
expr_stmt|;
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
name|rectReflection
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
comment|// paint right gradient
if|if
condition|(
name|colorShadow
operator|!=
literal|null
condition|)
block|{
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
name|rectShadow
operator|=
operator|new
name|Rectangle
argument_list|(
name|parent
operator|.
name|getWidth
argument_list|()
operator|/
literal|2
operator|-
name|loc
operator|.
name|x
argument_list|,
literal|0
argument_list|,
name|parent
operator|.
name|getWidth
argument_list|()
argument_list|,
name|parent
operator|.
name|getHeight
argument_list|()
argument_list|)
expr_stmt|;
name|KunststoffUtilities
operator|.
name|drawGradient
argument_list|(
name|g
argument_list|,
name|colorShadowFaded
argument_list|,
name|colorShadow
argument_list|,
name|rectShadow
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
comment|// if not in toolbar
comment|// paint upper gradient
name|Color
name|colorReflection
init|=
name|KunststoffLookAndFeel
operator|.
name|getComponentGradientColorReflection
argument_list|()
decl_stmt|;
if|if
condition|(
name|colorReflection
operator|!=
literal|null
condition|)
block|{
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
name|Rectangle
name|rect
init|=
operator|new
name|Rectangle
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
name|c
operator|.
name|getWidth
argument_list|()
argument_list|,
name|c
operator|.
name|getHeight
argument_list|()
operator|/
literal|2
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
name|rect
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
comment|// paint lower gradient
name|Color
name|colorShadow
init|=
name|KunststoffLookAndFeel
operator|.
name|getComponentGradientColorShadow
argument_list|()
decl_stmt|;
if|if
condition|(
name|colorShadow
operator|!=
literal|null
condition|)
block|{
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
name|Rectangle
name|rect
init|=
operator|new
name|Rectangle
argument_list|(
literal|0
argument_list|,
name|c
operator|.
name|getHeight
argument_list|()
operator|/
literal|2
argument_list|,
name|c
operator|.
name|getWidth
argument_list|()
argument_list|,
name|c
operator|.
name|getHeight
argument_list|()
operator|/
literal|2
argument_list|)
decl_stmt|;
name|KunststoffUtilities
operator|.
name|drawGradient
argument_list|(
name|g
argument_list|,
name|colorShadowFaded
argument_list|,
name|colorShadow
argument_list|,
name|rect
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

