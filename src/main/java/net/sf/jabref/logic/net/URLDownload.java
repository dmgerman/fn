begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

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
name|BufferedOutputStream
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
name|FileOutputStream
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
name|OutputStream
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
name|MalformedURLException
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
name|Map
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
name|Globals
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
DECL|field|source
specifier|private
specifier|final
name|URL
name|source
decl_stmt|;
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
literal|"JabRef"
argument_list|)
expr_stmt|;
name|URLDownload
operator|.
name|setCookieHandler
argument_list|()
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
DECL|method|setCookieHandler ()
specifier|private
specifier|static
name|void
name|setCookieHandler
parameter_list|()
block|{
try|try
block|{
comment|// This should set up JabRef to receive cookies properly
if|if
condition|(
name|CookieHandler
operator|.
name|getDefault
argument_list|()
operator|==
literal|null
condition|)
block|{
name|CookieHandler
operator|.
name|setDefault
argument_list|(
operator|new
name|CookieHandlerImpl
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|SecurityException
name|ignored
parameter_list|)
block|{
comment|// Setting or getting the system default cookie handler is forbidden
comment|// In this case cookie handling is not possible.
block|}
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
comment|/**      * Encoding will be determined from JabRefPreferences.DEFAULT_ENCODING      *      * @return the downloaded string      * @throws IOException      */
DECL|method|downloadToString ()
specifier|public
name|String
name|downloadToString
parameter_list|()
throws|throws
name|IOException
block|{
return|return
name|downloadToString
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getDefaultEncoding
argument_list|()
argument_list|)
return|;
block|}
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
name|OutputStream
name|output
operator|=
operator|new
name|BufferedOutputStream
argument_list|(
operator|new
name|FileOutputStream
argument_list|(
name|destination
argument_list|)
argument_list|)
init|)
block|{
name|copy
argument_list|(
name|input
argument_list|,
name|output
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
DECL|method|copy (InputStream in, OutputStream out)
specifier|private
name|void
name|copy
parameter_list|(
name|InputStream
name|in
parameter_list|,
name|OutputStream
name|out
parameter_list|)
throws|throws
name|IOException
block|{
try|try
init|(
name|InputStream
name|monitorInputStream
init|=
name|monitorInputStream
argument_list|(
name|in
argument_list|)
init|)
block|{
name|byte
index|[]
name|buffer
init|=
operator|new
name|byte
index|[
literal|512
index|]
decl_stmt|;
while|while
condition|(
literal|true
condition|)
block|{
name|int
name|bytesRead
init|=
name|monitorInputStream
operator|.
name|read
argument_list|(
name|buffer
argument_list|)
decl_stmt|;
if|if
condition|(
name|bytesRead
operator|==
operator|-
literal|1
condition|)
block|{
break|break;
block|}
name|out
operator|.
name|write
argument_list|(
name|buffer
argument_list|,
literal|0
argument_list|,
name|bytesRead
argument_list|)
expr_stmt|;
block|}
block|}
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
block|}
end_class

end_unit

