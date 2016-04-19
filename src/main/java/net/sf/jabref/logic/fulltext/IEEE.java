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
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Matcher
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
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
name|net
operator|.
name|URLDownload
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

begin_comment
comment|/**  * Class for finding PDF URLs for entries on IEEE  * Will first look for URLs of the type http://ieeexplore.ieee.org/stamp/stamp.jsp?[tp=&]arnumber=...  * If not found, will resolve the DOI, if it starts with 10.1109, and try to find a similar link on the HTML page  */
end_comment

begin_class
DECL|class|IEEE
specifier|public
class|class
name|IEEE
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
name|IEEE
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|STAMP_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|STAMP_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"(/stamp/stamp.jsp\\?t?p?=?&?arnumber=[0-9]+)"
argument_list|)
decl_stmt|;
DECL|field|PDF_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|PDF_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\"(http://ieeexplore.ieee.org/ielx[0-9/]+\\.pdf[^\"]+)\""
argument_list|)
decl_stmt|;
DECL|field|IEEE_DOI
specifier|private
specifier|static
specifier|final
name|String
name|IEEE_DOI
init|=
literal|"10.1109"
decl_stmt|;
DECL|field|BASE_URL
specifier|private
specifier|static
specifier|final
name|String
name|BASE_URL
init|=
literal|"http://ieeexplore.ieee.org"
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
name|String
name|stampString
init|=
literal|""
decl_stmt|;
comment|// Try URL first -- will primarily work for entries from the old IEEE search
if|if
condition|(
name|entry
operator|.
name|hasField
argument_list|(
literal|"url"
argument_list|)
condition|)
block|{
comment|// Is the URL a direct link to IEEE?
name|Matcher
name|matcher
init|=
name|STAMP_PATTERN
operator|.
name|matcher
argument_list|(
name|entry
operator|.
name|getField
argument_list|(
literal|"url"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|matcher
operator|.
name|find
argument_list|()
condition|)
block|{
comment|// Found it
name|stampString
operator|=
name|matcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
block|}
comment|// If not, try DOI
if|if
condition|(
name|stampString
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Optional
argument_list|<
name|DOI
argument_list|>
name|doi
init|=
name|DOI
operator|.
name|build
argument_list|(
name|entry
operator|.
name|getField
argument_list|(
literal|"doi"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|doi
operator|.
name|isPresent
argument_list|()
operator|&&
name|doi
operator|.
name|get
argument_list|()
operator|.
name|getDOI
argument_list|()
operator|.
name|startsWith
argument_list|(
name|IEEE_DOI
argument_list|)
operator|&&
name|doi
operator|.
name|get
argument_list|()
operator|.
name|getURI
argument_list|()
operator|.
name|isPresent
argument_list|()
condition|)
block|{
comment|// Download the HTML page from IEEE
name|String
name|resolvedDOIPage
init|=
operator|new
name|URLDownload
argument_list|(
name|doi
operator|.
name|get
argument_list|()
operator|.
name|getURI
argument_list|()
operator|.
name|get
argument_list|()
operator|.
name|toURL
argument_list|()
argument_list|)
operator|.
name|downloadToString
argument_list|(
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
decl_stmt|;
comment|// Try to find the link
name|Matcher
name|matcher
init|=
name|STAMP_PATTERN
operator|.
name|matcher
argument_list|(
name|resolvedDOIPage
argument_list|)
decl_stmt|;
if|if
condition|(
name|matcher
operator|.
name|find
argument_list|()
condition|)
block|{
comment|// Found it
name|stampString
operator|=
name|matcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|// Any success?
if|if
condition|(
name|stampString
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
comment|// Download the HTML page containing a frame with the PDF
name|String
name|framePage
init|=
operator|new
name|URLDownload
argument_list|(
operator|new
name|URL
argument_list|(
name|BASE_URL
operator|+
name|stampString
argument_list|)
argument_list|)
operator|.
name|downloadToString
argument_list|(
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
decl_stmt|;
comment|// Try to find the direct PDF link
name|Matcher
name|matcher
init|=
name|PDF_PATTERN
operator|.
name|matcher
argument_list|(
name|framePage
argument_list|)
decl_stmt|;
if|if
condition|(
name|matcher
operator|.
name|find
argument_list|()
condition|)
block|{
comment|// The PDF was found
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Full text document found on IEEE Xplore"
argument_list|)
expr_stmt|;
return|return
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|URL
argument_list|(
name|matcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
argument_list|)
return|;
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

