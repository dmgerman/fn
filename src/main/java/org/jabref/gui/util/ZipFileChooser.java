begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.util
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|util
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
name|nio
operator|.
name|file
operator|.
name|FileSystem
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
name|Files
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
name|time
operator|.
name|ZoneId
import|;
end_import

begin_import
import|import
name|java
operator|.
name|time
operator|.
name|ZonedDateTime
import|;
end_import

begin_import
import|import
name|java
operator|.
name|time
operator|.
name|format
operator|.
name|DateTimeFormatter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|time
operator|.
name|format
operator|.
name|FormatStyle
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|ReadOnlyLongWrapper
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|ReadOnlyStringWrapper
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|collections
operator|.
name|FXCollections
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|collections
operator|.
name|ObservableList
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|ButtonType
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|SelectionMode
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|TableColumn
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|TableView
import|;
end_import

begin_import
import|import
name|org
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

begin_comment
comment|/**  * Dialog to allow users to choose a file contained in a ZIP file.  */
end_comment

begin_class
DECL|class|ZipFileChooser
specifier|public
class|class
name|ZipFileChooser
extends|extends
name|BaseDialog
argument_list|<
name|Path
argument_list|>
block|{
comment|/**      * New ZIP file chooser.      *      * @param zipFile ZIP-Fle to choose from, must be readable      */
DECL|method|ZipFileChooser (FileSystem zipFile)
specifier|public
name|ZipFileChooser
parameter_list|(
name|FileSystem
name|zipFile
parameter_list|)
throws|throws
name|IOException
block|{
name|setTitle
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Select file from ZIP-archive"
argument_list|)
argument_list|)
expr_stmt|;
name|TableView
argument_list|<
name|Path
argument_list|>
name|table
init|=
operator|new
name|TableView
argument_list|<>
argument_list|(
name|getSelectableZipEntries
argument_list|(
name|zipFile
argument_list|)
argument_list|)
decl_stmt|;
name|TableColumn
argument_list|<
name|Path
argument_list|,
name|String
argument_list|>
name|nameColumn
init|=
operator|new
name|TableColumn
argument_list|<>
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Name"
argument_list|)
argument_list|)
decl_stmt|;
name|TableColumn
argument_list|<
name|Path
argument_list|,
name|String
argument_list|>
name|modifiedColumn
init|=
operator|new
name|TableColumn
argument_list|<>
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Last modified"
argument_list|)
argument_list|)
decl_stmt|;
name|TableColumn
argument_list|<
name|Path
argument_list|,
name|Number
argument_list|>
name|sizeColumn
init|=
operator|new
name|TableColumn
argument_list|<>
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Size"
argument_list|)
argument_list|)
decl_stmt|;
name|table
operator|.
name|getColumns
argument_list|()
operator|.
name|add
argument_list|(
name|nameColumn
argument_list|)
expr_stmt|;
name|table
operator|.
name|getColumns
argument_list|()
operator|.
name|add
argument_list|(
name|modifiedColumn
argument_list|)
expr_stmt|;
name|table
operator|.
name|getColumns
argument_list|()
operator|.
name|add
argument_list|(
name|sizeColumn
argument_list|)
expr_stmt|;
name|nameColumn
operator|.
name|setCellValueFactory
argument_list|(
name|data
lambda|->
operator|new
name|ReadOnlyStringWrapper
argument_list|(
name|data
operator|.
name|getValue
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|modifiedColumn
operator|.
name|setCellValueFactory
argument_list|(
name|data
lambda|->
block|{
try|try
block|{
return|return
operator|new
name|ReadOnlyStringWrapper
argument_list|(
name|ZonedDateTime
operator|.
name|ofInstant
argument_list|(
name|Files
operator|.
name|getLastModifiedTime
argument_list|(
name|data
operator|.
name|getValue
argument_list|()
argument_list|)
operator|.
name|toInstant
argument_list|()
argument_list|,
name|ZoneId
operator|.
name|systemDefault
argument_list|()
argument_list|)
operator|.
name|format
argument_list|(
name|DateTimeFormatter
operator|.
name|ofLocalizedDateTime
argument_list|(
name|FormatStyle
operator|.
name|MEDIUM
argument_list|)
argument_list|)
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
comment|// Ignore
return|return
operator|new
name|ReadOnlyStringWrapper
argument_list|(
literal|""
argument_list|)
return|;
block|}
block|}
argument_list|)
expr_stmt|;
name|sizeColumn
operator|.
name|setCellValueFactory
argument_list|(
name|data
lambda|->
block|{
try|try
block|{
return|return
operator|new
name|ReadOnlyLongWrapper
argument_list|(
name|Files
operator|.
name|size
argument_list|(
name|data
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
comment|// Ignore
return|return
operator|new
name|ReadOnlyLongWrapper
argument_list|(
literal|0
argument_list|)
return|;
block|}
block|}
argument_list|)
expr_stmt|;
name|table
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|setSelectionMode
argument_list|(
name|SelectionMode
operator|.
name|SINGLE
argument_list|)
expr_stmt|;
name|getDialogPane
argument_list|()
operator|.
name|setContent
argument_list|(
name|table
argument_list|)
expr_stmt|;
name|getDialogPane
argument_list|()
operator|.
name|getButtonTypes
argument_list|()
operator|.
name|setAll
argument_list|(
name|ButtonType
operator|.
name|OK
argument_list|,
name|ButtonType
operator|.
name|CANCEL
argument_list|)
expr_stmt|;
name|setResultConverter
argument_list|(
name|button
lambda|->
block|{
if|if
condition|(
name|button
operator|==
name|ButtonType
operator|.
name|OK
condition|)
block|{
return|return
name|table
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|getSelectedItem
argument_list|()
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
comment|/**      * Entries that can be selected with this dialog.      *      * @param zipFile ZIP-File      * @return entries that can be selected      */
DECL|method|getSelectableZipEntries (FileSystem zipFile)
specifier|private
specifier|static
name|ObservableList
argument_list|<
name|Path
argument_list|>
name|getSelectableZipEntries
parameter_list|(
name|FileSystem
name|zipFile
parameter_list|)
throws|throws
name|IOException
block|{
name|Path
name|rootDir
init|=
name|zipFile
operator|.
name|getRootDirectories
argument_list|()
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
return|return
name|FXCollections
operator|.
name|observableArrayList
argument_list|(
name|Files
operator|.
name|walk
argument_list|(
name|rootDir
argument_list|)
operator|.
name|filter
argument_list|(
name|file
lambda|->
name|file
operator|.
name|endsWith
argument_list|(
literal|".class"
argument_list|)
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
block|}
end_class

end_unit

