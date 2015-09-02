begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.export.layout.format
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
operator|.
name|layout
operator|.
name|format
package|;
end_package

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
operator|.
name|layout
operator|.
name|AbstractParamLayoutFormatter
import|;
end_import

begin_comment
comment|/**  * @author ralmond  *   * This formatter takes two arguments and examines the field text.    * If the field text represents multiple individuals, that is it contains the string "and"  * then the field text is replaced with the first argument, otherwise it is replaced with the second.  * For example:  *   * \format[IfPlural(Eds.,Ed.)]{\editor}  *   * Should expand to 'Eds.' if the document has more than one editor and 'Ed.' if it only has one.  *   *  */
end_comment

begin_class
DECL|class|IfPlural
specifier|public
class|class
name|IfPlural
extends|extends
name|AbstractParamLayoutFormatter
block|{
DECL|field|pluralText
specifier|private
name|String
name|pluralText
decl_stmt|;
DECL|field|singularText
specifier|private
name|String
name|singularText
decl_stmt|;
annotation|@
name|Override
DECL|method|setArgument (String arg)
specifier|public
name|void
name|setArgument
parameter_list|(
name|String
name|arg
parameter_list|)
block|{
name|String
index|[]
name|parts
init|=
name|AbstractParamLayoutFormatter
operator|.
name|parseArgument
argument_list|(
name|arg
argument_list|)
decl_stmt|;
if|if
condition|(
name|parts
operator|.
name|length
operator|<
literal|2
condition|)
block|{
return|return;
comment|// TODO: too few arguments. Print an error message here?
block|}
name|pluralText
operator|=
name|parts
index|[
literal|0
index|]
expr_stmt|;
name|singularText
operator|=
name|parts
index|[
literal|1
index|]
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|format (String fieldText)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|fieldText
parameter_list|)
block|{
if|if
condition|(
name|pluralText
operator|==
literal|null
condition|)
block|{
return|return
name|fieldText
return|;
comment|// TODO: argument missing or invalid. Print an error message here?
block|}
if|if
condition|(
name|fieldText
operator|.
name|matches
argument_list|(
literal|".*\\sand\\s.*"
argument_list|)
condition|)
block|{
return|return
name|pluralText
return|;
block|}
else|else
block|{
return|return
name|singularText
return|;
block|}
block|}
block|}
end_class

end_unit

