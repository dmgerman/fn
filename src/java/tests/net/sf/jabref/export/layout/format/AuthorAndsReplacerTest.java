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
name|export
operator|.
name|layout
operator|.
name|format
operator|.
name|AuthorAndsReplacer
import|;
end_import

begin_class
DECL|class|AuthorAndsReplacerTest
specifier|public
class|class
name|AuthorAndsReplacerTest
extends|extends
name|TestCase
block|{
DECL|method|testFormat ()
specifier|public
name|void
name|testFormat
parameter_list|()
block|{
name|AuthorAndsReplacer
name|a
init|=
operator|new
name|AuthorAndsReplacer
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"John Smith"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"John Smith"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"John Smith& Black Brown, Peter"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"John Smith and Black Brown, Peter"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"John von Neumann; John Smith& Peter Black Brown"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"John von Neumann and John Smith and Peter Black Brown"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

