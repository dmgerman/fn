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
name|LinkedHashMap
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
name|Objects
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
name|stream
operator|.
name|Stream
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|geometry
operator|.
name|VPos
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|Node
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|Parent
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|Label
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|ScrollPane
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|layout
operator|.
name|ColumnConstraints
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|layout
operator|.
name|GridPane
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|layout
operator|.
name|Priority
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|layout
operator|.
name|Region
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|layout
operator|.
name|RowConstraints
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

begin_comment
comment|/**  * A single tab displayed in the EntryEditor holding several FieldEditors.  */
end_comment

begin_class
DECL|class|FieldsEditorTab
class|class
name|FieldsEditorTab
extends|extends
name|EntryEditorTab
block|{
DECL|field|panel
specifier|private
specifier|final
name|Region
name|panel
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
name|LinkedHashMap
argument_list|<>
argument_list|()
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
DECL|field|entry
specifier|private
specifier|final
name|BibEntry
name|entry
decl_stmt|;
DECL|field|activeField
specifier|private
name|FieldEditorFX
name|activeField
decl_stmt|;
DECL|method|FieldsEditorTab (JabRefFrame frame, BasePanel basePanel, List<String> fields, EntryEditor parent, boolean addKeyField, boolean compressed, BibEntry entry)
specifier|public
name|FieldsEditorTab
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|BasePanel
name|basePanel
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
name|BibEntry
name|entry
parameter_list|)
block|{
name|this
operator|.
name|entry
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|this
operator|.
name|fields
operator|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|fields
argument_list|)
argument_list|)
expr_stmt|;
comment|// Add the edit field for Bibtex-key.
if|if
condition|(
name|addKeyField
condition|)
block|{
name|this
operator|.
name|fields
operator|.
name|add
argument_list|(
name|BibEntry
operator|.
name|KEY_FIELD
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
name|frame
operator|=
name|frame
expr_stmt|;
name|this
operator|.
name|basePanel
operator|=
name|basePanel
expr_stmt|;
name|panel
operator|=
name|setupPanel
argument_list|(
name|frame
argument_list|,
name|basePanel
argument_list|,
name|compressed
argument_list|)
expr_stmt|;
comment|// The following line makes sure focus cycles inside tab instead of being lost to other parts of the frame:
comment|//panel.setFocusCycleRoot(true);
block|}
DECL|method|addColumn (GridPane gridPane, int columnIndex, List<Label> nodes)
specifier|private
specifier|static
name|void
name|addColumn
parameter_list|(
name|GridPane
name|gridPane
parameter_list|,
name|int
name|columnIndex
parameter_list|,
name|List
argument_list|<
name|Label
argument_list|>
name|nodes
parameter_list|)
block|{
name|gridPane
operator|.
name|addColumn
argument_list|(
name|columnIndex
argument_list|,
name|nodes
operator|.
name|toArray
argument_list|(
operator|new
name|Node
index|[
name|nodes
operator|.
name|size
argument_list|()
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|addColumn (GridPane gridPane, int columnIndex, Stream<Parent> nodes)
specifier|private
specifier|static
name|void
name|addColumn
parameter_list|(
name|GridPane
name|gridPane
parameter_list|,
name|int
name|columnIndex
parameter_list|,
name|Stream
argument_list|<
name|Parent
argument_list|>
name|nodes
parameter_list|)
block|{
name|gridPane
operator|.
name|addColumn
argument_list|(
name|columnIndex
argument_list|,
name|nodes
operator|.
name|toArray
argument_list|(
name|Node
index|[]
operator|::
operator|new
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|convertToHex (java.awt.Color color)
specifier|private
name|String
name|convertToHex
parameter_list|(
name|java
operator|.
name|awt
operator|.
name|Color
name|color
parameter_list|)
block|{
return|return
name|String
operator|.
name|format
argument_list|(
literal|"#%02x%02x%02x"
argument_list|,
name|color
operator|.
name|getRed
argument_list|()
argument_list|,
name|color
operator|.
name|getGreen
argument_list|()
argument_list|,
name|color
operator|.
name|getBlue
argument_list|()
argument_list|)
return|;
block|}
DECL|method|setupPanel (JabRefFrame frame, BasePanel bPanel, boolean compressed)
specifier|private
name|Region
name|setupPanel
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|BasePanel
name|bPanel
parameter_list|,
name|boolean
name|compressed
parameter_list|)
block|{
comment|//setupKeyBindings(panel.getInputMap(JComponent.WHEN_FOCUSED), panel.getActionMap());
name|editors
operator|.
name|clear
argument_list|()
expr_stmt|;
name|List
argument_list|<
name|Label
argument_list|>
name|labels
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|fieldName
range|:
name|fields
control|)
block|{
comment|// TODO: Reenable/migrate this
comment|// Store the editor for later reference:
comment|/*             FieldEditor fieldEditor;             int defaultHeight;             int wHeight = (int) (50.0 * InternalBibtexFields.getFieldWeight(field));             if (InternalBibtexFields.getFieldProperties(field).contains(FieldProperty.SINGLE_ENTRY_LINK)) {                 fieldEditor = new EntryLinkListEditor(frame, bPanel.getBibDatabaseContext(), field, null, parent,                         true);                 defaultHeight = 0;             } else if (InternalBibtexFields.getFieldProperties(field).contains(FieldProperty.MULTIPLE_ENTRY_LINK)) {                 fieldEditor = new EntryLinkListEditor(frame, bPanel.getBibDatabaseContext(), field, null, parent,                         false);                 defaultHeight = 0;             } else {                 fieldEditor = new TextArea(field, null, getPrompt(field));                 //parent.addSearchListener((TextArea) fieldEditor);                 defaultHeight = fieldEditor.getPane().getPreferredSize().height;             }              Optional<JComponent> extra = parent.getExtra(fieldEditor);             */
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
name|TASK_EXECUTOR
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
argument_list|,
name|bPanel
operator|.
name|getSuggestionProviders
argument_list|()
argument_list|)
decl_stmt|;
name|fieldEditor
operator|.
name|bindToEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
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
name|labels
operator|.
name|add
argument_list|(
operator|new
name|FieldNameLabel
argument_list|(
name|fieldName
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|GridPane
name|gridPane
init|=
operator|new
name|GridPane
argument_list|()
decl_stmt|;
name|gridPane
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"editorPane"
argument_list|)
expr_stmt|;
name|ColumnConstraints
name|columnExpand
init|=
operator|new
name|ColumnConstraints
argument_list|()
decl_stmt|;
name|columnExpand
operator|.
name|setHgrow
argument_list|(
name|Priority
operator|.
name|ALWAYS
argument_list|)
expr_stmt|;
name|ColumnConstraints
name|columnDoNotContract
init|=
operator|new
name|ColumnConstraints
argument_list|()
decl_stmt|;
name|columnDoNotContract
operator|.
name|setMinWidth
argument_list|(
name|Region
operator|.
name|USE_PREF_SIZE
argument_list|)
expr_stmt|;
name|int
name|rows
decl_stmt|;
if|if
condition|(
name|compressed
condition|)
block|{
name|rows
operator|=
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
literal|2
argument_list|)
expr_stmt|;
name|addColumn
argument_list|(
name|gridPane
argument_list|,
literal|0
argument_list|,
name|labels
operator|.
name|subList
argument_list|(
literal|0
argument_list|,
name|rows
argument_list|)
argument_list|)
expr_stmt|;
name|addColumn
argument_list|(
name|gridPane
argument_list|,
literal|3
argument_list|,
name|labels
operator|.
name|subList
argument_list|(
name|rows
argument_list|,
name|labels
operator|.
name|size
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|addColumn
argument_list|(
name|gridPane
argument_list|,
literal|1
argument_list|,
name|editors
operator|.
name|values
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|FieldEditorFX
operator|::
name|getNode
argument_list|)
operator|.
name|limit
argument_list|(
name|rows
argument_list|)
argument_list|)
expr_stmt|;
name|addColumn
argument_list|(
name|gridPane
argument_list|,
literal|4
argument_list|,
name|editors
operator|.
name|values
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|FieldEditorFX
operator|::
name|getNode
argument_list|)
operator|.
name|skip
argument_list|(
name|rows
argument_list|)
argument_list|)
expr_stmt|;
name|gridPane
operator|.
name|getColumnConstraints
argument_list|()
operator|.
name|addAll
argument_list|(
name|columnDoNotContract
argument_list|,
name|columnExpand
argument_list|,
operator|new
name|ColumnConstraints
argument_list|(
literal|10
argument_list|)
argument_list|,
name|columnDoNotContract
argument_list|,
name|columnExpand
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|rows
operator|=
name|fields
operator|.
name|size
argument_list|()
expr_stmt|;
name|addColumn
argument_list|(
name|gridPane
argument_list|,
literal|0
argument_list|,
name|labels
argument_list|)
expr_stmt|;
name|addColumn
argument_list|(
name|gridPane
argument_list|,
literal|1
argument_list|,
name|editors
operator|.
name|values
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|FieldEditorFX
operator|::
name|getNode
argument_list|)
argument_list|)
expr_stmt|;
name|gridPane
operator|.
name|getColumnConstraints
argument_list|()
operator|.
name|addAll
argument_list|(
name|columnDoNotContract
argument_list|,
name|columnExpand
argument_list|)
expr_stmt|;
block|}
name|RowConstraints
name|rowExpand
init|=
operator|new
name|RowConstraints
argument_list|()
decl_stmt|;
name|rowExpand
operator|.
name|setVgrow
argument_list|(
name|Priority
operator|.
name|ALWAYS
argument_list|)
expr_stmt|;
name|rowExpand
operator|.
name|setValignment
argument_list|(
name|VPos
operator|.
name|TOP
argument_list|)
expr_stmt|;
if|if
condition|(
name|rows
operator|==
literal|0
condition|)
block|{
name|rowExpand
operator|.
name|setPercentHeight
argument_list|(
literal|100
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|rowExpand
operator|.
name|setPercentHeight
argument_list|(
literal|100
operator|/
name|rows
argument_list|)
expr_stmt|;
block|}
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
name|gridPane
operator|.
name|getRowConstraints
argument_list|()
operator|.
name|add
argument_list|(
name|rowExpand
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|GUIGlobals
operator|.
name|currentFont
operator|!=
literal|null
condition|)
block|{
name|gridPane
operator|.
name|setStyle
argument_list|(
literal|"text-area-background: "
operator|+
name|convertToHex
argument_list|(
name|GUIGlobals
operator|.
name|validFieldBackgroundColor
argument_list|)
operator|+
literal|";"
operator|+
literal|"text-area-foreground: "
operator|+
name|convertToHex
argument_list|(
name|GUIGlobals
operator|.
name|editorTextColor
argument_list|)
operator|+
literal|";"
operator|+
literal|"text-area-highlight: "
operator|+
name|convertToHex
argument_list|(
name|GUIGlobals
operator|.
name|activeBackgroundColor
argument_list|)
operator|+
literal|";"
argument_list|)
expr_stmt|;
block|}
name|gridPane
operator|.
name|getStylesheets
argument_list|()
operator|.
name|add
argument_list|(
literal|"org/jabref/gui/entryeditor/EntryEditor.css"
argument_list|)
expr_stmt|;
comment|// Warp everything in a scroll-pane
name|ScrollPane
name|scrollPane
init|=
operator|new
name|ScrollPane
argument_list|()
decl_stmt|;
name|scrollPane
operator|.
name|setHbarPolicy
argument_list|(
name|ScrollPane
operator|.
name|ScrollBarPolicy
operator|.
name|NEVER
argument_list|)
expr_stmt|;
name|scrollPane
operator|.
name|setVbarPolicy
argument_list|(
name|ScrollPane
operator|.
name|ScrollBarPolicy
operator|.
name|AS_NEEDED
argument_list|)
expr_stmt|;
name|scrollPane
operator|.
name|setContent
argument_list|(
name|gridPane
argument_list|)
expr_stmt|;
name|scrollPane
operator|.
name|setFitToWidth
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|scrollPane
operator|.
name|setFitToHeight
argument_list|(
literal|true
argument_list|)
expr_stmt|;
return|return
name|scrollPane
return|;
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
comment|/**      * Only sets the activeField variable but does not focus it.      *<p>      * If you want to focus it call {@link #focus()} afterwards.      */
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
annotation|@
name|Override
DECL|method|shouldShow ()
specifier|public
name|boolean
name|shouldShow
parameter_list|()
block|{
return|return
operator|!
name|fields
operator|.
name|isEmpty
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|requestFocus ()
specifier|public
name|void
name|requestFocus
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
annotation|@
name|Override
DECL|method|initialize ()
specifier|protected
name|void
name|initialize
parameter_list|()
block|{
name|setContent
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

