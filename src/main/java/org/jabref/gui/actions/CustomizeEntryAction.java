begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.actions
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|actions
package|;
end_package

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
name|gui
operator|.
name|JabRefFrame
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
name|customentrytypes
operator|.
name|EntryCustomizationDialog
import|;
end_import

begin_class
DECL|class|CustomizeEntryAction
specifier|public
class|class
name|CustomizeEntryAction
extends|extends
name|SimpleCommand
block|{
DECL|field|jabRefFrame
specifier|private
specifier|final
name|JabRefFrame
name|jabRefFrame
decl_stmt|;
DECL|method|CustomizeEntryAction (JabRefFrame jabRefFrame)
specifier|public
name|CustomizeEntryAction
parameter_list|(
name|JabRefFrame
name|jabRefFrame
parameter_list|)
block|{
name|this
operator|.
name|jabRefFrame
operator|=
name|jabRefFrame
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|execute ()
specifier|public
name|void
name|execute
parameter_list|()
block|{
name|JDialog
name|dl
init|=
operator|new
name|EntryCustomizationDialog
argument_list|(
name|jabRefFrame
argument_list|)
decl_stmt|;
name|dl
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

