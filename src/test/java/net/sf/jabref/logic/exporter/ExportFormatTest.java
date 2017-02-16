begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.exporter
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|exporter
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
name|nio
operator|.
name|charset
operator|.
name|Charset
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
name|Files
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
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
name|java
operator|.
name|util
operator|.
name|Map
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
name|com
operator|.
name|google
operator|.
name|common
operator|.
name|base
operator|.
name|Charsets
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
import|import
name|org
operator|.
name|junit
operator|.
name|runner
operator|.
name|RunWith
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|runners
operator|.
name|Parameterized
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
annotation|@
name|RunWith
argument_list|(
name|Parameterized
operator|.
name|class
argument_list|)
DECL|class|ExportFormatTest
specifier|public
class|class
name|ExportFormatTest
block|{
DECL|field|exportFormat
specifier|public
name|IExportFormat
name|exportFormat
decl_stmt|;
DECL|field|exportFormatName
specifier|public
name|String
name|exportFormatName
decl_stmt|;
DECL|field|databaseContext
specifier|public
name|BibDatabaseContext
name|databaseContext
decl_stmt|;
DECL|field|charset
specifier|public
name|Charset
name|charset
decl_stmt|;
DECL|field|entries
specifier|public
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
decl_stmt|;
DECL|method|ExportFormatTest (IExportFormat format, String name)
specifier|public
name|ExportFormatTest
parameter_list|(
name|IExportFormat
name|format
parameter_list|,
name|String
name|name
parameter_list|)
block|{
name|exportFormat
operator|=
name|format
expr_stmt|;
name|exportFormatName
operator|=
name|name
expr_stmt|;
block|}
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
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|databaseContext
operator|=
operator|new
name|BibDatabaseContext
argument_list|()
expr_stmt|;
name|charset
operator|=
name|Charsets
operator|.
name|UTF_8
expr_stmt|;
name|entries
operator|=
name|Collections
operator|.
name|emptyList
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testExportingEmptyDatabaseYieldsEmptyFile ()
specifier|public
name|void
name|testExportingEmptyDatabaseYieldsEmptyFile
parameter_list|()
throws|throws
name|Exception
block|{
name|File
name|tmpFile
init|=
name|testFolder
operator|.
name|newFile
argument_list|()
decl_stmt|;
name|String
name|filename
init|=
name|tmpFile
operator|.
name|getCanonicalPath
argument_list|()
decl_stmt|;
name|exportFormat
operator|.
name|performExport
argument_list|(
name|databaseContext
argument_list|,
name|filename
argument_list|,
name|charset
argument_list|,
name|entries
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|Files
operator|.
name|readAllLines
argument_list|(
name|tmpFile
operator|.
name|toPath
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|NullPointerException
operator|.
name|class
argument_list|)
DECL|method|testExportingNullDatabaseThrowsNPE ()
specifier|public
name|void
name|testExportingNullDatabaseThrowsNPE
parameter_list|()
throws|throws
name|Exception
block|{
name|File
name|tmpFile
init|=
name|testFolder
operator|.
name|newFile
argument_list|()
decl_stmt|;
name|String
name|filename
init|=
name|tmpFile
operator|.
name|getCanonicalPath
argument_list|()
decl_stmt|;
name|exportFormat
operator|.
name|performExport
argument_list|(
literal|null
argument_list|,
name|filename
argument_list|,
name|charset
argument_list|,
name|entries
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|NullPointerException
operator|.
name|class
argument_list|)
DECL|method|testExportingNullEntriesThrowsNPE ()
specifier|public
name|void
name|testExportingNullEntriesThrowsNPE
parameter_list|()
throws|throws
name|Exception
block|{
name|File
name|tmpFile
init|=
name|testFolder
operator|.
name|newFile
argument_list|()
decl_stmt|;
name|String
name|filename
init|=
name|tmpFile
operator|.
name|getCanonicalPath
argument_list|()
decl_stmt|;
name|exportFormat
operator|.
name|performExport
argument_list|(
name|databaseContext
argument_list|,
name|filename
argument_list|,
name|charset
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Parameterized
operator|.
name|Parameters
argument_list|(
name|name
operator|=
literal|"{index}: {1}"
argument_list|)
DECL|method|exportFormats ()
specifier|public
specifier|static
name|Collection
argument_list|<
name|Object
index|[]
argument_list|>
name|exportFormats
parameter_list|()
block|{
name|Collection
argument_list|<
name|Object
index|[]
argument_list|>
name|result
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|JabRefPreferences
name|prefs
init|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|JournalAbbreviationLoader
name|journalAbbreviationLoader
init|=
operator|new
name|JournalAbbreviationLoader
argument_list|()
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|ExportFormat
argument_list|>
name|customFormats
init|=
name|prefs
operator|.
name|customExports
operator|.
name|getCustomExportFormats
argument_list|(
name|prefs
argument_list|,
name|journalAbbreviationLoader
argument_list|)
decl_stmt|;
name|LayoutFormatterPreferences
name|layoutPreferences
init|=
name|prefs
operator|.
name|getLayoutFormatterPreferences
argument_list|(
name|journalAbbreviationLoader
argument_list|)
decl_stmt|;
name|SavePreferences
name|savePreferences
init|=
name|SavePreferences
operator|.
name|loadForExportFromPreferences
argument_list|(
name|prefs
argument_list|)
decl_stmt|;
name|ExportFormats
operator|.
name|initAllExports
argument_list|(
name|customFormats
argument_list|,
name|layoutPreferences
argument_list|,
name|savePreferences
argument_list|)
expr_stmt|;
for|for
control|(
name|IExportFormat
name|format
range|:
name|ExportFormats
operator|.
name|getExportFormats
argument_list|()
operator|.
name|values
argument_list|()
control|)
block|{
name|result
operator|.
name|add
argument_list|(
operator|new
name|Object
index|[]
block|{
name|format
block|,
name|format
operator|.
name|getDisplayName
argument_list|()
block|}
argument_list|)
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
block|}
end_class

end_unit

