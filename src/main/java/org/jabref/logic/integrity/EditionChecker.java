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
name|Objects
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

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|strings
operator|.
name|StringUtil
import|;
end_import

begin_class
DECL|class|EditionChecker
specifier|public
class|class
name|EditionChecker
implements|implements
name|ValueChecker
block|{
DECL|field|FIRST_LETTER_CAPITALIZED
specifier|private
specifier|static
specifier|final
name|Predicate
argument_list|<
name|String
argument_list|>
name|FIRST_LETTER_CAPITALIZED
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"^[A-Z]"
argument_list|)
operator|.
name|asPredicate
argument_list|()
decl_stmt|;
DECL|field|ONLY_NUMERALS_OR_LITERALS
specifier|private
specifier|static
specifier|final
name|Predicate
argument_list|<
name|String
argument_list|>
name|ONLY_NUMERALS_OR_LITERALS
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"^([0-9]+|[^0-9].+)$"
argument_list|)
operator|.
name|asPredicate
argument_list|()
decl_stmt|;
DECL|field|ONLY_NUMERALS
specifier|private
specifier|static
specifier|final
name|Predicate
argument_list|<
name|String
argument_list|>
name|ONLY_NUMERALS
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"[0-9]+"
argument_list|)
operator|.
name|asPredicate
argument_list|()
decl_stmt|;
DECL|field|FIRST_EDITION
specifier|private
specifier|static
specifier|final
name|String
name|FIRST_EDITION
init|=
literal|"1"
decl_stmt|;
DECL|field|bibDatabaseContextEdition
specifier|private
specifier|final
name|BibDatabaseContext
name|bibDatabaseContextEdition
decl_stmt|;
DECL|field|allowIntegerEdition
specifier|private
specifier|final
name|boolean
name|allowIntegerEdition
decl_stmt|;
DECL|method|EditionChecker (BibDatabaseContext bibDatabaseContext, boolean allowIntegerEdition)
specifier|public
name|EditionChecker
parameter_list|(
name|BibDatabaseContext
name|bibDatabaseContext
parameter_list|,
name|boolean
name|allowIntegerEdition
parameter_list|)
block|{
name|this
operator|.
name|bibDatabaseContextEdition
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|bibDatabaseContext
argument_list|)
expr_stmt|;
name|this
operator|.
name|allowIntegerEdition
operator|=
name|allowIntegerEdition
expr_stmt|;
block|}
comment|/**      * Checks, if field contains only an integer or a literal (biblatex mode)      * Checks, if the first letter is capitalized (BibTeX mode)      * biblatex package documentation:      * The edition of a printed publication. This must be an integer, not an ordinal.      * It is also possible to give the edition as a literal string, for example "Third, revised and expanded edition".      * Official BibTeX specification:      * The edition of a book-for example, "Second".      * This should be an ordinal, and should have the first letter capitalized.      */
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
name|StringUtil
operator|.
name|isBlank
argument_list|(
name|value
argument_list|)
condition|)
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
if|if
condition|(
name|value
operator|.
name|equals
argument_list|(
name|FIRST_EDITION
argument_list|)
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
literal|"edition of book reported as just 1"
argument_list|)
argument_list|)
return|;
block|}
comment|//biblatex
if|if
condition|(
name|bibDatabaseContextEdition
operator|.
name|isBiblatexMode
argument_list|()
operator|&&
operator|!
name|ONLY_NUMERALS_OR_LITERALS
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
return|return
name|Optional
operator|.
name|of
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"should contain an integer or a literal"
argument_list|)
argument_list|)
return|;
block|}
comment|//BibTeX
if|if
condition|(
operator|!
name|bibDatabaseContextEdition
operator|.
name|isBiblatexMode
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|allowIntegerEdition
condition|)
block|{
if|if
condition|(
operator|!
name|FIRST_LETTER_CAPITALIZED
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
return|return
name|Optional
operator|.
name|of
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"should have the first letter capitalized"
argument_list|)
argument_list|)
return|;
block|}
block|}
else|else
block|{
if|if
condition|(
operator|!
name|ONLY_NUMERALS
operator|.
name|test
argument_list|(
name|value
operator|.
name|trim
argument_list|()
argument_list|)
operator|&&
operator|!
name|FIRST_LETTER_CAPITALIZED
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
return|return
name|Optional
operator|.
name|of
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"should have the first letter capitalized"
argument_list|)
argument_list|)
return|;
block|}
block|}
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

