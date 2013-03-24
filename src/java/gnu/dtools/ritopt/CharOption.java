begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|gnu.dtools.ritopt
package|package
name|gnu
operator|.
name|dtools
operator|.
name|ritopt
package|;
end_package

begin_comment
comment|/**  * CharOption.java  *  * Version:  *    $Id$  */
end_comment

begin_comment
comment|/**  * This class is used for options with character values.  *  *<hr>  *  *<pre>  * Copyright (C) Damian Ryan Eads, 2001. All Rights Reserved.  *  * ritopt is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.   * ritopt is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *  * You should have received a copy of the GNU General Public License  * along with ritopt; if not, write to the Free Software  * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA  *</pre>  *  * @author Damian Eads  */
end_comment

begin_class
DECL|class|CharOption
specifier|public
class|class
name|CharOption
extends|extends
name|Option
block|{
comment|/**      * The value of this character option.      */
DECL|field|value
specifier|private
name|char
name|value
decl_stmt|;
comment|/**      * Constructs a character option that is set to the space character.      */
DECL|method|CharOption ()
specifier|public
name|CharOption
parameter_list|()
block|{
name|this
argument_list|(
literal|' '
argument_list|)
expr_stmt|;
block|}
comment|/**      * Constructs a character option by copying the character option passed.      *      * @param op     The character option to copy.      */
DECL|method|CharOption ( CharOption op )
specifier|public
name|CharOption
parameter_list|(
name|CharOption
name|op
parameter_list|)
block|{
name|super
argument_list|(
name|op
argument_list|)
expr_stmt|;
name|op
operator|.
name|value
operator|=
name|op
operator|.
name|getValue
argument_list|()
expr_stmt|;
block|}
comment|/**      * Constructs a character option initialized with the value passed.      *      * @param value    The initial value of this character option.      */
DECL|method|CharOption ( char value )
specifier|public
name|CharOption
parameter_list|(
name|char
name|value
parameter_list|)
block|{
name|this
argument_list|(
name|value
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
comment|/**      * Constructs a character option initialized with the value and      * long option passed.      *      * @param value      The initial value of this character option.      * @param longOption The long option associated with character option.      */
DECL|method|CharOption ( char value, String longOption )
specifier|public
name|CharOption
parameter_list|(
name|char
name|value
parameter_list|,
name|String
name|longOption
parameter_list|)
block|{
name|this
argument_list|(
name|value
argument_list|,
name|longOption
argument_list|,
literal|'\0'
argument_list|)
expr_stmt|;
block|}
comment|/**      * Constructs a character option initialized with the value and      * short option passed.      *      * @param value       The initial value of this character option.      * @param shortOption The short option associated with this option.      */
DECL|method|CharOption ( char value, char shortOption )
specifier|public
name|CharOption
parameter_list|(
name|char
name|value
parameter_list|,
name|char
name|shortOption
parameter_list|)
block|{
name|this
argument_list|(
name|value
argument_list|,
literal|null
argument_list|,
name|shortOption
argument_list|)
expr_stmt|;
block|}
comment|/**      * Constructs a character option initialized with the value, short      * and long option passed.      *      * @param shortOption The short option associated with this option.      * @param longOption  The long option associated with this option.      * @param value       The initial value of this character option.      */
DECL|method|CharOption ( char value, String longOption, char shortOption )
specifier|public
name|CharOption
parameter_list|(
name|char
name|value
parameter_list|,
name|String
name|longOption
parameter_list|,
name|char
name|shortOption
parameter_list|)
block|{
name|super
argument_list|(
name|longOption
argument_list|,
name|shortOption
argument_list|)
expr_stmt|;
name|this
operator|.
name|value
operator|=
name|value
expr_stmt|;
block|}
comment|/**      * Return the value as an object.      *      * @return This value as an option.      */
DECL|method|getObject ()
specifier|public
name|Object
name|getObject
parameter_list|()
block|{
return|return
operator|new
name|Character
argument_list|(
name|value
argument_list|)
return|;
block|}
comment|/**      * Modify this option based on a string representation.      *      * @param     value String representation of the object.      * @exception OptionModificationException Thrown if an error occurs      *                                  during modification of an option.      */
DECL|method|modify ( String value )
specifier|public
name|void
name|modify
parameter_list|(
name|String
name|value
parameter_list|)
throws|throws
name|OptionModificationException
block|{
name|this
operator|.
name|value
operator|=
operator|(
name|value
operator|.
name|length
argument_list|()
operator|>
literal|0
operator|)
condition|?
name|value
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
else|:
literal|' '
expr_stmt|;
block|}
comment|/**      * Modify this option based on a string representation.      *      * @param     value String representation of the object.      * @exception OptionModificationException Thrown if an error occurs      *                                  during modification of an option.      */
DECL|method|setValue ( String value )
specifier|public
name|void
name|setValue
parameter_list|(
name|String
name|value
parameter_list|)
throws|throws
name|OptionModificationException
block|{
name|modify
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
comment|/**      * Modify this option using a character value.      *      * @param     value A character value.      */
DECL|method|setValue ( char value )
specifier|public
name|void
name|setValue
parameter_list|(
name|char
name|value
parameter_list|)
block|{
name|this
operator|.
name|value
operator|=
name|value
expr_stmt|;
block|}
comment|/**      * Return this option as a character.      *      * @return This option as a character.      */
DECL|method|getValue ()
specifier|public
name|char
name|getValue
parameter_list|()
block|{
return|return
name|value
return|;
block|}
comment|/**      * Return this option as a string.      *      * @return This option as a string.      */
DECL|method|getStringValue ()
specifier|public
name|String
name|getStringValue
parameter_list|()
block|{
return|return
literal|""
operator|+
name|value
return|;
block|}
comment|/**      * Returns the type name of this option. For a ByteOption, "BYTE"      * is returned.      *      * @return The type name of this option.      */
DECL|method|getTypeName ()
specifier|public
name|String
name|getTypeName
parameter_list|()
block|{
return|return
literal|"CHAR"
return|;
block|}
comment|/**      * Returns a string representation of this object.      *      * @return A string representation of this object.      */
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getStringValue
argument_list|()
return|;
block|}
block|}
end_class

begin_comment
comment|/** CharOption */
end_comment

end_unit

