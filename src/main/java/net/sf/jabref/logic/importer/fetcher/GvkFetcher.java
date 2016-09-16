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
name|Arrays
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
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
name|stream
operator|.
name|Collectors
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
name|GvkParser
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

begin_class
DECL|class|GvkFetcher
specifier|public
class|class
name|GvkFetcher
implements|implements
name|SearchBasedParserFetcher
block|{
DECL|field|URL_PATTERN
specifier|private
specifier|static
specifier|final
name|String
name|URL_PATTERN
init|=
literal|"http://sru.gbv.de/gvk?"
decl_stmt|;
comment|/**      * Searchkeys are used to specify a search request. For example "tit" stands for "title".      * If no searchkey is used, the default searchkey "all" is used.      */
DECL|field|searchKeys
specifier|private
specifier|final
name|Collection
argument_list|<
name|String
argument_list|>
name|searchKeys
init|=
name|Arrays
operator|.
name|asList
argument_list|(
literal|"all"
argument_list|,
literal|"tit"
argument_list|,
literal|"per"
argument_list|,
literal|"thm"
argument_list|,
literal|"slw"
argument_list|,
literal|"txt"
argument_list|,
literal|"num"
argument_list|,
literal|"kon"
argument_list|,
literal|"ppn"
argument_list|,
literal|"bkl"
argument_list|,
literal|"erj"
argument_list|)
decl_stmt|;
annotation|@
name|Override
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"GVK"
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
name|FETCHER_GVK
return|;
block|}
DECL|method|getSearchQueryStringForComplexQuery (List<String> queryList)
specifier|private
name|String
name|getSearchQueryStringForComplexQuery
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|queryList
parameter_list|)
block|{
name|String
name|query
init|=
literal|""
decl_stmt|;
name|boolean
name|lastWasNoKey
init|=
literal|false
decl_stmt|;
for|for
control|(
name|String
name|key
range|:
name|queryList
control|)
block|{
if|if
condition|(
name|searchKeys
operator|.
name|contains
argument_list|(
name|key
argument_list|)
condition|)
block|{
if|if
condition|(
name|lastWasNoKey
condition|)
block|{
name|query
operator|=
name|query
operator|+
literal|"and "
expr_stmt|;
block|}
name|query
operator|=
name|query
operator|+
literal|"pica."
operator|+
name|key
operator|+
literal|"="
expr_stmt|;
block|}
else|else
block|{
name|query
operator|=
name|query
operator|+
name|key
operator|+
literal|" "
expr_stmt|;
name|lastWasNoKey
operator|=
literal|true
expr_stmt|;
block|}
block|}
return|return
name|query
operator|.
name|trim
argument_list|()
return|;
block|}
DECL|method|getSearchQueryString (String query)
specifier|protected
name|String
name|getSearchQueryString
parameter_list|(
name|String
name|query
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|query
argument_list|)
expr_stmt|;
name|LinkedList
argument_list|<
name|String
argument_list|>
name|queryList
init|=
operator|new
name|LinkedList
argument_list|<>
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|query
operator|.
name|split
argument_list|(
literal|"\\s"
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|searchKeys
operator|.
name|contains
argument_list|(
name|queryList
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
condition|)
block|{
return|return
name|getSearchQueryStringForComplexQuery
argument_list|(
name|queryList
argument_list|)
return|;
block|}
else|else
block|{
comment|// query as pica.all
return|return
name|queryList
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
literal|" "
argument_list|,
literal|"pica.all="
argument_list|,
literal|""
argument_list|)
argument_list|)
return|;
block|}
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
name|String
name|gvkQuery
init|=
name|getSearchQueryString
argument_list|(
name|query
argument_list|)
decl_stmt|;
name|URIBuilder
name|uriBuilder
init|=
operator|new
name|URIBuilder
argument_list|(
name|URL_PATTERN
argument_list|)
decl_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"version"
argument_list|,
literal|"1.1"
argument_list|)
expr_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"operation"
argument_list|,
literal|"searchRetrieve"
argument_list|)
expr_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"query"
argument_list|,
name|gvkQuery
argument_list|)
expr_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"maximumRecords"
argument_list|,
literal|"50"
argument_list|)
expr_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"recordSchema"
argument_list|,
literal|"picaxml"
argument_list|)
expr_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"sortKeys"
argument_list|,
literal|"Year,,1"
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
name|GvkParser
argument_list|()
return|;
block|}
block|}
end_class

end_unit

