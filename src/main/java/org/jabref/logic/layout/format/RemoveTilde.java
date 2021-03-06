begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.layout.format
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|layout
operator|.
name|format
package|;
end_package

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|layout
operator|.
name|LayoutFormatter
import|;
end_import

begin_comment
comment|/**  * Replace a non-command tilde ~ by a space.  *  * Useful for formatting Latex code.  */
end_comment

begin_class
DECL|class|RemoveTilde
specifier|public
class|class
name|RemoveTilde
implements|implements
name|LayoutFormatter
block|{
annotation|@
name|Override
DECL|method|format (String fieldText)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|fieldText
parameter_list|)
block|{
name|StringBuilder
name|result
init|=
operator|new
name|StringBuilder
argument_list|(
name|fieldText
operator|.
name|length
argument_list|()
argument_list|)
decl_stmt|;
name|char
index|[]
name|c
init|=
name|fieldText
operator|.
name|toCharArray
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|c
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|c
index|[
name|i
index|]
operator|==
literal|'~'
condition|)
block|{
name|result
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|result
operator|.
name|append
argument_list|(
name|c
index|[
name|i
index|]
argument_list|)
expr_stmt|;
comment|// Skip the next character if the current one is a backslash
if|if
condition|(
operator|(
name|c
index|[
name|i
index|]
operator|==
literal|'\\'
operator|)
operator|&&
operator|(
operator|(
name|i
operator|+
literal|1
operator|)
operator|<
name|c
operator|.
name|length
operator|)
condition|)
block|{
name|i
operator|++
expr_stmt|;
name|result
operator|.
name|append
argument_list|(
name|c
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|result
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

