begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.actions
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|actions
package|;
end_package

begin_comment
comment|/**  * Global String constants for GUI actions  */
end_comment

begin_enum
DECL|enum|Actions
specifier|public
enum|enum
name|Actions
block|{
DECL|enumConstant|ABBREVIATE_ISO
name|ABBREVIATE_ISO
block|,
DECL|enumConstant|ABBREVIATE_MEDLINE
name|ABBREVIATE_MEDLINE
block|,
DECL|enumConstant|ADD_FILE_LINK
name|ADD_FILE_LINK
block|,
DECL|enumConstant|ADD_TO_GROUP
name|ADD_TO_GROUP
block|,
DECL|enumConstant|CLEANUP
name|CLEANUP
block|,
DECL|enumConstant|COPY
name|COPY
block|,
DECL|enumConstant|COPY_CITATION_ASCII_DOC
name|COPY_CITATION_ASCII_DOC
block|,
DECL|enumConstant|COPY_CITATION_XSLFO
name|COPY_CITATION_XSLFO
block|,
DECL|enumConstant|COPY_CITATION_HTML
name|COPY_CITATION_HTML
block|,
DECL|enumConstant|COPY_CITATION_RTF
name|COPY_CITATION_RTF
block|,
DECL|enumConstant|COPY_CITATION_TEXT
name|COPY_CITATION_TEXT
block|,
DECL|enumConstant|COPY_KEY
name|COPY_KEY
block|,
DECL|enumConstant|COPY_CITE_KEY
name|COPY_CITE_KEY
block|,
DECL|enumConstant|COPY_KEY_AND_TITLE
name|COPY_KEY_AND_TITLE
block|,
DECL|enumConstant|COPY_KEY_AND_LINK
name|COPY_KEY_AND_LINK
block|,
DECL|enumConstant|COPY_TITLE
name|COPY_TITLE
block|,
DECL|enumConstant|CUT
name|CUT
block|,
DECL|enumConstant|DELETE
name|DELETE
block|,
DECL|enumConstant|DOWNLOAD_FULL_TEXT
name|DOWNLOAD_FULL_TEXT
block|,
DECL|enumConstant|DUPLI_CHECK
name|DUPLI_CHECK
block|,
DECL|enumConstant|EDIT
name|EDIT
block|,
DECL|enumConstant|EDIT_PREAMBLE
name|EDIT_PREAMBLE
block|,
DECL|enumConstant|EDIT_STRINGS
name|EDIT_STRINGS
block|,
DECL|enumConstant|EXPORT_TO_CLIPBOARD
name|EXPORT_TO_CLIPBOARD
block|,
DECL|enumConstant|MAKE_KEY
name|MAKE_KEY
block|,
DECL|enumConstant|MANAGE_SELECTORS
name|MANAGE_SELECTORS
block|,
DECL|enumConstant|MERGE_DATABASE
name|MERGE_DATABASE
block|,
DECL|enumConstant|MERGE_ENTRIES
name|MERGE_ENTRIES
block|,
DECL|enumConstant|MERGE_WITH_FETCHED_ENTRY
name|MERGE_WITH_FETCHED_ENTRY
block|,
DECL|enumConstant|NEXT_PREVIEW_STYLE
name|NEXT_PREVIEW_STYLE
block|,
DECL|enumConstant|MOVE_TO_GROUP
name|MOVE_TO_GROUP
block|,
DECL|enumConstant|OPEN_CONSOLE
name|OPEN_CONSOLE
block|,
DECL|enumConstant|OPEN_EXTERNAL_FILE
name|OPEN_EXTERNAL_FILE
block|,
DECL|enumConstant|OPEN_FOLDER
name|OPEN_FOLDER
block|,
DECL|enumConstant|OPEN_URL
name|OPEN_URL
block|,
DECL|enumConstant|PASTE
name|PASTE
block|,
DECL|enumConstant|PREVIOUS_PREVIEW_STYLE
name|PREVIOUS_PREVIEW_STYLE
block|,
DECL|enumConstant|PULL_CHANGES_FROM_SHARED_DATABASE
name|PULL_CHANGES_FROM_SHARED_DATABASE
block|,
DECL|enumConstant|REDO
name|REDO
block|,
DECL|enumConstant|REMOVE_FROM_GROUP
name|REMOVE_FROM_GROUP
block|,
DECL|enumConstant|REPLACE_ALL
name|REPLACE_ALL
block|,
DECL|enumConstant|RESOLVE_DUPLICATE_KEYS
name|RESOLVE_DUPLICATE_KEYS
block|,
DECL|enumConstant|SAVE
name|SAVE
block|,
DECL|enumConstant|SAVE_AS
name|SAVE_AS
block|,
DECL|enumConstant|SAVE_SELECTED_AS_PLAIN
name|SAVE_SELECTED_AS_PLAIN
block|,
DECL|enumConstant|SEARCH
name|SEARCH
block|,
DECL|enumConstant|GLOBAL_SEARCH
name|GLOBAL_SEARCH
block|,
DECL|enumConstant|SELECT_ALL
name|SELECT_ALL
block|,
DECL|enumConstant|SEND_AS_EMAIL
name|SEND_AS_EMAIL
block|,
DECL|enumConstant|TOGGLE_GROUPS
name|TOGGLE_GROUPS
block|,
DECL|enumConstant|TOGGLE_PREVIEW
name|TOGGLE_PREVIEW
block|,
DECL|enumConstant|UNABBREVIATE
name|UNABBREVIATE
block|,
DECL|enumConstant|UNDO
name|UNDO
block|,
DECL|enumConstant|WRITE_XMP
name|WRITE_XMP
block|,
DECL|enumConstant|PRINT_PREVIEW
name|PRINT_PREVIEW
block|,
DECL|enumConstant|togglePrinted
name|togglePrinted
block|,
DECL|enumConstant|clearPriority
name|clearPriority
block|,
DECL|enumConstant|setPriority1
name|setPriority1
block|,
DECL|enumConstant|setPriority2
name|setPriority2
block|,
DECL|enumConstant|setPriority3
name|setPriority3
block|,
DECL|enumConstant|toggleQualityAssured
name|toggleQualityAssured
block|,
DECL|enumConstant|clearRank
name|clearRank
block|,
DECL|enumConstant|setRank1
name|setRank1
block|,
DECL|enumConstant|setRank2
name|setRank2
block|,
DECL|enumConstant|setRank3
name|setRank3
block|,
DECL|enumConstant|setRank4
name|setRank4
block|,
DECL|enumConstant|setRank5
name|setRank5
block|,
DECL|enumConstant|clearReadStatus
name|clearReadStatus
block|,
DECL|enumConstant|setReadStatusToRead
name|setReadStatusToRead
block|,
DECL|enumConstant|setReadStatusToSkimmed
name|setReadStatusToSkimmed
block|,
DECL|enumConstant|toggleRelevance
name|toggleRelevance
block|}
end_enum

end_unit

