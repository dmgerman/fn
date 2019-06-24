begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.preferences
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|preferences
package|;
end_package

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
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Comparator
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
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Matcher
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
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
name|BooleanProperty
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
name|SimpleBooleanProperty
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
name|collections
operator|.
name|FXCollections
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|collections
operator|.
name|ObservableList
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
name|MultipleSelectionModel
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|JabRefGUI
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
name|BackgroundTask
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
name|logic
operator|.
name|citationstyle
operator|.
name|CitationStyle
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
name|citationstyle
operator|.
name|CitationStylePreviewLayout
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
name|citationstyle
operator|.
name|PreviewLayout
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
name|citationstyle
operator|.
name|TextBasedPreviewLayout
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
name|preferences
operator|.
name|JabRefPreferences
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
name|PreviewPreferences
import|;
end_import

begin_import
import|import
name|org
operator|.
name|fxmisc
operator|.
name|richtext
operator|.
name|model
operator|.
name|StyleSpans
import|;
end_import

begin_import
import|import
name|org
operator|.
name|fxmisc
operator|.
name|richtext
operator|.
name|model
operator|.
name|StyleSpansBuilder
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_class
DECL|class|PreviewTabViewModel
specifier|public
class|class
name|PreviewTabViewModel
implements|implements
name|PreferenceTabViewModel
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Logger
name|LOGGER
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|PreviewTabViewModel
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|availableListProperty
specifier|private
specifier|final
name|ListProperty
argument_list|<
name|PreviewLayout
argument_list|>
name|availableListProperty
init|=
operator|new
name|SimpleListProperty
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|availableSelectionModelProperty
specifier|private
specifier|final
name|ObjectProperty
argument_list|<
name|MultipleSelectionModel
argument_list|<
name|PreviewLayout
argument_list|>
argument_list|>
name|availableSelectionModelProperty
init|=
operator|new
name|SimpleObjectProperty
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|chosenListProperty
specifier|private
specifier|final
name|ListProperty
argument_list|<
name|PreviewLayout
argument_list|>
name|chosenListProperty
init|=
operator|new
name|SimpleListProperty
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|chosenSelectionModelProperty
specifier|private
specifier|final
name|ObjectProperty
argument_list|<
name|MultipleSelectionModel
argument_list|<
name|PreviewLayout
argument_list|>
argument_list|>
name|chosenSelectionModelProperty
init|=
operator|new
name|SimpleObjectProperty
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|selectedIsEditableProperty
specifier|private
specifier|final
name|BooleanProperty
name|selectedIsEditableProperty
init|=
operator|new
name|SimpleBooleanProperty
argument_list|(
literal|false
argument_list|)
decl_stmt|;
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
decl_stmt|;
DECL|field|preferences
specifier|private
specifier|final
name|JabRefPreferences
name|preferences
decl_stmt|;
DECL|field|previewPreferences
specifier|private
specifier|final
name|PreviewPreferences
name|previewPreferences
decl_stmt|;
DECL|field|taskExecutor
specifier|private
specifier|final
name|TaskExecutor
name|taskExecutor
decl_stmt|;
DECL|method|PreviewTabViewModel (DialogService dialogService, JabRefPreferences preferences, TaskExecutor taskExecutor)
specifier|public
name|PreviewTabViewModel
parameter_list|(
name|DialogService
name|dialogService
parameter_list|,
name|JabRefPreferences
name|preferences
parameter_list|,
name|TaskExecutor
name|taskExecutor
parameter_list|)
block|{
name|this
operator|.
name|dialogService
operator|=
name|dialogService
expr_stmt|;
name|this
operator|.
name|preferences
operator|=
name|preferences
expr_stmt|;
name|this
operator|.
name|taskExecutor
operator|=
name|taskExecutor
expr_stmt|;
name|previewPreferences
operator|=
name|preferences
operator|.
name|getPreviewPreferences
argument_list|()
expr_stmt|;
name|setValues
argument_list|()
expr_stmt|;
block|}
DECL|method|setValues ()
specifier|public
name|void
name|setValues
parameter_list|()
block|{
specifier|final
name|ObservableList
argument_list|<
name|PreviewLayout
argument_list|>
name|availableLayouts
init|=
name|FXCollections
operator|.
name|observableArrayList
argument_list|()
decl_stmt|;
name|chosenListProperty
operator|.
name|setValue
argument_list|(
name|FXCollections
operator|.
name|observableArrayList
argument_list|(
name|previewPreferences
operator|.
name|getPreviewCycle
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|chosenListProperty
operator|.
name|stream
argument_list|()
operator|.
name|noneMatch
argument_list|(
name|layout
lambda|->
name|layout
operator|instanceof
name|TextBasedPreviewLayout
argument_list|)
condition|)
block|{
name|chosenListProperty
operator|.
name|add
argument_list|(
name|previewPreferences
operator|.
name|getTextBasedPreviewLayout
argument_list|()
argument_list|)
block|;         }
name|BackgroundTask
operator|.
name|wrap
argument_list|(
name|CitationStyle
operator|::
name|discoverCitationStyles
argument_list|)
operator|.
name|onSuccess
argument_list|(
name|value
lambda|->
name|value
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|CitationStylePreviewLayout
operator|::
operator|new
argument_list|)
operator|.
name|sorted
argument_list|(
name|Comparator
operator|.
name|comparing
argument_list|(
name|PreviewLayout
operator|::
name|getName
argument_list|)
argument_list|)
operator|.
name|filter
argument_list|(
name|style
lambda|->
operator|!
name|chosenListProperty
operator|.
name|contains
argument_list|(
name|style
argument_list|)
argument_list|)
operator|.
name|forEach
argument_list|(
name|availableLayouts
operator|::
name|add
argument_list|)
argument_list|)
comment|// does not accept a property, so this is using availableLayouts
operator|.
name|onFailure
argument_list|(
name|ex
lambda|->
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Something went wrong while adding the discovered CitationStyles to the list. "
argument_list|,
name|ex
argument_list|)
argument_list|;
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error adding discovered CitationStyles"
argument_list|)
argument_list|,
name|ex
argument_list|)
argument_list|;
block|}
block|)
operator|.
name|executeWith
argument_list|(
name|taskExecutor
argument_list|)
expr_stmt|;
end_class

