begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.actions
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|actions
package|;
end_package

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|AbstractAction
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|Action
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|Icon
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

begin_comment
comment|/**  * This class extends {@link AbstractAction} with the ability to set  * the mnemonic key based on a '&' character inserted in front of  * the desired mnemonic letter. This is done by setting the action's  * name using putValue(NAME, actionname).  * This facilitates localized mnemonics.  */
end_comment

begin_class
DECL|class|MnemonicAwareAction
specifier|public
specifier|abstract
class|class
name|MnemonicAwareAction
extends|extends
name|AbstractAction
block|{
DECL|method|MnemonicAwareAction ()
specifier|public
name|MnemonicAwareAction
parameter_list|()
block|{}
DECL|method|MnemonicAwareAction (Icon icon)
specifier|public
name|MnemonicAwareAction
parameter_list|(
name|Icon
name|icon
parameter_list|)
block|{
if|if
condition|(
name|icon
operator|instanceof
name|IconTheme
operator|.
name|FontBasedIcon
condition|)
block|{
name|putValue
argument_list|(
name|Action
operator|.
name|SMALL_ICON
argument_list|,
operator|(
operator|(
name|IconTheme
operator|.
name|FontBasedIcon
operator|)
name|icon
operator|)
operator|.
name|createSmallIcon
argument_list|()
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|LARGE_ICON_KEY
argument_list|,
name|icon
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|putValue
argument_list|(
name|Action
operator|.
name|SMALL_ICON
argument_list|,
name|icon
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|putValue (String key, Object value)
specifier|public
name|void
name|putValue
parameter_list|(
name|String
name|key
parameter_list|,
name|Object
name|value
parameter_list|)
block|{
if|if
condition|(
name|key
operator|.
name|equals
argument_list|(
name|Action
operator|.
name|NAME
argument_list|)
condition|)
block|{
name|String
name|name
init|=
name|value
operator|.
name|toString
argument_list|()
decl_stmt|;
name|int
name|i
init|=
name|name
operator|.
name|indexOf
argument_list|(
literal|'&'
argument_list|)
decl_stmt|;
if|if
condition|(
name|i
operator|>=
literal|0
condition|)
block|{
name|char
name|mnemonic
init|=
name|Character
operator|.
name|toUpperCase
argument_list|(
name|name
operator|.
name|charAt
argument_list|(
name|i
operator|+
literal|1
argument_list|)
argument_list|)
decl_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|MNEMONIC_KEY
argument_list|,
operator|(
name|int
operator|)
name|mnemonic
argument_list|)
expr_stmt|;
name|value
operator|=
name|name
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|i
argument_list|)
operator|+
name|name
operator|.
name|substring
argument_list|(
name|i
operator|+
literal|1
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|value
operator|=
name|name
expr_stmt|;
block|}
block|}
name|super
operator|.
name|putValue
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
