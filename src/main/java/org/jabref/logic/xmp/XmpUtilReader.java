begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.xmp
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|xmp
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ByteArrayInputStream
import|;
end_import

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
name|IOException
import|;
end_import

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
name|nio
operator|.
name|file
operator|.
name|Paths
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
name|Collections
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
name|apache
operator|.
name|pdfbox
operator|.
name|pdmodel
operator|.
name|PDDocument
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|pdfbox
operator|.
name|pdmodel
operator|.
name|PDDocumentCatalog
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|pdfbox
operator|.
name|pdmodel
operator|.
name|PDDocumentInformation
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|pdfbox
operator|.
name|pdmodel
operator|.
name|common
operator|.
name|PDMetadata
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|xmpbox
operator|.
name|XMPMetadata
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|xmpbox
operator|.
name|schema
operator|.
name|DublinCoreSchema
import|;
end_import

begin_class
DECL|class|XmpUtilReader
specifier|public
class|class
name|XmpUtilReader
block|{
DECL|field|START_TAG
specifier|private
specifier|static
specifier|final
name|String
name|START_TAG
init|=
literal|"<rdf:Description"
decl_stmt|;
DECL|field|END_TAG
specifier|private
specifier|static
specifier|final
name|String
name|END_TAG
init|=
literal|"</rdf:Description>"
decl_stmt|;
DECL|method|XmpUtilReader ()
specifier|private
name|XmpUtilReader
parameter_list|()
block|{     }
comment|/**      * Will read the XMPMetadata from the given pdf file, closing the file      * afterwards.      *      * @param path The path to read the XMPMetadata from.      * @return The XMPMetadata object found in the file      */
DECL|method|readRawXmp (Path path)
specifier|public
specifier|static
name|List
argument_list|<
name|XMPMetadata
argument_list|>
name|readRawXmp
parameter_list|(
name|Path
name|path
parameter_list|)
throws|throws
name|IOException
block|{
try|try
init|(
name|PDDocument
name|document
init|=
name|XmpUtilReader
operator|.
name|loadWithAutomaticDecryption
argument_list|(
name|path
argument_list|)
init|)
block|{
return|return
name|XmpUtilReader
operator|.
name|getXmpMetadata
argument_list|(
name|document
argument_list|)
return|;
block|}
block|}
comment|/**      * Convenience method for readXMP(File).      *      * @param filename The filename from which to open the file.      * @return BibtexEntryies found in the PDF or an empty list      */
DECL|method|readXmp (String filename, XmpPreferences xmpPreferences)
specifier|public
specifier|static
name|List
argument_list|<
name|BibEntry
argument_list|>
name|readXmp
parameter_list|(
name|String
name|filename
parameter_list|,
name|XmpPreferences
name|xmpPreferences
parameter_list|)
throws|throws
name|IOException
block|{
return|return
name|XmpUtilReader
operator|.
name|readXmp
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
name|filename
argument_list|)
argument_list|,
name|xmpPreferences
argument_list|)
return|;
block|}
comment|/**      * Try to read the given BibTexEntry from the XMP-stream of the given      * inputstream containing a PDF-file.      *      * Only supports Dublin Core as a metadata format.      *      * @param path The path to read from.      * @return list of BibEntries retrieved from the stream. May be empty, but never null      * @throws IOException Throws an IOException if the file cannot be read, so the user than remove a lock or cancel      *                     the operation.      */
DECL|method|readXmp (Path path, XmpPreferences xmpPreferences)
specifier|public
specifier|static
name|List
argument_list|<
name|BibEntry
argument_list|>
name|readXmp
parameter_list|(
name|Path
name|path
parameter_list|,
name|XmpPreferences
name|xmpPreferences
parameter_list|)
throws|throws
name|IOException
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|result
init|=
operator|new
name|LinkedList
argument_list|<>
argument_list|()
decl_stmt|;
try|try
init|(
name|PDDocument
name|document
init|=
name|loadWithAutomaticDecryption
argument_list|(
name|path
argument_list|)
init|)
block|{
name|List
argument_list|<
name|XMPMetadata
argument_list|>
name|xmpMetaList
init|=
name|XmpUtilReader
operator|.
name|getXmpMetadata
argument_list|(
name|document
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|xmpMetaList
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// Only support Dublin Core since JabRef 4.2
for|for
control|(
name|XMPMetadata
name|xmpMeta
range|:
name|xmpMetaList
control|)
block|{
name|DublinCoreSchema
name|dcSchema
init|=
name|xmpMeta
operator|.
name|getDublinCoreSchema
argument_list|()
decl_stmt|;
if|if
condition|(
name|dcSchema
operator|!=
literal|null
condition|)
block|{
name|DublinCoreExtractor
name|dcExtractor
init|=
operator|new
name|DublinCoreExtractor
argument_list|(
name|dcSchema
argument_list|,
name|xmpPreferences
argument_list|,
operator|new
name|BibEntry
argument_list|()
argument_list|)
decl_stmt|;
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|entry
init|=
name|dcExtractor
operator|.
name|extractBibtexEntry
argument_list|()
decl_stmt|;
if|if
condition|(
name|entry
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|result
operator|.
name|add
argument_list|(
name|entry
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
if|if
condition|(
name|result
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// If we did not find any XMP metadata, search for non XMP metadata
name|PDDocumentInformation
name|documentInformation
init|=
name|document
operator|.
name|getDocumentInformation
argument_list|()
decl_stmt|;
name|DocumentInformationExtractor
name|diExtractor
init|=
operator|new
name|DocumentInformationExtractor
argument_list|(
name|documentInformation
argument_list|)
decl_stmt|;
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|entry
init|=
name|diExtractor
operator|.
name|extractBibtexEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|ifPresent
argument_list|(
name|result
operator|::
name|add
argument_list|)
expr_stmt|;
block|}
block|}
comment|// return empty list, if no metadata was found
if|if
condition|(
name|result
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
return|return
name|result
return|;
block|}
comment|/**      * This method is a hack to generate multiple XMPMetadata objects, because the      * implementation of the pdfbox does not support methods for reading multiple      * DublinCoreSchemas from a single metadata entry.      *<p/>      *      *      * @return empty Optional if no metadata has been found      */
DECL|method|getXmpMetadata (PDDocument document)
specifier|private
specifier|static
name|List
argument_list|<
name|XMPMetadata
argument_list|>
name|getXmpMetadata
parameter_list|(
name|PDDocument
name|document
parameter_list|)
throws|throws
name|IOException
block|{
name|PDDocumentCatalog
name|catalog
init|=
name|document
operator|.
name|getDocumentCatalog
argument_list|()
decl_stmt|;
name|PDMetadata
name|metaRaw
init|=
name|catalog
operator|.
name|getMetadata
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|XMPMetadata
argument_list|>
name|metaList
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
if|if
condition|(
name|metaRaw
operator|==
literal|null
condition|)
block|{
return|return
name|metaList
return|;
block|}
name|String
name|xmp
init|=
name|metaRaw
operator|.
name|getCOSObject
argument_list|()
operator|.
name|toTextString
argument_list|()
decl_stmt|;
name|int
name|startDescriptionSection
init|=
name|xmp
operator|.
name|indexOf
argument_list|(
name|START_TAG
argument_list|)
decl_stmt|;
name|int
name|endDescriptionSection
init|=
name|xmp
operator|.
name|lastIndexOf
argument_list|(
name|END_TAG
argument_list|)
operator|+
name|END_TAG
operator|.
name|length
argument_list|()
decl_stmt|;
comment|// XML header for the xmpDomParser
name|String
name|start
init|=
name|xmp
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|startDescriptionSection
argument_list|)
decl_stmt|;
comment|// descriptionArray - mid part of the textual metadata
name|String
index|[]
name|descriptionsArray
init|=
name|xmp
operator|.
name|substring
argument_list|(
name|startDescriptionSection
argument_list|,
name|endDescriptionSection
argument_list|)
operator|.
name|split
argument_list|(
name|END_TAG
argument_list|)
decl_stmt|;
comment|// XML footer for the xmpDomParser
name|String
name|end
init|=
name|xmp
operator|.
name|substring
argument_list|(
name|endDescriptionSection
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|s
range|:
name|descriptionsArray
control|)
block|{
comment|// END_TAG is appended, because of the split operation above
name|String
name|xmpMetaString
init|=
name|start
operator|+
name|s
operator|+
name|END_TAG
operator|+
name|end
decl_stmt|;
name|metaList
operator|.
name|add
argument_list|(
name|XmpUtilShared
operator|.
name|parseXmpMetadata
argument_list|(
operator|new
name|ByteArrayInputStream
argument_list|(
name|xmpMetaString
operator|.
name|getBytes
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|metaList
return|;
block|}
comment|/**      * Loads the specified file with the basic pdfbox functionality and uses an empty string as default password.      *      * @param path The path to load.      * @return      * @throws IOException from the underlying {@link PDDocument#load(File)}      */
DECL|method|loadWithAutomaticDecryption (Path path)
specifier|public
specifier|static
name|PDDocument
name|loadWithAutomaticDecryption
parameter_list|(
name|Path
name|path
parameter_list|)
throws|throws
name|IOException
block|{
comment|// try to load the document
comment|// also uses an empty string as default password
name|PDDocument
name|doc
init|=
name|PDDocument
operator|.
name|load
argument_list|(
name|path
operator|.
name|toFile
argument_list|()
argument_list|)
decl_stmt|;
return|return
name|doc
return|;
block|}
block|}
end_class

end_unit

