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
name|net
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ResourceBundle
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|function
operator|.
name|Supplier
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|fxml
operator|.
name|Initializable
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
name|MenuItem
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|input
operator|.
name|KeyEvent
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|layout
operator|.
name|HBox
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|layout
operator|.
name|Priority
import|;
end_import

begin_comment
comment|//import com.sun.javafx.scene.control.skin.TextFieldSkin;
end_comment

begin_comment
comment|// TODO: TextFieldSkin changed in Java 9
end_comment

begin_class
DECL|class|EditorTextField
specifier|public
class|class
name|EditorTextField
extends|extends
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|TextField
implements|implements
name|Initializable
implements|,
name|ContextMenuAddable
block|{
DECL|method|EditorTextField ()
specifier|public
name|EditorTextField
parameter_list|()
block|{
name|this
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
DECL|method|EditorTextField (final String text)
specifier|public
name|EditorTextField
parameter_list|(
specifier|final
name|String
name|text
parameter_list|)
block|{
name|super
argument_list|(
name|text
argument_list|)
expr_stmt|;
comment|// Always fill out all the available space
name|setPrefHeight
argument_list|(
name|Double
operator|.
name|POSITIVE_INFINITY
argument_list|)
expr_stmt|;
name|HBox
operator|.
name|setHgrow
argument_list|(
name|this
argument_list|,
name|Priority
operator|.
name|ALWAYS
argument_list|)
expr_stmt|;
comment|// Should behave as a normal text field with respect to TAB behaviour
name|addEventFilter
argument_list|(
name|KeyEvent
operator|.
name|KEY_PRESSED
argument_list|,
name|event
lambda|->
block|{
comment|//            if (event.getCode() == KeyCode.TAB) {
comment|//                TextFieldSkin skin = (TextFieldSkin) getSkin();
comment|//                if (event.isShiftDown()) {
comment|//                    // Shift + Tab> previous text area
comment|//                    skin.getBehavior().traversePrevious();
comment|//                } else {
comment|//                    if (event.isControlDown()) {
comment|//                        // Ctrl + Tab> insert tab
comment|//                        skin.getBehavior().callAction("InsertTab");
comment|//                    } else {
comment|//                        // Tab> next text area
comment|//                        skin.getBehavior().traverseNext();
comment|//                    }
comment|//                }
comment|//                event.consume();
comment|//            }
block|}
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|addToContextMenu (final Supplier<List<MenuItem>> items)
specifier|public
name|void
name|addToContextMenu
parameter_list|(
specifier|final
name|Supplier
argument_list|<
name|List
argument_list|<
name|MenuItem
argument_list|>
argument_list|>
name|items
parameter_list|)
block|{
comment|//        TextFieldSkin customContextSkin = new TextFieldSkin(this) {
comment|//            @Override
comment|//            public void populateContextMenu(ContextMenu contextMenu) {
comment|//                super.populateContextMenu(contextMenu);
comment|//                contextMenu.getItems().addAll(0, items.get());
comment|//            }
comment|//        };
comment|//        setSkin(customContextSkin);
block|}
annotation|@
name|Override
DECL|method|initialize (URL location, ResourceBundle resources)
specifier|public
name|void
name|initialize
parameter_list|(
name|URL
name|location
parameter_list|,
name|ResourceBundle
name|resources
parameter_list|)
block|{
comment|// not needed
block|}
block|}
end_class

end_unit

