begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2016 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.gui.keyboard
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|keyboard
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
name|SimpleStringProperty
import|;
end_import

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
name|google
operator|.
name|common
operator|.
name|base
operator|.
name|CaseFormat
import|;
end_import

begin_comment
comment|/**  * This class represents a view model for objects of the KeyBinding  * class. It has two properties representing the localized name of an  * action and its key bind. It can also represent a key binding category  * instead of a key bind itself.  *  */
end_comment

begin_class
DECL|class|KeyBindingViewModel
specifier|public
class|class
name|KeyBindingViewModel
block|{
DECL|field|keyBinding
specifier|private
name|KeyBinding
name|keyBinding
init|=
literal|null
decl_stmt|;
DECL|field|realBinding
specifier|private
name|String
name|realBinding
init|=
literal|""
decl_stmt|;
DECL|field|displayName
specifier|private
specifier|final
name|SimpleStringProperty
name|displayName
init|=
operator|new
name|SimpleStringProperty
argument_list|()
decl_stmt|;
DECL|field|shownBinding
specifier|private
specifier|final
name|SimpleStringProperty
name|shownBinding
init|=
operator|new
name|SimpleStringProperty
argument_list|()
decl_stmt|;
DECL|field|category
specifier|private
specifier|final
name|KeyBindingCategory
name|category
decl_stmt|;
DECL|method|KeyBindingViewModel (KeyBinding keyBinding, String binding)
specifier|public
name|KeyBindingViewModel
parameter_list|(
name|KeyBinding
name|keyBinding
parameter_list|,
name|String
name|binding
parameter_list|)
block|{
name|this
argument_list|(
name|keyBinding
operator|.
name|getCategory
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|keyBinding
operator|=
name|keyBinding
expr_stmt|;
name|setDisplayName
argument_list|()
expr_stmt|;
name|setBinding
argument_list|(
name|binding
argument_list|)
expr_stmt|;
block|}
DECL|method|KeyBindingViewModel (KeyBindingCategory category)
specifier|public
name|KeyBindingViewModel
parameter_list|(
name|KeyBindingCategory
name|category
parameter_list|)
block|{
name|this
operator|.
name|category
operator|=
name|category
expr_stmt|;
name|setDisplayName
argument_list|()
expr_stmt|;
block|}
DECL|method|getKeyBinding ()
specifier|public
name|KeyBinding
name|getKeyBinding
parameter_list|()
block|{
return|return
name|keyBinding
return|;
block|}
DECL|method|shownBindingProperty ()
specifier|public
name|StringProperty
name|shownBindingProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|shownBinding
return|;
block|}
DECL|method|getBinding ()
specifier|public
name|String
name|getBinding
parameter_list|()
block|{
return|return
name|realBinding
return|;
block|}
DECL|method|setBinding (String bind)
specifier|private
name|void
name|setBinding
parameter_list|(
name|String
name|bind
parameter_list|)
block|{
name|this
operator|.
name|realBinding
operator|=
name|bind
expr_stmt|;
name|String
index|[]
name|parts
init|=
name|bind
operator|.
name|split
argument_list|(
literal|" "
argument_list|)
decl_stmt|;
name|String
name|displayBind
init|=
literal|""
decl_stmt|;
for|for
control|(
name|String
name|part
range|:
name|parts
control|)
block|{
name|displayBind
operator|+=
name|CaseFormat
operator|.
name|LOWER_CAMEL
operator|.
name|to
argument_list|(
name|CaseFormat
operator|.
name|UPPER_CAMEL
argument_list|,
name|part
argument_list|)
operator|+
literal|" "
expr_stmt|;
block|}
name|this
operator|.
name|shownBinding
operator|.
name|set
argument_list|(
name|displayBind
operator|.
name|trim
argument_list|()
operator|.
name|replace
argument_list|(
literal|" "
argument_list|,
literal|" + "
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|setDisplayName ()
specifier|private
name|void
name|setDisplayName
parameter_list|()
block|{
name|this
operator|.
name|displayName
operator|.
name|set
argument_list|(
operator|(
name|keyBinding
operator|==
literal|null
operator|)
condition|?
name|this
operator|.
name|category
operator|.
name|getName
argument_list|()
else|:
name|keyBinding
operator|.
name|getLocalization
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|nameProperty ()
specifier|public
name|StringProperty
name|nameProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|displayName
return|;
block|}
DECL|method|isCategory ()
specifier|public
name|boolean
name|isCategory
parameter_list|()
block|{
return|return
operator|(
name|keyBinding
operator|==
literal|null
operator|)
condition|?
literal|true
else|:
literal|false
return|;
block|}
comment|/**      * Sets a a new key bind to this objects key binding object if      * the given key event is a valid combination of keys.      *      * @param evt as KeyEvent      * @return true if the KeyEvent is a valid binding, false else      */
DECL|method|setNewBinding (KeyEvent evt)
specifier|public
name|boolean
name|setNewBinding
parameter_list|(
name|KeyEvent
name|evt
parameter_list|)
block|{
comment|// validate the shortcut is no modifier key
name|KeyCode
name|code
init|=
name|evt
operator|.
name|getCode
argument_list|()
decl_stmt|;
if|if
condition|(
name|code
operator|.
name|isModifierKey
argument_list|()
operator|||
operator|(
name|code
operator|==
name|KeyCode
operator|.
name|BACK_SPACE
operator|)
operator|||
operator|(
name|code
operator|==
name|KeyCode
operator|.
name|SPACE
operator|)
operator|||
operator|(
name|code
operator|==
name|KeyCode
operator|.
name|TAB
operator|)
operator|||
operator|(
name|code
operator|==
name|KeyCode
operator|.
name|ENTER
operator|)
operator|||
operator|(
name|code
operator|==
name|KeyCode
operator|.
name|UNDEFINED
operator|)
condition|)
block|{
return|return
literal|false
return|;
block|}
comment|// gather the pressed modifier keys
name|String
name|modifiers
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|evt
operator|.
name|isControlDown
argument_list|()
condition|)
block|{
name|modifiers
operator|=
literal|"ctrl "
expr_stmt|;
block|}
if|if
condition|(
name|evt
operator|.
name|isShiftDown
argument_list|()
condition|)
block|{
name|modifiers
operator|+=
literal|"shift "
expr_stmt|;
block|}
if|if
condition|(
name|evt
operator|.
name|isAltDown
argument_list|()
condition|)
block|{
name|modifiers
operator|+=
literal|"alt "
expr_stmt|;
block|}
comment|// if no modifier keys are pressed, only special keys can be shortcuts
if|if
condition|(
name|modifiers
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
operator|(
name|code
operator|.
name|isFunctionKey
argument_list|()
operator|||
operator|(
name|code
operator|==
name|KeyCode
operator|.
name|ESCAPE
operator|)
operator|||
operator|(
name|code
operator|==
name|KeyCode
operator|.
name|DELETE
operator|)
operator|)
condition|)
block|{
return|return
literal|false
return|;
block|}
block|}
name|String
name|newShortcut
init|=
name|modifiers
operator|+
name|code
decl_stmt|;
name|setBinding
argument_list|(
name|newShortcut
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
comment|/**      * This method will reset the key bind of this models KeyBinding object to it's default bind      *      * @param keyBindingRepository as KeyBindingRepository      */
DECL|method|resetToDefault (KeyBindingRepository keyBindingRepository)
specifier|public
name|void
name|resetToDefault
parameter_list|(
name|KeyBindingRepository
name|keyBindingRepository
parameter_list|)
block|{
if|if
condition|(
operator|!
name|isCategory
argument_list|()
condition|)
block|{
name|String
name|key
init|=
name|getKeyBinding
argument_list|()
operator|.
name|getKey
argument_list|()
decl_stmt|;
name|keyBindingRepository
operator|.
name|resetToDefault
argument_list|(
name|key
argument_list|)
expr_stmt|;
name|setBinding
argument_list|(
name|keyBindingRepository
operator|.
name|get
argument_list|(
name|key
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

