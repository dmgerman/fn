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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|AbstractAction
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|Action
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|Globals
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|IconTheme
import|;
end_import

begin_import
import|import
name|org
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
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|JabRefIcon
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|keyboard
operator|.
name|KeyBinding
import|;
end_import

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

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
operator|.
name|BibDatabaseMode
import|;
end_import

begin_enum
DECL|enum|ActionsFX
specifier|public
enum|enum
name|ActionsFX
block|{
DECL|enumConstant|COPY_MORE
DECL|enumConstant|Localization.lang
name|COPY_MORE
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Copy"
argument_list|)
operator|+
literal|"..."
argument_list|)
block|,
DECL|enumConstant|COPY_TITLE
DECL|enumConstant|Localization.lang
DECL|enumConstant|KeyBinding.COPY_TITLE
name|COPY_TITLE
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Copy title"
argument_list|)
argument_list|,
name|KeyBinding
operator|.
name|COPY_TITLE
argument_list|)
block|,
DECL|enumConstant|COPY_KEY
DECL|enumConstant|Localization.lang
DECL|enumConstant|KeyBinding.COPY_BIBTEX_KEY
name|COPY_KEY
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Copy BibTeX key"
argument_list|)
argument_list|,
name|KeyBinding
operator|.
name|COPY_BIBTEX_KEY
argument_list|)
block|,
DECL|enumConstant|COPY_CITE_KEY
DECL|enumConstant|Localization.lang
DECL|enumConstant|KeyBinding.COPY_CITE_BIBTEX_KEY
name|COPY_CITE_KEY
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Copy \\cite{BibTeX key}"
argument_list|)
argument_list|,
name|KeyBinding
operator|.
name|COPY_CITE_BIBTEX_KEY
argument_list|)
block|,
DECL|enumConstant|COPY_KEY_AND_TITLE
DECL|enumConstant|Localization.lang
DECL|enumConstant|KeyBinding.COPY_BIBTEX_KEY_AND_TITLE
name|COPY_KEY_AND_TITLE
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Copy BibTeX key and title"
argument_list|)
argument_list|,
name|KeyBinding
operator|.
name|COPY_BIBTEX_KEY_AND_TITLE
argument_list|)
block|,
DECL|enumConstant|COPY_KEY_AND_LINK
DECL|enumConstant|Localization.lang
DECL|enumConstant|KeyBinding.COPY_BIBTEX_KEY_AND_LINK
name|COPY_KEY_AND_LINK
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Copy BibTeX key and link"
argument_list|)
argument_list|,
name|KeyBinding
operator|.
name|COPY_BIBTEX_KEY_AND_LINK
argument_list|)
block|,
DECL|enumConstant|COPY_CITATION_HTML
DECL|enumConstant|Localization.menuTitle
DECL|enumConstant|KeyBinding.COPY_PREVIEW
name|COPY_CITATION_HTML
argument_list|(
name|Localization
operator|.
name|menuTitle
argument_list|(
literal|"Copy citation"
argument_list|)
operator|+
literal|" (HTML)"
argument_list|,
name|KeyBinding
operator|.
name|COPY_PREVIEW
argument_list|)
block|,
DECL|enumConstant|COPY_CITATION_MORE
DECL|enumConstant|Localization.menuTitle
name|COPY_CITATION_MORE
argument_list|(
name|Localization
operator|.
name|menuTitle
argument_list|(
literal|"Copy citation"
argument_list|)
operator|+
literal|"..."
argument_list|)
block|,
DECL|enumConstant|COPY_CITATION_TEXT
name|COPY_CITATION_TEXT
argument_list|(
literal|"Text"
argument_list|)
block|,
DECL|enumConstant|COPY_CITATION_RTF
name|COPY_CITATION_RTF
argument_list|(
literal|"RTF"
argument_list|)
block|,
DECL|enumConstant|COPY_CITATION_ASCII_DOC
name|COPY_CITATION_ASCII_DOC
argument_list|(
literal|"AsciiDoc"
argument_list|)
block|,
DECL|enumConstant|COPY_CITATION_XSLFO
name|COPY_CITATION_XSLFO
argument_list|(
literal|"XSL-FO"
argument_list|)
block|,
DECL|enumConstant|COPY_CITATION_PREVIEW
DECL|enumConstant|Localization.lang
DECL|enumConstant|KeyBinding.COPY_PREVIEW
name|COPY_CITATION_PREVIEW
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Copy preview"
argument_list|)
argument_list|,
name|KeyBinding
operator|.
name|COPY_PREVIEW
argument_list|)
block|,
DECL|enumConstant|EXPORT_TO_CLIPBOARD
DECL|enumConstant|Localization.lang
DECL|enumConstant|IconTheme.JabRefIcons.EXPORT_TO_CLIPBOARD
name|EXPORT_TO_CLIPBOARD
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Export to clipboard"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|EXPORT_TO_CLIPBOARD
argument_list|)
block|,
DECL|enumConstant|COPY
DECL|enumConstant|Localization.lang
DECL|enumConstant|IconTheme.JabRefIcons.COPY
DECL|enumConstant|KeyBinding.COPY
name|COPY
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Copy"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|COPY
argument_list|,
name|KeyBinding
operator|.
name|COPY
argument_list|)
block|,
DECL|enumConstant|PASTE
DECL|enumConstant|Localization.lang
DECL|enumConstant|IconTheme.JabRefIcons.PASTE
DECL|enumConstant|KeyBinding.PASTE
name|PASTE
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Paste"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|PASTE
argument_list|,
name|KeyBinding
operator|.
name|PASTE
argument_list|)
block|,
DECL|enumConstant|CUT
DECL|enumConstant|Localization.lang
DECL|enumConstant|IconTheme.JabRefIcons.CUT
DECL|enumConstant|KeyBinding.CUT
name|CUT
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cut"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|CUT
argument_list|,
name|KeyBinding
operator|.
name|CUT
argument_list|)
block|,
DECL|enumConstant|DELETE
DECL|enumConstant|Localization.lang
DECL|enumConstant|IconTheme.JabRefIcons.DELETE_ENTRY
DECL|enumConstant|KeyBinding.DELETE_ENTRY
name|DELETE
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Delete"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|DELETE_ENTRY
argument_list|,
name|KeyBinding
operator|.
name|DELETE_ENTRY
argument_list|)
block|,
DECL|enumConstant|SEND_AS_EMAIL
DECL|enumConstant|Localization.lang
DECL|enumConstant|IconTheme.JabRefIcons.EMAIL
name|SEND_AS_EMAIL
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Send as email"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|EMAIL
argument_list|)
block|,
DECL|enumConstant|OPEN_FOLDER
DECL|enumConstant|Localization.lang
DECL|enumConstant|KeyBinding.OPEN_FOLDER
name|OPEN_FOLDER
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Open folder"
argument_list|)
argument_list|,
name|KeyBinding
operator|.
name|OPEN_FOLDER
argument_list|)
block|,
DECL|enumConstant|OPEN_EXTERNAL_FILE
DECL|enumConstant|Localization.lang
DECL|enumConstant|IconTheme.JabRefIcons.FILE
DECL|enumConstant|KeyBinding.OPEN_FILE
name|OPEN_EXTERNAL_FILE
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Open file"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|FILE
argument_list|,
name|KeyBinding
operator|.
name|OPEN_FILE
argument_list|)
block|,
DECL|enumConstant|OPEN_URL
DECL|enumConstant|Localization.lang
DECL|enumConstant|IconTheme.JabRefIcons.WWW
DECL|enumConstant|KeyBinding.OPEN_URL_OR_DOI
name|OPEN_URL
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Open URL or DOI"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|WWW
argument_list|,
name|KeyBinding
operator|.
name|OPEN_URL_OR_DOI
argument_list|)
block|,
DECL|enumConstant|MERGE_WITH_FETCHED_ENTRY
DECL|enumConstant|Localization.lang
name|MERGE_WITH_FETCHED_ENTRY
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Get BibTeX data from %0"
argument_list|,
literal|"DOI/ISBN/..."
argument_list|)
argument_list|)
block|,
DECL|enumConstant|ADD_FILE_LINK
DECL|enumConstant|Localization.lang
DECL|enumConstant|IconTheme.JabRefIcons.ATTACH_FILE
name|ADD_FILE_LINK
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Attach file"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|ATTACH_FILE
argument_list|)
block|,
DECL|enumConstant|MERGE_ENTRIES
DECL|enumConstant|Localization.lang
DECL|enumConstant|IconTheme.JabRefIcons.MERGE_ENTRIES
name|MERGE_ENTRIES
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Merge entries"
argument_list|)
operator|+
literal|"..."
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|MERGE_ENTRIES
argument_list|)
block|,
DECL|enumConstant|ADD_TO_GROUP
DECL|enumConstant|Localization.lang
name|ADD_TO_GROUP
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Add to group"
argument_list|)
argument_list|)
block|,
DECL|enumConstant|REMOVE_FROM_GROUP
DECL|enumConstant|Localization.lang
name|REMOVE_FROM_GROUP
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remove from group"
argument_list|)
argument_list|)
block|,
DECL|enumConstant|MOVE_TO_GROUP
DECL|enumConstant|Localization.lang
name|MOVE_TO_GROUP
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Move to group"
argument_list|)
argument_list|)
block|,
DECL|enumConstant|PRIORITY
DECL|enumConstant|Localization.lang
DECL|enumConstant|IconTheme.JabRefIcons.PRIORITY
name|PRIORITY
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Priority"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|PRIORITY
argument_list|)
block|,
DECL|enumConstant|CLEAR_PRIORITY
DECL|enumConstant|Localization.lang
name|CLEAR_PRIORITY
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Clear priority"
argument_list|)
argument_list|)
block|,
DECL|enumConstant|PRIORITY_HIGH
DECL|enumConstant|Localization.lang
DECL|enumConstant|IconTheme.JabRefIcons.PRIORITY_HIGH
name|PRIORITY_HIGH
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Set priority to high"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|PRIORITY_HIGH
argument_list|)
block|,
DECL|enumConstant|PRIORITY_MEDIUM
DECL|enumConstant|Localization.lang
DECL|enumConstant|IconTheme.JabRefIcons.PRIORITY_MEDIUM
name|PRIORITY_MEDIUM
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Set priority to medium"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|PRIORITY_MEDIUM
argument_list|)
block|,
DECL|enumConstant|PRIORITY_LOW
DECL|enumConstant|Localization.lang
DECL|enumConstant|IconTheme.JabRefIcons.PRIORITY_LOW
name|PRIORITY_LOW
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Set priority to low"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|PRIORITY_LOW
argument_list|)
block|,
DECL|enumConstant|QUALITY
DECL|enumConstant|Localization.lang
DECL|enumConstant|IconTheme.JabRefIcons.QUALITY
name|QUALITY
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Quality"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|QUALITY
argument_list|)
block|,
DECL|enumConstant|QUALITY_ASSURED
DECL|enumConstant|Localization.lang
DECL|enumConstant|IconTheme.JabRefIcons.QUALITY_ASSURED
name|QUALITY_ASSURED
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Toggle quality assured"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|QUALITY_ASSURED
argument_list|)
block|,
DECL|enumConstant|RANKING
DECL|enumConstant|Localization.lang
DECL|enumConstant|IconTheme.JabRefIcons.RANKING
name|RANKING
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Rank"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|RANKING
argument_list|)
block|,
DECL|enumConstant|CLEAR_RANK
DECL|enumConstant|Localization.lang
name|CLEAR_RANK
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Clear rank"
argument_list|)
argument_list|)
block|,
DECL|enumConstant|RANK_1
name|RANK_1
argument_list|(
literal|""
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|RANK1
argument_list|)
block|,
DECL|enumConstant|RANK_2
name|RANK_2
argument_list|(
literal|""
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|RANK2
argument_list|)
block|,
DECL|enumConstant|RANK_3
name|RANK_3
argument_list|(
literal|""
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|RANK3
argument_list|)
block|,
DECL|enumConstant|RANK_4
name|RANK_4
argument_list|(
literal|""
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|RANK4
argument_list|)
block|,
DECL|enumConstant|RANK_5
name|RANK_5
argument_list|(
literal|""
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|RANK5
argument_list|)
block|,
DECL|enumConstant|PRINTED
DECL|enumConstant|Localization.lang
DECL|enumConstant|IconTheme.JabRefIcons.PRINTED
name|PRINTED
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Printed"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|PRINTED
argument_list|)
block|,
DECL|enumConstant|TOGGLE_PRINTED
DECL|enumConstant|Localization.lang
DECL|enumConstant|IconTheme.JabRefIcons.PRINTED
name|TOGGLE_PRINTED
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Toggle print status"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|PRINTED
argument_list|)
block|,
DECL|enumConstant|READ_STATUS
DECL|enumConstant|Localization.lang
DECL|enumConstant|IconTheme.JabRefIcons.READ_STATUS
name|READ_STATUS
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Read status"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|READ_STATUS
argument_list|)
block|,
DECL|enumConstant|CLEAR_READ_STATUS
DECL|enumConstant|Localization.lang
name|CLEAR_READ_STATUS
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Clear read status"
argument_list|)
argument_list|)
block|,
DECL|enumConstant|READ
DECL|enumConstant|Localization.lang
DECL|enumConstant|IconTheme.JabRefIcons.READ_STATUS_READ
name|READ
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Set read status to read"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|READ_STATUS_READ
argument_list|)
block|,
DECL|enumConstant|SKIMMED
DECL|enumConstant|Localization.lang
DECL|enumConstant|IconTheme.JabRefIcons.READ_STATUS_SKIMMED
name|SKIMMED
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Set read status to skimmed"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|READ_STATUS_SKIMMED
argument_list|)
block|,
DECL|enumConstant|RELEVANCE
DECL|enumConstant|Localization.lang
DECL|enumConstant|IconTheme.JabRefIcons.RELEVANCE
name|RELEVANCE
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Relevance"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|RELEVANCE
argument_list|)
block|,
DECL|enumConstant|RELEVANT
DECL|enumConstant|Localization.lang
DECL|enumConstant|IconTheme.JabRefIcons.RELEVANCE
name|RELEVANT
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Toggle relevance"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|RELEVANCE
argument_list|)
block|,
DECL|enumConstant|NEW_LIBRARY_BIBTEX
DECL|enumConstant|Localization.menuTitle
DECL|enumConstant|IconTheme.JabRefIcons.NEW
name|NEW_LIBRARY_BIBTEX
argument_list|(
name|Localization
operator|.
name|menuTitle
argument_list|(
literal|"New %0 library"
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBTEX
operator|.
name|getFormattedName
argument_list|()
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|NEW
argument_list|)
block|,
DECL|enumConstant|NEW_LIBRARY_BIBLATEX
DECL|enumConstant|Localization.menuTitle
DECL|enumConstant|IconTheme.JabRefIcons.NEW
name|NEW_LIBRARY_BIBLATEX
argument_list|(
name|Localization
operator|.
name|menuTitle
argument_list|(
literal|"New %0 library"
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBLATEX
operator|.
name|getFormattedName
argument_list|()
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|NEW
argument_list|)
block|,
DECL|enumConstant|OPEN_LIBRARY
DECL|enumConstant|Localization.menuTitle
DECL|enumConstant|IconTheme.JabRefIcons.OPEN
DECL|enumConstant|KeyBinding.OPEN_DATABASE
name|OPEN_LIBRARY
argument_list|(
name|Localization
operator|.
name|menuTitle
argument_list|(
literal|"Open library"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|OPEN
argument_list|,
name|KeyBinding
operator|.
name|OPEN_DATABASE
argument_list|)
block|,
DECL|enumConstant|mergeDatabaseAction
DECL|enumConstant|Localization.menuTitle
DECL|enumConstant|Localization.lang
name|mergeDatabaseAction
argument_list|(
name|Localization
operator|.
name|menuTitle
argument_list|(
literal|"Append library"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Append contents from a BibTeX library into the currently viewed library"
argument_list|)
argument_list|)
block|,
DECL|enumConstant|save
DECL|enumConstant|Localization.menuTitle
DECL|enumConstant|IconTheme.JabRefIcons.SAVE
DECL|enumConstant|KeyBinding.SAVE_DATABASE
name|save
argument_list|(
name|Localization
operator|.
name|menuTitle
argument_list|(
literal|"Save library"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|SAVE
argument_list|,
name|KeyBinding
operator|.
name|SAVE_DATABASE
argument_list|)
block|,
DECL|enumConstant|AbstractAction
DECL|enumConstant|saveAs
specifier|private
specifier|final
name|AbstractAction
name|saveAs
init|=
operator|new
name|JabRefFrame
operator|.
name|GeneralAction
argument_list|(
name|Actions
operator|.
name|SAVE_AS
argument_list|,
name|Localization
operator|.
name|menuTitle
argument_list|(
literal|"Save library as..."
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save library as..."
argument_list|)
argument_list|,
name|Globals
operator|.
name|getKeyPrefs
argument_list|()
operator|.
name|getKey
argument_list|(
name|KeyBinding
operator|.
name|SAVE_DATABASE_AS
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|saveSelectedAsPlain
specifier|private
specifier|final
name|AbstractAction
name|saveSelectedAsPlain
init|=
operator|new
name|JabRefFrame
operator|.
name|GeneralAction
argument_list|(
name|Actions
operator|.
name|SAVE_SELECTED_AS_PLAIN
argument_list|,
name|Localization
operator|.
name|menuTitle
argument_list|(
literal|"Save selected as plain BibTeX..."
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save selected as plain BibTeX..."
argument_list|)
argument_list|)
decl_stmt|;
block|)
DECL|field|description
specifier|private
specifier|final
name|String
name|description
decl_stmt|;
end_enum

begin_expr_stmt
DECL|field|description
operator|.
name|saveAll
argument_list|(
name|super
argument_list|(
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|SAVE_ALL
operator|.
name|getIcon
argument_list|()
argument_list|)
name|putValue
argument_list|(
name|Action
operator|.
name|ACCELERATOR_KEY
argument_list|,
name|Globals
operator|.
name|getKeyPrefs
argument_list|()
argument_list|)
name|getKey
argument_list|(
name|KeyBinding
operator|.
name|SAVE_ALL
argument_list|)
argument_list|)
end_expr_stmt

begin_expr_stmt
unit|)
name|putValue
argument_list|(
name|Action
operator|.
name|SHORT_DESCRIPTION
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save all open libraries"
argument_list|)
name|putValue
argument_list|(
name|Action
operator|.
name|NAME
argument_list|,
name|Localization
operator|.
name|menuTitle
argument_list|(
literal|"Save all"
argument_list|)
argument_list|)
name|importNew
argument_list|(
name|putValue
argument_list|(
name|Action
operator|.
name|NAME
argument_list|,
name|Localization
operator|.
name|menuTitle
argument_list|(
literal|"Import into new library"
argument_list|)
operator|.
name|putValue
argument_list|(
name|Action
operator|.
name|ACCELERATOR_KEY
argument_list|,
name|Globals
operator|.
name|getKeyPrefs
argument_list|()
argument_list|)
argument_list|)
name|getKey
argument_list|(
name|KeyBinding
operator|.
name|IMPORT_INTO_NEW_DATABASE
argument_list|)
argument_list|)
name|importCurrent
argument_list|(
name|putValue
argument_list|(
name|Action
operator|.
name|NAME
argument_list|,
name|Localization
operator|.
name|menuTitle
argument_list|(
literal|"Import into current library"
argument_list|)
operator|.
name|putValue
argument_list|(
name|Action
operator|.
name|ACCELERATOR_KEY
argument_list|,
name|Globals
operator|.
name|getKeyPrefs
argument_list|()
argument_list|)
argument_list|)
specifier|private
name|final
name|String
name|text
argument_list|;
name|getKey
argument_list|(
name|KeyBinding
operator|.
name|IMPORT_INTO_CURRENT_DATABASE
argument_list|)
specifier|private
name|final
name|Optional
argument_list|<
name|JabRefIcon
argument_list|>
name|icon
argument_list|;
specifier|private
name|final
name|Optional
argument_list|<
name|KeyBinding
argument_list|>
name|keyBinding
argument_list|;
name|ActionsFX
argument_list|(
name|String
name|text
argument_list|)
block|{
name|this
argument_list|(
literal|""
argument_list|,
literal|""
argument_list|)
block|;     }
name|ActionsFX
argument_list|(
name|String
name|text
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
name|icon
argument_list|)
block|{
name|this
operator|.
name|text
operator|=
name|text
block|;
name|this
operator|.
name|description
operator|=
literal|""
block|;
name|this
operator|.
name|icon
operator|=
name|Optional
operator|.
name|of
argument_list|(
name|icon
argument_list|)
block|;
name|this
operator|.
name|keyBinding
operator|=
name|Optional
operator|.
name|empty
argument_list|()
block|;     }
name|ActionsFX
argument_list|(
name|String
name|text
argument_list|,
name|IconTheme
operator|.
name|JabRefIcons
name|icon
argument_list|,
name|KeyBinding
name|keyBinding
argument_list|)
block|{
name|this
operator|.
name|text
operator|=
name|text
block|;
name|this
operator|.
name|description
operator|=
literal|""
block|;
name|this
operator|.
name|icon
operator|=
name|Optional
operator|.
name|of
argument_list|(
name|icon
argument_list|)
block|;
name|this
operator|.
name|keyBinding
operator|=
name|Optional
operator|.
name|of
argument_list|(
name|keyBinding
argument_list|)
block|;     }
name|ActionsFX
argument_list|(
name|String
name|text
argument_list|,
name|KeyBinding
name|keyBinding
argument_list|)
block|{
name|this
operator|.
name|text
operator|=
name|text
block|;
name|this
operator|.
name|description
operator|=
literal|""
block|;
name|this
operator|.
name|keyBinding
operator|=
name|Optional
operator|.
name|of
argument_list|(
name|keyBinding
argument_list|)
block|;
name|this
operator|.
name|icon
operator|=
name|Optional
operator|.
name|empty
argument_list|()
block|;     }
name|ActionsFX
argument_list|(
name|String
name|text
argument_list|,
name|String
name|description
argument_list|)
block|{
name|this
operator|.
name|text
operator|=
name|text
block|;
name|this
operator|.
name|description
operator|=
name|description
block|;
name|this
operator|.
name|icon
operator|=
name|Optional
operator|.
name|empty
argument_list|()
block|;
name|this
operator|.
name|keyBinding
operator|=
name|Optional
operator|.
name|empty
argument_list|()
block|;     }
specifier|public
name|Optional
argument_list|<
name|JabRefIcon
argument_list|>
name|getIcon
argument_list|()
block|{
return|return
name|icon
return|;
block|}
end_expr_stmt

begin_function
specifier|public
name|Optional
argument_list|<
name|KeyBinding
argument_list|>
name|getKeyBinding
parameter_list|()
block|{
return|return
name|keyBinding
return|;
block|}
end_function

begin_function
specifier|public
name|String
name|getText
parameter_list|()
block|{
return|return
name|text
return|;
block|}
end_function

begin_function
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|description
return|;
block|}
end_function

unit|}
end_unit

