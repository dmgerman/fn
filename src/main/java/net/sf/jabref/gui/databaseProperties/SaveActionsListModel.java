begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.databaseProperties
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|databaseProperties
package|;
end_package

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
name|event
operator|.
name|ListDataEvent
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
name|ListDataListener
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_class
DECL|class|SaveActionsListModel
class|class
name|SaveActionsListModel
parameter_list|<
name|SaveAction
parameter_list|>
implements|implements
name|ListModel
argument_list|<
name|SaveAction
argument_list|>
block|{
DECL|field|saveActions
specifier|private
name|List
argument_list|<
name|SaveAction
argument_list|>
name|saveActions
decl_stmt|;
DECL|field|listeners
specifier|private
name|List
argument_list|<
name|ListDataListener
argument_list|>
name|listeners
decl_stmt|;
DECL|method|SaveActionsListModel (List<SaveAction> saveActions)
specifier|public
name|SaveActionsListModel
parameter_list|(
name|List
argument_list|<
name|SaveAction
argument_list|>
name|saveActions
parameter_list|)
block|{
if|if
condition|(
name|saveActions
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Input data must not be null"
argument_list|)
throw|;
block|}
name|this
operator|.
name|saveActions
operator|=
name|saveActions
expr_stmt|;
name|listeners
operator|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
expr_stmt|;
block|}
DECL|method|addSaveAction (SaveAction action)
specifier|public
name|void
name|addSaveAction
parameter_list|(
name|SaveAction
name|action
parameter_list|)
block|{
name|saveActions
operator|.
name|add
argument_list|(
name|action
argument_list|)
expr_stmt|;
for|for
control|(
name|ListDataListener
name|listener
range|:
name|listeners
control|)
block|{
name|listener
operator|.
name|intervalAdded
argument_list|(
operator|new
name|ListDataEvent
argument_list|(
name|action
argument_list|,
name|ListDataEvent
operator|.
name|INTERVAL_ADDED
argument_list|,
name|saveActions
operator|.
name|size
argument_list|()
argument_list|,
name|saveActions
operator|.
name|size
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|getAllActions ()
specifier|public
name|List
argument_list|<
name|SaveAction
argument_list|>
name|getAllActions
parameter_list|()
block|{
return|return
name|saveActions
return|;
block|}
annotation|@
name|Override
DECL|method|getSize ()
specifier|public
name|int
name|getSize
parameter_list|()
block|{
return|return
name|saveActions
operator|.
name|size
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|getElementAt (int index)
specifier|public
name|SaveAction
name|getElementAt
parameter_list|(
name|int
name|index
parameter_list|)
block|{
return|return
name|saveActions
operator|.
name|get
argument_list|(
name|index
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|addListDataListener (ListDataListener l)
specifier|public
name|void
name|addListDataListener
parameter_list|(
name|ListDataListener
name|l
parameter_list|)
block|{
name|listeners
operator|.
name|add
argument_list|(
name|l
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|removeListDataListener (ListDataListener l)
specifier|public
name|void
name|removeListDataListener
parameter_list|(
name|ListDataListener
name|l
parameter_list|)
block|{
name|listeners
operator|.
name|remove
argument_list|(
name|l
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

