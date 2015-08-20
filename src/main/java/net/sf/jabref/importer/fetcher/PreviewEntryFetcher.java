begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.importer.fetcher
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
operator|.
name|fetcher
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
name|gui
operator|.
name|FetcherPreviewDialog
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
name|importer
operator|.
name|ImportInspector
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
name|importer
operator|.
name|OutputPrinter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_comment
comment|/**  *  */
end_comment

begin_interface
DECL|interface|PreviewEntryFetcher
interface|interface
name|PreviewEntryFetcher
extends|extends
name|EntryFetcher
block|{
DECL|method|processQueryGetPreview (String query, FetcherPreviewDialog preview, OutputPrinter status)
name|boolean
name|processQueryGetPreview
parameter_list|(
name|String
name|query
parameter_list|,
name|FetcherPreviewDialog
name|preview
parameter_list|,
name|OutputPrinter
name|status
parameter_list|)
function_decl|;
DECL|method|getEntries (Map<String, Boolean> selection, ImportInspector inspector)
name|void
name|getEntries
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|Boolean
argument_list|>
name|selection
parameter_list|,
name|ImportInspector
name|inspector
parameter_list|)
function_decl|;
comment|/**      * The number of entries a user can select for download without getting a warning message.      * @return the warning limit      */
DECL|method|getWarningLimit ()
name|int
name|getWarningLimit
parameter_list|()
function_decl|;
comment|/**      * The preferred table row height for the previews.      * @return the preferred height      */
DECL|method|getPreferredPreviewHeight ()
name|int
name|getPreferredPreviewHeight
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

