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
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Path
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Paths
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
name|auxparser
operator|.
name|DefaultAuxParser
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
name|database
operator|.
name|BibDatabase
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
name|metadata
operator|.
name|MetaData
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
name|util
operator|.
name|DummyFileUpdateMonitor
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
name|BeforeEach
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
name|assertFalse
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
DECL|class|TexGroupTest
specifier|public
class|class
name|TexGroupTest
block|{
DECL|field|metaData
specifier|private
name|MetaData
name|metaData
decl_stmt|;
annotation|@
name|BeforeEach
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
throws|throws
name|Exception
block|{
name|metaData
operator|=
operator|new
name|MetaData
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|containsReturnsTrueForEntryInAux ()
specifier|public
name|void
name|containsReturnsTrueForEntryInAux
parameter_list|()
throws|throws
name|Exception
block|{
name|Path
name|auxFile
init|=
name|Paths
operator|.
name|get
argument_list|(
name|TexGroupTest
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"paper.aux"
argument_list|)
operator|.
name|toURI
argument_list|()
argument_list|)
decl_stmt|;
name|TexGroup
name|group
init|=
operator|new
name|TexGroup
argument_list|(
literal|"paper"
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
name|auxFile
argument_list|,
operator|new
name|DefaultAuxParser
argument_list|(
operator|new
name|BibDatabase
argument_list|()
argument_list|)
argument_list|,
operator|new
name|DummyFileUpdateMonitor
argument_list|()
argument_list|,
name|metaData
argument_list|)
decl_stmt|;
name|BibEntry
name|inAux
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|inAux
operator|.
name|setCiteKey
argument_list|(
literal|"Darwin1888"
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|group
operator|.
name|contains
argument_list|(
name|inAux
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|containsReturnsTrueForEntryNotInAux ()
specifier|public
name|void
name|containsReturnsTrueForEntryNotInAux
parameter_list|()
throws|throws
name|Exception
block|{
name|Path
name|auxFile
init|=
name|Paths
operator|.
name|get
argument_list|(
name|TexGroupTest
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"paper.aux"
argument_list|)
operator|.
name|toURI
argument_list|()
argument_list|)
decl_stmt|;
name|TexGroup
name|group
init|=
operator|new
name|TexGroup
argument_list|(
literal|"paper"
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
name|auxFile
argument_list|,
operator|new
name|DefaultAuxParser
argument_list|(
operator|new
name|BibDatabase
argument_list|()
argument_list|)
argument_list|,
operator|new
name|DummyFileUpdateMonitor
argument_list|()
argument_list|,
name|metaData
argument_list|)
decl_stmt|;
name|BibEntry
name|notInAux
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|notInAux
operator|.
name|setCiteKey
argument_list|(
literal|"NotInAux2017"
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|group
operator|.
name|contains
argument_list|(
name|notInAux
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getFilePathReturnsRelativePath ()
specifier|public
name|void
name|getFilePathReturnsRelativePath
parameter_list|()
throws|throws
name|Exception
block|{
name|Path
name|auxFile
init|=
name|Paths
operator|.
name|get
argument_list|(
name|TexGroupTest
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"paper.aux"
argument_list|)
operator|.
name|toURI
argument_list|()
argument_list|)
decl_stmt|;
name|String
name|user
init|=
literal|"Darwin"
decl_stmt|;
name|metaData
operator|.
name|setLaTexFileDirectory
argument_list|(
name|user
argument_list|,
name|auxFile
operator|.
name|getParent
argument_list|()
argument_list|)
expr_stmt|;
name|TexGroup
name|group
init|=
operator|new
name|TexGroup
argument_list|(
literal|"paper"
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
name|auxFile
argument_list|,
operator|new
name|DefaultAuxParser
argument_list|(
operator|new
name|BibDatabase
argument_list|()
argument_list|)
argument_list|,
operator|new
name|DummyFileUpdateMonitor
argument_list|()
argument_list|,
name|metaData
argument_list|,
name|user
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"paper.aux"
argument_list|,
name|group
operator|.
name|getFilePath
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

