begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.importer.fetcher
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|importer
operator|.
name|fetcher
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|BufferedInputStream
import|;
end_import

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
name|FileNotFoundException
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
name|InputStreamReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|HttpURLConnection
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
name|javax
operator|.
name|swing
operator|.
name|JOptionPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JPanel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|stream
operator|.
name|XMLInputFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|stream
operator|.
name|XMLStreamException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|stream
operator|.
name|XMLStreamReader
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
name|help
operator|.
name|HelpFile
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
name|ImportFormatPreferences
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
name|ImportInspector
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
name|OutputPrinter
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
name|ParserResult
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
name|l10n
operator|.
name|Localization
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
name|FieldName
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

begin_comment
comment|/**  *  * This class handles accessing and obtaining BibTeX entry  * from ADS(The NASA Astrophysics Data System).  * Fetching using DOI(Document Object Identifier) is only supported.  *  * @author Ryo IGARASHI  */
end_comment

begin_class
DECL|class|ADSFetcher
specifier|public
class|class
name|ADSFetcher
implements|implements
name|EntryFetcher
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
name|ADSFetcher
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Override
DECL|method|getOptionsPanel ()
specifier|public
name|JPanel
name|getOptionsPanel
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|getHelpPage ()
specifier|public
name|HelpFile
name|getHelpPage
parameter_list|()
block|{
return|return
name|HelpFile
operator|.
name|FETCHER_ADS
return|;
block|}
annotation|@
name|Override
DECL|method|getTitle ()
specifier|public
name|String
name|getTitle
parameter_list|()
block|{
return|return
literal|"ADS from ADS-DOI"
return|;
block|}
annotation|@
name|Override
DECL|method|processQuery (String query, ImportInspector dialog, OutputPrinter status)
specifier|public
name|boolean
name|processQuery
parameter_list|(
name|String
name|query
parameter_list|,
name|ImportInspector
name|dialog
parameter_list|,
name|OutputPrinter
name|status
parameter_list|)
block|{
try|try
block|{
comment|/* Remove "doi:" scheme identifier */
comment|/* Allow fetching only 1 key */
name|String
name|key
init|=
name|query
operator|.
name|replaceAll
argument_list|(
literal|"^(doi:|DOI:)"
argument_list|,
literal|""
argument_list|)
decl_stmt|;
comment|/* Query ADS and load the results into the BibDatabase */
name|status
operator|.
name|setStatus
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Processing %0"
argument_list|,
name|key
argument_list|)
argument_list|)
expr_stmt|;
name|BibDatabase
name|bd
init|=
name|importADSEntries
argument_list|(
name|key
argument_list|,
name|status
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|bd
operator|!=
literal|null
operator|)
operator|&&
name|bd
operator|.
name|hasEntries
argument_list|()
condition|)
block|{
comment|/* Add the entry to the inspection dialog */
for|for
control|(
name|BibEntry
name|entry
range|:
name|bd
operator|.
name|getEntries
argument_list|()
control|)
block|{
name|importADSAbstract
argument_list|(
name|key
argument_list|,
name|entry
argument_list|,
name|status
argument_list|)
expr_stmt|;
name|dialog
operator|.
name|addEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
return|return
literal|false
return|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|status
operator|.
name|setStatus
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error while fetching from %0"
argument_list|,
literal|"ADS"
argument_list|)
operator|+
literal|": "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Error while fetching from ADS"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
return|return
literal|true
return|;
block|}
annotation|@
name|Override
DECL|method|stopFetching ()
specifier|public
name|void
name|stopFetching
parameter_list|()
block|{
comment|// Do nothing
block|}
DECL|method|importADSEntries (String key, OutputPrinter status)
specifier|private
name|BibDatabase
name|importADSEntries
parameter_list|(
name|String
name|key
parameter_list|,
name|OutputPrinter
name|status
parameter_list|)
block|{
name|String
name|url
init|=
name|constructUrl
argument_list|(
name|key
argument_list|)
decl_stmt|;
try|try
block|{
name|URL
name|ADSUrl
init|=
operator|new
name|URL
argument_list|(
name|url
operator|+
literal|"&data_type=BIBTEX"
argument_list|)
decl_stmt|;
name|HttpURLConnection
name|ADSConnection
init|=
operator|(
name|HttpURLConnection
operator|)
name|ADSUrl
operator|.
name|openConnection
argument_list|()
decl_stmt|;
name|ADSConnection
operator|.
name|setRequestProperty
argument_list|(
literal|"User-Agent"
argument_list|,
literal|"JabRef"
argument_list|)
expr_stmt|;
try|try
init|(
name|BufferedReader
name|reader
init|=
operator|new
name|BufferedReader
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
name|ADSConnection
operator|.
name|getInputStream
argument_list|()
argument_list|,
name|Charset
operator|.
name|forName
argument_list|(
literal|"ISO-8859-1"
argument_list|)
argument_list|)
argument_list|)
init|)
block|{
name|ParserResult
name|pr
init|=
name|BibtexParser
operator|.
name|parse
argument_list|(
name|reader
argument_list|,
name|ImportFormatPreferences
operator|.
name|fromPreferences
argument_list|(
name|Globals
operator|.
name|prefs
argument_list|)
argument_list|)
decl_stmt|;
return|return
name|pr
operator|.
name|getDatabase
argument_list|()
return|;
block|}
block|}
catch|catch
parameter_list|(
name|FileNotFoundException
name|e
parameter_list|)
block|{
name|status
operator|.
name|showMessage
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"'%0' is not a valid ADS bibcode."
argument_list|,
name|key
argument_list|)
operator|+
literal|"\n\n"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Note: A full text search is currently not supported for %0"
argument_list|,
name|getTitle
argument_list|()
argument_list|)
argument_list|,
name|getTitle
argument_list|()
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"File not found"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|status
operator|.
name|showMessage
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"An exception occurred while accessing '%0'"
argument_list|,
name|url
argument_list|)
operator|+
literal|"\n\n"
operator|+
name|e
argument_list|,
name|getTitle
argument_list|()
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Problem accessing URL"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|RuntimeException
name|e
parameter_list|)
block|{
name|status
operator|.
name|showMessage
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"An error occurred while fetching from ADS (%0):"
argument_list|,
name|url
argument_list|)
operator|+
literal|"\n\n"
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|getTitle
argument_list|()
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Problem fetching from ADS"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
return|return
literal|null
return|;
block|}
DECL|method|constructUrl (String key)
specifier|private
specifier|static
name|String
name|constructUrl
parameter_list|(
name|String
name|key
parameter_list|)
block|{
return|return
literal|"http://adsabs.harvard.edu/doi/"
operator|+
name|key
return|;
block|}
DECL|method|importADSAbstract (String key, BibEntry entry, OutputPrinter status)
specifier|private
name|void
name|importADSAbstract
parameter_list|(
name|String
name|key
parameter_list|,
name|BibEntry
name|entry
parameter_list|,
name|OutputPrinter
name|status
parameter_list|)
block|{
comment|/* TODO: construct ADSUrl from BibEntry */
name|String
name|url
init|=
name|constructUrl
argument_list|(
name|key
argument_list|)
decl_stmt|;
try|try
block|{
name|URL
name|ADSUrl
init|=
operator|new
name|URL
argument_list|(
name|url
operator|+
literal|"&data_type=XML"
argument_list|)
decl_stmt|;
name|HttpURLConnection
name|ADSConnection
init|=
operator|(
name|HttpURLConnection
operator|)
name|ADSUrl
operator|.
name|openConnection
argument_list|()
decl_stmt|;
name|ADSConnection
operator|.
name|setRequestProperty
argument_list|(
literal|"User-Agent"
argument_list|,
literal|"JabRef"
argument_list|)
expr_stmt|;
name|BufferedInputStream
name|bis
init|=
operator|new
name|BufferedInputStream
argument_list|(
name|ADSConnection
operator|.
name|getInputStream
argument_list|()
argument_list|)
decl_stmt|;
name|XMLInputFactory
name|factory
init|=
name|XMLInputFactory
operator|.
name|newInstance
argument_list|()
decl_stmt|;
name|XMLStreamReader
name|reader
init|=
name|factory
operator|.
name|createXMLStreamReader
argument_list|(
name|bis
argument_list|)
decl_stmt|;
name|boolean
name|isAbstract
init|=
literal|false
decl_stmt|;
name|StringBuilder
name|abstractSB
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
while|while
condition|(
name|reader
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|reader
operator|.
name|next
argument_list|()
expr_stmt|;
if|if
condition|(
name|reader
operator|.
name|isStartElement
argument_list|()
operator|&&
name|FieldName
operator|.
name|ABSTRACT
operator|.
name|equals
argument_list|(
name|reader
operator|.
name|getLocalName
argument_list|()
argument_list|)
condition|)
block|{
name|isAbstract
operator|=
literal|true
expr_stmt|;
block|}
if|if
condition|(
name|isAbstract
operator|&&
name|reader
operator|.
name|isCharacters
argument_list|()
condition|)
block|{
name|abstractSB
operator|.
name|append
argument_list|(
name|reader
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|isAbstract
operator|&&
name|reader
operator|.
name|isEndElement
argument_list|()
condition|)
block|{
name|isAbstract
operator|=
literal|false
expr_stmt|;
block|}
block|}
name|String
name|abstractText
init|=
name|abstractSB
operator|.
name|toString
argument_list|()
decl_stmt|;
name|abstractText
operator|=
name|abstractText
operator|.
name|replace
argument_list|(
literal|"\n"
argument_list|,
literal|" "
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|ABSTRACT
argument_list|,
name|abstractText
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|XMLStreamException
name|e
parameter_list|)
block|{
name|status
operator|.
name|showMessage
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"An error occurred while parsing abstract"
argument_list|)
argument_list|,
name|getTitle
argument_list|()
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|status
operator|.
name|showMessage
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"An exception occurred while accessing '%0'"
argument_list|,
name|url
argument_list|)
operator|+
literal|"\n\n"
operator|+
name|e
argument_list|,
name|getTitle
argument_list|()
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|RuntimeException
name|e
parameter_list|)
block|{
name|status
operator|.
name|showMessage
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"An error occurred while fetching from ADS (%0):"
argument_list|,
name|url
argument_list|)
operator|+
literal|"\n\n"
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|getTitle
argument_list|()
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

