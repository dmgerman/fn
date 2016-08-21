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
DECL|field|PMID_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|PMID_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"PMID.*-.*"
argument_list|)
decl_stmt|;
DECL|field|PMC_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|PMC_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"PMC.*-.*"
argument_list|)
decl_stmt|;
DECL|field|PMCR_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|PMCR_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"PMCR.*-.*"
argument_list|)
decl_stmt|;
DECL|field|CREATE_DATE_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|CREATE_DATE_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\d{4}/[0123]?\\d/\\s?[012]\\d:[0-5]\\d"
argument_list|)
decl_stmt|;
DECL|field|COMPLETE_DATE_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|COMPLETE_DATE_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\d{8}"
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
literal|"MedlinePlain"
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
name|MEDLINE_PLAIN
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
literal|"Importer for the MedlinePlain format."
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
comment|// Our strategy is to look for the "PMID  - *", "PMC.*-.*", or "PMCR.*-.*" line
comment|// (i.e., PubMed Unique Identifier, PubMed Central Identifier, PubMed Central Release)
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
name|PMID_PATTERN
operator|.
name|matcher
argument_list|(
name|str
argument_list|)
operator|.
name|find
argument_list|()
operator|||
name|PMC_PATTERN
operator|.
name|matcher
argument_list|(
name|str
argument_list|)
operator|.
name|find
argument_list|()
operator|||
name|PMCR_PATTERN
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
name|BibEntry
operator|.
name|DEFAULT_TYPE
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
name|lines
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
name|lines
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
name|lines
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
if|if
condition|(
operator|!
name|checkLineValidity
argument_list|(
name|entry
argument_list|)
condition|)
block|{
continue|continue;
block|}
name|String
name|label
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
name|value
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
name|label
argument_list|)
condition|)
block|{
name|type
operator|=
name|addSourceType
argument_list|(
name|value
argument_list|,
name|type
argument_list|)
expr_stmt|;
block|}
name|addDates
argument_list|(
name|fields
argument_list|,
name|label
argument_list|,
name|value
argument_list|)
expr_stmt|;
name|addAbstract
argument_list|(
name|fields
argument_list|,
name|label
argument_list|,
name|value
argument_list|)
expr_stmt|;
name|addTitles
argument_list|(
name|fields
argument_list|,
name|label
argument_list|,
name|value
argument_list|,
name|type
argument_list|)
expr_stmt|;
name|addIDs
argument_list|(
name|fields
argument_list|,
name|label
argument_list|,
name|value
argument_list|)
expr_stmt|;
name|addStandardNumber
argument_list|(
name|fields
argument_list|,
name|label
argument_list|,
name|value
argument_list|)
expr_stmt|;
if|if
condition|(
literal|"FAU"
operator|.
name|equals
argument_list|(
name|label
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
literal|"FED"
operator|.
name|equals
argument_list|(
name|label
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
comment|//store the fields in a map
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|hashMap
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
name|hashMap
operator|.
name|put
argument_list|(
literal|"PG"
argument_list|,
name|FieldName
operator|.
name|PAGES
argument_list|)
expr_stmt|;
name|hashMap
operator|.
name|put
argument_list|(
literal|"PL"
argument_list|,
name|FieldName
operator|.
name|ADDRESS
argument_list|)
expr_stmt|;
name|hashMap
operator|.
name|put
argument_list|(
literal|"PHST"
argument_list|,
literal|"history"
argument_list|)
expr_stmt|;
name|hashMap
operator|.
name|put
argument_list|(
literal|"PST"
argument_list|,
literal|"publication-status"
argument_list|)
expr_stmt|;
name|hashMap
operator|.
name|put
argument_list|(
literal|"VI"
argument_list|,
name|FieldName
operator|.
name|VOLUME
argument_list|)
expr_stmt|;
name|hashMap
operator|.
name|put
argument_list|(
literal|"LA"
argument_list|,
name|FieldName
operator|.
name|LANGUAGE
argument_list|)
expr_stmt|;
name|hashMap
operator|.
name|put
argument_list|(
literal|"PUBM"
argument_list|,
literal|"model"
argument_list|)
expr_stmt|;
name|hashMap
operator|.
name|put
argument_list|(
literal|"RN"
argument_list|,
literal|"registry-number"
argument_list|)
expr_stmt|;
name|hashMap
operator|.
name|put
argument_list|(
literal|"NM"
argument_list|,
literal|"substance-name"
argument_list|)
expr_stmt|;
name|hashMap
operator|.
name|put
argument_list|(
literal|"OCI"
argument_list|,
literal|"copyright-owner"
argument_list|)
expr_stmt|;
name|hashMap
operator|.
name|put
argument_list|(
literal|"CN"
argument_list|,
literal|"corporate"
argument_list|)
expr_stmt|;
name|hashMap
operator|.
name|put
argument_list|(
literal|"IP"
argument_list|,
name|FieldName
operator|.
name|ISSUE
argument_list|)
expr_stmt|;
name|hashMap
operator|.
name|put
argument_list|(
literal|"EN"
argument_list|,
name|FieldName
operator|.
name|EDITION
argument_list|)
expr_stmt|;
name|hashMap
operator|.
name|put
argument_list|(
literal|"GS"
argument_list|,
literal|"gene-symbol"
argument_list|)
expr_stmt|;
name|hashMap
operator|.
name|put
argument_list|(
literal|"GN"
argument_list|,
name|FieldName
operator|.
name|NOTE
argument_list|)
expr_stmt|;
name|hashMap
operator|.
name|put
argument_list|(
literal|"GR"
argument_list|,
literal|"grantno"
argument_list|)
expr_stmt|;
name|hashMap
operator|.
name|put
argument_list|(
literal|"SO"
argument_list|,
literal|"source"
argument_list|)
expr_stmt|;
name|hashMap
operator|.
name|put
argument_list|(
literal|"NR"
argument_list|,
literal|"number-of-references"
argument_list|)
expr_stmt|;
name|hashMap
operator|.
name|put
argument_list|(
literal|"SFM"
argument_list|,
literal|"space-flight-mission"
argument_list|)
expr_stmt|;
name|hashMap
operator|.
name|put
argument_list|(
literal|"STAT"
argument_list|,
literal|"status"
argument_list|)
expr_stmt|;
name|hashMap
operator|.
name|put
argument_list|(
literal|"SB"
argument_list|,
literal|"subset"
argument_list|)
expr_stmt|;
name|hashMap
operator|.
name|put
argument_list|(
literal|"OTO"
argument_list|,
literal|"termowner"
argument_list|)
expr_stmt|;
name|hashMap
operator|.
name|put
argument_list|(
literal|"OWN"
argument_list|,
name|FieldName
operator|.
name|OWNER
argument_list|)
expr_stmt|;
comment|//add the fields to hm
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
name|mapEntry
range|:
name|hashMap
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|String
name|medlineKey
init|=
name|mapEntry
operator|.
name|getKey
argument_list|()
decl_stmt|;
name|String
name|bibtexKey
init|=
name|mapEntry
operator|.
name|getValue
argument_list|()
decl_stmt|;
if|if
condition|(
name|medlineKey
operator|.
name|equals
argument_list|(
name|label
argument_list|)
condition|)
block|{
name|fields
operator|.
name|put
argument_list|(
name|bibtexKey
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
literal|"IRAD"
operator|.
name|equals
argument_list|(
name|label
argument_list|)
operator|||
literal|"IR"
operator|.
name|equals
argument_list|(
name|label
argument_list|)
operator|||
literal|"FIR"
operator|.
name|equals
argument_list|(
name|label
argument_list|)
condition|)
block|{
name|String
name|oldInvestigator
init|=
name|fields
operator|.
name|get
argument_list|(
literal|"investigator"
argument_list|)
decl_stmt|;
if|if
condition|(
name|oldInvestigator
operator|==
literal|null
condition|)
block|{
name|fields
operator|.
name|put
argument_list|(
literal|"investigator"
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
literal|"investigator"
argument_list|,
name|oldInvestigator
operator|+
literal|", "
operator|+
name|value
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
name|label
argument_list|)
operator|||
literal|"OT"
operator|.
name|equals
argument_list|(
name|label
argument_list|)
condition|)
block|{
if|if
condition|(
operator|!
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
else|else
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
block|}
elseif|else
if|if
condition|(
literal|"CON"
operator|.
name|equals
argument_list|(
name|label
argument_list|)
operator|||
literal|"CIN"
operator|.
name|equals
argument_list|(
name|label
argument_list|)
operator|||
literal|"EIN"
operator|.
name|equals
argument_list|(
name|label
argument_list|)
operator|||
literal|"EFR"
operator|.
name|equals
argument_list|(
name|label
argument_list|)
operator|||
literal|"CRI"
operator|.
name|equals
argument_list|(
name|label
argument_list|)
operator|||
literal|"CRF"
operator|.
name|equals
argument_list|(
name|label
argument_list|)
operator|||
literal|"PRIN"
operator|.
name|equals
argument_list|(
name|label
argument_list|)
operator|||
literal|"PROF"
operator|.
name|equals
argument_list|(
name|label
argument_list|)
operator|||
literal|"RPI"
operator|.
name|equals
argument_list|(
name|label
argument_list|)
operator|||
literal|"RPF"
operator|.
name|equals
argument_list|(
name|label
argument_list|)
operator|||
literal|"RIN"
operator|.
name|equals
argument_list|(
name|label
argument_list|)
operator|||
literal|"ROF"
operator|.
name|equals
argument_list|(
name|label
argument_list|)
operator|||
literal|"UIN"
operator|.
name|equals
argument_list|(
name|label
argument_list|)
operator|||
literal|"UOF"
operator|.
name|equals
argument_list|(
name|label
argument_list|)
operator|||
literal|"SPIN"
operator|.
name|equals
argument_list|(
name|label
argument_list|)
operator|||
literal|"ORI"
operator|.
name|equals
argument_list|(
name|label
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
name|value
expr_stmt|;
block|}
block|}
name|fixAuthors
argument_list|(
name|fields
argument_list|,
name|author
argument_list|,
name|FieldName
operator|.
name|AUTHOR
argument_list|)
expr_stmt|;
name|fixAuthors
argument_list|(
name|fields
argument_list|,
name|editor
argument_list|,
name|FieldName
operator|.
name|EDITOR
argument_list|)
expr_stmt|;
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
name|fields
operator|.
name|entrySet
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|n
lambda|->
name|n
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
operator|.
name|forEach
argument_list|(
name|fields
operator|::
name|remove
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
DECL|method|checkLineValidity (String line)
specifier|private
name|boolean
name|checkLineValidity
parameter_list|(
name|String
name|line
parameter_list|)
block|{
return|return
operator|(
name|line
operator|.
name|length
argument_list|()
operator|>=
literal|5
operator|)
operator|&&
operator|(
name|line
operator|.
name|charAt
argument_list|(
literal|4
argument_list|)
operator|==
literal|'-'
operator|)
return|;
block|}
DECL|method|addSourceType (String value, String type)
specifier|private
name|String
name|addSourceType
parameter_list|(
name|String
name|value
parameter_list|,
name|String
name|type
parameter_list|)
block|{
name|String
name|val
init|=
name|value
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|ENGLISH
argument_list|)
decl_stmt|;
name|String
name|theType
init|=
name|type
decl_stmt|;
switch|switch
condition|(
name|val
condition|)
block|{
case|case
literal|"book"
case|:
name|theType
operator|=
literal|"book"
expr_stmt|;
break|break;
case|case
literal|"journal article"
case|:
case|case
literal|"classical article"
case|:
case|case
literal|"corrected and republished article"
case|:
case|case
literal|"historical article"
case|:
case|case
literal|"introductory journal article"
case|:
case|case
literal|"newspaper article"
case|:
name|theType
operator|=
literal|"article"
expr_stmt|;
break|break;
case|case
literal|"clinical conference"
case|:
case|case
literal|"consensus development conference"
case|:
case|case
literal|"consensus development conference, nih"
case|:
name|theType
operator|=
literal|"conference"
expr_stmt|;
break|break;
case|case
literal|"technical report"
case|:
name|theType
operator|=
literal|"techreport"
expr_stmt|;
break|break;
case|case
literal|"editorial"
case|:
name|theType
operator|=
literal|"inproceedings"
expr_stmt|;
break|break;
case|case
literal|"overall"
case|:
name|theType
operator|=
literal|"proceedings"
expr_stmt|;
break|break;
default|default:
break|break;
block|}
if|if
condition|(
literal|""
operator|.
name|equals
argument_list|(
name|theType
argument_list|)
condition|)
block|{
name|theType
operator|=
literal|"other"
expr_stmt|;
block|}
return|return
name|theType
return|;
block|}
DECL|method|addStandardNumber (Map<String, String> hm, String lab, String value)
specifier|private
name|void
name|addStandardNumber
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
name|lab
parameter_list|,
name|String
name|value
parameter_list|)
block|{
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
name|String
name|key
init|=
name|FieldName
operator|.
name|ISSN
decl_stmt|;
comment|//it is possible to have two issn, one for electronic and for print
comment|//if there are two then it comes at the end in brackets (electronic) or (print)
comment|//so search for the brackets
if|if
condition|(
name|value
operator|.
name|indexOf
argument_list|(
literal|'('
argument_list|)
operator|>
literal|0
condition|)
block|{
name|int
name|keyStart
init|=
name|value
operator|.
name|indexOf
argument_list|(
literal|'('
argument_list|)
decl_stmt|;
name|int
name|keyEnd
init|=
name|value
operator|.
name|indexOf
argument_list|(
literal|')'
argument_list|)
decl_stmt|;
name|key
operator|=
name|value
operator|.
name|substring
argument_list|(
name|keyStart
operator|+
literal|1
argument_list|,
name|keyEnd
argument_list|)
operator|+
literal|"-"
operator|+
name|key
expr_stmt|;
name|String
name|numberValue
init|=
name|value
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|keyStart
operator|-
literal|1
argument_list|)
decl_stmt|;
name|hm
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|numberValue
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|hm
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
literal|"ISBN"
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
name|FieldName
operator|.
name|ISBN
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|fixAuthors (Map<String, String> hm, String author, String field)
specifier|private
name|void
name|fixAuthors
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
name|author
parameter_list|,
name|String
name|field
parameter_list|)
block|{
if|if
condition|(
operator|!
name|author
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|String
name|fixedAuthor
init|=
name|AuthorList
operator|.
name|fixAuthorLastNameFirst
argument_list|(
name|author
argument_list|)
decl_stmt|;
name|hm
operator|.
name|put
argument_list|(
name|field
argument_list|,
name|fixedAuthor
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|addIDs (Map<String, String> hm, String lab, String value)
specifier|private
name|void
name|addIDs
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
name|lab
parameter_list|,
name|String
name|value
parameter_list|)
block|{
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
name|key
init|=
literal|"article-id"
decl_stmt|;
name|String
name|idValue
init|=
name|value
decl_stmt|;
if|if
condition|(
name|value
operator|.
name|startsWith
argument_list|(
literal|"doi:"
argument_list|)
condition|)
block|{
name|idValue
operator|=
name|idValue
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
name|key
operator|=
name|FieldName
operator|.
name|DOI
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|value
operator|.
name|indexOf
argument_list|(
literal|'['
argument_list|)
operator|>
literal|0
condition|)
block|{
name|int
name|startOfIdentifier
init|=
name|value
operator|.
name|indexOf
argument_list|(
literal|'['
argument_list|)
decl_stmt|;
name|int
name|endOfIdentifier
init|=
name|value
operator|.
name|indexOf
argument_list|(
literal|']'
argument_list|)
decl_stmt|;
name|key
operator|=
literal|"article-"
operator|+
name|value
operator|.
name|substring
argument_list|(
name|startOfIdentifier
operator|+
literal|1
argument_list|,
name|endOfIdentifier
argument_list|)
expr_stmt|;
name|idValue
operator|=
name|value
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|startOfIdentifier
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
name|hm
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|idValue
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"LID"
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
literal|"location-id"
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"MID"
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
literal|"manuscript-id"
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"JID"
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
literal|"nlm-unique-id"
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"OID"
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
literal|"other-id"
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"SI"
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
literal|"second-id"
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|addTitles (Map<String, String> hm, String lab, String val, String type)
specifier|private
name|void
name|addTitles
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
name|lab
parameter_list|,
name|String
name|val
parameter_list|,
name|String
name|type
parameter_list|)
block|{
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
name|hm
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|TITLE
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
name|FieldName
operator|.
name|TITLE
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
name|FieldName
operator|.
name|TITLE
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
name|FieldName
operator|.
name|BOOKTITLE
argument_list|,
name|val
argument_list|)
expr_stmt|;
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
name|FieldName
operator|.
name|BOOKTITLE
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
name|FieldName
operator|.
name|JOURNAL
argument_list|,
name|val
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
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
literal|"collection-title"
argument_list|,
name|val
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
name|lab
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"title-abbreviation"
argument_list|,
name|val
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
name|lab
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"transliterated-title"
argument_list|,
name|val
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"VTI"
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
literal|"volume-title"
argument_list|,
name|val
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|addAbstract (Map<String, String> hm, String lab, String value)
specifier|private
name|void
name|addAbstract
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
name|lab
parameter_list|,
name|String
name|value
parameter_list|)
block|{
name|String
name|abstractValue
init|=
literal|""
decl_stmt|;
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
comment|//adds copyright information that comes at the end of an abstract
if|if
condition|(
name|value
operator|.
name|contains
argument_list|(
literal|"Copyright"
argument_list|)
condition|)
block|{
name|int
name|copyrightIndex
init|=
name|value
operator|.
name|lastIndexOf
argument_list|(
literal|"Copyright"
argument_list|)
decl_stmt|;
comment|//remove the copyright from the field since the name of the field is copyright
name|String
name|copyrightInfo
init|=
name|value
operator|.
name|substring
argument_list|(
name|copyrightIndex
argument_list|,
name|value
operator|.
name|length
argument_list|()
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"Copyright "
argument_list|,
literal|""
argument_list|)
decl_stmt|;
name|hm
operator|.
name|put
argument_list|(
literal|"copyright"
argument_list|,
name|copyrightInfo
argument_list|)
expr_stmt|;
name|abstractValue
operator|=
name|value
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|copyrightIndex
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|abstractValue
operator|=
name|value
expr_stmt|;
block|}
name|String
name|oldAb
init|=
name|hm
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
name|hm
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|ABSTRACT
argument_list|,
name|abstractValue
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|hm
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
name|abstractValue
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
literal|"OAB"
operator|.
name|equals
argument_list|(
name|lab
argument_list|)
operator|||
literal|"OABL"
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
literal|"other-abstract"
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|addDates (Map<String, String> hm, String lab, String val)
specifier|private
name|void
name|addDates
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
name|lab
parameter_list|,
name|String
name|val
parameter_list|)
block|{
if|if
condition|(
literal|"CRDT"
operator|.
name|equals
argument_list|(
name|lab
argument_list|)
operator|&&
name|isCreateDateFormat
argument_list|(
name|val
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"create-date"
argument_list|,
name|val
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"DEP"
operator|.
name|equals
argument_list|(
name|lab
argument_list|)
operator|&&
name|isDateFormat
argument_list|(
name|val
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"electronic-publication"
argument_list|,
name|val
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"DA"
operator|.
name|equals
argument_list|(
name|lab
argument_list|)
operator|&&
name|isDateFormat
argument_list|(
name|val
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"date-created"
argument_list|,
name|val
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"DCOM"
operator|.
name|equals
argument_list|(
name|lab
argument_list|)
operator|&&
name|isDateFormat
argument_list|(
name|val
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"completed"
argument_list|,
name|val
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"LR"
operator|.
name|equals
argument_list|(
name|lab
argument_list|)
operator|&&
name|isDateFormat
argument_list|(
name|val
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"revised"
argument_list|,
name|val
argument_list|)
expr_stmt|;
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
name|FieldName
operator|.
name|YEAR
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
name|FieldName
operator|.
name|MONTH
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
literal|"EDAT"
operator|.
name|equals
argument_list|(
name|lab
argument_list|)
operator|&&
name|isCreateDateFormat
argument_list|(
name|val
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"publication"
argument_list|,
name|val
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"MHDA"
operator|.
name|equals
argument_list|(
name|lab
argument_list|)
operator|&&
name|isCreateDateFormat
argument_list|(
name|val
argument_list|)
condition|)
block|{
name|hm
operator|.
name|put
argument_list|(
literal|"mesh-date"
argument_list|,
name|val
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|isCreateDateFormat (String value)
specifier|private
name|boolean
name|isCreateDateFormat
parameter_list|(
name|String
name|value
parameter_list|)
block|{
return|return
name|CREATE_DATE_PATTERN
operator|.
name|matcher
argument_list|(
name|value
argument_list|)
operator|.
name|matches
argument_list|()
return|;
block|}
DECL|method|isDateFormat (String value)
specifier|private
name|boolean
name|isDateFormat
parameter_list|(
name|String
name|value
parameter_list|)
block|{
return|return
name|COMPLETE_DATE_PATTERN
operator|.
name|matcher
argument_list|(
name|value
argument_list|)
operator|.
name|matches
argument_list|()
return|;
block|}
block|}
end_class

end_unit

