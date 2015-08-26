begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|collab
operator|.
name|FileUpdateMonitor
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
name|exporter
operator|.
name|AutoSaveManager
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
name|GlobalFocusListener
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
name|JabRefFrame
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
name|SidePaneManager
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
name|help
operator|.
name|HelpDialog
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
name|importer
operator|.
name|ImportFormatReader
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
name|error
operator|.
name|StreamEavesdropper
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
name|JournalAbbreviationRepository
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
name|logging
operator|.
name|CacheableHandler
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
name|server
operator|.
name|RemoteListenerServerLifecycle
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
name|BuildInfo
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
name|BibtexEntryType
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
name|BibtexEntryTypes
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
name|FileNotFoundException
import|;
end_import

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
name|regex
operator|.
name|Pattern
import|;
end_import

begin_class
DECL|class|Globals
specifier|public
class|class
name|Globals
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
name|Globals
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|ENTRYTYPE_FLAG
specifier|public
specifier|static
specifier|final
name|String
name|ENTRYTYPE_FLAG
init|=
literal|"jabref-entrytype: "
decl_stmt|;
comment|// JabRef version info
DECL|field|BUILD_INFO
specifier|public
specifier|static
specifier|final
name|BuildInfo
name|BUILD_INFO
init|=
operator|new
name|BuildInfo
argument_list|()
decl_stmt|;
comment|// Signature written at the top of the .bib file.
DECL|field|SIGNATURE
specifier|public
specifier|static
specifier|final
name|String
name|SIGNATURE
init|=
literal|"This file was created with JabRef"
decl_stmt|;
DECL|field|encPrefix
specifier|public
specifier|static
specifier|final
name|String
name|encPrefix
init|=
literal|"Encoding: "
decl_stmt|;
comment|// Newlines
comment|// will be overridden in initialization due to feature #857 @ JabRef.java
DECL|field|NEWLINE
specifier|public
specifier|static
name|String
name|NEWLINE
init|=
name|System
operator|.
name|lineSeparator
argument_list|()
decl_stmt|;
comment|// Remote listener
DECL|field|remoteListener
specifier|public
specifier|static
name|RemoteListenerServerLifecycle
name|remoteListener
init|=
operator|new
name|RemoteListenerServerLifecycle
argument_list|()
decl_stmt|;
comment|// journal initialization
DECL|field|JOURNALS_FILE_BUILTIN
specifier|public
specifier|static
specifier|final
name|String
name|JOURNALS_FILE_BUILTIN
init|=
literal|"/journals/journalList.txt"
decl_stmt|;
DECL|field|JOURNALS_IEEE_INTERNAL_LIST
specifier|public
specifier|static
specifier|final
name|String
name|JOURNALS_IEEE_INTERNAL_LIST
init|=
literal|"/journals/IEEEJournalList.txt"
decl_stmt|;
DECL|field|journalAbbrev
specifier|public
specifier|static
name|JournalAbbreviationRepository
name|journalAbbrev
decl_stmt|;
DECL|method|initializeJournalNames ()
specifier|public
specifier|static
name|void
name|initializeJournalNames
parameter_list|()
block|{
comment|// Read internal lists:
name|Globals
operator|.
name|journalAbbrev
operator|=
operator|new
name|JournalAbbreviationRepository
argument_list|()
expr_stmt|;
name|Globals
operator|.
name|journalAbbrev
operator|.
name|readJournalListFromResource
argument_list|(
name|Globals
operator|.
name|JOURNALS_FILE_BUILTIN
argument_list|)
expr_stmt|;
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
name|USE_IEEE_ABRV
argument_list|)
condition|)
block|{
name|Globals
operator|.
name|journalAbbrev
operator|.
name|readJournalListFromResource
argument_list|(
name|JOURNALS_IEEE_INTERNAL_LIST
argument_list|)
expr_stmt|;
block|}
comment|// Read external lists, if any (in reverse order, so the upper lists
comment|// override the lower):
name|String
index|[]
name|lists
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getStringArray
argument_list|(
name|JabRefPreferences
operator|.
name|EXTERNAL_JOURNAL_LISTS
argument_list|)
decl_stmt|;
if|if
condition|(
name|lists
operator|!=
literal|null
operator|&&
name|lists
operator|.
name|length
operator|>
literal|0
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
name|lists
operator|.
name|length
operator|-
literal|1
init|;
name|i
operator|>=
literal|0
condition|;
name|i
operator|--
control|)
block|{
try|try
block|{
name|Globals
operator|.
name|journalAbbrev
operator|.
name|readJournalListFromFile
argument_list|(
operator|new
name|File
argument_list|(
name|lists
index|[
name|i
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|FileNotFoundException
name|e
parameter_list|)
block|{
comment|// The file couldn't be found... should we tell anyone?
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Cannot find file"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|// Read personal list, if set up:
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|PERSONAL_JOURNAL_LIST
argument_list|)
operator|!=
literal|null
condition|)
block|{
try|try
block|{
name|Globals
operator|.
name|journalAbbrev
operator|.
name|readJournalListFromFile
argument_list|(
operator|new
name|File
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|PERSONAL_JOURNAL_LIST
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|FileNotFoundException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Personal journal list file '"
operator|+
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|PERSONAL_JOURNAL_LIST
argument_list|)
operator|+
literal|"' not found."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|field|importFormatReader
specifier|public
specifier|static
specifier|final
name|ImportFormatReader
name|importFormatReader
init|=
operator|new
name|ImportFormatReader
argument_list|()
decl_stmt|;
DECL|field|handler
specifier|public
specifier|static
name|CacheableHandler
name|handler
decl_stmt|;
DECL|field|FILETYPE_PREFS_EXT
specifier|public
specifier|static
specifier|final
name|String
name|FILETYPE_PREFS_EXT
init|=
literal|"_dir"
decl_stmt|;
DECL|field|SELECTOR_META_PREFIX
specifier|public
specifier|static
specifier|final
name|String
name|SELECTOR_META_PREFIX
init|=
literal|"selector_"
decl_stmt|;
DECL|field|PROTECTED_FLAG_META
specifier|public
specifier|static
specifier|final
name|String
name|PROTECTED_FLAG_META
init|=
literal|"protectedFlag"
decl_stmt|;
DECL|field|NONE
specifier|public
specifier|static
specifier|final
name|String
name|NONE
init|=
literal|"_non__"
decl_stmt|;
DECL|field|FORMATTER_PACKAGE
specifier|public
specifier|static
specifier|final
name|String
name|FORMATTER_PACKAGE
init|=
literal|"net.sf.jabref.exporter.layout.format."
decl_stmt|;
comment|// In the main program, this field is initialized in JabRef.java
comment|// Each test case initializes this field if required
DECL|field|prefs
specifier|public
specifier|static
name|JabRefPreferences
name|prefs
decl_stmt|;
DECL|field|helpDiag
specifier|public
specifier|static
name|HelpDialog
name|helpDiag
decl_stmt|;
DECL|field|sidePaneManager
specifier|public
specifier|static
name|SidePaneManager
name|sidePaneManager
decl_stmt|;
DECL|field|SPECIAL_COMMAND_CHARS
specifier|public
specifier|static
specifier|final
name|String
name|SPECIAL_COMMAND_CHARS
init|=
literal|"\"`^~'c="
decl_stmt|;
comment|// Background tasks
DECL|field|focusListener
specifier|public
specifier|static
name|GlobalFocusListener
name|focusListener
decl_stmt|;
DECL|field|fileUpdateMonitor
specifier|public
specifier|static
name|FileUpdateMonitor
name|fileUpdateMonitor
decl_stmt|;
DECL|field|streamEavesdropper
specifier|public
specifier|static
name|StreamEavesdropper
name|streamEavesdropper
decl_stmt|;
DECL|method|startBackgroundTasks ()
specifier|public
specifier|static
name|void
name|startBackgroundTasks
parameter_list|()
block|{
name|Globals
operator|.
name|focusListener
operator|=
operator|new
name|GlobalFocusListener
argument_list|()
expr_stmt|;
name|Globals
operator|.
name|streamEavesdropper
operator|=
name|StreamEavesdropper
operator|.
name|eavesdropOnSystem
argument_list|()
expr_stmt|;
name|Globals
operator|.
name|fileUpdateMonitor
operator|=
operator|new
name|FileUpdateMonitor
argument_list|()
expr_stmt|;
name|JabRefExecutorService
operator|.
name|INSTANCE
operator|.
name|executeWithLowPriorityInOwnThread
argument_list|(
name|Globals
operator|.
name|fileUpdateMonitor
argument_list|,
literal|"FileUpdateMonitor"
argument_list|)
expr_stmt|;
block|}
comment|// Autosave manager
DECL|field|autoSaveManager
specifier|public
specifier|static
name|AutoSaveManager
name|autoSaveManager
decl_stmt|;
DECL|method|startAutoSaveManager (JabRefFrame frame)
specifier|public
specifier|static
name|void
name|startAutoSaveManager
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|Globals
operator|.
name|autoSaveManager
operator|=
operator|new
name|AutoSaveManager
argument_list|(
name|frame
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|autoSaveManager
operator|.
name|startAutoSaveTimer
argument_list|()
expr_stmt|;
block|}
comment|// Stop the autosave manager if it has been started
DECL|method|stopAutoSaveManager ()
specifier|public
specifier|static
name|void
name|stopAutoSaveManager
parameter_list|()
block|{
if|if
condition|(
name|Globals
operator|.
name|autoSaveManager
operator|!=
literal|null
condition|)
block|{
name|Globals
operator|.
name|autoSaveManager
operator|.
name|stopAutoSaveTimer
argument_list|()
expr_stmt|;
name|Globals
operator|.
name|autoSaveManager
operator|.
name|clearAutoSaves
argument_list|()
expr_stmt|;
name|Globals
operator|.
name|autoSaveManager
operator|=
literal|null
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

