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
name|*
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|border
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
name|util
operator|.
name|Vector
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
name|TreeSet
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

begin_class
DECL|class|ContentSelectorDialog2
specifier|public
class|class
name|ContentSelectorDialog2
extends|extends
name|JDialog
block|{
DECL|field|wordEditFieldListener
name|ActionListener
name|wordEditFieldListener
init|=
literal|null
decl_stmt|;
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
DECL|field|fieldPan
name|JPanel
name|fieldPan
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|,
DECL|field|wordPan
name|wordPan
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|,
DECL|field|buttonPan
name|buttonPan
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|,
DECL|field|fieldNamePan
name|fieldNamePan
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|,
DECL|field|wordEditPan
name|wordEditPan
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
specifier|final
name|String
DECL|field|WORD_EMPTY_TEXT
name|WORD_EMPTY_TEXT
init|=
name|Globals
operator|.
name|lang
argument_list|(
literal|"<no field>"
argument_list|)
decl_stmt|,
DECL|field|WORD_FIRSTLINE_TEXT
name|WORD_FIRSTLINE_TEXT
init|=
name|Globals
operator|.
name|lang
argument_list|(
literal|"<select word>"
argument_list|)
decl_stmt|,
DECL|field|FIELD_FIRST_LINE
name|FIELD_FIRST_LINE
init|=
name|Globals
operator|.
name|lang
argument_list|(
literal|"<field name>"
argument_list|)
decl_stmt|;
DECL|field|metaData
name|MetaData
name|metaData
decl_stmt|;
DECL|field|currentField
name|String
name|currentField
init|=
literal|null
decl_stmt|;
DECL|field|fieldSet
DECL|field|wordSet
name|TreeSet
name|fieldSet
decl_stmt|,
name|wordSet
decl_stmt|;
DECL|field|frame
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|panel
name|BasePanel
name|panel
decl_stmt|;
DECL|field|help
name|JButton
name|help
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Help"
argument_list|)
argument_list|)
decl_stmt|,
DECL|field|newField
name|newField
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"New"
argument_list|)
argument_list|)
decl_stmt|,
DECL|field|removeField
name|removeField
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Remove"
argument_list|)
argument_list|)
decl_stmt|,
DECL|field|newWord
name|newWord
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"New"
argument_list|)
argument_list|)
decl_stmt|,
DECL|field|removeWord
name|removeWord
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Remove"
argument_list|)
argument_list|)
decl_stmt|,
DECL|field|ok
name|ok
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Ok"
argument_list|)
argument_list|)
decl_stmt|,
DECL|field|cancel
name|cancel
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
argument_list|)
decl_stmt|,
DECL|field|apply
name|apply
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Apply"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|fieldListModel
name|DefaultListModel
name|fieldListModel
init|=
operator|new
name|DefaultListModel
argument_list|()
decl_stmt|,
DECL|field|wordListModel
name|wordListModel
init|=
operator|new
name|DefaultListModel
argument_list|()
decl_stmt|;
DECL|field|fieldList
name|JList
name|fieldList
init|=
operator|new
name|JList
argument_list|(
name|fieldListModel
argument_list|)
decl_stmt|,
DECL|field|wordList
name|wordList
init|=
operator|new
name|JList
argument_list|(
name|wordListModel
argument_list|)
decl_stmt|;
DECL|field|fieldNameField
name|JTextField
name|fieldNameField
init|=
operator|new
name|JTextField
argument_list|(
literal|""
argument_list|,
literal|20
argument_list|)
decl_stmt|,
DECL|field|wordEditField
name|wordEditField
init|=
operator|new
name|JTextField
argument_list|(
literal|""
argument_list|,
literal|20
argument_list|)
decl_stmt|;
DECL|field|fPane
name|JScrollPane
name|fPane
init|=
operator|new
name|JScrollPane
argument_list|(
name|fieldList
argument_list|)
decl_stmt|,
DECL|field|wPane
name|wPane
init|=
operator|new
name|JScrollPane
argument_list|(
name|wordList
argument_list|)
decl_stmt|;
DECL|field|wordListModels
name|HashMap
name|wordListModels
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
DECL|field|removedFields
name|ArrayList
name|removedFields
init|=
operator|new
name|ArrayList
argument_list|()
decl_stmt|;
DECL|method|ContentSelectorDialog2 (JabRefFrame frame, BasePanel panel, boolean modal, MetaData metaData, String fieldName)
specifier|public
name|ContentSelectorDialog2
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|BasePanel
name|panel
parameter_list|,
name|boolean
name|modal
parameter_list|,
name|MetaData
name|metaData
parameter_list|,
name|String
name|fieldName
parameter_list|)
block|{
name|super
argument_list|(
name|frame
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Setup selectors"
argument_list|)
argument_list|,
name|modal
argument_list|)
expr_stmt|;
name|this
operator|.
name|metaData
operator|=
name|metaData
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|this
operator|.
name|panel
operator|=
name|panel
expr_stmt|;
name|this
operator|.
name|currentField
operator|=
name|fieldName
expr_stmt|;
comment|//help = new JButton(Globals.lang("Help"));
comment|//help.addActionListener(new HelpAction(frame.helpDiag, GUIGlobals.contentSelectorHelp, "Help"));
comment|//help = new HelpAction(frame.helpDiag, GUIGlobals.contentSelectorHelp, "Help");
name|initLayout
argument_list|()
expr_stmt|;
comment|//	wordSelector.addItem(WORD_EMPTY_TEXT);
name|setupFieldSelector
argument_list|()
expr_stmt|;
name|setupWordSelector
argument_list|()
expr_stmt|;
name|setupActions
argument_list|()
expr_stmt|;
name|int
name|fieldInd
init|=
name|fieldListModel
operator|.
name|indexOf
argument_list|(
name|currentField
argument_list|)
decl_stmt|;
if|if
condition|(
name|fieldInd
operator|>=
literal|0
condition|)
name|fieldList
operator|.
name|setSelectedIndex
argument_list|(
name|fieldInd
argument_list|)
expr_stmt|;
name|pack
argument_list|()
expr_stmt|;
comment|//System.out.println("eee");
block|}
DECL|method|setupActions ()
specifier|private
name|void
name|setupActions
parameter_list|()
block|{
name|wordList
operator|.
name|addListSelectionListener
argument_list|(
operator|new
name|ListSelectionListener
argument_list|()
block|{
specifier|public
name|void
name|valueChanged
parameter_list|(
name|ListSelectionEvent
name|e
parameter_list|)
block|{
name|wordEditField
operator|.
name|setText
argument_list|(
operator|(
name|String
operator|)
name|wordList
operator|.
name|getSelectedValue
argument_list|()
argument_list|)
expr_stmt|;
name|wordEditField
operator|.
name|selectAll
argument_list|()
expr_stmt|;
operator|new
name|FocusRequester
argument_list|(
name|wordEditField
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|newWord
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
name|newWordAction
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|wordEditFieldListener
operator|=
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
name|int
name|index
init|=
name|wordList
operator|.
name|getSelectedIndex
argument_list|()
decl_stmt|;
name|String
name|old
init|=
operator|(
name|String
operator|)
name|wordList
operator|.
name|getSelectedValue
argument_list|()
decl_stmt|,
name|newVal
init|=
name|wordEditField
operator|.
name|getText
argument_list|()
decl_stmt|;
if|if
condition|(
name|newVal
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
operator|||
name|newVal
operator|.
name|equals
argument_list|(
name|old
argument_list|)
condition|)
return|return;
comment|// Empty string or no change.
name|int
name|newIndex
init|=
name|findPos
argument_list|(
name|wordListModel
argument_list|,
name|newVal
argument_list|)
decl_stmt|;
if|if
condition|(
name|index
operator|>=
literal|0
condition|)
block|{
name|wordListModel
operator|.
name|remove
argument_list|(
name|index
argument_list|)
expr_stmt|;
name|wordListModel
operator|.
name|add
argument_list|(
operator|(
name|newIndex
operator|<=
name|index
condition|?
name|newIndex
else|:
name|newIndex
operator|-
literal|1
operator|)
argument_list|,
name|newVal
argument_list|)
expr_stmt|;
block|}
else|else
name|wordListModel
operator|.
name|add
argument_list|(
name|newIndex
argument_list|,
name|newVal
argument_list|)
expr_stmt|;
name|wordEditField
operator|.
name|selectAll
argument_list|()
expr_stmt|;
block|}
block|}
expr_stmt|;
name|wordEditField
operator|.
name|addActionListener
argument_list|(
name|wordEditFieldListener
argument_list|)
expr_stmt|;
name|removeWord
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
name|int
name|index
init|=
name|wordList
operator|.
name|getSelectedIndex
argument_list|()
decl_stmt|;
if|if
condition|(
name|index
operator|==
operator|-
literal|1
condition|)
return|return;
name|wordListModel
operator|.
name|remove
argument_list|(
name|index
argument_list|)
expr_stmt|;
name|wordEditField
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
if|if
condition|(
name|wordListModel
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
name|wordList
operator|.
name|setSelectedIndex
argument_list|(
name|Math
operator|.
name|min
argument_list|(
name|index
argument_list|,
name|wordListModel
operator|.
name|size
argument_list|()
operator|-
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|fieldList
operator|.
name|addListSelectionListener
argument_list|(
operator|new
name|ListSelectionListener
argument_list|()
block|{
specifier|public
name|void
name|valueChanged
parameter_list|(
name|ListSelectionEvent
name|e
parameter_list|)
block|{
name|currentField
operator|=
operator|(
name|String
operator|)
name|fieldList
operator|.
name|getSelectedValue
argument_list|()
expr_stmt|;
name|fieldNameField
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|setupWordSelector
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|newField
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
name|fieldListModel
operator|.
name|add
argument_list|(
literal|0
argument_list|,
name|FIELD_FIRST_LINE
argument_list|)
expr_stmt|;
name|fieldList
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|fPane
operator|.
name|getVerticalScrollBar
argument_list|()
operator|.
name|setValue
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|fieldNameField
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|fieldNameField
operator|.
name|setText
argument_list|(
name|currentField
argument_list|)
expr_stmt|;
name|fieldNameField
operator|.
name|selectAll
argument_list|()
expr_stmt|;
operator|new
name|FocusRequester
argument_list|(
name|fieldNameField
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|fieldNameField
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
name|fieldNameField
operator|.
name|transferFocus
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|fieldNameField
operator|.
name|addFocusListener
argument_list|(
operator|new
name|FocusAdapter
argument_list|()
block|{
specifier|public
name|void
name|focusLost
parameter_list|(
name|FocusEvent
name|e
parameter_list|)
block|{
name|String
name|s
init|=
name|fieldNameField
operator|.
name|getText
argument_list|()
decl_stmt|;
name|fieldNameField
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|fieldNameField
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|FIELD_FIRST_LINE
operator|.
name|equals
argument_list|(
name|s
argument_list|)
operator|&&
operator|!
literal|""
operator|.
name|equals
argument_list|(
name|s
argument_list|)
condition|)
block|{
comment|// Add new field.
name|int
name|pos
init|=
name|findPos
argument_list|(
name|fieldListModel
argument_list|,
name|s
argument_list|)
decl_stmt|;
name|fieldListModel
operator|.
name|remove
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|fieldListModel
operator|.
name|add
argument_list|(
name|Math
operator|.
name|max
argument_list|(
literal|0
argument_list|,
name|pos
operator|-
literal|1
argument_list|)
argument_list|,
name|s
argument_list|)
expr_stmt|;
name|fieldList
operator|.
name|setSelectedIndex
argument_list|(
name|pos
argument_list|)
expr_stmt|;
name|currentField
operator|=
name|s
expr_stmt|;
name|setupWordSelector
argument_list|()
expr_stmt|;
name|newWordAction
argument_list|()
expr_stmt|;
comment|//new FocusRequester(wordEditField);
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|removeField
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
name|int
name|index
init|=
name|fieldList
operator|.
name|getSelectedIndex
argument_list|()
decl_stmt|;
if|if
condition|(
name|index
operator|==
operator|-
literal|1
condition|)
return|return;
name|String
name|fieldName
init|=
operator|(
name|String
operator|)
name|fieldListModel
operator|.
name|get
argument_list|(
name|index
argument_list|)
decl_stmt|;
name|removedFields
operator|.
name|add
argument_list|(
name|fieldName
argument_list|)
expr_stmt|;
name|fieldListModel
operator|.
name|remove
argument_list|(
name|index
argument_list|)
expr_stmt|;
name|wordListModels
operator|.
name|remove
argument_list|(
name|fieldName
argument_list|)
expr_stmt|;
name|fieldNameField
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
if|if
condition|(
name|fieldListModel
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
name|fieldList
operator|.
name|setSelectedIndex
argument_list|(
name|Math
operator|.
name|min
argument_list|(
name|index
argument_list|,
name|wordListModel
operator|.
name|size
argument_list|()
operator|-
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|help
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
name|frame
operator|.
name|helpDiag
operator|.
name|showPage
argument_list|(
name|GUIGlobals
operator|.
name|contentSelectorHelp
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|ok
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
name|applyChanges
argument_list|()
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|apply
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
name|applyChanges
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|cancel
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
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|newWordAction ()
specifier|private
name|void
name|newWordAction
parameter_list|()
block|{
if|if
condition|(
operator|(
name|wordListModel
operator|.
name|size
argument_list|()
operator|==
literal|0
operator|)
operator|||
operator|!
name|wordListModel
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|equals
argument_list|(
name|WORD_FIRSTLINE_TEXT
argument_list|)
condition|)
name|wordListModel
operator|.
name|add
argument_list|(
literal|0
argument_list|,
name|WORD_FIRSTLINE_TEXT
argument_list|)
expr_stmt|;
name|wordList
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|wPane
operator|.
name|getVerticalScrollBar
argument_list|()
operator|.
name|setValue
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
DECL|method|applyChanges ()
specifier|private
name|void
name|applyChanges
parameter_list|()
block|{
name|boolean
name|changedFieldSet
init|=
literal|false
decl_stmt|;
comment|// Watch if we need to rebuild entry editors
comment|// Store if an entry is currently being edited:
if|if
condition|(
operator|!
name|wordEditField
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
name|wordEditFieldListener
operator|.
name|actionPerformed
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
comment|// First remove the mappings for fields that have been deleted.
comment|// If these were re-added, they will be added below, so it doesn't
comment|// cause any harm to remove them here.
for|for
control|(
name|Iterator
name|i
init|=
name|removedFields
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
name|String
name|fieldName
init|=
operator|(
name|String
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|metaData
operator|.
name|remove
argument_list|(
name|Globals
operator|.
name|SELECTOR_META_PREFIX
operator|+
name|fieldName
argument_list|)
expr_stmt|;
name|changedFieldSet
operator|=
literal|true
expr_stmt|;
block|}
comment|// Cycle through all fields that we have created listmodels for:
name|loop
label|:
for|for
control|(
name|Iterator
name|i
init|=
name|wordListModels
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
comment|// For each field name, store the values:
name|String
name|fieldName
init|=
operator|(
name|String
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|fieldName
operator|==
literal|null
operator|)
operator|||
name|FIELD_FIRST_LINE
operator|.
name|equals
argument_list|(
name|fieldName
argument_list|)
condition|)
continue|continue
name|loop
continue|;
name|DefaultListModel
name|lm
init|=
operator|(
name|DefaultListModel
operator|)
name|wordListModels
operator|.
name|get
argument_list|(
name|fieldName
argument_list|)
decl_stmt|;
name|int
name|start
init|=
literal|0
decl_stmt|;
comment|// Avoid storing the<new word> marker if it is there:
if|if
condition|(
name|lm
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
while|while
condition|(
operator|(
name|start
operator|<
name|lm
operator|.
name|size
argument_list|()
operator|)
operator|&&
operator|(
operator|(
name|String
operator|)
name|lm
operator|.
name|get
argument_list|(
name|start
argument_list|)
operator|)
operator|.
name|equals
argument_list|(
name|WORD_FIRSTLINE_TEXT
argument_list|)
condition|)
name|start
operator|++
expr_stmt|;
name|Vector
name|data
init|=
name|metaData
operator|.
name|getData
argument_list|(
name|Globals
operator|.
name|SELECTOR_META_PREFIX
operator|+
name|fieldName
argument_list|)
decl_stmt|;
name|boolean
name|newField
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|data
operator|==
literal|null
condition|)
block|{
name|newField
operator|=
literal|true
expr_stmt|;
name|data
operator|=
operator|new
name|Vector
argument_list|()
expr_stmt|;
name|changedFieldSet
operator|=
literal|true
expr_stmt|;
block|}
else|else
name|data
operator|.
name|clear
argument_list|()
expr_stmt|;
for|for
control|(
name|int
name|wrd
init|=
name|start
init|;
name|wrd
operator|<
name|lm
operator|.
name|size
argument_list|()
condition|;
name|wrd
operator|++
control|)
block|{
name|String
name|word
init|=
operator|(
name|String
operator|)
name|lm
operator|.
name|get
argument_list|(
name|wrd
argument_list|)
decl_stmt|;
name|data
operator|.
name|add
argument_list|(
name|word
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|newField
condition|)
name|metaData
operator|.
name|putData
argument_list|(
name|Globals
operator|.
name|SELECTOR_META_PREFIX
operator|+
name|fieldName
argument_list|,
name|data
argument_list|)
expr_stmt|;
block|}
comment|// System.out.println("TODO: remove metadata for removed selector field.");
name|panel
operator|.
name|markNonUndoableBaseChanged
argument_list|()
expr_stmt|;
comment|// Update all selectors in the current BasePanel.
if|if
condition|(
operator|!
name|changedFieldSet
condition|)
name|panel
operator|.
name|updateAllContentSelectors
argument_list|()
expr_stmt|;
else|else
block|{
name|panel
operator|.
name|rebuildAllEntryEditors
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      * Set the contents of the field selector list.      *      */
DECL|method|setupFieldSelector ()
specifier|private
name|void
name|setupFieldSelector
parameter_list|()
block|{
name|fieldListModel
operator|.
name|clear
argument_list|()
expr_stmt|;
comment|//fieldListModel.addElement(FIELD_FIRST_LINE);
for|for
control|(
name|Iterator
name|i
init|=
name|metaData
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
name|String
name|s
init|=
operator|(
name|String
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|s
operator|.
name|startsWith
argument_list|(
name|Globals
operator|.
name|SELECTOR_META_PREFIX
argument_list|)
condition|)
name|fieldListModel
operator|.
name|addElement
argument_list|(
name|s
operator|.
name|substring
argument_list|(
name|Globals
operator|.
name|SELECTOR_META_PREFIX
operator|.
name|length
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|setupWordSelector ()
specifier|private
name|void
name|setupWordSelector
parameter_list|()
block|{
comment|// Have we already created a listmodel for this field?
name|Object
name|o
init|=
name|wordListModels
operator|.
name|get
argument_list|(
name|currentField
argument_list|)
decl_stmt|;
if|if
condition|(
name|o
operator|!=
literal|null
condition|)
block|{
name|wordListModel
operator|=
operator|(
name|DefaultListModel
operator|)
name|o
expr_stmt|;
name|wordList
operator|.
name|setModel
argument_list|(
name|wordListModel
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|wordListModel
operator|=
operator|new
name|DefaultListModel
argument_list|()
expr_stmt|;
name|wordList
operator|.
name|setModel
argument_list|(
name|wordListModel
argument_list|)
expr_stmt|;
name|wordListModels
operator|.
name|put
argument_list|(
name|currentField
argument_list|,
name|wordListModel
argument_list|)
expr_stmt|;
name|wordListModel
operator|.
name|clear
argument_list|()
expr_stmt|;
comment|//wordListModel.addElement(WORD_FIRSTLINE_TEXT);
name|Vector
name|items
init|=
name|metaData
operator|.
name|getData
argument_list|(
name|Globals
operator|.
name|SELECTOR_META_PREFIX
operator|+
name|currentField
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|items
operator|!=
literal|null
operator|)
condition|)
block|{
comment|//&& (items.size()> 0)) {
name|wordSet
operator|=
operator|new
name|TreeSet
argument_list|(
name|items
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|wordSet
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
name|String
name|s
init|=
operator|(
name|String
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|int
name|index
init|=
name|findPos
argument_list|(
name|wordListModel
argument_list|,
name|s
argument_list|)
decl_stmt|;
name|wordListModel
operator|.
name|add
argument_list|(
name|index
argument_list|,
name|s
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
DECL|method|findPos (DefaultListModel lm, String item)
specifier|private
name|int
name|findPos
parameter_list|(
name|DefaultListModel
name|lm
parameter_list|,
name|String
name|item
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
name|lm
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|String
name|s
init|=
operator|(
name|String
operator|)
name|lm
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|item
operator|.
name|compareToIgnoreCase
argument_list|(
name|s
argument_list|)
operator|<
literal|0
condition|)
block|{
comment|// item precedes s
return|return
name|i
return|;
block|}
block|}
return|return
name|wordListModel
operator|.
name|size
argument_list|()
return|;
block|}
DECL|method|initLayout ()
specifier|private
name|void
name|initLayout
parameter_list|()
block|{
name|fieldNameField
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|fieldList
operator|.
name|setVisibleRowCount
argument_list|(
literal|4
argument_list|)
expr_stmt|;
name|wordList
operator|.
name|setVisibleRowCount
argument_list|(
literal|10
argument_list|)
expr_stmt|;
specifier|final
name|String
name|VAL
init|=
literal|"Uren luren himmelturen, ja Besseggen."
decl_stmt|;
name|fieldList
operator|.
name|setPrototypeCellValue
argument_list|(
name|VAL
argument_list|)
expr_stmt|;
name|wordList
operator|.
name|setPrototypeCellValue
argument_list|(
name|VAL
argument_list|)
expr_stmt|;
name|fieldPan
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
literal|"Field name"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|wordPan
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
literal|"Keyword"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|fieldPan
operator|.
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|wordPan
operator|.
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|2
argument_list|,
literal|2
argument_list|,
literal|2
argument_list|,
literal|2
argument_list|)
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
name|gridwidth
operator|=
literal|2
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|weighty
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|gridx
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
literal|0
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|fPane
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|fieldPan
operator|.
name|add
argument_list|(
name|fPane
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|wPane
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|wordPan
operator|.
name|add
argument_list|(
name|wPane
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
name|gridx
operator|=
literal|2
expr_stmt|;
comment|//con.weightx = 0.7;
name|con
operator|.
name|gridheight
operator|=
literal|2
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|fieldNamePan
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|fieldPan
operator|.
name|add
argument_list|(
name|fieldNamePan
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|wordEditPan
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|wordPan
operator|.
name|add
argument_list|(
name|wordEditPan
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridx
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|gridy
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
name|weighty
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|gridheight
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|NONE
expr_stmt|;
name|con
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|WEST
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|newField
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|fieldPan
operator|.
name|add
argument_list|(
name|newField
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|newWord
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|wordPan
operator|.
name|add
argument_list|(
name|newWord
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridx
operator|=
literal|1
expr_stmt|;
comment|//con.anchor = GridBagConstraints.EAST;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|removeField
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|fieldPan
operator|.
name|add
argument_list|(
name|removeField
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|removeWord
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|wordPan
operator|.
name|add
argument_list|(
name|removeWord
argument_list|)
expr_stmt|;
name|con
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|WEST
expr_stmt|;
name|con
operator|.
name|gridx
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
literal|0
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|fieldNameField
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|fieldNamePan
operator|.
name|add
argument_list|(
name|fieldNameField
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|wordEditField
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|wordEditPan
operator|.
name|add
argument_list|(
name|wordEditField
argument_list|)
expr_stmt|;
comment|// Add buttons:
name|buttonPan
operator|.
name|add
argument_list|(
name|help
argument_list|)
expr_stmt|;
name|buttonPan
operator|.
name|add
argument_list|(
name|Box
operator|.
name|createHorizontalStrut
argument_list|(
literal|10
argument_list|)
argument_list|)
expr_stmt|;
name|buttonPan
operator|.
name|add
argument_list|(
name|ok
argument_list|)
expr_stmt|;
name|buttonPan
operator|.
name|add
argument_list|(
name|apply
argument_list|)
expr_stmt|;
name|buttonPan
operator|.
name|add
argument_list|(
name|cancel
argument_list|)
expr_stmt|;
comment|// Add panels to dialog:
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|BOTH
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|setLayout
argument_list|(
name|gbl
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
name|weighty
operator|=
literal|0.5
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|gridheight
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|gridx
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
literal|0
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|fieldPan
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|fieldPan
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|wordPan
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|wordPan
argument_list|)
expr_stmt|;
name|con
operator|.
name|weighty
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
literal|2
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|12
argument_list|,
literal|2
argument_list|,
literal|2
argument_list|,
literal|2
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|buttonPan
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|buttonPan
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

