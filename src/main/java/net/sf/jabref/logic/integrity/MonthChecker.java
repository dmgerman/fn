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
name|BibDatabaseContext
import|;
end_import

begin_class
DECL|class|MonthChecker
specifier|public
class|class
name|MonthChecker
implements|implements
name|ValueChecker
block|{
DECL|field|ONLY_AN_INTEGER
specifier|private
specifier|static
specifier|final
name|Predicate
argument_list|<
name|String
argument_list|>
name|ONLY_AN_INTEGER
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"[1-9]|10|11|12"
argument_list|)
operator|.
name|asPredicate
argument_list|()
decl_stmt|;
DECL|field|MONTH_NORMALIZED
specifier|private
specifier|static
specifier|final
name|Predicate
argument_list|<
name|String
argument_list|>
name|MONTH_NORMALIZED
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"#jan#|#feb#|#mar#|#apr#|#may#|#jun#|#jul#|#aug#|#sep#|#oct#|#nov#|#dec#"
argument_list|)
operator|.
name|asPredicate
argument_list|()
decl_stmt|;
DECL|field|bibDatabaseContextMonth
specifier|private
specifier|final
name|BibDatabaseContext
name|bibDatabaseContextMonth
decl_stmt|;
DECL|method|MonthChecker (BibDatabaseContext bibDatabaseContext)
specifier|public
name|MonthChecker
parameter_list|(
name|BibDatabaseContext
name|bibDatabaseContext
parameter_list|)
block|{
name|this
operator|.
name|bibDatabaseContextMonth
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|bibDatabaseContext
argument_list|)
expr_stmt|;
block|}
comment|/**      * BibLaTeX package documentation (Section 2.3.9):      * The month field is an integer field.      * The bibliography style converts the month to a language-dependent string as required.      * For backwards compatibility, you may also use the following three-letter abbreviations in the month field:      * jan, feb, mar, apr, may, jun, jul, aug, sep, oct, nov, dec.      * Note that these abbreviations are BibTeX strings which must be given without any braces or quotes.      */
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
comment|//BibLaTeX
if|if
condition|(
name|bibDatabaseContextMonth
operator|.
name|isBiblatexMode
argument_list|()
operator|&&
operator|!
operator|(
name|ONLY_AN_INTEGER
operator|.
name|test
argument_list|(
name|value
operator|.
name|trim
argument_list|()
argument_list|)
operator|||
name|MONTH_NORMALIZED
operator|.
name|test
argument_list|(
name|value
operator|.
name|trim
argument_list|()
argument_list|)
operator|)
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
literal|"should be an integer or normalized"
argument_list|)
argument_list|)
return|;
block|}
comment|//BibTeX
if|if
condition|(
operator|!
name|bibDatabaseContextMonth
operator|.
name|isBiblatexMode
argument_list|()
operator|&&
operator|!
name|MONTH_NORMALIZED
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
literal|"should be normalized"
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

