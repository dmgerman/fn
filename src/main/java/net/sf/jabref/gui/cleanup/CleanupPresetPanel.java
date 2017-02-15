begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.cleanup
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
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
name|javax
operator|.
name|swing
operator|.
name|JCheckBox
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JLabel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JPanel
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
name|Globals
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
name|cleanup
operator|.
name|CleanupPreset
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
name|cleanup
operator|.
name|Cleanups
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
name|l10n
operator|.
name|Localization
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
name|database
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
name|model
operator|.
name|entry
operator|.
name|FieldName
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

begin_import
import|import
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|builder
operator|.
name|FormBuilder
import|;
end_import

begin_import
import|import
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|layout
operator|.
name|FormLayout
import|;
end_import

begin_class
DECL|class|CleanupPresetPanel
specifier|public
class|class
name|CleanupPresetPanel
block|{
DECL|field|databaseContext
specifier|private
specifier|final
name|BibDatabaseContext
name|databaseContext
decl_stmt|;
DECL|field|cleanUpDOI
specifier|private
name|JCheckBox
name|cleanUpDOI
decl_stmt|;
DECL|field|cleanUpISSN
specifier|private
name|JCheckBox
name|cleanUpISSN
decl_stmt|;
DECL|field|cleanUpMovePDF
specifier|private
name|JCheckBox
name|cleanUpMovePDF
decl_stmt|;
DECL|field|cleanUpMakePathsRelative
specifier|private
name|JCheckBox
name|cleanUpMakePathsRelative
decl_stmt|;
DECL|field|cleanUpRenamePDF
specifier|private
name|JCheckBox
name|cleanUpRenamePDF
decl_stmt|;
DECL|field|cleanUpRenamePDFonlyRelativePaths
specifier|private
name|JCheckBox
name|cleanUpRenamePDFonlyRelativePaths
decl_stmt|;
DECL|field|cleanUpUpgradeExternalLinks
specifier|private
name|JCheckBox
name|cleanUpUpgradeExternalLinks
decl_stmt|;
DECL|field|cleanUpBibLatex
specifier|private
name|JCheckBox
name|cleanUpBibLatex
decl_stmt|;
DECL|field|cleanUpFormatters
specifier|private
name|FieldFormatterCleanupsPanel
name|cleanUpFormatters
decl_stmt|;
DECL|field|panel
specifier|private
name|JPanel
name|panel
decl_stmt|;
DECL|field|cleanupPreset
specifier|private
name|CleanupPreset
name|cleanupPreset
decl_stmt|;
DECL|method|CleanupPresetPanel (BibDatabaseContext databaseContext, CleanupPreset cleanupPreset)
specifier|public
name|CleanupPresetPanel
parameter_list|(
name|BibDatabaseContext
name|databaseContext
parameter_list|,
name|CleanupPreset
name|cleanupPreset
parameter_list|)
block|{
name|this
operator|.
name|cleanupPreset
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|cleanupPreset
argument_list|)
expr_stmt|;
name|this
operator|.
name|databaseContext
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|databaseContext
argument_list|)
expr_stmt|;
name|init
argument_list|()
expr_stmt|;
block|}
DECL|method|init ()
specifier|private
name|void
name|init
parameter_list|()
block|{
name|cleanUpDOI
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Move DOIs from note and URL field to DOI field and remove http prefix"
argument_list|)
argument_list|)
expr_stmt|;
name|cleanUpISSN
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Reformat ISSN"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|databaseContext
operator|.
name|getMetaData
argument_list|()
operator|.
name|getDefaultFileDirectory
argument_list|()
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|cleanUpMovePDF
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Move linked files to default file directory %0"
argument_list|,
name|databaseContext
operator|.
name|getMetaData
argument_list|()
operator|.
name|getDefaultFileDirectory
argument_list|()
operator|.
name|get
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|cleanUpMovePDF
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Move linked files to default file directory %0"
argument_list|,
literal|"..."
argument_list|)
argument_list|)
expr_stmt|;
name|cleanUpMovePDF
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|cleanUpMovePDF
operator|.
name|setSelected
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
name|cleanUpMakePathsRelative
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Make paths of linked files relative (if possible)"
argument_list|)
argument_list|)
expr_stmt|;
name|cleanUpRenamePDF
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Rename PDFs to given filename format pattern"
argument_list|)
argument_list|)
expr_stmt|;
name|cleanUpRenamePDF
operator|.
name|addChangeListener
argument_list|(
name|event
lambda|->
name|cleanUpRenamePDFonlyRelativePaths
operator|.
name|setEnabled
argument_list|(
name|cleanUpRenamePDF
operator|.
name|isSelected
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|cleanUpRenamePDFonlyRelativePaths
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Rename only PDFs having a relative path"
argument_list|)
argument_list|)
expr_stmt|;
name|cleanUpUpgradeExternalLinks
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Upgrade external PDF/PS links to use the '%0' field."
argument_list|,
name|FieldName
operator|.
name|FILE
argument_list|)
argument_list|)
expr_stmt|;
name|cleanUpBibLatex
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Convert to BibLatex format (for example, move the value of the 'journal' field to 'journaltitle')"
argument_list|)
argument_list|)
expr_stmt|;
name|cleanUpFormatters
operator|=
operator|new
name|FieldFormatterCleanupsPanel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Run field formatter:"
argument_list|)
argument_list|,
name|Cleanups
operator|.
name|DEFAULT_SAVE_ACTIONS
argument_list|)
expr_stmt|;
name|updateDisplay
argument_list|(
name|cleanupPreset
argument_list|)
expr_stmt|;
name|FormLayout
name|layout
init|=
operator|new
name|FormLayout
argument_list|(
literal|"left:15dlu, pref:grow"
argument_list|,
literal|"pref, pref, pref, pref, pref, pref, pref,pref, pref,190dlu, fill:pref:grow,"
argument_list|)
decl_stmt|;
name|FormBuilder
name|builder
init|=
name|FormBuilder
operator|.
name|create
argument_list|()
operator|.
name|layout
argument_list|(
name|layout
argument_list|)
decl_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|cleanUpDOI
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|,
literal|2
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|cleanUpUpgradeExternalLinks
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|2
argument_list|,
literal|2
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|cleanUpMovePDF
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|3
argument_list|,
literal|2
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|cleanUpMakePathsRelative
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|4
argument_list|,
literal|2
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|cleanUpRenamePDF
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|5
argument_list|,
literal|2
argument_list|)
expr_stmt|;
name|String
name|currentPattern
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Filename format pattern"
argument_list|)
operator|.
name|concat
argument_list|(
literal|": "
argument_list|)
operator|.
name|concat
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|IMPORT_FILENAMEPATTERN
argument_list|)
argument_list|)
decl_stmt|;
name|builder
operator|.
name|add
argument_list|(
operator|new
name|JLabel
argument_list|(
name|currentPattern
argument_list|)
argument_list|)
operator|.
name|xy
argument_list|(
literal|2
argument_list|,
literal|6
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|cleanUpRenamePDFonlyRelativePaths
argument_list|)
operator|.
name|xy
argument_list|(
literal|2
argument_list|,
literal|7
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|cleanUpBibLatex
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|8
argument_list|,
literal|2
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|cleanUpISSN
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|9
argument_list|,
literal|2
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|cleanUpFormatters
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|10
argument_list|,
literal|2
argument_list|)
expr_stmt|;
name|panel
operator|=
name|builder
operator|.
name|build
argument_list|()
expr_stmt|;
block|}
DECL|method|updateDisplay (CleanupPreset preset)
specifier|private
name|void
name|updateDisplay
parameter_list|(
name|CleanupPreset
name|preset
parameter_list|)
block|{
name|cleanUpDOI
operator|.
name|setSelected
argument_list|(
name|preset
operator|.
name|isCleanUpDOI
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|cleanUpMovePDF
operator|.
name|isEnabled
argument_list|()
condition|)
block|{
name|cleanUpMovePDF
operator|.
name|setSelected
argument_list|(
name|preset
operator|.
name|isMovePDF
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|cleanUpMakePathsRelative
operator|.
name|setSelected
argument_list|(
name|preset
operator|.
name|isMakePathsRelative
argument_list|()
argument_list|)
expr_stmt|;
name|cleanUpRenamePDF
operator|.
name|setSelected
argument_list|(
name|preset
operator|.
name|isRenamePDF
argument_list|()
argument_list|)
expr_stmt|;
name|cleanUpRenamePDFonlyRelativePaths
operator|.
name|setSelected
argument_list|(
name|preset
operator|.
name|isRenamePdfOnlyRelativePaths
argument_list|()
argument_list|)
expr_stmt|;
name|cleanUpRenamePDFonlyRelativePaths
operator|.
name|setEnabled
argument_list|(
name|cleanUpRenamePDF
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|cleanUpUpgradeExternalLinks
operator|.
name|setSelected
argument_list|(
name|preset
operator|.
name|isCleanUpUpgradeExternalLinks
argument_list|()
argument_list|)
expr_stmt|;
name|cleanUpBibLatex
operator|.
name|setSelected
argument_list|(
name|preset
operator|.
name|isConvertToBiblatex
argument_list|()
argument_list|)
expr_stmt|;
name|cleanUpBibLatex
operator|.
name|setSelected
argument_list|(
name|preset
operator|.
name|isCleanUpISSN
argument_list|()
argument_list|)
expr_stmt|;
name|cleanUpFormatters
operator|.
name|setValues
argument_list|(
name|preset
operator|.
name|getFormatterCleanups
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|getPanel ()
specifier|public
name|JPanel
name|getPanel
parameter_list|()
block|{
return|return
name|panel
return|;
block|}
DECL|method|getCleanupPreset ()
specifier|public
name|CleanupPreset
name|getCleanupPreset
parameter_list|()
block|{
name|Set
argument_list|<
name|CleanupPreset
operator|.
name|CleanupStep
argument_list|>
name|activeJobs
init|=
name|EnumSet
operator|.
name|noneOf
argument_list|(
name|CleanupPreset
operator|.
name|CleanupStep
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
name|cleanUpMovePDF
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|activeJobs
operator|.
name|add
argument_list|(
name|CleanupPreset
operator|.
name|CleanupStep
operator|.
name|MOVE_PDF
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|cleanUpDOI
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|activeJobs
operator|.
name|add
argument_list|(
name|CleanupPreset
operator|.
name|CleanupStep
operator|.
name|CLEAN_UP_DOI
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|cleanUpISSN
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|activeJobs
operator|.
name|add
argument_list|(
name|CleanupPreset
operator|.
name|CleanupStep
operator|.
name|CLEAN_UP_ISSN
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|cleanUpMakePathsRelative
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|activeJobs
operator|.
name|add
argument_list|(
name|CleanupPreset
operator|.
name|CleanupStep
operator|.
name|MAKE_PATHS_RELATIVE
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|cleanUpRenamePDF
operator|.
name|isSelected
argument_list|()
condition|)
block|{
if|if
condition|(
name|cleanUpRenamePDFonlyRelativePaths
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|activeJobs
operator|.
name|add
argument_list|(
name|CleanupPreset
operator|.
name|CleanupStep
operator|.
name|RENAME_PDF_ONLY_RELATIVE_PATHS
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|activeJobs
operator|.
name|add
argument_list|(
name|CleanupPreset
operator|.
name|CleanupStep
operator|.
name|RENAME_PDF
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|cleanUpUpgradeExternalLinks
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|activeJobs
operator|.
name|add
argument_list|(
name|CleanupPreset
operator|.
name|CleanupStep
operator|.
name|CLEAN_UP_UPGRADE_EXTERNAL_LINKS
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|cleanUpBibLatex
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|activeJobs
operator|.
name|add
argument_list|(
name|CleanupPreset
operator|.
name|CleanupStep
operator|.
name|CONVERT_TO_BIBLATEX
argument_list|)
expr_stmt|;
block|}
name|activeJobs
operator|.
name|add
argument_list|(
name|CleanupPreset
operator|.
name|CleanupStep
operator|.
name|FIX_FILE_LINKS
argument_list|)
expr_stmt|;
name|cleanupPreset
operator|=
operator|new
name|CleanupPreset
argument_list|(
name|activeJobs
argument_list|,
name|cleanUpFormatters
operator|.
name|getFormatterCleanups
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|cleanupPreset
return|;
block|}
block|}
end_class

end_unit

