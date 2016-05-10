begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
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

begin_class
DECL|class|KeyBindingRepository
specifier|public
class|class
name|KeyBindingRepository
block|{
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
DECL|method|KeyBindingRepository ()
specifier|public
name|KeyBindingRepository
parameter_list|()
block|{
name|bindings
operator|=
operator|new
name|TreeMap
argument_list|<>
argument_list|(
parameter_list|(
name|k1
parameter_list|,
name|k2
parameter_list|)
lambda|->
name|k1
operator|.
name|getLocalization
argument_list|()
operator|.
name|compareTo
argument_list|(
name|k2
operator|.
name|getLocalization
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
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
name|bindings
operator|.
name|put
argument_list|(
name|keyBinding
argument_list|,
name|keyBinding
operator|.
name|getDefaultBinding
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|KeyBindingRepository (SortedMap<KeyBinding, String> bindings)
specifier|public
name|KeyBindingRepository
parameter_list|(
name|SortedMap
argument_list|<
name|KeyBinding
argument_list|,
name|String
argument_list|>
name|bindings
parameter_list|)
block|{
name|this
operator|.
name|bindings
operator|=
name|bindings
expr_stmt|;
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
name|getKeyBinding
argument_list|(
name|key
argument_list|)
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
name|getDefaultBinding
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
DECL|method|overwriteBindings (SortedMap<KeyBinding, String> newBindings)
specifier|public
name|void
name|overwriteBindings
parameter_list|(
name|SortedMap
argument_list|<
name|KeyBinding
argument_list|,
name|String
argument_list|>
name|newBindings
parameter_list|)
block|{
name|bindings
operator|.
name|clear
argument_list|()
expr_stmt|;
name|newBindings
operator|.
name|forEach
argument_list|(
name|this
operator|::
name|put
argument_list|)
expr_stmt|;
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
name|getKeyBinding
argument_list|(
name|key
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|binding
lambda|->
name|bindings
operator|.
name|put
argument_list|(
name|binding
argument_list|,
name|value
argument_list|)
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
name|bindings
operator|.
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
name|getKey
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
DECL|method|getKeyBinding (KeyBinding key)
specifier|private
name|Optional
argument_list|<
name|KeyBinding
argument_list|>
name|getKeyBinding
parameter_list|(
name|KeyBinding
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
name|getDefaultBinding
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
name|getDefaultBinding
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
block|}
end_class

end_unit

