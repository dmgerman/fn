begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.push
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|push
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
name|javax
operator|.
name|swing
operator|.
name|JPanel
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
name|icon
operator|.
name|JabRefIcon
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

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|metadata
operator|.
name|MetaData
import|;
end_import

begin_comment
comment|/**  * Class that defines interaction with an external application in the form of "pushing" selected entries to it.  */
end_comment

begin_interface
DECL|interface|PushToApplication
specifier|public
interface|interface
name|PushToApplication
block|{
DECL|method|getName ()
name|String
name|getName
parameter_list|()
function_decl|;
DECL|method|getApplicationName ()
name|String
name|getApplicationName
parameter_list|()
function_decl|;
DECL|method|getTooltip ()
name|String
name|getTooltip
parameter_list|()
function_decl|;
DECL|method|getIcon ()
name|JabRefIcon
name|getIcon
parameter_list|()
function_decl|;
comment|/**      * The actual operation. This method will not be called on the event dispatch thread, so it should not do GUI      * operations without utilizing invokeLater().      *      * @param database      * @param entries      * @param metaData      */
DECL|method|pushEntries (BibDatabase database, List<BibEntry> entries, String keyString, MetaData metaData)
name|void
name|pushEntries
parameter_list|(
name|BibDatabase
name|database
parameter_list|,
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|,
name|String
name|keyString
parameter_list|,
name|MetaData
name|metaData
parameter_list|)
function_decl|;
comment|/**      * Reporting etc., this method is called on the event dispatch thread after pushEntries() returns.      */
DECL|method|operationCompleted (BasePanel panel)
name|void
name|operationCompleted
parameter_list|(
name|BasePanel
name|panel
parameter_list|)
function_decl|;
comment|/**      * Check whether this operation requires BibTeX keys to be set for the entries. If true is returned an error message      * will be displayed if keys are missing.      *      * @return true if BibTeX keys are required for this operation.      */
DECL|method|requiresBibtexKeys ()
name|boolean
name|requiresBibtexKeys
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

