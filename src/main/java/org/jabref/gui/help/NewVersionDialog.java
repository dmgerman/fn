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
name|Cursor
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|GridBagConstraints
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|GridBagLayout
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Insets
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|MouseEvent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JButton
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JDialog
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JFrame
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
name|event
operator|.
name|MouseInputAdapter
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
name|util
operator|.
name|Version
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
name|VersionPreferences
import|;
end_import

begin_class
DECL|class|NewVersionDialog
specifier|public
class|class
name|NewVersionDialog
extends|extends
name|JDialog
block|{
DECL|method|NewVersionDialog (JFrame frame, Version currentVersion, Version latestVersion)
specifier|public
name|NewVersionDialog
parameter_list|(
name|JFrame
name|frame
parameter_list|,
name|Version
name|currentVersion
parameter_list|,
name|Version
name|latestVersion
parameter_list|)
block|{
name|super
argument_list|(
name|frame
argument_list|)
expr_stmt|;
name|setTitle
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"New version available"
argument_list|)
argument_list|)
expr_stmt|;
name|JLabel
name|lblTitle
init|=
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"A new version of JabRef has been released."
argument_list|)
argument_list|)
decl_stmt|;
name|JLabel
name|lblCurrentVersion
init|=
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Installed version"
argument_list|)
operator|+
literal|": "
operator|+
name|currentVersion
operator|.
name|getFullVersion
argument_list|()
argument_list|)
decl_stmt|;
name|JLabel
name|lblLatestVersion
init|=
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Latest version"
argument_list|)
operator|+
literal|": "
operator|+
name|latestVersion
operator|.
name|getFullVersion
argument_list|()
argument_list|)
decl_stmt|;
name|String
name|localization
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"To see what is new view the changelog."
argument_list|)
decl_stmt|;
name|JLabel
name|lblMoreInformation
init|=
operator|new
name|JLabel
argument_list|(
literal|"<HTML><a href="
operator|+
name|latestVersion
operator|.
name|getChangelogUrl
argument_list|()
operator|+
literal|"'>"
operator|+
name|localization
operator|+
literal|"</a></HTML>"
argument_list|)
decl_stmt|;
name|lblMoreInformation
operator|.
name|setCursor
argument_list|(
name|Cursor
operator|.
name|getPredefinedCursor
argument_list|(
name|Cursor
operator|.
name|HAND_CURSOR
argument_list|)
argument_list|)
expr_stmt|;
name|lblMoreInformation
operator|.
name|addMouseListener
argument_list|(
operator|new
name|MouseInputAdapter
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|mouseClicked
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{
name|JabRefDesktop
operator|.
name|openBrowserShowPopup
argument_list|(
name|latestVersion
operator|.
name|getChangelogUrl
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|JButton
name|btnIgnoreUpdate
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Ignore this update"
argument_list|)
argument_list|)
decl_stmt|;
name|btnIgnoreUpdate
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
name|Globals
operator|.
name|prefs
operator|.
name|storeVersionPreferences
argument_list|(
operator|new
name|VersionPreferences
argument_list|(
name|latestVersion
argument_list|)
argument_list|)
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|JButton
name|btnDownloadUpdate
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Download update"
argument_list|)
argument_list|)
decl_stmt|;
name|btnDownloadUpdate
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
name|JabRefDesktop
operator|.
name|openBrowserShowPopup
argument_list|(
name|Version
operator|.
name|JABREF_DOWNLOAD_URL
argument_list|)
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|JButton
name|btnRemindMeLater
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remind me later"
argument_list|)
argument_list|)
decl_stmt|;
name|btnRemindMeLater
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|dispose
argument_list|()
argument_list|)
expr_stmt|;
name|JPanel
name|panel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|panel
operator|.
name|setLayout
argument_list|(
operator|new
name|GridBagLayout
argument_list|()
argument_list|)
expr_stmt|;
name|GridBagConstraints
name|c
init|=
operator|new
name|GridBagConstraints
argument_list|()
decl_stmt|;
name|c
operator|.
name|gridheight
operator|=
literal|1
expr_stmt|;
name|c
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|BOTH
expr_stmt|;
name|c
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|2
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|,
literal|2
argument_list|)
expr_stmt|;
name|c
operator|.
name|gridx
operator|=
name|c
operator|.
name|gridy
operator|=
literal|0
expr_stmt|;
name|c
operator|.
name|gridwidth
operator|=
literal|3
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|lblTitle
argument_list|,
name|c
argument_list|)
expr_stmt|;
name|c
operator|.
name|gridy
operator|=
literal|1
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|lblCurrentVersion
argument_list|,
name|c
argument_list|)
expr_stmt|;
name|c
operator|.
name|gridy
operator|=
literal|2
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|lblLatestVersion
argument_list|,
name|c
argument_list|)
expr_stmt|;
name|c
operator|.
name|gridy
operator|=
literal|3
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|lblMoreInformation
argument_list|,
name|c
argument_list|)
expr_stmt|;
name|c
operator|.
name|gridy
operator|=
literal|4
expr_stmt|;
name|c
operator|.
name|gridx
operator|=
literal|0
expr_stmt|;
name|c
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|btnDownloadUpdate
argument_list|,
name|c
argument_list|)
expr_stmt|;
name|c
operator|.
name|gridx
operator|=
literal|1
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|btnIgnoreUpdate
argument_list|,
name|c
argument_list|)
expr_stmt|;
name|c
operator|.
name|gridx
operator|=
literal|2
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|btnRemindMeLater
argument_list|,
name|c
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|panel
argument_list|)
expr_stmt|;
name|pack
argument_list|()
expr_stmt|;
name|setLocationRelativeTo
argument_list|(
name|frame
argument_list|)
expr_stmt|;
name|setModalityType
argument_list|(
name|ModalityType
operator|.
name|APPLICATION_MODAL
argument_list|)
expr_stmt|;
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

