begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.migrations
package|package
name|org
operator|.
name|jabref
operator|.
name|migrations
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
name|Optional
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|groups
operator|.
name|DefaultGroupsFactory
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|ParserResult
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
name|FieldName
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
name|groups
operator|.
name|ExplicitGroup
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
name|groups
operator|.
name|GroupHierarchyType
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
name|groups
operator|.
name|GroupTreeNode
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
DECL|class|ConvertMarkingToGroupsTest
class|class
name|ConvertMarkingToGroupsTest
block|{
annotation|@
name|Test
DECL|method|performMigrationForSingleEntry ()
name|void
name|performMigrationForSingleEntry
parameter_list|()
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
operator|.
name|withField
argument_list|(
name|FieldName
operator|.
name|MARKED_INTERNAL
argument_list|,
literal|"[Nicolas:6]"
argument_list|)
decl_stmt|;
name|ParserResult
name|parserResult
init|=
operator|new
name|ParserResult
argument_list|(
name|Collections
operator|.
name|singleton
argument_list|(
name|entry
argument_list|)
argument_list|)
decl_stmt|;
operator|new
name|ConvertMarkingToGroups
argument_list|()
operator|.
name|performMigration
argument_list|(
name|parserResult
argument_list|)
expr_stmt|;
name|GroupTreeNode
name|rootExpected
init|=
name|GroupTreeNode
operator|.
name|fromGroup
argument_list|(
name|DefaultGroupsFactory
operator|.
name|getAllEntriesGroup
argument_list|()
argument_list|)
decl_stmt|;
name|GroupTreeNode
name|markings
init|=
name|rootExpected
operator|.
name|addSubgroup
argument_list|(
operator|new
name|ExplicitGroup
argument_list|(
literal|"Markings"
argument_list|,
name|GroupHierarchyType
operator|.
name|INCLUDING
argument_list|,
literal|','
argument_list|)
argument_list|)
decl_stmt|;
name|markings
operator|.
name|addSubgroup
argument_list|(
operator|new
name|ExplicitGroup
argument_list|(
literal|"Nicolas:6"
argument_list|,
name|GroupHierarchyType
operator|.
name|INCLUDING
argument_list|,
literal|','
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|MARKED_INTERNAL
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|rootExpected
argument_list|)
argument_list|,
name|parserResult
operator|.
name|getMetaData
argument_list|()
operator|.
name|getGroups
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
