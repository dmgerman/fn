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
name|externalfiletype
operator|.
name|ExternalFileType
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
name|externalfiletype
operator|.
name|ExternalFileTypes
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
name|EmacsKeyBindings
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
name|specialfields
operator|.
name|SpecialFieldViewModel
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
name|entry
operator|.
name|FieldName
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
name|entry
operator|.
name|specialfields
operator|.
name|SpecialField
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
DECL|field|UNTITLED_TITLE
specifier|public
specifier|static
specifier|final
name|String
name|UNTITLED_TITLE
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"untitled"
argument_list|)
decl_stmt|;
DECL|field|currentFont
specifier|public
specifier|static
name|Font
name|currentFont
decl_stmt|;
DECL|field|TABLE_ICONS
specifier|private
specifier|static
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|JLabel
argument_list|>
name|TABLE_ICONS
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
comment|// Contains table icon mappings. Set up
comment|//	Colors.
DECL|field|ENTRY_EDITOR_LABEL_COLOR
specifier|public
specifier|static
specifier|final
name|Color
name|ENTRY_EDITOR_LABEL_COLOR
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
DECL|field|ACTIVE_TABBED_COLOR
specifier|static
specifier|final
name|Color
name|ACTIVE_TABBED_COLOR
init|=
name|ENTRY_EDITOR_LABEL_COLOR
operator|.
name|darker
argument_list|()
decl_stmt|;
comment|// active Database (JTabbedPane)
DECL|field|INACTIVE_TABBED_COLOR
specifier|static
specifier|final
name|Color
name|INACTIVE_TABBED_COLOR
init|=
name|Color
operator|.
name|black
decl_stmt|;
comment|// inactive Database
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
DECL|field|activeBackgroundColor
specifier|public
specifier|static
name|Color
name|activeBackgroundColor
decl_stmt|;
DECL|field|invalidFieldBackgroundColor
specifier|public
specifier|static
name|Color
name|invalidFieldBackgroundColor
decl_stmt|;
DECL|field|NULL_FIELD_COLOR
specifier|public
specifier|static
specifier|final
name|Color
name|NULL_FIELD_COLOR
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
DECL|field|ACTIVE_EDITOR_COLOR
specifier|public
specifier|static
specifier|final
name|Color
name|ACTIVE_EDITOR_COLOR
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
DECL|field|WIDTH_ICON_COL
specifier|public
specifier|static
specifier|final
name|int
name|WIDTH_ICON_COL
init|=
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
operator|+
literal|12
decl_stmt|;
comment|// add some additional space to improve appearance
DECL|field|WIDTH_ICON_COL_RANKING
specifier|public
specifier|static
specifier|final
name|int
name|WIDTH_ICON_COL_RANKING
init|=
literal|5
operator|*
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
decl_stmt|;
comment|// Width of Ranking Icon Column
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
DECL|method|GUIGlobals ()
specifier|private
name|GUIGlobals
parameter_list|()
block|{     }
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
name|JLabel
name|label
init|=
name|GUIGlobals
operator|.
name|TABLE_ICONS
operator|.
name|get
argument_list|(
name|fieldType
argument_list|)
decl_stmt|;
if|if
condition|(
name|label
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
name|label
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
name|activeBackgroundColor
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
name|TABLE_ICONS
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|PDF
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
name|TABLE_ICONS
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|URL
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
name|TABLE_ICONS
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
name|TABLE_ICONS
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|EPRINT
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
name|DOI
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
name|TABLE_ICONS
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|DOI
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
name|TABLE_ICONS
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|PS
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
name|TABLE_ICONS
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|FOLDER
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
name|TABLE_ICONS
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|FILE
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
name|TABLE_ICONS
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
name|SpecialFieldViewModel
name|relevanceViewModel
init|=
operator|new
name|SpecialFieldViewModel
argument_list|(
name|SpecialField
operator|.
name|RELEVANCE
argument_list|)
decl_stmt|;
name|label
operator|=
operator|new
name|JLabel
argument_list|(
name|relevanceViewModel
operator|.
name|getRepresentingIcon
argument_list|()
argument_list|)
expr_stmt|;
name|label
operator|.
name|setToolTipText
argument_list|(
name|relevanceViewModel
operator|.
name|getLocalization
argument_list|()
argument_list|)
expr_stmt|;
name|GUIGlobals
operator|.
name|TABLE_ICONS
operator|.
name|put
argument_list|(
name|SpecialField
operator|.
name|RELEVANCE
operator|.
name|getFieldName
argument_list|()
argument_list|,
name|label
argument_list|)
expr_stmt|;
name|SpecialFieldViewModel
name|qualityViewModel
init|=
operator|new
name|SpecialFieldViewModel
argument_list|(
name|SpecialField
operator|.
name|QUALITY
argument_list|)
decl_stmt|;
name|label
operator|=
operator|new
name|JLabel
argument_list|(
name|qualityViewModel
operator|.
name|getRepresentingIcon
argument_list|()
argument_list|)
expr_stmt|;
name|label
operator|.
name|setToolTipText
argument_list|(
name|qualityViewModel
operator|.
name|getLocalization
argument_list|()
argument_list|)
expr_stmt|;
name|GUIGlobals
operator|.
name|TABLE_ICONS
operator|.
name|put
argument_list|(
name|SpecialField
operator|.
name|QUALITY
operator|.
name|getFieldName
argument_list|()
argument_list|,
name|label
argument_list|)
expr_stmt|;
comment|// Ranking item in the menu uses one star
name|SpecialFieldViewModel
name|rankViewModel
init|=
operator|new
name|SpecialFieldViewModel
argument_list|(
name|SpecialField
operator|.
name|RANKING
argument_list|)
decl_stmt|;
name|label
operator|=
operator|new
name|JLabel
argument_list|(
name|rankViewModel
operator|.
name|getRepresentingIcon
argument_list|()
argument_list|)
expr_stmt|;
name|label
operator|.
name|setToolTipText
argument_list|(
name|rankViewModel
operator|.
name|getLocalization
argument_list|()
argument_list|)
expr_stmt|;
name|GUIGlobals
operator|.
name|TABLE_ICONS
operator|.
name|put
argument_list|(
name|SpecialField
operator|.
name|RANKING
operator|.
name|getFieldName
argument_list|()
argument_list|,
name|label
argument_list|)
expr_stmt|;
comment|// Priority icon used for the menu
name|SpecialFieldViewModel
name|priorityViewModel
init|=
operator|new
name|SpecialFieldViewModel
argument_list|(
name|SpecialField
operator|.
name|PRIORITY
argument_list|)
decl_stmt|;
name|label
operator|=
operator|new
name|JLabel
argument_list|(
name|priorityViewModel
operator|.
name|getRepresentingIcon
argument_list|()
argument_list|)
expr_stmt|;
name|label
operator|.
name|setToolTipText
argument_list|(
name|priorityViewModel
operator|.
name|getLocalization
argument_list|()
argument_list|)
expr_stmt|;
name|GUIGlobals
operator|.
name|TABLE_ICONS
operator|.
name|put
argument_list|(
name|SpecialField
operator|.
name|PRIORITY
operator|.
name|getFieldName
argument_list|()
argument_list|,
name|label
argument_list|)
expr_stmt|;
comment|// Read icon used for menu
name|SpecialFieldViewModel
name|readViewModel
init|=
operator|new
name|SpecialFieldViewModel
argument_list|(
name|SpecialField
operator|.
name|READ_STATUS
argument_list|)
decl_stmt|;
name|label
operator|=
operator|new
name|JLabel
argument_list|(
name|readViewModel
operator|.
name|getRepresentingIcon
argument_list|()
argument_list|)
expr_stmt|;
name|label
operator|.
name|setToolTipText
argument_list|(
name|readViewModel
operator|.
name|getLocalization
argument_list|()
argument_list|)
expr_stmt|;
name|GUIGlobals
operator|.
name|TABLE_ICONS
operator|.
name|put
argument_list|(
name|SpecialField
operator|.
name|READ_STATUS
operator|.
name|getFieldName
argument_list|()
argument_list|,
name|label
argument_list|)
expr_stmt|;
comment|// Print icon used for menu
name|SpecialFieldViewModel
name|printedViewModel
init|=
operator|new
name|SpecialFieldViewModel
argument_list|(
name|SpecialField
operator|.
name|PRINTED
argument_list|)
decl_stmt|;
name|label
operator|=
operator|new
name|JLabel
argument_list|(
name|printedViewModel
operator|.
name|getRepresentingIcon
argument_list|()
argument_list|)
expr_stmt|;
name|label
operator|.
name|setToolTipText
argument_list|(
name|printedViewModel
operator|.
name|getLocalization
argument_list|()
argument_list|)
expr_stmt|;
name|GUIGlobals
operator|.
name|TABLE_ICONS
operator|.
name|put
argument_list|(
name|SpecialField
operator|.
name|PRINTED
operator|.
name|getFieldName
argument_list|()
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
comment|// Set up entry editor colors, first time:
name|GUIGlobals
operator|.
name|updateEntryEditorColors
argument_list|()
expr_stmt|;
name|GUIGlobals
operator|.
name|currentFont
operator|=
operator|new
name|Font
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|FONT_FAMILY
argument_list|)
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getInt
argument_list|(
name|JabRefPreferences
operator|.
name|FONT_STYLE
argument_list|)
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getInt
argument_list|(
name|JabRefPreferences
operator|.
name|FONT_SIZE
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|setFont (int size)
specifier|public
specifier|static
name|void
name|setFont
parameter_list|(
name|int
name|size
parameter_list|)
block|{
name|currentFont
operator|=
operator|new
name|Font
argument_list|(
name|currentFont
operator|.
name|getFamily
argument_list|()
argument_list|,
name|currentFont
operator|.
name|getStyle
argument_list|()
argument_list|,
name|size
argument_list|)
expr_stmt|;
comment|// update preferences
name|Globals
operator|.
name|prefs
operator|.
name|putInt
argument_list|(
name|JabRefPreferences
operator|.
name|FONT_SIZE
argument_list|,
name|size
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

