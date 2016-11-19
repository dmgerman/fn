begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2016 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

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

begin_comment
comment|/**  * This class provides static methods to create default  * JavaFX dialogs which will also work on top of swing  * windows. The created dialogs are instances of the  * {@link FXDialog} class. The available dialogs in this class  * are useful for displaying small information graphic dialogs  * rather than complex windows. For more complex dialogs it is  * advised to rather create a new sub class of {@link FXDialog}.  *  */
end_comment

begin_class
DECL|class|FXDialogs
specifier|public
class|class
name|FXDialogs
block|{
comment|/**      * This will create and display a new information dialog.      * It will include a blue information icon on the left and      * a single OK Button. To create a information dialog with custom      * buttons see also {@link #showCustomButtonDialogAndWait(AlertType, String, String, ButtonType...)}      *      * @param title as String      * @param content as String      */
DECL|method|showInformationDialogAndWait (String title, String content)
specifier|public
specifier|static
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
comment|/**      * This will create and display a new information dialog.      * It will include a yellow warning icon on the left and      * a single OK Button. To create a warning dialog with custom      * buttons see also {@link #showCustomButtonDialogAndWait(AlertType, String, String, ButtonType...)}      *      * @param title as String      * @param content as String      */
DECL|method|showWarningDialogAndWait (String title, String content)
specifier|public
specifier|static
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
comment|/**      * This will create and display a new error dialog.      * It will include a red error icon on the left and      * a single OK Button. To create a error dialog with custom      * buttons see also {@link #showCustomButtonDialogAndWait(AlertType, String, String, ButtonType...)}      *      * @param title as String      * @param content as String      */
DECL|method|showErrorDialogAndWait (String title, String content)
specifier|public
specifier|static
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
comment|/**      * This will create and display a new confirmation dialog.      * It will include a blue question icon on the left and      * a OK and Cancel Button. To create a confirmation dialog with custom      * buttons see also {@link #showCustomButtonDialogAndWait(AlertType, String, String, ButtonType...)}      *      * @param title as String      * @param content as String      * @return Optional with the pressed Button as ButtonType      */
DECL|method|showConfirmationDialogAndWait (String title, String content)
specifier|public
specifier|static
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
comment|/**      * This will create and display a new dialog of the specified      * {@link AlertType} but with user defined buttons as optional      * {@link ButtonType}s.      *      * @param type as {@link AlertType}      * @param title as String      * @param content as String      * @param buttonTypes      * @return Optional with the pressed Button as ButtonType      */
DECL|method|showCustomButtonDialogAndWait (AlertType type, String title, String content, ButtonType... buttonTypes)
specifier|public
specifier|static
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
comment|/**      * This will create and display a new dialog showing a custom {@link DialogPane}      * and using custom {@link ButtonType}s.      *      * @param title as String      * @param contentPane as DialogPane      * @param buttonTypes as ButtonType      * @return Optional with the pressed Button as ButtonType      */
DECL|method|showCustomDialogAndWait (String title, DialogPane contentPane, ButtonType... buttonTypes)
specifier|public
specifier|static
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
block|}
end_class

end_unit

