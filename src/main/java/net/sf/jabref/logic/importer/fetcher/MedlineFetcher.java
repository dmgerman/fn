begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.importer.fetcher
package|package
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
name|net
operator|.
name|URLConnection
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
name|LinkedList
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
name|XMLStreamConstants
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
name|IdBasedParserFetcher
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
name|Parser
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
name|SearchBasedFetcher
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
name|MedlineImporter
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

begin_comment
comment|/**  * Fetch or search from PubMed<a href="http://www.ncbi.nlm.nih.gov/sites/entrez/">www.ncbi.nlm.nih.gov</a>  * The MedlineFetcher fetches the entries from the PubMed database.  * See<a href="http://help.jabref.org/en/MedlineRIS">help.jabref.org</a> for a detailed documentation of the available fields.  */
end_comment

begin_class
DECL|class|MedlineFetcher
specifier|public
class|class
name|MedlineFetcher
implements|implements
name|IdBasedParserFetcher
implements|,
name|SearchBasedFetcher
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
name|MedlineFetcher
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|NUMBER_TO_FETCH
specifier|private
specifier|static
specifier|final
name|int
name|NUMBER_TO_FETCH
init|=
literal|50
decl_stmt|;
DECL|field|numberOfResultsFound
specifier|private
name|int
name|numberOfResultsFound
decl_stmt|;
comment|/**      * Replaces all commas in a given string with " AND "      *      * @param query input to remove commas      * @return input without commas      */
DECL|method|replaceCommaWithAND (String query)
specifier|private
specifier|static
name|String
name|replaceCommaWithAND
parameter_list|(
name|String
name|query
parameter_list|)
block|{
return|return
name|query
operator|.
name|replaceAll
argument_list|(
literal|", "
argument_list|,
literal|" AND "
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|","
argument_list|,
literal|" AND "
argument_list|)
return|;
block|}
comment|/**      * When using 'esearch.fcgi?db=<database>&term=<query>' we will get a list of IDs matching the query.      * Input: Any text query (&term)      * Output: List of UIDs matching the query      *      * @see<a href="https://www.ncbi.nlm.nih.gov/books/NBK25500/">www.ncbi.nlm.nih.gov/books/NBK25500/</a>      */
DECL|method|getPubMedIdsFromQuery (String query)
specifier|private
name|List
argument_list|<
name|String
argument_list|>
name|getPubMedIdsFromQuery
parameter_list|(
name|String
name|query
parameter_list|)
throws|throws
name|FetcherException
block|{
name|boolean
name|fetchIDs
init|=
literal|false
decl_stmt|;
name|boolean
name|firstOccurrenceOfCount
init|=
literal|false
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|idList
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
try|try
block|{
name|URL
name|ncbi
init|=
name|createSearchUrl
argument_list|(
name|query
argument_list|)
decl_stmt|;
name|XMLInputFactory
name|inputFactory
init|=
name|XMLInputFactory
operator|.
name|newFactory
argument_list|()
decl_stmt|;
name|XMLStreamReader
name|streamReader
init|=
name|inputFactory
operator|.
name|createXMLStreamReader
argument_list|(
name|ncbi
operator|.
name|openStream
argument_list|()
argument_list|)
decl_stmt|;
name|fetchLoop
label|:
while|while
condition|(
name|streamReader
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|int
name|event
init|=
name|streamReader
operator|.
name|getEventType
argument_list|()
decl_stmt|;
switch|switch
condition|(
name|event
condition|)
block|{
case|case
name|XMLStreamConstants
operator|.
name|START_ELEMENT
case|:
if|if
condition|(
name|streamReader
operator|.
name|getName
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
literal|"Count"
argument_list|)
condition|)
block|{
name|firstOccurrenceOfCount
operator|=
literal|true
expr_stmt|;
block|}
if|if
condition|(
name|streamReader
operator|.
name|getName
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
literal|"IdList"
argument_list|)
condition|)
block|{
name|fetchIDs
operator|=
literal|true
expr_stmt|;
block|}
break|break;
case|case
name|XMLStreamConstants
operator|.
name|CHARACTERS
case|:
if|if
condition|(
name|firstOccurrenceOfCount
condition|)
block|{
name|numberOfResultsFound
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|streamReader
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|firstOccurrenceOfCount
operator|=
literal|false
expr_stmt|;
block|}
if|if
condition|(
name|fetchIDs
condition|)
block|{
name|idList
operator|.
name|add
argument_list|(
name|streamReader
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
block|}
break|break;
case|case
name|XMLStreamConstants
operator|.
name|END_ELEMENT
case|:
comment|//Everything relevant is listed before the IdList. So we break the loop right after the IdList tag closes.
if|if
condition|(
name|streamReader
operator|.
name|getName
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
literal|"IdList"
argument_list|)
condition|)
block|{
break|break
name|fetchLoop
break|;
block|}
block|}
name|streamReader
operator|.
name|next
argument_list|()
expr_stmt|;
block|}
name|streamReader
operator|.
name|close
argument_list|()
expr_stmt|;
return|return
name|idList
return|;
block|}
catch|catch
parameter_list|(
name|IOException
decl||
name|URISyntaxException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|FetcherException
argument_list|(
literal|"Unable to get PubMed IDs"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Unable to get PubMed IDs"
argument_list|)
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|XMLStreamException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|FetcherException
argument_list|(
literal|"Error while parsing ID list"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error while parsing ID list"
argument_list|)
argument_list|,
name|e
argument_list|)
throw|;
block|}
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
literal|"Medline"
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
name|FETCHER_MEDLINE
return|;
block|}
annotation|@
name|Override
DECL|method|getURLForID (String identifier)
specifier|public
name|URL
name|getURLForID
parameter_list|(
name|String
name|identifier
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
literal|"http://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi"
argument_list|)
decl_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"db"
argument_list|,
literal|"pubmed"
argument_list|)
expr_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"retmode"
argument_list|,
literal|"xml"
argument_list|)
expr_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"id"
argument_list|,
name|identifier
argument_list|)
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
operator|new
name|MedlineImporter
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|doPostCleanup (BibEntry entry)
specifier|public
name|void
name|doPostCleanup
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
name|entry
operator|.
name|clearField
argument_list|(
literal|"journal-abbreviation"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|clearField
argument_list|(
literal|"status"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|clearField
argument_list|(
literal|"copyright"
argument_list|)
expr_stmt|;
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
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entryList
init|=
operator|new
name|LinkedList
argument_list|<>
argument_list|()
decl_stmt|;
if|if
condition|(
name|query
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
else|else
block|{
name|String
name|searchTerm
init|=
name|replaceCommaWithAND
argument_list|(
name|query
argument_list|)
decl_stmt|;
comment|//searching for pubmed ids matching the query
name|List
argument_list|<
name|String
argument_list|>
name|idList
init|=
name|getPubMedIdsFromQuery
argument_list|(
name|searchTerm
argument_list|)
decl_stmt|;
if|if
condition|(
name|idList
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"No results found."
argument_list|)
expr_stmt|;
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
if|if
condition|(
name|numberOfResultsFound
operator|>
name|NUMBER_TO_FETCH
condition|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
name|numberOfResultsFound
operator|+
literal|" results found. Only 50 relevant results will be fetched by default."
argument_list|)
expr_stmt|;
block|}
comment|//pass the list of ids to fetchMedline to download them. like a id fetcher for mutliple ids
name|entryList
operator|=
name|fetchMedline
argument_list|(
name|idList
argument_list|)
expr_stmt|;
return|return
name|entryList
return|;
block|}
block|}
DECL|method|createSearchUrl (String term)
specifier|private
name|URL
name|createSearchUrl
parameter_list|(
name|String
name|term
parameter_list|)
throws|throws
name|URISyntaxException
throws|,
name|MalformedURLException
block|{
name|term
operator|=
name|replaceCommaWithAND
argument_list|(
name|term
argument_list|)
expr_stmt|;
name|URIBuilder
name|uriBuilder
init|=
operator|new
name|URIBuilder
argument_list|(
literal|"http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi"
argument_list|)
decl_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"db"
argument_list|,
literal|"pubmed"
argument_list|)
expr_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"sort"
argument_list|,
literal|"relevance"
argument_list|)
expr_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"retmax"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|NUMBER_TO_FETCH
argument_list|)
argument_list|)
expr_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"term"
argument_list|,
name|term
argument_list|)
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
comment|/**      * Fetch and parse an medline item from eutils.ncbi.nlm.nih.gov.      * The E-utilities generate a huge XML file containing all entries for the ids      *      * @param ids A list of IDs to search for.      * @return Will return an empty list on error.      */
DECL|method|fetchMedline (List<String> ids)
specifier|private
name|List
argument_list|<
name|BibEntry
argument_list|>
name|fetchMedline
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|ids
parameter_list|)
throws|throws
name|FetcherException
block|{
try|try
block|{
comment|//Separate the IDs with a comma to search multiple entries
name|URL
name|fetchURL
init|=
name|getURLForID
argument_list|(
name|String
operator|.
name|join
argument_list|(
literal|","
argument_list|,
name|ids
argument_list|)
argument_list|)
decl_stmt|;
name|URLConnection
name|data
init|=
name|fetchURL
operator|.
name|openConnection
argument_list|()
decl_stmt|;
name|ParserResult
name|result
init|=
operator|new
name|MedlineImporter
argument_list|()
operator|.
name|importDatabase
argument_list|(
operator|new
name|BufferedReader
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
name|data
operator|.
name|getInputStream
argument_list|()
argument_list|,
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|result
operator|.
name|hasWarnings
argument_list|()
condition|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
name|result
operator|.
name|getErrorMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|List
argument_list|<
name|BibEntry
argument_list|>
name|resultList
init|=
name|result
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|resultList
operator|.
name|forEach
argument_list|(
name|this
operator|::
name|doPostCleanup
argument_list|)
expr_stmt|;
return|return
name|resultList
return|;
block|}
catch|catch
parameter_list|(
name|URISyntaxException
decl||
name|MalformedURLException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|FetcherException
argument_list|(
literal|"Error while generating fetch URL"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error while generating fetch URL"
argument_list|)
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
throw|throw
operator|new
name|FetcherException
argument_list|(
literal|"Error while fetching from Medline"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error while fetching from %0"
argument_list|,
literal|"Medline"
argument_list|)
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit

