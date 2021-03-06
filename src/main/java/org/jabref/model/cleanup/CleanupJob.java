begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.cleanup
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|cleanup
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
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|FieldChange
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

begin_interface
annotation|@
name|FunctionalInterface
DECL|interface|CleanupJob
specifier|public
interface|interface
name|CleanupJob
block|{
comment|/**      * Cleanup the entry.      */
DECL|method|cleanup (BibEntry entry)
name|List
argument_list|<
name|FieldChange
argument_list|>
name|cleanup
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

