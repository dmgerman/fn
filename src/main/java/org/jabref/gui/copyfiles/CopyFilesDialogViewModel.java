begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.copyfiles
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|copyfiles
package|;
end_package

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|SimpleListProperty
import|;
end_import

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
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|AbstractViewModel
import|;
end_import

begin_class
DECL|class|CopyFilesDialogViewModel
specifier|public
class|class
name|CopyFilesDialogViewModel
extends|extends
name|AbstractViewModel
block|{
DECL|field|copyFilesResultItems
specifier|private
specifier|final
name|SimpleListProperty
argument_list|<
name|CopyFilesResultItemViewModel
argument_list|>
name|copyFilesResultItems
init|=
operator|new
name|SimpleListProperty
argument_list|<>
argument_list|(
name|FXCollections
operator|.
name|observableArrayList
argument_list|()
argument_list|)
decl_stmt|;
DECL|method|CopyFilesDialogViewModel (CopyFilesResultListDependency results)
specifier|public
name|CopyFilesDialogViewModel
parameter_list|(
name|CopyFilesResultListDependency
name|results
parameter_list|)
block|{
name|copyFilesResultItems
operator|.
name|addAll
argument_list|(
name|results
operator|.
name|getResults
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|copyFilesResultListProperty ()
specifier|public
name|SimpleListProperty
argument_list|<
name|CopyFilesResultItemViewModel
argument_list|>
name|copyFilesResultListProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|copyFilesResultItems
return|;
block|}
block|}
end_class

end_unit

