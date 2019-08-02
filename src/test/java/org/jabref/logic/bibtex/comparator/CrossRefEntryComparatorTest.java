begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.bibtex.comparator
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|bibtex
operator|.
name|comparator
package|;
end_package

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
name|field
operator|.
name|StandardField
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
name|AfterEach
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
DECL|class|CrossRefEntryComparatorTest
specifier|public
class|class
name|CrossRefEntryComparatorTest
block|{
DECL|field|comparator
specifier|private
name|CrossRefEntryComparator
name|comparator
decl_stmt|;
annotation|@
name|BeforeEach
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|comparator
operator|=
operator|new
name|CrossRefEntryComparator
argument_list|()
expr_stmt|;
block|}
annotation|@
name|AfterEach
DECL|method|tearDown ()
specifier|public
name|void
name|tearDown
parameter_list|()
block|{
name|comparator
operator|=
literal|null
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|isEqualForEntriesWithoutCrossRef ()
specifier|public
name|void
name|isEqualForEntriesWithoutCrossRef
parameter_list|()
block|{
name|BibEntry
name|e1
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|BibEntry
name|e2
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|0
argument_list|,
name|comparator
operator|.
name|compare
argument_list|(
name|e1
argument_list|,
name|e2
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|isEqualForEntriesWithCrossRef ()
specifier|public
name|void
name|isEqualForEntriesWithCrossRef
parameter_list|()
block|{
name|BibEntry
name|e1
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|e1
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|CROSSREF
argument_list|,
literal|"1"
argument_list|)
expr_stmt|;
name|BibEntry
name|e2
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|e2
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|CROSSREF
argument_list|,
literal|"2"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|0
argument_list|,
name|comparator
operator|.
name|compare
argument_list|(
name|e1
argument_list|,
name|e2
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|isGreaterForEntriesWithoutCrossRef ()
specifier|public
name|void
name|isGreaterForEntriesWithoutCrossRef
parameter_list|()
block|{
name|BibEntry
name|e1
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|BibEntry
name|e2
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|e2
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|CROSSREF
argument_list|,
literal|"1"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|comparator
operator|.
name|compare
argument_list|(
name|e1
argument_list|,
name|e2
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|isSmallerForEntriesWithCrossRef ()
specifier|public
name|void
name|isSmallerForEntriesWithCrossRef
parameter_list|()
block|{
name|BibEntry
name|e1
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|e1
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|CROSSREF
argument_list|,
literal|"1"
argument_list|)
expr_stmt|;
name|BibEntry
name|e2
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
operator|-
literal|1
argument_list|,
name|comparator
operator|.
name|compare
argument_list|(
name|e1
argument_list|,
name|e2
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

