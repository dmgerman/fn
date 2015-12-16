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
name|logic
operator|.
name|io
operator|.
name|MimeTypeDetector
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
name|Connection
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
name|org
operator|.
name|jsoup
operator|.
name|select
operator|.
name|Elements
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
comment|/**  * FullTextFinder implementation that follows the DOI resolution redirects and scans for a full-text PDF URL.  */
end_comment

begin_class
DECL|class|DoiResolution
specifier|public
class|class
name|DoiResolution
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
name|DoiResolution
operator|.
name|class
argument_list|)
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
name|String
name|sciLink
init|=
name|doi
operator|.
name|get
argument_list|()
operator|.
name|getURLAsASCIIString
argument_list|()
decl_stmt|;
comment|// follow all redirects and scan for a single pdf link
try|try
block|{
if|if
condition|(
operator|!
name|sciLink
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Connection
name|connection
init|=
name|Jsoup
operator|.
name|connect
argument_list|(
name|sciLink
argument_list|)
operator|.
name|followRedirects
argument_list|(
literal|true
argument_list|)
operator|.
name|ignoreHttpErrors
argument_list|(
literal|true
argument_list|)
decl_stmt|;
name|Document
name|html
init|=
name|connection
operator|.
name|get
argument_list|()
decl_stmt|;
comment|// scan for PDF
name|Elements
name|elements
init|=
name|html
operator|.
name|body
argument_list|()
operator|.
name|select
argument_list|(
literal|"[href]"
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|Optional
argument_list|<
name|URL
argument_list|>
argument_list|>
name|links
init|=
operator|new
name|ArrayList
argument_list|<
name|Optional
argument_list|<
name|URL
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Element
name|element
range|:
name|elements
control|)
block|{
name|String
name|href
init|=
name|element
operator|.
name|attr
argument_list|(
literal|"abs:href"
argument_list|)
decl_stmt|;
comment|// Only check if pdf is included in the link
comment|// FIXME: see https://github.com/lehner/LocalCopy for scrape ideas
if|if
condition|(
name|href
operator|.
name|contains
argument_list|(
literal|"pdf"
argument_list|)
condition|)
block|{
if|if
condition|(
name|MimeTypeDetector
operator|.
name|isPdfContentType
argument_list|(
name|href
argument_list|)
condition|)
block|{
name|links
operator|.
name|add
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|URL
argument_list|(
name|href
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|// return if only one link was found (high accuracy)
if|if
condition|(
name|links
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Fulltext PDF found @ "
operator|+
name|sciLink
argument_list|)
expr_stmt|;
name|pdfLink
operator|=
name|links
operator|.
name|get
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"DoiResolution fetcher failed: "
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|pdfLink
return|;
block|}
block|}
end_class

end_unit

