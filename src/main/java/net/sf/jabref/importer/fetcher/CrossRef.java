begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.importer.fetcher
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
operator|.
name|fetcher
package|;
end_package

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
name|json
operator|.
name|JSONArray
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

begin_comment
comment|/**  * A class for fetching DOIs from CrossRef  *  * @see https://github.com/CrossRef/rest-api-doc  */
end_comment

begin_class
DECL|class|CrossRef
specifier|public
class|class
name|CrossRef
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
name|CrossRef
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
literal|"http://api.crossref.org"
decl_stmt|;
DECL|method|findDOI (BibEntry entry)
specifier|public
specifier|static
name|Optional
argument_list|<
name|DOI
argument_list|>
name|findDOI
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
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
name|DOI
argument_list|>
name|doi
init|=
name|Optional
operator|.
name|empty
argument_list|()
decl_stmt|;
comment|// title is minimum requirement
name|String
name|title
init|=
name|entry
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|title
operator|==
literal|null
operator|)
operator|||
name|title
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
name|doi
return|;
block|}
name|String
name|query
init|=
name|enhanceQuery
argument_list|(
name|title
argument_list|,
name|entry
argument_list|)
decl_stmt|;
try|try
block|{
name|HttpResponse
argument_list|<
name|JsonNode
argument_list|>
name|response
init|=
name|Unirest
operator|.
name|get
argument_list|(
name|API_URL
operator|+
literal|"/works"
argument_list|)
operator|.
name|queryString
argument_list|(
literal|"query"
argument_list|,
name|query
argument_list|)
operator|.
name|queryString
argument_list|(
literal|"rows"
argument_list|,
literal|"1"
argument_list|)
operator|.
name|asJson
argument_list|()
decl_stmt|;
name|JSONArray
name|items
init|=
name|response
operator|.
name|getBody
argument_list|()
operator|.
name|getObject
argument_list|()
operator|.
name|getJSONObject
argument_list|(
literal|"message"
argument_list|)
operator|.
name|getJSONArray
argument_list|(
literal|"items"
argument_list|)
decl_stmt|;
name|String
name|dataTitle
init|=
name|items
operator|.
name|getJSONObject
argument_list|(
literal|0
argument_list|)
operator|.
name|getJSONArray
argument_list|(
literal|"title"
argument_list|)
operator|.
name|getString
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|String
name|dataDOI
init|=
name|items
operator|.
name|getJSONObject
argument_list|(
literal|0
argument_list|)
operator|.
name|getString
argument_list|(
literal|"DOI"
argument_list|)
decl_stmt|;
name|LOGGER
operator|.
name|info
argument_list|(
literal|"DOI "
operator|+
name|dataDOI
operator|+
literal|" for "
operator|+
name|title
operator|+
literal|" found."
argument_list|)
expr_stmt|;
return|return
name|DOI
operator|.
name|build
argument_list|(
name|dataDOI
argument_list|)
return|;
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
literal|"Unable to query CrossRef API: "
operator|+
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
name|doi
return|;
block|}
DECL|method|enhanceQuery (String query, BibEntry entry)
specifier|private
specifier|static
name|String
name|enhanceQuery
parameter_list|(
name|String
name|query
parameter_list|,
name|BibEntry
name|entry
parameter_list|)
block|{
name|StringBuilder
name|enhancedQuery
init|=
operator|new
name|StringBuilder
argument_list|(
name|query
argument_list|)
decl_stmt|;
comment|// author
name|String
name|author
init|=
name|entry
operator|.
name|getField
argument_list|(
literal|"author"
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|author
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|author
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|enhancedQuery
operator|.
name|append
argument_list|(
literal|'+'
argument_list|)
operator|.
name|append
argument_list|(
name|author
argument_list|)
expr_stmt|;
block|}
comment|// year
name|String
name|year
init|=
name|entry
operator|.
name|getField
argument_list|(
literal|"year"
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|year
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|year
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|enhancedQuery
operator|.
name|append
argument_list|(
literal|'+'
argument_list|)
operator|.
name|append
argument_list|(
name|year
argument_list|)
expr_stmt|;
block|}
return|return
name|enhancedQuery
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

