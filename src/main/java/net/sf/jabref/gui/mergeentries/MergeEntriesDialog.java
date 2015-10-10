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
name|BibtexEntry
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
name|util
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
comment|/**  * @author Oscar  *   *         Dialog for merging two Bibtex entries  */
end_comment

begin_class
DECL|class|MergeEntriesDialog
specifier|public
class|class
name|MergeEntriesDialog
extends|extends
name|JDialog
block|{
DECL|field|serialVersionUID
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|5454378088546423798L
decl_stmt|;
comment|// private String [] preferedOrder = {"author", "title", "journal", "booktitle", "volume", "number", "pages", "year", "month"};
DECL|field|DIM
specifier|private
specifier|final
name|Dimension
name|DIM
init|=
operator|new
name|Dimension
argument_list|(
literal|800
argument_list|,
literal|800
argument_list|)
decl_stmt|;
DECL|field|panel
specifier|private
specifier|final
name|BasePanel
name|panel
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
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
DECL|field|one
specifier|private
name|BibtexEntry
name|one
decl_stmt|;
DECL|field|two
specifier|private
name|BibtexEntry
name|two
decl_stmt|;
DECL|field|ce
specifier|private
name|NamedCompound
name|ce
decl_stmt|;
DECL|field|mergeEntries
specifier|private
name|MergeEntries
name|mergeEntries
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"Merge entries"
argument_list|)
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
name|this
operator|.
name|frame
operator|=
name|panel
operator|.
name|frame
argument_list|()
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
name|Util
operator|.
name|placeDialog
argument_list|(
name|this
argument_list|,
name|this
operator|.
name|frame
argument_list|)
expr_stmt|;
block|}
comment|/**      * Sets up the dialog      *       * @param selected Selected BibtexEntries      */
DECL|method|init (BibtexEntry[] selected)
specifier|private
name|void
name|init
parameter_list|(
name|BibtexEntry
index|[]
name|selected
parameter_list|)
block|{
comment|// Check if there are two entries selected
if|if
condition|(
name|selected
operator|.
name|length
operator|!=
literal|2
condition|)
block|{
comment|// None selected. Inform the user to select entries first.
comment|// @formatter:off
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"You have to choose exactly two entries to merge."
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Merge entries"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|INFORMATION_MESSAGE
argument_list|)
expr_stmt|;
comment|// @formatter:on
name|this
operator|.
name|dispose
argument_list|()
expr_stmt|;
return|return;
block|}
comment|// Store the two entries
name|one
operator|=
name|selected
index|[
literal|0
index|]
expr_stmt|;
name|two
operator|=
name|selected
index|[
literal|1
index|]
expr_stmt|;
name|mergeEntries
operator|=
operator|new
name|MergeEntries
argument_list|(
name|one
argument_list|,
name|two
argument_list|)
expr_stmt|;
comment|// Create undo-compound
name|ce
operator|=
operator|new
name|NamedCompound
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Merge entries"
argument_list|)
argument_list|)
expr_stmt|;
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
comment|// layout.setColumnGroups(new int[][] {{3, 11}});
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
name|buttonPressed
argument_list|(
name|e
operator|.
name|getActionCommand
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|JButton
name|replaceentries
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Merge entries"
argument_list|)
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
name|buttonPressed
argument_list|(
name|e
operator|.
name|getActionCommand
argument_list|()
argument_list|)
expr_stmt|;
block|}
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
literal|"5px"
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
literal|"5px"
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
literal|"5px"
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
literal|"5px"
argument_list|)
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
comment|// Show what we've got
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|pack
argument_list|()
expr_stmt|;
block|}
comment|/**      * Act on button pressed      *       * @param button Button pressed      */
DECL|method|buttonPressed (String button)
specifier|private
name|void
name|buttonPressed
parameter_list|(
name|String
name|button
parameter_list|)
block|{
name|BibtexEntry
name|mergedEntry
init|=
name|mergeEntries
operator|.
name|getMergeEntry
argument_list|()
decl_stmt|;
if|if
condition|(
name|button
operator|.
name|equals
argument_list|(
literal|"cancel"
argument_list|)
condition|)
block|{
comment|// Cancelled, throw it away
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
elseif|else
if|if
condition|(
name|button
operator|.
name|equals
argument_list|(
literal|"replace"
argument_list|)
condition|)
block|{
comment|// Remove the other two entries and add them to the undo stack (which is not working...)
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
operator|.
name|getId
argument_list|()
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
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
comment|// Create a new entry and add it to the undo stack
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
block|}
block|}
end_class

end_unit

