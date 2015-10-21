begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|BorderLayout
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
name|javax
operator|.
name|swing
operator|.
name|*
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|DocumentEvent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|DocumentListener
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

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|JabRefPreferences
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
name|FileDialogs
import|;
end_import

begin_import
import|import
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|builder
operator|.
name|ButtonBarBuilder
import|;
end_import

begin_import
import|import
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|builder
operator|.
name|FormBuilder
import|;
end_import

begin_import
import|import
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|layout
operator|.
name|FormLayout
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
name|IconTheme
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|OS
import|;
end_import

begin_comment
comment|/**  * This class produces a dialog box for editing an external file type.  */
end_comment

begin_class
DECL|class|ExternalFileTypeEntryEditor
specifier|public
class|class
name|ExternalFileTypeEntryEditor
block|{
DECL|field|fParent
specifier|private
name|JFrame
name|fParent
decl_stmt|;
DECL|field|dParent
specifier|private
name|JDialog
name|dParent
decl_stmt|;
DECL|field|diag
specifier|private
name|JDialog
name|diag
decl_stmt|;
DECL|field|extension
specifier|private
specifier|final
name|JTextField
name|extension
init|=
operator|new
name|JTextField
argument_list|()
decl_stmt|;
DECL|field|name
specifier|private
specifier|final
name|JTextField
name|name
init|=
operator|new
name|JTextField
argument_list|()
decl_stmt|;
DECL|field|mimeType
specifier|private
specifier|final
name|JTextField
name|mimeType
init|=
operator|new
name|JTextField
argument_list|()
decl_stmt|;
DECL|field|application
specifier|private
specifier|final
name|JTextField
name|application
init|=
operator|new
name|JTextField
argument_list|()
decl_stmt|;
DECL|field|selectedIcon
specifier|private
name|String
name|selectedIcon
decl_stmt|;
DECL|field|icon
specifier|private
specifier|final
name|JButton
name|icon
init|=
operator|new
name|JButton
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|FILE
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
decl_stmt|;
DECL|field|ok
specifier|private
specifier|final
name|JButton
name|ok
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Ok"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|cancel
specifier|private
specifier|final
name|JButton
name|cancel
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|useDefault
specifier|private
specifier|final
name|JRadioButton
name|useDefault
init|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Default"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|other
specifier|private
specifier|final
name|JRadioButton
name|other
init|=
operator|new
name|JRadioButton
argument_list|(
literal|""
argument_list|)
decl_stmt|;
DECL|field|emptyMessage
specifier|final
name|String
name|emptyMessage
init|=
literal|"<"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Use default viewer"
argument_list|)
operator|+
literal|">"
decl_stmt|;
DECL|field|editFileTitle
specifier|private
specifier|final
name|String
name|editFileTitle
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Edit file type"
argument_list|)
decl_stmt|;
DECL|field|newFileTitle
specifier|private
specifier|final
name|String
name|newFileTitle
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Add new file type"
argument_list|)
decl_stmt|;
DECL|field|applicationFieldEmpty
name|boolean
name|applicationFieldEmpty
decl_stmt|;
DECL|field|entry
specifier|private
name|ExternalFileType
name|entry
decl_stmt|;
DECL|field|okPressed
specifier|private
name|boolean
name|okPressed
decl_stmt|;
DECL|method|ExternalFileTypeEntryEditor (JFrame parent, ExternalFileType entry)
specifier|public
name|ExternalFileTypeEntryEditor
parameter_list|(
name|JFrame
name|parent
parameter_list|,
name|ExternalFileType
name|entry
parameter_list|)
block|{
name|fParent
operator|=
name|parent
expr_stmt|;
name|init
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
DECL|method|ExternalFileTypeEntryEditor (JDialog parent, ExternalFileType entry)
specifier|public
name|ExternalFileTypeEntryEditor
parameter_list|(
name|JDialog
name|parent
parameter_list|,
name|ExternalFileType
name|entry
parameter_list|)
block|{
name|dParent
operator|=
name|parent
expr_stmt|;
name|init
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
DECL|method|init (ExternalFileType entry)
specifier|private
name|void
name|init
parameter_list|(
name|ExternalFileType
name|entry
parameter_list|)
block|{
name|this
operator|.
name|entry
operator|=
name|entry
expr_stmt|;
name|icon
operator|.
name|setText
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|ButtonGroup
name|bg
init|=
operator|new
name|ButtonGroup
argument_list|()
decl_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|useDefault
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|other
argument_list|)
expr_stmt|;
name|FormBuilder
name|builder
init|=
name|FormBuilder
operator|.
name|create
argument_list|()
decl_stmt|;
name|builder
operator|.
name|layout
argument_list|(
operator|new
name|FormLayout
argument_list|(
literal|"left:pref, 4dlu, fill:150dlu, 4dlu, fill:pref"
argument_list|,
literal|"p, 2dlu, p, 2dlu, p, 2dlu, p, 2dlu, p, 2dlu, p"
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Icon"
argument_list|)
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|icon
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Name"
argument_list|)
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|name
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Extension"
argument_list|)
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|extension
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"MIME type"
argument_list|)
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|7
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|mimeType
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|7
argument_list|)
expr_stmt|;
name|builder
operator|.
name|getPanel
argument_list|()
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Application"
argument_list|)
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|9
argument_list|)
expr_stmt|;
name|JButton
name|browseBut
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Browse"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|OS
operator|.
name|WINDOWS
condition|)
block|{
name|builder
operator|.
name|add
argument_list|(
name|useDefault
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|9
argument_list|)
expr_stmt|;
name|builder
operator|.
name|appendRows
argument_list|(
literal|"2dlu, p"
argument_list|)
expr_stmt|;
name|JPanel
name|p1
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|p1
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|11
argument_list|)
expr_stmt|;
name|JPanel
name|p2
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|application
operator|.
name|setPreferredSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|300
argument_list|,
name|application
operator|.
name|getPreferredSize
argument_list|()
operator|.
name|height
argument_list|)
argument_list|)
expr_stmt|;
name|BorderLayout
name|bl
init|=
operator|new
name|BorderLayout
argument_list|()
decl_stmt|;
name|bl
operator|.
name|setHgap
argument_list|(
literal|4
argument_list|)
expr_stmt|;
name|p2
operator|.
name|setLayout
argument_list|(
name|bl
argument_list|)
expr_stmt|;
name|p2
operator|.
name|add
argument_list|(
name|other
argument_list|,
name|BorderLayout
operator|.
name|WEST
argument_list|)
expr_stmt|;
name|p2
operator|.
name|add
argument_list|(
name|application
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|p2
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|11
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|browseBut
argument_list|)
operator|.
name|xy
argument_list|(
literal|5
argument_list|,
literal|11
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|builder
operator|.
name|add
argument_list|(
name|application
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|9
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|browseBut
argument_list|)
operator|.
name|xy
argument_list|(
literal|5
argument_list|,
literal|9
argument_list|)
expr_stmt|;
block|}
name|ButtonBarBuilder
name|bb
init|=
operator|new
name|ButtonBarBuilder
argument_list|()
decl_stmt|;
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|ok
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|cancel
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|ok
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|okPressed
operator|=
literal|true
expr_stmt|;
name|storeSettings
argument_list|(
name|ExternalFileTypeEntryEditor
operator|.
name|this
operator|.
name|entry
argument_list|)
expr_stmt|;
name|diag
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|cancel
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|diag
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
if|if
condition|(
name|OS
operator|.
name|WINDOWS
condition|)
block|{
name|application
operator|.
name|getDocument
argument_list|()
operator|.
name|addDocumentListener
argument_list|(
operator|new
name|DocumentListener
argument_list|()
block|{
specifier|private
name|void
name|handle
parameter_list|(
name|DocumentEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|application
operator|.
name|getText
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|useDefault
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|other
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|insertUpdate
parameter_list|(
name|DocumentEvent
name|e
parameter_list|)
block|{
name|handle
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|removeUpdate
parameter_list|(
name|DocumentEvent
name|documentEvent
parameter_list|)
block|{
name|handle
argument_list|(
name|documentEvent
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|changedUpdate
parameter_list|(
name|DocumentEvent
name|documentEvent
parameter_list|)
block|{
name|handle
argument_list|(
name|documentEvent
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
name|String
name|title
init|=
name|editFileTitle
decl_stmt|;
if|if
condition|(
name|entry
operator|.
name|getName
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|title
operator|=
name|newFileTitle
expr_stmt|;
block|}
if|if
condition|(
name|dParent
operator|!=
literal|null
condition|)
block|{
name|diag
operator|=
operator|new
name|JDialog
argument_list|(
name|dParent
argument_list|,
name|title
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|diag
operator|=
operator|new
name|JDialog
argument_list|(
name|fParent
argument_list|,
name|title
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
name|diag
operator|.
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|builder
operator|.
name|getPanel
argument_list|()
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|diag
operator|.
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|bb
operator|.
name|getPanel
argument_list|()
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
name|diag
operator|.
name|pack
argument_list|()
expr_stmt|;
name|BrowseListener
name|browse
init|=
operator|new
name|BrowseListener
argument_list|(
name|diag
argument_list|,
name|application
argument_list|)
decl_stmt|;
name|browseBut
operator|.
name|addActionListener
argument_list|(
name|browse
argument_list|)
expr_stmt|;
if|if
condition|(
name|dParent
operator|!=
literal|null
condition|)
block|{
name|diag
operator|.
name|setLocationRelativeTo
argument_list|(
name|dParent
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|diag
operator|.
name|setLocationRelativeTo
argument_list|(
name|fParent
argument_list|)
expr_stmt|;
comment|//Util.placeDialog(diag, parent);
block|}
name|setValues
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
DECL|method|setEntry (ExternalFileType entry)
specifier|public
name|void
name|setEntry
parameter_list|(
name|ExternalFileType
name|entry
parameter_list|)
block|{
name|this
operator|.
name|entry
operator|=
name|entry
expr_stmt|;
if|if
condition|(
name|entry
operator|.
name|getName
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|diag
operator|.
name|setTitle
argument_list|(
name|newFileTitle
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|diag
operator|.
name|setTitle
argument_list|(
name|editFileTitle
argument_list|)
expr_stmt|;
block|}
name|setValues
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
DECL|method|setVisible (boolean visible)
specifier|public
name|void
name|setVisible
parameter_list|(
name|boolean
name|visible
parameter_list|)
block|{
if|if
condition|(
name|visible
condition|)
block|{
name|okPressed
operator|=
literal|false
expr_stmt|;
block|}
name|diag
operator|.
name|setVisible
argument_list|(
name|visible
argument_list|)
expr_stmt|;
block|}
DECL|method|setValues (ExternalFileType entry)
specifier|private
name|void
name|setValues
parameter_list|(
name|ExternalFileType
name|entry
parameter_list|)
block|{
name|name
operator|.
name|setText
argument_list|(
name|entry
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|extension
operator|.
name|setText
argument_list|(
name|entry
operator|.
name|getExtension
argument_list|()
argument_list|)
expr_stmt|;
name|mimeType
operator|.
name|setText
argument_list|(
name|entry
operator|.
name|getMimeType
argument_list|()
argument_list|)
expr_stmt|;
name|application
operator|.
name|setText
argument_list|(
name|entry
operator|.
name|getOpenWith
argument_list|()
argument_list|)
expr_stmt|;
name|icon
operator|.
name|setIcon
argument_list|(
name|entry
operator|.
name|getIcon
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|application
operator|.
name|getText
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|useDefault
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|other
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
name|selectedIcon
operator|=
literal|null
expr_stmt|;
block|}
DECL|method|storeSettings (ExternalFileType entry)
specifier|private
name|void
name|storeSettings
parameter_list|(
name|ExternalFileType
name|entry
parameter_list|)
block|{
name|entry
operator|.
name|setName
argument_list|(
name|name
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setMimeType
argument_list|(
name|mimeType
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
comment|// Set extension, but remove initial dot if user has added that:
name|String
name|ext
init|=
name|extension
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|ext
operator|.
name|isEmpty
argument_list|()
operator|&&
name|ext
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|==
literal|'.'
condition|)
block|{
name|entry
operator|.
name|setExtension
argument_list|(
name|ext
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|entry
operator|.
name|setExtension
argument_list|(
name|ext
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|selectedIcon
operator|!=
literal|null
condition|)
block|{
name|entry
operator|.
name|setIconName
argument_list|(
name|selectedIcon
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setIcon
argument_list|(
name|IconTheme
operator|.
name|getImage
argument_list|(
name|entry
operator|.
name|getIconName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|OS
operator|.
name|WINDOWS
condition|)
block|{
name|entry
operator|.
name|setOpenWith
argument_list|(
name|application
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// On Windows, store application as empty if the "Default" option is selected,
comment|// or if the application name is empty:
if|if
condition|(
name|useDefault
operator|.
name|isSelected
argument_list|()
operator|||
name|application
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|entry
operator|.
name|setOpenWith
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|entry
operator|.
name|setOpenWith
argument_list|(
name|application
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|okPressed ()
specifier|public
name|boolean
name|okPressed
parameter_list|()
block|{
return|return
name|okPressed
return|;
block|}
DECL|class|BrowseListener
class|class
name|BrowseListener
implements|implements
name|ActionListener
block|{
DECL|field|comp
specifier|private
specifier|final
name|JTextField
name|comp
decl_stmt|;
DECL|method|BrowseListener (JDialog parent, JTextField comp)
specifier|public
name|BrowseListener
parameter_list|(
name|JDialog
name|parent
parameter_list|,
name|JTextField
name|comp
parameter_list|)
block|{
name|this
operator|.
name|comp
operator|=
name|comp
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|File
name|initial
init|=
operator|new
name|File
argument_list|(
name|comp
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|comp
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// Nothing in the field. Go to the last file dir used:
name|initial
operator|=
operator|new
name|File
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|FILE_WORKING_DIRECTORY
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|String
name|chosen
init|=
name|FileDialogs
operator|.
name|getNewFile
argument_list|(
comment|/*parent*/
literal|null
argument_list|,
name|initial
argument_list|,
name|Globals
operator|.
name|NONE
argument_list|,
name|JFileChooser
operator|.
name|OPEN_DIALOG
argument_list|,
literal|false
argument_list|)
decl_stmt|;
if|if
condition|(
name|chosen
operator|!=
literal|null
condition|)
block|{
name|File
name|newFile
init|=
operator|new
name|File
argument_list|(
name|chosen
argument_list|)
decl_stmt|;
comment|// Store the directory for next time:
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|FILE_WORKING_DIRECTORY
argument_list|,
name|newFile
operator|.
name|getParent
argument_list|()
argument_list|)
expr_stmt|;
name|comp
operator|.
name|setText
argument_list|(
name|newFile
operator|.
name|getPath
argument_list|()
argument_list|)
expr_stmt|;
name|comp
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

