begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.maintable
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|maintable
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
name|Arrays
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
name|Objects
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
name|undo
operator|.
name|UndoManager
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
name|control
operator|.
name|ContextMenu
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
name|MenuItem
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
name|TableColumn
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
name|Tooltip
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|input
operator|.
name|MouseButton
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
name|BorderPane
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|paint
operator|.
name|Color
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|shape
operator|.
name|Rectangle
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
name|IconTheme
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
name|JabRefIcon
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
name|fieldeditors
operator|.
name|LinkedFileViewModel
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
name|SpecialFieldValueViewModel
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
name|gui
operator|.
name|util
operator|.
name|OptionalValueTableCellFactory
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
name|ValueTableCellFactory
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
name|LinkedFile
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
name|model
operator|.
name|entry
operator|.
name|specialfields
operator|.
name|SpecialFieldValue
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
name|AbstractGroup
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
name|util
operator|.
name|OptionalUtil
import|;
end_import

begin_import
import|import
name|org
operator|.
name|controlsfx
operator|.
name|control
operator|.
name|Rating
import|;
end_import

begin_import
import|import
name|org
operator|.
name|fxmisc
operator|.
name|easybind
operator|.
name|EasyBind
import|;
end_import

begin_class
DECL|class|MainTableColumnFactory
class|class
name|MainTableColumnFactory
block|{
DECL|field|ICON_COLUMN
specifier|private
specifier|static
specifier|final
name|String
name|ICON_COLUMN
init|=
literal|"column-icon"
decl_stmt|;
DECL|field|GROUP_COLUMN
specifier|private
specifier|static
specifier|final
name|String
name|GROUP_COLUMN
init|=
literal|"column-groups"
decl_stmt|;
DECL|field|preferences
specifier|private
specifier|final
name|ColumnPreferences
name|preferences
decl_stmt|;
DECL|field|externalFileTypes
specifier|private
specifier|final
name|ExternalFileTypes
name|externalFileTypes
decl_stmt|;
DECL|field|database
specifier|private
specifier|final
name|BibDatabaseContext
name|database
decl_stmt|;
DECL|field|cellFactory
specifier|private
specifier|final
name|CellFactory
name|cellFactory
decl_stmt|;
DECL|field|undoManager
specifier|private
specifier|final
name|UndoManager
name|undoManager
decl_stmt|;
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
decl_stmt|;
DECL|method|MainTableColumnFactory (BibDatabaseContext database, ColumnPreferences preferences, ExternalFileTypes externalFileTypes, UndoManager undoManager, DialogService dialogService)
specifier|public
name|MainTableColumnFactory
parameter_list|(
name|BibDatabaseContext
name|database
parameter_list|,
name|ColumnPreferences
name|preferences
parameter_list|,
name|ExternalFileTypes
name|externalFileTypes
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
name|database
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|database
argument_list|)
expr_stmt|;
name|this
operator|.
name|preferences
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|preferences
argument_list|)
expr_stmt|;
name|this
operator|.
name|externalFileTypes
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|externalFileTypes
argument_list|)
expr_stmt|;
name|this
operator|.
name|dialogService
operator|=
name|dialogService
expr_stmt|;
name|this
operator|.
name|cellFactory
operator|=
operator|new
name|CellFactory
argument_list|(
name|externalFileTypes
argument_list|,
name|undoManager
argument_list|)
expr_stmt|;
name|this
operator|.
name|undoManager
operator|=
name|undoManager
expr_stmt|;
block|}
DECL|method|createColumns ()
specifier|public
name|List
argument_list|<
name|TableColumn
argument_list|<
name|BibEntryTableViewModel
argument_list|,
name|?
argument_list|>
argument_list|>
name|createColumns
parameter_list|()
block|{
name|List
argument_list|<
name|TableColumn
argument_list|<
name|BibEntryTableViewModel
argument_list|,
name|?
argument_list|>
argument_list|>
name|columns
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|columns
operator|.
name|add
argument_list|(
name|createGroupColumn
argument_list|()
argument_list|)
expr_stmt|;
comment|// Add column for linked files
if|if
condition|(
name|preferences
operator|.
name|showFileColumn
argument_list|()
condition|)
block|{
name|columns
operator|.
name|add
argument_list|(
name|createFileColumn
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// Add column for DOI/URL
if|if
condition|(
name|preferences
operator|.
name|showUrlColumn
argument_list|()
condition|)
block|{
if|if
condition|(
name|preferences
operator|.
name|preferDoiOverUrl
argument_list|()
condition|)
block|{
name|columns
operator|.
name|add
argument_list|(
name|createIconColumn
argument_list|(
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|DOI
argument_list|,
name|FieldName
operator|.
name|DOI
argument_list|,
name|FieldName
operator|.
name|URL
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|columns
operator|.
name|add
argument_list|(
name|createIconColumn
argument_list|(
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|WWW
argument_list|,
name|FieldName
operator|.
name|URL
argument_list|,
name|FieldName
operator|.
name|DOI
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Add column for eprints
if|if
condition|(
name|preferences
operator|.
name|showEprintColumn
argument_list|()
condition|)
block|{
name|columns
operator|.
name|add
argument_list|(
name|createIconColumn
argument_list|(
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|WWW
argument_list|,
name|FieldName
operator|.
name|EPRINT
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Add columns for other file types
name|columns
operator|.
name|addAll
argument_list|(
name|preferences
operator|.
name|getExtraFileColumns
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|this
operator|::
name|createExtraFileColumn
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
comment|// Add 'normal' bibtex fields as configured in the preferences
name|columns
operator|.
name|addAll
argument_list|(
name|createNormalColumns
argument_list|()
argument_list|)
expr_stmt|;
comment|// Add the "special" icon columns (e.g., ranking, file, ...) that are enabled in preferences
for|for
control|(
name|SpecialField
name|field
range|:
name|preferences
operator|.
name|getSpecialFieldColumns
argument_list|()
control|)
block|{
name|columns
operator|.
name|add
argument_list|(
name|createSpecialFieldColumn
argument_list|(
operator|(
name|field
operator|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|columns
return|;
block|}
DECL|method|createGroupColumn ()
specifier|private
name|TableColumn
argument_list|<
name|BibEntryTableViewModel
argument_list|,
name|?
argument_list|>
name|createGroupColumn
parameter_list|()
block|{
name|TableColumn
argument_list|<
name|BibEntryTableViewModel
argument_list|,
name|List
argument_list|<
name|AbstractGroup
argument_list|>
argument_list|>
name|column
init|=
operator|new
name|TableColumn
argument_list|<>
argument_list|()
decl_stmt|;
name|column
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
name|GROUP_COLUMN
argument_list|)
expr_stmt|;
name|setExactWidth
argument_list|(
name|column
argument_list|,
literal|20
argument_list|)
expr_stmt|;
name|column
operator|.
name|setCellValueFactory
argument_list|(
name|cellData
lambda|->
name|cellData
operator|.
name|getValue
argument_list|()
operator|.
name|getMatchedGroups
argument_list|(
name|database
argument_list|)
argument_list|)
expr_stmt|;
operator|new
name|ValueTableCellFactory
argument_list|<
name|BibEntryTableViewModel
argument_list|,
name|List
argument_list|<
name|AbstractGroup
argument_list|>
argument_list|>
argument_list|()
operator|.
name|withGraphic
argument_list|(
name|this
operator|::
name|createGroupColorRegion
argument_list|)
operator|.
name|install
argument_list|(
name|column
argument_list|)
expr_stmt|;
return|return
name|column
return|;
block|}
DECL|method|createGroupColorRegion (BibEntryTableViewModel entry, List<AbstractGroup> matchedGroups)
specifier|private
name|Node
name|createGroupColorRegion
parameter_list|(
name|BibEntryTableViewModel
name|entry
parameter_list|,
name|List
argument_list|<
name|AbstractGroup
argument_list|>
name|matchedGroups
parameter_list|)
block|{
name|Rectangle
name|rectangle
init|=
operator|new
name|Rectangle
argument_list|()
decl_stmt|;
name|rectangle
operator|.
name|setWidth
argument_list|(
literal|3
argument_list|)
expr_stmt|;
name|rectangle
operator|.
name|setHeight
argument_list|(
literal|20
argument_list|)
expr_stmt|;
name|Color
name|color
init|=
name|matchedGroups
operator|.
name|stream
argument_list|()
operator|.
name|flatMap
argument_list|(
name|group
lambda|->
name|OptionalUtil
operator|.
name|toStream
argument_list|(
name|group
operator|.
name|getColor
argument_list|()
argument_list|)
argument_list|)
operator|.
name|findFirst
argument_list|()
operator|.
name|orElse
argument_list|(
name|Color
operator|.
name|TRANSPARENT
argument_list|)
decl_stmt|;
name|rectangle
operator|.
name|setFill
argument_list|(
name|color
argument_list|)
expr_stmt|;
name|String
name|matchedGroupsString
init|=
name|matchedGroups
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|AbstractGroup
operator|::
name|getName
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|joining
argument_list|(
literal|", "
argument_list|)
argument_list|)
decl_stmt|;
name|Tooltip
name|tooltip
init|=
operator|new
name|Tooltip
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Entry is contained in the following groups:"
argument_list|)
operator|+
literal|"\n"
operator|+
name|matchedGroupsString
argument_list|)
decl_stmt|;
name|Tooltip
operator|.
name|install
argument_list|(
name|rectangle
argument_list|,
name|tooltip
argument_list|)
expr_stmt|;
name|BorderPane
name|container
init|=
operator|new
name|BorderPane
argument_list|()
decl_stmt|;
name|container
operator|.
name|setLeft
argument_list|(
name|rectangle
argument_list|)
expr_stmt|;
return|return
name|container
return|;
block|}
DECL|method|createNormalColumns ()
specifier|private
name|List
argument_list|<
name|TableColumn
argument_list|<
name|BibEntryTableViewModel
argument_list|,
name|?
argument_list|>
argument_list|>
name|createNormalColumns
parameter_list|()
block|{
name|List
argument_list|<
name|TableColumn
argument_list|<
name|BibEntryTableViewModel
argument_list|,
name|?
argument_list|>
argument_list|>
name|columns
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
comment|// Read table columns from preferences
for|for
control|(
name|String
name|columnName
range|:
name|preferences
operator|.
name|getNormalColumns
argument_list|()
control|)
block|{
comment|// Stored column name will be used as header
comment|// There might be more than one field to display, e.g., "author/editor" or "date/year" - so split
name|String
index|[]
name|fields
init|=
name|columnName
operator|.
name|split
argument_list|(
name|FieldName
operator|.
name|FIELD_SEPARATOR
argument_list|)
decl_stmt|;
name|NormalTableColumn
name|column
init|=
operator|new
name|NormalTableColumn
argument_list|(
name|columnName
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
name|fields
argument_list|)
argument_list|,
name|database
operator|.
name|getDatabase
argument_list|()
argument_list|)
decl_stmt|;
operator|new
name|ValueTableCellFactory
argument_list|<
name|BibEntryTableViewModel
argument_list|,
name|String
argument_list|>
argument_list|()
operator|.
name|withText
argument_list|(
name|text
lambda|->
name|text
argument_list|)
operator|.
name|install
argument_list|(
name|column
argument_list|)
expr_stmt|;
name|column
operator|.
name|setPrefWidth
argument_list|(
name|preferences
operator|.
name|getPrefColumnWidth
argument_list|(
name|columnName
argument_list|)
argument_list|)
expr_stmt|;
name|columns
operator|.
name|add
argument_list|(
name|column
argument_list|)
expr_stmt|;
block|}
return|return
name|columns
return|;
block|}
DECL|method|createSpecialFieldColumn (SpecialField specialField)
specifier|private
name|TableColumn
argument_list|<
name|BibEntryTableViewModel
argument_list|,
name|Optional
argument_list|<
name|SpecialFieldValueViewModel
argument_list|>
argument_list|>
name|createSpecialFieldColumn
parameter_list|(
name|SpecialField
name|specialField
parameter_list|)
block|{
name|TableColumn
argument_list|<
name|BibEntryTableViewModel
argument_list|,
name|Optional
argument_list|<
name|SpecialFieldValueViewModel
argument_list|>
argument_list|>
name|column
init|=
operator|new
name|TableColumn
argument_list|<>
argument_list|()
decl_stmt|;
name|SpecialFieldViewModel
name|specialFieldViewModel
init|=
operator|new
name|SpecialFieldViewModel
argument_list|(
name|specialField
argument_list|,
name|undoManager
argument_list|)
decl_stmt|;
name|column
operator|.
name|setGraphic
argument_list|(
name|specialFieldViewModel
operator|.
name|getIcon
argument_list|()
operator|.
name|getGraphicNode
argument_list|()
argument_list|)
expr_stmt|;
name|column
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
name|ICON_COLUMN
argument_list|)
expr_stmt|;
if|if
condition|(
name|specialField
operator|==
name|SpecialField
operator|.
name|RANKING
condition|)
block|{
name|setExactWidth
argument_list|(
name|column
argument_list|,
name|GUIGlobals
operator|.
name|WIDTH_ICON_COL_RANKING
argument_list|)
expr_stmt|;
operator|new
name|OptionalValueTableCellFactory
argument_list|<
name|BibEntryTableViewModel
argument_list|,
name|SpecialFieldValueViewModel
argument_list|>
argument_list|()
operator|.
name|withGraphicIfPresent
argument_list|(
name|this
operator|::
name|createRating
argument_list|)
operator|.
name|install
argument_list|(
name|column
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|setExactWidth
argument_list|(
name|column
argument_list|,
name|GUIGlobals
operator|.
name|WIDTH_ICON_COL
argument_list|)
expr_stmt|;
if|if
condition|(
name|specialField
operator|.
name|isSingleValueField
argument_list|()
condition|)
block|{
operator|new
name|OptionalValueTableCellFactory
argument_list|<
name|BibEntryTableViewModel
argument_list|,
name|SpecialFieldValueViewModel
argument_list|>
argument_list|()
operator|.
name|withGraphic
argument_list|(
parameter_list|(
name|entry
parameter_list|,
name|value
parameter_list|)
lambda|->
name|createSpecialFieldIcon
argument_list|(
name|value
argument_list|,
name|specialFieldViewModel
argument_list|)
argument_list|)
operator|.
name|withOnMouseClickedEvent
argument_list|(
parameter_list|(
name|entry
parameter_list|,
name|value
parameter_list|)
lambda|->
name|event
lambda|->
block|{
block|if (event.getButton(
argument_list|)
operator|==
name|MouseButton
operator|.
name|PRIMARY
block|)
block|{
name|specialFieldViewModel
operator|.
name|toggle
argument_list|(
name|entry
operator|.
name|getEntry
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|)
function|.install
parameter_list|(
name|column
parameter_list|)
function|;
block|}
end_class

begin_else
else|else
block|{
operator|new
name|OptionalValueTableCellFactory
argument_list|<
name|BibEntryTableViewModel
argument_list|,
name|SpecialFieldValueViewModel
argument_list|>
argument_list|()
operator|.
name|withGraphic
argument_list|(
parameter_list|(
name|entry
parameter_list|,
name|value
parameter_list|)
lambda|->
name|createSpecialFieldIcon
argument_list|(
name|value
argument_list|,
name|specialFieldViewModel
argument_list|)
argument_list|)
operator|.
name|withMenu
argument_list|(
parameter_list|(
name|entry
parameter_list|,
name|value
parameter_list|)
lambda|->
name|createSpecialFieldMenu
argument_list|(
name|entry
operator|.
name|getEntry
argument_list|()
argument_list|,
name|specialFieldViewModel
argument_list|)
argument_list|)
operator|.
name|install
argument_list|(
name|column
argument_list|)
expr_stmt|;
block|}
end_else

begin_expr_stmt
unit|}         column
operator|.
name|setCellValueFactory
argument_list|(
name|cellData
lambda|->
name|cellData
operator|.
name|getValue
argument_list|()
operator|.
name|getSpecialField
argument_list|(
name|specialField
argument_list|)
argument_list|)
expr_stmt|;
end_expr_stmt

begin_return
return|return
name|column
return|;
end_return

begin_function
unit|}      private
DECL|method|createRating (BibEntryTableViewModel entry, SpecialFieldValueViewModel value)
name|Rating
name|createRating
parameter_list|(
name|BibEntryTableViewModel
name|entry
parameter_list|,
name|SpecialFieldValueViewModel
name|value
parameter_list|)
block|{
name|Rating
name|ranking
init|=
operator|new
name|Rating
argument_list|()
decl_stmt|;
name|ranking
operator|.
name|setRating
argument_list|(
name|value
operator|.
name|getValue
argument_list|()
operator|.
name|toRating
argument_list|()
argument_list|)
expr_stmt|;
name|EasyBind
operator|.
name|subscribe
argument_list|(
name|ranking
operator|.
name|ratingProperty
argument_list|()
argument_list|,
name|rating
lambda|->
operator|new
name|SpecialFieldViewModel
argument_list|(
name|SpecialField
operator|.
name|RANKING
argument_list|,
name|undoManager
argument_list|)
operator|.
name|setSpecialFieldValue
argument_list|(
name|entry
operator|.
name|getEntry
argument_list|()
argument_list|,
name|SpecialFieldValue
operator|.
name|getRating
argument_list|(
name|rating
operator|.
name|intValue
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|ranking
return|;
block|}
end_function

begin_function
DECL|method|createSpecialFieldMenu (BibEntry entry, SpecialFieldViewModel specialField)
specifier|private
name|ContextMenu
name|createSpecialFieldMenu
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|SpecialFieldViewModel
name|specialField
parameter_list|)
block|{
name|ContextMenu
name|contextMenu
init|=
operator|new
name|ContextMenu
argument_list|()
decl_stmt|;
for|for
control|(
name|SpecialFieldValueViewModel
name|value
range|:
name|specialField
operator|.
name|getValues
argument_list|()
control|)
block|{
name|MenuItem
name|menuItem
init|=
operator|new
name|MenuItem
argument_list|(
name|value
operator|.
name|getMenuString
argument_list|()
argument_list|,
name|value
operator|.
name|getIcon
argument_list|()
operator|.
name|map
argument_list|(
name|JabRefIcon
operator|::
name|getGraphicNode
argument_list|)
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
argument_list|)
decl_stmt|;
name|menuItem
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|specialField
operator|.
name|setSpecialFieldValue
argument_list|(
name|entry
argument_list|,
name|value
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|contextMenu
operator|.
name|getItems
argument_list|()
operator|.
name|add
argument_list|(
name|menuItem
argument_list|)
expr_stmt|;
block|}
return|return
name|contextMenu
return|;
block|}
end_function

begin_function
DECL|method|createSpecialFieldIcon (Optional<SpecialFieldValueViewModel> fieldValue, SpecialFieldViewModel specialField)
specifier|private
name|Node
name|createSpecialFieldIcon
parameter_list|(
name|Optional
argument_list|<
name|SpecialFieldValueViewModel
argument_list|>
name|fieldValue
parameter_list|,
name|SpecialFieldViewModel
name|specialField
parameter_list|)
block|{
return|return
name|fieldValue
operator|.
name|flatMap
argument_list|(
name|SpecialFieldValueViewModel
operator|::
name|getIcon
argument_list|)
operator|.
name|map
argument_list|(
name|JabRefIcon
operator|::
name|getGraphicNode
argument_list|)
operator|.
name|orElseGet
argument_list|(
parameter_list|()
lambda|->
block|{
name|Node
name|node
init|=
name|specialField
operator|.
name|getEmptyIcon
argument_list|()
operator|.
name|getGraphicNode
argument_list|()
decl_stmt|;
name|node
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"empty-special-field"
argument_list|)
expr_stmt|;
return|return
name|node
return|;
block|}
argument_list|)
return|;
block|}
end_function

begin_function
DECL|method|setExactWidth (TableColumn<?, ?> column, int width)
specifier|private
name|void
name|setExactWidth
parameter_list|(
name|TableColumn
argument_list|<
name|?
argument_list|,
name|?
argument_list|>
name|column
parameter_list|,
name|int
name|width
parameter_list|)
block|{
name|column
operator|.
name|setMinWidth
argument_list|(
name|width
argument_list|)
expr_stmt|;
name|column
operator|.
name|setPrefWidth
argument_list|(
name|width
argument_list|)
expr_stmt|;
name|column
operator|.
name|setMaxWidth
argument_list|(
name|width
argument_list|)
expr_stmt|;
block|}
end_function

begin_function
DECL|method|createFileColumn ()
specifier|private
name|TableColumn
argument_list|<
name|BibEntryTableViewModel
argument_list|,
name|List
argument_list|<
name|LinkedFile
argument_list|>
argument_list|>
name|createFileColumn
parameter_list|()
block|{
name|TableColumn
argument_list|<
name|BibEntryTableViewModel
argument_list|,
name|List
argument_list|<
name|LinkedFile
argument_list|>
argument_list|>
name|column
init|=
operator|new
name|TableColumn
argument_list|<>
argument_list|()
decl_stmt|;
name|column
operator|.
name|setGraphic
argument_list|(
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|FILE
operator|.
name|getGraphicNode
argument_list|()
argument_list|)
expr_stmt|;
name|column
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
name|ICON_COLUMN
argument_list|)
expr_stmt|;
name|setExactWidth
argument_list|(
name|column
argument_list|,
name|GUIGlobals
operator|.
name|WIDTH_ICON_COL
argument_list|)
expr_stmt|;
name|column
operator|.
name|setCellValueFactory
argument_list|(
name|cellData
lambda|->
name|cellData
operator|.
name|getValue
argument_list|()
operator|.
name|getLinkedFiles
argument_list|()
argument_list|)
expr_stmt|;
operator|new
name|ValueTableCellFactory
argument_list|<
name|BibEntryTableViewModel
argument_list|,
name|List
argument_list|<
name|LinkedFile
argument_list|>
argument_list|>
argument_list|()
operator|.
name|withGraphic
argument_list|(
name|this
operator|::
name|createFileIcon
argument_list|)
operator|.
name|withMenu
argument_list|(
name|this
operator|::
name|createFileMenu
argument_list|)
operator|.
name|withOnMouseClickedEvent
argument_list|(
parameter_list|(
name|entry
parameter_list|,
name|linkedFiles
parameter_list|)
lambda|->
name|event
lambda|->
block|{
block|if (event.getButton(
argument_list|)
operator|==
name|MouseButton
operator|.
name|PRIMARY
operator|&&
name|linkedFiles
operator|.
name|size
argument_list|()
operator|==
literal|1
block|)
block|{
comment|// Only one linked file -> open directly
name|LinkedFileViewModel
name|linkedFileViewModel
init|=
operator|new
name|LinkedFileViewModel
argument_list|(
name|linkedFiles
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|,
name|entry
operator|.
name|getEntry
argument_list|()
argument_list|,
name|database
argument_list|,
name|dialogService
argument_list|,
name|Globals
operator|.
name|TASK_EXECUTOR
argument_list|)
decl_stmt|;
name|linkedFileViewModel
operator|.
name|open
argument_list|()
expr_stmt|;
block|}
end_function

begin_expr_stmt
unit|})
operator|.
name|install
argument_list|(
name|column
argument_list|)
expr_stmt|;
end_expr_stmt

begin_return
return|return
name|column
return|;
end_return

begin_function
unit|}      private
DECL|method|createFileMenu (BibEntryTableViewModel entry, List<LinkedFile> linkedFiles)
name|ContextMenu
name|createFileMenu
parameter_list|(
name|BibEntryTableViewModel
name|entry
parameter_list|,
name|List
argument_list|<
name|LinkedFile
argument_list|>
name|linkedFiles
parameter_list|)
block|{
if|if
condition|(
name|linkedFiles
operator|.
name|size
argument_list|()
operator|<=
literal|1
condition|)
block|{
return|return
literal|null
return|;
block|}
name|ContextMenu
name|contextMenu
init|=
operator|new
name|ContextMenu
argument_list|()
decl_stmt|;
for|for
control|(
name|LinkedFile
name|linkedFile
range|:
name|linkedFiles
control|)
block|{
name|LinkedFileViewModel
name|linkedFileViewModel
init|=
operator|new
name|LinkedFileViewModel
argument_list|(
name|linkedFile
argument_list|,
name|entry
operator|.
name|getEntry
argument_list|()
argument_list|,
name|database
argument_list|,
name|dialogService
argument_list|,
name|Globals
operator|.
name|TASK_EXECUTOR
argument_list|)
decl_stmt|;
name|MenuItem
name|menuItem
init|=
operator|new
name|MenuItem
argument_list|(
name|linkedFileViewModel
operator|.
name|getDescriptionAndLink
argument_list|()
argument_list|,
name|linkedFileViewModel
operator|.
name|getTypeIcon
argument_list|()
operator|.
name|getGraphicNode
argument_list|()
argument_list|)
decl_stmt|;
name|menuItem
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|linkedFileViewModel
operator|.
name|open
argument_list|()
argument_list|)
expr_stmt|;
name|contextMenu
operator|.
name|getItems
argument_list|()
operator|.
name|add
argument_list|(
name|menuItem
argument_list|)
expr_stmt|;
block|}
return|return
name|contextMenu
return|;
block|}
end_function

begin_comment
comment|/**      * Creates a column which shows an icon instead of the textual content      */
end_comment

begin_function
DECL|method|createIconColumn (JabRefIcon icon, String firstField, String secondField)
specifier|private
name|TableColumn
argument_list|<
name|BibEntryTableViewModel
argument_list|,
name|String
argument_list|>
name|createIconColumn
parameter_list|(
name|JabRefIcon
name|icon
parameter_list|,
name|String
name|firstField
parameter_list|,
name|String
name|secondField
parameter_list|)
block|{
name|TableColumn
argument_list|<
name|BibEntryTableViewModel
argument_list|,
name|String
argument_list|>
name|column
init|=
operator|new
name|TableColumn
argument_list|<>
argument_list|()
decl_stmt|;
name|column
operator|.
name|setGraphic
argument_list|(
name|icon
operator|.
name|getGraphicNode
argument_list|()
argument_list|)
expr_stmt|;
name|column
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
name|ICON_COLUMN
argument_list|)
expr_stmt|;
name|setExactWidth
argument_list|(
name|column
argument_list|,
name|GUIGlobals
operator|.
name|WIDTH_ICON_COL
argument_list|)
expr_stmt|;
name|column
operator|.
name|setCellValueFactory
argument_list|(
name|cellData
lambda|->
name|EasyBind
operator|.
name|monadic
argument_list|(
name|cellData
operator|.
name|getValue
argument_list|()
operator|.
name|getField
argument_list|(
name|firstField
argument_list|)
argument_list|)
operator|.
name|orElse
argument_list|(
name|cellData
operator|.
name|getValue
argument_list|()
operator|.
name|getField
argument_list|(
name|secondField
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
operator|new
name|ValueTableCellFactory
argument_list|<
name|BibEntryTableViewModel
argument_list|,
name|String
argument_list|>
argument_list|()
operator|.
name|withGraphic
argument_list|(
name|cellFactory
operator|::
name|getTableIcon
argument_list|)
operator|.
name|install
argument_list|(
name|column
argument_list|)
expr_stmt|;
return|return
name|column
return|;
block|}
end_function

begin_function
DECL|method|createIconColumn (JabRefIcon icon, String field)
specifier|private
name|TableColumn
argument_list|<
name|BibEntryTableViewModel
argument_list|,
name|String
argument_list|>
name|createIconColumn
parameter_list|(
name|JabRefIcon
name|icon
parameter_list|,
name|String
name|field
parameter_list|)
block|{
name|TableColumn
argument_list|<
name|BibEntryTableViewModel
argument_list|,
name|String
argument_list|>
name|column
init|=
operator|new
name|TableColumn
argument_list|<>
argument_list|()
decl_stmt|;
name|column
operator|.
name|setGraphic
argument_list|(
name|icon
operator|.
name|getGraphicNode
argument_list|()
argument_list|)
expr_stmt|;
name|column
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
name|ICON_COLUMN
argument_list|)
expr_stmt|;
name|setExactWidth
argument_list|(
name|column
argument_list|,
name|GUIGlobals
operator|.
name|WIDTH_ICON_COL
argument_list|)
expr_stmt|;
name|column
operator|.
name|setCellValueFactory
argument_list|(
name|cellData
lambda|->
name|cellData
operator|.
name|getValue
argument_list|()
operator|.
name|getField
argument_list|(
name|field
argument_list|)
argument_list|)
expr_stmt|;
operator|new
name|ValueTableCellFactory
argument_list|<
name|BibEntryTableViewModel
argument_list|,
name|String
argument_list|>
argument_list|()
operator|.
name|withGraphic
argument_list|(
name|cellFactory
operator|::
name|getTableIcon
argument_list|)
operator|.
name|install
argument_list|(
name|column
argument_list|)
expr_stmt|;
return|return
name|column
return|;
block|}
end_function

begin_comment
comment|/**      * Creates a column for specific file types. Shows the icon for the given type (or the FILE_MULTIPLE icon)      *      * @param externalFileTypeName the name of the externalFileType      */
end_comment

begin_function
DECL|method|createExtraFileColumn (String externalFileTypeName)
specifier|private
name|TableColumn
argument_list|<
name|BibEntryTableViewModel
argument_list|,
name|List
argument_list|<
name|LinkedFile
argument_list|>
argument_list|>
name|createExtraFileColumn
parameter_list|(
name|String
name|externalFileTypeName
parameter_list|)
block|{
name|TableColumn
argument_list|<
name|BibEntryTableViewModel
argument_list|,
name|List
argument_list|<
name|LinkedFile
argument_list|>
argument_list|>
name|column
init|=
operator|new
name|TableColumn
argument_list|<>
argument_list|()
decl_stmt|;
name|column
operator|.
name|setGraphic
argument_list|(
name|externalFileTypes
operator|.
name|getExternalFileTypeByName
argument_list|(
name|externalFileTypeName
argument_list|)
operator|.
name|map
argument_list|(
name|ExternalFileType
operator|::
name|getIcon
argument_list|)
operator|.
name|orElse
argument_list|(
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|FILE
argument_list|)
operator|.
name|getGraphicNode
argument_list|()
argument_list|)
expr_stmt|;
name|column
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
name|ICON_COLUMN
argument_list|)
expr_stmt|;
name|setExactWidth
argument_list|(
name|column
argument_list|,
name|GUIGlobals
operator|.
name|WIDTH_ICON_COL
argument_list|)
expr_stmt|;
name|column
operator|.
name|setCellValueFactory
argument_list|(
name|cellData
lambda|->
name|cellData
operator|.
name|getValue
argument_list|()
operator|.
name|getLinkedFiles
argument_list|()
argument_list|)
expr_stmt|;
operator|new
name|ValueTableCellFactory
argument_list|<
name|BibEntryTableViewModel
argument_list|,
name|List
argument_list|<
name|LinkedFile
argument_list|>
argument_list|>
argument_list|()
operator|.
name|withGraphic
argument_list|(
name|linkedFiles
lambda|->
name|createFileIcon
argument_list|(
name|linkedFiles
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|linkedFile
lambda|->
name|linkedFile
operator|.
name|getFileType
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
name|externalFileTypeName
argument_list|)
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
argument_list|)
operator|.
name|install
argument_list|(
name|column
argument_list|)
expr_stmt|;
return|return
name|column
return|;
block|}
end_function

begin_function
DECL|method|createFileIcon (List<LinkedFile> linkedFiles)
specifier|private
name|Node
name|createFileIcon
parameter_list|(
name|List
argument_list|<
name|LinkedFile
argument_list|>
name|linkedFiles
parameter_list|)
block|{
if|if
condition|(
name|linkedFiles
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|)
block|{
return|return
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|FILE_MULTIPLE
operator|.
name|getGraphicNode
argument_list|()
return|;
block|}
elseif|else
if|if
condition|(
name|linkedFiles
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
return|return
name|externalFileTypes
operator|.
name|fromLinkedFile
argument_list|(
name|linkedFiles
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|,
literal|false
argument_list|)
operator|.
name|map
argument_list|(
name|ExternalFileType
operator|::
name|getIcon
argument_list|)
operator|.
name|orElse
argument_list|(
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|FILE
argument_list|)
operator|.
name|getGraphicNode
argument_list|()
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
end_function

unit|}
end_unit

