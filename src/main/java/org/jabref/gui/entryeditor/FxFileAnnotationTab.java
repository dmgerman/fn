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
name|Map
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
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|ComboBox
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
name|Label
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
name|ScrollPane
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
name|ColumnConstraints
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
name|GridPane
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
name|Region
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
name|gui
operator|.
name|fieldeditors
operator|.
name|FieldEditorFX
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
name|logic
operator|.
name|pdf
operator|.
name|FileAnnotationCache
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
name|pdf
operator|.
name|FileAnnotation
import|;
end_import

begin_class
DECL|class|FxFileAnnotationTab
specifier|public
class|class
name|FxFileAnnotationTab
extends|extends
name|EntryEditorTab
block|{
DECL|field|panel
specifier|private
specifier|final
name|Region
name|panel
decl_stmt|;
DECL|field|parent
specifier|private
specifier|final
name|EntryEditor
name|parent
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|basePanel
specifier|private
specifier|final
name|BasePanel
name|basePanel
decl_stmt|;
DECL|field|cache
specifier|private
specifier|final
name|FileAnnotationCache
name|cache
decl_stmt|;
DECL|field|activeField
specifier|private
name|FieldEditorFX
name|activeField
decl_stmt|;
DECL|method|FxFileAnnotationTab (JabRefFrame frame, BasePanel basePanel, EntryEditor parent, FileAnnotationCache cache)
specifier|public
name|FxFileAnnotationTab
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|BasePanel
name|basePanel
parameter_list|,
name|EntryEditor
name|parent
parameter_list|,
name|FileAnnotationCache
name|cache
parameter_list|)
block|{
name|this
operator|.
name|parent
operator|=
name|parent
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|this
operator|.
name|basePanel
operator|=
name|basePanel
expr_stmt|;
name|this
operator|.
name|cache
operator|=
name|cache
expr_stmt|;
name|this
operator|.
name|panel
operator|=
name|setupPanel
argument_list|(
name|frame
argument_list|,
name|basePanel
argument_list|)
expr_stmt|;
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"File annotations"
argument_list|)
argument_list|)
expr_stmt|;
comment|// TODO: rename in "File annotations"
name|setTooltip
argument_list|(
operator|new
name|Tooltip
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Show file annotations"
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
name|REQUIRED
operator|.
name|getGraphicNode
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|setupPanel (JabRefFrame frame, BasePanel basePanel)
specifier|private
name|Region
name|setupPanel
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|BasePanel
name|basePanel
parameter_list|)
block|{
name|GridPane
name|gridPane
init|=
operator|new
name|GridPane
argument_list|()
decl_stmt|;
name|gridPane
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"editorPane"
argument_list|)
expr_stmt|;
name|ColumnConstraints
name|leftSideConstraint
init|=
operator|new
name|ColumnConstraints
argument_list|()
decl_stmt|;
name|leftSideConstraint
operator|.
name|setPercentWidth
argument_list|(
literal|50
argument_list|)
expr_stmt|;
name|gridPane
operator|.
name|getColumnConstraints
argument_list|()
operator|.
name|addAll
argument_list|(
name|leftSideConstraint
argument_list|)
expr_stmt|;
name|gridPane
operator|.
name|addColumn
argument_list|(
literal|0
argument_list|,
name|setupLeftSide
argument_list|()
argument_list|)
expr_stmt|;
name|gridPane
operator|.
name|addColumn
argument_list|(
literal|1
argument_list|,
name|setupRightSide
argument_list|()
argument_list|)
expr_stmt|;
comment|// Warp everything in a scroll-pane
name|ScrollPane
name|scrollPane
init|=
operator|new
name|ScrollPane
argument_list|()
decl_stmt|;
name|scrollPane
operator|.
name|setHbarPolicy
argument_list|(
name|ScrollPane
operator|.
name|ScrollBarPolicy
operator|.
name|NEVER
argument_list|)
expr_stmt|;
name|scrollPane
operator|.
name|setVbarPolicy
argument_list|(
name|ScrollPane
operator|.
name|ScrollBarPolicy
operator|.
name|AS_NEEDED
argument_list|)
expr_stmt|;
name|scrollPane
operator|.
name|setContent
argument_list|(
name|gridPane
argument_list|)
expr_stmt|;
name|scrollPane
operator|.
name|setFitToWidth
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|scrollPane
operator|.
name|setFitToHeight
argument_list|(
literal|true
argument_list|)
expr_stmt|;
return|return
name|scrollPane
return|;
block|}
DECL|method|setupRightSide ()
specifier|private
name|GridPane
name|setupRightSide
parameter_list|()
block|{
name|GridPane
name|rightSide
init|=
operator|new
name|GridPane
argument_list|()
decl_stmt|;
name|rightSide
operator|.
name|addRow
argument_list|(
literal|0
argument_list|,
operator|new
name|Label
argument_list|(
literal|"Author"
argument_list|)
argument_list|)
expr_stmt|;
name|rightSide
operator|.
name|addRow
argument_list|(
literal|1
argument_list|,
operator|new
name|Label
argument_list|(
literal|"date"
argument_list|)
argument_list|)
expr_stmt|;
name|rightSide
operator|.
name|addRow
argument_list|(
literal|2
argument_list|,
operator|new
name|Label
argument_list|(
literal|"page"
argument_list|)
argument_list|)
expr_stmt|;
name|rightSide
operator|.
name|addRow
argument_list|(
literal|3
argument_list|,
operator|new
name|Label
argument_list|(
literal|"content"
argument_list|)
argument_list|)
expr_stmt|;
name|rightSide
operator|.
name|addRow
argument_list|(
literal|4
argument_list|,
operator|new
name|Label
argument_list|(
literal|"highlight"
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|rightSide
return|;
block|}
DECL|method|setupLeftSide ()
specifier|private
name|GridPane
name|setupLeftSide
parameter_list|()
block|{
name|GridPane
name|leftSide
init|=
operator|new
name|GridPane
argument_list|()
decl_stmt|;
name|leftSide
operator|.
name|addColumn
argument_list|(
literal|0
argument_list|,
operator|new
name|Label
argument_list|(
literal|"Filename"
argument_list|)
argument_list|)
expr_stmt|;
name|leftSide
operator|.
name|addRow
argument_list|(
literal|0
argument_list|,
name|createFileNameComboBox
argument_list|()
argument_list|)
expr_stmt|;
name|leftSide
operator|.
name|addRow
argument_list|(
literal|1
argument_list|,
operator|new
name|Label
argument_list|(
literal|"AnnotationsList"
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|leftSide
return|;
block|}
DECL|method|createFileNameComboBox ()
specifier|private
name|ComboBox
argument_list|<
name|String
argument_list|>
name|createFileNameComboBox
parameter_list|()
block|{
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|FileAnnotation
argument_list|>
argument_list|>
name|fileAnnotations
init|=
name|cache
operator|.
name|getFromCache
argument_list|(
name|parent
operator|.
name|getEntry
argument_list|()
argument_list|)
decl_stmt|;
name|ComboBox
argument_list|<
name|String
argument_list|>
name|comboBox
init|=
operator|new
name|ComboBox
argument_list|<>
argument_list|(
name|FXCollections
operator|.
name|observableArrayList
argument_list|(
name|fileAnnotations
operator|.
name|keySet
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|comboBox
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|selectFirst
argument_list|()
expr_stmt|;
return|return
name|comboBox
return|;
block|}
annotation|@
name|Override
DECL|method|shouldShow ()
specifier|public
name|boolean
name|shouldShow
parameter_list|()
block|{
return|return
name|parent
operator|.
name|getEntry
argument_list|()
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|FILE
argument_list|)
operator|.
name|isPresent
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|requestFocus ()
specifier|public
name|void
name|requestFocus
parameter_list|()
block|{
if|if
condition|(
name|activeField
operator|!=
literal|null
condition|)
block|{
name|activeField
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|initialize ()
specifier|protected
name|void
name|initialize
parameter_list|()
block|{
name|setContent
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

