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
name|util
operator|.
name|List
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
name|io
operator|.
name|IOException
import|;
end_import

begin_comment
comment|/**  * Importer for the MS Office 2007 XML bibliography format  * By S. M. Mahbub Murshed  *  * ...  */
end_comment

begin_class
DECL|class|MsBibImporter
specifier|public
class|class
name|MsBibImporter
extends|extends
name|ImportFormat
block|{
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
comment|/*             This method is available for checking if a file can be of the MSBib type.             The effect of this method is primarily to avoid unnecessary processing of             files when searching for a suitable import format. If this method returns             false, the import routine will move on to the next import format.              The correct behaviour is to return false if it is certain that the file is             not of the MsBib type, and true otherwise. Returning true is the safe choice             if not certain.          */
return|return
literal|true
return|;
block|}
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
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Hello world MsBibImporter"
argument_list|)
expr_stmt|;
name|List
name|entries
init|=
operator|new
name|ArrayList
argument_list|()
decl_stmt|;
comment|/*             This method should read the input stream until the end, and add any entries             found to a List which is returned. If the stream doesn't contain any useful             data (is of the wrong format?) you can return null to signal that this is the             wrong import filter.          */
return|return
literal|null
return|;
comment|//return entries;
block|}
DECL|method|getFormatName ()
specifier|public
name|String
name|getFormatName
parameter_list|()
block|{
comment|// This method should return the name of this import format.
return|return
literal|"MSBib"
return|;
block|}
block|}
end_class

end_unit

