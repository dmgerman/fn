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
name|util
operator|.
name|ArrayList
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
name|ReadOnlyListWrapper
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
name|importer
operator|.
name|ImportEntriesDialog
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
name|texparser
operator|.
name|Citation
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
name|TexBibEntriesResolverResult
import|;
end_import

begin_class
DECL|class|ParseTexResultViewModel
specifier|public
class|class
name|ParseTexResultViewModel
extends|extends
name|AbstractViewModel
block|{
DECL|field|resolverResult
specifier|private
specifier|final
name|TexBibEntriesResolverResult
name|resolverResult
decl_stmt|;
DECL|field|databaseContext
specifier|private
specifier|final
name|BibDatabaseContext
name|databaseContext
decl_stmt|;
DECL|field|referenceList
specifier|private
specifier|final
name|ObservableList
argument_list|<
name|ReferenceViewModel
argument_list|>
name|referenceList
decl_stmt|;
DECL|field|citationList
specifier|private
specifier|final
name|ObservableList
argument_list|<
name|Citation
argument_list|>
name|citationList
decl_stmt|;
DECL|field|close
specifier|private
specifier|final
name|BooleanProperty
name|close
decl_stmt|;
DECL|field|importButtonDisabled
specifier|private
specifier|final
name|BooleanProperty
name|importButtonDisabled
decl_stmt|;
DECL|method|ParseTexResultViewModel (TexBibEntriesResolverResult resolverResult, BibDatabaseContext databaseContext)
specifier|public
name|ParseTexResultViewModel
parameter_list|(
name|TexBibEntriesResolverResult
name|resolverResult
parameter_list|,
name|BibDatabaseContext
name|databaseContext
parameter_list|)
block|{
name|this
operator|.
name|resolverResult
operator|=
name|resolverResult
expr_stmt|;
name|this
operator|.
name|databaseContext
operator|=
name|databaseContext
expr_stmt|;
name|this
operator|.
name|referenceList
operator|=
name|FXCollections
operator|.
name|observableArrayList
argument_list|()
expr_stmt|;
name|this
operator|.
name|citationList
operator|=
name|FXCollections
operator|.
name|observableArrayList
argument_list|()
expr_stmt|;
name|this
operator|.
name|close
operator|=
operator|new
name|SimpleBooleanProperty
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|resolverResult
operator|.
name|getCitations
argument_list|()
operator|.
name|asMap
argument_list|()
operator|.
name|forEach
argument_list|(
parameter_list|(
name|entry
parameter_list|,
name|citations
parameter_list|)
lambda|->
name|referenceList
operator|.
name|add
argument_list|(
operator|new
name|ReferenceViewModel
argument_list|(
name|entry
argument_list|,
name|resolverResult
operator|.
name|getNewEntryKeys
argument_list|()
operator|.
name|contains
argument_list|(
name|entry
argument_list|)
argument_list|,
name|citations
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|importButtonDisabled
operator|=
operator|new
name|SimpleBooleanProperty
argument_list|(
name|referenceList
operator|.
name|stream
argument_list|()
operator|.
name|noneMatch
argument_list|(
name|ReferenceViewModel
operator|::
name|isHighlighted
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|getReferenceList ()
specifier|public
name|ObservableList
argument_list|<
name|ReferenceViewModel
argument_list|>
name|getReferenceList
parameter_list|()
block|{
return|return
operator|new
name|ReadOnlyListWrapper
argument_list|<>
argument_list|(
name|referenceList
argument_list|)
return|;
block|}
DECL|method|getCitationListByReference ()
specifier|public
name|ObservableList
argument_list|<
name|Citation
argument_list|>
name|getCitationListByReference
parameter_list|()
block|{
return|return
operator|new
name|ReadOnlyListWrapper
argument_list|<>
argument_list|(
name|citationList
argument_list|)
return|;
block|}
DECL|method|importButtonDisabledProperty ()
specifier|public
name|BooleanProperty
name|importButtonDisabledProperty
parameter_list|()
block|{
return|return
name|importButtonDisabled
return|;
block|}
DECL|method|closeProperty ()
specifier|public
name|BooleanProperty
name|closeProperty
parameter_list|()
block|{
return|return
name|close
return|;
block|}
comment|/**      * Update the citation list depending on the selected reference.      */
DECL|method|activeReferenceChanged (ReferenceViewModel reference)
specifier|public
name|void
name|activeReferenceChanged
parameter_list|(
name|ReferenceViewModel
name|reference
parameter_list|)
block|{
if|if
condition|(
name|reference
operator|==
literal|null
condition|)
block|{
name|citationList
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|citationList
operator|.
name|setAll
argument_list|(
name|reference
operator|.
name|getCitationList
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Search and import unknown references from associated BIB files.      */
DECL|method|importButtonClicked ()
specifier|public
name|void
name|importButtonClicked
parameter_list|()
block|{
name|ImportEntriesDialog
name|dialog
init|=
operator|new
name|ImportEntriesDialog
argument_list|(
name|databaseContext
argument_list|,
name|BackgroundTask
operator|.
name|wrap
argument_list|(
parameter_list|()
lambda|->
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|resolverResult
operator|.
name|getNewEntries
argument_list|()
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|dialog
operator|.
name|setTitle
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Import entries from LaTeX files"
argument_list|)
argument_list|)
expr_stmt|;
name|dialog
operator|.
name|showAndWait
argument_list|()
expr_stmt|;
name|close
operator|.
name|set
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

