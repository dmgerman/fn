begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.search
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|search
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
name|TextField
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
name|controlsfx
operator|.
name|control
operator|.
name|textfield
operator|.
name|CustomTextField
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
name|textfield
operator|.
name|TextFields
import|;
end_import

begin_class
DECL|class|SearchTextField
specifier|public
class|class
name|SearchTextField
block|{
DECL|method|create ()
specifier|public
specifier|static
name|TextField
name|create
parameter_list|()
block|{
name|CustomTextField
name|textField
init|=
operator|(
name|CustomTextField
operator|)
name|TextFields
operator|.
name|createClearableTextField
argument_list|()
decl_stmt|;
name|textField
operator|.
name|setPromptText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Search"
argument_list|)
operator|+
literal|"..."
argument_list|)
expr_stmt|;
name|textField
operator|.
name|setLeft
argument_list|(
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|SEARCH
operator|.
name|getGraphicNode
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|textField
return|;
block|}
block|}
end_class

end_unit

