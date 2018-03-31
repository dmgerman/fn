begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.search
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|search
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|common
operator|.
name|eventbus
operator|.
name|Subscribe
import|;
end_import

begin_comment
comment|/**  * Every Listener that wants to receive events from a search needs to  * implement this interface  */
end_comment

begin_interface
annotation|@
name|FunctionalInterface
DECL|interface|SearchQueryHighlightListener
specifier|public
interface|interface
name|SearchQueryHighlightListener
block|{
comment|/**      * Pattern with which one can determine what to highlight      */
annotation|@
name|Subscribe
DECL|method|highlightPattern (Optional<Pattern> highlightPattern)
name|void
name|highlightPattern
parameter_list|(
name|Optional
argument_list|<
name|Pattern
argument_list|>
name|highlightPattern
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

