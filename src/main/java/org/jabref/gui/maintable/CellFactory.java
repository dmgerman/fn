begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.maintable
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|maintable
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|undo
operator|.
name|UndoManager
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|Node
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
name|externalfiletype
operator|.
name|ExternalFileType
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
name|externalfiletype
operator|.
name|ExternalFileTypes
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
name|icon
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
name|icon
operator|.
name|JabRefIcon
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
name|specialfields
operator|.
name|SpecialFieldViewModel
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
name|FieldName
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
name|specialfields
operator|.
name|SpecialField
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
DECL|class|CellFactory
specifier|public
class|class
name|CellFactory
block|{
DECL|field|TABLE_ICONS
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|JabRefIcon
argument_list|>
name|TABLE_ICONS
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
DECL|method|CellFactory (ExternalFileTypes externalFileTypes, UndoManager undoManager)
specifier|public
name|CellFactory
parameter_list|(
name|ExternalFileTypes
name|externalFileTypes
parameter_list|,
name|UndoManager
name|undoManager
parameter_list|)
block|{
name|JabRefIcon
name|icon
decl_stmt|;
name|icon
operator|=
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|PDF_FILE
expr_stmt|;
comment|//icon.setToo(Localization.lang("Open") + " PDF");
name|TABLE_ICONS
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|PDF
argument_list|,
name|icon
argument_list|)
expr_stmt|;
name|icon
operator|=
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|WWW
expr_stmt|;
comment|//icon.setToolTipText(Localization.lang("Open") + " URL");
name|TABLE_ICONS
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|URL
argument_list|,
name|icon
argument_list|)
expr_stmt|;
name|icon
operator|=
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|WWW
expr_stmt|;
comment|//icon.setToolTipText(Localization.lang("Open") + " CiteSeer URL");
name|TABLE_ICONS
operator|.
name|put
argument_list|(
literal|"citeseerurl"
argument_list|,
name|icon
argument_list|)
expr_stmt|;
name|icon
operator|=
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|WWW
expr_stmt|;
comment|//icon.setToolTipText(Localization.lang("Open") + " ArXiv URL");
name|TABLE_ICONS
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|EPRINT
argument_list|,
name|icon
argument_list|)
expr_stmt|;
name|icon
operator|=
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|DOI
expr_stmt|;
comment|//icon.setToolTipText(Localization.lang("Open") + " DOI " + Localization.lang("web link"));
name|TABLE_ICONS
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|DOI
argument_list|,
name|icon
argument_list|)
expr_stmt|;
name|icon
operator|=
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|FILE
expr_stmt|;
comment|//icon.setToolTipText(Localization.lang("Open") + " PS");
name|TABLE_ICONS
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|PS
argument_list|,
name|icon
argument_list|)
expr_stmt|;
name|icon
operator|=
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|FOLDER
expr_stmt|;
comment|//icon.setToolTipText(Localization.lang("Open folder"));
name|TABLE_ICONS
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|FOLDER
argument_list|,
name|icon
argument_list|)
expr_stmt|;
name|icon
operator|=
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|FILE
expr_stmt|;
comment|//icon.setToolTipText(Localization.lang("Open file"));
name|TABLE_ICONS
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|FILE
argument_list|,
name|icon
argument_list|)
expr_stmt|;
for|for
control|(
name|ExternalFileType
name|fileType
range|:
name|externalFileTypes
operator|.
name|getExternalFileTypeSelection
argument_list|()
control|)
block|{
name|icon
operator|=
name|fileType
operator|.
name|getIcon
argument_list|()
expr_stmt|;
comment|//icon.setToolTipText(Localization.lang("Open %0 file", fileType.getName()));
name|TABLE_ICONS
operator|.
name|put
argument_list|(
name|fileType
operator|.
name|getName
argument_list|()
argument_list|,
name|icon
argument_list|)
expr_stmt|;
block|}
name|SpecialFieldViewModel
name|relevanceViewModel
init|=
operator|new
name|SpecialFieldViewModel
argument_list|(
name|SpecialField
operator|.
name|RELEVANCE
argument_list|,
name|undoManager
argument_list|)
decl_stmt|;
name|icon
operator|=
name|relevanceViewModel
operator|.
name|getIcon
argument_list|()
expr_stmt|;
comment|//icon.setToolTipText(relevanceViewModel.getLocalization());
name|TABLE_ICONS
operator|.
name|put
argument_list|(
name|SpecialField
operator|.
name|RELEVANCE
operator|.
name|getFieldName
argument_list|()
argument_list|,
name|icon
argument_list|)
expr_stmt|;
name|SpecialFieldViewModel
name|qualityViewModel
init|=
operator|new
name|SpecialFieldViewModel
argument_list|(
name|SpecialField
operator|.
name|QUALITY
argument_list|,
name|undoManager
argument_list|)
decl_stmt|;
name|icon
operator|=
name|qualityViewModel
operator|.
name|getIcon
argument_list|()
expr_stmt|;
comment|//icon.setToolTipText(qualityViewModel.getLocalization());
name|TABLE_ICONS
operator|.
name|put
argument_list|(
name|SpecialField
operator|.
name|QUALITY
operator|.
name|getFieldName
argument_list|()
argument_list|,
name|icon
argument_list|)
expr_stmt|;
comment|// Ranking item in the menu uses one star
name|SpecialFieldViewModel
name|rankViewModel
init|=
operator|new
name|SpecialFieldViewModel
argument_list|(
name|SpecialField
operator|.
name|RANKING
argument_list|,
name|undoManager
argument_list|)
decl_stmt|;
name|icon
operator|=
name|rankViewModel
operator|.
name|getIcon
argument_list|()
expr_stmt|;
comment|//icon.setToolTipText(rankViewModel.getLocalization());
name|TABLE_ICONS
operator|.
name|put
argument_list|(
name|SpecialField
operator|.
name|RANKING
operator|.
name|getFieldName
argument_list|()
argument_list|,
name|icon
argument_list|)
expr_stmt|;
comment|// Priority icon used for the menu
name|SpecialFieldViewModel
name|priorityViewModel
init|=
operator|new
name|SpecialFieldViewModel
argument_list|(
name|SpecialField
operator|.
name|PRIORITY
argument_list|,
name|undoManager
argument_list|)
decl_stmt|;
name|icon
operator|=
name|priorityViewModel
operator|.
name|getIcon
argument_list|()
expr_stmt|;
comment|//icon.setToolTipText(priorityViewModel.getLocalization());
name|TABLE_ICONS
operator|.
name|put
argument_list|(
name|SpecialField
operator|.
name|PRIORITY
operator|.
name|getFieldName
argument_list|()
argument_list|,
name|icon
argument_list|)
expr_stmt|;
comment|// Read icon used for menu
name|SpecialFieldViewModel
name|readViewModel
init|=
operator|new
name|SpecialFieldViewModel
argument_list|(
name|SpecialField
operator|.
name|READ_STATUS
argument_list|,
name|undoManager
argument_list|)
decl_stmt|;
name|icon
operator|=
name|readViewModel
operator|.
name|getIcon
argument_list|()
expr_stmt|;
comment|//icon.setToolTipText(readViewModel.getLocalization());
name|TABLE_ICONS
operator|.
name|put
argument_list|(
name|SpecialField
operator|.
name|READ_STATUS
operator|.
name|getFieldName
argument_list|()
argument_list|,
name|icon
argument_list|)
expr_stmt|;
comment|// Print icon used for menu
name|SpecialFieldViewModel
name|printedViewModel
init|=
operator|new
name|SpecialFieldViewModel
argument_list|(
name|SpecialField
operator|.
name|PRINTED
argument_list|,
name|undoManager
argument_list|)
decl_stmt|;
name|icon
operator|=
name|printedViewModel
operator|.
name|getIcon
argument_list|()
expr_stmt|;
comment|//icon.setToolTipText(printedViewModel.getLocalization());
name|TABLE_ICONS
operator|.
name|put
argument_list|(
name|SpecialField
operator|.
name|PRINTED
operator|.
name|getFieldName
argument_list|()
argument_list|,
name|icon
argument_list|)
expr_stmt|;
block|}
DECL|method|getTableIcon (String fieldType)
specifier|public
name|Node
name|getTableIcon
parameter_list|(
name|String
name|fieldType
parameter_list|)
block|{
if|if
condition|(
name|StringUtil
operator|.
name|isBlank
argument_list|(
name|fieldType
argument_list|)
condition|)
block|{
return|return
literal|null
return|;
block|}
name|JabRefIcon
name|icon
init|=
name|TABLE_ICONS
operator|.
name|get
argument_list|(
name|fieldType
argument_list|)
decl_stmt|;
if|if
condition|(
name|icon
operator|==
literal|null
condition|)
block|{
comment|//LOGGER.info("Error: no table icon defined for type '" + fieldType + "'.");
return|return
literal|null
return|;
block|}
else|else
block|{
comment|// node should be generated for each call, as nodes can be added to the scene graph only once
return|return
name|icon
operator|.
name|getGraphicNode
argument_list|()
return|;
block|}
block|}
block|}
end_class

end_unit

