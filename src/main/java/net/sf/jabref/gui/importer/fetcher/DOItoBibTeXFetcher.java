begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.importer.fetcher
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
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
name|logic
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
name|logic
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
name|logic
operator|.
name|importer
operator|.
name|fetcher
operator|.
name|DOItoBibTeX
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
DECL|class|DOItoBibTeXFetcher
specifier|public
class|class
name|DOItoBibTeXFetcher
implements|implements
name|EntryFetcher
block|{
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
name|DOItoBibTeX
operator|.
name|getEntryFromDOI
argument_list|(
name|query
argument_list|,
name|parserResult
argument_list|,
name|ImportFormatPreferences
operator|.
name|fromPreferences
argument_list|(
name|Globals
operator|.
name|prefs
argument_list|)
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
block|}
end_class

end_unit

