begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|event
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
name|Iterator
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

begin_class
DECL|class|GeneralTab
specifier|public
class|class
name|GeneralTab
extends|extends
name|JPanel
implements|implements
name|PrefsTab
block|{
DECL|field|gbl
name|GridBagLayout
name|gbl
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
DECL|field|con
name|GridBagConstraints
name|con
init|=
operator|new
name|GridBagConstraints
argument_list|()
decl_stmt|;
DECL|field|autoOpenForm
DECL|field|backup
DECL|field|openLast
specifier|private
name|JCheckBox
name|autoOpenForm
decl_stmt|,
name|backup
decl_stmt|,
name|openLast
decl_stmt|,
DECL|field|defSource
DECL|field|editSource
DECL|field|defSort
DECL|field|ctrlClick
DECL|field|disableOnMultiple
name|defSource
decl_stmt|,
name|editSource
decl_stmt|,
name|defSort
decl_stmt|,
name|ctrlClick
decl_stmt|,
name|disableOnMultiple
decl_stmt|;
DECL|field|groupField
specifier|private
name|JTextField
name|groupField
init|=
operator|new
name|JTextField
argument_list|(
literal|15
argument_list|)
decl_stmt|;
DECL|field|defOwnerField
specifier|private
name|JTextField
name|defOwnerField
decl_stmt|;
DECL|field|_prefs
name|JabRefPreferences
name|_prefs
decl_stmt|;
DECL|field|_frame
name|JabRefFrame
name|_frame
decl_stmt|;
DECL|field|language
specifier|private
name|JComboBox
name|language
init|=
operator|new
name|JComboBox
argument_list|(
name|GUIGlobals
operator|.
name|LANGUAGES
operator|.
name|keySet
argument_list|()
operator|.
name|toArray
argument_list|()
argument_list|)
decl_stmt|;
name|JTextField
DECL|field|pdfDir
DECL|field|pdf
DECL|field|ps
DECL|field|html
DECL|field|lyx
name|pdfDir
decl_stmt|,
name|pdf
decl_stmt|,
name|ps
decl_stmt|,
name|html
decl_stmt|,
name|lyx
decl_stmt|;
DECL|method|GeneralTab (JabRefFrame frame, JabRefPreferences prefs)
specifier|public
name|GeneralTab
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|JabRefPreferences
name|prefs
parameter_list|)
block|{
name|_prefs
operator|=
name|prefs
expr_stmt|;
name|_frame
operator|=
name|frame
expr_stmt|;
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|0
expr_stmt|;
comment|//con.insets = new Insets(10, 10, 10, 10);
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|5
argument_list|,
literal|10
argument_list|,
literal|5
argument_list|,
literal|10
argument_list|)
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|HORIZONTAL
expr_stmt|;
name|autoOpenForm
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Open editor when a new entry is created"
argument_list|)
argument_list|,
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"autoOpenForm"
argument_list|)
argument_list|)
expr_stmt|;
name|openLast
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Open last edited databases at startup"
argument_list|)
argument_list|,
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"openLastEdited"
argument_list|)
argument_list|)
expr_stmt|;
name|backup
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Backup old file when saving"
argument_list|)
argument_list|,
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"backup"
argument_list|)
argument_list|)
expr_stmt|;
name|defSource
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Show BibTeX source by default"
argument_list|)
argument_list|,
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"defaultShowSource"
argument_list|)
argument_list|)
expr_stmt|;
name|editSource
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Enable source editing"
argument_list|)
argument_list|,
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"enableSourceEditing"
argument_list|)
argument_list|)
expr_stmt|;
name|defSort
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Sort Automatically"
argument_list|)
argument_list|,
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"defaultAutoSort"
argument_list|)
argument_list|)
expr_stmt|;
name|ctrlClick
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Open right-click menu with Ctrl+left button"
argument_list|)
argument_list|,
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"ctrlClick"
argument_list|)
argument_list|)
expr_stmt|;
name|disableOnMultiple
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Disable entry editor when multiple entries are selected"
argument_list|)
argument_list|,
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"disableOnMultipleSelection"
argument_list|)
argument_list|)
expr_stmt|;
name|JPanel
name|general
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|,
name|external
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|defOwnerField
operator|=
operator|new
name|JTextField
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
literal|"defaultOwner"
argument_list|)
argument_list|)
expr_stmt|;
name|groupField
operator|=
operator|new
name|JTextField
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
literal|"groupsDefaultField"
argument_list|)
argument_list|,
literal|15
argument_list|)
expr_stmt|;
name|general
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createTitledBorder
argument_list|(
name|BorderFactory
operator|.
name|createEtchedBorder
argument_list|()
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Miscellaneous"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|external
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createTitledBorder
argument_list|(
name|BorderFactory
operator|.
name|createEtchedBorder
argument_list|()
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"External programs"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|general
operator|.
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|external
operator|.
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|openLast
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|general
operator|.
name|add
argument_list|(
name|openLast
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|autoOpenForm
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|general
operator|.
name|add
argument_list|(
name|autoOpenForm
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|backup
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|general
operator|.
name|add
argument_list|(
name|backup
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|defSource
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|general
operator|.
name|add
argument_list|(
name|defSource
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|disableOnMultiple
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|general
operator|.
name|add
argument_list|(
name|disableOnMultiple
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|ctrlClick
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|general
operator|.
name|add
argument_list|(
name|ctrlClick
argument_list|)
expr_stmt|;
comment|//con.gridwidth = GridBagConstraints.REMAINDER;
comment|//gbl.setConstraints(defSort, con);
comment|//general.add(defSort);
comment|// Default owner
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|JLabel
name|lab
init|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Default owner"
argument_list|)
operator|+
literal|":"
argument_list|)
decl_stmt|;
name|lab
operator|.
name|setHorizontalAlignment
argument_list|(
name|SwingConstants
operator|.
name|LEFT
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|lab
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|general
operator|.
name|add
argument_list|(
name|lab
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|defOwnerField
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|general
operator|.
name|add
argument_list|(
name|defOwnerField
argument_list|)
expr_stmt|;
comment|// Grouping field
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|lab
operator|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Default grouping field"
argument_list|)
operator|+
literal|":"
argument_list|)
expr_stmt|;
name|lab
operator|.
name|setHorizontalAlignment
argument_list|(
name|SwingConstants
operator|.
name|LEFT
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|lab
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|general
operator|.
name|add
argument_list|(
name|lab
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|groupField
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|general
operator|.
name|add
argument_list|(
name|groupField
argument_list|)
expr_stmt|;
comment|// Language choice
name|String
name|oldLan
init|=
name|_prefs
operator|.
name|get
argument_list|(
literal|"language"
argument_list|)
decl_stmt|;
name|int
name|ilk
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|GUIGlobals
operator|.
name|LANGUAGES
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
if|if
condition|(
name|GUIGlobals
operator|.
name|LANGUAGES
operator|.
name|get
argument_list|(
name|i
operator|.
name|next
argument_list|()
argument_list|)
operator|.
name|equals
argument_list|(
name|oldLan
argument_list|)
condition|)
block|{
name|language
operator|.
name|setSelectedIndex
argument_list|(
name|ilk
argument_list|)
expr_stmt|;
block|}
name|ilk
operator|++
expr_stmt|;
block|}
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|lab
operator|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Language"
argument_list|)
operator|+
literal|":"
argument_list|)
expr_stmt|;
name|lab
operator|.
name|setHorizontalAlignment
argument_list|(
name|SwingConstants
operator|.
name|LEFT
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|lab
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|general
operator|.
name|add
argument_list|(
name|lab
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|language
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|general
operator|.
name|add
argument_list|(
name|language
argument_list|)
expr_stmt|;
comment|// ------------------------------------------------------------
comment|// External programs panel.
comment|// ------------------------------------------------------------
name|pdfDir
operator|=
operator|new
name|JTextField
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
literal|"pdfDirectory"
argument_list|)
argument_list|,
literal|30
argument_list|)
expr_stmt|;
name|pdf
operator|=
operator|new
name|JTextField
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
literal|"pdfviewer"
argument_list|)
argument_list|,
literal|30
argument_list|)
expr_stmt|;
name|ps
operator|=
operator|new
name|JTextField
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
literal|"psviewer"
argument_list|)
argument_list|,
literal|30
argument_list|)
expr_stmt|;
name|html
operator|=
operator|new
name|JTextField
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
literal|"htmlviewer"
argument_list|)
argument_list|,
literal|30
argument_list|)
expr_stmt|;
name|lyx
operator|=
operator|new
name|JTextField
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
literal|"lyxpipe"
argument_list|)
argument_list|,
literal|30
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|5
argument_list|,
literal|10
argument_list|,
literal|15
argument_list|,
literal|10
argument_list|)
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|HORIZONTAL
expr_stmt|;
name|lab
operator|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Main PDF directory"
argument_list|)
operator|+
literal|":"
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|lab
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|external
operator|.
name|add
argument_list|(
name|lab
argument_list|)
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|pdfDir
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|external
operator|.
name|add
argument_list|(
name|pdfDir
argument_list|)
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|JButton
name|browse
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Browse"
argument_list|)
argument_list|)
decl_stmt|;
name|browse
operator|.
name|addActionListener
argument_list|(
operator|new
name|BrowseAction
argument_list|(
name|pdfDir
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|browse
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|external
operator|.
name|add
argument_list|(
name|browse
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|HORIZONTAL
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|5
argument_list|,
literal|10
argument_list|,
literal|5
argument_list|,
literal|10
argument_list|)
expr_stmt|;
name|lab
operator|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Path to PDF viewer"
argument_list|)
operator|+
literal|":"
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|lab
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|external
operator|.
name|add
argument_list|(
name|lab
argument_list|)
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|pdf
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|external
operator|.
name|add
argument_list|(
name|pdf
argument_list|)
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|browse
operator|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Browse"
argument_list|)
argument_list|)
expr_stmt|;
name|browse
operator|.
name|addActionListener
argument_list|(
operator|new
name|BrowseAction
argument_list|(
name|pdf
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|browse
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|external
operator|.
name|add
argument_list|(
name|browse
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|HORIZONTAL
expr_stmt|;
name|lab
operator|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Path to PS viewer"
argument_list|)
operator|+
literal|":"
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|lab
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|external
operator|.
name|add
argument_list|(
name|lab
argument_list|)
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|ps
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|external
operator|.
name|add
argument_list|(
name|ps
argument_list|)
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|browse
operator|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Browse"
argument_list|)
argument_list|)
expr_stmt|;
name|browse
operator|.
name|addActionListener
argument_list|(
operator|new
name|BrowseAction
argument_list|(
name|ps
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|browse
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|external
operator|.
name|add
argument_list|(
name|browse
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|lab
operator|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Path to HTML viewer"
argument_list|)
operator|+
literal|":"
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|lab
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|external
operator|.
name|add
argument_list|(
name|lab
argument_list|)
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|html
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|external
operator|.
name|add
argument_list|(
name|html
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|0
expr_stmt|;
name|browse
operator|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Browse"
argument_list|)
argument_list|)
expr_stmt|;
name|browse
operator|.
name|addActionListener
argument_list|(
operator|new
name|BrowseAction
argument_list|(
name|html
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|browse
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|external
operator|.
name|add
argument_list|(
name|browse
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|HORIZONTAL
expr_stmt|;
name|lab
operator|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Path to LyX pipe"
argument_list|)
operator|+
literal|":"
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|lab
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|external
operator|.
name|add
argument_list|(
name|lab
argument_list|)
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|lyx
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|external
operator|.
name|add
argument_list|(
name|lyx
argument_list|)
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|0
expr_stmt|;
name|browse
operator|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Browse"
argument_list|)
argument_list|)
expr_stmt|;
name|browse
operator|.
name|addActionListener
argument_list|(
operator|new
name|BrowseAction
argument_list|(
name|lyx
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|browse
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|external
operator|.
name|add
argument_list|(
name|browse
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|general
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|general
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|external
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|external
argument_list|)
expr_stmt|;
block|}
comment|/**    * Action used to produce a "Browse" button for one of the text fields.    */
DECL|class|BrowseAction
class|class
name|BrowseAction
extends|extends
name|AbstractAction
block|{
DECL|field|comp
name|JTextField
name|comp
decl_stmt|;
DECL|field|dir
name|boolean
name|dir
decl_stmt|;
DECL|method|BrowseAction (JTextField tc, boolean dir)
specifier|public
name|BrowseAction
parameter_list|(
name|JTextField
name|tc
parameter_list|,
name|boolean
name|dir
parameter_list|)
block|{
name|super
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Browse"
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|dir
operator|=
name|dir
expr_stmt|;
name|comp
operator|=
name|tc
expr_stmt|;
block|}
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|String
name|chosen
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|dir
condition|)
name|chosen
operator|=
name|Globals
operator|.
name|getNewDir
argument_list|(
name|_frame
argument_list|,
name|_prefs
argument_list|,
operator|new
name|File
argument_list|(
name|comp
operator|.
name|getText
argument_list|()
argument_list|)
argument_list|,
name|Globals
operator|.
name|NONE
argument_list|,
name|JFileChooser
operator|.
name|OPEN_DIALOG
argument_list|,
literal|false
argument_list|)
expr_stmt|;
else|else
name|chosen
operator|=
name|Globals
operator|.
name|getNewFile
argument_list|(
name|_frame
argument_list|,
name|_prefs
argument_list|,
operator|new
name|File
argument_list|(
name|comp
operator|.
name|getText
argument_list|()
argument_list|)
argument_list|,
name|Globals
operator|.
name|NONE
argument_list|,
name|JFileChooser
operator|.
name|OPEN_DIALOG
argument_list|,
literal|false
argument_list|)
expr_stmt|;
if|if
condition|(
name|chosen
operator|!=
literal|null
condition|)
block|{
name|File
name|newFile
init|=
operator|new
name|File
argument_list|(
name|chosen
argument_list|)
decl_stmt|;
name|comp
operator|.
name|setText
argument_list|(
name|newFile
operator|.
name|getPath
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|storeSettings ()
specifier|public
name|void
name|storeSettings
parameter_list|()
block|{
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"autoOpenForm"
argument_list|,
name|autoOpenForm
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"backup"
argument_list|,
name|backup
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"openLastEdited"
argument_list|,
name|openLast
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"defaultShowSource"
argument_list|,
name|defSource
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"enableSourceEditing"
argument_list|,
name|editSource
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"disableOnMultipleSelection"
argument_list|,
name|disableOnMultiple
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"ctrlClick"
argument_list|,
name|ctrlClick
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
comment|//_prefs.putBoolean("defaultAutoSort", defSort.isSelected());
name|_prefs
operator|.
name|put
argument_list|(
literal|"defaultOwner"
argument_list|,
name|defOwnerField
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|put
argument_list|(
literal|"groupsDefaultField"
argument_list|,
name|groupField
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
comment|// We should maybe do some checking on the validity of the contents?
name|_prefs
operator|.
name|put
argument_list|(
literal|"pdfDirectory"
argument_list|,
name|pdfDir
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|put
argument_list|(
literal|"pdfviewer"
argument_list|,
name|pdf
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|put
argument_list|(
literal|"psviewer"
argument_list|,
name|ps
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|put
argument_list|(
literal|"htmlviewer"
argument_list|,
name|html
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|put
argument_list|(
literal|"lyxpipe"
argument_list|,
name|lyx
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|GUIGlobals
operator|.
name|LANGUAGES
operator|.
name|get
argument_list|(
name|language
operator|.
name|getSelectedItem
argument_list|()
argument_list|)
operator|.
name|equals
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
literal|"language"
argument_list|)
argument_list|)
condition|)
block|{
name|_prefs
operator|.
name|put
argument_list|(
literal|"language"
argument_list|,
name|GUIGlobals
operator|.
name|LANGUAGES
operator|.
name|get
argument_list|(
name|language
operator|.
name|getSelectedItem
argument_list|()
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|setLanguage
argument_list|(
name|GUIGlobals
operator|.
name|LANGUAGES
operator|.
name|get
argument_list|(
name|language
operator|.
name|getSelectedItem
argument_list|()
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
literal|null
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"You have changed the language setting. "
operator|+
literal|"You must restart JabRef for this to come into effect."
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Changed language settings"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|WARNING_MESSAGE
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

