begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  *  This file is part of JabRef.  *  *  JabRef is free software: you can redistribute it and/or modify  *  it under the terms of the GNU General Public License as published by  *  the Free Software Foundation, either version 3 of the License, or  *  (at your option) any later version.  *  *  JabRef is distributed in the hope that it will be useful,  *  but WITHOUT ANY WARRANTY; without even the implied warranty of  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  *  GNU General Public License for more details.  *  *  You should have received a copy of the GNU General Public License  *  along with JAbRef.  If not, see<http://www.gnu.org/licenses/>.   */
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
name|io
operator|.
name|InputStreamReader
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
name|OutputPrinter
import|;
end_import

begin_comment
comment|/**  * Imported requried to support --importToOpen someEntry.bib   */
end_comment

begin_class
DECL|class|BibtexImporter
specifier|public
class|class
name|BibtexImporter
extends|extends
name|ImportFormat
block|{
comment|/**      * @return false as that does not cause any harm in the current implementation of JabRef      */
annotation|@
name|Override
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
name|BibtexParser
operator|.
name|isRecognizedFormat
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
name|in
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * Parses the given input stream.      * Only plain bibtex entries are returned.      * That especially means that metadata is ignored.      *       * @param in the inputStream to read from      * @param status the OutputPrinter to put status to      * @return a list of BibTeX entries contained in the given inputStream      */
annotation|@
name|Override
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
name|ParserResult
name|pr
init|=
name|BibtexParser
operator|.
name|parse
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
name|in
argument_list|)
argument_list|)
decl_stmt|;
return|return
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|pr
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getFormatName ()
specifier|public
name|String
name|getFormatName
parameter_list|()
block|{
return|return
literal|"BibTeX"
return|;
block|}
annotation|@
name|Override
DECL|method|getExtensions ()
specifier|public
name|String
name|getExtensions
parameter_list|()
block|{
return|return
literal|"bib"
return|;
block|}
block|}
end_class

end_unit

