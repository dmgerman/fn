begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

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
name|io
operator|.
name|File
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
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|transform
operator|.
name|OutputKeys
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|transform
operator|.
name|Transformer
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|transform
operator|.
name|TransformerFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|transform
operator|.
name|dom
operator|.
name|DOMSource
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|transform
operator|.
name|stream
operator|.
name|StreamResult
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
name|BibtexDatabase
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
name|MetaData
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
name|MSBibDatabase
import|;
end_import

begin_comment
comment|/**  * ExportFormat for exporting in MSBIB XML format.  */
end_comment

begin_class
DECL|class|MSBibExportFormat
class|class
name|MSBibExportFormat
extends|extends
name|ExportFormat
block|{
DECL|method|MSBibExportFormat ()
specifier|public
name|MSBibExportFormat
parameter_list|()
block|{
name|super
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"MS Office 2007"
argument_list|)
argument_list|,
literal|"MSBib"
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
literal|".xml"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|performExport (final BibtexDatabase database, final MetaData metaData, final String file, final String encoding, Set<String> keySet)
specifier|public
name|void
name|performExport
parameter_list|(
specifier|final
name|BibtexDatabase
name|database
parameter_list|,
specifier|final
name|MetaData
name|metaData
parameter_list|,
specifier|final
name|String
name|file
parameter_list|,
specifier|final
name|String
name|encoding
parameter_list|,
name|Set
argument_list|<
name|String
argument_list|>
name|keySet
parameter_list|)
throws|throws
name|IOException
block|{
comment|// forcing to use UTF8 output format for some problems with
comment|// xml export in other encodings
name|SaveSession
name|ss
init|=
name|getSaveSession
argument_list|(
literal|"UTF8"
argument_list|,
operator|new
name|File
argument_list|(
name|file
argument_list|)
argument_list|)
decl_stmt|;
name|VerifyingWriter
name|ps
init|=
name|ss
operator|.
name|getWriter
argument_list|()
decl_stmt|;
name|MSBibDatabase
name|md
init|=
operator|new
name|MSBibDatabase
argument_list|(
name|database
argument_list|,
name|keySet
argument_list|)
decl_stmt|;
comment|// PS: DOES NOT SUPPORT EXPORTING ONLY A SET OF ENTRIES
try|try
block|{
name|DOMSource
name|source
init|=
operator|new
name|DOMSource
argument_list|(
name|md
operator|.
name|getDOMrepresentation
argument_list|()
argument_list|)
decl_stmt|;
name|StreamResult
name|result
init|=
operator|new
name|StreamResult
argument_list|(
name|ps
argument_list|)
decl_stmt|;
name|Transformer
name|trans
init|=
name|TransformerFactory
operator|.
name|newInstance
argument_list|()
operator|.
name|newTransformer
argument_list|()
decl_stmt|;
name|trans
operator|.
name|setOutputProperty
argument_list|(
name|OutputKeys
operator|.
name|INDENT
argument_list|,
literal|"yes"
argument_list|)
expr_stmt|;
name|trans
operator|.
name|transform
argument_list|(
name|source
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|Error
argument_list|(
name|e
argument_list|)
throw|;
block|}
try|try
block|{
name|finalizeSaveSession
argument_list|(
name|ss
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit

