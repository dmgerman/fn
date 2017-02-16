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
name|FulltextFetcher
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
name|util
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
name|com
operator|.
name|mashape
operator|.
name|unirest
operator|.
name|http
operator|.
name|HttpResponse
import|;
end_import

begin_import
import|import
name|com
operator|.
name|mashape
operator|.
name|unirest
operator|.
name|http
operator|.
name|JsonNode
import|;
end_import

begin_import
import|import
name|com
operator|.
name|mashape
operator|.
name|unirest
operator|.
name|http
operator|.
name|Unirest
import|;
end_import

begin_import
import|import
name|com
operator|.
name|mashape
operator|.
name|unirest
operator|.
name|http
operator|.
name|exceptions
operator|.
name|UnirestException
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
name|json
operator|.
name|JSONObject
import|;
end_import

begin_comment
comment|/**  * FulltextFetcher implementation that attempts to find a PDF URL at SpringerLink.  *  * Uses Springer API, see @link{https://dev.springer.com}  */
end_comment

begin_class
DECL|class|SpringerLink
specifier|public
class|class
name|SpringerLink
implements|implements
name|FulltextFetcher
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
name|SpringerLink
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
literal|"http://api.springer.com/meta/v1/json"
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
DECL|field|CONTENT_HOST
specifier|private
specifier|static
specifier|final
name|String
name|CONTENT_HOST
init|=
literal|"link.springer.com"
decl_stmt|;
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
name|Optional
argument_list|<
name|URL
argument_list|>
name|pdfLink
init|=
name|Optional
operator|.
name|empty
argument_list|()
decl_stmt|;
comment|// Try unique DOI first
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
name|FieldName
operator|.
name|DOI
argument_list|)
operator|.
name|flatMap
argument_list|(
name|DOI
operator|::
name|build
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
comment|// Available in catalog?
try|try
block|{
name|HttpResponse
argument_list|<
name|JsonNode
argument_list|>
name|jsonResponse
init|=
name|Unirest
operator|.
name|get
argument_list|(
name|API_URL
argument_list|)
operator|.
name|queryString
argument_list|(
literal|"api_key"
argument_list|,
name|API_KEY
argument_list|)
operator|.
name|queryString
argument_list|(
literal|"q"
argument_list|,
name|String
operator|.
name|format
argument_list|(
literal|"doi:%s"
argument_list|,
name|doi
operator|.
name|get
argument_list|()
operator|.
name|getDOI
argument_list|()
argument_list|)
argument_list|)
operator|.
name|asJson
argument_list|()
decl_stmt|;
name|JSONObject
name|json
init|=
name|jsonResponse
operator|.
name|getBody
argument_list|()
operator|.
name|getObject
argument_list|()
decl_stmt|;
name|int
name|results
init|=
name|json
operator|.
name|getJSONArray
argument_list|(
literal|"result"
argument_list|)
operator|.
name|getJSONObject
argument_list|(
literal|0
argument_list|)
operator|.
name|getInt
argument_list|(
literal|"total"
argument_list|)
decl_stmt|;
if|if
condition|(
name|results
operator|>
literal|0
condition|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Fulltext PDF found @ Springer."
argument_list|)
expr_stmt|;
name|pdfLink
operator|=
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|URL
argument_list|(
literal|"http"
argument_list|,
name|CONTENT_HOST
argument_list|,
name|String
operator|.
name|format
argument_list|(
literal|"/content/pdf/%s.pdf"
argument_list|,
name|doi
operator|.
name|get
argument_list|()
operator|.
name|getDOI
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|UnirestException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"SpringerLink API request failed"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|pdfLink
return|;
block|}
block|}
end_class

end_unit

