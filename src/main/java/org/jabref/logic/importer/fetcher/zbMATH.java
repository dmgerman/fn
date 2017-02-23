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
name|security
operator|.
name|SecureRandom
import|;
end_import

begin_import
import|import
name|java
operator|.
name|security
operator|.
name|cert
operator|.
name|CertificateException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|security
operator|.
name|cert
operator|.
name|X509Certificate
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
name|javax
operator|.
name|net
operator|.
name|ssl
operator|.
name|HttpsURLConnection
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|net
operator|.
name|ssl
operator|.
name|SSLContext
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|net
operator|.
name|ssl
operator|.
name|TrustManager
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|net
operator|.
name|ssl
operator|.
name|X509TrustManager
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
name|cleanup
operator|.
name|MoveFieldCleanup
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
name|formatter
operator|.
name|bibtexfields
operator|.
name|RemoveBracesFormatter
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
name|cleanup
operator|.
name|FieldFormatterCleanup
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
comment|/**  * Fetches data from the Zentralblatt Math (https://www.zbmath.org/)  */
end_comment

begin_class
DECL|class|zbMATH
specifier|public
class|class
name|zbMATH
implements|implements
name|SearchBasedParserFetcher
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
name|zbMATH
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|preferences
specifier|private
specifier|final
name|ImportFormatPreferences
name|preferences
decl_stmt|;
DECL|method|zbMATH (ImportFormatPreferences preferences)
specifier|public
name|zbMATH
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
literal|"zbMATH"
return|;
block|}
comment|/**      * TODO: Implement EntryBasedParserFetcher      * We use the zbMATH Citation matcher (https://www.zbmath.org/citationmatching/)      * instead of the usual search since this tool is optimized for finding a publication based on partial information.      */
comment|/*     @Override     public URL getURLForEntry(BibEntry entry) throws URISyntaxException, MalformedURLException, FetcherException {         // Example: https://zbmath.org/citationmatching/match?q=Ratiu     }     */
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
literal|"https://zbmath.org/bibtexoutput/"
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
comment|// search all fields
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"start"
argument_list|,
literal|"0"
argument_list|)
expr_stmt|;
comment|// start index
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"count"
argument_list|,
literal|"200"
argument_list|)
expr_stmt|;
comment|// should return up to 200 items (instead of default 100)
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
operator|new
name|BibtexParser
argument_list|(
name|preferences
argument_list|)
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
operator|new
name|MoveFieldCleanup
argument_list|(
literal|"msc2010"
argument_list|,
name|FieldName
operator|.
name|KEYWORDS
argument_list|)
operator|.
name|cleanup
argument_list|(
name|entry
argument_list|)
expr_stmt|;
operator|new
name|MoveFieldCleanup
argument_list|(
literal|"fjournal"
argument_list|,
name|FieldName
operator|.
name|JOURNAL
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
name|JOURNAL
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
block|}
block|}
end_class

end_unit

