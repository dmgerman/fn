begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.gui
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
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
name|mergeentries
operator|.
name|MergeEntries
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
name|logic
operator|.
name|l10n
operator|.
name|Localization
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
DECL|field|NOT_CHOSEN
specifier|private
specifier|static
specifier|final
name|int
name|NOT_CHOSEN
init|=
operator|-
literal|1
decl_stmt|;
DECL|field|KEEP_BOTH
specifier|public
specifier|static
specifier|final
name|int
name|KEEP_BOTH
init|=
literal|0
decl_stmt|;
DECL|field|KEEP_UPPER
specifier|public
specifier|static
specifier|final
name|int
name|KEEP_UPPER
init|=
literal|1
decl_stmt|;
DECL|field|KEEP_LOWER
specifier|public
specifier|static
specifier|final
name|int
name|KEEP_LOWER
init|=
literal|2
decl_stmt|;
DECL|field|AUTOREMOVE_EXACT
specifier|public
specifier|static
specifier|final
name|int
name|AUTOREMOVE_EXACT
init|=
literal|3
decl_stmt|;
DECL|field|KEEP_MERGE
specifier|public
specifier|static
specifier|final
name|int
name|KEEP_MERGE
init|=
literal|4
decl_stmt|;
DECL|field|BREAK
specifier|public
specifier|static
specifier|final
name|int
name|BREAK
init|=
literal|5
decl_stmt|;
comment|// close
DECL|field|IMPORT_AND_DELETE_OLD
specifier|public
specifier|static
specifier|final
name|int
name|IMPORT_AND_DELETE_OLD
init|=
literal|1
decl_stmt|;
DECL|field|IMPORT_AND_KEEP_OLD
specifier|public
specifier|static
specifier|final
name|int
name|IMPORT_AND_KEEP_OLD
init|=
literal|0
decl_stmt|;
DECL|field|DO_NOT_IMPORT
specifier|public
specifier|static
specifier|final
name|int
name|DO_NOT_IMPORT
init|=
literal|2
decl_stmt|;
DECL|field|DUPLICATE_SEARCH
specifier|public
specifier|static
specifier|final
name|int
name|DUPLICATE_SEARCH
init|=
literal|1
decl_stmt|;
DECL|field|IMPORT_CHECK
specifier|public
specifier|static
specifier|final
name|int
name|IMPORT_CHECK
init|=
literal|2
decl_stmt|;
DECL|field|INSPECTION
specifier|public
specifier|static
specifier|final
name|int
name|INSPECTION
init|=
literal|3
decl_stmt|;
DECL|field|DUPLICATE_SEARCH_WITH_EXACT
specifier|public
specifier|static
specifier|final
name|int
name|DUPLICATE_SEARCH_WITH_EXACT
init|=
literal|4
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
DECL|field|merge
specifier|private
specifier|final
name|JButton
name|merge
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Keep merged entry only"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|removeExact
specifier|private
name|JButton
name|removeExact
decl_stmt|;
DECL|field|options
specifier|private
specifier|final
name|JPanel
name|options
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|field|status
specifier|private
name|int
name|status
init|=
name|DuplicateResolverDialog
operator|.
name|NOT_CHOSEN
decl_stmt|;
DECL|field|me
specifier|private
name|MergeEntries
name|me
decl_stmt|;
DECL|field|pw
specifier|private
name|PositionWindow
name|pw
decl_stmt|;
DECL|method|DuplicateResolverDialog (JabRefFrame frame, BibEntry one, BibEntry two, int type)
specifier|public
name|DuplicateResolverDialog
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|BibEntry
name|one
parameter_list|,
name|BibEntry
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"Possible duplicate entries"
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
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
block|}
DECL|method|DuplicateResolverDialog (ImportInspectionDialog frame, BibEntry one, BibEntry two, int type)
specifier|public
name|DuplicateResolverDialog
parameter_list|(
name|ImportInspectionDialog
name|frame
parameter_list|,
name|BibEntry
name|one
parameter_list|,
name|BibEntry
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"Possible duplicate entries"
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
operator|.
name|frame
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
block|}
DECL|method|init (BibEntry one, BibEntry two, int type)
specifier|private
name|void
name|init
parameter_list|(
name|BibEntry
name|one
parameter_list|,
name|BibEntry
name|two
parameter_list|,
name|int
name|type
parameter_list|)
block|{
name|JButton
name|both
decl_stmt|;
name|JButton
name|second
decl_stmt|;
name|JButton
name|first
decl_stmt|;
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"Keep left"
argument_list|)
argument_list|)
expr_stmt|;
name|second
operator|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Keep right"
argument_list|)
argument_list|)
expr_stmt|;
name|both
operator|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Keep both"
argument_list|)
argument_list|)
expr_stmt|;
name|me
operator|=
operator|new
name|MergeEntries
argument_list|(
name|one
argument_list|,
name|two
argument_list|,
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|getLoadedDatabase
argument_list|()
operator|.
name|getType
argument_list|()
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
name|Localization
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
name|Localization
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"Keep both"
argument_list|)
argument_list|)
expr_stmt|;
name|me
operator|=
operator|new
name|MergeEntries
argument_list|(
name|one
argument_list|,
name|two
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Old entry"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"From import"
argument_list|)
argument_list|,
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|getLoadedDatabase
argument_list|()
operator|.
name|getType
argument_list|()
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"Keep left"
argument_list|)
argument_list|)
expr_stmt|;
name|second
operator|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Keep right"
argument_list|)
argument_list|)
expr_stmt|;
name|both
operator|=
operator|new
name|JButton
argument_list|(
name|Localization
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"Automatically remove exact duplicates"
argument_list|)
argument_list|)
expr_stmt|;
name|me
operator|=
operator|new
name|MergeEntries
argument_list|(
name|one
argument_list|,
name|two
argument_list|,
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|getLoadedDatabase
argument_list|()
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
break|break;
default|default:
name|first
operator|=
operator|new
name|JButton
argument_list|(
name|Localization
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
name|Localization
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"Import and keep old entry"
argument_list|)
argument_list|)
expr_stmt|;
name|me
operator|=
operator|new
name|MergeEntries
argument_list|(
name|one
argument_list|,
name|two
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Old entry"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"From import"
argument_list|)
argument_list|,
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|getLoadedDatabase
argument_list|()
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|removeExact
operator|!=
literal|null
condition|)
block|{
name|options
operator|.
name|add
argument_list|(
name|removeExact
argument_list|)
expr_stmt|;
block|}
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
name|options
operator|.
name|add
argument_list|(
name|merge
argument_list|)
expr_stmt|;
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
name|first
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|buttonPressed
argument_list|(
name|KEEP_UPPER
argument_list|)
argument_list|)
expr_stmt|;
name|second
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|buttonPressed
argument_list|(
name|KEEP_LOWER
argument_list|)
argument_list|)
expr_stmt|;
name|both
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|buttonPressed
argument_list|(
name|KEEP_BOTH
argument_list|)
argument_list|)
expr_stmt|;
name|merge
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|buttonPressed
argument_list|(
name|KEEP_MERGE
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|removeExact
operator|!=
literal|null
condition|)
block|{
name|removeExact
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|buttonPressed
argument_list|(
name|AUTOREMOVE_EXACT
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|cancel
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|buttonPressed
argument_list|(
name|BREAK
argument_list|)
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|me
operator|.
name|getMergeEntryPanel
argument_list|()
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
name|pw
operator|=
operator|new
name|PositionWindow
argument_list|(
name|this
argument_list|,
name|JabRefPreferences
operator|.
name|DUPLICATES_POS_X
argument_list|,
name|JabRefPreferences
operator|.
name|DUPLICATES_POS_Y
argument_list|,
name|JabRefPreferences
operator|.
name|DUPLICATES_SIZE_X
argument_list|,
name|JabRefPreferences
operator|.
name|DUPLICATES_SIZE_Y
argument_list|)
expr_stmt|;
name|pw
operator|.
name|setWindowPosition
argument_list|()
expr_stmt|;
comment|// Set up a ComponentListener that saves the last size and position of the dialog
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
name|both
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
block|}
DECL|method|buttonPressed (int button)
specifier|private
name|void
name|buttonPressed
parameter_list|(
name|int
name|button
parameter_list|)
block|{
name|status
operator|=
name|button
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
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
DECL|method|getMergedEntry ()
specifier|public
name|BibEntry
name|getMergedEntry
parameter_list|()
block|{
return|return
name|me
operator|.
name|getMergeEntry
argument_list|()
return|;
block|}
block|}
end_class

end_unit

