begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui
package|package
name|net
operator|.
name|sf
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
name|JScrollPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|SwingUtilities
import|;
end_import

begin_comment
comment|/**  *  FlowLayout subclass that fully supports wrapping of components.  */
end_comment

begin_class
DECL|class|WrapLayout
specifier|public
class|class
name|WrapLayout
extends|extends
name|FlowLayout
block|{
comment|/**      * Constructs a new<code>WrapLayout</code> with a left      * alignment and a default 5-unit horizontal and vertical gap.      */
DECL|method|WrapLayout ()
specifier|public
name|WrapLayout
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/**      * Constructs a new<code>FlowLayout</code> with the specified      * alignment and a default 5-unit horizontal and vertical gap.      * The value of the alignment argument must be one of      *<code>WrapLayout</code>,<code>WrapLayout</code>,      * or<code>WrapLayout</code>.      * @param align the alignment value      */
DECL|method|WrapLayout (int align)
specifier|public
name|WrapLayout
parameter_list|(
name|int
name|align
parameter_list|)
block|{
name|super
argument_list|(
name|align
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a new flow layout manager with the indicated alignment      * and the indicated horizontal and vertical gaps.      *<p>      * The value of the alignment argument must be one of      *<code>WrapLayout</code>,<code>WrapLayout</code>,      * or<code>WrapLayout</code>.      * @param align the alignment value      * @param hgap the horizontal gap between components      * @param vgap the vertical gap between components      */
DECL|method|WrapLayout (int align, int hgap, int vgap)
specifier|public
name|WrapLayout
parameter_list|(
name|int
name|align
parameter_list|,
name|int
name|hgap
parameter_list|,
name|int
name|vgap
parameter_list|)
block|{
name|super
argument_list|(
name|align
argument_list|,
name|hgap
argument_list|,
name|vgap
argument_list|)
expr_stmt|;
block|}
comment|/**      * Returns the preferred dimensions for this layout given the      *<i>visible</i> components in the specified target container.      * @param target the component which needs to be laid out      * @return the preferred dimensions to lay out the      * subcomponents of the specified container      */
annotation|@
name|Override
DECL|method|preferredLayoutSize (Container target)
specifier|public
name|Dimension
name|preferredLayoutSize
parameter_list|(
name|Container
name|target
parameter_list|)
block|{
return|return
name|layoutSize
argument_list|(
name|target
argument_list|,
literal|true
argument_list|)
return|;
block|}
comment|/**      * Returns the minimum dimensions needed to layout the<i>visible</i>      * components contained in the specified target container.      * @param target the component which needs to be laid out      * @return the minimum dimensions to lay out the      * subcomponents of the specified container      */
annotation|@
name|Override
DECL|method|minimumLayoutSize (Container target)
specifier|public
name|Dimension
name|minimumLayoutSize
parameter_list|(
name|Container
name|target
parameter_list|)
block|{
name|Dimension
name|minimum
init|=
name|layoutSize
argument_list|(
name|target
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|minimum
operator|.
name|width
operator|-=
operator|(
name|getHgap
argument_list|()
operator|+
literal|1
operator|)
expr_stmt|;
return|return
name|minimum
return|;
block|}
comment|/**      * Returns the minimum or preferred dimension needed to layout the target      * container.      *      * @param target target to get layout size for      * @param preferred should preferred size be calculated      * @return the dimension to layout the target container      */
DECL|method|layoutSize (Container target, boolean preferred)
specifier|private
name|Dimension
name|layoutSize
parameter_list|(
name|Container
name|target
parameter_list|,
name|boolean
name|preferred
parameter_list|)
block|{
synchronized|synchronized
init|(
name|target
operator|.
name|getTreeLock
argument_list|()
init|)
block|{
comment|//  Each row must fit with the width allocated to the containter.
comment|//  When the container width = 0, the preferred width of the container
comment|//  has not yet been calculated so lets ask for the maximum.
name|int
name|targetWidth
init|=
name|target
operator|.
name|getSize
argument_list|()
operator|.
name|width
decl_stmt|;
if|if
condition|(
name|targetWidth
operator|==
literal|0
condition|)
name|targetWidth
operator|=
name|Integer
operator|.
name|MAX_VALUE
expr_stmt|;
name|int
name|hgap
init|=
name|getHgap
argument_list|()
decl_stmt|;
name|int
name|vgap
init|=
name|getVgap
argument_list|()
decl_stmt|;
name|Insets
name|insets
init|=
name|target
operator|.
name|getInsets
argument_list|()
decl_stmt|;
name|int
name|horizontalInsetsAndGap
init|=
name|insets
operator|.
name|left
operator|+
name|insets
operator|.
name|right
operator|+
operator|(
name|hgap
operator|*
literal|2
operator|)
decl_stmt|;
name|int
name|maxWidth
init|=
name|targetWidth
operator|-
name|horizontalInsetsAndGap
decl_stmt|;
comment|//  Fit components into the allowed width
name|Dimension
name|dim
init|=
operator|new
name|Dimension
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|)
decl_stmt|;
name|int
name|rowWidth
init|=
literal|0
decl_stmt|;
name|int
name|rowHeight
init|=
literal|0
decl_stmt|;
name|int
name|nmembers
init|=
name|target
operator|.
name|getComponentCount
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|nmembers
condition|;
name|i
operator|++
control|)
block|{
name|Component
name|m
init|=
name|target
operator|.
name|getComponent
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|m
operator|.
name|isVisible
argument_list|()
condition|)
block|{
name|Dimension
name|d
init|=
name|preferred
condition|?
name|m
operator|.
name|getPreferredSize
argument_list|()
else|:
name|m
operator|.
name|getMinimumSize
argument_list|()
decl_stmt|;
comment|//  Can't add the component to current row. Start a new row.
if|if
condition|(
name|rowWidth
operator|+
name|d
operator|.
name|width
operator|>
name|maxWidth
condition|)
block|{
name|addRow
argument_list|(
name|dim
argument_list|,
name|rowWidth
argument_list|,
name|rowHeight
argument_list|)
expr_stmt|;
name|rowWidth
operator|=
literal|0
expr_stmt|;
name|rowHeight
operator|=
literal|0
expr_stmt|;
block|}
comment|//  Add a horizontal gap for all components after the first
if|if
condition|(
name|rowWidth
operator|!=
literal|0
condition|)
block|{
name|rowWidth
operator|+=
name|hgap
expr_stmt|;
block|}
name|rowWidth
operator|+=
name|d
operator|.
name|width
expr_stmt|;
name|rowHeight
operator|=
name|Math
operator|.
name|max
argument_list|(
name|rowHeight
argument_list|,
name|d
operator|.
name|height
argument_list|)
expr_stmt|;
block|}
block|}
name|addRow
argument_list|(
name|dim
argument_list|,
name|rowWidth
argument_list|,
name|rowHeight
argument_list|)
expr_stmt|;
name|dim
operator|.
name|width
operator|+=
name|horizontalInsetsAndGap
expr_stmt|;
name|dim
operator|.
name|height
operator|+=
name|insets
operator|.
name|top
operator|+
name|insets
operator|.
name|bottom
operator|+
name|vgap
operator|*
literal|2
expr_stmt|;
comment|//	When using a scroll pane or the DecoratedLookAndFeel we need to
comment|//  make sure the preferred size is less than the size of the
comment|//  target containter so shrinking the container size works
comment|//  correctly. Removing the horizontal gap is an easy way to do this.
name|Container
name|scrollPane
init|=
name|SwingUtilities
operator|.
name|getAncestorOfClass
argument_list|(
name|JScrollPane
operator|.
name|class
argument_list|,
name|target
argument_list|)
decl_stmt|;
if|if
condition|(
name|scrollPane
operator|!=
literal|null
condition|)
block|{
name|dim
operator|.
name|width
operator|-=
operator|(
name|hgap
operator|+
literal|1
operator|)
expr_stmt|;
block|}
return|return
name|dim
return|;
block|}
block|}
comment|/*      *  A new row has been completed. Use the dimensions of this row      *  to update the preferred size for the container.      *      *  @param dim update the width and height when appropriate      *  @param rowWidth the width of the row to add      *  @param rowHeight the height of the row to add      */
DECL|method|addRow (Dimension dim, int rowWidth, int rowHeight)
specifier|private
name|void
name|addRow
parameter_list|(
name|Dimension
name|dim
parameter_list|,
name|int
name|rowWidth
parameter_list|,
name|int
name|rowHeight
parameter_list|)
block|{
name|dim
operator|.
name|width
operator|=
name|Math
operator|.
name|max
argument_list|(
name|dim
operator|.
name|width
argument_list|,
name|rowWidth
argument_list|)
expr_stmt|;
if|if
condition|(
name|dim
operator|.
name|height
operator|>
literal|0
condition|)
block|{
name|dim
operator|.
name|height
operator|+=
name|getVgap
argument_list|()
expr_stmt|;
block|}
name|dim
operator|.
name|height
operator|+=
name|rowHeight
expr_stmt|;
block|}
block|}
end_class

end_unit

