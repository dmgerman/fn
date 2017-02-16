begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.shared
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|shared
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
name|awt
operator|.
name|Component
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Dimension
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
name|ActionEvent
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
name|KeyEvent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|AbstractAction
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
name|Box
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|BoxLayout
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
name|JComponent
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
name|KeyStroke
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|border
operator|.
name|EmptyBorder
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
name|HelpAction
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

begin_class
DECL|class|MigrationHelpDialog
specifier|public
class|class
name|MigrationHelpDialog
extends|extends
name|JDialog
block|{
DECL|method|MigrationHelpDialog (ConnectToSharedDatabaseDialog openSharedDatabaseDialog)
specifier|public
name|MigrationHelpDialog
parameter_list|(
name|ConnectToSharedDatabaseDialog
name|openSharedDatabaseDialog
parameter_list|)
block|{
name|super
argument_list|(
name|openSharedDatabaseDialog
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Migration help information"
argument_list|)
argument_list|)
expr_stmt|;
name|setModal
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|String
name|migrationMessage
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Entered library has obsolete structure and is no longer supported."
argument_list|)
decl_stmt|;
name|JLabel
name|migrationLabel
init|=
operator|new
name|JLabel
argument_list|(
name|migrationMessage
argument_list|)
decl_stmt|;
name|migrationLabel
operator|.
name|setAlignmentX
argument_list|(
name|Component
operator|.
name|LEFT_ALIGNMENT
argument_list|)
expr_stmt|;
name|String
name|helpMessage
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Click here to learn about the migration of pre-3.6 libraries."
argument_list|)
decl_stmt|;
name|JLabel
name|helpLabel
init|=
operator|new
name|HelpAction
argument_list|(
name|HelpFile
operator|.
name|SQL_DATABASE_MIGRATION
argument_list|)
operator|.
name|getHelpLabel
argument_list|(
name|helpMessage
argument_list|)
decl_stmt|;
name|helpLabel
operator|.
name|setAlignmentX
argument_list|(
name|Component
operator|.
name|LEFT_ALIGNMENT
argument_list|)
expr_stmt|;
name|String
name|informationMessage
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"However, a new library was created alongside the pre-3.6 one."
argument_list|)
decl_stmt|;
name|JLabel
name|informationLabel
init|=
operator|new
name|JLabel
argument_list|(
name|informationMessage
argument_list|)
decl_stmt|;
name|informationLabel
operator|.
name|setAlignmentX
argument_list|(
name|Component
operator|.
name|LEFT_ALIGNMENT
argument_list|)
expr_stmt|;
name|Action
name|openAction
init|=
operator|new
name|AbstractAction
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|openSharedDatabaseDialog
operator|.
name|openSharedDatabase
argument_list|()
expr_stmt|;
block|}
block|}
decl_stmt|;
name|JButton
name|okButton
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"OK"
argument_list|)
argument_list|)
decl_stmt|;
name|okButton
operator|.
name|addActionListener
argument_list|(
name|openAction
argument_list|)
expr_stmt|;
name|okButton
operator|.
name|setAlignmentX
argument_list|(
name|Component
operator|.
name|CENTER_ALIGNMENT
argument_list|)
expr_stmt|;
name|okButton
operator|.
name|getInputMap
argument_list|(
name|JComponent
operator|.
name|WHEN_IN_FOCUSED_WINDOW
argument_list|)
operator|.
name|put
argument_list|(
name|KeyStroke
operator|.
name|getKeyStroke
argument_list|(
name|KeyEvent
operator|.
name|VK_ENTER
argument_list|,
literal|0
argument_list|)
argument_list|,
literal|"Enter_pressed"
argument_list|)
expr_stmt|;
name|okButton
operator|.
name|getActionMap
argument_list|()
operator|.
name|put
argument_list|(
literal|"Enter_pressed"
argument_list|,
name|openAction
argument_list|)
expr_stmt|;
name|JPanel
name|buttonPanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|buttonPanel
operator|.
name|add
argument_list|(
name|okButton
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|JPanel
name|contentPanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|contentPanel
operator|.
name|setBorder
argument_list|(
operator|new
name|EmptyBorder
argument_list|(
literal|9
argument_list|,
literal|9
argument_list|,
literal|9
argument_list|,
literal|9
argument_list|)
argument_list|)
expr_stmt|;
name|contentPanel
operator|.
name|setLayout
argument_list|(
operator|new
name|BoxLayout
argument_list|(
name|contentPanel
argument_list|,
name|BoxLayout
operator|.
name|Y_AXIS
argument_list|)
argument_list|)
expr_stmt|;
name|contentPanel
operator|.
name|add
argument_list|(
name|migrationLabel
argument_list|)
expr_stmt|;
name|contentPanel
operator|.
name|add
argument_list|(
name|Box
operator|.
name|createRigidArea
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|0
argument_list|,
literal|10
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|contentPanel
operator|.
name|add
argument_list|(
name|helpLabel
argument_list|)
expr_stmt|;
name|contentPanel
operator|.
name|add
argument_list|(
name|Box
operator|.
name|createRigidArea
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|0
argument_list|,
literal|10
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|contentPanel
operator|.
name|add
argument_list|(
name|informationLabel
argument_list|)
expr_stmt|;
name|contentPanel
operator|.
name|add
argument_list|(
name|Box
operator|.
name|createRigidArea
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|0
argument_list|,
literal|20
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|contentPanel
operator|.
name|add
argument_list|(
name|buttonPanel
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|contentPanel
argument_list|)
expr_stmt|;
name|setResizable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|pack
argument_list|()
expr_stmt|;
name|setLocationRelativeTo
argument_list|(
name|openSharedDatabaseDialog
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

