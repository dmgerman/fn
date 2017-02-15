begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.model.metadata
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
package|;
end_package

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|InvalidPathException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Path
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Paths
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
name|FieldName
import|;
end_import

begin_class
DECL|class|FileDirectoryPreferences
specifier|public
class|class
name|FileDirectoryPreferences
block|{
DECL|field|DIR_SUFFIX
specifier|public
specifier|static
specifier|final
name|String
name|DIR_SUFFIX
init|=
literal|"Directory"
decl_stmt|;
DECL|field|user
specifier|private
specifier|final
name|String
name|user
decl_stmt|;
DECL|field|fieldFileDirectories
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|fieldFileDirectories
decl_stmt|;
DECL|field|bibLocationAsPrimary
specifier|private
specifier|final
name|boolean
name|bibLocationAsPrimary
decl_stmt|;
DECL|method|FileDirectoryPreferences (String user, Map<String, String> fieldFileDirectories, boolean bibLocationAsPrimary)
specifier|public
name|FileDirectoryPreferences
parameter_list|(
name|String
name|user
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|fieldFileDirectories
parameter_list|,
name|boolean
name|bibLocationAsPrimary
parameter_list|)
block|{
name|this
operator|.
name|user
operator|=
name|user
expr_stmt|;
name|this
operator|.
name|fieldFileDirectories
operator|=
name|fieldFileDirectories
expr_stmt|;
name|this
operator|.
name|bibLocationAsPrimary
operator|=
name|bibLocationAsPrimary
expr_stmt|;
block|}
DECL|method|getUser ()
specifier|public
name|String
name|getUser
parameter_list|()
block|{
return|return
name|user
return|;
block|}
DECL|method|getFileDirectory (String field)
specifier|public
name|Optional
argument_list|<
name|Path
argument_list|>
name|getFileDirectory
parameter_list|(
name|String
name|field
parameter_list|)
block|{
try|try
block|{
name|String
name|value
init|=
name|fieldFileDirectories
operator|.
name|get
argument_list|(
name|field
argument_list|)
decl_stmt|;
comment|// filter empty paths
if|if
condition|(
name|value
operator|!=
literal|null
operator|&&
operator|!
name|value
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Path
name|path
init|=
name|Paths
operator|.
name|get
argument_list|(
name|value
argument_list|)
decl_stmt|;
return|return
name|Optional
operator|.
name|of
argument_list|(
name|path
argument_list|)
return|;
block|}
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|InvalidPathException
name|ex
parameter_list|)
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
block|}
DECL|method|getFileDirectory ()
specifier|public
name|Optional
argument_list|<
name|Path
argument_list|>
name|getFileDirectory
parameter_list|()
block|{
return|return
name|getFileDirectory
argument_list|(
name|FieldName
operator|.
name|FILE
argument_list|)
return|;
block|}
DECL|method|isBibLocationAsPrimary ()
specifier|public
name|boolean
name|isBibLocationAsPrimary
parameter_list|()
block|{
return|return
name|bibLocationAsPrimary
return|;
block|}
block|}
end_class

end_unit
