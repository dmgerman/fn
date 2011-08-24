begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
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

begin_comment
comment|/**  * Every Listener that wants to receive events from a search needs to  * implement this interface  *   * @author Ben  *   */
end_comment

begin_interface
DECL|interface|SearchTextListener
interface|interface
name|SearchTextListener
block|{
comment|/** 	 * Array of words that were searched for 	 *  	 * @param words 	 */
DECL|method|searchText (ArrayList<String> words)
specifier|public
name|void
name|searchText
parameter_list|(
name|ArrayList
argument_list|<
name|String
argument_list|>
name|words
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

