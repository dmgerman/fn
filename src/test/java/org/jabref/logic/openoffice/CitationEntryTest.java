begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.openoffice
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|openoffice
package|;
end_package

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
name|junit
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
name|assertNotEquals
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
name|assertTrue
import|;
end_import

begin_class
DECL|class|CitationEntryTest
specifier|public
class|class
name|CitationEntryTest
block|{
annotation|@
name|Test
DECL|method|testCitationEntryInitalPageInfo ()
specifier|public
name|void
name|testCitationEntryInitalPageInfo
parameter_list|()
block|{
name|CitationEntry
name|citationEntry
init|=
operator|new
name|CitationEntry
argument_list|(
literal|"RefMark"
argument_list|,
literal|"Context"
argument_list|,
literal|"Info"
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
name|citationEntry
operator|.
name|pageInfoChanged
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|citationEntry
operator|.
name|getPageInfo
argument_list|()
operator|.
name|isPresent
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Info"
argument_list|,
name|citationEntry
operator|.
name|getPageInfo
argument_list|()
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"RefMark"
argument_list|,
name|citationEntry
operator|.
name|getRefMarkName
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Context"
argument_list|,
name|citationEntry
operator|.
name|getContext
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testCitationEntryOptionalInitalPageInfo ()
specifier|public
name|void
name|testCitationEntryOptionalInitalPageInfo
parameter_list|()
block|{
name|CitationEntry
name|citationEntry
init|=
operator|new
name|CitationEntry
argument_list|(
literal|"RefMark"
argument_list|,
literal|"Context"
argument_list|,
name|Optional
operator|.
name|of
argument_list|(
literal|"Info"
argument_list|)
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
name|citationEntry
operator|.
name|pageInfoChanged
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|citationEntry
operator|.
name|getPageInfo
argument_list|()
operator|.
name|isPresent
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Info"
argument_list|,
name|citationEntry
operator|.
name|getPageInfo
argument_list|()
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"RefMark"
argument_list|,
name|citationEntry
operator|.
name|getRefMarkName
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Context"
argument_list|,
name|citationEntry
operator|.
name|getContext
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testCitationEntryInitalPageInfoChanged ()
specifier|public
name|void
name|testCitationEntryInitalPageInfoChanged
parameter_list|()
block|{
name|CitationEntry
name|citationEntry
init|=
operator|new
name|CitationEntry
argument_list|(
literal|"RefMark"
argument_list|,
literal|"Context"
argument_list|,
literal|"Info"
argument_list|)
decl_stmt|;
name|citationEntry
operator|.
name|setPageInfo
argument_list|(
literal|"Other info"
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|citationEntry
operator|.
name|pageInfoChanged
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|citationEntry
operator|.
name|getPageInfo
argument_list|()
operator|.
name|isPresent
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Other info"
argument_list|,
name|citationEntry
operator|.
name|getPageInfo
argument_list|()
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testCitationEntryInitalPageInfoRemoved ()
specifier|public
name|void
name|testCitationEntryInitalPageInfoRemoved
parameter_list|()
block|{
name|CitationEntry
name|citationEntry
init|=
operator|new
name|CitationEntry
argument_list|(
literal|"RefMark"
argument_list|,
literal|"Context"
argument_list|,
literal|"Info"
argument_list|)
decl_stmt|;
name|citationEntry
operator|.
name|setPageInfo
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|citationEntry
operator|.
name|pageInfoChanged
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|citationEntry
operator|.
name|getPageInfo
argument_list|()
operator|.
name|isPresent
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testCitationEntryNoInitalPageInfo ()
specifier|public
name|void
name|testCitationEntryNoInitalPageInfo
parameter_list|()
block|{
name|CitationEntry
name|citationEntry
init|=
operator|new
name|CitationEntry
argument_list|(
literal|"RefMark"
argument_list|,
literal|"Context"
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
name|citationEntry
operator|.
name|pageInfoChanged
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|citationEntry
operator|.
name|getPageInfo
argument_list|()
operator|.
name|isPresent
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testCitationEntryNoInitalPageInfoChanged ()
specifier|public
name|void
name|testCitationEntryNoInitalPageInfoChanged
parameter_list|()
block|{
name|CitationEntry
name|citationEntry
init|=
operator|new
name|CitationEntry
argument_list|(
literal|"RefMark"
argument_list|,
literal|"Context"
argument_list|)
decl_stmt|;
name|citationEntry
operator|.
name|setPageInfo
argument_list|(
literal|"Other info"
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|citationEntry
operator|.
name|pageInfoChanged
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|citationEntry
operator|.
name|getPageInfo
argument_list|()
operator|.
name|isPresent
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Other info"
argument_list|,
name|citationEntry
operator|.
name|getPageInfo
argument_list|()
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testCitationEntryEquals ()
specifier|public
name|void
name|testCitationEntryEquals
parameter_list|()
block|{
name|CitationEntry
name|citationEntry1
init|=
operator|new
name|CitationEntry
argument_list|(
literal|"RefMark"
argument_list|,
literal|"Context"
argument_list|,
literal|"Info"
argument_list|)
decl_stmt|;
name|CitationEntry
name|citationEntry2
init|=
operator|new
name|CitationEntry
argument_list|(
literal|"RefMark2"
argument_list|,
literal|"Context"
argument_list|,
literal|"Info"
argument_list|)
decl_stmt|;
name|CitationEntry
name|citationEntry3
init|=
operator|new
name|CitationEntry
argument_list|(
literal|"RefMark"
argument_list|,
literal|"Other Context"
argument_list|,
literal|"Other Info"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|citationEntry1
argument_list|,
name|citationEntry1
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|citationEntry1
argument_list|,
name|citationEntry3
argument_list|)
expr_stmt|;
name|assertNotEquals
argument_list|(
name|citationEntry1
argument_list|,
name|citationEntry2
argument_list|)
expr_stmt|;
name|assertNotEquals
argument_list|(
name|citationEntry1
argument_list|,
literal|"Random String"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testCitationEntryCompareTo ()
specifier|public
name|void
name|testCitationEntryCompareTo
parameter_list|()
block|{
name|CitationEntry
name|citationEntry1
init|=
operator|new
name|CitationEntry
argument_list|(
literal|"RefMark"
argument_list|,
literal|"Context"
argument_list|,
literal|"Info"
argument_list|)
decl_stmt|;
name|CitationEntry
name|citationEntry2
init|=
operator|new
name|CitationEntry
argument_list|(
literal|"RefMark2"
argument_list|,
literal|"Context"
argument_list|,
literal|"Info"
argument_list|)
decl_stmt|;
name|CitationEntry
name|citationEntry3
init|=
operator|new
name|CitationEntry
argument_list|(
literal|"RefMark"
argument_list|,
literal|"Other Context"
argument_list|,
literal|"Other Info"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|0
argument_list|,
name|citationEntry1
operator|.
name|compareTo
argument_list|(
name|citationEntry3
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
operator|-
literal|1
argument_list|,
name|citationEntry1
operator|.
name|compareTo
argument_list|(
name|citationEntry2
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|citationEntry2
operator|.
name|compareTo
argument_list|(
name|citationEntry1
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

