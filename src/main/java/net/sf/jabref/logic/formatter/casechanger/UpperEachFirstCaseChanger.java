begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.formatter.casechanger
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
operator|.
name|casechanger
package|;
end_package

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|formatter
operator|.
name|Formatter
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
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
DECL|class|UpperEachFirstCaseChanger
specifier|public
class|class
name|UpperEachFirstCaseChanger
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"Upper Each First"
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
literal|"UpperEachFirstCaseChanger"
return|;
block|}
comment|/**      * Converts the first character of each word of the given string to a upper case (and all others to lower case), but does not change words starting with "{"      */
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
name|input
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
name|forEach
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
literal|"Converts the first character of each word in %s to upper case (and all others to lower case), but does not change words starting with \"{\"."
argument_list|)
return|;
block|}
block|}
end_class

end_unit

