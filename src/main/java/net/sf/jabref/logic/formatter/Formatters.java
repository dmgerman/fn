begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.formatter
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
package|;
end_package

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
name|Arrays
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
operator|.
name|ClearFormatter
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
name|bibtexfields
operator|.
name|HtmlToLatexFormatter
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
name|bibtexfields
operator|.
name|HtmlToUnicodeFormatter
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
name|bibtexfields
operator|.
name|LatexCleanupFormatter
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
name|bibtexfields
operator|.
name|NormalizeDateFormatter
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
name|bibtexfields
operator|.
name|NormalizeMonthFormatter
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
name|bibtexfields
operator|.
name|NormalizeNamesFormatter
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
name|bibtexfields
operator|.
name|NormalizePagesFormatter
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
name|bibtexfields
operator|.
name|OrdinalsToSuperscriptFormatter
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
name|bibtexfields
operator|.
name|RemoveBracesFormatter
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
name|bibtexfields
operator|.
name|UnicodeToLatexFormatter
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
name|bibtexfields
operator|.
name|UnitsToLatexFormatter
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
name|casechanger
operator|.
name|CapitalizeFormatter
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
name|casechanger
operator|.
name|LowerCaseFormatter
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
name|casechanger
operator|.
name|ProtectTermsFormatter
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
name|casechanger
operator|.
name|SentenceCaseFormatter
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
name|casechanger
operator|.
name|TitleCaseFormatter
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
name|casechanger
operator|.
name|UpperCaseFormatter
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
name|minifier
operator|.
name|MinifyNameListFormatter
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
name|layout
operator|.
name|format
operator|.
name|LatexToUnicodeFormatter
import|;
end_import

begin_class
DECL|class|Formatters
specifier|public
class|class
name|Formatters
block|{
DECL|field|CONVERTERS
specifier|public
specifier|static
specifier|final
name|List
argument_list|<
name|Formatter
argument_list|>
name|CONVERTERS
init|=
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|HtmlToLatexFormatter
argument_list|()
argument_list|,
operator|new
name|HtmlToUnicodeFormatter
argument_list|()
argument_list|,
operator|new
name|LatexToUnicodeFormatter
argument_list|()
argument_list|,
operator|new
name|UnicodeToLatexFormatter
argument_list|()
argument_list|)
decl_stmt|;
DECL|field|CASE_CHANGERS
specifier|public
specifier|static
specifier|final
name|List
argument_list|<
name|Formatter
argument_list|>
name|CASE_CHANGERS
init|=
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|CapitalizeFormatter
argument_list|()
argument_list|,
operator|new
name|LowerCaseFormatter
argument_list|()
argument_list|,
operator|new
name|ProtectTermsFormatter
argument_list|()
argument_list|,
operator|new
name|SentenceCaseFormatter
argument_list|()
argument_list|,
operator|new
name|TitleCaseFormatter
argument_list|()
argument_list|,
operator|new
name|UpperCaseFormatter
argument_list|()
argument_list|)
decl_stmt|;
DECL|field|OTHERS
specifier|public
specifier|static
specifier|final
name|List
argument_list|<
name|Formatter
argument_list|>
name|OTHERS
init|=
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|ClearFormatter
argument_list|()
argument_list|,
operator|new
name|LatexCleanupFormatter
argument_list|()
argument_list|,
operator|new
name|MinifyNameListFormatter
argument_list|()
argument_list|,
operator|new
name|NormalizeDateFormatter
argument_list|()
argument_list|,
operator|new
name|NormalizeMonthFormatter
argument_list|()
argument_list|,
operator|new
name|NormalizeNamesFormatter
argument_list|()
argument_list|,
operator|new
name|NormalizePagesFormatter
argument_list|()
argument_list|,
operator|new
name|OrdinalsToSuperscriptFormatter
argument_list|()
argument_list|,
operator|new
name|RemoveBracesFormatter
argument_list|()
argument_list|,
operator|new
name|UnitsToLatexFormatter
argument_list|()
argument_list|)
decl_stmt|;
DECL|field|ALL
specifier|public
specifier|static
specifier|final
name|List
argument_list|<
name|Formatter
argument_list|>
name|ALL
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
static|static
block|{
name|ALL
operator|.
name|addAll
argument_list|(
name|CONVERTERS
argument_list|)
expr_stmt|;
name|ALL
operator|.
name|addAll
argument_list|(
name|CASE_CHANGERS
argument_list|)
expr_stmt|;
name|ALL
operator|.
name|addAll
argument_list|(
name|OTHERS
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

