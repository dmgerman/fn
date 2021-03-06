begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.l10n
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|l10n
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
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
name|regex
operator|.
name|Matcher
import|;
end_import

begin_class
DECL|class|LocalizationKeyParams
specifier|public
class|class
name|LocalizationKeyParams
block|{
DECL|field|key
specifier|private
specifier|final
name|LocalizationKey
name|key
decl_stmt|;
DECL|field|params
specifier|private
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|params
decl_stmt|;
DECL|method|LocalizationKeyParams (String key, String... params)
specifier|public
name|LocalizationKeyParams
parameter_list|(
name|String
name|key
parameter_list|,
name|String
modifier|...
name|params
parameter_list|)
block|{
name|this
operator|.
name|key
operator|=
operator|new
name|LocalizationKey
argument_list|(
name|key
argument_list|)
expr_stmt|;
name|this
operator|.
name|params
operator|=
name|Arrays
operator|.
name|asList
argument_list|(
name|params
argument_list|)
expr_stmt|;
if|if
condition|(
name|this
operator|.
name|params
operator|.
name|size
argument_list|()
operator|>
literal|10
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Translations can only have at most 10 parameters"
argument_list|)
throw|;
block|}
block|}
DECL|method|replacePlaceholders ()
specifier|public
name|String
name|replacePlaceholders
parameter_list|()
block|{
name|String
name|translation
init|=
name|key
operator|.
name|getTranslationValue
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
name|params
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|String
name|param
init|=
name|params
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|translation
operator|=
name|translation
operator|.
name|replaceAll
argument_list|(
literal|"%"
operator|+
name|i
argument_list|,
name|Matcher
operator|.
name|quoteReplacement
argument_list|(
name|param
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|translation
return|;
block|}
block|}
end_class

end_unit

