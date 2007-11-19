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
name|io
operator|.
name|File
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
name|net
operator|.
name|MalformedURLException
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
name|Collections
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
name|javax
operator|.
name|swing
operator|.
name|JLabel
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
comment|// Frame titles.
specifier|public
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
comment|//linuxDefaultLookAndFeel = "com.jgoodies.looks.plastic.Plastic3DLookAndFeel",
comment|//linuxDefaultLookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel",
DECL|field|linuxDefaultLookAndFeel
name|linuxDefaultLookAndFeel
init|=
literal|"org.jvnet.substance.skin.SubstanceCremeCoffeeLookAndFeel"
decl_stmt|,
comment|//linuxDefaultLookAndFeel = "org.jvnet.substance.skin.SubstanceNebulaLookAndFeel",
comment|//linuxDefaultLookAndFeel = "org.jvnet.substance.skin.SubstanceBusinessLookAndFeel",
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
operator|+
literal|15
decl_stmt|,
comment|// + 15 for possible scrollbar.
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
argument_list|<
name|String
argument_list|,
name|JLabel
argument_list|>
name|tableIcons
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|JLabel
argument_list|>
argument_list|()
decl_stmt|;
comment|// Contains table icon mappings. Set up
comment|// further below.
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
DECL|field|iconMap
specifier|static
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|iconMap
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
comment|//Help files (in HTML format):
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
comment|//	searchHelp = "SearchHelp.html",
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
decl_stmt|,
DECL|field|regularExpressionSearchHelp
name|regularExpressionSearchHelp
init|=
literal|"ExternalFiles.html#RegularExpressionSearch"
decl_stmt|,
DECL|field|nameFormatterHelp
name|nameFormatterHelp
init|=
literal|"CustomExports.html#NameFormatter"
decl_stmt|,
DECL|field|previewHelp
name|previewHelp
init|=
literal|"PreviewHelp.html"
decl_stmt|;
comment|//	Colors.
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
comment|//	invalidFieldBackground = new Color(210, 70, 70), // Invalid field backgnd.
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
comment|// constants for editor types:
specifier|public
specifier|static
specifier|final
name|int
DECL|field|STANDARD_EDITOR
name|STANDARD_EDITOR
init|=
literal|1
decl_stmt|,
DECL|field|FILE_LIST_EDITOR
name|FILE_LIST_EDITOR
init|=
literal|2
decl_stmt|;
DECL|field|FILE_FIELD
specifier|public
specifier|static
specifier|final
name|String
name|FILE_FIELD
init|=
literal|"file"
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
comment|//	Size constants for EntryTypeForm; small, medium and large.
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
comment|//	Constants controlling formatted bibtex output.
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
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|LANGUAGES
decl_stmt|;
static|static
block|{
name|LANGUAGES
operator|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
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
literal|"Nederlands"
argument_list|,
literal|"du"
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
comment|//LANGUAGES.put("EspaÃ±ol", "es");
block|}
comment|/** 	 * Read either the default icon theme, or a custom one. If loading of the custom theme 	 * fails, try to fall back on the default theme. 	 */
DECL|method|setUpIconTheme ()
specifier|public
specifier|static
name|void
name|setUpIconTheme
parameter_list|()
block|{
name|String
name|defaultPrefix
init|=
literal|"/images/crystal_16/"
decl_stmt|,
name|prefix
init|=
name|defaultPrefix
decl_stmt|;
name|URL
name|defaultResource
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|prefix
operator|+
literal|"Icons.properties"
argument_list|)
decl_stmt|;
name|URL
name|resource
init|=
name|defaultResource
decl_stmt|;
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"useCustomIconTheme"
argument_list|)
condition|)
block|{
name|String
name|filename
init|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"customIconThemeFile"
argument_list|)
decl_stmt|;
if|if
condition|(
name|filename
operator|!=
literal|null
condition|)
try|try
block|{
name|File
name|file
init|=
operator|new
name|File
argument_list|(
name|filename
argument_list|)
decl_stmt|;
name|String
name|parent
init|=
name|file
operator|.
name|getParentFile
argument_list|()
operator|.
name|getAbsolutePath
argument_list|()
decl_stmt|;
name|prefix
operator|=
literal|"file://"
operator|+
name|parent
operator|+
name|System
operator|.
name|getProperty
argument_list|(
literal|"file.separator"
argument_list|)
expr_stmt|;
name|resource
operator|=
operator|new
name|URL
argument_list|(
literal|"file://"
operator|+
name|file
operator|.
name|getAbsolutePath
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|MalformedURLException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
try|try
block|{
name|iconMap
operator|=
name|readIconThemeFile
argument_list|(
name|resource
argument_list|,
name|prefix
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Unable to read icon theme file"
argument_list|)
operator|+
literal|" '"
operator|+
name|resource
operator|.
name|toString
argument_list|()
operator|+
literal|"'"
argument_list|)
expr_stmt|;
comment|// If we were trying to load a custom theme, try the default one as a fallback:
if|if
condition|(
name|resource
operator|!=
name|defaultResource
condition|)
try|try
block|{
name|iconMap
operator|=
name|readIconThemeFile
argument_list|(
name|defaultResource
argument_list|,
name|defaultPrefix
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e2
parameter_list|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Unable to read default icon theme."
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/** 	 * Looks up the URL for the image representing the given function, in the resource 	 * file listing images. 	 * @param name The name of the icon, such as "open", "save", "saveAs" etc. 	 * @return The URL to the actual image to use. 	 */
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
if|if
condition|(
name|iconMap
operator|.
name|containsKey
argument_list|(
name|name
argument_list|)
condition|)
block|{
name|String
name|path
init|=
name|iconMap
operator|.
name|get
argument_list|(
name|name
argument_list|)
decl_stmt|;
name|URL
name|url
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|path
argument_list|)
decl_stmt|;
if|if
condition|(
name|url
operator|==
literal|null
condition|)
comment|// This may be a resource outside of the jar file, so we try a general URL:
try|try
block|{
name|url
operator|=
operator|new
name|URL
argument_list|(
name|path
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|MalformedURLException
name|e
parameter_list|)
block|{ 				}
if|if
condition|(
name|url
operator|==
literal|null
condition|)
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Could not find image file"
argument_list|)
operator|+
literal|" '"
operator|+
name|path
operator|+
literal|"'"
argument_list|)
expr_stmt|;
return|return
name|url
return|;
block|}
else|else
return|return
literal|null
return|;
block|}
comment|/** 	 * Constructs an ImageIcon for the given function, using the image specified in 	 * the resource files resource/Icons_en.properties. 	 * @param name The name of the icon, such as "open", "save", "saveAs" etc. 	 * @return The ImageIcon for the function. 	 */
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
name|URL
name|u
init|=
name|getIconUrl
argument_list|(
name|name
argument_list|)
decl_stmt|;
return|return
name|u
operator|!=
literal|null
condition|?
operator|new
name|ImageIcon
argument_list|(
name|getIconUrl
argument_list|(
name|name
argument_list|)
argument_list|)
else|:
literal|null
return|;
block|}
comment|/**      * Get a Map of all application icons mapped from their keys.      * @return A Map containing all icons used in the application.      */
DECL|method|getAllIcons ()
specifier|public
specifier|static
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|getAllIcons
parameter_list|()
block|{
return|return
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|iconMap
argument_list|)
return|;
block|}
comment|/** 	 * Read a typical java property file into a HashMap. Currently doesn't support escaping 	 * of the '=' character - it simply looks for the first '=' to determine where the key ends. 	 * Both the key and the value is trimmed for whitespace at the ends. 	 * @param file The URL to read information from. 	 * @param prefix A String to prefix to all values read. Can represent e.g. the directory 	 * where icon files are to be found. 	 * @return A HashMap containing all key-value pairs found. 	 * @throws IOException 	 */
DECL|method|readIconThemeFile (URL file, String prefix)
specifier|private
specifier|static
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|readIconThemeFile
parameter_list|(
name|URL
name|file
parameter_list|,
name|String
name|prefix
parameter_list|)
throws|throws
name|IOException
block|{
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|map
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|InputStream
name|in
init|=
literal|null
decl_stmt|;
try|try
block|{
name|in
operator|=
name|file
operator|.
name|openStream
argument_list|()
expr_stmt|;
name|StringBuffer
name|buffer
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|int
name|c
decl_stmt|;
while|while
condition|(
operator|(
name|c
operator|=
name|in
operator|.
name|read
argument_list|()
operator|)
operator|!=
operator|-
literal|1
condition|)
name|buffer
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
expr_stmt|;
name|String
index|[]
name|lines
init|=
name|buffer
operator|.
name|toString
argument_list|()
operator|.
name|split
argument_list|(
literal|"\n"
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|lines
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|String
name|line
init|=
name|lines
index|[
name|i
index|]
operator|.
name|trim
argument_list|()
decl_stmt|;
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
if|if
condition|(
name|index
operator|>=
literal|0
condition|)
block|{
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
name|map
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
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
throw|throw
name|ex
throw|;
block|}
finally|finally
block|{
try|try
block|{
if|if
condition|(
name|in
operator|!=
literal|null
condition|)
name|in
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
return|return
name|map
return|;
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
comment|/** 	 * Perform initializations that are only used in graphical mode. This is to prevent 	 * the "Xlib: connection to ":0.0" refused by server" error when access to the X server 	 * on Un*x is unavailable. 	 */
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
name|JLabel
name|lab
decl_stmt|;
name|lab
operator|=
operator|new
name|JLabel
argument_list|(
name|getImage
argument_list|(
literal|"pdfSmall"
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
name|getImage
argument_list|(
literal|"wwwSmall"
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
name|getImage
argument_list|(
literal|"citeseer"
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
name|getImage
argument_list|(
literal|"doiSmall"
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
name|getImage
argument_list|(
literal|"psSmall"
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
name|lab
operator|=
operator|new
name|JLabel
argument_list|(
name|getImage
argument_list|(
literal|"psSmall"
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
literal|"Open file"
argument_list|)
argument_list|)
expr_stmt|;
name|tableIcons
operator|.
name|put
argument_list|(
name|GUIGlobals
operator|.
name|FILE_FIELD
argument_list|,
name|lab
argument_list|)
expr_stmt|;
comment|//jabRefFont = new Font("arial", Font.ITALIC/*+Font.BOLD*/, 20);
block|}
block|}
end_class

end_unit

