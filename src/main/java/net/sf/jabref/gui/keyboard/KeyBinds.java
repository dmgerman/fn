begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.gui.keyboard
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|keyboard
package|;
end_package

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
name|FindUnlinkedFilesDialog
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_class
DECL|class|KeyBinds
specifier|public
class|class
name|KeyBinds
block|{
DECL|field|ABBREVIATE
specifier|public
specifier|static
specifier|final
name|String
name|ABBREVIATE
init|=
literal|"Abbreviate"
decl_stmt|;
DECL|field|AUTOGENERATE_BIB_TE_X_KEYS
specifier|public
specifier|static
specifier|final
name|String
name|AUTOGENERATE_BIB_TE_X_KEYS
init|=
literal|"Autogenerate BibTeX keys"
decl_stmt|;
DECL|field|AUTOMATICALLY_LINK_FILES
specifier|public
specifier|static
specifier|final
name|String
name|AUTOMATICALLY_LINK_FILES
init|=
literal|"Automatically link files"
decl_stmt|;
DECL|field|BACK
specifier|public
specifier|static
specifier|final
name|String
name|BACK
init|=
literal|"Back"
decl_stmt|;
DECL|field|BACK_HELP_DIALOG
specifier|public
specifier|static
specifier|final
name|String
name|BACK_HELP_DIALOG
init|=
literal|"Back, help dialog"
decl_stmt|;
DECL|field|CLEANUP
specifier|public
specifier|static
specifier|final
name|String
name|CLEANUP
init|=
literal|"Cleanup"
decl_stmt|;
DECL|field|CLEAR_SEARCH
specifier|public
specifier|static
specifier|final
name|String
name|CLEAR_SEARCH
init|=
literal|"Clear search"
decl_stmt|;
DECL|field|CLOSE_DATABASE
specifier|public
specifier|static
specifier|final
name|String
name|CLOSE_DATABASE
init|=
literal|"Close database"
decl_stmt|;
DECL|field|CLOSE_DIALOG
specifier|public
specifier|static
specifier|final
name|String
name|CLOSE_DIALOG
init|=
literal|"Close dialog"
decl_stmt|;
DECL|field|CLOSE_ENTRY_EDITOR
specifier|public
specifier|static
specifier|final
name|String
name|CLOSE_ENTRY_EDITOR
init|=
literal|"Close entry editor"
decl_stmt|;
DECL|field|COPY
specifier|public
specifier|static
specifier|final
name|String
name|COPY
init|=
literal|"Copy"
decl_stmt|;
DECL|field|COPY_BIB_TE_X_KEY
specifier|public
specifier|static
specifier|final
name|String
name|COPY_BIB_TE_X_KEY
init|=
literal|"Copy BibTeX key"
decl_stmt|;
DECL|field|COPY_BIB_TE_X_KEY_AND_TITLE
specifier|public
specifier|static
specifier|final
name|String
name|COPY_BIB_TE_X_KEY_AND_TITLE
init|=
literal|"Copy BibTeX key and title"
decl_stmt|;
DECL|field|COPY_CITE_BIB_TE_X_KEY
specifier|public
specifier|static
specifier|final
name|String
name|COPY_CITE_BIB_TE_X_KEY
init|=
literal|"Copy \\cite{BibTeX key}"
decl_stmt|;
DECL|field|CUT
specifier|public
specifier|static
specifier|final
name|String
name|CUT
init|=
literal|"Cut"
decl_stmt|;
DECL|field|DECREASE_TABLE_FONT_SIZE
specifier|public
specifier|static
specifier|final
name|String
name|DECREASE_TABLE_FONT_SIZE
init|=
literal|"Decrease table font size"
decl_stmt|;
DECL|field|DELETE
specifier|public
specifier|static
specifier|final
name|String
name|DELETE
init|=
literal|"Delete"
decl_stmt|;
DECL|field|EDIT_ENTRY
specifier|public
specifier|static
specifier|final
name|String
name|EDIT_ENTRY
init|=
literal|"Edit entry"
decl_stmt|;
DECL|field|EDIT_PREAMBLE
specifier|public
specifier|static
specifier|final
name|String
name|EDIT_PREAMBLE
init|=
literal|"Edit preamble"
decl_stmt|;
DECL|field|EDIT_STRINGS
specifier|public
specifier|static
specifier|final
name|String
name|EDIT_STRINGS
init|=
literal|"Edit strings"
decl_stmt|;
DECL|field|ENTRY_EDITOR_NEXT_ENTRY
specifier|public
specifier|static
specifier|final
name|String
name|ENTRY_EDITOR_NEXT_ENTRY
init|=
literal|"Entry editor, next entry"
decl_stmt|;
DECL|field|ENTRY_EDITOR_NEXT_PANEL
specifier|public
specifier|static
specifier|final
name|String
name|ENTRY_EDITOR_NEXT_PANEL
init|=
literal|"Entry editor, next panel"
decl_stmt|;
DECL|field|ENTRY_EDITOR_NEXT_PANEL_2
specifier|public
specifier|static
specifier|final
name|String
name|ENTRY_EDITOR_NEXT_PANEL_2
init|=
literal|"Entry editor, next panel 2"
decl_stmt|;
DECL|field|ENTRY_EDITOR_PREVIOUS_ENTRY
specifier|public
specifier|static
specifier|final
name|String
name|ENTRY_EDITOR_PREVIOUS_ENTRY
init|=
literal|"Entry editor, previous entry"
decl_stmt|;
DECL|field|ENTRY_EDITOR_PREVIOUS_PANEL
specifier|public
specifier|static
specifier|final
name|String
name|ENTRY_EDITOR_PREVIOUS_PANEL
init|=
literal|"Entry editor, previous panel"
decl_stmt|;
DECL|field|ENTRY_EDITOR_PREVIOUS_PANEL_2
specifier|public
specifier|static
specifier|final
name|String
name|ENTRY_EDITOR_PREVIOUS_PANEL_2
init|=
literal|"Entry editor, previous panel 2"
decl_stmt|;
DECL|field|ENTRY_EDITOR_STORE_FIELD
specifier|public
specifier|static
specifier|final
name|String
name|ENTRY_EDITOR_STORE_FIELD
init|=
literal|"Entry editor, store field"
decl_stmt|;
DECL|field|FILE_LIST_EDITOR_MOVE_ENTRY_DOWN
specifier|public
specifier|static
specifier|final
name|String
name|FILE_LIST_EDITOR_MOVE_ENTRY_DOWN
init|=
literal|"File list editor, move entry down"
decl_stmt|;
DECL|field|FILE_LIST_EDITOR_MOVE_ENTRY_UP
specifier|public
specifier|static
specifier|final
name|String
name|FILE_LIST_EDITOR_MOVE_ENTRY_UP
init|=
literal|"File list editor, move entry up"
decl_stmt|;
DECL|field|FOCUS_ENTRY_TABLE
specifier|public
specifier|static
specifier|final
name|String
name|FOCUS_ENTRY_TABLE
init|=
literal|"Focus entry table"
decl_stmt|;
DECL|field|FORWARD
specifier|public
specifier|static
specifier|final
name|String
name|FORWARD
init|=
literal|"Forward"
decl_stmt|;
DECL|field|FORWARD_HELP_DIALOG
specifier|public
specifier|static
specifier|final
name|String
name|FORWARD_HELP_DIALOG
init|=
literal|"Forward, help dialog"
decl_stmt|;
DECL|field|HELP
specifier|public
specifier|static
specifier|final
name|String
name|HELP
init|=
literal|"Help"
decl_stmt|;
DECL|field|HIDE_SHOW_TOOLBAR
specifier|public
specifier|static
specifier|final
name|String
name|HIDE_SHOW_TOOLBAR
init|=
literal|"Hide/show toolbar"
decl_stmt|;
DECL|field|IMPORT_INTO_CURRENT_DATABASE
specifier|public
specifier|static
specifier|final
name|String
name|IMPORT_INTO_CURRENT_DATABASE
init|=
literal|"Import into current database"
decl_stmt|;
DECL|field|IMPORT_INTO_NEW_DATABASE
specifier|public
specifier|static
specifier|final
name|String
name|IMPORT_INTO_NEW_DATABASE
init|=
literal|"Import into new database"
decl_stmt|;
DECL|field|INCREASE_TABLE_FONT_SIZE
specifier|public
specifier|static
specifier|final
name|String
name|INCREASE_TABLE_FONT_SIZE
init|=
literal|"Increase table font size"
decl_stmt|;
DECL|field|LOAD_SESSION
specifier|public
specifier|static
specifier|final
name|String
name|LOAD_SESSION
init|=
literal|"Load session"
decl_stmt|;
DECL|field|MARK_ENTRIES
specifier|public
specifier|static
specifier|final
name|String
name|MARK_ENTRIES
init|=
literal|"Mark entries"
decl_stmt|;
DECL|field|NEW_ARTICLE
specifier|public
specifier|static
specifier|final
name|String
name|NEW_ARTICLE
init|=
literal|"New article"
decl_stmt|;
DECL|field|NEW_BOOK
specifier|public
specifier|static
specifier|final
name|String
name|NEW_BOOK
init|=
literal|"New book"
decl_stmt|;
DECL|field|NEW_ENTRY
specifier|public
specifier|static
specifier|final
name|String
name|NEW_ENTRY
init|=
literal|"New entry"
decl_stmt|;
DECL|field|NEW_FILE_LINK
specifier|public
specifier|static
specifier|final
name|String
name|NEW_FILE_LINK
init|=
literal|"New file link"
decl_stmt|;
DECL|field|NEW_FROM_PLAIN_TEXT
specifier|public
specifier|static
specifier|final
name|String
name|NEW_FROM_PLAIN_TEXT
init|=
literal|"New from plain text"
decl_stmt|;
DECL|field|NEW_INBOOK
specifier|public
specifier|static
specifier|final
name|String
name|NEW_INBOOK
init|=
literal|"New inbook"
decl_stmt|;
DECL|field|NEW_MASTERSTHESIS
specifier|public
specifier|static
specifier|final
name|String
name|NEW_MASTERSTHESIS
init|=
literal|"New mastersthesis"
decl_stmt|;
DECL|field|NEW_PHDTHESIS
specifier|public
specifier|static
specifier|final
name|String
name|NEW_PHDTHESIS
init|=
literal|"New phdthesis"
decl_stmt|;
DECL|field|NEW_PROCEEDINGS
specifier|public
specifier|static
specifier|final
name|String
name|NEW_PROCEEDINGS
init|=
literal|"New proceedings"
decl_stmt|;
DECL|field|NEW_UNPUBLISHED
specifier|public
specifier|static
specifier|final
name|String
name|NEW_UNPUBLISHED
init|=
literal|"New unpublished"
decl_stmt|;
DECL|field|NEXT_TAB
specifier|public
specifier|static
specifier|final
name|String
name|NEXT_TAB
init|=
literal|"Next tab"
decl_stmt|;
DECL|field|OPEN_DATABASE
specifier|public
specifier|static
specifier|final
name|String
name|OPEN_DATABASE
init|=
literal|"Open database"
decl_stmt|;
DECL|field|OPEN_FILE
specifier|public
specifier|static
specifier|final
name|String
name|OPEN_FILE
init|=
literal|"Open file"
decl_stmt|;
DECL|field|OPEN_FOLDER
specifier|public
specifier|static
specifier|final
name|String
name|OPEN_FOLDER
init|=
literal|"Open folder"
decl_stmt|;
DECL|field|OPEN_PDF_OR_PS
specifier|public
specifier|static
specifier|final
name|String
name|OPEN_PDF_OR_PS
init|=
literal|"Open PDF or PS"
decl_stmt|;
DECL|field|OPEN_SPIRES_ENTRY
specifier|public
specifier|static
specifier|final
name|String
name|OPEN_SPIRES_ENTRY
init|=
literal|"Open SPIRES entry"
decl_stmt|;
DECL|field|OPEN_URL_OR_DOI
specifier|public
specifier|static
specifier|final
name|String
name|OPEN_URL_OR_DOI
init|=
literal|"Open URL or DOI"
decl_stmt|;
DECL|field|PASTE
specifier|public
specifier|static
specifier|final
name|String
name|PASTE
init|=
literal|"Paste"
decl_stmt|;
DECL|field|PREAMBLE_EDITOR_STORE_CHANGES
specifier|public
specifier|static
specifier|final
name|String
name|PREAMBLE_EDITOR_STORE_CHANGES
init|=
literal|"Preamble editor, store changes"
decl_stmt|;
DECL|field|PREVIOUS_TAB
specifier|public
specifier|static
specifier|final
name|String
name|PREVIOUS_TAB
init|=
literal|"Previous tab"
decl_stmt|;
DECL|field|PRINT_ENTRY_PREVIEW
specifier|public
specifier|static
specifier|final
name|String
name|PRINT_ENTRY_PREVIEW
init|=
literal|"Print entry preview"
decl_stmt|;
DECL|field|PUSH_TO_APPLICATION
specifier|public
specifier|static
specifier|final
name|String
name|PUSH_TO_APPLICATION
init|=
literal|"Push to application"
decl_stmt|;
DECL|field|QUIT_JAB_REF
specifier|public
specifier|static
specifier|final
name|String
name|QUIT_JAB_REF
init|=
literal|"Quit JabRef"
decl_stmt|;
DECL|field|REDO
specifier|public
specifier|static
specifier|final
name|String
name|REDO
init|=
literal|"Redo"
decl_stmt|;
DECL|field|REFRESH_OO
specifier|public
specifier|static
specifier|final
name|String
name|REFRESH_OO
init|=
literal|"Refresh OO"
decl_stmt|;
DECL|field|REPLACE_STRING
specifier|public
specifier|static
specifier|final
name|String
name|REPLACE_STRING
init|=
literal|"Replace string"
decl_stmt|;
DECL|field|RESOLVE_DUPLICATE_BIB_TE_X_KEYS
specifier|public
specifier|static
specifier|final
name|String
name|RESOLVE_DUPLICATE_BIB_TE_X_KEYS
init|=
literal|"Resolve duplicate BibTeX keys"
decl_stmt|;
DECL|field|SAVE_ALL
specifier|public
specifier|static
specifier|final
name|String
name|SAVE_ALL
init|=
literal|"Save all"
decl_stmt|;
DECL|field|SAVE_DATABASE
specifier|public
specifier|static
specifier|final
name|String
name|SAVE_DATABASE
init|=
literal|"Save database"
decl_stmt|;
DECL|field|SAVE_DATABASE_AS
specifier|public
specifier|static
specifier|final
name|String
name|SAVE_DATABASE_AS
init|=
literal|"Save database as ..."
decl_stmt|;
DECL|field|SAVE_SESSION
specifier|public
specifier|static
specifier|final
name|String
name|SAVE_SESSION
init|=
literal|"Save session"
decl_stmt|;
DECL|field|SEARCH
specifier|public
specifier|static
specifier|final
name|String
name|SEARCH
init|=
literal|"Search"
decl_stmt|;
DECL|field|SELECT_ALL
specifier|public
specifier|static
specifier|final
name|String
name|SELECT_ALL
init|=
literal|"Select all"
decl_stmt|;
DECL|field|STRING_DIALOG_ADD_STRING
specifier|public
specifier|static
specifier|final
name|String
name|STRING_DIALOG_ADD_STRING
init|=
literal|"String dialog, add string"
decl_stmt|;
DECL|field|STRING_DIALOG_REMOVE_STRING
specifier|public
specifier|static
specifier|final
name|String
name|STRING_DIALOG_REMOVE_STRING
init|=
literal|"String dialog, remove string"
decl_stmt|;
DECL|field|SWITCH_PREVIEW_LAYOUT
specifier|public
specifier|static
specifier|final
name|String
name|SWITCH_PREVIEW_LAYOUT
init|=
literal|"Switch preview layout"
decl_stmt|;
DECL|field|SYNCHRONIZE_FILES
specifier|public
specifier|static
specifier|final
name|String
name|SYNCHRONIZE_FILES
init|=
literal|"Synchronize files"
decl_stmt|;
DECL|field|TOGGLE_ENTRY_PREVIEW
specifier|public
specifier|static
specifier|final
name|String
name|TOGGLE_ENTRY_PREVIEW
init|=
literal|"Toggle entry preview"
decl_stmt|;
DECL|field|TOGGLE_GROUPS_INTERFACE
specifier|public
specifier|static
specifier|final
name|String
name|TOGGLE_GROUPS_INTERFACE
init|=
literal|"Toggle groups interface"
decl_stmt|;
DECL|field|UNABBREVIATE
specifier|public
specifier|static
specifier|final
name|String
name|UNABBREVIATE
init|=
literal|"Unabbreviate"
decl_stmt|;
DECL|field|UNDO
specifier|public
specifier|static
specifier|final
name|String
name|UNDO
init|=
literal|"Undo"
decl_stmt|;
DECL|field|UNMARK_ENTRIES
specifier|public
specifier|static
specifier|final
name|String
name|UNMARK_ENTRIES
init|=
literal|"Unmark entries"
decl_stmt|;
DECL|field|WEB_SEARCH
specifier|public
specifier|static
specifier|final
name|String
name|WEB_SEARCH
init|=
literal|"Web search"
decl_stmt|;
DECL|field|WRITE_XMP
specifier|public
specifier|static
specifier|final
name|String
name|WRITE_XMP
init|=
literal|"Write XMP"
decl_stmt|;
DECL|field|keyBindMap
specifier|private
specifier|final
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|keyBindMap
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
DECL|method|KeyBinds ()
specifier|public
name|KeyBinds
parameter_list|()
block|{
name|keyBindMap
operator|.
name|put
argument_list|(
name|PUSH_TO_APPLICATION
argument_list|,
literal|"ctrl L"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|QUIT_JAB_REF
argument_list|,
literal|"ctrl Q"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|OPEN_DATABASE
argument_list|,
literal|"ctrl O"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|SAVE_DATABASE
argument_list|,
literal|"ctrl S"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|SAVE_DATABASE_AS
argument_list|,
literal|"ctrl shift S"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|SAVE_ALL
argument_list|,
literal|"ctrl alt S"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|CLOSE_DATABASE
argument_list|,
literal|"ctrl W"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|NEW_ENTRY
argument_list|,
literal|"ctrl N"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|CUT
argument_list|,
literal|"ctrl X"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|COPY
argument_list|,
literal|"ctrl C"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|PASTE
argument_list|,
literal|"ctrl V"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|UNDO
argument_list|,
literal|"ctrl Z"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|REDO
argument_list|,
literal|"ctrl Y"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|HELP
argument_list|,
literal|"F1"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|NEW_ARTICLE
argument_list|,
literal|"ctrl shift A"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|NEW_BOOK
argument_list|,
literal|"ctrl shift B"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|NEW_PHDTHESIS
argument_list|,
literal|"ctrl shift T"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|NEW_INBOOK
argument_list|,
literal|"ctrl shift I"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|NEW_MASTERSTHESIS
argument_list|,
literal|"ctrl shift M"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|NEW_PROCEEDINGS
argument_list|,
literal|"ctrl shift P"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|NEW_UNPUBLISHED
argument_list|,
literal|"ctrl shift U"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|EDIT_STRINGS
argument_list|,
literal|"ctrl T"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|EDIT_PREAMBLE
argument_list|,
literal|"ctrl P"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|SELECT_ALL
argument_list|,
literal|"ctrl A"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|TOGGLE_GROUPS_INTERFACE
argument_list|,
literal|"ctrl shift G"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|AUTOGENERATE_BIB_TE_X_KEYS
argument_list|,
literal|"ctrl G"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|SEARCH
argument_list|,
literal|"ctrl F"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|CLOSE_DIALOG
argument_list|,
literal|"ESCAPE"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|BACK_HELP_DIALOG
argument_list|,
literal|"LEFT"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|FORWARD_HELP_DIALOG
argument_list|,
literal|"RIGHT"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|PREAMBLE_EDITOR_STORE_CHANGES
argument_list|,
literal|"alt S"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|CLEAR_SEARCH
argument_list|,
literal|"ESCAPE"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|CLOSE_ENTRY_EDITOR
argument_list|,
literal|"ESCAPE"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|ENTRY_EDITOR_NEXT_PANEL
argument_list|,
literal|"ctrl TAB"
argument_list|)
expr_stmt|;
comment|//"ctrl PLUS");//"shift Right");
name|keyBindMap
operator|.
name|put
argument_list|(
name|ENTRY_EDITOR_PREVIOUS_PANEL
argument_list|,
literal|"ctrl shift TAB"
argument_list|)
expr_stmt|;
comment|//"ctrl MINUS");
name|keyBindMap
operator|.
name|put
argument_list|(
name|ENTRY_EDITOR_NEXT_PANEL_2
argument_list|,
literal|"ctrl PLUS"
argument_list|)
expr_stmt|;
comment|//"ctrl PLUS");//"shift Right");
name|keyBindMap
operator|.
name|put
argument_list|(
name|ENTRY_EDITOR_PREVIOUS_PANEL_2
argument_list|,
literal|"ctrl MINUS"
argument_list|)
expr_stmt|;
comment|//"ctrl MINUS");
name|keyBindMap
operator|.
name|put
argument_list|(
name|ENTRY_EDITOR_NEXT_ENTRY
argument_list|,
literal|"ctrl shift DOWN"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|ENTRY_EDITOR_PREVIOUS_ENTRY
argument_list|,
literal|"ctrl shift UP"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|ENTRY_EDITOR_STORE_FIELD
argument_list|,
literal|"alt S"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|STRING_DIALOG_ADD_STRING
argument_list|,
literal|"ctrl N"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|STRING_DIALOG_REMOVE_STRING
argument_list|,
literal|"shift DELETE"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|SAVE_SESSION
argument_list|,
literal|"F11"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|LOAD_SESSION
argument_list|,
literal|"F12"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|COPY_CITE_BIB_TE_X_KEY
argument_list|,
literal|"ctrl K"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|COPY_BIB_TE_X_KEY
argument_list|,
literal|"ctrl shift K"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|COPY_BIB_TE_X_KEY_AND_TITLE
argument_list|,
literal|"ctrl shift alt K"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|NEXT_TAB
argument_list|,
literal|"ctrl PAGE_DOWN"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|PREVIOUS_TAB
argument_list|,
literal|"ctrl PAGE_UP"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|REPLACE_STRING
argument_list|,
literal|"ctrl R"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|DELETE
argument_list|,
literal|"DELETE"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|OPEN_FILE
argument_list|,
literal|"F4"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|OPEN_FOLDER
argument_list|,
literal|"ctrl shift O"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|OPEN_PDF_OR_PS
argument_list|,
literal|"shift F5"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|OPEN_URL_OR_DOI
argument_list|,
literal|"F3"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|OPEN_SPIRES_ENTRY
argument_list|,
literal|"ctrl F3"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|TOGGLE_ENTRY_PREVIEW
argument_list|,
literal|"ctrl F9"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|SWITCH_PREVIEW_LAYOUT
argument_list|,
literal|"F9"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|EDIT_ENTRY
argument_list|,
literal|"ctrl E"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|MARK_ENTRIES
argument_list|,
literal|"ctrl M"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|UNMARK_ENTRIES
argument_list|,
literal|"ctrl shift M"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|WEB_SEARCH
argument_list|,
literal|"F5"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|NEW_FROM_PLAIN_TEXT
argument_list|,
literal|"ctrl shift N"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|SYNCHRONIZE_FILES
argument_list|,
literal|"ctrl F4"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|FOCUS_ENTRY_TABLE
argument_list|,
literal|"ctrl shift E"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|ABBREVIATE
argument_list|,
literal|"ctrl alt A"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|UNABBREVIATE
argument_list|,
literal|"ctrl alt shift A"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|CLEANUP
argument_list|,
literal|"ctrl shift F7"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|WRITE_XMP
argument_list|,
literal|"ctrl F7"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|NEW_FILE_LINK
argument_list|,
literal|"ctrl N"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|BACK
argument_list|,
literal|"alt LEFT"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|FORWARD
argument_list|,
literal|"alt RIGHT"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|IMPORT_INTO_CURRENT_DATABASE
argument_list|,
literal|"ctrl I"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|IMPORT_INTO_NEW_DATABASE
argument_list|,
literal|"ctrl alt I"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|FindUnlinkedFilesDialog
operator|.
name|ACTION_KEYBINDING_ACTION
argument_list|,
literal|"shift F7"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|INCREASE_TABLE_FONT_SIZE
argument_list|,
literal|"ctrl PLUS"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|DECREASE_TABLE_FONT_SIZE
argument_list|,
literal|"ctrl MINUS"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|AUTOMATICALLY_LINK_FILES
argument_list|,
literal|"alt F"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|RESOLVE_DUPLICATE_BIB_TE_X_KEYS
argument_list|,
literal|"ctrl shift D"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|REFRESH_OO
argument_list|,
literal|"ctrl alt O"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|FILE_LIST_EDITOR_MOVE_ENTRY_UP
argument_list|,
literal|"ctrl UP"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|FILE_LIST_EDITOR_MOVE_ENTRY_DOWN
argument_list|,
literal|"ctrl DOWN"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|HIDE_SHOW_TOOLBAR
argument_list|,
literal|"ctrl alt T"
argument_list|)
expr_stmt|;
name|keyBindMap
operator|.
name|put
argument_list|(
name|PRINT_ENTRY_PREVIEW
argument_list|,
literal|"alt P"
argument_list|)
expr_stmt|;
block|}
DECL|method|get (String key)
specifier|public
name|String
name|get
parameter_list|(
name|String
name|key
parameter_list|)
block|{
return|return
name|keyBindMap
operator|.
name|get
argument_list|(
name|key
argument_list|)
return|;
block|}
DECL|method|getKeyBindings ()
specifier|public
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|getKeyBindings
parameter_list|()
block|{
return|return
operator|new
name|HashMap
argument_list|<>
argument_list|(
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|keyBindMap
argument_list|)
argument_list|)
return|;
block|}
DECL|method|overwriteBindings (Map<String, String> newBindings)
specifier|public
name|void
name|overwriteBindings
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|newBindings
parameter_list|)
block|{
name|keyBindMap
operator|.
name|clear
argument_list|()
expr_stmt|;
name|keyBindMap
operator|.
name|putAll
argument_list|(
name|newBindings
argument_list|)
expr_stmt|;
block|}
DECL|method|put (String key, String value)
specifier|public
name|void
name|put
parameter_list|(
name|String
name|key
parameter_list|,
name|String
name|value
parameter_list|)
block|{
name|keyBindMap
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

