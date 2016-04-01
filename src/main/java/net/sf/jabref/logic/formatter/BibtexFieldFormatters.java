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
name|*
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

begin_class
DECL|class|BibtexFieldFormatters
specifier|public
class|class
name|BibtexFieldFormatters
block|{
DECL|field|NORMALIZE_PAGES
specifier|public
specifier|static
specifier|final
name|NormalizePagesFormatter
name|NORMALIZE_PAGES
init|=
operator|new
name|NormalizePagesFormatter
argument_list|()
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|OrdinalsToSuperscriptFormatter
DECL|field|ORDINALS_TO_LATEX_SUPERSCRIPT
name|ORDINALS_TO_LATEX_SUPERSCRIPT
init|=
operator|new
name|OrdinalsToSuperscriptFormatter
argument_list|()
decl_stmt|;
DECL|field|NORMALIZE_DATE
specifier|public
specifier|static
specifier|final
name|NormalizeDateFormatter
name|NORMALIZE_DATE
init|=
operator|new
name|NormalizeDateFormatter
argument_list|()
decl_stmt|;
DECL|field|NORMALIZE_MONTH
specifier|public
specifier|static
specifier|final
name|NormalizeMonthFormatter
name|NORMALIZE_MONTH
init|=
operator|new
name|NormalizeMonthFormatter
argument_list|()
decl_stmt|;
DECL|field|NORMALIZE_PERSON_NAMES
specifier|public
specifier|static
specifier|final
name|NormalizeNamesFormatter
name|NORMALIZE_PERSON_NAMES
init|=
operator|new
name|NormalizeNamesFormatter
argument_list|()
decl_stmt|;
DECL|field|LATEX_CLEANUP
specifier|public
specifier|static
specifier|final
name|LatexCleanupFormatter
name|LATEX_CLEANUP
init|=
operator|new
name|LatexCleanupFormatter
argument_list|()
decl_stmt|;
DECL|field|UNITS_TO_LATEX
specifier|public
specifier|static
specifier|final
name|UnitsToLatexFormatter
name|UNITS_TO_LATEX
init|=
operator|new
name|UnitsToLatexFormatter
argument_list|()
decl_stmt|;
DECL|field|REMOVE_BRACES_FORMATTER
specifier|public
specifier|static
specifier|final
name|RemoveBracesFormatter
name|REMOVE_BRACES_FORMATTER
init|=
operator|new
name|RemoveBracesFormatter
argument_list|()
decl_stmt|;
DECL|field|HTML_TO_LATEX
specifier|public
specifier|static
specifier|final
name|HtmlToLatexFormatter
name|HTML_TO_LATEX
init|=
operator|new
name|HtmlToLatexFormatter
argument_list|()
decl_stmt|;
DECL|field|UNICODE_TO_LATEX
specifier|public
specifier|static
specifier|final
name|UnicodeToLatexFormatter
name|UNICODE_TO_LATEX
init|=
operator|new
name|UnicodeToLatexFormatter
argument_list|()
decl_stmt|;
DECL|field|LATEX_TO_UNICODE
specifier|public
specifier|static
specifier|final
name|LatexToUnicodeFormatter
name|LATEX_TO_UNICODE
init|=
operator|new
name|LatexToUnicodeFormatter
argument_list|()
decl_stmt|;
DECL|field|CLEAR
specifier|public
specifier|static
specifier|final
name|ClearFormatter
name|CLEAR
init|=
operator|new
name|ClearFormatter
argument_list|()
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
name|Arrays
operator|.
name|asList
argument_list|(
name|NORMALIZE_PAGES
argument_list|,
name|ORDINALS_TO_LATEX_SUPERSCRIPT
argument_list|,
name|NORMALIZE_DATE
argument_list|,
name|NORMALIZE_PERSON_NAMES
argument_list|,
name|LATEX_CLEANUP
argument_list|,
name|NORMALIZE_MONTH
argument_list|,
name|UNITS_TO_LATEX
argument_list|,
name|REMOVE_BRACES_FORMATTER
argument_list|,
name|HTML_TO_LATEX
argument_list|,
name|UNICODE_TO_LATEX
argument_list|,
name|CLEAR
argument_list|)
decl_stmt|;
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
name|HTML_TO_LATEX
argument_list|,
name|UNICODE_TO_LATEX
argument_list|,
name|LATEX_TO_UNICODE
argument_list|)
decl_stmt|;
block|}
end_class

end_unit

