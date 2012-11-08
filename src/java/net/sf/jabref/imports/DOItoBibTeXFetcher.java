begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2012 JabRef contributors.     This program is free software: you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation, either version 3 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License     along with this program.  If not, see<http://www.gnu.org/licenses/>. */
end_comment

begin_package
DECL|package|net.sf.jabref.imports
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileNotFoundException
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
name|InputStream
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
name|OutputPrinter
import|;
end_import

begin_class
DECL|class|DOItoBibTeXFetcher
specifier|public
class|class
name|DOItoBibTeXFetcher
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
literal|"http://dx.doi.org/%s"
decl_stmt|;
DECL|method|stopFetching ()
specifier|public
name|void
name|stopFetching
parameter_list|()
block|{
comment|// nothing needed as the fetching is a single HTTP GET
block|}
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
name|URLConnection
name|conn
decl_stmt|;
try|try
block|{
name|conn
operator|=
name|url
operator|.
name|openConnection
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
name|conn
operator|.
name|setRequestProperty
argument_list|(
literal|"Accept"
argument_list|,
literal|"text/bibliography; style=bibtex"
argument_list|)
expr_stmt|;
name|String
name|bibtexString
decl_stmt|;
try|try
block|{
name|bibtexString
operator|=
name|getResults
argument_list|(
name|conn
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|FileNotFoundException
name|e
parameter_list|)
block|{
name|status
operator|.
name|showMessage
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Unknown DOI: '%0'."
argument_list|,
name|query
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Get BibTeX entry from DOI"
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
comment|// Do not use the provided key
comment|// entry.setField(BibtexFields.KEY_FIELD,null);
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
DECL|method|getTitle ()
specifier|public
name|String
name|getTitle
parameter_list|()
block|{
return|return
literal|"DOI to BibTeX"
return|;
block|}
DECL|method|getKeyName ()
specifier|public
name|String
name|getKeyName
parameter_list|()
block|{
return|return
literal|"DOItoBibTeX"
return|;
block|}
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
DECL|method|getHelpPage ()
specifier|public
name|String
name|getHelpPage
parameter_list|()
block|{
return|return
literal|"DOItoBibTeXHelp.html"
return|;
block|}
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
comment|/**      * Download the URL and return contents as a String.      * @param source      * @return      * @throws IOException      */
DECL|method|getResults (URLConnection source)
specifier|public
name|String
name|getResults
parameter_list|(
name|URLConnection
name|source
parameter_list|)
throws|throws
name|IOException
block|{
name|InputStream
name|in
init|=
name|source
operator|.
name|getInputStream
argument_list|()
decl_stmt|;
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|byte
index|[]
name|buffer
init|=
operator|new
name|byte
index|[
literal|256
index|]
decl_stmt|;
while|while
condition|(
literal|true
condition|)
block|{
name|int
name|bytesRead
init|=
name|in
operator|.
name|read
argument_list|(
name|buffer
argument_list|)
decl_stmt|;
if|if
condition|(
name|bytesRead
operator|==
operator|-
literal|1
condition|)
break|break;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|bytesRead
condition|;
name|i
operator|++
control|)
name|sb
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|buffer
index|[
name|i
index|]
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
block|}
end_class

end_unit

