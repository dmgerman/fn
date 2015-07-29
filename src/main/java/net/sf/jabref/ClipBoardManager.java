begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_comment
comment|// created by : r.nagel 14.09.2004
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|// function : handle all clipboard action
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|// modified :
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
name|datatransfer
operator|.
name|Clipboard
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|datatransfer
operator|.
name|ClipboardOwner
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|datatransfer
operator|.
name|DataFlavor
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|datatransfer
operator|.
name|StringSelection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|datatransfer
operator|.
name|Transferable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|datatransfer
operator|.
name|UnsupportedFlavorException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_class
DECL|class|ClipBoardManager
specifier|public
class|class
name|ClipBoardManager
implements|implements
name|ClipboardOwner
block|{
DECL|field|clipBoard
specifier|public
specifier|static
specifier|final
name|ClipBoardManager
name|clipBoard
init|=
operator|new
name|ClipBoardManager
argument_list|()
decl_stmt|;
comment|/**      * Empty implementation of the ClipboardOwner interface.      */
annotation|@
name|Override
DECL|method|lostOwnership (Clipboard aClipboard, Transferable aContents)
specifier|public
name|void
name|lostOwnership
parameter_list|(
name|Clipboard
name|aClipboard
parameter_list|,
name|Transferable
name|aContents
parameter_list|)
block|{
comment|//do nothing
block|}
comment|/**      * Place a String on the clipboard, and make this class the      * owner of the Clipboard's contents.      */
DECL|method|setClipboardContents (String aString)
specifier|public
name|void
name|setClipboardContents
parameter_list|(
name|String
name|aString
parameter_list|)
block|{
name|StringSelection
name|stringSelection
init|=
operator|new
name|StringSelection
argument_list|(
name|aString
argument_list|)
decl_stmt|;
name|Clipboard
name|clipboard
init|=
name|Toolkit
operator|.
name|getDefaultToolkit
argument_list|()
operator|.
name|getSystemClipboard
argument_list|()
decl_stmt|;
name|clipboard
operator|.
name|setContents
argument_list|(
name|stringSelection
argument_list|,
name|this
argument_list|)
expr_stmt|;
block|}
comment|/**      * Get the String residing on the clipboard.      *      * @return any text found on the Clipboard; if none found, return an      * empty String.      */
DECL|method|getClipboardContents ()
specifier|public
name|String
name|getClipboardContents
parameter_list|()
block|{
name|String
name|result
init|=
literal|""
decl_stmt|;
name|Clipboard
name|clipboard
init|=
name|Toolkit
operator|.
name|getDefaultToolkit
argument_list|()
operator|.
name|getSystemClipboard
argument_list|()
decl_stmt|;
comment|//odd: the Object param of getContents is not currently used
name|Transferable
name|contents
init|=
name|clipboard
operator|.
name|getContents
argument_list|(
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|contents
operator|!=
literal|null
operator|&&
name|contents
operator|.
name|isDataFlavorSupported
argument_list|(
name|DataFlavor
operator|.
name|stringFlavor
argument_list|)
condition|)
block|{
try|try
block|{
name|result
operator|=
operator|(
name|String
operator|)
name|contents
operator|.
name|getTransferData
argument_list|(
name|DataFlavor
operator|.
name|stringFlavor
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsupportedFlavorException
decl||
name|IOException
name|ex
parameter_list|)
block|{
comment|//highly unlikely since we are using a standard DataFlavor
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|ex
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|result
return|;
block|}
block|}
end_class

end_unit

