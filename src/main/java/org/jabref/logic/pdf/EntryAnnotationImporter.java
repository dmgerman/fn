begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.pdf
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|pdf
package|;
end_package

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Path
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
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
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
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
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|LinkedFile
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
name|metadata
operator|.
name|FilePreferences
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
name|pdf
operator|.
name|FileAnnotation
import|;
end_import

begin_comment
comment|/**  * Here all PDF files attached to a BibEntry are scanned for annotations using a PdfAnnotationImporter.  */
end_comment

begin_class
DECL|class|EntryAnnotationImporter
specifier|public
class|class
name|EntryAnnotationImporter
block|{
DECL|field|entry
specifier|private
specifier|final
name|BibEntry
name|entry
decl_stmt|;
comment|/**      * @param entry The BibEntry whose attached files are scanned for annotations.      */
DECL|method|EntryAnnotationImporter (BibEntry entry)
specifier|public
name|EntryAnnotationImporter
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
name|this
operator|.
name|entry
operator|=
name|entry
expr_stmt|;
block|}
comment|/**      * Filter files with a web address containing "www."      *      * @return a list of file parsed files      */
DECL|method|getFilteredFileList ()
specifier|private
name|List
argument_list|<
name|LinkedFile
argument_list|>
name|getFilteredFileList
parameter_list|()
block|{
return|return
name|entry
operator|.
name|getFiles
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|parsedFileField
lambda|->
name|parsedFileField
operator|.
name|getFileType
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"pdf"
argument_list|)
argument_list|)
operator|.
name|filter
argument_list|(
name|parsedFileField
lambda|->
operator|!
name|parsedFileField
operator|.
name|isOnlineLink
argument_list|()
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
return|;
block|}
comment|/**      * Reads the annotations from the files that are attached to a BibEntry.      *      * @param databaseContext The context is needed for the importer.      * @return Map from each PDF to a list of file annotations      */
DECL|method|importAnnotationsFromFiles (BibDatabaseContext databaseContext, FilePreferences filePreferences)
specifier|public
name|Map
argument_list|<
name|Path
argument_list|,
name|List
argument_list|<
name|FileAnnotation
argument_list|>
argument_list|>
name|importAnnotationsFromFiles
parameter_list|(
name|BibDatabaseContext
name|databaseContext
parameter_list|,
name|FilePreferences
name|filePreferences
parameter_list|)
block|{
name|Map
argument_list|<
name|Path
argument_list|,
name|List
argument_list|<
name|FileAnnotation
argument_list|>
argument_list|>
name|annotations
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
name|AnnotationImporter
name|importer
init|=
operator|new
name|PdfAnnotationImporter
argument_list|()
decl_stmt|;
comment|//import annotationsOfFiles if the selected files are valid which is checked in getFilteredFileList()
for|for
control|(
name|LinkedFile
name|linkedFile
range|:
name|this
operator|.
name|getFilteredFileList
argument_list|()
control|)
block|{
name|linkedFile
operator|.
name|findIn
argument_list|(
name|databaseContext
argument_list|,
name|filePreferences
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|file
lambda|->
name|annotations
operator|.
name|put
argument_list|(
name|file
argument_list|,
name|importer
operator|.
name|importAnnotations
argument_list|(
name|file
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|annotations
return|;
block|}
block|}
end_class

end_unit

