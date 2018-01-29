begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.pdf
package|package
name|org
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
name|nio
operator|.
name|file
operator|.
name|Path
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
name|Map
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
name|database
operator|.
name|BibDatabaseContext
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
name|pdf
operator|.
name|FileAnnotation
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|common
operator|.
name|cache
operator|.
name|CacheBuilder
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|common
operator|.
name|cache
operator|.
name|CacheLoader
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|common
operator|.
name|cache
operator|.
name|LoadingCache
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

begin_class
DECL|class|FileAnnotationCache
specifier|public
class|class
name|FileAnnotationCache
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
name|FileAnnotation
operator|.
name|class
argument_list|)
decl_stmt|;
comment|//cache size in entries
DECL|field|CACHE_SIZE
specifier|private
specifier|final
specifier|static
name|int
name|CACHE_SIZE
init|=
literal|10
decl_stmt|;
comment|//the inner list holds the annotations per file, the outer collection maps this to a BibEntry.
DECL|field|annotationCache
specifier|private
name|LoadingCache
argument_list|<
name|BibEntry
argument_list|,
name|Map
argument_list|<
name|Path
argument_list|,
name|List
argument_list|<
name|FileAnnotation
argument_list|>
argument_list|>
argument_list|>
name|annotationCache
decl_stmt|;
comment|/**      * Creates an empty fil annotation cache. Required to allow the annotation cache to be injected into views without      * hitting the bug https://github.com/AdamBien/afterburner.fx/issues/71 .      */
DECL|method|FileAnnotationCache ()
specifier|public
name|FileAnnotationCache
parameter_list|()
block|{      }
DECL|method|FileAnnotationCache (BibDatabaseContext context)
specifier|public
name|FileAnnotationCache
parameter_list|(
name|BibDatabaseContext
name|context
parameter_list|)
block|{
name|annotationCache
operator|=
name|CacheBuilder
operator|.
name|newBuilder
argument_list|()
operator|.
name|maximumSize
argument_list|(
name|CACHE_SIZE
argument_list|)
operator|.
name|build
argument_list|(
operator|new
name|CacheLoader
argument_list|<
name|BibEntry
argument_list|,
name|Map
argument_list|<
name|Path
argument_list|,
name|List
argument_list|<
name|FileAnnotation
argument_list|>
argument_list|>
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|Map
argument_list|<
name|Path
argument_list|,
name|List
argument_list|<
name|FileAnnotation
argument_list|>
argument_list|>
name|load
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
throws|throws
name|Exception
block|{
return|return
operator|new
name|EntryAnnotationImporter
argument_list|(
name|entry
argument_list|)
operator|.
name|importAnnotationsFromFiles
argument_list|(
name|context
argument_list|)
return|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
comment|/**      * Note that entry becomes the most recent entry in the cache      *      * @param entry entry for which to get the annotations      * @return Map containing a list of annotations in a list for each file      */
DECL|method|getFromCache (BibEntry entry)
specifier|public
name|Map
argument_list|<
name|Path
argument_list|,
name|List
argument_list|<
name|FileAnnotation
argument_list|>
argument_list|>
name|getFromCache
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
name|LOGGER
operator|.
name|debug
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Loading Bibentry '%s' from cache."
argument_list|,
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
operator|.
name|orElse
argument_list|(
name|entry
operator|.
name|getId
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|annotationCache
operator|.
name|getUnchecked
argument_list|(
name|entry
argument_list|)
return|;
block|}
DECL|method|remove (BibEntry entry)
specifier|public
name|void
name|remove
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
name|LOGGER
operator|.
name|debug
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Deleted Bibentry '%s' from cache."
argument_list|,
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
operator|.
name|orElse
argument_list|(
name|entry
operator|.
name|getId
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|annotationCache
operator|.
name|invalidate
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

