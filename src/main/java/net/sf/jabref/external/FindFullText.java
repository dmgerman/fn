begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.external
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|external
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileWriter
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
name|io
operator|.
name|InputStreamReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Reader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|HttpURLConnection
import|;
end_import

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
name|URL
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URLConnection
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|BibtexEntry
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
name|util
operator|.
name|DOIUtil
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
name|Globals
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
name|net
operator|.
name|URLDownload
import|;
end_import

begin_comment
comment|/**  * Utility class for trying to resolve URLs to full-text PDF for articles.  */
end_comment

begin_class
DECL|class|FindFullText
specifier|public
class|class
name|FindFullText
block|{
specifier|private
specifier|static
specifier|final
name|int
DECL|field|FOUND_PDF
name|FOUND_PDF
init|=
literal|0
decl_stmt|;
DECL|field|WRONG_MIME_TYPE
specifier|public
specifier|static
specifier|final
name|int
name|WRONG_MIME_TYPE
init|=
literal|1
decl_stmt|;
DECL|field|UNKNOWN_DOMAIN
specifier|public
specifier|static
specifier|final
name|int
name|UNKNOWN_DOMAIN
init|=
literal|2
decl_stmt|;
DECL|field|LINK_NOT_FOUND
specifier|public
specifier|static
specifier|final
name|int
name|LINK_NOT_FOUND
init|=
literal|3
decl_stmt|;
DECL|field|IO_EXCEPTION
specifier|public
specifier|static
specifier|final
name|int
name|IO_EXCEPTION
init|=
literal|4
decl_stmt|;
DECL|field|NO_URLS_DEFINED
specifier|public
specifier|static
specifier|final
name|int
name|NO_URLS_DEFINED
init|=
literal|5
decl_stmt|;
DECL|field|finders
specifier|private
specifier|final
name|List
argument_list|<
name|FullTextFinder
argument_list|>
name|finders
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|method|FindFullText ()
specifier|public
name|FindFullText
parameter_list|()
block|{
name|finders
operator|.
name|add
argument_list|(
operator|new
name|ScienceDirectPdfDownload
argument_list|()
argument_list|)
expr_stmt|;
name|finders
operator|.
name|add
argument_list|(
operator|new
name|SpringerLinkPdfDownload
argument_list|()
argument_list|)
expr_stmt|;
name|finders
operator|.
name|add
argument_list|(
operator|new
name|ACSPdfDownload
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|findFullText (BibtexEntry entry)
specifier|public
name|FindResult
name|findFullText
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
name|String
name|urlText
init|=
name|entry
operator|.
name|getField
argument_list|(
literal|"url"
argument_list|)
decl_stmt|;
name|String
name|doiText
init|=
name|entry
operator|.
name|getField
argument_list|(
literal|"doi"
argument_list|)
decl_stmt|;
comment|// First try the DOI link, if defined:
if|if
condition|(
name|doiText
operator|!=
literal|null
operator|&&
operator|!
name|doiText
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|doiText
operator|=
name|DOIUtil
operator|.
name|getDOI
argument_list|(
name|doiText
argument_list|)
expr_stmt|;
name|FindResult
name|resDoi
init|=
name|lookForFullTextAtURL
argument_list|(
name|Globals
operator|.
name|DOI_LOOKUP_PREFIX
operator|+
name|doiText
argument_list|)
decl_stmt|;
if|if
condition|(
name|resDoi
operator|.
name|status
operator|==
name|FindFullText
operator|.
name|FOUND_PDF
condition|)
block|{
return|return
name|resDoi
return|;
block|}
elseif|else
if|if
condition|(
name|urlText
operator|!=
literal|null
operator|&&
operator|!
name|urlText
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|FindResult
name|resUrl
init|=
name|lookForFullTextAtURL
argument_list|(
name|urlText
argument_list|)
decl_stmt|;
if|if
condition|(
name|resUrl
operator|.
name|status
operator|==
name|FindFullText
operator|.
name|FOUND_PDF
condition|)
block|{
return|return
name|resUrl
return|;
block|}
else|else
block|{
return|return
name|resDoi
return|;
comment|// If both URL and DOI fail, we assume that the error code for DOI is
comment|// probably the most relevant.
block|}
block|}
else|else
block|{
return|return
name|resDoi
return|;
block|}
block|}
comment|// No DOI? Try URL:
elseif|else
if|if
condition|(
name|urlText
operator|!=
literal|null
operator|&&
operator|!
name|urlText
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
name|lookForFullTextAtURL
argument_list|(
name|urlText
argument_list|)
return|;
block|}
comment|// No URL either? Return error code.
else|else
block|{
return|return
operator|new
name|FindResult
argument_list|(
name|FindFullText
operator|.
name|NO_URLS_DEFINED
argument_list|,
literal|null
argument_list|)
return|;
block|}
block|}
DECL|method|lookForFullTextAtURL (String urlText)
specifier|private
name|FindResult
name|lookForFullTextAtURL
parameter_list|(
name|String
name|urlText
parameter_list|)
block|{
try|try
block|{
name|URL
name|url
init|=
operator|new
name|URL
argument_list|(
name|urlText
argument_list|)
decl_stmt|;
name|url
operator|=
name|resolveRedirects
argument_list|(
name|url
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|boolean
name|domainKnown
init|=
literal|false
decl_stmt|;
for|for
control|(
name|FullTextFinder
name|finder
range|:
name|finders
control|)
block|{
if|if
condition|(
name|finder
operator|.
name|supportsSite
argument_list|(
name|url
argument_list|)
condition|)
block|{
name|domainKnown
operator|=
literal|true
expr_stmt|;
name|URL
name|result
init|=
name|finder
operator|.
name|findFullTextURL
argument_list|(
name|url
argument_list|)
decl_stmt|;
if|if
condition|(
name|result
operator|!=
literal|null
condition|)
block|{
comment|// Check the MIME type of this URL to see if it is a PDF. If not,
comment|// it could be because the user doesn't have access:
try|try
block|{
name|String
name|mimeType
init|=
operator|new
name|URLDownload
argument_list|(
name|result
argument_list|)
operator|.
name|determineMimeType
argument_list|()
decl_stmt|;
if|if
condition|(
name|mimeType
operator|!=
literal|null
operator|&&
name|mimeType
operator|.
name|toLowerCase
argument_list|()
operator|.
name|equals
argument_list|(
literal|"application/pdf"
argument_list|)
condition|)
block|{
return|return
operator|new
name|FindResult
argument_list|(
name|result
argument_list|,
name|url
argument_list|)
return|;
block|}
else|else
block|{
operator|new
name|URLDownload
argument_list|(
name|result
argument_list|)
operator|.
name|downloadToFile
argument_list|(
operator|new
name|File
argument_list|(
literal|"page.html"
argument_list|)
argument_list|)
expr_stmt|;
return|return
operator|new
name|FindResult
argument_list|(
name|FindFullText
operator|.
name|WRONG_MIME_TYPE
argument_list|,
name|url
argument_list|)
return|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
return|return
operator|new
name|FindResult
argument_list|(
name|FindFullText
operator|.
name|IO_EXCEPTION
argument_list|,
name|url
argument_list|)
return|;
block|}
block|}
block|}
block|}
if|if
condition|(
operator|!
name|domainKnown
condition|)
block|{
return|return
operator|new
name|FindResult
argument_list|(
name|FindFullText
operator|.
name|UNKNOWN_DOMAIN
argument_list|,
name|url
argument_list|)
return|;
block|}
else|else
block|{
return|return
operator|new
name|FindResult
argument_list|(
name|FindFullText
operator|.
name|LINK_NOT_FOUND
argument_list|,
name|url
argument_list|)
return|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
return|return
literal|null
return|;
block|}
comment|/**      * Follow redirects until the final location is reached. This is necessary to handle DOI links, which      * redirect to publishers' web sites. We need to know the publisher's domain name in order to choose      * which FullTextFinder to use.      * @param url The url to start with.      * @param redirectCount The number of previous redirects. We will follow a maximum of 5 redirects.      * @return the final URL, or the initial one in case there is no redirect.      * @throws IOException for connection error      */
DECL|method|resolveRedirects (URL url, int redirectCount)
specifier|private
name|URL
name|resolveRedirects
parameter_list|(
name|URL
name|url
parameter_list|,
name|int
name|redirectCount
parameter_list|)
throws|throws
name|IOException
block|{
name|URLConnection
name|uc
init|=
name|url
operator|.
name|openConnection
argument_list|()
decl_stmt|;
if|if
condition|(
name|uc
operator|instanceof
name|HttpURLConnection
condition|)
block|{
name|HttpURLConnection
name|huc
init|=
operator|(
name|HttpURLConnection
operator|)
name|uc
decl_stmt|;
name|huc
operator|.
name|setInstanceFollowRedirects
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|huc
operator|.
name|connect
argument_list|()
expr_stmt|;
name|int
name|responseCode
init|=
name|huc
operator|.
name|getResponseCode
argument_list|()
decl_stmt|;
name|String
name|location
init|=
name|huc
operator|.
name|getHeaderField
argument_list|(
literal|"location"
argument_list|)
decl_stmt|;
name|huc
operator|.
name|disconnect
argument_list|()
expr_stmt|;
if|if
condition|(
name|responseCode
operator|==
name|HttpURLConnection
operator|.
name|HTTP_MOVED_TEMP
operator|&&
name|redirectCount
operator|<
literal|5
condition|)
block|{
comment|//System.out.println(responseCode);
comment|//System.out.println(location);
try|try
block|{
name|URL
name|newUrl
init|=
operator|new
name|URL
argument_list|(
name|location
argument_list|)
decl_stmt|;
return|return
name|resolveRedirects
argument_list|(
name|newUrl
argument_list|,
name|redirectCount
operator|+
literal|1
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|MalformedURLException
name|ex
parameter_list|)
block|{
return|return
name|url
return|;
comment|// take the previous one, since this one didn't make sense.
comment|// TODO: this could be caused by location being a relative link, but this would just give
comment|// the default page in the case of www.springerlink.com, not the article page. Don't know why.
block|}
block|}
else|else
block|{
return|return
name|url
return|;
block|}
block|}
else|else
block|{
return|return
name|url
return|;
block|}
block|}
DECL|method|loadPage (URL url)
specifier|public
specifier|static
name|String
name|loadPage
parameter_list|(
name|URL
name|url
parameter_list|)
throws|throws
name|IOException
block|{
name|Reader
name|in
init|=
literal|null
decl_stmt|;
name|URLConnection
name|uc
decl_stmt|;
name|HttpURLConnection
name|huc
init|=
literal|null
decl_stmt|;
try|try
block|{
name|uc
operator|=
name|url
operator|.
name|openConnection
argument_list|()
expr_stmt|;
if|if
condition|(
name|uc
operator|instanceof
name|HttpURLConnection
condition|)
block|{
name|huc
operator|=
operator|(
name|HttpURLConnection
operator|)
name|uc
expr_stmt|;
name|huc
operator|.
name|setInstanceFollowRedirects
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|huc
operator|.
name|connect
argument_list|()
expr_stmt|;
name|in
operator|=
operator|new
name|InputStreamReader
argument_list|(
name|huc
operator|.
name|getInputStream
argument_list|()
argument_list|)
expr_stmt|;
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|int
name|c
decl_stmt|;
while|while
condition|(
operator|(
name|c
operator|=
name|in
operator|.
name|read
argument_list|()
operator|)
operator|!=
operator|-
literal|1
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
comment|// TODO: are other types of connection (https?) relevant?
block|}
block|}
finally|finally
block|{
try|try
block|{
if|if
condition|(
name|in
operator|!=
literal|null
condition|)
block|{
name|in
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|huc
operator|!=
literal|null
condition|)
block|{
name|huc
operator|.
name|disconnect
argument_list|()
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
block|}
DECL|class|FindResult
specifier|public
specifier|static
class|class
name|FindResult
block|{
DECL|field|url
specifier|public
specifier|final
name|URL
name|url
decl_stmt|;
DECL|field|host
specifier|public
name|String
name|host
init|=
literal|null
decl_stmt|;
DECL|field|status
specifier|public
specifier|final
name|int
name|status
decl_stmt|;
DECL|method|FindResult (URL url, URL originalUrl)
specifier|public
name|FindResult
parameter_list|(
name|URL
name|url
parameter_list|,
name|URL
name|originalUrl
parameter_list|)
block|{
name|this
operator|.
name|url
operator|=
name|url
expr_stmt|;
name|this
operator|.
name|status
operator|=
name|FindFullText
operator|.
name|FOUND_PDF
expr_stmt|;
if|if
condition|(
name|originalUrl
operator|!=
literal|null
condition|)
block|{
name|host
operator|=
name|originalUrl
operator|.
name|getHost
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|FindResult (int status, URL originalUrl)
specifier|public
name|FindResult
parameter_list|(
name|int
name|status
parameter_list|,
name|URL
name|originalUrl
parameter_list|)
block|{
name|this
operator|.
name|url
operator|=
literal|null
expr_stmt|;
name|this
operator|.
name|status
operator|=
name|status
expr_stmt|;
if|if
condition|(
name|originalUrl
operator|!=
literal|null
condition|)
block|{
name|this
operator|.
name|host
operator|=
name|originalUrl
operator|.
name|getHost
argument_list|()
expr_stmt|;
block|}
block|}
block|}
DECL|method|dumpToFile (String text, File f)
specifier|public
specifier|static
name|void
name|dumpToFile
parameter_list|(
name|String
name|text
parameter_list|,
name|File
name|f
parameter_list|)
block|{
try|try
block|{
name|FileWriter
name|fw
init|=
operator|new
name|FileWriter
argument_list|(
name|f
argument_list|)
decl_stmt|;
name|fw
operator|.
name|write
argument_list|(
name|text
argument_list|)
expr_stmt|;
name|fw
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

