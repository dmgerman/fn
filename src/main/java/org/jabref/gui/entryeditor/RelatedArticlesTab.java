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
name|io
operator|.
name|IOException
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
name|Hyperlink
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
name|ProgressIndicator
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
name|StackPane
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
name|Font
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
name|FontPosture
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
name|Globals
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
name|desktop
operator|.
name|JabRefDesktop
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
name|logic
operator|.
name|importer
operator|.
name|fetcher
operator|.
name|MrDLibFetcher
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
name|FieldName
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

begin_comment
comment|/**  * GUI for tab displaying article recommendations based on the currently selected BibEntry  */
end_comment

begin_class
DECL|class|RelatedArticlesTab
specifier|public
class|class
name|RelatedArticlesTab
extends|extends
name|EntryEditorTab
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
name|RelatedArticlesTab
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|preferences
specifier|private
specifier|final
name|EntryEditorPreferences
name|preferences
decl_stmt|;
DECL|field|entryEditor
specifier|private
specifier|final
name|EntryEditor
name|entryEditor
decl_stmt|;
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
decl_stmt|;
DECL|method|RelatedArticlesTab (EntryEditor entryEditor, EntryEditorPreferences preferences, DialogService dialogService)
specifier|public
name|RelatedArticlesTab
parameter_list|(
name|EntryEditor
name|entryEditor
parameter_list|,
name|EntryEditorPreferences
name|preferences
parameter_list|,
name|DialogService
name|dialogService
parameter_list|)
block|{
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Related articles"
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
literal|"Related articles"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|entryEditor
operator|=
name|entryEditor
expr_stmt|;
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
block|}
comment|/**      * Gets a StackPane of related article information to be displayed in the Related Articles tab      * @param entry The currently selected BibEntry on the JabRef UI.      * @return A StackPane with related article information to be displayed in the Related Articles tab.      */
DECL|method|getRelatedArticlesPane (BibEntry entry)
specifier|private
name|StackPane
name|getRelatedArticlesPane
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
name|StackPane
name|root
init|=
operator|new
name|StackPane
argument_list|()
decl_stmt|;
name|root
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"related-articles-tab"
argument_list|)
expr_stmt|;
name|ProgressIndicator
name|progress
init|=
operator|new
name|ProgressIndicator
argument_list|()
decl_stmt|;
name|progress
operator|.
name|setMaxSize
argument_list|(
literal|100
argument_list|,
literal|100
argument_list|)
expr_stmt|;
name|MrDLibFetcher
name|fetcher
init|=
operator|new
name|MrDLibFetcher
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|LANGUAGE
argument_list|)
argument_list|,
name|Globals
operator|.
name|BUILD_INFO
operator|.
name|getVersion
argument_list|()
argument_list|)
decl_stmt|;
name|BackgroundTask
operator|.
name|wrap
argument_list|(
parameter_list|()
lambda|->
name|fetcher
operator|.
name|performSearch
argument_list|(
name|entry
argument_list|)
argument_list|)
operator|.
name|onRunning
argument_list|(
parameter_list|()
lambda|->
name|progress
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
argument_list|)
operator|.
name|onSuccess
argument_list|(
name|relatedArticles
lambda|->
block|{
name|BackgroundTask
operator|.
name|wrap
argument_list|(
parameter_list|()
lambda|->
name|fetcher
operator|.
name|confirmRecommendations
argument_list|(
literal|"received"
argument_list|)
argument_list|)
operator|.
name|executeWith
argument_list|(
name|Globals
operator|.
name|TASK_EXECUTOR
argument_list|)
argument_list|;
name|progress
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
argument_list|;
name|root
operator|.
name|getChildren
argument_list|()
operator|.
name|add
argument_list|(
name|getRelatedArticleInfo
argument_list|(
name|relatedArticles
argument_list|,
name|fetcher
argument_list|)
argument_list|)
argument_list|;                           if
operator|(
name|entry
operator|.
name|getAuthorTitleYear
argument_list|(
literal|100
argument_list|)
operator|.
name|equals
argument_list|(
name|entryEditor
operator|.
name|getEntry
argument_list|()
operator|.
name|getAuthorTitleYear
argument_list|(
literal|100
argument_list|)
argument_list|)
operator|)
block|{
name|BackgroundTask
operator|.
name|wrap
argument_list|(
parameter_list|()
lambda|->
name|fetcher
operator|.
name|confirmRecommendations
argument_list|(
literal|"displayed"
argument_list|)
argument_list|)
operator|.
name|executeWith
argument_list|(
name|Globals
operator|.
name|TASK_EXECUTOR
argument_list|)
block|;                           }
block|}
block|)
operator|.
name|onFailure
argument_list|(
name|exception
lambda|->
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Error while fetching from Mr. DLib"
argument_list|,
name|exception
argument_list|)
expr_stmt|;
name|progress
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|root
operator|.
name|getChildren
argument_list|()
operator|.
name|add
argument_list|(
name|getErrorInfo
argument_list|()
argument_list|)
expr_stmt|;
block|}
argument_list|)
operator|.
name|executeWith
argument_list|(
name|Globals
operator|.
name|TASK_EXECUTOR
argument_list|)
expr_stmt|;
end_class

