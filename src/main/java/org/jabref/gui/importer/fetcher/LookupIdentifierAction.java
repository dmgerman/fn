begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.importer.fetcher
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|importer
operator|.
name|fetcher
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
name|Optional
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
name|actions
operator|.
name|Action
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
name|actions
operator|.
name|SimpleCommand
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
name|icon
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
name|gui
operator|.
name|undo
operator|.
name|UndoableFieldChange
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
name|importer
operator|.
name|FetcherException
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
name|importer
operator|.
name|IdFetcher
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
name|identifier
operator|.
name|Identifier
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|actions
operator|.
name|ActionHelper
operator|.
name|needsDatabase
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|actions
operator|.
name|ActionHelper
operator|.
name|needsEntriesSelected
import|;
end_import

begin_class
DECL|class|LookupIdentifierAction
specifier|public
class|class
name|LookupIdentifierAction
parameter_list|<
name|T
extends|extends
name|Identifier
parameter_list|>
extends|extends
name|SimpleCommand
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Logger
name|LOGGER
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|LookupIdentifierAction
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|fetcher
specifier|private
specifier|final
name|IdFetcher
argument_list|<
name|T
argument_list|>
name|fetcher
decl_stmt|;
DECL|field|stateManager
specifier|private
specifier|final
name|StateManager
name|stateManager
decl_stmt|;
DECL|field|undoManager
specifier|private
name|UndoManager
name|undoManager
decl_stmt|;
DECL|method|LookupIdentifierAction (JabRefFrame frame, IdFetcher<T> fetcher, StateManager stateManager, UndoManager undoManager)
specifier|public
name|LookupIdentifierAction
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|IdFetcher
argument_list|<
name|T
argument_list|>
name|fetcher
parameter_list|,
name|StateManager
name|stateManager
parameter_list|,
name|UndoManager
name|undoManager
parameter_list|)
block|{
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|this
operator|.
name|fetcher
operator|=
name|fetcher
expr_stmt|;
name|this
operator|.
name|stateManager
operator|=
name|stateManager
expr_stmt|;
name|this
operator|.
name|undoManager
operator|=
name|undoManager
expr_stmt|;
name|this
operator|.
name|executable
operator|.
name|bind
argument_list|(
name|needsDatabase
argument_list|(
name|this
operator|.
name|stateManager
argument_list|)
operator|.
name|and
argument_list|(
name|needsEntriesSelected
argument_list|(
name|this
operator|.
name|stateManager
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|statusMessage
operator|.
name|bind
argument_list|(
name|BindingsHelper
operator|.
name|ifThenElse
argument_list|(
name|executable
argument_list|,
literal|""
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"This operation requires one or more entries to be selected."
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|execute ()
specifier|public
name|void
name|execute
parameter_list|()
block|{
try|try
block|{
name|BackgroundTask
operator|.
name|wrap
argument_list|(
parameter_list|()
lambda|->
name|lookupIdentifiers
argument_list|(
name|stateManager
operator|.
name|getSelectedEntries
argument_list|()
argument_list|)
argument_list|)
operator|.
name|onSuccess
argument_list|(
name|frame
operator|.
name|getDialogService
argument_list|()
operator|::
name|notify
argument_list|)
operator|.
name|executeWith
argument_list|(
name|Globals
operator|.
name|TASK_EXECUTOR
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Problem running ID Worker"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|getAction ()
specifier|public
name|Action
name|getAction
parameter_list|()
block|{
return|return
operator|new
name|Action
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|Optional
argument_list|<
name|JabRefIcon
argument_list|>
name|getIcon
parameter_list|()
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|Optional
argument_list|<
name|KeyBinding
argument_list|>
name|getKeyBinding
parameter_list|()
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getText
parameter_list|()
block|{
return|return
name|fetcher
operator|.
name|getIdentifierName
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
literal|""
return|;
block|}
block|}
return|;
block|}
DECL|method|lookupIdentifiers (List<BibEntry> bibEntries)
specifier|private
name|String
name|lookupIdentifiers
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|bibEntries
parameter_list|)
block|{
name|String
name|totalCount
init|=
name|Integer
operator|.
name|toString
argument_list|(
name|bibEntries
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
name|NamedCompound
name|namedCompound
init|=
operator|new
name|NamedCompound
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Look up %0"
argument_list|,
name|fetcher
operator|.
name|getIdentifierName
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|int
name|count
init|=
literal|0
decl_stmt|;
name|int
name|foundCount
init|=
literal|0
decl_stmt|;
for|for
control|(
name|BibEntry
name|bibEntry
range|:
name|bibEntries
control|)
block|{
name|count
operator|++
expr_stmt|;
specifier|final
name|String
name|statusMessage
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Looking up %0... - entry %1 out of %2 - found %3"
argument_list|,
name|fetcher
operator|.
name|getIdentifierName
argument_list|()
argument_list|,
name|Integer
operator|.
name|toString
argument_list|(
name|count
argument_list|)
argument_list|,
name|totalCount
argument_list|,
name|Integer
operator|.
name|toString
argument_list|(
name|foundCount
argument_list|)
argument_list|)
decl_stmt|;
name|DefaultTaskExecutor
operator|.
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
name|frame
operator|.
name|getDialogService
argument_list|()
operator|.
name|notify
argument_list|(
name|statusMessage
argument_list|)
argument_list|)
expr_stmt|;
name|Optional
argument_list|<
name|T
argument_list|>
name|identifier
init|=
name|Optional
operator|.
name|empty
argument_list|()
decl_stmt|;
try|try
block|{
name|identifier
operator|=
name|fetcher
operator|.
name|findIdentifier
argument_list|(
name|bibEntry
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|FetcherException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Could not fetch "
operator|+
name|fetcher
operator|.
name|getIdentifierName
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|identifier
operator|.
name|isPresent
argument_list|()
operator|&&
operator|!
name|bibEntry
operator|.
name|hasField
argument_list|(
name|identifier
operator|.
name|get
argument_list|()
operator|.
name|getDefaultField
argument_list|()
argument_list|)
condition|)
block|{
name|Optional
argument_list|<
name|FieldChange
argument_list|>
name|fieldChange
init|=
name|bibEntry
operator|.
name|setField
argument_list|(
name|identifier
operator|.
name|get
argument_list|()
operator|.
name|getDefaultField
argument_list|()
argument_list|,
name|identifier
operator|.
name|get
argument_list|()
operator|.
name|getNormalized
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|fieldChange
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|namedCompound
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableFieldChange
argument_list|(
name|fieldChange
operator|.
name|get
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|foundCount
operator|++
expr_stmt|;
specifier|final
name|String
name|nextStatusMessage
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Looking up %0... - entry %1 out of %2 - found %3"
argument_list|,
name|fetcher
operator|.
name|getIdentifierName
argument_list|()
argument_list|,
name|Integer
operator|.
name|toString
argument_list|(
name|count
argument_list|)
argument_list|,
name|totalCount
argument_list|,
name|Integer
operator|.
name|toString
argument_list|(
name|foundCount
argument_list|)
argument_list|)
decl_stmt|;
name|DefaultTaskExecutor
operator|.
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
name|frame
operator|.
name|getDialogService
argument_list|()
operator|.
name|notify
argument_list|(
name|nextStatusMessage
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|namedCompound
operator|.
name|end
argument_list|()
expr_stmt|;
if|if
condition|(
name|foundCount
operator|>
literal|0
condition|)
block|{
name|undoManager
operator|.
name|addEdit
argument_list|(
name|namedCompound
argument_list|)
expr_stmt|;
block|}
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Determined %0 for %1 entries"
argument_list|,
name|fetcher
operator|.
name|getIdentifierName
argument_list|()
argument_list|,
name|Integer
operator|.
name|toString
argument_list|(
name|foundCount
argument_list|)
argument_list|)
return|;
block|}
block|}
end_class

end_unit
