begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.integrity
package|package
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
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
name|Assert
operator|.
name|assertEquals
import|;
end_import

begin_class
DECL|class|NoBibTexFieldCheckerTest
specifier|public
class|class
name|NoBibTexFieldCheckerTest
block|{
DECL|field|checker
specifier|private
name|NoBibtexFieldChecker
name|checker
init|=
operator|new
name|NoBibtexFieldChecker
argument_list|()
decl_stmt|;
annotation|@
name|Test
DECL|method|addressIsNotRecognizedAsBibLaTeXOnlyField ()
specifier|public
name|void
name|addressIsNotRecognizedAsBibLaTeXOnlyField
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
literal|"address"
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
DECL|method|journalIsNotRecognizedAsBibLaTeXOnlyField ()
specifier|public
name|void
name|journalIsNotRecognizedAsBibLaTeXOnlyField
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
literal|"journal"
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
DECL|method|journaltitleIsRecognizedAsBibLaTeXOnlyField ()
specifier|public
name|void
name|journaltitleIsRecognizedAsBibLaTeXOnlyField
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
literal|"journaltitle"
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
literal|"BibLaTeX field only"
argument_list|,
name|entry
argument_list|,
literal|"journaltitle"
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
name|messages
argument_list|,
name|Collections
operator|.
name|singletonList
argument_list|(
name|message
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|locationIsRecognizedAsBibLaTeXOnlyField ()
specifier|public
name|void
name|locationIsRecognizedAsBibLaTeXOnlyField
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
literal|"location"
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
literal|"BibLaTeX field only"
argument_list|,
name|entry
argument_list|,
literal|"location"
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
name|messages
argument_list|,
name|Collections
operator|.
name|singletonList
argument_list|(
name|message
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

