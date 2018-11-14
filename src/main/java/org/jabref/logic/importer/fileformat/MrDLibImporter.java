begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/**  *  */
end_comment

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
name|Iterator
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
name|l10n
operator|.
name|Localization
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
name|database
operator|.
name|BibDatabase
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

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_comment
comment|/**  * Handles importing of recommended articles to be displayed in the Related Articles tab.  */
end_comment

begin_class
DECL|class|MrDLibImporter
specifier|public
class|class
name|MrDLibImporter
extends|extends
name|Importer
block|{
DECL|field|DEFAULT_MRDLIB_ERROR_MESSAGE
specifier|private
specifier|static
specifier|final
name|String
name|DEFAULT_MRDLIB_ERROR_MESSAGE
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error while fetching from Mr.DLib."
argument_list|)
decl_stmt|;
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Logger
name|LOGGER
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|MrDLibImporter
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|parserResult
specifier|public
name|ParserResult
name|parserResult
decl_stmt|;
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unused"
argument_list|)
annotation|@
name|Override
DECL|method|isRecognizedFormat (BufferedReader input)
specifier|public
name|boolean
name|isRecognizedFormat
parameter_list|(
name|BufferedReader
name|input
parameter_list|)
throws|throws
name|IOException
block|{
name|String
name|recommendationsAsString
init|=
name|convertToString
argument_list|(
name|input
argument_list|)
decl_stmt|;
try|try
block|{
name|JSONObject
name|jsonObject
init|=
operator|new
name|JSONObject
argument_list|(
name|recommendationsAsString
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|jsonObject
operator|.
name|has
argument_list|(
literal|"recommendations"
argument_list|)
condition|)
block|{
return|return
literal|false
return|;
block|}
block|}
catch|catch
parameter_list|(
name|JSONException
name|ex
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
return|return
literal|true
return|;
block|}
annotation|@
name|Override
DECL|method|importDatabase (BufferedReader input)
specifier|public
name|ParserResult
name|importDatabase
parameter_list|(
name|BufferedReader
name|input
parameter_list|)
throws|throws
name|IOException
block|{
name|parse
argument_list|(
name|input
argument_list|)
expr_stmt|;
return|return
name|parserResult
return|;
block|}
annotation|@
name|Override
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"MrDLibImporter"
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
name|JSON
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
literal|"Takes valid JSON documents from the Mr. DLib API and parses them into a BibEntry"
return|;
block|}
comment|/**      * Convert Buffered Reader response to string for JSON parsing.      * @param input Takes a BufferedReader with a reference to the JSON document delivered by mdl server.      * @return Returns an String containing the JSON document.      * @throws IOException      */
DECL|method|convertToString (BufferedReader input)
specifier|private
name|String
name|convertToString
parameter_list|(
name|BufferedReader
name|input
parameter_list|)
throws|throws
name|IOException
block|{
name|String
name|line
decl_stmt|;
name|StringBuilder
name|stringBuilder
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
try|try
block|{
while|while
condition|(
operator|(
name|line
operator|=
name|input
operator|.
name|readLine
argument_list|()
operator|)
operator|!=
literal|null
condition|)
block|{
name|stringBuilder
operator|.
name|append
argument_list|(
name|line
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|stringBuilder
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Small pair-class to ensure the right order of the recommendations.      */
DECL|class|RankedBibEntry
specifier|private
class|class
name|RankedBibEntry
block|{
DECL|field|entry
specifier|public
name|BibEntry
name|entry
decl_stmt|;
DECL|field|rank
specifier|public
name|Integer
name|rank
decl_stmt|;
DECL|method|RankedBibEntry (BibEntry entry, Integer rank)
specifier|public
name|RankedBibEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|Integer
name|rank
parameter_list|)
block|{
name|this
operator|.
name|rank
operator|=
name|rank
expr_stmt|;
name|this
operator|.
name|entry
operator|=
name|entry
expr_stmt|;
block|}
block|}
comment|/**      * Parses the input from the server to a ParserResult      * @param input A BufferedReader with a reference to a string with the server's response      * @throws IOException      */
DECL|method|parse (BufferedReader input)
specifier|private
name|void
name|parse
parameter_list|(
name|BufferedReader
name|input
parameter_list|)
throws|throws
name|IOException
block|{
comment|// The Bibdatabase that gets returned in the ParserResult.
name|BibDatabase
name|bibDatabase
init|=
operator|new
name|BibDatabase
argument_list|()
decl_stmt|;
comment|// The document to parse
name|String
name|recommendations
init|=
name|convertToString
argument_list|(
name|input
argument_list|)
decl_stmt|;
comment|// The sorted BibEntries gets stored here later
name|List
argument_list|<
name|RankedBibEntry
argument_list|>
name|rankedBibEntries
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
comment|// Get recommendations from response and populate bib entries
name|JSONObject
name|recommendationsJson
init|=
operator|new
name|JSONObject
argument_list|(
name|recommendations
argument_list|)
operator|.
name|getJSONObject
argument_list|(
literal|"recommendations"
argument_list|)
decl_stmt|;
name|Iterator
argument_list|<
name|String
argument_list|>
name|keys
init|=
name|recommendationsJson
operator|.
name|keys
argument_list|()
decl_stmt|;
while|while
condition|(
name|keys
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|String
name|key
init|=
name|keys
operator|.
name|next
argument_list|()
decl_stmt|;
name|JSONObject
name|value
init|=
name|recommendationsJson
operator|.
name|getJSONObject
argument_list|(
name|key
argument_list|)
decl_stmt|;
name|rankedBibEntries
operator|.
name|add
argument_list|(
name|populateBibEntry
argument_list|(
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Sort bib entries according to rank
name|rankedBibEntries
operator|.
name|sort
argument_list|(
parameter_list|(
name|RankedBibEntry
name|rankedBibEntry1
parameter_list|,
name|RankedBibEntry
name|rankedBibEntry2
parameter_list|)
lambda|->
name|rankedBibEntry1
operator|.
name|rank
operator|.
name|compareTo
argument_list|(
name|rankedBibEntry2
operator|.
name|rank
argument_list|)
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|bibEntries
init|=
name|rankedBibEntries
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|e
lambda|->
name|e
operator|.
name|entry
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|BibEntry
name|bibentry
range|:
name|bibEntries
control|)
block|{
name|bibDatabase
operator|.
name|insertEntry
argument_list|(
name|bibentry
argument_list|)
expr_stmt|;
block|}
name|parserResult
operator|=
operator|new
name|ParserResult
argument_list|(
name|bibDatabase
argument_list|)
expr_stmt|;
name|JSONObject
name|label
init|=
operator|new
name|JSONObject
argument_list|(
name|recommendations
argument_list|)
operator|.
name|getJSONObject
argument_list|(
literal|"label"
argument_list|)
decl_stmt|;
name|parserResult
operator|.
name|setTitle
argument_list|(
name|label
operator|.
name|getString
argument_list|(
literal|"label-text"
argument_list|)
argument_list|)
expr_stmt|;
name|parserResult
operator|.
name|setDescription
argument_list|(
name|label
operator|.
name|getString
argument_list|(
literal|"label-description"
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Parses the JSON recommendations into bib entries      * @param recommendation JSON object of a single recommendation returned by Mr. DLib      * @return A ranked bib entry created from the recommendation input      */
DECL|method|populateBibEntry (JSONObject recommendation)
specifier|private
name|RankedBibEntry
name|populateBibEntry
parameter_list|(
name|JSONObject
name|recommendation
parameter_list|)
block|{
name|BibEntry
name|current
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
comment|// parse each of the relevant fields into variables
name|String
name|authors
init|=
name|isRecommendationFieldPresent
argument_list|(
name|recommendation
argument_list|,
literal|"authors"
argument_list|)
condition|?
name|getAuthorsString
argument_list|(
name|recommendation
argument_list|)
else|:
literal|""
decl_stmt|;
name|String
name|title
init|=
name|isRecommendationFieldPresent
argument_list|(
name|recommendation
argument_list|,
literal|"title"
argument_list|)
condition|?
name|recommendation
operator|.
name|getString
argument_list|(
literal|"title"
argument_list|)
else|:
literal|""
decl_stmt|;
name|String
name|year
init|=
name|isRecommendationFieldPresent
argument_list|(
name|recommendation
argument_list|,
literal|"published_year"
argument_list|)
condition|?
name|Integer
operator|.
name|toString
argument_list|(
name|recommendation
operator|.
name|getInt
argument_list|(
literal|"published_year"
argument_list|)
argument_list|)
else|:
literal|""
decl_stmt|;
name|String
name|journal
init|=
name|isRecommendationFieldPresent
argument_list|(
name|recommendation
argument_list|,
literal|"published_in"
argument_list|)
condition|?
name|recommendation
operator|.
name|getString
argument_list|(
literal|"published_in"
argument_list|)
else|:
literal|""
decl_stmt|;
name|String
name|url
init|=
name|isRecommendationFieldPresent
argument_list|(
name|recommendation
argument_list|,
literal|"url"
argument_list|)
condition|?
name|recommendation
operator|.
name|getString
argument_list|(
literal|"url"
argument_list|)
else|:
literal|""
decl_stmt|;
name|Integer
name|rank
init|=
name|isRecommendationFieldPresent
argument_list|(
name|recommendation
argument_list|,
literal|"recommendation_id"
argument_list|)
condition|?
name|recommendation
operator|.
name|getInt
argument_list|(
literal|"recommendation_id"
argument_list|)
else|:
literal|100
decl_stmt|;
comment|// Populate bib entry with relevant data
name|current
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|AUTHOR
argument_list|,
name|authors
argument_list|)
expr_stmt|;
name|current
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|TITLE
argument_list|,
name|title
argument_list|)
expr_stmt|;
name|current
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|YEAR
argument_list|,
name|year
argument_list|)
expr_stmt|;
name|current
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|JOURNAL
argument_list|,
name|journal
argument_list|)
expr_stmt|;
name|current
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|URL
argument_list|,
name|url
argument_list|)
expr_stmt|;
return|return
operator|new
name|RankedBibEntry
argument_list|(
name|current
argument_list|,
name|rank
argument_list|)
return|;
block|}
DECL|method|isRecommendationFieldPresent (JSONObject recommendation, String field)
specifier|private
name|Boolean
name|isRecommendationFieldPresent
parameter_list|(
name|JSONObject
name|recommendation
parameter_list|,
name|String
name|field
parameter_list|)
block|{
return|return
name|recommendation
operator|.
name|has
argument_list|(
name|field
argument_list|)
operator|&&
operator|!
name|recommendation
operator|.
name|isNull
argument_list|(
name|field
argument_list|)
return|;
block|}
comment|/**      * Creates an authors string from a JSON recommendation      * @param recommendation JSON Object recommendation from Mr. DLib      * @return A string of all authors, separated by commas and finished with a full stop.      */
DECL|method|getAuthorsString (JSONObject recommendation)
specifier|private
name|String
name|getAuthorsString
parameter_list|(
name|JSONObject
name|recommendation
parameter_list|)
block|{
name|String
name|authorsString
init|=
literal|""
decl_stmt|;
name|JSONArray
name|array
init|=
name|recommendation
operator|.
name|getJSONArray
argument_list|(
literal|"authors"
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
name|array
operator|.
name|length
argument_list|()
condition|;
operator|++
name|i
control|)
block|{
name|authorsString
operator|+=
name|array
operator|.
name|getString
argument_list|(
name|i
argument_list|)
operator|+
literal|"; "
expr_stmt|;
block|}
name|int
name|stringLength
init|=
name|authorsString
operator|.
name|length
argument_list|()
decl_stmt|;
if|if
condition|(
name|stringLength
operator|>
literal|2
condition|)
block|{
name|authorsString
operator|=
name|authorsString
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|stringLength
operator|-
literal|2
argument_list|)
operator|+
literal|"."
expr_stmt|;
block|}
return|return
name|authorsString
return|;
block|}
DECL|method|getParserResult ()
specifier|public
name|ParserResult
name|getParserResult
parameter_list|()
block|{
return|return
name|parserResult
return|;
block|}
comment|/**      * Gets the error message to be returned if there has been an error in returning recommendations.      * Returns default error message if there is no message from Mr. DLib.      * @param response The response from the MDL server as a string.      * @return String error message to be shown to the user.      */
DECL|method|getResponseErrorMessage (String response)
specifier|public
name|String
name|getResponseErrorMessage
parameter_list|(
name|String
name|response
parameter_list|)
block|{
try|try
block|{
name|JSONObject
name|jsonObject
init|=
operator|new
name|JSONObject
argument_list|(
name|response
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|jsonObject
operator|.
name|has
argument_list|(
literal|"message"
argument_list|)
condition|)
block|{
return|return
name|jsonObject
operator|.
name|getString
argument_list|(
literal|"message"
argument_list|)
return|;
block|}
block|}
catch|catch
parameter_list|(
name|JSONException
name|ex
parameter_list|)
block|{
return|return
name|DEFAULT_MRDLIB_ERROR_MESSAGE
return|;
block|}
return|return
name|DEFAULT_MRDLIB_ERROR_MESSAGE
return|;
block|}
block|}
end_class

end_unit

