begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
package|;
end_package

begin_comment
comment|//import javax.swing.*;
end_comment

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|EventQueue
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Frame
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Graphics
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Image
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|MediaTracker
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Toolkit
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
name|net
operator|.
name|URL
import|;
end_import

begin_comment
comment|/**  *<p>Title:</p>  *<p>Description:</p>  *<p>Copyright: Copyright (c) 2003</p>  *<p>Company:</p>  * @author not attributable  * @version 1.0  */
end_comment

begin_class
DECL|class|SplashScreen
class|class
name|SplashScreen
extends|extends
name|Window
block|{
DECL|field|splashImage
specifier|private
specifier|final
name|Image
name|splashImage
decl_stmt|;
DECL|field|paintCalled
specifier|private
name|boolean
name|paintCalled
init|=
literal|false
decl_stmt|;
DECL|method|SplashScreen (Frame owner)
specifier|private
name|SplashScreen
parameter_list|(
name|Frame
name|owner
parameter_list|)
block|{
name|super
argument_list|(
name|owner
argument_list|)
expr_stmt|;
name|URL
name|imageURL
init|=
name|SplashScreen
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"/images/splash.png"
argument_list|)
decl_stmt|;
name|splashImage
operator|=
name|Toolkit
operator|.
name|getDefaultToolkit
argument_list|()
operator|.
name|createImage
argument_list|(
name|imageURL
argument_list|)
expr_stmt|;
comment|// Load the image
name|MediaTracker
name|mt
init|=
operator|new
name|MediaTracker
argument_list|(
name|this
argument_list|)
decl_stmt|;
name|mt
operator|.
name|addImage
argument_list|(
name|splashImage
argument_list|,
literal|0
argument_list|)
expr_stmt|;
try|try
block|{
name|mt
operator|.
name|waitForID
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|ignored
parameter_list|)
block|{         }
comment|// Center the window on the screen.
name|int
name|imgWidth
init|=
name|splashImage
operator|.
name|getWidth
argument_list|(
name|this
argument_list|)
decl_stmt|;
name|int
name|imgHeight
init|=
name|splashImage
operator|.
name|getHeight
argument_list|(
name|this
argument_list|)
decl_stmt|;
name|setSize
argument_list|(
name|imgWidth
argument_list|,
name|imgHeight
argument_list|)
expr_stmt|;
name|setLocationRelativeTo
argument_list|(
literal|null
argument_list|)
expr_stmt|;
comment|/* Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();         setLocation(             (screenDim.width - imgWidth) / 2,             (screenDim.height - imgHeight) / 2         );         */
block|}
comment|/**      * Updates the display area of the window.      */
annotation|@
name|Override
DECL|method|update (Graphics g)
specifier|public
name|void
name|update
parameter_list|(
name|Graphics
name|g
parameter_list|)
block|{
comment|// Note: Since the paint method is going to draw an
comment|// image that covers the complete area of the component we
comment|// do not fill the component with its background color
comment|// here. This avoids flickering.
name|g
operator|.
name|setColor
argument_list|(
name|getForeground
argument_list|()
argument_list|)
expr_stmt|;
name|paint
argument_list|(
name|g
argument_list|)
expr_stmt|;
block|}
comment|/**      * Paints the image on the window.      */
annotation|@
name|Override
DECL|method|paint (Graphics g)
specifier|public
name|void
name|paint
parameter_list|(
name|Graphics
name|g
parameter_list|)
block|{
name|g
operator|.
name|drawImage
argument_list|(
name|splashImage
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
name|this
argument_list|)
expr_stmt|;
comment|// Notify method splash that the window
comment|// has been painted.
if|if
condition|(
operator|!
name|paintCalled
condition|)
block|{
name|paintCalled
operator|=
literal|true
expr_stmt|;
synchronized|synchronized
init|(
name|this
init|)
block|{
name|notifyAll
argument_list|()
expr_stmt|;
block|}
block|}
block|}
comment|/**      * Constructs and displays a SplashWindow.<p>      * This method is useful for startup splashs.      * Dispose the returned frame to get rid of the splash window.<p>      *      * @return Returns the frame that owns the SplashWindow.      */
DECL|method|splash ()
specifier|public
specifier|static
name|Frame
name|splash
parameter_list|()
block|{
name|Frame
name|f
init|=
operator|new
name|Frame
argument_list|()
decl_stmt|;
name|SplashScreen
name|w
init|=
operator|new
name|SplashScreen
argument_list|(
name|f
argument_list|)
decl_stmt|;
comment|// Show the window.
name|w
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|w
operator|.
name|toFront
argument_list|()
expr_stmt|;
comment|// Note: To make sure the user gets a chance to see the
comment|// splash window we wait until its paint method has been
comment|// called at least once by the AWT event dispatcher thread.
comment|// sebwills adds: However, just in case the paint method never gets called
comment|// (e.g. if the splashscreen is completely obscured by an 'always on top'
comment|// window of some other application), we time-out after 5 seconds.
if|if
condition|(
operator|!
name|EventQueue
operator|.
name|isDispatchThread
argument_list|()
condition|)
block|{
synchronized|synchronized
init|(
name|w
init|)
block|{
if|if
condition|(
operator|!
name|w
operator|.
name|paintCalled
condition|)
block|{
try|try
block|{
name|w
operator|.
name|wait
argument_list|(
literal|5000
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|ignored
parameter_list|)
block|{                     }
block|}
block|}
block|}
return|return
name|f
return|;
block|}
block|}
end_class

end_unit

