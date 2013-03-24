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
comment|/**  * Formatter that returns the first page from the "pages" field, if set.  *  * For instance, if the pages field is set to "345-360" or "345--360",  * this formatter will return "345".  */
end_comment

begin_class
DECL|class|FirstPage
specifier|public
class|class
name|FirstPage
implements|implements
name|LayoutFormatter
block|{
DECL|method|format (String s)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|s
parameter_list|)
block|{
if|if
condition|(
name|s
operator|==
literal|null
condition|)
return|return
literal|""
return|;
name|String
index|[]
name|pageParts
init|=
name|s
operator|.
name|split
argument_list|(
literal|"[ \\-]+"
argument_list|)
decl_stmt|;
if|if
condition|(
name|pageParts
operator|.
name|length
operator|==
literal|2
condition|)
return|return
name|pageParts
index|[
literal|0
index|]
return|;
else|else
return|return
name|s
return|;
block|}
block|}
end_class

end_unit

