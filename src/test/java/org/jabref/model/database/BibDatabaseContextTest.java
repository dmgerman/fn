begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.database
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
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
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|metadata
operator|.
name|FilePreferences
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
name|mockito
operator|.
name|Mockito
operator|.
name|mock
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
name|when
import|;
end_import

begin_class
DECL|class|BibDatabaseContextTest
specifier|public
class|class
name|BibDatabaseContextTest
block|{
DECL|field|currentWorkingDir
specifier|private
name|Path
name|currentWorkingDir
decl_stmt|;
comment|// Store the minimal preferences for the
comment|// BibDatabaseContext.getFileDirectories(File,
comment|// FilePreferences) incocation:
DECL|field|fileDirPrefs
specifier|private
name|FilePreferences
name|fileDirPrefs
decl_stmt|;
annotation|@
name|BeforeEach
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|fileDirPrefs
operator|=
name|mock
argument_list|(
name|FilePreferences
operator|.
name|class
argument_list|)
expr_stmt|;
name|currentWorkingDir
operator|=
name|Paths
operator|.
name|get
argument_list|(
name|System
operator|.
name|getProperty
argument_list|(
literal|"user.dir"
argument_list|)
argument_list|)
expr_stmt|;
name|when
argument_list|(
name|fileDirPrefs
operator|.
name|isBibLocationAsPrimary
argument_list|()
argument_list|)
operator|.
name|thenReturn
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getFileDirectoriesWithEmptyDbParent ()
specifier|public
name|void
name|getFileDirectoriesWithEmptyDbParent
parameter_list|()
block|{
name|BibDatabaseContext
name|dbContext
init|=
operator|new
name|BibDatabaseContext
argument_list|()
decl_stmt|;
name|dbContext
operator|.
name|setDatabaseFile
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
literal|"biblio.bib"
argument_list|)
operator|.
name|toFile
argument_list|()
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|fileDirectories
init|=
name|dbContext
operator|.
name|getFileDirectories
argument_list|(
literal|"file"
argument_list|,
name|fileDirPrefs
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
name|currentWorkingDir
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|,
name|fileDirectories
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getFileDirectoriesWithRelativeDbParent ()
specifier|public
name|void
name|getFileDirectoriesWithRelativeDbParent
parameter_list|()
block|{
name|Path
name|file
init|=
name|Paths
operator|.
name|get
argument_list|(
literal|"relative/subdir"
argument_list|)
operator|.
name|resolve
argument_list|(
literal|"biblio.bib"
argument_list|)
decl_stmt|;
name|BibDatabaseContext
name|dbContext
init|=
operator|new
name|BibDatabaseContext
argument_list|()
decl_stmt|;
name|dbContext
operator|.
name|setDatabaseFile
argument_list|(
name|file
operator|.
name|toFile
argument_list|()
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|fileDirectories
init|=
name|dbContext
operator|.
name|getFileDirectories
argument_list|(
literal|"file"
argument_list|,
name|fileDirPrefs
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
name|currentWorkingDir
operator|.
name|resolve
argument_list|(
name|file
operator|.
name|getParent
argument_list|()
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|,
name|fileDirectories
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getFileDirectoriesWithRelativeDottedDbParent ()
specifier|public
name|void
name|getFileDirectoriesWithRelativeDottedDbParent
parameter_list|()
block|{
name|Path
name|file
init|=
name|Paths
operator|.
name|get
argument_list|(
literal|"./relative/subdir"
argument_list|)
operator|.
name|resolve
argument_list|(
literal|"biblio.bib"
argument_list|)
decl_stmt|;
name|BibDatabaseContext
name|dbContext
init|=
operator|new
name|BibDatabaseContext
argument_list|()
decl_stmt|;
name|dbContext
operator|.
name|setDatabaseFile
argument_list|(
name|file
operator|.
name|toFile
argument_list|()
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|fileDirectories
init|=
name|dbContext
operator|.
name|getFileDirectories
argument_list|(
literal|"file"
argument_list|,
name|fileDirPrefs
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
name|currentWorkingDir
operator|.
name|resolve
argument_list|(
name|file
operator|.
name|getParent
argument_list|()
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|,
name|fileDirectories
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getFileDirectoriesWithAbsoluteDbParent ()
specifier|public
name|void
name|getFileDirectoriesWithAbsoluteDbParent
parameter_list|()
block|{
name|Path
name|file
init|=
name|Paths
operator|.
name|get
argument_list|(
literal|"/absolute/subdir"
argument_list|)
operator|.
name|resolve
argument_list|(
literal|"biblio.bib"
argument_list|)
decl_stmt|;
name|BibDatabaseContext
name|dbContext
init|=
operator|new
name|BibDatabaseContext
argument_list|()
decl_stmt|;
name|dbContext
operator|.
name|setDatabaseFile
argument_list|(
name|file
operator|.
name|toFile
argument_list|()
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|fileDirectories
init|=
name|dbContext
operator|.
name|getFileDirectories
argument_list|(
literal|"file"
argument_list|,
name|fileDirPrefs
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
name|currentWorkingDir
operator|.
name|resolve
argument_list|(
name|file
operator|.
name|getParent
argument_list|()
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|,
name|fileDirectories
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

