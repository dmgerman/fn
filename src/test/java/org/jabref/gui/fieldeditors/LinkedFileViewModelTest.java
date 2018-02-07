begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.fieldeditors
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|fieldeditors
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
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
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|Alert
operator|.
name|AlertType
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|ButtonType
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|DialogService
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|util
operator|.
name|TaskExecutor
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
name|BibDatabaseContext
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
name|LinkedFile
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
name|FileDirectoryPreferences
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
name|Rule
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
name|org
operator|.
name|junit
operator|.
name|rules
operator|.
name|TemporaryFolder
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

begin_import
import|import static
name|org
operator|.
name|mockito
operator|.
name|ArgumentMatchers
operator|.
name|any
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|mockito
operator|.
name|ArgumentMatchers
operator|.
name|anyString
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
name|doReturn
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
name|spy
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
name|verify
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
name|verifyZeroInteractions
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
DECL|class|LinkedFileViewModelTest
specifier|public
class|class
name|LinkedFileViewModelTest
block|{
DECL|field|tempFolder
annotation|@
name|Rule
specifier|public
name|TemporaryFolder
name|tempFolder
init|=
operator|new
name|TemporaryFolder
argument_list|()
decl_stmt|;
DECL|field|linkedFile
specifier|private
name|LinkedFile
name|linkedFile
decl_stmt|;
DECL|field|entry
specifier|private
name|BibEntry
name|entry
decl_stmt|;
DECL|field|databaseContext
specifier|private
name|BibDatabaseContext
name|databaseContext
decl_stmt|;
DECL|field|taskExecutor
specifier|private
name|TaskExecutor
name|taskExecutor
decl_stmt|;
DECL|field|dialogService
specifier|private
name|DialogService
name|dialogService
decl_stmt|;
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|entry
operator|=
operator|new
name|BibEntry
argument_list|()
expr_stmt|;
name|databaseContext
operator|=
operator|new
name|BibDatabaseContext
argument_list|()
expr_stmt|;
name|taskExecutor
operator|=
name|mock
argument_list|(
name|TaskExecutor
operator|.
name|class
argument_list|)
expr_stmt|;
name|dialogService
operator|=
name|mock
argument_list|(
name|DialogService
operator|.
name|class
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|deleteWhenFilePathNotPresentReturnsTrue ()
specifier|public
name|void
name|deleteWhenFilePathNotPresentReturnsTrue
parameter_list|()
block|{
comment|// Making this a spy, so we can inject an empty optional without digging into the implementation
name|linkedFile
operator|=
name|spy
argument_list|(
operator|new
name|LinkedFile
argument_list|(
literal|""
argument_list|,
literal|"nonexistent file"
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|doReturn
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|)
operator|.
name|when
argument_list|(
name|linkedFile
argument_list|)
operator|.
name|findIn
argument_list|(
name|any
argument_list|(
name|BibDatabaseContext
operator|.
name|class
argument_list|)
argument_list|,
name|any
argument_list|(
name|FileDirectoryPreferences
operator|.
name|class
argument_list|)
argument_list|)
expr_stmt|;
name|LinkedFileViewModel
name|viewModel
init|=
operator|new
name|LinkedFileViewModel
argument_list|(
name|linkedFile
argument_list|,
name|entry
argument_list|,
name|databaseContext
argument_list|,
name|taskExecutor
argument_list|,
name|dialogService
argument_list|)
decl_stmt|;
name|boolean
name|removed
init|=
name|viewModel
operator|.
name|delete
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|removed
argument_list|)
expr_stmt|;
name|verifyZeroInteractions
argument_list|(
name|dialogService
argument_list|)
expr_stmt|;
comment|// dialog was never shown
block|}
annotation|@
name|Test
DECL|method|deleteWhenRemoveChosenReturnsTrue ()
specifier|public
name|void
name|deleteWhenRemoveChosenReturnsTrue
parameter_list|()
throws|throws
name|IOException
block|{
name|File
name|tempFile
init|=
name|tempFolder
operator|.
name|newFile
argument_list|()
decl_stmt|;
name|linkedFile
operator|=
operator|new
name|LinkedFile
argument_list|(
literal|""
argument_list|,
name|tempFile
operator|.
name|getAbsolutePath
argument_list|()
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|when
argument_list|(
name|dialogService
operator|.
name|showCustomButtonDialogAndWait
argument_list|(
name|any
argument_list|(
name|AlertType
operator|.
name|class
argument_list|)
argument_list|,
name|anyString
argument_list|()
argument_list|,
name|anyString
argument_list|()
argument_list|,
name|any
argument_list|(
name|ButtonType
operator|.
name|class
argument_list|)
argument_list|,
name|any
argument_list|(
name|ButtonType
operator|.
name|class
argument_list|)
argument_list|,
name|any
argument_list|(
name|ButtonType
operator|.
name|class
argument_list|)
argument_list|)
argument_list|)
operator|.
name|thenAnswer
argument_list|(
name|invocation
lambda|->
name|Optional
operator|.
name|of
argument_list|(
name|invocation
operator|.
name|getArgument
argument_list|(
literal|3
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|// first vararg - remove button
name|LinkedFileViewModel
name|viewModel
init|=
operator|new
name|LinkedFileViewModel
argument_list|(
name|linkedFile
argument_list|,
name|entry
argument_list|,
name|databaseContext
argument_list|,
name|taskExecutor
argument_list|,
name|dialogService
argument_list|)
decl_stmt|;
name|boolean
name|removed
init|=
name|viewModel
operator|.
name|delete
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|removed
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|tempFile
operator|.
name|exists
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|deleteWhenDeleteChosenReturnsTrueAndDeletesFile ()
specifier|public
name|void
name|deleteWhenDeleteChosenReturnsTrueAndDeletesFile
parameter_list|()
throws|throws
name|IOException
block|{
name|File
name|tempFile
init|=
name|tempFolder
operator|.
name|newFile
argument_list|()
decl_stmt|;
name|linkedFile
operator|=
operator|new
name|LinkedFile
argument_list|(
literal|""
argument_list|,
name|tempFile
operator|.
name|getAbsolutePath
argument_list|()
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|when
argument_list|(
name|dialogService
operator|.
name|showCustomButtonDialogAndWait
argument_list|(
name|any
argument_list|(
name|AlertType
operator|.
name|class
argument_list|)
argument_list|,
name|anyString
argument_list|()
argument_list|,
name|anyString
argument_list|()
argument_list|,
name|any
argument_list|(
name|ButtonType
operator|.
name|class
argument_list|)
argument_list|,
name|any
argument_list|(
name|ButtonType
operator|.
name|class
argument_list|)
argument_list|,
name|any
argument_list|(
name|ButtonType
operator|.
name|class
argument_list|)
argument_list|)
argument_list|)
operator|.
name|thenAnswer
argument_list|(
name|invocation
lambda|->
name|Optional
operator|.
name|of
argument_list|(
name|invocation
operator|.
name|getArgument
argument_list|(
literal|4
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|// second vararg - delete button
name|LinkedFileViewModel
name|viewModel
init|=
operator|new
name|LinkedFileViewModel
argument_list|(
name|linkedFile
argument_list|,
name|entry
argument_list|,
name|databaseContext
argument_list|,
name|taskExecutor
argument_list|,
name|dialogService
argument_list|)
decl_stmt|;
name|boolean
name|removed
init|=
name|viewModel
operator|.
name|delete
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|removed
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|tempFile
operator|.
name|exists
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|deleteWhenDeleteChosenAndFileMissingReturnsFalse ()
specifier|public
name|void
name|deleteWhenDeleteChosenAndFileMissingReturnsFalse
parameter_list|()
throws|throws
name|IOException
block|{
name|linkedFile
operator|=
operator|new
name|LinkedFile
argument_list|(
literal|""
argument_list|,
literal|"!!nonexistent file!!"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|when
argument_list|(
name|dialogService
operator|.
name|showCustomButtonDialogAndWait
argument_list|(
name|any
argument_list|(
name|AlertType
operator|.
name|class
argument_list|)
argument_list|,
name|anyString
argument_list|()
argument_list|,
name|anyString
argument_list|()
argument_list|,
name|any
argument_list|(
name|ButtonType
operator|.
name|class
argument_list|)
argument_list|,
name|any
argument_list|(
name|ButtonType
operator|.
name|class
argument_list|)
argument_list|,
name|any
argument_list|(
name|ButtonType
operator|.
name|class
argument_list|)
argument_list|)
argument_list|)
operator|.
name|thenAnswer
argument_list|(
name|invocation
lambda|->
name|Optional
operator|.
name|of
argument_list|(
name|invocation
operator|.
name|getArgument
argument_list|(
literal|4
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|// second vararg - delete button
name|LinkedFileViewModel
name|viewModel
init|=
operator|new
name|LinkedFileViewModel
argument_list|(
name|linkedFile
argument_list|,
name|entry
argument_list|,
name|databaseContext
argument_list|,
name|taskExecutor
argument_list|,
name|dialogService
argument_list|)
decl_stmt|;
name|boolean
name|removed
init|=
name|viewModel
operator|.
name|delete
argument_list|()
decl_stmt|;
name|verify
argument_list|(
name|dialogService
argument_list|)
operator|.
name|showErrorDialogAndWait
argument_list|(
name|anyString
argument_list|()
argument_list|,
name|anyString
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|removed
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|deleteWhenDialogCancelledReturnsFalse ()
specifier|public
name|void
name|deleteWhenDialogCancelledReturnsFalse
parameter_list|()
throws|throws
name|IOException
block|{
name|File
name|tempFile
init|=
name|tempFolder
operator|.
name|newFile
argument_list|()
decl_stmt|;
name|linkedFile
operator|=
operator|new
name|LinkedFile
argument_list|(
literal|"desc"
argument_list|,
name|tempFile
operator|.
name|getAbsolutePath
argument_list|()
argument_list|,
literal|"pdf"
argument_list|)
expr_stmt|;
name|when
argument_list|(
name|dialogService
operator|.
name|showCustomButtonDialogAndWait
argument_list|(
name|any
argument_list|(
name|AlertType
operator|.
name|class
argument_list|)
argument_list|,
name|anyString
argument_list|()
argument_list|,
name|anyString
argument_list|()
argument_list|,
name|any
argument_list|(
name|ButtonType
operator|.
name|class
argument_list|)
argument_list|,
name|any
argument_list|(
name|ButtonType
operator|.
name|class
argument_list|)
argument_list|,
name|any
argument_list|(
name|ButtonType
operator|.
name|class
argument_list|)
argument_list|)
argument_list|)
operator|.
name|thenAnswer
argument_list|(
name|invocation
lambda|->
name|Optional
operator|.
name|of
argument_list|(
name|invocation
operator|.
name|getArgument
argument_list|(
literal|5
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|// third vararg - cancel button
name|LinkedFileViewModel
name|viewModel
init|=
operator|new
name|LinkedFileViewModel
argument_list|(
name|linkedFile
argument_list|,
name|entry
argument_list|,
name|databaseContext
argument_list|,
name|taskExecutor
argument_list|,
name|dialogService
argument_list|)
decl_stmt|;
name|boolean
name|removed
init|=
name|viewModel
operator|.
name|delete
argument_list|()
decl_stmt|;
name|assertFalse
argument_list|(
name|removed
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|tempFile
operator|.
name|exists
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

