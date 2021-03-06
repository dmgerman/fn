begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.bibtex.comparator
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|bibtex
operator|.
name|comparator
package|;
end_package

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
name|org
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
DECL|class|MetaDataDiff
specifier|public
class|class
name|MetaDataDiff
block|{
DECL|field|groupDiff
specifier|private
specifier|final
name|Optional
argument_list|<
name|GroupDiff
argument_list|>
name|groupDiff
decl_stmt|;
DECL|field|newMetaData
specifier|private
name|MetaData
name|newMetaData
decl_stmt|;
DECL|method|MetaDataDiff (MetaData originalMetaData, MetaData newMetaData)
specifier|private
name|MetaDataDiff
parameter_list|(
name|MetaData
name|originalMetaData
parameter_list|,
name|MetaData
name|newMetaData
parameter_list|)
block|{
name|this
operator|.
name|newMetaData
operator|=
name|newMetaData
expr_stmt|;
name|this
operator|.
name|groupDiff
operator|=
name|GroupDiff
operator|.
name|compare
argument_list|(
name|originalMetaData
argument_list|,
name|newMetaData
argument_list|)
expr_stmt|;
block|}
DECL|method|compare (MetaData originalMetaData, MetaData newMetaData)
specifier|public
specifier|static
name|Optional
argument_list|<
name|MetaDataDiff
argument_list|>
name|compare
parameter_list|(
name|MetaData
name|originalMetaData
parameter_list|,
name|MetaData
name|newMetaData
parameter_list|)
block|{
if|if
condition|(
name|originalMetaData
operator|.
name|equals
argument_list|(
name|newMetaData
argument_list|)
condition|)
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
else|else
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|MetaDataDiff
argument_list|(
name|originalMetaData
argument_list|,
name|newMetaData
argument_list|)
argument_list|)
return|;
block|}
block|}
DECL|method|getNewMetaData ()
specifier|public
name|MetaData
name|getNewMetaData
parameter_list|()
block|{
return|return
name|newMetaData
return|;
block|}
DECL|method|getGroupDifferences ()
specifier|public
name|Optional
argument_list|<
name|GroupDiff
argument_list|>
name|getGroupDifferences
parameter_list|()
block|{
return|return
name|groupDiff
return|;
block|}
block|}
end_class

end_unit

