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
name|logic
operator|.
name|bibtex
operator|.
name|comparator
operator|.
name|MetaDataDiff
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
name|metadata
operator|.
name|MetaData
import|;
end_import

begin_comment
comment|/**  *  */
end_comment

begin_class
DECL|class|MetaDataChangeViewModel
class|class
name|MetaDataChangeViewModel
extends|extends
name|ChangeViewModel
block|{
DECL|field|infoPane
specifier|private
specifier|final
name|InfoPane
name|infoPane
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
name|infoPane
argument_list|)
decl_stmt|;
DECL|field|originalMetaData
specifier|private
specifier|final
name|MetaData
name|originalMetaData
decl_stmt|;
DECL|field|newMetaData
specifier|private
specifier|final
name|MetaData
name|newMetaData
decl_stmt|;
DECL|method|MetaDataChangeViewModel (MetaData originalMetaData, MetaDataDiff metaDataDiff)
specifier|public
name|MetaDataChangeViewModel
parameter_list|(
name|MetaData
name|originalMetaData
parameter_list|,
name|MetaDataDiff
name|metaDataDiff
parameter_list|)
block|{
name|super
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Metadata change"
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|originalMetaData
operator|=
name|originalMetaData
expr_stmt|;
name|this
operator|.
name|newMetaData
operator|=
name|metaDataDiff
operator|.
name|getNewMetaData
argument_list|()
expr_stmt|;
name|infoPane
operator|.
name|setText
argument_list|(
literal|"<html>"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Metadata change"
argument_list|)
operator|+
literal|"</html>"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|description ()
specifier|public
name|JComponent
name|description
parameter_list|()
block|{
comment|/*         // TODO: Show detailed description of the changes         StringBuilder sb = new StringBuilder(                 "<html>" + Localization.lang("Changes have been made to the following metadata elements")                         + ":<p><br>&nbsp;&nbsp;");         sb.append(changes.stream().map(unit -> unit.key).collect(Collectors.joining("<br>&nbsp;&nbsp;")));         sb.append("</html>");         infoPane.setText(sb.toString());         */
return|return
name|sp
return|;
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
name|getBibDatabaseContext
argument_list|()
operator|.
name|setMetaData
argument_list|(
name|newMetaData
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
block|}
end_class

end_unit

