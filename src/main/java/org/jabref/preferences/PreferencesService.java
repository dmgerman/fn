begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.preferences
package|package
name|org
operator|.
name|jabref
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
name|charset
operator|.
name|Charset
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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
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
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|keyboard
operator|.
name|KeyBindingRepository
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
name|exporter
operator|.
name|SavePreferences
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
name|exporter
operator|.
name|TemplateExporter
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
name|importer
operator|.
name|ImportFormatPreferences
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
name|journals
operator|.
name|JournalAbbreviationLoader
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
name|journals
operator|.
name|JournalAbbreviationPreferences
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
name|layout
operator|.
name|LayoutFormatterPreferences
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
name|openoffice
operator|.
name|OpenOfficePreferences
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
name|protectedterms
operator|.
name|ProtectedTermsLoader
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
name|UpdateFieldPreferences
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
name|AutoLinkPreferences
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
name|xmp
operator|.
name|XmpPreferences
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
name|field
operator|.
name|Field
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
name|model
operator|.
name|metadata
operator|.
name|SaveOrderConfig
import|;
end_import

begin_interface
DECL|interface|PreferencesService
specifier|public
interface|interface
name|PreferencesService
block|{
DECL|method|setProtectedTermsPreferences (ProtectedTermsLoader loader)
name|void
name|setProtectedTermsPreferences
parameter_list|(
name|ProtectedTermsLoader
name|loader
parameter_list|)
function_decl|;
DECL|method|getJournalAbbreviationPreferences ()
name|JournalAbbreviationPreferences
name|getJournalAbbreviationPreferences
parameter_list|()
function_decl|;
DECL|method|getKeywordDelimiter ()
name|Character
name|getKeywordDelimiter
parameter_list|()
function_decl|;
DECL|method|storeKeyBindingRepository (KeyBindingRepository keyBindingRepository)
name|void
name|storeKeyBindingRepository
parameter_list|(
name|KeyBindingRepository
name|keyBindingRepository
parameter_list|)
function_decl|;
DECL|method|getKeyBindingRepository ()
name|KeyBindingRepository
name|getKeyBindingRepository
parameter_list|()
function_decl|;
DECL|method|storeJournalAbbreviationPreferences (JournalAbbreviationPreferences abbreviationsPreferences)
name|void
name|storeJournalAbbreviationPreferences
parameter_list|(
name|JournalAbbreviationPreferences
name|abbreviationsPreferences
parameter_list|)
function_decl|;
DECL|method|getFilePreferences ()
name|FilePreferences
name|getFilePreferences
parameter_list|()
function_decl|;
DECL|method|getXMPPreferences ()
name|XmpPreferences
name|getXMPPreferences
parameter_list|()
function_decl|;
DECL|method|getAutoLinkPreferences ()
name|AutoLinkPreferences
name|getAutoLinkPreferences
parameter_list|()
function_decl|;
DECL|method|getWorkingDir ()
name|Path
name|getWorkingDir
parameter_list|()
function_decl|;
DECL|method|setWorkingDir (Path dir)
name|void
name|setWorkingDir
parameter_list|(
name|Path
name|dir
parameter_list|)
function_decl|;
DECL|method|getOpenOfficePreferences ()
name|OpenOfficePreferences
name|getOpenOfficePreferences
parameter_list|()
function_decl|;
DECL|method|setOpenOfficePreferences (OpenOfficePreferences openOfficePreferences)
name|void
name|setOpenOfficePreferences
parameter_list|(
name|OpenOfficePreferences
name|openOfficePreferences
parameter_list|)
function_decl|;
DECL|method|getPreviewPreferences ()
name|PreviewPreferences
name|getPreviewPreferences
parameter_list|()
function_decl|;
DECL|method|getEntryEditorTabList ()
name|Map
argument_list|<
name|String
argument_list|,
name|Set
argument_list|<
name|Field
argument_list|>
argument_list|>
name|getEntryEditorTabList
parameter_list|()
function_decl|;
DECL|method|getEnforceLegalKeys ()
name|Boolean
name|getEnforceLegalKeys
parameter_list|()
function_decl|;
DECL|method|getCustomTabsNamesAndFields ()
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|getCustomTabsNamesAndFields
parameter_list|()
function_decl|;
DECL|method|setCustomTabsNameAndFields (String name, String fields, int defNumber)
name|void
name|setCustomTabsNameAndFields
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|fields
parameter_list|,
name|int
name|defNumber
parameter_list|)
function_decl|;
DECL|method|purgeSeries (String prefix, int number)
name|void
name|purgeSeries
parameter_list|(
name|String
name|prefix
parameter_list|,
name|int
name|number
parameter_list|)
function_decl|;
DECL|method|updateEntryEditorTabList ()
name|void
name|updateEntryEditorTabList
parameter_list|()
function_decl|;
DECL|method|getCustomExportFormats (JournalAbbreviationLoader loader)
name|List
argument_list|<
name|TemplateExporter
argument_list|>
name|getCustomExportFormats
parameter_list|(
name|JournalAbbreviationLoader
name|loader
parameter_list|)
function_decl|;
DECL|method|storeCustomExportFormats (List<TemplateExporter> exporters)
name|void
name|storeCustomExportFormats
parameter_list|(
name|List
argument_list|<
name|TemplateExporter
argument_list|>
name|exporters
parameter_list|)
function_decl|;
DECL|method|getLayoutFormatterPreferences (JournalAbbreviationLoader loader)
name|LayoutFormatterPreferences
name|getLayoutFormatterPreferences
parameter_list|(
name|JournalAbbreviationLoader
name|loader
parameter_list|)
function_decl|;
DECL|method|getUpdateFieldPreferences ()
name|UpdateFieldPreferences
name|getUpdateFieldPreferences
parameter_list|()
function_decl|;
DECL|method|getImportFormatPreferences ()
name|ImportFormatPreferences
name|getImportFormatPreferences
parameter_list|()
function_decl|;
DECL|method|isKeywordSyncEnabled ()
name|boolean
name|isKeywordSyncEnabled
parameter_list|()
function_decl|;
DECL|method|loadForExportFromPreferences ()
name|SavePreferences
name|loadForExportFromPreferences
parameter_list|()
function_decl|;
DECL|method|getExportWorkingDirectory ()
name|String
name|getExportWorkingDirectory
parameter_list|()
function_decl|;
DECL|method|getDefaultEncoding ()
name|Charset
name|getDefaultEncoding
parameter_list|()
function_decl|;
DECL|method|setDefaultEncoding (Charset encoding)
name|void
name|setDefaultEncoding
parameter_list|(
name|Charset
name|encoding
parameter_list|)
function_decl|;
DECL|method|getUser ()
name|String
name|getUser
parameter_list|()
function_decl|;
DECL|method|setExportWorkingDirectory (String layoutFileDirString)
name|void
name|setExportWorkingDirectory
parameter_list|(
name|String
name|layoutFileDirString
parameter_list|)
function_decl|;
DECL|method|loadExportSaveOrder ()
name|SaveOrderConfig
name|loadExportSaveOrder
parameter_list|()
function_decl|;
DECL|method|storeExportSaveOrder (SaveOrderConfig config)
name|void
name|storeExportSaveOrder
parameter_list|(
name|SaveOrderConfig
name|config
parameter_list|)
function_decl|;
DECL|method|shouldWarnAboutDuplicatesForImport ()
name|boolean
name|shouldWarnAboutDuplicatesForImport
parameter_list|()
function_decl|;
DECL|method|setShouldWarnAboutDuplicatesForImport (boolean value)
name|void
name|setShouldWarnAboutDuplicatesForImport
parameter_list|(
name|boolean
name|value
parameter_list|)
function_decl|;
DECL|method|saveCustomEntryTypes ()
name|void
name|saveCustomEntryTypes
parameter_list|()
function_decl|;
DECL|method|getAllowIntegerEdition ()
name|boolean
name|getAllowIntegerEdition
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

