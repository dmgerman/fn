begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2012 JabRef contributors.     This program is free software: you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation, either version 3 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License     along with this program.  If not, see<http://www.gnu.org/licenses/>. */
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
name|importer
operator|.
name|*
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
name|model
operator|.
name|entry
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
name|JabRefPreferences
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
name|util
operator|.
name|Util
import|;
end_import

begin_class
DECL|class|DiVAtoBibTeXFetcher
specifier|public
class|class
name|DiVAtoBibTeXFetcher
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
literal|"http://www.diva-portal.org/smash/getreferences?referenceFormat=BibTex&pids=%s"
decl_stmt|;
DECL|field|caseKeeper
specifier|private
specifier|final
name|CaseKeeper
name|caseKeeper
init|=
operator|new
name|CaseKeeper
argument_list|()
decl_stmt|;
DECL|field|unitFormatter
specifier|private
specifier|final
name|UnitFormatter
name|unitFormatter
init|=
operator|new
name|UnitFormatter
argument_list|()
decl_stmt|;
DECL|field|htmlConverter
specifier|private
specifier|final
name|HTMLConverter
name|htmlConverter
init|=
operator|new
name|HTMLConverter
argument_list|()
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
name|Localization
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
name|DiVAtoBibTeXFetcher
operator|.
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
name|String
name|bibtexString
decl_stmt|;
try|try
block|{
name|bibtexString
operator|=
name|Util
operator|.
name|getResultsWithEncoding
argument_list|(
name|url
argument_list|,
literal|"UTF-8"
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"Unknown DiVA entry: '%0'."
argument_list|,
name|query
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Get BibTeX entry from DiVA"
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
if|if
condition|(
name|entry
operator|!=
literal|null
condition|)
block|{
comment|// Optionally add curly brackets around key words to keep the case
name|String
name|title
init|=
name|entry
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
decl_stmt|;
if|if
condition|(
name|title
operator|!=
literal|null
condition|)
block|{
comment|// Unit formatting
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_UNIT_FORMATTER_ON_SEARCH
argument_list|)
condition|)
block|{
name|title
operator|=
name|unitFormatter
operator|.
name|format
argument_list|(
name|title
argument_list|)
expr_stmt|;
block|}
comment|// Case keeping
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_CASE_KEEPER_ON_SEARCH
argument_list|)
condition|)
block|{
name|title
operator|=
name|caseKeeper
operator|.
name|format
argument_list|(
name|title
argument_list|)
expr_stmt|;
block|}
name|entry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
name|title
argument_list|)
expr_stmt|;
block|}
name|String
name|institution
init|=
name|entry
operator|.
name|getField
argument_list|(
literal|"institution"
argument_list|)
decl_stmt|;
if|if
condition|(
name|institution
operator|!=
literal|null
condition|)
block|{
name|institution
operator|=
name|htmlConverter
operator|.
name|formatUnicode
argument_list|(
name|institution
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"institution"
argument_list|,
name|institution
argument_list|)
expr_stmt|;
block|}
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
literal|"DiVA"
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
literal|"DiVAtoBibTeXHelp.html"
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

