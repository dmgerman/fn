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
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|StringWriter
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
name|export
operator|.
name|LatexFieldFormatter
import|;
end_import

begin_comment
comment|// created by : ?
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|// modified : r.nagel 2.09.2004
end_comment

begin_comment
comment|//            - insert close button
end_comment

begin_class
DECL|class|DuplicateResolverDialog
specifier|public
class|class
name|DuplicateResolverDialog
extends|extends
name|JDialog
block|{
specifier|public
specifier|static
specifier|final
name|int
DECL|field|NOT_CHOSEN
name|NOT_CHOSEN
init|=
operator|-
literal|1
decl_stmt|,
DECL|field|KEEP_BOTH
name|KEEP_BOTH
init|=
literal|0
decl_stmt|,
DECL|field|KEEP_UPPER
name|KEEP_UPPER
init|=
literal|1
decl_stmt|,
DECL|field|KEEP_LOWER
name|KEEP_LOWER
init|=
literal|2
decl_stmt|,
DECL|field|AUTOREMOVE_EXACT
name|AUTOREMOVE_EXACT
init|=
literal|3
decl_stmt|,
DECL|field|BREAK
name|BREAK
init|=
literal|5
decl_stmt|,
comment|// close
DECL|field|IMPORT_AND_DELETE_OLD
name|IMPORT_AND_DELETE_OLD
init|=
literal|1
decl_stmt|,
DECL|field|IMPORT_AND_KEEP_OLD
name|IMPORT_AND_KEEP_OLD
init|=
literal|0
decl_stmt|,
DECL|field|DO_NOT_IMPORT
name|DO_NOT_IMPORT
init|=
literal|2
decl_stmt|,
DECL|field|DUPLICATE_SEARCH
name|DUPLICATE_SEARCH
init|=
literal|1
decl_stmt|,
DECL|field|IMPORT_CHECK
name|IMPORT_CHECK
init|=
literal|2
decl_stmt|,
DECL|field|INSPECTION
name|INSPECTION
init|=
literal|3
decl_stmt|,
DECL|field|DUPLICATE_SEARCH_WITH_EXACT
name|DUPLICATE_SEARCH_WITH_EXACT
init|=
literal|4
decl_stmt|;
DECL|field|DIM
specifier|final
name|Dimension
name|DIM
init|=
operator|new
name|Dimension
argument_list|(
literal|650
argument_list|,
literal|600
argument_list|)
decl_stmt|;
DECL|field|p1
DECL|field|p2
name|PreviewPanel
name|p1
decl_stmt|,
name|p2
decl_stmt|;
DECL|field|ta1
DECL|field|ta2
name|JTextArea
name|ta1
decl_stmt|,
name|ta2
decl_stmt|;
DECL|field|tabbed
name|JTabbedPane
name|tabbed
init|=
operator|new
name|JTabbedPane
argument_list|()
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
DECL|field|first
DECL|field|second
DECL|field|both
name|JButton
name|first
decl_stmt|,
name|second
decl_stmt|,
name|both
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
DECL|field|removeExact
name|removeExact
init|=
literal|null
decl_stmt|;
DECL|field|options
name|JPanel
name|options
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|,
DECL|field|main
name|main
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|,
DECL|field|source
name|source
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|field|status
name|int
name|status
init|=
name|NOT_CHOSEN
decl_stmt|;
DECL|field|block
name|boolean
name|block
init|=
literal|true
decl_stmt|;
DECL|field|lab
name|TitleLabel
name|lab
decl_stmt|;
DECL|method|DuplicateResolverDialog (JFrame frame, BibtexEntry one, BibtexEntry two, int type)
specifier|public
name|DuplicateResolverDialog
parameter_list|(
name|JFrame
name|frame
parameter_list|,
name|BibtexEntry
name|one
parameter_list|,
name|BibtexEntry
name|two
parameter_list|,
name|int
name|type
parameter_list|)
block|{
name|super
argument_list|(
name|frame
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Possible duplicate entries"
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|init
argument_list|(
name|one
argument_list|,
name|two
argument_list|,
name|type
argument_list|)
expr_stmt|;
name|Util
operator|.
name|placeDialog
argument_list|(
name|this
argument_list|,
name|frame
argument_list|)
expr_stmt|;
block|}
DECL|method|DuplicateResolverDialog (JDialog frame, BibtexEntry one, BibtexEntry two, int type)
specifier|public
name|DuplicateResolverDialog
parameter_list|(
name|JDialog
name|frame
parameter_list|,
name|BibtexEntry
name|one
parameter_list|,
name|BibtexEntry
name|two
parameter_list|,
name|int
name|type
parameter_list|)
block|{
name|super
argument_list|(
name|frame
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Possible duplicate entries"
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|init
argument_list|(
name|one
argument_list|,
name|two
argument_list|,
name|type
argument_list|)
expr_stmt|;
name|Util
operator|.
name|placeDialog
argument_list|(
name|this
argument_list|,
name|frame
argument_list|)
expr_stmt|;
block|}
DECL|method|init (BibtexEntry one, BibtexEntry two, int type)
specifier|private
name|void
name|init
parameter_list|(
name|BibtexEntry
name|one
parameter_list|,
name|BibtexEntry
name|two
parameter_list|,
name|int
name|type
parameter_list|)
block|{
switch|switch
condition|(
name|type
condition|)
block|{
case|case
name|DUPLICATE_SEARCH
case|:
name|first
operator|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Keep upper"
argument_list|)
argument_list|)
expr_stmt|;
name|second
operator|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Keep lower"
argument_list|)
argument_list|)
expr_stmt|;
name|both
operator|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Keep both"
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|INSPECTION
case|:
name|first
operator|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Remove old entry"
argument_list|)
argument_list|)
expr_stmt|;
name|second
operator|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Remove entry from import"
argument_list|)
argument_list|)
expr_stmt|;
name|both
operator|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Keep both"
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|DUPLICATE_SEARCH_WITH_EXACT
case|:
name|first
operator|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Keep upper"
argument_list|)
argument_list|)
expr_stmt|;
name|second
operator|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Keep lower"
argument_list|)
argument_list|)
expr_stmt|;
name|both
operator|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Keep both"
argument_list|)
argument_list|)
expr_stmt|;
name|removeExact
operator|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Automatically remove exact duplicates"
argument_list|)
argument_list|)
expr_stmt|;
break|break;
default|default:
name|first
operator|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Import and remove old entry"
argument_list|)
argument_list|)
expr_stmt|;
name|second
operator|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Do not import entry"
argument_list|)
argument_list|)
expr_stmt|;
name|both
operator|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Import and keep old entry"
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|String
name|layout
init|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"preview0"
argument_list|)
decl_stmt|;
name|p1
operator|=
operator|new
name|PreviewPanel
argument_list|(
literal|null
argument_list|,
name|one
argument_list|,
literal|null
argument_list|,
operator|new
name|MetaData
argument_list|()
argument_list|,
name|layout
argument_list|)
expr_stmt|;
name|p2
operator|=
operator|new
name|PreviewPanel
argument_list|(
literal|null
argument_list|,
name|two
argument_list|,
literal|null
argument_list|,
operator|new
name|MetaData
argument_list|()
argument_list|,
name|layout
argument_list|)
expr_stmt|;
name|ta1
operator|=
operator|new
name|JTextArea
argument_list|()
expr_stmt|;
name|ta2
operator|=
operator|new
name|JTextArea
argument_list|()
expr_stmt|;
name|ta1
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|ta2
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
comment|//ta1.setPreferredSize(dim);
comment|//ta2.setPreferredSize(dim);
name|setSourceView
argument_list|(
name|one
argument_list|,
name|two
argument_list|)
expr_stmt|;
comment|//getContentPane().setLayout();
name|main
operator|.
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|source
operator|.
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|10
argument_list|,
literal|10
argument_list|,
literal|0
argument_list|,
literal|10
argument_list|)
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|BOTH
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|weighty
operator|=
literal|0
expr_stmt|;
name|lab
operator|=
operator|new
name|TitleLabel
argument_list|(
operator|(
name|type
operator|==
name|DUPLICATE_SEARCH
operator|)
condition|?
literal|""
else|:
name|Globals
operator|.
name|lang
argument_list|(
literal|"Entry in current database"
argument_list|)
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|lab
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|lab
argument_list|)
expr_stmt|;
name|con
operator|.
name|weighty
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
literal|5
argument_list|,
literal|10
argument_list|,
literal|10
argument_list|,
literal|10
argument_list|)
expr_stmt|;
name|JScrollPane
name|sp
init|=
operator|new
name|JScrollPane
argument_list|(
name|p1
argument_list|)
decl_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|sp
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|sp
argument_list|)
expr_stmt|;
name|con
operator|.
name|weighty
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|10
argument_list|,
literal|10
argument_list|,
literal|0
argument_list|,
literal|10
argument_list|)
expr_stmt|;
name|lab
operator|=
operator|new
name|TitleLabel
argument_list|(
operator|(
name|type
operator|==
name|DUPLICATE_SEARCH
operator|)
condition|?
literal|""
else|:
name|Globals
operator|.
name|lang
argument_list|(
literal|"Entry in import"
argument_list|)
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|lab
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|lab
argument_list|)
expr_stmt|;
name|con
operator|.
name|weighty
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
literal|5
argument_list|,
literal|10
argument_list|,
literal|10
argument_list|,
literal|10
argument_list|)
expr_stmt|;
name|sp
operator|=
operator|new
name|JScrollPane
argument_list|(
name|ta1
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|sp
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|source
operator|.
name|add
argument_list|(
name|sp
argument_list|)
expr_stmt|;
name|sp
operator|=
operator|new
name|JScrollPane
argument_list|(
name|p2
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|sp
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|sp
argument_list|)
expr_stmt|;
name|sp
operator|=
operator|new
name|JScrollPane
argument_list|(
name|ta2
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|sp
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|source
operator|.
name|add
argument_list|(
name|sp
argument_list|)
expr_stmt|;
name|tabbed
operator|.
name|add
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Short form"
argument_list|)
argument_list|,
name|main
argument_list|)
expr_stmt|;
name|tabbed
operator|.
name|add
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Complete record"
argument_list|)
argument_list|,
name|source
argument_list|)
expr_stmt|;
if|if
condition|(
name|removeExact
operator|!=
literal|null
condition|)
name|options
operator|.
name|add
argument_list|(
name|removeExact
argument_list|)
expr_stmt|;
name|options
operator|.
name|add
argument_list|(
name|first
argument_list|)
expr_stmt|;
name|options
operator|.
name|add
argument_list|(
name|second
argument_list|)
expr_stmt|;
name|options
operator|.
name|add
argument_list|(
name|both
argument_list|)
expr_stmt|;
if|if
condition|(
name|type
operator|!=
name|IMPORT_CHECK
condition|)
block|{
name|options
operator|.
name|add
argument_list|(
name|Box
operator|.
name|createHorizontalStrut
argument_list|(
literal|5
argument_list|)
argument_list|)
expr_stmt|;
name|options
operator|.
name|add
argument_list|(
name|cancel
argument_list|)
expr_stmt|;
block|}
name|first
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
name|status
operator|=
name|KEEP_UPPER
expr_stmt|;
name|block
operator|=
literal|false
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|second
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
name|status
operator|=
name|KEEP_LOWER
expr_stmt|;
name|block
operator|=
literal|false
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|both
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
name|status
operator|=
name|KEEP_BOTH
expr_stmt|;
name|block
operator|=
literal|false
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
if|if
condition|(
name|removeExact
operator|!=
literal|null
condition|)
name|removeExact
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
name|status
operator|=
name|AUTOREMOVE_EXACT
expr_stmt|;
name|block
operator|=
literal|false
expr_stmt|;
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
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|status
operator|=
name|BREAK
expr_stmt|;
name|block
operator|=
literal|false
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|tabbed
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
name|options
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
name|pack
argument_list|()
expr_stmt|;
if|if
condition|(
name|getHeight
argument_list|()
operator|>
name|DIM
operator|.
name|height
condition|)
block|{
name|setSize
argument_list|(
operator|new
name|Dimension
argument_list|(
name|getWidth
argument_list|()
argument_list|,
name|DIM
operator|.
name|height
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|getWidth
argument_list|()
operator|>
name|DIM
operator|.
name|width
condition|)
block|{
name|setSize
argument_list|(
operator|new
name|Dimension
argument_list|(
name|DIM
operator|.
name|width
argument_list|,
name|getHeight
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|both
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
block|}
DECL|method|setSourceView (BibtexEntry one, BibtexEntry two)
specifier|private
name|void
name|setSourceView
parameter_list|(
name|BibtexEntry
name|one
parameter_list|,
name|BibtexEntry
name|two
parameter_list|)
block|{
try|try
block|{
name|StringWriter
name|sw
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
name|one
operator|.
name|write
argument_list|(
name|sw
argument_list|,
operator|new
name|LatexFieldFormatter
argument_list|()
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|ta1
operator|.
name|setText
argument_list|(
name|sw
operator|.
name|getBuffer
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|sw
operator|=
operator|new
name|StringWriter
argument_list|()
expr_stmt|;
name|two
operator|.
name|write
argument_list|(
name|sw
argument_list|,
operator|new
name|LatexFieldFormatter
argument_list|()
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|ta2
operator|.
name|setText
argument_list|(
name|sw
operator|.
name|getBuffer
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ignored
parameter_list|)
block|{         }
block|}
DECL|method|setEntries (BibtexEntry newOne, BibtexEntry newTwo)
specifier|public
name|void
name|setEntries
parameter_list|(
name|BibtexEntry
name|newOne
parameter_list|,
name|BibtexEntry
name|newTwo
parameter_list|)
block|{
name|setSourceView
argument_list|(
name|newOne
argument_list|,
name|newTwo
argument_list|)
expr_stmt|;
name|p1
operator|.
name|setEntry
argument_list|(
name|newOne
argument_list|)
expr_stmt|;
name|p2
operator|.
name|setEntry
argument_list|(
name|newTwo
argument_list|)
expr_stmt|;
name|status
operator|=
name|NOT_CHOSEN
expr_stmt|;
name|p1
operator|.
name|revalidate
argument_list|()
expr_stmt|;
name|p1
operator|.
name|repaint
argument_list|()
expr_stmt|;
name|block
operator|=
literal|true
expr_stmt|;
block|}
DECL|method|isBlocking ()
specifier|public
name|boolean
name|isBlocking
parameter_list|()
block|{
return|return
name|block
return|;
block|}
DECL|method|getSelected ()
specifier|public
name|int
name|getSelected
parameter_list|()
block|{
return|return
name|status
return|;
block|}
DECL|method|resolveDuplicate (JFrame frame, BibtexEntry one, BibtexEntry two)
specifier|public
specifier|static
name|int
name|resolveDuplicate
parameter_list|(
name|JFrame
name|frame
parameter_list|,
name|BibtexEntry
name|one
parameter_list|,
name|BibtexEntry
name|two
parameter_list|)
block|{
name|DuplicateResolverDialog
name|drd
init|=
operator|new
name|DuplicateResolverDialog
argument_list|(
name|frame
argument_list|,
name|one
argument_list|,
name|two
argument_list|,
name|DUPLICATE_SEARCH
argument_list|)
decl_stmt|;
name|drd
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
comment|// drd.show(); -> deprecated since 1.5
return|return
name|drd
operator|.
name|getSelected
argument_list|()
return|;
block|}
DECL|method|resolveDuplicate (JDialog frame, BibtexEntry one, BibtexEntry two)
specifier|public
specifier|static
name|int
name|resolveDuplicate
parameter_list|(
name|JDialog
name|frame
parameter_list|,
name|BibtexEntry
name|one
parameter_list|,
name|BibtexEntry
name|two
parameter_list|)
block|{
name|DuplicateResolverDialog
name|drd
init|=
operator|new
name|DuplicateResolverDialog
argument_list|(
name|frame
argument_list|,
name|one
argument_list|,
name|two
argument_list|,
name|DUPLICATE_SEARCH
argument_list|)
decl_stmt|;
name|drd
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
comment|// drd.show(); -> deprecated since 1.5
return|return
name|drd
operator|.
name|getSelected
argument_list|()
return|;
block|}
DECL|method|resolveDuplicateInImport (JabRefFrame frame, BibtexEntry existing, BibtexEntry imported)
specifier|public
specifier|static
name|int
name|resolveDuplicateInImport
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|BibtexEntry
name|existing
parameter_list|,
name|BibtexEntry
name|imported
parameter_list|)
block|{
name|DuplicateResolverDialog
name|drd
init|=
operator|new
name|DuplicateResolverDialog
argument_list|(
name|frame
argument_list|,
name|existing
argument_list|,
name|imported
argument_list|,
name|IMPORT_CHECK
argument_list|)
decl_stmt|;
name|drd
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
comment|// drd.show(); -> deprecated since 1.5
return|return
name|drd
operator|.
name|getSelected
argument_list|()
return|;
block|}
block|}
end_class

end_unit

