begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
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
name|util
operator|.
name|Optional
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|Alert
operator|.
name|AlertType
import|;
end_import

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
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|DialogPane
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|stage
operator|.
name|FileChooser
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
name|JabRefGUI
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
name|gui
operator|.
name|util
operator|.
name|FileDialogConfiguration
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
name|controlsfx
operator|.
name|dialog
operator|.
name|ExceptionDialog
import|;
end_import

begin_comment
comment|/**  * This class provides methods to create default  * JavaFX dialogs which will also work on top of Swing  * windows. The created dialogs are instances of the  * {@link FXDialog} class. The available dialogs in this class  * are useful for displaying small information graphic dialogs  * rather than complex windows. For more complex dialogs it is  * advised to rather create a new sub class of {@link FXDialog}.  */
end_comment

begin_class
DECL|class|FXDialogService
specifier|public
class|class
name|FXDialogService
implements|implements
name|DialogService
block|{
DECL|method|createDialog (AlertType type, String title, String content)
specifier|private
specifier|static
name|FXDialog
name|createDialog
parameter_list|(
name|AlertType
name|type
parameter_list|,
name|String
name|title
parameter_list|,
name|String
name|content
parameter_list|)
block|{
name|FXDialog
name|alert
init|=
operator|new
name|FXDialog
argument_list|(
name|type
argument_list|,
name|title
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|alert
operator|.
name|setHeaderText
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|alert
operator|.
name|setContentText
argument_list|(
name|content
argument_list|)
expr_stmt|;
return|return
name|alert
return|;
block|}
annotation|@
name|Override
DECL|method|showInformationDialogAndWait (String title, String content)
specifier|public
name|void
name|showInformationDialogAndWait
parameter_list|(
name|String
name|title
parameter_list|,
name|String
name|content
parameter_list|)
block|{
name|FXDialog
name|alert
init|=
name|createDialog
argument_list|(
name|AlertType
operator|.
name|INFORMATION
argument_list|,
name|title
argument_list|,
name|content
argument_list|)
decl_stmt|;
name|alert
operator|.
name|showAndWait
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|showWarningDialogAndWait (String title, String content)
specifier|public
name|void
name|showWarningDialogAndWait
parameter_list|(
name|String
name|title
parameter_list|,
name|String
name|content
parameter_list|)
block|{
name|FXDialog
name|alert
init|=
name|createDialog
argument_list|(
name|AlertType
operator|.
name|WARNING
argument_list|,
name|title
argument_list|,
name|content
argument_list|)
decl_stmt|;
name|alert
operator|.
name|showAndWait
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|showErrorDialogAndWait (String title, String content)
specifier|public
name|void
name|showErrorDialogAndWait
parameter_list|(
name|String
name|title
parameter_list|,
name|String
name|content
parameter_list|)
block|{
name|FXDialog
name|alert
init|=
name|createDialog
argument_list|(
name|AlertType
operator|.
name|ERROR
argument_list|,
name|title
argument_list|,
name|content
argument_list|)
decl_stmt|;
name|alert
operator|.
name|showAndWait
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|showErrorDialogAndWait (String message, Throwable exception)
specifier|public
name|void
name|showErrorDialogAndWait
parameter_list|(
name|String
name|message
parameter_list|,
name|Throwable
name|exception
parameter_list|)
block|{
name|ExceptionDialog
name|exceptionDialog
init|=
operator|new
name|ExceptionDialog
argument_list|(
name|exception
argument_list|)
decl_stmt|;
name|exceptionDialog
operator|.
name|setHeaderText
argument_list|(
name|message
argument_list|)
expr_stmt|;
name|exceptionDialog
operator|.
name|showAndWait
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|showErrorDialogAndWait (String message)
specifier|public
name|void
name|showErrorDialogAndWait
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|FXDialog
name|alert
init|=
name|createDialog
argument_list|(
name|AlertType
operator|.
name|ERROR
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error Occurred"
argument_list|)
argument_list|,
name|message
argument_list|)
decl_stmt|;
name|alert
operator|.
name|showAndWait
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|showConfirmationDialogAndWait (String title, String content)
specifier|public
name|Optional
argument_list|<
name|ButtonType
argument_list|>
name|showConfirmationDialogAndWait
parameter_list|(
name|String
name|title
parameter_list|,
name|String
name|content
parameter_list|)
block|{
name|FXDialog
name|alert
init|=
name|createDialog
argument_list|(
name|AlertType
operator|.
name|CONFIRMATION
argument_list|,
name|title
argument_list|,
name|content
argument_list|)
decl_stmt|;
return|return
name|alert
operator|.
name|showAndWait
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|showCustomButtonDialogAndWait (AlertType type, String title, String content, ButtonType... buttonTypes)
specifier|public
name|Optional
argument_list|<
name|ButtonType
argument_list|>
name|showCustomButtonDialogAndWait
parameter_list|(
name|AlertType
name|type
parameter_list|,
name|String
name|title
parameter_list|,
name|String
name|content
parameter_list|,
name|ButtonType
modifier|...
name|buttonTypes
parameter_list|)
block|{
name|FXDialog
name|alert
init|=
name|createDialog
argument_list|(
name|type
argument_list|,
name|title
argument_list|,
name|content
argument_list|)
decl_stmt|;
name|alert
operator|.
name|getButtonTypes
argument_list|()
operator|.
name|setAll
argument_list|(
name|buttonTypes
argument_list|)
expr_stmt|;
return|return
name|alert
operator|.
name|showAndWait
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|showCustomDialogAndWait (String title, DialogPane contentPane, ButtonType... buttonTypes)
specifier|public
name|Optional
argument_list|<
name|ButtonType
argument_list|>
name|showCustomDialogAndWait
parameter_list|(
name|String
name|title
parameter_list|,
name|DialogPane
name|contentPane
parameter_list|,
name|ButtonType
modifier|...
name|buttonTypes
parameter_list|)
block|{
name|FXDialog
name|alert
init|=
operator|new
name|FXDialog
argument_list|(
name|AlertType
operator|.
name|NONE
argument_list|,
name|title
argument_list|)
decl_stmt|;
name|alert
operator|.
name|setDialogPane
argument_list|(
name|contentPane
argument_list|)
expr_stmt|;
name|alert
operator|.
name|getButtonTypes
argument_list|()
operator|.
name|setAll
argument_list|(
name|buttonTypes
argument_list|)
expr_stmt|;
return|return
name|alert
operator|.
name|showAndWait
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|showCustomDialogAndWait (Dialog<R> dialog)
specifier|public
parameter_list|<
name|R
parameter_list|>
name|Optional
argument_list|<
name|R
argument_list|>
name|showCustomDialogAndWait
parameter_list|(
name|Dialog
argument_list|<
name|R
argument_list|>
name|dialog
parameter_list|)
block|{
return|return
name|dialog
operator|.
name|showAndWait
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|notify (String message)
specifier|public
name|void
name|notify
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|JabRefGUI
operator|.
name|getMainFrame
argument_list|()
operator|.
name|output
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|showSaveDialog (FileDialogConfiguration fileDialogConfiguration)
specifier|public
name|Optional
argument_list|<
name|Path
argument_list|>
name|showSaveDialog
parameter_list|(
name|FileDialogConfiguration
name|fileDialogConfiguration
parameter_list|)
block|{
name|FileChooser
name|chooser
init|=
name|getConfiguredFileChooser
argument_list|(
name|fileDialogConfiguration
argument_list|)
decl_stmt|;
name|File
name|file
init|=
name|chooser
operator|.
name|showSaveDialog
argument_list|(
literal|null
argument_list|)
decl_stmt|;
return|return
name|Optional
operator|.
name|ofNullable
argument_list|(
name|file
argument_list|)
operator|.
name|map
argument_list|(
name|File
operator|::
name|toPath
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|showOpenDialog (FileDialogConfiguration fileDialogConfiguration)
specifier|public
name|Optional
argument_list|<
name|Path
argument_list|>
name|showOpenDialog
parameter_list|(
name|FileDialogConfiguration
name|fileDialogConfiguration
parameter_list|)
block|{
name|FileChooser
name|chooser
init|=
name|getConfiguredFileChooser
argument_list|(
name|fileDialogConfiguration
argument_list|)
decl_stmt|;
name|File
name|file
init|=
name|chooser
operator|.
name|showOpenDialog
argument_list|(
literal|null
argument_list|)
decl_stmt|;
return|return
name|Optional
operator|.
name|ofNullable
argument_list|(
name|file
argument_list|)
operator|.
name|map
argument_list|(
name|File
operator|::
name|toPath
argument_list|)
return|;
block|}
DECL|method|getConfiguredFileChooser (FileDialogConfiguration fileDialogConfiguration)
specifier|private
name|FileChooser
name|getConfiguredFileChooser
parameter_list|(
name|FileDialogConfiguration
name|fileDialogConfiguration
parameter_list|)
block|{
name|FileChooser
name|chooser
init|=
operator|new
name|FileChooser
argument_list|()
decl_stmt|;
name|chooser
operator|.
name|getExtensionFilters
argument_list|()
operator|.
name|addAll
argument_list|(
name|fileDialogConfiguration
operator|.
name|getExtensionFilters
argument_list|()
argument_list|)
expr_stmt|;
name|chooser
operator|.
name|setSelectedExtensionFilter
argument_list|(
name|fileDialogConfiguration
operator|.
name|getDefaultExtension
argument_list|()
argument_list|)
expr_stmt|;
name|fileDialogConfiguration
operator|.
name|getInitialDirectory
argument_list|()
operator|.
name|map
argument_list|(
name|Path
operator|::
name|toFile
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|chooser
operator|::
name|setInitialDirectory
argument_list|)
expr_stmt|;
return|return
name|chooser
return|;
block|}
block|}
end_class

end_unit

