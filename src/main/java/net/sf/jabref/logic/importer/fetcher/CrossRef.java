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
name|util
operator|.
name|Locale
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
name|layout
operator|.
name|format
operator|.
name|LatexToUnicodeFormatter
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
name|info
operator|.
name|debatty
operator|.
name|java
operator|.
name|stringsimilarity
operator|.
name|Levenshtein
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
name|org
operator|.
name|json
operator|.
name|JSONException
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
comment|/**  * A class for fetching DOIs from CrossRef  *  * See https://github.com/CrossRef/rest-api-doc  */
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
DECL|field|METRIC_DISTANCE
specifier|private
specifier|static
specifier|final
name|Levenshtein
name|METRIC_DISTANCE
init|=
operator|new
name|Levenshtein
argument_list|()
decl_stmt|;
DECL|field|METRIC_THRESHOLD
specifier|private
specifier|static
specifier|final
name|int
name|METRIC_THRESHOLD
init|=
literal|4
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
name|Optional
argument_list|<
name|String
argument_list|>
name|title
init|=
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|TITLE
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|title
operator|.
name|isPresent
argument_list|()
operator|||
name|title
operator|.
name|get
argument_list|()
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
operator|.
name|get
argument_list|()
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
comment|// quality check
if|if
condition|(
name|checkValidity
argument_list|(
name|entry
argument_list|,
name|items
argument_list|)
condition|)
block|{
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
name|debug
argument_list|(
literal|"DOI "
operator|+
name|dataDOI
operator|+
literal|" for "
operator|+
name|title
operator|.
name|get
argument_list|()
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
name|entry
operator|.
name|getField
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
if|if
condition|(
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
block|}
argument_list|)
expr_stmt|;
comment|// year
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|YEAR
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|year
lambda|->
block|{
if|if
condition|(
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
block|}
argument_list|)
expr_stmt|;
return|return
name|enhancedQuery
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|checkValidity (BibEntry entry, JSONArray result)
specifier|private
specifier|static
name|boolean
name|checkValidity
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|JSONArray
name|result
parameter_list|)
block|{
comment|// TODO: use latex-free version instead in the future
specifier|final
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
name|map
argument_list|(
name|CrossRef
operator|::
name|removeLaTeX
argument_list|)
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
decl_stmt|;
comment|// currently only title-based
comment|// title: [ "How the Mind Hurts and Heals the Body." ]
comment|// subtitle: [ "" ]
try|try
block|{
comment|// title
name|JSONObject
name|data
init|=
name|result
operator|.
name|getJSONObject
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|String
name|dataTitle
init|=
name|data
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
if|if
condition|(
name|editDistanceIgnoreCase
argument_list|(
name|entryTitle
argument_list|,
name|dataTitle
argument_list|)
operator|<=
name|METRIC_THRESHOLD
condition|)
block|{
return|return
literal|true
return|;
block|}
comment|// subtitle
comment|// additional check, as sometimes subtitle is needed but sometimes only duplicates the title
if|if
condition|(
name|data
operator|.
name|getJSONArray
argument_list|(
literal|"subtitle"
argument_list|)
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|String
name|dataWithSubTitle
init|=
name|dataTitle
operator|+
literal|" "
operator|+
name|data
operator|.
name|getJSONArray
argument_list|(
literal|"subtitle"
argument_list|)
operator|.
name|getString
argument_list|(
literal|0
argument_list|)
decl_stmt|;
return|return
name|editDistanceIgnoreCase
argument_list|(
name|entryTitle
argument_list|,
name|dataWithSubTitle
argument_list|)
operator|<=
name|METRIC_THRESHOLD
return|;
block|}
return|return
literal|false
return|;
block|}
catch|catch
parameter_list|(
name|JSONException
name|ex
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
block|}
DECL|method|removeLaTeX (String text)
specifier|private
specifier|static
name|String
name|removeLaTeX
parameter_list|(
name|String
name|text
parameter_list|)
block|{
name|String
name|result
decl_stmt|;
comment|// remove braces
name|result
operator|=
operator|new
name|RemoveBracesFormatter
argument_list|()
operator|.
name|format
argument_list|(
name|text
argument_list|)
expr_stmt|;
comment|// convert to unicode
name|result
operator|=
operator|new
name|LatexToUnicodeFormatter
argument_list|()
operator|.
name|format
argument_list|(
name|result
argument_list|)
expr_stmt|;
return|return
name|result
return|;
block|}
DECL|method|editDistanceIgnoreCase (String a, String b)
specifier|private
specifier|static
name|double
name|editDistanceIgnoreCase
parameter_list|(
name|String
name|a
parameter_list|,
name|String
name|b
parameter_list|)
block|{
comment|// TODO: locale is dependent on the language of the strings?!
return|return
name|METRIC_DISTANCE
operator|.
name|distance
argument_list|(
name|a
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|ENGLISH
argument_list|)
argument_list|,
name|b
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|ENGLISH
argument_list|)
argument_list|)
return|;
block|}
block|}
end_class

end_unit

