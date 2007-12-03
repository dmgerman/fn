begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.imports
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
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
name|MalformedURLException
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
name|URLConnection
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
name|StringTokenizer
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
name|BibtexEntry
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
name|GUIGlobals
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
name|JabRefFrame
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
name|gui
operator|.
name|ImportInspectionDialog
import|;
end_import

begin_comment
comment|/**  * This class fetches up to 200 citations from JStor by a given search query. It  * communicates with jstor via HTTP and Cookies. The fetcher automates the  * following steps:  *<ol>  *<li>Do a basic search on www.jstor.org</li>  *<li>Save the first 200 hits</li>  *<li>Download the saved citations as bibtex</li>  *<li>Parse it with the BibtexParser</li>  *<li>Import the BibtexEntrys via the ImportInspectionDialog</li>  *</ol>  *   * @author Juliane Doege, Tobias Langner  */
end_comment

begin_class
DECL|class|JSTORFetcher
specifier|public
class|class
name|JSTORFetcher
implements|implements
name|EntryFetcher
block|{
comment|/**      * cookies can't save more than 200 citations      */
DECL|field|MAX_CITATIONS
specifier|protected
specifier|static
name|int
name|MAX_CITATIONS
init|=
literal|200
decl_stmt|;
comment|/**      * Cookie key for Jstor ticket (authentication)      */
DECL|field|COOKIE_TICKET
specifier|protected
specifier|static
specifier|final
name|String
name|COOKIE_TICKET
init|=
literal|"Jstor_Ticket"
decl_stmt|;
comment|/**      * location where the ticket is obtained      *       */
DECL|field|URL_TICKET
specifier|protected
specifier|static
specifier|final
name|String
name|URL_TICKET
init|=
literal|"http://www.jstor.org/search"
decl_stmt|;
comment|/**      * Cookie key for citations to be fetched      *       */
DECL|field|COOKIE_CITATIONS
specifier|protected
specifier|static
specifier|final
name|String
name|COOKIE_CITATIONS
init|=
literal|"Jstor_citations0"
decl_stmt|;
comment|/**      * location where to obtain the citations cookie      *       */
DECL|field|URL_BIBTEX
specifier|protected
specifier|static
specifier|final
name|String
name|URL_BIBTEX
init|=
literal|"http://www.jstor.org/browse/citations.txt?exportFormat=bibtex&exportAction=Display&frame=noframe&dpi=3&config=jstor&viewCitations=1&View=View"
decl_stmt|;
DECL|method|getHelpPage ()
specifier|public
name|String
name|getHelpPage
parameter_list|()
block|{
return|return
literal|"JSTOR.html"
return|;
block|}
DECL|method|getIcon ()
specifier|public
name|URL
name|getIcon
parameter_list|()
block|{
return|return
name|GUIGlobals
operator|.
name|getIconUrl
argument_list|(
literal|"www"
argument_list|)
return|;
block|}
DECL|method|getKeyName ()
specifier|public
name|String
name|getKeyName
parameter_list|()
block|{
return|return
literal|"Search JSTOR"
return|;
block|}
DECL|method|getOptionsPanel ()
specifier|public
name|JPanel
name|getOptionsPanel
parameter_list|()
block|{
comment|// No Options panel
return|return
literal|null
return|;
block|}
DECL|method|getTitle ()
specifier|public
name|String
name|getTitle
parameter_list|()
block|{
return|return
name|Globals
operator|.
name|menuTitle
argument_list|(
literal|"Search JSTOR"
argument_list|)
return|;
block|}
DECL|method|processQuery (String query, ImportInspectionDialog dialog, JabRefFrame frame)
specifier|public
name|void
name|processQuery
parameter_list|(
name|String
name|query
parameter_list|,
name|ImportInspectionDialog
name|dialog
parameter_list|,
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|dialog
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
try|try
block|{
comment|// First open a ticket with JStor
name|String
name|ticket
init|=
name|openTicket
argument_list|()
decl_stmt|;
comment|// Then execute the query
name|String
name|citations
init|=
name|getCitations
argument_list|(
name|ticket
argument_list|,
name|query
argument_list|)
decl_stmt|;
comment|// Last retrieve the Bibtex-entries of the citations found
name|Collection
argument_list|<
name|BibtexEntry
argument_list|>
name|entries
init|=
name|getBibtexEntries
argument_list|(
name|ticket
argument_list|,
name|citations
argument_list|)
decl_stmt|;
if|if
condition|(
name|entries
operator|.
name|size
argument_list|()
operator|==
literal|0
condition|)
block|{
name|dialog
operator|.
name|dispose
argument_list|()
expr_stmt|;
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"No entries found for the search string '%0'"
argument_list|,
name|query
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Search JSTOR"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|INFORMATION_MESSAGE
argument_list|)
expr_stmt|;
return|return;
block|}
name|dialog
operator|.
name|addEntries
argument_list|(
name|entries
argument_list|)
expr_stmt|;
name|dialog
operator|.
name|entryListComplete
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|frame
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Error while fetching from JSTOR"
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
block|}
block|}
comment|/**      * Given a ticket an a list of citations, retrieve BibtexEntries from JStor      *       * @param ticket      *            A valid ticket as returned by openTicket()      * @param citations      *            A list of citations as returned by getCitations()      * @return A collection of BibtexEntries parsed from the bibtex returned by      *         JStor.      * @throws IOException      *             Most probably related to a problem connecting to JStor.      */
DECL|method|getBibtexEntries (String ticket, String citations)
specifier|protected
name|Collection
argument_list|<
name|BibtexEntry
argument_list|>
name|getBibtexEntries
parameter_list|(
name|String
name|ticket
parameter_list|,
name|String
name|citations
parameter_list|)
throws|throws
name|IOException
block|{
try|try
block|{
name|URL
name|url
init|=
operator|new
name|URL
argument_list|(
name|URL_BIBTEX
argument_list|)
decl_stmt|;
name|URLConnection
name|conn
init|=
name|url
operator|.
name|openConnection
argument_list|()
decl_stmt|;
name|conn
operator|.
name|setRequestProperty
argument_list|(
literal|"Cookie"
argument_list|,
name|ticket
operator|+
literal|"; "
operator|+
name|citations
argument_list|)
expr_stmt|;
name|conn
operator|.
name|connect
argument_list|()
expr_stmt|;
name|BibtexParser
name|parser
init|=
operator|new
name|BibtexParser
argument_list|(
operator|new
name|BufferedReader
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
name|conn
operator|.
name|getInputStream
argument_list|()
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
return|return
name|parser
operator|.
name|parse
argument_list|()
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
name|MalformedURLException
name|e
parameter_list|)
block|{
comment|// Propagate...
throw|throw
operator|new
name|RuntimeException
argument_list|(
name|e
argument_list|)
throw|;
block|}
block|}
DECL|method|cancelled ()
specifier|public
name|void
name|cancelled
parameter_list|()
block|{
comment|// do nothing
block|}
DECL|method|done (int entriesImported)
specifier|public
name|void
name|done
parameter_list|(
name|int
name|entriesImported
parameter_list|)
block|{
comment|// do nothing
block|}
DECL|method|stopFetching ()
specifier|public
name|void
name|stopFetching
parameter_list|()
block|{
comment|// cannot be interrupted
block|}
comment|/**      *       * @return a Jstor ticket ID      * @throws IOException      */
DECL|method|openTicket ()
specifier|protected
name|String
name|openTicket
parameter_list|()
throws|throws
name|IOException
block|{
name|URL
name|url
init|=
operator|new
name|URL
argument_list|(
name|URL_TICKET
argument_list|)
decl_stmt|;
name|URLConnection
name|conn
init|=
name|url
operator|.
name|openConnection
argument_list|()
decl_stmt|;
return|return
name|getCookie
argument_list|(
name|COOKIE_TICKET
argument_list|,
name|conn
argument_list|)
return|;
block|}
comment|/**      * requires a valid JStor Ticket ID      *       * @param query      *            The search term to query JStor for.      * @param ticket      *            JStor ticket      * @return cookie value of the key JSTORFetcher.COOKIE_CITATIONS. null if      *         search is empty or ticket is invalid      * @throws IOException      */
DECL|method|getCitations (String ticket, String query)
specifier|protected
name|String
name|getCitations
parameter_list|(
name|String
name|ticket
parameter_list|,
name|String
name|query
parameter_list|)
throws|throws
name|IOException
block|{
name|String
name|urlQuery
decl_stmt|;
try|try
block|{
name|urlQuery
operator|=
literal|"http://www.jstor.org/search/BasicResults?hp="
operator|+
name|MAX_CITATIONS
operator|+
literal|"&si=1&gw=jtx&jtxsi=1&jcpsi=1&artsi=1&Query="
operator|+
name|URLEncoder
operator|.
name|encode
argument_list|(
name|query
argument_list|,
literal|"UTF-8"
argument_list|)
operator|+
literal|"&wc=on&citationAction=saveAll"
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsupportedEncodingException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|(
name|e
argument_list|)
throw|;
block|}
name|URL
name|url
init|=
operator|new
name|URL
argument_list|(
name|urlQuery
argument_list|)
decl_stmt|;
name|URLConnection
name|conn
init|=
name|url
operator|.
name|openConnection
argument_list|()
decl_stmt|;
name|conn
operator|.
name|setRequestProperty
argument_list|(
literal|"Cookie"
argument_list|,
name|ticket
argument_list|)
expr_stmt|;
return|return
name|getCookie
argument_list|(
name|COOKIE_CITATIONS
argument_list|,
name|conn
argument_list|)
return|;
block|}
comment|/**      * evaluates the 'Set-Cookie'-Header of a HTTP response      *       * @param name      *            key of a cookie value      * @param conn      *            URLConnection      * @return cookie value referenced by the key. null if key not found      * @throws IOException      */
DECL|method|getCookie (String name, URLConnection conn)
specifier|public
specifier|static
name|String
name|getCookie
parameter_list|(
name|String
name|name
parameter_list|,
name|URLConnection
name|conn
parameter_list|)
throws|throws
name|IOException
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
condition|;
name|i
operator|++
control|)
block|{
name|String
name|headerName
init|=
name|conn
operator|.
name|getHeaderFieldKey
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|String
name|headerValue
init|=
name|conn
operator|.
name|getHeaderField
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|headerName
operator|==
literal|null
operator|&&
name|headerValue
operator|==
literal|null
condition|)
block|{
comment|// No more headers
break|break;
block|}
if|if
condition|(
name|headerName
operator|!=
literal|null
operator|&&
name|headerName
operator|.
name|equals
argument_list|(
literal|"Set-Cookie"
argument_list|)
condition|)
block|{
if|if
condition|(
name|headerValue
operator|.
name|startsWith
argument_list|(
name|name
argument_list|)
condition|)
block|{
comment|// several key-value-pairs are separated by ';'
name|StringTokenizer
name|st
init|=
operator|new
name|StringTokenizer
argument_list|(
name|headerValue
argument_list|,
literal|"; "
argument_list|)
decl_stmt|;
while|while
condition|(
name|st
operator|.
name|hasMoreElements
argument_list|()
condition|)
block|{
name|String
name|token
init|=
name|st
operator|.
name|nextToken
argument_list|()
decl_stmt|;
if|if
condition|(
name|token
operator|.
name|startsWith
argument_list|(
name|name
argument_list|)
condition|)
block|{
return|return
name|token
return|;
block|}
block|}
block|}
block|}
block|}
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

