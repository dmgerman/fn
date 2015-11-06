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
name|database
operator|.
name|BibtexDatabase
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
name|BibtexEntry
import|;
end_import

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
name|Pattern
import|;
end_import

begin_class
DECL|class|IntegrityCheck
specifier|public
class|class
name|IntegrityCheck
block|{
DECL|field|AUTHOR_NAME_CHECKER
specifier|public
specifier|static
specifier|final
name|Checker
name|AUTHOR_NAME_CHECKER
init|=
operator|new
name|AuthorNameChecker
argument_list|()
decl_stmt|;
DECL|field|YEAR_CHECKER
specifier|public
specifier|static
specifier|final
name|Checker
name|YEAR_CHECKER
init|=
operator|new
name|YearChecker
argument_list|()
decl_stmt|;
DECL|field|TITLE_CHECKER
specifier|public
specifier|static
specifier|final
name|Checker
name|TITLE_CHECKER
init|=
operator|new
name|TitleChecker
argument_list|()
decl_stmt|;
DECL|field|BRACKET_CHECKER
specifier|public
specifier|static
specifier|final
name|Checker
name|BRACKET_CHECKER
init|=
operator|new
name|BracketChecker
argument_list|()
decl_stmt|;
DECL|method|checkBibtexDatabase (BibtexDatabase base)
specifier|public
name|List
argument_list|<
name|IntegrityMessage
argument_list|>
name|checkBibtexDatabase
parameter_list|(
name|BibtexDatabase
name|base
parameter_list|)
block|{
name|List
argument_list|<
name|IntegrityMessage
argument_list|>
name|result
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
if|if
condition|(
name|base
operator|==
literal|null
condition|)
block|{
return|return
name|result
return|;
block|}
for|for
control|(
name|BibtexEntry
name|entry
range|:
name|base
operator|.
name|getEntries
argument_list|()
control|)
block|{
name|result
operator|.
name|addAll
argument_list|(
name|checkBibtexEntry
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
DECL|method|checkBibtexEntry (BibtexEntry entry)
specifier|public
name|List
argument_list|<
name|IntegrityMessage
argument_list|>
name|checkBibtexEntry
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
name|List
argument_list|<
name|IntegrityMessage
argument_list|>
name|result
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
if|if
condition|(
name|entry
operator|==
literal|null
condition|)
block|{
return|return
name|result
return|;
block|}
name|Object
name|data
init|=
name|entry
operator|.
name|getField
argument_list|(
literal|"author"
argument_list|)
decl_stmt|;
if|if
condition|(
name|data
operator|!=
literal|null
condition|)
block|{
name|AUTHOR_NAME_CHECKER
operator|.
name|check
argument_list|(
name|data
operator|.
name|toString
argument_list|()
argument_list|,
literal|"author"
argument_list|,
name|entry
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
name|data
operator|=
name|entry
operator|.
name|getField
argument_list|(
literal|"editor"
argument_list|)
expr_stmt|;
if|if
condition|(
name|data
operator|!=
literal|null
condition|)
block|{
name|AUTHOR_NAME_CHECKER
operator|.
name|check
argument_list|(
name|data
operator|.
name|toString
argument_list|()
argument_list|,
literal|"editor"
argument_list|,
name|entry
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
name|data
operator|=
name|entry
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
expr_stmt|;
if|if
condition|(
name|data
operator|!=
literal|null
condition|)
block|{
name|TITLE_CHECKER
operator|.
name|check
argument_list|(
name|data
operator|.
name|toString
argument_list|()
argument_list|,
literal|"title"
argument_list|,
name|entry
argument_list|,
name|result
argument_list|)
expr_stmt|;
name|BRACKET_CHECKER
operator|.
name|check
argument_list|(
name|data
operator|.
name|toString
argument_list|()
argument_list|,
literal|"title"
argument_list|,
name|entry
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
name|data
operator|=
name|entry
operator|.
name|getField
argument_list|(
literal|"year"
argument_list|)
expr_stmt|;
if|if
condition|(
name|data
operator|!=
literal|null
condition|)
block|{
name|YEAR_CHECKER
operator|.
name|check
argument_list|(
name|data
operator|.
name|toString
argument_list|()
argument_list|,
literal|"year"
argument_list|,
name|entry
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
DECL|interface|Checker
specifier|public
specifier|static
interface|interface
name|Checker
block|{
DECL|method|check (String value, String fieldName, BibtexEntry entry, List<IntegrityMessage> collector)
name|void
name|check
parameter_list|(
name|String
name|value
parameter_list|,
name|String
name|fieldName
parameter_list|,
name|BibtexEntry
name|entry
parameter_list|,
name|List
argument_list|<
name|IntegrityMessage
argument_list|>
name|collector
parameter_list|)
function_decl|;
block|}
DECL|class|AuthorNameChecker
specifier|private
specifier|static
class|class
name|AuthorNameChecker
implements|implements
name|Checker
block|{
annotation|@
name|Override
DECL|method|check (String value, String fieldName, BibtexEntry entry, List<IntegrityMessage> collector)
specifier|public
name|void
name|check
parameter_list|(
name|String
name|value
parameter_list|,
name|String
name|fieldName
parameter_list|,
name|BibtexEntry
name|entry
parameter_list|,
name|List
argument_list|<
name|IntegrityMessage
argument_list|>
name|collector
parameter_list|)
block|{
name|String
name|valueTrimmedAndLowerCase
init|=
name|value
operator|.
name|trim
argument_list|()
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
if|if
condition|(
name|valueTrimmedAndLowerCase
operator|.
name|startsWith
argument_list|(
literal|"and "
argument_list|)
operator|||
name|valueTrimmedAndLowerCase
operator|.
name|startsWith
argument_list|(
literal|","
argument_list|)
condition|)
block|{
name|collector
operator|.
name|add
argument_list|(
operator|new
name|IntegrityMessage
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"should start with a name"
argument_list|)
argument_list|,
name|entry
argument_list|,
name|fieldName
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|valueTrimmedAndLowerCase
operator|.
name|endsWith
argument_list|(
literal|" and"
argument_list|)
operator|||
name|valueTrimmedAndLowerCase
operator|.
name|endsWith
argument_list|(
literal|","
argument_list|)
condition|)
block|{
name|collector
operator|.
name|add
argument_list|(
operator|new
name|IntegrityMessage
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"should end with a name"
argument_list|)
argument_list|,
name|entry
argument_list|,
name|fieldName
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|class|BracketChecker
specifier|private
specifier|static
class|class
name|BracketChecker
implements|implements
name|Checker
block|{
annotation|@
name|Override
DECL|method|check (String value, String fieldName, BibtexEntry entry, List<IntegrityMessage> collector)
specifier|public
name|void
name|check
parameter_list|(
name|String
name|value
parameter_list|,
name|String
name|fieldName
parameter_list|,
name|BibtexEntry
name|entry
parameter_list|,
name|List
argument_list|<
name|IntegrityMessage
argument_list|>
name|collector
parameter_list|)
block|{
comment|// metaphor: integer-based stack (push + / pop -)
name|int
name|counter
init|=
literal|0
decl_stmt|;
for|for
control|(
name|char
name|a
range|:
name|value
operator|.
name|trim
argument_list|()
operator|.
name|toCharArray
argument_list|()
control|)
block|{
if|if
condition|(
name|a
operator|==
literal|'{'
condition|)
block|{
name|counter
operator|++
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|a
operator|==
literal|'}'
condition|)
block|{
if|if
condition|(
name|counter
operator|==
literal|0
condition|)
block|{
name|collector
operator|.
name|add
argument_list|(
operator|new
name|IntegrityMessage
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"unexpected closing curly braket"
argument_list|)
argument_list|,
name|entry
argument_list|,
name|fieldName
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|counter
operator|--
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|counter
operator|>
literal|0
condition|)
block|{
name|collector
operator|.
name|add
argument_list|(
operator|new
name|IntegrityMessage
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"unexpected opening curly braket"
argument_list|)
argument_list|,
name|entry
argument_list|,
name|fieldName
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|class|TitleChecker
specifier|private
specifier|static
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
literal|"\\{[^}]*\\}"
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
DECL|method|check (String value, String fieldName, BibtexEntry entry, List<IntegrityMessage> collector)
specifier|public
name|void
name|check
parameter_list|(
name|String
name|value
parameter_list|,
name|String
name|fieldName
parameter_list|,
name|BibtexEntry
name|entry
parameter_list|,
name|List
argument_list|<
name|IntegrityMessage
argument_list|>
name|collector
parameter_list|)
block|{
comment|/*              * Algorithm:              * - remove trailing whitespaces              * - ignore first letter as this can always be written in caps              * - remove everything that is in brackets              * - check if at least one capital letter is in the title              */
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
name|INSIDE_CURLY_BRAKETS
operator|.
name|matcher
argument_list|(
name|valueIgnoringFirstLetter
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|""
argument_list|)
decl_stmt|;
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
name|collector
operator|.
name|add
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
name|fieldName
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|class|YearChecker
specifier|private
specifier|static
class|class
name|YearChecker
implements|implements
name|Checker
block|{
DECL|field|CONTAINS_FOUR_DIGIT
specifier|private
specifier|static
specifier|final
name|Predicate
argument_list|<
name|String
argument_list|>
name|CONTAINS_FOUR_DIGIT
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"([^0-9]|^)[0-9]{4}([^0-9]|$)"
argument_list|)
operator|.
name|asPredicate
argument_list|()
decl_stmt|;
comment|/**          * Checks, if the number String contains a four digit year          */
annotation|@
name|Override
DECL|method|check (String value, String fieldName, BibtexEntry entry, List<IntegrityMessage> collector)
specifier|public
name|void
name|check
parameter_list|(
name|String
name|value
parameter_list|,
name|String
name|fieldName
parameter_list|,
name|BibtexEntry
name|entry
parameter_list|,
name|List
argument_list|<
name|IntegrityMessage
argument_list|>
name|collector
parameter_list|)
block|{
if|if
condition|(
operator|!
name|CONTAINS_FOUR_DIGIT
operator|.
name|test
argument_list|(
name|value
operator|.
name|trim
argument_list|()
argument_list|)
condition|)
block|{
name|collector
operator|.
name|add
argument_list|(
operator|new
name|IntegrityMessage
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"should contain a four digit number"
argument_list|)
argument_list|,
name|entry
argument_list|,
name|fieldName
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

