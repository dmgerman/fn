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
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
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
name|EntryBasedFetcher
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
name|FieldName
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
name|util
operator|.
name|OptionalUtil
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jsoup
operator|.
name|helper
operator|.
name|StringUtil
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_comment
comment|/**  * Fetcher for ISBN trying ebook.de first and then chimbori.com  */
end_comment

begin_class
DECL|class|IsbnFetcher
specifier|public
class|class
name|IsbnFetcher
implements|implements
name|EntryBasedFetcher
implements|,
name|IdBasedFetcher
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Logger
name|LOGGER
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|IsbnFetcher
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|importFormatPreferences
specifier|protected
specifier|final
name|ImportFormatPreferences
name|importFormatPreferences
decl_stmt|;
DECL|method|IsbnFetcher (ImportFormatPreferences importFormatPreferences)
specifier|public
name|IsbnFetcher
parameter_list|(
name|ImportFormatPreferences
name|importFormatPreferences
parameter_list|)
block|{
name|this
operator|.
name|importFormatPreferences
operator|=
name|importFormatPreferences
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
literal|"ISBN"
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
name|FETCHER_ISBN
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
if|if
condition|(
name|StringUtil
operator|.
name|isBlank
argument_list|(
name|identifier
argument_list|)
condition|)
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
name|IsbnViaEbookDeFetcher
name|isbnViaEbookDeFetcher
init|=
operator|new
name|IsbnViaEbookDeFetcher
argument_list|(
name|importFormatPreferences
argument_list|)
decl_stmt|;
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|bibEntry
init|=
name|isbnViaEbookDeFetcher
operator|.
name|performSearchById
argument_list|(
name|identifier
argument_list|)
decl_stmt|;
comment|// nothing found at ebook.de, try chimbori.com
if|if
condition|(
operator|!
name|bibEntry
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"No entry found at ebook.de try chimbori.com"
argument_list|)
expr_stmt|;
name|IsbnViaChimboriFetcher
name|isbnViaChimboriFetcher
init|=
operator|new
name|IsbnViaChimboriFetcher
argument_list|(
name|importFormatPreferences
argument_list|)
decl_stmt|;
name|bibEntry
operator|=
name|isbnViaChimboriFetcher
operator|.
name|performSearchById
argument_list|(
name|identifier
argument_list|)
expr_stmt|;
block|}
return|return
name|bibEntry
return|;
block|}
annotation|@
name|Override
DECL|method|performSearch (BibEntry entry)
specifier|public
name|List
argument_list|<
name|BibEntry
argument_list|>
name|performSearch
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
throws|throws
name|FetcherException
block|{
name|Optional
argument_list|<
name|String
argument_list|>
name|isbn
init|=
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|ISBN
argument_list|)
decl_stmt|;
if|if
condition|(
name|isbn
operator|.
name|isPresent
argument_list|()
condition|)
block|{
return|return
name|OptionalUtil
operator|.
name|toList
argument_list|(
name|performSearchById
argument_list|(
name|isbn
operator|.
name|get
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
block|}
block|}
end_class

end_unit

