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
name|net
operator|.
name|URLDownload
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
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
name|StringTokenizer
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Matcher
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
import|;
end_import

begin_class
DECL|class|JSTORFetcher2
specifier|public
class|class
name|JSTORFetcher2
implements|implements
name|EntryFetcher
block|{
DECL|field|MAX_PAGES_TO_LOAD
specifier|protected
specifier|static
name|int
name|MAX_PAGES_TO_LOAD
init|=
literal|8
decl_stmt|;
DECL|field|JSTOR_URL
specifier|protected
specifier|static
specifier|final
name|String
name|JSTOR_URL
init|=
literal|"http://www.jstor.org"
decl_stmt|;
DECL|field|SEARCH_URL
specifier|protected
specifier|static
specifier|final
name|String
name|SEARCH_URL
init|=
name|JSTOR_URL
operator|+
literal|"/action/doBasicSearch?Query="
decl_stmt|;
DECL|field|SEARCH_URL_END
specifier|protected
specifier|static
specifier|final
name|String
name|SEARCH_URL_END
init|=
literal|"&x=0&y=0&wc=on"
decl_stmt|;
DECL|field|SINGLE_CIT_ENC
specifier|protected
specifier|static
specifier|final
name|String
name|SINGLE_CIT_ENC
init|=
literal|"http%3A%2F%2Fwww.jstor.org%2Faction%2FexportSingleCitation%3FsingleCitation"
operator|+
literal|"%3Dtrue%26suffix%3D"
decl_stmt|;
DECL|field|BIBSONOMY_SCRAPER
specifier|protected
specifier|static
specifier|final
name|String
name|BIBSONOMY_SCRAPER
init|=
literal|"http://scraper.bibsonomy.org/service?url="
decl_stmt|;
DECL|field|BIBSONOMY_SCRAPER_POST
specifier|protected
specifier|static
specifier|final
name|String
name|BIBSONOMY_SCRAPER_POST
init|=
literal|"&format=bibtex"
decl_stmt|;
DECL|field|idPattern
specifier|protected
specifier|static
specifier|final
name|Pattern
name|idPattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"<a class=\"title\" href=\"/stable/(\\d+)\\?"
argument_list|)
decl_stmt|;
DECL|field|nextPagePattern
specifier|protected
specifier|static
specifier|final
name|Pattern
name|nextPagePattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"<a href=\"(.*)\">Next&gt;"
argument_list|)
decl_stmt|;
DECL|field|stopFetching
specifier|protected
name|boolean
name|stopFetching
init|=
literal|false
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
DECL|method|stopFetching ()
specifier|public
name|void
name|stopFetching
parameter_list|()
block|{
name|stopFetching
operator|=
literal|true
expr_stmt|;
block|}
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
name|stopFetching
operator|=
literal|false
expr_stmt|;
try|try
block|{
name|List
argument_list|<
name|String
argument_list|>
name|citations
init|=
name|getCitations
argument_list|(
name|query
argument_list|)
decl_stmt|;
if|if
condition|(
name|citations
operator|.
name|size
argument_list|()
operator|==
literal|0
condition|)
block|{
name|status
operator|.
name|showMessage
argument_list|(
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
return|return
literal|false
return|;
block|}
name|int
name|i
init|=
literal|0
decl_stmt|;
for|for
control|(
name|String
name|cit
range|:
name|citations
control|)
block|{
if|if
condition|(
name|stopFetching
condition|)
break|break;
name|BibtexEntry
name|entry
init|=
name|getSingleCitation
argument_list|(
name|cit
argument_list|)
decl_stmt|;
if|if
condition|(
name|entry
operator|!=
literal|null
condition|)
name|dialog
operator|.
name|addEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|dialog
operator|.
name|setProgress
argument_list|(
operator|++
name|i
argument_list|,
name|citations
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
literal|true
return|;
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
name|status
operator|.
name|showMessage
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
return|return
literal|false
return|;
block|}
comment|/**      *      * @param query      *            The search term to query JStor for.      * @return a list of IDs      * @throws java.io.IOException      */
DECL|method|getCitations (String query)
specifier|protected
name|List
argument_list|<
name|String
argument_list|>
name|getCitations
parameter_list|(
name|String
name|query
parameter_list|)
throws|throws
name|IOException
block|{
name|String
name|urlQuery
decl_stmt|;
name|ArrayList
argument_list|<
name|String
argument_list|>
name|ids
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
try|try
block|{
name|urlQuery
operator|=
name|SEARCH_URL
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
name|SEARCH_URL_END
expr_stmt|;
name|int
name|count
init|=
literal|1
decl_stmt|;
name|String
name|nextPage
init|=
literal|null
decl_stmt|;
while|while
condition|(
operator|(
operator|(
name|nextPage
operator|=
name|getCitationsFromUrl
argument_list|(
name|urlQuery
argument_list|,
name|ids
argument_list|)
operator|)
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|count
operator|<
name|MAX_PAGES_TO_LOAD
operator|)
condition|)
block|{
name|urlQuery
operator|=
name|nextPage
expr_stmt|;
name|count
operator|++
expr_stmt|;
block|}
return|return
name|ids
return|;
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
block|}
DECL|method|getCitationsFromUrl (String urlQuery, List<String> ids)
specifier|protected
name|String
name|getCitationsFromUrl
parameter_list|(
name|String
name|urlQuery
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|ids
parameter_list|)
throws|throws
name|IOException
block|{
name|URL
name|url
init|=
operator|new
name|URL
argument_list|(
name|urlQuery
argument_list|)
decl_stmt|;
name|URLDownload
name|ud
init|=
operator|new
name|URLDownload
argument_list|(
name|url
argument_list|)
decl_stmt|;
name|ud
operator|.
name|download
argument_list|()
expr_stmt|;
name|String
name|cont
init|=
name|ud
operator|.
name|getStringContent
argument_list|()
decl_stmt|;
name|String
name|entirePage
init|=
name|cont
decl_stmt|;
name|Matcher
name|m
init|=
name|idPattern
operator|.
name|matcher
argument_list|(
name|cont
argument_list|)
decl_stmt|;
while|while
condition|(
name|m
operator|.
name|find
argument_list|()
condition|)
block|{
name|ids
operator|.
name|add
argument_list|(
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|cont
operator|=
name|cont
operator|.
name|substring
argument_list|(
name|m
operator|.
name|end
argument_list|()
argument_list|)
expr_stmt|;
name|m
operator|=
name|idPattern
operator|.
name|matcher
argument_list|(
name|cont
argument_list|)
expr_stmt|;
block|}
name|m
operator|=
name|nextPagePattern
operator|.
name|matcher
argument_list|(
name|entirePage
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|m
operator|.
name|find
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|m
operator|.
name|find
argument_list|()
condition|)
block|{
name|String
name|newQuery
init|=
name|JSTOR_URL
operator|+
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
decl_stmt|;
return|return
name|newQuery
return|;
block|}
else|else
return|return
literal|null
return|;
block|}
DECL|method|getSingleCitation (String cit)
specifier|protected
name|BibtexEntry
name|getSingleCitation
parameter_list|(
name|String
name|cit
parameter_list|)
block|{
name|String
name|jstorEntryUrl
init|=
name|SINGLE_CIT_ENC
operator|+
name|cit
decl_stmt|;
try|try
block|{
name|URL
name|url
init|=
operator|new
name|URL
argument_list|(
name|BIBSONOMY_SCRAPER
operator|+
name|jstorEntryUrl
operator|+
name|BIBSONOMY_SCRAPER_POST
argument_list|)
decl_stmt|;
name|URLDownload
name|ud
init|=
operator|new
name|URLDownload
argument_list|(
name|url
argument_list|)
decl_stmt|;
name|ud
operator|.
name|download
argument_list|()
expr_stmt|;
name|String
name|bibtex
init|=
name|ud
operator|.
name|getStringContent
argument_list|()
decl_stmt|;
name|BibtexParser
name|bp
init|=
operator|new
name|BibtexParser
argument_list|(
operator|new
name|StringReader
argument_list|(
name|bibtex
argument_list|)
argument_list|)
decl_stmt|;
name|ParserResult
name|pr
init|=
name|bp
operator|.
name|parse
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|pr
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|pr
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntryCount
argument_list|()
operator|>
literal|0
operator|)
condition|)
block|{
return|return
name|pr
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
return|;
block|}
else|else
return|return
literal|null
return|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
comment|/**      * evaluates the 'Set-Cookie'-Header of a HTTP response      *      * @param name      *            key of a cookie value      * @param conn      *            URLConnection      * @return cookie value referenced by the key. null if key not found      * @throws java.io.IOException      */
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
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Cookie: '"
operator|+
name|headerValue
operator|+
literal|"'"
argument_list|)
expr_stmt|;
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

