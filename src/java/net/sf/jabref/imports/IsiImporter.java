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

begin_comment
comment|/**  * Importer for the ISI Web of Science format.  */
end_comment

begin_class
DECL|class|IsiImporter
specifier|public
class|class
name|IsiImporter
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
literal|"ISI"
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
comment|//Pattern fieldPattern = Pattern.compile("^AU |^TI |^SO |^DT |^C1 |^AB
comment|// |^ID |^BP |^PY |^SE |^PY |^VL |^IS ");
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
if|if
condition|(
name|str
operator|.
name|length
argument_list|()
operator|<
literal|3
condition|)
continue|continue;
comment|// begining of a new item
if|if
condition|(
name|str
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|3
argument_list|)
operator|.
name|equals
argument_list|(
literal|"PT "
argument_list|)
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"::"
operator|+
name|str
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|String
name|beg
init|=
name|str
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|3
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
comment|// I could have used the fieldPattern regular expression instead
comment|// however this seems to be
comment|// quick and dirty and it works!
if|if
condition|(
name|beg
operator|.
name|length
argument_list|()
operator|==
literal|2
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|" ## "
argument_list|)
expr_stmt|;
comment|// mark the begining of each field
name|sb
operator|.
name|append
argument_list|(
name|str
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"EOLEOL"
argument_list|)
expr_stmt|;
comment|// mark the end of each line
name|sb
operator|.
name|append
argument_list|(
name|str
operator|.
name|substring
argument_list|(
literal|2
argument_list|,
name|str
operator|.
name|length
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|//remove the initial " "
block|}
block|}
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
literal|"::"
argument_list|)
decl_stmt|;
comment|// skip the first entry as it is either empty or has document header
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
literal|" ## "
argument_list|)
decl_stmt|;
if|if
condition|(
name|fields
operator|.
name|length
operator|==
literal|0
condition|)
name|fields
operator|=
name|entries
index|[
name|i
index|]
operator|.
name|split
argument_list|(
literal|"\n"
argument_list|)
expr_stmt|;
name|String
name|Type
init|=
literal|""
decl_stmt|,
name|PT
init|=
literal|""
decl_stmt|,
name|pages
init|=
literal|""
decl_stmt|;
name|hm
operator|.
name|clear
argument_list|()
expr_stmt|;
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
comment|//empty field don't do anything
if|if
condition|(
name|fields
index|[
name|j
index|]
operator|.
name|length
argument_list|()
operator|<=
literal|2
condition|)
continue|continue;
name|String
name|beg
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
name|value
init|=
name|fields
index|[
name|j
index|]
operator|.
name|substring
argument_list|(
literal|2
argument_list|)
decl_stmt|;
name|value
operator|=
name|value
operator|.
name|trim
argument_list|()
expr_stmt|;
if|if
condition|(
name|value
operator|.
name|startsWith
argument_list|(
literal|"-"
argument_list|)
condition|)
name|value
operator|=
name|value
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|value
operator|=
name|value
operator|.
name|trim
argument_list|()
expr_stmt|;
if|if
condition|(
name|beg
operator|.
name|equals
argument_list|(
literal|"PT"
argument_list|)
condition|)
block|{
name|PT
operator|=
name|value
operator|.
name|replaceAll
argument_list|(
literal|"Journal"
argument_list|,
literal|"article"
argument_list|)
expr_stmt|;
name|Type
operator|=
literal|"article"
expr_stmt|;
comment|//make all of them PT?
block|}
elseif|else
if|if
condition|(
name|beg
operator|.
name|equals
argument_list|(
literal|"TY"
argument_list|)
condition|)
block|{
name|Type
operator|=
literal|"inproceedings"
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|beg
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
literal|"booktitle"
argument_list|,
name|value
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|beg
operator|.
name|equals
argument_list|(
literal|"AU"
argument_list|)
condition|)
name|hm
operator|.
name|put
argument_list|(
literal|"author"
argument_list|,
name|ImportFormatReader
operator|.
name|fixAuthor_lastnameFirst
argument_list|(
name|value
operator|.
name|replaceAll
argument_list|(
literal|"EOLEOL"
argument_list|,
literal|" and "
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|beg
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
name|value
operator|.
name|replaceAll
argument_list|(
literal|"EOLEOL"
argument_list|,
literal|" "
argument_list|)
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|beg
operator|.
name|equals
argument_list|(
literal|"SO"
argument_list|)
condition|)
block|{
comment|// journal name
name|hm
operator|.
name|put
argument_list|(
literal|"journal"
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|beg
operator|.
name|equals
argument_list|(
literal|"ID"
argument_list|)
condition|)
name|hm
operator|.
name|put
argument_list|(
literal|"keywords"
argument_list|,
name|value
operator|.
name|replaceAll
argument_list|(
literal|"EOLEOL"
argument_list|,
literal|" "
argument_list|)
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|beg
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
name|value
operator|.
name|replaceAll
argument_list|(
literal|"EOLEOL"
argument_list|,
literal|" "
argument_list|)
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|beg
operator|.
name|equals
argument_list|(
literal|"BP"
argument_list|)
operator|||
name|beg
operator|.
name|equals
argument_list|(
literal|"BR"
argument_list|)
operator|||
name|beg
operator|.
name|equals
argument_list|(
literal|"SP"
argument_list|)
condition|)
name|pages
operator|=
name|value
expr_stmt|;
elseif|else
if|if
condition|(
name|beg
operator|.
name|equals
argument_list|(
literal|"EP"
argument_list|)
condition|)
block|{
name|int
name|detpos
init|=
name|value
operator|.
name|indexOf
argument_list|(
literal|' '
argument_list|)
decl_stmt|;
comment|// tweak for IEEE Explore
if|if
condition|(
name|detpos
operator|!=
operator|-
literal|1
condition|)
block|{
name|value
operator|=
name|value
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|detpos
argument_list|)
expr_stmt|;
block|}
name|pages
operator|=
name|pages
operator|+
literal|"--"
operator|+
name|value
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|beg
operator|.
name|equals
argument_list|(
literal|"AR"
argument_list|)
condition|)
name|pages
operator|=
name|value
expr_stmt|;
elseif|else
if|if
condition|(
name|beg
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
name|value
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|beg
operator|.
name|equals
argument_list|(
literal|"PY"
argument_list|)
condition|)
name|hm
operator|.
name|put
argument_list|(
literal|"year"
argument_list|,
name|value
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|beg
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
name|value
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|beg
operator|.
name|equals
argument_list|(
literal|"DT"
argument_list|)
condition|)
block|{
name|Type
operator|=
name|value
expr_stmt|;
if|if
condition|(
operator|!
name|Type
operator|.
name|equals
argument_list|(
literal|"Article"
argument_list|)
operator|&&
operator|!
name|PT
operator|.
name|equals
argument_list|(
literal|"Journal"
argument_list|)
condition|)
comment|//Article"))
name|Type
operator|=
literal|"misc"
expr_stmt|;
else|else
name|Type
operator|=
literal|"article"
expr_stmt|;
block|}
comment|//ignore
elseif|else
if|if
condition|(
name|beg
operator|.
name|equals
argument_list|(
literal|"CR"
argument_list|)
condition|)
comment|//cited references
name|hm
operator|.
name|put
argument_list|(
literal|"CitedReferences"
argument_list|,
name|value
operator|.
name|replaceAll
argument_list|(
literal|"EOLEOL"
argument_list|,
literal|" ; "
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|hm
operator|.
name|put
argument_list|(
literal|"pages"
argument_list|,
name|pages
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

