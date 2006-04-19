begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.imports
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
package|;
end_package

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
name|BufferedReader
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
name|ArrayList
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|BibtexEntry
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|AuthorList
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
name|BibtexFields
import|;
end_import

begin_comment
comment|/**  * Imports a Biblioscape Tag File. The format is described on  * http://www.biblioscape.com/manual_bsp/Biblioscape_Tag_File.htm Several  * Biblioscape field types are ignored. Others are only included in the BibTeX  * field "comment".  */
end_comment

begin_class
DECL|class|ScifinderImporter
specifier|public
class|class
name|ScifinderImporter
extends|extends
name|ImportFormat
block|{
comment|/**      * Return the name of this import format.      */
DECL|method|getFormatName ()
specifier|public
name|String
name|getFormatName
parameter_list|()
block|{
return|return
literal|"Scifinder"
return|;
block|}
comment|/*      *  (non-Javadoc)      * @see net.sf.jabref.imports.ImportFormat#getCLIId()      */
DECL|method|getCLIId ()
specifier|public
name|String
name|getCLIId
parameter_list|()
block|{
return|return
literal|"scifinder"
return|;
block|}
comment|/**      * Check whether the source is in the correct format for this importer.      */
DECL|method|isRecognizedFormat (InputStream in)
specifier|public
name|boolean
name|isRecognizedFormat
parameter_list|(
name|InputStream
name|in
parameter_list|)
throws|throws
name|IOException
block|{
return|return
literal|true
return|;
block|}
comment|/**      * Parse the entries in the source, and return a List of BibtexEntry      * objects.      */
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
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
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
name|str
decl_stmt|;
while|while
condition|(
operator|(
name|str
operator|=
name|in
operator|.
name|readLine
argument_list|()
operator|)
operator|!=
literal|null
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|str
argument_list|)
expr_stmt|;
block|}
name|String
index|[]
name|entries
init|=
name|sb
operator|.
name|toString
argument_list|()
operator|.
name|split
argument_list|(
literal|"START_RECORD"
argument_list|)
decl_stmt|;
name|HashMap
name|hm
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<
name|entries
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|String
index|[]
name|fields
init|=
name|entries
index|[
name|i
index|]
operator|.
name|split
argument_list|(
literal|"FIELD "
argument_list|)
decl_stmt|;
name|String
name|Type
init|=
literal|""
decl_stmt|;
name|hm
operator|.
name|clear
argument_list|()
expr_stmt|;
comment|// reset
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|fields
operator|.
name|length
condition|;
name|j
operator|++
control|)
if|if
condition|(
name|fields
index|[
name|j
index|]
operator|.
name|indexOf
argument_list|(
literal|":"
argument_list|)
operator|>=
literal|0
condition|)
block|{
name|String
name|tmp
index|[]
init|=
operator|new
name|String
index|[
literal|2
index|]
decl_stmt|;
name|tmp
index|[
literal|0
index|]
operator|=
name|fields
index|[
name|j
index|]
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|fields
index|[
name|j
index|]
operator|.
name|indexOf
argument_list|(
literal|":"
argument_list|)
argument_list|)
expr_stmt|;
name|tmp
index|[
literal|1
index|]
operator|=
name|fields
index|[
name|j
index|]
operator|.
name|substring
argument_list|(
name|fields
index|[
name|j
index|]
operator|.
name|indexOf
argument_list|(
literal|":"
argument_list|)
operator|+
literal|1
argument_list|)
operator|.
name|trim
argument_list|()
expr_stmt|;
if|if
condition|(
name|tmp
operator|.
name|length
operator|>
literal|1
condition|)
block|{
comment|//==2
if|if
condition|(
name|tmp
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
literal|"Author"
argument_list|)
condition|)
name|hm
operator|.
name|put
argument_list|(
literal|"author"
argument_list|,
name|AuthorList
operator|.
name|fixAuthor_lastNameFirst
argument_list|(
name|tmp
index|[
literal|1
index|]
operator|.
name|replaceAll
argument_list|(
literal|";"
argument_list|,
literal|" and "
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|tmp
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
literal|"Title"
argument_list|)
condition|)
name|hm
operator|.
name|put
argument_list|(
literal|"title"
argument_list|,
name|tmp
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|tmp
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
literal|"Journal Title"
argument_list|)
condition|)
name|hm
operator|.
name|put
argument_list|(
literal|"journal"
argument_list|,
name|tmp
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|tmp
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
literal|"Volume"
argument_list|)
condition|)
name|hm
operator|.
name|put
argument_list|(
literal|"volume"
argument_list|,
name|tmp
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|tmp
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
literal|"Page"
argument_list|)
condition|)
name|hm
operator|.
name|put
argument_list|(
literal|"pages"
argument_list|,
name|tmp
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|tmp
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
literal|"Publication Year"
argument_list|)
condition|)
name|hm
operator|.
name|put
argument_list|(
literal|"year"
argument_list|,
name|tmp
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|tmp
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
literal|"Abstract"
argument_list|)
condition|)
name|hm
operator|.
name|put
argument_list|(
literal|"abstract"
argument_list|,
name|tmp
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|tmp
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
literal|"Supplementary Terms"
argument_list|)
condition|)
name|hm
operator|.
name|put
argument_list|(
literal|"keywords"
argument_list|,
name|tmp
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|tmp
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
literal|"Document Type"
argument_list|)
condition|)
block|{
if|if
condition|(
name|tmp
index|[
literal|1
index|]
operator|.
name|startsWith
argument_list|(
literal|"Journal"
argument_list|)
operator|||
name|tmp
index|[
literal|1
index|]
operator|.
name|startsWith
argument_list|(
literal|"Review"
argument_list|)
condition|)
name|Type
operator|=
literal|"article"
expr_stmt|;
elseif|else
if|if
condition|(
name|tmp
index|[
literal|1
index|]
operator|.
name|equals
argument_list|(
literal|"Dissertation"
argument_list|)
condition|)
name|Type
operator|=
literal|"phdthesis"
expr_stmt|;
else|else
name|Type
operator|=
name|tmp
index|[
literal|1
index|]
expr_stmt|;
block|}
block|}
block|}
name|BibtexEntry
name|b
init|=
operator|new
name|BibtexEntry
argument_list|(
name|BibtexFields
operator|.
name|DEFAULT_BIBTEXENTRY_ID
argument_list|,
name|Globals
operator|.
name|getEntryType
argument_list|(
name|Type
argument_list|)
argument_list|)
decl_stmt|;
comment|// id assumes an existing database so don't
comment|// create one here
name|b
operator|.
name|setField
argument_list|(
name|hm
argument_list|)
expr_stmt|;
name|bibitems
operator|.
name|add
argument_list|(
name|b
argument_list|)
expr_stmt|;
block|}
return|return
name|bibitems
return|;
block|}
block|}
end_class

end_unit

