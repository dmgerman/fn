begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.journals
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|journals
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
name|OutputStream
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
name|util
operator|.
name|List
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
name|util
operator|.
name|OS
import|;
end_import

begin_comment
comment|/**  * This class provides handy static methodes to save abbreviations to the file system.  */
end_comment

begin_class
DECL|class|AbbreviationWriter
specifier|public
class|class
name|AbbreviationWriter
block|{
DECL|method|AbbreviationWriter ()
specifier|private
name|AbbreviationWriter
parameter_list|()
block|{     }
comment|/**      * This method will write the list of abbreviations to a file on the file system specified by the given path.      * If the file already exists its content will be overridden, otherwise a new file will be created.      *      * @param path to a file (doesn't have to exist just yet)      * @param abbreviations as a list specifying which entries should be written      * @throws IOException      */
DECL|method|writeOrCreate (Path path, List<Abbreviation> abbreviations, Charset encoding)
specifier|public
specifier|static
name|void
name|writeOrCreate
parameter_list|(
name|Path
name|path
parameter_list|,
name|List
argument_list|<
name|Abbreviation
argument_list|>
name|abbreviations
parameter_list|,
name|Charset
name|encoding
parameter_list|)
throws|throws
name|IOException
block|{
try|try
init|(
name|OutputStream
name|outStream
init|=
name|Files
operator|.
name|newOutputStream
argument_list|(
name|path
argument_list|)
init|;
name|OutputStreamWriter
name|writer
operator|=
operator|new
name|OutputStreamWriter
argument_list|(
name|outStream
argument_list|,
name|encoding
argument_list|)
init|)
block|{
for|for
control|(
name|Abbreviation
name|entry
range|:
name|abbreviations
control|)
block|{
name|writer
operator|.
name|write
argument_list|(
name|entry
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
literal|" = "
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|entry
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|OS
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

