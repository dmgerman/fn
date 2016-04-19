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
name|entry
operator|.
name|BibtexEntryTypes
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
name|IdGenerator
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
DECL|class|ExplicitGroupTest
specifier|public
class|class
name|ExplicitGroupTest
block|{
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
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
DECL|method|testToStringSimple ()
specifier|public
name|void
name|testToStringSimple
parameter_list|()
block|{
name|ExplicitGroup
name|group
init|=
operator|new
name|ExplicitGroup
argument_list|(
literal|"myExplicitGroup"
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"ExplicitGroup:myExplicitGroup;0;"
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
DECL|method|testToStringComplex ()
specifier|public
name|void
name|testToStringComplex
parameter_list|()
block|{
name|ExplicitGroup
name|group
init|=
operator|new
name|ExplicitGroup
argument_list|(
literal|"myExplicitGroup"
argument_list|,
name|GroupHierarchyType
operator|.
name|INCLUDING
argument_list|)
decl_stmt|;
name|group
operator|.
name|addEntry
argument_list|(
name|makeBibtexEntry
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"ExplicitGroup:myExplicitGroup;2;shields01;"
argument_list|,
name|group
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|makeBibtexEntry ()
specifier|public
name|BibEntry
name|makeBibtexEntry
parameter_list|()
block|{
name|BibEntry
name|e
init|=
operator|new
name|BibEntry
argument_list|(
name|IdGenerator
operator|.
name|next
argument_list|()
argument_list|,
name|BibtexEntryTypes
operator|.
name|INCOLLECTION
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|e
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"Marine finfish larviculture in Europe"
argument_list|)
expr_stmt|;
name|e
operator|.
name|setField
argument_list|(
literal|"bibtexkey"
argument_list|,
literal|"shields01"
argument_list|)
expr_stmt|;
name|e
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2001"
argument_list|)
expr_stmt|;
name|e
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"Kevin Shields"
argument_list|)
expr_stmt|;
return|return
name|e
return|;
block|}
block|}
end_class

end_unit

