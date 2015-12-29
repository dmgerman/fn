begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.net
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|net
package|;
end_package

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|*
import|;
end_import

begin_class
DECL|class|Cookie
class|class
name|Cookie
block|{
DECL|field|name
specifier|private
specifier|final
name|String
name|name
decl_stmt|;
DECL|field|value
specifier|private
specifier|final
name|String
name|value
decl_stmt|;
DECL|field|domain
specifier|private
name|String
name|domain
decl_stmt|;
DECL|field|expires
specifier|private
name|Date
name|expires
decl_stmt|;
DECL|field|path
specifier|private
name|String
name|path
decl_stmt|;
comment|/**      * DateFormats should not be reused among instances (or rather among threads), because they are not thread-safe.      * If they are shared, their usage should be synchronized.      */
DECL|field|whiteSpaceFormat
specifier|private
specifier|final
name|DateFormat
name|whiteSpaceFormat
init|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"E, dd MMM yyyy k:m:s 'GMT'"
argument_list|,
name|Locale
operator|.
name|US
argument_list|)
decl_stmt|;
DECL|field|hyphenFormat
specifier|private
specifier|final
name|DateFormat
name|hyphenFormat
init|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"E, dd-MMM-yyyy k:m:s 'GMT'"
argument_list|,
name|Locale
operator|.
name|US
argument_list|)
decl_stmt|;
comment|/**      * Construct a cookie from the URI and header fields      *      * @param uri URI for cookie      * @param header Set of attributes in header      */
DECL|method|Cookie (URI uri, String header)
specifier|public
name|Cookie
parameter_list|(
name|URI
name|uri
parameter_list|,
name|String
name|header
parameter_list|)
block|{
name|String
index|[]
name|attributes
init|=
name|header
operator|.
name|split
argument_list|(
literal|";"
argument_list|)
decl_stmt|;
name|String
name|nameValue
init|=
name|attributes
index|[
literal|0
index|]
operator|.
name|trim
argument_list|()
decl_stmt|;
name|this
operator|.
name|name
operator|=
name|nameValue
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|nameValue
operator|.
name|indexOf
argument_list|(
literal|'='
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|value
operator|=
name|nameValue
operator|.
name|substring
argument_list|(
name|nameValue
operator|.
name|indexOf
argument_list|(
literal|'='
argument_list|)
operator|+
literal|1
argument_list|)
expr_stmt|;
name|this
operator|.
name|path
operator|=
literal|"/"
expr_stmt|;
name|this
operator|.
name|domain
operator|=
name|uri
operator|.
name|getHost
argument_list|()
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<
name|attributes
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|nameValue
operator|=
name|attributes
index|[
name|i
index|]
operator|.
name|trim
argument_list|()
expr_stmt|;
name|int
name|equals
init|=
name|nameValue
operator|.
name|indexOf
argument_list|(
literal|'='
argument_list|)
decl_stmt|;
if|if
condition|(
name|equals
operator|==
operator|-
literal|1
condition|)
block|{
continue|continue;
block|}
name|String
name|name
init|=
name|nameValue
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|equals
argument_list|)
decl_stmt|;
name|String
name|value
init|=
name|nameValue
operator|.
name|substring
argument_list|(
name|equals
operator|+
literal|1
argument_list|)
decl_stmt|;
if|if
condition|(
literal|"domain"
operator|.
name|equalsIgnoreCase
argument_list|(
name|name
argument_list|)
condition|)
block|{
name|String
name|uriDomain
init|=
name|uri
operator|.
name|getHost
argument_list|()
decl_stmt|;
if|if
condition|(
name|uriDomain
operator|.
name|equals
argument_list|(
name|value
argument_list|)
condition|)
block|{
name|this
operator|.
name|domain
operator|=
name|value
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
operator|!
name|value
operator|.
name|isEmpty
argument_list|()
operator|&&
operator|(
name|value
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|!=
literal|'.'
operator|)
condition|)
block|{
name|value
operator|=
literal|'.'
operator|+
name|value
expr_stmt|;
block|}
name|uriDomain
operator|=
name|uriDomain
operator|.
name|substring
argument_list|(
name|uriDomain
operator|.
name|indexOf
argument_list|(
literal|'.'
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|uriDomain
operator|.
name|equals
argument_list|(
name|value
argument_list|)
operator|&&
operator|!
name|uriDomain
operator|.
name|endsWith
argument_list|(
name|value
argument_list|)
operator|&&
operator|!
name|value
operator|.
name|endsWith
argument_list|(
name|uriDomain
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Trying to set foreign cookie"
argument_list|)
throw|;
block|}
name|this
operator|.
name|domain
operator|=
name|value
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
literal|"path"
operator|.
name|equalsIgnoreCase
argument_list|(
name|name
argument_list|)
condition|)
block|{
name|this
operator|.
name|path
operator|=
name|value
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"expires"
operator|.
name|equalsIgnoreCase
argument_list|(
name|name
argument_list|)
condition|)
block|{
try|try
block|{
name|this
operator|.
name|expires
operator|=
name|whiteSpaceFormat
operator|.
name|parse
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ParseException
name|e
parameter_list|)
block|{
try|try
block|{
name|this
operator|.
name|expires
operator|=
name|hyphenFormat
operator|.
name|parse
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ParseException
name|e2
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Bad date format in header: "
operator|+
name|value
argument_list|)
throw|;
block|}
block|}
block|}
block|}
block|}
DECL|method|hasExpired ()
specifier|public
name|boolean
name|hasExpired
parameter_list|()
block|{
if|if
condition|(
name|expires
operator|==
literal|null
condition|)
block|{
return|return
literal|false
return|;
block|}
name|Date
name|now
init|=
operator|new
name|Date
argument_list|()
decl_stmt|;
return|return
name|now
operator|.
name|after
argument_list|(
name|expires
argument_list|)
return|;
block|}
comment|/**      * Check if cookie isn't expired and if URI matches,      * should cookie be included in response.      *      * @param uri URI to check against      * @return true if match, false otherwise      */
DECL|method|matches (URI uri)
specifier|public
name|boolean
name|matches
parameter_list|(
name|URI
name|uri
parameter_list|)
block|{
if|if
condition|(
name|hasExpired
argument_list|()
condition|)
block|{
return|return
literal|false
return|;
block|}
name|String
name|uriPath
init|=
name|uri
operator|.
name|getPath
argument_list|()
decl_stmt|;
if|if
condition|(
name|uriPath
operator|==
literal|null
condition|)
block|{
name|uriPath
operator|=
literal|"/"
expr_stmt|;
block|}
return|return
name|uriPath
operator|.
name|startsWith
argument_list|(
name|this
operator|.
name|path
argument_list|)
return|;
block|}
DECL|method|equals (Cookie cookie)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Cookie
name|cookie
parameter_list|)
block|{
return|return
operator|(
operator|(
name|domain
operator|.
name|equals
argument_list|(
name|cookie
operator|.
name|domain
argument_list|)
operator|)
operator|&&
operator|(
name|name
operator|.
name|equals
argument_list|(
name|cookie
operator|.
name|name
argument_list|)
operator|)
operator|)
return|;
block|}
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|name
operator|+
literal|'='
operator|+
name|value
return|;
block|}
block|}
end_class

end_unit

