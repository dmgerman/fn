begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.cleanup
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|cleanup
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
name|Arrays
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|BibDatabaseContext
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
name|Defaults
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
name|MetaData
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
name|logic
operator|.
name|journals
operator|.
name|JournalAbbreviationLoader
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
name|logic
operator|.
name|layout
operator|.
name|LayoutFormatterPreferences
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
name|database
operator|.
name|BibDatabase
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
name|FileField
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
name|ParsedFileField
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
name|preferences
operator|.
name|JabRefPreferences
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

begin_class
DECL|class|RenamePdfCleanupTest
specifier|public
class|class
name|RenamePdfCleanupTest
block|{
annotation|@
name|Rule
DECL|field|testFolder
specifier|public
name|TemporaryFolder
name|testFolder
init|=
operator|new
name|TemporaryFolder
argument_list|()
decl_stmt|;
DECL|field|context
specifier|private
name|BibDatabaseContext
name|context
decl_stmt|;
DECL|field|entry
specifier|private
name|BibEntry
name|entry
decl_stmt|;
DECL|field|prefs
specifier|private
name|JabRefPreferences
name|prefs
decl_stmt|;
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
throws|throws
name|Exception
block|{
name|prefs
operator|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
expr_stmt|;
name|MetaData
name|metaData
init|=
operator|new
name|MetaData
argument_list|()
decl_stmt|;
name|context
operator|=
operator|new
name|BibDatabaseContext
argument_list|(
operator|new
name|BibDatabase
argument_list|()
argument_list|,
name|metaData
argument_list|,
operator|new
name|Defaults
argument_list|()
argument_list|)
expr_stmt|;
name|context
operator|.
name|setDatabaseFile
argument_list|(
name|testFolder
operator|.
name|newFile
argument_list|(
literal|"test.bib"
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|=
operator|new
name|BibEntry
argument_list|()
expr_stmt|;
name|entry
operator|.
name|setCiteKey
argument_list|(
literal|"Toot"
argument_list|)
expr_stmt|;
block|}
comment|/**      * Test for #466      */
annotation|@
name|Test
DECL|method|cleanupRenamePdfRenamesFileEvenIfOnlyDifferenceIsCase ()
specifier|public
name|void
name|cleanupRenamePdfRenamesFileEvenIfOnlyDifferenceIsCase
parameter_list|()
throws|throws
name|IOException
block|{
name|String
name|fileNamePattern
init|=
literal|"\\bibtexkey"
decl_stmt|;
name|File
name|tempFile
init|=
name|testFolder
operator|.
name|newFile
argument_list|(
literal|"toot.tmp"
argument_list|)
decl_stmt|;
name|ParsedFileField
name|fileField
init|=
operator|new
name|ParsedFileField
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
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"file"
argument_list|,
name|FileField
operator|.
name|getStringRepresentation
argument_list|(
name|fileField
argument_list|)
argument_list|)
expr_stmt|;
name|RenamePdfCleanup
name|cleanup
init|=
operator|new
name|RenamePdfCleanup
argument_list|(
literal|false
argument_list|,
name|context
argument_list|,
name|fileNamePattern
argument_list|,
name|mock
argument_list|(
name|LayoutFormatterPreferences
operator|.
name|class
argument_list|)
argument_list|,
name|prefs
operator|.
name|getFileDirectoryPreferences
argument_list|()
argument_list|)
decl_stmt|;
name|cleanup
operator|.
name|cleanup
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|ParsedFileField
name|newFileField
init|=
operator|new
name|ParsedFileField
argument_list|(
literal|""
argument_list|,
literal|"Toot.tmp"
argument_list|,
literal|""
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|FileField
operator|.
name|getStringRepresentation
argument_list|(
name|newFileField
argument_list|)
argument_list|)
argument_list|,
name|entry
operator|.
name|getFieldOptional
argument_list|(
literal|"file"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|cleanupRenamePdfRenamesWithMultipleFiles ()
specifier|public
name|void
name|cleanupRenamePdfRenamesWithMultipleFiles
parameter_list|()
throws|throws
name|IOException
block|{
name|String
name|fileNamePattern
init|=
literal|"\\bibtexkey - \\title"
decl_stmt|;
name|File
name|tempFile
init|=
name|testFolder
operator|.
name|newFile
argument_list|(
literal|"Toot.tmp"
argument_list|)
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"test title"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"file"
argument_list|,
name|FileField
operator|.
name|getStringRepresentation
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|ParsedFileField
argument_list|(
literal|""
argument_list|,
literal|""
argument_list|,
literal|""
argument_list|)
argument_list|,
operator|new
name|ParsedFileField
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
argument_list|,
operator|new
name|ParsedFileField
argument_list|(
literal|""
argument_list|,
literal|""
argument_list|,
literal|""
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|RenamePdfCleanup
name|cleanup
init|=
operator|new
name|RenamePdfCleanup
argument_list|(
literal|false
argument_list|,
name|context
argument_list|,
name|fileNamePattern
argument_list|,
name|mock
argument_list|(
name|LayoutFormatterPreferences
operator|.
name|class
argument_list|)
argument_list|,
name|prefs
operator|.
name|getFileDirectoryPreferences
argument_list|()
argument_list|)
decl_stmt|;
name|cleanup
operator|.
name|cleanup
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|FileField
operator|.
name|getStringRepresentation
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|ParsedFileField
argument_list|(
literal|""
argument_list|,
literal|""
argument_list|,
literal|""
argument_list|)
argument_list|,
operator|new
name|ParsedFileField
argument_list|(
literal|""
argument_list|,
literal|"Toot - test title.tmp"
argument_list|,
literal|""
argument_list|)
argument_list|,
operator|new
name|ParsedFileField
argument_list|(
literal|""
argument_list|,
literal|""
argument_list|,
literal|""
argument_list|)
argument_list|)
argument_list|)
argument_list|)
argument_list|,
name|entry
operator|.
name|getFieldOptional
argument_list|(
literal|"file"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|cleanupRenamePdfRenamesFileStartingWithBibtexKey ()
specifier|public
name|void
name|cleanupRenamePdfRenamesFileStartingWithBibtexKey
parameter_list|()
throws|throws
name|IOException
block|{
name|String
name|fileNamePattern
init|=
literal|"\\bibtexkey - \\title"
decl_stmt|;
name|File
name|tempFile
init|=
name|testFolder
operator|.
name|newFile
argument_list|(
literal|"Toot.tmp"
argument_list|)
decl_stmt|;
name|ParsedFileField
name|fileField
init|=
operator|new
name|ParsedFileField
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
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"file"
argument_list|,
name|FileField
operator|.
name|getStringRepresentation
argument_list|(
name|fileField
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"test title"
argument_list|)
expr_stmt|;
name|RenamePdfCleanup
name|cleanup
init|=
operator|new
name|RenamePdfCleanup
argument_list|(
literal|false
argument_list|,
name|context
argument_list|,
name|fileNamePattern
argument_list|,
name|mock
argument_list|(
name|LayoutFormatterPreferences
operator|.
name|class
argument_list|)
argument_list|,
name|prefs
operator|.
name|getFileDirectoryPreferences
argument_list|()
argument_list|)
decl_stmt|;
name|cleanup
operator|.
name|cleanup
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|ParsedFileField
name|newFileField
init|=
operator|new
name|ParsedFileField
argument_list|(
literal|""
argument_list|,
literal|"Toot - test title.tmp"
argument_list|,
literal|""
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|FileField
operator|.
name|getStringRepresentation
argument_list|(
name|newFileField
argument_list|)
argument_list|)
argument_list|,
name|entry
operator|.
name|getFieldOptional
argument_list|(
literal|"file"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|cleanupRenamePdfRenamesFileInSameFolder ()
specifier|public
name|void
name|cleanupRenamePdfRenamesFileInSameFolder
parameter_list|()
throws|throws
name|IOException
block|{
name|String
name|fileNamePattern
init|=
literal|"\\bibtexkey\\begin{title} - \\format[RemoveBrackets]{\\title}\\end{title}"
decl_stmt|;
name|testFolder
operator|.
name|newFile
argument_list|(
literal|"Toot.pdf"
argument_list|)
expr_stmt|;
name|ParsedFileField
name|fileField
init|=
operator|new
name|ParsedFileField
argument_list|(
literal|""
argument_list|,
literal|"Toot.pdf"
argument_list|,
literal|"PDF"
argument_list|)
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"file"
argument_list|,
name|FileField
operator|.
name|getStringRepresentation
argument_list|(
name|fileField
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"test title"
argument_list|)
expr_stmt|;
name|RenamePdfCleanup
name|cleanup
init|=
operator|new
name|RenamePdfCleanup
argument_list|(
literal|false
argument_list|,
name|context
argument_list|,
name|fileNamePattern
argument_list|,
name|LayoutFormatterPreferences
operator|.
name|fromPreferences
argument_list|(
name|prefs
argument_list|,
name|mock
argument_list|(
name|JournalAbbreviationLoader
operator|.
name|class
argument_list|)
argument_list|)
argument_list|,
name|prefs
operator|.
name|getFileDirectoryPreferences
argument_list|()
argument_list|)
decl_stmt|;
name|cleanup
operator|.
name|cleanup
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|ParsedFileField
name|newFileField
init|=
operator|new
name|ParsedFileField
argument_list|(
literal|""
argument_list|,
literal|"Toot - test title.pdf"
argument_list|,
literal|"PDF"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|FileField
operator|.
name|getStringRepresentation
argument_list|(
name|newFileField
argument_list|)
argument_list|)
argument_list|,
name|entry
operator|.
name|getFieldOptional
argument_list|(
literal|"file"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

