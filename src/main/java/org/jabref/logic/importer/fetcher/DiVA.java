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
name|IdBasedParserFetcher
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

begin_comment
comment|/*  * http://www.diva-portal.org/smash/aboutdiva.jsf?dswid=-3222  * DiVA portal contains research publications and student theses from 40 Swedish universities and research institutions.  */
end_comment

begin_class
DECL|class|DiVA
specifier|public
class|class
name|DiVA
implements|implements
name|IdBasedParserFetcher
block|{
DECL|field|importFormatPreferences
specifier|private
specifier|final
name|ImportFormatPreferences
name|importFormatPreferences
decl_stmt|;
DECL|method|DiVA (ImportFormatPreferences importFormatPreferences)
specifier|public
name|DiVA
parameter_list|(
name|ImportFormatPreferences
name|importFormatPreferences
parameter_list|)
block|{
name|this
operator|.
name|importFormatPreferences
operator|=
name|importFormatPreferences
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
literal|"DiVA"
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
name|FETCHER_DIVA
argument_list|)
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
name|URIBuilder
name|uriBuilder
init|=
operator|new
name|URIBuilder
argument_list|(
literal|"http://www.diva-portal.org/smash/getreferences"
argument_list|)
decl_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"referenceFormat"
argument_list|,
literal|"BibTex"
argument_list|)
expr_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"pids"
argument_list|,
name|identifier
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
name|BibtexParser
argument_list|(
name|importFormatPreferences
argument_list|,
operator|new
name|DummyFileUpdateMonitor
argument_list|()
argument_list|)
return|;
block|}
DECL|method|isValidId (String identifier)
specifier|public
name|boolean
name|isValidId
parameter_list|(
name|String
name|identifier
parameter_list|)
block|{
return|return
name|identifier
operator|.
name|startsWith
argument_list|(
literal|"diva2:"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

