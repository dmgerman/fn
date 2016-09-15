begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui
package|package
name|net
operator|.
name|sf
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
name|awt
operator|.
name|Toolkit
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|datatransfer
operator|.
name|Clipboard
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|datatransfer
operator|.
name|ClipboardOwner
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|datatransfer
operator|.
name|DataFlavor
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|datatransfer
operator|.
name|StringSelection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|datatransfer
operator|.
name|Transferable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|datatransfer
operator|.
name|UnsupportedFlavorException
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
name|StringReader
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|Globals
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
name|logic
operator|.
name|importer
operator|.
name|FetcherException
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
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|DOI
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
name|model
operator|.
name|database
operator|.
name|BibDatabase
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
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_class
DECL|class|ClipBoardManager
specifier|public
class|class
name|ClipBoardManager
implements|implements
name|ClipboardOwner
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|ClipBoardManager
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|CLIPBOARD
specifier|private
specifier|static
specifier|final
name|Clipboard
name|CLIPBOARD
init|=
name|Toolkit
operator|.
name|getDefaultToolkit
argument_list|()
operator|.
name|getSystemClipboard
argument_list|()
decl_stmt|;
comment|/**      * Empty implementation of the ClipboardOwner interface.      */
annotation|@
name|Override
DECL|method|lostOwnership (Clipboard aClipboard, Transferable aContents)
specifier|public
name|void
name|lostOwnership
parameter_list|(
name|Clipboard
name|aClipboard
parameter_list|,
name|Transferable
name|aContents
parameter_list|)
block|{
comment|//do nothing
block|}
comment|/**      * Place a String on the clipboard, and make this class the      * owner of the Clipboard's contents.      */
DECL|method|setClipboardContents (String aString)
specifier|public
name|void
name|setClipboardContents
parameter_list|(
name|String
name|aString
parameter_list|)
block|{
name|StringSelection
name|stringSelection
init|=
operator|new
name|StringSelection
argument_list|(
name|aString
argument_list|)
decl_stmt|;
name|CLIPBOARD
operator|.
name|setContents
argument_list|(
name|stringSelection
argument_list|,
name|this
argument_list|)
expr_stmt|;
block|}
comment|/**      * Get the String residing on the clipboard.      *      * @return any text found on the Clipboard; if none found, return an      * empty String.      */
DECL|method|getClipboardContents ()
specifier|public
name|String
name|getClipboardContents
parameter_list|()
block|{
name|String
name|result
init|=
literal|""
decl_stmt|;
comment|//odd: the Object param of getContents is not currently used
name|Transferable
name|contents
init|=
name|CLIPBOARD
operator|.
name|getContents
argument_list|(
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|contents
operator|!=
literal|null
operator|)
operator|&&
name|contents
operator|.
name|isDataFlavorSupported
argument_list|(
name|DataFlavor
operator|.
name|stringFlavor
argument_list|)
condition|)
block|{
try|try
block|{
name|result
operator|=
operator|(
name|String
operator|)
name|contents
operator|.
name|getTransferData
argument_list|(
name|DataFlavor
operator|.
name|stringFlavor
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsupportedFlavorException
decl||
name|IOException
name|e
parameter_list|)
block|{
comment|//highly unlikely since we are using a standard DataFlavor
name|LOGGER
operator|.
name|info
argument_list|(
literal|"problem with getting clipboard contents"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|result
return|;
block|}
DECL|method|extractBibEntriesFromClipboard ()
specifier|public
name|List
argument_list|<
name|BibEntry
argument_list|>
name|extractBibEntriesFromClipboard
parameter_list|()
block|{
comment|// Get clipboard contents, and see if TransferableBibtexEntry is among the content flavors offered
name|Transferable
name|content
init|=
name|CLIPBOARD
operator|.
name|getContents
argument_list|(
literal|null
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|result
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
if|if
condition|(
name|content
operator|.
name|isDataFlavorSupported
argument_list|(
name|TransferableBibtexEntry
operator|.
name|entryFlavor
argument_list|)
condition|)
block|{
comment|// We have determined that the clipboard data is a set of entries.
try|try
block|{
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
name|List
argument_list|<
name|BibEntry
argument_list|>
name|contents
init|=
operator|(
name|List
argument_list|<
name|BibEntry
argument_list|>
operator|)
name|content
operator|.
name|getTransferData
argument_list|(
name|TransferableBibtexEntry
operator|.
name|entryFlavor
argument_list|)
decl_stmt|;
name|result
operator|=
name|contents
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsupportedFlavorException
decl||
name|ClassCastException
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Could not paste this type"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Could not paste"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|content
operator|.
name|isDataFlavorSupported
argument_list|(
name|DataFlavor
operator|.
name|stringFlavor
argument_list|)
condition|)
block|{
try|try
block|{
name|String
name|data
init|=
operator|(
name|String
operator|)
name|content
operator|.
name|getTransferData
argument_list|(
name|DataFlavor
operator|.
name|stringFlavor
argument_list|)
decl_stmt|;
comment|// fetch from doi
if|if
condition|(
name|DOI
operator|.
name|build
argument_list|(
name|data
argument_list|)
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Found DOI in clipboard"
argument_list|)
expr_stmt|;
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
operator|new
name|DOI
argument_list|(
name|data
argument_list|)
operator|.
name|getDOI
argument_list|()
argument_list|)
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
else|else
block|{
comment|// parse bibtex string
name|BibtexParser
name|bp
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
argument_list|)
decl_stmt|;
name|BibDatabase
name|db
init|=
name|bp
operator|.
name|parse
argument_list|(
operator|new
name|StringReader
argument_list|(
name|data
argument_list|)
argument_list|)
operator|.
name|getDatabase
argument_list|()
decl_stmt|;
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Parsed "
operator|+
name|db
operator|.
name|getEntryCount
argument_list|()
operator|+
literal|" entries from clipboard text"
argument_list|)
expr_stmt|;
if|if
condition|(
name|db
operator|.
name|hasEntries
argument_list|()
condition|)
block|{
name|result
operator|=
name|db
operator|.
name|getEntries
argument_list|()
expr_stmt|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|UnsupportedFlavorException
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Could not parse this type"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Data is no longer available in the requested flavor"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
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
block|}
block|}
return|return
name|result
return|;
block|}
block|}
end_class

end_unit

