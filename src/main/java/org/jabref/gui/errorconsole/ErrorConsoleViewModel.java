begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.errorconsole
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|errorconsole
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
name|net
operator|.
name|URISyntaxException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|DateFormat
import|;
end_import

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|SimpleDateFormat
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
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
name|Objects
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
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
name|ReadOnlyListWrapper
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|collections
operator|.
name|ObservableList
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
name|AbstractViewModel
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
name|ClipBoardManager
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
name|MappedList
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
name|desktop
operator|.
name|JabRefDesktop
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
name|logic
operator|.
name|logging
operator|.
name|LogMessages
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
name|BuildInfo
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
name|OS
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
name|org
operator|.
name|apache
operator|.
name|http
operator|.
name|client
operator|.
name|utils
operator|.
name|URIBuilder
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|logging
operator|.
name|log4j
operator|.
name|core
operator|.
name|LogEvent
import|;
end_import

begin_class
DECL|class|ErrorConsoleViewModel
specifier|public
class|class
name|ErrorConsoleViewModel
extends|extends
name|AbstractViewModel
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
name|ErrorConsoleViewModel
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|dateFormat
specifier|private
specifier|final
name|DateFormat
name|dateFormat
init|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"yyyyMMddHHmmss"
argument_list|)
decl_stmt|;
DECL|field|date
specifier|private
specifier|final
name|Date
name|date
init|=
operator|new
name|Date
argument_list|()
decl_stmt|;
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
decl_stmt|;
DECL|field|clipBoardManager
specifier|private
specifier|final
name|ClipBoardManager
name|clipBoardManager
decl_stmt|;
DECL|field|buildInfo
specifier|private
specifier|final
name|BuildInfo
name|buildInfo
decl_stmt|;
DECL|field|allMessagesData
specifier|private
specifier|final
name|ListProperty
argument_list|<
name|LogEventViewModel
argument_list|>
name|allMessagesData
decl_stmt|;
DECL|method|ErrorConsoleViewModel (DialogService dialogService, ClipBoardManager clipBoardManager, BuildInfo buildInfo)
specifier|public
name|ErrorConsoleViewModel
parameter_list|(
name|DialogService
name|dialogService
parameter_list|,
name|ClipBoardManager
name|clipBoardManager
parameter_list|,
name|BuildInfo
name|buildInfo
parameter_list|)
block|{
name|this
operator|.
name|dialogService
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|dialogService
argument_list|)
expr_stmt|;
name|this
operator|.
name|clipBoardManager
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|clipBoardManager
argument_list|)
expr_stmt|;
name|this
operator|.
name|buildInfo
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|buildInfo
argument_list|)
expr_stmt|;
name|ObservableList
argument_list|<
name|LogEventViewModel
argument_list|>
name|eventViewModels
init|=
operator|new
name|MappedList
argument_list|<>
argument_list|(
name|LogMessages
operator|.
name|getInstance
argument_list|()
operator|.
name|getMessages
argument_list|()
argument_list|,
name|LogEventViewModel
operator|::
operator|new
argument_list|)
decl_stmt|;
name|allMessagesData
operator|=
operator|new
name|ReadOnlyListWrapper
argument_list|<>
argument_list|(
name|eventViewModels
argument_list|)
expr_stmt|;
block|}
DECL|method|allMessagesDataProperty ()
specifier|public
name|ListProperty
argument_list|<
name|LogEventViewModel
argument_list|>
name|allMessagesDataProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|allMessagesData
return|;
block|}
comment|/**      * Concatenates the formatted message of the given {@link LogEvent}s by using the a new line separator.      *      * @return all messages as String      */
DECL|method|getLogMessagesAsString (List<LogEventViewModel> messages)
specifier|private
name|String
name|getLogMessagesAsString
parameter_list|(
name|List
argument_list|<
name|LogEventViewModel
argument_list|>
name|messages
parameter_list|)
block|{
return|return
name|messages
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|LogEventViewModel
operator|::
name|getDetailedText
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|joining
argument_list|(
name|OS
operator|.
name|NEWLINE
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * Copies the whole log to the clipboard      */
DECL|method|copyLog ()
specifier|public
name|void
name|copyLog
parameter_list|()
block|{
name|copyLog
argument_list|(
name|allMessagesData
argument_list|)
expr_stmt|;
block|}
comment|/**      * Copies the given list of {@link LogEvent}s to the clipboard.      */
DECL|method|copyLog (List<LogEventViewModel> messages)
specifier|public
name|void
name|copyLog
parameter_list|(
name|List
argument_list|<
name|LogEventViewModel
argument_list|>
name|messages
parameter_list|)
block|{
if|if
condition|(
name|messages
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return;
block|}
name|clipBoardManager
operator|.
name|setClipboardContents
argument_list|(
name|getLogMessagesAsString
argument_list|(
name|messages
argument_list|)
argument_list|)
expr_stmt|;
name|dialogService
operator|.
name|notify
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Log copied to clipboard."
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Clears the current log      */
DECL|method|clearLog ()
specifier|public
name|void
name|clearLog
parameter_list|()
block|{
name|LogMessages
operator|.
name|getInstance
argument_list|()
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
comment|/**      * Opens a new issue on GitHub and copies log to clipboard.      */
DECL|method|reportIssue ()
specifier|public
name|void
name|reportIssue
parameter_list|()
block|{
try|try
block|{
name|String
name|issueTitle
init|=
literal|"Automatic Bug Report - "
operator|+
name|dateFormat
operator|.
name|format
argument_list|(
name|date
argument_list|)
decl_stmt|;
comment|// system info
name|String
name|systemInfo
init|=
name|String
operator|.
name|format
argument_list|(
literal|"JabRef %s%n%s %s %s %nJava %s"
argument_list|,
name|buildInfo
operator|.
name|getVersion
argument_list|()
argument_list|,
name|BuildInfo
operator|.
name|OS
argument_list|,
name|BuildInfo
operator|.
name|OS_VERSION
argument_list|,
name|BuildInfo
operator|.
name|OS_ARCH
argument_list|,
name|BuildInfo
operator|.
name|JAVA_VERSION
argument_list|)
decl_stmt|;
comment|// steps to reproduce
name|String
name|howToReproduce
init|=
literal|"Steps to reproduce:\n\n1. ...\n2. ...\n3. ..."
decl_stmt|;
comment|// log messages
name|String
name|issueDetails
init|=
literal|"<details>\n"
operator|+
literal|"<summary>"
operator|+
literal|"Detail information:"
operator|+
literal|"</summary>\n\n```\n"
operator|+
name|getLogMessagesAsString
argument_list|(
name|allMessagesData
argument_list|)
operator|+
literal|"\n```\n\n</details>"
decl_stmt|;
name|clipBoardManager
operator|.
name|setClipboardContents
argument_list|(
name|issueDetails
argument_list|)
expr_stmt|;
comment|// bug report body
name|String
name|issueBody
init|=
name|systemInfo
operator|+
literal|"\n\n"
operator|+
name|howToReproduce
operator|+
literal|"\n\n"
operator|+
literal|"Paste your log details here."
decl_stmt|;
name|dialogService
operator|.
name|notify
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Issue on GitHub successfully reported."
argument_list|)
argument_list|)
expr_stmt|;
name|dialogService
operator|.
name|showInformationDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Issue report successful"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Your issue was reported in your browser."
argument_list|)
operator|+
literal|"\n"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"The log and exception information was copied to your clipboard."
argument_list|)
operator|+
literal|" "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Please paste this information (with Ctrl+V) in the issue description."
argument_list|)
operator|+
literal|"\n"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Please also add all steps to reproduce this issue, if possible."
argument_list|)
argument_list|)
expr_stmt|;
name|URIBuilder
name|uriBuilder
init|=
operator|new
name|URIBuilder
argument_list|()
operator|.
name|setScheme
argument_list|(
literal|"https"
argument_list|)
operator|.
name|setHost
argument_list|(
literal|"github.com"
argument_list|)
operator|.
name|setPath
argument_list|(
literal|"/JabRef/jabref/issues/new"
argument_list|)
operator|.
name|setParameter
argument_list|(
literal|"title"
argument_list|,
name|issueTitle
argument_list|)
operator|.
name|setParameter
argument_list|(
literal|"body"
argument_list|,
name|issueBody
argument_list|)
decl_stmt|;
name|JabRefDesktop
operator|.
name|openBrowser
argument_list|(
name|uriBuilder
operator|.
name|build
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
decl||
name|URISyntaxException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

