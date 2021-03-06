begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.importer.actions
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|importer
operator|.
name|actions
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
name|stream
operator|.
name|Collectors
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
name|importer
operator|.
name|ImportCustomEntryTypesDialog
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
name|ParserResult
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
name|BibDatabaseMode
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
name|BibEntryType
import|;
end_import

begin_comment
comment|/**  * This action checks whether any new custom entry types were loaded from this  * BIB file. If so, an offer to remember these entry types is given.  */
end_comment

begin_class
DECL|class|CheckForNewEntryTypesAction
specifier|public
class|class
name|CheckForNewEntryTypesAction
implements|implements
name|GUIPostOpenAction
block|{
annotation|@
name|Override
DECL|method|isActionNecessary (ParserResult parserResult)
specifier|public
name|boolean
name|isActionNecessary
parameter_list|(
name|ParserResult
name|parserResult
parameter_list|)
block|{
return|return
operator|!
name|getListOfUnknownAndUnequalCustomizations
argument_list|(
name|parserResult
argument_list|)
operator|.
name|isEmpty
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|performAction (BasePanel panel, ParserResult parserResult)
specifier|public
name|void
name|performAction
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|ParserResult
name|parserResult
parameter_list|)
block|{
name|BibDatabaseMode
name|mode
init|=
name|getBibDatabaseModeFromParserResult
argument_list|(
name|parserResult
argument_list|)
decl_stmt|;
name|ImportCustomEntryTypesDialog
name|importBibEntryTypesDialog
init|=
operator|new
name|ImportCustomEntryTypesDialog
argument_list|(
name|mode
argument_list|,
name|getListOfUnknownAndUnequalCustomizations
argument_list|(
name|parserResult
argument_list|)
argument_list|)
decl_stmt|;
name|importBibEntryTypesDialog
operator|.
name|showAndWait
argument_list|()
expr_stmt|;
block|}
DECL|method|getListOfUnknownAndUnequalCustomizations (ParserResult parserResult)
specifier|private
name|List
argument_list|<
name|BibEntryType
argument_list|>
name|getListOfUnknownAndUnequalCustomizations
parameter_list|(
name|ParserResult
name|parserResult
parameter_list|)
block|{
name|BibDatabaseMode
name|mode
init|=
name|getBibDatabaseModeFromParserResult
argument_list|(
name|parserResult
argument_list|)
decl_stmt|;
return|return
name|parserResult
operator|.
name|getEntryTypes
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|type
lambda|->
name|Globals
operator|.
name|entryTypesManager
operator|.
name|isCustomizedType
argument_list|(
name|type
argument_list|,
name|mode
argument_list|)
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
return|;
block|}
DECL|method|getBibDatabaseModeFromParserResult (ParserResult parserResult)
specifier|private
name|BibDatabaseMode
name|getBibDatabaseModeFromParserResult
parameter_list|(
name|ParserResult
name|parserResult
parameter_list|)
block|{
return|return
name|parserResult
operator|.
name|getMetaData
argument_list|()
operator|.
name|getMode
argument_list|()
operator|.
name|orElse
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getDefaultBibDatabaseMode
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

