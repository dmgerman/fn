begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.export.layout.format
package|package
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

begin_comment
comment|/**  * Test case  that verifies the functionalities of the  * formater AuthorLastFirstAbbreviator.  *  * @author Carlos Silla  * @author Christopher Oezbek<oezi@oezi.de>  */
end_comment

begin_class
DECL|class|AuthorLastFirstAbbreviatorTester
specifier|public
class|class
name|AuthorLastFirstAbbreviatorTester
block|{
comment|/**      * Verifies the Abbreviation of one single author with a simple name.      *<p/>      * Ex: Lastname, Name      */
annotation|@
name|Test
DECL|method|testOneAuthorSimpleName ()
specifier|public
name|void
name|testOneAuthorSimpleName
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Abbreviator Test"
argument_list|,
literal|"Lastname, N."
argument_list|,
name|abbreviate
argument_list|(
literal|"Lastname, Name"
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Verifies the Abbreviation of one single author with a common name.      *<p/>      * Ex: Lastname, Name Middlename      */
annotation|@
name|Test
DECL|method|testOneAuthorCommonName ()
specifier|public
name|void
name|testOneAuthorCommonName
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Abbreviator Test"
argument_list|,
literal|"Lastname, N. M."
argument_list|,
name|abbreviate
argument_list|(
literal|"Lastname, Name Middlename"
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Verifies the Abbreviation of two single with a common name.      *<p/>      * Ex: Lastname, Name Middlename      */
annotation|@
name|Test
DECL|method|testTwoAuthorsCommonName ()
specifier|public
name|void
name|testTwoAuthorsCommonName
parameter_list|()
block|{
name|String
name|result
init|=
name|abbreviate
argument_list|(
literal|"Lastname, Name Middlename and Sobrenome, Nome Nomedomeio"
argument_list|)
decl_stmt|;
name|String
name|expectedResult
init|=
literal|"Lastname, N. M. and Sobrenome, N. N."
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Abbreviator Test"
argument_list|,
name|expectedResult
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
comment|/**      * Testcase for      * http://sourceforge.net/tracker/index.php?func=detail&aid=1466924&group_id=92314&atid=600306      */
annotation|@
name|Test
annotation|@
name|Ignore
DECL|method|testJrAuthor ()
specifier|public
name|void
name|testJrAuthor
parameter_list|()
block|{
comment|//TODO what should be done here? reimplement it?
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Other, A. N."
argument_list|,
name|abbreviate
argument_list|(
literal|"Other, Jr., Anthony N."
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testFormat ()
specifier|public
name|void
name|testFormat
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|abbreviate
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Someone, V. S."
argument_list|,
name|abbreviate
argument_list|(
literal|"Someone, Van Something"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Smith, J."
argument_list|,
name|abbreviate
argument_list|(
literal|"Smith, John"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"von Neumann, J. and Smith, J. and Black Brown, P."
argument_list|,
name|abbreviate
argument_list|(
literal|"von Neumann, John and Smith, John and Black Brown, Peter"
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

