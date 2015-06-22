begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.help
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|help
package|;
end_package

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
name|java
operator|.
name|awt
operator|.
name|Shape
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
name|AffineTransform
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|text
operator|.
name|AbstractDocument
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|text
operator|.
name|AttributeSet
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|text
operator|.
name|BadLocationException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|text
operator|.
name|Element
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|text
operator|.
name|Position
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|text
operator|.
name|StyleConstants
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|text
operator|.
name|View
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|text
operator|.
name|ViewFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|text
operator|.
name|html
operator|.
name|BlockView
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|text
operator|.
name|html
operator|.
name|CSS
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|text
operator|.
name|html
operator|.
name|HTML
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|text
operator|.
name|html
operator|.
name|HTMLEditorKit
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
comment|/**  * An extended {@link HTMLEditorKit} that allow faster  * rendering of large html files and allow zooming of content.  * @author Alessio Pollero  * @version 1.0  */
end_comment

begin_class
DECL|class|LargeHTMLEditorKit
specifier|public
class|class
name|LargeHTMLEditorKit
extends|extends
name|HTMLEditorKit
block|{
DECL|field|factory
specifier|final
name|ViewFactory
name|factory
init|=
operator|new
name|MyViewFactory
argument_list|()
decl_stmt|;
annotation|@
name|Override
DECL|method|getViewFactory ()
specifier|public
name|ViewFactory
name|getViewFactory
parameter_list|()
block|{
return|return
name|factory
return|;
block|}
DECL|class|MyViewFactory
class|class
name|MyViewFactory
extends|extends
name|HTMLFactory
block|{
annotation|@
name|Override
DECL|method|create (Element elem)
specifier|public
name|View
name|create
parameter_list|(
name|Element
name|elem
parameter_list|)
block|{
name|AttributeSet
name|attrs
init|=
name|elem
operator|.
name|getAttributes
argument_list|()
decl_stmt|;
name|Object
name|elementName
init|=
name|attrs
operator|.
name|getAttribute
argument_list|(
name|AbstractDocument
operator|.
name|ElementNameAttribute
argument_list|)
decl_stmt|;
name|Object
name|o
init|=
operator|(
name|elementName
operator|!=
literal|null
operator|)
condition|?
literal|null
else|:
name|attrs
operator|.
name|getAttribute
argument_list|(
name|StyleConstants
operator|.
name|NameAttribute
argument_list|)
decl_stmt|;
if|if
condition|(
name|o
operator|instanceof
name|HTML
operator|.
name|Tag
condition|)
block|{
name|HTML
operator|.
name|Tag
name|kind
init|=
operator|(
name|HTML
operator|.
name|Tag
operator|)
name|o
decl_stmt|;
if|if
condition|(
name|kind
operator|==
name|HTML
operator|.
name|Tag
operator|.
name|HTML
condition|)
block|{
return|return
operator|new
name|HTMLBlockView
argument_list|(
name|elem
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|kind
operator|==
name|HTML
operator|.
name|Tag
operator|.
name|IMPLIED
condition|)
block|{
name|String
name|ws
init|=
operator|(
name|String
operator|)
name|elem
operator|.
name|getAttributes
argument_list|()
operator|.
name|getAttribute
argument_list|(
name|CSS
operator|.
name|Attribute
operator|.
name|WHITE_SPACE
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|ws
operator|!=
literal|null
operator|)
operator|&&
name|ws
operator|.
name|equals
argument_list|(
literal|"pre"
argument_list|)
condition|)
block|{
return|return
name|super
operator|.
name|create
argument_list|(
name|elem
argument_list|)
return|;
block|}
return|return
operator|new
name|HTMLParagraphView
argument_list|(
name|elem
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
operator|(
name|kind
operator|==
name|HTML
operator|.
name|Tag
operator|.
name|P
operator|)
operator|||
operator|(
name|kind
operator|==
name|HTML
operator|.
name|Tag
operator|.
name|H1
operator|)
operator|||
operator|(
name|kind
operator|==
name|HTML
operator|.
name|Tag
operator|.
name|H2
operator|)
operator|||
operator|(
name|kind
operator|==
name|HTML
operator|.
name|Tag
operator|.
name|H3
operator|)
operator|||
operator|(
name|kind
operator|==
name|HTML
operator|.
name|Tag
operator|.
name|H4
operator|)
operator|||
operator|(
name|kind
operator|==
name|HTML
operator|.
name|Tag
operator|.
name|H5
operator|)
operator|||
operator|(
name|kind
operator|==
name|HTML
operator|.
name|Tag
operator|.
name|H6
operator|)
operator|||
operator|(
name|kind
operator|==
name|HTML
operator|.
name|Tag
operator|.
name|DT
operator|)
condition|)
block|{
comment|// paragraph
return|return
operator|new
name|HTMLParagraphView
argument_list|(
name|elem
argument_list|)
return|;
block|}
block|}
return|return
name|super
operator|.
name|create
argument_list|(
name|elem
argument_list|)
return|;
block|}
block|}
DECL|class|HTMLBlockView
specifier|private
class|class
name|HTMLBlockView
extends|extends
name|BlockView
block|{
DECL|method|HTMLBlockView (Element elem)
specifier|public
name|HTMLBlockView
parameter_list|(
name|Element
name|elem
parameter_list|)
block|{
name|super
argument_list|(
name|elem
argument_list|,
name|View
operator|.
name|Y_AXIS
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|layout (int width, int height)
specifier|protected
name|void
name|layout
parameter_list|(
name|int
name|width
parameter_list|,
name|int
name|height
parameter_list|)
block|{
if|if
condition|(
name|width
operator|<
name|Integer
operator|.
name|MAX_VALUE
condition|)
block|{
name|super
operator|.
name|layout
argument_list|(
operator|new
name|Double
argument_list|(
name|width
operator|/
name|getZoomFactor
argument_list|()
argument_list|)
operator|.
name|intValue
argument_list|()
argument_list|,
operator|new
name|Double
argument_list|(
name|height
operator|*
name|getZoomFactor
argument_list|()
argument_list|)
operator|.
name|intValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|getZoomFactor ()
specifier|public
name|double
name|getZoomFactor
parameter_list|()
block|{
name|Double
name|scale
init|=
operator|(
name|Double
operator|)
name|getDocument
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"ZOOM_FACTOR"
argument_list|)
decl_stmt|;
if|if
condition|(
name|scale
operator|==
literal|null
condition|)
block|{
return|return
name|GUIGlobals
operator|.
name|zoomLevel
return|;
block|}
else|else
block|{
return|return
name|scale
return|;
block|}
block|}
annotation|@
name|Override
DECL|method|paint (Graphics g, Shape allocation)
specifier|public
name|void
name|paint
parameter_list|(
name|Graphics
name|g
parameter_list|,
name|Shape
name|allocation
parameter_list|)
block|{
name|Graphics2D
name|g2d
init|=
operator|(
name|Graphics2D
operator|)
name|g
decl_stmt|;
name|double
name|zoomFactor
init|=
name|getZoomFactor
argument_list|()
decl_stmt|;
name|AffineTransform
name|old
init|=
name|g2d
operator|.
name|getTransform
argument_list|()
decl_stmt|;
name|g2d
operator|.
name|scale
argument_list|(
name|zoomFactor
argument_list|,
name|zoomFactor
argument_list|)
expr_stmt|;
name|super
operator|.
name|paint
argument_list|(
name|g2d
argument_list|,
name|allocation
argument_list|)
expr_stmt|;
name|g2d
operator|.
name|setTransform
argument_list|(
name|old
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getMinimumSpan (int axis)
specifier|public
name|float
name|getMinimumSpan
parameter_list|(
name|int
name|axis
parameter_list|)
block|{
name|float
name|f
init|=
name|super
operator|.
name|getMinimumSpan
argument_list|(
name|axis
argument_list|)
decl_stmt|;
name|f
operator|*=
name|getZoomFactor
argument_list|()
expr_stmt|;
return|return
name|f
return|;
block|}
annotation|@
name|Override
DECL|method|getMaximumSpan (int axis)
specifier|public
name|float
name|getMaximumSpan
parameter_list|(
name|int
name|axis
parameter_list|)
block|{
name|float
name|f
init|=
name|super
operator|.
name|getMaximumSpan
argument_list|(
name|axis
argument_list|)
decl_stmt|;
name|f
operator|*=
name|getZoomFactor
argument_list|()
expr_stmt|;
return|return
name|f
return|;
block|}
annotation|@
name|Override
DECL|method|getPreferredSpan (int axis)
specifier|public
name|float
name|getPreferredSpan
parameter_list|(
name|int
name|axis
parameter_list|)
block|{
name|float
name|f
init|=
name|super
operator|.
name|getPreferredSpan
argument_list|(
name|axis
argument_list|)
decl_stmt|;
name|f
operator|*=
name|getZoomFactor
argument_list|()
expr_stmt|;
return|return
name|f
return|;
block|}
annotation|@
name|Override
DECL|method|modelToView (int pos, Shape a, Position.Bias b)
specifier|public
name|Shape
name|modelToView
parameter_list|(
name|int
name|pos
parameter_list|,
name|Shape
name|a
parameter_list|,
name|Position
operator|.
name|Bias
name|b
parameter_list|)
throws|throws
name|BadLocationException
block|{
name|double
name|zoomFactor
init|=
name|getZoomFactor
argument_list|()
decl_stmt|;
name|Rectangle
name|alloc
decl_stmt|;
name|alloc
operator|=
name|a
operator|.
name|getBounds
argument_list|()
expr_stmt|;
name|Shape
name|s
init|=
name|super
operator|.
name|modelToView
argument_list|(
name|pos
argument_list|,
name|alloc
argument_list|,
name|b
argument_list|)
decl_stmt|;
name|alloc
operator|=
name|s
operator|.
name|getBounds
argument_list|()
expr_stmt|;
name|alloc
operator|.
name|x
operator|*=
name|zoomFactor
expr_stmt|;
name|alloc
operator|.
name|y
operator|*=
name|zoomFactor
expr_stmt|;
name|alloc
operator|.
name|width
operator|*=
name|zoomFactor
expr_stmt|;
name|alloc
operator|.
name|height
operator|*=
name|zoomFactor
expr_stmt|;
return|return
name|alloc
return|;
block|}
annotation|@
name|Override
DECL|method|viewToModel (float x, float y, Shape a, Position.Bias[] bias)
specifier|public
name|int
name|viewToModel
parameter_list|(
name|float
name|x
parameter_list|,
name|float
name|y
parameter_list|,
name|Shape
name|a
parameter_list|,
name|Position
operator|.
name|Bias
index|[]
name|bias
parameter_list|)
block|{
name|double
name|zoomFactor
init|=
name|getZoomFactor
argument_list|()
decl_stmt|;
name|Rectangle
name|alloc
init|=
name|a
operator|.
name|getBounds
argument_list|()
decl_stmt|;
name|x
operator|/=
name|zoomFactor
expr_stmt|;
name|y
operator|/=
name|zoomFactor
expr_stmt|;
name|alloc
operator|.
name|x
operator|/=
name|zoomFactor
expr_stmt|;
name|alloc
operator|.
name|y
operator|/=
name|zoomFactor
expr_stmt|;
name|alloc
operator|.
name|width
operator|/=
name|zoomFactor
expr_stmt|;
name|alloc
operator|.
name|height
operator|/=
name|zoomFactor
expr_stmt|;
return|return
name|super
operator|.
name|viewToModel
argument_list|(
name|x
argument_list|,
name|y
argument_list|,
name|alloc
argument_list|,
name|bias
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

