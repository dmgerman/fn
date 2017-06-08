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
name|model
operator|.
name|entry
operator|.
name|identifier
operator|.
name|MathSciNetId
import|;
end_import

begin_class
DECL|class|MathSciNetTab
specifier|public
class|class
name|MathSciNetTab
extends|extends
name|EntryEditorTab
block|{
DECL|field|mathSciNetId
specifier|private
name|Optional
argument_list|<
name|MathSciNetId
argument_list|>
name|mathSciNetId
decl_stmt|;
DECL|method|MathSciNetTab (BibEntry entry)
specifier|public
name|MathSciNetTab
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
name|this
operator|.
name|mathSciNetId
operator|=
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|MR_NUMBER
argument_list|)
operator|.
name|flatMap
argument_list|(
name|MathSciNetId
operator|::
name|parse
argument_list|)
expr_stmt|;
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"MathSciNet Review"
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|getPane ()
specifier|private
name|StackPane
name|getPane
parameter_list|()
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
comment|// Quick hack to disable navigating
name|browser
operator|.
name|addEventFilter
argument_list|(
name|javafx
operator|.
name|scene
operator|.
name|input
operator|.
name|MouseEvent
operator|.
name|ANY
argument_list|,
name|javafx
operator|.
name|scene
operator|.
name|input
operator|.
name|MouseEvent
operator|::
name|consume
argument_list|)
expr_stmt|;
name|browser
operator|.
name|setContextMenuEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
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
name|mathSciNetId
operator|.
name|flatMap
argument_list|(
name|MathSciNetId
operator|::
name|getExternalURI
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|url
lambda|->
name|browser
operator|.
name|getEngine
argument_list|()
operator|.
name|load
argument_list|(
name|url
operator|.
name|toASCIIString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|// Hide progress indicator if finished (over 70% loaded)
name|browser
operator|.
name|getEngine
argument_list|()
operator|.
name|getLoadWorker
argument_list|()
operator|.
name|progressProperty
argument_list|()
operator|.
name|addListener
argument_list|(
parameter_list|(
name|observable
parameter_list|,
name|oldValue
parameter_list|,
name|newValue
parameter_list|)
lambda|->
block|{
if|if
condition|(
name|newValue
operator|.
name|doubleValue
argument_list|()
operator|>=
literal|0.7
condition|)
block|{
name|progress
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
return|return
name|root
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
name|mathSciNetId
operator|.
name|isPresent
argument_list|()
return|;
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
name|getPane
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