begin_expr_stmt
name|availableListProperty
operator|.
name|setValue
argument_list|(
name|availableLayouts
argument_list|)
expr_stmt|;
end_expr_stmt

begin_comment
unit|}
comment|// ToDo: Does this really work as intended? Test!
end_comment

begin_function
DECL|method|findLayoutByNameOrDefault (String name)
unit|private
name|PreviewLayout
name|findLayoutByNameOrDefault
parameter_list|(
name|String
name|name
parameter_list|)
block|{
return|return
name|availableListProperty
operator|.
name|getValue
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|layout
lambda|->
name|layout
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|name
argument_list|)
argument_list|)
operator|.
name|findAny
argument_list|()
operator|.
name|orElse
argument_list|(
name|chosenListProperty
operator|.
name|getValue
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|layout
lambda|->
name|layout
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|name
argument_list|)
argument_list|)
operator|.
name|findAny
argument_list|()
operator|.
name|orElse
argument_list|(
name|previewPreferences
operator|.
name|getTextBasedPreviewLayout
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
end_function

begin_comment
comment|/**      * XML-Syntax-Highlighting for RichTextFX-Codearea      * created by (c) Carlos Martins (github: @cemartins)      * License: BSD-2-Clause      * see https://github.com/FXMisc/RichTextFX/blob/master/LICENSE      * and: https://github.com/FXMisc/RichTextFX/blob/master/richtextfx-demos/README.md#xml-editor      *      * @param text to parse and highlight      * @return highlighted span for codeArea      */
end_comment

begin_function
DECL|method|computeHighlighting (String text)
specifier|public
name|StyleSpans
argument_list|<
name|Collection
argument_list|<
name|String
argument_list|>
argument_list|>
name|computeHighlighting
parameter_list|(
name|String
name|text
parameter_list|)
block|{
specifier|final
name|Pattern
name|XML_TAG
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"(?<ELEMENT>(</?\\h*)(\\w+)([^<>]*)(\\h*/?>))"
operator|+
literal|"|(?<COMMENT><!--[^<>]+-->)"
argument_list|)
decl_stmt|;
specifier|final
name|Pattern
name|ATTRIBUTES
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"(\\w+\\h*)(=)(\\h*\"[^\"]+\")"
argument_list|)
decl_stmt|;
specifier|final
name|int
name|GROUP_OPEN_BRACKET
init|=
literal|2
decl_stmt|;
specifier|final
name|int
name|GROUP_ELEMENT_NAME
init|=
literal|3
decl_stmt|;
specifier|final
name|int
name|GROUP_ATTRIBUTES_SECTION
init|=
literal|4
decl_stmt|;
specifier|final
name|int
name|GROUP_CLOSE_BRACKET
init|=
literal|5
decl_stmt|;
specifier|final
name|int
name|GROUP_ATTRIBUTE_NAME
init|=
literal|1
decl_stmt|;
specifier|final
name|int
name|GROUP_EQUAL_SYMBOL
init|=
literal|2
decl_stmt|;
specifier|final
name|int
name|GROUP_ATTRIBUTE_VALUE
init|=
literal|3
decl_stmt|;
name|Matcher
name|matcher
init|=
name|XML_TAG
operator|.
name|matcher
argument_list|(
name|text
argument_list|)
decl_stmt|;
name|int
name|lastKwEnd
init|=
literal|0
decl_stmt|;
name|StyleSpansBuilder
argument_list|<
name|Collection
argument_list|<
name|String
argument_list|>
argument_list|>
name|spansBuilder
init|=
operator|new
name|StyleSpansBuilder
argument_list|<>
argument_list|()
decl_stmt|;
while|while
condition|(
name|matcher
operator|.
name|find
argument_list|()
condition|)
block|{
name|spansBuilder
operator|.
name|add
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|matcher
operator|.
name|start
argument_list|()
operator|-
name|lastKwEnd
argument_list|)
expr_stmt|;
if|if
condition|(
name|matcher
operator|.
name|group
argument_list|(
literal|"COMMENT"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|spansBuilder
operator|.
name|add
argument_list|(
name|Collections
operator|.
name|singleton
argument_list|(
literal|"comment"
argument_list|)
argument_list|,
name|matcher
operator|.
name|end
argument_list|()
operator|-
name|matcher
operator|.
name|start
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|matcher
operator|.
name|group
argument_list|(
literal|"ELEMENT"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|String
name|attributesText
init|=
name|matcher
operator|.
name|group
argument_list|(
name|GROUP_ATTRIBUTES_SECTION
argument_list|)
decl_stmt|;
name|spansBuilder
operator|.
name|add
argument_list|(
name|Collections
operator|.
name|singleton
argument_list|(
literal|"tagmark"
argument_list|)
argument_list|,
name|matcher
operator|.
name|end
argument_list|(
name|GROUP_OPEN_BRACKET
argument_list|)
operator|-
name|matcher
operator|.
name|start
argument_list|(
name|GROUP_OPEN_BRACKET
argument_list|)
argument_list|)
expr_stmt|;
name|spansBuilder
operator|.
name|add
argument_list|(
name|Collections
operator|.
name|singleton
argument_list|(
literal|"anytag"
argument_list|)
argument_list|,
name|matcher
operator|.
name|end
argument_list|(
name|GROUP_ELEMENT_NAME
argument_list|)
operator|-
name|matcher
operator|.
name|end
argument_list|(
name|GROUP_OPEN_BRACKET
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|attributesText
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|lastKwEnd
operator|=
literal|0
expr_stmt|;
name|Matcher
name|amatcher
init|=
name|ATTRIBUTES
operator|.
name|matcher
argument_list|(
name|attributesText
argument_list|)
decl_stmt|;
while|while
condition|(
name|amatcher
operator|.
name|find
argument_list|()
condition|)
block|{
name|spansBuilder
operator|.
name|add
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|amatcher
operator|.
name|start
argument_list|()
operator|-
name|lastKwEnd
argument_list|)
expr_stmt|;
name|spansBuilder
operator|.
name|add
argument_list|(
name|Collections
operator|.
name|singleton
argument_list|(
literal|"attribute"
argument_list|)
argument_list|,
name|amatcher
operator|.
name|end
argument_list|(
name|GROUP_ATTRIBUTE_NAME
argument_list|)
operator|-
name|amatcher
operator|.
name|start
argument_list|(
name|GROUP_ATTRIBUTE_NAME
argument_list|)
argument_list|)
expr_stmt|;
name|spansBuilder
operator|.
name|add
argument_list|(
name|Collections
operator|.
name|singleton
argument_list|(
literal|"tagmark"
argument_list|)
argument_list|,
name|amatcher
operator|.
name|end
argument_list|(
name|GROUP_EQUAL_SYMBOL
argument_list|)
operator|-
name|amatcher
operator|.
name|end
argument_list|(
name|GROUP_ATTRIBUTE_NAME
argument_list|)
argument_list|)
expr_stmt|;
name|spansBuilder
operator|.
name|add
argument_list|(
name|Collections
operator|.
name|singleton
argument_list|(
literal|"avalue"
argument_list|)
argument_list|,
name|amatcher
operator|.
name|end
argument_list|(
name|GROUP_ATTRIBUTE_VALUE
argument_list|)
operator|-
name|amatcher
operator|.
name|end
argument_list|(
name|GROUP_EQUAL_SYMBOL
argument_list|)
argument_list|)
expr_stmt|;
name|lastKwEnd
operator|=
name|amatcher
operator|.
name|end
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|attributesText
operator|.
name|length
argument_list|()
operator|>
name|lastKwEnd
condition|)
block|{
name|spansBuilder
operator|.
name|add
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|attributesText
operator|.
name|length
argument_list|()
operator|-
name|lastKwEnd
argument_list|)
expr_stmt|;
block|}
block|}
name|lastKwEnd
operator|=
name|matcher
operator|.
name|end
argument_list|(
name|GROUP_ATTRIBUTES_SECTION
argument_list|)
expr_stmt|;
name|spansBuilder
operator|.
name|add
argument_list|(
name|Collections
operator|.
name|singleton
argument_list|(
literal|"tagmark"
argument_list|)
argument_list|,
name|matcher
operator|.
name|end
argument_list|(
name|GROUP_CLOSE_BRACKET
argument_list|)
operator|-
name|lastKwEnd
argument_list|)
expr_stmt|;
block|}
block|}
name|lastKwEnd
operator|=
name|matcher
operator|.
name|end
argument_list|()
expr_stmt|;
block|}
name|spansBuilder
operator|.
name|add
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|text
operator|.
name|length
argument_list|()
operator|-
name|lastKwEnd
argument_list|)
expr_stmt|;
return|return
name|spansBuilder
operator|.
name|create
argument_list|()
return|;
block|}
end_function

