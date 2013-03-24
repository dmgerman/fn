begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

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
name|BibtexEntry
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
name|OutputPrinter
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
DECL|method|importEntries (InputStream in, OutputPrinter status)
specifier|public
name|List
argument_list|<
name|BibtexEntry
argument_list|>
name|importEntries
parameter_list|(
name|InputStream
name|in
parameter_list|,
name|OutputPrinter
name|status
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

