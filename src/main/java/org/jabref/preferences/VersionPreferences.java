begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.preferences
package|package
name|org
operator|.
name|jabref
operator|.
name|preferences
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
name|util
operator|.
name|Version
import|;
end_import

begin_class
DECL|class|VersionPreferences
specifier|public
class|class
name|VersionPreferences
block|{
DECL|field|ignoredVersion
specifier|private
specifier|final
name|Version
name|ignoredVersion
decl_stmt|;
DECL|method|VersionPreferences (Version ignoredVersion)
specifier|public
name|VersionPreferences
parameter_list|(
name|Version
name|ignoredVersion
parameter_list|)
block|{
name|this
operator|.
name|ignoredVersion
operator|=
name|ignoredVersion
expr_stmt|;
block|}
DECL|method|getIgnoredVersion ()
specifier|public
name|Version
name|getIgnoredVersion
parameter_list|()
block|{
return|return
name|ignoredVersion
return|;
block|}
block|}
end_class

end_unit

