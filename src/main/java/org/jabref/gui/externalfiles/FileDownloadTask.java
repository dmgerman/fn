begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.externalfiles
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|externalfiles
package|;
end_package

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
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|util
operator|.
name|BackgroundTask
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
name|ProgressInputStream
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
name|fxmisc
operator|.
name|easybind
operator|.
name|EasyBind
import|;
end_import

begin_class
DECL|class|FileDownloadTask
specifier|public
class|class
name|FileDownloadTask
extends|extends
name|BackgroundTask
argument_list|<
name|Path
argument_list|>
block|{
DECL|field|source
specifier|private
specifier|final
name|URL
name|source
decl_stmt|;
DECL|field|destination
specifier|private
specifier|final
name|Path
name|destination
decl_stmt|;
DECL|method|FileDownloadTask (URL source, Path destination)
specifier|public
name|FileDownloadTask
parameter_list|(
name|URL
name|source
parameter_list|,
name|Path
name|destination
parameter_list|)
block|{
name|this
operator|.
name|source
operator|=
name|source
expr_stmt|;
name|this
operator|.
name|destination
operator|=
name|destination
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|call ()
specifier|protected
name|Path
name|call
parameter_list|()
throws|throws
name|Exception
block|{
name|URLDownload
name|download
init|=
operator|new
name|URLDownload
argument_list|(
name|source
argument_list|)
decl_stmt|;
try|try
init|(
name|ProgressInputStream
name|inputStream
init|=
name|download
operator|.
name|asInputStream
argument_list|()
init|)
block|{
name|EasyBind
operator|.
name|subscribe
argument_list|(
name|inputStream
operator|.
name|totalNumBytesReadProperty
argument_list|()
argument_list|,
name|bytesRead
lambda|->
name|updateProgress
argument_list|(
name|bytesRead
operator|.
name|longValue
argument_list|()
argument_list|,
name|inputStream
operator|.
name|getMaxNumBytes
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|// Make sure directory exists since otherwise copy fails
name|Files
operator|.
name|createDirectories
argument_list|(
name|destination
operator|.
name|getParent
argument_list|()
argument_list|)
expr_stmt|;
name|Files
operator|.
name|copy
argument_list|(
name|inputStream
argument_list|,
name|destination
argument_list|,
name|StandardCopyOption
operator|.
name|REPLACE_EXISTING
argument_list|)
expr_stmt|;
block|}
return|return
name|destination
return|;
block|}
block|}
end_class

end_unit

