begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.                   2003 Ulrik Stervbo (ulriks AT ruc.dk)      This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|Hashtable
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
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_comment
comment|/**  * A small table, where an entry type is associated with a label pattern (an  *<code>ArrayList</code>). A parent LabelPattern can be set.  */
end_comment

begin_class
DECL|class|AbstractLabelPattern
specifier|public
specifier|abstract
class|class
name|AbstractLabelPattern
block|{
DECL|field|defaultPattern
specifier|protected
name|List
argument_list|<
name|String
argument_list|>
name|defaultPattern
decl_stmt|;
DECL|field|data
specifier|protected
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|data
init|=
operator|new
name|Hashtable
argument_list|<>
argument_list|()
decl_stmt|;
DECL|method|AbstractLabelPattern ()
specifier|public
name|AbstractLabelPattern
parameter_list|()
block|{     }
DECL|method|addLabelPattern (String type, String pattern)
specifier|public
name|void
name|addLabelPattern
parameter_list|(
name|String
name|type
parameter_list|,
name|String
name|pattern
parameter_list|)
block|{
name|data
operator|.
name|put
argument_list|(
name|type
argument_list|,
name|LabelPatternUtil
operator|.
name|split
argument_list|(
name|pattern
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Remove a label pattern from the LabelPattern.      *      * @param type a<code>String</code>      */
DECL|method|removeLabelPattern (String type)
specifier|public
name|void
name|removeLabelPattern
parameter_list|(
name|String
name|type
parameter_list|)
block|{
if|if
condition|(
name|data
operator|.
name|containsKey
argument_list|(
name|type
argument_list|)
condition|)
block|{
name|data
operator|.
name|remove
argument_list|(
name|type
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Gets an object for a desired label from this LabelPattern or one of it's      * parents (in the case of DatabaseLAbelPattern). This method first tries to obtain the object from this      * LabelPattern via the<code>get</code> method of<code>Hashtable</code>.      * If this fails, we try the default.<br />      * If that fails, we try the parent.<br />      * If that fails, we return the DEFAULT_LABELPATTERN<br />      *      * @param key a<code>String</code>      * @return the list of Strings for the given key. First entry: the complete key      */
DECL|method|getValue (String key)
specifier|public
specifier|abstract
name|List
argument_list|<
name|String
argument_list|>
name|getValue
parameter_list|(
name|String
name|key
parameter_list|)
function_decl|;
comment|/**      * Checks whether this pattern is customized or the default value.      */
DECL|method|isDefaultValue (String key)
specifier|public
specifier|final
name|boolean
name|isDefaultValue
parameter_list|(
name|String
name|key
parameter_list|)
block|{
specifier|final
name|Object
name|_obj
init|=
name|data
operator|.
name|get
argument_list|(
name|key
argument_list|)
decl_stmt|;
return|return
name|_obj
operator|==
literal|null
return|;
block|}
comment|/**      * This method is called "...Value" to be in line with the other methods      *      * @return null if not available.      */
DECL|method|getDefaultValue ()
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getDefaultValue
parameter_list|()
block|{
return|return
name|this
operator|.
name|defaultPattern
return|;
block|}
comment|/**      * Sets the DEFAULT PATTERN for this label pattern      * @param labelPattern the pattern to store      */
DECL|method|setDefaultValue (String labelPattern)
specifier|public
name|void
name|setDefaultValue
parameter_list|(
name|String
name|labelPattern
parameter_list|)
block|{
name|this
operator|.
name|defaultPattern
operator|=
name|LabelPatternUtil
operator|.
name|split
argument_list|(
name|labelPattern
argument_list|)
expr_stmt|;
block|}
DECL|method|getAllKeys ()
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getAllKeys
parameter_list|()
block|{
return|return
name|data
operator|.
name|keySet
argument_list|()
return|;
block|}
block|}
end_class

end_unit

