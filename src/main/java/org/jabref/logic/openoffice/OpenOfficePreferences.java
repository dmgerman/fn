begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.openoffice
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|openoffice
package|;
end_package

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
name|util
operator|.
name|Arrays
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
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_comment
comment|/**  * The OpenOffice connection preferences are:  * OO_PATH main directory for OO/LO installation, used to detect location on Win/OS X when using manual connect  * OO_EXECUTABLE_PATH path to soffice-file  * OO_JARS_PATH directory that contains juh.jar, jurt.jar, ridl.jar, unoil.jar  * OO_SYNC_WHEN_CITING true if the reference list is updated when adding a new citation  * OO_SHOW_PANEL true if the OO panel is shown on startup  * OO_USE_ALL_OPEN_DATABASES true if all databases should be used when citing  * OO_BIBLIOGRAPHY_STYLE_FILE path to the used style file  * OO_EXTERNAL_STYLE_FILES list with paths to external style files  */
end_comment

begin_class
DECL|class|OpenOfficePreferences
specifier|public
class|class
name|OpenOfficePreferences
block|{
DECL|field|preferences
specifier|private
specifier|final
name|JabRefPreferences
name|preferences
decl_stmt|;
DECL|field|DEFAULT_WINDOWS_PATH
specifier|public
specifier|static
specifier|final
name|String
name|DEFAULT_WINDOWS_PATH
init|=
literal|"C:\\Program Files\\OpenOffice.org 4"
decl_stmt|;
DECL|field|DEFAULT_WIN_EXEC_PATH
specifier|public
specifier|static
specifier|final
name|String
name|DEFAULT_WIN_EXEC_PATH
init|=
literal|"C:\\Program Files\\OpenOffice.org 4\\program\\soffice.exe"
decl_stmt|;
DECL|field|WINDOWS_EXECUTABLE
specifier|public
specifier|static
specifier|final
name|String
name|WINDOWS_EXECUTABLE
init|=
literal|"soffice.exe"
decl_stmt|;
DECL|field|DEFAULT_OSX_PATH
specifier|public
specifier|static
specifier|final
name|String
name|DEFAULT_OSX_PATH
init|=
literal|"/Applications/OpenOffice.org.app"
decl_stmt|;
DECL|field|DEFAULT_OSX_EXEC_PATH
specifier|public
specifier|static
specifier|final
name|String
name|DEFAULT_OSX_EXEC_PATH
init|=
literal|"/Applications/OpenOffice.org.app/Contents/MacOS/soffice.bin"
decl_stmt|;
DECL|field|OSX_EXECUTABLE
specifier|public
specifier|static
specifier|final
name|String
name|OSX_EXECUTABLE
init|=
literal|"soffice"
decl_stmt|;
DECL|field|DEFAULT_LINUX_PATH
specifier|public
specifier|static
specifier|final
name|String
name|DEFAULT_LINUX_PATH
init|=
literal|"/opt/openoffice.org3"
decl_stmt|;
DECL|field|DEFAULT_LINUX_JARS
specifier|public
specifier|static
specifier|final
name|String
name|DEFAULT_LINUX_JARS
init|=
literal|"/opt/openoffice.org/basis3.0"
decl_stmt|;
DECL|field|DEFAULT_LINUX_EXEC_PATH
specifier|public
specifier|static
specifier|final
name|String
name|DEFAULT_LINUX_EXEC_PATH
init|=
literal|"/usr/lib/openoffice/program/soffice"
decl_stmt|;
DECL|field|LINUX_EXECUTABLE
specifier|public
specifier|static
specifier|final
name|String
name|LINUX_EXECUTABLE
init|=
literal|"soffice"
decl_stmt|;
DECL|field|OO_JARS
specifier|public
specifier|static
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|OO_JARS
init|=
name|Arrays
operator|.
name|asList
argument_list|(
literal|"unoil.jar"
argument_list|,
literal|"jurt.jar"
argument_list|,
literal|"juh.jar"
argument_list|,
literal|"ridl.jar"
argument_list|)
decl_stmt|;
DECL|method|OpenOfficePreferences (JabRefPreferences preferences)
specifier|public
name|OpenOfficePreferences
parameter_list|(
name|JabRefPreferences
name|preferences
parameter_list|)
block|{
name|this
operator|.
name|preferences
operator|=
name|preferences
expr_stmt|;
block|}
DECL|method|updateConnectionParams (String ooPath, String execPath, String jarsPath)
specifier|public
name|void
name|updateConnectionParams
parameter_list|(
name|String
name|ooPath
parameter_list|,
name|String
name|execPath
parameter_list|,
name|String
name|jarsPath
parameter_list|)
block|{
name|setOOPath
argument_list|(
name|ooPath
argument_list|)
expr_stmt|;
name|setExecutablePath
argument_list|(
name|execPath
argument_list|)
expr_stmt|;
name|setJarsPath
argument_list|(
name|jarsPath
argument_list|)
expr_stmt|;
block|}
DECL|method|checkAutoDetectedPaths ()
specifier|public
name|boolean
name|checkAutoDetectedPaths
parameter_list|()
block|{
if|if
condition|(
name|preferences
operator|.
name|hasKey
argument_list|(
name|JabRefPreferences
operator|.
name|OO_PATH
argument_list|)
condition|)
block|{
return|return
operator|new
name|File
argument_list|(
name|getExecutablePath
argument_list|()
argument_list|)
operator|.
name|exists
argument_list|()
return|;
block|}
else|else
block|{
return|return
literal|false
return|;
block|}
block|}
DECL|method|clearConnectionSettings ()
specifier|public
name|String
name|clearConnectionSettings
parameter_list|()
block|{
name|preferences
operator|.
name|clear
argument_list|(
name|JabRefPreferences
operator|.
name|OO_PATH
argument_list|)
expr_stmt|;
name|preferences
operator|.
name|clear
argument_list|(
name|JabRefPreferences
operator|.
name|OO_EXECUTABLE_PATH
argument_list|)
expr_stmt|;
name|preferences
operator|.
name|clear
argument_list|(
name|JabRefPreferences
operator|.
name|OO_JARS_PATH
argument_list|)
expr_stmt|;
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cleared connection settings."
argument_list|)
return|;
block|}
DECL|method|getJarsPath ()
specifier|public
name|String
name|getJarsPath
parameter_list|()
block|{
return|return
name|preferences
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|OO_JARS_PATH
argument_list|)
return|;
block|}
DECL|method|setJarsPath (String path)
specifier|public
name|void
name|setJarsPath
parameter_list|(
name|String
name|path
parameter_list|)
block|{
name|preferences
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|OO_JARS_PATH
argument_list|,
name|path
argument_list|)
expr_stmt|;
block|}
DECL|method|getExecutablePath ()
specifier|public
name|String
name|getExecutablePath
parameter_list|()
block|{
return|return
name|preferences
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|OO_EXECUTABLE_PATH
argument_list|)
return|;
block|}
DECL|method|setExecutablePath (String path)
specifier|public
name|void
name|setExecutablePath
parameter_list|(
name|String
name|path
parameter_list|)
block|{
name|preferences
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|OO_EXECUTABLE_PATH
argument_list|,
name|path
argument_list|)
expr_stmt|;
block|}
DECL|method|getInstallationPath ()
specifier|public
name|String
name|getInstallationPath
parameter_list|()
block|{
return|return
name|preferences
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|OO_PATH
argument_list|)
return|;
block|}
DECL|method|setOOPath (String path)
specifier|public
name|void
name|setOOPath
parameter_list|(
name|String
name|path
parameter_list|)
block|{
name|preferences
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|OO_PATH
argument_list|,
name|path
argument_list|)
expr_stmt|;
block|}
DECL|method|useAllDatabases ()
specifier|public
name|boolean
name|useAllDatabases
parameter_list|()
block|{
return|return
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|OO_USE_ALL_OPEN_BASES
argument_list|)
return|;
block|}
DECL|method|setUseAllDatabases (boolean use)
specifier|public
name|void
name|setUseAllDatabases
parameter_list|(
name|boolean
name|use
parameter_list|)
block|{
name|preferences
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|OO_USE_ALL_OPEN_BASES
argument_list|,
name|use
argument_list|)
expr_stmt|;
block|}
DECL|method|syncWhenCiting ()
specifier|public
name|boolean
name|syncWhenCiting
parameter_list|()
block|{
return|return
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|OO_SYNC_WHEN_CITING
argument_list|)
return|;
block|}
DECL|method|setSyncWhenCiting (boolean sync)
specifier|public
name|void
name|setSyncWhenCiting
parameter_list|(
name|boolean
name|sync
parameter_list|)
block|{
name|preferences
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|OO_SYNC_WHEN_CITING
argument_list|,
name|sync
argument_list|)
expr_stmt|;
block|}
DECL|method|showPanel ()
specifier|public
name|boolean
name|showPanel
parameter_list|()
block|{
return|return
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|OO_SHOW_PANEL
argument_list|)
return|;
block|}
DECL|method|setShowPanel (boolean show)
specifier|public
name|void
name|setShowPanel
parameter_list|(
name|boolean
name|show
parameter_list|)
block|{
name|preferences
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|OO_SHOW_PANEL
argument_list|,
name|show
argument_list|)
expr_stmt|;
block|}
DECL|method|getExternalStyles ()
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getExternalStyles
parameter_list|()
block|{
return|return
name|preferences
operator|.
name|getStringList
argument_list|(
name|JabRefPreferences
operator|.
name|OO_EXTERNAL_STYLE_FILES
argument_list|)
return|;
block|}
DECL|method|setExternalStyles (List<String> filenames)
specifier|public
name|void
name|setExternalStyles
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|filenames
parameter_list|)
block|{
name|preferences
operator|.
name|putStringList
argument_list|(
name|JabRefPreferences
operator|.
name|OO_EXTERNAL_STYLE_FILES
argument_list|,
name|filenames
argument_list|)
expr_stmt|;
block|}
DECL|method|getCurrentStyle ()
specifier|public
name|String
name|getCurrentStyle
parameter_list|()
block|{
return|return
name|preferences
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|OO_BIBLIOGRAPHY_STYLE_FILE
argument_list|)
return|;
block|}
DECL|method|clearCurrentStyle ()
specifier|public
name|void
name|clearCurrentStyle
parameter_list|()
block|{
name|preferences
operator|.
name|remove
argument_list|(
name|JabRefPreferences
operator|.
name|OO_BIBLIOGRAPHY_STYLE_FILE
argument_list|)
expr_stmt|;
block|}
DECL|method|setCurrentStyle (String path)
specifier|public
name|void
name|setCurrentStyle
parameter_list|(
name|String
name|path
parameter_list|)
block|{
name|preferences
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|OO_BIBLIOGRAPHY_STYLE_FILE
argument_list|,
name|path
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

