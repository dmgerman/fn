begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.journals
package|package
name|org
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
name|logic
operator|.
name|journals
operator|.
name|Abbreviation
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
name|journals
operator|.
name|JournalAbbreviationRepository
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
name|BibDatabase
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
name|field
operator|.
name|Field
import|;
end_import

begin_class
DECL|class|UndoableUnabbreviator
specifier|public
class|class
name|UndoableUnabbreviator
block|{
DECL|field|journalAbbreviationRepository
specifier|private
specifier|final
name|JournalAbbreviationRepository
name|journalAbbreviationRepository
decl_stmt|;
DECL|method|UndoableUnabbreviator (JournalAbbreviationRepository journalAbbreviationRepository)
specifier|public
name|UndoableUnabbreviator
parameter_list|(
name|JournalAbbreviationRepository
name|journalAbbreviationRepository
parameter_list|)
block|{
name|this
operator|.
name|journalAbbreviationRepository
operator|=
name|journalAbbreviationRepository
expr_stmt|;
block|}
comment|/**      * Unabbreviate the journal name of the given entry.      *      * @param entry     The entry to be treated.      * @param field The field      * @param ce        If the entry is changed, add an edit to this compound.      * @return true if the entry was changed, false otherwise.      */
DECL|method|unabbreviate (BibDatabase database, BibEntry entry, Field field, CompoundEdit ce)
specifier|public
name|boolean
name|unabbreviate
parameter_list|(
name|BibDatabase
name|database
parameter_list|,
name|BibEntry
name|entry
parameter_list|,
name|Field
name|field
parameter_list|,
name|CompoundEdit
name|ce
parameter_list|)
block|{
if|if
condition|(
operator|!
name|entry
operator|.
name|hasField
argument_list|(
name|field
argument_list|)
condition|)
block|{
return|return
literal|false
return|;
block|}
name|String
name|text
init|=
name|entry
operator|.
name|getField
argument_list|(
name|field
argument_list|)
operator|.
name|get
argument_list|()
decl_stmt|;
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
comment|// cannot do anything if it is not known
block|}
if|if
condition|(
operator|!
name|journalAbbreviationRepository
operator|.
name|isAbbreviatedName
argument_list|(
name|text
argument_list|)
condition|)
block|{
return|return
literal|false
return|;
comment|// cannot unabbreviate unabbreviated name.
block|}
name|Abbreviation
name|abbreviation
init|=
name|journalAbbreviationRepository
operator|.
name|getAbbreviation
argument_list|(
name|text
argument_list|)
operator|.
name|get
argument_list|()
decl_stmt|;
comment|// must be here
name|String
name|newText
init|=
name|abbreviation
operator|.
name|getName
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|field
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
name|field
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
block|}
end_class

end_unit