begin_expr_stmt
name|root
operator|.
name|getChildren
argument_list|()
operator|.
name|add
argument_list|(
name|progress
argument_list|)
expr_stmt|;
end_expr_stmt

begin_return
return|return
name|root
return|;
end_return

begin_comment
unit|}
comment|/**      * Creates a VBox of the related article information to be used in the StackPane displayed in the Related Articles tab      * @param list List of BibEntries of related articles      * @return VBox of related article descriptions to be displayed in the Related Articles tab      */
end_comment

begin_function
DECL|method|getRelatedArticleInfo (List<BibEntry> list, MrDLibFetcher fetcher)
unit|private
name|ScrollPane
name|getRelatedArticleInfo
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|list
parameter_list|,
name|MrDLibFetcher
name|fetcher
parameter_list|)
block|{
name|ScrollPane
name|scrollPane
init|=
operator|new
name|ScrollPane
argument_list|()
decl_stmt|;
name|VBox
name|vBox
init|=
operator|new
name|VBox
argument_list|()
decl_stmt|;
name|vBox
operator|.
name|setSpacing
argument_list|(
literal|20.0
argument_list|)
expr_stmt|;
name|String
name|heading
init|=
name|fetcher
operator|.
name|getHeading
argument_list|()
decl_stmt|;
name|Text
name|headingText
init|=
operator|new
name|Text
argument_list|(
name|heading
argument_list|)
decl_stmt|;
name|headingText
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"recommendation-heading"
argument_list|)
expr_stmt|;
name|String
name|description
init|=
name|fetcher
operator|.
name|getDescription
argument_list|()
decl_stmt|;
name|Text
name|descriptionText
init|=
operator|new
name|Text
argument_list|(
name|description
argument_list|)
decl_stmt|;
name|descriptionText
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"recommendation-description"
argument_list|)
expr_stmt|;
name|vBox
operator|.
name|getChildren
argument_list|()
operator|.
name|add
argument_list|(
name|headingText
argument_list|)
expr_stmt|;
name|vBox
operator|.
name|getChildren
argument_list|()
operator|.
name|add
argument_list|(
name|descriptionText
argument_list|)
expr_stmt|;
for|for
control|(
name|BibEntry
name|entry
range|:
name|list
control|)
block|{
name|HBox
name|hBox
init|=
operator|new
name|HBox
argument_list|()
decl_stmt|;
name|hBox
operator|.
name|setSpacing
argument_list|(
literal|5.0
argument_list|)
expr_stmt|;
name|hBox
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"recommendation-item"
argument_list|)
expr_stmt|;
name|String
name|title
init|=
name|entry
operator|.
name|getTitle
argument_list|()
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
decl_stmt|;
name|String
name|journal
init|=
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|JOURNAL
argument_list|)
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
decl_stmt|;
name|String
name|authors
init|=
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|AUTHOR
argument_list|)
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
decl_stmt|;
name|String
name|year
init|=
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|YEAR
argument_list|)
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
decl_stmt|;
name|Hyperlink
name|titleLink
init|=
operator|new
name|Hyperlink
argument_list|(
name|title
argument_list|)
decl_stmt|;
name|Text
name|journalText
init|=
operator|new
name|Text
argument_list|(
name|journal
argument_list|)
decl_stmt|;
name|journalText
operator|.
name|setFont
argument_list|(
name|Font
operator|.
name|font
argument_list|(
name|Font
operator|.
name|getDefault
argument_list|()
operator|.
name|getFamily
argument_list|()
argument_list|,
name|FontPosture
operator|.
name|ITALIC
argument_list|,
name|Font
operator|.
name|getDefault
argument_list|()
operator|.
name|getSize
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|Text
name|authorsText
init|=
operator|new
name|Text
argument_list|(
name|authors
argument_list|)
decl_stmt|;
name|Text
name|yearText
init|=
operator|new
name|Text
argument_list|(
literal|"("
operator|+
name|year
operator|+
literal|")"
argument_list|)
decl_stmt|;
name|titleLink
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
block|{
if|if
condition|(
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|URL
argument_list|)
operator|.
name|isPresent
argument_list|()
condition|)
block|{
try|try
block|{
name|JabRefDesktop
operator|.
name|openBrowser
argument_list|(
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|URL
argument_list|)
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Error opening the browser to: "
operator|+
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|URL
argument_list|)
operator|.
name|get
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|hBox
operator|.
name|getChildren
argument_list|()
operator|.
name|addAll
argument_list|(
name|titleLink
argument_list|,
name|journalText
argument_list|,
name|authorsText
argument_list|,
name|yearText
argument_list|)
expr_stmt|;
name|vBox
operator|.
name|getChildren
argument_list|()
operator|.
name|add
argument_list|(
name|hBox
argument_list|)
expr_stmt|;
block|}
name|scrollPane
operator|.
name|setContent
argument_list|(
name|vBox
argument_list|)
expr_stmt|;
return|return
name|scrollPane
return|;
block|}
end_function

begin_comment
comment|/**      * Gets a ScrollPane to display error info when recommendations fail.      * @return ScrollPane to display in place of recommendations      */
end_comment

begin_function
DECL|method|getErrorInfo ()
specifier|private
name|ScrollPane
name|getErrorInfo
parameter_list|()
block|{
name|ScrollPane
name|scrollPane
init|=
operator|new
name|ScrollPane
argument_list|()
decl_stmt|;
name|VBox
name|vBox
init|=
operator|new
name|VBox
argument_list|()
decl_stmt|;
name|vBox
operator|.
name|setSpacing
argument_list|(
literal|20.0
argument_list|)
expr_stmt|;
name|Text
name|descriptionText
init|=
operator|new
name|Text
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"No recommendations received from Mr. DLib for this entry."
argument_list|)
argument_list|)
decl_stmt|;
name|descriptionText
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"recommendation-description"
argument_list|)
expr_stmt|;
name|vBox
operator|.
name|getChildren
argument_list|()
operator|.
name|add
argument_list|(
name|descriptionText
argument_list|)
expr_stmt|;
name|scrollPane
operator|.
name|setContent
argument_list|(
name|vBox
argument_list|)
expr_stmt|;
return|return
name|scrollPane
return|;
block|}
end_function

