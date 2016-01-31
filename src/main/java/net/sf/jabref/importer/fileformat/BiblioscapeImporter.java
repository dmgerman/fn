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
name|*
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
name|bibtex
operator|.
name|EntryTypes
import|;
end_import

begin_comment
comment|/**  * Imports a Biblioscape Tag File. The format is described on  * http://www.biblioscape.com/manual_bsp/Biblioscape_Tag_File.htm Several  * Biblioscape field types are ignored. Others are only included in the BibTeX  * field "comment".  */
end_comment

begin_class
DECL|class|BiblioscapeImporter
specifier|public
class|class
name|BiblioscapeImporter
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
literal|"Biblioscape"
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
literal|"biblioscape"
return|;
block|}
comment|/**      * Check whether the source is in the correct format for this importer.      */
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
literal|true
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
name|bibItems
init|=
operator|new
name|ArrayList
argument_list|<>
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
name|Map
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
name|Map
argument_list|<
name|String
argument_list|,
name|StringBuffer
argument_list|>
name|lines
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
name|StringBuffer
name|previousLine
init|=
literal|null
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
name|isEmpty
argument_list|()
condition|)
block|{
continue|continue;
comment|// ignore empty lines, e.g. at file
block|}
comment|// end
comment|// entry delimiter -> item complete
if|if
condition|(
literal|"------"
operator|.
name|equals
argument_list|(
name|line
argument_list|)
condition|)
block|{
name|String
index|[]
name|type
init|=
operator|new
name|String
index|[
literal|2
index|]
decl_stmt|;
name|String
index|[]
name|pages
init|=
operator|new
name|String
index|[
literal|2
index|]
decl_stmt|;
name|String
name|country
init|=
literal|null
decl_stmt|;
name|String
name|address
init|=
literal|null
decl_stmt|;
name|String
name|titleST
init|=
literal|null
decl_stmt|;
name|String
name|titleTI
init|=
literal|null
decl_stmt|;
name|Vector
argument_list|<
name|String
argument_list|>
name|comments
init|=
operator|new
name|Vector
argument_list|<>
argument_list|()
decl_stmt|;
comment|// add item
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|StringBuffer
argument_list|>
name|entry
range|:
name|lines
operator|.
name|entrySet
argument_list|()
control|)
block|{
if|if
condition|(
literal|"AU"
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"author"
argument_list|,
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"TI"
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|titleTI
operator|=
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"ST"
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|titleST
operator|=
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"YP"
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"year"
argument_list|,
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"VL"
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"volume"
argument_list|,
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"NB"
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"number"
argument_list|,
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"PS"
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|pages
index|[
literal|0
index|]
operator|=
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"PE"
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|pages
index|[
literal|1
index|]
operator|=
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"KW"
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"keywords"
argument_list|,
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"RT"
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|type
index|[
literal|0
index|]
operator|=
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"SB"
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|comments
operator|.
name|add
argument_list|(
literal|"Subject: "
operator|+
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"SA"
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|comments
operator|.
name|add
argument_list|(
literal|"Secondary Authors: "
operator|+
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"NT"
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"note"
argument_list|,
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"PB"
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"publisher"
argument_list|,
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"TA"
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|comments
operator|.
name|add
argument_list|(
literal|"Tertiary Authors: "
operator|+
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"TT"
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|comments
operator|.
name|add
argument_list|(
literal|"Tertiary Title: "
operator|+
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"ED"
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"edition"
argument_list|,
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"TW"
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|type
index|[
literal|1
index|]
operator|=
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"QA"
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|comments
operator|.
name|add
argument_list|(
literal|"Quaternary Authors: "
operator|+
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"QT"
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|comments
operator|.
name|add
argument_list|(
literal|"Quaternary Title: "
operator|+
name|entry
operator|.
name|getValue
argument_list|()
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
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"isbn"
argument_list|,
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|toString
argument_list|()
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
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"abstract"
argument_list|,
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"AD"
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|address
operator|=
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"LG"
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"language"
argument_list|,
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"CO"
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|country
operator|=
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"UR"
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
operator|||
literal|"AT"
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|String
name|s
init|=
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|trim
argument_list|()
decl_stmt|;
name|hm
operator|.
name|put
argument_list|(
name|s
operator|.
name|startsWith
argument_list|(
literal|"http://"
argument_list|)
operator|||
name|s
operator|.
name|startsWith
argument_list|(
literal|"ftp://"
argument_list|)
condition|?
literal|"url"
else|:
literal|"pdf"
argument_list|,
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"C1"
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|comments
operator|.
name|add
argument_list|(
literal|"Custom1: "
operator|+
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"C2"
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|comments
operator|.
name|add
argument_list|(
literal|"Custom2: "
operator|+
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"C3"
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|comments
operator|.
name|add
argument_list|(
literal|"Custom3: "
operator|+
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"C4"
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|comments
operator|.
name|add
argument_list|(
literal|"Custom4: "
operator|+
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"C5"
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|comments
operator|.
name|add
argument_list|(
literal|"Custom5: "
operator|+
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"C6"
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|comments
operator|.
name|add
argument_list|(
literal|"Custom6: "
operator|+
name|entry
operator|.
name|getValue
argument_list|()
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
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"annote"
argument_list|,
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"CA"
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|comments
operator|.
name|add
argument_list|(
literal|"Categories: "
operator|+
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"TH"
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|comments
operator|.
name|add
argument_list|(
literal|"Short Title: "
operator|+
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"SE"
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"chapter"
argument_list|,
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
comment|//else if (entry.getKey().equals("AC"))
comment|// hm.put("",entry.getValue().toString());
comment|//else if (entry.getKey().equals("LP"))
comment|// hm.put("",entry.getValue().toString());
block|}
block|}
name|String
name|bibtexType
init|=
literal|"misc"
decl_stmt|;
comment|// to find type, first check TW, then RT
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
operator|(
name|i
operator|>=
literal|0
operator|)
operator|&&
literal|"misc"
operator|.
name|equals
argument_list|(
name|bibtexType
argument_list|)
condition|;
operator|--
name|i
control|)
block|{
if|if
condition|(
name|type
index|[
name|i
index|]
operator|==
literal|null
condition|)
block|{
continue|continue;
block|}
name|type
index|[
name|i
index|]
operator|=
name|type
index|[
name|i
index|]
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
if|if
condition|(
name|type
index|[
name|i
index|]
operator|.
name|contains
argument_list|(
literal|"article"
argument_list|)
condition|)
block|{
name|bibtexType
operator|=
literal|"article"
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|type
index|[
name|i
index|]
operator|.
name|contains
argument_list|(
literal|"journal"
argument_list|)
condition|)
block|{
name|bibtexType
operator|=
literal|"article"
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|type
index|[
name|i
index|]
operator|.
name|contains
argument_list|(
literal|"book section"
argument_list|)
condition|)
block|{
name|bibtexType
operator|=
literal|"inbook"
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|type
index|[
name|i
index|]
operator|.
name|contains
argument_list|(
literal|"book"
argument_list|)
condition|)
block|{
name|bibtexType
operator|=
literal|"book"
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|type
index|[
name|i
index|]
operator|.
name|contains
argument_list|(
literal|"conference"
argument_list|)
condition|)
block|{
name|bibtexType
operator|=
literal|"inproceedings"
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|type
index|[
name|i
index|]
operator|.
name|contains
argument_list|(
literal|"proceedings"
argument_list|)
condition|)
block|{
name|bibtexType
operator|=
literal|"inproceedings"
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|type
index|[
name|i
index|]
operator|.
name|contains
argument_list|(
literal|"report"
argument_list|)
condition|)
block|{
name|bibtexType
operator|=
literal|"techreport"
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|type
index|[
name|i
index|]
operator|.
name|contains
argument_list|(
literal|"thesis"
argument_list|)
operator|&&
name|type
index|[
name|i
index|]
operator|.
name|contains
argument_list|(
literal|"master"
argument_list|)
condition|)
block|{
name|bibtexType
operator|=
literal|"mastersthesis"
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|type
index|[
name|i
index|]
operator|.
name|contains
argument_list|(
literal|"thesis"
argument_list|)
condition|)
block|{
name|bibtexType
operator|=
literal|"phdthesis"
expr_stmt|;
block|}
block|}
comment|// depending on bibtexType, decide where to place the titleRT and
comment|// titleTI
if|if
condition|(
literal|"article"
operator|.
name|equals
argument_list|(
name|bibtexType
argument_list|)
condition|)
block|{
if|if
condition|(
name|titleST
operator|!=
literal|null
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"journal"
argument_list|,
name|titleST
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|titleTI
operator|!=
literal|null
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"title"
argument_list|,
name|titleTI
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
literal|"inbook"
operator|.
name|equals
argument_list|(
name|bibtexType
argument_list|)
condition|)
block|{
if|if
condition|(
name|titleST
operator|!=
literal|null
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"booktitle"
argument_list|,
name|titleST
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|titleTI
operator|!=
literal|null
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"title"
argument_list|,
name|titleTI
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
if|if
condition|(
name|titleST
operator|!=
literal|null
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"booktitle"
argument_list|,
name|titleST
argument_list|)
expr_stmt|;
comment|// should not
block|}
comment|// happen, I
comment|// think
if|if
condition|(
name|titleTI
operator|!=
literal|null
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"title"
argument_list|,
name|titleTI
argument_list|)
expr_stmt|;
block|}
block|}
comment|// concatenate pages
if|if
condition|(
operator|(
name|pages
index|[
literal|0
index|]
operator|!=
literal|null
operator|)
operator|||
operator|(
name|pages
index|[
literal|1
index|]
operator|!=
literal|null
operator|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"pages"
argument_list|,
operator|(
name|pages
index|[
literal|0
index|]
operator|==
literal|null
condition|?
literal|""
else|:
name|pages
index|[
literal|0
index|]
operator|)
operator|+
operator|(
name|pages
index|[
literal|1
index|]
operator|==
literal|null
condition|?
literal|""
else|:
literal|"--"
operator|+
name|pages
index|[
literal|1
index|]
operator|)
argument_list|)
expr_stmt|;
block|}
comment|// concatenate address and country
if|if
condition|(
name|address
operator|!=
literal|null
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"address"
argument_list|,
name|address
operator|+
operator|(
name|country
operator|==
literal|null
condition|?
literal|""
else|:
literal|", "
operator|+
name|country
operator|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|comments
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// set comment if present
name|StringBuilder
name|s
init|=
operator|new
name|StringBuilder
argument_list|()
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
name|comments
operator|.
name|size
argument_list|()
condition|;
operator|++
name|i
control|)
block|{
name|s
operator|.
name|append
argument_list|(
name|i
operator|>
literal|0
condition|?
literal|"; "
else|:
literal|""
argument_list|)
operator|.
name|append
argument_list|(
name|comments
operator|.
name|elementAt
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|hm
operator|.
name|put
argument_list|(
literal|"comment"
argument_list|,
name|s
operator|.
name|toString
argument_list|()
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
name|bibtexType
argument_list|)
decl_stmt|;
name|b
operator|.
name|setField
argument_list|(
name|hm
argument_list|)
expr_stmt|;
name|bibItems
operator|.
name|add
argument_list|(
name|b
argument_list|)
expr_stmt|;
name|hm
operator|.
name|clear
argument_list|()
expr_stmt|;
name|lines
operator|.
name|clear
argument_list|()
expr_stmt|;
name|previousLine
operator|=
literal|null
expr_stmt|;
continue|continue;
block|}
comment|// new key
if|if
condition|(
name|line
operator|.
name|startsWith
argument_list|(
literal|"--"
argument_list|)
operator|&&
operator|(
name|line
operator|.
name|length
argument_list|()
operator|>=
literal|7
operator|)
operator|&&
literal|"-- "
operator|.
name|equals
argument_list|(
name|line
operator|.
name|substring
argument_list|(
literal|4
argument_list|,
literal|7
argument_list|)
argument_list|)
condition|)
block|{
name|lines
operator|.
name|put
argument_list|(
name|line
operator|.
name|substring
argument_list|(
literal|2
argument_list|,
literal|4
argument_list|)
argument_list|,
name|previousLine
operator|=
operator|new
name|StringBuffer
argument_list|(
name|line
operator|.
name|substring
argument_list|(
literal|7
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
continue|continue;
block|}
comment|// continuation (folding) of previous line
if|if
condition|(
name|previousLine
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
name|previousLine
operator|.
name|append
argument_list|(
name|line
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|bibItems
return|;
block|}
block|}
end_class

end_unit

