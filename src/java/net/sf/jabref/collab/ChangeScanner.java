begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.collab
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|collab
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|*
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|*
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|tree
operator|.
name|DefaultMutableTreeNode
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
name|groups
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
name|imports
operator|.
name|*
import|;
end_import

begin_class
DECL|class|ChangeScanner
specifier|public
class|class
name|ChangeScanner
extends|extends
name|Thread
block|{
DECL|field|MATCH_THRESHOLD
specifier|final
name|double
name|MATCH_THRESHOLD
init|=
literal|0.4
decl_stmt|;
DECL|field|sortBy
specifier|final
name|String
index|[]
name|sortBy
init|=
operator|new
name|String
index|[]
block|{
literal|"year"
block|,
literal|"author"
block|,
literal|"title"
block|}
decl_stmt|;
DECL|field|f
name|File
name|f
decl_stmt|;
DECL|field|inMem
name|BibtexDatabase
name|inMem
decl_stmt|;
DECL|field|mdInMem
name|MetaData
name|mdInMem
decl_stmt|;
DECL|field|panel
name|BasePanel
name|panel
decl_stmt|;
DECL|field|frame
name|JabRefFrame
name|frame
decl_stmt|;
comment|/**      * We create an ArrayList to hold the changes we find. These will be added in the form      * of UndoEdit objects. We instantiate these so that the changes found in the file on disk      * can be reproduced in memory by calling redo() on them. REDO, not UNDO!      */
comment|//ArrayList changes = new ArrayList();
DECL|field|changes
name|DefaultMutableTreeNode
name|changes
init|=
operator|new
name|DefaultMutableTreeNode
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"External changes"
argument_list|)
argument_list|)
decl_stmt|;
comment|//  NamedCompound edit = new NamedCompound("Merged external changes")
DECL|method|ChangeScanner (JabRefFrame frame, BasePanel bp)
specifier|public
name|ChangeScanner
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|BasePanel
name|bp
parameter_list|)
block|{
comment|//, BibtexDatabase inMem, MetaData mdInMem) {
name|panel
operator|=
name|bp
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|this
operator|.
name|inMem
operator|=
name|bp
operator|.
name|database
argument_list|()
expr_stmt|;
name|this
operator|.
name|mdInMem
operator|=
name|bp
operator|.
name|metaData
argument_list|()
expr_stmt|;
comment|// Set low priority:
name|setPriority
argument_list|(
name|Thread
operator|.
name|MIN_PRIORITY
argument_list|)
expr_stmt|;
block|}
DECL|method|changeScan (File f)
specifier|public
name|void
name|changeScan
parameter_list|(
name|File
name|f
parameter_list|)
block|{
name|this
operator|.
name|f
operator|=
name|f
expr_stmt|;
name|start
argument_list|()
expr_stmt|;
block|}
DECL|method|run ()
specifier|public
name|void
name|run
parameter_list|()
block|{
try|try
block|{
comment|//long startTime = System.currentTimeMillis();
comment|// Parse the temporary file.
name|File
name|tempFile
init|=
name|Globals
operator|.
name|fileUpdateMonitor
operator|.
name|getTempFile
argument_list|(
name|panel
operator|.
name|fileMonitorHandle
argument_list|()
argument_list|)
decl_stmt|;
name|ParserResult
name|pr
init|=
name|ImportFormatReader
operator|.
name|loadDatabase
argument_list|(
name|tempFile
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"defaultEncoding"
argument_list|)
argument_list|)
decl_stmt|;
name|BibtexDatabase
name|inTemp
init|=
name|pr
operator|.
name|getDatabase
argument_list|()
decl_stmt|;
name|MetaData
name|mdInTemp
init|=
operator|new
name|MetaData
argument_list|(
name|pr
operator|.
name|getMetaData
argument_list|()
argument_list|,
name|inTemp
argument_list|)
decl_stmt|;
comment|//Util.pr(tempFile.getPath()+": "+inMem.getEntryCount());
comment|// Parse the modified file.
name|pr
operator|=
name|ImportFormatReader
operator|.
name|loadDatabase
argument_list|(
name|f
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"defaultEncoding"
argument_list|)
argument_list|)
expr_stmt|;
name|BibtexDatabase
name|onDisk
init|=
name|pr
operator|.
name|getDatabase
argument_list|()
decl_stmt|;
name|MetaData
name|mdOnDisk
init|=
operator|new
name|MetaData
argument_list|(
name|pr
operator|.
name|getMetaData
argument_list|()
argument_list|,
name|onDisk
argument_list|)
decl_stmt|;
comment|//Util.pr(f.getPath()+": "+onDisk.getEntryCount());
comment|// Sort both databases according to a common sort key.
name|EntryComparator
name|comp
init|=
operator|new
name|EntryComparator
argument_list|(
literal|false
argument_list|,
literal|true
argument_list|,
name|sortBy
index|[
literal|2
index|]
argument_list|)
decl_stmt|;
name|comp
operator|=
operator|new
name|EntryComparator
argument_list|(
literal|false
argument_list|,
literal|true
argument_list|,
name|sortBy
index|[
literal|1
index|]
argument_list|,
name|comp
argument_list|)
expr_stmt|;
name|comp
operator|=
operator|new
name|EntryComparator
argument_list|(
literal|false
argument_list|,
literal|true
argument_list|,
name|sortBy
index|[
literal|0
index|]
argument_list|,
name|comp
argument_list|)
expr_stmt|;
name|EntrySorter
name|sInTemp
init|=
name|inTemp
operator|.
name|getSorter
argument_list|(
name|comp
argument_list|)
decl_stmt|;
name|comp
operator|=
operator|new
name|EntryComparator
argument_list|(
literal|false
argument_list|,
literal|true
argument_list|,
name|sortBy
index|[
literal|2
index|]
argument_list|)
expr_stmt|;
name|comp
operator|=
operator|new
name|EntryComparator
argument_list|(
literal|false
argument_list|,
literal|true
argument_list|,
name|sortBy
index|[
literal|1
index|]
argument_list|,
name|comp
argument_list|)
expr_stmt|;
name|comp
operator|=
operator|new
name|EntryComparator
argument_list|(
literal|false
argument_list|,
literal|true
argument_list|,
name|sortBy
index|[
literal|0
index|]
argument_list|,
name|comp
argument_list|)
expr_stmt|;
name|EntrySorter
name|sOnDisk
init|=
name|onDisk
operator|.
name|getSorter
argument_list|(
name|comp
argument_list|)
decl_stmt|;
name|comp
operator|=
operator|new
name|EntryComparator
argument_list|(
literal|false
argument_list|,
literal|true
argument_list|,
name|sortBy
index|[
literal|2
index|]
argument_list|)
expr_stmt|;
name|comp
operator|=
operator|new
name|EntryComparator
argument_list|(
literal|false
argument_list|,
literal|true
argument_list|,
name|sortBy
index|[
literal|1
index|]
argument_list|,
name|comp
argument_list|)
expr_stmt|;
name|comp
operator|=
operator|new
name|EntryComparator
argument_list|(
literal|false
argument_list|,
literal|true
argument_list|,
name|sortBy
index|[
literal|0
index|]
argument_list|,
name|comp
argument_list|)
expr_stmt|;
name|EntrySorter
name|sInMem
init|=
name|inMem
operator|.
name|getSorter
argument_list|(
name|comp
argument_list|)
decl_stmt|;
comment|// Start looking at changes.
name|scanPreamble
argument_list|(
name|inMem
argument_list|,
name|inTemp
argument_list|,
name|onDisk
argument_list|)
expr_stmt|;
name|scanStrings
argument_list|(
name|inMem
argument_list|,
name|inTemp
argument_list|,
name|onDisk
argument_list|)
expr_stmt|;
name|scanEntries
argument_list|(
name|sInMem
argument_list|,
name|sInTemp
argument_list|,
name|sOnDisk
argument_list|)
expr_stmt|;
name|scanGroups
argument_list|(
name|mdInMem
argument_list|,
name|mdInTemp
argument_list|,
name|mdOnDisk
argument_list|)
expr_stmt|;
comment|//System.out.println("Time used: "+(System.currentTimeMillis()-startTime));
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|changesFound ()
specifier|public
name|boolean
name|changesFound
parameter_list|()
block|{
return|return
name|changes
operator|.
name|getChildCount
argument_list|()
operator|>
literal|0
return|;
block|}
DECL|method|displayResult ()
specifier|public
name|void
name|displayResult
parameter_list|()
block|{
if|if
condition|(
name|changes
operator|.
name|getChildCount
argument_list|()
operator|>
literal|0
condition|)
block|{
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
operator|new
name|Runnable
argument_list|()
block|{
specifier|public
name|void
name|run
parameter_list|()
block|{
name|ChangeDisplayDialog
name|dial
init|=
operator|new
name|ChangeDisplayDialog
argument_list|(
name|frame
argument_list|,
name|panel
argument_list|,
name|changes
argument_list|)
decl_stmt|;
name|Util
operator|.
name|placeDialog
argument_list|(
name|dial
argument_list|,
name|frame
argument_list|)
expr_stmt|;
name|dial
operator|.
name|show
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"No actual changes found."
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"External changes"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|INFORMATION_MESSAGE
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|scanEntries (EntrySorter mem, EntrySorter tmp, EntrySorter disk)
specifier|private
name|void
name|scanEntries
parameter_list|(
name|EntrySorter
name|mem
parameter_list|,
name|EntrySorter
name|tmp
parameter_list|,
name|EntrySorter
name|disk
parameter_list|)
block|{
comment|// Create pointers that are incremented as the entries of each base are used in
comment|// successive order from the beginning. Entries "further down" in the "disk" base
comment|// can also be matched.
name|int
name|piv1
init|=
literal|0
decl_stmt|,
name|piv2
init|=
literal|0
decl_stmt|;
comment|// Create a HashSet where we can put references to entry numbers in the "disk"
comment|// database that we have matched. This is to avoid matching them twice.
name|HashSet
name|used
init|=
operator|new
name|HashSet
argument_list|(
name|disk
operator|.
name|getEntryCount
argument_list|()
argument_list|)
decl_stmt|;
name|HashSet
name|notMatched
init|=
operator|new
name|HashSet
argument_list|(
name|tmp
operator|.
name|getEntryCount
argument_list|()
argument_list|)
decl_stmt|;
comment|// Loop through the entries of the "mem" database, looking for exact matches in the "disk" one.
comment|// We must finish scanning for exact matches before looking for near matches, to avoid an exact
comment|// match being "stolen" from another entry.
name|mainLoop
label|:
for|for
control|(
name|piv1
operator|=
literal|0
init|;
name|piv1
operator|<
name|tmp
operator|.
name|getEntryCount
argument_list|()
condition|;
name|piv1
operator|++
control|)
block|{
comment|//System.out.println(">>> "+piv1+"\t"+tmp.getEntryCount());
comment|// First check if the similarly placed entry in the other base matches exactly.
name|double
name|comp
init|=
operator|-
literal|1
decl_stmt|;
comment|// (if there are not any entries left in the "disk" database, comp will stay at -1,
comment|// and this entry will be marked as nonmatched).
if|if
condition|(
operator|!
name|used
operator|.
name|contains
argument_list|(
literal|""
operator|+
name|piv2
argument_list|)
operator|&&
operator|(
name|piv2
operator|<
name|disk
operator|.
name|getEntryCount
argument_list|()
operator|)
condition|)
block|{
name|comp
operator|=
name|Util
operator|.
name|compareEntriesStrictly
argument_list|(
name|tmp
operator|.
name|getEntryAt
argument_list|(
name|piv1
argument_list|)
argument_list|,
name|disk
operator|.
name|getEntryAt
argument_list|(
name|piv2
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|comp
operator|>
literal|1
condition|)
block|{
name|used
operator|.
name|add
argument_list|(
literal|""
operator|+
name|piv2
argument_list|)
expr_stmt|;
name|piv2
operator|++
expr_stmt|;
continue|continue
name|mainLoop
continue|;
block|}
comment|// No? Then check if another entry matches exactly.
if|if
condition|(
name|piv2
operator|<
name|disk
operator|.
name|getEntryCount
argument_list|()
operator|-
literal|1
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
name|piv2
operator|+
literal|1
init|;
name|i
operator|<
name|disk
operator|.
name|getEntryCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
operator|!
name|used
operator|.
name|contains
argument_list|(
literal|""
operator|+
name|i
argument_list|)
condition|)
name|comp
operator|=
name|Util
operator|.
name|compareEntriesStrictly
argument_list|(
name|tmp
operator|.
name|getEntryAt
argument_list|(
name|piv1
argument_list|)
argument_list|,
name|disk
operator|.
name|getEntryAt
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
else|else
name|comp
operator|=
operator|-
literal|1
expr_stmt|;
if|if
condition|(
name|comp
operator|>
literal|1
condition|)
block|{
name|used
operator|.
name|add
argument_list|(
literal|""
operator|+
name|i
argument_list|)
expr_stmt|;
continue|continue
name|mainLoop
continue|;
block|}
block|}
block|}
comment|// No? Add this entry to the list of nonmatched entries.
name|notMatched
operator|.
name|add
argument_list|(
operator|new
name|Integer
argument_list|(
name|piv1
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Now we've found all exact matches, look through the remaining entries, looking
comment|// for close matches.
if|if
condition|(
name|notMatched
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
comment|//Util.pr("Could not find exact match for "+notMatched.size()+" entries.");
name|fuzzyLoop
label|:
for|for
control|(
name|Iterator
name|it
init|=
name|notMatched
operator|.
name|iterator
argument_list|()
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Integer
name|integ
init|=
operator|(
name|Integer
operator|)
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|piv1
operator|=
name|integ
operator|.
name|intValue
argument_list|()
expr_stmt|;
comment|//Util.printEntry(mem.getEntryAt(piv1));
comment|// These two variables will keep track of which entry most closely matches the
comment|// one we're looking at, in case none matches completely.
name|int
name|bestMatchI
init|=
operator|-
literal|1
decl_stmt|;
name|double
name|bestMatch
init|=
literal|0
decl_stmt|;
name|double
name|comp
init|=
operator|-
literal|1
decl_stmt|;
if|if
condition|(
name|piv2
operator|<
name|disk
operator|.
name|getEntryCount
argument_list|()
operator|-
literal|1
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
name|piv2
init|;
name|i
operator|<
name|disk
operator|.
name|getEntryCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
comment|//Util.pr("This one? "+i);
if|if
condition|(
operator|!
name|used
operator|.
name|contains
argument_list|(
literal|""
operator|+
name|i
argument_list|)
condition|)
block|{
comment|//Util.pr("Fuzzy matching for entry: "+piv1+" - "+i);
name|comp
operator|=
name|Util
operator|.
name|compareEntriesStrictly
argument_list|(
name|tmp
operator|.
name|getEntryAt
argument_list|(
name|piv1
argument_list|)
argument_list|,
name|disk
operator|.
name|getEntryAt
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
name|comp
operator|=
operator|-
literal|1
expr_stmt|;
if|if
condition|(
name|comp
operator|>
name|bestMatch
condition|)
block|{
name|bestMatch
operator|=
name|comp
expr_stmt|;
name|bestMatchI
operator|=
name|i
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|bestMatch
operator|>
name|MATCH_THRESHOLD
condition|)
block|{
name|used
operator|.
name|add
argument_list|(
literal|""
operator|+
name|bestMatchI
argument_list|)
expr_stmt|;
name|it
operator|.
name|remove
argument_list|()
expr_stmt|;
name|EntryChange
name|ec
init|=
operator|new
name|EntryChange
argument_list|(
name|bestFit
argument_list|(
name|tmp
argument_list|,
name|mem
argument_list|,
name|piv1
argument_list|)
argument_list|,
name|tmp
operator|.
name|getEntryAt
argument_list|(
name|piv1
argument_list|)
argument_list|,
name|disk
operator|.
name|getEntryAt
argument_list|(
name|bestMatchI
argument_list|)
argument_list|)
decl_stmt|;
name|changes
operator|.
name|add
argument_list|(
name|ec
argument_list|)
expr_stmt|;
comment|// Create an undo edit to represent this change:
comment|//NamedCompound ce = new NamedCompound("Modified entry");
comment|//ce.addEdit(new UndoableRemoveEntry(inMem, disk.getEntryAt(bestMatchI), panel));
comment|//ce.addEdit(new UndoableInsertEntry(inMem, tmp.getEntryAt(piv1), panel));
comment|//ce.end();
comment|//changes.add(ce);
comment|//System.out.println("Possible match for entry:");
comment|//Util.printEntry(mem.getEntryAt(piv1));
comment|//System.out.println("----------------------------------------------");
comment|//Util.printEntry(disk.getEntryAt(bestMatchI));
block|}
else|else
block|{
name|EntryDeleteChange
name|ec
init|=
operator|new
name|EntryDeleteChange
argument_list|(
name|bestFit
argument_list|(
name|tmp
argument_list|,
name|mem
argument_list|,
name|piv1
argument_list|)
argument_list|,
name|tmp
operator|.
name|getEntryAt
argument_list|(
name|piv1
argument_list|)
argument_list|)
decl_stmt|;
name|changes
operator|.
name|add
argument_list|(
name|ec
argument_list|)
expr_stmt|;
comment|/*NamedCompound ce = new NamedCompound("Removed entry");           ce.addEdit(new UndoableInsertEntry(inMem, tmp.getEntryAt(piv1), panel));           ce.end();           changes.add(ce);*/
block|}
block|}
block|}
comment|// Finally, look if there are still untouched entries in the disk database. These
comment|// mayhave been added.
if|if
condition|(
name|used
operator|.
name|size
argument_list|()
operator|<
name|disk
operator|.
name|getEntryCount
argument_list|()
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|disk
operator|.
name|getEntryCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
operator|!
name|used
operator|.
name|contains
argument_list|(
literal|""
operator|+
name|i
argument_list|)
condition|)
block|{
comment|// See if there is an identical dupe in the mem database:
name|boolean
name|hasAlready
init|=
literal|false
decl_stmt|;
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|mem
operator|.
name|getEntryCount
argument_list|()
condition|;
name|j
operator|++
control|)
block|{
if|if
condition|(
name|Util
operator|.
name|compareEntriesStrictly
argument_list|(
name|mem
operator|.
name|getEntryAt
argument_list|(
name|j
argument_list|)
argument_list|,
name|disk
operator|.
name|getEntryAt
argument_list|(
name|i
argument_list|)
argument_list|)
operator|>=
literal|1
condition|)
block|{
name|hasAlready
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
operator|!
name|hasAlready
condition|)
block|{
name|EntryAddChange
name|ec
init|=
operator|new
name|EntryAddChange
argument_list|(
name|disk
operator|.
name|getEntryAt
argument_list|(
name|i
argument_list|)
argument_list|)
decl_stmt|;
name|changes
operator|.
name|add
argument_list|(
name|ec
argument_list|)
expr_stmt|;
block|}
comment|/*NamedCompound ce = new NamedCompound("Added entry");           ce.addEdit(new UndoableRemoveEntry(inMem, disk.getEntryAt(i), panel));           ce.end();           changes.add(ce);*/
block|}
block|}
comment|//System.out.println("Suspected new entries in file: "+(disk.getEntryCount()-used.size()));
block|}
block|}
comment|/**      * Finds the entry in neu best fitting the specified entry in old. If no entries get a score      * above zero, an entry is still returned.      * @param old EntrySorter      * @param neu EntrySorter      * @param index int      * @return BibtexEntry      */
DECL|method|bestFit (EntrySorter old, EntrySorter neu, int index)
specifier|private
name|BibtexEntry
name|bestFit
parameter_list|(
name|EntrySorter
name|old
parameter_list|,
name|EntrySorter
name|neu
parameter_list|,
name|int
name|index
parameter_list|)
block|{
name|double
name|comp
init|=
operator|-
literal|1
decl_stmt|;
name|int
name|found
init|=
literal|0
decl_stmt|;
name|loop
label|:
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|neu
operator|.
name|getEntryCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|double
name|res
init|=
name|Util
operator|.
name|compareEntriesStrictly
argument_list|(
name|old
operator|.
name|getEntryAt
argument_list|(
name|index
argument_list|)
argument_list|,
name|neu
operator|.
name|getEntryAt
argument_list|(
name|i
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|res
operator|>
name|comp
condition|)
block|{
name|comp
operator|=
name|res
expr_stmt|;
name|found
operator|=
name|i
expr_stmt|;
block|}
if|if
condition|(
name|comp
operator|>
literal|1
condition|)
break|break
name|loop
break|;
block|}
return|return
name|neu
operator|.
name|getEntryAt
argument_list|(
name|found
argument_list|)
return|;
block|}
DECL|method|scanPreamble (BibtexDatabase inMem, BibtexDatabase onTmp, BibtexDatabase onDisk)
specifier|private
name|void
name|scanPreamble
parameter_list|(
name|BibtexDatabase
name|inMem
parameter_list|,
name|BibtexDatabase
name|onTmp
parameter_list|,
name|BibtexDatabase
name|onDisk
parameter_list|)
block|{
name|String
name|mem
init|=
name|inMem
operator|.
name|getPreamble
argument_list|()
decl_stmt|,
name|tmp
init|=
name|onTmp
operator|.
name|getPreamble
argument_list|()
decl_stmt|,
name|disk
init|=
name|onDisk
operator|.
name|getPreamble
argument_list|()
decl_stmt|;
if|if
condition|(
name|tmp
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
operator|(
name|disk
operator|==
literal|null
operator|)
operator|||
operator|!
name|tmp
operator|.
name|equals
argument_list|(
name|disk
argument_list|)
condition|)
name|changes
operator|.
name|add
argument_list|(
operator|new
name|PreambleChange
argument_list|(
name|tmp
argument_list|,
name|mem
argument_list|,
name|disk
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|(
name|disk
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|disk
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|changes
operator|.
name|add
argument_list|(
operator|new
name|PreambleChange
argument_list|(
name|tmp
argument_list|,
name|mem
argument_list|,
name|disk
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|scanStrings (BibtexDatabase inMem, BibtexDatabase onTmp, BibtexDatabase onDisk)
specifier|private
name|void
name|scanStrings
parameter_list|(
name|BibtexDatabase
name|inMem
parameter_list|,
name|BibtexDatabase
name|onTmp
parameter_list|,
name|BibtexDatabase
name|onDisk
parameter_list|)
block|{
name|int
name|nTmp
init|=
name|onTmp
operator|.
name|getStringCount
argument_list|()
decl_stmt|,
name|nDisk
init|=
name|onDisk
operator|.
name|getStringCount
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|nTmp
operator|==
literal|0
operator|)
operator|&&
operator|(
name|nDisk
operator|==
literal|0
operator|)
condition|)
return|return;
name|HashSet
name|used
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
name|HashSet
name|usedInMem
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
name|HashSet
name|notMatched
init|=
operator|new
name|HashSet
argument_list|(
name|onTmp
operator|.
name|getStringCount
argument_list|()
argument_list|)
decl_stmt|;
comment|// First try to match by string names.
comment|//int piv2 = -1;
name|mainLoop
label|:
for|for
control|(
name|Iterator
name|i
init|=
name|onTmp
operator|.
name|getStringKeySet
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Object
name|tmpId
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|BibtexString
name|tmp
init|=
name|onTmp
operator|.
name|getString
argument_list|(
name|tmpId
argument_list|)
decl_stmt|;
comment|//      for (int j=piv2+1; j<nDisk; j++)
for|for
control|(
name|Iterator
name|j
init|=
name|onDisk
operator|.
name|getStringKeySet
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|j
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Object
name|diskId
init|=
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|used
operator|.
name|contains
argument_list|(
name|diskId
argument_list|)
condition|)
block|{
name|BibtexString
name|disk
init|=
name|onDisk
operator|.
name|getString
argument_list|(
name|diskId
argument_list|)
decl_stmt|;
if|if
condition|(
name|disk
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|tmp
operator|.
name|getName
argument_list|()
argument_list|)
condition|)
block|{
comment|// We have found a string with a matching name.
if|if
condition|(
operator|(
name|tmp
operator|.
name|getContent
argument_list|()
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|tmp
operator|.
name|getContent
argument_list|()
operator|.
name|equals
argument_list|(
name|disk
operator|.
name|getContent
argument_list|()
argument_list|)
condition|)
block|{
comment|// But they have nonmatching contents, so we've found a change.
name|BibtexString
name|mem
init|=
name|findString
argument_list|(
name|inMem
argument_list|,
name|tmp
operator|.
name|getName
argument_list|()
argument_list|,
name|usedInMem
argument_list|)
decl_stmt|;
if|if
condition|(
name|mem
operator|!=
literal|null
condition|)
name|changes
operator|.
name|add
argument_list|(
operator|new
name|StringChange
argument_list|(
name|mem
argument_list|,
name|tmp
operator|.
name|getName
argument_list|()
argument_list|,
name|mem
operator|.
name|getContent
argument_list|()
argument_list|,
name|tmp
operator|.
name|getContent
argument_list|()
argument_list|,
name|disk
operator|.
name|getContent
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
else|else
name|changes
operator|.
name|add
argument_list|(
operator|new
name|StringChange
argument_list|(
literal|null
argument_list|,
name|tmp
operator|.
name|getName
argument_list|()
argument_list|,
literal|null
argument_list|,
name|tmp
operator|.
name|getContent
argument_list|()
argument_list|,
name|disk
operator|.
name|getContent
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|used
operator|.
name|add
argument_list|(
name|diskId
argument_list|)
expr_stmt|;
comment|//if (j==piv2)
comment|//  piv2++;
continue|continue
name|mainLoop
continue|;
block|}
block|}
block|}
comment|// If we get here, there was no match for this string.
name|notMatched
operator|.
name|add
argument_list|(
name|tmp
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// See if we can detect a name change for those entries that we couldn't match.
if|if
condition|(
name|notMatched
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|notMatched
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Object
name|nmId
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|BibtexString
name|tmp
init|=
name|onTmp
operator|.
name|getString
argument_list|(
name|nmId
argument_list|)
decl_stmt|;
comment|// If we get to this point, we found no string with matching name. See if we
comment|// can find one with matching content.
name|String
name|tmpContent
init|=
name|tmp
operator|.
name|getContent
argument_list|()
decl_stmt|;
comment|//for (Iterator i=onTmp.getStringKeySet().iterator(); i.hasNext();) {
for|for
control|(
name|Iterator
name|j
init|=
name|onDisk
operator|.
name|getStringKeySet
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|j
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Object
name|diskId
init|=
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
comment|//for (int j = piv2 + 1; j< nDisk; j++)
if|if
condition|(
operator|!
name|used
operator|.
name|contains
argument_list|(
name|diskId
argument_list|)
condition|)
block|{
name|BibtexString
name|disk
init|=
name|onDisk
operator|.
name|getString
argument_list|(
name|diskId
argument_list|)
decl_stmt|;
comment|//System.out.println("Cand: "+disk.getName());
if|if
condition|(
name|disk
operator|.
name|getContent
argument_list|()
operator|.
name|equals
argument_list|(
name|tmp
operator|.
name|getContent
argument_list|()
argument_list|)
condition|)
block|{
comment|// We have found a string with the same content. It cannot have the same
comment|// name, or we would have found it above.
comment|// Try to find the matching one in memory:
name|BibtexString
name|bsMem
init|=
literal|null
decl_stmt|;
name|findInMem
label|:
for|for
control|(
name|Iterator
name|k
init|=
name|inMem
operator|.
name|getStringKeySet
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|k
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Object
name|memId
init|=
name|k
operator|.
name|next
argument_list|()
decl_stmt|;
comment|//for (int k = 0; k< inMem.getStringCount(); k++) {
name|BibtexString
name|bsMem_cand
init|=
name|inMem
operator|.
name|getString
argument_list|(
name|memId
argument_list|)
decl_stmt|;
if|if
condition|(
name|bsMem_cand
operator|.
name|getContent
argument_list|()
operator|.
name|equals
argument_list|(
name|disk
operator|.
name|getContent
argument_list|()
argument_list|)
operator|&&
operator|!
name|usedInMem
operator|.
name|contains
argument_list|(
name|memId
argument_list|)
condition|)
block|{
name|usedInMem
operator|.
name|add
argument_list|(
name|memId
argument_list|)
expr_stmt|;
name|bsMem
operator|=
name|bsMem_cand
expr_stmt|;
break|break
name|findInMem
break|;
block|}
block|}
name|changes
operator|.
name|add
argument_list|(
operator|new
name|StringNameChange
argument_list|(
name|bsMem
argument_list|,
name|bsMem
operator|.
name|getName
argument_list|()
argument_list|,
name|tmp
operator|.
name|getName
argument_list|()
argument_list|,
name|disk
operator|.
name|getName
argument_list|()
argument_list|,
name|tmp
operator|.
name|getContent
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
name|used
operator|.
name|add
argument_list|(
name|diskId
argument_list|)
expr_stmt|;
comment|//System.out.println(onDisk.getString(diskId).getName());
block|}
block|}
block|}
block|}
block|}
if|if
condition|(
name|notMatched
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
comment|// Still one or more non-matched strings. So they must have been removed.
for|for
control|(
name|Iterator
name|i
init|=
name|notMatched
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Object
name|nmId
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|BibtexString
name|tmp
init|=
name|onTmp
operator|.
name|getString
argument_list|(
name|nmId
argument_list|)
decl_stmt|;
name|BibtexString
name|mem
init|=
name|findString
argument_list|(
name|inMem
argument_list|,
name|tmp
operator|.
name|getName
argument_list|()
argument_list|,
name|usedInMem
argument_list|)
decl_stmt|;
if|if
condition|(
name|mem
operator|!=
literal|null
condition|)
block|{
comment|// The removed string is not removed from the mem version.
name|changes
operator|.
name|add
argument_list|(
operator|new
name|StringRemoveChange
argument_list|(
name|tmp
argument_list|,
name|mem
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|//System.out.println(used.size());
comment|// Finally, see if there are remaining strings in the disk database. They
comment|// must have been added.
for|for
control|(
name|Iterator
name|i
init|=
name|onDisk
operator|.
name|getStringKeySet
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Object
name|diskId
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|used
operator|.
name|contains
argument_list|(
name|diskId
argument_list|)
condition|)
block|{
name|BibtexString
name|disk
init|=
name|onDisk
operator|.
name|getString
argument_list|(
name|diskId
argument_list|)
decl_stmt|;
comment|//System.out.println(disk.getName());
name|used
operator|.
name|add
argument_list|(
name|diskId
argument_list|)
expr_stmt|;
name|changes
operator|.
name|add
argument_list|(
operator|new
name|StringAddChange
argument_list|(
name|disk
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|findString (BibtexDatabase base, String name, HashSet used)
specifier|private
name|BibtexString
name|findString
parameter_list|(
name|BibtexDatabase
name|base
parameter_list|,
name|String
name|name
parameter_list|,
name|HashSet
name|used
parameter_list|)
block|{
if|if
condition|(
operator|!
name|base
operator|.
name|hasStringLabel
argument_list|(
name|name
argument_list|)
condition|)
return|return
literal|null
return|;
for|for
control|(
name|Iterator
name|i
init|=
name|base
operator|.
name|getStringKeySet
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Object
name|key
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|BibtexString
name|bs
init|=
name|base
operator|.
name|getString
argument_list|(
name|key
argument_list|)
decl_stmt|;
if|if
condition|(
name|bs
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|name
argument_list|)
operator|&&
operator|!
name|used
operator|.
name|contains
argument_list|(
name|key
argument_list|)
condition|)
block|{
name|used
operator|.
name|add
argument_list|(
name|key
argument_list|)
expr_stmt|;
return|return
name|bs
return|;
block|}
block|}
return|return
literal|null
return|;
block|}
comment|/**      * This method only detects wheter a change took place or not. It does not      * determine the type of change. This would be possible, but difficult to do      * properly, so I rather only report the change.      */
DECL|method|scanGroups (MetaData inMem, MetaData onTmp, MetaData onDisk)
specifier|public
name|void
name|scanGroups
parameter_list|(
name|MetaData
name|inMem
parameter_list|,
name|MetaData
name|onTmp
parameter_list|,
name|MetaData
name|onDisk
parameter_list|)
block|{
specifier|final
name|GroupTreeNode
name|groupsMem
init|=
name|inMem
operator|.
name|getGroups
argument_list|()
decl_stmt|;
specifier|final
name|GroupTreeNode
name|groupsTmp
init|=
name|onTmp
operator|.
name|getGroups
argument_list|()
decl_stmt|;
specifier|final
name|GroupTreeNode
name|groupsDisk
init|=
name|onDisk
operator|.
name|getGroups
argument_list|()
decl_stmt|;
if|if
condition|(
name|groupsTmp
operator|==
literal|null
operator|&&
name|groupsDisk
operator|==
literal|null
condition|)
return|return;
if|if
condition|(
operator|(
name|groupsTmp
operator|!=
literal|null
operator|&&
name|groupsDisk
operator|==
literal|null
operator|)
operator|||
operator|(
name|groupsTmp
operator|==
literal|null
operator|&&
name|groupsDisk
operator|!=
literal|null
operator|)
condition|)
block|{
name|changes
operator|.
name|add
argument_list|(
operator|new
name|GroupChange
argument_list|(
name|groupsDisk
argument_list|)
argument_list|)
expr_stmt|;
return|return;
block|}
if|if
condition|(
name|groupsTmp
operator|.
name|equals
argument_list|(
name|groupsDisk
argument_list|)
condition|)
return|return;
name|changes
operator|.
name|add
argument_list|(
operator|new
name|GroupChange
argument_list|(
name|groupsDisk
argument_list|)
argument_list|)
expr_stmt|;
return|return;
comment|//
comment|//        if (((vOnTmp == null) || (vOnTmp.size()==0))&& ((vOnDisk == null) || (vOnDisk.size()==0))) {
comment|//            // No groups defined in either the tmp or disk version.
comment|//            return;
comment|//        }
comment|//
comment|//        // To avoid checking for null all the time, make empty vectors to replace null refs. We clone
comment|//        // non-null vectors so we can remove the elements as we finish with them.
comment|//        if (vOnDisk == null)
comment|//            vOnDisk = new Vector(0);
comment|//        else
comment|//            vOnDisk = (Vector)vOnDisk.clone();
comment|//        if (vOnTmp == null)
comment|//            vOnTmp = new Vector(0);
comment|//        else
comment|//            vOnTmp = (Vector)vOnTmp.clone();
comment|//        if (vInMem == null)
comment|//            vInMem = new Vector(0);
comment|//        else
comment|//            vInMem = (Vector)vInMem.clone();
comment|//
comment|//        // If the tmp version has groups, iterate through these and compare with disk version:
comment|//        while (vOnTmp.size()>= 1) {
comment|//            AbstractGroup group = (AbstractGroup)vOnTmp.firstElement();
comment|//            vOnTmp.removeElementAt(0);
comment|//            int pos = GroupSelector.findGroupByName(vOnDisk,group.getName());
comment|//            if (pos == -1) {
comment|//                // Couldn't find the group.
comment|//                changes.add(new GroupAddOrRemove(group, false));
comment|//            } else {
comment|//                AbstractGroup diskGroup = (AbstractGroup)vOnDisk.elementAt(pos);
comment|//
comment|//                if (!diskGroup.equals(group)) {
comment|//                    // Group has changed.
comment|//                    changes.add(new GroupChange(inMem, group, diskGroup));
comment|//                }
comment|//
comment|//                // Remove this group, since it's been accounted for.
comment|//                vOnDisk.remove(pos);
comment|//            }
comment|//        }
comment|//
comment|//        // If there are entries left in the disk version, these must have been added.
comment|//        while (vOnDisk.size()>= 1) {
comment|//            AbstractGroup group = (AbstractGroup)vOnDisk.firstElement();
comment|//            vOnDisk.removeElementAt(0);
comment|//            changes.add(new GroupAddOrRemove(group, true));
comment|//        }
block|}
block|}
end_class

end_unit

