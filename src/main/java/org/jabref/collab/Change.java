begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.collab
package|package
name|org
operator|.
name|jabref
operator|.
name|collab
package|;
end_package

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JComponent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|tree
operator|.
name|DefaultMutableTreeNode
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
name|gui
operator|.
name|BasePanel
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
name|BibDatabase
import|;
end_import

begin_class
DECL|class|Change
specifier|abstract
class|class
name|Change
extends|extends
name|DefaultMutableTreeNode
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
DECL|method|Change ()
name|Change
parameter_list|()
block|{
name|name
operator|=
literal|""
expr_stmt|;
block|}
DECL|method|Change (String name)
name|Change
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
comment|/**      * This method is used to disable the "accept" box if the parent has been set to "not accepted".      * Thus the user can disable e.g. an entry change without having to disable all field changes.      * @return boolean false if the parent overrides by not being accepted.      */
DECL|method|isAcceptable ()
specifier|public
name|boolean
name|isAcceptable
parameter_list|()
block|{
if|if
condition|(
operator|(
name|getParent
argument_list|()
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|getParent
argument_list|()
operator|instanceof
name|Change
operator|)
condition|)
block|{
return|return
operator|(
operator|(
name|Change
operator|)
name|getParent
argument_list|()
operator|)
operator|.
name|isAccepted
argument_list|()
return|;
block|}
else|else
block|{
return|return
literal|true
return|;
block|}
block|}
comment|/**      * This method returns a JComponent detailing the nature of the change.      * @return JComponent      */
DECL|method|description ()
specifier|public
specifier|abstract
name|JComponent
name|description
parameter_list|()
function_decl|;
comment|/**      * Perform the change. This method is responsible for adding a proper undo edit to      * the NamedCompound, so the change can be undone.      * @param panel BasePanel The tab where the database lives.      * @param secondary BibDatabase The "tmp" database for which the change      *   should also be made.      * @param undoEdit NamedCompound The compound to hold the undo edits.      * @return true if all changes were made, false if not all were accepted.      */
DECL|method|makeChange (BasePanel panel, BibDatabase secondary, NamedCompound undoEdit)
specifier|public
specifier|abstract
name|boolean
name|makeChange
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|BibDatabase
name|secondary
parameter_list|,
name|NamedCompound
name|undoEdit
parameter_list|)
function_decl|;
block|}
end_class

end_unit

