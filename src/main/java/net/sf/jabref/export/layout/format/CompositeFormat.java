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
name|LayoutFormatter
import|;
end_import

begin_comment
comment|/**  * A layout formatter that is the composite of the given Formatters executed in  * order.  *   * @author $Author$  * @version $Revision$ ($Date$)  *   */
end_comment

begin_class
DECL|class|CompositeFormat
specifier|public
class|class
name|CompositeFormat
implements|implements
name|LayoutFormatter
block|{
DECL|field|formatters
name|LayoutFormatter
index|[]
name|formatters
decl_stmt|;
comment|/**      * If called with this constructor, this formatter does nothing.      */
DECL|method|CompositeFormat ()
specifier|public
name|CompositeFormat
parameter_list|()
block|{
comment|// Nothing
block|}
DECL|method|CompositeFormat (LayoutFormatter first, LayoutFormatter second)
specifier|public
name|CompositeFormat
parameter_list|(
name|LayoutFormatter
name|first
parameter_list|,
name|LayoutFormatter
name|second
parameter_list|)
block|{
name|formatters
operator|=
operator|new
name|LayoutFormatter
index|[]
block|{
name|first
block|,
name|second
block|}
expr_stmt|;
block|}
DECL|method|CompositeFormat (LayoutFormatter[] formatters)
specifier|public
name|CompositeFormat
parameter_list|(
name|LayoutFormatter
index|[]
name|formatters
parameter_list|)
block|{
name|this
operator|.
name|formatters
operator|=
name|formatters
expr_stmt|;
block|}
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
name|formatters
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|LayoutFormatter
name|formatter
range|:
name|formatters
control|)
block|{
name|fieldText
operator|=
name|formatter
operator|.
name|format
argument_list|(
name|fieldText
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|fieldText
return|;
block|}
block|}
end_class

end_unit

