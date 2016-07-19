begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2016 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
package|;
end_package

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|Authenticator
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|SwingUtilities
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
name|cli
operator|.
name|ArgumentProcessor
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
name|remote
operator|.
name|JabRefMessageHandler
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
name|CustomEntryTypesManager
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
name|exporter
operator|.
name|ExportFormats
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
name|journals
operator|.
name|JournalAbbreviationLoader
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
name|net
operator|.
name|ProxyAuthenticator
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
name|net
operator|.
name|ProxyPreferences
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
name|net
operator|.
name|ProxyRegisterer
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
name|remote
operator|.
name|RemotePreferences
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
name|remote
operator|.
name|client
operator|.
name|RemoteListenerClient
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
name|InternalBibtexFields
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
name|preferences
operator|.
name|JabRefPreferences
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
comment|/**  * JabRef MainClass  */
end_comment

begin_class
DECL|class|JabRefMain
specifier|public
class|class
name|JabRefMain
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
name|JabRefMain
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|method|main (String[] args)
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
block|{
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
parameter_list|()
lambda|->
name|start
argument_list|(
name|args
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|start (String[] args)
specifier|private
specifier|static
name|void
name|start
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
block|{
name|JabRefPreferences
name|preferences
init|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|ProxyPreferences
name|proxyPreferences
init|=
name|ProxyPreferences
operator|.
name|loadFromPreferences
argument_list|(
name|preferences
argument_list|)
decl_stmt|;
name|ProxyRegisterer
operator|.
name|register
argument_list|(
name|proxyPreferences
argument_list|)
expr_stmt|;
if|if
condition|(
name|proxyPreferences
operator|.
name|isUseProxy
argument_list|()
operator|&&
name|proxyPreferences
operator|.
name|isUseAuthentication
argument_list|()
condition|)
block|{
name|Authenticator
operator|.
name|setDefault
argument_list|(
operator|new
name|ProxyAuthenticator
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|Globals
operator|.
name|startBackgroundTasks
argument_list|()
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|=
name|preferences
expr_stmt|;
name|Localization
operator|.
name|setLanguage
argument_list|(
name|preferences
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|LANGUAGE
argument_list|)
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|setLanguageDependentDefaultValues
argument_list|()
expr_stmt|;
comment|// Update which fields should be treated as numeric, based on preferences:
name|InternalBibtexFields
operator|.
name|setNumericFieldsFromPrefs
argument_list|()
expr_stmt|;
comment|/* Build list of Import and Export formats */
name|Globals
operator|.
name|IMPORT_FORMAT_READER
operator|.
name|resetImportFormats
argument_list|()
expr_stmt|;
name|CustomEntryTypesManager
operator|.
name|loadCustomEntryTypes
argument_list|(
name|preferences
argument_list|)
expr_stmt|;
name|ExportFormats
operator|.
name|initAllExports
argument_list|()
expr_stmt|;
comment|// Read list(s) of journal names and abbreviations
name|Globals
operator|.
name|journalAbbreviationLoader
operator|=
operator|new
name|JournalAbbreviationLoader
argument_list|()
expr_stmt|;
comment|// Check for running JabRef
name|RemotePreferences
name|remotePreferences
init|=
operator|new
name|RemotePreferences
argument_list|(
name|Globals
operator|.
name|prefs
argument_list|)
decl_stmt|;
if|if
condition|(
name|remotePreferences
operator|.
name|useRemoteServer
argument_list|()
condition|)
block|{
name|Globals
operator|.
name|REMOTE_LISTENER
operator|.
name|open
argument_list|(
operator|new
name|JabRefMessageHandler
argument_list|()
argument_list|,
name|remotePreferences
operator|.
name|getPort
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|Globals
operator|.
name|REMOTE_LISTENER
operator|.
name|isOpen
argument_list|()
condition|)
block|{
comment|// we are not alone, there is already a server out there, try to contact already running JabRef:
if|if
condition|(
name|RemoteListenerClient
operator|.
name|sendToActiveJabRefInstance
argument_list|(
name|args
argument_list|,
name|remotePreferences
operator|.
name|getPort
argument_list|()
argument_list|)
condition|)
block|{
comment|// We have successfully sent our command line options through the socket to another JabRef instance.
comment|// So we assume it's all taken care of, and quit.
name|LOGGER
operator|.
name|info
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Arguments passed on to running JabRef instance. Shutting down."
argument_list|)
argument_list|)
expr_stmt|;
name|JabRefExecutorService
operator|.
name|INSTANCE
operator|.
name|shutdownEverything
argument_list|()
expr_stmt|;
return|return;
block|}
block|}
comment|// we are alone, we start the server
name|Globals
operator|.
name|REMOTE_LISTENER
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
comment|// override used newline character with the one stored in the preferences
comment|// The preferences return the system newline character sequence as default
name|Globals
operator|.
name|NEWLINE
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
comment|// Process arguments
name|ArgumentProcessor
name|argumentProcessor
init|=
operator|new
name|ArgumentProcessor
argument_list|(
name|args
argument_list|,
name|ArgumentProcessor
operator|.
name|Mode
operator|.
name|INITIAL_START
argument_list|)
decl_stmt|;
comment|// See if we should shut down now
if|if
condition|(
name|argumentProcessor
operator|.
name|shouldShutDown
argument_list|()
condition|)
block|{
name|JabRefExecutorService
operator|.
name|INSTANCE
operator|.
name|shutdownEverything
argument_list|()
expr_stmt|;
return|return;
block|}
comment|// If not, start GUI
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
parameter_list|()
lambda|->
operator|new
name|JabRefGUI
argument_list|(
name|argumentProcessor
operator|.
name|getParserResults
argument_list|()
argument_list|,
name|argumentProcessor
operator|.
name|isBlank
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

