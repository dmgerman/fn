begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.collab
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|collab
package|;
end_package

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JComponent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JScrollPane
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|*
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|undo
operator|.
name|NamedCompound
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|undo
operator|.
name|UndoableInsertString
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|undo
operator|.
name|UndoableStringChange
import|;
end_import

begin_class
DECL|class|StringChange
specifier|public
class|class
name|StringChange
extends|extends
name|Change
block|{
DECL|field|string
name|BibtexString
name|string
decl_stmt|;
DECL|field|mem
DECL|field|tmp
DECL|field|disk
DECL|field|label
name|String
name|mem
decl_stmt|,
name|tmp
decl_stmt|,
name|disk
decl_stmt|,
name|label
decl_stmt|;
DECL|field|tp
name|InfoPane
name|tp
init|=
operator|new
name|InfoPane
argument_list|()
decl_stmt|;
DECL|field|sp
name|JScrollPane
name|sp
init|=
operator|new
name|JScrollPane
argument_list|(
name|tp
argument_list|)
decl_stmt|;
DECL|field|tmpString
specifier|private
name|BibtexString
name|tmpString
decl_stmt|;
DECL|method|StringChange (BibtexString string, BibtexString tmpString, String label, String mem, String tmp, String disk)
specifier|public
name|StringChange
parameter_list|(
name|BibtexString
name|string
parameter_list|,
name|BibtexString
name|tmpString
parameter_list|,
name|String
name|label
parameter_list|,
name|String
name|mem
parameter_list|,
name|String
name|tmp
parameter_list|,
name|String
name|disk
parameter_list|)
block|{
name|this
operator|.
name|tmpString
operator|=
name|tmpString
expr_stmt|;
name|name
operator|=
name|Globals
operator|.
name|lang
argument_list|(
literal|"Modified string"
argument_list|)
operator|+
literal|": '"
operator|+
name|label
operator|+
literal|"'"
expr_stmt|;
name|this
operator|.
name|string
operator|=
name|string
expr_stmt|;
name|this
operator|.
name|label
operator|=
name|label
expr_stmt|;
name|this
operator|.
name|mem
operator|=
name|mem
expr_stmt|;
name|this
operator|.
name|tmp
operator|=
name|tmp
expr_stmt|;
name|this
operator|.
name|disk
operator|=
name|disk
expr_stmt|;
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"<HTML><H2>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Modified string"
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"</H2><H3>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Label"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|":</H3>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|label
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"<H3>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"New content"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|":</H3>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|disk
argument_list|)
expr_stmt|;
if|if
condition|(
name|string
operator|!=
literal|null
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"<H3>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Current content"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|":</H3>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|string
operator|.
name|getContent
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"<P><I>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Cannot merge this change"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|": "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"The string has been removed locally"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|"</I>"
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
literal|"</HTML>"
argument_list|)
expr_stmt|;
name|tp
operator|.
name|setText
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|makeChange (BasePanel panel, BibtexDatabase secondary, NamedCompound undoEdit)
specifier|public
name|boolean
name|makeChange
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|BibtexDatabase
name|secondary
parameter_list|,
name|NamedCompound
name|undoEdit
parameter_list|)
block|{
if|if
condition|(
name|string
operator|!=
literal|null
condition|)
block|{
name|string
operator|.
name|setContent
argument_list|(
name|disk
argument_list|)
expr_stmt|;
name|undoEdit
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableStringChange
argument_list|(
name|panel
argument_list|,
name|string
argument_list|,
literal|false
argument_list|,
name|mem
argument_list|,
name|disk
argument_list|)
argument_list|)
expr_stmt|;
comment|// Update tmp databse:
block|}
else|else
block|{
comment|// The string was removed or renamed locally. We guess that it was removed.
name|String
name|newId
init|=
name|Util
operator|.
name|createNeutralId
argument_list|()
decl_stmt|;
name|BibtexString
name|bs
init|=
operator|new
name|BibtexString
argument_list|(
name|newId
argument_list|,
name|label
argument_list|,
name|disk
argument_list|)
decl_stmt|;
try|try
block|{
name|panel
operator|.
name|database
argument_list|()
operator|.
name|addString
argument_list|(
name|bs
argument_list|)
expr_stmt|;
name|undoEdit
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableInsertString
argument_list|(
name|panel
argument_list|,
name|panel
operator|.
name|database
argument_list|()
argument_list|,
name|bs
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|KeyCollisionException
name|ex
parameter_list|)
block|{
name|Globals
operator|.
name|logger
argument_list|(
literal|"Error: could not add string '"
operator|+
name|string
operator|.
name|getName
argument_list|()
operator|+
literal|"': "
operator|+
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Update tmp database:
if|if
condition|(
name|tmpString
operator|!=
literal|null
condition|)
block|{
name|tmpString
operator|.
name|setContent
argument_list|(
name|disk
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|BibtexString
name|bs
init|=
operator|new
name|BibtexString
argument_list|(
name|Util
operator|.
name|createNeutralId
argument_list|()
argument_list|,
name|label
argument_list|,
name|disk
argument_list|)
decl_stmt|;
name|secondary
operator|.
name|addString
argument_list|(
name|bs
argument_list|)
expr_stmt|;
block|}
return|return
literal|true
return|;
block|}
DECL|method|description ()
name|JComponent
name|description
parameter_list|()
block|{
return|return
name|sp
return|;
block|}
block|}
end_class

end_unit

