begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|com.incors.plaf.kunststoff
package|package
name|com
operator|.
name|incors
operator|.
name|plaf
operator|.
name|kunststoff
package|;
end_package

begin_comment
comment|/*  * This code was developed by INCORS GmbH (www.incors.com)  * based on a contribution by Timo Haberkern.  * It is published under the terms of the Lesser GNU Public License.  */
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
name|plaf
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
name|plaf
operator|.
name|basic
operator|.
name|*
import|;
end_import

begin_class
DECL|class|KunststoffTreeUI
specifier|public
class|class
name|KunststoffTreeUI
extends|extends
name|BasicTreeUI
block|{
DECL|field|m_iconExpanded
specifier|protected
specifier|static
name|ImageIcon
name|m_iconExpanded
decl_stmt|;
DECL|field|m_iconCollapsed
specifier|protected
specifier|static
name|ImageIcon
name|m_iconCollapsed
decl_stmt|;
DECL|method|KunststoffTreeUI (JComponent tree)
specifier|public
name|KunststoffTreeUI
parameter_list|(
name|JComponent
name|tree
parameter_list|)
block|{
try|try
block|{
name|m_iconExpanded
operator|=
operator|new
name|ImageIcon
argument_list|(
name|getClass
argument_list|()
operator|.
name|getResource
argument_list|(
literal|"icons/treeex.gif"
argument_list|)
argument_list|)
expr_stmt|;
name|m_iconCollapsed
operator|=
operator|new
name|ImageIcon
argument_list|(
name|getClass
argument_list|()
operator|.
name|getResource
argument_list|(
literal|"icons/treecol.gif"
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|createUI (JComponent tree)
specifier|public
specifier|static
name|ComponentUI
name|createUI
parameter_list|(
name|JComponent
name|tree
parameter_list|)
block|{
return|return
operator|new
name|KunststoffTreeUI
argument_list|(
name|tree
argument_list|)
return|;
block|}
comment|// This method replaces the metal expand-/collaps-icons with some nicer ones.
DECL|method|paintExpandControl (Graphics g, Rectangle clipBounds, Insets insets, Rectangle bounds, TreePath path, int row, boolean isExpanded, boolean hasBeenExpanded, boolean isLeaf)
specifier|protected
name|void
name|paintExpandControl
parameter_list|(
name|Graphics
name|g
parameter_list|,
name|Rectangle
name|clipBounds
parameter_list|,
name|Insets
name|insets
parameter_list|,
name|Rectangle
name|bounds
parameter_list|,
name|TreePath
name|path
parameter_list|,
name|int
name|row
parameter_list|,
name|boolean
name|isExpanded
parameter_list|,
name|boolean
name|hasBeenExpanded
parameter_list|,
name|boolean
name|isLeaf
parameter_list|)
block|{
comment|//super.paintExpandControl(g, clipBounds, insets, bounds, path, row, isExpanded, hasBeenExpanded, isLeaf);
if|if
condition|(
name|isExpanded
operator|==
literal|true
condition|)
block|{
if|if
condition|(
literal|null
operator|!=
name|m_iconExpanded
condition|)
block|{
comment|//g.drawImage(m_iconExpanded.getImage(), (int)bounds.x-15, (int)bounds.y+5, null);
name|g
operator|.
name|drawImage
argument_list|(
name|m_iconExpanded
operator|.
name|getImage
argument_list|()
argument_list|,
operator|(
name|int
operator|)
name|bounds
operator|.
name|x
operator|-
literal|17
argument_list|,
operator|(
name|int
operator|)
name|bounds
operator|.
name|y
operator|+
literal|4
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
if|if
condition|(
literal|null
operator|!=
name|m_iconCollapsed
condition|)
block|{
comment|//g.drawImage(m_iconCollapsed.getImage(), (int)bounds.x-15, (int)bounds.y+5, null);
name|g
operator|.
name|drawImage
argument_list|(
name|m_iconCollapsed
operator|.
name|getImage
argument_list|()
argument_list|,
operator|(
name|int
operator|)
name|bounds
operator|.
name|x
operator|-
literal|17
argument_list|,
operator|(
name|int
operator|)
name|bounds
operator|.
name|y
operator|+
literal|4
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

