begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.importer
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
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
name|help
operator|.
name|HelpFile
import|;
end_import

begin_comment
comment|/**  * Searches web resources for bibliographic information.  */
end_comment

begin_interface
DECL|interface|WebFetcher
specifier|public
interface|interface
name|WebFetcher
block|{
comment|/**      * Returns the localized name of this fetcher.      * The title can be used to display the fetcher in the menu and in the side pane.      *      * @return the localized name      */
DECL|method|getName ()
name|String
name|getName
parameter_list|()
function_decl|;
comment|/**      * Returns the help page for this fetcher.      *      * @return the {@link HelpFile} enum constant for the help page      */
DECL|method|getHelpPage ()
specifier|default
name|Optional
argument_list|<
name|HelpFile
argument_list|>
name|getHelpPage
parameter_list|()
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
comment|// no help page by default
block|}
block|}
end_interface

end_unit

