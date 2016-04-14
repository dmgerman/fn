begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
operator|.
name|ParserResult
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
name|labelpattern
operator|.
name|LabelPatternUtil
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
name|model
operator|.
name|entry
operator|.
name|BibEntry
import|;
end_import

begin_comment
comment|/**  * Importer for the Refer/Endnote format.  * modified to use article number for pages if pages are missing (some  * journals, e.g., Physical Review Letters, don't use pages anymore)  *  * check here for details on the format  * http://www.ecst.csuchico.edu/~jacobsd/bib/formats/endnote.html  */
end_comment

begin_class
DECL|class|EndnoteImporter
specifier|public
class|class
name|EndnoteImporter
extends|extends
name|ImportFormat
block|{
DECL|field|ENDOFRECORD
specifier|private
specifier|static
specifier|final
name|String
name|ENDOFRECORD
init|=
literal|"__EOREOR__"
decl_stmt|;
DECL|field|A_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|A_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"%A .*"
argument_list|)
decl_stmt|;
DECL|field|E_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|E_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"%E .*"
argument_list|)
decl_stmt|;
annotation|@
name|Override
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
annotation|@
name|Override
DECL|method|getExtensions ()
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getExtensions
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|getId ()
specifier|public
name|String
name|getId
parameter_list|()
block|{
return|return
literal|"refer"
return|;
block|}
annotation|@
name|Override
DECL|method|getDescription ()
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|isRecognizedFormat (BufferedReader reader)
specifier|public
name|boolean
name|isRecognizedFormat
parameter_list|(
name|BufferedReader
name|reader
parameter_list|)
throws|throws
name|IOException
block|{
comment|// Our strategy is to look for the "%A *" line.
name|String
name|str
decl_stmt|;
while|while
condition|(
operator|(
name|str
operator|=
name|reader
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
name|A_PATTERN
operator|.
name|matcher
argument_list|(
name|str
argument_list|)
operator|.
name|matches
argument_list|()
operator|||
name|E_PATTERN
operator|.
name|matcher
argument_list|(
name|str
argument_list|)
operator|.
name|matches
argument_list|()
condition|)
block|{
return|return
literal|true
return|;
block|}
block|}
return|return
literal|false
return|;
block|}
annotation|@
name|Override
DECL|method|importDatabase (BufferedReader reader)
specifier|public
name|ParserResult
name|importDatabase
parameter_list|(
name|BufferedReader
name|reader
parameter_list|)
throws|throws
name|IOException
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|bibitems
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
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
name|reader
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
block|{
name|sb
operator|.
name|append
argument_list|(
name|str
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
literal|'\n'
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
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|hm
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
name|String
name|author
decl_stmt|;
name|String
name|type
decl_stmt|;
name|String
name|editor
decl_stmt|;
name|String
name|artnum
decl_stmt|;
for|for
control|(
name|String
name|entry
range|:
name|entries
control|)
block|{
name|hm
operator|.
name|clear
argument_list|()
expr_stmt|;
name|author
operator|=
literal|""
expr_stmt|;
name|type
operator|=
literal|"misc"
expr_stmt|;
name|editor
operator|=
literal|""
expr_stmt|;
name|artnum
operator|=
literal|""
expr_stmt|;
name|boolean
name|isEditedBook
init|=
literal|false
decl_stmt|;
name|String
index|[]
name|fields
init|=
name|entry
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
for|for
control|(
name|String
name|field
range|:
name|fields
control|)
block|{
if|if
condition|(
name|field
operator|.
name|length
argument_list|()
operator|<
literal|3
condition|)
block|{
continue|continue;
block|}
comment|/*                  * Details of Refer format for Journal Article and Book:                  *                  * Generic Ref Journal Article Book Code Author %A Author Author Year %D                  * Year Year Title %T Title Title Secondary Author %E Series Editor                  * Secondary Title %B Journal Series Title Place Published %C City                  * Publisher %I Publisher Volume %V Volume Volume Number of Volumes %6                  * Number of Volumes Number %N Issue Pages %P Pages Number of Pages                  * Edition %7 Edition Subsidiary Author %? Translator Alternate Title %J                  * Alternate Journal Label %F Label Label Keywords %K Keywords Keywords                  * Abstract %X Abstract Abstract Notes %O Notes Notes                  */
name|String
name|prefix
init|=
name|field
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
name|field
operator|.
name|substring
argument_list|(
literal|2
argument_list|)
decl_stmt|;
if|if
condition|(
literal|"A"
operator|.
name|equals
argument_list|(
name|prefix
argument_list|)
condition|)
block|{
if|if
condition|(
literal|""
operator|.
name|equals
argument_list|(
name|author
argument_list|)
condition|)
block|{
name|author
operator|=
name|val
expr_stmt|;
block|}
else|else
block|{
name|author
operator|+=
literal|" and "
operator|+
name|val
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
literal|"E"
operator|.
name|equals
argument_list|(
name|prefix
argument_list|)
condition|)
block|{
if|if
condition|(
literal|""
operator|.
name|equals
argument_list|(
name|editor
argument_list|)
condition|)
block|{
name|editor
operator|=
name|val
expr_stmt|;
block|}
else|else
block|{
name|editor
operator|+=
literal|" and "
operator|+
name|val
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
literal|"T"
operator|.
name|equals
argument_list|(
name|prefix
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"title"
argument_list|,
name|val
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"0"
operator|.
name|equals
argument_list|(
name|prefix
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
block|{
name|type
operator|=
literal|"article"
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|val
operator|.
name|indexOf
argument_list|(
literal|"Book Section"
argument_list|)
operator|==
literal|0
condition|)
block|{
name|type
operator|=
literal|"incollection"
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|val
operator|.
name|indexOf
argument_list|(
literal|"Book"
argument_list|)
operator|==
literal|0
condition|)
block|{
name|type
operator|=
literal|"book"
expr_stmt|;
block|}
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
name|type
operator|=
literal|"book"
expr_stmt|;
name|isEditedBook
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
block|{
name|type
operator|=
literal|"inproceedings"
expr_stmt|;
block|}
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
block|{
name|type
operator|=
literal|"techreport"
expr_stmt|;
block|}
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
block|{
name|type
operator|=
literal|"article"
expr_stmt|;
block|}
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
block|{
name|type
operator|=
literal|"phdthesis"
expr_stmt|;
block|}
else|else
block|{
name|type
operator|=
literal|"misc"
expr_stmt|;
comment|//
block|}
block|}
elseif|else
if|if
condition|(
literal|"7"
operator|.
name|equals
argument_list|(
name|prefix
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"edition"
argument_list|,
name|val
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"C"
operator|.
name|equals
argument_list|(
name|prefix
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"address"
argument_list|,
name|val
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"D"
operator|.
name|equals
argument_list|(
name|prefix
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"year"
argument_list|,
name|val
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"8"
operator|.
name|equals
argument_list|(
name|prefix
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"date"
argument_list|,
name|val
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"J"
operator|.
name|equals
argument_list|(
name|prefix
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
block|{
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
block|}
elseif|else
if|if
condition|(
literal|"B"
operator|.
name|equals
argument_list|(
name|prefix
argument_list|)
condition|)
block|{
comment|// This prefix stands for "journal" in a journal entry, and
comment|// "series" in a book entry.
if|if
condition|(
literal|"article"
operator|.
name|equals
argument_list|(
name|type
argument_list|)
condition|)
block|{
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
literal|"book"
operator|.
name|equals
argument_list|(
name|type
argument_list|)
operator|||
literal|"inbook"
operator|.
name|equals
argument_list|(
name|type
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"series"
argument_list|,
name|val
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|/* type = inproceedings */
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
block|}
elseif|else
if|if
condition|(
literal|"I"
operator|.
name|equals
argument_list|(
name|prefix
argument_list|)
condition|)
block|{
if|if
condition|(
literal|"phdthesis"
operator|.
name|equals
argument_list|(
name|type
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"school"
argument_list|,
name|val
argument_list|)
expr_stmt|;
block|}
else|else
block|{
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
block|}
comment|// replace single dash page ranges (23-45) with double dashes (23--45):
elseif|else
if|if
condition|(
literal|"P"
operator|.
name|equals
argument_list|(
name|prefix
argument_list|)
condition|)
block|{
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
block|}
elseif|else
if|if
condition|(
literal|"V"
operator|.
name|equals
argument_list|(
name|prefix
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"volume"
argument_list|,
name|val
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"N"
operator|.
name|equals
argument_list|(
name|prefix
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"number"
argument_list|,
name|val
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"U"
operator|.
name|equals
argument_list|(
name|prefix
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"url"
argument_list|,
name|val
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"R"
operator|.
name|equals
argument_list|(
name|prefix
argument_list|)
condition|)
block|{
name|String
name|doi
init|=
name|val
decl_stmt|;
if|if
condition|(
name|doi
operator|.
name|startsWith
argument_list|(
literal|"doi:"
argument_list|)
condition|)
block|{
name|doi
operator|=
name|doi
operator|.
name|substring
argument_list|(
literal|4
argument_list|)
expr_stmt|;
block|}
name|hm
operator|.
name|put
argument_list|(
literal|"doi"
argument_list|,
name|doi
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"O"
operator|.
name|equals
argument_list|(
name|prefix
argument_list|)
condition|)
block|{
comment|// Notes may contain Article number
if|if
condition|(
name|val
operator|.
name|startsWith
argument_list|(
literal|"Artn"
argument_list|)
condition|)
block|{
name|String
index|[]
name|tokens
init|=
name|val
operator|.
name|split
argument_list|(
literal|"\\s"
argument_list|)
decl_stmt|;
name|artnum
operator|=
name|tokens
index|[
literal|1
index|]
expr_stmt|;
block|}
else|else
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"note"
argument_list|,
name|val
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
literal|"K"
operator|.
name|equals
argument_list|(
name|prefix
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"keywords"
argument_list|,
name|val
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"X"
operator|.
name|equals
argument_list|(
name|prefix
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"abstract"
argument_list|,
name|val
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"9"
operator|.
name|equals
argument_list|(
name|prefix
argument_list|)
condition|)
block|{
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
block|{
name|type
operator|=
literal|"phdthesis"
expr_stmt|;
block|}
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
block|{
name|type
operator|=
literal|"mastersthesis"
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
literal|"F"
operator|.
name|equals
argument_list|(
name|prefix
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
name|BibEntry
operator|.
name|KEY_FIELD
argument_list|,
name|LabelPatternUtil
operator|.
name|checkLegalKey
argument_list|(
name|val
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|// For Edited Book, EndNote puts the editors in the author field.
comment|// We want them in the editor field so that bibtex knows it's an edited book
if|if
condition|(
name|isEditedBook
operator|&&
literal|""
operator|.
name|equals
argument_list|(
name|editor
argument_list|)
condition|)
block|{
name|editor
operator|=
name|author
expr_stmt|;
name|author
operator|=
literal|""
expr_stmt|;
block|}
comment|//fixauthorscomma
if|if
condition|(
operator|!
literal|""
operator|.
name|equals
argument_list|(
name|author
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"author"
argument_list|,
name|fixAuthor
argument_list|(
name|author
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
literal|""
operator|.
name|equals
argument_list|(
name|editor
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"editor"
argument_list|,
name|fixAuthor
argument_list|(
name|editor
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|//if pages missing and article number given, use the article number
if|if
condition|(
operator|(
operator|(
name|hm
operator|.
name|get
argument_list|(
literal|"pages"
argument_list|)
operator|==
literal|null
operator|)
operator|||
literal|"-"
operator|.
name|equals
argument_list|(
name|hm
operator|.
name|get
argument_list|(
literal|"pages"
argument_list|)
argument_list|)
operator|)
operator|&&
operator|!
literal|""
operator|.
name|equals
argument_list|(
name|artnum
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"pages"
argument_list|,
name|artnum
argument_list|)
expr_stmt|;
block|}
name|BibEntry
name|b
init|=
operator|new
name|BibEntry
argument_list|(
name|DEFAULT_BIBTEXENTRY_ID
argument_list|,
name|type
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
if|if
condition|(
operator|!
name|b
operator|.
name|getFieldNames
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|bibitems
operator|.
name|add
argument_list|(
name|b
argument_list|)
expr_stmt|;
block|}
block|}
return|return
operator|new
name|ParserResult
argument_list|(
name|bibitems
argument_list|)
return|;
block|}
comment|/**      * We must be careful about the author names, since they can be presented differently      * by different sources. Normally each %A tag brings one name, and we get the authors      * separated by " and ". This is the correct behaviour.      * One source lists the names separated by comma, with a comma at the end. We can detect      * this format and fix it.      * @param s The author string      * @return The fixed author string      */
DECL|method|fixAuthor (String s)
specifier|private
specifier|static
name|String
name|fixAuthor
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|int
name|index
init|=
name|s
operator|.
name|indexOf
argument_list|(
literal|" and "
argument_list|)
decl_stmt|;
if|if
condition|(
name|index
operator|>=
literal|0
condition|)
block|{
return|return
name|AuthorList
operator|.
name|fixAuthorLastNameFirst
argument_list|(
name|s
argument_list|)
return|;
block|}
comment|// Look for the comma at the end:
name|index
operator|=
name|s
operator|.
name|lastIndexOf
argument_list|(
literal|','
argument_list|)
expr_stmt|;
if|if
condition|(
name|index
operator|==
operator|(
name|s
operator|.
name|length
argument_list|()
operator|-
literal|1
operator|)
condition|)
block|{
name|String
name|mod
init|=
name|s
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|s
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
operator|.
name|replace
argument_list|(
literal|", "
argument_list|,
literal|" and "
argument_list|)
decl_stmt|;
return|return
name|AuthorList
operator|.
name|fixAuthorLastNameFirst
argument_list|(
name|mod
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|AuthorList
operator|.
name|fixAuthorLastNameFirst
argument_list|(
name|s
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

