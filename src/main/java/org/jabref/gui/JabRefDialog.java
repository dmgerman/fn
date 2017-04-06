begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui
package|package
name|org
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
name|Frame
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Window
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JDialog
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|Globals
import|;
end_import

begin_class
DECL|class|JabRefDialog
specifier|public
class|class
name|JabRefDialog
extends|extends
name|JDialog
block|{
DECL|method|JabRefDialog (Frame owner, boolean modal, Class<T> clazz)
specifier|public
parameter_list|<
name|T
extends|extends
name|JabRefDialog
parameter_list|>
name|JabRefDialog
parameter_list|(
name|Frame
name|owner
parameter_list|,
name|boolean
name|modal
parameter_list|,
name|Class
argument_list|<
name|T
argument_list|>
name|clazz
parameter_list|)
block|{
name|super
argument_list|(
name|owner
argument_list|,
name|modal
argument_list|)
expr_stmt|;
name|trackDialogOpening
argument_list|(
name|clazz
argument_list|)
expr_stmt|;
block|}
DECL|method|JabRefDialog (Class<T> clazz)
specifier|public
parameter_list|<
name|T
extends|extends
name|JabRefDialog
parameter_list|>
name|JabRefDialog
parameter_list|(
name|Class
argument_list|<
name|T
argument_list|>
name|clazz
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|trackDialogOpening
argument_list|(
name|clazz
argument_list|)
expr_stmt|;
block|}
DECL|method|JabRefDialog (Frame owner, Class<T> clazz)
specifier|public
parameter_list|<
name|T
extends|extends
name|JabRefDialog
parameter_list|>
name|JabRefDialog
parameter_list|(
name|Frame
name|owner
parameter_list|,
name|Class
argument_list|<
name|T
argument_list|>
name|clazz
parameter_list|)
block|{
name|super
argument_list|(
name|owner
argument_list|)
expr_stmt|;
name|trackDialogOpening
argument_list|(
name|clazz
argument_list|)
expr_stmt|;
block|}
DECL|method|JabRefDialog (Window owner, String title, Class<T> clazz)
specifier|public
parameter_list|<
name|T
extends|extends
name|JabRefDialog
parameter_list|>
name|JabRefDialog
parameter_list|(
name|Window
name|owner
parameter_list|,
name|String
name|title
parameter_list|,
name|Class
argument_list|<
name|T
argument_list|>
name|clazz
parameter_list|)
block|{
name|super
argument_list|(
name|owner
argument_list|,
name|title
argument_list|)
expr_stmt|;
name|trackDialogOpening
argument_list|(
name|clazz
argument_list|)
expr_stmt|;
block|}
DECL|method|trackDialogOpening (Class<T> clazz)
specifier|private
parameter_list|<
name|T
extends|extends
name|JabRefDialog
parameter_list|>
name|void
name|trackDialogOpening
parameter_list|(
name|Class
argument_list|<
name|T
argument_list|>
name|clazz
parameter_list|)
block|{
name|Globals
operator|.
name|getTelemetryClient
argument_list|()
operator|.
name|trackPageView
argument_list|(
name|clazz
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|JabRefDialog (Frame owner, String title, Class<T> clazz)
specifier|public
parameter_list|<
name|T
extends|extends
name|JabRefDialog
parameter_list|>
name|JabRefDialog
parameter_list|(
name|Frame
name|owner
parameter_list|,
name|String
name|title
parameter_list|,
name|Class
argument_list|<
name|T
argument_list|>
name|clazz
parameter_list|)
block|{
name|this
argument_list|(
name|owner
argument_list|,
name|title
argument_list|,
literal|true
argument_list|,
name|clazz
argument_list|)
expr_stmt|;
block|}
DECL|method|JabRefDialog (Frame owner, String title, boolean modal, Class<T> clazz)
specifier|public
parameter_list|<
name|T
extends|extends
name|JabRefDialog
parameter_list|>
name|JabRefDialog
parameter_list|(
name|Frame
name|owner
parameter_list|,
name|String
name|title
parameter_list|,
name|boolean
name|modal
parameter_list|,
name|Class
argument_list|<
name|T
argument_list|>
name|clazz
parameter_list|)
block|{
name|super
argument_list|(
name|owner
argument_list|,
name|title
argument_list|,
name|modal
argument_list|)
expr_stmt|;
name|trackDialogOpening
argument_list|(
name|clazz
argument_list|)
expr_stmt|;
block|}
DECL|method|JabRefDialog (java.awt.Dialog owner, String title, Class<T> clazz)
specifier|public
parameter_list|<
name|T
extends|extends
name|JabRefDialog
parameter_list|>
name|JabRefDialog
parameter_list|(
name|java
operator|.
name|awt
operator|.
name|Dialog
name|owner
parameter_list|,
name|String
name|title
parameter_list|,
name|Class
argument_list|<
name|T
argument_list|>
name|clazz
parameter_list|)
block|{
name|this
argument_list|(
name|owner
argument_list|,
name|title
argument_list|,
literal|true
argument_list|,
name|clazz
argument_list|)
expr_stmt|;
block|}
DECL|method|JabRefDialog (java.awt.Dialog owner, String title, boolean modal, Class<T> clazz)
specifier|public
parameter_list|<
name|T
extends|extends
name|JabRefDialog
parameter_list|>
name|JabRefDialog
parameter_list|(
name|java
operator|.
name|awt
operator|.
name|Dialog
name|owner
parameter_list|,
name|String
name|title
parameter_list|,
name|boolean
name|modal
parameter_list|,
name|Class
argument_list|<
name|T
argument_list|>
name|clazz
parameter_list|)
block|{
name|super
argument_list|(
name|owner
argument_list|,
name|title
argument_list|,
name|modal
argument_list|)
expr_stmt|;
name|trackDialogOpening
argument_list|(
name|clazz
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

