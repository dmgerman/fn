begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.net
package|package
name|org
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
name|FilterInputStream
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
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|LongProperty
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|SimpleLongProperty
import|;
end_import

begin_comment
comment|/**  * An input stream that keeps track of the amount of bytes already read.  * Code based on http://stackoverflow.com/a/1339589/873661, but converted to use JavaFX properties instead of listeners  */
end_comment

begin_class
DECL|class|ProgressInputStream
specifier|public
class|class
name|ProgressInputStream
extends|extends
name|FilterInputStream
block|{
DECL|field|maxNumBytes
specifier|private
specifier|final
name|long
name|maxNumBytes
decl_stmt|;
DECL|field|totalNumBytesRead
specifier|private
specifier|final
name|LongProperty
name|totalNumBytesRead
decl_stmt|;
DECL|field|progress
specifier|private
specifier|final
name|LongProperty
name|progress
decl_stmt|;
DECL|method|ProgressInputStream (InputStream in, long maxNumBytes)
specifier|public
name|ProgressInputStream
parameter_list|(
name|InputStream
name|in
parameter_list|,
name|long
name|maxNumBytes
parameter_list|)
block|{
name|super
argument_list|(
name|in
argument_list|)
expr_stmt|;
name|this
operator|.
name|maxNumBytes
operator|=
name|maxNumBytes
expr_stmt|;
name|this
operator|.
name|totalNumBytesRead
operator|=
operator|new
name|SimpleLongProperty
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|this
operator|.
name|progress
operator|=
operator|new
name|SimpleLongProperty
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|this
operator|.
name|progress
operator|.
name|bind
argument_list|(
name|totalNumBytesRead
operator|.
name|divide
argument_list|(
name|maxNumBytes
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|getTotalNumBytesRead ()
specifier|public
name|long
name|getTotalNumBytesRead
parameter_list|()
block|{
return|return
name|totalNumBytesRead
operator|.
name|get
argument_list|()
return|;
block|}
DECL|method|totalNumBytesReadProperty ()
specifier|public
name|LongProperty
name|totalNumBytesReadProperty
parameter_list|()
block|{
return|return
name|totalNumBytesRead
return|;
block|}
DECL|method|getProgress ()
specifier|public
name|long
name|getProgress
parameter_list|()
block|{
return|return
name|progress
operator|.
name|get
argument_list|()
return|;
block|}
DECL|method|progressProperty ()
specifier|public
name|LongProperty
name|progressProperty
parameter_list|()
block|{
return|return
name|progress
return|;
block|}
DECL|method|getMaxNumBytes ()
specifier|public
name|long
name|getMaxNumBytes
parameter_list|()
block|{
return|return
name|maxNumBytes
return|;
block|}
annotation|@
name|Override
DECL|method|read ()
specifier|public
name|int
name|read
parameter_list|()
throws|throws
name|IOException
block|{
name|int
name|b
init|=
name|super
operator|.
name|read
argument_list|()
decl_stmt|;
name|updateProgress
argument_list|(
literal|1
argument_list|)
expr_stmt|;
return|return
name|b
return|;
block|}
annotation|@
name|Override
DECL|method|read (byte[] b, int off, int len)
specifier|public
name|int
name|read
parameter_list|(
name|byte
index|[]
name|b
parameter_list|,
name|int
name|off
parameter_list|,
name|int
name|len
parameter_list|)
throws|throws
name|IOException
block|{
return|return
operator|(
name|int
operator|)
name|updateProgress
argument_list|(
name|super
operator|.
name|read
argument_list|(
name|b
argument_list|,
name|off
argument_list|,
name|len
argument_list|)
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|skip (long n)
specifier|public
name|long
name|skip
parameter_list|(
name|long
name|n
parameter_list|)
throws|throws
name|IOException
block|{
return|return
name|updateProgress
argument_list|(
name|super
operator|.
name|skip
argument_list|(
name|n
argument_list|)
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|mark (int readlimit)
specifier|public
name|void
name|mark
parameter_list|(
name|int
name|readlimit
parameter_list|)
block|{
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|()
throw|;
block|}
annotation|@
name|Override
DECL|method|reset ()
specifier|public
name|void
name|reset
parameter_list|()
throws|throws
name|IOException
block|{
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|()
throw|;
block|}
annotation|@
name|Override
DECL|method|markSupported ()
specifier|public
name|boolean
name|markSupported
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
DECL|method|updateProgress (long numBytesRead)
specifier|private
name|long
name|updateProgress
parameter_list|(
name|long
name|numBytesRead
parameter_list|)
block|{
if|if
condition|(
name|numBytesRead
operator|>
literal|0
condition|)
block|{
name|totalNumBytesRead
operator|.
name|set
argument_list|(
name|totalNumBytesRead
operator|.
name|get
argument_list|()
operator|+
name|numBytesRead
argument_list|)
expr_stmt|;
block|}
return|return
name|numBytesRead
return|;
block|}
block|}
end_class

end_unit

