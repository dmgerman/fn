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
name|zbMATH
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
DECL|method|getIdBasedFetcherForField (String field, ImportFormatPreferences preferences)
specifier|public
specifier|static
name|Optional
argument_list|<
name|IdBasedFetcher
argument_list|>
name|getIdBasedFetcherForField
parameter_list|(
name|String
name|field
parameter_list|,
name|ImportFormatPreferences
name|preferences
parameter_list|)
block|{
name|IdBasedFetcher
name|fetcher
decl_stmt|;
switch|switch
condition|(
name|field
condition|)
block|{
case|case
name|FieldName
operator|.
name|DOI
case|:
name|fetcher
operator|=
operator|new
name|DoiFetcher
argument_list|(
name|preferences
argument_list|)
expr_stmt|;
break|break;
case|case
name|FieldName
operator|.
name|ISBN
case|:
name|fetcher
operator|=
operator|new
name|IsbnFetcher
argument_list|(
name|preferences
argument_list|)
expr_stmt|;
break|break;
case|case
name|FieldName
operator|.
name|EPRINT
case|:
name|fetcher
operator|=
operator|new
name|ArXiv
argument_list|(
name|preferences
argument_list|)
expr_stmt|;
break|break;
default|default:
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
DECL|method|getIdFetcherForField (String fieldName)
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
name|String
name|fieldName
parameter_list|)
block|{
switch|switch
condition|(
name|fieldName
condition|)
block|{
case|case
name|FieldName
operator|.
name|DOI
case|:
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
name|zbMATH
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
block|}
end_class

end_unit

