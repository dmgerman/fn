begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.entry
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
package|;
end_package

begin_interface
DECL|interface|EntryType
specifier|public
interface|interface
name|EntryType
block|{
comment|/**      * Returns the tag name of the entry type.      *      * @return tag name of the entry type.      */
DECL|method|getName ()
name|String
name|getName
parameter_list|()
function_decl|;
DECL|method|getDisplayName ()
name|String
name|getDisplayName
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

