begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.formatter
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|formatter
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Matcher
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

begin_comment
comment|/**  * This class includes sensible defaults for consistent formatting of BibTex page numbers.  */
end_comment

begin_class
DECL|class|PageNumbersFormatter
specifier|public
class|class
name|PageNumbersFormatter
implements|implements
name|Formatter
block|{
annotation|@
name|Override
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Page numbers"
return|;
block|}
comment|/**      * Format page numbers, separated either by commas or double-hyphens.      * Converts the range number format of the<code>pages</code> field to page_number--page_number.      * Removes all literals except [0-9,-].      * Keeps the existing String if the resulting field does not match the expected Regex.      *      *<example>      *     1-2 -> 1--2      *     1,2,3 -> 1,2,3      *     {1}-{2} -> 1--2      *     Invalid -> Invalid      *</example>      */
DECL|method|format (String value)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|value
parameter_list|)
block|{
specifier|final
name|String
name|rejectLiterals
init|=
literal|"[^0-9,-]"
decl_stmt|;
specifier|final
name|Pattern
name|pagesPattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\A(\\d+)-{1,2}(\\d+)\\Z"
argument_list|)
decl_stmt|;
specifier|final
name|String
name|replace
init|=
literal|"$1--$2"
decl_stmt|;
comment|// nothing to do
if|if
condition|(
name|value
operator|==
literal|null
operator|||
name|value
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
name|value
return|;
block|}
comment|// remove unwanted literals incl. whitespace
name|String
name|cleanValue
init|=
name|value
operator|.
name|replaceAll
argument_list|(
name|rejectLiterals
argument_list|,
literal|""
argument_list|)
decl_stmt|;
comment|// try to find pages pattern
name|Matcher
name|matcher
init|=
name|pagesPattern
operator|.
name|matcher
argument_list|(
name|cleanValue
argument_list|)
decl_stmt|;
comment|// replace
name|String
name|newValue
init|=
name|matcher
operator|.
name|replaceFirst
argument_list|(
name|replace
argument_list|)
decl_stmt|;
comment|// replacement?
if|if
condition|(
operator|!
name|newValue
operator|.
name|equals
argument_list|(
name|cleanValue
argument_list|)
condition|)
block|{
comment|// write field
return|return
name|newValue
return|;
block|}
return|return
name|value
return|;
block|}
block|}
end_class

end_unit

