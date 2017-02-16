begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.shared.listener
package|package
name|org
operator|.
name|jabref
operator|.
name|shared
operator|.
name|listener
package|;
end_package

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|shared
operator|.
name|DBMSSynchronizer
import|;
end_import

begin_import
import|import
name|oracle
operator|.
name|jdbc
operator|.
name|dcn
operator|.
name|DatabaseChangeEvent
import|;
end_import

begin_import
import|import
name|oracle
operator|.
name|jdbc
operator|.
name|dcn
operator|.
name|DatabaseChangeListener
import|;
end_import

begin_comment
comment|/**  * A listener for Oracle database notifications.  */
end_comment

begin_class
DECL|class|OracleNotificationListener
specifier|public
class|class
name|OracleNotificationListener
implements|implements
name|DatabaseChangeListener
block|{
DECL|field|dbmsSynchronizer
specifier|private
specifier|final
name|DBMSSynchronizer
name|dbmsSynchronizer
decl_stmt|;
DECL|method|OracleNotificationListener (DBMSSynchronizer dbmsSynchronizer)
specifier|public
name|OracleNotificationListener
parameter_list|(
name|DBMSSynchronizer
name|dbmsSynchronizer
parameter_list|)
block|{
name|this
operator|.
name|dbmsSynchronizer
operator|=
name|dbmsSynchronizer
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|onDatabaseChangeNotification (DatabaseChangeEvent event)
specifier|public
name|void
name|onDatabaseChangeNotification
parameter_list|(
name|DatabaseChangeEvent
name|event
parameter_list|)
block|{
name|dbmsSynchronizer
operator|.
name|pullChanges
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

