begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.entryeditor.fileannotationtab
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|entryeditor
operator|.
name|fileannotationtab
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
name|Tooltip
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
name|entryeditor
operator|.
name|EntryEditorTab
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

begin_class
DECL|class|FileAnnotationTab
specifier|public
class|class
name|FileAnnotationTab
extends|extends
name|EntryEditorTab
block|{
DECL|field|fileAnnotationCache
specifier|private
specifier|final
name|FileAnnotationCache
name|fileAnnotationCache
decl_stmt|;
DECL|field|entry
specifier|private
specifier|final
name|BibEntry
name|entry
decl_stmt|;
DECL|method|FileAnnotationTab (FileAnnotationCache cache, BibEntry entry)
specifier|public
name|FileAnnotationTab
parameter_list|(
name|FileAnnotationCache
name|cache
parameter_list|,
name|BibEntry
name|entry
parameter_list|)
block|{
name|this
operator|.
name|fileAnnotationCache
operator|=
name|cache
expr_stmt|;
name|this
operator|.
name|entry
operator|=
name|entry
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
name|entry
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
DECL|method|notifyAboutFocus ()
specifier|public
name|void
name|notifyAboutFocus
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
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
operator|new
name|FileAnnotationTabView
argument_list|(
name|entry
argument_list|,
name|fileAnnotationCache
argument_list|)
operator|.
name|getView
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

