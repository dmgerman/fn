begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.net
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|net
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|BufferedInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|BufferedReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|DataOutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

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
name|io
operator|.
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStreamReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Reader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|StringWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Writer
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|CookieHandler
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|CookieManager
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|CookiePolicy
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|HttpCookie
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|HttpURLConnection
import|;
end_import

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
name|net
operator|.
name|URLConnection
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
name|Charset
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Files
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Path
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|StandardCopyOption
import|;
end_import

begin_import
import|import
name|java
operator|.
name|security
operator|.
name|SecureRandom
import|;
end_import

begin_import
import|import
name|java
operator|.
name|security
operator|.
name|cert
operator|.
name|X509Certificate
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
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
name|Map
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|net
operator|.
name|ssl
operator|.
name|HttpsURLConnection
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|net
operator|.
name|ssl
operator|.
name|SSLContext
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|net
operator|.
name|ssl
operator|.
name|TrustManager
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|net
operator|.
name|ssl
operator|.
name|X509TrustManager
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
name|io
operator|.
name|FileUtil
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
comment|/**  * URL download to a string.  *<p>  * Example:  * URLDownload dl = new URLDownload(URL);  * String content = dl.downloadToString(ENCODING);  * dl.downloadToFile(FILE); // available in FILE  * String contentType = dl.determineMimeType();  *  * Each call to a public method creates a new HTTP connection. Nothing is cached.  *  * @author Erik Putrycz erik.putrycz-at-nrc-cnrc.gc.ca  * @author Simon Harrer  */
end_comment

