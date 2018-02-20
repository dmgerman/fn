begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.importer
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|importer
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileFilter
import|;
end_import

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
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|LinkedList
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
name|java
operator|.
name|util
operator|.
name|Optional
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|ChangeEvent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|ChangeListener
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
name|org
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
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|externalfiletype
operator|.
name|ExternalFileType
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
name|externalfiletype
operator|.
name|ExternalFileTypes
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
name|EntryType
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
name|IdGenerator
import|;
end_import

begin_comment
comment|/**  * The class EntryFromFileCreatorManager manages entry creators.  * The manager knows all existing implementations of the interface EntryFromFileCreator.  * Given a file, the manager can then provide a creator, which is able to create a Bibtex entry for his file.  * Knowing all implementations of the interface, the manager also knows the set of all files, of which Bibtex entries can be created.  * The GUI uses this capability for offering the user only such files, of which entries could actually be created.  * @author Dan&Nosh  *  */
end_comment

begin_class
DECL|class|EntryFromFileCreatorManager
specifier|public
specifier|final
class|class
name|EntryFromFileCreatorManager
block|{
DECL|field|entryCreators
specifier|private
specifier|final
name|List
argument_list|<
name|EntryFromFileCreator
argument_list|>
name|entryCreators
decl_stmt|;
DECL|method|EntryFromFileCreatorManager (ExternalFileTypes externalFilesTypes)
specifier|public
name|EntryFromFileCreatorManager
parameter_list|(
name|ExternalFileTypes
name|externalFilesTypes
parameter_list|)
block|{
name|entryCreators
operator|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
literal|10
argument_list|)
expr_stmt|;
name|entryCreators
operator|.
name|add
argument_list|(
operator|new
name|EntryFromPDFCreator
argument_list|(
name|externalFilesTypes
argument_list|)
argument_list|)
expr_stmt|;
comment|// add a creator for each ExternalFileType if there is no specialized
comment|// creator existing.
name|Collection
argument_list|<
name|ExternalFileType
argument_list|>
name|fileTypes
init|=
name|externalFilesTypes
operator|.
name|getExternalFileTypeSelection
argument_list|()
decl_stmt|;
for|for
control|(
name|ExternalFileType
name|exFileType
range|:
name|fileTypes
control|)
block|{
if|if
condition|(
operator|!
name|hasSpecialisedCreatorForExternalFileType
argument_list|(
name|exFileType
argument_list|)
condition|)
block|{
name|entryCreators
operator|.
name|add
argument_list|(
operator|new
name|EntryFromExternalFileCreator
argument_list|(
name|exFileType
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|hasSpecialisedCreatorForExternalFileType ( ExternalFileType externalFileType)
specifier|private
name|boolean
name|hasSpecialisedCreatorForExternalFileType
parameter_list|(
name|ExternalFileType
name|externalFileType
parameter_list|)
block|{
for|for
control|(
name|EntryFromFileCreator
name|entryCreator
range|:
name|entryCreators
control|)
block|{
if|if
condition|(
operator|(
name|entryCreator
operator|.
name|getExternalFileType
argument_list|()
operator|==
literal|null
operator|)
operator|||
operator|(
name|entryCreator
operator|.
name|getExternalFileType
argument_list|()
operator|.
name|getExtension
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|)
condition|)
block|{
continue|continue;
block|}
if|if
condition|(
name|entryCreator
operator|.
name|getExternalFileType
argument_list|()
operator|.
name|getExtension
argument_list|()
operator|.
name|equals
argument_list|(
name|externalFileType
operator|.
name|getExtension
argument_list|()
argument_list|)
condition|)
block|{
return|return
literal|true
return|;
block|}
block|}
return|return
literal|false
return|;
block|}
comment|/**      * Returns a EntryFromFileCreator object that is capable of creating a      * BibEntry for the given File.      *      * @param file the pdf file      * @return null if there is no EntryFromFileCreator for this File.      */
DECL|method|getEntryCreator (File file)
specifier|public
name|EntryFromFileCreator
name|getEntryCreator
parameter_list|(
name|File
name|file
parameter_list|)
block|{
if|if
condition|(
operator|(
name|file
operator|==
literal|null
operator|)
operator|||
operator|!
name|file
operator|.
name|exists
argument_list|()
condition|)
block|{
return|return
literal|null
return|;
block|}
for|for
control|(
name|EntryFromFileCreator
name|creator
range|:
name|entryCreators
control|)
block|{
if|if
condition|(
name|creator
operator|.
name|accept
argument_list|(
name|file
argument_list|)
condition|)
block|{
return|return
name|creator
return|;
block|}
block|}
return|return
literal|null
return|;
block|}
comment|/**      * Tries to add a entry for each file in the List.      *      * @param files      * @param database      * @param entryType      * @return List of unexpected import event messages including failures.      */
DECL|method|addEntrysFromFiles (List<File> files, BibDatabase database, EntryType entryType, boolean generateKeywordsFromPathToFile)
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|addEntrysFromFiles
parameter_list|(
name|List
argument_list|<
name|File
argument_list|>
name|files
parameter_list|,
name|BibDatabase
name|database
parameter_list|,
name|EntryType
name|entryType
parameter_list|,
name|boolean
name|generateKeywordsFromPathToFile
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|importGUIMessages
init|=
operator|new
name|LinkedList
argument_list|<>
argument_list|()
decl_stmt|;
name|addEntriesFromFiles
argument_list|(
name|files
argument_list|,
name|database
argument_list|,
literal|null
argument_list|,
name|entryType
argument_list|,
name|generateKeywordsFromPathToFile
argument_list|,
literal|null
argument_list|,
name|importGUIMessages
argument_list|)
expr_stmt|;
return|return
name|importGUIMessages
return|;
block|}
comment|/**      * Tries to add a entry for each file in the List.      *      * @param files      * @param database      * @param panel      * @param entryType      * @param generateKeywordsFromPathToFile      * @param changeListener      * @param importGUIMessages list of unexpected import event - Messages including      *         failures      * @return Returns The number of entries added      */
DECL|method|addEntriesFromFiles (List<File> files, BibDatabase database, BasePanel panel, EntryType entryType, boolean generateKeywordsFromPathToFile, ChangeListener changeListener, List<String> importGUIMessages)
specifier|public
name|int
name|addEntriesFromFiles
parameter_list|(
name|List
argument_list|<
name|File
argument_list|>
name|files
parameter_list|,
name|BibDatabase
name|database
parameter_list|,
name|BasePanel
name|panel
parameter_list|,
name|EntryType
name|entryType
parameter_list|,
name|boolean
name|generateKeywordsFromPathToFile
parameter_list|,
name|ChangeListener
name|changeListener
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|importGUIMessages
parameter_list|)
block|{
name|int
name|count
init|=
literal|0
decl_stmt|;
name|CompoundEdit
name|ce
init|=
operator|new
name|CompoundEdit
argument_list|()
decl_stmt|;
for|for
control|(
name|File
name|f
range|:
name|files
control|)
block|{
name|EntryFromFileCreator
name|creator
init|=
name|getEntryCreator
argument_list|(
name|f
argument_list|)
decl_stmt|;
if|if
condition|(
name|creator
operator|==
literal|null
condition|)
block|{
name|importGUIMessages
operator|.
name|add
argument_list|(
literal|"Problem importing "
operator|+
name|f
operator|.
name|getPath
argument_list|()
operator|+
literal|": Unknown filetype."
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|entry
init|=
name|creator
operator|.
name|createEntry
argument_list|(
name|f
argument_list|,
name|generateKeywordsFromPathToFile
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|entry
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|importGUIMessages
operator|.
name|add
argument_list|(
literal|"Problem importing "
operator|+
name|f
operator|.
name|getPath
argument_list|()
operator|+
literal|": Entry could not be created."
argument_list|)
expr_stmt|;
continue|continue;
block|}
if|if
condition|(
name|entryType
operator|!=
literal|null
condition|)
block|{
name|entry
operator|.
name|get
argument_list|()
operator|.
name|setType
argument_list|(
name|entryType
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|entry
operator|.
name|get
argument_list|()
operator|.
name|getId
argument_list|()
operator|==
literal|null
condition|)
block|{
name|entry
operator|.
name|get
argument_list|()
operator|.
name|setId
argument_list|(
name|IdGenerator
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/*                  * TODO: database.insertEntry(BibEntry) is not sensible. Why                  * does 'true' mean "There were duplicates", while 'false' means                  * "Everything alright"?                  */
if|if
condition|(
operator|!
name|database
operator|.
name|containsEntryWithId
argument_list|(
name|entry
operator|.
name|get
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|)
condition|)
block|{
comment|// Work around SIDE EFFECT of creator.createEntry. The EntryFromPDFCreator also creates the entry in the table
comment|// Therefore, we only insert the entry if it is not already present
if|if
condition|(
name|database
operator|.
name|insertEntry
argument_list|(
name|entry
operator|.
name|get
argument_list|()
argument_list|)
condition|)
block|{
name|importGUIMessages
operator|.
name|add
argument_list|(
literal|"Problem importing "
operator|+
name|f
operator|.
name|getPath
argument_list|()
operator|+
literal|": Insert into BibDatabase failed."
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|count
operator|++
expr_stmt|;
if|if
condition|(
name|panel
operator|!=
literal|null
condition|)
block|{
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableInsertEntry
argument_list|(
name|database
argument_list|,
name|entry
operator|.
name|get
argument_list|()
argument_list|,
name|panel
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
if|if
condition|(
name|changeListener
operator|!=
literal|null
condition|)
block|{
name|changeListener
operator|.
name|stateChanged
argument_list|(
operator|new
name|ChangeEvent
argument_list|(
name|this
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|(
name|count
operator|>
literal|0
operator|)
operator|&&
operator|(
name|panel
operator|!=
literal|null
operator|)
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
block|}
return|return
name|count
return|;
block|}
comment|/**      * Returns a {@link FileFilter} instance which will accept all files, for      * which a {@link EntryFromFileCreator} exists, that accepts the files.<br>      *<br>      * This {@link FileFilter} will be displayed in the GUI as      * "All supported files".      *      * @return A {@link FileFilter} that accepts all files for which creators      *         exist.      */
DECL|method|getFileFilter ()
specifier|private
name|FileFilter
name|getFileFilter
parameter_list|()
block|{
return|return
operator|new
name|FileFilter
argument_list|()
block|{
comment|/**              * Accepts all files, which are accepted by any known creator.              */
annotation|@
name|Override
specifier|public
name|boolean
name|accept
parameter_list|(
name|File
name|file
parameter_list|)
block|{
for|for
control|(
name|EntryFromFileCreator
name|creator
range|:
name|entryCreators
control|)
block|{
if|if
condition|(
name|creator
operator|.
name|accept
argument_list|(
name|file
argument_list|)
condition|)
block|{
return|return
literal|true
return|;
block|}
block|}
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"All external files"
argument_list|)
return|;
block|}
block|}
return|;
block|}
comment|/**      * Returns a list of all {@link FileFilter} instances (i.e.      * {@link EntryFromFileCreator}, plus the file filter that comes with the      * {@link #getFileFilter()} method.      *      * @return A List of all known possible file filters.      */
DECL|method|getFileFilterList ()
specifier|public
name|List
argument_list|<
name|FileFilter
argument_list|>
name|getFileFilterList
parameter_list|()
block|{
name|List
argument_list|<
name|FileFilter
argument_list|>
name|filters
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|filters
operator|.
name|add
argument_list|(
name|getFileFilter
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|FileFilter
name|creator
range|:
name|entryCreators
control|)
block|{
name|filters
operator|.
name|add
argument_list|(
name|creator
argument_list|)
expr_stmt|;
block|}
return|return
name|filters
return|;
block|}
block|}
end_class

end_unit

