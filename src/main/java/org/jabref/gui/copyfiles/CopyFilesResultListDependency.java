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

begin_comment
comment|/**  * This class is a wrapper class for the containing list as it is currently not possible to inject complex object types into FXML controller  *  */
end_comment

begin_class
DECL|class|CopyFilesResultListDependency
specifier|public
class|class
name|CopyFilesResultListDependency
block|{
DECL|field|results
specifier|private
name|List
argument_list|<
name|CopyFilesResultItemViewModel
argument_list|>
name|results
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|method|CopyFilesResultListDependency ()
specifier|public
name|CopyFilesResultListDependency
parameter_list|()
block|{
comment|//empty, workaround for injection into FXML controller
block|}
DECL|method|CopyFilesResultListDependency (List<CopyFilesResultItemViewModel> results)
specifier|public
name|CopyFilesResultListDependency
parameter_list|(
name|List
argument_list|<
name|CopyFilesResultItemViewModel
argument_list|>
name|results
parameter_list|)
block|{
name|this
operator|.
name|results
operator|=
name|results
expr_stmt|;
block|}
DECL|method|getResults ()
specifier|public
name|List
argument_list|<
name|CopyFilesResultItemViewModel
argument_list|>
name|getResults
parameter_list|()
block|{
return|return
name|results
return|;
block|}
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"CopyFilesResultListDependency [results="
operator|+
name|results
operator|+
literal|"]"
return|;
block|}
block|}
end_class

end_unit

