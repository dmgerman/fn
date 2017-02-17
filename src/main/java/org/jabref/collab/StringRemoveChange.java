begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.collab
package|package
name|org
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
name|UndoableRemoveString
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
name|entry
operator|.
name|BibtexString
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_class
DECL|class|StringRemoveChange
class|class
name|StringRemoveChange
extends|extends
name|Change
block|{
DECL|field|string
specifier|private
specifier|final
name|BibtexString
name|string
decl_stmt|;
DECL|field|inMem
specifier|private
specifier|final
name|BibtexString
name|inMem
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
DECL|field|tmpString
specifier|private
specifier|final
name|BibtexString
name|tmpString
decl_stmt|;
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|StringRemoveChange
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|method|StringRemoveChange (BibtexString string, BibtexString tmpString, BibtexString inMem)
specifier|public
name|StringRemoveChange
parameter_list|(
name|BibtexString
name|string
parameter_list|,
name|BibtexString
name|tmpString
parameter_list|,
name|BibtexString
name|inMem
parameter_list|)
block|{
name|super
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Removed string"
argument_list|)
operator|+
literal|": '"
operator|+
name|string
operator|.
name|getName
argument_list|()
operator|+
literal|'\''
argument_list|)
expr_stmt|;
name|this
operator|.
name|tmpString
operator|=
name|tmpString
expr_stmt|;
name|this
operator|.
name|string
operator|=
name|string
expr_stmt|;
name|this
operator|.
name|inMem
operator|=
name|inMem
expr_stmt|;
comment|// Holds the version in memory. Check if it has been modified...?
name|tp
operator|.
name|setText
argument_list|(
literal|"<HTML><H2>"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Removed string"
argument_list|)
operator|+
literal|"</H2><H3>"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Label"
argument_list|)
operator|+
literal|":</H3>"
operator|+
name|string
operator|.
name|getName
argument_list|()
operator|+
literal|"<H3>"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Content"
argument_list|)
operator|+
literal|":</H3>"
operator|+
name|string
operator|.
name|getContent
argument_list|()
operator|+
literal|"</HTML>"
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
try|try
block|{
name|panel
operator|.
name|getDatabase
argument_list|()
operator|.
name|removeString
argument_list|(
name|inMem
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|undoEdit
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableRemoveString
argument_list|(
name|panel
argument_list|,
name|panel
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|string
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|info
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
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
comment|// Update tmp database:
name|secondary
operator|.
name|removeString
argument_list|(
name|tmpString
operator|.
name|getId
argument_list|()
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
