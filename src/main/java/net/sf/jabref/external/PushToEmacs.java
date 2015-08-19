begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|java
operator|.
name|io
operator|.
name|InputStream
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
name|DefaultFormBuilder
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
name|GUIGlobals
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
name|action
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
name|logic
operator|.
name|util
operator|.
name|OS
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
comment|/**  * Created by IntelliJ IDEA.  * User: alver  * Date: Jan 14, 2006  * Time: 4:55:23 PM  */
end_comment

begin_class
DECL|class|PushToEmacs
specifier|public
class|class
name|PushToEmacs
implements|implements
name|PushToApplication
block|{
DECL|field|settings
specifier|private
name|JPanel
name|settings
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
DECL|field|emacsPath
specifier|private
specifier|final
name|JTextField
name|emacsPath
init|=
operator|new
name|JTextField
argument_list|(
literal|30
argument_list|)
decl_stmt|;
DECL|field|additionalParams
specifier|private
specifier|final
name|JTextField
name|additionalParams
init|=
operator|new
name|JTextField
argument_list|(
literal|30
argument_list|)
decl_stmt|;
DECL|field|useEmacs23
specifier|private
specifier|final
name|JCheckBox
name|useEmacs23
init|=
operator|new
name|JCheckBox
argument_list|()
decl_stmt|;
DECL|field|couldNotConnect
specifier|private
name|boolean
name|couldNotConnect
decl_stmt|;
DECL|field|couldNotRunClient
specifier|private
name|boolean
name|couldNotRunClient
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
literal|"Insert selected citations into Emacs"
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
literal|"Emacs"
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
literal|"Push selection to Emacs"
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
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"emacs"
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
literal|"Push to Emacs"
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
name|CITE_COMMAND_EMACS
argument_list|)
argument_list|)
expr_stmt|;
name|emacsPath
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
name|EMACS_PATH
argument_list|)
argument_list|)
expr_stmt|;
name|additionalParams
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
name|EMACS_ADDITIONAL_PARAMETERS
argument_list|)
argument_list|)
expr_stmt|;
name|useEmacs23
operator|.
name|setSelected
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|EMACS_23
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|settings
return|;
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
name|CITE_COMMAND_EMACS
argument_list|,
name|citeCommand
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
name|EMACS_PATH
argument_list|,
name|emacsPath
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
name|EMACS_ADDITIONAL_PARAMETERS
argument_list|,
name|additionalParams
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|EMACS_23
argument_list|,
name|useEmacs23
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|initSettingsPanel ()
specifier|private
name|void
name|initSettingsPanel
parameter_list|()
block|{
name|DefaultFormBuilder
name|builder
init|=
operator|new
name|DefaultFormBuilder
argument_list|(
operator|new
name|FormLayout
argument_list|(
literal|"left:pref, 4dlu, fill:pref, 4dlu, fill:pref"
argument_list|,
literal|""
argument_list|)
argument_list|)
decl_stmt|;
name|builder
operator|.
name|append
argument_list|(
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Path to gnuclient or emacsclient"
argument_list|)
operator|.
name|concat
argument_list|(
literal|":"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|emacsPath
argument_list|)
expr_stmt|;
name|BrowseAction
name|action
init|=
name|BrowseAction
operator|.
name|buildForFile
argument_list|(
name|emacsPath
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
name|append
argument_list|(
name|browse
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Additional parameters"
argument_list|)
operator|.
name|concat
argument_list|(
literal|":"
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|additionalParams
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Use EMACS 23 insertion string"
argument_list|)
operator|.
name|concat
argument_list|(
literal|":"
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|useEmacs23
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
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
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|citeCommand
argument_list|)
expr_stmt|;
name|settings
operator|=
name|builder
operator|.
name|getPanel
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|pushEntries (BibtexDatabase database, BibtexEntry[] entries, String keys, MetaData metaData)
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
name|keys
parameter_list|,
name|MetaData
name|metaData
parameter_list|)
block|{
name|couldNotConnect
operator|=
literal|false
expr_stmt|;
name|couldNotRunClient
operator|=
literal|false
expr_stmt|;
name|String
name|command
init|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|EMACS_PATH
argument_list|)
decl_stmt|;
name|String
index|[]
name|addParams
init|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|EMACS_ADDITIONAL_PARAMETERS
argument_list|)
operator|.
name|split
argument_list|(
literal|" "
argument_list|)
decl_stmt|;
try|try
block|{
name|String
index|[]
name|com
init|=
operator|new
name|String
index|[
name|addParams
operator|.
name|length
operator|+
literal|2
index|]
decl_stmt|;
name|com
index|[
literal|0
index|]
operator|=
name|command
expr_stmt|;
name|System
operator|.
name|arraycopy
argument_list|(
name|addParams
argument_list|,
literal|0
argument_list|,
name|com
argument_list|,
literal|1
argument_list|,
name|addParams
operator|.
name|length
argument_list|)
expr_stmt|;
name|String
name|prefix
decl_stmt|;
name|String
name|suffix
decl_stmt|;
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|EMACS_23
argument_list|)
condition|)
block|{
name|prefix
operator|=
literal|"(with-current-buffer (window-buffer) (insert "
expr_stmt|;
name|suffix
operator|=
literal|"))"
expr_stmt|;
block|}
else|else
block|{
name|prefix
operator|=
literal|"(insert "
expr_stmt|;
name|suffix
operator|=
literal|")"
expr_stmt|;
block|}
name|com
index|[
name|com
operator|.
name|length
operator|-
literal|1
index|]
operator|=
name|OS
operator|.
name|WINDOWS
condition|?
comment|// Windows gnuclient escaping:
comment|// java string: "(insert \\\"\\\\cite{Blah2001}\\\")";
comment|// so cmd receives: (insert \"\\cite{Blah2001}\")
comment|// so emacs receives: (insert "\cite{Blah2001}")
name|prefix
operator|.
name|concat
argument_list|(
literal|"\\\"\\"
operator|+
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|CITE_COMMAND_EMACS
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"\\\\"
argument_list|,
literal|"\\\\\\\\"
argument_list|)
operator|+
literal|"{"
operator|+
name|keys
operator|+
literal|"}\\\""
argument_list|)
operator|.
name|concat
argument_list|(
name|suffix
argument_list|)
else|:
comment|// Linux gnuclient escaping:
comment|// java string: "(insert \"\\\\cite{Blah2001}\")"
comment|// so sh receives: (insert "\\cite{Blah2001}")
comment|// so emacs receives: (insert "\cite{Blah2001}")
name|prefix
operator|.
name|concat
argument_list|(
literal|"\""
operator|+
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|CITE_COMMAND_EMACS
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"\\\\"
argument_list|,
literal|"\\\\\\\\"
argument_list|)
operator|+
literal|"{"
operator|+
name|keys
operator|+
literal|"}\""
argument_list|)
operator|.
name|concat
argument_list|(
name|suffix
argument_list|)
expr_stmt|;
specifier|final
name|Process
name|p
init|=
name|Runtime
operator|.
name|getRuntime
argument_list|()
operator|.
name|exec
argument_list|(
name|com
argument_list|)
decl_stmt|;
name|Runnable
name|errorListener
init|=
operator|new
name|Runnable
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
name|InputStream
name|out
init|=
name|p
operator|.
name|getErrorStream
argument_list|()
decl_stmt|;
comment|//                    try {
comment|//                    	if (out.available()<= 0)
comment|//                    		out = p.getInputStream();
comment|//                    } catch (Exception e) {
comment|//                    }
name|int
name|c
decl_stmt|;
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
try|try
block|{
while|while
condition|(
operator|(
name|c
operator|=
name|out
operator|.
name|read
argument_list|()
operator|)
operator|!=
operator|-
literal|1
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
comment|// Error stream has been closed. See if there were any errors:
if|if
condition|(
operator|!
name|sb
operator|.
name|toString
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|sb
argument_list|)
expr_stmt|;
name|couldNotConnect
operator|=
literal|true
expr_stmt|;
block|}
block|}
block|}
decl_stmt|;
name|JabRefExecutorService
operator|.
name|INSTANCE
operator|.
name|executeAndWait
argument_list|(
name|errorListener
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|excep
parameter_list|)
block|{
name|couldNotRunClient
operator|=
literal|true
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
name|couldNotConnect
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|,
literal|"<HTML>"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Could not connect to a running gnuserv process. Make sure that "
operator|+
literal|"Emacs or XEmacs is running,<BR>and that the server has been started "
operator|+
literal|"(by running the command 'server-start'/'gnuserv-start')."
argument_list|)
operator|+
literal|"</HTML>"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|couldNotRunClient
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Could not run the gnuclient/emacsclient program. Make sure you have "
operator|+
literal|"the emacsclient/gnuclient program installed and available in the PATH."
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
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
literal|"Pushed citations to Emacs"
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
block|}
end_class

end_unit

