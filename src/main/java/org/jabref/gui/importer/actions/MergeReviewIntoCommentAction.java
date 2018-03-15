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
name|migrations
operator|.
name|MergeReviewIntoCommentMigration
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

begin_class
DECL|class|MergeReviewIntoCommentAction
specifier|public
class|class
name|MergeReviewIntoCommentAction
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
name|MergeReviewIntoCommentMigration
operator|.
name|needsMigration
argument_list|(
name|parserResult
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|performAction (BasePanel basePanel, ParserResult parserResult)
specifier|public
name|void
name|performAction
parameter_list|(
name|BasePanel
name|basePanel
parameter_list|,
name|ParserResult
name|parserResult
parameter_list|)
block|{
name|MergeReviewIntoCommentMigration
name|migration
init|=
operator|new
name|MergeReviewIntoCommentMigration
argument_list|()
decl_stmt|;
name|migration
operator|.
name|performMigration
argument_list|(
name|parserResult
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|conflicts
init|=
name|MergeReviewIntoCommentMigration
operator|.
name|collectConflicts
argument_list|(
name|parserResult
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|conflicts
operator|.
name|isEmpty
argument_list|()
operator|&&
operator|new
name|MergeReviewIntoCommentConfirmationDialog
argument_list|(
name|basePanel
argument_list|)
operator|.
name|askUserForMerge
argument_list|(
name|conflicts
argument_list|)
condition|)
block|{
name|migration
operator|.
name|performConflictingMigration
argument_list|(
name|parserResult
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

