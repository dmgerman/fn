begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.customjfx
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|customjfx
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|InputMethodEvent
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|Field
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|embed
operator|.
name|swing
operator|.
name|JFXPanel
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
name|customjfx
operator|.
name|support
operator|.
name|InputMethodSupport
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
name|embed
operator|.
name|EmbeddedSceneInterface
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

begin_comment
comment|/***  * WARNING: THIS IS A CUSTOM HACK TO PREVENT A BUG WITH ACCENTED CHARACTERS PRODUCING AN NPE IN LINUX</br>  * So far the bug has only been resolved in openjfx10:<a href="https://bugs.openjdk.java.net/browse/JDK-8185792">https://bugs.openjdk.java.net/browse/JDK-8185792</a>  *  */
end_comment

begin_class
DECL|class|CustomJFXPanel
specifier|public
class|class
name|CustomJFXPanel
extends|extends
name|JFXPanel
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
name|CustomJFXPanel
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|scenePeerField
specifier|private
name|Field
name|scenePeerField
init|=
literal|null
decl_stmt|;
DECL|method|CustomJFXPanel ()
specifier|public
name|CustomJFXPanel
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
try|try
block|{
name|scenePeerField
operator|=
name|this
operator|.
name|getClass
argument_list|()
operator|.
name|getSuperclass
argument_list|()
operator|.
name|getDeclaredField
argument_list|(
literal|"scenePeer"
argument_list|)
expr_stmt|;
name|scenePeerField
operator|.
name|setAccessible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NoSuchFieldException
decl||
name|SecurityException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Could not access scenePeer Field"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|processInputMethodEvent (InputMethodEvent e)
specifier|protected
name|void
name|processInputMethodEvent
parameter_list|(
name|InputMethodEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|.
name|getID
argument_list|()
operator|==
name|InputMethodEvent
operator|.
name|INPUT_METHOD_TEXT_CHANGED
condition|)
block|{
name|sendInputMethodEventToFX
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|sendInputMethodEventToFX (InputMethodEvent e)
specifier|private
name|void
name|sendInputMethodEventToFX
parameter_list|(
name|InputMethodEvent
name|e
parameter_list|)
block|{
name|String
name|t
init|=
name|InputMethodSupport
operator|.
name|getTextForEvent
argument_list|(
name|e
argument_list|)
decl_stmt|;
name|int
name|insertionIndex
init|=
literal|0
decl_stmt|;
if|if
condition|(
name|e
operator|.
name|getCaret
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|insertionIndex
operator|=
name|e
operator|.
name|getCaret
argument_list|()
operator|.
name|getInsertionIndex
argument_list|()
expr_stmt|;
block|}
name|EmbeddedSceneInterface
name|myScencePeer
init|=
literal|null
decl_stmt|;
try|try
block|{
comment|//the variable must be named different to the original, otherwise reflection does not find the right ones
name|myScencePeer
operator|=
operator|(
name|EmbeddedSceneInterface
operator|)
name|scenePeerField
operator|.
name|get
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
decl||
name|IllegalAccessException
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Could not access scenePeer Field"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
name|myScencePeer
operator|.
name|inputMethodEvent
argument_list|(
name|javafx
operator|.
name|scene
operator|.
name|input
operator|.
name|InputMethodEvent
operator|.
name|INPUT_METHOD_TEXT_CHANGED
argument_list|,
name|InputMethodSupport
operator|.
name|inputMethodEventComposed
argument_list|(
name|t
argument_list|,
name|e
operator|.
name|getCommittedCharacterCount
argument_list|()
argument_list|)
argument_list|,
name|t
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|e
operator|.
name|getCommittedCharacterCount
argument_list|()
argument_list|)
argument_list|,
name|insertionIndex
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

