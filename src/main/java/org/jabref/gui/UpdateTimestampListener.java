begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
package|;
end_package

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
name|event
operator|.
name|EntryChangedEvent
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
name|google
operator|.
name|common
operator|.
name|eventbus
operator|.
name|Subscribe
import|;
end_import

begin_comment
comment|/**  * Updates the timestamp of changed entries if the feature is enabled  */
end_comment

begin_class
DECL|class|UpdateTimestampListener
class|class
name|UpdateTimestampListener
block|{
DECL|field|jabRefPreferences
specifier|private
specifier|final
name|JabRefPreferences
name|jabRefPreferences
decl_stmt|;
DECL|method|UpdateTimestampListener (JabRefPreferences jabRefPreferences)
name|UpdateTimestampListener
parameter_list|(
name|JabRefPreferences
name|jabRefPreferences
parameter_list|)
block|{
name|this
operator|.
name|jabRefPreferences
operator|=
name|jabRefPreferences
expr_stmt|;
block|}
annotation|@
name|Subscribe
DECL|method|listen (EntryChangedEvent event)
specifier|public
name|void
name|listen
parameter_list|(
name|EntryChangedEvent
name|event
parameter_list|)
block|{
if|if
condition|(
name|jabRefPreferences
operator|.
name|getTimestampPreferences
argument_list|()
operator|.
name|includeTimestamps
argument_list|()
condition|)
block|{
name|event
operator|.
name|getBibEntry
argument_list|()
operator|.
name|setField
argument_list|(
name|jabRefPreferences
operator|.
name|getTimestampPreferences
argument_list|()
operator|.
name|getTimestampField
argument_list|()
argument_list|,
name|jabRefPreferences
operator|.
name|getTimestampPreferences
argument_list|()
operator|.
name|now
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit
