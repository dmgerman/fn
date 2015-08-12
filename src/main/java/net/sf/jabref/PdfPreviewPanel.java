begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2012 Meltem Meltem DemirkÃ¶prÃ¼, Ahmad Hammoud, Oliver Kopp      This program is free software: you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation, either version 3 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License     along with this program.  If not, see<http://www.gnu.org/licenses/>. */
end_comment

begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Graphics2D
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|image
operator|.
name|BufferedImage
import|;
end_import

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
name|FileInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileNotFoundException
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
name|swing
operator|.
name|ImageIcon
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JLabel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JPanel
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
name|gui
operator|.
name|FileListEntry
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
name|gui
operator|.
name|FileListTableModel
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
name|l10n
operator|.
name|Localization
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
name|FileUtil
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|pdfbox
operator|.
name|pdmodel
operator|.
name|PDDocument
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|pdfbox
operator|.
name|pdmodel
operator|.
name|PDPage
import|;
end_import

begin_class
DECL|class|PdfPreviewPanel
class|class
name|PdfPreviewPanel
extends|extends
name|JPanel
block|{
DECL|field|picLabel
specifier|private
specifier|final
name|JLabel
name|picLabel
decl_stmt|;
DECL|field|metaData
specifier|private
specifier|final
name|MetaData
name|metaData
decl_stmt|;
DECL|method|PdfPreviewPanel (MetaData metaData)
specifier|public
name|PdfPreviewPanel
parameter_list|(
name|MetaData
name|metaData
parameter_list|)
block|{
name|this
operator|.
name|metaData
operator|=
name|metaData
expr_stmt|;
name|picLabel
operator|=
operator|new
name|JLabel
argument_list|()
expr_stmt|;
name|add
argument_list|(
name|picLabel
argument_list|)
expr_stmt|;
block|}
DECL|method|renderPDFFile (File file)
specifier|private
name|void
name|renderPDFFile
parameter_list|(
name|File
name|file
parameter_list|)
block|{
name|InputStream
name|input
decl_stmt|;
try|try
block|{
name|input
operator|=
operator|new
name|FileInputStream
argument_list|(
name|file
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|FileNotFoundException
name|e
parameter_list|)
block|{
comment|// TODO Auto-generated catch block
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
return|return;
block|}
name|PDDocument
name|document
decl_stmt|;
try|try
block|{
name|document
operator|=
name|PDDocument
operator|.
name|load
argument_list|(
name|input
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
comment|// TODO Auto-generated catch block
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
return|return;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
name|List
argument_list|<
name|PDPage
argument_list|>
name|pages
init|=
name|document
operator|.
name|getDocumentCatalog
argument_list|()
operator|.
name|getAllPages
argument_list|()
decl_stmt|;
name|PDPage
name|page
init|=
name|pages
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|BufferedImage
name|image
decl_stmt|;
try|try
block|{
name|image
operator|=
name|page
operator|.
name|convertToImage
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e1
parameter_list|)
block|{
comment|// silently ignores all rendering exceptions
name|image
operator|=
literal|null
expr_stmt|;
block|}
if|if
condition|(
name|image
operator|!=
literal|null
condition|)
block|{
name|int
name|width
init|=
name|this
operator|.
name|getParent
argument_list|()
operator|.
name|getWidth
argument_list|()
decl_stmt|;
name|int
name|height
init|=
name|this
operator|.
name|getParent
argument_list|()
operator|.
name|getHeight
argument_list|()
decl_stmt|;
name|BufferedImage
name|resImage
init|=
name|resizeImage
argument_list|(
name|image
argument_list|,
name|width
argument_list|,
name|height
argument_list|,
name|BufferedImage
operator|.
name|TYPE_INT_RGB
argument_list|)
decl_stmt|;
name|ImageIcon
name|icon
init|=
operator|new
name|ImageIcon
argument_list|(
name|resImage
argument_list|)
decl_stmt|;
name|picLabel
operator|.
name|setText
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|picLabel
operator|.
name|setIcon
argument_list|(
name|icon
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|clearPreview
argument_list|()
expr_stmt|;
block|}
try|try
block|{
name|document
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
comment|// TODO Auto-generated catch block
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|resizeImage (BufferedImage originalImage, int width, int height, int type)
specifier|private
name|BufferedImage
name|resizeImage
parameter_list|(
name|BufferedImage
name|originalImage
parameter_list|,
name|int
name|width
parameter_list|,
name|int
name|height
parameter_list|,
name|int
name|type
parameter_list|)
block|{
name|int
name|h
init|=
name|originalImage
operator|.
name|getHeight
argument_list|()
decl_stmt|;
name|int
name|w
init|=
name|originalImage
operator|.
name|getWidth
argument_list|()
decl_stmt|;
if|if
condition|(
name|height
operator|==
literal|0
operator|||
name|width
operator|==
literal|0
condition|)
block|{
name|height
operator|=
name|h
expr_stmt|;
name|width
operator|=
name|w
expr_stmt|;
block|}
else|else
block|{
name|float
name|factorH
init|=
operator|(
name|float
operator|)
name|height
operator|/
operator|(
name|float
operator|)
name|h
decl_stmt|;
name|float
name|factorW
init|=
operator|(
name|float
operator|)
name|width
operator|/
operator|(
name|float
operator|)
name|w
decl_stmt|;
if|if
condition|(
name|factorH
operator|<
name|factorW
condition|)
block|{
comment|// use factorH, only width has to be changed as height is
comment|// already correct
name|width
operator|=
name|Math
operator|.
name|round
argument_list|(
name|w
operator|*
name|factorH
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|width
operator|=
name|Math
operator|.
name|round
argument_list|(
name|h
operator|*
name|factorW
argument_list|)
expr_stmt|;
block|}
block|}
name|BufferedImage
name|resizedImage
init|=
operator|new
name|BufferedImage
argument_list|(
name|width
argument_list|,
name|height
argument_list|,
name|type
argument_list|)
decl_stmt|;
name|Graphics2D
name|g
init|=
name|resizedImage
operator|.
name|createGraphics
argument_list|()
decl_stmt|;
name|g
operator|.
name|drawImage
argument_list|(
name|originalImage
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
name|width
argument_list|,
name|height
argument_list|,
literal|null
argument_list|)
expr_stmt|;
return|return
name|resizedImage
return|;
block|}
DECL|method|updatePanel (BibtexEntry entry)
specifier|public
name|void
name|updatePanel
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
if|if
condition|(
name|entry
operator|==
literal|null
condition|)
block|{
name|clearPreview
argument_list|()
expr_stmt|;
return|return;
block|}
name|picLabel
operator|.
name|setText
argument_list|(
literal|"rendering preview..."
argument_list|)
expr_stmt|;
name|picLabel
operator|.
name|setIcon
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|FileListTableModel
name|tm
init|=
operator|new
name|FileListTableModel
argument_list|()
decl_stmt|;
name|tm
operator|.
name|setContent
argument_list|(
name|entry
operator|.
name|getField
argument_list|(
literal|"file"
argument_list|)
argument_list|)
expr_stmt|;
name|FileListEntry
name|flEntry
init|=
literal|null
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|tm
operator|.
name|getRowCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|flEntry
operator|=
name|tm
operator|.
name|getEntry
argument_list|(
name|i
argument_list|)
expr_stmt|;
if|if
condition|(
name|flEntry
operator|.
name|getType
argument_list|()
operator|.
name|getName
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|.
name|equals
argument_list|(
literal|"pdf"
argument_list|)
condition|)
block|{
break|break;
block|}
block|}
if|if
condition|(
name|flEntry
operator|!=
literal|null
condition|)
block|{
name|File
name|pdfFile
init|=
name|FileUtil
operator|.
name|expandFilename
argument_list|(
name|metaData
argument_list|,
name|flEntry
operator|.
name|getLink
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|pdfFile
operator|!=
literal|null
condition|)
block|{
name|renderPDFFile
argument_list|(
name|pdfFile
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|clearPreview
argument_list|()
expr_stmt|;
block|}
block|}
else|else
block|{
name|clearPreview
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|clearPreview ()
specifier|private
name|void
name|clearPreview
parameter_list|()
block|{
name|this
operator|.
name|picLabel
operator|.
name|setIcon
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|this
operator|.
name|picLabel
operator|.
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"no preview available"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

