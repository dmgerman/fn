begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.groups
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|groups
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|BorderLayout
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
name|event
operator|.
name|ActionListener
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

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
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|StringTokenizer
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
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
name|ActionMap
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|BorderFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|ButtonGroup
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|InputMap
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JButton
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JCheckBox
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JComponent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JDialog
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JPanel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JRadioButton
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JTextField
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
name|CaretEvent
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
name|CaretListener
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
name|BasePanel
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
name|gui
operator|.
name|undo
operator|.
name|NamedCompound
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
name|logic
operator|.
name|layout
operator|.
name|format
operator|.
name|LatexToUnicodeFormatter
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
name|BibDatabase
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
name|Author
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
name|AuthorList
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
name|BibEntry
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
name|groups
operator|.
name|ExplicitGroup
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
name|groups
operator|.
name|GroupHierarchyType
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
name|groups
operator|.
name|GroupTreeNode
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
name|groups
operator|.
name|WordKeywordGroup
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
name|strings
operator|.
name|StringUtil
import|;
end_import

begin_import
import|import
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|builder
operator|.
name|ButtonBarBuilder
import|;
end_import

begin_import
import|import
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|builder
operator|.
name|FormBuilder
import|;
end_import

begin_import
import|import
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|layout
operator|.
name|FormLayout
import|;
end_import

begin_comment
comment|/**  * Dialog for creating or modifying groups. Operates directly on the Vector containing group information.  */
end_comment

