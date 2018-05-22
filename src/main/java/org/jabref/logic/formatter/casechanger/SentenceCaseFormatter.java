begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.formatter.casechanger
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|formatter
operator|.
name|casechanger
package|;
end_package

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

begin_class
DECL|class|SentenceCaseFormatter
specifier|public
class|class
name|SentenceCaseFormatter
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
literal|"Sentence case"
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
literal|"sentence_case"
return|;
block|}
comment|/**      * Converts the first character of the first word of the given string to upper case (and the remaining characters of the first word to lower case) and changes other words to lower case, but does not change anything if word starts with "{"      */
annotation|@
name|Override
DECL|method|format (String input)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|input
parameter_list|)
block|{
name|Title
name|title
init|=
operator|new
name|Title
argument_list|(
operator|new
name|LowerCaseFormatter
argument_list|()
operator|.
name|format
argument_list|(
name|input
argument_list|)
argument_list|)
decl_stmt|;
name|title
operator|.
name|getWords
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|findFirst
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|Word
operator|::
name|toUpperFirst
argument_list|)
expr_stmt|;
return|return
name|title
operator|.
name|toString
argument_list|()
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
literal|"Capitalize the first word, changes other words to lower case."
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
literal|"i have {Aa} DREAM"
return|;
block|}
block|}
end_class

end_unit

