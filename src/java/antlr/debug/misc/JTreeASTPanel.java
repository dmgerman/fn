begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|antlr.debug.misc
package|package
name|antlr
operator|.
name|debug
operator|.
name|misc
package|;
end_package

begin_comment
comment|/* ANTLR Translator Generator  * Project led by Terence Parr at http://www.jGuru.com  * Software rights: http://www.antlr.org/license.html  *  * $Id$  */
end_comment

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|*
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

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|tree
operator|.
name|*
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|*
import|;
end_import

begin_class
DECL|class|JTreeASTPanel
specifier|public
class|class
name|JTreeASTPanel
extends|extends
name|JPanel
block|{
DECL|field|tree
name|JTree
name|tree
decl_stmt|;
DECL|method|JTreeASTPanel (TreeModel tm, TreeSelectionListener listener)
specifier|public
name|JTreeASTPanel
parameter_list|(
name|TreeModel
name|tm
parameter_list|,
name|TreeSelectionListener
name|listener
parameter_list|)
block|{
comment|// use a layout that will stretch tree to panel size
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
comment|// Create tree
name|tree
operator|=
operator|new
name|JTree
argument_list|(
name|tm
argument_list|)
expr_stmt|;
comment|// Change line style
name|tree
operator|.
name|putClientProperty
argument_list|(
literal|"JTree.lineStyle"
argument_list|,
literal|"Angled"
argument_list|)
expr_stmt|;
comment|// Add TreeSelectionListener
if|if
condition|(
name|listener
operator|!=
literal|null
condition|)
name|tree
operator|.
name|addTreeSelectionListener
argument_list|(
name|listener
argument_list|)
expr_stmt|;
comment|// Put tree in a scrollable pane's viewport
name|JScrollPane
name|sp
init|=
operator|new
name|JScrollPane
argument_list|()
decl_stmt|;
name|sp
operator|.
name|getViewport
argument_list|()
operator|.
name|add
argument_list|(
name|tree
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|sp
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

