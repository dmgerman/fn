begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright (C) 2003-2016 JabRef contributors.  * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *  * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *  * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.importer
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
name|Optional
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

begin_class
DECL|class|MimeTypeDetector
specifier|public
class|class
name|MimeTypeDetector
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
name|MimeTypeDetector
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|method|isPdfContentType (String url)
specifier|public
specifier|static
name|boolean
name|isPdfContentType
parameter_list|(
name|String
name|url
parameter_list|)
block|{
name|Optional
argument_list|<
name|String
argument_list|>
name|contentType
init|=
name|getMimeType
argument_list|(
name|url
argument_list|)
decl_stmt|;
return|return
name|contentType
operator|.
name|isPresent
argument_list|()
operator|&&
name|contentType
operator|.
name|get
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"application/pdf"
argument_list|)
return|;
block|}
DECL|method|getMimeType (String url)
specifier|private
specifier|static
name|Optional
argument_list|<
name|String
argument_list|>
name|getMimeType
parameter_list|(
name|String
name|url
parameter_list|)
block|{
name|Unirest
operator|.
name|setDefaultHeader
argument_list|(
literal|"User-Agent"
argument_list|,
literal|"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2"
argument_list|)
expr_stmt|;
comment|// Try to use HEAD request to avoid donloading the whole file
name|String
name|contentType
decl_stmt|;
try|try
block|{
name|contentType
operator|=
name|Unirest
operator|.
name|head
argument_list|(
name|url
argument_list|)
operator|.
name|asString
argument_list|()
operator|.
name|getHeaders
argument_list|()
operator|.
name|get
argument_list|(
literal|"Content-Type"
argument_list|)
operator|.
name|get
argument_list|(
literal|0
argument_list|)
expr_stmt|;
if|if
condition|(
name|contentType
operator|!=
literal|null
condition|)
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|contentType
argument_list|)
return|;
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
name|debug
argument_list|(
literal|"Error getting MIME type of URL via HEAD request"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
comment|// Use GET request as alternative if no HEAD request is available
try|try
block|{
name|contentType
operator|=
name|Unirest
operator|.
name|get
argument_list|(
name|url
argument_list|)
operator|.
name|asString
argument_list|()
operator|.
name|getHeaders
argument_list|()
operator|.
name|get
argument_list|(
literal|"Content-Type"
argument_list|)
operator|.
name|get
argument_list|(
literal|0
argument_list|)
expr_stmt|;
if|if
condition|(
name|contentType
operator|!=
literal|null
condition|)
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|contentType
argument_list|)
return|;
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
name|debug
argument_list|(
literal|"Error getting MIME type of URL via GET request"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
comment|// Try to resolve local URIs
try|try
block|{
name|URLConnection
name|connection
init|=
operator|new
name|URL
argument_list|(
name|url
argument_list|)
operator|.
name|openConnection
argument_list|()
decl_stmt|;
return|return
name|Optional
operator|.
name|ofNullable
argument_list|(
name|connection
operator|.
name|getContentType
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Error trying to get MIME type of local URI"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
block|}
end_class

end_unit

