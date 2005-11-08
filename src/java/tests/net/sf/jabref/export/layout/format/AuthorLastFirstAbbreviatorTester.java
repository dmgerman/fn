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
comment|/**  * Test case  that verifies the functionalities of the  * formater AuthorLastFirstAbbreviator.  *   * @author Carlos Silla  */
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
comment|//TODO: Verify how to tell this test that it should pass if fail.
comment|/*	public void testTwoAuthorsBadFormating() { 		String name = new String("Lastname, Name Middlename and Nome Nomedomeio Sobrenome"); 		 		AuthorLastFirstAbbreviator ab = new AuthorLastFirstAbbreviator(); 		 		String result = ab.format(name);		 	}*/
block|}
end_class

end_unit

