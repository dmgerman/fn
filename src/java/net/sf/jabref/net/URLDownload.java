begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Created on 1-Dec-2004  *  */
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
name|java
operator|.
name|awt
operator|.
name|Component
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
name|net
operator|.
name|CookieHandler
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|ProgressMonitorInputStream
import|;
end_import

begin_comment
comment|/**  * @author Erik Putrycz erik.putrycz-at-nrc-cnrc.gc.ca  */
end_comment

begin_class
DECL|class|URLDownload
specifier|public
class|class
name|URLDownload
block|{
DECL|field|source
specifier|private
name|URL
name|source
decl_stmt|;
DECL|field|con
specifier|private
name|URLConnection
name|con
init|=
literal|null
decl_stmt|;
DECL|field|dest
specifier|private
name|File
name|dest
decl_stmt|;
DECL|field|parent
specifier|private
name|Component
name|parent
decl_stmt|;
DECL|field|mimeType
specifier|private
name|String
name|mimeType
init|=
literal|null
decl_stmt|;
DECL|field|cm
specifier|private
name|CookieHandler
name|cm
decl_stmt|;
DECL|method|URLDownload (Component _parent, URL _source, File _dest)
specifier|public
name|URLDownload
parameter_list|(
name|Component
name|_parent
parameter_list|,
name|URL
name|_source
parameter_list|,
name|File
name|_dest
parameter_list|)
block|{
name|source
operator|=
name|_source
expr_stmt|;
name|dest
operator|=
name|_dest
expr_stmt|;
name|parent
operator|=
name|_parent
expr_stmt|;
try|try
block|{
comment|// This should set up JabRef to receive cookies properly
if|if
condition|(
operator|(
name|cm
operator|=
name|CookieHandler
operator|.
name|getDefault
argument_list|()
operator|)
operator|==
literal|null
condition|)
block|{
name|cm
operator|=
operator|new
name|CookieHandlerImpl
argument_list|()
expr_stmt|;
name|CookieHandler
operator|.
name|setDefault
argument_list|(
name|cm
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|SecurityException
name|e
parameter_list|)
block|{
comment|// Setting or getting the system default cookie handler is forbidden
comment|// In this case cookie handling is not possible.
block|}
block|}
DECL|method|getMimeType ()
specifier|public
name|String
name|getMimeType
parameter_list|()
block|{
return|return
name|mimeType
return|;
block|}
DECL|method|openConnectionOnly ()
specifier|public
name|void
name|openConnectionOnly
parameter_list|()
throws|throws
name|IOException
block|{
name|con
operator|=
name|source
operator|.
name|openConnection
argument_list|()
expr_stmt|;
name|con
operator|.
name|setRequestProperty
argument_list|(
literal|"User-Agent"
argument_list|,
literal|"Jabref"
argument_list|)
expr_stmt|;
name|mimeType
operator|=
name|con
operator|.
name|getContentType
argument_list|()
expr_stmt|;
block|}
DECL|method|download ()
specifier|public
name|void
name|download
parameter_list|()
throws|throws
name|IOException
block|{
if|if
condition|(
name|con
operator|==
literal|null
condition|)
block|{
name|con
operator|=
name|source
operator|.
name|openConnection
argument_list|()
expr_stmt|;
name|con
operator|.
name|setRequestProperty
argument_list|(
literal|"User-Agent"
argument_list|,
literal|"Jabref"
argument_list|)
expr_stmt|;
name|mimeType
operator|=
name|con
operator|.
name|getContentType
argument_list|()
expr_stmt|;
block|}
name|InputStream
name|input
init|=
operator|new
name|BufferedInputStream
argument_list|(
name|con
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
name|dest
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
name|output
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{               }
block|}
block|}
DECL|method|copy (InputStream in, OutputStream out)
specifier|public
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
name|_in
init|=
operator|new
name|ProgressMonitorInputStream
argument_list|(
name|parent
argument_list|,
literal|"Downloading "
operator|+
name|source
operator|.
name|toString
argument_list|()
argument_list|,
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
name|_in
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
break|break;
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
end_class

end_unit

