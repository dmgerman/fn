begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.mergeentries
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|mergeentries
package|;
end_package

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

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertNull
import|;
end_import

begin_class
DECL|class|MergeEntriesTest
specifier|public
class|class
name|MergeEntriesTest
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
name|MergeEntries
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
argument_list|(
name|expected
operator|=
name|NullPointerException
operator|.
name|class
argument_list|)
DECL|method|testNullSeparatorThrowsNPE ()
specifier|public
name|void
name|testNullSeparatorThrowsNPE
parameter_list|()
block|{
name|assertNull
argument_list|(
name|MergeEntries
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
name|MergeEntries
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
name|MergeEntries
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
name|MergeEntries
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
name|MergeEntries
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
name|MergeEntries
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
block|}
end_class

end_unit

