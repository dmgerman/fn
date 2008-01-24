begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.plugin
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|plugin
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
name|JabRefFrame
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
name|SidePaneComponent
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
name|SidePaneManager
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
comment|/**  * Created by IntelliJ IDEA.  * User: alver  * Date: Nov 26, 2007  * Time: 5:44:16 PM  * To change this template use File | Settings | File Templates.  */
end_comment

begin_interface
DECL|interface|SidePanePlugin
specifier|public
interface|interface
name|SidePanePlugin
block|{
DECL|method|init (JabRefFrame frame, SidePaneManager manager)
specifier|public
name|void
name|init
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|SidePaneManager
name|manager
parameter_list|)
function_decl|;
DECL|method|getSidePaneComponent ()
specifier|public
name|SidePaneComponent
name|getSidePaneComponent
parameter_list|()
function_decl|;
DECL|method|getMenuItem ()
specifier|public
name|JMenuItem
name|getMenuItem
parameter_list|()
function_decl|;
DECL|method|getShortcutKey ()
specifier|public
name|String
name|getShortcutKey
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

