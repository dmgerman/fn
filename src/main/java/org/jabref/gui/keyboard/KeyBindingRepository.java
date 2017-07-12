begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.keyboard
package|package
name|org
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
name|java
operator|.
name|awt
operator|.
name|AWTError
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|HeadlessException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Toolkit
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|InputEvent
import|;
end_import

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
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Comparator
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
name|Optional
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|SortedMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|KeyStroke
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
name|KeyCombination
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
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_class
DECL|class|KeyBindingRepository
specifier|public
class|class
name|KeyBindingRepository
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|KeyBindingRepository
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * sorted by localization      */
DECL|field|bindings
specifier|private
specifier|final
name|SortedMap
argument_list|<
name|KeyBinding
argument_list|,
name|String
argument_list|>
name|bindings
decl_stmt|;
DECL|field|shortcutMask
specifier|private
name|int
name|shortcutMask
init|=
operator|-
literal|1
decl_stmt|;
DECL|method|KeyBindingRepository ()
specifier|public
name|KeyBindingRepository
parameter_list|()
block|{
name|this
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|KeyBindingRepository (List<String> bindNames, List<String> bindings)
specifier|public
name|KeyBindingRepository
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|bindNames
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|bindings
parameter_list|)
block|{
name|this
operator|.
name|bindings
operator|=
operator|new
name|TreeMap
argument_list|<>
argument_list|(
name|Comparator
operator|.
name|comparing
argument_list|(
name|KeyBinding
operator|::
name|getLocalization
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
operator|(
name|bindNames
operator|.
name|isEmpty
argument_list|()
operator|)
operator|||
operator|(
name|bindings
operator|.
name|isEmpty
argument_list|()
operator|)
operator|||
operator|(
name|bindNames
operator|.
name|size
argument_list|()
operator|!=
name|bindings
operator|.
name|size
argument_list|()
operator|)
condition|)
block|{
comment|// Use default key bindings
for|for
control|(
name|KeyBinding
name|keyBinding
range|:
name|KeyBinding
operator|.
name|values
argument_list|()
control|)
block|{
name|put
argument_list|(
name|keyBinding
argument_list|,
name|keyBinding
operator|.
name|getDefaultKeyBinding
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|bindNames
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|put
argument_list|(
name|bindNames
operator|.
name|get
argument_list|(
name|i
argument_list|)
argument_list|,
name|bindings
operator|.
name|get
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * Check if the given keyCombination equals the given keyEvent      *      * @param combination as KeyCombination      * @param keyEvent    as KeEvent      * @return true if matching, else false      */
DECL|method|checkKeyCombinationEquality (KeyCombination combination, KeyEvent keyEvent)
specifier|public
specifier|static
name|boolean
name|checkKeyCombinationEquality
parameter_list|(
name|KeyCombination
name|combination
parameter_list|,
name|KeyEvent
name|keyEvent
parameter_list|)
block|{
name|KeyCode
name|code
init|=
name|keyEvent
operator|.
name|getCode
argument_list|()
decl_stmt|;
if|if
condition|(
name|code
operator|==
name|KeyCode
operator|.
name|UNDEFINED
condition|)
block|{
return|return
literal|false
return|;
block|}
return|return
name|combination
operator|.
name|match
argument_list|(
name|keyEvent
argument_list|)
return|;
block|}
DECL|method|get (KeyBinding key)
specifier|public
name|Optional
argument_list|<
name|String
argument_list|>
name|get
parameter_list|(
name|KeyBinding
name|key
parameter_list|)
block|{
return|return
name|Optional
operator|.
name|ofNullable
argument_list|(
name|bindings
operator|.
name|get
argument_list|(
name|key
argument_list|)
argument_list|)
return|;
block|}
DECL|method|get (String key)
specifier|public
name|String
name|get
parameter_list|(
name|String
name|key
parameter_list|)
block|{
name|Optional
argument_list|<
name|KeyBinding
argument_list|>
name|keyBinding
init|=
name|getKeyBinding
argument_list|(
name|key
argument_list|)
decl_stmt|;
name|Optional
argument_list|<
name|String
argument_list|>
name|result
init|=
name|keyBinding
operator|.
name|flatMap
argument_list|(
name|k
lambda|->
name|Optional
operator|.
name|ofNullable
argument_list|(
name|bindings
operator|.
name|get
argument_list|(
name|k
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|result
operator|.
name|isPresent
argument_list|()
condition|)
block|{
return|return
name|result
operator|.
name|get
argument_list|()
return|;
block|}
elseif|else
if|if
condition|(
name|keyBinding
operator|.
name|isPresent
argument_list|()
condition|)
block|{
return|return
name|keyBinding
operator|.
name|get
argument_list|()
operator|.
name|getDefaultKeyBinding
argument_list|()
return|;
block|}
else|else
block|{
return|return
literal|"Not associated"
return|;
block|}
block|}
comment|/**      * Returns the HashMap containing all key bindings.      */
DECL|method|getKeyBindings ()
specifier|public
name|SortedMap
argument_list|<
name|KeyBinding
argument_list|,
name|String
argument_list|>
name|getKeyBindings
parameter_list|()
block|{
return|return
operator|new
name|TreeMap
argument_list|<>
argument_list|(
name|bindings
argument_list|)
return|;
block|}
DECL|method|put (KeyBinding key, String value)
specifier|public
name|void
name|put
parameter_list|(
name|KeyBinding
name|key
parameter_list|,
name|String
name|value
parameter_list|)
block|{
name|bindings
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
DECL|method|put (String key, String value)
specifier|public
name|void
name|put
parameter_list|(
name|String
name|key
parameter_list|,
name|String
name|value
parameter_list|)
block|{
name|getKeyBinding
argument_list|(
name|key
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|binding
lambda|->
name|put
argument_list|(
name|binding
argument_list|,
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|getKeyBinding (String key)
specifier|private
name|Optional
argument_list|<
name|KeyBinding
argument_list|>
name|getKeyBinding
parameter_list|(
name|String
name|key
parameter_list|)
block|{
return|return
name|Arrays
operator|.
name|stream
argument_list|(
name|KeyBinding
operator|.
name|values
argument_list|()
argument_list|)
operator|.
name|filter
argument_list|(
name|b
lambda|->
name|b
operator|.
name|getConstant
argument_list|()
operator|.
name|equals
argument_list|(
name|key
argument_list|)
argument_list|)
operator|.
name|findFirst
argument_list|()
return|;
block|}
DECL|method|resetToDefault (String key)
specifier|public
name|void
name|resetToDefault
parameter_list|(
name|String
name|key
parameter_list|)
block|{
name|getKeyBinding
argument_list|(
name|key
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|b
lambda|->
name|bindings
operator|.
name|put
argument_list|(
name|b
argument_list|,
name|b
operator|.
name|getDefaultKeyBinding
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|resetToDefault ()
specifier|public
name|void
name|resetToDefault
parameter_list|()
block|{
name|bindings
operator|.
name|forEach
argument_list|(
parameter_list|(
name|b
parameter_list|,
name|s
parameter_list|)
lambda|->
name|bindings
operator|.
name|put
argument_list|(
name|b
argument_list|,
name|b
operator|.
name|getDefaultKeyBinding
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|size ()
specifier|public
name|int
name|size
parameter_list|()
block|{
return|return
name|this
operator|.
name|bindings
operator|.
name|size
argument_list|()
return|;
block|}
DECL|method|mapToKeyBinding (KeyEvent keyEvent)
specifier|public
name|Optional
argument_list|<
name|KeyBinding
argument_list|>
name|mapToKeyBinding
parameter_list|(
name|KeyEvent
name|keyEvent
parameter_list|)
block|{
for|for
control|(
name|KeyBinding
name|binding
range|:
name|KeyBinding
operator|.
name|values
argument_list|()
control|)
block|{
if|if
condition|(
name|checkKeyCombinationEquality
argument_list|(
name|binding
argument_list|,
name|keyEvent
argument_list|)
condition|)
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|binding
argument_list|)
return|;
block|}
block|}
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
DECL|method|mapToKeyBinding (java.awt.event.KeyEvent keyEvent)
specifier|public
name|Optional
argument_list|<
name|KeyBinding
argument_list|>
name|mapToKeyBinding
parameter_list|(
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|KeyEvent
name|keyEvent
parameter_list|)
block|{
name|Optional
argument_list|<
name|KeyCode
argument_list|>
name|keyCode
init|=
name|Arrays
operator|.
name|stream
argument_list|(
name|KeyCode
operator|.
name|values
argument_list|()
argument_list|)
operator|.
name|filter
argument_list|(
name|k
lambda|->
name|k
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|keyEvent
operator|.
name|getKeyText
argument_list|(
name|keyEvent
operator|.
name|getKeyCode
argument_list|()
argument_list|)
argument_list|)
argument_list|)
operator|.
name|findFirst
argument_list|()
decl_stmt|;
if|if
condition|(
name|keyCode
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|KeyEvent
name|event
init|=
operator|new
name|KeyEvent
argument_list|(
name|keyEvent
operator|.
name|getSource
argument_list|()
argument_list|,
literal|null
argument_list|,
name|KeyEvent
operator|.
name|KEY_PRESSED
argument_list|,
literal|""
argument_list|,
literal|""
argument_list|,
name|keyCode
operator|.
name|get
argument_list|()
argument_list|,
name|keyEvent
operator|.
name|isShiftDown
argument_list|()
argument_list|,
name|keyEvent
operator|.
name|isControlDown
argument_list|()
argument_list|,
name|keyEvent
operator|.
name|isAltDown
argument_list|()
argument_list|,
name|keyEvent
operator|.
name|isMetaDown
argument_list|()
argument_list|)
decl_stmt|;
return|return
name|mapToKeyBinding
argument_list|(
name|event
argument_list|)
return|;
block|}
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
comment|/**      * Returns the KeyStroke for this binding, as defined by the defaults, or in the Preferences.      */
DECL|method|getKey (KeyBinding bindName)
specifier|public
name|KeyStroke
name|getKey
parameter_list|(
name|KeyBinding
name|bindName
parameter_list|)
block|{
name|String
name|s
init|=
name|get
argument_list|(
name|bindName
operator|.
name|getConstant
argument_list|()
argument_list|)
decl_stmt|;
name|s
operator|=
name|s
operator|.
name|replace
argument_list|(
literal|"+"
argument_list|,
literal|" "
argument_list|)
expr_stmt|;
comment|//swing needs the keys without pluses but whitespace between the modifiers
if|if
condition|(
name|OS
operator|.
name|OS_X
condition|)
block|{
return|return
name|getKeyForMac
argument_list|(
name|KeyStroke
operator|.
name|getKeyStroke
argument_list|(
name|s
argument_list|)
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|KeyStroke
operator|.
name|getKeyStroke
argument_list|(
name|s
argument_list|)
return|;
block|}
block|}
DECL|method|getKeyCombination (KeyBinding bindName)
specifier|private
name|KeyCombination
name|getKeyCombination
parameter_list|(
name|KeyBinding
name|bindName
parameter_list|)
block|{
name|String
name|binding
init|=
name|get
argument_list|(
name|bindName
operator|.
name|getConstant
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|OS
operator|.
name|OS_X
condition|)
block|{
name|binding
operator|=
name|binding
operator|.
name|replace
argument_list|(
literal|"ctrl"
argument_list|,
literal|"meta"
argument_list|)
expr_stmt|;
block|}
return|return
name|KeyCombination
operator|.
name|valueOf
argument_list|(
name|binding
argument_list|)
return|;
block|}
comment|/**      * Check if the given KeyBinding equals the given keyEvent      *      * @param binding as KeyBinding      * @param keyEvent as KeEvent      * @return true if matching, else false      */
DECL|method|checkKeyCombinationEquality (KeyBinding binding, KeyEvent keyEvent)
specifier|public
name|boolean
name|checkKeyCombinationEquality
parameter_list|(
name|KeyBinding
name|binding
parameter_list|,
name|KeyEvent
name|keyEvent
parameter_list|)
block|{
name|KeyCombination
name|keyCombination
init|=
name|getKeyCombination
argument_list|(
name|binding
argument_list|)
decl_stmt|;
return|return
name|checkKeyCombinationEquality
argument_list|(
name|keyCombination
argument_list|,
name|keyEvent
argument_list|)
return|;
block|}
comment|/**      * Returns the KeyStroke for this binding, as defined by the defaults, or in the Preferences, but adapted for Mac      * users, with the Command key preferred instead of Control.      * TODO: Move to OS.java? Or replace with portable Java key codes, i.e. KeyEvent      */
DECL|method|getKeyForMac (KeyStroke ks)
specifier|private
name|KeyStroke
name|getKeyForMac
parameter_list|(
name|KeyStroke
name|ks
parameter_list|)
block|{
if|if
condition|(
name|ks
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
name|int
name|keyCode
init|=
name|ks
operator|.
name|getKeyCode
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|ks
operator|.
name|getModifiers
argument_list|()
operator|&
name|InputEvent
operator|.
name|CTRL_MASK
operator|)
operator|==
literal|0
condition|)
block|{
return|return
name|ks
return|;
block|}
else|else
block|{
name|int
name|modifiers
init|=
literal|0
decl_stmt|;
if|if
condition|(
operator|(
name|ks
operator|.
name|getModifiers
argument_list|()
operator|&
name|InputEvent
operator|.
name|SHIFT_MASK
operator|)
operator|!=
literal|0
condition|)
block|{
name|modifiers
operator|=
name|modifiers
operator||
name|InputEvent
operator|.
name|SHIFT_MASK
expr_stmt|;
block|}
if|if
condition|(
operator|(
name|ks
operator|.
name|getModifiers
argument_list|()
operator|&
name|InputEvent
operator|.
name|ALT_MASK
operator|)
operator|!=
literal|0
condition|)
block|{
name|modifiers
operator|=
name|modifiers
operator||
name|InputEvent
operator|.
name|ALT_MASK
expr_stmt|;
block|}
if|if
condition|(
name|shortcutMask
operator|==
operator|-
literal|1
condition|)
block|{
try|try
block|{
name|shortcutMask
operator|=
name|Toolkit
operator|.
name|getDefaultToolkit
argument_list|()
operator|.
name|getMenuShortcutKeyMask
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|AWTError
decl||
name|HeadlessException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Problem geting shortcut mask"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|KeyStroke
operator|.
name|getKeyStroke
argument_list|(
name|keyCode
argument_list|,
name|shortcutMask
operator|+
name|modifiers
argument_list|)
return|;
block|}
block|}
DECL|method|getBindNames ()
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getBindNames
parameter_list|()
block|{
return|return
name|bindings
operator|.
name|keySet
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|KeyBinding
operator|::
name|getConstant
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
return|;
block|}
DECL|method|getBindings ()
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getBindings
parameter_list|()
block|{
return|return
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|bindings
operator|.
name|values
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

