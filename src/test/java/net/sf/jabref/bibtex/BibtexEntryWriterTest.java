begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.bibtex
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|bibtex
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
name|exporter
operator|.
name|LatexFieldFormatter
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
name|entry
operator|.
name|BibtexEntry
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
DECL|class|BibtexEntryWriterTest
specifier|public
class|class
name|BibtexEntryWriterTest
block|{
DECL|field|writer
specifier|private
name|BibtexEntryWriter
name|writer
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
comment|// make sure that we use the "new style" serialization
name|Globals
operator|.
name|prefs
operator|.
name|putInt
argument_list|(
name|JabRefPreferences
operator|.
name|WRITEFIELD_SORTSTYLE
argument_list|,
literal|0
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
name|BibtexEntryWriter
argument_list|(
operator|new
name|LatexFieldFormatter
argument_list|()
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
name|BibtexEntry
name|entry
init|=
operator|new
name|BibtexEntry
argument_list|(
literal|"1234"
argument_list|,
name|EntryTypes
operator|.
name|getType
argument_list|(
literal|"Article"
argument_list|)
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
literal|"@Article{,"
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
DECL|method|roundTripTest ()
specifier|public
name|void
name|roundTripTest
parameter_list|()
throws|throws
name|IOException
block|{
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
name|BibtexEntry
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
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|entries
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|BibtexEntry
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
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"test"
argument_list|,
name|entry
operator|.
name|getCiteKey
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|5
argument_list|,
name|entry
operator|.
name|getFieldNames
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
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
name|Assert
operator|.
name|assertTrue
argument_list|(
name|fields
operator|.
name|contains
argument_list|(
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Foo Bar"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"author"
argument_list|)
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
name|BibtexEntry
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
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|entries
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|BibtexEntry
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
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"test"
argument_list|,
name|entry
operator|.
name|getCiteKey
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|5
argument_list|,
name|entry
operator|.
name|getFieldNames
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
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
name|Assert
operator|.
name|assertTrue
argument_list|(
name|fields
operator|.
name|contains
argument_list|(
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Foo Bar"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"author"
argument_list|)
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
DECL|method|roundTripWithAppendedNewlines ()
specifier|public
name|void
name|roundTripWithAppendedNewlines
parameter_list|()
throws|throws
name|IOException
block|{
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
name|BibtexEntry
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
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|entries
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|BibtexEntry
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
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"test"
argument_list|,
name|entry
operator|.
name|getCiteKey
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|5
argument_list|,
name|entry
operator|.
name|getFieldNames
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
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
name|Assert
operator|.
name|assertTrue
argument_list|(
name|fields
operator|.
name|contains
argument_list|(
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Foo Bar"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"author"
argument_list|)
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
DECL|method|multipleWritesWithoutModification ()
specifier|public
name|void
name|multipleWritesWithoutModification
parameter_list|()
throws|throws
name|IOException
block|{
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
name|BibtexEntry
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
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|entries
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|BibtexEntry
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
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"test"
argument_list|,
name|entry
operator|.
name|getCiteKey
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|5
argument_list|,
name|entry
operator|.
name|getFieldNames
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
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
name|Assert
operator|.
name|assertTrue
argument_list|(
name|fields
operator|.
name|contains
argument_list|(
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Foo Bar"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"author"
argument_list|)
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
name|BibtexEntry
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
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|entries
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|BibtexEntry
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
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"test"
argument_list|,
name|entry
operator|.
name|getCiteKey
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|4
argument_list|,
name|entry
operator|.
name|getFieldNames
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
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
name|Assert
operator|.
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
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"#mar#"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"month"
argument_list|)
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
block|}
end_class

end_unit

