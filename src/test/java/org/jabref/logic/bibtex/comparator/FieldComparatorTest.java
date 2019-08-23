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
name|InternalField
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
name|OrFields
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
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|types
operator|.
name|StandardEntryType
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
DECL|class|FieldComparatorTest
specifier|public
class|class
name|FieldComparatorTest
block|{
annotation|@
name|Test
DECL|method|compareMonthFieldIdentity ()
specifier|public
name|void
name|compareMonthFieldIdentity
parameter_list|()
throws|throws
name|Exception
block|{
name|FieldComparator
name|comparator
init|=
operator|new
name|FieldComparator
argument_list|(
name|StandardField
operator|.
name|MONTH
argument_list|)
decl_stmt|;
name|BibEntry
name|equal
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|equal
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|MONTH
argument_list|,
literal|"1"
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
name|equal
argument_list|,
name|equal
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|compareMonthFieldEquality ()
specifier|public
name|void
name|compareMonthFieldEquality
parameter_list|()
throws|throws
name|Exception
block|{
name|FieldComparator
name|comparator
init|=
operator|new
name|FieldComparator
argument_list|(
name|StandardField
operator|.
name|MONTH
argument_list|)
decl_stmt|;
name|BibEntry
name|equal
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|equal
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|MONTH
argument_list|,
literal|"1"
argument_list|)
expr_stmt|;
name|BibEntry
name|equal2
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|equal2
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|MONTH
argument_list|,
literal|"1"
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
name|equal
argument_list|,
name|equal2
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|compareMonthFieldBiggerAscending ()
specifier|public
name|void
name|compareMonthFieldBiggerAscending
parameter_list|()
throws|throws
name|Exception
block|{
name|FieldComparator
name|comparator
init|=
operator|new
name|FieldComparator
argument_list|(
name|StandardField
operator|.
name|MONTH
argument_list|)
decl_stmt|;
name|BibEntry
name|smaller
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|smaller
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|MONTH
argument_list|,
literal|"jan"
argument_list|)
expr_stmt|;
name|BibEntry
name|bigger
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|bigger
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|MONTH
argument_list|,
literal|"feb"
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
name|bigger
argument_list|,
name|smaller
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|compareMonthFieldBiggerDescending ()
specifier|public
name|void
name|compareMonthFieldBiggerDescending
parameter_list|()
throws|throws
name|Exception
block|{
name|FieldComparator
name|comparator
init|=
operator|new
name|FieldComparator
argument_list|(
operator|new
name|OrFields
argument_list|(
name|StandardField
operator|.
name|MONTH
argument_list|)
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|BibEntry
name|smaller
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|smaller
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|MONTH
argument_list|,
literal|"feb"
argument_list|)
expr_stmt|;
name|BibEntry
name|bigger
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|bigger
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|MONTH
argument_list|,
literal|"jan"
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
name|bigger
argument_list|,
name|smaller
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|compareYearFieldIdentity ()
specifier|public
name|void
name|compareYearFieldIdentity
parameter_list|()
throws|throws
name|Exception
block|{
name|FieldComparator
name|comparator
init|=
operator|new
name|FieldComparator
argument_list|(
name|StandardField
operator|.
name|YEAR
argument_list|)
decl_stmt|;
name|BibEntry
name|equal
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|equal
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|YEAR
argument_list|,
literal|"2016"
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
name|equal
argument_list|,
name|equal
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|compareYearFieldEquality ()
specifier|public
name|void
name|compareYearFieldEquality
parameter_list|()
throws|throws
name|Exception
block|{
name|FieldComparator
name|comparator
init|=
operator|new
name|FieldComparator
argument_list|(
name|StandardField
operator|.
name|YEAR
argument_list|)
decl_stmt|;
name|BibEntry
name|equal
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|equal
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|YEAR
argument_list|,
literal|"2016"
argument_list|)
expr_stmt|;
name|BibEntry
name|equal2
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|equal2
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|YEAR
argument_list|,
literal|"2016"
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
name|equal
argument_list|,
name|equal2
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|compareYearFieldBiggerAscending ()
specifier|public
name|void
name|compareYearFieldBiggerAscending
parameter_list|()
throws|throws
name|Exception
block|{
name|FieldComparator
name|comparator
init|=
operator|new
name|FieldComparator
argument_list|(
name|StandardField
operator|.
name|YEAR
argument_list|)
decl_stmt|;
name|BibEntry
name|smaller
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|smaller
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|YEAR
argument_list|,
literal|"2000"
argument_list|)
expr_stmt|;
name|BibEntry
name|bigger
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|bigger
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|YEAR
argument_list|,
literal|"2016"
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
name|bigger
argument_list|,
name|smaller
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|compareYearFieldBiggerDescending ()
specifier|public
name|void
name|compareYearFieldBiggerDescending
parameter_list|()
throws|throws
name|Exception
block|{
name|FieldComparator
name|comparator
init|=
operator|new
name|FieldComparator
argument_list|(
operator|new
name|OrFields
argument_list|(
name|StandardField
operator|.
name|YEAR
argument_list|)
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|BibEntry
name|smaller
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|smaller
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|YEAR
argument_list|,
literal|"2016"
argument_list|)
expr_stmt|;
name|BibEntry
name|bigger
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|bigger
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|YEAR
argument_list|,
literal|"2000"
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
name|bigger
argument_list|,
name|smaller
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|compareTypeFieldIdentity ()
specifier|public
name|void
name|compareTypeFieldIdentity
parameter_list|()
throws|throws
name|Exception
block|{
name|FieldComparator
name|comparator
init|=
operator|new
name|FieldComparator
argument_list|(
name|InternalField
operator|.
name|TYPE_HEADER
argument_list|)
decl_stmt|;
name|BibEntry
name|equal
init|=
operator|new
name|BibEntry
argument_list|(
name|StandardEntryType
operator|.
name|Article
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|0
argument_list|,
name|comparator
operator|.
name|compare
argument_list|(
name|equal
argument_list|,
name|equal
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|compareTypeFieldEquality ()
specifier|public
name|void
name|compareTypeFieldEquality
parameter_list|()
throws|throws
name|Exception
block|{
name|FieldComparator
name|comparator
init|=
operator|new
name|FieldComparator
argument_list|(
name|InternalField
operator|.
name|TYPE_HEADER
argument_list|)
decl_stmt|;
name|BibEntry
name|equal
init|=
operator|new
name|BibEntry
argument_list|(
name|StandardEntryType
operator|.
name|Article
argument_list|)
decl_stmt|;
name|equal
operator|.
name|setId
argument_list|(
literal|"1"
argument_list|)
expr_stmt|;
name|BibEntry
name|equal2
init|=
operator|new
name|BibEntry
argument_list|(
name|StandardEntryType
operator|.
name|Article
argument_list|)
decl_stmt|;
name|equal2
operator|.
name|setId
argument_list|(
literal|"1"
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
name|equal
argument_list|,
name|equal2
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|compareTypeFieldBiggerAscending ()
specifier|public
name|void
name|compareTypeFieldBiggerAscending
parameter_list|()
throws|throws
name|Exception
block|{
name|FieldComparator
name|comparator
init|=
operator|new
name|FieldComparator
argument_list|(
name|InternalField
operator|.
name|TYPE_HEADER
argument_list|)
decl_stmt|;
name|BibEntry
name|smaller
init|=
operator|new
name|BibEntry
argument_list|(
name|StandardEntryType
operator|.
name|Article
argument_list|)
decl_stmt|;
name|BibEntry
name|bigger
init|=
operator|new
name|BibEntry
argument_list|(
name|StandardEntryType
operator|.
name|Book
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|comparator
operator|.
name|compare
argument_list|(
name|bigger
argument_list|,
name|smaller
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|compareTypeFieldBiggerDescending ()
specifier|public
name|void
name|compareTypeFieldBiggerDescending
parameter_list|()
throws|throws
name|Exception
block|{
name|FieldComparator
name|comparator
init|=
operator|new
name|FieldComparator
argument_list|(
operator|new
name|OrFields
argument_list|(
name|InternalField
operator|.
name|TYPE_HEADER
argument_list|)
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|BibEntry
name|bigger
init|=
operator|new
name|BibEntry
argument_list|(
name|StandardEntryType
operator|.
name|Article
argument_list|)
decl_stmt|;
name|BibEntry
name|smaller
init|=
operator|new
name|BibEntry
argument_list|(
name|StandardEntryType
operator|.
name|Book
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|comparator
operator|.
name|compare
argument_list|(
name|bigger
argument_list|,
name|smaller
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|compareStringFieldsIdentity ()
specifier|public
name|void
name|compareStringFieldsIdentity
parameter_list|()
throws|throws
name|Exception
block|{
name|FieldComparator
name|comparator
init|=
operator|new
name|FieldComparator
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|)
decl_stmt|;
name|BibEntry
name|equal
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|equal
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|,
literal|"title"
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
name|equal
argument_list|,
name|equal
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|compareStringFieldsEquality ()
specifier|public
name|void
name|compareStringFieldsEquality
parameter_list|()
throws|throws
name|Exception
block|{
name|FieldComparator
name|comparator
init|=
operator|new
name|FieldComparator
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|)
decl_stmt|;
name|BibEntry
name|equal
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|equal
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|,
literal|"title"
argument_list|)
expr_stmt|;
name|BibEntry
name|equal2
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|equal2
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|,
literal|"title"
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
name|equal
argument_list|,
name|equal2
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|compareStringFieldsBiggerAscending ()
specifier|public
name|void
name|compareStringFieldsBiggerAscending
parameter_list|()
throws|throws
name|Exception
block|{
name|FieldComparator
name|comparator
init|=
operator|new
name|FieldComparator
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|)
decl_stmt|;
name|BibEntry
name|bigger
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|bigger
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|,
literal|"b"
argument_list|)
expr_stmt|;
name|BibEntry
name|smaller
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|smaller
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|,
literal|"a"
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
name|bigger
argument_list|,
name|smaller
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|compareStringFieldsBiggerDescending ()
specifier|public
name|void
name|compareStringFieldsBiggerDescending
parameter_list|()
throws|throws
name|Exception
block|{
name|FieldComparator
name|comparator
init|=
operator|new
name|FieldComparator
argument_list|(
operator|new
name|OrFields
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|)
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|BibEntry
name|bigger
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|bigger
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|,
literal|"a"
argument_list|)
expr_stmt|;
name|BibEntry
name|smaller
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|smaller
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|,
literal|"b"
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
name|bigger
argument_list|,
name|smaller
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

