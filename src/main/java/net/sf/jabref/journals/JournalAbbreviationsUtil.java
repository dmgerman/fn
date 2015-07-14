begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.journals
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|journals
package|;
end_package

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|EntryEditor
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
name|FieldEditor
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
name|journals
operator|.
name|logic
operator|.
name|Abbreviation
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
name|journals
operator|.
name|logic
operator|.
name|JournalAbbreviationRepository
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
name|undo
operator|.
name|UndoableFieldChange
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
name|JComponent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|table
operator|.
name|DefaultTableModel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|table
operator|.
name|TableModel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|undo
operator|.
name|UndoManager
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

begin_class
DECL|class|JournalAbbreviationsUtil
specifier|public
class|class
name|JournalAbbreviationsUtil
block|{
DECL|field|TOOLTIP_TEXT
specifier|private
specifier|static
specifier|final
name|String
name|TOOLTIP_TEXT
init|=
literal|"<HTML>"
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"Switches between full and abbreviated journal name "
operator|+
literal|"if the journal name is known."
argument_list|)
operator|+
literal|"<BR>"
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"To set up, go to<B>Tools -> Manage journal abbreviations</B>"
argument_list|)
operator|+
literal|".</HTML>"
decl_stmt|;
comment|/**      * Create a control panel for the entry editor's journal field, to toggle      * abbreviated/full journal name      * @param editor The FieldEditor for the journal field.      * @return The control panel for the entry editor.      */
DECL|method|getNameSwitcher (final EntryEditor entryEditor, final FieldEditor editor, final UndoManager undoManager)
specifier|public
specifier|static
name|JComponent
name|getNameSwitcher
parameter_list|(
specifier|final
name|EntryEditor
name|entryEditor
parameter_list|,
specifier|final
name|FieldEditor
name|editor
parameter_list|,
specifier|final
name|UndoManager
name|undoManager
parameter_list|)
block|{
name|JButton
name|button
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Toggle abbreviation"
argument_list|)
argument_list|)
decl_stmt|;
name|button
operator|.
name|setToolTipText
argument_list|(
name|TOOLTIP_TEXT
argument_list|)
expr_stmt|;
name|button
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|actionEvent
parameter_list|)
block|{
name|String
name|text
init|=
name|editor
operator|.
name|getText
argument_list|()
decl_stmt|;
if|if
condition|(
name|Globals
operator|.
name|journalAbbrev
operator|.
name|isKnownName
argument_list|(
name|text
argument_list|)
condition|)
block|{
name|String
name|s
init|=
name|toggleAbbreviation
argument_list|(
name|text
argument_list|)
decl_stmt|;
if|if
condition|(
name|s
operator|!=
literal|null
condition|)
block|{
name|editor
operator|.
name|setText
argument_list|(
name|s
argument_list|)
expr_stmt|;
name|entryEditor
operator|.
name|storeFieldAction
operator|.
name|actionPerformed
argument_list|(
operator|new
name|ActionEvent
argument_list|(
name|editor
argument_list|,
literal|0
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|undoManager
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableFieldChange
argument_list|(
name|entryEditor
operator|.
name|getEntry
argument_list|()
argument_list|,
name|editor
operator|.
name|getFieldName
argument_list|()
argument_list|,
name|text
argument_list|,
name|s
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|public
name|String
name|toggleAbbreviation
parameter_list|(
name|String
name|currentText
parameter_list|)
block|{
return|return
name|Globals
operator|.
name|journalAbbrev
operator|.
name|getNextAbbreviation
argument_list|(
name|currentText
argument_list|)
operator|.
name|or
argument_list|(
name|currentText
argument_list|)
return|;
block|}
block|}
argument_list|)
expr_stmt|;
return|return
name|button
return|;
block|}
DECL|method|getTableModel (JournalAbbreviationRepository journalAbbreviationRepository)
specifier|public
specifier|static
name|TableModel
name|getTableModel
parameter_list|(
name|JournalAbbreviationRepository
name|journalAbbreviationRepository
parameter_list|)
block|{
name|Object
index|[]
index|[]
name|cells
init|=
operator|new
name|Object
index|[
name|journalAbbreviationRepository
operator|.
name|size
argument_list|()
index|]
index|[
literal|2
index|]
decl_stmt|;
name|int
name|row
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Abbreviation
name|abbreviation
range|:
name|journalAbbreviationRepository
operator|.
name|getAbbreviations
argument_list|()
control|)
block|{
name|cells
index|[
name|row
index|]
index|[
literal|0
index|]
operator|=
name|abbreviation
operator|.
name|getName
argument_list|()
expr_stmt|;
name|cells
index|[
name|row
index|]
index|[
literal|1
index|]
operator|=
name|abbreviation
operator|.
name|getIsoAbbreviation
argument_list|()
expr_stmt|;
name|row
operator|++
expr_stmt|;
block|}
return|return
operator|new
name|DefaultTableModel
argument_list|(
name|cells
argument_list|,
operator|new
name|Object
index|[]
block|{
name|Globals
operator|.
name|lang
argument_list|(
literal|"Full name"
argument_list|)
block|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Abbreviation"
argument_list|)
block|}
argument_list|)
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|isCellEditable
parameter_list|(
name|int
name|row
parameter_list|,
name|int
name|column
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
block|}
return|;
block|}
block|}
end_class

end_unit

