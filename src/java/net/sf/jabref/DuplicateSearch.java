begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 Nizar N. Batada, Morten O. Alver  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
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
comment|//            - new SearcherThread.setFinish() method
end_comment

begin_comment
comment|//            - replace thread.sleep in run() by wait() and notify() mechanism
end_comment

begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
package|;
end_package

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|undo
operator|.
name|UndoableRemoveEntry
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

begin_class
DECL|class|DuplicateSearch
specifier|public
class|class
name|DuplicateSearch
extends|extends
name|Thread
block|{
DECL|field|panel
name|BasePanel
name|panel
decl_stmt|;
DECL|field|bes
name|BibtexEntry
index|[]
name|bes
decl_stmt|;
DECL|field|duplicates
specifier|final
name|Vector
name|duplicates
init|=
operator|new
name|Vector
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
DECL|method|run ()
specifier|public
name|void
name|run
parameter_list|()
block|{
name|NamedCompound
name|ce
init|=
literal|null
decl_stmt|;
name|int
name|duplicateCounter
init|=
literal|0
decl_stmt|;
name|panel
operator|.
name|output
argument_list|(
name|Globals
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
name|database
operator|.
name|getKeySet
argument_list|()
operator|.
name|toArray
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|keys
operator|==
literal|null
operator|)
operator|||
operator|(
name|keys
operator|.
name|length
operator|<
literal|2
operator|)
condition|)
return|return;
name|bes
operator|=
operator|new
name|BibtexEntry
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
name|bes
index|[
name|i
index|]
operator|=
name|panel
operator|.
name|database
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
name|SearcherThread
name|st
init|=
operator|new
name|SearcherThread
argument_list|()
decl_stmt|;
name|st
operator|.
name|setPriority
argument_list|(
name|Thread
operator|.
name|MIN_PRIORITY
argument_list|)
expr_stmt|;
name|st
operator|.
name|start
argument_list|()
expr_stmt|;
name|int
name|current
init|=
literal|0
decl_stmt|;
name|DuplicateResolverDialog
name|drd
init|=
literal|null
decl_stmt|;
comment|/*   loop: while (!st.finished() || (current< duplicates.size()))   {     if ( current>= duplicates.size() )     {       // No more duplicates to resolve, but search is still in progress. Sleep a little.        try        {          sleep(10);        } catch (InterruptedException ex) {}        continue loop;     }   } */
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
synchronized|synchronized
init|(
name|duplicates
init|)
block|{
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
name|Exception
name|e
parameter_list|)
block|{}
block|}
block|}
else|else
comment|// duplicates found
block|{
name|BibtexEntry
index|[]
name|be
init|=
operator|(
name|BibtexEntry
index|[]
operator|)
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
operator|(
name|panel
operator|.
name|database
operator|.
name|getEntryById
argument_list|(
name|be
index|[
literal|0
index|]
operator|.
name|getId
argument_list|()
argument_list|)
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|panel
operator|.
name|database
operator|.
name|getEntryById
argument_list|(
name|be
index|[
literal|1
index|]
operator|.
name|getId
argument_list|()
argument_list|)
operator|!=
literal|null
operator|)
condition|)
block|{
name|drd
operator|=
operator|new
name|DuplicateResolverDialog
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
name|DuplicateResolverDialog
operator|.
name|DUPLICATE_SEARCH
argument_list|)
expr_stmt|;
name|drd
operator|.
name|show
argument_list|()
expr_stmt|;
name|duplicateCounter
operator|++
expr_stmt|;
name|int
name|answer
init|=
name|drd
operator|.
name|getSelected
argument_list|()
decl_stmt|;
if|if
condition|(
name|answer
operator|==
name|DuplicateResolverDialog
operator|.
name|KEEP_UPPER
condition|)
block|{
if|if
condition|(
name|ce
operator|==
literal|null
condition|)
name|ce
operator|=
operator|new
name|NamedCompound
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"duplicate removal"
argument_list|)
argument_list|)
expr_stmt|;
name|panel
operator|.
name|database
operator|.
name|removeEntry
argument_list|(
name|be
index|[
literal|1
index|]
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|panel
operator|.
name|markBaseChanged
argument_list|()
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
name|database
argument_list|,
name|be
index|[
literal|1
index|]
argument_list|,
name|panel
argument_list|)
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
name|KEEP_LOWER
condition|)
block|{
if|if
condition|(
name|ce
operator|==
literal|null
condition|)
name|ce
operator|=
operator|new
name|NamedCompound
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"duplicate removal"
argument_list|)
argument_list|)
expr_stmt|;
name|panel
operator|.
name|database
operator|.
name|removeEntry
argument_list|(
name|be
index|[
literal|0
index|]
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|panel
operator|.
name|markBaseChanged
argument_list|()
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
name|database
argument_list|,
name|be
index|[
literal|0
index|]
argument_list|,
name|panel
argument_list|)
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
name|drd
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|drd
operator|!=
literal|null
condition|)
name|drd
operator|.
name|dispose
argument_list|()
expr_stmt|;
name|panel
operator|.
name|output
argument_list|(
name|Globals
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
literal|" "
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"pairs processed"
argument_list|)
operator|+
literal|": "
operator|+
name|duplicateCounter
argument_list|)
expr_stmt|;
if|if
condition|(
name|ce
operator|!=
literal|null
condition|)
block|{
name|ce
operator|.
name|end
argument_list|()
expr_stmt|;
comment|//Util.pr("ox");
name|panel
operator|.
name|undoManager
operator|.
name|addEdit
argument_list|(
name|ce
argument_list|)
expr_stmt|;
comment|//markBaseChanged();
comment|//refreshTable();
block|}
block|}
DECL|class|SearcherThread
class|class
name|SearcherThread
extends|extends
name|Thread
block|{
DECL|field|finished
specifier|private
name|boolean
name|finished
init|=
literal|false
decl_stmt|;
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
name|bes
operator|.
name|length
operator|-
literal|1
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
name|Util
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
argument_list|,
name|Globals
operator|.
name|duplicateThreshold
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
name|BibtexEntry
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
block|}
end_class

end_unit

