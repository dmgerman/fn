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
name|Objects
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

begin_import
import|import
name|com
operator|.
name|sun
operator|.
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
implements|,
name|ContextMenuAddable
block|{
comment|/**      *  Variable that contains user-defined behavior for paste action.      */
DECL|field|pasteActionHandler
specifier|private
name|PasteActionHandler
name|pasteActionHandler
init|=
parameter_list|()
lambda|->
block|{
comment|// Set empty paste behavior by default
block|}
decl_stmt|;
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
DECL|method|EditorTextArea (final String text)
specifier|public
name|EditorTextArea
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
name|TextAreaSkin
name|skin
init|=
operator|(
name|TextAreaSkin
operator|)
name|getSkin
argument_list|()
decl_stmt|;
if|if
condition|(
name|event
operator|.
name|isShiftDown
argument_list|()
condition|)
block|{
comment|// Shift + Tab> previous text area
name|skin
operator|.
name|getBehavior
argument_list|()
operator|.
name|traversePrevious
argument_list|()
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|event
operator|.
name|isControlDown
argument_list|()
condition|)
block|{
comment|// Ctrl + Tab> insert tab
name|skin
operator|.
name|getBehavior
argument_list|()
operator|.
name|callAction
argument_list|(
literal|"InsertTab"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// Tab> next text area
name|skin
operator|.
name|getBehavior
argument_list|()
operator|.
name|traverseNext
argument_list|()
expr_stmt|;
block|}
block|}
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
name|TextAreaSkin
name|customContextSkin
init|=
operator|new
name|TextAreaSkin
argument_list|(
name|this
argument_list|)
block|{
annotation|@
name|Override
specifier|public
name|void
name|populateContextMenu
parameter_list|(
name|ContextMenu
name|contextMenu
parameter_list|)
block|{
name|super
operator|.
name|populateContextMenu
argument_list|(
name|contextMenu
argument_list|)
expr_stmt|;
name|contextMenu
operator|.
name|getItems
argument_list|()
operator|.
name|addAll
argument_list|(
literal|0
argument_list|,
name|items
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
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
comment|/**      * Set pasteActionHandler variable to passed handler      * @param  handler an instance of PasteActionHandler that describes paste behavior      */
DECL|method|setPasteActionHandler (PasteActionHandler handler)
specifier|public
name|void
name|setPasteActionHandler
parameter_list|(
name|PasteActionHandler
name|handler
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|handler
argument_list|)
expr_stmt|;
name|this
operator|.
name|pasteActionHandler
operator|=
name|handler
expr_stmt|;
block|}
comment|/**      *  Override javafx TextArea method applying TextArea.paste() and pasteActionHandler after      */
annotation|@
name|Override
DECL|method|paste ()
specifier|public
name|void
name|paste
parameter_list|()
block|{
name|super
operator|.
name|paste
argument_list|()
expr_stmt|;
name|pasteActionHandler
operator|.
name|handle
argument_list|()
expr_stmt|;
block|}
comment|/**      *  Interface presents user-described paste behaviour applying to paste method      */
annotation|@
name|FunctionalInterface
DECL|interface|PasteActionHandler
specifier|public
interface|interface
name|PasteActionHandler
block|{
DECL|method|handle ()
name|void
name|handle
parameter_list|()
function_decl|;
block|}
block|}
end_class

end_unit

