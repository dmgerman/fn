begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.net
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|net
package|;
end_package

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
name|javax
operator|.
name|swing
operator|.
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|*
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

begin_comment
comment|/**  * Each call to a public method creates a new HTTP connection. Nothing is cached.  *  * @author Erik Putrycz erik.putrycz-at-nrc-cnrc.gc.ca  * @author Simon Harrer  */
end_comment

begin_class
DECL|class|URLDownload
specifier|public
class|class
name|URLDownload
block|{
DECL|method|buildMonitoredDownload (final Component component, URL source)
specifier|public
specifier|static
name|URLDownload
name|buildMonitoredDownload
parameter_list|(
specifier|final
name|Component
name|component
parameter_list|,
name|URL
name|source
parameter_list|)
block|{
return|return
operator|new
name|URLDownload
argument_list|(
name|source
argument_list|)
block|{
annotation|@
name|Override
specifier|protected
name|InputStream
name|monitorInputStream
parameter_list|(
name|InputStream
name|in
parameter_list|)
block|{
return|return
operator|new
name|ProgressMonitorInputStream
argument_list|(
name|component
argument_list|,
literal|"Downloading "
operator|+
name|this
operator|.
name|getSource
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|,
name|in
argument_list|)
return|;
block|}
block|}
return|;
block|}
DECL|field|source
specifier|private
specifier|final
name|URL
name|source
decl_stmt|;
comment|/**      * URL download to a string.      *<p/>      * Example      * URLDownload dl = new URLDownload(URL);      * String content = dl.downloadToString(ENCODING);      * dl.downloadToFile(FILE); // available in FILE      * String contentType = dl.determineMimeType();      *      * @param source The URL to download.      */
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
name|URLDownload
operator|.
name|setCookieHandler
argument_list|()
expr_stmt|;
block|}
DECL|method|getSource ()
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
block|{              }
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
name|connection
operator|.
name|setRequestProperty
argument_list|(
literal|"User-Agent"
argument_list|,
literal|"JabRef"
argument_list|)
expr_stmt|;
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
comment|/**      * Encoding will be determined from "defaultEncoding"      *      * @return the downloaded string      * @throws IOException      */
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
name|get
argument_list|(
literal|"defaultEncoding"
argument_list|)
argument_list|)
return|;
block|}
DECL|method|downloadToString (String encoding)
specifier|public
name|String
name|downloadToString
parameter_list|(
name|String
name|encoding
parameter_list|)
throws|throws
name|IOException
block|{
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
decl_stmt|;
name|Writer
name|output
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
try|try
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
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
finally|finally
block|{
try|try
block|{
name|input
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ignored
parameter_list|)
block|{             }
try|try
block|{
name|output
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ignored
parameter_list|)
block|{             }
block|}
return|return
name|output
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|copy (InputStream in, Writer out, String encoding)
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
name|String
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
name|BufferedReader
name|read
init|=
operator|new
name|BufferedReader
argument_list|(
name|r
argument_list|)
decl_stmt|;
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
decl_stmt|;
name|OutputStream
name|output
init|=
operator|new
name|BufferedOutputStream
argument_list|(
operator|new
name|FileOutputStream
argument_list|(
name|destination
argument_list|)
argument_list|)
decl_stmt|;
try|try
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
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
finally|finally
block|{
try|try
block|{
name|input
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ignored
parameter_list|)
block|{             }
try|try
block|{
name|output
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ignored
parameter_list|)
block|{             }
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
name|InputStream
name|monitorInputStream
init|=
name|monitorInputStream
argument_list|(
name|in
argument_list|)
decl_stmt|;
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

