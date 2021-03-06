begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.integrity
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|integrity
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
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
DECL|class|NoBibTexFieldCheckerTest
class|class
name|NoBibTexFieldCheckerTest
block|{
DECL|field|checker
specifier|private
specifier|final
name|NoBibtexFieldChecker
name|checker
init|=
operator|new
name|NoBibtexFieldChecker
argument_list|()
decl_stmt|;
annotation|@
name|Test
DECL|method|abstractIsNotRecognizedAsBiblatexOnlyField ()
name|void
name|abstractIsNotRecognizedAsBiblatexOnlyField
parameter_list|()
block|{
name|BibEntry
name|entry
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
name|ABSTRACT
argument_list|,
literal|"test"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|checker
operator|.
name|check
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|addressIsNotRecognizedAsBiblatexOnlyField ()
name|void
name|addressIsNotRecognizedAsBiblatexOnlyField
parameter_list|()
block|{
name|BibEntry
name|entry
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
name|ADDRESS
argument_list|,
literal|"test"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|checker
operator|.
name|check
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|afterwordIsRecognizedAsBiblatexOnlyField ()
name|void
name|afterwordIsRecognizedAsBiblatexOnlyField
parameter_list|()
block|{
name|BibEntry
name|entry
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
name|AFTERWORD
argument_list|,
literal|"test"
argument_list|)
expr_stmt|;
name|IntegrityMessage
name|message
init|=
operator|new
name|IntegrityMessage
argument_list|(
literal|"biblatex field only"
argument_list|,
name|entry
argument_list|,
name|StandardField
operator|.
name|AFTERWORD
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|IntegrityMessage
argument_list|>
name|messages
init|=
name|checker
operator|.
name|check
argument_list|(
name|entry
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
name|message
argument_list|)
argument_list|,
name|messages
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|arbitraryNonBiblatexFieldIsNotRecognizedAsBiblatexOnlyField ()
name|void
name|arbitraryNonBiblatexFieldIsNotRecognizedAsBiblatexOnlyField
parameter_list|()
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
operator|new
name|UnknownField
argument_list|(
literal|"fieldNameNotDefinedInThebiblatexManual"
argument_list|)
argument_list|,
literal|"test"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|checker
operator|.
name|check
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|commentIsNotRecognizedAsBiblatexOnlyField ()
name|void
name|commentIsNotRecognizedAsBiblatexOnlyField
parameter_list|()
block|{
name|BibEntry
name|entry
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
name|COMMENT
argument_list|,
literal|"test"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|checker
operator|.
name|check
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|instituationIsNotRecognizedAsBiblatexOnlyField ()
name|void
name|instituationIsNotRecognizedAsBiblatexOnlyField
parameter_list|()
block|{
name|BibEntry
name|entry
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
name|INSTITUTION
argument_list|,
literal|"test"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|checker
operator|.
name|check
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|journalIsNotRecognizedAsBiblatexOnlyField ()
name|void
name|journalIsNotRecognizedAsBiblatexOnlyField
parameter_list|()
block|{
name|BibEntry
name|entry
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
name|JOURNAL
argument_list|,
literal|"test"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|checker
operator|.
name|check
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|journaltitleIsRecognizedAsBiblatexOnlyField ()
name|void
name|journaltitleIsRecognizedAsBiblatexOnlyField
parameter_list|()
block|{
name|BibEntry
name|entry
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
name|JOURNALTITLE
argument_list|,
literal|"test"
argument_list|)
expr_stmt|;
name|IntegrityMessage
name|message
init|=
operator|new
name|IntegrityMessage
argument_list|(
literal|"biblatex field only"
argument_list|,
name|entry
argument_list|,
name|StandardField
operator|.
name|JOURNALTITLE
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|IntegrityMessage
argument_list|>
name|messages
init|=
name|checker
operator|.
name|check
argument_list|(
name|entry
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
name|message
argument_list|)
argument_list|,
name|messages
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|keywordsNotRecognizedAsBiblatexOnlyField ()
name|void
name|keywordsNotRecognizedAsBiblatexOnlyField
parameter_list|()
block|{
name|BibEntry
name|entry
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
name|KEYWORDS
argument_list|,
literal|"test"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|checker
operator|.
name|check
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|locationIsRecognizedAsBiblatexOnlyField ()
name|void
name|locationIsRecognizedAsBiblatexOnlyField
parameter_list|()
block|{
name|BibEntry
name|entry
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
name|LOCATION
argument_list|,
literal|"test"
argument_list|)
expr_stmt|;
name|IntegrityMessage
name|message
init|=
operator|new
name|IntegrityMessage
argument_list|(
literal|"biblatex field only"
argument_list|,
name|entry
argument_list|,
name|StandardField
operator|.
name|LOCATION
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|IntegrityMessage
argument_list|>
name|messages
init|=
name|checker
operator|.
name|check
argument_list|(
name|entry
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
name|message
argument_list|)
argument_list|,
name|messages
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|reviewIsNotRecognizedAsBiblatexOnlyField ()
name|void
name|reviewIsNotRecognizedAsBiblatexOnlyField
parameter_list|()
block|{
name|BibEntry
name|entry
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
name|REVIEW
argument_list|,
literal|"test"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|checker
operator|.
name|check
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

