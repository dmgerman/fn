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
name|Reader
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
name|Vector
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
name|BibtexEntryType
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
name|Util
import|;
end_import

begin_comment
comment|/**  * Imports a Biblioscape Tag File. The format is described on  * http://www.biblioscape.com/manual_bsp/Biblioscape_Tag_File.htm Several  * Biblioscape field types are ignored. Others are only included in the BibTeX  * field "comment".  */
end_comment

begin_class
DECL|class|RisImporter
specifier|public
class|class
name|RisImporter
implements|implements
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
literal|"RIS"
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
name|sb
operator|.
name|append
argument_list|(
literal|"\n"
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
literal|"ER  -"
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
literal|0
init|;
name|i
operator|<
name|entries
operator|.
name|length
operator|-
literal|1
condition|;
name|i
operator|++
control|)
block|{
name|String
name|Type
init|=
literal|""
decl_stmt|,
name|Author
init|=
literal|""
decl_stmt|,
name|StartPage
init|=
literal|""
decl_stmt|,
name|EndPage
init|=
literal|""
decl_stmt|;
name|hm
operator|.
name|clear
argument_list|()
expr_stmt|;
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
literal|"\n"
argument_list|)
decl_stmt|;
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
block|{
if|if
condition|(
name|fields
index|[
name|j
index|]
operator|.
name|length
argument_list|()
operator|<
literal|6
condition|)
continue|continue;
else|else
block|{
name|String
name|lab
init|=
name|fields
index|[
name|j
index|]
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|2
argument_list|)
decl_stmt|;
name|String
name|val
init|=
name|fields
index|[
name|j
index|]
operator|.
name|substring
argument_list|(
literal|6
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
if|if
condition|(
name|lab
operator|.
name|equals
argument_list|(
literal|"TY"
argument_list|)
condition|)
block|{
if|if
condition|(
name|val
operator|.
name|equals
argument_list|(
literal|"BOOK"
argument_list|)
condition|)
name|Type
operator|=
literal|"book"
expr_stmt|;
elseif|else
if|if
condition|(
name|val
operator|.
name|equals
argument_list|(
literal|"JOUR"
argument_list|)
condition|)
name|Type
operator|=
literal|"article"
expr_stmt|;
else|else
name|Type
operator|=
literal|"other"
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|lab
operator|.
name|equals
argument_list|(
literal|"T1"
argument_list|)
operator|||
name|lab
operator|.
name|equals
argument_list|(
literal|"TI"
argument_list|)
condition|)
name|hm
operator|.
name|put
argument_list|(
literal|"title"
argument_list|,
name|val
argument_list|)
expr_stmt|;
comment|//Title
comment|// =
comment|// val;
elseif|else
if|if
condition|(
name|lab
operator|.
name|equals
argument_list|(
literal|"A1"
argument_list|)
operator|||
name|lab
operator|.
name|equals
argument_list|(
literal|"AU"
argument_list|)
condition|)
block|{
if|if
condition|(
name|Author
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
comment|// don't add " and " for the first author
name|Author
operator|=
name|val
expr_stmt|;
else|else
name|Author
operator|+=
literal|" and "
operator|+
name|val
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|lab
operator|.
name|equals
argument_list|(
literal|"JA"
argument_list|)
operator|||
name|lab
operator|.
name|equals
argument_list|(
literal|"JF"
argument_list|)
operator|||
name|lab
operator|.
name|equals
argument_list|(
literal|"JO"
argument_list|)
condition|)
name|hm
operator|.
name|put
argument_list|(
literal|"journal"
argument_list|,
name|val
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|lab
operator|.
name|equals
argument_list|(
literal|"SP"
argument_list|)
condition|)
name|StartPage
operator|=
name|val
expr_stmt|;
elseif|else
if|if
condition|(
name|lab
operator|.
name|equals
argument_list|(
literal|"EP"
argument_list|)
condition|)
name|EndPage
operator|=
name|val
expr_stmt|;
elseif|else
if|if
condition|(
name|lab
operator|.
name|equals
argument_list|(
literal|"VL"
argument_list|)
condition|)
name|hm
operator|.
name|put
argument_list|(
literal|"volume"
argument_list|,
name|val
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|lab
operator|.
name|equals
argument_list|(
literal|"IS"
argument_list|)
condition|)
name|hm
operator|.
name|put
argument_list|(
literal|"number"
argument_list|,
name|val
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|lab
operator|.
name|equals
argument_list|(
literal|"N2"
argument_list|)
operator|||
name|lab
operator|.
name|equals
argument_list|(
literal|"AB"
argument_list|)
condition|)
name|hm
operator|.
name|put
argument_list|(
literal|"abstract"
argument_list|,
name|val
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|lab
operator|.
name|equals
argument_list|(
literal|"UR"
argument_list|)
condition|)
name|hm
operator|.
name|put
argument_list|(
literal|"url"
argument_list|,
name|val
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
operator|(
name|lab
operator|.
name|equals
argument_list|(
literal|"Y1"
argument_list|)
operator|||
name|lab
operator|.
name|equals
argument_list|(
literal|"PY"
argument_list|)
operator|)
operator|&&
name|val
operator|.
name|length
argument_list|()
operator|>=
literal|4
condition|)
name|hm
operator|.
name|put
argument_list|(
literal|"year"
argument_list|,
name|val
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|4
argument_list|)
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|lab
operator|.
name|equals
argument_list|(
literal|"KW"
argument_list|)
condition|)
block|{
if|if
condition|(
operator|!
name|hm
operator|.
name|containsKey
argument_list|(
literal|"keywords"
argument_list|)
condition|)
name|hm
operator|.
name|put
argument_list|(
literal|"keywords"
argument_list|,
name|val
argument_list|)
expr_stmt|;
else|else
block|{
name|String
name|kw
init|=
operator|(
name|String
operator|)
name|hm
operator|.
name|get
argument_list|(
literal|"keywords"
argument_list|)
decl_stmt|;
name|hm
operator|.
name|put
argument_list|(
literal|"keywords"
argument_list|,
name|kw
operator|+
literal|", "
operator|+
name|val
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
comment|// fix authors
name|Author
operator|=
name|ImportFormatReader
operator|.
name|fixAuthor_lastnameFirst
argument_list|(
name|Author
argument_list|)
expr_stmt|;
name|hm
operator|.
name|put
argument_list|(
literal|"author"
argument_list|,
name|Author
argument_list|)
expr_stmt|;
name|hm
operator|.
name|put
argument_list|(
literal|"pages"
argument_list|,
name|StartPage
operator|+
literal|"--"
operator|+
name|EndPage
argument_list|)
expr_stmt|;
name|BibtexEntry
name|b
init|=
operator|new
name|BibtexEntry
argument_list|(
name|Globals
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

