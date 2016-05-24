begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2012-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.specialfields
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|specialfields
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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
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

begin_class
DECL|class|SpecialField
specifier|public
specifier|abstract
class|class
name|SpecialField
block|{
comment|// currently, menuString is used for undo string
comment|// public static String TEXT_UNDO;
comment|// Plain string; NOT treated by Globals.lang
DECL|field|TEXT_DONE_PATTERN
specifier|public
name|String
name|TEXT_DONE_PATTERN
decl_stmt|;
DECL|field|values
specifier|private
name|List
argument_list|<
name|SpecialFieldValue
argument_list|>
name|values
decl_stmt|;
DECL|field|keywords
specifier|private
name|List
argument_list|<
name|String
argument_list|>
name|keywords
decl_stmt|;
DECL|field|map
specifier|private
name|HashMap
argument_list|<
name|String
argument_list|,
name|SpecialFieldValue
argument_list|>
name|map
decl_stmt|;
DECL|method|setValues (List<SpecialFieldValue> values)
specifier|protected
name|void
name|setValues
parameter_list|(
name|List
argument_list|<
name|SpecialFieldValue
argument_list|>
name|values
parameter_list|)
block|{
name|this
operator|.
name|values
operator|=
name|values
expr_stmt|;
name|this
operator|.
name|keywords
operator|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
expr_stmt|;
name|this
operator|.
name|map
operator|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
expr_stmt|;
for|for
control|(
name|SpecialFieldValue
name|v
range|:
name|values
control|)
block|{
name|v
operator|.
name|getKeyword
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|keywords
operator|::
name|add
argument_list|)
expr_stmt|;
name|v
operator|.
name|getFieldValue
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|fieldValue
lambda|->
name|map
operator|.
name|put
argument_list|(
name|fieldValue
argument_list|,
name|v
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|getValues ()
specifier|public
name|List
argument_list|<
name|SpecialFieldValue
argument_list|>
name|getValues
parameter_list|()
block|{
return|return
name|this
operator|.
name|values
return|;
block|}
DECL|method|getKeyWords ()
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getKeyWords
parameter_list|()
block|{
return|return
name|this
operator|.
name|keywords
return|;
block|}
DECL|method|parse (String s)
specifier|public
name|SpecialFieldValue
name|parse
parameter_list|(
name|String
name|s
parameter_list|)
block|{
return|return
name|map
operator|.
name|get
argument_list|(
name|s
argument_list|)
return|;
block|}
DECL|method|getFieldName ()
specifier|public
specifier|abstract
name|String
name|getFieldName
parameter_list|()
function_decl|;
DECL|method|getRepresentingIcon ()
specifier|public
specifier|abstract
name|Icon
name|getRepresentingIcon
parameter_list|()
function_decl|;
DECL|method|getMenuString ()
specifier|public
specifier|abstract
name|String
name|getMenuString
parameter_list|()
function_decl|;
DECL|method|getToolTip ()
specifier|public
specifier|abstract
name|String
name|getToolTip
parameter_list|()
function_decl|;
DECL|method|isSingleValueField ()
specifier|public
name|boolean
name|isSingleValueField
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
block|}
end_class

end_unit