begin_class
DECL|class|AutoGroupDialog
class|class
name|AutoGroupDialog
extends|extends
name|JDialog
implements|implements
name|CaretListener
block|{
DECL|field|remove
specifier|private
specifier|final
name|JTextField
name|remove
init|=
operator|new
name|JTextField
argument_list|(
literal|60
argument_list|)
decl_stmt|;
DECL|field|field
specifier|private
specifier|final
name|JTextField
name|field
init|=
operator|new
name|JTextField
argument_list|(
literal|60
argument_list|)
decl_stmt|;
DECL|field|deliminator
specifier|private
specifier|final
name|JTextField
name|deliminator
init|=
operator|new
name|JTextField
argument_list|(
literal|60
argument_list|)
decl_stmt|;
DECL|field|keywords
specifier|private
specifier|final
name|JRadioButton
name|keywords
init|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Generate groups from keywords in a BibTeX field"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|authors
specifier|private
specifier|final
name|JRadioButton
name|authors
init|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Generate groups for author last names"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|editors
specifier|private
specifier|final
name|JRadioButton
name|editors
init|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Generate groups for editor last names"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|useCustomDelimiter
specifier|private
specifier|final
name|JCheckBox
name|useCustomDelimiter
init|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Use the following delimiter character(s):"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|ok
specifier|private
specifier|final
name|JButton
name|ok
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"OK"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|m_groupsRoot
specifier|private
specifier|final
name|GroupTreeNodeViewModel
name|m_groupsRoot
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|panel
specifier|private
specifier|final
name|BasePanel
name|panel
decl_stmt|;
comment|/**      * @param groupsRoot The original set of groups, which is required as undo information when all groups are cleared.      */
DECL|method|AutoGroupDialog (JabRefFrame jabrefFrame, BasePanel basePanel, GroupTreeNodeViewModel groupsRoot, String defaultField, String defaultRemove, String defaultDeliminator)
specifier|public
name|AutoGroupDialog
parameter_list|(
name|JabRefFrame
name|jabrefFrame
parameter_list|,
name|BasePanel
name|basePanel
parameter_list|,
name|GroupTreeNodeViewModel
name|groupsRoot
parameter_list|,
name|String
name|defaultField
parameter_list|,
name|String
name|defaultRemove
parameter_list|,
name|String
name|defaultDeliminator
parameter_list|)
block|{
name|super
argument_list|(
name|jabrefFrame
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Automatically create groups"
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|frame
operator|=
name|jabrefFrame
expr_stmt|;
name|panel
operator|=
name|basePanel
expr_stmt|;
name|m_groupsRoot
operator|=
name|groupsRoot
expr_stmt|;
name|field
operator|.
name|setText
argument_list|(
name|defaultField
argument_list|)
expr_stmt|;
name|remove
operator|.
name|setText
argument_list|(
name|defaultRemove
argument_list|)
expr_stmt|;
name|deliminator
operator|.
name|setText
argument_list|(
name|defaultDeliminator
argument_list|)
expr_stmt|;
name|useCustomDelimiter
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|ActionListener
name|okListener
init|=
name|e
lambda|->
block|{
name|dispose
argument_list|()
expr_stmt|;
try|try
block|{
name|GroupTreeNode
name|autoGroupsRoot
init|=
name|GroupTreeNode
operator|.
name|fromGroup
argument_list|(
operator|new
name|ExplicitGroup
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Automatically created groups"
argument_list|)
argument_list|,
name|GroupHierarchyType
operator|.
name|INCLUDING
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getKeywordDelimiter
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|keywords
decl_stmt|;
name|String
name|fieldText
init|=
name|field
operator|.
name|getText
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|.
name|trim
argument_list|()
decl_stmt|;
if|if
condition|(
name|this
operator|.
name|keywords
operator|.
name|isSelected
argument_list|()
condition|)
block|{
if|if
condition|(
name|useCustomDelimiter
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|keywords
operator|=
name|findDeliminatedWordsInField
argument_list|(
name|panel
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|fieldText
argument_list|,
name|deliminator
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|keywords
operator|=
name|findAllWordsInField
argument_list|(
name|panel
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|fieldText
argument_list|,
name|remove
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|authors
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|fields
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
literal|2
argument_list|)
decl_stmt|;
name|fields
operator|.
name|add
argument_list|(
name|FieldName
operator|.
name|AUTHOR
argument_list|)
expr_stmt|;
name|keywords
operator|=
name|findAuthorLastNames
argument_list|(
name|panel
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|fields
argument_list|)
expr_stmt|;
name|fieldText
operator|=
name|FieldName
operator|.
name|AUTHOR
expr_stmt|;
block|}
else|else
block|{
comment|// editors.isSelected() as it is a radio button group.
name|List
argument_list|<
name|String
argument_list|>
name|fields
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
literal|2
argument_list|)
decl_stmt|;
name|fields
operator|.
name|add
argument_list|(
name|FieldName
operator|.
name|EDITOR
argument_list|)
expr_stmt|;
name|keywords
operator|=
name|findAuthorLastNames
argument_list|(
name|panel
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|fields
argument_list|)
expr_stmt|;
name|fieldText
operator|=
name|FieldName
operator|.
name|EDITOR
expr_stmt|;
block|}
name|LatexToUnicodeFormatter
name|formatter
init|=
operator|new
name|LatexToUnicodeFormatter
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|keyword
range|:
name|keywords
control|)
block|{
name|WordKeywordGroup
name|group
init|=
operator|new
name|WordKeywordGroup
argument_list|(
name|formatter
operator|.
name|format
argument_list|(
name|keyword
argument_list|)
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
name|fieldText
argument_list|,
name|keyword
argument_list|,
literal|false
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getKeywordDelimiter
argument_list|()
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|autoGroupsRoot
operator|.
name|addChild
argument_list|(
name|GroupTreeNode
operator|.
name|fromGroup
argument_list|(
name|group
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|autoGroupsRoot
operator|.
name|moveTo
argument_list|(
name|m_groupsRoot
operator|.
name|getNode
argument_list|()
argument_list|)
expr_stmt|;
name|NamedCompound
name|ce
init|=
operator|new
name|NamedCompound
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Automatically create groups"
argument_list|)
argument_list|)
decl_stmt|;
name|UndoableAddOrRemoveGroup
name|undo
init|=
operator|new
name|UndoableAddOrRemoveGroup
argument_list|(
name|m_groupsRoot
argument_list|,
operator|new
name|GroupTreeNodeViewModel
argument_list|(
name|autoGroupsRoot
argument_list|)
argument_list|,
name|UndoableAddOrRemoveGroup
operator|.
name|ADD_NODE
argument_list|)
decl_stmt|;
name|ce
operator|.
name|addEdit
argument_list|(
name|undo
argument_list|)
expr_stmt|;
name|panel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
comment|// a change always occurs
name|frame
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Created groups."
argument_list|)
argument_list|)
expr_stmt|;
name|ce
operator|.
name|end
argument_list|()
expr_stmt|;
name|panel
operator|.
name|getUndoManager
argument_list|()
operator|.
name|addEdit
argument_list|(
name|ce
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|exception
parameter_list|)
block|{
name|frame
operator|.
name|showMessage
argument_list|(
name|exception
operator|.
name|getLocalizedMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
decl_stmt|;
name|remove
operator|.
name|addActionListener
argument_list|(
name|okListener
argument_list|)
expr_stmt|;
name|field
operator|.
name|addActionListener
argument_list|(
name|okListener
argument_list|)
expr_stmt|;
name|field
operator|.
name|addCaretListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|AbstractAction
name|cancelAction
init|=
operator|new
name|AbstractAction
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
decl_stmt|;
name|JButton
name|cancel
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
argument_list|)
decl_stmt|;
name|cancel
operator|.
name|addActionListener
argument_list|(
name|cancelAction
argument_list|)
expr_stmt|;
name|ok
operator|.
name|addActionListener
argument_list|(
name|okListener
argument_list|)
expr_stmt|;
comment|// Key bindings:
name|JPanel
name|main
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|ActionMap
name|am
init|=
name|main
operator|.
name|getActionMap
argument_list|()
decl_stmt|;
name|InputMap
name|im
init|=
name|main
operator|.
name|getInputMap
argument_list|(
name|JComponent
operator|.
name|WHEN_IN_FOCUSED_WINDOW
argument_list|)
decl_stmt|;
name|im
operator|.
name|put
argument_list|(
name|Globals
operator|.
name|getKeyPrefs
argument_list|()
operator|.
name|getKey
argument_list|(
name|KeyBinding
operator|.
name|CLOSE_DIALOG
argument_list|)
argument_list|,
literal|"close"
argument_list|)
expr_stmt|;
name|am
operator|.
name|put
argument_list|(
literal|"close"
argument_list|,
name|cancelAction
argument_list|)
expr_stmt|;
name|ButtonGroup
name|bg
init|=
operator|new
name|ButtonGroup
argument_list|()
decl_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|keywords
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|authors
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|editors
argument_list|)
expr_stmt|;
name|keywords
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|FormBuilder
name|b
init|=
name|FormBuilder
operator|.
name|create
argument_list|()
decl_stmt|;
name|b
operator|.
name|layout
argument_list|(
operator|new
name|FormLayout
argument_list|(
literal|"left:20dlu, 4dlu, left:pref, 4dlu, fill:60dlu"
argument_list|,
literal|"p, 2dlu, p, 2dlu, p, 2dlu, p, 2dlu, p, 2dlu, p"
argument_list|)
argument_list|)
expr_stmt|;
name|b
operator|.
name|add
argument_list|(
name|keywords
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|b
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Field to group by"
argument_list|)
operator|+
literal|":"
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|b
operator|.
name|add
argument_list|(
name|field
argument_list|)
operator|.
name|xy
argument_list|(
literal|5
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|b
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Characters to ignore"
argument_list|)
operator|+
literal|":"
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|b
operator|.
name|add
argument_list|(
name|remove
argument_list|)
operator|.
name|xy
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|b
operator|.
name|add
argument_list|(
name|useCustomDelimiter
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|7
argument_list|)
expr_stmt|;
name|b
operator|.
name|add
argument_list|(
name|deliminator
argument_list|)
operator|.
name|xy
argument_list|(
literal|5
argument_list|,
literal|7
argument_list|)
expr_stmt|;
name|b
operator|.
name|add
argument_list|(
name|authors
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|9
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|b
operator|.
name|add
argument_list|(
name|editors
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|11
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|b
operator|.
name|build
argument_list|()
expr_stmt|;
name|b
operator|.
name|border
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|)
argument_list|)
expr_stmt|;
name|JPanel
name|opt
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|ButtonBarBuilder
name|bb
init|=
operator|new
name|ButtonBarBuilder
argument_list|(
name|opt
argument_list|)
decl_stmt|;
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|ok
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|cancel
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|main
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|)
argument_list|)
expr_stmt|;
name|opt
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|)
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|main
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|b
operator|.
name|getPanel
argument_list|()
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|opt
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
name|updateComponents
argument_list|()
expr_stmt|;
name|pack
argument_list|()
expr_stmt|;
name|setLocationRelativeTo
argument_list|(
name|frame
argument_list|)
expr_stmt|;
block|}
DECL|method|findDeliminatedWordsInField (BibDatabase db, String field, String deliminator)
specifier|public
specifier|static
name|Set
argument_list|<
name|String
argument_list|>
name|findDeliminatedWordsInField
parameter_list|(
name|BibDatabase
name|db
parameter_list|,
name|String
name|field
parameter_list|,
name|String
name|deliminator
parameter_list|)
block|{
name|Set
argument_list|<
name|String
argument_list|>
name|res
init|=
operator|new
name|TreeSet
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|BibEntry
name|be
range|:
name|db
operator|.
name|getEntries
argument_list|()
control|)
block|{
name|be
operator|.
name|getField
argument_list|(
name|field
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|fieldValue
lambda|->
block|{
name|StringTokenizer
name|tok
init|=
operator|new
name|StringTokenizer
argument_list|(
name|fieldValue
operator|.
name|trim
argument_list|()
argument_list|,
name|deliminator
argument_list|)
decl_stmt|;
while|while
condition|(
name|tok
operator|.
name|hasMoreTokens
argument_list|()
condition|)
block|{
name|res
operator|.
name|add
argument_list|(
name|StringUtil
operator|.
name|capitalizeFirst
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
return|return
name|res
return|;
block|}
comment|/**      * Returns a Set containing all words used in the database in the given field type. Characters in      *<code>remove</code> are not included.      *      * @param db a<code>BibDatabase</code> value      * @param field a<code>String</code> value      * @param remove a<code>String</code> value      * @return a<code>Set</code> value      */
DECL|method|findAllWordsInField (BibDatabase db, String field, String remove)
specifier|public
specifier|static
name|Set
argument_list|<
name|String
argument_list|>
name|findAllWordsInField
parameter_list|(
name|BibDatabase
name|db
parameter_list|,
name|String
name|field
parameter_list|,
name|String
name|remove
parameter_list|)
block|{
name|Set
argument_list|<
name|String
argument_list|>
name|res
init|=
operator|new
name|TreeSet
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|BibEntry
name|be
range|:
name|db
operator|.
name|getEntries
argument_list|()
control|)
block|{
name|be
operator|.
name|getField
argument_list|(
name|field
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|o
lambda|->
block|{
name|StringTokenizer
name|tok
init|=
operator|new
name|StringTokenizer
argument_list|(
name|o
argument_list|,
name|remove
argument_list|,
literal|false
argument_list|)
decl_stmt|;
while|while
condition|(
name|tok
operator|.
name|hasMoreTokens
argument_list|()
condition|)
block|{
name|res
operator|.
name|add
argument_list|(
name|StringUtil
operator|.
name|capitalizeFirst
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
return|return
name|res
return|;
block|}
comment|/**      * Finds all authors' last names in all the given fields for the given database.      *      * @param db The database.      * @param fields The fields to look in.      * @return a set containing the names.      */
DECL|method|findAuthorLastNames (BibDatabase db, List<String> fields)
specifier|public
specifier|static
name|Set
argument_list|<
name|String
argument_list|>
name|findAuthorLastNames
parameter_list|(
name|BibDatabase
name|db
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|fields
parameter_list|)
block|{
name|Set
argument_list|<
name|String
argument_list|>
name|res
init|=
operator|new
name|TreeSet
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|BibEntry
name|be
range|:
name|db
operator|.
name|getEntries
argument_list|()
control|)
block|{
for|for
control|(
name|String
name|field
range|:
name|fields
control|)
block|{
name|be
operator|.
name|getField
argument_list|(
name|field
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|val
lambda|->
block|{
if|if
condition|(
operator|!
name|val
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|AuthorList
name|al
init|=
name|AuthorList
operator|.
name|parse
argument_list|(
name|val
argument_list|)
decl_stmt|;
name|res
operator|.
name|addAll
argument_list|(
name|al
operator|.
name|getAuthors
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|Author
operator|::
name|getLast
argument_list|)
operator|.
name|filter
argument_list|(
name|Optional
operator|::
name|isPresent
argument_list|)
operator|.
name|map
argument_list|(
name|Optional
operator|::
name|get
argument_list|)
operator|.
name|filter
argument_list|(
name|lastName
lambda|->
operator|!
name|lastName
operator|.
name|isEmpty
argument_list|()
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|res
return|;
block|}
annotation|@
name|Override
DECL|method|caretUpdate (CaretEvent e)
specifier|public
name|void
name|caretUpdate
parameter_list|(
name|CaretEvent
name|e
parameter_list|)
block|{
name|updateComponents
argument_list|()
expr_stmt|;
block|}
DECL|method|updateComponents ()
specifier|private
name|void
name|updateComponents
parameter_list|()
block|{
name|String
name|groupField
init|=
name|field
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
decl_stmt|;
name|ok
operator|.
name|setEnabled
argument_list|(
name|groupField
operator|.
name|matches
argument_list|(
literal|"\\w+"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
