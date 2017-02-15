begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.model.search.matchers
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|search
operator|.
name|matchers
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
name|model
operator|.
name|search
operator|.
name|rules
operator|.
name|MockSearchMatcher
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
DECL|class|MatcherSetsTest
specifier|public
class|class
name|MatcherSetsTest
block|{
annotation|@
name|Test
DECL|method|testBuildAnd ()
specifier|public
name|void
name|testBuildAnd
parameter_list|()
block|{
name|MatcherSet
name|matcherSet
init|=
name|MatcherSets
operator|.
name|build
argument_list|(
name|MatcherSets
operator|.
name|MatcherType
operator|.
name|AND
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|matcherSet
operator|.
name|isMatch
argument_list|(
operator|new
name|BibEntry
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|matcherSet
operator|.
name|addRule
argument_list|(
operator|new
name|MockSearchMatcher
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|matcherSet
operator|.
name|isMatch
argument_list|(
operator|new
name|BibEntry
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|matcherSet
operator|.
name|addRule
argument_list|(
operator|new
name|MockSearchMatcher
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|matcherSet
operator|.
name|isMatch
argument_list|(
operator|new
name|BibEntry
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testBuildOr ()
specifier|public
name|void
name|testBuildOr
parameter_list|()
block|{
name|MatcherSet
name|matcherSet
init|=
name|MatcherSets
operator|.
name|build
argument_list|(
name|MatcherSets
operator|.
name|MatcherType
operator|.
name|OR
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
name|matcherSet
operator|.
name|isMatch
argument_list|(
operator|new
name|BibEntry
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|matcherSet
operator|.
name|addRule
argument_list|(
operator|new
name|MockSearchMatcher
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|matcherSet
operator|.
name|isMatch
argument_list|(
operator|new
name|BibEntry
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|matcherSet
operator|.
name|addRule
argument_list|(
operator|new
name|MockSearchMatcher
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|matcherSet
operator|.
name|isMatch
argument_list|(
operator|new
name|BibEntry
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testBuildNotWithTrue ()
specifier|public
name|void
name|testBuildNotWithTrue
parameter_list|()
block|{
name|NotMatcher
name|matcher
init|=
operator|new
name|NotMatcher
argument_list|(
operator|new
name|MockSearchMatcher
argument_list|(
literal|true
argument_list|)
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
name|matcher
operator|.
name|isMatch
argument_list|(
operator|new
name|BibEntry
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testBuildNotWithFalse ()
specifier|public
name|void
name|testBuildNotWithFalse
parameter_list|()
block|{
name|NotMatcher
name|matcher
init|=
operator|new
name|NotMatcher
argument_list|(
operator|new
name|MockSearchMatcher
argument_list|(
literal|false
argument_list|)
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|matcher
operator|.
name|isMatch
argument_list|(
operator|new
name|BibEntry
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
