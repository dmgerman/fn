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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|bibtex
operator|.
name|EntryTypes
import|;
end_import

begin_comment
comment|/**  * Importer for the MEDLINE Plain format.  *  * check here for details on the format  * http://www.nlm.nih.gov/bsd/mms/medlineelements.html  *  * @author vegeziel  */
end_comment

begin_class
DECL|class|MedlinePlainImporter
specifier|public
class|class
name|MedlinePlainImporter
extends|extends
name|ImportFormat
block|{
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
literal|"MedlinePlain"
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
literal|"medlineplain"
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
comment|// Our strategy is to look for the "PMID  - *", "PMC.*-.*", or "PMCR.*-.*" line
comment|// (i.e., PubMed Unique Identifier, PubMed Central Identifier, PubMed Central Release)
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
literal|"PMID.*-.*"
argument_list|)
decl_stmt|;
name|Pattern
name|pat2
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"PMC.*-.*"
argument_list|)
decl_stmt|;
name|Pattern
name|pat3
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"PMCR.*-.*"
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
operator|||
name|pat2
operator|.
name|matcher
argument_list|(
name|str
argument_list|)
operator|.
name|find
argument_list|()
operator|||
name|pat3
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
literal|true
return|;
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
name|ArrayList
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
name|replace
argument_list|(
literal|"\u2013"
argument_list|,
literal|"-"
argument_list|)
operator|.
name|replace
argument_list|(
literal|"\u2014"
argument_list|,
literal|"--"
argument_list|)
operator|.
name|replace
argument_list|(
literal|"\u2015"
argument_list|,
literal|"--"
argument_list|)
operator|.
name|split
argument_list|(
literal|"\\n\\n"
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|entry1
range|:
name|entries
control|)
block|{
if|if
condition|(
name|entry1
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|||
operator|!
name|entry1
operator|.
name|contains
argument_list|(
literal|"-"
argument_list|)
condition|)
block|{
continue|continue;
block|}
name|String
name|type
init|=
literal|"misc"
decl_stmt|;
name|String
name|author
init|=
literal|""
decl_stmt|;
name|String
name|editor
init|=
literal|""
decl_stmt|;
name|String
name|comment
init|=
literal|""
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
index|[]
name|fields
init|=
name|entry1
operator|.
name|split
argument_list|(
literal|"\n"
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
name|StringBuilder
name|current
init|=
operator|new
name|StringBuilder
argument_list|(
name|fields
index|[
name|j
index|]
argument_list|)
decl_stmt|;
name|boolean
name|done
init|=
literal|false
decl_stmt|;
while|while
condition|(
operator|!
name|done
operator|&&
operator|(
name|j
operator|<
operator|(
name|fields
operator|.
name|length
operator|-
literal|1
operator|)
operator|)
condition|)
block|{
if|if
condition|(
name|fields
index|[
name|j
operator|+
literal|1
index|]
operator|.
name|length
argument_list|()
operator|<=
literal|4
condition|)
block|{
name|j
operator|++
expr_stmt|;
continue|continue;
block|}
if|if
condition|(
name|fields
index|[
name|j
operator|+
literal|1
index|]
operator|.
name|charAt
argument_list|(
literal|4
argument_list|)
operator|!=
literal|'-'
condition|)
block|{
if|if
condition|(
operator|(
name|current
operator|.
name|length
argument_list|()
operator|>
literal|0
operator|)
operator|&&
operator|!
name|Character
operator|.
name|isWhitespace
argument_list|(
name|current
operator|.
name|charAt
argument_list|(
name|current
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
argument_list|)
condition|)
block|{
name|current
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
expr_stmt|;
block|}
name|current
operator|.
name|append
argument_list|(
name|fields
index|[
name|j
operator|+
literal|1
index|]
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
name|j
operator|++
expr_stmt|;
block|}
else|else
block|{
name|done
operator|=
literal|true
expr_stmt|;
block|}
block|}
name|String
name|entry
init|=
name|current
operator|.
name|toString
argument_list|()
decl_stmt|;
name|String
name|lab
init|=
name|entry
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|entry
operator|.
name|indexOf
argument_list|(
literal|'-'
argument_list|)
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
name|String
name|val
init|=
name|entry
operator|.
name|substring
argument_list|(
name|entry
operator|.
name|indexOf
argument_list|(
literal|'-'
argument_list|)
operator|+
literal|1
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
if|if
condition|(
literal|"PT"
operator|.
name|equals
argument_list|(
name|lab
argument_list|)
condition|)
block|{
name|val
operator|=
name|val
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
if|if
condition|(
literal|"book"
operator|.
name|equals
argument_list|(
name|val
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
literal|"journal article"
operator|.
name|equals
argument_list|(
name|val
argument_list|)
operator|||
literal|"classical article"
operator|.
name|equals
argument_list|(
name|val
argument_list|)
operator|||
literal|"corrected and republished article"
operator|.
name|equals
argument_list|(
name|val
argument_list|)
operator|||
literal|"historical article"
operator|.
name|equals
argument_list|(
name|val
argument_list|)
operator|||
literal|"introductory journal article"
operator|.
name|equals
argument_list|(
name|val
argument_list|)
operator|||
literal|"newspaper article"
operator|.
name|equals
argument_list|(
name|val
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
literal|"clinical conference"
operator|.
name|equals
argument_list|(
name|val
argument_list|)
operator|||
literal|"consensus development conference"
operator|.
name|equals
argument_list|(
name|val
argument_list|)
operator|||
literal|"consensus development conference, nih"
operator|.
name|equals
argument_list|(
name|val
argument_list|)
condition|)
block|{
name|type
operator|=
literal|"conference"
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"technical report"
operator|.
name|equals
argument_list|(
name|val
argument_list|)
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
literal|"editorial"
operator|.
name|equals
argument_list|(
name|val
argument_list|)
condition|)
block|{
name|type
operator|=
literal|"inproceedings"
expr_stmt|;
comment|//"incollection";"inbook";
block|}
elseif|else
if|if
condition|(
literal|"overall"
operator|.
name|equals
argument_list|(
name|val
argument_list|)
condition|)
block|{
name|type
operator|=
literal|"proceedings"
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|""
operator|.
name|equals
argument_list|(
name|type
argument_list|)
condition|)
block|{
name|type
operator|=
literal|"other"
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
literal|"TI"
operator|.
name|equals
argument_list|(
name|lab
argument_list|)
condition|)
block|{
name|String
name|oldVal
init|=
name|hm
operator|.
name|get
argument_list|(
literal|"title"
argument_list|)
decl_stmt|;
if|if
condition|(
name|oldVal
operator|==
literal|null
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
else|else
block|{
if|if
condition|(
name|oldVal
operator|.
name|endsWith
argument_list|(
literal|":"
argument_list|)
operator|||
name|oldVal
operator|.
name|endsWith
argument_list|(
literal|"."
argument_list|)
operator|||
name|oldVal
operator|.
name|endsWith
argument_list|(
literal|"?"
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"title"
argument_list|,
name|oldVal
operator|+
literal|" "
operator|+
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
literal|"title"
argument_list|,
name|oldVal
operator|+
literal|": "
operator|+
name|val
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|// =
comment|// val;
elseif|else
if|if
condition|(
literal|"BTI"
operator|.
name|equals
argument_list|(
name|lab
argument_list|)
operator|||
literal|"CTI"
operator|.
name|equals
argument_list|(
name|lab
argument_list|)
condition|)
block|{
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
literal|"FAU"
operator|.
name|equals
argument_list|(
name|lab
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
literal|"FED"
operator|.
name|equals
argument_list|(
name|lab
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
literal|"JT"
operator|.
name|equals
argument_list|(
name|lab
argument_list|)
condition|)
block|{
if|if
condition|(
literal|"inproceedings"
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
literal|"booktitle"
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
literal|"PG"
operator|.
name|equals
argument_list|(
name|lab
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
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"PL"
operator|.
name|equals
argument_list|(
name|lab
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
literal|"IS"
operator|.
name|equals
argument_list|(
name|lab
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"issn"
argument_list|,
name|val
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"VI"
operator|.
name|equals
argument_list|(
name|lab
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
literal|"AB"
operator|.
name|equals
argument_list|(
name|lab
argument_list|)
condition|)
block|{
name|String
name|oldAb
init|=
name|hm
operator|.
name|get
argument_list|(
literal|"abstract"
argument_list|)
decl_stmt|;
if|if
condition|(
name|oldAb
operator|==
literal|null
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
else|else
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"abstract"
argument_list|,
name|oldAb
operator|+
literal|"\n"
operator|+
name|val
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
literal|"DP"
operator|.
name|equals
argument_list|(
name|lab
argument_list|)
condition|)
block|{
name|String
index|[]
name|parts
init|=
name|val
operator|.
name|split
argument_list|(
literal|" "
argument_list|)
decl_stmt|;
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
if|if
condition|(
operator|(
name|parts
operator|.
name|length
operator|>
literal|1
operator|)
operator|&&
operator|!
name|parts
index|[
literal|1
index|]
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"month"
argument_list|,
name|parts
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
literal|"MH"
operator|.
name|equals
argument_list|(
name|lab
argument_list|)
operator|||
literal|"OT"
operator|.
name|equals
argument_list|(
name|lab
argument_list|)
condition|)
block|{
if|if
condition|(
operator|!
name|hm
operator|.
name|containsKey
argument_list|(
literal|"keywords"
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
else|else
block|{
name|String
name|kw
init|=
name|hm
operator|.
name|get
argument_list|(
literal|"keywords"
argument_list|)
decl_stmt|;
name|hm
operator|.
name|put
argument_list|(
literal|"keywords"
argument_list|,
name|kw
operator|+
literal|", "
operator|+
name|val
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
literal|"CON"
operator|.
name|equals
argument_list|(
name|lab
argument_list|)
operator|||
literal|"CIN"
operator|.
name|equals
argument_list|(
name|lab
argument_list|)
operator|||
literal|"EIN"
operator|.
name|equals
argument_list|(
name|lab
argument_list|)
operator|||
literal|"EFR"
operator|.
name|equals
argument_list|(
name|lab
argument_list|)
operator|||
literal|"CRI"
operator|.
name|equals
argument_list|(
name|lab
argument_list|)
operator|||
literal|"CRF"
operator|.
name|equals
argument_list|(
name|lab
argument_list|)
operator|||
literal|"PRIN"
operator|.
name|equals
argument_list|(
name|lab
argument_list|)
operator|||
literal|"PROF"
operator|.
name|equals
argument_list|(
name|lab
argument_list|)
operator|||
literal|"RPI"
operator|.
name|equals
argument_list|(
name|lab
argument_list|)
operator|||
literal|"RPF"
operator|.
name|equals
argument_list|(
name|lab
argument_list|)
operator|||
literal|"RIN"
operator|.
name|equals
argument_list|(
name|lab
argument_list|)
operator|||
literal|"ROF"
operator|.
name|equals
argument_list|(
name|lab
argument_list|)
operator|||
literal|"UIN"
operator|.
name|equals
argument_list|(
name|lab
argument_list|)
operator|||
literal|"UOF"
operator|.
name|equals
argument_list|(
name|lab
argument_list|)
operator|||
literal|"SPIN"
operator|.
name|equals
argument_list|(
name|lab
argument_list|)
operator|||
literal|"ORI"
operator|.
name|equals
argument_list|(
name|lab
argument_list|)
condition|)
block|{
if|if
condition|(
operator|!
name|comment
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|comment
operator|=
name|comment
operator|+
literal|"\n"
expr_stmt|;
block|}
name|comment
operator|=
name|comment
operator|+
name|val
expr_stmt|;
block|}
comment|//                // Added ID import 2005.12.01, Morten Alver:
comment|//                else if (lab.equals("ID"))
comment|//                    hm.put("refid", val);
comment|//                    // Added doi import (sciencedirect.com) 2011.01.10, Alexander Hug<alexander@alexanderhug.info>
elseif|else
if|if
condition|(
literal|"AID"
operator|.
name|equals
argument_list|(
name|lab
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
name|replaceAll
argument_list|(
literal|"(?i)doi:"
argument_list|,
literal|""
argument_list|)
operator|.
name|trim
argument_list|()
expr_stmt|;
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
block|}
block|}
comment|// fix authors
if|if
condition|(
operator|!
name|author
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|author
operator|=
name|AuthorList
operator|.
name|fixAuthor_lastNameFirst
argument_list|(
name|author
argument_list|)
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
if|if
condition|(
operator|!
name|editor
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|editor
operator|=
name|AuthorList
operator|.
name|fixAuthor_lastNameFirst
argument_list|(
name|editor
argument_list|)
expr_stmt|;
name|hm
operator|.
name|put
argument_list|(
literal|"editor"
argument_list|,
name|editor
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|comment
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"comment"
argument_list|,
name|comment
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
comment|// Remove empty fields:
name|ArrayList
argument_list|<
name|Object
argument_list|>
name|toRemove
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|key
range|:
name|hm
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|String
name|content
init|=
name|key
operator|.
name|getValue
argument_list|()
decl_stmt|;
comment|// content can never be null so only check if content is empty
if|if
condition|(
name|content
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|toRemove
operator|.
name|add
argument_list|(
name|key
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|Object
name|aToRemove
range|:
name|toRemove
control|)
block|{
name|hm
operator|.
name|remove
argument_list|(
name|aToRemove
argument_list|)
expr_stmt|;
block|}
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

