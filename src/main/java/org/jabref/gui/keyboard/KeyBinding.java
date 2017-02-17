begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.keyboard
package|package
name|org
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
name|org
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

begin_enum
DECL|enum|KeyBinding
specifier|public
enum|enum
name|KeyBinding
block|{
DECL|enumConstant|ABBREVIATE
name|ABBREVIATE
argument_list|(
literal|"Abbreviate"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Abbreviate journal names"
argument_list|)
argument_list|,
literal|"ctrl alt A"
argument_list|,
name|KeyBindingCategory
operator|.
name|TOOLS
argument_list|)
block|,
DECL|enumConstant|AUTOGENERATE_BIBTEX_KEYS
name|AUTOGENERATE_BIBTEX_KEYS
argument_list|(
literal|"Autogenerate BibTeX keys"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Autogenerate BibTeX keys"
argument_list|)
argument_list|,
literal|"ctrl G"
argument_list|,
name|KeyBindingCategory
operator|.
name|QUALITY
argument_list|)
block|,
DECL|enumConstant|ACCEPT
name|ACCEPT
argument_list|(
literal|"Accept"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Accept"
argument_list|)
argument_list|,
literal|"ctrl ENTER"
argument_list|,
name|KeyBindingCategory
operator|.
name|EDIT
argument_list|)
block|,
DECL|enumConstant|AUTOMATICALLY_LINK_FILES
name|AUTOMATICALLY_LINK_FILES
argument_list|(
literal|"Automatically link files"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Automatically set file links"
argument_list|)
argument_list|,
literal|"F7"
argument_list|,
name|KeyBindingCategory
operator|.
name|QUALITY
argument_list|)
block|,
DECL|enumConstant|BACK
name|BACK
argument_list|(
literal|"Back"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Back"
argument_list|)
argument_list|,
literal|"alt LEFT"
argument_list|,
name|KeyBindingCategory
operator|.
name|VIEW
argument_list|)
block|,
DECL|enumConstant|CHECK_INTEGRITY
name|CHECK_INTEGRITY
argument_list|(
literal|"Check integrity"
argument_list|,
name|Localization
operator|.
name|menuTitle
argument_list|(
literal|"Check integrity"
argument_list|)
argument_list|,
literal|"ctrl F8"
argument_list|,
name|KeyBindingCategory
operator|.
name|QUALITY
argument_list|)
block|,
DECL|enumConstant|CLEANUP
name|CLEANUP
argument_list|(
literal|"Cleanup"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cleanup entries"
argument_list|)
argument_list|,
literal|"alt F8"
argument_list|,
name|KeyBindingCategory
operator|.
name|QUALITY
argument_list|)
block|,
DECL|enumConstant|CLEAR_SEARCH
name|CLEAR_SEARCH
argument_list|(
literal|"Clear search"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Clear search"
argument_list|)
argument_list|,
literal|"ESCAPE"
argument_list|,
name|KeyBindingCategory
operator|.
name|SEARCH
argument_list|)
block|,
DECL|enumConstant|CLOSE_DATABASE
name|CLOSE_DATABASE
argument_list|(
literal|"Close library"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Close library"
argument_list|)
argument_list|,
literal|"ctrl W"
argument_list|,
name|KeyBindingCategory
operator|.
name|FILE
argument_list|)
block|,
DECL|enumConstant|CLOSE_DIALOG
name|CLOSE_DIALOG
argument_list|(
literal|"Close dialog"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Close dialog"
argument_list|)
argument_list|,
literal|"ESCAPE"
argument_list|,
name|KeyBindingCategory
operator|.
name|FILE
argument_list|)
block|,
DECL|enumConstant|CLOSE_ENTRY_EDITOR
name|CLOSE_ENTRY_EDITOR
argument_list|(
literal|"Close entry editor"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Close entry editor"
argument_list|)
argument_list|,
literal|"ESCAPE"
argument_list|,
name|KeyBindingCategory
operator|.
name|VIEW
argument_list|)
block|,
DECL|enumConstant|COPY
name|COPY
argument_list|(
literal|"Copy"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Copy"
argument_list|)
argument_list|,
literal|"ctrl C"
argument_list|,
name|KeyBindingCategory
operator|.
name|EDIT
argument_list|)
block|,
DECL|enumConstant|COPY_CITE_BIBTEX_KEY
name|COPY_CITE_BIBTEX_KEY
argument_list|(
literal|"Copy \\cite{BibTeX key}"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Copy \\cite{BibTeX key}"
argument_list|)
argument_list|,
literal|"ctrl K"
argument_list|,
name|KeyBindingCategory
operator|.
name|EDIT
argument_list|)
block|,
DECL|enumConstant|COPY_BIBTEX_KEY
name|COPY_BIBTEX_KEY
argument_list|(
literal|"Copy BibTeX key"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Copy BibTeX key"
argument_list|)
argument_list|,
literal|"ctrl shift K"
argument_list|,
name|KeyBindingCategory
operator|.
name|EDIT
argument_list|)
block|,
DECL|enumConstant|COPY_BIBTEX_KEY_AND_TITLE
name|COPY_BIBTEX_KEY_AND_TITLE
argument_list|(
literal|"Copy BibTeX key and title"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Copy BibTeX key and title"
argument_list|)
argument_list|,
literal|"ctrl shift alt K"
argument_list|,
name|KeyBindingCategory
operator|.
name|EDIT
argument_list|)
block|,
DECL|enumConstant|COPY_BIBTEX_KEY_AND_LINK
name|COPY_BIBTEX_KEY_AND_LINK
argument_list|(
literal|"Copy BibTeX key and link"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Copy BibTeX key and link"
argument_list|)
argument_list|,
literal|"ctrl alt K"
argument_list|,
name|KeyBindingCategory
operator|.
name|EDIT
argument_list|)
block|,
DECL|enumConstant|COPY_PREVIEW
name|COPY_PREVIEW
argument_list|(
literal|"Copy preview"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Copy preview"
argument_list|)
argument_list|,
literal|"ctrl shift C"
argument_list|,
name|KeyBindingCategory
operator|.
name|VIEW
argument_list|)
block|,
DECL|enumConstant|CUT
name|CUT
argument_list|(
literal|"Cut"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cut"
argument_list|)
argument_list|,
literal|"ctrl X"
argument_list|,
name|KeyBindingCategory
operator|.
name|EDIT
argument_list|)
block|,
DECL|enumConstant|DECREASE_TABLE_FONT_SIZE
name|DECREASE_TABLE_FONT_SIZE
argument_list|(
literal|"Decrease table font size"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Decrease table font size"
argument_list|)
argument_list|,
literal|"ctrl MINUS"
argument_list|,
name|KeyBindingCategory
operator|.
name|VIEW
argument_list|)
block|,
DECL|enumConstant|DELETE_ENTRY
name|DELETE_ENTRY
argument_list|(
literal|"Delete entry"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Delete entry"
argument_list|)
argument_list|,
literal|"DELETE"
argument_list|,
name|KeyBindingCategory
operator|.
name|BIBTEX
argument_list|)
block|,
DECL|enumConstant|EDIT_ENTRY
name|EDIT_ENTRY
argument_list|(
literal|"Edit entry"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Edit entry"
argument_list|)
argument_list|,
literal|"ctrl E"
argument_list|,
name|KeyBindingCategory
operator|.
name|BIBTEX
argument_list|)
block|,
DECL|enumConstant|EDIT_STRINGS
name|EDIT_STRINGS
argument_list|(
literal|"Edit strings"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Edit strings"
argument_list|)
argument_list|,
literal|"ctrl T"
argument_list|,
name|KeyBindingCategory
operator|.
name|BIBTEX
argument_list|)
block|,
DECL|enumConstant|ENTRY_EDITOR_NEXT_ENTRY
name|ENTRY_EDITOR_NEXT_ENTRY
argument_list|(
literal|"Entry editor, next entry"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Entry editor, next entry"
argument_list|)
argument_list|,
literal|"ctrl shift DOWN"
argument_list|,
name|KeyBindingCategory
operator|.
name|VIEW
argument_list|)
block|,
DECL|enumConstant|ENTRY_EDITOR_NEXT_PANEL
name|ENTRY_EDITOR_NEXT_PANEL
argument_list|(
literal|"Entry editor, next panel"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Entry editor, next panel"
argument_list|)
argument_list|,
literal|"ctrl TAB"
argument_list|,
name|KeyBindingCategory
operator|.
name|VIEW
argument_list|)
block|,
DECL|enumConstant|ENTRY_EDITOR_NEXT_PANEL_2
name|ENTRY_EDITOR_NEXT_PANEL_2
argument_list|(
literal|"Entry editor, next panel 2"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Entry editor, next panel 2"
argument_list|)
argument_list|,
literal|"ctrl PLUS"
argument_list|,
name|KeyBindingCategory
operator|.
name|VIEW
argument_list|)
block|,
DECL|enumConstant|ENTRY_EDITOR_PREVIOUS_ENTRY
name|ENTRY_EDITOR_PREVIOUS_ENTRY
argument_list|(
literal|"Entry editor, previous entry"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Entry editor, previous entry"
argument_list|)
argument_list|,
literal|"ctrl shift UP"
argument_list|,
name|KeyBindingCategory
operator|.
name|VIEW
argument_list|)
block|,
DECL|enumConstant|ENTRY_EDITOR_PREVIOUS_PANEL
name|ENTRY_EDITOR_PREVIOUS_PANEL
argument_list|(
literal|"Entry editor, previous panel"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Entry editor, previous panel"
argument_list|)
argument_list|,
literal|"ctrl shift TAB"
argument_list|,
name|KeyBindingCategory
operator|.
name|VIEW
argument_list|)
block|,
DECL|enumConstant|ENTRY_EDITOR_PREVIOUS_PANEL_2
name|ENTRY_EDITOR_PREVIOUS_PANEL_2
argument_list|(
literal|"Entry editor, previous panel 2"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Entry editor, previous panel 2"
argument_list|)
argument_list|,
literal|"ctrl MINUS"
argument_list|,
name|KeyBindingCategory
operator|.
name|VIEW
argument_list|)
block|,
DECL|enumConstant|ENTRY_EDITOR_STORE_FIELD
name|ENTRY_EDITOR_STORE_FIELD
argument_list|(
literal|"Entry editor, store field"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Entry editor, store field"
argument_list|)
argument_list|,
literal|"alt S"
argument_list|,
name|KeyBindingCategory
operator|.
name|TOOLS
argument_list|)
block|,
DECL|enumConstant|FILE_LIST_EDITOR_MOVE_ENTRY_DOWN
name|FILE_LIST_EDITOR_MOVE_ENTRY_DOWN
argument_list|(
literal|"File list editor, move entry down"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"File list editor, move entry down"
argument_list|)
argument_list|,
literal|"ctrl DOWN"
argument_list|,
name|KeyBindingCategory
operator|.
name|VIEW
argument_list|)
block|,
DECL|enumConstant|FILE_LIST_EDITOR_MOVE_ENTRY_UP
name|FILE_LIST_EDITOR_MOVE_ENTRY_UP
argument_list|(
literal|"File list editor, move entry up"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"File list editor, move entry up"
argument_list|)
argument_list|,
literal|"ctrl UP"
argument_list|,
name|KeyBindingCategory
operator|.
name|VIEW
argument_list|)
block|,
DECL|enumConstant|FIND_UNLINKED_FILES
name|FIND_UNLINKED_FILES
argument_list|(
literal|"Find unlinked files"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Find unlinked files"
argument_list|)
argument_list|,
literal|"shift F7"
argument_list|,
name|KeyBindingCategory
operator|.
name|QUALITY
argument_list|)
block|,
DECL|enumConstant|FOCUS_ENTRY_TABLE
name|FOCUS_ENTRY_TABLE
argument_list|(
literal|"Focus entry table"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Focus entry table"
argument_list|)
argument_list|,
literal|"alt 1"
argument_list|,
name|KeyBindingCategory
operator|.
name|VIEW
argument_list|)
block|,
DECL|enumConstant|FORWARD
name|FORWARD
argument_list|(
literal|"Forward"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Forward"
argument_list|)
argument_list|,
literal|"alt RIGHT"
argument_list|,
name|KeyBindingCategory
operator|.
name|VIEW
argument_list|)
block|,
DECL|enumConstant|GLOBAL_SEARCH
name|GLOBAL_SEARCH
argument_list|(
literal|"Search globally"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Search globally"
argument_list|)
argument_list|,
literal|"ctrl shift F"
argument_list|,
name|KeyBindingCategory
operator|.
name|SEARCH
argument_list|)
block|,
DECL|enumConstant|HELP
name|HELP
argument_list|(
literal|"Help"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Help"
argument_list|)
argument_list|,
literal|"F1"
argument_list|,
name|KeyBindingCategory
operator|.
name|FILE
argument_list|)
block|,
DECL|enumConstant|IMPORT_INTO_CURRENT_DATABASE
name|IMPORT_INTO_CURRENT_DATABASE
argument_list|(
literal|"Import into current library"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Import into current library"
argument_list|)
argument_list|,
literal|"ctrl I"
argument_list|,
name|KeyBindingCategory
operator|.
name|FILE
argument_list|)
block|,
DECL|enumConstant|IMPORT_INTO_NEW_DATABASE
name|IMPORT_INTO_NEW_DATABASE
argument_list|(
literal|"Import into new library"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Import into new library"
argument_list|)
argument_list|,
literal|"ctrl alt I"
argument_list|,
name|KeyBindingCategory
operator|.
name|FILE
argument_list|)
block|,
DECL|enumConstant|INCREASE_TABLE_FONT_SIZE
name|INCREASE_TABLE_FONT_SIZE
argument_list|(
literal|"Increase table font size"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Increase table font size"
argument_list|)
argument_list|,
literal|"ctrl PLUS"
argument_list|,
name|KeyBindingCategory
operator|.
name|VIEW
argument_list|)
block|,
DECL|enumConstant|DEFAULT_TABLE_FONT_SIZE
name|DEFAULT_TABLE_FONT_SIZE
argument_list|(
literal|"Default table font size"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Default table font size"
argument_list|)
argument_list|,
literal|"ctrl 0"
argument_list|,
name|KeyBindingCategory
operator|.
name|VIEW
argument_list|)
block|,
DECL|enumConstant|MARK_ENTRIES
name|MARK_ENTRIES
argument_list|(
literal|"Mark entries"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Mark entries"
argument_list|)
argument_list|,
literal|"ctrl M"
argument_list|,
name|KeyBindingCategory
operator|.
name|EDIT
argument_list|)
block|,
DECL|enumConstant|NEW_ARTICLE
name|NEW_ARTICLE
argument_list|(
literal|"New article"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"New article"
argument_list|)
argument_list|,
literal|"ctrl shift A"
argument_list|,
name|KeyBindingCategory
operator|.
name|BIBTEX
argument_list|)
block|,
DECL|enumConstant|NEW_BOOK
name|NEW_BOOK
argument_list|(
literal|"New book"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"New book"
argument_list|)
argument_list|,
literal|"ctrl shift B"
argument_list|,
name|KeyBindingCategory
operator|.
name|BIBTEX
argument_list|)
block|,
DECL|enumConstant|NEW_ENTRY
name|NEW_ENTRY
argument_list|(
literal|"New entry"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"New entry"
argument_list|)
argument_list|,
literal|"ctrl N"
argument_list|,
name|KeyBindingCategory
operator|.
name|BIBTEX
argument_list|)
block|,
DECL|enumConstant|NEW_FROM_PLAIN_TEXT
name|NEW_FROM_PLAIN_TEXT
argument_list|(
literal|"New from plain text"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"New from plain text"
argument_list|)
argument_list|,
literal|"ctrl shift N"
argument_list|,
name|KeyBindingCategory
operator|.
name|BIBTEX
argument_list|)
block|,
DECL|enumConstant|NEW_INBOOK
name|NEW_INBOOK
argument_list|(
literal|"New inbook"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"New inbook"
argument_list|)
argument_list|,
literal|"ctrl shift I"
argument_list|,
name|KeyBindingCategory
operator|.
name|BIBTEX
argument_list|)
block|,
DECL|enumConstant|NEW_MASTERSTHESIS
name|NEW_MASTERSTHESIS
argument_list|(
literal|"New mastersthesis"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"New mastersthesis"
argument_list|)
argument_list|,
literal|"ctrl shift M"
argument_list|,
name|KeyBindingCategory
operator|.
name|BIBTEX
argument_list|)
block|,
DECL|enumConstant|NEW_PHDTHESIS
name|NEW_PHDTHESIS
argument_list|(
literal|"New phdthesis"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"New phdthesis"
argument_list|)
argument_list|,
literal|"ctrl shift T"
argument_list|,
name|KeyBindingCategory
operator|.
name|BIBTEX
argument_list|)
block|,
DECL|enumConstant|NEW_PROCEEDINGS
name|NEW_PROCEEDINGS
argument_list|(
literal|"New proceedings"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"New proceedings"
argument_list|)
argument_list|,
literal|"ctrl shift P"
argument_list|,
name|KeyBindingCategory
operator|.
name|BIBTEX
argument_list|)
block|,
DECL|enumConstant|NEW_UNPUBLISHED
name|NEW_UNPUBLISHED
argument_list|(
literal|"New unpublished"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"New unpublished"
argument_list|)
argument_list|,
literal|"ctrl shift U"
argument_list|,
name|KeyBindingCategory
operator|.
name|BIBTEX
argument_list|)
block|,
DECL|enumConstant|NEW_TECHREPORT
name|NEW_TECHREPORT
argument_list|(
literal|"New technical report"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"New technical report"
argument_list|)
argument_list|,
literal|"ctrl shift R"
argument_list|,
name|KeyBindingCategory
operator|.
name|BIBTEX
argument_list|)
block|,
DECL|enumConstant|NEXT_PREVIEW_LAYOUT
name|NEXT_PREVIEW_LAYOUT
argument_list|(
literal|"Next preview layout"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Next preview layout"
argument_list|)
argument_list|,
literal|"F9"
argument_list|,
name|KeyBindingCategory
operator|.
name|VIEW
argument_list|)
block|,
DECL|enumConstant|NEXT_TAB
name|NEXT_TAB
argument_list|(
literal|"Next tab"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Next tab"
argument_list|)
argument_list|,
literal|"ctrl PAGE_DOWN"
argument_list|,
name|KeyBindingCategory
operator|.
name|VIEW
argument_list|)
block|,
DECL|enumConstant|OPEN_CONSOLE
name|OPEN_CONSOLE
argument_list|(
literal|"Open terminal here"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Open terminal here"
argument_list|)
argument_list|,
literal|"ctrl shift L"
argument_list|,
name|KeyBindingCategory
operator|.
name|TOOLS
argument_list|)
block|,
DECL|enumConstant|OPEN_DATABASE
name|OPEN_DATABASE
argument_list|(
literal|"Open library"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Open library"
argument_list|)
argument_list|,
literal|"ctrl O"
argument_list|,
name|KeyBindingCategory
operator|.
name|FILE
argument_list|)
block|,
DECL|enumConstant|OPEN_FILE
name|OPEN_FILE
argument_list|(
literal|"Open file"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Open file"
argument_list|)
argument_list|,
literal|"F4"
argument_list|,
name|KeyBindingCategory
operator|.
name|TOOLS
argument_list|)
block|,
DECL|enumConstant|OPEN_FOLDER
name|OPEN_FOLDER
argument_list|(
literal|"Open folder"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Open folder"
argument_list|)
argument_list|,
literal|"ctrl shift O"
argument_list|,
name|KeyBindingCategory
operator|.
name|TOOLS
argument_list|)
block|,
DECL|enumConstant|OPEN_OPEN_OFFICE_LIBRE_OFFICE_CONNECTION
name|OPEN_OPEN_OFFICE_LIBRE_OFFICE_CONNECTION
argument_list|(
literal|"Open OpenOffice/LibreOffice connection"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Open OpenOffice/LibreOffice connection"
argument_list|)
argument_list|,
literal|"alt 0"
argument_list|,
name|KeyBindingCategory
operator|.
name|TOOLS
argument_list|)
block|,
DECL|enumConstant|OPEN_URL_OR_DOI
name|OPEN_URL_OR_DOI
argument_list|(
literal|"Open URL or DOI"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Open URL or DOI"
argument_list|)
argument_list|,
literal|"F3"
argument_list|,
name|KeyBindingCategory
operator|.
name|TOOLS
argument_list|)
block|,
DECL|enumConstant|PASTE
name|PASTE
argument_list|(
literal|"Paste"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Paste"
argument_list|)
argument_list|,
literal|"ctrl V"
argument_list|,
name|KeyBindingCategory
operator|.
name|EDIT
argument_list|)
block|,
DECL|enumConstant|PULL_CHANGES_FROM_SHARED_DATABASE
name|PULL_CHANGES_FROM_SHARED_DATABASE
argument_list|(
literal|"Pull changes from shared database"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Pull changes from shared database"
argument_list|)
argument_list|,
literal|"ctrl shift R"
argument_list|,
name|KeyBindingCategory
operator|.
name|FILE
argument_list|)
block|,
DECL|enumConstant|PREAMBLE_EDITOR_STORE_CHANGES
name|PREAMBLE_EDITOR_STORE_CHANGES
argument_list|(
literal|"Preamble editor, store changes"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Preamble editor, store changes"
argument_list|)
argument_list|,
literal|"alt S"
argument_list|,
name|KeyBindingCategory
operator|.
name|FILE
argument_list|)
block|,
DECL|enumConstant|PREVIOUS_PREVIEW_LAYOUT
name|PREVIOUS_PREVIEW_LAYOUT
argument_list|(
literal|"Previous preview layout"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Previous preview layout"
argument_list|)
argument_list|,
literal|"shift F9"
argument_list|,
name|KeyBindingCategory
operator|.
name|VIEW
argument_list|)
block|,
DECL|enumConstant|PREVIOUS_TAB
name|PREVIOUS_TAB
argument_list|(
literal|"Previous tab"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Previous tab"
argument_list|)
argument_list|,
literal|"ctrl PAGE_UP"
argument_list|,
name|KeyBindingCategory
operator|.
name|VIEW
argument_list|)
block|,
DECL|enumConstant|PUSH_TO_APPLICATION
name|PUSH_TO_APPLICATION
argument_list|(
literal|"Push to application"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Push to application"
argument_list|)
argument_list|,
literal|"ctrl L"
argument_list|,
name|KeyBindingCategory
operator|.
name|TOOLS
argument_list|)
block|,
DECL|enumConstant|QUIT_JABREF
name|QUIT_JABREF
argument_list|(
literal|"Quit JabRef"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Quit JabRef"
argument_list|)
argument_list|,
literal|"ctrl Q"
argument_list|,
name|KeyBindingCategory
operator|.
name|FILE
argument_list|)
block|,
DECL|enumConstant|REDO
name|REDO
argument_list|(
literal|"Redo"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Redo"
argument_list|)
argument_list|,
literal|"ctrl Y"
argument_list|,
name|KeyBindingCategory
operator|.
name|EDIT
argument_list|)
block|,
DECL|enumConstant|REFRESH_OO
name|REFRESH_OO
argument_list|(
literal|"Refresh OO"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Refresh OpenOffice/LibreOffice"
argument_list|)
argument_list|,
literal|"ctrl alt O"
argument_list|,
name|KeyBindingCategory
operator|.
name|TOOLS
argument_list|)
block|,
DECL|enumConstant|REPLACE_STRING
name|REPLACE_STRING
argument_list|(
literal|"Replace string"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Replace string"
argument_list|)
argument_list|,
literal|"ctrl R"
argument_list|,
name|KeyBindingCategory
operator|.
name|SEARCH
argument_list|)
block|,
DECL|enumConstant|RESOLVE_DUPLICATE_BIBTEX_KEYS
name|RESOLVE_DUPLICATE_BIBTEX_KEYS
argument_list|(
literal|"Resolve duplicate BibTeX keys"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Resolve duplicate BibTeX keys"
argument_list|)
argument_list|,
literal|"ctrl shift D"
argument_list|,
name|KeyBindingCategory
operator|.
name|BIBTEX
argument_list|)
block|,
DECL|enumConstant|SAVE_ALL
name|SAVE_ALL
argument_list|(
literal|"Save all"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save all"
argument_list|)
argument_list|,
literal|"ctrl alt S"
argument_list|,
name|KeyBindingCategory
operator|.
name|FILE
argument_list|)
block|,
DECL|enumConstant|SAVE_DATABASE
name|SAVE_DATABASE
argument_list|(
literal|"Save library"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save library"
argument_list|)
argument_list|,
literal|"ctrl S"
argument_list|,
name|KeyBindingCategory
operator|.
name|FILE
argument_list|)
block|,
DECL|enumConstant|SAVE_DATABASE_AS
name|SAVE_DATABASE_AS
argument_list|(
literal|"Save library as ..."
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save library as..."
argument_list|)
argument_list|,
literal|"ctrl shift S"
argument_list|,
name|KeyBindingCategory
operator|.
name|FILE
argument_list|)
block|,
DECL|enumConstant|SEARCH
name|SEARCH
argument_list|(
literal|"Search"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Search"
argument_list|)
argument_list|,
literal|"ctrl F"
argument_list|,
name|KeyBindingCategory
operator|.
name|SEARCH
argument_list|)
block|,
DECL|enumConstant|SELECT_ALL
name|SELECT_ALL
argument_list|(
literal|"Select all"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Select all"
argument_list|)
argument_list|,
literal|"ctrl A"
argument_list|,
name|KeyBindingCategory
operator|.
name|EDIT
argument_list|)
block|,
DECL|enumConstant|SELECT_FIRST_ENTRY
name|SELECT_FIRST_ENTRY
argument_list|(
literal|"Select first entry"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Select first entry"
argument_list|)
argument_list|,
literal|"HOME"
argument_list|,
name|KeyBindingCategory
operator|.
name|EDIT
argument_list|)
block|,
DECL|enumConstant|SELECT_LAST_ENTRY
name|SELECT_LAST_ENTRY
argument_list|(
literal|"Select last entry"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Select last entry"
argument_list|)
argument_list|,
literal|"END"
argument_list|,
name|KeyBindingCategory
operator|.
name|EDIT
argument_list|)
block|,
DECL|enumConstant|STRING_DIALOG_ADD_STRING
name|STRING_DIALOG_ADD_STRING
argument_list|(
literal|"String dialog, add string"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"String dialog, add string"
argument_list|)
argument_list|,
literal|"ctrl N"
argument_list|,
name|KeyBindingCategory
operator|.
name|FILE
argument_list|)
block|,
DECL|enumConstant|STRING_DIALOG_REMOVE_STRING
name|STRING_DIALOG_REMOVE_STRING
argument_list|(
literal|"String dialog, remove string"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"String dialog, remove string"
argument_list|)
argument_list|,
literal|"shift DELETE"
argument_list|,
name|KeyBindingCategory
operator|.
name|FILE
argument_list|)
block|,
DECL|enumConstant|SYNCHRONIZE_FILES
name|SYNCHRONIZE_FILES
argument_list|(
literal|"Synchronize files"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Synchronize files"
argument_list|)
argument_list|,
literal|"ctrl shift F7"
argument_list|,
name|KeyBindingCategory
operator|.
name|QUALITY
argument_list|)
block|,
DECL|enumConstant|TOGGLE_ENTRY_PREVIEW
name|TOGGLE_ENTRY_PREVIEW
argument_list|(
literal|"Toggle entry preview"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Toggle entry preview"
argument_list|)
argument_list|,
literal|"alt 2"
argument_list|,
name|KeyBindingCategory
operator|.
name|VIEW
argument_list|)
block|,
DECL|enumConstant|TOGGLE_GROUPS_INTERFACE
name|TOGGLE_GROUPS_INTERFACE
argument_list|(
literal|"Toggle groups interface"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Toggle groups interface"
argument_list|)
argument_list|,
literal|"alt 3"
argument_list|,
name|KeyBindingCategory
operator|.
name|VIEW
argument_list|)
block|,
DECL|enumConstant|UNABBREVIATE
name|UNABBREVIATE
argument_list|(
literal|"Unabbreviate"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Unabbreviate"
argument_list|)
argument_list|,
literal|"ctrl alt shift A"
argument_list|,
name|KeyBindingCategory
operator|.
name|TOOLS
argument_list|)
block|,
DECL|enumConstant|UNDO
name|UNDO
argument_list|(
literal|"Undo"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Undo"
argument_list|)
argument_list|,
literal|"ctrl Z"
argument_list|,
name|KeyBindingCategory
operator|.
name|EDIT
argument_list|)
block|,
DECL|enumConstant|UNMARK_ENTRIES
name|UNMARK_ENTRIES
argument_list|(
literal|"Unmark entries"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Unmark entries"
argument_list|)
argument_list|,
literal|"ctrl shift M"
argument_list|,
name|KeyBindingCategory
operator|.
name|EDIT
argument_list|)
block|,
DECL|enumConstant|WEB_SEARCH
name|WEB_SEARCH
argument_list|(
literal|"Web search"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Web search"
argument_list|)
argument_list|,
literal|"alt 4"
argument_list|,
name|KeyBindingCategory
operator|.
name|SEARCH
argument_list|)
block|,
DECL|enumConstant|WRITE_XMP
name|WRITE_XMP
argument_list|(
literal|"Write XMP"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Write XMP"
argument_list|)
argument_list|,
literal|"F6"
argument_list|,
name|KeyBindingCategory
operator|.
name|TOOLS
argument_list|)
block|;
DECL|field|key
specifier|private
specifier|final
name|String
name|key
decl_stmt|;
DECL|field|localization
specifier|private
specifier|final
name|String
name|localization
decl_stmt|;
DECL|field|defaultBinding
specifier|private
specifier|final
name|String
name|defaultBinding
decl_stmt|;
DECL|field|category
specifier|private
specifier|final
name|KeyBindingCategory
name|category
decl_stmt|;
DECL|method|KeyBinding (String key, String localization, String defaultBinding, KeyBindingCategory category)
name|KeyBinding
parameter_list|(
name|String
name|key
parameter_list|,
name|String
name|localization
parameter_list|,
name|String
name|defaultBinding
parameter_list|,
name|KeyBindingCategory
name|category
parameter_list|)
block|{
name|this
operator|.
name|key
operator|=
name|key
expr_stmt|;
name|this
operator|.
name|localization
operator|=
name|localization
expr_stmt|;
name|this
operator|.
name|defaultBinding
operator|=
name|defaultBinding
expr_stmt|;
name|this
operator|.
name|category
operator|=
name|category
expr_stmt|;
block|}
DECL|method|getKey ()
specifier|public
name|String
name|getKey
parameter_list|()
block|{
return|return
name|key
return|;
block|}
DECL|method|getLocalization ()
specifier|public
name|String
name|getLocalization
parameter_list|()
block|{
return|return
name|localization
return|;
block|}
DECL|method|getDefaultBinding ()
specifier|public
name|String
name|getDefaultBinding
parameter_list|()
block|{
return|return
name|defaultBinding
return|;
block|}
DECL|method|getCategory ()
specifier|public
name|KeyBindingCategory
name|getCategory
parameter_list|()
block|{
return|return
name|category
return|;
block|}
block|}
end_enum

end_unit
