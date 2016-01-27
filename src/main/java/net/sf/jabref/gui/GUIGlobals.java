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
name|Color
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Dimension
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
name|javax
operator|.
name|swing
operator|.
name|JLabel
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
name|org
operator|.
name|xnap
operator|.
name|commons
operator|.
name|gui
operator|.
name|shortcut
operator|.
name|EmacsKeyBindings
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
name|Globals
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
name|external
operator|.
name|ExternalFileType
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
name|external
operator|.
name|ExternalFileTypes
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
name|help
operator|.
name|AboutDialog
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
name|specialfields
operator|.
name|Printed
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
name|specialfields
operator|.
name|Priority
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
name|specialfields
operator|.
name|Quality
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
name|specialfields
operator|.
name|Rank
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
name|specialfields
operator|.
name|ReadStatus
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
name|specialfields
operator|.
name|Relevance
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
name|specialfields
operator|.
name|SpecialFieldsUtils
import|;
end_import

begin_comment
comment|/**  * Static variables for graphics files and keyboard shortcuts.  */
end_comment

begin_class
DECL|class|GUIGlobals
specifier|public
class|class
name|GUIGlobals
block|{
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
name|GUIGlobals
operator|.
name|class
argument_list|)
decl_stmt|;
comment|// Frame titles.
DECL|field|frameTitle
specifier|public
specifier|static
specifier|final
name|String
name|frameTitle
init|=
literal|"JabRef"
decl_stmt|;
DECL|field|stringsTitle
specifier|public
specifier|static
specifier|final
name|String
name|stringsTitle
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Strings for database"
argument_list|)
decl_stmt|;
DECL|field|untitledTitle
specifier|public
specifier|static
specifier|final
name|String
name|untitledTitle
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"untitled"
argument_list|)
decl_stmt|;
DECL|field|NUMBER_COL
specifier|public
specifier|static
specifier|final
name|String
name|NUMBER_COL
init|=
literal|"#"
decl_stmt|;
DECL|field|CURRENTFONT
specifier|public
specifier|static
name|Font
name|CURRENTFONT
decl_stmt|;
DECL|field|typeNameFont
specifier|public
specifier|static
name|Font
name|typeNameFont
decl_stmt|;
DECL|field|aboutSize
specifier|public
specifier|static
specifier|final
name|Dimension
name|aboutSize
init|=
operator|new
name|Dimension
argument_list|(
literal|600
argument_list|,
literal|265
argument_list|)
decl_stmt|;
DECL|field|zoomLevel
specifier|public
specifier|static
name|Double
name|zoomLevel
init|=
literal|1.0
decl_stmt|;
comment|// Divider size for BaseFrame split pane. 0 means non-resizable.
DECL|field|SPLIT_PANE_DIVIDER_SIZE
specifier|public
specifier|static
specifier|final
name|int
name|SPLIT_PANE_DIVIDER_SIZE
init|=
literal|4
decl_stmt|;
DECL|field|SPLIT_PANE_DIVIDER_LOCATION
specifier|public
specifier|static
specifier|final
name|int
name|SPLIT_PANE_DIVIDER_LOCATION
init|=
literal|145
operator|+
literal|15
decl_stmt|;
comment|// + 15 for possible scrollbar.
DECL|field|TABLE_ROW_PADDING
specifier|public
specifier|static
specifier|final
name|int
name|TABLE_ROW_PADDING
init|=
literal|9
decl_stmt|;
DECL|field|KEYBIND_COL_0
specifier|public
specifier|static
specifier|final
name|int
name|KEYBIND_COL_0
init|=
literal|200
decl_stmt|;
DECL|field|KEYBIND_COL_1
specifier|public
specifier|static
specifier|final
name|int
name|KEYBIND_COL_1
init|=
literal|80
decl_stmt|;
comment|// Added to the font size when determining table
DECL|field|MAX_CONTENT_SELECTOR_WIDTH
specifier|public
specifier|static
specifier|final
name|int
name|MAX_CONTENT_SELECTOR_WIDTH
init|=
literal|240
decl_stmt|;
comment|// The max width of the combobox for content selectors.
comment|// Filenames.
DECL|field|backupExt
specifier|public
specifier|static
specifier|final
name|String
name|backupExt
init|=
literal|".bak"
decl_stmt|;
comment|// Image paths.
DECL|field|imageSize
specifier|private
specifier|static
specifier|final
name|String
name|imageSize
init|=
literal|"24"
decl_stmt|;
DECL|field|extension
specifier|private
specifier|static
specifier|final
name|String
name|extension
init|=
literal|".gif"
decl_stmt|;
DECL|field|ex
specifier|public
specifier|static
name|String
name|ex
init|=
name|GUIGlobals
operator|.
name|imageSize
operator|+
name|GUIGlobals
operator|.
name|extension
decl_stmt|;
DECL|field|pre
specifier|public
specifier|static
name|String
name|pre
init|=
literal|"/images/"
decl_stmt|;
DECL|field|tableIcons
specifier|private
specifier|static
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|JLabel
argument_list|>
name|tableIcons
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
comment|// Contains table icon mappings. Set up
comment|// further below.
DECL|field|activeEditor
specifier|public
specifier|static
specifier|final
name|Color
name|activeEditor
init|=
operator|new
name|Color
argument_list|(
literal|230
argument_list|,
literal|230
argument_list|,
literal|255
argument_list|)
decl_stmt|;
DECL|field|sidePaneManager
specifier|public
specifier|static
name|SidePaneManager
name|sidePaneManager
decl_stmt|;
DECL|field|helpDiag
specifier|public
specifier|static
name|AboutDialog
name|helpDiag
decl_stmt|;
comment|//	Colors.
DECL|field|entryEditorLabelColor
specifier|public
specifier|static
specifier|final
name|Color
name|entryEditorLabelColor
init|=
operator|new
name|Color
argument_list|(
literal|100
argument_list|,
literal|100
argument_list|,
literal|150
argument_list|)
decl_stmt|;
comment|// Empty field, blue.
DECL|field|nullFieldColor
specifier|public
specifier|static
specifier|final
name|Color
name|nullFieldColor
init|=
operator|new
name|Color
argument_list|(
literal|75
argument_list|,
literal|130
argument_list|,
literal|95
argument_list|)
decl_stmt|;
comment|// Valid field, green.
DECL|field|activeTabbed
specifier|public
specifier|static
specifier|final
name|Color
name|activeTabbed
init|=
name|GUIGlobals
operator|.
name|entryEditorLabelColor
operator|.
name|darker
argument_list|()
decl_stmt|;
comment|// active Database (JTabbedPane)
DECL|field|inActiveTabbed
specifier|public
specifier|static
specifier|final
name|Color
name|inActiveTabbed
init|=
name|Color
operator|.
name|black
decl_stmt|;
comment|// inactive Database
DECL|field|infoField
specifier|public
specifier|static
specifier|final
name|Color
name|infoField
init|=
operator|new
name|Color
argument_list|(
literal|254
argument_list|,
literal|255
argument_list|,
literal|225
argument_list|)
decl_stmt|;
comment|// color for an info field
DECL|field|editorTextColor
specifier|public
specifier|static
name|Color
name|editorTextColor
decl_stmt|;
DECL|field|validFieldBackgroundColor
specifier|public
specifier|static
name|Color
name|validFieldBackgroundColor
decl_stmt|;
DECL|field|activeBackground
specifier|public
specifier|static
name|Color
name|activeBackground
decl_stmt|;
DECL|field|invalidFieldBackgroundColor
specifier|public
specifier|static
name|Color
name|invalidFieldBackgroundColor
decl_stmt|;
comment|// some fieldname constants
DECL|field|DEFAULT_FIELD_WEIGHT
specifier|public
specifier|static
specifier|final
name|double
name|DEFAULT_FIELD_WEIGHT
init|=
literal|1
decl_stmt|;
DECL|field|MAX_FIELD_WEIGHT
specifier|public
specifier|static
specifier|final
name|double
name|MAX_FIELD_WEIGHT
init|=
literal|2
decl_stmt|;
comment|// constants for editor types:
DECL|field|STANDARD_EDITOR
specifier|public
specifier|static
specifier|final
name|int
name|STANDARD_EDITOR
init|=
literal|1
decl_stmt|;
DECL|field|FILE_LIST_EDITOR
specifier|public
specifier|static
specifier|final
name|int
name|FILE_LIST_EDITOR
init|=
literal|2
decl_stmt|;
DECL|field|MAX_BACK_HISTORY_SIZE
specifier|public
specifier|static
specifier|final
name|int
name|MAX_BACK_HISTORY_SIZE
init|=
literal|10
decl_stmt|;
comment|// The maximum number of "Back" operations stored.
DECL|field|SMALL_W
specifier|public
specifier|static
specifier|final
name|double
name|SMALL_W
init|=
literal|0.30
decl_stmt|;
DECL|field|MEDIUM_W
specifier|public
specifier|static
specifier|final
name|double
name|MEDIUM_W
init|=
literal|0.5
decl_stmt|;
DECL|field|LARGE_W
specifier|public
specifier|static
specifier|final
name|double
name|LARGE_W
init|=
literal|1.5
decl_stmt|;
DECL|field|PE_HEIGHT
specifier|public
specifier|static
specifier|final
name|double
name|PE_HEIGHT
init|=
literal|2
decl_stmt|;
comment|//	Size constants for EntryTypeForm; small, medium and large.
DECL|field|FORM_WIDTH
specifier|public
specifier|static
specifier|final
name|int
index|[]
name|FORM_WIDTH
init|=
operator|new
name|int
index|[]
block|{
literal|500
block|,
literal|650
block|,
literal|820
block|}
decl_stmt|;
DECL|field|FORM_HEIGHT
specifier|public
specifier|static
specifier|final
name|int
index|[]
name|FORM_HEIGHT
init|=
operator|new
name|int
index|[]
block|{
literal|90
block|,
literal|110
block|,
literal|130
block|}
decl_stmt|;
comment|//	Constants controlling formatted bibtex output.
DECL|field|INDENT
specifier|public
specifier|static
specifier|final
name|int
name|INDENT
init|=
literal|4
decl_stmt|;
DECL|field|LINE_LENGTH
specifier|public
specifier|static
specifier|final
name|int
name|LINE_LENGTH
init|=
literal|65
decl_stmt|;
comment|// Maximum
DECL|field|DEFAULT_FIELD_LENGTH
specifier|public
specifier|static
specifier|final
name|int
name|DEFAULT_FIELD_LENGTH
init|=
literal|100
decl_stmt|;
DECL|field|NUMBER_COL_LENGTH
specifier|public
specifier|static
specifier|final
name|int
name|NUMBER_COL_LENGTH
init|=
literal|32
decl_stmt|;
DECL|field|WIDTH_ICON_COL_RANKING
specifier|public
specifier|static
specifier|final
name|int
name|WIDTH_ICON_COL_RANKING
init|=
literal|80
decl_stmt|;
comment|// Width of Ranking Icon Column
DECL|field|WIDTH_ICON_COL
specifier|public
specifier|static
specifier|final
name|int
name|WIDTH_ICON_COL
init|=
literal|26
decl_stmt|;
comment|// Column widths for export customization dialog table:
DECL|field|EXPORT_DIALOG_COL_0_WIDTH
specifier|public
specifier|static
specifier|final
name|int
name|EXPORT_DIALOG_COL_0_WIDTH
init|=
literal|50
decl_stmt|;
DECL|field|EXPORT_DIALOG_COL_1_WIDTH
specifier|public
specifier|static
specifier|final
name|int
name|EXPORT_DIALOG_COL_1_WIDTH
init|=
literal|200
decl_stmt|;
DECL|field|EXPORT_DIALOG_COL_2_WIDTH
specifier|public
specifier|static
specifier|final
name|int
name|EXPORT_DIALOG_COL_2_WIDTH
init|=
literal|30
decl_stmt|;
comment|// Column widths for import customization dialog table:
DECL|field|IMPORT_DIALOG_COL_0_WIDTH
specifier|public
specifier|static
specifier|final
name|int
name|IMPORT_DIALOG_COL_0_WIDTH
init|=
literal|200
decl_stmt|;
DECL|field|IMPORT_DIALOG_COL_1_WIDTH
specifier|public
specifier|static
specifier|final
name|int
name|IMPORT_DIALOG_COL_1_WIDTH
init|=
literal|80
decl_stmt|;
DECL|field|IMPORT_DIALOG_COL_2_WIDTH
specifier|public
specifier|static
specifier|final
name|int
name|IMPORT_DIALOG_COL_2_WIDTH
init|=
literal|200
decl_stmt|;
DECL|field|IMPORT_DIALOG_COL_3_WIDTH
specifier|public
specifier|static
specifier|final
name|int
name|IMPORT_DIALOG_COL_3_WIDTH
init|=
literal|200
decl_stmt|;
static|static
block|{
comment|// Set up entry editor colors, first time:
name|GUIGlobals
operator|.
name|updateEntryEditorColors
argument_list|()
expr_stmt|;
block|}
DECL|method|getTableIcon (String fieldType)
specifier|public
specifier|static
name|JLabel
name|getTableIcon
parameter_list|(
name|String
name|fieldType
parameter_list|)
block|{
name|Object
name|o
init|=
name|GUIGlobals
operator|.
name|tableIcons
operator|.
name|get
argument_list|(
name|fieldType
argument_list|)
decl_stmt|;
if|if
condition|(
name|o
operator|==
literal|null
condition|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Error: no table icon defined for type '"
operator|+
name|fieldType
operator|+
literal|"'."
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
else|else
block|{
return|return
operator|(
name|JLabel
operator|)
name|o
return|;
block|}
block|}
DECL|method|updateEntryEditorColors ()
specifier|public
specifier|static
name|void
name|updateEntryEditorColors
parameter_list|()
block|{
name|GUIGlobals
operator|.
name|activeBackground
operator|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
operator|.
name|getColor
argument_list|(
name|JabRefPreferences
operator|.
name|ACTIVE_FIELD_EDITOR_BACKGROUND_COLOR
argument_list|)
expr_stmt|;
name|GUIGlobals
operator|.
name|validFieldBackgroundColor
operator|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
operator|.
name|getColor
argument_list|(
name|JabRefPreferences
operator|.
name|VALID_FIELD_BACKGROUND_COLOR
argument_list|)
expr_stmt|;
name|GUIGlobals
operator|.
name|invalidFieldBackgroundColor
operator|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
operator|.
name|getColor
argument_list|(
name|JabRefPreferences
operator|.
name|INVALID_FIELD_BACKGROUND_COLOR
argument_list|)
expr_stmt|;
name|GUIGlobals
operator|.
name|editorTextColor
operator|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
operator|.
name|getColor
argument_list|(
name|JabRefPreferences
operator|.
name|FIELD_EDITOR_TEXT_COLOR
argument_list|)
expr_stmt|;
block|}
comment|/**      * Perform initializations that are only used in graphical mode. This is to prevent      * the "Xlib: connection to ":0.0" refused by server" error when access to the X server      * on Un*x is unavailable.      */
DECL|method|init ()
specifier|public
specifier|static
name|void
name|init
parameter_list|()
block|{
name|GUIGlobals
operator|.
name|typeNameFont
operator|=
operator|new
name|Font
argument_list|(
literal|"dialog"
argument_list|,
name|Font
operator|.
name|ITALIC
operator|+
name|Font
operator|.
name|BOLD
argument_list|,
literal|18
argument_list|)
expr_stmt|;
name|JLabel
name|label
decl_stmt|;
name|label
operator|=
operator|new
name|JLabel
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|PDF_FILE
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
expr_stmt|;
name|label
operator|.
name|setToolTipText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Open"
argument_list|)
operator|+
literal|" PDF"
argument_list|)
expr_stmt|;
name|GUIGlobals
operator|.
name|tableIcons
operator|.
name|put
argument_list|(
literal|"pdf"
argument_list|,
name|label
argument_list|)
expr_stmt|;
name|label
operator|=
operator|new
name|JLabel
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|WWW
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
expr_stmt|;
name|label
operator|.
name|setToolTipText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Open"
argument_list|)
operator|+
literal|" URL"
argument_list|)
expr_stmt|;
name|GUIGlobals
operator|.
name|tableIcons
operator|.
name|put
argument_list|(
literal|"url"
argument_list|,
name|label
argument_list|)
expr_stmt|;
name|label
operator|=
operator|new
name|JLabel
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|WWW
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
expr_stmt|;
name|label
operator|.
name|setToolTipText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Open"
argument_list|)
operator|+
literal|" CiteSeer URL"
argument_list|)
expr_stmt|;
name|GUIGlobals
operator|.
name|tableIcons
operator|.
name|put
argument_list|(
literal|"citeseerurl"
argument_list|,
name|label
argument_list|)
expr_stmt|;
name|label
operator|=
operator|new
name|JLabel
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|WWW
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
expr_stmt|;
name|label
operator|.
name|setToolTipText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Open"
argument_list|)
operator|+
literal|" ArXiv URL"
argument_list|)
expr_stmt|;
name|GUIGlobals
operator|.
name|tableIcons
operator|.
name|put
argument_list|(
literal|"eprint"
argument_list|,
name|label
argument_list|)
expr_stmt|;
name|label
operator|=
operator|new
name|JLabel
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|WWW
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
expr_stmt|;
name|label
operator|.
name|setToolTipText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Open"
argument_list|)
operator|+
literal|" DOI "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"web link"
argument_list|)
argument_list|)
expr_stmt|;
name|GUIGlobals
operator|.
name|tableIcons
operator|.
name|put
argument_list|(
literal|"doi"
argument_list|,
name|label
argument_list|)
expr_stmt|;
name|label
operator|=
operator|new
name|JLabel
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|FILE
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
expr_stmt|;
name|label
operator|.
name|setToolTipText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Open"
argument_list|)
operator|+
literal|" PS"
argument_list|)
expr_stmt|;
name|GUIGlobals
operator|.
name|tableIcons
operator|.
name|put
argument_list|(
literal|"ps"
argument_list|,
name|label
argument_list|)
expr_stmt|;
name|label
operator|=
operator|new
name|JLabel
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|FOLDER
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
expr_stmt|;
name|label
operator|.
name|setToolTipText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Open folder"
argument_list|)
argument_list|)
expr_stmt|;
name|GUIGlobals
operator|.
name|tableIcons
operator|.
name|put
argument_list|(
name|Globals
operator|.
name|FOLDER_FIELD
argument_list|,
name|label
argument_list|)
expr_stmt|;
name|label
operator|=
operator|new
name|JLabel
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|FILE
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
expr_stmt|;
name|label
operator|.
name|setToolTipText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Open file"
argument_list|)
argument_list|)
expr_stmt|;
name|GUIGlobals
operator|.
name|tableIcons
operator|.
name|put
argument_list|(
name|Globals
operator|.
name|FILE_FIELD
argument_list|,
name|label
argument_list|)
expr_stmt|;
for|for
control|(
name|ExternalFileType
name|fileType
range|:
name|ExternalFileTypes
operator|.
name|getInstance
argument_list|()
operator|.
name|getExternalFileTypeSelection
argument_list|()
control|)
block|{
name|label
operator|=
operator|new
name|JLabel
argument_list|(
name|fileType
operator|.
name|getIcon
argument_list|()
argument_list|)
expr_stmt|;
name|label
operator|.
name|setToolTipText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Open %0 file"
argument_list|,
name|fileType
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|GUIGlobals
operator|.
name|tableIcons
operator|.
name|put
argument_list|(
name|fileType
operator|.
name|getName
argument_list|()
argument_list|,
name|label
argument_list|)
expr_stmt|;
block|}
name|label
operator|=
operator|new
name|JLabel
argument_list|(
name|Relevance
operator|.
name|getInstance
argument_list|()
operator|.
name|getRepresentingIcon
argument_list|()
argument_list|)
expr_stmt|;
name|label
operator|.
name|setToolTipText
argument_list|(
name|Relevance
operator|.
name|getInstance
argument_list|()
operator|.
name|getToolTip
argument_list|()
argument_list|)
expr_stmt|;
name|GUIGlobals
operator|.
name|tableIcons
operator|.
name|put
argument_list|(
name|SpecialFieldsUtils
operator|.
name|FIELDNAME_RELEVANCE
argument_list|,
name|label
argument_list|)
expr_stmt|;
name|label
operator|=
operator|new
name|JLabel
argument_list|(
name|Quality
operator|.
name|getInstance
argument_list|()
operator|.
name|getRepresentingIcon
argument_list|()
argument_list|)
expr_stmt|;
name|label
operator|.
name|setToolTipText
argument_list|(
name|Quality
operator|.
name|getInstance
argument_list|()
operator|.
name|getToolTip
argument_list|()
argument_list|)
expr_stmt|;
name|GUIGlobals
operator|.
name|tableIcons
operator|.
name|put
argument_list|(
name|SpecialFieldsUtils
operator|.
name|FIELDNAME_QUALITY
argument_list|,
name|label
argument_list|)
expr_stmt|;
comment|// Ranking item in the menu uses one star
name|label
operator|=
operator|new
name|JLabel
argument_list|(
name|Rank
operator|.
name|getInstance
argument_list|()
operator|.
name|getRepresentingIcon
argument_list|()
argument_list|)
expr_stmt|;
name|label
operator|.
name|setToolTipText
argument_list|(
name|Rank
operator|.
name|getInstance
argument_list|()
operator|.
name|getToolTip
argument_list|()
argument_list|)
expr_stmt|;
name|GUIGlobals
operator|.
name|tableIcons
operator|.
name|put
argument_list|(
name|SpecialFieldsUtils
operator|.
name|FIELDNAME_RANKING
argument_list|,
name|label
argument_list|)
expr_stmt|;
comment|// Priority icon used for the menu
name|label
operator|=
operator|new
name|JLabel
argument_list|(
name|Priority
operator|.
name|getInstance
argument_list|()
operator|.
name|getRepresentingIcon
argument_list|()
argument_list|)
expr_stmt|;
name|label
operator|.
name|setToolTipText
argument_list|(
name|Rank
operator|.
name|getInstance
argument_list|()
operator|.
name|getToolTip
argument_list|()
argument_list|)
expr_stmt|;
name|GUIGlobals
operator|.
name|tableIcons
operator|.
name|put
argument_list|(
name|SpecialFieldsUtils
operator|.
name|FIELDNAME_PRIORITY
argument_list|,
name|label
argument_list|)
expr_stmt|;
comment|// Read icon used for menu
name|label
operator|=
operator|new
name|JLabel
argument_list|(
name|ReadStatus
operator|.
name|getInstance
argument_list|()
operator|.
name|getRepresentingIcon
argument_list|()
argument_list|)
expr_stmt|;
name|label
operator|.
name|setToolTipText
argument_list|(
name|ReadStatus
operator|.
name|getInstance
argument_list|()
operator|.
name|getToolTip
argument_list|()
argument_list|)
expr_stmt|;
name|GUIGlobals
operator|.
name|tableIcons
operator|.
name|put
argument_list|(
name|SpecialFieldsUtils
operator|.
name|FIELDNAME_READ
argument_list|,
name|label
argument_list|)
expr_stmt|;
comment|// Print icon used for menu
name|label
operator|=
operator|new
name|JLabel
argument_list|(
name|Printed
operator|.
name|getInstance
argument_list|()
operator|.
name|getRepresentingIcon
argument_list|()
argument_list|)
expr_stmt|;
name|label
operator|.
name|setToolTipText
argument_list|(
name|Printed
operator|.
name|getInstance
argument_list|()
operator|.
name|getToolTip
argument_list|()
argument_list|)
expr_stmt|;
name|GUIGlobals
operator|.
name|tableIcons
operator|.
name|put
argument_list|(
name|SpecialFieldsUtils
operator|.
name|FIELDNAME_PRINTED
argument_list|,
name|label
argument_list|)
expr_stmt|;
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|EDITOR_EMACS_KEYBINDINGS
argument_list|)
condition|)
block|{
name|EmacsKeyBindings
operator|.
name|load
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

