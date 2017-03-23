begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.search
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|search
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
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
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
name|runner
operator|.
name|RunWith
import|;
end_import

begin_import
import|import
name|org
operator|.
name|mockito
operator|.
name|ArgumentCaptor
import|;
end_import

begin_import
import|import
name|org
operator|.
name|mockito
operator|.
name|Captor
import|;
end_import

begin_import
import|import
name|org
operator|.
name|mockito
operator|.
name|Mock
import|;
end_import

begin_import
import|import
name|org
operator|.
name|mockito
operator|.
name|runners
operator|.
name|MockitoJUnitRunner
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

begin_import
import|import static
name|org
operator|.
name|mockito
operator|.
name|Mockito
operator|.
name|atLeastOnce
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|mockito
operator|.
name|Mockito
operator|.
name|verify
import|;
end_import

begin_class
annotation|@
name|RunWith
argument_list|(
name|MockitoJUnitRunner
operator|.
name|class
argument_list|)
DECL|class|SearchQueryHighlightObservableTest
specifier|public
class|class
name|SearchQueryHighlightObservableTest
block|{
DECL|field|captor
annotation|@
name|Captor
name|ArgumentCaptor
argument_list|<
name|Optional
argument_list|<
name|Pattern
argument_list|>
argument_list|>
name|captor
decl_stmt|;
DECL|field|listener
annotation|@
name|Mock
specifier|private
name|SearchQueryHighlightListener
name|listener
decl_stmt|;
DECL|field|observable
specifier|private
name|SearchQueryHighlightObservable
name|observable
decl_stmt|;
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
throws|throws
name|Exception
block|{
name|observable
operator|=
operator|new
name|SearchQueryHighlightObservable
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|addSearchListenerNotifiesListenerAboutPreviousPattern ()
specifier|public
name|void
name|addSearchListenerNotifiesListenerAboutPreviousPattern
parameter_list|()
throws|throws
name|Exception
block|{
name|observable
operator|.
name|fireSearchlistenerEvent
argument_list|(
operator|new
name|SearchQuery
argument_list|(
literal|"test"
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|observable
operator|.
name|addSearchListener
argument_list|(
name|listener
argument_list|)
expr_stmt|;
name|verify
argument_list|(
name|listener
argument_list|)
operator|.
name|highlightPattern
argument_list|(
name|captor
operator|.
name|capture
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"(\\Qtest\\E)"
argument_list|,
name|captor
operator|.
name|getValue
argument_list|()
operator|.
name|get
argument_list|()
operator|.
name|pattern
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|addSearchListenerNotifiesRegisteredListener ()
specifier|public
name|void
name|addSearchListenerNotifiesRegisteredListener
parameter_list|()
throws|throws
name|Exception
block|{
name|observable
operator|.
name|addSearchListener
argument_list|(
name|listener
argument_list|)
expr_stmt|;
name|observable
operator|.
name|fireSearchlistenerEvent
argument_list|(
operator|new
name|SearchQuery
argument_list|(
literal|"test"
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|verify
argument_list|(
name|listener
argument_list|,
name|atLeastOnce
argument_list|()
argument_list|)
operator|.
name|highlightPattern
argument_list|(
name|captor
operator|.
name|capture
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"(\\Qtest\\E)"
argument_list|,
name|captor
operator|.
name|getValue
argument_list|()
operator|.
name|get
argument_list|()
operator|.
name|pattern
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|addSearchListenerNotifiesRegisteredListenerAboutGrammarBasedSearches ()
specifier|public
name|void
name|addSearchListenerNotifiesRegisteredListenerAboutGrammarBasedSearches
parameter_list|()
throws|throws
name|Exception
block|{
name|observable
operator|.
name|addSearchListener
argument_list|(
name|listener
argument_list|)
expr_stmt|;
name|observable
operator|.
name|fireSearchlistenerEvent
argument_list|(
operator|new
name|SearchQuery
argument_list|(
literal|"author=harrer"
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|verify
argument_list|(
name|listener
argument_list|,
name|atLeastOnce
argument_list|()
argument_list|)
operator|.
name|highlightPattern
argument_list|(
name|captor
operator|.
name|capture
argument_list|()
argument_list|)
expr_stmt|;
comment|// TODO: We would expect "harrer" here
name|assertEquals
argument_list|(
literal|"(\\Qauthor=harrer\\E)"
argument_list|,
name|captor
operator|.
name|getValue
argument_list|()
operator|.
name|get
argument_list|()
operator|.
name|pattern
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

