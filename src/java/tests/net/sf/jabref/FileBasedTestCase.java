begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|tests.net.sf.jabref
package|package
name|tests
operator|.
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|StringReader
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
comment|/**  * A base class for Testing in JabRef that comes along with some useful  * functions.  *   * @author $Author$  * @version $Revision$ ($Date$)  *   */
end_comment

begin_class
DECL|class|FileBasedTestCase
specifier|public
class|class
name|FileBasedTestCase
extends|extends
name|TestCase
block|{
comment|/** 	 * Will check if two paths are the same. 	 */
DECL|method|assertEqualPaths (String path1, String path2)
specifier|public
specifier|static
name|void
name|assertEqualPaths
parameter_list|(
name|String
name|path1
parameter_list|,
name|String
name|path2
parameter_list|)
block|{
if|if
condition|(
name|path1
operator|==
name|path2
condition|)
return|return;
if|if
condition|(
operator|(
name|path1
operator|==
literal|null
operator|||
name|path2
operator|==
literal|null
operator|)
operator|&&
name|path1
operator|!=
name|path2
condition|)
name|fail
argument_list|(
literal|"Expected: "
operator|+
name|path1
operator|+
literal|" but was: "
operator|+
name|path2
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|path1
operator|.
name|replaceAll
argument_list|(
literal|"\\\\"
argument_list|,
literal|"/"
argument_list|)
argument_list|,
name|path2
operator|.
name|replaceAll
argument_list|(
literal|"\\\\"
argument_list|,
literal|"/"
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Creates a temp directory in the System temp directory. 	 *  	 * Taken from 	 * http://forum.java.sun.com/thread.jspa?threadID=470197&messageID=2169110 	 *  	 * Author: jfbriere 	 *  	 * @return returns null if directory could not created. 	 */
DECL|method|createTempDir (String prefix)
specifier|public
specifier|static
name|File
name|createTempDir
parameter_list|(
name|String
name|prefix
parameter_list|)
block|{
return|return
name|createTempDir
argument_list|(
name|prefix
argument_list|,
literal|null
argument_list|)
return|;
block|}
comment|/** 	 * Creates a temp directory in a given directory. 	 *  	 * Taken from 	 * http://forum.java.sun.com/thread.jspa?threadID=470197&messageID=2169110 	 *  	 * Author: jfbriere 	 *  	 * @param directory 	 *            MayBeNull - null indicates that the system tmp directory 	 *            should be used. 	 *  	 * @return returns null if directory could not created. 	 */
DECL|method|createTempDir (String prefix, File directory)
specifier|public
specifier|static
name|File
name|createTempDir
parameter_list|(
name|String
name|prefix
parameter_list|,
name|File
name|directory
parameter_list|)
block|{
try|try
block|{
name|File
name|tempFile
init|=
name|File
operator|.
name|createTempFile
argument_list|(
name|prefix
argument_list|,
literal|""
argument_list|,
name|directory
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|tempFile
operator|.
name|delete
argument_list|()
condition|)
return|return
literal|null
return|;
if|if
condition|(
operator|!
name|tempFile
operator|.
name|mkdir
argument_list|()
condition|)
return|return
literal|null
return|;
return|return
name|tempFile
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
block|}
comment|/** 	 * Deletes a directory or file 	 *  	 * Taken from 	 * http://forum.java.sun.com/thread.jspa?threadID=470197&messageID=2169110 	 *  	 * Author: jfbriere 	 *  	 * @param file 	 */
DECL|method|deleteRecursive (File file)
specifier|public
specifier|static
name|void
name|deleteRecursive
parameter_list|(
name|File
name|file
parameter_list|)
block|{
if|if
condition|(
name|file
operator|.
name|isDirectory
argument_list|()
condition|)
block|{
name|File
index|[]
name|fileArray
init|=
name|file
operator|.
name|listFiles
argument_list|()
decl_stmt|;
if|if
condition|(
name|fileArray
operator|!=
literal|null
condition|)
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|fileArray
operator|.
name|length
condition|;
name|i
operator|++
control|)
name|deleteRecursive
argument_list|(
name|fileArray
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
name|file
operator|.
name|delete
argument_list|()
expr_stmt|;
block|}
DECL|field|database
specifier|static
name|BibtexDatabase
name|database
decl_stmt|;
DECL|field|entry
specifier|static
name|BibtexEntry
name|entry
decl_stmt|;
DECL|field|root
name|File
name|root
decl_stmt|;
DECL|field|oldPdfDirectory
specifier|private
name|String
name|oldPdfDirectory
decl_stmt|;
DECL|field|oldUseRegExp
specifier|private
name|boolean
name|oldUseRegExp
decl_stmt|;
DECL|method|getBibtexEntry ()
specifier|public
specifier|static
name|BibtexEntry
name|getBibtexEntry
parameter_list|()
block|{
if|if
condition|(
name|database
operator|==
literal|null
condition|)
block|{
name|StringReader
name|reader
init|=
operator|new
name|StringReader
argument_list|(
literal|"@ARTICLE{HipKro03,\n"
operator|+
literal|"  author = {Eric von Hippel and Georg von Krogh},\n"
operator|+
literal|"  title = {Open Source Software and the \"Private-Collective\" Innovation Model: Issues for Organization Science},\n"
operator|+
literal|"  journal = {Organization Science},\n"
operator|+
literal|"  year = {2003},\n"
operator|+
literal|"  volume = {14},\n"
operator|+
literal|"  pages = {209--223},\n"
operator|+
literal|"  number = {2},\n"
operator|+
literal|"  address = {Institute for Operations Research and the Management Sciences (INFORMS), Linthicum, Maryland, USA},\n"
operator|+
literal|"  doi = {http://dx.doi.org/10.1287/orsc.14.2.209.14992},"
operator|+
literal|"\n"
operator|+
literal|"  issn = {1526-5455},"
operator|+
literal|"\n"
operator|+
literal|"  publisher = {INFORMS}\n"
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
block|}
return|return
name|entry
return|;
block|}
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
name|oldUseRegExp
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_REG_EXP_SEARCH_KEY
argument_list|)
expr_stmt|;
name|oldPdfDirectory
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"pdfDirectory"
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_REG_EXP_SEARCH_KEY
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|getBibtexEntry
argument_list|()
expr_stmt|;
name|assertNotNull
argument_list|(
name|database
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|entry
argument_list|)
expr_stmt|;
comment|// Create file structure
try|try
block|{
name|root
operator|=
name|createTempDir
argument_list|(
literal|"UtilFindFileTest"
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
literal|"pdfDirectory"
argument_list|,
name|root
operator|.
name|getPath
argument_list|()
argument_list|)
expr_stmt|;
name|File
name|subDir1
init|=
operator|new
name|File
argument_list|(
name|root
argument_list|,
literal|"Organization Science"
argument_list|)
decl_stmt|;
name|subDir1
operator|.
name|mkdir
argument_list|()
expr_stmt|;
name|File
name|pdf1
init|=
operator|new
name|File
argument_list|(
name|subDir1
argument_list|,
literal|"HipKro03 - Hello.pdf"
argument_list|)
decl_stmt|;
name|pdf1
operator|.
name|createNewFile
argument_list|()
expr_stmt|;
name|File
name|pdf1a
init|=
operator|new
name|File
argument_list|(
name|root
argument_list|,
literal|"HipKro03 - Hello.pdf"
argument_list|)
decl_stmt|;
name|pdf1a
operator|.
name|createNewFile
argument_list|()
expr_stmt|;
name|File
name|subDir2
init|=
operator|new
name|File
argument_list|(
name|root
argument_list|,
literal|"pdfs"
argument_list|)
decl_stmt|;
name|subDir2
operator|.
name|mkdir
argument_list|()
expr_stmt|;
name|File
name|subsubDir1
init|=
operator|new
name|File
argument_list|(
name|subDir2
argument_list|,
literal|"sub"
argument_list|)
decl_stmt|;
name|subsubDir1
operator|.
name|mkdir
argument_list|()
expr_stmt|;
name|File
name|pdf2
init|=
operator|new
name|File
argument_list|(
name|subsubDir1
argument_list|,
literal|"HipKro03-sub.pdf"
argument_list|)
decl_stmt|;
name|pdf2
operator|.
name|createNewFile
argument_list|()
expr_stmt|;
name|File
name|dir2002
init|=
operator|new
name|File
argument_list|(
name|root
argument_list|,
literal|"2002"
argument_list|)
decl_stmt|;
name|dir2002
operator|.
name|mkdir
argument_list|()
expr_stmt|;
name|File
name|dir2003
init|=
operator|new
name|File
argument_list|(
name|root
argument_list|,
literal|"2003"
argument_list|)
decl_stmt|;
name|dir2003
operator|.
name|mkdir
argument_list|()
expr_stmt|;
name|File
name|pdf3
init|=
operator|new
name|File
argument_list|(
name|dir2003
argument_list|,
literal|"Paper by HipKro03.pdf"
argument_list|)
decl_stmt|;
name|pdf3
operator|.
name|createNewFile
argument_list|()
expr_stmt|;
name|File
name|dirTest
init|=
operator|new
name|File
argument_list|(
name|root
argument_list|,
literal|"test"
argument_list|)
decl_stmt|;
name|dirTest
operator|.
name|mkdir
argument_list|()
expr_stmt|;
name|File
name|pdf4
init|=
operator|new
name|File
argument_list|(
name|dirTest
argument_list|,
literal|"HipKro03.pdf"
argument_list|)
decl_stmt|;
name|pdf4
operator|.
name|createNewFile
argument_list|()
expr_stmt|;
name|File
name|pdf5
init|=
operator|new
name|File
argument_list|(
name|dirTest
argument_list|,
literal|".TEST"
argument_list|)
decl_stmt|;
name|pdf5
operator|.
name|createNewFile
argument_list|()
expr_stmt|;
name|File
name|pdf6
init|=
operator|new
name|File
argument_list|(
name|dirTest
argument_list|,
literal|"TEST["
argument_list|)
decl_stmt|;
name|pdf6
operator|.
name|createNewFile
argument_list|()
expr_stmt|;
name|File
name|pdf7
init|=
operator|new
name|File
argument_list|(
name|dirTest
argument_list|,
literal|"TE.ST"
argument_list|)
decl_stmt|;
name|pdf7
operator|.
name|createNewFile
argument_list|()
expr_stmt|;
name|File
name|foo
init|=
operator|new
name|File
argument_list|(
name|dirTest
argument_list|,
literal|"foo.dat"
argument_list|)
decl_stmt|;
name|foo
operator|.
name|createNewFile
argument_list|()
expr_stmt|;
name|File
name|graphicsDir
init|=
operator|new
name|File
argument_list|(
name|root
argument_list|,
literal|"graphicsDir"
argument_list|)
decl_stmt|;
name|graphicsDir
operator|.
name|mkdir
argument_list|()
expr_stmt|;
name|File
name|graphicsSubDir
init|=
operator|new
name|File
argument_list|(
name|graphicsDir
argument_list|,
literal|"subDir"
argument_list|)
decl_stmt|;
name|graphicsSubDir
operator|.
name|mkdir
argument_list|()
expr_stmt|;
name|File
name|jpg
init|=
operator|new
name|File
argument_list|(
name|graphicsSubDir
argument_list|,
literal|"testHipKro03test.jpg"
argument_list|)
decl_stmt|;
name|jpg
operator|.
name|createNewFile
argument_list|()
expr_stmt|;
name|File
name|png
init|=
operator|new
name|File
argument_list|(
name|graphicsSubDir
argument_list|,
literal|"testHipKro03test.png"
argument_list|)
decl_stmt|;
name|png
operator|.
name|createNewFile
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|()
throw|;
block|}
block|}
DECL|method|tearDown ()
specifier|public
name|void
name|tearDown
parameter_list|()
block|{
name|deleteRecursive
argument_list|(
name|root
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_REG_EXP_SEARCH_KEY
argument_list|,
name|oldUseRegExp
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
literal|"pdfDirectory"
argument_list|,
name|oldPdfDirectory
argument_list|)
expr_stmt|;
comment|// TODO: This is not a great way to do this, sure ;-)
block|}
DECL|method|testVoid ()
specifier|public
name|void
name|testVoid
parameter_list|()
block|{
comment|// to remove warning
block|}
block|}
end_class

end_unit

