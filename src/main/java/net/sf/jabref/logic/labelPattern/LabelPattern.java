begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Hashtable
import|;
end_import

begin_comment
comment|/**  * A small table, where an entry type is associated with a label pattern (an  *<code>ArrayList</code>). A parent LabelPattern can be set.  *   * @author Ulrik Stervbo (ulriks AT ruc.dk)  */
end_comment

begin_class
DECL|class|LabelPattern
specifier|public
class|class
name|LabelPattern
extends|extends
name|Hashtable
argument_list|<
name|String
argument_list|,
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|>
block|{
DECL|field|defaultPattern
specifier|private
name|ArrayList
argument_list|<
name|String
argument_list|>
name|defaultPattern
init|=
literal|null
decl_stmt|;
comment|/**      * The parent of this LabelPattern.      */
DECL|field|parent
specifier|private
name|LabelPattern
name|parent
init|=
literal|null
decl_stmt|;
DECL|method|LabelPattern ()
specifier|public
name|LabelPattern
parameter_list|()
block|{     }
DECL|method|LabelPattern (LabelPattern parent)
specifier|public
name|LabelPattern
parameter_list|(
name|LabelPattern
name|parent
parameter_list|)
block|{
name|this
operator|.
name|parent
operator|=
name|parent
expr_stmt|;
block|}
comment|/**      * Sets the parent LabelPattern.      *       * @param parent      *            a<code>String</code>      */
DECL|method|setParent (LabelPattern parent)
specifier|public
name|void
name|setParent
parameter_list|(
name|LabelPattern
name|parent
parameter_list|)
block|{
name|this
operator|.
name|parent
operator|=
name|parent
expr_stmt|;
block|}
comment|/**      * Get the parent LabelPattern      *       * @return the parent LabelPattern      */
DECL|method|getParent ()
specifier|public
name|LabelPattern
name|getParent
parameter_list|()
block|{
return|return
name|parent
return|;
block|}
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
comment|/**      * Remove a label pattern from the LabelPattern. No key patterns can be      * removed from the very parent LabelPattern since is thought of as a      * default. To do this, use the removeKeyPattern(String type, boolean sure)      *       * @param type      *            a<code>String</code>      */
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
name|containsKey
argument_list|(
name|type
argument_list|)
operator|&&
name|parent
operator|!=
literal|null
condition|)
block|{
name|remove
argument_list|(
name|type
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|removeLabelPattern (String type, boolean sure)
specifier|public
name|void
name|removeLabelPattern
parameter_list|(
name|String
name|type
parameter_list|,
name|boolean
name|sure
parameter_list|)
block|{
if|if
condition|(
name|containsKey
argument_list|(
name|type
argument_list|)
operator|&&
name|sure
condition|)
block|{
name|remove
argument_list|(
name|type
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Gets an object for a desired label from this LabelPattern or one of it's      * parents. This method first tries to obtain the object from this      * LabelPattern via the<code>get</code> method of<code>Hashtable</code>.      * If this fails, we try the default.<br />      * If that fails, we try the parent.<br />      * If that fails, we return the DEFAULT_LABELPATTERN<br />      *       * @param key a<code>String</code>      * @return the list of Strings for the given key      */
DECL|method|getValue (String key)
specifier|public
specifier|final
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
name|get
argument_list|(
name|key
argument_list|)
decl_stmt|;
comment|// Test to see if we found anything
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
comment|// no default value, ask parent
if|if
condition|(
name|parent
operator|!=
literal|null
condition|)
block|{
name|result
operator|=
name|parent
operator|.
name|getValue
argument_list|(
name|key
argument_list|)
expr_stmt|;
comment|// parent will definitely return something != null
block|}
else|else
block|{
comment|// we are the "last" parent
comment|// we don't have anything left
comment|// return the global default pattern
return|return
name|LabelPatternUtil
operator|.
name|DEFAULT_LABELPATTERN
return|;
block|}
block|}
block|}
return|return
name|result
return|;
block|}
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
name|Object
name|_obj
init|=
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
comment|/**      * This method is called "...Value" to be in line with the other methods      * @return      */
DECL|method|getDefaultValue ()
specifier|public
name|ArrayList
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
block|}
end_class

end_unit

