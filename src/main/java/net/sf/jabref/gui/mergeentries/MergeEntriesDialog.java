begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2012-2105 JabRef contributors.  This program is free software; you can redistribute it and/or modify  it under the terms of the GNU General Public License as published by  the Free Software Foundation; either version 2 of the License, or  (at your option) any later version.   This program is distributed in the hope that it will be useful,  but WITHOUT ANY WARRANTY; without even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  GNU General Public License for more details.   You should have received a copy of the GNU General Public License along  with this program; if not, write to the Free Software Foundation, Inc.,  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.gui.mergeentries
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|mergeentries
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
name|ComponentAdapter
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
name|ComponentEvent
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
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
name|model
operator|.
name|entry
operator|.
name|BibEntry
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
name|BasePanel
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
name|undo
operator|.
name|NamedCompound
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
name|undo
operator|.
name|UndoableInsertEntry
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
name|undo
operator|.
name|UndoableRemoveEntry
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
name|util
operator|.
name|PositionWindow
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
name|layout
operator|.
name|CellConstraints
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
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|layout
operator|.
name|RowSpec
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
name|ColumnSpec
import|;
end_import

begin_comment
comment|/**  * @author Oscar  *  *         Dialog for merging two Bibtex entries  */
end_comment

begin_class
DECL|class|MergeEntriesDialog
specifier|public
class|class
name|MergeEntriesDialog
extends|extends
name|JDialog
block|{
DECL|field|panel
specifier|private
specifier|final
name|BasePanel
name|panel
decl_stmt|;
DECL|field|cc
specifier|private
specifier|final
name|CellConstraints
name|cc
init|=
operator|new
name|CellConstraints
argument_list|()
decl_stmt|;
DECL|field|pw
specifier|private
name|PositionWindow
name|pw
decl_stmt|;
DECL|field|MERGE_ENTRIES
specifier|private
specifier|static
specifier|final
name|String
name|MERGE_ENTRIES
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Merge entries"
argument_list|)
decl_stmt|;
DECL|field|MARGIN
specifier|private
specifier|static
specifier|final
name|String
name|MARGIN
init|=
literal|"5px"
decl_stmt|;
DECL|method|MergeEntriesDialog (BasePanel panel)
specifier|public
name|MergeEntriesDialog
parameter_list|(
name|BasePanel
name|panel
parameter_list|)
block|{
name|super
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|,
name|MERGE_ENTRIES
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|this
operator|.
name|panel
operator|=
name|panel
expr_stmt|;
comment|// Start setting up the dialog
name|init
argument_list|(
name|panel
operator|.
name|getSelectedEntries
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Sets up the dialog      *      * @param selected Selected BibtexEntries      */
DECL|method|init (List<BibEntry> selected)
specifier|private
name|void
name|init
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|selected
parameter_list|)
block|{
comment|// Check if there are two entries selected
if|if
condition|(
name|selected
operator|.
name|size
argument_list|()
operator|!=
literal|2
condition|)
block|{
comment|// None selected. Inform the user to select entries first.
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"You have to choose exactly two entries to merge."
argument_list|)
argument_list|,
name|MERGE_ENTRIES
argument_list|,
name|JOptionPane
operator|.
name|INFORMATION_MESSAGE
argument_list|)
expr_stmt|;
name|this
operator|.
name|dispose
argument_list|()
expr_stmt|;
return|return;
block|}
comment|// Store the two entries
name|BibEntry
name|one
init|=
name|selected
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|BibEntry
name|two
init|=
name|selected
operator|.
name|get
argument_list|(
literal|1
argument_list|)
decl_stmt|;
name|MergeEntries
name|mergeEntries
init|=
operator|new
name|MergeEntries
argument_list|(
name|one
argument_list|,
name|two
argument_list|,
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getMode
argument_list|()
argument_list|)
decl_stmt|;
comment|// Create undo-compound
name|NamedCompound
name|ce
init|=
operator|new
name|NamedCompound
argument_list|(
name|MERGE_ENTRIES
argument_list|)
decl_stmt|;
name|FormLayout
name|layout
init|=
operator|new
name|FormLayout
argument_list|(
literal|"fill:700px:grow"
argument_list|,
literal|"fill:400px:grow, 4px, p, 5px, p"
argument_list|)
decl_stmt|;
name|this
operator|.
name|setLayout
argument_list|(
name|layout
argument_list|)
expr_stmt|;
name|this
operator|.
name|add
argument_list|(
name|mergeEntries
operator|.
name|getMergeEntryPanel
argument_list|()
argument_list|,
name|cc
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|add
argument_list|(
operator|new
name|JSeparator
argument_list|()
argument_list|,
name|cc
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|3
argument_list|)
argument_list|)
expr_stmt|;
comment|// Create buttons
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
name|cancel
operator|.
name|setActionCommand
argument_list|(
literal|"cancel"
argument_list|)
expr_stmt|;
name|cancel
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
name|panel
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cancelled merging entries"
argument_list|)
argument_list|)
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|JButton
name|replaceentries
init|=
operator|new
name|JButton
argument_list|(
name|MERGE_ENTRIES
argument_list|)
decl_stmt|;
name|replaceentries
operator|.
name|setActionCommand
argument_list|(
literal|"replace"
argument_list|)
expr_stmt|;
name|replaceentries
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
comment|// Create a new entry and add it to the undo stack
comment|// Remove the other two entries and add them to the undo stack (which is not working...)
name|BibEntry
name|mergedEntry
init|=
name|mergeEntries
operator|.
name|getMergeEntry
argument_list|()
decl_stmt|;
name|panel
operator|.
name|insertEntry
argument_list|(
name|mergedEntry
argument_list|)
expr_stmt|;
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableInsertEntry
argument_list|(
name|panel
operator|.
name|database
argument_list|()
argument_list|,
name|mergedEntry
argument_list|,
name|panel
argument_list|)
argument_list|)
expr_stmt|;
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableRemoveEntry
argument_list|(
name|panel
operator|.
name|database
argument_list|()
argument_list|,
name|one
argument_list|,
name|panel
argument_list|)
argument_list|)
expr_stmt|;
name|panel
operator|.
name|database
argument_list|()
operator|.
name|removeEntry
argument_list|(
name|one
argument_list|)
expr_stmt|;
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableRemoveEntry
argument_list|(
name|panel
operator|.
name|database
argument_list|()
argument_list|,
name|two
argument_list|,
name|panel
argument_list|)
argument_list|)
expr_stmt|;
name|panel
operator|.
name|database
argument_list|()
operator|.
name|removeEntry
argument_list|(
name|two
argument_list|)
expr_stmt|;
name|ce
operator|.
name|end
argument_list|()
expr_stmt|;
name|panel
operator|.
name|undoManager
operator|.
name|addEdit
argument_list|(
name|ce
argument_list|)
expr_stmt|;
name|panel
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Merged entries"
argument_list|)
argument_list|)
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
operator|new
name|JButton
index|[]
block|{
name|replaceentries
block|,
name|cancel
block|}
argument_list|)
expr_stmt|;
name|this
operator|.
name|add
argument_list|(
name|bb
operator|.
name|getPanel
argument_list|()
argument_list|,
name|cc
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|5
argument_list|)
argument_list|)
expr_stmt|;
comment|// Add some margin around the layout
name|layout
operator|.
name|appendRow
argument_list|(
name|RowSpec
operator|.
name|decode
argument_list|(
name|MARGIN
argument_list|)
argument_list|)
expr_stmt|;
name|layout
operator|.
name|appendColumn
argument_list|(
name|ColumnSpec
operator|.
name|decode
argument_list|(
name|MARGIN
argument_list|)
argument_list|)
expr_stmt|;
name|layout
operator|.
name|insertRow
argument_list|(
literal|1
argument_list|,
name|RowSpec
operator|.
name|decode
argument_list|(
name|MARGIN
argument_list|)
argument_list|)
expr_stmt|;
name|layout
operator|.
name|insertColumn
argument_list|(
literal|1
argument_list|,
name|ColumnSpec
operator|.
name|decode
argument_list|(
name|MARGIN
argument_list|)
argument_list|)
expr_stmt|;
comment|// Set up a ComponentListener that saves the last size and position of the dialog
name|this
operator|.
name|addComponentListener
argument_list|(
operator|new
name|ComponentAdapter
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|componentResized
parameter_list|(
name|ComponentEvent
name|e
parameter_list|)
block|{
comment|// Save dialog position
name|pw
operator|.
name|storeWindowPosition
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|componentMoved
parameter_list|(
name|ComponentEvent
name|e
parameter_list|)
block|{
comment|// Save dialog position
name|pw
operator|.
name|storeWindowPosition
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|pw
operator|=
operator|new
name|PositionWindow
argument_list|(
name|this
argument_list|,
name|JabRefPreferences
operator|.
name|MERGEENTRIES_POS_X
argument_list|,
name|JabRefPreferences
operator|.
name|MERGEENTRIES_POS_Y
argument_list|,
name|JabRefPreferences
operator|.
name|MERGEENTRIES_SIZE_X
argument_list|,
name|JabRefPreferences
operator|.
name|MERGEENTRIES_SIZE_Y
argument_list|)
expr_stmt|;
name|pw
operator|.
name|setWindowPosition
argument_list|()
expr_stmt|;
comment|// Show what we've got
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

