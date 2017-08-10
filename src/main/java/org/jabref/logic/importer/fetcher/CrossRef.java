begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.importer.fetcher
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|fetcher
package|;
end_package

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|MalformedURLException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URISyntaxException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
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
name|logic
operator|.
name|formatter
operator|.
name|bibtexfields
operator|.
name|ClearFormatter
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
name|formatter
operator|.
name|bibtexfields
operator|.
name|RemoveBracesFormatter
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
name|EntryBasedParserFetcher
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
name|FetcherException
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
name|IdBasedParserFetcher
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
name|IdParserFetcher
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
name|ParseException
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
name|Parser
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
name|SearchBasedParserFetcher
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
name|util
operator|.
name|JsonReader
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
name|strings
operator|.
name|StringSimilarity
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
name|cleanup
operator|.
name|FieldFormatterCleanup
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
name|BiblatexEntryTypes
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
name|identifier
operator|.
name|DOI
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
name|util
operator|.
name|OptionalUtil
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|http
operator|.
name|client
operator|.
name|utils
operator|.
name|URIBuilder
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
name|JSONException
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

begin_comment
comment|/**  * A class for fetching DOIs from CrossRef  *  * See https://github.com/CrossRef/rest-api-doc  */
end_comment

begin_class
DECL|class|CrossRef
specifier|public
class|class
name|CrossRef
implements|implements
name|IdParserFetcher
argument_list|<
name|DOI
argument_list|>
implements|,
name|EntryBasedParserFetcher
implements|,
name|SearchBasedParserFetcher
implements|,
name|IdBasedParserFetcher
block|{
DECL|field|API_URL
specifier|private
specifier|static
specifier|final
name|String
name|API_URL
init|=
literal|"http://api.crossref.org/works"
decl_stmt|;
DECL|field|REMOVE_BRACES_FORMATTER
specifier|private
specifier|static
specifier|final
name|RemoveBracesFormatter
name|REMOVE_BRACES_FORMATTER
init|=
operator|new
name|RemoveBracesFormatter
argument_list|()
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
literal|"Crossref"
return|;
block|}
annotation|@
name|Override
DECL|method|getURLForEntry (BibEntry entry)
specifier|public
name|URL
name|getURLForEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
throws|throws
name|URISyntaxException
throws|,
name|MalformedURLException
throws|,
name|FetcherException
block|{
name|URIBuilder
name|uriBuilder
init|=
operator|new
name|URIBuilder
argument_list|(
name|API_URL
argument_list|)
decl_stmt|;
name|entry
operator|.
name|getLatexFreeField
argument_list|(
name|FieldName
operator|.
name|TITLE
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|title
lambda|->
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"query.title"
argument_list|,
name|title
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|.
name|getLatexFreeField
argument_list|(
name|FieldName
operator|.
name|AUTHOR
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|author
lambda|->
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"query.author"
argument_list|,
name|author
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|.
name|getLatexFreeField
argument_list|(
name|FieldName
operator|.
name|YEAR
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|year
lambda|->
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"filter"
argument_list|,
literal|"from-pub-date:"
operator|+
name|year
argument_list|)
argument_list|)
expr_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"rows"
argument_list|,
literal|"20"
argument_list|)
expr_stmt|;
comment|// = API default
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"offset"
argument_list|,
literal|"0"
argument_list|)
expr_stmt|;
comment|// start at beginning
return|return
name|uriBuilder
operator|.
name|build
argument_list|()
operator|.
name|toURL
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|getURLForQuery (String query)
specifier|public
name|URL
name|getURLForQuery
parameter_list|(
name|String
name|query
parameter_list|)
throws|throws
name|URISyntaxException
throws|,
name|MalformedURLException
throws|,
name|FetcherException
block|{
name|URIBuilder
name|uriBuilder
init|=
operator|new
name|URIBuilder
argument_list|(
name|API_URL
argument_list|)
decl_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"query"
argument_list|,
name|query
argument_list|)
expr_stmt|;
return|return
name|uriBuilder
operator|.
name|build
argument_list|()
operator|.
name|toURL
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|getURLForID (String identifier)
specifier|public
name|URL
name|getURLForID
parameter_list|(
name|String
name|identifier
parameter_list|)
throws|throws
name|URISyntaxException
throws|,
name|MalformedURLException
throws|,
name|FetcherException
block|{
name|URIBuilder
name|uriBuilder
init|=
operator|new
name|URIBuilder
argument_list|(
name|API_URL
operator|+
literal|"/"
operator|+
name|identifier
argument_list|)
decl_stmt|;
return|return
name|uriBuilder
operator|.
name|build
argument_list|()
operator|.
name|toURL
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|getParser ()
specifier|public
name|Parser
name|getParser
parameter_list|()
block|{
return|return
name|inputStream
lambda|->
block|{
name|JSONObject
name|response
init|=
name|JsonReader
operator|.
name|toJsonObject
argument_list|(
name|inputStream
argument_list|)
operator|.
name|getJSONObject
argument_list|(
literal|"message"
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
if|if
condition|(
name|response
operator|.
name|has
argument_list|(
literal|"items"
argument_list|)
condition|)
block|{
comment|// Response contains a list
name|JSONArray
name|items
init|=
name|response
operator|.
name|getJSONArray
argument_list|(
literal|"items"
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
name|items
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|JSONObject
name|item
init|=
name|items
operator|.
name|getJSONObject
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|BibEntry
name|entry
init|=
name|jsonItemToBibEntry
argument_list|(
name|item
argument_list|)
decl_stmt|;
name|entries
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
comment|// Singleton response
name|BibEntry
name|entry
init|=
name|jsonItemToBibEntry
argument_list|(
name|response
argument_list|)
decl_stmt|;
name|entries
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
return|return
name|entries
return|;
block|}
return|;
block|}
annotation|@
name|Override
DECL|method|doPostCleanup (BibEntry entry)
specifier|public
name|void
name|doPostCleanup
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
comment|// Sometimes the fetched entry returns the title also in the subtitle field; in this case only keep the title field
if|if
condition|(
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|TITLE
argument_list|)
operator|.
name|equals
argument_list|(
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|SUBTITLE
argument_list|)
argument_list|)
condition|)
block|{
operator|new
name|FieldFormatterCleanup
argument_list|(
name|FieldName
operator|.
name|SUBTITLE
argument_list|,
operator|new
name|ClearFormatter
argument_list|()
argument_list|)
operator|.
name|cleanup
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|jsonItemToBibEntry (JSONObject item)
specifier|private
name|BibEntry
name|jsonItemToBibEntry
parameter_list|(
name|JSONObject
name|item
parameter_list|)
throws|throws
name|ParseException
block|{
try|try
block|{
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
name|convertType
argument_list|(
name|item
operator|.
name|getString
argument_list|(
literal|"type"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|TITLE
argument_list|,
name|item
operator|.
name|getJSONArray
argument_list|(
literal|"title"
argument_list|)
operator|.
name|optString
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|SUBTITLE
argument_list|,
name|Optional
operator|.
name|ofNullable
argument_list|(
name|item
operator|.
name|optJSONArray
argument_list|(
literal|"subtitle"
argument_list|)
argument_list|)
operator|.
name|map
argument_list|(
name|array
lambda|->
name|array
operator|.
name|optString
argument_list|(
literal|0
argument_list|)
argument_list|)
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|AUTHOR
argument_list|,
name|toAuthors
argument_list|(
name|item
operator|.
name|optJSONArray
argument_list|(
literal|"author"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|YEAR
argument_list|,
name|Optional
operator|.
name|ofNullable
argument_list|(
name|item
operator|.
name|optJSONObject
argument_list|(
literal|"published-print"
argument_list|)
argument_list|)
operator|.
name|map
argument_list|(
name|array
lambda|->
name|array
operator|.
name|optJSONArray
argument_list|(
literal|"date-parts"
argument_list|)
argument_list|)
operator|.
name|map
argument_list|(
name|array
lambda|->
name|array
operator|.
name|optJSONArray
argument_list|(
literal|0
argument_list|)
argument_list|)
operator|.
name|map
argument_list|(
name|array
lambda|->
name|array
operator|.
name|optInt
argument_list|(
literal|0
argument_list|)
argument_list|)
operator|.
name|map
argument_list|(
name|year
lambda|->
name|Integer
operator|.
name|toString
argument_list|(
name|year
argument_list|)
argument_list|)
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|DOI
argument_list|,
name|item
operator|.
name|getString
argument_list|(
literal|"DOI"
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|PAGES
argument_list|,
name|item
operator|.
name|optString
argument_list|(
literal|"page"
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|VOLUME
argument_list|,
name|item
operator|.
name|optString
argument_list|(
literal|"volume"
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|ISSN
argument_list|,
name|Optional
operator|.
name|ofNullable
argument_list|(
name|item
operator|.
name|optJSONArray
argument_list|(
literal|"ISSN"
argument_list|)
argument_list|)
operator|.
name|map
argument_list|(
name|array
lambda|->
name|array
operator|.
name|getString
argument_list|(
literal|0
argument_list|)
argument_list|)
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|entry
return|;
block|}
catch|catch
parameter_list|(
name|JSONException
name|exception
parameter_list|)
block|{
throw|throw
operator|new
name|ParseException
argument_list|(
literal|"CrossRef API JSON format has changed"
argument_list|,
name|exception
argument_list|)
throw|;
block|}
block|}
DECL|method|toAuthors (JSONArray authors)
specifier|private
name|String
name|toAuthors
parameter_list|(
name|JSONArray
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
return|return
literal|""
return|;
block|}
comment|// input: list of {"given":"A.","family":"Riel","affiliation":[]}
name|AuthorList
name|authorsParsed
init|=
operator|new
name|AuthorList
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
name|JSONObject
name|author
init|=
name|authors
operator|.
name|getJSONObject
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|authorsParsed
operator|.
name|addAuthor
argument_list|(
name|author
operator|.
name|optString
argument_list|(
literal|"given"
argument_list|,
literal|""
argument_list|)
argument_list|,
literal|""
argument_list|,
literal|""
argument_list|,
name|author
operator|.
name|optString
argument_list|(
literal|"family"
argument_list|,
literal|""
argument_list|)
argument_list|,
literal|""
argument_list|)
expr_stmt|;
block|}
return|return
name|authorsParsed
operator|.
name|getAsFirstLastNamesWithAnd
argument_list|()
return|;
block|}
DECL|method|convertType (String type)
specifier|private
name|EntryType
name|convertType
parameter_list|(
name|String
name|type
parameter_list|)
block|{
switch|switch
condition|(
name|type
condition|)
block|{
case|case
literal|"journal-article"
case|:
return|return
name|BiblatexEntryTypes
operator|.
name|ARTICLE
return|;
default|default:
return|return
name|BiblatexEntryTypes
operator|.
name|MISC
return|;
block|}
block|}
annotation|@
name|Override
DECL|method|extractIdentifier (BibEntry inputEntry, List<BibEntry> fetchedEntries)
specifier|public
name|Optional
argument_list|<
name|DOI
argument_list|>
name|extractIdentifier
parameter_list|(
name|BibEntry
name|inputEntry
parameter_list|,
name|List
argument_list|<
name|BibEntry
argument_list|>
name|fetchedEntries
parameter_list|)
throws|throws
name|FetcherException
block|{
specifier|final
name|String
name|entryTitle
init|=
name|REMOVE_BRACES_FORMATTER
operator|.
name|format
argument_list|(
name|inputEntry
operator|.
name|getLatexFreeField
argument_list|(
name|FieldName
operator|.
name|TITLE
argument_list|)
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
argument_list|)
decl_stmt|;
specifier|final
name|StringSimilarity
name|stringSimilarity
init|=
operator|new
name|StringSimilarity
argument_list|()
decl_stmt|;
for|for
control|(
name|BibEntry
name|fetchedEntry
range|:
name|fetchedEntries
control|)
block|{
comment|// currently only title-based comparison
comment|// title
name|Optional
argument_list|<
name|String
argument_list|>
name|dataTitle
init|=
name|fetchedEntry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|TITLE
argument_list|)
decl_stmt|;
if|if
condition|(
name|OptionalUtil
operator|.
name|isPresentAnd
argument_list|(
name|dataTitle
argument_list|,
name|title
lambda|->
name|stringSimilarity
operator|.
name|isSimilar
argument_list|(
name|entryTitle
argument_list|,
name|title
argument_list|)
argument_list|)
condition|)
block|{
return|return
name|fetchedEntry
operator|.
name|getDOI
argument_list|()
return|;
block|}
comment|// subtitle
comment|// additional check, as sometimes subtitle is needed but sometimes only duplicates the title
name|Optional
argument_list|<
name|String
argument_list|>
name|dataSubtitle
init|=
name|fetchedEntry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|SUBTITLE
argument_list|)
decl_stmt|;
name|Optional
argument_list|<
name|String
argument_list|>
name|dataWithSubTitle
init|=
name|OptionalUtil
operator|.
name|combine
argument_list|(
name|dataTitle
argument_list|,
name|dataSubtitle
argument_list|,
parameter_list|(
name|title
parameter_list|,
name|subtitle
parameter_list|)
lambda|->
name|title
operator|+
literal|" "
operator|+
name|subtitle
argument_list|)
decl_stmt|;
if|if
condition|(
name|OptionalUtil
operator|.
name|isPresentAnd
argument_list|(
name|dataWithSubTitle
argument_list|,
name|titleWithSubtitle
lambda|->
name|stringSimilarity
operator|.
name|isSimilar
argument_list|(
name|entryTitle
argument_list|,
name|titleWithSubtitle
argument_list|)
argument_list|)
condition|)
block|{
return|return
name|fetchedEntry
operator|.
name|getDOI
argument_list|()
return|;
block|}
block|}
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|getIdentifierName ()
specifier|public
name|String
name|getIdentifierName
parameter_list|()
block|{
return|return
literal|"DOI"
return|;
block|}
block|}
end_class

end_unit