begin_class
DECL|class|URLDownload
specifier|public
class|class
name|URLDownload
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
name|URLDownload
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|USER_AGENT
specifier|private
specifier|static
specifier|final
name|String
name|USER_AGENT
init|=
literal|"JabRef"
decl_stmt|;
DECL|field|source
specifier|private
specifier|final
name|URL
name|source
decl_stmt|;
DECL|field|parameters
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|parameters
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|postData
specifier|private
name|String
name|postData
init|=
literal|""
decl_stmt|;
DECL|method|createURLDownloadWithBrowserUserAgent (String address)
specifier|public
specifier|static
name|URLDownload
name|createURLDownloadWithBrowserUserAgent
parameter_list|(
name|String
name|address
parameter_list|)
throws|throws
name|MalformedURLException
block|{
name|URLDownload
name|downloader
init|=
operator|new
name|URLDownload
argument_list|(
name|address
argument_list|)
decl_stmt|;
name|downloader
operator|.
name|addParameters
argument_list|(
literal|"User-Agent"
argument_list|,
literal|"Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0"
argument_list|)
expr_stmt|;
return|return
name|downloader
return|;
block|}
comment|/**      * @param address the URL to download from      * @throws MalformedURLException if no protocol is specified in the address, or an unknown protocol is found      */
DECL|method|URLDownload (String address)
specifier|public
name|URLDownload
parameter_list|(
name|String
name|address
parameter_list|)
throws|throws
name|MalformedURLException
block|{
name|this
argument_list|(
operator|new
name|URL
argument_list|(
name|address
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param source The URL to download.      */
DECL|method|URLDownload (URL source)
specifier|public
name|URLDownload
parameter_list|(
name|URL
name|source
parameter_list|)
block|{
name|this
operator|.
name|source
operator|=
name|source
expr_stmt|;
name|addParameters
argument_list|(
literal|"User-Agent"
argument_list|,
name|USER_AGENT
argument_list|)
expr_stmt|;
block|}
DECL|method|getSource ()
specifier|public
name|URL
name|getSource
parameter_list|()
block|{
return|return
name|source
return|;
block|}
DECL|method|determineMimeType ()
specifier|public
name|String
name|determineMimeType
parameter_list|()
throws|throws
name|IOException
block|{
comment|// this does not cause a real performance issue as the underlying HTTP/TCP connection is reused
name|URLConnection
name|urlConnection
init|=
name|openConnection
argument_list|()
decl_stmt|;
try|try
block|{
return|return
name|urlConnection
operator|.
name|getContentType
argument_list|()
return|;
block|}
finally|finally
block|{
try|try
block|{
name|urlConnection
operator|.
name|getInputStream
argument_list|()
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ignored
parameter_list|)
block|{
comment|// Ignored
block|}
block|}
block|}
DECL|method|addParameters (String key, String value)
specifier|public
name|void
name|addParameters
parameter_list|(
name|String
name|key
parameter_list|,
name|String
name|value
parameter_list|)
block|{
name|parameters
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
DECL|method|setPostData (String postData)
specifier|public
name|void
name|setPostData
parameter_list|(
name|String
name|postData
parameter_list|)
block|{
if|if
condition|(
name|postData
operator|!=
literal|null
condition|)
block|{
name|this
operator|.
name|postData
operator|=
name|postData
expr_stmt|;
block|}
block|}
DECL|method|openConnection ()
specifier|private
name|URLConnection
name|openConnection
parameter_list|()
throws|throws
name|IOException
block|{
name|URLConnection
name|connection
init|=
name|source
operator|.
name|openConnection
argument_list|()
decl_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|entry
range|:
name|parameters
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|connection
operator|.
name|setRequestProperty
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|,
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|postData
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|connection
operator|.
name|setDoOutput
argument_list|(
literal|true
argument_list|)
expr_stmt|;
try|try
init|(
name|DataOutputStream
name|wr
init|=
operator|new
name|DataOutputStream
argument_list|(
name|connection
operator|.
name|getOutputStream
argument_list|()
argument_list|)
init|)
block|{
name|wr
operator|.
name|writeBytes
argument_list|(
name|postData
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|connection
operator|instanceof
name|HttpURLConnection
condition|)
block|{
comment|// normally, 3xx is redirect
name|int
name|status
init|=
operator|(
operator|(
name|HttpURLConnection
operator|)
name|connection
operator|)
operator|.
name|getResponseCode
argument_list|()
decl_stmt|;
if|if
condition|(
name|status
operator|!=
name|HttpURLConnection
operator|.
name|HTTP_OK
condition|)
block|{
if|if
condition|(
operator|(
name|status
operator|==
name|HttpURLConnection
operator|.
name|HTTP_MOVED_TEMP
operator|)
operator|||
operator|(
name|status
operator|==
name|HttpURLConnection
operator|.
name|HTTP_MOVED_PERM
operator|)
operator|||
operator|(
name|status
operator|==
name|HttpURLConnection
operator|.
name|HTTP_SEE_OTHER
operator|)
condition|)
block|{
comment|// get redirect url from "location" header field
name|String
name|newUrl
init|=
name|connection
operator|.
name|getHeaderField
argument_list|(
literal|"Location"
argument_list|)
decl_stmt|;
comment|// open the new connnection again
name|connection
operator|=
operator|new
name|URLDownload
argument_list|(
name|newUrl
argument_list|)
operator|.
name|openConnection
argument_list|()
expr_stmt|;
block|}
block|}
block|}
comment|// this does network i/o: GET + read returned headers
name|connection
operator|.
name|connect
argument_list|()
expr_stmt|;
return|return
name|connection
return|;
block|}
comment|/**      *      * @return the downloaded string      * @throws IOException      */
DECL|method|downloadToString (Charset encoding)
specifier|public
name|String
name|downloadToString
parameter_list|(
name|Charset
name|encoding
parameter_list|)
throws|throws
name|IOException
block|{
try|try
init|(
name|InputStream
name|input
init|=
operator|new
name|BufferedInputStream
argument_list|(
name|openConnection
argument_list|()
operator|.
name|getInputStream
argument_list|()
argument_list|)
init|;
name|Writer
name|output
operator|=
operator|new
name|StringWriter
argument_list|()
init|)
block|{
name|copy
argument_list|(
name|input
argument_list|,
name|output
argument_list|,
name|encoding
argument_list|)
expr_stmt|;
return|return
name|output
operator|.
name|toString
argument_list|()
return|;
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
literal|"Could not copy input"
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
name|e
throw|;
block|}
block|}
DECL|method|getCookieFromUrl ()
specifier|public
name|List
argument_list|<
name|HttpCookie
argument_list|>
name|getCookieFromUrl
parameter_list|()
throws|throws
name|IOException
block|{
name|CookieManager
name|cookieManager
init|=
operator|new
name|CookieManager
argument_list|()
decl_stmt|;
name|CookieHandler
operator|.
name|setDefault
argument_list|(
name|cookieManager
argument_list|)
expr_stmt|;
name|cookieManager
operator|.
name|setCookiePolicy
argument_list|(
name|CookiePolicy
operator|.
name|ACCEPT_ALL
argument_list|)
expr_stmt|;
name|URLConnection
name|con
init|=
name|openConnection
argument_list|()
decl_stmt|;
name|con
operator|.
name|getHeaderFields
argument_list|()
expr_stmt|;
comment|// must be read to store the cookie
try|try
block|{
return|return
name|cookieManager
operator|.
name|getCookieStore
argument_list|()
operator|.
name|get
argument_list|(
name|source
operator|.
name|toURI
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|URISyntaxException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Unable to convert download URL to URI"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
block|}
DECL|method|copy (InputStream in, Writer out, Charset encoding)
specifier|private
name|void
name|copy
parameter_list|(
name|InputStream
name|in
parameter_list|,
name|Writer
name|out
parameter_list|,
name|Charset
name|encoding
parameter_list|)
throws|throws
name|IOException
block|{
name|InputStream
name|monitoredInputStream
init|=
name|monitorInputStream
argument_list|(
name|in
argument_list|)
decl_stmt|;
name|Reader
name|r
init|=
operator|new
name|InputStreamReader
argument_list|(
name|monitoredInputStream
argument_list|,
name|encoding
argument_list|)
decl_stmt|;
try|try
init|(
name|BufferedReader
name|read
init|=
operator|new
name|BufferedReader
argument_list|(
name|r
argument_list|)
init|)
block|{
name|String
name|line
decl_stmt|;
while|while
condition|(
operator|(
name|line
operator|=
name|read
operator|.
name|readLine
argument_list|()
operator|)
operator|!=
literal|null
condition|)
block|{
name|out
operator|.
name|write
argument_list|(
name|line
argument_list|)
expr_stmt|;
name|out
operator|.
name|write
argument_list|(
literal|"\n"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * @deprecated use {@link #downloadToFile(Path)}      */
annotation|@
name|Deprecated
DECL|method|downloadToFile (File destination)
specifier|public
name|void
name|downloadToFile
parameter_list|(
name|File
name|destination
parameter_list|)
throws|throws
name|IOException
block|{
name|downloadToFile
argument_list|(
name|destination
operator|.
name|toPath
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|downloadToFile (Path destination)
specifier|public
name|void
name|downloadToFile
parameter_list|(
name|Path
name|destination
parameter_list|)
throws|throws
name|IOException
block|{
try|try
init|(
name|InputStream
name|input
init|=
name|monitorInputStream
argument_list|(
operator|new
name|BufferedInputStream
argument_list|(
name|openConnection
argument_list|()
operator|.
name|getInputStream
argument_list|()
argument_list|)
argument_list|)
init|)
block|{
name|Files
operator|.
name|copy
argument_list|(
name|input
argument_list|,
name|destination
argument_list|,
name|StandardCopyOption
operator|.
name|REPLACE_EXISTING
argument_list|)
expr_stmt|;
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
literal|"Could not copy input"
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
name|e
throw|;
block|}
block|}
comment|/**      * Downloads the web resource to a temporary file.      *      * @return the path to the downloaded file.      */
DECL|method|downloadToTemporaryFile ()
specifier|public
name|Path
name|downloadToTemporaryFile
parameter_list|()
throws|throws
name|IOException
block|{
comment|// Determine file name and extension from source url
name|String
name|sourcePath
init|=
name|source
operator|.
name|getPath
argument_list|()
decl_stmt|;
comment|// Take everything after the last '/' as name + extension
name|String
name|fileNameWithExtension
init|=
name|sourcePath
operator|.
name|substring
argument_list|(
name|sourcePath
operator|.
name|lastIndexOf
argument_list|(
literal|'/'
argument_list|)
operator|+
literal|1
argument_list|)
decl_stmt|;
name|String
name|fileName
init|=
name|FileUtil
operator|.
name|getFileName
argument_list|(
name|fileNameWithExtension
argument_list|)
decl_stmt|;
name|String
name|extension
init|=
literal|"."
operator|+
name|FileUtil
operator|.
name|getFileExtension
argument_list|(
name|fileNameWithExtension
argument_list|)
operator|.
name|orElse
argument_list|(
literal|"tmp"
argument_list|)
decl_stmt|;
comment|// Create temporary file and download to it
name|Path
name|file
init|=
name|Files
operator|.
name|createTempFile
argument_list|(
name|fileName
argument_list|,
name|extension
argument_list|)
decl_stmt|;
name|downloadToFile
argument_list|(
name|file
argument_list|)
expr_stmt|;
return|return
name|file
return|;
block|}
DECL|method|monitorInputStream (InputStream in)
specifier|protected
name|InputStream
name|monitorInputStream
parameter_list|(
name|InputStream
name|in
parameter_list|)
block|{
return|return
name|in
return|;
block|}
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"URLDownload{"
operator|+
literal|"source="
operator|+
name|source
operator|+
literal|'}'
return|;
block|}
DECL|method|fixSSLVerification ()
specifier|public
name|void
name|fixSSLVerification
parameter_list|()
block|{
comment|// Create a trust manager that does not validate certificate chains
name|TrustManager
index|[]
name|trustAllCerts
init|=
operator|new
name|TrustManager
index|[]
block|{
operator|new
name|X509TrustManager
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|checkClientTrusted
parameter_list|(
name|java
operator|.
name|security
operator|.
name|cert
operator|.
name|X509Certificate
index|[]
name|chain
parameter_list|,
name|String
name|authType
parameter_list|)
throws|throws
name|java
operator|.
name|security
operator|.
name|cert
operator|.
name|CertificateException
block|{
comment|// TODO Auto-generated method stub
block|}
function|@Override             public void checkServerTrusted
parameter_list|(
name|java
operator|.
name|security
operator|.
name|cert
operator|.
name|X509Certificate
index|[]
name|chain
parameter_list|,
name|String
name|authType
parameter_list|)
throws|throws
name|java
operator|.
name|security
operator|.
name|cert
operator|.
name|CertificateException
block|{
comment|// TODO Auto-generated method stub
block|}
function|@Override             public java.security.cert.X509Certificate[] getAcceptedIssuers
parameter_list|()
block|{
comment|// TODO Auto-generated method stub
return|return
operator|new
name|X509Certificate
index|[
literal|0
index|]
return|;
block|}
function|}};
comment|// Install the all-trusting trust manager
try|try
block|{
name|SSLContext
name|sc
init|=
name|SSLContext
operator|.
name|getInstance
argument_list|(
literal|"TLS"
argument_list|)
decl_stmt|;
name|sc
operator|.
name|init
argument_list|(
literal|null
argument_list|,
name|trustAllCerts
argument_list|,
operator|new
name|SecureRandom
argument_list|()
argument_list|)
expr_stmt|;
name|HttpsURLConnection
operator|.
name|setDefaultSSLSocketFactory
argument_list|(
name|sc
operator|.
name|getSocketFactory
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"SSL problem"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

