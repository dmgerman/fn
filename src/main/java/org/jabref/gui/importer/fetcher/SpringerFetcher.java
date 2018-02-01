begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.importer.fetcher
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
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
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URLEncoder
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JOptionPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JPanel
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|importer
operator|.
name|ImportInspectionDialog
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
name|help
operator|.
name|HelpFile
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
name|ImportInspector
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
name|OutputPrinter
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
name|JSONEntryParser
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
name|model
operator|.
name|entry
operator|.
name|BibEntry
import|;
end_import

begin_import
import|import
name|com
operator|.
name|mashape
operator|.
name|unirest
operator|.
name|http
operator|.
name|HttpResponse
import|;
end_import

begin_import
import|import
name|com
operator|.
name|mashape
operator|.
name|unirest
operator|.
name|http
operator|.
name|JsonNode
import|;
end_import

begin_import
import|import
name|com
operator|.
name|mashape
operator|.
name|unirest
operator|.
name|http
operator|.
name|Unirest
import|;
end_import

begin_import
import|import
name|com
operator|.
name|mashape
operator|.
name|unirest
operator|.
name|http
operator|.
name|exceptions
operator|.
name|UnirestException
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

begin_class
DECL|class|SpringerFetcher
specifier|public
class|class
name|SpringerFetcher
implements|implements
name|EntryFetcher
block|{
DECL|field|API_URL
specifier|private
specifier|static
specifier|final
name|String
name|API_URL
init|=
literal|"http://api.springer.com/metadata/json?q="
decl_stmt|;
DECL|field|API_KEY
specifier|private
specifier|static
specifier|final
name|String
name|API_KEY
init|=
literal|"b0c7151179b3d9c1119cf325bca8460d"
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
name|SpringerFetcher
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|MAX_PER_PAGE
specifier|private
specifier|static
specifier|final
name|int
name|MAX_PER_PAGE
init|=
literal|100
decl_stmt|;
DECL|field|shouldContinue
specifier|private
name|boolean
name|shouldContinue
decl_stmt|;
annotation|@
name|Override
DECL|method|stopFetching ()
specifier|public
name|void
name|stopFetching
parameter_list|()
block|{
name|shouldContinue
operator|=
literal|false
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|processQuery (String query, ImportInspector inspector, OutputPrinter status)
specifier|public
name|boolean
name|processQuery
parameter_list|(
name|String
name|query
parameter_list|,
name|ImportInspector
name|inspector
parameter_list|,
name|OutputPrinter
name|status
parameter_list|)
block|{
name|shouldContinue
operator|=
literal|true
expr_stmt|;
try|try
block|{
name|status
operator|.
name|setStatus
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Searching..."
argument_list|)
argument_list|)
expr_stmt|;
name|HttpResponse
argument_list|<
name|JsonNode
argument_list|>
name|jsonResponse
decl_stmt|;
name|String
name|encodedQuery
init|=
name|URLEncoder
operator|.
name|encode
argument_list|(
name|query
argument_list|,
literal|"UTF-8"
argument_list|)
decl_stmt|;
name|jsonResponse
operator|=
name|Unirest
operator|.
name|get
argument_list|(
name|API_URL
operator|+
name|encodedQuery
operator|+
literal|"&api_key="
operator|+
name|API_KEY
operator|+
literal|"&p=1"
argument_list|)
operator|.
name|header
argument_list|(
literal|"accept"
argument_list|,
literal|"application/json"
argument_list|)
operator|.
name|asJson
argument_list|()
expr_stmt|;
name|JSONObject
name|jo
init|=
name|jsonResponse
operator|.
name|getBody
argument_list|()
operator|.
name|getObject
argument_list|()
decl_stmt|;
name|int
name|numberToFetch
init|=
name|jo
operator|.
name|getJSONArray
argument_list|(
literal|"result"
argument_list|)
operator|.
name|getJSONObject
argument_list|(
literal|0
argument_list|)
operator|.
name|getInt
argument_list|(
literal|"total"
argument_list|)
decl_stmt|;
if|if
condition|(
name|numberToFetch
operator|>
literal|0
condition|)
block|{
if|if
condition|(
name|numberToFetch
operator|>
name|MAX_PER_PAGE
condition|)
block|{
name|boolean
name|numberEntered
init|=
literal|false
decl_stmt|;
do|do
block|{
name|String
name|strCount
init|=
name|JOptionPane
operator|.
name|showInputDialog
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 references found. Number of references to fetch?"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|numberToFetch
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|strCount
operator|==
literal|null
condition|)
block|{
name|status
operator|.
name|setStatus
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 import canceled"
argument_list|,
name|getTitle
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
try|try
block|{
name|numberToFetch
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|strCount
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
name|numberEntered
operator|=
literal|true
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|ex
parameter_list|)
block|{
name|status
operator|.
name|showMessage
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Please enter a valid number"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
do|while
condition|(
operator|!
name|numberEntered
condition|)
do|;
block|}
name|int
name|fetched
init|=
literal|0
decl_stmt|;
comment|// Keep track of number of items fetched for the progress bar
for|for
control|(
name|int
name|startItem
init|=
literal|1
init|;
name|startItem
operator|<=
name|numberToFetch
condition|;
name|startItem
operator|+=
name|MAX_PER_PAGE
control|)
block|{
if|if
condition|(
operator|!
name|shouldContinue
condition|)
block|{
break|break;
block|}
name|int
name|noToFetch
init|=
name|Math
operator|.
name|min
argument_list|(
name|MAX_PER_PAGE
argument_list|,
operator|(
name|numberToFetch
operator|-
name|startItem
operator|)
operator|+
literal|1
argument_list|)
decl_stmt|;
name|jsonResponse
operator|=
name|Unirest
operator|.
name|get
argument_list|(
name|API_URL
operator|+
name|encodedQuery
operator|+
literal|"&api_key="
operator|+
name|API_KEY
operator|+
literal|"&p="
operator|+
name|noToFetch
operator|+
literal|"&s="
operator|+
name|startItem
argument_list|)
operator|.
name|header
argument_list|(
literal|"accept"
argument_list|,
literal|"application/json"
argument_list|)
operator|.
name|asJson
argument_list|()
expr_stmt|;
name|jo
operator|=
name|jsonResponse
operator|.
name|getBody
argument_list|()
operator|.
name|getObject
argument_list|()
expr_stmt|;
if|if
condition|(
name|jo
operator|.
name|has
argument_list|(
literal|"records"
argument_list|)
condition|)
block|{
name|JSONArray
name|results
init|=
name|jo
operator|.
name|getJSONArray
argument_list|(
literal|"records"
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
name|results
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|JSONObject
name|springerJsonEntry
init|=
name|results
operator|.
name|getJSONObject
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|BibEntry
name|entry
init|=
name|JSONEntryParser
operator|.
name|parseSpringerJSONtoBibtex
argument_list|(
name|springerJsonEntry
argument_list|)
decl_stmt|;
name|inspector
operator|.
name|addEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|fetched
operator|++
expr_stmt|;
name|inspector
operator|.
name|setProgress
argument_list|(
name|fetched
argument_list|,
name|numberToFetch
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
literal|true
return|;
block|}
else|else
block|{
name|status
operator|.
name|showMessage
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"No entries found for the search string '%0'"
argument_list|,
name|encodedQuery
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Search %0"
argument_list|,
name|getTitle
argument_list|()
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|INFORMATION_MESSAGE
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
decl||
name|UnirestException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Error while fetching from "
operator|+
name|getTitle
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
operator|(
operator|(
name|ImportInspectionDialog
operator|)
name|inspector
operator|)
operator|.
name|showErrorMessage
argument_list|(
name|this
operator|.
name|getTitle
argument_list|()
argument_list|,
name|e
operator|.
name|getLocalizedMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
literal|false
return|;
block|}
annotation|@
name|Override
DECL|method|getTitle ()
specifier|public
name|String
name|getTitle
parameter_list|()
block|{
return|return
literal|"Springer"
return|;
block|}
annotation|@
name|Override
DECL|method|getHelpPage ()
specifier|public
name|HelpFile
name|getHelpPage
parameter_list|()
block|{
return|return
name|HelpFile
operator|.
name|FETCHER_SPRINGER
return|;
block|}
annotation|@
name|Override
DECL|method|getOptionsPanel ()
specifier|public
name|JPanel
name|getOptionsPanel
parameter_list|()
block|{
comment|// No additional options available
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

