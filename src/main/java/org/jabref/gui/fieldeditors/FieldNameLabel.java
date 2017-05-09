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
name|Label
block|{
DECL|method|FieldNameLabel (String fieldName)
specifier|public
name|FieldNameLabel
parameter_list|(
name|String
name|fieldName
parameter_list|)
block|{
name|super
argument_list|(
name|FieldName
operator|.
name|getDisplayName
argument_list|(
name|fieldName
argument_list|)
argument_list|)
expr_stmt|;
comment|// TODO: style!
comment|//setVerticalAlignment(SwingConstants.TOP);
comment|//setForeground(GUIGlobals.ENTRY_EDITOR_LABEL_COLOR);
comment|//setBorder(BorderFactory.createEmptyBorder());
block|}
block|}
end_class

end_unit

