begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.external
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|external
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
name|BibtexEntry
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|BasePanel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|*
import|;
end_import

begin_comment
comment|/**  * Class that defines interaction with an external application in the form of  * "pushing" selected entries to it.  */
end_comment

begin_interface
DECL|interface|PushToApplication
specifier|public
interface|interface
name|PushToApplication
block|{
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
function_decl|;
DECL|method|getApplicationName ()
specifier|public
name|String
name|getApplicationName
parameter_list|()
function_decl|;
DECL|method|getTooltip ()
specifier|public
name|String
name|getTooltip
parameter_list|()
function_decl|;
DECL|method|getIcon ()
specifier|public
name|Icon
name|getIcon
parameter_list|()
function_decl|;
DECL|method|getKeyStrokeName ()
specifier|public
name|String
name|getKeyStrokeName
parameter_list|()
function_decl|;
comment|/**      * The actual operation. This method will not be called on the event dispatch      * thread, so it should not do GUI operations without utilizing invokeLater().      * @param entries      */
DECL|method|pushEntries (BibtexEntry[] entries, String keyString)
specifier|public
name|void
name|pushEntries
parameter_list|(
name|BibtexEntry
index|[]
name|entries
parameter_list|,
name|String
name|keyString
parameter_list|)
function_decl|;
comment|/**      * Reporting etc., this method is called on the event dispatch thread after      * pushEntries() returns.      */
DECL|method|operationCompleted (BasePanel panel)
specifier|public
name|void
name|operationCompleted
parameter_list|(
name|BasePanel
name|panel
parameter_list|)
function_decl|;
comment|/**      * Check whether this operation requires BibTeX keys to be set for the entries.      * If true is returned an error message will be displayed if keys are missing.      * @return true if BibTeX keys are required for this operation.      */
DECL|method|requiresBibtexKeys ()
specifier|public
name|boolean
name|requiresBibtexKeys
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

