begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.fieldeditors
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|fieldeditors
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
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|GUIGlobals
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|FieldName
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
name|FieldNameLabel
operator|.
name|getFieldNameLabelText
argument_list|(
name|name
argument_list|)
argument_list|,
name|SwingConstants
operator|.
name|LEFT
argument_list|)
expr_stmt|;
name|setVerticalAlignment
argument_list|(
name|SwingConstants
operator|.
name|TOP
argument_list|)
expr_stmt|;
name|setForeground
argument_list|(
name|GUIGlobals
operator|.
name|ENTRY_EDITOR_LABEL_COLOR
argument_list|)
expr_stmt|;
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
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
DECL|method|getFieldNameLabelText (String fieldName)
specifier|private
specifier|static
name|String
name|getFieldNameLabelText
parameter_list|(
name|String
name|fieldName
parameter_list|)
block|{
return|return
literal|' '
operator|+
name|FieldName
operator|.
name|getDisplayName
argument_list|(
name|fieldName
argument_list|)
operator|+
literal|' '
return|;
block|}
block|}
end_class

end_unit
