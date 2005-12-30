begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.journals
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|journals
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
name|util
operator|.
name|CaseChanger
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
name|FieldEditor
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
name|Globals
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
name|EntryEditor
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
name|BibtexEntry
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
name|UndoableFieldChange
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
name|table
operator|.
name|DefaultTableModel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|table
operator|.
name|TableModel
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
name|javax
operator|.
name|swing
operator|.
name|undo
operator|.
name|CompoundEdit
import|;
end_import

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
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionListener
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionEvent
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
import|;
end_import

begin_comment
comment|/**  * Created by IntelliJ IDEA.  * User: alver  * Date: Sep 16, 2005  * Time: 10:49:08 PM  * To browseOld this template use File | Settings | File Templates.  */
end_comment

begin_class
DECL|class|JournalAbbreviations
specifier|public
class|class
name|JournalAbbreviations
block|{
DECL|field|TOOLTIPTEXT
specifier|static
name|String
name|TOOLTIPTEXT
init|=
literal|"<HTML>"
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"Switches between full and abbreviated journal name"
argument_list|)
operator|+
literal|"<BR>"
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"if the journal name is known. Go to (...............)"
argument_list|)
operator|+
literal|"</HTML>"
decl_stmt|;
DECL|field|fullNameKeyed
name|TreeMap
name|fullNameKeyed
init|=
operator|new
name|TreeMap
argument_list|()
decl_stmt|;
DECL|field|abbrNameKeyed
name|HashMap
name|abbrNameKeyed
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
DECL|field|abbrNoDotsToAbbr
name|HashMap
name|abbrNoDotsToAbbr
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
DECL|field|all
name|TreeMap
name|all
init|=
operator|new
name|TreeMap
argument_list|()
decl_stmt|;
DECL|field|caseChanger
name|CaseChanger
name|caseChanger
init|=
operator|new
name|CaseChanger
argument_list|()
decl_stmt|;
DECL|method|JournalAbbreviations ()
specifier|public
name|JournalAbbreviations
parameter_list|()
block|{      }
DECL|method|JournalAbbreviations (String resource)
specifier|public
name|JournalAbbreviations
parameter_list|(
name|String
name|resource
parameter_list|)
block|{
name|readJournalList
argument_list|(
name|resource
argument_list|)
expr_stmt|;
block|}
DECL|method|JournalAbbreviations (File file)
specifier|public
name|JournalAbbreviations
parameter_list|(
name|File
name|file
parameter_list|)
throws|throws
name|FileNotFoundException
block|{
name|readJournalList
argument_list|(
name|file
argument_list|)
expr_stmt|;
block|}
comment|/**      * Get an iterator for the known journals in alphabetical order.      * @return Iterator for journal full names      */
DECL|method|fullNameIterator ()
specifier|public
name|Iterator
name|fullNameIterator
parameter_list|()
block|{
return|return
name|fullNameKeyed
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
return|;
block|}
DECL|method|isKnownName (String journalName)
specifier|public
name|boolean
name|isKnownName
parameter_list|(
name|String
name|journalName
parameter_list|)
block|{
name|String
name|s
init|=
name|journalName
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
return|return
operator|(
operator|(
name|fullNameKeyed
operator|.
name|get
argument_list|(
name|s
argument_list|)
operator|!=
literal|null
operator|)
operator|||
operator|(
name|abbrNameKeyed
operator|.
name|get
argument_list|(
name|s
argument_list|)
operator|!=
literal|null
operator|)
operator|||
operator|(
name|abbrNoDotsToAbbr
operator|.
name|get
argument_list|(
name|s
argument_list|)
operator|!=
literal|null
operator|)
operator|)
return|;
block|}
DECL|method|isAbbreviatedName (String journalName)
specifier|public
name|boolean
name|isAbbreviatedName
parameter_list|(
name|String
name|journalName
parameter_list|)
block|{
name|String
name|s
init|=
name|journalName
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
return|return
operator|(
operator|(
name|abbrNameKeyed
operator|.
name|get
argument_list|(
name|s
argument_list|)
operator|!=
literal|null
operator|)
operator|||
operator|(
name|abbrNoDotsToAbbr
operator|.
name|get
argument_list|(
name|s
argument_list|)
operator|!=
literal|null
operator|)
operator|)
return|;
block|}
DECL|method|dotsToNodots (String name)
specifier|public
name|String
name|dotsToNodots
parameter_list|(
name|String
name|name
parameter_list|)
block|{
return|return
name|name
operator|.
name|replaceAll
argument_list|(
literal|"\\."
argument_list|,
literal|" "
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"  "
argument_list|,
literal|" "
argument_list|)
operator|.
name|trim
argument_list|()
return|;
block|}
comment|/**      * Attempts to get the abbreviated name of the journal given. Returns null if no      * abbreviated name is known.      * @param journalName The journal name to abbreviate.      * @param withDots True if the abbreviation should have dots.      * if only the first character should be.      * @return The abbreviated name, or null if it couldn't be found.      */
DECL|method|getAbbreviatedName (String journalName, boolean withDots)
specifier|public
name|String
name|getAbbreviatedName
parameter_list|(
name|String
name|journalName
parameter_list|,
name|boolean
name|withDots
parameter_list|)
block|{
name|String
name|s
init|=
name|journalName
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
name|String
name|abbr
decl_stmt|;
if|if
condition|(
name|fullNameKeyed
operator|.
name|containsKey
argument_list|(
name|s
argument_list|)
condition|)
block|{
name|abbr
operator|=
operator|(
name|String
operator|)
name|fullNameKeyed
operator|.
name|get
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|abbrNameKeyed
operator|.
name|containsKey
argument_list|(
name|s
argument_list|)
condition|)
block|{
name|abbr
operator|=
name|journalName
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|abbrNoDotsToAbbr
operator|.
name|containsKey
argument_list|(
name|s
argument_list|)
condition|)
block|{
name|abbr
operator|=
operator|(
name|String
operator|)
name|abbrNoDotsToAbbr
operator|.
name|get
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
else|else
return|return
literal|null
return|;
if|if
condition|(
operator|!
name|withDots
condition|)
block|{
name|abbr
operator|=
name|dotsToNodots
argument_list|(
name|abbr
argument_list|)
expr_stmt|;
block|}
return|return
name|abbr
return|;
block|}
comment|/**      * Attempts to get the full name of the abbreviation given. Returns null if no      * full name is known.      * @param journalName The abbreviation to resolve.      * @return The full name, or null if it couldn't be found.      */
DECL|method|getFullName (String journalName)
specifier|public
name|String
name|getFullName
parameter_list|(
name|String
name|journalName
parameter_list|)
block|{
comment|// Normalize name first:
name|String
name|s
init|=
name|getAbbreviatedName
argument_list|(
name|journalName
argument_list|,
literal|true
argument_list|)
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
name|Object
name|o
init|=
name|abbrNameKeyed
operator|.
name|get
argument_list|(
name|s
argument_list|)
decl_stmt|;
if|if
condition|(
name|o
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|fullNameKeyed
operator|.
name|containsKey
argument_list|(
name|s
argument_list|)
condition|)
name|o
operator|=
name|s
expr_stmt|;
else|else
return|return
literal|null
return|;
block|}
name|s
operator|=
operator|(
name|String
operator|)
name|o
expr_stmt|;
return|return
name|caseChanger
operator|.
name|changeCase
argument_list|(
name|s
argument_list|,
name|CaseChanger
operator|.
name|UPPER_EACH_FIRST
argument_list|)
return|;
block|}
DECL|method|readJournalList (String resourceFileName)
specifier|public
name|void
name|readJournalList
parameter_list|(
name|String
name|resourceFileName
parameter_list|)
block|{
name|URL
name|url
init|=
name|JournalAbbreviations
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|resourceFileName
argument_list|)
decl_stmt|;
try|try
block|{
name|readJournalList
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
name|url
operator|.
name|openStream
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|FileNotFoundException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|readJournalList (File file)
specifier|public
name|void
name|readJournalList
parameter_list|(
name|File
name|file
parameter_list|)
throws|throws
name|FileNotFoundException
block|{
name|readJournalList
argument_list|(
operator|new
name|FileReader
argument_list|(
name|file
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Read the given file, which should contain a list of journal names and their      * abbreviations. Each line should be formatted as: "Full Journal Name=Abbr. Journal Name"      * @param in      */
DECL|method|readJournalList (Reader in)
specifier|public
name|void
name|readJournalList
parameter_list|(
name|Reader
name|in
parameter_list|)
throws|throws
name|FileNotFoundException
block|{
name|BufferedReader
name|reader
init|=
operator|new
name|BufferedReader
argument_list|(
name|in
argument_list|)
decl_stmt|;
try|try
block|{
name|String
name|line
decl_stmt|;
while|while
condition|(
operator|(
name|line
operator|=
name|reader
operator|.
name|readLine
argument_list|()
operator|)
operator|!=
literal|null
condition|)
block|{
comment|//System.out.println(line);
if|if
condition|(
name|line
operator|.
name|startsWith
argument_list|(
literal|"#"
argument_list|)
condition|)
continue|continue;
name|String
index|[]
name|parts
init|=
name|line
operator|.
name|split
argument_list|(
literal|"="
argument_list|)
decl_stmt|;
if|if
condition|(
name|parts
operator|.
name|length
operator|==
literal|2
condition|)
block|{
name|String
name|fullName
init|=
name|parts
index|[
literal|0
index|]
operator|.
name|trim
argument_list|()
decl_stmt|;
name|String
name|fullNameLC
init|=
name|fullName
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
name|String
name|abbrName
init|=
name|parts
index|[
literal|1
index|]
operator|.
name|trim
argument_list|()
decl_stmt|;
if|if
condition|(
name|abbrName
operator|.
name|indexOf
argument_list|(
literal|';'
argument_list|)
operator|>=
literal|0
condition|)
block|{
name|String
index|[]
name|restParts
init|=
name|abbrName
operator|.
name|split
argument_list|(
literal|";"
argument_list|)
decl_stmt|;
name|abbrName
operator|=
name|restParts
index|[
literal|0
index|]
expr_stmt|;
block|}
name|String
name|abbrNameLC
init|=
name|abbrName
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
name|String
name|abbrNoDots
init|=
name|dotsToNodots
argument_list|(
name|abbrName
argument_list|)
decl_stmt|;
name|String
name|abbrNoDotsLC
init|=
name|abbrNoDots
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
comment|//System.out.println(abbrNoDots);
if|if
condition|(
operator|(
name|fullName
operator|.
name|length
argument_list|()
operator|>
literal|0
operator|)
operator|&&
operator|(
name|abbrName
operator|.
name|length
argument_list|()
operator|>
literal|0
operator|)
condition|)
block|{
comment|//System.out.println("'"+fullName+"' : '"+abbrNoDots+"'");
name|fullNameKeyed
operator|.
name|put
argument_list|(
name|fullNameLC
argument_list|,
name|abbrName
argument_list|)
expr_stmt|;
name|abbrNameKeyed
operator|.
name|put
argument_list|(
name|abbrNameLC
argument_list|,
name|fullName
argument_list|)
expr_stmt|;
name|abbrNoDotsToAbbr
operator|.
name|put
argument_list|(
name|abbrNoDotsLC
argument_list|,
name|abbrName
argument_list|)
expr_stmt|;
name|all
operator|.
name|put
argument_list|(
name|fullName
argument_list|,
name|abbrName
argument_list|)
expr_stmt|;
block|}
block|}
block|}
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
finally|finally
block|{
try|try
block|{
name|reader
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex2
parameter_list|)
block|{
name|ex2
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
block|}
comment|/**      * Abbreviate the journal name of the given entry.      * @param entry The entry to be treated.      * @param fieldName The field name (e.g. "journal")      * @param ce If the entry is changed, add an edit to this compound.      * @param withDots True if the abbreviations should have dots.      * @return true if the entry was changed, false otherwise.      */
DECL|method|abbreviate (BibtexEntry entry, String fieldName, CompoundEdit ce, boolean withDots)
specifier|public
name|boolean
name|abbreviate
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|String
name|fieldName
parameter_list|,
name|CompoundEdit
name|ce
parameter_list|,
name|boolean
name|withDots
parameter_list|)
block|{
name|Object
name|o
init|=
name|entry
operator|.
name|getField
argument_list|(
name|fieldName
argument_list|)
decl_stmt|;
if|if
condition|(
name|o
operator|==
literal|null
condition|)
return|return
literal|false
return|;
name|String
name|text
init|=
operator|(
name|String
operator|)
name|o
decl_stmt|;
if|if
condition|(
name|isKnownName
argument_list|(
name|text
argument_list|)
operator|&&
operator|!
name|isAbbreviatedName
argument_list|(
name|text
argument_list|)
condition|)
block|{
name|String
name|newText
init|=
name|getAbbreviatedName
argument_list|(
name|text
argument_list|,
name|withDots
argument_list|)
decl_stmt|;
if|if
condition|(
name|newText
operator|==
literal|null
condition|)
return|return
literal|false
return|;
name|entry
operator|.
name|setField
argument_list|(
name|fieldName
argument_list|,
name|newText
argument_list|)
expr_stmt|;
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableFieldChange
argument_list|(
name|entry
argument_list|,
name|fieldName
argument_list|,
name|text
argument_list|,
name|newText
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
else|else
block|{
name|String
name|unabbr
init|=
name|getFullName
argument_list|(
name|text
argument_list|)
decl_stmt|;
if|if
condition|(
name|unabbr
operator|!=
literal|null
condition|)
block|{
name|String
name|newText
init|=
name|getAbbreviatedName
argument_list|(
name|unabbr
argument_list|,
name|withDots
argument_list|)
decl_stmt|;
if|if
condition|(
name|newText
operator|==
literal|null
condition|)
return|return
literal|false
return|;
name|entry
operator|.
name|setField
argument_list|(
name|fieldName
argument_list|,
name|newText
argument_list|)
expr_stmt|;
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableFieldChange
argument_list|(
name|entry
argument_list|,
name|fieldName
argument_list|,
name|text
argument_list|,
name|newText
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
else|else
return|return
literal|false
return|;
block|}
block|}
comment|/**      * Unabbreviate the journal name of the given entry.      * @param entry The entry to be treated.      * @param fieldName The field name (e.g. "journal")      * @param ce If the entry is changed, add an edit to this compound.      * @return true if the entry was changed, false otherwise.      */
DECL|method|unabbreviate (BibtexEntry entry, String fieldName, CompoundEdit ce)
specifier|public
name|boolean
name|unabbreviate
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|String
name|fieldName
parameter_list|,
name|CompoundEdit
name|ce
parameter_list|)
block|{
name|Object
name|o
init|=
name|entry
operator|.
name|getField
argument_list|(
name|fieldName
argument_list|)
decl_stmt|;
if|if
condition|(
name|o
operator|==
literal|null
condition|)
return|return
literal|false
return|;
name|String
name|text
init|=
operator|(
name|String
operator|)
name|o
decl_stmt|;
if|if
condition|(
name|isKnownName
argument_list|(
name|text
argument_list|)
operator|&&
name|isAbbreviatedName
argument_list|(
name|text
argument_list|)
condition|)
block|{
name|String
name|newText
init|=
name|getFullName
argument_list|(
name|text
argument_list|)
decl_stmt|;
if|if
condition|(
name|newText
operator|==
literal|null
condition|)
return|return
literal|false
return|;
name|entry
operator|.
name|setField
argument_list|(
name|fieldName
argument_list|,
name|newText
argument_list|)
expr_stmt|;
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableFieldChange
argument_list|(
name|entry
argument_list|,
name|fieldName
argument_list|,
name|text
argument_list|,
name|newText
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
else|else
return|return
literal|false
return|;
block|}
DECL|method|getJournals ()
specifier|public
name|Map
name|getJournals
parameter_list|()
block|{
return|return
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|all
argument_list|)
return|;
block|}
comment|/**      * Create a control panel for the entry editor's journal field, to toggle      * abbreviated/full journal name      * @param editor The FieldEditor for the journal field.      * @return The control panel for the entry editor.      */
DECL|method|getNameSwitcher (final EntryEditor entryEditor, final FieldEditor editor, final UndoManager undoManager)
specifier|public
specifier|static
name|JComponent
name|getNameSwitcher
parameter_list|(
specifier|final
name|EntryEditor
name|entryEditor
parameter_list|,
specifier|final
name|FieldEditor
name|editor
parameter_list|,
specifier|final
name|UndoManager
name|undoManager
parameter_list|)
block|{
name|JButton
name|button
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Toggle abbreviation"
argument_list|)
argument_list|)
decl_stmt|;
name|button
operator|.
name|setToolTipText
argument_list|(
name|TOOLTIPTEXT
argument_list|)
expr_stmt|;
name|button
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
argument_list|()
block|{
name|boolean
name|withDots
init|=
literal|true
decl_stmt|;
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|actionEvent
parameter_list|)
block|{
name|String
name|text
init|=
name|editor
operator|.
name|getText
argument_list|()
decl_stmt|;
if|if
condition|(
name|Globals
operator|.
name|journalAbbrev
operator|.
name|isKnownName
argument_list|(
name|text
argument_list|)
condition|)
block|{
name|String
name|s
decl_stmt|;
if|if
condition|(
name|Globals
operator|.
name|journalAbbrev
operator|.
name|isAbbreviatedName
argument_list|(
name|text
argument_list|)
condition|)
block|{
comment|//System.out.println(withDots);
if|if
condition|(
operator|!
name|withDots
condition|)
block|{
name|s
operator|=
name|Globals
operator|.
name|journalAbbrev
operator|.
name|getAbbreviatedName
argument_list|(
name|text
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|withDots
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
name|s
operator|=
name|Globals
operator|.
name|journalAbbrev
operator|.
name|getFullName
argument_list|(
name|text
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|s
operator|=
name|Globals
operator|.
name|journalAbbrev
operator|.
name|getAbbreviatedName
argument_list|(
name|text
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|withDots
operator|=
literal|false
expr_stmt|;
block|}
if|if
condition|(
name|s
operator|!=
literal|null
condition|)
block|{
name|editor
operator|.
name|setText
argument_list|(
name|s
argument_list|)
expr_stmt|;
name|entryEditor
operator|.
name|storeFieldAction
operator|.
name|actionPerformed
argument_list|(
operator|new
name|ActionEvent
argument_list|(
name|editor
argument_list|,
literal|0
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|undoManager
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableFieldChange
argument_list|(
name|entryEditor
operator|.
name|entry
argument_list|,
name|editor
operator|.
name|getFieldName
argument_list|()
argument_list|,
name|text
argument_list|,
name|s
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
argument_list|)
expr_stmt|;
return|return
name|button
return|;
block|}
DECL|method|getTableModel ()
specifier|public
name|TableModel
name|getTableModel
parameter_list|()
block|{
name|Object
index|[]
index|[]
name|cells
init|=
operator|new
name|Object
index|[
name|fullNameKeyed
operator|.
name|size
argument_list|()
index|]
index|[
literal|2
index|]
decl_stmt|;
name|int
name|row
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|fullNameIterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|String
name|name
init|=
operator|(
name|String
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|cells
index|[
name|row
index|]
index|[
literal|0
index|]
operator|=
name|getFullName
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|cells
index|[
name|row
index|]
index|[
literal|1
index|]
operator|=
name|getAbbreviatedName
argument_list|(
name|name
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|row
operator|++
expr_stmt|;
block|}
name|DefaultTableModel
name|tableModel
init|=
operator|new
name|DefaultTableModel
argument_list|(
name|cells
argument_list|,
operator|new
name|Object
index|[]
block|{
name|Globals
operator|.
name|lang
argument_list|(
literal|"Full name"
argument_list|)
block|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Abbreviation"
argument_list|)
block|}
argument_list|)
block|{
specifier|public
name|boolean
name|isCellEditable
parameter_list|(
name|int
name|row
parameter_list|,
name|int
name|column
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
block|}
decl_stmt|;
return|return
name|tableModel
return|;
block|}
block|}
end_class

end_unit

