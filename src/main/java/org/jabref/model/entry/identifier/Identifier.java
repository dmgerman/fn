begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.entry.identifier
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|identifier
package|;
end_package

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URI
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
name|model
operator|.
name|entry
operator|.
name|field
operator|.
name|Field
import|;
end_import

begin_interface
DECL|interface|Identifier
specifier|public
interface|interface
name|Identifier
block|{
DECL|method|getDefaultField ()
name|Field
name|getDefaultField
parameter_list|()
function_decl|;
DECL|method|getNormalized ()
name|String
name|getNormalized
parameter_list|()
function_decl|;
DECL|method|getExternalURI ()
name|Optional
argument_list|<
name|URI
argument_list|>
name|getExternalURI
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

