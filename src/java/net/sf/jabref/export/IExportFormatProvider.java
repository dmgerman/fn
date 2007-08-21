begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.export
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_comment
comment|/**  * @author kariem  */
end_comment

begin_interface
DECL|interface|IExportFormatProvider
specifier|public
interface|interface
name|IExportFormatProvider
block|{
comment|/** 	 * @return a list of export formats 	 */
DECL|method|getExportFormats ()
name|List
argument_list|<
name|IExportFormat
argument_list|>
name|getExportFormats
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

