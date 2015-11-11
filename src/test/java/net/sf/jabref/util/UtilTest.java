begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.util
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
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
name|model
operator|.
name|database
operator|.
name|BibtexDatabase
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
name|gui
operator|.
name|preftabs
operator|.
name|NameFormatterTab
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
name|logic
operator|.
name|util
operator|.
name|strings
operator|.
name|StringUtil
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
name|Util
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
name|Ignore
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
name|StringReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|*
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

begin_class
DECL|class|UtilTest
specifier|public
class|class
name|UtilTest
block|{
annotation|@
name|Test
DECL|method|testNCase ()
specifier|public
name|void
name|testNCase
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|Util
operator|.
name|capitalizeFirst
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Hello world"
argument_list|,
name|Util
operator|.
name|capitalizeFirst
argument_list|(
literal|"Hello World"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"A"
argument_list|,
name|Util
operator|.
name|capitalizeFirst
argument_list|(
literal|"a"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Aa"
argument_list|,
name|Util
operator|.
name|capitalizeFirst
argument_list|(
literal|"AA"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetPublicationDate ()
specifier|public
name|void
name|testGetPublicationDate
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"2003-02"
argument_list|,
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|getPublicationDate
argument_list|(
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{HipKro03, year = {2003}, month = #FEB# }"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"2003-03"
argument_list|,
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|getPublicationDate
argument_list|(
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{HipKro03, year = {2003}, month = 3 }"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"2003"
argument_list|,
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|getPublicationDate
argument_list|(
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{HipKro03, year = {2003}}"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|null
argument_list|,
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|getPublicationDate
argument_list|(
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{HipKro03, month = 3 }"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|null
argument_list|,
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|getPublicationDate
argument_list|(
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{HipKro03, author={bla}}"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"2003-12"
argument_list|,
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|getPublicationDate
argument_list|(
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{HipKro03, year = {03}, month = #DEC# }"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testShaveString ()
specifier|public
name|void
name|testShaveString
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|null
argument_list|,
name|StringUtil
operator|.
name|shaveString
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|StringUtil
operator|.
name|shaveString
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"aaa"
argument_list|,
name|StringUtil
operator|.
name|shaveString
argument_list|(
literal|"   aaa\t\t\n\r"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"a"
argument_list|,
name|StringUtil
operator|.
name|shaveString
argument_list|(
literal|"  {a}    "
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"a"
argument_list|,
name|StringUtil
operator|.
name|shaveString
argument_list|(
literal|"  \"a\"    "
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"{a}"
argument_list|,
name|StringUtil
operator|.
name|shaveString
argument_list|(
literal|"  {{a}}    "
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"{a}"
argument_list|,
name|StringUtil
operator|.
name|shaveString
argument_list|(
literal|"  \"{a}\"    "
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"\"{a\"}"
argument_list|,
name|StringUtil
operator|.
name|shaveString
argument_list|(
literal|"  \"{a\"}    "
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testCheckLegalKey ()
specifier|public
name|void
name|testCheckLegalKey
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"AAAA"
argument_list|,
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|checkLegalKey
argument_list|(
literal|"AA AA"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"SPECIALCHARS"
argument_list|,
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|checkLegalKey
argument_list|(
literal|"SPECIAL CHARS#{\\\"}~,^"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|checkLegalKey
argument_list|(
literal|"\n\t\r"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
annotation|@
name|Ignore
DECL|method|testReplaceSpecialCharacters ()
specifier|public
name|void
name|testReplaceSpecialCharacters
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Hallo Arger"
argument_list|,
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|replaceSpecialCharacters
argument_list|(
literal|"Hallo Arger"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Shouldn't German Ã¯Â¿Â½ be resolved to Ae
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"AeaeaAAA"
argument_list|,
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|replaceSpecialCharacters
argument_list|(
literal|"Ã¯Â¿Â½Ã¯Â¿Â½Ã¯Â¿Â½Ã¯Â¿Â½Ã¯Â¿Â½Ã¯Â¿Â½"
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|field|database
name|BibtexDatabase
name|database
decl_stmt|;
DECL|field|entry
name|BibtexEntry
name|entry
decl_stmt|;
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
comment|// Required by BibtexParser -> FieldContentParser
name|Globals
operator|.
name|prefs
operator|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
expr_stmt|;
name|StringReader
name|reader
init|=
operator|new
name|StringReader
argument_list|(
literal|"@ARTICLE{HipKro03,"
operator|+
literal|"\n"
operator|+
literal|"  author = {Eric von Hippel and Georg von Krogh},"
operator|+
literal|"\n"
operator|+
literal|"  title = {Open Source Software and the \"Private-Collective\" Innovation Model: Issues for Organization Science},"
operator|+
literal|"\n"
operator|+
literal|"  journal = {Organization Science},"
operator|+
literal|"\n"
operator|+
literal|"  year = {2003},"
operator|+
literal|"\n"
operator|+
literal|"  volume = {14},"
operator|+
literal|"\n"
operator|+
literal|"  pages = {209--223},"
operator|+
literal|"\n"
operator|+
literal|"  number = {2},"
operator|+
literal|"\n"
operator|+
literal|"  address = {Institute for Operations Research and the Management Sciences (INFORMS), Linthicum, Maryland, USA},"
operator|+
literal|"\n"
operator|+
literal|"  doi = {http://dx.doi.org/10.1287/orsc.14.2.209.14992},"
operator|+
literal|"\n"
operator|+
literal|"  issn = {1526-5455},"
operator|+
literal|"\n"
operator|+
literal|"  publisher = {INFORMS}"
operator|+
literal|"\n"
operator|+
literal|"}"
argument_list|)
decl_stmt|;
name|BibtexParser
name|parser
init|=
operator|new
name|BibtexParser
argument_list|(
name|reader
argument_list|)
decl_stmt|;
name|ParserResult
name|result
init|=
literal|null
decl_stmt|;
try|try
block|{
name|result
operator|=
name|parser
operator|.
name|parse
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|Assert
operator|.
name|fail
argument_list|()
expr_stmt|;
block|}
name|database
operator|=
name|result
operator|.
name|getDatabase
argument_list|()
expr_stmt|;
name|entry
operator|=
name|database
operator|.
name|getEntriesByKey
argument_list|(
literal|"HipKro03"
argument_list|)
index|[
literal|0
index|]
expr_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|database
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testParseMethodCalls ()
specifier|public
name|void
name|testParseMethodCalls
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|parseMethodsCalls
argument_list|(
literal|"bla"
argument_list|)
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"bla"
argument_list|,
operator|(
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|parseMethodsCalls
argument_list|(
literal|"bla"
argument_list|)
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|)
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|parseMethodsCalls
argument_list|(
literal|"bla,"
argument_list|)
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"bla"
argument_list|,
operator|(
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|parseMethodsCalls
argument_list|(
literal|"bla,"
argument_list|)
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|)
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|parseMethodsCalls
argument_list|(
literal|"_bla.bla.blub,"
argument_list|)
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"_bla.bla.blub"
argument_list|,
operator|(
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|parseMethodsCalls
argument_list|(
literal|"_bla.bla.blub,"
argument_list|)
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|)
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|parseMethodsCalls
argument_list|(
literal|"bla,foo"
argument_list|)
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"bla"
argument_list|,
operator|(
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|parseMethodsCalls
argument_list|(
literal|"bla,foo"
argument_list|)
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|)
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"foo"
argument_list|,
operator|(
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|parseMethodsCalls
argument_list|(
literal|"bla,foo"
argument_list|)
operator|.
name|get
argument_list|(
literal|1
argument_list|)
operator|)
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|parseMethodsCalls
argument_list|(
literal|"bla(\"test\"),foo(\"fark\")"
argument_list|)
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"bla"
argument_list|,
operator|(
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|parseMethodsCalls
argument_list|(
literal|"bla(\"test\"),foo(\"fark\")"
argument_list|)
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|)
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"foo"
argument_list|,
operator|(
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|parseMethodsCalls
argument_list|(
literal|"bla(\"test\"),foo(\"fark\")"
argument_list|)
operator|.
name|get
argument_list|(
literal|1
argument_list|)
operator|)
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"test"
argument_list|,
operator|(
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|parseMethodsCalls
argument_list|(
literal|"bla(\"test\"),foo(\"fark\")"
argument_list|)
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|)
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"fark"
argument_list|,
operator|(
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|parseMethodsCalls
argument_list|(
literal|"bla(\"test\"),foo(\"fark\")"
argument_list|)
operator|.
name|get
argument_list|(
literal|1
argument_list|)
operator|)
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|parseMethodsCalls
argument_list|(
literal|"bla(test),foo(fark)"
argument_list|)
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"bla"
argument_list|,
operator|(
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|parseMethodsCalls
argument_list|(
literal|"bla(test),foo(fark)"
argument_list|)
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|)
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"foo"
argument_list|,
operator|(
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|parseMethodsCalls
argument_list|(
literal|"bla(test),foo(fark)"
argument_list|)
operator|.
name|get
argument_list|(
literal|1
argument_list|)
operator|)
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"test"
argument_list|,
operator|(
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|parseMethodsCalls
argument_list|(
literal|"bla(test),foo(fark)"
argument_list|)
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|)
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"fark"
argument_list|,
operator|(
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|parseMethodsCalls
argument_list|(
literal|"bla(test),foo(fark)"
argument_list|)
operator|.
name|get
argument_list|(
literal|1
argument_list|)
operator|)
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
annotation|@
name|Ignore
DECL|method|testFieldAndFormat ()
specifier|public
name|void
name|testFieldAndFormat
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Eric von Hippel and Georg von Krogh"
argument_list|,
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|getFieldAndFormat
argument_list|(
literal|"[author]"
argument_list|,
name|entry
argument_list|,
name|database
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Eric von Hippel and Georg von Krogh"
argument_list|,
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|getFieldAndFormat
argument_list|(
literal|"author"
argument_list|,
name|entry
argument_list|,
name|database
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|null
argument_list|,
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|getFieldAndFormat
argument_list|(
literal|"[unknownkey]"
argument_list|,
name|entry
argument_list|,
name|database
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|null
argument_list|,
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|getFieldAndFormat
argument_list|(
literal|"[:]"
argument_list|,
name|entry
argument_list|,
name|database
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|null
argument_list|,
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|getFieldAndFormat
argument_list|(
literal|"[:lower]"
argument_list|,
name|entry
argument_list|,
name|database
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"eric von hippel and georg von krogh"
argument_list|,
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|getFieldAndFormat
argument_list|(
literal|"[author:lower]"
argument_list|,
name|entry
argument_list|,
name|database
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"HipKro03"
argument_list|,
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|getFieldAndFormat
argument_list|(
literal|"[bibtexkey]"
argument_list|,
name|entry
argument_list|,
name|database
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"HipKro03"
argument_list|,
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|getFieldAndFormat
argument_list|(
literal|"[bibtexkey:]"
argument_list|,
name|entry
argument_list|,
name|database
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
annotation|@
name|Ignore
DECL|method|testUserFieldAndFormat ()
specifier|public
name|void
name|testUserFieldAndFormat
parameter_list|()
block|{
name|String
index|[]
name|names
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getStringArray
argument_list|(
name|NameFormatterTab
operator|.
name|NAME_FORMATER_KEY
argument_list|)
decl_stmt|;
if|if
condition|(
name|names
operator|==
literal|null
condition|)
block|{
name|names
operator|=
operator|new
name|String
index|[]
block|{}
expr_stmt|;
block|}
name|String
index|[]
name|formats
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getStringArray
argument_list|(
name|NameFormatterTab
operator|.
name|NAME_FORMATTER_VALUE
argument_list|)
decl_stmt|;
if|if
condition|(
name|formats
operator|==
literal|null
condition|)
block|{
name|formats
operator|=
operator|new
name|String
index|[]
block|{}
expr_stmt|;
block|}
try|try
block|{
name|List
argument_list|<
name|String
argument_list|>
name|f
init|=
operator|new
name|LinkedList
argument_list|<>
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|formats
argument_list|)
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|n
init|=
operator|new
name|LinkedList
argument_list|<>
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|names
argument_list|)
argument_list|)
decl_stmt|;
name|n
operator|.
name|add
argument_list|(
literal|"testMe123454321"
argument_list|)
expr_stmt|;
name|f
operator|.
name|add
argument_list|(
literal|"*@*@test"
argument_list|)
expr_stmt|;
name|String
index|[]
name|newNames
init|=
name|n
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|n
operator|.
name|size
argument_list|()
index|]
argument_list|)
decl_stmt|;
name|String
index|[]
name|newFormats
init|=
name|f
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|f
operator|.
name|size
argument_list|()
index|]
argument_list|)
decl_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putStringArray
argument_list|(
name|NameFormatterTab
operator|.
name|NAME_FORMATER_KEY
argument_list|,
name|newNames
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putStringArray
argument_list|(
name|NameFormatterTab
operator|.
name|NAME_FORMATTER_VALUE
argument_list|,
name|newFormats
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"testtest"
argument_list|,
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|getFieldAndFormat
argument_list|(
literal|"[author:testMe123454321]"
argument_list|,
name|entry
argument_list|,
name|database
argument_list|)
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|Globals
operator|.
name|prefs
operator|.
name|putStringArray
argument_list|(
name|NameFormatterTab
operator|.
name|NAME_FORMATER_KEY
argument_list|,
name|names
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putStringArray
argument_list|(
name|NameFormatterTab
operator|.
name|NAME_FORMATTER_VALUE
argument_list|,
name|formats
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|testExpandBrackets ()
specifier|public
name|void
name|testExpandBrackets
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|expandBrackets
argument_list|(
literal|""
argument_list|,
name|entry
argument_list|,
name|database
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"dropped"
argument_list|,
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|expandBrackets
argument_list|(
literal|"drop[unknownkey]ped"
argument_list|,
name|entry
argument_list|,
name|database
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Eric von Hippel and Georg von Krogh"
argument_list|,
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|expandBrackets
argument_list|(
literal|"[author]"
argument_list|,
name|entry
argument_list|,
name|database
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Eric von Hippel and Georg von Krogh are two famous authors."
argument_list|,
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|expandBrackets
argument_list|(
literal|"[author] are two famous authors."
argument_list|,
name|entry
argument_list|,
name|database
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Eric von Hippel and Georg von Krogh are two famous authors."
argument_list|,
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|expandBrackets
argument_list|(
literal|"[author] are two famous authors."
argument_list|,
name|entry
argument_list|,
name|database
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Eric von Hippel and Georg von Krogh have published Open Source Software and the \"Private-Collective\" Innovation Model: Issues for Organization Science in Organization Science."
argument_list|,
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|expandBrackets
argument_list|(
literal|"[author] have published [title] in [journal]."
argument_list|,
name|entry
argument_list|,
name|database
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getSeparatedKeywords ()
specifier|public
name|void
name|getSeparatedKeywords
parameter_list|()
block|{
name|String
name|keywords
init|=
literal|"w1, w2a w2b, w3"
decl_stmt|;
name|ArrayList
argument_list|<
name|String
argument_list|>
name|separatedKeywords
init|=
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
operator|.
name|getSeparatedKeywords
argument_list|(
name|keywords
argument_list|)
decl_stmt|;
name|String
index|[]
name|expected
init|=
operator|new
name|String
index|[]
block|{
literal|"w1"
block|,
literal|"w2a w2b"
block|,
literal|"w3"
block|}
decl_stmt|;
name|Assert
operator|.
name|assertArrayEquals
argument_list|(
name|expected
argument_list|,
name|separatedKeywords
operator|.
name|toArray
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

