begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.util.strings
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|strings
package|;
end_package

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
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
name|jupiter
operator|.
name|api
operator|.
name|Assertions
operator|.
name|assertEquals
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Assertions
operator|.
name|assertNull
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Assertions
operator|.
name|assertThrows
import|;
end_import

begin_class
DECL|class|DiffHighlightingTest
specifier|public
class|class
name|DiffHighlightingTest
block|{
annotation|@
name|Test
DECL|method|testGenerateDiffHighlightingBothNullReturnsNull ()
specifier|public
name|void
name|testGenerateDiffHighlightingBothNullReturnsNull
parameter_list|()
block|{
name|assertNull
argument_list|(
name|DiffHighlighting
operator|.
name|generateDiffHighlighting
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testNullSeparatorThrowsNPE ()
specifier|public
name|void
name|testNullSeparatorThrowsNPE
parameter_list|()
block|{
name|assertThrows
argument_list|(
name|NullPointerException
operator|.
name|class
argument_list|,
parameter_list|()
lambda|->
name|DiffHighlighting
operator|.
name|generateDiffHighlighting
argument_list|(
literal|""
argument_list|,
literal|""
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGenerateDiffHighlightingNoDiff ()
specifier|public
name|void
name|testGenerateDiffHighlightingNoDiff
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"foo"
argument_list|,
name|DiffHighlighting
operator|.
name|generateDiffHighlighting
argument_list|(
literal|"foo"
argument_list|,
literal|"foo"
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGenerateDiffHighlightingSingleWordAddTextWordDiff ()
specifier|public
name|void
name|testGenerateDiffHighlightingSingleWordAddTextWordDiff
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"<span class=del>foo</span><span class=add>foobar</span>"
argument_list|,
name|DiffHighlighting
operator|.
name|generateDiffHighlighting
argument_list|(
literal|"foo"
argument_list|,
literal|"foobar"
argument_list|,
literal|" "
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGenerateDiffHighlightingSingleWordAddTextCharacterDiff ()
specifier|public
name|void
name|testGenerateDiffHighlightingSingleWordAddTextCharacterDiff
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"foo<span class=add>bar</span>"
argument_list|,
name|DiffHighlighting
operator|.
name|generateDiffHighlighting
argument_list|(
literal|"foo"
argument_list|,
literal|"foobar"
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGenerateDiffHighlightingSingleWordDeleteTextWordDiff ()
specifier|public
name|void
name|testGenerateDiffHighlightingSingleWordDeleteTextWordDiff
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"<span class=del>foobar</span><span class=add>foo</span>"
argument_list|,
name|DiffHighlighting
operator|.
name|generateDiffHighlighting
argument_list|(
literal|"foobar"
argument_list|,
literal|"foo"
argument_list|,
literal|" "
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGenerateDiffHighlightingSingleWordDeleteTextCharacterDiff ()
specifier|public
name|void
name|testGenerateDiffHighlightingSingleWordDeleteTextCharacterDiff
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"foo<span class=del>bar</span>"
argument_list|,
name|DiffHighlighting
operator|.
name|generateDiffHighlighting
argument_list|(
literal|"foobar"
argument_list|,
literal|"foo"
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateSymmetricHighlightingSingleWordAddTextWordDiff ()
specifier|public
name|void
name|generateSymmetricHighlightingSingleWordAddTextWordDiff
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"<span class=change>foo</span>"
argument_list|,
name|DiffHighlighting
operator|.
name|generateSymmetricHighlighting
argument_list|(
literal|"foo"
argument_list|,
literal|"foobar"
argument_list|,
literal|" "
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateSymmetricHighlightingSingleWordAddTextCharacterDiff ()
specifier|public
name|void
name|generateSymmetricHighlightingSingleWordAddTextCharacterDiff
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"foo"
argument_list|,
name|DiffHighlighting
operator|.
name|generateSymmetricHighlighting
argument_list|(
literal|"foo"
argument_list|,
literal|"foobar"
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateSymmetricHighlightingSingleWordDeleteTextWordDiff ()
specifier|public
name|void
name|generateSymmetricHighlightingSingleWordDeleteTextWordDiff
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"<span class=change>foobar</span>"
argument_list|,
name|DiffHighlighting
operator|.
name|generateSymmetricHighlighting
argument_list|(
literal|"foobar"
argument_list|,
literal|"foo"
argument_list|,
literal|" "
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateSymmetricHighlightingSingleWordDeleteTextCharacterDiff ()
specifier|public
name|void
name|generateSymmetricHighlightingSingleWordDeleteTextCharacterDiff
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"foo<span class=add>bar</span>"
argument_list|,
name|DiffHighlighting
operator|.
name|generateSymmetricHighlighting
argument_list|(
literal|"foobar"
argument_list|,
literal|"foo"
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateSymmetricHighlightingMultipleWordsDeleteTextCharacterDiff ()
specifier|public
name|void
name|generateSymmetricHighlightingMultipleWordsDeleteTextCharacterDiff
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"foo<span class=add>bar</span> and<span class=add>some</span>thing"
argument_list|,
name|DiffHighlighting
operator|.
name|generateSymmetricHighlighting
argument_list|(
literal|"foobar and something"
argument_list|,
literal|"foo and thing"
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateSymmetricHighlightingMultipleWordsDeleteTextWordDiff ()
specifier|public
name|void
name|generateSymmetricHighlightingMultipleWordsDeleteTextWordDiff
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"foo<span class=add>bar</span> and<span class=add>some</span> thing"
argument_list|,
name|DiffHighlighting
operator|.
name|generateSymmetricHighlighting
argument_list|(
literal|"foo bar and some thing"
argument_list|,
literal|"foo and thing"
argument_list|,
literal|" "
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

