begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.importer.fetcher
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|fetcher
package|;
end_package

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
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|FetcherException
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
name|util
operator|.
name|Version
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
name|entry
operator|.
name|BibEntry
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
name|entry
operator|.
name|FieldName
import|;
end_import

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
name|assertFalse
import|;
end_import

begin_class
annotation|@
name|FetcherTest
DECL|class|MrDLibFetcherTest
specifier|public
class|class
name|MrDLibFetcherTest
block|{
DECL|field|fetcher
specifier|private
name|MrDLibFetcher
name|fetcher
decl_stmt|;
annotation|@
name|BeforeEach
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|fetcher
operator|=
operator|new
name|MrDLibFetcher
argument_list|(
literal|""
argument_list|,
name|Version
operator|.
name|parse
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPerformSearch ()
specifier|public
name|void
name|testPerformSearch
parameter_list|()
throws|throws
name|FetcherException
block|{
name|BibEntry
name|bibEntry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|TITLE
argument_list|,
literal|"lernen"
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|bibEntrys
init|=
name|fetcher
operator|.
name|performSearch
argument_list|(
name|bibEntry
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
name|bibEntrys
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPerformSearchForHornecker2006 ()
specifier|public
name|void
name|testPerformSearchForHornecker2006
parameter_list|()
throws|throws
name|FetcherException
block|{
name|BibEntry
name|bibEntry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|bibEntry
operator|.
name|setCiteKey
argument_list|(
literal|"Hornecker:2006:GGT:1124772.1124838"
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|ADDRESS
argument_list|,
literal|"New York, NY, USA"
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|AUTHOR
argument_list|,
literal|"Hornecker, Eva and Buur, Jacob"
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|BOOKTITLE
argument_list|,
literal|"Proceedings of the SIGCHI Conference on Human Factors in Computing Systems"
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|DOI
argument_list|,
literal|"10.1145/1124772.1124838"
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|ISBN
argument_list|,
literal|"1-59593-372-7"
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|KEYWORDS
argument_list|,
literal|"CSCW,analysis,collaboration,design,framework,social interaction,tangible interaction,tangible interface"
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|PAGES
argument_list|,
literal|"437--446"
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|PUBLISHER
argument_list|,
literal|"ACM"
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|SERIES
argument_list|,
literal|"CHI '06"
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|TITLE
argument_list|,
literal|"{Getting a Grip on Tangible Interaction: A Framework on Physical Space and Social Interaction}"
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|URL
argument_list|,
literal|"http://doi.acm.org/10.1145/1124772.1124838"
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|YEAR
argument_list|,
literal|"2006"
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|bibEntrys
init|=
name|fetcher
operator|.
name|performSearch
argument_list|(
name|bibEntry
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
name|bibEntrys
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetName ()
specifier|public
name|void
name|testGetName
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"MDL_FETCHER"
argument_list|,
name|fetcher
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

