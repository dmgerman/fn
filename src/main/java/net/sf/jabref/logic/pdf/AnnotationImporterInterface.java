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
name|pdf
operator|.
name|FileAnnotation
import|;
end_import

begin_interface
DECL|interface|AnnotationImporterInterface
specifier|public
interface|interface
name|AnnotationImporterInterface
block|{
DECL|method|importAnnotations (final String path, final BibDatabaseContext context)
name|List
argument_list|<
name|FileAnnotation
argument_list|>
name|importAnnotations
parameter_list|(
specifier|final
name|String
name|path
parameter_list|,
specifier|final
name|BibDatabaseContext
name|context
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

