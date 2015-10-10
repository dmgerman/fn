begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.external
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|external
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
name|IconTheme
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

begin_comment
comment|/**  * Class for pushing entries into TexMaker.  */
end_comment

begin_class
DECL|class|PushToTexmaker
specifier|public
class|class
name|PushToTexmaker
implements|implements
name|PushToApplication
block|{
DECL|field|couldNotCall
specifier|private
name|boolean
name|couldNotCall
decl_stmt|;
DECL|field|notDefined
specifier|private
name|boolean
name|notDefined
decl_stmt|;
DECL|field|settings
specifier|private
name|JPanel
name|settings
decl_stmt|;
DECL|field|texmakerPath
specifier|private
specifier|final
name|JTextField
name|texmakerPath
init|=
operator|new
name|JTextField
argument_list|(
literal|30
argument_list|)
decl_stmt|;
DECL|field|citeCommand
specifier|private
specifier|final
name|JTextField
name|citeCommand
init|=
operator|new
name|JTextField
argument_list|(
literal|30
argument_list|)
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
name|lang
argument_list|(
literal|"Insert selected citations into %0"
argument_list|,
name|getApplicationName
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getApplicationName ()
specifier|public
name|String
name|getApplicationName
parameter_list|()
block|{
return|return
literal|"Texmaker"
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
DECL|method|getIcon ()
specifier|public
name|Icon
name|getIcon
parameter_list|()
block|{
return|return
name|IconTheme
operator|.
name|getImage
argument_list|(
literal|"texmaker"
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getKeyStrokeName ()
specifier|public
name|String
name|getKeyStrokeName
parameter_list|()
block|{
return|return
literal|null
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
name|couldNotCall
operator|=
literal|false
expr_stmt|;
name|notDefined
operator|=
literal|false
expr_stmt|;
name|String
name|texMaker
init|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|TEXMAKER_PATH
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|texMaker
operator|==
literal|null
operator|)
operator|||
name|texMaker
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
try|try
block|{
name|Runtime
operator|.
name|getRuntime
argument_list|()
operator|.
name|exec
argument_list|(
name|texMaker
operator|+
literal|" "
operator|+
literal|"-insert "
operator|+
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|CITE_COMMAND_TEXMAKER
argument_list|)
operator|+
literal|"{"
operator|+
name|keyString
operator|+
literal|"}"
argument_list|)
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
name|excep
operator|.
name|printStackTrace
argument_list|()
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
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|TEXMAKER_PATH
argument_list|)
operator|+
literal|"'."
argument_list|)
expr_stmt|;
comment|// @formatter:on
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
annotation|@
name|Override
DECL|method|getSettingsPanel ()
specifier|public
name|JPanel
name|getSettingsPanel
parameter_list|()
block|{
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
name|texmakerPath
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|TEXMAKER_PATH
argument_list|)
argument_list|)
expr_stmt|;
name|citeCommand
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|CITE_COMMAND_TEXMAKER
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|settings
return|;
block|}
DECL|method|initSettingsPanel ()
specifier|private
name|void
name|initSettingsPanel
parameter_list|()
block|{
name|FormBuilder
name|builder
init|=
name|FormBuilder
operator|.
name|create
argument_list|()
decl_stmt|;
name|builder
operator|.
name|layout
argument_list|(
operator|new
name|FormLayout
argument_list|(
literal|"left:pref, 4dlu, fill:pref:grow, 4dlu, fill:pref"
argument_list|,
literal|"p, 2dlu, p"
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Path to %0"
argument_list|,
name|getApplicationName
argument_list|()
argument_list|)
operator|+
literal|":"
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
name|texmakerPath
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
name|texmakerPath
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
name|builder
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cite command"
argument_list|)
operator|+
literal|":"
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|citeCommand
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|3
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
name|JabRefPreferences
operator|.
name|TEXMAKER_PATH
argument_list|,
name|texmakerPath
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|CITE_COMMAND_TEXMAKER
argument_list|,
name|citeCommand
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

