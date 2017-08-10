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
name|org
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
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|identifier
operator|.
name|DOI
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
name|jsoup
operator|.
name|Connection
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jsoup
operator|.
name|Jsoup
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jsoup
operator|.
name|nodes
operator|.
name|Document
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jsoup
operator|.
name|nodes
operator|.
name|Element
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jsoup
operator|.
name|select
operator|.
name|Elements
import|;
end_import

begin_comment
comment|/**  * FulltextFetcher implementation that follows the DOI resolution redirects and scans for a full-text PDF URL.  */
end_comment

begin_class
DECL|class|DoiResolution
specifier|public
class|class
name|DoiResolution
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
name|DoiResolution
operator|.
name|class
argument_list|)
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
name|parse
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
name|String
name|sciLink
init|=
name|doi
operator|.
name|get
argument_list|()
operator|.
name|getURIAsASCIIString
argument_list|()
decl_stmt|;
comment|// follow all redirects and scan for a single pdf link
if|if
condition|(
operator|!
name|sciLink
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
try|try
block|{
name|Connection
name|connection
init|=
name|Jsoup
operator|.
name|connect
argument_list|(
name|sciLink
argument_list|)
decl_stmt|;
comment|// pretend to be a browser (agent& referrer)
name|connection
operator|.
name|userAgent
argument_list|(
name|URLDownload
operator|.
name|USER_AGENT
argument_list|)
expr_stmt|;
name|connection
operator|.
name|referrer
argument_list|(
literal|"http://www.google.com"
argument_list|)
expr_stmt|;
name|connection
operator|.
name|followRedirects
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|connection
operator|.
name|ignoreHttpErrors
argument_list|(
literal|true
argument_list|)
expr_stmt|;
comment|// some publishers are quite slow (default is 3s)
name|connection
operator|.
name|timeout
argument_list|(
literal|5000
argument_list|)
expr_stmt|;
name|Document
name|html
init|=
name|connection
operator|.
name|get
argument_list|()
decl_stmt|;
comment|// scan for PDF
name|Elements
name|elements
init|=
name|html
operator|.
name|body
argument_list|()
operator|.
name|select
argument_list|(
literal|"a[href]"
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|Optional
argument_list|<
name|URL
argument_list|>
argument_list|>
name|links
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|Element
name|element
range|:
name|elements
control|)
block|{
name|String
name|href
init|=
name|element
operator|.
name|attr
argument_list|(
literal|"abs:href"
argument_list|)
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|ENGLISH
argument_list|)
decl_stmt|;
name|String
name|hrefText
init|=
name|element
operator|.
name|text
argument_list|()
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|ENGLISH
argument_list|)
decl_stmt|;
comment|// Only check if pdf is included in the link or inside the text
comment|// ACM uses tokens without PDF inside the link
comment|// See https://github.com/lehner/LocalCopy for more scrape ideas
if|if
condition|(
operator|(
name|href
operator|.
name|contains
argument_list|(
literal|"pdf"
argument_list|)
operator|||
name|hrefText
operator|.
name|contains
argument_list|(
literal|"pdf"
argument_list|)
operator|)
operator|&&
operator|new
name|URLDownload
argument_list|(
name|href
argument_list|)
operator|.
name|isPdf
argument_list|()
condition|)
block|{
name|links
operator|.
name|add
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|URL
argument_list|(
name|href
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|// return if only one link was found (high accuracy)
if|if
condition|(
name|links
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Fulltext PDF found @ "
operator|+
name|sciLink
argument_list|)
expr_stmt|;
name|pdfLink
operator|=
name|links
operator|.
name|get
argument_list|(
literal|0
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
name|warn
argument_list|(
literal|"DoiResolution fetcher failed: "
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|pdfLink
return|;
block|}
block|}
end_class

end_unit

