begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.entryeditor
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|entryeditor
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|AWTKeyStroke
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Component
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|KeyboardFocusManager
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
name|FocusListener
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
name|Collections
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
name|HashSet
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
name|Map
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
name|InputMap
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
name|JPanel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JScrollPane
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
name|javax
operator|.
name|swing
operator|.
name|ScrollPaneConstants
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|embed
operator|.
name|swing
operator|.
name|JFXPanel
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|Scene
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
name|FXDialogService
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
name|GUIGlobals
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
name|autocompleter
operator|.
name|AutoCompleteListener
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
name|gui
operator|.
name|fieldeditors
operator|.
name|FieldEditorFX
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
name|fieldeditors
operator|.
name|FieldEditors
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
name|fieldeditors
operator|.
name|FieldNameLabel
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
name|fieldeditors
operator|.
name|TextField
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
name|util
operator|.
name|DefaultTaskExecutor
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
name|entry
operator|.
name|FieldProperty
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
name|InternalBibtexFields
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
name|DefaultFormBuilder
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
comment|/**  * A single tab displayed in the EntryEditor holding several FieldEditors.  */
end_comment

begin_class
DECL|class|EntryEditorTab
class|class
name|EntryEditorTab
block|{
DECL|field|panel
specifier|private
specifier|final
name|JPanel
name|panel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|field|scrollPane
specifier|private
specifier|final
name|JScrollPane
name|scrollPane
init|=
operator|new
name|JScrollPane
argument_list|(
name|panel
argument_list|,
name|ScrollPaneConstants
operator|.
name|VERTICAL_SCROLLBAR_AS_NEEDED
argument_list|,
name|ScrollPaneConstants
operator|.
name|HORIZONTAL_SCROLLBAR_NEVER
argument_list|)
decl_stmt|;
DECL|field|fields
specifier|private
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|fields
decl_stmt|;
DECL|field|parent
specifier|private
specifier|final
name|EntryEditor
name|parent
decl_stmt|;
DECL|field|editors
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|FieldEditorFX
argument_list|>
name|editors
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|fieldListener
specifier|private
specifier|final
name|FocusListener
name|fieldListener
init|=
operator|new
name|EntryEditorTabFocusListener
argument_list|(
name|this
argument_list|)
decl_stmt|;
DECL|field|tabTitle
specifier|private
specifier|final
name|String
name|tabTitle
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|basePanel
specifier|private
specifier|final
name|BasePanel
name|basePanel
decl_stmt|;
DECL|field|activeField
specifier|private
name|FieldEditorFX
name|activeField
decl_stmt|;
DECL|field|entry
specifier|private
name|BibEntry
name|entry
decl_stmt|;
DECL|field|updating
specifier|private
name|boolean
name|updating
decl_stmt|;
DECL|method|EntryEditorTab (JabRefFrame frame, BasePanel panel, List<String> fields, EntryEditor parent, boolean addKeyField, boolean compressed, String tabTitle)
specifier|public
name|EntryEditorTab
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|BasePanel
name|panel
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|fields
parameter_list|,
name|EntryEditor
name|parent
parameter_list|,
name|boolean
name|addKeyField
parameter_list|,
name|boolean
name|compressed
parameter_list|,
name|String
name|tabTitle
parameter_list|)
block|{
if|if
condition|(
name|fields
operator|==
literal|null
condition|)
block|{
name|this
operator|.
name|fields
operator|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|fields
operator|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|fields
argument_list|)
expr_stmt|;
block|}
name|this
operator|.
name|parent
operator|=
name|parent
expr_stmt|;
name|this
operator|.
name|tabTitle
operator|=
name|tabTitle
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|this
operator|.
name|basePanel
operator|=
name|panel
expr_stmt|;
name|setupPanel
argument_list|(
name|frame
argument_list|,
name|panel
argument_list|,
name|addKeyField
argument_list|,
name|compressed
argument_list|,
name|tabTitle
argument_list|)
expr_stmt|;
comment|// The following line makes sure focus cycles inside tab instead of being lost to other parts of the frame:
name|scrollPane
operator|.
name|setFocusCycleRoot
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
DECL|method|setupPanel (JabRefFrame frame, BasePanel bPanel, boolean addKeyField, boolean compressed, String title)
specifier|private
name|void
name|setupPanel
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|BasePanel
name|bPanel
parameter_list|,
name|boolean
name|addKeyField
parameter_list|,
name|boolean
name|compressed
parameter_list|,
name|String
name|title
parameter_list|)
block|{
name|setupKeyBindings
argument_list|(
name|panel
operator|.
name|getInputMap
argument_list|(
name|JComponent
operator|.
name|WHEN_FOCUSED
argument_list|)
argument_list|,
name|panel
operator|.
name|getActionMap
argument_list|()
argument_list|)
expr_stmt|;
name|panel
operator|.
name|setName
argument_list|(
name|title
argument_list|)
expr_stmt|;
comment|// Use the title for the scrollPane, too.
comment|// This enables the correct execution of EntryEditor.setVisiblePanel(String name).
name|scrollPane
operator|.
name|setName
argument_list|(
name|title
argument_list|)
expr_stmt|;
name|int
name|fieldsPerRow
init|=
name|compressed
condition|?
literal|2
else|:
literal|1
decl_stmt|;
name|String
name|colSpec
init|=
name|compressed
condition|?
literal|"fill:pref, 1dlu, fill:10dlu:grow, 1dlu, fill:pref, "
operator|+
literal|"8dlu, fill:pref, 1dlu, fill:10dlu:grow, 1dlu, fill:pref"
else|:
literal|"fill:pref, 1dlu, fill:pref:grow, 1dlu, fill:pref"
decl_stmt|;
name|StringBuilder
name|stringBuilder
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|int
name|rows
init|=
operator|(
name|int
operator|)
name|Math
operator|.
name|ceil
argument_list|(
operator|(
name|double
operator|)
name|fields
operator|.
name|size
argument_list|()
operator|/
name|fieldsPerRow
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
name|rows
condition|;
name|i
operator|++
control|)
block|{
name|stringBuilder
operator|.
name|append
argument_list|(
literal|"fill:pref:grow, "
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|addKeyField
condition|)
block|{
name|stringBuilder
operator|.
name|append
argument_list|(
literal|"4dlu, fill:pref"
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|stringBuilder
operator|.
name|length
argument_list|()
operator|>=
literal|2
condition|)
block|{
name|stringBuilder
operator|.
name|delete
argument_list|(
name|stringBuilder
operator|.
name|length
argument_list|()
operator|-
literal|2
argument_list|,
name|stringBuilder
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|String
name|rowSpec
init|=
name|stringBuilder
operator|.
name|toString
argument_list|()
decl_stmt|;
name|DefaultFormBuilder
name|builder
init|=
operator|new
name|DefaultFormBuilder
argument_list|(
operator|new
name|FormLayout
argument_list|(
name|colSpec
argument_list|,
name|rowSpec
argument_list|)
argument_list|,
name|panel
argument_list|)
decl_stmt|;
comment|// BibTex edit fields are defined here
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|fields
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|String
name|fieldName
init|=
name|fields
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
comment|// TODO: Reenable/migrate this
comment|// Store the editor for later reference:
comment|/*             FieldEditor fieldEditor;             int defaultHeight;             int wHeight = (int) (50.0 * InternalBibtexFields.getFieldWeight(field));             if (InternalBibtexFields.getFieldProperties(field).contains(FieldProperty.SINGLE_ENTRY_LINK)) {                 fieldEditor = new EntryLinkListEditor(frame, bPanel.getBibDatabaseContext(), field, null, parent,                         true);                 defaultHeight = 0;             } else if (InternalBibtexFields.getFieldProperties(field).contains(FieldProperty.MULTIPLE_ENTRY_LINK)) {                 fieldEditor = new EntryLinkListEditor(frame, bPanel.getBibDatabaseContext(), field, null, parent,                         false);                 defaultHeight = 0;             } else {                 fieldEditor = new TextArea(field, null, getPrompt(field));                 //parent.addSearchListener((TextArea) fieldEditor);                 defaultHeight = fieldEditor.getPane().getPreferredSize().height;             }              Optional<JComponent> extra = parent.getExtra(fieldEditor);              // Add autocompleter listener, if required for this field:             /*             AutoCompleter<String> autoCompleter = bPanel.getAutoCompleters().get(field);             AutoCompleteListener autoCompleteListener = null;             if (autoCompleter != null) {                 autoCompleteListener = new AutoCompleteListener(autoCompleter);             }             setupJTextComponent(fieldEditor.getTextComponent(), autoCompleteListener);             fieldEditor.setAutoCompleteListener(autoCompleteListener);             */
name|FieldEditorFX
name|fieldEditor
init|=
name|FieldEditors
operator|.
name|getForField
argument_list|(
name|fieldName
argument_list|,
name|Globals
operator|.
name|taskExecutor
argument_list|,
operator|new
name|FXDialogService
argument_list|()
argument_list|,
name|Globals
operator|.
name|journalAbbreviationLoader
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getJournalAbbreviationPreferences
argument_list|()
argument_list|,
name|Globals
operator|.
name|prefs
argument_list|,
name|bPanel
operator|.
name|getBibDatabaseContext
argument_list|()
argument_list|,
name|entry
operator|.
name|getType
argument_list|()
argument_list|)
decl_stmt|;
name|editors
operator|.
name|put
argument_list|(
name|fieldName
argument_list|,
name|fieldEditor
argument_list|)
expr_stmt|;
comment|/*             // TODO: Reenable this             if (i == 0) {                 activeField = fieldEditor;             }             */
comment|/*             // TODO: Reenable this             if (!compressed) {                 fieldEditor.getPane().setPreferredSize(new Dimension(100, Math.max(defaultHeight, wHeight)));             }             */
comment|/*             // TODO: Reenable content selector             if (!panel.getBibDatabaseContext().getMetaData().getContentSelectorValuesForField(editor.getFieldName()).isEmpty()) {                 FieldContentSelector ws = new FieldContentSelector(frame, panel, frame, editor, storeFieldAction, false,                         ", ");                 contentSelectors.add(ws);                 controls.add(ws, BorderLayout.NORTH);             }             //} else if (!panel.getBibDatabaseContext().getMetaData().getContentSelectorValuesForField(fieldName).isEmpty()) {             //return FieldExtraComponents.getSelectorExtraComponent(frame, panel, editor, contentSelectors, storeFieldAction);              */
name|builder
operator|.
name|append
argument_list|(
operator|new
name|FieldNameLabel
argument_list|(
name|fieldName
argument_list|)
argument_list|)
expr_stmt|;
name|JFXPanel
name|swingPanel
init|=
operator|new
name|JFXPanel
argument_list|()
decl_stmt|;
name|swingPanel
operator|.
name|setBackground
argument_list|(
name|GUIGlobals
operator|.
name|activeBackgroundColor
argument_list|)
expr_stmt|;
name|DefaultTaskExecutor
operator|.
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
block|{
name|Scene
name|scene
init|=
operator|new
name|Scene
argument_list|(
name|fieldEditor
operator|.
name|getNode
argument_list|()
argument_list|)
decl_stmt|;
name|swingPanel
operator|.
name|setScene
argument_list|(
name|scene
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|swingPanel
argument_list|,
literal|3
argument_list|)
expr_stmt|;
comment|/*             // TODO: Delete when no longer required             if (extra.isPresent()) {                 builder.append(fieldEditor.getPane());                 JPanel pan = new JPanel();                 pan.setLayout(new BorderLayout());                 pan.add(extra.get(), BorderLayout.NORTH);                 builder.append(pan);             } else {                 builder.append(fieldEditor.getPane(), 3);             }             */
if|if
condition|(
operator|(
operator|(
name|i
operator|+
literal|1
operator|)
operator|%
name|fieldsPerRow
operator|)
operator|==
literal|0
condition|)
block|{
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
block|}
block|}
comment|// Add the edit field for Bibtex-key.
if|if
condition|(
name|addKeyField
condition|)
block|{
specifier|final
name|TextField
name|textField
init|=
operator|new
name|TextField
argument_list|(
name|BibEntry
operator|.
name|KEY_FIELD
argument_list|,
name|parent
operator|.
name|getEntry
argument_list|()
operator|.
name|getCiteKeyOptional
argument_list|()
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|setupJTextComponent
argument_list|(
name|textField
argument_list|,
literal|null
argument_list|)
expr_stmt|;
comment|// TODO: Reenable this
comment|//editors.put(BibEntry.KEY_FIELD, textField);
name|fields
operator|.
name|add
argument_list|(
name|BibEntry
operator|.
name|KEY_FIELD
argument_list|)
expr_stmt|;
comment|/*              * If the key field is the only field, we should have only one              * editor, and this one should be set as active initially:              */
if|if
condition|(
name|editors
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
comment|// TODO: Reenable this
comment|//activeField = textField;
block|}
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|textField
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|textField
argument_list|,
literal|3
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|getPrompt (String field)
specifier|private
name|String
name|getPrompt
parameter_list|(
name|String
name|field
parameter_list|)
block|{
name|Set
argument_list|<
name|FieldProperty
argument_list|>
name|fieldProperties
init|=
name|InternalBibtexFields
operator|.
name|getFieldProperties
argument_list|(
name|field
argument_list|)
decl_stmt|;
if|if
condition|(
name|fieldProperties
operator|.
name|contains
argument_list|(
name|FieldProperty
operator|.
name|PERSON_NAMES
argument_list|)
condition|)
block|{
return|return
name|String
operator|.
name|format
argument_list|(
literal|"%1$s and %1$s and others"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Firstname Lastname"
argument_list|)
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|fieldProperties
operator|.
name|contains
argument_list|(
name|FieldProperty
operator|.
name|DOI
argument_list|)
condition|)
block|{
return|return
literal|"10.ORGANISATION/ID"
return|;
block|}
elseif|else
if|if
condition|(
name|fieldProperties
operator|.
name|contains
argument_list|(
name|FieldProperty
operator|.
name|DATE
argument_list|)
condition|)
block|{
return|return
literal|"YYYY-MM-DD"
return|;
block|}
switch|switch
condition|(
name|field
condition|)
block|{
case|case
name|FieldName
operator|.
name|YEAR
case|:
return|return
literal|"YYYY"
return|;
case|case
name|FieldName
operator|.
name|MONTH
case|:
return|return
literal|"MM or #mmm#"
return|;
case|case
name|FieldName
operator|.
name|URL
case|:
return|return
literal|"https://"
return|;
block|}
return|return
literal|""
return|;
block|}
DECL|method|getEntry ()
specifier|private
name|BibEntry
name|getEntry
parameter_list|()
block|{
return|return
name|entry
return|;
block|}
DECL|method|setEntry (BibEntry entry)
specifier|public
name|void
name|setEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
try|try
block|{
name|updating
operator|=
literal|true
expr_stmt|;
for|for
control|(
name|FieldEditorFX
name|editor
range|:
name|editors
operator|.
name|values
argument_list|()
control|)
block|{
name|DefaultTaskExecutor
operator|.
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
name|editor
operator|.
name|bindToEntry
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|this
operator|.
name|entry
operator|=
name|entry
expr_stmt|;
block|}
finally|finally
block|{
name|updating
operator|=
literal|false
expr_stmt|;
block|}
block|}
DECL|method|isFieldModified (FieldEditor fieldEditor)
specifier|private
name|boolean
name|isFieldModified
parameter_list|(
name|FieldEditor
name|fieldEditor
parameter_list|)
block|{
name|String
name|text
init|=
name|fieldEditor
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
decl_stmt|;
if|if
condition|(
name|text
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
name|getEntry
argument_list|()
operator|.
name|hasField
argument_list|(
name|fieldEditor
operator|.
name|getFieldName
argument_list|()
argument_list|)
return|;
block|}
else|else
block|{
return|return
operator|!
name|Optional
operator|.
name|of
argument_list|(
name|text
argument_list|)
operator|.
name|equals
argument_list|(
name|getEntry
argument_list|()
operator|.
name|getField
argument_list|(
name|fieldEditor
operator|.
name|getFieldName
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
block|}
DECL|method|markIfModified (FieldEditor fieldEditor)
specifier|public
name|void
name|markIfModified
parameter_list|(
name|FieldEditor
name|fieldEditor
parameter_list|)
block|{
comment|// Only mark as changed if not already is and the field was indeed modified
if|if
condition|(
operator|!
name|updating
operator|&&
operator|!
name|basePanel
operator|.
name|isModified
argument_list|()
operator|&&
name|isFieldModified
argument_list|(
name|fieldEditor
argument_list|)
condition|)
block|{
name|markBaseChanged
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|markBaseChanged ()
specifier|private
name|void
name|markBaseChanged
parameter_list|()
block|{
name|basePanel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
block|}
comment|/**      * Only sets the activeField variable but does not focus it.      *<p>      * If you want to focus it call {@link #focus()} afterwards.      */
comment|// TODO: Reenable or delete this
comment|//public void setActive(FieldEditor fieldEditor) {
comment|//    activeField = fieldEditor;
comment|//}
comment|//public FieldEditor getActive() {
comment|//    return activeField;
comment|//}
DECL|method|setActive (String fieldName)
specifier|public
name|void
name|setActive
parameter_list|(
name|String
name|fieldName
parameter_list|)
block|{
if|if
condition|(
name|editors
operator|.
name|containsKey
argument_list|(
name|fieldName
argument_list|)
condition|)
block|{
name|activeField
operator|=
name|editors
operator|.
name|get
argument_list|(
name|fieldName
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|getFields ()
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getFields
parameter_list|()
block|{
return|return
name|Collections
operator|.
name|unmodifiableList
argument_list|(
name|fields
argument_list|)
return|;
block|}
DECL|method|focus ()
specifier|public
name|void
name|focus
parameter_list|()
block|{
if|if
condition|(
name|activeField
operator|!=
literal|null
condition|)
block|{
name|activeField
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      * Reset all fields from the data in the BibEntry.      */
DECL|method|updateAll ()
specifier|public
name|void
name|updateAll
parameter_list|()
block|{
name|setEntry
argument_list|(
name|getEntry
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|updateField (String field, String content)
specifier|public
name|boolean
name|updateField
parameter_list|(
name|String
name|field
parameter_list|,
name|String
name|content
parameter_list|)
block|{
if|if
condition|(
operator|!
name|editors
operator|.
name|containsKey
argument_list|(
name|field
argument_list|)
condition|)
block|{
return|return
literal|false
return|;
block|}
comment|// TODO: Reenable or probably better delete this
comment|/*         FieldEditor fieldEditor = editors.get(field);         if (fieldEditor.getText().equals(content)) {             return true;         }          // trying to preserve current edit position (fixes SF bug #1285)         if (fieldEditor.getTextComponent() instanceof JTextComponent) {             int initialCaretPosition = ((JTextComponent) fieldEditor).getCaretPosition();             fieldEditor.setText(content);             int textLength = fieldEditor.getText().length();             if (initialCaretPosition< textLength) {                 ((JTextComponent) fieldEditor).setCaretPosition(initialCaretPosition);             } else {                 ((JTextComponent) fieldEditor).setCaretPosition(textLength);             }         } else {             fieldEditor.setText(content);         }         */
return|return
literal|true
return|;
block|}
DECL|method|setEnabled (boolean enabled)
specifier|public
name|void
name|setEnabled
parameter_list|(
name|boolean
name|enabled
parameter_list|)
block|{
comment|/*         // TODO: Reenable this         for (FieldEditor editor : editors.values()) {             editor.setEnabled(enabled);         }         */
block|}
DECL|method|getPane ()
specifier|public
name|Component
name|getPane
parameter_list|()
block|{
return|return
name|scrollPane
return|;
block|}
DECL|method|getParent ()
specifier|public
name|EntryEditor
name|getParent
parameter_list|()
block|{
return|return
name|parent
return|;
block|}
DECL|method|getTabTitle ()
specifier|public
name|String
name|getTabTitle
parameter_list|()
block|{
return|return
name|tabTitle
return|;
block|}
DECL|method|setupKeyBindings (final InputMap inputMap, final ActionMap actionMap)
specifier|private
name|void
name|setupKeyBindings
parameter_list|(
specifier|final
name|InputMap
name|inputMap
parameter_list|,
specifier|final
name|ActionMap
name|actionMap
parameter_list|)
block|{
name|inputMap
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
name|ENTRY_EDITOR_PREVIOUS_ENTRY
argument_list|)
argument_list|,
literal|"prev"
argument_list|)
expr_stmt|;
name|actionMap
operator|.
name|put
argument_list|(
literal|"prev"
argument_list|,
name|parent
operator|.
name|getPrevEntryAction
argument_list|()
argument_list|)
expr_stmt|;
name|inputMap
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
name|ENTRY_EDITOR_NEXT_ENTRY
argument_list|)
argument_list|,
literal|"next"
argument_list|)
expr_stmt|;
name|actionMap
operator|.
name|put
argument_list|(
literal|"next"
argument_list|,
name|parent
operator|.
name|getNextEntryAction
argument_list|()
argument_list|)
expr_stmt|;
name|inputMap
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
name|ENTRY_EDITOR_STORE_FIELD
argument_list|)
argument_list|,
literal|"store"
argument_list|)
expr_stmt|;
name|actionMap
operator|.
name|put
argument_list|(
literal|"store"
argument_list|,
name|parent
operator|.
name|getStoreFieldAction
argument_list|()
argument_list|)
expr_stmt|;
name|inputMap
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
name|ENTRY_EDITOR_NEXT_PANEL
argument_list|)
argument_list|,
literal|"right"
argument_list|)
expr_stmt|;
name|inputMap
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
name|ENTRY_EDITOR_NEXT_PANEL_2
argument_list|)
argument_list|,
literal|"right"
argument_list|)
expr_stmt|;
name|actionMap
operator|.
name|put
argument_list|(
literal|"left"
argument_list|,
name|parent
operator|.
name|getSwitchLeftAction
argument_list|()
argument_list|)
expr_stmt|;
name|inputMap
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
name|ENTRY_EDITOR_PREVIOUS_PANEL
argument_list|)
argument_list|,
literal|"left"
argument_list|)
expr_stmt|;
name|inputMap
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
name|ENTRY_EDITOR_PREVIOUS_PANEL_2
argument_list|)
argument_list|,
literal|"left"
argument_list|)
expr_stmt|;
name|actionMap
operator|.
name|put
argument_list|(
literal|"right"
argument_list|,
name|parent
operator|.
name|getSwitchRightAction
argument_list|()
argument_list|)
expr_stmt|;
name|inputMap
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
name|HELP
argument_list|)
argument_list|,
literal|"help"
argument_list|)
expr_stmt|;
name|actionMap
operator|.
name|put
argument_list|(
literal|"help"
argument_list|,
name|parent
operator|.
name|getHelpAction
argument_list|()
argument_list|)
expr_stmt|;
name|inputMap
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
name|SAVE_DATABASE
argument_list|)
argument_list|,
literal|"save"
argument_list|)
expr_stmt|;
name|actionMap
operator|.
name|put
argument_list|(
literal|"save"
argument_list|,
name|parent
operator|.
name|getSaveDatabaseAction
argument_list|()
argument_list|)
expr_stmt|;
name|inputMap
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
name|NEXT_TAB
argument_list|)
argument_list|,
literal|"nexttab"
argument_list|)
expr_stmt|;
name|actionMap
operator|.
name|put
argument_list|(
literal|"nexttab"
argument_list|,
name|this
operator|.
name|frame
operator|.
name|nextTab
argument_list|)
expr_stmt|;
name|inputMap
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
name|PREVIOUS_TAB
argument_list|)
argument_list|,
literal|"prevtab"
argument_list|)
expr_stmt|;
name|actionMap
operator|.
name|put
argument_list|(
literal|"prevtab"
argument_list|,
name|this
operator|.
name|frame
operator|.
name|prevTab
argument_list|)
expr_stmt|;
block|}
comment|/**      * Set up key bindings and focus listener for the FieldEditor.      *      * @param component      */
DECL|method|setupJTextComponent (final JComponent component, final AutoCompleteListener autoCompleteListener)
specifier|private
name|void
name|setupJTextComponent
parameter_list|(
specifier|final
name|JComponent
name|component
parameter_list|,
specifier|final
name|AutoCompleteListener
name|autoCompleteListener
parameter_list|)
block|{
comment|// Here we add focus listeners to the component. The funny code is because we need
comment|// to guarantee that the AutoCompleteListener - if used - is called before fieldListener
comment|// on a focus lost event. The AutoCompleteListener is responsible for removing any
comment|// current suggestion when focus is lost, and this must be done before fieldListener
comment|// stores the current edit. Swing doesn't guarantee the order of execution of event
comment|// listeners, so we handle this by only adding the AutoCompleteListener and telling
comment|// it to call fieldListener afterwards. If no AutoCompleteListener is used, we
comment|// add the fieldListener normally.
if|if
condition|(
name|autoCompleteListener
operator|==
literal|null
condition|)
block|{
name|component
operator|.
name|addFocusListener
argument_list|(
name|fieldListener
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|component
operator|.
name|addKeyListener
argument_list|(
name|autoCompleteListener
argument_list|)
expr_stmt|;
name|component
operator|.
name|addFocusListener
argument_list|(
name|autoCompleteListener
argument_list|)
expr_stmt|;
name|autoCompleteListener
operator|.
name|setNextFocusListener
argument_list|(
name|fieldListener
argument_list|)
expr_stmt|;
block|}
name|setupKeyBindings
argument_list|(
name|component
operator|.
name|getInputMap
argument_list|(
name|JComponent
operator|.
name|WHEN_FOCUSED
argument_list|)
argument_list|,
name|component
operator|.
name|getActionMap
argument_list|()
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|AWTKeyStroke
argument_list|>
name|keys
init|=
operator|new
name|HashSet
argument_list|<>
argument_list|(
name|component
operator|.
name|getFocusTraversalKeys
argument_list|(
name|KeyboardFocusManager
operator|.
name|FORWARD_TRAVERSAL_KEYS
argument_list|)
argument_list|)
decl_stmt|;
name|keys
operator|.
name|clear
argument_list|()
expr_stmt|;
name|keys
operator|.
name|add
argument_list|(
name|AWTKeyStroke
operator|.
name|getAWTKeyStroke
argument_list|(
literal|"pressed TAB"
argument_list|)
argument_list|)
expr_stmt|;
name|component
operator|.
name|setFocusTraversalKeys
argument_list|(
name|KeyboardFocusManager
operator|.
name|FORWARD_TRAVERSAL_KEYS
argument_list|,
name|keys
argument_list|)
expr_stmt|;
name|keys
operator|=
operator|new
name|HashSet
argument_list|<>
argument_list|(
name|component
operator|.
name|getFocusTraversalKeys
argument_list|(
name|KeyboardFocusManager
operator|.
name|BACKWARD_TRAVERSAL_KEYS
argument_list|)
argument_list|)
expr_stmt|;
name|keys
operator|.
name|clear
argument_list|()
expr_stmt|;
name|keys
operator|.
name|add
argument_list|(
name|KeyStroke
operator|.
name|getKeyStroke
argument_list|(
literal|"shift pressed TAB"
argument_list|)
argument_list|)
expr_stmt|;
name|component
operator|.
name|setFocusTraversalKeys
argument_list|(
name|KeyboardFocusManager
operator|.
name|BACKWARD_TRAVERSAL_KEYS
argument_list|,
name|keys
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

