begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.exporter
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|exporter
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|ListProperty
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|SimpleListProperty
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|collections
operator|.
name|FXCollections
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
name|AbstractViewModel
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
name|DialogService
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
name|exporter
operator|.
name|TemplateExporter
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
name|journals
operator|.
name|JournalAbbreviationLoader
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|preferences
operator|.
name|PreferencesService
import|;
end_import

begin_class
DECL|class|ExportCustomizationDialogViewModel
specifier|public
class|class
name|ExportCustomizationDialogViewModel
extends|extends
name|AbstractViewModel
block|{
DECL|field|exporters
specifier|private
specifier|final
name|ListProperty
argument_list|<
name|ExporterViewModel
argument_list|>
name|exporters
init|=
operator|new
name|SimpleListProperty
argument_list|<>
argument_list|(
name|FXCollections
operator|.
name|observableArrayList
argument_list|()
argument_list|)
decl_stmt|;
DECL|field|selectedExporters
specifier|private
specifier|final
name|ListProperty
argument_list|<
name|ExporterViewModel
argument_list|>
name|selectedExporters
init|=
operator|new
name|SimpleListProperty
argument_list|<>
argument_list|(
name|FXCollections
operator|.
name|observableArrayList
argument_list|()
argument_list|)
decl_stmt|;
DECL|field|preferences
specifier|private
specifier|final
name|PreferencesService
name|preferences
decl_stmt|;
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
decl_stmt|;
DECL|field|loader
specifier|private
specifier|final
name|JournalAbbreviationLoader
name|loader
decl_stmt|;
DECL|method|ExportCustomizationDialogViewModel (PreferencesService preferences, DialogService dialogService, JournalAbbreviationLoader loader)
specifier|public
name|ExportCustomizationDialogViewModel
parameter_list|(
name|PreferencesService
name|preferences
parameter_list|,
name|DialogService
name|dialogService
parameter_list|,
name|JournalAbbreviationLoader
name|loader
parameter_list|)
block|{
name|this
operator|.
name|preferences
operator|=
name|preferences
expr_stmt|;
name|this
operator|.
name|dialogService
operator|=
name|dialogService
expr_stmt|;
name|this
operator|.
name|loader
operator|=
name|loader
expr_stmt|;
name|loadExporters
argument_list|()
expr_stmt|;
block|}
DECL|method|loadExporters ()
specifier|private
name|void
name|loadExporters
parameter_list|()
block|{
name|List
argument_list|<
name|TemplateExporter
argument_list|>
name|exportersLogic
init|=
name|preferences
operator|.
name|getCustomExportFormats
argument_list|(
name|loader
argument_list|)
decl_stmt|;
for|for
control|(
name|TemplateExporter
name|exporter
range|:
name|exportersLogic
control|)
block|{
name|exporters
operator|.
name|add
argument_list|(
operator|new
name|ExporterViewModel
argument_list|(
name|exporter
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|addExporter ()
specifier|public
name|void
name|addExporter
parameter_list|()
block|{
name|CreateModifyExporterDialogView
name|dialog
init|=
operator|new
name|CreateModifyExporterDialogView
argument_list|(
literal|null
argument_list|,
name|dialogService
argument_list|,
name|preferences
argument_list|,
name|loader
argument_list|)
decl_stmt|;
name|Optional
argument_list|<
name|ExporterViewModel
argument_list|>
name|exporter
init|=
name|dialogService
operator|.
name|showCustomDialogAndWait
argument_list|(
name|dialog
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|exporter
operator|!=
literal|null
operator|)
operator|&&
name|exporter
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|exporters
operator|.
name|add
argument_list|(
name|exporter
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|modifyExporter ()
specifier|public
name|void
name|modifyExporter
parameter_list|()
block|{
name|CreateModifyExporterDialogView
name|dialog
decl_stmt|;
name|ExporterViewModel
name|exporterToModify
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|selectedExporters
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return;
block|}
name|exporterToModify
operator|=
name|selectedExporters
operator|.
name|get
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|dialog
operator|=
operator|new
name|CreateModifyExporterDialogView
argument_list|(
name|exporterToModify
argument_list|,
name|dialogService
argument_list|,
name|preferences
argument_list|,
name|loader
argument_list|)
expr_stmt|;
name|Optional
argument_list|<
name|ExporterViewModel
argument_list|>
name|exporter
init|=
name|dialogService
operator|.
name|showCustomDialogAndWait
argument_list|(
name|dialog
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|exporter
operator|!=
literal|null
operator|)
operator|&&
name|exporter
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|exporters
operator|.
name|remove
argument_list|(
name|exporterToModify
argument_list|)
expr_stmt|;
name|exporters
operator|.
name|add
argument_list|(
name|exporter
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|removeExporters ()
specifier|public
name|void
name|removeExporters
parameter_list|()
block|{
name|exporters
operator|.
name|removeAll
argument_list|(
name|selectedExporters
argument_list|)
expr_stmt|;
block|}
DECL|method|saveToPrefs ()
specifier|public
name|void
name|saveToPrefs
parameter_list|()
block|{
name|List
argument_list|<
name|TemplateExporter
argument_list|>
name|exportersLogic
init|=
name|exporters
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|ExporterViewModel
operator|::
name|getLogic
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
decl_stmt|;
name|preferences
operator|.
name|storeCustomExportFormats
argument_list|(
name|exportersLogic
argument_list|)
expr_stmt|;
block|}
DECL|method|selectedExportersProperty ()
specifier|public
name|ListProperty
argument_list|<
name|ExporterViewModel
argument_list|>
name|selectedExportersProperty
parameter_list|()
block|{
return|return
name|selectedExporters
return|;
block|}
DECL|method|exportersProperty ()
specifier|public
name|ListProperty
argument_list|<
name|ExporterViewModel
argument_list|>
name|exportersProperty
parameter_list|()
block|{
return|return
name|exporters
return|;
block|}
block|}
end_class

end_unit

