begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.model.groups.event
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|groups
operator|.
name|event
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
name|model
operator|.
name|database
operator|.
name|event
operator|.
name|BibDatabaseContextChangedEvent
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
name|metadata
operator|.
name|MetaData
import|;
end_import

begin_class
DECL|class|GroupUpdatedEvent
specifier|public
class|class
name|GroupUpdatedEvent
extends|extends
name|BibDatabaseContextChangedEvent
block|{
DECL|field|metaData
specifier|private
specifier|final
name|MetaData
name|metaData
decl_stmt|;
comment|/**      * @param metaData Affected instance      */
DECL|method|GroupUpdatedEvent (MetaData metaData)
specifier|public
name|GroupUpdatedEvent
parameter_list|(
name|MetaData
name|metaData
parameter_list|)
block|{
name|this
operator|.
name|metaData
operator|=
name|metaData
expr_stmt|;
block|}
DECL|method|getMetaData ()
specifier|public
name|MetaData
name|getMetaData
parameter_list|()
block|{
return|return
name|this
operator|.
name|metaData
return|;
block|}
block|}
end_class

end_unit

