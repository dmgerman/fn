begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.formatter.bibtexfields
package|package
name|org
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
name|org
operator|.
name|jabref
operator|.
name|testutils
operator|.
name|category
operator|.
name|FetcherTest
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|BeforeEach
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Test
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Assertions
operator|.
name|assertEquals
import|;
end_import

begin_class
annotation|@
name|FetcherTest
DECL|class|ShortenDOIFormatterTest
class|class
name|ShortenDOIFormatterTest
block|{
DECL|field|formatter
specifier|private
name|ShortenDOIFormatter
name|formatter
decl_stmt|;
annotation|@
name|BeforeEach
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|formatter
operator|=
operator|new
name|ShortenDOIFormatter
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|formatDoi ()
specifier|public
name|void
name|formatDoi
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"10/adc"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"10.1006/jmbi.1998.2354"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

