begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.importer.fileformat
package|package
name|org
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
name|time
operator|.
name|Year
import|;
end_import

begin_import
import|import
name|java
operator|.
name|time
operator|.
name|format
operator|.
name|DateTimeFormatter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|time
operator|.
name|format
operator|.
name|DateTimeParseException
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
name|Arrays
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
name|org
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
name|org
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
name|org
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
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|StandardFileType
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
name|AuthorList
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
name|EntryType
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
name|Month
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
name|StandardEntryType
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
name|field
operator|.
name|Field
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
name|field
operator|.
name|StandardField
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
name|field
operator|.
name|UnknownField
import|;
end_import

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
DECL|field|formatter
specifier|private
specifier|static
name|DateTimeFormatter
name|formatter
init|=
name|DateTimeFormatter
operator|.
name|ofPattern
argument_list|(
literal|"yyyy"
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
DECL|method|getFileType ()
specifier|public
name|StandardFileType
name|getFileType
parameter_list|()
block|{
return|return
name|StandardFileType
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
name|String
name|linesAsString
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
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
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
comment|//stores all the date tags from highest to lowest priority
name|List
argument_list|<
name|String
argument_list|>
name|dateTags
init|=
name|Arrays
operator|.
name|asList
argument_list|(
literal|"Y1"
argument_list|,
literal|"PY"
argument_list|,
literal|"DA"
argument_list|,
literal|"Y2"
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
name|dateTag
init|=
literal|""
decl_stmt|;
name|String
name|dateValue
init|=
literal|""
decl_stmt|;
name|int
name|datePriority
init|=
name|dateTags
operator|.
name|size
argument_list|()
decl_stmt|;
name|int
name|tagPriority
decl_stmt|;
name|EntryType
name|type
init|=
name|StandardEntryType
operator|.
name|Misc
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
name|Optional
argument_list|<
name|Month
argument_list|>
name|month
init|=
name|Optional
operator|.
name|empty
argument_list|()
decl_stmt|;
name|Map
argument_list|<
name|Field
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
name|StandardEntryType
operator|.
name|Book
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
name|StandardEntryType
operator|.
name|Article
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
name|StandardEntryType
operator|.
name|PhdThesis
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
name|StandardEntryType
operator|.
name|Unpublished
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
name|StandardEntryType
operator|.
name|TechReport
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
name|StandardEntryType
operator|.
name|InProceedings
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
name|StandardEntryType
operator|.
name|InCollection
expr_stmt|;
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
name|StandardEntryType
operator|.
name|Patent
expr_stmt|;
block|}
else|else
block|{
name|type
operator|=
name|StandardEntryType
operator|.
name|Misc
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
name|StandardField
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
name|StandardField
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
name|StandardField
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
name|StandardField
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
name|StandardField
operator|.
name|TITLE
argument_list|,
name|fields
operator|.
name|get
argument_list|(
name|StandardField
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
name|StandardField
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
operator|(
literal|"T2"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
operator|||
literal|"J2"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
operator|||
literal|"JA"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
operator|)
operator|&&
operator|(
operator|(
name|fields
operator|.
name|get
argument_list|(
name|StandardField
operator|.
name|JOURNAL
argument_list|)
operator|==
literal|null
operator|)
operator|||
literal|""
operator|.
name|equals
argument_list|(
name|fields
operator|.
name|get
argument_list|(
name|StandardField
operator|.
name|JOURNAL
argument_list|)
argument_list|)
operator|)
condition|)
block|{
comment|//if there is no journal title, then put second title as journal title
name|fields
operator|.
name|put
argument_list|(
name|StandardField
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
literal|"JO"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
operator|||
literal|"J1"
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
comment|//if this field appears then this should be the journal title
name|fields
operator|.
name|put
argument_list|(
name|StandardField
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
name|StandardField
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
operator|||
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
literal|"ED"
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
name|type
operator|.
name|equals
argument_list|(
name|StandardEntryType
operator|.
name|InProceedings
argument_list|)
condition|)
block|{
name|fields
operator|.
name|put
argument_list|(
name|StandardField
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
name|StandardField
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
name|StandardField
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
operator|new
name|UnknownField
argument_list|(
literal|"caption"
argument_list|)
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
operator|new
name|UnknownField
argument_list|(
literal|"database"
argument_list|)
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
operator|||
literal|"AN"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
operator|||
literal|"C7"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
operator|||
literal|"M1"
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
name|StandardField
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
name|type
operator|.
name|equals
argument_list|(
name|StandardEntryType
operator|.
name|PhdThesis
argument_list|)
condition|)
block|{
name|fields
operator|.
name|put
argument_list|(
name|StandardField
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
name|StandardField
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
operator|||
literal|"PP"
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
name|StandardField
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
name|StandardField
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
name|StandardField
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
name|StandardField
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
name|StandardField
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
name|StandardField
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
name|StandardField
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
operator|||
literal|"L2"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
operator|||
literal|"LK"
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
name|StandardField
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
name|tagPriority
operator|=
name|dateTags
operator|.
name|indexOf
argument_list|(
name|tag
argument_list|)
operator|)
operator|!=
operator|-
literal|1
operator|&&
name|value
operator|.
name|length
argument_list|()
operator|>=
literal|4
condition|)
block|{
if|if
condition|(
name|tagPriority
operator|<
name|datePriority
condition|)
block|{
name|String
name|year
init|=
name|value
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|4
argument_list|)
decl_stmt|;
try|try
block|{
name|Year
operator|.
name|parse
argument_list|(
name|year
argument_list|,
name|formatter
argument_list|)
expr_stmt|;
comment|//if the year is parsebale we have found a higher priority date
name|dateTag
operator|=
name|tag
expr_stmt|;
name|dateValue
operator|=
name|value
expr_stmt|;
name|datePriority
operator|=
name|tagPriority
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|DateTimeParseException
name|ex
parameter_list|)
block|{
comment|//We can't parse the year, we ignore it
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
name|StandardField
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
name|StandardField
operator|.
name|KEYWORDS
argument_list|)
decl_stmt|;
name|fields
operator|.
name|put
argument_list|(
name|StandardField
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
name|StandardField
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
name|OS
operator|.
name|NEWLINE
expr_stmt|;
block|}
name|comment
operator|=
name|comment
operator|+
name|value
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
elseif|else
if|if
condition|(
literal|"C3"
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
name|StandardField
operator|.
name|EVENTTITLE
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"N1"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
operator|||
literal|"RN"
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
name|StandardField
operator|.
name|NOTE
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"ST"
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
name|StandardField
operator|.
name|SHORTTITLE
argument_list|,
name|value
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
name|tag
argument_list|)
condition|)
block|{
name|fields
operator|.
name|put
argument_list|(
name|StandardField
operator|.
name|EPRINT
argument_list|,
name|value
argument_list|)
expr_stmt|;
name|fields
operator|.
name|put
argument_list|(
name|StandardField
operator|.
name|EPRINTTYPE
argument_list|,
literal|"pubmed"
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
name|tag
argument_list|)
condition|)
block|{
name|fields
operator|.
name|put
argument_list|(
name|StandardField
operator|.
name|TRANSLATOR
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
comment|// fields for which there is no direct mapping in the bibtext standard
elseif|else
if|if
condition|(
literal|"AV"
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
operator|new
name|UnknownField
argument_list|(
literal|"archive_location"
argument_list|)
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"CN"
operator|.
name|equals
argument_list|(
name|tag
argument_list|)
operator|||
literal|"VO"
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
operator|new
name|UnknownField
argument_list|(
literal|"call-number"
argument_list|)
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
operator|new
name|UnknownField
argument_list|(
literal|"archive"
argument_list|)
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"NV"
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
operator|new
name|UnknownField
argument_list|(
literal|"number-of-volumes"
argument_list|)
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"OP"
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
operator|new
name|UnknownField
argument_list|(
literal|"original-title"
argument_list|)
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"RI"
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
operator|new
name|UnknownField
argument_list|(
literal|"reviewed-title"
argument_list|)
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"RP"
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
operator|new
name|UnknownField
argument_list|(
literal|"status"
argument_list|)
argument_list|,
name|value
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
name|tag
argument_list|)
condition|)
block|{
name|fields
operator|.
name|put
argument_list|(
operator|new
name|UnknownField
argument_list|(
literal|"section"
argument_list|)
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
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
operator|new
name|UnknownField
argument_list|(
literal|"refid"
argument_list|)
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
name|StandardField
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
name|StandardField
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
name|StandardField
operator|.
name|COMMENT
argument_list|,
name|comment
argument_list|)
expr_stmt|;
block|}
name|fields
operator|.
name|put
argument_list|(
name|StandardField
operator|.
name|PAGES
argument_list|,
name|startPage
operator|+
name|endPage
argument_list|)
expr_stmt|;
block|}
comment|// if we found a date
if|if
condition|(
name|dateTag
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|fields
operator|.
name|put
argument_list|(
name|StandardField
operator|.
name|YEAR
argument_list|,
name|dateValue
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
name|dateValue
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
name|month
operator|=
name|Month
operator|.
name|getMonthByNumber
argument_list|(
name|monthNumber
argument_list|)
expr_stmt|;
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
comment|// type is set in the loop above
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|(
name|type
argument_list|)
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|fields
argument_list|)
expr_stmt|;
comment|// month has a special treatment as we use the separate method "setMonth" of BibEntry instead of directly setting the value
name|month
operator|.
name|ifPresent
argument_list|(
name|entry
operator|::
name|setMonth
argument_list|)
expr_stmt|;
name|bibitems
operator|.
name|add
argument_list|(
name|entry
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
DECL|method|addDoi (Map<Field, String> hm, String val)
specifier|private
name|void
name|addDoi
parameter_list|(
name|Map
argument_list|<
name|Field
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
name|StandardField
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

