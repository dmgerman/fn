begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/**  *  */
end_comment

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
name|URI
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
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|EntryBasedFetcher
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
name|fileformat
operator|.
name|MrDLibImporter
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
name|database
operator|.
name|BibDatabase
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
comment|/**  * This class is responible to get the recommendations from MDL  */
end_comment

begin_class
DECL|class|MrDLibFetcher
specifier|public
class|class
name|MrDLibFetcher
implements|implements
name|EntryBasedFetcher
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
name|MrDLibFetcher
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|NAME
specifier|private
specifier|static
specifier|final
name|String
name|NAME
init|=
literal|"MDL_FETCHER"
decl_stmt|;
DECL|field|LANGUAGE
specifier|private
specifier|final
name|String
name|LANGUAGE
decl_stmt|;
DECL|field|VERSION
specifier|private
specifier|final
name|String
name|VERSION
decl_stmt|;
DECL|method|MrDLibFetcher (String language, String version)
specifier|public
name|MrDLibFetcher
parameter_list|(
name|String
name|language
parameter_list|,
name|String
name|version
parameter_list|)
block|{
name|LANGUAGE
operator|=
name|language
expr_stmt|;
name|VERSION
operator|=
name|version
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
name|NAME
return|;
block|}
annotation|@
name|Override
DECL|method|performSearch (BibEntry entry)
specifier|public
name|List
argument_list|<
name|BibEntry
argument_list|>
name|performSearch
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
throws|throws
name|FetcherException
block|{
name|Optional
argument_list|<
name|String
argument_list|>
name|title
init|=
name|entry
operator|.
name|getLatexFreeField
argument_list|(
name|FieldName
operator|.
name|TITLE
argument_list|)
decl_stmt|;
if|if
condition|(
name|title
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|String
name|response
init|=
name|makeServerRequest
argument_list|(
name|title
operator|.
name|get
argument_list|()
argument_list|)
decl_stmt|;
name|MrDLibImporter
name|importer
init|=
operator|new
name|MrDLibImporter
argument_list|()
decl_stmt|;
name|ParserResult
name|parserResult
init|=
operator|new
name|ParserResult
argument_list|()
decl_stmt|;
try|try
block|{
if|if
condition|(
name|importer
operator|.
name|isRecognizedFormat
argument_list|(
name|response
argument_list|)
condition|)
block|{
name|parserResult
operator|=
name|importer
operator|.
name|importDatabase
argument_list|(
name|response
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// For displaying An ErrorMessage
name|BibEntry
name|errorBibEntry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|errorBibEntry
operator|.
name|setField
argument_list|(
literal|"html_representation"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error while fetching from %0"
argument_list|,
literal|"Mr.DLib"
argument_list|)
argument_list|)
expr_stmt|;
name|BibDatabase
name|errorBibDataBase
init|=
operator|new
name|BibDatabase
argument_list|()
decl_stmt|;
name|errorBibDataBase
operator|.
name|insertEntry
argument_list|(
name|errorBibEntry
argument_list|)
expr_stmt|;
name|parserResult
operator|=
operator|new
name|ParserResult
argument_list|(
name|errorBibDataBase
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
name|LOGGER
operator|.
name|error
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|FetcherException
argument_list|(
literal|"XML Parser IOException."
argument_list|)
throw|;
block|}
return|return
name|parserResult
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
return|;
block|}
else|else
block|{
comment|// without a title there is no reason to ask MrDLib
return|return
operator|new
name|ArrayList
argument_list|<>
argument_list|(
literal|0
argument_list|)
return|;
block|}
block|}
comment|/**      * Contact the server with the title of the selected item      *      * @param query: The query holds the title of the selected entry. Used to make a query to the MDL Server      * @return Returns the server response. This is an XML document as a String.      */
DECL|method|makeServerRequest (String queryByTitle)
specifier|private
name|String
name|makeServerRequest
parameter_list|(
name|String
name|queryByTitle
parameter_list|)
throws|throws
name|FetcherException
block|{
try|try
block|{
name|URLDownload
name|urlDownload
init|=
operator|new
name|URLDownload
argument_list|(
name|constructQuery
argument_list|(
name|queryByTitle
argument_list|)
argument_list|)
decl_stmt|;
name|urlDownload
operator|.
name|bypassSSLVerification
argument_list|()
expr_stmt|;
name|String
name|response
init|=
name|urlDownload
operator|.
name|asString
argument_list|()
decl_stmt|;
comment|//Conversion of< and>
name|response
operator|=
name|response
operator|.
name|replaceAll
argument_list|(
literal|"&gt;"
argument_list|,
literal|">"
argument_list|)
expr_stmt|;
name|response
operator|=
name|response
operator|.
name|replaceAll
argument_list|(
literal|"&lt;"
argument_list|,
literal|"<"
argument_list|)
expr_stmt|;
return|return
name|response
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
literal|"Problem downloading"
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
comment|/**      * Constructs the query based on title of the bibentry. Adds statistical stuff to the url.      *      * @param query: the title of the bib entry.      * @return the string used to make the query at mdl server      */
DECL|method|constructQuery (String queryWithTitle)
specifier|private
name|String
name|constructQuery
parameter_list|(
name|String
name|queryWithTitle
parameter_list|)
block|{
comment|// The encoding does not work for / so we convert them by our own
name|queryWithTitle
operator|=
name|queryWithTitle
operator|.
name|replaceAll
argument_list|(
literal|"/"
argument_list|,
literal|"convbckslsh"
argument_list|)
expr_stmt|;
name|URIBuilder
name|builder
init|=
operator|new
name|URIBuilder
argument_list|()
decl_stmt|;
name|builder
operator|.
name|setScheme
argument_list|(
literal|"https"
argument_list|)
expr_stmt|;
name|builder
operator|.
name|setHost
argument_list|(
literal|"api.mr-dlib.org"
argument_list|)
expr_stmt|;
name|builder
operator|.
name|setPath
argument_list|(
literal|"/v1/documents/"
operator|+
name|queryWithTitle
operator|+
literal|"/related_documents"
argument_list|)
expr_stmt|;
name|builder
operator|.
name|addParameter
argument_list|(
literal|"partner_id"
argument_list|,
literal|"jabref"
argument_list|)
expr_stmt|;
name|builder
operator|.
name|addParameter
argument_list|(
literal|"app_id"
argument_list|,
literal|"jabref_desktop"
argument_list|)
expr_stmt|;
name|builder
operator|.
name|addParameter
argument_list|(
literal|"app_version"
argument_list|,
name|VERSION
argument_list|)
expr_stmt|;
name|builder
operator|.
name|addParameter
argument_list|(
literal|"app_lang"
argument_list|,
name|LANGUAGE
argument_list|)
expr_stmt|;
name|URI
name|uri
init|=
literal|null
decl_stmt|;
try|try
block|{
name|uri
operator|=
name|builder
operator|.
name|build
argument_list|()
expr_stmt|;
return|return
name|uri
operator|.
name|toString
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|URISyntaxException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
return|return
literal|""
return|;
block|}
block|}
end_class

end_unit

