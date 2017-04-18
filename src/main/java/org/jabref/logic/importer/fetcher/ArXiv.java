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
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|DocumentBuilder
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|DocumentBuilderFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|ParserConfigurationException
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
name|IdBasedFetcher
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
name|IdFetcher
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
name|util
operator|.
name|OAI2Handler
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
name|io
operator|.
name|XMLUtil
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
name|strings
operator|.
name|StringSimilarity
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
name|BibtexEntryTypes
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
name|FieldName
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
name|identifier
operator|.
name|ArXivIdentifier
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
name|strings
operator|.
name|StringUtil
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
name|w3c
operator|.
name|dom
operator|.
name|Document
import|;
end_import

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Node
import|;
end_import

begin_import
import|import
name|org
operator|.
name|xml
operator|.
name|sax
operator|.
name|SAXException
import|;
end_import

begin_comment
comment|/**  * Fetcher for the arXiv.  *  * @see<a href="http://arxiv.org/help/api/index">ArXiv API</a> for an overview of the API  * @see<a href="http://arxiv.org/help/api/user-manual#_calling_the_api">ArXiv API User's Manual</a> for a detailed  * description on how to use the API  *  * Similar implementions:  *<a href="https://github.com/nathangrigg/arxiv2bib">arxiv2bib</a> which is<a href="https://arxiv2bibtex.org/">live</a>  *<a herf="https://gitlab.c3sl.ufpr.br/portalmec/dspace-portalmec/blob/aa209d15082a9870f9daac42c78a35490ce77b52/dspace-api/src/main/java/org/dspace/submit/lookup/ArXivService.java">dspace-portalmec</a>  */
end_comment

