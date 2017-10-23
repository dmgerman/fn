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
name|net
operator|.
name|URL
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
name|Optional
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
name|StackPane
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|web
operator|.
name|WebView
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
name|OpenHyperlinksInExternalBrowser
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
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_class
DECL|class|RelatedArticlesTab
specifier|public
class|class
name|RelatedArticlesTab
extends|extends
name|EntryEditorTab
block|{
DECL|field|preferences
specifier|private
specifier|final
name|JabRefPreferences
name|preferences
decl_stmt|;
DECL|method|RelatedArticlesTab (JabRefPreferences preferences)
specifier|public
name|RelatedArticlesTab
parameter_list|(
name|JabRefPreferences
name|preferences
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
name|preferences
operator|=
name|preferences
expr_stmt|;
block|}
DECL|method|getPane (BibEntry entry)
specifier|private
name|StackPane
name|getPane
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
name|WebView
name|browser
init|=
operator|new
name|WebView
argument_list|()
decl_stmt|;
name|root
operator|.
name|getChildren
argument_list|()
operator|.
name|addAll
argument_list|(
name|browser
argument_list|,
name|progress
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
operator|.
name|getFullVersion
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
name|progress
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
argument_list|;
name|browser
operator|.
name|getEngine
argument_list|()
operator|.
name|loadContent
argument_list|(
name|convertToHtml
argument_list|(
name|relatedArticles
argument_list|)
argument_list|)
argument_list|;
block|}
block|)
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
name|browser
operator|.
name|getEngine
argument_list|()
operator|.
name|getLoadWorker
argument_list|()
operator|.
name|stateProperty
argument_list|()
operator|.
name|addListener
argument_list|(
operator|new
name|OpenHyperlinksInExternalBrowser
argument_list|(
name|browser
argument_list|)
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
comment|/**      * Takes a List of HTML snippets stored in the field "html_representation" of a list of bibentries      *      * @param list of bib entries having a field html_representation      */
end_comment

begin_function
DECL|method|convertToHtml (List<BibEntry> list)
unit|private
name|String
name|convertToHtml
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|list
parameter_list|)
block|{
name|StringBuilder
name|htmlContent
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|URL
name|url
init|=
name|IconTheme
operator|.
name|getIconUrl
argument_list|(
literal|"mdlListIcon"
argument_list|)
decl_stmt|;
name|htmlContent
operator|.
name|append
argument_list|(
literal|"<html><head><title></title></head><body bgcolor='#ffffff'>"
argument_list|)
expr_stmt|;
name|htmlContent
operator|.
name|append
argument_list|(
literal|"<ul style='list-style-image:("
argument_list|)
expr_stmt|;
name|htmlContent
operator|.
name|append
argument_list|(
name|url
argument_list|)
expr_stmt|;
name|htmlContent
operator|.
name|append
argument_list|(
literal|")'>"
argument_list|)
expr_stmt|;
name|list
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|bibEntry
lambda|->
name|bibEntry
operator|.
name|getField
argument_list|(
literal|"html_representation"
argument_list|)
argument_list|)
operator|.
name|filter
argument_list|(
name|Optional
operator|::
name|isPresent
argument_list|)
operator|.
name|map
argument_list|(
name|o
lambda|->
literal|"<li style='margin: 5px'>"
operator|+
name|o
operator|.
name|get
argument_list|()
operator|+
literal|"</li>"
argument_list|)
operator|.
name|forEach
argument_list|(
name|html
lambda|->
name|htmlContent
operator|.
name|append
argument_list|(
name|html
argument_list|)
argument_list|)
expr_stmt|;
name|htmlContent
operator|.
name|append
argument_list|(
literal|"</ul>"
argument_list|)
expr_stmt|;
name|htmlContent
operator|.
name|append
argument_list|(
literal|"<br><div style='margin-left: 5px'>"
argument_list|)
expr_stmt|;
name|htmlContent
operator|.
name|append
argument_list|(
literal|"<a href='http://mr-dlib.org/information-for-users/information-about-mr-dlib-for-jabref-users/#' target=\"_blank\">"
argument_list|)
expr_stmt|;
name|htmlContent
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"What is Mr. DLib?"
argument_list|)
argument_list|)
expr_stmt|;
name|htmlContent
operator|.
name|append
argument_list|(
literal|"</a></div>"
argument_list|)
expr_stmt|;
name|htmlContent
operator|.
name|append
argument_list|(
literal|"</body></html>"
argument_list|)
expr_stmt|;
return|return
name|htmlContent
operator|.
name|toString
argument_list|()
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
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|SHOW_RECOMMENDATIONS
argument_list|)
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
name|setContent
argument_list|(
name|getPane
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
end_function

unit|}
end_unit

