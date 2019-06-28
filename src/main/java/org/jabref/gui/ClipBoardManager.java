begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
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
name|StandardCharsets
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
name|javafx
operator|.
name|scene
operator|.
name|input
operator|.
name|Clipboard
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|input
operator|.
name|ClipboardContent
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|input
operator|.
name|DataFormat
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
name|FetcherException
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
name|ImportException
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
name|ImportFormatReader
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
name|ImportFormatReader
operator|.
name|UnknownFormatImport
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
name|ParseException
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
name|fetcher
operator|.
name|DoiFetcher
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
name|model
operator|.
name|entry
operator|.
name|identifier
operator|.
name|DOI
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
name|util
operator|.
name|OptionalUtil
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
DECL|class|ClipBoardManager
specifier|public
class|class
name|ClipBoardManager
block|{
DECL|field|XML
specifier|public
specifier|static
specifier|final
name|DataFormat
name|XML
init|=
operator|new
name|DataFormat
argument_list|(
literal|"application/xml"
argument_list|)
decl_stmt|;
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
name|ClipBoardManager
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|clipboard
specifier|private
specifier|final
name|Clipboard
name|clipboard
decl_stmt|;
DECL|field|importFormatReader
specifier|private
specifier|final
name|ImportFormatReader
name|importFormatReader
decl_stmt|;
DECL|method|ClipBoardManager ()
specifier|public
name|ClipBoardManager
parameter_list|()
block|{
name|this
argument_list|(
name|Clipboard
operator|.
name|getSystemClipboard
argument_list|()
argument_list|,
name|Globals
operator|.
name|IMPORT_FORMAT_READER
argument_list|)
expr_stmt|;
block|}
DECL|method|ClipBoardManager (Clipboard clipboard, ImportFormatReader importFormatReader)
specifier|public
name|ClipBoardManager
parameter_list|(
name|Clipboard
name|clipboard
parameter_list|,
name|ImportFormatReader
name|importFormatReader
parameter_list|)
block|{
name|this
operator|.
name|clipboard
operator|=
name|clipboard
expr_stmt|;
name|this
operator|.
name|importFormatReader
operator|=
name|importFormatReader
expr_stmt|;
block|}
comment|/**      * Puts content onto the clipboard.      */
DECL|method|setContent (ClipboardContent content)
specifier|public
name|void
name|setContent
parameter_list|(
name|ClipboardContent
name|content
parameter_list|)
block|{
name|clipboard
operator|.
name|setContent
argument_list|(
name|content
argument_list|)
expr_stmt|;
block|}
comment|/**      * Get the String residing on the clipboard.      *      * @return any text found on the Clipboard; if none found, return an      * empty String.      */
DECL|method|getContents ()
specifier|public
name|String
name|getContents
parameter_list|()
block|{
name|String
name|result
init|=
name|clipboard
operator|.
name|getString
argument_list|()
decl_stmt|;
if|if
condition|(
name|result
operator|==
literal|null
condition|)
block|{
return|return
literal|""
return|;
block|}
return|return
name|result
return|;
block|}
DECL|method|setHtmlContent (String html)
specifier|public
name|void
name|setHtmlContent
parameter_list|(
name|String
name|html
parameter_list|)
block|{
specifier|final
name|ClipboardContent
name|content
init|=
operator|new
name|ClipboardContent
argument_list|()
decl_stmt|;
name|content
operator|.
name|putHtml
argument_list|(
name|html
argument_list|)
expr_stmt|;
name|clipboard
operator|.
name|setContent
argument_list|(
name|content
argument_list|)
expr_stmt|;
block|}
DECL|method|setContent (String string)
specifier|public
name|void
name|setContent
parameter_list|(
name|String
name|string
parameter_list|)
block|{
specifier|final
name|ClipboardContent
name|content
init|=
operator|new
name|ClipboardContent
argument_list|()
decl_stmt|;
name|content
operator|.
name|putString
argument_list|(
name|string
argument_list|)
expr_stmt|;
name|clipboard
operator|.
name|setContent
argument_list|(
name|content
argument_list|)
expr_stmt|;
block|}
DECL|method|setContent (List<BibEntry> entries)
specifier|public
name|void
name|setContent
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|)
throws|throws
name|IOException
block|{
specifier|final
name|ClipboardContent
name|content
init|=
operator|new
name|ClipboardContent
argument_list|()
decl_stmt|;
name|BibEntryWriter
name|writer
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
name|String
name|serializedEntries
init|=
name|writer
operator|.
name|serializeAll
argument_list|(
name|entries
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
decl_stmt|;
name|content
operator|.
name|put
argument_list|(
name|DragAndDropDataFormats
operator|.
name|ENTRIES
argument_list|,
name|serializedEntries
argument_list|)
expr_stmt|;
name|content
operator|.
name|putString
argument_list|(
name|serializedEntries
argument_list|)
expr_stmt|;
name|clipboard
operator|.
name|setContent
argument_list|(
name|content
argument_list|)
expr_stmt|;
block|}
DECL|method|extractData ()
specifier|public
name|List
argument_list|<
name|BibEntry
argument_list|>
name|extractData
parameter_list|()
block|{
name|Object
name|entries
init|=
name|clipboard
operator|.
name|getContent
argument_list|(
name|DragAndDropDataFormats
operator|.
name|ENTRIES
argument_list|)
decl_stmt|;
if|if
condition|(
name|entries
operator|==
literal|null
condition|)
block|{
return|return
name|handleStringData
argument_list|(
name|clipboard
operator|.
name|getString
argument_list|()
argument_list|)
return|;
block|}
return|return
name|handleBibTeXData
argument_list|(
operator|(
name|String
operator|)
name|entries
argument_list|)
return|;
block|}
DECL|method|handleBibTeXData (String entries)
specifier|private
name|List
argument_list|<
name|BibEntry
argument_list|>
name|handleBibTeXData
parameter_list|(
name|String
name|entries
parameter_list|)
block|{
name|BibtexParser
name|parser
init|=
operator|new
name|BibtexParser
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getImportFormatPreferences
argument_list|()
argument_list|,
name|Globals
operator|.
name|getFileUpdateMonitor
argument_list|()
argument_list|)
decl_stmt|;
try|try
block|{
return|return
name|parser
operator|.
name|parseEntries
argument_list|(
operator|new
name|ByteArrayInputStream
argument_list|(
name|entries
operator|.
name|getBytes
argument_list|(
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
argument_list|)
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|ParseException
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Could not paste"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
block|}
DECL|method|handleStringData (String data)
specifier|private
name|List
argument_list|<
name|BibEntry
argument_list|>
name|handleStringData
parameter_list|(
name|String
name|data
parameter_list|)
block|{
if|if
condition|(
name|data
operator|==
literal|null
operator|||
name|data
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
name|Optional
argument_list|<
name|DOI
argument_list|>
name|doi
init|=
name|DOI
operator|.
name|parse
argument_list|(
name|data
argument_list|)
decl_stmt|;
if|if
condition|(
name|doi
operator|.
name|isPresent
argument_list|()
condition|)
block|{
return|return
name|fetchByDOI
argument_list|(
name|doi
operator|.
name|get
argument_list|()
argument_list|)
return|;
block|}
return|return
name|tryImportFormats
argument_list|(
name|data
argument_list|)
return|;
block|}
DECL|method|tryImportFormats (String data)
specifier|private
name|List
argument_list|<
name|BibEntry
argument_list|>
name|tryImportFormats
parameter_list|(
name|String
name|data
parameter_list|)
block|{
try|try
block|{
name|UnknownFormatImport
name|unknownFormatImport
init|=
name|importFormatReader
operator|.
name|importUnknownFormat
argument_list|(
name|data
argument_list|)
decl_stmt|;
return|return
name|unknownFormatImport
operator|.
name|parserResult
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|ImportException
name|ignored
parameter_list|)
block|{
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
block|}
DECL|method|fetchByDOI (DOI doi)
specifier|private
name|List
argument_list|<
name|BibEntry
argument_list|>
name|fetchByDOI
parameter_list|(
name|DOI
name|doi
parameter_list|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Found DOI in clipboard"
argument_list|)
expr_stmt|;
try|try
block|{
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|entry
init|=
operator|new
name|DoiFetcher
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getImportFormatPreferences
argument_list|()
argument_list|)
operator|.
name|performSearchById
argument_list|(
name|doi
operator|.
name|getDOI
argument_list|()
argument_list|)
decl_stmt|;
return|return
name|OptionalUtil
operator|.
name|toList
argument_list|(
name|entry
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|FetcherException
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Error while fetching"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
block|}
block|}
end_class

end_unit

