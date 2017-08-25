begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.fieldeditors
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|fieldeditors
package|;
end_package

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|Parent
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
DECL|interface|FieldEditorFX
specifier|public
interface|interface
name|FieldEditorFX
block|{
DECL|method|bindToEntry (BibEntry entry)
name|void
name|bindToEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
function_decl|;
DECL|method|getNode ()
name|Parent
name|getNode
parameter_list|()
function_decl|;
DECL|method|requestFocus ()
specifier|default
name|void
name|requestFocus
parameter_list|()
block|{
name|getNode
argument_list|()
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
block|}
comment|/**      * Returns relative size of the field editor in terms of display space.      *      * A value of 1 means that the editor gets exactly as much space as all other regular editors.      * A value of 2 means that the editor gets twice as much space as regular editors.      *      * @return the relative weight of the editor in terms of display space      */
DECL|method|getWeight ()
specifier|default
name|double
name|getWeight
parameter_list|()
block|{
return|return
literal|1
return|;
block|}
block|}
end_interface

end_unit

