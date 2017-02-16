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
name|java
operator|.
name|util
operator|.
name|List
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
name|net
operator|.
name|sf
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
name|worker
operator|.
name|AbstractWorker
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
name|InternalBibtexFields
import|;
end_import

begin_comment
comment|/**  * Converts journal full names to either iso or medline abbreviations for all selected entries.  */
end_comment

begin_class
DECL|class|AbbreviateAction
specifier|public
class|class
name|AbbreviateAction
extends|extends
name|AbstractWorker
block|{
DECL|field|panel
specifier|private
specifier|final
name|BasePanel
name|panel
decl_stmt|;
DECL|field|message
specifier|private
name|String
name|message
init|=
literal|""
decl_stmt|;
DECL|field|iso
specifier|private
specifier|final
name|boolean
name|iso
decl_stmt|;
DECL|method|AbbreviateAction (BasePanel panel, boolean iso)
specifier|public
name|AbbreviateAction
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|boolean
name|iso
parameter_list|)
block|{
name|this
operator|.
name|panel
operator|=
name|panel
expr_stmt|;
name|this
operator|.
name|iso
operator|=
name|iso
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|init ()
specifier|public
name|void
name|init
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
literal|"Abbreviating..."
argument_list|)
argument_list|)
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
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|panel
operator|.
name|getSelectedEntries
argument_list|()
decl_stmt|;
if|if
condition|(
name|entries
operator|==
literal|null
condition|)
block|{
return|return;
block|}
name|UndoableAbbreviator
name|undoableAbbreviator
init|=
operator|new
name|UndoableAbbreviator
argument_list|(
name|Globals
operator|.
name|journalAbbreviationLoader
operator|.
name|getRepository
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getJournalAbbreviationPreferences
argument_list|()
argument_list|)
argument_list|,
name|iso
argument_list|)
decl_stmt|;
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
literal|"Abbreviate journal names"
argument_list|)
argument_list|)
decl_stmt|;
name|int
name|count
init|=
literal|0
decl_stmt|;
for|for
control|(
name|BibEntry
name|entry
range|:
name|entries
control|)
block|{
for|for
control|(
name|String
name|journalField
range|:
name|InternalBibtexFields
operator|.
name|getJournalNameFields
argument_list|()
control|)
block|{
if|if
condition|(
name|undoableAbbreviator
operator|.
name|abbreviate
argument_list|(
name|panel
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|entry
argument_list|,
name|journalField
argument_list|,
name|ce
argument_list|)
condition|)
block|{
name|count
operator|++
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|count
operator|>
literal|0
condition|)
block|{
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
name|panel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
name|message
operator|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Abbreviated %0 journal names."
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|count
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|message
operator|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"No journal names could be abbreviated."
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|update ()
specifier|public
name|void
name|update
parameter_list|()
block|{
name|panel
operator|.
name|output
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

