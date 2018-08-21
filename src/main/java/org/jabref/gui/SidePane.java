begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|layout
operator|.
name|BorderPane
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|layout
operator|.
name|VBox
import|;
end_import

begin_comment
comment|/**  * The side pane is displayed at the left side of JabRef and shows instances of {@link SidePaneComponent}.  */
end_comment

begin_class
DECL|class|SidePane
specifier|public
class|class
name|SidePane
extends|extends
name|BorderPane
block|{
DECL|field|mainPanel
specifier|private
specifier|final
name|VBox
name|mainPanel
init|=
operator|new
name|VBox
argument_list|()
decl_stmt|;
DECL|method|SidePane ()
specifier|public
name|SidePane
parameter_list|()
block|{
name|setId
argument_list|(
literal|"sidePane"
argument_list|)
expr_stmt|;
name|setCenter
argument_list|(
name|mainPanel
argument_list|)
expr_stmt|;
block|}
DECL|method|setComponents (Collection<SidePaneComponent> components)
specifier|public
name|void
name|setComponents
parameter_list|(
name|Collection
argument_list|<
name|SidePaneComponent
argument_list|>
name|components
parameter_list|)
block|{
name|mainPanel
operator|.
name|getChildren
argument_list|()
operator|.
name|clear
argument_list|()
expr_stmt|;
for|for
control|(
name|SidePaneComponent
name|component
range|:
name|components
control|)
block|{
name|BorderPane
name|node
init|=
operator|new
name|BorderPane
argument_list|()
decl_stmt|;
name|node
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"sidePaneComponent"
argument_list|)
expr_stmt|;
name|node
operator|.
name|setTop
argument_list|(
name|component
operator|.
name|getHeader
argument_list|()
argument_list|)
expr_stmt|;
name|node
operator|.
name|setCenter
argument_list|(
name|component
operator|.
name|getContentPane
argument_list|()
argument_list|)
expr_stmt|;
name|mainPanel
operator|.
name|getChildren
argument_list|()
operator|.
name|add
argument_list|(
name|node
argument_list|)
expr_stmt|;
name|VBox
operator|.
name|setVgrow
argument_list|(
name|node
argument_list|,
name|component
operator|.
name|getResizePolicy
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

