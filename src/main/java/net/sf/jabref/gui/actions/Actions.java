begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.actions
package|package
name|net
operator|.
name|sf
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

begin_class
DECL|class|Actions
specifier|public
class|class
name|Actions
block|{
DECL|field|ABBREVIATE_ISO
specifier|public
specifier|static
specifier|final
name|String
name|ABBREVIATE_ISO
init|=
literal|"abbreviateIso"
decl_stmt|;
DECL|field|ABBREVIATE_MEDLINE
specifier|public
specifier|static
specifier|final
name|String
name|ABBREVIATE_MEDLINE
init|=
literal|"abbreviateMedline"
decl_stmt|;
DECL|field|ADD_FILE_LINK
specifier|public
specifier|static
specifier|final
name|String
name|ADD_FILE_LINK
init|=
literal|"addFileLink"
decl_stmt|;
DECL|field|ADD_TO_GROUP
specifier|public
specifier|static
specifier|final
name|String
name|ADD_TO_GROUP
init|=
literal|"addToGroup"
decl_stmt|;
DECL|field|AUTO_SET_FILE
specifier|public
specifier|static
specifier|final
name|String
name|AUTO_SET_FILE
init|=
literal|"autoSetFile"
decl_stmt|;
DECL|field|BACK
specifier|public
specifier|static
specifier|final
name|String
name|BACK
init|=
literal|"back"
decl_stmt|;
DECL|field|CHECK_INTEGRITY
specifier|public
specifier|static
specifier|final
name|String
name|CHECK_INTEGRITY
init|=
literal|"checkIntegrity"
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
DECL|field|COPY
specifier|public
specifier|static
specifier|final
name|String
name|COPY
init|=
literal|"copy"
decl_stmt|;
DECL|field|COPY_KEY
specifier|public
specifier|static
specifier|final
name|String
name|COPY_KEY
init|=
literal|"copyKey"
decl_stmt|;
DECL|field|COPY_CITE_KEY
specifier|public
specifier|static
specifier|final
name|String
name|COPY_CITE_KEY
init|=
literal|"copyCiteKey"
decl_stmt|;
DECL|field|COPY_KEY_AND_TITLE
specifier|public
specifier|static
specifier|final
name|String
name|COPY_KEY_AND_TITLE
init|=
literal|"copyKeyAndTitle"
decl_stmt|;
DECL|field|CUT
specifier|public
specifier|static
specifier|final
name|String
name|CUT
init|=
literal|"cut"
decl_stmt|;
DECL|field|DB_CONNECT
specifier|public
specifier|static
specifier|final
name|String
name|DB_CONNECT
init|=
literal|"dbConnect"
decl_stmt|;
DECL|field|DB_EXPORT
specifier|public
specifier|static
specifier|final
name|String
name|DB_EXPORT
init|=
literal|"dbExport"
decl_stmt|;
DECL|field|DELETE
specifier|public
specifier|static
specifier|final
name|String
name|DELETE
init|=
literal|"delete"
decl_stmt|;
DECL|field|DOWNLOAD_FULL_TEXT
specifier|public
specifier|static
specifier|final
name|String
name|DOWNLOAD_FULL_TEXT
init|=
literal|"downloadFullText"
decl_stmt|;
DECL|field|DUPLI_CHECK
specifier|public
specifier|static
specifier|final
name|String
name|DUPLI_CHECK
init|=
literal|"dupliCheck"
decl_stmt|;
DECL|field|EDIT
specifier|public
specifier|static
specifier|final
name|String
name|EDIT
init|=
literal|"edit"
decl_stmt|;
DECL|field|EDIT_PREAMBLE
specifier|public
specifier|static
specifier|final
name|String
name|EDIT_PREAMBLE
init|=
literal|"editPreamble"
decl_stmt|;
DECL|field|EDIT_STRINGS
specifier|public
specifier|static
specifier|final
name|String
name|EDIT_STRINGS
init|=
literal|"editStrings"
decl_stmt|;
DECL|field|EXPORT_TO_CLIPBOARD
specifier|public
specifier|static
specifier|final
name|String
name|EXPORT_TO_CLIPBOARD
init|=
literal|"exportToClipboard"
decl_stmt|;
DECL|field|FOCUS_TABLE
specifier|public
specifier|static
specifier|final
name|String
name|FOCUS_TABLE
init|=
literal|"focusTable"
decl_stmt|;
DECL|field|FORWARD
specifier|public
specifier|static
specifier|final
name|String
name|FORWARD
init|=
literal|"forward"
decl_stmt|;
DECL|field|MAKE_KEY
specifier|public
specifier|static
specifier|final
name|String
name|MAKE_KEY
init|=
literal|"makeKey"
decl_stmt|;
DECL|field|MANAGE_SELECTORS
specifier|public
specifier|static
specifier|final
name|String
name|MANAGE_SELECTORS
init|=
literal|"manageSelectors"
decl_stmt|;
DECL|field|MARK_ENTRIES
specifier|public
specifier|static
specifier|final
name|String
name|MARK_ENTRIES
init|=
literal|"markEntries"
decl_stmt|;
DECL|field|MERGE_DATABASE
specifier|public
specifier|static
specifier|final
name|String
name|MERGE_DATABASE
init|=
literal|"mergeDatabase"
decl_stmt|;
DECL|field|MERGE_ENTRIES
specifier|public
specifier|static
specifier|final
name|String
name|MERGE_ENTRIES
init|=
literal|"mergeEntries"
decl_stmt|;
DECL|field|MERGE_DOI
specifier|public
specifier|static
specifier|final
name|String
name|MERGE_DOI
init|=
literal|"mergeWithDOI"
decl_stmt|;
DECL|field|MOVE_TO_GROUP
specifier|public
specifier|static
specifier|final
name|String
name|MOVE_TO_GROUP
init|=
literal|"moveToGroup"
decl_stmt|;
DECL|field|OPEN_EXTERNAL_FILE
specifier|public
specifier|static
specifier|final
name|String
name|OPEN_EXTERNAL_FILE
init|=
literal|"openExternalFile"
decl_stmt|;
DECL|field|OPEN_FOLDER
specifier|public
specifier|static
specifier|final
name|String
name|OPEN_FOLDER
init|=
literal|"openFolder"
decl_stmt|;
DECL|field|OPEN_URL
specifier|public
specifier|static
specifier|final
name|String
name|OPEN_URL
init|=
literal|"openUrl"
decl_stmt|;
DECL|field|PASTE
specifier|public
specifier|static
specifier|final
name|String
name|PASTE
init|=
literal|"paste"
decl_stmt|;
DECL|field|PLAIN_TEXT_IMPORT
specifier|public
specifier|static
specifier|final
name|String
name|PLAIN_TEXT_IMPORT
init|=
literal|"plainTextImport"
decl_stmt|;
DECL|field|REDO
specifier|public
specifier|static
specifier|final
name|String
name|REDO
init|=
literal|"redo"
decl_stmt|;
DECL|field|REMOVE_FROM_GROUP
specifier|public
specifier|static
specifier|final
name|String
name|REMOVE_FROM_GROUP
init|=
literal|"removeFromGroup"
decl_stmt|;
DECL|field|REPLACE_ALL
specifier|public
specifier|static
specifier|final
name|String
name|REPLACE_ALL
init|=
literal|"replaceAll"
decl_stmt|;
DECL|field|RESOLVE_DUPLICATE_KEYS
specifier|public
specifier|static
specifier|final
name|String
name|RESOLVE_DUPLICATE_KEYS
init|=
literal|"resolveDuplicateKeys"
decl_stmt|;
DECL|field|SAVE
specifier|public
specifier|static
specifier|final
name|String
name|SAVE
init|=
literal|"save"
decl_stmt|;
DECL|field|SAVE_AS
specifier|public
specifier|static
specifier|final
name|String
name|SAVE_AS
init|=
literal|"saveAs"
decl_stmt|;
DECL|field|SAVE_SELECTED_AS
specifier|public
specifier|static
specifier|final
name|String
name|SAVE_SELECTED_AS
init|=
literal|"saveSelectedAs"
decl_stmt|;
DECL|field|SAVE_SELECTED_AS_PLAIN
specifier|public
specifier|static
specifier|final
name|String
name|SAVE_SELECTED_AS_PLAIN
init|=
literal|"saveSelectedAsPlain"
decl_stmt|;
DECL|field|SEARCH
specifier|public
specifier|static
specifier|final
name|String
name|SEARCH
init|=
literal|"search"
decl_stmt|;
DECL|field|SELECT_ALL
specifier|public
specifier|static
specifier|final
name|String
name|SELECT_ALL
init|=
literal|"selectAll"
decl_stmt|;
DECL|field|SEND_AS_EMAIL
specifier|public
specifier|static
specifier|final
name|String
name|SEND_AS_EMAIL
init|=
literal|"sendAsEmail"
decl_stmt|;
DECL|field|SWITCH_PREVIEW
specifier|public
specifier|static
specifier|final
name|String
name|SWITCH_PREVIEW
init|=
literal|"switchPreview"
decl_stmt|;
DECL|field|TOGGLE_HIGHLIGHTS_GROUPS_MATCHING_ALL
specifier|public
specifier|static
specifier|final
name|String
name|TOGGLE_HIGHLIGHTS_GROUPS_MATCHING_ALL
init|=
literal|"toggleHighlightGroupsMatchingAll"
decl_stmt|;
DECL|field|TOGGLE_HIGHLIGHTS_GROUPS_MATCHING_ANY
specifier|public
specifier|static
specifier|final
name|String
name|TOGGLE_HIGHLIGHTS_GROUPS_MATCHING_ANY
init|=
literal|"toggleHighlightGroupsMatchingAny"
decl_stmt|;
DECL|field|TOGGLE_GROUPS
specifier|public
specifier|static
specifier|final
name|String
name|TOGGLE_GROUPS
init|=
literal|"toggleGroups"
decl_stmt|;
DECL|field|TOGGLE_PREVIEW
specifier|public
specifier|static
specifier|final
name|String
name|TOGGLE_PREVIEW
init|=
literal|"togglePreview"
decl_stmt|;
DECL|field|TOGGLE_TOOLBAR
specifier|public
specifier|static
specifier|final
name|String
name|TOGGLE_TOOLBAR
init|=
literal|"toggleToolbar"
decl_stmt|;
DECL|field|UNABBREVIATE
specifier|public
specifier|static
specifier|final
name|String
name|UNABBREVIATE
init|=
literal|"unabbreviate"
decl_stmt|;
DECL|field|UNDO
specifier|public
specifier|static
specifier|final
name|String
name|UNDO
init|=
literal|"undo"
decl_stmt|;
DECL|field|UNMARK_ALL
specifier|public
specifier|static
specifier|final
name|String
name|UNMARK_ALL
init|=
literal|"unmarkAll"
decl_stmt|;
DECL|field|UNMARK_ENTRIES
specifier|public
specifier|static
specifier|final
name|String
name|UNMARK_ENTRIES
init|=
literal|"unmarkEntries"
decl_stmt|;
DECL|field|WRITE_XMP
specifier|public
specifier|static
specifier|final
name|String
name|WRITE_XMP
init|=
literal|"writeXMP"
decl_stmt|;
block|}
end_class

end_unit

