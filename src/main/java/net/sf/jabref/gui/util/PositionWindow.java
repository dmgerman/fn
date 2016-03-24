begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.gui.util
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|util
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Dimension
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|GraphicsEnvironment
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Point
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Rectangle
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|Globals
import|;
end_import

begin_class
DECL|class|PositionWindow
specifier|public
class|class
name|PositionWindow
block|{
DECL|field|posXKey
specifier|private
specifier|final
name|String
name|posXKey
decl_stmt|;
DECL|field|posYKey
specifier|private
specifier|final
name|String
name|posYKey
decl_stmt|;
DECL|field|sizeXKey
specifier|private
specifier|final
name|String
name|sizeXKey
decl_stmt|;
DECL|field|sizeYKey
specifier|private
specifier|final
name|String
name|sizeYKey
decl_stmt|;
DECL|field|window
specifier|private
specifier|final
name|Window
name|window
decl_stmt|;
DECL|method|PositionWindow (Window window, String posXKey, String posYKey, String sizeXKey, String sizeYKey)
specifier|public
name|PositionWindow
parameter_list|(
name|Window
name|window
parameter_list|,
name|String
name|posXKey
parameter_list|,
name|String
name|posYKey
parameter_list|,
name|String
name|sizeXKey
parameter_list|,
name|String
name|sizeYKey
parameter_list|)
block|{
name|this
operator|.
name|posXKey
operator|=
name|posXKey
expr_stmt|;
name|this
operator|.
name|posYKey
operator|=
name|posYKey
expr_stmt|;
name|this
operator|.
name|sizeXKey
operator|=
name|sizeXKey
expr_stmt|;
name|this
operator|.
name|sizeYKey
operator|=
name|sizeYKey
expr_stmt|;
name|this
operator|.
name|window
operator|=
name|window
expr_stmt|;
block|}
DECL|method|setWindowPosition ()
specifier|public
name|void
name|setWindowPosition
parameter_list|()
block|{
name|int
name|sizeX
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getInt
argument_list|(
name|sizeXKey
argument_list|)
decl_stmt|;
name|int
name|sizeY
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getInt
argument_list|(
name|sizeYKey
argument_list|)
decl_stmt|;
name|int
name|posX
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getInt
argument_list|(
name|posXKey
argument_list|)
decl_stmt|;
name|int
name|posY
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getInt
argument_list|(
name|posYKey
argument_list|)
decl_stmt|;
comment|//
comment|// Fix for [ 1738920 ] Windows Position in Multi-Monitor environment
comment|//
comment|// Do not put a window outside the screen if the preference values are wrong.
comment|//
comment|// Useful reference: http://www.exampledepot.com/egs/java.awt/screen_ScreenSize.html?l=rel
comment|// googled on forums.java.sun.com graphicsenvironment second screen java
comment|//
if|if
condition|(
name|GraphicsEnvironment
operator|.
name|getLocalGraphicsEnvironment
argument_list|()
operator|.
name|getScreenDevices
argument_list|()
operator|.
name|length
operator|>=
literal|1
condition|)
block|{
name|Rectangle
name|bounds
init|=
name|GraphicsEnvironment
operator|.
name|getLocalGraphicsEnvironment
argument_list|()
operator|.
name|getScreenDevices
argument_list|()
index|[
literal|0
index|]
operator|.
name|getDefaultConfiguration
argument_list|()
operator|.
name|getBounds
argument_list|()
decl_stmt|;
name|Dimension
name|dim
init|=
name|Toolkit
operator|.
name|getDefaultToolkit
argument_list|()
operator|.
name|getScreenSize
argument_list|()
decl_stmt|;
comment|// Make sure we are not above or to the left of the screen bounds:
if|if
condition|(
name|posX
operator|<
name|bounds
operator|.
name|x
condition|)
block|{
name|posX
operator|=
name|bounds
operator|.
name|x
expr_stmt|;
block|}
if|if
condition|(
name|posY
operator|<
name|bounds
operator|.
name|y
condition|)
block|{
name|posY
operator|=
name|bounds
operator|.
name|y
expr_stmt|;
block|}
name|int
name|height
init|=
operator|(
name|int
operator|)
name|dim
operator|.
name|getHeight
argument_list|()
decl_stmt|;
name|int
name|width
init|=
operator|(
name|int
operator|)
name|dim
operator|.
name|getWidth
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|posX
operator|+
name|sizeX
operator|)
operator|>
name|width
condition|)
block|{
if|if
condition|(
name|sizeX
operator|<=
name|width
condition|)
block|{
name|posX
operator|=
name|width
operator|-
name|sizeX
expr_stmt|;
block|}
else|else
block|{
name|posX
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|getIntDefault
argument_list|(
name|posXKey
argument_list|)
expr_stmt|;
name|sizeX
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|getIntDefault
argument_list|(
name|sizeXKey
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|(
name|posY
operator|+
name|sizeY
operator|)
operator|>
name|height
condition|)
block|{
if|if
condition|(
name|sizeY
operator|<=
name|height
condition|)
block|{
name|posY
operator|=
name|height
operator|-
name|sizeY
expr_stmt|;
block|}
else|else
block|{
name|posY
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|getIntDefault
argument_list|(
name|posYKey
argument_list|)
expr_stmt|;
name|sizeY
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|getIntDefault
argument_list|(
name|sizeYKey
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|window
operator|.
name|setLocation
argument_list|(
name|posX
argument_list|,
name|posY
argument_list|)
expr_stmt|;
name|window
operator|.
name|setSize
argument_list|(
name|sizeX
argument_list|,
name|sizeY
argument_list|)
expr_stmt|;
block|}
DECL|method|storeWindowPosition ()
specifier|public
name|void
name|storeWindowPosition
parameter_list|()
block|{
name|Point
name|p
init|=
name|window
operator|.
name|getLocation
argument_list|()
decl_stmt|;
name|Dimension
name|d
init|=
name|window
operator|.
name|getSize
argument_list|()
decl_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putInt
argument_list|(
name|posXKey
argument_list|,
name|p
operator|.
name|x
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putInt
argument_list|(
name|posYKey
argument_list|,
name|p
operator|.
name|y
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putInt
argument_list|(
name|sizeXKey
argument_list|,
name|d
operator|.
name|width
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putInt
argument_list|(
name|sizeYKey
argument_list|,
name|d
operator|.
name|height
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

