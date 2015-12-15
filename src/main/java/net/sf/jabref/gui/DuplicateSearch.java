begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_comment
comment|// created by : ?
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|// modified : r.nagel 2.09.2004
end_comment

begin_comment
comment|//            - new SearcherRunnable.setFinish() method
end_comment

begin_comment
comment|//            - replace thread.sleep in run() by wait() and notify() mechanism
end_comment

begin_package
DECL|package|net.sf.jabref.gui
package|package
name|net
operator|.
name|sf
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
name|Vector
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|*
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
name|undo
operator|.
name|NamedCompound
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
name|undo
operator|.
name|UndoableInsertEntry
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
name|undo
operator|.
name|UndoableRemoveEntry
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
name|worker
operator|.
name|CallBack
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
name|bibtex
operator|.
name|DuplicateCheck
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
name|logic
operator|.
name|l10n
operator|.
name|Localization
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
name|BibEntry
index|[]
name|bes
decl_stmt|;
DECL|field|duplicates
specifier|private
specifier|final
name|Vector
argument_list|<
name|BibEntry
index|[]
argument_list|>
name|duplicates
init|=
operator|new
name|Vector
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
name|Object
index|[]
name|keys
init|=
name|panel
operator|.
name|getDatabase
argument_list|()
operator|.
name|getKeySet
argument_list|()
operator|.
name|toArray
argument_list|()
decl_stmt|;
if|if
condition|(
name|keys
operator|.
name|length
operator|<
literal|2
condition|)
block|{
return|return;
block|}
name|bes
operator|=
operator|new
name|BibEntry
index|[
name|keys
operator|.
name|length
index|]
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|keys
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|bes
index|[
name|i
index|]
operator|=
name|panel
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntryById
argument_list|(
operator|(
name|String
operator|)
name|keys
index|[
name|i
index|]
argument_list|)
expr_stmt|;
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
name|executeWithLowPriorityInOwnThread
argument_list|(
name|st
argument_list|,
literal|"Searcher"
argument_list|)
expr_stmt|;
name|int
name|current
init|=
literal|0
decl_stmt|;
specifier|final
name|ArrayList
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
name|ArrayList
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
comment|// duplicates found
block|{
name|BibEntry
index|[]
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
index|[
literal|0
index|]
argument_list|)
operator|&&
operator|!
name|toRemove
operator|.
name|contains
argument_list|(
name|be
index|[
literal|1
index|]
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
index|[
literal|0
index|]
argument_list|,
name|be
index|[
literal|1
index|]
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
index|[
literal|1
index|]
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
name|panel
operator|.
name|frame
argument_list|,
name|be
index|[
literal|0
index|]
argument_list|,
name|be
index|[
literal|1
index|]
argument_list|,
name|askAboutExact
condition|?
name|DuplicateResolverDialog
operator|.
name|DUPLICATE_SEARCH_WITH_EXACT
else|:
name|DuplicateResolverDialog
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
name|int
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
name|DuplicateResolverDialog
operator|.
name|KEEP_UPPER
operator|)
operator|||
operator|(
name|answer
operator|==
name|DuplicateResolverDialog
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
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
if|if
condition|(
name|answer
operator|==
name|DuplicateResolverDialog
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
name|DuplicateResolverDialog
operator|.
name|KEEP_LOWER
condition|)
block|{
name|toRemove
operator|.
name|add
argument_list|(
name|be
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|answer
operator|==
name|DuplicateResolverDialog
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
name|DuplicateResolverDialog
operator|.
name|KEEP_MERGE
condition|)
block|{
name|toRemove
operator|.
name|add
argument_list|(
name|be
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|toRemove
operator|.
name|add
argument_list|(
name|be
index|[
literal|1
index|]
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
operator|.
name|getId
argument_list|()
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
name|panel
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Duplicate pairs found"
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
name|ce
operator|.
name|end
argument_list|()
expr_stmt|;
name|panel
operator|.
name|undoManager
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
name|length
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
name|length
operator|)
operator|&&
operator|!
name|finished
condition|;
name|j
operator|++
control|)
block|{
name|boolean
name|eq
init|=
name|DuplicateCheck
operator|.
name|isDuplicate
argument_list|(
name|bes
index|[
name|i
index|]
argument_list|,
name|bes
index|[
name|j
index|]
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
operator|new
name|BibEntry
index|[]
block|{
name|bes
index|[
name|i
index|]
block|,
name|bes
index|[
name|j
index|]
block|}
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
comment|// no synchronized used because no "realy" critical situations expected
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
name|int
name|reply
init|=
operator|-
literal|1
decl_stmt|;
DECL|field|diag
name|DuplicateResolverDialog
name|diag
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
name|int
name|dialogType
decl_stmt|;
DECL|field|merged
specifier|private
name|BibEntry
name|merged
decl_stmt|;
DECL|method|DuplicateCallBack (JabRefFrame frame, BibEntry one, BibEntry two, int dialogType)
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
name|int
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
name|int
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
name|diag
operator|=
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
expr_stmt|;
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

