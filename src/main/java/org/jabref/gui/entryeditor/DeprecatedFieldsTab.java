begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.entryeditor
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|entryeditor
package|;
end_package

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|Tooltip
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
name|IconTheme
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
name|entry
operator|.
name|BibEntry
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
name|EntryType
import|;
end_import

begin_class
DECL|class|DeprecatedFieldsTab
specifier|public
class|class
name|DeprecatedFieldsTab
extends|extends
name|FieldsEditorTab
block|{
DECL|method|DeprecatedFieldsTab (JabRefFrame frame, BasePanel basePanel, EntryType entryType, EntryEditor parent, BibEntry entry)
specifier|public
name|DeprecatedFieldsTab
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|BasePanel
name|basePanel
parameter_list|,
name|EntryType
name|entryType
parameter_list|,
name|EntryEditor
name|parent
parameter_list|,
name|BibEntry
name|entry
parameter_list|)
block|{
name|super
argument_list|(
name|frame
argument_list|,
name|basePanel
argument_list|,
name|entryType
operator|.
name|getDeprecatedFields
argument_list|()
argument_list|,
name|parent
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|,
name|entry
argument_list|)
expr_stmt|;
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Deprecated fields"
argument_list|)
argument_list|)
expr_stmt|;
name|setTooltip
argument_list|(
operator|new
name|Tooltip
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Show deprecated BibTeX fields"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|setGraphic
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|OPTIONAL
operator|.
name|getGraphicNode
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

