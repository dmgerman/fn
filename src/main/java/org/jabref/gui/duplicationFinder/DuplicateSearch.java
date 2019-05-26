begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.duplicationFinder
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|duplicationFinder
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
name|Collection
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
name|concurrent
operator|.
name|BlockingQueue
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|LinkedBlockingQueue
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|TimeUnit
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|atomic
operator|.
name|AtomicBoolean
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|atomic
operator|.
name|AtomicInteger
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
name|JabRefExecutorService
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
name|duplicationFinder
operator|.
name|DuplicateResolverDialog
operator|.
name|DuplicateResolverResult
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
name|duplicationFinder
operator|.
name|DuplicateResolverDialog
operator|.
name|DuplicateResolverType
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
name|UndoableInsertEntry
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
name|UndoableRemoveEntry
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
name|bibtex
operator|.
name|DuplicateCheck
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
name|database
operator|.
name|BibDatabaseMode
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

begin_class
DECL|class|DuplicateSearch
specifier|public
class|class
name|DuplicateSearch
extends|extends
name|SimpleCommand
block|{
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|duplicates
specifier|private
specifier|final
name|BlockingQueue
argument_list|<
name|List
argument_list|<
name|BibEntry
argument_list|>
argument_list|>
name|duplicates
init|=
operator|new
name|LinkedBlockingQueue
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|libraryAnalyzed
specifier|private
specifier|final
name|AtomicBoolean
name|libraryAnalyzed
init|=
operator|new
name|AtomicBoolean
argument_list|()
decl_stmt|;
DECL|field|autoRemoveExactDuplicates
specifier|private
specifier|final
name|AtomicBoolean
name|autoRemoveExactDuplicates
init|=
operator|new
name|AtomicBoolean
argument_list|()
decl_stmt|;
DECL|field|duplicateCount
specifier|private
specifier|final
name|AtomicInteger
name|duplicateCount
init|=
operator|new
name|AtomicInteger
argument_list|()
decl_stmt|;
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
decl_stmt|;
DECL|field|stateManager
specifier|private
specifier|final
name|StateManager
name|stateManager
decl_stmt|;
DECL|method|DuplicateSearch (JabRefFrame frame, DialogService dialogService, StateManager stateManager)
specifier|public
name|DuplicateSearch
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|DialogService
name|dialogService
parameter_list|,
name|StateManager
name|stateManager
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
name|dialogService
operator|=
name|dialogService
expr_stmt|;
name|this
operator|.
name|stateManager
operator|=
name|stateManager
expr_stmt|;
name|this
operator|.
name|executable
operator|.
name|bind
argument_list|(
name|needsDatabase
argument_list|(
name|stateManager
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
name|BibDatabaseContext
name|database
init|=
name|stateManager
operator|.
name|getActiveDatabase
argument_list|()
operator|.
name|orElseThrow
argument_list|(
parameter_list|()
lambda|->
operator|new
name|NullPointerException
argument_list|(
literal|"Database null"
argument_list|)
argument_list|)
decl_stmt|;
name|dialogService
operator|.
name|notify
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Searching for duplicates..."
argument_list|)
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|database
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|duplicates
operator|.
name|clear
argument_list|()
expr_stmt|;
name|libraryAnalyzed
operator|.
name|set
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|autoRemoveExactDuplicates
operator|.
name|set
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|duplicateCount
operator|.
name|set
argument_list|(
literal|0
argument_list|)
expr_stmt|;
if|if
condition|(
name|entries
operator|.
name|size
argument_list|()
operator|<
literal|2
condition|)
block|{
return|return;
block|}
name|JabRefExecutorService
operator|.
name|INSTANCE
operator|.
name|executeInterruptableTask
argument_list|(
parameter_list|()
lambda|->
name|searchPossibleDuplicates
argument_list|(
name|entries
argument_list|,
name|database
operator|.
name|getMode
argument_list|()
argument_list|)
argument_list|,
literal|"DuplicateSearcher"
argument_list|)
expr_stmt|;
name|BackgroundTask
operator|.
name|wrap
argument_list|(
name|this
operator|::
name|verifyDuplicates
argument_list|)
operator|.
name|onSuccess
argument_list|(
name|this
operator|::
name|handleDuplicates
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
DECL|method|searchPossibleDuplicates (List<BibEntry> entries, BibDatabaseMode databaseMode)
specifier|private
name|void
name|searchPossibleDuplicates
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|,
name|BibDatabaseMode
name|databaseMode
parameter_list|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
operator|(
name|i
operator|<
operator|(
name|entries
operator|.
name|size
argument_list|()
operator|-
literal|1
operator|)
operator|)
condition|;
name|i
operator|++
control|)
block|{
for|for
control|(
name|int
name|j
init|=
name|i
operator|+
literal|1
init|;
operator|(
name|j
operator|<
name|entries
operator|.
name|size
argument_list|()
operator|)
condition|;
name|j
operator|++
control|)
block|{
if|if
condition|(
name|Thread
operator|.
name|interrupted
argument_list|()
condition|)
block|{
return|return;
block|}
name|BibEntry
name|first
init|=
name|entries
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|BibEntry
name|second
init|=
name|entries
operator|.
name|get
argument_list|(
name|j
argument_list|)
decl_stmt|;
if|if
condition|(
name|DuplicateCheck
operator|.
name|isDuplicate
argument_list|(
name|first
argument_list|,
name|second
argument_list|,
name|databaseMode
argument_list|)
condition|)
block|{
name|duplicates
operator|.
name|add
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|first
argument_list|,
name|second
argument_list|)
argument_list|)
expr_stmt|;
name|duplicateCount
operator|.
name|getAndIncrement
argument_list|()
expr_stmt|;
block|}
block|}
block|}
name|libraryAnalyzed
operator|.
name|set
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
DECL|method|verifyDuplicates ()
specifier|private
name|DuplicateSearchResult
name|verifyDuplicates
parameter_list|()
block|{
name|DuplicateSearchResult
name|result
init|=
operator|new
name|DuplicateSearchResult
argument_list|()
decl_stmt|;
while|while
condition|(
operator|!
name|libraryAnalyzed
operator|.
name|get
argument_list|()
operator|||
operator|!
name|duplicates
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|dups
decl_stmt|;
try|try
block|{
comment|// poll with timeout in case the library is not analyzed completely, but contains no more duplicates
name|dups
operator|=
name|this
operator|.
name|duplicates
operator|.
name|poll
argument_list|(
literal|100
argument_list|,
name|TimeUnit
operator|.
name|MILLISECONDS
argument_list|)
expr_stmt|;
if|if
condition|(
name|dups
operator|==
literal|null
condition|)
block|{
continue|continue;
block|}
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|e
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
name|BibEntry
name|first
init|=
name|dups
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|BibEntry
name|second
init|=
name|dups
operator|.
name|get
argument_list|(
literal|1
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|result
operator|.
name|isToRemove
argument_list|(
name|first
argument_list|)
operator|&&
operator|!
name|result
operator|.
name|isToRemove
argument_list|(
name|second
argument_list|)
condition|)
block|{
comment|// Check if they are exact duplicates:
name|boolean
name|askAboutExact
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|DuplicateCheck
operator|.
name|compareEntriesStrictly
argument_list|(
name|first
argument_list|,
name|second
argument_list|)
operator|>
literal|1
condition|)
block|{
if|if
condition|(
name|autoRemoveExactDuplicates
operator|.
name|get
argument_list|()
condition|)
block|{
name|result
operator|.
name|remove
argument_list|(
name|second
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|askAboutExact
operator|=
literal|true
expr_stmt|;
block|}
name|DuplicateResolverType
name|resolverType
init|=
name|askAboutExact
condition|?
name|DuplicateResolverType
operator|.
name|DUPLICATE_SEARCH_WITH_EXACT
else|:
name|DuplicateResolverType
operator|.
name|DUPLICATE_SEARCH
decl_stmt|;
name|DefaultTaskExecutor
operator|.
name|runAndWaitInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
name|askResolveStrategy
argument_list|(
name|result
argument_list|,
name|first
argument_list|,
name|second
argument_list|,
name|resolverType
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|result
return|;
block|}
DECL|method|askResolveStrategy (DuplicateSearchResult result, BibEntry first, BibEntry second, DuplicateResolverType resolverType)
specifier|private
name|void
name|askResolveStrategy
parameter_list|(
name|DuplicateSearchResult
name|result
parameter_list|,
name|BibEntry
name|first
parameter_list|,
name|BibEntry
name|second
parameter_list|,
name|DuplicateResolverType
name|resolverType
parameter_list|)
block|{
name|DuplicateResolverDialog
name|dialog
init|=
operator|new
name|DuplicateResolverDialog
argument_list|(
name|first
argument_list|,
name|second
argument_list|,
name|resolverType
argument_list|,
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|getBibDatabaseContext
argument_list|()
argument_list|)
decl_stmt|;
name|DuplicateResolverResult
name|resolverResult
init|=
name|dialog
operator|.
name|showAndWait
argument_list|()
operator|.
name|orElse
argument_list|(
name|DuplicateResolverResult
operator|.
name|BREAK
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|resolverResult
operator|==
name|DuplicateResolverResult
operator|.
name|KEEP_LEFT
operator|)
operator|||
operator|(
name|resolverResult
operator|==
name|DuplicateResolverResult
operator|.
name|AUTOREMOVE_EXACT
operator|)
condition|)
block|{
name|result
operator|.
name|remove
argument_list|(
name|second
argument_list|)
expr_stmt|;
if|if
condition|(
name|resolverResult
operator|==
name|DuplicateResolverResult
operator|.
name|AUTOREMOVE_EXACT
condition|)
block|{
name|autoRemoveExactDuplicates
operator|.
name|set
argument_list|(
literal|true
argument_list|)
expr_stmt|;
comment|// Remember choice
block|}
block|}
elseif|else
if|if
condition|(
name|resolverResult
operator|==
name|DuplicateResolverResult
operator|.
name|KEEP_RIGHT
condition|)
block|{
name|result
operator|.
name|remove
argument_list|(
name|first
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|resolverResult
operator|==
name|DuplicateResolverResult
operator|.
name|BREAK
condition|)
block|{
name|libraryAnalyzed
operator|.
name|set
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|duplicates
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|resolverResult
operator|==
name|DuplicateResolverResult
operator|.
name|KEEP_MERGE
condition|)
block|{
name|result
operator|.
name|replace
argument_list|(
name|first
argument_list|,
name|second
argument_list|,
name|dialog
operator|.
name|getMergedEntry
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|handleDuplicates (DuplicateSearchResult result)
specifier|private
name|void
name|handleDuplicates
parameter_list|(
name|DuplicateSearchResult
name|result
parameter_list|)
block|{
if|if
condition|(
name|result
operator|==
literal|null
condition|)
block|{
return|return;
block|}
name|BasePanel
name|panel
init|=
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
decl_stmt|;
specifier|final
name|NamedCompound
name|compoundEdit
init|=
operator|new
name|NamedCompound
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"duplicate removal"
argument_list|)
argument_list|)
decl_stmt|;
comment|// Now, do the actual removal:
if|if
condition|(
operator|!
name|result
operator|.
name|getToRemove
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
for|for
control|(
name|BibEntry
name|entry
range|:
name|result
operator|.
name|getToRemove
argument_list|()
control|)
block|{
name|panel
operator|.
name|getDatabase
argument_list|()
operator|.
name|removeEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|compoundEdit
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableRemoveEntry
argument_list|(
name|panel
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|panel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
block|}
comment|// and adding merged entries:
if|if
condition|(
operator|!
name|result
operator|.
name|getToAdd
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
for|for
control|(
name|BibEntry
name|entry
range|:
name|result
operator|.
name|getToAdd
argument_list|()
control|)
block|{
name|panel
operator|.
name|getDatabase
argument_list|()
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|compoundEdit
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableInsertEntry
argument_list|(
name|panel
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|panel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
block|}
name|dialogService
operator|.
name|notify
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Duplicates found"
argument_list|)
operator|+
literal|": "
operator|+
name|duplicateCount
operator|.
name|get
argument_list|()
operator|+
literal|' '
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"pairs processed"
argument_list|)
operator|+
literal|": "
operator|+
name|result
operator|.
name|getDuplicateCount
argument_list|()
argument_list|)
expr_stmt|;
name|compoundEdit
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
name|compoundEdit
argument_list|)
expr_stmt|;
block|}
comment|/**      * Result of a duplicate search.      * Uses {@link System#identityHashCode(Object)} for identifying objects for removal, as completely identical      * {@link BibEntry BibEntries} are equal to each other.      */
DECL|class|DuplicateSearchResult
class|class
name|DuplicateSearchResult
block|{
DECL|field|toRemove
specifier|private
specifier|final
name|Map
argument_list|<
name|Integer
argument_list|,
name|BibEntry
argument_list|>
name|toRemove
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|toAdd
specifier|private
specifier|final
name|List
argument_list|<
name|BibEntry
argument_list|>
name|toAdd
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|duplicates
specifier|private
name|int
name|duplicates
init|=
literal|0
decl_stmt|;
DECL|method|getToRemove ()
specifier|public
specifier|synchronized
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|getToRemove
parameter_list|()
block|{
return|return
name|toRemove
operator|.
name|values
argument_list|()
return|;
block|}
DECL|method|getToAdd ()
specifier|public
specifier|synchronized
name|List
argument_list|<
name|BibEntry
argument_list|>
name|getToAdd
parameter_list|()
block|{
return|return
name|toAdd
return|;
block|}
DECL|method|remove (BibEntry entry)
specifier|public
specifier|synchronized
name|void
name|remove
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
name|toRemove
operator|.
name|put
argument_list|(
name|System
operator|.
name|identityHashCode
argument_list|(
name|entry
argument_list|)
argument_list|,
name|entry
argument_list|)
expr_stmt|;
name|duplicates
operator|++
expr_stmt|;
block|}
DECL|method|replace (BibEntry first, BibEntry second, BibEntry replacement)
specifier|public
specifier|synchronized
name|void
name|replace
parameter_list|(
name|BibEntry
name|first
parameter_list|,
name|BibEntry
name|second
parameter_list|,
name|BibEntry
name|replacement
parameter_list|)
block|{
name|remove
argument_list|(
name|first
argument_list|)
expr_stmt|;
name|remove
argument_list|(
name|second
argument_list|)
expr_stmt|;
name|toAdd
operator|.
name|add
argument_list|(
name|replacement
argument_list|)
expr_stmt|;
name|duplicates
operator|++
expr_stmt|;
block|}
DECL|method|isToRemove (BibEntry entry)
specifier|public
specifier|synchronized
name|boolean
name|isToRemove
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
return|return
name|toRemove
operator|.
name|containsKey
argument_list|(
name|System
operator|.
name|identityHashCode
argument_list|(
name|entry
argument_list|)
argument_list|)
return|;
block|}
DECL|method|getDuplicateCount ()
specifier|public
specifier|synchronized
name|int
name|getDuplicateCount
parameter_list|()
block|{
return|return
name|duplicates
return|;
block|}
block|}
block|}
end_class

end_unit