begin_function
annotation|@
name|Override
DECL|method|storeSettings ()
specifier|public
name|void
name|storeSettings
parameter_list|()
block|{
name|PreviewPreferences
name|previewPreferences
init|=
name|preferences
operator|.
name|getPreviewPreferences
argument_list|()
decl_stmt|;
if|if
condition|(
name|chosenListProperty
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|chosenListProperty
operator|.
name|add
argument_list|(
name|previewPreferences
operator|.
name|getTextBasedPreviewLayout
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|PreviewPreferences
name|newPreviewPreferences
init|=
name|preferences
operator|.
name|getPreviewPreferences
argument_list|()
operator|.
name|getBuilder
argument_list|()
operator|.
name|withPreviewCycle
argument_list|(
name|chosenListProperty
argument_list|)
operator|.
name|withPreviewStyle
argument_list|(
operator|(
operator|(
name|TextBasedPreviewLayout
operator|)
name|findLayoutByNameOrDefault
argument_list|(
literal|"Preview"
argument_list|)
operator|)
operator|.
name|getText
argument_list|()
argument_list|)
operator|.
name|build
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|chosenSelectionModelProperty
operator|.
name|getValue
argument_list|()
operator|.
name|getSelectedItems
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|newPreviewPreferences
operator|=
name|newPreviewPreferences
operator|.
name|getBuilder
argument_list|()
operator|.
name|withPreviewCyclePosition
argument_list|(
name|chosenListProperty
operator|.
name|getValue
argument_list|()
operator|.
name|indexOf
argument_list|(
name|chosenSelectionModelProperty
operator|.
name|getValue
argument_list|()
operator|.
name|getSelectedItems
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
argument_list|)
operator|.
name|build
argument_list|()
expr_stmt|;
block|}
name|preferences
operator|.
name|storePreviewPreferences
argument_list|(
name|newPreviewPreferences
argument_list|)
expr_stmt|;
for|for
control|(
name|BasePanel
name|basePanel
range|:
name|JabRefGUI
operator|.
name|getMainFrame
argument_list|()
operator|.
name|getBasePanelList
argument_list|()
control|)
block|{
comment|// TODO: Find a better way to update preview
name|basePanel
operator|.
name|closeBottomPane
argument_list|()
expr_stmt|;
comment|//basePanel.getPreviewPanel().updateLayout(preferences.getPreviewPreferences());
block|}
block|}
end_function

begin_function
annotation|@
name|Override
DECL|method|validateSettings ()
specifier|public
name|boolean
name|validateSettings
parameter_list|()
block|{
return|return
operator|!
name|chosenListProperty
operator|.
name|getValue
argument_list|()
operator|.
name|isEmpty
argument_list|()
return|;
block|}
end_function

begin_function
DECL|method|addToChosen ()
specifier|public
name|void
name|addToChosen
parameter_list|()
block|{
for|for
control|(
name|PreviewLayout
name|layout
range|:
name|availableSelectionModelProperty
operator|.
name|getValue
argument_list|()
operator|.
name|getSelectedItems
argument_list|()
control|)
block|{
name|availableListProperty
operator|.
name|remove
argument_list|(
name|layout
argument_list|)
expr_stmt|;
name|chosenListProperty
operator|.
name|add
argument_list|(
name|layout
argument_list|)
expr_stmt|;
block|}
block|}
end_function

begin_function
DECL|method|removeFromChosen ()
specifier|public
name|void
name|removeFromChosen
parameter_list|()
block|{
for|for
control|(
name|PreviewLayout
name|layout
range|:
name|chosenSelectionModelProperty
operator|.
name|getValue
argument_list|()
operator|.
name|getSelectedItems
argument_list|()
control|)
block|{
name|availableListProperty
operator|.
name|add
argument_list|(
name|layout
argument_list|)
expr_stmt|;
name|chosenListProperty
operator|.
name|remove
argument_list|(
name|layout
argument_list|)
expr_stmt|;
block|}
name|availableListProperty
operator|.
name|sort
argument_list|(
parameter_list|(
name|a
parameter_list|,
name|b
parameter_list|)
lambda|->
name|a
operator|.
name|getName
argument_list|()
operator|.
name|compareToIgnoreCase
argument_list|(
name|b
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
end_function

begin_function
DECL|method|selectedInChosenUp (List<Integer> oldindices)
specifier|public
name|List
argument_list|<
name|Integer
argument_list|>
name|selectedInChosenUp
parameter_list|(
name|List
argument_list|<
name|Integer
argument_list|>
name|oldindices
parameter_list|)
block|{
name|List
argument_list|<
name|Integer
argument_list|>
name|newIndices
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|oldIndex
range|:
name|oldindices
control|)
block|{
name|boolean
name|alreadyTaken
init|=
name|newIndices
operator|.
name|contains
argument_list|(
name|oldIndex
operator|-
literal|1
argument_list|)
decl_stmt|;
name|int
name|newIndex
init|=
operator|(
operator|(
name|oldIndex
operator|>
literal|0
operator|)
operator|&&
operator|!
name|alreadyTaken
operator|)
condition|?
name|oldIndex
operator|-
literal|1
else|:
name|oldIndex
decl_stmt|;
name|chosenListProperty
operator|.
name|add
argument_list|(
name|newIndex
argument_list|,
name|chosenListProperty
operator|.
name|remove
argument_list|(
name|oldIndex
argument_list|)
argument_list|)
expr_stmt|;
name|newIndices
operator|.
name|add
argument_list|(
name|newIndex
argument_list|)
expr_stmt|;
block|}
return|return
name|newIndices
return|;
block|}
end_function

begin_function
DECL|method|selectedInChosenDown (List<Integer> oldIndices)
specifier|public
name|List
argument_list|<
name|Integer
argument_list|>
name|selectedInChosenDown
parameter_list|(
name|List
argument_list|<
name|Integer
argument_list|>
name|oldIndices
parameter_list|)
block|{
name|List
argument_list|<
name|Integer
argument_list|>
name|newSelectedIndices
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
name|oldIndices
operator|.
name|size
argument_list|()
operator|-
literal|1
init|;
name|i
operator|>=
literal|0
condition|;
name|i
operator|--
control|)
block|{
name|int
name|oldIndex
init|=
name|oldIndices
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|boolean
name|alreadyTaken
init|=
name|newSelectedIndices
operator|.
name|contains
argument_list|(
name|oldIndex
operator|+
literal|1
argument_list|)
decl_stmt|;
name|int
name|newIndex
init|=
operator|(
operator|(
name|oldIndex
operator|<
operator|(
name|chosenListProperty
operator|.
name|size
argument_list|()
operator|-
literal|1
operator|)
operator|)
operator|&&
operator|!
name|alreadyTaken
operator|)
condition|?
name|oldIndex
operator|+
literal|1
else|:
name|oldIndex
decl_stmt|;
name|chosenListProperty
operator|.
name|add
argument_list|(
name|newIndex
argument_list|,
name|chosenListProperty
operator|.
name|remove
argument_list|(
name|oldIndex
argument_list|)
argument_list|)
expr_stmt|;
name|newSelectedIndices
operator|.
name|add
argument_list|(
name|newIndex
argument_list|)
expr_stmt|;
block|}
return|return
name|newSelectedIndices
return|;
block|}
end_function

begin_function
DECL|method|getCurrentLayout ()
specifier|public
name|PreviewLayout
name|getCurrentLayout
parameter_list|()
block|{
if|if
condition|(
operator|!
name|chosenSelectionModelProperty
operator|.
name|getValue
argument_list|()
operator|.
name|getSelectedItems
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
name|chosenSelectionModelProperty
operator|.
name|getValue
argument_list|()
operator|.
name|getSelectedItems
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
return|;
block|}
if|if
condition|(
operator|!
name|chosenListProperty
operator|.
name|getValue
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
name|chosenListProperty
operator|.
name|getValue
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
return|;
block|}
return|return
name|findLayoutByNameOrDefault
argument_list|(
literal|"Preview"
argument_list|)
return|;
block|}
end_function

begin_function
DECL|method|resetDefaultStyle ()
specifier|public
name|void
name|resetDefaultStyle
parameter_list|()
block|{
operator|(
operator|(
name|TextBasedPreviewLayout
operator|)
name|findLayoutByNameOrDefault
argument_list|(
literal|"Preview"
argument_list|)
operator|)
operator|.
name|setText
argument_list|(
name|preferences
operator|.
name|getPreviewPreferences
argument_list|()
operator|.
name|getDefaultPreviewStyle
argument_list|()
argument_list|)
expr_stmt|;
block|}
end_function

begin_function
DECL|method|availableListProperty ()
specifier|public
name|ListProperty
argument_list|<
name|PreviewLayout
argument_list|>
name|availableListProperty
parameter_list|()
block|{
return|return
name|availableListProperty
return|;
block|}
end_function

begin_function
DECL|method|availableSelectionModelProperty ()
specifier|public
name|ObjectProperty
argument_list|<
name|MultipleSelectionModel
argument_list|<
name|PreviewLayout
argument_list|>
argument_list|>
name|availableSelectionModelProperty
parameter_list|()
block|{
return|return
name|availableSelectionModelProperty
return|;
block|}
end_function

begin_function
DECL|method|chosenListProperty ()
specifier|public
name|ListProperty
argument_list|<
name|PreviewLayout
argument_list|>
name|chosenListProperty
parameter_list|()
block|{
return|return
name|chosenListProperty
return|;
block|}
end_function

begin_function
DECL|method|chosenSelectionModelProperty ()
specifier|public
name|ObjectProperty
argument_list|<
name|MultipleSelectionModel
argument_list|<
name|PreviewLayout
argument_list|>
argument_list|>
name|chosenSelectionModelProperty
parameter_list|()
block|{
return|return
name|chosenSelectionModelProperty
return|;
block|}
end_function

begin_function
DECL|method|selectedIsEditableProperty ()
specifier|public
name|BooleanProperty
name|selectedIsEditableProperty
parameter_list|()
block|{
return|return
name|selectedIsEditableProperty
return|;
block|}
end_function

unit|}
end_unit

