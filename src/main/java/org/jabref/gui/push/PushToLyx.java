begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.push
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|push
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|BufferedWriter
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
name|FileWriter
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
name|javax
operator|.
name|swing
operator|.
name|JLabel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JPanel
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
name|JabRefExecutorService
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
name|IconTheme
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
name|JabRefIcon
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
name|database
operator|.
name|BibDatabase
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
name|JabRefPreferences
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_class
DECL|class|PushToLyx
specifier|public
class|class
name|PushToLyx
extends|extends
name|AbstractPushToApplication
implements|implements
name|PushToApplication
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Logger
name|LOGGER
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|PushToLyx
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Override
DECL|method|getApplicationName ()
specifier|public
name|String
name|getApplicationName
parameter_list|()
block|{
return|return
literal|"LyX/Kile"
return|;
block|}
annotation|@
name|Override
DECL|method|getIcon ()
specifier|public
name|JabRefIcon
name|getIcon
parameter_list|()
block|{
return|return
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|APPLICATION_LYX
return|;
block|}
annotation|@
name|Override
DECL|method|initParameters ()
specifier|protected
name|void
name|initParameters
parameter_list|()
block|{
name|commandPathPreferenceKey
operator|=
name|JabRefPreferences
operator|.
name|LYXPIPE
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|operationCompleted (BasePanel panel)
specifier|public
name|void
name|operationCompleted
parameter_list|(
name|BasePanel
name|panel
parameter_list|)
block|{
if|if
condition|(
name|couldNotConnect
condition|)
block|{
name|panel
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error"
argument_list|)
operator|+
literal|": "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"verify that LyX is running and that the lyxpipe is valid"
argument_list|)
operator|+
literal|". ["
operator|+
name|commandPath
operator|+
literal|"]"
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|couldNotCall
condition|)
block|{
name|panel
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error"
argument_list|)
operator|+
literal|": "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"unable to write to"
argument_list|)
operator|+
literal|" "
operator|+
name|commandPath
operator|+
literal|".in"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|super
operator|.
name|operationCompleted
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|initSettingsPanel ()
specifier|protected
name|void
name|initSettingsPanel
parameter_list|()
block|{
name|super
operator|.
name|initSettingsPanel
argument_list|()
expr_stmt|;
name|settings
operator|=
operator|new
name|JPanel
argument_list|()
expr_stmt|;
name|settings
operator|.
name|add
argument_list|(
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Path to LyX pipe"
argument_list|)
operator|+
literal|":"
argument_list|)
argument_list|)
expr_stmt|;
name|settings
operator|.
name|add
argument_list|(
name|path
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|pushEntries (BibDatabase database, final List<BibEntry> entries, final String keyString, MetaData metaData)
specifier|public
name|void
name|pushEntries
parameter_list|(
name|BibDatabase
name|database
parameter_list|,
specifier|final
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|,
specifier|final
name|String
name|keyString
parameter_list|,
name|MetaData
name|metaData
parameter_list|)
block|{
name|couldNotConnect
operator|=
literal|false
expr_stmt|;
name|couldNotCall
operator|=
literal|false
expr_stmt|;
name|notDefined
operator|=
literal|false
expr_stmt|;
name|initParameters
argument_list|()
expr_stmt|;
name|commandPath
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|commandPathPreferenceKey
argument_list|)
expr_stmt|;
if|if
condition|(
operator|(
name|commandPath
operator|==
literal|null
operator|)
operator|||
name|commandPath
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|notDefined
operator|=
literal|true
expr_stmt|;
return|return;
block|}
if|if
condition|(
operator|!
name|commandPath
operator|.
name|endsWith
argument_list|(
literal|".in"
argument_list|)
condition|)
block|{
name|commandPath
operator|=
name|commandPath
operator|+
literal|".in"
expr_stmt|;
block|}
name|File
name|lp
init|=
operator|new
name|File
argument_list|(
name|commandPath
argument_list|)
decl_stmt|;
comment|// this needs to fixed because it gives "asdf" when going prefs.get("lyxpipe")
if|if
condition|(
operator|!
name|lp
operator|.
name|exists
argument_list|()
operator|||
operator|!
name|lp
operator|.
name|canWrite
argument_list|()
condition|)
block|{
comment|// See if it helps to append ".in":
name|lp
operator|=
operator|new
name|File
argument_list|(
name|commandPath
operator|+
literal|".in"
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|lp
operator|.
name|exists
argument_list|()
operator|||
operator|!
name|lp
operator|.
name|canWrite
argument_list|()
condition|)
block|{
name|couldNotConnect
operator|=
literal|true
expr_stmt|;
return|return;
block|}
block|}
specifier|final
name|File
name|lyxpipe
init|=
name|lp
decl_stmt|;
name|JabRefExecutorService
operator|.
name|INSTANCE
operator|.
name|executeAndWait
argument_list|(
parameter_list|()
lambda|->
block|{
try|try
init|(
name|FileWriter
name|fw
init|=
operator|new
name|FileWriter
argument_list|(
name|lyxpipe
argument_list|)
init|;
name|BufferedWriter
name|lyxOut
operator|=
operator|new
name|BufferedWriter
argument_list|(
name|fw
argument_list|)
init|)
block|{
name|String
name|citeStr
decl_stmt|;
name|citeStr
operator|=
literal|"LYXCMD:sampleclient:citation-insert:"
operator|+
name|keyString
expr_stmt|;
name|lyxOut
operator|.
name|write
argument_list|(
name|citeStr
operator|+
literal|"\n"
argument_list|)
expr_stmt|;
name|lyxOut
operator|.
name|close
argument_list|()
expr_stmt|;
name|fw
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|excep
parameter_list|)
block|{
name|couldNotCall
operator|=
literal|true
expr_stmt|;
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Problem pushing to LyX/Kile."
argument_list|,
name|excep
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

