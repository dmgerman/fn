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
comment|/**  * ByteOption.java  *  * Version:  *     $Id$  */
end_comment

begin_comment
comment|/**  * This class is used for options with byte values.  *  *<hr>  *  *<pre>  * Copyright (C) Damian Ryan Eads, 2001. All Rights Reserved.  *  * ritopt is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.   * ritopt is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *  * You should have received a copy of the GNU General Public License  * along with ritopt; if not, write to the Free Software  * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA  *</pre>  *  * @author Damian Eads  */
end_comment

begin_class
DECL|class|ByteOption
specifier|public
class|class
name|ByteOption
extends|extends
name|Option
block|{
comment|/**      * The value of this byte option.      */
DECL|field|value
specifier|private
name|byte
name|value
decl_stmt|;
comment|/**      * Constructs a byte option that is set to the null character.      */
DECL|method|ByteOption ()
specifier|public
name|ByteOption
parameter_list|()
block|{
name|this
argument_list|(
operator|(
name|byte
operator|)
literal|0
argument_list|)
expr_stmt|;
block|}
comment|/**      * Constructs a byte option by copying the byte option passed.      *      * @param op     The byte option to copy.      */
DECL|method|ByteOption ( ByteOption op )
specifier|public
name|ByteOption
parameter_list|(
name|ByteOption
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
comment|/**      * Constructs a byte option initialized with the value passed.      *      * @param value    The initial value of this byte option.      */
DECL|method|ByteOption ( byte value )
specifier|public
name|ByteOption
parameter_list|(
name|byte
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
comment|/**      * Constructs a byte option initialized with the value and      * long option passed.      *      * @param value      The initial value of this byte option.      * @param longOption The long option associated with this option.      */
DECL|method|ByteOption ( byte value, String longOption )
specifier|public
name|ByteOption
parameter_list|(
name|byte
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
comment|/**      * Constructs a byte option initialized with the value and short      * option passed.      *      * @param shortOption The short option associated with this option.      * @param value       The initial value of this byte option.      */
DECL|method|ByteOption ( byte value, char shortOption )
specifier|public
name|ByteOption
parameter_list|(
name|byte
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
comment|/**      * Constructs a byte option initialized with the value, short      * and long option passed.      *      * @param shortOption The short option associated with this option.      * @param longOption  The long option associated with this option.      * @param value       The initial value of this byte option.      */
DECL|method|ByteOption ( byte value, String longOption, char shortOption )
specifier|public
name|ByteOption
parameter_list|(
name|byte
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
name|Byte
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
try|try
block|{
name|this
operator|.
name|value
operator|=
name|Byte
operator|.
name|parseByte
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|OptionModificationException
argument_list|(
literal|"Error. A byte must be"
operator|+
literal|" specified, not '"
operator|+
name|value
operator|+
literal|"'."
argument_list|)
throw|;
block|}
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
comment|/**      * Modify this option using a byte value.      *      * @param     value A byte value.      */
DECL|method|setValue ( byte value )
specifier|public
name|void
name|setValue
parameter_list|(
name|byte
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
comment|/**      * Return this option as a boolean.      *      * @return This option as a boolean.      */
DECL|method|getValue ()
specifier|public
name|byte
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
name|Byte
operator|.
name|toString
argument_list|(
name|value
argument_list|)
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
literal|"BYTE"
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
comment|/** ByteOption */
end_comment

end_unit

