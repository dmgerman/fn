begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.wizard.auximport
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|wizard
operator|.
name|auximport
package|;
end_package

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|*
import|;
end_import

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
name|io
operator|.
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStreamReader
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
name|Test
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
name|BibDatabase
import|;
end_import

begin_class
DECL|class|AuxFileParserTest
specifier|public
class|class
name|AuxFileParserTest
block|{
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
name|Globals
operator|.
name|prefs
operator|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testNormal ()
specifier|public
name|void
name|testNormal
parameter_list|()
block|{
name|InputStream
name|originalStream
init|=
name|AuxFileParserTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"origin.bib"
argument_list|)
decl_stmt|;
name|File
name|auxFile
init|=
operator|new
name|File
argument_list|(
name|AuxFileParserTest
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"paper.aux"
argument_list|)
operator|.
name|getFile
argument_list|()
argument_list|)
decl_stmt|;
try|try
init|(
name|InputStreamReader
name|originalReader
init|=
operator|new
name|InputStreamReader
argument_list|(
name|originalStream
argument_list|)
init|)
block|{
name|ParserResult
name|result
init|=
name|BibtexParser
operator|.
name|parse
argument_list|(
name|originalReader
argument_list|)
decl_stmt|;
name|AuxFileParser
name|auxFileParser
init|=
operator|new
name|AuxFileParser
argument_list|(
name|auxFile
operator|.
name|getAbsolutePath
argument_list|()
argument_list|,
name|result
operator|.
name|getDatabase
argument_list|()
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
name|auxFileParser
operator|.
name|getGeneratedBibDatabase
argument_list|()
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|0
argument_list|,
name|auxFileParser
operator|.
name|getUnresolvedKeysCount
argument_list|()
argument_list|)
expr_stmt|;
name|BibDatabase
name|newDB
init|=
name|auxFileParser
operator|.
name|getGeneratedBibDatabase
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|newDB
operator|.
name|getEntries
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|auxFileParser
operator|.
name|getResolvedKeysCount
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|auxFileParser
operator|.
name|getFoundKeysInAux
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|auxFileParser
operator|.
name|getFoundKeysInAux
argument_list|()
argument_list|,
name|auxFileParser
operator|.
name|getResolvedKeysCount
argument_list|()
operator|+
name|auxFileParser
operator|.
name|getUnresolvedKeysCount
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|0
argument_list|,
name|auxFileParser
operator|.
name|getCrossRefEntriesCount
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|fail
argument_list|(
literal|"Cannot open file"
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|testNotAllFound ()
specifier|public
name|void
name|testNotAllFound
parameter_list|()
block|{
name|InputStream
name|originalStream
init|=
name|AuxFileParserTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"origin.bib"
argument_list|)
decl_stmt|;
name|File
name|auxFile
init|=
operator|new
name|File
argument_list|(
name|AuxFileParserTest
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"badpaper.aux"
argument_list|)
operator|.
name|getFile
argument_list|()
argument_list|)
decl_stmt|;
try|try
init|(
name|InputStreamReader
name|originalReader
init|=
operator|new
name|InputStreamReader
argument_list|(
name|originalStream
argument_list|)
init|)
block|{
name|ParserResult
name|result
init|=
name|BibtexParser
operator|.
name|parse
argument_list|(
name|originalReader
argument_list|)
decl_stmt|;
name|AuxFileParser
name|auxFileParser
init|=
operator|new
name|AuxFileParser
argument_list|(
name|auxFile
operator|.
name|getAbsolutePath
argument_list|()
argument_list|,
name|result
operator|.
name|getDatabase
argument_list|()
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
name|auxFileParser
operator|.
name|getGeneratedBibDatabase
argument_list|()
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|auxFileParser
operator|.
name|getUnresolvedKeysCount
argument_list|()
argument_list|)
expr_stmt|;
name|BibDatabase
name|newDB
init|=
name|auxFileParser
operator|.
name|getGeneratedBibDatabase
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|newDB
operator|.
name|getEntries
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|auxFileParser
operator|.
name|getResolvedKeysCount
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|3
argument_list|,
name|auxFileParser
operator|.
name|getFoundKeysInAux
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|auxFileParser
operator|.
name|getFoundKeysInAux
argument_list|()
argument_list|,
name|auxFileParser
operator|.
name|getResolvedKeysCount
argument_list|()
operator|+
name|auxFileParser
operator|.
name|getUnresolvedKeysCount
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|0
argument_list|,
name|auxFileParser
operator|.
name|getCrossRefEntriesCount
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|fail
argument_list|(
literal|"Cannot open file"
argument_list|)
expr_stmt|;
block|}
block|}
comment|// TODO strings and preamble test
comment|// TODO return type of generate during error should be false
block|}
end_class

end_unit

