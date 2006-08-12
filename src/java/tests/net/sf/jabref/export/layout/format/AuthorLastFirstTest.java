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
name|LayoutFormatter
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
name|AuthorLastFirst
import|;
end_import

begin_class
DECL|class|AuthorLastFirstTest
specifier|public
class|class
name|AuthorLastFirstTest
extends|extends
name|TestCase
block|{
DECL|method|testFormat ()
specifier|public
name|void
name|testFormat
parameter_list|()
block|{
name|LayoutFormatter
name|a
init|=
operator|new
name|AuthorLastFirst
argument_list|()
decl_stmt|;
comment|// Empty case
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
comment|// Single Names
name|assertEquals
argument_list|(
literal|"Someone, Van Something"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"Van Something Someone"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Two names
name|assertEquals
argument_list|(
literal|"von Neumann, John and Black Brown, Peter"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"John von Neumann and Black Brown, Peter"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Three names
name|assertEquals
argument_list|(
literal|"von Neumann, John and Smith, John and Black Brown, Peter"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"von Neumann, John and Smith, John and Black Brown, Peter"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"von Neumann, John and Smith, John and Black Brown, Peter"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"John von Neumann and John Smith and Black Brown, Peter"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

