begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.util
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
package|;
end_package

begin_comment
comment|/**  * This little contraption is used if a generic type is needed that is either a  * S or a T.  *   * @author oezbek  *   * @param<S>  * @param<T>  */
end_comment

begin_class
DECL|class|TypeOr
specifier|public
class|class
name|TypeOr
parameter_list|<
name|S
parameter_list|,
name|T
parameter_list|>
block|{
DECL|field|s
specifier|public
name|S
name|s
decl_stmt|;
DECL|field|t
specifier|public
name|T
name|t
decl_stmt|;
DECL|method|TypeOr (S s, T t)
specifier|public
name|TypeOr
parameter_list|(
name|S
name|s
parameter_list|,
name|T
name|t
parameter_list|)
block|{
if|if
condition|(
operator|!
operator|(
name|s
operator|==
literal|null
operator|^
name|t
operator|==
literal|null
operator|)
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Either s or t need to be null"
argument_list|)
throw|;
name|this
operator|.
name|s
operator|=
name|s
expr_stmt|;
name|this
operator|.
name|t
operator|=
name|t
expr_stmt|;
block|}
DECL|method|isS ()
specifier|public
name|boolean
name|isS
parameter_list|()
block|{
return|return
name|s
operator|!=
literal|null
return|;
block|}
DECL|method|isT ()
specifier|public
name|boolean
name|isT
parameter_list|()
block|{
return|return
name|t
operator|!=
literal|null
return|;
block|}
block|}
end_class

end_unit

