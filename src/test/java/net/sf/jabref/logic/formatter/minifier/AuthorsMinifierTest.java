begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.formatter.minifier
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|formatter
operator|.
name|minifier
package|;
end_package

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|After
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
name|Test
import|;
end_import

begin_class
DECL|class|AuthorsMinifierTest
specifier|public
class|class
name|AuthorsMinifierTest
block|{
DECL|field|formatter
specifier|private
name|AuthorsMinifier
name|formatter
decl_stmt|;
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|formatter
operator|=
operator|new
name|AuthorsMinifier
argument_list|()
expr_stmt|;
block|}
annotation|@
name|After
DECL|method|teardown ()
specifier|public
name|void
name|teardown
parameter_list|()
block|{
name|formatter
operator|=
literal|null
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|returnsFormatterName ()
specifier|public
name|void
name|returnsFormatterName
parameter_list|()
block|{
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|formatter
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNotEquals
argument_list|(
literal|""
argument_list|,
name|formatter
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|minifyAuthorNames ()
specifier|public
name|void
name|minifyAuthorNames
parameter_list|()
block|{
name|expectCorrect
argument_list|(
literal|"Simon Harrer"
argument_list|,
literal|"Simon Harrer"
argument_list|)
expr_stmt|;
name|expectCorrect
argument_list|(
literal|"Simon Harrer and others"
argument_list|,
literal|"Simon Harrer and others"
argument_list|)
expr_stmt|;
name|expectCorrect
argument_list|(
literal|"Simon Harrer and JÃ¶rg Lenhard"
argument_list|,
literal|"Simon Harrer and JÃ¶rg Lenhard"
argument_list|)
expr_stmt|;
name|expectCorrect
argument_list|(
literal|"Simon Harrer and JÃ¶rg Lenhard and Guido Wirtz"
argument_list|,
literal|"Simon Harrer and others"
argument_list|)
expr_stmt|;
name|expectCorrect
argument_list|(
literal|"Simon Harrer and JÃ¶rg Lenhard and Guido Wirtz and others"
argument_list|,
literal|"Simon Harrer and others"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|formatEmptyFields ()
specifier|public
name|void
name|formatEmptyFields
parameter_list|()
block|{
name|expectCorrect
argument_list|(
literal|""
argument_list|,
literal|""
argument_list|)
expr_stmt|;
block|}
DECL|method|expectCorrect (String input, String expected)
specifier|private
name|void
name|expectCorrect
parameter_list|(
name|String
name|input
parameter_list|,
name|String
name|expected
parameter_list|)
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
name|input
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

