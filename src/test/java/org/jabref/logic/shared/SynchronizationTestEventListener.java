begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.shared
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|shared
package|;
end_package

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|shared
operator|.
name|event
operator|.
name|SharedEntryNotPresentEvent
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
name|shared
operator|.
name|event
operator|.
name|UpdateRefusedEvent
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|testutils
operator|.
name|category
operator|.
name|DatabaseTest
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

begin_class
annotation|@
name|DatabaseTest
DECL|class|SynchronizationTestEventListener
specifier|public
class|class
name|SynchronizationTestEventListener
block|{
DECL|field|sharedEntryNotPresentEvent
specifier|private
name|SharedEntryNotPresentEvent
name|sharedEntryNotPresentEvent
decl_stmt|;
DECL|field|updateRefusedEvent
specifier|private
name|UpdateRefusedEvent
name|updateRefusedEvent
decl_stmt|;
annotation|@
name|Subscribe
DECL|method|listen (SharedEntryNotPresentEvent event)
specifier|public
name|void
name|listen
parameter_list|(
name|SharedEntryNotPresentEvent
name|event
parameter_list|)
block|{
name|this
operator|.
name|sharedEntryNotPresentEvent
operator|=
name|event
expr_stmt|;
block|}
annotation|@
name|Subscribe
DECL|method|listen (UpdateRefusedEvent event)
specifier|public
name|void
name|listen
parameter_list|(
name|UpdateRefusedEvent
name|event
parameter_list|)
block|{
name|this
operator|.
name|updateRefusedEvent
operator|=
name|event
expr_stmt|;
block|}
DECL|method|getSharedEntryNotPresentEvent ()
specifier|public
name|SharedEntryNotPresentEvent
name|getSharedEntryNotPresentEvent
parameter_list|()
block|{
return|return
name|sharedEntryNotPresentEvent
return|;
block|}
DECL|method|getUpdateRefusedEvent ()
specifier|public
name|UpdateRefusedEvent
name|getUpdateRefusedEvent
parameter_list|()
block|{
return|return
name|updateRefusedEvent
return|;
block|}
block|}
end_class

end_unit

