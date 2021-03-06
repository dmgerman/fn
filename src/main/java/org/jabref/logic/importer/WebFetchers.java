begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.importer
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Comparator
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
name|importer
operator|.
name|fetcher
operator|.
name|ACMPortalFetcher
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
name|fetcher
operator|.
name|ACS
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
name|fetcher
operator|.
name|ArXiv
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
name|fetcher
operator|.
name|AstrophysicsDataSystem
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
name|fetcher
operator|.
name|CiteSeer
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
name|fetcher
operator|.
name|CrossRef
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
name|fetcher
operator|.
name|DBLPFetcher
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
name|fetcher
operator|.
name|DOAJFetcher
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
name|fetcher
operator|.
name|DiVA
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
name|fetcher
operator|.
name|DoiFetcher
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
name|fetcher
operator|.
name|DoiResolution
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
name|fetcher
operator|.
name|GoogleScholar
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
name|fetcher
operator|.
name|GvkFetcher
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
name|fetcher
operator|.
name|IEEE
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
name|fetcher
operator|.
name|INSPIREFetcher
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
name|fetcher
operator|.
name|IacrEprintFetcher
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
name|fetcher
operator|.
name|IsbnFetcher
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
name|fetcher
operator|.
name|LibraryOfCongress
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
name|fetcher
operator|.
name|MathSciNet
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
name|fetcher
operator|.
name|MedlineFetcher
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
name|fetcher
operator|.
name|OpenAccessDoi
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
name|fetcher
operator|.
name|RfcFetcher
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
name|fetcher
operator|.
name|ScienceDirect
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
name|fetcher
operator|.
name|SpringerFetcher
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
name|fetcher
operator|.
name|SpringerLink
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
name|fetcher
operator|.
name|TitleFetcher
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
name|fetcher
operator|.
name|ZbMATH
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
name|Field
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
name|Identifier
import|;
end_import

begin_import
import|import static
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
operator|.
name|EPRINT
import|;
end_import

begin_import
import|import static
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
operator|.
name|ISBN
import|;
end_import

