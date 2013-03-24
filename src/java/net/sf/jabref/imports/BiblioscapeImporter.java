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
name|OutputPrinter
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
name|bibItems
init|=
operator|new
name|ArrayList
argument_list|<
name|BibtexEntry
argument_list|>
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
name|HashMap
argument_list|<
name|String
argument_list|,
name|StringBuffer
argument_list|>
name|lines
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|StringBuffer
argument_list|>
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
name|length
argument_list|()
operator|==
literal|0
condition|)
continue|continue;
comment|// ignore empty lines, e.g. at file
comment|// end
comment|// entry delimiter -> item complete
if|if
condition|(
name|line
operator|.
name|equals
argument_list|(
literal|"------"
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
argument_list|<
name|String
argument_list|>
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
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"AU"
argument_list|)
condition|)
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
elseif|else
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"TI"
argument_list|)
condition|)
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
elseif|else
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"ST"
argument_list|)
condition|)
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
elseif|else
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"YP"
argument_list|)
condition|)
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
elseif|else
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
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
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"NB"
argument_list|)
condition|)
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
elseif|else
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"PS"
argument_list|)
condition|)
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
elseif|else
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"PE"
argument_list|)
condition|)
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
elseif|else
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"KW"
argument_list|)
condition|)
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
comment|//else if (entry.getKey().equals("RM"))
comment|// hm.put("",entry.getValue().toString());
comment|//else if (entry.getKey().equals("RU"))
comment|// hm.put("",entry.getValue().toString());
elseif|else
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"RT"
argument_list|)
condition|)
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
elseif|else
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"SB"
argument_list|)
condition|)
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
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"SA"
argument_list|)
condition|)
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
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"NT"
argument_list|)
condition|)
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
comment|//else if (entry.getKey().equals("PP"))
comment|// hm.put("",entry.getValue().toString());
elseif|else
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"PB"
argument_list|)
condition|)
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
elseif|else
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"TA"
argument_list|)
condition|)
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
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"TT"
argument_list|)
condition|)
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
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"ED"
argument_list|)
condition|)
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
comment|//else if (entry.getKey().equals("DP"))
comment|// hm.put("",entry.getValue().toString());
elseif|else
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"TW"
argument_list|)
condition|)
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
elseif|else
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"QA"
argument_list|)
condition|)
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
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"QT"
argument_list|)
condition|)
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
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
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
comment|//else if (entry.getKey().equals("LA"))
comment|// hm.put("",entry.getValue().toString());
elseif|else
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
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
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
comment|//else if (entry.getKey().equals("DI"))
comment|// hm.put("",entry.getValue().toString());
comment|//else if (entry.getKey().equals("DM"))
comment|// hm.put("",entry.getValue().toString());
comment|//else if (entry.getKey().equals("AV"))
comment|// hm.put("",entry.getValue().toString());
comment|//else if (entry.getKey().equals("PR"))
comment|// hm.put("",entry.getValue().toString());
comment|//else if (entry.getKey().equals("LO"))
comment|// hm.put("",entry.getValue().toString());
elseif|else
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"AD"
argument_list|)
condition|)
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
elseif|else
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"LG"
argument_list|)
condition|)
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
elseif|else
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"CO"
argument_list|)
condition|)
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
elseif|else
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"UR"
argument_list|)
operator|||
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"AT"
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
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"C1"
argument_list|)
condition|)
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
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"C2"
argument_list|)
condition|)
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
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"C3"
argument_list|)
condition|)
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
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"C4"
argument_list|)
condition|)
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
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
comment|//else if (entry.getKey().equals("RD"))
comment|// hm.put("",entry.getValue().toString());
comment|//else if (entry.getKey().equals("MB"))
comment|// hm.put("",entry.getValue().toString());
elseif|else
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"C5"
argument_list|)
condition|)
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
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"C6"
argument_list|)
condition|)
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
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
comment|//else if (entry.getKey().equals("FA"))
comment|// hm.put("",entry.getValue().toString());
comment|//else if (entry.getKey().equals("CN"))
comment|// hm.put("",entry.getValue().toString());
elseif|else
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"DE"
argument_list|)
condition|)
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
comment|//else if (entry.getKey().equals("RP"))
comment|// hm.put("",entry.getValue().toString());
comment|//else if (entry.getKey().equals("DF"))
comment|// hm.put("",entry.getValue().toString());
comment|//else if (entry.getKey().equals("RS"))
comment|// hm.put("",entry.getValue().toString());
elseif|else
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"CA"
argument_list|)
condition|)
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
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
comment|//else if (entry.getKey().equals("WP"))
comment|// hm.put("",entry.getValue().toString());
elseif|else
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"TH"
argument_list|)
condition|)
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
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
comment|//else if (entry.getKey().equals("WR"))
comment|// hm.put("",entry.getValue().toString());
comment|//else if (entry.getKey().equals("EW"))
comment|// hm.put("",entry.getValue().toString());
elseif|else
if|if
condition|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"SE"
argument_list|)
condition|)
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
name|i
operator|>=
literal|0
operator|&&
name|bibtexType
operator|.
name|equals
argument_list|(
literal|"misc"
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
continue|continue;
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
name|indexOf
argument_list|(
literal|"article"
argument_list|)
operator|>=
literal|0
condition|)
name|bibtexType
operator|=
literal|"article"
expr_stmt|;
elseif|else
if|if
condition|(
name|type
index|[
name|i
index|]
operator|.
name|indexOf
argument_list|(
literal|"journal"
argument_list|)
operator|>=
literal|0
condition|)
name|bibtexType
operator|=
literal|"article"
expr_stmt|;
elseif|else
if|if
condition|(
name|type
index|[
name|i
index|]
operator|.
name|indexOf
argument_list|(
literal|"book section"
argument_list|)
operator|>=
literal|0
condition|)
name|bibtexType
operator|=
literal|"inbook"
expr_stmt|;
elseif|else
if|if
condition|(
name|type
index|[
name|i
index|]
operator|.
name|indexOf
argument_list|(
literal|"book"
argument_list|)
operator|>=
literal|0
condition|)
name|bibtexType
operator|=
literal|"book"
expr_stmt|;
elseif|else
if|if
condition|(
name|type
index|[
name|i
index|]
operator|.
name|indexOf
argument_list|(
literal|"conference"
argument_list|)
operator|>=
literal|0
condition|)
name|bibtexType
operator|=
literal|"inproceedings"
expr_stmt|;
elseif|else
if|if
condition|(
name|type
index|[
name|i
index|]
operator|.
name|indexOf
argument_list|(
literal|"proceedings"
argument_list|)
operator|>=
literal|0
condition|)
name|bibtexType
operator|=
literal|"inproceedings"
expr_stmt|;
elseif|else
if|if
condition|(
name|type
index|[
name|i
index|]
operator|.
name|indexOf
argument_list|(
literal|"report"
argument_list|)
operator|>=
literal|0
condition|)
name|bibtexType
operator|=
literal|"techreport"
expr_stmt|;
elseif|else
if|if
condition|(
name|type
index|[
name|i
index|]
operator|.
name|indexOf
argument_list|(
literal|"thesis"
argument_list|)
operator|>=
literal|0
operator|&&
name|type
index|[
name|i
index|]
operator|.
name|indexOf
argument_list|(
literal|"master"
argument_list|)
operator|>=
literal|0
condition|)
name|bibtexType
operator|=
literal|"mastersthesis"
expr_stmt|;
elseif|else
if|if
condition|(
name|type
index|[
name|i
index|]
operator|.
name|indexOf
argument_list|(
literal|"thesis"
argument_list|)
operator|>=
literal|0
condition|)
name|bibtexType
operator|=
literal|"phdthesis"
expr_stmt|;
block|}
comment|// depending on bibtexType, decide where to place the titleRT and
comment|// titleTI
if|if
condition|(
name|bibtexType
operator|.
name|equals
argument_list|(
literal|"article"
argument_list|)
condition|)
block|{
if|if
condition|(
name|titleST
operator|!=
literal|null
condition|)
name|hm
operator|.
name|put
argument_list|(
literal|"journal"
argument_list|,
name|titleST
argument_list|)
expr_stmt|;
if|if
condition|(
name|titleTI
operator|!=
literal|null
condition|)
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
elseif|else
if|if
condition|(
name|bibtexType
operator|.
name|equals
argument_list|(
literal|"inbook"
argument_list|)
condition|)
block|{
if|if
condition|(
name|titleST
operator|!=
literal|null
condition|)
name|hm
operator|.
name|put
argument_list|(
literal|"booktitle"
argument_list|,
name|titleST
argument_list|)
expr_stmt|;
if|if
condition|(
name|titleTI
operator|!=
literal|null
condition|)
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
else|else
block|{
if|if
condition|(
name|titleST
operator|!=
literal|null
condition|)
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
comment|// happen, I
comment|// think
if|if
condition|(
name|titleTI
operator|!=
literal|null
condition|)
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
comment|// concatenate pages
if|if
condition|(
name|pages
index|[
literal|0
index|]
operator|!=
literal|null
operator|||
name|pages
index|[
literal|1
index|]
operator|!=
literal|null
condition|)
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
operator|!=
literal|null
condition|?
name|pages
index|[
literal|0
index|]
else|:
literal|""
operator|)
operator|+
operator|(
name|pages
index|[
literal|1
index|]
operator|!=
literal|null
condition|?
literal|"--"
operator|+
name|pages
index|[
literal|1
index|]
else|:
literal|""
operator|)
argument_list|)
expr_stmt|;
comment|// concatenate address and country
if|if
condition|(
name|address
operator|!=
literal|null
condition|)
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
operator|!=
literal|null
condition|?
literal|", "
operator|+
name|country
else|:
literal|""
operator|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|comments
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
comment|// set comment if present
name|StringBuffer
name|s
init|=
operator|new
name|StringBuffer
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
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
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
name|bibtexType
argument_list|)
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
name|line
operator|.
name|length
argument_list|()
operator|>=
literal|7
operator|&&
name|line
operator|.
name|substring
argument_list|(
literal|4
argument_list|,
literal|7
argument_list|)
operator|.
name|equals
argument_list|(
literal|"-- "
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
comment|// sanity check; should never happen
return|return
literal|null
return|;
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

