begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 Morten O. Alver, Nizar N. Batada  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
end_comment

begin_package
DECL|package|net.sf.jabref.export
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|*
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
name|*
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
name|java
operator|.
name|util
operator|.
name|Vector
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|JabRefFrame
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
name|Util
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
name|GUIGlobals
import|;
end_import

begin_comment
comment|/**  * Dialog for creating or modifying custom exports.  */
end_comment

begin_class
DECL|class|CustomExportDialog
class|class
name|CustomExportDialog
extends|extends
name|JDialog
block|{
name|JTextField
DECL|field|name
name|name
init|=
operator|new
name|JTextField
argument_list|(
literal|60
argument_list|)
decl_stmt|,
DECL|field|layoutFile
name|layoutFile
init|=
operator|new
name|JTextField
argument_list|(
literal|60
argument_list|)
decl_stmt|,
DECL|field|extension
name|extension
init|=
operator|new
name|JTextField
argument_list|(
literal|60
argument_list|)
decl_stmt|;
name|JLabel
DECL|field|nl
name|nl
init|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Export name"
argument_list|)
operator|+
literal|":"
argument_list|)
decl_stmt|,
DECL|field|nr
name|nr
init|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Main layout file"
argument_list|)
operator|+
literal|":"
argument_list|)
decl_stmt|,
DECL|field|nf
name|nf
init|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"File extension"
argument_list|)
operator|+
literal|":"
argument_list|)
decl_stmt|;
name|JButton
DECL|field|ok
name|ok
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Ok"
argument_list|)
argument_list|)
decl_stmt|,
DECL|field|cancel
name|cancel
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
argument_list|)
decl_stmt|,
DECL|field|browse
name|browse
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Browse"
argument_list|)
argument_list|)
decl_stmt|;
name|JPanel
DECL|field|main
name|main
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|,
DECL|field|opt
name|opt
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|field|ok_pressed
specifier|private
name|boolean
name|ok_pressed
init|=
literal|false
decl_stmt|;
DECL|field|groups
specifier|private
name|Vector
name|groups
decl_stmt|;
DECL|field|index
specifier|private
name|int
name|index
decl_stmt|;
DECL|field|parent
specifier|private
name|JabRefFrame
name|parent
decl_stmt|;
DECL|field|oldName
DECL|field|oldRegexp
DECL|field|oldField
specifier|private
name|String
comment|/*name, regexp, field,*/
name|oldName
decl_stmt|,
name|oldRegexp
decl_stmt|,
name|oldField
decl_stmt|;
DECL|field|gbl
name|GridBagLayout
name|gbl
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
DECL|field|con
name|GridBagConstraints
name|con
init|=
operator|new
name|GridBagConstraints
argument_list|()
decl_stmt|;
DECL|method|CustomExportDialog (JabRefFrame parent_, String name_, String layoutFile_, String extension_)
specifier|public
name|CustomExportDialog
parameter_list|(
name|JabRefFrame
name|parent_
parameter_list|,
name|String
name|name_
parameter_list|,
name|String
name|layoutFile_
parameter_list|,
name|String
name|extension_
parameter_list|)
block|{
name|this
argument_list|(
name|parent_
argument_list|)
expr_stmt|;
name|name
operator|.
name|setText
argument_list|(
name|name_
argument_list|)
expr_stmt|;
name|layoutFile
operator|.
name|setText
argument_list|(
name|layoutFile_
argument_list|)
expr_stmt|;
name|extension
operator|.
name|setText
argument_list|(
name|extension_
argument_list|)
expr_stmt|;
block|}
DECL|method|CustomExportDialog (JabRefFrame parent_)
specifier|public
name|CustomExportDialog
parameter_list|(
name|JabRefFrame
name|parent_
parameter_list|)
block|{
name|super
argument_list|(
name|parent_
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Edit custom export"
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|parent
operator|=
name|parent_
expr_stmt|;
comment|//groups = groups_;
comment|//index = index_;
comment|/*if (index>= 0) { 	    // Group entry already exists. 	    try { 		oldField = (String)groups.elementAt(index); 		field.setText(oldField); 		oldName = (String)groups.elementAt(index+1); 		name.setText(oldName); 		oldRegexp = (String)groups.elementAt(index+2); 		regexp.setText(oldRegexp);  		// We disable these text fields, since changing field 		// or regexp would leave the entries added to the 		// group hanging. 		field.setEnabled(false); 		regexp.setEnabled(false); 	    } catch (ArrayIndexOutOfBoundsException ex) { 	    } 	} else 	    field.setText(defaultField); */
name|ActionListener
name|okListener
init|=
operator|new
name|ActionListener
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
comment|// Check that there are no empty strings.
if|if
condition|(
operator|(
name|layoutFile
operator|.
name|getText
argument_list|()
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
operator|)
operator|||
operator|(
name|name
operator|.
name|getText
argument_list|()
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
operator|)
operator|||
operator|(
name|extension
operator|.
name|getText
argument_list|()
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
operator|)
operator|||
operator|(
operator|!
name|layoutFile
operator|.
name|getText
argument_list|()
operator|.
name|endsWith
argument_list|(
literal|".layout"
argument_list|)
operator|)
condition|)
block|{
comment|//JOptionPane.showMessageDialog
comment|//    (parent, Globals.lang("You must provide a name, a search "
comment|//			  +"string and a field name for this group."),
comment|//			  Globals.lang("Create group"),
comment|//     JOptionPane.ERROR_MESSAGE);
return|return;
block|}
comment|// Handling of : and ; must also be done.
name|ok_pressed
operator|=
literal|true
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
decl_stmt|;
name|ok
operator|.
name|addActionListener
argument_list|(
name|okListener
argument_list|)
expr_stmt|;
name|name
operator|.
name|addActionListener
argument_list|(
name|okListener
argument_list|)
expr_stmt|;
name|layoutFile
operator|.
name|addActionListener
argument_list|(
name|okListener
argument_list|)
expr_stmt|;
name|extension
operator|.
name|addActionListener
argument_list|(
name|okListener
argument_list|)
expr_stmt|;
name|AbstractAction
name|cancelAction
init|=
operator|new
name|AbstractAction
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
decl_stmt|;
name|cancel
operator|.
name|addActionListener
argument_list|(
name|cancelAction
argument_list|)
expr_stmt|;
name|browse
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|File
name|directory
init|=
operator|new
name|File
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"exportWorkingDirectory"
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|chosenStr
init|=
name|Globals
operator|.
name|getNewFile
argument_list|(
name|parent
argument_list|,
name|Globals
operator|.
name|prefs
argument_list|,
name|directory
argument_list|,
literal|".layout"
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
name|chosenStr
operator|==
literal|null
condition|)
return|return;
name|File
name|chosen
init|=
operator|new
name|File
argument_list|(
name|chosenStr
argument_list|)
decl_stmt|;
comment|// Update working directory for layout files.
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
literal|"exportWorkingDirectory"
argument_list|,
name|chosen
operator|.
name|getParent
argument_list|()
argument_list|)
expr_stmt|;
name|layoutFile
operator|.
name|setText
argument_list|(
name|chosen
operator|.
name|getPath
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
comment|// Key bindings:
name|ActionMap
name|am
init|=
name|main
operator|.
name|getActionMap
argument_list|()
decl_stmt|;
name|InputMap
name|im
init|=
name|main
operator|.
name|getInputMap
argument_list|(
name|JComponent
operator|.
name|WHEN_IN_FOCUSED_WINDOW
argument_list|)
decl_stmt|;
name|im
operator|.
name|put
argument_list|(
name|parent
operator|.
name|prefs
argument_list|()
operator|.
name|getKey
argument_list|(
literal|"Close dialog"
argument_list|)
argument_list|,
literal|"close"
argument_list|)
expr_stmt|;
name|am
operator|.
name|put
argument_list|(
literal|"close"
argument_list|,
name|cancelAction
argument_list|)
expr_stmt|;
comment|// Layout starts here.
name|main
operator|.
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|opt
operator|.
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|main
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createTitledBorder
argument_list|(
name|BorderFactory
operator|.
name|createEtchedBorder
argument_list|()
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Export properties"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|// Main panel:
name|con
operator|.
name|weightx
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|3
argument_list|,
literal|5
argument_list|,
literal|3
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|con
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|EAST
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|NONE
expr_stmt|;
name|con
operator|.
name|gridx
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
literal|0
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|nl
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|nl
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|nr
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|nr
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
literal|2
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|nf
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|nf
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|2
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|WEST
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|HORIZONTAL
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|gridx
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|name
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|layoutFile
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|layoutFile
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridx
operator|=
literal|2
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|0
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|browse
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|browse
argument_list|)
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|2
expr_stmt|;
name|con
operator|.
name|gridx
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
literal|2
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|extension
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|extension
argument_list|)
expr_stmt|;
comment|// Option buttons:
name|con
operator|.
name|gridx
operator|=
name|GridBagConstraints
operator|.
name|RELATIVE
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
name|GridBagConstraints
operator|.
name|RELATIVE
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|EAST
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|NONE
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|ok
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|opt
operator|.
name|add
argument_list|(
name|ok
argument_list|)
expr_stmt|;
name|con
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|WEST
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|cancel
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|opt
operator|.
name|add
argument_list|(
name|cancel
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|main
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|opt
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
comment|//pack();
name|setSize
argument_list|(
literal|600
argument_list|,
literal|170
argument_list|)
expr_stmt|;
name|Util
operator|.
name|placeDialog
argument_list|(
name|this
argument_list|,
name|parent
argument_list|)
expr_stmt|;
block|}
DECL|method|okPressed ()
specifier|public
name|boolean
name|okPressed
parameter_list|()
block|{
return|return
name|ok_pressed
return|;
block|}
DECL|method|index ()
specifier|public
name|int
name|index
parameter_list|()
block|{
return|return
name|index
return|;
block|}
DECL|method|oldField ()
specifier|public
name|String
name|oldField
parameter_list|()
block|{
return|return
name|oldField
return|;
block|}
DECL|method|oldName ()
specifier|public
name|String
name|oldName
parameter_list|()
block|{
return|return
name|oldName
return|;
block|}
DECL|method|oldRegexp ()
specifier|public
name|String
name|oldRegexp
parameter_list|()
block|{
return|return
name|oldRegexp
return|;
block|}
DECL|method|layoutFile ()
specifier|public
name|String
name|layoutFile
parameter_list|()
block|{
return|return
name|layoutFile
operator|.
name|getText
argument_list|()
return|;
block|}
DECL|method|name ()
specifier|public
name|String
name|name
parameter_list|()
block|{
return|return
name|name
operator|.
name|getText
argument_list|()
return|;
block|}
DECL|method|extension ()
specifier|public
name|String
name|extension
parameter_list|()
block|{
return|return
name|extension
operator|.
name|getText
argument_list|()
return|;
block|}
block|}
end_class

end_unit

