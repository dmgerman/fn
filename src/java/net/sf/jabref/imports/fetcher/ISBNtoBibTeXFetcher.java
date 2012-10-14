begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2012 JabRef contributors.     This program is free software: you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation, either version 3 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License     along with this program.  If not, see<http://www.gnu.org/licenses/>. */
end_comment

begin_package
DECL|package|net.sf.jabref.imports.fetcher
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
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
name|io
operator|.
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|OutputStreamWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|UnsupportedEncodingException
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
name|net
operator|.
name|URLEncoder
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Scanner
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
name|BibtexEntryType
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
name|GUIGlobals
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
name|JabRef
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
name|imports
operator|.
name|BibtexParser
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
name|imports
operator|.
name|EntryFetcher
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
name|imports
operator|.
name|ImportInspector
import|;
end_import

begin_comment
comment|/**  * This class uses Manas Tungare's ISBN to BibTeX Converter to convert an ISBN to a BibTeX entry<br />  * The online version of the converter is available at http://manas.tungare.name/software/isbn-to-bibtex/  */
end_comment

begin_class
DECL|class|ISBNtoBibTeXFetcher
specifier|public
class|class
name|ISBNtoBibTeXFetcher
implements|implements
name|EntryFetcher
block|{
DECL|field|URL_PATTERN
specifier|private
specifier|static
specifier|final
name|String
name|URL_PATTERN
init|=
literal|"http://manas.tungare.name/software/isbn-to-bibtex/isbn-service?isbn=%s"
decl_stmt|;
annotation|@
name|Override
DECL|method|stopFetching ()
specifier|public
name|void
name|stopFetching
parameter_list|()
block|{
comment|// nothing needed as the fetching is a single HTTP GET
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
name|String
name|q
decl_stmt|;
try|try
block|{
name|q
operator|=
name|URLEncoder
operator|.
name|encode
argument_list|(
name|query
argument_list|,
literal|"UTF-8"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsupportedEncodingException
name|e
parameter_list|)
block|{
comment|// this should never happen
name|status
operator|.
name|setStatus
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Error"
argument_list|)
argument_list|)
expr_stmt|;
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
return|return
literal|false
return|;
block|}
name|String
name|urlString
init|=
name|String
operator|.
name|format
argument_list|(
name|URL_PATTERN
argument_list|,
name|q
argument_list|)
decl_stmt|;
comment|// Send the request
name|URL
name|url
decl_stmt|;
name|URLConnection
name|conn
decl_stmt|;
try|try
block|{
name|url
operator|=
operator|new
name|URL
argument_list|(
name|urlString
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|MalformedURLException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
return|return
literal|false
return|;
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
return|return
literal|false
return|;
block|}
name|InputStream
name|source
decl_stmt|;
try|try
block|{
name|source
operator|=
name|url
operator|.
name|openStream
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
return|return
literal|false
return|;
block|}
name|String
name|bibtexString
init|=
operator|new
name|Scanner
argument_list|(
name|source
argument_list|)
operator|.
name|useDelimiter
argument_list|(
literal|"\\A"
argument_list|)
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|bibtexString
operator|.
name|startsWith
argument_list|(
literal|"@comment"
argument_list|)
condition|)
block|{
comment|// an error occured
comment|// the error is nested in @comment{...}
name|String
name|errorMsg
init|=
name|bibtexString
operator|.
name|substring
argument_list|(
literal|"@comment{"
operator|.
name|length
argument_list|()
argument_list|)
decl_stmt|;
name|errorMsg
operator|=
name|errorMsg
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|errorMsg
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
name|status
operator|.
name|showMessage
argument_list|(
name|errorMsg
argument_list|)
expr_stmt|;
comment|// showMessage does not work -> NPE
return|return
literal|false
return|;
block|}
name|BibtexEntry
name|entry
init|=
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
name|bibtexString
argument_list|)
decl_stmt|;
name|inspector
operator|.
name|addEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
return|return
literal|true
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
literal|"ISBN to BibTeX"
return|;
block|}
annotation|@
name|Override
DECL|method|getKeyName ()
specifier|public
name|String
name|getKeyName
parameter_list|()
block|{
return|return
literal|"ISBNtoBibTeX"
return|;
block|}
annotation|@
name|Override
DECL|method|getIcon ()
specifier|public
name|URL
name|getIcon
parameter_list|()
block|{
comment|// no special icon for this fetcher available.
comment|// Therefore, we return some kind of default icon
return|return
name|GUIGlobals
operator|.
name|getIconUrl
argument_list|(
literal|"www"
argument_list|)
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
literal|"ISBNtoBibTeXHelp.html"
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
comment|// no additional options available
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

