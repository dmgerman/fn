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
name|CaseChangers
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
name|formatter
operator|.
name|Formatter
import|;
end_import

begin_class
DECL|class|UpperFirstCaseChanger
specifier|public
class|class
name|UpperFirstCaseChanger
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
literal|"Upper first"
return|;
block|}
comment|/**      * Converts the first character of the first word of the given string to a upper case (and the remaining characters of the first word to lower case), but does not change anything if word starts with "{"      */
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
name|CaseChangers
operator|.
name|LOWER
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
block|}
end_class

end_unit

