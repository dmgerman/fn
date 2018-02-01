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
name|Locale
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
name|javafx
operator|.
name|beans
operator|.
name|binding
operator|.
name|Bindings
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|binding
operator|.
name|BooleanBinding
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|SimpleBooleanProperty
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|SimpleIntegerProperty
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|collections
operator|.
name|FXCollections
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|collections
operator|.
name|ObservableList
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
name|Dragboard
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
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|DragAndDropDataFormats
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
name|InternalMaterialDesignIcon
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
name|StateManager
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
name|BackgroundTask
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
name|BindingsHelper
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
name|TaskExecutor
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
name|groups
operator|.
name|DefaultGroupsFactory
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
name|FieldChange
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
name|event
operator|.
name|EntryEvent
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
name|groups
operator|.
name|AutomaticGroup
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
name|GroupEntryChanger
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
name|strings
operator|.
name|StringUtil
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|common
operator|.
name|base
operator|.
name|Enums
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|common
operator|.
name|eventbus
operator|.
name|Subscribe
import|;
end_import

begin_import
import|import
name|de
operator|.
name|jensd
operator|.
name|fx
operator|.
name|glyphs
operator|.
name|materialdesignicons
operator|.
name|MaterialDesignIcon
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
DECL|class|GroupNodeViewModel
specifier|public
class|class
name|GroupNodeViewModel
block|{
DECL|field|displayName
specifier|private
specifier|final
name|String
name|displayName
decl_stmt|;
DECL|field|isRoot
specifier|private
specifier|final
name|boolean
name|isRoot
decl_stmt|;
DECL|field|children
specifier|private
specifier|final
name|ObservableList
argument_list|<
name|GroupNodeViewModel
argument_list|>
name|children
decl_stmt|;
DECL|field|databaseContext
specifier|private
specifier|final
name|BibDatabaseContext
name|databaseContext
decl_stmt|;
DECL|field|stateManager
specifier|private
specifier|final
name|StateManager
name|stateManager
decl_stmt|;
DECL|field|groupNode
specifier|private
specifier|final
name|GroupTreeNode
name|groupNode
decl_stmt|;
DECL|field|hits
specifier|private
specifier|final
name|SimpleIntegerProperty
name|hits
decl_stmt|;
DECL|field|hasChildren
specifier|private
specifier|final
name|SimpleBooleanProperty
name|hasChildren
decl_stmt|;
DECL|field|expandedProperty
specifier|private
specifier|final
name|SimpleBooleanProperty
name|expandedProperty
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|anySelectedEntriesMatched
specifier|private
specifier|final
name|BooleanBinding
name|anySelectedEntriesMatched
decl_stmt|;
DECL|field|allSelectedEntriesMatched
specifier|private
specifier|final
name|BooleanBinding
name|allSelectedEntriesMatched
decl_stmt|;
DECL|field|taskExecutor
specifier|private
specifier|final
name|TaskExecutor
name|taskExecutor
decl_stmt|;
DECL|method|GroupNodeViewModel (BibDatabaseContext databaseContext, StateManager stateManager, TaskExecutor taskExecutor, GroupTreeNode groupNode)
specifier|public
name|GroupNodeViewModel
parameter_list|(
name|BibDatabaseContext
name|databaseContext
parameter_list|,
name|StateManager
name|stateManager
parameter_list|,
name|TaskExecutor
name|taskExecutor
parameter_list|,
name|GroupTreeNode
name|groupNode
parameter_list|)
block|{
name|this
operator|.
name|databaseContext
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|databaseContext
argument_list|)
expr_stmt|;
name|this
operator|.
name|taskExecutor
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|taskExecutor
argument_list|)
expr_stmt|;
name|this
operator|.
name|stateManager
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|stateManager
argument_list|)
expr_stmt|;
name|this
operator|.
name|groupNode
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|groupNode
argument_list|)
expr_stmt|;
name|LatexToUnicodeFormatter
name|formatter
init|=
operator|new
name|LatexToUnicodeFormatter
argument_list|()
decl_stmt|;
name|displayName
operator|=
name|formatter
operator|.
name|format
argument_list|(
name|groupNode
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|isRoot
operator|=
name|groupNode
operator|.
name|isRoot
argument_list|()
expr_stmt|;
if|if
condition|(
name|groupNode
operator|.
name|getGroup
argument_list|()
operator|instanceof
name|AutomaticGroup
condition|)
block|{
name|AutomaticGroup
name|automaticGroup
init|=
operator|(
name|AutomaticGroup
operator|)
name|groupNode
operator|.
name|getGroup
argument_list|()
decl_stmt|;
name|children
operator|=
name|automaticGroup
operator|.
name|createSubgroups
argument_list|(
name|databaseContext
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
argument_list|)
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|this
operator|::
name|toViewModel
argument_list|)
operator|.
name|sorted
argument_list|(
parameter_list|(
name|group1
parameter_list|,
name|group2
parameter_list|)
lambda|->
name|group1
operator|.
name|getDisplayName
argument_list|()
operator|.
name|compareToIgnoreCase
argument_list|(
name|group2
operator|.
name|getDisplayName
argument_list|()
argument_list|)
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toCollection
argument_list|(
name|FXCollections
operator|::
name|observableArrayList
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|children
operator|=
name|BindingsHelper
operator|.
name|mapBacked
argument_list|(
name|groupNode
operator|.
name|getChildren
argument_list|()
argument_list|,
name|this
operator|::
name|toViewModel
argument_list|)
expr_stmt|;
block|}
name|hasChildren
operator|=
operator|new
name|SimpleBooleanProperty
argument_list|()
expr_stmt|;
name|hasChildren
operator|.
name|bind
argument_list|(
name|Bindings
operator|.
name|isNotEmpty
argument_list|(
name|children
argument_list|)
argument_list|)
expr_stmt|;
name|hits
operator|=
operator|new
name|SimpleIntegerProperty
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|calculateNumberOfMatches
argument_list|()
expr_stmt|;
name|expandedProperty
operator|.
name|set
argument_list|(
name|groupNode
operator|.
name|getGroup
argument_list|()
operator|.
name|isExpanded
argument_list|()
argument_list|)
expr_stmt|;
name|expandedProperty
operator|.
name|addListener
argument_list|(
parameter_list|(
name|observable
parameter_list|,
name|oldValue
parameter_list|,
name|newValue
parameter_list|)
lambda|->
name|groupNode
operator|.
name|getGroup
argument_list|()
operator|.
name|setExpanded
argument_list|(
name|newValue
argument_list|)
argument_list|)
expr_stmt|;
comment|// Register listener
name|databaseContext
operator|.
name|getDatabase
argument_list|()
operator|.
name|registerListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|ObservableList
argument_list|<
name|Boolean
argument_list|>
name|selectedEntriesMatchStatus
init|=
name|EasyBind
operator|.
name|map
argument_list|(
name|stateManager
operator|.
name|getSelectedEntries
argument_list|()
argument_list|,
name|groupNode
operator|::
name|matches
argument_list|)
decl_stmt|;
name|anySelectedEntriesMatched
operator|=
name|BindingsHelper
operator|.
name|any
argument_list|(
name|selectedEntriesMatchStatus
argument_list|,
name|matched
lambda|->
name|matched
argument_list|)
expr_stmt|;
name|allSelectedEntriesMatched
operator|=
name|BindingsHelper
operator|.
name|all
argument_list|(
name|selectedEntriesMatchStatus
argument_list|,
name|matched
lambda|->
name|matched
argument_list|)
expr_stmt|;
block|}
DECL|method|GroupNodeViewModel (BibDatabaseContext databaseContext, StateManager stateManager, TaskExecutor taskExecutor, AbstractGroup group)
specifier|public
name|GroupNodeViewModel
parameter_list|(
name|BibDatabaseContext
name|databaseContext
parameter_list|,
name|StateManager
name|stateManager
parameter_list|,
name|TaskExecutor
name|taskExecutor
parameter_list|,
name|AbstractGroup
name|group
parameter_list|)
block|{
name|this
argument_list|(
name|databaseContext
argument_list|,
name|stateManager
argument_list|,
name|taskExecutor
argument_list|,
operator|new
name|GroupTreeNode
argument_list|(
name|group
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|getAllEntriesGroup (BibDatabaseContext newDatabase, StateManager stateManager, TaskExecutor taskExecutor)
specifier|static
name|GroupNodeViewModel
name|getAllEntriesGroup
parameter_list|(
name|BibDatabaseContext
name|newDatabase
parameter_list|,
name|StateManager
name|stateManager
parameter_list|,
name|TaskExecutor
name|taskExecutor
parameter_list|)
block|{
return|return
operator|new
name|GroupNodeViewModel
argument_list|(
name|newDatabase
argument_list|,
name|stateManager
argument_list|,
name|taskExecutor
argument_list|,
name|DefaultGroupsFactory
operator|.
name|getAllEntriesGroup
argument_list|()
argument_list|)
return|;
block|}
DECL|method|toViewModel (GroupTreeNode child)
specifier|private
name|GroupNodeViewModel
name|toViewModel
parameter_list|(
name|GroupTreeNode
name|child
parameter_list|)
block|{
return|return
operator|new
name|GroupNodeViewModel
argument_list|(
name|databaseContext
argument_list|,
name|stateManager
argument_list|,
name|taskExecutor
argument_list|,
name|child
argument_list|)
return|;
block|}
DECL|method|addEntriesToGroup (List<BibEntry> entries)
specifier|public
name|List
argument_list|<
name|FieldChange
argument_list|>
name|addEntriesToGroup
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|)
block|{
comment|// TODO: warn if assignment has undesired side effects (modifies a field != keywords)
comment|//if (!WarnAssignmentSideEffects.warnAssignmentSideEffects(group, groupSelector.frame))
comment|//{
comment|//    return; // user aborted operation
comment|//}
return|return
name|groupNode
operator|.
name|addEntriesToGroup
argument_list|(
name|entries
argument_list|)
return|;
comment|// TODO: Store undo
comment|// if (!undo.isEmpty()) {
comment|// groupSelector.concludeAssignment(UndoableChangeEntriesOfGroup.getUndoableEdit(target, undo), target.getNode(), assignedEntries);
block|}
DECL|method|expandedProperty ()
specifier|public
name|SimpleBooleanProperty
name|expandedProperty
parameter_list|()
block|{
return|return
name|expandedProperty
return|;
block|}
DECL|method|anySelectedEntriesMatchedProperty ()
specifier|public
name|BooleanBinding
name|anySelectedEntriesMatchedProperty
parameter_list|()
block|{
return|return
name|anySelectedEntriesMatched
return|;
block|}
DECL|method|allSelectedEntriesMatchedProperty ()
specifier|public
name|BooleanBinding
name|allSelectedEntriesMatchedProperty
parameter_list|()
block|{
return|return
name|allSelectedEntriesMatched
return|;
block|}
DECL|method|hasChildrenProperty ()
specifier|public
name|SimpleBooleanProperty
name|hasChildrenProperty
parameter_list|()
block|{
return|return
name|hasChildren
return|;
block|}
DECL|method|getDisplayName ()
specifier|public
name|String
name|getDisplayName
parameter_list|()
block|{
return|return
name|displayName
return|;
block|}
DECL|method|isRoot ()
specifier|public
name|boolean
name|isRoot
parameter_list|()
block|{
return|return
name|isRoot
return|;
block|}
DECL|method|getDescription ()
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|groupNode
operator|.
name|getGroup
argument_list|()
operator|.
name|getDescription
argument_list|()
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
return|;
block|}
DECL|method|getHits ()
specifier|public
name|SimpleIntegerProperty
name|getHits
parameter_list|()
block|{
return|return
name|hits
return|;
block|}
annotation|@
name|Override
DECL|method|equals (Object o)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|this
operator|==
name|o
condition|)
block|{
return|return
literal|true
return|;
block|}
if|if
condition|(
operator|(
name|o
operator|==
literal|null
operator|)
operator|||
operator|(
name|getClass
argument_list|()
operator|!=
name|o
operator|.
name|getClass
argument_list|()
operator|)
condition|)
block|{
return|return
literal|false
return|;
block|}
name|GroupNodeViewModel
name|that
init|=
operator|(
name|GroupNodeViewModel
operator|)
name|o
decl_stmt|;
return|return
name|groupNode
operator|.
name|equals
argument_list|(
name|that
operator|.
name|groupNode
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"GroupNodeViewModel{"
operator|+
literal|"displayName='"
operator|+
name|displayName
operator|+
literal|'\''
operator|+
literal|", isRoot="
operator|+
name|isRoot
operator|+
literal|", icon='"
operator|+
name|getIcon
argument_list|()
operator|+
literal|'\''
operator|+
literal|", children="
operator|+
name|children
operator|+
literal|", databaseContext="
operator|+
name|databaseContext
operator|+
literal|", groupNode="
operator|+
name|groupNode
operator|+
literal|", hits="
operator|+
name|hits
operator|+
literal|'}'
return|;
block|}
annotation|@
name|Override
DECL|method|hashCode ()
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|groupNode
operator|.
name|hashCode
argument_list|()
return|;
block|}
DECL|method|getIcon ()
specifier|public
name|JabRefIcon
name|getIcon
parameter_list|()
block|{
name|Optional
argument_list|<
name|String
argument_list|>
name|iconName
init|=
name|groupNode
operator|.
name|getGroup
argument_list|()
operator|.
name|getIconName
argument_list|()
decl_stmt|;
return|return
name|iconName
operator|.
name|flatMap
argument_list|(
name|this
operator|::
name|parseIcon
argument_list|)
operator|.
name|orElse
argument_list|(
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|DEFAULT_GROUP_ICON
argument_list|)
return|;
block|}
DECL|method|parseIcon (String iconCode)
specifier|private
name|Optional
argument_list|<
name|JabRefIcon
argument_list|>
name|parseIcon
parameter_list|(
name|String
name|iconCode
parameter_list|)
block|{
return|return
name|Enums
operator|.
name|getIfPresent
argument_list|(
name|MaterialDesignIcon
operator|.
name|class
argument_list|,
name|iconCode
operator|.
name|toUpperCase
argument_list|(
name|Locale
operator|.
name|ENGLISH
argument_list|)
argument_list|)
operator|.
name|toJavaUtil
argument_list|()
operator|.
name|map
argument_list|(
name|icon
lambda|->
operator|new
name|InternalMaterialDesignIcon
argument_list|(
name|getColor
argument_list|()
argument_list|,
name|icon
argument_list|)
argument_list|)
return|;
block|}
DECL|method|getChildren ()
specifier|public
name|ObservableList
argument_list|<
name|GroupNodeViewModel
argument_list|>
name|getChildren
parameter_list|()
block|{
return|return
name|children
return|;
block|}
DECL|method|getGroupNode ()
specifier|public
name|GroupTreeNode
name|getGroupNode
parameter_list|()
block|{
return|return
name|groupNode
return|;
block|}
comment|/**     * Gets invoked if an entry in the current database changes.     */
annotation|@
name|Subscribe
DECL|method|listen (@uppressWarningsR) EntryEvent entryEvent)
specifier|public
name|void
name|listen
parameter_list|(
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unused"
argument_list|)
name|EntryEvent
name|entryEvent
parameter_list|)
block|{
name|calculateNumberOfMatches
argument_list|()
expr_stmt|;
block|}
DECL|method|calculateNumberOfMatches ()
specifier|private
name|void
name|calculateNumberOfMatches
parameter_list|()
block|{
comment|// We calculate the new hit value
comment|// We could be more intelligent and try to figure out the new number of hits based on the entry change
comment|// for example, a previously matched entry gets removed -> hits = hits - 1
name|BackgroundTask
operator|.
name|wrap
argument_list|(
parameter_list|()
lambda|->
name|groupNode
operator|.
name|calculateNumberOfMatches
argument_list|(
name|databaseContext
operator|.
name|getDatabase
argument_list|()
argument_list|)
argument_list|)
operator|.
name|onSuccess
argument_list|(
name|hits
operator|::
name|setValue
argument_list|)
operator|.
name|executeWith
argument_list|(
name|taskExecutor
argument_list|)
expr_stmt|;
block|}
DECL|method|addSubgroup (AbstractGroup subgroup)
specifier|public
name|GroupTreeNode
name|addSubgroup
parameter_list|(
name|AbstractGroup
name|subgroup
parameter_list|)
block|{
return|return
name|groupNode
operator|.
name|addSubgroup
argument_list|(
name|subgroup
argument_list|)
return|;
block|}
DECL|method|toggleExpansion ()
name|void
name|toggleExpansion
parameter_list|()
block|{
name|expandedProperty
argument_list|()
operator|.
name|set
argument_list|(
operator|!
name|expandedProperty
argument_list|()
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|isMatchedBy (String searchString)
name|boolean
name|isMatchedBy
parameter_list|(
name|String
name|searchString
parameter_list|)
block|{
return|return
name|StringUtil
operator|.
name|isBlank
argument_list|(
name|searchString
argument_list|)
operator|||
name|StringUtil
operator|.
name|containsIgnoreCase
argument_list|(
name|getDisplayName
argument_list|()
argument_list|,
name|searchString
argument_list|)
return|;
block|}
DECL|method|getColor ()
specifier|public
name|Color
name|getColor
parameter_list|()
block|{
return|return
name|groupNode
operator|.
name|getGroup
argument_list|()
operator|.
name|getColor
argument_list|()
operator|.
name|orElse
argument_list|(
name|IconTheme
operator|.
name|getDefaultColor
argument_list|()
argument_list|)
return|;
block|}
DECL|method|getPath ()
specifier|public
name|String
name|getPath
parameter_list|()
block|{
return|return
name|groupNode
operator|.
name|getPath
argument_list|()
return|;
block|}
DECL|method|getChildByPath (String pathToSource)
specifier|public
name|Optional
argument_list|<
name|GroupNodeViewModel
argument_list|>
name|getChildByPath
parameter_list|(
name|String
name|pathToSource
parameter_list|)
block|{
return|return
name|groupNode
operator|.
name|getChildByPath
argument_list|(
name|pathToSource
argument_list|)
operator|.
name|map
argument_list|(
name|this
operator|::
name|toViewModel
argument_list|)
return|;
block|}
comment|/**      * Decides if the content stored in the given {@link Dragboard} can be droped on the given target row.      * Currently, the following sources are allowed:      *  - another group (will be added as subgroup on drop)      *  - entries if the group implements {@link GroupEntryChanger} (will be assigned to group on drop)      */
DECL|method|acceptableDrop (Dragboard dragboard)
specifier|public
name|boolean
name|acceptableDrop
parameter_list|(
name|Dragboard
name|dragboard
parameter_list|)
block|{
comment|// TODO: we should also check isNodeDescendant
name|boolean
name|canDropOtherGroup
init|=
name|dragboard
operator|.
name|hasContent
argument_list|(
name|DragAndDropDataFormats
operator|.
name|GROUP
argument_list|)
decl_stmt|;
name|boolean
name|canDropEntries
init|=
name|dragboard
operator|.
name|hasContent
argument_list|(
name|DragAndDropDataFormats
operator|.
name|ENTRIES
argument_list|)
operator|&&
operator|(
name|groupNode
operator|.
name|getGroup
argument_list|()
operator|instanceof
name|GroupEntryChanger
operator|)
decl_stmt|;
return|return
name|canDropOtherGroup
operator|||
name|canDropEntries
return|;
block|}
DECL|method|moveTo (GroupNodeViewModel target)
specifier|public
name|void
name|moveTo
parameter_list|(
name|GroupNodeViewModel
name|target
parameter_list|)
block|{
comment|// TODO: Add undo and display message
comment|//MoveGroupChange undo = new MoveGroupChange(((GroupTreeNodeViewModel)source.getParent()).getNode(),
comment|//        source.getNode().getPositionInParent(), target.getNode(), target.getChildCount());
name|getGroupNode
argument_list|()
operator|.
name|moveTo
argument_list|(
name|target
operator|.
name|getGroupNode
argument_list|()
argument_list|)
expr_stmt|;
comment|//panel.getUndoManager().addEdit(new UndoableMoveGroup(this.groupsRoot, moveChange));
comment|//panel.markBaseChanged();
comment|//frame.output(Localization.lang("Moved group \"%0\".", node.getNode().getGroup().getName()));
block|}
DECL|method|moveTo (GroupTreeNode target, int targetIndex)
specifier|public
name|void
name|moveTo
parameter_list|(
name|GroupTreeNode
name|target
parameter_list|,
name|int
name|targetIndex
parameter_list|)
block|{
name|getGroupNode
argument_list|()
operator|.
name|moveTo
argument_list|(
name|target
argument_list|,
name|targetIndex
argument_list|)
expr_stmt|;
block|}
DECL|method|getParent ()
specifier|public
name|Optional
argument_list|<
name|GroupTreeNode
argument_list|>
name|getParent
parameter_list|()
block|{
return|return
name|groupNode
operator|.
name|getParent
argument_list|()
return|;
block|}
DECL|method|draggedOn (GroupNodeViewModel target, DroppingMouseLocation mouseLocation)
specifier|public
name|void
name|draggedOn
parameter_list|(
name|GroupNodeViewModel
name|target
parameter_list|,
name|DroppingMouseLocation
name|mouseLocation
parameter_list|)
block|{
name|Optional
argument_list|<
name|GroupTreeNode
argument_list|>
name|targetParent
init|=
name|target
operator|.
name|getParent
argument_list|()
decl_stmt|;
if|if
condition|(
name|targetParent
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|int
name|targetIndex
init|=
name|target
operator|.
name|getPositionInParent
argument_list|()
decl_stmt|;
comment|// In case we want to move an item in the same parent
comment|// and the item is moved down, we need to adjust the target index
if|if
condition|(
name|targetParent
operator|.
name|equals
argument_list|(
name|getParent
argument_list|()
argument_list|)
condition|)
block|{
name|int
name|sourceIndex
init|=
name|this
operator|.
name|getPositionInParent
argument_list|()
decl_stmt|;
if|if
condition|(
name|sourceIndex
operator|<
name|targetIndex
condition|)
block|{
name|targetIndex
operator|--
expr_stmt|;
block|}
block|}
comment|// Different actions depending on where the user releases the drop in the target row
comment|// Bottom + top -> insert source row before / after this row
comment|// Center -> add as child
switch|switch
condition|(
name|mouseLocation
condition|)
block|{
case|case
name|BOTTOM
case|:
name|this
operator|.
name|moveTo
argument_list|(
name|targetParent
operator|.
name|get
argument_list|()
argument_list|,
name|targetIndex
operator|+
literal|1
argument_list|)
expr_stmt|;
break|break;
case|case
name|CENTER
case|:
name|this
operator|.
name|moveTo
argument_list|(
name|target
argument_list|)
expr_stmt|;
break|break;
case|case
name|TOP
case|:
name|this
operator|.
name|moveTo
argument_list|(
name|targetParent
operator|.
name|get
argument_list|()
argument_list|,
name|targetIndex
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
else|else
block|{
comment|// No parent = root -> just add
name|this
operator|.
name|moveTo
argument_list|(
name|target
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|getPositionInParent ()
specifier|private
name|int
name|getPositionInParent
parameter_list|()
block|{
return|return
name|groupNode
operator|.
name|getPositionInParent
argument_list|()
return|;
block|}
block|}
end_class

end_unit

