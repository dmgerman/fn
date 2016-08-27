begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.model.metadata.event
package|package
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
name|metadata
operator|.
name|MetaData
import|;
end_import

begin_comment
comment|/**  * {@link MetaDataChangedEvent} is fired when a tuple of meta data has been put or removed.  */
end_comment

begin_class
DECL|class|MetaDataChangedEvent
specifier|public
class|class
name|MetaDataChangedEvent
block|{
DECL|field|metaData
specifier|private
specifier|final
name|MetaData
name|metaData
decl_stmt|;
comment|/**      * @param metaData Affected instance      */
DECL|method|MetaDataChangedEvent (MetaData metaData)
specifier|public
name|MetaDataChangedEvent
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

