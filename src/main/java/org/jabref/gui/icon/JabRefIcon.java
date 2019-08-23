begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.icon
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|icon
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
name|javafx
operator|.
name|scene
operator|.
name|paint
operator|.
name|Color
import|;
end_import

begin_import
import|import
name|de
operator|.
name|jensd
operator|.
name|fx
operator|.
name|glyphs
operator|.
name|GlyphIcons
import|;
end_import

begin_interface
DECL|interface|JabRefIcon
specifier|public
interface|interface
name|JabRefIcon
extends|extends
name|GlyphIcons
block|{
DECL|method|getGraphicNode ()
name|Node
name|getGraphicNode
parameter_list|()
function_decl|;
annotation|@
name|Override
DECL|method|name ()
name|String
name|name
parameter_list|()
function_decl|;
DECL|method|withColor (Color color)
name|JabRefIcon
name|withColor
parameter_list|(
name|Color
name|color
parameter_list|)
function_decl|;
DECL|method|disabled ()
name|JabRefIcon
name|disabled
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

