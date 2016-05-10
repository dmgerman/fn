begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.fulltext
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|fulltext
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
name|net
operator|.
name|URLEncoder
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|charset
operator|.
name|StandardCharsets
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
name|select
operator|.
name|Elements
import|;
end_import

begin_comment
comment|/**  * FullTextFinder implementation that attempts to find a PDF URL at GoogleScholar.  */
end_comment

begin_class
DECL|class|GoogleScholar
specifier|public
class|class
name|GoogleScholar
implements|implements
name|FullTextFinder
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
name|GoogleScholar
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|SEARCH_URL
specifier|private
specifier|static
specifier|final
name|String
name|SEARCH_URL
init|=
literal|"https://scholar.google.com//scholar?as_q=&as_epq=%s&as_occt=title"
decl_stmt|;
DECL|field|NUM_RESULTS
specifier|private
specifier|static
specifier|final
name|int
name|NUM_RESULTS
init|=
literal|10
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
comment|// Search in title
if|if
condition|(
operator|!
name|entry
operator|.
name|hasField
argument_list|(
literal|"title"
argument_list|)
condition|)
block|{
return|return
name|pdfLink
return|;
block|}
name|String
name|url
init|=
name|String
operator|.
name|format
argument_list|(
name|SEARCH_URL
argument_list|,
name|URLEncoder
operator|.
name|encode
argument_list|(
name|entry
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
argument_list|,
name|StandardCharsets
operator|.
name|UTF_8
operator|.
name|name
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|Document
name|doc
init|=
name|Jsoup
operator|.
name|connect
argument_list|(
name|url
argument_list|)
operator|.
name|userAgent
argument_list|(
literal|"Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0"
argument_list|)
comment|// don't identify as a crawler
operator|.
name|get
argument_list|()
decl_stmt|;
comment|// Check results for PDF link
comment|// TODO: link always on first result or none?
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|NUM_RESULTS
condition|;
name|i
operator|++
control|)
block|{
name|Elements
name|link
init|=
name|doc
operator|.
name|select
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"#gs_ggsW%s a"
argument_list|,
name|i
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|link
operator|.
name|first
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|String
name|s
init|=
name|link
operator|.
name|first
argument_list|()
operator|.
name|attr
argument_list|(
literal|"href"
argument_list|)
decl_stmt|;
comment|// link present?
if|if
condition|(
operator|!
literal|""
operator|.
name|equals
argument_list|(
name|s
argument_list|)
condition|)
block|{
comment|// TODO: check title inside pdf + length?
comment|// TODO: report error function needed?! query -> result
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Fulltext PDF found @ Google: "
operator|+
name|s
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
name|s
argument_list|)
argument_list|)
expr_stmt|;
break|break;
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

