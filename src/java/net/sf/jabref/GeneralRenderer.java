begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
package|;
end_package

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|table
operator|.
name|DefaultTableCellRenderer
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
name|java
operator|.
name|awt
operator|.
name|*
import|;
end_import

begin_comment
comment|/**  * Created by IntelliJ IDEA.  * User: alver  * Date: May 30, 2005  * Time: 9:43:45 PM  * To change this template use File | Settings | File Templates.  */
end_comment

begin_class
DECL|class|GeneralRenderer
specifier|public
class|class
name|GeneralRenderer
comment|/*extends JTable implements TableCellRenderer {*/
extends|extends
name|DefaultTableCellRenderer
block|{
DECL|field|table
specifier|private
name|JTable
name|table
decl_stmt|;
DECL|field|antialiasing
specifier|private
name|boolean
name|antialiasing
decl_stmt|;
DECL|method|GeneralRenderer (JTable entryTable, Color c, boolean antialiasing)
specifier|public
name|GeneralRenderer
parameter_list|(
name|JTable
name|entryTable
parameter_list|,
name|Color
name|c
parameter_list|,
name|boolean
name|antialiasing
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|this
operator|.
name|table
operator|=
name|entryTable
expr_stmt|;
name|this
operator|.
name|antialiasing
operator|=
name|antialiasing
expr_stmt|;
name|setBackground
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
DECL|method|GeneralRenderer (JTable table, Color c, Color fg, boolean antialiasing)
specifier|public
name|GeneralRenderer
parameter_list|(
name|JTable
name|table
parameter_list|,
name|Color
name|c
parameter_list|,
name|Color
name|fg
parameter_list|,
name|boolean
name|antialiasing
parameter_list|)
block|{
name|this
argument_list|(
name|table
argument_list|,
name|c
argument_list|,
name|antialiasing
argument_list|)
expr_stmt|;
comment|//setForeground(fg)????
block|}
DECL|method|firePropertyChange (String propertyName, boolean old, boolean newV)
specifier|public
name|void
name|firePropertyChange
parameter_list|(
name|String
name|propertyName
parameter_list|,
name|boolean
name|old
parameter_list|,
name|boolean
name|newV
parameter_list|)
block|{}
DECL|method|firePropertyChange (String propertyName, Object old, Object newV)
specifier|public
name|void
name|firePropertyChange
parameter_list|(
name|String
name|propertyName
parameter_list|,
name|Object
name|old
parameter_list|,
name|Object
name|newV
parameter_list|)
block|{}
comment|/* For enabling the renderer to handle icons. */
DECL|method|setValue (Object value)
specifier|protected
name|void
name|setValue
parameter_list|(
name|Object
name|value
parameter_list|)
block|{
comment|//System.out.println(""+value);
if|if
condition|(
name|value
operator|instanceof
name|Icon
condition|)
block|{
name|setIcon
argument_list|(
operator|(
name|Icon
operator|)
name|value
argument_list|)
expr_stmt|;
name|setText
argument_list|(
literal|null
argument_list|)
expr_stmt|;
comment|//super.setValue(null);
block|}
elseif|else
if|if
condition|(
name|value
operator|instanceof
name|JLabel
condition|)
block|{
name|JLabel
name|lab
init|=
operator|(
name|JLabel
operator|)
name|value
decl_stmt|;
name|setIcon
argument_list|(
name|lab
operator|.
name|getIcon
argument_list|()
argument_list|)
expr_stmt|;
comment|//table.setToolTipText(lab.getToolTipText());
name|setToolTipText
argument_list|(
name|lab
operator|.
name|getToolTipText
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|lab
operator|.
name|getIcon
argument_list|()
operator|!=
literal|null
condition|)
name|setText
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|setIcon
argument_list|(
literal|null
argument_list|)
expr_stmt|;
comment|//table.setToolTipText(null);
name|setToolTipText
argument_list|(
literal|null
argument_list|)
expr_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
name|setText
argument_list|(
name|value
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|setText
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|paint (Graphics g)
specifier|public
name|void
name|paint
parameter_list|(
name|Graphics
name|g
parameter_list|)
block|{
name|Graphics2D
name|g2
init|=
operator|(
name|Graphics2D
operator|)
name|g
decl_stmt|;
if|if
condition|(
name|antialiasing
condition|)
block|{
name|RenderingHints
name|rh
init|=
name|g2
operator|.
name|getRenderingHints
argument_list|()
decl_stmt|;
name|rh
operator|.
name|put
argument_list|(
name|RenderingHints
operator|.
name|KEY_ANTIALIASING
argument_list|,
name|RenderingHints
operator|.
name|VALUE_ANTIALIAS_ON
argument_list|)
expr_stmt|;
name|rh
operator|.
name|put
argument_list|(
name|RenderingHints
operator|.
name|KEY_RENDERING
argument_list|,
name|RenderingHints
operator|.
name|VALUE_RENDER_QUALITY
argument_list|)
expr_stmt|;
name|g2
operator|.
name|setRenderingHints
argument_list|(
name|rh
argument_list|)
expr_stmt|;
block|}
name|super
operator|.
name|paint
argument_list|(
name|g2
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

