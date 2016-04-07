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
name|BibDatabaseContext
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
name|logic
operator|.
name|util
operator|.
name|io
operator|.
name|FileUtil
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
name|FileField
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
name|ParsedFileField
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|*
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
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
import|;
end_import

begin_class
DECL|class|IntegrityCheck
specifier|public
class|class
name|IntegrityCheck
block|{
DECL|field|bibDatabaseContext
specifier|private
specifier|final
name|BibDatabaseContext
name|bibDatabaseContext
decl_stmt|;
DECL|method|IntegrityCheck (BibDatabaseContext bibDatabaseContext)
specifier|public
name|IntegrityCheck
parameter_list|(
name|BibDatabaseContext
name|bibDatabaseContext
parameter_list|)
block|{
name|this
operator|.
name|bibDatabaseContext
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|bibDatabaseContext
argument_list|)
expr_stmt|;
block|}
DECL|method|checkBibtexDatabase ()
specifier|public
name|List
argument_list|<
name|IntegrityMessage
argument_list|>
name|checkBibtexDatabase
parameter_list|()
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
for|for
control|(
name|BibEntry
name|entry
range|:
name|bibDatabaseContext
operator|.
name|getDatabase
argument_list|()
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
DECL|method|checkBibtexEntry (BibEntry entry)
specifier|private
name|List
argument_list|<
name|IntegrityMessage
argument_list|>
name|checkBibtexEntry
parameter_list|(
name|BibEntry
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
name|result
operator|.
name|addAll
argument_list|(
operator|new
name|AuthorNameChecker
argument_list|(
literal|"author"
argument_list|)
operator|.
name|check
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
name|result
operator|.
name|addAll
argument_list|(
operator|new
name|AuthorNameChecker
argument_list|(
literal|"editor"
argument_list|)
operator|.
name|check
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|bibDatabaseContext
operator|.
name|isBiblatexMode
argument_list|()
condition|)
block|{
name|result
operator|.
name|addAll
argument_list|(
operator|new
name|TitleChecker
argument_list|()
operator|.
name|check
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|result
operator|.
name|addAll
argument_list|(
operator|new
name|BracketChecker
argument_list|(
literal|"title"
argument_list|)
operator|.
name|check
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
name|result
operator|.
name|addAll
argument_list|(
operator|new
name|YearChecker
argument_list|()
operator|.
name|check
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
name|result
operator|.
name|addAll
argument_list|(
operator|new
name|PagesChecker
argument_list|()
operator|.
name|check
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
name|result
operator|.
name|addAll
argument_list|(
operator|new
name|UrlChecker
argument_list|()
operator|.
name|check
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
name|result
operator|.
name|addAll
argument_list|(
operator|new
name|FileChecker
argument_list|(
name|bibDatabaseContext
argument_list|)
operator|.
name|check
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
name|result
operator|.
name|addAll
argument_list|(
operator|new
name|TypeChecker
argument_list|()
operator|.
name|check
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
name|result
operator|.
name|addAll
argument_list|(
operator|new
name|AbbreviationChecker
argument_list|(
literal|"journal"
argument_list|)
operator|.
name|check
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
name|result
operator|.
name|addAll
argument_list|(
operator|new
name|AbbreviationChecker
argument_list|(
literal|"booktitle"
argument_list|)
operator|.
name|check
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|result
return|;
block|}
annotation|@
name|FunctionalInterface
DECL|interface|Checker
specifier|public
interface|interface
name|Checker
block|{
DECL|method|check (BibEntry entry)
name|List
argument_list|<
name|IntegrityMessage
argument_list|>
name|check
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
function_decl|;
block|}
DECL|class|TypeChecker
specifier|private
specifier|static
class|class
name|TypeChecker
implements|implements
name|Checker
block|{
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
name|getFieldOptional
argument_list|(
literal|"pages"
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
if|if
condition|(
literal|"proceedings"
operator|.
name|equalsIgnoreCase
argument_list|(
name|entry
operator|.
name|getType
argument_list|()
argument_list|)
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
literal|"wrong entry type as proceedings has page numbers"
argument_list|)
argument_list|,
name|entry
argument_list|,
literal|"pages"
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
DECL|class|AbbreviationChecker
specifier|private
specifier|static
class|class
name|AbbreviationChecker
implements|implements
name|Checker
block|{
DECL|field|field
specifier|private
specifier|final
name|String
name|field
decl_stmt|;
DECL|method|AbbreviationChecker (String field)
specifier|private
name|AbbreviationChecker
parameter_list|(
name|String
name|field
parameter_list|)
block|{
name|this
operator|.
name|field
operator|=
name|field
expr_stmt|;
block|}
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
name|getFieldOptional
argument_list|(
name|field
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
if|if
condition|(
name|value
operator|.
name|get
argument_list|()
operator|.
name|contains
argument_list|(
literal|"."
argument_list|)
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
literal|"abbreviation detected"
argument_list|)
argument_list|,
name|entry
argument_list|,
name|field
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
DECL|class|FileChecker
specifier|private
specifier|static
class|class
name|FileChecker
implements|implements
name|Checker
block|{
DECL|field|context
specifier|private
specifier|final
name|BibDatabaseContext
name|context
decl_stmt|;
DECL|method|FileChecker (BibDatabaseContext context)
specifier|private
name|FileChecker
parameter_list|(
name|BibDatabaseContext
name|context
parameter_list|)
block|{
name|this
operator|.
name|context
operator|=
name|context
expr_stmt|;
block|}
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
name|getFieldOptional
argument_list|(
literal|"file"
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
name|List
argument_list|<
name|ParsedFileField
argument_list|>
name|parsedFileFields
init|=
name|FileField
operator|.
name|parse
argument_list|(
name|value
operator|.
name|get
argument_list|()
argument_list|)
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|p
lambda|->
operator|!
operator|(
name|p
operator|.
name|getLink
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"http://"
argument_list|)
operator|||
name|p
operator|.
name|getLink
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"https://"
argument_list|)
operator|)
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|ParsedFileField
name|p
range|:
name|parsedFileFields
control|)
block|{
name|Optional
argument_list|<
name|File
argument_list|>
name|file
init|=
name|FileUtil
operator|.
name|expandFilename
argument_list|(
name|context
argument_list|,
name|p
operator|.
name|getLink
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
operator|!
name|file
operator|.
name|isPresent
argument_list|()
operator|)
operator|||
operator|!
name|file
operator|.
name|get
argument_list|()
operator|.
name|exists
argument_list|()
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
literal|"link should refer to a correct file path"
argument_list|)
argument_list|,
name|entry
argument_list|,
literal|"file"
argument_list|)
argument_list|)
return|;
block|}
block|}
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
block|}
DECL|class|UrlChecker
specifier|private
specifier|static
class|class
name|UrlChecker
implements|implements
name|Checker
block|{
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
name|getFieldOptional
argument_list|(
literal|"url"
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
if|if
condition|(
operator|!
name|value
operator|.
name|get
argument_list|()
operator|.
name|contains
argument_list|(
literal|"://"
argument_list|)
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
literal|"should contain a protocol"
argument_list|)
operator|+
literal|": http[s]://, file://, ftp://, ..."
argument_list|,
name|entry
argument_list|,
literal|"url"
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
DECL|class|AuthorNameChecker
specifier|private
specifier|static
class|class
name|AuthorNameChecker
implements|implements
name|Checker
block|{
DECL|field|field
specifier|private
specifier|final
name|String
name|field
decl_stmt|;
DECL|method|AuthorNameChecker (String field)
specifier|private
name|AuthorNameChecker
parameter_list|(
name|String
name|field
parameter_list|)
block|{
name|this
operator|.
name|field
operator|=
name|field
expr_stmt|;
block|}
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
name|getFieldOptional
argument_list|(
name|field
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
name|String
name|valueTrimmedAndLowerCase
init|=
name|value
operator|.
name|get
argument_list|()
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
literal|"should start with a name"
argument_list|)
argument_list|,
name|entry
argument_list|,
name|field
argument_list|)
argument_list|)
return|;
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
literal|"should end with a name"
argument_list|)
argument_list|,
name|entry
argument_list|,
name|field
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
DECL|class|BracketChecker
specifier|private
specifier|static
class|class
name|BracketChecker
implements|implements
name|Checker
block|{
DECL|field|field
specifier|private
specifier|final
name|String
name|field
decl_stmt|;
DECL|method|BracketChecker (String field)
specifier|private
name|BracketChecker
parameter_list|(
name|String
name|field
parameter_list|)
block|{
name|this
operator|.
name|field
operator|=
name|field
expr_stmt|;
block|}
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
name|getFieldOptional
argument_list|(
name|field
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
name|get
argument_list|()
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
literal|"unexpected closing curly braket"
argument_list|)
argument_list|,
name|entry
argument_list|,
name|field
argument_list|)
argument_list|)
return|;
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
literal|"unexpected opening curly braket"
argument_list|)
argument_list|,
name|entry
argument_list|,
name|field
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
name|getFieldOptional
argument_list|(
literal|"title"
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
comment|/*              * Algorithm:              * - remove trailing whitespaces              * - ignore first letter as this can always be written in caps              * - remove everything that is in brackets              * - check if at least one capital letter is in the title              */
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
literal|"title"
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
name|getFieldOptional
argument_list|(
literal|"year"
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
if|if
condition|(
operator|!
name|CONTAINS_FOUR_DIGIT
operator|.
name|test
argument_list|(
name|value
operator|.
name|get
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
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
literal|"should contain a four digit number"
argument_list|)
argument_list|,
name|entry
argument_list|,
literal|"year"
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
comment|/**      * From BibTex manual:      * One or more page numbers or range of numbers, such as 42--111 or 7,41,73--97 or 43+      * (the '+' in this last example indicates pages following that don't form a simple range).      * To make it easier to maintain Scribe-compatible databases, the standard styles convert      * a single dash (as in 7-33) to the double dash used in TEX to denote number ranges (as in 7--33).      */
DECL|class|PagesChecker
specifier|private
specifier|static
class|class
name|PagesChecker
implements|implements
name|Checker
block|{
DECL|field|PAGES_EXP
specifier|private
specifier|static
specifier|final
name|String
name|PAGES_EXP
init|=
literal|""
operator|+
literal|"\\A"
comment|// begin String
operator|+
literal|"\\d+"
comment|// number
operator|+
literal|"(?:"
comment|// non-capture group
operator|+
literal|"\\+|\\-{2}\\d+"
comment|// + or --number (range)
operator|+
literal|")?"
comment|// optional group
operator|+
literal|"(?:"
comment|// non-capture group
operator|+
literal|","
comment|// comma
operator|+
literal|"\\d+(?:\\+|\\-{2}\\d+)?"
comment|// repeat former pattern
operator|+
literal|")*"
comment|// repeat group 0,*
operator|+
literal|"\\z"
decl_stmt|;
comment|// end String
DECL|field|VALID_PAGE_NUMBER
specifier|private
specifier|static
specifier|final
name|Predicate
argument_list|<
name|String
argument_list|>
name|VALID_PAGE_NUMBER
init|=
name|Pattern
operator|.
name|compile
argument_list|(
name|PAGES_EXP
argument_list|)
operator|.
name|asPredicate
argument_list|()
decl_stmt|;
comment|/**          * Checks, if the page numbers String conforms to the BibTex manual          */
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
name|getFieldOptional
argument_list|(
literal|"pages"
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
if|if
condition|(
operator|!
name|VALID_PAGE_NUMBER
operator|.
name|test
argument_list|(
name|value
operator|.
name|get
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
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
literal|"should contain a valid page number range"
argument_list|)
argument_list|,
name|entry
argument_list|,
literal|"pages"
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
block|}
end_class

end_unit

