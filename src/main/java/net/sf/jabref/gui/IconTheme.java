begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
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
name|image
operator|.
name|BufferedImage
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|BufferedReader
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
name|InputStreamReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|*
import|;
end_import

begin_class
DECL|class|IconTheme
specifier|public
class|class
name|IconTheme
block|{
DECL|field|FONT
specifier|public
specifier|static
name|Font
name|FONT
decl_stmt|;
DECL|field|FONT_16
specifier|public
specifier|static
name|Font
name|FONT_16
decl_stmt|;
static|static
block|{
try|try
block|{
name|FONT
operator|=
name|Font
operator|.
name|createFont
argument_list|(
name|Font
operator|.
name|TRUETYPE_FONT
argument_list|,
name|FontBasedIcon
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"/fonts/materialdesignicons-webfont.ttf"
argument_list|)
argument_list|)
expr_stmt|;
name|FONT_16
operator|=
name|FONT
operator|.
name|deriveFont
argument_list|(
name|Font
operator|.
name|PLAIN
argument_list|,
literal|16f
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|FontFormatException
decl||
name|IOException
name|e
parameter_list|)
block|{
comment|// PROBLEM!
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
DECL|enum|JabRefIcon
specifier|public
enum|enum
name|JabRefIcon
block|{
DECL|enumConstant|ADD
name|ADD
argument_list|(
literal|"\uf494"
argument_list|)
comment|/*css: plus-box*/
block|,
DECL|enumConstant|ADD_NOBOX
name|ADD_NOBOX
argument_list|(
literal|"\uF493"
argument_list|)
comment|/*css: plus */
block|,
DECL|enumConstant|ADD_ENTRY
name|ADD_ENTRY
argument_list|(
literal|"\uF571"
argument_list|)
comment|/*css: tooltip-outline-plus */
block|,
DECL|enumConstant|EDIT_ENTRY
name|EDIT_ENTRY
argument_list|(
literal|"\uf56e"
argument_list|)
comment|/*css: tooltip-edit */
block|,
DECL|enumConstant|EDIT_STRINGS
name|EDIT_STRINGS
argument_list|(
literal|"\uf572"
argument_list|)
comment|/*css: tooltip-text */
block|,
DECL|enumConstant|CLIPBOARD
name|CLIPBOARD
argument_list|(
literal|"\uf218"
argument_list|)
block|,
DECL|enumConstant|FOLDER
name|FOLDER
argument_list|(
literal|"\uf07b"
argument_list|)
block|,
DECL|enumConstant|REMOVE
name|REMOVE
argument_list|(
literal|"\uf406"
argument_list|)
block|,
DECL|enumConstant|REMOVE_NOBOX
name|REMOVE_NOBOX
argument_list|(
literal|"\uF405"
argument_list|)
comment|/*css: minus */
block|,
DECL|enumConstant|FILE
name|FILE
argument_list|(
literal|"\uf2cf"
argument_list|)
block|,
DECL|enumConstant|PDF_FILE
name|PDF_FILE
argument_list|(
literal|"\uf2dc"
argument_list|)
block|,
DECL|enumConstant|TAGS
name|TAGS
argument_list|(
literal|"\uf485"
argument_list|)
block|,
DECL|enumConstant|DOI
name|DOI
argument_list|(
literal|"\uF5D1"
argument_list|)
comment|/*css: web*/
block|,
DECL|enumConstant|DUPLICATE
name|DUPLICATE
argument_list|(
literal|"\uF255"
argument_list|)
comment|/*css: content-duplicate */
block|,
DECL|enumConstant|EDIT
name|EDIT
argument_list|(
literal|"\uF1AB"
argument_list|)
comment|/*css: pencil */
block|,
DECL|enumConstant|NEW
name|NEW
argument_list|(
literal|"\uF2DB"
argument_list|)
comment|/* css: file-outline */
block|,
DECL|enumConstant|SAVE
name|SAVE
argument_list|(
literal|"\uF257"
argument_list|)
comment|/*css: content-save*/
block|,
DECL|enumConstant|SAVE_ALL
name|SAVE_ALL
argument_list|(
literal|"\uF258"
argument_list|)
comment|/*css: content-save-all*/
block|,
DECL|enumConstant|CLOSE
name|CLOSE
argument_list|(
literal|"\uF5DE"
argument_list|)
comment|/* css: close */
block|,
DECL|enumConstant|PASTE
name|PASTE
argument_list|(
literal|"\uF256"
argument_list|)
comment|/*css: content-paste*/
block|,
DECL|enumConstant|CUT
name|CUT
argument_list|(
literal|"\uF254"
argument_list|)
comment|/*css: content-cut*/
block|,
DECL|enumConstant|COPY
name|COPY
argument_list|(
literal|"\uF253"
argument_list|)
comment|/*css: content-copy */
block|,
DECL|enumConstant|REDO
name|REDO
argument_list|(
literal|"\uF4B9"
argument_list|)
comment|/*css: redo*/
block|,
DECL|enumConstant|UNDO
name|UNDO
argument_list|(
literal|"\uF58F"
argument_list|)
comment|/*css: undo*/
block|,
DECL|enumConstant|MARK_ENTRIES
name|MARK_ENTRIES
argument_list|(
literal|"\uF1A2"
argument_list|)
comment|/*css: bookmark */
block|,
DECL|enumConstant|UNMARK_ENTRIES
name|UNMARK_ENTRIES
argument_list|(
literal|"\uF1A5"
argument_list|)
comment|/*css: bookmark-outline */
block|,
DECL|enumConstant|REFRESH
name|REFRESH
argument_list|(
literal|"\uF4BB"
argument_list|)
comment|/*css: refresh */
block|,
DECL|enumConstant|DELETE
name|DELETE
argument_list|(
literal|"\uF280"
argument_list|)
comment|/*css: delete */
block|,
DECL|enumConstant|SEARCH
name|SEARCH
argument_list|(
literal|"\uF3DE"
argument_list|)
comment|/*css: magnify */
block|,
DECL|enumConstant|INC_SEARCH
name|INC_SEARCH
argument_list|(
literal|"\uF3DE"
argument_list|)
comment|/*css: magnify */
block|,
DECL|enumConstant|PREFERENCES
name|PREFERENCES
argument_list|(
literal|"\uF4F0"
argument_list|)
comment|/*css: settings */
block|,
DECL|enumConstant|HELP
name|HELP
argument_list|(
literal|"\uF37D"
argument_list|)
comment|/*css: help-circle*/
block|,
DECL|enumConstant|HELP_CONTENTS
name|HELP_CONTENTS
argument_list|(
literal|"\uF1A0"
argument_list|)
comment|/*css: book-open */
block|,
DECL|enumConstant|UP
name|UP
argument_list|(
literal|"\uF214"
argument_list|)
comment|/*css: chevron-up */
block|,
DECL|enumConstant|DOWN
name|DOWN
argument_list|(
literal|"\uF211"
argument_list|)
comment|/*css: chevron-down */
block|,
DECL|enumConstant|LEFT
name|LEFT
argument_list|(
literal|"\uF141"
argument_list|)
comment|/* css; arrow-left-bold */
block|,
DECL|enumConstant|RIGHT
name|RIGHT
argument_list|(
literal|"\uF146"
argument_list|)
comment|/*css: arrow-right-bold */
block|,
DECL|enumConstant|UNKNOWN
name|UNKNOWN
argument_list|(
literal|"\uF37C"
argument_list|)
comment|/*css: help */
block|,
DECL|enumConstant|SOURCE
name|SOURCE
argument_list|(
literal|"\uF232"
argument_list|)
comment|/*css: code-braces*/
block|,
DECL|enumConstant|MAKE_KEY
name|MAKE_KEY
argument_list|(
literal|"\uF3AC"
argument_list|)
comment|/*css: key-variant */
block|,
DECL|enumConstant|CLEANUP_ENTRIES
name|CLEANUP_ENTRIES
argument_list|(
literal|"\uF1C2"
argument_list|)
comment|/*css: broom */
block|,
DECL|enumConstant|PRIORITY
name|PRIORITY
argument_list|(
literal|"\uF2F0"
argument_list|)
comment|/*css: flag */
block|,
DECL|enumConstant|PRIORITY_HIGH
name|PRIORITY_HIGH
argument_list|(
literal|"\uF2F0"
argument_list|,
name|Color
operator|.
name|RED
argument_list|)
comment|/*css: flag */
block|,
DECL|enumConstant|PRIORITY_MEDIUM
name|PRIORITY_MEDIUM
argument_list|(
literal|"\uF2F0"
argument_list|,
name|Color
operator|.
name|ORANGE
argument_list|)
comment|/*css: flag */
block|,
DECL|enumConstant|PRIORITY_LOW
name|PRIORITY_LOW
argument_list|(
literal|"\uF2F0"
argument_list|,
name|Color
operator|.
name|GREEN
argument_list|)
comment|/*css: flag */
block|,
DECL|enumConstant|PRINTED
name|PRINTED
argument_list|(
literal|"\uF4A5"
argument_list|)
comment|/*css: printer */
block|,
DECL|enumConstant|TOGGLE_PRINTED
name|TOGGLE_PRINTED
argument_list|(
literal|"\uF4A5"
argument_list|)
comment|/*css: printer */
block|,
DECL|enumConstant|RANKING
name|RANKING
argument_list|(
literal|"\uf521"
argument_list|)
comment|/*css: numeric*/
block|,
comment|//RANK1("\uF430") /*css: numeric-1-box */,
DECL|enumConstant|RANK1
name|RANK1
argument_list|(
literal|"\uf521\uf524\uF524\uF524\uF524"
argument_list|)
block|,
DECL|enumConstant|RANK2
name|RANK2
argument_list|(
literal|"\uf521\uf521\uF524\uF524\uF524"
argument_list|)
block|,
DECL|enumConstant|RANK3
name|RANK3
argument_list|(
literal|"\uf521\uf521\uF521\uF524\uF524"
argument_list|)
block|,
DECL|enumConstant|RANK4
name|RANK4
argument_list|(
literal|"\uf521\uf521\uF521\uF521\uF524"
argument_list|)
block|,
DECL|enumConstant|RANK5
name|RANK5
argument_list|(
literal|"\uf521\uf521\uF521\uF521\uF521"
argument_list|)
block|,
DECL|enumConstant|WWW
name|WWW
argument_list|(
literal|"\uF5D1"
argument_list|)
comment|/*css: web*/
block|,
DECL|enumConstant|GROUP_INCLUDING
name|GROUP_INCLUDING
argument_list|(
literal|"\uF2E9"
argument_list|)
comment|/*css: filter-outline*/
block|,
DECL|enumConstant|GROUP_REFINING
name|GROUP_REFINING
argument_list|(
literal|"\uF2E8"
argument_list|)
comment|/*css: filter*/
block|,
DECL|enumConstant|AUTO_GROUP
name|AUTO_GROUP
argument_list|(
literal|"\uF154"
argument_list|)
block|,
comment|/*css: auto-fix*/
DECL|enumConstant|EMAIL
name|EMAIL
argument_list|(
literal|"\uF2AD"
argument_list|)
comment|/*css: email*/
block|,
DECL|enumConstant|DOWNLOAD
name|DOWNLOAD
argument_list|(
literal|"\uF299"
argument_list|)
comment|/*css: download */
block|,
DECL|enumConstant|EXPORT_TO_CLIPBOARD
name|EXPORT_TO_CLIPBOARD
argument_list|(
literal|"\uF21C"
argument_list|)
comment|/*css: clipboard-arrow-left */
block|,
DECL|enumConstant|ATTACH_FILE
name|ATTACH_FILE
argument_list|(
literal|"\uF469"
argument_list|)
comment|/*css: paperclip*/
block|,
DECL|enumConstant|AUTO_FILE_LINK
name|AUTO_FILE_LINK
argument_list|(
literal|"\uF2D6"
argument_list|)
comment|/*css: file-find */
block|,
DECL|enumConstant|TOGGLE_QUALITY_ASSURED
name|TOGGLE_QUALITY_ASSURED
argument_list|(
literal|"\uF5E7"
argument_list|)
block|,
DECL|enumConstant|QUALITY_ASSURED
name|QUALITY_ASSURED
argument_list|(
literal|"\uF5E7"
argument_list|)
block|,
DECL|enumConstant|QUALITY
name|QUALITY
argument_list|(
literal|"\uF5E7"
argument_list|)
block|,
DECL|enumConstant|OPEN
name|OPEN
argument_list|(
literal|"\uF300"
argument_list|)
comment|/*css: folder */
block|,
DECL|enumConstant|ADD_ROW
name|ADD_ROW
argument_list|(
literal|"\uf4ed"
argument_list|)
comment|/* css: server-plus*/
block|,
DECL|enumConstant|REMOVE_ROW
name|REMOVE_ROW
argument_list|(
literal|"\uF4E9"
argument_list|)
comment|/*css: server-minus */
block|,
DECL|enumConstant|PICTURE
name|PICTURE
argument_list|(
literal|"\uf2d7"
argument_list|)
comment|/*css: file-image */
block|,
DECL|enumConstant|READ_STATUS_READ
name|READ_STATUS_READ
argument_list|(
literal|"\uF2C4"
argument_list|,
name|Color
operator|.
name|GREEN
argument_list|)
block|,
comment|/*css: eye */
DECL|enumConstant|READ_STATUS_SKIMMED
name|READ_STATUS_SKIMMED
argument_list|(
literal|"\uF2C4"
argument_list|,
name|Color
operator|.
name|ORANGE
argument_list|)
block|,
comment|/*css: eye */
DECL|enumConstant|READ_STATUS
name|READ_STATUS
argument_list|(
literal|"\uF2C4"
argument_list|)
block|,
comment|/*css: eye */
DECL|enumConstant|RELEVANT
name|RELEVANT
argument_list|(
literal|"\uF522"
argument_list|)
block|,
comment|/*css: star-circle */
DECL|enumConstant|TOGGLE_RELEVANCE
name|TOGGLE_RELEVANCE
argument_list|(
literal|"\uF522"
argument_list|)
block|,
comment|/*css: star-circle */
DECL|enumConstant|SET_RELEVANT
name|SET_RELEVANT
argument_list|(
literal|"\uF522"
argument_list|)
block|,
comment|/*css: star-circle */
DECL|enumConstant|MERGE_ENTRIES
name|MERGE_ENTRIES
argument_list|(
literal|"\uf24f"
argument_list|)
block|,
comment|/* css: compare */
DECL|enumConstant|CONNECT_OPEN_OFFICE
name|CONNECT_OPEN_OFFICE
argument_list|(
literal|"\uf454"
argument_list|)
comment|/*css: open-in-app */
block|,
DECL|enumConstant|PLAIN_TEXT_IMPORT_TODO
name|PLAIN_TEXT_IMPORT_TODO
argument_list|(
literal|"\uf202"
argument_list|)
comment|/* css: checkbox-blank-circle-outline*/
block|,
DECL|enumConstant|PLAIN_TEXT_IMPORT_DONE
name|PLAIN_TEXT_IMPORT_DONE
argument_list|(
literal|"\uf206"
argument_list|)
comment|/* checkbox-marked-circle-outline */
block|,
DECL|enumConstant|DONATE
name|DONATE
argument_list|(
literal|"\uf34d"
argument_list|)
block|,
comment|/* css: gift */
DECL|enumConstant|MOVE_TAB_ARROW
name|MOVE_TAB_ARROW
argument_list|(
literal|"\uf14d"
argument_list|)
block|,
comment|/*css:  arrow-up-bold */
comment|// simple color boxes:
DECL|enumConstant|BOX_RED
name|BOX_RED
argument_list|(
literal|"\uF200"
argument_list|,
name|Color
operator|.
name|RED
argument_list|)
block|,
DECL|enumConstant|BOX_ORANGE
name|BOX_ORANGE
argument_list|(
literal|"\uF200"
argument_list|,
name|Color
operator|.
name|ORANGE
argument_list|)
block|,
DECL|enumConstant|BOX_GREEN
name|BOX_GREEN
argument_list|(
literal|"\uF200"
argument_list|,
name|Color
operator|.
name|GREEN
argument_list|)
block|,
comment|// STILL MISSING:
DECL|enumConstant|COMPLETE
name|COMPLETE
argument_list|(
literal|"\uF4E6"
argument_list|,
name|Color
operator|.
name|RED
argument_list|)
block|,
DECL|enumConstant|EDIT_PREAMBLE
name|EDIT_PREAMBLE
argument_list|(
literal|"\uF4E6"
argument_list|,
name|Color
operator|.
name|RED
argument_list|)
block|,
DECL|enumConstant|EXPORT_TO_KEYWORDS
name|EXPORT_TO_KEYWORDS
argument_list|(
literal|"\uF4E6"
argument_list|,
name|Color
operator|.
name|RED
argument_list|)
block|,
comment|//SAVE_AS("\uF4E6", Color.RED),
DECL|enumConstant|WRONG
name|WRONG
argument_list|(
literal|"\uF4E6"
argument_list|,
name|Color
operator|.
name|RED
argument_list|)
block|,
DECL|enumConstant|GROUPS_HIGHLIGHT_ALL
name|GROUPS_HIGHLIGHT_ALL
argument_list|(
literal|"\uF4E6"
argument_list|,
name|Color
operator|.
name|RED
argument_list|)
block|,
DECL|enumConstant|GROUPS_HIGHLIGHT_ANY
name|GROUPS_HIGHLIGHT_ANY
argument_list|(
literal|"\uF4E6"
argument_list|,
name|Color
operator|.
name|RED
argument_list|)
block|,
DECL|enumConstant|GENERAL
name|GENERAL
argument_list|(
literal|"\uF4E6"
argument_list|,
name|Color
operator|.
name|RED
argument_list|)
block|,
DECL|enumConstant|GREEN
name|GREEN
argument_list|(
literal|"\uF4E6"
argument_list|,
name|Color
operator|.
name|RED
argument_list|)
block|,
DECL|enumConstant|IMPORT_FROM_KEYWORDS
name|IMPORT_FROM_KEYWORDS
argument_list|(
literal|"\uF4E6"
argument_list|,
name|Color
operator|.
name|RED
argument_list|)
block|,
DECL|enumConstant|INTEGRITY_CHECK
name|INTEGRITY_CHECK
argument_list|(
literal|"\uF4E6"
argument_list|,
name|Color
operator|.
name|RED
argument_list|)
block|,
DECL|enumConstant|INTEGRITY_FAIL
name|INTEGRITY_FAIL
argument_list|(
literal|"\uF4E6"
argument_list|,
name|Color
operator|.
name|RED
argument_list|)
block|,
DECL|enumConstant|INTEGRITY_INFO
name|INTEGRITY_INFO
argument_list|(
literal|"\uF4E6"
argument_list|,
name|Color
operator|.
name|RED
argument_list|)
block|,
DECL|enumConstant|INTEGRITY_WARN
name|INTEGRITY_WARN
argument_list|(
literal|"\uF4E6"
argument_list|,
name|Color
operator|.
name|RED
argument_list|)
block|,
DECL|enumConstant|LOAD_SESSION
name|LOAD_SESSION
argument_list|(
literal|"\uF4E6"
argument_list|,
name|Color
operator|.
name|RED
argument_list|)
block|,
DECL|enumConstant|OPTIONAL
name|OPTIONAL
argument_list|(
literal|"\uF4E6"
argument_list|,
name|Color
operator|.
name|RED
argument_list|)
block|,
DECL|enumConstant|ORANGE
name|ORANGE
argument_list|(
literal|"\uF4E6"
argument_list|,
name|Color
operator|.
name|RED
argument_list|)
block|,
DECL|enumConstant|PLUGIN
name|PLUGIN
argument_list|(
literal|"\uF4E6"
argument_list|,
name|Color
operator|.
name|RED
argument_list|)
block|,
DECL|enumConstant|RED
name|RED
argument_list|(
literal|"\uF4E6"
argument_list|,
name|Color
operator|.
name|RED
argument_list|)
block|,
DECL|enumConstant|REQUIRED
name|REQUIRED
argument_list|(
literal|"\uF4E6"
argument_list|,
name|Color
operator|.
name|RED
argument_list|)
block|,
DECL|enumConstant|SECONDARY_SORTED_REVERSE
name|SECONDARY_SORTED_REVERSE
argument_list|(
literal|"\uF4E6"
argument_list|,
name|Color
operator|.
name|RED
argument_list|)
block|,
DECL|enumConstant|TOGGLE_GROUPS
name|TOGGLE_GROUPS
argument_list|(
literal|"\uF4E6"
argument_list|,
name|Color
operator|.
name|RED
argument_list|)
block|,
DECL|enumConstant|TOGGLE_ENTRY_PREVIEW
name|TOGGLE_ENTRY_PREVIEW
argument_list|(
literal|"\uF4E6"
argument_list|,
name|Color
operator|.
name|RED
argument_list|)
block|,
DECL|enumConstant|OPEN_FOLDER
name|OPEN_FOLDER
argument_list|(
literal|"\uF4E6"
argument_list|,
name|Color
operator|.
name|RED
argument_list|)
block|,
DECL|enumConstant|GROUP_REGULAR
name|GROUP_REGULAR
argument_list|(
literal|"\uF4E6"
argument_list|,
name|Color
operator|.
name|RED
argument_list|)
block|,
DECL|enumConstant|WRITE_XMP
name|WRITE_XMP
argument_list|(
literal|"\uF4E6"
argument_list|,
name|Color
operator|.
name|RED
argument_list|)
block|;
DECL|field|code
specifier|private
specifier|final
name|String
name|code
decl_stmt|;
DECL|field|color
specifier|private
specifier|final
name|Color
name|color
decl_stmt|;
DECL|method|JabRefIcon (String code)
name|JabRefIcon
parameter_list|(
name|String
name|code
parameter_list|)
block|{
name|this
argument_list|(
name|code
argument_list|,
operator|new
name|Color
argument_list|(
literal|113
argument_list|,
literal|134
argument_list|,
literal|145
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|JabRefIcon (String code, Color color)
name|JabRefIcon
parameter_list|(
name|String
name|code
parameter_list|,
name|Color
name|color
parameter_list|)
block|{
name|this
operator|.
name|code
operator|=
name|code
expr_stmt|;
name|this
operator|.
name|color
operator|=
name|color
expr_stmt|;
block|}
DECL|method|getIcon ()
specifier|public
name|FontBasedIcon
name|getIcon
parameter_list|()
block|{
return|return
operator|new
name|FontBasedIcon
argument_list|(
name|this
operator|.
name|code
argument_list|,
name|this
operator|.
name|color
argument_list|)
return|;
block|}
DECL|method|getSmallIcon ()
specifier|public
name|FontBasedIcon
name|getSmallIcon
parameter_list|()
block|{
return|return
operator|new
name|FontBasedIcon
argument_list|(
name|this
operator|.
name|code
argument_list|,
name|this
operator|.
name|color
argument_list|,
literal|16
argument_list|)
return|;
block|}
DECL|method|getCode ()
specifier|public
name|String
name|getCode
parameter_list|()
block|{
return|return
name|this
operator|.
name|code
return|;
block|}
DECL|method|getImageIcon ()
specifier|public
name|ImageIcon
name|getImageIcon
parameter_list|()
block|{
return|return
operator|new
name|ImageIcon
argument_list|()
block|{
specifier|private
name|FontBasedIcon
name|icon
init|=
name|getIcon
argument_list|()
decl_stmt|;
annotation|@
name|Override
specifier|public
specifier|synchronized
name|void
name|paintIcon
parameter_list|(
name|Component
name|c
parameter_list|,
name|Graphics
name|g
parameter_list|,
name|int
name|x
parameter_list|,
name|int
name|y
parameter_list|)
block|{
name|icon
operator|.
name|paintIcon
argument_list|(
name|c
argument_list|,
name|g
argument_list|,
name|x
argument_list|,
name|y
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|getIconWidth
parameter_list|()
block|{
return|return
name|icon
operator|.
name|getIconWidth
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|getIconHeight
parameter_list|()
block|{
return|return
name|icon
operator|.
name|getIconHeight
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|Image
name|getImage
parameter_list|()
block|{
name|int
name|w
init|=
name|icon
operator|.
name|getIconWidth
argument_list|()
decl_stmt|;
name|int
name|h
init|=
name|icon
operator|.
name|getIconHeight
argument_list|()
decl_stmt|;
name|GraphicsEnvironment
name|ge
init|=
name|GraphicsEnvironment
operator|.
name|getLocalGraphicsEnvironment
argument_list|()
decl_stmt|;
name|GraphicsDevice
name|gd
init|=
name|ge
operator|.
name|getDefaultScreenDevice
argument_list|()
decl_stmt|;
name|GraphicsConfiguration
name|gc
init|=
name|gd
operator|.
name|getDefaultConfiguration
argument_list|()
decl_stmt|;
name|BufferedImage
name|image
init|=
name|gc
operator|.
name|createCompatibleImage
argument_list|(
name|w
argument_list|,
name|h
argument_list|)
decl_stmt|;
name|Graphics2D
name|g
init|=
name|image
operator|.
name|createGraphics
argument_list|()
decl_stmt|;
name|icon
operator|.
name|paintIcon
argument_list|(
literal|null
argument_list|,
name|g
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|g
operator|.
name|dispose
argument_list|()
expr_stmt|;
return|return
name|image
return|;
block|}
block|}
return|;
block|}
DECL|method|getLabel ()
specifier|public
name|JLabel
name|getLabel
parameter_list|()
block|{
name|JLabel
name|label
init|=
operator|new
name|JLabel
argument_list|(
name|this
operator|.
name|code
argument_list|)
decl_stmt|;
name|label
operator|.
name|setForeground
argument_list|(
name|this
operator|.
name|color
argument_list|)
expr_stmt|;
name|label
operator|.
name|setFont
argument_list|(
name|FONT_16
argument_list|)
expr_stmt|;
return|return
name|label
return|;
block|}
block|}
DECL|class|FontBasedIcon
specifier|public
specifier|static
class|class
name|FontBasedIcon
implements|implements
name|Icon
block|{
DECL|field|iconCode
specifier|private
specifier|final
name|String
name|iconCode
decl_stmt|;
DECL|field|iconColor
specifier|private
specifier|final
name|Color
name|iconColor
decl_stmt|;
DECL|field|size
specifier|private
specifier|final
name|int
name|size
decl_stmt|;
DECL|method|FontBasedIcon (String code, Color iconColor)
specifier|public
name|FontBasedIcon
parameter_list|(
name|String
name|code
parameter_list|,
name|Color
name|iconColor
parameter_list|)
block|{
name|this
operator|.
name|iconCode
operator|=
name|code
expr_stmt|;
name|this
operator|.
name|iconColor
operator|=
name|iconColor
expr_stmt|;
name|this
operator|.
name|size
operator|=
literal|24
expr_stmt|;
block|}
DECL|method|FontBasedIcon (String code, Color iconColor, int size)
specifier|public
name|FontBasedIcon
parameter_list|(
name|String
name|code
parameter_list|,
name|Color
name|iconColor
parameter_list|,
name|int
name|size
parameter_list|)
block|{
name|this
operator|.
name|iconCode
operator|=
name|code
expr_stmt|;
name|this
operator|.
name|iconColor
operator|=
name|iconColor
expr_stmt|;
name|this
operator|.
name|size
operator|=
name|size
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|paintIcon (Component c, Graphics g, int x, int y)
specifier|public
name|void
name|paintIcon
parameter_list|(
name|Component
name|c
parameter_list|,
name|Graphics
name|g
parameter_list|,
name|int
name|x
parameter_list|,
name|int
name|y
parameter_list|)
block|{
name|Graphics2D
name|g2
init|=
operator|(
name|Graphics2D
operator|)
name|g
operator|.
name|create
argument_list|()
decl_stmt|;
name|RenderingHints
name|rh
init|=
operator|new
name|RenderingHints
argument_list|(
name|RenderingHints
operator|.
name|KEY_TEXT_ANTIALIASING
argument_list|,
name|RenderingHints
operator|.
name|VALUE_TEXT_ANTIALIAS_ON
argument_list|)
decl_stmt|;
name|g2
operator|.
name|setRenderingHints
argument_list|(
name|rh
argument_list|)
expr_stmt|;
name|g2
operator|.
name|setFont
argument_list|(
name|FONT
operator|.
name|deriveFont
argument_list|(
name|Font
operator|.
name|PLAIN
argument_list|,
name|size
argument_list|)
argument_list|)
expr_stmt|;
name|g2
operator|.
name|setColor
argument_list|(
name|iconColor
argument_list|)
expr_stmt|;
name|FontMetrics
name|fm
init|=
name|g2
operator|.
name|getFontMetrics
argument_list|()
decl_stmt|;
name|g2
operator|.
name|translate
argument_list|(
name|x
argument_list|,
name|y
operator|+
name|fm
operator|.
name|getAscent
argument_list|()
argument_list|)
expr_stmt|;
name|g2
operator|.
name|drawString
argument_list|(
name|iconCode
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|g2
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getIconWidth ()
specifier|public
name|int
name|getIconWidth
parameter_list|()
block|{
return|return
name|size
return|;
block|}
annotation|@
name|Override
DECL|method|getIconHeight ()
specifier|public
name|int
name|getIconHeight
parameter_list|()
block|{
return|return
name|size
return|;
block|}
block|}
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|IconTheme
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|KEY_TO_ICON
specifier|private
specifier|static
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|KEY_TO_ICON
init|=
name|readIconThemeFile
argument_list|(
name|IconTheme
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"/images/Icons.properties"
argument_list|)
argument_list|,
literal|"/images/external/"
argument_list|)
decl_stmt|;
DECL|field|DEFAULT_ICON_PATH
specifier|private
specifier|static
specifier|final
name|String
name|DEFAULT_ICON_PATH
init|=
literal|"/images/external/red.png"
decl_stmt|;
comment|/**      * Constructs an ImageIcon for the image representing the given function, in the resource      * file listing images.      *      * @param name The name of the icon, such as "open", "save", "saveAs" etc.      * @return The ImageIcon for the function.      */
DECL|method|getImage (String name)
specifier|public
specifier|static
name|ImageIcon
name|getImage
parameter_list|(
name|String
name|name
parameter_list|)
block|{
comment|//return JabRefIcon.values()[new Random().nextInt(JabRefIcon.values().length)].getImageIcon();
return|return
operator|new
name|ImageIcon
argument_list|(
name|getIconUrl
argument_list|(
name|name
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * Looks up the URL for the image representing the given function, in the resource      * file listing images.      *      * @param name The name of the icon, such as "open", "save", "saveAs" etc.      * @return The URL to the actual image to use.      */
DECL|method|getIconUrl (String name)
specifier|public
specifier|static
name|URL
name|getIconUrl
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|String
name|key
init|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|name
argument_list|,
literal|"icon name"
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|KEY_TO_ICON
operator|.
name|containsKey
argument_list|(
name|key
argument_list|)
condition|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"could not find icon url by name "
operator|+
name|name
operator|+
literal|", so falling back on default icon "
operator|+
name|DEFAULT_ICON_PATH
argument_list|)
expr_stmt|;
block|}
name|String
name|path
init|=
name|KEY_TO_ICON
operator|.
name|getOrDefault
argument_list|(
name|key
argument_list|,
name|DEFAULT_ICON_PATH
argument_list|)
decl_stmt|;
return|return
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|IconTheme
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|path
argument_list|)
argument_list|,
literal|"Path must not be null for key "
operator|+
name|key
argument_list|)
return|;
block|}
comment|/**      * Read a typical java property url into a Map. Currently doesn't support escaping      * of the '=' character - it simply looks for the first '=' to determine where the key ends.      * Both the key and the value is trimmed for whitespace at the ends.      *      * @param url    The URL to read information from.      * @param prefix A String to prefix to all values read. Can represent e.g. the directory      *               where icon files are to be found.      * @return A Map containing all key-value pairs found.      */
comment|// FIXME: prefix can be removed?!
DECL|method|readIconThemeFile (URL url, String prefix)
specifier|private
specifier|static
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|readIconThemeFile
parameter_list|(
name|URL
name|url
parameter_list|,
name|String
name|prefix
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|url
argument_list|,
literal|"url"
argument_list|)
expr_stmt|;
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|prefix
argument_list|,
literal|"prefix"
argument_list|)
expr_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|result
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
try|try
init|(
name|BufferedReader
name|in
init|=
operator|new
name|BufferedReader
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
name|url
operator|.
name|openStream
argument_list|()
argument_list|)
argument_list|)
init|)
block|{
name|String
name|line
decl_stmt|;
while|while
condition|(
operator|(
name|line
operator|=
name|in
operator|.
name|readLine
argument_list|()
operator|)
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
operator|!
name|line
operator|.
name|contains
argument_list|(
literal|"="
argument_list|)
condition|)
block|{
continue|continue;
block|}
name|int
name|index
init|=
name|line
operator|.
name|indexOf
argument_list|(
literal|"="
argument_list|)
decl_stmt|;
name|String
name|key
init|=
name|line
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|index
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
name|String
name|value
init|=
name|prefix
operator|+
name|line
operator|.
name|substring
argument_list|(
name|index
operator|+
literal|1
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
name|result
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
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Unable to read default icon theme."
argument_list|)
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
block|}
end_class

end_unit

