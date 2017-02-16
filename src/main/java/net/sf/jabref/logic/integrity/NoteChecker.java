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
DECL|class|NoteChecker
specifier|public
class|class
name|NoteChecker
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
DECL|field|bibDatabaseContextEdition
specifier|private
specifier|final
name|BibDatabaseContext
name|bibDatabaseContextEdition
decl_stmt|;
DECL|method|NoteChecker (BibDatabaseContext bibDatabaseContext)
specifier|public
name|NoteChecker
parameter_list|(
name|BibDatabaseContext
name|bibDatabaseContext
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
block|}
comment|/**      * biblatex package documentation (Section 4.9.1):      * The biblatex package will automatically capitalize the first word when required at the beginning of a sentence.      * Official BibTeX specification:      * note: Any additional information that can help the reader. The first word should be capitalized.      */
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
comment|//BibTeX
if|if
condition|(
operator|!
name|bibDatabaseContextEdition
operator|.
name|isBiblatexMode
argument_list|()
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

