begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.integrity
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|integrity
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

begin_class
DECL|class|BracesCorrector
specifier|public
class|class
name|BracesCorrector
block|{
DECL|field|PATTERN_ESCAPED_CURLY_BRACES
specifier|private
specifier|static
specifier|final
name|Pattern
name|PATTERN_ESCAPED_CURLY_BRACES
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"(\\\\\\{)|(\\\\\\})"
argument_list|)
decl_stmt|;
comment|//private static final Pattern PATTERN_ESCAPED_CLOSING_CURLY_BRACE = Pattern.compile("");
DECL|method|apply (String input)
specifier|public
specifier|static
name|String
name|apply
parameter_list|(
name|String
name|input
parameter_list|)
block|{
if|if
condition|(
name|input
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
else|else
block|{
name|Matcher
name|matcher
init|=
name|PATTERN_ESCAPED_CURLY_BRACES
operator|.
name|matcher
argument_list|(
name|input
argument_list|)
decl_stmt|;
name|String
name|addedBraces
init|=
name|input
decl_stmt|;
name|String
name|c
init|=
name|matcher
operator|.
name|replaceAll
argument_list|(
literal|""
argument_list|)
decl_stmt|;
name|long
name|diff
init|=
name|c
operator|.
name|chars
argument_list|()
operator|.
name|filter
argument_list|(
name|ch
lambda|->
name|ch
operator|==
literal|'{'
argument_list|)
operator|.
name|count
argument_list|()
operator|-
name|c
operator|.
name|chars
argument_list|()
operator|.
name|filter
argument_list|(
name|ch
lambda|->
name|ch
operator|==
literal|'}'
argument_list|)
operator|.
name|count
argument_list|()
decl_stmt|;
while|while
condition|(
name|diff
operator|!=
literal|0
condition|)
block|{
if|if
condition|(
name|diff
operator|<
literal|0
condition|)
block|{
name|addedBraces
operator|=
literal|"{"
operator|+
name|addedBraces
expr_stmt|;
name|diff
operator|++
expr_stmt|;
block|}
else|else
block|{
name|addedBraces
operator|=
name|addedBraces
operator|+
literal|"}"
expr_stmt|;
name|diff
operator|--
expr_stmt|;
block|}
block|}
return|return
name|addedBraces
return|;
block|}
block|}
block|}
end_class

end_unit
