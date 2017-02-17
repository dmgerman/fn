begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.importer.fileformat
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|fileformat
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
name|IOException
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
name|logic
operator|.
name|exporter
operator|.
name|SavePreferences
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
name|importer
operator|.
name|ImportFormatPreferences
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
name|importer
operator|.
name|Importer
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
name|importer
operator|.
name|ParserResult
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
name|FileExtensions
import|;
end_import

begin_comment
comment|/**  * This importer exists only to enable `--importToOpen someEntry.bib`  *  * It is NOT intended to import a BIB file. This is done via the option action, which treats the metadata fields  * The metadata is not required to be read here, as this class is NOT called at --import  */
end_comment

begin_class
DECL|class|BibtexImporter
specifier|public
class|class
name|BibtexImporter
extends|extends
name|Importer
block|{
comment|// Signature written at the top of the .bib file in earlier versions.
DECL|field|SIGNATURE
specifier|private
specifier|static
specifier|final
name|String
name|SIGNATURE
init|=
literal|"This file was created with JabRef"
decl_stmt|;
DECL|field|importFormatPreferences
specifier|private
specifier|final
name|ImportFormatPreferences
name|importFormatPreferences
decl_stmt|;
DECL|method|BibtexImporter (ImportFormatPreferences importFormatPreferences)
specifier|public
name|BibtexImporter
parameter_list|(
name|ImportFormatPreferences
name|importFormatPreferences
parameter_list|)
block|{
name|this
operator|.
name|importFormatPreferences
operator|=
name|importFormatPreferences
expr_stmt|;
block|}
comment|/**      * @return true as we have no effective way to decide whether a file is in bibtex format or not. See      *         https://github.com/JabRef/jabref/pull/379#issuecomment-158685726 for more details.      */
annotation|@
name|Override
DECL|method|isRecognizedFormat (BufferedReader reader)
specifier|public
name|boolean
name|isRecognizedFormat
parameter_list|(
name|BufferedReader
name|reader
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|reader
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
annotation|@
name|Override
DECL|method|importDatabase (Path filePath, Charset defaultEncoding)
specifier|public
name|ParserResult
name|importDatabase
parameter_list|(
name|Path
name|filePath
parameter_list|,
name|Charset
name|defaultEncoding
parameter_list|)
throws|throws
name|IOException
block|{
comment|// We want to check if there is a JabRef signature in the file, because that would tell us
comment|// which character encoding is used. However, to read the signature we must be using a compatible
comment|// encoding in the first place. Since the signature doesn't contain any fancy characters, we can
comment|// read it regardless of encoding, with either UTF-8 or UTF-16. That's the hypothesis, at any rate.
comment|// 8 bit is most likely, so we try that first:
name|Optional
argument_list|<
name|Charset
argument_list|>
name|suppliedEncoding
decl_stmt|;
try|try
init|(
name|BufferedReader
name|utf8Reader
init|=
name|getUTF8Reader
argument_list|(
name|filePath
argument_list|)
init|)
block|{
name|suppliedEncoding
operator|=
name|getSuppliedEncoding
argument_list|(
name|utf8Reader
argument_list|)
expr_stmt|;
block|}
comment|// Now if that did not get us anywhere, we check with the 16 bit encoding:
if|if
condition|(
operator|!
name|suppliedEncoding
operator|.
name|isPresent
argument_list|()
condition|)
block|{
try|try
init|(
name|BufferedReader
name|utf16Reader
init|=
name|getUTF16Reader
argument_list|(
name|filePath
argument_list|)
init|)
block|{
name|suppliedEncoding
operator|=
name|getSuppliedEncoding
argument_list|(
name|utf16Reader
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|suppliedEncoding
operator|.
name|isPresent
argument_list|()
condition|)
block|{
return|return
name|super
operator|.
name|importDatabase
argument_list|(
name|filePath
argument_list|,
name|suppliedEncoding
operator|.
name|get
argument_list|()
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|super
operator|.
name|importDatabase
argument_list|(
name|filePath
argument_list|,
name|defaultEncoding
argument_list|)
return|;
block|}
block|}
annotation|@
name|Override
DECL|method|importDatabase (BufferedReader reader)
specifier|public
name|ParserResult
name|importDatabase
parameter_list|(
name|BufferedReader
name|reader
parameter_list|)
throws|throws
name|IOException
block|{
return|return
operator|new
name|BibtexParser
argument_list|(
name|importFormatPreferences
argument_list|)
operator|.
name|parse
argument_list|(
name|reader
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"BibTeX"
return|;
block|}
annotation|@
name|Override
DECL|method|getExtensions ()
specifier|public
name|FileExtensions
name|getExtensions
parameter_list|()
block|{
return|return
name|FileExtensions
operator|.
name|BIBTEX_DB
return|;
block|}
annotation|@
name|Override
DECL|method|getDescription ()
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
literal|"This importer exists only to enable `--importToOpen someEntry.bib`\n"
operator|+
literal|"It is NOT intended to import a BIB file. This is done via the option action, which treats the metadata fields.\n"
operator|+
literal|"The metadata is not required to be read here, as this class is NOT called at --import."
return|;
block|}
comment|/**      * Searches the file for "Encoding: myEncoding" and returns the found supplied encoding.      */
DECL|method|getSuppliedEncoding (BufferedReader reader)
specifier|private
specifier|static
name|Optional
argument_list|<
name|Charset
argument_list|>
name|getSuppliedEncoding
parameter_list|(
name|BufferedReader
name|reader
parameter_list|)
block|{
try|try
block|{
name|String
name|line
decl_stmt|;
while|while
condition|(
operator|(
name|line
operator|=
name|reader
operator|.
name|readLine
argument_list|()
operator|)
operator|!=
literal|null
condition|)
block|{
name|line
operator|=
name|line
operator|.
name|trim
argument_list|()
expr_stmt|;
comment|// Line does not start with %, so there are no comment lines for us and we can stop parsing
if|if
condition|(
operator|!
name|line
operator|.
name|startsWith
argument_list|(
literal|"%"
argument_list|)
condition|)
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
comment|// Only keep the part after %
name|line
operator|=
name|line
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
operator|.
name|trim
argument_list|()
expr_stmt|;
if|if
condition|(
name|line
operator|.
name|startsWith
argument_list|(
name|BibtexImporter
operator|.
name|SIGNATURE
argument_list|)
condition|)
block|{
comment|// Signature line, so keep reading and skip to next line
block|}
elseif|else
if|if
condition|(
name|line
operator|.
name|startsWith
argument_list|(
name|SavePreferences
operator|.
name|ENCODING_PREFIX
argument_list|)
condition|)
block|{
comment|// Line starts with "Encoding: ", so the rest of the line should contain the name of the encoding
comment|// Except if there is already a @ symbol signaling the starting of a BibEntry
name|Integer
name|atSymbolIndex
init|=
name|line
operator|.
name|indexOf
argument_list|(
literal|'@'
argument_list|)
decl_stmt|;
name|String
name|encoding
decl_stmt|;
if|if
condition|(
name|atSymbolIndex
operator|>
literal|0
condition|)
block|{
name|encoding
operator|=
name|line
operator|.
name|substring
argument_list|(
name|SavePreferences
operator|.
name|ENCODING_PREFIX
operator|.
name|length
argument_list|()
argument_list|,
name|atSymbolIndex
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|encoding
operator|=
name|line
operator|.
name|substring
argument_list|(
name|SavePreferences
operator|.
name|ENCODING_PREFIX
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|Optional
operator|.
name|of
argument_list|(
name|Charset
operator|.
name|forName
argument_list|(
name|encoding
argument_list|)
argument_list|)
return|;
block|}
else|else
block|{
comment|// Line not recognized so stop parsing
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|ignored
parameter_list|)
block|{
comment|// Ignored
block|}
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
block|}
end_class

end_unit
