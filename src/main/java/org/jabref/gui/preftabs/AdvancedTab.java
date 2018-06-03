begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.preftabs
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|preftabs
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|BorderLayout
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|BorderFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JCheckBox
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
name|javax
operator|.
name|swing
operator|.
name|JTextField
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
name|help
operator|.
name|HelpAction
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
name|remote
operator|.
name|JabRefMessageHandler
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
name|util
operator|.
name|DefaultTaskExecutor
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
name|help
operator|.
name|HelpFile
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
name|remote
operator|.
name|RemotePreferences
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
name|remote
operator|.
name|RemoteUtil
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

begin_class
DECL|class|AdvancedTab
class|class
name|AdvancedTab
extends|extends
name|JPanel
implements|implements
name|PrefsTab
block|{
DECL|field|preferences
specifier|private
specifier|final
name|JabRefPreferences
name|preferences
decl_stmt|;
DECL|field|useRemoteServer
specifier|private
specifier|final
name|JCheckBox
name|useRemoteServer
decl_stmt|;
DECL|field|useIEEEAbrv
specifier|private
specifier|final
name|JCheckBox
name|useIEEEAbrv
decl_stmt|;
DECL|field|remoteServerPort
specifier|private
specifier|final
name|JTextField
name|remoteServerPort
decl_stmt|;
DECL|field|useCaseKeeperOnSearch
specifier|private
specifier|final
name|JCheckBox
name|useCaseKeeperOnSearch
decl_stmt|;
DECL|field|useUnitFormatterOnSearch
specifier|private
specifier|final
name|JCheckBox
name|useUnitFormatterOnSearch
decl_stmt|;
DECL|field|remotePreferences
specifier|private
specifier|final
name|RemotePreferences
name|remotePreferences
decl_stmt|;
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
decl_stmt|;
DECL|method|AdvancedTab (DialogService dialogService, JabRefPreferences prefs)
specifier|public
name|AdvancedTab
parameter_list|(
name|DialogService
name|dialogService
parameter_list|,
name|JabRefPreferences
name|prefs
parameter_list|)
block|{
name|this
operator|.
name|dialogService
operator|=
name|dialogService
expr_stmt|;
name|preferences
operator|=
name|prefs
expr_stmt|;
name|remotePreferences
operator|=
name|prefs
operator|.
name|getRemotePreferences
argument_list|()
expr_stmt|;
name|useRemoteServer
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Listen for remote operation on port"
argument_list|)
operator|+
literal|':'
argument_list|)
expr_stmt|;
name|useIEEEAbrv
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Use IEEE LaTeX abbreviations"
argument_list|)
argument_list|)
expr_stmt|;
name|remoteServerPort
operator|=
operator|new
name|JTextField
argument_list|()
expr_stmt|;
name|useCaseKeeperOnSearch
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Add {} to specified title words on search to keep the correct case"
argument_list|)
argument_list|)
expr_stmt|;
name|useUnitFormatterOnSearch
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Format units by adding non-breaking separators and keeping the correct case on search"
argument_list|)
argument_list|)
expr_stmt|;
name|FormLayout
name|layout
init|=
operator|new
name|FormLayout
argument_list|(
literal|"1dlu, 8dlu, left:pref, 4dlu, fill:3dlu"
argument_list|,
comment|//, 4dlu, fill:pref",// 4dlu, left:pref, 4dlu",
literal|""
argument_list|)
decl_stmt|;
name|DefaultFormBuilder
name|builder
init|=
operator|new
name|DefaultFormBuilder
argument_list|(
name|layout
argument_list|)
decl_stmt|;
name|JPanel
name|pan
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|builder
operator|.
name|appendSeparator
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remote operation"
argument_list|)
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
operator|new
name|JPanel
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
operator|new
name|JLabel
argument_list|(
literal|"<html>"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"This feature lets new files be opened or imported into an "
operator|+
literal|"already running instance of JabRef<BR>instead of opening a new instance. For instance, this "
operator|+
literal|"is useful when you open a file in JabRef<br>from your web browser."
operator|+
literal|"<BR>Note that this will prevent you from running more than one instance of JabRef at a time."
argument_list|)
operator|+
literal|"</html>"
argument_list|)
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
operator|new
name|JPanel
argument_list|()
argument_list|)
expr_stmt|;
name|JPanel
name|p
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|p
operator|.
name|add
argument_list|(
name|useRemoteServer
argument_list|)
expr_stmt|;
name|p
operator|.
name|add
argument_list|(
name|remoteServerPort
argument_list|)
expr_stmt|;
name|p
operator|.
name|add
argument_list|(
operator|new
name|HelpAction
argument_list|(
name|HelpFile
operator|.
name|REMOTE
argument_list|)
operator|.
name|getHelpButton
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|p
argument_list|)
expr_stmt|;
comment|// IEEE
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|appendSeparator
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Search %0"
argument_list|,
literal|"IEEEXplore"
argument_list|)
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
operator|new
name|JPanel
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|useIEEEAbrv
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|appendSeparator
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Import conversions"
argument_list|)
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
name|pan
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|useCaseKeeperOnSearch
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
name|pan
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|useUnitFormatterOnSearch
argument_list|)
expr_stmt|;
name|pan
operator|=
name|builder
operator|.
name|getPanel
argument_list|()
expr_stmt|;
name|pan
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|)
argument_list|)
expr_stmt|;
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|pan
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|setValues ()
specifier|public
name|void
name|setValues
parameter_list|()
block|{
name|useRemoteServer
operator|.
name|setSelected
argument_list|(
name|remotePreferences
operator|.
name|useRemoteServer
argument_list|()
argument_list|)
expr_stmt|;
name|remoteServerPort
operator|.
name|setText
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|remotePreferences
operator|.
name|getPort
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|useIEEEAbrv
operator|.
name|setSelected
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getJournalAbbreviationPreferences
argument_list|()
operator|.
name|useIEEEAbbreviations
argument_list|()
argument_list|)
expr_stmt|;
name|useCaseKeeperOnSearch
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
name|USE_CASE_KEEPER_ON_SEARCH
argument_list|)
argument_list|)
expr_stmt|;
name|useUnitFormatterOnSearch
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
name|USE_UNIT_FORMATTER_ON_SEARCH
argument_list|)
argument_list|)
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
name|JournalAbbreviationPreferences
name|journalAbbreviationPreferences
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getJournalAbbreviationPreferences
argument_list|()
decl_stmt|;
if|if
condition|(
name|journalAbbreviationPreferences
operator|.
name|useIEEEAbbreviations
argument_list|()
operator|!=
name|useIEEEAbrv
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|journalAbbreviationPreferences
operator|.
name|setUseIEEEAbbreviations
argument_list|(
name|useIEEEAbrv
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|storeJournalAbbreviationPreferences
argument_list|(
name|journalAbbreviationPreferences
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|journalAbbreviationLoader
operator|.
name|update
argument_list|(
name|journalAbbreviationPreferences
argument_list|)
expr_stmt|;
block|}
name|storeRemoteSettings
argument_list|()
expr_stmt|;
name|preferences
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_CASE_KEEPER_ON_SEARCH
argument_list|,
name|useCaseKeeperOnSearch
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|preferences
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_UNIT_FORMATTER_ON_SEARCH
argument_list|,
name|useUnitFormatterOnSearch
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|storeRemoteSettings ()
specifier|private
name|void
name|storeRemoteSettings
parameter_list|()
block|{
name|getPortAsInt
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|newPort
lambda|->
block|{
if|if
condition|(
name|remotePreferences
operator|.
name|isDifferentPort
argument_list|(
name|newPort
argument_list|)
condition|)
block|{
name|remotePreferences
operator|.
name|setPort
argument_list|(
name|newPort
argument_list|)
expr_stmt|;
if|if
condition|(
name|remotePreferences
operator|.
name|useRemoteServer
argument_list|()
condition|)
block|{
name|DefaultTaskExecutor
operator|.
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
name|dialogService
operator|.
name|showWarningDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remote server port"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remote server port"
argument_list|)
operator|.
name|concat
argument_list|(
literal|" "
argument_list|)
operator|.
name|concat
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"You must restart JabRef for this to come into effect."
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|remotePreferences
operator|.
name|setUseRemoteServer
argument_list|(
name|useRemoteServer
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
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
name|openAndStart
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
block|}
else|else
block|{
name|Globals
operator|.
name|REMOTE_LISTENER
operator|.
name|stop
argument_list|()
expr_stmt|;
block|}
name|preferences
operator|.
name|setRemotePreferences
argument_list|(
name|remotePreferences
argument_list|)
expr_stmt|;
block|}
DECL|method|getPortAsInt ()
specifier|private
name|Optional
argument_list|<
name|Integer
argument_list|>
name|getPortAsInt
parameter_list|()
block|{
try|try
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|remoteServerPort
operator|.
name|getText
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|ex
parameter_list|)
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
block|}
annotation|@
name|Override
DECL|method|validateSettings ()
specifier|public
name|boolean
name|validateSettings
parameter_list|()
block|{
try|try
block|{
name|int
name|portNumber
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|remoteServerPort
operator|.
name|getText
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|RemoteUtil
operator|.
name|isUserPort
argument_list|(
name|portNumber
argument_list|)
condition|)
block|{
return|return
literal|true
return|;
block|}
else|else
block|{
throw|throw
operator|new
name|NumberFormatException
argument_list|()
throw|;
block|}
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|ex
parameter_list|)
block|{
name|DefaultTaskExecutor
operator|.
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remote server port"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"You must enter an integer value in the interval 1025-65535 in the text field for"
argument_list|)
operator|+
literal|" '"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remote server port"
argument_list|)
operator|+
literal|'\''
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
block|}
annotation|@
name|Override
DECL|method|getTabName ()
specifier|public
name|String
name|getTabName
parameter_list|()
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Advanced"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

