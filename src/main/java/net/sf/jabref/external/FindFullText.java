begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.external
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|external
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
name|io
operator|.
name|MimeTypeDetector
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
name|logic
operator|.
name|fetcher
operator|.
name|*
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

begin_comment
comment|/**  * Utility class for trying to resolve URLs to full-text PDF for articles.  */
end_comment

begin_class
DECL|class|FindFullText
specifier|public
class|class
name|FindFullText
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
name|FindFullText
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|finders
specifier|private
specifier|final
name|List
argument_list|<
name|FullTextFinder
argument_list|>
name|finders
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|method|FindFullText ()
specifier|public
name|FindFullText
parameter_list|()
block|{
comment|// Ordering is important, authorities first!
comment|// Publisher
name|finders
operator|.
name|add
argument_list|(
operator|new
name|DoiResolution
argument_list|()
argument_list|)
expr_stmt|;
name|finders
operator|.
name|add
argument_list|(
operator|new
name|ScienceDirect
argument_list|()
argument_list|)
expr_stmt|;
name|finders
operator|.
name|add
argument_list|(
operator|new
name|SpringerLink
argument_list|()
argument_list|)
expr_stmt|;
name|finders
operator|.
name|add
argument_list|(
operator|new
name|ACS
argument_list|()
argument_list|)
expr_stmt|;
name|finders
operator|.
name|add
argument_list|(
operator|new
name|ArXiv
argument_list|()
argument_list|)
expr_stmt|;
comment|// Meta search
name|finders
operator|.
name|add
argument_list|(
operator|new
name|GoogleScholar
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|FindFullText (List<FullTextFinder> fetcher)
specifier|public
name|FindFullText
parameter_list|(
name|List
argument_list|<
name|FullTextFinder
argument_list|>
name|fetcher
parameter_list|)
block|{
name|finders
operator|.
name|addAll
argument_list|(
name|fetcher
argument_list|)
expr_stmt|;
block|}
DECL|method|findFullTextPDF (BibEntry entry)
specifier|public
name|Optional
argument_list|<
name|URL
argument_list|>
name|findFullTextPDF
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
for|for
control|(
name|FullTextFinder
name|finder
range|:
name|finders
control|)
block|{
try|try
block|{
name|Optional
argument_list|<
name|URL
argument_list|>
name|result
init|=
name|finder
operator|.
name|findFullText
argument_list|(
name|entry
argument_list|)
decl_stmt|;
if|if
condition|(
name|result
operator|.
name|isPresent
argument_list|()
condition|)
block|{
if|if
condition|(
name|MimeTypeDetector
operator|.
name|isPdfContentType
argument_list|(
name|result
operator|.
name|get
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
condition|)
block|{
return|return
name|result
return|;
block|}
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
name|debug
argument_list|(
literal|"Failed to find fulltext PDF at given URL"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
block|}
end_class

end_unit

