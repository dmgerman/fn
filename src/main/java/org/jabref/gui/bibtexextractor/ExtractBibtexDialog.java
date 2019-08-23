begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.bibtexextractor
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|bibtexextractor
package|;
end_package

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|*
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
name|util
operator|.
name|BaseDialog
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
name|BiblatexEntryTypes
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
name|EntryType
import|;
end_import

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

begin_comment
comment|/**  * GUI Dialog for the feature "Extract BibTeX from plain text".  */
end_comment

begin_class
DECL|class|ExtractBibtexDialog
specifier|public
class|class
name|ExtractBibtexDialog
extends|extends
name|BaseDialog
argument_list|<
name|Void
argument_list|>
block|{
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|textArea
specifier|private
name|TextArea
name|textArea
decl_stmt|;
DECL|field|buttonExtract
specifier|private
name|Button
name|buttonExtract
decl_stmt|;
DECL|method|ExtractBibtexDialog (JabRefFrame frame)
specifier|public
name|ExtractBibtexDialog
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|this
operator|.
name|setTitle
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Input text to parse"
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|initialize
argument_list|()
expr_stmt|;
block|}
DECL|method|initialize ()
specifier|private
name|void
name|initialize
parameter_list|()
block|{
name|textArea
operator|=
operator|new
name|TextArea
argument_list|()
expr_stmt|;
name|textArea
operator|.
name|setWrapText
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|textArea
operator|.
name|textProperty
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
name|buttonExtract
operator|.
name|setDisable
argument_list|(
name|newValue
operator|.
name|isEmpty
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|VBox
name|container
init|=
operator|new
name|VBox
argument_list|(
literal|20
argument_list|)
decl_stmt|;
name|container
operator|.
name|getChildren
argument_list|()
operator|.
name|addAll
argument_list|(
name|textArea
argument_list|)
expr_stmt|;
name|container
operator|.
name|setPrefWidth
argument_list|(
literal|600
argument_list|)
expr_stmt|;
name|ButtonType
name|buttonTypeGenerate
init|=
operator|new
name|ButtonType
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Extract"
argument_list|)
argument_list|,
name|ButtonBar
operator|.
name|ButtonData
operator|.
name|OK_DONE
argument_list|)
decl_stmt|;
name|getDialogPane
argument_list|()
operator|.
name|getButtonTypes
argument_list|()
operator|.
name|setAll
argument_list|(
name|buttonTypeGenerate
argument_list|,
name|ButtonType
operator|.
name|CANCEL
argument_list|)
expr_stmt|;
name|buttonExtract
operator|=
operator|(
name|Button
operator|)
name|getDialogPane
argument_list|()
operator|.
name|lookupButton
argument_list|(
name|buttonTypeGenerate
argument_list|)
expr_stmt|;
name|buttonExtract
operator|.
name|setTooltip
argument_list|(
operator|new
name|Tooltip
argument_list|(
operator|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Starts the extraction of the BibTeX entry"
argument_list|)
operator|)
argument_list|)
argument_list|)
expr_stmt|;
name|buttonExtract
operator|.
name|setDisable
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|buttonExtract
operator|.
name|setOnAction
argument_list|(
name|e
lambda|->
name|startExtraction
argument_list|()
argument_list|)
expr_stmt|;
name|getDialogPane
argument_list|()
operator|.
name|setContent
argument_list|(
name|container
argument_list|)
expr_stmt|;
block|}
DECL|method|startExtraction ()
specifier|private
name|void
name|startExtraction
parameter_list|()
block|{
name|BibtexExtractor
name|extractor
init|=
operator|new
name|BibtexExtractor
argument_list|()
decl_stmt|;
name|BibEntry
name|entity
init|=
name|extractor
operator|.
name|extract
argument_list|(
name|textArea
operator|.
name|getText
argument_list|()
argument_list|)
decl_stmt|;
name|trackNewEntry
argument_list|(
name|BiblatexEntryTypes
operator|.
name|ARTICLE
argument_list|)
expr_stmt|;
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|insertEntry
argument_list|(
name|entity
argument_list|)
expr_stmt|;
block|}
DECL|method|trackNewEntry (EntryType type)
specifier|private
name|void
name|trackNewEntry
parameter_list|(
name|EntryType
name|type
parameter_list|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|properties
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
name|properties
operator|.
name|put
argument_list|(
literal|"EntryType"
argument_list|,
name|type
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|getTelemetryClient
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|client
lambda|->
name|client
operator|.
name|trackEvent
argument_list|(
literal|"NewEntry"
argument_list|,
name|properties
argument_list|,
operator|new
name|HashMap
argument_list|<>
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

