begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.formatter.bibtexfields
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|formatter
operator|.
name|bibtexfields
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Objects
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|l10n
operator|.
name|Localization
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|cleanup
operator|.
name|Formatter
import|;
end_import

begin_class
DECL|class|RemoveBracesFormatter
specifier|public
class|class
name|RemoveBracesFormatter
extends|extends
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remove enclosing braces"
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getKey ()
specifier|public
name|String
name|getKey
parameter_list|()
block|{
return|return
literal|"remove_braces"
return|;
block|}
annotation|@
name|Override
DECL|method|format (String value)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|value
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|value
argument_list|)
expr_stmt|;
name|String
name|formatted
init|=
name|value
decl_stmt|;
while|while
condition|(
operator|(
name|formatted
operator|.
name|length
argument_list|()
operator|>=
literal|2
operator|)
operator|&&
operator|(
name|formatted
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|==
literal|'{'
operator|)
operator|&&
operator|(
name|formatted
operator|.
name|charAt
argument_list|(
name|formatted
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
operator|==
literal|'}'
operator|)
condition|)
block|{
name|String
name|trimmed
init|=
name|formatted
operator|.
name|substring
argument_list|(
literal|1
argument_list|,
name|formatted
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
decl_stmt|;
comment|// It could be that the removed braces were not matching
comment|// For example, "{A} test {B}" results in "A} test {B"
comment|// In this case, trimmed has a closing } without an opening { before that
if|if
condition|(
name|hasNegativeBraceCount
argument_list|(
name|trimmed
argument_list|)
condition|)
block|{
return|return
name|formatted
return|;
block|}
else|else
block|{
name|formatted
operator|=
name|trimmed
expr_stmt|;
block|}
block|}
return|return
name|formatted
return|;
block|}
annotation|@
name|Override
DECL|method|getDescription ()
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Removes braces encapsulating the complete field content."
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getExampleInput ()
specifier|public
name|String
name|getExampleInput
parameter_list|()
block|{
return|return
literal|"{In CDMA}"
return|;
block|}
comment|/**      * Check if a string at any point has had more ending } braces than opening { ones.      * Will e.g. return true for the string "DNA} blahblal {EPA"      *      * @param value The string to check.      * @return true if at any index the brace count is negative.      */
DECL|method|hasNegativeBraceCount (String value)
specifier|private
name|boolean
name|hasNegativeBraceCount
parameter_list|(
name|String
name|value
parameter_list|)
block|{
name|int
name|braceCount
init|=
literal|0
decl_stmt|;
for|for
control|(
name|int
name|index
init|=
literal|0
init|;
name|index
operator|<
name|value
operator|.
name|length
argument_list|()
condition|;
name|index
operator|++
control|)
block|{
name|char
name|charAtIndex
init|=
name|value
operator|.
name|charAt
argument_list|(
name|index
argument_list|)
decl_stmt|;
if|if
condition|(
name|charAtIndex
operator|==
literal|'{'
condition|)
block|{
name|braceCount
operator|++
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|charAtIndex
operator|==
literal|'}'
condition|)
block|{
name|braceCount
operator|--
expr_stmt|;
block|}
if|if
condition|(
name|braceCount
operator|<
literal|0
condition|)
block|{
return|return
literal|true
return|;
block|}
block|}
return|return
literal|false
return|;
block|}
block|}
end_class

end_unit