begin_class
DECL|class|WebFetchers
specifier|public
class|class
name|WebFetchers
block|{
DECL|method|WebFetchers ()
specifier|private
name|WebFetchers
parameter_list|()
block|{     }
DECL|method|getIdBasedFetcherForField (Field field, ImportFormatPreferences preferences)
specifier|public
specifier|static
name|Optional
argument_list|<
name|IdBasedFetcher
argument_list|>
name|getIdBasedFetcherForField
parameter_list|(
name|Field
name|field
parameter_list|,
name|ImportFormatPreferences
name|preferences
parameter_list|)
block|{
name|IdBasedFetcher
name|fetcher
decl_stmt|;
if|if
condition|(
name|field
operator|==
name|StandardField
operator|.
name|DOI
condition|)
block|{
name|fetcher
operator|=
operator|new
name|DoiFetcher
argument_list|(
name|preferences
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|field
operator|==
name|ISBN
condition|)
block|{
name|fetcher
operator|=
operator|new
name|IsbnFetcher
argument_list|(
name|preferences
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|field
operator|==
name|EPRINT
condition|)
block|{
name|fetcher
operator|=
operator|new
name|ArXiv
argument_list|(
name|preferences
argument_list|)
expr_stmt|;
block|}
else|else
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
return|return
name|Optional
operator|.
name|of
argument_list|(
name|fetcher
argument_list|)
return|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
DECL|method|getIdFetcherForIdentifier (Class<T> clazz)
specifier|public
specifier|static
parameter_list|<
name|T
extends|extends
name|Identifier
parameter_list|>
name|IdFetcher
argument_list|<
name|T
argument_list|>
name|getIdFetcherForIdentifier
parameter_list|(
name|Class
argument_list|<
name|T
argument_list|>
name|clazz
parameter_list|)
block|{
if|if
condition|(
name|clazz
operator|==
name|DOI
operator|.
name|class
condition|)
block|{
return|return
operator|(
name|IdFetcher
argument_list|<
name|T
argument_list|>
operator|)
operator|new
name|CrossRef
argument_list|()
return|;
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"No fetcher found for identifier"
operator|+
name|clazz
operator|.
name|getCanonicalName
argument_list|()
argument_list|)
throw|;
block|}
block|}
DECL|method|getIdFetcherForField (Field field)
specifier|public
specifier|static
name|Optional
argument_list|<
name|IdFetcher
argument_list|<
name|?
extends|extends
name|Identifier
argument_list|>
argument_list|>
name|getIdFetcherForField
parameter_list|(
name|Field
name|field
parameter_list|)
block|{
if|if
condition|(
name|field
operator|==
name|StandardField
operator|.
name|DOI
condition|)
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|CrossRef
argument_list|()
argument_list|)
return|;
block|}
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
DECL|method|getSearchBasedFetchers (ImportFormatPreferences importFormatPreferences)
specifier|public
specifier|static
name|List
argument_list|<
name|SearchBasedFetcher
argument_list|>
name|getSearchBasedFetchers
parameter_list|(
name|ImportFormatPreferences
name|importFormatPreferences
parameter_list|)
block|{
name|ArrayList
argument_list|<
name|SearchBasedFetcher
argument_list|>
name|list
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|ArXiv
argument_list|(
name|importFormatPreferences
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|INSPIREFetcher
argument_list|(
name|importFormatPreferences
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|GvkFetcher
argument_list|()
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|MedlineFetcher
argument_list|()
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|AstrophysicsDataSystem
argument_list|(
name|importFormatPreferences
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|MathSciNet
argument_list|(
name|importFormatPreferences
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|ZbMATH
argument_list|(
name|importFormatPreferences
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|ACMPortalFetcher
argument_list|(
name|importFormatPreferences
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|GoogleScholar
argument_list|(
name|importFormatPreferences
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|DBLPFetcher
argument_list|(
name|importFormatPreferences
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|SpringerFetcher
argument_list|()
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|CrossRef
argument_list|()
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|CiteSeer
argument_list|()
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|DOAJFetcher
argument_list|(
name|importFormatPreferences
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|IEEE
argument_list|(
name|importFormatPreferences
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|sort
argument_list|(
name|Comparator
operator|.
name|comparing
argument_list|(
name|WebFetcher
operator|::
name|getName
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|list
return|;
block|}
DECL|method|getIdBasedFetchers (ImportFormatPreferences importFormatPreferences)
specifier|public
specifier|static
name|List
argument_list|<
name|IdBasedFetcher
argument_list|>
name|getIdBasedFetchers
parameter_list|(
name|ImportFormatPreferences
name|importFormatPreferences
parameter_list|)
block|{
name|ArrayList
argument_list|<
name|IdBasedFetcher
argument_list|>
name|list
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|ArXiv
argument_list|(
name|importFormatPreferences
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|AstrophysicsDataSystem
argument_list|(
name|importFormatPreferences
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|IsbnFetcher
argument_list|(
name|importFormatPreferences
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|DiVA
argument_list|(
name|importFormatPreferences
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|DoiFetcher
argument_list|(
name|importFormatPreferences
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|MedlineFetcher
argument_list|()
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|TitleFetcher
argument_list|(
name|importFormatPreferences
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|MathSciNet
argument_list|(
name|importFormatPreferences
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|CrossRef
argument_list|()
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|LibraryOfCongress
argument_list|(
name|importFormatPreferences
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|IacrEprintFetcher
argument_list|(
name|importFormatPreferences
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|RfcFetcher
argument_list|(
name|importFormatPreferences
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|sort
argument_list|(
name|Comparator
operator|.
name|comparing
argument_list|(
name|WebFetcher
operator|::
name|getName
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|list
return|;
block|}
DECL|method|getEntryBasedFetchers (ImportFormatPreferences importFormatPreferences)
specifier|public
specifier|static
name|List
argument_list|<
name|EntryBasedFetcher
argument_list|>
name|getEntryBasedFetchers
parameter_list|(
name|ImportFormatPreferences
name|importFormatPreferences
parameter_list|)
block|{
name|ArrayList
argument_list|<
name|EntryBasedFetcher
argument_list|>
name|list
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|AstrophysicsDataSystem
argument_list|(
name|importFormatPreferences
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|DoiFetcher
argument_list|(
name|importFormatPreferences
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|IsbnFetcher
argument_list|(
name|importFormatPreferences
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|MathSciNet
argument_list|(
name|importFormatPreferences
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|CrossRef
argument_list|()
argument_list|)
expr_stmt|;
name|list
operator|.
name|sort
argument_list|(
name|Comparator
operator|.
name|comparing
argument_list|(
name|WebFetcher
operator|::
name|getName
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|list
return|;
block|}
DECL|method|getIdFetchers (ImportFormatPreferences importFormatPreferences)
specifier|public
specifier|static
name|List
argument_list|<
name|IdFetcher
argument_list|>
name|getIdFetchers
parameter_list|(
name|ImportFormatPreferences
name|importFormatPreferences
parameter_list|)
block|{
name|ArrayList
argument_list|<
name|IdFetcher
argument_list|>
name|list
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|CrossRef
argument_list|()
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|ArXiv
argument_list|(
name|importFormatPreferences
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|sort
argument_list|(
name|Comparator
operator|.
name|comparing
argument_list|(
name|WebFetcher
operator|::
name|getName
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|list
return|;
block|}
DECL|method|getFullTextFetchers (ImportFormatPreferences importFormatPreferences)
specifier|public
specifier|static
name|List
argument_list|<
name|FulltextFetcher
argument_list|>
name|getFullTextFetchers
parameter_list|(
name|ImportFormatPreferences
name|importFormatPreferences
parameter_list|)
block|{
name|List
argument_list|<
name|FulltextFetcher
argument_list|>
name|fetchers
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
comment|// Original
name|fetchers
operator|.
name|add
argument_list|(
operator|new
name|DoiResolution
argument_list|()
argument_list|)
expr_stmt|;
comment|// Publishers
name|fetchers
operator|.
name|add
argument_list|(
operator|new
name|ScienceDirect
argument_list|()
argument_list|)
expr_stmt|;
name|fetchers
operator|.
name|add
argument_list|(
operator|new
name|SpringerLink
argument_list|()
argument_list|)
expr_stmt|;
name|fetchers
operator|.
name|add
argument_list|(
operator|new
name|ACS
argument_list|()
argument_list|)
expr_stmt|;
name|fetchers
operator|.
name|add
argument_list|(
operator|new
name|ArXiv
argument_list|(
name|importFormatPreferences
argument_list|)
argument_list|)
expr_stmt|;
name|fetchers
operator|.
name|add
argument_list|(
operator|new
name|IEEE
argument_list|(
name|importFormatPreferences
argument_list|)
argument_list|)
expr_stmt|;
comment|// Meta search
name|fetchers
operator|.
name|add
argument_list|(
operator|new
name|GoogleScholar
argument_list|(
name|importFormatPreferences
argument_list|)
argument_list|)
expr_stmt|;
name|fetchers
operator|.
name|add
argument_list|(
operator|new
name|OpenAccessDoi
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|fetchers
return|;
block|}
block|}
end_class

end_unit

