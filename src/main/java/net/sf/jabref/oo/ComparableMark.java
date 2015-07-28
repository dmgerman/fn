begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.oo
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|oo
package|;
end_package

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|awt
operator|.
name|Point
import|;
end_import

begin_comment
comment|/**  *  */
end_comment

begin_class
DECL|class|ComparableMark
class|class
name|ComparableMark
implements|implements
name|Comparable
argument_list|<
name|ComparableMark
argument_list|>
block|{
DECL|field|name
specifier|private
specifier|final
name|String
name|name
decl_stmt|;
DECL|field|position
specifier|private
specifier|final
name|Point
name|position
decl_stmt|;
DECL|method|ComparableMark (String name, Point position)
specifier|public
name|ComparableMark
parameter_list|(
name|String
name|name
parameter_list|,
name|Point
name|position
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
name|this
operator|.
name|position
operator|=
name|position
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|compareTo (ComparableMark other)
specifier|public
name|int
name|compareTo
parameter_list|(
name|ComparableMark
name|other
parameter_list|)
block|{
if|if
condition|(
name|position
operator|.
name|Y
operator|!=
name|other
operator|.
name|position
operator|.
name|Y
condition|)
block|{
return|return
name|position
operator|.
name|Y
operator|-
name|other
operator|.
name|position
operator|.
name|Y
return|;
block|}
else|else
block|{
return|return
name|position
operator|.
name|X
operator|-
name|other
operator|.
name|position
operator|.
name|X
return|;
block|}
block|}
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|name
return|;
block|}
DECL|method|getPosition ()
specifier|public
name|Point
name|getPosition
parameter_list|()
block|{
return|return
name|position
return|;
block|}
block|}
end_class

end_unit

