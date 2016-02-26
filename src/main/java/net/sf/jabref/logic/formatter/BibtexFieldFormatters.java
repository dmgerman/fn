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
DECL|field|PAGE_NUMBERS
specifier|public
specifier|static
specifier|final
name|PageNumbersFormatter
name|PAGE_NUMBERS
init|=
operator|new
name|PageNumbersFormatter
argument_list|()
decl_stmt|;
DECL|field|SUPERSCRIPTS
specifier|public
specifier|static
specifier|final
name|SuperscriptFormatter
name|SUPERSCRIPTS
init|=
operator|new
name|SuperscriptFormatter
argument_list|()
decl_stmt|;
DECL|field|DATE
specifier|public
specifier|static
specifier|final
name|DateFormatter
name|DATE
init|=
operator|new
name|DateFormatter
argument_list|()
decl_stmt|;
DECL|field|MONTH_FORMATTER
specifier|public
specifier|static
specifier|final
name|MonthFormatter
name|MONTH_FORMATTER
init|=
operator|new
name|MonthFormatter
argument_list|()
decl_stmt|;
DECL|field|AUTHORS_FORMATTER
specifier|public
specifier|static
specifier|final
name|AuthorsFormatter
name|AUTHORS_FORMATTER
init|=
operator|new
name|AuthorsFormatter
argument_list|()
decl_stmt|;
DECL|field|LATEX_FORMATTER
specifier|public
specifier|static
specifier|final
name|LatexFormatter
name|LATEX_FORMATTER
init|=
operator|new
name|LatexFormatter
argument_list|()
decl_stmt|;
DECL|field|UNIT_FORMATTER
specifier|public
specifier|static
specifier|final
name|UnitFormatter
name|UNIT_FORMATTER
init|=
operator|new
name|UnitFormatter
argument_list|()
decl_stmt|;
DECL|field|TRIM_FORMATTER
specifier|public
specifier|static
specifier|final
name|TrimFormatter
name|TRIM_FORMATTER
init|=
operator|new
name|TrimFormatter
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
name|HTMLToLatexFormatter
name|HTML_TO_LATEX
init|=
operator|new
name|HTMLToLatexFormatter
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
name|PAGE_NUMBERS
argument_list|,
name|SUPERSCRIPTS
argument_list|,
name|DATE
argument_list|,
name|AUTHORS_FORMATTER
argument_list|,
name|LATEX_FORMATTER
argument_list|,
name|MONTH_FORMATTER
argument_list|,
name|UNIT_FORMATTER
argument_list|,
name|TRIM_FORMATTER
argument_list|,
name|REMOVE_BRACES_FORMATTER
argument_list|,
name|HTML_TO_LATEX
argument_list|,
name|UNICODE_TO_LATEX
argument_list|)
decl_stmt|;
block|}
end_class

end_unit

