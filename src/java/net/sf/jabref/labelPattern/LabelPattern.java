begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Created on 09-Dec-2003  */
end_comment

begin_package
DECL|package|net.sf.jabref.labelPattern
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
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
comment|/**  * A small table, where an entry type is associated with a label pattern (an   *<code>ArrayList</code>).  *   * A parent LabelPattern can be set.   * @author Ulrik Stervbo (ulriks AT ruc.dk)  */
end_comment

begin_class
DECL|class|LabelPattern
specifier|public
class|class
name|LabelPattern
extends|extends
name|Hashtable
block|{
comment|/** 	 * The parent of this LabelPattern. 	 */
DECL|field|parent
specifier|protected
name|LabelPattern
name|parent
init|=
literal|null
decl_stmt|;
DECL|method|LabelPattern ()
specifier|public
name|LabelPattern
parameter_list|()
block|{}
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
comment|/** 	 * Sets the parent LabelPattern. 	 * @param parent a<code>String</code> 	 */
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
comment|/** 	 * Get the parent LabelPattern 	 * @return the parent LabelPattern 	 */
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
comment|/** 	 * Remove a label pattern from the LabelPattern. No key patterns can be removed from 	 * the very parent LabelPattern since is thought of as a default. To do this, use 	 * the  removeKeyPattern(String type, boolean sure) 	 * @param type a<code>String</code> 	 */
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
comment|/** 	 * Gets an object for a desired label from this LabelPattern or one of it's parents. 	 * This method first tries to obtain the object from this LabelPattern via the  	 *<code>get</code> method of<code>Hashtable</code>. If this fails, we try the  	 * parent. 	 *   	 * @param key a<code>String</code> 	 * @return the object for the given key 	 * @throws NullPointerException 	 */
DECL|method|getValue (String key)
specifier|public
specifier|final
name|ArrayList
name|getValue
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
comment|// throws the NullPointerException
comment|// Test to see if we found anything
if|if
condition|(
name|_obj
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|parent
operator|!=
literal|null
condition|)
block|{
name|_obj
operator|=
name|parent
operator|.
name|getValue
argument_list|(
name|key
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|_obj
operator|==
literal|null
condition|)
block|{
comment|//TODO Throw some error
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Warning: I could not find the label \'"
operator|+
name|key
operator|+
literal|"\' and gave up"
argument_list|)
expr_stmt|;
block|}
block|}
return|return
operator|(
name|ArrayList
operator|)
name|_obj
return|;
block|}
block|}
end_class

end_unit

