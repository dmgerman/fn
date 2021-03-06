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
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|OutputStreamWriter
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
name|charset
operator|.
name|CharsetEncoder
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
name|Set
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
import|;
end_import

begin_comment
comment|/**  * Writer that similar to the built-in {@link java.io.FileWriter} but uses the {@link AtomicFileOutputStream} as the  * underlying output stream. In this way, we make sure that the errors during the write process do not destroy the  * contents of the target file.  * Moreover, this writer checks if the chosen encoding supports all text that is written. Characters whose encoding  * was problematic can be retrieved by {@link #getEncodingProblems()}.  */
end_comment

begin_class
DECL|class|AtomicFileWriter
specifier|public
class|class
name|AtomicFileWriter
extends|extends
name|OutputStreamWriter
block|{
DECL|field|encoder
specifier|private
specifier|final
name|CharsetEncoder
name|encoder
decl_stmt|;
DECL|field|problemCharacters
specifier|private
specifier|final
name|Set
argument_list|<
name|Character
argument_list|>
name|problemCharacters
init|=
operator|new
name|TreeSet
argument_list|<>
argument_list|()
decl_stmt|;
DECL|method|AtomicFileWriter (Path file, Charset encoding)
specifier|public
name|AtomicFileWriter
parameter_list|(
name|Path
name|file
parameter_list|,
name|Charset
name|encoding
parameter_list|)
throws|throws
name|IOException
block|{
name|this
argument_list|(
name|file
argument_list|,
name|encoding
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
DECL|method|AtomicFileWriter (Path file, Charset encoding, boolean keepBackup)
specifier|public
name|AtomicFileWriter
parameter_list|(
name|Path
name|file
parameter_list|,
name|Charset
name|encoding
parameter_list|,
name|boolean
name|keepBackup
parameter_list|)
throws|throws
name|IOException
block|{
name|super
argument_list|(
operator|new
name|AtomicFileOutputStream
argument_list|(
name|file
argument_list|,
name|keepBackup
argument_list|)
argument_list|,
name|encoding
argument_list|)
expr_stmt|;
name|encoder
operator|=
name|encoding
operator|.
name|newEncoder
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|write (String str)
specifier|public
name|void
name|write
parameter_list|(
name|String
name|str
parameter_list|)
throws|throws
name|IOException
block|{
name|super
operator|.
name|write
argument_list|(
name|str
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|encoder
operator|.
name|canEncode
argument_list|(
name|str
argument_list|)
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|str
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|char
name|character
init|=
name|str
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|encoder
operator|.
name|canEncode
argument_list|(
name|character
argument_list|)
condition|)
block|{
name|problemCharacters
operator|.
name|add
argument_list|(
name|character
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
DECL|method|hasEncodingProblems ()
specifier|public
name|boolean
name|hasEncodingProblems
parameter_list|()
block|{
return|return
operator|!
name|problemCharacters
operator|.
name|isEmpty
argument_list|()
return|;
block|}
DECL|method|getEncodingProblems ()
specifier|public
name|Set
argument_list|<
name|Character
argument_list|>
name|getEncodingProblems
parameter_list|()
block|{
return|return
name|Collections
operator|.
name|unmodifiableSet
argument_list|(
name|problemCharacters
argument_list|)
return|;
block|}
block|}
end_class

end_unit

