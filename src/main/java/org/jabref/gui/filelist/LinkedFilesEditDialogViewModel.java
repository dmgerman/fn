begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.filelist
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|filelist
package|;
end_package

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
name|nio
operator|.
name|file
operator|.
name|Paths
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
name|Optional
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
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
name|ListProperty
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
name|ObjectProperty
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
name|SimpleListProperty
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
name|SimpleObjectProperty
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
name|SimpleStringProperty
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
name|StringProperty
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
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|AbstractViewModel
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
name|DialogService
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
name|externalfiletype
operator|.
name|ExternalFileType
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
name|externalfiletype
operator|.
name|ExternalFileTypes
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
name|externalfiletype
operator|.
name|UnknownExternalFileType
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
name|util
operator|.
name|FileDialogConfiguration
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
name|util
operator|.
name|io
operator|.
name|FileUtil
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
name|database
operator|.
name|BibDatabaseContext
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
name|jabref
operator|.
name|model
operator|.
name|util
operator|.
name|FileHelper
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|preferences
operator|.
name|PreferencesService
import|;
end_import

begin_import
import|import
name|org
operator|.
name|fxmisc
operator|.
name|easybind
operator|.
name|EasyBind
import|;
end_import

begin_import
import|import
name|org
operator|.
name|fxmisc
operator|.
name|easybind
operator|.
name|monadic
operator|.
name|MonadicObservableValue
import|;
end_import

