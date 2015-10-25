begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2015 JabRef contributors.     Copyright (C) 2015 Oscar Gustafsson.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.external.push
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|external
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
name|IOException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|*
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|*
import|;
end_import

begin_import
import|import
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|builder
operator|.
name|FormBuilder
import|;
end_import

begin_import
import|import
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|layout
operator|.
name|FormLayout
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|actions
operator|.
name|BrowseAction
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
operator|.
name|BibtexDatabase
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|BibtexEntry
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
comment|/**  * Abstract class for pushing entries into different editors.  */
end_comment

begin_class
DECL|class|AbstractPushToApplication
specifier|public
specifier|abstract
class|class
name|AbstractPushToApplication
implements|implements
name|PushToApplication
block|{
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
name|AbstractPushToApplication
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|couldNotCall
specifier|protected
name|boolean
name|couldNotCall
decl_stmt|;
comment|// Set to true in case the command could not be executed, e.g., if the file is not found
DECL|field|couldNotConnect
specifier|protected
name|boolean
name|couldNotConnect
decl_stmt|;
comment|// Set to true in case the tunnel to the program (if one is used) does not operate
DECL|field|notDefined
specifier|protected
name|boolean
name|notDefined
decl_stmt|;
comment|// Set to true if the corresponding path is not defined in the preferences
DECL|field|settings
specifier|protected
name|JPanel
name|settings
decl_stmt|;
DECL|field|Path
specifier|protected
specifier|final
name|JTextField
name|Path
init|=
operator|new
name|JTextField
argument_list|(
literal|30
argument_list|)
decl_stmt|;
DECL|field|commandPath
specifier|protected
name|String
name|commandPath
decl_stmt|;
DECL|field|commandPathPreferenceKey
specifier|protected
name|String
name|commandPathPreferenceKey
decl_stmt|;
DECL|field|citeCommand
specifier|protected
name|String
name|citeCommand
init|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|CITE_COMMAND
argument_list|)
decl_stmt|;
DECL|field|builder
specifier|protected
name|FormBuilder
name|builder
decl_stmt|;
annotation|@
name|Override
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|Localization
operator|.
name|menuTitle
argument_list|(
literal|"Insert selected citations into %d"
argument_list|,
name|getApplicationName
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getTooltip ()
specifier|public
name|String
name|getTooltip
parameter_list|()
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Push to %0"
argument_list|,
name|getApplicationName
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|pushEntries (BibtexDatabase database, BibtexEntry[] entries, String keyString, MetaData metaData)
specifier|public
name|void
name|pushEntries
parameter_list|(
name|BibtexDatabase
name|database
parameter_list|,
name|BibtexEntry
index|[]
name|entries
parameter_list|,
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
comment|// Check if a path to the command has been specified
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
comment|// Execute command
try|try
block|{
name|Runtime
operator|.
name|getRuntime
argument_list|()
operator|.
name|exec
argument_list|(
name|getCommandLine
argument_list|(
name|keyString
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// In case it didn't work
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
literal|"Could not call executable"
argument_list|)
operator|+
literal|" '"
operator|+
name|commandPath
operator|+
literal|"'."
argument_list|)
expr_stmt|;
block|}
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
name|notDefined
condition|)
block|{
comment|// @formatter:off
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
literal|"Path to %0 not defined"
argument_list|,
name|getApplicationName
argument_list|()
argument_list|)
operator|+
literal|"."
argument_list|)
expr_stmt|;
comment|// @formatter:on
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
literal|"Could not call executable"
argument_list|)
operator|+
literal|" '"
operator|+
name|commandPath
operator|+
literal|"'."
argument_list|)
expr_stmt|;
block|}
elseif|else
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
literal|"Could not connect to "
argument_list|)
operator|+
name|getApplicationName
argument_list|()
operator|+
literal|"."
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|panel
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Pushed citations to %0"
argument_list|,
name|getApplicationName
argument_list|()
argument_list|)
operator|+
literal|"."
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|requiresBibtexKeys ()
specifier|public
name|boolean
name|requiresBibtexKeys
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
comment|/**      * Function to get the command to be executed for pushing keys to be cited      *      * @param keyString String containing the Bibtex keys to be pushed to the application      * @return String array with the command to call and its arguments      */
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unused"
argument_list|)
DECL|method|getCommandLine (String keyString)
specifier|protected
name|String
index|[]
name|getCommandLine
parameter_list|(
name|String
name|keyString
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
comment|/**      * Function to get the command name in case it is different from the application name      *      * @return String with the command name      */
DECL|method|getCommandName ()
specifier|protected
name|String
name|getCommandName
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|getSettingsPanel ()
specifier|public
name|JPanel
name|getSettingsPanel
parameter_list|()
block|{
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
name|settings
operator|==
literal|null
condition|)
block|{
name|initSettingsPanel
argument_list|()
expr_stmt|;
block|}
name|Path
operator|.
name|setText
argument_list|(
name|commandPath
argument_list|)
expr_stmt|;
return|return
name|settings
return|;
block|}
comment|/**      * Function to initialize parameters. Currently it is expected that commandPathPreferenceKey is set to the path of      * the application.      */
DECL|method|initParameters ()
specifier|abstract
specifier|protected
name|void
name|initParameters
parameter_list|()
function_decl|;
comment|/**      * Create a FormBuilder, fill it with a textbox for the path and store the JPanel in settings      */
DECL|method|initSettingsPanel ()
specifier|protected
name|void
name|initSettingsPanel
parameter_list|()
block|{
name|builder
operator|=
name|FormBuilder
operator|.
name|create
argument_list|()
expr_stmt|;
name|builder
operator|.
name|layout
argument_list|(
operator|new
name|FormLayout
argument_list|(
literal|"left:pref, 4dlu, fill:pref:grow, 4dlu, fill:pref"
argument_list|,
literal|"p"
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|label
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Path to %0"
argument_list|,
name|getApplicationName
argument_list|()
argument_list|)
decl_stmt|;
comment|// In case the application name and the actual command is not the same, add the command in brackets
if|if
condition|(
name|getCommandName
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|label
operator|+=
literal|" ("
operator|+
name|getCommandName
argument_list|()
operator|+
literal|"):"
expr_stmt|;
block|}
else|else
block|{
name|label
operator|+=
literal|":"
expr_stmt|;
block|}
name|builder
operator|.
name|add
argument_list|(
name|label
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|Path
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|BrowseAction
name|action
init|=
name|BrowseAction
operator|.
name|buildForFile
argument_list|(
name|Path
argument_list|)
decl_stmt|;
name|JButton
name|browse
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Browse"
argument_list|)
argument_list|)
decl_stmt|;
name|browse
operator|.
name|addActionListener
argument_list|(
name|action
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|browse
argument_list|)
operator|.
name|xy
argument_list|(
literal|5
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|settings
operator|=
name|builder
operator|.
name|build
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|storeSettings ()
specifier|public
name|void
name|storeSettings
parameter_list|()
block|{
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
name|commandPathPreferenceKey
argument_list|,
name|Path
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

