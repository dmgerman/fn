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
name|jabref
operator|.
name|model
operator|.
name|strings
operator|.
name|StringUtil
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
comment|/**  * Fetches data from the Directory of Open Access Journals (DOAJ)  *  * @implNote<a href="https://doaj.org/api/v1/docs">API documentation</a>  */
end_comment

begin_class
DECL|class|DOAJFetcher
specifier|public
class|class
name|DOAJFetcher
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
name|DOAJFetcher
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|SEARCH_URL
specifier|private
specifier|static
specifier|final
name|String
name|SEARCH_URL
init|=
literal|"https://doaj.org/api/v1/search/articles/"
decl_stmt|;
DECL|field|preferences
specifier|private
specifier|final
name|ImportFormatPreferences
name|preferences
decl_stmt|;
DECL|method|DOAJFetcher (ImportFormatPreferences preferences)
specifier|public
name|DOAJFetcher
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
comment|/**      * Convert a JSONObject containing a bibJSON entry to a BibEntry      *      * @param bibJsonEntry The JSONObject to convert      * @return the converted BibEntry      */
DECL|method|parseBibJSONtoBibtex (JSONObject bibJsonEntry, Character keywordSeparator)
specifier|public
specifier|static
name|BibEntry
name|parseBibJSONtoBibtex
parameter_list|(
name|JSONObject
name|bibJsonEntry
parameter_list|,
name|Character
name|keywordSeparator
parameter_list|)
block|{
comment|// Fields that are directly accessible at the top level BibJson object
name|Field
index|[]
name|singleFields
init|=
block|{
name|StandardField
operator|.
name|YEAR
block|,
name|StandardField
operator|.
name|TITLE
block|,
name|StandardField
operator|.
name|ABSTRACT
block|,
name|StandardField
operator|.
name|MONTH
block|}
decl_stmt|;
comment|// Fields that are accessible in the journal part of the BibJson object
name|Field
index|[]
name|journalSingleFields
init|=
block|{
name|StandardField
operator|.
name|PUBLISHER
block|,
name|StandardField
operator|.
name|NUMBER
block|,
name|StandardField
operator|.
name|VOLUME
block|}
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|(
name|StandardEntryType
operator|.
name|Article
argument_list|)
decl_stmt|;
comment|// Authors
if|if
condition|(
name|bibJsonEntry
operator|.
name|has
argument_list|(
literal|"author"
argument_list|)
condition|)
block|{
name|JSONArray
name|authors
init|=
name|bibJsonEntry
operator|.
name|getJSONArray
argument_list|(
literal|"author"
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
literal|"name"
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
literal|"name"
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
name|singleFields
control|)
block|{
if|if
condition|(
name|bibJsonEntry
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
name|entry
operator|.
name|setField
argument_list|(
name|field
argument_list|,
name|bibJsonEntry
operator|.
name|getString
argument_list|(
name|field
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Page numbers
if|if
condition|(
name|bibJsonEntry
operator|.
name|has
argument_list|(
literal|"start_page"
argument_list|)
condition|)
block|{
if|if
condition|(
name|bibJsonEntry
operator|.
name|has
argument_list|(
literal|"end_page"
argument_list|)
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
name|bibJsonEntry
operator|.
name|getString
argument_list|(
literal|"start_page"
argument_list|)
operator|+
literal|"--"
operator|+
name|bibJsonEntry
operator|.
name|getString
argument_list|(
literal|"end_page"
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
name|bibJsonEntry
operator|.
name|getString
argument_list|(
literal|"start_page"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Journal
if|if
condition|(
name|bibJsonEntry
operator|.
name|has
argument_list|(
literal|"journal"
argument_list|)
condition|)
block|{
name|JSONObject
name|journal
init|=
name|bibJsonEntry
operator|.
name|getJSONObject
argument_list|(
literal|"journal"
argument_list|)
decl_stmt|;
comment|// Journal title
if|if
condition|(
name|journal
operator|.
name|has
argument_list|(
literal|"title"
argument_list|)
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|JOURNAL
argument_list|,
name|journal
operator|.
name|getString
argument_list|(
literal|"title"
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"No journal title found."
argument_list|)
expr_stmt|;
block|}
comment|// Other journal related fields
for|for
control|(
name|Field
name|field
range|:
name|journalSingleFields
control|)
block|{
if|if
condition|(
name|journal
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
name|entry
operator|.
name|setField
argument_list|(
name|field
argument_list|,
name|journal
operator|.
name|getString
argument_list|(
name|field
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"No journal information found."
argument_list|)
expr_stmt|;
block|}
comment|// Keywords
if|if
condition|(
name|bibJsonEntry
operator|.
name|has
argument_list|(
literal|"keywords"
argument_list|)
condition|)
block|{
name|JSONArray
name|keywords
init|=
name|bibJsonEntry
operator|.
name|getJSONArray
argument_list|(
literal|"keywords"
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
name|keywords
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
operator|!
name|keywords
operator|.
name|isNull
argument_list|(
name|i
argument_list|)
condition|)
block|{
name|entry
operator|.
name|addKeyword
argument_list|(
name|keywords
operator|.
name|getString
argument_list|(
name|i
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|,
name|keywordSeparator
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|// Identifiers
if|if
condition|(
name|bibJsonEntry
operator|.
name|has
argument_list|(
literal|"identifier"
argument_list|)
condition|)
block|{
name|JSONArray
name|identifiers
init|=
name|bibJsonEntry
operator|.
name|getJSONArray
argument_list|(
literal|"identifier"
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
name|identifiers
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|String
name|type
init|=
name|identifiers
operator|.
name|getJSONObject
argument_list|(
name|i
argument_list|)
operator|.
name|getString
argument_list|(
literal|"type"
argument_list|)
decl_stmt|;
if|if
condition|(
literal|"doi"
operator|.
name|equals
argument_list|(
name|type
argument_list|)
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|DOI
argument_list|,
name|identifiers
operator|.
name|getJSONObject
argument_list|(
name|i
argument_list|)
operator|.
name|getString
argument_list|(
literal|"id"
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"pissn"
operator|.
name|equals
argument_list|(
name|type
argument_list|)
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|ISSN
argument_list|,
name|identifiers
operator|.
name|getJSONObject
argument_list|(
name|i
argument_list|)
operator|.
name|getString
argument_list|(
literal|"id"
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"eissn"
operator|.
name|equals
argument_list|(
name|type
argument_list|)
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|ISSN
argument_list|,
name|identifiers
operator|.
name|getJSONObject
argument_list|(
name|i
argument_list|)
operator|.
name|getString
argument_list|(
literal|"id"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|// Links
if|if
condition|(
name|bibJsonEntry
operator|.
name|has
argument_list|(
literal|"link"
argument_list|)
condition|)
block|{
name|JSONArray
name|links
init|=
name|bibJsonEntry
operator|.
name|getJSONArray
argument_list|(
literal|"link"
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
name|links
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
name|links
operator|.
name|getJSONObject
argument_list|(
name|i
argument_list|)
operator|.
name|has
argument_list|(
literal|"type"
argument_list|)
condition|)
block|{
name|String
name|type
init|=
name|links
operator|.
name|getJSONObject
argument_list|(
name|i
argument_list|)
operator|.
name|getString
argument_list|(
literal|"type"
argument_list|)
decl_stmt|;
if|if
condition|(
literal|"fulltext"
operator|.
name|equals
argument_list|(
name|type
argument_list|)
operator|&&
name|links
operator|.
name|getJSONObject
argument_list|(
name|i
argument_list|)
operator|.
name|has
argument_list|(
literal|"url"
argument_list|)
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
name|links
operator|.
name|getJSONObject
argument_list|(
name|i
argument_list|)
operator|.
name|getString
argument_list|(
literal|"url"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
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
literal|"DOAJ"
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
name|FETCHER_DOAJ
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
name|SEARCH_URL
argument_list|)
decl_stmt|;
name|DOAJFetcher
operator|.
name|addPath
argument_list|(
name|uriBuilder
argument_list|,
name|query
argument_list|)
expr_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"pageSize"
argument_list|,
literal|"30"
argument_list|)
expr_stmt|;
comment|// Number of results
comment|//uriBuilder.addParameter("page", "1"); // Page (not needed so far)
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
comment|/**      * @implNote slightly altered version based on https://gist.github.com/enginer/230e2dc2f1d213a825d5      */
DECL|method|addPath (URIBuilder base, String subPath)
specifier|public
specifier|static
name|URIBuilder
name|addPath
parameter_list|(
name|URIBuilder
name|base
parameter_list|,
name|String
name|subPath
parameter_list|)
block|{
if|if
condition|(
name|StringUtil
operator|.
name|isBlank
argument_list|(
name|subPath
argument_list|)
operator|||
literal|"/"
operator|.
name|equals
argument_list|(
name|subPath
argument_list|)
condition|)
block|{
return|return
name|base
return|;
block|}
else|else
block|{
name|base
operator|.
name|setPath
argument_list|(
name|appendSegmentToPath
argument_list|(
name|base
operator|.
name|getPath
argument_list|()
argument_list|,
name|subPath
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|base
return|;
block|}
block|}
DECL|method|appendSegmentToPath (String path, String segment)
specifier|private
specifier|static
name|String
name|appendSegmentToPath
parameter_list|(
name|String
name|path
parameter_list|,
name|String
name|segment
parameter_list|)
block|{
if|if
condition|(
name|StringUtil
operator|.
name|isBlank
argument_list|(
name|path
argument_list|)
condition|)
block|{
name|path
operator|=
literal|"/"
expr_stmt|;
block|}
if|if
condition|(
name|path
operator|.
name|charAt
argument_list|(
name|path
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
operator|==
literal|'/'
operator|||
name|segment
operator|.
name|startsWith
argument_list|(
literal|"/"
argument_list|)
condition|)
block|{
return|return
name|path
operator|+
name|segment
return|;
block|}
return|return
name|path
operator|+
literal|"/"
operator|+
name|segment
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
literal|"results"
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
literal|"results"
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
name|bibJsonEntry
init|=
name|results
operator|.
name|getJSONObject
argument_list|(
name|i
argument_list|)
operator|.
name|getJSONObject
argument_list|(
literal|"bibjson"
argument_list|)
decl_stmt|;
name|BibEntry
name|entry
init|=
name|parseBibJSONtoBibtex
argument_list|(
name|bibJsonEntry
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
block|}
end_class

end_unit

