begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.importer.fetcher
package|package
name|net
operator|.
name|sf
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
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|testutils
operator|.
name|category
operator|.
name|FetcherTests
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Assert
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Before
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|experimental
operator|.
name|categories
operator|.
name|Category
import|;
end_import

begin_class
annotation|@
name|Category
argument_list|(
name|FetcherTests
operator|.
name|class
argument_list|)
DECL|class|SpringerLinkTest
specifier|public
class|class
name|SpringerLinkTest
block|{
DECL|field|finder
specifier|private
name|SpringerLink
name|finder
decl_stmt|;
DECL|field|entry
specifier|private
name|BibEntry
name|entry
decl_stmt|;
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|finder
operator|=
operator|new
name|SpringerLink
argument_list|()
expr_stmt|;
name|entry
operator|=
operator|new
name|BibEntry
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|NullPointerException
operator|.
name|class
argument_list|)
DECL|method|rejectNullParameter ()
specifier|public
name|void
name|rejectNullParameter
parameter_list|()
throws|throws
name|IOException
block|{
name|finder
operator|.
name|findFullText
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|fail
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|doiNotPresent ()
specifier|public
name|void
name|doiNotPresent
parameter_list|()
throws|throws
name|IOException
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|finder
operator|.
name|findFullText
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|findByDOI ()
specifier|public
name|void
name|findByDOI
parameter_list|()
throws|throws
name|IOException
block|{
name|entry
operator|.
name|setField
argument_list|(
literal|"doi"
argument_list|,
literal|"10.1186/s13677-015-0042-8"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|URL
argument_list|(
literal|"http://link.springer.com/content/pdf/10.1186/s13677-015-0042-8.pdf"
argument_list|)
argument_list|)
argument_list|,
name|finder
operator|.
name|findFullText
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|notFoundByDOI ()
specifier|public
name|void
name|notFoundByDOI
parameter_list|()
throws|throws
name|IOException
block|{
name|entry
operator|.
name|setField
argument_list|(
literal|"doi"
argument_list|,
literal|"10.1186/unknown-doi"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|finder
operator|.
name|findFullText
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

