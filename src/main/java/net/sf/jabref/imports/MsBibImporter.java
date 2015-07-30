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
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|DocumentBuilder
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
name|DocumentBuilderFactory
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

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|msbib
operator|.
name|MSBibDatabase
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
name|Document
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
comment|/*             This method is available for checking if a file can be of the MSBib type.             The effect of this method is primarily to avoid unnecessary processing of             files when searching for a suitable import format. If this method returns             false, the import routine will move on to the next import format.              The correct behaviour is to return false if it is certain that the file is             not of the MsBib type, and true otherwise. Returning true is the safe choice             if not certain.          */
name|Document
name|docin
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
operator|!
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
condition|)
block|{
return|return
literal|false
return|;
block|}
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
comment|/**      * String used to identify this import filter on the command line.      * @return "msbib"      */
DECL|method|getCommandLineId ()
specifier|public
name|String
name|getCommandLineId
parameter_list|()
block|{
return|return
literal|"msbib"
return|;
block|}
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
name|MSBibDatabase
name|dbase
init|=
operator|new
name|MSBibDatabase
argument_list|()
decl_stmt|;
return|return
name|dbase
operator|.
name|importEntries
argument_list|(
name|in
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
comment|// This method should return the name of this import format.
return|return
literal|"MSBib"
return|;
block|}
block|}
end_class

end_unit

