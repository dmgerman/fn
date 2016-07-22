begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|help
operator|.
name|HelpFile
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
name|FieldName
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
name|preferences
operator|.
name|JabRefPreferences
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
comment|// not needed as the fetching is a single HTTP GET
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
name|Optional
argument_list|<
name|BibEntry
argument_list|>
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
name|entry
operator|.
name|ifPresent
argument_list|(
name|e
lambda|->
name|inspector
operator|.
name|addEntry
argument_list|(
name|e
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|entry
operator|.
name|isPresent
argument_list|()
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
name|HelpFile
name|getHelpPage
parameter_list|()
block|{
return|return
name|HelpFile
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
DECL|method|getEntryFromDOI (String doiStr)
specifier|public
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|getEntryFromDOI
parameter_list|(
name|String
name|doiStr
parameter_list|)
block|{
return|return
name|getEntryFromDOI
argument_list|(
name|doiStr
argument_list|,
literal|null
argument_list|)
return|;
block|}
DECL|method|getEntryFromDOI (String doiStr, ParserResult parserResult)
specifier|public
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|getEntryFromDOI
parameter_list|(
name|String
name|doiStr
parameter_list|,
name|ParserResult
name|parserResult
parameter_list|)
block|{
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
name|doiStr
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|doi
operator|.
name|isPresent
argument_list|()
condition|)
block|{
if|if
condition|(
name|parserResult
operator|!=
literal|null
condition|)
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
block|}
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
try|try
block|{
name|URL
name|doiURL
init|=
operator|new
name|URL
argument_list|(
name|doi
operator|.
name|get
argument_list|()
operator|.
name|getURIAsASCIIString
argument_list|()
argument_list|)
decl_stmt|;
comment|// BibTeX data
name|URLDownload
name|download
init|=
operator|new
name|URLDownload
argument_list|(
name|doiURL
argument_list|)
decl_stmt|;
name|download
operator|.
name|addParameters
argument_list|(
literal|"Accept"
argument_list|,
literal|"application/x-bibtex"
argument_list|)
expr_stmt|;
name|String
name|bibtexString
init|=
name|download
operator|.
name|downloadToString
argument_list|(
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
decl_stmt|;
name|bibtexString
operator|=
name|cleanupEncoding
argument_list|(
name|bibtexString
argument_list|)
expr_stmt|;
comment|// BibTeX entry
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
operator|==
literal|null
condition|)
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
comment|// Optionally re-format BibTeX entry
name|formatTitleField
argument_list|(
name|entry
argument_list|)
expr_stmt|;
return|return
name|Optional
operator|.
name|of
argument_list|(
name|entry
argument_list|)
return|;
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
literal|"Bad DOI URL"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|FileNotFoundException
name|e
parameter_list|)
block|{
if|if
condition|(
name|parserResult
operator|!=
literal|null
condition|)
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
name|get
argument_list|()
operator|.
name|getDOI
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
name|Optional
operator|.
name|empty
argument_list|()
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
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
block|}
DECL|method|formatTitleField (BibEntry entry)
specifier|private
name|void
name|formatTitleField
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
comment|// Optionally add curly brackets around key words to keep the case
name|entry
operator|.
name|getFieldOptional
argument_list|(
name|FieldName
operator|.
name|TITLE
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
name|FieldName
operator|.
name|TITLE
argument_list|,
name|title
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|cleanupEncoding (String bibtex)
specifier|private
name|String
name|cleanupEncoding
parameter_list|(
name|String
name|bibtex
parameter_list|)
block|{
comment|// Usually includes an en-dash in the page range. Char is in cp1252 but not
comment|// ISO 8859-1 (which is what latex expects). For convenience replace here.
return|return
name|bibtex
operator|.
name|replaceAll
argument_list|(
literal|"(pages=\\{[0-9]+)\u2013([0-9]+\\})"
argument_list|,
literal|"$1--$2"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

