begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.autosaveandbackup
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|autosaveandbackup
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
DECL|class|BackupManagerTest
specifier|public
class|class
name|BackupManagerTest
block|{
annotation|@
name|Test
DECL|method|backupFileNameIsCorrectlyGeneratedWithinTmpDirectory ()
specifier|public
name|void
name|backupFileNameIsCorrectlyGeneratedWithinTmpDirectory
parameter_list|()
block|{
name|Path
name|bibPath
init|=
name|Paths
operator|.
name|get
argument_list|(
literal|"tmp"
argument_list|,
literal|"test.bib"
argument_list|)
decl_stmt|;
name|Path
name|savPath
init|=
name|BackupManager
operator|.
name|getBackupPath
argument_list|(
name|bibPath
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
literal|"tmp"
argument_list|,
literal|"test.bib.sav"
argument_list|)
argument_list|,
name|savPath
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

