begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.cleanup
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|cleanup
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
name|layout
operator|.
name|LayoutFormatterPreferences
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
name|FileDirectoryPreferences
import|;
end_import

begin_class
DECL|class|CleanupPreferences
specifier|public
class|class
name|CleanupPreferences
block|{
DECL|field|fileNamePattern
specifier|private
specifier|final
name|String
name|fileNamePattern
decl_stmt|;
DECL|field|layoutFormatterPreferences
specifier|private
specifier|final
name|LayoutFormatterPreferences
name|layoutFormatterPreferences
decl_stmt|;
DECL|field|fileDirectoryPreferences
specifier|private
specifier|final
name|FileDirectoryPreferences
name|fileDirectoryPreferences
decl_stmt|;
DECL|method|CleanupPreferences (String fileNamePattern, LayoutFormatterPreferences layoutFormatterPreferences, FileDirectoryPreferences fileDirectoryPreferences)
specifier|public
name|CleanupPreferences
parameter_list|(
name|String
name|fileNamePattern
parameter_list|,
name|LayoutFormatterPreferences
name|layoutFormatterPreferences
parameter_list|,
name|FileDirectoryPreferences
name|fileDirectoryPreferences
parameter_list|)
block|{
name|this
operator|.
name|fileNamePattern
operator|=
name|fileNamePattern
expr_stmt|;
name|this
operator|.
name|layoutFormatterPreferences
operator|=
name|layoutFormatterPreferences
expr_stmt|;
name|this
operator|.
name|fileDirectoryPreferences
operator|=
name|fileDirectoryPreferences
expr_stmt|;
block|}
DECL|method|getFileNamePattern ()
specifier|public
name|String
name|getFileNamePattern
parameter_list|()
block|{
return|return
name|fileNamePattern
return|;
block|}
DECL|method|getLayoutFormatterPreferences ()
specifier|public
name|LayoutFormatterPreferences
name|getLayoutFormatterPreferences
parameter_list|()
block|{
return|return
name|layoutFormatterPreferences
return|;
block|}
DECL|method|getFileDirectoryPreferences ()
specifier|public
name|FileDirectoryPreferences
name|getFileDirectoryPreferences
parameter_list|()
block|{
return|return
name|fileDirectoryPreferences
return|;
block|}
block|}
end_class

end_unit

