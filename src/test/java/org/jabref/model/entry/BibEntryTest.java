begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.entry
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
package|;
end_package

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
name|database
operator|.
name|BibDatabase
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
name|BibField
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
name|FieldPriority
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
name|field
operator|.
name|UnknownField
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

begin_class
DECL|class|BibEntryTest
specifier|public
class|class
name|BibEntryTest
block|{
DECL|field|entry
specifier|private
name|BibEntry
name|entry
decl_stmt|;
annotation|@
name|BeforeEach
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|entry
operator|=
operator|new
name|BibEntry
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
name|entry
operator|=
literal|null
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getFieldIsCaseInsensitive ()
specifier|public
name|void
name|getFieldIsCaseInsensitive
parameter_list|()
throws|throws
name|Exception
block|{
name|entry
operator|.
name|setField
argument_list|(
operator|new
name|UnknownField
argument_list|(
literal|"TeSt"
argument_list|)
argument_list|,
literal|"value"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"value"
argument_list|)
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
operator|new
name|UnknownField
argument_list|(
literal|"tEsT"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getFieldWorksWithBibFieldAsWell ()
specifier|public
name|void
name|getFieldWorksWithBibFieldAsWell
parameter_list|()
throws|throws
name|Exception
block|{
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|AUTHOR
argument_list|,
literal|"value"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"value"
argument_list|)
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
operator|new
name|BibField
argument_list|(
name|StandardField
operator|.
name|AUTHOR
argument_list|,
name|FieldPriority
operator|.
name|IMPORTANT
argument_list|)
operator|.
name|getField
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|setFieldWorksWithBibFieldAsWell ()
specifier|public
name|void
name|setFieldWorksWithBibFieldAsWell
parameter_list|()
throws|throws
name|Exception
block|{
name|entry
operator|.
name|setField
argument_list|(
operator|new
name|BibField
argument_list|(
name|StandardField
operator|.
name|AUTHOR
argument_list|,
name|FieldPriority
operator|.
name|IMPORTANT
argument_list|)
operator|.
name|getField
argument_list|()
argument_list|,
literal|"value"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"value"
argument_list|)
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
name|StandardField
operator|.
name|AUTHOR
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|clonedBibentryHasUniqueID ()
specifier|public
name|void
name|clonedBibentryHasUniqueID
parameter_list|()
throws|throws
name|Exception
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|BibEntry
name|entryClone
init|=
operator|(
name|BibEntry
operator|)
name|entry
operator|.
name|clone
argument_list|()
decl_stmt|;
name|assertNotEquals
argument_list|(
name|entry
operator|.
name|getId
argument_list|()
argument_list|,
name|entryClone
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetAndAddToLinkedFileList ()
specifier|public
name|void
name|testGetAndAddToLinkedFileList
parameter_list|()
block|{
name|List
argument_list|<
name|LinkedFile
argument_list|>
name|files
init|=
name|entry
operator|.
name|getFiles
argument_list|()
decl_stmt|;
name|files
operator|.
name|add
argument_list|(
operator|new
name|LinkedFile
argument_list|(
literal|""
argument_list|,
literal|""
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setFiles
argument_list|(
name|files
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|LinkedFile
argument_list|(
literal|""
argument_list|,
literal|""
argument_list|,
literal|""
argument_list|)
argument_list|)
argument_list|,
name|entry
operator|.
name|getFiles
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetEmptyKeywords ()
specifier|public
name|void
name|testGetEmptyKeywords
parameter_list|()
block|{
name|KeywordList
name|actual
init|=
name|entry
operator|.
name|getKeywords
argument_list|(
literal|','
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
operator|new
name|KeywordList
argument_list|()
argument_list|,
name|actual
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetSingleKeywords ()
specifier|public
name|void
name|testGetSingleKeywords
parameter_list|()
block|{
name|entry
operator|.
name|addKeyword
argument_list|(
literal|"kw"
argument_list|,
literal|','
argument_list|)
expr_stmt|;
name|KeywordList
name|actual
init|=
name|entry
operator|.
name|getKeywords
argument_list|(
literal|','
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
operator|new
name|KeywordList
argument_list|(
operator|new
name|Keyword
argument_list|(
literal|"kw"
argument_list|)
argument_list|)
argument_list|,
name|actual
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetKeywords ()
specifier|public
name|void
name|testGetKeywords
parameter_list|()
block|{
name|entry
operator|.
name|addKeyword
argument_list|(
literal|"kw"
argument_list|,
literal|','
argument_list|)
expr_stmt|;
name|entry
operator|.
name|addKeyword
argument_list|(
literal|"kw2"
argument_list|,
literal|','
argument_list|)
expr_stmt|;
name|entry
operator|.
name|addKeyword
argument_list|(
literal|"kw3"
argument_list|,
literal|','
argument_list|)
expr_stmt|;
name|KeywordList
name|actual
init|=
name|entry
operator|.
name|getKeywords
argument_list|(
literal|','
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
operator|new
name|KeywordList
argument_list|(
operator|new
name|Keyword
argument_list|(
literal|"kw"
argument_list|)
argument_list|,
operator|new
name|Keyword
argument_list|(
literal|"kw2"
argument_list|)
argument_list|,
operator|new
name|Keyword
argument_list|(
literal|"kw3"
argument_list|)
argument_list|)
argument_list|,
name|actual
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetEmptyResolvedKeywords ()
specifier|public
name|void
name|testGetEmptyResolvedKeywords
parameter_list|()
block|{
name|BibDatabase
name|database
init|=
operator|new
name|BibDatabase
argument_list|()
decl_stmt|;
name|BibEntry
name|entry2
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|CROSSREF
argument_list|,
literal|"entry2"
argument_list|)
expr_stmt|;
name|entry2
operator|.
name|setCiteKey
argument_list|(
literal|"entry2"
argument_list|)
expr_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry2
argument_list|)
expr_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|KeywordList
name|actual
init|=
name|entry
operator|.
name|getResolvedKeywords
argument_list|(
literal|','
argument_list|,
name|database
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
operator|new
name|KeywordList
argument_list|()
argument_list|,
name|actual
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetSingleResolvedKeywords ()
specifier|public
name|void
name|testGetSingleResolvedKeywords
parameter_list|()
block|{
name|BibDatabase
name|database
init|=
operator|new
name|BibDatabase
argument_list|()
decl_stmt|;
name|BibEntry
name|entry2
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|CROSSREF
argument_list|,
literal|"entry2"
argument_list|)
expr_stmt|;
name|entry2
operator|.
name|setCiteKey
argument_list|(
literal|"entry2"
argument_list|)
expr_stmt|;
name|entry2
operator|.
name|addKeyword
argument_list|(
literal|"kw"
argument_list|,
literal|','
argument_list|)
expr_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry2
argument_list|)
expr_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|KeywordList
name|actual
init|=
name|entry
operator|.
name|getResolvedKeywords
argument_list|(
literal|','
argument_list|,
name|database
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
operator|new
name|KeywordList
argument_list|(
operator|new
name|Keyword
argument_list|(
literal|"kw"
argument_list|)
argument_list|)
argument_list|,
name|actual
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetResolvedKeywords ()
specifier|public
name|void
name|testGetResolvedKeywords
parameter_list|()
block|{
name|BibDatabase
name|database
init|=
operator|new
name|BibDatabase
argument_list|()
decl_stmt|;
name|BibEntry
name|entry2
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|CROSSREF
argument_list|,
literal|"entry2"
argument_list|)
expr_stmt|;
name|entry2
operator|.
name|setCiteKey
argument_list|(
literal|"entry2"
argument_list|)
expr_stmt|;
name|entry2
operator|.
name|addKeyword
argument_list|(
literal|"kw"
argument_list|,
literal|','
argument_list|)
expr_stmt|;
name|entry2
operator|.
name|addKeyword
argument_list|(
literal|"kw2"
argument_list|,
literal|','
argument_list|)
expr_stmt|;
name|entry2
operator|.
name|addKeyword
argument_list|(
literal|"kw3"
argument_list|,
literal|','
argument_list|)
expr_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry2
argument_list|)
expr_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|KeywordList
name|actual
init|=
name|entry
operator|.
name|getResolvedKeywords
argument_list|(
literal|','
argument_list|,
name|database
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
operator|new
name|KeywordList
argument_list|(
operator|new
name|Keyword
argument_list|(
literal|"kw"
argument_list|)
argument_list|,
operator|new
name|Keyword
argument_list|(
literal|"kw2"
argument_list|)
argument_list|,
operator|new
name|Keyword
argument_list|(
literal|"kw3"
argument_list|)
argument_list|)
argument_list|,
name|actual
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

