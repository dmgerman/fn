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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|formatter
operator|.
name|bibtexfields
operator|.
name|ClearFormatter
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
name|formatter
operator|.
name|bibtexfields
operator|.
name|NormalizeNamesFormatter
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
name|formatter
operator|.
name|bibtexfields
operator|.
name|RemoveBracesFormatter
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
name|EntryBasedParserFetcher
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
name|ParseException
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
name|SearchBasedParserFetcher
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
name|cleanup
operator|.
name|FieldFormatterCleanup
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
name|helper
operator|.
name|StringUtil
import|;
end_import

begin_comment
comment|/**  * Fetches data from the SAO/NASA Astrophysics Data System (http://www.adsabs.harvard.edu/)  *  * Search query-based: http://adsabs.harvard.edu/basic_search.html  * Entry -based: http://adsabs.harvard.edu/abstract_service.html  *  * There is also a new API (https://github.com/adsabs/adsabs-dev-api) but it returns JSON  * (or at least needs multiple calls to get BibTeX, status: September 2016)  */
end_comment

begin_class
DECL|class|AstrophysicsDataSystem
specifier|public
class|class
name|AstrophysicsDataSystem
implements|implements
name|IdBasedParserFetcher
implements|,
name|SearchBasedParserFetcher
implements|,
name|EntryBasedParserFetcher
block|{
DECL|field|API_QUERY_URL
specifier|private
specifier|static
name|String
name|API_QUERY_URL
init|=
literal|"http://adsabs.harvard.edu/cgi-bin/nph-basic_connect"
decl_stmt|;
DECL|field|API_ENTRY_URL
specifier|private
specifier|static
name|String
name|API_ENTRY_URL
init|=
literal|"http://adsabs.harvard.edu/cgi-bin/nph-abs_connect"
decl_stmt|;
DECL|field|API_DOI_URL
specifier|private
specifier|static
name|String
name|API_DOI_URL
init|=
literal|"http://adsabs.harvard.edu/doi/"
decl_stmt|;
DECL|field|patternRemoveDOI
specifier|private
specifier|final
name|String
name|patternRemoveDOI
init|=
literal|"^(doi:|DOI:)"
decl_stmt|;
DECL|field|preferences
specifier|private
specifier|final
name|ImportFormatPreferences
name|preferences
decl_stmt|;
DECL|method|AstrophysicsDataSystem (ImportFormatPreferences preferences)
specifier|public
name|AstrophysicsDataSystem
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
annotation|@
name|Override
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"SAO/NASA Astrophysics Data System"
return|;
block|}
DECL|method|getBaseUrl (String apiUrl)
specifier|private
name|URIBuilder
name|getBaseUrl
parameter_list|(
name|String
name|apiUrl
parameter_list|)
throws|throws
name|URISyntaxException
block|{
name|URIBuilder
name|uriBuilder
init|=
operator|new
name|URIBuilder
argument_list|(
name|apiUrl
argument_list|)
decl_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"data_type"
argument_list|,
literal|"BIBTEXPLUS"
argument_list|)
expr_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"start_nr"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"nr_to_return"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
literal|200
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|uriBuilder
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
name|getBaseUrl
argument_list|(
name|API_QUERY_URL
argument_list|)
decl_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"qsearch"
argument_list|,
name|query
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
DECL|method|getURLForEntry (BibEntry entry)
specifier|public
name|URL
name|getURLForEntry
parameter_list|(
name|BibEntry
name|entry
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
name|getBaseUrl
argument_list|(
name|API_ENTRY_URL
argument_list|)
decl_stmt|;
comment|// Search astronomy + physics + arXiv db
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"db_key"
argument_list|,
literal|"AST"
argument_list|)
expr_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"db_key"
argument_list|,
literal|"PHY"
argument_list|)
expr_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"db_key"
argument_list|,
literal|"PRE"
argument_list|)
expr_stmt|;
comment|// Add title search
name|entry
operator|.
name|getFieldOrAlias
argument_list|(
name|FieldName
operator|.
name|TITLE
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|title
lambda|->
block|{
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"ttl_logic"
argument_list|,
literal|"OR"
argument_list|)
expr_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"title"
argument_list|,
name|title
argument_list|)
expr_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"ttl_syn"
argument_list|,
literal|"YES"
argument_list|)
expr_stmt|;
comment|// Synonym replacement
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"ttl_wt"
argument_list|,
literal|"0.3"
argument_list|)
expr_stmt|;
comment|// Weight
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"ttl_wgt"
argument_list|,
literal|"YES"
argument_list|)
expr_stmt|;
comment|// Consider Weight
block|}
argument_list|)
expr_stmt|;
comment|// Add author search
name|entry
operator|.
name|getFieldOrAlias
argument_list|(
name|FieldName
operator|.
name|AUTHOR
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|author
lambda|->
block|{
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"aut_logic"
argument_list|,
literal|"OR"
argument_list|)
expr_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"author"
argument_list|,
name|author
argument_list|)
expr_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"aut_syn"
argument_list|,
literal|"YES"
argument_list|)
expr_stmt|;
comment|// Synonym replacement
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"aut_wt"
argument_list|,
literal|"1.0"
argument_list|)
expr_stmt|;
comment|// Weight
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"aut_wgt"
argument_list|,
literal|"YES"
argument_list|)
expr_stmt|;
comment|// Consider weight
block|}
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
name|String
name|key
init|=
name|identifier
operator|.
name|replaceAll
argument_list|(
name|patternRemoveDOI
argument_list|,
literal|""
argument_list|)
decl_stmt|;
name|URIBuilder
name|uriBuilder
init|=
operator|new
name|URIBuilder
argument_list|(
name|API_DOI_URL
operator|+
name|key
argument_list|)
decl_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"data_type"
argument_list|,
literal|"BIBTEXPLUS"
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
DECL|method|getParser ()
specifier|public
name|Parser
name|getParser
parameter_list|()
block|{
return|return
operator|new
name|BibtexParser
argument_list|(
name|preferences
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
if|if
condition|(
name|StringUtil
operator|.
name|isBlank
argument_list|(
name|query
argument_list|)
condition|)
block|{
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
try|try
block|{
name|URLConnection
name|connection
init|=
name|getURLForQuery
argument_list|(
name|query
argument_list|)
operator|.
name|openConnection
argument_list|()
decl_stmt|;
name|connection
operator|.
name|setRequestProperty
argument_list|(
literal|"User-Agent"
argument_list|,
literal|"Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0"
argument_list|)
expr_stmt|;
try|try
init|(
name|InputStream
name|stream
init|=
name|connection
operator|.
name|getInputStream
argument_list|()
init|)
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|fetchedEntries
init|=
name|getParser
argument_list|()
operator|.
name|parseEntries
argument_list|(
name|stream
argument_list|)
decl_stmt|;
comment|// Post-cleanup
name|fetchedEntries
operator|.
name|forEach
argument_list|(
name|this
operator|::
name|doPostCleanup
argument_list|)
expr_stmt|;
return|return
name|fetchedEntries
return|;
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
literal|"An I/O exception occurred"
argument_list|,
name|e
argument_list|)
throw|;
block|}
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
literal|"Search URI is malformed"
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
literal|"An I/O exception occurred"
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|ParseException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|FetcherException
argument_list|(
literal|"Error occurred when parsing entry"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error occurred when parsing entry"
argument_list|)
argument_list|,
name|e
argument_list|)
throw|;
block|}
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
operator|new
name|FieldFormatterCleanup
argument_list|(
name|FieldName
operator|.
name|ABSTRACT
argument_list|,
operator|new
name|RemoveBracesFormatter
argument_list|()
argument_list|)
operator|.
name|cleanup
argument_list|(
name|entry
argument_list|)
expr_stmt|;
operator|new
name|FieldFormatterCleanup
argument_list|(
name|FieldName
operator|.
name|TITLE
argument_list|,
operator|new
name|RemoveBracesFormatter
argument_list|()
argument_list|)
operator|.
name|cleanup
argument_list|(
name|entry
argument_list|)
expr_stmt|;
operator|new
name|FieldFormatterCleanup
argument_list|(
name|FieldName
operator|.
name|AUTHOR
argument_list|,
operator|new
name|NormalizeNamesFormatter
argument_list|()
argument_list|)
operator|.
name|cleanup
argument_list|(
name|entry
argument_list|)
expr_stmt|;
comment|// Remove url to ADS page
operator|new
name|FieldFormatterCleanup
argument_list|(
literal|"adsnote"
argument_list|,
operator|new
name|ClearFormatter
argument_list|()
argument_list|)
operator|.
name|cleanup
argument_list|(
name|entry
argument_list|)
expr_stmt|;
operator|new
name|FieldFormatterCleanup
argument_list|(
literal|"adsurl"
argument_list|,
operator|new
name|ClearFormatter
argument_list|()
argument_list|)
operator|.
name|cleanup
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
