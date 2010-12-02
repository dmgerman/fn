begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|awt
operator|.
name|Color
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
name|RenderingHints
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|BorderFactory
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

begin_class
DECL|class|FieldNameLabel
specifier|public
class|class
name|FieldNameLabel
extends|extends
name|JLabel
block|{
DECL|method|FieldNameLabel (String name)
specifier|public
name|FieldNameLabel
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|super
argument_list|(
name|name
argument_list|,
name|JLabel
operator|.
name|LEFT
argument_list|)
expr_stmt|;
name|setVerticalAlignment
argument_list|(
name|TOP
argument_list|)
expr_stmt|;
comment|//setFont(GUIGlobals.fieldNameFont);
name|setForeground
argument_list|(
name|GUIGlobals
operator|.
name|entryEditorLabelColor
argument_list|)
expr_stmt|;
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createMatteBorder
argument_list|(
literal|1
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
name|Color
operator|.
name|GRAY
argument_list|)
argument_list|)
expr_stmt|;
comment|//setBorder(BorderFactory.createEtchedBorder());
block|}
DECL|method|paintComponent (Graphics g)
specifier|public
name|void
name|paintComponent
parameter_list|(
name|Graphics
name|g
parameter_list|)
block|{
name|Graphics2D
name|g2
init|=
operator|(
name|Graphics2D
operator|)
name|g
decl_stmt|;
name|g2
operator|.
name|setRenderingHint
argument_list|(
name|RenderingHints
operator|.
name|KEY_ANTIALIASING
argument_list|,
name|RenderingHints
operator|.
name|VALUE_ANTIALIAS_ON
argument_list|)
expr_stmt|;
name|g2
operator|.
name|setRenderingHint
argument_list|(
name|RenderingHints
operator|.
name|KEY_RENDERING
argument_list|,
name|RenderingHints
operator|.
name|VALUE_RENDER_QUALITY
argument_list|)
expr_stmt|;
name|super
operator|.
name|paintComponent
argument_list|(
name|g2
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

