begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.pdf
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|pdf
package|;
end_package

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
name|Map
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
name|model
operator|.
name|database
operator|.
name|BibDatabaseContext
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
name|pdf
operator|.
name|FileAnnotation
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
name|collections4
operator|.
name|map
operator|.
name|LRUMap
import|;
end_import

begin_class
DECL|class|FileAnnotationCache
specifier|public
class|class
name|FileAnnotationCache
block|{
comment|//cache size in entries
DECL|field|CACHE_SIZE
specifier|final
specifier|static
name|int
name|CACHE_SIZE
init|=
literal|10
decl_stmt|;
comment|//the inner list holds the annotations of a file, the outer list holds such a list for every file attached to an entry
DECL|field|annotationCache
name|LRUMap
argument_list|<
name|BibEntry
argument_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|FileAnnotation
argument_list|>
argument_list|>
argument_list|>
name|annotationCache
decl_stmt|;
DECL|field|bibDatabaseContext
name|BibDatabaseContext
name|bibDatabaseContext
decl_stmt|;
DECL|method|FileAnnotationCache (BibDatabaseContext bibDatabaseContext)
specifier|public
name|FileAnnotationCache
parameter_list|(
name|BibDatabaseContext
name|bibDatabaseContext
parameter_list|)
block|{
name|this
operator|.
name|bibDatabaseContext
operator|=
name|bibDatabaseContext
expr_stmt|;
name|annotationCache
operator|=
operator|new
name|LRUMap
argument_list|<>
argument_list|(
name|CACHE_SIZE
argument_list|)
expr_stmt|;
block|}
DECL|method|addToCache (BibEntry entry, final Map<String, List<FileAnnotation>> annotations)
specifier|public
name|void
name|addToCache
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|FileAnnotation
argument_list|>
argument_list|>
name|annotations
parameter_list|)
block|{
name|annotationCache
operator|.
name|put
argument_list|(
name|entry
argument_list|,
name|annotations
argument_list|)
expr_stmt|;
block|}
comment|/**      * Note that entry becomes the most recent entry in the cache      * @param entry entry for which to get the annotations      * @return Map containing a list of annotations in a list for each file      */
DECL|method|getFromCache (Optional<BibEntry> entry)
specifier|public
name|Optional
argument_list|<
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|FileAnnotation
argument_list|>
argument_list|>
argument_list|>
name|getFromCache
parameter_list|(
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|entry
parameter_list|)
block|{
name|Optional
argument_list|<
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|FileAnnotation
argument_list|>
argument_list|>
argument_list|>
name|cachedAnnotations
init|=
name|Optional
operator|.
name|empty
argument_list|()
decl_stmt|;
if|if
condition|(
name|entry
operator|.
name|isPresent
argument_list|()
operator|&&
name|annotationCache
operator|.
name|containsKey
argument_list|(
name|entry
operator|.
name|get
argument_list|()
argument_list|)
condition|)
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|annotationCache
operator|.
name|get
argument_list|(
name|entry
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
name|cachedAnnotations
return|;
block|}
block|}
block|}
end_class

end_unit