begin_class
DECL|class|ArXiv
specifier|public
class|class
name|ArXiv
implements|implements
name|FulltextFetcher
implements|,
name|SearchBasedFetcher
implements|,
name|IdBasedFetcher
implements|,
name|IdFetcher
argument_list|<
name|ArXivIdentifier
argument_list|>
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
name|ArXiv
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
literal|"http://export.arxiv.org/api/query"
decl_stmt|;
DECL|field|importFormatPreferences
specifier|private
specifier|final
name|ImportFormatPreferences
name|importFormatPreferences
decl_stmt|;
DECL|method|ArXiv (ImportFormatPreferences importFormatPreferences)
specifier|public
name|ArXiv
parameter_list|(
name|ImportFormatPreferences
name|importFormatPreferences
parameter_list|)
block|{
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
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|entry
argument_list|)
expr_stmt|;
try|try
block|{
name|Optional
argument_list|<
name|URL
argument_list|>
name|pdfUrl
init|=
name|searchForEntries
argument_list|(
name|entry
argument_list|)
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|ArXivEntry
operator|::
name|getPdfUrl
argument_list|)
operator|.
name|filter
argument_list|(
name|Optional
operator|::
name|isPresent
argument_list|)
operator|.
name|map
argument_list|(
name|Optional
operator|::
name|get
argument_list|)
operator|.
name|findFirst
argument_list|()
decl_stmt|;
if|if
condition|(
name|pdfUrl
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Fulltext PDF found @ arXiv."
argument_list|)
expr_stmt|;
block|}
return|return
name|pdfUrl
return|;
block|}
catch|catch
parameter_list|(
name|FetcherException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"arXiv API request failed"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
DECL|method|searchForEntry (String searchQuery)
specifier|private
name|Optional
argument_list|<
name|ArXivEntry
argument_list|>
name|searchForEntry
parameter_list|(
name|String
name|searchQuery
parameter_list|)
throws|throws
name|FetcherException
block|{
name|List
argument_list|<
name|ArXivEntry
argument_list|>
name|entries
init|=
name|queryApi
argument_list|(
name|searchQuery
argument_list|,
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
literal|0
argument_list|,
literal|1
argument_list|)
decl_stmt|;
if|if
condition|(
name|entries
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|entries
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
block|}
DECL|method|searchForEntryById (String id)
specifier|private
name|Optional
argument_list|<
name|ArXivEntry
argument_list|>
name|searchForEntryById
parameter_list|(
name|String
name|id
parameter_list|)
throws|throws
name|FetcherException
block|{
name|Optional
argument_list|<
name|ArXivIdentifier
argument_list|>
name|identifier
init|=
name|ArXivIdentifier
operator|.
name|parse
argument_list|(
name|id
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|identifier
operator|.
name|isPresent
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
name|List
argument_list|<
name|ArXivEntry
argument_list|>
name|entries
init|=
name|queryApi
argument_list|(
literal|""
argument_list|,
name|Collections
operator|.
name|singletonList
argument_list|(
name|identifier
operator|.
name|get
argument_list|()
argument_list|)
argument_list|,
literal|0
argument_list|,
literal|1
argument_list|)
decl_stmt|;
if|if
condition|(
name|entries
operator|.
name|size
argument_list|()
operator|>=
literal|1
condition|)
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|entries
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
block|}
DECL|method|searchForEntries (BibEntry entry)
specifier|private
name|List
argument_list|<
name|ArXivEntry
argument_list|>
name|searchForEntries
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
throws|throws
name|FetcherException
block|{
comment|// 1. Eprint
name|Optional
argument_list|<
name|String
argument_list|>
name|identifier
init|=
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|EPRINT
argument_list|)
decl_stmt|;
if|if
condition|(
name|StringUtil
operator|.
name|isNotBlank
argument_list|(
name|identifier
argument_list|)
condition|)
block|{
try|try
block|{
comment|// Get pdf of entry with the specified id
return|return
name|OptionalUtil
operator|.
name|toList
argument_list|(
name|searchForEntryById
argument_list|(
name|identifier
operator|.
name|get
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|FetcherException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"arXiv eprint API request failed"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
comment|// 2. DOI and other fields
name|String
name|query
decl_stmt|;
name|Optional
argument_list|<
name|String
argument_list|>
name|doi
init|=
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
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
operator|.
name|map
argument_list|(
name|DOI
operator|::
name|getNormalized
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
comment|// Search for an entry in the ArXiv which is linked to the doi
name|query
operator|=
literal|"doi:"
operator|+
name|doi
operator|.
name|get
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|Optional
argument_list|<
name|String
argument_list|>
name|authorQuery
init|=
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|AUTHOR
argument_list|)
operator|.
name|map
argument_list|(
name|author
lambda|->
literal|"au:"
operator|+
name|author
argument_list|)
decl_stmt|;
name|Optional
argument_list|<
name|String
argument_list|>
name|titleQuery
init|=
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|TITLE
argument_list|)
operator|.
name|map
argument_list|(
name|title
lambda|->
literal|"ti:"
operator|+
name|title
argument_list|)
decl_stmt|;
name|query
operator|=
name|OptionalUtil
operator|.
name|toList
argument_list|(
name|authorQuery
argument_list|,
name|titleQuery
argument_list|)
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
literal|"+AND+"
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|Optional
argument_list|<
name|ArXivEntry
argument_list|>
name|arxivEntry
init|=
name|searchForEntry
argument_list|(
name|query
argument_list|)
decl_stmt|;
if|if
condition|(
name|arxivEntry
operator|.
name|isPresent
argument_list|()
condition|)
block|{
comment|// Check if entry is a match
name|StringSimilarity
name|match
init|=
operator|new
name|StringSimilarity
argument_list|()
decl_stmt|;
name|String
name|arxivTitle
init|=
name|arxivEntry
operator|.
name|get
argument_list|()
operator|.
name|title
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
decl_stmt|;
name|String
name|entryTitle
init|=
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|TITLE
argument_list|)
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
decl_stmt|;
if|if
condition|(
name|match
operator|.
name|isSimilar
argument_list|(
name|arxivTitle
argument_list|,
name|entryTitle
argument_list|)
condition|)
block|{
return|return
name|OptionalUtil
operator|.
name|toList
argument_list|(
name|arxivEntry
argument_list|)
return|;
block|}
block|}
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
DECL|method|searchForEntries (String searchQuery)
specifier|private
name|List
argument_list|<
name|ArXivEntry
argument_list|>
name|searchForEntries
parameter_list|(
name|String
name|searchQuery
parameter_list|)
throws|throws
name|FetcherException
block|{
return|return
name|queryApi
argument_list|(
name|searchQuery
argument_list|,
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
literal|0
argument_list|,
literal|10
argument_list|)
return|;
block|}
DECL|method|queryApi (String searchQuery, List<ArXivIdentifier> ids, int start, int maxResults)
specifier|private
name|List
argument_list|<
name|ArXivEntry
argument_list|>
name|queryApi
parameter_list|(
name|String
name|searchQuery
parameter_list|,
name|List
argument_list|<
name|ArXivIdentifier
argument_list|>
name|ids
parameter_list|,
name|int
name|start
parameter_list|,
name|int
name|maxResults
parameter_list|)
throws|throws
name|FetcherException
block|{
name|Document
name|result
init|=
name|callApi
argument_list|(
name|searchQuery
argument_list|,
name|ids
argument_list|,
name|start
argument_list|,
name|maxResults
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|Node
argument_list|>
name|entries
init|=
name|XMLUtil
operator|.
name|asList
argument_list|(
name|result
operator|.
name|getElementsByTagName
argument_list|(
literal|"entry"
argument_list|)
argument_list|)
decl_stmt|;
return|return
name|entries
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|ArXivEntry
operator|::
operator|new
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
return|;
block|}
comment|/**      * Queries the API.      *      * If only {@code searchQuery} is given, then the API will return results for each article that matches the query.      * If only {@code ids} is given, then the API will return results for each article in the list.      * If both {@code searchQuery} and {@code ids} are given, then the API will return each article in      * {@code ids} that matches {@code searchQuery}. This allows the API to act as a results filter.      *      * @param searchQuery the search query used to find articles;      *<a href="http://arxiv.org/help/api/user-manual#query_details">details</a>      * @param ids         a list of arXiv identifiers      * @param start       the index of the first returned result (zero-based)      * @param maxResults  the number of maximal results (has to be smaller than 2000)      * @return the response from the API as a XML document (Atom 1.0)      * @throws FetcherException if there was a problem while building the URL or the API was not accessible      */
DECL|method|callApi (String searchQuery, List<ArXivIdentifier> ids, int start, int maxResults)
specifier|private
name|Document
name|callApi
parameter_list|(
name|String
name|searchQuery
parameter_list|,
name|List
argument_list|<
name|ArXivIdentifier
argument_list|>
name|ids
parameter_list|,
name|int
name|start
parameter_list|,
name|int
name|maxResults
parameter_list|)
throws|throws
name|FetcherException
block|{
if|if
condition|(
name|maxResults
operator|>
literal|2000
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"The arXiv API limits the number of maximal results to be 2000"
argument_list|)
throw|;
block|}
try|try
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
comment|// The arXiv API has problems with accents, so we remove them (i.e. FrÃ©chet -> Frechet)
if|if
condition|(
name|StringUtil
operator|.
name|isNotBlank
argument_list|(
name|searchQuery
argument_list|)
condition|)
block|{
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"search_query"
argument_list|,
name|StringUtil
operator|.
name|stripAccents
argument_list|(
name|searchQuery
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|ids
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"id_list"
argument_list|,
name|ids
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|ArXivIdentifier
operator|::
name|getNormalized
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|joining
argument_list|(
literal|","
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"start"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|start
argument_list|)
argument_list|)
expr_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"max_results"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|maxResults
argument_list|)
argument_list|)
expr_stmt|;
name|URL
name|url
init|=
name|uriBuilder
operator|.
name|build
argument_list|()
operator|.
name|toURL
argument_list|()
decl_stmt|;
name|DocumentBuilderFactory
name|factory
init|=
name|DocumentBuilderFactory
operator|.
name|newInstance
argument_list|()
decl_stmt|;
name|DocumentBuilder
name|builder
init|=
name|factory
operator|.
name|newDocumentBuilder
argument_list|()
decl_stmt|;
name|HttpURLConnection
name|connection
init|=
operator|(
name|HttpURLConnection
operator|)
name|url
operator|.
name|openConnection
argument_list|()
decl_stmt|;
if|if
condition|(
name|connection
operator|.
name|getResponseCode
argument_list|()
operator|==
literal|400
condition|)
block|{
comment|// Bad request error from server, try to get more information
throw|throw
name|getException
argument_list|(
name|builder
operator|.
name|parse
argument_list|(
name|connection
operator|.
name|getErrorStream
argument_list|()
argument_list|)
argument_list|)
throw|;
block|}
else|else
block|{
return|return
name|builder
operator|.
name|parse
argument_list|(
name|connection
operator|.
name|getInputStream
argument_list|()
argument_list|)
return|;
block|}
block|}
catch|catch
parameter_list|(
name|SAXException
decl||
name|ParserConfigurationException
decl||
name|IOException
decl||
name|URISyntaxException
name|exception
parameter_list|)
block|{
throw|throw
operator|new
name|FetcherException
argument_list|(
literal|"arXiv API request failed"
argument_list|,
name|exception
argument_list|)
throw|;
block|}
block|}
DECL|method|getException (Document error)
specifier|private
name|FetcherException
name|getException
parameter_list|(
name|Document
name|error
parameter_list|)
block|{
name|List
argument_list|<
name|Node
argument_list|>
name|entries
init|=
name|XMLUtil
operator|.
name|asList
argument_list|(
name|error
operator|.
name|getElementsByTagName
argument_list|(
literal|"entry"
argument_list|)
argument_list|)
decl_stmt|;
comment|// Check if the API returned an error
comment|// In case of an error, only one entry will be returned with the error information. For example:
comment|// http://export.arxiv.org/api/query?id_list=0307015
comment|//<entry>
comment|//<id>http://arxiv.org/api/errors#incorrect_id_format_for_0307015</id>
comment|//<title>Error</title>
comment|//<summary>incorrect id format for 0307015</summary>
comment|//</entry>
if|if
condition|(
name|entries
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
name|Node
name|node
init|=
name|entries
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|Optional
argument_list|<
name|String
argument_list|>
name|id
init|=
name|XMLUtil
operator|.
name|getNodeContent
argument_list|(
name|node
argument_list|,
literal|"id"
argument_list|)
decl_stmt|;
name|Boolean
name|isError
init|=
name|id
operator|.
name|map
argument_list|(
name|idContent
lambda|->
name|idContent
operator|.
name|startsWith
argument_list|(
literal|"http://arxiv.org/api/errors"
argument_list|)
argument_list|)
operator|.
name|orElse
argument_list|(
literal|false
argument_list|)
decl_stmt|;
if|if
condition|(
name|isError
condition|)
block|{
name|String
name|errorMessage
init|=
name|XMLUtil
operator|.
name|getNodeContent
argument_list|(
name|node
argument_list|,
literal|"summary"
argument_list|)
operator|.
name|orElse
argument_list|(
literal|"Unknown error"
argument_list|)
decl_stmt|;
return|return
operator|new
name|FetcherException
argument_list|(
name|errorMessage
argument_list|)
return|;
block|}
block|}
return|return
operator|new
name|FetcherException
argument_list|(
literal|"arXiv API request failed"
argument_list|)
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
literal|"ArXiv"
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
name|FETCHER_OAI2_ARXIV
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
return|return
name|searchForEntries
argument_list|(
name|query
argument_list|)
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
parameter_list|(
name|arXivEntry
parameter_list|)
lambda|->
name|arXivEntry
operator|.
name|toBibEntry
argument_list|(
name|importFormatPreferences
operator|.
name|getKeywordSeparator
argument_list|()
argument_list|)
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|performSearchById (String identifier)
specifier|public
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|performSearchById
parameter_list|(
name|String
name|identifier
parameter_list|)
throws|throws
name|FetcherException
block|{
return|return
name|searchForEntryById
argument_list|(
name|identifier
argument_list|)
operator|.
name|map
argument_list|(
parameter_list|(
name|arXivEntry
parameter_list|)
lambda|->
name|arXivEntry
operator|.
name|toBibEntry
argument_list|(
name|importFormatPreferences
operator|.
name|getKeywordSeparator
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|findIdentifier (BibEntry entry)
specifier|public
name|Optional
argument_list|<
name|ArXivIdentifier
argument_list|>
name|findIdentifier
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
throws|throws
name|FetcherException
block|{
return|return
name|searchForEntries
argument_list|(
name|entry
argument_list|)
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|ArXivEntry
operator|::
name|getId
argument_list|)
operator|.
name|filter
argument_list|(
name|Optional
operator|::
name|isPresent
argument_list|)
operator|.
name|map
argument_list|(
name|Optional
operator|::
name|get
argument_list|)
operator|.
name|findFirst
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|getIdentifierName ()
specifier|public
name|String
name|getIdentifierName
parameter_list|()
block|{
return|return
literal|"ArXiv"
return|;
block|}
DECL|class|ArXivEntry
specifier|private
specifier|static
class|class
name|ArXivEntry
block|{
DECL|field|title
specifier|private
specifier|final
name|Optional
argument_list|<
name|String
argument_list|>
name|title
decl_stmt|;
DECL|field|urlAbstractPage
specifier|private
specifier|final
name|Optional
argument_list|<
name|String
argument_list|>
name|urlAbstractPage
decl_stmt|;
DECL|field|publishedDate
specifier|private
specifier|final
name|Optional
argument_list|<
name|String
argument_list|>
name|publishedDate
decl_stmt|;
DECL|field|abstractText
specifier|private
specifier|final
name|Optional
argument_list|<
name|String
argument_list|>
name|abstractText
decl_stmt|;
DECL|field|authorNames
specifier|private
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|authorNames
decl_stmt|;
DECL|field|categories
specifier|private
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|categories
decl_stmt|;
DECL|field|pdfUrl
specifier|private
specifier|final
name|Optional
argument_list|<
name|URL
argument_list|>
name|pdfUrl
decl_stmt|;
DECL|field|doi
specifier|private
specifier|final
name|Optional
argument_list|<
name|String
argument_list|>
name|doi
decl_stmt|;
DECL|field|journalReferenceText
specifier|private
specifier|final
name|Optional
argument_list|<
name|String
argument_list|>
name|journalReferenceText
decl_stmt|;
DECL|field|primaryCategory
specifier|private
specifier|final
name|Optional
argument_list|<
name|String
argument_list|>
name|primaryCategory
decl_stmt|;
DECL|method|ArXivEntry (Node item)
specifier|public
name|ArXivEntry
parameter_list|(
name|Node
name|item
parameter_list|)
block|{
comment|// see http://arxiv.org/help/api/user-manual#_details_of_atom_results_returned
comment|// Title of the article
comment|// The result from the arXiv contains hard line breaks, try to remove them
name|title
operator|=
name|XMLUtil
operator|.
name|getNodeContent
argument_list|(
name|item
argument_list|,
literal|"title"
argument_list|)
operator|.
name|map
argument_list|(
name|OAI2Handler
operator|::
name|correctLineBreaks
argument_list|)
expr_stmt|;
comment|// The url leading to the abstract page
name|urlAbstractPage
operator|=
name|XMLUtil
operator|.
name|getNodeContent
argument_list|(
name|item
argument_list|,
literal|"id"
argument_list|)
expr_stmt|;
comment|// Date on which the first version was published
name|publishedDate
operator|=
name|XMLUtil
operator|.
name|getNodeContent
argument_list|(
name|item
argument_list|,
literal|"published"
argument_list|)
expr_stmt|;
comment|// Abstract of the article
name|abstractText
operator|=
name|XMLUtil
operator|.
name|getNodeContent
argument_list|(
name|item
argument_list|,
literal|"summary"
argument_list|)
operator|.
name|map
argument_list|(
name|OAI2Handler
operator|::
name|correctLineBreaks
argument_list|)
operator|.
name|map
argument_list|(
name|String
operator|::
name|trim
argument_list|)
expr_stmt|;
comment|// Authors of the article
name|authorNames
operator|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
expr_stmt|;
for|for
control|(
name|Node
name|authorNode
range|:
name|XMLUtil
operator|.
name|getNodesByName
argument_list|(
name|item
argument_list|,
literal|"author"
argument_list|)
control|)
block|{
name|Optional
argument_list|<
name|String
argument_list|>
name|authorName
init|=
name|XMLUtil
operator|.
name|getNodeContent
argument_list|(
name|authorNode
argument_list|,
literal|"name"
argument_list|)
operator|.
name|map
argument_list|(
name|String
operator|::
name|trim
argument_list|)
decl_stmt|;
name|authorName
operator|.
name|ifPresent
argument_list|(
name|authorNames
operator|::
name|add
argument_list|)
expr_stmt|;
block|}
comment|// Categories (arXiv, ACM, or MSC classification)
name|categories
operator|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
expr_stmt|;
for|for
control|(
name|Node
name|categoryNode
range|:
name|XMLUtil
operator|.
name|getNodesByName
argument_list|(
name|item
argument_list|,
literal|"category"
argument_list|)
control|)
block|{
name|Optional
argument_list|<
name|String
argument_list|>
name|category
init|=
name|XMLUtil
operator|.
name|getAttributeContent
argument_list|(
name|categoryNode
argument_list|,
literal|"term"
argument_list|)
decl_stmt|;
name|category
operator|.
name|ifPresent
argument_list|(
name|categories
operator|::
name|add
argument_list|)
expr_stmt|;
block|}
comment|// Links
name|Optional
argument_list|<
name|URL
argument_list|>
name|pdfUrlParsed
init|=
name|Optional
operator|.
name|empty
argument_list|()
decl_stmt|;
for|for
control|(
name|Node
name|linkNode
range|:
name|XMLUtil
operator|.
name|getNodesByName
argument_list|(
name|item
argument_list|,
literal|"link"
argument_list|)
control|)
block|{
name|Optional
argument_list|<
name|String
argument_list|>
name|linkTitle
init|=
name|XMLUtil
operator|.
name|getAttributeContent
argument_list|(
name|linkNode
argument_list|,
literal|"title"
argument_list|)
decl_stmt|;
if|if
condition|(
name|linkTitle
operator|.
name|equals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"pdf"
argument_list|)
argument_list|)
condition|)
block|{
name|pdfUrlParsed
operator|=
name|XMLUtil
operator|.
name|getAttributeContent
argument_list|(
name|linkNode
argument_list|,
literal|"href"
argument_list|)
operator|.
name|map
argument_list|(
name|url
lambda|->
block|{
try|try
block|{
return|return
operator|new
name|URL
argument_list|(
name|url
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|MalformedURLException
name|e
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
name|pdfUrl
operator|=
name|pdfUrlParsed
expr_stmt|;
comment|// Associated DOI
name|doi
operator|=
name|XMLUtil
operator|.
name|getNodeContent
argument_list|(
name|item
argument_list|,
literal|"arxiv:doi"
argument_list|)
expr_stmt|;
comment|// Journal reference (as provided by the author)
name|journalReferenceText
operator|=
name|XMLUtil
operator|.
name|getNodeContent
argument_list|(
name|item
argument_list|,
literal|"arxiv:journal_ref"
argument_list|)
expr_stmt|;
comment|// Primary category
comment|// Ex:<arxiv:primary_category xmlns:arxiv="http://arxiv.org/schemas/atom" term="math-ph" scheme="http://arxiv.org/schemas/atom"/>
name|primaryCategory
operator|=
name|XMLUtil
operator|.
name|getNode
argument_list|(
name|item
argument_list|,
literal|"arxiv:primary_category"
argument_list|)
operator|.
name|flatMap
argument_list|(
name|node
lambda|->
name|XMLUtil
operator|.
name|getAttributeContent
argument_list|(
name|node
argument_list|,
literal|"term"
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**          * Returns the url of the linked pdf          */
DECL|method|getPdfUrl ()
specifier|public
name|Optional
argument_list|<
name|URL
argument_list|>
name|getPdfUrl
parameter_list|()
block|{
return|return
name|pdfUrl
return|;
block|}
comment|/**          * Returns the arXiv identifier          */
DECL|method|getIdString ()
specifier|public
name|Optional
argument_list|<
name|String
argument_list|>
name|getIdString
parameter_list|()
block|{
comment|// remove leading http://arxiv.org/abs/ from abstract url to get arXiv ID
name|String
name|prefix
init|=
literal|"http://arxiv.org/abs/"
decl_stmt|;
return|return
name|urlAbstractPage
operator|.
name|map
argument_list|(
name|abstractUrl
lambda|->
block|{
if|if
condition|(
name|abstractUrl
operator|.
name|startsWith
argument_list|(
name|prefix
argument_list|)
condition|)
block|{
return|return
name|abstractUrl
operator|.
name|substring
argument_list|(
name|prefix
operator|.
name|length
argument_list|()
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|abstractUrl
return|;
block|}
block|}
argument_list|)
return|;
block|}
DECL|method|getId ()
specifier|public
name|Optional
argument_list|<
name|ArXivIdentifier
argument_list|>
name|getId
parameter_list|()
block|{
return|return
name|getIdString
argument_list|()
operator|.
name|flatMap
argument_list|(
name|ArXivIdentifier
operator|::
name|parse
argument_list|)
return|;
block|}
comment|/**          * Returns the date when the first version was put on the arXiv          */
DECL|method|getDate ()
specifier|public
name|Optional
argument_list|<
name|String
argument_list|>
name|getDate
parameter_list|()
block|{
comment|// Publication string also contains time, e.g. 2014-05-09T14:49:43Z
return|return
name|publishedDate
operator|.
name|map
argument_list|(
name|date
lambda|->
block|{
if|if
condition|(
name|date
operator|.
name|length
argument_list|()
operator|<
literal|10
condition|)
block|{
return|return
literal|null
return|;
block|}
else|else
block|{
return|return
name|date
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|10
argument_list|)
return|;
block|}
block|}
argument_list|)
return|;
block|}
DECL|method|toBibEntry (Character keywordDelimiter)
specifier|public
name|BibEntry
name|toBibEntry
parameter_list|(
name|Character
name|keywordDelimiter
parameter_list|)
block|{
name|BibEntry
name|bibEntry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|bibEntry
operator|.
name|setType
argument_list|(
name|BibtexEntryTypes
operator|.
name|ARTICLE
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|EPRINTTYPE
argument_list|,
literal|"arXiv"
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|AUTHOR
argument_list|,
name|String
operator|.
name|join
argument_list|(
literal|" and "
argument_list|,
name|authorNames
argument_list|)
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|addKeywords
argument_list|(
name|categories
argument_list|,
name|keywordDelimiter
argument_list|)
expr_stmt|;
name|getIdString
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|id
lambda|->
name|bibEntry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|EPRINT
argument_list|,
name|id
argument_list|)
argument_list|)
expr_stmt|;
name|title
operator|.
name|ifPresent
argument_list|(
name|titleContent
lambda|->
name|bibEntry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|TITLE
argument_list|,
name|titleContent
argument_list|)
argument_list|)
expr_stmt|;
name|doi
operator|.
name|ifPresent
argument_list|(
name|doiContent
lambda|->
name|bibEntry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|DOI
argument_list|,
name|doiContent
argument_list|)
argument_list|)
expr_stmt|;
name|abstractText
operator|.
name|ifPresent
argument_list|(
name|abstractContent
lambda|->
name|bibEntry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|ABSTRACT
argument_list|,
name|abstractContent
argument_list|)
argument_list|)
expr_stmt|;
name|getDate
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|date
lambda|->
name|bibEntry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|DATE
argument_list|,
name|date
argument_list|)
argument_list|)
expr_stmt|;
name|primaryCategory
operator|.
name|ifPresent
argument_list|(
name|category
lambda|->
name|bibEntry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|EPRINTCLASS
argument_list|,
name|category
argument_list|)
argument_list|)
expr_stmt|;
name|journalReferenceText
operator|.
name|ifPresent
argument_list|(
name|journal
lambda|->
name|bibEntry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|JOURNALTITLE
argument_list|,
name|journal
argument_list|)
argument_list|)
expr_stmt|;
name|getPdfUrl
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|url
lambda|->
name|bibEntry
operator|.
name|setFiles
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
operator|new
name|LinkedFile
argument_list|(
literal|"online"
argument_list|,
name|url
argument_list|,
literal|"PDF"
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|bibEntry
return|;
block|}
block|}
block|}
end_class

end_unit

