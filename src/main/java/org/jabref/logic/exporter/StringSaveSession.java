begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.exporter
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|exporter
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ByteArrayOutputStream
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
name|UnsupportedEncodingException
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
DECL|class|StringSaveSession
specifier|public
class|class
name|StringSaveSession
extends|extends
name|SaveSession
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
name|StringSaveSession
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|outputStream
specifier|private
specifier|final
name|ByteArrayOutputStream
name|outputStream
decl_stmt|;
DECL|method|StringSaveSession (Charset encoding, boolean backup)
specifier|public
name|StringSaveSession
parameter_list|(
name|Charset
name|encoding
parameter_list|,
name|boolean
name|backup
parameter_list|)
block|{
name|this
argument_list|(
name|encoding
argument_list|,
name|backup
argument_list|,
operator|new
name|ByteArrayOutputStream
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|StringSaveSession (Charset encoding, boolean backup, ByteArrayOutputStream outputStream)
specifier|private
name|StringSaveSession
parameter_list|(
name|Charset
name|encoding
parameter_list|,
name|boolean
name|backup
parameter_list|,
name|ByteArrayOutputStream
name|outputStream
parameter_list|)
block|{
name|super
argument_list|(
name|encoding
argument_list|,
name|backup
argument_list|,
operator|new
name|VerifyingWriter
argument_list|(
name|outputStream
argument_list|,
name|encoding
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|outputStream
operator|=
name|outputStream
expr_stmt|;
block|}
DECL|method|getStringValue ()
specifier|public
name|String
name|getStringValue
parameter_list|()
block|{
try|try
block|{
return|return
name|outputStream
operator|.
name|toString
argument_list|(
name|encoding
operator|.
name|name
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|UnsupportedEncodingException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
name|e
argument_list|)
expr_stmt|;
return|return
literal|""
return|;
block|}
block|}
annotation|@
name|Override
DECL|method|commit (Path file)
specifier|public
name|void
name|commit
parameter_list|(
name|Path
name|file
parameter_list|)
throws|throws
name|SaveException
block|{
try|try
block|{
name|Files
operator|.
name|write
argument_list|(
name|file
argument_list|,
name|outputStream
operator|.
name|toByteArray
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|SaveException
argument_list|(
name|e
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
DECL|method|cancel ()
specifier|public
name|void
name|cancel
parameter_list|()
block|{
try|try
block|{
name|outputStream
operator|.
name|close
argument_list|()
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
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit
