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
name|util
operator|.
name|ArrayList
import|;
end_import

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
name|List
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|geometry
operator|.
name|Point2D
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|geometry
operator|.
name|Rectangle2D
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|Scene
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
name|IndexRange
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
name|PasswordField
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
name|SeparatorMenuItem
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
name|TextArea
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
name|TextField
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
name|TextInputControl
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
name|control
operator|.
name|skin
operator|.
name|TextFieldSkin
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
name|Clipboard
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
name|ContextMenuEvent
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|stage
operator|.
name|Screen
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|stage
operator|.
name|Window
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
name|logic
operator|.
name|util
operator|.
name|OS
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
name|Properties
import|;
end_import

begin_comment
comment|/**  * This class contains some code taken from {@link com.sun.javafx.scene.control.behavior.TextInputControlBehavior},  * witch is not accessible and thus we have no other choice.  * TODO: remove this ugly workaround as soon as control behavior is made public  * reported at https://github.com/javafxports/openjdk-jfx/issues/583  */
end_comment

begin_class
DECL|class|TextInputControlBehavior
specifier|public
class|class
name|TextInputControlBehavior
block|{
DECL|field|SHOW_HANDLES
specifier|private
specifier|static
specifier|final
name|boolean
name|SHOW_HANDLES
init|=
name|Properties
operator|.
name|IS_TOUCH_SUPPORTED
operator|&&
operator|!
name|OS
operator|.
name|OS_X
decl_stmt|;
comment|/**      * Returns the default context menu items (except undo/redo)      */
DECL|method|getDefaultContextMenuItems (TextInputControl textInputControl)
specifier|public
specifier|static
name|List
argument_list|<
name|MenuItem
argument_list|>
name|getDefaultContextMenuItems
parameter_list|(
name|TextInputControl
name|textInputControl
parameter_list|)
block|{
name|boolean
name|editable
init|=
name|textInputControl
operator|.
name|isEditable
argument_list|()
decl_stmt|;
name|boolean
name|hasText
init|=
operator|(
name|textInputControl
operator|.
name|getLength
argument_list|()
operator|>
literal|0
operator|)
decl_stmt|;
name|boolean
name|hasSelection
init|=
operator|(
name|textInputControl
operator|.
name|getSelection
argument_list|()
operator|.
name|getLength
argument_list|()
operator|>
literal|0
operator|)
decl_stmt|;
name|boolean
name|allSelected
init|=
operator|(
name|textInputControl
operator|.
name|getSelection
argument_list|()
operator|.
name|getLength
argument_list|()
operator|==
name|textInputControl
operator|.
name|getLength
argument_list|()
operator|)
decl_stmt|;
name|boolean
name|maskText
init|=
operator|(
name|textInputControl
operator|instanceof
name|PasswordField
operator|)
decl_stmt|;
comment|// (maskText("A") != "A");
name|ArrayList
argument_list|<
name|MenuItem
argument_list|>
name|items
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|MenuItem
name|cutMI
init|=
operator|new
name|MenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cut"
argument_list|)
argument_list|)
decl_stmt|;
name|cutMI
operator|.
name|setOnAction
argument_list|(
name|e
lambda|->
name|textInputControl
operator|.
name|cut
argument_list|()
argument_list|)
expr_stmt|;
name|MenuItem
name|copyMI
init|=
operator|new
name|MenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Copy"
argument_list|)
argument_list|)
decl_stmt|;
name|copyMI
operator|.
name|setOnAction
argument_list|(
name|e
lambda|->
name|textInputControl
operator|.
name|copy
argument_list|()
argument_list|)
expr_stmt|;
name|MenuItem
name|pasteMI
init|=
operator|new
name|MenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Paste"
argument_list|)
argument_list|)
decl_stmt|;
name|pasteMI
operator|.
name|setOnAction
argument_list|(
name|e
lambda|->
name|textInputControl
operator|.
name|paste
argument_list|()
argument_list|)
expr_stmt|;
name|MenuItem
name|deleteMI
init|=
operator|new
name|MenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Delete"
argument_list|)
argument_list|)
decl_stmt|;
name|deleteMI
operator|.
name|setOnAction
argument_list|(
name|e
lambda|->
block|{
name|IndexRange
name|selection
init|=
name|textInputControl
operator|.
name|getSelection
argument_list|()
decl_stmt|;
name|textInputControl
operator|.
name|deleteText
argument_list|(
name|selection
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|MenuItem
name|selectAllMI
init|=
operator|new
name|MenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Select all"
argument_list|)
argument_list|)
decl_stmt|;
name|selectAllMI
operator|.
name|setOnAction
argument_list|(
name|e
lambda|->
name|textInputControl
operator|.
name|selectAll
argument_list|()
argument_list|)
expr_stmt|;
name|MenuItem
name|separatorMI
init|=
operator|new
name|SeparatorMenuItem
argument_list|()
decl_stmt|;
if|if
condition|(
name|SHOW_HANDLES
condition|)
block|{
if|if
condition|(
operator|!
name|maskText
operator|&&
name|hasSelection
condition|)
block|{
if|if
condition|(
name|editable
condition|)
block|{
name|items
operator|.
name|add
argument_list|(
name|cutMI
argument_list|)
expr_stmt|;
block|}
name|items
operator|.
name|add
argument_list|(
name|copyMI
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|editable
operator|&&
name|Clipboard
operator|.
name|getSystemClipboard
argument_list|()
operator|.
name|hasString
argument_list|()
condition|)
block|{
name|items
operator|.
name|add
argument_list|(
name|pasteMI
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|hasText
operator|&&
operator|!
name|allSelected
condition|)
block|{
name|items
operator|.
name|add
argument_list|(
name|selectAllMI
argument_list|)
expr_stmt|;
block|}
name|selectAllMI
operator|.
name|getProperties
argument_list|()
operator|.
name|put
argument_list|(
literal|"refreshMenu"
argument_list|,
name|Boolean
operator|.
name|TRUE
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|editable
condition|)
block|{
name|items
operator|.
name|addAll
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|cutMI
argument_list|,
name|copyMI
argument_list|,
name|pasteMI
argument_list|,
name|deleteMI
argument_list|,
name|separatorMI
argument_list|,
name|selectAllMI
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|items
operator|.
name|addAll
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|copyMI
argument_list|,
name|separatorMI
argument_list|,
name|selectAllMI
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|cutMI
operator|.
name|setDisable
argument_list|(
name|maskText
operator|||
operator|!
name|hasSelection
argument_list|)
expr_stmt|;
name|copyMI
operator|.
name|setDisable
argument_list|(
name|maskText
operator|||
operator|!
name|hasSelection
argument_list|)
expr_stmt|;
name|pasteMI
operator|.
name|setDisable
argument_list|(
operator|!
name|Clipboard
operator|.
name|getSystemClipboard
argument_list|()
operator|.
name|hasString
argument_list|()
argument_list|)
expr_stmt|;
name|deleteMI
operator|.
name|setDisable
argument_list|(
operator|!
name|hasSelection
argument_list|)
expr_stmt|;
block|}
return|return
name|items
return|;
block|}
comment|/**      * @implNote taken from {@link com.sun.javafx.scene.control.behavior.TextFieldBehavior#contextMenuRequested(javafx.scene.input.ContextMenuEvent)}      */
DECL|method|showContextMenu (TextField textField, ContextMenu contextMenu, ContextMenuEvent e)
specifier|public
specifier|static
name|void
name|showContextMenu
parameter_list|(
name|TextField
name|textField
parameter_list|,
name|ContextMenu
name|contextMenu
parameter_list|,
name|ContextMenuEvent
name|e
parameter_list|)
block|{
name|double
name|screenX
init|=
name|e
operator|.
name|getScreenX
argument_list|()
decl_stmt|;
name|double
name|screenY
init|=
name|e
operator|.
name|getScreenY
argument_list|()
decl_stmt|;
name|double
name|sceneX
init|=
name|e
operator|.
name|getSceneX
argument_list|()
decl_stmt|;
name|TextFieldSkin
name|skin
init|=
operator|(
name|TextFieldSkin
operator|)
name|textField
operator|.
name|getSkin
argument_list|()
decl_stmt|;
if|if
condition|(
name|Properties
operator|.
name|IS_TOUCH_SUPPORTED
condition|)
block|{
name|Point2D
name|menuPos
decl_stmt|;
if|if
condition|(
name|textField
operator|.
name|getSelection
argument_list|()
operator|.
name|getLength
argument_list|()
operator|==
literal|0
condition|)
block|{
name|skin
operator|.
name|positionCaret
argument_list|(
name|skin
operator|.
name|getIndex
argument_list|(
name|e
operator|.
name|getX
argument_list|()
argument_list|,
name|e
operator|.
name|getY
argument_list|()
argument_list|)
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|menuPos
operator|=
name|skin
operator|.
name|getMenuPosition
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|menuPos
operator|=
name|skin
operator|.
name|getMenuPosition
argument_list|()
expr_stmt|;
if|if
condition|(
name|menuPos
operator|!=
literal|null
operator|&&
operator|(
name|menuPos
operator|.
name|getX
argument_list|()
operator|<=
literal|0
operator|||
name|menuPos
operator|.
name|getY
argument_list|()
operator|<=
literal|0
operator|)
condition|)
block|{
name|skin
operator|.
name|positionCaret
argument_list|(
name|skin
operator|.
name|getIndex
argument_list|(
name|e
operator|.
name|getX
argument_list|()
argument_list|,
name|e
operator|.
name|getY
argument_list|()
argument_list|)
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|menuPos
operator|=
name|skin
operator|.
name|getMenuPosition
argument_list|()
expr_stmt|;
block|}
block|}
if|if
condition|(
name|menuPos
operator|!=
literal|null
condition|)
block|{
name|Point2D
name|p
init|=
name|textField
operator|.
name|localToScene
argument_list|(
name|menuPos
argument_list|)
decl_stmt|;
name|Scene
name|scene
init|=
name|textField
operator|.
name|getScene
argument_list|()
decl_stmt|;
name|Window
name|window
init|=
name|scene
operator|.
name|getWindow
argument_list|()
decl_stmt|;
name|Point2D
name|location
init|=
operator|new
name|Point2D
argument_list|(
name|window
operator|.
name|getX
argument_list|()
operator|+
name|scene
operator|.
name|getX
argument_list|()
operator|+
name|p
operator|.
name|getX
argument_list|()
argument_list|,
name|window
operator|.
name|getY
argument_list|()
operator|+
name|scene
operator|.
name|getY
argument_list|()
operator|+
name|p
operator|.
name|getY
argument_list|()
argument_list|)
decl_stmt|;
name|screenX
operator|=
name|location
operator|.
name|getX
argument_list|()
expr_stmt|;
name|sceneX
operator|=
name|p
operator|.
name|getX
argument_list|()
expr_stmt|;
name|screenY
operator|=
name|location
operator|.
name|getY
argument_list|()
expr_stmt|;
block|}
block|}
name|double
name|menuWidth
init|=
name|contextMenu
operator|.
name|prefWidth
argument_list|(
operator|-
literal|1
argument_list|)
decl_stmt|;
name|double
name|menuX
init|=
name|screenX
operator|-
operator|(
name|Properties
operator|.
name|IS_TOUCH_SUPPORTED
condition|?
operator|(
name|menuWidth
operator|/
literal|2
operator|)
else|:
literal|0
operator|)
decl_stmt|;
name|Screen
name|currentScreen
init|=
name|Screen
operator|.
name|getPrimary
argument_list|()
decl_stmt|;
name|Rectangle2D
name|bounds
init|=
name|currentScreen
operator|.
name|getBounds
argument_list|()
decl_stmt|;
if|if
condition|(
name|menuX
operator|<
name|bounds
operator|.
name|getMinX
argument_list|()
condition|)
block|{
name|textField
operator|.
name|getProperties
argument_list|()
operator|.
name|put
argument_list|(
literal|"CONTEXT_MENU_SCREEN_X"
argument_list|,
name|screenX
argument_list|)
expr_stmt|;
name|textField
operator|.
name|getProperties
argument_list|()
operator|.
name|put
argument_list|(
literal|"CONTEXT_MENU_SCENE_X"
argument_list|,
name|sceneX
argument_list|)
expr_stmt|;
name|contextMenu
operator|.
name|show
argument_list|(
name|textField
argument_list|,
name|bounds
operator|.
name|getMinX
argument_list|()
argument_list|,
name|screenY
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|screenX
operator|+
name|menuWidth
operator|>
name|bounds
operator|.
name|getMaxX
argument_list|()
condition|)
block|{
name|double
name|leftOver
init|=
name|menuWidth
operator|-
operator|(
name|bounds
operator|.
name|getMaxX
argument_list|()
operator|-
name|screenX
operator|)
decl_stmt|;
name|textField
operator|.
name|getProperties
argument_list|()
operator|.
name|put
argument_list|(
literal|"CONTEXT_MENU_SCREEN_X"
argument_list|,
name|screenX
argument_list|)
expr_stmt|;
name|textField
operator|.
name|getProperties
argument_list|()
operator|.
name|put
argument_list|(
literal|"CONTEXT_MENU_SCENE_X"
argument_list|,
name|sceneX
argument_list|)
expr_stmt|;
name|contextMenu
operator|.
name|show
argument_list|(
name|textField
argument_list|,
name|screenX
operator|-
name|leftOver
argument_list|,
name|screenY
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|textField
operator|.
name|getProperties
argument_list|()
operator|.
name|put
argument_list|(
literal|"CONTEXT_MENU_SCREEN_X"
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|textField
operator|.
name|getProperties
argument_list|()
operator|.
name|put
argument_list|(
literal|"CONTEXT_MENU_SCENE_X"
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|contextMenu
operator|.
name|show
argument_list|(
name|textField
argument_list|,
name|menuX
argument_list|,
name|screenY
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * @implNote taken from {@link com.sun.javafx.scene.control.behavior.TextAreaBehavior#contextMenuRequested(javafx.scene.input.ContextMenuEvent)}      */
DECL|method|showContextMenu (TextArea textArea, ContextMenu contextMenu, ContextMenuEvent e)
specifier|public
specifier|static
name|void
name|showContextMenu
parameter_list|(
name|TextArea
name|textArea
parameter_list|,
name|ContextMenu
name|contextMenu
parameter_list|,
name|ContextMenuEvent
name|e
parameter_list|)
block|{
name|double
name|screenX
init|=
name|e
operator|.
name|getScreenX
argument_list|()
decl_stmt|;
name|double
name|screenY
init|=
name|e
operator|.
name|getScreenY
argument_list|()
decl_stmt|;
name|double
name|sceneX
init|=
name|e
operator|.
name|getSceneX
argument_list|()
decl_stmt|;
name|TextAreaSkin
name|skin
init|=
operator|(
name|TextAreaSkin
operator|)
name|textArea
operator|.
name|getSkin
argument_list|()
decl_stmt|;
if|if
condition|(
name|Properties
operator|.
name|IS_TOUCH_SUPPORTED
condition|)
block|{
name|Point2D
name|menuPos
decl_stmt|;
if|if
condition|(
name|textArea
operator|.
name|getSelection
argument_list|()
operator|.
name|getLength
argument_list|()
operator|==
literal|0
condition|)
block|{
name|skin
operator|.
name|positionCaret
argument_list|(
name|skin
operator|.
name|getIndex
argument_list|(
name|e
operator|.
name|getX
argument_list|()
argument_list|,
name|e
operator|.
name|getY
argument_list|()
argument_list|)
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|menuPos
operator|=
name|skin
operator|.
name|getMenuPosition
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|menuPos
operator|=
name|skin
operator|.
name|getMenuPosition
argument_list|()
expr_stmt|;
if|if
condition|(
name|menuPos
operator|!=
literal|null
operator|&&
operator|(
name|menuPos
operator|.
name|getX
argument_list|()
operator|<=
literal|0
operator|||
name|menuPos
operator|.
name|getY
argument_list|()
operator|<=
literal|0
operator|)
condition|)
block|{
name|skin
operator|.
name|positionCaret
argument_list|(
name|skin
operator|.
name|getIndex
argument_list|(
name|e
operator|.
name|getX
argument_list|()
argument_list|,
name|e
operator|.
name|getY
argument_list|()
argument_list|)
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|menuPos
operator|=
name|skin
operator|.
name|getMenuPosition
argument_list|()
expr_stmt|;
block|}
block|}
if|if
condition|(
name|menuPos
operator|!=
literal|null
condition|)
block|{
name|Point2D
name|p
init|=
name|textArea
operator|.
name|localToScene
argument_list|(
name|menuPos
argument_list|)
decl_stmt|;
name|Scene
name|scene
init|=
name|textArea
operator|.
name|getScene
argument_list|()
decl_stmt|;
name|Window
name|window
init|=
name|scene
operator|.
name|getWindow
argument_list|()
decl_stmt|;
name|Point2D
name|location
init|=
operator|new
name|Point2D
argument_list|(
name|window
operator|.
name|getX
argument_list|()
operator|+
name|scene
operator|.
name|getX
argument_list|()
operator|+
name|p
operator|.
name|getX
argument_list|()
argument_list|,
name|window
operator|.
name|getY
argument_list|()
operator|+
name|scene
operator|.
name|getY
argument_list|()
operator|+
name|p
operator|.
name|getY
argument_list|()
argument_list|)
decl_stmt|;
name|screenX
operator|=
name|location
operator|.
name|getX
argument_list|()
expr_stmt|;
name|sceneX
operator|=
name|p
operator|.
name|getX
argument_list|()
expr_stmt|;
name|screenY
operator|=
name|location
operator|.
name|getY
argument_list|()
expr_stmt|;
block|}
block|}
name|double
name|menuWidth
init|=
name|contextMenu
operator|.
name|prefWidth
argument_list|(
operator|-
literal|1
argument_list|)
decl_stmt|;
name|double
name|menuX
init|=
name|screenX
operator|-
operator|(
name|Properties
operator|.
name|IS_TOUCH_SUPPORTED
condition|?
operator|(
name|menuWidth
operator|/
literal|2
operator|)
else|:
literal|0
operator|)
decl_stmt|;
name|Screen
name|currentScreen
init|=
name|Screen
operator|.
name|getPrimary
argument_list|()
decl_stmt|;
name|Rectangle2D
name|bounds
init|=
name|currentScreen
operator|.
name|getBounds
argument_list|()
decl_stmt|;
if|if
condition|(
name|menuX
operator|<
name|bounds
operator|.
name|getMinX
argument_list|()
condition|)
block|{
name|textArea
operator|.
name|getProperties
argument_list|()
operator|.
name|put
argument_list|(
literal|"CONTEXT_MENU_SCREEN_X"
argument_list|,
name|screenX
argument_list|)
expr_stmt|;
name|textArea
operator|.
name|getProperties
argument_list|()
operator|.
name|put
argument_list|(
literal|"CONTEXT_MENU_SCENE_X"
argument_list|,
name|sceneX
argument_list|)
expr_stmt|;
name|contextMenu
operator|.
name|show
argument_list|(
name|textArea
argument_list|,
name|bounds
operator|.
name|getMinX
argument_list|()
argument_list|,
name|screenY
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|screenX
operator|+
name|menuWidth
operator|>
name|bounds
operator|.
name|getMaxX
argument_list|()
condition|)
block|{
name|double
name|leftOver
init|=
name|menuWidth
operator|-
operator|(
name|bounds
operator|.
name|getMaxX
argument_list|()
operator|-
name|screenX
operator|)
decl_stmt|;
name|textArea
operator|.
name|getProperties
argument_list|()
operator|.
name|put
argument_list|(
literal|"CONTEXT_MENU_SCREEN_X"
argument_list|,
name|screenX
argument_list|)
expr_stmt|;
name|textArea
operator|.
name|getProperties
argument_list|()
operator|.
name|put
argument_list|(
literal|"CONTEXT_MENU_SCENE_X"
argument_list|,
name|sceneX
argument_list|)
expr_stmt|;
name|contextMenu
operator|.
name|show
argument_list|(
name|textArea
argument_list|,
name|screenX
operator|-
name|leftOver
argument_list|,
name|screenY
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|textArea
operator|.
name|getProperties
argument_list|()
operator|.
name|put
argument_list|(
literal|"CONTEXT_MENU_SCREEN_X"
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|textArea
operator|.
name|getProperties
argument_list|()
operator|.
name|put
argument_list|(
literal|"CONTEXT_MENU_SCENE_X"
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|contextMenu
operator|.
name|show
argument_list|(
name|textArea
argument_list|,
name|menuX
argument_list|,
name|screenY
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit
