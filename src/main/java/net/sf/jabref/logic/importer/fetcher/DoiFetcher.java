begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.importer.fetcher
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
name|NormalizePagesFormatter
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
name|importer
operator|.
name|FetcherException
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
name|importer
operator|.
name|IdBasedFetcher
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
name|importer
operator|.
name|ImportFormatPreferences
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
name|importer
operator|.
name|ParseException
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

begin_class
DECL|class|DoiFetcher
specifier|public
class|class
name|DoiFetcher
implements|implements
name|IdBasedFetcher
block|{
DECL|field|preferences
specifier|private
specifier|final
name|ImportFormatPreferences
name|preferences
decl_stmt|;
DECL|method|DoiFetcher (ImportFormatPreferences preferences)
specifier|public
name|DoiFetcher
parameter_list|(
name|ImportFormatPreferences
name|preferences
parameter_list|)
block|{
name|this
operator|.
name|preferences
operator|=
name|preferences
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
return|return
operator|new
name|NormalizePagesFormatter
argument_list|()
operator|.
name|format
argument_list|(
name|bibtex
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"DOI"
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
name|FETCHER_DOI
return|;
block|}
annotation|@
name|Override
DECL|method|performSearchById (String identifier)
specifier|public
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|performSearchById
parameter_list|(
name|String
name|identifier
parameter_list|)
throws|throws
name|FetcherException
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
name|identifier
argument_list|)
decl_stmt|;
try|try
block|{
if|if
condition|(
name|doi
operator|.
name|isPresent
argument_list|()
condition|)
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
comment|// BibTeX entry
return|return
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
name|cleanupEncoding
argument_list|(
name|bibtexString
argument_list|)
argument_list|,
name|preferences
argument_list|)
return|;
block|}
else|else
block|{
throw|throw
operator|new
name|FetcherException
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Invalid_DOI:_'%0'."
argument_list|,
name|identifier
argument_list|)
argument_list|)
throw|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|FetcherException
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Invalid URL"
argument_list|)
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|ParseException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|FetcherException
argument_list|(
literal|"Could not parse BibTeX entry"
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit

