begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.exporter.layout.format
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|exporter
operator|.
name|layout
operator|.
name|format
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
name|org
operator|.
name|junit
operator|.
name|Test
import|;
end_import

begin_class
DECL|class|RemoveWhitespaceTest
specifier|public
class|class
name|RemoveWhitespaceTest
block|{
annotation|@
name|Test
DECL|method|testEmpty ()
specifier|public
name|void
name|testEmpty
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|""
argument_list|,
operator|new
name|RemoveWhitespace
argument_list|()
operator|.
name|format
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testNull ()
specifier|public
name|void
name|testNull
parameter_list|()
block|{
name|assertNull
argument_list|(
operator|new
name|RemoveWhitespace
argument_list|()
operator|.
name|format
argument_list|(
literal|null
argument_list|)
argument_list|)
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
name|assertEquals
argument_list|(
literal|"abcd EFG"
argument_list|,
operator|new
name|RemoveWhitespace
argument_list|()
operator|.
name|format
argument_list|(
literal|"abcd EFG"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testTab ()
specifier|public
name|void
name|testTab
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"abcd EFG"
argument_list|,
operator|new
name|RemoveWhitespace
argument_list|()
operator|.
name|format
argument_list|(
literal|"abcd\t EFG"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testNewLineCombo ()
specifier|public
name|void
name|testNewLineCombo
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"abcd EFG"
argument_list|,
operator|new
name|RemoveWhitespace
argument_list|()
operator|.
name|format
argument_list|(
literal|"abcd\r E\nFG\r\n"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

