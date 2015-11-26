begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2015 Oscar Gustafsson.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.importer.fetcher
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
operator|.
name|fetcher
package|;
end_package

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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
operator|.
name|ImportInspector
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
name|importer
operator|.
name|OutputPrinter
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
name|importer
operator|.
name|fileformat
operator|.
name|JSONEntryParser
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
name|l10n
operator|.
name|Localization
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
name|BibtexEntry
import|;
end_import

begin_class
DECL|class|DOAJFetcher
specifier|public
class|class
name|DOAJFetcher
implements|implements
name|EntryFetcher
block|{
DECL|field|searchURL
specifier|private
specifier|final
name|String
name|searchURL
init|=
literal|"https://doaj.org/api/v1/search/articles/"
decl_stmt|;
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
name|DOAJFetcher
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|maxPerPage
specifier|private
specifier|final
name|int
name|maxPerPage
init|=
literal|100
decl_stmt|;
DECL|field|shouldContinue
specifier|private
name|boolean
name|shouldContinue
decl_stmt|;
DECL|field|jsonConverter
specifier|private
specifier|final
name|JSONEntryParser
name|jsonConverter
init|=
operator|new
name|JSONEntryParser
argument_list|()
decl_stmt|;
DECL|method|DOAJFetcher ()
specifier|public
name|DOAJFetcher
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
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
name|jsonResponse
operator|=
name|Unirest
operator|.
name|get
argument_list|(
name|searchURL
operator|+
name|query
operator|+
literal|"?pageSize=1"
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
name|hits
init|=
name|jo
operator|.
name|getInt
argument_list|(
literal|"total"
argument_list|)
decl_stmt|;
name|int
name|numberToFetch
init|=
literal|0
decl_stmt|;
if|if
condition|(
name|hits
operator|>
literal|0
condition|)
block|{
if|if
condition|(
name|hits
operator|>
name|maxPerPage
condition|)
block|{
while|while
condition|(
literal|true
condition|)
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
literal|"References found"
argument_list|)
operator|+
literal|": "
operator|+
name|hits
operator|+
literal|"  "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Number of references to fetch?"
argument_list|)
argument_list|,
name|Integer
operator|.
name|toString
argument_list|(
name|hits
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
literal|"Search canceled"
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
break|break;
block|}
catch|catch
parameter_list|(
name|RuntimeException
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
block|}
else|else
block|{
name|numberToFetch
operator|=
name|hits
expr_stmt|;
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
name|page
init|=
literal|1
init|;
operator|(
operator|(
name|page
operator|-
literal|1
operator|)
operator|*
name|maxPerPage
operator|)
operator|<=
name|numberToFetch
condition|;
name|page
operator|++
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
name|maxPerPage
argument_list|,
name|numberToFetch
operator|-
operator|(
operator|(
name|page
operator|-
literal|1
operator|)
operator|*
name|maxPerPage
operator|)
argument_list|)
decl_stmt|;
name|jsonResponse
operator|=
name|Unirest
operator|.
name|get
argument_list|(
name|searchURL
operator|+
name|query
operator|+
literal|"?page="
operator|+
name|page
operator|+
literal|"&pageSize="
operator|+
name|noToFetch
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
literal|"results"
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
literal|"results"
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
name|bibJsonEntry
init|=
name|results
operator|.
name|getJSONObject
argument_list|(
name|i
argument_list|)
operator|.
name|getJSONObject
argument_list|(
literal|"bibjson"
argument_list|)
decl_stmt|;
name|BibtexEntry
name|entry
init|=
name|jsonConverter
operator|.
name|BibJSONtoBibtex
argument_list|(
name|bibJsonEntry
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
name|query
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Search %0"
argument_list|,
literal|"DOAJ"
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
name|UnirestException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Problem searching DOAJ"
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|status
operator|.
name|setStatus
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Search canceled"
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
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
literal|"DOAJ"
return|;
block|}
annotation|@
name|Override
DECL|method|getHelpPage ()
specifier|public
name|String
name|getHelpPage
parameter_list|()
block|{
return|return
literal|"DOAJHelp.html"
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

