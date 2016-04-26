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
name|javafx
operator|.
name|application
operator|.
name|Platform
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
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|image
operator|.
name|Image
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|stage
operator|.
name|Stage
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Window
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|WindowAdapter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|WindowEvent
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

begin_comment
comment|/**  * This class shall provide a super class for future dialogs implemented in java fx.  * It mimics the behavior of a swing JDialog which means once a object of this class  * is shown all swing windows will be blocked and stay in the background. Since this  * class extends from a java fx Alert it behaves as a normal dialog towards all  * windows in the java fx thread.  *<p>To create a custom java fx dialog one should extend this class and set a dialog  * pane through the inherited {@link setDialogPane(DialogPane)} method in the constructor.  * The layout of the pane should be define in an external fxml file and loaded it via the  * {@link FXMLLoader}.  *  */
end_comment

begin_class
DECL|class|FXAlert
specifier|public
class|class
name|FXAlert
extends|extends
name|Alert
block|{
comment|/**      * The WindowAdapter will be added to all swing windows once an instance      * of this class is shown and redirects the focus towards this instance.      * It will be removed once the instance of this class gets hidden.      *      */
DECL|field|fxOverSwingHelper
specifier|private
specifier|final
name|WindowAdapter
name|fxOverSwingHelper
init|=
operator|new
name|WindowAdapter
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|windowActivated
parameter_list|(
name|WindowEvent
name|e
parameter_list|)
block|{
name|Platform
operator|.
name|runLater
argument_list|(
parameter_list|()
lambda|->
block|{
name|Stage
name|fxDialogWindow
init|=
name|getDialogWindow
argument_list|()
decl_stmt|;
name|fxDialogWindow
operator|.
name|toFront
argument_list|()
expr_stmt|;
name|fxDialogWindow
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|windowGainedFocus
parameter_list|(
name|WindowEvent
name|e
parameter_list|)
block|{
name|Platform
operator|.
name|runLater
argument_list|(
parameter_list|()
lambda|->
block|{
name|Stage
name|fxDialogWindow
init|=
name|getDialogWindow
argument_list|()
decl_stmt|;
name|fxDialogWindow
operator|.
name|toFront
argument_list|()
expr_stmt|;
name|fxDialogWindow
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
block|}
block|}
decl_stmt|;
DECL|method|FXAlert (AlertType type, String title, Image image)
specifier|public
name|FXAlert
parameter_list|(
name|AlertType
name|type
parameter_list|,
name|String
name|title
parameter_list|,
name|Image
name|image
parameter_list|)
block|{
name|this
argument_list|(
name|type
argument_list|,
name|title
argument_list|)
expr_stmt|;
name|setDialogIcon
argument_list|(
name|image
argument_list|)
expr_stmt|;
block|}
DECL|method|FXAlert (AlertType type, String title)
specifier|public
name|FXAlert
parameter_list|(
name|AlertType
name|type
parameter_list|,
name|String
name|title
parameter_list|)
block|{
name|this
argument_list|(
name|type
argument_list|)
expr_stmt|;
name|setTitle
argument_list|(
name|title
argument_list|)
expr_stmt|;
block|}
DECL|method|FXAlert (AlertType type)
specifier|public
name|FXAlert
parameter_list|(
name|AlertType
name|type
parameter_list|)
block|{
name|super
argument_list|(
name|type
argument_list|)
expr_stmt|;
name|Stage
name|fxDialogWindow
init|=
name|getDialogWindow
argument_list|()
decl_stmt|;
name|fxDialogWindow
operator|.
name|setOnShown
argument_list|(
name|evt
lambda|->
block|{
name|setSwingWindowsEnabledAndFocusable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|setLocationRelativeToMainWindow
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|fxDialogWindow
operator|.
name|setOnHiding
argument_list|(
name|evt
lambda|->
name|setSwingWindowsEnabledAndFocusable
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|fxDialogWindow
operator|.
name|setOnCloseRequest
argument_list|(
name|evt
lambda|->
name|this
operator|.
name|close
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|setDialogStyle (String pathToStyleSheet)
specifier|public
name|void
name|setDialogStyle
parameter_list|(
name|String
name|pathToStyleSheet
parameter_list|)
block|{
name|getDialogPane
argument_list|()
operator|.
name|getScene
argument_list|()
operator|.
name|getStylesheets
argument_list|()
operator|.
name|add
argument_list|(
name|pathToStyleSheet
argument_list|)
expr_stmt|;
block|}
DECL|method|setDialogIcon (Image image)
specifier|public
name|void
name|setDialogIcon
parameter_list|(
name|Image
name|image
parameter_list|)
block|{
name|Stage
name|fxDialogWindow
init|=
name|getDialogWindow
argument_list|()
decl_stmt|;
name|fxDialogWindow
operator|.
name|getIcons
argument_list|()
operator|.
name|add
argument_list|(
name|image
argument_list|)
expr_stmt|;
block|}
DECL|method|getDialogWindow ()
specifier|private
name|Stage
name|getDialogWindow
parameter_list|()
block|{
return|return
operator|(
name|Stage
operator|)
name|getDialogPane
argument_list|()
operator|.
name|getScene
argument_list|()
operator|.
name|getWindow
argument_list|()
return|;
block|}
DECL|method|setSwingWindowsEnabledAndFocusable (boolean enabled)
specifier|private
name|void
name|setSwingWindowsEnabledAndFocusable
parameter_list|(
name|boolean
name|enabled
parameter_list|)
block|{
for|for
control|(
name|Window
name|swingWindow
range|:
name|Window
operator|.
name|getWindows
argument_list|()
control|)
block|{
name|swingWindow
operator|.
name|setEnabled
argument_list|(
name|enabled
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|enabled
condition|)
block|{
name|swingWindow
operator|.
name|addWindowListener
argument_list|(
name|fxOverSwingHelper
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|swingWindow
operator|.
name|removeWindowListener
argument_list|(
name|fxOverSwingHelper
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|setLocationRelativeToMainWindow ()
specifier|private
name|void
name|setLocationRelativeToMainWindow
parameter_list|()
block|{
name|double
name|mainWindowX
init|=
name|JabRefGUI
operator|.
name|getMainFrame
argument_list|()
operator|.
name|getLocationOnScreen
argument_list|()
operator|.
name|getX
argument_list|()
decl_stmt|;
name|double
name|mainWindowY
init|=
name|JabRefGUI
operator|.
name|getMainFrame
argument_list|()
operator|.
name|getLocationOnScreen
argument_list|()
operator|.
name|getY
argument_list|()
decl_stmt|;
name|double
name|mainWindowWidth
init|=
name|JabRefGUI
operator|.
name|getMainFrame
argument_list|()
operator|.
name|getSize
argument_list|()
operator|.
name|getWidth
argument_list|()
decl_stmt|;
name|double
name|mainWindowHeight
init|=
name|JabRefGUI
operator|.
name|getMainFrame
argument_list|()
operator|.
name|getSize
argument_list|()
operator|.
name|getHeight
argument_list|()
decl_stmt|;
name|setX
argument_list|(
operator|(
name|mainWindowX
operator|+
operator|(
name|mainWindowWidth
operator|/
literal|2
operator|)
operator|)
operator|-
operator|(
name|getWidth
argument_list|()
operator|/
literal|2
operator|)
argument_list|)
expr_stmt|;
name|setY
argument_list|(
operator|(
name|mainWindowY
operator|+
operator|(
name|mainWindowHeight
operator|/
literal|2
operator|)
operator|)
operator|-
operator|(
name|getHeight
argument_list|()
operator|/
literal|2
operator|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

