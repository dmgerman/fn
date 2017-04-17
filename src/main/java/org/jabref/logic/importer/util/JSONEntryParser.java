begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.importer.util
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|util
package|;
end_package

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
name|List
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
name|Month
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_import
import|import
name|org
operator|.
name|json
operator|.
name|JSONArray
import|;
end_import

begin_import
import|import
name|org
operator|.
name|json
operator|.
name|JSONObject
import|;
end_import

begin_class
DECL|class|JSONEntryParser
specifier|public
class|class
name|JSONEntryParser
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|JSONEntryParser
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * Convert a JSONObject containing a bibJSON entry to a BibEntry      *      * @param bibJsonEntry The JSONObject to convert      * @return the converted BibEntry      */
DECL|method|parseBibJSONtoBibtex (JSONObject bibJsonEntry, Character keywordSeparator)
specifier|public
name|BibEntry
name|parseBibJSONtoBibtex
parameter_list|(
name|JSONObject
name|bibJsonEntry
parameter_list|,
name|Character
name|keywordSeparator
parameter_list|)
block|{
comment|// Fields that are directly accessible at the top level BibJson object
name|String
index|[]
name|singleFieldStrings
init|=
block|{
name|FieldName
operator|.
name|YEAR
block|,
name|FieldName
operator|.
name|TITLE
block|,
name|FieldName
operator|.
name|ABSTRACT
block|,
name|FieldName
operator|.
name|MONTH
block|}
decl_stmt|;
comment|// Fields that are accessible in the journal part of the BibJson object
name|String
index|[]
name|journalSingleFieldStrings
init|=
block|{
name|FieldName
operator|.
name|PUBLISHER
block|,
name|FieldName
operator|.
name|NUMBER
block|,
name|FieldName
operator|.
name|VOLUME
block|}
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setType
argument_list|(
literal|"article"
argument_list|)
expr_stmt|;
comment|// Authors
if|if
condition|(
name|bibJsonEntry
operator|.
name|has
argument_list|(
literal|"author"
argument_list|)
condition|)
block|{
name|JSONArray
name|authors
init|=
name|bibJsonEntry
operator|.
name|getJSONArray
argument_list|(
literal|"author"
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|authorList
init|=
operator|new
name|ArrayList
argument_list|<>
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
name|authors
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|authors
operator|.
name|getJSONObject
argument_list|(
name|i
argument_list|)
operator|.
name|has
argument_list|(
literal|"name"
argument_list|)
condition|)
block|{
name|authorList
operator|.
name|add
argument_list|(
name|authors
operator|.
name|getJSONObject
argument_list|(
name|i
argument_list|)
operator|.
name|getString
argument_list|(
literal|"name"
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Empty author name."
argument_list|)
expr_stmt|;
block|}
block|}
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|AUTHOR
argument_list|,
name|String
operator|.
name|join
argument_list|(
literal|" and "
argument_list|,
name|authorList
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"No author found."
argument_list|)
expr_stmt|;
block|}
comment|// Direct accessible fields
for|for
control|(
name|String
name|field
range|:
name|singleFieldStrings
control|)
block|{
if|if
condition|(
name|bibJsonEntry
operator|.
name|has
argument_list|(
name|field
argument_list|)
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
name|field
argument_list|,
name|bibJsonEntry
operator|.
name|getString
argument_list|(
name|field
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Page numbers
if|if
condition|(
name|bibJsonEntry
operator|.
name|has
argument_list|(
literal|"start_page"
argument_list|)
condition|)
block|{
if|if
condition|(
name|bibJsonEntry
operator|.
name|has
argument_list|(
literal|"end_page"
argument_list|)
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|PAGES
argument_list|,
name|bibJsonEntry
operator|.
name|getString
argument_list|(
literal|"start_page"
argument_list|)
operator|+
literal|"--"
operator|+
name|bibJsonEntry
operator|.
name|getString
argument_list|(
literal|"end_page"
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|PAGES
argument_list|,
name|bibJsonEntry
operator|.
name|getString
argument_list|(
literal|"start_page"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Journal
if|if
condition|(
name|bibJsonEntry
operator|.
name|has
argument_list|(
literal|"journal"
argument_list|)
condition|)
block|{
name|JSONObject
name|journal
init|=
name|bibJsonEntry
operator|.
name|getJSONObject
argument_list|(
literal|"journal"
argument_list|)
decl_stmt|;
comment|// Journal title
if|if
condition|(
name|journal
operator|.
name|has
argument_list|(
literal|"title"
argument_list|)
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|JOURNAL
argument_list|,
name|journal
operator|.
name|getString
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"No journal title found."
argument_list|)
expr_stmt|;
block|}
comment|// Other journal related fields
for|for
control|(
name|String
name|field
range|:
name|journalSingleFieldStrings
control|)
block|{
if|if
condition|(
name|journal
operator|.
name|has
argument_list|(
name|field
argument_list|)
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
name|field
argument_list|,
name|journal
operator|.
name|getString
argument_list|(
name|field
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"No journal information found."
argument_list|)
expr_stmt|;
block|}
comment|// Keywords
if|if
condition|(
name|bibJsonEntry
operator|.
name|has
argument_list|(
literal|"keywords"
argument_list|)
condition|)
block|{
name|JSONArray
name|keywords
init|=
name|bibJsonEntry
operator|.
name|getJSONArray
argument_list|(
literal|"keywords"
argument_list|)
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
name|keywords
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
operator|!
name|keywords
operator|.
name|isNull
argument_list|(
name|i
argument_list|)
condition|)
block|{
name|entry
operator|.
name|addKeyword
argument_list|(
name|keywords
operator|.
name|getString
argument_list|(
name|i
argument_list|)
argument_list|,
name|keywordSeparator
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|// Identifiers
if|if
condition|(
name|bibJsonEntry
operator|.
name|has
argument_list|(
literal|"identifier"
argument_list|)
condition|)
block|{
name|JSONArray
name|identifiers
init|=
name|bibJsonEntry
operator|.
name|getJSONArray
argument_list|(
literal|"identifier"
argument_list|)
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
name|identifiers
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|String
name|type
init|=
name|identifiers
operator|.
name|getJSONObject
argument_list|(
name|i
argument_list|)
operator|.
name|getString
argument_list|(
literal|"type"
argument_list|)
decl_stmt|;
if|if
condition|(
literal|"doi"
operator|.
name|equals
argument_list|(
name|type
argument_list|)
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|DOI
argument_list|,
name|identifiers
operator|.
name|getJSONObject
argument_list|(
name|i
argument_list|)
operator|.
name|getString
argument_list|(
literal|"id"
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"pissn"
operator|.
name|equals
argument_list|(
name|type
argument_list|)
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|ISSN
argument_list|,
name|identifiers
operator|.
name|getJSONObject
argument_list|(
name|i
argument_list|)
operator|.
name|getString
argument_list|(
literal|"id"
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"eissn"
operator|.
name|equals
argument_list|(
name|type
argument_list|)
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|ISSN
argument_list|,
name|identifiers
operator|.
name|getJSONObject
argument_list|(
name|i
argument_list|)
operator|.
name|getString
argument_list|(
literal|"id"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|// Links
if|if
condition|(
name|bibJsonEntry
operator|.
name|has
argument_list|(
literal|"link"
argument_list|)
condition|)
block|{
name|JSONArray
name|links
init|=
name|bibJsonEntry
operator|.
name|getJSONArray
argument_list|(
literal|"link"
argument_list|)
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
name|links
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|links
operator|.
name|getJSONObject
argument_list|(
name|i
argument_list|)
operator|.
name|has
argument_list|(
literal|"type"
argument_list|)
condition|)
block|{
name|String
name|type
init|=
name|links
operator|.
name|getJSONObject
argument_list|(
name|i
argument_list|)
operator|.
name|getString
argument_list|(
literal|"type"
argument_list|)
decl_stmt|;
if|if
condition|(
literal|"fulltext"
operator|.
name|equals
argument_list|(
name|type
argument_list|)
operator|&&
name|links
operator|.
name|getJSONObject
argument_list|(
name|i
argument_list|)
operator|.
name|has
argument_list|(
literal|"url"
argument_list|)
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|URL
argument_list|,
name|links
operator|.
name|getJSONObject
argument_list|(
name|i
argument_list|)
operator|.
name|getString
argument_list|(
literal|"url"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
return|return
name|entry
return|;
block|}
comment|/**      * Convert a JSONObject obtained from http://api.springer.com/metadata/json to a BibEntry      *      * @param springerJsonEntry the JSONObject from search results      * @return the converted BibEntry      */
DECL|method|parseSpringerJSONtoBibtex (JSONObject springerJsonEntry)
specifier|public
specifier|static
name|BibEntry
name|parseSpringerJSONtoBibtex
parameter_list|(
name|JSONObject
name|springerJsonEntry
parameter_list|)
block|{
comment|// Fields that are directly accessible at the top level Json object
name|String
index|[]
name|singleFieldStrings
init|=
block|{
name|FieldName
operator|.
name|ISSN
block|,
name|FieldName
operator|.
name|VOLUME
block|,
name|FieldName
operator|.
name|ABSTRACT
block|,
name|FieldName
operator|.
name|DOI
block|,
name|FieldName
operator|.
name|TITLE
block|,
name|FieldName
operator|.
name|NUMBER
block|,
name|FieldName
operator|.
name|PUBLISHER
block|}
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|String
name|nametype
decl_stmt|;
comment|// Guess publication type
name|String
name|isbn
init|=
name|springerJsonEntry
operator|.
name|optString
argument_list|(
literal|"isbn"
argument_list|)
decl_stmt|;
if|if
condition|(
name|com
operator|.
name|google
operator|.
name|common
operator|.
name|base
operator|.
name|Strings
operator|.
name|isNullOrEmpty
argument_list|(
name|isbn
argument_list|)
condition|)
block|{
comment|// Probably article
name|entry
operator|.
name|setType
argument_list|(
literal|"article"
argument_list|)
expr_stmt|;
name|nametype
operator|=
name|FieldName
operator|.
name|JOURNAL
expr_stmt|;
block|}
else|else
block|{
comment|// Probably book chapter or from proceeding, go for book chapter
name|entry
operator|.
name|setType
argument_list|(
literal|"incollection"
argument_list|)
expr_stmt|;
name|nametype
operator|=
name|FieldName
operator|.
name|BOOKTITLE
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|ISBN
argument_list|,
name|isbn
argument_list|)
expr_stmt|;
block|}
comment|// Authors
if|if
condition|(
name|springerJsonEntry
operator|.
name|has
argument_list|(
literal|"creators"
argument_list|)
condition|)
block|{
name|JSONArray
name|authors
init|=
name|springerJsonEntry
operator|.
name|getJSONArray
argument_list|(
literal|"creators"
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|authorList
init|=
operator|new
name|ArrayList
argument_list|<>
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
name|authors
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|authors
operator|.
name|getJSONObject
argument_list|(
name|i
argument_list|)
operator|.
name|has
argument_list|(
literal|"creator"
argument_list|)
condition|)
block|{
name|authorList
operator|.
name|add
argument_list|(
name|authors
operator|.
name|getJSONObject
argument_list|(
name|i
argument_list|)
operator|.
name|getString
argument_list|(
literal|"creator"
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Empty author name."
argument_list|)
expr_stmt|;
block|}
block|}
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|AUTHOR
argument_list|,
name|String
operator|.
name|join
argument_list|(
literal|" and "
argument_list|,
name|authorList
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"No author found."
argument_list|)
expr_stmt|;
block|}
comment|// Direct accessible fields
for|for
control|(
name|String
name|field
range|:
name|singleFieldStrings
control|)
block|{
if|if
condition|(
name|springerJsonEntry
operator|.
name|has
argument_list|(
name|field
argument_list|)
condition|)
block|{
name|String
name|text
init|=
name|springerJsonEntry
operator|.
name|getString
argument_list|(
name|field
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|text
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
name|field
argument_list|,
name|text
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|// Page numbers
if|if
condition|(
name|springerJsonEntry
operator|.
name|has
argument_list|(
literal|"startingPage"
argument_list|)
operator|&&
operator|!
operator|(
name|springerJsonEntry
operator|.
name|getString
argument_list|(
literal|"startingPage"
argument_list|)
operator|.
name|isEmpty
argument_list|()
operator|)
condition|)
block|{
if|if
condition|(
name|springerJsonEntry
operator|.
name|has
argument_list|(
literal|"endPage"
argument_list|)
operator|&&
operator|!
operator|(
name|springerJsonEntry
operator|.
name|getString
argument_list|(
literal|"endPage"
argument_list|)
operator|.
name|isEmpty
argument_list|()
operator|)
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|PAGES
argument_list|,
name|springerJsonEntry
operator|.
name|getString
argument_list|(
literal|"startingPage"
argument_list|)
operator|+
literal|"--"
operator|+
name|springerJsonEntry
operator|.
name|getString
argument_list|(
literal|"endPage"
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|PAGES
argument_list|,
name|springerJsonEntry
operator|.
name|getString
argument_list|(
literal|"startingPage"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Journal
if|if
condition|(
name|springerJsonEntry
operator|.
name|has
argument_list|(
literal|"publicationName"
argument_list|)
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
name|nametype
argument_list|,
name|springerJsonEntry
operator|.
name|getString
argument_list|(
literal|"publicationName"
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// URL
if|if
condition|(
name|springerJsonEntry
operator|.
name|has
argument_list|(
literal|"url"
argument_list|)
condition|)
block|{
name|JSONArray
name|urlarray
init|=
name|springerJsonEntry
operator|.
name|optJSONArray
argument_list|(
literal|"url"
argument_list|)
decl_stmt|;
if|if
condition|(
name|urlarray
operator|==
literal|null
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|URL
argument_list|,
name|springerJsonEntry
operator|.
name|optString
argument_list|(
literal|"url"
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|URL
argument_list|,
name|urlarray
operator|.
name|getJSONObject
argument_list|(
literal|0
argument_list|)
operator|.
name|optString
argument_list|(
literal|"value"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Date
if|if
condition|(
name|springerJsonEntry
operator|.
name|has
argument_list|(
literal|"publicationDate"
argument_list|)
condition|)
block|{
name|String
name|date
init|=
name|springerJsonEntry
operator|.
name|getString
argument_list|(
literal|"publicationDate"
argument_list|)
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|DATE
argument_list|,
name|date
argument_list|)
expr_stmt|;
comment|// For biblatex
name|String
index|[]
name|dateparts
init|=
name|date
operator|.
name|split
argument_list|(
literal|"-"
argument_list|)
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|YEAR
argument_list|,
name|dateparts
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|Optional
argument_list|<
name|Month
argument_list|>
name|month
init|=
name|Month
operator|.
name|getMonthByNumber
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|dateparts
index|[
literal|1
index|]
argument_list|)
argument_list|)
decl_stmt|;
name|month
operator|.
name|ifPresent
argument_list|(
name|entry
operator|::
name|setMonth
argument_list|)
expr_stmt|;
block|}
comment|// Clean up abstract (often starting with Abstract)
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|ABSTRACT
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|abstractContents
lambda|->
block|{
if|if
condition|(
name|abstractContents
operator|.
name|startsWith
argument_list|(
literal|"Abstract"
argument_list|)
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|ABSTRACT
argument_list|,
name|abstractContents
operator|.
name|substring
argument_list|(
literal|8
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
return|return
name|entry
return|;
block|}
block|}
end_class

end_unit

