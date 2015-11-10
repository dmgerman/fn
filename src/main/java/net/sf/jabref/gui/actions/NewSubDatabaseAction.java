begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.actions
package|package
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|Globals
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
name|JabRefPreferences
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
name|MetaData
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
name|gui
operator|.
name|util
operator|.
name|PositionWindow
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
name|wizard
operator|.
name|auximport
operator|.
name|gui
operator|.
name|FromAuxDialog
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
comment|/**  * The action concerned with generate a new (sub-)database from latex aux file.  */
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
name|JabRefIcon
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
literal|"New subdatabase based on AUX file"
argument_list|)
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
literal|"New BibTeX subdatabase"
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
name|tabbedPane
argument_list|)
decl_stmt|;
name|PositionWindow
operator|.
name|placeDialog
argument_list|(
name|dialog
argument_list|,
name|jabRefFrame
argument_list|)
expr_stmt|;
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
name|BasePanel
name|bp
init|=
operator|new
name|BasePanel
argument_list|(
name|jabRefFrame
argument_list|,
name|dialog
operator|.
name|getGenerateDB
argument_list|()
argument_list|,
comment|// database
literal|null
argument_list|,
comment|// file
operator|new
name|MetaData
argument_list|()
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|DEFAULT_ENCODING
argument_list|)
argument_list|)
decl_stmt|;
comment|// meta data
name|jabRefFrame
operator|.
name|tabbedPane
operator|.
name|add
argument_list|(
name|GUIGlobals
operator|.
name|untitledTitle
argument_list|,
name|bp
argument_list|)
expr_stmt|;
name|jabRefFrame
operator|.
name|tabbedPane
operator|.
name|setSelectedComponent
argument_list|(
name|bp
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
literal|"New database created."
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

