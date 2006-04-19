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
name|*
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

begin_comment
comment|//import java.util.List;
end_comment

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
specifier|public
specifier|static
name|String
DECL|field|frameTitle
name|frameTitle
init|=
literal|"JabRef"
decl_stmt|,
comment|//      version = "1.8b",
DECL|field|version
name|version
init|=
name|Globals
operator|.
name|VERSION
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
decl_stmt|,
DECL|field|encPrefix
name|encPrefix
init|=
literal|"Encoding: "
decl_stmt|,
comment|// Part of the signature in written bib files.
DECL|field|linuxDefaultLookAndFeel
name|linuxDefaultLookAndFeel
init|=
literal|"com.jgoodies.looks.plastic.Plastic3DLookAndFeel"
decl_stmt|,
comment|//"com.shfarr.ui.plaf.fh.FhLookAndFeel",
comment|//"net.sourceforge.mlf.metouia.MetouiaLookAndFeel",
comment|//"org.compiere.plaf.CompiereLookAndFeel",
DECL|field|windowsDefaultLookAndFeel
name|windowsDefaultLookAndFeel
init|=
literal|"com.jgoodies.looks.windows.WindowsLookAndFeel"
decl_stmt|;
DECL|field|CURRENTFONT
specifier|public
specifier|static
name|Font
name|CURRENTFONT
decl_stmt|,
DECL|field|typeNameFont
name|typeNameFont
decl_stmt|,
DECL|field|jabRefFont
name|jabRefFont
decl_stmt|,
DECL|field|fieldNameFont
name|fieldNameFont
decl_stmt|;
comment|// Signature written at the top of the .bib file.
DECL|field|SIGNATURE
specifier|public
specifier|static
specifier|final
name|String
name|SIGNATURE
init|=
literal|"This file was created with JabRef"
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
literal|4
decl_stmt|,
DECL|field|SPLIT_PANE_DIVIDER_LOCATION
name|SPLIT_PANE_DIVIDER_LOCATION
init|=
literal|145
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
DECL|field|PREVIEW_PANEL_PADDING
name|PREVIEW_PANEL_PADDING
init|=
literal|15
decl_stmt|,
comment|// Extra room given to the preview editor, in addition to its own
DECL|field|PREVIEW_PANEL_HEIGHT
name|PREVIEW_PANEL_HEIGHT
init|=
literal|200
decl_stmt|,
DECL|field|MAX_CONTENT_SELECTOR_WIDTH
name|MAX_CONTENT_SELECTOR_WIDTH
init|=
literal|240
decl_stmt|;
comment|// The max width of the combobox for content selectors.
comment|// calculated preferred size
comment|//public static final int[] PREVIEW_HEIGHT = {115, 300};
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
decl_stmt|,
DECL|field|fontPath
name|fontPath
init|=
literal|"/images/font/"
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
DECL|field|incompleteLabel
specifier|public
specifier|static
name|JLabel
name|incompleteLabel
decl_stmt|;
comment|// JLabel with icon signaling an incomplete entry.
DECL|field|activeEditor
specifier|public
specifier|static
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
name|JLabel
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
literal|"fldr_obj.gif"
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
literal|"edittsk_tsk.gif"
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
literal|"save_edit.gif"
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
literal|"saveas_edit.gif"
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
literal|"new_page.gif"
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
literal|"undo_edit.gif"
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
literal|"redo_edit.gif"
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
literal|"plus.gif"
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
literal|"minus.gif"
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
literal|"reqIcon.png"
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
literal|"optIcon.png"
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
literal|"absIcon.png"
argument_list|)
decl_stmt|,
DECL|field|showAbsIconFile
name|showAbsIconFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"genIcon.png"
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
literal|"copy_edit.gif"
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
literal|"cut_edit.gif"
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
literal|"copy_edit.gif"
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
literal|"lyx2.png"
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
literal|"backward_nav.gif"
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
literal|"forward_nav.gif"
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
literal|"toc_closed.gif"
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
literal|"delete_edit.gif"
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
literal|"prev_nav.gif"
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
literal|"next_nav.gif"
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
DECL|field|groupsHighlightMatchingAnyFile
name|groupsHighlightMatchingAnyFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"groupsHighlightAny.png"
argument_list|)
decl_stmt|,
DECL|field|groupsHighlightMatchingAllFile
name|groupsHighlightMatchingAllFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"groupsHighlightAll.png"
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
DECL|field|close2IconFile
name|close2IconFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"fileclose2.png"
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
literal|"refresh_nav.gif"
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
literal|"view.gif"
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
literal|"view.gif"
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
literal|"view.gif"
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
literal|"new_page.gif"
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
literal|"paste_edit.gif"
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
literal|"search.gif"
argument_list|)
decl_stmt|,
DECL|field|previewIconFile
name|previewIconFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"preview.png"
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
literal|"addtsk_tsk.gif"
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
DECL|field|wwwCiteSeerIcon
name|wwwCiteSeerIcon
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"wwwciteseer.png"
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
DECL|field|fetchHourglassIcon
name|fetchHourglassIcon
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"Hourglass.png"
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
literal|"defaults_ps.gif"
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
DECL|field|incompleteIcon
name|incompleteIcon
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"exclamation.gif"
argument_list|)
decl_stmt|,
DECL|field|winEdtIcon
name|winEdtIcon
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"winedt.png"
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
literal|"JabRef-icon.png"
argument_list|)
decl_stmt|,
DECL|field|completeTagIcon
name|completeTagIcon
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"completeItem.png"
argument_list|)
decl_stmt|,
DECL|field|wrongTagIcon
name|wrongTagIcon
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"wrongItem.png"
argument_list|)
decl_stmt|,
DECL|field|clearInputArea
name|clearInputArea
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"new_page.gif"
argument_list|)
decl_stmt|,
DECL|field|markIcon
name|markIcon
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"mark.png"
argument_list|)
decl_stmt|,
DECL|field|unmarkIcon
name|unmarkIcon
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"unmark.png"
argument_list|)
decl_stmt|,
DECL|field|newBibFile
name|newBibFile
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"newBibFile.png"
argument_list|)
decl_stmt|,
DECL|field|integrityCheck
name|integrityCheck
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"integrity.png"
argument_list|)
decl_stmt|,
DECL|field|integrityInfo
name|integrityInfo
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"messageInfo.png"
argument_list|)
decl_stmt|,
DECL|field|integrityWarn
name|integrityWarn
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"messageWarn.png"
argument_list|)
decl_stmt|,
DECL|field|integrityFail
name|integrityFail
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"messageFail.png"
argument_list|)
decl_stmt|,
DECL|field|duplicateIcon
name|duplicateIcon
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"duplicate.png"
argument_list|)
decl_stmt|,
DECL|field|emacsIcon
name|emacsIcon
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"emacs.png"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
name|ImageIcon
DECL|field|groupRefiningIcon
name|groupRefiningIcon
init|=
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"groupRefining.png"
argument_list|)
argument_list|)
decl_stmt|,
DECL|field|groupIncludingIcon
name|groupIncludingIcon
init|=
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|pre
operator|+
literal|"groupIncluding.png"
argument_list|)
argument_list|)
decl_stmt|,
DECL|field|groupRegularIcon
name|groupRegularIcon
init|=
literal|null
decl_stmt|;
comment|/*public static incompleteEntryIcon = new ImageIcon(incompleteIcon);     static {       incompleteEntryIcon.setTool     }*/
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
DECL|field|timeStampHelp
name|timeStampHelp
init|=
literal|"TimeStampHelp.html"
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
DECL|field|importCustomizationHelp
name|importCustomizationHelp
init|=
literal|"CustomImports.html"
decl_stmt|,
DECL|field|medlineHelp
name|medlineHelp
init|=
literal|"MedlineHelp.html"
decl_stmt|,
DECL|field|citeSeerHelp
name|citeSeerHelp
init|=
literal|"CiteSeerHelp.html"
decl_stmt|,
DECL|field|generalFieldsHelp
name|generalFieldsHelp
init|=
literal|"GeneralFields.html"
decl_stmt|,
comment|//      searchHelp = "SearchHelp.html",
DECL|field|aboutPage
name|aboutPage
init|=
literal|"About.html"
decl_stmt|,
DECL|field|shortPlainImport
name|shortPlainImport
init|=
literal|"ShortPlainImport.html"
decl_stmt|,
DECL|field|importInspectionHelp
name|importInspectionHelp
init|=
literal|"ImportInspectionDialog.html"
decl_stmt|,
DECL|field|shortIntegrityCheck
name|shortIntegrityCheck
init|=
literal|"ShortIntegrityCheck.html"
decl_stmt|,
DECL|field|shortAuxImport
name|shortAuxImport
init|=
literal|"ShortAuxImport.html"
decl_stmt|,
DECL|field|remoteHelp
name|remoteHelp
init|=
literal|"RemoteHelp.html"
decl_stmt|,
DECL|field|journalAbbrHelp
name|journalAbbrHelp
init|=
literal|"JournalAbbreviations.html"
decl_stmt|;
comment|// Colors.
specifier|public
specifier|static
name|Color
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
DECL|field|validFieldColor
name|validFieldColor
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
DECL|field|nullFieldColor
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
literal|255
argument_list|,
literal|100
argument_list|,
literal|100
argument_list|)
decl_stmt|,
comment|// Invalid field backgnd.
DECL|field|gradientGray
name|gradientGray
init|=
operator|new
name|Color
argument_list|(
literal|112
argument_list|,
literal|121
argument_list|,
literal|165
argument_list|)
decl_stmt|,
comment|// Title bar gradient color, sidepaneheader
DECL|field|gradientBlue
name|gradientBlue
init|=
operator|new
name|Color
argument_list|(
literal|0
argument_list|,
literal|27
argument_list|,
literal|102
argument_list|)
decl_stmt|,
comment|// Title bar gradient color, sidepaneheader
comment|//activeTabbed = Color.black,  // active Database (JTabbedPane)
comment|//inActiveTabbed = Color.gray.darker(),  // inactive Database
DECL|field|activeTabbed
name|activeTabbed
init|=
name|validFieldColor
operator|.
name|darker
argument_list|()
decl_stmt|,
comment|// active Database (JTabbedPane)
DECL|field|inActiveTabbed
name|inActiveTabbed
init|=
name|Color
operator|.
name|black
decl_stmt|,
comment|// inactive Database
DECL|field|infoField
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
comment|// color for an info field
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
DECL|field|ENTRYTYPE_FLAG
specifier|public
specifier|static
name|String
name|ENTRYTYPE_FLAG
init|=
literal|"jabref-entrytype: "
decl_stmt|;
comment|// some fieldname constants
specifier|public
specifier|static
specifier|final
name|double
DECL|field|DEFAULT_FIELD_WEIGHT
name|DEFAULT_FIELD_WEIGHT
init|=
literal|1
decl_stmt|,
DECL|field|MAX_FIELD_WEIGHT
name|MAX_FIELD_WEIGHT
init|=
literal|2
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|double
DECL|field|SMALL_W
name|SMALL_W
init|=
literal|0.30
decl_stmt|,
DECL|field|MEDIUM_W
name|MEDIUM_W
init|=
literal|0.5
decl_stmt|,
DECL|field|LARGE_W
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
comment|// Column widths for import customization dialog table:
specifier|public
specifier|static
specifier|final
name|int
DECL|field|IMPORT_DIALOG_COL_0_WIDTH
name|IMPORT_DIALOG_COL_0_WIDTH
init|=
literal|200
decl_stmt|,
DECL|field|IMPORT_DIALOG_COL_1_WIDTH
name|IMPORT_DIALOG_COL_1_WIDTH
init|=
literal|80
decl_stmt|,
DECL|field|IMPORT_DIALOG_COL_2_WIDTH
name|IMPORT_DIALOG_COL_2_WIDTH
init|=
literal|200
decl_stmt|,
DECL|field|IMPORT_DIALOG_COL_3_WIDTH
name|IMPORT_DIALOG_COL_3_WIDTH
init|=
literal|200
decl_stmt|;
DECL|field|LANGUAGES
specifier|public
specifier|static
specifier|final
name|Map
name|LANGUAGES
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
literal|"Fran\u00E7ais"
argument_list|,
literal|"fr"
argument_list|)
expr_stmt|;
name|LANGUAGES
operator|.
name|put
argument_list|(
literal|"Italiano"
argument_list|,
literal|"it"
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
block|}
comment|/** returns the path to language independent help files */
DECL|method|getLocaleHelpPath ()
specifier|public
specifier|static
name|String
name|getLocaleHelpPath
parameter_list|()
block|{
name|JabRefPreferences
name|prefs
init|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|String
name|middle
init|=
name|prefs
operator|.
name|get
argument_list|(
literal|"language"
argument_list|)
operator|+
literal|"/"
decl_stmt|;
if|if
condition|(
name|middle
operator|.
name|equals
argument_list|(
literal|"en/"
argument_list|)
condition|)
name|middle
operator|=
literal|""
expr_stmt|;
comment|// english in base help dir.
return|return
operator|(
name|helpPre
operator|+
name|middle
operator|)
return|;
block|}
comment|/**    * Perform initializations that are only used in graphical mode. This is to prevent    * the "Xlib: connection to ":0.0" refused by server" error when access to the X server    * on Un*x is unavailable.    */
DECL|method|init ()
specifier|public
specifier|static
name|void
name|init
parameter_list|()
block|{
name|typeNameFont
operator|=
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
expr_stmt|;
name|fieldNameFont
operator|=
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
literal|14
argument_list|)
expr_stmt|;
name|incompleteLabel
operator|=
operator|new
name|JLabel
argument_list|(
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|incompleteIcon
argument_list|)
argument_list|)
expr_stmt|;
name|incompleteLabel
operator|.
name|setToolTipText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Entry is incomplete"
argument_list|)
argument_list|)
expr_stmt|;
name|JLabel
name|lab
decl_stmt|;
name|lab
operator|=
operator|new
name|JLabel
argument_list|(
operator|new
name|ImageIcon
argument_list|(
name|pdfIcon
argument_list|)
argument_list|)
expr_stmt|;
name|lab
operator|.
name|setToolTipText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Open"
argument_list|)
operator|+
literal|" PDF"
argument_list|)
expr_stmt|;
name|tableIcons
operator|.
name|put
argument_list|(
literal|"pdf"
argument_list|,
name|lab
argument_list|)
expr_stmt|;
name|lab
operator|=
operator|new
name|JLabel
argument_list|(
operator|new
name|ImageIcon
argument_list|(
name|wwwIcon
argument_list|)
argument_list|)
expr_stmt|;
name|lab
operator|.
name|setToolTipText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Open"
argument_list|)
operator|+
literal|" URL"
argument_list|)
expr_stmt|;
name|tableIcons
operator|.
name|put
argument_list|(
literal|"url"
argument_list|,
name|lab
argument_list|)
expr_stmt|;
name|lab
operator|=
operator|new
name|JLabel
argument_list|(
operator|new
name|ImageIcon
argument_list|(
name|wwwCiteSeerIcon
argument_list|)
argument_list|)
expr_stmt|;
name|lab
operator|.
name|setToolTipText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Open"
argument_list|)
operator|+
literal|" CiteSeer URL"
argument_list|)
expr_stmt|;
name|tableIcons
operator|.
name|put
argument_list|(
literal|"citeseerurl"
argument_list|,
name|lab
argument_list|)
expr_stmt|;
name|lab
operator|=
operator|new
name|JLabel
argument_list|(
operator|new
name|ImageIcon
argument_list|(
name|doiSmallIcon
argument_list|)
argument_list|)
expr_stmt|;
name|lab
operator|.
name|setToolTipText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Open"
argument_list|)
operator|+
literal|" DOI "
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"web link"
argument_list|)
argument_list|)
expr_stmt|;
name|tableIcons
operator|.
name|put
argument_list|(
literal|"doi"
argument_list|,
name|lab
argument_list|)
expr_stmt|;
name|lab
operator|=
operator|new
name|JLabel
argument_list|(
operator|new
name|ImageIcon
argument_list|(
name|psIcon
argument_list|)
argument_list|)
expr_stmt|;
name|lab
operator|.
name|setToolTipText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Open"
argument_list|)
operator|+
literal|" PS"
argument_list|)
expr_stmt|;
name|tableIcons
operator|.
name|put
argument_list|(
literal|"ps"
argument_list|,
name|lab
argument_list|)
expr_stmt|;
comment|//jabRefFont = new Font("arial", Font.ITALIC/*+Font.BOLD*/, 20);
block|}
block|}
end_class

end_unit

