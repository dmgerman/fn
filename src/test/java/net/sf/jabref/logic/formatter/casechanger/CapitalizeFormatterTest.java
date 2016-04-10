begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.formatter.casechanger
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
name|casechanger
package|;
end_package

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
name|Test
import|;
end_import

begin_comment
comment|/**  * Tests in addition to the general tests from {@link net.sf.jabref.logic.formatter.FormatterTest}  */
end_comment

begin_class
DECL|class|CapitalizeFormatterTest
specifier|public
class|class
name|CapitalizeFormatterTest
block|{
DECL|field|formatter
specifier|private
specifier|final
name|CapitalizeFormatter
name|formatter
init|=
operator|new
name|CapitalizeFormatter
argument_list|()
decl_stmt|;
annotation|@
name|Test
DECL|method|test ()
specifier|public
name|void
name|test
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Upper Each First"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"upper each First"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Upper Each First {NOT} {this}"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"upper each first {NOT} {this}"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Upper Each First {N}ot {t}his"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"upper each first {N}OT {t}his"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