begin_class
DECL|class|LinkedFilesEditDialogViewModel
specifier|public
class|class
name|LinkedFilesEditDialogViewModel
extends|extends
name|AbstractViewModel
block|{
DECL|field|REMOTE_LINK_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|REMOTE_LINK_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"[a-z]+://.*"
argument_list|)
decl_stmt|;
DECL|field|link
specifier|private
specifier|final
name|StringProperty
name|link
init|=
operator|new
name|SimpleStringProperty
argument_list|(
literal|""
argument_list|)
decl_stmt|;
DECL|field|description
specifier|private
specifier|final
name|StringProperty
name|description
init|=
operator|new
name|SimpleStringProperty
argument_list|(
literal|""
argument_list|)
decl_stmt|;
DECL|field|allExternalFileTypes
specifier|private
specifier|final
name|ListProperty
argument_list|<
name|ExternalFileType
argument_list|>
name|allExternalFileTypes
init|=
operator|new
name|SimpleListProperty
argument_list|<>
argument_list|(
name|FXCollections
operator|.
name|emptyObservableList
argument_list|()
argument_list|)
decl_stmt|;
DECL|field|selectedExternalFileType
specifier|private
specifier|final
name|ObjectProperty
argument_list|<
name|ExternalFileType
argument_list|>
name|selectedExternalFileType
init|=
operator|new
name|SimpleObjectProperty
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|monadicSelectedExternalFileType
specifier|private
specifier|final
name|MonadicObservableValue
argument_list|<
name|ExternalFileType
argument_list|>
name|monadicSelectedExternalFileType
decl_stmt|;
DECL|field|database
specifier|private
specifier|final
name|BibDatabaseContext
name|database
decl_stmt|;
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
decl_stmt|;
DECL|field|preferences
specifier|private
specifier|final
name|PreferencesService
name|preferences
decl_stmt|;
DECL|field|externalFileTypes
specifier|private
specifier|final
name|ExternalFileTypes
name|externalFileTypes
decl_stmt|;
DECL|method|LinkedFilesEditDialogViewModel (LinkedFile linkedFile, BibDatabaseContext database, DialogService dialogService, PreferencesService preferences, ExternalFileTypes externalFileTypes)
specifier|public
name|LinkedFilesEditDialogViewModel
parameter_list|(
name|LinkedFile
name|linkedFile
parameter_list|,
name|BibDatabaseContext
name|database
parameter_list|,
name|DialogService
name|dialogService
parameter_list|,
name|PreferencesService
name|preferences
parameter_list|,
name|ExternalFileTypes
name|externalFileTypes
parameter_list|)
block|{
name|this
operator|.
name|database
operator|=
name|database
expr_stmt|;
name|this
operator|.
name|dialogService
operator|=
name|dialogService
expr_stmt|;
name|this
operator|.
name|preferences
operator|=
name|preferences
expr_stmt|;
name|this
operator|.
name|externalFileTypes
operator|=
name|externalFileTypes
expr_stmt|;
name|allExternalFileTypes
operator|.
name|set
argument_list|(
name|FXCollections
operator|.
name|observableArrayList
argument_list|(
name|externalFileTypes
operator|.
name|getExternalFileTypeSelection
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|monadicSelectedExternalFileType
operator|=
name|EasyBind
operator|.
name|monadic
argument_list|(
name|selectedExternalFileType
argument_list|)
expr_stmt|;
name|setValues
argument_list|(
name|linkedFile
argument_list|)
expr_stmt|;
block|}
DECL|method|setExternalFileTypeByExtension (String link)
specifier|private
name|void
name|setExternalFileTypeByExtension
parameter_list|(
name|String
name|link
parameter_list|)
block|{
if|if
condition|(
operator|!
name|link
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// Check if this looks like a remote link:
if|if
condition|(
name|REMOTE_LINK_PATTERN
operator|.
name|matcher
argument_list|(
name|link
argument_list|)
operator|.
name|matches
argument_list|()
condition|)
block|{
name|externalFileTypes
operator|.
name|getExternalFileTypeByExt
argument_list|(
literal|"html"
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|selectedExternalFileType
operator|::
name|setValue
argument_list|)
expr_stmt|;
block|}
comment|// Try to guess the file type:
name|String
name|theLink
init|=
name|link
operator|.
name|trim
argument_list|()
decl_stmt|;
name|externalFileTypes
operator|.
name|getExternalFileTypeForName
argument_list|(
name|theLink
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|selectedExternalFileType
operator|::
name|setValue
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|openBrowseDialog ()
specifier|public
name|void
name|openBrowseDialog
parameter_list|()
block|{
name|String
name|fileText
init|=
name|link
operator|.
name|get
argument_list|()
decl_stmt|;
name|Optional
argument_list|<
name|Path
argument_list|>
name|file
init|=
name|FileHelper
operator|.
name|expandFilename
argument_list|(
name|database
argument_list|,
name|fileText
argument_list|,
name|preferences
operator|.
name|getFilePreferences
argument_list|()
argument_list|)
decl_stmt|;
name|Path
name|workingDir
init|=
name|file
operator|.
name|orElse
argument_list|(
name|preferences
operator|.
name|getWorkingDir
argument_list|()
argument_list|)
decl_stmt|;
name|String
name|fileName
init|=
name|Paths
operator|.
name|get
argument_list|(
name|fileText
argument_list|)
operator|.
name|getFileName
argument_list|()
operator|.
name|toString
argument_list|()
decl_stmt|;
name|FileDialogConfiguration
name|fileDialogConfiguration
init|=
operator|new
name|FileDialogConfiguration
operator|.
name|Builder
argument_list|()
operator|.
name|withInitialDirectory
argument_list|(
name|workingDir
argument_list|)
operator|.
name|withInitialFileName
argument_list|(
name|fileName
argument_list|)
operator|.
name|build
argument_list|()
decl_stmt|;
name|dialogService
operator|.
name|showFileOpenDialog
argument_list|(
name|fileDialogConfiguration
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|path
lambda|->
block|{
comment|// Store the directory for next time:
name|preferences
operator|.
name|setWorkingDir
argument_list|(
name|path
argument_list|)
expr_stmt|;
name|link
operator|.
name|set
argument_list|(
name|relativize
argument_list|(
name|path
argument_list|)
argument_list|)
expr_stmt|;
name|setExternalFileTypeByExtension
argument_list|(
name|link
operator|.
name|getValueSafe
argument_list|()
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|setValues (LinkedFile linkedFile)
specifier|public
name|void
name|setValues
parameter_list|(
name|LinkedFile
name|linkedFile
parameter_list|)
block|{
name|description
operator|.
name|set
argument_list|(
name|linkedFile
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|linkedFile
operator|.
name|isOnlineLink
argument_list|()
condition|)
block|{
name|link
operator|.
name|setValue
argument_list|(
name|linkedFile
operator|.
name|getLink
argument_list|()
argument_list|)
expr_stmt|;
comment|//Might be an URL
block|}
else|else
block|{
name|link
operator|.
name|setValue
argument_list|(
name|relativize
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
name|linkedFile
operator|.
name|getLink
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|selectedExternalFileType
operator|.
name|setValue
argument_list|(
literal|null
argument_list|)
expr_stmt|;
comment|// See what is a reasonable selection for the type combobox:
name|Optional
argument_list|<
name|ExternalFileType
argument_list|>
name|fileType
init|=
name|externalFileTypes
operator|.
name|fromLinkedFile
argument_list|(
name|linkedFile
argument_list|,
literal|false
argument_list|)
decl_stmt|;
if|if
condition|(
name|fileType
operator|.
name|isPresent
argument_list|()
operator|&&
operator|!
operator|(
name|fileType
operator|.
name|get
argument_list|()
operator|instanceof
name|UnknownExternalFileType
operator|)
condition|)
block|{
name|selectedExternalFileType
operator|.
name|setValue
argument_list|(
name|fileType
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|(
name|linkedFile
operator|.
name|getLink
argument_list|()
operator|!=
literal|null
operator|)
operator|&&
operator|(
operator|!
name|linkedFile
operator|.
name|getLink
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|)
condition|)
block|{
name|setExternalFileTypeByExtension
argument_list|(
name|linkedFile
operator|.
name|getLink
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|linkProperty ()
specifier|public
name|StringProperty
name|linkProperty
parameter_list|()
block|{
return|return
name|link
return|;
block|}
DECL|method|descriptionProperty ()
specifier|public
name|StringProperty
name|descriptionProperty
parameter_list|()
block|{
return|return
name|description
return|;
block|}
DECL|method|externalFileTypeProperty ()
specifier|public
name|ListProperty
argument_list|<
name|ExternalFileType
argument_list|>
name|externalFileTypeProperty
parameter_list|()
block|{
return|return
name|allExternalFileTypes
return|;
block|}
DECL|method|selectedExternalFileTypeProperty ()
specifier|public
name|ObjectProperty
argument_list|<
name|ExternalFileType
argument_list|>
name|selectedExternalFileTypeProperty
parameter_list|()
block|{
return|return
name|selectedExternalFileType
return|;
block|}
DECL|method|getNewLinkedFile ()
specifier|public
name|LinkedFile
name|getNewLinkedFile
parameter_list|()
block|{
return|return
operator|new
name|LinkedFile
argument_list|(
name|description
operator|.
name|getValue
argument_list|()
argument_list|,
name|link
operator|.
name|getValue
argument_list|()
argument_list|,
name|monadicSelectedExternalFileType
operator|.
name|map
argument_list|(
name|ExternalFileType
operator|::
name|toString
argument_list|)
operator|.
name|getOrElse
argument_list|(
literal|""
argument_list|)
argument_list|)
return|;
block|}
DECL|method|relativize (Path filePath)
specifier|private
name|String
name|relativize
parameter_list|(
name|Path
name|filePath
parameter_list|)
block|{
name|List
argument_list|<
name|Path
argument_list|>
name|fileDirectories
init|=
name|database
operator|.
name|getFileDirectoriesAsPaths
argument_list|(
name|preferences
operator|.
name|getFilePreferences
argument_list|()
argument_list|)
decl_stmt|;
return|return
name|FileUtil
operator|.
name|relativize
argument_list|(
name|filePath
argument_list|,
name|fileDirectories
argument_list|)
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

