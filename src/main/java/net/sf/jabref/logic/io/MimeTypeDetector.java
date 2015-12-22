begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.io
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|io
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

begin_class
DECL|class|MimeTypeDetector
specifier|public
class|class
name|MimeTypeDetector
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
name|MimeTypeDetector
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|method|isPdfContentType (String url)
specifier|public
specifier|static
name|boolean
name|isPdfContentType
parameter_list|(
name|String
name|url
parameter_list|)
block|{
name|String
name|contentType
init|=
name|getMimeType
argument_list|(
name|url
argument_list|)
decl_stmt|;
if|if
condition|(
name|contentType
operator|==
literal|null
condition|)
block|{
return|return
literal|false
return|;
block|}
return|return
name|contentType
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"application/pdf"
argument_list|)
return|;
block|}
DECL|method|getMimeType (String url)
specifier|public
specifier|static
name|String
name|getMimeType
parameter_list|(
name|String
name|url
parameter_list|)
block|{
try|try
block|{
name|String
name|contentType
init|=
name|Unirest
operator|.
name|head
argument_list|(
name|url
argument_list|)
operator|.
name|asBinary
argument_list|()
operator|.
name|getHeaders
argument_list|()
operator|.
name|getFirst
argument_list|(
literal|"content-type"
argument_list|)
decl_stmt|;
comment|// HEAD and GET headers might differ, try real GET request
if|if
condition|(
name|contentType
operator|==
literal|null
condition|)
block|{
name|contentType
operator|=
name|Unirest
operator|.
name|get
argument_list|(
name|url
argument_list|)
operator|.
name|asBinary
argument_list|()
operator|.
name|getHeaders
argument_list|()
operator|.
name|getFirst
argument_list|(
literal|"content-type"
argument_list|)
expr_stmt|;
block|}
return|return
name|contentType
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
name|debug
argument_list|(
literal|"Error getting MIME type of URL"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
block|}
end_class

end_unit

