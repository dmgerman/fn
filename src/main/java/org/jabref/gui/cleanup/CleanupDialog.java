begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.cleanup
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|cleanup
package|;
end_package

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|ButtonType
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|util
operator|.
name|BaseDialog
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
name|cleanup
operator|.
name|CleanupPreset
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
name|l10n
operator|.
name|Localization
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
name|database
operator|.
name|BibDatabaseContext
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
DECL|class|CleanupDialog
specifier|public
class|class
name|CleanupDialog
extends|extends
name|BaseDialog
argument_list|<
name|CleanupPreset
argument_list|>
block|{
DECL|method|CleanupDialog (BibDatabaseContext databaseContext, CleanupPreset initialPreset, FilePreferences filePreferences)
specifier|public
name|CleanupDialog
parameter_list|(
name|BibDatabaseContext
name|databaseContext
parameter_list|,
name|CleanupPreset
name|initialPreset
parameter_list|,
name|FilePreferences
name|filePreferences
parameter_list|)
block|{
name|setTitle
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cleanup entries"
argument_list|)
argument_list|)
expr_stmt|;
name|getDialogPane
argument_list|()
operator|.
name|setPrefSize
argument_list|(
literal|600
argument_list|,
literal|600
argument_list|)
expr_stmt|;
name|getDialogPane
argument_list|()
operator|.
name|getButtonTypes
argument_list|()
operator|.
name|setAll
argument_list|(
name|ButtonType
operator|.
name|OK
argument_list|,
name|ButtonType
operator|.
name|CANCEL
argument_list|)
expr_stmt|;
name|CleanupPresetPanel
name|presetPanel
init|=
operator|new
name|CleanupPresetPanel
argument_list|(
name|databaseContext
argument_list|,
name|initialPreset
argument_list|,
name|filePreferences
argument_list|)
decl_stmt|;
name|getDialogPane
argument_list|()
operator|.
name|setContent
argument_list|(
name|presetPanel
argument_list|)
expr_stmt|;
name|setResultConverter
argument_list|(
name|button
lambda|->
block|{
if|if
condition|(
name|button
operator|==
name|ButtonType
operator|.
name|OK
condition|)
block|{
return|return
name|presetPanel
operator|.
name|getCleanupPreset
argument_list|()
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

