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
name|ContextMenu
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
name|control
operator|.
name|skin
operator|.
name|TextAreaSkin
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
name|KeyCode
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

begin_class
DECL|class|EditorTextArea
specifier|public
class|class
name|EditorTextArea
extends|extends
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|TextArea
implements|implements
name|Initializable
block|{
DECL|method|EditorTextArea ()
specifier|public
name|EditorTextArea
parameter_list|()
block|{
name|this
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
DECL|method|EditorTextArea (String text)
specifier|public
name|EditorTextArea
parameter_list|(
name|String
name|text
parameter_list|)
block|{
name|super
argument_list|(
name|text
argument_list|)
expr_stmt|;
name|setMinHeight
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|setMinWidth
argument_list|(
literal|200
argument_list|)
expr_stmt|;
comment|// Hide horizontal scrollbar and always wrap text
name|setWrapText
argument_list|(
literal|true
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
if|if
condition|(
name|event
operator|.
name|getCode
argument_list|()
operator|==
name|KeyCode
operator|.
name|TAB
condition|)
block|{
comment|// TODO: temporarily removed, as this is internal API
comment|//                TextAreaSkin skin = (TextAreaSkin) getSkin();
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
name|event
operator|.
name|consume
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
comment|/**      * Adds the given list of menu items to the context menu. The usage of {@link Supplier} prevents that the menus need      * to be instantiated at this point. They are populated when the user needs them which prevents many unnecessary      * allocations when the main table is just scrolled with the entry editor open.      */
DECL|method|addToContextMenu (Supplier<List<MenuItem>> items)
specifier|public
name|void
name|addToContextMenu
parameter_list|(
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
name|TextAreaSkin
name|customContextSkin
init|=
operator|new
name|TextAreaSkin
argument_list|(
name|this
argument_list|)
block|{
comment|// TODO: temporarily removed, internal API
comment|//            @Override
comment|//            public void populateContextMenu(ContextMenu contextMenu) {
comment|//                super.populateContextMenu(contextMenu);
comment|//                contextMenu.getItems().addAll(0, items.get());
comment|//            }
block|}
decl_stmt|;
name|setSkin
argument_list|(
name|customContextSkin
argument_list|)
expr_stmt|;
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

