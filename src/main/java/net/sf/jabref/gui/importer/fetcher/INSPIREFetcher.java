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
name|UnsupportedEncodingException
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
name|net
operator|.
name|URLEncoder
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
name|importer
operator|.
name|util
operator|.
name|INSPIREBibtexFilterReader
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
comment|/**  *  * This class allows to access the Slac INSPIRE database. It is just a port of the original SPIRES Fetcher.  *  * It can either be a GeneralFetcher to pose requests to the database or fetch individual entries.  *  * @author Fedor Bezrukov  * @author Sheer El-Showk  *  * @version $Id$  *  */
end_comment

begin_class
DECL|class|INSPIREFetcher
specifier|public
class|class
name|INSPIREFetcher
implements|implements
name|EntryFetcher
block|{
DECL|field|INSPIRE_HOST
specifier|private
specifier|static
specifier|final
name|String
name|INSPIRE_HOST
init|=
literal|"inspirehep.net"
decl_stmt|;
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
name|INSPIREFetcher
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * Construct the query URL      *      * NOTE: we truncate at 1000 returned entries but its likely INSPIRE returns fewer anyway. This shouldn't be a      * problem since users should probably do more specific searches.      *      * @param key The key of the OAI2 entry that the url should point to.      *      * @return a String denoting the query URL      */
DECL|method|constructUrl (String key)
specifier|private
name|String
name|constructUrl
parameter_list|(
name|String
name|key
parameter_list|)
block|{
name|String
name|identifier
decl_stmt|;
try|try
block|{
name|identifier
operator|=
name|URLEncoder
operator|.
name|encode
argument_list|(
name|key
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
catch|catch
parameter_list|(
name|UnsupportedEncodingException
name|e
parameter_list|)
block|{
return|return
literal|""
return|;
block|}
comment|// At least 87 characters
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|(
literal|87
argument_list|)
operator|.
name|append
argument_list|(
literal|"http://"
argument_list|)
operator|.
name|append
argument_list|(
name|INSPIREFetcher
operator|.
name|INSPIRE_HOST
argument_list|)
operator|.
name|append
argument_list|(
literal|"/search?ln=en&ln=en&p=find+"
argument_list|)
operator|.
name|append
argument_list|(
name|identifier
argument_list|)
operator|.
name|append
argument_list|(
literal|"&action_search=Search&sf=&so=d&rm=&rg=1000&sc=0&of=hx"
argument_list|)
decl_stmt|;
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Inspire URL: "
operator|+
name|sb
operator|+
literal|"\n"
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Constructs a INSPIRE query url from slaccitation field      *      * @param slaccitation      * @return query string      *      *         public static String constructUrlFromSlaccitation(String slaccitation) { String cmd = "j"; String key =      *         slaccitation.replaceAll("^%%CITATION = ", "").replaceAll( ";%%$", ""); if (key.matches("^\\w*-\\w*[ /].*"      *         )) cmd = "eprint"; try { key = URLEncoder.encode(key, "UTF-8"); } catch (UnsupportedEncodingException e)      *         { } StringBuffer sb = new StringBuffer("http://").append(INSPIRE_HOST) .append("/");      *         sb.append("spires/find/hep/www").append("?"); sb.append("rawcmd=find+").append(cmd).append("+");      *         sb.append(key); return sb.toString(); }      *      *         /** Construct an INSPIRE query url from eprint field      *      * @param eprint      * @return query string      *      *         public static String constructUrlFromEprint(String eprint) { String key = eprint.replaceAll(" [.*]$",      *         ""); try { key = URLEncoder.encode(key, "UTF-8"); } catch (UnsupportedEncodingException e) { return ""; }      *         StringBuffer sb = new StringBuffer("http://").append(INSPIRE_HOST) .append("/");      *         sb.append("spires/find/hep/www").append("?"); sb.append("rawcmd=find+eprint+"); sb.append(key); return      *         sb.toString(); }      */
comment|/**      * Import an entry from an OAI2 archive. The BibEntry provided has to have the field OAI2_IDENTIFIER_FIELD set to      * the search string.      *      * @param key The OAI2 key to fetch from ArXiv.      * @return The imported BibEntry or null if none.      */
DECL|method|importInspireEntries (String key, OutputPrinter frame)
specifier|private
name|BibDatabase
name|importInspireEntries
parameter_list|(
name|String
name|key
parameter_list|,
name|OutputPrinter
name|frame
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
name|HttpURLConnection
name|conn
init|=
operator|(
name|HttpURLConnection
operator|)
operator|new
name|URL
argument_list|(
name|url
argument_list|)
operator|.
name|openConnection
argument_list|()
decl_stmt|;
name|conn
operator|.
name|setRequestProperty
argument_list|(
literal|"User-Agent"
argument_list|,
literal|"JabRef"
argument_list|)
expr_stmt|;
name|InputStream
name|inputStream
init|=
name|conn
operator|.
name|getInputStream
argument_list|()
decl_stmt|;
try|try
init|(
name|INSPIREBibtexFilterReader
name|reader
init|=
operator|new
name|INSPIREBibtexFilterReader
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
name|inputStream
argument_list|,
name|Charset
operator|.
name|forName
argument_list|(
literal|"UTF-8"
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
name|Globals
operator|.
name|prefs
operator|.
name|getImportFormatPreferences
argument_list|()
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
name|RuntimeException
decl||
name|IOException
name|e
parameter_list|)
block|{
name|frame
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
return|return
literal|null
return|;
block|}
comment|/*      * @see net.sf.jabref.imports.fetcher.EntryFetcher      */
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
name|FETCHER_INSPIRE
return|;
block|}
annotation|@
name|Override
DECL|method|getOptionsPanel ()
specifier|public
name|JPanel
name|getOptionsPanel
parameter_list|()
block|{
comment|// we have no additional options
return|return
literal|null
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
literal|"INSPIRE"
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
comment|/*      * @see java.lang.Runnable      */
annotation|@
name|Override
DECL|method|processQuery (String query, ImportInspector dialog, OutputPrinter frame)
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
name|frame
parameter_list|)
block|{
try|try
block|{
name|frame
operator|.
name|setStatus
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Fetching entries from Inspire"
argument_list|)
argument_list|)
expr_stmt|;
comment|/* query the archive and load the results into the BibEntry */
name|BibDatabase
name|bd
init|=
name|importInspireEntries
argument_list|(
name|query
argument_list|,
name|frame
argument_list|)
decl_stmt|;
name|frame
operator|.
name|setStatus
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Adding fetched entries"
argument_list|)
argument_list|)
expr_stmt|;
comment|/* add the entry to the inspection dialog */
if|if
condition|(
name|bd
operator|==
literal|null
condition|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Error while fetching from Inspire"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|bd
operator|.
name|getEntries
argument_list|()
operator|.
name|forEach
argument_list|(
name|dialog
operator|::
name|addEntry
argument_list|)
expr_stmt|;
block|}
comment|/* inform the inspection dialog, that we're done */
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|frame
operator|.
name|showMessage
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error while fetching from %0"
argument_list|,
literal|"Inspire"
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
literal|"Error while fetching from Inspire"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
return|return
literal|true
return|;
block|}
block|}
end_class

end_unit

