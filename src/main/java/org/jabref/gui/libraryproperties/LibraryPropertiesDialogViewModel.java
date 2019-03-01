begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.libraryproperties
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|libraryproperties
package|;
end_package

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|charset
operator|.
name|Charset
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
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|BooleanProperty
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
name|SimpleBooleanProperty
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
name|BasePanel
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
name|util
operator|.
name|DirectoryDialogConfiguration
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
name|Encodings
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
name|shared
operator|.
name|DatabaseLocation
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
name|metadata
operator|.
name|MetaData
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

begin_class
DECL|class|LibraryPropertiesDialogViewModel
specifier|public
class|class
name|LibraryPropertiesDialogViewModel
block|{
DECL|field|generalFileDirectoryProperty
specifier|private
specifier|final
name|StringProperty
name|generalFileDirectoryProperty
init|=
operator|new
name|SimpleStringProperty
argument_list|(
literal|""
argument_list|)
decl_stmt|;
DECL|field|userSpecificFileDirectoryProperty
specifier|private
specifier|final
name|StringProperty
name|userSpecificFileDirectoryProperty
init|=
operator|new
name|SimpleStringProperty
argument_list|(
literal|""
argument_list|)
decl_stmt|;
DECL|field|encodingsProperty
specifier|private
specifier|final
name|ListProperty
argument_list|<
name|Charset
argument_list|>
name|encodingsProperty
init|=
operator|new
name|SimpleListProperty
argument_list|<>
argument_list|(
name|FXCollections
operator|.
name|observableArrayList
argument_list|(
name|Encodings
operator|.
name|getCharsets
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|selectedEncodingPropety
specifier|private
specifier|final
name|ObjectProperty
argument_list|<
name|Charset
argument_list|>
name|selectedEncodingPropety
init|=
operator|new
name|SimpleObjectProperty
argument_list|<>
argument_list|(
name|Encodings
operator|.
name|getCharsets
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|libraryProtectedProperty
specifier|private
specifier|final
name|BooleanProperty
name|libraryProtectedProperty
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|encodingDisableProperty
specifier|private
specifier|final
name|BooleanProperty
name|encodingDisableProperty
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|protectDisableProperty
specifier|private
specifier|final
name|BooleanProperty
name|protectDisableProperty
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
decl_stmt|;
DECL|field|directoryDialogConfiguration
specifier|private
specifier|final
name|DirectoryDialogConfiguration
name|directoryDialogConfiguration
decl_stmt|;
DECL|field|metaData
specifier|private
specifier|final
name|MetaData
name|metaData
decl_stmt|;
DECL|field|oldUserSpecificFileDir
specifier|private
specifier|final
name|String
name|oldUserSpecificFileDir
decl_stmt|;
DECL|field|oldGeneralFileDir
specifier|private
specifier|final
name|String
name|oldGeneralFileDir
decl_stmt|;
DECL|field|oldLibraryProtected
specifier|private
specifier|final
name|boolean
name|oldLibraryProtected
decl_stmt|;
DECL|method|LibraryPropertiesDialogViewModel (BasePanel panel, DialogService dialogService, PreferencesService preferencesService)
specifier|public
name|LibraryPropertiesDialogViewModel
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|DialogService
name|dialogService
parameter_list|,
name|PreferencesService
name|preferencesService
parameter_list|)
block|{
name|this
operator|.
name|dialogService
operator|=
name|dialogService
expr_stmt|;
name|this
operator|.
name|metaData
operator|=
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getMetaData
argument_list|()
expr_stmt|;
name|DatabaseLocation
name|location
init|=
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getLocation
argument_list|()
decl_stmt|;
name|boolean
name|isShared
init|=
operator|(
name|location
operator|==
name|DatabaseLocation
operator|.
name|SHARED
operator|)
decl_stmt|;
name|encodingDisableProperty
operator|.
name|setValue
argument_list|(
name|isShared
argument_list|)
expr_stmt|;
comment|// the encoding of shared database is always UTF-8
name|protectDisableProperty
operator|.
name|setValue
argument_list|(
name|isShared
argument_list|)
expr_stmt|;
name|directoryDialogConfiguration
operator|=
operator|new
name|DirectoryDialogConfiguration
operator|.
name|Builder
argument_list|()
operator|.
name|withInitialDirectory
argument_list|(
name|preferencesService
operator|.
name|getWorkingDir
argument_list|()
argument_list|)
operator|.
name|build
argument_list|()
expr_stmt|;
name|Optional
argument_list|<
name|Charset
argument_list|>
name|charset
init|=
name|metaData
operator|.
name|getEncoding
argument_list|()
decl_stmt|;
name|selectedEncodingPropety
operator|.
name|setValue
argument_list|(
name|charset
operator|.
name|orElse
argument_list|(
name|preferencesService
operator|.
name|getDefaultEncoding
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|Optional
argument_list|<
name|String
argument_list|>
name|fileD
init|=
name|metaData
operator|.
name|getDefaultFileDirectory
argument_list|()
decl_stmt|;
name|fileD
operator|.
name|ifPresent
argument_list|(
name|path
lambda|->
name|generalFileDirectoryProperty
operator|.
name|setValue
argument_list|(
name|path
operator|.
name|trim
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|Optional
argument_list|<
name|String
argument_list|>
name|fileDI
init|=
name|metaData
operator|.
name|getUserFileDirectory
argument_list|(
name|preferencesService
operator|.
name|getUser
argument_list|()
argument_list|)
decl_stmt|;
name|fileDI
operator|.
name|ifPresent
argument_list|(
name|userSpecificFileDirectoryProperty
operator|::
name|setValue
argument_list|)
expr_stmt|;
name|oldUserSpecificFileDir
operator|=
name|generalFileDirectoryProperty
operator|.
name|getValue
argument_list|()
expr_stmt|;
name|oldGeneralFileDir
operator|=
name|userSpecificFileDirectoryProperty
operator|.
name|getValue
argument_list|()
expr_stmt|;
name|libraryProtectedProperty
operator|.
name|setValue
argument_list|(
name|metaData
operator|.
name|isProtected
argument_list|()
argument_list|)
expr_stmt|;
name|oldLibraryProtected
operator|=
name|libraryProtectedProperty
operator|.
name|getValue
argument_list|()
expr_stmt|;
block|}
DECL|method|generalFileDirectoryPropertyProperty ()
specifier|public
name|StringProperty
name|generalFileDirectoryPropertyProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|generalFileDirectoryProperty
return|;
block|}
DECL|method|userSpecificFileDirectoryProperty ()
specifier|public
name|StringProperty
name|userSpecificFileDirectoryProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|userSpecificFileDirectoryProperty
return|;
block|}
DECL|method|encodingsProperty ()
specifier|public
name|ListProperty
argument_list|<
name|Charset
argument_list|>
name|encodingsProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|encodingsProperty
return|;
block|}
DECL|method|selectedEncodingProperty ()
specifier|public
name|ObjectProperty
argument_list|<
name|Charset
argument_list|>
name|selectedEncodingProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|selectedEncodingPropety
return|;
block|}
DECL|method|browseGeneralDir ()
specifier|public
name|void
name|browseGeneralDir
parameter_list|()
block|{
name|dialogService
operator|.
name|showDirectorySelectionDialog
argument_list|(
name|directoryDialogConfiguration
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|dir
lambda|->
name|generalFileDirectoryProperty
operator|.
name|setValue
argument_list|(
name|dir
operator|.
name|toAbsolutePath
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|browseUserDir ()
specifier|public
name|void
name|browseUserDir
parameter_list|()
block|{
name|dialogService
operator|.
name|showDirectorySelectionDialog
argument_list|(
name|directoryDialogConfiguration
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|dir
lambda|->
name|userSpecificFileDirectoryProperty
operator|.
name|setValue
argument_list|(
name|dir
operator|.
name|toAbsolutePath
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|libraryProtectedProperty ()
specifier|public
name|BooleanProperty
name|libraryProtectedProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|libraryProtectedProperty
return|;
block|}
DECL|method|generalFileDirChanged ()
specifier|public
name|boolean
name|generalFileDirChanged
parameter_list|()
block|{
return|return
operator|!
name|oldGeneralFileDir
operator|.
name|equals
argument_list|(
name|generalFileDirectoryProperty
operator|.
name|getValue
argument_list|()
argument_list|)
return|;
block|}
DECL|method|userFileDirChanged ()
specifier|public
name|boolean
name|userFileDirChanged
parameter_list|()
block|{
return|return
operator|!
name|oldUserSpecificFileDir
operator|.
name|equals
argument_list|(
name|userSpecificFileDirectoryProperty
operator|.
name|getValue
argument_list|()
argument_list|)
return|;
block|}
DECL|method|protectedValueChanged ()
specifier|public
name|boolean
name|protectedValueChanged
parameter_list|()
block|{
return|return
operator|!
name|oldLibraryProtected
operator|==
name|libraryProtectedProperty
operator|.
name|getValue
argument_list|()
return|;
block|}
DECL|method|encodingDisableProperty ()
specifier|public
name|BooleanProperty
name|encodingDisableProperty
parameter_list|()
block|{
return|return
name|encodingDisableProperty
return|;
block|}
DECL|method|protectDisableProperty ()
specifier|public
name|BooleanProperty
name|protectDisableProperty
parameter_list|()
block|{
return|return
name|protectDisableProperty
return|;
block|}
block|}
end_class

end_unit

