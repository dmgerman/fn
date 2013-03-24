begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|tests.net.sf.jabref.imports
package|package
name|tests
operator|.
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
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
name|Iterator
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
name|junit
operator|.
name|framework
operator|.
name|TestCase
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
name|BibtexEntryType
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
name|imports
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
name|imports
operator|.
name|ParserResult
import|;
end_import

begin_comment
comment|/**  * Test the BibtexParser  *   * @version $revision: 1.1$ $date: $  *   * @author Christopher Oezbek<oezi@oezi.de>  */
end_comment

begin_class
DECL|class|BibtexParserTest
specifier|public
class|class
name|BibtexParserTest
extends|extends
name|TestCase
block|{
DECL|method|testParseReader ()
specifier|public
name|void
name|testParseReader
parameter_list|()
throws|throws
name|IOException
block|{
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
literal|"@article{test,author={Ed von Test}}"
argument_list|)
argument_list|)
decl_stmt|;
name|Collection
argument_list|<
name|BibtexEntry
argument_list|>
name|c
init|=
name|result
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|c
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|BibtexEntry
name|e
init|=
name|c
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|"test"
argument_list|,
name|e
operator|.
name|getCiteKey
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|e
operator|.
name|getAllFields
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
name|o
init|=
name|e
operator|.
name|getAllFields
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|o
operator|.
name|contains
argument_list|(
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Ed von Test"
argument_list|,
name|e
operator|.
name|getField
argument_list|(
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|testBibtexParser ()
specifier|public
name|void
name|testBibtexParser
parameter_list|()
block|{
try|try
block|{
operator|new
name|BibtexParser
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|fail
argument_list|(
literal|"Should not accept null."
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NullPointerException
name|npe
parameter_list|)
block|{  		}
block|}
DECL|method|testIsRecognizedFormat ()
specifier|public
name|void
name|testIsRecognizedFormat
parameter_list|()
throws|throws
name|IOException
block|{
name|assertTrue
argument_list|(
name|BibtexParser
operator|.
name|isRecognizedFormat
argument_list|(
operator|new
name|StringReader
argument_list|(
literal|"This file was created with JabRef 2.1 beta 2."
operator|+
literal|"\n"
operator|+
literal|"Encoding: Cp1252"
operator|+
literal|"\n"
operator|+
literal|""
operator|+
literal|"\n"
operator|+
literal|"@INPROCEEDINGS{CroAnnHow05,"
operator|+
literal|"\n"
operator|+
literal|"  author = {Crowston, K. and Annabi, H. and Howison, J. and Masango, C.},"
operator|+
literal|"\n"
operator|+
literal|"  title = {Effective work practices for floss development: A model and propositions},"
operator|+
literal|"\n"
operator|+
literal|"  booktitle = {Hawaii International Conference On System Sciences (HICSS)},"
operator|+
literal|"\n"
operator|+
literal|"  year = {2005},"
operator|+
literal|"\n"
operator|+
literal|"  owner = {oezbek},"
operator|+
literal|"\n"
operator|+
literal|"  timestamp = {2006.05.29},"
operator|+
literal|"\n"
operator|+
literal|"  url = {http://james.howison.name/publications.html}"
operator|+
literal|"\n"
operator|+
literal|"}))"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|BibtexParser
operator|.
name|isRecognizedFormat
argument_list|(
operator|new
name|StringReader
argument_list|(
literal|"This file was created with JabRef 2.1 beta 2."
operator|+
literal|"\n"
operator|+
literal|"Encoding: Cp1252"
operator|+
literal|"\n"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|BibtexParser
operator|.
name|isRecognizedFormat
argument_list|(
operator|new
name|StringReader
argument_list|(
literal|"@INPROCEEDINGS{CroAnnHow05,"
operator|+
literal|"\n"
operator|+
literal|"  author = {Crowston, K. and Annabi, H. and Howison, J. and Masango, C.},"
operator|+
literal|"\n"
operator|+
literal|"  title = {Effective work practices for floss development: A model and propositions},"
operator|+
literal|"\n"
operator|+
literal|"  booktitle = {Hawaii International Conference On System Sciences (HICSS)},"
operator|+
literal|"\n"
operator|+
literal|"  year = {2005},"
operator|+
literal|"\n"
operator|+
literal|"  owner = {oezbek},"
operator|+
literal|"\n"
operator|+
literal|"  timestamp = {2006.05.29},"
operator|+
literal|"\n"
operator|+
literal|"  url = {http://james.howison.name/publications.html}"
operator|+
literal|"\n"
operator|+
literal|"}))"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|BibtexParser
operator|.
name|isRecognizedFormat
argument_list|(
operator|new
name|StringReader
argument_list|(
literal|"  author = {Crowston, K. and Annabi, H. and Howison, J. and Masango, C.},"
operator|+
literal|"\n"
operator|+
literal|"  title = {Effective work practices for floss development: A model and propositions},"
operator|+
literal|"\n"
operator|+
literal|"  booktitle = {Hawaii International Conference On System Sciences (HICSS)},"
operator|+
literal|"\n"
operator|+
literal|"  year = {2005},"
operator|+
literal|"\n"
operator|+
literal|"  owner = {oezbek},"
operator|+
literal|"\n"
operator|+
literal|"  timestamp = {2006.05.29},"
operator|+
literal|"\n"
operator|+
literal|"  url = {http://james.howison.name/publications.html}"
operator|+
literal|"\n"
operator|+
literal|"}))"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|BibtexParser
operator|.
name|isRecognizedFormat
argument_list|(
operator|new
name|StringReader
argument_list|(
literal|"This was created with JabRef 2.1 beta 2."
operator|+
literal|"\n"
operator|+
literal|"Encoding: Cp1252"
operator|+
literal|"\n"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|testFromString ()
specifier|public
name|void
name|testFromString
parameter_list|()
throws|throws
name|Exception
block|{
block|{
comment|// Simple case
name|Collection
argument_list|<
name|BibtexEntry
argument_list|>
name|c
init|=
name|BibtexParser
operator|.
name|fromString
argument_list|(
literal|"@article{test,author={Ed von Test}}"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|c
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|BibtexEntry
name|e
init|=
name|c
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|"test"
argument_list|,
name|e
operator|.
name|getCiteKey
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|e
operator|.
name|getAllFields
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|e
operator|.
name|getAllFields
argument_list|()
operator|.
name|contains
argument_list|(
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Ed von Test"
argument_list|,
name|e
operator|.
name|getField
argument_list|(
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|{
comment|// Empty String
name|Collection
argument_list|<
name|BibtexEntry
argument_list|>
name|c
init|=
name|BibtexParser
operator|.
name|fromString
argument_list|(
literal|""
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|0
argument_list|,
name|c
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|{
comment|// Error
name|Collection
argument_list|<
name|BibtexEntry
argument_list|>
name|c
init|=
name|BibtexParser
operator|.
name|fromString
argument_list|(
literal|"@@article@@{{{{{{}"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|null
argument_list|,
name|c
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|testFromSingle2 ()
specifier|public
name|void
name|testFromSingle2
parameter_list|()
block|{
comment|/** 		 * More 		 */
name|Collection
argument_list|<
name|BibtexEntry
argument_list|>
name|c
init|=
name|BibtexParser
operator|.
name|fromString
argument_list|(
literal|"@article{canh05,"
operator|+
literal|"  author = {Crowston, K. and Annabi, H.},\n"
operator|+
literal|"  title = {Title A}}\n"
operator|+
literal|"@inProceedings{foo,"
operator|+
literal|"  author={Norton Bar}}"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|c
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|Iterator
argument_list|<
name|BibtexEntry
argument_list|>
name|i
init|=
name|c
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|BibtexEntry
name|a
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|BibtexEntry
name|b
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|a
operator|.
name|getCiteKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"foo"
argument_list|)
condition|)
block|{
name|BibtexEntry
name|tmp
init|=
name|a
decl_stmt|;
name|a
operator|=
name|b
expr_stmt|;
name|b
operator|=
name|tmp
expr_stmt|;
block|}
name|assertEquals
argument_list|(
literal|"canh05"
argument_list|,
name|a
operator|.
name|getCiteKey
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Crowston, K. and Annabi, H."
argument_list|,
name|a
operator|.
name|getField
argument_list|(
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Title A"
argument_list|,
name|a
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|BibtexEntryType
operator|.
name|ARTICLE
argument_list|,
name|a
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"foo"
argument_list|,
name|b
operator|.
name|getCiteKey
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Norton Bar"
argument_list|,
name|b
operator|.
name|getField
argument_list|(
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|BibtexEntryType
operator|.
name|INPROCEEDINGS
argument_list|,
name|b
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|testFromStringSingle ()
specifier|public
name|void
name|testFromStringSingle
parameter_list|()
block|{
name|BibtexEntry
name|a
init|=
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@article{canh05,"
operator|+
literal|"  author = {Crowston, K. and Annabi, H.},\n"
operator|+
literal|"  title = {Title A}}\n"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"canh05"
argument_list|,
name|a
operator|.
name|getCiteKey
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Crowston, K. and Annabi, H."
argument_list|,
name|a
operator|.
name|getField
argument_list|(
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Title A"
argument_list|,
name|a
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|BibtexEntryType
operator|.
name|ARTICLE
argument_list|,
name|a
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|BibtexEntry
name|b
init|=
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@article{canh05,"
operator|+
literal|"  author = {Crowston, K. and Annabi, H.},\n"
operator|+
literal|"  title = {Title A}}\n"
operator|+
literal|"@inProceedings{foo,"
operator|+
literal|"  author={Norton Bar}}"
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
operator|(
name|b
operator|.
name|getCiteKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"canh05"
argument_list|)
operator|||
name|b
operator|.
name|getCiteKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"foo"
argument_list|)
operator|)
condition|)
block|{
name|fail
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|testParse ()
specifier|public
name|void
name|testParse
parameter_list|()
throws|throws
name|IOException
block|{
comment|// Test Standard parsing
name|BibtexParser
name|parser
init|=
operator|new
name|BibtexParser
argument_list|(
operator|new
name|StringReader
argument_list|(
literal|"@article{test,author={Ed von Test}}"
argument_list|)
argument_list|)
decl_stmt|;
name|ParserResult
name|result
init|=
name|parser
operator|.
name|parse
argument_list|()
decl_stmt|;
name|Collection
argument_list|<
name|BibtexEntry
argument_list|>
name|c
init|=
name|result
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|c
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|BibtexEntry
name|e
init|=
name|c
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|"test"
argument_list|,
name|e
operator|.
name|getCiteKey
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|e
operator|.
name|getAllFields
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|e
operator|.
name|getAllFields
argument_list|()
operator|.
name|contains
argument_list|(
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Ed von Test"
argument_list|,
name|e
operator|.
name|getField
argument_list|(
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Calling parse again will return the same result
name|assertEquals
argument_list|(
name|result
argument_list|,
name|parser
operator|.
name|parse
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|testParse2 ()
specifier|public
name|void
name|testParse2
parameter_list|()
throws|throws
name|IOException
block|{
name|BibtexParser
name|parser
init|=
operator|new
name|BibtexParser
argument_list|(
operator|new
name|StringReader
argument_list|(
literal|"@article{test,author={Ed von Test}}"
argument_list|)
argument_list|)
decl_stmt|;
name|ParserResult
name|result
init|=
name|parser
operator|.
name|parse
argument_list|()
decl_stmt|;
name|BibtexEntry
name|e
init|=
operator|new
name|BibtexEntry
argument_list|(
literal|""
argument_list|,
name|BibtexEntryType
operator|.
name|ARTICLE
argument_list|)
decl_stmt|;
name|e
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"Ed von Test"
argument_list|)
expr_stmt|;
name|e
operator|.
name|setField
argument_list|(
literal|"bibtexkey"
argument_list|,
literal|"test"
argument_list|)
expr_stmt|;
name|Collection
argument_list|<
name|BibtexEntry
argument_list|>
name|c
init|=
name|result
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|c
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|BibtexEntry
name|e2
init|=
name|c
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
name|assertNotSame
argument_list|(
name|e
operator|.
name|getId
argument_list|()
argument_list|,
name|e2
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|field
range|:
name|e
operator|.
name|getAllFields
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|e
operator|.
name|getField
argument_list|(
name|field
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|equals
argument_list|(
name|e2
operator|.
name|getField
argument_list|(
name|field
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
condition|)
block|{
name|fail
argument_list|(
literal|"e and e2 differ in field "
operator|+
name|field
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/** 	 * Test for [ 1594123 ] Failure to import big numbers 	 *  	 * Issue Reported by Ulf Martin. 	 *  	 * @throws IOException 	 */
DECL|method|testBigNumbers ()
specifier|public
name|void
name|testBigNumbers
parameter_list|()
throws|throws
name|IOException
block|{
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
literal|"@article{canh05,"
operator|+
literal|"isbn = 1234567890123456789,\n"
operator|+
literal|"isbn2 = {1234567890123456789},\n"
operator|+
literal|"small = 1234,\n"
operator|+
literal|"}"
argument_list|)
argument_list|)
decl_stmt|;
name|Collection
argument_list|<
name|BibtexEntry
argument_list|>
name|c
init|=
name|result
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|BibtexEntry
name|e
init|=
name|c
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|"1234567890123456789"
argument_list|,
name|e
operator|.
name|getField
argument_list|(
literal|"isbn"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"1234567890123456789"
argument_list|,
name|e
operator|.
name|getField
argument_list|(
literal|"isbn2"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"1234"
argument_list|,
name|e
operator|.
name|getField
argument_list|(
literal|"small"
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|testBigNumbers2 ()
specifier|public
name|void
name|testBigNumbers2
parameter_list|()
throws|throws
name|IOException
block|{
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
literal|""
operator|+
literal|"@string{bourdieu = {Bourdieu, Pierre}}"
operator|+
literal|"@book{bourdieu-2002-questions-sociologie, "
operator|+
literal|"	Address = {Paris},"
operator|+
literal|"	Author = bourdieu,"
operator|+
literal|"	Isbn = 2707318256,"
operator|+
literal|"	Publisher = {Minuit},"
operator|+
literal|"	Title = {Questions de sociologie},"
operator|+
literal|"	Year = 2002"
operator|+
literal|"}"
argument_list|)
argument_list|)
decl_stmt|;
name|Collection
argument_list|<
name|BibtexEntry
argument_list|>
name|c
init|=
name|result
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|c
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|BibtexEntry
name|e
init|=
name|c
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|"bourdieu-2002-questions-sociologie"
argument_list|,
name|e
operator|.
name|getCiteKey
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|BibtexEntryType
operator|.
name|BOOK
argument_list|,
name|e
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"2707318256"
argument_list|,
name|e
operator|.
name|getField
argument_list|(
literal|"isbn"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Paris"
argument_list|,
name|e
operator|.
name|getField
argument_list|(
literal|"address"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Minuit"
argument_list|,
name|e
operator|.
name|getField
argument_list|(
literal|"publisher"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Questions de sociologie"
argument_list|,
name|e
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"#bourdieu#"
argument_list|,
name|e
operator|.
name|getField
argument_list|(
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"2002"
argument_list|,
name|e
operator|.
name|getField
argument_list|(
literal|"year"
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|testNewlineHandling ()
specifier|public
name|void
name|testNewlineHandling
parameter_list|()
throws|throws
name|IOException
block|{
name|BibtexEntry
name|e
init|=
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@article{canh05,"
operator|+
literal|"a = {a\nb},"
operator|+
literal|"b = {a\n\nb},"
operator|+
literal|"c = {a\n \nb},"
operator|+
literal|"d = {a \n \n b},"
operator|+
literal|"title = {\nHallo \nWorld \nthis \n is\n\nnot \n\nan \n\n exercise \n \n.\n \n\n},\n"
operator|+
literal|"tabs = {\nHallo \tWorld \tthis \t is\t\tnot \t\tan \t\n exercise \t \n.\t \n\t},\n"
operator|+
literal|"file = {Bemerkung:H:\\bla\\ups  sala.pdf:PDF}, \n"
operator|+
literal|"}"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"canh05"
argument_list|,
name|e
operator|.
name|getCiteKey
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|BibtexEntryType
operator|.
name|ARTICLE
argument_list|,
name|e
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"a b"
argument_list|,
name|e
operator|.
name|getField
argument_list|(
literal|"a"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"a\nb"
argument_list|,
name|e
operator|.
name|getField
argument_list|(
literal|"b"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"a b"
argument_list|,
name|e
operator|.
name|getField
argument_list|(
literal|"c"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"a b"
argument_list|,
name|e
operator|.
name|getField
argument_list|(
literal|"d"
argument_list|)
argument_list|)
expr_stmt|;
comment|// I think the last \n is a bug in the parser...
name|assertEquals
argument_list|(
literal|"Hallo World this is\nnot \nan \n exercise . \n\n"
argument_list|,
name|e
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Hallo World this isnot an exercise . "
argument_list|,
name|e
operator|.
name|getField
argument_list|(
literal|"tabs"
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Test for [2022983] 	 *  	 * @author Uwe Kuehn 	 * @author Andrei Haralevich 	 */
DECL|method|testFileNaming ()
specifier|public
name|void
name|testFileNaming
parameter_list|()
block|{
name|BibtexEntry
name|e
init|=
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@article{canh05,"
operator|+
literal|"title = {\nHallo \nWorld \nthis \n is\n\nnot \n\nan \n\n exercise \n \n.\n \n\n},\n"
operator|+
literal|"tabs = {\nHallo \tWorld \tthis \t is\t\tnot \t\tan \t\n exercise \t \n.\t \n\t},\n"
operator|+
literal|"file = {Bemerkung:H:\\bla\\ups  sala.pdf:PDF}, \n"
operator|+
literal|"}"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"Bemerkung:H:\\bla\\ups  sala.pdf:PDF"
argument_list|,
name|e
operator|.
name|getField
argument_list|(
literal|"file"
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Test for [2022983] 	 *  	 * @author Uwe Kuehn 	 * @author Andrei Haralevich 	 */
DECL|method|testFileNaming1 ()
specifier|public
name|void
name|testFileNaming1
parameter_list|()
block|{
name|BibtexEntry
name|e
init|=
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@article{canh05,"
operator|+
literal|"title = {\nHallo \nWorld \nthis \n is\n\nnot \n\nan \n\n exercise \n \n.\n \n\n},\n"
operator|+
literal|"tabs = {\nHallo \tWorld \tthis \t is\t\tnot \t\tan \t\n exercise \t \n.\t \n\t},\n"
operator|+
literal|"file = {Bemerkung:H:\\bla\\ups  \tsala.pdf:PDF}, \n"
operator|+
literal|"}"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"Bemerkung:H:\\bla\\ups  sala.pdf:PDF"
argument_list|,
name|e
operator|.
name|getField
argument_list|(
literal|"file"
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Test for [2022983] 	 *  	 * @author Uwe Kuehn 	 * @author Andrei Haralevich 	 */
DECL|method|testFileNaming3 ()
specifier|public
name|void
name|testFileNaming3
parameter_list|()
block|{
name|BibtexEntry
name|e
init|=
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@article{canh05,"
operator|+
literal|"title = {\nHallo \nWorld \nthis \n is\n\nnot \n\nan \n\n exercise \n \n.\n \n\n},\n"
operator|+
literal|"tabs = {\nHallo \tWorld \tthis \t is\t\tnot \t\tan \t\n exercise \t \n.\t \n\t},\n"
operator|+
literal|"file = {Bemerkung:H:\\bla\\ups \n\tsala.pdf:PDF}, \n"
operator|+
literal|"}"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"Bemerkung:H:\\bla\\ups  sala.pdf:PDF"
argument_list|,
name|e
operator|.
name|getField
argument_list|(
literal|"file"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

