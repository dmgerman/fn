begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.formatter.bibtexfields
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
name|bibtexfields
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
name|formatter
operator|.
name|Formatter
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
name|com
operator|.
name|google
operator|.
name|common
operator|.
name|base
operator|.
name|Strings
import|;
end_import

begin_comment
comment|/**  * This class includes sensible defaults for consistent formatting of BibTex page numbers.  *  * From BibTex manual:  * One or more page numbers or range of numbers, such as 42--111 or 7,41,73--97 or 43+  * (the '+' in this last example indicates pages following that don't form a simple range).  * To make it easier to maintain Scribe-compatible databases, the standard styles convert  * a single dash (as in 7-33) to the double dash used in TEX to denote number ranges (as in 7--33).  */
end_comment

begin_class
DECL|class|NormalizePagesFormatter
specifier|public
class|class
name|NormalizePagesFormatter
implements|implements
name|Formatter
block|{
DECL|field|PAGES_DETECT_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|PAGES_DETECT_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\A(\\d+)(?:-{1,2}(\\d+))?\\Z"
argument_list|)
decl_stmt|;
DECL|field|REJECT_LITERALS
specifier|private
specifier|static
specifier|final
name|String
name|REJECT_LITERALS
init|=
literal|"[^a-zA-Z0-9,\\-\\+,]"
decl_stmt|;
DECL|field|PAGES_REPLACE_PATTERN
specifier|private
specifier|static
specifier|final
name|String
name|PAGES_REPLACE_PATTERN
init|=
literal|"$1--$2"
decl_stmt|;
DECL|field|SINGLE_PAGE_REPLACE_PATTERN
specifier|private
specifier|static
specifier|final
name|String
name|SINGLE_PAGE_REPLACE_PATTERN
init|=
literal|"$1"
decl_stmt|;
annotation|@
name|Override
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Normalize page numbers"
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getKey ()
specifier|public
name|String
name|getKey
parameter_list|()
block|{
return|return
literal|"normalize_page_numbers"
return|;
block|}
comment|/**      * Format page numbers, separated either by commas or double-hyphens.      * Converts the range number format of the<code>pages</code> field to page_number--page_number.      * Removes unwanted literals except letters, numbers and -+ signs.      * Keeps the existing String if the resulting field does not match the expected Regex.      *      *<example>      *     1-2 -> 1--2      *     1,2,3 -> 1,2,3      *     {1}-{2} -> 1--2      *     43+ -> 43+      *     Invalid -> Invalid      *</example>      */
annotation|@
name|Override
DECL|method|format (String value)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|value
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|value
argument_list|)
expr_stmt|;
if|if
condition|(
name|value
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// nothing to do
return|return
name|value
return|;
block|}
comment|// Remove pages prefix
name|String
name|cleanValue
init|=
name|value
operator|.
name|replace
argument_list|(
literal|"pp."
argument_list|,
literal|""
argument_list|)
operator|.
name|replace
argument_list|(
literal|"p."
argument_list|,
literal|""
argument_list|)
decl_stmt|;
comment|// remove unwanted literals incl. whitespace
name|cleanValue
operator|=
name|cleanValue
operator|.
name|replaceAll
argument_list|(
literal|"\u2013|\u2014"
argument_list|,
literal|"-"
argument_list|)
operator|.
name|replaceAll
argument_list|(
name|REJECT_LITERALS
argument_list|,
literal|""
argument_list|)
expr_stmt|;
comment|// try to find pages pattern
name|Matcher
name|matcher
init|=
name|PAGES_DETECT_PATTERN
operator|.
name|matcher
argument_list|(
name|cleanValue
argument_list|)
decl_stmt|;
if|if
condition|(
name|matcher
operator|.
name|matches
argument_list|()
condition|)
block|{
comment|// replace
if|if
condition|(
name|Strings
operator|.
name|isNullOrEmpty
argument_list|(
name|matcher
operator|.
name|group
argument_list|(
literal|2
argument_list|)
argument_list|)
condition|)
block|{
return|return
name|matcher
operator|.
name|replaceFirst
argument_list|(
name|SINGLE_PAGE_REPLACE_PATTERN
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|matcher
operator|.
name|replaceFirst
argument_list|(
name|PAGES_REPLACE_PATTERN
argument_list|)
return|;
block|}
block|}
comment|// no replacement
return|return
name|value
return|;
block|}
annotation|@
name|Override
DECL|method|getDescription ()
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Normalize pages to BibTeX standard."
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getExampleInput ()
specifier|public
name|String
name|getExampleInput
parameter_list|()
block|{
return|return
literal|"1 - 2"
return|;
block|}
annotation|@
name|Override
DECL|method|hashCode ()
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|defaultHashCode
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|equals (Object obj)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
return|return
name|defaultEquals
argument_list|(
name|obj
argument_list|)
return|;
block|}
block|}
end_class

end_unit

