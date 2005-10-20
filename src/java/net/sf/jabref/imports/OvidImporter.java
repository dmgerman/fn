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
name|util
operator|.
name|regex
operator|.
name|Matcher
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
import|;
end_import

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
DECL|class|OvidImporter
specifier|public
class|class
name|OvidImporter
implements|implements
name|ImportFormat
block|{
DECL|field|ovid_src_pat
specifier|public
specifier|static
name|Pattern
name|ovid_src_pat
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"Source ([ \\w&\\-]+)\\.[ ]+([0-9]+)\\(([\\w\\-]+)\\):([0-9]+\\-?[0-9]+?)\\,.*([0-9][0-9][0-9][0-9])"
argument_list|)
decl_stmt|;
DECL|field|ovid_src_pat_no_issue
specifier|public
specifier|static
name|Pattern
name|ovid_src_pat_no_issue
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"Source ([ \\w&\\-]+)\\.[ ]+([0-9]+):([0-9]+\\-?[0-9]+?)\\,.*([0-9][0-9][0-9][0-9])"
argument_list|)
decl_stmt|;
comment|//   public static Pattern ovid_pat_inspec= Pattern.compile("Source ([
comment|// \\w&\\-]+)");
comment|/**      * Return the name of this import format.      */
DECL|method|getFormatName ()
specifier|public
name|String
name|getFormatName
parameter_list|()
block|{
return|return
literal|"Ovid"
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
name|line
decl_stmt|;
while|while
condition|(
operator|(
name|line
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
name|line
operator|.
name|length
argument_list|()
operator|>
literal|0
operator|&&
name|line
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|!=
literal|' '
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"__NEWFIELD__"
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
name|line
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|'\n'
argument_list|)
expr_stmt|;
block|}
name|String
name|items
index|[]
init|=
name|sb
operator|.
name|toString
argument_list|()
operator|.
name|split
argument_list|(
literal|"<[0-9]+>"
argument_list|)
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
name|items
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|HashMap
name|h
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
name|String
index|[]
name|fields
init|=
name|items
index|[
name|i
index|]
operator|.
name|split
argument_list|(
literal|"__NEWFIELD__"
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
name|int
name|linebreak
init|=
name|fields
index|[
name|j
index|]
operator|.
name|indexOf
argument_list|(
literal|'\n'
argument_list|)
decl_stmt|;
name|String
name|fieldName
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
name|linebreak
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
name|String
name|content
init|=
name|fields
index|[
name|j
index|]
operator|.
name|substring
argument_list|(
name|linebreak
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
comment|//fields[j] = fields[j].trim();
if|if
condition|(
name|fieldName
operator|.
name|indexOf
argument_list|(
literal|"Author"
argument_list|)
operator|==
literal|0
operator|&&
name|fieldName
operator|.
name|indexOf
argument_list|(
literal|"Author Keywords"
argument_list|)
operator|==
operator|-
literal|1
operator|&&
name|fieldName
operator|.
name|indexOf
argument_list|(
literal|"Author e-mail"
argument_list|)
operator|==
operator|-
literal|1
condition|)
block|{
name|String
name|author
decl_stmt|;
name|String
index|[]
name|names
decl_stmt|;
if|if
condition|(
name|content
operator|.
name|indexOf
argument_list|(
literal|";"
argument_list|)
operator|>
literal|0
condition|)
block|{
comment|//LN FN; [LN FN;]*
name|names
operator|=
name|content
operator|.
name|replaceAll
argument_list|(
literal|"[^\\.A-Za-z,;\\- ]"
argument_list|,
literal|""
argument_list|)
operator|.
name|split
argument_list|(
literal|";"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// LN FN. [LN FN.]*
comment|//author = content.replaceAll("\\.", " and").replaceAll(" and$", "");
name|names
operator|=
name|content
operator|.
name|split
argument_list|(
literal|"  "
argument_list|)
expr_stmt|;
block|}
name|StringBuffer
name|buf
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|ii
init|=
literal|0
init|;
name|ii
operator|<
name|names
operator|.
name|length
condition|;
name|ii
operator|++
control|)
block|{
name|names
index|[
name|ii
index|]
operator|=
name|names
index|[
name|ii
index|]
operator|.
name|trim
argument_list|()
expr_stmt|;
name|int
name|space
init|=
name|names
index|[
name|ii
index|]
operator|.
name|indexOf
argument_list|(
literal|' '
argument_list|)
decl_stmt|;
if|if
condition|(
name|space
operator|>=
literal|0
condition|)
block|{
name|buf
operator|.
name|append
argument_list|(
name|names
index|[
name|ii
index|]
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|space
argument_list|)
argument_list|)
expr_stmt|;
name|buf
operator|.
name|append
argument_list|(
literal|','
argument_list|)
expr_stmt|;
name|buf
operator|.
name|append
argument_list|(
name|names
index|[
name|ii
index|]
operator|.
name|substring
argument_list|(
name|space
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|buf
operator|.
name|append
argument_list|(
name|names
index|[
name|ii
index|]
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|ii
operator|<
name|names
operator|.
name|length
operator|-
literal|1
condition|)
name|buf
operator|.
name|append
argument_list|(
literal|" and "
argument_list|)
expr_stmt|;
block|}
name|h
operator|.
name|put
argument_list|(
literal|"author"
argument_list|,
name|buf
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
comment|//    author = content.replaceAll("  ", " and ").replaceAll(" and $", "");
comment|//h.put("author", ImportFormatReader.fixAuthor_lastnameFirst(author));
block|}
elseif|else
if|if
condition|(
name|fieldName
operator|.
name|indexOf
argument_list|(
literal|"Title"
argument_list|)
operator|==
literal|0
condition|)
name|h
operator|.
name|put
argument_list|(
literal|"title"
argument_list|,
name|content
operator|.
name|replaceAll
argument_list|(
literal|"\\[.+\\]"
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|fieldName
operator|.
name|indexOf
argument_list|(
literal|"Source"
argument_list|)
operator|==
literal|0
condition|)
block|{
comment|//System.out.println(fields[j]);
name|String
name|s
init|=
name|content
decl_stmt|;
name|Matcher
name|matcher
init|=
name|ovid_src_pat
operator|.
name|matcher
argument_list|(
name|s
argument_list|)
decl_stmt|;
name|boolean
name|matchfound
init|=
name|matcher
operator|.
name|find
argument_list|()
decl_stmt|;
if|if
condition|(
name|matchfound
condition|)
block|{
name|h
operator|.
name|put
argument_list|(
literal|"journal"
argument_list|,
name|matcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"volume"
argument_list|,
name|matcher
operator|.
name|group
argument_list|(
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"issue"
argument_list|,
name|matcher
operator|.
name|group
argument_list|(
literal|3
argument_list|)
argument_list|)
expr_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"pages"
argument_list|,
name|matcher
operator|.
name|group
argument_list|(
literal|4
argument_list|)
argument_list|)
expr_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"year"
argument_list|,
name|matcher
operator|.
name|group
argument_list|(
literal|5
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// may be missing the issue
name|matcher
operator|=
name|ovid_src_pat_no_issue
operator|.
name|matcher
argument_list|(
name|s
argument_list|)
expr_stmt|;
name|matchfound
operator|=
name|matcher
operator|.
name|find
argument_list|()
expr_stmt|;
if|if
condition|(
name|matchfound
condition|)
block|{
name|h
operator|.
name|put
argument_list|(
literal|"journal"
argument_list|,
name|matcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"volume"
argument_list|,
name|matcher
operator|.
name|group
argument_list|(
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"pages"
argument_list|,
name|matcher
operator|.
name|group
argument_list|(
literal|3
argument_list|)
argument_list|)
expr_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"year"
argument_list|,
name|matcher
operator|.
name|group
argument_list|(
literal|4
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
elseif|else
if|if
condition|(
name|fieldName
operator|.
name|equals
argument_list|(
literal|"Abstract"
argument_list|)
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"'"
operator|+
name|content
operator|+
literal|"'"
argument_list|)
expr_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"abstract"
argument_list|,
name|content
argument_list|)
expr_stmt|;
block|}
block|}
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
literal|"article"
argument_list|)
argument_list|)
decl_stmt|;
comment|// id assumes an existing database so
comment|// don't create one here
name|b
operator|.
name|setField
argument_list|(
name|h
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

