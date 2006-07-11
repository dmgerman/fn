begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|tests.net.sf.jabref.export.layout.format
package|package
name|tests
operator|.
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
operator|.
name|layout
operator|.
name|format
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
name|export
operator|.
name|layout
operator|.
name|format
operator|.
name|AuthorLastFirstAbbreviator
import|;
end_import

begin_import
import|import
name|junit
operator|.
name|framework
operator|.
name|Assert
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

begin_comment
comment|/**  * Test case  that verifies the functionalities of the  * formater AuthorLastFirstAbbreviator.  *   * @author Carlos Silla  * @author Christopher Oezbek<oezi@oezi.de>  */
end_comment

begin_class
DECL|class|AuthorLastFirstAbbreviatorTester
specifier|public
class|class
name|AuthorLastFirstAbbreviatorTester
extends|extends
name|TestCase
block|{
comment|/** 	 * Verifies the Abbreviation of one single author with a simple name. 	 *  	 * Ex: Lastname, Name 	 */
DECL|method|testOneAuthorSimpleName ()
specifier|public
name|void
name|testOneAuthorSimpleName
parameter_list|()
block|{
name|String
name|name
init|=
literal|"Lastname, Name"
decl_stmt|;
name|AuthorLastFirstAbbreviator
name|ab
init|=
operator|new
name|AuthorLastFirstAbbreviator
argument_list|()
decl_stmt|;
name|String
name|result
init|=
name|ab
operator|.
name|format
argument_list|(
name|name
argument_list|)
decl_stmt|;
comment|//Expected Results:
name|String
name|expectedResult
init|=
literal|"Lastname, N."
decl_stmt|;
comment|//Verifies the functionality:
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Abbreviator Test"
argument_list|,
name|result
argument_list|,
name|expectedResult
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Verifies the Abbreviation of one single author with a common name. 	 *  	 * Ex: Lastname, Name Middlename 	 */
DECL|method|testOneAuthorCommonName ()
specifier|public
name|void
name|testOneAuthorCommonName
parameter_list|()
block|{
name|String
name|name
init|=
literal|"Lastname, Name Middlename"
decl_stmt|;
name|AuthorLastFirstAbbreviator
name|ab
init|=
operator|new
name|AuthorLastFirstAbbreviator
argument_list|()
decl_stmt|;
name|String
name|result
init|=
name|ab
operator|.
name|format
argument_list|(
name|name
argument_list|)
decl_stmt|;
comment|//Expected Results:
name|String
name|expectedResult
init|=
literal|"Lastname, N.M."
decl_stmt|;
comment|//Verifies the functionality:
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Abbreviator Test"
argument_list|,
name|result
argument_list|,
name|expectedResult
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Verifies the Abbreviation of two single with a common name. 	 *  	 * Ex: Lastname, Name Middlename 	 */
DECL|method|testTwoAuthorsCommonName ()
specifier|public
name|void
name|testTwoAuthorsCommonName
parameter_list|()
block|{
name|String
name|name
init|=
literal|"Lastname, Name Middlename and Sobrenome, Nome Nomedomeio"
decl_stmt|;
name|AuthorLastFirstAbbreviator
name|ab
init|=
operator|new
name|AuthorLastFirstAbbreviator
argument_list|()
decl_stmt|;
name|String
name|result
init|=
name|ab
operator|.
name|format
argument_list|(
name|name
argument_list|)
decl_stmt|;
comment|//Expected Results:
name|String
name|expectedResult
init|=
literal|"Lastname, N.M. and Sobrenome, N.N."
decl_stmt|;
comment|//Verifies the functionality:
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Abbreviator Test"
argument_list|,
name|result
argument_list|,
name|expectedResult
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Verifies the Abbreviation of two authors in the incorrect format. 	 *  	 * Ex: Lastname, Name Middlename 	 */
DECL|method|testTwoAuthorsBadFormating ()
specifier|public
name|void
name|testTwoAuthorsBadFormating
parameter_list|()
block|{
comment|// String name = new String("Lastname, Name Middlename and Nome Nomedomeio Sobrenome");
name|fail
argument_list|()
expr_stmt|;
comment|// @TODO: How should a Formatter fail?
comment|// assertEquals("Author names must be formatted \"Last, First\" or \"Last, Jr., First\" before formatting with AuthorLastFirstAbbreviator", abbreviate(name));
block|}
comment|/** 	 * Testcase for  	 * http://sourceforge.net/tracker/index.php?func=detail&aid=1466924&group_id=92314&atid=600306 	 */
DECL|method|testJrAuthor ()
specifier|public
name|void
name|testJrAuthor
parameter_list|()
block|{
name|String
name|name
init|=
literal|"Other, Jr., Anthony N."
decl_stmt|;
name|assertEquals
argument_list|(
literal|"Other, A.N."
argument_list|,
name|abbreviate
argument_list|(
name|name
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|abbreviate (String name)
specifier|protected
name|String
name|abbreviate
parameter_list|(
name|String
name|name
parameter_list|)
block|{
return|return
operator|(
operator|new
name|AuthorLastFirstAbbreviator
argument_list|()
operator|)
operator|.
name|format
argument_list|(
name|name
argument_list|)
return|;
block|}
block|}
end_class

end_unit

