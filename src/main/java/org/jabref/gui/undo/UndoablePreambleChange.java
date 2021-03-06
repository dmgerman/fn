begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.undo
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|undo
package|;
end_package

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|l10n
operator|.
name|Localization
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
operator|.
name|BibDatabase
import|;
end_import

begin_comment
comment|/**  * This class represents a change in any field value. The relevant  * information is the BibEntry, the field name, the old and the  * new value. Old/new values can be null.  */
end_comment

begin_class
DECL|class|UndoablePreambleChange
specifier|public
class|class
name|UndoablePreambleChange
extends|extends
name|AbstractUndoableJabRefEdit
block|{
DECL|field|base
specifier|private
specifier|final
name|BibDatabase
name|base
decl_stmt|;
DECL|field|oldValue
specifier|private
specifier|final
name|String
name|oldValue
decl_stmt|;
DECL|field|newValue
specifier|private
specifier|final
name|String
name|newValue
decl_stmt|;
DECL|method|UndoablePreambleChange (BibDatabase base, String oldValue, String newValue)
specifier|public
name|UndoablePreambleChange
parameter_list|(
name|BibDatabase
name|base
parameter_list|,
name|String
name|oldValue
parameter_list|,
name|String
name|newValue
parameter_list|)
block|{
name|this
operator|.
name|base
operator|=
name|base
expr_stmt|;
name|this
operator|.
name|oldValue
operator|=
name|oldValue
expr_stmt|;
name|this
operator|.
name|newValue
operator|=
name|newValue
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getPresentationName ()
specifier|public
name|String
name|getPresentationName
parameter_list|()
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"change preamble"
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|undo ()
specifier|public
name|void
name|undo
parameter_list|()
block|{
name|super
operator|.
name|undo
argument_list|()
expr_stmt|;
comment|// Revert the change.
name|base
operator|.
name|setPreamble
argument_list|(
name|oldValue
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|redo ()
specifier|public
name|void
name|redo
parameter_list|()
block|{
name|super
operator|.
name|redo
argument_list|()
expr_stmt|;
comment|// Redo the change.
name|base
operator|.
name|setPreamble
argument_list|(
name|newValue
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

