begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.documentviewer
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|documentviewer
package|;
end_package

begin_import
import|import
name|javafx
operator|.
name|collections
operator|.
name|ObservableList
import|;
end_import

begin_class
DECL|class|DocumentViewModel
specifier|public
specifier|abstract
class|class
name|DocumentViewModel
block|{
DECL|method|getPages ()
specifier|public
specifier|abstract
name|ObservableList
argument_list|<
name|DocumentPageViewModel
argument_list|>
name|getPages
parameter_list|()
function_decl|;
block|}
end_class

end_unit

