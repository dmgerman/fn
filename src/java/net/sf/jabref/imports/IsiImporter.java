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
name|Iterator
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
name|BibtexFields
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

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|CaseChanger
import|;
end_import

begin_comment
comment|/**  * Importer for the ISI Web of Science format.  *   * Documentation about ISI WOS format:  *   *<ul>  *<li>http://wos.isitrial.com/help/helpprn.html</li>  *</ul>  *   * Todo:  *<ul>  *<li>Check compatibility with other ISI2Bib tools like:  * http://www-lab.imr.tohoku.ac.jp/~t-nissie/computer/software/isi/ or  * http://www.tug.org/tex-archive/biblio/bibtex/utils/isi2bibtex/isi2bibtex or  * http://web.mit.edu/emilio/www/utils.html</li>  *<li>Deal with capitalization correctly</li>  *</ul>  *   * @author $Author$  * @version $Revision$ ($Date$)  *   */
end_comment

begin_class
DECL|class|IsiImporter
specifier|public
class|class
name|IsiImporter
extends|extends
name|ImportFormat
block|{
comment|/** 	 * Return the name of this import format. 	 */
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
comment|/* 	 * (non-Javadoc) 	 *  	 * @see net.sf.jabref.imports.ImportFormat#getCLIId() 	 */
DECL|method|getCLIId ()
specifier|public
name|String
name|getCLIId
parameter_list|()
block|{
return|return
literal|"isi"
return|;
block|}
comment|/** 	 * Check whether the source is in the correct format for this importer. 	 */
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
comment|// Our strategy is to look for the "PY<year>" line.
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
name|Pattern
name|pat1
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"PY \\d{4}"
argument_list|)
decl_stmt|;
comment|// was PY \\\\d{4}? before
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
comment|// The following line gives false positives for RIS files, so it
comment|// should
comment|// not be uncommented. The hypen is a characteristic of the RIS
comment|// format.
comment|// str = str.replace(" - ", "");
if|if
condition|(
name|pat1
operator|.
name|matcher
argument_list|(
name|str
argument_list|)
operator|.
name|find
argument_list|()
condition|)
return|return
literal|true
return|;
block|}
return|return
literal|false
return|;
block|}
DECL|field|subsupPattern
specifier|static
name|Pattern
name|subsupPattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"/(sub|sup)\\s+(.*?)\\s*/"
argument_list|)
decl_stmt|;
DECL|method|processSubSup (HashMap map)
specifier|static
specifier|public
name|void
name|processSubSup
parameter_list|(
name|HashMap
name|map
parameter_list|)
block|{
name|String
index|[]
name|subsup
init|=
block|{
literal|"title"
block|,
literal|"abstract"
block|,
literal|"review"
block|,
literal|"notes"
block|}
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
name|subsup
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|map
operator|.
name|containsKey
argument_list|(
name|subsup
index|[
name|i
index|]
argument_list|)
condition|)
block|{
name|Matcher
name|m
init|=
name|subsupPattern
operator|.
name|matcher
argument_list|(
operator|(
name|String
operator|)
name|map
operator|.
name|get
argument_list|(
name|subsup
index|[
name|i
index|]
argument_list|)
argument_list|)
decl_stmt|;
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
while|while
condition|(
name|m
operator|.
name|find
argument_list|()
condition|)
block|{
name|String
name|group2
init|=
name|m
operator|.
name|group
argument_list|(
literal|2
argument_list|)
decl_stmt|;
if|if
condition|(
name|group2
operator|.
name|length
argument_list|()
operator|>
literal|1
condition|)
block|{
name|group2
operator|=
literal|"{"
operator|+
name|group2
operator|+
literal|"}"
expr_stmt|;
block|}
if|if
condition|(
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
operator|.
name|equals
argument_list|(
literal|"sub"
argument_list|)
condition|)
block|{
name|m
operator|.
name|appendReplacement
argument_list|(
name|sb
argument_list|,
literal|"\\$_"
operator|+
name|group2
operator|+
literal|"\\$"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|m
operator|.
name|appendReplacement
argument_list|(
name|sb
argument_list|,
literal|"\\$^"
operator|+
name|group2
operator|+
literal|"\\$"
argument_list|)
expr_stmt|;
block|}
block|}
name|m
operator|.
name|appendTail
argument_list|(
name|sb
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
name|subsup
index|[
name|i
index|]
argument_list|,
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|processCapitalization (HashMap map)
specifier|static
specifier|public
name|void
name|processCapitalization
parameter_list|(
name|HashMap
name|map
parameter_list|)
block|{
name|String
index|[]
name|subsup
init|=
block|{
literal|"title"
block|,
literal|"journal"
block|,
literal|"publisher"
block|}
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
name|subsup
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|map
operator|.
name|containsKey
argument_list|(
name|subsup
index|[
name|i
index|]
argument_list|)
condition|)
block|{
name|String
name|s
init|=
operator|(
name|String
operator|)
name|map
operator|.
name|get
argument_list|(
name|subsup
index|[
name|i
index|]
argument_list|)
decl_stmt|;
if|if
condition|(
name|s
operator|.
name|toUpperCase
argument_list|()
operator|.
name|equals
argument_list|(
name|s
argument_list|)
condition|)
block|{
name|s
operator|=
name|CaseChanger
operator|.
name|changeCase
argument_list|(
name|s
argument_list|,
name|CaseChanger
operator|.
name|UPPER_EACH_FIRST
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
name|subsup
index|[
name|i
index|]
argument_list|,
name|s
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
comment|/** 	 * Parse the entries in the source, and return a List of BibtexEntry 	 * objects. 	 */
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
comment|// Pattern fieldPattern = Pattern.compile("^AU |^TI |^SO |^DT |^C1 |^AB
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
name|sb
operator|.
name|append
argument_list|(
literal|"::"
argument_list|)
operator|.
name|append
argument_list|(
name|str
argument_list|)
expr_stmt|;
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
name|trim
argument_list|()
argument_list|)
expr_stmt|;
comment|// remove the initial spaces
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
name|HashMap
name|hm
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
comment|// skip the first entry as it is either empty or has document header
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
decl_stmt|;
name|String
name|PT
init|=
literal|""
decl_stmt|;
name|String
name|pages
init|=
literal|""
decl_stmt|;
name|hm
operator|.
name|clear
argument_list|()
expr_stmt|;
name|nextField
label|:
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
comment|// empty field don't do anything
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
comment|// this is Java 1.5.0 code:
comment|// fields[j] = fields[j].replace(" - ", "");
comment|// TODO: switch to 1.5.0 some day; until then, use 1.4.2 code:
name|fields
index|[
name|j
index|]
operator|=
name|fields
index|[
name|j
index|]
operator|.
name|replaceAll
argument_list|(
literal|" - "
argument_list|,
literal|""
argument_list|)
expr_stmt|;
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
operator|.
name|trim
argument_list|()
decl_stmt|;
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
operator|.
name|replaceAll
argument_list|(
literal|"J"
argument_list|,
literal|"article"
argument_list|)
expr_stmt|;
name|Type
operator|=
literal|"article"
expr_stmt|;
comment|// make all of them PT?
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
if|if
condition|(
literal|"CONF"
operator|.
name|equals
argument_list|(
name|value
argument_list|)
condition|)
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
block|{
name|String
name|author
init|=
name|isiAuthorsConvert
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
decl_stmt|;
comment|// if there is already someone there then append with "and"
if|if
condition|(
name|hm
operator|.
name|get
argument_list|(
literal|"author"
argument_list|)
operator|!=
literal|null
condition|)
name|author
operator|=
operator|(
name|String
operator|)
name|hm
operator|.
name|get
argument_list|(
literal|"author"
argument_list|)
operator|+
literal|" and "
operator|+
name|author
expr_stmt|;
name|hm
operator|.
name|put
argument_list|(
literal|"author"
argument_list|,
name|author
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
name|hm
operator|.
name|put
argument_list|(
literal|"journal"
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
literal|"PU"
argument_list|)
condition|)
name|hm
operator|.
name|put
argument_list|(
literal|"publisher"
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
literal|"PD"
argument_list|)
condition|)
block|{
name|String
index|[]
name|parts
init|=
name|value
operator|.
name|split
argument_list|(
literal|" "
argument_list|)
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
name|parts
operator|.
name|length
condition|;
name|ii
operator|++
control|)
block|{
if|if
condition|(
name|Globals
operator|.
name|MONTH_STRINGS
operator|.
name|containsKey
argument_list|(
name|parts
index|[
name|ii
index|]
operator|.
name|toLowerCase
argument_list|()
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"month"
argument_list|,
literal|"#"
operator|+
name|parts
index|[
name|ii
index|]
operator|.
name|toLowerCase
argument_list|()
operator|+
literal|"#"
argument_list|)
expr_stmt|;
continue|continue
name|nextField
continue|;
block|}
block|}
comment|// Try two digit month
for|for
control|(
name|int
name|ii
init|=
literal|0
init|;
name|ii
operator|<
name|parts
operator|.
name|length
condition|;
name|ii
operator|++
control|)
block|{
name|int
name|number
decl_stmt|;
try|try
block|{
name|number
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|parts
index|[
name|ii
index|]
argument_list|)
expr_stmt|;
if|if
condition|(
name|number
operator|>=
literal|1
operator|&&
name|number
operator|<=
literal|12
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"month"
argument_list|,
literal|"#"
operator|+
name|Globals
operator|.
name|MONTHS
index|[
name|number
operator|-
literal|1
index|]
operator|+
literal|"#"
argument_list|)
expr_stmt|;
continue|continue
name|nextField
continue|;
block|}
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{  						}
block|}
block|}
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
name|Type
operator|.
name|equals
argument_list|(
literal|"Review"
argument_list|)
condition|)
block|{
name|Type
operator|=
literal|"article"
expr_stmt|;
comment|// set "Review" in Note/Comment?
block|}
elseif|else
if|if
condition|(
name|Type
operator|.
name|startsWith
argument_list|(
literal|"Article"
argument_list|)
operator|||
name|Type
operator|.
name|startsWith
argument_list|(
literal|"Journal"
argument_list|)
operator|||
name|PT
operator|.
name|equals
argument_list|(
literal|"article"
argument_list|)
condition|)
block|{
name|Type
operator|=
literal|"article"
expr_stmt|;
continue|continue;
block|}
else|else
block|{
name|Type
operator|=
literal|"misc"
expr_stmt|;
block|}
block|}
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
block|{
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
else|else
block|{
comment|// Preserve all other entries except
if|if
condition|(
name|beg
operator|.
name|equals
argument_list|(
literal|"ER"
argument_list|)
operator|||
name|beg
operator|.
name|equals
argument_list|(
literal|"EF"
argument_list|)
operator|||
name|beg
operator|.
name|equals
argument_list|(
literal|"VR"
argument_list|)
operator|||
name|beg
operator|.
name|equals
argument_list|(
literal|"FN"
argument_list|)
condition|)
continue|continue
name|nextField
continue|;
name|hm
operator|.
name|put
argument_list|(
name|beg
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|!
literal|""
operator|.
name|equals
argument_list|(
name|pages
argument_list|)
condition|)
name|hm
operator|.
name|put
argument_list|(
literal|"pages"
argument_list|,
name|pages
argument_list|)
expr_stmt|;
comment|// Skip empty entries
if|if
condition|(
name|hm
operator|.
name|size
argument_list|()
operator|==
literal|0
condition|)
continue|continue;
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
comment|// Remove empty fields:
name|ArrayList
name|toRemove
init|=
operator|new
name|ArrayList
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|it
init|=
name|hm
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Object
name|key
init|=
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|content
init|=
operator|(
name|String
operator|)
name|hm
operator|.
name|get
argument_list|(
name|key
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|content
operator|==
literal|null
operator|)
operator|||
operator|(
name|content
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
operator|)
condition|)
name|toRemove
operator|.
name|add
argument_list|(
name|key
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|iterator
init|=
name|toRemove
operator|.
name|iterator
argument_list|()
init|;
name|iterator
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|hm
operator|.
name|remove
argument_list|(
name|iterator
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// Polish entries
name|processSubSup
argument_list|(
name|hm
argument_list|)
expr_stmt|;
name|processCapitalization
argument_list|(
name|hm
argument_list|)
expr_stmt|;
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
comment|/** 	 * Will expand ISI first names. 	 *  	 * Fixed bug from: 	 * http://sourceforge.net/tracker/index.php?func=detail&aid=1542552&group_id=92314&atid=600306 	 *  	 */
DECL|method|isiAuthorConvert (String author)
specifier|public
specifier|static
name|String
name|isiAuthorConvert
parameter_list|(
name|String
name|author
parameter_list|)
block|{
name|String
index|[]
name|s
init|=
name|author
operator|.
name|split
argument_list|(
literal|","
argument_list|)
decl_stmt|;
if|if
condition|(
name|s
operator|.
name|length
operator|!=
literal|2
condition|)
return|return
name|author
return|;
name|String
name|last
init|=
name|s
index|[
literal|0
index|]
operator|.
name|trim
argument_list|()
decl_stmt|;
name|String
name|first
init|=
name|s
index|[
literal|1
index|]
operator|.
name|trim
argument_list|()
decl_stmt|;
name|first
operator|=
name|first
operator|.
name|replaceAll
argument_list|(
literal|"\\.|\\s"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|last
argument_list|)
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|first
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|first
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|"."
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|<
name|first
operator|.
name|length
argument_list|()
operator|-
literal|1
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|" "
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|isiAuthorsConvert (String[] authors)
specifier|public
specifier|static
name|String
index|[]
name|isiAuthorsConvert
parameter_list|(
name|String
index|[]
name|authors
parameter_list|)
block|{
name|String
index|[]
name|result
init|=
operator|new
name|String
index|[
name|authors
operator|.
name|length
index|]
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
name|result
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|result
index|[
name|i
index|]
operator|=
name|isiAuthorConvert
argument_list|(
name|authors
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
DECL|method|isiAuthorsConvert (String authors)
specifier|public
specifier|static
name|String
name|isiAuthorsConvert
parameter_list|(
name|String
name|authors
parameter_list|)
block|{
name|String
index|[]
name|s
init|=
name|isiAuthorsConvert
argument_list|(
name|authors
operator|.
name|split
argument_list|(
literal|" and "
argument_list|)
argument_list|)
decl_stmt|;
return|return
name|Util
operator|.
name|join
argument_list|(
name|s
argument_list|,
literal|" and "
argument_list|,
literal|0
argument_list|,
name|s
operator|.
name|length
argument_list|)
return|;
block|}
block|}
end_class

end_unit

