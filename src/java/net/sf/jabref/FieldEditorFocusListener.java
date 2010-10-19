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
name|Component
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
name|FocusEvent
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
name|FocusListener
import|;
end_import

begin_comment
comment|/**  * Focus listener that changes the color of the text area when it has focus.  * Created by IntelliJ IDEA.  * User: alver  * Date: 18.mar.2005  * Time: 18:20:14  * To change this template use File | Settings | File Templates.  */
end_comment

begin_class
DECL|class|FieldEditorFocusListener
specifier|public
class|class
name|FieldEditorFocusListener
implements|implements
name|FocusListener
block|{
DECL|method|FieldEditorFocusListener ()
specifier|public
name|FieldEditorFocusListener
parameter_list|()
block|{     }
DECL|method|focusGained (FocusEvent event)
specifier|public
name|void
name|focusGained
parameter_list|(
name|FocusEvent
name|event
parameter_list|)
block|{
operator|(
operator|(
name|FieldEditor
operator|)
name|event
operator|.
name|getSource
argument_list|()
operator|)
operator|.
name|setActiveBackgroundColor
argument_list|()
expr_stmt|;
block|}
DECL|method|focusLost (FocusEvent event)
specifier|public
name|void
name|focusLost
parameter_list|(
name|FocusEvent
name|event
parameter_list|)
block|{
operator|(
operator|(
name|FieldEditor
operator|)
name|event
operator|.
name|getSource
argument_list|()
operator|)
operator|.
name|setValidBackgroundColor
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

