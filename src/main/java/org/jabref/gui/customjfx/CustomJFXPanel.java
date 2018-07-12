begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.customjfx
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|customjfx
package|;
end_package

begin_import
import|import
name|javafx
operator|.
name|embed
operator|.
name|swing
operator|.
name|JFXPanel
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|Scene
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|Globals
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
name|util
operator|.
name|DefaultTaskExecutor
import|;
end_import

begin_comment
comment|/**  * TODO: Remove as soon as possible  */
end_comment

begin_class
DECL|class|CustomJFXPanel
specifier|public
class|class
name|CustomJFXPanel
block|{
DECL|method|wrap (Scene scene)
specifier|public
specifier|static
name|JFXPanel
name|wrap
parameter_list|(
name|Scene
name|scene
parameter_list|)
block|{
name|JFXPanel
name|container
init|=
operator|new
name|JFXPanel
argument_list|()
decl_stmt|;
name|Globals
operator|.
name|getThemeLoader
argument_list|()
operator|.
name|installBaseCss
argument_list|(
name|scene
argument_list|)
expr_stmt|;
name|DefaultTaskExecutor
operator|.
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
name|container
operator|.
name|setScene
argument_list|(
name|scene
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|container
return|;
block|}
block|}
end_class

end_unit

