begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|com.incors.plaf
package|package
name|com
operator|.
name|incors
operator|.
name|plaf
package|;
end_package

begin_comment
comment|/*  * This code was contributed by Sebastian Ferreyra (sebastianf@citycolor.net).  * It is published under the terms of the GNU Lesser General Public License.  */
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
name|java
operator|.
name|awt
operator|.
name|geom
operator|.
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|image
operator|.
name|ColorModel
import|;
end_import

begin_comment
comment|/**  * DOCUMENT ME!  *  * @author $author$  * @version $Revision$  */
end_comment

begin_class
DECL|class|FastGradientPaint
specifier|public
class|class
name|FastGradientPaint
implements|implements
name|Paint
block|{
comment|//~ Instance fields ////////////////////////////////////////////////////////
DECL|field|isVertical
name|boolean
name|isVertical
decl_stmt|;
DECL|field|endColor
name|int
name|endColor
decl_stmt|;
DECL|field|startColor
name|int
name|startColor
decl_stmt|;
comment|//~ Constructors ///////////////////////////////////////////////////////////
DECL|method|FastGradientPaint (Color sc, Color ec, boolean isV)
specifier|public
name|FastGradientPaint
parameter_list|(
name|Color
name|sc
parameter_list|,
name|Color
name|ec
parameter_list|,
name|boolean
name|isV
parameter_list|)
block|{
name|startColor
operator|=
name|sc
operator|.
name|getRGB
argument_list|()
expr_stmt|;
name|endColor
operator|=
name|ec
operator|.
name|getRGB
argument_list|()
expr_stmt|;
name|isVertical
operator|=
name|isV
expr_stmt|;
block|}
comment|//~ Methods ////////////////////////////////////////////////////////////////
DECL|method|getTransparency ()
specifier|public
name|int
name|getTransparency
parameter_list|()
block|{
return|return
operator|(
operator|(
operator|(
operator|(
name|startColor
operator|&
name|endColor
operator|)
operator|>>
literal|24
operator|)
operator|&
literal|0xFF
operator|)
operator|==
literal|0xFF
operator|)
condition|?
name|OPAQUE
else|:
name|TRANSLUCENT
return|;
block|}
DECL|method|createContext (ColorModel cm, Rectangle r, Rectangle2D r2d, AffineTransform xform, RenderingHints hints)
specifier|public
specifier|synchronized
name|PaintContext
name|createContext
parameter_list|(
name|ColorModel
name|cm
parameter_list|,
name|Rectangle
name|r
parameter_list|,
name|Rectangle2D
name|r2d
parameter_list|,
name|AffineTransform
name|xform
parameter_list|,
name|RenderingHints
name|hints
parameter_list|)
block|{
return|return
operator|new
name|FastGradientPaintContext
argument_list|(
name|cm
argument_list|,
name|r
argument_list|,
name|startColor
argument_list|,
name|endColor
argument_list|,
name|isVertical
argument_list|)
return|;
block|}
block|}
end_class

begin_comment
comment|///////////////////////////////////////////////////////////////////////////////
end_comment

begin_comment
comment|//  END OF FILE.
end_comment

begin_comment
comment|///////////////////////////////////////////////////////////////////////////////
end_comment

end_unit

