begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.preferences
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|preferences
package|;
end_package

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|text
operator|.
name|Font
import|;
end_import

begin_comment
comment|/**  *   This class is used to save the font size of all the controls in the preferences dialog  */
end_comment

begin_class
DECL|class|FontSize
specifier|public
class|class
name|FontSize
block|{
DECL|field|bigFont
specifier|public
specifier|static
name|Font
name|bigFont
init|=
operator|new
name|Font
argument_list|(
literal|14
argument_list|)
decl_stmt|;
block|}
end_class

end_unit

