begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.imports
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
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
name|JTextField
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
name|TestUtils
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
name|JabRef
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
name|JabRefFrame
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
name|SidePaneManager
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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_comment
comment|/**  * Tests GeneralFetcher  *   * @author Dennis Hartrampf, Ines Moosdorf  */
end_comment

begin_class
DECL|class|GeneralFetcherTest
specifier|public
class|class
name|GeneralFetcherTest
extends|extends
name|TestCase
block|{
DECL|field|jrf
specifier|static
name|JabRefFrame
name|jrf
decl_stmt|;
DECL|field|spm
specifier|static
name|SidePaneManager
name|spm
decl_stmt|;
DECL|field|gf
specifier|static
name|GeneralFetcher
name|gf
decl_stmt|;
DECL|field|acmpf
specifier|static
name|ACMPortalFetcher
name|acmpf
decl_stmt|;
comment|/** 	 * Tests the reset-button. Types a text into tf, pushs reset and check tf's 	 * text 	 *  	 * @throws Exception 	 */
DECL|method|testResetButton ()
specifier|public
name|void
name|testResetButton
parameter_list|()
throws|throws
name|Exception
block|{
name|String
name|testString
init|=
literal|"test string"
decl_stmt|;
name|JTextField
name|tf
init|=
operator|(
name|JTextField
operator|)
name|TestUtils
operator|.
name|getChildNamed
argument_list|(
name|gf
argument_list|,
literal|"tf"
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|tf
argument_list|)
expr_stmt|;
comment|// tf found?
name|tf
operator|.
name|setText
argument_list|(
name|testString
argument_list|)
expr_stmt|;
name|tf
operator|.
name|postActionEvent
argument_list|()
expr_stmt|;
comment|// send message
name|assertEquals
argument_list|(
name|testString
argument_list|,
name|tf
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|JButton
name|reset
init|=
operator|(
name|JButton
operator|)
name|TestUtils
operator|.
name|getChildNamed
argument_list|(
name|gf
argument_list|,
literal|"reset"
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|reset
argument_list|)
expr_stmt|;
comment|// reset found?
name|reset
operator|.
name|doClick
argument_list|()
expr_stmt|;
comment|// "click" reset
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|tf
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Get an instance of JabRef via its singleton and get a GeneralFetcher and an ACMPortalFetcher 	 */
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|JabRef
operator|.
name|main
argument_list|(
operator|new
name|String
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|jrf
operator|=
name|JabRef
operator|.
name|singleton
operator|.
name|jrf
expr_stmt|;
name|spm
operator|=
name|jrf
operator|.
name|sidePaneManager
expr_stmt|;
name|acmpf
operator|=
operator|new
name|ACMPortalFetcher
argument_list|()
expr_stmt|;
name|ArrayList
argument_list|<
name|EntryFetcher
argument_list|>
name|al
init|=
operator|new
name|ArrayList
argument_list|<
name|EntryFetcher
argument_list|>
argument_list|()
decl_stmt|;
name|al
operator|.
name|add
argument_list|(
name|acmpf
argument_list|)
expr_stmt|;
name|gf
operator|=
operator|new
name|GeneralFetcher
argument_list|(
name|spm
argument_list|,
name|jrf
argument_list|,
name|al
argument_list|)
expr_stmt|;
block|}
DECL|method|tearDown ()
specifier|public
name|void
name|tearDown
parameter_list|()
block|{
name|gf
operator|=
literal|null
expr_stmt|;
name|acmpf
operator|=
literal|null
expr_stmt|;
name|spm
operator|=
literal|null
expr_stmt|;
name|jrf
operator|=
literal|null
expr_stmt|;
block|}
block|}
end_class

end_unit

