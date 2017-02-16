begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.importer
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
name|ArXiv
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
name|DoiFetcher
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
name|IsbnFetcher
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

begin_class
DECL|class|WebFetchers
specifier|public
class|class
name|WebFetchers
block|{
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
block|}
end_class

end_unit

