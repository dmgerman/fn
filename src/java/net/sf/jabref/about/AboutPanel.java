begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 Raik Nagel     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.about
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|about
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Color
import|;
end_import

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
name|Font
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|FontMetrics
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
name|image
operator|.
name|ImageProducer
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|BufferedReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStreamReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Enumeration
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Vector
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|BorderFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|ImageIcon
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JComponent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|UIManager
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|border
operator|.
name|BevelBorder
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
name|GUIGlobals
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

begin_comment
comment|// AboutPanel class
end_comment

begin_class
DECL|class|AboutPanel
specifier|public
class|class
name|AboutPanel
extends|extends
name|JComponent
block|{
DECL|field|textBlocks
specifier|private
name|Vector
argument_list|<
name|TextBlock
argument_list|>
name|textBlocks
decl_stmt|;
DECL|field|versionStr
specifier|private
name|String
name|versionStr
decl_stmt|;
DECL|field|buildStr
specifier|private
name|String
name|buildStr
decl_stmt|;
DECL|field|thread
specifier|private
name|AnimationThread
name|thread
decl_stmt|;
DECL|field|image
specifier|private
name|ImageIcon
name|image
decl_stmt|;
comment|// animated text positions
DECL|field|TOP
specifier|public
name|int
name|TOP
init|=
literal|300
decl_stmt|;
comment|// offset from top - hide
DECL|field|BOTTOM
specifier|public
name|int
name|BOTTOM
init|=
literal|0
decl_stmt|;
comment|// show
DECL|field|HEIGHT
specifier|public
name|int
name|HEIGHT
init|=
literal|500
decl_stmt|;
DECL|field|WIDTH
specifier|public
name|int
name|WIDTH
init|=
literal|500
decl_stmt|;
DECL|field|borders
specifier|private
name|int
name|borders
index|[]
decl_stmt|;
comment|// Border-Coordinates of paintarea (xLeft, xRight, yTop, yBottom)
DECL|field|paintWidth
specifier|private
name|int
name|paintWidth
decl_stmt|;
DECL|field|font1
specifier|private
name|Font
name|font1
decl_stmt|;
DECL|field|font2
specifier|private
name|Font
name|font2
decl_stmt|;
DECL|field|font3
specifier|private
name|Font
name|font3
decl_stmt|;
DECL|field|aniListener
specifier|private
name|AnimationListener
name|aniListener
decl_stmt|;
DECL|field|iProducer
specifier|private
name|ImageProducer
name|iProducer
decl_stmt|;
DECL|method|AboutPanel ()
name|AboutPanel
parameter_list|()
block|{
name|Font
name|font
init|=
name|loadFont
argument_list|(
literal|"ASTROLYT.TTF"
argument_list|)
decl_stmt|;
name|font1
operator|=
name|font
operator|.
name|deriveFont
argument_list|(
name|Font
operator|.
name|BOLD
argument_list|,
operator|(
name|float
operator|)
literal|14.0
argument_list|)
expr_stmt|;
name|font2
operator|=
name|font
operator|.
name|deriveFont
argument_list|(
name|Font
operator|.
name|BOLD
argument_list|,
operator|(
name|float
operator|)
literal|20.0
argument_list|)
expr_stmt|;
name|font
operator|=
name|loadFont
argument_list|(
literal|"AUGIE.TTF"
argument_list|)
expr_stmt|;
name|font3
operator|=
name|font
operator|.
name|deriveFont
argument_list|(
name|Font
operator|.
name|BOLD
argument_list|,
operator|(
name|float
operator|)
literal|14.0
argument_list|)
expr_stmt|;
name|versionStr
operator|=
literal|"Version "
operator|+
name|Globals
operator|.
name|VERSION
expr_stmt|;
name|buildStr
operator|=
literal|" build "
operator|+
name|Globals
operator|.
name|BUILD
expr_stmt|;
name|image
operator|=
operator|new
name|ImageIcon
argument_list|(
name|getClass
argument_list|()
operator|.
name|getResource
argument_list|(
literal|"/images/autumn.png"
argument_list|)
argument_list|)
expr_stmt|;
name|HEIGHT
operator|=
name|image
operator|.
name|getIconHeight
argument_list|()
expr_stmt|;
name|WIDTH
operator|=
name|image
operator|.
name|getIconWidth
argument_list|()
expr_stmt|;
name|FontMetrics
name|fm
init|=
name|getFontMetrics
argument_list|(
name|font2
argument_list|)
decl_stmt|;
name|TOP
operator|=
literal|2
operator|*
name|fm
operator|.
name|getHeight
argument_list|()
expr_stmt|;
name|fm
operator|=
name|getFontMetrics
argument_list|(
name|font1
argument_list|)
expr_stmt|;
name|BOTTOM
operator|=
literal|2
operator|*
name|fm
operator|.
name|getHeight
argument_list|()
expr_stmt|;
name|borders
operator|=
operator|new
name|int
index|[
literal|4
index|]
expr_stmt|;
name|borders
index|[
literal|0
index|]
operator|=
literal|0
expr_stmt|;
name|borders
index|[
literal|1
index|]
operator|=
name|WIDTH
expr_stmt|;
name|borders
index|[
literal|2
index|]
operator|=
name|TOP
expr_stmt|;
name|borders
index|[
literal|3
index|]
operator|=
name|HEIGHT
operator|-
name|TOP
operator|-
name|BOTTOM
expr_stmt|;
name|paintWidth
operator|=
name|borders
index|[
literal|1
index|]
operator|-
name|borders
index|[
literal|0
index|]
expr_stmt|;
name|setForeground
argument_list|(
name|Color
operator|.
name|black
argument_list|)
expr_stmt|;
name|setBackground
argument_list|(
name|Color
operator|.
name|white
argument_list|)
expr_stmt|;
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createBevelBorder
argument_list|(
name|BevelBorder
operator|.
name|RAISED
argument_list|)
argument_list|)
expr_stmt|;
name|textBlocks
operator|=
operator|new
name|Vector
argument_list|<
name|TextBlock
argument_list|>
argument_list|(
literal|50
argument_list|)
expr_stmt|;
name|loadAboutText
argument_list|()
expr_stmt|;
name|this
operator|.
name|setDoubleBuffered
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|thread
operator|=
operator|new
name|AnimationThread
argument_list|()
expr_stmt|;
block|}
comment|// ----------------------------------------------------------------------------
DECL|method|addAnimationListener (AnimationListener listener)
specifier|public
name|void
name|addAnimationListener
parameter_list|(
name|AnimationListener
name|listener
parameter_list|)
block|{
name|aniListener
operator|=
name|listener
expr_stmt|;
block|}
comment|// ----------------------------------------------------------------------------
comment|// returns
DECL|method|getMiddleX (String text, Font font)
specifier|private
name|int
name|getMiddleX
parameter_list|(
name|String
name|text
parameter_list|,
name|Font
name|font
parameter_list|)
block|{
name|FontMetrics
name|fm
init|=
name|getFontMetrics
argument_list|(
name|font
argument_list|)
decl_stmt|;
return|return
operator|(
name|paintWidth
operator|/
literal|2
operator|-
operator|(
operator|(
name|fm
operator|.
name|stringWidth
argument_list|(
name|text
argument_list|)
operator|+
literal|10
operator|)
operator|/
literal|2
operator|)
operator|)
return|;
block|}
comment|// ----------------------------------------------------------------------------
DECL|method|loadFont (String fontName)
specifier|private
name|Font
name|loadFont
parameter_list|(
name|String
name|fontName
parameter_list|)
block|{
name|Font
name|back
init|=
name|UIManager
operator|.
name|getFont
argument_list|(
literal|"Label.font"
argument_list|)
decl_stmt|;
try|try
block|{
name|InputStream
name|myStream
init|=
name|getClass
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
name|GUIGlobals
operator|.
name|fontPath
operator|+
name|fontName
argument_list|)
decl_stmt|;
name|back
operator|=
name|Font
operator|.
name|createFont
argument_list|(
name|Font
operator|.
name|TRUETYPE_FONT
argument_list|,
name|myStream
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
return|return
name|back
return|;
block|}
comment|// ----------------------------------------------------------------------------
DECL|method|loadAboutText ()
specifier|private
specifier|final
name|void
name|loadAboutText
parameter_list|()
block|{
name|TextBlock
name|block
init|=
literal|null
decl_stmt|;
name|AboutTextLine
name|aLine
init|=
literal|null
decl_stmt|;
name|int
name|index
init|=
operator|-
literal|3
decl_stmt|;
name|FontMetrics
name|fm
init|=
name|getFontMetrics
argument_list|(
name|font3
argument_list|)
decl_stmt|;
try|try
block|{
name|InputStream
name|stream
init|=
name|getClass
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
name|GUIGlobals
operator|.
name|getLocaleHelpPath
argument_list|()
operator|+
literal|"credits.txt"
argument_list|)
decl_stmt|;
if|if
condition|(
name|stream
operator|==
literal|null
condition|)
block|{
name|stream
operator|=
name|getClass
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
name|GUIGlobals
operator|.
name|helpPre
operator|+
literal|"credits.txt"
argument_list|)
expr_stmt|;
block|}
name|InputStreamReader
name|reader
init|=
operator|new
name|InputStreamReader
argument_list|(
name|stream
argument_list|)
decl_stmt|;
name|BufferedReader
name|input
init|=
operator|new
name|BufferedReader
argument_list|(
name|reader
argument_list|,
literal|1000
argument_list|)
decl_stmt|;
while|while
condition|(
name|input
operator|.
name|ready
argument_list|()
condition|)
block|{
name|String
name|line
init|=
name|input
operator|.
name|readLine
argument_list|()
decl_stmt|;
if|if
condition|(
name|line
operator|!=
literal|null
condition|)
block|{
name|line
operator|=
name|line
operator|.
name|trim
argument_list|()
expr_stmt|;
if|if
condition|(
name|line
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
if|if
condition|(
name|line
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|==
literal|'#'
condition|)
comment|// new Block....
block|{
if|if
condition|(
name|block
operator|!=
literal|null
condition|)
comment|//insert previous block
block|{
name|textBlocks
operator|.
name|add
argument_list|(
name|block
argument_list|)
expr_stmt|;
name|index
operator|+=
literal|2
expr_stmt|;
block|}
name|aLine
operator|=
operator|new
name|AboutTextLine
argument_list|(
name|line
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
name|aLine
operator|.
name|setTag
argument_list|(
literal|2
argument_list|)
expr_stmt|;
name|aLine
operator|.
name|setPos
argument_list|(
name|getMiddleX
argument_list|(
name|aLine
operator|.
name|getText
argument_list|()
argument_list|,
name|font2
argument_list|)
argument_list|,
name|borders
index|[
literal|0
index|]
operator|-
name|fm
operator|.
name|getHeight
argument_list|()
operator|*
operator|(
name|index
operator|+
literal|3
operator|)
operator|*
literal|1.5
argument_list|)
expr_stmt|;
name|aLine
operator|.
name|setDirection
argument_list|(
literal|0.0
argument_list|,
literal|1.0
argument_list|)
expr_stmt|;
name|aLine
operator|.
name|setFont
argument_list|(
name|font2
argument_list|)
expr_stmt|;
name|block
operator|=
operator|new
name|TextBlock
argument_list|()
expr_stmt|;
name|block
operator|.
name|setHeading
argument_list|(
name|aLine
argument_list|)
expr_stmt|;
name|block
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
comment|// Blocklines
block|{
name|aLine
operator|=
operator|new
name|AboutTextLine
argument_list|(
name|line
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
name|aLine
operator|.
name|setPos
argument_list|(
name|getMiddleX
argument_list|(
name|line
argument_list|,
name|font3
argument_list|)
argument_list|,
name|borders
index|[
literal|3
index|]
operator|+
operator|(
name|index
operator|*
name|fm
operator|.
name|getHeight
argument_list|()
operator|*
literal|1.5
operator|)
argument_list|)
expr_stmt|;
name|aLine
operator|.
name|setTag
argument_list|(
literal|10
argument_list|)
expr_stmt|;
name|aLine
operator|.
name|setDirection
argument_list|(
literal|0.0
argument_list|,
operator|-
literal|1.0
argument_list|)
expr_stmt|;
name|aLine
operator|.
name|setFont
argument_list|(
name|font3
argument_list|)
expr_stmt|;
name|block
operator|=
operator|new
name|TextBlock
argument_list|()
expr_stmt|;
name|block
operator|.
name|add
argument_list|(
name|aLine
argument_list|)
expr_stmt|;
name|block
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|index
operator|++
expr_stmt|;
block|}
block|}
block|}
block|}
name|input
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|block
operator|=
operator|new
name|TextBlock
argument_list|()
expr_stmt|;
name|block
operator|.
name|setHeading
argument_list|(
operator|new
name|AboutTextLine
argument_list|(
literal|"failure"
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|line
init|=
literal|"no infos available"
decl_stmt|;
name|aLine
operator|=
operator|new
name|AboutTextLine
argument_list|(
name|line
argument_list|)
expr_stmt|;
name|block
operator|.
name|add
argument_list|(
name|aLine
argument_list|)
expr_stmt|;
block|}
name|textBlocks
operator|.
name|add
argument_list|(
name|block
argument_list|)
expr_stmt|;
comment|// insert last block
block|}
comment|// ----------------------------------------------------------------------------
DECL|method|paintComponent ( Graphics g )
specifier|public
name|void
name|paintComponent
parameter_list|(
name|Graphics
name|g
parameter_list|)
block|{
if|if
condition|(
name|thread
operator|.
name|mode
operator|==
literal|0
condition|)
block|{
name|thread
operator|.
name|start
argument_list|()
expr_stmt|;
comment|//      thread.setEnabled(true);
block|}
elseif|else
if|if
condition|(
name|thread
operator|.
name|mode
operator|==
literal|1
condition|)
block|{
name|image
operator|.
name|paintIcon
argument_list|(
name|this
argument_list|,
name|g
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|FontMetrics
name|fm
init|=
name|g
operator|.
name|getFontMetrics
argument_list|(
name|font1
argument_list|)
decl_stmt|;
name|int
name|x1
init|=
operator|(
name|getWidth
argument_list|()
operator|-
name|fm
operator|.
name|stringWidth
argument_list|(
name|versionStr
argument_list|)
operator|)
operator|/
literal|2
decl_stmt|;
name|int
name|y1
init|=
name|getHeight
argument_list|()
operator|-
name|fm
operator|.
name|getHeight
argument_list|()
operator|-
literal|4
decl_stmt|;
name|int
name|y2
init|=
name|getHeight
argument_list|()
operator|-
literal|5
decl_stmt|;
comment|/*       int x1 = ( getWidth() - fm.stringWidth( versionStr ) ) / 2 ;       int y1 = 4 ;       int y2 = fm.getHeight() +4 ; */
name|g
operator|.
name|setFont
argument_list|(
name|font1
argument_list|)
expr_stmt|;
name|g
operator|.
name|setColor
argument_list|(
name|Color
operator|.
name|black
argument_list|)
expr_stmt|;
name|g
operator|.
name|drawString
argument_list|(
name|versionStr
argument_list|,
name|x1
argument_list|,
name|y1
argument_list|)
expr_stmt|;
name|g
operator|.
name|drawString
argument_list|(
name|buildStr
argument_list|,
name|x1
argument_list|,
name|y2
argument_list|)
expr_stmt|;
name|g
operator|.
name|setFont
argument_list|(
name|font2
argument_list|)
expr_stmt|;
name|fm
operator|=
name|g
operator|.
name|getFontMetrics
argument_list|(
name|font2
argument_list|)
expr_stmt|;
name|g
operator|.
name|drawString
argument_list|(
literal|"JabRef"
argument_list|,
operator|(
name|getWidth
argument_list|()
operator|-
name|fm
operator|.
name|stringWidth
argument_list|(
literal|"JabRef"
argument_list|)
operator|)
operator|/
literal|2
argument_list|,
name|fm
operator|.
name|getHeight
argument_list|()
operator|+
literal|10
argument_list|)
expr_stmt|;
for|for
control|(
name|TextBlock
name|block
range|:
name|textBlocks
control|)
block|{
if|if
condition|(
name|block
operator|.
name|isVisible
argument_list|()
condition|)
comment|// only if Block is marked as visible
block|{
comment|// print Heading
name|AboutTextLine
name|head
init|=
name|block
operator|.
name|getHeading
argument_list|()
decl_stmt|;
name|drawLine
argument_list|(
name|head
argument_list|,
name|g
argument_list|)
expr_stmt|;
for|for
control|(
name|AboutTextLine
name|line
range|:
name|block
control|)
block|{
name|drawLine
argument_list|(
name|line
argument_list|,
name|g
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
else|else
block|{
name|image
operator|.
name|paintIcon
argument_list|(
name|this
argument_list|,
name|g
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
expr_stmt|;
block|}
block|}
comment|// ----------------------------------------------------------------------------
DECL|method|drawLine (AboutTextLine line, Graphics g)
specifier|private
name|void
name|drawLine
parameter_list|(
name|AboutTextLine
name|line
parameter_list|,
name|Graphics
name|g
parameter_list|)
block|{
name|int
name|x
init|=
name|line
operator|.
name|getPosX
argument_list|()
decl_stmt|;
name|int
name|y
init|=
name|line
operator|.
name|getPosY
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|x
operator|>
name|borders
index|[
literal|0
index|]
operator|-
literal|10
operator|)
operator|&&
operator|(
name|x
operator|<
name|borders
index|[
literal|1
index|]
operator|+
literal|10
operator|)
operator|&&
operator|(
name|y
operator|>
name|borders
index|[
literal|2
index|]
operator|-
literal|10
operator|)
operator|&&
operator|(
name|y
operator|<
name|borders
index|[
literal|3
index|]
operator|+
literal|10
operator|)
condition|)
block|{
if|if
condition|(
name|line
operator|.
name|getVisible
argument_list|()
condition|)
block|{
name|g
operator|.
name|setFont
argument_list|(
name|line
operator|.
name|getFont
argument_list|()
argument_list|)
expr_stmt|;
name|g
operator|.
name|setColor
argument_list|(
name|line
operator|.
name|getColor
argument_list|()
argument_list|)
expr_stmt|;
name|g
operator|.
name|drawString
argument_list|(
name|line
operator|.
name|getText
argument_list|()
argument_list|,
name|line
operator|.
name|getPosX
argument_list|()
argument_list|,
name|line
operator|.
name|getPosY
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|// ----------------------------------------------------------------------------
comment|// ----------------------------------------------------------------------------
DECL|method|getPreferredSize ()
specifier|public
name|Dimension
name|getPreferredSize
parameter_list|()
block|{
return|return
operator|new
name|Dimension
argument_list|(
name|WIDTH
argument_list|,
name|HEIGHT
argument_list|)
return|;
block|}
DECL|method|removeNotify ()
specifier|public
name|void
name|removeNotify
parameter_list|()
block|{
name|super
operator|.
name|removeNotify
argument_list|()
expr_stmt|;
name|thread
operator|.
name|kill
argument_list|()
expr_stmt|;
block|}
DECL|method|skipAnimation ()
specifier|public
name|void
name|skipAnimation
parameter_list|()
block|{
name|thread
operator|.
name|kill
argument_list|()
expr_stmt|;
if|if
condition|(
name|aniListener
operator|!=
literal|null
condition|)
name|aniListener
operator|.
name|animationReady
argument_list|()
expr_stmt|;
block|}
comment|// ---------------------------------------------------------------------------
comment|// ---------------------------------------------------------------------------
DECL|class|AnimationThread
class|class
name|AnimationThread
extends|extends
name|Thread
block|{
DECL|field|running
specifier|private
name|boolean
name|running
init|=
literal|true
decl_stmt|;
DECL|field|help01
specifier|private
name|double
name|help01
init|=
literal|1.0
decl_stmt|;
DECL|field|mode
specifier|private
name|int
name|mode
init|=
literal|0
decl_stmt|;
DECL|field|sleepTime
specifier|public
name|int
name|sleepTime
init|=
literal|50
decl_stmt|;
DECL|field|zone2Counter
specifier|private
name|int
name|zone2Counter
init|=
literal|0
decl_stmt|;
DECL|field|runMode
specifier|private
name|boolean
name|runMode
init|=
literal|true
decl_stmt|;
DECL|method|AnimationThread ()
name|AnimationThread
parameter_list|()
block|{
name|super
argument_list|(
literal|"About box animation thread"
argument_list|)
expr_stmt|;
name|setPriority
argument_list|(
name|Thread
operator|.
name|MIN_PRIORITY
argument_list|)
expr_stmt|;
block|}
DECL|method|kill ()
specifier|public
name|void
name|kill
parameter_list|()
block|{
name|running
operator|=
literal|false
expr_stmt|;
block|}
DECL|method|setEnabled (boolean onOff)
specifier|public
name|void
name|setEnabled
parameter_list|(
name|boolean
name|onOff
parameter_list|)
block|{
name|runMode
operator|=
name|onOff
expr_stmt|;
block|}
DECL|method|setMode (int newMode)
specifier|public
specifier|synchronized
name|void
name|setMode
parameter_list|(
name|int
name|newMode
parameter_list|)
block|{
name|mode
operator|=
name|newMode
expr_stmt|;
block|}
DECL|method|run ()
specifier|public
name|void
name|run
parameter_list|()
block|{
name|Object
name|mutex
init|=
operator|new
name|Object
argument_list|()
decl_stmt|;
name|mode
operator|=
literal|1
expr_stmt|;
name|runMode
operator|=
literal|true
expr_stmt|;
while|while
condition|(
name|running
condition|)
block|{
synchronized|synchronized
init|(
name|mutex
init|)
block|{
try|try
block|{
do|do
block|{
name|mutex
operator|.
name|wait
argument_list|(
name|sleepTime
argument_list|)
expr_stmt|;
block|}
do|while
condition|(
operator|!
name|runMode
condition|)
do|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ignored
parameter_list|)
block|{           }
block|}
if|if
condition|(
name|mode
operator|==
literal|1
condition|)
comment|// Textanimation
block|{
name|int
name|counter
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Enumeration
argument_list|<
name|TextBlock
argument_list|>
name|myE
init|=
name|textBlocks
operator|.
name|elements
argument_list|()
init|;
name|myE
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|TextBlock
name|block
init|=
name|myE
operator|.
name|nextElement
argument_list|()
decl_stmt|;
name|AboutTextLine
name|head
init|=
name|block
operator|.
name|getHeading
argument_list|()
decl_stmt|;
name|counter
operator|=
name|performStep
argument_list|(
name|head
argument_list|)
expr_stmt|;
for|for
control|(
name|AboutTextLine
name|line
range|:
name|block
control|)
block|{
name|counter
operator|+=
name|performStep
argument_list|(
name|line
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|counter
operator|<
literal|1
condition|)
block|{
name|mode
operator|=
literal|2
expr_stmt|;
block|}
name|repaint
argument_list|(
name|borders
index|[
literal|0
index|]
operator|-
literal|10
argument_list|,
name|borders
index|[
literal|2
index|]
operator|-
literal|10
argument_list|,
name|borders
index|[
literal|1
index|]
operator|+
literal|10
argument_list|,
name|borders
index|[
literal|3
index|]
operator|+
literal|10
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|mode
operator|==
literal|2
condition|)
comment|// Picture animation
block|{
if|if
condition|(
name|sleepTime
operator|<
literal|2
condition|)
name|sleepTime
operator|=
literal|5
expr_stmt|;
else|else
name|sleepTime
operator|-=
name|sleepTime
operator|/
literal|3
expr_stmt|;
name|image
operator|.
name|setImage
argument_list|(
name|createImage
argument_list|(
name|iProducer
argument_list|)
argument_list|)
expr_stmt|;
name|repaint
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
name|WIDTH
argument_list|,
name|HEIGHT
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|performStep (AboutTextLine line)
specifier|private
name|int
name|performStep
parameter_list|(
name|AboutTextLine
name|line
parameter_list|)
block|{
name|int
name|back
init|=
literal|0
decl_stmt|;
name|line
operator|.
name|performTimeStep
argument_list|(
literal|1.0
argument_list|)
expr_stmt|;
if|if
condition|(
name|line
operator|.
name|getTag
argument_list|()
operator|==
literal|2
condition|)
comment|// Heading
block|{
name|int
name|zone
init|=
call|(
name|int
call|)
argument_list|(
name|HEIGHT
operator|/
literal|3.5
argument_list|)
decl_stmt|;
if|if
condition|(
name|line
operator|.
name|getPosY
argument_list|()
operator|>
name|zone
condition|)
block|{
name|line
operator|.
name|setSpeed
argument_list|(
literal|0.0
argument_list|)
expr_stmt|;
name|line
operator|.
name|setTag
argument_list|(
literal|4
argument_list|)
expr_stmt|;
name|zone2Counter
operator|=
literal|0
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|line
operator|.
name|getPosY
argument_list|()
operator|>
operator|(
name|zone
operator|-
literal|10
operator|)
condition|)
block|{
name|zone2Counter
operator|=
literal|1
expr_stmt|;
block|}
name|back
operator|++
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|line
operator|.
name|getTag
argument_list|()
operator|==
literal|4
condition|)
comment|// Heading Blender
block|{
if|if
condition|(
name|zone2Counter
operator|<
literal|1
condition|)
block|{
name|Color
name|col
init|=
name|line
operator|.
name|getColor
argument_list|()
decl_stmt|;
name|int
name|rgb
init|=
name|col
operator|.
name|getRGB
argument_list|()
operator|+
literal|1023
decl_stmt|;
name|line
operator|.
name|setColor
argument_list|(
operator|new
name|Color
argument_list|(
name|rgb
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|line
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|line
operator|.
name|setTag
argument_list|(
literal|5
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|line
operator|.
name|getTag
argument_list|()
operator|==
literal|10
condition|)
comment|// scrolling text
block|{
if|if
condition|(
name|line
operator|.
name|getPosY
argument_list|()
operator|<
operator|(
name|HEIGHT
operator|/
literal|3
operator|)
condition|)
block|{
name|line
operator|.
name|setDirection
argument_list|(
name|help01
argument_list|,
literal|0.0
argument_list|)
expr_stmt|;
name|line
operator|.
name|setAccel
argument_list|(
literal|0.5
argument_list|)
expr_stmt|;
name|line
operator|.
name|setTag
argument_list|(
literal|11
argument_list|)
expr_stmt|;
name|help01
operator|=
name|help01
operator|*
operator|-
literal|1.0
expr_stmt|;
block|}
name|back
operator|=
literal|1
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|line
operator|.
name|getTag
argument_list|()
operator|==
literal|11
condition|)
comment|// text line out
block|{
if|if
condition|(
operator|(
name|line
operator|.
name|getPosX
argument_list|()
operator|<
operator|-
literal|100
operator|)
operator|||
operator|(
name|line
operator|.
name|getPosX
argument_list|()
operator|>
name|WIDTH
operator|+
literal|100
operator|)
condition|)
block|{
name|line
operator|.
name|setTag
argument_list|(
literal|12
argument_list|)
expr_stmt|;
name|line
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
name|back
operator|=
literal|1
expr_stmt|;
block|}
return|return
name|back
return|;
block|}
block|}
block|}
end_class

end_unit

