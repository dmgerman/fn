begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.keyboard
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|keyboard
package|;
end_package

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JTable
import|;
end_import

begin_class
annotation|@
name|SuppressWarnings
argument_list|(
literal|"serial"
argument_list|)
DECL|class|KeyBindingTable
specifier|public
class|class
name|KeyBindingTable
extends|extends
name|JTable
block|{
annotation|@
name|Override
DECL|method|isCellEditable (int row, int col)
specifier|public
name|boolean
name|isCellEditable
parameter_list|(
name|int
name|row
parameter_list|,
name|int
name|col
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
DECL|method|getOriginalName (int row)
specifier|public
name|String
name|getOriginalName
parameter_list|(
name|int
name|row
parameter_list|)
block|{
return|return
operator|(
operator|(
name|KeyBindingTableModel
operator|)
name|getModel
argument_list|()
operator|)
operator|.
name|getOriginalName
argument_list|(
name|row
argument_list|)
return|;
block|}
block|}
end_class

end_unit

