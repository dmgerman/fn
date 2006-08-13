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
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
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
name|Globals
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
name|util
operator|.
name|XMPUtil
import|;
end_import

begin_comment
comment|/**  * Wraps the XMPUtility function to be used as an ImportFormat.  *   * @author $Author$  * @version $Revision$ ($Date$)  *   */
end_comment

begin_class
DECL|class|PdfXmpImporter
specifier|public
class|class
name|PdfXmpImporter
extends|extends
name|ImportFormat
block|{
DECL|method|getFormatName ()
specifier|public
name|String
name|getFormatName
parameter_list|()
block|{
return|return
name|Globals
operator|.
name|lang
argument_list|(
literal|"XMP-annotated PDF"
argument_list|)
return|;
block|}
comment|/** 	 * Returns a list of all BibtexEntries found in the inputstream. 	 */
DECL|method|importEntries (InputStream in)
specifier|public
name|List
name|importEntries
parameter_list|(
name|InputStream
name|in
parameter_list|)
throws|throws
name|IOException
block|{
return|return
name|XMPUtil
operator|.
name|readXMP
argument_list|(
name|in
argument_list|)
return|;
block|}
comment|/** 	 * Returns whether the given stream contains data that is a.) a pdf and b.) 	 * contains at least one BibtexEntry. 	 *  	 * @override 	 */
DECL|method|isRecognizedFormat (InputStream in)
specifier|public
name|boolean
name|isRecognizedFormat
parameter_list|(
name|InputStream
name|in
parameter_list|)
throws|throws
name|IOException
block|{
return|return
name|XMPUtil
operator|.
name|hasMetadata
argument_list|(
name|in
argument_list|)
return|;
block|}
comment|/** 	 * String used to identify this import filter on the command line. 	 *  	 * @override 	 * @return "xmp" 	 */
DECL|method|getCLIid ()
specifier|public
name|String
name|getCLIid
parameter_list|()
block|{
return|return
literal|"xmp"
return|;
block|}
block|}
end_class

end_unit

