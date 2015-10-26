begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.labelPattern
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|labelPattern
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_class
DECL|class|GlobalLabelPattern
specifier|public
class|class
name|GlobalLabelPattern
extends|extends
name|AbstractLabelPattern
block|{
annotation|@
name|Override
DECL|method|getValue (String key)
specifier|public
name|ArrayList
argument_list|<
name|String
argument_list|>
name|getValue
parameter_list|(
name|String
name|key
parameter_list|)
block|{
name|ArrayList
argument_list|<
name|String
argument_list|>
name|result
init|=
name|data
operator|.
name|get
argument_list|(
name|key
argument_list|)
decl_stmt|;
comment|//  Test to see if we found anything
if|if
condition|(
name|result
operator|==
literal|null
condition|)
block|{
comment|// check default value
name|result
operator|=
name|getDefaultValue
argument_list|()
expr_stmt|;
if|if
condition|(
name|result
operator|==
literal|null
condition|)
block|{
comment|// we are the "last" to ask
comment|// we don't have anything left
comment|// return the global default pattern
return|return
name|LabelPatternUtil
operator|.
name|DEFAULT_LABELPATTERN
return|;
block|}
block|}
return|return
name|result
return|;
block|}
block|}
end_class

end_unit

