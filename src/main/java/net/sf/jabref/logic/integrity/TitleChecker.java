begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.integrity
package|package
name|net
operator|.
name|sf
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
name|Collections
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|integrity
operator|.
name|IntegrityCheck
operator|.
name|Checker
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

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|BibEntry
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
name|model
operator|.
name|entry
operator|.
name|FieldName
import|;
end_import

begin_class
DECL|class|TitleChecker
specifier|public
class|class
name|TitleChecker
implements|implements
name|Checker
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
annotation|@
name|Override
DECL|method|check (BibEntry entry)
specifier|public
name|List
argument_list|<
name|IntegrityMessage
argument_list|>
name|check
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
name|Optional
argument_list|<
name|String
argument_list|>
name|value
init|=
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|TITLE
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|value
operator|.
name|isPresent
argument_list|()
condition|)
block|{
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
comment|/*          * Algorithm:          * - remove trailing whitespaces          * - ignore first letter as this can always be written in caps          * - remove everything that is in brackets          * - check if at least one capital letter is in the title          */
name|String
name|valueTrimmed
init|=
name|value
operator|.
name|get
argument_list|()
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
name|Collections
operator|.
name|singletonList
argument_list|(
operator|new
name|IntegrityMessage
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"large capitals are not masked using curly brackets {}"
argument_list|)
argument_list|,
name|entry
argument_list|,
name|FieldName
operator|.
name|TITLE
argument_list|)
argument_list|)
return|;
block|}
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
block|}
end_class

end_unit

