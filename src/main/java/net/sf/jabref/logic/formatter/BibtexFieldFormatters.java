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
name|PageNumbersFormatter
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
name|SuperscriptFormatter
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
argument_list|)
decl_stmt|;
block|}
end_class

end_unit

