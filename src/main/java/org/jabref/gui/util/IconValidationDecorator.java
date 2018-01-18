begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.util
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|util
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|geometry
operator|.
name|Pos
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|Node
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|Label
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|Tooltip
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|IconTheme
import|;
end_import

begin_import
import|import
name|org
operator|.
name|controlsfx
operator|.
name|control
operator|.
name|decoration
operator|.
name|Decoration
import|;
end_import

begin_import
import|import
name|org
operator|.
name|controlsfx
operator|.
name|control
operator|.
name|decoration
operator|.
name|GraphicDecoration
import|;
end_import

begin_import
import|import
name|org
operator|.
name|controlsfx
operator|.
name|validation
operator|.
name|Severity
import|;
end_import

begin_import
import|import
name|org
operator|.
name|controlsfx
operator|.
name|validation
operator|.
name|ValidationMessage
import|;
end_import

begin_import
import|import
name|org
operator|.
name|controlsfx
operator|.
name|validation
operator|.
name|decoration
operator|.
name|GraphicValidationDecoration
import|;
end_import

begin_comment
comment|/**  * This class is similar to {@link GraphicValidationDecoration} but with a different style and font-based icon.  */
end_comment

begin_class
DECL|class|IconValidationDecorator
specifier|public
class|class
name|IconValidationDecorator
extends|extends
name|GraphicValidationDecoration
block|{
DECL|field|position
specifier|private
specifier|final
name|Pos
name|position
decl_stmt|;
DECL|method|IconValidationDecorator ()
specifier|public
name|IconValidationDecorator
parameter_list|()
block|{
name|this
argument_list|(
name|Pos
operator|.
name|BOTTOM_LEFT
argument_list|)
expr_stmt|;
block|}
DECL|method|IconValidationDecorator (Pos position)
specifier|public
name|IconValidationDecorator
parameter_list|(
name|Pos
name|position
parameter_list|)
block|{
name|this
operator|.
name|position
operator|=
name|position
expr_stmt|;
block|}
DECL|method|createErrorNode ()
specifier|protected
name|Node
name|createErrorNode
parameter_list|()
block|{
return|return
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|ERROR
operator|.
name|getGraphicNode
argument_list|()
return|;
block|}
DECL|method|createWarningNode ()
specifier|protected
name|Node
name|createWarningNode
parameter_list|()
block|{
return|return
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|WARNING
operator|.
name|getGraphicNode
argument_list|()
return|;
block|}
DECL|method|createDecorationNode (ValidationMessage message)
specifier|public
name|Node
name|createDecorationNode
parameter_list|(
name|ValidationMessage
name|message
parameter_list|)
block|{
name|Node
name|graphic
init|=
name|Severity
operator|.
name|ERROR
operator|==
name|message
operator|.
name|getSeverity
argument_list|()
condition|?
name|createErrorNode
argument_list|()
else|:
name|createWarningNode
argument_list|()
decl_stmt|;
name|graphic
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
name|Severity
operator|.
name|ERROR
operator|==
name|message
operator|.
name|getSeverity
argument_list|()
condition|?
literal|"error-icon"
else|:
literal|"warning-icon"
argument_list|)
expr_stmt|;
name|Label
name|label
init|=
operator|new
name|Label
argument_list|()
decl_stmt|;
name|label
operator|.
name|setGraphic
argument_list|(
name|graphic
argument_list|)
expr_stmt|;
name|label
operator|.
name|setTooltip
argument_list|(
name|createTooltip
argument_list|(
name|message
argument_list|)
argument_list|)
expr_stmt|;
name|label
operator|.
name|setAlignment
argument_list|(
name|Pos
operator|.
name|CENTER
argument_list|)
expr_stmt|;
return|return
name|label
return|;
block|}
DECL|method|createTooltip (ValidationMessage message)
specifier|protected
name|Tooltip
name|createTooltip
parameter_list|(
name|ValidationMessage
name|message
parameter_list|)
block|{
name|Tooltip
name|tooltip
init|=
operator|new
name|Tooltip
argument_list|(
name|message
operator|.
name|getText
argument_list|()
argument_list|)
decl_stmt|;
name|tooltip
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
name|Severity
operator|.
name|ERROR
operator|==
name|message
operator|.
name|getSeverity
argument_list|()
condition|?
literal|"tooltip-error"
else|:
literal|"tooltip-warning"
argument_list|)
expr_stmt|;
return|return
name|tooltip
return|;
block|}
annotation|@
name|Override
DECL|method|createValidationDecorations (ValidationMessage message)
specifier|protected
name|Collection
argument_list|<
name|Decoration
argument_list|>
name|createValidationDecorations
parameter_list|(
name|ValidationMessage
name|message
parameter_list|)
block|{
return|return
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|GraphicDecoration
argument_list|(
name|createDecorationNode
argument_list|(
name|message
argument_list|)
argument_list|,
name|position
argument_list|)
argument_list|)
return|;
block|}
block|}
end_class

end_unit

