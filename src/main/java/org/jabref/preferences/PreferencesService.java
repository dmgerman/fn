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
name|file
operator|.
name|Path
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
name|model
operator|.
name|metadata
operator|.
name|FileDirectoryPreferences
import|;
end_import

begin_interface
DECL|interface|PreferencesService
specifier|public
interface|interface
name|PreferencesService
block|{
DECL|method|getJournalAbbreviationPreferences ()
name|JournalAbbreviationPreferences
name|getJournalAbbreviationPreferences
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
DECL|method|getFileDirectoryPreferences ()
name|FileDirectoryPreferences
name|getFileDirectoryPreferences
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
block|}
end_interface

end_unit

