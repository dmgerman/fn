begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|util
operator|.
name|*
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
name|*
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
name|imports
operator|.
name|ImportFormat
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
name|imports
operator|.
name|ImportFormatReader
import|;
end_import

begin_class
DECL|class|SimpleCsvImporter
specifier|public
class|class
name|SimpleCsvImporter
extends|extends
name|ImportFormat
block|{
DECL|method|getFormatName ()
specifier|public
name|String
name|getFormatName
parameter_list|()
block|{
return|return
literal|"Simple CSV Importer"
return|;
block|}
DECL|method|isRecognizedFormat (InputStream stream)
specifier|public
name|boolean
name|isRecognizedFormat
parameter_list|(
name|InputStream
name|stream
parameter_list|)
throws|throws
name|IOException
block|{
return|return
literal|true
return|;
comment|// this is discouraged except for demonstration purposes
block|}
DECL|method|importEntries (InputStream stream)
specifier|public
name|List
name|importEntries
parameter_list|(
name|InputStream
name|stream
parameter_list|)
throws|throws
name|IOException
block|{
name|ArrayList
name|bibitems
init|=
operator|new
name|ArrayList
argument_list|()
decl_stmt|;
name|BufferedReader
name|in
init|=
operator|new
name|BufferedReader
argument_list|(
name|ImportFormatReader
operator|.
name|getReaderDefaultEncoding
argument_list|(
name|stream
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|line
init|=
name|in
operator|.
name|readLine
argument_list|()
decl_stmt|;
while|while
condition|(
name|line
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
operator|!
literal|""
operator|.
name|equals
argument_list|(
name|line
operator|.
name|trim
argument_list|()
argument_list|)
condition|)
block|{
name|String
index|[]
name|fields
init|=
name|line
operator|.
name|split
argument_list|(
literal|";"
argument_list|)
decl_stmt|;
name|BibtexEntry
name|be
init|=
operator|new
name|BibtexEntry
argument_list|(
name|Util
operator|.
name|createNeutralId
argument_list|()
argument_list|)
decl_stmt|;
name|be
operator|.
name|setType
argument_list|(
name|BibtexEntryType
operator|.
name|getType
argument_list|(
literal|"techreport"
argument_list|)
argument_list|)
expr_stmt|;
name|be
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
name|fields
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|be
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
name|fields
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
name|be
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
name|fields
index|[
literal|2
index|]
argument_list|)
expr_stmt|;
name|bibitems
operator|.
name|add
argument_list|(
name|be
argument_list|)
expr_stmt|;
name|line
operator|=
name|in
operator|.
name|readLine
argument_list|()
expr_stmt|;
block|}
block|}
return|return
name|bibitems
return|;
block|}
block|}
end_class

end_unit

