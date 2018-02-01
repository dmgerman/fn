begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.actions
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|actions
package|;
end_package

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
name|BasePanel
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
name|BasePanelPreferences
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
name|IconTheme
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
name|JabRefFrame
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
name|auximport
operator|.
name|FromAuxDialog
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
name|externalfiletype
operator|.
name|ExternalFileTypes
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
name|model
operator|.
name|Defaults
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
operator|.
name|BibDatabaseContext
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
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionEvent
import|;
end_import

begin_comment
comment|/**  * The action concerned with generate a new (sub-)database from latex AUX file.  */
end_comment

begin_class
DECL|class|NewSubDatabaseAction
specifier|public
class|class
name|NewSubDatabaseAction
extends|extends
name|MnemonicAwareAction
block|{
DECL|field|jabRefFrame
specifier|private
specifier|final
name|JabRefFrame
name|jabRefFrame
decl_stmt|;
DECL|method|NewSubDatabaseAction (JabRefFrame jabRefFrame)
specifier|public
name|NewSubDatabaseAction
parameter_list|(
name|JabRefFrame
name|jabRefFrame
parameter_list|)
block|{
name|super
argument_list|(
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|NEW
operator|.
name|getIcon
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|jabRefFrame
operator|=
name|jabRefFrame
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|NAME
argument_list|,
name|Localization
operator|.
name|menuTitle
argument_list|(
literal|"New sublibrary based on AUX file"
argument_list|)
operator|+
literal|"..."
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|SHORT_DESCRIPTION
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"New BibTeX sublibrary"
argument_list|)
argument_list|)
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
comment|// Create a new, empty, database.
name|FromAuxDialog
name|dialog
init|=
operator|new
name|FromAuxDialog
argument_list|(
name|jabRefFrame
argument_list|,
literal|""
argument_list|,
literal|true
argument_list|,
name|jabRefFrame
operator|.
name|getTabbedPane
argument_list|()
argument_list|)
decl_stmt|;
name|dialog
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|dialog
operator|.
name|generatePressed
argument_list|()
condition|)
block|{
name|Defaults
name|defaults
init|=
operator|new
name|Defaults
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getDefaultBibDatabaseMode
argument_list|()
argument_list|)
decl_stmt|;
name|BasePanel
name|bp
init|=
operator|new
name|BasePanel
argument_list|(
name|jabRefFrame
argument_list|,
name|BasePanelPreferences
operator|.
name|from
argument_list|(
name|Globals
operator|.
name|prefs
argument_list|)
argument_list|,
operator|new
name|BibDatabaseContext
argument_list|(
name|dialog
operator|.
name|getGenerateDB
argument_list|()
argument_list|,
name|defaults
argument_list|)
argument_list|,
name|ExternalFileTypes
operator|.
name|getInstance
argument_list|()
argument_list|)
decl_stmt|;
name|jabRefFrame
operator|.
name|addTab
argument_list|(
name|bp
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|jabRefFrame
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"New library created."
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

