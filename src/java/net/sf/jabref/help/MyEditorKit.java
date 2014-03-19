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
name|Point
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
name|ActionEvent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|Action
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JViewport
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|SwingConstants
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
name|DefaultEditorKit
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
name|JTextComponent
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
name|TextAction
import|;
end_import

begin_class
annotation|@
name|SuppressWarnings
argument_list|(
literal|"serial"
argument_list|)
DECL|class|MyEditorKit
specifier|public
class|class
name|MyEditorKit
extends|extends
name|LargeHTMLEditorKit
block|{
DECL|class|MyNextVisualPositionAction
specifier|public
class|class
name|MyNextVisualPositionAction
extends|extends
name|TextAction
block|{
DECL|field|textActn
specifier|private
name|Action
name|textActn
decl_stmt|;
DECL|field|direction
specifier|private
name|int
name|direction
decl_stmt|;
DECL|method|MyNextVisualPositionAction (Action textActn, int direction)
specifier|private
name|MyNextVisualPositionAction
parameter_list|(
name|Action
name|textActn
parameter_list|,
name|int
name|direction
parameter_list|)
block|{
name|super
argument_list|(
operator|(
name|String
operator|)
name|textActn
operator|.
name|getValue
argument_list|(
name|Action
operator|.
name|NAME
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|textActn
operator|=
name|textActn
expr_stmt|;
name|this
operator|.
name|direction
operator|=
name|direction
expr_stmt|;
block|}
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|JTextComponent
name|c
init|=
name|getTextComponent
argument_list|(
name|e
argument_list|)
decl_stmt|;
if|if
condition|(
name|c
operator|.
name|getParent
argument_list|()
operator|instanceof
name|JViewport
condition|)
block|{
name|JViewport
name|viewport
init|=
operator|(
name|JViewport
operator|)
name|c
operator|.
name|getParent
argument_list|()
decl_stmt|;
name|Point
name|p
init|=
name|viewport
operator|.
name|getViewPosition
argument_list|()
decl_stmt|;
if|if
condition|(
name|this
operator|.
name|direction
operator|==
name|SwingConstants
operator|.
name|NORTH
condition|)
block|{
name|c
operator|.
name|setCaretPosition
argument_list|(
name|c
operator|.
name|viewToModel
argument_list|(
name|p
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|p
operator|.
name|y
operator|+=
name|viewport
operator|.
name|getExtentSize
argument_list|()
operator|.
name|height
expr_stmt|;
name|c
operator|.
name|setCaretPosition
argument_list|(
name|c
operator|.
name|viewToModel
argument_list|(
name|p
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|textActn
operator|.
name|actionPerformed
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
DECL|field|myActions
specifier|private
name|Action
index|[]
name|myActions
decl_stmt|;
DECL|method|getActions ()
specifier|public
name|Action
index|[]
name|getActions
parameter_list|()
block|{
if|if
condition|(
name|myActions
operator|==
literal|null
condition|)
block|{
name|Action
index|[]
name|actions
init|=
name|super
operator|.
name|getActions
argument_list|()
decl_stmt|;
name|Action
index|[]
name|newActions
init|=
operator|new
name|Action
index|[
literal|2
index|]
decl_stmt|;
for|for
control|(
name|Action
name|actn
range|:
name|actions
control|)
block|{
name|String
name|name
init|=
operator|(
name|String
operator|)
name|actn
operator|.
name|getValue
argument_list|(
name|Action
operator|.
name|NAME
argument_list|)
decl_stmt|;
if|if
condition|(
name|name
operator|.
name|equals
argument_list|(
name|DefaultEditorKit
operator|.
name|upAction
argument_list|)
condition|)
block|{
name|newActions
index|[
literal|0
index|]
operator|=
operator|new
name|MyNextVisualPositionAction
argument_list|(
name|actn
argument_list|,
name|SwingConstants
operator|.
name|NORTH
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|name
operator|.
name|equals
argument_list|(
name|DefaultEditorKit
operator|.
name|downAction
argument_list|)
condition|)
block|{
name|newActions
index|[
literal|1
index|]
operator|=
operator|new
name|MyNextVisualPositionAction
argument_list|(
name|actn
argument_list|,
name|SwingConstants
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
block|}
block|}
name|myActions
operator|=
name|TextAction
operator|.
name|augmentList
argument_list|(
name|actions
argument_list|,
name|newActions
argument_list|)
expr_stmt|;
block|}
return|return
name|myActions
return|;
block|}
block|}
end_class

end_unit

