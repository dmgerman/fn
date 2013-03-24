begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.external
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|external
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionEvent
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
name|ActionListener
import|;
end_import

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
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|*
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
name|*
import|;
end_import

begin_comment
comment|/**  * The menu item used in the popup menu for opening external resources associated  * with an entry. Shows the resource name and icon given, and adds an action listener  * to process the request if the user clicks this menu item.  */
end_comment

begin_class
DECL|class|ExternalFileMenuItem
specifier|public
class|class
name|ExternalFileMenuItem
extends|extends
name|JMenuItem
implements|implements
name|ActionListener
block|{
DECL|field|entry
specifier|private
name|BibtexEntry
name|entry
decl_stmt|;
DECL|field|link
specifier|final
name|String
name|link
decl_stmt|;
DECL|field|metaData
specifier|final
name|MetaData
name|metaData
decl_stmt|;
DECL|field|fileType
name|ExternalFileType
name|fileType
decl_stmt|;
DECL|field|frame
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|fieldName
specifier|private
name|String
name|fieldName
init|=
literal|null
decl_stmt|;
DECL|method|ExternalFileMenuItem (JabRefFrame frame, BibtexEntry entry, String name, String link, Icon icon, MetaData metaData, ExternalFileType fileType)
specifier|public
name|ExternalFileMenuItem
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|BibtexEntry
name|entry
parameter_list|,
name|String
name|name
parameter_list|,
name|String
name|link
parameter_list|,
name|Icon
name|icon
parameter_list|,
name|MetaData
name|metaData
parameter_list|,
name|ExternalFileType
name|fileType
parameter_list|)
block|{
name|super
argument_list|(
name|name
argument_list|,
name|icon
argument_list|)
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|this
operator|.
name|entry
operator|=
name|entry
expr_stmt|;
name|this
operator|.
name|link
operator|=
name|link
expr_stmt|;
name|this
operator|.
name|metaData
operator|=
name|metaData
expr_stmt|;
name|this
operator|.
name|fileType
operator|=
name|fileType
expr_stmt|;
name|addActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
DECL|method|ExternalFileMenuItem (JabRefFrame frame, BibtexEntry entry, String name, String link, Icon icon, MetaData metaData, String fieldName)
specifier|public
name|ExternalFileMenuItem
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|BibtexEntry
name|entry
parameter_list|,
name|String
name|name
parameter_list|,
name|String
name|link
parameter_list|,
name|Icon
name|icon
parameter_list|,
name|MetaData
name|metaData
parameter_list|,
name|String
name|fieldName
parameter_list|)
block|{
name|this
argument_list|(
name|frame
argument_list|,
name|entry
argument_list|,
name|name
argument_list|,
name|link
argument_list|,
name|icon
argument_list|,
name|metaData
argument_list|,
operator|(
name|ExternalFileType
operator|)
literal|null
argument_list|)
expr_stmt|;
name|this
operator|.
name|fieldName
operator|=
name|fieldName
expr_stmt|;
block|}
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|boolean
name|success
init|=
name|openLink
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|success
condition|)
block|{
name|frame
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Unable to open link."
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|openLink ()
specifier|public
name|boolean
name|openLink
parameter_list|()
block|{
name|frame
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"External viewer called"
argument_list|)
operator|+
literal|"."
argument_list|)
expr_stmt|;
try|try
block|{
name|ExternalFileType
name|type
init|=
name|fileType
decl_stmt|;
if|if
condition|(
name|this
operator|.
name|fileType
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|this
operator|.
name|fieldName
operator|!=
literal|null
condition|)
block|{
name|Util
operator|.
name|openExternalViewer
argument_list|(
name|frame
operator|.
name|basePanel
argument_list|()
operator|.
name|metaData
argument_list|()
argument_list|,
name|link
argument_list|,
name|fieldName
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
else|else
block|{
comment|// We don't already know the file type, so we try to deduce it from the extension:
name|File
name|file
init|=
operator|new
name|File
argument_list|(
name|link
argument_list|)
decl_stmt|;
comment|// We try to check the extension for the file:
name|String
name|name
init|=
name|file
operator|.
name|getName
argument_list|()
decl_stmt|;
name|int
name|pos
init|=
name|name
operator|.
name|indexOf
argument_list|(
literal|'.'
argument_list|)
decl_stmt|;
name|String
name|extension
init|=
operator|(
operator|(
name|pos
operator|>=
literal|0
operator|)
operator|&&
operator|(
name|pos
operator|<
name|name
operator|.
name|length
argument_list|()
operator|-
literal|1
operator|)
operator|)
condition|?
name|name
operator|.
name|substring
argument_list|(
name|pos
operator|+
literal|1
argument_list|)
operator|.
name|trim
argument_list|()
operator|.
name|toLowerCase
argument_list|()
else|:
literal|null
decl_stmt|;
comment|// Now we know the extension, check if it is one we know about:
name|type
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|getExternalFileTypeByExt
argument_list|(
name|extension
argument_list|)
expr_stmt|;
name|fileType
operator|=
name|type
expr_stmt|;
block|}
block|}
if|if
condition|(
name|type
operator|instanceof
name|UnknownExternalFileType
condition|)
return|return
name|Util
operator|.
name|openExternalFileUnknown
argument_list|(
name|frame
argument_list|,
name|entry
argument_list|,
name|metaData
argument_list|,
name|link
argument_list|,
operator|(
name|UnknownExternalFileType
operator|)
name|type
argument_list|)
return|;
else|else
return|return
name|Util
operator|.
name|openExternalFileAnyFormat
argument_list|(
name|metaData
argument_list|,
name|link
argument_list|,
name|type
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e1
parameter_list|)
block|{
comment|// See if we should show an error message concerning the application to open the
comment|// link with. We check if the file type is set, and if the file type has a non-empty
comment|// application link. If that link is referred by the error message, we can assume
comment|// that the problem is in the open-with-application setting:
if|if
condition|(
operator|(
name|fileType
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|fileType
operator|.
name|getOpenWith
argument_list|()
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|fileType
operator|.
name|getOpenWith
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
operator|)
operator|&&
operator|(
name|e1
operator|.
name|getMessage
argument_list|()
operator|.
name|indexOf
argument_list|(
name|fileType
operator|.
name|getOpenWith
argument_list|()
argument_list|)
operator|>=
literal|0
operator|)
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Unable to open link. "
operator|+
literal|"The application '%0' associated with the file type '%1' could not be called."
argument_list|,
name|fileType
operator|.
name|getOpenWith
argument_list|()
argument_list|,
name|fileType
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Could not open link"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
name|e1
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
return|return
literal|false
return|;
block|}
block|}
end_class

end_unit

