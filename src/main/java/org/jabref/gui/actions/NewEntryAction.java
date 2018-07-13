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
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
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
name|SwingUtilities
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
name|EntryTypeDialog
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
name|model
operator|.
name|entry
operator|.
name|EntryType
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_class
DECL|class|NewEntryAction
specifier|public
class|class
name|NewEntryAction
extends|extends
name|SimpleCommand
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Logger
name|LOGGER
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|NewEntryAction
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|jabRefFrame
specifier|private
specifier|final
name|JabRefFrame
name|jabRefFrame
decl_stmt|;
comment|/**      * The type of the entry to create.      */
DECL|field|type
specifier|private
specifier|final
name|Optional
argument_list|<
name|EntryType
argument_list|>
name|type
decl_stmt|;
DECL|method|NewEntryAction (JabRefFrame jabRefFrame)
specifier|public
name|NewEntryAction
parameter_list|(
name|JabRefFrame
name|jabRefFrame
parameter_list|)
block|{
name|this
operator|.
name|jabRefFrame
operator|=
name|jabRefFrame
expr_stmt|;
name|this
operator|.
name|type
operator|=
name|Optional
operator|.
name|empty
argument_list|()
expr_stmt|;
block|}
DECL|method|NewEntryAction (JabRefFrame jabRefFrame, EntryType type)
specifier|public
name|NewEntryAction
parameter_list|(
name|JabRefFrame
name|jabRefFrame
parameter_list|,
name|EntryType
name|type
parameter_list|)
block|{
name|this
operator|.
name|jabRefFrame
operator|=
name|jabRefFrame
expr_stmt|;
name|this
operator|.
name|type
operator|=
name|Optional
operator|.
name|of
argument_list|(
name|type
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|execute ()
specifier|public
name|void
name|execute
parameter_list|()
block|{
if|if
condition|(
name|jabRefFrame
operator|.
name|getBasePanelCount
argument_list|()
operator|<=
literal|0
condition|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Action 'New entry' must be disabled when no database is open."
argument_list|)
expr_stmt|;
return|return;
block|}
if|if
condition|(
name|type
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|jabRefFrame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|newEntry
argument_list|(
name|type
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
parameter_list|()
lambda|->
block|{
name|EntryTypeDialog
name|typeChoiceDialog
init|=
operator|new
name|EntryTypeDialog
argument_list|(
name|jabRefFrame
argument_list|)
decl_stmt|;
name|typeChoiceDialog
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|EntryType
name|selectedType
init|=
name|typeChoiceDialog
operator|.
name|getChoice
argument_list|()
decl_stmt|;
if|if
condition|(
name|selectedType
operator|==
literal|null
condition|)
block|{
return|return;
block|}
name|trackNewEntry
argument_list|(
name|selectedType
argument_list|)
expr_stmt|;
name|DefaultTaskExecutor
operator|.
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
name|jabRefFrame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|newEntry
argument_list|(
name|selectedType
argument_list|)
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|trackNewEntry (EntryType type)
specifier|private
name|void
name|trackNewEntry
parameter_list|(
name|EntryType
name|type
parameter_list|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|properties
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
name|properties
operator|.
name|put
argument_list|(
literal|"EntryType"
argument_list|,
name|type
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|Double
argument_list|>
name|measurements
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
name|Globals
operator|.
name|getTelemetryClient
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|client
lambda|->
name|client
operator|.
name|trackEvent
argument_list|(
literal|"NewEntry"
argument_list|,
name|properties
argument_list|,
name|measurements
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

