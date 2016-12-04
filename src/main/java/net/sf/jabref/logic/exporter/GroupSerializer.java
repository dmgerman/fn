begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.exporter
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|exporter
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
name|logic
operator|.
name|util
operator|.
name|MetadataSerializationConfiguration
import|;
end_import

begin_comment
comment|/**  * Created by Tobias on 12/5/2016.  */
end_comment

begin_class
DECL|class|GroupSerializer
specifier|public
class|class
name|GroupSerializer
block|{
DECL|method|serializeAllEntriesGroup ()
specifier|public
specifier|static
name|String
name|serializeAllEntriesGroup
parameter_list|()
block|{
return|return
name|MetadataSerializationConfiguration
operator|.
name|ALL_ENTRIES_GROUP_ID
return|;
block|}
block|}
end_class

end_unit

