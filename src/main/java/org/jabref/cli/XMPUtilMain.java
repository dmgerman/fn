begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.cli
package|package
name|org
operator|.
name|jabref
operator|.
name|cli
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
name|FileReader
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
name|StringWriter
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
name|xml
operator|.
name|transform
operator|.
name|TransformerException
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
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|bibtex
operator|.
name|BibEntryWriter
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
name|LatexFieldFormatter
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
name|importer
operator|.
name|fileformat
operator|.
name|BibtexParser
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
name|xmp
operator|.
name|XMPPreferences
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
name|xmp
operator|.
name|XMPUtil
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
name|BibDatabaseMode
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
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|jempbox
operator|.
name|impl
operator|.
name|XMLUtil
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|jempbox
operator|.
name|xmp
operator|.
name|XMPMetadata
import|;
end_import

begin_class
DECL|class|XMPUtilMain
specifier|public
class|class
name|XMPUtilMain
block|{
DECL|method|XMPUtilMain ()
specifier|private
name|XMPUtilMain
parameter_list|()
block|{     }
comment|/**      * Command-line tool for working with XMP-data.      *      * Read or write XMP-metadata from or to pdf file.      *      * Usage:      *<dl>      *<dd>Read from PDF and print as bibtex:</dd>      *<dt>xmpUtil PDF</dt>      *<dd>Read from PDF and print raw XMP:</dd>      *<dt>xmpUtil -x PDF</dt>      *<dd>Write the entry in BIB given by KEY to the PDF:</dd>      *<dt>xmpUtil KEY BIB PDF</dt>      *<dd>Write all entries in BIB to the PDF:</dd>      *<dt>xmpUtil BIB PDF</dt>      *</dl>      *      * @param args      *            Command line strings passed to utility.      * @throws IOException      *             If any of the given files could not be read or written.      * @throws TransformerException      *             If the given BibEntry is malformed.      */
DECL|method|main (String[] args)
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
throws|throws
name|IOException
throws|,
name|TransformerException
block|{
comment|// Don't forget to initialize the preferences
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|==
literal|null
condition|)
block|{
name|Globals
operator|.
name|prefs
operator|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
expr_stmt|;
block|}
name|XMPPreferences
name|xmpPreferences
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getXMPPreferences
argument_list|()
decl_stmt|;
name|ImportFormatPreferences
name|importFormatPreferences
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getImportFormatPreferences
argument_list|()
decl_stmt|;
switch|switch
condition|(
name|args
operator|.
name|length
condition|)
block|{
case|case
literal|0
case|:
name|usage
argument_list|()
expr_stmt|;
break|break;
case|case
literal|1
case|:
if|if
condition|(
name|args
index|[
literal|0
index|]
operator|.
name|endsWith
argument_list|(
literal|".pdf"
argument_list|)
condition|)
block|{
comment|// Read from pdf and write as BibTex
name|List
argument_list|<
name|BibEntry
argument_list|>
name|l
init|=
name|XMPUtil
operator|.
name|readXMP
argument_list|(
operator|new
name|File
argument_list|(
name|args
index|[
literal|0
index|]
argument_list|)
argument_list|,
name|xmpPreferences
argument_list|)
decl_stmt|;
name|BibEntryWriter
name|bibtexEntryWriter
init|=
operator|new
name|BibEntryWriter
argument_list|(
operator|new
name|LatexFieldFormatter
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getLatexFieldFormatterPreferences
argument_list|()
argument_list|)
argument_list|,
literal|false
argument_list|)
decl_stmt|;
for|for
control|(
name|BibEntry
name|entry
range|:
name|l
control|)
block|{
name|StringWriter
name|sw
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
name|bibtexEntryWriter
operator|.
name|write
argument_list|(
name|entry
argument_list|,
name|sw
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|sw
operator|.
name|getBuffer
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|args
index|[
literal|0
index|]
operator|.
name|endsWith
argument_list|(
literal|".bib"
argument_list|)
condition|)
block|{
comment|// Read from BIB and write as XMP
try|try
init|(
name|FileReader
name|fr
init|=
operator|new
name|FileReader
argument_list|(
name|args
index|[
literal|0
index|]
argument_list|)
init|)
block|{
name|ParserResult
name|result
init|=
operator|new
name|BibtexParser
argument_list|(
name|importFormatPreferences
argument_list|,
name|Globals
operator|.
name|getFileUpdateMonitor
argument_list|()
argument_list|)
operator|.
name|parse
argument_list|(
name|fr
argument_list|)
decl_stmt|;
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|result
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
if|if
condition|(
name|entries
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Could not find BibEntry in "
operator|+
name|args
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|XMPUtil
operator|.
name|toXMP
argument_list|(
name|entries
argument_list|,
name|result
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|xmpPreferences
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
name|usage
argument_list|()
expr_stmt|;
block|}
break|break;
case|case
literal|2
case|:
if|if
condition|(
literal|"-x"
operator|.
name|equals
argument_list|(
name|args
index|[
literal|0
index|]
argument_list|)
operator|&&
name|args
index|[
literal|1
index|]
operator|.
name|endsWith
argument_list|(
literal|".pdf"
argument_list|)
condition|)
block|{
comment|// Read from pdf and write as BibTex
name|Optional
argument_list|<
name|XMPMetadata
argument_list|>
name|meta
init|=
name|XMPUtil
operator|.
name|readRawXMP
argument_list|(
operator|new
name|File
argument_list|(
name|args
index|[
literal|1
index|]
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|meta
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|XMLUtil
operator|.
name|save
argument_list|(
name|meta
operator|.
name|get
argument_list|()
operator|.
name|getXMPDocument
argument_list|()
argument_list|,
name|System
operator|.
name|out
argument_list|,
name|StandardCharsets
operator|.
name|UTF_8
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"The given pdf does not contain any XMP-metadata."
argument_list|)
expr_stmt|;
block|}
break|break;
block|}
if|if
condition|(
name|args
index|[
literal|0
index|]
operator|.
name|endsWith
argument_list|(
literal|".bib"
argument_list|)
operator|&&
name|args
index|[
literal|1
index|]
operator|.
name|endsWith
argument_list|(
literal|".pdf"
argument_list|)
condition|)
block|{
name|ParserResult
name|result
init|=
operator|new
name|BibtexParser
argument_list|(
name|importFormatPreferences
argument_list|,
name|Globals
operator|.
name|getFileUpdateMonitor
argument_list|()
argument_list|)
operator|.
name|parse
argument_list|(
operator|new
name|FileReader
argument_list|(
name|args
index|[
literal|0
index|]
argument_list|)
argument_list|)
decl_stmt|;
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|result
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
if|if
condition|(
name|entries
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Could not find BibEntry in "
operator|+
name|args
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|XMPUtil
operator|.
name|writeXMP
argument_list|(
operator|new
name|File
argument_list|(
name|args
index|[
literal|1
index|]
argument_list|)
argument_list|,
name|entries
argument_list|,
name|result
operator|.
name|getDatabase
argument_list|()
argument_list|,
literal|false
argument_list|,
name|xmpPreferences
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"XMP written."
argument_list|)
expr_stmt|;
block|}
break|break;
block|}
name|usage
argument_list|()
expr_stmt|;
break|break;
case|case
literal|3
case|:
if|if
condition|(
operator|!
name|args
index|[
literal|1
index|]
operator|.
name|endsWith
argument_list|(
literal|".bib"
argument_list|)
operator|&&
operator|!
name|args
index|[
literal|2
index|]
operator|.
name|endsWith
argument_list|(
literal|".pdf"
argument_list|)
condition|)
block|{
name|usage
argument_list|()
expr_stmt|;
break|break;
block|}
name|ParserResult
name|result
init|=
operator|new
name|BibtexParser
argument_list|(
name|importFormatPreferences
argument_list|,
name|Globals
operator|.
name|getFileUpdateMonitor
argument_list|()
argument_list|)
operator|.
name|parse
argument_list|(
operator|new
name|FileReader
argument_list|(
name|args
index|[
literal|1
index|]
argument_list|)
argument_list|)
decl_stmt|;
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|bibEntry
init|=
name|result
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntryByKey
argument_list|(
name|args
index|[
literal|0
index|]
argument_list|)
decl_stmt|;
if|if
condition|(
name|bibEntry
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|XMPUtil
operator|.
name|writeXMP
argument_list|(
operator|new
name|File
argument_list|(
name|args
index|[
literal|2
index|]
argument_list|)
argument_list|,
name|bibEntry
operator|.
name|get
argument_list|()
argument_list|,
name|result
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|xmpPreferences
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"XMP written."
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Could not find BibEntry "
operator|+
name|args
index|[
literal|0
index|]
operator|+
literal|" in "
operator|+
name|args
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
block|}
break|break;
default|default:
name|usage
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      * Print usage information for the command line tool xmpUtil.      *      * @see XMPUtilMain#main(String[])      */
DECL|method|usage ()
specifier|private
specifier|static
name|void
name|usage
parameter_list|()
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Read or write XMP-metadata from or to pdf file."
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Usage:"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Read from PDF and print as bibtex:"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"  xmpUtil<pdf>"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Read from PDF and print raw XMP:"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"  xmpUtil -x<pdf>"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Write the entry in<bib> given by<key> to the PDF:"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"  xmpUtil<key><bib><pdf>"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Write all entries in<bib> to the PDF:"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"  xmpUtil<bib><pdf>"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"To report bugs visit https://issues.jabref.org"
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

