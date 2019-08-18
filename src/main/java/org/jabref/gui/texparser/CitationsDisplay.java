begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.texparser
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|texparser
package|;
end_package

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Path
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

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
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|ObjectProperty
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
name|SimpleObjectProperty
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
name|control
operator|.
name|ContentDisplay
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
name|layout
operator|.
name|VBox
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
name|javafx
operator|.
name|scene
operator|.
name|text
operator|.
name|TextFlow
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
name|model
operator|.
name|strings
operator|.
name|LatexToUnicodeAdapter
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
name|texparser
operator|.
name|Citation
import|;
end_import

begin_class
DECL|class|CitationsDisplay
specifier|public
class|class
name|CitationsDisplay
extends|extends
name|ListView
argument_list|<
name|Citation
argument_list|>
block|{
DECL|field|basePath
specifier|private
specifier|final
name|ObjectProperty
argument_list|<
name|Path
argument_list|>
name|basePath
decl_stmt|;
DECL|method|CitationsDisplay ()
specifier|public
name|CitationsDisplay
parameter_list|()
block|{
name|this
operator|.
name|basePath
operator|=
operator|new
name|SimpleObjectProperty
argument_list|<>
argument_list|(
literal|null
argument_list|)
expr_stmt|;
operator|new
name|ViewModelListCellFactory
argument_list|<
name|Citation
argument_list|>
argument_list|()
operator|.
name|withGraphic
argument_list|(
name|this
operator|::
name|getDisplayGraphic
argument_list|)
operator|.
name|withTooltip
argument_list|(
name|this
operator|::
name|getDisplayTooltip
argument_list|)
operator|.
name|install
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
DECL|method|basePathProperty ()
specifier|public
name|ObjectProperty
argument_list|<
name|Path
argument_list|>
name|basePathProperty
parameter_list|()
block|{
return|return
name|basePath
return|;
block|}
DECL|method|getDisplayGraphic (Citation item)
specifier|private
name|Node
name|getDisplayGraphic
parameter_list|(
name|Citation
name|item
parameter_list|)
block|{
if|if
condition|(
name|basePath
operator|.
name|get
argument_list|()
operator|==
literal|null
condition|)
block|{
name|basePath
operator|.
name|set
argument_list|(
name|item
operator|.
name|getPath
argument_list|()
operator|.
name|getRoot
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|Node
name|citationIcon
init|=
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|LATEX_COMMENT
operator|.
name|getGraphicNode
argument_list|()
decl_stmt|;
name|Text
name|contextText
init|=
operator|new
name|Text
argument_list|(
name|LatexToUnicodeAdapter
operator|.
name|format
argument_list|(
name|item
operator|.
name|getContext
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|contextText
operator|.
name|wrappingWidthProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|this
operator|.
name|widthProperty
argument_list|()
operator|.
name|subtract
argument_list|(
literal|85
argument_list|)
argument_list|)
expr_stmt|;
name|HBox
name|contextBox
init|=
operator|new
name|HBox
argument_list|(
literal|8
argument_list|,
name|citationIcon
argument_list|,
name|contextText
argument_list|)
decl_stmt|;
name|contextBox
operator|.
name|setStyle
argument_list|(
literal|"-fx-border-color: grey;-fx-border-insets: 5;-fx-border-style: dashed;-fx-border-width: 2;-fx-padding: 12;"
argument_list|)
expr_stmt|;
name|Label
name|fileNameLabel
init|=
operator|new
name|Label
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"%s"
argument_list|,
name|basePath
operator|.
name|get
argument_list|()
operator|.
name|relativize
argument_list|(
name|item
operator|.
name|getPath
argument_list|()
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|fileNameLabel
operator|.
name|setStyle
argument_list|(
literal|"-fx-font-family: 'Courier New', Courier, monospace;-fx-font-weight: bold;-fx-label-padding: 5 0 10 10;"
argument_list|)
expr_stmt|;
name|fileNameLabel
operator|.
name|setGraphic
argument_list|(
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|LATEX_FILE
operator|.
name|getGraphicNode
argument_list|()
argument_list|)
expr_stmt|;
name|Label
name|positionLabel
init|=
operator|new
name|Label
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"(%s:%s-%s)"
argument_list|,
name|item
operator|.
name|getLine
argument_list|()
argument_list|,
name|item
operator|.
name|getColStart
argument_list|()
argument_list|,
name|item
operator|.
name|getColEnd
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|positionLabel
operator|.
name|setStyle
argument_list|(
literal|"-fx-font-family: 'Courier New', Courier, monospace;-fx-font-weight: bold;-fx-label-padding: 5 0 10 10;"
argument_list|)
expr_stmt|;
name|positionLabel
operator|.
name|setGraphic
argument_list|(
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|LATEX_LINE
operator|.
name|getGraphicNode
argument_list|()
argument_list|)
expr_stmt|;
name|HBox
name|dataBox
init|=
operator|new
name|HBox
argument_list|(
literal|5
argument_list|,
name|fileNameLabel
argument_list|,
name|positionLabel
argument_list|)
decl_stmt|;
return|return
operator|new
name|VBox
argument_list|(
name|contextBox
argument_list|,
name|dataBox
argument_list|)
return|;
block|}
DECL|method|getDisplayTooltip (Citation item)
specifier|private
name|Tooltip
name|getDisplayTooltip
parameter_list|(
name|Citation
name|item
parameter_list|)
block|{
name|String
name|line
init|=
name|item
operator|.
name|getLineText
argument_list|()
decl_stmt|;
name|int
name|start
init|=
name|item
operator|.
name|getColStart
argument_list|()
decl_stmt|;
name|int
name|end
init|=
name|item
operator|.
name|getColEnd
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|Text
argument_list|>
name|texts
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
literal|3
argument_list|)
decl_stmt|;
comment|// Text before the citation.
if|if
condition|(
name|start
operator|>
literal|0
condition|)
block|{
name|texts
operator|.
name|add
argument_list|(
operator|new
name|Text
argument_list|(
name|line
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|start
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Citation text (highlighted).
name|Text
name|citation
init|=
operator|new
name|Text
argument_list|(
name|line
operator|.
name|substring
argument_list|(
name|start
argument_list|,
name|end
argument_list|)
argument_list|)
decl_stmt|;
name|citation
operator|.
name|getStyleClass
argument_list|()
operator|.
name|setAll
argument_list|(
literal|"tooltip-text-bold"
argument_list|)
expr_stmt|;
name|texts
operator|.
name|add
argument_list|(
name|citation
argument_list|)
expr_stmt|;
comment|// Text after the citation.
if|if
condition|(
name|end
operator|<
name|line
operator|.
name|length
argument_list|()
condition|)
block|{
name|texts
operator|.
name|add
argument_list|(
operator|new
name|Text
argument_list|(
name|line
operator|.
name|substring
argument_list|(
name|end
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|Tooltip
name|tooltip
init|=
operator|new
name|Tooltip
argument_list|()
decl_stmt|;
name|tooltip
operator|.
name|setContentDisplay
argument_list|(
name|ContentDisplay
operator|.
name|GRAPHIC_ONLY
argument_list|)
expr_stmt|;
name|tooltip
operator|.
name|setGraphic
argument_list|(
operator|new
name|TextFlow
argument_list|(
name|texts
operator|.
name|toArray
argument_list|(
operator|new
name|Text
index|[
literal|0
index|]
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|tooltip
operator|.
name|setMaxHeight
argument_list|(
literal|10
argument_list|)
expr_stmt|;
name|tooltip
operator|.
name|setMinWidth
argument_list|(
literal|200
argument_list|)
expr_stmt|;
name|tooltip
operator|.
name|maxWidthProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|this
operator|.
name|widthProperty
argument_list|()
operator|.
name|subtract
argument_list|(
literal|85
argument_list|)
argument_list|)
expr_stmt|;
name|tooltip
operator|.
name|setWrapText
argument_list|(
literal|true
argument_list|)
expr_stmt|;
return|return
name|tooltip
return|;
block|}
block|}
end_class

end_unit
