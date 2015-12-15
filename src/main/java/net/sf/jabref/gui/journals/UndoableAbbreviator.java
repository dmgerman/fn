begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.journals
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
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
name|model
operator|.
name|database
operator|.
name|BibDatabase
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|journals
operator|.
name|Abbreviation
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
name|journals
operator|.
name|JournalAbbreviationRepository
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
name|UndoableFieldChange
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

begin_class
DECL|class|UndoableAbbreviator
specifier|public
class|class
name|UndoableAbbreviator
block|{
DECL|field|journalAbbreviationRepository
specifier|private
specifier|final
name|JournalAbbreviationRepository
name|journalAbbreviationRepository
decl_stmt|;
DECL|field|isoAbbreviationStyle
specifier|private
specifier|final
name|boolean
name|isoAbbreviationStyle
decl_stmt|;
DECL|method|UndoableAbbreviator (JournalAbbreviationRepository journalAbbreviationRepository, boolean isoAbbreviationStyle)
specifier|public
name|UndoableAbbreviator
parameter_list|(
name|JournalAbbreviationRepository
name|journalAbbreviationRepository
parameter_list|,
name|boolean
name|isoAbbreviationStyle
parameter_list|)
block|{
name|this
operator|.
name|journalAbbreviationRepository
operator|=
name|journalAbbreviationRepository
expr_stmt|;
name|this
operator|.
name|isoAbbreviationStyle
operator|=
name|isoAbbreviationStyle
expr_stmt|;
block|}
comment|/**      * Abbreviate the journal name of the given entry.      *      * @param database  The database the entry belongs to, or null if no database.      * @param entry     The entry to be treated.      * @param fieldName The field name (e.g. "journal")      * @param ce        If the entry is changed, add an edit to this compound.      * @return true if the entry was changed, false otherwise.      */
DECL|method|abbreviate (BibDatabase database, BibEntry entry, String fieldName, CompoundEdit ce)
specifier|public
name|boolean
name|abbreviate
parameter_list|(
name|BibDatabase
name|database
parameter_list|,
name|BibEntry
name|entry
parameter_list|,
name|String
name|fieldName
parameter_list|,
name|CompoundEdit
name|ce
parameter_list|)
block|{
name|String
name|text
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
name|text
operator|==
literal|null
condition|)
block|{
return|return
literal|false
return|;
block|}
name|String
name|origText
init|=
name|text
decl_stmt|;
if|if
condition|(
name|database
operator|!=
literal|null
condition|)
block|{
name|text
operator|=
name|database
operator|.
name|resolveForStrings
argument_list|(
name|text
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|journalAbbreviationRepository
operator|.
name|isKnownName
argument_list|(
name|text
argument_list|)
condition|)
block|{
return|return
literal|false
return|;
comment|// unknown, cannot un/abbreviate anything
block|}
name|String
name|newText
init|=
name|getAbbreviatedName
argument_list|(
name|journalAbbreviationRepository
operator|.
name|getAbbreviation
argument_list|(
name|text
argument_list|)
operator|.
name|get
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|newText
operator|.
name|equals
argument_list|(
name|origText
argument_list|)
condition|)
block|{
return|return
literal|false
return|;
block|}
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
name|origText
argument_list|,
name|newText
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
DECL|method|getAbbreviatedName (Abbreviation text)
specifier|private
name|String
name|getAbbreviatedName
parameter_list|(
name|Abbreviation
name|text
parameter_list|)
block|{
if|if
condition|(
name|isoAbbreviationStyle
condition|)
block|{
return|return
name|text
operator|.
name|getIsoAbbreviation
argument_list|()
return|;
block|}
else|else
block|{
return|return
name|text
operator|.
name|getMedlineAbbreviation
argument_list|()
return|;
block|}
block|}
block|}
end_class

end_unit

