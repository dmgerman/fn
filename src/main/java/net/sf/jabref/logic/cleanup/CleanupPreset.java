begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|EnumSet
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
name|Set
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
name|util
operator|.
name|OS
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
name|cleanup
operator|.
name|FieldFormatterCleanups
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
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_class
DECL|class|CleanupPreset
specifier|public
class|class
name|CleanupPreset
block|{
DECL|field|activeJobs
specifier|private
specifier|final
name|Set
argument_list|<
name|CleanupStep
argument_list|>
name|activeJobs
decl_stmt|;
DECL|field|formatterCleanups
specifier|private
specifier|final
name|FieldFormatterCleanups
name|formatterCleanups
decl_stmt|;
DECL|method|CleanupPreset (Set<CleanupStep> activeJobs)
specifier|public
name|CleanupPreset
parameter_list|(
name|Set
argument_list|<
name|CleanupStep
argument_list|>
name|activeJobs
parameter_list|)
block|{
name|this
argument_list|(
name|activeJobs
argument_list|,
operator|new
name|FieldFormatterCleanups
argument_list|(
literal|false
argument_list|,
operator|new
name|ArrayList
argument_list|<>
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|CleanupPreset (CleanupStep activeJob)
specifier|public
name|CleanupPreset
parameter_list|(
name|CleanupStep
name|activeJob
parameter_list|)
block|{
name|this
argument_list|(
name|EnumSet
operator|.
name|of
argument_list|(
name|activeJob
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|CleanupPreset (FieldFormatterCleanups formatterCleanups)
specifier|public
name|CleanupPreset
parameter_list|(
name|FieldFormatterCleanups
name|formatterCleanups
parameter_list|)
block|{
name|this
argument_list|(
name|EnumSet
operator|.
name|noneOf
argument_list|(
name|CleanupStep
operator|.
name|class
argument_list|)
argument_list|,
name|formatterCleanups
argument_list|)
expr_stmt|;
block|}
DECL|method|CleanupPreset (Set<CleanupStep> activeJobs, FieldFormatterCleanups formatterCleanups)
specifier|public
name|CleanupPreset
parameter_list|(
name|Set
argument_list|<
name|CleanupStep
argument_list|>
name|activeJobs
parameter_list|,
name|FieldFormatterCleanups
name|formatterCleanups
parameter_list|)
block|{
name|this
operator|.
name|activeJobs
operator|=
name|activeJobs
expr_stmt|;
name|this
operator|.
name|formatterCleanups
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|formatterCleanups
argument_list|)
expr_stmt|;
block|}
DECL|method|loadFromPreferences (JabRefPreferences preferences)
specifier|public
specifier|static
name|CleanupPreset
name|loadFromPreferences
parameter_list|(
name|JabRefPreferences
name|preferences
parameter_list|)
block|{
name|Set
argument_list|<
name|CleanupStep
argument_list|>
name|activeJobs
init|=
name|EnumSet
operator|.
name|noneOf
argument_list|(
name|CleanupStep
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|CLEANUP_DOI
argument_list|)
condition|)
block|{
name|activeJobs
operator|.
name|add
argument_list|(
name|CleanupStep
operator|.
name|CLEAN_UP_DOI
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|CLEANUP_ISSN
argument_list|)
condition|)
block|{
name|activeJobs
operator|.
name|add
argument_list|(
name|CleanupStep
operator|.
name|CLEAN_UP_ISSN
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|CLEANUP_MOVE_PDF
argument_list|)
condition|)
block|{
name|activeJobs
operator|.
name|add
argument_list|(
name|CleanupStep
operator|.
name|MOVE_PDF
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|CLEANUP_MAKE_PATHS_RELATIVE
argument_list|)
condition|)
block|{
name|activeJobs
operator|.
name|add
argument_list|(
name|CleanupStep
operator|.
name|MAKE_PATHS_RELATIVE
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|CLEANUP_RENAME_PDF
argument_list|)
condition|)
block|{
name|activeJobs
operator|.
name|add
argument_list|(
name|CleanupStep
operator|.
name|RENAME_PDF
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|CLEANUP_RENAME_PDF_ONLY_RELATIVE_PATHS
argument_list|)
condition|)
block|{
name|activeJobs
operator|.
name|add
argument_list|(
name|CleanupStep
operator|.
name|RENAME_PDF_ONLY_RELATIVE_PATHS
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|CLEANUP_UPGRADE_EXTERNAL_LINKS
argument_list|)
condition|)
block|{
name|activeJobs
operator|.
name|add
argument_list|(
name|CleanupStep
operator|.
name|CLEAN_UP_UPGRADE_EXTERNAL_LINKS
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|CLEANUP_CONVERT_TO_BIBLATEX
argument_list|)
condition|)
block|{
name|activeJobs
operator|.
name|add
argument_list|(
name|CleanupStep
operator|.
name|CONVERT_TO_BIBLATEX
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|CLEANUP_FIX_FILE_LINKS
argument_list|)
condition|)
block|{
name|activeJobs
operator|.
name|add
argument_list|(
name|CleanupStep
operator|.
name|FIX_FILE_LINKS
argument_list|)
expr_stmt|;
block|}
name|FieldFormatterCleanups
name|formatterCleanups
init|=
name|Cleanups
operator|.
name|parse
argument_list|(
name|preferences
operator|.
name|getStringList
argument_list|(
name|JabRefPreferences
operator|.
name|CLEANUP_FORMATTERS
argument_list|)
argument_list|)
decl_stmt|;
return|return
operator|new
name|CleanupPreset
argument_list|(
name|activeJobs
argument_list|,
name|formatterCleanups
argument_list|)
return|;
block|}
DECL|method|isCleanUpUpgradeExternalLinks ()
specifier|public
name|boolean
name|isCleanUpUpgradeExternalLinks
parameter_list|()
block|{
return|return
name|isActive
argument_list|(
name|CleanupStep
operator|.
name|CLEAN_UP_UPGRADE_EXTERNAL_LINKS
argument_list|)
return|;
block|}
DECL|method|isCleanUpDOI ()
specifier|public
name|boolean
name|isCleanUpDOI
parameter_list|()
block|{
return|return
name|isActive
argument_list|(
name|CleanupStep
operator|.
name|CLEAN_UP_DOI
argument_list|)
return|;
block|}
DECL|method|isCleanUpISSN ()
specifier|public
name|boolean
name|isCleanUpISSN
parameter_list|()
block|{
return|return
name|isActive
argument_list|(
name|CleanupStep
operator|.
name|CLEAN_UP_ISSN
argument_list|)
return|;
block|}
DECL|method|isFixFileLinks ()
specifier|public
name|boolean
name|isFixFileLinks
parameter_list|()
block|{
return|return
name|isActive
argument_list|(
name|CleanupStep
operator|.
name|FIX_FILE_LINKS
argument_list|)
return|;
block|}
DECL|method|isMovePDF ()
specifier|public
name|boolean
name|isMovePDF
parameter_list|()
block|{
return|return
name|isActive
argument_list|(
name|CleanupStep
operator|.
name|MOVE_PDF
argument_list|)
return|;
block|}
DECL|method|isMakePathsRelative ()
specifier|public
name|boolean
name|isMakePathsRelative
parameter_list|()
block|{
return|return
name|isActive
argument_list|(
name|CleanupStep
operator|.
name|MAKE_PATHS_RELATIVE
argument_list|)
return|;
block|}
DECL|method|isRenamePDF ()
specifier|public
name|boolean
name|isRenamePDF
parameter_list|()
block|{
return|return
name|isActive
argument_list|(
name|CleanupStep
operator|.
name|RENAME_PDF
argument_list|)
operator|||
name|isActive
argument_list|(
name|CleanupStep
operator|.
name|RENAME_PDF_ONLY_RELATIVE_PATHS
argument_list|)
return|;
block|}
DECL|method|isConvertToBiblatex ()
specifier|public
name|boolean
name|isConvertToBiblatex
parameter_list|()
block|{
return|return
name|isActive
argument_list|(
name|CleanupStep
operator|.
name|CONVERT_TO_BIBLATEX
argument_list|)
return|;
block|}
DECL|method|isRenamePdfOnlyRelativePaths ()
specifier|public
name|boolean
name|isRenamePdfOnlyRelativePaths
parameter_list|()
block|{
return|return
name|isActive
argument_list|(
name|CleanupStep
operator|.
name|RENAME_PDF_ONLY_RELATIVE_PATHS
argument_list|)
return|;
block|}
DECL|method|storeInPreferences (JabRefPreferences preferences)
specifier|public
name|void
name|storeInPreferences
parameter_list|(
name|JabRefPreferences
name|preferences
parameter_list|)
block|{
name|preferences
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|CLEANUP_DOI
argument_list|,
name|isActive
argument_list|(
name|CleanupStep
operator|.
name|CLEAN_UP_DOI
argument_list|)
argument_list|)
expr_stmt|;
name|preferences
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|CLEANUP_ISSN
argument_list|,
name|isActive
argument_list|(
name|CleanupStep
operator|.
name|CLEAN_UP_ISSN
argument_list|)
argument_list|)
expr_stmt|;
name|preferences
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|CLEANUP_MOVE_PDF
argument_list|,
name|isActive
argument_list|(
name|CleanupStep
operator|.
name|MOVE_PDF
argument_list|)
argument_list|)
expr_stmt|;
name|preferences
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|CLEANUP_MAKE_PATHS_RELATIVE
argument_list|,
name|isActive
argument_list|(
name|CleanupStep
operator|.
name|MAKE_PATHS_RELATIVE
argument_list|)
argument_list|)
expr_stmt|;
name|preferences
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|CLEANUP_RENAME_PDF
argument_list|,
name|isActive
argument_list|(
name|CleanupStep
operator|.
name|RENAME_PDF
argument_list|)
argument_list|)
expr_stmt|;
name|preferences
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|CLEANUP_RENAME_PDF_ONLY_RELATIVE_PATHS
argument_list|,
name|isActive
argument_list|(
name|CleanupStep
operator|.
name|RENAME_PDF_ONLY_RELATIVE_PATHS
argument_list|)
argument_list|)
expr_stmt|;
name|preferences
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|CLEANUP_UPGRADE_EXTERNAL_LINKS
argument_list|,
name|isActive
argument_list|(
name|CleanupStep
operator|.
name|CLEAN_UP_UPGRADE_EXTERNAL_LINKS
argument_list|)
argument_list|)
expr_stmt|;
name|preferences
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|CLEANUP_CONVERT_TO_BIBLATEX
argument_list|,
name|isActive
argument_list|(
name|CleanupStep
operator|.
name|CONVERT_TO_BIBLATEX
argument_list|)
argument_list|)
expr_stmt|;
name|preferences
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|CLEANUP_FIX_FILE_LINKS
argument_list|,
name|isActive
argument_list|(
name|CleanupStep
operator|.
name|FIX_FILE_LINKS
argument_list|)
argument_list|)
expr_stmt|;
name|preferences
operator|.
name|putStringList
argument_list|(
name|JabRefPreferences
operator|.
name|CLEANUP_FORMATTERS
argument_list|,
name|formatterCleanups
operator|.
name|getAsStringList
argument_list|(
name|OS
operator|.
name|NEWLINE
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|isActive (CleanupStep step)
specifier|private
name|Boolean
name|isActive
parameter_list|(
name|CleanupStep
name|step
parameter_list|)
block|{
return|return
name|activeJobs
operator|.
name|contains
argument_list|(
name|step
argument_list|)
return|;
block|}
DECL|method|getFormatterCleanups ()
specifier|public
name|FieldFormatterCleanups
name|getFormatterCleanups
parameter_list|()
block|{
return|return
name|formatterCleanups
return|;
block|}
DECL|enum|CleanupStep
specifier|public
enum|enum
name|CleanupStep
block|{
comment|/**          * Removes the http://... for each DOI. Moves DOIs from URL and NOTE filed to DOI field.          */
DECL|enumConstant|CLEAN_UP_DOI
name|CLEAN_UP_DOI
block|,
DECL|enumConstant|MAKE_PATHS_RELATIVE
name|MAKE_PATHS_RELATIVE
block|,
DECL|enumConstant|RENAME_PDF
name|RENAME_PDF
block|,
DECL|enumConstant|RENAME_PDF_ONLY_RELATIVE_PATHS
name|RENAME_PDF_ONLY_RELATIVE_PATHS
block|,
comment|/**          * Collects file links from the pdf or ps field, and adds them to the list contained in the file field.          */
DECL|enumConstant|CLEAN_UP_UPGRADE_EXTERNAL_LINKS
name|CLEAN_UP_UPGRADE_EXTERNAL_LINKS
block|,
comment|/**          * Converts to biblatex format          */
DECL|enumConstant|CONVERT_TO_BIBLATEX
name|CONVERT_TO_BIBLATEX
block|,
DECL|enumConstant|MOVE_PDF
name|MOVE_PDF
block|,
DECL|enumConstant|FIX_FILE_LINKS
name|FIX_FILE_LINKS
block|,
DECL|enumConstant|CLEAN_UP_ISSN
name|CLEAN_UP_ISSN
block|}
block|}
end_class

end_unit

