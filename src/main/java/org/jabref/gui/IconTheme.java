begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui
package|package
name|org
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
name|Color
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Component
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Font
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|FontFormatException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|FontMetrics
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Graphics
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Graphics2D
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|RenderingHints
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
name|InputStream
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
name|nio
operator|.
name|charset
operator|.
name|StandardCharsets
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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Objects
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|Icon
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|ImageIcon
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|Node
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|image
operator|.
name|Image
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|text
operator|.
name|Text
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|preferences
operator|.
name|JabRefPreferences
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
DECL|field|FX_FONT
specifier|public
specifier|static
name|javafx
operator|.
name|scene
operator|.
name|text
operator|.
name|Font
name|FX_FONT
decl_stmt|;
comment|/* Colors */
comment|// JabRef's default colors
DECL|field|DEFAULT_COLOR
specifier|public
specifier|static
specifier|final
name|Color
name|DEFAULT_COLOR
init|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
operator|.
name|getColor
argument_list|(
name|JabRefPreferences
operator|.
name|ICON_ENABLED_COLOR
argument_list|)
decl_stmt|;
DECL|field|DEFAULT_DISABLED_COLOR
specifier|public
specifier|static
specifier|final
name|Color
name|DEFAULT_DISABLED_COLOR
init|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
operator|.
name|getColor
argument_list|(
name|JabRefPreferences
operator|.
name|ICON_DISABLED_COLOR
argument_list|)
decl_stmt|;
comment|// Christmas edition
comment|//public static final Color DEFAULT_COLOR = new Color(0x155115);
comment|//public static final Color DEFAULT_DISABLED_COLOR = new Color(0x990000);
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
static|static
block|{
try|try
init|(
name|InputStream
name|stream
init|=
name|FontBasedIcon
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"/fonts/materialdesignicons-webfont.ttf"
argument_list|)
init|)
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
name|stream
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
try|try
init|(
name|InputStream
name|stream2
init|=
name|FontBasedIcon
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"/fonts/materialdesignicons-webfont.ttf"
argument_list|)
init|)
block|{
name|FX_FONT
operator|=
name|javafx
operator|.
name|scene
operator|.
name|text
operator|.
name|Font
operator|.
name|loadFont
argument_list|(
name|stream2
argument_list|,
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
operator|.
name|getInt
argument_list|(
name|JabRefPreferences
operator|.
name|ICON_SIZE_LARGE
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|FontFormatException
decl||
name|IOException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Error loading font"
argument_list|,
name|e
argument_list|)
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
literal|"\uf416"
argument_list|)
comment|/*css: mdi-plus-box*/
block|,
DECL|enumConstant|ADD_NOBOX
name|ADD_NOBOX
argument_list|(
literal|"\uf415"
argument_list|)
comment|/*css: plus */
block|,
DECL|enumConstant|ADD_ENTRY
name|ADD_ENTRY
argument_list|(
literal|"\uf527"
argument_list|)
comment|/*css: tooltip-outline-plus */
block|,
DECL|enumConstant|EDIT_ENTRY
name|EDIT_ENTRY
argument_list|(
literal|"\uf524"
argument_list|)
comment|/*css: tooltip-edit */
block|,
DECL|enumConstant|EDIT_STRINGS
name|EDIT_STRINGS
argument_list|(
literal|"\uf528"
argument_list|)
comment|/*css: tooltip-text */
block|,
DECL|enumConstant|FOLDER
name|FOLDER
argument_list|(
literal|"\uf24b"
argument_list|)
block|,
comment|/*css: folder */
DECL|enumConstant|REMOVE
name|REMOVE
argument_list|(
literal|"\uf375"
argument_list|)
block|,
comment|/*css: minus-box */
DECL|enumConstant|REMOVE_NOBOX
name|REMOVE_NOBOX
argument_list|(
literal|"\uf374"
argument_list|)
comment|/*css: minus */
block|,
DECL|enumConstant|FILE
name|FILE
argument_list|(
literal|"\uf214"
argument_list|)
block|,
comment|/* css: file*/
DECL|enumConstant|PDF_FILE
name|PDF_FILE
argument_list|(
literal|"\uf225"
argument_list|)
block|,
comment|/* css: file-pdf*/
DECL|enumConstant|DOI
name|DOI
argument_list|(
literal|"\uF072"
argument_list|)
comment|/*css: barcode-scan*/
block|,
DECL|enumConstant|DUPLICATE
name|DUPLICATE
argument_list|(
literal|"\uf191"
argument_list|)
comment|/*css: content-duplicate */
block|,
DECL|enumConstant|EDIT
name|EDIT
argument_list|(
literal|"\uf3eb"
argument_list|)
comment|/*css: pencil */
block|,
DECL|enumConstant|NEW
name|NEW
argument_list|(
literal|"\uf224"
argument_list|)
comment|/* css: file-outline */
block|,
DECL|enumConstant|SAVE
name|SAVE
argument_list|(
literal|"\uf193"
argument_list|)
comment|/*css: content-save*/
block|,
DECL|enumConstant|SAVE_ALL
name|SAVE_ALL
argument_list|(
literal|"\uf194"
argument_list|)
comment|/*css: content-save-all*/
block|,
DECL|enumConstant|CLOSE
name|CLOSE
argument_list|(
literal|"\uf156"
argument_list|)
comment|/* css: close */
block|,
DECL|enumConstant|PASTE
name|PASTE
argument_list|(
literal|"\uf192"
argument_list|)
comment|/*css: content-paste*/
block|,
DECL|enumConstant|CUT
name|CUT
argument_list|(
literal|"\uf190"
argument_list|)
comment|/*css: content-cut*/
block|,
DECL|enumConstant|COPY
name|COPY
argument_list|(
literal|"\uf18f"
argument_list|)
comment|/*css: content-copy */
block|,
DECL|enumConstant|COMMENT
name|COMMENT
argument_list|(
literal|"\uF188"
argument_list|)
comment|/*css: comment*/
block|,
DECL|enumConstant|REDO
name|REDO
argument_list|(
literal|"\uf44e"
argument_list|)
comment|/*css: redo*/
block|,
DECL|enumConstant|UNDO
name|UNDO
argument_list|(
literal|"\uf54c"
argument_list|)
comment|/*css: undo*/
block|,
DECL|enumConstant|MARK_ENTRIES
name|MARK_ENTRIES
argument_list|(
literal|"\uf0c0"
argument_list|)
comment|/*css: bookmark */
block|,
DECL|enumConstant|MARKER
name|MARKER
argument_list|(
literal|"\uF524"
argument_list|)
comment|/*css: marker */
block|,
DECL|enumConstant|UNMARK_ENTRIES
name|UNMARK_ENTRIES
argument_list|(
literal|"\uf0c3"
argument_list|)
comment|/*css: bookmark-outline */
block|,
DECL|enumConstant|REFRESH
name|REFRESH
argument_list|(
literal|"\uf450"
argument_list|)
comment|/*css: refresh */
block|,
DECL|enumConstant|DELETE_ENTRY
name|DELETE_ENTRY
argument_list|(
literal|"\uf1c0"
argument_list|)
comment|/*css: delete */
block|,
DECL|enumConstant|SEARCH
name|SEARCH
argument_list|(
literal|"\uf349"
argument_list|)
comment|/*css: magnify */
block|,
DECL|enumConstant|PREFERENCES
name|PREFERENCES
argument_list|(
literal|"\uf493"
argument_list|)
comment|/*css: settings */
block|,
DECL|enumConstant|HELP
name|HELP
argument_list|(
literal|"\uf2d7"
argument_list|)
comment|/*css: help-circle*/
block|,
DECL|enumConstant|UP
name|UP
argument_list|(
literal|"\uf143"
argument_list|)
comment|/*css: chevron-up */
block|,
DECL|enumConstant|DOWN
name|DOWN
argument_list|(
literal|"\uf140"
argument_list|)
comment|/*css: chevron-down */
block|,
DECL|enumConstant|LEFT
name|LEFT
argument_list|(
literal|"\uf04e"
argument_list|)
comment|/* css; arrow-left-bold */
block|,
DECL|enumConstant|RIGHT
name|RIGHT
argument_list|(
literal|"\uf055"
argument_list|)
comment|/*css: arrow-right-bold */
block|,
DECL|enumConstant|SOURCE
name|SOURCE
argument_list|(
literal|"\uf169"
argument_list|)
comment|/*css: code-braces*/
block|,
DECL|enumConstant|MAKE_KEY
name|MAKE_KEY
argument_list|(
literal|"\uf30b"
argument_list|)
comment|/*css: key-variant */
block|,
DECL|enumConstant|CLEANUP_ENTRIES
name|CLEANUP_ENTRIES
argument_list|(
literal|"\uf0e2"
argument_list|)
comment|/*css: broom */
block|,
DECL|enumConstant|PRIORITY
name|PRIORITY
argument_list|(
literal|"\uf23b"
argument_list|)
comment|/*css: flag */
block|,
DECL|enumConstant|PRIORITY_HIGH
name|PRIORITY_HIGH
argument_list|(
literal|"\uF23B"
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
literal|"\uF23B"
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
literal|"\uF23B"
argument_list|,
operator|new
name|Color
argument_list|(
literal|111
argument_list|,
literal|204
argument_list|,
literal|117
argument_list|)
argument_list|)
comment|/*css: flag */
block|,
DECL|enumConstant|PRINTED
name|PRINTED
argument_list|(
literal|"\uf42a"
argument_list|)
comment|/*css: printer */
block|,
DECL|enumConstant|RANKING
name|RANKING
argument_list|(
literal|"\uf4ce"
argument_list|)
comment|/*css: star + star-outline: f4d2*/
block|,
DECL|enumConstant|RANK1
name|RANK1
argument_list|(
literal|"\uF4CE\uF4D2\uF4D2\uF4D2\uf4d2"
argument_list|)
block|,
DECL|enumConstant|RANK2
name|RANK2
argument_list|(
literal|"\uF4CE\uF4CE\uF4D2\uF4D2\uF4D2"
argument_list|)
block|,
DECL|enumConstant|RANK3
name|RANK3
argument_list|(
literal|"\uF4CE\uF4CE\uF4CE\uF4D2\uF4D2"
argument_list|)
block|,
DECL|enumConstant|RANK4
name|RANK4
argument_list|(
literal|"\uF4CE\uF4CE\uF4CE\uF4CE\uF4D2"
argument_list|)
block|,
DECL|enumConstant|RANK5
name|RANK5
argument_list|(
literal|"\uF4CE\uF4CE\uF4CE\uF4CE\uF4CE"
argument_list|)
block|,
DECL|enumConstant|WWW
name|WWW
argument_list|(
literal|"\uf59f"
argument_list|)
comment|/*css: web*/
block|,
DECL|enumConstant|GROUP_INCLUDING
name|GROUP_INCLUDING
argument_list|(
literal|"\uf233"
argument_list|)
comment|/*css: filter-outline*/
block|,
DECL|enumConstant|GROUP_REFINING
name|GROUP_REFINING
argument_list|(
literal|"\uf232"
argument_list|)
comment|/*css: filter*/
block|,
DECL|enumConstant|AUTO_GROUP
name|AUTO_GROUP
argument_list|(
literal|"\uf068"
argument_list|)
block|,
comment|/*css: auto-fix*/
DECL|enumConstant|EMAIL
name|EMAIL
argument_list|(
literal|"\uf1ee"
argument_list|)
comment|/*css: email*/
block|,
DECL|enumConstant|EXPORT_TO_CLIPBOARD
name|EXPORT_TO_CLIPBOARD
argument_list|(
literal|"\uf14b"
argument_list|)
comment|/*css: clipboard-arrow-left */
block|,
DECL|enumConstant|ATTACH_FILE
name|ATTACH_FILE
argument_list|(
literal|"\uf3e2"
argument_list|)
comment|/*css: paperclip*/
block|,
DECL|enumConstant|AUTO_FILE_LINK
name|AUTO_FILE_LINK
argument_list|(
literal|"\uf21e"
argument_list|)
comment|/*css: file-find */
block|,
DECL|enumConstant|QUALITY_ASSURED
name|QUALITY_ASSURED
argument_list|(
literal|"\uf124"
argument_list|)
block|,
comment|/*css: certificate */
DECL|enumConstant|QUALITY
name|QUALITY
argument_list|(
literal|"\uF124"
argument_list|)
block|,
comment|/*css: certificate */
DECL|enumConstant|OPEN
name|OPEN
argument_list|(
literal|"\uf24b"
argument_list|)
comment|/*css: folder */
block|,
DECL|enumConstant|ADD_ROW
name|ADD_ROW
argument_list|(
literal|"\uf490"
argument_list|)
comment|/* css: server-plus*/
block|,
DECL|enumConstant|REMOVE_ROW
name|REMOVE_ROW
argument_list|(
literal|"\uf48c"
argument_list|)
comment|/*css: server-minus */
block|,
DECL|enumConstant|PICTURE
name|PICTURE
argument_list|(
literal|"\uf21f"
argument_list|)
comment|/*css: file-image */
block|,
DECL|enumConstant|READ_STATUS_READ
name|READ_STATUS_READ
argument_list|(
literal|"\uf208"
argument_list|,
operator|new
name|Color
argument_list|(
literal|111
argument_list|,
literal|204
argument_list|,
literal|117
argument_list|)
argument_list|)
block|,
comment|/*css: eye */
DECL|enumConstant|READ_STATUS_SKIMMED
name|READ_STATUS_SKIMMED
argument_list|(
literal|"\uF208"
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
literal|"\uF208"
argument_list|)
block|,
comment|/*css: eye */
DECL|enumConstant|RELEVANCE
name|RELEVANCE
argument_list|(
literal|"\uf4cf"
argument_list|)
block|,
comment|/*css: star-circle */
DECL|enumConstant|MERGE_ENTRIES
name|MERGE_ENTRIES
argument_list|(
literal|"\uf18a"
argument_list|)
block|,
comment|/* css: compare */
DECL|enumConstant|CONNECT_OPEN_OFFICE
name|CONNECT_OPEN_OFFICE
argument_list|(
literal|"\uf3cb"
argument_list|)
comment|/*css: open-in-app */
block|,
DECL|enumConstant|PLAIN_TEXT_IMPORT_TODO
name|PLAIN_TEXT_IMPORT_TODO
argument_list|(
literal|"\uf130"
argument_list|)
comment|/* css: checkbox-blank-circle-outline*/
block|,
DECL|enumConstant|PLAIN_TEXT_IMPORT_DONE
name|PLAIN_TEXT_IMPORT_DONE
argument_list|(
literal|"\uf134"
argument_list|)
comment|/* checkbox-marked-circle-outline */
block|,
DECL|enumConstant|DONATE
name|DONATE
argument_list|(
literal|"\uf2a1"
argument_list|)
block|,
comment|/* css: gift */
DECL|enumConstant|MOVE_TAB_ARROW
name|MOVE_TAB_ARROW
argument_list|(
literal|"\uf05e"
argument_list|)
block|,
comment|/*css:  arrow-up-bold */
DECL|enumConstant|OPTIONAL
name|OPTIONAL
argument_list|(
literal|"\uf316"
argument_list|)
block|,
comment|/*css: label-outline */
DECL|enumConstant|REQUIRED
name|REQUIRED
argument_list|(
literal|"\uf315"
argument_list|)
block|,
comment|/*css: label */
DECL|enumConstant|INTEGRITY_FAIL
name|INTEGRITY_FAIL
argument_list|(
literal|"\uf159"
argument_list|,
name|Color
operator|.
name|RED
argument_list|)
block|,
comment|/*css: close-circle */
DECL|enumConstant|INTEGRITY_INFO
name|INTEGRITY_INFO
argument_list|(
literal|"\uf2fc"
argument_list|)
block|,
comment|/*css: information */
DECL|enumConstant|INTEGRITY_WARN
name|INTEGRITY_WARN
argument_list|(
literal|"\uf028"
argument_list|)
block|,
comment|/*css alert-circle */
DECL|enumConstant|INTEGRITY_SUCCESS
name|INTEGRITY_SUCCESS
argument_list|(
literal|"\uF134"
argument_list|)
comment|/*css: checkbox-marked-circle-outline */
block|,
DECL|enumConstant|GITHUB
name|GITHUB
argument_list|(
literal|"\uf2a4"
argument_list|)
block|,
comment|/*css: github-circle*/
DECL|enumConstant|TOGGLE_ENTRY_PREVIEW
name|TOGGLE_ENTRY_PREVIEW
argument_list|(
literal|"\uf332"
argument_list|)
block|,
comment|/*css: library-books */
DECL|enumConstant|TOGGLE_GROUPS
name|TOGGLE_GROUPS
argument_list|(
literal|"\uf572"
argument_list|)
block|,
comment|/*css: view-list */
DECL|enumConstant|WRITE_XMP
name|WRITE_XMP
argument_list|(
literal|"\uf2fa"
argument_list|)
block|,
comment|/* css: import */
DECL|enumConstant|FILE_WORD
name|FILE_WORD
argument_list|(
literal|"\uf22c"
argument_list|)
block|,
comment|/*css: file-word */
DECL|enumConstant|FILE_EXCEL
name|FILE_EXCEL
argument_list|(
literal|"\uf21b"
argument_list|)
block|,
comment|/*css: file-excel */
DECL|enumConstant|FILE_POWERPOINT
name|FILE_POWERPOINT
argument_list|(
literal|"\uf227"
argument_list|)
block|,
comment|/*css: file-powerpoint */
DECL|enumConstant|FILE_TEXT
name|FILE_TEXT
argument_list|(
literal|"\uf219"
argument_list|)
block|,
comment|/*css: file-document */
DECL|enumConstant|FILE_MULTIPLE
name|FILE_MULTIPLE
argument_list|(
literal|"\uf222"
argument_list|)
block|,
comment|/*css: file-multiple */
DECL|enumConstant|KEY_BINDINGS
name|KEY_BINDINGS
argument_list|(
literal|"\uf30c"
argument_list|)
block|,
comment|/*css: keyboard */
DECL|enumConstant|FIND_DUPLICATES
name|FIND_DUPLICATES
argument_list|(
literal|"\uf16b"
argument_list|)
block|,
comment|/*css: code-equal */
DECL|enumConstant|PULL
name|PULL
argument_list|(
literal|"\uf4c2"
argument_list|)
block|,
comment|/*source-pull*/
DECL|enumConstant|OPEN_IN_NEW_WINDOW
name|OPEN_IN_NEW_WINDOW
argument_list|(
literal|"\uf3cc"
argument_list|)
block|,
comment|/*css: open-in-new */
DECL|enumConstant|CASE_SENSITIVE
name|CASE_SENSITIVE
argument_list|(
literal|"\uf02c"
argument_list|)
block|,
comment|/* css: mdi-alphabetical */
DECL|enumConstant|REG_EX
name|REG_EX
argument_list|(
literal|"\uf451"
argument_list|)
block|,
comment|/*css: mdi-regex */
DECL|enumConstant|CONSOLE
name|CONSOLE
argument_list|(
literal|"\uf18d"
argument_list|)
block|,
comment|/*css: console */
DECL|enumConstant|FORUM
name|FORUM
argument_list|(
literal|"\uf28c"
argument_list|)
block|,
comment|/* css: forum */
DECL|enumConstant|FACEBOOK
name|FACEBOOK
argument_list|(
literal|"\uf20c"
argument_list|)
block|,
comment|/* css: facebook */
DECL|enumConstant|BLOG
name|BLOG
argument_list|(
literal|"\uf46b"
argument_list|)
block|,
comment|/* css: rss */
DECL|enumConstant|GLOBAL_SEARCH
name|GLOBAL_SEARCH
argument_list|(
literal|"\uF1E7"
argument_list|)
block|,
comment|/* css: earth */
DECL|enumConstant|DATE_PICKER
name|DATE_PICKER
argument_list|(
literal|"\uF0ED;"
argument_list|)
block|,
comment|/* css: calendar */
comment|// STILL MISSING:
DECL|enumConstant|GROUP_REGULAR
name|GROUP_REGULAR
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
name|IconTheme
operator|.
name|DEFAULT_COLOR
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
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
operator|.
name|getInt
argument_list|(
name|JabRefPreferences
operator|.
name|ICON_SIZE_SMALL
argument_list|)
argument_list|)
return|;
block|}
DECL|method|getGraphicNode ()
specifier|public
name|Node
name|getGraphicNode
parameter_list|()
block|{
name|Text
name|graphic
init|=
operator|new
name|Text
argument_list|(
name|this
operator|.
name|code
argument_list|)
decl_stmt|;
name|graphic
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"icon"
argument_list|)
expr_stmt|;
return|return
name|graphic
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
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
operator|.
name|getInt
argument_list|(
name|JabRefPreferences
operator|.
name|ICON_SIZE_LARGE
argument_list|)
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
DECL|method|createDisabledIcon ()
specifier|public
name|FontBasedIcon
name|createDisabledIcon
parameter_list|()
block|{
return|return
operator|new
name|FontBasedIcon
argument_list|(
name|this
operator|.
name|iconCode
argument_list|,
name|IconTheme
operator|.
name|DEFAULT_DISABLED_COLOR
argument_list|,
name|this
operator|.
name|size
argument_list|)
return|;
block|}
DECL|method|createSmallIcon ()
specifier|public
name|FontBasedIcon
name|createSmallIcon
parameter_list|()
block|{
return|return
operator|new
name|FontBasedIcon
argument_list|(
name|this
operator|.
name|iconCode
argument_list|,
name|this
operator|.
name|iconColor
argument_list|,
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
operator|.
name|getInt
argument_list|(
name|JabRefPreferences
operator|.
name|ICON_SIZE_SMALL
argument_list|)
argument_list|)
return|;
block|}
DECL|method|createWithNewColor (Color newColor)
specifier|public
name|FontBasedIcon
name|createWithNewColor
parameter_list|(
name|Color
name|newColor
parameter_list|)
block|{
return|return
operator|new
name|FontBasedIcon
argument_list|(
name|this
operator|.
name|iconCode
argument_list|,
name|newColor
argument_list|,
name|this
operator|.
name|size
argument_list|)
return|;
block|}
block|}
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
DECL|method|getJabRefImageFX ()
specifier|public
specifier|static
name|Image
name|getJabRefImageFX
parameter_list|()
block|{
return|return
name|getImageFX
argument_list|(
literal|"jabrefIcon48"
argument_list|)
return|;
block|}
comment|/**      * Constructs an {@link Image} for the image representing the given function, in the resource      * file listing images.      *      * @param name The name of the icon, such as "open", "save", "saveAs" etc.      * @return The {@link Image} for the function.      */
DECL|method|getImageFX (String name)
specifier|public
specifier|static
name|Image
name|getImageFX
parameter_list|(
name|String
name|name
parameter_list|)
block|{
return|return
operator|new
name|Image
argument_list|(
name|getIconUrl
argument_list|(
name|name
argument_list|)
operator|.
name|toString
argument_list|()
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
literal|"Could not find icon url by name "
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
argument_list|,
name|StandardCharsets
operator|.
name|ISO_8859_1
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
literal|'='
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
literal|"Unable to read default icon theme."
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
