begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.help
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|help
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionEvent
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
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
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Stream
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|Action
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|Icon
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|KeyStroke
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
name|actions
operator|.
name|MnemonicAwareAction
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
name|actions
operator|.
name|SimpleCommand
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
name|gui
operator|.
name|icon
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
comment|/**  * This Action keeps a reference to a URL. When activated, it shows the help  * Dialog unless it is already visible, and shows the URL in it.  */
end_comment

begin_class
DECL|class|HelpAction
specifier|public
class|class
name|HelpAction
extends|extends
name|MnemonicAwareAction
block|{
comment|/**      * New languages of the help have to be added here      */
DECL|field|AVAIABLE_LANG_FILES
specifier|private
specifier|static
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|AVAIABLE_LANG_FILES
init|=
name|Stream
operator|.
name|of
argument_list|(
literal|"en"
argument_list|,
literal|"de"
argument_list|,
literal|"fr"
argument_list|,
literal|"in"
argument_list|,
literal|"ja"
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toCollection
argument_list|(
name|HashSet
operator|::
operator|new
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|helpPage
specifier|private
name|HelpFile
name|helpPage
decl_stmt|;
DECL|method|HelpAction (String title, String tooltip, HelpFile helpPage, KeyStroke key)
specifier|public
name|HelpAction
parameter_list|(
name|String
name|title
parameter_list|,
name|String
name|tooltip
parameter_list|,
name|HelpFile
name|helpPage
parameter_list|,
name|KeyStroke
name|key
parameter_list|)
block|{
name|this
argument_list|(
name|title
argument_list|,
name|tooltip
argument_list|,
name|helpPage
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|HELP
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|ACCELERATOR_KEY
argument_list|,
name|key
argument_list|)
expr_stmt|;
block|}
DECL|method|HelpAction (String title, String tooltip, HelpFile helpPage, Icon icon)
specifier|private
name|HelpAction
parameter_list|(
name|String
name|title
parameter_list|,
name|String
name|tooltip
parameter_list|,
name|HelpFile
name|helpPage
parameter_list|,
name|Icon
name|icon
parameter_list|)
block|{
name|super
argument_list|(
name|icon
argument_list|)
expr_stmt|;
name|this
operator|.
name|helpPage
operator|=
name|helpPage
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|NAME
argument_list|,
name|title
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|SHORT_DESCRIPTION
argument_list|,
name|tooltip
argument_list|)
expr_stmt|;
block|}
DECL|method|HelpAction (String tooltip, HelpFile helpPage)
specifier|public
name|HelpAction
parameter_list|(
name|String
name|tooltip
parameter_list|,
name|HelpFile
name|helpPage
parameter_list|)
block|{
name|this
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Help"
argument_list|)
argument_list|,
name|tooltip
argument_list|,
name|helpPage
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|HELP
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|HelpAction (HelpFile helpPage, Icon icon)
specifier|public
name|HelpAction
parameter_list|(
name|HelpFile
name|helpPage
parameter_list|,
name|Icon
name|icon
parameter_list|)
block|{
name|this
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Help"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Help"
argument_list|)
argument_list|,
name|helpPage
argument_list|,
name|icon
argument_list|)
expr_stmt|;
block|}
DECL|method|HelpAction (HelpFile helpPage)
specifier|public
name|HelpAction
parameter_list|(
name|HelpFile
name|helpPage
parameter_list|)
block|{
name|this
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Help"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Help"
argument_list|)
argument_list|,
name|helpPage
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|HELP
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|openHelpPage (HelpFile helpPage)
specifier|public
specifier|static
name|void
name|openHelpPage
parameter_list|(
name|HelpFile
name|helpPage
parameter_list|)
block|{
name|String
name|lang
init|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|LANGUAGE
argument_list|)
decl_stmt|;
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|(
literal|"https://help.jabref.org/"
argument_list|)
decl_stmt|;
if|if
condition|(
name|AVAIABLE_LANG_FILES
operator|.
name|contains
argument_list|(
name|lang
argument_list|)
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|lang
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"/"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"en/"
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
name|helpPage
operator|.
name|getPageName
argument_list|()
argument_list|)
expr_stmt|;
name|JabRefDesktop
operator|.
name|openBrowserShowPopup
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|setHelpFile (HelpFile urlPart)
specifier|public
name|void
name|setHelpFile
parameter_list|(
name|HelpFile
name|urlPart
parameter_list|)
block|{
name|this
operator|.
name|helpPage
operator|=
name|urlPart
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|openHelpPage
argument_list|(
name|helpPage
argument_list|)
expr_stmt|;
block|}
DECL|method|getMainHelpPageCommand ()
specifier|public
specifier|static
name|SimpleCommand
name|getMainHelpPageCommand
parameter_list|()
block|{
return|return
operator|new
name|SimpleCommand
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|execute
parameter_list|()
block|{
name|openHelpPage
argument_list|(
name|HelpFile
operator|.
name|CONTENTS
argument_list|)
expr_stmt|;
block|}
block|}
return|;
block|}
DECL|method|getCommand ()
specifier|public
name|SimpleCommand
name|getCommand
parameter_list|()
block|{
return|return
operator|new
name|SimpleCommand
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|execute
parameter_list|()
block|{
name|openHelpPage
argument_list|(
name|helpPage
argument_list|)
expr_stmt|;
block|}
block|}
return|;
block|}
block|}
end_class

end_unit

