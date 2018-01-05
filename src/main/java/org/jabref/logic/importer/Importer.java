begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.importer
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
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
name|BufferedReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileInputStream
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
name|io
operator|.
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStreamReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|StringReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|charset
operator|.
name|Charset
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|charset
operator|.
name|StandardCharsets
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
name|util
operator|.
name|Objects
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
name|util
operator|.
name|FileType
import|;
end_import

begin_comment
comment|/**  * Role of an importer for JabRef.  */
end_comment

begin_class
DECL|class|Importer
specifier|public
specifier|abstract
class|class
name|Importer
implements|implements
name|Comparable
argument_list|<
name|Importer
argument_list|>
block|{
comment|/**      * Check whether the source is in the correct format for this importer.      *      * The effect of this method is primarily to avoid unnecessary processing of      * files when searching for a suitable import format. If this method returns      * false, the import routine will move on to the next import format.      *      * Thus the correct behaviour is to return false if it is certain that the file is      * not of the suitable type, and true otherwise. Returning true is the safe choice if not certain.      */
DECL|method|isRecognizedFormat (BufferedReader input)
specifier|public
specifier|abstract
name|boolean
name|isRecognizedFormat
parameter_list|(
name|BufferedReader
name|input
parameter_list|)
throws|throws
name|IOException
function_decl|;
comment|/**      * Check whether the source is in the correct format for this importer.      *      * @param filePath the path of the file to check      * @param encoding the encoding of the file      * @return true, if the file is in a recognized format      * @throws IOException Signals that an I/O exception has occurred.      */
DECL|method|isRecognizedFormat (Path filePath, Charset encoding)
specifier|public
name|boolean
name|isRecognizedFormat
parameter_list|(
name|Path
name|filePath
parameter_list|,
name|Charset
name|encoding
parameter_list|)
throws|throws
name|IOException
block|{
try|try
init|(
name|BufferedReader
name|bufferedReader
init|=
name|getReader
argument_list|(
name|filePath
argument_list|,
name|encoding
argument_list|)
init|)
block|{
return|return
name|isRecognizedFormat
argument_list|(
name|bufferedReader
argument_list|)
return|;
block|}
block|}
comment|/**      * Check whether the source is in the correct format for this importer.      *      * @param data the data to check      * @return true, if the data is in a recognized format      * @throws IOException Signals that an I/O exception has occurred.      */
DECL|method|isRecognizedFormat (String data)
specifier|public
name|boolean
name|isRecognizedFormat
parameter_list|(
name|String
name|data
parameter_list|)
throws|throws
name|IOException
block|{
try|try
init|(
name|StringReader
name|stringReader
init|=
operator|new
name|StringReader
argument_list|(
name|data
argument_list|)
init|;
name|BufferedReader
name|bufferedReader
operator|=
operator|new
name|BufferedReader
argument_list|(
name|stringReader
argument_list|)
init|)
block|{
return|return
name|isRecognizedFormat
argument_list|(
name|bufferedReader
argument_list|)
return|;
block|}
block|}
comment|/**      * Parse the database in the source.      *      * This method can be called in two different contexts - either when importing in      * a specified format, or when importing in unknown format. In the latter case,      * JabRef cycles through all available import formats. No error messages or feedback      * is displayed from individual import formats in this case.      *      * If importing in a specified format and an empty library is returned, JabRef reports      * that no entries were found.      *      * This method should never return null.      *      * @param input the input to read from      */
DECL|method|importDatabase (BufferedReader input)
specifier|public
specifier|abstract
name|ParserResult
name|importDatabase
parameter_list|(
name|BufferedReader
name|input
parameter_list|)
throws|throws
name|IOException
function_decl|;
comment|/**      * Parse the database in the specified file.      *      * Importer having the facilities to detect the correct encoding of a file should overwrite this method,      * determine the encoding and then call {@link #importDatabase(BufferedReader)}.      *      * @param filePath the path to the file which should be imported      * @param encoding the encoding used to decode the file      */
DECL|method|importDatabase (Path filePath, Charset encoding)
specifier|public
name|ParserResult
name|importDatabase
parameter_list|(
name|Path
name|filePath
parameter_list|,
name|Charset
name|encoding
parameter_list|)
throws|throws
name|IOException
block|{
try|try
init|(
name|BufferedReader
name|bufferedReader
init|=
name|getReader
argument_list|(
name|filePath
argument_list|,
name|encoding
argument_list|)
init|)
block|{
name|ParserResult
name|parserResult
init|=
name|importDatabase
argument_list|(
name|bufferedReader
argument_list|)
decl_stmt|;
name|parserResult
operator|.
name|getMetaData
argument_list|()
operator|.
name|setEncoding
argument_list|(
name|encoding
argument_list|)
expr_stmt|;
name|parserResult
operator|.
name|setFile
argument_list|(
name|filePath
operator|.
name|toFile
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|parserResult
return|;
block|}
block|}
comment|/**      * Parse the database in the specified string.      *      * Importer having the facilities to detect the correct encoding of a string should overwrite this method,      * determine the encoding and then call {@link #importDatabase(BufferedReader)}.      *      * @param data the string which should be imported      * @return the parsed result      * @throws IOException Signals that an I/O exception has occurred.      */
DECL|method|importDatabase (String data)
specifier|public
name|ParserResult
name|importDatabase
parameter_list|(
name|String
name|data
parameter_list|)
throws|throws
name|IOException
block|{
try|try
init|(
name|StringReader
name|stringReader
init|=
operator|new
name|StringReader
argument_list|(
name|data
argument_list|)
init|;
name|BufferedReader
name|bufferedReader
operator|=
operator|new
name|BufferedReader
argument_list|(
name|stringReader
argument_list|)
init|)
block|{
name|ParserResult
name|parserResult
init|=
name|importDatabase
argument_list|(
name|bufferedReader
argument_list|)
decl_stmt|;
return|return
name|parserResult
return|;
block|}
block|}
DECL|method|getUTF8Reader (Path filePath)
specifier|protected
specifier|static
name|BufferedReader
name|getUTF8Reader
parameter_list|(
name|Path
name|filePath
parameter_list|)
throws|throws
name|IOException
block|{
return|return
name|getReader
argument_list|(
name|filePath
argument_list|,
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
return|;
block|}
DECL|method|getUTF16Reader (Path filePath)
specifier|protected
specifier|static
name|BufferedReader
name|getUTF16Reader
parameter_list|(
name|Path
name|filePath
parameter_list|)
throws|throws
name|IOException
block|{
return|return
name|getReader
argument_list|(
name|filePath
argument_list|,
name|StandardCharsets
operator|.
name|UTF_16
argument_list|)
return|;
block|}
DECL|method|getReader (Path filePath, Charset encoding)
specifier|public
specifier|static
name|BufferedReader
name|getReader
parameter_list|(
name|Path
name|filePath
parameter_list|,
name|Charset
name|encoding
parameter_list|)
throws|throws
name|IOException
block|{
name|InputStream
name|stream
init|=
operator|new
name|FileInputStream
argument_list|(
name|filePath
operator|.
name|toFile
argument_list|()
argument_list|)
decl_stmt|;
return|return
operator|new
name|BufferedReader
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
name|stream
argument_list|,
name|encoding
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * Returns the name of this import format.      *      *<p>The name must be unique.</p>      *      * @return format name, must be unique and not<code>null</code>      */
DECL|method|getName ()
specifier|public
specifier|abstract
name|String
name|getName
parameter_list|()
function_decl|;
comment|/**      * Returns the type of files that this importer can read      * @return {@link FileType} corresponding to the importer      */
DECL|method|getFileType ()
specifier|public
specifier|abstract
name|FileType
name|getFileType
parameter_list|()
function_decl|;
comment|/**      * Returns a one-word ID which identifies this importer.      * Used for example, to identify the importer when used from the command line.      *      * @return ID, must be unique and not<code>null</code>      */
DECL|method|getId ()
specifier|public
name|String
name|getId
parameter_list|()
block|{
name|String
name|id
init|=
name|getName
argument_list|()
decl_stmt|;
name|StringBuilder
name|result
init|=
operator|new
name|StringBuilder
argument_list|(
name|id
operator|.
name|length
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|id
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|char
name|c
init|=
name|id
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|Character
operator|.
name|isLetterOrDigit
argument_list|(
name|c
argument_list|)
condition|)
block|{
name|result
operator|.
name|append
argument_list|(
name|Character
operator|.
name|toLowerCase
argument_list|(
name|c
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|result
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Returns the description of the import format.      *      * The description should specify      *<ul><li>      *   what kind of entries from what sources and based on what specification it is able to import      *</li><li>      *   by what criteria it {@link #isRecognizedFormat(BufferedReader) recognizes} an import format      *</li></ul>      *      * @return description of the import format      */
DECL|method|getDescription ()
specifier|public
specifier|abstract
name|String
name|getDescription
parameter_list|()
function_decl|;
annotation|@
name|Override
DECL|method|hashCode ()
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|getName
argument_list|()
operator|.
name|hashCode
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|equals (Object obj)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
if|if
condition|(
name|this
operator|==
name|obj
condition|)
block|{
return|return
literal|true
return|;
block|}
if|if
condition|(
operator|!
operator|(
name|obj
operator|instanceof
name|Importer
operator|)
condition|)
block|{
return|return
literal|false
return|;
block|}
name|Importer
name|other
init|=
operator|(
name|Importer
operator|)
name|obj
decl_stmt|;
return|return
name|Objects
operator|.
name|equals
argument_list|(
name|this
operator|.
name|getName
argument_list|()
argument_list|,
name|other
operator|.
name|getName
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getName
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|compareTo (Importer o)
specifier|public
name|int
name|compareTo
parameter_list|(
name|Importer
name|o
parameter_list|)
block|{
return|return
name|getName
argument_list|()
operator|.
name|compareTo
argument_list|(
name|o
operator|.
name|getName
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

