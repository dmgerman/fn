begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2016 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.     This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.     You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.cleanup
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|cleanup
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
name|Arrays
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
name|Objects
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|BibDatabaseContext
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|FieldChange
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|journals
operator|.
name|JournalAbbreviationRepository
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
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
DECL|class|CleanupWorker
specifier|public
class|class
name|CleanupWorker
block|{
DECL|field|databaseContext
specifier|private
specifier|final
name|BibDatabaseContext
name|databaseContext
decl_stmt|;
DECL|field|repository
specifier|private
specifier|final
name|JournalAbbreviationRepository
name|repository
decl_stmt|;
DECL|field|unsuccessfulRenames
specifier|private
name|int
name|unsuccessfulRenames
decl_stmt|;
DECL|method|CleanupWorker (BibDatabaseContext databaseContext, JournalAbbreviationRepository repository)
specifier|public
name|CleanupWorker
parameter_list|(
name|BibDatabaseContext
name|databaseContext
parameter_list|,
name|JournalAbbreviationRepository
name|repository
parameter_list|)
block|{
name|this
operator|.
name|databaseContext
operator|=
name|databaseContext
expr_stmt|;
name|this
operator|.
name|repository
operator|=
name|repository
expr_stmt|;
block|}
DECL|method|getUnsuccessfulRenames ()
specifier|public
name|int
name|getUnsuccessfulRenames
parameter_list|()
block|{
return|return
name|unsuccessfulRenames
return|;
block|}
DECL|method|cleanup (CleanupPreset preset, BibEntry entry)
specifier|public
name|List
argument_list|<
name|FieldChange
argument_list|>
name|cleanup
parameter_list|(
name|CleanupPreset
name|preset
parameter_list|,
name|BibEntry
name|entry
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|preset
argument_list|)
expr_stmt|;
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|CleanupJob
argument_list|>
name|jobs
init|=
name|determineCleanupActions
argument_list|(
name|preset
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|FieldChange
argument_list|>
name|changes
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|CleanupJob
name|job
range|:
name|jobs
control|)
block|{
name|changes
operator|.
name|addAll
argument_list|(
name|job
operator|.
name|cleanup
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|changes
return|;
block|}
DECL|method|determineCleanupActions (CleanupPreset preset)
specifier|private
name|List
argument_list|<
name|CleanupJob
argument_list|>
name|determineCleanupActions
parameter_list|(
name|CleanupPreset
name|preset
parameter_list|)
block|{
name|List
argument_list|<
name|CleanupJob
argument_list|>
name|jobs
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
if|if
condition|(
name|preset
operator|.
name|isCleanUpUpgradeExternalLinks
argument_list|()
condition|)
block|{
name|jobs
operator|.
name|add
argument_list|(
operator|new
name|UpgradePdfPsToFileCleanup
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|"pdf"
argument_list|,
literal|"ps"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|preset
operator|.
name|isCleanUpDOI
argument_list|()
condition|)
block|{
name|jobs
operator|.
name|add
argument_list|(
operator|new
name|DoiCleanup
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|preset
operator|.
name|isFixFileLinks
argument_list|()
condition|)
block|{
name|jobs
operator|.
name|add
argument_list|(
operator|new
name|FileLinksCleanup
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|preset
operator|.
name|isMovePDF
argument_list|()
condition|)
block|{
name|jobs
operator|.
name|add
argument_list|(
operator|new
name|MoveFilesCleanup
argument_list|(
name|databaseContext
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|preset
operator|.
name|isMakePathsRelative
argument_list|()
condition|)
block|{
name|jobs
operator|.
name|add
argument_list|(
operator|new
name|RelativePathsCleanup
argument_list|(
name|databaseContext
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|preset
operator|.
name|isRenamePDF
argument_list|()
condition|)
block|{
name|RenamePdfCleanup
name|cleaner
init|=
operator|new
name|RenamePdfCleanup
argument_list|(
name|preset
operator|.
name|isRenamePdfOnlyRelativePaths
argument_list|()
argument_list|,
name|databaseContext
argument_list|,
name|repository
argument_list|)
decl_stmt|;
name|jobs
operator|.
name|add
argument_list|(
name|cleaner
argument_list|)
expr_stmt|;
name|unsuccessfulRenames
operator|+=
name|cleaner
operator|.
name|getUnsuccessfulRenames
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|preset
operator|.
name|isConvertToBiblatex
argument_list|()
condition|)
block|{
name|jobs
operator|.
name|add
argument_list|(
operator|new
name|BiblatexCleanup
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|preset
operator|.
name|getFormatterCleanups
argument_list|()
operator|.
name|isEnabled
argument_list|()
condition|)
block|{
name|jobs
operator|.
name|addAll
argument_list|(
name|preset
operator|.
name|getFormatterCleanups
argument_list|()
operator|.
name|getConfiguredActions
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|jobs
return|;
block|}
block|}
end_class

end_unit

