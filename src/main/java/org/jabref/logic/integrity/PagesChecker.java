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
DECL|class|PagesChecker
specifier|public
class|class
name|PagesChecker
implements|implements
name|ValueChecker
block|{
DECL|field|PAGES_EXP_BIBTEX
specifier|private
specifier|static
specifier|final
name|String
name|PAGES_EXP_BIBTEX
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
DECL|field|PAGES_EXP_BIBLATEX
specifier|private
specifier|static
specifier|final
name|String
name|PAGES_EXP_BIBLATEX
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
literal|"\\+|\\-{1,2}\\d+"
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
literal|"\\d+(?:\\+|\\-{1,2}\\d+)?"
comment|// repeat former pattern
operator|+
literal|")*"
comment|// repeat group 0,*
operator|+
literal|"\\z"
decl_stmt|;
comment|// end String
DECL|field|isValidPageNumber
specifier|private
specifier|final
name|Predicate
argument_list|<
name|String
argument_list|>
name|isValidPageNumber
decl_stmt|;
DECL|method|PagesChecker (BibDatabaseContext databaseContext)
specifier|public
name|PagesChecker
parameter_list|(
name|BibDatabaseContext
name|databaseContext
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
name|isValidPageNumber
operator|=
name|Pattern
operator|.
name|compile
argument_list|(
name|PAGES_EXP_BIBLATEX
argument_list|)
operator|.
name|asPredicate
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|isValidPageNumber
operator|=
name|Pattern
operator|.
name|compile
argument_list|(
name|PAGES_EXP_BIBTEX
argument_list|)
operator|.
name|asPredicate
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      * From BibTex manual:      *  One or more page numbers or range of numbers, such as 42--111 or 7,41,73--97 or 43+      *  (the '+' in this last example indicates pages following that don't form a simple range).      *  To make it easier to maintain Scribe-compatible databases, the standard styles convert      *  a single dash (as in 7-33) to the double dash used in TEX to denote number ranges (as in 7--33).      * biblatex:      *  same as above but allows single dash as well      */
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
operator|!
name|isValidPageNumber
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
literal|"should contain a valid page number range"
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

