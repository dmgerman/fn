begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.importer.fetcher
package|package
name|org
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
name|util
operator|.
name|Optional
import|;
end_import

begin_import
import|import
name|org
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
name|org
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
name|org
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
name|org
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
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|WebFetchers
import|;
end_import

begin_import
import|import
name|org
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
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|field
operator|.
name|StandardField
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|identifier
operator|.
name|DOI
import|;
end_import

begin_class
DECL|class|TitleFetcher
specifier|public
class|class
name|TitleFetcher
implements|implements
name|IdBasedFetcher
block|{
DECL|field|preferences
specifier|private
specifier|final
name|ImportFormatPreferences
name|preferences
decl_stmt|;
DECL|method|TitleFetcher (ImportFormatPreferences preferences)
specifier|public
name|TitleFetcher
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
annotation|@
name|Override
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Title"
return|;
block|}
annotation|@
name|Override
DECL|method|getHelpPage ()
specifier|public
name|Optional
argument_list|<
name|HelpFile
argument_list|>
name|getHelpPage
parameter_list|()
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|HelpFile
operator|.
name|FETCHER_TITLE
argument_list|)
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
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|,
name|identifier
argument_list|)
expr_stmt|;
name|Optional
argument_list|<
name|DOI
argument_list|>
name|doi
init|=
name|WebFetchers
operator|.
name|getIdFetcherForIdentifier
argument_list|(
name|DOI
operator|.
name|class
argument_list|)
operator|.
name|findIdentifier
argument_list|(
name|entry
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
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
name|DoiFetcher
name|doiFetcher
init|=
operator|new
name|DoiFetcher
argument_list|(
name|this
operator|.
name|preferences
argument_list|)
decl_stmt|;
return|return
name|doiFetcher
operator|.
name|performSearchById
argument_list|(
name|doi
operator|.
name|get
argument_list|()
operator|.
name|getDOI
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

