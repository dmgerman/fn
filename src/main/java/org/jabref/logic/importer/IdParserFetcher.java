begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.importer
package|package
name|org
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
name|FileNotFoundException
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
name|org
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
name|identifier
operator|.
name|Identifier
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

begin_comment
comment|/**  * Provides a convenient interface for {@link IdFetcher}, which follow the usual three-step procedure:  * 1. Open a URL based on the search query  * 2. Parse the response to get a list of {@link BibEntry}  * 3. Extract identifier  */
end_comment

begin_interface
DECL|interface|IdParserFetcher
specifier|public
interface|interface
name|IdParserFetcher
parameter_list|<
name|T
extends|extends
name|Identifier
parameter_list|>
extends|extends
name|IdFetcher
argument_list|<
name|T
argument_list|>
block|{
DECL|field|LOGGER
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|IdParserFetcher
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * Constructs a URL based on the {@link BibEntry}.      *      * @param entry the entry to look information for      */
DECL|method|getURLForEntry (BibEntry entry)
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
function_decl|;
comment|/**      * Returns the parser used to convert the response to a list of {@link BibEntry}.      */
DECL|method|getParser ()
name|Parser
name|getParser
parameter_list|()
function_decl|;
comment|/**      * Performs a cleanup of the fetched entry.      *      * Only systematic errors of the fetcher should be corrected here      * (i.e. if information is consistently contained in the wrong field or the wrong format)      * but not cosmetic issues which may depend on the user's taste (for example, LateX code vs HTML in the abstract).      *      * Try to reuse existing {@link Formatter} for the cleanup. For example,      * {@code new FieldFormatterCleanup(FieldName.TITLE, new RemoveBracesFormatter()).cleanup(entry);}      *      * By default, no cleanup is done.      *      * @param entry the entry to be cleaned-up      */
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
comment|/**      * Extracts the identifier from the list of fetched entries.      *      * @param inputEntry     the entry for which we are searching the identifier (can be used to find closest match in      *                       the result)      * @param fetchedEntries list of entries returned by the web service      */
DECL|method|extractIdentifier (BibEntry inputEntry, List<BibEntry> fetchedEntries)
name|Optional
argument_list|<
name|T
argument_list|>
name|extractIdentifier
parameter_list|(
name|BibEntry
name|inputEntry
parameter_list|,
name|List
argument_list|<
name|BibEntry
argument_list|>
name|fetchedEntries
parameter_list|)
throws|throws
name|FetcherException
function_decl|;
annotation|@
name|Override
DECL|method|findIdentifier (BibEntry entry)
specifier|default
name|Optional
argument_list|<
name|T
argument_list|>
name|findIdentifier
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
throws|throws
name|FetcherException
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|entry
argument_list|)
expr_stmt|;
try|try
init|(
name|InputStream
name|stream
init|=
operator|new
name|BufferedInputStream
argument_list|(
name|getURLForEntry
argument_list|(
name|entry
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
if|if
condition|(
name|fetchedEntries
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
name|extractIdentifier
argument_list|(
name|entry
argument_list|,
name|fetchedEntries
argument_list|)
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
name|FileNotFoundException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Id not found"
argument_list|)
expr_stmt|;
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
comment|// TODO: Catch HTTP Response 401 errors and report that user has no rights to access resource
comment|// TODO catch 503 service unavailable and alert user
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
