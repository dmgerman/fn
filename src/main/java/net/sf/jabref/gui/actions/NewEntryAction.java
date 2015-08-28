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
name|strings
operator|.
name|StringUtil
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
name|util
operator|.
name|Util
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

begin_class
DECL|class|NewEntryAction
specifier|public
class|class
name|NewEntryAction
extends|extends
name|MnemonicAwareAction
block|{
DECL|field|LOGGER
specifier|private
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|NewEntryAction
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|jabRefFrame
specifier|private
name|JabRefFrame
name|jabRefFrame
decl_stmt|;
DECL|field|type
name|String
name|type
decl_stmt|;
comment|// The type of item to create.
DECL|field|keyStroke
name|KeyStroke
name|keyStroke
decl_stmt|;
comment|// Used for the specific instances.
DECL|method|NewEntryAction (JabRefFrame jabRefFrame, KeyStroke key)
specifier|public
name|NewEntryAction
parameter_list|(
name|JabRefFrame
name|jabRefFrame
parameter_list|,
name|KeyStroke
name|key
parameter_list|)
block|{
comment|// This action leads to a dialog asking for entry type.
name|super
argument_list|(
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"add"
argument_list|)
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
literal|"New entry"
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
literal|"New BibTeX entry"
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|NewEntryAction (JabRefFrame jabRefFrame, String type_)
specifier|public
name|NewEntryAction
parameter_list|(
name|JabRefFrame
name|jabRefFrame
parameter_list|,
name|String
name|type_
parameter_list|)
block|{
name|this
operator|.
name|jabRefFrame
operator|=
name|jabRefFrame
expr_stmt|;
comment|// This action leads to the creation of a specific entry.
name|putValue
argument_list|(
name|Action
operator|.
name|NAME
argument_list|,
name|StringUtil
operator|.
name|capitalizeFirst
argument_list|(
name|type_
argument_list|)
argument_list|)
expr_stmt|;
name|type
operator|=
name|type_
expr_stmt|;
block|}
DECL|method|NewEntryAction (JabRefFrame jabRefFrame, String type_, KeyStroke key)
specifier|public
name|NewEntryAction
parameter_list|(
name|JabRefFrame
name|jabRefFrame
parameter_list|,
name|String
name|type_
parameter_list|,
name|KeyStroke
name|key
parameter_list|)
block|{
name|this
operator|.
name|jabRefFrame
operator|=
name|jabRefFrame
expr_stmt|;
comment|// This action leads to the creation of a specific entry.
name|putValue
argument_list|(
name|Action
operator|.
name|NAME
argument_list|,
name|StringUtil
operator|.
name|capitalizeFirst
argument_list|(
name|type_
argument_list|)
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
name|type
operator|=
name|type_
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
name|String
name|thisType
init|=
name|type
decl_stmt|;
if|if
condition|(
name|thisType
operator|==
literal|null
condition|)
block|{
name|EntryTypeDialog
name|etd
init|=
operator|new
name|EntryTypeDialog
argument_list|(
name|jabRefFrame
argument_list|)
decl_stmt|;
name|Util
operator|.
name|placeDialog
argument_list|(
name|etd
argument_list|,
name|jabRefFrame
argument_list|)
expr_stmt|;
name|etd
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|BibtexEntryType
name|tp
init|=
name|etd
operator|.
name|getChoice
argument_list|()
decl_stmt|;
if|if
condition|(
name|tp
operator|==
literal|null
condition|)
block|{
return|return;
block|}
name|thisType
operator|=
name|tp
operator|.
name|getName
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|jabRefFrame
operator|.
name|tabbedPane
operator|.
name|getTabCount
argument_list|()
operator|>
literal|0
condition|)
block|{
operator|(
operator|(
name|BasePanel
operator|)
name|jabRefFrame
operator|.
name|tabbedPane
operator|.
name|getSelectedComponent
argument_list|()
operator|)
operator|.
name|newEntry
argument_list|(
name|BibtexEntryType
operator|.
name|getType
argument_list|(
name|thisType
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Action 'New entry' must be disabled when no "
operator|+
literal|"database is open."
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

