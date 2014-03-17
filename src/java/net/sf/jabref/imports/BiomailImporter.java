begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|OutputPrinter
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
comment|/**  * Importer for the ISI Web of Science format.  */
end_comment

begin_class
DECL|class|BiomailImporter
specifier|public
class|class
name|BiomailImporter
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
literal|"Biomail"
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
literal|"biomail"
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
comment|// Our strategy is to look for the "BioMail" line.
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
literal|"BioMail"
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
DECL|method|importEntries (InputStream stream, OutputPrinter status)
specifier|public
name|List
argument_list|<
name|BibtexEntry
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
name|ArrayList
argument_list|<
name|BibtexEntry
argument_list|>
name|bibitems
init|=
operator|new
name|ArrayList
argument_list|<
name|BibtexEntry
argument_list|>
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
literal|6
argument_list|)
operator|.
name|equals
argument_list|(
literal|"PMID- "
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
literal|6
argument_list|)
decl_stmt|;
if|if
condition|(
name|beg
operator|.
name|indexOf
argument_list|(
literal|" "
argument_list|)
operator|>
literal|0
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
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|hm
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
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
name|String
index|[]
name|fields
init|=
name|entry
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
name|entry
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
name|pages
init|=
literal|""
decl_stmt|;
name|String
name|shortauthor
init|=
literal|""
decl_stmt|;
name|String
name|fullauthor
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
name|String
name|field
range|:
name|fields
control|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|">>>"
operator|+
name|field
operator|+
literal|"<<<"
argument_list|)
expr_stmt|;
comment|//empty field don't do anything
if|if
condition|(
name|field
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
name|field
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|6
argument_list|)
decl_stmt|;
name|String
name|value
init|=
name|field
operator|.
name|substring
argument_list|(
literal|6
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
name|beg
operator|.
name|equals
argument_list|(
literal|"PT  - "
argument_list|)
condition|)
block|{
comment|// PT = value.replaceAll("JOURNAL ARTICLE", "article").replaceAll("Journal Article", "article");
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
literal|"TY  - "
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
literal|"JO  - "
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
literal|"FAU - "
argument_list|)
condition|)
block|{
name|String
name|tmpauthor
init|=
name|value
operator|.
name|replaceAll
argument_list|(
literal|"EOLEOL"
argument_list|,
literal|" and "
argument_list|)
decl_stmt|;
comment|// if there is already someone there then append with "and"
if|if
condition|(
operator|!
literal|""
operator|.
name|equals
argument_list|(
name|fullauthor
argument_list|)
condition|)
name|fullauthor
operator|=
name|fullauthor
operator|+
literal|" and "
operator|+
name|tmpauthor
expr_stmt|;
else|else
name|fullauthor
operator|=
name|tmpauthor
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|beg
operator|.
name|equals
argument_list|(
literal|"AU  - "
argument_list|)
condition|)
block|{
name|String
name|tmpauthor
init|=
name|value
operator|.
name|replaceAll
argument_list|(
literal|"EOLEOL"
argument_list|,
literal|" and "
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|" "
argument_list|,
literal|", "
argument_list|)
decl_stmt|;
comment|// if there is already someone there then append with "and"
if|if
condition|(
operator|!
literal|""
operator|.
name|equals
argument_list|(
name|shortauthor
argument_list|)
condition|)
name|shortauthor
operator|=
name|shortauthor
operator|+
literal|" and "
operator|+
name|tmpauthor
expr_stmt|;
else|else
name|shortauthor
operator|=
name|tmpauthor
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|beg
operator|.
name|equals
argument_list|(
literal|"TI  - "
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
literal|"TA  - "
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
literal|"AB  - "
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
literal|"PG  - "
argument_list|)
condition|)
name|pages
operator|=
name|value
operator|.
name|replaceAll
argument_list|(
literal|"-"
argument_list|,
literal|"--"
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|beg
operator|.
name|equals
argument_list|(
literal|"IP  - "
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
literal|"DP  - "
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
comment|// sometimes this is just year, sometimes full date
name|hm
operator|.
name|put
argument_list|(
literal|"year"
argument_list|,
name|parts
index|[
literal|0
index|]
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
literal|"VI  - "
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
literal|"AID - "
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
if|if
condition|(
literal|"[doi]"
operator|.
name|equals
argument_list|(
name|parts
index|[
literal|1
index|]
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"doi"
argument_list|,
name|parts
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|hm
operator|.
name|put
argument_list|(
literal|"url"
argument_list|,
literal|"http://dx.doi.org/"
operator|+
name|parts
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
block|}
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
if|if
condition|(
operator|!
literal|""
operator|.
name|equals
argument_list|(
name|fullauthor
argument_list|)
condition|)
name|hm
operator|.
name|put
argument_list|(
literal|"author"
argument_list|,
name|fullauthor
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
operator|!
literal|""
operator|.
name|equals
argument_list|(
name|shortauthor
argument_list|)
condition|)
name|hm
operator|.
name|put
argument_list|(
literal|"author"
argument_list|,
name|shortauthor
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
comment|// the first bibitem is always empty, presumably as a result of trying
comment|// to parse header informaion. So add only if we have at least author or
comment|// title fields.
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
operator|||
name|hm
operator|.
name|get
argument_list|(
literal|"title"
argument_list|)
operator|!=
literal|null
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

