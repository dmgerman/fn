begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.collab
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|collab
package|;
end_package

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|Node
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|undo
operator|.
name|NamedCompound
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

begin_class
DECL|class|DatabaseChangeViewModel
specifier|abstract
class|class
name|DatabaseChangeViewModel
block|{
DECL|field|name
specifier|protected
name|String
name|name
decl_stmt|;
DECL|field|accepted
specifier|private
name|boolean
name|accepted
init|=
literal|true
decl_stmt|;
DECL|method|DatabaseChangeViewModel ()
name|DatabaseChangeViewModel
parameter_list|()
block|{
name|name
operator|=
literal|""
expr_stmt|;
block|}
DECL|method|DatabaseChangeViewModel (String name)
name|DatabaseChangeViewModel
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|name
return|;
block|}
DECL|method|isAccepted ()
specifier|public
name|boolean
name|isAccepted
parameter_list|()
block|{
return|return
name|accepted
return|;
block|}
DECL|method|setAccepted (boolean a)
specifier|public
name|void
name|setAccepted
parameter_list|(
name|boolean
name|a
parameter_list|)
block|{
name|accepted
operator|=
name|a
expr_stmt|;
block|}
comment|/**      * This method returns a JComponent detailing the nature of the change.      * @return JComponent      */
DECL|method|description ()
specifier|public
specifier|abstract
name|Node
name|description
parameter_list|()
function_decl|;
comment|/**      * Performs the change. This method is responsible for adding a proper undo edit to      * the NamedCompound, so the change can be undone.      * @param database the database that should be modified accordingly.      * @param undoEdit NamedCompound The compound to hold the undo edits.      */
DECL|method|makeChange (BibDatabaseContext database, NamedCompound undoEdit)
specifier|public
specifier|abstract
name|void
name|makeChange
parameter_list|(
name|BibDatabaseContext
name|database
parameter_list|,
name|NamedCompound
name|undoEdit
parameter_list|)
function_decl|;
block|}
end_class

end_unit

