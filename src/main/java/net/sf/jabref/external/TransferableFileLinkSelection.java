begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.external
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|external
package|;
end_package

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
name|util
operator|.
name|FileUtil
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
name|GUIGlobals
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
name|BasePanel
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
name|java
operator|.
name|awt
operator|.
name|datatransfer
operator|.
name|Transferable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|datatransfer
operator|.
name|DataFlavor
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|datatransfer
operator|.
name|UnsupportedFlavorException
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
name|IOException
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
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_comment
comment|/**  *   */
end_comment

begin_class
DECL|class|TransferableFileLinkSelection
specifier|public
class|class
name|TransferableFileLinkSelection
implements|implements
name|Transferable
block|{
DECL|field|fileList
specifier|private
specifier|final
name|List
argument_list|<
name|File
argument_list|>
name|fileList
init|=
operator|new
name|ArrayList
argument_list|<
name|File
argument_list|>
argument_list|()
decl_stmt|;
DECL|method|TransferableFileLinkSelection (BasePanel panel, BibtexEntry[] selection)
specifier|public
name|TransferableFileLinkSelection
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|BibtexEntry
index|[]
name|selection
parameter_list|)
block|{
name|String
name|s
init|=
name|selection
index|[
literal|0
index|]
operator|.
name|getField
argument_list|(
name|GUIGlobals
operator|.
name|FILE_FIELD
argument_list|)
decl_stmt|;
name|FileListTableModel
name|tm
init|=
operator|new
name|FileListTableModel
argument_list|()
decl_stmt|;
if|if
condition|(
name|s
operator|!=
literal|null
condition|)
block|{
name|tm
operator|.
name|setContent
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|tm
operator|.
name|getRowCount
argument_list|()
operator|>
literal|0
condition|)
block|{
comment|// Find the default directory for this field type, if any:
name|String
index|[]
name|dirs
init|=
name|panel
operator|.
name|metaData
argument_list|()
operator|.
name|getFileDirectory
argument_list|(
name|GUIGlobals
operator|.
name|FILE_FIELD
argument_list|)
decl_stmt|;
name|File
name|expLink
init|=
name|FileUtil
operator|.
name|expandFilename
argument_list|(
name|tm
operator|.
name|getEntry
argument_list|(
literal|0
argument_list|)
operator|.
name|getLink
argument_list|()
argument_list|,
name|dirs
argument_list|)
decl_stmt|;
name|fileList
operator|.
name|add
argument_list|(
name|expLink
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|getTransferDataFlavors ()
specifier|public
name|DataFlavor
index|[]
name|getTransferDataFlavors
parameter_list|()
block|{
return|return
operator|new
name|DataFlavor
index|[]
block|{
name|DataFlavor
operator|.
name|javaFileListFlavor
block|}
return|;
comment|//, DataFlavor.stringFlavor};
block|}
annotation|@
name|Override
DECL|method|isDataFlavorSupported (DataFlavor dataFlavor)
specifier|public
name|boolean
name|isDataFlavorSupported
parameter_list|(
name|DataFlavor
name|dataFlavor
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Query: "
operator|+
name|dataFlavor
operator|.
name|getHumanPresentableName
argument_list|()
operator|+
literal|" , "
operator|+
name|dataFlavor
operator|.
name|getDefaultRepresentationClass
argument_list|()
operator|+
literal|" , "
operator|+
name|dataFlavor
operator|.
name|getMimeType
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|dataFlavor
operator|.
name|equals
argument_list|(
name|DataFlavor
operator|.
name|javaFileListFlavor
argument_list|)
operator|||
name|dataFlavor
operator|.
name|equals
argument_list|(
name|DataFlavor
operator|.
name|stringFlavor
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getTransferData (DataFlavor dataFlavor)
specifier|public
name|Object
name|getTransferData
parameter_list|(
name|DataFlavor
name|dataFlavor
parameter_list|)
throws|throws
name|UnsupportedFlavorException
throws|,
name|IOException
block|{
comment|//if (dataFlavor.equals(DataFlavor.javaFileListFlavor))
return|return
name|fileList
return|;
comment|//else
comment|//    return "test";
block|}
comment|/*     private StringSelection ss;      public TransferableFileLinkSelection(BasePanel panel, BibtexEntry[] selection) {         String s = selection[0].getField(GUIGlobals.FILE_FIELD);         FileListTableModel tm = new FileListTableModel();         if (s != null)             tm.setContent(s);         if (tm.getRowCount()> 0) {             // Find the default directory for this field type, if any:             String dir = panel.metaData().getFileDirectory(GUIGlobals.FILE_FIELD);             // Include the standard "file" directory:             String fileDir = panel.metaData().getFileDirectory(GUIGlobals.FILE_FIELD);             // Include the directory of the bib file:             String[] dirs;             if (panel.metaData().getFile() != null) {                 String databaseDir = panel.metaData().getFile().getParent();                 dirs = new String[] { dir, fileDir, databaseDir };             }             else                 dirs = new String[] { dir, fileDir };             System.out.println(tm.getEntry(0).getLink());             for (int i = 0; i< dirs.length; i++) {                 String dir1 = dirs[i];                 System.out.println("dir:"+dir1);             }             File expLink = Util.expandFilename(tm.getEntry(0).getLink(), dirs);             try {                  System.out.println(expLink.toURI().toURL().toString());                 ss = new StringSelection(expLink.toURI().toURL().toString());                              } catch (MalformedURLException ex) {                 ss = new StringSelection("");             }         }         else             ss = new StringSelection("");      }      public Transferable getTransferable() {         return ss;     } */
block|}
end_class

end_unit

