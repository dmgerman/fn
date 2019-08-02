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
name|Optional
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
name|Month
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
name|StandardEntryType
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
name|Field
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
comment|/**  * Fetches data from the Springer  *  * @implNote see<a href="https://dev.springernature.com/">API documentation</a> for more details  */
end_comment

begin_class
DECL|class|SpringerFetcher
specifier|public
class|class
name|SpringerFetcher
implements|implements
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
name|SpringerFetcher
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|API_URL
specifier|private
specifier|static
specifier|final
name|String
name|API_URL
init|=
literal|"http://api.springernature.com/meta/v1/json?q="
decl_stmt|;
DECL|field|API_KEY
specifier|private
specifier|static
specifier|final
name|String
name|API_KEY
init|=
literal|"b0c7151179b3d9c1119cf325bca8460d"
decl_stmt|;
comment|/**      * Convert a JSONObject obtained from http://api.springer.com/metadata/json to a BibEntry      *      * @param springerJsonEntry the JSONObject from search results      * @return the converted BibEntry      */
DECL|method|parseSpringerJSONtoBibtex (JSONObject springerJsonEntry)
specifier|public
specifier|static
name|BibEntry
name|parseSpringerJSONtoBibtex
parameter_list|(
name|JSONObject
name|springerJsonEntry
parameter_list|)
block|{
comment|// Fields that are directly accessible at the top level Json object
name|Field
index|[]
name|singleFieldStrings
init|=
block|{
name|StandardField
operator|.
name|ISSN
block|,
name|StandardField
operator|.
name|VOLUME
block|,
name|StandardField
operator|.
name|ABSTRACT
block|,
name|StandardField
operator|.
name|DOI
block|,
name|StandardField
operator|.
name|TITLE
block|,
name|StandardField
operator|.
name|NUMBER
block|,
name|StandardField
operator|.
name|PUBLISHER
block|}
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|Field
name|nametype
decl_stmt|;
comment|// Guess publication type
name|String
name|isbn
init|=
name|springerJsonEntry
operator|.
name|optString
argument_list|(
literal|"isbn"
argument_list|)
decl_stmt|;
if|if
condition|(
name|com
operator|.
name|google
operator|.
name|common
operator|.
name|base
operator|.
name|Strings
operator|.
name|isNullOrEmpty
argument_list|(
name|isbn
argument_list|)
condition|)
block|{
comment|// Probably article
name|entry
operator|.
name|setType
argument_list|(
name|StandardEntryType
operator|.
name|Article
argument_list|)
expr_stmt|;
name|nametype
operator|=
name|StandardField
operator|.
name|JOURNAL
expr_stmt|;
block|}
else|else
block|{
comment|// Probably book chapter or from proceeding, go for book chapter
name|entry
operator|.
name|setType
argument_list|(
name|StandardEntryType
operator|.
name|InCollection
argument_list|)
expr_stmt|;
name|nametype
operator|=
name|StandardField
operator|.
name|BOOKTITLE
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|ISBN
argument_list|,
name|isbn
argument_list|)
expr_stmt|;
block|}
comment|// Authors
if|if
condition|(
name|springerJsonEntry
operator|.
name|has
argument_list|(
literal|"creators"
argument_list|)
condition|)
block|{
name|JSONArray
name|authors
init|=
name|springerJsonEntry
operator|.
name|getJSONArray
argument_list|(
literal|"creators"
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|authorList
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
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
name|authors
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|authors
operator|.
name|getJSONObject
argument_list|(
name|i
argument_list|)
operator|.
name|has
argument_list|(
literal|"creator"
argument_list|)
condition|)
block|{
name|authorList
operator|.
name|add
argument_list|(
name|authors
operator|.
name|getJSONObject
argument_list|(
name|i
argument_list|)
operator|.
name|getString
argument_list|(
literal|"creator"
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Empty author name."
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
name|AUTHOR
argument_list|,
name|String
operator|.
name|join
argument_list|(
literal|" and "
argument_list|,
name|authorList
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"No author found."
argument_list|)
expr_stmt|;
block|}
comment|// Direct accessible fields
for|for
control|(
name|Field
name|field
range|:
name|singleFieldStrings
control|)
block|{
if|if
condition|(
name|springerJsonEntry
operator|.
name|has
argument_list|(
name|field
operator|.
name|getName
argument_list|()
argument_list|)
condition|)
block|{
name|String
name|text
init|=
name|springerJsonEntry
operator|.
name|getString
argument_list|(
name|field
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|text
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
name|field
argument_list|,
name|text
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|// Page numbers
if|if
condition|(
name|springerJsonEntry
operator|.
name|has
argument_list|(
literal|"startingPage"
argument_list|)
operator|&&
operator|!
operator|(
name|springerJsonEntry
operator|.
name|getString
argument_list|(
literal|"startingPage"
argument_list|)
operator|.
name|isEmpty
argument_list|()
operator|)
condition|)
block|{
if|if
condition|(
name|springerJsonEntry
operator|.
name|has
argument_list|(
literal|"endingPage"
argument_list|)
operator|&&
operator|!
operator|(
name|springerJsonEntry
operator|.
name|getString
argument_list|(
literal|"endingPage"
argument_list|)
operator|.
name|isEmpty
argument_list|()
operator|)
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|PAGES
argument_list|,
name|springerJsonEntry
operator|.
name|getString
argument_list|(
literal|"startingPage"
argument_list|)
operator|+
literal|"--"
operator|+
name|springerJsonEntry
operator|.
name|getString
argument_list|(
literal|"endingPage"
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|PAGES
argument_list|,
name|springerJsonEntry
operator|.
name|getString
argument_list|(
literal|"startingPage"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Journal
if|if
condition|(
name|springerJsonEntry
operator|.
name|has
argument_list|(
literal|"publicationName"
argument_list|)
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
name|nametype
argument_list|,
name|springerJsonEntry
operator|.
name|getString
argument_list|(
literal|"publicationName"
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Online file
if|if
condition|(
name|springerJsonEntry
operator|.
name|has
argument_list|(
literal|"url"
argument_list|)
condition|)
block|{
name|JSONArray
name|urls
init|=
name|springerJsonEntry
operator|.
name|optJSONArray
argument_list|(
literal|"url"
argument_list|)
decl_stmt|;
if|if
condition|(
name|urls
operator|==
literal|null
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|URL
argument_list|,
name|springerJsonEntry
operator|.
name|optString
argument_list|(
literal|"url"
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|urls
operator|.
name|forEach
argument_list|(
name|data
lambda|->
block|{
name|JSONObject
name|url
init|=
operator|(
name|JSONObject
operator|)
name|data
decl_stmt|;
if|if
condition|(
name|url
operator|.
name|optString
argument_list|(
literal|"format"
argument_list|)
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"pdf"
argument_list|)
condition|)
block|{
name|entry
operator|.
name|addFile
argument_list|(
operator|new
name|LinkedFile
argument_list|(
literal|"online"
argument_list|,
name|url
operator|.
name|optString
argument_list|(
literal|"value"
argument_list|)
argument_list|,
literal|"PDF"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Date
if|if
condition|(
name|springerJsonEntry
operator|.
name|has
argument_list|(
literal|"publicationDate"
argument_list|)
condition|)
block|{
name|String
name|date
init|=
name|springerJsonEntry
operator|.
name|getString
argument_list|(
literal|"publicationDate"
argument_list|)
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|DATE
argument_list|,
name|date
argument_list|)
expr_stmt|;
comment|// For biblatex
name|String
index|[]
name|dateparts
init|=
name|date
operator|.
name|split
argument_list|(
literal|"-"
argument_list|)
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|YEAR
argument_list|,
name|dateparts
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|Optional
argument_list|<
name|Month
argument_list|>
name|month
init|=
name|Month
operator|.
name|getMonthByNumber
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|dateparts
index|[
literal|1
index|]
argument_list|)
argument_list|)
decl_stmt|;
name|month
operator|.
name|ifPresent
argument_list|(
name|entry
operator|::
name|setMonth
argument_list|)
expr_stmt|;
block|}
comment|// Clean up abstract (often starting with Abstract)
name|entry
operator|.
name|getField
argument_list|(
name|StandardField
operator|.
name|ABSTRACT
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|abstractContents
lambda|->
block|{
if|if
condition|(
name|abstractContents
operator|.
name|startsWith
argument_list|(
literal|"Abstract"
argument_list|)
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|ABSTRACT
argument_list|,
name|abstractContents
operator|.
name|substring
argument_list|(
literal|8
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
return|return
name|entry
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
literal|"Springer"
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
name|FETCHER_SPRINGER
argument_list|)
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
name|API_URL
argument_list|)
decl_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"q"
argument_list|,
name|query
argument_list|)
expr_stmt|;
comment|// Search query
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"api_key"
argument_list|,
name|API_KEY
argument_list|)
expr_stmt|;
comment|// API key
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"p"
argument_list|,
literal|"20"
argument_list|)
expr_stmt|;
comment|// Number of results to return
comment|//uriBuilder.addParameter("s", "1"); // Start item (not needed at the moment)
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
literal|"records"
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
literal|"records"
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
name|parseSpringerJSONtoBibtex
argument_list|(
name|jsonEntry
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
block|}
end_class

end_unit

