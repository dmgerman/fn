begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.bibtex
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|bibtex
package|;
end_package

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
name|io
operator|.
name|StringReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|StringWriter
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
name|Set
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
name|importer
operator|.
name|ParserResult
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
name|importer
operator|.
name|fileformat
operator|.
name|BibtexParser
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
name|BibDatabaseMode
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
name|AfterClass
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
name|BeforeClass
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

begin_class
DECL|class|BibEntryWriterTest
specifier|public
class|class
name|BibEntryWriterTest
block|{
DECL|field|writer
specifier|private
name|BibEntryWriter
name|writer
decl_stmt|;
DECL|field|backup
specifier|private
specifier|static
name|JabRefPreferences
name|backup
decl_stmt|;
annotation|@
name|BeforeClass
DECL|method|setUp ()
specifier|public
specifier|static
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
name|backup
operator|=
name|Globals
operator|.
name|prefs
expr_stmt|;
block|}
annotation|@
name|AfterClass
DECL|method|tearDown ()
specifier|public
specifier|static
name|void
name|tearDown
parameter_list|()
block|{
name|Globals
operator|.
name|prefs
operator|.
name|overwritePreferences
argument_list|(
name|backup
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Before
DECL|method|setUpWriter ()
specifier|public
name|void
name|setUpWriter
parameter_list|()
block|{
name|writer
operator|=
operator|new
name|BibEntryWriter
argument_list|(
operator|new
name|LatexFieldFormatter
argument_list|(
name|LatexFieldFormatterPreferences
operator|.
name|fromPreferences
argument_list|(
name|Globals
operator|.
name|prefs
argument_list|)
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSerialization ()
specifier|public
name|void
name|testSerialization
parameter_list|()
throws|throws
name|IOException
block|{
name|StringWriter
name|stringWriter
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|(
literal|"1234"
argument_list|,
literal|"article"
argument_list|)
decl_stmt|;
comment|//set a required field
name|entry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"Foo Bar"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"journal"
argument_list|,
literal|"International Journal of Something"
argument_list|)
expr_stmt|;
comment|//set an optional field
name|entry
operator|.
name|setField
argument_list|(
literal|"number"
argument_list|,
literal|"1"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"note"
argument_list|,
literal|"some note"
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|entry
argument_list|,
name|stringWriter
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
expr_stmt|;
name|String
name|actual
init|=
name|stringWriter
operator|.
name|toString
argument_list|()
decl_stmt|;
comment|// @formatter:off
name|String
name|expected
init|=
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"@Article{,"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  author  = {Foo Bar},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  journal = {International Journal of Something},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  number  = {1},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  note    = {some note},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"}"
operator|+
name|Globals
operator|.
name|NEWLINE
decl_stmt|;
comment|// @formatter:on
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|actual
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|roundTripTest ()
specifier|public
name|void
name|roundTripTest
parameter_list|()
throws|throws
name|IOException
block|{
comment|// @formatter:off
name|String
name|bibtexEntry
init|=
literal|"@Article{test,"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  Author                   = {Foo Bar},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  Journal                  = {International Journal of Something},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  Note                     = {some note},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  Number                   = {1}"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"}"
decl_stmt|;
comment|// @formatter:on
comment|// read in bibtex string
name|ParserResult
name|result
init|=
name|BibtexParser
operator|.
name|parse
argument_list|(
operator|new
name|StringReader
argument_list|(
name|bibtexEntry
argument_list|)
argument_list|)
decl_stmt|;
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|result
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|BibEntry
name|entry
init|=
name|entries
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
comment|//write out bibtex string
name|StringWriter
name|stringWriter
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|entry
argument_list|,
name|stringWriter
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
expr_stmt|;
name|String
name|actual
init|=
name|stringWriter
operator|.
name|toString
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
name|bibtexEntry
argument_list|,
name|actual
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|roundTripWithPrependingNewlines ()
specifier|public
name|void
name|roundTripWithPrependingNewlines
parameter_list|()
throws|throws
name|IOException
block|{
comment|// @formatter:off
name|String
name|bibtexEntry
init|=
literal|"\r\n@Article{test,"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  Author                   = {Foo Bar},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  Journal                  = {International Journal of Something},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  Note                     = {some note},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  Number                   = {1}"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"}"
decl_stmt|;
comment|// @formatter:on
comment|// read in bibtex string
name|ParserResult
name|result
init|=
name|BibtexParser
operator|.
name|parse
argument_list|(
operator|new
name|StringReader
argument_list|(
name|bibtexEntry
argument_list|)
argument_list|)
decl_stmt|;
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|result
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|BibEntry
name|entry
init|=
name|entries
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
comment|//write out bibtex string
name|StringWriter
name|stringWriter
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|entry
argument_list|,
name|stringWriter
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
expr_stmt|;
name|String
name|actual
init|=
name|stringWriter
operator|.
name|toString
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
name|bibtexEntry
argument_list|,
name|actual
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|roundTripWithModification ()
specifier|public
name|void
name|roundTripWithModification
parameter_list|()
throws|throws
name|IOException
block|{
comment|// @formatter:off
name|String
name|bibtexEntry
init|=
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"@Article{test,"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  Author                   = {Foo Bar},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  Journal                  = {International Journal of Something},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  Note                     = {some note},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  Number                   = {1},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"}"
decl_stmt|;
comment|// @formatter:on
comment|// read in bibtex string
name|ParserResult
name|result
init|=
name|BibtexParser
operator|.
name|parse
argument_list|(
operator|new
name|StringReader
argument_list|(
name|bibtexEntry
argument_list|)
argument_list|)
decl_stmt|;
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|result
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|BibEntry
name|entry
init|=
name|entries
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
comment|// Modify entry
name|entry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"BlaBla"
argument_list|)
expr_stmt|;
comment|// write out bibtex string
name|StringWriter
name|stringWriter
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|entry
argument_list|,
name|stringWriter
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
expr_stmt|;
name|String
name|actual
init|=
name|stringWriter
operator|.
name|toString
argument_list|()
decl_stmt|;
comment|// @formatter:off
name|String
name|expected
init|=
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"@Article{test,"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  author  = {BlaBla},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  journal = {International Journal of Something},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  number  = {1},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  note    = {some note},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"}"
operator|+
name|Globals
operator|.
name|NEWLINE
decl_stmt|;
comment|// @formatter:on
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|actual
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|roundTripWithCamelCasingInTheOriginalEntryAndResultInLowerCase ()
specifier|public
name|void
name|roundTripWithCamelCasingInTheOriginalEntryAndResultInLowerCase
parameter_list|()
throws|throws
name|IOException
block|{
comment|// @formatter:off
name|String
name|bibtexEntry
init|=
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"@Article{test,"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  Author                   = {Foo Bar},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  Journal                  = {International Journal of Something},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  Note                     = {some note},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  Number                   = {1},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  HowPublished             = {asdf},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"}"
decl_stmt|;
comment|// @formatter:on
comment|// read in bibtex string
name|ParserResult
name|result
init|=
name|BibtexParser
operator|.
name|parse
argument_list|(
operator|new
name|StringReader
argument_list|(
name|bibtexEntry
argument_list|)
argument_list|)
decl_stmt|;
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|result
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|BibEntry
name|entry
init|=
name|entries
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
comment|// modify entry
name|entry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"BlaBla"
argument_list|)
expr_stmt|;
comment|//write out bibtex string
name|StringWriter
name|stringWriter
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|entry
argument_list|,
name|stringWriter
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
expr_stmt|;
name|String
name|actual
init|=
name|stringWriter
operator|.
name|toString
argument_list|()
decl_stmt|;
comment|// @formatter:off
name|String
name|expected
init|=
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"@Article{test,"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  author       = {BlaBla},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  journal      = {International Journal of Something},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  number       = {1},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  note         = {some note},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  howpublished = {asdf},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"}"
operator|+
name|Globals
operator|.
name|NEWLINE
decl_stmt|;
comment|// @formatter:on
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|actual
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testEntryTypeChange ()
specifier|public
name|void
name|testEntryTypeChange
parameter_list|()
throws|throws
name|IOException
block|{
comment|// @formatter:off
name|String
name|expected
init|=
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"@Article{test,"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  author       = {BlaBla},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  journal      = {International Journal of Something},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  number       = {1},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  note         = {some note},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  howpublished = {asdf},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"}"
operator|+
name|Globals
operator|.
name|NEWLINE
decl_stmt|;
comment|// @formatter:on
comment|// read in bibtex string
name|ParserResult
name|result
init|=
name|BibtexParser
operator|.
name|parse
argument_list|(
operator|new
name|StringReader
argument_list|(
name|expected
argument_list|)
argument_list|)
decl_stmt|;
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|result
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|BibEntry
name|entry
init|=
name|entries
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
comment|// modify entry
name|entry
operator|.
name|setType
argument_list|(
literal|"inproceedings"
argument_list|)
expr_stmt|;
comment|//write out bibtex string
name|StringWriter
name|stringWriter
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|entry
argument_list|,
name|stringWriter
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
expr_stmt|;
name|String
name|actual
init|=
name|stringWriter
operator|.
name|toString
argument_list|()
decl_stmt|;
comment|// @formatter:off
name|String
name|expectedNewEntry
init|=
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"@InProceedings{test,"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  author       = {BlaBla},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  number       = {1},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  note         = {some note},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  howpublished = {asdf},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  journal      = {International Journal of Something},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"}"
operator|+
name|Globals
operator|.
name|NEWLINE
decl_stmt|;
comment|// @formatter:on
name|assertEquals
argument_list|(
name|expectedNewEntry
argument_list|,
name|actual
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|roundTripWithAppendedNewlines ()
specifier|public
name|void
name|roundTripWithAppendedNewlines
parameter_list|()
throws|throws
name|IOException
block|{
comment|// @formatter:off
name|String
name|bibtexEntry
init|=
literal|"@Article{test,"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  Author                   = {Foo Bar},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  Journal                  = {International Journal of Something},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  Note                     = {some note},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  Number                   = {1}"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"}\n\n"
decl_stmt|;
comment|// @formatter:on
comment|// read in bibtex string
name|ParserResult
name|result
init|=
name|BibtexParser
operator|.
name|parse
argument_list|(
operator|new
name|StringReader
argument_list|(
name|bibtexEntry
argument_list|)
argument_list|)
decl_stmt|;
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|result
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|BibEntry
name|entry
init|=
name|entries
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
comment|//write out bibtex string
name|StringWriter
name|stringWriter
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|entry
argument_list|,
name|stringWriter
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
expr_stmt|;
name|String
name|actual
init|=
name|stringWriter
operator|.
name|toString
argument_list|()
decl_stmt|;
comment|// Only one appending newline is written by the writer, the rest by FileActions. So, these should be removed here.
name|assertEquals
argument_list|(
name|bibtexEntry
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|bibtexEntry
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
argument_list|,
name|actual
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|multipleWritesWithoutModification ()
specifier|public
name|void
name|multipleWritesWithoutModification
parameter_list|()
throws|throws
name|IOException
block|{
comment|// @formatter:off
name|String
name|bibtexEntry
init|=
literal|"@Article{test,"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  Author                   = {Foo Bar},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  Journal                  = {International Journal of Something},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  Note                     = {some note},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  Number                   = {1}"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"}"
decl_stmt|;
comment|// @formatter:on
name|String
name|result
init|=
name|testSingleWrite
argument_list|(
name|bibtexEntry
argument_list|)
decl_stmt|;
name|result
operator|=
name|testSingleWrite
argument_list|(
name|result
argument_list|)
expr_stmt|;
name|result
operator|=
name|testSingleWrite
argument_list|(
name|result
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|bibtexEntry
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
DECL|method|testSingleWrite (String bibtexEntry)
specifier|private
name|String
name|testSingleWrite
parameter_list|(
name|String
name|bibtexEntry
parameter_list|)
throws|throws
name|IOException
block|{
comment|// read in bibtex string
name|ParserResult
name|result
init|=
name|BibtexParser
operator|.
name|parse
argument_list|(
operator|new
name|StringReader
argument_list|(
name|bibtexEntry
argument_list|)
argument_list|)
decl_stmt|;
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|result
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|BibEntry
name|entry
init|=
name|entries
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
comment|//write out bibtex string
name|StringWriter
name|stringWriter
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|entry
argument_list|,
name|stringWriter
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
expr_stmt|;
name|String
name|actual
init|=
name|stringWriter
operator|.
name|toString
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
name|bibtexEntry
argument_list|,
name|actual
argument_list|)
expr_stmt|;
return|return
name|actual
return|;
block|}
annotation|@
name|Test
DECL|method|monthFieldSpecialSyntax ()
specifier|public
name|void
name|monthFieldSpecialSyntax
parameter_list|()
throws|throws
name|IOException
block|{
comment|// @formatter:off
name|String
name|bibtexEntry
init|=
literal|"@Article{test,"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  Author                   = {Foo Bar},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  Month                    = mar,"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  Number                   = {1}"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"}"
decl_stmt|;
comment|// @formatter:on
comment|// read in bibtex string
name|ParserResult
name|result
init|=
name|BibtexParser
operator|.
name|parse
argument_list|(
operator|new
name|StringReader
argument_list|(
name|bibtexEntry
argument_list|)
argument_list|)
decl_stmt|;
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|result
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|BibEntry
name|entry
init|=
name|entries
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
comment|// modify month field
name|Set
argument_list|<
name|String
argument_list|>
name|fields
init|=
name|entry
operator|.
name|getFieldNames
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|fields
operator|.
name|contains
argument_list|(
literal|"month"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"#mar#"
argument_list|,
name|entry
operator|.
name|getFieldOptional
argument_list|(
literal|"month"
argument_list|)
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
comment|//write out bibtex string
name|StringWriter
name|stringWriter
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|entry
argument_list|,
name|stringWriter
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
expr_stmt|;
name|String
name|actual
init|=
name|stringWriter
operator|.
name|toString
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
name|bibtexEntry
argument_list|,
name|actual
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|addFieldWithLongerLength ()
specifier|public
name|void
name|addFieldWithLongerLength
parameter_list|()
throws|throws
name|IOException
block|{
comment|// @formatter:off
name|String
name|bibtexEntry
init|=
name|Globals
operator|.
name|NEWLINE
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"@Article{test,"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  author =  {BlaBla},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  journal = {International Journal of Something},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  number =  {1},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  note =    {some note},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"}"
decl_stmt|;
comment|// @formatter:on
comment|// read in bibtex string
name|ParserResult
name|result
init|=
name|BibtexParser
operator|.
name|parse
argument_list|(
operator|new
name|StringReader
argument_list|(
name|bibtexEntry
argument_list|)
argument_list|)
decl_stmt|;
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|result
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|BibEntry
name|entry
init|=
name|entries
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
comment|// modify entry
name|entry
operator|.
name|setField
argument_list|(
literal|"howpublished"
argument_list|,
literal|"asdf"
argument_list|)
expr_stmt|;
comment|//write out bibtex string
name|StringWriter
name|stringWriter
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|entry
argument_list|,
name|stringWriter
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
expr_stmt|;
name|String
name|actual
init|=
name|stringWriter
operator|.
name|toString
argument_list|()
decl_stmt|;
comment|// @formatter:off
name|String
name|expected
init|=
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"@Article{test,"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  author       = {BlaBla},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  journal      = {International Journal of Something},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  number       = {1},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  note         = {some note},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  howpublished = {asdf},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"}"
operator|+
name|Globals
operator|.
name|NEWLINE
decl_stmt|;
comment|// @formatter:on
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|actual
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|doNotWriteEmptyFields ()
specifier|public
name|void
name|doNotWriteEmptyFields
parameter_list|()
throws|throws
name|IOException
block|{
name|StringWriter
name|stringWriter
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|(
literal|"1234"
argument_list|,
literal|"article"
argument_list|)
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"  "
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"note"
argument_list|,
literal|"some note"
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|entry
argument_list|,
name|stringWriter
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
expr_stmt|;
name|String
name|actual
init|=
name|stringWriter
operator|.
name|toString
argument_list|()
decl_stmt|;
name|String
name|expected
init|=
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"@Article{,"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  note   = {some note},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"}"
operator|+
name|Globals
operator|.
name|NEWLINE
decl_stmt|;
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|actual
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|trimFieldContents ()
specifier|public
name|void
name|trimFieldContents
parameter_list|()
throws|throws
name|IOException
block|{
name|StringWriter
name|stringWriter
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|(
literal|"1234"
argument_list|,
literal|"article"
argument_list|)
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"note"
argument_list|,
literal|"        some note    \t"
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|entry
argument_list|,
name|stringWriter
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
expr_stmt|;
name|String
name|actual
init|=
name|stringWriter
operator|.
name|toString
argument_list|()
decl_stmt|;
name|String
name|expected
init|=
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"@Article{,"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  note = {some note},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"}"
operator|+
name|Globals
operator|.
name|NEWLINE
decl_stmt|;
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|actual
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|roundTripWithPrecedingCommentTest ()
specifier|public
name|void
name|roundTripWithPrecedingCommentTest
parameter_list|()
throws|throws
name|IOException
block|{
comment|// @formatter:off
name|String
name|bibtexEntry
init|=
literal|"% Some random comment that should stay here"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"@Article{test,"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  Author                   = {Foo Bar},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  Journal                  = {International Journal of Something},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  Note                     = {some note},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  Number                   = {1}"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"}"
decl_stmt|;
comment|// @formatter:on
comment|// read in bibtex string
name|ParserResult
name|result
init|=
name|BibtexParser
operator|.
name|parse
argument_list|(
operator|new
name|StringReader
argument_list|(
name|bibtexEntry
argument_list|)
argument_list|)
decl_stmt|;
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|result
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|BibEntry
name|entry
init|=
name|entries
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
comment|//write out bibtex string
name|StringWriter
name|stringWriter
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|entry
argument_list|,
name|stringWriter
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
expr_stmt|;
name|String
name|actual
init|=
name|stringWriter
operator|.
name|toString
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
name|bibtexEntry
argument_list|,
name|actual
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|roundTripWithPrecedingCommentAndModificationTest ()
specifier|public
name|void
name|roundTripWithPrecedingCommentAndModificationTest
parameter_list|()
throws|throws
name|IOException
block|{
comment|// @formatter:off
name|String
name|bibtexEntry
init|=
literal|"% Some random comment that should stay here"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"@Article{test,"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  Author                   = {Foo Bar},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  Journal                  = {International Journal of Something},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  Note                     = {some note},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  Number                   = {1}"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"}"
decl_stmt|;
comment|// @formatter:on
comment|// read in bibtex string
name|ParserResult
name|result
init|=
name|BibtexParser
operator|.
name|parse
argument_list|(
operator|new
name|StringReader
argument_list|(
name|bibtexEntry
argument_list|)
argument_list|)
decl_stmt|;
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|result
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|BibEntry
name|entry
init|=
name|entries
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
comment|// change the entry
name|entry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"John Doe"
argument_list|)
expr_stmt|;
comment|//write out bibtex string
name|StringWriter
name|stringWriter
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|entry
argument_list|,
name|stringWriter
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
expr_stmt|;
name|String
name|actual
init|=
name|stringWriter
operator|.
name|toString
argument_list|()
decl_stmt|;
comment|// @formatter:off
name|String
name|expected
init|=
literal|"% Some random comment that should stay here"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"@Article{test,"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  author  = {John Doe},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  journal = {International Journal of Something},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  number  = {1},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"  note    = {some note},"
operator|+
name|Globals
operator|.
name|NEWLINE
operator|+
literal|"}"
operator|+
name|Globals
operator|.
name|NEWLINE
decl_stmt|;
comment|// @formatter:on
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|actual
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

