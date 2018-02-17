begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.migrations
package|package
name|org
operator|.
name|jabref
operator|.
name|migrations
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
name|Objects
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
name|dialogs
operator|.
name|MergeReviewIntoCommentUIManager
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
DECL|class|MergeReviewIntoComment
specifier|public
class|class
name|MergeReviewIntoComment
implements|implements
name|PostOpenMigration
block|{
DECL|field|LOGGER
specifier|public
specifier|static
specifier|final
name|Logger
name|LOGGER
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|MergeReviewIntoComment
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Override
DECL|method|performMigration (ParserResult parserResult)
specifier|public
name|void
name|performMigration
parameter_list|(
name|ParserResult
name|parserResult
parameter_list|)
block|{
name|ObservableList
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|parserResult
argument_list|)
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
comment|// migrate non-conflicting entries first
name|entries
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|this
operator|::
name|hasReviewField
argument_list|)
operator|.
name|filter
argument_list|(
name|entry
lambda|->
operator|!
name|this
operator|.
name|hasCommentField
argument_list|(
name|entry
argument_list|)
argument_list|)
operator|.
name|forEach
argument_list|(
name|entry
lambda|->
name|migrate
argument_list|(
name|entry
argument_list|,
name|parserResult
argument_list|)
argument_list|)
expr_stmt|;
comment|// determine conflicts
name|List
argument_list|<
name|BibEntry
argument_list|>
name|conflicts
init|=
name|entries
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|this
operator|::
name|hasReviewField
argument_list|)
operator|.
name|filter
argument_list|(
name|this
operator|::
name|hasCommentField
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
decl_stmt|;
comment|// resolve conflicts if users agrees
if|if
condition|(
operator|!
name|conflicts
operator|.
name|isEmpty
argument_list|()
operator|&&
operator|new
name|MergeReviewIntoCommentUIManager
argument_list|()
operator|.
name|askUserForMerge
argument_list|(
name|conflicts
argument_list|)
condition|)
block|{
name|conflicts
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|this
operator|::
name|hasReviewField
argument_list|)
operator|.
name|forEach
argument_list|(
name|entry
lambda|->
name|migrate
argument_list|(
name|entry
argument_list|,
name|parserResult
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|mergeCommentFieldIfPresent (BibEntry entry, String review)
specifier|private
name|String
name|mergeCommentFieldIfPresent
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|String
name|review
parameter_list|)
block|{
if|if
condition|(
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|COMMENT
argument_list|)
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Both Comment and Review fields are present in %s! Merging them into the comment field."
argument_list|,
name|entry
operator|.
name|getAuthorTitleYear
argument_list|(
literal|150
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|String
operator|.
name|format
argument_list|(
literal|"%s\n%s:\n%s"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|COMMENT
argument_list|)
operator|.
name|get
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Review"
argument_list|)
argument_list|,
name|review
operator|.
name|trim
argument_list|()
argument_list|)
return|;
block|}
return|return
name|review
return|;
block|}
DECL|method|hasCommentField (BibEntry entry)
specifier|private
name|boolean
name|hasCommentField
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
return|return
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|COMMENT
argument_list|)
operator|.
name|isPresent
argument_list|()
return|;
block|}
DECL|method|hasReviewField (BibEntry entry)
specifier|private
name|boolean
name|hasReviewField
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
return|return
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|REVIEW
argument_list|)
operator|.
name|isPresent
argument_list|()
return|;
block|}
DECL|method|migrate (BibEntry entry, ParserResult parserResult)
specifier|private
name|void
name|migrate
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|ParserResult
name|parserResult
parameter_list|)
block|{
comment|// this method may only be called if the review field is present
name|updateFields
argument_list|(
name|entry
argument_list|,
name|mergeCommentFieldIfPresent
argument_list|(
name|entry
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|REVIEW
argument_list|)
operator|.
name|get
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|parserResult
operator|.
name|wasChangedOnMigration
argument_list|()
expr_stmt|;
block|}
DECL|method|updateFields (BibEntry entry, String review)
specifier|private
name|void
name|updateFields
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|String
name|review
parameter_list|)
block|{
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|COMMENT
argument_list|,
name|review
argument_list|)
expr_stmt|;
name|entry
operator|.
name|clearField
argument_list|(
name|FieldName
operator|.
name|REVIEW
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
