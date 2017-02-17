begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
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
name|javax
operator|.
name|swing
operator|.
name|SwingUtilities
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
name|JabRefGUI
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
name|worker
operator|.
name|CallBack
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
name|DuplicateCheck
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
name|spin
operator|.
name|Spin
import|;
end_import

begin_class
DECL|class|DuplicateSearch
specifier|public
class|class
name|DuplicateSearch
implements|implements
name|Runnable
block|{
DECL|field|panel
specifier|private
specifier|final
name|BasePanel
name|panel
decl_stmt|;
DECL|field|bes
specifier|private
name|List
argument_list|<
name|BibEntry
argument_list|>
name|bes
decl_stmt|;
DECL|field|duplicates
specifier|private
specifier|final
name|List
argument_list|<
name|List
argument_list|<
name|BibEntry
argument_list|>
argument_list|>
name|duplicates
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|method|DuplicateSearch (BasePanel bp)
specifier|public
name|DuplicateSearch
parameter_list|(
name|BasePanel
name|bp
parameter_list|)
block|{
name|panel
operator|=
name|bp
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|run ()
specifier|public
name|void
name|run
parameter_list|()
block|{
name|panel
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Searching for duplicates..."
argument_list|)
argument_list|)
expr_stmt|;
name|bes
operator|=
name|panel
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
expr_stmt|;
if|if
condition|(
name|bes
operator|.
name|size
argument_list|()
operator|<
literal|2
condition|)
block|{
return|return;
block|}
name|SearcherRunnable
name|st
init|=
operator|new
name|SearcherRunnable
argument_list|()
decl_stmt|;
name|JabRefExecutorService
operator|.
name|INSTANCE
operator|.
name|executeInterruptableTask
argument_list|(
name|st
argument_list|,
literal|"DuplicateSearcher"
argument_list|)
expr_stmt|;
name|int
name|current
init|=
literal|0
decl_stmt|;
specifier|final
name|List
argument_list|<
name|BibEntry
argument_list|>
name|toRemove
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
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
name|int
name|duplicateCounter
init|=
literal|0
decl_stmt|;
name|boolean
name|autoRemoveExactDuplicates
init|=
literal|false
decl_stmt|;
synchronized|synchronized
init|(
name|duplicates
init|)
block|{
while|while
condition|(
operator|!
name|st
operator|.
name|finished
argument_list|()
operator|||
operator|(
name|current
operator|<
name|duplicates
operator|.
name|size
argument_list|()
operator|)
condition|)
block|{
if|if
condition|(
name|current
operator|>=
name|duplicates
operator|.
name|size
argument_list|()
condition|)
block|{
comment|// wait until the search thread puts something into duplicates vector
comment|// or finish its work
try|try
block|{
name|duplicates
operator|.
name|wait
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|ignored
parameter_list|)
block|{
comment|// Ignore
block|}
block|}
else|else
block|{
comment|// duplicates found
name|List
argument_list|<
name|BibEntry
argument_list|>
name|be
init|=
name|duplicates
operator|.
name|get
argument_list|(
name|current
argument_list|)
decl_stmt|;
name|current
operator|++
expr_stmt|;
if|if
condition|(
operator|!
name|toRemove
operator|.
name|contains
argument_list|(
name|be
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
operator|&&
operator|!
name|toRemove
operator|.
name|contains
argument_list|(
name|be
operator|.
name|get
argument_list|(
literal|1
argument_list|)
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
name|be
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|,
name|be
operator|.
name|get
argument_list|(
literal|1
argument_list|)
argument_list|)
operator|>
literal|1
condition|)
block|{
if|if
condition|(
name|autoRemoveExactDuplicates
condition|)
block|{
name|toRemove
operator|.
name|add
argument_list|(
name|be
operator|.
name|get
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|duplicateCounter
operator|++
expr_stmt|;
continue|continue;
block|}
name|askAboutExact
operator|=
literal|true
expr_stmt|;
block|}
name|DuplicateCallBack
name|cb
init|=
operator|new
name|DuplicateCallBack
argument_list|(
name|JabRefGUI
operator|.
name|getMainFrame
argument_list|()
argument_list|,
name|be
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|,
name|be
operator|.
name|get
argument_list|(
literal|1
argument_list|)
argument_list|,
name|askAboutExact
condition|?
name|DuplicateResolverType
operator|.
name|DUPLICATE_SEARCH_WITH_EXACT
else|:
name|DuplicateResolverType
operator|.
name|DUPLICATE_SEARCH
argument_list|)
decl_stmt|;
operator|(
operator|(
name|CallBack
operator|)
name|Spin
operator|.
name|over
argument_list|(
name|cb
argument_list|)
operator|)
operator|.
name|update
argument_list|()
expr_stmt|;
name|duplicateCounter
operator|++
expr_stmt|;
name|DuplicateResolverResult
name|answer
init|=
name|cb
operator|.
name|getSelected
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|answer
operator|==
name|DuplicateResolverResult
operator|.
name|KEEP_LEFT
operator|)
operator|||
operator|(
name|answer
operator|==
name|DuplicateResolverResult
operator|.
name|AUTOREMOVE_EXACT
operator|)
condition|)
block|{
name|toRemove
operator|.
name|add
argument_list|(
name|be
operator|.
name|get
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|answer
operator|==
name|DuplicateResolverResult
operator|.
name|AUTOREMOVE_EXACT
condition|)
block|{
name|autoRemoveExactDuplicates
operator|=
literal|true
expr_stmt|;
comment|// Remember choice
block|}
block|}
elseif|else
if|if
condition|(
name|answer
operator|==
name|DuplicateResolverResult
operator|.
name|KEEP_RIGHT
condition|)
block|{
name|toRemove
operator|.
name|add
argument_list|(
name|be
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|answer
operator|==
name|DuplicateResolverResult
operator|.
name|BREAK
condition|)
block|{
name|st
operator|.
name|setFinished
argument_list|()
expr_stmt|;
comment|// thread killing
name|current
operator|=
name|Integer
operator|.
name|MAX_VALUE
expr_stmt|;
name|duplicateCounter
operator|--
expr_stmt|;
comment|// correct counter
block|}
elseif|else
if|if
condition|(
name|answer
operator|==
name|DuplicateResolverResult
operator|.
name|KEEP_MERGE
condition|)
block|{
name|toRemove
operator|.
name|addAll
argument_list|(
name|be
argument_list|)
expr_stmt|;
name|toAdd
operator|.
name|add
argument_list|(
name|cb
operator|.
name|getMergedEntry
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
specifier|final
name|NamedCompound
name|ce
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
specifier|final
name|int
name|dupliC
init|=
name|duplicateCounter
decl_stmt|;
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
operator|new
name|Runnable
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
comment|// Now, do the actual removal:
if|if
condition|(
operator|!
name|toRemove
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
name|toRemove
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
name|ce
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
argument_list|,
name|panel
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
name|toAdd
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
name|toAdd
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
name|ce
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
argument_list|,
name|panel
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
synchronized|synchronized
init|(
name|duplicates
init|)
block|{
name|panel
operator|.
name|output
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
name|duplicates
operator|.
name|size
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
name|dupliC
argument_list|)
expr_stmt|;
block|}
name|ce
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
name|ce
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
DECL|class|SearcherRunnable
class|class
name|SearcherRunnable
implements|implements
name|Runnable
block|{
DECL|field|finished
specifier|private
specifier|volatile
name|boolean
name|finished
decl_stmt|;
annotation|@
name|Override
DECL|method|run ()
specifier|public
name|void
name|run
parameter_list|()
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
name|bes
operator|.
name|size
argument_list|()
operator|-
literal|1
operator|)
operator|)
operator|&&
operator|!
name|finished
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
name|bes
operator|.
name|size
argument_list|()
operator|)
operator|&&
operator|!
name|finished
condition|;
name|j
operator|++
control|)
block|{
name|BibEntry
name|first
init|=
name|bes
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|BibEntry
name|second
init|=
name|bes
operator|.
name|get
argument_list|(
name|j
argument_list|)
decl_stmt|;
name|boolean
name|eq
init|=
name|DuplicateCheck
operator|.
name|isDuplicate
argument_list|(
name|first
argument_list|,
name|second
argument_list|,
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getMode
argument_list|()
argument_list|)
decl_stmt|;
comment|// If (suspected) duplicates, add them to the duplicates vector.
if|if
condition|(
name|eq
condition|)
block|{
synchronized|synchronized
init|(
name|duplicates
init|)
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
name|duplicates
operator|.
name|notifyAll
argument_list|()
expr_stmt|;
comment|// send wake up all
block|}
block|}
block|}
block|}
name|finished
operator|=
literal|true
expr_stmt|;
comment|// if no duplicates found, the graphical thread will never wake up
synchronized|synchronized
init|(
name|duplicates
init|)
block|{
name|duplicates
operator|.
name|notifyAll
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|finished ()
specifier|public
name|boolean
name|finished
parameter_list|()
block|{
return|return
name|finished
return|;
block|}
comment|// Thread cancel option
comment|// no synchronized used because no "really" critical situations expected
DECL|method|setFinished ()
specifier|public
name|void
name|setFinished
parameter_list|()
block|{
name|finished
operator|=
literal|true
expr_stmt|;
block|}
block|}
DECL|class|DuplicateCallBack
specifier|static
class|class
name|DuplicateCallBack
implements|implements
name|CallBack
block|{
DECL|field|reply
specifier|private
name|DuplicateResolverResult
name|reply
init|=
name|DuplicateResolverResult
operator|.
name|NOT_CHOSEN
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|one
specifier|private
specifier|final
name|BibEntry
name|one
decl_stmt|;
DECL|field|two
specifier|private
specifier|final
name|BibEntry
name|two
decl_stmt|;
DECL|field|dialogType
specifier|private
specifier|final
name|DuplicateResolverType
name|dialogType
decl_stmt|;
DECL|field|merged
specifier|private
name|BibEntry
name|merged
decl_stmt|;
DECL|method|DuplicateCallBack (JabRefFrame frame, BibEntry one, BibEntry two, DuplicateResolverType dialogType)
specifier|public
name|DuplicateCallBack
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|BibEntry
name|one
parameter_list|,
name|BibEntry
name|two
parameter_list|,
name|DuplicateResolverType
name|dialogType
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
name|one
operator|=
name|one
expr_stmt|;
name|this
operator|.
name|two
operator|=
name|two
expr_stmt|;
name|this
operator|.
name|dialogType
operator|=
name|dialogType
expr_stmt|;
block|}
DECL|method|getSelected ()
specifier|public
name|DuplicateResolverResult
name|getSelected
parameter_list|()
block|{
return|return
name|reply
return|;
block|}
DECL|method|getMergedEntry ()
specifier|public
name|BibEntry
name|getMergedEntry
parameter_list|()
block|{
return|return
name|merged
return|;
block|}
annotation|@
name|Override
DECL|method|update ()
specifier|public
name|void
name|update
parameter_list|()
block|{
name|DuplicateResolverDialog
name|diag
init|=
operator|new
name|DuplicateResolverDialog
argument_list|(
name|frame
argument_list|,
name|one
argument_list|,
name|two
argument_list|,
name|dialogType
argument_list|)
decl_stmt|;
name|diag
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|diag
operator|.
name|dispose
argument_list|()
expr_stmt|;
name|reply
operator|=
name|diag
operator|.
name|getSelected
argument_list|()
expr_stmt|;
name|merged
operator|=
name|diag
operator|.
name|getMergedEntry
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit
