begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
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
name|Component
import|;
end_import

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
name|FontMetrics
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
name|Rectangle
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|Icon
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JComponent
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
name|SwingUtilities
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
name|ComponentUI
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
name|BasicLabelUI
import|;
end_import

begin_comment
comment|/**  * A UI delegate for JLabel that rotates the label 90Â°  *<P>  * Extends {@link BasicLabelUI}.  *<P>  * The only difference between the appearance of labels in the Basic and Metal L&Fs is the manner in which diabled text  * is painted. As VerticalLabelUI does not override the method paintDisabledText, this class can be adapted for Metal  * L&F by extending MetalLabelUI instead of BasicLabelUI.  *<P>  * No other changes are required.  *  * @author Darryl  */
end_comment

begin_class
DECL|class|VerticalLabelUI
specifier|public
class|class
name|VerticalLabelUI
extends|extends
name|BasicLabelUI
block|{
DECL|field|clockwise
specifier|private
name|boolean
name|clockwise
decl_stmt|;
comment|// see comment in BasicLabelUI
DECL|field|verticalViewR
specifier|private
name|Rectangle
name|verticalViewR
init|=
operator|new
name|Rectangle
argument_list|()
decl_stmt|;
DECL|field|verticalIconR
specifier|private
name|Rectangle
name|verticalIconR
init|=
operator|new
name|Rectangle
argument_list|()
decl_stmt|;
DECL|field|verticalTextR
specifier|private
name|Rectangle
name|verticalTextR
init|=
operator|new
name|Rectangle
argument_list|()
decl_stmt|;
DECL|field|VERTICAL_LABEL_UI
specifier|private
specifier|static
specifier|final
name|VerticalLabelUI
name|VERTICAL_LABEL_UI
init|=
operator|new
name|VerticalLabelUI
argument_list|()
decl_stmt|;
DECL|field|SAFE_VERTICAL_LABEL_UI
specifier|private
specifier|static
specifier|final
name|VerticalLabelUI
name|SAFE_VERTICAL_LABEL_UI
init|=
operator|new
name|VerticalLabelUI
argument_list|()
decl_stmt|;
comment|/**      * Constructs a<code>VerticalLabelUI</code> with the default anticlockwise      * rotation      */
DECL|method|VerticalLabelUI ()
specifier|private
name|VerticalLabelUI
parameter_list|()
block|{     }
comment|/**      * Constructs a<code>VerticalLabelUI</code> with the desired rotation.      *<P>      * @param clockwise true to rotate clockwise, false for anticlockwise      */
DECL|method|VerticalLabelUI (boolean clockwise)
specifier|public
name|VerticalLabelUI
parameter_list|(
name|boolean
name|clockwise
parameter_list|)
block|{
name|this
operator|.
name|clockwise
operator|=
name|clockwise
expr_stmt|;
block|}
comment|/**      * @see ComponentUI#createUI(javax.swing.JComponent)      */
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
if|if
condition|(
name|System
operator|.
name|getSecurityManager
argument_list|()
operator|==
literal|null
condition|)
block|{
return|return
name|VerticalLabelUI
operator|.
name|VERTICAL_LABEL_UI
return|;
block|}
else|else
block|{
return|return
name|VerticalLabelUI
operator|.
name|SAFE_VERTICAL_LABEL_UI
return|;
block|}
block|}
comment|/**      * Overridden to always return -1, since a vertical label does not have a      * meaningful baseline.      *      * @see ComponentUI#getBaseline(JComponent, int, int)      */
annotation|@
name|Override
DECL|method|getBaseline (JComponent c, int width, int height)
specifier|public
name|int
name|getBaseline
parameter_list|(
name|JComponent
name|c
parameter_list|,
name|int
name|width
parameter_list|,
name|int
name|height
parameter_list|)
block|{
name|super
operator|.
name|getBaseline
argument_list|(
name|c
argument_list|,
name|width
argument_list|,
name|height
argument_list|)
expr_stmt|;
return|return
operator|-
literal|1
return|;
block|}
comment|/**      * Overridden to always return Component.BaselineResizeBehavior.OTHER,      * since a vertical label does not have a meaningful baseline      *      * @see ComponentUI#getBaselineResizeBehavior(javax.swing.JComponent)      */
annotation|@
name|Override
DECL|method|getBaselineResizeBehavior ( JComponent c)
specifier|public
name|Component
operator|.
name|BaselineResizeBehavior
name|getBaselineResizeBehavior
parameter_list|(
name|JComponent
name|c
parameter_list|)
block|{
name|super
operator|.
name|getBaselineResizeBehavior
argument_list|(
name|c
argument_list|)
expr_stmt|;
return|return
name|Component
operator|.
name|BaselineResizeBehavior
operator|.
name|OTHER
return|;
block|}
comment|/**      * Transposes the view rectangles as appropriate for a vertical view      * before invoking the super method and copies them after they have been      * altered by {@link SwingUtilities#layoutCompoundLabel(FontMetrics, String,      * Icon, int, int, int, int, Rectangle, Rectangle, Rectangle, int)}      */
annotation|@
name|Override
DECL|method|layoutCL (JLabel label, FontMetrics fontMetrics, String text, Icon icon, Rectangle viewR, Rectangle iconR, Rectangle textR)
specifier|protected
name|String
name|layoutCL
parameter_list|(
name|JLabel
name|label
parameter_list|,
name|FontMetrics
name|fontMetrics
parameter_list|,
name|String
name|text
parameter_list|,
name|Icon
name|icon
parameter_list|,
name|Rectangle
name|viewR
parameter_list|,
name|Rectangle
name|iconR
parameter_list|,
name|Rectangle
name|textR
parameter_list|)
block|{
name|verticalViewR
operator|=
name|transposeRectangle
argument_list|(
name|viewR
argument_list|,
name|verticalViewR
argument_list|)
expr_stmt|;
name|verticalIconR
operator|=
name|transposeRectangle
argument_list|(
name|iconR
argument_list|,
name|verticalIconR
argument_list|)
expr_stmt|;
name|verticalTextR
operator|=
name|transposeRectangle
argument_list|(
name|textR
argument_list|,
name|verticalTextR
argument_list|)
expr_stmt|;
name|text
operator|=
name|super
operator|.
name|layoutCL
argument_list|(
name|label
argument_list|,
name|fontMetrics
argument_list|,
name|text
argument_list|,
name|icon
argument_list|,
name|verticalViewR
argument_list|,
name|verticalIconR
argument_list|,
name|verticalTextR
argument_list|)
expr_stmt|;
name|copyRectangle
argument_list|(
name|verticalViewR
argument_list|,
name|viewR
argument_list|)
expr_stmt|;
name|copyRectangle
argument_list|(
name|verticalIconR
argument_list|,
name|iconR
argument_list|)
expr_stmt|;
name|copyRectangle
argument_list|(
name|verticalTextR
argument_list|,
name|textR
argument_list|)
expr_stmt|;
return|return
name|text
return|;
block|}
comment|/**      * Transforms the Graphics for vertical rendering and invokes the      * super method.      */
annotation|@
name|Override
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
name|Graphics2D
name|g2
init|=
operator|(
name|Graphics2D
operator|)
name|g
operator|.
name|create
argument_list|()
decl_stmt|;
if|if
condition|(
name|clockwise
condition|)
block|{
name|g2
operator|.
name|rotate
argument_list|(
name|Math
operator|.
name|PI
operator|/
literal|2
argument_list|,
name|c
operator|.
name|getSize
argument_list|()
operator|.
name|width
operator|/
literal|2
argument_list|,
name|c
operator|.
name|getSize
argument_list|()
operator|.
name|width
operator|/
literal|2
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|g2
operator|.
name|rotate
argument_list|(
operator|-
name|Math
operator|.
name|PI
operator|/
literal|2
argument_list|,
name|c
operator|.
name|getSize
argument_list|()
operator|.
name|height
operator|/
literal|2
argument_list|,
name|c
operator|.
name|getSize
argument_list|()
operator|.
name|height
operator|/
literal|2
argument_list|)
expr_stmt|;
block|}
name|super
operator|.
name|paint
argument_list|(
name|g2
argument_list|,
name|c
argument_list|)
expr_stmt|;
block|}
comment|/**      * Returns a Dimension appropriate for vertical rendering      *      * @see ComponentUI#getPreferredSize(javax.swing.JComponent)      */
annotation|@
name|Override
DECL|method|getPreferredSize (JComponent c)
specifier|public
name|Dimension
name|getPreferredSize
parameter_list|(
name|JComponent
name|c
parameter_list|)
block|{
return|return
name|transposeDimension
argument_list|(
name|super
operator|.
name|getPreferredSize
argument_list|(
name|c
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * Returns a Dimension appropriate for vertical rendering      *      * @see ComponentUI#getMaximumSize(javax.swing.JComponent)      */
annotation|@
name|Override
DECL|method|getMaximumSize (JComponent c)
specifier|public
name|Dimension
name|getMaximumSize
parameter_list|(
name|JComponent
name|c
parameter_list|)
block|{
return|return
name|transposeDimension
argument_list|(
name|super
operator|.
name|getMaximumSize
argument_list|(
name|c
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * Returns a Dimension appropriate for vertical rendering      *      * @see ComponentUI#getMinimumSize(javax.swing.JComponent)      */
annotation|@
name|Override
DECL|method|getMinimumSize (JComponent c)
specifier|public
name|Dimension
name|getMinimumSize
parameter_list|(
name|JComponent
name|c
parameter_list|)
block|{
return|return
name|transposeDimension
argument_list|(
name|super
operator|.
name|getMinimumSize
argument_list|(
name|c
argument_list|)
argument_list|)
return|;
block|}
DECL|method|transposeDimension (Dimension from)
specifier|private
specifier|static
name|Dimension
name|transposeDimension
parameter_list|(
name|Dimension
name|from
parameter_list|)
block|{
return|return
operator|new
name|Dimension
argument_list|(
name|from
operator|.
name|height
argument_list|,
name|from
operator|.
name|width
operator|+
literal|2
argument_list|)
return|;
block|}
DECL|method|transposeRectangle (Rectangle from, Rectangle to)
specifier|private
specifier|static
name|Rectangle
name|transposeRectangle
parameter_list|(
name|Rectangle
name|from
parameter_list|,
name|Rectangle
name|to
parameter_list|)
block|{
if|if
condition|(
name|to
operator|==
literal|null
condition|)
block|{
name|to
operator|=
operator|new
name|Rectangle
argument_list|()
expr_stmt|;
block|}
name|to
operator|.
name|x
operator|=
name|from
operator|.
name|y
expr_stmt|;
name|to
operator|.
name|y
operator|=
name|from
operator|.
name|x
expr_stmt|;
name|to
operator|.
name|width
operator|=
name|from
operator|.
name|height
expr_stmt|;
name|to
operator|.
name|height
operator|=
name|from
operator|.
name|width
expr_stmt|;
return|return
name|to
return|;
block|}
DECL|method|copyRectangle (Rectangle from, Rectangle to)
specifier|private
specifier|static
name|void
name|copyRectangle
parameter_list|(
name|Rectangle
name|from
parameter_list|,
name|Rectangle
name|to
parameter_list|)
block|{
if|if
condition|(
name|to
operator|==
literal|null
condition|)
block|{
return|return;
block|}
name|to
operator|.
name|x
operator|=
name|from
operator|.
name|x
expr_stmt|;
name|to
operator|.
name|y
operator|=
name|from
operator|.
name|y
expr_stmt|;
name|to
operator|.
name|width
operator|=
name|from
operator|.
name|width
expr_stmt|;
name|to
operator|.
name|height
operator|=
name|from
operator|.
name|height
expr_stmt|;
block|}
block|}
end_class

end_unit

