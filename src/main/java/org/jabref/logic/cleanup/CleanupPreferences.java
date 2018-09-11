begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.cleanup
package|package
name|org
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
name|org
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
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|metadata
operator|.
name|FilePreferences
import|;
end_import

begin_class
DECL|class|CleanupPreferences
specifier|public
class|class
name|CleanupPreferences
block|{
DECL|field|layoutFormatterPreferences
specifier|private
specifier|final
name|LayoutFormatterPreferences
name|layoutFormatterPreferences
decl_stmt|;
DECL|field|filePreferences
specifier|private
specifier|final
name|FilePreferences
name|filePreferences
decl_stmt|;
DECL|method|CleanupPreferences (LayoutFormatterPreferences layoutFormatterPreferences, FilePreferences filePreferences)
specifier|public
name|CleanupPreferences
parameter_list|(
name|LayoutFormatterPreferences
name|layoutFormatterPreferences
parameter_list|,
name|FilePreferences
name|filePreferences
parameter_list|)
block|{
name|this
operator|.
name|layoutFormatterPreferences
operator|=
name|layoutFormatterPreferences
expr_stmt|;
name|this
operator|.
name|filePreferences
operator|=
name|filePreferences
expr_stmt|;
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
DECL|method|getFilePreferences ()
specifier|public
name|FilePreferences
name|getFilePreferences
parameter_list|()
block|{
return|return
name|filePreferences
return|;
block|}
block|}
end_class

end_unit

