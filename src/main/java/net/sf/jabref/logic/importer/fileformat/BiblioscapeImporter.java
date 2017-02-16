begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.importer.fileformat
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
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
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Objects
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
name|importer
operator|.
name|Importer
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
name|util
operator|.
name|FileExtensions
import|;
end_import

begin_import
import|import
name|org
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
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|FieldName
import|;
end_import

begin_comment
comment|/**  * Imports a Biblioscape Tag File. The format is described on  * http://www.biblioscape.com/download/Biblioscape8.pdf Several  * Biblioscape field types are ignored. Others are only included in the BibTeX  * field "comment".  */
end_comment

begin_class
DECL|class|BiblioscapeImporter
specifier|public
class|class
name|BiblioscapeImporter
extends|extends
name|Importer
block|{
annotation|@
name|Override
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Biblioscape"
return|;
block|}
annotation|@
name|Override
DECL|method|getExtensions ()
specifier|public
name|FileExtensions
name|getExtensions
parameter_list|()
block|{
return|return
name|FileExtensions
operator|.
name|BILBIOSCAPE
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
literal|"Imports a Biblioscape Tag File.\n"
operator|+
literal|"Several Biblioscape field types are ignored. Others are only included in the BibTeX field \"comment\"."
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
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|reader
argument_list|)
expr_stmt|;
return|return
literal|true
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
name|bibItems
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
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
name|StringBuilder
argument_list|>
name|lines
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
name|StringBuilder
name|previousLine
init|=
literal|null
decl_stmt|;
while|while
condition|(
operator|(
name|line
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
name|List
argument_list|<
name|String
argument_list|>
name|comments
init|=
operator|new
name|ArrayList
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
name|StringBuilder
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
name|FieldName
operator|.
name|AUTHOR
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
name|FieldName
operator|.
name|YEAR
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
name|FieldName
operator|.
name|VOLUME
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
name|FieldName
operator|.
name|NUMBER
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
name|FieldName
operator|.
name|KEYWORDS
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
name|FieldName
operator|.
name|NOTE
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
name|FieldName
operator|.
name|PUBLISHER
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
name|FieldName
operator|.
name|EDITION
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
name|FieldName
operator|.
name|ISBN
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
name|FieldName
operator|.
name|ABSTRACT
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
name|FieldName
operator|.
name|LANGUAGE
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
name|FieldName
operator|.
name|URL
else|:
name|FieldName
operator|.
name|PDF
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
name|FieldName
operator|.
name|ANNOTE
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
name|FieldName
operator|.
name|CHAPTER
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
name|BibEntry
operator|.
name|DEFAULT_TYPE
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
name|BibEntry
operator|.
name|DEFAULT_TYPE
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
name|FieldName
operator|.
name|JOURNAL
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
name|FieldName
operator|.
name|TITLE
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
name|FieldName
operator|.
name|BOOKTITLE
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
name|FieldName
operator|.
name|TITLE
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
name|FieldName
operator|.
name|BOOKTITLE
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
name|FieldName
operator|.
name|TITLE
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
name|FieldName
operator|.
name|PAGES
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
name|FieldName
operator|.
name|ADDRESS
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
name|hm
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|COMMENT
argument_list|,
name|String
operator|.
name|join
argument_list|(
literal|";"
argument_list|,
name|comments
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|BibEntry
name|b
init|=
operator|new
name|BibEntry
argument_list|(
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
name|previousLine
operator|=
operator|new
name|StringBuilder
argument_list|(
name|line
operator|.
name|substring
argument_list|(
literal|7
argument_list|)
argument_list|)
expr_stmt|;
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
operator|new
name|ParserResult
argument_list|()
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
operator|new
name|ParserResult
argument_list|(
name|bibItems
argument_list|)
return|;
block|}
block|}
end_class

end_unit

