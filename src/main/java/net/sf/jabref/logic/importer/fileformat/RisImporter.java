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
name|Locale
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
name|OS
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
name|FieldName
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
name|MonthUtil
import|;
end_import

begin_comment
comment|/**  * Imports a Biblioscape Tag File. The format is described on  * http://www.biblioscape.com/manual_bsp/Biblioscape_Tag_File.htm  * Several Biblioscape field types are ignored. Others are only included in the BibTeX  * field "comment".  */
end_comment

begin_class
DECL|class|RisImporter
specifier|public
class|class
name|RisImporter
extends|extends
name|Importer
block|{
DECL|field|RECOGNIZED_FORMAT_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|RECOGNIZED_FORMAT_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"TY  - .*"
argument_list|)
decl_stmt|;
annotation|@
name|Override
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"RIS"
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
name|RIS
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
literal|"Imports a Biblioscape Tag File."
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
comment|// Our strategy is to look for the "TY  - *" line.
return|return
name|reader
operator|.
name|lines
argument_list|()
operator|.
name|anyMatch
argument_list|(
name|line
lambda|->
name|RECOGNIZED_FORMAT_PATTERN
operator|.
name|matcher
argument_list|(
name|line
argument_list|)
operator|.
name|find
argument_list|()
argument_list|)
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
comment|//use optional here, so that no exception will be thrown if the file is empty
name|Optional
argument_list|<
name|String
argument_list|>
name|OptionalLines
init|=
name|reader
operator|.
name|lines
argument_list|()
operator|.
name|reduce
argument_list|(
parameter_list|(
name|line
parameter_list|,
name|nextline
parameter_list|)
lambda|->
name|line
operator|+
literal|"\n"
operator|+
name|nextline
argument_list|)
decl_stmt|;
name|String
name|linesAsString
init|=
name|OptionalLines
operator|.
name|isPresent
argument_list|()
condition|?
name|OptionalLines
operator|.
name|get
argument_list|()
else|:
literal|""
decl_stmt|;
name|String
index|[]
name|entries
init|=
name|linesAsString
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
literal|"ER  -.*\\n"
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
name|String
name|type
init|=
literal|""
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
name|startPage
init|=
literal|""
decl_stmt|;
name|String
name|endPage
init|=
literal|""
decl_stmt|;
name|String
name|comment
init|=
literal|""
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|fields
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
name|String
index|[]
name|lines
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
name|lines
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
name|lines
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
name|lines
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
operator|(
name|lines
index|[
name|j
operator|+
literal|1
index|]
operator|.
name|length
argument_list|()
operator|>=
literal|6
operator|)
operator|&&
operator|!
literal|"  - "
operator|.
name|equals
argument_list|(
name|lines
index|[
name|j
operator|+
literal|1
index|]
operator|.
name|substring
argument_list|(
literal|2
argument_list|,
literal|6
argument_list|)
argument_list|)
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
operator|&&
operator|!
name|Character
operator|.
name|isWhitespace
argument_list|(
name|lines
index|[
name|j
operator|+
literal|1
index|]
operator|.
name|charAt
argument_list|(
literal|0
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
name|lines
index|[
name|j
operator|+
literal|1
index|]
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
if|if
condition|(
name|entry
operator|.
name|length
argument_list|()
operator|<
literal|6
condition|)
block|{
continue|continue;
block|}
else|else
block|{
name|String
name|tag
init|=
name|entry
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|2
argument_list|)
decl_stmt|;
name|String
name|value
init|=
name|entry
operator|.
name|substring
argument_list|(
literal|6
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
if|if
condition|(
literal|"TY"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
condition|)
block|{
if|if
condition|(
literal|"BOOK"
operator|.
name|equals
argument_list|(
name|value
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
literal|"JOUR"
operator|.
name|equals
argument_list|(
name|value
argument_list|)
operator|||
literal|"MGZN"
operator|.
name|equals
argument_list|(
name|value
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
literal|"THES"
operator|.
name|equals
argument_list|(
name|value
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
literal|"UNPB"
operator|.
name|equals
argument_list|(
name|value
argument_list|)
condition|)
block|{
name|type
operator|=
literal|"unpublished"
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"RPRT"
operator|.
name|equals
argument_list|(
name|value
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
literal|"CONF"
operator|.
name|equals
argument_list|(
name|value
argument_list|)
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
literal|"CHAP"
operator|.
name|equals
argument_list|(
name|value
argument_list|)
condition|)
block|{
name|type
operator|=
literal|"incollection"
expr_stmt|;
comment|//"inbook";
block|}
elseif|else
if|if
condition|(
literal|"PAT"
operator|.
name|equals
argument_list|(
name|value
argument_list|)
condition|)
block|{
name|type
operator|=
literal|"patent"
expr_stmt|;
block|}
else|else
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
literal|"T1"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
operator|||
literal|"TI"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
condition|)
block|{
name|String
name|oldVal
init|=
name|fields
operator|.
name|get
argument_list|(
name|FieldName
operator|.
name|TITLE
argument_list|)
decl_stmt|;
if|if
condition|(
name|oldVal
operator|==
literal|null
condition|)
block|{
name|fields
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|TITLE
argument_list|,
name|value
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
name|fields
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|TITLE
argument_list|,
name|oldVal
operator|+
literal|" "
operator|+
name|value
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|fields
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|TITLE
argument_list|,
name|oldVal
operator|+
literal|": "
operator|+
name|value
argument_list|)
expr_stmt|;
block|}
block|}
name|fields
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|TITLE
argument_list|,
name|fields
operator|.
name|get
argument_list|(
name|FieldName
operator|.
name|TITLE
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"\\s+"
argument_list|,
literal|" "
argument_list|)
argument_list|)
expr_stmt|;
comment|// Normalize whitespaces
block|}
elseif|else
if|if
condition|(
literal|"BT"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
condition|)
block|{
name|fields
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|BOOKTITLE
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"T2"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
operator|||
literal|"JO"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
condition|)
block|{
name|fields
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|JOURNAL
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"T3"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
condition|)
block|{
name|fields
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|SERIES
argument_list|,
name|value
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
name|tag
argument_list|)
operator|||
literal|"A1"
operator|.
name|equals
argument_list|(
name|tag
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
name|value
expr_stmt|;
block|}
else|else
block|{
name|author
operator|+=
literal|" and "
operator|+
name|value
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
literal|"A2"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
operator|||
literal|"A3"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
operator|||
literal|"A4"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
condition|)
block|{
if|if
condition|(
name|editor
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|editor
operator|=
name|value
expr_stmt|;
block|}
else|else
block|{
name|editor
operator|+=
literal|" and "
operator|+
name|value
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
literal|"JA"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
operator|||
literal|"JF"
operator|.
name|equals
argument_list|(
name|tag
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
name|fields
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|BOOKTITLE
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|fields
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|JOURNAL
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
literal|"LA"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
condition|)
block|{
name|fields
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|LANGUAGE
argument_list|,
name|value
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
name|tag
argument_list|)
condition|)
block|{
name|fields
operator|.
name|put
argument_list|(
literal|"caption"
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"DB"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
condition|)
block|{
name|fields
operator|.
name|put
argument_list|(
literal|"database"
argument_list|,
name|value
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
name|tag
argument_list|)
condition|)
block|{
name|fields
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|NUMBER
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"SP"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
condition|)
block|{
name|startPage
operator|=
name|value
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"PB"
operator|.
name|equals
argument_list|(
name|tag
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
name|fields
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|SCHOOL
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|fields
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|PUBLISHER
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
literal|"AD"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
operator|||
literal|"CY"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
condition|)
block|{
name|fields
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|ADDRESS
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"EP"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
condition|)
block|{
name|endPage
operator|=
name|value
expr_stmt|;
if|if
condition|(
operator|!
name|endPage
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|endPage
operator|=
literal|"--"
operator|+
name|endPage
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
literal|"ET"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
condition|)
block|{
name|fields
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|EDITION
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"SN"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
condition|)
block|{
name|fields
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|ISSN
argument_list|,
name|value
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
name|tag
argument_list|)
condition|)
block|{
name|fields
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|VOLUME
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"N2"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
operator|||
literal|"AB"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
condition|)
block|{
name|String
name|oldAb
init|=
name|fields
operator|.
name|get
argument_list|(
name|FieldName
operator|.
name|ABSTRACT
argument_list|)
decl_stmt|;
if|if
condition|(
name|oldAb
operator|==
literal|null
condition|)
block|{
name|fields
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|ABSTRACT
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|fields
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|ABSTRACT
argument_list|,
name|oldAb
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
name|value
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
literal|"UR"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
condition|)
block|{
name|fields
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|URL
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|(
literal|"Y1"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
operator|||
literal|"PY"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
operator|||
literal|"DA"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
operator|)
operator|&&
operator|(
name|value
operator|.
name|length
argument_list|()
operator|>=
literal|4
operator|)
condition|)
block|{
name|fields
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|YEAR
argument_list|,
name|value
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|4
argument_list|)
argument_list|)
expr_stmt|;
name|String
index|[]
name|parts
init|=
name|value
operator|.
name|split
argument_list|(
literal|"/"
argument_list|)
decl_stmt|;
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
try|try
block|{
name|int
name|monthNumber
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|parts
index|[
literal|1
index|]
argument_list|)
decl_stmt|;
name|MonthUtil
operator|.
name|Month
name|month
init|=
name|MonthUtil
operator|.
name|getMonthByNumber
argument_list|(
name|monthNumber
argument_list|)
decl_stmt|;
if|if
condition|(
name|month
operator|.
name|isValid
argument_list|()
condition|)
block|{
name|fields
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|MONTH
argument_list|,
name|month
operator|.
name|bibtexFormat
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|ex
parameter_list|)
block|{
comment|// The month part is unparseable, so we ignore it.
block|}
block|}
block|}
elseif|else
if|if
condition|(
literal|"KW"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
condition|)
block|{
if|if
condition|(
name|fields
operator|.
name|containsKey
argument_list|(
name|FieldName
operator|.
name|KEYWORDS
argument_list|)
condition|)
block|{
name|String
name|kw
init|=
name|fields
operator|.
name|get
argument_list|(
name|FieldName
operator|.
name|KEYWORDS
argument_list|)
decl_stmt|;
name|fields
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|KEYWORDS
argument_list|,
name|kw
operator|+
literal|", "
operator|+
name|value
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|fields
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|KEYWORDS
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
literal|"U1"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
operator|||
literal|"U2"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
operator|||
literal|"N1"
operator|.
name|equals
argument_list|(
name|tag
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
literal|" "
expr_stmt|;
block|}
name|comment
operator|=
name|comment
operator|+
name|value
expr_stmt|;
block|}
comment|// Added ID import 2005.12.01, Morten Alver:
elseif|else
if|if
condition|(
literal|"ID"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
condition|)
block|{
name|fields
operator|.
name|put
argument_list|(
literal|"refid"
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"M3"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
operator|||
literal|"DO"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
condition|)
block|{
name|addDoi
argument_list|(
name|fields
argument_list|,
name|value
argument_list|)
expr_stmt|;
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
name|fixAuthorLastNameFirst
argument_list|(
name|author
argument_list|)
expr_stmt|;
name|fields
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|AUTHOR
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
name|fixAuthorLastNameFirst
argument_list|(
name|editor
argument_list|)
expr_stmt|;
name|fields
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|EDITOR
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
name|fields
operator|.
name|put
argument_list|(
literal|"comment"
argument_list|,
name|comment
argument_list|)
expr_stmt|;
block|}
name|fields
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|PAGES
argument_list|,
name|startPage
operator|+
name|endPage
argument_list|)
expr_stmt|;
block|}
name|BibEntry
name|b
init|=
operator|new
name|BibEntry
argument_list|(
name|type
argument_list|)
decl_stmt|;
comment|// Remove empty fields:
name|fields
operator|.
name|entrySet
argument_list|()
operator|.
name|removeIf
argument_list|(
name|key
lambda|->
operator|(
name|key
operator|.
name|getValue
argument_list|()
operator|==
literal|null
operator|)
operator|||
name|key
operator|.
name|getValue
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
comment|// create one here
name|b
operator|.
name|setField
argument_list|(
name|fields
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
operator|new
name|ParserResult
argument_list|(
name|bibitems
argument_list|)
return|;
block|}
DECL|method|addDoi (Map<String, String> hm, String val)
specifier|private
name|void
name|addDoi
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|hm
parameter_list|,
name|String
name|val
parameter_list|)
block|{
name|String
name|doi
init|=
name|val
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|ENGLISH
argument_list|)
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
name|FieldName
operator|.
name|DOI
argument_list|,
name|doi
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

