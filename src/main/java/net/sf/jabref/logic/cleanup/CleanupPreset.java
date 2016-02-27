begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.     This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.     You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|JabRefPreferences
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
name|exporter
operator|.
name|FieldFormatterCleanups
import|;
end_import

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

begin_class
DECL|class|CleanupPreset
specifier|public
class|class
name|CleanupPreset
block|{
DECL|field|activeJobs
specifier|private
specifier|final
name|EnumSet
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
DECL|method|CleanupPreset (EnumSet<CleanupStep> activeJobs)
specifier|public
name|CleanupPreset
parameter_list|(
name|EnumSet
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
DECL|method|CleanupPreset (EnumSet<CleanupStep> activeJobs, FieldFormatterCleanups formatterCleanups)
specifier|public
name|CleanupPreset
parameter_list|(
name|EnumSet
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
name|EnumSet
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
name|CLEANUP_SUPERSCRIPTS
argument_list|)
condition|)
block|{
name|activeJobs
operator|.
name|add
argument_list|(
name|CleanupStep
operator|.
name|CLEAN_UP_SUPERSCRIPTS
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
name|CLEANUP_UNICODE
argument_list|)
condition|)
block|{
name|activeJobs
operator|.
name|add
argument_list|(
name|CleanupStep
operator|.
name|CONVERT_UNICODE_TO_LATEX
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
name|FieldFormatterCleanups
operator|.
name|parseFromString
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
DECL|method|isCleanUpSuperscripts ()
specifier|public
name|boolean
name|isCleanUpSuperscripts
parameter_list|()
block|{
return|return
name|isActive
argument_list|(
name|CleanupStep
operator|.
name|CLEAN_UP_SUPERSCRIPTS
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
DECL|method|isConvertUnicodeToLatex ()
specifier|public
name|boolean
name|isConvertUnicodeToLatex
parameter_list|()
block|{
return|return
name|isActive
argument_list|(
name|CleanupStep
operator|.
name|CONVERT_UNICODE_TO_LATEX
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
name|CLEANUP_SUPERSCRIPTS
argument_list|,
name|isActive
argument_list|(
name|CleanupStep
operator|.
name|CLEAN_UP_SUPERSCRIPTS
argument_list|)
argument_list|)
expr_stmt|;
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
name|CLEANUP_UNICODE
argument_list|,
name|isActive
argument_list|(
name|CleanupStep
operator|.
name|CONVERT_UNICODE_TO_LATEX
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
name|convertToString
argument_list|()
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
comment|/**          * Converts the text in 1st, 2nd, ... to real superscripts by wrapping in \textsuperscript{st}, ...          */
DECL|enumConstant|CLEAN_UP_SUPERSCRIPTS
name|CLEAN_UP_SUPERSCRIPTS
block|,
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
comment|/**          * Converts Unicode characters to LaTeX code          */
DECL|enumConstant|CONVERT_UNICODE_TO_LATEX
name|CONVERT_UNICODE_TO_LATEX
block|,
comment|/**          * Converts to BibLatex format          */
DECL|enumConstant|CONVERT_TO_BIBLATEX
name|CONVERT_TO_BIBLATEX
block|,
DECL|enumConstant|FIX_FILE_LINKS
name|FIX_FILE_LINKS
block|}
block|}
end_class

end_unit

