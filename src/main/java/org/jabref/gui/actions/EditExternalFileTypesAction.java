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
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|externalfiletype
operator|.
name|ExternalFileTypeEditor
import|;
end_import

begin_comment
comment|//TODO: DOES NOT SHOW UP
end_comment

begin_class
DECL|class|EditExternalFileTypesAction
specifier|public
class|class
name|EditExternalFileTypesAction
extends|extends
name|SimpleCommand
block|{
DECL|field|editor
specifier|private
name|ExternalFileTypeEditor
name|editor
decl_stmt|;
annotation|@
name|Override
DECL|method|execute ()
specifier|public
name|void
name|execute
parameter_list|()
block|{
if|if
condition|(
name|editor
operator|==
literal|null
condition|)
block|{
name|editor
operator|=
operator|new
name|ExternalFileTypeEditor
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

