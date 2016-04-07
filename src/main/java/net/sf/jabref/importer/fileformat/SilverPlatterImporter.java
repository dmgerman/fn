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
name|importer
operator|.
name|ImportFormatReader
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
name|AuthorList
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
comment|/**  * Imports a SilverPlatter exported file. This is a poor format to parse,  * so it currently doesn't handle everything correctly.  */
end_comment

begin_class
DECL|class|SilverPlatterImporter
specifier|public
class|class
name|SilverPlatterImporter
extends|extends
name|ImportFormat
block|{
DECL|field|START_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|START_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"Record.*INSPEC.*"
argument_list|)
decl_stmt|;
comment|/**      * Return the name of this import format.      */
annotation|@
name|Override
DECL|method|getFormatName ()
specifier|public
name|String
name|getFormatName
parameter_list|()
block|{
return|return
literal|"SilverPlatter"
return|;
block|}
comment|/*      *  (non-Javadoc)      * @see net.sf.jabref.imports.ImportFormat#getCLIId()      */
annotation|@
name|Override
DECL|method|getCLIId ()
specifier|public
name|String
name|getCLIId
parameter_list|()
block|{
return|return
literal|"silverplatter"
return|;
block|}
comment|/**      * Check whether the source is in the correct format for this importer.      */
annotation|@
name|Override
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
try|try
init|(
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
init|)
block|{
comment|// This format is very similar to Inspec, so we have a two-fold strategy:
comment|// If we see the flag signaling that it is an Inspec file, return false.
comment|// This flag should appear above the first entry and prevent us from
comment|// accepting the Inspec format. Then we look for the title entry.
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
name|START_PATTERN
operator|.
name|matcher
argument_list|(
name|str
argument_list|)
operator|.
name|find
argument_list|()
condition|)
block|{
return|return
literal|false
return|;
comment|// This is an Inspec file, so return false.
block|}
if|if
condition|(
operator|(
name|str
operator|.
name|length
argument_list|()
operator|>=
literal|5
operator|)
operator|&&
literal|"TI:  "
operator|.
name|equals
argument_list|(
name|str
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|5
argument_list|)
argument_list|)
condition|)
block|{
return|return
literal|true
return|;
block|}
block|}
block|}
return|return
literal|false
return|;
block|}
comment|/**      * Parse the entries in the source, and return a List of BibEntry      * objects.      */
annotation|@
name|Override
DECL|method|importEntries (InputStream stream, OutputPrinter status)
specifier|public
name|List
argument_list|<
name|BibEntry
argument_list|>
name|importEntries
parameter_list|(
name|InputStream
name|stream
parameter_list|,
name|OutputPrinter
name|status
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
try|try
init|(
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
init|)
block|{
name|boolean
name|isChapter
init|=
literal|false
decl_stmt|;
name|String
name|str
decl_stmt|;
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
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
literal|2
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"__::__"
argument_list|)
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
literal|"__NEWFIELD__"
argument_list|)
operator|.
name|append
argument_list|(
name|str
argument_list|)
expr_stmt|;
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
literal|"__::__"
argument_list|)
decl_stmt|;
name|String
name|type
init|=
literal|""
decl_stmt|;
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|h
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|entry
range|:
name|entries
control|)
block|{
if|if
condition|(
name|entry
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|<
literal|6
condition|)
block|{
continue|continue;
block|}
name|h
operator|.
name|clear
argument_list|()
expr_stmt|;
name|String
index|[]
name|fields
init|=
name|entry
operator|.
name|split
argument_list|(
literal|"__NEWFIELD__"
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
literal|6
condition|)
block|{
continue|continue;
block|}
name|String
name|f3
init|=
name|field
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|2
argument_list|)
decl_stmt|;
name|String
name|frest
init|=
name|field
operator|.
name|substring
argument_list|(
literal|5
argument_list|)
decl_stmt|;
if|if
condition|(
literal|"TI"
operator|.
name|equals
argument_list|(
name|f3
argument_list|)
condition|)
block|{
name|h
operator|.
name|put
argument_list|(
literal|"title"
argument_list|,
name|frest
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"AU"
operator|.
name|equals
argument_list|(
name|f3
argument_list|)
condition|)
block|{
if|if
condition|(
name|frest
operator|.
name|trim
argument_list|()
operator|.
name|endsWith
argument_list|(
literal|"(ed)"
argument_list|)
condition|)
block|{
name|String
name|ed
init|=
name|frest
operator|.
name|trim
argument_list|()
decl_stmt|;
name|ed
operator|=
name|ed
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|ed
operator|.
name|length
argument_list|()
operator|-
literal|4
argument_list|)
expr_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"editor"
argument_list|,
name|AuthorList
operator|.
name|fixAuthorLastNameFirst
argument_list|(
name|ed
operator|.
name|replace
argument_list|(
literal|",-"
argument_list|,
literal|", "
argument_list|)
operator|.
name|replace
argument_list|(
literal|";"
argument_list|,
literal|" and "
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|h
operator|.
name|put
argument_list|(
literal|"author"
argument_list|,
name|AuthorList
operator|.
name|fixAuthorLastNameFirst
argument_list|(
name|frest
operator|.
name|replace
argument_list|(
literal|",-"
argument_list|,
literal|", "
argument_list|)
operator|.
name|replace
argument_list|(
literal|";"
argument_list|,
literal|" and "
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
literal|"AB"
operator|.
name|equals
argument_list|(
name|f3
argument_list|)
condition|)
block|{
name|h
operator|.
name|put
argument_list|(
literal|"abstract"
argument_list|,
name|frest
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"DE"
operator|.
name|equals
argument_list|(
name|f3
argument_list|)
condition|)
block|{
name|String
name|kw
init|=
name|frest
operator|.
name|replace
argument_list|(
literal|"-;"
argument_list|,
literal|","
argument_list|)
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"keywords"
argument_list|,
name|kw
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|kw
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"SO"
operator|.
name|equals
argument_list|(
name|f3
argument_list|)
condition|)
block|{
name|int
name|m
init|=
name|frest
operator|.
name|indexOf
argument_list|(
literal|'.'
argument_list|)
decl_stmt|;
if|if
condition|(
name|m
operator|>=
literal|0
condition|)
block|{
name|String
name|jr
init|=
name|frest
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|m
argument_list|)
decl_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"journal"
argument_list|,
name|jr
operator|.
name|replace
argument_list|(
literal|"-"
argument_list|,
literal|" "
argument_list|)
argument_list|)
expr_stmt|;
name|frest
operator|=
name|frest
operator|.
name|substring
argument_list|(
name|m
argument_list|)
expr_stmt|;
name|m
operator|=
name|frest
operator|.
name|indexOf
argument_list|(
literal|';'
argument_list|)
expr_stmt|;
if|if
condition|(
name|m
operator|>=
literal|5
condition|)
block|{
name|String
name|yr
init|=
name|frest
operator|.
name|substring
argument_list|(
name|m
operator|-
literal|5
argument_list|,
name|m
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"year"
argument_list|,
name|yr
argument_list|)
expr_stmt|;
name|frest
operator|=
name|frest
operator|.
name|substring
argument_list|(
name|m
argument_list|)
expr_stmt|;
name|m
operator|=
name|frest
operator|.
name|indexOf
argument_list|(
literal|':'
argument_list|)
expr_stmt|;
name|int
name|issueIndex
init|=
name|frest
operator|.
name|indexOf
argument_list|(
literal|'('
argument_list|)
decl_stmt|;
name|int
name|endIssueIndex
init|=
name|frest
operator|.
name|indexOf
argument_list|(
literal|')'
argument_list|)
decl_stmt|;
if|if
condition|(
name|m
operator|>=
literal|0
condition|)
block|{
name|String
name|pg
init|=
name|frest
operator|.
name|substring
argument_list|(
name|m
operator|+
literal|1
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"pages"
argument_list|,
name|pg
argument_list|)
expr_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"volume"
argument_list|,
name|frest
operator|.
name|substring
argument_list|(
literal|1
argument_list|,
name|issueIndex
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"issue"
argument_list|,
name|frest
operator|.
name|substring
argument_list|(
name|issueIndex
operator|+
literal|1
argument_list|,
name|endIssueIndex
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
elseif|else
if|if
condition|(
literal|"PB"
operator|.
name|equals
argument_list|(
name|f3
argument_list|)
condition|)
block|{
name|int
name|m
init|=
name|frest
operator|.
name|indexOf
argument_list|(
literal|':'
argument_list|)
decl_stmt|;
if|if
condition|(
name|m
operator|>=
literal|0
condition|)
block|{
name|String
name|jr
init|=
name|frest
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|m
argument_list|)
decl_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"publisher"
argument_list|,
name|jr
operator|.
name|replace
argument_list|(
literal|"-"
argument_list|,
literal|" "
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
name|frest
operator|=
name|frest
operator|.
name|substring
argument_list|(
name|m
argument_list|)
expr_stmt|;
name|m
operator|=
name|frest
operator|.
name|indexOf
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
if|if
condition|(
operator|(
name|m
operator|+
literal|2
operator|)
operator|<
name|frest
operator|.
name|length
argument_list|()
condition|)
block|{
name|String
name|yr
init|=
name|frest
operator|.
name|substring
argument_list|(
name|m
operator|+
literal|2
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
try|try
block|{
name|Integer
operator|.
name|parseInt
argument_list|(
name|yr
argument_list|)
expr_stmt|;
name|h
operator|.
name|put
argument_list|(
literal|"year"
argument_list|,
name|yr
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|ex
parameter_list|)
block|{
comment|// Let's assume that this wasn't a number, since it
comment|// couldn't be parsed as an integer.
block|}
block|}
block|}
block|}
elseif|else
if|if
condition|(
literal|"AF"
operator|.
name|equals
argument_list|(
name|f3
argument_list|)
condition|)
block|{
name|h
operator|.
name|put
argument_list|(
literal|"school"
argument_list|,
name|frest
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"DT"
operator|.
name|equals
argument_list|(
name|f3
argument_list|)
condition|)
block|{
name|frest
operator|=
name|frest
operator|.
name|trim
argument_list|()
expr_stmt|;
if|if
condition|(
literal|"Monograph"
operator|.
name|equals
argument_list|(
name|frest
argument_list|)
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
name|frest
operator|.
name|startsWith
argument_list|(
literal|"Dissertation"
argument_list|)
condition|)
block|{
name|type
operator|=
literal|"phdthesis"
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|frest
operator|.
name|toLowerCase
argument_list|()
operator|.
name|contains
argument_list|(
literal|"journal"
argument_list|)
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
literal|"Contribution"
operator|.
name|equals
argument_list|(
name|frest
argument_list|)
operator|||
literal|"Chapter"
operator|.
name|equals
argument_list|(
name|frest
argument_list|)
condition|)
block|{
name|type
operator|=
literal|"incollection"
expr_stmt|;
comment|// This entry type contains page numbers and booktitle in the
comment|// title field.
name|isChapter
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
name|type
operator|=
name|frest
operator|.
name|replace
argument_list|(
literal|" "
argument_list|,
literal|""
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|isChapter
condition|)
block|{
name|Object
name|titleO
init|=
name|h
operator|.
name|get
argument_list|(
literal|"title"
argument_list|)
decl_stmt|;
if|if
condition|(
name|titleO
operator|!=
literal|null
condition|)
block|{
name|String
name|title
init|=
operator|(
operator|(
name|String
operator|)
name|titleO
operator|)
operator|.
name|trim
argument_list|()
decl_stmt|;
name|int
name|inPos
init|=
name|title
operator|.
name|indexOf
argument_list|(
literal|"\" in "
argument_list|)
decl_stmt|;
if|if
condition|(
name|inPos
operator|>
literal|1
condition|)
block|{
name|h
operator|.
name|put
argument_list|(
literal|"title"
argument_list|,
name|title
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|inPos
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
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
block|}
return|return
name|bibitems
return|;
block|}
block|}
end_class

end_unit

