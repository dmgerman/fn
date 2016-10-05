begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.importer
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
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|BufferedInputStream
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
name|Formatter
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
name|jsoup
operator|.
name|helper
operator|.
name|StringUtil
import|;
end_import

begin_comment
comment|/**  * Provides a convenient interface for search-based fetcher, which follow the usual three-step procedure:  * 1. Open a URL based on the search query  * 2. Parse the response to get a list of {@link BibEntry}  * 3. Post-process fetched entries  */
end_comment

begin_interface
DECL|interface|SearchBasedParserFetcher
specifier|public
interface|interface
name|SearchBasedParserFetcher
extends|extends
name|SearchBasedFetcher
block|{
comment|/**      * Constructs a URL based on the query.      * @param query the search query      */
DECL|method|getURLForQuery (String query)
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
function_decl|;
comment|/**      * Returns the parser used to convert the response to a list of {@link BibEntry}.      */
DECL|method|getParser ()
name|Parser
name|getParser
parameter_list|()
function_decl|;
comment|/**      * Performs a cleanup of the fetched entry.      *      * Only systematic errors of the fetcher should be corrected here      * (i.e. if information is consistently contained in the wrong field or the wrong format)      * but not cosmetic issues which may depend on the user's taste (for example, LateX code vs HTML in the abstract).      *      * Try to reuse existing {@link Formatter} for the cleanup. For example,      * {@code new FieldFormatterCleanup(FieldName.TITLE, new RemoveBracesFormatter()).cleanup(entry);}      *      * By default, no cleanup is done.      * @param entry the entry to be cleaned-up      */
DECL|method|doPostCleanup (BibEntry entry)
specifier|default
name|void
name|doPostCleanup
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
comment|// Do nothing by default
block|}
annotation|@
name|Override
DECL|method|performSearch (String query)
specifier|default
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
init|(
name|InputStream
name|stream
init|=
operator|new
name|BufferedInputStream
argument_list|(
name|getURLForQuery
argument_list|(
name|query
argument_list|)
operator|.
name|openStream
argument_list|()
argument_list|)
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
name|URISyntaxException
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
comment|// TODO: Catch HTTP Response 401 errors and report that user has no rights to access resource
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
literal|"An internal parser error occurred"
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
block|}
end_interface

end_unit

