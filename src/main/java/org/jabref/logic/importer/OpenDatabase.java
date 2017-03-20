begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.importer
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
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
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|fileformat
operator|.
name|BibtexImporter
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
name|util
operator|.
name|ConvertLegacyExplicitGroups
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
name|util
operator|.
name|PostOpenAction
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
name|specialfields
operator|.
name|SpecialFieldsUtils
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
name|util
operator|.
name|io
operator|.
name|FileBasedLock
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
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_class
DECL|class|OpenDatabase
specifier|public
class|class
name|OpenDatabase
block|{
DECL|field|LOGGER
specifier|public
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|OpenDatabase
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|method|OpenDatabase ()
specifier|private
name|OpenDatabase
parameter_list|()
block|{     }
comment|/**      * Load database (bib-file)      *      * @param name Name of the BIB-file to open      * @return ParserResult which never is null      */
DECL|method|loadDatabase (String name, ImportFormatPreferences importFormatPreferences)
specifier|public
specifier|static
name|ParserResult
name|loadDatabase
parameter_list|(
name|String
name|name
parameter_list|,
name|ImportFormatPreferences
name|importFormatPreferences
parameter_list|)
block|{
name|File
name|file
init|=
operator|new
name|File
argument_list|(
name|name
argument_list|)
decl_stmt|;
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Opening: "
operator|+
name|name
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|file
operator|.
name|exists
argument_list|()
condition|)
block|{
name|ParserResult
name|pr
init|=
name|ParserResult
operator|.
name|fromErrorMessage
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"File not found"
argument_list|)
argument_list|)
decl_stmt|;
name|pr
operator|.
name|setFile
argument_list|(
name|file
argument_list|)
expr_stmt|;
name|LOGGER
operator|.
name|error
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error"
argument_list|)
operator|+
literal|": "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"File not found"
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|pr
return|;
block|}
try|try
block|{
if|if
condition|(
operator|!
name|FileBasedLock
operator|.
name|waitForFileLock
argument_list|(
name|file
operator|.
name|toPath
argument_list|()
argument_list|)
condition|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error opening file"
argument_list|)
operator|+
literal|" '"
operator|+
name|name
operator|+
literal|"'. "
operator|+
literal|"File is locked by another JabRef instance."
argument_list|)
expr_stmt|;
return|return
operator|new
name|ParserResult
argument_list|()
return|;
block|}
name|ParserResult
name|pr
init|=
name|OpenDatabase
operator|.
name|loadDatabase
argument_list|(
name|file
argument_list|,
name|importFormatPreferences
argument_list|)
decl_stmt|;
name|pr
operator|.
name|setFile
argument_list|(
name|file
argument_list|)
expr_stmt|;
if|if
condition|(
name|pr
operator|.
name|hasWarnings
argument_list|()
condition|)
block|{
for|for
control|(
name|String
name|aWarn
range|:
name|pr
operator|.
name|warnings
argument_list|()
control|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
name|aWarn
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|pr
return|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|ParserResult
name|pr
init|=
name|ParserResult
operator|.
name|fromError
argument_list|(
name|ex
argument_list|)
decl_stmt|;
name|pr
operator|.
name|setFile
argument_list|(
name|file
argument_list|)
expr_stmt|;
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Problem opening .bib-file"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
return|return
name|pr
return|;
block|}
block|}
comment|/**      * Opens a new database.      */
DECL|method|loadDatabase (File fileToOpen, ImportFormatPreferences importFormatPreferences)
specifier|public
specifier|static
name|ParserResult
name|loadDatabase
parameter_list|(
name|File
name|fileToOpen
parameter_list|,
name|ImportFormatPreferences
name|importFormatPreferences
parameter_list|)
throws|throws
name|IOException
block|{
name|ParserResult
name|result
init|=
operator|new
name|BibtexImporter
argument_list|(
name|importFormatPreferences
argument_list|)
operator|.
name|importDatabase
argument_list|(
name|fileToOpen
operator|.
name|toPath
argument_list|()
argument_list|,
name|importFormatPreferences
operator|.
name|getEncoding
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|importFormatPreferences
operator|.
name|isKeywordSyncEnabled
argument_list|()
condition|)
block|{
for|for
control|(
name|BibEntry
name|entry
range|:
name|result
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
control|)
block|{
name|SpecialFieldsUtils
operator|.
name|syncSpecialFieldsFromKeywords
argument_list|(
name|entry
argument_list|,
name|importFormatPreferences
operator|.
name|getKeywordSeparator
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Synchronized special fields based on keywords"
argument_list|)
expr_stmt|;
block|}
name|applyPostActions
argument_list|(
name|result
argument_list|)
expr_stmt|;
return|return
name|result
return|;
block|}
DECL|method|applyPostActions (ParserResult parserResult)
specifier|private
specifier|static
name|void
name|applyPostActions
parameter_list|(
name|ParserResult
name|parserResult
parameter_list|)
block|{
name|List
argument_list|<
name|PostOpenAction
argument_list|>
name|actions
init|=
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|ConvertLegacyExplicitGroups
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|PostOpenAction
name|action
range|:
name|actions
control|)
block|{
name|action
operator|.
name|performAction
argument_list|(
name|parserResult
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

