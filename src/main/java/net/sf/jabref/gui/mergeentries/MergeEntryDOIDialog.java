begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2012-2015 JabRef contributors.  This program is free software; you can redistribute it and/or modify  it under the terms of the GNU General Public License as published by  the Free Software Foundation; either version 2 of the License, or  (at your option) any later version.   This program is distributed in the hope that it will be useful,  but WITHOUT ANY WARRANTY; without even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  GNU General Public License for more details.   You should have received a copy of the GNU General Public License along  with this program; if not, write to the Free Software Foundation, Inc.,  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
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
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JButton
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JDialog
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JOptionPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JSeparator
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
name|UndoableChangeType
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
name|UndoableFieldChange
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
operator|.
name|fetcher
operator|.
name|DOItoBibTeXFetcher
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
name|model
operator|.
name|entry
operator|.
name|BibEntry
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
name|ColumnSpec
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

begin_comment
comment|/**  * @author Oscar  *  *         Dialog for merging Bibtex entry with data fetched from DOI  */
end_comment

begin_class
DECL|class|MergeEntryDOIDialog
specifier|public
class|class
name|MergeEntryDOIDialog
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
DECL|field|originalEntry
specifier|private
name|BibEntry
name|originalEntry
decl_stmt|;
DECL|field|doiEntry
specifier|private
name|BibEntry
name|doiEntry
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
DECL|field|doiFetcher
specifier|private
specifier|final
name|DOItoBibTeXFetcher
name|doiFetcher
init|=
operator|new
name|DOItoBibTeXFetcher
argument_list|()
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
DECL|method|MergeEntryDOIDialog (BasePanel panel)
specifier|public
name|MergeEntryDOIDialog
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
literal|"Merge entry with DOI information"
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
if|if
condition|(
name|panel
operator|.
name|getSelectedEntries
argument_list|()
operator|.
name|size
argument_list|()
operator|!=
literal|1
condition|)
block|{
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
literal|"Select one entry."
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Merge entry with DOI information"
argument_list|)
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
name|this
operator|.
name|originalEntry
operator|=
name|panel
operator|.
name|getSelectedEntries
argument_list|()
operator|.
name|get
argument_list|(
literal|0
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
literal|"Fetching info based on DOI"
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|doiEntry
operator|=
name|doiFetcher
operator|.
name|getEntryFromDOI
argument_list|(
name|this
operator|.
name|originalEntry
operator|.
name|getField
argument_list|(
literal|"doi"
argument_list|)
argument_list|,
literal|null
argument_list|)
expr_stmt|;
if|if
condition|(
name|this
operator|.
name|doiEntry
operator|==
literal|null
condition|)
block|{
name|panel
operator|.
name|output
argument_list|(
literal|""
argument_list|)
expr_stmt|;
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
literal|"Cannot get info based on given DOI: %0"
argument_list|,
name|this
operator|.
name|originalEntry
operator|.
name|getField
argument_list|(
literal|"doi"
argument_list|)
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Merge entry with DOI information"
argument_list|)
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
name|panel
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Opening dialog"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Start setting up the dialog
name|init
argument_list|()
expr_stmt|;
block|}
comment|/**      * Sets up the dialog      */
DECL|method|init ()
specifier|private
name|void
name|init
parameter_list|()
block|{
name|mergeEntries
operator|=
operator|new
name|MergeEntries
argument_list|(
name|this
operator|.
name|originalEntry
argument_list|,
name|this
operator|.
name|doiEntry
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Original entry"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Entry from DOI"
argument_list|)
argument_list|,
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getMode
argument_list|()
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
literal|"Merge entry with DOI information"
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
name|e
lambda|->
name|buttonPressed
argument_list|(
name|e
operator|.
name|getActionCommand
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|JButton
name|replaceentry
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Replace original entry"
argument_list|)
argument_list|)
decl_stmt|;
name|replaceentry
operator|.
name|setActionCommand
argument_list|(
literal|"done"
argument_list|)
expr_stmt|;
name|replaceentry
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|buttonPressed
argument_list|(
name|e
operator|.
name|getActionCommand
argument_list|()
argument_list|)
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
name|replaceentry
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
name|PositionWindow
name|pw
init|=
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
decl_stmt|;
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
comment|/**      * Act on button pressed      *      * @param button Button pressed      */
DECL|method|buttonPressed (String button)
specifier|private
name|void
name|buttonPressed
parameter_list|(
name|String
name|button
parameter_list|)
block|{
name|BibEntry
name|mergedEntry
init|=
name|mergeEntries
operator|.
name|getMergeEntry
argument_list|()
decl_stmt|;
if|if
condition|(
literal|"cancel"
operator|.
name|equals
argument_list|(
name|button
argument_list|)
condition|)
block|{
comment|// Canceled, throw it away
name|panel
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Canceled merging entries"
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"done"
operator|.
name|equals
argument_list|(
name|button
argument_list|)
condition|)
block|{
comment|// Updated the original entry with the new fields
name|Set
argument_list|<
name|String
argument_list|>
name|jointFields
init|=
operator|new
name|TreeSet
argument_list|<>
argument_list|(
name|mergedEntry
operator|.
name|getFieldNames
argument_list|()
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|originalFields
init|=
operator|new
name|TreeSet
argument_list|<>
argument_list|(
name|originalEntry
operator|.
name|getFieldNames
argument_list|()
argument_list|)
decl_stmt|;
name|boolean
name|edited
init|=
literal|false
decl_stmt|;
comment|// entry type
name|String
name|oldType
init|=
name|originalEntry
operator|.
name|getType
argument_list|()
decl_stmt|;
name|String
name|newType
init|=
name|mergedEntry
operator|.
name|getType
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|oldType
operator|.
name|equalsIgnoreCase
argument_list|(
name|newType
argument_list|)
condition|)
block|{
name|originalEntry
operator|.
name|setType
argument_list|(
name|newType
argument_list|)
expr_stmt|;
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableChangeType
argument_list|(
name|originalEntry
argument_list|,
name|oldType
argument_list|,
name|newType
argument_list|)
argument_list|)
expr_stmt|;
name|edited
operator|=
literal|true
expr_stmt|;
block|}
comment|// fields
for|for
control|(
name|String
name|field
range|:
name|jointFields
control|)
block|{
name|String
name|originalString
init|=
name|originalEntry
operator|.
name|getField
argument_list|(
name|field
argument_list|)
decl_stmt|;
name|String
name|mergedString
init|=
name|mergedEntry
operator|.
name|getField
argument_list|(
name|field
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|originalString
operator|==
literal|null
operator|)
operator|||
operator|!
name|originalString
operator|.
name|equals
argument_list|(
name|mergedEntry
operator|.
name|getField
argument_list|(
name|field
argument_list|)
argument_list|)
condition|)
block|{
name|originalEntry
operator|.
name|setField
argument_list|(
name|field
argument_list|,
name|mergedString
argument_list|)
expr_stmt|;
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableFieldChange
argument_list|(
name|originalEntry
argument_list|,
name|field
argument_list|,
name|originalString
argument_list|,
name|mergedString
argument_list|)
argument_list|)
expr_stmt|;
name|edited
operator|=
literal|true
expr_stmt|;
block|}
block|}
comment|// Remove fields which are not in the merged entry
for|for
control|(
name|String
name|field
range|:
name|originalFields
control|)
block|{
if|if
condition|(
operator|!
name|jointFields
operator|.
name|contains
argument_list|(
name|field
argument_list|)
condition|)
block|{
name|String
name|originalString
init|=
name|originalEntry
operator|.
name|getField
argument_list|(
name|field
argument_list|)
decl_stmt|;
name|originalEntry
operator|.
name|clearField
argument_list|(
name|field
argument_list|)
expr_stmt|;
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableFieldChange
argument_list|(
name|originalEntry
argument_list|,
name|field
argument_list|,
name|originalString
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|edited
operator|=
literal|true
expr_stmt|;
block|}
block|}
if|if
condition|(
name|edited
condition|)
block|{
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
literal|"Updated entry with info from DOI"
argument_list|)
argument_list|)
expr_stmt|;
name|panel
operator|.
name|updateEntryEditorIfShowing
argument_list|()
expr_stmt|;
name|panel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|panel
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"No information added"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

