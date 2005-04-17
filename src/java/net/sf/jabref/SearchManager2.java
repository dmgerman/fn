begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 JabRef team  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
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
name|util
operator|.
name|Hashtable
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
name|javax
operator|.
name|swing
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
name|event
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
name|event
operator|.
name|ChangeListener
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|ChangeEvent
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
name|search
operator|.
name|*
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
name|search
operator|.
name|SearchExpression
import|;
end_import

begin_class
DECL|class|SearchManager2
class|class
name|SearchManager2
extends|extends
name|SidePaneComponent
implements|implements
name|ActionListener
implements|,
name|KeyListener
implements|,
name|ItemListener
implements|,
name|CaretListener
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
DECL|field|incSearcher
name|IncrementalSearcher
name|incSearcher
decl_stmt|;
comment|//private JabRefFrame frame;
DECL|field|searchField
specifier|private
name|JTextField
name|searchField
init|=
operator|new
name|JTextField
argument_list|(
literal|""
argument_list|,
literal|12
argument_list|)
decl_stmt|;
DECL|field|lab
specifier|private
name|JLabel
name|lab
init|=
comment|//new JLabel(Globals.lang("Search")+":");
operator|new
name|JLabel
argument_list|(
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|searchIconFile
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|settings
specifier|private
name|JPopupMenu
name|settings
init|=
operator|new
name|JPopupMenu
argument_list|()
decl_stmt|;
DECL|field|openset
specifier|private
name|JButton
name|openset
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Settings"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|escape
specifier|private
name|JButton
name|escape
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Clear"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|help
specifier|private
name|JButton
name|help
init|=
operator|new
name|JButton
argument_list|(
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|helpIconFile
argument_list|)
argument_list|)
decl_stmt|;
comment|/** This button's text will be set later. */
DECL|field|search
specifier|private
name|JButton
name|search
init|=
operator|new
name|JButton
argument_list|()
decl_stmt|;
DECL|field|searchReq
DECL|field|searchOpt
DECL|field|searchGen
specifier|private
name|JCheckBoxMenuItem
name|searchReq
decl_stmt|,
name|searchOpt
decl_stmt|,
name|searchGen
decl_stmt|,
DECL|field|searchAll
DECL|field|caseSensitive
DECL|field|regExpSearch
name|searchAll
decl_stmt|,
name|caseSensitive
decl_stmt|,
name|regExpSearch
decl_stmt|;
DECL|field|increment
DECL|field|highlight
DECL|field|reorder
specifier|private
name|JRadioButton
name|increment
decl_stmt|,
name|highlight
decl_stmt|,
name|reorder
decl_stmt|;
DECL|field|select
specifier|private
name|JCheckBoxMenuItem
name|select
decl_stmt|;
DECL|field|types
specifier|private
name|ButtonGroup
name|types
init|=
operator|new
name|ButtonGroup
argument_list|()
decl_stmt|;
DECL|field|ths
specifier|private
name|SearchManager2
name|ths
init|=
name|this
decl_stmt|;
DECL|field|incSearch
specifier|private
name|boolean
name|incSearch
init|=
literal|false
decl_stmt|;
DECL|field|incSearchPos
specifier|private
name|int
name|incSearchPos
init|=
operator|-
literal|1
decl_stmt|;
comment|// To keep track of where we are in
comment|// an incremental search. -1 means
comment|// that the search is inactive.
DECL|method|SearchManager2 (SidePaneManager manager)
specifier|public
name|SearchManager2
parameter_list|(
name|SidePaneManager
name|manager
parameter_list|)
block|{
name|super
argument_list|(
name|manager
argument_list|,
name|GUIGlobals
operator|.
name|searchIconFile
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Search"
argument_list|)
argument_list|)
expr_stmt|;
name|incSearcher
operator|=
operator|new
name|IncrementalSearcher
argument_list|(
name|Globals
operator|.
name|prefs
argument_list|)
expr_stmt|;
comment|//setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.magenta));
name|searchReq
operator|=
operator|new
name|JCheckBoxMenuItem
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Search required fields"
argument_list|)
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"searchReq"
argument_list|)
argument_list|)
expr_stmt|;
name|searchOpt
operator|=
operator|new
name|JCheckBoxMenuItem
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Search optional fields"
argument_list|)
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"searchOpt"
argument_list|)
argument_list|)
expr_stmt|;
name|searchGen
operator|=
operator|new
name|JCheckBoxMenuItem
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Search general fields"
argument_list|)
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"searchGen"
argument_list|)
argument_list|)
expr_stmt|;
name|searchAll
operator|=
operator|new
name|JCheckBoxMenuItem
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Search all fields"
argument_list|)
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"searchAll"
argument_list|)
argument_list|)
expr_stmt|;
name|regExpSearch
operator|=
operator|new
name|JCheckBoxMenuItem
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Use regular expressions"
argument_list|)
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"regExpSearch"
argument_list|)
argument_list|)
expr_stmt|;
name|increment
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Incremental"
argument_list|)
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|highlight
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Highlight"
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|reorder
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Float"
argument_list|)
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|types
operator|.
name|add
argument_list|(
name|increment
argument_list|)
expr_stmt|;
name|types
operator|.
name|add
argument_list|(
name|highlight
argument_list|)
expr_stmt|;
name|types
operator|.
name|add
argument_list|(
name|reorder
argument_list|)
expr_stmt|;
name|select
operator|=
operator|new
name|JCheckBoxMenuItem
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Select matches"
argument_list|)
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|increment
operator|.
name|setToolTipText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Incremental search"
argument_list|)
argument_list|)
expr_stmt|;
name|highlight
operator|.
name|setToolTipText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Gray out non-matching entries"
argument_list|)
argument_list|)
expr_stmt|;
name|reorder
operator|.
name|setToolTipText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Move matching entries to the top"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Add an item listener that makes sure we only listen for key events
comment|// when incremental search is turned on.
name|increment
operator|.
name|addItemListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|reorder
operator|.
name|addItemListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
comment|// Add the global focus listener, so a menu item can see if this field was focused when
comment|// an action was called.
name|searchField
operator|.
name|addFocusListener
argument_list|(
name|Globals
operator|.
name|focusListener
argument_list|)
expr_stmt|;
if|if
condition|(
name|searchAll
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|searchReq
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|searchOpt
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|searchGen
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
name|searchAll
operator|.
name|addChangeListener
argument_list|(
operator|new
name|ChangeListener
argument_list|()
block|{
specifier|public
name|void
name|stateChanged
parameter_list|(
name|ChangeEvent
name|event
parameter_list|)
block|{
name|boolean
name|state
init|=
operator|!
name|searchAll
operator|.
name|isSelected
argument_list|()
decl_stmt|;
name|searchReq
operator|.
name|setEnabled
argument_list|(
name|state
argument_list|)
expr_stmt|;
name|searchOpt
operator|.
name|setEnabled
argument_list|(
name|state
argument_list|)
expr_stmt|;
name|searchGen
operator|.
name|setEnabled
argument_list|(
name|state
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|caseSensitive
operator|=
operator|new
name|JCheckBoxMenuItem
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Case sensitive"
argument_list|)
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"caseSensitiveSearch"
argument_list|)
argument_list|)
expr_stmt|;
name|settings
operator|.
name|add
argument_list|(
name|select
argument_list|)
expr_stmt|;
comment|// 2005.03.29, trying to remove field category searches, to simplify
comment|// search usability.
comment|//settings.addSeparator();
comment|//settings.add(searchReq);
comment|//settings.add(searchOpt);
comment|//settings.add(searchGen);
comment|//settings.addSeparator();
comment|//settings.add(searchAll);
comment|// ---------------------------------------------------------------
name|settings
operator|.
name|addSeparator
argument_list|()
expr_stmt|;
name|settings
operator|.
name|add
argument_list|(
name|caseSensitive
argument_list|)
expr_stmt|;
name|settings
operator|.
name|add
argument_list|(
name|regExpSearch
argument_list|)
expr_stmt|;
comment|//settings.addSeparator();
name|searchField
operator|.
name|addActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|searchField
operator|.
name|addCaretListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|search
operator|.
name|addActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|searchField
operator|.
name|addFocusListener
argument_list|(
operator|new
name|FocusAdapter
argument_list|()
block|{
specifier|public
name|void
name|focusGained
parameter_list|(
name|FocusEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|increment
operator|.
name|isSelected
argument_list|()
condition|)
name|searchField
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|focusLost
parameter_list|(
name|FocusEvent
name|e
parameter_list|)
block|{
name|incSearch
operator|=
literal|false
expr_stmt|;
name|incSearchPos
operator|=
operator|-
literal|1
expr_stmt|;
comment|// Reset incremental
comment|// search. This makes the
comment|// incremental search reset
comment|// once the user moves focus to
comment|// somewhere else.
if|if
condition|(
name|increment
operator|.
name|isSelected
argument_list|()
condition|)
block|{
comment|//searchField.setText("");
comment|//System.out.println("focuslistener");
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|escape
operator|.
name|addActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|openset
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|settings
operator|.
name|isVisible
argument_list|()
condition|)
block|{
comment|//System.out.println("oee");
comment|//settings.setVisible(false);
block|}
else|else
block|{
name|JButton
name|src
init|=
operator|(
name|JButton
operator|)
name|e
operator|.
name|getSource
argument_list|()
decl_stmt|;
name|settings
operator|.
name|show
argument_list|(
name|src
argument_list|,
literal|0
argument_list|,
name|openset
operator|.
name|getHeight
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|Insets
name|margin
init|=
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|2
argument_list|,
literal|0
argument_list|,
literal|2
argument_list|)
decl_stmt|;
comment|//search.setMargin(margin);
name|escape
operator|.
name|setMargin
argument_list|(
name|margin
argument_list|)
expr_stmt|;
name|openset
operator|.
name|setMargin
argument_list|(
name|margin
argument_list|)
expr_stmt|;
name|Dimension
name|butDim
init|=
operator|new
name|Dimension
argument_list|(
literal|20
argument_list|,
literal|20
argument_list|)
decl_stmt|;
name|help
operator|.
name|setPreferredSize
argument_list|(
name|butDim
argument_list|)
expr_stmt|;
name|help
operator|.
name|setMinimumSize
argument_list|(
name|butDim
argument_list|)
expr_stmt|;
name|help
operator|.
name|setMargin
argument_list|(
name|margin
argument_list|)
expr_stmt|;
name|help
operator|.
name|addActionListener
argument_list|(
operator|new
name|HelpAction
argument_list|(
name|Globals
operator|.
name|helpDiag
argument_list|,
name|GUIGlobals
operator|.
name|searchHelp
argument_list|,
literal|"Help"
argument_list|)
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
literal|"incrementS"
argument_list|)
condition|)
name|increment
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
operator|!
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"selectS"
argument_list|)
condition|)
name|reorder
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|JPanel
name|main
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|main
operator|.
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
comment|//SidePaneHeader header = new SidePaneHeader("Search", GUIGlobals.searchIconFile, this);
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
name|fill
operator|=
name|GridBagConstraints
operator|.
name|BOTH
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
comment|//con.insets = new Insets(0, 0, 2,  0);
comment|//gbl.setConstraints(header, con);
comment|//add(header);
comment|//con.insets = new Insets(0, 0, 0,  0);
name|gbl
operator|.
name|setConstraints
argument_list|(
name|searchField
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|searchField
argument_list|)
expr_stmt|;
comment|//con.gridwidth = 1;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|search
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|search
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
name|escape
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|escape
argument_list|)
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|2
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|increment
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|increment
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|highlight
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|highlight
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|reorder
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|reorder
argument_list|)
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|JPanel
name|pan
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|GridBagLayout
name|gb
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|pan
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|pan
operator|.
name|setLayout
argument_list|(
name|gb
argument_list|)
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|gb
operator|.
name|setConstraints
argument_list|(
name|openset
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|pan
operator|.
name|add
argument_list|(
name|openset
argument_list|)
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|0
expr_stmt|;
name|gb
operator|.
name|setConstraints
argument_list|(
name|help
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|pan
operator|.
name|add
argument_list|(
name|help
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|pan
argument_list|)
expr_stmt|;
name|main
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|main
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|searchField
operator|.
name|getInputMap
argument_list|()
operator|.
name|put
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getKey
argument_list|(
literal|"Repeat incremental search"
argument_list|)
argument_list|,
literal|"repeat"
argument_list|)
expr_stmt|;
name|searchField
operator|.
name|getActionMap
argument_list|()
operator|.
name|put
argument_list|(
literal|"repeat"
argument_list|,
operator|new
name|AbstractAction
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|increment
operator|.
name|isSelected
argument_list|()
condition|)
name|repeatIncremental
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|searchField
operator|.
name|getInputMap
argument_list|()
operator|.
name|put
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getKey
argument_list|(
literal|"Clear search"
argument_list|)
argument_list|,
literal|"escape"
argument_list|)
expr_stmt|;
name|searchField
operator|.
name|getActionMap
argument_list|()
operator|.
name|put
argument_list|(
literal|"escape"
argument_list|,
operator|new
name|AbstractAction
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|ths
operator|.
name|actionPerformed
argument_list|(
operator|new
name|ActionEvent
argument_list|(
name|escape
argument_list|,
literal|0
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|setSearchButtonSizes
argument_list|()
expr_stmt|;
name|updateSearchButtonText
argument_list|()
expr_stmt|;
block|}
comment|/** force the search button to be large enough for      * the longer of the two texts */
DECL|method|setSearchButtonSizes ()
specifier|private
name|void
name|setSearchButtonSizes
parameter_list|()
block|{
name|search
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Search Specified Field(s)"
argument_list|)
argument_list|)
expr_stmt|;
name|Dimension
name|size1
init|=
name|search
operator|.
name|getPreferredSize
argument_list|()
decl_stmt|;
name|search
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Search All Fields"
argument_list|)
argument_list|)
expr_stmt|;
name|Dimension
name|size2
init|=
name|search
operator|.
name|getPreferredSize
argument_list|()
decl_stmt|;
name|size2
operator|.
name|width
operator|=
name|Math
operator|.
name|max
argument_list|(
name|size1
operator|.
name|width
argument_list|,
name|size2
operator|.
name|width
argument_list|)
expr_stmt|;
name|search
operator|.
name|setMinimumSize
argument_list|(
name|size2
argument_list|)
expr_stmt|;
name|search
operator|.
name|setPreferredSize
argument_list|(
name|size2
argument_list|)
expr_stmt|;
block|}
DECL|method|updatePrefs ()
specifier|public
name|void
name|updatePrefs
parameter_list|()
block|{
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
literal|"searchReq"
argument_list|,
name|searchReq
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
literal|"searchOpt"
argument_list|,
name|searchOpt
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
literal|"searchGen"
argument_list|,
name|searchGen
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
literal|"searchAll"
argument_list|,
name|searchAll
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
literal|"incrementS"
argument_list|,
name|increment
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
literal|"selectS"
argument_list|,
name|highlight
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
comment|//	Globals.prefs.putBoolean("grayOutNonHits", grayOut.isSelected());
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
literal|"caseSensitiveSearch"
argument_list|,
name|caseSensitive
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
literal|"regExpSearch"
argument_list|,
name|regExpSearch
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|startIncrementalSearch ()
specifier|public
name|void
name|startIncrementalSearch
parameter_list|()
block|{
name|increment
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|searchField
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
comment|//System.out.println("startIncrementalSearch");
name|searchField
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
block|}
comment|/**      * Clears and focuses the search field if it is not      * focused. Otherwise, cycles to the next search type.      */
DECL|method|startSearch ()
specifier|public
name|void
name|startSearch
parameter_list|()
block|{
if|if
condition|(
name|increment
operator|.
name|isSelected
argument_list|()
operator|&&
name|incSearch
condition|)
block|{
name|repeatIncremental
argument_list|()
expr_stmt|;
return|return;
block|}
if|if
condition|(
operator|!
name|searchField
operator|.
name|hasFocus
argument_list|()
condition|)
block|{
comment|//searchField.setText("");
name|searchField
operator|.
name|selectAll
argument_list|()
expr_stmt|;
name|searchField
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|increment
operator|.
name|isSelected
argument_list|()
condition|)
name|highlight
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|highlight
operator|.
name|isSelected
argument_list|()
condition|)
name|reorder
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
else|else
block|{
name|increment
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
name|increment
operator|.
name|revalidate
argument_list|()
expr_stmt|;
name|increment
operator|.
name|repaint
argument_list|()
expr_stmt|;
name|searchField
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
block|}
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
if|if
condition|(
name|e
operator|.
name|getSource
argument_list|()
operator|==
name|escape
condition|)
block|{
name|incSearch
operator|=
literal|false
expr_stmt|;
if|if
condition|(
name|panel
operator|!=
literal|null
condition|)
block|{
operator|(
operator|new
name|Thread
argument_list|()
block|{
specifier|public
name|void
name|run
parameter_list|()
block|{
name|panel
operator|.
name|stopShowingSearchResults
argument_list|()
expr_stmt|;
block|}
block|}
operator|)
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
operator|(
operator|(
name|e
operator|.
name|getSource
argument_list|()
operator|==
name|searchField
operator|)
operator|||
operator|(
name|e
operator|.
name|getSource
argument_list|()
operator|==
name|search
operator|)
operator|)
operator|&&
operator|!
name|increment
operator|.
name|isSelected
argument_list|()
operator|&&
operator|(
name|panel
operator|!=
literal|null
operator|)
condition|)
block|{
name|updatePrefs
argument_list|()
expr_stmt|;
comment|// Make sure the user's choices are recorded.
if|if
condition|(
name|searchField
operator|.
name|getText
argument_list|()
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
comment|// An empty search field should cause the search to be cleared.
name|panel
operator|.
name|stopShowingSearchResults
argument_list|()
expr_stmt|;
return|return;
block|}
comment|// Setup search parameters common to both highlight and float.
name|Hashtable
name|searchOptions
init|=
operator|new
name|Hashtable
argument_list|()
decl_stmt|;
name|searchOptions
operator|.
name|put
argument_list|(
literal|"option"
argument_list|,
name|searchField
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|SearchRuleSet
name|searchRules
init|=
operator|new
name|SearchRuleSet
argument_list|()
decl_stmt|;
name|SearchRule
name|rule1
decl_stmt|;
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"regExpSearch"
argument_list|)
condition|)
name|rule1
operator|=
operator|new
name|RegExpRule
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"caseSensitiveSearch"
argument_list|)
argument_list|)
expr_stmt|;
else|else
name|rule1
operator|=
operator|new
name|SimpleSearchRule
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"caseSensitiveSearch"
argument_list|)
argument_list|)
expr_stmt|;
try|try
block|{
comment|// this searches specified fields if specified,
comment|// and all fields otherwise
name|rule1
operator|=
operator|new
name|SearchExpression
argument_list|(
name|Globals
operator|.
name|prefs
argument_list|,
name|searchOptions
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
comment|// we'll do a search in all fields
block|}
comment|//		} catch (PatternSyntaxException ex) {
comment|//			System.out.println(ex);
comment|//			return;
comment|//		} catch (TokenStreamException ex) {
comment|//			System.out.println(ex);
comment|//			return;
comment|//		} catch (RecognitionException ex) {
comment|//			System.out.println(ex);
comment|//			return;
comment|//		}
name|searchRules
operator|.
name|addRule
argument_list|(
name|rule1
argument_list|)
expr_stmt|;
if|if
condition|(
name|reorder
operator|.
name|isSelected
argument_list|()
condition|)
block|{
comment|// Float search.
name|DatabaseSearch
name|search
init|=
operator|new
name|DatabaseSearch
argument_list|(
name|searchOptions
argument_list|,
name|searchRules
argument_list|,
name|panel
argument_list|,
name|Globals
operator|.
name|SEARCH
argument_list|,
literal|true
argument_list|,
literal|true
comment|/*Globals.Globals.prefs.getBoolean("grayOutNonHits")*/
argument_list|,
name|select
operator|.
name|isSelected
argument_list|()
argument_list|)
decl_stmt|;
name|search
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|highlight
operator|.
name|isSelected
argument_list|()
condition|)
block|{
comment|// Highlight search.
name|DatabaseSearch
name|search
init|=
operator|new
name|DatabaseSearch
argument_list|(
name|searchOptions
argument_list|,
name|searchRules
argument_list|,
name|panel
argument_list|,
name|Globals
operator|.
name|SEARCH
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|,
name|select
operator|.
name|isSelected
argument_list|()
argument_list|)
decl_stmt|;
name|search
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
comment|// Afterwards, select all text in the search field.
name|searchField
operator|.
name|select
argument_list|(
literal|0
argument_list|,
name|searchField
operator|.
name|getText
argument_list|()
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
comment|//new FocusRequester(frame.basePanel().entryTable);
block|}
block|}
DECL|method|itemStateChanged (ItemEvent e)
specifier|public
name|void
name|itemStateChanged
parameter_list|(
name|ItemEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|.
name|getSource
argument_list|()
operator|==
name|increment
condition|)
block|{
if|if
condition|(
name|increment
operator|.
name|isSelected
argument_list|()
condition|)
name|searchField
operator|.
name|addKeyListener
argument_list|(
name|ths
argument_list|)
expr_stmt|;
else|else
name|searchField
operator|.
name|removeKeyListener
argument_list|(
name|ths
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|e
operator|.
name|getSource
argument_list|()
operator|==
name|reorder
condition|)
block|{
comment|// If this search type is disabled, remove reordering from
comment|// all databases.
if|if
condition|(
operator|!
name|reorder
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|panel
operator|.
name|stopShowingSearchResults
argument_list|()
expr_stmt|;
block|}
block|}
block|}
DECL|method|repeatIncremental ()
specifier|private
name|void
name|repeatIncremental
parameter_list|()
block|{
name|incSearchPos
operator|++
expr_stmt|;
if|if
condition|(
name|panel
operator|!=
literal|null
condition|)
name|goIncremental
argument_list|()
expr_stmt|;
block|}
comment|/**      * Used for incremental search. Only activated when incremental      * is selected.      *      * The variable incSearchPos keeps track of which entry was last      * checked.      */
DECL|method|keyTyped (KeyEvent e)
specifier|public
name|void
name|keyTyped
parameter_list|(
name|KeyEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|.
name|isControlDown
argument_list|()
condition|)
block|{
return|return;
block|}
if|if
condition|(
name|panel
operator|!=
literal|null
condition|)
name|goIncremental
argument_list|()
expr_stmt|;
block|}
DECL|method|goIncremental ()
specifier|private
name|void
name|goIncremental
parameter_list|()
block|{
name|incSearch
operator|=
literal|true
expr_stmt|;
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
operator|new
name|Thread
argument_list|()
block|{
specifier|public
name|void
name|run
parameter_list|()
block|{
name|String
name|text
init|=
name|searchField
operator|.
name|getText
argument_list|()
decl_stmt|;
if|if
condition|(
name|incSearchPos
operator|>=
name|panel
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntryCount
argument_list|()
condition|)
block|{
name|panel
operator|.
name|output
argument_list|(
literal|"'"
operator|+
name|text
operator|+
literal|"' : "
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"Incremental search failed. Repeat to search from top."
argument_list|)
operator|+
literal|"."
argument_list|)
expr_stmt|;
name|incSearchPos
operator|=
operator|-
literal|1
expr_stmt|;
return|return;
block|}
if|if
condition|(
name|searchField
operator|.
name|getText
argument_list|()
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
return|return;
if|if
condition|(
name|incSearchPos
operator|<
literal|0
condition|)
name|incSearchPos
operator|=
literal|0
expr_stmt|;
name|BibtexEntry
name|be
init|=
name|panel
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntryById
argument_list|(
name|panel
operator|.
name|tableModel
operator|.
name|getNameFromNumber
argument_list|(
name|incSearchPos
argument_list|)
argument_list|)
decl_stmt|;
while|while
condition|(
operator|!
name|incSearcher
operator|.
name|search
argument_list|(
name|text
argument_list|,
name|be
argument_list|)
condition|)
block|{
name|incSearchPos
operator|++
expr_stmt|;
if|if
condition|(
name|incSearchPos
operator|<
name|panel
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntryCount
argument_list|()
condition|)
name|be
operator|=
name|panel
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntryById
argument_list|(
name|panel
operator|.
name|tableModel
operator|.
name|getNameFromNumber
argument_list|(
name|incSearchPos
argument_list|)
argument_list|)
expr_stmt|;
else|else
block|{
name|panel
operator|.
name|output
argument_list|(
literal|"'"
operator|+
name|text
operator|+
literal|"' : "
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"Incremental search failed. Repeat to search from top."
argument_list|)
argument_list|)
expr_stmt|;
name|incSearchPos
operator|=
operator|-
literal|1
expr_stmt|;
return|return;
block|}
block|}
if|if
condition|(
name|incSearchPos
operator|>=
literal|0
condition|)
block|{
name|panel
operator|.
name|selectSingleEntry
argument_list|(
name|incSearchPos
argument_list|)
expr_stmt|;
name|panel
operator|.
name|output
argument_list|(
literal|"'"
operator|+
name|text
operator|+
literal|"' "
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"found"
argument_list|)
operator|+
literal|"."
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|componentClosing ()
specifier|public
name|void
name|componentClosing
parameter_list|()
block|{
name|panel
operator|.
name|frame
operator|.
name|searchToggle
operator|.
name|setSelected
argument_list|(
literal|false
argument_list|)
expr_stmt|;
if|if
condition|(
name|panel
operator|!=
literal|null
condition|)
name|panel
operator|.
name|stopShowingSearchResults
argument_list|()
expr_stmt|;
block|}
DECL|method|keyPressed (KeyEvent e)
specifier|public
name|void
name|keyPressed
parameter_list|(
name|KeyEvent
name|e
parameter_list|)
block|{}
DECL|method|keyReleased (KeyEvent e)
specifier|public
name|void
name|keyReleased
parameter_list|(
name|KeyEvent
name|e
parameter_list|)
block|{}
DECL|method|caretUpdate (CaretEvent e)
specifier|public
name|void
name|caretUpdate
parameter_list|(
name|CaretEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|.
name|getSource
argument_list|()
operator|==
name|searchField
condition|)
block|{
name|updateSearchButtonText
argument_list|()
expr_stmt|;
block|}
block|}
comment|/** Updates the text on the search button to reflect       * the type of search that will happen on click. */
DECL|method|updateSearchButtonText ()
specifier|private
name|void
name|updateSearchButtonText
parameter_list|()
block|{
name|search
operator|.
name|setText
argument_list|(
name|SearchExpressionParser
operator|.
name|isValidSyntax
argument_list|(
name|searchField
operator|.
name|getText
argument_list|()
argument_list|,
name|caseSensitive
operator|.
name|isSelected
argument_list|()
argument_list|,
name|regExpSearch
operator|.
name|isSelected
argument_list|()
argument_list|)
condition|?
name|Globals
operator|.
name|lang
argument_list|(
literal|"Search Specified Field(s)"
argument_list|)
else|:
name|Globals
operator|.
name|lang
argument_list|(
literal|"Search All Fields"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

