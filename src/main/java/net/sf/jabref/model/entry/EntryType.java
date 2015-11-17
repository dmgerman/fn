begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.model.entry
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
package|;
end_package

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
name|BibtexDatabase
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

begin_interface
DECL|interface|EntryType
specifier|public
interface|interface
name|EntryType
extends|extends
name|Comparable
argument_list|<
name|BibtexEntryType
argument_list|>
block|{
DECL|method|getName ()
name|String
name|getName
parameter_list|()
function_decl|;
DECL|method|hasAllRequiredFields (BibtexEntry entry, BibtexDatabase database)
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexDatabase
name|database
parameter_list|)
function_decl|;
DECL|method|getOptionalFields ()
name|List
argument_list|<
name|String
argument_list|>
name|getOptionalFields
parameter_list|()
function_decl|;
DECL|method|getRequiredFields ()
name|List
argument_list|<
name|String
argument_list|>
name|getRequiredFields
parameter_list|()
function_decl|;
DECL|method|isRequired (String field)
name|boolean
name|isRequired
parameter_list|(
name|String
name|field
parameter_list|)
function_decl|;
DECL|method|isOptional (String field)
name|boolean
name|isOptional
parameter_list|(
name|String
name|field
parameter_list|)
function_decl|;
DECL|method|isVisibleAtNewEntryDialog ()
name|boolean
name|isVisibleAtNewEntryDialog
parameter_list|()
function_decl|;
DECL|method|getRequiredFieldsForCustomization ()
name|List
argument_list|<
name|String
argument_list|>
name|getRequiredFieldsForCustomization
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

