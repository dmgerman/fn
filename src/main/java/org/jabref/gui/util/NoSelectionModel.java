begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.util
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|util
package|;
end_package

begin_import
import|import
name|javafx
operator|.
name|collections
operator|.
name|FXCollections
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|collections
operator|.
name|ObservableList
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|MultipleSelectionModel
import|;
end_import

begin_comment
comment|/**  * Disables selection  */
end_comment

begin_class
DECL|class|NoSelectionModel
specifier|public
class|class
name|NoSelectionModel
parameter_list|<
name|T
parameter_list|>
extends|extends
name|MultipleSelectionModel
argument_list|<
name|T
argument_list|>
block|{
annotation|@
name|Override
DECL|method|getSelectedIndices ()
specifier|public
name|ObservableList
argument_list|<
name|Integer
argument_list|>
name|getSelectedIndices
parameter_list|()
block|{
return|return
name|FXCollections
operator|.
name|emptyObservableList
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|getSelectedItems ()
specifier|public
name|ObservableList
argument_list|<
name|T
argument_list|>
name|getSelectedItems
parameter_list|()
block|{
return|return
name|FXCollections
operator|.
name|emptyObservableList
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|selectIndices (int index, int... indices)
specifier|public
name|void
name|selectIndices
parameter_list|(
name|int
name|index
parameter_list|,
name|int
modifier|...
name|indices
parameter_list|)
block|{     }
annotation|@
name|Override
DECL|method|selectAll ()
specifier|public
name|void
name|selectAll
parameter_list|()
block|{     }
annotation|@
name|Override
DECL|method|selectFirst ()
specifier|public
name|void
name|selectFirst
parameter_list|()
block|{     }
annotation|@
name|Override
DECL|method|selectLast ()
specifier|public
name|void
name|selectLast
parameter_list|()
block|{     }
annotation|@
name|Override
DECL|method|clearAndSelect (int index)
specifier|public
name|void
name|clearAndSelect
parameter_list|(
name|int
name|index
parameter_list|)
block|{     }
annotation|@
name|Override
DECL|method|select (int index)
specifier|public
name|void
name|select
parameter_list|(
name|int
name|index
parameter_list|)
block|{     }
annotation|@
name|Override
DECL|method|select (T obj)
specifier|public
name|void
name|select
parameter_list|(
name|T
name|obj
parameter_list|)
block|{     }
annotation|@
name|Override
DECL|method|clearSelection (int index)
specifier|public
name|void
name|clearSelection
parameter_list|(
name|int
name|index
parameter_list|)
block|{     }
annotation|@
name|Override
DECL|method|clearSelection ()
specifier|public
name|void
name|clearSelection
parameter_list|()
block|{     }
annotation|@
name|Override
DECL|method|isSelected (int index)
specifier|public
name|boolean
name|isSelected
parameter_list|(
name|int
name|index
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
DECL|method|isEmpty ()
specifier|public
name|boolean
name|isEmpty
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
annotation|@
name|Override
DECL|method|selectPrevious ()
specifier|public
name|void
name|selectPrevious
parameter_list|()
block|{     }
annotation|@
name|Override
DECL|method|selectNext ()
specifier|public
name|void
name|selectNext
parameter_list|()
block|{     }
block|}
end_class

end_unit

