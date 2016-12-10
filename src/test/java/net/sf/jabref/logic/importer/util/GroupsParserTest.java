begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.importer.util
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|util
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
name|groups
operator|.
name|AbstractGroup
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
name|groups
operator|.
name|ExplicitGroup
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
name|groups
operator|.
name|GroupHierarchyType
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
DECL|class|GroupsParserTest
specifier|public
class|class
name|GroupsParserTest
block|{
annotation|@
name|Test
comment|// For https://github.com/JabRef/jabref/issues/1681
DECL|method|fromStringParsesExplicitGroupWithEscapedCharacterInName ()
specifier|public
name|void
name|fromStringParsesExplicitGroupWithEscapedCharacterInName
parameter_list|()
throws|throws
name|Exception
block|{
name|ExplicitGroup
name|expected
init|=
operator|new
name|ExplicitGroup
argument_list|(
literal|"B{\\\"{o}}hmer"
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
literal|','
argument_list|)
decl_stmt|;
name|AbstractGroup
name|parsed
init|=
name|GroupsParser
operator|.
name|fromString
argument_list|(
literal|"ExplicitGroup:B{\\\\\"{o}}hmer;0;"
argument_list|,
literal|','
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|parsed
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

