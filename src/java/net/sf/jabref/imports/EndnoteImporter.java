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
name|Pattern
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
comment|/**  * Importer for the Refer/Endnote format.  *  * check here for details on the format  * http://www.ecst.csuchico.edu/~jacobsd/bib/formats/endnote.html  */
end_comment

begin_class
DECL|class|EndnoteImporter
specifier|public
class|class
name|EndnoteImporter
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
literal|"Refer/Endnote"
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
literal|"refer"
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
comment|// Our strategy is to look for the "%A *" line.
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
literal|"%A .*"
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
name|ENDOFRECORD
init|=
literal|"__EOREOR__"
decl_stmt|;
name|String
name|str
decl_stmt|;
name|boolean
name|first
init|=
literal|true
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
name|str
operator|=
name|str
operator|.
name|trim
argument_list|()
expr_stmt|;
comment|// if(str.equals("")) continue;
if|if
condition|(
name|str
operator|.
name|indexOf
argument_list|(
literal|"%0"
argument_list|)
operator|==
literal|0
condition|)
block|{
if|if
condition|(
name|first
condition|)
block|{
name|first
operator|=
literal|false
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|ENDOFRECORD
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
name|str
argument_list|)
expr_stmt|;
block|}
else|else
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
name|ENDOFRECORD
argument_list|)
decl_stmt|;
name|HashMap
name|hm
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
name|String
name|Author
init|=
literal|""
decl_stmt|,
name|Type
init|=
literal|""
decl_stmt|,
name|Editor
init|=
literal|""
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
name|hm
operator|.
name|clear
argument_list|()
expr_stmt|;
name|Author
operator|=
literal|""
expr_stmt|;
name|Type
operator|=
literal|""
expr_stmt|;
name|Editor
operator|=
literal|""
expr_stmt|;
name|boolean
name|IsEditedBook
init|=
literal|false
decl_stmt|;
name|String
index|[]
name|fields
init|=
name|entries
index|[
name|i
index|]
operator|.
name|trim
argument_list|()
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
operator|.
name|split
argument_list|(
literal|"\n%"
argument_list|)
decl_stmt|;
comment|//String lastPrefix = "";
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
literal|3
condition|)
continue|continue;
comment|/*            * Details of Refer format for Journal Article and Book:            *            * Generic Ref Journal Article Book Code Author %A Author Author Year %D            * Year Year Title %T Title Title Secondary Author %E Series Editor            * Secondary Title %B Journal Series Title Place Published %C City            * Publisher %I Publisher Volume %V Volume Volume Number of Volumes %6            * Number of Volumes Number %N Issue Pages %P Pages Number of Pages            * Edition %7 Edition Subsidiary Author %? Translator Alternate Title %J            * Alternate Journal Label %F Label Label Keywords %K Keywords Keywords            * Abstract %X Abstract Abstract Notes %O Notes Notes            */
name|String
name|prefix
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
literal|1
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
literal|2
argument_list|)
decl_stmt|;
if|if
condition|(
name|prefix
operator|.
name|equals
argument_list|(
literal|"A"
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
name|prefix
operator|.
name|equals
argument_list|(
literal|"E"
argument_list|)
condition|)
block|{
if|if
condition|(
name|Editor
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
name|Editor
operator|=
name|val
expr_stmt|;
else|else
name|Editor
operator|+=
literal|" and "
operator|+
name|val
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|prefix
operator|.
name|equals
argument_list|(
literal|"T"
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
elseif|else
if|if
condition|(
name|prefix
operator|.
name|equals
argument_list|(
literal|"0"
argument_list|)
condition|)
block|{
if|if
condition|(
name|val
operator|.
name|indexOf
argument_list|(
literal|"Journal"
argument_list|)
operator|==
literal|0
condition|)
name|Type
operator|=
literal|"article"
expr_stmt|;
elseif|else
if|if
condition|(
operator|(
name|val
operator|.
name|indexOf
argument_list|(
literal|"Book Section"
argument_list|)
operator|==
literal|0
operator|)
condition|)
name|Type
operator|=
literal|"incollection"
expr_stmt|;
elseif|else
if|if
condition|(
operator|(
name|val
operator|.
name|indexOf
argument_list|(
literal|"Book"
argument_list|)
operator|==
literal|0
operator|)
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
name|indexOf
argument_list|(
literal|"Edited Book"
argument_list|)
operator|==
literal|0
condition|)
block|{
name|Type
operator|=
literal|"book"
expr_stmt|;
name|IsEditedBook
operator|=
literal|true
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|val
operator|.
name|indexOf
argument_list|(
literal|"Conference"
argument_list|)
operator|==
literal|0
condition|)
comment|// Proceedings
name|Type
operator|=
literal|"inproceedings"
expr_stmt|;
elseif|else
if|if
condition|(
name|val
operator|.
name|indexOf
argument_list|(
literal|"Report"
argument_list|)
operator|==
literal|0
condition|)
comment|// Techreport
name|Type
operator|=
literal|"techreport"
expr_stmt|;
elseif|else
if|if
condition|(
name|val
operator|.
name|indexOf
argument_list|(
literal|"Review"
argument_list|)
operator|==
literal|0
condition|)
name|Type
operator|=
literal|"article"
expr_stmt|;
elseif|else
if|if
condition|(
name|val
operator|.
name|indexOf
argument_list|(
literal|"Thesis"
argument_list|)
operator|==
literal|0
condition|)
name|Type
operator|=
literal|"phdthesis"
expr_stmt|;
else|else
name|Type
operator|=
literal|"misc"
expr_stmt|;
comment|//
block|}
elseif|else
if|if
condition|(
name|prefix
operator|.
name|equals
argument_list|(
literal|"7"
argument_list|)
condition|)
name|hm
operator|.
name|put
argument_list|(
literal|"edition"
argument_list|,
name|val
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|prefix
operator|.
name|equals
argument_list|(
literal|"C"
argument_list|)
condition|)
name|hm
operator|.
name|put
argument_list|(
literal|"address"
argument_list|,
name|val
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|prefix
operator|.
name|equals
argument_list|(
literal|"D"
argument_list|)
condition|)
name|hm
operator|.
name|put
argument_list|(
literal|"year"
argument_list|,
name|val
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|prefix
operator|.
name|equals
argument_list|(
literal|"8"
argument_list|)
condition|)
name|hm
operator|.
name|put
argument_list|(
literal|"date"
argument_list|,
name|val
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|prefix
operator|.
name|equals
argument_list|(
literal|"J"
argument_list|)
condition|)
block|{
comment|// "Alternate journal. Let's set it only if no journal
comment|// has been set with %B.
if|if
condition|(
name|hm
operator|.
name|get
argument_list|(
literal|"journal"
argument_list|)
operator|==
literal|null
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
block|}
elseif|else
if|if
condition|(
name|prefix
operator|.
name|equals
argument_list|(
literal|"B"
argument_list|)
condition|)
block|{
comment|// This prefix stands for "journal" in a journal entry, and
comment|// "series" in a book entry.
if|if
condition|(
name|Type
operator|.
name|equals
argument_list|(
literal|"article"
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
name|Type
operator|.
name|equals
argument_list|(
literal|"book"
argument_list|)
operator|||
name|Type
operator|.
name|equals
argument_list|(
literal|"inbook"
argument_list|)
condition|)
name|hm
operator|.
name|put
argument_list|(
literal|"series"
argument_list|,
name|val
argument_list|)
expr_stmt|;
else|else
comment|/* if (Type.equals("inproceedings")) */
name|hm
operator|.
name|put
argument_list|(
literal|"booktitle"
argument_list|,
name|val
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|prefix
operator|.
name|equals
argument_list|(
literal|"I"
argument_list|)
condition|)
block|{
if|if
condition|(
name|Type
operator|.
name|equals
argument_list|(
literal|"phdthesis"
argument_list|)
condition|)
name|hm
operator|.
name|put
argument_list|(
literal|"school"
argument_list|,
name|val
argument_list|)
expr_stmt|;
else|else
name|hm
operator|.
name|put
argument_list|(
literal|"publisher"
argument_list|,
name|val
argument_list|)
expr_stmt|;
block|}
comment|// replace single dash page ranges (23-45) with double dashes (23--45):
elseif|else
if|if
condition|(
name|prefix
operator|.
name|equals
argument_list|(
literal|"P"
argument_list|)
condition|)
name|hm
operator|.
name|put
argument_list|(
literal|"pages"
argument_list|,
name|val
operator|.
name|replaceAll
argument_list|(
literal|"([0-9]) *- *([0-9])"
argument_list|,
literal|"$1--$2"
argument_list|)
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|prefix
operator|.
name|equals
argument_list|(
literal|"V"
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
name|prefix
operator|.
name|equals
argument_list|(
literal|"N"
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
name|prefix
operator|.
name|equals
argument_list|(
literal|"U"
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
name|prefix
operator|.
name|equals
argument_list|(
literal|"O"
argument_list|)
condition|)
name|hm
operator|.
name|put
argument_list|(
literal|"note"
argument_list|,
name|val
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|prefix
operator|.
name|equals
argument_list|(
literal|"K"
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
elseif|else
if|if
condition|(
name|prefix
operator|.
name|equals
argument_list|(
literal|"X"
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
name|prefix
operator|.
name|equals
argument_list|(
literal|"9"
argument_list|)
condition|)
block|{
comment|//Util.pr(val);
if|if
condition|(
name|val
operator|.
name|indexOf
argument_list|(
literal|"Ph.D."
argument_list|)
operator|==
literal|0
condition|)
name|Type
operator|=
literal|"phdthesis"
expr_stmt|;
if|if
condition|(
name|val
operator|.
name|indexOf
argument_list|(
literal|"Masters"
argument_list|)
operator|==
literal|0
condition|)
name|Type
operator|=
literal|"mastersthesis"
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|prefix
operator|.
name|equals
argument_list|(
literal|"F"
argument_list|)
condition|)
name|hm
operator|.
name|put
argument_list|(
name|BibtexFields
operator|.
name|KEY_FIELD
argument_list|,
name|Util
operator|.
name|checkLegalKey
argument_list|(
name|val
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// For Edited Book, EndNote puts the editors in the author field.
comment|// We want them in the editor field so that bibtex knows it's an edited book
if|if
condition|(
name|IsEditedBook
operator|&&
name|Editor
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|Editor
operator|=
name|Author
expr_stmt|;
name|Author
operator|=
literal|""
expr_stmt|;
block|}
comment|//fixauthorscomma
if|if
condition|(
operator|!
name|Author
operator|.
name|equals
argument_list|(
literal|""
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
name|Author
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|Editor
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
name|hm
operator|.
name|put
argument_list|(
literal|"editor"
argument_list|,
name|AuthorList
operator|.
name|fixAuthor_lastNameFirst
argument_list|(
name|Editor
argument_list|)
argument_list|)
expr_stmt|;
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
comment|//if (hm.isEmpty())
if|if
condition|(
name|b
operator|.
name|getAllFields
argument_list|()
operator|.
name|length
operator|>
literal|0
condition|)
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

