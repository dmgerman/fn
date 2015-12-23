begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.search
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|search
package|;
end_package

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|Globals
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
name|JabRefPreferences
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
name|logic
operator|.
name|search
operator|.
name|SearchQueryHighlightObservable
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|BeforeClass
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
name|Optional
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|function
operator|.
name|Predicate
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
name|junit
operator|.
name|Assert
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
name|Assert
operator|.
name|assertTrue
import|;
end_import

begin_class
DECL|class|MatchesHighlighterTest
specifier|public
class|class
name|MatchesHighlighterTest
block|{
annotation|@
name|BeforeClass
DECL|method|setup ()
specifier|public
specifier|static
name|void
name|setup
parameter_list|()
block|{
name|Globals
operator|.
name|prefs
operator|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testHighlightWords ()
specifier|public
name|void
name|testHighlightWords
parameter_list|()
throws|throws
name|Exception
block|{
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|MatchesHighlighter
operator|.
name|highlightWordsWithHTML
argument_list|(
literal|""
argument_list|,
name|Optional
operator|.
name|empty
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Einstein"
argument_list|,
name|MatchesHighlighter
operator|.
name|highlightWordsWithHTML
argument_list|(
literal|"Einstein"
argument_list|,
name|Optional
operator|.
name|empty
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"<span style=\"background-color:#3399FF;\">Einstein</span>"
argument_list|,
name|MatchesHighlighter
operator|.
name|highlightWordsWithHTML
argument_list|(
literal|"Einstein"
argument_list|,
name|Optional
operator|.
name|of
argument_list|(
name|Pattern
operator|.
name|compile
argument_list|(
literal|"Einstein"
argument_list|)
argument_list|)
argument_list|)
argument_list|)
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
DECL|method|testNullText ()
specifier|public
name|void
name|testNullText
parameter_list|()
block|{
name|MatchesHighlighter
operator|.
name|highlightWordsWithHTML
argument_list|(
literal|null
argument_list|,
name|Optional
operator|.
name|empty
argument_list|()
argument_list|)
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
DECL|method|testNullList ()
specifier|public
name|void
name|testNullList
parameter_list|()
block|{
name|MatchesHighlighter
operator|.
name|highlightWordsWithHTML
argument_list|(
literal|""
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testNoWords ()
specifier|public
name|void
name|testNoWords
parameter_list|()
throws|throws
name|Exception
block|{
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|SearchQueryHighlightObservable
operator|.
name|getPatternForWords
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPatternCaseInsensitive ()
specifier|public
name|void
name|testPatternCaseInsensitive
parameter_list|()
throws|throws
name|Exception
block|{
name|Predicate
argument_list|<
name|String
argument_list|>
name|predicate
init|=
name|SearchQueryHighlightObservable
operator|.
name|getPatternForWords
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
literal|"abc"
argument_list|)
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|)
operator|.
name|get
argument_list|()
operator|.
name|asPredicate
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|predicate
operator|.
name|test
argument_list|(
literal|"abc"
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|predicate
operator|.
name|test
argument_list|(
literal|"ABC"
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|predicate
operator|.
name|test
argument_list|(
literal|"abd"
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|predicate
operator|.
name|test
argument_list|(
literal|"ab"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPatternCaseSensitive ()
specifier|public
name|void
name|testPatternCaseSensitive
parameter_list|()
throws|throws
name|Exception
block|{
name|Predicate
argument_list|<
name|String
argument_list|>
name|predicate
init|=
name|SearchQueryHighlightObservable
operator|.
name|getPatternForWords
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
literal|"abc"
argument_list|)
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
operator|.
name|get
argument_list|()
operator|.
name|asPredicate
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|predicate
operator|.
name|test
argument_list|(
literal|"abc"
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|predicate
operator|.
name|test
argument_list|(
literal|"ABC"
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|predicate
operator|.
name|test
argument_list|(
literal|"abd"
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|predicate
operator|.
name|test
argument_list|(
literal|"ab"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

