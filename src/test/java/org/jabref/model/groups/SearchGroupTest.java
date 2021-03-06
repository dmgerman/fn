begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.groups
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|groups
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
name|assertTrue
import|;
end_import

begin_class
DECL|class|SearchGroupTest
specifier|public
class|class
name|SearchGroupTest
block|{
annotation|@
name|Test
DECL|method|containsFindsWordWithRegularExpression ()
specifier|public
name|void
name|containsFindsWordWithRegularExpression
parameter_list|()
block|{
name|SearchGroup
name|group
init|=
operator|new
name|SearchGroup
argument_list|(
literal|"myExplicitGroup"
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
literal|"anyfield=rev*"
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|addKeyword
argument_list|(
literal|"review"
argument_list|,
literal|','
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|group
operator|.
name|contains
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

