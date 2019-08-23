begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.preferences
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|preferences
package|;
end_package

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
name|DialogPane
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
name|JabRefFrame
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
name|EditExternalFileTypesAction
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
name|push
operator|.
name|PushToApplication
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
name|push
operator|.
name|PushToApplicationSettings
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
name|push
operator|.
name|PushToApplicationsManager
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
name|l10n
operator|.
name|Localization
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
name|OS
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
name|strings
operator|.
name|StringUtil
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
name|JabRefPreferences
import|;
end_import

begin_class
DECL|class|ExternalTabViewModel
specifier|public
class|class
name|ExternalTabViewModel
implements|implements
name|PreferenceTabViewModel
block|{
DECL|field|eMailReferenceSubjectProperty
specifier|private
specifier|final
name|StringProperty
name|eMailReferenceSubjectProperty
init|=
operator|new
name|SimpleStringProperty
argument_list|(
literal|""
argument_list|)
decl_stmt|;
DECL|field|autoOpenAttachedFoldersProperty
specifier|private
specifier|final
name|BooleanProperty
name|autoOpenAttachedFoldersProperty
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|pushToApplicationsListProperty
specifier|private
specifier|final
name|ListProperty
argument_list|<
name|PushToApplication
argument_list|>
name|pushToApplicationsListProperty
init|=
operator|new
name|SimpleListProperty
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|selectedPushToApplicationProperty
specifier|private
specifier|final
name|ObjectProperty
argument_list|<
name|PushToApplication
argument_list|>
name|selectedPushToApplicationProperty
init|=
operator|new
name|SimpleObjectProperty
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|citeCommandProperty
specifier|private
specifier|final
name|StringProperty
name|citeCommandProperty
init|=
operator|new
name|SimpleStringProperty
argument_list|(
literal|""
argument_list|)
decl_stmt|;
DECL|field|useTerminalDefaultProperty
specifier|private
specifier|final
name|BooleanProperty
name|useTerminalDefaultProperty
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|useTerminalSpecialProperty
specifier|private
specifier|final
name|BooleanProperty
name|useTerminalSpecialProperty
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|useTerminalCommandProperty
specifier|private
specifier|final
name|StringProperty
name|useTerminalCommandProperty
init|=
operator|new
name|SimpleStringProperty
argument_list|(
literal|""
argument_list|)
decl_stmt|;
DECL|field|usePDFAcrobatProperty
specifier|private
specifier|final
name|BooleanProperty
name|usePDFAcrobatProperty
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|usePDFAcrobatCommandProperty
specifier|private
specifier|final
name|StringProperty
name|usePDFAcrobatCommandProperty
init|=
operator|new
name|SimpleStringProperty
argument_list|(
literal|""
argument_list|)
decl_stmt|;
DECL|field|usePDFSumatraProperty
specifier|private
specifier|final
name|BooleanProperty
name|usePDFSumatraProperty
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|usePDFSumatraCommandProperty
specifier|private
specifier|final
name|StringProperty
name|usePDFSumatraCommandProperty
init|=
operator|new
name|SimpleStringProperty
argument_list|(
literal|""
argument_list|)
decl_stmt|;
DECL|field|useFileBrowserDefaultProperty
specifier|private
specifier|final
name|BooleanProperty
name|useFileBrowserDefaultProperty
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|useFileBrowserSpecialProperty
specifier|private
specifier|final
name|BooleanProperty
name|useFileBrowserSpecialProperty
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|useFileBrowserSpecialCommandProperty
specifier|private
specifier|final
name|StringProperty
name|useFileBrowserSpecialCommandProperty
init|=
operator|new
name|SimpleStringProperty
argument_list|(
literal|""
argument_list|)
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
name|JabRefPreferences
name|preferences
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|fileDialogConfiguration
specifier|private
specifier|final
name|FileDialogConfiguration
name|fileDialogConfiguration
init|=
operator|new
name|FileDialogConfiguration
operator|.
name|Builder
argument_list|()
operator|.
name|build
argument_list|()
decl_stmt|;
DECL|method|ExternalTabViewModel (DialogService dialogService, JabRefPreferences preferences, JabRefFrame frame)
specifier|public
name|ExternalTabViewModel
parameter_list|(
name|DialogService
name|dialogService
parameter_list|,
name|JabRefPreferences
name|preferences
parameter_list|,
name|JabRefFrame
name|frame
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
name|preferences
operator|=
name|preferences
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|setValues
argument_list|()
expr_stmt|;
block|}
DECL|method|setValues ()
specifier|public
name|void
name|setValues
parameter_list|()
block|{
name|eMailReferenceSubjectProperty
operator|.
name|setValue
argument_list|(
name|preferences
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|EMAIL_SUBJECT
argument_list|)
argument_list|)
expr_stmt|;
name|autoOpenAttachedFoldersProperty
operator|.
name|setValue
argument_list|(
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|OPEN_FOLDERS_OF_ATTACHED_FILES
argument_list|)
argument_list|)
expr_stmt|;
name|pushToApplicationsListProperty
operator|.
name|setValue
argument_list|(
name|FXCollections
operator|.
name|observableArrayList
argument_list|(
name|frame
operator|.
name|getPushToApplicationsManager
argument_list|()
operator|.
name|getApplications
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|selectedPushToApplicationProperty
operator|.
name|setValue
argument_list|(
name|preferences
operator|.
name|getActivePushToApplication
argument_list|(
name|frame
operator|.
name|getPushToApplicationsManager
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|citeCommandProperty
operator|.
name|setValue
argument_list|(
name|preferences
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|CITE_COMMAND
argument_list|)
argument_list|)
expr_stmt|;
name|useTerminalDefaultProperty
operator|.
name|setValue
argument_list|(
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_DEFAULT_CONSOLE_APPLICATION
argument_list|)
argument_list|)
expr_stmt|;
name|useTerminalCommandProperty
operator|.
name|setValue
argument_list|(
name|preferences
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|CONSOLE_COMMAND
argument_list|)
argument_list|)
expr_stmt|;
name|useTerminalSpecialProperty
operator|.
name|setValue
argument_list|(
operator|!
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_DEFAULT_CONSOLE_APPLICATION
argument_list|)
argument_list|)
expr_stmt|;
name|usePDFAcrobatCommandProperty
operator|.
name|setValue
argument_list|(
name|preferences
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|ADOBE_ACROBAT_COMMAND
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|OS
operator|.
name|WINDOWS
condition|)
block|{
name|usePDFSumatraCommandProperty
operator|.
name|setValue
argument_list|(
name|preferences
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|SUMATRA_PDF_COMMAND
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|preferences
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|USE_PDF_READER
argument_list|)
operator|.
name|equals
argument_list|(
name|usePDFAcrobatCommandProperty
operator|.
name|getValue
argument_list|()
argument_list|)
condition|)
block|{
name|usePDFAcrobatProperty
operator|.
name|setValue
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|preferences
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|USE_PDF_READER
argument_list|)
operator|.
name|equals
argument_list|(
name|usePDFSumatraCommandProperty
operator|.
name|getValue
argument_list|()
argument_list|)
condition|)
block|{
name|usePDFSumatraProperty
operator|.
name|setValue
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
name|useFileBrowserDefaultProperty
operator|.
name|setValue
argument_list|(
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_DEFAULT_FILE_BROWSER_APPLICATION
argument_list|)
argument_list|)
expr_stmt|;
name|useFileBrowserSpecialProperty
operator|.
name|setValue
argument_list|(
operator|!
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_DEFAULT_FILE_BROWSER_APPLICATION
argument_list|)
argument_list|)
expr_stmt|;
name|useFileBrowserSpecialCommandProperty
operator|.
name|setValue
argument_list|(
name|preferences
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|FILE_BROWSER_COMMAND
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|storeSettings ()
specifier|public
name|void
name|storeSettings
parameter_list|()
block|{
name|preferences
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|EMAIL_SUBJECT
argument_list|,
name|eMailReferenceSubjectProperty
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|preferences
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|OPEN_FOLDERS_OF_ATTACHED_FILES
argument_list|,
name|autoOpenAttachedFoldersProperty
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|preferences
operator|.
name|setActivePushToApplication
argument_list|(
name|selectedPushToApplicationProperty
operator|.
name|getValue
argument_list|()
argument_list|,
name|frame
operator|.
name|getPushToApplicationsManager
argument_list|()
argument_list|)
expr_stmt|;
name|preferences
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|CITE_COMMAND
argument_list|,
name|citeCommandProperty
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|preferences
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_DEFAULT_CONSOLE_APPLICATION
argument_list|,
name|useTerminalDefaultProperty
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|preferences
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|CONSOLE_COMMAND
argument_list|,
name|useTerminalCommandProperty
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|preferences
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|ADOBE_ACROBAT_COMMAND
argument_list|,
name|usePDFAcrobatCommandProperty
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|OS
operator|.
name|WINDOWS
condition|)
block|{
name|preferences
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|SUMATRA_PDF_COMMAND
argument_list|,
name|usePDFSumatraCommandProperty
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|usePDFAcrobatProperty
operator|.
name|getValue
argument_list|()
condition|)
block|{
name|preferences
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|USE_PDF_READER
argument_list|,
name|usePDFAcrobatCommandProperty
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|usePDFSumatraProperty
operator|.
name|getValue
argument_list|()
condition|)
block|{
name|preferences
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|USE_PDF_READER
argument_list|,
name|usePDFSumatraCommandProperty
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|preferences
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_DEFAULT_FILE_BROWSER_APPLICATION
argument_list|,
name|useFileBrowserDefaultProperty
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|StringUtil
operator|.
name|isNotBlank
argument_list|(
name|useFileBrowserSpecialCommandProperty
operator|.
name|getValue
argument_list|()
argument_list|)
condition|)
block|{
name|preferences
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|FILE_BROWSER_COMMAND
argument_list|,
name|useFileBrowserSpecialCommandProperty
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|preferences
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_DEFAULT_FILE_BROWSER_APPLICATION
argument_list|,
literal|true
argument_list|)
expr_stmt|;
comment|//default if no command specified
block|}
block|}
DECL|method|validateSettings ()
specifier|public
name|boolean
name|validateSettings
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
annotation|@
name|Override
DECL|method|getRestartWarnings ()
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getRestartWarnings
parameter_list|()
block|{
return|return
operator|new
name|ArrayList
argument_list|<>
argument_list|()
return|;
block|}
DECL|method|pushToApplicationSettings ()
specifier|public
name|void
name|pushToApplicationSettings
parameter_list|()
block|{
name|PushToApplicationsManager
name|manager
init|=
name|frame
operator|.
name|getPushToApplicationsManager
argument_list|()
decl_stmt|;
name|PushToApplication
name|selectedApplication
init|=
name|selectedPushToApplicationProperty
operator|.
name|getValue
argument_list|()
decl_stmt|;
name|PushToApplicationSettings
name|settings
init|=
name|manager
operator|.
name|getSettings
argument_list|(
name|selectedApplication
argument_list|)
decl_stmt|;
name|DialogPane
name|dialogPane
init|=
operator|new
name|DialogPane
argument_list|()
decl_stmt|;
name|dialogPane
operator|.
name|setContent
argument_list|(
name|settings
operator|.
name|getSettingsPane
argument_list|()
argument_list|)
expr_stmt|;
name|dialogService
operator|.
name|showCustomDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Application settings"
argument_list|)
argument_list|,
name|dialogPane
argument_list|,
name|ButtonType
operator|.
name|OK
argument_list|,
name|ButtonType
operator|.
name|CANCEL
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|btn
lambda|->
block|{
if|if
condition|(
name|btn
operator|==
name|ButtonType
operator|.
name|OK
condition|)
block|{
name|settings
operator|.
name|storeSettings
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|manageExternalFileTypes ()
specifier|public
name|void
name|manageExternalFileTypes
parameter_list|()
block|{
operator|new
name|EditExternalFileTypesAction
argument_list|()
operator|.
name|execute
argument_list|()
expr_stmt|;
block|}
DECL|method|useTerminalCommandBrowse ()
specifier|public
name|void
name|useTerminalCommandBrowse
parameter_list|()
block|{
name|dialogService
operator|.
name|showFileOpenDialog
argument_list|(
name|fileDialogConfiguration
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|file
lambda|->
name|useTerminalCommandProperty
operator|.
name|setValue
argument_list|(
name|file
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
DECL|method|usePDFAcrobatCommandBrowse ()
specifier|public
name|void
name|usePDFAcrobatCommandBrowse
parameter_list|()
block|{
name|dialogService
operator|.
name|showFileOpenDialog
argument_list|(
name|fileDialogConfiguration
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|file
lambda|->
name|usePDFAcrobatCommandProperty
operator|.
name|setValue
argument_list|(
name|file
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
DECL|method|usePDFSumatraCommandBrowse ()
specifier|public
name|void
name|usePDFSumatraCommandBrowse
parameter_list|()
block|{
name|dialogService
operator|.
name|showFileOpenDialog
argument_list|(
name|fileDialogConfiguration
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|file
lambda|->
name|usePDFSumatraCommandProperty
operator|.
name|setValue
argument_list|(
name|file
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
DECL|method|useFileBrowserSpecialCommandBrowse ()
specifier|public
name|void
name|useFileBrowserSpecialCommandBrowse
parameter_list|()
block|{
name|dialogService
operator|.
name|showFileOpenDialog
argument_list|(
name|fileDialogConfiguration
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|file
lambda|->
name|useFileBrowserSpecialCommandProperty
operator|.
name|setValue
argument_list|(
name|file
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
comment|// EMail
DECL|method|eMailReferenceSubjectProperty ()
specifier|public
name|StringProperty
name|eMailReferenceSubjectProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|eMailReferenceSubjectProperty
return|;
block|}
DECL|method|autoOpenAttachedFoldersProperty ()
specifier|public
name|BooleanProperty
name|autoOpenAttachedFoldersProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|autoOpenAttachedFoldersProperty
return|;
block|}
comment|// Push-To-Application
DECL|method|pushToApplicationsListProperty ()
specifier|public
name|ListProperty
argument_list|<
name|PushToApplication
argument_list|>
name|pushToApplicationsListProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|pushToApplicationsListProperty
return|;
block|}
DECL|method|selectedPushToApplication ()
specifier|public
name|ObjectProperty
argument_list|<
name|PushToApplication
argument_list|>
name|selectedPushToApplication
parameter_list|()
block|{
return|return
name|this
operator|.
name|selectedPushToApplicationProperty
return|;
block|}
DECL|method|citeCommandProperty ()
specifier|public
name|StringProperty
name|citeCommandProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|citeCommandProperty
return|;
block|}
comment|// Open console
DECL|method|useTerminalDefaultProperty ()
specifier|public
name|BooleanProperty
name|useTerminalDefaultProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|useTerminalDefaultProperty
return|;
block|}
DECL|method|useTerminalSpecialProperty ()
specifier|public
name|BooleanProperty
name|useTerminalSpecialProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|useTerminalSpecialProperty
return|;
block|}
DECL|method|useTerminalCommandProperty ()
specifier|public
name|StringProperty
name|useTerminalCommandProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|useTerminalCommandProperty
return|;
block|}
comment|// Open PDF
DECL|method|usePDFAcrobatProperty ()
specifier|public
name|BooleanProperty
name|usePDFAcrobatProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|usePDFAcrobatProperty
return|;
block|}
DECL|method|usePDFAcrobatCommandProperty ()
specifier|public
name|StringProperty
name|usePDFAcrobatCommandProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|usePDFAcrobatCommandProperty
return|;
block|}
DECL|method|usePDFSumatraProperty ()
specifier|public
name|BooleanProperty
name|usePDFSumatraProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|usePDFSumatraProperty
return|;
block|}
DECL|method|usePDFSumatraCommandProperty ()
specifier|public
name|StringProperty
name|usePDFSumatraCommandProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|usePDFSumatraCommandProperty
return|;
block|}
comment|// Open File Browser
DECL|method|useFileBrowserDefaultProperty ()
specifier|public
name|BooleanProperty
name|useFileBrowserDefaultProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|useFileBrowserDefaultProperty
return|;
block|}
DECL|method|useFileBrowserSpecialProperty ()
specifier|public
name|BooleanProperty
name|useFileBrowserSpecialProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|useFileBrowserSpecialProperty
return|;
block|}
DECL|method|useFileBrowserSpecialCommandProperty ()
specifier|public
name|StringProperty
name|useFileBrowserSpecialCommandProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|useFileBrowserSpecialCommandProperty
return|;
block|}
block|}
end_class

end_unit

