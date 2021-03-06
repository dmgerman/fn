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
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
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
name|Parser
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
name|SearchBasedParserFetcher
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
name|logic
operator|.
name|util
operator|.
name|OS
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
name|LinkedFile
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
name|entry
operator|.
name|types
operator|.
name|StandardEntryType
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
name|json
operator|.
name|JSONArray
import|;
end_import

begin_import
import|import
name|org
operator|.
name|json
operator|.
name|JSONObject
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
comment|/**  * Class for finding PDF URLs for entries on IEEE  * Will first look for URLs of the type https://ieeexplore.ieee.org/stamp/stamp.jsp?[tp=&]arnumber=...  * If not found, will resolve the DOI, if it starts with 10.1109, and try to find a similar link on the HTML page  *  * @implNote<a href="https://developer.ieee.org/docs">API documentation</a>  */
end_comment

begin_class
DECL|class|IEEE
specifier|public
class|class
name|IEEE
implements|implements
name|FulltextFetcher
implements|,
name|SearchBasedParserFetcher
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
name|IEEE
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|STAMP_BASE_STRING_DOCUMENT
specifier|private
specifier|static
specifier|final
name|String
name|STAMP_BASE_STRING_DOCUMENT
init|=
literal|"/stamp/stamp.jsp?tp=&arnumber="
decl_stmt|;
DECL|field|STAMP_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|STAMP_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"(/stamp/stamp.jsp\\?t?p?=?&?arnumber=[0-9]+)"
argument_list|)
decl_stmt|;
DECL|field|DOCUMENT_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|DOCUMENT_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"document/([0-9]+)/"
argument_list|)
decl_stmt|;
DECL|field|PDF_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|PDF_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\"(https://ieeexplore.ieee.org/ielx[0-9/]+\\.pdf[^\"]+)\""
argument_list|)
decl_stmt|;
DECL|field|IEEE_DOI
specifier|private
specifier|static
specifier|final
name|String
name|IEEE_DOI
init|=
literal|"10.1109"
decl_stmt|;
DECL|field|BASE_URL
specifier|private
specifier|static
specifier|final
name|String
name|BASE_URL
init|=
literal|"https://ieeexplore.ieee.org"
decl_stmt|;
DECL|field|preferences
specifier|private
specifier|final
name|ImportFormatPreferences
name|preferences
decl_stmt|;
DECL|method|IEEE (ImportFormatPreferences preferences)
specifier|public
name|IEEE
parameter_list|(
name|ImportFormatPreferences
name|preferences
parameter_list|)
block|{
name|this
operator|.
name|preferences
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|preferences
argument_list|)
expr_stmt|;
block|}
comment|/**      * @implNote<a href="https://developer.ieee.org/docs/read/Metadata_API_responses">documentation</a>      */
DECL|method|parseJsonRespone (JSONObject jsonEntry, Character keywordSeparator)
specifier|private
specifier|static
name|BibEntry
name|parseJsonRespone
parameter_list|(
name|JSONObject
name|jsonEntry
parameter_list|,
name|Character
name|keywordSeparator
parameter_list|)
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
switch|switch
condition|(
name|jsonEntry
operator|.
name|optString
argument_list|(
literal|"content_type"
argument_list|)
condition|)
block|{
case|case
literal|"Books"
case|:
name|entry
operator|.
name|setType
argument_list|(
name|StandardEntryType
operator|.
name|Book
argument_list|)
expr_stmt|;
break|break;
case|case
literal|"Conferences"
case|:
name|entry
operator|.
name|setType
argument_list|(
name|StandardEntryType
operator|.
name|InProceedings
argument_list|)
expr_stmt|;
break|break;
case|case
literal|"Courses"
case|:
name|entry
operator|.
name|setType
argument_list|(
name|StandardEntryType
operator|.
name|Misc
argument_list|)
expr_stmt|;
break|break;
default|default:
name|entry
operator|.
name|setType
argument_list|(
name|StandardEntryType
operator|.
name|Article
argument_list|)
expr_stmt|;
break|break;
block|}
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|ABSTRACT
argument_list|,
name|jsonEntry
operator|.
name|optString
argument_list|(
literal|"abstract"
argument_list|)
argument_list|)
expr_stmt|;
comment|//entry.setField(StandardField.IEEE_ID, jsonEntry.optString("article_number"));
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|authors
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|JSONObject
name|authorsContainer
init|=
name|jsonEntry
operator|.
name|optJSONObject
argument_list|(
literal|"authors"
argument_list|)
decl_stmt|;
name|authorsContainer
operator|.
name|getJSONArray
argument_list|(
literal|"authors"
argument_list|)
operator|.
name|forEach
argument_list|(
name|authorPure
lambda|->
block|{
name|JSONObject
name|author
init|=
operator|(
name|JSONObject
operator|)
name|authorPure
decl_stmt|;
name|authors
operator|.
name|add
argument_list|(
name|author
operator|.
name|optString
argument_list|(
literal|"full_name"
argument_list|)
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|AUTHOR
argument_list|,
name|authors
operator|.
name|stream
argument_list|()
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|joining
argument_list|(
literal|" and "
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|LOCATION
argument_list|,
name|jsonEntry
operator|.
name|optString
argument_list|(
literal|"conference_location"
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|DOI
argument_list|,
name|jsonEntry
operator|.
name|optString
argument_list|(
literal|"doi"
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|YEAR
argument_list|,
name|jsonEntry
operator|.
name|optString
argument_list|(
literal|"publication_year"
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|PAGES
argument_list|,
name|jsonEntry
operator|.
name|optString
argument_list|(
literal|"start_page"
argument_list|)
operator|+
literal|"--"
operator|+
name|jsonEntry
operator|.
name|optString
argument_list|(
literal|"end_page"
argument_list|)
argument_list|)
expr_stmt|;
name|JSONObject
name|keywordsContainer
init|=
name|jsonEntry
operator|.
name|optJSONObject
argument_list|(
literal|"index_terms"
argument_list|)
decl_stmt|;
if|if
condition|(
name|keywordsContainer
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|keywordsContainer
operator|.
name|has
argument_list|(
literal|"ieee_terms"
argument_list|)
condition|)
block|{
name|keywordsContainer
operator|.
name|getJSONObject
argument_list|(
literal|"ieee_terms"
argument_list|)
operator|.
name|getJSONArray
argument_list|(
literal|"terms"
argument_list|)
operator|.
name|forEach
argument_list|(
name|data
lambda|->
block|{
name|String
name|keyword
init|=
operator|(
name|String
operator|)
name|data
decl_stmt|;
name|entry
operator|.
name|addKeyword
argument_list|(
name|keyword
argument_list|,
name|keywordSeparator
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|keywordsContainer
operator|.
name|has
argument_list|(
literal|"author_terms"
argument_list|)
condition|)
block|{
name|keywordsContainer
operator|.
name|getJSONObject
argument_list|(
literal|"author_terms"
argument_list|)
operator|.
name|getJSONArray
argument_list|(
literal|"terms"
argument_list|)
operator|.
name|forEach
argument_list|(
name|data
lambda|->
block|{
name|String
name|keyword
init|=
operator|(
name|String
operator|)
name|data
decl_stmt|;
name|entry
operator|.
name|addKeyword
argument_list|(
name|keyword
argument_list|,
name|keywordSeparator
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
block|}
block|}
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|ISBN
argument_list|,
name|jsonEntry
operator|.
name|optString
argument_list|(
literal|"isbn"
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|ISSN
argument_list|,
name|jsonEntry
operator|.
name|optString
argument_list|(
literal|"issn"
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|ISSUE
argument_list|,
name|jsonEntry
operator|.
name|optString
argument_list|(
literal|"issue"
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|.
name|addFile
argument_list|(
operator|new
name|LinkedFile
argument_list|(
literal|""
argument_list|,
name|jsonEntry
operator|.
name|optString
argument_list|(
literal|"pdf_url"
argument_list|)
argument_list|,
literal|"PDF"
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|JOURNALTITLE
argument_list|,
name|jsonEntry
operator|.
name|optString
argument_list|(
literal|"publication_title"
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|DATE
argument_list|,
name|jsonEntry
operator|.
name|optString
argument_list|(
literal|"publication_date"
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|EVENTTITLEADDON
argument_list|,
name|jsonEntry
operator|.
name|optString
argument_list|(
literal|"conference_location"
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|EVENTDATE
argument_list|,
name|jsonEntry
operator|.
name|optString
argument_list|(
literal|"conference_dates"
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|PUBLISHER
argument_list|,
name|jsonEntry
operator|.
name|optString
argument_list|(
literal|"publisher"
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|,
name|jsonEntry
operator|.
name|optString
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|VOLUME
argument_list|,
name|jsonEntry
operator|.
name|optString
argument_list|(
literal|"volume"
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|entry
return|;
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
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|String
name|stampString
init|=
literal|""
decl_stmt|;
comment|// Try URL first -- will primarily work for entries from the old IEEE search
name|Optional
argument_list|<
name|String
argument_list|>
name|urlString
init|=
name|entry
operator|.
name|getField
argument_list|(
name|StandardField
operator|.
name|URL
argument_list|)
decl_stmt|;
if|if
condition|(
name|urlString
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|Matcher
name|documentUrlMatcher
init|=
name|DOCUMENT_PATTERN
operator|.
name|matcher
argument_list|(
name|urlString
operator|.
name|get
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|documentUrlMatcher
operator|.
name|find
argument_list|()
condition|)
block|{
name|String
name|docId
init|=
name|documentUrlMatcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
decl_stmt|;
name|stampString
operator|=
name|STAMP_BASE_STRING_DOCUMENT
operator|+
name|docId
expr_stmt|;
block|}
comment|//You get this url if you export bibtex from IEEE
name|Matcher
name|stampMatcher
init|=
name|STAMP_PATTERN
operator|.
name|matcher
argument_list|(
name|urlString
operator|.
name|get
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|stampMatcher
operator|.
name|find
argument_list|()
condition|)
block|{
comment|// Found it
name|stampString
operator|=
name|stampMatcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
block|}
comment|// If not, try DOI
if|if
condition|(
name|stampString
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Optional
argument_list|<
name|DOI
argument_list|>
name|doi
init|=
name|entry
operator|.
name|getField
argument_list|(
name|StandardField
operator|.
name|DOI
argument_list|)
operator|.
name|flatMap
argument_list|(
name|DOI
operator|::
name|parse
argument_list|)
decl_stmt|;
if|if
condition|(
name|doi
operator|.
name|isPresent
argument_list|()
operator|&&
name|doi
operator|.
name|get
argument_list|()
operator|.
name|getDOI
argument_list|()
operator|.
name|startsWith
argument_list|(
name|IEEE_DOI
argument_list|)
operator|&&
name|doi
operator|.
name|get
argument_list|()
operator|.
name|getExternalURI
argument_list|()
operator|.
name|isPresent
argument_list|()
condition|)
block|{
comment|// Download the HTML page from IEEE
name|URLDownload
name|urlDownload
init|=
operator|new
name|URLDownload
argument_list|(
name|doi
operator|.
name|get
argument_list|()
operator|.
name|getExternalURI
argument_list|()
operator|.
name|get
argument_list|()
operator|.
name|toURL
argument_list|()
argument_list|)
decl_stmt|;
comment|//We don't need to modify the cookies, but we need support for them
name|urlDownload
operator|.
name|getCookieFromUrl
argument_list|()
expr_stmt|;
name|String
name|resolvedDOIPage
init|=
name|urlDownload
operator|.
name|asString
argument_list|()
decl_stmt|;
comment|// Try to find the link
name|Matcher
name|matcher
init|=
name|STAMP_PATTERN
operator|.
name|matcher
argument_list|(
name|resolvedDOIPage
argument_list|)
decl_stmt|;
if|if
condition|(
name|matcher
operator|.
name|find
argument_list|()
condition|)
block|{
comment|// Found it
name|stampString
operator|=
name|matcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|// Any success?
if|if
condition|(
name|stampString
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
comment|// Download the HTML page containing a frame with the PDF
name|URLDownload
name|urlDownload
init|=
operator|new
name|URLDownload
argument_list|(
name|BASE_URL
operator|+
name|stampString
argument_list|)
decl_stmt|;
comment|//We don't need to modify the cookies, but we need support for them
name|urlDownload
operator|.
name|getCookieFromUrl
argument_list|()
expr_stmt|;
name|String
name|framePage
init|=
name|urlDownload
operator|.
name|asString
argument_list|()
decl_stmt|;
comment|// Try to find the direct PDF link
name|Matcher
name|matcher
init|=
name|PDF_PATTERN
operator|.
name|matcher
argument_list|(
name|framePage
argument_list|)
decl_stmt|;
if|if
condition|(
name|matcher
operator|.
name|find
argument_list|()
condition|)
block|{
comment|// The PDF was found
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Full text document found on IEEE Xplore"
argument_list|)
expr_stmt|;
return|return
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|URL
argument_list|(
name|matcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
argument_list|)
return|;
block|}
return|return
name|Optional
operator|.
name|empty
argument_list|()
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
name|PUBLISHER
return|;
block|}
annotation|@
name|Override
DECL|method|getURLForQuery (String query)
specifier|public
name|URL
name|getURLForQuery
parameter_list|(
name|String
name|query
parameter_list|)
throws|throws
name|URISyntaxException
throws|,
name|MalformedURLException
throws|,
name|FetcherException
block|{
name|URIBuilder
name|uriBuilder
init|=
operator|new
name|URIBuilder
argument_list|(
literal|"https://ieeexploreapi.ieee.org/api/v1/search/articles"
argument_list|)
decl_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"apikey"
argument_list|,
literal|"86wnawtvtc986d3wtnqynm8c"
argument_list|)
expr_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"querytext"
argument_list|,
name|query
argument_list|)
expr_stmt|;
name|URLDownload
operator|.
name|bypassSSLVerification
argument_list|()
expr_stmt|;
return|return
name|uriBuilder
operator|.
name|build
argument_list|()
operator|.
name|toURL
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|getParser ()
specifier|public
name|Parser
name|getParser
parameter_list|()
block|{
return|return
name|inputStream
lambda|->
block|{
name|String
name|response
init|=
operator|new
name|BufferedReader
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
name|inputStream
argument_list|)
argument_list|)
operator|.
name|lines
argument_list|()
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|joining
argument_list|(
name|OS
operator|.
name|NEWLINE
argument_list|)
argument_list|)
decl_stmt|;
name|JSONObject
name|jsonObject
init|=
operator|new
name|JSONObject
argument_list|(
name|response
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
if|if
condition|(
name|jsonObject
operator|.
name|has
argument_list|(
literal|"articles"
argument_list|)
condition|)
block|{
name|JSONArray
name|results
init|=
name|jsonObject
operator|.
name|getJSONArray
argument_list|(
literal|"articles"
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
name|results
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|JSONObject
name|jsonEntry
init|=
name|results
operator|.
name|getJSONObject
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|BibEntry
name|entry
init|=
name|parseJsonRespone
argument_list|(
name|jsonEntry
argument_list|,
name|preferences
operator|.
name|getKeywordSeparator
argument_list|()
argument_list|)
decl_stmt|;
name|entries
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|entries
return|;
block|}
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
literal|"IEEEXplore"
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
name|FETCHER_IEEEXPLORE
argument_list|)
return|;
block|}
block|}
end_class

end_unit

