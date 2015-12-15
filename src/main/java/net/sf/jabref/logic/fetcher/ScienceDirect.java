begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.fetcher
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|fetcher
package|;
end_package

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
name|logic
operator|.
name|util
operator|.
name|DOI
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
name|jsoup
operator|.
name|Jsoup
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jsoup
operator|.
name|nodes
operator|.
name|Document
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jsoup
operator|.
name|nodes
operator|.
name|Element
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
name|Objects
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

begin_comment
comment|/**  * FullTextFinder implementation that attempts to find a PDF URL at ScienceDirect.  *  * @see http://dev.elsevier.com/  */
end_comment

begin_class
DECL|class|ScienceDirect
specifier|public
class|class
name|ScienceDirect
implements|implements
name|FullTextFinder
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
name|ScienceDirect
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|API_URL
specifier|private
specifier|static
specifier|final
name|String
name|API_URL
init|=
literal|"http://api.elsevier.com/content/article/doi/"
decl_stmt|;
DECL|field|API_KEY
specifier|private
specifier|static
specifier|final
name|String
name|API_KEY
init|=
literal|"fb82f2e692b3c72dafe5f4f1fa0ac00b"
decl_stmt|;
annotation|@
name|Override
DECL|method|findFullText (BibEntry entry)
specifier|public
name|Optional
argument_list|<
name|URL
argument_list|>
name|findFullText
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
throws|throws
name|IOException
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|Optional
argument_list|<
name|URL
argument_list|>
name|pdfLink
init|=
name|Optional
operator|.
name|empty
argument_list|()
decl_stmt|;
comment|// Try unique DOI first
name|Optional
argument_list|<
name|DOI
argument_list|>
name|doi
init|=
name|DOI
operator|.
name|build
argument_list|(
name|entry
operator|.
name|getField
argument_list|(
literal|"doi"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|doi
operator|.
name|isPresent
argument_list|()
condition|)
block|{
comment|// Available in catalog?
try|try
block|{
name|String
name|sciLink
init|=
name|getUrlByDoi
argument_list|(
name|doi
operator|.
name|get
argument_list|()
operator|.
name|getDOI
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|sciLink
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// Retrieve PDF link
name|Document
name|html
init|=
name|Jsoup
operator|.
name|connect
argument_list|(
name|sciLink
argument_list|)
operator|.
name|ignoreHttpErrors
argument_list|(
literal|true
argument_list|)
operator|.
name|get
argument_list|()
decl_stmt|;
name|Element
name|link
init|=
name|html
operator|.
name|getElementById
argument_list|(
literal|"pdfLink"
argument_list|)
decl_stmt|;
if|if
condition|(
name|link
operator|!=
literal|null
condition|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Fulltext PDF found @ ScienceDirect."
argument_list|)
expr_stmt|;
name|pdfLink
operator|=
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|URL
argument_list|(
name|link
operator|.
name|attr
argument_list|(
literal|"pdfurl"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
literal|"ScienceDirect API request failed: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
comment|// TODO: title search
comment|// We can also get abstract automatically!
return|return
name|pdfLink
return|;
block|}
DECL|method|getUrlByDoi (String doi)
specifier|private
name|String
name|getUrlByDoi
parameter_list|(
name|String
name|doi
parameter_list|)
throws|throws
name|UnirestException
block|{
name|String
name|sciLink
init|=
literal|""
decl_stmt|;
try|try
block|{
name|String
name|request
init|=
name|API_URL
operator|+
name|doi
decl_stmt|;
name|HttpResponse
argument_list|<
name|JsonNode
argument_list|>
name|jsonResponse
init|=
name|Unirest
operator|.
name|get
argument_list|(
name|request
argument_list|)
operator|.
name|header
argument_list|(
literal|"X-ELS-APIKey"
argument_list|,
name|API_KEY
argument_list|)
operator|.
name|queryString
argument_list|(
literal|"httpAccept"
argument_list|,
literal|"application/json"
argument_list|)
operator|.
name|asJson
argument_list|()
decl_stmt|;
name|JSONObject
name|json
init|=
name|jsonResponse
operator|.
name|getBody
argument_list|()
operator|.
name|getObject
argument_list|()
decl_stmt|;
name|JSONArray
name|links
init|=
name|json
operator|.
name|getJSONObject
argument_list|(
literal|"full-text-retrieval-response"
argument_list|)
operator|.
name|getJSONObject
argument_list|(
literal|"coredata"
argument_list|)
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
name|JSONObject
name|link
init|=
name|links
operator|.
name|getJSONObject
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|link
operator|.
name|getString
argument_list|(
literal|"@rel"
argument_list|)
operator|.
name|equals
argument_list|(
literal|"scidir"
argument_list|)
condition|)
block|{
name|sciLink
operator|=
name|link
operator|.
name|getString
argument_list|(
literal|"@href"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|JSONException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"No ScienceDirect link found in API request: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
return|return
name|sciLink
return|;
block|}
block|}
block|}
end_class

end_unit

