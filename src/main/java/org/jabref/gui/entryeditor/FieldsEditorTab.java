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
name|Collection
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
name|Optional
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|SortedSet
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
name|javafx
operator|.
name|application
operator|.
name|Platform
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
name|control
operator|.
name|SplitPane
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
name|DialogService
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
name|SuggestionProviders
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
name|preview
operator|.
name|PreviewPanel
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
name|BibDatabaseContext
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
name|field
operator|.
name|Field
import|;
end_import

begin_comment
comment|/**  * A single tab displayed in the EntryEditor holding several FieldEditors.  */
end_comment

begin_class
DECL|class|FieldsEditorTab
specifier|abstract
class|class
name|FieldsEditorTab
extends|extends
name|EntryEditorTab
block|{
DECL|field|previewPanel
specifier|public
name|PreviewPanel
name|previewPanel
decl_stmt|;
DECL|field|databaseContext
specifier|protected
specifier|final
name|BibDatabaseContext
name|databaseContext
decl_stmt|;
DECL|field|editors
specifier|private
specifier|final
name|Map
argument_list|<
name|Field
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
DECL|field|isCompressed
specifier|private
specifier|final
name|boolean
name|isCompressed
decl_stmt|;
DECL|field|suggestionProviders
specifier|private
specifier|final
name|SuggestionProviders
name|suggestionProviders
decl_stmt|;
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
decl_stmt|;
DECL|field|activeField
specifier|private
name|FieldEditorFX
name|activeField
decl_stmt|;
DECL|field|undoManager
specifier|private
name|UndoManager
name|undoManager
decl_stmt|;
DECL|field|fields
specifier|private
name|Collection
argument_list|<
name|Field
argument_list|>
name|fields
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|gridPane
specifier|private
name|GridPane
name|gridPane
decl_stmt|;
DECL|method|FieldsEditorTab (boolean compressed, BibDatabaseContext databaseContext, SuggestionProviders suggestionProviders, UndoManager undoManager, DialogService dialogService)
specifier|public
name|FieldsEditorTab
parameter_list|(
name|boolean
name|compressed
parameter_list|,
name|BibDatabaseContext
name|databaseContext
parameter_list|,
name|SuggestionProviders
name|suggestionProviders
parameter_list|,
name|UndoManager
name|undoManager
parameter_list|,
name|DialogService
name|dialogService
parameter_list|)
block|{
name|this
operator|.
name|isCompressed
operator|=
name|compressed
expr_stmt|;
name|this
operator|.
name|databaseContext
operator|=
name|databaseContext
expr_stmt|;
name|this
operator|.
name|suggestionProviders
operator|=
name|suggestionProviders
expr_stmt|;
name|this
operator|.
name|undoManager
operator|=
name|undoManager
expr_stmt|;
name|this
operator|.
name|dialogService
operator|=
name|dialogService
expr_stmt|;
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
DECL|method|setupPanel (BibEntry entry, boolean compressed, SuggestionProviders suggestionProviders, UndoManager undoManager)
specifier|private
name|void
name|setupPanel
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|boolean
name|compressed
parameter_list|,
name|SuggestionProviders
name|suggestionProviders
parameter_list|,
name|UndoManager
name|undoManager
parameter_list|)
block|{
comment|// The preferences might be not initialized in tests -> return immediately
comment|// TODO: Replace this ugly workaround by proper injection propagation
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|==
literal|null
condition|)
block|{
return|return;
block|}
name|editors
operator|.
name|clear
argument_list|()
expr_stmt|;
name|gridPane
operator|.
name|getChildren
argument_list|()
operator|.
name|clear
argument_list|()
expr_stmt|;
name|gridPane
operator|.
name|getColumnConstraints
argument_list|()
operator|.
name|clear
argument_list|()
expr_stmt|;
name|gridPane
operator|.
name|getRowConstraints
argument_list|()
operator|.
name|clear
argument_list|()
expr_stmt|;
name|fields
operator|=
name|determineFieldsToShow
argument_list|(
name|entry
argument_list|)
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
name|boolean
name|isFirstField
init|=
literal|true
decl_stmt|;
for|for
control|(
name|Field
name|field
range|:
name|fields
control|)
block|{
name|FieldEditorFX
name|fieldEditor
init|=
name|FieldEditors
operator|.
name|getForField
argument_list|(
name|field
argument_list|,
name|Globals
operator|.
name|TASK_EXECUTOR
argument_list|,
name|dialogService
argument_list|,
name|Globals
operator|.
name|journalAbbreviationLoader
operator|.
name|getRepository
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getJournalAbbreviationPreferences
argument_list|()
argument_list|)
argument_list|,
name|Globals
operator|.
name|prefs
argument_list|,
name|databaseContext
argument_list|,
name|entry
operator|.
name|getType
argument_list|()
argument_list|,
name|suggestionProviders
argument_list|,
name|undoManager
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
name|field
argument_list|,
name|fieldEditor
argument_list|)
expr_stmt|;
if|if
condition|(
name|isFirstField
condition|)
block|{
name|activeField
operator|=
name|fieldEditor
expr_stmt|;
name|isFirstField
operator|=
literal|false
expr_stmt|;
block|}
name|labels
operator|.
name|add
argument_list|(
operator|new
name|FieldNameLabel
argument_list|(
name|field
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
name|setCompressedRowLayout
argument_list|(
name|gridPane
argument_list|,
name|rows
argument_list|)
expr_stmt|;
block|}
else|else
block|{
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
name|setRegularRowLayout
argument_list|(
name|gridPane
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|setRegularRowLayout (GridPane gridPane)
specifier|private
name|void
name|setRegularRowLayout
parameter_list|(
name|GridPane
name|gridPane
parameter_list|)
block|{
name|double
name|totalWeight
init|=
name|fields
operator|.
name|stream
argument_list|()
operator|.
name|mapToDouble
argument_list|(
name|field
lambda|->
name|editors
operator|.
name|get
argument_list|(
name|field
argument_list|)
operator|.
name|getWeight
argument_list|()
argument_list|)
operator|.
name|sum
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|RowConstraints
argument_list|>
name|constraints
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|Field
name|field
range|:
name|fields
control|)
block|{
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
name|rowExpand
operator|.
name|setPercentHeight
argument_list|(
literal|100
operator|*
name|editors
operator|.
name|get
argument_list|(
name|field
argument_list|)
operator|.
name|getWeight
argument_list|()
operator|/
name|totalWeight
argument_list|)
expr_stmt|;
name|constraints
operator|.
name|add
argument_list|(
name|rowExpand
argument_list|)
expr_stmt|;
block|}
name|gridPane
operator|.
name|getRowConstraints
argument_list|()
operator|.
name|addAll
argument_list|(
name|constraints
argument_list|)
expr_stmt|;
block|}
DECL|method|setCompressedRowLayout (GridPane gridPane, int rows)
specifier|private
name|void
name|setCompressedRowLayout
parameter_list|(
name|GridPane
name|gridPane
parameter_list|,
name|int
name|rows
parameter_list|)
block|{
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
operator|(
name|double
operator|)
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
block|}
comment|/**      * Focuses the given field.      */
DECL|method|requestFocus (Field fieldName)
specifier|public
name|void
name|requestFocus
parameter_list|(
name|Field
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
name|activeField
operator|.
name|focus
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|shouldShow (BibEntry entry)
specifier|public
name|boolean
name|shouldShow
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
return|return
operator|!
name|determineFieldsToShow
argument_list|(
name|entry
argument_list|)
operator|.
name|isEmpty
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|handleFocus ()
specifier|public
name|void
name|handleFocus
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
name|focus
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|bindToEntry (BibEntry entry)
specifier|protected
name|void
name|bindToEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
name|Optional
argument_list|<
name|Field
argument_list|>
name|selectedFieldName
init|=
name|editors
operator|.
name|entrySet
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|editor
lambda|->
name|editor
operator|.
name|getValue
argument_list|()
operator|.
name|childIsFocused
argument_list|()
argument_list|)
operator|.
name|map
argument_list|(
name|Map
operator|.
name|Entry
operator|::
name|getKey
argument_list|)
operator|.
name|findFirst
argument_list|()
decl_stmt|;
name|initPanel
argument_list|()
expr_stmt|;
name|setupPanel
argument_list|(
name|entry
argument_list|,
name|isCompressed
argument_list|,
name|suggestionProviders
argument_list|,
name|undoManager
argument_list|)
expr_stmt|;
name|previewPanel
operator|.
name|setEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|Platform
operator|.
name|runLater
argument_list|(
parameter_list|()
lambda|->
block|{
comment|// Restore focus to field (run this async so that editor is already initialized correctly)
name|selectedFieldName
operator|.
name|ifPresent
argument_list|(
name|this
operator|::
name|requestFocus
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|determineFieldsToShow (BibEntry entry)
specifier|protected
specifier|abstract
name|SortedSet
argument_list|<
name|Field
argument_list|>
name|determineFieldsToShow
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
function_decl|;
DECL|method|getShownFields ()
specifier|public
name|Collection
argument_list|<
name|Field
argument_list|>
name|getShownFields
parameter_list|()
block|{
return|return
name|fields
return|;
block|}
DECL|method|initPanel ()
specifier|private
name|void
name|initPanel
parameter_list|()
block|{
if|if
condition|(
name|gridPane
operator|==
literal|null
condition|)
block|{
name|gridPane
operator|=
operator|new
name|GridPane
argument_list|()
expr_stmt|;
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
name|previewPanel
operator|=
operator|new
name|PreviewPanel
argument_list|(
name|databaseContext
argument_list|,
literal|null
argument_list|,
name|dialogService
argument_list|,
name|ExternalFileTypes
operator|.
name|getInstance
argument_list|()
argument_list|,
name|Globals
operator|.
name|getKeyPrefs
argument_list|()
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getPreviewPreferences
argument_list|()
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
name|SplitPane
name|container
init|=
operator|new
name|SplitPane
argument_list|(
name|scrollPane
argument_list|,
name|previewPanel
argument_list|)
decl_stmt|;
name|setContent
argument_list|(
name|container
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

