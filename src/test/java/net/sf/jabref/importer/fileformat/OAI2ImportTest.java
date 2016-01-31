begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.importer.fileformat
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
operator|.
name|fileformat
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
name|importer
operator|.
name|OAI2Handler
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
name|fetcher
operator|.
name|OAI2Fetcher
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
name|bibtex
operator|.
name|EntryTypes
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
name|IdGenerator
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
name|org
operator|.
name|xml
operator|.
name|sax
operator|.
name|SAXException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|SAXParser
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|SAXParserFactory
import|;
end_import

begin_comment
comment|/**  * Test for OAI2-Handler and Fetcher.  *  * @author Ulrich St&auml;rk  * @author Christian Kopf  * @author Christopher Oezbek  */
end_comment

begin_class
DECL|class|OAI2ImportTest
specifier|public
class|class
name|OAI2ImportTest
block|{
DECL|field|handler
name|OAI2Handler
name|handler
decl_stmt|;
DECL|field|be
name|BibEntry
name|be
decl_stmt|;
DECL|field|parserFactory
specifier|protected
name|SAXParserFactory
name|parserFactory
decl_stmt|;
DECL|field|saxParser
specifier|protected
name|SAXParser
name|saxParser
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
name|parserFactory
operator|=
name|SAXParserFactory
operator|.
name|newInstance
argument_list|()
expr_stmt|;
name|saxParser
operator|=
name|parserFactory
operator|.
name|newSAXParser
argument_list|()
expr_stmt|;
name|be
operator|=
operator|new
name|BibEntry
argument_list|(
name|IdGenerator
operator|.
name|next
argument_list|()
argument_list|,
literal|"article"
argument_list|)
expr_stmt|;
name|handler
operator|=
operator|new
name|OAI2Handler
argument_list|(
name|be
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testCorrectLineBreaks ()
specifier|public
name|void
name|testCorrectLineBreaks
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Test this"
argument_list|,
name|OAI2Fetcher
operator|.
name|correctLineBreaks
argument_list|(
literal|"Test\nthis"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Test this"
argument_list|,
name|OAI2Fetcher
operator|.
name|correctLineBreaks
argument_list|(
literal|"Test \n this"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Test\nthis"
argument_list|,
name|OAI2Fetcher
operator|.
name|correctLineBreaks
argument_list|(
literal|"Test\n\nthis"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Test\nthis"
argument_list|,
name|OAI2Fetcher
operator|.
name|correctLineBreaks
argument_list|(
literal|"Test\n    \nthis"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Test\nthis"
argument_list|,
name|OAI2Fetcher
operator|.
name|correctLineBreaks
argument_list|(
literal|"  Test   \n   \n   this  "
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testParse ()
specifier|public
name|void
name|testParse
parameter_list|()
throws|throws
name|Throwable
block|{
try|try
block|{
name|saxParser
operator|.
name|parse
argument_list|(
name|this
operator|.
name|getClass
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
literal|"oai2.xml"
argument_list|)
argument_list|,
name|handler
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"hep-ph/0408155"
argument_list|,
name|be
operator|.
name|getField
argument_list|(
literal|"eprint"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"G. F. Giudice and A. Riotto and A. Zaffaroni and J. LÃ³pez-PeÃ±a"
argument_list|,
name|be
operator|.
name|getField
argument_list|(
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Nucl.Phys. B"
argument_list|,
name|be
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
literal|"710"
argument_list|,
name|be
operator|.
name|getField
argument_list|(
literal|"volume"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"2005"
argument_list|,
name|be
operator|.
name|getField
argument_list|(
literal|"year"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"511-525"
argument_list|,
name|be
operator|.
name|getField
argument_list|(
literal|"pages"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Citekey is only generated if the user says so in the import
comment|// inspection dialog.
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|null
argument_list|,
name|be
operator|.
name|getCiteKey
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Heavy Particles from Inflation"
argument_list|,
name|be
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|be
operator|.
name|getField
argument_list|(
literal|"abstract"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"23 pages"
argument_list|,
name|be
operator|.
name|getField
argument_list|(
literal|"comments"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"CERN-PH-TH/2004-151"
argument_list|,
name|be
operator|.
name|getField
argument_list|(
literal|"reportno"
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SAXException
name|e
parameter_list|)
block|{
throw|throw
name|e
operator|.
name|getException
argument_list|()
throw|;
block|}
block|}
annotation|@
name|Test
DECL|method|testOai22xml ()
specifier|public
name|void
name|testOai22xml
parameter_list|()
throws|throws
name|Exception
block|{
try|try
block|{
name|saxParser
operator|.
name|parse
argument_list|(
name|this
operator|.
name|getClass
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
literal|"oai22.xml"
argument_list|)
argument_list|,
name|handler
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"2005"
argument_list|,
name|be
operator|.
name|getField
argument_list|(
literal|"year"
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SAXException
name|e
parameter_list|)
block|{
throw|throw
name|e
operator|.
name|getException
argument_list|()
throw|;
block|}
block|}
annotation|@
name|Test
DECL|method|testOai23xml ()
specifier|public
name|void
name|testOai23xml
parameter_list|()
throws|throws
name|Throwable
block|{
try|try
block|{
name|saxParser
operator|.
name|parse
argument_list|(
name|this
operator|.
name|getClass
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
literal|"oai23.xml"
argument_list|)
argument_list|,
name|handler
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Javier LÃ³pez PeÃ±a and Gabriel Navarro"
argument_list|,
name|be
operator|.
name|getField
argument_list|(
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SAXException
name|e
parameter_list|)
block|{
throw|throw
name|e
operator|.
name|getException
argument_list|()
throw|;
block|}
block|}
annotation|@
name|Test
DECL|method|testUrlConstructor ()
specifier|public
name|void
name|testUrlConstructor
parameter_list|()
block|{
name|OAI2Fetcher
name|fetcher
init|=
operator|new
name|OAI2Fetcher
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"http://export.arxiv.org/oai2?verb=GetRecord&identifier=oai%3AarXiv.org%3Ahep-ph%2F0408155&metadataPrefix=arXiv"
argument_list|,
name|fetcher
operator|.
name|constructUrl
argument_list|(
literal|"hep-ph/0408155"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"http://export.arxiv.org/oai2?verb=GetRecord&identifier=oai%3AarXiv.org%3Amath%2F0612188&metadataPrefix=arXiv"
argument_list|,
name|fetcher
operator|.
name|constructUrl
argument_list|(
literal|"math/0612188"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testFixKey ()
specifier|public
name|void
name|testFixKey
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|OAI2Fetcher
operator|.
name|fixKey
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"test"
argument_list|,
name|OAI2Fetcher
operator|.
name|fixKey
argument_list|(
literal|"test"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"math/0601001"
argument_list|,
name|OAI2Fetcher
operator|.
name|fixKey
argument_list|(
literal|"math.RA/0601001"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"math/0601001"
argument_list|,
name|OAI2Fetcher
operator|.
name|fixKey
argument_list|(
literal|"math.QA/0601001"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"hep-ph/0408155"
argument_list|,
name|OAI2Fetcher
operator|.
name|fixKey
argument_list|(
literal|"hep-ph/0408155"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"0709.3040v1"
argument_list|,
name|OAI2Fetcher
operator|.
name|fixKey
argument_list|(
literal|"arXiv:0709.3040v1"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|OAI2Fetcher
operator|.
name|fixKey
argument_list|(
literal|"arXiv:"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
annotation|@
name|Ignore
DECL|method|testOnline ()
specifier|public
name|void
name|testOnline
parameter_list|()
throws|throws
name|InterruptedException
block|{
block|{
name|OAI2Fetcher
name|fetcher
init|=
operator|new
name|OAI2Fetcher
argument_list|()
decl_stmt|;
name|be
operator|=
name|fetcher
operator|.
name|importOai2Entry
argument_list|(
literal|"math.RA/0612188"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|be
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"math/0612188"
argument_list|,
name|be
operator|.
name|getField
argument_list|(
literal|"eprint"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"On the classification and properties of noncommutative duplicates"
argument_list|,
name|be
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Javier LÃ³pez PeÃ±a and Gabriel Navarro"
argument_list|,
name|be
operator|.
name|getField
argument_list|(
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"2007"
argument_list|,
name|be
operator|.
name|getField
argument_list|(
literal|"year"
argument_list|)
argument_list|)
expr_stmt|;
name|Thread
operator|.
name|sleep
argument_list|(
literal|20000
argument_list|)
expr_stmt|;
block|}
block|{
name|OAI2Fetcher
name|fetcher
init|=
operator|new
name|OAI2Fetcher
argument_list|()
decl_stmt|;
name|be
operator|=
name|fetcher
operator|.
name|importOai2Entry
argument_list|(
literal|"astro-ph/0702080"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|be
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"astro-ph/0702080"
argument_list|,
name|be
operator|.
name|getField
argument_list|(
literal|"eprint"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Magnetized Hypermassive Neutron Star Collapse: a candidate central engine for short-hard GRBs"
argument_list|,
name|be
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
name|Thread
operator|.
name|sleep
argument_list|(
literal|20000
argument_list|)
expr_stmt|;
block|}
block|{
name|OAI2Fetcher
name|fetcher
init|=
operator|new
name|OAI2Fetcher
argument_list|()
decl_stmt|;
name|be
operator|=
name|fetcher
operator|.
name|importOai2Entry
argument_list|(
literal|"math.QA/0601001"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|be
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"math/0601001"
argument_list|,
name|be
operator|.
name|getField
argument_list|(
literal|"eprint"
argument_list|)
argument_list|)
expr_stmt|;
name|Thread
operator|.
name|sleep
argument_list|(
literal|20000
argument_list|)
expr_stmt|;
block|}
block|{
name|OAI2Fetcher
name|fetcher
init|=
operator|new
name|OAI2Fetcher
argument_list|()
decl_stmt|;
name|be
operator|=
name|fetcher
operator|.
name|importOai2Entry
argument_list|(
literal|"hep-ph/0408155"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|be
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"hep-ph/0408155"
argument_list|,
name|be
operator|.
name|getField
argument_list|(
literal|"eprint"
argument_list|)
argument_list|)
expr_stmt|;
name|Thread
operator|.
name|sleep
argument_list|(
literal|20000
argument_list|)
expr_stmt|;
block|}
name|OAI2Fetcher
name|fetcher
init|=
operator|new
name|OAI2Fetcher
argument_list|()
decl_stmt|;
name|be
operator|=
name|fetcher
operator|.
name|importOai2Entry
argument_list|(
literal|"0709.3040"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|be
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"2007"
argument_list|,
name|be
operator|.
name|getField
argument_list|(
literal|"year"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"#sep#"
argument_list|,
name|be
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

