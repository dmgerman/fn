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

begin_interface
DECL|interface|AnnotationImporter
specifier|public
interface|interface
name|AnnotationImporter
block|{
DECL|method|importAnnotations (final Path path)
name|List
argument_list|<
name|FileAnnotation
argument_list|>
name|importAnnotations
parameter_list|(
specifier|final
name|Path
name|path
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

