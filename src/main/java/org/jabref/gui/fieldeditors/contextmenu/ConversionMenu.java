begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.fieldeditors.contextmenu
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|fieldeditors
operator|.
name|contextmenu
package|;
end_package

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|StringProperty
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
name|CustomMenuItem
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
name|Menu
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
name|logic
operator|.
name|formatter
operator|.
name|Formatters
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|l10n
operator|.
name|Localization
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
name|cleanup
operator|.
name|Formatter
import|;
end_import

begin_comment
comment|/**  * Menu to show up on right-click in a text field for converting text formats  */
end_comment

begin_class
DECL|class|ConversionMenu
class|class
name|ConversionMenu
extends|extends
name|Menu
block|{
DECL|method|ConversionMenu (StringProperty text)
specifier|public
name|ConversionMenu
parameter_list|(
name|StringProperty
name|text
parameter_list|)
block|{
name|super
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Convert"
argument_list|)
argument_list|)
expr_stmt|;
comment|// create menu items, one for each converter
for|for
control|(
name|Formatter
name|converter
range|:
name|Formatters
operator|.
name|getConverters
argument_list|()
control|)
block|{
name|CustomMenuItem
name|menuItem
init|=
operator|new
name|CustomMenuItem
argument_list|(
operator|new
name|Label
argument_list|(
name|converter
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|Tooltip
name|toolTip
init|=
operator|new
name|Tooltip
argument_list|(
name|converter
operator|.
name|getDescription
argument_list|()
argument_list|)
decl_stmt|;
name|Tooltip
operator|.
name|install
argument_list|(
name|menuItem
operator|.
name|getContent
argument_list|()
argument_list|,
name|toolTip
argument_list|)
expr_stmt|;
name|menuItem
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|text
operator|.
name|set
argument_list|(
name|converter
operator|.
name|format
argument_list|(
name|text
operator|.
name|get
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|getItems
argument_list|()
operator|.
name|add
argument_list|(
name|menuItem
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

