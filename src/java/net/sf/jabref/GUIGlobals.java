begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*   Copyright (C) 2003 Morten O. Alver    All programs in this directory and   subdirectories are published under the GNU General Public License as   described below.    This program is free software; you can redistribute it and/or modify   it under the terms of the GNU General Public License as published by   the Free Software Foundation; either version 2 of the License, or (at   your option) any later version.    This program is distributed in the hope that it will be useful, but   WITHOUT ANY WARRANTY; without even the implied warranty of   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU   General Public License for more details.    You should have received a copy of the GNU General Public License   along with this program; if not, write to the Free Software   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307   USA    Further information about the GNU GPL is available at:   http://www.gnu.org/copyleft/gpl.ja.html    Note:   Modified for use in JabRef.   */
end_comment

begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|KeyEvent
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
name|*
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|KeyStroke
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
name|javax
operator|.
name|swing
operator|.
name|*
import|;
end_import

begin_class
DECL|class|GUIGlobals
specifier|public
class|class
name|GUIGlobals
block|{
comment|/*    * Static variables for graphics files and keyboard shortcuts.    */
comment|// for debugging
DECL|field|teller
specifier|static
name|int
name|teller
init|=
literal|0
decl_stmt|;
comment|// HashMap containing refs to all open BibtexDatabases.
comment|//static HashMap frames = new HashMap();
comment|// Frame titles.
specifier|static
name|String
DECL|field|frameTitle
name|frameTitle
init|=
literal|"JabRef"
decl_stmt|,
DECL|field|version
name|version
init|=
literal|"1.3.1"
decl_stmt|,
DECL|field|stringsTitle
name|stringsTitle
init|=
literal|"Strings for database"
decl_stmt|,
comment|//untitledStringsTitle = stringsTitle + Globals.lang("untitled"),
DECL|field|untitledTitle
name|untitledTitle
init|=
literal|"untitled"
decl_stmt|,
DECL|field|helpTitle
name|helpTitle
init|=
literal|"JabRef help"
decl_stmt|,
DECL|field|TYPE_HEADER
name|TYPE_HEADER
init|=
literal|"entrytype"
decl_stmt|,
DECL|field|NUMBER_COL
name|NUMBER_COL
init|=
literal|"#"
decl_stmt|;
DECL|field|CURRENTFONT
specifier|public
specifier|static
name|Font
name|CURRENTFONT
decl_stmt|,
DECL|field|typeNameFont
name|typeNameFont
init|=
operator|new
name|Font
argument_list|(
literal|"arial"
argument_list|,
name|Font
operator|.
name|ITALIC
operator|+
name|Font
operator|.
name|BOLD
argument_list|,
literal|24
argument_list|)
decl_stmt|,
DECL|field|jabRefFont
name|jabRefFont
init|=
operator|new
name|Font
argument_list|(
literal|"arial"
argument_list|,
name|Font
operator|.
name|ITALIC
comment|/*+Font.BOLD*/
argument_list|,
literal|20
argument_list|)
decl_stmt|;
comment|// Signature written at the top of the .bib file.
DECL|field|SIGNATURE
specifier|public
specifier|static
specifier|final
name|String
name|SIGNATURE
init|=
literal|"This file was created with JabRef "
operator|+
name|version
operator|+
literal|".\n\n"
decl_stmt|;
comment|// Size of help window.
specifier|static
name|Dimension
DECL|field|helpSize
name|helpSize
init|=
operator|new
name|Dimension
argument_list|(
literal|700
argument_list|,
literal|600
argument_list|)
decl_stmt|,
DECL|field|aboutSize
name|aboutSize
init|=
operator|new
name|Dimension
argument_list|(
literal|600
argument_list|,
literal|265
argument_list|)
decl_stmt|,
DECL|field|searchPaneSize
name|searchPaneSize
init|=
operator|new
name|Dimension
argument_list|(
literal|430
argument_list|,
literal|70
argument_list|)
decl_stmt|,
DECL|field|searchFieldSize
name|searchFieldSize
init|=
operator|new
name|Dimension
argument_list|(
literal|215
argument_list|,
literal|25
argument_list|)
decl_stmt|;
comment|// Divider size for BaseFrame split pane. 0 means non-resizable.
specifier|public
specifier|static
specifier|final
name|int
DECL|field|SPLIT_PANE_DIVIDER_SIZE
name|SPLIT_PANE_DIVIDER_SIZE
init|=
literal|2
decl_stmt|,
DECL|field|SPLIT_PANE_DIVIDER_LOCATION
name|SPLIT_PANE_DIVIDER_LOCATION
init|=
literal|145
decl_stmt|,
DECL|field|GROUPS_VISIBLE_ROWS
name|GROUPS_VISIBLE_ROWS
init|=
literal|10
decl_stmt|,
DECL|field|TABLE_ROW_PADDING
name|TABLE_ROW_PADDING
init|=
literal|4
decl_stmt|,
DECL|field|KEYBIND_COL_0
name|KEYBIND_COL_0
init|=
literal|200
decl_stmt|,
DECL|field|KEYBIND_COL_1
name|KEYBIND_COL_1
init|=
literal|80
decl_stmt|,
comment|// Added to the font size when determining table
DECL|field|PREVIEW_HEIGHT
name|PREVIEW_HEIGHT
init|=
literal|115
decl_stmt|;
comment|// row height
specifier|public
specifier|static
specifier|final
name|double
DECL|field|VERTICAL_DIVIDER_LOCATION
name|VERTICAL_DIVIDER_LOCATION
init|=
literal|0.4
decl_stmt|;
comment|// File names.
specifier|public
specifier|static
name|String
comment|//configFile = "preferences.dat",
DECL|field|backupExt
name|backupExt
init|=
literal|".bak"
decl_stmt|,
DECL|field|tempExt
name|tempExt
init|=
literal|".tmp"
decl_stmt|,
DECL|field|defaultDir
name|defaultDir
init|=
literal|"."
decl_stmt|;
comment|// Image paths.
specifier|public
specifier|static
name|String
DECL|field|imageSize
name|imageSize
init|=
literal|"24"
decl_stmt|,
DECL|field|extension
name|extension
init|=
literal|".gif"
decl_stmt|,
DECL|field|ex
name|ex
init|=
name|imageSize
operator|+
name|extension
decl_stmt|,
DECL|field|pre
name|pre
init|=
literal|"/images/"
decl_stmt|,
DECL|field|helpPre
name|helpPre
init|=
literal|"/help/"
decl_stmt|;
DECL|field|tableIcons
specifier|static
name|HashMap
name|tableIcons
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
comment|// Contains table icon mappings. Set up
comment|// further below.
DECL|method|getTableIcon (String fieldType)
specifier|public
specifier|static
name|ImageIcon
name|getTableIcon
parameter_list|(
name|String
name|fieldType
parameter_list|)
block|{
name|Object
name|o
init|=
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
name|Globals
operator|.
name|logger
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
return|return
operator|(
name|ImageIcon
operator|)
name|o
return|;
block|}
specifier|public
specifier|static
name|URL
DECL|field|openIconFile
name|openIconFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"fileopen2.png"
argument_list|)
decl_stmt|,
DECL|field|editIconFile
name|editIconFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"pencil.png"
argument_list|)
decl_stmt|,
DECL|field|saveIconFile
name|saveIconFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"filesave.png"
argument_list|)
decl_stmt|,
DECL|field|saveAsIconFile
name|saveAsIconFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"filesave.png"
argument_list|)
decl_stmt|,
DECL|field|prefsIconFile
name|prefsIconFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"configure2.png"
argument_list|)
decl_stmt|,
DECL|field|newIconFile
name|newIconFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"filenew.png"
argument_list|)
decl_stmt|,
DECL|field|undoIconFile
name|undoIconFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"undo.png"
argument_list|)
decl_stmt|,
DECL|field|redoIconFile
name|redoIconFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"redo.png"
argument_list|)
decl_stmt|,
DECL|field|preambleIconFile
name|preambleIconFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"preamble.png"
argument_list|)
decl_stmt|,
DECL|field|addIconFile
name|addIconFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"news_subscribe.png"
argument_list|)
decl_stmt|,
DECL|field|delRowIconFile
name|delRowIconFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"news_unsubscribe.png"
argument_list|)
decl_stmt|,
DECL|field|showReqIconFile
name|showReqIconFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"r_icon.gif"
argument_list|)
decl_stmt|,
DECL|field|showOptIconFile
name|showOptIconFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"o_icon.gif"
argument_list|)
decl_stmt|,
DECL|field|showGenIconFile
name|showGenIconFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"g_icon.gif"
argument_list|)
decl_stmt|,
DECL|field|sourceIconFile
name|sourceIconFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"viewsource.gif"
argument_list|)
decl_stmt|,
DECL|field|copyIconFile
name|copyIconFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"editcopy.png"
argument_list|)
decl_stmt|,
DECL|field|cutIconFile
name|cutIconFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"editcut.png"
argument_list|)
decl_stmt|,
DECL|field|copyKeyIconFile
name|copyKeyIconFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"editcopy.png"
argument_list|)
decl_stmt|,
DECL|field|genKeyIconFile
name|genKeyIconFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"wizard.png"
argument_list|)
decl_stmt|,
DECL|field|lyxIconFile
name|lyxIconFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"lyx.png"
argument_list|)
decl_stmt|,
DECL|field|backIconFile
name|backIconFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"back.png"
argument_list|)
decl_stmt|,
DECL|field|forwardIconFile
name|forwardIconFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"forward.png"
argument_list|)
decl_stmt|,
DECL|field|contentsIconFile
name|contentsIconFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"contents2.png"
argument_list|)
decl_stmt|,
DECL|field|removeIconFile
name|removeIconFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"editdelete.png"
argument_list|)
decl_stmt|,
DECL|field|upIconFile
name|upIconFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"up.png"
argument_list|)
decl_stmt|,
DECL|field|downIconFile
name|downIconFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"down.png"
argument_list|)
decl_stmt|,
DECL|field|stringsIconFile
name|stringsIconFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"strings.png"
argument_list|)
decl_stmt|,
DECL|field|groupsIconFile
name|groupsIconFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"queue.png"
argument_list|)
decl_stmt|,
DECL|field|closeIconFile
name|closeIconFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"fileclose.png"
argument_list|)
decl_stmt|,
DECL|field|refreshSmallIconFile
name|refreshSmallIconFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"reload.png"
argument_list|)
decl_stmt|,
DECL|field|helpSmallIconFile
name|helpSmallIconFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"help.png"
argument_list|)
decl_stmt|,
DECL|field|helpIconFile
name|helpIconFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"help.png"
argument_list|)
decl_stmt|,
DECL|field|aboutIcon
name|aboutIcon
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"hwinfo.png"
argument_list|)
decl_stmt|,
DECL|field|helpContentsIconFile
name|helpContentsIconFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"contents2.png"
argument_list|)
decl_stmt|,
DECL|field|newSmallIconFile
name|newSmallIconFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"filenew.png"
argument_list|)
decl_stmt|,
DECL|field|pasteIconFile
name|pasteIconFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"editpaste.png"
argument_list|)
decl_stmt|,
DECL|field|editEntryIconFile
name|editEntryIconFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"DocumentDraw.gif"
argument_list|)
decl_stmt|,
DECL|field|searchIconFile
name|searchIconFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"find.png"
argument_list|)
decl_stmt|,
DECL|field|autoGroupIcon
name|autoGroupIcon
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"run.png"
argument_list|)
decl_stmt|,
DECL|field|wwwIcon
name|wwwIcon
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"www.png"
argument_list|)
decl_stmt|,
DECL|field|fetchMedlineIcon
name|fetchMedlineIcon
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"goto.png"
argument_list|)
decl_stmt|,
DECL|field|pdfIcon
name|pdfIcon
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"pdf.png"
argument_list|)
decl_stmt|,
DECL|field|pdfSmallIcon
name|pdfSmallIcon
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"pdf_small.gif"
argument_list|)
decl_stmt|,
DECL|field|sheetIcon
name|sheetIcon
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"window_nofullscreen.png"
argument_list|)
decl_stmt|,
DECL|field|doiIcon
name|doiIcon
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"doi.png"
argument_list|)
decl_stmt|,
DECL|field|doiSmallIcon
name|doiSmallIcon
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"doismall.png"
argument_list|)
decl_stmt|,
DECL|field|psIcon
name|psIcon
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"postscript.png"
argument_list|)
decl_stmt|,
DECL|field|jabreflogo
name|jabreflogo
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"JabRef-Logo-small.gif"
argument_list|)
decl_stmt|,
DECL|field|splashScreenImage
name|splashScreenImage
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"JabRef-splash2.png"
argument_list|)
decl_stmt|;
comment|// Help files (in HTML format):
specifier|public
specifier|static
name|String
DECL|field|baseFrameHelp
name|baseFrameHelp
init|=
literal|"BaseFrameHelp.html"
decl_stmt|,
DECL|field|entryEditorHelp
name|entryEditorHelp
init|=
literal|"EntryEditorHelp.html"
decl_stmt|,
DECL|field|stringEditorHelp
name|stringEditorHelp
init|=
literal|"StringEditorHelp.html"
decl_stmt|,
DECL|field|helpContents
name|helpContents
init|=
literal|"Contents.html"
decl_stmt|,
DECL|field|searchHelp
name|searchHelp
init|=
literal|"SearchHelp.html"
decl_stmt|,
DECL|field|groupsHelp
name|groupsHelp
init|=
literal|"GroupsHelp.html"
decl_stmt|,
DECL|field|customEntriesHelp
name|customEntriesHelp
init|=
literal|"CustomEntriesHelp.html"
decl_stmt|,
DECL|field|contentSelectorHelp
name|contentSelectorHelp
init|=
literal|"ContentSelectorHelp.html"
decl_stmt|,
DECL|field|labelPatternHelp
name|labelPatternHelp
init|=
literal|"LabelPatterns.html"
decl_stmt|,
DECL|field|ownerHelp
name|ownerHelp
init|=
literal|"OwnerHelp.html"
decl_stmt|,
DECL|field|pdfHelp
name|pdfHelp
init|=
literal|"ExternalFiles.html"
decl_stmt|,
DECL|field|exportCustomizationHelp
name|exportCustomizationHelp
init|=
literal|"CustomExports.html"
decl_stmt|,
comment|//      searchHelp = "SearchHelp.html",
DECL|field|aboutPage
name|aboutPage
init|=
literal|"About.html"
decl_stmt|;
comment|// Colors.
specifier|public
specifier|static
name|Color
DECL|field|gridColor
name|gridColor
init|=
operator|new
name|Color
argument_list|(
literal|210
argument_list|,
literal|210
argument_list|,
literal|210
argument_list|)
decl_stmt|,
DECL|field|lightGray
name|lightGray
init|=
operator|new
name|Color
argument_list|(
literal|230
argument_list|,
literal|30
argument_list|,
literal|30
argument_list|)
decl_stmt|,
comment|// Light gray background
DECL|field|nullFieldColor
name|nullFieldColor
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
decl_stmt|,
comment|// Empty field, blue.
DECL|field|validFieldColor
name|validFieldColor
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
decl_stmt|,
comment|// Valid field, green.
DECL|field|invalidFieldColor
name|invalidFieldColor
init|=
operator|new
name|Color
argument_list|(
literal|141
argument_list|,
literal|0
argument_list|,
literal|61
argument_list|)
decl_stmt|,
comment|// Invalid field, red.
comment|//	invalidFieldColor = new Color(210, 70, 70), // Invalid field, red.
DECL|field|validFieldBackground
name|validFieldBackground
init|=
name|Color
operator|.
name|white
decl_stmt|,
comment|// Valid field backgnd.
comment|//invalidFieldBackground = new Color(210, 70, 70), // Invalid field backgnd.
DECL|field|invalidFieldBackground
name|invalidFieldBackground
init|=
operator|new
name|Color
argument_list|(
literal|141
argument_list|,
literal|0
argument_list|,
literal|61
argument_list|)
decl_stmt|,
comment|// Invalid field backgnd.
DECL|field|tableBackground
name|tableBackground
init|=
name|Color
operator|.
name|white
decl_stmt|,
comment|// Background color for the entry table.
DECL|field|tableReqFieldBackground
name|tableReqFieldBackground
init|=
operator|new
name|Color
argument_list|(
literal|235
argument_list|,
literal|235
argument_list|,
literal|255
argument_list|)
decl_stmt|,
DECL|field|tableOptFieldBackground
name|tableOptFieldBackground
init|=
operator|new
name|Color
argument_list|(
literal|230
argument_list|,
literal|255
argument_list|,
literal|230
argument_list|)
decl_stmt|,
DECL|field|tableIncompleteEntryBackground
name|tableIncompleteEntryBackground
init|=
operator|new
name|Color
argument_list|(
literal|250
argument_list|,
literal|175
argument_list|,
literal|175
argument_list|)
decl_stmt|,
DECL|field|maybeIncompleteEntryBackground
name|maybeIncompleteEntryBackground
init|=
operator|new
name|Color
argument_list|(
literal|255
argument_list|,
literal|255
argument_list|,
literal|200
argument_list|)
decl_stmt|,
DECL|field|markedEntryBackground
name|markedEntryBackground
init|=
operator|new
name|Color
argument_list|(
literal|255
argument_list|,
literal|255
argument_list|,
literal|180
argument_list|)
decl_stmt|,
DECL|field|grayedOutBackground
name|grayedOutBackground
init|=
operator|new
name|Color
argument_list|(
literal|225
argument_list|,
literal|210
argument_list|,
literal|210
argument_list|)
decl_stmt|,
DECL|field|grayedOutText
name|grayedOutText
init|=
operator|new
name|Color
argument_list|(
literal|40
argument_list|,
literal|40
argument_list|,
literal|40
argument_list|)
decl_stmt|,
DECL|field|veryGrayedOutBackground
name|veryGrayedOutBackground
init|=
operator|new
name|Color
argument_list|(
literal|205
argument_list|,
literal|180
argument_list|,
literal|180
argument_list|)
decl_stmt|,
DECL|field|veryGrayedOutText
name|veryGrayedOutText
init|=
operator|new
name|Color
argument_list|(
literal|40
argument_list|,
literal|40
argument_list|,
literal|40
argument_list|)
decl_stmt|;
DECL|field|META_FLAG
specifier|public
specifier|static
name|String
name|META_FLAG
init|=
literal|"jabref-meta: "
decl_stmt|;
DECL|field|META_FLAG_OLD
specifier|public
specifier|static
name|String
name|META_FLAG_OLD
init|=
literal|"bibkeeper-meta: "
decl_stmt|;
DECL|field|KEY_FIELD
specifier|public
specifier|static
name|String
name|KEY_FIELD
init|=
literal|"bibtexkey"
decl_stmt|;
DECL|field|ALL_FIELDS
specifier|public
specifier|static
name|String
index|[]
name|ALL_FIELDS
init|=
operator|new
name|String
index|[]
block|{
literal|"author"
block|,
literal|"editor"
block|,
literal|"title"
block|,
literal|"year"
block|,
literal|"pages"
block|,
literal|"month"
block|,
literal|"note"
block|,
literal|"publisher"
block|,
literal|"journal"
block|,
literal|"volume"
block|,
literal|"edition"
block|,
literal|"number"
block|,
literal|"chapter"
block|,
literal|"series"
block|,
literal|"type"
block|,
literal|"address"
block|,
literal|"annote"
block|,
literal|"booktitle"
block|,
literal|"crossref"
block|,
literal|"howpublished"
block|,
literal|"institution"
block|,
literal|"key"
block|,
literal|"organization"
block|,
literal|"school"
block|,
literal|"abstract"
block|,
literal|"url"
block|,
literal|"pdf"
block|,
literal|"comment"
block|,
literal|"bibtexkey"
block|,
literal|"keywords"
block|,
literal|"doi"
block|,
literal|"eid"
block|,
literal|"search"
block|}
decl_stmt|;
comment|// These are the fields that BibTex might want to treat, so these
comment|// must conform to BibTex rules.
DECL|field|BIBTEX_STANDARD_FIELDS
specifier|public
specifier|static
name|String
index|[]
name|BIBTEX_STANDARD_FIELDS
init|=
operator|new
name|String
index|[]
block|{
literal|"author"
block|,
literal|"editor"
block|,
literal|"title"
block|,
literal|"year"
block|,
literal|"pages"
block|,
literal|"month"
block|,
literal|"note"
block|,
literal|"publisher"
block|,
literal|"journal"
block|,
literal|"volume"
block|,
literal|"edition"
block|,
literal|"number"
block|,
literal|"chapter"
block|,
literal|"series"
block|,
literal|"type"
block|,
literal|"address"
block|,
literal|"annote"
block|,
literal|"booktitle"
block|,
literal|"crossref"
block|,
literal|"howpublished"
block|,
literal|"institution"
block|,
literal|"key"
block|,
literal|"organization"
block|,
literal|"school"
block|,
literal|"bibtexkey"
block|,
literal|"doi"
block|,
literal|"eid"
block|,
literal|"date"
block|}
decl_stmt|;
comment|// These fields will not be saved to the .bib file.
DECL|field|NON_WRITABLE_FIELDS
specifier|public
specifier|static
name|String
index|[]
name|NON_WRITABLE_FIELDS
init|=
operator|new
name|String
index|[]
block|{
name|Globals
operator|.
name|SEARCH
block|,
name|Globals
operator|.
name|GROUPSEARCH
block|}
decl_stmt|;
comment|// These fields will not be shown inside the source editor panel.
DECL|field|NON_DISPLAYABLE_FIELDS
specifier|public
specifier|static
name|String
index|[]
name|NON_DISPLAYABLE_FIELDS
init|=
operator|new
name|String
index|[]
block|{
name|Globals
operator|.
name|MARKED
block|,
name|Globals
operator|.
name|SEARCH
block|,
name|Globals
operator|.
name|GROUPSEARCH
block|}
decl_stmt|;
DECL|method|isWriteableField (String field)
specifier|public
specifier|static
name|boolean
name|isWriteableField
parameter_list|(
name|String
name|field
parameter_list|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|NON_WRITABLE_FIELDS
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|NON_WRITABLE_FIELDS
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
name|field
argument_list|)
condition|)
block|{
return|return
literal|false
return|;
block|}
block|}
return|return
literal|true
return|;
block|}
DECL|method|isDisplayableField (String field)
specifier|public
specifier|static
name|boolean
name|isDisplayableField
parameter_list|(
name|String
name|field
parameter_list|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|NON_DISPLAYABLE_FIELDS
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|NON_DISPLAYABLE_FIELDS
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
name|field
argument_list|)
condition|)
block|{
return|return
literal|false
return|;
block|}
block|}
return|return
literal|true
return|;
block|}
comment|/**    * Returns true if the given field is a standard Bibtex field.    *    * @param field a<code>String</code> value    * @return a<code>boolean</code> value    */
DECL|method|isStandardField (String field)
specifier|public
specifier|static
name|boolean
name|isStandardField
parameter_list|(
name|String
name|field
parameter_list|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|BIBTEX_STANDARD_FIELDS
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|BIBTEX_STANDARD_FIELDS
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
name|field
argument_list|)
condition|)
block|{
return|return
literal|true
return|;
block|}
block|}
return|return
literal|false
return|;
block|}
DECL|field|DEFAULT_FIELD_WEIGHT
specifier|public
specifier|static
name|double
name|DEFAULT_FIELD_WEIGHT
init|=
literal|1
decl_stmt|;
specifier|public
specifier|static
name|Double
DECL|field|SMALL_W
name|SMALL_W
init|=
operator|new
name|Double
argument_list|(
literal|0.30
argument_list|)
decl_stmt|,
DECL|field|MEDIUM_W
name|MEDIUM_W
init|=
operator|new
name|Double
argument_list|(
literal|0.5
argument_list|)
decl_stmt|,
DECL|field|LARGE_W
name|LARGE_W
init|=
operator|new
name|Double
argument_list|(
literal|1.5
argument_list|)
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
comment|// Size constants for EntryTypeForm; small, medium and large.
DECL|field|FORM_WIDTH
specifier|public
specifier|static
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
comment|// Constants controlling formatted bibtex output.
specifier|public
specifier|static
specifier|final
name|int
DECL|field|INDENT
name|INDENT
init|=
literal|4
decl_stmt|,
DECL|field|LINE_LENGTH
name|LINE_LENGTH
init|=
literal|65
decl_stmt|;
comment|// Maximum
DECL|field|DEFAULT_FIELD_LENGTH
specifier|public
specifier|static
name|int
name|DEFAULT_FIELD_LENGTH
init|=
literal|100
decl_stmt|,
DECL|field|NUMBER_COL_LENGTH
name|NUMBER_COL_LENGTH
init|=
literal|32
decl_stmt|,
DECL|field|WIDTH_ICON_COL
name|WIDTH_ICON_COL
init|=
literal|19
decl_stmt|;
comment|// Column widths for export customization dialog table:
specifier|public
specifier|static
specifier|final
name|int
DECL|field|EXPORT_DIALOG_COL_0_WIDTH
name|EXPORT_DIALOG_COL_0_WIDTH
init|=
literal|50
decl_stmt|,
DECL|field|EXPORT_DIALOG_COL_1_WIDTH
name|EXPORT_DIALOG_COL_1_WIDTH
init|=
literal|200
decl_stmt|,
DECL|field|EXPORT_DIALOG_COL_2_WIDTH
name|EXPORT_DIALOG_COL_2_WIDTH
init|=
literal|30
decl_stmt|;
DECL|field|FIELD_WEIGHT
specifier|public
specifier|static
specifier|final
name|Map
name|FIELD_WEIGHT
decl_stmt|;
DECL|field|FIELD_EXTRAS
DECL|field|LANGUAGES
specifier|public
specifier|static
specifier|final
name|Map
name|FIELD_EXTRAS
decl_stmt|,
name|LANGUAGES
decl_stmt|;
DECL|field|fieldLength
specifier|public
specifier|static
name|Map
name|fieldLength
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
static|static
block|{
name|LANGUAGES
operator|=
operator|new
name|HashMap
argument_list|()
expr_stmt|;
comment|// LANGUAGES contains mappings for supported languages.
name|LANGUAGES
operator|.
name|put
argument_list|(
literal|"English"
argument_list|,
literal|"en"
argument_list|)
expr_stmt|;
name|LANGUAGES
operator|.
name|put
argument_list|(
literal|"Deutsch"
argument_list|,
literal|"de"
argument_list|)
expr_stmt|;
name|LANGUAGES
operator|.
name|put
argument_list|(
literal|"Français"
argument_list|,
literal|"fr"
argument_list|)
expr_stmt|;
name|LANGUAGES
operator|.
name|put
argument_list|(
literal|"Norsk"
argument_list|,
literal|"no"
argument_list|)
expr_stmt|;
name|FIELD_EXTRAS
operator|=
operator|new
name|HashMap
argument_list|()
expr_stmt|;
comment|// fieldExtras contains mappings to tell the EntryEditor to add a specific
comment|// function to this field, for instance a "browse" button for the "pdf" field.
name|FIELD_EXTRAS
operator|.
name|put
argument_list|(
literal|"pdf"
argument_list|,
literal|"browsePdf"
argument_list|)
expr_stmt|;
name|FIELD_EXTRAS
operator|.
name|put
argument_list|(
literal|"ps"
argument_list|,
literal|"browse"
argument_list|)
expr_stmt|;
name|FIELD_EXTRAS
operator|.
name|put
argument_list|(
literal|"url"
argument_list|,
literal|"external"
argument_list|)
expr_stmt|;
name|FIELD_EXTRAS
operator|.
name|put
argument_list|(
literal|"doi"
argument_list|,
literal|"external"
argument_list|)
expr_stmt|;
comment|//FIELD_EXTRAS.put("keywords", "selector");
name|tableIcons
operator|.
name|put
argument_list|(
literal|"pdf"
argument_list|,
operator|new
name|ImageIcon
argument_list|(
name|pdfIcon
argument_list|)
argument_list|)
expr_stmt|;
name|tableIcons
operator|.
name|put
argument_list|(
literal|"url"
argument_list|,
operator|new
name|ImageIcon
argument_list|(
name|wwwIcon
argument_list|)
argument_list|)
expr_stmt|;
name|tableIcons
operator|.
name|put
argument_list|(
literal|"doi"
argument_list|,
operator|new
name|ImageIcon
argument_list|(
name|doiSmallIcon
argument_list|)
argument_list|)
expr_stmt|;
name|tableIcons
operator|.
name|put
argument_list|(
literal|"ps"
argument_list|,
operator|new
name|ImageIcon
argument_list|(
name|psIcon
argument_list|)
argument_list|)
expr_stmt|;
name|fieldLength
operator|.
name|put
argument_list|(
literal|"author"
argument_list|,
operator|new
name|Integer
argument_list|(
literal|280
argument_list|)
argument_list|)
expr_stmt|;
name|fieldLength
operator|.
name|put
argument_list|(
literal|"editor"
argument_list|,
operator|new
name|Integer
argument_list|(
literal|280
argument_list|)
argument_list|)
expr_stmt|;
name|fieldLength
operator|.
name|put
argument_list|(
literal|"title"
argument_list|,
operator|new
name|Integer
argument_list|(
literal|400
argument_list|)
argument_list|)
expr_stmt|;
name|fieldLength
operator|.
name|put
argument_list|(
literal|"abstract"
argument_list|,
operator|new
name|Integer
argument_list|(
literal|400
argument_list|)
argument_list|)
expr_stmt|;
name|fieldLength
operator|.
name|put
argument_list|(
literal|"booktitle"
argument_list|,
operator|new
name|Integer
argument_list|(
literal|175
argument_list|)
argument_list|)
expr_stmt|;
name|fieldLength
operator|.
name|put
argument_list|(
literal|"year"
argument_list|,
operator|new
name|Integer
argument_list|(
literal|60
argument_list|)
argument_list|)
expr_stmt|;
name|fieldLength
operator|.
name|put
argument_list|(
literal|"volume"
argument_list|,
operator|new
name|Integer
argument_list|(
literal|60
argument_list|)
argument_list|)
expr_stmt|;
name|fieldLength
operator|.
name|put
argument_list|(
literal|"number"
argument_list|,
operator|new
name|Integer
argument_list|(
literal|60
argument_list|)
argument_list|)
expr_stmt|;
name|fieldLength
operator|.
name|put
argument_list|(
literal|"entrytype"
argument_list|,
operator|new
name|Integer
argument_list|(
literal|75
argument_list|)
argument_list|)
expr_stmt|;
name|fieldLength
operator|.
name|put
argument_list|(
literal|"search"
argument_list|,
operator|new
name|Integer
argument_list|(
literal|75
argument_list|)
argument_list|)
expr_stmt|;
name|fieldLength
operator|.
name|put
argument_list|(
name|NUMBER_COL
argument_list|,
operator|new
name|Integer
argument_list|(
literal|32
argument_list|)
argument_list|)
expr_stmt|;
name|Map
name|fieldWeight
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
name|fieldWeight
operator|.
name|put
argument_list|(
literal|"author"
argument_list|,
name|MEDIUM_W
argument_list|)
expr_stmt|;
name|fieldWeight
operator|.
name|put
argument_list|(
literal|"year"
argument_list|,
name|SMALL_W
argument_list|)
expr_stmt|;
name|fieldWeight
operator|.
name|put
argument_list|(
literal|"pages"
argument_list|,
name|SMALL_W
argument_list|)
expr_stmt|;
name|fieldWeight
operator|.
name|put
argument_list|(
literal|"month"
argument_list|,
name|SMALL_W
argument_list|)
expr_stmt|;
name|fieldWeight
operator|.
name|put
argument_list|(
literal|"url"
argument_list|,
name|SMALL_W
argument_list|)
expr_stmt|;
name|fieldWeight
operator|.
name|put
argument_list|(
literal|"crossref"
argument_list|,
name|SMALL_W
argument_list|)
expr_stmt|;
name|fieldWeight
operator|.
name|put
argument_list|(
literal|"note"
argument_list|,
name|MEDIUM_W
argument_list|)
expr_stmt|;
name|fieldWeight
operator|.
name|put
argument_list|(
literal|"publisher"
argument_list|,
name|MEDIUM_W
argument_list|)
expr_stmt|;
name|fieldWeight
operator|.
name|put
argument_list|(
literal|"journal"
argument_list|,
name|SMALL_W
argument_list|)
expr_stmt|;
name|fieldWeight
operator|.
name|put
argument_list|(
literal|"volume"
argument_list|,
name|SMALL_W
argument_list|)
expr_stmt|;
name|fieldWeight
operator|.
name|put
argument_list|(
literal|"edition"
argument_list|,
name|SMALL_W
argument_list|)
expr_stmt|;
name|fieldWeight
operator|.
name|put
argument_list|(
literal|"keywords"
argument_list|,
name|SMALL_W
argument_list|)
expr_stmt|;
name|fieldWeight
operator|.
name|put
argument_list|(
literal|"doi"
argument_list|,
name|SMALL_W
argument_list|)
expr_stmt|;
name|fieldWeight
operator|.
name|put
argument_list|(
literal|"eid"
argument_list|,
name|SMALL_W
argument_list|)
expr_stmt|;
name|fieldWeight
operator|.
name|put
argument_list|(
literal|"pdf"
argument_list|,
name|SMALL_W
argument_list|)
expr_stmt|;
name|fieldWeight
operator|.
name|put
argument_list|(
literal|"number"
argument_list|,
name|SMALL_W
argument_list|)
expr_stmt|;
name|fieldWeight
operator|.
name|put
argument_list|(
literal|"chapter"
argument_list|,
name|SMALL_W
argument_list|)
expr_stmt|;
name|fieldWeight
operator|.
name|put
argument_list|(
literal|"editor"
argument_list|,
name|MEDIUM_W
argument_list|)
expr_stmt|;
name|fieldWeight
operator|.
name|put
argument_list|(
literal|"series"
argument_list|,
name|SMALL_W
argument_list|)
expr_stmt|;
name|fieldWeight
operator|.
name|put
argument_list|(
literal|"type"
argument_list|,
name|SMALL_W
argument_list|)
expr_stmt|;
name|fieldWeight
operator|.
name|put
argument_list|(
literal|"address"
argument_list|,
name|SMALL_W
argument_list|)
expr_stmt|;
name|fieldWeight
operator|.
name|put
argument_list|(
literal|"howpublished"
argument_list|,
name|MEDIUM_W
argument_list|)
expr_stmt|;
name|fieldWeight
operator|.
name|put
argument_list|(
literal|"institution"
argument_list|,
name|MEDIUM_W
argument_list|)
expr_stmt|;
name|fieldWeight
operator|.
name|put
argument_list|(
literal|"organization"
argument_list|,
name|MEDIUM_W
argument_list|)
expr_stmt|;
name|fieldWeight
operator|.
name|put
argument_list|(
literal|"school"
argument_list|,
name|MEDIUM_W
argument_list|)
expr_stmt|;
name|fieldWeight
operator|.
name|put
argument_list|(
literal|"comment"
argument_list|,
name|MEDIUM_W
argument_list|)
expr_stmt|;
name|fieldWeight
operator|.
name|put
argument_list|(
literal|"abstract"
argument_list|,
name|LARGE_W
argument_list|)
expr_stmt|;
name|FIELD_WEIGHT
operator|=
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|fieldWeight
argument_list|)
expr_stmt|;
block|}
empty_stmt|;
comment|/*     public static int getPreferredFieldLength(String name) {     int l = DEFAULT_FIELD_LENGTH;     Object o = fieldLength.get(name.toLowerCase());     if (o != null)     l = ((Integer)o).intValue();     return l;     }*/
DECL|method|getFieldWeight (String name)
specifier|public
specifier|static
name|double
name|getFieldWeight
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|double
name|l
init|=
name|DEFAULT_FIELD_WEIGHT
decl_stmt|;
name|Object
name|o
init|=
name|FIELD_WEIGHT
operator|.
name|get
argument_list|(
name|name
operator|.
name|toLowerCase
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|o
operator|!=
literal|null
condition|)
block|{
name|l
operator|=
operator|(
operator|(
name|Double
operator|)
name|o
operator|)
operator|.
name|doubleValue
argument_list|()
expr_stmt|;
block|}
return|return
name|l
return|;
block|}
block|}
end_class

end_unit

