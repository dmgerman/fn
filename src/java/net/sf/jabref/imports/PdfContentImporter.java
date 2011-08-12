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
name|awt
operator|.
name|Rectangle
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
name|logging
operator|.
name|Level
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|logging
operator|.
name|Logger
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
name|pdmodel
operator|.
name|PDPage
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
name|common
operator|.
name|PDRectangle
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
name|org
operator|.
name|apache
operator|.
name|pdfbox
operator|.
name|util
operator|.
name|PDFTextStripperByArea
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
name|GUIGlobals
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

begin_class
DECL|class|PdfContentImporter
specifier|public
class|class
name|PdfContentImporter
extends|extends
name|ImportFormat
block|{
DECL|field|logger
specifier|private
specifier|static
name|Logger
name|logger
init|=
name|Logger
operator|.
name|getLogger
argument_list|(
name|PdfContentImporter
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
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
comment|/** 	 * Removes all non-letter characters at the end 	 *  	 * EXCEPTION: a closing bracket is NOT removed 	 *  	 * @param input 	 * @return 	 */
DECL|method|removeNonLettersAtEnd (String input)
specifier|private
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
name|length
argument_list|()
operator|>
literal|0
condition|)
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
else|else
block|{
break|break;
block|}
block|}
return|return
name|input
return|;
block|}
DECL|method|streamlineNames (String names)
specifier|private
name|String
name|streamlineNames
parameter_list|(
name|String
name|names
parameter_list|)
block|{
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
name|split
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
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|split
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|String
name|curName
init|=
name|removeNonLettersAtEnd
argument_list|(
name|split
index|[
name|i
index|]
argument_list|)
decl_stmt|;
if|if
condition|(
name|curName
operator|.
name|indexOf
argument_list|(
literal|"and "
argument_list|)
operator|==
literal|0
condition|)
block|{
name|curName
operator|=
name|curName
operator|.
name|substring
argument_list|(
literal|4
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
if|if
condition|(
name|i
operator|!=
name|split
operator|.
name|length
operator|-
literal|1
condition|)
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
block|}
block|}
else|else
block|{
comment|// names could be spearated by "and" - not treated here
comment|// assume: names separated by space
comment|// will fail at double names
name|String
index|[]
name|split
init|=
name|names
operator|.
name|split
argument_list|(
literal|" "
argument_list|)
decl_stmt|;
name|res
operator|=
literal|null
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
name|split
operator|.
name|length
condition|;
name|i
operator|+=
literal|2
control|)
block|{
if|if
condition|(
name|i
operator|==
literal|0
condition|)
block|{
name|res
operator|=
name|split
index|[
literal|0
index|]
expr_stmt|;
if|if
condition|(
name|split
operator|.
name|length
operator|>
literal|1
condition|)
block|{
name|res
operator|=
name|res
operator|.
name|concat
argument_list|(
literal|" "
argument_list|)
operator|.
name|concat
argument_list|(
name|split
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
block|}
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
operator|.
name|concat
argument_list|(
name|split
index|[
name|i
index|]
argument_list|)
expr_stmt|;
if|if
condition|(
name|split
operator|.
name|length
operator|>
name|i
operator|+
literal|1
condition|)
block|{
name|res
operator|=
name|res
operator|.
name|concat
argument_list|(
literal|" "
argument_list|)
operator|.
name|concat
argument_list|(
name|split
index|[
name|i
operator|+
literal|1
index|]
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
return|return
name|res
return|;
block|}
DECL|method|streamlineTitle (String title)
specifier|private
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
DECL|method|isYear (String yearStr)
specifier|private
name|boolean
name|isYear
parameter_list|(
name|String
name|yearStr
parameter_list|)
block|{
try|try
block|{
name|Integer
operator|.
name|parseInt
argument_list|(
name|yearStr
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
block|}
annotation|@
name|Override
DECL|method|importEntries (InputStream in)
specifier|public
name|List
argument_list|<
name|BibtexEntry
argument_list|>
name|importEntries
parameter_list|(
name|InputStream
name|in
parameter_list|)
throws|throws
name|IOException
block|{
name|ArrayList
argument_list|<
name|BibtexEntry
argument_list|>
name|res
init|=
operator|new
name|ArrayList
argument_list|<
name|BibtexEntry
argument_list|>
argument_list|(
literal|1
argument_list|)
decl_stmt|;
name|PDDocument
name|document
init|=
literal|null
decl_stmt|;
try|try
block|{
name|document
operator|=
name|PDDocument
operator|.
name|load
argument_list|(
name|in
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|log
argument_list|(
name|Level
operator|.
name|SEVERE
argument_list|,
literal|"Could not load document"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
name|res
return|;
block|}
try|try
block|{
if|if
condition|(
name|document
operator|.
name|isEncrypted
argument_list|()
condition|)
block|{
name|logger
operator|.
name|log
argument_list|(
name|Level
operator|.
name|INFO
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Encrypted documents are not supported"
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|res
return|;
block|}
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
name|getProperty
argument_list|(
literal|"line.separator"
argument_list|)
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
name|String
name|textResult
init|=
name|writer
operator|.
name|toString
argument_list|()
decl_stmt|;
name|String
name|author
init|=
literal|null
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
init|=
literal|null
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
name|pages
init|=
literal|null
decl_stmt|;
name|String
name|year
init|=
literal|null
decl_stmt|;
name|String
name|publisher
init|=
literal|null
decl_stmt|;
specifier|final
name|String
name|lineBreak
init|=
name|System
operator|.
name|getProperty
argument_list|(
literal|"line.separator"
argument_list|)
decl_stmt|;
name|String
index|[]
name|split
init|=
name|textResult
operator|.
name|split
argument_list|(
name|lineBreak
argument_list|)
decl_stmt|;
name|String
name|curString
init|=
name|split
index|[
literal|0
index|]
operator|.
name|concat
argument_list|(
literal|" "
argument_list|)
decl_stmt|;
name|int
name|i
init|=
literal|1
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
name|boolean
name|match
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|isYear
argument_list|(
name|curString
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|4
argument_list|)
argument_list|)
condition|)
block|{
name|year
operator|=
name|curString
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|4
argument_list|)
expr_stmt|;
name|match
operator|=
literal|true
expr_stmt|;
block|}
name|match
operator|=
name|match
operator|||
name|curString
operator|.
name|contains
argument_list|(
literal|"Conference"
argument_list|)
expr_stmt|;
if|if
condition|(
name|match
condition|)
block|{
while|while
condition|(
operator|(
name|i
operator|<
name|split
operator|.
name|length
operator|)
operator|&&
operator|(
operator|!
name|split
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
operator|)
condition|)
block|{
name|curString
operator|=
name|curString
operator|.
name|concat
argument_list|(
name|split
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
name|i
operator|++
expr_stmt|;
block|}
name|conference
operator|=
name|curString
expr_stmt|;
name|curString
operator|=
literal|""
expr_stmt|;
name|i
operator|++
expr_stmt|;
block|}
block|}
comment|// start: title
while|while
condition|(
operator|(
name|i
operator|<
name|split
operator|.
name|length
operator|)
operator|&&
operator|(
operator|!
name|split
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
operator|)
condition|)
block|{
name|curString
operator|=
name|curString
operator|.
name|concat
argument_list|(
name|split
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
name|i
operator|++
expr_stmt|;
block|}
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
name|i
operator|++
expr_stmt|;
comment|//i points to the next non-empty line
comment|// PDFTextStripper does NOT produce multiple empty lines (besides at strange PDFs)
comment|// special handling for strange PDFs which contain a line with " "
while|while
condition|(
operator|(
name|i
operator|<
name|split
operator|.
name|length
operator|)
operator|&&
operator|(
name|split
index|[
name|i
index|]
operator|.
name|trim
argument_list|()
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
operator|)
condition|)
name|i
operator|++
expr_stmt|;
comment|// after title: authors
while|while
condition|(
operator|(
name|i
operator|<
name|split
operator|.
name|length
operator|)
operator|&&
operator|(
operator|!
name|split
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
operator|)
condition|)
block|{
name|curString
operator|=
name|curString
operator|.
name|concat
argument_list|(
name|split
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
name|i
operator|++
expr_stmt|;
block|}
name|author
operator|=
name|streamlineNames
argument_list|(
name|curString
argument_list|)
expr_stmt|;
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
name|split
operator|.
name|length
condition|)
block|{
name|curString
operator|=
name|split
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
operator|(
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
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"Abstract"
argument_list|)
operator|)
condition|)
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
name|lineBreak
argument_list|)
expr_stmt|;
name|i
operator|++
expr_stmt|;
while|while
condition|(
operator|(
name|i
operator|<
name|split
operator|.
name|length
operator|)
operator|&&
operator|(
operator|!
name|split
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
operator|)
condition|)
block|{
name|curString
operator|=
name|curString
operator|.
name|concat
argument_list|(
name|split
index|[
name|i
index|]
argument_list|)
operator|.
name|concat
argument_list|(
name|lineBreak
argument_list|)
expr_stmt|;
name|i
operator|++
expr_stmt|;
block|}
name|abstractT
operator|=
name|curString
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
operator|(
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
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"Keywords"
argument_list|)
operator|)
condition|)
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
name|i
operator|++
expr_stmt|;
while|while
condition|(
operator|(
name|i
operator|<
name|split
operator|.
name|length
operator|)
operator|&&
operator|(
operator|!
name|split
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
operator|)
condition|)
block|{
name|curString
operator|=
name|curString
operator|.
name|concat
argument_list|(
name|split
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
name|i
operator|++
expr_stmt|;
block|}
name|keywords
operator|=
name|removeNonLettersAtEnd
argument_list|(
name|curString
argument_list|)
expr_stmt|;
block|}
name|i
operator|++
expr_stmt|;
block|}
name|i
operator|=
name|split
operator|.
name|length
operator|-
literal|1
expr_stmt|;
comment|// last block: DOI, detailed information
while|while
condition|(
operator|(
name|i
operator|>
literal|0
operator|)
operator|&&
operator|(
operator|!
name|split
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
operator|)
condition|)
block|{
name|i
operator|--
expr_stmt|;
block|}
name|curString
operator|=
literal|""
expr_stmt|;
if|if
condition|(
name|i
operator|>
literal|0
condition|)
block|{
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
operator|<
name|split
operator|.
name|length
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
name|split
index|[
name|j
index|]
argument_list|)
expr_stmt|;
if|if
condition|(
name|j
operator|!=
name|split
operator|.
name|length
operator|-
literal|1
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
name|pos
operator|>=
literal|0
condition|)
block|{
comment|// looks like a Springer last line
comment|// e.g: A. Persson and J. Stirna (Eds.): PoEM 2009, LNBIP 39, pp. 161175, 2009.
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
name|pos
operator|=
name|curString
operator|.
name|indexOf
argument_list|(
literal|"doi"
argument_list|)
expr_stmt|;
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
else|else
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
if|if
condition|(
name|curString
operator|.
name|indexOf
argument_list|(
literal|"IEEE"
argument_list|)
operator|>=
literal|0
condition|)
block|{
comment|// IEEE has the conference things at the end
name|publisher
operator|=
literal|"IEEE"
expr_stmt|;
name|String
name|yearStr
init|=
name|curString
operator|.
name|substring
argument_list|(
name|curString
operator|.
name|length
argument_list|()
operator|-
literal|4
argument_list|)
decl_stmt|;
if|if
condition|(
name|isYear
argument_list|(
name|yearStr
argument_list|)
condition|)
block|{
name|year
operator|=
name|yearStr
expr_stmt|;
block|}
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
name|pos
operator|--
expr_stmt|;
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
name|BibtexEntry
name|entry
init|=
operator|new
name|BibtexEntry
argument_list|()
decl_stmt|;
if|if
condition|(
name|author
operator|!=
literal|null
condition|)
name|entry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
name|author
argument_list|)
expr_stmt|;
if|if
condition|(
name|editor
operator|!=
literal|null
condition|)
name|entry
operator|.
name|setField
argument_list|(
literal|"editor"
argument_list|,
name|editor
argument_list|)
expr_stmt|;
if|if
condition|(
name|abstractT
operator|!=
literal|null
condition|)
name|entry
operator|.
name|setField
argument_list|(
literal|"abstract"
argument_list|,
name|abstractT
argument_list|)
expr_stmt|;
if|if
condition|(
name|keywords
operator|!=
literal|null
condition|)
name|entry
operator|.
name|setField
argument_list|(
literal|"keywords"
argument_list|,
name|keywords
argument_list|)
expr_stmt|;
if|if
condition|(
name|title
operator|!=
literal|null
condition|)
name|entry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
name|title
argument_list|)
expr_stmt|;
if|if
condition|(
name|conference
operator|!=
literal|null
condition|)
name|entry
operator|.
name|setField
argument_list|(
literal|"booktitle"
argument_list|,
name|conference
argument_list|)
expr_stmt|;
if|if
condition|(
name|DOI
operator|!=
literal|null
condition|)
name|entry
operator|.
name|setField
argument_list|(
literal|"doi"
argument_list|,
name|DOI
argument_list|)
expr_stmt|;
if|if
condition|(
name|series
operator|!=
literal|null
condition|)
name|entry
operator|.
name|setField
argument_list|(
literal|"series"
argument_list|,
name|series
argument_list|)
expr_stmt|;
if|if
condition|(
name|volume
operator|!=
literal|null
condition|)
name|entry
operator|.
name|setField
argument_list|(
literal|"volume"
argument_list|,
name|volume
argument_list|)
expr_stmt|;
if|if
condition|(
name|pages
operator|!=
literal|null
condition|)
name|entry
operator|.
name|setField
argument_list|(
literal|"pages"
argument_list|,
name|pages
argument_list|)
expr_stmt|;
if|if
condition|(
name|year
operator|!=
literal|null
condition|)
name|entry
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
name|year
argument_list|)
expr_stmt|;
if|if
condition|(
name|publisher
operator|!=
literal|null
condition|)
name|entry
operator|.
name|setField
argument_list|(
literal|"publisher"
argument_list|,
name|publisher
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"review"
argument_list|,
name|textResult
argument_list|)
expr_stmt|;
name|res
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|document
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
return|return
name|res
return|;
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

