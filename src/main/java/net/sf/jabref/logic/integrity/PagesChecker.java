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

begin_comment
comment|/**      * From BibTex manual:      * One or more page numbers or range of numbers, such as 42--111 or 7,41,73--97 or 43+      * (the '+' in this last example indicates pages following that don't form a simple range).      * To make it easier to maintain Scribe-compatible databases, the standard styles convert      * a single dash (as in 7-33) to the double dash used in TEX to denote number ranges (as in 7--33).      */
end_comment

begin_class
DECL|class|PagesChecker
specifier|public
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
comment|/**      * Checks, if the page numbers String conforms to the BibTex manual      */
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
name|PAGES
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
name|FieldName
operator|.
name|PAGES
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

