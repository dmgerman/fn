begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.importer.actions
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|importer
operator|.
name|actions
package|;
end_package

begin_import
import|import
name|org
operator|.
name|jabref
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
name|logic
operator|.
name|importer
operator|.
name|ParserResult
import|;
end_import

begin_comment
comment|/**  * This interface defines potential actions that may need to be taken after  * opening a BIB file into JabRef. This can for instance be file upgrade actions  * that should be offered due to new features in JabRef, and may depend on e.g.  * which JabRef version the file was last written by.  *  * This interface is introduced in an attempt to add such functionality in a  * flexible manner.  */
end_comment

begin_interface
DECL|interface|GUIPostOpenAction
specifier|public
interface|interface
name|GUIPostOpenAction
block|{
comment|/**      * This method is queried in order to find out whether the action needs to be      * performed or not.      * @param pr The result of the BIB parse operation.      * @return true if the action should be called, false otherwise.      */
DECL|method|isActionNecessary (ParserResult pr)
name|boolean
name|isActionNecessary
parameter_list|(
name|ParserResult
name|pr
parameter_list|)
function_decl|;
comment|/**      * This method is called after the new database has been added to the GUI, if      * the isActionNecessary() method returned true.      *      * Note: if several such methods need to be called sequentially, it is      *       important that all implementations of this method do not return      *       until the operation is finished.      *      * @param panel The BasePanel where the database is shown.      * @param pr The result of the BIB parse operation.      * @param dialogService      */
DECL|method|performAction (BasePanel panel, ParserResult pr)
name|void
name|performAction
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|ParserResult
name|pr
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

