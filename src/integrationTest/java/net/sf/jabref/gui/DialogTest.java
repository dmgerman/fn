begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
package|;
end_package

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JButton
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JDialog
import|;
end_import

begin_import
import|import
name|org
operator|.
name|assertj
operator|.
name|swing
operator|.
name|core
operator|.
name|GenericTypeMatcher
import|;
end_import

begin_import
import|import
name|org
operator|.
name|assertj
operator|.
name|swing
operator|.
name|dependency
operator|.
name|jsr305
operator|.
name|Nonnull
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
name|assertj
operator|.
name|swing
operator|.
name|finder
operator|.
name|WindowFinder
operator|.
name|findDialog
import|;
end_import

begin_class
DECL|class|DialogTest
specifier|public
class|class
name|DialogTest
extends|extends
name|AbstractUITest
block|{
comment|// Not working on Travis - time out
annotation|@
name|Ignore
annotation|@
name|Test
DECL|method|testCancelStyleSelectDialog ()
specifier|public
name|void
name|testCancelStyleSelectDialog
parameter_list|()
block|{
name|mainFrame
operator|.
name|menuItemWithPath
argument_list|(
literal|"Tools"
argument_list|,
literal|"OpenOffice/LibreOffice connection"
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|GenericTypeMatcher
argument_list|<
name|JButton
argument_list|>
name|buttonMatcher
init|=
operator|new
name|GenericTypeMatcher
argument_list|<
name|JButton
argument_list|>
argument_list|(
name|JButton
operator|.
name|class
argument_list|)
block|{
annotation|@
name|Override
specifier|protected
name|boolean
name|isMatching
parameter_list|(
annotation|@
name|Nonnull
name|JButton
name|jButton
parameter_list|)
block|{
return|return
literal|"Select style"
operator|.
name|equals
argument_list|(
name|jButton
operator|.
name|getText
argument_list|()
argument_list|)
return|;
block|}
block|}
decl_stmt|;
name|mainFrame
operator|.
name|button
argument_list|(
name|buttonMatcher
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|GenericTypeMatcher
argument_list|<
name|JDialog
argument_list|>
name|styleDialogMatcher
init|=
operator|new
name|GenericTypeMatcher
argument_list|<
name|JDialog
argument_list|>
argument_list|(
name|JDialog
operator|.
name|class
argument_list|)
block|{
annotation|@
name|Override
specifier|protected
name|boolean
name|isMatching
parameter_list|(
name|JDialog
name|dialog
parameter_list|)
block|{
return|return
literal|"Select style"
operator|.
name|equals
argument_list|(
name|dialog
operator|.
name|getTitle
argument_list|()
argument_list|)
return|;
comment|// Only a single SidePane
block|}
block|}
decl_stmt|;
name|GenericTypeMatcher
argument_list|<
name|JButton
argument_list|>
name|buttonMatcher2
init|=
operator|new
name|GenericTypeMatcher
argument_list|<
name|JButton
argument_list|>
argument_list|(
name|JButton
operator|.
name|class
argument_list|)
block|{
annotation|@
name|Override
specifier|protected
name|boolean
name|isMatching
parameter_list|(
annotation|@
name|Nonnull
name|JButton
name|jButton
parameter_list|)
block|{
return|return
literal|"Cancel"
operator|.
name|equals
argument_list|(
name|jButton
operator|.
name|getText
argument_list|()
argument_list|)
return|;
block|}
block|}
decl_stmt|;
name|findDialog
argument_list|(
name|styleDialogMatcher
argument_list|)
operator|.
name|withTimeout
argument_list|(
literal|10_000
argument_list|)
operator|.
name|using
argument_list|(
name|robot
argument_list|()
argument_list|)
operator|.
name|button
argument_list|(
name|buttonMatcher2
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|exitJabRef
argument_list|()
expr_stmt|;
block|}
comment|// Tests work separately, but not when running both...
annotation|@
name|Test
annotation|@
name|Ignore
DECL|method|testCloseStyleSelectDialog ()
specifier|public
name|void
name|testCloseStyleSelectDialog
parameter_list|()
block|{
name|mainFrame
operator|.
name|menuItemWithPath
argument_list|(
literal|"Tools"
argument_list|,
literal|"OpenOffice/LibreOffice connection"
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|GenericTypeMatcher
argument_list|<
name|JButton
argument_list|>
name|buttonMatcher
init|=
operator|new
name|GenericTypeMatcher
argument_list|<
name|JButton
argument_list|>
argument_list|(
name|JButton
operator|.
name|class
argument_list|)
block|{
annotation|@
name|Override
specifier|protected
name|boolean
name|isMatching
parameter_list|(
annotation|@
name|Nonnull
name|JButton
name|jButton
parameter_list|)
block|{
return|return
literal|"Select style"
operator|.
name|equals
argument_list|(
name|jButton
operator|.
name|getText
argument_list|()
argument_list|)
return|;
block|}
block|}
decl_stmt|;
name|mainFrame
operator|.
name|button
argument_list|(
name|buttonMatcher
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|GenericTypeMatcher
argument_list|<
name|JDialog
argument_list|>
name|styleDialogMatcher
init|=
operator|new
name|GenericTypeMatcher
argument_list|<
name|JDialog
argument_list|>
argument_list|(
name|JDialog
operator|.
name|class
argument_list|)
block|{
annotation|@
name|Override
specifier|protected
name|boolean
name|isMatching
parameter_list|(
name|JDialog
name|dialog
parameter_list|)
block|{
return|return
literal|"Select style"
operator|.
name|equals
argument_list|(
name|dialog
operator|.
name|getTitle
argument_list|()
argument_list|)
return|;
comment|// Only a single SidePane
block|}
block|}
decl_stmt|;
name|findDialog
argument_list|(
name|styleDialogMatcher
argument_list|)
operator|.
name|withTimeout
argument_list|(
literal|10_000
argument_list|)
operator|.
name|using
argument_list|(
name|robot
argument_list|()
argument_list|)
operator|.
name|close
argument_list|()
expr_stmt|;
name|exitJabRef
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

