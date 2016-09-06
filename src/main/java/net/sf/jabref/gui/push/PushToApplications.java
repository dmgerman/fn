begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.push
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|push
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

begin_class
DECL|class|PushToApplications
specifier|public
class|class
name|PushToApplications
block|{
DECL|field|applications
specifier|private
specifier|final
name|List
argument_list|<
name|PushToApplication
argument_list|>
name|applications
decl_stmt|;
DECL|method|PushToApplications ()
specifier|public
name|PushToApplications
parameter_list|()
block|{
comment|/**      * Set up the current available choices:      */
name|applications
operator|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
expr_stmt|;
name|applications
operator|.
name|add
argument_list|(
operator|new
name|PushToEmacs
argument_list|()
argument_list|)
expr_stmt|;
name|applications
operator|.
name|add
argument_list|(
operator|new
name|PushToLatexEditor
argument_list|()
argument_list|)
expr_stmt|;
name|applications
operator|.
name|add
argument_list|(
operator|new
name|PushToLyx
argument_list|()
argument_list|)
expr_stmt|;
name|applications
operator|.
name|add
argument_list|(
operator|new
name|PushToTexmaker
argument_list|()
argument_list|)
expr_stmt|;
name|applications
operator|.
name|add
argument_list|(
operator|new
name|PushToTeXstudio
argument_list|()
argument_list|)
expr_stmt|;
name|applications
operator|.
name|add
argument_list|(
operator|new
name|PushToVim
argument_list|()
argument_list|)
expr_stmt|;
name|applications
operator|.
name|add
argument_list|(
operator|new
name|PushToWinEdt
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|getApplications ()
specifier|public
name|List
argument_list|<
name|PushToApplication
argument_list|>
name|getApplications
parameter_list|()
block|{
return|return
name|applications
return|;
block|}
block|}
end_class

end_unit