begin_comment
comment|/**      * Returns a consent dialog used to ask permission to send data to Mr. DLib.      * @param entry Currently selected BibEntry. (required to allow reloading of pane if accepted)      * @return StackPane returned to be placed into Related Articles tab.      */
end_comment

begin_function
DECL|method|getPrivacyDialog (BibEntry entry)
specifier|private
name|ScrollPane
name|getPrivacyDialog
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
name|ScrollPane
name|root
init|=
operator|new
name|ScrollPane
argument_list|()
decl_stmt|;
name|root
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"related-articles-tab"
argument_list|)
expr_stmt|;
name|VBox
name|vbox
init|=
operator|new
name|VBox
argument_list|()
decl_stmt|;
name|vbox
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"gdpr-dialog"
argument_list|)
expr_stmt|;
name|vbox
operator|.
name|setSpacing
argument_list|(
literal|20.0
argument_list|)
expr_stmt|;
name|Button
name|button
init|=
operator|new
name|Button
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"I Agree"
argument_list|)
argument_list|)
decl_stmt|;
name|button
operator|.
name|setDefaultButton
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|Text
name|line1
init|=
operator|new
name|Text
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Mr. DLib is an external service which provides article recommendations based on the currently selected entry. Data about the selected entry must be sent to Mr. DLib in order to provide these recommendations. Do you agree that this data may be sent?"
argument_list|)
argument_list|)
decl_stmt|;
name|line1
operator|.
name|setWrappingWidth
argument_list|(
literal|1300.0
argument_list|)
expr_stmt|;
name|Text
name|line2
init|=
operator|new
name|Text
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"This setting may be changed in preferences at any time."
argument_list|)
argument_list|)
decl_stmt|;
name|Hyperlink
name|mdlLink
init|=
operator|new
name|Hyperlink
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Further information about Mr DLib. for JabRef users."
argument_list|)
argument_list|)
decl_stmt|;
name|mdlLink
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
block|{
try|try
block|{
name|JabRefDesktop
operator|.
name|openBrowser
argument_list|(
literal|"http://mr-dlib.org/information-for-users/information-about-mr-dlib-for-jabref-users/"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Error opening the browser to Mr. DLib information page."
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|button
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
block|{
name|JabRefPreferences
name|prefs
init|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|ACCEPT_RECOMMENDATIONS
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|dialogService
operator|.
name|showWarningDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Restart"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Please restart JabRef for preferences to take effect."
argument_list|)
argument_list|)
expr_stmt|;
name|setContent
argument_list|(
name|getRelatedArticlesPane
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|vbox
operator|.
name|getChildren
argument_list|()
operator|.
name|addAll
argument_list|(
name|line1
argument_list|,
name|mdlLink
argument_list|,
name|line2
argument_list|,
name|button
argument_list|)
expr_stmt|;
name|root
operator|.
name|setContent
argument_list|(
name|vbox
argument_list|)
expr_stmt|;
return|return
name|root
return|;
block|}
end_function

begin_function
annotation|@
name|Override
DECL|method|shouldShow (BibEntry entry)
specifier|public
name|boolean
name|shouldShow
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
return|return
name|preferences
operator|.
name|shouldShowRecommendationsTab
argument_list|()
return|;
block|}
end_function

begin_function
annotation|@
name|Override
DECL|method|bindToEntry (BibEntry entry)
specifier|protected
name|void
name|bindToEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
comment|// Ask for consent to send data to Mr. DLib on first time to tab
if|if
condition|(
name|preferences
operator|.
name|isMrdlibAccepted
argument_list|()
condition|)
block|{
name|setContent
argument_list|(
name|getRelatedArticlesPane
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|setContent
argument_list|(
name|getPrivacyDialog
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_function

unit|}
end_unit

