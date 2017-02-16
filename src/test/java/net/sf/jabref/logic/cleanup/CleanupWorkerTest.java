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
name|ArrayList
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
name|EnumSet
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
name|logic
operator|.
name|formatter
operator|.
name|bibtexfields
operator|.
name|HtmlToLatexFormatter
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
name|formatter
operator|.
name|bibtexfields
operator|.
name|LatexCleanupFormatter
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
name|formatter
operator|.
name|bibtexfields
operator|.
name|NormalizeDateFormatter
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
name|formatter
operator|.
name|bibtexfields
operator|.
name|NormalizeMonthFormatter
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
name|formatter
operator|.
name|bibtexfields
operator|.
name|NormalizePagesFormatter
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
name|formatter
operator|.
name|bibtexfields
operator|.
name|UnitsToLatexFormatter
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
name|formatter
operator|.
name|casechanger
operator|.
name|ProtectTermsFormatter
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
name|protectedterms
operator|.
name|ProtectedTermsLoader
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
name|protectedterms
operator|.
name|ProtectedTermsPreferences
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
name|FieldChange
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
name|cleanup
operator|.
name|FieldFormatterCleanup
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
name|cleanup
operator|.
name|FieldFormatterCleanups
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
name|FileField
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
name|ParsedFileField
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
name|Assert
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
name|mockito
operator|.
name|Mockito
operator|.
name|mock
import|;
end_import

