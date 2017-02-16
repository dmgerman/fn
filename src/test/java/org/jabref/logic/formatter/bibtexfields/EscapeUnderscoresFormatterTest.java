begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.formatter.bibtexfields
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|formatter
operator|.
name|bibtexfields
package|;
end_package

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

begin_class
DECL|class|EscapeUnderscoresFormatterTest
specifier|public
class|class
name|EscapeUnderscoresFormatterTest
block|{
DECL|field|formatter
specifier|private
name|EscapeUnderscoresFormatter
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
name|EscapeUnderscoresFormatter
argument_list|()
expr_stmt|;
block|}
comment|/**      * Check whether the clear formatter really returns the empty string for the empty string      */
annotation|@
name|Test
DECL|method|formatReturnsSameTextIfNoUnderscoresPresent ()
specifier|public
name|void
name|formatReturnsSameTextIfNoUnderscoresPresent
parameter_list|()
throws|throws
name|Exception
block|{
name|assertEquals
argument_list|(
literal|"Lorem ipsum"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Lorem ipsum"
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Check whether the clear formatter really returns the empty string for some string      */
annotation|@
name|Test
DECL|method|formatEscapesUnderscoresIfPresent ()
specifier|public
name|void
name|formatEscapesUnderscoresIfPresent
parameter_list|()
throws|throws
name|Exception
block|{
name|assertEquals
argument_list|(
literal|"Lorem\\_ipsum"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Lorem_ipsum"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|formatExample ()
specifier|public
name|void
name|formatExample
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Text\\_with\\_underscores"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
name|formatter
operator|.
name|getExampleInput
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

