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
name|Optional
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|function
operator|.
name|Predicate
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
name|database
operator|.
name|BibDatabaseContext
import|;
end_import

begin_class
DECL|class|TitleChecker
specifier|public
class|class
name|TitleChecker
implements|implements
name|ValueChecker
block|{
DECL|field|INSIDE_CURLY_BRAKETS
specifier|private
specifier|static
specifier|final
name|Pattern
name|INSIDE_CURLY_BRAKETS
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\{[^}\\{]*\\}"
argument_list|)
decl_stmt|;
DECL|field|HAS_CAPITAL_LETTERS
specifier|private
specifier|static
specifier|final
name|Predicate
argument_list|<
name|String
argument_list|>
name|HAS_CAPITAL_LETTERS
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"[\\p{Lu}\\p{Lt}]"
argument_list|)
operator|.
name|asPredicate
argument_list|()
decl_stmt|;
DECL|field|databaseContext
specifier|private
specifier|final
name|BibDatabaseContext
name|databaseContext
decl_stmt|;
DECL|method|TitleChecker (BibDatabaseContext databaseContext)
specifier|public
name|TitleChecker
parameter_list|(
name|BibDatabaseContext
name|databaseContext
parameter_list|)
block|{
name|this
operator|.
name|databaseContext
operator|=
name|databaseContext
expr_stmt|;
block|}
comment|/**      * Algorithm:      * - remove trailing whitespaces      * - ignore first letter as this can always be written in caps      * - remove everything that is in brackets      * - check if at least one capital letter is in the title      */
annotation|@
name|Override
DECL|method|checkValue (String value)
specifier|public
name|Optional
argument_list|<
name|String
argument_list|>
name|checkValue
parameter_list|(
name|String
name|value
parameter_list|)
block|{
if|if
condition|(
name|databaseContext
operator|.
name|isBiblatexMode
argument_list|()
condition|)
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
name|String
name|valueTrimmed
init|=
name|value
operator|.
name|trim
argument_list|()
decl_stmt|;
name|String
name|valueIgnoringFirstLetter
init|=
name|valueTrimmed
operator|.
name|startsWith
argument_list|(
literal|"{"
argument_list|)
condition|?
name|valueTrimmed
else|:
name|valueTrimmed
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
decl_stmt|;
name|String
name|valueOnlySpacesWithinCurlyBraces
init|=
name|valueIgnoringFirstLetter
decl_stmt|;
while|while
condition|(
literal|true
condition|)
block|{
name|Matcher
name|matcher
init|=
name|INSIDE_CURLY_BRAKETS
operator|.
name|matcher
argument_list|(
name|valueOnlySpacesWithinCurlyBraces
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|matcher
operator|.
name|find
argument_list|()
condition|)
block|{
break|break;
block|}
name|valueOnlySpacesWithinCurlyBraces
operator|=
name|matcher
operator|.
name|replaceAll
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
name|boolean
name|hasCapitalLettersThatBibtexWillConvertToSmallerOnes
init|=
name|HAS_CAPITAL_LETTERS
operator|.
name|test
argument_list|(
name|valueOnlySpacesWithinCurlyBraces
argument_list|)
decl_stmt|;
if|if
condition|(
name|hasCapitalLettersThatBibtexWillConvertToSmallerOnes
condition|)
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"capital letters are not masked using curly brackets {}"
argument_list|)
argument_list|)
return|;
block|}
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
block|}
end_class

end_unit

