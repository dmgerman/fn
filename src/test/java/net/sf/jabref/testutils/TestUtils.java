begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.testutils
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|testutils
package|;
end_package

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

begin_comment
comment|/**  * UtilsClass for UnitTests.  *  * @author kahlert, cordes  */
end_comment

begin_class
DECL|class|TestUtils
specifier|public
class|class
name|TestUtils
block|{
DECL|field|PATH_TO_TEST_BIBTEX
specifier|public
specifier|static
specifier|final
name|String
name|PATH_TO_TEST_BIBTEX
init|=
literal|"src/test/resources/net/sf/jabref/bibtexFiles/test.bib"
decl_stmt|;
comment|/**      * Initialize JabRef. Can be cleaned up with      * {@link TestUtils#closeJabRef()}      *      * @see TestUtils#closeJabRef()      */
DECL|method|initJabRef ()
specifier|public
specifier|static
name|void
name|initJabRef
parameter_list|()
block|{
name|String
index|[]
name|args
init|=
block|{
literal|"-p"
block|,
literal|" "
block|,
name|TestUtils
operator|.
name|PATH_TO_TEST_BIBTEX
block|}
decl_stmt|;
name|JabRef
operator|.
name|main
argument_list|(
name|args
argument_list|)
expr_stmt|;
block|}
comment|/**      * Closes the current instance of JabRef.      */
DECL|method|closeJabRef ()
specifier|public
specifier|static
name|void
name|closeJabRef
parameter_list|()
block|{
name|JabRef
name|jabref
init|=
name|JabRef
operator|.
name|singleton
decl_stmt|;
if|if
condition|(
name|jabref
operator|!=
literal|null
condition|)
block|{
name|JabRef
operator|.
name|jrf
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

