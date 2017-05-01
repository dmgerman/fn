begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.fieldeditors
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|fieldeditors
package|;
end_package

begin_import
import|import
name|javafx
operator|.
name|event
operator|.
name|ActionEvent
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|fxml
operator|.
name|FXML
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
name|javafx
operator|.
name|scene
operator|.
name|Parent
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|Button
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|ListView
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|ProgressBar
import|;
end_import

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
name|javafx
operator|.
name|scene
operator|.
name|layout
operator|.
name|HBox
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|text
operator|.
name|Text
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
name|gui
operator|.
name|util
operator|.
name|ControlHelper
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
name|util
operator|.
name|TaskExecutor
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
name|util
operator|.
name|ViewModelListCellFactory
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
name|BibDatabaseContext
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
name|de
operator|.
name|jensd
operator|.
name|fx
operator|.
name|glyphs
operator|.
name|materialdesignicons
operator|.
name|MaterialDesignIcon
import|;
end_import

begin_import
import|import
name|de
operator|.
name|jensd
operator|.
name|fx
operator|.
name|glyphs
operator|.
name|materialdesignicons
operator|.
name|utils
operator|.
name|MaterialDesignIconFactory
import|;
end_import

begin_class
DECL|class|LinkedFilesEditor
specifier|public
class|class
name|LinkedFilesEditor
extends|extends
name|HBox
implements|implements
name|FieldEditorFX
block|{
DECL|field|fieldName
specifier|private
specifier|final
name|String
name|fieldName
decl_stmt|;
DECL|field|viewModel
annotation|@
name|FXML
specifier|private
name|LinkedFilesEditorViewModel
name|viewModel
decl_stmt|;
DECL|field|listView
annotation|@
name|FXML
specifier|private
name|ListView
argument_list|<
name|LinkedFileViewModel
argument_list|>
name|listView
decl_stmt|;
DECL|method|LinkedFilesEditor (String fieldName, DialogService dialogService, BibDatabaseContext databaseContext, TaskExecutor taskExecutor)
specifier|public
name|LinkedFilesEditor
parameter_list|(
name|String
name|fieldName
parameter_list|,
name|DialogService
name|dialogService
parameter_list|,
name|BibDatabaseContext
name|databaseContext
parameter_list|,
name|TaskExecutor
name|taskExecutor
parameter_list|)
block|{
name|this
operator|.
name|fieldName
operator|=
name|fieldName
expr_stmt|;
name|this
operator|.
name|viewModel
operator|=
operator|new
name|LinkedFilesEditorViewModel
argument_list|(
name|dialogService
argument_list|,
name|databaseContext
argument_list|,
name|taskExecutor
argument_list|)
expr_stmt|;
name|ControlHelper
operator|.
name|loadFXMLForControl
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|ViewModelListCellFactory
argument_list|<
name|LinkedFileViewModel
argument_list|>
name|cellFactory
init|=
operator|new
name|ViewModelListCellFactory
argument_list|<
name|LinkedFileViewModel
argument_list|>
argument_list|()
operator|.
name|withTooltip
argument_list|(
name|LinkedFileViewModel
operator|::
name|getDescription
argument_list|)
operator|.
name|withGraphic
argument_list|(
name|LinkedFilesEditor
operator|::
name|createFileDisplay
argument_list|)
decl_stmt|;
name|listView
operator|.
name|setCellFactory
argument_list|(
name|cellFactory
argument_list|)
expr_stmt|;
name|listView
operator|.
name|itemsProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|viewModel
operator|.
name|filesProperty
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|createFileDisplay (LinkedFileViewModel linkedFile)
specifier|private
specifier|static
name|Node
name|createFileDisplay
parameter_list|(
name|LinkedFileViewModel
name|linkedFile
parameter_list|)
block|{
name|Text
name|icon
init|=
name|MaterialDesignIconFactory
operator|.
name|get
argument_list|()
operator|.
name|createIcon
argument_list|(
name|linkedFile
operator|.
name|getTypeIcon
argument_list|()
argument_list|)
decl_stmt|;
name|Text
name|text
init|=
operator|new
name|Text
argument_list|(
name|linkedFile
operator|.
name|getLink
argument_list|()
argument_list|)
decl_stmt|;
name|ProgressBar
name|progressIndicator
init|=
operator|new
name|ProgressBar
argument_list|()
decl_stmt|;
name|progressIndicator
operator|.
name|progressProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|linkedFile
operator|.
name|downloadProgressProperty
argument_list|()
argument_list|)
expr_stmt|;
name|progressIndicator
operator|.
name|visibleProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|linkedFile
operator|.
name|downloadOngoingProperty
argument_list|()
argument_list|)
expr_stmt|;
name|Button
name|acceptAutoLinkedFile
init|=
name|MaterialDesignIconFactory
operator|.
name|get
argument_list|()
operator|.
name|createIconButton
argument_list|(
name|MaterialDesignIcon
operator|.
name|BRIEFCASE_CHECK
argument_list|)
decl_stmt|;
name|acceptAutoLinkedFile
operator|.
name|setTooltip
argument_list|(
operator|new
name|Tooltip
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"This file was found automatically. Do you want to link it to this entry?"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|acceptAutoLinkedFile
operator|.
name|visibleProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|linkedFile
operator|.
name|isAutomaticallyFoundProperty
argument_list|()
argument_list|)
expr_stmt|;
name|acceptAutoLinkedFile
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|linkedFile
operator|.
name|acceptAsLinked
argument_list|()
argument_list|)
expr_stmt|;
name|acceptAutoLinkedFile
operator|.
name|getStyleClass
argument_list|()
operator|.
name|setAll
argument_list|(
literal|"flatButton"
argument_list|)
expr_stmt|;
name|HBox
name|container
init|=
operator|new
name|HBox
argument_list|(
literal|10
argument_list|)
decl_stmt|;
name|container
operator|.
name|setPrefHeight
argument_list|(
name|Double
operator|.
name|NEGATIVE_INFINITY
argument_list|)
expr_stmt|;
name|container
operator|.
name|getChildren
argument_list|()
operator|.
name|addAll
argument_list|(
name|icon
argument_list|,
name|text
argument_list|,
name|progressIndicator
argument_list|,
name|acceptAutoLinkedFile
argument_list|)
expr_stmt|;
return|return
name|container
return|;
block|}
DECL|method|getViewModel ()
specifier|public
name|LinkedFilesEditorViewModel
name|getViewModel
parameter_list|()
block|{
return|return
name|viewModel
return|;
block|}
annotation|@
name|Override
DECL|method|bindToEntry (BibEntry entry)
specifier|public
name|void
name|bindToEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
name|viewModel
operator|.
name|bindToEntry
argument_list|(
name|fieldName
argument_list|,
name|entry
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getNode ()
specifier|public
name|Parent
name|getNode
parameter_list|()
block|{
return|return
name|this
return|;
block|}
annotation|@
name|FXML
DECL|method|addNewFile (ActionEvent event)
specifier|private
name|void
name|addNewFile
parameter_list|(
name|ActionEvent
name|event
parameter_list|)
block|{
name|viewModel
operator|.
name|addNewFile
argument_list|()
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|fetchFulltext (ActionEvent event)
specifier|private
name|void
name|fetchFulltext
parameter_list|(
name|ActionEvent
name|event
parameter_list|)
block|{
name|viewModel
operator|.
name|fetchFulltext
argument_list|()
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|addFromURL (ActionEvent event)
specifier|private
name|void
name|addFromURL
parameter_list|(
name|ActionEvent
name|event
parameter_list|)
block|{
name|viewModel
operator|.
name|addFromURL
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

