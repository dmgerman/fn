begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.msbib
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|msbib
package|;
end_package

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
name|regex
operator|.
name|Matcher
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
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
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
name|MonthUtil
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
name|MonthUtil
operator|.
name|Month
import|;
end_import

begin_class
DECL|class|BibTeXConverter
specifier|public
class|class
name|BibTeXConverter
block|{
DECL|field|MSBIB_PREFIX
specifier|private
specifier|static
specifier|final
name|String
name|MSBIB_PREFIX
init|=
literal|"msbib-"
decl_stmt|;
DECL|method|BibTeXConverter ()
specifier|private
name|BibTeXConverter
parameter_list|()
block|{     }
comment|/**      * Converts an {@link MSBibEntry} to a {@link BibEntry} for import      * @param entry The MsBibEntry to convert      * @return The bib entry      */
DECL|method|convert (MSBibEntry entry)
specifier|public
specifier|static
name|BibEntry
name|convert
parameter_list|(
name|MSBibEntry
name|entry
parameter_list|)
block|{
name|BibEntry
name|result
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|fieldValues
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
name|String
name|bibTexEntryType
init|=
name|MSBibMapping
operator|.
name|getBiblatexEntryType
argument_list|(
name|entry
operator|.
name|getType
argument_list|()
argument_list|)
decl_stmt|;
name|result
operator|=
operator|new
name|BibEntry
argument_list|(
name|bibTexEntryType
argument_list|)
expr_stmt|;
comment|// add String fields
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
name|field
range|:
name|entry
operator|.
name|fields
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|String
name|msField
init|=
name|field
operator|.
name|getKey
argument_list|()
decl_stmt|;
name|String
name|value
init|=
name|field
operator|.
name|getValue
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|value
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|MSBibMapping
operator|.
name|getBibTeXField
argument_list|(
name|msField
argument_list|)
operator|!=
literal|null
operator|)
condition|)
block|{
name|fieldValues
operator|.
name|put
argument_list|(
name|MSBibMapping
operator|.
name|getBibTeXField
argument_list|(
name|msField
argument_list|)
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Value must be converted
if|if
condition|(
name|fieldValues
operator|.
name|containsKey
argument_list|(
name|FieldName
operator|.
name|LANGUAGE
argument_list|)
condition|)
block|{
name|int
name|lcid
init|=
name|Integer
operator|.
name|valueOf
argument_list|(
name|fieldValues
operator|.
name|get
argument_list|(
name|FieldName
operator|.
name|LANGUAGE
argument_list|)
argument_list|)
decl_stmt|;
name|fieldValues
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|LANGUAGE
argument_list|,
name|MSBibMapping
operator|.
name|getLanguage
argument_list|(
name|lcid
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|addAuthor
argument_list|(
name|fieldValues
argument_list|,
name|FieldName
operator|.
name|AUTHOR
argument_list|,
name|entry
operator|.
name|authors
argument_list|)
expr_stmt|;
name|addAuthor
argument_list|(
name|fieldValues
argument_list|,
name|MSBIB_PREFIX
operator|+
name|FieldName
operator|.
name|BOOKAUTHOR
argument_list|,
name|entry
operator|.
name|bookAuthors
argument_list|)
expr_stmt|;
name|addAuthor
argument_list|(
name|fieldValues
argument_list|,
name|FieldName
operator|.
name|EDITOR
argument_list|,
name|entry
operator|.
name|editors
argument_list|)
expr_stmt|;
name|addAuthor
argument_list|(
name|fieldValues
argument_list|,
name|MSBIB_PREFIX
operator|+
name|FieldName
operator|.
name|TRANSLATOR
argument_list|,
name|entry
operator|.
name|translators
argument_list|)
expr_stmt|;
name|addAuthor
argument_list|(
name|fieldValues
argument_list|,
name|MSBIB_PREFIX
operator|+
literal|"producername"
argument_list|,
name|entry
operator|.
name|producerNames
argument_list|)
expr_stmt|;
name|addAuthor
argument_list|(
name|fieldValues
argument_list|,
name|MSBIB_PREFIX
operator|+
literal|"composer"
argument_list|,
name|entry
operator|.
name|composers
argument_list|)
expr_stmt|;
name|addAuthor
argument_list|(
name|fieldValues
argument_list|,
name|MSBIB_PREFIX
operator|+
literal|"conductor"
argument_list|,
name|entry
operator|.
name|conductors
argument_list|)
expr_stmt|;
name|addAuthor
argument_list|(
name|fieldValues
argument_list|,
name|MSBIB_PREFIX
operator|+
literal|"performer"
argument_list|,
name|entry
operator|.
name|performers
argument_list|)
expr_stmt|;
name|addAuthor
argument_list|(
name|fieldValues
argument_list|,
name|MSBIB_PREFIX
operator|+
literal|"writer"
argument_list|,
name|entry
operator|.
name|writers
argument_list|)
expr_stmt|;
name|addAuthor
argument_list|(
name|fieldValues
argument_list|,
name|MSBIB_PREFIX
operator|+
literal|"director"
argument_list|,
name|entry
operator|.
name|directors
argument_list|)
expr_stmt|;
name|addAuthor
argument_list|(
name|fieldValues
argument_list|,
name|MSBIB_PREFIX
operator|+
literal|"compiler"
argument_list|,
name|entry
operator|.
name|compilers
argument_list|)
expr_stmt|;
name|addAuthor
argument_list|(
name|fieldValues
argument_list|,
name|MSBIB_PREFIX
operator|+
literal|"interviewer"
argument_list|,
name|entry
operator|.
name|interviewers
argument_list|)
expr_stmt|;
name|addAuthor
argument_list|(
name|fieldValues
argument_list|,
name|MSBIB_PREFIX
operator|+
literal|"interviewee"
argument_list|,
name|entry
operator|.
name|interviewees
argument_list|)
expr_stmt|;
name|addAuthor
argument_list|(
name|fieldValues
argument_list|,
name|MSBIB_PREFIX
operator|+
literal|"inventor"
argument_list|,
name|entry
operator|.
name|inventors
argument_list|)
expr_stmt|;
name|addAuthor
argument_list|(
name|fieldValues
argument_list|,
name|MSBIB_PREFIX
operator|+
literal|"counsel"
argument_list|,
name|entry
operator|.
name|counsels
argument_list|)
expr_stmt|;
if|if
condition|(
name|entry
operator|.
name|pages
operator|!=
literal|null
condition|)
block|{
name|fieldValues
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|PAGES
argument_list|,
name|entry
operator|.
name|pages
operator|.
name|toString
argument_list|(
literal|"--"
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|parseStandardNumber
argument_list|(
name|entry
operator|.
name|standardNumber
argument_list|,
name|fieldValues
argument_list|)
expr_stmt|;
if|if
condition|(
name|entry
operator|.
name|address
operator|!=
literal|null
condition|)
block|{
name|fieldValues
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|LOCATION
argument_list|,
name|entry
operator|.
name|address
argument_list|)
expr_stmt|;
block|}
comment|// TODO: ConferenceName is saved as booktitle when converting from MSBIB to BibTeX
if|if
condition|(
name|entry
operator|.
name|conferenceName
operator|!=
literal|null
condition|)
block|{
name|fieldValues
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|ORGANIZATION
argument_list|,
name|entry
operator|.
name|conferenceName
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|entry
operator|.
name|dateAccessed
operator|!=
literal|null
condition|)
block|{
name|fieldValues
operator|.
name|put
argument_list|(
name|MSBIB_PREFIX
operator|+
literal|"accessed"
argument_list|,
name|entry
operator|.
name|dateAccessed
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|entry
operator|.
name|journalName
operator|!=
literal|null
condition|)
block|{
name|fieldValues
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|JOURNAL
argument_list|,
name|entry
operator|.
name|journalName
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|entry
operator|.
name|month
operator|!=
literal|null
condition|)
block|{
name|Month
name|month
init|=
name|MonthUtil
operator|.
name|getMonth
argument_list|(
name|entry
operator|.
name|month
argument_list|)
decl_stmt|;
comment|//if we encouter an invalid month shortname would be null
if|if
condition|(
name|month
operator|.
name|isValid
argument_list|()
condition|)
block|{
name|fieldValues
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|MONTH
argument_list|,
name|month
operator|.
name|shortName
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|fieldValues
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|MONTH
argument_list|,
literal|""
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|entry
operator|.
name|number
operator|!=
literal|null
condition|)
block|{
name|fieldValues
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|NUMBER
argument_list|,
name|entry
operator|.
name|number
argument_list|)
expr_stmt|;
block|}
comment|// set all fields
name|result
operator|.
name|setField
argument_list|(
name|fieldValues
argument_list|)
expr_stmt|;
return|return
name|result
return|;
block|}
DECL|method|addAuthor (Map<String, String> map, String type, List<PersonName> authors)
specifier|private
specifier|static
name|void
name|addAuthor
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|map
parameter_list|,
name|String
name|type
parameter_list|,
name|List
argument_list|<
name|PersonName
argument_list|>
name|authors
parameter_list|)
block|{
if|if
condition|(
name|authors
operator|==
literal|null
condition|)
block|{
return|return;
block|}
name|String
name|allAuthors
init|=
name|authors
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|PersonName
operator|::
name|getFullname
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|joining
argument_list|(
literal|" and "
argument_list|)
argument_list|)
decl_stmt|;
name|map
operator|.
name|put
argument_list|(
name|type
argument_list|,
name|allAuthors
argument_list|)
expr_stmt|;
block|}
DECL|method|parseSingleStandardNumber (String type, String bibtype, String standardNum, Map<String, String> map)
specifier|private
specifier|static
name|void
name|parseSingleStandardNumber
parameter_list|(
name|String
name|type
parameter_list|,
name|String
name|bibtype
parameter_list|,
name|String
name|standardNum
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|map
parameter_list|)
block|{
name|Pattern
name|pattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|':'
operator|+
name|type
operator|+
literal|":(.[^:]+)"
argument_list|)
decl_stmt|;
name|Matcher
name|matcher
init|=
name|pattern
operator|.
name|matcher
argument_list|(
name|standardNum
argument_list|)
decl_stmt|;
if|if
condition|(
name|matcher
operator|.
name|matches
argument_list|()
condition|)
block|{
name|map
operator|.
name|put
argument_list|(
name|bibtype
argument_list|,
name|matcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|parseStandardNumber (String standardNum, Map<String, String> map)
specifier|private
specifier|static
name|void
name|parseStandardNumber
parameter_list|(
name|String
name|standardNum
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|map
parameter_list|)
block|{
if|if
condition|(
name|standardNum
operator|==
literal|null
condition|)
block|{
return|return;
block|}
name|parseSingleStandardNumber
argument_list|(
literal|"ISBN"
argument_list|,
name|FieldName
operator|.
name|ISBN
argument_list|,
name|standardNum
argument_list|,
name|map
argument_list|)
expr_stmt|;
name|parseSingleStandardNumber
argument_list|(
literal|"ISSN"
argument_list|,
name|FieldName
operator|.
name|ISSN
argument_list|,
name|standardNum
argument_list|,
name|map
argument_list|)
expr_stmt|;
name|parseSingleStandardNumber
argument_list|(
literal|"LCCN"
argument_list|,
literal|"lccn"
argument_list|,
name|standardNum
argument_list|,
name|map
argument_list|)
expr_stmt|;
name|parseSingleStandardNumber
argument_list|(
literal|"MRN"
argument_list|,
literal|"mrnumber"
argument_list|,
name|standardNum
argument_list|,
name|map
argument_list|)
expr_stmt|;
name|parseSingleStandardNumber
argument_list|(
literal|"DOI"
argument_list|,
name|FieldName
operator|.
name|DOI
argument_list|,
name|standardNum
argument_list|,
name|map
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

