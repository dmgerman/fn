begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.documentviewer
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|documentviewer
package|;
end_package

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|image
operator|.
name|Image
import|;
end_import

begin_comment
comment|/**  * Represents the view model for a page in the document viewer.  */
end_comment

begin_class
DECL|class|DocumentPageViewModel
specifier|public
specifier|abstract
class|class
name|DocumentPageViewModel
block|{
comment|/**      * Renders this page and returns an image representation of itself.      * @param width      * @param height      */
DECL|method|render (int width, int height)
specifier|public
specifier|abstract
name|Image
name|render
parameter_list|(
name|int
name|width
parameter_list|,
name|int
name|height
parameter_list|)
function_decl|;
comment|/**      * Get the page number of the current page in the document.      */
DECL|method|getPageNumber ()
specifier|public
specifier|abstract
name|int
name|getPageNumber
parameter_list|()
function_decl|;
comment|/**      * Calculates the aspect ratio (width / height) of the page.      */
DECL|method|getAspectRatio ()
specifier|public
specifier|abstract
name|double
name|getAspectRatio
parameter_list|()
function_decl|;
block|}
end_class

end_unit

