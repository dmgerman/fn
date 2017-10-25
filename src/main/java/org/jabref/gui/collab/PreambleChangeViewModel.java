begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.collab
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
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
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|BasePanel
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
name|undo
operator|.
name|NamedCompound
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
name|undo
operator|.
name|UndoablePreambleChange
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|bibtex
operator|.
name|comparator
operator|.
name|PreambleDiff
import|;
end_import

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

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|strings
operator|.
name|StringUtil
import|;
end_import

begin_class
DECL|class|PreambleChangeViewModel
class|class
name|PreambleChangeViewModel
extends|extends
name|ChangeViewModel
block|{
DECL|field|mem
specifier|private
specifier|final
name|String
name|mem
decl_stmt|;
DECL|field|disk
specifier|private
specifier|final
name|String
name|disk
decl_stmt|;
DECL|field|tp
specifier|private
specifier|final
name|InfoPane
name|tp
init|=
operator|new
name|InfoPane
argument_list|()
decl_stmt|;
DECL|field|sp
specifier|private
specifier|final
name|JScrollPane
name|sp
init|=
operator|new
name|JScrollPane
argument_list|(
name|tp
argument_list|)
decl_stmt|;
DECL|method|PreambleChangeViewModel (String mem, PreambleDiff diff)
specifier|public
name|PreambleChangeViewModel
parameter_list|(
name|String
name|mem
parameter_list|,
name|PreambleDiff
name|diff
parameter_list|)
block|{
name|super
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Changed preamble"
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|disk
operator|=
name|diff
operator|.
name|getNewPreamble
argument_list|()
expr_stmt|;
name|this
operator|.
name|mem
operator|=
name|mem
expr_stmt|;
name|StringBuilder
name|text
init|=
operator|new
name|StringBuilder
argument_list|(
literal|34
argument_list|)
decl_stmt|;
name|text
operator|.
name|append
argument_list|(
literal|"<FONT SIZE=3><H2>"
argument_list|)
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Changed preamble"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|"</H2>"
argument_list|)
expr_stmt|;
if|if
condition|(
name|StringUtil
operator|.
name|isNotBlank
argument_list|(
name|disk
argument_list|)
condition|)
block|{
name|text
operator|.
name|append
argument_list|(
literal|"<H3>"
argument_list|)
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Value set externally"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|":</H3>"
operator|+
literal|"<CODE>"
argument_list|)
operator|.
name|append
argument_list|(
name|disk
argument_list|)
operator|.
name|append
argument_list|(
literal|"</CODE>"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|text
operator|.
name|append
argument_list|(
literal|"<H3>"
argument_list|)
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Value cleared externally"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|"</H3>"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|StringUtil
operator|.
name|isNotBlank
argument_list|(
name|mem
argument_list|)
condition|)
block|{
name|text
operator|.
name|append
argument_list|(
literal|"<H3>"
argument_list|)
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Current value"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|":</H3>"
operator|+
literal|"<CODE>"
argument_list|)
operator|.
name|append
argument_list|(
name|mem
argument_list|)
operator|.
name|append
argument_list|(
literal|"</CODE>"
argument_list|)
expr_stmt|;
block|}
name|tp
operator|.
name|setText
argument_list|(
name|text
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|makeChange (BasePanel panel, BibDatabase secondary, NamedCompound undoEdit)
specifier|public
name|boolean
name|makeChange
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|BibDatabase
name|secondary
parameter_list|,
name|NamedCompound
name|undoEdit
parameter_list|)
block|{
name|panel
operator|.
name|getDatabase
argument_list|()
operator|.
name|setPreamble
argument_list|(
name|disk
argument_list|)
expr_stmt|;
name|undoEdit
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoablePreambleChange
argument_list|(
name|panel
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|panel
argument_list|,
name|mem
argument_list|,
name|disk
argument_list|)
argument_list|)
expr_stmt|;
name|secondary
operator|.
name|setPreamble
argument_list|(
name|disk
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
annotation|@
name|Override
DECL|method|description ()
specifier|public
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

