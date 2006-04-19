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
name|io
operator|.
name|InputStream
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
name|regex
operator|.
name|Pattern
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
name|Matcher
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
comment|/**  * Importer for records downloaded from CSA: Cambridge Scientific Abstracts  * in full text format.  Although the same basic format is used by all CSA  * databases, this importer has been tailored and tested to handle  * ASFA: Aquatic Sciences and Fisheries records.  *  * @author John Relph  */
end_comment

begin_class
DECL|class|CsaImporter
specifier|public
class|class
name|CsaImporter
extends|extends
name|ImportFormat
block|{
comment|// local fields
DECL|field|line
specifier|private
name|int
name|line
decl_stmt|;
comment|// pre-compiled patterns
DECL|field|FIELD_PATTERN
specifier|private
specifier|final
specifier|static
name|Pattern
name|FIELD_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"^([A-Z][A-Z]): ([A-Z].*)$"
argument_list|)
decl_stmt|;
DECL|field|VOLNOPP_PATTERN
specifier|private
specifier|final
specifier|static
name|Pattern
name|VOLNOPP_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"[;,\\.]\\s+(\\d+[A-Za-z]?)\\((\\d+(?:-\\d+)?)\\)(?:,\\s+|:)(\\d+-\\d+)"
argument_list|)
decl_stmt|;
DECL|field|PAGES_PATTERN
specifier|private
specifier|final
specifier|static
name|Pattern
name|PAGES_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"[;,\\.]\\s+(?:(\\[?[vn]\\.?p\\.?\\]?)|(?:pp?\\.?\\s+)(\\d+[A-Z]?(?:-\\d+[A-Z]?)?)|(\\d+[A-Z]?(?:-\\d+[A-Z]?)?)(?:\\s+pp?))"
argument_list|)
decl_stmt|;
DECL|field|VOLUME_PATTERN
specifier|private
specifier|final
specifier|static
name|Pattern
name|VOLUME_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"[;,\\.]?\\s+[vV][oO][lL]\\.?\\s+(\\d+[A-Z]?(?:-\\d+[A-Z]?)?)"
argument_list|)
decl_stmt|;
DECL|field|NUMBER_PATTERN
specifier|private
specifier|final
specifier|static
name|Pattern
name|NUMBER_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"[;,\\.]\\s+(?:No|no|Part|part|NUMB)\\.?\\s+([A-Z]?\\d+(?:[/-]\\d+)?)"
argument_list|)
decl_stmt|;
DECL|field|DATE_PATTERN
specifier|private
specifier|final
specifier|static
name|Pattern
name|DATE_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"[;,\\.]\\s+(?:(\\d+)\\s)?(?:([A-Z][a-z][a-z])[\\.,]*\\s)?\\(?(\\d\\d\\d\\d)\\)?(?:\\s([A-Z][a-z][a-z]))?(?:\\s+(\\d+))?"
argument_list|)
decl_stmt|;
DECL|field|LT_PATTERN
specifier|private
specifier|final
specifier|static
name|Pattern
name|LT_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\[Lt\\]"
argument_list|)
decl_stmt|;
comment|// other constants
DECL|field|MONS
specifier|private
specifier|static
specifier|final
name|String
name|MONS
init|=
literal|"jan feb mar apr may jun jul aug sep oct nov dec"
decl_stmt|;
DECL|field|MONTHS
specifier|private
specifier|static
specifier|final
name|String
index|[]
name|MONTHS
init|=
block|{
literal|"January"
block|,
literal|"February"
block|,
literal|"March"
block|,
literal|"April"
block|,
literal|"May"
block|,
literal|"June"
block|,
literal|"July"
block|,
literal|"August"
block|,
literal|"September"
block|,
literal|"October"
block|,
literal|"November"
block|,
literal|"December"
block|}
decl_stmt|;
comment|/**      * Return the name of this import format.      */
DECL|method|getFormatName ()
specifier|public
name|String
name|getFormatName
parameter_list|()
block|{
return|return
literal|"CSA"
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
literal|"csa"
return|;
block|}
comment|// read a line
DECL|method|readLine (BufferedReader file)
specifier|private
name|String
name|readLine
parameter_list|(
name|BufferedReader
name|file
parameter_list|)
throws|throws
name|IOException
block|{
name|String
name|str
init|=
name|file
operator|.
name|readLine
argument_list|()
decl_stmt|;
if|if
condition|(
name|str
operator|!=
literal|null
condition|)
name|line
operator|++
expr_stmt|;
return|return
name|str
return|;
block|}
comment|// append to the "note" field
DECL|method|addNote (HashMap hm, String note)
specifier|private
name|void
name|addNote
parameter_list|(
name|HashMap
name|hm
parameter_list|,
name|String
name|note
parameter_list|)
block|{
name|StringBuffer
name|notebuf
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
if|if
condition|(
name|hm
operator|.
name|get
argument_list|(
literal|"note"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|notebuf
operator|.
name|append
argument_list|(
operator|(
name|String
operator|)
name|hm
operator|.
name|get
argument_list|(
literal|"note"
argument_list|)
argument_list|)
expr_stmt|;
name|notebuf
operator|.
name|append
argument_list|(
literal|"\n"
argument_list|)
expr_stmt|;
block|}
name|notebuf
operator|.
name|append
argument_list|(
name|note
argument_list|)
expr_stmt|;
name|hm
operator|.
name|put
argument_list|(
literal|"note"
argument_list|,
name|notebuf
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// parse the date from the Source field
DECL|method|parseDate (HashMap hm, String fstr)
specifier|private
name|String
name|parseDate
parameter_list|(
name|HashMap
name|hm
parameter_list|,
name|String
name|fstr
parameter_list|)
block|{
comment|// find LAST matching date in string
name|int
name|match
init|=
operator|-
literal|1
decl_stmt|;
name|Matcher
name|pm
init|=
name|DATE_PATTERN
operator|.
name|matcher
argument_list|(
name|fstr
argument_list|)
decl_stmt|;
while|while
condition|(
name|pm
operator|.
name|find
argument_list|()
condition|)
block|{
name|match
operator|=
name|pm
operator|.
name|start
argument_list|()
expr_stmt|;
comment|//	    System.out.println("MATCH: " + match + ": " + pm.group(0));
block|}
if|if
condition|(
name|match
operator|==
operator|-
literal|1
condition|)
block|{
comment|//	    System.out.println("NO MATCH: \"" + fstr + "\"");
return|return
name|fstr
return|;
block|}
if|if
condition|(
operator|!
name|pm
operator|.
name|find
argument_list|(
name|match
argument_list|)
condition|)
block|{
comment|//	    System.out.println("MATCH FAILED: \"" + fstr + "\"");
return|return
name|fstr
return|;
block|}
name|StringBuffer
name|date
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|String
name|day
init|=
name|pm
operator|.
name|group
argument_list|(
literal|1
argument_list|)
decl_stmt|;
if|if
condition|(
name|day
operator|==
literal|null
condition|)
name|day
operator|=
name|pm
operator|.
name|group
argument_list|(
literal|5
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|pm
operator|.
name|group
argument_list|(
literal|5
argument_list|)
operator|!=
literal|null
condition|)
return|return
name|fstr
return|;
comment|// possible day found in two places
if|if
condition|(
name|day
operator|!=
literal|null
operator|&&
operator|!
name|day
operator|.
name|equals
argument_list|(
literal|"0"
argument_list|)
condition|)
block|{
name|date
operator|.
name|append
argument_list|(
name|day
argument_list|)
expr_stmt|;
name|date
operator|.
name|append
argument_list|(
literal|" "
argument_list|)
expr_stmt|;
block|}
else|else
name|day
operator|=
literal|null
expr_stmt|;
name|String
name|mon
init|=
name|pm
operator|.
name|group
argument_list|(
literal|2
argument_list|)
decl_stmt|;
if|if
condition|(
name|mon
operator|==
literal|null
condition|)
name|mon
operator|=
name|pm
operator|.
name|group
argument_list|(
literal|4
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|pm
operator|.
name|group
argument_list|(
literal|4
argument_list|)
operator|!=
literal|null
condition|)
return|return
name|fstr
return|;
comment|// possible month found in two places
name|int
name|idx
init|=
operator|-
literal|1
decl_stmt|;
if|if
condition|(
name|mon
operator|!=
literal|null
condition|)
block|{
name|String
name|lmon
init|=
name|mon
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
name|idx
operator|=
name|MONS
operator|.
name|indexOf
argument_list|(
name|lmon
argument_list|)
expr_stmt|;
if|if
condition|(
name|idx
operator|==
operator|-
literal|1
condition|)
comment|// not legal month, error
return|return
name|fstr
return|;
name|date
operator|.
name|append
argument_list|(
name|mon
argument_list|)
expr_stmt|;
name|date
operator|.
name|append
argument_list|(
literal|" "
argument_list|)
expr_stmt|;
name|idx
operator|=
name|idx
operator|/
literal|4
expr_stmt|;
name|hm
operator|.
name|put
argument_list|(
literal|"month"
argument_list|,
name|MONTHS
index|[
name|idx
index|]
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|day
operator|!=
literal|null
condition|)
comment|// day found but not month, error
return|return
name|fstr
return|;
name|String
name|year
init|=
name|pm
operator|.
name|group
argument_list|(
literal|3
argument_list|)
decl_stmt|;
name|date
operator|.
name|append
argument_list|(
name|year
argument_list|)
expr_stmt|;
name|StringBuffer
name|note
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
if|if
condition|(
name|day
operator|!=
literal|null
operator|&&
operator|!
name|day
operator|.
name|equals
argument_list|(
literal|"0"
argument_list|)
condition|)
block|{
name|note
operator|.
name|append
argument_list|(
literal|"Source Date: "
argument_list|)
expr_stmt|;
name|note
operator|.
name|append
argument_list|(
name|date
argument_list|)
expr_stmt|;
name|note
operator|.
name|append
argument_list|(
literal|"."
argument_list|)
expr_stmt|;
name|addNote
argument_list|(
name|hm
argument_list|,
name|note
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// check if journal year matches PY field
if|if
condition|(
name|hm
operator|.
name|get
argument_list|(
literal|"year"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|String
name|oyear
init|=
operator|(
name|String
operator|)
name|hm
operator|.
name|get
argument_list|(
literal|"year"
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|year
operator|.
name|equals
argument_list|(
name|oyear
argument_list|)
condition|)
block|{
name|note
operator|.
name|setLength
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|note
operator|.
name|append
argument_list|(
literal|"Source Year: "
argument_list|)
expr_stmt|;
name|note
operator|.
name|append
argument_list|(
name|year
argument_list|)
expr_stmt|;
name|note
operator|.
name|append
argument_list|(
literal|"."
argument_list|)
expr_stmt|;
name|addNote
argument_list|(
name|hm
argument_list|,
name|note
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
comment|//		System.out.println(year + " != " + oyear);
block|}
block|}
else|else
name|hm
operator|.
name|put
argument_list|(
literal|"year"
argument_list|,
name|year
argument_list|)
expr_stmt|;
name|int
name|len
init|=
name|fstr
operator|.
name|length
argument_list|()
decl_stmt|;
name|StringBuffer
name|newf
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
if|if
condition|(
name|pm
operator|.
name|start
argument_list|()
operator|>
literal|0
condition|)
name|newf
operator|.
name|append
argument_list|(
name|fstr
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|pm
operator|.
name|start
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|pm
operator|.
name|end
argument_list|()
operator|<
name|len
condition|)
name|newf
operator|.
name|append
argument_list|(
name|fstr
operator|.
name|substring
argument_list|(
name|pm
operator|.
name|end
argument_list|()
argument_list|,
name|len
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|newf
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Check whether the source is in the correct format for this importer.      */
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
comment|// CSA records start with "DN: Database Name"
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
if|if
condition|(
name|str
operator|.
name|equals
argument_list|(
literal|"DN: Database Name"
argument_list|)
condition|)
return|return
literal|true
return|;
block|}
return|return
literal|false
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
name|HashMap
name|hm
init|=
operator|new
name|HashMap
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
name|Type
init|=
literal|null
decl_stmt|;
name|String
name|str
decl_stmt|;
name|boolean
name|first
init|=
literal|true
decl_stmt|;
name|int
name|rline
init|=
literal|1
decl_stmt|;
name|line
operator|=
literal|1
expr_stmt|;
name|str
operator|=
name|readLine
argument_list|(
name|in
argument_list|)
expr_stmt|;
while|while
condition|(
literal|true
condition|)
block|{
if|if
condition|(
name|str
operator|==
literal|null
operator|||
name|str
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
comment|// end of record
if|if
condition|(
operator|!
name|hm
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// have a record
if|if
condition|(
name|Type
operator|==
literal|null
condition|)
block|{
name|addNote
argument_list|(
name|hm
argument_list|,
literal|"Publication Type: [NOT SPECIFIED]"
argument_list|)
expr_stmt|;
name|addNote
argument_list|(
name|hm
argument_list|,
literal|"[PERHAPS NOT FULL FORMAT]"
argument_list|)
expr_stmt|;
name|Type
operator|=
literal|"article"
expr_stmt|;
block|}
comment|// post-process Journal article
if|if
condition|(
name|Type
operator|.
name|equals
argument_list|(
literal|"article"
argument_list|)
operator|&&
name|hm
operator|.
name|get
argument_list|(
literal|"booktitle"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|String
name|booktitle
init|=
operator|(
name|String
operator|)
name|hm
operator|.
name|get
argument_list|(
literal|"booktitle"
argument_list|)
decl_stmt|;
name|hm
operator|.
name|remove
argument_list|(
literal|"booktitle"
argument_list|)
expr_stmt|;
name|hm
operator|.
name|put
argument_list|(
literal|"journal"
argument_list|,
name|booktitle
argument_list|)
expr_stmt|;
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
name|hm
operator|.
name|clear
argument_list|()
expr_stmt|;
comment|// ready for next record
name|first
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|str
operator|==
literal|null
condition|)
break|break;
comment|// end of file
name|str
operator|=
name|readLine
argument_list|(
name|in
argument_list|)
expr_stmt|;
name|rline
operator|=
name|line
expr_stmt|;
continue|continue;
block|}
name|int
name|fline
init|=
name|line
decl_stmt|;
comment|// save this before reading field contents
name|Matcher
name|fm
init|=
name|FIELD_PATTERN
operator|.
name|matcher
argument_list|(
name|str
argument_list|)
decl_stmt|;
if|if
condition|(
name|fm
operator|.
name|find
argument_list|()
condition|)
block|{
comment|// save the field name (long and short)
name|String
name|fabbr
init|=
name|fm
operator|.
name|group
argument_list|(
literal|1
argument_list|)
decl_stmt|;
name|String
name|fname
init|=
name|fm
operator|.
name|group
argument_list|(
literal|2
argument_list|)
decl_stmt|;
comment|// read the contents of the field
name|sb
operator|.
name|setLength
argument_list|(
literal|0
argument_list|)
expr_stmt|;
comment|// clear the buffer
while|while
condition|(
operator|(
name|str
operator|=
name|readLine
argument_list|(
name|in
argument_list|)
operator|)
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
operator|!
name|str
operator|.
name|startsWith
argument_list|(
literal|"    "
argument_list|)
condition|)
comment|// field contents?
break|break;
comment|// nope
if|if
condition|(
name|sb
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|" "
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
name|str
operator|.
name|substring
argument_list|(
literal|4
argument_list|)
argument_list|)
expr_stmt|;
comment|// skip spaces
block|}
name|String
name|fstr
init|=
name|sb
operator|.
name|toString
argument_list|()
decl_stmt|;
if|if
condition|(
name|fstr
operator|==
literal|null
operator|||
name|fstr
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
name|int
name|line1
init|=
name|line
operator|-
literal|1
decl_stmt|;
throw|throw
operator|new
name|IOException
argument_list|(
literal|"illegal empty field at line "
operator|+
name|line1
argument_list|)
throw|;
block|}
comment|// replace [Lt] with<
name|fm
operator|=
name|LT_PATTERN
operator|.
name|matcher
argument_list|(
name|fstr
argument_list|)
expr_stmt|;
if|if
condition|(
name|fm
operator|.
name|find
argument_list|()
condition|)
name|fstr
operator|=
name|fm
operator|.
name|replaceAll
argument_list|(
literal|"<"
argument_list|)
expr_stmt|;
comment|// check for start of new record
if|if
condition|(
name|fabbr
operator|.
name|equals
argument_list|(
literal|"DN"
argument_list|)
operator|&&
name|fname
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"Database Name"
argument_list|)
condition|)
block|{
if|if
condition|(
name|first
operator|==
literal|false
condition|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
literal|"format error at line "
operator|+
name|fline
operator|+
literal|": DN out of order"
argument_list|)
throw|;
block|}
name|first
operator|=
literal|false
expr_stmt|;
name|rline
operator|=
name|fline
expr_stmt|;
comment|// save start of record
block|}
elseif|else
if|if
condition|(
name|first
operator|==
literal|true
condition|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
literal|"format error at line "
operator|+
name|fline
operator|+
literal|": missing DN"
argument_list|)
throw|;
block|}
if|if
condition|(
name|fabbr
operator|.
name|equals
argument_list|(
literal|"PT"
argument_list|)
condition|)
block|{
name|Type
operator|=
literal|null
expr_stmt|;
name|String
name|flow
init|=
name|fstr
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
name|String
index|[]
name|types
init|=
name|flow
operator|.
name|split
argument_list|(
literal|"; "
argument_list|)
decl_stmt|;
if|if
condition|(
name|types
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
literal|"article"
argument_list|)
operator|||
name|types
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
literal|"journal article"
argument_list|)
condition|)
block|{
name|Type
operator|=
literal|"article"
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|types
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
literal|"dissertation"
argument_list|)
condition|)
block|{
name|Type
operator|=
literal|"phdthesis"
expr_stmt|;
block|}
else|else
block|{
for|for
control|(
name|int
name|ii
init|=
literal|0
init|;
name|ii
operator|<
name|types
operator|.
name|length
condition|;
operator|++
name|ii
control|)
block|{
if|if
condition|(
name|types
index|[
name|ii
index|]
operator|.
name|equals
argument_list|(
literal|"conference"
argument_list|)
condition|)
block|{
name|Type
operator|=
literal|"inproceedings"
expr_stmt|;
break|break;
block|}
elseif|else
if|if
condition|(
name|types
index|[
name|ii
index|]
operator|.
name|equals
argument_list|(
literal|"book monograph"
argument_list|)
operator|&&
name|Type
operator|==
literal|null
condition|)
block|{
name|Type
operator|=
literal|"book"
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|types
index|[
name|ii
index|]
operator|.
name|equals
argument_list|(
literal|"report"
argument_list|)
operator|&&
name|Type
operator|==
literal|null
condition|)
block|{
name|Type
operator|=
literal|"techreport"
expr_stmt|;
block|}
block|}
if|if
condition|(
name|Type
operator|==
literal|null
condition|)
block|{
name|Type
operator|=
literal|"misc"
expr_stmt|;
block|}
block|}
block|}
name|String
name|ftype
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|fabbr
operator|.
name|equals
argument_list|(
literal|"AB"
argument_list|)
condition|)
name|ftype
operator|=
literal|"abstract"
expr_stmt|;
elseif|else
if|if
condition|(
name|fabbr
operator|.
name|equals
argument_list|(
literal|"AF"
argument_list|)
condition|)
name|ftype
operator|=
literal|"affiliation"
expr_stmt|;
elseif|else
if|if
condition|(
name|fabbr
operator|.
name|equals
argument_list|(
literal|"AU"
argument_list|)
condition|)
name|ftype
operator|=
literal|"author"
expr_stmt|;
elseif|else
if|if
condition|(
name|fabbr
operator|.
name|equals
argument_list|(
literal|"CA"
argument_list|)
condition|)
name|ftype
operator|=
literal|"organization"
expr_stmt|;
elseif|else
if|if
condition|(
name|fabbr
operator|.
name|equals
argument_list|(
literal|"DE"
argument_list|)
condition|)
name|ftype
operator|=
literal|"keywords"
expr_stmt|;
elseif|else
if|if
condition|(
name|fabbr
operator|.
name|equals
argument_list|(
literal|"DO"
argument_list|)
condition|)
name|ftype
operator|=
literal|"doi"
expr_stmt|;
elseif|else
if|if
condition|(
name|fabbr
operator|.
name|equals
argument_list|(
literal|"ED"
argument_list|)
condition|)
name|ftype
operator|=
literal|"editor"
expr_stmt|;
elseif|else
if|if
condition|(
name|fabbr
operator|.
name|equals
argument_list|(
literal|"IB"
argument_list|)
condition|)
name|ftype
operator|=
literal|"ISBN"
expr_stmt|;
elseif|else
if|if
condition|(
name|fabbr
operator|.
name|equals
argument_list|(
literal|"IS"
argument_list|)
condition|)
name|ftype
operator|=
literal|"ISSN"
expr_stmt|;
elseif|else
if|if
condition|(
name|fabbr
operator|.
name|equals
argument_list|(
literal|"JN"
argument_list|)
condition|)
name|ftype
operator|=
literal|"journal"
expr_stmt|;
elseif|else
if|if
condition|(
name|fabbr
operator|.
name|equals
argument_list|(
literal|"LA"
argument_list|)
condition|)
name|ftype
operator|=
literal|"language"
expr_stmt|;
elseif|else
if|if
condition|(
name|fabbr
operator|.
name|equals
argument_list|(
literal|"PB"
argument_list|)
condition|)
name|ftype
operator|=
literal|"publisher"
expr_stmt|;
elseif|else
if|if
condition|(
name|fabbr
operator|.
name|equals
argument_list|(
literal|"PY"
argument_list|)
condition|)
block|{
name|ftype
operator|=
literal|"year"
expr_stmt|;
if|if
condition|(
name|hm
operator|.
name|get
argument_list|(
literal|"year"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|String
name|oyear
init|=
operator|(
name|String
operator|)
name|hm
operator|.
name|get
argument_list|(
literal|"year"
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|fstr
operator|.
name|equals
argument_list|(
name|oyear
argument_list|)
condition|)
block|{
name|StringBuffer
name|note
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|note
operator|.
name|append
argument_list|(
literal|"Source Year: "
argument_list|)
expr_stmt|;
name|note
operator|.
name|append
argument_list|(
name|oyear
argument_list|)
expr_stmt|;
name|note
operator|.
name|append
argument_list|(
literal|"."
argument_list|)
expr_stmt|;
name|addNote
argument_list|(
name|hm
argument_list|,
name|note
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
comment|//			    System.out.println(fstr + " != " + oyear);
block|}
block|}
block|}
elseif|else
if|if
condition|(
name|fabbr
operator|.
name|equals
argument_list|(
literal|"RL"
argument_list|)
condition|)
block|{
name|ftype
operator|=
literal|"url"
expr_stmt|;
name|String
index|[]
name|lines
init|=
name|fstr
operator|.
name|split
argument_list|(
literal|" "
argument_list|)
decl_stmt|;
name|StringBuffer
name|urls
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
name|lines
operator|.
name|length
condition|;
operator|++
name|ii
control|)
block|{
if|if
condition|(
name|lines
index|[
name|ii
index|]
operator|.
name|startsWith
argument_list|(
literal|"[URL:"
argument_list|)
condition|)
name|urls
operator|.
name|append
argument_list|(
name|lines
index|[
name|ii
index|]
operator|.
name|substring
argument_list|(
literal|5
argument_list|)
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|lines
index|[
name|ii
index|]
operator|.
name|endsWith
argument_list|(
literal|"]"
argument_list|)
condition|)
block|{
name|int
name|len
init|=
name|lines
index|[
name|ii
index|]
operator|.
name|length
argument_list|()
decl_stmt|;
name|urls
operator|.
name|append
argument_list|(
name|lines
index|[
name|ii
index|]
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|len
operator|-
literal|1
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|ii
operator|<
name|lines
operator|.
name|length
operator|-
literal|1
condition|)
name|urls
operator|.
name|append
argument_list|(
literal|"\n"
argument_list|)
expr_stmt|;
block|}
else|else
name|urls
operator|.
name|append
argument_list|(
name|lines
index|[
name|ii
index|]
argument_list|)
expr_stmt|;
block|}
name|fstr
operator|=
name|urls
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|fabbr
operator|.
name|equals
argument_list|(
literal|"SO"
argument_list|)
condition|)
block|{
name|ftype
operator|=
literal|"booktitle"
expr_stmt|;
comment|// see if we can extract journal information
comment|// compact vol(no):page-page:
name|Matcher
name|pm
init|=
name|VOLNOPP_PATTERN
operator|.
name|matcher
argument_list|(
name|fstr
argument_list|)
decl_stmt|;
if|if
condition|(
name|pm
operator|.
name|find
argument_list|()
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"volume"
argument_list|,
name|pm
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|hm
operator|.
name|put
argument_list|(
literal|"number"
argument_list|,
name|pm
operator|.
name|group
argument_list|(
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|hm
operator|.
name|put
argument_list|(
literal|"pages"
argument_list|,
name|pm
operator|.
name|group
argument_list|(
literal|3
argument_list|)
argument_list|)
expr_stmt|;
name|fstr
operator|=
name|pm
operator|.
name|replaceFirst
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
comment|// pages
name|pm
operator|=
name|PAGES_PATTERN
operator|.
name|matcher
argument_list|(
name|fstr
argument_list|)
expr_stmt|;
name|StringBuffer
name|pages
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
while|while
condition|(
name|pm
operator|.
name|find
argument_list|()
condition|)
block|{
if|if
condition|(
name|pages
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|pages
operator|.
name|append
argument_list|(
literal|","
argument_list|)
expr_stmt|;
name|String
name|pp
init|=
name|pm
operator|.
name|group
argument_list|(
literal|1
argument_list|)
decl_stmt|;
if|if
condition|(
name|pp
operator|==
literal|null
condition|)
name|pp
operator|=
name|pm
operator|.
name|group
argument_list|(
literal|2
argument_list|)
expr_stmt|;
if|if
condition|(
name|pp
operator|==
literal|null
condition|)
name|pp
operator|=
name|pm
operator|.
name|group
argument_list|(
literal|3
argument_list|)
expr_stmt|;
name|pages
operator|.
name|append
argument_list|(
name|pp
argument_list|)
expr_stmt|;
name|fstr
operator|=
name|pm
operator|.
name|replaceFirst
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|pm
operator|=
name|PAGES_PATTERN
operator|.
name|matcher
argument_list|(
name|fstr
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|pages
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|hm
operator|.
name|put
argument_list|(
literal|"pages"
argument_list|,
name|pages
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
comment|// volume:
name|pm
operator|=
name|VOLUME_PATTERN
operator|.
name|matcher
argument_list|(
name|fstr
argument_list|)
expr_stmt|;
if|if
condition|(
name|pm
operator|.
name|find
argument_list|()
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"volume"
argument_list|,
name|pm
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|fstr
operator|=
name|pm
operator|.
name|replaceFirst
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
comment|// number:
name|pm
operator|=
name|NUMBER_PATTERN
operator|.
name|matcher
argument_list|(
name|fstr
argument_list|)
expr_stmt|;
if|if
condition|(
name|pm
operator|.
name|find
argument_list|()
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"number"
argument_list|,
name|pm
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|fstr
operator|=
name|pm
operator|.
name|replaceFirst
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
comment|// journal date:
name|fstr
operator|=
name|parseDate
argument_list|(
name|hm
argument_list|,
name|fstr
argument_list|)
expr_stmt|;
comment|// strip trailing whitespace
name|Pattern
name|pp
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|",?\\s*$"
argument_list|)
decl_stmt|;
name|pm
operator|=
name|pp
operator|.
name|matcher
argument_list|(
name|fstr
argument_list|)
expr_stmt|;
if|if
condition|(
name|pm
operator|.
name|find
argument_list|()
condition|)
name|fstr
operator|=
name|pm
operator|.
name|replaceFirst
argument_list|(
literal|""
argument_list|)
expr_stmt|;
if|if
condition|(
name|fstr
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
continue|continue;
comment|//		    System.out.println("SOURCE: \"" + fstr + "\"");
block|}
elseif|else
if|if
condition|(
name|fabbr
operator|.
name|equals
argument_list|(
literal|"TI"
argument_list|)
condition|)
name|ftype
operator|=
literal|"title"
expr_stmt|;
elseif|else
if|if
condition|(
name|fabbr
operator|.
name|equals
argument_list|(
literal|"RE"
argument_list|)
condition|)
continue|continue;
comment|// throw away References
if|if
condition|(
name|ftype
operator|!=
literal|null
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
name|ftype
argument_list|,
name|fstr
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|StringBuffer
name|val
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|val
operator|.
name|append
argument_list|(
name|fname
argument_list|)
expr_stmt|;
name|val
operator|.
name|append
argument_list|(
literal|": "
argument_list|)
expr_stmt|;
name|val
operator|.
name|append
argument_list|(
name|fstr
argument_list|)
expr_stmt|;
name|val
operator|.
name|append
argument_list|(
literal|"."
argument_list|)
expr_stmt|;
name|addNote
argument_list|(
name|hm
argument_list|,
name|val
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
else|else
name|str
operator|=
name|readLine
argument_list|(
name|in
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

