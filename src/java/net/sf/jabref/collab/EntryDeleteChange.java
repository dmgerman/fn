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
name|javax
operator|.
name|swing
operator|.
name|JComponent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JScrollPane
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

begin_class
DECL|class|EntryDeleteChange
specifier|public
class|class
name|EntryDeleteChange
extends|extends
name|Change
block|{
DECL|field|memEntry
DECL|field|tmpEntry
DECL|field|diskEntry
name|BibtexEntry
name|memEntry
decl_stmt|,
name|tmpEntry
decl_stmt|,
name|diskEntry
decl_stmt|;
DECL|field|isModifiedLocally
name|boolean
name|isModifiedLocally
decl_stmt|;
DECL|field|matchWithTmp
name|double
name|matchWithTmp
decl_stmt|;
DECL|field|pp
name|PreviewPanel
name|pp
decl_stmt|;
DECL|field|sp
name|JScrollPane
name|sp
decl_stmt|;
DECL|method|EntryDeleteChange (BibtexEntry memEntry, BibtexEntry tmpEntry)
specifier|public
name|EntryDeleteChange
parameter_list|(
name|BibtexEntry
name|memEntry
parameter_list|,
name|BibtexEntry
name|tmpEntry
parameter_list|)
block|{
name|super
argument_list|(
literal|"Deleted entry"
argument_list|)
expr_stmt|;
name|this
operator|.
name|memEntry
operator|=
name|memEntry
expr_stmt|;
name|this
operator|.
name|tmpEntry
operator|=
name|tmpEntry
expr_stmt|;
comment|// Compare the deleted entry in memory with the one in the tmpfile. The
comment|// entry could have been removed in memory.
name|matchWithTmp
operator|=
name|Util
operator|.
name|compareEntriesStrictly
argument_list|(
name|memEntry
argument_list|,
name|tmpEntry
argument_list|)
expr_stmt|;
comment|// Check if it has been modified locally, since last tempfile was saved.
name|isModifiedLocally
operator|=
operator|!
operator|(
name|matchWithTmp
operator|>
literal|1
operator|)
expr_stmt|;
comment|//Util.pr("Modified entry: "+memEntry.getCiteKey()+"\n Modified locally: "+isModifiedLocally
comment|//        +" Modifications agree: "+modificationsAgree);
name|pp
operator|=
operator|new
name|PreviewPanel
argument_list|(
literal|null
argument_list|,
name|memEntry
argument_list|,
literal|null
argument_list|,
operator|new
name|MetaData
argument_list|()
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"preview0"
argument_list|)
argument_list|)
expr_stmt|;
name|sp
operator|=
operator|new
name|JScrollPane
argument_list|(
name|pp
argument_list|)
expr_stmt|;
block|}
DECL|method|makeChange (BasePanel panel, NamedCompound undoEdit)
specifier|public
name|void
name|makeChange
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|NamedCompound
name|undoEdit
parameter_list|)
block|{
name|panel
operator|.
name|database
argument_list|()
operator|.
name|removeEntry
argument_list|(
name|memEntry
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|undoEdit
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableRemoveEntry
argument_list|(
name|panel
operator|.
name|database
argument_list|()
argument_list|,
name|memEntry
argument_list|,
name|panel
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|description ()
name|JComponent
name|description
parameter_list|()
block|{
return|return
name|sp
return|;
block|}
block|}
end_class

end_unit

