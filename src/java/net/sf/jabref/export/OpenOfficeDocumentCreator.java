begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * OpenOfficeDocumentCreator.java  *  * Created on February 16, 2005, 8:04 PM  */
end_comment

begin_package
DECL|package|net.sf.jabref.export
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
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
name|*
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
name|zip
operator|.
name|*
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

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|*
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|transform
operator|.
name|*
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|transform
operator|.
name|dom
operator|.
name|*
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|transform
operator|.
name|stream
operator|.
name|*
import|;
end_import

begin_comment
comment|/**  * @author alver  */
end_comment

begin_class
DECL|class|OpenOfficeDocumentCreator
specifier|public
class|class
name|OpenOfficeDocumentCreator
block|{
comment|/**      * Creates a new instance of OpenOfficeDocumentCreator      */
DECL|method|OpenOfficeDocumentCreator ()
specifier|private
name|OpenOfficeDocumentCreator
parameter_list|()
block|{     }
DECL|method|storeOpenOfficeFile (File file, InputStream source)
specifier|public
specifier|static
name|void
name|storeOpenOfficeFile
parameter_list|(
name|File
name|file
parameter_list|,
name|InputStream
name|source
parameter_list|)
throws|throws
name|Exception
block|{
name|ZipOutputStream
name|out
init|=
operator|new
name|ZipOutputStream
argument_list|(
operator|new
name|BufferedOutputStream
argument_list|(
operator|new
name|FileOutputStream
argument_list|(
name|file
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
try|try
block|{
name|ZipEntry
name|zipEntry
init|=
operator|new
name|ZipEntry
argument_list|(
literal|"content.xml"
argument_list|)
decl_stmt|;
name|out
operator|.
name|putNextEntry
argument_list|(
name|zipEntry
argument_list|)
expr_stmt|;
name|int
name|c
init|=
operator|-
literal|1
decl_stmt|;
while|while
condition|(
operator|(
name|c
operator|=
name|source
operator|.
name|read
argument_list|()
operator|)
operator|>=
literal|0
condition|)
block|{
name|out
operator|.
name|write
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
name|out
operator|.
name|closeEntry
argument_list|()
expr_stmt|;
comment|// Add manifest (required for OOo 2.0), "meta.xml", "mimetype" files. These are in the
comment|// resource/openoffice directory, and are copied verbatim into the zip file.
name|addResourceFile
argument_list|(
literal|"meta.xml"
argument_list|,
literal|"/resource/openoffice/meta.xml"
argument_list|,
name|out
argument_list|)
expr_stmt|;
name|addResourceFile
argument_list|(
literal|"mimetype"
argument_list|,
literal|"/resource/openoffice/mimetype"
argument_list|,
name|out
argument_list|)
expr_stmt|;
name|addResourceFile
argument_list|(
literal|"META-INF/manifest.xml"
argument_list|,
literal|"/resource/openoffice/manifest.xml"
argument_list|,
name|out
argument_list|)
expr_stmt|;
comment|//zipEntry = new ZipEntry()
block|}
finally|finally
block|{
name|out
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|exportOpenOfficeCalc (File file, BibtexDatabase database)
specifier|public
specifier|static
name|void
name|exportOpenOfficeCalc
parameter_list|(
name|File
name|file
parameter_list|,
name|BibtexDatabase
name|database
parameter_list|)
throws|throws
name|Exception
block|{
comment|// First store the xml formatted content to a temporary file.
name|File
name|tmpFile
init|=
name|File
operator|.
name|createTempFile
argument_list|(
literal|"oocalc"
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|exportOpenOfficeCalcXML
argument_list|(
name|tmpFile
argument_list|,
name|database
argument_list|)
expr_stmt|;
comment|// Then add the content to the zip file:
name|BufferedInputStream
name|in
init|=
operator|new
name|BufferedInputStream
argument_list|(
operator|new
name|FileInputStream
argument_list|(
name|tmpFile
argument_list|)
argument_list|)
decl_stmt|;
name|storeOpenOfficeFile
argument_list|(
name|file
argument_list|,
name|in
argument_list|)
expr_stmt|;
comment|// Delete the temporary file:
name|tmpFile
operator|.
name|delete
argument_list|()
expr_stmt|;
block|}
DECL|method|exportOpenOfficeCalcXML (File tmpFile, BibtexDatabase database)
specifier|public
specifier|static
name|void
name|exportOpenOfficeCalcXML
parameter_list|(
name|File
name|tmpFile
parameter_list|,
name|BibtexDatabase
name|database
parameter_list|)
block|{
name|OOCalcDatabase
name|od
init|=
operator|new
name|OOCalcDatabase
argument_list|(
name|database
argument_list|)
decl_stmt|;
try|try
block|{
name|Writer
name|ps
init|=
operator|new
name|OutputStreamWriter
argument_list|(
operator|new
name|FileOutputStream
argument_list|(
name|tmpFile
argument_list|)
argument_list|,
literal|"UTF8"
argument_list|)
decl_stmt|;
try|try
block|{
comment|//            Writer ps = new FileWriter(tmpFile);
name|DOMSource
name|source
init|=
operator|new
name|DOMSource
argument_list|(
name|od
operator|.
name|getDOMrepresentation
argument_list|()
argument_list|)
decl_stmt|;
name|StreamResult
name|result
init|=
operator|new
name|StreamResult
argument_list|(
name|ps
argument_list|)
decl_stmt|;
name|Transformer
name|trans
init|=
name|TransformerFactory
operator|.
name|newInstance
argument_list|()
operator|.
name|newTransformer
argument_list|()
decl_stmt|;
name|trans
operator|.
name|setOutputProperty
argument_list|(
name|OutputKeys
operator|.
name|INDENT
argument_list|,
literal|"yes"
argument_list|)
expr_stmt|;
name|trans
operator|.
name|transform
argument_list|(
name|source
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|ps
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|Error
argument_list|(
name|e
argument_list|)
throw|;
block|}
return|return;
block|}
DECL|method|addResourceFile (String name, String resource, ZipOutputStream out)
specifier|private
specifier|static
name|void
name|addResourceFile
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|resource
parameter_list|,
name|ZipOutputStream
name|out
parameter_list|)
throws|throws
name|IOException
block|{
name|ZipEntry
name|zipEntry
init|=
operator|new
name|ZipEntry
argument_list|(
name|name
argument_list|)
decl_stmt|;
name|out
operator|.
name|putNextEntry
argument_list|(
name|zipEntry
argument_list|)
expr_stmt|;
name|addFromResource
argument_list|(
name|resource
argument_list|,
name|out
argument_list|)
expr_stmt|;
name|out
operator|.
name|closeEntry
argument_list|()
expr_stmt|;
block|}
DECL|method|addFromResource (String resource, OutputStream out)
specifier|private
specifier|static
name|void
name|addFromResource
parameter_list|(
name|String
name|resource
parameter_list|,
name|OutputStream
name|out
parameter_list|)
block|{
name|URL
name|url
init|=
name|OpenOfficeDocumentCreator
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|resource
argument_list|)
decl_stmt|;
try|try
block|{
name|InputStream
name|in
init|=
name|url
operator|.
name|openStream
argument_list|()
decl_stmt|;
name|byte
index|[]
name|buffer
init|=
operator|new
name|byte
index|[
literal|256
index|]
decl_stmt|;
synchronized|synchronized
init|(
name|in
init|)
block|{
synchronized|synchronized
init|(
name|out
init|)
block|{
while|while
condition|(
literal|true
condition|)
block|{
name|int
name|bytesRead
init|=
name|in
operator|.
name|read
argument_list|(
name|buffer
argument_list|)
decl_stmt|;
if|if
condition|(
name|bytesRead
operator|==
operator|-
literal|1
condition|)
break|break;
name|out
operator|.
name|write
argument_list|(
name|buffer
argument_list|,
literal|0
argument_list|,
name|bytesRead
argument_list|)
expr_stmt|;
block|}
block|}
block|}
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
block|}
end_class

end_unit

