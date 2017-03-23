begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.util.component
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|util
operator|.
name|component
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
name|Font
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
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|KeyEvent
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
name|KeyListener
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JTextArea
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|UIManager
import|;
end_import

begin_comment
comment|/**  * A text area which displays a predefined text the same way as {@link JTextFieldWithPlaceholder} does.  */
end_comment

begin_class
DECL|class|JTextAreaWithPlaceholder
specifier|public
class|class
name|JTextAreaWithPlaceholder
extends|extends
name|JTextArea
implements|implements
name|KeyListener
block|{
DECL|field|textWhenNotFocused
specifier|private
specifier|final
name|String
name|textWhenNotFocused
decl_stmt|;
DECL|method|JTextAreaWithPlaceholder ()
specifier|public
name|JTextAreaWithPlaceholder
parameter_list|()
block|{
name|this
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
comment|/**      * Additionally to {@link JTextAreaWithPlaceholder#JTextAreaWithPlaceholder(String)}      * this also sets the initial text of the text field component.      *      * @param content as the text of the textfield      * @param placeholder as the placeholder of the textfield      */
DECL|method|JTextAreaWithPlaceholder (String content, String placeholder)
specifier|public
name|JTextAreaWithPlaceholder
parameter_list|(
name|String
name|content
parameter_list|,
name|String
name|placeholder
parameter_list|)
block|{
name|this
argument_list|(
name|placeholder
argument_list|)
expr_stmt|;
name|setText
argument_list|(
name|content
argument_list|)
expr_stmt|;
block|}
comment|/**      * This will create a {@link JTextArea} with a placeholder text. The placeholder      * will always be displayed if the text of the {@link JTextArea} is empty.      *      * @param placeholder as the placeholder of the textarea      */
DECL|method|JTextAreaWithPlaceholder (String placeholder)
specifier|public
name|JTextAreaWithPlaceholder
parameter_list|(
name|String
name|placeholder
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|this
operator|.
name|setEditable
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|this
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|this
operator|.
name|textWhenNotFocused
operator|=
name|placeholder
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|paintComponent (Graphics graphics)
specifier|protected
name|void
name|paintComponent
parameter_list|(
name|Graphics
name|graphics
parameter_list|)
block|{
name|super
operator|.
name|paintComponent
argument_list|(
name|graphics
argument_list|)
expr_stmt|;
if|if
condition|(
name|this
operator|.
name|getText
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Font
name|prev
init|=
name|graphics
operator|.
name|getFont
argument_list|()
decl_stmt|;
name|Color
name|prevColor
init|=
name|graphics
operator|.
name|getColor
argument_list|()
decl_stmt|;
name|graphics
operator|.
name|setColor
argument_list|(
name|UIManager
operator|.
name|getColor
argument_list|(
literal|"textInactiveText"
argument_list|)
argument_list|)
expr_stmt|;
name|int
name|textHeight
init|=
name|graphics
operator|.
name|getFontMetrics
argument_list|()
operator|.
name|getHeight
argument_list|()
decl_stmt|;
name|int
name|x
init|=
name|this
operator|.
name|getInsets
argument_list|()
operator|.
name|left
decl_stmt|;
name|Graphics2D
name|g2d
init|=
operator|(
name|Graphics2D
operator|)
name|graphics
decl_stmt|;
name|RenderingHints
name|hints
init|=
name|g2d
operator|.
name|getRenderingHints
argument_list|()
decl_stmt|;
name|g2d
operator|.
name|setRenderingHint
argument_list|(
name|RenderingHints
operator|.
name|KEY_TEXT_ANTIALIASING
argument_list|,
name|RenderingHints
operator|.
name|VALUE_TEXT_ANTIALIAS_ON
argument_list|)
expr_stmt|;
name|g2d
operator|.
name|drawString
argument_list|(
name|textWhenNotFocused
argument_list|,
name|x
argument_list|,
name|textHeight
operator|+
name|this
operator|.
name|getInsets
argument_list|()
operator|.
name|top
argument_list|)
expr_stmt|;
name|g2d
operator|.
name|setRenderingHints
argument_list|(
name|hints
argument_list|)
expr_stmt|;
name|graphics
operator|.
name|setFont
argument_list|(
name|prev
argument_list|)
expr_stmt|;
name|graphics
operator|.
name|setColor
argument_list|(
name|prevColor
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|keyTyped (KeyEvent e)
specifier|public
name|void
name|keyTyped
parameter_list|(
name|KeyEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|this
operator|.
name|getText
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|this
operator|.
name|repaint
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|keyPressed (KeyEvent e)
specifier|public
name|void
name|keyPressed
parameter_list|(
name|KeyEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|this
operator|.
name|getText
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|this
operator|.
name|repaint
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|keyReleased (KeyEvent e)
specifier|public
name|void
name|keyReleased
parameter_list|(
name|KeyEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|this
operator|.
name|getText
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|this
operator|.
name|repaint
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

