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

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|msbib
operator|.
name|*
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|*
import|;
end_import

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|*
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
name|Document
name|docin
init|=
literal|null
decl_stmt|;
try|try
block|{
name|DocumentBuilder
name|dbuild
init|=
name|DocumentBuilderFactory
operator|.
name|newInstance
argument_list|()
operator|.
name|newDocumentBuilder
argument_list|()
decl_stmt|;
name|docin
operator|=
name|dbuild
operator|.
name|parse
argument_list|(
name|in
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
if|if
condition|(
name|docin
operator|!=
literal|null
operator|&&
name|docin
operator|.
name|getDocumentElement
argument_list|()
operator|.
name|getTagName
argument_list|()
operator|.
name|contains
argument_list|(
literal|"Sources"
argument_list|)
operator|==
literal|false
condition|)
return|return
literal|false
return|;
comment|//   		NodeList rootLst = docin.getElementsByTagName("b:Sources");
comment|//   		if(rootLst.getLength()==0)
comment|//   			rootLst = docin.getElementsByTagName("Sources");
comment|//   		if(rootLst.getLength()==0)
comment|//   			return false;
comment|// System.out.println(docin.getDocumentElement().getTagName());
return|return
literal|true
return|;
block|}
comment|/** 	 * String used to identify this import filter on the command line. 	 *  	 * @override 	 * @return "msbib" 	 */
DECL|method|getCLIid ()
specifier|public
name|String
name|getCLIid
parameter_list|()
block|{
return|return
literal|"msbib"
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
name|MSBibDatabase
name|dbase
init|=
operator|new
name|MSBibDatabase
argument_list|()
decl_stmt|;
name|List
name|entries
init|=
name|dbase
operator|.
name|importEntries
argument_list|(
name|in
argument_list|)
decl_stmt|;
return|return
name|entries
return|;
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

