begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.groups
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|groups
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
name|importer
operator|.
name|fileformat
operator|.
name|ParseException
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
DECL|class|KeywordGroupTest
specifier|public
class|class
name|KeywordGroupTest
block|{
annotation|@
name|Test
DECL|method|testToString ()
specifier|public
name|void
name|testToString
parameter_list|()
throws|throws
name|ParseException
block|{
name|KeywordGroup
name|group
init|=
operator|new
name|KeywordGroup
argument_list|(
literal|"myExplicitGroup"
argument_list|,
literal|"author"
argument_list|,
literal|"asdf"
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"KeywordGroup:myExplicitGroup;0;author;asdf;1;1;"
argument_list|,
name|group
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testToString2 ()
specifier|public
name|void
name|testToString2
parameter_list|()
throws|throws
name|ParseException
block|{
name|KeywordGroup
name|group
init|=
operator|new
name|KeywordGroup
argument_list|(
literal|"myExplicitGroup"
argument_list|,
literal|"author"
argument_list|,
literal|"asdf"
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|,
name|GroupHierarchyType
operator|.
name|REFINING
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"KeywordGroup:myExplicitGroup;1;author;asdf;0;1;"
argument_list|,
name|group
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

