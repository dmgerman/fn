begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2014 JabRef contributors.     Copyright (C) 2015 Oliver Kopp      This program is free software: you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation, either version 3 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License     along with this program.  If not, see<http://www.gnu.org/licenses/>.  */
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
name|URI
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
name|nio
operator|.
name|charset
operator|.
name|StandardCharsets
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
name|gui
operator|.
name|help
operator|.
name|HelpFiles
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
name|ParserResult
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
name|logic
operator|.
name|formatter
operator|.
name|bibtexfields
operator|.
name|UnitsToLatexFormatter
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
name|formatter
operator|.
name|casechanger
operator|.
name|ProtectTermsFormatter
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
name|logic
operator|.
name|net
operator|.
name|URLDownload
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

begin_class
DECL|class|DOItoBibTeXFetcher
specifier|public
class|class
name|DOItoBibTeXFetcher
implements|implements
name|EntryFetcher
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
name|DOItoBibTeXFetcher
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|protectTermsFormatter
specifier|private
specifier|final
name|ProtectTermsFormatter
name|protectTermsFormatter
init|=
operator|new
name|ProtectTermsFormatter
argument_list|()
decl_stmt|;
DECL|field|unitsToLatexFormatter
specifier|private
specifier|final
name|UnitsToLatexFormatter
name|unitsToLatexFormatter
init|=
operator|new
name|UnitsToLatexFormatter
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
name|ParserResult
name|parserResult
init|=
operator|new
name|ParserResult
argument_list|()
decl_stmt|;
name|BibEntry
name|entry
init|=
name|getEntryFromDOI
argument_list|(
name|query
argument_list|,
name|parserResult
argument_list|)
decl_stmt|;
if|if
condition|(
name|parserResult
operator|.
name|hasWarnings
argument_list|()
condition|)
block|{
name|status
operator|.
name|showMessage
argument_list|(
name|parserResult
operator|.
name|getErrorMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|entry
operator|!=
literal|null
condition|)
block|{
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
literal|"DOI to BibTeX"
return|;
block|}
annotation|@
name|Override
DECL|method|getHelpPage ()
specifier|public
name|HelpFiles
name|getHelpPage
parameter_list|()
block|{
return|return
name|HelpFiles
operator|.
name|FETCHER_DOI_TO_BIBTEX
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
DECL|method|getEntryFromDOI (String doiStr, ParserResult parserResult)
specifier|public
name|BibEntry
name|getEntryFromDOI
parameter_list|(
name|String
name|doiStr
parameter_list|,
name|ParserResult
name|parserResult
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|parserResult
argument_list|)
expr_stmt|;
name|DOI
name|doi
decl_stmt|;
try|try
block|{
name|doi
operator|=
operator|new
name|DOI
argument_list|(
name|doiStr
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|e
parameter_list|)
block|{
name|parserResult
operator|.
name|addWarning
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Invalid DOI: '%0'."
argument_list|,
name|doiStr
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
comment|// Send the request
comment|// construct URL
name|URL
name|url
decl_stmt|;
try|try
block|{
name|Optional
argument_list|<
name|URI
argument_list|>
name|uri
init|=
name|doi
operator|.
name|getURI
argument_list|()
decl_stmt|;
if|if
condition|(
name|uri
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|url
operator|=
name|uri
operator|.
name|get
argument_list|()
operator|.
name|toURL
argument_list|()
expr_stmt|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
catch|catch
parameter_list|(
name|MalformedURLException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Bad URL"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
name|String
name|bibtexString
init|=
literal|""
decl_stmt|;
try|try
block|{
name|URLDownload
name|dl
init|=
operator|new
name|URLDownload
argument_list|(
name|url
argument_list|)
decl_stmt|;
name|dl
operator|.
name|addParameters
argument_list|(
literal|"Accept"
argument_list|,
literal|"application/x-bibtex"
argument_list|)
expr_stmt|;
name|bibtexString
operator|=
name|dl
operator|.
name|downloadToString
argument_list|(
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|FileNotFoundException
name|e
parameter_list|)
block|{
name|parserResult
operator|.
name|addWarning
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Unknown DOI: '%0'."
argument_list|,
name|doi
operator|.
name|getDOI
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Unknown DOI"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|null
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
name|warn
argument_list|(
literal|"Communication problems"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
comment|//Usually includes an en-dash in the page range. Char is in cp1252 but not
comment|// ISO 8859-1 (which is what latex expects). For convenience replace here.
name|bibtexString
operator|=
name|bibtexString
operator|.
name|replaceAll
argument_list|(
literal|"(pages=\\{[0-9]+)\u2013([0-9]+\\})"
argument_list|,
literal|"$1--$2"
argument_list|)
expr_stmt|;
name|BibEntry
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
name|entry
operator|.
name|getFieldOptional
argument_list|(
literal|"title"
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|title
lambda|->
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
name|unitsToLatexFormatter
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
name|protectTermsFormatter
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
argument_list|)
expr_stmt|;
block|}
return|return
name|entry
return|;
block|}
block|}
end_class

end_unit

