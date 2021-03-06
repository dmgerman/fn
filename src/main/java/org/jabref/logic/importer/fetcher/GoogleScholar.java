begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.importer.fetcher
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
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
name|StringReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|HttpCookie
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URISyntaxException
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

begin_import
import|import
name|org
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
name|FulltextFetcher
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
name|SearchBasedFetcher
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
name|l10n
operator|.
name|Localization
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
name|net
operator|.
name|URLDownload
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
name|field
operator|.
name|StandardField
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
name|DummyFileUpdateMonitor
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|http
operator|.
name|client
operator|.
name|utils
operator|.
name|URIBuilder
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jsoup
operator|.
name|Jsoup
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jsoup
operator|.
name|nodes
operator|.
name|Document
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jsoup
operator|.
name|select
operator|.
name|Elements
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

begin_comment
comment|/**  * FulltextFetcher implementation that attempts to find a PDF URL at GoogleScholar.  *  * Search String infos: https://scholar.google.com/intl/en/scholar/help.html#searching  */
end_comment

begin_class
DECL|class|GoogleScholar
specifier|public
class|class
name|GoogleScholar
implements|implements
name|FulltextFetcher
implements|,
name|SearchBasedFetcher
block|{
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
name|GoogleScholar
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|LINK_TO_BIB_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|LINK_TO_BIB_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"(https:\\/\\/scholar.googleusercontent.com\\/scholar.bib[^\"]*)"
argument_list|)
decl_stmt|;
DECL|field|BASIC_SEARCH_URL
specifier|private
specifier|static
specifier|final
name|String
name|BASIC_SEARCH_URL
init|=
literal|"https://scholar.google.com/scholar?"
decl_stmt|;
DECL|field|SEARCH_IN_TITLE_URL
specifier|private
specifier|static
specifier|final
name|String
name|SEARCH_IN_TITLE_URL
init|=
literal|"https://scholar.google.com//scholar?"
decl_stmt|;
DECL|field|NUM_RESULTS
specifier|private
specifier|static
specifier|final
name|int
name|NUM_RESULTS
init|=
literal|10
decl_stmt|;
DECL|field|importFormatPreferences
specifier|private
specifier|final
name|ImportFormatPreferences
name|importFormatPreferences
decl_stmt|;
DECL|method|GoogleScholar (ImportFormatPreferences importFormatPreferences)
specifier|public
name|GoogleScholar
parameter_list|(
name|ImportFormatPreferences
name|importFormatPreferences
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|importFormatPreferences
argument_list|)
expr_stmt|;
name|this
operator|.
name|importFormatPreferences
operator|=
name|importFormatPreferences
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|findFullText (BibEntry entry)
specifier|public
name|Optional
argument_list|<
name|URL
argument_list|>
name|findFullText
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
throws|throws
name|IOException
throws|,
name|FetcherException
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|Optional
argument_list|<
name|URL
argument_list|>
name|pdfLink
init|=
name|Optional
operator|.
name|empty
argument_list|()
decl_stmt|;
comment|// Search in title
if|if
condition|(
operator|!
name|entry
operator|.
name|hasField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|)
condition|)
block|{
return|return
name|pdfLink
return|;
block|}
try|try
block|{
comment|// title search
name|URIBuilder
name|uriBuilder
init|=
operator|new
name|URIBuilder
argument_list|(
name|SEARCH_IN_TITLE_URL
argument_list|)
decl_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"as_q"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
comment|// as_epq as exact phrase
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"as_epq"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|)
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
comment|// as_occt field to search in
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"as_occt"
argument_list|,
literal|"title"
argument_list|)
expr_stmt|;
name|pdfLink
operator|=
name|search
argument_list|(
name|uriBuilder
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|URISyntaxException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|FetcherException
argument_list|(
literal|"Building URI failed."
argument_list|,
name|e
argument_list|)
throw|;
block|}
return|return
name|pdfLink
return|;
block|}
annotation|@
name|Override
DECL|method|getTrustLevel ()
specifier|public
name|TrustLevel
name|getTrustLevel
parameter_list|()
block|{
return|return
name|TrustLevel
operator|.
name|META_SEARCH
return|;
block|}
DECL|method|search (String url)
specifier|private
name|Optional
argument_list|<
name|URL
argument_list|>
name|search
parameter_list|(
name|String
name|url
parameter_list|)
throws|throws
name|IOException
block|{
name|Optional
argument_list|<
name|URL
argument_list|>
name|pdfLink
init|=
name|Optional
operator|.
name|empty
argument_list|()
decl_stmt|;
name|Document
name|doc
init|=
name|Jsoup
operator|.
name|connect
argument_list|(
name|url
argument_list|)
operator|.
name|userAgent
argument_list|(
name|URLDownload
operator|.
name|USER_AGENT
argument_list|)
operator|.
name|get
argument_list|()
decl_stmt|;
comment|// Check results for PDF link
comment|// TODO: link always on first result or none?
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|NUM_RESULTS
condition|;
name|i
operator|++
control|)
block|{
name|Elements
name|link
init|=
name|doc
operator|.
name|select
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"div[data-rp=%S] div.gs_or_ggsm a"
argument_list|,
name|i
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|link
operator|.
name|first
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|String
name|target
init|=
name|link
operator|.
name|first
argument_list|()
operator|.
name|attr
argument_list|(
literal|"href"
argument_list|)
decl_stmt|;
comment|// link present?
if|if
condition|(
operator|!
name|target
operator|.
name|isEmpty
argument_list|()
operator|&&
operator|new
name|URLDownload
argument_list|(
name|target
argument_list|)
operator|.
name|isPdf
argument_list|()
condition|)
block|{
comment|// TODO: check title inside pdf + length?
comment|// TODO: report error function needed?! query -> result
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Fulltext PDF found @ Google: "
operator|+
name|target
argument_list|)
expr_stmt|;
name|pdfLink
operator|=
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|URL
argument_list|(
name|target
argument_list|)
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
block|}
return|return
name|pdfLink
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
literal|"Google Scholar"
return|;
block|}
annotation|@
name|Override
DECL|method|getHelpPage ()
specifier|public
name|Optional
argument_list|<
name|HelpFile
argument_list|>
name|getHelpPage
parameter_list|()
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|HelpFile
operator|.
name|FETCHER_GOOGLE_SCHOLAR
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|performSearch (String query)
specifier|public
name|List
argument_list|<
name|BibEntry
argument_list|>
name|performSearch
parameter_list|(
name|String
name|query
parameter_list|)
throws|throws
name|FetcherException
block|{
try|try
block|{
name|obtainAndModifyCookie
argument_list|()
expr_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|foundEntries
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
literal|10
argument_list|)
decl_stmt|;
name|URIBuilder
name|uriBuilder
init|=
operator|new
name|URIBuilder
argument_list|(
name|BASIC_SEARCH_URL
argument_list|)
decl_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"hl"
argument_list|,
literal|"en"
argument_list|)
expr_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"btnG"
argument_list|,
literal|"Search"
argument_list|)
expr_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"q"
argument_list|,
name|query
argument_list|)
expr_stmt|;
name|addHitsFromQuery
argument_list|(
name|foundEntries
argument_list|,
name|uriBuilder
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|foundEntries
operator|.
name|size
argument_list|()
operator|==
literal|10
condition|)
block|{
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"start"
argument_list|,
literal|"10"
argument_list|)
expr_stmt|;
name|addHitsFromQuery
argument_list|(
name|foundEntries
argument_list|,
name|uriBuilder
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|foundEntries
return|;
block|}
catch|catch
parameter_list|(
name|URISyntaxException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|FetcherException
argument_list|(
literal|"Error while fetching from "
operator|+
name|getName
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
comment|// if there are too much requests from the same IP adress google is answering with a 503 and redirecting to a captcha challenge
comment|// The caught IOException looks for example like this:
comment|// java.io.IOException: Server returned HTTP response code: 503 for URL: https://ipv4.google.com/sorry/index?continue=https://scholar.google.com/scholar%3Fhl%3Den%26btnG%3DSearch%26q%3Dbpmn&hl=en&q=CGMSBI0NBDkYuqy9wAUiGQDxp4NLQCWbIEY1HjpH5zFJhv4ANPGdWj0
if|if
condition|(
name|e
operator|.
name|getMessage
argument_list|()
operator|.
name|contains
argument_list|(
literal|"Server returned HTTP response code: 503 for URL"
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|FetcherException
argument_list|(
literal|"Fetching from Google Scholar failed."
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"This might be caused by reaching the traffic limitation of Google Scholar (see 'Help' for details)."
argument_list|)
argument_list|,
name|e
argument_list|)
throw|;
block|}
else|else
block|{
throw|throw
operator|new
name|FetcherException
argument_list|(
literal|"Error while fetching from "
operator|+
name|getName
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
block|}
DECL|method|addHitsFromQuery (List<BibEntry> entryList, String queryURL)
specifier|private
name|void
name|addHitsFromQuery
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entryList
parameter_list|,
name|String
name|queryURL
parameter_list|)
throws|throws
name|IOException
throws|,
name|FetcherException
block|{
name|String
name|content
init|=
operator|new
name|URLDownload
argument_list|(
name|queryURL
argument_list|)
operator|.
name|asString
argument_list|()
decl_stmt|;
name|Matcher
name|matcher
init|=
name|LINK_TO_BIB_PATTERN
operator|.
name|matcher
argument_list|(
name|content
argument_list|)
decl_stmt|;
while|while
condition|(
name|matcher
operator|.
name|find
argument_list|()
condition|)
block|{
name|String
name|citationsPageURL
init|=
name|matcher
operator|.
name|group
argument_list|()
operator|.
name|replace
argument_list|(
literal|"&amp;"
argument_list|,
literal|"&"
argument_list|)
decl_stmt|;
name|BibEntry
name|newEntry
init|=
name|downloadEntry
argument_list|(
name|citationsPageURL
argument_list|)
decl_stmt|;
name|entryList
operator|.
name|add
argument_list|(
name|newEntry
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|downloadEntry (String link)
specifier|private
name|BibEntry
name|downloadEntry
parameter_list|(
name|String
name|link
parameter_list|)
throws|throws
name|IOException
throws|,
name|FetcherException
block|{
name|String
name|downloadedContent
init|=
operator|new
name|URLDownload
argument_list|(
name|link
argument_list|)
operator|.
name|asString
argument_list|()
decl_stmt|;
name|BibtexParser
name|parser
init|=
operator|new
name|BibtexParser
argument_list|(
name|importFormatPreferences
argument_list|,
operator|new
name|DummyFileUpdateMonitor
argument_list|()
argument_list|)
decl_stmt|;
name|ParserResult
name|result
init|=
name|parser
operator|.
name|parse
argument_list|(
operator|new
name|StringReader
argument_list|(
name|downloadedContent
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|result
operator|==
literal|null
operator|)
operator|||
operator|(
name|result
operator|.
name|getDatabase
argument_list|()
operator|==
literal|null
operator|)
condition|)
block|{
throw|throw
operator|new
name|FetcherException
argument_list|(
literal|"Parsing entries from Google Scholar bib file failed."
argument_list|)
throw|;
block|}
else|else
block|{
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
name|size
argument_list|()
operator|!=
literal|1
condition|)
block|{
name|LOGGER
operator|.
name|debug
argument_list|(
name|entries
operator|.
name|size
argument_list|()
operator|+
literal|" entries found! ("
operator|+
name|link
operator|+
literal|")"
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|FetcherException
argument_list|(
literal|"Parsing entries from Google Scholar bib file failed."
argument_list|)
throw|;
block|}
else|else
block|{
name|BibEntry
name|entry
init|=
name|entries
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
return|return
name|entry
return|;
block|}
block|}
block|}
DECL|method|obtainAndModifyCookie ()
specifier|private
name|void
name|obtainAndModifyCookie
parameter_list|()
throws|throws
name|FetcherException
block|{
try|try
block|{
name|URLDownload
name|downloader
init|=
operator|new
name|URLDownload
argument_list|(
literal|"https://scholar.google.com"
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|HttpCookie
argument_list|>
name|cookies
init|=
name|downloader
operator|.
name|getCookieFromUrl
argument_list|()
decl_stmt|;
for|for
control|(
name|HttpCookie
name|cookie
range|:
name|cookies
control|)
block|{
comment|// append "CF=4" which represents "Citation format bibtex"
name|cookie
operator|.
name|setValue
argument_list|(
name|cookie
operator|.
name|getValue
argument_list|()
operator|+
literal|":CF=4"
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|FetcherException
argument_list|(
literal|"Cookie configuration for Google Scholar failed."
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit

