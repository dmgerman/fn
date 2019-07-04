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
name|nio
operator|.
name|file
operator|.
name|Paths
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
name|Localization
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
name|FieldName
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
name|FilePreferences
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

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|preferences
operator|.
name|NewLineSeparator
import|;
end_import

begin_import
import|import
name|de
operator|.
name|saxsys
operator|.
name|mvvmfx
operator|.
name|utils
operator|.
name|validation
operator|.
name|FunctionBasedValidator
import|;
end_import

begin_import
import|import
name|de
operator|.
name|saxsys
operator|.
name|mvvmfx
operator|.
name|utils
operator|.
name|validation
operator|.
name|ValidationMessage
import|;
end_import

begin_import
import|import
name|de
operator|.
name|saxsys
operator|.
name|mvvmfx
operator|.
name|utils
operator|.
name|validation
operator|.
name|ValidationStatus
import|;
end_import

begin_class
DECL|class|FileTabViewModel
specifier|public
class|class
name|FileTabViewModel
implements|implements
name|PreferenceTabViewModel
block|{
DECL|field|openLastStartupProperty
specifier|private
specifier|final
name|BooleanProperty
name|openLastStartupProperty
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|backupOldFileProperty
specifier|private
specifier|final
name|BooleanProperty
name|backupOldFileProperty
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|noWrapFilesProperty
specifier|private
specifier|final
name|StringProperty
name|noWrapFilesProperty
init|=
operator|new
name|SimpleStringProperty
argument_list|(
literal|""
argument_list|)
decl_stmt|;
DECL|field|resolveStringsBibTexProperty
specifier|private
specifier|final
name|BooleanProperty
name|resolveStringsBibTexProperty
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|resolveStringsAllProperty
specifier|private
specifier|final
name|BooleanProperty
name|resolveStringsAllProperty
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|resolveStringsExceptProperty
specifier|private
specifier|final
name|StringProperty
name|resolveStringsExceptProperty
init|=
operator|new
name|SimpleStringProperty
argument_list|(
literal|""
argument_list|)
decl_stmt|;
DECL|field|newLineSeparatorListProperty
specifier|private
specifier|final
name|ListProperty
argument_list|<
name|NewLineSeparator
argument_list|>
name|newLineSeparatorListProperty
init|=
operator|new
name|SimpleListProperty
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|selectedNewLineSeparatorProperty
specifier|private
specifier|final
name|ObjectProperty
argument_list|<
name|NewLineSeparator
argument_list|>
name|selectedNewLineSeparatorProperty
init|=
operator|new
name|SimpleObjectProperty
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|alwaysReformatBibProperty
specifier|private
specifier|final
name|BooleanProperty
name|alwaysReformatBibProperty
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|mainFileDirProperty
specifier|private
specifier|final
name|StringProperty
name|mainFileDirProperty
init|=
operator|new
name|SimpleStringProperty
argument_list|(
literal|""
argument_list|)
decl_stmt|;
DECL|field|useBibLocationAsPrimaryProperty
specifier|private
specifier|final
name|BooleanProperty
name|useBibLocationAsPrimaryProperty
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|autolinkFileStartsBibtexProperty
specifier|private
specifier|final
name|BooleanProperty
name|autolinkFileStartsBibtexProperty
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|autolinkFileExactBibtexProperty
specifier|private
specifier|final
name|BooleanProperty
name|autolinkFileExactBibtexProperty
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|autolinkUseRegexProperty
specifier|private
specifier|final
name|BooleanProperty
name|autolinkUseRegexProperty
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|autolinkRegexKeyProperty
specifier|private
specifier|final
name|StringProperty
name|autolinkRegexKeyProperty
init|=
operator|new
name|SimpleStringProperty
argument_list|(
literal|""
argument_list|)
decl_stmt|;
DECL|field|searchFilesOnOpenProperty
specifier|private
specifier|final
name|BooleanProperty
name|searchFilesOnOpenProperty
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|openBrowseOnCreateProperty
specifier|private
specifier|final
name|BooleanProperty
name|openBrowseOnCreateProperty
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|autosaveLocalLibraries
specifier|private
specifier|final
name|BooleanProperty
name|autosaveLocalLibraries
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|mainFileDirValidator
specifier|private
specifier|final
name|FunctionBasedValidator
name|mainFileDirValidator
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
DECL|method|FileTabViewModel (DialogService dialogService, JabRefPreferences preferences)
specifier|public
name|FileTabViewModel
parameter_list|(
name|DialogService
name|dialogService
parameter_list|,
name|JabRefPreferences
name|preferences
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
name|setValues
argument_list|()
expr_stmt|;
name|mainFileDirValidator
operator|=
operator|new
name|FunctionBasedValidator
argument_list|(
name|mainFileDirProperty
argument_list|,
name|input
lambda|->
block|{
name|Path
name|path
init|=
name|Paths
operator|.
name|get
argument_list|(
name|mainFileDirProperty
operator|.
name|getValue
argument_list|()
argument_list|)
decl_stmt|;
return|return
operator|(
name|Files
operator|.
name|exists
argument_list|(
name|path
argument_list|)
operator|&&
name|Files
operator|.
name|isDirectory
argument_list|(
name|path
argument_list|)
operator|)
return|;
block|}
argument_list|,
name|ValidationMessage
operator|.
name|error
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"%s> %s> %s %n %n %s"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"File"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"External file links"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Main file directory"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Directory not found"
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|setValues ()
specifier|public
name|void
name|setValues
parameter_list|()
block|{
name|openLastStartupProperty
operator|.
name|setValue
argument_list|(
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|OPEN_LAST_EDITED
argument_list|)
argument_list|)
expr_stmt|;
name|backupOldFileProperty
operator|.
name|setValue
argument_list|(
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|BACKUP
argument_list|)
argument_list|)
expr_stmt|;
name|noWrapFilesProperty
operator|.
name|setValue
argument_list|(
name|preferences
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|NON_WRAPPABLE_FIELDS
argument_list|)
argument_list|)
expr_stmt|;
name|resolveStringsAllProperty
operator|.
name|setValue
argument_list|(
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|RESOLVE_STRINGS_ALL_FIELDS
argument_list|)
argument_list|)
expr_stmt|;
comment|// Flipped around
name|resolveStringsBibTexProperty
operator|.
name|setValue
argument_list|(
operator|!
name|resolveStringsAllProperty
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|resolveStringsExceptProperty
operator|.
name|setValue
argument_list|(
name|preferences
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|DO_NOT_RESOLVE_STRINGS_FOR
argument_list|)
argument_list|)
expr_stmt|;
name|newLineSeparatorListProperty
operator|.
name|setValue
argument_list|(
name|FXCollections
operator|.
name|observableArrayList
argument_list|(
name|NewLineSeparator
operator|.
name|values
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|selectedNewLineSeparatorProperty
operator|.
name|setValue
argument_list|(
name|preferences
operator|.
name|getNewLineSeparator
argument_list|()
argument_list|)
expr_stmt|;
name|alwaysReformatBibProperty
operator|.
name|setValue
argument_list|(
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|REFORMAT_FILE_ON_SAVE_AND_EXPORT
argument_list|)
argument_list|)
expr_stmt|;
name|mainFileDirProperty
operator|.
name|setValue
argument_list|(
name|preferences
operator|.
name|getAsOptional
argument_list|(
name|FieldName
operator|.
name|FILE
operator|+
name|FilePreferences
operator|.
name|DIR_SUFFIX
argument_list|)
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|useBibLocationAsPrimaryProperty
operator|.
name|setValue
argument_list|(
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|BIB_LOC_AS_PRIMARY_DIR
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|AUTOLINK_USE_REG_EXP_SEARCH_KEY
argument_list|)
condition|)
block|{
comment|// Flipped around
name|autolinkUseRegexProperty
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
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|AUTOLINK_EXACT_KEY_ONLY
argument_list|)
condition|)
block|{
name|autolinkFileExactBibtexProperty
operator|.
name|setValue
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|autolinkFileStartsBibtexProperty
operator|.
name|setValue
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
name|autolinkRegexKeyProperty
operator|.
name|setValue
argument_list|(
name|preferences
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|AUTOLINK_REG_EXP_SEARCH_EXPRESSION_KEY
argument_list|)
argument_list|)
expr_stmt|;
name|searchFilesOnOpenProperty
operator|.
name|setValue
argument_list|(
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|RUN_AUTOMATIC_FILE_SEARCH
argument_list|)
argument_list|)
expr_stmt|;
name|openBrowseOnCreateProperty
operator|.
name|setValue
argument_list|(
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|ALLOW_FILE_AUTO_OPEN_BROWSE
argument_list|)
argument_list|)
expr_stmt|;
name|autosaveLocalLibraries
operator|.
name|setValue
argument_list|(
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|LOCAL_AUTO_SAVE
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
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|OPEN_LAST_EDITED
argument_list|,
name|openLastStartupProperty
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
name|BACKUP
argument_list|,
name|backupOldFileProperty
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|noWrapFilesProperty
operator|.
name|getValue
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|equals
argument_list|(
name|preferences
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|NON_WRAPPABLE_FIELDS
argument_list|)
argument_list|)
condition|)
block|{
name|preferences
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|NON_WRAPPABLE_FIELDS
argument_list|,
name|noWrapFilesProperty
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
name|RESOLVE_STRINGS_ALL_FIELDS
argument_list|,
name|resolveStringsAllProperty
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
name|DO_NOT_RESOLVE_STRINGS_FOR
argument_list|,
name|resolveStringsExceptProperty
operator|.
name|getValue
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
name|resolveStringsExceptProperty
operator|.
name|setValue
argument_list|(
name|preferences
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|DO_NOT_RESOLVE_STRINGS_FOR
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|autolinkUseRegexProperty
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
name|AUTOLINK_REG_EXP_SEARCH_EXPRESSION_KEY
argument_list|,
name|autolinkRegexKeyProperty
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|preferences
operator|.
name|setNewLineSeparator
argument_list|(
name|selectedNewLineSeparatorProperty
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
name|REFORMAT_FILE_ON_SAVE_AND_EXPORT
argument_list|,
name|alwaysReformatBibProperty
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|preferences
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|FILE
operator|+
name|FilePreferences
operator|.
name|DIR_SUFFIX
argument_list|,
name|mainFileDirProperty
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
name|BIB_LOC_AS_PRIMARY_DIR
argument_list|,
name|useBibLocationAsPrimaryProperty
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
name|AUTOLINK_USE_REG_EXP_SEARCH_KEY
argument_list|,
name|autolinkUseRegexProperty
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
name|AUTOLINK_EXACT_KEY_ONLY
argument_list|,
name|autolinkFileExactBibtexProperty
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
name|RUN_AUTOMATIC_FILE_SEARCH
argument_list|,
name|searchFilesOnOpenProperty
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
name|ALLOW_FILE_AUTO_OPEN_BROWSE
argument_list|,
name|openBrowseOnCreateProperty
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
name|LOCAL_AUTO_SAVE
argument_list|,
name|autosaveLocalLibraries
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|mainFileDirValidationStatus ()
name|ValidationStatus
name|mainFileDirValidationStatus
parameter_list|()
block|{
return|return
name|mainFileDirValidator
operator|.
name|getValidationStatus
argument_list|()
return|;
block|}
DECL|method|validateSettings ()
specifier|public
name|boolean
name|validateSettings
parameter_list|()
block|{
name|ValidationStatus
name|status
init|=
name|mainFileDirValidationStatus
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|status
operator|.
name|isValid
argument_list|()
condition|)
block|{
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|status
operator|.
name|getHighestMessage
argument_list|()
operator|.
name|get
argument_list|()
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
return|return
literal|true
return|;
block|}
DECL|method|mainFileDirBrowse ()
specifier|public
name|void
name|mainFileDirBrowse
parameter_list|()
block|{
name|DirectoryDialogConfiguration
name|dirDialogConfiguration
init|=
operator|new
name|DirectoryDialogConfiguration
operator|.
name|Builder
argument_list|()
operator|.
name|withInitialDirectory
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
name|mainFileDirProperty
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
operator|.
name|build
argument_list|()
decl_stmt|;
name|dialogService
operator|.
name|showDirectorySelectionDialog
argument_list|(
name|dirDialogConfiguration
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|f
lambda|->
name|mainFileDirProperty
operator|.
name|setValue
argument_list|(
name|f
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// General
DECL|method|openLastStartupProperty ()
specifier|public
name|BooleanProperty
name|openLastStartupProperty
parameter_list|()
block|{
return|return
name|openLastStartupProperty
return|;
block|}
DECL|method|backupOldFileProperty ()
specifier|public
name|BooleanProperty
name|backupOldFileProperty
parameter_list|()
block|{
return|return
name|backupOldFileProperty
return|;
block|}
DECL|method|noWrapFilesProperty ()
specifier|public
name|StringProperty
name|noWrapFilesProperty
parameter_list|()
block|{
return|return
name|noWrapFilesProperty
return|;
block|}
DECL|method|resolveStringsBibTexProperty ()
specifier|public
name|BooleanProperty
name|resolveStringsBibTexProperty
parameter_list|()
block|{
return|return
name|resolveStringsBibTexProperty
return|;
block|}
DECL|method|resolveStringsAllProperty ()
specifier|public
name|BooleanProperty
name|resolveStringsAllProperty
parameter_list|()
block|{
return|return
name|resolveStringsAllProperty
return|;
block|}
DECL|method|resolvStringsExceptProperty ()
specifier|public
name|StringProperty
name|resolvStringsExceptProperty
parameter_list|()
block|{
return|return
name|resolveStringsExceptProperty
return|;
block|}
DECL|method|newLineSeparatorListProperty ()
specifier|public
name|ListProperty
argument_list|<
name|NewLineSeparator
argument_list|>
name|newLineSeparatorListProperty
parameter_list|()
block|{
return|return
name|newLineSeparatorListProperty
return|;
block|}
DECL|method|selectedNewLineSeparatorProperty ()
specifier|public
name|ObjectProperty
argument_list|<
name|NewLineSeparator
argument_list|>
name|selectedNewLineSeparatorProperty
parameter_list|()
block|{
return|return
name|selectedNewLineSeparatorProperty
return|;
block|}
DECL|method|alwaysReformatBibProperty ()
specifier|public
name|BooleanProperty
name|alwaysReformatBibProperty
parameter_list|()
block|{
return|return
name|alwaysReformatBibProperty
return|;
block|}
comment|// External file links
DECL|method|mainFileDirProperty ()
specifier|public
name|StringProperty
name|mainFileDirProperty
parameter_list|()
block|{
return|return
name|mainFileDirProperty
return|;
block|}
DECL|method|useBibLocationAsPrimaryProperty ()
specifier|public
name|BooleanProperty
name|useBibLocationAsPrimaryProperty
parameter_list|()
block|{
return|return
name|useBibLocationAsPrimaryProperty
return|;
block|}
DECL|method|autolinkFileStartsBibtexProperty ()
specifier|public
name|BooleanProperty
name|autolinkFileStartsBibtexProperty
parameter_list|()
block|{
return|return
name|autolinkFileStartsBibtexProperty
return|;
block|}
DECL|method|autolinkFileExactBibtexProperty ()
specifier|public
name|BooleanProperty
name|autolinkFileExactBibtexProperty
parameter_list|()
block|{
return|return
name|autolinkFileExactBibtexProperty
return|;
block|}
DECL|method|autolinkUseRegexProperty ()
specifier|public
name|BooleanProperty
name|autolinkUseRegexProperty
parameter_list|()
block|{
return|return
name|autolinkUseRegexProperty
return|;
block|}
DECL|method|autolinkRegexKeyProperty ()
specifier|public
name|StringProperty
name|autolinkRegexKeyProperty
parameter_list|()
block|{
return|return
name|autolinkRegexKeyProperty
return|;
block|}
DECL|method|searchFilesOnOpenProperty ()
specifier|public
name|BooleanProperty
name|searchFilesOnOpenProperty
parameter_list|()
block|{
return|return
name|searchFilesOnOpenProperty
return|;
block|}
DECL|method|openBrowseOnCreateProperty ()
specifier|public
name|BooleanProperty
name|openBrowseOnCreateProperty
parameter_list|()
block|{
return|return
name|openBrowseOnCreateProperty
return|;
block|}
comment|// Autosave
DECL|method|autosaveLocalLibrariesProperty ()
specifier|public
name|BooleanProperty
name|autosaveLocalLibrariesProperty
parameter_list|()
block|{
return|return
name|autosaveLocalLibraries
return|;
block|}
block|}
end_class

end_unit

