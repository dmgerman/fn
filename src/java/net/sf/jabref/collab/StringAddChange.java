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
name|UndoableStringChange
import|;
end_import

begin_class
DECL|class|StringAddChange
specifier|public
class|class
name|StringAddChange
extends|extends
name|Change
block|{
DECL|field|string
name|BibtexString
name|string
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
DECL|method|StringAddChange (BibtexString string)
specifier|public
name|StringAddChange
parameter_list|(
name|BibtexString
name|string
parameter_list|)
block|{
name|name
operator|=
name|Globals
operator|.
name|lang
argument_list|(
literal|"Added string"
argument_list|)
operator|+
literal|": '"
operator|+
name|string
operator|.
name|getName
argument_list|()
operator|+
literal|"'"
expr_stmt|;
name|this
operator|.
name|string
operator|=
name|string
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
literal|"Added string"
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
name|string
operator|.
name|getName
argument_list|()
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
literal|"Content"
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
DECL|method|makeChange (BasePanel panel, NamedCompound undoEdit)
specifier|public
name|void
name|makeChange
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|NamedCompound
name|undoEdit
parameter_list|)
block|{
if|if
condition|(
name|panel
operator|.
name|database
argument_list|()
operator|.
name|hasStringLabel
argument_list|(
name|string
operator|.
name|getName
argument_list|()
argument_list|)
condition|)
block|{
comment|// The name to change to is already in the database, so we can't comply.
name|Globals
operator|.
name|logger
argument_list|(
literal|"Cannot add string '"
operator|+
name|string
operator|.
name|getName
argument_list|()
operator|+
literal|"' because the name "
operator|+
literal|"is already in use."
argument_list|)
expr_stmt|;
block|}
try|try
block|{
name|panel
operator|.
name|database
argument_list|()
operator|.
name|addString
argument_list|(
name|string
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
name|string
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

