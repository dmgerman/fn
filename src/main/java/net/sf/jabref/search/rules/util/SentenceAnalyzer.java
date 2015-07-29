begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.search.rules.util
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|search
operator|.
name|rules
operator|.
name|util
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
name|List
import|;
end_import

begin_class
DECL|class|SentenceAnalyzer
specifier|public
class|class
name|SentenceAnalyzer
block|{
DECL|field|ESCAPE_CHAR
specifier|public
specifier|static
specifier|final
name|char
name|ESCAPE_CHAR
init|=
literal|'\\'
decl_stmt|;
DECL|field|QUOTE_CHAR
specifier|public
specifier|static
specifier|final
name|char
name|QUOTE_CHAR
init|=
literal|'"'
decl_stmt|;
DECL|field|query
specifier|private
specifier|final
name|String
name|query
decl_stmt|;
DECL|method|SentenceAnalyzer (String query)
specifier|public
name|SentenceAnalyzer
parameter_list|(
name|String
name|query
parameter_list|)
block|{
name|this
operator|.
name|query
operator|=
name|query
expr_stmt|;
block|}
DECL|method|getWords ()
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getWords
parameter_list|()
block|{
name|List
argument_list|<
name|String
argument_list|>
name|result
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|boolean
name|escaped
init|=
literal|false
decl_stmt|;
name|boolean
name|quoted
init|=
literal|false
decl_stmt|;
for|for
control|(
name|char
name|c
range|:
name|query
operator|.
name|toCharArray
argument_list|()
control|)
block|{
comment|// Check if we are entering an escape sequence:
if|if
condition|(
operator|!
name|escaped
operator|&&
name|c
operator|==
name|ESCAPE_CHAR
condition|)
block|{
name|escaped
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
comment|// See if we have reached the end of a word:
if|if
condition|(
operator|!
name|escaped
operator|&&
operator|!
name|quoted
operator|&&
name|Character
operator|.
name|isWhitespace
argument_list|(
name|c
argument_list|)
condition|)
block|{
if|if
condition|(
name|sb
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|result
operator|.
name|add
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|=
operator|new
name|StringBuffer
argument_list|()
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|c
operator|==
name|QUOTE_CHAR
condition|)
block|{
comment|// Whether it is a start or end quote, store the current
comment|// word if any:
if|if
condition|(
name|sb
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|result
operator|.
name|add
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|=
operator|new
name|StringBuffer
argument_list|()
expr_stmt|;
block|}
name|quoted
operator|=
operator|!
name|quoted
expr_stmt|;
block|}
else|else
block|{
comment|// All other possibilities exhausted, we add the char to
comment|// the current word:
name|sb
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
name|escaped
operator|=
literal|false
expr_stmt|;
block|}
block|}
comment|// Finished with the loop. If we have a current word, add it:
if|if
condition|(
name|sb
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|result
operator|.
name|add
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
block|}
end_class

end_unit

