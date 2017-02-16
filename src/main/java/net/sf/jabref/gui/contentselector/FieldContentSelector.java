begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.contentselector
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|contentselector
package|;
end_package

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
name|GridBagConstraints
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|GridBagLayout
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Window
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
name|Box
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
name|JComboBox
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
name|KeyStroke
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
name|BasePanel
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
name|JabRefFrame
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
name|fieldeditors
operator|.
name|FieldEditor
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
name|Keyword
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
name|KeywordList
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
name|metadata
operator|.
name|MetaData
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
name|Sizes
import|;
end_import

begin_import
import|import
name|com
operator|.
name|jgoodies
operator|.
name|looks
operator|.
name|Options
import|;
end_import

begin_comment
comment|/**  * A combo-box and a manage button that will add selected strings to an  * associated entry editor.  *  * Used to manage keywords and authors for instance.  */
end_comment

begin_class
DECL|class|FieldContentSelector
specifier|public
class|class
name|FieldContentSelector
extends|extends
name|JComponent
block|{
DECL|field|MAX_CONTENT_SELECTOR_WIDTH
specifier|private
specifier|static
specifier|final
name|int
name|MAX_CONTENT_SELECTOR_WIDTH
init|=
literal|240
decl_stmt|;
comment|// The max width of the combobox for content selectors.
DECL|field|comboBox
specifier|private
specifier|final
name|JComboBox
argument_list|<
name|String
argument_list|>
name|comboBox
decl_stmt|;
DECL|field|editor
specifier|private
specifier|final
name|FieldEditor
name|editor
decl_stmt|;
DECL|field|metaData
specifier|private
specifier|final
name|MetaData
name|metaData
decl_stmt|;
DECL|field|action
specifier|private
specifier|final
name|AbstractAction
name|action
decl_stmt|;
DECL|field|delimiter
specifier|private
specifier|final
name|String
name|delimiter
decl_stmt|;
comment|/**      *      * Create a new FieldContentSelector.      *      * @param frame      *            The one JabRef-Frame.      * @param panel      *            The basepanel the entry-editor is on.      * @param owner      *            The window/frame/dialog which should be the owner of the      *            content selector dialog.      * @param editor      *            The entry editor which will be appended by the text selected      *            by the user from the combobox.      * @param action      *            The action that will be performed to after an item from the      *            combobox has been appended to the text in the entryeditor.      * @param horizontalLayout      *            Whether to put a 2 pixel horizontal strut between combobox and      *            button.      */
DECL|method|FieldContentSelector (JabRefFrame frame, final BasePanel panel, Window owner, final FieldEditor editor, final AbstractAction action, boolean horizontalLayout, String delimiter)
specifier|public
name|FieldContentSelector
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
specifier|final
name|BasePanel
name|panel
parameter_list|,
name|Window
name|owner
parameter_list|,
specifier|final
name|FieldEditor
name|editor
parameter_list|,
specifier|final
name|AbstractAction
name|action
parameter_list|,
name|boolean
name|horizontalLayout
parameter_list|,
name|String
name|delimiter
parameter_list|)
block|{
name|this
operator|.
name|editor
operator|=
name|editor
expr_stmt|;
name|this
operator|.
name|metaData
operator|=
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getMetaData
argument_list|()
expr_stmt|;
name|this
operator|.
name|action
operator|=
name|action
expr_stmt|;
name|this
operator|.
name|delimiter
operator|=
name|delimiter
expr_stmt|;
name|comboBox
operator|=
operator|new
name|JComboBox
argument_list|<
name|String
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|Dimension
name|getPreferredSize
parameter_list|()
block|{
name|Dimension
name|parents
init|=
name|super
operator|.
name|getPreferredSize
argument_list|()
decl_stmt|;
if|if
condition|(
name|parents
operator|.
name|width
operator|>
name|MAX_CONTENT_SELECTOR_WIDTH
condition|)
block|{
name|parents
operator|.
name|width
operator|=
name|MAX_CONTENT_SELECTOR_WIDTH
expr_stmt|;
block|}
return|return
name|parents
return|;
block|}
block|}
expr_stmt|;
name|GridBagLayout
name|gbl
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
name|GridBagConstraints
name|con
init|=
operator|new
name|GridBagConstraints
argument_list|()
decl_stmt|;
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
comment|// comboBox.setEditable(true);
name|comboBox
operator|.
name|setMaximumRowCount
argument_list|(
literal|35
argument_list|)
expr_stmt|;
comment|// Set the width of the popup independent of the size of th box itself:
name|comboBox
operator|.
name|putClientProperty
argument_list|(
name|Options
operator|.
name|COMBO_POPUP_PROTOTYPE_DISPLAY_VALUE_KEY
argument_list|,
literal|"The longest text in the combo popup menu. And even longer."
argument_list|)
expr_stmt|;
name|rebuildComboBox
argument_list|()
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
name|horizontalLayout
condition|?
literal|3
else|:
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
name|HORIZONTAL
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
name|comboBox
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|comboBox
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
comment|/*              * These conditions signify arrow key navigation in the dropdown              * list, so we should not react to it. I'm not sure if this is              * well defined enough to be guaranteed to work everywhere.              */
if|if
condition|(
literal|"comboBoxChanged"
operator|.
name|equals
argument_list|(
name|e
operator|.
name|getActionCommand
argument_list|()
argument_list|)
operator|&&
operator|(
name|e
operator|.
name|getModifiers
argument_list|()
operator|==
literal|0
operator|)
condition|)
block|{
return|return;
block|}
name|selectionMade
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
comment|// Add an action for the Enter key that signals a selection:
name|comboBox
operator|.
name|getInputMap
argument_list|()
operator|.
name|put
argument_list|(
name|KeyStroke
operator|.
name|getKeyStroke
argument_list|(
literal|"ENTER"
argument_list|)
argument_list|,
literal|"enter"
argument_list|)
expr_stmt|;
name|comboBox
operator|.
name|getActionMap
argument_list|()
operator|.
name|put
argument_list|(
literal|"enter"
argument_list|,
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
name|actionEvent
parameter_list|)
block|{
name|selectionMade
argument_list|()
expr_stmt|;
name|comboBox
operator|.
name|setPopupVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|comboBox
argument_list|)
expr_stmt|;
if|if
condition|(
name|horizontalLayout
condition|)
block|{
name|add
argument_list|(
name|Box
operator|.
name|createHorizontalStrut
argument_list|(
name|Sizes
operator|.
name|dialogUnitXAsPixel
argument_list|(
literal|2
argument_list|,
name|this
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|JButton
name|manage
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Manage"
argument_list|)
argument_list|)
decl_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|manage
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|manage
argument_list|)
expr_stmt|;
name|manage
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
name|ContentSelectorDialog
name|csd
init|=
operator|new
name|ContentSelectorDialog
argument_list|(
name|owner
argument_list|,
name|frame
argument_list|,
name|panel
argument_list|,
literal|true
argument_list|,
name|editor
operator|.
name|getFieldName
argument_list|()
argument_list|)
decl_stmt|;
name|csd
operator|.
name|setLocationRelativeTo
argument_list|(
name|frame
argument_list|)
expr_stmt|;
comment|// Calling setVisible(true) will open the modal dialog and block
comment|// for the dialog to close.
name|csd
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
comment|// So we need to rebuild the ComboBox afterwards
name|rebuildComboBox
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|selectionMade ()
specifier|private
name|void
name|selectionMade
parameter_list|()
block|{
comment|// The first element is empty to avoid a preselection
if|if
condition|(
name|comboBox
operator|.
name|getSelectedIndex
argument_list|()
operator|==
literal|0
condition|)
block|{
return|return;
block|}
name|String
name|chosen
init|=
operator|(
name|String
operator|)
name|comboBox
operator|.
name|getSelectedItem
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|chosen
operator|==
literal|null
operator|)
operator|||
name|chosen
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return;
block|}
name|String
name|currentText
init|=
name|editor
operator|.
name|getText
argument_list|()
decl_stmt|;
name|KeywordList
name|words
init|=
name|KeywordList
operator|.
name|parse
argument_list|(
name|currentText
argument_list|,
name|this
operator|.
name|delimiter
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
argument_list|)
decl_stmt|;
name|boolean
name|alreadyInList
init|=
name|words
operator|.
name|contains
argument_list|(
operator|new
name|Keyword
argument_list|(
name|chosen
argument_list|)
argument_list|)
decl_stmt|;
comment|// not the first word and no duplicate -> we need a comma
if|if
condition|(
operator|!
literal|""
operator|.
name|equals
argument_list|(
name|currentText
argument_list|)
operator|&&
operator|!
name|alreadyInList
condition|)
block|{
name|editor
operator|.
name|append
argument_list|(
name|FieldContentSelector
operator|.
name|this
operator|.
name|delimiter
argument_list|)
expr_stmt|;
block|}
comment|// no duplicate -> add it
if|if
condition|(
operator|!
name|alreadyInList
condition|)
block|{
name|editor
operator|.
name|append
argument_list|(
name|chosen
argument_list|)
expr_stmt|;
block|}
name|comboBox
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
comment|// Fire event that we changed the editor
if|if
condition|(
name|action
operator|!=
literal|null
condition|)
block|{
name|action
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
block|}
comment|// Transfer focus to the editor.
name|editor
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
block|}
DECL|method|rebuildComboBox ()
specifier|public
name|void
name|rebuildComboBox
parameter_list|()
block|{
name|comboBox
operator|.
name|removeAllItems
argument_list|()
expr_stmt|;
comment|// To have an empty field as the default for the combobox
name|comboBox
operator|.
name|addItem
argument_list|(
literal|""
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|item
range|:
name|metaData
operator|.
name|getContentSelectorValuesForField
argument_list|(
name|editor
operator|.
name|getFieldName
argument_list|()
argument_list|)
control|)
block|{
name|comboBox
operator|.
name|addItem
argument_list|(
name|item
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