begin_class
DECL|class|CleanupWorkerTest
specifier|public
class|class
name|CleanupWorkerTest
block|{
annotation|@
name|Rule
DECL|field|bibFolder
specifier|public
name|TemporaryFolder
name|bibFolder
init|=
operator|new
name|TemporaryFolder
argument_list|()
decl_stmt|;
DECL|field|emptyPreset
specifier|private
specifier|final
name|CleanupPreset
name|emptyPreset
init|=
operator|new
name|CleanupPreset
argument_list|(
name|EnumSet
operator|.
name|noneOf
argument_list|(
name|CleanupPreset
operator|.
name|CleanupStep
operator|.
name|class
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|worker
specifier|private
name|CleanupWorker
name|worker
decl_stmt|;
DECL|field|pdfFolder
specifier|private
name|File
name|pdfFolder
decl_stmt|;
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
throws|throws
name|IOException
block|{
name|pdfFolder
operator|=
name|bibFolder
operator|.
name|newFolder
argument_list|()
expr_stmt|;
name|MetaData
name|metaData
init|=
operator|new
name|MetaData
argument_list|()
decl_stmt|;
name|metaData
operator|.
name|setDefaultFileDirectory
argument_list|(
name|pdfFolder
operator|.
name|getAbsolutePath
argument_list|()
argument_list|)
expr_stmt|;
name|BibDatabaseContext
name|context
init|=
operator|new
name|BibDatabaseContext
argument_list|(
operator|new
name|BibDatabase
argument_list|()
argument_list|,
name|metaData
argument_list|,
name|bibFolder
operator|.
name|newFile
argument_list|(
literal|"test.bib"
argument_list|)
argument_list|)
decl_stmt|;
name|JabRefPreferences
name|prefs
init|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|worker
operator|=
operator|new
name|CleanupWorker
argument_list|(
name|context
argument_list|,
operator|new
name|CleanupPreferences
argument_list|(
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|IMPORT_FILENAMEPATTERN
argument_list|)
argument_list|,
literal|""
argument_list|,
comment|//empty fileDirPattern for backwards compatibility
name|prefs
operator|.
name|getLayoutFormatterPreferences
argument_list|(
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
DECL|method|cleanupWithNullPresetThrowsException ()
specifier|public
name|void
name|cleanupWithNullPresetThrowsException
parameter_list|()
block|{
name|worker
operator|.
name|cleanup
argument_list|(
literal|null
argument_list|,
operator|new
name|BibEntry
argument_list|()
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
DECL|method|cleanupNullEntryThrowsException ()
specifier|public
name|void
name|cleanupNullEntryThrowsException
parameter_list|()
block|{
name|worker
operator|.
name|cleanup
argument_list|(
name|emptyPreset
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|cleanupDoesNothingByDefault ()
specifier|public
name|void
name|cleanupDoesNothingByDefault
parameter_list|()
throws|throws
name|IOException
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setCiteKey
argument_list|(
literal|"Toot"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"pdf"
argument_list|,
literal|"aPdfFile"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"some"
argument_list|,
literal|"1st"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"doi"
argument_list|,
literal|"http://dx.doi.org/10.1016/0001-8708(80)90035-3"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"month"
argument_list|,
literal|"01"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"pages"
argument_list|,
literal|"1-2"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"date"
argument_list|,
literal|"01/1999"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"pdf"
argument_list|,
literal|"aPdfFile"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"ps"
argument_list|,
literal|"aPsFile"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"file"
argument_list|,
literal|"link::"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"journal"
argument_list|,
literal|"test"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"<b>hallo</b> units 1 A case AlGaAs and latex $\\alpha$$\\beta$"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"abstract"
argument_list|,
literal|"RÃ©flexions"
argument_list|)
expr_stmt|;
name|File
name|tempFile
init|=
name|bibFolder
operator|.
name|newFile
argument_list|()
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
name|List
argument_list|<
name|FieldChange
argument_list|>
name|changes
init|=
name|worker
operator|.
name|cleanup
argument_list|(
name|emptyPreset
argument_list|,
name|entry
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|changes
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|upgradeExternalLinksMoveFromPdfToFile ()
specifier|public
name|void
name|upgradeExternalLinksMoveFromPdfToFile
parameter_list|()
block|{
name|CleanupPreset
name|preset
init|=
operator|new
name|CleanupPreset
argument_list|(
name|CleanupPreset
operator|.
name|CleanupStep
operator|.
name|CLEAN_UP_UPGRADE_EXTERNAL_LINKS
argument_list|)
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"pdf"
argument_list|,
literal|"aPdfFile"
argument_list|)
expr_stmt|;
name|worker
operator|.
name|cleanup
argument_list|(
name|preset
argument_list|,
name|entry
argument_list|)
expr_stmt|;
name|Assert
operator|.
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
literal|"pdf"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"aPdfFile:aPdfFile:PDF"
argument_list|)
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"file"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|upgradeExternalLinksMoveFromPsToFile ()
specifier|public
name|void
name|upgradeExternalLinksMoveFromPsToFile
parameter_list|()
block|{
name|CleanupPreset
name|preset
init|=
operator|new
name|CleanupPreset
argument_list|(
name|CleanupPreset
operator|.
name|CleanupStep
operator|.
name|CLEAN_UP_UPGRADE_EXTERNAL_LINKS
argument_list|)
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"ps"
argument_list|,
literal|"aPsFile"
argument_list|)
expr_stmt|;
name|worker
operator|.
name|cleanup
argument_list|(
name|preset
argument_list|,
name|entry
argument_list|)
expr_stmt|;
name|Assert
operator|.
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
literal|"pdf"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"aPsFile:aPsFile:PostScript"
argument_list|)
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"file"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|cleanupDoiRemovesLeadingHttp ()
specifier|public
name|void
name|cleanupDoiRemovesLeadingHttp
parameter_list|()
block|{
name|CleanupPreset
name|preset
init|=
operator|new
name|CleanupPreset
argument_list|(
name|CleanupPreset
operator|.
name|CleanupStep
operator|.
name|CLEAN_UP_DOI
argument_list|)
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"doi"
argument_list|,
literal|"http://dx.doi.org/10.1016/0001-8708(80)90035-3"
argument_list|)
expr_stmt|;
name|worker
operator|.
name|cleanup
argument_list|(
name|preset
argument_list|,
name|entry
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"10.1016/0001-8708(80)90035-3"
argument_list|)
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"doi"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|cleanupDoiReturnsChanges ()
specifier|public
name|void
name|cleanupDoiReturnsChanges
parameter_list|()
block|{
name|CleanupPreset
name|preset
init|=
operator|new
name|CleanupPreset
argument_list|(
name|CleanupPreset
operator|.
name|CleanupStep
operator|.
name|CLEAN_UP_DOI
argument_list|)
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"doi"
argument_list|,
literal|"http://dx.doi.org/10.1016/0001-8708(80)90035-3"
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|FieldChange
argument_list|>
name|changes
init|=
name|worker
operator|.
name|cleanup
argument_list|(
name|preset
argument_list|,
name|entry
argument_list|)
decl_stmt|;
name|FieldChange
name|expectedChange
init|=
operator|new
name|FieldChange
argument_list|(
name|entry
argument_list|,
literal|"doi"
argument_list|,
literal|"http://dx.doi.org/10.1016/0001-8708(80)90035-3"
argument_list|,
literal|"10.1016/0001-8708(80)90035-3"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
name|expectedChange
argument_list|)
argument_list|,
name|changes
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|cleanupDoiFindsDoiInURLFieldAndMoveItToDOIField ()
specifier|public
name|void
name|cleanupDoiFindsDoiInURLFieldAndMoveItToDOIField
parameter_list|()
block|{
name|CleanupPreset
name|preset
init|=
operator|new
name|CleanupPreset
argument_list|(
name|CleanupPreset
operator|.
name|CleanupStep
operator|.
name|CLEAN_UP_DOI
argument_list|)
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"url"
argument_list|,
literal|"http://dx.doi.org/10.1016/0001-8708(80)90035-3"
argument_list|)
expr_stmt|;
name|worker
operator|.
name|cleanup
argument_list|(
name|preset
argument_list|,
name|entry
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"10.1016/0001-8708(80)90035-3"
argument_list|)
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"doi"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
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
literal|"url"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|cleanupDoiReturnsChangeWhenDoiInURLField ()
specifier|public
name|void
name|cleanupDoiReturnsChangeWhenDoiInURLField
parameter_list|()
block|{
name|CleanupPreset
name|preset
init|=
operator|new
name|CleanupPreset
argument_list|(
name|CleanupPreset
operator|.
name|CleanupStep
operator|.
name|CLEAN_UP_DOI
argument_list|)
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"url"
argument_list|,
literal|"http://dx.doi.org/10.1016/0001-8708(80)90035-3"
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|FieldChange
argument_list|>
name|changes
init|=
name|worker
operator|.
name|cleanup
argument_list|(
name|preset
argument_list|,
name|entry
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|FieldChange
argument_list|>
name|changeList
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|changeList
operator|.
name|add
argument_list|(
operator|new
name|FieldChange
argument_list|(
name|entry
argument_list|,
literal|"doi"
argument_list|,
literal|null
argument_list|,
literal|"10.1016/0001-8708(80)90035-3"
argument_list|)
argument_list|)
expr_stmt|;
name|changeList
operator|.
name|add
argument_list|(
operator|new
name|FieldChange
argument_list|(
name|entry
argument_list|,
literal|"url"
argument_list|,
literal|"http://dx.doi.org/10.1016/0001-8708(80)90035-3"
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|changeList
argument_list|,
name|changes
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|cleanupMonthChangesNumberToBibtex ()
specifier|public
name|void
name|cleanupMonthChangesNumberToBibtex
parameter_list|()
block|{
name|CleanupPreset
name|preset
init|=
operator|new
name|CleanupPreset
argument_list|(
operator|new
name|FieldFormatterCleanups
argument_list|(
literal|true
argument_list|,
name|Collections
operator|.
name|singletonList
argument_list|(
operator|new
name|FieldFormatterCleanup
argument_list|(
literal|"month"
argument_list|,
operator|new
name|NormalizeMonthFormatter
argument_list|()
argument_list|)
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"month"
argument_list|,
literal|"01"
argument_list|)
expr_stmt|;
name|worker
operator|.
name|cleanup
argument_list|(
name|preset
argument_list|,
name|entry
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"#jan#"
argument_list|)
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"month"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|cleanupPageNumbersConvertsSingleDashToDouble ()
specifier|public
name|void
name|cleanupPageNumbersConvertsSingleDashToDouble
parameter_list|()
block|{
name|CleanupPreset
name|preset
init|=
operator|new
name|CleanupPreset
argument_list|(
operator|new
name|FieldFormatterCleanups
argument_list|(
literal|true
argument_list|,
name|Collections
operator|.
name|singletonList
argument_list|(
operator|new
name|FieldFormatterCleanup
argument_list|(
literal|"pages"
argument_list|,
operator|new
name|NormalizePagesFormatter
argument_list|()
argument_list|)
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"pages"
argument_list|,
literal|"1-2"
argument_list|)
expr_stmt|;
name|worker
operator|.
name|cleanup
argument_list|(
name|preset
argument_list|,
name|entry
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"1--2"
argument_list|)
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"pages"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|cleanupDatesConvertsToCorrectFormat ()
specifier|public
name|void
name|cleanupDatesConvertsToCorrectFormat
parameter_list|()
block|{
name|CleanupPreset
name|preset
init|=
operator|new
name|CleanupPreset
argument_list|(
operator|new
name|FieldFormatterCleanups
argument_list|(
literal|true
argument_list|,
name|Collections
operator|.
name|singletonList
argument_list|(
operator|new
name|FieldFormatterCleanup
argument_list|(
literal|"date"
argument_list|,
operator|new
name|NormalizeDateFormatter
argument_list|()
argument_list|)
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"date"
argument_list|,
literal|"01/1999"
argument_list|)
expr_stmt|;
name|worker
operator|.
name|cleanup
argument_list|(
name|preset
argument_list|,
name|entry
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"1999-01"
argument_list|)
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"date"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|cleanupFixFileLinksMovesSingleDescriptionToLink ()
specifier|public
name|void
name|cleanupFixFileLinksMovesSingleDescriptionToLink
parameter_list|()
block|{
name|CleanupPreset
name|preset
init|=
operator|new
name|CleanupPreset
argument_list|(
name|CleanupPreset
operator|.
name|CleanupStep
operator|.
name|FIX_FILE_LINKS
argument_list|)
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"file"
argument_list|,
literal|"link::"
argument_list|)
expr_stmt|;
name|worker
operator|.
name|cleanup
argument_list|(
name|preset
argument_list|,
name|entry
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|":link:"
argument_list|)
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"file"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|cleanupMoveFilesMovesFileFromSubfolder ()
specifier|public
name|void
name|cleanupMoveFilesMovesFileFromSubfolder
parameter_list|()
throws|throws
name|IOException
block|{
name|CleanupPreset
name|preset
init|=
operator|new
name|CleanupPreset
argument_list|(
name|CleanupPreset
operator|.
name|CleanupStep
operator|.
name|MOVE_PDF
argument_list|)
decl_stmt|;
name|File
name|subfolder
init|=
name|bibFolder
operator|.
name|newFolder
argument_list|()
decl_stmt|;
name|File
name|tempFile
init|=
operator|new
name|File
argument_list|(
name|subfolder
argument_list|,
literal|"test.pdf"
argument_list|)
decl_stmt|;
name|tempFile
operator|.
name|createNewFile
argument_list|()
expr_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
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
name|worker
operator|.
name|cleanup
argument_list|(
name|preset
argument_list|,
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
name|tempFile
operator|.
name|getName
argument_list|()
argument_list|,
literal|""
argument_list|)
decl_stmt|;
name|Assert
operator|.
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
name|getField
argument_list|(
literal|"file"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|cleanupRelativePathsConvertAbsoluteToRelativePath ()
specifier|public
name|void
name|cleanupRelativePathsConvertAbsoluteToRelativePath
parameter_list|()
throws|throws
name|IOException
block|{
name|CleanupPreset
name|preset
init|=
operator|new
name|CleanupPreset
argument_list|(
name|CleanupPreset
operator|.
name|CleanupStep
operator|.
name|MAKE_PATHS_RELATIVE
argument_list|)
decl_stmt|;
name|File
name|tempFile
init|=
name|bibFolder
operator|.
name|newFile
argument_list|()
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
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
name|worker
operator|.
name|cleanup
argument_list|(
name|preset
argument_list|,
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
name|tempFile
operator|.
name|getName
argument_list|()
argument_list|,
literal|""
argument_list|)
decl_stmt|;
name|Assert
operator|.
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
name|getField
argument_list|(
literal|"file"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|cleanupRenamePdfRenamesRelativeFile ()
specifier|public
name|void
name|cleanupRenamePdfRenamesRelativeFile
parameter_list|()
throws|throws
name|IOException
block|{
name|CleanupPreset
name|preset
init|=
operator|new
name|CleanupPreset
argument_list|(
name|CleanupPreset
operator|.
name|CleanupStep
operator|.
name|RENAME_PDF
argument_list|)
decl_stmt|;
name|File
name|tempFile
init|=
name|bibFolder
operator|.
name|newFile
argument_list|()
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setCiteKey
argument_list|(
literal|"Toot"
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
name|worker
operator|.
name|cleanup
argument_list|(
name|preset
argument_list|,
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
name|Assert
operator|.
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
name|getField
argument_list|(
literal|"file"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|cleanupHtmlToLatexConvertsEpsilonToLatex ()
specifier|public
name|void
name|cleanupHtmlToLatexConvertsEpsilonToLatex
parameter_list|()
block|{
name|CleanupPreset
name|preset
init|=
operator|new
name|CleanupPreset
argument_list|(
operator|new
name|FieldFormatterCleanups
argument_list|(
literal|true
argument_list|,
name|Collections
operator|.
name|singletonList
argument_list|(
operator|new
name|FieldFormatterCleanup
argument_list|(
literal|"title"
argument_list|,
operator|new
name|HtmlToLatexFormatter
argument_list|()
argument_list|)
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"&Epsilon;"
argument_list|)
expr_stmt|;
name|worker
operator|.
name|cleanup
argument_list|(
name|preset
argument_list|,
name|entry
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"{{$\\Epsilon$}}"
argument_list|)
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|cleanupUnitsConvertsOneAmpereToLatex ()
specifier|public
name|void
name|cleanupUnitsConvertsOneAmpereToLatex
parameter_list|()
block|{
name|CleanupPreset
name|preset
init|=
operator|new
name|CleanupPreset
argument_list|(
operator|new
name|FieldFormatterCleanups
argument_list|(
literal|true
argument_list|,
name|Collections
operator|.
name|singletonList
argument_list|(
operator|new
name|FieldFormatterCleanup
argument_list|(
literal|"title"
argument_list|,
operator|new
name|UnitsToLatexFormatter
argument_list|()
argument_list|)
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"1 A"
argument_list|)
expr_stmt|;
name|worker
operator|.
name|cleanup
argument_list|(
name|preset
argument_list|,
name|entry
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"1~{A}"
argument_list|)
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|cleanupCasesAddsBracketAroundAluminiumGalliumArsenid ()
specifier|public
name|void
name|cleanupCasesAddsBracketAroundAluminiumGalliumArsenid
parameter_list|()
block|{
name|ProtectedTermsLoader
name|protectedTermsLoader
init|=
operator|new
name|ProtectedTermsLoader
argument_list|(
operator|new
name|ProtectedTermsPreferences
argument_list|(
name|ProtectedTermsLoader
operator|.
name|getInternalLists
argument_list|()
argument_list|,
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertNotEquals
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|protectedTermsLoader
operator|.
name|getProtectedTerms
argument_list|()
argument_list|)
expr_stmt|;
name|CleanupPreset
name|preset
init|=
operator|new
name|CleanupPreset
argument_list|(
operator|new
name|FieldFormatterCleanups
argument_list|(
literal|true
argument_list|,
name|Collections
operator|.
name|singletonList
argument_list|(
operator|new
name|FieldFormatterCleanup
argument_list|(
literal|"title"
argument_list|,
operator|new
name|ProtectTermsFormatter
argument_list|(
name|protectedTermsLoader
argument_list|)
argument_list|)
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"AlGaAs"
argument_list|)
expr_stmt|;
name|worker
operator|.
name|cleanup
argument_list|(
name|preset
argument_list|,
name|entry
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"{AlGaAs}"
argument_list|)
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|cleanupLatexMergesTwoLatexMathEnvironments ()
specifier|public
name|void
name|cleanupLatexMergesTwoLatexMathEnvironments
parameter_list|()
block|{
name|CleanupPreset
name|preset
init|=
operator|new
name|CleanupPreset
argument_list|(
operator|new
name|FieldFormatterCleanups
argument_list|(
literal|true
argument_list|,
name|Collections
operator|.
name|singletonList
argument_list|(
operator|new
name|FieldFormatterCleanup
argument_list|(
literal|"title"
argument_list|,
operator|new
name|LatexCleanupFormatter
argument_list|()
argument_list|)
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"$\\alpha$$\\beta$"
argument_list|)
expr_stmt|;
name|worker
operator|.
name|cleanup
argument_list|(
name|preset
argument_list|,
name|entry
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"$\\alpha\\beta$"
argument_list|)
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|convertToBiblatexMovesAddressToLocation ()
specifier|public
name|void
name|convertToBiblatexMovesAddressToLocation
parameter_list|()
block|{
name|CleanupPreset
name|preset
init|=
operator|new
name|CleanupPreset
argument_list|(
name|CleanupPreset
operator|.
name|CleanupStep
operator|.
name|CONVERT_TO_BIBLATEX
argument_list|)
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"address"
argument_list|,
literal|"test"
argument_list|)
expr_stmt|;
name|worker
operator|.
name|cleanup
argument_list|(
name|preset
argument_list|,
name|entry
argument_list|)
expr_stmt|;
name|Assert
operator|.
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
literal|"address"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"test"
argument_list|)
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"location"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|convertToBiblatexMovesJournalToJournalTitle ()
specifier|public
name|void
name|convertToBiblatexMovesJournalToJournalTitle
parameter_list|()
block|{
name|CleanupPreset
name|preset
init|=
operator|new
name|CleanupPreset
argument_list|(
name|CleanupPreset
operator|.
name|CleanupStep
operator|.
name|CONVERT_TO_BIBLATEX
argument_list|)
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"journal"
argument_list|,
literal|"test"
argument_list|)
expr_stmt|;
name|worker
operator|.
name|cleanup
argument_list|(
name|preset
argument_list|,
name|entry
argument_list|)
expr_stmt|;
name|Assert
operator|.
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
literal|"journal"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"test"
argument_list|)
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"journaltitle"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|cleanupWithDisabledFieldFormatterChangesNothing ()
specifier|public
name|void
name|cleanupWithDisabledFieldFormatterChangesNothing
parameter_list|()
block|{
name|CleanupPreset
name|preset
init|=
operator|new
name|CleanupPreset
argument_list|(
operator|new
name|FieldFormatterCleanups
argument_list|(
literal|false
argument_list|,
name|Collections
operator|.
name|singletonList
argument_list|(
operator|new
name|FieldFormatterCleanup
argument_list|(
literal|"month"
argument_list|,
operator|new
name|NormalizeMonthFormatter
argument_list|()
argument_list|)
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"month"
argument_list|,
literal|"01"
argument_list|)
expr_stmt|;
name|worker
operator|.
name|cleanup
argument_list|(
name|preset
argument_list|,
name|entry
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"01"
argument_list|)
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"month"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

