begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.importer.fileformat
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
operator|.
name|fileformat
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
name|importer
operator|.
name|ImportInspector
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
name|importer
operator|.
name|OutputPrinter
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
name|importer
operator|.
name|fetcher
operator|.
name|DOItoBibTeXFetcher
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
name|logic
operator|.
name|util
operator|.
name|DOI
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
name|model
operator|.
name|entry
operator|.
name|BibEntry
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
name|model
operator|.
name|entry
operator|.
name|BibtexEntryTypes
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
name|model
operator|.
name|entry
operator|.
name|EntryType
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|pdfbox
operator|.
name|pdmodel
operator|.
name|PDDocument
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|pdfbox
operator|.
name|util
operator|.
name|PDFTextStripper
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|common
operator|.
name|base
operator|.
name|Strings
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
name|io
operator|.
name|StringWriter
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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
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

begin_comment
comment|/**  * PdfContentImporter parses data of the first page of the PDF and creates a BibTeX entry.  *<p>  * Currently, Springer and IEEE formats are supported.  *<p>  * Integrating XMP support is future work  */
end_comment

begin_class
DECL|class|PdfContentImporter
specifier|public
class|class
name|PdfContentImporter
extends|extends
name|ImportFormat
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|PdfContentImporter
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|YEAR_EXTRACT_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|YEAR_EXTRACT_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\d{4}"
argument_list|)
decl_stmt|;
DECL|field|doiToBibTeXFetcher
specifier|private
specifier|static
specifier|final
name|DOItoBibTeXFetcher
name|doiToBibTeXFetcher
init|=
operator|new
name|DOItoBibTeXFetcher
argument_list|()
decl_stmt|;
comment|// input lines into several lines
DECL|field|lines
specifier|private
name|String
index|[]
name|lines
decl_stmt|;
comment|// current index in lines
DECL|field|i
specifier|private
name|int
name|i
decl_stmt|;
DECL|field|curString
specifier|private
name|String
name|curString
decl_stmt|;
DECL|field|year
specifier|private
name|String
name|year
decl_stmt|;
comment|/**      * Removes all non-letter characters at the end      *<p>      * EXCEPTION: a closing bracket is NOT removed      *</p>      *<p>      * TODO: Additionally replace multiple subsequent spaces by one space, which will cause a rename of this method      *</p>      */
DECL|method|removeNonLettersAtEnd (String input)
specifier|private
specifier|static
name|String
name|removeNonLettersAtEnd
parameter_list|(
name|String
name|input
parameter_list|)
block|{
name|input
operator|=
name|input
operator|.
name|trim
argument_list|()
expr_stmt|;
if|if
condition|(
name|input
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
name|input
return|;
block|}
name|char
name|lastC
init|=
name|input
operator|.
name|charAt
argument_list|(
name|input
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
decl_stmt|;
while|while
condition|(
operator|!
name|Character
operator|.
name|isLetter
argument_list|(
name|lastC
argument_list|)
operator|&&
operator|(
name|lastC
operator|!=
literal|')'
operator|)
condition|)
block|{
comment|// if there is an asterix, a dot or something else at the end: remove it
name|input
operator|=
name|input
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|input
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
name|input
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
break|break;
block|}
else|else
block|{
name|lastC
operator|=
name|input
operator|.
name|charAt
argument_list|(
name|input
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|input
return|;
block|}
DECL|method|streamlineNames (String names)
specifier|private
specifier|static
name|String
name|streamlineNames
parameter_list|(
name|String
name|names
parameter_list|)
block|{
comment|// TODO: replace with AuthorsFormatter?!
name|String
name|res
decl_stmt|;
comment|// supported formats:
comment|//   Matthias Schrepfer1, Johannes Wolf1, Jan Mendling1, and Hajo A. Reijers2
if|if
condition|(
name|names
operator|.
name|contains
argument_list|(
literal|","
argument_list|)
condition|)
block|{
name|String
index|[]
name|splitNames
init|=
name|names
operator|.
name|split
argument_list|(
literal|","
argument_list|)
decl_stmt|;
name|res
operator|=
literal|""
expr_stmt|;
name|boolean
name|isFirst
init|=
literal|true
decl_stmt|;
for|for
control|(
name|String
name|splitName
range|:
name|splitNames
control|)
block|{
name|String
name|curName
init|=
name|removeNonLettersAtEnd
argument_list|(
name|splitName
argument_list|)
decl_stmt|;
if|if
condition|(
name|curName
operator|.
name|indexOf
argument_list|(
literal|"and"
argument_list|)
operator|==
literal|0
condition|)
block|{
comment|// skip possible ands between names
name|curName
operator|=
name|curName
operator|.
name|substring
argument_list|(
literal|3
argument_list|)
operator|.
name|trim
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|int
name|posAnd
init|=
name|curName
operator|.
name|indexOf
argument_list|(
literal|" and "
argument_list|)
decl_stmt|;
if|if
condition|(
name|posAnd
operator|>=
literal|0
condition|)
block|{
name|String
name|nameBefore
init|=
name|curName
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|posAnd
argument_list|)
decl_stmt|;
comment|// cannot be first name as "," is contained in the string
name|res
operator|=
name|res
operator|.
name|concat
argument_list|(
literal|" and "
argument_list|)
operator|.
name|concat
argument_list|(
name|removeNonLettersAtEnd
argument_list|(
name|nameBefore
argument_list|)
argument_list|)
expr_stmt|;
name|curName
operator|=
name|curName
operator|.
name|substring
argument_list|(
name|posAnd
operator|+
literal|5
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
name|curName
argument_list|)
condition|)
block|{
if|if
condition|(
literal|"et al."
operator|.
name|equalsIgnoreCase
argument_list|(
name|curName
argument_list|)
condition|)
block|{
name|curName
operator|=
literal|"others"
expr_stmt|;
block|}
if|if
condition|(
name|isFirst
condition|)
block|{
name|isFirst
operator|=
literal|false
expr_stmt|;
block|}
else|else
block|{
name|res
operator|=
name|res
operator|.
name|concat
argument_list|(
literal|" and "
argument_list|)
expr_stmt|;
block|}
name|res
operator|=
name|res
operator|.
name|concat
argument_list|(
name|curName
argument_list|)
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
comment|// assumption: names separated by space
name|String
index|[]
name|splitNames
init|=
name|names
operator|.
name|split
argument_list|(
literal|" "
argument_list|)
decl_stmt|;
if|if
condition|(
name|splitNames
operator|.
name|length
operator|==
literal|0
condition|)
block|{
comment|// empty names... something was really wrong...
return|return
literal|""
return|;
block|}
name|boolean
name|workedOnFirstOrMiddle
init|=
literal|false
decl_stmt|;
name|boolean
name|isFirst
init|=
literal|true
decl_stmt|;
name|int
name|i
init|=
literal|0
decl_stmt|;
name|res
operator|=
literal|""
expr_stmt|;
do|do
block|{
if|if
condition|(
name|workedOnFirstOrMiddle
condition|)
block|{
comment|// last item was a first or a middle name
comment|// we have to check whether we are on a middle name
comment|// if not, just add the item as last name and add an "and"
if|if
condition|(
name|splitNames
index|[
name|i
index|]
operator|.
name|contains
argument_list|(
literal|"."
argument_list|)
condition|)
block|{
comment|// we found a middle name
name|res
operator|=
name|res
operator|.
name|concat
argument_list|(
name|splitNames
index|[
name|i
index|]
argument_list|)
operator|.
name|concat
argument_list|(
literal|" "
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// last name found
name|res
operator|=
name|res
operator|.
name|concat
argument_list|(
name|removeNonLettersAtEnd
argument_list|(
name|splitNames
index|[
name|i
index|]
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|splitNames
index|[
name|i
index|]
operator|.
name|isEmpty
argument_list|()
operator|&&
name|Character
operator|.
name|isLowerCase
argument_list|(
name|splitNames
index|[
name|i
index|]
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
argument_list|)
condition|)
block|{
comment|// it is probably be "van", "vom", ...
comment|// we just rely on the fact that these things are written in lower case letters
comment|// do NOT finish name
name|res
operator|=
name|res
operator|.
name|concat
argument_list|(
literal|" "
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// finish this name
name|workedOnFirstOrMiddle
operator|=
literal|false
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
if|if
condition|(
literal|"and"
operator|.
name|equalsIgnoreCase
argument_list|(
name|splitNames
index|[
name|i
index|]
argument_list|)
condition|)
block|{
comment|// do nothing, just increment i at the end of this iteration
block|}
else|else
block|{
if|if
condition|(
name|isFirst
condition|)
block|{
name|isFirst
operator|=
literal|false
expr_stmt|;
block|}
else|else
block|{
name|res
operator|=
name|res
operator|.
name|concat
argument_list|(
literal|" and "
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
literal|"et"
operator|.
name|equalsIgnoreCase
argument_list|(
name|splitNames
index|[
name|i
index|]
argument_list|)
operator|&&
operator|(
name|splitNames
operator|.
name|length
operator|>
operator|(
name|i
operator|+
literal|1
operator|)
operator|)
operator|&&
literal|"al."
operator|.
name|equalsIgnoreCase
argument_list|(
name|splitNames
index|[
name|i
operator|+
literal|1
index|]
argument_list|)
condition|)
block|{
name|res
operator|=
name|res
operator|.
name|concat
argument_list|(
literal|"others"
argument_list|)
expr_stmt|;
break|break;
block|}
else|else
block|{
name|res
operator|=
name|res
operator|.
name|concat
argument_list|(
name|splitNames
index|[
name|i
index|]
argument_list|)
operator|.
name|concat
argument_list|(
literal|" "
argument_list|)
expr_stmt|;
name|workedOnFirstOrMiddle
operator|=
literal|true
expr_stmt|;
block|}
block|}
block|}
name|i
operator|++
expr_stmt|;
block|}
do|while
condition|(
name|i
operator|<
name|splitNames
operator|.
name|length
condition|)
do|;
block|}
return|return
name|res
return|;
block|}
DECL|method|streamlineTitle (String title)
specifier|private
specifier|static
name|String
name|streamlineTitle
parameter_list|(
name|String
name|title
parameter_list|)
block|{
return|return
name|removeNonLettersAtEnd
argument_list|(
name|title
argument_list|)
return|;
block|}
annotation|@
name|Override
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
literal|false
return|;
block|}
annotation|@
name|Override
DECL|method|importEntries (InputStream in, OutputPrinter status)
specifier|public
name|List
argument_list|<
name|BibEntry
argument_list|>
name|importEntries
parameter_list|(
name|InputStream
name|in
parameter_list|,
name|OutputPrinter
name|status
parameter_list|)
throws|throws
name|IOException
block|{
specifier|final
name|ArrayList
argument_list|<
name|BibEntry
argument_list|>
name|result
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
literal|1
argument_list|)
decl_stmt|;
try|try
init|(
name|PDDocument
name|document
init|=
name|PDDocument
operator|.
name|load
argument_list|(
name|in
argument_list|)
init|)
block|{
if|if
condition|(
name|document
operator|.
name|isEncrypted
argument_list|()
condition|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Encrypted documents are not supported"
argument_list|)
expr_stmt|;
return|return
name|result
return|;
block|}
name|String
name|firstPageContents
init|=
name|getFirstPageContents
argument_list|(
name|document
argument_list|)
decl_stmt|;
name|Optional
argument_list|<
name|DOI
argument_list|>
name|doi
init|=
name|DOI
operator|.
name|findInText
argument_list|(
name|firstPageContents
argument_list|)
decl_stmt|;
if|if
condition|(
name|doi
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|ImportInspector
name|inspector
init|=
operator|new
name|ImportInspector
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|toFront
parameter_list|()
block|{
comment|// Do nothing
block|}
annotation|@
name|Override
specifier|public
name|void
name|setProgress
parameter_list|(
name|int
name|current
parameter_list|,
name|int
name|max
parameter_list|)
block|{
comment|// Do nothing
block|}
annotation|@
name|Override
specifier|public
name|void
name|addEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
comment|// add the entry to the result object
name|result
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
decl_stmt|;
name|doiToBibTeXFetcher
operator|.
name|processQuery
argument_list|(
name|doi
operator|.
name|get
argument_list|()
operator|.
name|getDOI
argument_list|()
argument_list|,
name|inspector
argument_list|,
name|status
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|result
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
name|result
return|;
block|}
block|}
comment|// idea: split[] contains the different lines
comment|// blocks are separated by empty lines
comment|// treat each block
comment|//   or do special treatment at authors (which are not broken)
comment|//   therefore, we do a line-based and not a block-based splitting
comment|// i points to the current line
comment|// curString (mostly) contains the current block
comment|//   the different lines are joined into one and thereby separated by " "
name|lines
operator|=
name|firstPageContents
operator|.
name|split
argument_list|(
name|System
operator|.
name|lineSeparator
argument_list|()
argument_list|)
expr_stmt|;
name|proceedToNextNonEmptyLine
argument_list|()
expr_stmt|;
if|if
condition|(
name|i
operator|>=
name|lines
operator|.
name|length
condition|)
block|{
comment|// PDF could not be parsed or is empty
comment|// return empty list
return|return
name|result
return|;
block|}
comment|// we start at the current line
name|curString
operator|=
name|lines
index|[
name|i
index|]
expr_stmt|;
comment|// i might get incremented later and curString modified, too
name|i
operator|=
name|i
operator|+
literal|1
expr_stmt|;
name|String
name|author
decl_stmt|;
name|String
name|editor
init|=
literal|null
decl_stmt|;
name|String
name|abstractT
init|=
literal|null
decl_stmt|;
name|String
name|keywords
init|=
literal|null
decl_stmt|;
name|String
name|title
decl_stmt|;
name|String
name|conference
init|=
literal|null
decl_stmt|;
name|String
name|DOI
init|=
literal|null
decl_stmt|;
name|String
name|series
init|=
literal|null
decl_stmt|;
name|String
name|volume
init|=
literal|null
decl_stmt|;
name|String
name|number
init|=
literal|null
decl_stmt|;
name|String
name|pages
init|=
literal|null
decl_stmt|;
comment|// year is a class variable as the method extractYear() uses it;
name|String
name|publisher
init|=
literal|null
decl_stmt|;
name|EntryType
name|type
init|=
name|BibtexEntryTypes
operator|.
name|INPROCEEDINGS
decl_stmt|;
if|if
condition|(
name|curString
operator|.
name|length
argument_list|()
operator|>
literal|4
condition|)
block|{
comment|// special case: possibly conference as first line on the page
name|extractYear
argument_list|()
expr_stmt|;
if|if
condition|(
name|curString
operator|.
name|contains
argument_list|(
literal|"Conference"
argument_list|)
condition|)
block|{
name|fillCurStringWithNonEmptyLines
argument_list|()
expr_stmt|;
name|conference
operator|=
name|curString
expr_stmt|;
name|curString
operator|=
literal|""
expr_stmt|;
block|}
else|else
block|{
comment|// e.g. Copyright (c) 1998 by the Genetics Society of America
comment|// future work: get year using RegEx
name|String
name|lower
init|=
name|curString
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
if|if
condition|(
name|lower
operator|.
name|contains
argument_list|(
literal|"copyright"
argument_list|)
condition|)
block|{
name|fillCurStringWithNonEmptyLines
argument_list|()
expr_stmt|;
name|publisher
operator|=
name|curString
expr_stmt|;
name|curString
operator|=
literal|""
expr_stmt|;
block|}
block|}
block|}
comment|// start: title
name|fillCurStringWithNonEmptyLines
argument_list|()
expr_stmt|;
name|title
operator|=
name|streamlineTitle
argument_list|(
name|curString
argument_list|)
expr_stmt|;
name|curString
operator|=
literal|""
expr_stmt|;
comment|//i points to the next non-empty line
comment|// after title: authors
name|author
operator|=
literal|null
expr_stmt|;
while|while
condition|(
operator|(
name|i
operator|<
name|lines
operator|.
name|length
operator|)
operator|&&
operator|!
literal|""
operator|.
name|equals
argument_list|(
name|lines
index|[
name|i
index|]
argument_list|)
condition|)
block|{
comment|// author names are unlikely to be lines among different lines
comment|// treat them line by line
name|curString
operator|=
name|streamlineNames
argument_list|(
name|lines
index|[
name|i
index|]
argument_list|)
expr_stmt|;
if|if
condition|(
name|author
operator|==
literal|null
condition|)
block|{
name|author
operator|=
name|curString
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
literal|""
operator|.
name|equals
argument_list|(
name|curString
argument_list|)
condition|)
block|{
comment|// if lines[i] is "and" then "" is returned by streamlineNames -> do nothing
block|}
else|else
block|{
name|author
operator|=
name|author
operator|.
name|concat
argument_list|(
literal|" and "
argument_list|)
operator|.
name|concat
argument_list|(
name|curString
argument_list|)
expr_stmt|;
block|}
block|}
name|i
operator|++
expr_stmt|;
block|}
name|curString
operator|=
literal|""
expr_stmt|;
name|i
operator|++
expr_stmt|;
comment|// then, abstract and keywords follow
while|while
condition|(
name|i
operator|<
name|lines
operator|.
name|length
condition|)
block|{
name|curString
operator|=
name|lines
index|[
name|i
index|]
expr_stmt|;
if|if
condition|(
operator|(
name|curString
operator|.
name|length
argument_list|()
operator|>=
literal|"Abstract"
operator|.
name|length
argument_list|()
operator|)
operator|&&
literal|"Abstract"
operator|.
name|equalsIgnoreCase
argument_list|(
name|curString
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|"Abstract"
operator|.
name|length
argument_list|()
argument_list|)
argument_list|)
condition|)
block|{
if|if
condition|(
name|curString
operator|.
name|length
argument_list|()
operator|==
literal|"Abstract"
operator|.
name|length
argument_list|()
condition|)
block|{
comment|// only word "abstract" found -- skip line
name|curString
operator|=
literal|""
expr_stmt|;
block|}
else|else
block|{
name|curString
operator|=
name|curString
operator|.
name|substring
argument_list|(
literal|"Abstract"
operator|.
name|length
argument_list|()
operator|+
literal|1
argument_list|)
operator|.
name|trim
argument_list|()
operator|.
name|concat
argument_list|(
name|System
operator|.
name|lineSeparator
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|i
operator|++
expr_stmt|;
comment|// fillCurStringWithNonEmptyLines() cannot be used as that uses " " as line separator
comment|// whereas we need linebreak as separator
while|while
condition|(
operator|(
name|i
operator|<
name|lines
operator|.
name|length
operator|)
operator|&&
operator|!
literal|""
operator|.
name|equals
argument_list|(
name|lines
index|[
name|i
index|]
argument_list|)
condition|)
block|{
name|curString
operator|=
name|curString
operator|.
name|concat
argument_list|(
name|lines
index|[
name|i
index|]
argument_list|)
operator|.
name|concat
argument_list|(
name|System
operator|.
name|lineSeparator
argument_list|()
argument_list|)
expr_stmt|;
name|i
operator|++
expr_stmt|;
block|}
name|abstractT
operator|=
name|curString
operator|.
name|trim
argument_list|()
expr_stmt|;
name|i
operator|++
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|(
name|curString
operator|.
name|length
argument_list|()
operator|>=
literal|"Keywords"
operator|.
name|length
argument_list|()
operator|)
operator|&&
literal|"Keywords"
operator|.
name|equalsIgnoreCase
argument_list|(
name|curString
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|"Keywords"
operator|.
name|length
argument_list|()
argument_list|)
argument_list|)
condition|)
block|{
if|if
condition|(
name|curString
operator|.
name|length
argument_list|()
operator|==
literal|"Keywords"
operator|.
name|length
argument_list|()
condition|)
block|{
comment|// only word "Keywords" found -- skip line
name|curString
operator|=
literal|""
expr_stmt|;
block|}
else|else
block|{
name|curString
operator|=
name|curString
operator|.
name|substring
argument_list|(
literal|"Keywords"
operator|.
name|length
argument_list|()
operator|+
literal|1
argument_list|)
operator|.
name|trim
argument_list|()
expr_stmt|;
block|}
name|i
operator|++
expr_stmt|;
name|fillCurStringWithNonEmptyLines
argument_list|()
expr_stmt|;
name|keywords
operator|=
name|removeNonLettersAtEnd
argument_list|(
name|curString
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|String
name|lower
init|=
name|curString
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
name|int
name|pos
init|=
name|lower
operator|.
name|indexOf
argument_list|(
literal|"technical"
argument_list|)
decl_stmt|;
if|if
condition|(
name|pos
operator|>=
literal|0
condition|)
block|{
name|type
operator|=
name|BibtexEntryTypes
operator|.
name|TECHREPORT
expr_stmt|;
name|pos
operator|=
name|curString
operator|.
name|trim
argument_list|()
operator|.
name|lastIndexOf
argument_list|(
literal|' '
argument_list|)
expr_stmt|;
if|if
condition|(
name|pos
operator|>=
literal|0
condition|)
block|{
comment|// assumption: last character of curString is NOT ' '
comment|//   otherwise pos+1 leads to an out-of-bounds exception
name|number
operator|=
name|curString
operator|.
name|substring
argument_list|(
name|pos
operator|+
literal|1
argument_list|)
expr_stmt|;
block|}
block|}
name|i
operator|++
expr_stmt|;
name|proceedToNextNonEmptyLine
argument_list|()
expr_stmt|;
block|}
block|}
name|i
operator|=
name|lines
operator|.
name|length
operator|-
literal|1
expr_stmt|;
comment|// last block: DOI, detailed information
comment|// sometimes, this information is in the third last block etc...
comment|// therefore, read until the beginning of the file
while|while
condition|(
name|i
operator|>=
literal|0
condition|)
block|{
name|readLastBlock
argument_list|()
expr_stmt|;
comment|// i now points to the block before or is -1
comment|// curString contains the last block, separated by " "
name|extractYear
argument_list|()
expr_stmt|;
name|int
name|pos
init|=
name|curString
operator|.
name|indexOf
argument_list|(
literal|"(Eds.)"
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|pos
operator|>=
literal|0
operator|)
operator|&&
operator|(
name|publisher
operator|==
literal|null
operator|)
condition|)
block|{
comment|// looks like a Springer last line
comment|// e.g: A. Persson and J. Stirna (Eds.): PoEM 2009, LNBIP 39, pp. 161-175, 2009.
name|publisher
operator|=
literal|"Springer"
expr_stmt|;
name|editor
operator|=
name|streamlineNames
argument_list|(
name|curString
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|pos
operator|-
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|curString
operator|=
name|curString
operator|.
name|substring
argument_list|(
name|pos
operator|+
literal|"(Eds.)"
operator|.
name|length
argument_list|()
operator|+
literal|2
argument_list|)
expr_stmt|;
comment|//+2 because of ":" after (Eds.) and the subsequent space
name|String
index|[]
name|springerSplit
init|=
name|curString
operator|.
name|split
argument_list|(
literal|", "
argument_list|)
decl_stmt|;
if|if
condition|(
name|springerSplit
operator|.
name|length
operator|>=
literal|4
condition|)
block|{
name|conference
operator|=
name|springerSplit
index|[
literal|0
index|]
expr_stmt|;
name|String
name|seriesData
init|=
name|springerSplit
index|[
literal|1
index|]
decl_stmt|;
name|int
name|lastSpace
init|=
name|seriesData
operator|.
name|lastIndexOf
argument_list|(
literal|' '
argument_list|)
decl_stmt|;
name|series
operator|=
name|seriesData
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|lastSpace
argument_list|)
expr_stmt|;
name|volume
operator|=
name|seriesData
operator|.
name|substring
argument_list|(
name|lastSpace
operator|+
literal|1
argument_list|)
expr_stmt|;
name|pages
operator|=
name|springerSplit
index|[
literal|2
index|]
operator|.
name|substring
argument_list|(
literal|4
argument_list|)
expr_stmt|;
if|if
condition|(
name|springerSplit
index|[
literal|3
index|]
operator|.
name|length
argument_list|()
operator|>=
literal|4
condition|)
block|{
name|year
operator|=
name|springerSplit
index|[
literal|3
index|]
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|4
argument_list|)
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
if|if
condition|(
name|DOI
operator|==
literal|null
condition|)
block|{
name|pos
operator|=
name|curString
operator|.
name|indexOf
argument_list|(
literal|"DOI"
argument_list|)
expr_stmt|;
if|if
condition|(
name|pos
operator|<
literal|0
condition|)
block|{
name|pos
operator|=
name|curString
operator|.
name|indexOf
argument_list|(
literal|"doi"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|pos
operator|>=
literal|0
condition|)
block|{
name|pos
operator|+=
literal|3
expr_stmt|;
name|char
name|delimiter
init|=
name|curString
operator|.
name|charAt
argument_list|(
name|pos
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|delimiter
operator|==
literal|':'
operator|)
operator|||
operator|(
name|delimiter
operator|==
literal|' '
operator|)
condition|)
block|{
name|pos
operator|++
expr_stmt|;
block|}
name|int
name|nextSpace
init|=
name|curString
operator|.
name|indexOf
argument_list|(
literal|' '
argument_list|,
name|pos
argument_list|)
decl_stmt|;
if|if
condition|(
name|nextSpace
operator|>
literal|0
condition|)
block|{
name|DOI
operator|=
name|curString
operator|.
name|substring
argument_list|(
name|pos
argument_list|,
name|nextSpace
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|DOI
operator|=
name|curString
operator|.
name|substring
argument_list|(
name|pos
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
operator|(
name|publisher
operator|==
literal|null
operator|)
operator|&&
name|curString
operator|.
name|contains
argument_list|(
literal|"IEEE"
argument_list|)
condition|)
block|{
comment|// IEEE has the conference things at the end
name|publisher
operator|=
literal|"IEEE"
expr_stmt|;
comment|// year is extracted by extractYear
comment|// otherwise, we could it determine as follows:
comment|// String yearStr = curString.substring(curString.length()-4);
comment|// if (isYear(yearStr)) {
comment|//	year = yearStr;
comment|// }
if|if
condition|(
name|conference
operator|==
literal|null
condition|)
block|{
name|pos
operator|=
name|curString
operator|.
name|indexOf
argument_list|(
literal|'$'
argument_list|)
expr_stmt|;
if|if
condition|(
name|pos
operator|>
literal|0
condition|)
block|{
comment|// we found the price
comment|// before the price, the ISSN is stated
comment|// skip that
name|pos
operator|-=
literal|2
expr_stmt|;
while|while
condition|(
operator|(
name|pos
operator|>=
literal|0
operator|)
operator|&&
operator|(
name|curString
operator|.
name|charAt
argument_list|(
name|pos
argument_list|)
operator|!=
literal|' '
operator|)
condition|)
block|{
name|pos
operator|--
expr_stmt|;
block|}
if|if
condition|(
name|pos
operator|>
literal|0
condition|)
block|{
name|conference
operator|=
name|curString
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|pos
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
block|}
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setType
argument_list|(
name|type
argument_list|)
expr_stmt|;
comment|// TODO: institution parsing missing
if|if
condition|(
name|author
operator|!=
literal|null
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
name|author
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|editor
operator|!=
literal|null
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
literal|"editor"
argument_list|,
name|editor
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|abstractT
operator|!=
literal|null
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
literal|"abstract"
argument_list|,
name|abstractT
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|Strings
operator|.
name|isNullOrEmpty
argument_list|(
name|keywords
argument_list|)
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
literal|"keywords"
argument_list|,
name|keywords
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|title
operator|!=
literal|null
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
name|title
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|conference
operator|!=
literal|null
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
literal|"booktitle"
argument_list|,
name|conference
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|DOI
operator|!=
literal|null
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
literal|"doi"
argument_list|,
name|DOI
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|series
operator|!=
literal|null
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
literal|"series"
argument_list|,
name|series
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|volume
operator|!=
literal|null
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
literal|"volume"
argument_list|,
name|volume
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|number
operator|!=
literal|null
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
literal|"number"
argument_list|,
name|number
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|pages
operator|!=
literal|null
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
literal|"pages"
argument_list|,
name|pages
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|year
operator|!=
literal|null
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
name|year
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|publisher
operator|!=
literal|null
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
literal|"publisher"
argument_list|,
name|publisher
argument_list|)
expr_stmt|;
block|}
name|result
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
DECL|method|getFirstPageContents (PDDocument document)
specifier|private
name|String
name|getFirstPageContents
parameter_list|(
name|PDDocument
name|document
parameter_list|)
throws|throws
name|IOException
block|{
name|PDFTextStripper
name|stripper
init|=
operator|new
name|PDFTextStripper
argument_list|()
decl_stmt|;
name|stripper
operator|.
name|setStartPage
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|stripper
operator|.
name|setEndPage
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|stripper
operator|.
name|setSortByPosition
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|stripper
operator|.
name|setParagraphEnd
argument_list|(
name|System
operator|.
name|lineSeparator
argument_list|()
argument_list|)
expr_stmt|;
name|StringWriter
name|writer
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
name|stripper
operator|.
name|writeText
argument_list|(
name|document
argument_list|,
name|writer
argument_list|)
expr_stmt|;
return|return
name|writer
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Extract the year out of curString (if it is not yet defined)      */
DECL|method|extractYear ()
specifier|private
name|void
name|extractYear
parameter_list|()
block|{
if|if
condition|(
name|year
operator|!=
literal|null
condition|)
block|{
return|return;
block|}
name|Matcher
name|m
init|=
name|YEAR_EXTRACT_PATTERN
operator|.
name|matcher
argument_list|(
name|curString
argument_list|)
decl_stmt|;
if|if
condition|(
name|m
operator|.
name|find
argument_list|()
condition|)
block|{
name|year
operator|=
name|curString
operator|.
name|substring
argument_list|(
name|m
operator|.
name|start
argument_list|()
argument_list|,
name|m
operator|.
name|end
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * PDFTextStripper normally does NOT produce multiple empty lines      * (besides at strange PDFs). These strange PDFs are handled here:      * proceed to next non-empty line      */
DECL|method|proceedToNextNonEmptyLine ()
specifier|private
name|void
name|proceedToNextNonEmptyLine
parameter_list|()
block|{
while|while
condition|(
operator|(
name|i
operator|<
name|lines
operator|.
name|length
operator|)
operator|&&
literal|""
operator|.
name|equals
argument_list|(
name|lines
index|[
name|i
index|]
operator|.
name|trim
argument_list|()
argument_list|)
condition|)
block|{
name|i
operator|++
expr_stmt|;
block|}
block|}
comment|/**      * Fill curString with lines until "" is found      * No trailing space is added      * i is advanced to the next non-empty line (ignoring white space)      *<p>      * Lines containing only white spaces are ignored,      * but NOT considered as ""      *<p>      * Uses GLOBAL variables lines, curLine, i      */
DECL|method|fillCurStringWithNonEmptyLines ()
specifier|private
name|void
name|fillCurStringWithNonEmptyLines
parameter_list|()
block|{
comment|// ensure that curString does not end with " "
name|curString
operator|=
name|curString
operator|.
name|trim
argument_list|()
expr_stmt|;
while|while
condition|(
operator|(
name|i
operator|<
name|lines
operator|.
name|length
operator|)
operator|&&
operator|!
literal|""
operator|.
name|equals
argument_list|(
name|lines
index|[
name|i
index|]
argument_list|)
condition|)
block|{
name|String
name|curLine
init|=
name|lines
index|[
name|i
index|]
operator|.
name|trim
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
literal|""
operator|.
name|equals
argument_list|(
name|curLine
argument_list|)
condition|)
block|{
if|if
condition|(
operator|!
name|curString
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// insert separating space if necessary
name|curString
operator|=
name|curString
operator|.
name|concat
argument_list|(
literal|" "
argument_list|)
expr_stmt|;
block|}
name|curString
operator|=
name|curString
operator|.
name|concat
argument_list|(
name|lines
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
name|i
operator|++
expr_stmt|;
block|}
name|proceedToNextNonEmptyLine
argument_list|()
expr_stmt|;
block|}
comment|/**      * resets curString      * curString now contains the last block (until "" reached)      * Trailing space is added      *<p>      * invariant before/after: i points to line before the last handled block      */
DECL|method|readLastBlock ()
specifier|private
name|void
name|readLastBlock
parameter_list|()
block|{
while|while
condition|(
operator|(
name|i
operator|>=
literal|0
operator|)
operator|&&
literal|""
operator|.
name|equals
argument_list|(
name|lines
index|[
name|i
index|]
operator|.
name|trim
argument_list|()
argument_list|)
condition|)
block|{
name|i
operator|--
expr_stmt|;
block|}
comment|// i is now at the end of a block
name|int
name|end
init|=
name|i
decl_stmt|;
comment|// find beginning
while|while
condition|(
operator|(
name|i
operator|>=
literal|0
operator|)
operator|&&
operator|!
literal|""
operator|.
name|equals
argument_list|(
name|lines
index|[
name|i
index|]
argument_list|)
condition|)
block|{
name|i
operator|--
expr_stmt|;
block|}
comment|// i is now the line before the beginning of the block
comment|// this fulfills the invariant
name|curString
operator|=
literal|""
expr_stmt|;
for|for
control|(
name|int
name|j
init|=
name|i
operator|+
literal|1
init|;
name|j
operator|<=
name|end
condition|;
name|j
operator|++
control|)
block|{
name|curString
operator|=
name|curString
operator|.
name|concat
argument_list|(
name|lines
index|[
name|j
index|]
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|j
operator|!=
name|end
condition|)
block|{
name|curString
operator|=
name|curString
operator|.
name|concat
argument_list|(
literal|" "
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Override
DECL|method|getFormatName ()
specifier|public
name|String
name|getFormatName
parameter_list|()
block|{
return|return
literal|"PDFcontent"
return|;
block|}
block|}
end_class

end_unit

