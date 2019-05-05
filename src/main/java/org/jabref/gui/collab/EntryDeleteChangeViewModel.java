begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.collab
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|collab
package|;
end_package

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|Node
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
name|preview
operator|.
name|PreviewViewer
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
name|UndoableRemoveEntry
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
name|entry
operator|.
name|BibEntry
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_class
DECL|class|EntryDeleteChangeViewModel
class|class
name|EntryDeleteChangeViewModel
extends|extends
name|DatabaseChangeViewModel
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Logger
name|LOGGER
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|EntryDeleteChangeViewModel
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|memEntry
specifier|private
specifier|final
name|BibEntry
name|memEntry
decl_stmt|;
DECL|field|tmpEntry
specifier|private
specifier|final
name|BibEntry
name|tmpEntry
decl_stmt|;
DECL|method|EntryDeleteChangeViewModel (BibEntry memEntry, BibEntry tmpEntry)
specifier|public
name|EntryDeleteChangeViewModel
parameter_list|(
name|BibEntry
name|memEntry
parameter_list|,
name|BibEntry
name|tmpEntry
parameter_list|)
block|{
name|super
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Deleted entry"
argument_list|)
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
name|double
name|matchWithTmp
init|=
name|DuplicateCheck
operator|.
name|compareEntriesStrictly
argument_list|(
name|memEntry
argument_list|,
name|tmpEntry
argument_list|)
decl_stmt|;
comment|// Check if it has been modified locally, since last tempfile was saved.
name|boolean
name|isModifiedLocally
init|=
operator|(
name|matchWithTmp
operator|<=
literal|1
operator|)
decl_stmt|;
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Modified entry: "
operator|+
name|memEntry
operator|.
name|getCiteKeyOptional
argument_list|()
operator|.
name|orElse
argument_list|(
literal|"<no BibTeX key set>"
argument_list|)
operator|+
literal|"\n Modified locally: "
operator|+
name|isModifiedLocally
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|makeChange (BibDatabaseContext database, NamedCompound undoEdit)
specifier|public
name|void
name|makeChange
parameter_list|(
name|BibDatabaseContext
name|database
parameter_list|,
name|NamedCompound
name|undoEdit
parameter_list|)
block|{
name|database
operator|.
name|getDatabase
argument_list|()
operator|.
name|removeEntry
argument_list|(
name|memEntry
argument_list|)
expr_stmt|;
name|undoEdit
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableRemoveEntry
argument_list|(
name|database
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|memEntry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|description ()
specifier|public
name|Node
name|description
parameter_list|()
block|{
name|PreviewViewer
name|previewViewer
init|=
operator|new
name|PreviewViewer
argument_list|(
operator|new
name|BibDatabaseContext
argument_list|()
argument_list|,
name|JabRefGUI
operator|.
name|getMainFrame
argument_list|()
operator|.
name|getDialogService
argument_list|()
argument_list|)
decl_stmt|;
name|previewViewer
operator|.
name|setEntry
argument_list|(
name|memEntry
argument_list|)
expr_stmt|;
return|return
name|previewViewer
return|;
block|}
block|}
end_class

end_unit

