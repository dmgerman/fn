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
name|event
operator|.
name|FocusListener
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

begin_class
DECL|class|GlobalFocusListener
specifier|public
class|class
name|GlobalFocusListener
implements|implements
name|FocusListener
block|{
DECL|field|focused
name|Component
name|focused
init|=
literal|null
decl_stmt|;
DECL|method|GlobalFocusListener ()
specifier|public
name|GlobalFocusListener
parameter_list|()
block|{   }
DECL|method|focusGained (FocusEvent e)
specifier|public
name|void
name|focusGained
parameter_list|(
name|FocusEvent
name|e
parameter_list|)
block|{
if|if
condition|(
operator|!
name|e
operator|.
name|isTemporary
argument_list|()
condition|)
name|focused
operator|=
operator|(
name|Component
operator|)
name|e
operator|.
name|getSource
argument_list|()
expr_stmt|;
block|}
DECL|method|focusLost (FocusEvent e)
specifier|public
name|void
name|focusLost
parameter_list|(
name|FocusEvent
name|e
parameter_list|)
block|{   }
DECL|method|getFocused ()
specifier|public
name|JComponent
name|getFocused
parameter_list|()
block|{
return|return
operator|(
name|JComponent
operator|)
name|focused
return|;
block|}
block|}
end_class

end_unit

