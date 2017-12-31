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
name|Node
argument_list|>
name|TABLE_ICONS
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
DECL|method|CellFactory ()
specifier|public
name|CellFactory
parameter_list|()
block|{
name|Node
name|label
decl_stmt|;
name|label
operator|=
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|PDF_FILE
operator|.
name|getGraphicNode
argument_list|()
expr_stmt|;
comment|//label.setToo(Localization.lang("Open") + " PDF");
name|TABLE_ICONS
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|PDF
argument_list|,
name|label
argument_list|)
expr_stmt|;
name|label
operator|=
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|WWW
operator|.
name|getGraphicNode
argument_list|()
expr_stmt|;
comment|//label.setToolTipText(Localization.lang("Open") + " URL");
name|TABLE_ICONS
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|URL
argument_list|,
name|label
argument_list|)
expr_stmt|;
name|label
operator|=
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|WWW
operator|.
name|getGraphicNode
argument_list|()
expr_stmt|;
comment|//label.setToolTipText(Localization.lang("Open") + " CiteSeer URL");
name|TABLE_ICONS
operator|.
name|put
argument_list|(
literal|"citeseerurl"
argument_list|,
name|label
argument_list|)
expr_stmt|;
name|label
operator|=
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|WWW
operator|.
name|getGraphicNode
argument_list|()
expr_stmt|;
comment|//label.setToolTipText(Localization.lang("Open") + " ArXiv URL");
name|TABLE_ICONS
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|EPRINT
argument_list|,
name|label
argument_list|)
expr_stmt|;
name|label
operator|=
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|DOI
operator|.
name|getGraphicNode
argument_list|()
expr_stmt|;
comment|//label.setToolTipText(Localization.lang("Open") + " DOI " + Localization.lang("web link"));
name|TABLE_ICONS
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|DOI
argument_list|,
name|label
argument_list|)
expr_stmt|;
name|label
operator|=
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|FILE
operator|.
name|getGraphicNode
argument_list|()
expr_stmt|;
comment|//label.setToolTipText(Localization.lang("Open") + " PS");
name|TABLE_ICONS
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|PS
argument_list|,
name|label
argument_list|)
expr_stmt|;
name|label
operator|=
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|FOLDER
operator|.
name|getGraphicNode
argument_list|()
expr_stmt|;
comment|//label.setToolTipText(Localization.lang("Open folder"));
name|TABLE_ICONS
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|FOLDER
argument_list|,
name|label
argument_list|)
expr_stmt|;
name|label
operator|=
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|FILE
operator|.
name|getGraphicNode
argument_list|()
expr_stmt|;
comment|//label.setToolTipText(Localization.lang("Open file"));
name|TABLE_ICONS
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|FILE
argument_list|,
name|label
argument_list|)
expr_stmt|;
for|for
control|(
name|ExternalFileType
name|fileType
range|:
name|ExternalFileTypes
operator|.
name|getInstance
argument_list|()
operator|.
name|getExternalFileTypeSelection
argument_list|()
control|)
block|{
name|label
operator|=
name|fileType
operator|.
name|getIcon
argument_list|()
operator|.
name|getGraphicNode
argument_list|()
expr_stmt|;
comment|//label.setToolTipText(Localization.lang("Open %0 file", fileType.getName()));
name|TABLE_ICONS
operator|.
name|put
argument_list|(
name|fileType
operator|.
name|getName
argument_list|()
argument_list|,
name|label
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
argument_list|)
decl_stmt|;
name|label
operator|=
name|relevanceViewModel
operator|.
name|getIcon
argument_list|()
operator|.
name|getGraphicNode
argument_list|()
expr_stmt|;
comment|//label.setToolTipText(relevanceViewModel.getLocalization());
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
name|label
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
argument_list|)
decl_stmt|;
name|label
operator|=
name|qualityViewModel
operator|.
name|getIcon
argument_list|()
operator|.
name|getGraphicNode
argument_list|()
expr_stmt|;
comment|//label.setToolTipText(qualityViewModel.getLocalization());
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
name|label
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
argument_list|)
decl_stmt|;
name|label
operator|=
name|rankViewModel
operator|.
name|getIcon
argument_list|()
operator|.
name|getGraphicNode
argument_list|()
expr_stmt|;
comment|//label.setToolTipText(rankViewModel.getLocalization());
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
name|label
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
argument_list|)
decl_stmt|;
name|label
operator|=
name|priorityViewModel
operator|.
name|getIcon
argument_list|()
operator|.
name|getGraphicNode
argument_list|()
expr_stmt|;
comment|//label.setToolTipText(priorityViewModel.getLocalization());
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
name|label
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
argument_list|)
decl_stmt|;
name|label
operator|=
name|readViewModel
operator|.
name|getIcon
argument_list|()
operator|.
name|getGraphicNode
argument_list|()
expr_stmt|;
comment|//label.setToolTipText(readViewModel.getLocalization());
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
name|label
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
argument_list|)
decl_stmt|;
name|label
operator|=
name|printedViewModel
operator|.
name|getIcon
argument_list|()
operator|.
name|getGraphicNode
argument_list|()
expr_stmt|;
comment|//label.setToolTipText(printedViewModel.getLocalization());
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
name|label
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
name|Node
name|label
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
name|label
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
return|return
name|label
return|;
block|}
block|}
block|}
end_class

end_unit

