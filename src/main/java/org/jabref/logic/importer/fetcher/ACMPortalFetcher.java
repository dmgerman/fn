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
name|logic
operator|.
name|help
operator|.
name|HelpFile
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
name|model
operator|.
name|util
operator|.
name|DummyFileUpdateMonitor
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
DECL|class|ACMPortalFetcher
specifier|public
class|class
name|ACMPortalFetcher
implements|implements
name|SearchBasedParserFetcher
block|{
DECL|field|SEARCH_URL
specifier|private
specifier|static
specifier|final
name|String
name|SEARCH_URL
init|=
literal|"https://dl.acm.org/exportformats_search.cfm"
decl_stmt|;
DECL|field|preferences
specifier|private
specifier|final
name|ImportFormatPreferences
name|preferences
decl_stmt|;
DECL|method|ACMPortalFetcher (ImportFormatPreferences preferences)
specifier|public
name|ACMPortalFetcher
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
literal|"ACM Portal"
return|;
block|}
annotation|@
name|Override
DECL|method|getHelpPage ()
specifier|public
name|Optional
argument_list|<
name|HelpFile
argument_list|>
name|getHelpPage
parameter_list|()
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|HelpFile
operator|.
name|FETCHER_ACM
argument_list|)
return|;
block|}
DECL|method|createQueryString (String query)
specifier|private
specifier|static
name|String
name|createQueryString
parameter_list|(
name|String
name|query
parameter_list|)
block|{
comment|// Query syntax to search for an entry that matches "one" and "two" in any field is: (+one +two)
return|return
literal|"(%252B"
operator|+
name|query
operator|.
name|trim
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|"\\s+"
argument_list|,
literal|"%20%252B"
argument_list|)
operator|+
literal|")"
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
operator|new
name|URIBuilder
argument_list|(
name|SEARCH_URL
argument_list|)
decl_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"query"
argument_list|,
name|createQueryString
argument_list|(
name|query
argument_list|)
argument_list|)
expr_stmt|;
comment|// Search all fields
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"within"
argument_list|,
literal|"owners.owner=GUIDE"
argument_list|)
expr_stmt|;
comment|// Search within the ACM Guide to Computing Literature (encompasses the ACM Full-Text Collection)
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"expformat"
argument_list|,
literal|"bibtex"
argument_list|)
expr_stmt|;
comment|// BibTeX format
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
argument_list|,
operator|new
name|DummyFileUpdateMonitor
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

