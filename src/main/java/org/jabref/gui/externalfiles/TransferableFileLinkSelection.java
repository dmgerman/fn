begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.externalfiles
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|externalfiles
package|;
end_package

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
name|UnsupportedFlavorException
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
name|nio
operator|.
name|file
operator|.
name|Path
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
name|org
operator|.
name|jabref
operator|.
name|Globals
import|;
end_import

begin_import
import|import
name|org
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
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|BibEntry
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|LinkedFile
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_comment
comment|/**  *  */
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
name|Path
argument_list|>
name|fileList
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|TransferableFileLinkSelection
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|method|TransferableFileLinkSelection (BasePanel panel, List<BibEntry> selection)
specifier|public
name|TransferableFileLinkSelection
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|List
argument_list|<
name|BibEntry
argument_list|>
name|selection
parameter_list|)
block|{
name|BibEntry
name|entry
init|=
name|selection
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|LinkedFile
argument_list|>
name|files
init|=
name|entry
operator|.
name|getFiles
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|files
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// Find the default directory for this field type, if any:
name|LinkedFile
name|firstFile
init|=
name|files
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|firstFile
operator|.
name|findIn
argument_list|(
name|panel
operator|.
name|getDatabaseContext
argument_list|()
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getFileDirectoryPreferences
argument_list|()
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|fileList
operator|::
name|add
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
name|LOGGER
operator|.
name|debug
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
comment|/*     private StringSelection ss;      public TransferableFileLinkSelection(BasePanel panel, BibEntry[] selection) {         String s = selection[0].getField(GUIGlobals.FILE_FIELD);         FileListTableModel tm = new FileListTableModel();         if (s != null)             tm.setContent(s);         if (tm.getRowCount()> 0) {             // Find the default directory for this field type, if any:             String dir = panel.metaData().getFileDirectory(GUIGlobals.FILE_FIELD);             // Include the standard "file" directory:             String fileDir = panel.metaData().getFileDirectory(GUIGlobals.FILE_FIELD);             // Include the directory of the BIB file:             String[] dirs;             if (panel.metaData().getDatabaseFile() != null) {                 String databaseDir = panel.metaData().getDatabaseFile().getParent();                 dirs = new String[] { dir, fileDir, databaseDir };             }             else                 dirs = new String[] { dir, fileDir };             System.out.println(tm.getEntry(0).getLink());             for (int i = 0; i< dirs.length; i++) {                 String dir1 = dirs[i];                 System.out.println("dir:"+dir1);             }             File expLink = Util.expandFilename(tm.getEntry(0).getLink(), dirs);             try {                 System.out.println(expLink.toURI().toURL().toString());                 ss = new StringSelection(expLink.toURI().toURL().toString());              } catch (MalformedURLException ex) {                 ss = new StringSelection("");             }         }         else             ss = new StringSelection("");      }      public Transferable getTransferable() {         return ss;     } */
block|}
end_class

end_unit

